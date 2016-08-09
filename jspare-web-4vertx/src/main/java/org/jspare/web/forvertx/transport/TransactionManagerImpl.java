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

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.factory;
import static org.jspare.web.forvertx.commons.ServerDefinitions.TRANSACTION_TIME_TO_LIVE_DEFAULT;
import static org.jspare.web.forvertx.commons.ServerDefinitions.TRANSACTION_TIME_TO_LIVE_KEY;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.jspare.core.collections.TimedHashMap;

/**
 * The Class TransactionManagerImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class TransactionManagerImpl implements TransactionManager {

	/** The transactions. */
	private static Map<String, Transaction> transactions;

	/**
	 * Instantiates a new transaction manager impl.
	 */
	public TransactionManagerImpl() {

		long timeToLive = Long.parseLong(CONFIG.get(TRANSACTION_TIME_TO_LIVE_KEY, TRANSACTION_TIME_TO_LIVE_DEFAULT));

		transactions = new TimedHashMap<>(TimeUnit.MINUTES, timeToLive);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionManager#end(java.lang.String)
	 */
	@Override
	public void end(String tid) {

		if (getTransaction(tid).isPresent()) {

			Transaction transaction = getTransaction(tid).get();
			transaction.changeStatus(TransactionStatus.DONE);
			transactions.put(tid, transaction);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionManager#getTransaction(java.lang
	 * .String)
	 */
	@Override
	public Optional<Transaction> getTransaction(String tid) {

		return Optional.ofNullable(transactions.get(tid));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionManager#registryTransaction()
	 */
	@Override
	public Transaction registryTransaction() {

		Transaction transaction = factory(Transaction.class);
		transactions.put(transaction.getId(), transaction);
		return transaction;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionManager#yield(java.lang.String,
	 * org.jspare.server.transaction.Transaction)
	 */
	@Override
	public void yield(String bind, Transaction transaction) {

		transaction.changeStatus(TransactionStatus.YIELD);
		transactions.put(transaction.getId(), transaction);
	}

}
