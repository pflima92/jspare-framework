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
package org.jspare.server.vertx.commons;

import org.jspare.server.commons.ServerDefinitions;

public interface VertxDefinitions extends ServerDefinitions {

	String SERVER_MIN_POOL_THREADS_KEY = "server.threads.min";
	Integer SERVER_MIN_POOL_THREADS_DEFAULT = 13;
	String SERVER_MAX_POOL_THREADS_KEY = "server.threads.max";
	Integer SERVER_MAX_POOL_THREADS_DEFAULT = 13;
	String SERVER_DAEMON_KEY = "server.daemon";

}
