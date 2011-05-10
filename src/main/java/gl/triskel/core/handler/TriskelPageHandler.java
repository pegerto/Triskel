package gl.triskel.core.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;


import org.w3c.dom.Document;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import gl.triskel.annotations.Parameter;
import gl.triskel.components.WebPage;
import gl.triskel.core.Application;
import gl.triskel.core.TriskelRequest;
import gl.triskel.core.TriskelResponse;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.util.HtmlFormatter;

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
public class TriskelPageHandler extends TriskelHandler{

	public TriskelPageHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	
	public void process(TriskelRequest request, TriskelResponse response) throws PageNotFoundException,
		UnableToLoadPageException {
		
		String pagename = request.getRequestURI().substring(request.getContextPath().length() + 1);
		pagename = pagename.toLowerCase();
		
		//Check if exists page in application
		if (!app.getPagesClass().containsKey(pagename))
			throw new PageNotFoundException(pagename);
		
		PrintWriter print = null;
		try {
			print = response.getWriter();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Class page = app.getPagesClass().get(pagename);
	
		WebPage spage = null;
		
		
		if (!app.getUserPages().containsKey(pagename))
		{
			try {
				//Instanciate a new web page for this user.
				spage = (WebPage)page.newInstance();
				
				//set prereaded url
				spage.setUrl(app.getTriskelContext().getContextPath() + "/" + pagename);
				
				app.getUserPages().put(pagename, spage);
			} catch (InstantiationException e) {
				throw new UnableToLoadPageException(e);
			} catch (IllegalAccessException e) {
				throw new UnableToLoadPageException(e);
			}
		}else{
			spage = app.getUserPages().get(pagename);
		}
		//configure parameters.
		configureParameters(spage, request);
		spage.onLoad();
		
		Document doc = new DocumentImpl();
		doc.appendChild(spage.render(doc));
		
		print.write(HtmlFormatter.format(doc));		
	}

	

	
	private void configureParameters(WebPage page, TriskelRequest request)
	{
		//Parameters.
		Enumeration params = request.getParameterNames();
		
		//Check all parameters
		while(params.hasMoreElements())
		{
		       String name = (String)params.nextElement();
		       
		       try {
		    	   Field classField = page.getClass().getDeclaredField(name);
		    	   if (classField.getAnnotation(Parameter.class) != null)
		    	   {
		    		   //Set property accesible.
		    		   classField.setAccessible(true);
		    		   
		    		   //Ajust target data type
		    		   if (classField.getType().equals(String.class))
		    		   {
		    			   classField.set(page, request.getParameter(name));
		    		   }else if(classField.getType().equals(Integer.class))
		    		   {
		    			   classField.set(page, Integer.valueOf(request.getParameter(name)));
		    		   }else if(classField.getType().equals(Double.class))
		    		   {
		    			   classField.set(page, Double.valueOf(request.getParameter(name)));
		    		   }
		    		   //Is not logger accessible.
		    		   classField.setAccessible(false);
		    	   }
		    	   
		       
				} catch (NoSuchFieldException e) {
					//Logging to debug, but dosen't mather.
				} catch (IllegalAccessException e) {
					//Logging to debug, but dosen't mather.
			}
		}
	}

	
	
}
