package gl.triskel.core;

import gl.triskel.components.WebPage;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.handler.TriskelHandler;
import gl.triskel.core.handler.TriskelPageHandler;
import gl.triskel.core.handler.TriskelStaticResourceHandler;
import gl.triskel.core.util.ResourceConstants;

import java.io.Serializable;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
@SuppressWarnings("rawtypes")
public class Application implements Serializable {

	private static final long serialVersionUID = -2933467198850667085L;

	private static HashMap<String, Class> pagesClass;
	private HashMap<String, WebPage> userPages;
	private static TriskelHandler handler;
	private TriskelContext context;
	
	
	public static HashMap<String, Class> getPagesClass() {
		return pagesClass;
	}

	public HashMap<String, WebPage> getUserPages() {
		return userPages;
	}

	public Application() {
		userPages = new HashMap<String, WebPage>();
		configureHandlers();
	}
	
	@SuppressWarnings("static-access")
	protected void setPagesClass(HashMap<String, Class> pagesClass)
	{
		this.pagesClass = pagesClass;
	}
	
	
	protected void process(TriskelRequest request, TriskelResponse response) throws UnableToLoadPageException, PageNotFoundException
	{
		if (this.handler != null)
		{
			handler.process(request, response);
		}
	}
	
	
	protected void configureHandlers()
	{
		handler = new TriskelStaticResourceHandler(
				new TriskelPageHandler(null, this), this);
		
		
	}
	
	
	public RequestType getRequestType(TriskelRequest request)
	{
		String path = request.getRequestURL().toString().toLowerCase();
		
		if (request.getMethod().equals("GET"))
		{
			if(path.endsWith(ResourceConstants.HTM_FILE) 
					||path.endsWith(ResourceConstants.HTML_FILE)
					||path.endsWith(ResourceConstants.JPG_FILE)
					||path.endsWith(ResourceConstants.GIF_FILE)
					||path.endsWith(ResourceConstants.PNG_FILE)
					||path.endsWith(ResourceConstants.CSS_FILE)) return RequestType.WEB_RESOURCE;
		
		
			return RequestType.APPLICATION_PAGE;
		}else if (request.getMethod().equals("POST"))
		{
			return RequestType.FORM_POST;
		}else
		{
			return RequestType.APPLICATION_PAGE;
		}
		
	}
	
	public TriskelContext getTriskelContext()
	{
		return context;
	}
	
	public void setTriskelContext(TriskelContext context)
	{
		this.context = context;
	}
	
	
}
