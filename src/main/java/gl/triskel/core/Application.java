package gl.triskel.core;

import gl.triskel.annotations.Parameter;
import gl.triskel.components.WebPage;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
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
	
	public Application() {
		userPages = new HashMap<String, WebPage>();
	}
	
	@SuppressWarnings("static-access")
	protected void setPagesClass(HashMap<String, Class> pagesClass)
	{
		this.pagesClass = pagesClass;
	}
	
	protected void servePage(HttpServletResponse response, HttpServletRequest request) 
		throws IOException, UnableToLoadPageException, PageNotFoundException
	{
		String pagename = request.getRequestURI().substring(request.getContextPath().length() + 1);
		pagename = pagename.toLowerCase();
		
		//Check if exists page in application
		if (!pagesClass.containsKey(pagename))
			throw new PageNotFoundException(pagename);
		
		PrintWriter print = response.getWriter();
		Class page = pagesClass.get(pagename);
	
		WebPage spage = null;
		
		if (!userPages.containsKey(pagename))
		{
			try {
				spage = (WebPage)page.newInstance();
				userPages.put(pagename, spage);
			} catch (InstantiationException e) {
				throw new UnableToLoadPageException(e);
			} catch (IllegalAccessException e) {
				throw new UnableToLoadPageException(e);
			}
		}else{
			spage = userPages.get(pagename);
		}
		
		//configure parameters.
		configureParameters(spage, request);
		spage.onLoad();
		
		Document doc = new DocumentImpl();
		doc.appendChild(spage.render(doc));
		
		print.write(HtmlFormatter.format(doc));
	
	}
	
	
	private void configureParameters(WebPage page, HttpServletRequest request)
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
					//Logging to debug, but dosen�t mather.
				} catch (IllegalAccessException e) {
					//Logging to debug, but dosen�t mather.
			}
		}
	}
	
}
