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
package org.jspare.core.exception;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

/**
 * The Class ExpiredTokenExceptionTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class ExpiredTokenExceptionTest {

	/**
	 * Expired token exception message test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void expiredTokenExceptionMessageTest() throws Exception {
		Calendar calendar = Calendar.getInstance();
		try {
			throw new ExpiredTokenException("12345", "333", calendar.getTimeInMillis());
		} catch (ExpiredTokenException e) {
			Assert.assertEquals("Token expired for the user id: 12345 - group id: 333, the token wasa used for the lastTime on "
					+ (new Date(calendar.getTimeInMillis())), e.getMessage());
		}
	}
}
