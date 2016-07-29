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
package org.jspare.web.container;

import org.jspare.core.container.AbstractBootstrap;
import org.jspare.server.Status;
import org.jspare.server.container.ServerBoostrap;
import org.jspare.server.jetty.bundle.JettyServerBundle;
import org.jspare.server.jetty.handler.DefaultNotFoundErrorHandler;
import org.jspare.server.jetty.handler.PublicResourceHandler;
import org.jspare.ui.jtwig.JtwigBundle;

/**
 * The Class WebBootstrap.
 *
 * @author pflima
 * @since 10/05/2016
 */
public class WebBootstrap extends ServerBoostrap {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.container.ServerBoostrap#load()
	 */
	@Override
	public AbstractBootstrap load() {

		// All Bundles need be registered before load Boostrap
		loadBundles();

		super.load();

		// After load Boostrap the resources can be readed
		loadResourceHandlers();
		loadErrorHanlders();

		return this;
	}

	/**
	 * Load bundles.
	 */
	protected void loadBundles() {

		addBundle(JettyServerBundle.class);
		addBundle(JtwigBundle.class);
	}

	/**
	 * Load resource handlers.
	 */
	protected void loadResourceHandlers() {

		getRouter().addResourceHandler(PublicResourceHandler.class);
	}

	/**
	 * Load error hanlders.
	 */
	protected void loadErrorHanlders() {

		getRouter().addErrorHandler(Status.NOT_FOUND, DefaultNotFoundErrorHandler.class);
	}
}
