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
 * The Interface CacheControl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public interface CacheControl {

	/**
	 * Instantiates a new cache accessor.
	 *
	 * @param value
	 *            the value
	 */
	@AllArgsConstructor
	public enum CacheAccessor {

		/** The public. */
		PUBLIC("public"),
		/** The private. */
		PRIVATE("private");

		/**
		 * Gets the value.
		 *
		 * @return the value
		 */
		@Getter
		private final String value;
	}

	/**
	 * Of.
	 *
	 * @param accessor
	 *            the accessor
	 * @param duration
	 *            the duration
	 * @return the cache control
	 */
	static CacheControl of(CacheAccessor accessor, long duration) {

		return new CacheControl() {

			@Override
			public CacheAccessor getAccessor() {
				return accessor;
			}

			@Override
			public long getDuration() {
				return duration;
			}
		};
	}

	/**
	 * Gets the accessor.
	 *
	 * @return the accessor
	 */
	CacheAccessor getAccessor();

	/**
	 * Gets the duration.
	 *
	 * @return the duration
	 */
	long getDuration();

}