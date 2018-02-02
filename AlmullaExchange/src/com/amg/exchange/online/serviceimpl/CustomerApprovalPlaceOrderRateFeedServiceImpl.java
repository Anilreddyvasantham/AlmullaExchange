package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.dao.ICustomerApprovalPlaceOrderRateFeedDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.ICustomerApprovalPlaceOrderRateFeedService;
import com.amg.exchange.remittance.bean.PopulateData;

@Service("customerApprovalPlaceOrderRateFeedServiceImpl")
@Transactional
public class CustomerApprovalPlaceOrderRateFeedServiceImpl implements ICustomerApprovalPlaceOrderRateFeedService {

	@Autowired 
	ICustomerApprovalPlaceOrderRateFeedDao customerApprovalPlaceOrderRateFeedDao;


	@Override
	public String toFetchAccountNumber(BigDecimal beneficiaryAccountSeqId) {
		return customerApprovalPlaceOrderRateFeedDao.toFetchAccountNumber(beneficiaryAccountSeqId);
	}

	@Override
	public void toDeActivateRecord(BigDecimal rateOfferedPk,  String userName) {
		customerApprovalPlaceOrderRateFeedDao.toDeActivateRecord(rateOfferedPk, userName);
	}

	@Override
	public void toActivateRecord(BigDecimal rateOfferedPk,BigDecimal beneficiaryMasterId,BigDecimal accountSeqId,String accountNumber,String userName) {
		customerApprovalPlaceOrderRateFeedDao.toActivateRecord(rateOfferedPk,beneficiaryMasterId,accountSeqId,accountNumber, userName);
	}

	@Override
	public String toFetchEmailForBranchMgr(BigDecimal countryBranchId) {
		return customerApprovalPlaceOrderRateFeedDao.toFetchEmailForBranchMgr(countryBranchId);
	}

	@Override
	public void toActivateRecordForBranchSupport(BigDecimal rateOfferedPk,BigDecimal countryBranchId,String userName) {
		customerApprovalPlaceOrderRateFeedDao.toActivateRecordForBranchSupport(rateOfferedPk,countryBranchId, userName);
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsforUnApprovedFromCustomer(BigDecimal customerId,BigDecimal branchId) {
		return customerApprovalPlaceOrderRateFeedDao.fetchAllRecrdsforUnApprovedFromCustomer(customerId,branchId);
	}

	@Override
	public List<RatePlaceOrder> toFetchRecordsBasedOnCustomerId(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear) {
		return customerApprovalPlaceOrderRateFeedDao.toFetchRecordsBasedOnCustomerId(customerId, documentNumber, documentFinanceYear);
	}

	@Override
	public List<RatePlaceOrder> toFetchRecordsUnApprovesgsm(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear) {
		return customerApprovalPlaceOrderRateFeedDao.toFetchRecordsUnApprovesgsm(customerId, documentNumber, documentFinanceYear);
	}

	@Override
	public BigDecimal toFetchBeneMasterId(String beneficiaryName,BigDecimal countryId) {
		return customerApprovalPlaceOrderRateFeedDao.toFetchBeneMasterId(beneficiaryName, countryId);
	}

	@Override
	public BigDecimal getBeneficiarList(String beneficiaryName,BigDecimal countryId, BigDecimal remitType, BigDecimal customerRef,BigDecimal customerId) {
		return customerApprovalPlaceOrderRateFeedDao.getBeneficiarList(beneficiaryName, countryId, remitType, customerRef,customerId);
	}

	

	
}
