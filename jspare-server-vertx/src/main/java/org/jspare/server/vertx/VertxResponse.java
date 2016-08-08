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
package org.jspare.server.vertx;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.transport.DefaultResponse;
import org.jspare.server.transport.Media;
import org.jspare.server.transport.Renderable;
import org.jspare.server.transport.Status;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.impl.CookieImpl;

/**
 * The Class JettyResponse.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class VertxResponse extends DefaultResponse {
	
	private RoutingContext routingContext;

	public VertxResponse(Request request, RoutingContext routingContext) {
		super(request);
		this.routingContext = routingContext;
	}

	@Override
	public Response addCookie(String name, String value) {

		routingContext.addCookie(new CookieImpl(name, value));
		return this;
	}

	@Override
	public void end() {
		if (closed) {
			return;
		}
		try {
			HttpServerResponse response = routingContext.response();
			response.setStatusCode(status.getCode());
			response.setStatusMessage(status.name());
			response.putHeader("Content-type", resolveContentType());

			if (entity != null) {
				
				response.end(entity.toString(), StandardCharsets.UTF_8.name());
				return;
			}
			response.end();
		} finally {
			closed = true;
		}
	}

	private String resolveContentType() {

		StringBuilder builder = new StringBuilder();
		List<Media> itens = Arrays.asList(media); 
		while(itens.iterator().hasNext()){
			Media item = itens.iterator().next();
			builder.append(item.getValue());
			if(itens.iterator().hasNext()) builder.append(";");
		}
		return builder.toString();
	}

	@Override
	public Response entity(Renderable view) {

		try {

			this.entity = view.render(request);
			return this;
		} catch (RenderableException e) {

			status(Status.INTERNAL_SERVER_ERROR).end();
			return this;
		}
	}

	@Override
	public Response addHeader(String name, String value) {

		this.routingContext.response().headers().add(name, value);
		return this;
	}
}