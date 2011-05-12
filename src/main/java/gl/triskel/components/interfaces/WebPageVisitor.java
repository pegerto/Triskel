/**
 * 
 */
package gl.triskel.components.interfaces;

import gl.triskel.components.Component;
import gl.triskel.components.Image;
import gl.triskel.components.Label;
import gl.triskel.components.WebPage;
import gl.triskel.components.form.Form;
import gl.triskel.components.form.SubmitButton;
import gl.triskel.components.form.TextField;
import gl.triskel.components.link.Link;

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
 * Interface for visitor pattern.
 *
 */
public interface WebPageVisitor {
	
	public void visit(WebPage webpage);
	public void visit(Form form);
	public void visit(TextField textfield);
	public void visit(Label label);
	public void visit(Image image);
	public void visit(SubmitButton submitButton);
	public void visit(Link link);

}
