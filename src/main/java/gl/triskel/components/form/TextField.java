package gl.triskel.components.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;

/**
 * 
 * Triskel Web Framework 
 * A Coruña 2011
 *   
 *  
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * @author pegerto
 *
 */
public class TextField extends Component{

	@Override
	public Element render(Document doc) {
		Element inputElement= doc.createElement("input");
		inputElement.setAttribute("type", "text");
		if (this.getId() != null)
			inputElement.setAttribute("name", this.getId());
		return inputElement;
	}
	

}
