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

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;
import static org.junit.Assert.assertEquals;

import org.jspare.core.container.Environment;
import org.jspare.server.basic.BasicHolder;
import org.jspare.server.basic.BasicServer;
import org.jspare.server.basic.BasicServerFactory;
import org.jspare.server.exception.RegistryServerException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class ServerTest.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ServerTest {

	/** The server factory. */
	private ServerFactory serverFactory;

	/** The holder. */
	private Holder holder;

	/**
	 * After.
	 */
	@After
	public void after() {

		Environment.release();
	}

	/**
	 * Registry server test.
	 */
	@Test(expected = RegistryServerException.class)
	public void registryServerTest() {

		BasicServer server = (BasicServer) serverFactory.createServer();
		assertEquals(false, server.isUseCertificates());

		server.certificates(true);
		assertEquals(true, server.isUseCertificates());

		BasicServer sameServer = (BasicServer) holder.getServer(Server.DEFAULT_PORT).get();
		assertEquals(true, sameServer.isUseCertificates());

		// Test Environment Exception
		serverFactory.createServer(Server.DEFAULT_PORT);
	}

	/**
	 * Setup.
	 */
	@Before
	public void setup() {

		registryComponent(BasicServerFactory.class);
		registryComponent(BasicHolder.class);

		serverFactory = my(ServerFactory.class);
		holder = my(Holder.class);
	}

}