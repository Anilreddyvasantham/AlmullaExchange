package com.amg.exchange.remittance.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.ISpecialRateRequestDao;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.service.ISpecialRateRequestService;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;

@Service
public class SpecialRateRequestServiceImpl<T> implements ISpecialRateRequestService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	ISpecialRateRequestDao<T> specialRateDao;

	@Override
	@Transactional
	public List<SpecialRateRequest> getSpecialRateRequestList() {

		return specialRateDao.getSpecialRateRequestList();
	}

	@Override
	@Transactional
	public void updateRecord(SpecialRateRequest specialRateRequest) {
		specialRateDao.updateRecords(specialRateRequest);
		;
	}

	@Override
	@Transactional
	public Boolean isSpotRate(BigDecimal documentNo) {

		return specialRateDao.isSpotRate(documentNo);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> toFetchAllDetialsFromSpecialRateReq(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId) {
		return specialRateDao.toFetchAllDetialsFromSpecialRateReq(customerId, fcAmount, createdDate, beneficiaryId, beneficiaryBankId);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> toFetchSpotRate(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId) {
		return specialRateDao.toFetchSpotRate(customerId, fcAmount, createdDate, beneficiaryId, beneficiaryBankId);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> getActiveSpecialRateRequest(
			BigDecimal customerId, BigDecimal fcAmount, String createdDate,
			BigDecimal beneficiaryId, BigDecimal beneficiaryBankId) {
		
		return specialRateDao.getActiveSpecialRateRequest(customerId, fcAmount, createdDate, beneficiaryId, beneficiaryBankId);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> getMinMaxRate(BigDecimal currencyId) {
		
		return specialRateDao.getMinMaxRate(currencyId);
	}

	@Override
	@Transactional
	public void updateSpecialRateRequest(BigDecimal SpecialRatePk,
			BigDecimal fcAmount) {
		specialRateDao.updateSpecialRateRequest(SpecialRatePk,fcAmount);
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> fetchSpotRateRecords(BigDecimal customerId,
			String createdDate, BigDecimal beneficiaryId,
			BigDecimal beneficiaryBankId) {
		return specialRateDao.fetchSpotRateRecords(customerId, createdDate, beneficiaryId, beneficiaryBankId);
 
	}

	@Override
	@Transactional
	public List<SpecialRateRequest> fetchAllDetailsFromCustomerAndBranch(BigDecimal customerReferenceNo, BigDecimal branchId) {
		return specialRateDao.fetchAllDetailsFromCustomerAndBranch(customerReferenceNo, branchId);
	}

}
