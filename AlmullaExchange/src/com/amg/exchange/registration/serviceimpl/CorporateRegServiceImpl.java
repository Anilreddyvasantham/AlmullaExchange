package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.registration.dao.ICorporateRegDao;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.PartnerAuthorized;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("corporateRegServiceImpl")
public class CorporateRegServiceImpl<T> implements ICorporateRegService<T>, Serializable {
	@Autowired
    ICorporateRegDao<T>  corporateRegDao;
	
	public ICorporateRegDao<T> getCorporateRegDao() {
		return corporateRegDao;
	}
	public void setCorporateRegDao(ICorporateRegDao<T> corporateRegDao) {
		this.corporateRegDao = corporateRegDao;
	}

	@Override
	@Transactional
	public List<Amlstatus> getAMLStatusList(String compName) {
		
		return corporateRegDao.getAMLStatusList(compName);
	}

	@Override
	@Transactional
	public void saveCorporateCustomer(Customer cust) {
		 corporateRegDao.saveCorporateCustomer(cust);
		
	}

	
	@Override
	@Transactional
	public void saveCorporateContDtl(ContactDetail contdetail) {
		
		corporateRegDao.saveCorporateContDtl(contdetail);
	}

	@Override
	@Transactional
	public void saveCoIdentityDoc(CustomerIdProof custmrIdProof) {
		corporateRegDao.saveCoIdentityDoc(custmrIdProof);
		
	}

	@Override
	@Transactional
	public void savePartnerDtl(CustomerIdProof custmrIdProof) {
		corporateRegDao.savePartnerDtl(custmrIdProof);
		
	}

	@Override
	@Transactional
	public void saveAuthouSigDoc(CustomerIdProof custmrIdProof) {
	   corporateRegDao.saveAuthouSigDoc(custmrIdProof);
		
	}
	
	@Override
	@Transactional
	public List<Customer> getCompanyRegistrationStatus(BigDecimal countryId, String crno) {
		
		return corporateRegDao.getCompanyRegistrationStatus(countryId, crno);
	}
	@Override
	@Transactional
	public List<ContactDetail> getContactDetail(BigDecimal customerId) {
	
		return corporateRegDao.getContactDetail(customerId); 
	}
	
	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProof(BigDecimal languageId,BigDecimal customerId, BigDecimal customerTypeId) {

		return corporateRegDao.getCustomerIdProof(languageId, customerId, customerTypeId); 
	}
	
	@Override
	@Transactional
	public List<CustomerIdProof> getCustomerIdProofForPartner(BigDecimal languageId,BigDecimal customerId, BigDecimal customerTypeId) {

		return corporateRegDao.getCustomerIdProoForPartner(languageId, customerId, customerTypeId); 
	}
	
	@Override
	@Transactional
	public List<CustCorporateAddlDetail> getCustomerCorporateAdditionalDetail(BigDecimal languageId, BigDecimal customerId,String objective) {
		
		return corporateRegDao.getCustomerCorporateAdditionalDetail(languageId, customerId,objective);
	}
	
	@Override
	@Transactional
	public void updateCorpCustomer(Customer corprateCustomer) {
		
		corporateRegDao.updateCorpCustomer(corprateCustomer);
	}
	@Override
	@Transactional
	public void updateContact(ContactDetail contdetail, BigDecimal customerId) {
		corporateRegDao.updateContact(contdetail,customerId);
		
	}
	
	@Override
	@Transactional
	public void updateCompanyIdentityDocument(CustomerIdProof custmrIdProof,
			BigDecimal customerId) {
		corporateRegDao.updateCompanyIdentityDocument(custmrIdProof,customerId);
		
	}

