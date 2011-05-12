package gl.triskel.components;

import gl.triskel.components.interfaces.Renderizable;
import gl.triskel.components.interfaces.WebPageVisitor;

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
public abstract class Component implements Renderizable{

	private String id;
	private Component parent;

	public Component()
	{}
	
	public Component(String id)
	{
		this.id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setParent(Component parent) {
		this.parent = parent;
	}

	public Component getParent() {
		return parent;
	}
	
	/**
	 * Method for accept visitor pattern into the webpage structure.
	 * @param visitor 
	 */
	public abstract void accept(WebPageVisitor visitor);
	
}
