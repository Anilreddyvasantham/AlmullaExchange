package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;

/**
 * ExMessage Created by Chennai ODC
 */
@Entity
@Table(name = "EX_MESSAGE")
public class Message implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6574053513365351225L;
	private BigDecimal messageId;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;
	private byte documentCode;
	private short documentFinanceYr;
	private long documentNo;
	private short documentLineNo;
	private short messageLineNo;
	private String messageDesc;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;

	public Message() {
	}

	public Message(BigDecimal messageId, byte documentCode,
			short documentFinanceYr, long documentNo, short documentLineNo,
			short messageLineNo) {
		this.messageId = messageId;
		this.documentCode = documentCode;
		this.documentFinanceYr = documentFinanceYr;
		this.documentNo = documentNo;
		this.documentLineNo = documentLineNo;
		this.messageLineNo = messageLineNo;
	}

	public Message(BigDecimal messageId, CompanyMaster fsCompanyMaster,
			CountryMaster fsCountryMaster, byte documentCode,
			short documentFinanceYr, long documentNo, short documentLineNo,
			short messageLineNo, String messageDesc, String isactive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate) {
		this.messageId = messageId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.documentCode = documentCode;
		this.documentFinanceYr = documentFinanceYr;
		this.documentNo = documentNo;
		this.documentLineNo = documentLineNo;
		this.messageLineNo = messageLineNo;
		this.messageDesc = messageDesc;
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;

	}
	
	@Id
	@GeneratedValue(generator="ex_message_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_message_seq",sequenceName="EX_MESSAGE_SEQ",allocationSize=1)
	@Column(name = "MESSAGE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getMessageId() {
		return this.messageId;
	}

	public void setMessageId(BigDecimal messageId) {
		this.messageId = messageId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "DOCUMENT_CODE", nullable = false, precision = 2, scale = 0)
	public byte getDocumentCode() {
		return this.documentCode;
	}

	public void setDocumentCode(byte documentCode) {
		this.documentCode = documentCode;
	}

	@Column(name = "DOCUMENT_FINANCE_YR", nullable = false, precision = 4, scale = 0)
	public short getDocumentFinanceYr() {
		return this.documentFinanceYr;
	}

	public void setDocumentFinanceYr(short documentFinanceYr) {
		this.documentFinanceYr = documentFinanceYr;
	}

	@Column(name = "DOCUMENT_NO", nullable = false, precision = 14, scale = 0)
	public long getDocumentNo() {
		return this.documentNo;
	}

	public void setDocumentNo(long documentNo) {
		this.documentNo = documentNo;
	}

	@Column(name = "DOCUMENT_LINE_NO", nullable = false, precision = 4, scale = 0)
	public short getDocumentLineNo() {
		return this.documentLineNo;
	}

	public void setDocumentLineNo(short documentLineNo) {
		this.documentLineNo = documentLineNo;
	}

	@Column(name = "MESSAGE_LINE_NO", nullable = false, precision = 3, scale = 0)
	public short getMessageLineNo() {
		return this.messageLineNo;
	}

	public void setMessageLineNo(short messageLineNo) {
		this.messageLineNo = messageLineNo;
	}

	@Column(name = "MESSAGE_DESC", length = 100)
	public String getMessageDesc() {
		return this.messageDesc;
	}

	public void setMessageDesc(String messageDesc) {
		this.messageDesc = messageDesc;
	}

	@Column(name = "ISACTIVE", length = 1)
	public String getIsactive() {
		return this.isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "CREATED_BY", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY", length = 15)
	public String getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return this.modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

}
