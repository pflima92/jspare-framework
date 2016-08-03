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

import java.util.Set;

import javax.validation.ConstraintViolation;

import org.jspare.core.exception.InfraException;

import lombok.Getter;

/**
 * The Class CommandInstantiationException.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class BeanValidationException extends InfraException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Getter
	private final Set<ConstraintViolation<Object>> violedConstraints;
	
	public BeanValidationException(Set<ConstraintViolation<Object>> violedConstraints) {
		super("Invalid bean exception");
		this.violedConstraints = violedConstraints;
	}

	/**
	 * Instantiates a new command instantiation exception.
	 *
	 * @param message
	 *            the message
	 */
	public BeanValidationException(String message, Set<ConstraintViolation<Object>> violedConstraints) {
		super(message);
		this.violedConstraints = violedConstraints;
	}

	/**
	 * Instantiates a new command instantiation exception.
	 *
	 * @param message
	 *            the message
	 * @param cause
	 *            the cause
	 */
	public BeanValidationException(String message, Throwable cause, Set<ConstraintViolation<Object>> violedConstraints) {
		super(message, cause);
		this.violedConstraints = violedConstraints;
	}

	/**
	 * Instantiates a new command instantiation exception.
	 *
	 * @param e
	 *            the e
	 */
	public BeanValidationException(Throwable e, Set<ConstraintViolation<Object>> violedConstraints) {
		super(e);
		this.violedConstraints = violedConstraints;
	}
}