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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspare.server.Holder;
import org.jspare.server.Server;
import org.jspare.server.exception.RegistryServerException;

/**
 * The Class BasicHolder.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class BasicHolder implements Holder {

	/** The Constant servers. */
	private final static Map<Integer, Server> servers = new HashMap<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Holder#activePorts()
	 */
	@Override
	public List<Integer> activePorts() {

		return servers.keySet().stream().collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Holder#getServer(int)
	 */
	@Override
	public Optional<Server> getServer(int port) {

		return Optional.ofNullable(servers.get(port));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Holder#registryServer(org.jspare.server.Server,
	 * int)
	 */
	@Override
	public Server registryServer(Server server, int port) {

		if (servers.containsKey(port)) {

			throw new RegistryServerException("Already contains a registered server in the given port.");
		}

		server.port(port);
		servers.put(port, server);
		return server;
	}
}