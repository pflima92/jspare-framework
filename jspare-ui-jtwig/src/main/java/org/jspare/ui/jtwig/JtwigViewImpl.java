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

import static org.jspare.core.container.Environment.my;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.container.Inject;
import org.jspare.core.container.MySupport;
import org.jspare.server.Request;
import org.jspare.server.controller.Controller;
import org.jspare.server.exception.LoadTemplateException;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.mapping.Model;
import org.jspare.ui.i18n.I18n;
import org.jspare.ui.view.ApplicationModel;
import org.jspare.ui.view.Template;
import org.jspare.ui.view.UIFormatter;
import org.jspare.ui.view.UiToolkit;
import org.jspare.ui.view.View;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class JtwigViewImpl.
 *
 * @author pflima
 * @since 22/04/2016
 */

@Slf4j
public class JtwigViewImpl extends MySupport implements View {

	/** The ui tookit. */
	@Inject
	private UiToolkit uiTookit;

	/** The content. */
	private String content;

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

		if (request != null) {

			// XXX Add for super class
			values.put("request", request);
			values.put("i18n", my(I18n.class).values(request.getSessionContext().getLanguage()));
			values.put("app", new ApplicationModel(request));
			values.put("formatter", new UIFormatter());

			if (request.getController() != null) {

				Class<? extends Controller> clazzController = request.getController().getClass();
				for (Field field : clazzController.getDeclaredFields()) {

					if (field.isAnnotationPresent(Model.class)) {

						try {

							field.setAccessible(true);
							Object value = field.get(request.getController());
							values.put(field.getName(), value);
						} catch (Exception e) {

							log.warn("Error on add field [{}] annoted with @Model on Controller [{}]", field.getName(),
									clazzController.getName());
						}
					}
				}

			}
		}

		if (StringUtils.isEmpty(route) && StringUtils.isEmpty(content)) {

			throw new RenderableException("Route cannot be null");
		}

		Template template = null;

		try {

			if (StringUtils.isEmpty(content)) {

				template = uiTookit.loadTemplate(route);
			} else {

				template = uiTookit.convert2Template(content);
			}

			return uiTookit.compile(template, values);

		} catch (LoadTemplateException e) {

			log.error(e.getMessage(), e);
			throw new RenderableException("Route invalid");
		}
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