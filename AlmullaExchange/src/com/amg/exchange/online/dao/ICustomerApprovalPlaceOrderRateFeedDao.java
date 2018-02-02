package com.amg.exchange.online.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.bean.PopulateData;

public interface ICustomerApprovalPlaceOrderRateFeedDao {

	public List<RatePlaceOrder> toFetchRecordsBasedOnCustomerId(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear);
	
	public String toFetchAccountNumber(BigDecimal beneficiaryAccountSeqId);
	
	public void toDeActivateRecord(BigDecimal rateOfferedPk, String userName);
	
	public void toActivateRecord(BigDecimal rateOfferedPk,BigDecimal beneficiaryMasterId,BigDecimal accountSeqId,String accountNumber, String userName);
	
	public String toFetchEmailForBranchMgr(BigDecimal countryBranchId);
	
	public void toActivateRecordForBranchSupport(BigDecimal rateOfferedPk,BigDecimal countryBranchId,String userName);
	
	public List<RatePlaceOrder> fetchAllRecrdsforUnApprovedFromCustomer(BigDecimal customerId,BigDecimal branchId);
	
	public List<RatePlaceOrder> toFetchRecordsUnApprovesgsm(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear);
	
	public BigDecimal toFetchBeneMasterId(String beneficiaryName,BigDecimal countryId);
	
	public BigDecimal getBeneficiarList(String beneficiaryName,BigDecimal countryId, BigDecimal remitType, BigDecimal customerRef,BigDecimal customerId);
	
	
	
	}
