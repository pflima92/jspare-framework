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
package org.jspare.server.transport;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Enum Status.
 *
 * @author pflima
 * @since 30/03/2016
 */
@AllArgsConstructor
public enum Status {

	CONTINUE(100),
	OK(200),
	NO_CONTENT(204),
	RESET_CONTENT(205),
	PARTIAL_CONTENT(206),
	MOVED_PERMANENTLY(301),
	MOVED_TEMPORARILY(302),
	NOT_MODIFIED(304),
	USE_PROXY(305),
	TEMPORARY_REDIRECT(307),
	BAD_REQUEST(400),
	UNAUTHORIZED(401),
	PAYMENT_REQUIRED(402),
	FORBIDDEN(403),
	NOT_FOUND(404),
	METHOD_NOT_ALLOWED(405),
	NOT_ACCEPTABLE(406),
	PROXY_AUTHENTICATION_REQUIRED(407),
	REQUEST_TIMEOUT(408),
	CONFLICT(409),
	PRECONDITION_FAILED(412),
	UNSUPPORTED_MEDIA_TYPE(415),
	INTERNAL_SERVER_ERROR(500),
	NOT_IMPLEMENTED(501),
	BAD_GATEWAY(502),
	SERVICE_UNAVAILABLE(503),
	YIELD(600);

	@Getter
	private final int code;

	public String asString() {

		return toString();
	}
}
