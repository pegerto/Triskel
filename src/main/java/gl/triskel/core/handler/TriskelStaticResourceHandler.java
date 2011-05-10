package gl.triskel.core.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;


import gl.triskel.core.Application;
import gl.triskel.core.RequestType;
import gl.triskel.core.TriskelRequest;
import gl.triskel.core.TriskelResponse;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;

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
public class TriskelStaticResourceHandler extends TriskelHandler{

	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	
	
	public TriskelStaticResourceHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	@Override
	public void process(TriskelRequest request, TriskelResponse response)
			throws UnableToLoadPageException, PageNotFoundException {
		
		//Process only if is a static resource, in other case, delegate to next handler
		if(app.getRequestType(request) == RequestType.WEB_RESOURCE )
		{
			
			try{
			URL file = app.getTriskelContext().getResource(request.getRequestURI().toString().substring(app.getTriskelContext().getContextPath().length()));
			
			//Resource not found
			if (file == null)
			{
				response.setStatus(TriskelResponse.SC_NOT_FOUND);
			}else{
				long lastModifiedTime = file.openConnection().getLastModified();
				lastModifiedTime = lastModifiedTime - lastModifiedTime % 1000;
				if (browserHasNewestVersion(request, lastModifiedTime)) {
					response.setStatus(TriskelResponse.SC_NOT_MODIFIED);
					
				}else{
					final String mimetype = app.getTriskelContext().getMimeType(file.toString());
					
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
	
	private boolean browserHasNewestVersion(TriskelRequest request,
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
