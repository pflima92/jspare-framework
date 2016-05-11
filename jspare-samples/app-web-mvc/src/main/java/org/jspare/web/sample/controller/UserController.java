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

import java.util.List;

import org.jspare.core.container.Inject;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Command;
import org.jspare.server.mapping.Parameter;
import org.jspare.web.sample.model.User;
import org.jspare.web.sample.service.UserService;

public class UserController extends Controller {

	@Inject
	private UserService userService;

	@Command
	public void saveUser(User user) {

		if (!userService.saveUser(user)) {

			response.entity("Error on save").businessError();
			return;
		}

		response.success("Saved with success");
	}

	@Command("user/{id}")
	public void findUser(@Parameter("id") String id) {

		User user = userService.findUserById(Integer.parseInt(id));
		response.success(user);
	}

	@Command
	public void listUsers() {

		List<User> users = userService.listUsers();
		response.success(users);
	}

	@Command("users")
	public void users() {

		listUsers();
	}
}
