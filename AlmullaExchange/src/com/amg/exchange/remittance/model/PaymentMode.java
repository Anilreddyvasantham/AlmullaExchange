package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "EX_PAYMENT_MODE")
public class PaymentMode implements Serializable {
	private static final long serialVersionUID = 2315791709068216697L;
	private BigDecimal paymentModeId;
	private String paymentCode;
	/*private String paymentDescEnglish;
	private String paymentDescArabic;*/
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvBy;
	private Date approvDate;
	private String remarks;

	private List<PaymentModeDesc> serviceApplicabilityList = new ArrayList<PaymentModeDesc>();
	private Set<Remittance> exRemittance = new HashSet<Remittance>(0);

	public PaymentMode() {

	}
	
	public PaymentMode(BigDecimal paymentModeId, String paymentCode,
			
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String isActive,
			List<PaymentModeDesc> serviceApplicabilityList,
			Set<Remittance> exRemittance,String remarks) {
		super();
		this.paymentModeId = paymentModeId;
		this.paymentCode = paymentCode;
		/*this.paymentDescEnglish = paymentDescEnglish;
		this.paymentDescArabic = paymentDescArabic;*/
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.isActive = isActive;
		this.serviceApplicabilityList = serviceApplicabilityList;
		this.exRemittance = exRemittance;
		this.remarks=remarks;
	}

	@Id
	@GeneratedValue(generator = "ex_payment_mode_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_payment_mode_seq", sequenceName = "EX_PAYMENT_MODE_SEQ", allocationSize = 1)
	@Column(name = "PAYMENT_MODE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getPaymentModeId() {
		return paymentModeId;

	}

	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}

	@Column(name = "PAYMENT_CODE", unique = true)
	public String getPaymentCode() {
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	/*@Column(name = "PAYMENT_DESC_ENGLISH")
	public String getPaymentDescEnglish() {
		return paymentDescEnglish;
	}

	public void setPaymentDescEnglish(String paymentDescEnglish) {
		this.paymentDescEnglish = paymentDescEnglish;
	}
	
	@Column(name = "PAYMENT_DESC_ARABIC")
	public String getPaymentDescArabic() {
		return paymentDescArabic;
	}



	public void setPaymentDescArabic(String paymentDescArabic) {
		this.paymentDescArabic = paymentDescArabic;
	}
*/
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "paymentMode")
	public List<PaymentModeDesc> getServiceApplicabilityList() {
		return serviceApplicabilityList;
	}

	public void setServiceApplicabilityList(
			List<PaymentModeDesc> serviceApplicabilityList) {
		this.serviceApplicabilityList = serviceApplicabilityList;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "exPaymentMode")
	public Set<Remittance> getExRemittance() {
		return exRemittance;
	}

	public void setExRemittance(Set<Remittance> exRemittance) {
		this.exRemittance = exRemittance;
	}
	@Column(name = "APPROVED_BY")
	public String getApprovBy() {
		return approvBy;
	}

	public void setApprovBy(String approvBy) {
		this.approvBy = approvBy;
	}
	@Column(name = "APPROVED_DATE")
	public Date getApprovDate() {
		return approvDate;
	}

	public void setApprovDate(Date approvDate) {
		this.approvDate = approvDate;
	}

	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	

}
