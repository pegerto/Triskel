package gl.triskel.core.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
