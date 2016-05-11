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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jspare.server.Holder;
import org.jspare.server.Server;
import org.jspare.server.exception.RegistryServerException;

/**
 * The Class JettyHolder.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class JettyHolder implements Holder {

	/** The Constant CERTIFICATE_KEYSTORE_KEY. */
	public static final String CERTIFICATE_KEYSTORE_KEY = "certificate.keystore.path";

	/** The Constant CERTIFICATE_KEYSTORE_PATH. */
	public static final String CERTIFICATE_KEYSTORE_PATH = String.format("%s%s%s", "certificate", File.separator, "vertx_keystore.jks");

	/** The Constant CERTIFICATE_KEYSTORE_PASSWORD_KEY. */
	public static final String CERTIFICATE_KEYSTORE_PASSWORD_KEY = "certificate.keystore.password";

	/** The Constant CERTIFICATE_KEYSTORE_PASSWORD. */
	public static final String CERTIFICATE_KEYSTORE_PASSWORD = "password";

	/** The jetty servers. */
	private Map<Integer, Server> jettyServers = new HashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.Holder#activePorts()
	 */
	@Override
	public List<Integer> activePorts() {

		return jettyServers.keySet().stream().collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.Holder#getServer(int)
	 */
	@Override
	public Optional<Server> getServer(int port) {

		return Optional.ofNullable(jettyServers.get(port));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.Holder#registryServer(org.jspare.server.Server,
	 * int)
	 */
	@Override
	public Server registryServer(Server server, int port) {

		if (jettyServers.containsKey(port)) {

			throw new RegistryServerException("Already contains a registered server in the given port.");
		}
		server.port(port);

		jettyServers.put(port, server);
		return server;
	}
}