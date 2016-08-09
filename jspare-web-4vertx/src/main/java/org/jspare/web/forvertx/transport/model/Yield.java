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
package org.jspare.web.forvertx.transport.model;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class Yield.
 *
 * @author pflima
 * @since 10/05/2016
 */
@Data
@AllArgsConstructor
public class Yield {

	/** The tid. */
	private String tid;

	/** The bind. */
	private String bind;

	/** The context. */
	private Map<String, Object> context;

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public Map<String, Object> getContext() {

		if (context == null) {

			context = new HashMap<>();
		}
		return context;
	}
}
