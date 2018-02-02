package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.PartnerAuthorized;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.AMGException;

public interface ICorporateRegService<T> {

	public List<Amlstatus> getAMLStatusList(String compName);
	public void saveCorporateCustomer(Customer cust);
	public void saveCorporateContDtl(ContactDetail contdetail);
	public void saveCoIdentityDoc(CustomerIdProof custmrIdProof);
	public void savePartnerDtl(CustomerIdProof custmrIdProof);
	public void saveAuthouSigDoc(CustomerIdProof custmrIdProof);
	public List<Customer> getCompanyRegistrationStatus(BigDecimal countryId, String crno);
	public List<ContactDetail> getContactDetail(BigDecimal customerId);

	public List<CustomerIdProof> getCustomerIdProof(BigDecimal languageId, BigDecimal customerId, BigDecimal customerTypeId);
	public List<CustomerIdProof> getCustomerIdProofForPartner(BigDecimal languageId, BigDecimal customerId, BigDecimal customerTypeId);
	
	public List<CustCorporateAddlDetail> getCustomerCorporateAdditionalDetail(BigDecimal languageId, BigDecimal customerId,String objective);
	public void updateCorpCustomer(Customer corprateCustomer);
	public void updateContact(ContactDetail contdetail, BigDecimal customerId);
	public void updateCompanyIdentityDocument(CustomerIdProof custmrIdProof,BigDecimal customerId);
	
	public void updatePartnerDetail(CustomerIdProof custmrIdProof,BigDecimal customerId);
	public void updateAutourSignatoriesDetail(CustomerIdProof custmrIdProof,BigDecimal customerId);
	public void updateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail, BigDecimal customerId);
	public List<CustomerIdProof> getIdNoAvailabilityStatus(String idno,BigDecimal bdCompanyIdType);
	
	//for image upload
	public void saveImage(DocumentImg document);
	public void updateImage(DocumentImg document, BigDecimal custId);
	public List<DocumentImg> getImage(BigDecimal imageId);
	public void commonSaveOrUpdateIdProof(CustomerIdProof custmrIdProof);
	public void saveOrUpdateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail);
	
	public List<CustomerIdProof> getRegisterId(String registerId, BigDecimal countryId);
	public List<Customer> getCustomerNameFromCustomer(BigDecimal customerIdLocal);
	public List<CustCorporateAddlDetail> getCorporateBusinessNatureDetails(BigDecimal customerId);
	public List<ArticleDetails> getLevelData(BigDecimal articleId);
	public List<ArticleMaster> getArtilces(BigDecimal countryId);
	public List<CustomerIdProof> getRegisterIdWtihPartnerCustomerId(BigDecimal partnerCustomerId);
	public void savePartnerAuthorized(PartnerAuthorized partnerAuthorized);
	public List<PartnerAuthorized> getPartnerAuthorized(BigDecimal customerId);
	public String getIdNumber(BigDecimal customerId);
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId);
	
	//to get customer reference number and Y or N to save the Customer
	public List<String> getCustomerRefOrSave(BigDecimal custIDType , String crNo ,String custType) throws AMGException;
	
	public List<Customer> getCustomerList(BigDecimal customerId);
	public void updateEmail(BigDecimal customerId, String email);
	public void deleteSecondaryObjective(BigDecimal customerId);
	
	public List<Customer> getCompanyRegistrationStatusIsActive(BigDecimal countryId, String crno);

}
