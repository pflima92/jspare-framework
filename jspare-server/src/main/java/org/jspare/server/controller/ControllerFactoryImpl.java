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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.jspare.core.container.ContainerUtils;
import org.jspare.core.exception.NotImplementedException;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.ControllerInstantiationException;
import org.jspare.server.exception.InvalidControllerException;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The Class ControllerFactoryImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ControllerFactoryImpl implements ControllerFactory {

	/** The Constant instances. */
	private static final Map<ControllerKey, Object> instances = new HashMap<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.controller.ControllerFactory#dispatch(java.lang.Class,
	 * org.jspare.server.Request, org.jspare.server.Response)
	 */
	@Override
	public void dispatch(Class<?> cmdClazz, Request request, Response response) {

		throw new NotImplementedException("Dispatch for controller and method specific.");
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.controller.ControllerFactory#instantiate(java.lang.
	 * Class)
	 */
	@Override
	public Object instantiate(Class<?> cmdClazz) {

		ScopeType scope = cmdClazz.isAnnotationPresent(Scope.class) ? cmdClazz.getAnnotation(Scope.class).value() : ScopeType.REQUEST;

		ControllerKey key = new ControllerKey(cmdClazz, scope);
		if (!instances.containsKey(key)) {
			try {

				Constructor<?> constructor = cmdClazz.getConstructor();

				if (constructor == null) {
					throw new InvalidControllerException(String.format("Cannot found default Constructor for [%s]"));
				}

				Object instance = constructor.newInstance();
				ContainerUtils.processInjection(cmdClazz, instance);

				if (!scope.equals(ScopeType.REQUEST)) {
					instances.put(key, instance);
				}

				return instance;

			} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {

				throw new ControllerInstantiationException(e);
			}
		}

		return instances.get(cmdClazz);
	}
}

@Data
@AllArgsConstructor
@EqualsAndHashCode
class ControllerKey {

	private Class<?> clazz;
	private ScopeType scope;
}