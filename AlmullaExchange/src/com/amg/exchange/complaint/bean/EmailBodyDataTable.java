package com.amg.exchange.complaint.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class EmailBodyDataTable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal emailId;
	private BigDecimal complaintReferenceNo;
	private BigDecimal emailRemittanceYear;
	private BigDecimal emailRemittanceDocNo;
	private String emailCC;
	private String emailBody;
	
	public BigDecimal getEmailId() {
		return emailId;
	}
	
	public void setEmailId(BigDecimal emailId) {
		this.emailId = emailId;
	}

	public String getEmailCC() {
		return emailCC;
	}
	
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	
	public String getEmailBody() {
		return emailBody;
	}
	
	public void setEmailBody(String emailBody) {
		this.emailBody = emailBody;
	}

	public BigDecimal getComplaintReferenceNo() {
		return complaintReferenceNo;
	}

	public void setComplaintReferenceNo(BigDecimal complaintReferenceNo) {
		this.complaintReferenceNo = complaintReferenceNo;
	}

	public BigDecimal getEmailRemittanceYear() {
		return emailRemittanceYear;
	}

	public void setEmailRemittanceYear(BigDecimal emailRemittanceYear) {
		this.emailRemittanceYear = emailRemittanceYear;
	}

	public BigDecimal getEmailRemittanceDocNo() {
		return emailRemittanceDocNo;
	}

	public void setEmailRemittanceDocNo(BigDecimal emailRemittanceDocNo) {
		this.emailRemittanceDocNo = emailRemittanceDocNo;
	}
	
}
