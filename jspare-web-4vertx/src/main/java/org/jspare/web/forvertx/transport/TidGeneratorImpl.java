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
package org.jspare.web.forvertx.transport;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang.StringUtils;

/**
 * The Class TidGeneratorImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class TidGeneratorImpl implements TidGenerator {

	/** The Constant TIMESTAMP_FORMAT. */
	private static final String TIMESTAMP_FORMAT = "yyyyMMddHHmmss";

	/** The Constant MILLI_OF_SECOND. */
	private static final int MILLI_OF_SECOND = 3;

	/** The Constant MIN. */
	private static final long MIN = 100000l;

	/** The Constant MAX. */
	private static final long MAX = 989898l;

	/** The dtf. */
	private DateTimeFormatter dtf = new DateTimeFormatterBuilder().appendPattern(TIMESTAMP_FORMAT)
			.appendValue(ChronoField.MILLI_OF_SECOND, MILLI_OF_SECOND).toFormatter();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.TidGenerator#generate()
	 */
	@Override
	public String generate() {

		StringBuilder tid = new StringBuilder();
		tid.append(LocalDateTime.now().format(dtf));
		tid.append(StringUtils.leftPad(String.valueOf(ThreadLocalRandom.current().nextLong(MIN, MAX)), 6, "0"));
		tid.append(calculateVerifyDigit(tid.toString()));
		return tid.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TidGenerator#validate(java.lang.String)
	 */
	@Override
	public boolean validate(final String tid) {

		try {
			if (StringUtils.isEmpty(tid)) {
				return false;
			}

			String prefix = tid.substring(0, TIMESTAMP_FORMAT.length() + MILLI_OF_SECOND);
			if (!LocalDateTime.parse(prefix, dtf).isBefore(LocalDateTime.now())) {
				return false;
			}

			long sufix = Long.parseLong(tid.substring(TIMESTAMP_FORMAT.length() + MILLI_OF_SECOND, tid.length() - 1));
			if (sufix <= MIN || sufix >= MAX) {
				return false;
			}
			int verifyDigit = Integer.parseInt(String.valueOf(tid.charAt(tid.length() - 1)));

			return validateVerifyDigit(tid, verifyDigit);

		} catch (Exception e) {

			return false;
		}
	}

	/**
	 * Calculate verify digit.
	 *
	 * @param preTid
	 *            the pre tid
	 * @return the int
	 */
	private int calculateVerifyDigit(final String preTid) {

		int calc = 0;
		for (int i = 0, n = preTid.length(); i < n; i++) {
			if (i % 2 != 0) {
				calc += preTid.charAt(i);
			}
		}
		calc *= 3;
		for (int p = 0, n = preTid.length(); p < n; p++) {
			if (p % 2 == 0) {
				calc += preTid.charAt(p);
			}
		}

		int d = 0;
		while (((calc + d) % 10) != 0) {
			d++;
		}
		return d;
	}

	/**
	 * Validate verify digit.
	 *
	 * @param tid
	 *            the tid
	 * @param verifyDigit
	 *            the verify digit
	 * @return true, if successful
	 */
	private boolean validateVerifyDigit(final String tid, final int verifyDigit) {

		String preTid = tid.substring(0, tid.length() - 1);
		return calculateVerifyDigit(preTid) == verifyDigit;
	}
}
