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
package org.jspare.ui.controller;

import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;

import org.jspare.server.controller.Controller;
import org.jspare.ui.i18n.I18n;
import org.jspare.ui.view.View;

/**
 * The Class UIController.
 *
 * @author pflima
 * @since 22/04/2016
 */
public abstract class UIController extends Controller {

	/** The i18n. */
	protected I18n i18n;

	/** The view. */
	protected View view;

	/**
	 * Instantiates a new UI controller.
	 */
	public UIController() {

		i18n = my(I18n.class);
		view = factory(View.class);
	}

	/**
	 * I18n.
	 *
	 * @param key
	 *            the key
	 * @return the string
	 */
	protected String i18n(String key) {

		return i18n.get(key, request.getSessionContext().getLanguage());
	}
}
