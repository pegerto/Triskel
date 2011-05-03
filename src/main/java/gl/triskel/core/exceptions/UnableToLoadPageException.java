package gl.triskel.core.exceptions;

public class UnableToLoadPageException extends Exception {
	private static final long serialVersionUID = 1L;
	private Exception nestedException;
	
	public UnableToLoadPageException(Exception nestedException) {
		this.nestedException = nestedException;
	}
}
