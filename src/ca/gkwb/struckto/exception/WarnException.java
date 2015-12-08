package ca.gkwb.struckto.exception;

public class WarnException extends Exception {
	  public WarnException() { super(); }
	  public WarnException(String message) { super(message); }
	  public WarnException(String message, Throwable cause) { super(message, cause); }
	  public WarnException(Throwable cause) { super(cause); }	
}
