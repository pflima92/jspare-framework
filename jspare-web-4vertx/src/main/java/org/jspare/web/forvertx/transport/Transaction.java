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

import java.time.LocalDateTime;
import java.util.Map;

import org.jspare.core.container.Component;
import org.jspare.core.container.Scope;

/**
 * The Interface Transaction.
 *
 * @author pflima
 * @since 30/03/2016
 */
@Component(scope = Scope.FACTORY)
public interface Transaction {

	/**
	 * Change status.
	 *
	 * @param status
	 *            the status
	 */
	void changeStatus(TransactionStatus status);

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	Map<String, Object> getContext();

	/**
	 * Gets the executor.
	 *
	 * @return the executor
	 */
	TransactionExecutor getExecutor();

	/**
	 * Gets the finish date time.
	 *
	 * @return the finish date time
	 */
	LocalDateTime getFinishDateTime();

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	String getId();

	/**
	 * Gets the last change date time.
	 *
	 * @return the last change date time
	 */
	LocalDateTime getLastChangeDateTime();

	/**
	 * Gets the start date time.
	 *
	 * @return the start date time
	 */
	LocalDateTime getStartDateTime();

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	TransactionStatus getStatus();
}
