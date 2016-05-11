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
package org.jspare.server.context;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.context.Context;
import org.jspare.core.provider.ApplicationProvider;

import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * The Class ServerContext.
 *
 * @author pflima
 * @since 22/04/2016
 */
/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#hashCode()
 */

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#hashCode()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#hashCode()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#hashCode()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#hashCode()
 */
@EqualsAndHashCode(callSuper = false)
public class ServerContext extends Context {

	/**
	 * Empty.
	 *
	 * @return the server context
	 */
	public static ServerContext empty() {

		return new ServerContext();
	}

	/**
	 * The Server Context define the prefix of Server context on web application
	 *
	 * For e.g: For declaration: 'serverContext = "jspare-context"' on web
	 * access the contex used will be: http://localhost/jspare-context
	 *
	 * Default of this field are: {@link StringUtils.EMPTY}
	 *
	 */

	/**
	 * Gets the server context.
	 *
	 * @return the server context
	 */

	/**
	 * Gets the server context.
	 *
	 * @return the server context
	 */

	/**
	 * Gets the server context.
	 *
	 * @return the server context
	 */

	/**
	 * Gets the server context.
	 *
	 * @return the server context
	 */

	/**
	 * Gets the server context.
	 *
	 * @return the server context
	 */
	@Getter
	private String serverContext = StringUtils.EMPTY;

	/** The controllers. */
	private List<String> controllers;

	/** The filters. */
	private List<String> filters;

	/** The error handlers. */
	private List<String> errorHandlers;

	/**
	 * Instantiates a new server context.
	 *
	 * @param applicationProvider
	 *            the application provider
	 * @param applicationName
	 *            the application name
	 * @param version
	 *            the version
	 * @param parameters
	 *            the parameters
	 * @param registryComponents
	 *            the registry components
	 * @param providers
	 *            the providers
	 */
	public ServerContext(ApplicationProvider applicationProvider, String applicationName, String version, Map<String, Object> parameters,
			List<String> registryComponents, Map<String, Map<String, String>> providers) {
		super(applicationProvider, applicationName, version, parameters, registryComponents, providers);
	}

	/**
	 * Instantiates a new server context.
	 */
	private ServerContext() {
	}

	/**
	 * Adds the controller.
	 *
	 * @param controller
	 *            the controller
	 * @return the server context
	 */
	public ServerContext addController(String controller) {

		if (!getControllers().isPresent()) {

			controllers = new ArrayList<String>();
		}

		controllers.add(controller);

		return this;
	}

	/**
	 * Gets the controllers.
	 *
	 * @return the controllers
	 */
	public Optional<List<String>> getControllers() {

		return Optional.ofNullable(controllers);
	}

	/**
	 * Gets the error handlers.
	 *
	 * @return the error handlers
	 */
	public Optional<List<String>> getErrorHandlers() {

		return Optional.ofNullable(errorHandlers);
	}

	/**
	 * Gets the filters.
	 *
	 * @return the filters
	 */
	public Optional<List<String>> getFilters() {

		return Optional.ofNullable(filters);
	}

}
