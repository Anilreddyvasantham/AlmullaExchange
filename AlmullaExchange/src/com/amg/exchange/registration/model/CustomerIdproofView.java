package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * Author Rahamathali Shaik
*/
@Entity
@Table(name= "VW_EX_CUSTOMER_IDPROOF_INFO")
public class CustomerIdproofView implements Serializable{

	
	private BigDecimal customerId;
	private BigDecimal idproofId;
	private BigDecimal idproofCustomerTypeId;
	private String idProofProofName;
	private BigDecimal idProofIdentityFor;
	private String idProofTypeDesc;
	private BigDecimal idProofTypeId;
	private String idProofInt;
	//private BigDecimal idProofImageId;
	private Date idProofExpiredDate;
	private Date idProofEffectiveDate;
	private Date idProofEndDate;
	private String idProofApproveBy;
	private Date idProofApproveDate;
	private String idProofStatus;
	//private BigDecimal idProofCustomerReferenceId;
	private String idProofFor;
	
	
	
	@Column(name = "CUSTOMERID")
	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	@Id
	@Column(name = "IDPROOF_ID")
	public BigDecimal getIdproofId() {
		return idproofId;
	}
	public void setIdproofId(BigDecimal idproofId) {
		this.idproofId = idproofId;
	}
	@Column(name = "IDPROOF_CUSTOMERTYPEID")
	public BigDecimal getIdproofCustomerTypeId() {
		return idproofCustomerTypeId;
	}
	public void setIdproofCustomerTypeId(BigDecimal idproofCustomerTypeId) {
		this.idproofCustomerTypeId = idproofCustomerTypeId;
	}
	@Column(name = "IDPROOF_PROOFNAME")
	public String getIdProofProofName() {
		return idProofProofName;
	}
	public void setIdProofProofName(String idProofProofName) {
		
		this.idProofProofName = idProofProofName;
	}
	@Column(name = "IDPROOF_IDENTITYFOR")
	public BigDecimal getIdProofIdentityFor() {
		return idProofIdentityFor;
	}
	public void setIdProofIdentityFor(BigDecimal idProofIdentityFor) {
		this.idProofIdentityFor = idProofIdentityFor;
	}
	@Column(name = "IDPROOF_TYPE_DESC")
	public String getIdProofTypeDesc() {
		return idProofTypeDesc;
	}
	public void setIdProofTypeDesc(String idProofTypeDesc) {
		this.idProofTypeDesc = idProofTypeDesc;
	}
	@Column(name = "IDPROOF_TYPEID")
	public BigDecimal getIdProofTypeId() {
		return idProofTypeId;
	}
	public void setIdProofTypeId(BigDecimal idProofTypeId) {
		this.idProofTypeId = idProofTypeId;
	}
	@Column(name = "IDPROOF_INT")
	public String getIdProofInt() {
		return idProofInt;
	}
	public void setIdProofInt(String idProofInt) {
		this.idProofInt = idProofInt;
	}
	/*@Column(name = "IDPROOF_IMAGEID")
	public BigDecimal getIdProofImageId() {
		return idProofImageId;
	}
	public void setIdProofImageId(BigDecimal idProofImageId) {
		this.idProofImageId = idProofImageId;
	}*/
	@Column(name = "IDPROOF_EXPIREDATE")
	public Date getIdProofExpiredDate() {
		return idProofExpiredDate;
	}
	public void setIdProofExpiredDate(Date idProofExpiredDate) {
		this.idProofExpiredDate = idProofExpiredDate;
	}
	@Column(name = "IDEFFDATE")
	public Date getIdProofEffectiveDate() {
		return idProofEffectiveDate;
	}
	public void setIdProofEffectiveDate(Date idProofEffectiveDate) {
		this.idProofEffectiveDate = idProofEffectiveDate;
	}
	@Column(name = "IDENDDATE")
	public Date getIdProofEndDate() {
		return idProofEndDate;
	}
	public void setIdProofEndDate(Date idProofEndDate) {
		this.idProofEndDate = idProofEndDate;
	}
	@Column(name = "IDPROOF_APPROVEDBY")
	public String getIdProofApproveBy() {
		return idProofApproveBy;
	}
	public void setIdProofApproveBy(String idProofApproveBy) {
		this.idProofApproveBy = idProofApproveBy;
	}
	@Column(name = "IDPROOF_APPROVEDDATE")
	public Date getIdProofApproveDate() {
		return idProofApproveDate;
	}
	public void setIdProofApproveDate(Date idProofApproveDate) {
		this.idProofApproveDate = idProofApproveDate;
	}
	@Column(name = "IDPROOF_STATUS")
	public String getIdProofStatus() {
		return idProofStatus;
	}
	public void setIdProofStatus(String idProofStatus) {
		this.idProofStatus = idProofStatus;
	}
	/*@Column(name = "IDPROOF_REFCUSTID")
	public BigDecimal getIdProofCustomerReferenceId() {
		return idProofCustomerReferenceId;
	}
	public void setIdProofCustomerReferenceId(BigDecimal idProofCustomerReferenceId) {
		this.idProofCustomerReferenceId = idProofCustomerReferenceId;
	}*/
	@Column(name = "IDFOR")
	public String getIdProofFor() {
		return idProofFor;
	}
	public void setIdProofFor(String idProofFor) {
		this.idProofFor = idProofFor;
	}
	
	
	
	
	
}
