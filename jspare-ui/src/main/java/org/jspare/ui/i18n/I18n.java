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

import java.util.Locale;
import java.util.Map;

import org.jspare.core.container.Component;

/**
 * @author pflima
 *
 *         Reference: https://docs.oracle.com/javase/tutorial/i18n/intro/
 *
 */
@Component
public interface I18n {

	/**
	 * Gets the.
	 *
	 * @param key
	 *            the key
	 * @param locale
	 *            the locale
	 * @return the string
	 */
	String get(String key, Locale locale);

	/**
	 * Gets the.
	 *
	 * @param resource
	 *            the resource
	 * @param key
	 *            the key
	 * @param locale
	 *            the locale
	 * @return the string
	 */
	String get(String resource, String key, Locale locale);

	/**
	 * Values.
	 *
	 * @param locale
	 *            the locale
	 * @return the map
	 */
	Map<String, String> values(Locale locale);

	/**
	 * Values.
	 *
	 * @param resource
	 *            the resource
	 * @param locale
	 *            the locale
	 * @return the map
	 */
	Map<String, String> values(String resource, Locale locale);
}