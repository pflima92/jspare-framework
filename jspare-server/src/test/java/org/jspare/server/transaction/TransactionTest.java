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
package org.jspare.server.transaction;

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The Class TransactionTest.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class TransactionTest {

	/**
	 * Generate and validate tid test.
	 */
	@Test
	public void generateAndValidateTidTest() {

		String tid = my(TidGenerator.class).generate();

		assertEquals(true, my(TidGenerator.class).validate(tid));
		assertEquals(false, my(TidGenerator.class).validate("201603220122453422204672"));
	}

}
