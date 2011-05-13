package gl.triskel.components;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import gl.triskel.annotations.CssStyle;
import gl.triskel.annotations.Page;
import gl.triskel.components.interfaces.WebPageVisitor;
import gl.triskel.core.Application;

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
public class WebPage extends Layout {
	
	private String url;
	private Application application;
	
	
	
	public void setUrl(String url)
	{
		this.url = url;
	}
	
	public String getUrl()
	{
		return url;
	}
	/**
	 * @param application the application to set
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}
	

	public Element render(Document doc) {
		//Invoke the render action of webpage.
		onRender();
		
		Element htmlElement = doc.createElement("html");
		
		//Set the page title.
		String pageTitle = ((Page)this.getClass().getAnnotation(Page.class)).title();
		
		//Html encoding and definition
		htmlElement.setAttribute("xmlns", "http://www.w3.org/1999/xhtml");
		htmlElement.setAttribute("xml:lang", "en");
		
		//Head
		Element headElement = doc.createElement("head");
		htmlElement.appendChild(headElement);
		
		
		//Title
		Element titleElement = doc.createElement("title");
		titleElement.setTextContent(pageTitle);
		headElement.appendChild(titleElement);
		
		//Basic js library
		Element basicLib = doc.createElement("script");
		basicLib.setAttribute("type", "text/javascript");
		basicLib.setAttribute("src", "TRISKELJSLIBRARY/tiskel.js");
		basicLib.setTextContent(";"); //We need to divide nodes, <script> dosen't support <script/>
		headElement.appendChild(basicLib);
		
		//Styles
		CssStyle cssStyle = (CssStyle)(this.getClass().getAnnotation(CssStyle.class));
		if (cssStyle != null)
		{
			Element linkElement = doc.createElement("link");
			linkElement.setAttribute("type", "text/css");
			linkElement.setAttribute("rel", "stylesheet");
			linkElement.setAttribute("href", cssStyle.relativePath());
			headElement.appendChild(linkElement);
		}
			
		
		
		//Body
		Element bodyElement = doc.createElement("body");
		htmlElement.appendChild(bodyElement);

		//Content
		if (this.hasChildrens())
		{
			for(Component component: this.getComponets())
			{
				bodyElement.appendChild(component.render(doc));
			}
		}
		
		return htmlElement;
	}
	

	protected void onRender()
	{}
	
	public void onLoad()
	{}

	/* (non-Javadoc)
	 * @see gl.triskel.components.Component#accept(gl.triskel.components.interfaces.WebPageVisitor)
	 */
	@Override
	public void accept(WebPageVisitor visitor) {
		visitor.visit(this);
	}


	
}
