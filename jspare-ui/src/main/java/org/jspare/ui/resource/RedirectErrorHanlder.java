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
package org.jspare.ui.resource;

import org.jspare.server.Request;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.mapping.Type;

import lombok.AllArgsConstructor;

/**
 * Instantiates a new page error hanlder.
 *
 * @param status
 *            the status
 * @param route
 *            the route
 */

/**
 * Instantiates a new redirect error hanlder.
 *
 * @param route
 *            the route
 */
@AllArgsConstructor
public class RedirectErrorHanlder implements ResourceHandler {

	/** The route. */
	private final String route;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.handler.ResourceHandler#doIt(org.jspare.server.Request,
	 * org.jspare.server.Response)
	 */
	@Override
	public void doIt(Request request, org.jspare.server.Response response) {

		response.redirect(route);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.handler.ResourceHandler#getCommand()
	 */
	@Override
	public String getCommand() {

		return route;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.handler.ResourceHandler#getType()
	 */
	@Override
	public Type getType() {
		return Type.ANY;
	}
}
