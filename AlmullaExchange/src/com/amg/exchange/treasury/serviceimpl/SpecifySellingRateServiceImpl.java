package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.SpecifySellingRateDataTable;
import com.amg.exchange.treasury.dao.ISpecifySellingRateDao;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.service.ISpecifySellingRateService;
@Service("specifySellingRateService")
public class SpecifySellingRateServiceImpl<T> implements ISpecifySellingRateService<T> {
	
	@Autowired
	ISpecifySellingRateDao<T> specifySellingRateDao;

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getSpecialDealData(BigDecimal appcountryId) {
		return specifySellingRateDao.getSpecialDealData(appcountryId);
	}

	@Override
	@Transactional
	public void saveSpecialCustomer(CustomerSpecialDealRequest customerSpecial) {
		specifySellingRateDao.saveSpecialCustomer(customerSpecial);
		
	}
	@Override
	@Transactional
	public void updateApprove(List<SpecifySellingRateDataTable> listUpdate, String userName){
		specifySellingRateDao.updateApprove(listUpdate, userName);
		
	}
	
	@Override
	@Transactional
	public List<TreasuryDealDetail> getBuyRate(BigDecimal customerSpecialDealReqId,BigDecimal bankId,BigDecimal currencyId,String lineType){
		
		return specifySellingRateDao.getBuyRate( customerSpecialDealReqId,bankId,currencyId,lineType);
	}
	
	@Override
	@Transactional
	public List<TreasuryDealDetail> getSellRate(BigDecimal bankId,BigDecimal currencyId,String lineType){
	
		return specifySellingRateDao.getSellRate(bankId,currencyId,lineType);
	}

}
