package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.util.AMGException;

public interface IRemmiterOnlineRegDao<T>{
	
	public List<ContactDetail> getAllEmployees(String id);
	public void saveCustomer(Customer customer, String custId);
	public void saveCustomer(Customer customer);
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId);
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp);
	public void saveCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId);
	public void updateLoginCustomerId(String userName, BigDecimal customerId, BigDecimal companyId);
	public void saveCustomerEmploymentProofInfo(CustomerIdProof proof);
	public List<CustomerIdProof> getCustomerIdProof(String civilId);
	public List<CustomerLogin> getLoginInfoForCustomer(String userName, String password);
	public List<ContactDetail> getFsContactDetails(BigDecimal customerId);
	public void saveContactDetails(ContactDetail contactDetail);
	public List<Customer> getFsCustomer(BigDecimal cust_id);
	public List<CustomerEmploymentInfo> getFsCustEmpInfo(BigDecimal customerId);
	public List<CustomerIdProof> getFsCustIdProof(BigDecimal cust_id, BigDecimal identityType);
	public List<Amlstatus> getAMLStatus(String name);
	public void updateCustomer(Customer customer, String custId);
	public void updateCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId);
	public void updateCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId);
	public void saveImage(DocumentImg document);
	public void updateImage(DocumentImg document, BigDecimal custId);
	public List<CustomerLogin> getLoginInfoForCustomer(String userName);
	
	public List<CityMaster> getCity(BigDecimal state);
    public List<CountryMaster> getCountry();
	public List<StateMaster> getState();
	
	public List<DocumentImg> getImage(BigDecimal id);
	public List<Customer> CheckTokenAvailable(String token);
	public List<CustomerIdProof> getProofDetails(BigDecimal customerId);
	
	public List<CountryBranch> getCountryBranch(BigDecimal branchId);
	
	public List<String> getMigrationData(String idNumber , BigDecimal mobile ,String email) throws AMGException;
	public List<CustomerIdProof> getIdproofDetails(BigDecimal idType, String idNumber);
}


