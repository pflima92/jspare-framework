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

import java.util.Map;

import org.jspare.server.exception.RenderableException;
import org.jspare.ui.view.Template;
import org.jtwig.JtwigModel;
import org.jtwig.exceptions.JtwigException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Instantiates a new jtwig template.
 *
 * @param content
 *            the content
 */

@Slf4j

/**
 * Instantiates a new jtwig template.
 *
 * @param content
 *            the content
 */
@AllArgsConstructor
public class JtwigTemplate implements Template {

	/** The content. */
	private org.jtwig.JtwigTemplate content;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.Template#apply(java.util.Map)
	 */
	@Override
	public String apply(Map<String, Object> viewValues) throws RenderableException {

		JtwigModel modelMap = JtwigModel.newModel();
		viewValues.entrySet().forEach(es -> modelMap.with(es.getKey(), es.getValue()));

		try {

			return content.render(modelMap);
		} catch (JtwigException e) {

			log.error(e.getMessage(), e);
			throw new RenderableException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.Template#setContent(java.lang.Object)
	 */
	@Override
	public void setContent(Object content) {
		this.content = (org.jtwig.JtwigTemplate) content;
	}
}