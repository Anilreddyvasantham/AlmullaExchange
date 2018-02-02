package com.amg.exchange.util;

public class AMGException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String exceptionMessage;
	public AMGException(){

	}

	public AMGException(String msg){
		exceptionMessage = msg;
	}


	public String getMessage() {
		return exceptionMessage;
	}


}
