package org.jspare.server.transport;

import static org.jspare.core.commons.Definitions.DEFAULT_CHARSET;
import static org.jspare.core.container.Environment.my;

import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.serializer.Json;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.NoSuchCallerException;
import org.jspare.server.session.SessionContext;
import org.jspare.server.session.SessionManager;
import org.jspare.server.transaction.Transaction;

import lombok.Getter;
import lombok.Setter;

public abstract class DefaultResponse implements Response {

	/** The Constant MEDIA_TYPE_SEPARATOR. */
	protected static final String MEDIA_TYPE_SEPARATOR = ";";

	/** The status. */
	protected Status status;

	/** The entity. */
	protected Object entity;

	/** The cache control. */
	protected CacheControl cacheControl;

	/** The closed. */
	protected boolean closed;

	/** The request. */
	protected Request request;

	/**
	 * Sets the media.
	 *
	 * @param media
	 *            the new media
	 */
	@Setter
	protected Media[] media;

	/** The transaction. */
	@Getter
	protected Transaction transaction;

	public DefaultResponse(Request request) {
		this.request = request;
		this.transaction = request.getTransaction();
		this.addCookie(SessionManager.SESSION_ID_KEY, request.getSessionContext().getSessionId());
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
	protected String resolveMediaType() {

		List<String> mediaTypes = Arrays.asList(media).stream().map(Media::getValue).collect(Collectors.toList());
		StringBuilder builerMediaType = new StringBuilder(StringUtils.join(mediaTypes, MEDIA_TYPE_SEPARATOR));
		return builerMediaType.toString();
	}
}