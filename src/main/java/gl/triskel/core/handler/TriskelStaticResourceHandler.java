package gl.triskel.core.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gl.triskel.core.Application;
import gl.triskel.core.RequestType;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;

public class TriskelStaticResourceHandler extends TriskelHandler{

	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	
	
	public TriskelStaticResourceHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response)
			throws UnableToLoadPageException, PageNotFoundException {
		
		//Process only if is a static resource, in other case, delegate to next handler
		if(app.getRequestType(request) == RequestType.WEB_RESOURCE )
		{
			
			try{
			final ServletContext sc = app.getServletContext();
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
			}catch (IOException e) {
				throw new UnableToLoadPageException(null);
			}

			
			
		}else{
			if (next == null)
			{
				throw new UnableToLoadPageException(null);
			}
			next.process(request, response);
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
