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
package org.jspare.server.jetty.handler;

import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.mapping.Type;

/**
 * The Class DefaultNotFoundErrorHandler.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class DefaultNotFoundErrorHandler implements ResourceHandler {

	/** The command value. */
	private final String COMMAND_VALUE = "/error/404";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jspare.server.handler.ResourceHandler#doIt(org.jspare.server.Request,
	 * org.jspare.server.Response)
	 */
	@Override
	public void doIt(Request request, Response response) {

		StringBuilder builder = new StringBuilder();
		builder.append("<html><body>");
		builder.append("<h1>404 - Page Not Found</h1>");
		builder.append("</body></html>");

		response.entity(builder.toString()).notFound();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.handler.ResourceHandler#getCommandType()
	 */
	@Override
	public Type getType() {

		return Type.ANY;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.handler.ResourceHandler#getCommandValue()
	 */
	@Override
	public String getCommand() {

		return COMMAND_VALUE;
	}

}