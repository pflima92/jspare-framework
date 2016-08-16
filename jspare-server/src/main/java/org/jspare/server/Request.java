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

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jspare.commons.collections.MultiValueMap;
import org.jspare.server.content.Entity;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Type;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.Media;

/**
 * The Interface Request.
 *
 * @author pflima
 * @since 30/03/2016
 */
public interface Request {

	/** The alias hd. */
	String HD_ALIAS = "hd-alias-tkn";

	/** The tid hd. */
	String HD_TID = "hd-tid-tkn";

	/**
	 * Gets the base path.
	 *
	 * @return the base path
	 */
	String getBasePath();

	/**
	 * Gets the command alias.
	 *
	 * @return the command alias
	 */
	String getCommandAlias();

	/**
	 * Gets the source request.
	 *
	 * @return the source request
	 */
	Object getContext();

	/**
	 * Gets the controller when the super class is child of {@link #org
	 * .jspare.server.controller.Controller}
	 *
	 * @return the command alias
	 */
	Controller getController();

	/**
	 * Gets the client resource.
	 *
	 * @param name
	 *            the name
	 * @return the client resource
	 */
	String getCookie(String name);

	/**
	 * Gets the entity.
	 *
	 * @return the entity
	 */
	Entity getEntity();

	/**
	 * Gets the header.
	 *
	 * @param key
	 *            the key
	 * @return the header
	 */
	Optional<String> getHeader(String key);

	/**
	 * Gets the headers.
	 *
	 * @return the headers
	 */
	MultiValueMap<String, Object> getHeaders();

	/**
	 * Gets the headers string.
	 *
	 * @return the headers string
	 */
	Map<String, String> getHeadersAsString();

	/**
	 * Gets the locale.
	 *
	 * @return the locale
	 */
	Locale getLocale();

	/**
	 * Gets the media.
	 *
	 * @return the media
	 */
	Media getMedia();

	/**
	 * Gets the parameter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @return the parameter
	 */
	<T> T getParameter(String key);

	/**
	 * Gets the parameters.
	 *
	 * @param <T>
	 *            the generic type
	 * @return the parameters
	 */
	Map<String, Object> getParameters();

	/**
	 * Gets the path.
	 *
	 * @return the path
	 */
	String getPath();

	String getRemoteAddr();

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
	 * Gets the type.
	 *
	 * @return the type
	 */
	Type getType();

	/**
	 * Sets the controller.
	 *
	 * @param controller
	 *            the new controller
	 */
	void setController(Controller controller);
}