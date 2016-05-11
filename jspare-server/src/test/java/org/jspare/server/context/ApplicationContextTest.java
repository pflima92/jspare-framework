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
package org.jspare.server.context;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.registryComponent;
import static org.junit.Assert.assertEquals;

import org.jspare.core.exception.InfraException;
import org.junit.Before;
import org.junit.Test;

/**
 * The Class ConfigurationTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class ApplicationContextTest {

	/**
	 * Load default application context.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void loadDefaultApplicationContext() throws InfraException {

		my(ServerApplicationContext.class).load();

		ServerContext context = my(ServerApplicationContext.class).getContext();

		assertEquals("1.0.0", context.getVersion());
	}

	/**
	 * Setup.
	 */
	@Before
	public void setup() {

		registryComponent(ServerApplicationContextImpl.class);
	}

}