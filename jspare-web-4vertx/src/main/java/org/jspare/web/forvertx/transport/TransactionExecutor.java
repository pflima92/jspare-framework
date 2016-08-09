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
package org.jspare.web.forvertx.transport;

import org.jspare.core.container.Component;
import org.jspare.core.container.Scope;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.controller.CommandData;
import org.jspare.server.exception.NoSuchCallerException;

/**
 * The Interface TransactionExecutor.
 *
 * Responsible to execute a single request.
 *
 * @author pflima
 * @since 08/05/2016
 */
@Component(scope = Scope.FACTORY)
public interface TransactionExecutor {

	/**
	 * Consume response.
	 *
	 * @return the response
	 */
	Response consumeResponse();

	/**
	 * Do it.
	 *
	 * @param caller
	 *            the caller
	 * @param cmd
	 *            the cmd
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	void doIt(CommandData cmd, Request request, Response response) throws InterruptedException;

	/**
	 * Checks for response.
	 *
	 * @return true, if successful
	 */
	boolean hasResponse();

	/**
	 * Notify finish.
	 *
	 * @param the
	 *            response
	 *
	 * @throws NoSuchCallerException
	 *             the no such caller exception
	 */
	void notifyFinish(Response response) throws NoSuchCallerException;

	/**
	 * Resume.
	 *
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	void resume() throws InterruptedException;

	/**
	 * Sets the caller.
	 *
	 * @param caller
	 *            the new caller
	 */
	void setCaller(Object caller);

	/**
	 * Yield.
	 *
	 * @param the
	 *            response
	 *
	 */
	void yield(Request request, Response response, String bind);
}