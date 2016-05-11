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
package org.jspare.server.basic;

import org.jspare.server.Router;
import org.jspare.server.Server;

import lombok.Getter;

/**
 * The Class BasicServer.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class BasicServer implements Server {

	/** The router. */
	private Router router;

	/**
	 * Checks if is use certificates.
	 *
	 * @return true, if is use certificates
	 */

	/**
	 * Checks if is use certificates.
	 *
	 * @return true, if is use certificates
	 */

	/**
	 * Checks if is use certificates.
	 *
	 * @return true, if is use certificates
	 */

	/**
	 * Checks if is use certificates.
	 *
	 * @return true, if is use certificates
	 */

	/**
	 * Checks if is use certificates.
	 *
	 * @return true, if is use certificates
	 */
	@Getter
	private boolean useCertificates;

	/**
	 * Instantiates a new basic server.
	 */
	public BasicServer() {

		router = new BasicRouter();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#certificates(boolean)
	 */
	@Override
	public Server certificates(boolean use) {
		useCertificates = use;
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#getRouter()
	 */
	@Override
	public Router getRouter() {

		return router;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#name(java.lang.String)
	 */
	@Override
	public Server name(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#port(int)
	 */
	@Override
	public Server port(int port) {
		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#router(org.jspare.server.Router)
	 */
	@Override
	public Server router(Router router) {

		return this;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#start()
	 */
	@Override
	public void start() {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#startOn(int)
	 */
	@Override
	public void startOn(int port) {
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Server#stop()
	 */
	@Override
	public void stop() {
	}

}
