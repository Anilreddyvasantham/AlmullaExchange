package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.registration.dao.ICorporateRegDao;
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
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class CorporateRegDaoImpl<T> extends CommonDaoImpl<T> implements ICorporateRegDao<T>, Serializable {
	
	private static final Logger LOG = Logger.getLogger(CorporateRegDaoImpl.class);

	@Override
	public void save(T entity) {
		LOG.info("Entering into save method");
		getSession().save(entity);
		LOG.info("Exit into save method");
	}

	@Override
	public void update(T entity) {
		LOG.info("Entering into update method");
		getSession().saveOrUpdate(entity);
		LOG.info("Exit into update method");
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public List<T> findByName(String name) {
		
		return null;
	}

	@Override
	public List<T> findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		LOG.info("Entering into findAll method");
		return criteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Amlstatus> getAMLStatusList(String compName) {
		LOG.info("Entering into getAMLStatusList method");
		LOG.info("compName == " + compName);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Amlstatus.class);
		dCriteria.add(Restrictions.eq("remName", compName).ignoreCase());
		LOG.info("Exit into getAMLStatusList method");
		return (List<Amlstatus>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCorporateCustomer(Customer cust) {
		LOG.info("Entering into saveCorporateCustomer method");
		save((T) cust);
		LOG.info("Exit into saveCorporateCustomer method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCorporateContDtl(ContactDetail contdetail) {
		LOG.info("Entering into saveCorporateContDtl method");
		saveOrUpdate((T) contdetail);
		LOG.info("Exit into saveCorporateContDtl method");
	}

	@Override
	public void saveCoIdentityDoc(CustomerIdProof custmrIdProof) {
		LOG.info("Entering into saveCoIdentityDoc method");
		getSession().saveOrUpdate(custmrIdProof);
		LOG.info("Exit into saveCoIdentityDoc method");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void savePartnerDtl(CustomerIdProof custmrIdProof) {
		LOG.info("Entering into savePartnerDtl method");
		save((T)custmrIdProof);
		LOG.info("Exit into savePartnerDtl method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAuthouSigDoc(CustomerIdProof custmrIdProof) {
		LOG.info("Entering into saveAuthouSigDoc method");
		save((T)custmrIdProof);
		LOG.info("Exit into saveAuthouSigDoc method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCompanyRegistrationStatus(BigDecimal countryId, String crno) {
		LOG.info("Entering into getCompanyRegistrationStatus method");
		LOG.info("countryId == "+countryId);
		LOG.info("crno == "+crno);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.setFetchMode("customer.fsArticleDetails", FetchMode.JOIN);
		detachedCriteria.createAlias("customer.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		detachedCriteria.setFetchMode("customer.fsCountryMasterByCountryId", FetchMode.JOIN);
		detachedCriteria.createAlias("customer.fsCountryMasterByCountryId", "fsCountryMasterByCountryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMasterByCountryId.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("customer.crNo", crno));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCompanyRegistrationStatus method");
		return (List<Customer>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<ContactDetail> getContactDetail(BigDecimal customerId) {
		LOG.info("Entering into getContactDetail method");
		LOG.info("customerId == " + customerId);
		DetachedCriteria creteria = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
		creteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		creteria.add(Restrictions.eq("contactDetail.activeStatus", Constants.CUST_ACTIVE_INDICATOR));
		creteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", CriteriaSpecification.LEFT_JOIN);
		creteria.setFetchMode("contactDetail.fsCountryMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCountryMaster", "fsCountryMaster", CriteriaSpecification.LEFT_JOIN);
		creteria.setFetchMode("contactDetail.fsStateMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsStateMaster", "fsStateMaster", CriteriaSpecification.LEFT_JOIN);
		creteria.setFetchMode("contactDetail.fsDistrictMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsDistrictMaster", "fsDistrictMaster", CriteriaSpecification.LEFT_JOIN);
		creteria.setFetchMode("contactDetail.fsCityMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCityMaster", "fsCityMaster", CriteriaSpecification.LEFT_JOIN);
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getContactDetail method");
		return (List<ContactDetail>) findAll(creteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerIdProof> getCustomerIdProof(BigDecimal languageId, BigDecimal customerId, BigDecimal customerTypeId) {
		LOG.info("Entering into getCustomerIdProof method");
		LOG.info("languageId == " + languageId);
		LOG.info("customerId == " + customerId);
		LOG.info("customerTypeId == " + customerTypeId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.CUST_ACTIVE_INDICATOR));
		detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", CriteriaSpecification.INNER_JOIN);
		//detachedCriteria.add(Restrictions.eq("fsBizComponentDataByCustomerTypeId.componentDataId", new BigDecimal(Constants.CORPORATE_COMPONENT_ID)));
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", CriteriaSpecification.INNER_JOIN);
		/*detachedCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", CriteriaSpecification.INNER_JOIN);*/
		BigDecimal cusProofId = getMaxCusProofId(customerId,languageId);
		if(cusProofId!=null){
			detachedCriteria.add(Restrictions.eq("customerIdProof.custProofId", cusProofId));
		}		
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> creteriaList = (List<CustomerIdProof>) findAll(detachedCriteria);
		LOG.info("size " + creteriaList.size());
		LOG.info("Exit into getCustomerIdProof method");
		return creteriaList;
	}
	
	//Added By Anil(24-01-2018)
	private BigDecimal getMaxCusProofId(BigDecimal customerId, BigDecimal languageId){
		BigDecimal maxCusProofId=null;
		String hqlQuery="select distinct Max(a.custProofId) from  CustomerIdProof a where a.fsCustomer.customerId = :pcusId and a.fsLanguageType.languageId = :plangId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pcusId", customerId);
		query.setParameter("plangId", languageId);
		
		List<BigDecimal> lstCustomerIdProof =query.list();
		if(lstCustomerIdProof.size()>0){
			maxCusProofId=lstCustomerIdProof.get(0);
		}
		return maxCusProofId;
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerIdProof> getCustomerIdProoForPartner(BigDecimal languageId, BigDecimal customerId, BigDecimal customerTypeId) {
		LOG.info("Entering into getCustomerIdProoForPartner method");
		LOG.info("languageId == " + languageId);
		LOG.info("customerId == " + customerId);
		LOG.info("customerTypeId == " + customerTypeId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus", "1"));
		detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsBizComponentDataByCustomerTypeId.componentDataId", customerTypeId));
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", CriteriaSpecification.INNER_JOIN);
		/*detachedCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", CriteriaSpecification.INNER_JOIN);*/
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> creteriaList = (List<CustomerIdProof>) findAll(detachedCriteria);
		LOG.info("Exit  into getCustomerIdProoForPartner method");
		return creteriaList;
	}
	
	
	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustCorporateAddlDetail> getCustomerCorporateAdditionalDetail(BigDecimal languageId, BigDecimal customerId, String objective) {
		LOG.info("Entering into getCustomerCorporateAdditionalDetail method");
		LOG.info("languageId == " + languageId);
		LOG.info("customerId == " + customerId);
		LOG.info("objective == " + objective);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustCorporateAddlDetail.class, "custCorporateAddlDetail");
		detachedCriteria.setFetchMode("custCorporateAddlDetail.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("custCorporateAddlDetail.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		detachedCriteria.add(Restrictions.eq("custCorporateAddlDetail.addlStatus", Constants.CUST_ACTIVE_INDICATOR));
		detachedCriteria.setFetchMode("custCorporateAddlDetail.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("custCorporateAddlDetail.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		detachedCriteria.setFetchMode("custCorporateAddlDetail.fsBizComponentDataByObjectiveId", FetchMode.JOIN);
		detachedCriteria.createAlias("custCorporateAddlDetail.fsBizComponentDataByObjectiveId", "fsBizComponentDataByObjectiveId", CriteriaSpecification.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("custCorporateAddlDetail.objectiveType", objective));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerCorporateAdditionalDetail method");
		return (List<CustCorporateAddlDetail>) findAll(detachedCriteria);
	}

	@Override
	public void updateCorpCustomer(Customer corprateCustomer) {
		LOG.info("Entering into updateCorpCustomer method");
		getSession().saveOrUpdate(corprateCustomer);
		LOG.info("Exit into updateCorpCustomer method");
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateContact(ContactDetail contdetail, BigDecimal customerId) {
		LOG.info("Entering into updateContact method");
		LOG.info("customerId == " + customerId);
		update((T) contdetail);
		LOG.info("Exit into updateContact method");
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public void updateCompanyIdentityDocument(CustomerIdProof custmrIdProof, BigDecimal customerId) {
		LOG.info("Entering into updateCompanyIdentityDocument method");
		LOG.info("customerId == " + customerId);
		update((T) custmrIdProof);
		LOG.info("Exit into updateCompanyIdentityDocument method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updatePartnerDetail(CustomerIdProof custmrIdProof, BigDecimal customerId) {
		LOG.info("Entering into updateCompanyIdentityDocument method");
		LOG.info("customerId == " + customerId);
		update((T) custmrIdProof);
		LOG.info("Exit into updateCompanyIdentityDocument method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateAutourSignatoriesDetail(CustomerIdProof custmrIdProof, BigDecimal customerId) {
		LOG.info("Entering into updateAutourSignatoriesDetail method");
		LOG.info("customerId == " + customerId);
		update((T) custmrIdProof);
		LOG.info("Exit into updateAutourSignatoriesDetail method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail, BigDecimal customerId) {
		LOG.info("Entering into updateSecondaryObjective method");
		LOG.info("customerId == " + customerId);
		update((T) custCorpAddDetail);
		LOG.info("Entering into updateSecondaryObjective method");
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<CustomerIdProof> getIdNoAvailabilityStatus(String idno,BigDecimal bdCompanyIdType) {
		LOG.info("Entering into getIdNoAvailabilityStatus method");
		LOG.info("idno == " + idno);
		LOG.info("bdCompanyIdType == " + bdCompanyIdType);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		dCriteria.add(Restrictions.eq("identityInt", idno));
		dCriteria.setFetchMode("customerIdProof.fsCustomerType", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomerType", "fsCustomerType", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomerType.customerTypeId", bdCompanyIdType));
		LOG.info("Exit into getIdNoAvailabilityStatus method");
		return (List<CustomerIdProof>)findAll(dCriteria);
	}

	//Save Image 
	@SuppressWarnings("unchecked")
	@Override
	public void saveImage(DocumentImg document) {
		LOG.info("Entering into saveImage method");
		save((T)document);
		LOG.info("Exit into saveImage method");
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void updateImage(DocumentImg document, BigDecimal custId) {/*
		LOG.info("Entering into saveImage method");
		LOG.info("custId == "+custId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", custId));
		List<CustomerIdProof> proof = (List<CustomerIdProof>) findAll(dCriteria);
		DocumentImg docImage = proof.get(0).getFsDocumentImg();
		docImage.setImage(document.getImage());
		docImage.setImageName(document.getImageName());
		docImage.setUploadDate(new Date());
		save((T) docImage);
		LOG.info("Exit into saveImage method");
	*/}
	//Retive Image 
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentImg> getImage(BigDecimal imageId) {
		LOG.info("Entering into getImage method");
		LOG.info("custId == "+imageId);
		return  (List<DocumentImg>) getSession().createQuery("from DocumentImg where imgId="+imageId).list();
	}

	@Override
	public void commonSaveOrUpdateIdProof(CustomerIdProof custmrIdProof) {
		LOG.info("Entering into commonSaveOrUpdateIdProof method");
		getSession().saveOrUpdate(custmrIdProof);
		LOG.info("Exit into commonSaveOrUpdateIdProof method");
		
	}

	@Override
	public void saveOrUpdateSecondaryObjective(CustCorporateAddlDetail custCorpAddDetail) {
		LOG.info("Entering into saveOrUpdateSecondaryObjective method");
		LOG.info("the objective is"+custCorpAddDetail.getObjectiveType());
		getSession().saveOrUpdate(custCorpAddDetail);
		LOG.info("Exit into saveOrUpdateSecondaryObjective method");
	}
	
	@SuppressWarnings("unchecked")
	 @Override
	public List<CustomerIdProof> getRegisterId(String registerId, BigDecimal countryId) {
		LOG.info("Entering into getRegisterdId method");
		LOG.info("registerId == "+registerId);
		LOG.info("countryId == "+countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.fsCountryMasterByCountryId.countryId", countryId));
		dCriteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes)); // constatns yes =Y
		//dCriteria.add(Restrictions.eq("fsCustomer.activatedInd", Constants.Yes));
		
		//blocked due to signature is optional
		//dCriteria.add(Restrictions.isNotNull("fsCustomer.signatureSpecimenClob")); 
		dCriteria.add(Restrictions.eq("identityInt", registerId));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into saveOrUpdateSecondaryObjective method");
		return (List<CustomerIdProof>) findAll(dCriteria);
	}

	 @SuppressWarnings("unchecked")
	 @Override
	public List<Customer> getCustomerNameFromCustomer(BigDecimal customerIdLocal) {
		LOG.info("Entering into getCustomerNameFromCustomer method");
		LOG.info("customerIdLocal == " + customerIdLocal);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class);
		dCriteria.add(Restrictions.eq("customerId", customerIdLocal));
		LOG.info("Exit into getCustomerNameFromCustomer method");
		return (List<Customer>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustCorporateAddlDetail> getCorporateBusinessNatureDetails(BigDecimal customerId) {
		LOG.info("Entering into getCorporateBusinessNatureDetails method");
		LOG.info("customerId == " + customerId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustCorporateAddlDetail.class, "custCorporateAddlDetail");
		dCriteria.setFetchMode("custCorporateAddlDetail.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("custCorporateAddlDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCorporateBusinessNatureDetails method");
		return (List<CustCorporateAddlDetail>) findAll(dCriteria);
	}
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetails> getLevelData(BigDecimal Id) {
		LOG.info("Entering into getLevelData method");
		LOG.info("Id == " + Id);
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetails.class, "articleDetails");
		criteria.setFetchMode("articleDetails.fsArticleMaster", FetchMode.JOIN);
		criteria.createAlias("articleDetails.fsArticleMaster", "fsArticleMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsArticleMaster.articleId", Id));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getLevelData method");
		return (List<ArticleDetails>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleMaster> getArtilces(BigDecimal countryId) {
		LOG.info("Entering into getArtilces method");
		LOG.info("countryId == " + countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		criteria.add(Restrictions.eq("customerType", "C"));
		criteria.setFetchMode("articleMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("articleMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ArticleMaster> data = (List<ArticleMaster>) findAll(criteria);
		LOG.info("Exit into getArtilces method");
		return data;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<CustomerIdProof> getRegisterIdWtihPartnerCustomerId(BigDecimal partnerCustomerId) {
		LOG.info("Entering into getRegisterIdWtihPartnerCustomerId method");
		LOG.info("partnerCustomerId == " + partnerCustomerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", partnerCustomerId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getRegisterIdWtihPartnerCustomerId method");
		return (List<CustomerIdProof>)findAll(criteria);
	}

	@Override
	public void savePartnerAuthorized(PartnerAuthorized partnerAuthorized) {
		LOG.info("Entering into savePartnerAuthorized method");
	 getSession().saveOrUpdate(partnerAuthorized);
		LOG.info("Exit into savePartnerAuthorized method");
		
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<PartnerAuthorized> getPartnerAuthorized(BigDecimal customerId) {
		LOG.info("Entering into getPartnerAuthorized method");
		LOG.info("customerId == " + customerId);
		DetachedCriteria creteria = DetachedCriteria.forClass(PartnerAuthorized.class, "partnerAuthorized");
		creteria.setFetchMode("partnerAuthorized.fsCustomer", FetchMode.JOIN);
		creteria.createAlias("partnerAuthorized.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		creteria.setFetchMode("partnerAuthorized.fsCustomerByRefCustomerId", FetchMode.JOIN);
		creteria.createAlias("partnerAuthorized.fsCustomerByRefCustomerId", "fsCustomerByRefCustomerId", CriteriaSpecification.INNER_JOIN);
		/*creteria.setFetchMode("partnerAuthorized.fsDocumentImg", FetchMode.JOIN);
		creteria.createAlias("partnerAuthorized.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);*/
		creteria.setFetchMode("partnerAuthorized.fsLanguageType", FetchMode.JOIN);
		creteria.createAlias("partnerAuthorized.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getPartnerAuthorized method");
		return (List<PartnerAuthorized>) findAll(creteria);
	}

	@Override
	public String getIdNumber(BigDecimal customerId) {
		LOG.info("Entering into getIdNumber method");
		LOG.info("customerId == " + customerId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getIdNumber method");
		return ((CustomerIdProof) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getIdentityInt();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
		dCriteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
		dCriteria.createAlias("articleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("articleMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("articleMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.add(Restrictions.eq( "articleMaster.isActive",Constants.Yes));
		dCriteria.add(Restrictions.eq( "articleMaster.customerType",Constants.C));
		dCriteria.addOrder(Order.asc("articleMasterDesc.articleeDescription"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleMasterDesc>) findAll(dCriteria);
	}

	@Override
	public List<String> getCustomerRefOrSave(BigDecimal custIDType,String crNo, String custType) throws AMGException {
		List<String> outLst = new ArrayList<String>();
		
		LOG.info("FS_VALIDATE_DUPLICATE_ID custIDType :"+custIDType);
		LOG.info("crNo :"+crNo);
		LOG.info("custType :"+custType);
		
		Connection connection=null;
		try {
			//connection = DBConnection.getdataconnection();
			connection =getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			
			String call = " { call FS_VALIDATE_DUPLICATE_ID (?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, custIDType);
			cs.setString(2, crNo);
			cs.setString(3, custType);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			
			cs.execute();//teUpdate();
			String out1=cs.getString(4);
			String out2=cs.getString(5);
			
			outLst.add(out1);
			outLst.add(out2);
			
			connection.close();
		}catch (SQLException e) {
			throw new AMGException(e.getMessage());
		}
		System.out.println("!!!!!!outLst out!!!!!!!!!"+outLst);
		return outLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerList(BigDecimal customerId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class,"fsCustomer");
		detachedCriteria.add(Restrictions.eq("customerId", customerId));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Customer>)findAll(detachedCriteria);
	}

	@Override
	public void updateEmail(BigDecimal customerId, String email) {
	
		Customer customer=(Customer) getSession().get(Customer.class, customerId);
		customer.setEmail(email);
		getSession().update(customer);
		updateEmailinContactDetails(customerId,email);
		
	}
	
	public void updateEmailinContactDetails(BigDecimal customerId, String email) {
		
		ContactDetail customer=(ContactDetail) getSession().get(ContactDetail.class, customerId);
		customer.setAlterEmailId(email);
		getSession().update(customer);
		
	}

	@Override
	public void deleteSecondaryObjective(BigDecimal customerId) {
		String queryString = "delete from  CustCorporateAddlDetail  where CUSTOMER_ID =" + customerId ;
		
		
		try {
			getSession().createQuery(queryString).executeUpdate();
		} catch (HibernateException e) {
			e.printStackTrace();
		}
		finally
		{
			getSession().clear();
			getSession().flush();
			
			
			
		}
	    
	}
	
	@Override
	public List<Customer> getCompanyRegistrationStatusIsActive(BigDecimal countryId, String crno) {
		LOG.info("Entering into getCompanyRegistrationStatus method");
		LOG.info("countryId == "+countryId);
		LOG.info("crno == "+crno);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.setFetchMode("customer.fsArticleDetails", FetchMode.JOIN);
		detachedCriteria.createAlias("customer.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		detachedCriteria.setFetchMode("customer.fsCountryMasterByCountryId", FetchMode.JOIN);
		detachedCriteria.createAlias("customer.fsCountryMasterByCountryId", "fsCountryMasterByCountryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCountryMasterByCountryId.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("customer.crNo", crno));
		
		detachedCriteria.add(Restrictions.eq("customer.isActive", Constants.Yes));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCompanyRegistrationStatus method");
		return (List<Customer>) findAll(detachedCriteria);
	}

}
