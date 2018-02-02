package com.amg.exchange.remittance.model;

import java.io.Serializable;
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

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.Customer;

@Entity
@Table(name = "EX_KNET_DOWNLOAD")
public class KnetDownload implements Serializable {

	

	
	@Id
	@Column(name="APPROVAL_NO")
	private BigDecimal approvalNumber;
	
	@Column(name="CARD_NUMBER")
	private String cardNumber = null;
	
	
	@Column(name="COL_COMCOD")
	private BigDecimal colComCode;
	
	@Column(name="COL_DOCFYR")
	private BigDecimal coldocFyr;
	
	
	@Column(name="COL_DOCNO")
	private BigDecimal colDocNo;
	
	
	@Column(name="COL_DOCCOD")
	private BigDecimal colDocCode;
	
	@Column(name="CREATOR")
	private String creator;
	
	@Column(name="MODIFIER")
	private String modifier;
	
	@Column(name="CRTDAT")
	private Date crtDate;
	
 
	@Column(name="CUSREF")
	private BigDecimal customerReference = null;
	
	@Column(name="ERROR_MESSAGE")
	private String errorMessage;
	
	@Column(name="FM_DOCCOD")
	private BigDecimal fmDocCode ;
	
	
	@Column(name="FM_DOCFYR")
	private BigDecimal fmDocFyr ;
	
	
	@Column(name="FM_DOCNO")
	private BigDecimal fmDocNumber ;
	
    @Column(name="MERCHANT_TRACK_ID")	
	private String merchantTranckId = null;
	
    @Id
    @Column(name="ORDER_ID")
    private String orderId = null;
    
    @Column(name="RESULT_CODE")
    private String resultCode = null;
    @Column(name="REFERENCE_ID")
	private String referenceId = null;
    
    @Column(name="TRANSACTION_ID")
	private String transactionId = null;

    @Column(name="USER_FIELD1")
    private String userField1;
    
    @Column(name="USER_FIELD2")
	private String userField2;
    @Column(name="USER_FIELD3")
	private String userField3;
    @Column(name="USER_FIELD4")
	private String userField4;
    @Column(name="USER_FIELD5")
	private String userField5;
    
    @Column(name="TRANSACTION_DATE")
    private Date trnxDate = null;
    
	
    @Column(name="TRANSACTION_AMT")
    private BigDecimal trnxAmount = null;
    
    @Column(name="STATUS")
    private String status = null;
    
    
    @Column(name="TRNX_COMCOD")
    private BigDecimal trnxComCod;
    
    @Column(name="TRNX_DOCCOD")
    private BigDecimal trnxDocCod;
    
    @Column(name="TRNX_DOCFYR")
    private BigDecimal trnxdocFyr;
    
    @Column(name="TRNX_DOCNO")
    private BigDecimal trnxDocNo;
    
    @Column(name="UPD_IND")
    private String updateIndicator;
    
    
    @Column(name="SERIAL_NO")
    private BigDecimal srNo;


	public BigDecimal getApprovalNumber() {
		return approvalNumber;
	}


	public void setApprovalNumber(BigDecimal approvalNumber) {
		this.approvalNumber = approvalNumber;
	}


	public String getCardNumber() {
		return cardNumber;
	}


	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public BigDecimal getColComCode() {
		return colComCode;
	}


	public void setColComCode(BigDecimal colComCode) {
		this.colComCode = colComCode;
	}


	public BigDecimal getColdocFyr() {
		return coldocFyr;
	}


	public void setColdocFyr(BigDecimal coldocFyr) {
		this.coldocFyr = coldocFyr;
	}


	public BigDecimal getColDocNo() {
		return colDocNo;
	}


	public void setColDocNo(BigDecimal colDocNo) {
		this.colDocNo = colDocNo;
	}


	public BigDecimal getColDocCode() {
		return colDocCode;
	}


	public void setColDocCode(BigDecimal colDocCode) {
		this.colDocCode = colDocCode;
	}


	public String getCreator() {
		return creator;
	}


	public void setCreator(String creator) {
		this.creator = creator;
	}


	public String getModifier() {
		return modifier;
	}


	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	public Date getCrtDate() {
		return crtDate;
	}


	public void setCrtDate(Date crtDate) {
		this.crtDate = crtDate;
	}


	public BigDecimal getCustomerReference() {
		return customerReference;
	}


	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public BigDecimal getFmDocCode() {
		return fmDocCode;
	}


	public void setFmDocCode(BigDecimal fmDocCode) {
		this.fmDocCode = fmDocCode;
	}


	public BigDecimal getFmDocFyr() {
		return fmDocFyr;
	}


	public void setFmDocFyr(BigDecimal fmDocFyr) {
		this.fmDocFyr = fmDocFyr;
	}


	public BigDecimal getFmDocNumber() {
		return fmDocNumber;
	}


	public void setFmDocNumber(BigDecimal fmDocNumber) {
		this.fmDocNumber = fmDocNumber;
	}


	public String getMerchantTranckId() {
		return merchantTranckId;
	}


	public void setMerchantTranckId(String merchantTranckId) {
		this.merchantTranckId = merchantTranckId;
	}


	public String getOrderId() {
		return orderId;
	}


	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}


	public String getReferenceId() {
		return referenceId;
	}


	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}


	public String getTransactionId() {
		return transactionId;
	}


	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}


	public String getUserField1() {
		return userField1;
	}


	public void setUserField1(String userField1) {
		this.userField1 = userField1;
	}


	public String getUserField2() {
		return userField2;
	}


	public void setUserField2(String userField2) {
		this.userField2 = userField2;
	}


	public String getUserField3() {
		return userField3;
	}


	public void setUserField3(String userField3) {
		this.userField3 = userField3;
	}


	public String getUserField4() {
		return userField4;
	}


	public void setUserField4(String userField4) {
		this.userField4 = userField4;
	}


	public String getUserField5() {
		return userField5;
	}


	public void setUserField5(String userField5) {
		this.userField5 = userField5;
	}


	public Date getTrnxDate() {
		return trnxDate;
	}


	public void setTrnxDate(Date trnxDate) {
		this.trnxDate = trnxDate;
	}


	public BigDecimal getTrnxAmount() {
		return trnxAmount;
	}


	public void setTrnxAmount(BigDecimal trnxAmount) {
		this.trnxAmount = trnxAmount;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public BigDecimal getTrnxComCod() {
		return trnxComCod;
	}


	public void setTrnxComCod(BigDecimal trnxComCod) {
		this.trnxComCod = trnxComCod;
	}


	public BigDecimal getTrnxDocCod() {
		return trnxDocCod;
	}


	public void setTrnxDocCod(BigDecimal trnxDocCod) {
		this.trnxDocCod = trnxDocCod;
	}


	public BigDecimal getTrnxdocFyr() {
		return trnxdocFyr;
	}


	public void setTrnxdocFyr(BigDecimal trnxdocFyr) {
		this.trnxdocFyr = trnxdocFyr;
	}


	public BigDecimal getTrnxDocNo() {
		return trnxDocNo;
	}


	public void setTrnxDocNo(BigDecimal trnxDocNo) {
		this.trnxDocNo = trnxDocNo;
	}


	public String getUpdateIndicator() {
		return updateIndicator;
	}


	public void setUpdateIndicator(String updateIndicator) {
		this.updateIndicator = updateIndicator;
	}


	public BigDecimal getSrNo() {
		return srNo;
	}


	public void setSrNo(BigDecimal srNo) {
		this.srNo = srNo;
	}
    
   

	

}
