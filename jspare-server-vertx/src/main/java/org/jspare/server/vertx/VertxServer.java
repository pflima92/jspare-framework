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
package org.jspare.server.vertx;

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_ENABLE;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_KEY;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_PASSWORD;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_PASSWORD_KEY;
import static org.jspare.server.commons.ServerDefinitions.CERTIFICATE_KEYSTORE_PATH;
import static org.jspare.server.commons.ServerDefinitions.SERVER_PORT_DEFAULT;
import static org.jspare.server.commons.ServerDefinitions.SERVER_PORT_KEY;

import java.time.Duration;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.container.Inject;
import org.jspare.core.serializer.Json;
import org.jspare.server.Server;
import org.jspare.server.controller.CommandData;
import org.jspare.server.resource.ResourceFactory;
import org.jspare.server.router.Router;
import org.jspare.server.transaction.TransactionStatus;
import org.jspare.server.transaction.model.Yield;
import org.jspare.server.transport.Status;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerOptions;
import io.vertx.core.net.JksOptions;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxServer implements Server {

	/** The port. */
	private int port = SERVER_PORT_DEFAULT;

	/** The use certificates. */
	private boolean useCertificates;

	/** The router. */
	@Inject
	@Getter
	private Router router;

	private io.vertx.ext.web.Router resourceConfig;

	private HttpServer serverEngine;

	@Getter
	private String name;

	public VertxServer() {
		this.name = UUID.randomUUID().toString();
	}

	@Override
	public Server certificates(boolean useCertificates) {
		this.useCertificates = useCertificates;
		return this;
	}

	@Override
	public Server name(String name) {
		this.name = name;
		return this;
	}

	@Override
	public int port() {
		return this.port;
	}

	@Override
	public Server port(int port) {
		this.port = port;
		return this;
	}

	@Override
	public Server router(Router router) {
		this.router = router;
		return this;
	}

	@Override
	public void start() {

		log.info("Initializing JSpare Server with ID: [{}]", name);

		initializeVertx();

		remap();

		prepareAndStartServerEngine();

		log.info("JSpare Server is listening port {}", port);
	}

	private void initializeVertx() {

		if (port == 0) {

			port = Integer.parseInt(CONFIG.get(SERVER_PORT_KEY, SERVER_PORT_DEFAULT));
		}
		if (!useCertificates) {

			useCertificates = Boolean.valueOf(CONFIG.get(CERTIFICATE_ENABLE, Boolean.FALSE));
		}

		Vertx vertx = Vertx.vertx(new VertxOptions());
		resourceConfig = io.vertx.ext.web.Router.router(vertx);

		HttpServerOptions httpServerOptions = new HttpServerOptions();
		httpServerOptions.setTcpKeepAlive(true);
		httpServerOptions.setReuseAddress(true);
		if (useCertificates) {

			httpServerOptions.setSsl(true).setKeyStoreOptions(new JksOptions().setPath(CONFIG.get(CERTIFICATE_KEYSTORE_KEY, CERTIFICATE_KEYSTORE_PATH))
					.setPassword(CONFIG.get(CERTIFICATE_KEYSTORE_PASSWORD_KEY, CERTIFICATE_KEYSTORE_PASSWORD)));
		}
		serverEngine = vertx.createHttpServer(httpServerOptions);
	}

	@Override
	public void startOn(int port) {

		this.port(port).start();
	}

	@Override
	public void stop() {

	}

	private void prepareAndStartServerEngine() {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	private void remap() {

		log.info("Initializing mapping routes");

		resourceConfig.route().handler(BodyHandler.create().setBodyLimit(-1));
		serverEngine.requestHandler(resourceConfig::accept);

		log.info("Mapping commands");
		router.getMappings().forEach(m -> ResourceWrapper.handle(resourceConfig, m));

		log.info("Mapping resources");
		router.getResources().forEach(res -> resourceConfig.route().handler((Handler<RoutingContext>) my(ResourceFactory.class).create(res)));

		log.info("Mapping routes done");
	}

	static class ResourceWrapper {

		public static void handle(io.vertx.ext.web.Router resourceConfig, CommandData cmd) {

			resourceConfig.route().handler(new Handler<RoutingContext>() {

				@Override
				public void handle(RoutingContext context) {

					VertxRequest request = new VertxRequest(context);
					VertxResponse response = new VertxResponse(request);

					log.debug("Receive request for command [{}] alias [{}] - tid [{}]", cmd.getMethod().getName(), cmd.getCommand(),
							request.getTransaction().getId());
					
					try {
						
						if (request.getTransaction().getStatus().is(TransactionStatus.YIELD)) {

							if (request.getEntity().hasValue() && !StringUtils.isEmpty(request.getEntity().asString())) {

								Yield yieldData = my(Json.class).fromJSON(request.getEntity().asString(), Yield.class);
								request.getTransaction().getContext().putAll(yieldData.getContext());
							}

							request.getTransaction().getExecutor().setCaller(this);
							request.getTransaction().getExecutor().resume();
							
							VertxResponse result = ((VertxResponse) request.getTransaction().getExecutor().consumeResponse());
							if (result != null) {
								
								result.end();
								return;
							}
							response.end();
						}
						
						request.getTransaction().getExecutor().setCaller(this);
						request.getTransaction().getExecutor().doIt(cmd, request, response);

						log.info("Response for command [{}] - tid [{}] - duration [{}ms]", cmd.getCommand(),
								request.getTransaction().getId(), Duration.between(request.getTransaction().getStartDateTime(),
										request.getTransaction().getLastChangeDateTime()).toMillis());
						
					} catch (InterruptedException e) {
						
						response.entity(e).status(Status.INTERNAL_SERVER_ERROR).end();
					}
					
					((VertxResponse) request.getTransaction().getExecutor().consumeResponse()).end();
				}
			});
		}
	}
}