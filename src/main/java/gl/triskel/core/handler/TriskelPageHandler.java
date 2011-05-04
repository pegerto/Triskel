package gl.triskel.core.handler;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;

import com.sun.org.apache.xerces.internal.dom.DocumentImpl;

import gl.triskel.annotations.Parameter;
import gl.triskel.components.WebPage;
import gl.triskel.core.Application;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.util.HtmlFormatter;

public class TriskelPageHandler extends TriskelHandler{

	public TriskelPageHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	
	public void process(HttpServletRequest request, HttpServletResponse response) throws PageNotFoundException,
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
				spage = (WebPage)page.newInstance();
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
					//Logging to debug, but dosen't mather.
				} catch (IllegalAccessException e) {
					//Logging to debug, but dosen't mather.
			}
		}
	}

	
	
}
