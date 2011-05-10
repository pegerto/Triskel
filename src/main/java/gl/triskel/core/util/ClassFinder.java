package gl.triskel.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
public class ClassFinder {

	private List<String> foundClasses;
	private String rootDirectory;
	
	public ClassFinder()
	{
		foundClasses = new ArrayList<String>();
	}
	
	
	public List<String> getClassFiles(String rootPackage)
 	{
		
		//save original directory to convert the class in packages.
		if (rootDirectory == null ) rootDirectory = rootPackage;
		
		File currentDir = new File(rootPackage);
		File [] files = currentDir.listFiles();
		
		for(File file : files)
		{
			if (file.isDirectory()) 
			{
				getClassFiles(file.getAbsolutePath());
			}else{
				if(file.getAbsolutePath().endsWith(".class"))
				{
					String fclass = file.getAbsolutePath();
					
					//convert filename in package.
					fclass = fclass.substring(rootDirectory.length() + 1);
					fclass = fclass.replace("/", ".").replace("\\",".");
					fclass = fclass.substring(0,fclass.length() - 6 );
					
					foundClasses.add(fclass);
					
				}
			}
		}
		
		return foundClasses;
 	}
}
