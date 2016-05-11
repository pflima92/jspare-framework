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
package org.jspare.server.controller;

import java.util.Map;

import org.jspare.core.exception.NotImplementedException;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.session.SessionContext;

import lombok.Setter;

/**
 * The Class Controller.
 *
 * @author pflima
 * @since 22/04/2016
 */
public abstract class Controller {

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */
	@Setter
	protected Request request;

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	@Setter
	protected Response response;

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	protected Map<String, Object> getContext() {

		return request.getTransaction().getContext();
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	protected SessionContext getSession() {

		return request.getSessionContext();
	}

	/**
	 * Yeld.
	 */
	protected void yield() {

		throw new NotImplementedException();
	}

	/**
	 * Yield.
	 *
	 * @param bind
	 *            the bind
	 */
	protected void yield(String bind) {

		request.getTransaction().getExecutor().yield(request, response, bind);
	}
}