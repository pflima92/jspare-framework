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

	/** The assets root folder key. */
	String ASSETS_ROOT_FOLDER_KEY = "ui.root.assets";

	/** The assets root folder. */
	String ASSETS_ROOT_FOLDER = "public/assets/";

	/** The images root folder key. */
	String IMAGES_ROOT_FOLDER_KEY = "ui.root.images";

	/** The images root folder. */
	String IMAGES_ROOT_FOLDER = "images/";

	/** The script root folder key. */
	String SCRIPT_ROOT_FOLDER_KEY = "ui.root.js";

	/** The script root folder. */
	String SCRIPT_ROOT_FOLDER = "js/";

	/** The styles root folder key. */
	String STYLES_ROOT_FOLDER_KEY = "ui.root.css";

	/** The styles root folder. */
	String STYLES_ROOT_FOLDER = "css/";

	/** The Constant ROOT_FILE_KEY. */
	String ROOT_FILE_KEY = "ui.root.file";

	/** The Constant ROOT_FILE. */
	String ROOT_FILE = "pages/";

	/** The Constant SUFIX_FILE_KEY. */
	String SUFIX_FILE_KEY = "ui.sufix.file";

	/** The Constant SUFIX_FILE. */
	String SUFIX_PAGE_FILE = ".html";

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