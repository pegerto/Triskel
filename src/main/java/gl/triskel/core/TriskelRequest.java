package gl.triskel.core;

import gl.triskel.components.WebPage;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

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
public class TriskelRequest {
	
	
	private HttpServletRequest servletRequest;
	private RequestProvider provider;
	private WebPage page;
	
	private  enum RequestProvider {
		SERVLET
	}

	public TriskelRequest(HttpServletRequest servlet ) {
		this.servletRequest = servlet;
		this.provider = RequestProvider.SERVLET;
	}
	
	
	public WebPage getPage() {
		return page;
	}

	public void setPage(WebPage page) {
		this.page = page;
	}





	public String getContextPath()
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getContextPath();

		default:
			return null;
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public Enumeration getParameterNames()
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getParameterNames();

		default:
			return null;
		}
		
	}
	
	
	public String getParameter(String parameter)
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getParameter(parameter);
				
		default:
			return null;
		}
	}

	public String getRequestURI()
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getRequestURI();
	
		default:
			return null;
		}
	}
		
	public long getDateHeader(String property)
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getDateHeader(property);
		
		default:
			return 0;
		}
	}
	
	
	public String getMethod()
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getMethod();

		default:
			return null;
		}
	}
	
	
	public StringBuffer getRequestURL()
	{
		switch (provider) {
		case SERVLET:
			return servletRequest.getRequestURL();

		default:
			return null;
		}
	}
	
}
