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
package org.jspare.server.content;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * (non-Javadoc)
 * 
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new data part.
 *
 * @param reader
 *            the reader
 * @param entity
 *            the entity
 * @param name
 *            the name
 * @param contentDisposition
 *            the content disposition
 */
@AllArgsConstructor
public class DataPart {

	/** The reader. */
	private final Reader reader;

	/** The entity. */
	private Object entity;

	/** The name. */
	private String name;

	/** The content disposition. */
	private ContentDisposition contentDisposition;

	/**
	 * Gets the entity as.
	 *
	 * @param <T>
	 *            the generic type
	 * @param clazz
	 *            the clazz
	 * @return the entity as
	 */
	public <T> T getEntityAs(Class<?> clazz) {

		return reader.read(clazz);
	}
}