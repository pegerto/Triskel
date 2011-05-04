package gl.triskel.core;

import gl.triskel.annotations.Parameter;
import gl.triskel.components.WebPage;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.handler.TriskelHandler;
import gl.triskel.core.handler.TriskelPageHandler;
import gl.triskel.core.util.HtmlFormatter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;



@SuppressWarnings("rawtypes")
public class Application implements Serializable {

	private static final long serialVersionUID = -2933467198850667085L;

	private static HashMap<String, Class> pagesClass;
	private static HashMap<String, WebPage> userPages;
	private static TriskelHandler handler;
	
	
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
		handler = new TriskelPageHandler(null, this);
	}
	
	
	
}
