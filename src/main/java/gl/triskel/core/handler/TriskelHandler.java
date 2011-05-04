package gl.triskel.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import gl.triskel.core.Application;
import gl.triskel.core.exceptions.PageNotFoundException;
import gl.triskel.core.exceptions.UnableToLoadPageException;

public abstract class TriskelHandler {

	protected TriskelHandler next;
	protected Application app;
	
	public TriskelHandler(TriskelHandler next, Application app)
	{
		this.next = next;
		this.app = app;
	}
	
	
	public abstract void process(HttpServletRequest request, HttpServletResponse response)
		throws UnableToLoadPageException, PageNotFoundException ;
	
}
