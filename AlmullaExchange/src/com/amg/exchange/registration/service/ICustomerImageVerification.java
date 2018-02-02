package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.registration.model.CustomerImageVerification;

public interface  ICustomerImageVerification {

	List<CustomerImageVerification> getList();

	String approveRecord(List<BigDecimal> lstApproved, String userName);

	public void rejectCustomerIdentityTypeStatus(BigDecimal customerId,String userName);

	public BigDecimal toFetchIdentityPk(BigDecimal customerId, BigDecimal idType,Date idExpDate);

	public void rejectCustomerImageVeryfiedDate(BigDecimal customerImageVerificationId, String userName);
	
	public void upDateRecord(BigDecimal imageVerificationId, String userName,String comStatus);
	
	List<CustomerImageVerification> getAllVerifiedList(Date creationDate);
	
	List<CustomerImageVerification> getListApproval(BigDecimal imageVerificationId);
	
	List<CustomerImageVerification> getList(Date creationDate);
	
	//Anil(01/10/2018)
	public List<CustomerImageVerification> getCustomerDetails(Date creationDate,BigDecimal cusId,String idNum);
	public List<CustomerImageVerification> getCustomerDetailsLog(Date creationDate,BigDecimal cusId,String idNum);
	
}
