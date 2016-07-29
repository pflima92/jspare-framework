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
package org.jspare.server.controller;

import static org.jspare.core.container.Environment.my;

import java.util.Map;
import java.util.Optional;

import org.jspare.core.serializer.Json;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.exception.RenderableException;
import org.jspare.server.session.SessionContext;
import org.jspare.server.transport.Media;
import org.jspare.server.transport.Renderable;
import org.jspare.server.transport.Status;

import lombok.Setter;

/**
 * The Class Controller.
 *
 * @author pflima
 * @since 22/04/2016
 */
public abstract class Controller {

	/**
	 * Sets the request.
	 *
	 * @param request
	 *            the new request
	 */
	@Setter
	protected Request request;

	/**
	 * Sets the response.
	 *
	 * @param response
	 *            the new response
	 */
	@Setter
	protected Response response;

	protected Optional<String> getHeader(String name) {

		return request.getHeader(name);
	}

	protected <T> T getParameter(String name) {

		return request.getParameter(name);
	}

	/**
	 * Bad gateway.
	 */
	protected void badGateway() {

		response.status(Status.BAD_GATEWAY).end();
	}

	/**
	 * Bad request.
	 */
	protected void badRequest() {

		response.status(Status.BAD_REQUEST).end();
	}

	/**
	 * Bad request.
	 *
	 * @param object
	 *            the object
	 */
	protected void badRequest(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.BAD_REQUEST).entity(content).end();
	}

	/**
	 * Bad request.
	 *
	 * @param content
	 *            the content
	 */
	protected void badRequest(String content) {

		response.status(Status.BAD_REQUEST).entity(content).end();
	}

	/**
	 * Conflict.
	 */
	protected void conflict() {

		response.status(Status.CONFLICT).end();
	}

	/**
	 * Conflict.
	 *
	 * @param object
	 *            the object
	 */
	protected void conflict(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.CONFLICT).entity(content).end();
	}

	/**
	 * Conflict.
	 *
	 * @param content
	 *            the content
	 */
	protected void conflict(String content) {

		response.status(Status.CONFLICT).entity(content).end();
	}

	/**
	 * Error.
	 */
	protected void error() {

		response.status(Status.INTERNAL_SERVER_ERROR).end();
	}

	/**
	 * Error.
	 *
	 * @param e
	 *            the e
	 */
	protected void error(Exception e) {

		response.status(Status.INTERNAL_SERVER_ERROR).entity(e).end();
	}

	/**
	 * Error.
	 *
	 * @param content
	 *            the content
	 */
	protected void error(String content) {

		response.status(Status.INTERNAL_SERVER_ERROR).entity(content).end();
	}

	/**
	 * Forbidden.
	 */
	protected void forbidden() {

		response.status(Status.FORBIDDEN).end();
	}

	/**
	 * Forbidden.
	 *
	 * @param object
	 *            the object
	 */
	protected void forbidden(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.FORBIDDEN).entity(content).end();
	}

	/**
	 * Forbidden.
	 *
	 * @param content
	 *            the content
	 */
	protected void forbidden(String content) {

		response.status(Status.FORBIDDEN).entity(content).end();
	}

	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	protected Map<String, Object> getContext() {

		return request.getTransaction().getContext();
	}

	/**
	 * Gets the session.
	 *
	 * @return the session
	 */
	protected SessionContext getSession() {

		return request.getSessionContext();
	}

	/**
	 * No content.
	 */
	protected void noContent() {

		response.status(Status.NO_CONTENT).end();
	}

	/**
	 * Not acceptable.
	 */
	protected void notAcceptable() {

		response.status(Status.NOT_FOUND).end();
	}

	/**
	 * Not found.
	 */
	protected void notFound() {

		response.status(Status.NOT_FOUND).end();
	}

	/**
	 * Not implemented.
	 */
	protected void notImplemented() {

		response.status(Status.NOT_IMPLEMENTED).end();
	}

	/**
	 * Pre condition failed.
	 */
	protected void preConditionFailed() {

		response.status(Status.PRECONDITION_FAILED).end();
	}

	/**
	 * Pre condition failed.
	 *
	 * @param object
	 *            the object
	 */
	protected void preConditionFailed(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.PRECONDITION_FAILED).entity(content).end();
	}

	/**
	 * Pre condition failed.
	 *
	 * @param content
	 *            the content
	 */
	protected void preConditionFailed(String content) {

		response.status(Status.PRECONDITION_FAILED).entity(content).end();
	}

	/**
	 * Redirect.
	 *
	 * @param target
	 *            the target
	 */
	protected void redirect(String target) {

		response.status(Status.MOVED_PERMANENTLY).entity(target).end();
	}

	/**
	 * Success.
	 */
	protected void success() {

		response.status(Status.OK).end();
	}

	/**
	 * Success.
	 *
	 * @param object
	 *            the object
	 */
	protected void success(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.OK).entity(content).end();
	}

	/**
	 * Success.
	 *
	 * @param renderable
	 *            the renderable
	 */
	protected void success(Renderable renderable) {

		String content;
		try {
			content = renderable.render(request);
			response.status(Status.OK).entity(content).end();
		} catch (RenderableException e) {

			error(e);
		}
	}

	/**
	 * Success.
	 *
	 * @param content
	 *            the content
	 */
	protected void success(String content) {

		if (my(Json.class).isValidJson(content)) {
			response.media(Media.of("application/json"));
		}

		response.status(Status.OK).entity(content).end();
	}

	/**
	 * Unauthorized.
	 */
	protected void unauthorized() {

		response.status(Status.UNAUTHORIZED).end();
	}

	/**
	 * Unauthorized.
	 *
	 * @param object
	 *            the object
	 */
	protected void unauthorized(Object object) {

		String content = my(Json.class).toJSON(object);
		response.status(Status.UNAUTHORIZED).entity(content).end();
	}

	/**
	 * Unauthorized.
	 *
	 * @param content
	 *            the content
	 */
	protected void unauthorized(String content) {

		response.status(Status.UNAUTHORIZED).entity(content).end();
	}

	/**
	 * Unvailable.
	 */
	protected void unvailable() {

		response.status(Status.SERVICE_UNAVAILABLE).end();
	}

	/**
	 * Yield.
	 *
	 * @param bind
	 *            the bind
	 */
	protected void yield(String bind) {

		request.getTransaction().getExecutor().yield(request, response, bind);
	}

}