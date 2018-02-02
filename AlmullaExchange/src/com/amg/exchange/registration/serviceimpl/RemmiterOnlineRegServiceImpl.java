package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.registration.dao.IRemmiterOnlineRegDao;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("remmiterOnlineReg")
public class RemmiterOnlineRegServiceImpl<T> implements IRemmiterOnlineRegService<T>, Serializable {
	
	@Autowired
	IRemmiterOnlineRegDao<T> iRemOnlineRegDao;

	public IRemmiterOnlineRegDao<T> getiRemOnlineRegDao() {
		return iRemOnlineRegDao;
	}

	public void setiRemOnlineRegDao(IRemmiterOnlineRegDao<T> iRemOnlineRegDao) {
		this.iRemOnlineRegDao = iRemOnlineRegDao;
	}

	@Override
	@Transactional
	public List<ContactDetail> getAllEmployees(String id) {
		return getiRemOnlineRegDao().getAllEmployees(id);
	}

	@Override
	@Transactional
	public void saveCustomer(Customer customer, String custId) {
		getiRemOnlineRegDao().saveCustomer(customer, custId);
	}
	
	@Override
	@Transactional
	public void saveCustomer(Customer customer) {
		getiRemOnlineRegDao().saveCustomer(customer);
	}

	@Override
	@Transactional
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId) {
		getiRemOnlineRegDao().saveCustomerEmploymentInfo(customerEmp, custId);
	}

	@Override
	@Transactional
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp) {
		getiRemOnlineRegDao().saveCustomerEmploymentInfo(customerEmp);
	}
	
	@Override
	@Transactional
	public void saveCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId) {
		getiRemOnlineRegDao().saveCustomerEmploymentProofInfo(customerEmployeeProof, custId);
	}
	
	@Override
	@Transactional
	public void saveCustomerEmploymentProofInfo(CustomerIdProof proof) {
		getiRemOnlineRegDao().saveCustomerEmploymentProofInfo(proof);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProof(String civilId) {
		return getiRemOnlineRegDao().getCustomerIdProof(civilId);
	}

	@Override
	@Transactional
	public List<CustomerLogin> getLoginInfoForCustomer(String userName,	String password) {
		return getiRemOnlineRegDao().getLoginInfoForCustomer(userName, password);
	}

	@Override
	@Transactional
	public List<ContactDetail> getFsContactDetails(BigDecimal customerId) {
		return getiRemOnlineRegDao().getFsContactDetails(customerId);
	}

	@Override
	@Transactional
	public List<Customer> getFsCustomer(BigDecimal cust_id) {
		return getiRemOnlineRegDao().getFsCustomer(cust_id);
	}

	@Override
	@Transactional
	public List<CustomerEmploymentInfo> getFsCustEmpInfo(BigDecimal customerId) {
		return getiRemOnlineRegDao().getFsCustEmpInfo(customerId);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getFsCustIdProof(BigDecimal cust_id, BigDecimal identityType) {
		return getiRemOnlineRegDao().getFsCustIdProof(cust_id, identityType);
	}

	@Override
	@Transactional
	public List<Amlstatus> getAMLStatus(String name) {
		return getiRemOnlineRegDao().getAMLStatus(name);
	}

	@Transactional
	@Override
	public void updateCustomer(Customer customer, String custId) {
		getiRemOnlineRegDao().updateCustomer(customer, custId);
		
	}

	@Transactional
	@Override
	public void updateCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId) {
		getiRemOnlineRegDao().updateCustomerEmploymentInfo(customerEmp, custId);
	}
	
	@Transactional
	@Override
	public void updateCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId) {
		getiRemOnlineRegDao().updateCustomerEmploymentProofInfo(customerEmployeeProof, custId);
	}
	
	@Transactional
	@Override
	public void saveImage(DocumentImg document) {
		getiRemOnlineRegDao().saveImage(document);
	}
	
	@Transactional
	@Override
	public void updateImage(DocumentImg document, BigDecimal custId) {
		getiRemOnlineRegDao().updateImage(document, custId);
	}
	
	@Transactional
	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(String userName) {
		return getiRemOnlineRegDao().getLoginInfoForCustomer(userName);
	}
	
	@Transactional
	@Override
	public List<DocumentImg> getImage(BigDecimal id) {
		return getiRemOnlineRegDao().getImage(id);
	}
	
	@Transactional
	@Override
	public List<Customer> CheckTokenAvailable(String token) {
		return getiRemOnlineRegDao().CheckTokenAvailable(token);
	}
	
	@Transactional
	@Override
	public void updateLoginCustomerId(String userName, BigDecimal customerId, BigDecimal companyId) {
		getiRemOnlineRegDao().updateLoginCustomerId(userName, customerId, companyId);
		
	}
	
	@Transactional
	@Override
	public void saveContactDetails(ContactDetail contactDetail) {
		getiRemOnlineRegDao().saveContactDetails(contactDetail);
		
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getProofDetails(BigDecimal customerId) {
	
		return getiRemOnlineRegDao().getProofDetails(customerId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranch(BigDecimal branchId) {
		return getiRemOnlineRegDao().getCountryBranch(branchId);
	}

	@Override
	@Transactional
	public List<String> getMigrationData(String idNumber, BigDecimal mobile,
			String email) throws AMGException {
		return getiRemOnlineRegDao().getMigrationData(idNumber, mobile, email);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getIdproofDetails(BigDecimal idType,
			String idNumber) {
		return getiRemOnlineRegDao().getIdproofDetails(idType, idNumber);
	}
}
