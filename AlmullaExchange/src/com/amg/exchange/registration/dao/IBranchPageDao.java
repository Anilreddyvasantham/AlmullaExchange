package com.amg.exchange.registration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.BranchSystemInventory;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.EmployeeSystemsAssigned;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

public interface IBranchPageDao<T> {

	 public void saveApprove(BigDecimal customerId);
	 public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmploymentInfo,BigDecimal customerId);
	 public void removeIdDetails(BigDecimal customerId);
	 public void saveCustomerEmploymentProofInfo(CustomerIdProof customerIdProof,BigDecimal customerId);
	 public void saveCustomer(Customer customer,BigDecimal customerId);
	 public void saveAdditionalInfo(Customer customer,BigDecimal customerId);
	 public void saveCustomerIndividual(Customer customer,BigDecimal customerId);
	 public void addContactDetails(ContactDetail contactDetail,BigDecimal customerId);
	 public List<ArticleDetails> getLevelData(BigDecimal Id,BigDecimal languageid);
	 public List<ArticleMaster> getArtilces(BigDecimal countryId);
	 public void deleteContactDetails(BigDecimal customerId);
	 
	 public List<Amlstatus> getAmlStatus(String name);
	 public List<CustomerIdProof> popUploadedData( String customerId);
	 public List<CountryMasterDesc> getCountryList(BigDecimal languageId);
	 public List<CustomerIdProof> getCustomerIdProof(String idNumber);

	 public List<Customer> getCustomerInfo(BigDecimal customerId);
	 public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId);
	 public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(BigDecimal customerId);
	 public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId);
	 public List<CustomerIdProof> getCustomerIdProofListAll();
	 public List<CustomerLogin> getLoginInfoForCustomer(BigDecimal customerId);
	 
	 public void saveImage(DocumentImg document);
	 public List<DocumentImg> getImage(BigDecimal id);
	 public void saveOrUpdateVerifiedCustomer(Customer customer);
	 public void saveOrUpdateEmpInfo(CustomerEmploymentInfo custEmp);
     public void saveOrUpdateCustomerIdProof(CustomerIdProof custProof);
	public List<Customer> getCustomerInfoWithToken(BigDecimal customerId,String tokenNumber);
	public void saveOrUpdateContact(ContactDetail contactDetail);
	
	public List<Amlstatus> amlCheck(String name);
	public String checkExistOrNot(Object value);
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId);
	
	public List<BranchSystemInventory> fetchSystemAllocation(BigDecimal branchId);
	public boolean saveSystemInventory(List<BranchSystemInventory> lstSysAllDt);
	public boolean saveEmpSystemAssign(List<EmployeeSystemsAssigned> lstSysAllDt);
	public void deleteBranchSystemInventory(BigDecimal branchsystemInventoryId,String remarks);
	public List<EmployeeSystemsAssigned> fetchEmployeeSystemAssignByUserName(BigDecimal employeeId);
	public void deleteEmpSystemAssign(BigDecimal branchsystemInventoryId,String remarks);
	public List<BranchSystemInventory> fetchSystemAllocationByIp(String ipAddress);

}
