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
package org.jspare.sample.svc;

import static org.jspare.core.container.Environment.my;

import org.jspare.core.container.Application;
import org.jspare.core.container.ApplicationBuilder;
import org.jspare.core.exception.InfraException;
import org.jspare.server.Server;
import org.jspare.server.jetty.bundle.JettyServerBundle;

public class Bootstrap extends Application {

	public static void main(String[] args) throws InfraException {

		new Bootstrap().start();
	}

	@Override
	protected void load() {

		builder(ApplicationBuilder.create().addBundle(JettyServerBundle.class));
	}

	@Override
	protected void start() throws InfraException {

		super.start();

		Server server = my(Server.class);
		server.port(8090);
		server.start();
	}
}