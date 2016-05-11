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

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.jspare.core.container.Inject;
import org.jspare.core.loader.ResourceLoader;
import org.jspare.server.exception.LoadTemplateException;
import org.jspare.server.exception.RenderableException;
import org.jspare.ui.view.Template;
import org.jspare.ui.view.UiToolkit;
import org.jspare.ui.view.ViewContext;
import org.jtwig.environment.Environment;
import org.jtwig.environment.EnvironmentConfiguration;
import org.jtwig.environment.EnvironmentConfigurationBuilder;
import org.jtwig.resource.Resource;
import org.jtwig.resource.StringResource;
import org.jtwig.resource.resolver.ClasspathResourceResolver;
import org.jtwig.resource.resolver.ResourceResolver;

import com.google.common.base.Optional;

/**
 * The Class JtwigUiToolkitImpl.
 *
 * @author pflima
 * @since 22/04/2016
 */
public class JtwigUiToolkitImpl implements UiToolkit {

	/**
	 * The Class JspareUiResourceResolver.
	 *
	 * @author pflima
	 * @since 22/04/2016
	 */
	class JspareUiResourceResolver implements ResourceResolver {

		/*
		 * (non-Javadoc)
		 *
		 * @see org.jtwig.resource.resolver.ResourceResolver#resolve(org.jtwig.
		 * environment.Environment, org.jtwig.resource.Resource,
		 * java.lang.String)
		 */
		@Override
		public Optional<Resource> resolve(Environment environment, Resource resource, String path) {

			if (path.startsWith(ClasspathResourceResolver.PREFIX)) {
				path = path.substring(ClasspathResourceResolver.PREFIX.length());
			}

			if (!path.endsWith(CONFIG.get(SUFIX_FILE_KEY, SUFIX_PAGE_FILE))) {

				path += CONFIG.get(SUFIX_FILE_KEY, SUFIX_PAGE_FILE);
			}

			path = CONFIG.get(ROOT_FILE_KEY, ROOT_FILE) + path;

			String content = my(ResourceLoader.class).readFileToStringIfExist(path);

			return StringUtils.isEmpty(content) ? Optional.absent() : Optional.of(new StringResource(content));
		}
	}

	/** The view context. */
	@Inject
	private ViewContext viewContext;

	/** The templates. */
	private Map<String, Template> templates = new ConcurrentHashMap<>();

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.UiToolkit#compile(org.jspare.ui.view.Template,
	 * java.util.Map)
	 */
	@Override
	public String compile(Template template, Map<String, Object> viewValues) throws RenderableException {

		Map<String, Object> values = new HashMap<>(viewValues);
		values.putAll(viewContext.values());

		return template.apply(values);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.UiToolkit#convert2Template(java.lang.String)
	 */
	@Override
	public Template convert2Template(String resource) throws LoadTemplateException {

		return new JtwigTemplate(org.jtwig.JtwigTemplate.inlineTemplate(resource));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.ui.view.UiToolkit#loadTemplate(java.lang.String)
	 */
	@Override
	public Template loadTemplate(String route) throws LoadTemplateException {

		try {

			return loadTemplateIfNecessary(route);
		} catch (NoSuchMethodException | IOException e) {

			throw new LoadTemplateException(e);
		}
	}

	/**
	 * Load template from classpath.
	 *
	 * @param resource
	 *            the resource
	 * @return the template
	 */
	private Template loadTemplateFromClasspath(String resource) {

		EnvironmentConfiguration configuration = EnvironmentConfigurationBuilder.configuration().resources().resourceResolvers()
				.add(new JspareUiResourceResolver()).and().and().build();

		return new JtwigTemplate(org.jtwig.JtwigTemplate.classpathTemplate(resource, configuration));
	}

	/**
	 * Load template if necessary.
	 *
	 * @param resource
	 *            the resource
	 * @return the template
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 */
	private Template loadTemplateIfNecessary(String resource) throws IOException, NoSuchMethodException {

		boolean loadTemplates = Boolean.valueOf(CONFIG.get(CACHE_TEMPLATE, true));

		if (!loadTemplates) {

			return loadTemplateFromClasspath(resource);
		}

		if (!templates.containsKey(resource)) {

			templates.put(resource, loadTemplateFromClasspath(resource));
		}
		return templates.get(resource);
	}
}
