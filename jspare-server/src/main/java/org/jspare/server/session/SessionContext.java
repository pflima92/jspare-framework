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
package org.jspare.server.session;

import static org.jspare.core.container.Environment.my;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * The Class SessionContext.
 *
 * @author pflima
 * @since 10/05/2016
 */
/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 */

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 */

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 */
@RequiredArgsConstructor

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 * @param language
 *            the language
 * @param contextMap
 *            the context map
 */

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 * @param language
 *            the language
 * @param contextMap
 *            the context map
 */

/**
 * Instantiates a new session context.
 *
 * @param sessionId
 *            the session id
 * @param createDateTime
 *            the create date time
 * @param language
 *            the language
 * @param contextMap
 *            the context map
 */
@AllArgsConstructor
public class SessionContext {

	/** The session id. */
	private final String sessionId;

	/** The create date time. */
	private final LocalDateTime createDateTime;

	/** The language. */
	private Locale language = Locale.getDefault();

	/** The context map. */
	private Map<Object, Object> contextMap = new HashMap<>();

	/**
	 * Expire.
	 */
	public void expire() {

		my(SessionManager.class).invalidate(this.sessionId);
	}

	/**
	 * Gets the.
	 *
	 * @param key
	 *            the key
	 * @return the object
	 */
	public Object get(Object key) {

		return contextMap.get(key);
	}

	/**
	 * Gets the language.
	 *
	 * @return the language
	 */
	public Locale getLanguage() {

		return language;
	}

	/**
	 * Language.
	 *
	 * @param language
	 *            the language
	 * @return the session context
	 */
	public SessionContext language(Locale language) {

		this.language = language;
		return this;
	}

	/**
	 * Put.
	 *
	 * @param key
	 *            the key
	 * @param value
	 *            the value
	 * @return the session context
	 */
	public SessionContext put(Object key, Object value) {

		contextMap.put(key, value);
		return this;
	}

	/**
	 * Save.
	 */
	public void save() {

		my(SessionManager.class).updateSession(this.sessionId, this);
	}

}