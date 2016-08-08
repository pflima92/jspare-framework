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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.jspare.core.collections.MultiValueHashMap;
import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.content.Entity;
import org.jspare.server.mapping.Type;
import org.jspare.server.transport.DefaultRequest;
import org.jspare.server.transport.Media;

import io.vertx.core.MultiMap;
import io.vertx.ext.web.RoutingContext;
import lombok.Getter;

public class VertxRequest extends DefaultRequest  {
	
	@Getter
	private RoutingContext context;

	public VertxRequest(RoutingContext context) {
		super(context);
	}

	@Override
	public String getBasePath() {
		return context.request().absoluteURI();
	}

	@Override
	public String getCommandAlias() {

		return context.request().getHeader(HD_ALIAS);
	}

	@Override
	public String getCookie(String name) {
		
		return context.getCookie(name).getValue();
	}

	@Override
	public Optional<String> getHeader(String key) {

		return Optional.ofNullable(context.request().getHeader(key));
	}

	@Override
	public MultiValueMap<String, Object> getHeaders() {

		MultiValueMap<String, Object> mVmap = new MultiValueHashMap<>();
		context.request().headers().entries().forEach(mv -> {
			mVmap.put(mv.getKey(), Arrays.asList(mv.getValue()));
		});
		return mVmap;
	}

	@Override
	public Map<String, String> getHeadersAsString() {
		Map<String, String> mVmap = new HashMap<>();
		context.request().headers().entries().forEach(mv -> {
			mVmap.put(mv.getKey(), mv.getValue());
		});
		return mVmap;
	}

	@Override
	public Locale getLocale() {
		
		if(context.acceptableLocales().isEmpty()) return Locale.getDefault();
		io.vertx.ext.web.Locale requestLocale = context.acceptableLocales().get(0);
		return new Locale(String.format("%s%s", requestLocale.language(), requestLocale.country().toUpperCase())); 
	}

	@Override
	public Media getMedia() {
		
		return (Media) (() -> context.getAcceptableContentType());
	}

	@Override
	public Map<String, Object> getParameters() {

		Map<String, Object> parameters = new HashMap<>();
		MultiMap map = context.request().params();
		map.forEach(mp -> parameters.put(mp.getKey(), mp.getValue()));
		return parameters;
	}

	@Override
	public String getPath() {

		return context.request().path();
	}

	@Override
	public String getRemoteAddr() {

		return context.request().remoteAddress().toString();
	}

	@Override
	public Type getType() {

		return Type.valueOf(context.request().method().toString().toUpperCase());
	}

	@Override
	protected Entity buildEntity() {

		if(context.getBody().getBytes() == null){
			
			return Entity.empty();
		}
		return new Entity(context.getBody().getBytes());
	}

	@Override
	protected Map<String, Object> buildMapParameters() {

		Map<String, Object> mapParameters = new HashMap<>();
		context.request().params().forEach(entry -> mapParameters.put(entry.getKey(), entry.getValue()));
		return mapParameters;
	}
}