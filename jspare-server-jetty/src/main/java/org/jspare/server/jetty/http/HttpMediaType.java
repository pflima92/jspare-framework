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
package org.jspare.server.jetty.http;

import org.jspare.server.transport.Media;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Instantiates a new http media type.
 *
 * @param value
 *            the value
 */

/**
 * Instantiates a new http media type.
 *
 * @param value
 *            the value
 */
@AllArgsConstructor
public enum HttpMediaType implements Media {

	/** The application xml. */
	APPLICATION_XML("application/xml"),

	/** The application atom xml. */
	APPLICATION_ATOM_XML("application/atom+xml"),

	/** The application xhtml xml. */
	APPLICATION_XHTML_XML("application/xhtml+xml"),

	/** The application svg xml. */
	APPLICATION_SVG_XML("application/svg+xml"),

	/** The application javascript. */
	APPLICATION_JAVASCRIPT("application/javascript"),

	/** The application json. */
	APPLICATION_JSON("application/json"),

	/** The application form urlencoded. */
	APPLICATION_FORM_URLENCODED("application/x-www-form-urlencoded"),

	/** The multipart form data. */
	MULTIPART_FORM_DATA("multipart/form-data"),

	/** The application octet stream. */
	APPLICATION_OCTET_STREAM("application/octet-stream"),

	/** The text plain. */
	TEXT_PLAIN("text/plain"),

	/** The text xml. */
	TEXT_XML("text/xml"),

	/** The text html. */
	TEXT_HTML("text/html"),

	/** The text css. */
	TEXT_CSS("text/css"),

	/** The all. */
	ALL("*/*");

	/** The value. */
	@Getter
	private final String value;

}
