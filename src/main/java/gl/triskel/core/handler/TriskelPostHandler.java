/**
 * 
 */
package gl.triskel.core.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import gl.triskel.core.Application;
import gl.triskel.core.TriskelRequest;
import gl.triskel.core.TriskelResponse;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;
import gl.triskel.core.visitor.ParameterVisitor;

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
public class TriskelPostHandler extends TriskelHandler{

	/**
	 * @param next
	 * @param app
	 */
	public TriskelPostHandler(TriskelHandler next, Application app) {
		super(next, app);
	}

	/* (non-Javadoc)
	 * @see gl.triskel.core.handler.TriskelHandler#process(gl.triskel.core.TriskelRequest, gl.triskel.core.TriskelResponse)
	 */
	@Override
	public void process(TriskelRequest request, TriskelResponse response)
			throws UnableToLoadPageException, PageNotFoundException {
		
		
		//rewrite parameters into the components using visitor
		request.getPage().accept(new ParameterVisitor(request));
		request.getPage().onLoad();
		
		
		//Process action.
		try {
			Method defaultPostAction = request.getPage().getClass().getMethod("onSuccess", null);
			defaultPostAction.invoke(request.getPage(), null);
			
		} catch (SecurityException e) {
			// TODO Check how to proceed
		} catch (NoSuchMethodException e) {
			//No method in the 
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

	
	
	
}
