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
package org.jspare.server.basic;

import java.util.List;
import java.util.Optional;

import org.jspare.server.Router;
import org.jspare.server.Status;
import org.jspare.server.apification.Apification;
import org.jspare.server.controller.CommandData;
import org.jspare.server.filter.Filter;
import org.jspare.server.handler.ResourceHandler;

/**
 * The Class BasicRouter.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class BasicRouter implements Router {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addApificationResource(org.jspare.server.
	 * apification.Apification)
	 */
	@Override
	public Router addApificationResource(Apification apification) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addApificationResource(java.lang.Class)
	 */
	@Override
	public Router addApificationResource(Class<? extends Apification> apificationClazz) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addErrorHandler(org.jspare.server.Status,
	 * java.lang.Class)
	 */
	@Override
	public Router addErrorHandler(Status status, Class<? extends ResourceHandler> errorHandlerClazz) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addErrorHandler(org.jspare.server.Status,
	 * org.jspare.server.handler.ResourceHandler)
	 */
	@Override
	public Router addErrorHandler(Status status, ResourceHandler errorHandler) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addMapping(java.lang.Class)
	 */
	@Override
	public Router addMapping(Class<?> cmdClazz) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addResourceHandler(java.lang.Class)
	 */
	@Override
	public Router addResourceHandler(Class<? extends ResourceHandler> resourceHandlerClazz) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.Router#addResourceHandler(org.jspare.server.handler.
	 * ResourceHandler)
	 */
	@Override
	public Router addResourceHandler(ResourceHandler resourceHandler) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getAfterRouteFilters()
	 */
	@Override
	public List<Filter> getAfterRouteFilters() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getBeforeRouteFilters()
	 */
	@Override
	public List<Filter> getBeforeRouteFilters() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getErrorHanlder(org.jspare.server.Status)
	 */
	@Override
	public Optional<ResourceHandler> getErrorHanlder(Status status) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getMappings()
	 */
	@Override
	public List<CommandData> getMappings() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getResourceHandlers()
	 */
	@Override
	public List<ResourceHandler> getResourceHandlers() {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#isValidCommand(java.lang.Class)
	 */
	@Override
	public boolean isValidCommand(Class<?> clazz) {

		return true;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#registryAfterRouteFilter(org.jspare.server.
	 * filter.Filter)
	 */
	@Override
	public Router registryAfterRouteFilter(Filter filter) {

		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.Router#registryBeforeRouteFilter(org.jspare.server.
	 * filter.Filter)
	 */
	@Override
	public Router registryBeforeRouteFilter(Filter filter) {

		return null;
	}

}
