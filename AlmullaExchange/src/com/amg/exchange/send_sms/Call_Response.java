package com.amg.exchange.send_sms;

import java.io.Serializable;

public class Call_Response implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	boolean result;
	String response_desc, request_xml, response_xml;

	public String getRequest_xml() {
		return request_xml;
	}

	public void setRequest_xml(String request_xml) {
		this.request_xml = request_xml;
	}

	public String getResponse_xml() {
		return response_xml;
	}

	public void setResponse_xml(String response_xml) {
		this.response_xml = response_xml;
	}

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getResponse_desc() {
		return response_desc;
	}

	public void setResponse_desc(String response_desc) {
		this.response_desc = response_desc;
	}

	@Override
	public String toString() {
		return "Call_Response [result=" + result + ", response_desc="
				+ response_desc + "]";
	}
}