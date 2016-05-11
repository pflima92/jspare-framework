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
import static org.jspare.core.context.ApplicationContext.ALL_SCAN_QUOTE;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.container.AbstractBootstrap;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.InfraException;
import org.jspare.server.Router;
import org.jspare.server.Server;
import org.jspare.server.ServerFactory;
import org.jspare.server.context.ServerApplicationContext;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ServerBoostrap extends AbstractBootstrap {

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	@Getter
	protected static ServerApplicationContext applicationContext = my(ServerApplicationContext.class);

	/** The controller package. */
	private final String CONTROLLER_PACKAGE = ".controller";

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */

	/**
	 * Gets the server.
	 *
	 * @return the server
	 */
	@Getter
	private Server server;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.container.AbstractBootstrap#load()
	 */
	@Override
	public AbstractBootstrap load() {

		log.info("Starting bootstrap");
		try {

			if (loadApplicationContext) {

				log.info("Loading default Application Context");
				if (StringUtils.isEmpty(customApplicationContext)) {
					getApplicationContext().load();
				} else {
					getApplicationContext().load(customApplicationContext);
				}
			}

			if (loadBundles) {

				log.info("Instantiate and Registry Bundles packages");
				instantiateAndRegistry();
			}

			loadBundle(this::mySupport);

			server = my(ServerFactory.class).createServer();

			String defaultControllerPackage = getClass().getPackage().getName().concat(CONTROLLER_PACKAGE).concat(ALL_SCAN_QUOTE);
			getApplicationContext().getContext().addController(defaultControllerPackage);

			my(ServerApplicationContext.class).loadControllers(server.getRouter());

			return this;
		} catch (Exception e) {

			throw new EnvironmentException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.container.AbstractBootstrap#start()
	 */
	@Override
	public void start() throws InfraException {

		super.start();

		getServer().start();
	}

	/**
	 * Gets the router.
	 *
	 * @return the router
	 */
	protected Router getRouter() {

		return server.getRouter();
	}
}