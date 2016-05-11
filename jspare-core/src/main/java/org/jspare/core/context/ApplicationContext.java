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
package org.jspare.core.context;

import org.jspare.core.container.Component;
import org.jspare.core.exception.InfraException;

/**
 * @author pflima
 *
 */
@Component
public interface ApplicationContext {

	/** The Constant ALL_SCAN_QUOTE. */
	String ALL_SCAN_QUOTE = ".*";

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	Context getContext();

	/**
	 * Load.
	 *
	 * This method is responsible for load Application Context by default value
	 * defined on this Interface
	 */
	void load() throws InfraException;

	/**
	 * Load.
	 *
	 * The @param applicationPath is param responsible for provide path for load
	 * Context.
	 *
	 */
	void load(String resourceApplicationPath) throws InfraException;

	/**
	 * Release the Context
	 */
	void release();

}
