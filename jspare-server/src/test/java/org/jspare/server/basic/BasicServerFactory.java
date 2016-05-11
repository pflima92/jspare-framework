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
package org.jspare.server.basic;

import org.jspare.core.container.Inject;
import org.jspare.server.Holder;
import org.jspare.server.Server;
import org.jspare.server.ServerFactory;

/**
 * A factory for creating BasicServer objects.
 */
public class BasicServerFactory implements ServerFactory {

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

		return createServer(Server.DEFAULT_PORT);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.ServerFactory#createServer(int)
	 */
	@Override
	public Server createServer(int port) {

		Server server = new BasicServer();
		server.port(port);

		return holder.registryServer(server, port);
	}
}