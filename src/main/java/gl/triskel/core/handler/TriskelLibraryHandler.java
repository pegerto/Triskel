/**
 * 
 */
package gl.triskel.core.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import gl.triskel.core.Application;
import gl.triskel.core.TriskelRequest;
import gl.triskel.core.TriskelResponse;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.util.ResourceConstants;

/**
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
public class TriskelLibraryHandler extends TriskelHandler{

	static final int DEFAULT_BUFFER_SIZE = 32 * 1024;
	static final String TRISKELJSLIBRARY_PREFIX = "TRISKELJSLIBRARY";
	
	/**
	 * @param next, following handler
	 * @param app Application instance
	 */
	public TriskelLibraryHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	/* (non-Javadoc)
	 * @see gl.triskel.core.handler.TriskelHandler#process(gl.triskel.core.TriskelRequest, gl.triskel.core.TriskelResponse)
	 */
	@Override
	public void process(TriskelRequest request, TriskelResponse response)
			throws UnableToLoadPageException, PageNotFoundException {
		
		
			if ( request.getRequestURI().endsWith(ResourceConstants.JS_FILE)
				 && request.getRequestURI().contains(TRISKELJSLIBRARY_PREFIX))
			{
				//Serve library file.
				try {
					
					URL resource = app.getTriskelContext().getResource(
								   		request.getRequestURI().substring(
								   				request.getRequestURI().indexOf(TRISKELJSLIBRARY_PREFIX)
								   				+ TRISKELJSLIBRARY_PREFIX.length() + 1));
					
					if (resource == null)
					{
						resource = getClass().getClassLoader().getResource(
								   		request.getRequestURI().substring(
								   				request.getRequestURI().indexOf(TRISKELJSLIBRARY_PREFIX)
								   				+ TRISKELJSLIBRARY_PREFIX.length() + 1));
					}
					
					if (resource == null)
					{
						// FIXME Control error. 
						//We need a log system right now.
						//Or a generic exception to throw.
						
					}
										
				
				       // Write the resource to the client.
					OutputStream os;
					os = response.getOutputStream();
					final byte buffer[] = new byte[DEFAULT_BUFFER_SIZE];
					int bytes;
					InputStream is = resource.openStream();
					while ((bytes = is.read(buffer)) >= 0) {
						os.write(buffer, 0, bytes);
					}
					is.close();
				
				
				} catch (MalformedURLException e) {
					throw new UnableToLoadPageException(e);
				} catch (IOException e) {
					throw new UnableToLoadPageException(e);
				}
				
			}else
			{
				if (next != null) next.process(request, response); 
			}
		
		
	}

	
	
	
}
