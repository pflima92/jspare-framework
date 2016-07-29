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

import static org.jspare.core.commons.Definitions.DEFAULT_CHARSET;
import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.collections.MultiValueHashMap;
import org.jspare.core.collections.MultiValueMap;
import org.jspare.core.serializer.Json;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.NoSuchCallerException;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.jetty.http.HttpMediaType;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.CacheControl;
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
public class JettyResponse implements Response {

	/** The Constant MEDIA_TYPE_SEPARATOR. */
	private static final String MEDIA_TYPE_SEPARATOR = ";";

	/** The status. */
	private Status status;

	/** The entity. */
	private Object entity;

	/** The cache control. */
	private CacheControl cacheControl;

	/** The closed. */
	private boolean closed;

	/** The request. */
	private Request request;

	/** The response builder. */
	private ResponseBuilder responseBuilder;

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */
	@Setter
	private Media[] media = { HttpMediaType.ALL };

	/**
	 * Gets the http response.
	 *
	 * @return the http response
	 */

	/**
	 * Gets the http response.
	 *
	 * @return the http response
	 */
	@Getter
	private javax.ws.rs.core.Response httpResponse;

	/** The transaction. */
	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getTransaction()
	 */

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getTransaction()
	 */
	@Getter
	private Transaction transaction;

	/**
	 * Instantiates a new jetty response.
	 *
	 * @param request
	 *            the request
	 * @param responseBuilder
	 *            the response builder
	 */
	public JettyResponse(Request request, ResponseBuilder responseBuilder) {
		this.request = request;
		this.responseBuilder = responseBuilder;
		this.transaction = request.getTransaction();
		this.addCookie(JettyServer.SESSION_ID_KEY, request.getSessionContext().getSessionId());
	}

	/**
	 * Instantiates a new jetty response.
	 *
	 * @param responseBuilder
	 *            the response builder
	 */
	public JettyResponse(ResponseBuilder responseBuilder) {
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#cache(org.jspare.server.CacheControl)
	 */
	@Override
	public Response cache(CacheControl cacheControl) {

		this.cacheControl = cacheControl;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() {
		try {

			return super.clone();
		} catch (CloneNotSupportedException e) {

			return null;
		}
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
	 * @see org.jspare.server.Response#entity(byte[])
	 */
	@Override
	public Response entity(byte[] entity) {

		this.entity = entity;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#entity(java.io.InputStream)
	 */
	@Override
	public Response entity(InputStream entity) {

		this.entity = entity;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#entity(java.lang.Object)
	 */
	@Override
	public Response entity(Object object) {

		try {

			String content = my(Json.class).toJSON(object);
			this.entity(content);
		} catch (Exception e) {

			status(Status.INTERNAL_SERVER_ERROR).end();
		}
		return this;
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

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#entity(java.lang.String)
	 */
	@Override
	public Response entity(String entity) {

		this.entity = entity;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getEntity()
	 */
	@Override
	public Object getEntity() {

		if (entity instanceof byte[]) {

			return new String((byte[]) entity, DEFAULT_CHARSET);
		}
		return entity;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getHeaders()
	 */
	@Override
	public MultiValueMap<String, Object> getHeaders() {

		MultiValueMap<String, Object> mVmap = new MultiValueHashMap<>();
		((ContainerRequestContext) request.getSourceRequest()).getHeaders().entrySet().forEach(es -> {

			mVmap.put(es.getKey(), es.getValue().stream().collect(Collectors.toList()));
		});

		return mVmap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getLanguage()
	 */
	@Override
	public Locale getLanguage() {

		return this.request.getLocale();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#getSessionContext()
	 */
	@Override
	public SessionContext getSessionContext() {

		return request.getSessionContext();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#media(org.jspare.server.Media[])
	 */
	@Override
	public Response media(Media... medias) {

		this.media = medias;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#status(org.jspare.server.Status)
	 */
	@Override
	public Response status(Status status) {

		this.status = status;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Response#yeld()
	 */
	@Override
	public void yield() {

		this.status = Status.YIELD;
		end();

		try {

			request.getTransaction().getExecutor().notifyFinish(this);
		} catch (NoSuchCallerException e) {

			status(Status.INTERNAL_SERVER_ERROR).end();
		}
	}

	/**
	 * Resolve media type.
	 *
	 * @return the string
	 */
	private String resolveMediaType() {

		List<String> mediaTypes = Arrays.asList(media).stream().map(Media::getValue).collect(Collectors.toList());
		StringBuilder builerMediaType = new StringBuilder(StringUtils.join(mediaTypes, MEDIA_TYPE_SEPARATOR));
		return builerMediaType.toString();
	}
}