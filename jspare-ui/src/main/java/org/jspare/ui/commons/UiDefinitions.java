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
package org.jspare.ui.commons;

import org.jspare.server.commons.ServerDefinitions;

public interface UiDefinitions extends ServerDefinitions {

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

	/** The Constant CACHE_TEMPLATES. */
	String CACHE_TEMPLATE = "ui.template.cache";

	/** The Constant I18N_ROOT_FOLDER_KEY. */
	String I18N_ROOT_FOLDER_KEY = "ui.i18n.root";

	/** The Constant I18N_ROOT_FOLDER. */
	String I18N_ROOT_FOLDER = "i18n/app";
}
