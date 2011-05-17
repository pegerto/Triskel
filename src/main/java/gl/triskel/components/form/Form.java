package gl.triskel.components.form;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.components.Component;
import gl.triskel.components.Layout;
import gl.triskel.components.WebPage;
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
public class Form extends Layout{

	@Override
	public Element render(Document doc) {
		Element formElement = doc.createElement("form");
	
		//Only POST method is allowed.
		formElement.setAttribute("method", "POST");
		formElement.setAttribute("action", this.resolveActionURL());
		if (this.getId() != null) formElement.setAttribute("id", this.getId());
	
		if (this.hasChildrens())
		{
			for(Component component: this.getComponets())
			{
				formElement.appendChild(component.render(doc));
			}
		}
		
		Element validationScript = doc.createElement("script");
		StringBuffer script = new StringBuffer(20);
		
		script.append("var form = $(\"#"+this.getId()+"\");");
		script.append("form.validation();");
		
		validationScript.setTextContent(script.toString());
		
		formElement.appendChild(validationScript);
		
		return formElement;
	}
	
	
	/**
	 * Resolve the url of the webpage parent of this form componet.
	 * @return webpage's url.
	 */
	private String resolveActionURL()
	{
		Component parent = this.getParent();
		while (parent != null && !WebPage.class.isAssignableFrom(parent.getClass())) {
			parent =  parent.getParent();
		}
		if (parent instanceof WebPage) {
			WebPage page = (WebPage) parent;
			return  page.getUrl();
		}else	return "#";
	}


	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		visitor.visit(this);		
	}
	
	

}
