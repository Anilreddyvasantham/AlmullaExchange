package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.IBankBranchEnquiryDao;
import com.amg.exchange.enquiry.service.IBankBranchEnquiryService;
import com.amg.exchange.treasury.model.BankBranch;

@Service
public class BankBranchEnquiryServiceImpl implements IBankBranchEnquiryService{
	
	@Autowired
	IBankBranchEnquiryDao bankBranchEnquiryDao;

	@Override
	@Transactional
	public List<BankBranch> getBranchDetails(BigDecimal bankCountry, BigDecimal bankId) {
		return bankBranchEnquiryDao.getBranchDetails(bankCountry, bankId);
	}
	
	

}
