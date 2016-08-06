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
package org.jspare.server.router;

import java.util.List;
import java.util.Optional;

import org.jspare.core.container.Component;
import org.jspare.server.controller.CommandData;
import org.jspare.server.filter.Filter;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.resource.Resource;
import org.jspare.server.transport.Status;

/**
 * The Interface Router.
 *
 * @author pflima
 * @since 30/03/2016
 */
/**
 * @author pflima
 *
 */
@Component
public interface Router {

	/**
	 * Adds the error handler.
	 *
	 * @param status
	 *            the status
	 * @param errorHandlerClazz
	 *            the error handler clazz
	 * @return the router
	 */
	Router addErrorHandler(Status status, Class<? extends ResourceHandler> errorHandlerClazz);

	/**
	 * Adds the error handler.
	 *
	 * @param status
	 *            the status
	 * @param errorHandler
	 *            the error handler
	 * @return the router
	 */
	Router addErrorHandler(Status status, ResourceHandler errorHandler);

	/**
	 * Adds the mapping.
	 *
	 * @param cmdClazz
	 *            the cmd clazz
	 * @return the router
	 */
	Router addMapping(Class<?> cmdClazz);

	/**
	 * @param resource
	 */
	void addResource(Resource<?> resource);

	/**
	 * @param resource
	 */
	void addResourceClazz(Class<? extends Resource<?>> resource);

	/**
	 * Adds the resource handler.
	 *
	 * @param resourceHandlerClazz
	 *            the resource handler clazz
	 * @return the router
	 */
	Router addResourceHandler(Class<? extends ResourceHandler> resourceHandlerClazz);

	/**
	 * Adds the resource handler.
	 *
	 * @param resourceHandler
	 *            the resource handler
	 * @return the router
	 */
	Router addResourceHandler(ResourceHandler resourceHandler);

	/**
	 * Gets the after route filters.
	 *
	 * @return the after route filters
	 */
	List<Filter> getAfterRouteFilters();

	/**
	 * Gets the before route filters.
	 *
	 * @return the before route filters
	 */
	List<Filter> getBeforeRouteFilters();

	/**
	 * Gets the error hanlder.
	 *
	 * @param status
	 *            the status
	 * @return the error hanlder
	 */
	Optional<ResourceHandler> getErrorHanlder(Status status);

	/**
	 * Gets the mappings.
	 *
	 * @return the mappings
	 */
	List<CommandData> getMappings();

	/**
	 * @return
	 */
	List<Resource<?>> getResources();

	/**
	 * @return
	 */
	List<Class<? extends Resource<?>>> getResourcesClazz();

	/**
	 * Gets the resource handlers.
	 *
	 * @return the resource handlers
	 */
	List<ResourceHandler> getResourceHandlers();

	/**
	 * Checks if is valid command.
	 *
	 * @param clazz
	 *            the clazz
	 * @return true, if is valid command
	 */
	boolean isValidCommand(Class<?> clazz);

	/**
	 * Registry after route filter.
	 *
	 * @param filter
	 *            the filter
	 * @return the router
	 */
	Router registryAfterRouteFilter(Filter filter);

	/**
	 * Registry before route filter.
	 *
	 * @param filter
	 *            the filter
	 * @return the router
	 */
	Router registryBeforeRouteFilter(Filter filter);
}