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
 * Servlet implementation class TrisquelServlet
 */
public class TriskelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String APP = "APP";
	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	
	
	private Class<? extends Application > applicationClass;
	private HashMap<String, Class> pagePool;  
	
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
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TriskelServlet() {
        super();
        
        pagePool = new HashMap<String, Class>();
    }

    
    
    protected void service(HttpServletRequest request,
  	            HttpServletResponse response) throws ServletException, IOException {
    	
    	 HttpSession session = request.getSession();
    	 Application app = (Application) session.getAttribute(APP);
    	 
    	 switch (getRequestType(request)) {
    	 	case APPLICATION_PAGE:
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
					app.servePage(response,request);
				} catch (UnableToLoadPageException e) {
					//TODO GESTIONAR ERROR GRAVE APPLICACION.
					e.printStackTrace();
				} catch (PageNotFoundException e) {
					// TODO GESTIONAR PAGINA ERROR.
					e.printStackTrace();
				}
				 break;
    	 	case WEB_RESOURCE:
    	 		serveStaticResource(request, response);
    	 		break;
    	 		
    	 	case FORM_POST:
    	 		//load the data into the components.
    	 		
    	 		
    	 		//execute page acction method.
    	 		break;
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
	
	protected RequestType getRequestType(HttpServletRequest request)
	{
		String path = request.getRequestURL().toString().toLowerCase();
		
		if (request.getMethod().equals("GET"))
		{
			if(path.endsWith(ResourceConstants.HTM_FILE) 
					||path.endsWith(ResourceConstants.HTML_FILE)
					||path.endsWith(ResourceConstants.JPG_FILE)
					||path.endsWith(ResourceConstants.GIF_FILE)
					||path.endsWith(ResourceConstants.GIF_FILE)
					||path.endsWith(ResourceConstants.CSS_FILE)) return RequestType.WEB_RESOURCE;
		
		
			return RequestType.APPLICATION_PAGE;
		}else if (request.getMethod().equals("POST"))
		{
			return RequestType.FORM_POST;
		}else
		{
			//Miento :(
			return RequestType.APPLICATION_PAGE;
		}
		
	}
	
	protected void serveStaticResource(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		final ServletContext sc = getServletContext();
		URL file = sc.getResource(request.getRequestURI().toString().substring(sc.getContextPath().length()));
		
		//Resource not found
		if (file == null)
		{
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}else{
			long lastModifiedTime = file.openConnection().getLastModified();
			lastModifiedTime = lastModifiedTime - lastModifiedTime % 1000;
			if (browserHasNewestVersion(request, lastModifiedTime)) {
				response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
				
			}else{
				final String mimetype = sc.getMimeType(file.toString());
				
				if (mimetype != null) {
					response.setContentType(mimetype);
				}
				
				if (lastModifiedTime > 0) {
					response.setDateHeader("Last-Modified", lastModifiedTime);
				}
				
				//Serve file
				final OutputStream os = response.getOutputStream();
				final byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
				int bytes;
				InputStream is = file.openStream();
				while ((bytes = is.read(buffer)) >= 0) {
					os.write(buffer, 0, bytes);
				}
				is.close();
			}
		}
	}
	
	private boolean browserHasNewestVersion(HttpServletRequest request,
			long resourceLastModifiedTimestamp) {

		if (resourceLastModifiedTimestamp < 1) {
			return false;
		}

		try {	  
			long headerIfModifiedSince = request
			.getDateHeader("If-Modified-Since");
			if (headerIfModifiedSince >= resourceLastModifiedTimestamp) {
				return true;
			}
		} catch (Exception e) {
		}
		return false;
	}

}


