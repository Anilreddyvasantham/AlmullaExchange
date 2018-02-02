package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.bean.SpecifySellingRateDataTable;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;

public interface ISpecifySellingRateDao<T> {
	
	public List<CustomerSpecialDealRequest> getSpecialDealData(BigDecimal appcountryId);
	public void saveSpecialCustomer(CustomerSpecialDealRequest customerSpecial);
	public void updateApprove(List<SpecifySellingRateDataTable> listUpdate, String userName);
	public List<TreasuryDealDetail> getBuyRate( BigDecimal customerSpecialDealReqId,BigDecimal bankId,BigDecimal currencyId,String lineType);
	public List<TreasuryDealDetail> getSellRate(BigDecimal bankId,BigDecimal currencyId,String lineType);
}
