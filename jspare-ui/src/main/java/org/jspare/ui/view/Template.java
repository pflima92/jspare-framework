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

/*
 * Copyright 2015 TechFull IT Services.
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
import java.util.Map;

import org.jspare.server.exception.RenderableException;

/**
 * The Interface Template.
 *
 * @author pflima
 * @since 22/04/2016
 */
public interface Template {

	/**
	 * Apply.
	 *
	 * @param viewValues
	 *            the view values
	 * @return the string
	 * @throws RenderableException
	 *             the renderable exception
	 */
	String apply(Map<String, Object> viewValues) throws RenderableException;

	/**
	 * Sets the content.
	 *
	 * @param content
	 *            the new content
	 */
	void setContent(Object content);
}