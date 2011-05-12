package gl.triskel.components;

import gl.triskel.components.interfaces.WebPageVisitor;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
public class Label extends Component{

	private String text;
	
	public Label()
	{}
	
	public Label(String text) {
		this.text = text;
	}
	
	public Label(String id, String text)
	{
		this.setId(id);
		this.text = text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public String getText() {
		return text;
	}

	@Override
	public Element render(Document doc) {
		
		Element spanElement = doc.createElement("span");

		//Element id
		if (this.getId() != null)
			spanElement.setAttribute("id", this.getId());
		
		//Text of the element
		if(this.getText() != null)
			spanElement.setTextContent(this.getText());
		
		
		return spanElement;
	}

	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		visitor.visit(this);
		
	}

	
	
}
