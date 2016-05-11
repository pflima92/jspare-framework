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

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jspare.core.loader.ResourceLoader;
import org.jspare.server.CacheControl;
import org.jspare.server.CacheControl.CacheAccessor;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.jetty.http.HttpMediaType;
import org.jspare.server.mapping.Type;

/**
 * The Class PublicResourceHandler.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class PublicResourceHandler implements ResourceHandler {

	/** The Constant COMMAND_VALUE. */
	private final static String PUBLIC_RESOURCE_PATH = "/public";

	/** The Constant DEFAULT_SUFIX_PATTERN. */
	private final static String DEFAULT_SUFIX_PATTERN = "/*";

	/** The Constant PUBLIC_RESOURCE_PATH_KEY. */
	private final static String PUBLIC_RESOURCE_PATH_KEY = "server.public.path";

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.handler.ResourceHandler#doIt(org.jspare.server.Request,
	 * org.jspare.server.Response)
	 */
	@Override
	public void doIt(Request request, Response response) {

		String path = request.getPath();

		try {
			InputStream resource = my(ResourceLoader.class).readFileToInputStream(path);

			resolveMediaType(request, response);
			response.cache(CacheControl.of(CacheAccessor.PUBLIC, 3600)).entity(IOUtils.toByteArray(resource)).success();

		} catch (IOException e) {

			response.notFound();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.handler.ResourceHandler#getCommandType()
	 */
	@Override
	public Type getType() {

		return Type.RETRIEVE;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.handler.ResourceHandler#getCommandValue()
	 */
	@Override
	public String getCommand() {

		return String.format("%s%s", CONFIG.get(PUBLIC_RESOURCE_PATH_KEY, PUBLIC_RESOURCE_PATH), DEFAULT_SUFIX_PATTERN);
	}

	/**
	 * Resolve media type.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 */
	private void resolveMediaType(Request request, Response response) {

		String mediaType = request.getHeader("accept").orElse(StringUtils.EMPTY);
		if (mediaType.contains(HttpMediaType.TEXT_CSS.getValue()) || request.getPath().endsWith("css"))
			response.media(HttpMediaType.TEXT_CSS);
	}

}
