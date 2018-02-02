package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.bean.SpecifySellingRateDataTable;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;

public interface ISpecifySellingRateService<T> {
	
	public List<CustomerSpecialDealRequest> getSpecialDealData(BigDecimal appcountryId);
	public void saveSpecialCustomer(CustomerSpecialDealRequest customerSpecial);
	public void updateApprove(List<SpecifySellingRateDataTable> listUpdate, String userName);
	//nagarjuna made 15/12/2014
	public List<TreasuryDealDetail> getBuyRate( BigDecimal customerSpecialDealReqId,BigDecimal bankId, BigDecimal currencyId,String lineType);
	public List<TreasuryDealDetail> getSellRate(BigDecimal bankId, BigDecimal currencyId,String lineType);

}
