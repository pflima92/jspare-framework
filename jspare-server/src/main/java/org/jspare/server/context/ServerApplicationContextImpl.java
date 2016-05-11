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
package org.jspare.server.context;

import static org.jspare.core.container.Environment.my;

import org.jspare.core.context.ApplicationContextImpl;
import org.jspare.core.exception.EnvironmentException;
import org.jspare.core.exception.InfraException;
import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.core.exception.SerializationException;
import org.jspare.core.scanner.ComponentScanner;
import org.jspare.core.serializer.Serializer;
import org.jspare.server.Router;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServerApplicationContextImpl extends ApplicationContextImpl implements ServerApplicationContext {

	/** The context. */
	private ServerContext context;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.core.context.ApplicationContextImpl#getContext()
	 */
	@Override
	public ServerContext getContext() {
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
	 * @see
	 * org.jspare.server.context.ServerApplicationContext#loadControllers(org.
	 * jspare.server.Router)
	 */
	@Override
	public void loadControllers(Router router) {

		context.getControllers().ifPresent(ctl -> ctl.forEach(c -> {

			try {

				if (!c.endsWith(ALL_SCAN_QUOTE)) {

					Class<?> controllerClazz = Class.forName(c);
					router.addMapping(controllerClazz);
				}

				my(ComponentScanner.class).scanAndExecute(c, (clazzName) -> {

					try {

						Class<?> controllerClazz = Class.forName((String) clazzName[0]);

						if (router.isValidCommand(controllerClazz)) {

							router.addMapping(controllerClazz);
						}
					} catch (Exception e) {

						log.error(e.getMessage(), e);
					}
					return Void.TYPE;
				});

			} catch (Exception e) {
				log.error("Error on load controller: [{}]", c);
				log.error(e.getMessage(), e);
			}

		}));

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.core.context.ApplicationContextImpl#loadContext(java.lang.
	 * String)
	 */
	@Override
	protected void loadContext(String content) {
		super.loadContext(content);

		try {
			ServerContext context = my(Serializer.class).fromJSON(content, ServerContext.class);

			if (context == null) {

				context = ServerContext.empty();
			}

			this.context = context;

		} catch (SerializationException e) {

			throw new EnvironmentException(e);
		}
	}

}
