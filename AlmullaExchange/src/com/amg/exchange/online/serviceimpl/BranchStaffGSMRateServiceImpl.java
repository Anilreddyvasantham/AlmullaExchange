package com.amg.exchange.online.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.online.dao.IBranchStaffGSMRateDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.remittance.bean.PopulateData;
@Service("branchStaffGSMRateServiceImpl")
@Transactional
public class BranchStaffGSMRateServiceImpl implements IBranchStaffGSMRateService {

	@Autowired
	IBranchStaffGSMRateDao branchStaffGSMRateDao;

	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal branchId) {
		return branchStaffGSMRateDao.toFetchAllRecordsFromDb(branchId);
	}

	@Override
	public List<RatePlaceOrder> tofetchCustomerPlaceOrderRecords(BigDecimal customerId,
			BigDecimal branchId) {
		
		return branchStaffGSMRateDao.tofetchCustomerPlaceOrderRecords(customerId, branchId);
	}

	@Override
	public void toRejectRateForCustomer(BigDecimal rateOfferedPk,String userName) {
		branchStaffGSMRateDao.toRejectRateForCustomer(rateOfferedPk, userName);
	}

	@Override
	public String checkPlaceOrderStatusForAccept(BigDecimal rateOfferedPk) {
		return branchStaffGSMRateDao.checkPlaceOrderStatusForAccept(rateOfferedPk);
	}

	@Override
	public String checkPlaceOrderStatusForReject(BigDecimal rateOfferedPk) {
		return branchStaffGSMRateDao.checkPlaceOrderStatusForReject(rateOfferedPk);
	}

	@Override
	public void createPlaceOrderForNegotiate(BigDecimal rateOfferedPk,
			String userName, BigDecimal documentNumber) {
		branchStaffGSMRateDao.createPlaceOrderForNegotiate(rateOfferedPk, userName, documentNumber);
		
	}

	@Override
	public String saveOrUpdatePlaceOrderAddlData(
			HashMap<String, Object> inputValues) throws Exception {
		
		return branchStaffGSMRateDao.saveOrUpdatePlaceOrderAddlData(inputValues);
	}

	@Override
	public List<RatePlaceOrder> toFetchAllBranchStaffGsmBasedOnCustId(BigDecimal branchId,BigDecimal customerId) {
		return branchStaffGSMRateDao.toFetchAllBranchStaffGsmBasedOnCustId(branchId, customerId);
	}

	@Override
	public String checkPlaceOrderStatusForNegotiate(BigDecimal rateOfferedPk) {
		return branchStaffGSMRateDao.checkPlaceOrderStatusForNegotiate(rateOfferedPk);
	}

	@Override
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId, BigDecimal serviceGroupId,
			BigDecimal customerRef, BigDecimal beneBankId) {
		
		return branchStaffGSMRateDao.getBeneficiarNameList(customerId, beneCountryId, serviceGroupId, customerRef, beneBankId);
	}
}
