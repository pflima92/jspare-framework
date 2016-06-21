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

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.jspare.core.container.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.provider.ApplicationProvider;

import com.google.gson.internal.LinkedTreeMap;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Instantiates a new context.
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

/**
 * Instantiates a new context.
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

/**
 * Instantiates a new context.
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

/**
 * Instantiates a new context.
 *
 * @param applicationProvider
 *            the application provider
 * @param applicationName
 *            the application name
 * @param version
 *            the version
 * @param parameters
 *            the parameters
 * @param components
 *            the registry components
 * @param providers
 *            the providers
 */
@AllArgsConstructor
public class Context extends MySupport {

	/**
	 * Empty.
	 *
	 * @return the context
	 */
	public static Context empty() {

		return new Context();
	}

	/** The application provider. */
	@Inject
	private ApplicationProvider applicationProvider;

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */

	/**
	 * Gets the application name.
	 *
	 * @return the application name
	 */
	@Getter
	private String applicationName;

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	@Getter
	private String version;

	/** The parameters. */
	private Map<String, Object> parameters;

	/** The registry components. */
	private List<String> components;

	/** The providers. */
	private Map<String, Map<String, String>> providers;

	/**
	 * Instantiates a new context.
	 */
	protected Context() {

	}

	/**
	 * Contains parameter.
	 *
	 * @param key
	 *            the key
	 * @return true, if successful
	 */
	public boolean containsParameter(String key) {
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		return parameters.containsKey(key);
	}

	/**
	 * Gets the parameter.
	 *
	 * @param key
	 *            the key
	 * @return the parameter
	 */
	public Object getParameter(String key) {
		if (parameters == null) {
			parameters = new HashMap<>();
		}
		return parameters.get(key);
	}

	/**
	 * Gets the parameter.
	 *
	 * @param <T>
	 *            the generic type
	 * @param key
	 *            the key
	 * @param clazzType
	 *            the clazz type
	 * @return the parameter
	 */
	@SuppressWarnings("unchecked")
	public <T> T getParameter(String key, Class<T> clazzType) {

		Object resultObject = getParameter(key);

		if (resultObject instanceof LinkedTreeMap) {

			try {

				T instance = clazzType.newInstance();

				for (Entry<String, Object> entry : ((LinkedTreeMap<String, Object>) resultObject).entrySet()) {

					Field field = clazzType.getDeclaredField(entry.getKey());
					field.setAccessible(true);

					Object value = entry.getValue();
					// Force cast if linked tree map use wrong type... XXX
					if (!entry.getValue().getClass().equals(field.getType())) {
						// XXX Quick Fix
						if (value instanceof Double && field.getType().equals(Integer.class)) {
							value = Double.valueOf(value.toString()).intValue();
						}
						value = field.getType().cast(value);
					}

					field.set(instance, value);
				}

				return instance;
			} catch (Exception e) {

				throw new EnvironmentException(
						String.format("Error on cast Context for target class [%s] - Throw [%s]", clazzType.getName(), e.getMessage()));
			}
		}

		return clazzType.cast(resultObject);
	}

	/**
	 * Gets the providers.
	 *
	 * @return the providers
	 */
	protected Optional<Map<String, Map<String, String>>> getProviders() {

		return Optional.ofNullable(providers);
	}

	/**
	 * Gets the registry components.
	 *
	 * @return the registry components
	 */
	protected Optional<List<String>> getRegistryComponents() {

		return Optional.ofNullable(components);
	}
}
