package com.amg.exchange.treasury.model;


	import java.io.Serializable;
	import java.math.BigDecimal;
	import java.util.Date;

	public class TreasuryDealHeaderDTO implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private BigDecimal treasuryDealHeaderId;
		private BigDecimal treasuryDocumentNumber;
		private BigDecimal companyId;
		private BigDecimal companyCode;
		private String createdBy;
		private Date createdDate;
		private String concludedBy;
		private String contactName;
		private String dealWithType;
		private BigDecimal documentID;
		private BigDecimal documentFinanceYear;
		private String isActive;
		private BigDecimal dealwithBankId;
		private BigDecimal dealWithCustomer;
		private  String documentDesc;
		
		private String reutersReference;
		private Date documentDate;
		private String approvedBy;
		private String attention;
		private Date valueDate;
		private String remarks;
		
		
		
		

		public BigDecimal getDocumentFinanceYear() {
			return documentFinanceYear;
		}

		public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
			this.documentFinanceYear = documentFinanceYear;
		}

		public String getIsActive() {
			return isActive;
		}

		public void setIsActive(String isActive) {
			this.isActive = isActive;
		}

		public String getDealWithType() {
			return dealWithType;
		}

		public BigDecimal getTreasuryDealHeaderId() {
			return treasuryDealHeaderId;
		}

		public void setTreasuryDealHeaderId(BigDecimal treasuryDealHeaderId) {
			this.treasuryDealHeaderId = treasuryDealHeaderId;
		}

		public void setDealWithType(String dealWithType) {
			this.dealWithType = dealWithType;
		}

		public BigDecimal getDocumentID() {
			return documentID;
		}

		public void setDocumentID(BigDecimal documentID) {
			this.documentID = documentID;
		}

		public BigDecimal getTreasuryDocumentNumber() {
			return treasuryDocumentNumber;
		}

		public void setTreasuryDocumentNumber(BigDecimal treasuryDocumentNumber) {
			this.treasuryDocumentNumber = treasuryDocumentNumber;
		}

		public BigDecimal getCompanyId() {
			return companyId;
		}

		public void setCompanyId(BigDecimal companyId) {
			this.companyId = companyId;
		}

		public BigDecimal getCompanyCode() {
			return companyCode;
		}

		public void setCompanyCode(BigDecimal companyCode) {
			this.companyCode = companyCode;
		}

		public String getCreatedBy() {
			return createdBy;
		}

		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}

		public Date getCreatedDate() {
			return createdDate;
		}

		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}

		public String getConcludedBy() {
			return concludedBy;
		}

		public void setConcludedBy(String concludedBy) {
			this.concludedBy = concludedBy;
		}

		public String getContactName() {
			return contactName;
		}

		public void setContactName(String contactName) {
			this.contactName = contactName;
		}

		public BigDecimal getDealwithBankId() {
			return dealwithBankId;
		}

		public void setDealwithBankId(BigDecimal dealwithBankId) {
			this.dealwithBankId = dealwithBankId;
		}

		public BigDecimal getDealWithCustomer() {
			return dealWithCustomer;
		}

		public void setDealWithCustomer(BigDecimal dealWithCustomer) {
			this.dealWithCustomer = dealWithCustomer;
		}

		public String getReutersReference() {
			return reutersReference;
		}

		public void setReutersReference(String reutersReference) {
			this.reutersReference = reutersReference;
		}

		public String getDocumentDesc() {
			return documentDesc;
		}

		public void setDocumentDesc(String documentDesc) {
			this.documentDesc = documentDesc;
		}

		public Date getDocumentDate() {
			return documentDate;
		}

		public void setDocumentDate(Date documentDate) {
			this.documentDate = documentDate;
		}

		public String getApprovedBy() {
			return approvedBy;
		}

		public void setApprovedBy(String approvedBy) {
			this.approvedBy = approvedBy;
		}

		public Date getValueDate() {
			return valueDate;
		}

		public void setValueDate(Date valueDate) {
			this.valueDate = valueDate;
		}

		public String getAttention() {
			return attention;
		}

		public void setAttention(String attention) {
			this.attention = attention;
		}

		public String getRemarks() {
			return remarks;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		
		
		
		
		
}
