package gl.triskel.core.exceptions;


public class PageNotFoundException extends Exception{

	private static final long serialVersionUID = 1L;

	private String pageName;
	
	public PageNotFoundException(String pageName) {
		this.pageName = pageName;
	}
	
	public String toString()
	{
		return "Page not found in application:  " + pageName;
	}
	
	
}
