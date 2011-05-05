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



@SuppressWarnings("rawtypes")
public class Application implements Serializable {

	private static final long serialVersionUID = -2933467198850667085L;

	private static HashMap<String, Class> pagesClass;
	private static HashMap<String, WebPage> userPages;
	private static TriskelHandler handler;
	private static ServletContext context;
	
	
	public static HashMap<String, Class> getPagesClass() {
		return pagesClass;
	}

	public static HashMap<String, WebPage> getUserPages() {
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
	
	
	protected void process(HttpServletRequest request, HttpServletResponse response) throws UnableToLoadPageException, PageNotFoundException
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
	
	
	public RequestType getRequestType(HttpServletRequest request)
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
	
	public ServletContext getServletContext()
	{
		return context;
	}
	
	public void setServletContext(ServletContext context)
	{
		this.context = context;
	}
	
	
}
