package com.amg.exchange.util;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("customeError")
@Scope("session")
public class CustomExceptionHandle {
	private  String message="";
	

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public   void handleException (Exception exception){
		
		setMessage ("Exception Occured Because: "+exception.getMessage() );
		
		RequestContext.getCurrentInstance().execute("error.show();");
	
	}
}
