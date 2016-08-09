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
package org.jspare.web.forvertx.transport;

import static org.jspare.core.container.Environment.factory;
import static org.jspare.core.container.Environment.my;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Instantiates a new transaction impl.
 *
 * @param startDateTime
 *            the start date time
 * @param originAddress
 *            the origin address
 * @param locale
 *            the locale
 */

/**
 * Instantiates a new transaction impl.
 *
 * @param startDateTime
 *            the start date time
 * @param originAddress
 *            the origin address
 * @param locale
 *            the locale
 */
public class TransactionImpl implements Transaction {

	/** The Constant tid. */
	private final String tid;

	/** The context. */
	private final Map<String, Object> context = new ConcurrentHashMap<>();

	/** The start date time. */
	private final LocalDateTime startDateTime;

	/** The last change time. */
	private LocalDateTime lastChangeTime;

	/** The finish date time. */
	private LocalDateTime finishDateTime;

	/** The current status. */
	private TransactionStatus currentStatus;

	/** The executor. */
	private TransactionExecutor executor;

	/**
	 * Instantiates a new transaction impl.
	 */
	public TransactionImpl() {

		this.tid = my(TidGenerator.class).generate();
		this.startDateTime = LocalDateTime.now();
		this.executor = factory(TransactionExecutor.class);
		changeStatus(TransactionStatus.PROGRESS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.Transaction#changeStatus(org.jspare.server.
	 * transaction.TransactionStatus)
	 */
	@Override
	public void changeStatus(TransactionStatus status) {

		if (status.equals(TransactionStatus.DONE)) {

			finishDateTime = LocalDateTime.now();
		}

		lastChangeTime = LocalDateTime.now();
		this.currentStatus = status;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getContext()
	 */
	@Override
	public Map<String, Object> getContext() {

		return this.context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getExecutor()
	 */
	@Override
	public TransactionExecutor getExecutor() {

		return this.executor;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getFinishDateTime()
	 */
	@Override
	public LocalDateTime getFinishDateTime() {

		return finishDateTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getId()
	 */
	@Override
	public String getId() {

		return tid;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getLastChangeDateTime()
	 */
	@Override
	public LocalDateTime getLastChangeDateTime() {

		return lastChangeTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getStartDateTime()
	 */
	@Override
	public LocalDateTime getStartDateTime() {

		return startDateTime;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.Transaction#getStatus()
	 */
	@Override
	public TransactionStatus getStatus() {

		return currentStatus;
	}

}
