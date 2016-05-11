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
package org.jspare.server.jetty.filter;

import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.FilterException;
import org.jspare.server.filter.Filter;

/**
 * The Class CrossOriginFilter.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class CrossOriginFilter implements Filter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.filter.Filter#doIt(org.jspare.server.Request,
	 * org.jspare.server.Response)
	 */
	@Override
	public void doIt(Request request, Response response) throws FilterException {

		response.getHeaders().add("Access-Control-Allow-Origin", "*");
		response.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		response.getHeaders().add("Access-Control-Allow-Credentials", "true");
		response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}

}
