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
package org.jspare.server;

import org.jspare.core.container.Component;
import org.jspare.server.router.Router;

/**
 * The Interface Server.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface Server {

	/**
	 * Certificates.
	 *
	 * @param use
	 *            the use
	 * @return the server
	 */
	Server certificates(boolean use);

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	String getName();

	/**
	 * Gets the router.
	 *
	 * @return the router
	 */
	Router getRouter();

	/**
	 * Name.
	 *
	 * @param name
	 *            the name
	 * @return the server
	 */
	Server name(String name);

	int port();

	/**
	 * Port.
	 *
	 * @param port
	 *            the port
	 * @return the server
	 */
	Server port(int port);

	/**
	 * Router.
	 *
	 * @param router
	 *            the router
	 * @return the server
	 */
	Server router(Router router);

	/**
	 * Listen server with default port
	 */
	void start();

	/**
	 * Listen server with custom port
	 */
	void startOn(int port);

	/**
	 * Stop listen server
	 */
	void stop();
}