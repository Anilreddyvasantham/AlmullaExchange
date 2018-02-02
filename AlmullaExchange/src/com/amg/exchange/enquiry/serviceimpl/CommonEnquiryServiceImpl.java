package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.ICommonEnquiryDao;
import com.amg.exchange.enquiry.service.ICommonEnquiryService;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;

@Service
public class CommonEnquiryServiceImpl implements ICommonEnquiryService{

	@Autowired
	ICommonEnquiryDao commonEnquiryDao;
	
	@Override
	@Transactional
	public List<Customer> getallCustomer(BigDecimal customerId) {
		return commonEnquiryDao.getallCustomer(customerId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProofDetails(BigDecimal customerId){
		return commonEnquiryDao.getCustomerIdProofDetails(customerId);
	}
	
	@Override
	@Transactional
	public List<ContactDetail> getContactDetails(BigDecimal customerId){
		return commonEnquiryDao.getContactDetails(customerId);
	}
	
	@Override
	@Transactional
	public List<CustomerEmploymentInfo> getEmployeementDetails(BigDecimal customerId){
		return commonEnquiryDao.getEmployeementDetails(customerId);
	}
	
	@Override
	@Transactional
	public String getArticalName(BigDecimal articalId,BigDecimal languageId){
		return commonEnquiryDao.getArticalName(articalId,languageId);
	}
	
	@Override
	@Transactional
	public String getlevelName(BigDecimal levelId,BigDecimal languageId){
		return commonEnquiryDao.getlevelName(levelId,languageId);
	}
	
	@Override
	@Transactional
	public String getIncomeRangeName(BigDecimal incomeRangeId){
		return commonEnquiryDao.getIncomeRangeName(incomeRangeId);
	}
	
}
