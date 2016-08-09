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
package org.jspare.web.forvertx.transport;

import static org.jspare.core.container.Environment.CONFIG;
import static org.jspare.core.container.Environment.my;
import static org.jspare.server.commons.ServerDefinitions.YIELD_ENABLE_KEY;

import java.lang.reflect.Parameter;

import org.apache.commons.lang.StringUtils;
import org.jspare.core.exception.InfraRuntimeException;
import org.jspare.core.exception.SerializationException;
import org.jspare.server.Request;
import org.jspare.server.Response;
import org.jspare.server.commons.SimpleResponse;
import org.jspare.server.controller.CommandData;
import org.jspare.server.controller.Controller;
import org.jspare.server.controller.ControllerFactory;
import org.jspare.server.exception.BeanValidationException;
import org.jspare.server.exception.NoSuchCallerException;
import org.jspare.server.filter.Filter;
import org.jspare.server.mapping.Model;
import org.jspare.server.transport.Status;
import org.jspare.web.forvertx.transport.model.Yield;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransactionExecutorImpl implements TransactionExecutor {

	/** The caller. */
	@Setter
	private Object caller;

	/** The process. */
	private Thread process;

	/** The response. */
	private Response response;

	private boolean hasYield;

	public TransactionExecutorImpl() {

		hasYield = Boolean.valueOf(CONFIG.get(YIELD_ENABLE_KEY, Boolean.FALSE));
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.TransactionExecutor#consumeResponse()
	 */
	@Override
	public Response consumeResponse() {

		return this.response;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionExecutor#doIt(org.jspare.server.
	 * controller.CommandData, org.jspare.server.Request,
	 * org.jspare.server.Response)
	 */
	@Override
	public void doIt(CommandData cmd, Request request, Response response) throws InterruptedException {

		if (!hasYield) {

			execute(cmd, request, response);
			return;
		}

		process = new Thread(() -> {

			execute(cmd, request, response);

		}, "jspare-server-executor");

		synchronized (caller) {

			process.start();
			caller.wait();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.TransactionExecutor#hasResponse()
	 */
	@Override
	public boolean hasResponse() {

		return this.response != null;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionExecutor#notifyFinish(org.jspare
	 * .server.Response)
	 */
	@Override
	public void notifyFinish(Response response) throws NoSuchCallerException {

		this.response = response;

		if (caller == null) {

			throw new NoSuchCallerException("Caller not seted on executor");
		}

		synchronized (caller) {

			caller.notify();
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see org.jspare.server.transaction.TransactionExecutor#resume()
	 */
	@Override
	public void resume() throws InterruptedException {

		synchronized (process) {

			process.notify();

		}
		synchronized (caller) {

			caller.wait();
		}

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.jspare.server.transaction.TransactionExecutor#yield(org.jspare.server
	 * .Request, org.jspare.server.Response, java.lang.String)
	 */
	@Override
	public void yield(Request request, Response response, String bind) {

		if (!hasYield) {

			throw new InfraRuntimeException("Yield is not enabled on environment, please put key server.yield.enable = true");
		}

		my(TransactionManager.class).yield(bind, request.getTransaction());

		Yield yield = new Yield(request.getTransaction().getId(), bind, request.getTransaction().getContext());

		Response yieldResponse = (Response) response.clone();
		yieldResponse.status(Status.YIELD).entity(yield).end();
		try {

			notifyFinish(yieldResponse);

			synchronized (process) {

				process.wait();
			}

		} catch (NoSuchCallerException | InterruptedException e) {

			log.error("Error on yield process", e);
			response.status(Status.INTERNAL_SERVER_ERROR).end();
		}
	}

	private void execute(CommandData cmd, Request request, Response response) {
		try {

			for (Filter f : cmd.getBeforeFilters()) {
				f.doIt(request, response);
			}

			// Inject Request and Response if is Available
			Object newInstance = my(ControllerFactory.class).instantiate(cmd.getCmdClazz());

			if (newInstance instanceof Controller) {

				request.setController((Controller) newInstance);
				((Controller) newInstance).setRequest(request);
				((Controller) newInstance).setResponse(response);
			}

			Object[] parameters = new Object[cmd.getMethod().getParameterCount()];
			int i = 0;
			for (Parameter parameter : cmd.getMethod().getParameters()) {

				parameters[i] = resolveParameter(parameter, request, response);
				i++;
			}

			// Validate default parameters
			if (newInstance instanceof Controller) {

				try {

					((Controller) newInstance).validate(parameters);
				} catch (BeanValidationException e) {

					((Controller) newInstance).badRequest(SimpleResponse.invalidConstraints(e.getVioledConstraints()));
					return;
				}
			}

			cmd.getMethod().invoke(newInstance, parameters);

			for (Filter f : cmd.getAfterFilters()) {
				f.doIt(request, response);
			}

			response.end();

			my(TransactionManager.class).end(request.getTransaction().getId());

		} catch (Throwable t) {

			log.error("Fail execute", t);
			response.status(Status.INTERNAL_SERVER_ERROR).end();
		} finally {

			this.response = response;

			try {
				notifyFinish(response);

			} catch (Exception e) {

				log.error("Fail execute", e);
			}
		}
	}

	/**
	 * Resolve parameter.
	 *
	 * @param parameter
	 *            the parameter
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 * @return the object
	 */
	private Object resolveParameter(Parameter parameter, Request request, Response response) {

		if (parameter.getType().equals(Request.class)) {

			return request;
		}
		if (parameter.getType().equals(Response.class)) {

			return response;
		}
		if (!StringUtils.isEmpty(request.getParameter(parameter.getName()))) {

			return request.getParameter(parameter.getName());
		}

		if (parameter.getType().getPackage().getName().endsWith(".model") || parameter.getType().isAnnotationPresent(Model.class)
				|| parameter.isAnnotationPresent(Model.class)) {

			try {

				return request.getEntity().as(parameter.getType());
			} catch (SerializationException e) {

				log.warn("Invalid content of entity for class [{}] on parameter [{}]", parameter.getClass(), parameter.getName());
				return null;
			}
		}
		if (parameter.isAnnotationPresent(org.jspare.server.mapping.Parameter.class)) {

			String parameterName = parameter.getAnnotation(org.jspare.server.mapping.Parameter.class).value();
			return request.getParameter(parameterName);
		}

		return null;
	}
}