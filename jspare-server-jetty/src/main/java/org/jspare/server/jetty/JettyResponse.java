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
package org.jspare.server.jetty;

import static org.jspare.core.container.Environment.factory;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.jetty.http.HttpMediaType;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.DefaultResponse;
import org.jspare.server.transport.Media;
import org.jspare.server.transport.Renderable;
import org.jspare.server.transport.Status;

import lombok.Getter;
import lombok.Setter;

/**
 * The Class JettyResponse.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class JettyResponse extends DefaultResponse {

	/** The response builder. */
	private ResponseBuilder responseBuilder;

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */
	@Setter
	protected Media[] media = { HttpMediaType.ALL };

	/**
	 * Gets the http response.
	 *
	 * @return the http response
	 */
	@Getter
	private javax.ws.rs.core.Response httpResponse;

	/**
	 * Instantiates a new jetty response.
	 *
	 * @param request
	 *            the request
	 * @param responseBuilder
	 *            the response builder
	 */
	public JettyResponse(Request request, ResponseBuilder responseBuilder) {
		super(request);
		this.responseBuilder = responseBuilder;
	}

	/**
	 * Instantiates a new jetty response.
	 *
	 * @param responseBuilder
	 *            the response builder
	 */
	public JettyResponse(ResponseBuilder responseBuilder) {
		super(null);
		this.responseBuilder = responseBuilder;
		this.transaction = factory(Transaction.class);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#addClientResource(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Response addCookie(String name, String value) {

		NewCookie newCookie = new NewCookie(name, value);
		responseBuilder.cookie(newCookie);
		return this;
	}

	@Override
	public Response addHeader(String name, String value) {

		this.responseBuilder.header(name, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#end()
	 */
	@Override
	public void end() {
		if (closed) {
			return;
		}
		try {
			if (status == null) {

				status = Status.INTERNAL_SERVER_ERROR;
			}
			if (entity == null) {

				entity = StringUtils.EMPTY;
			}
			if (cacheControl != null) {

				javax.ws.rs.core.CacheControl httpCache = javax.ws.rs.core.CacheControl
						.valueOf(String.format("%s, max-age=%d", cacheControl.getAccessor().getValue(), cacheControl.getDuration()));
				responseBuilder.cacheControl(httpCache);
			}
			if (status == Status.MOVED_PERMANENTLY) {
				responseBuilder = javax.ws.rs.core.Response.seeOther(new URI((String) entity));
				this.httpResponse = responseBuilder.build();
				return;
			}

			this.httpResponse = responseBuilder.status(status.getCode()).type(resolveMediaType()).entity(entity).build();

		} catch (URISyntaxException e) {

			this.status = Status.INTERNAL_SERVER_ERROR;
			this.entity = "Invalid url location to redirect.";
			this.httpResponse = responseBuilder.status(status.getCode()).type(resolveMediaType()).entity(entity).build();
		} finally {

			closed = true;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#entity(org.jspare.server.Renderable)
	 */
	@Override
	public Response entity(Renderable view) {

		try {

			this.entity = view.render(request);
			return this.media(HttpMediaType.TEXT_HTML);
		} catch (RenderableException e) {

			status(Status.INTERNAL_SERVER_ERROR).end();
			return this;
		}
	}

	
}