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
package org.jspare.web.sample.service;

import java.util.Arrays;
import java.util.List;

import org.jspare.web.sample.model.User;

public class UserServiceImpl implements UserService {

	@Override
	public boolean saveUser(User user) {

		return user != null;
	}

	@Override
	public User findUserById(int id) {

		User user = new User(id, String.format("User %s", id));
		return user;
	}

	@Override
	public List<User> listUsers() {

		List<User> users = Arrays.asList(new User(1, "User 1"), new User(2, "User 2"));
		return users;
	}
}