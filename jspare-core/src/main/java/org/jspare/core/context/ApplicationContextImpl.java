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
package org.jspare.core.context;

import static org.jspare.core.container.Environment.my;
import static org.jspare.core.container.Environment.scanAndRegistryComponents;

import java.io.IOException;

import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.InfraException;
import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.core.loader.ResourceLoader;
import org.jspare.core.provider.ApplicationProvider;
import org.jspare.core.serializer.Serializer;

/**
 * The Class ApplicationContextImpl.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class ApplicationContextImpl implements ApplicationContext {

	/** The default context. */
	private final String DEFAULT_CONTEXT = "application.jspare";

	/** The context. */
	protected Context context;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.context.ApplicationContext#getContext()
	 */
	@Override
	public Context getContext() {
		if (context == null) {
			try {
				load();
			} catch (InfraException e) {
				throw new InfraRuntimeException(e);
			}
		}
		return context;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.context.ApplicationContext#load()
	 */
	@Override
	public void load() throws InfraException {
		load(DEFAULT_CONTEXT, false);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.context.ApplicationContext#load(java.lang.String)
	 */
	@Override
	public void load(String applicationPath) throws InfraException {
		load(applicationPath, true);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.context.ApplicationContext#release()
	 */
	@Override
	public void release() {
		context = null;
	}

	/**
	 * Load.
	 *
	 * @param applicationPath
	 *            the application path
	 * @param validateIfExist
	 *            the validate if exist
	 * @throws InfraException
	 *             the infra exception
	 */
	private void load(String applicationPath, boolean validateIfExist) throws InfraException {
		try {

			String content = validateIfExist ? my(ResourceLoader.class).readFileToString(applicationPath)
					: my(ResourceLoader.class).readFileToStringIfExist(applicationPath);

			loadContext(content);

		} catch (IOException e) {
			throw new InfraException("Cannot load Application Context", e);
		}
	}

	/**
	 * Load context.
	 *
	 * @param content
	 *            the content
	 */
	protected void loadContext(String content) {
		try {
			Context context = my(Serializer.class).fromJSON(content, Context.class);

			if (context == null) {
				this.context = Context.empty();
				return;
			}

			this.context = context;

			if (context.getRegistryComponents().isPresent()) {

				scanAndRegistryComponents(context.getRegistryComponents().get());
			}
			if (context.getProviders().isPresent()) {

				my(ApplicationProvider.class).load(context.getProviders().get());
			}

		} catch (Exception e) {
			throw new EnvironmentException(e);
		}
	}
}