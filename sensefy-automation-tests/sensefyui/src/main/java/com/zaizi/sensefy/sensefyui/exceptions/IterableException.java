package com.zaizi.sensefy.sensefyui.exceptions;

public class IterableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public IterableException () {

    }

    /**
     * @param message
     */
    public IterableException (String message) {
        super (message);
    }

    /**
     * @param cause
     */
    public IterableException (Throwable cause) {
        super (cause);
    }

    /**
     * @param message
     * @param cause
     */
    public IterableException (String message, Throwable cause) {
        super (message, cause);
    }

}
