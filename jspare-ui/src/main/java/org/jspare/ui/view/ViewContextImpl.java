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
package org.jspare.ui.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The Class ViewContextImpl.
 *
 * @author pflima
 * @since 22/04/2016
 */
public class ViewContextImpl implements ViewContext {

	/** The view context. */
	private Map<String, Object> viewContext = new ConcurrentHashMap<>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.view.ViewContext#add(java.util.Map)
	 */
	@Override
	public ViewContext add(Map<String, Object> values) {
		this.viewContext.putAll(values);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.view.ViewContext#add(java.lang.String,
	 * java.lang.Object)
	 */
	@Override
	public ViewContext add(String key, Object value) {
		this.viewContext.put(key, value);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.view.ViewContext#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return this.viewContext.get(key);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.view.ViewContext#remove(java.lang.String)
	 */
	@Override
	public ViewContext remove(String key) {
		this.viewContext.remove(key);
		return this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jspare.ui.view.ViewContext#values()
	 */
	@Override
	public Map<String, Object> values() {
		return viewContext;
	}
}