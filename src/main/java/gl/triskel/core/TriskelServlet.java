package gl.triskel.core;

import gl.triskel.annotations.Page;
import gl.triskel.components.WebPage;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.util.ClassFinder;
import gl.triskel.core.util.ResourceConstants;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
 * @version
 * Servlet integration for Triskel web framework, used to run Triskel like a 
 * servlet.
 *
 */
public class TriskelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String APP = "APP";
	
	private Class<? extends Application > applicationClass;
	private HashMap<String, Class> pagePool;  
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TriskelServlet() {
        super();
        
        pagePool = new HashMap<String, Class>();
    }

	
	@SuppressWarnings("unchecked")
	public void init(ServletConfig servletConfig) 
		throws ServletException 
	{
		super.init(servletConfig);
		
		/*Get application class, application will store the main application
		object for a session*/
		final String apclass = servletConfig.getInitParameter("application");
		
		if (apclass == null)
			throw new ServletException("Application not configure in servlet"); 
		
		try
		{
			applicationClass = (Class<? extends Application>)getClassLoader()
				.loadClass(apclass);
		}catch (ClassNotFoundException e) {
			throw new ServletException("Application class not found:" + apclass );
		}
		
		/*Create a PagePool, pool of pages of the application*/
		populatePagePool(servletConfig);
		
	}
    
    
    protected void service(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException {

    	HttpSession session = request.getSession();
    	Application app = (Application) session.getAttribute(APP);

    	if ( app == null)
    	{
    		//Create a application instance to server the user.
    		try {
    			app = applicationClass.newInstance();
    		} catch (InstantiationException e) {
    			throw new ServletException("Error loading application");
    		} catch (IllegalAccessException e) {
    			throw new ServletException("Error loading application");
    		}
    		app.setPagesClass(pagePool);
    		session.setAttribute(APP, app);
    	}

    	try {


    		app.setTriskelContext(new TriskelContext(getServletContext()));
    		app.process(new TriskelRequest(request), new TriskelResponse(response));


    	} catch (UnableToLoadPageException e) {
    		//TODO GESTIONAR ERROR GRAVE APPLICACION.
    		e.printStackTrace();
    	} catch (PageNotFoundException e) {
    		// TODO GESTIONAR PAGINA ERROR.
    		e.printStackTrace();
    	}

    }
     
	protected ClassLoader getClassLoader() throws ServletException {
		// Gets custom class loader
		final String classLoaderName = getApplicationOrSystemProperty(
				"ClassLoader", null);
		ClassLoader classLoader;
		if (classLoaderName == null) {
			classLoader = getClass().getClassLoader();
		} else {
			try {
				final Class<?> classLoaderClass = getClass().getClassLoader()
				.loadClass(classLoaderName);
				final Constructor<?> c = classLoaderClass
				.getConstructor(new Class[] { ClassLoader.class });
				classLoader = (ClassLoader) c
				.newInstance(new Object[] { getClass().getClassLoader() });
			} catch (final Exception e) {
				throw new ServletException(
						"Could not find specified class loader: "
						+ classLoaderName, e);
			}
		}
		return classLoader;
	}
	
	private String getApplicationOrSystemProperty(String parameterName,
			String defaultValue) {

		String val = null;

		// Try application properties
		val = getApplicationProperty(parameterName);
		if (val != null) {
			return val;
		}

		// Try system properties
		val = getSystemProperty(parameterName);
		if (val != null) {
			return val;
		}

		return defaultValue;
	}

	protected String getApplicationProperty(String parameterName) 
	{
		//FIXME: Research about application properties
		//return applicationProperties.getProperty(parameterName);
		return null;
	}
	
	protected String getSystemProperty(String parameterName) {
		String val = null;

		String pkgName;
		final Package pkg = getClass().getPackage();
		if (pkg != null) {
			pkgName = pkg.getName();
		} else {
			final String className = getClass().getName();
			pkgName = new String(className.toCharArray(), 0,
					className.lastIndexOf('.'));
		}
		val = System.getProperty(pkgName + "." + parameterName);
		if (val != null) {
			return val;
		}

		// Try lowercase system properties
		val = System.getProperty(pkgName + "." + parameterName.toLowerCase());
		return val;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void populatePagePool(ServletConfig servletConfig) throws ServletException
	{
		String separator = System.getProperty("file.separator");
		List<String> classes = new ClassFinder().getClassFiles(getServletContext().getRealPath("") + separator + "WEB-INF"+ separator + "classes");;
		
		for (String apclass : classes )
		{
			try {
				Class objClass = getClassLoader().loadClass(apclass);
				
				//Fixme change with page inheritance
				if (objClass.getGenericSuperclass() == WebPage.class)
				{
					Page pageAnnotation = (Page)objClass.getAnnotation(Page.class);
					if (pageAnnotation != null)
					{
						String relativePath = pageAnnotation.relativePath();
						
						if (pagePool.get(relativePath) != null) throw new 
							ServletException("Path in use in more than one page: " + relativePath);
						
						pagePool.put(relativePath.toLowerCase(), objClass);
					}
				}
				
			} catch (ClassNotFoundException e) {
				throw new ServletException("Error loading: " + apclass);
			}
		}	
	}
	
}	
	