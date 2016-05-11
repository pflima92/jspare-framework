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
package org.jspare.ui.view;

import java.util.Map;

import org.jspare.core.container.Component;
import org.jspare.core.container.Scope;
import org.jspare.server.Renderable;

/**
 * The Interface View.
 *
 * @author pflima
 * @since 22/04/2016
 */
@Component(scope = Scope.FACTORY)
public interface View extends Renderable {

	/**
	 * Adds the.
	 *
	 * @param values
	 *            the values
	 * @return the view
	 */
	View add(Map<String, Object> values);

	/**
	 * Adds the.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the view
	 */
	View add(String key, Object value);

	/**
	 * Content.
	 *
	 * @param content
	 *            the content
	 * @return the view
	 */
	View content(String content);

	/**
	 * Removes the.
	 *
	 * @param key
	 *            the key
	 * @return the view
	 */
	View remove(String key);

	/**
	 * Route.
	 *
	 * @param route
	 *            the route
	 * @return the view
	 */
	View route(String route);
}