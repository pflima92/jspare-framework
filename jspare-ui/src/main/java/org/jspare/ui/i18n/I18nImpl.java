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

import static org.jspare.core.container.Environment.CONFIG;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class I18nImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */

@Slf4j
public class I18nImpl implements I18n {

	/** The Constant I18N_ROOT_FOLDER_KEY. */
	private final static String I18N_ROOT_FOLDER_KEY = "ui.i18n.root";

	/** The Constant I18N_ROOT_FOLDER. */
	private final static String I18N_ROOT_FOLDER = "i18n/app";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.i18n.I18n#get(java.lang.String, java.util.Locale)
	 */
	@Override
	public String get(String key, Locale locale) {

		return get(CONFIG.get(I18N_ROOT_FOLDER_KEY, I18N_ROOT_FOLDER), key, locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.i18n.I18n#get(java.lang.String, java.lang.String,
	 * java.util.Locale)
	 */
	@Override
	public String get(String resource, String key, Locale locale) {

		return values(resource, locale).get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.i18n.I18n#values(java.util.Locale)
	 */
	@Override
	public Map<String, String> values(Locale locale) {

		return values(CONFIG.get(I18N_ROOT_FOLDER_KEY, I18N_ROOT_FOLDER), locale);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.i18n.I18n#values(java.lang.String, java.util.Locale)
	 */
	@Override
	public Map<String, String> values(String resource, Locale locale) {

		Map<String, String> values = new HashMap<>();
		try {

			ResourceBundle resourceBundle = ResourceBundle.getBundle(resource, locale);
			Enumeration<String> keys = resourceBundle.getKeys();
			while (keys.hasMoreElements()) {
				String key = keys.nextElement();
				values.put(key, resourceBundle.getString(key));
			}
		} catch (MissingResourceException e) {
			log.warn("i18n not found for resource [{}]", resource);
		}
		return values;
	}

}