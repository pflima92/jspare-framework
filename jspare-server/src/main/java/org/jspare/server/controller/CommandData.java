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
package org.jspare.server.controller;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.jspare.server.CacheControl;
import org.jspare.server.exception.InvalidControllerException;
import org.jspare.server.filter.Filter;
import org.jspare.server.filter.RequestFilter;
import org.jspare.server.filter.ResponseFilter;
import org.jspare.server.mapping.Cache;
import org.jspare.server.mapping.Command;
import org.jspare.server.mapping.Start;
import org.jspare.server.mapping.Type;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class CommandData.
 *
 * @author pflima
 * @since 22/04/2016
 */
/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new command data.
 *
 * @param cmdClazz
 *            the cmd clazz
 * @param method
 *            the method
 * @param command
 *            the command
 * @param type
 *            the type
 * @param afterFilters
 *            the after filters
 * @param beforeFilters
 *            the before filters
 * @param targetMedias
 *            the target medias
 * @param cacheControl
 *            the cache control
 * @param startCommand
 *            the start command
 * @param apified
 *            the apified
 */

/**
 * Instantiates a new command data.
 *
 * @param cmdClazz
 *            the cmd clazz
 * @param method
 *            the method
 * @param command
 *            the command
 * @param type
 *            the type
 * @param afterFilters
 *            the after filters
 * @param beforeFilters
 *            the before filters
 * @param targetMedias
 *            the target medias
 * @param cacheControl
 *            the cache control
 * @param startCommand
 *            the start command
 * @param apified
 *            the apified
 */

/**
 * Instantiates a new command data.
 *
 * @param cmdClazz
 *            the cmd clazz
 * @param method
 *            the method
 * @param command
 *            the command
 * @param type
 *            the type
 * @param afterFilters
 *            the after filters
 * @param beforeFilters
 *            the before filters
 * @param targetMedias
 *            the target medias
 * @param cacheControl
 *            the cache control
 * @param startCommand
 *            the start command
 * @param apified
 *            the apified
 */

/**
 * Instantiates a new command data.
 *
 * @param cmdClazz
 *            the cmd clazz
 * @param method
 *            the method
 * @param command
 *            the command
 * @param type
 *            the type
 * @param afterFilters
 *            the after filters
 * @param beforeFilters
 *            the before filters
 * @param targetMedias
 *            the target medias
 * @param cacheControl
 *            the cache control
 * @param startCommand
 *            the start command
 * @param apified
 *            the apified
 */

/**
 * Instantiates a new command data.
 *
 * @param cmdClazz
 *            the cmd clazz
 * @param method
 *            the method
 * @param command
 *            the command
 * @param type
 *            the type
 * @param afterFilters
 *            the after filters
 * @param beforeFilters
 *            the before filters
 * @param targetMedias
 *            the target medias
 * @param cacheControl
 *            the cache control
 * @param startCommand
 *            the start command
 * @param apified
 *            the apified
 */
@AllArgsConstructor
public class CommandData implements Cloneable {

	/** The cmd clazz. */
	private final Class<?> cmdClazz;

	/** The method. */
	private final Method method;

	/** The command. */
	private String command;

	/** The type. */
	private Type type;

	/** The after filters. */
	private List<Filter> afterFilters = new ArrayList<>();

	/** The before filters. */
	private List<Filter> beforeFilters = new ArrayList<>();

	/** The target medias. */
	private Optional<String[]> targetMedias = Optional.empty();

	/** The cache control. */
	private Optional<CacheControl> cacheControl = Optional.empty();

	/** The start command. */
	private boolean startCommand;

	/** The apified. */
	private boolean apified;

	/**
	 * Instantiates a new command data.
	 *
	 * @param cmdClazz
	 *            the cmd clazz
	 * @param method
	 *            the method
	 */
	public CommandData(Class<?> cmdClazz, Method method) {
		this.cmdClazz = cmdClazz;
		this.method = method;
		buildCommandData();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CommandData clone() {

		try {

			return (CommandData) super.clone();

		} catch (CloneNotSupportedException e) {

			return this;
		}
	}

	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	public String getKey() {

		return String.format("%s:%s:%s", cmdClazz.getName(), method.getName(), command);
	}

	/**
	 * Builds the command data.
	 */
	private void buildCommandData() {

		Command command = method.getAnnotation(Command.class);

		this.command = StringUtils.isEmpty(command.value()) ? method.getName() : command.value();

		this.type = command.type();

		if (method.isAnnotationPresent(ResponseFilter.class)) {

			registryFilters(afterFilters, method.getAnnotation(ResponseFilter.class).value());
		}
		if (method.isAnnotationPresent(RequestFilter.class)) {

			registryFilters(beforeFilters, method.getAnnotation(RequestFilter.class).value());
		}
		if (method.isAnnotationPresent(Cache.class)) {

			Cache cache = method.getAnnotation(Cache.class);
			this.cacheControl = Optional.of(CacheControl.of(cache.accessor(), cache.duration()));
		}
		if (method.isAnnotationPresent(org.jspare.server.mapping.Media.class)) {

			org.jspare.server.mapping.Media media = method.getAnnotation(org.jspare.server.mapping.Media.class);
			this.targetMedias = Optional.of(media.value());
		}

		startCommand = method.isAnnotationPresent(Start.class);
	}

	/**
	 * Registry filters.
	 *
	 * @param filters
	 *            the filters
	 * @param filtersClazz
	 *            the filters clazz
	 */
	private void registryFilters(List<Filter> filters, Class<?>[] filtersClazz) {

		for (int i = 0; i < filtersClazz.length; i++) {

			try {

				filters.add((Filter) filtersClazz[i].newInstance());
			} catch (Exception e) {

				throw new InvalidControllerException(String.format("Cannot instantiate Filter [%s]", filtersClazz[i].getName()));
			}
		}
	}
}