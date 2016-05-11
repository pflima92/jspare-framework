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

import org.jspare.core.container.Inject;
import org.jspare.server.Holder;
import org.jspare.server.Router;
import org.jspare.server.Server;
import org.jspare.server.ServerFactory;

/**
 * A factory for creating JettyServer objects.
 */
public class JettyServerFactory implements ServerFactory {

	/** The Constant SERVER_PORT_KEY. */
	private final static String SERVER_PORT_KEY = "server.default.port";

	/** The Constant SERVER_REMOTE_KEY. */
	private final static String SERVER_REMOTE_KEY = "server.remote.port";

	/** The holder. */
	@Inject
	private Holder holder;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.ServerFactory#createServer()
	 */
	@Override
	public Server createServer() {

		return createServer(Integer.parseInt(CONFIG.get(SERVER_PORT_KEY, Server.DEFAULT_PORT)));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.ServerFactory#createServer(int)
	 */
	@Override
	public Server createServer(int port) {

		Router router = new JettyRouter();
		Server server = new JettyServer().port(port).router(router);
		return holder.registryServer(server, port);
	}
}