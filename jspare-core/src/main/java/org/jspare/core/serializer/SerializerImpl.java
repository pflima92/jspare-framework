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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.Base64;

import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.core.exception.SerializationException;
import org.jspare.core.serializer.converter.LocalDateConverter;
import org.jspare.core.serializer.converter.LocalDateTimeConverter;
import org.jspare.core.serializer.converter.LocalTimeConverter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.TypeAdapterFactory;

import lombok.Cleanup;

/**
 * The Class SerializerImpl.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class SerializerImpl implements Serializer {

	/** The builder. */
	private static GsonBuilder builder = new GsonBuilder();

	/**
	 * Instantiates a new serializer impl.
	 */
	public SerializerImpl() {
		try {

			registryJsonConverter(new LocalDateConverter());
			registryJsonConverter(new LocalTimeConverter());
			registryJsonConverter(new LocalDateTimeConverter());
		} catch (Exception e) {

			throw new InfraRuntimeException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#convert(java.lang.Class,
	 * java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T convert(Class<T> targetType, String value) {
		PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
		editor.setAsText(value);
		return (T) editor.getValue();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#fromBase64(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromBase64(String data) throws SerializationException {
		try {
			@Cleanup
			ByteArrayInputStream bin = new ByteArrayInputStream(Base64.getDecoder().decode(data));
			@Cleanup
			ObjectInputStream in = new ObjectInputStream(bin);
			return (T) in.readObject();

		} catch (IOException | ClassNotFoundException e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#fromJSON(java.lang.Object,
	 * java.lang.Class)
	 */
	@Override
	public <T> T fromJSON(Object jsonObject, Class<T> clazz) throws SerializationException {
		try {

			return getGson().fromJson((JsonElement) jsonObject, clazz);
		} catch (Throwable e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#fromJSON(java.lang.Object,
	 * java.lang.reflect.Type)
	 */
	@Override
	public <T> T fromJSON(Object jsonObject, Type type) throws SerializationException {
		try {

			return getGson().fromJson((JsonElement) jsonObject, type);
		} catch (Throwable e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#fromJSON(java.lang.String,
	 * java.lang.Class)
	 */
	@Override
	public <T> T fromJSON(String json, Class<T> clazz) throws SerializationException {
		try {

			return getGson().fromJson(json, clazz);
		} catch (Throwable e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#fromJSON(java.lang.String,
	 * java.lang.reflect.Type)
	 */
	@Override
	public <T> T fromJSON(String json, Type type) throws SerializationException {
		try {

			return getGson().fromJson(json, type);
		} catch (Throwable e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#isValidJson(java.lang.String)
	 */
	@Override
	public boolean isValidJson(String json) {

		try {

			getGson().fromJson(json, Object.class);
			return true;
		} catch (Exception e) {

			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.serializer.Serializer#registryJsonConverter(java.lang.
	 * Object)
	 */
	@Override
	public Serializer registryJsonConverter(Object converter) throws SerializationException {
		try {

			if (converter instanceof JsonConverter) {
				JsonConverter jsonConverter = (JsonConverter) converter;
				builder.registerTypeAdapter(jsonConverter.getType(), jsonConverter.getAdapter());
				return this;
			}
			if (converter instanceof TypeAdapterFactory) {

				builder.registerTypeAdapterFactory((TypeAdapterFactory) converter);
				return this;
			}

			throw new Exception();
		} catch (Exception e) {

			throw new SerializationException(String.format("Cannot register converter [%s].", converter.getClass().getName()), e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#toBase64(java.lang.Object)
	 */
	@Override
	public String toBase64(Object instance) throws SerializationException {
		try {
			@Cleanup
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			@Cleanup
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(instance);
			return Base64.getEncoder().encodeToString(bout.toByteArray());

		} catch (IOException e) {
			throw new SerializationException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.serializer.Serializer#toJSON(java.lang.Object)
	 */
	@Override
	public String toJSON(Object instance) throws SerializationException {
		try {

			return getGson().toJson(instance);
		} catch (Throwable e) {
			throw new SerializationException(e);
		}
	}

	/**
	 * Gets the gson.
	 *
	 * @return the gson
	 */
	private Gson getGson() {

		return builder.create();
	}
}