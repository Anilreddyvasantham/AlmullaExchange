package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("miscReceiptOrPaymentRequest")
@Scope("session")
public class MiscReceiptOrPaymentRquest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal reeciptOrPayment;
	private BigDecimal documentCode;
	private BigDecimal documentNo;
	private Date documentDate;
	private BigDecimal remittanceYear;
	private BigDecimal remittanceNo;
	private Date transcationDate;
	private BigDecimal telephoneNumber;
	private String applicant;
	private BigDecimal commission;
	private  BigDecimal charges;
	private BigDecimal deleveryCharges;
	private BigDecimal rateAdjust;
	private BigDecimal otherAdjust;
	private BigDecimal netAmount;
	private BigDecimal reasonCode;
	private String remarks;
	private String signature;
	
	
	private SessionStateManage session=new SessionStateManage();
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	
	
	
	
	
	public BigDecimal getReeciptOrPayment() {
		return reeciptOrPayment;
	}
	public void setReeciptOrPayment(BigDecimal reeciptOrPayment) {
		this.reeciptOrPayment = reeciptOrPayment;
	}
	public BigDecimal getDocumentCode() {
		return documentCode;
	}
	public void setDocumentCode(BigDecimal documentCode) {
		this.documentCode = documentCode;
	}
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	public BigDecimal getRemittanceYear() {
		return remittanceYear;
	}
	public void setRemittanceYear(BigDecimal remittanceYear) {
		this.remittanceYear = remittanceYear;
	}
	public BigDecimal getRemittanceNo() {
		return remittanceNo;
	}
	public void setRemittanceNo(BigDecimal remittanceNo) {
		this.remittanceNo = remittanceNo;
	}
	public Date getTranscationDate() {
		return transcationDate;
	}
	public void setTranscationDate(Date transcationDate) {
		this.transcationDate = transcationDate;
	}
	public BigDecimal getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(BigDecimal telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public BigDecimal getCommission() {
		return commission;
	}
	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}
	public BigDecimal getCharges() {
		return charges;
	}
	public void setCharges(BigDecimal charges) {
		this.charges = charges;
	}
	public BigDecimal getDeleveryCharges() {
		return deleveryCharges;
	}
	public void setDeleveryCharges(BigDecimal deleveryCharges) {
		this.deleveryCharges = deleveryCharges;
	}
	public BigDecimal getRateAdjust() {
		return rateAdjust;
	}
	public void setRateAdjust(BigDecimal rateAdjust) {
		this.rateAdjust = rateAdjust;
	}
	public BigDecimal getOtherAdjust() {
		return otherAdjust;
	}
	public void setOtherAdjust(BigDecimal otherAdjust) {
		this.otherAdjust = otherAdjust;
	}
	public BigDecimal getNetAmount() {
		return netAmount;
	}
	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}
	public BigDecimal getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(BigDecimal reasonCode) {
		this.reasonCode = reasonCode;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	
	
	
	
	
	public void misReceiprOrPaymentRequestPageNavigation(){
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/MiscReceiptOrPaymentRequest.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	
	}
	
	public void clearAllFields(){
		setReeciptOrPayment(null);
		setDocumentCode(null);
		setDocumentNo(null);
		setDocumentDate(null);
		setRemittanceYear(null);
		setRemittanceNo(null);
		setTranscationDate(null);
		setTelephoneNumber(null);
		setApplicant(null);
		setCommission(null);
		setCharges(null);
		setDeleveryCharges(null);
		setRateAdjust(null);
		setOtherAdjust(null);
		setNetAmount(null);
		setReasonCode(null);
		setRemarks(null);
		setSignature(null);
	}
	
	public void exit() throws IOException {
		/*if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}*/
		String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(session.getRoleId()));
		String roleName = roleNameDesc.trim();
		if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCHSTAFF)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER) || roleName.equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}else if (roleName.equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/corporatehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}	

}
