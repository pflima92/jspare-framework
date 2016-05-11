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
package org.jspare.core.serializer;

import java.lang.reflect.Type;

import org.jspare.core.container.Component;
import org.jspare.core.exception.SerializationException;

/**
 * The Interface Serializer.
 *
 * @author pflima
 * @since 05/10/2015
 */
@Component
public interface Serializer {

	/**
	 * Convert.
	 *
	 * @param <T>
	 *            the generic type
	 * @param targetType
	 *            the target type
	 * @param value
	 *            the value
	 * @return the t
	 */
	<T> T convert(Class<T> targetType, String value);

	/**
	 * From base64.
	 *
	 * @param <T>
	 *            the generic type
	 * @param data
	 *            the data
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromBase64(String data) throws SerializationException;

	/**
	 * From json.
	 *
	 * @param <T>
	 *            the generic type
	 * @param jsonElement
	 *            the json element
	 * @param clazz
	 *            the clazz
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(Object jsonElement, Class<T> clazz) throws SerializationException;

	/**
	 * From json.
	 *
	 * @param <T>
	 *            the generic type
	 * @param jsonElement
	 *            the json element
	 * @param type
	 *            the type
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(Object jsonElement, Type type) throws SerializationException;

	/**
	 * From json.
	 *
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the json
	 * @param clazz
	 *            the clazz
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(String json, Class<T> clazz) throws SerializationException;

	/**
	 * From json.
	 *
	 * @param <T>
	 *            the generic type
	 * @param json
	 *            the json
	 * @param type
	 *            the type
	 * @return the t
	 * @throws SerializationException
	 *             the serialization exception
	 */
	<T> T fromJSON(String json, Type type) throws SerializationException;

	/**
	 * Checks if is valid json.
	 *
	 * @param json
	 *            the json
	 * @return true, if is valid json
	 */
	boolean isValidJson(String json);

	/**
	 * Registry json converter.
	 *
	 * @param converter
	 *            the converter
	 * @return the serializer
	 * @throws SerializationException
	 *             the serialization exception
	 */
	Serializer registryJsonConverter(Object converter) throws SerializationException;

	/**
	 * To base64.
	 *
	 * @param instance
	 *            the instance
	 * @return the string
	 * @throws SerializationException
	 *             the serialization exception
	 */
	String toBase64(Object instance) throws SerializationException;

	/**
	 * To json.
	 *
	 * @param instance
	 *            the instance
	 * @return the string
	 * @throws SerializationException
	 *             the serialization exception
	 */
	String toJSON(Object instance) throws SerializationException;
}