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
package org.jspare.server.filter;

import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.FilterException;

/**
 * The Interface Filter.
 *
 * @author pflima
 * @since 30/03/2016
 */
@FunctionalInterface
public interface Filter {

	/**
	 * Do it.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @throws FilterException
	 *             the filter exception
	 */
	void doIt(Request request, Response response) throws FilterException;
}