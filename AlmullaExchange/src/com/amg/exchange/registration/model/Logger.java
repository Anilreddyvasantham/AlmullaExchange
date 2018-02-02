package com.amg.exchange.registration.model;

// Generated Jun 6, 2014 6:13:24  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Logger Created by Chennai ODC
 */
@Entity
@Table(name = "FS_LOGGER")
public class Logger implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal logId;
	private BigDecimal sessionId;
	private BigDecimal transactionRef;
	private String applicationName;
	private String pageName;
	private String tableName;
	private String userName;
	private String ipAddress;
	private String oldValue;
	private String newValue;
	private Date modifiedDate;

	public Logger() {
	}

	public Logger(BigDecimal logId) {
		this.logId = logId;
	}

	public Logger(BigDecimal logId, BigDecimal sessionId,
			BigDecimal transactionRef, String applicationName, String pageName,
			String tableName, String userName, String ipAddress,
			String oldValue, String newValue, Date modifiedDate) {
		this.logId = logId;
		this.sessionId = sessionId;
		this.transactionRef = transactionRef;
		this.applicationName = applicationName;
		this.pageName = pageName;
		this.tableName = tableName;
		this.userName = userName;
		this.ipAddress = ipAddress;
		this.oldValue = oldValue;
		this.newValue = newValue;
		this.modifiedDate = modifiedDate;
	}

	@Id
	/*@TableGenerator(name = "logid", table = "fs_logidpk", pkColumnName = "logidkey", pkColumnValue = "logidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "logid")
	*/@GeneratedValue(generator="fs_logger_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_logger_seq" ,sequenceName="FS_LOGGER_SEQ",allocationSize=1)		
	@Column(name = "LOG_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLogId() {
		return this.logId;
	}

	public void setLogId(BigDecimal logId) {
		this.logId = logId;
	}

	@Column(name = "SESSION_ID", precision = 22, scale = 0)
	public BigDecimal getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(BigDecimal sessionId) {
		this.sessionId = sessionId;
	}

	@Column(name = "TRANSACTION_REF", precision = 22, scale = 0)
	public BigDecimal getTransactionRef() {
		return this.transactionRef;
	}

	public void setTransactionRef(BigDecimal transactionRef) {
		this.transactionRef = transactionRef;
	}

	@Column(name = "APPLICATION_NAME", length = 200)
	public String getApplicationName() {
		return this.applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Column(name = "PAGE_NAME", length = 200)
	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	@Column(name = "TABLE_NAME", length = 50)
	public String getTableName() {
		return this.tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "USER_NAME", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "IP_ADDRESS", length = 30)
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Column(name = "OLD_VALUE", length = 200)
	public String getOldValue() {
		return this.oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	@Column(name = "NEW_VALUE", length = 200)
	public String getNewValue() {
		return this.newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
