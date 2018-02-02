package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * BankAccountLength Created by Chennai ODC
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EX_BANK_ACCOUNT_LENGTH")
public class BankAccountLength implements java.io.Serializable {

	private BigDecimal accountLenId;
	private BankMaster bankMaster;
	private BigDecimal acLength;
	private String recordStatus;
	private Date createDate;
//	private Date updateDate;
	private String creator;
//	private String modifier;
	private String remarks;

	public BankAccountLength() {
	}

	public BankAccountLength(BigDecimal accountLenId) {
		this.accountLenId = accountLenId;
	}
	


	public BankAccountLength(BigDecimal accountLenId, BankMaster bankMaster,
			BigDecimal acLength, String recordStatus, Date createDate,
			String creator, String remarks) {
		super();
		this.accountLenId = accountLenId;
		this.bankMaster = bankMaster;
		this.acLength = acLength;
		this.recordStatus = recordStatus;
		this.createDate = createDate;
		this.creator = creator;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator="ex_bank_account_length_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_account_length_seq",sequenceName="EX_BANK_ACCOUNT_LENGTH_SEQ",allocationSize=1)
	@Column(name = "ACCOUNT_LENGTH_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getAccountLenId() {
		return this.accountLenId;
	}

	public void setAccountLenId(BigDecimal accountLenId) {
		this.accountLenId = accountLenId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_ID")
	public BankMaster getBankMaster() {
		return bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}
	


	@Column(name = "ACCOUNT_LENGTH", precision = 22, scale = 0)
	public BigDecimal getAcLength() {
		return this.acLength;
	}

	public void setAcLength(BigDecimal acLength) {
		this.acLength = acLength;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

/*	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}*/

	@Column(name = "CREATED_BY")
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

/*	@Column(name = "MODIFIER", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}*/
	
	

}
