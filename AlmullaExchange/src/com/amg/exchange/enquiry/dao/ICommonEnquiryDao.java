package com.amg.exchange.enquiry.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;

public interface ICommonEnquiryDao {

	//for customer enquiry services
	public List<Customer> getallCustomer(BigDecimal customerId);
	public List<CustomerIdProof> getCustomerIdProofDetails(BigDecimal customerId);
	public List<ContactDetail> getContactDetails(BigDecimal customerId);
	public List<CustomerEmploymentInfo> getEmployeementDetails(BigDecimal customerId);
	
	public String getArticalName(BigDecimal articalId,BigDecimal languageId);
	public String getlevelName(BigDecimal levelId,BigDecimal languageId);
	public String getIncomeRangeName(BigDecimal incomeRangeId);
}
