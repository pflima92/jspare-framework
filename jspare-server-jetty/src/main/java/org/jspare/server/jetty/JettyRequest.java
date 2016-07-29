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
import static org.jspare.core.container.Environment.my;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.server.ContainerRequest;
import org.jspare.core.collections.MultiValueHashMap;
import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.Request;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Type;
import org.jspare.server.session.SessionContext;
import org.jspare.server.session.SessionManager;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transaction.TransactionManager;
import org.jspare.server.transport.Media;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyRequest implements Request {

	/** The context. */
	private final ContainerRequestContext context;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getController()
	 */
	@Getter

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.Request#setController(org.jspare.server.controller.
	 * Controller)
	 */
	@Setter
	private Controller controller;

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	@Getter
	private final String sessionId;

	/** The transaction. */
	private final Transaction transaction;

	/** The parameters. */
	private final Map<String, String> parameters;

	/** The entity. */
	private final String entity;

	/**
	 * Instantiates a new jetty request.
	 *
	 * @param context
	 *            the context
	 */
	public JettyRequest(final ContainerRequestContext context) {
		this.context = context;
		this.parameters = buidMapParameters();
		this.entity = buildBody();
		this.transaction = buildTransaction();
		this.sessionId = buildSessionId();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getBasePath()
	 */
	@Override
	public String getBasePath() {

		return context.getUriInfo().getBaseUri().toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getCommandAlias()
	 */
	@Override
	public String getCommandAlias() {

		return this.context.getHeaderString(HD_ALIAS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getClientResource(java.lang.String)
	 */
	@Override
	public String getCookie(String name) {

		Cookie cookie = this.context.getCookies().get(name);
		return cookie != null ? cookie.getValue() : StringUtils.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getEntity()
	 */
	@Override
	public Optional<Object> getEntity() {

		return Optional.ofNullable(entity);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeader(java.lang.String)
	 */
	@Override
	public Optional<String> getHeader(String key) {

		return Optional.ofNullable(context.getHeaderString(key));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeaders()
	 */
	@Override
	public MultiValueMap<String, Object> getHeaders() {

		MultiValueMap<String, Object> mVmap = new MultiValueHashMap<>();
		context.getHeaders().entrySet().forEach(es -> {

			mVmap.put(es.getKey(), es.getValue().stream().collect(Collectors.toList()));
		});
		return mVmap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeadersString()
	 */
	@Override
	public Map<String, String> getHeadersAsString() {

		Map<String, String> mHd = new HashMap<>();
		context.getHeaders().entrySet().forEach(es -> {
			mHd.put(es.getKey(), context.getHeaderString(es.getKey()));
		});
		return mHd;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getLocale()
	 */
	@Override
	public Locale getLocale() {

		return context.getLanguage();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getMedia()
	 */
	@Override
	public Media getMedia() {

		return (Media) (() -> context.getMediaType().toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getParameter(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String key) {

		return (T) parameters.get(key);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getParameters()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getParameters() {
		Map<String, String> map = context.getPropertyNames().stream()
				.collect(Collectors.toMap(String::toString, key -> context.getProperty(key).toString()));
		int size = map.size();
		size = map.containsKey("password") ? size - 1 : size;
		Map<String, String> result = new HashMap<>();
		Iterator<Entry<String, String>> iterator = map.entrySet().iterator();
		Entry<String, String> entry;
		for (int i = 0; i < size; i++) {
			entry = iterator.next();
			String key = entry.getKey();
			if (key.equals("password")) {
				entry = iterator.next();
			}
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getPath()
	 */
	@Override
	public String getPath() {
		return context.getUriInfo().getPath();
	}

	@Override
	public String getRemoteAddr() {

		return getHeader("X-FORWARDED-FOR").orElse(null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getSessionContext()
	 */
	@Override
	public SessionContext getSessionContext() {

		return my(SessionManager.class).getSessionContext(this.sessionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getSourceRequest()
	 */
	@Override
	public Object getSourceRequest() {

		return this.context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getTransaction()
	 */
	@Override
	public Transaction getTransaction() {

		return this.transaction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getType()
	 */
	@Override
	public Type getType() {

		return Type.valueOf(context.getMethod());
	}

	/**
	 * Buid map parameters.
	 *
	 * @return the map
	 */
	private Map<String, String> buidMapParameters() {
		javax.ws.rs.core.Form form = null;
		if (context instanceof ContainerRequest) {
			ContainerRequest request = (ContainerRequest) context;
			if (request.hasEntity() && javax.ws.rs.core.MediaType.APPLICATION_FORM_URLENCODED_TYPE.getSubtype()
					.equals(request.getMediaType().getSubtype())) {
				request.bufferEntity();
				form = request.readEntity(Form.class);
			}
		}
		MultivaluedHashMap<String, String> requestParams = new MultivaluedHashMap<String, String>();
		requestParams.putAll(context.getUriInfo().getPathParameters());
		requestParams.putAll(context.getUriInfo().getQueryParameters());
		if (form != null) {
			requestParams.putAll(form.asMap());
		}

		return requestParams.keySet().stream().collect(Collectors.toMap(String::toString, key -> requestParams.getFirst(key)));

	}

	/**
	 * Builds the body.
	 *
	 * @return the string
	 */
	private String buildBody() {

		try {

			if (context.hasEntity()) {

				return IOUtils.toString(context.getEntityStream(), DEFAULT_CHARSET);
			}

		} catch (IOException e) {
			log.error("No content on  body request", e);
		}
		return StringUtils.EMPTY;
	}

	/**
	 * Builds the session id.
	 *
	 * @return the string
	 */
	private String buildSessionId() {

		String sessionId = getCookie(JettyServer.SESSION_ID_KEY);
		if (StringUtils.isEmpty(sessionId) || !my(SessionManager.class).renew(sessionId)) {

			SessionContext session = my(SessionManager.class).nextSessionContext();
			sessionId = session.getSessionId();
		}

		return sessionId;
	}

	/**
	 * Builds the transaction.
	 *
	 * @return the transaction
	 */
	private Transaction buildTransaction() {

		String tid = context.getHeaderString(HD_TID);
		Optional<Transaction> transaction = my(TransactionManager.class).getTransaction(tid);

		if (transaction.isPresent()) {

			return transaction.get();
		}

		return my(TransactionManager.class).registryTransaction();
	}
}