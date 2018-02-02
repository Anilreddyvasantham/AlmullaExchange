package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
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
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class BranchPageDaoImpl<T> extends CommonDaoImpl<T> implements IBranchPageDao<T>, Serializable {
	
	private static final Logger LOG = Logger.getLogger(BranchPageDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@SuppressWarnings("rawtypes")
	protected Class genericClassName;

	@SuppressWarnings("rawtypes")
	public Class getGenericClassName() {
		return genericClassName;
	}

	@SuppressWarnings("rawtypes")
	public void setGenericClassName(Class genericClassName) {
		this.genericClassName = genericClassName;
	}

	@Override
	public void save(T entity) {
		LOG.info("Entering into save method");
		getSession().save(entity);
		LOG.info("Exit into save method");
	}

	@Override
	public void update(T entity) {
		LOG.info("Entering into update method");
		getSession().update(entity);
		LOG.info("Exit into update method");
	}

	@Override
	public void delete(T entity) {
		LOG.info("Entering into delete method");
		getSession().delete(entity);
		LOG.info("Exit into delete method");
	}

	@Override
	public List<T> findByName(String name) {
		return null;
	}

	@Override
	public List<T> findById(int id) {

		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T findByIdTest(T t, int id) {

		return (T) getSession().get((Class) t, id);
	}

	@Override
	public List<T> findAll() {

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(DetachedCriteria criteria) {

		return criteria.getExecutableCriteria(getSession()).list();
	}

	@Override
	public List<CustomerIdProof> popUploadedData(String id) {
		return null;
	}

	@Override
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo custEmp, BigDecimal customerid) {

	}

	@Override
	public void saveApprove(BigDecimal customerId) {

	}

	@Override
	public void removeIdDetails(BigDecimal customerId) {

	}

	@Override
	public void saveCustomerEmploymentProofInfo(CustomerIdProof customerIdProof, BigDecimal customerId) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomer(Customer customer, BigDecimal customerId) {
		LOG.info("Entering into saveCustomer method");
		LOG.info("customerId == "+customerId);
		if (customerId.intValue() == 0) {
			save((T) customer);
		} else {
			Customer customerFromDb = (Customer) getSession().get(Customer.class, customerId);
			customerFromDb.setFsCountryMasterByCountryId(customer.getFsCountryMasterByCountryId());
			customerFromDb.setFsCompanyMaster(customer.getFsCompanyMaster());
			customerFromDb.setFsLanguageType(customer.getFsLanguageType());
			customerFromDb.setShortName(customer.getShortName());
			customerFromDb.setShortNameLocal(customer.getShortNameLocal());
			customerFromDb.setAmlStatus(customer.getAmlStatus());
			customerFromDb.setTitle(customer.getTitle());
			customerFromDb.setFirstName(customer.getFirstName());
			customerFromDb.setMiddleName(customer.getMiddleName());
			customerFromDb.setLastName(customer.getLastName());
			customerFromDb.setTitleLocal(customer.getTitleLocal());
			customerFromDb.setFirstNameLocal(customer.getFirstNameLocal());
			// customerFromDb.setTokenKey(customer.getTokenKey());
			customerFromDb.setMiddleNameLocal(customer.getMiddleNameLocal());
			customerFromDb.setLastNameLocal(customer.getLastNameLocal());
			customerFromDb.setGender(customer.getGender());
			customerFromDb.setDateOfBirth(customer.getDateOfBirth());
			customerFromDb.setFsCountryMasterByNationality(customer.getFsCountryMasterByNationality());
			customerFromDb.setAlterEmailId(customer.getAlterEmailId());
			customerFromDb.setMobile(customer.getMobile());
			customerFromDb.setEmail(customer.getEmail());
			customerFromDb.setUpdatedBy(customer.getCreatedBy());
			customerFromDb.setLastUpdated(new Date());
			customerFromDb.setActivatedInd(customer.getActivatedInd());
			if (customer.getAmlStatus().equalsIgnoreCase("Pending")) {
				customerFromDb.setPlaceOfBirth(customer.getPlaceOfBirth());
				customerFromDb.setFatherName(customer.getFatherName());
				customerFromDb.setCountryOfBirth(customer.getCountryOfBirth());
			}
			update((T) customerFromDb);
		}
		LOG.info("Exit into saveCustomer method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAdditionalInfo(Customer customer, BigDecimal customerId) {
		LOG.info("Entering into saveAdditionalInfo method");
		LOG.info("customerId == "+customerId);
		if (customerId.intValue() == 0) {
			save((T) customer);
		} else {
			customer.setCustomerId(customerId);
			update((T) customer);
		}
		LOG.info("Exit into saveAdditionalInfo method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomerIndividual(Customer customer, BigDecimal customerId) {
		LOG.info("Entering into saveCustomerIndividual method");
		LOG.info("customerId == "+customerId);
		
		if (customerId.intValue() == 0) {
			save((T) customer);
		} else {
			customer.setCustomerId(customerId);
			update((T) customer);
		}
		LOG.info("Exit into saveCustomerIndividual method");
	}

	@Override
	public void addContactDetails(ContactDetail contactDetail, BigDecimal customerId) {

	}

	@SuppressWarnings("unchecked")
	@Override
	public void deleteContactDetails(BigDecimal customerId) {
		LOG.info("Entering into deleteContactDetails method");
		Customer customer = new Customer();
		customer.setCustomerId(customerId);
		delete((T) customer);
		LOG.info("Exit into deleteContactDetails method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Amlstatus> getAmlStatus(String name) {
		LOG.info("Entering into getAmlStatus method");
		LOG.info("name == "+name);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Amlstatus.class);
		detachedCriteria.add(Restrictions.eq("remName", name));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getAmlStatus method");
		return (List<Amlstatus>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		LOG.info("Entering into getCountryList method");
		LOG.info("languageId == " + languageId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", CriteriaSpecification.INNER_JOIN);
		// Join Rule Application Description Table
		detachedCriteria.setFetchMode("fsCountryMaster.fsRuleApplicationDescs", FetchMode.JOIN);
		detachedCriteria.createAlias("fsCountryMaster.fsRuleApplicationDescs", "fsRuleApplicationDescs", CriteriaSpecification.INNER_JOIN);
		// detachedCriteria.setProjection(Projections.distinct(Projections.property("countryMasterDesc.countryName")));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCountryList method");
		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CustomerIdProof> getCustomerIdProof(String idNumber) {
		LOG.info("Entering into getCustomerIdProof method");
		LOG.info("idNumber == " + idNumber);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		/*dCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);*/
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerIdProof method");
		return (List<CustomerIdProof>) findAll(dCriteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId) {
		LOG.info("Entering into getCustomerIdProofList method");
		LOG.info("customerId == " + customerId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		// Join FS_CUSTOMER table
		detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		// Add Restriction by Customer
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		// detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus",
		// "1"));
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.LEFT_OUTER_JOIN);
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.LEFT_OUTER_JOIN);
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityFor", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityFor", "fsBizComponentDataByIdentityFor", JoinType.LEFT_OUTER_JOIN);
		// Join FS_IDENTITY_TYPE Table
		detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		/*detachedCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", CriteriaSpecification.INNER_JOIN);*/
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerIdProofList method");
		return (List<CustomerIdProof>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerIdProof> getCustomerIdProofListAll() {
		LOG.info("Entering into getCustomerIdProofListAll method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		// Join FS_CUSTOMER table
		detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		// Add Restriction by Customer
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.CUST_ACTIVE_INDICATOR));
		// Join FS_CUSTOMER_TYPE Table
		detachedCriteria.setFetchMode("customerIdProof.fsCustomerType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomerType", "fsCustomerType", CriteriaSpecification.INNER_JOIN);
		// Join FS_IDENTITY_TYPE Table
		detachedCriteria.setFetchMode("customerIdProof.fsIdentityType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsIdentityType", "fsIdentityType", CriteriaSpecification.INNER_JOIN);
		// Join FS_IDENTITY_TYPE Table
		detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		/*detachedCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", CriteriaSpecification.INNER_JOIN);*/
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerIdProofListAll method");
		return (List<CustomerIdProof>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		LOG.info("Entering into getCustomerInfo method");
		LOG.info("customerId == "+customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customerDetails");
		criteria.setFetchMode("customerDetails.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customerDetails.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("customerId", customerId));
		LOG.info("Exit into getCustomerInfo method");
		return (List<Customer>) findAll(criteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId) {
		LOG.info("Entering into getCustomerContactDetails method");
		LOG.info("customerId == "+customerId);
		DetachedCriteria creteria = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
		creteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		creteria.add(Restrictions.eq("contactDetail.activeStatus", Constants.CUST_ACTIVE_INDICATOR));
		creteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", JoinType.LEFT_OUTER_JOIN);
		creteria.setFetchMode("contactDetail.fsCountryMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		creteria.setFetchMode("contactDetail.fsStateMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsStateMaster", "fsStateMaster", JoinType.LEFT_OUTER_JOIN);
		creteria.setFetchMode("contactDetail.fsDistrictMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsDistrictMaster", "fsDistrictMaster", JoinType.LEFT_OUTER_JOIN);
		creteria.setFetchMode("contactDetail.fsCityMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCityMaster", "fsCityMaster", JoinType.LEFT_OUTER_JOIN);
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerContactDetails method");
		return (List<ContactDetail>) findAll(creteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(BigDecimal customerId) {
		LOG.info("Entering into getCustomerEmploymentInfo method");
		LOG.info("customerId == "+customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmploymentInfo");
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", "fsBizComponentDataByEmploymentTypeId", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByOccupationId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByOccupationId", "fsBizComponentDataByOccupationId", JoinType.LEFT_OUTER_JOIN);
		criteria.setFetchMode("customerEmploymentInfo.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerEmploymentInfo method");
		return (List<CustomerEmploymentInfo>) findAll(criteria);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(BigDecimal customerId) {
		LOG.info("Entering into getLoginInfoForCustomer method");
		LOG.info("customerId == "+customerId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		// Join FS_CUSTOMER table
		criteria.setFetchMode("customerLogin.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerLogin.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		// Filter By Customer ID
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getLoginInfoForCustomer method");
		return (List<CustomerLogin>) findAll(criteria);
	}

	// Save Image
	@SuppressWarnings("unchecked")
	@Override
	public void saveImage(DocumentImg document) {
		LOG.info("Entering into saveImage method");
		save((T) document);
		LOG.info("Exit into saveImage method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentImg> getImage(BigDecimal imageId) {
		LOG.info("Entering into getImage method");
		LOG.info("imageId == "+imageId);
		return (List<DocumentImg>) getSession().createQuery("from DocumentImg where imgId=" + imageId).list();
		
	}

	@Override
	public void saveOrUpdateVerifiedCustomer(Customer customer) {
		LOG.info("Entering into saveOrUpdateVerifiedCustomer method");
		getSession().saveOrUpdate(customer);
		LOG.info("Exit into saveOrUpdateVerifiedCustomer method");

	}

	@Override
	public void saveOrUpdateEmpInfo(CustomerEmploymentInfo custEmp) {
		LOG.info("Entering into saveOrUpdateEmpInfo method");
		getSession().saveOrUpdate(custEmp);
		LOG.info("Exit into saveOrUpdateEmpInfo method");

	}

	@Override
	public void saveOrUpdateCustomerIdProof(CustomerIdProof custProof) {
		LOG.info("Entering into saveOrUpdateCustomerIdProof method");
		getSession().saveOrUpdate(custProof);
		LOG.info("Exit into saveOrUpdateCustomerIdProof method");
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetails> getLevelData(BigDecimal Id, BigDecimal languageid) {
		LOG.info("Entering into getLevelData method");
		LOG.info("Id == "+Id);
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetailsDesc.class, "articleDetailsDesc");
		criteria.setFetchMode("articleDetailsDesc.articleDetails", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.articleDetails", "articleDetails", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("articleDetails.fsArticleMaster.articleId", Id));
		criteria.add(Restrictions.eq("articleDetails.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("articleDetailsDesc.languageId.languageId", languageid));
		criteria.add(Restrictions.isNotNull("articleDetailsDesc.articleDetailDesc"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getLevelData method");
		return (List<ArticleDetails>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleMaster> getArtilces(BigDecimal countryId) {
		LOG.info("Entering into getArtilces method");
		LOG.info("countryId == " + countryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		criteria.setFetchMode("articleMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("articleMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("customerType", "I"));
		criteria.add(Restrictions.eq("isActive", Constants.Yes));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getArtilces method");
		List<ArticleMaster> data = (List<ArticleMaster>) findAll(criteria);
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerInfoWithToken(BigDecimal customerId, String tokenNumber) {
		LOG.info("Entering into getCustomerInfoWithToken method");
		LOG.info("customerId == " + customerId);
		LOG.info("tokenNumber == " + tokenNumber);
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");
		criteria.setFetchMode("customer.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customer.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("customerId", customerId));
		criteria.add(Restrictions.eq("tokenKey", tokenNumber));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getCustomerInfoWithToken method");
		return (List<Customer>) findAll(criteria);
	}

	@Override
	public void saveOrUpdateContact(ContactDetail contactDetail) {
		LOG.info("Entering into saveOrUpdateContact method");
		getSession().saveOrUpdate(contactDetail);
		LOG.info("Exit into saveOrUpdateContact method");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Amlstatus> amlCheck(String name) {
		LOG.info("Entering into amlCheck method");
		LOG.info("name == "+name);
		DetachedCriteria criteria = DetachedCriteria.forClass(Amlstatus.class, "amlstatus");
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		criteria.add(Restrictions.eq("remName", name));
		return (List<Amlstatus>) findAll(criteria);

	}

	@Override
	public String checkExistOrNot(Object value) {
		LOG.info("Entering into checkExistOrNot method");
		LOG.info("value == "+value.toString());
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "idProof");
		dCriteria.add(Restrictions.eq("idProof.identityInt", value.toString()));
		LOG.info("Exit into checkExistOrNot method");
		return String.valueOf(dCriteria.getExecutableCriteria(getSession()).list().size());
	}

	@Override
	public List<ArticleMasterDesc> getArtilces(BigDecimal countryId, BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
		dCriteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
		dCriteria.createAlias("articleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("articleMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("articleMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.add(Restrictions.eq( "articleMaster.isActive",Constants.Yes));
		//dCriteria.add(Restrictions.eq("articleMaster.customerType", Constants.Individual));
		dCriteria.add(Restrictions.ne("articleMaster.customerType", Constants.Corporate));
		dCriteria.addOrder(Order.asc("articleMasterDesc.articleeDescription"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ArticleMasterDesc>) findAll(dCriteria);
	}

	@Override
	public List<BranchSystemInventory> fetchSystemAllocation(BigDecimal branchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BranchSystemInventory.class, "branchSystemInventory");
		dCriteria.add(Restrictions.eq("branchSystemInventory.countryBranchId",branchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BranchSystemInventory> lstBranch = (List<BranchSystemInventory>) findAll(dCriteria);
		return lstBranch;
	}

	@Override
	public boolean saveSystemInventory(List<BranchSystemInventory> lstSysAllDt) {
		Boolean saveStatus = false;
		
		try{
			if(lstSysAllDt != null){
				for (BranchSystemInventory branchSystemInventory : lstSysAllDt) {
					getSession().saveOrUpdate(branchSystemInventory);
				}
				saveStatus = Boolean.TRUE;
			}
		}catch(Exception e){
			LOG.info(e.getMessage());
		}
		
		return saveStatus;
	}

	@Override
	public void deleteBranchSystemInventory(BigDecimal branchsystemInventoryId,String remarks) {
		BranchSystemInventory branchSystemInv = (BranchSystemInventory) getSession().get(BranchSystemInventory.class, branchsystemInventoryId);
		branchSystemInv.setIsActive(Constants.D);
		branchSystemInv.setUpdatedBy(sessionStateManage.getUserName());
		branchSystemInv.setUpdatedDate(new Date());
		branchSystemInv.setRemarks(remarks);
		getSession().update(branchSystemInv);
	}

	@Override
	public List<EmployeeSystemsAssigned> fetchEmployeeSystemAssignByUserName(BigDecimal employeeId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(EmployeeSystemsAssigned.class, "employeeSystemsAssigned");
		dCriteria.add(Restrictions.eq("employeeSystemsAssigned.employeeId",employeeId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<EmployeeSystemsAssigned> lstempSysAss = (List<EmployeeSystemsAssigned>) findAll(dCriteria);
		return lstempSysAss;
	}

	@Override
	public boolean saveEmpSystemAssign(List<EmployeeSystemsAssigned> lstSysAllDt) {
		Boolean saveStatus = false;
		
		try{
			if(lstSysAllDt != null){
				for (EmployeeSystemsAssigned employeeSystemsAssigned : lstSysAllDt) {
					getSession().saveOrUpdate(employeeSystemsAssigned);
				}
				saveStatus = Boolean.TRUE;
			}
		}catch(Exception e){
			LOG.info(e.getMessage());
		}
		
		return saveStatus;
	}

	@Override
	public void deleteEmpSystemAssign(BigDecimal branchsystemInventoryId,String remarks) {
		EmployeeSystemsAssigned employeeSystemsAssigned = (EmployeeSystemsAssigned) getSession().get(EmployeeSystemsAssigned.class, branchsystemInventoryId);
		employeeSystemsAssigned.setIsActive(Constants.D);
		employeeSystemsAssigned.setUpdatedBy(sessionStateManage.getUserName());
		employeeSystemsAssigned.setUpdatedDate(new Date());
		employeeSystemsAssigned.setRemarks(remarks);
		getSession().update(employeeSystemsAssigned);
	}

	@Override
	public List<BranchSystemInventory> fetchSystemAllocationByIp(String ipAddress) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BranchSystemInventory.class, "branchSystemInventory");
		dCriteria.add(Restrictions.eq("branchSystemInventory.ipAddress",ipAddress));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BranchSystemInventory> lstBranch = (List<BranchSystemInventory>) findAll(dCriteria);
		return lstBranch;
	}

}
