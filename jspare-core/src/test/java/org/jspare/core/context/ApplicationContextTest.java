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
package org.jspare.core.context;

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.context.additional.CmptAutoLoader;
import org.jspare.core.context.additional.CmptAutoLoaderOneImpl;
import org.jspare.core.context.additional.CmptAutoLoaderTwoImpl;
import org.jspare.core.context.additional.more.CmptAutoLoaderThreeImpl;
import org.jspare.core.context.additional.multiple.Multiple;
import org.jspare.core.context.additional.multiple.MultipleImpl;
import org.jspare.core.context.additional.multiple.MultipleOne;
import org.jspare.core.context.additional.multiple.MultipleTwo;
import org.jspare.core.exception.InfraException;
import org.jspare.core.provider.ApplicationProvider;
import org.jspare.core.provider.factory.PropertiesFactory;
import org.junit.Test;

/**
 * The Class ConfigurationTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class ApplicationContextTest {

	/**
	 * Load custom application context.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void loadCustomApplicationContext() throws InfraException {

		my(ApplicationContext.class).load("context/custom.jspare");
		Context context = my(ApplicationContext.class).getContext();

		assertEquals(true, !StringUtils.isEmpty(context.getApplicationName()));
		assertEquals(true, !StringUtils.isEmpty((String) context.getParameter("customParam")));
	}

	/**
	 * Load default application context.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void loadDefaultApplicationContext() throws InfraException {

		my(ApplicationContext.class).load();

		Context context = my(ApplicationContext.class).getContext();

		assertEquals(true, !StringUtils.isEmpty(context.getApplicationName()));
		assertEquals(true, !StringUtils.isEmpty((String) context.getParameter("param1")));
	}

	/**
	 * Test load providers.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void testLoadProviders() throws InfraException {

		my(ApplicationContext.class).load("context/providers.jspare");

		PropertiesFactory propertiesFactory = my(ApplicationProvider.class).getFactory(PropertiesFactory.class);
		Properties properties = propertiesFactory.get("config");
		assertEquals("teste", properties.getProperty("parameter"));

	}

	/**
	 * Test multiple.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void testMultiple() throws InfraException {

		my(ApplicationContext.class).load("context/multiple.jspare");

		Multiple multiple = my(Multiple.class);
		assertTrue(multiple instanceof MultipleImpl);

		Multiple multipleOne = my(Multiple.class, "MultipleOne");
		assertTrue(multipleOne instanceof MultipleOne);

		Multiple multipleTwo = my(Multiple.class, "MultipleTwo");
		assertTrue(multipleTwo instanceof MultipleTwo);
	}

	/**
	 * Test registry components.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	@Test
	public void testRegistryComponents() throws InfraException {

		my(ApplicationContext.class).load("context/autoRegistry.jspare");

		CmptAutoLoader cmptAutoLoader = my(CmptAutoLoader.class);
		assertTrue(cmptAutoLoader instanceof CmptAutoLoaderOneImpl);

		cmptAutoLoader = my(CmptAutoLoader.class, "CmptAutoLoaderTwoImpl");
		assertTrue(cmptAutoLoader instanceof CmptAutoLoaderTwoImpl);

		cmptAutoLoader = my(CmptAutoLoader.class, "CmptAutoLoaderThreeImpl");
		assertTrue(cmptAutoLoader instanceof CmptAutoLoaderThreeImpl);
	}
}