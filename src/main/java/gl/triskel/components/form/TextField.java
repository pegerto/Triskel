package gl.triskel.components.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;
import gl.triskel.components.interfaces.WebPageVisitor;

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

	public enum TextFieldType
	{
		TEXT,INTEGER
	}
	
	private String text;
	private TextFieldType type;
	
	
	public TextField()
	{
		text = "";
		type = TextFieldType.TEXT;
	}
	
	/**
	 * Text value of the TextField
	 * @return value of the text field.
	 */
	public String getText() {
		return text;
	}

	/**
	 * Set the value of the text field.
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	public TextFieldType getType() {
		return type;
	}

	public void setType(TextFieldType type) {
		this.type = type;
	}

	@Override
	public Element render(Document doc) {
		Element inputElement= doc.createElement("input");
		inputElement.setAttribute("type", "text");
		if (this.getId() != null)
			inputElement.setAttribute("name", this.getId());
		inputElement.setAttribute("value", text);
		
		//Validate content
		switch (type) {
		case INTEGER:
			inputElement.setAttribute("onKeyPress", "return checkTextFieldType(evt)");
			break;

		default:
			break;
		}
		
		return inputElement;
	}

	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		visitor.visit(this);
	}

	

}
