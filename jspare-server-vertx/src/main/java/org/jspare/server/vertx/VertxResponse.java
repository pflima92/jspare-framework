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

import java.io.InputStream;
import java.util.Locale;

import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.Response;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transaction.Transaction;
import org.jspare.server.transport.CacheControl;
import org.jspare.server.transport.Media;
import org.jspare.server.transport.Renderable;
import org.jspare.server.transport.Status;

/**
 * The Class JettyResponse.
 *
 * @author pflima
 * @since 30/03/2016
 */
public class VertxResponse implements Response {

	@Override
	public Response addCookie(String name, String value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response cache(CacheControl cacheControl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub

	}

	@Override
	public Response entity(byte[] entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response entity(InputStream entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response entity(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response entity(Renderable view) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response entity(String entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MultiValueMap<String, Object> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLanguage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Transaction getTransaction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response media(Media... medias) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response status(Status status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void yield() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() {
		try {

			return super.clone();
		} catch (CloneNotSupportedException e) {

			return null;
		}
	}
}