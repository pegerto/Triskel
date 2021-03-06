/**
 * 
 */
package gl.triskel.core.util;

import java.lang.reflect.Field;

import gl.triskel.annotations.LabelConfig;
import gl.triskel.annotations.LinkConfig;
import gl.triskel.annotations.TextFieldConfig;
import gl.triskel.components.Label;
import gl.triskel.components.WebPage;
import gl.triskel.components.form.TextField;
import gl.triskel.components.link.Link;
import gl.triskel.components.link.LinkParameter;

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
public class PageConfiguration {
	
	public static void configure(WebPage page)
	{
		boolean accessible;
		
		try {
			for(Field field : page.getClass().getDeclaredFields())
			{
				if (field.getType().equals(TextField.class))
				{
					TextFieldConfig annotation = field.getAnnotation(TextFieldConfig.class);
					
					if (annotation != null)
					{
						accessible = field.isAccessible();
						field.setAccessible(true);
					
						TextField textfield = new TextField();
						textfield.setId(annotation.id());
						textfield.setType(annotation.type());
						textfield.setCaption(annotation.caption());
						textfield.setPassword(annotation.password());
						field.set(page, textfield);
						field.setAccessible(accessible);
					}				
				}else if (field.getType().equals(Label.class))
				{
					LabelConfig annotation = field.getAnnotation(LabelConfig.class);
					
					if (annotation != null)
					{
						accessible = field.isAccessible();
						field.setAccessible(true);
						
						Label label = new Label();
						label.setId(annotation.id());
						label.setText(annotation.text());
						field.set(page, label);
						field.setAccessible(accessible);
					}
				}else if (field.getType().equals(Link.class))
				{
					LinkConfig annotation = field.getAnnotation(LinkConfig.class);
					if (annotation != null)
					{
						accessible = field.isAccessible();
						field.setAccessible(true);
						
						Link link = new Link();
						link.setId(annotation.id());
						link.setPage(annotation.page());
						link.setText(annotation.text());
						field.set(page, link);
						field.setAccessible(accessible);
					}
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
}
