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
package org.jspare.server.exception;

import org.jspare.core.exception.InfraException;

/**
 * The Class CommandException.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class LoadTemplateException extends InfraException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new command exception.
	 *
	 * @param msg
	 *            the msg
	 */
	public LoadTemplateException(String msg) {
		super(msg);
	}

	/**
	 * Instantiates a new command exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public LoadTemplateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new command exception.
	 *
	 * @param e
	 *            the e
	 */
	public LoadTemplateException(Throwable e) {
		super(e);
	}
}