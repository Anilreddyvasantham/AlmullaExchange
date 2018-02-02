package com.amg.exchange.enquiry.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.enquiry.model.PlEnquiryView;
import com.amg.exchange.treasury.model.BankMaster;

public interface IPlEnquiryDao<T> {
	
	public List<PlEnquiryView> viewRecord(BigDecimal currencyId, BigDecimal bankId);
	public List<BankMaster> getBanksList(BigDecimal currencyId);

}
