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

import static org.jspare.core.container.Environment.CONFIG;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import org.jspare.core.collections.TimedHashMap;
import org.jspare.core.collections.TimedMap;

/**
 * The Class SessionManagerImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class SessionManagerImpl implements SessionManager {

	/** The Constant DEFAULT_SESSION_TIMEOUT_KEY. */
	private final static String DEFAULT_SESSION_TIMEOUT_KEY = "server.session.timeout";

	/** The Constant DEFAULT_SESSION_TIMEOUT. */
	private final static Integer DEFAULT_SESSION_TIMEOUT = 20;

	/** The session context map. */
	private final TimedMap<String, SessionContext> sessionContextMap;

	/** The generator. */
	private final SessionIdentifierGenerator generator;

	/**
	 * Instantiates a new session manager impl.
	 */
	public SessionManagerImpl() {

		this.generator = new SessionIdentifierGenerator();
		this.sessionContextMap = new TimedHashMap<>(TimeUnit.MINUTES,
				Integer.parseInt((CONFIG.get(DEFAULT_SESSION_TIMEOUT_KEY, DEFAULT_SESSION_TIMEOUT.toString()))));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.session.SessionManager#getSessionContext(java.lang.
	 * String)
	 */
	@Override
	public SessionContext getSessionContext(String sessionId) {

		return sessionContextMap.get(sessionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jspare.server.session.SessionManager#invalidate(java.lang.String)
	 */
	@Override
	public void invalidate(String sessionId) {

		this.sessionContextMap.remove(sessionId);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.session.SessionManager#nextSessionContext()
	 */
	@Override
	public SessionContext nextSessionContext() {

		SessionContext sessionContext = new SessionContext(generator.generateSessionId(), LocalDateTime.now());
		this.sessionContextMap.put(sessionContext.getSessionId(), sessionContext);
		return sessionContext;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.session.SessionManager#renew(java.lang.String)
	 */
	@Override
	public boolean renew(String sessionId) {

		if (!this.sessionContextMap.containsKey(sessionId)) {

			return false;
		}
		this.sessionContextMap.renewKey(sessionId);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jspare.server.session.SessionManager#updateSession(java.lang.String,
	 * org.jspare.server.session.SessionContext)
	 */
	@Override
	public void updateSession(String sessionId, SessionContext session) {

		if (sessionContextMap.containsKey(sessionId)) {

			this.sessionContextMap.put(sessionId, session);
		}
	}

}

final class SessionIdentifierGenerator {
	private SecureRandom random = new SecureRandom();

	public String generateSessionId() {
		return new BigInteger(130, random).toString(32);
	}
}