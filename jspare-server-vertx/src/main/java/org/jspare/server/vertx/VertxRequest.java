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
package org.jspare.server.vertx;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.Request;
import org.jspare.server.controller.Controller;
import org.jspare.server.mapping.Type;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.Media;

public class VertxRequest implements Request {

	@Override
	public String getBasePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCommandAlias() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Object> getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<String> getHeader(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiValueMap<String, Object> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getHeadersAsString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Media getMedia() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getParameter(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> Map<String, T> getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRemoteAddr() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getSourceRequest() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setController(Controller controller) {
		// TODO Auto-generated method stub

	}

}