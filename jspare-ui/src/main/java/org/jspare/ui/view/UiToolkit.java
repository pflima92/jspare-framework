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
import org.jspare.server.exception.LoadTemplateException;
import org.jspare.server.exception.RenderableException;

/**
 * The Interface UiToolkit.
 *
 * @author pflima
 * @since 22/04/2016
 */
@Component
public interface UiToolkit {

	/**
	 * Compile.
	 *
	 * @param template
	 *            the template
	 * @param viewValues
	 *            the view values
	 * @return the string
	 * @throws RenderableException
	 *             the renderable exception
	 */
	String compile(Template template, Map<String, Object> viewValues) throws RenderableException;

	/**
	 * Convert2 template.
	 *
	 * @param content
	 *            the content
	 * @return the template
	 * @throws LoadTemplateException
	 *             the load template exception
	 */
	Template convert2Template(String content) throws LoadTemplateException;

	/**
	 * Load template.
	 *
	 * @param route
	 *            the route
	 * @return the template
	 * @throws LoadTemplateException
	 *             the load template exception
	 */
	Template loadTemplate(String route) throws LoadTemplateException;
}