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
package org.jspare.server.resource;

import org.jspare.core.exception.InfraRuntimeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResourceFactoryImpl implements ResourceFactory {

	@Override
	public <T> T create(Resource<T> resource) {

		return resource.generate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.resource.ResourceFactory#create(java.lang.Class)
	 */
	@Override
	public <T> T create(Class<? extends Resource<T>> clazz) {

		try {

			Resource<T> resource = clazz.newInstance();
			return create(resource);
		} catch (InstantiationException | IllegalAccessException e) {

			log.error("Error on create resource factory", e);
			throw new InfraRuntimeException(e);
		}
	}
}