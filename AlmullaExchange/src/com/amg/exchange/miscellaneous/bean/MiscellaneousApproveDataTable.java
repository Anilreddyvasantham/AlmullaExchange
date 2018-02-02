package com.amg.exchange.miscellaneous.bean;

import java.math.BigDecimal;
import java.util.Date;

public class MiscellaneousApproveDataTable {

		private String collectionMode;
		private String bankName;
		private BigDecimal chequeNo;
		private Date chequeDate;
		private BigDecimal approvalNo;
		private BigDecimal refundAmount;
		private BigDecimal paymentModeId;
		private String paymentMode;
		
		
		
		public BigDecimal getPaymentModeId() {
			return paymentModeId;
		}
		public String getPaymentMode() {
			return paymentMode;
		}
		public void setPaymentModeId(BigDecimal paymentModeId) {
			this.paymentModeId = paymentModeId;
		}
		public void setPaymentMode(String paymentMode) {
			this.paymentMode = paymentMode;
		}
		public String getCollectionMode() {
			return collectionMode;
		}
		public String getBankName() {
			return bankName;
		}
		public BigDecimal getChequeNo() {
			return chequeNo;
		}
		public BigDecimal getApprovalNo() {
			return approvalNo;
		}
		public BigDecimal getRefundAmount() {
			return refundAmount;
		}
		public void setCollectionMode(String collectionMode) {
			this.collectionMode = collectionMode;
		}
		public void setBankName(String bankName) {
			this.bankName = bankName;
		}
		public void setChequeNo(BigDecimal chequeNo) {
			this.chequeNo = chequeNo;
		}
		public void setApprovalNo(BigDecimal approvalNo) {
			this.approvalNo = approvalNo;
		}
		public void setRefundAmount(BigDecimal refundAmount) {
			this.refundAmount = refundAmount;
		}
		public Date getChequeDate() {
			return chequeDate;
		}
		public void setChequeDate(Date chequeDate) {
			this.chequeDate = chequeDate;
		}
	
		
}
