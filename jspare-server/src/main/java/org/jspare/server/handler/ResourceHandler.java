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

package org.jspare.server.handler;

import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.mapping.Type;

/**
 * The Interface ResourceHandler.
 *
 * @author pflima
 * @since 30/03/2016
 */
public interface ResourceHandler {

	/**
	 * Do it.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	void doIt(Request request, Response response);

	/**
	 * Gets the command value.
	 *
	 * @return the command value
	 */
	String getCommand();

	/**
	 * Gets the command type.
	 *
	 * @return the command type
	 */
	Type getType();
}
