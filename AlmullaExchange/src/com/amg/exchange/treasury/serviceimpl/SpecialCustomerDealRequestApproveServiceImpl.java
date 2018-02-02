package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.ISpecialCustomerDealRequestApprovDao;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestApprovService;



@SuppressWarnings("serial")
@Service("ispecialCustomerDealRequestBeanDataServiceImpl")
public class SpecialCustomerDealRequestApproveServiceImpl<T> implements
		ISpecialCustomerDealRequestApprovService<T>, Serializable {

	@Autowired
	ISpecialCustomerDealRequestApprovDao<T> iSpecialCustomerDealRequestApprovDao;

	@Transactional
	@Override
	public List<CustomerSpecialDealRequest> CustomerSpecialDealRequest(
			BigDecimal countryId) {
		return iSpecialCustomerDealRequestApprovDao
				.CustomerSpecialDealRequest(countryId);
	}

	@Transactional
	@Override
	public void updateApprove(List<BigDecimal> lstApproved, String userName) {
		iSpecialCustomerDealRequestApprovDao.updateApprove(lstApproved,
				userName);
	}

	@Transactional
	@Override
	public String getCountryName(BigDecimal countryId) {

		return iSpecialCustomerDealRequestApprovDao.getCountryName(countryId);
	}

	@Transactional
	@Override
	public List<CustomerSpecialDealRequest> getCustName(Customer custId) {
		// TODO Auto-generated method stub
		return iSpecialCustomerDealRequestApprovDao.getCustName(custId);
	}

	@Transactional
	@Override
	public String countryName(BigDecimal countryId, BigDecimal languageId) {
		return iSpecialCustomerDealRequestApprovDao.countryName(countryId, languageId);
	}
}
