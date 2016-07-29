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
package org.jspare.server.container;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.scanner.ComponentScanner.ALL_SCAN_QUOTE;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_KEY;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_PASSWORD_KEY;
import static org.jspare.server.commons.ServerDefinitions.SERVER_PORT_KEY;
import static org.jspare.server.commons.ServerDefinitions.YIELD_ENABLE_KEY;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jspare.core.container.ApplicationBuilder;
import org.jspare.core.exception.NotImplementedException;
import org.jspare.core.scanner.ComponentScanner;
import org.jspare.server.Router;
import org.jspare.server.Server;
import org.jspare.server.handler.ResourceHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ApplicationServerBuilder extends ApplicationBuilder {

	/** The controller package. */
	private final String CONTROLLER_PACKAGE = ".controller";

	private final Object source;

	/** The controllers. */
	private List<String> controllers;

	private List<Class<? extends ResourceHandler>> resourceHanldersClazz;

	private List<ResourceHandler> resourceHanlders;

	public ApplicationServerBuilder(Object source) {

		this.source = source;
		controllers = new ArrayList<>();
		resourceHanlders = new ArrayList<>();
		resourceHanldersClazz = new ArrayList<>();
	}

	public static ApplicationServerBuilder create(Object source) {

		return new ApplicationServerBuilder(source);
	}

	@Override
	public void build() {

		super.build();

		Server server = my(Server.class);
		Router router = server.getRouter();

		String defaultControllerPackage = source.getClass().getPackage().getName().concat(CONTROLLER_PACKAGE).concat(ALL_SCAN_QUOTE);
		controllers.add(defaultControllerPackage);

		controllers.forEach(c -> {

			try {

				if (!c.endsWith(ALL_SCAN_QUOTE)) {

					Class<?> controllerClazz = Class.forName(c);
					router.addMapping(controllerClazz);
				}

				my(ComponentScanner.class).scanAndExecute(c, (clazzName) -> {

					try {

						Class<?> controllerClazz = Class.forName((String) clazzName[0]);

						if (router.isValidCommand(controllerClazz)) {

							router.addMapping(controllerClazz);
						}
					} catch (Exception e) {

						log.error(e.getMessage(), e);
					}
					return Void.TYPE;
				});

			} catch (Exception e) {
				log.error("Error on load controller: [{}]", c);
				log.error(e.getMessage(), e);
			}
		});

		resourceHanlders.forEach(router::addResourceHandler);
		resourceHanldersClazz.forEach(router::addResourceHandler);

	}

	public ApplicationServerBuilder port(int port) {
		putConfig(SERVER_PORT_KEY, String.valueOf(port));
		return this;
	}

	public ApplicationServerBuilder remotePort(int port) {

		throw new NotImplementedException();
	}

	public ApplicationServerBuilder certificate(String keystorePath, String keystorePassword) {
		putConfig(CERTIFICATE_KEYSTORE_KEY, keystorePath);
		putConfig(CERTIFICATE_KEYSTORE_PASSWORD_KEY, keystorePassword);
		return this;
	}

	public ApplicationServerBuilder yield(boolean enabled) {
		putConfig(YIELD_ENABLE_KEY, String.valueOf(enabled));
		return this;
	}

	public ApplicationServerBuilder resourceHandlers(ResourceHandler... handlers) {
		resourceHanlders.addAll(Arrays.asList(handlers));
		return this;
	}

	public ApplicationServerBuilder resourceHandlers(Class<? extends ResourceHandler> handler) {
		resourceHanldersClazz.add(handler);
		return this;
	}

	public ApplicationBuilder controllers(String... optControllers) {

		controllers.addAll(Arrays.asList(optControllers));
		return this;
	}

	public ApplicationBuilder controllers(Class<?>... clazzControllers) {

		Arrays.asList(clazzControllers).forEach(clazz -> controllers.add(clazz.getCanonicalName()));
		return this;
	}
}