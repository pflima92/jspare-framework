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

import java.util.Date;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * (non-Javadoc)
 *
 * @see java.lang.Object#toString()
 */
@Data

/**
 * Instantiates a new content disposition.
 *
 * @param type
 *            the type
 * @param parameters
 *            the parameters
 * @param fileName
 *            the file name
 * @param creationDate
 *            the creation date
 * @param modificationDate
 *            the modification date
 * @param readDate
 *            the read date
 * @param size
 *            the size
 */
@AllArgsConstructor
public class ContentDisposition {

	/** The type. */
	private final CharSequence type;

	/** The parameters. */
	private final Map<String, String> parameters;

	/** The file name. */
	private String fileName;

	/** The creation date. */
	private Date creationDate;

	/** The modification date. */
	private Date modificationDate;

	/** The read date. */
	private Date readDate;

	/** The size. */
	private long size;
}