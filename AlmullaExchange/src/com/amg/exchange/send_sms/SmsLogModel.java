package com.amg.exchange.send_sms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Clob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_SMS_LOG")
public class SmsLogModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal smsLogId;
	private String ecNo;
	private String IdNumber;
	private String otpNo;
	private String created_By;
	private Date created_Date;
	private Clob requestXml;
	private Clob responseXml;
	private String errorMsg;
	private String mobile;
	private BigDecimal customerId;
	private String ipAddress;
	private String loginType;
	private String otpType;
	
	public SmsLogModel() {
		super();
	}
	
	@Id
	@GeneratedValue(generator="ex_sms_log_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_sms_log_seq",sequenceName="EX_SMS_LOG_SEQ",allocationSize=1)
	@Column(name ="SMS_LOG_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getSmsLogId() {
		return smsLogId;
	}
	public void setSmsLogId(BigDecimal smsLogId) {
		this.smsLogId = smsLogId;
	}
	
	@Column(name = "EC_NO")
	public String getEcNo() {
		return ecNo;
	}
	public void setEcNo(String ecNo) {
		this.ecNo = ecNo;
	}
	
	@Column(name = "ID_NUMBER")
	public String getIdNumber() {
		return IdNumber;
	}
	public void setIdNumber(String idNumber) {
		IdNumber = idNumber;
	}
	
	@Column(name = "OTP_NO")
	public String getOtpNo() {
		return otpNo;
	}
	public void setOtpNo(String otpNo) {
		this.otpNo = otpNo;
	}
	
	@Column(name = "CREATED_BY")
	public String getCreated_By() {
		return created_By;
	}
	public void setCreated_By(String created_By) {
		this.created_By = created_By;
	} 
	
	@Column(name = "CREATED_DATE")
	public Date getCreated_Date() {
		return created_Date;
	}
	public void setCreated_Date(Date created_Date) {
		this.created_Date = created_Date;
	}
	
	@Column(name = "REQUEST_XML")
	public Clob getRequestXml() {
		return requestXml;
	}
	public void setRequestXml(Clob requestXml) {
		this.requestXml = requestXml;
	}
	
	@Column(name = "RESPONSE_XML")
	public Clob getResponseXml() {
		return responseXml;
	}
	public void setResponseXml(Clob responseXml) {
		this.responseXml = responseXml;
	}
	
	@Column(name = "RESPONSE_MSG")
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	@Column(name = "CUSTOMER_ID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "LOGIN_TYPE")
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	@Column(name = "OTP_TYPE")
	public String getOtpType() {
		return otpType;
	}
	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}
	
}
