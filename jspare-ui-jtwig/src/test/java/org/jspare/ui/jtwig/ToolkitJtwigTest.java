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
package org.jspare.ui.jtwig;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jspare.core.container.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.ui.view.Template;
import org.jspare.ui.view.UiToolkit;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * The Class ToolkitJtwigTest.
 *
 * @author pflima
 * @since 22/04/2016
 */
public class ToolkitJtwigTest extends MySupport {

	/**
	 * Pre setup.
	 */
	@BeforeClass
	public static void preSetup() {

		new JtwigBundle().registryComponents();
	}

	/** The ui tookit. */
	@Inject
	private UiToolkit uiTookit;

	/**
	 * Apply test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void applyTest() throws Exception {

		String resource = "apply";

		Map<String, Object> values = new HashMap<>();
		values.put("name", "jspare");

		Template template = uiTookit.loadTemplate(resource);
		String output = template.apply(values);
		Assert.assertEquals("<p>jspare</p>", output);
	}

	/**
	 * Assign test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void assignTest() throws Exception {

		String resource = "assign";

		Map<String, Object> values = new HashMap<>();

		Template template = uiTookit.loadTemplate(resource);
		String output = template.apply(values);
		Assert.assertEquals("1</br>2", output);
	}

	/**
	 * Extends test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void extendsTest() throws Exception {

		String resource = "extends";

		Map<String, Object> values = new HashMap<>();
		values.put("template", "general");
		values.put("page", "jspare-framework");

		Template template = uiTookit.loadTemplate(resource);
		String output = template.apply(values);
		Assert.assertEquals("jspare-framework", StringUtils.remove(output, " "));
	}

	/**
	 * Import test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void importTest() throws Exception {

		String resource = "import";

		Map<String, Object> values = new HashMap<>();
		values.put("name", "jspare");
		values.put("footer", "powered-by-jtwig");

		Template template = uiTookit.loadTemplate(resource);
		String output = template.apply(values);
		Assert.assertEquals("<p>jspare<b>powered-by-jtwig</b></p>", output);
	}

	/**
	 * Functions test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void functionsTest() throws Exception {

		String resource = "functions";

		Map<String, Object> values = new HashMap<>();
		values.put("model", new Model("teste"));

		Template template = uiTookit.loadTemplate(resource);
		String output = template.apply(values);
		Assert.assertEquals("teste-prefix teste", output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Data

	/**
	 * Instantiates a new model.
	 *
	 * @param name
	 *            the name
	 */
	@AllArgsConstructor
	public class Model {

		/** The name. */
		private String name;

		/**
		 * Gets the custom name.
		 *
		 * @param prefix
		 *            the prefix
		 * @return the custom name
		 */
		public String getCustomName(String prefix) {

			return prefix + " " + name;
		}
	}

}
