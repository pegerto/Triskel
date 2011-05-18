package gl.triskel.components.link;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.annotations.Page;
import gl.triskel.components.Component;
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
public class Link extends Component{

	private Class<? extends WebPage> page;
	private String text;
	
	private Set<LinkParameter> parameters;
	
	public Link() 
	{}
	
	
	public Link(Class<? extends WebPage> page, String text, LinkParameter... parameters)
	{
		this.page = page;
		this.text = text;
		this.parameters = new HashSet<LinkParameter>();
		this.parameters.addAll(Arrays.asList(parameters));
	}
	
	public void setPage(Class<? extends WebPage> page) {
		this.page = page;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public void addParameter(LinkParameter parameter)
	{
		if (parameters == null) parameters = new HashSet<LinkParameter>();
		parameters.add(parameter);
	}
	

	@Override
	public Element render(Document doc) {
		
		//Create the root element.
		Element aElement = doc.createElement("a");
		

		if (page != null)
		{
			String href = resolveConextPath() + "/"  + ((Page)page.getAnnotation(Page.class)).relativePath();
			if (parameters != null && !parameters.isEmpty())
			{
				href += "?";
				Iterator<LinkParameter> iter = parameters.iterator();
				while (iter.hasNext())
				{
					LinkParameter parameter = iter.next();
					href += parameter.getName() + "=" + parameter.getValue().toString();
					//if(iter.hasNext()) href += "&amp;";
					if(iter.hasNext()) href += "&";
				}
					
			}
			aElement.setAttribute("href", href);
		}
		if(this.getText() != null) 
			aElement.setTextContent(this.getText());

		return aElement;
	}


	/**
	 * Return the application context path.
	 * @return
	 */
	private String resolveConextPath()
	{
		Component parent = this.getParent();
		while (parent != null && !WebPage.class.isAssignableFrom(parent.getClass())) {
			parent =  parent.getParent();
		}
		if (parent instanceof WebPage) {
			WebPage page = (WebPage) parent;
			return  page.getApplication().getTriskelContext().getContextPath();
		}else	return "";
	}
	
	
	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		visitor.visit(this);
	}
	
}
