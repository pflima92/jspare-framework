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
package org.jspare.server.jetty;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.server.Router;
import org.jspare.server.controller.CommandData;
import org.jspare.server.exception.InvalidControllerException;
import org.jspare.server.filter.Filter;
import org.jspare.server.handler.ResourceHandler;
import org.jspare.server.mapping.Mapping;
import org.jspare.server.mapping.Type;
import org.jspare.server.transport.Status;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyRouter implements Router {

	/** The Constant CONTROLLER_SUFIX. */
	private static final String CONTROLLER_SUFIX = "Controller";

	/** The Constant START_PATTERN. */
	private static final String START_PATTERN = "/";

	/** The command map. */
	private final Map<String, CommandData> commandMap = new ConcurrentHashMap<>();

	/** The error handler map. */
	private final Map<Status, ResourceHandler> errorHandlerMap = new ConcurrentHashMap<>();

	/** The resource handler map. */

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getResourceHandlers()
	 */
	@Getter
	private final List<ResourceHandler> resourceHandlers = new ArrayList<>();

	/** The before route filters. */
	private final List<Filter> beforeRouteFilters = new ArrayList<>();

	/** The after route filters. */
	private final List<Filter> afterRouteFilters = new ArrayList<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addErrorHandler(org.jspare.server.Status,
	 * java.lang.Class)
	 */
	@Override
	public Router addErrorHandler(Status status, Class<? extends ResourceHandler> errorHandlerClazz) {

		try {

			ResourceHandler errorHandler = errorHandlerClazz.newInstance();
			this.errorHandlerMap.put(status, errorHandler);
			return this;
		} catch (Exception e) {

			String errorMessage = String.format("Cannot instantiate Error Hanlder mapped by [%s]", errorHandlerClazz.getName());
			throw new InfraRuntimeException(errorMessage, e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addErrorHandler(org.jspare.server.Status,
	 * org.jspare.server.handler.ResourceHandler)
	 */
	@Override
	public Router addErrorHandler(Status status, ResourceHandler errorHandler) {

		this.errorHandlerMap.put(status, errorHandler);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addMapping(java.lang.Class)
	 */
	@Override
	public Router addMapping(Class<?> cmdClazz) {

		buildAndRegistryCommandMap(cmdClazz);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#addResourceHandler(java.lang.Class)
	 */
	@Override
	public Router addResourceHandler(Class<? extends ResourceHandler> resourceHandlerClazz) {

		try {

			ResourceHandler resourceHandler = resourceHandlerClazz.newInstance();
			return addResourceHandler(resourceHandler);
		} catch (Exception e) {

			String errorMessage = String.format("Cannot instantiate Error Hanlder mapped by [%s]", resourceHandlerClazz.getName());
			throw new InfraRuntimeException(errorMessage, e);
		}
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

		log.info("Founded mapping: TYPE: [{}] ALIAS: [{}]", resourceHandler.getTypes(), resourceHandler.getCommand());

		this.resourceHandlers.add(resourceHandler);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getAfterRouteFilters()
	 */
	@Override
	public List<Filter> getAfterRouteFilters() {

		return this.afterRouteFilters;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getBeforeRouteFilters()
	 */
	@Override
	public List<Filter> getBeforeRouteFilters() {

		return this.beforeRouteFilters;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getErrorHanlder(org.jspare.server.Status)
	 */
	@Override
	public Optional<ResourceHandler> getErrorHanlder(Status status) {

		return Optional.ofNullable(errorHandlerMap.get(status));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#getMappings()
	 */
	@Override
	public List<CommandData> getMappings() {

		return commandMap.values().stream().collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#isValidCommand(java.lang.Class)
	 */
	@Override
	public boolean isValidCommand(Class<?> clazz) {

		return clazz.getName().endsWith(CONTROLLER_SUFIX);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Router#registryAfterRouteFilter(org.jspare.server.
	 * filter.Filter)
	 */
	@Override
	public Router registryAfterRouteFilter(Filter filter) {

		this.afterRouteFilters.add(filter);
		return this;
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

		this.beforeRouteFilters.add(filter);
		return this;
	}

	/**
	 * Adds the command.
	 *
	 * @param cmdData
	 *            the cmd data
	 */
	private void addCommand(CommandData cmdData) {

		for (Type type : cmdData.getTypes()) {

			log.info("Founded mapping: TYPE: [{}] ALIAS: [{}]", type, cmdData.getCommand());
			this.commandMap.put(cmdData.getKey(), cmdData);
		}
	}

	/**
	 * Builds the and registry command map.
	 *
	 * @param cmdClazz
	 *            the cmd clazz
	 */
	private void buildAndRegistryCommandMap(Class<?> cmdClazz) {

		if (!isValidCommand(cmdClazz)) {

			throw new InvalidControllerException(String.format("Cannot find name Controller on class [%s]", cmdClazz.getName()));
		}

		for (

		Method method : cmdClazz.getDeclaredMethods()) {

			if (method.isAnnotationPresent(Mapping.class)) {

				CommandData cmdData = new CommandData(cmdClazz, method);
				addCommand(cmdData);

				if (cmdData.isStartCommand() && !cmdData.getCommand().equals(StringUtils.EMPTY)
						&& !cmdData.getCommand().equals(START_PATTERN)) {

					CommandData startCommand = cmdData.clone();
					startCommand.setCommand(START_PATTERN);
					addCommand(startCommand);
				}
			}
		}
	}
}