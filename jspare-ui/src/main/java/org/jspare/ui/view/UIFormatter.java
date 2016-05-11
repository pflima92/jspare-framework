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
package org.jspare.ui.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang.StringUtils;

/**
 * The Class UIFormatter.
 *
 * @author pflima
 * @since 10/05/2016
 */
public class UIFormatter {

	/**
	 * Format local date time.
	 *
	 * @param value
	 *            the value
	 * @param pattern
	 *            the pattern
	 * @return the string
	 */
	public static String formatLocalDateTime(LocalDateTime value, String pattern) {

		if (value == null || StringUtils.isEmpty(pattern)) {

			return StringUtils.EMPTY;
		}

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
		return dateTimeFormatter.format(value);
	}

}
