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
package org.jspare.ui.i18n;

import static org.jspare.core.container.Environment.my;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class I18nTest.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class I18nTest {

	/** The Constant MESSAGE_LOCATION. */
	private final static String MESSAGE_LOCATION = "i18n/MessagesBundle";

	/**
	 * Test i18n.
	 */
	@Test
	public void testI18n() {

		String message = my(I18n.class).get(MESSAGE_LOCATION, "hello", new Locale("pt", "BR"));
		Assert.assertEquals("Ola Mundo, estamos usando JSpare Framework", message);

		message = my(I18n.class).get(MESSAGE_LOCATION, "hello", Locale.US);
		Assert.assertEquals("Hello world, we are using JSpare Framework", message);

	}
}