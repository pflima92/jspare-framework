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

import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.CacheControl;
import org.jspare.server.transport.Media;
import org.jspare.server.transport.Renderable;
import org.jspare.server.transport.Status;

/**
 * @author pflima
 *
 *         The Response class
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
	Response addCookie(String name, String value);
	
	/**
	 * @param name
	 * @param value
	 * @return
	 */
	Response addHeader(String name, String value);

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
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	Object getEntity();
	
	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	Locale getLanguage();

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
	 * Media.
	 *
	 * @param medias
	 *            the medias
	 * @return the response
	 */
	Response media(Media... medias);

	/**
	 * Status.
	 *
	 * @param status
	 *            the status
	 * @return the response
	 */
	Response status(Status status);

	/**
	 * Yeld.
	 */
	void yield();
}