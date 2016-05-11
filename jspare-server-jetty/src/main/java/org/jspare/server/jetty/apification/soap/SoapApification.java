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
package org.jspare.server.jetty.apification.soap;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

import org.jspare.server.apification.Apification;
import org.jspare.server.controller.CommandData;

/**
 * The Class SoapApification.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class SoapApification implements Apification {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.server.apification.Apification#annotedBy()
	 */
	@Override
	public Class<? extends Annotation> annotedBy() {

		return Soap.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jspare.server.apification.Apification#wrap(java.lang.reflect.Method,
	 * java.lang.Object[])
	 */
	@Override
	public List<CommandData> wrap(Method method, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
}
