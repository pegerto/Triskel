package gl.triskel.core;


import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

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
public class TriskelContext 
{
	
	
	private ServletContext servletContext;
	private RequestProvider provider;
	
	private  enum RequestProvider {
		SERVLET
	}
	
	public TriskelContext(ServletContext context) {
		provider = RequestProvider.SERVLET;
		this.servletContext = context;
	}
	
	public String getContextPath()
	{
		switch (provider) {
		case SERVLET:
			return servletContext.getContextPath();

		default:
			return null;
		}
	}
	
	public String getMimeType(String file)
	{
		switch (provider) {
		case SERVLET:
			return servletContext.getMimeType(file);

		default:
			return null;
		}
	}
	
	
	public URL getResource(String resource) throws MalformedURLException
	{
		switch (provider) {
		case SERVLET:
			return servletContext.getResource(resource);

		default:
			return null;
		}
		
	}

}
