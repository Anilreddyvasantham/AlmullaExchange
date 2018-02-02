package com.amg.exchange.enquiry.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.IPlEnquiryDao;
import com.amg.exchange.enquiry.model.PlEnquiryView;
import com.amg.exchange.enquiry.service.IPlEnquiryService;
import com.amg.exchange.treasury.model.BankMaster;

@SuppressWarnings("serial")
@Service("plEnquiryServiceImpl")
public class PlEnquiryServiceImpl<T> implements IPlEnquiryService<T>, Serializable {
	
	@Autowired
	IPlEnquiryDao<T> plEnquiryDao;

	@Override
	@Transactional
	public List<PlEnquiryView> viewRecord(BigDecimal currencyId,BigDecimal bankId) {
		return plEnquiryDao.viewRecord(currencyId, bankId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBanksList(BigDecimal currencyId) {
	 
		return plEnquiryDao.getBanksList(currencyId);
	}

}
