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

import java.util.List;
import java.util.Optional;

import org.jspare.core.container.Component;

/**
 * The Interface Holder.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component
public interface Holder {

	/**
	 * Active ports.
	 *
	 * @return the list
	 */
	List<Integer> activePorts();

	/**
	 * Gets the server.
	 *
	 * @param port
	 *            the port
	 * @return the server
	 */
	Optional<Server> getServer(int port);

	/**
	 * Registry server.
	 *
	 * @param server
	 *            the server
	 * @param port
	 *            the port
	 * @return the server
	 */
	Server registryServer(Server server, int port);
}
