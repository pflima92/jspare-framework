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
package org.jspare.core.container;

import static org.jspare.core.container.Environment.my;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.context.ApplicationContext;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.InfraException;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractBootstrap {

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */

	/**
	 * Gets the application context.
	 *
	 * @return the application context
	 */
	@Getter
	protected static ApplicationContext applicationContext = my(ApplicationContext.class);

	/** The custom application context. */
	protected String customApplicationContext;

	/** The load application context. */
	protected boolean loadApplicationContext = true;

	/** The load bundles. */
	protected boolean loadBundles = true;

	/** The bundles. */
	protected List<Class<? extends Bundle>> bundles = new ArrayList<>();

	/**
	 * Adds the bundle.
	 *
	 * @param bundle
	 *            the bundle
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap addBundle(Class<? extends Bundle> bundle) {

		this.bundles.add(bundle);
		return this;
	}

	/**
	 * Adds the bundles.
	 *
	 * @param bundles
	 *            the bundles
	 * @return the abstract bootstrap
	 */
	@SuppressWarnings("unchecked")
	public AbstractBootstrap addBundles(Class<? extends Bundle>... bundles) {

		this.bundles.addAll(Arrays.asList(bundles));
		return this;
	}

	/**
	 * Custom application context.
	 *
	 * @param customApplicationContext
	 *            the custom application context
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap customApplicationContext(String customApplicationContext) {
		this.customApplicationContext = customApplicationContext;
		return this;
	}

	/**
	 * Load.
	 *
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap load() {
		log.info("Starting bootstrap");
		try {

			if (loadApplicationContext) {

				log.info("Loading default Application Context");
				if (StringUtils.isEmpty(customApplicationContext)) {
					getApplicationContext().load();
				} else {
					getApplicationContext().load(customApplicationContext);
				}
			}

			if (loadBundles) {

				log.info("Instantiate and Registry Bundles packages");
				instantiateAndRegistry();
			}

			return this;
		} catch (Exception e) {

			throw new EnvironmentException(e);
		}
	}

	/**
	 * Load application context.
	 *
	 * @param loadApplicationContext
	 *            the load application context
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap loadApplicationContext(boolean loadApplicationContext) {
		this.loadApplicationContext = loadApplicationContext;
		return this;
	}

	/**
	 * Load bundle.
	 *
	 * @param bundle
	 *            the bundle
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap loadBundle(Bundle bundle) {
		bundle.registryComponents();
		return this;
	}

	/**
	 * Load bundles.
	 *
	 * @param loadBundles
	 *            the load bundles
	 * @return the abstract bootstrap
	 */
	public AbstractBootstrap loadBundles(boolean loadBundles) {
		this.loadBundles = loadBundles;
		return this;
	}

	/**
	 * My support.
	 */
	public void mySupport() {

		ContainerUtils.processInjection(this.getClass(), this);
	}

	/**
	 * Start.
	 *
	 * @throws InfraException
	 *             the infra exception
	 */
	public void start() throws InfraException {

		mySupport();
	}

	/**
	 * Exit.
	 *
	 * @param status
	 *            the status
	 */
	protected void exit(int status) {

		log.info("Exit from bootstrap.");
		System.exit(status);
	}

	/**
	 * Instantiate and registry.
	 */
	protected void instantiateAndRegistry() {

		bundles.forEach(clazz -> {

			try {
				Bundle instance = clazz.newInstance();
				instance.registryComponents();
			} catch (InstantiationException | IllegalAccessException e) {

				throw new EnvironmentException(e);
			}
		});
	}
}