	@Override
    @Transactional
	public void updatePartnerDetail(CustomerIdProof custmrIdProof,BigDecimal customerId) {
		corporateRegDao.updatePartnerDetail(custmrIdProof,customerId);
		
	}
	@Override
	@Transactional
	public void updateAutourSignatoriesDetail(CustomerIdProof custmrIdProof,BigDecimal customerId) {
		corporateRegDao.updateAutourSignatoriesDetail(custmrIdProof,customerId);
		
	}
	@Override
	@Transactional
	public void updateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail, BigDecimal customerId) {
		corporateRegDao.updateSecondaryObjective(custCorpAddDetail,customerId);
		
	}
	
	@Override
	@Transactional
	public List<CustomerIdProof> getIdNoAvailabilityStatus(String idno,
			BigDecimal bdCompanyIdType) {
		
		return corporateRegDao.getIdNoAvailabilityStatus(idno,bdCompanyIdType);
	}
	
	//For Save Image
	@Transactional
	@Override
	public void saveImage(DocumentImg document) {
		corporateRegDao.saveImage(document);
	}
	
	@Transactional
	@Override
	public void updateImage(DocumentImg document, BigDecimal custId) {
		corporateRegDao.updateImage(document, custId);
	}
	@Transactional
	@Override
	public List<DocumentImg> getImage(BigDecimal id) {
		return corporateRegDao.getImage(id);
	}
	@Transactional
	@Override
	public void commonSaveOrUpdateIdProof(CustomerIdProof custmrIdProof) {
		getCorporateRegDao().commonSaveOrUpdateIdProof(custmrIdProof);
	}
	@Transactional
	@Override
	public void saveOrUpdateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail) {
		getCorporateRegDao().saveOrUpdateSecondaryObjective(custCorpAddDetail);
	}
	
	@Transactional
	 @Override
	 public List<CustomerIdProof> getRegisterId(String registerId,BigDecimal countryId) {
	  
	  return getCorporateRegDao().getRegisterId(registerId, countryId);
	 }
	 @Transactional
	 @Override
	 public List<Customer> getCustomerNameFromCustomer(BigDecimal customerIdLocal) {
	  
	  return getCorporateRegDao().getCustomerNameFromCustomer(customerIdLocal);
	 }
	 @Transactional
	@Override
	public List<CustCorporateAddlDetail> getCorporateBusinessNatureDetails(BigDecimal customerId) {
		
		return getCorporateRegDao().getCorporateBusinessNatureDetails(customerId);
	}
	 @Override
	 @Transactional
	public List<ArticleDetails> getLevelData(BigDecimal Id) {
	  return getCorporateRegDao().getLevelData(Id);
	}
	@Override
	@Transactional
	public List<ArticleMaster> getArtilces(BigDecimal countryId) {
		return getCorporateRegDao().getArtilces(countryId);
	}
	@Override
	@Transactional
	public List<CustomerIdProof> getRegisterIdWtihPartnerCustomerId(BigDecimal partnerCustomerId) {
		return getCorporateRegDao().getRegisterIdWtihPartnerCustomerId(partnerCustomerId);
	}
	@Override
	@Transactional
	public void savePartnerAuthorized(PartnerAuthorized partnerAuthorized) {
		getCorporateRegDao().savePartnerAuthorized(partnerAuthorized);
		
	}
	@Override
	@Transactional
	public List<PartnerAuthorized> getPartnerAuthorized(BigDecimal customerId) {
		return getCorporateRegDao().getPartnerAuthorized(customerId);
	}
	@Override
	@Transactional
	public String getIdNumber(BigDecimal customerId) {
		return getCorporateRegDao().getIdNumber(customerId);
	}
	@Override
	@Transactional
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId) {
		return corporateRegDao.getArtilces(countryId,languageId);
	}
	@Override
	@Transactional
	public List<String> getCustomerRefOrSave(BigDecimal custIDType,String crNo, String custType) throws AMGException {
		return corporateRegDao.getCustomerRefOrSave(custIDType, crNo, custType);
	}
	@Override
	@Transactional
	public List<Customer> getCustomerList(BigDecimal customerId) {
		return corporateRegDao.getCustomerList(customerId);
	}
	@Override
	@Transactional
	public void updateEmail(BigDecimal customerId, String email) {
		
		 corporateRegDao.updateEmail(customerId,email);
	}
	@Override
	@Transactional
	public void deleteSecondaryObjective(BigDecimal customerId) {
		corporateRegDao.deleteSecondaryObjective(customerId);
	}
	
	@Override
	@Transactional
	public List<Customer> getCompanyRegistrationStatusIsActive(BigDecimal countryId, String crno) {
		return corporateRegDao.getCompanyRegistrationStatusIsActive(countryId, crno);
	}

}
