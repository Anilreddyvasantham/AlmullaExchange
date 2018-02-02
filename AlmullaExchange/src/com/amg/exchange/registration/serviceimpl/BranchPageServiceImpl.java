package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.IBranchPageDao;
import com.amg.exchange.registration.model.BranchSystemInventory;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.EmployeeSystemsAssigned;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

@SuppressWarnings("serial")
@Service("branchPageServiceImpl")
public class BranchPageServiceImpl<T> implements IBranchPageService<T>, Serializable{

	@Autowired
	IBranchPageDao<T> branchPageDao;

	public IBranchPageDao<T> getBranchPageDao() {
		return branchPageDao;
	}

	public void setBranchPageDao(IBranchPageDao<T> branchPageDao) {
		this.branchPageDao = branchPageDao;
	}

	public void popUploadedData() {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public List<CustomerIdProof> popUploadedData(String id) {

		return getBranchPageDao().popUploadedData(id);
	} 

	@Transactional
	@Override
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo custEmp, BigDecimal customerid) {

		getBranchPageDao().saveCustomerEmploymentInfo(custEmp, customerid);
	}

	@Transactional
	@Override
	public void saveApprove(BigDecimal customerId) {

		getBranchPageDao().saveApprove(customerId);
	}

	@Transactional
	@Override
	public void removeIdDetails(BigDecimal customerId) {

		getBranchPageDao().removeIdDetails(customerId);
	}

	@Transactional
	@Override
	public void saveCustomerEmploymentProofInfo(CustomerIdProof customerIdProof, BigDecimal customerId) {

		getBranchPageDao().saveCustomerEmploymentProofInfo(customerIdProof, customerId);
	}

	@Transactional
	@Override
	public void saveCustomer(Customer customer, BigDecimal customerId) {

		getBranchPageDao().saveCustomer(customer, customerId);
	}

	@Transactional
	@Override
	public void saveAdditionalInfo(Customer customer, BigDecimal customerId) {

		getBranchPageDao().saveAdditionalInfo(customer, customerId);
	}

	@Transactional
	@Override
	public void saveCustomerIndividual(Customer customer, BigDecimal customerId) {

		getBranchPageDao().saveCustomerIndividual(customer, customerId);
	}

	@Transactional
	@Override
	public void addContactDetails(ContactDetail contactDetail,BigDecimal customerId) {

		getBranchPageDao().addContactDetails(contactDetail, customerId);
	}

	@Transactional
	@Override 
	public void deleteContactDetails(BigDecimal customerId) {

		getBranchPageDao().deleteContactDetails(customerId);
	}

	@Transactional
	@Override
	public List<Amlstatus> getAmlStatus(String name) {

		return getBranchPageDao().getAmlStatus(name);
	}

	@Transactional
	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		return getBranchPageDao().getCountryList(languageId);
	}

	@Transactional
	@Override
	public List<CustomerIdProof> getCustomerIdProof(String idNumber) {
		return getBranchPageDao().getCustomerIdProof(idNumber);
	}

	@Transactional
	@Override
	public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId) {
		return getBranchPageDao().getCustomerIdProofList(customerId);
	}

	@Transactional
	@Override
	public List<CustomerIdProof> getCustomerIdProofListAll() {
		return getBranchPageDao().getCustomerIdProofListAll();
	}

	@Transactional
	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		return getBranchPageDao().getCustomerInfo(customerId);
	}

	@Transactional
	@Override
	public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId) {
		return getBranchPageDao().getCustomerContactDetails(customerId);
	}

	@Transactional
	@Override
	public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(BigDecimal customerId) {
		return getBranchPageDao().getCustomerEmploymentInfo(customerId);
	}

	@Transactional
	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(BigDecimal customerId) {
		return getBranchPageDao().getLoginInfoForCustomer(customerId);
	}

	//For Save Image
	@Transactional
	@Override
	public void saveImage(DocumentImg document) {
		getBranchPageDao().saveImage(document);
	}

	@Transactional
	@Override
	public List<DocumentImg> getImage(BigDecimal id) {
		return getBranchPageDao().getImage(id);
	}
	@Transactional
	@Override
	public void saveOrUpdateVerifiedCustomer(Customer customer) {
		getBranchPageDao().saveOrUpdateVerifiedCustomer(customer);

	}

	@Transactional
	@Override
	public void saveOrUpdateEmpInfo(CustomerEmploymentInfo custEmp) {
		getBranchPageDao().saveOrUpdateEmpInfo(custEmp);

	}
	@Transactional
	@Override
	public void saveOrUpdateCustomerIdProof(CustomerIdProof custProof) {
		getBranchPageDao().saveOrUpdateCustomerIdProof(custProof);

	}

	@Override
	@Transactional
	public List<ArticleDetails> getLevelData(BigDecimal Id,BigDecimal languageid) {
		return getBranchPageDao().getLevelData(Id,languageid);
	}

	@Override
	@Transactional
	public List<ArticleMaster> getArtilces(BigDecimal countryId) {
		return getBranchPageDao().getArtilces(countryId);
	}

	@Override
	@Transactional
	public List<Customer> getCustomerInfoWithToken(BigDecimal customerId,String tokenNumber) {

		return getBranchPageDao().getCustomerInfoWithToken(customerId,tokenNumber);
	}

	@Override
	@Transactional
	public void saveOrUpdateContact(ContactDetail contactDetail) {
		getBranchPageDao().saveOrUpdateContact(contactDetail);		
	}

	@Override
	@Transactional
	public List<Amlstatus> amlCheck(String name) {
		return branchPageDao.amlCheck(name);

	}

	@Override
	@Transactional
	public String checkExistOrNot(Object value) {
		return branchPageDao.checkExistOrNot(value);
	}

	@Override
	@Transactional
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId) {
		return branchPageDao.getArtilces(countryId,languageId);
	}

	@Override
	@Transactional
	public List<BranchSystemInventory> fetchSystemAllocation(BigDecimal branchId) {
		return branchPageDao.fetchSystemAllocation(branchId);
	}

	@Override
	@Transactional
	public boolean saveSystemInventory(List<BranchSystemInventory> lstSysAllDt) {
		return branchPageDao.saveSystemInventory(lstSysAllDt);
	}

	@Override
	@Transactional
	public void deleteBranchSystemInventory(BigDecimal branchsystemInventoryId,String remarks) {
		branchPageDao.deleteBranchSystemInventory(branchsystemInventoryId, remarks);
	}

	@Override
	@Transactional
	public List<EmployeeSystemsAssigned> fetchEmployeeSystemAssignByUserName(BigDecimal employeeId) {
		return branchPageDao.fetchEmployeeSystemAssignByUserName(employeeId);
	}

	@Override
	@Transactional
	public boolean saveEmpSystemAssign(List<EmployeeSystemsAssigned> lstSysAllDt) {
		return branchPageDao.saveEmpSystemAssign(lstSysAllDt);
	}

	@Override
	@Transactional
	public void deleteEmpSystemAssign(BigDecimal branchsystemInventoryId,String remarks) {
		branchPageDao.deleteEmpSystemAssign(branchsystemInventoryId, remarks);
	}

	@Override
	@Transactional
	public List<BranchSystemInventory> fetchSystemAllocationByIp(String ipAddress) {
		return branchPageDao.fetchSystemAllocationByIp(ipAddress);
	}

} 
