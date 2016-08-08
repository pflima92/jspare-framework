package org.jspare.server.transport;

import static org.jspare.core.container.Environment.my;

import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.jspare.server.Request;
import org.jspare.server.content.Entity;
import org.jspare.server.controller.Controller;
import org.jspare.server.session.SessionContext;
import org.jspare.server.session.SessionManager;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transaction.TransactionManager;

import lombok.Getter;
import lombok.Setter;

public abstract class DefaultRequest implements Request {
	
	protected Object context;
	
	@Getter
	@Setter
	protected Controller controller;

	/**
	 * Gets the session id.
	 *
	 * @return the session id
	 */
	@Getter
	protected String sessionId;

	/** The transaction. */
	@Getter
	protected Transaction transaction;

	/** The parameters. */
	@Getter
	protected Map<String, Object> parameters;

	/** The entity. */
	@Getter
	protected Entity entity;
	
	/**
	 * Instantiates a new jetty request.
	 *
	 * @param context
	 *            the context
	 */
	public DefaultRequest(final Object context) {
		this.context = context;
		this.parameters = buildMapParameters();
		this.entity = buildEntity();
		this.transaction = buildTransaction();
		this.sessionId = buildSessionId();
	}
	
	protected abstract Entity buildEntity();

	protected abstract Map<String, Object> buildMapParameters();

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
	 * @see org.jspare.server.Request#getParameter(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String key) {

		return (T) parameters.get(key);
	}
	
	/**
	 * Builds the session id.
	 *
	 * @return the string
	 */
	protected String buildSessionId() {

		String sessionId = getCookie(SessionManager.SESSION_ID_KEY);
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
	protected Transaction buildTransaction() {

		String tid = getHeadersAsString().get(HD_TID);
		Optional<Transaction> transaction = my(TransactionManager.class).getTransaction(tid);

		if (transaction.isPresent()) {

			return transaction.get();
		}
		return my(TransactionManager.class).registryTransaction();
	}
}