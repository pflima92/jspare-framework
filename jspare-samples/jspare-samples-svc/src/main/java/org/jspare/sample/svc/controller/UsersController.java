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
package org.jspare.sample.svc.controller;

import java.util.List;

import org.jspare.core.container.Inject;
import org.jspare.sample.svc.model.User;
import org.jspare.sample.svc.service.UserService;
import org.jspare.server.commons.SimpleResponse;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Mapping;
import org.jspare.server.mapping.Method;
import org.jspare.server.mapping.Namespace;
import org.jspare.server.mapping.Parameter;
import org.jspare.server.mapping.Type;

@Namespace
public class UsersController extends Controller {

	@Inject
	private UserService userService;

	@Mapping
	@Method(Type.POST)
	public void saveUser(User user) {

		if (!userService.saveUser(user)) {

			conflict();
			return;
		}

		success(SimpleResponse.ok());
	}

	@Mapping("{id}")
	public void findUser(@Parameter("id") String id) {

		User user = userService.findUserById(Integer.parseInt(id));
		success(user);
	}

	@Mapping
	public void listUsers() {

		List<User> users = userService.listUsers();
		success(users);
	}
}
