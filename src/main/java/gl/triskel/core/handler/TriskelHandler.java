package gl.triskel.core.handler;

import gl.triskel.core.Application;
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
public abstract class TriskelHandler {

	protected TriskelHandler next;
	protected Application app;
	
	public TriskelHandler(TriskelHandler next, Application app)
	{
		this.next = next;
		this.app = app;
	}
	
	
	public abstract void process(TriskelRequest request, TriskelResponse response)
		throws UnableToLoadPageException, PageNotFoundException ;
	
}
