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

import static org.jspare.core.container.Environment.CONFIG;

import org.jspare.server.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new application model.
 *
 * @param request
 *            the request
 */
@AllArgsConstructor
public class ApplicationModel {

	/** The Constant SEPARATOR. */
	private static final String SEPARATOR = "/";

	/** The request. */
	private final Request request;

	/**
	 * Asset.
	 *
	 * @param src
	 *            the src
	 * @return the string
	 */
	public String asset(String src) {

		return getPath() + CONFIG.get(UiToolkit.ASSETS_ROOT_FOLDER_KEY, UiToolkit.ASSETS_ROOT_FOLDER) + src;
	}

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	private String getPath() {
		String path = request.getBasePath();
		if (!path.endsWith(SEPARATOR)) {

			path += SEPARATOR;
		}
		return path;
	}

	/**
	 * Command.
	 *
	 * @param name
	 *            the name
	 * @return the string
	 */
	public String command(String name) {

		return getPath() + name;
	}

	/**
	 * Image.
	 *
	 * @param src
	 *            the src
	 * @return the string
	 */
	public String image(String src) {

		return asset(CONFIG.get(UiToolkit.IMAGES_ROOT_FOLDER_KEY, UiToolkit.IMAGES_ROOT_FOLDER)) + src;
	}

	/**
	 * Script.
	 *
	 * @param src
	 *            the src
	 * @return the string
	 */
	public String script(String src) {

		return asset(CONFIG.get(UiToolkit.SCRIPT_ROOT_FOLDER_KEY, UiToolkit.SCRIPT_ROOT_FOLDER)) + src;
	}

	/**
	 * Style.
	 *
	 * @param src
	 *            the src
	 * @return the string
	 */
	public String style(String src) {

		return asset(CONFIG.get(UiToolkit.STYLES_ROOT_FOLDER_KEY, UiToolkit.STYLES_ROOT_FOLDER)) + src;
	}

}