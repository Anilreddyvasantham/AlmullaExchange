package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.dao.ICustomerImageVerificationDao;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.service.ICustomerImageVerification;

@Service("cstomerImageVerification")
@Transactional
public class CustomerImageVerificationServiceImpl implements ICustomerImageVerification {
	
	
	@Autowired
	ICustomerImageVerificationDao customerImageVerificationDao;

	@Override
	public List<CustomerImageVerification> getList() {
		return customerImageVerificationDao.getList();
	}

	@Override
	public String approveRecord(List<BigDecimal> lstApproved, String userName) {
		return customerImageVerificationDao.approveRecord(lstApproved,userName);
	}

	@Override
	public void rejectCustomerIdentityTypeStatus(BigDecimal customerId,String userName) {
		customerImageVerificationDao.rejectCustomerIdentityTypeStatus(customerId, userName);
		
	}

	@Override
	public BigDecimal toFetchIdentityPk(BigDecimal customerId, BigDecimal idType,Date idExpDate) {
		return customerImageVerificationDao.toFetchIdentityPk(customerId, idType,idExpDate);
	}

	@Override
	public void rejectCustomerImageVeryfiedDate(BigDecimal customerImageVerificationId, String userName) {
		customerImageVerificationDao.rejectCustomerImageVeryfiedDate(customerImageVerificationId, userName);
	}

	@Override
	public void upDateRecord(BigDecimal imageVerificationId, String userName,
			String comStatus) {

		customerImageVerificationDao.upDateRecord(imageVerificationId, userName, comStatus);
	}

	@Override
	public List<CustomerImageVerification> getAllVerifiedList(Date creationDate) {
		return customerImageVerificationDao.getAllVerifiedList(creationDate);
	}

	@Override
	public List<CustomerImageVerification> getListApproval(
			BigDecimal imageVerificationId) {
		return customerImageVerificationDao.getListApproval(imageVerificationId);
	}

	@Override
	public List<CustomerImageVerification> getList(Date creationDate) {
		return customerImageVerificationDao.getList(creationDate);
	}
	
	
	//Anil(01/10/2018)
	
	@Override
	public List<CustomerImageVerification> getCustomerDetails(Date creationDate,BigDecimal cusId,String idNum){
		return customerImageVerificationDao.getCustomerDetails(creationDate, cusId,idNum);
	}
	
	@Override
	public List<CustomerImageVerification> getCustomerDetailsLog(Date creationDate,BigDecimal cusId,String idNum) {
		return customerImageVerificationDao.getCustomerDetailsLog(creationDate, cusId,idNum);
	}
	
}
