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
package org.jspare.server;

import java.io.InputStream;
import java.util.Locale;

import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;

/**
 * @author pflima
 *
 *         http://www.w3.org/Protocols/rfc2616/rfc2616-sec6.html
 *
 *         1xx: Informational - Request received, continuing process - 2xx:
 *         Success - The action was successfully received, understood, and
 *         accepted - 3xx: Redirection - Further action must be taken in order
 *         to complete the request - 4xx: Client Error - The request contains
 *         bad syntax or cannot be fulfilled - 5xx: Server Error - The server
 *         failed to fulfill an apparently valid request
 *
 */
public interface Response extends Cloneable {

	/**
	 * Adds the client resource.
	 *
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @return the response
	 */
	Response addClientResource(String name, String value);

	/**
	 * Business error.
	 */
	void businessError();

	/**
	 * Cache.
	 *
	 * @param cacheControl
	 *            the cache control
	 * @return the response
	 */
	Response cache(CacheControl cacheControl);

	/**
	 * Clone.
	 *
	 * @return the object
	 */
	Object clone();

	/**
	 * End.
	 */
	void end();

	/**
	 * Entity.
	 *
	 * @param entity
	 *            the entity
	 * @return the response
	 */
	Response entity(byte[] entity);

	/**
	 * Entity.
	 *
	 * @param entity
	 *            the entity
	 * @return the response
	 */
	Response entity(InputStream entity);

	/**
	 * Entity.
	 *
	 * @param object
	 *            the object
	 * @return the response
	 */
	Response entity(Object object);

	/**
	 * Entity.
	 *
	 * @param view
	 *            the view
	 * @return the response
	 */
	Response entity(Renderable view);

	/**
	 * Entity.
	 *
	 * @param entity
	 *            the entity
	 * @return the response
	 */
	Response entity(String entity);

	/**
	 * Forbidden.
	 */
	void forbidden();

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	Object getEntity();

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	MultiValueMap<String, Object> getHeaders();

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	Locale getLanguage();

	/**
	 * Gets the security context.
	 *
	 * @return the security context
	 */
	SecurityContext getSecurityContext();

	/**
	 * Gets the session context.
	 *
	 * @return the session context
	 */
	SessionContext getSessionContext();

	/**
	 * Gets the transaction.
	 *
	 * @return the transaction
	 */
	Transaction getTransaction();

	/**
	 * Invalid.
	 */
	void invalid();

	/**
	 * Media.
	 *
	 * @param medias
	 *            the medias
	 * @return the response
	 */
	Response media(Media... medias);

	/**
	 * Not found.
	 */
	void notFound();

	/**
	 * Redirect.
	 *
	 * @param to
	 *            the to
	 */
	void redirect(String to);

	/**
	 * Status.
	 *
	 * @param status
	 *            the status
	 * @return the response
	 */
	Response status(Status status);

	/**
	 * Success.
	 */
	void success();

	/**
	 * Success.
	 *
	 * @param entity
	 *            the entity
	 */
	void success(byte[] entity);

	/**
	 *
	 * Return as a JSON Entity
	 *
	 * @param object
	 */
	void success(Object object);

	/**
	 * Success.
	 *
	 * @param view
	 *            the view
	 */
	void success(Renderable view);

	/**
	 * Success.
	 *
	 * @param entity
	 *            the entity
	 */
	void success(String entity);

	/**
	 * System error.
	 */
	void systemError();

	/**
	 * System error.
	 *
	 * @param t
	 *            the t
	 */
	void systemError(Throwable t);

	/**
	 * Unauthorized.
	 */
	void unauthorized();

	/**
	 * Yeld.
	 */
	void yield();
}