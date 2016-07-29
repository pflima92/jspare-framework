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

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;
import static org.jspare.server.commons.Definitions.CERTIFICATE_ENABLE;
import static org.jspare.server.commons.Definitions.CERTIFICATE_KEYSTORE_KEY;
import static org.jspare.server.commons.Definitions.CERTIFICATE_KEYSTORE_PASSWORD;
import static org.jspare.server.commons.Definitions.CERTIFICATE_KEYSTORE_PASSWORD_KEY;
import static org.jspare.server.commons.Definitions.CERTIFICATE_KEYSTORE_PATH;
import static org.jspare.server.commons.Definitions.SERVER_PORT_DEFAULT;
import static org.jspare.server.commons.Definitions.SERVER_PORT_KEY;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.ServerErrorException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.http.HttpStatus;
import org.eclipse.jetty.server.ConnectionFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.glassfish.jersey.process.Inflector;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.model.Resource;
import org.glassfish.jersey.server.model.Resource.Builder;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.jspare.core.container.Inject;
import org.jspare.core.exception.SerializationException;
import org.jspare.core.serializer.Json;
import org.jspare.server.Router;
import org.jspare.server.Server;
import org.jspare.server.controller.CommandData;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.mapping.Type;
import org.jspare.server.transaction.TransactionStatus;
import org.jspare.server.transaction.model.Yield;
import org.jspare.server.transport.Status;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyServer implements Server {

	@Slf4j
	static class ResourceWrapper extends ResourceConfig {

		/** The Constant DEFAULT_CONSUMES_AND_PRODUCES. */
		private static final String[] DEFAULT_CONSUMES_AND_PRODUCES = { "*/*" };

		/**
		 * Handle.
		 *
		 * @param cmd
		 *            the cmd
		 * @return the resource
		 */
		public static Resource handle(CommandData cmd) {

			final Resource.Builder resourceBuilder = Resource.builder();

			resourceBuilder.path(resolvePattern(cmd.getCommand()));

			resolveHttpMethod(cmd.getTypes()).forEach(method -> build(resourceBuilder, method, cmd));

			return resourceBuilder.build();
		}

		/**
		 * Handle.
		 *
		 * @param res
		 *            the res
		 * @return the resource
		 */
		public static Resource handle(ResourceHandler res) {

			final Resource.Builder resourceBuilder = Resource.builder();

			resourceBuilder.path(resolvePattern(res.getCommand()));

			resolveHttpMethod(res.getTypes()).forEach(method -> build(resourceBuilder, method, res));

			return resourceBuilder.build();
		}

		/**
		 * Builds the.
		 *
		 * @param resourceBuilder
		 *            the resource builder
		 * @param method
		 *            the method
		 * @param res
		 *            the res
		 * @return the object
		 */
		private static Object build(Builder resourceBuilder, String method, ResourceHandler res) {

			final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(method);
			return methodBuilder.consumes(DEFAULT_CONSUMES_AND_PRODUCES).produces(DEFAULT_CONSUMES_AND_PRODUCES)
					.handledBy(new Inflector<ContainerRequestContext, Response>() {
						@Override
						public Response apply(ContainerRequestContext containerRequestContext) {
							try {

								JettyRequest request = new JettyRequest(containerRequestContext);
								JettyResponse response = new JettyResponse(request, Response.ok());

								res.doIt(request, response);

								response.end();

								return response.getHttpResponse();
							} catch (IllegalArgumentException e) {
								log.error("Error on routing: ", e);
							}
							return Response.status(HttpStatus.INTERNAL_SERVER_ERROR_500).build();
						}
					});
		}

		/**
		 * Builds the.
		 *
		 * @param resourceBuilder
		 *            the resource builder
		 * @param method
		 *            the method
		 * @param cmd
		 *            the cmd
		 */
		private static void build(Resource.Builder resourceBuilder, String method, CommandData cmd) {

			String[] consumes = DEFAULT_CONSUMES_AND_PRODUCES;
			String[] produces = cmd.getTargetMedias().isPresent() ? cmd.getTargetMedias().get() : DEFAULT_CONSUMES_AND_PRODUCES;

			final ResourceMethod.Builder methodBuilder = resourceBuilder.addMethod(method);
			methodBuilder.consumes(consumes).produces(produces).handledBy(new Inflector<ContainerRequestContext, Response>() {

				@Context
				private HttpServletRequest request;

				@Override
				public Response apply(ContainerRequestContext containerRequestContext) {

					JettyRequest request = new JettyRequest(containerRequestContext);
					JettyResponse response = new JettyResponse(request, Response.ok());

					log.debug("Receive request for command [{}] alias [{}] - tid [{}]", cmd.getMethod().getName(), cmd.getCommand(),
							request.getTransaction().getId());

					try {
						if (request.getTransaction().getStatus().is(TransactionStatus.YIELD)) {

							if (request.getEntity().isPresent() && !StringUtils.isEmpty((String) request.getEntity().get())) {

								Yield yieldData = my(Json.class).fromJSON((String) request.getEntity().get(), Yield.class);
								request.getTransaction().getContext().putAll(yieldData.getContext());
							}

							request.getTransaction().getExecutor().setCaller(this);
							request.getTransaction().getExecutor().resume();

							JettyResponse result = ((JettyResponse) request.getTransaction().getExecutor().consumeResponse());

							// XXX Check this procedure, is not clear.
							return result != null ? result.getHttpResponse() : response.getHttpResponse();
						}

						request.getTransaction().getExecutor().setCaller(this);
						request.getTransaction().getExecutor().doIt(cmd, request, response);

						log.info("Response for command [{}] - tid [{}] - duration [{}ms]", cmd.getCommand(),
								request.getTransaction().getId(), Duration.between(request.getTransaction().getStartDateTime(),
										request.getTransaction().getLastChangeDateTime()).toMillis());

					} catch (IllegalArgumentException | InterruptedException | SerializationException e) {

						log.error("Fail on execute", e);
						throw new InternalServerErrorException();
					}

					JettyResponse result = ((JettyResponse) request.getTransaction().getExecutor().consumeResponse());

					return result.getHttpResponse();

				}
			});

		}

		/**
		 * Resolve http method.
		 *
		 * @param type
		 *            the type
		 * @return the list
		 */
		private static List<String> resolveHttpMethod(Type[] types) {

			return Arrays.asList(types).stream().map(t -> t.asString()).collect(Collectors.toList());
		}

		/**
		 * Resolve pattern.
		 *
		 * @param command
		 *            the command
		 * @return the string
		 */
		private static String resolvePattern(String command) {

			return command.replaceAll("\\*", "{subResources:.*}");
		}

		/**
		 * Resource config.
		 *
		 * @return the resource config
		 */
		public ResourceConfig resourceConfig() {
			return registerResources();
		}
	}

	/** The Constant DEFAULT_ADDRESS. */
	private final static String DEFAULT_ADDRESS = "127.0.0.1";

	/** The Constant SESSION_ID_KEY. */
	protected final static String SESSION_ID_KEY = "jsid";

	/** The Constant HTTP. */
	private final static String HTTP = "http";

	/** The Constant HTTPS. */
	private final static String HTTPS = "https";

	/** The Constant URL_FORMAT. */
	private final static String URL_FORMAT = "%s://%s/";

	/** The resources. */
	private Set<Resource> resources = new HashSet<Resource>();

	/** The name. */
	private String name;

	/** The port. */
	private int port = SERVER_PORT_DEFAULT;

	/** The server engine. */
	private org.eclipse.jetty.server.Server serverEngine;

	/** The use certificates. */
	private boolean useCertificates;

	/** The router. */
	@Inject
	private Router router;

	/**
	 * Instantiates a new jetty server.
	 */
	public JettyServer() {

		this.name = UUID.randomUUID().toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#certificates(boolean)
	 */
	@Override
	public Server certificates(boolean use) {

		this.useCertificates = use;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#getName()
	 */
	@Override
	public String getName() {

		return this.name;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#getRouter()
	 */
	@Override
	public Router getRouter() {

		return this.router;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#name(java.lang.String)
	 */
	@Override
	public Server name(String name) {

		this.name = name;
		return this;
	}

	@Override
	public int port() {

		return this.port;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#port(int)
	 */
	@Override
	public Server port(int port) {

		this.port = port;
		return this;
	}

	/**
	 * Prepare and start server engine.
	 */
	public void prepareAndStartServerEngine() {

		if (port == 0) {

			port = Integer.parseInt(CONFIG.get(SERVER_PORT_KEY, SERVER_PORT_DEFAULT));
		}
		if (!useCertificates) {

			useCertificates = Boolean.valueOf(CONFIG.get(CERTIFICATE_ENABLE, Boolean.FALSE));
		}

		URI baseUri = UriBuilder.fromUri(String.format(URL_FORMAT, useCertificates ? HTTPS : HTTP, DEFAULT_ADDRESS)).port(port).build();
		SslContextFactory sslContextFactory = null;
		if (useCertificates) {
			sslContextFactory = new SslContextFactory();
			sslContextFactory.setKeyStorePath(CONFIG.get(CERTIFICATE_KEYSTORE_KEY, CERTIFICATE_KEYSTORE_PATH));
			sslContextFactory.setKeyStorePassword(CONFIG.get(CERTIFICATE_KEYSTORE_PASSWORD_KEY, CERTIFICATE_KEYSTORE_PASSWORD));
			sslContextFactory.setKeyManagerPassword(CONFIG.get(CERTIFICATE_KEYSTORE_PASSWORD_KEY, CERTIFICATE_KEYSTORE_PASSWORD));
		}

		ResourceConfig resourceConfig = new ResourceConfig().registerResources(resources);

		resourceConfig.register(new ExceptionMapper<RenderableException>() {

			@Override
			public Response toResponse(RenderableException exception) {

				StringBuilder entity = new StringBuilder();
				entity.append("<b>JSpare Error</b> ").append("occurs one error on render Renderable component.");
				entity.append(StringUtils.join(exception.getStackTrace()));
				JettyResponse response = new JettyResponse(Response.ok());
				response.status(Status.INTERNAL_SERVER_ERROR).entity(entity.toString());
				return response.getHttpResponse();
			}
		});

		Optional<ResourceHandler> notFoundException = router.getErrorHanlder(Status.NOT_FOUND);
		if (notFoundException.isPresent()) {
			resourceConfig.register(new ExceptionMapper<NotFoundException>() {

				@Override
				public Response toResponse(NotFoundException exception) {

					JettyResponse response = new JettyResponse(Response.ok());

					notFoundException.get().doIt(null, response);
					return response.getHttpResponse();
				}
			});
		}

		Optional<ResourceHandler> serverException = router.getErrorHanlder(Status.INTERNAL_SERVER_ERROR);
		if (serverException.isPresent()) {
			resourceConfig.register(new ExceptionMapper<ServerErrorException>() {

				@Override
				public Response toResponse(ServerErrorException exception) {

					JettyResponse response = new JettyResponse(Response.ok());

					serverException.get().doIt(null, response);
					return response.getHttpResponse();
				}
			});
			resourceConfig.register(new ExceptionMapper<InternalServerErrorException>() {

				@Override
				public Response toResponse(InternalServerErrorException exception) {

					JettyResponse response = new JettyResponse(Response.ok());

					serverException.get().doIt(null, response);
					return response.getHttpResponse();
				}
			});
			resourceConfig.register(new ExceptionMapper<InvocationTargetException>() {

				@Override
				public Response toResponse(InvocationTargetException exception) {

					JettyResponse response = new JettyResponse(Response.ok());

					serverException.get().doIt(null, response);
					return response.getHttpResponse();
				}
			});
		}

		serverEngine = JettyServerEngineFactory.createServer(baseUri, resourceConfig, Optional.ofNullable(sslContextFactory), true);

		for (Connector y : serverEngine.getConnectors())
			for (ConnectionFactory x : y.getConnectionFactories())
				if (x instanceof HttpConnectionFactory) {
					((HttpConnectionFactory) x).getHttpConfiguration().setSendServerVersion(false);
					((HttpConnectionFactory) x).getHttpConfiguration().setSendDateHeader(false);
				}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#router(org.jspare.server.Router)
	 */
	@Override
	public Server router(Router router) {

		this.router = router;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#start()
	 */
	@Override
	public void start() {

		log.info("Initializing JSpare Server with ID: [{}]", name);

		remap();

		prepareAndStartServerEngine();

		log.info("JSpare Server is listening port {}", port);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#startOn(int)
	 */
	@Override
	public void startOn(int port) {

		this.port(port).start();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#stop()
	 */
	@Override
	public void stop() {

		serverEngine.destroy();
	}

	/**
	 * Remap.
	 */
	private void remap() {
		log.info("Initializing mapping routes");

		log.info("Mapping commands");
		router.getMappings().forEach(cmd -> this.resources.add(ResourceWrapper.handle(cmd)));

		log.info("Mapping resource handlers");
		router.getResourceHandlers().forEach(res -> this.resources.add(ResourceWrapper.handle(res)));

		// Set default impl for Jetty Server
		org.eclipse.jetty.util.log.Log.setLog(new org.eclipse.jetty.util.log.StdErrLog());

		log.info("Mapping routes done");
	}
}