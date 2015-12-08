package ca.gkwb.struckto.exception;

public class FatalException extends Exception {
	  public FatalException() { super(); }
	  public FatalException(String message) { super(message); }
	  public FatalException(String message, Throwable cause) { super(message, cause); }
	  public FatalException(Throwable cause) { super(cause); }	
}
