/**
 * 
 */
package gl.triskel.core.visitor;

import gl.triskel.components.Component;
import gl.triskel.components.Image;
import gl.triskel.components.Label;
import gl.triskel.components.WebPage;
import gl.triskel.components.form.Form;
import gl.triskel.components.form.SubmitButton;
import gl.triskel.components.form.TextField;
import gl.triskel.components.interfaces.WebPageVisitor;
import gl.triskel.components.link.Link;
import gl.triskel.core.TriskelRequest;

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
public class ParameterVisitor implements WebPageVisitor{

	private TriskelRequest request;
	
	public ParameterVisitor(TriskelRequest request)
	{
		this.request = request;
	}
	
	
	public void visit(WebPage page){
		if(page.hasChildrens()) 
		{
			for(Component component : page.getComponets())
			{
				component.accept(this);
			}
		}
	}

	public void visit(Form form) {
		if(form.hasChildrens()) 
		{
			for(Component component : form.getComponets())
			{
				component.accept(this);
			}
		}	
	}
	
	public void visit(TextField textfield) {
		if (request.getParameter(textfield.getId()) != null)
			textfield.setText(request.getParameter(textfield.getId()));
	}


	/* (non-Javadoc)
	 * @see gl.triskel.components.interfaces.WebPageVisitor#visit(gl.triskel.components.Label)
	 */
	@Override
	public void visit(Label label) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see gl.triskel.components.interfaces.WebPageVisitor#visit(gl.triskel.components.Image)
	 */
	@Override
	public void visit(Image image) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see gl.triskel.components.interfaces.WebPageVisitor#visit(gl.triskel.components.form.SubmitButton)
	 */
	@Override
	public void visit(SubmitButton submitButton) {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see gl.triskel.components.interfaces.WebPageVisitor#visit(gl.triskel.components.link.Link)
	 */
	@Override
	public void visit(Link link) {
		// TODO Auto-generated method stub
		
	}
	

}
