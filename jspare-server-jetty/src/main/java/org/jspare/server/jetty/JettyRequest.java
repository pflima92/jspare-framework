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
package org.jspare.server.jetty;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.server.ContainerRequest;
import org.jspare.core.collections.MultiValueHashMap;
import org.jspare.core.collections.MultiValueMap;
import org.jspare.server.content.ContentDisposition;
import org.jspare.server.content.DataPart;
import org.jspare.server.content.Entity;
import org.jspare.server.content.Reader;
import org.jspare.server.mapping.Type;
import org.jspare.server.transport.DefaultRequest;
import org.jspare.server.transport.Media;

import lombok.Cleanup;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JettyRequest extends DefaultRequest {

	/** The context. */
	@Getter
	private ContainerRequestContext context;

	/**
	 * Instantiates a new jetty request.
	 *
	 * @param context
	 *            the context
	 */
	public JettyRequest(final ContainerRequestContext context) {
		super(context);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getBasePath()
	 */
	@Override
	public String getBasePath() {

		return context.getUriInfo().getBaseUri().toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getCommandAlias()
	 */
	@Override
	public String getCommandAlias() {

		return this.context.getHeaderString(HD_ALIAS);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getClientResource(java.lang.String)
	 */
	@Override
	public String getCookie(String name) {

		Cookie cookie = this.context.getCookies().get(name);
		return cookie != null ? cookie.getValue() : StringUtils.EMPTY;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeader(java.lang.String)
	 */
	@Override
	public Optional<String> getHeader(String key) {

		return Optional.ofNullable(context.getHeaderString(key));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeaders()
	 */
	@Override
	public MultiValueMap<String, Object> getHeaders() {

		MultiValueMap<String, Object> mVmap = new MultiValueHashMap<>();
		context.getHeaders().entrySet().forEach(es -> {

			mVmap.put(es.getKey(), es.getValue().stream().collect(Collectors.toList()));
		});
		return mVmap;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getHeadersString()
	 */
	@Override
	public Map<String, String> getHeadersAsString() {

		Map<String, String> mHd = new HashMap<>();
		context.getHeaders().entrySet().forEach(es -> {
			mHd.put(es.getKey(), context.getHeaderString(es.getKey()));
		});
		return mHd;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getLocale()
	 */
	@Override
	public Locale getLocale() {

		return context.getLanguage();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getMedia()
	 */
	@Override
	public Media getMedia() {

		return (Media) (() -> context.getMediaType().toString());
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getPath()
	 */
	@Override
	public String getPath() {
		return context.getUriInfo().getPath();
	}

	@Override
	public String getRemoteAddr() {

		return getHeader("X-FORWARDED-FOR").orElse(null);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.Request#getType()
	 */
	@Override
	public Type getType() {

		return Type.valueOf(context.getMethod());
	}

	/**
	 * Buid map parameters.
	 *
	 * @return the map
	 */
	protected Map<String, Object> buildMapParameters() {

		Map<String, Object> mapParameters = new HashMap<>();
		if (context instanceof ContainerRequest) {

			ContainerRequest request = (ContainerRequest) context;
			if (isType(request, MediaType.APPLICATION_FORM_URLENCODED_TYPE)) {
				request.bufferEntity();
				mapParameters.putAll(request.readEntity(Form.class).asMap());
			}
			if (isType(request, MediaType.MULTIPART_FORM_DATA_TYPE)) {
				request.bufferEntity();
				FormDataMultiPart multiPart = request.readEntity(FormDataMultiPart.class);
				mapParameters.putAll(multiPart.getFields().keySet().stream().collect(Collectors.toMap(String::toString,
						key -> multiPart.getFields(key).size() > 1 ? multiPart.getFields(key) : multiPart.getField(key))));

				multiPart.getFields().keySet().forEach(key -> {

					List<DataPart> parts = new ArrayList<>();
					parts.addAll(multiPart.getFields(key).stream().map(bodyPart -> wrapDataPart(bodyPart)).collect(Collectors.toList()));

					if (parts.size() > 1) {

						mapParameters.put(key, parts);
					} else {

						mapParameters.put(key, parts.stream().findFirst().orElse(null));
					}
				});

			}
		}

		MultivaluedHashMap<String, String> requestParams = new MultivaluedHashMap<String, String>();
		requestParams.putAll(context.getUriInfo().getPathParameters());
		requestParams.putAll(context.getUriInfo().getQueryParameters());
		mapParameters
				.putAll(requestParams.keySet().stream().collect(Collectors.toMap(String::toString, key -> requestParams.getFirst(key))));

		return mapParameters;
	}

	/**
	 * Builds the body.
	 *
	 * @return the string
	 */
	@Override
	protected Entity buildEntity() {

		try {

			if (context.hasEntity()) {

				@Cleanup
				InputStream inputStream = context.getEntityStream();
				return new Entity(IOUtils.toByteArray(inputStream));
			}

		} catch (IOException e) {
			log.error("No content on  body request", e);
		}
		return Entity.empty();
	}

	private boolean isType(ContainerRequest request, MediaType mediaType) {

		return request.hasEntity() && mediaType.getSubtype().equals(request.getMediaType().getSubtype());
	}

	private DataPart wrapDataPart(FormDataBodyPart bodyPart) {

		Reader reader = new Reader() {

			@Override
			@SuppressWarnings("unchecked")
			public <T> T read(Class<?> clazz) {

				return (T) bodyPart.getEntityAs(clazz);
			}
		};
		org.glassfish.jersey.media.multipart.ContentDisposition cd = bodyPart.getContentDisposition();
		return new DataPart(reader, bodyPart.getEntity(), bodyPart.getName(), new ContentDisposition(cd.getType(), cd.getParameters(),
				cd.getFileName(), cd.getCreationDate(), cd.getModificationDate(), cd.getReadDate(), cd.getSize()));
	}
}