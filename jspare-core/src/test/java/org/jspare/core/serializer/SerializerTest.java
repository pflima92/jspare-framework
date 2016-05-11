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
package org.jspare.core.serializer;

import static org.jspare.core.container.Environment.my;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import org.junit.Assert;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class SerializerTest.
 *
 * @author pflima
 * @since 05/10/2015
 */
public class SerializerTest {

	/**
	 * Convert test.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Test
	public void convertTest() throws Throwable {

		Serializer s = my(Serializer.class);

		long result01 = s.convert(Long.class, "12345");
		Assert.assertEquals(result01, 12345);

		double result02 = s.convert(Double.class, "123.45");
		Assert.assertEquals(result02, 123.45, 0.0001);

		boolean result03 = s.convert(Boolean.class, "true");
		Assert.assertTrue(result03);

		boolean result04 = s.convert(Boolean.class, "false");
		Assert.assertFalse(result04);

	}

	/**
	 * Checks if is valid json test.
	 */
	@Test
	public void isValidJsonTest() {

		String validJson = "{ \"name\" : \"jspare\"}", invalidJson = "name = jspare";

		assertTrue(my(Serializer.class).isValidJson(validJson));

		assertTrue(!my(Serializer.class).isValidJson(invalidJson));
	}

	/**
	 * Serialization test.
	 *
	 * @throws Throwable
	 *             the throwable
	 */
	@Test
	public void serializationTest() throws Throwable {

		Serializer s = my(Serializer.class);

		ToSerialize bean1 = new ToSerialize("A", "B");
		String data = s.toBase64(bean1);
		ToSerialize bean2 = s.fromBase64(data);

		Assert.assertEquals(bean1, bean2);
	}
}

@Data
@AllArgsConstructor
class ToSerialize implements Serializable {

	private static final long serialVersionUID = 1L;

	private transient String a01;
	private final String a02;
}