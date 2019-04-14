package com.cuccatti.inventory.exception;

public class JSONParseException extends RuntimeException {
	private static final long serialVersionUID = 4178607725895402133L;
	
	public JSONParseException() {
	        super();
	    }
	    public JSONParseException(String message, Throwable cause) {
	        super(message, cause);
	    }
	    public JSONParseException(String message) {
	        super(message);
	    }
	    public JSONParseException(Throwable cause) {
	        super(cause);
	    }
	}