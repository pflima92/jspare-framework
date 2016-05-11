/*
 * Copyright 2016 JSpare.org.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package org.jspare.server.jetty;

import java.net.URI;
import java.util.Optional;
import java.util.concurrent.ThreadFactory;

import javax.ws.rs.ProcessingException;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.glassfish.jersey.jetty.JettyHttpContainer;
import org.glassfish.jersey.jetty.internal.LocalizationMessages;
import org.glassfish.jersey.process.JerseyProcessingUncaughtExceptionHandler;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;

import jersey.repackaged.com.google.common.util.concurrent.ThreadFactoryBuilder;

/**
 * A factory for creating JettyServerEngine objects.
 */
public final class JettyServerEngineFactory {

	/**
	 * The Class JettyConnectorThreadPool.
	 *
	 * @author pflima
	 * @since 30/03/2016
	 */
	private static final class JettyConnectorThreadPool extends QueuedThreadPool {

		/** The thread factory. */
		private final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("jetty-http-server-%d")
				.setUncaughtExceptionHandler(new JerseyProcessingUncaughtExceptionHandler()).build();

		/**
		 * Instantiates a new jetty connector thread pool.
		 */
		public JettyConnectorThreadPool() {
			setName("jspare-server-thread");
			setMinThreads(50);
			setDaemon(false);
			setDetailedDump(true);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.eclipse.jetty.util.thread.QueuedThreadPool#newThread(java.lang.
		 * Runnable)
		 */
		@Override
		protected Thread newThread(Runnable runnable) {
			return threadFactory.newThread(runnable);
		}
	}

	/**
	 * Creates a new JettyServerEngine object.
	 *
	 * @param uri
	 *            the uri
	 * @param resourceConfig
	 *            the resource config
	 * @param sslContextFactory
	 *            the ssl context factory
	 * @param start
	 *            the start
	 * @return the server
	 */
	public static Server createServer(final URI uri, final ResourceConfig resourceConfig,
			final Optional<SslContextFactory> sslContextFactory, boolean start) {

		final JettyHttpContainer handler = ContainerFactory.createContainer(JettyHttpContainer.class, resourceConfig);

		if (uri == null) {
			throw new IllegalArgumentException(LocalizationMessages.URI_CANNOT_BE_NULL());
		}
		final String scheme = uri.getScheme();
		int defaultPort = Container.DEFAULT_HTTP_PORT;

		if (!sslContextFactory.isPresent()) {
			if (!"http".equalsIgnoreCase(scheme)) {
				throw new IllegalArgumentException(LocalizationMessages.WRONG_SCHEME_WHEN_USING_HTTP());
			}
		} else {
			if (!"https".equalsIgnoreCase(scheme)) {
				throw new IllegalArgumentException(LocalizationMessages.WRONG_SCHEME_WHEN_USING_HTTPS());
			}
			defaultPort = Container.DEFAULT_HTTPS_PORT;
		}
		final int port = (uri.getPort() == -1) ? defaultPort : uri.getPort();

		final Server server = new Server(new JettyConnectorThreadPool());
		final HttpConfiguration config = new HttpConfiguration();

		if (sslContextFactory.isPresent()) {
			config.setSecureScheme("https");
			config.setSecurePort(port);
			config.addCustomizer(new SecureRequestCustomizer());

			final ServerConnector https = new ServerConnector(server, new SslConnectionFactory(sslContextFactory.get(), "http/1.1"),
					new HttpConnectionFactory(config));
			https.setPort(port);
			server.setConnectors(new Connector[] { https });

		} else {
			final ServerConnector http = new ServerConnector(server, new HttpConnectionFactory(config));
			http.setPort(port);
			server.setConnectors(new Connector[] { http });
		}
		if (handler != null) {
			server.setHandler(handler);
		}

		if (start) {
			try {
				// Start the server.
				server.start();
			} catch (final Exception e) {
				throw new ProcessingException(LocalizationMessages.ERROR_WHEN_CREATING_SERVER(), e);
			}
		}
		return server;
	}

	/**
	 * Instantiates a new jetty server engine factory.
	 */
	private JettyServerEngineFactory() {
	}
}
