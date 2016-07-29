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
package org.jspare.web.sample.controller;

import org.jspare.server.mapping.Mapping;
import org.jspare.server.mapping.Method;
import org.jspare.server.mapping.Type;
import org.jspare.ui.controller.UIController;

public class FormController extends UIController {

	@Mapping("/form/view")
	public void view() {

		success(view.route("form"));
	}

	@Method(Type.POST)
	@Mapping(value = "/form/view")
	public void receive() {
		String name = request.getParameter("name");
		String message = String.format("Hello %s!", name);
		success(message);
	}
}
