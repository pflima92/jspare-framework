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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.container.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.server.Request;
import org.jspare.server.exception.LoadTemplateException;
import org.jspare.server.exception.RenderableException;

import lombok.Getter;

/**
 * The Class ViewImpl.
 *
 * @author pflima
 * @since 22/04/2016
 */
public class ViewImpl extends MySupport implements View {

	/** The ui tookit. */
	@Inject
	private UiToolkit uiTookit;

	/** The values. */
	private Map<String, Object> values = new HashMap<>();

	/**
	 * Gets the route.
	 *
	 * @return the route
	 */

	/**
	 * Gets the route.
	 *
	 * @return the route
	 */
	@Getter
	private String route;

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */

	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	@Getter
	private String content;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.View#add(java.util.Map)
	 */
	@Override
	public View add(Map<String, Object> values) {
		this.values.putAll(values);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.View#add(java.lang.String, java.lang.Object)
	 */
	@Override
	public View add(String key, Object value) {
		this.values.put(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.View#content(java.lang.String)
	 */
	@Override
	public View content(String content) {
		this.content = content;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.View#remove(java.lang.String)
	 */
	@Override
	public View remove(String key) {
		values.remove(key);
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Renderable#render(org.jspare.server.Request)
	 */
	@Override
	public String render(Request request) throws RenderableException {

		String result = content;

		if (StringUtils.isEmpty(content) && StringUtils.isEmpty(route)) {

			throw new RenderableException("Route and Content cannot be null");
		}

		if (result == null) {

			try {

				Template template = uiTookit.loadTemplate(route);
				result = uiTookit.compile(template, values);

			} catch (LoadTemplateException e) {

				throw new RenderableException("Route invalid");
			}
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.View#route(java.lang.String)
	 */
	@Override
	public View route(String route) {
		this.route = route;
		return this;
	}
}