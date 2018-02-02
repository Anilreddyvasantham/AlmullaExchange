package com.amg.exchange.wuh2h.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MessageDetail implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String wuMessage;
	public MessageDetail(){
		
	}
	public MessageDetail(String wuMessage) {
		this.wuMessage = wuMessage;
	}

	public String getWuMessage() {
		return wuMessage;
	}

	public void setWuMessage(String wuMessage) {
		this.wuMessage = wuMessage;
	}
	
	
	
}
