package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bean.CustomerRegistrationBranchBean;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.ICustomerRegistrationBranchDao;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CorpatePartnerInfoView;
import com.amg.exchange.registration.model.CorporateCustomerInfoView;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerChangeLog;
import com.amg.exchange.registration.model.CustomerContactDetailView;
import com.amg.exchange.registration.model.CustomerDependant;
import com.amg.exchange.registration.model.CustomerEmployeeInfoView;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.CustomerSponsor;
import com.amg.exchange.registration.model.DMSApplMap;
import com.amg.exchange.registration.model.DmsDocBlobUpload;
import com.amg.exchange.registration.model.DmsDocumentBlobTemparory;
import com.amg.exchange.registration.model.DmsMas;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.EMOSCustomer;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.model.SmartCardInfo;
import com.amg.exchange.registration.model.ViewExDmsApplMap;
import com.amg.exchange.registration.model.ViewOMIDTemp;
import com.amg.exchange.remittance.model.AuthorizedLog;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({"serial", "deprecation", "unchecked" })
@Repository
public class CustomerRegistrationBranchDaoImpl<T> extends CommonDaoImpl<T> implements ICustomerRegistrationBranchDao<T>, Serializable {
	
	Logger log = Logger.getLogger(CustomerRegistrationBranchBean.class); 
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public void saveCustomer(Customer customer) {
		getSession().saveOrUpdate(customer);
		
	}

	@Override
	public void saveCustomerIdProof(CustomerIdProof proof) {
		getSession().saveOrUpdate(proof);
	}

	@Override
	public void saveImage(DocumentImg document) {
		getSession().saveOrUpdate(document);
		
	}

	@Override
	public void saveContactDetails(ContactDetail contactDetail) {
		getSession().saveOrUpdate(contactDetail);
		
	}

	@Override
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp) {
		getSession().saveOrUpdate(customerEmp);
		
	}

	@Override
	public CustomerIdProof getCustomerIdProof(String idNumber) {
		
		return null;
	}

	
	@Override
	public List<CustomerIdProof> getCustomerIdProofLst(BigDecimal idType,
			String idNumber) {
		 DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
			
	        dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", idType));
			
			/*dCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);*/
			
			dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
			dCriteria.setFetchMode("customerIdProof.fsCustomer.fsArticleDetails", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
			
			dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
			dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			return (List<CustomerIdProof>) findAll(dCriteria);
	}
	
	
	@Override
	public List<CustomerIdProof> getCustomerIdProofList(BigDecimal customerId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		
		// Join FS_CUSTOMER table
		detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
		
		// Add Restriction by Customer
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.CUST_ACTIVE_INDICATOR));
		
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.LEFT_OUTER_JOIN);
		
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.LEFT_OUTER_JOIN);
		
		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityFor",  FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityFor", "fsBizComponentDataByIdentityFor", JoinType.LEFT_OUTER_JOIN);
		
		// Join FS_IDENTITY_TYPE Table
		detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
		
		/*detachedCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", CriteriaSpecification.INNER_JOIN);*/
		
		//detachedCriteria.addOrder(Order.desc("customerIdProof.imgUploadDate"));
		
		detachedCriteria.addOrder(Order.desc("customerIdProof.creationDate"));
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<CustomerIdProof>)findAll(detachedCriteria);
	}
	
	
	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,"customerDetails");
		
		criteria.setFetchMode("customerDetails.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customerDetails.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("customerId", customerId));
		
	//	criteria.addOrder(Order.asc("Customer.fsArticleDetails.fsArticleMaster.articleId"));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)findAll(criteria);
	}
	
	
	@Override
	public List<ContactDetail> getCustomerContactDetails(BigDecimal customerId) { 
		

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
		
		return (List<ContactDetail>) findAll(creteria);
	}
	
	
	@Override
	public List<CustomerEmploymentInfo> getCustomerEmploymentInfo(BigDecimal customerId) {

		
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmploymentInfo");
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId",  FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", "fsBizComponentDataByEmploymentTypeId", 
																																JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByOccupationId",  FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByOccupationId", "fsBizComponentDataByOccupationId", 
																																JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsCustomer",  FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("customerEmploymentInfo.isActive", Constants.Yes));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	
	return (List<CustomerEmploymentInfo>)findAll(criteria);
}

	
	@Override
	public List<Customer> getCustomerInfoFetch(BigDecimal customerId) {
	DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,"customerDetails");
		
		criteria.add(Restrictions.eq("customerId", customerId));
		criteria.setFetchMode("customerDetails.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customerDetails.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)findAll(criteria);
	}
	
	
	@Override
	public List<CustomerIdProof> getCustomerIdProofCheck(BigDecimal idType,String idNumber,BigDecimal countryid) {
		
		List<CustomerIdProof> lstCustomerIDProof  = new ArrayList<CustomerIdProof>();
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCustomer.fsCountryMasterByCountryId.countryId", countryid));

		dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", idType));

		/*dCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);*/

		/*dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);*/

		dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
		//dCriteria.add(Restrictions.isNotNull("customerIdProof.identityExpiryDate"));

		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<CustomerIdProof> lstCustIDPrf = (List<CustomerIdProof>) findAll(dCriteria);
		System.out.println("lstCustIDPrf :"+lstCustIDPrf.size());

		if(lstCustIDPrf!= null  && lstCustIDPrf.size() != 0){

			if(lstCustIDPrf.size() > 1){
				
				for (CustomerIdProof customerIdProof : lstCustIDPrf) {
					if(customerIdProof.getIdentityStatus() != null && customerIdProof.getFsCustomer().getIsActive() != null && customerIdProof.getIdentityStatus().equalsIgnoreCase(Constants.Yes) && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
						lstCustomerIDProof.add(customerIdProof);
					}
				}
				
				if(lstCustomerIDProof.size() == 0){
					lstCustomerIDProof.addAll(lstCustIDPrf);
				}

			}else{
				lstCustomerIDProof.addAll(lstCustIDPrf);
			}
		}

		return lstCustomerIDProof;
	}

	@Override
	public void saveSmartCardInfo(SmartCardInfo smartCardDetails) {
		getSession().saveOrUpdate(smartCardDetails);
		
	}

	
	@Override
	public List<CountryMaster> getNationalityAlphaCode(String alphaCode) {
		 DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		 dCriteria.add(Restrictions.eq("countryMaster.countryAlpha3Code", alphaCode));
		 dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		 return (List<CountryMaster>) findAll(dCriteria);
	}

	
	@Override
	 public List<DmsMas> getSmartCardData(String idNo) {
		 DetachedCriteria dCriteria = DetachedCriteria.forClass(DmsMas.class, "dmsMas");
		 dCriteria.add(Restrictions.eq("dmsMas.idno", idNo));
		 dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		 return (List<DmsMas>) findAll(dCriteria);
	}
	
		
	public List<Customer> getVerificationToken(BigDecimal customerId){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class,"fsCustomer");
		detachedCriteria.add(Restrictions.eq("customerId", customerId));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Customer>)findAll(detachedCriteria);
	}
	
	
	//Added by kani begin
		public List<CustomerIdProof> getCustomerId(BigDecimal countryid , String idNumber){
			
			System.out.println("calling getCustomerId   -----");
			DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
			
			dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsCustomer.fsCountryMasterByCountryId.countryId", countryid));
	
			dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
			
			dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	
			return (List<CustomerIdProof>) findAll(dCriteria);
			
			
			
			
		}
		
		
	
				
				public List<CustomerIdProof> getCivilID(BigDecimal cusRefNumber){
					
					//System.out.println("calling getCustomerId   -----");
					DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
					dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
					dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
					dCriteria.add(Restrictions.eq("fsCustomer.customerReference", cusRefNumber));
					
					dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
					return (List<CustomerIdProof>) findAll(dCriteria);
					
					
					//return new BigDecimal(249);
					
				}

				
				@Override
				public List<Customer> getIntroducerCustId(
						BigDecimal introducerRefNo) {
					
					
					DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class, "customer");
					
					dCriteria.add(Restrictions.eq("customerReference", introducerRefNo));
					
					dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					return (List<Customer>) findAll(dCriteria);
				}
				
		
				
				
				
				@Override
				public List<CustomerIdProof> getRefCustCivilID(BigDecimal refCusId) {
					
					
					DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
					
					dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
					dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
					
					dCriteria.add(Restrictions.eq("fsCustomer.customerReference", refCusId));
					
					dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
									
					return (List<CustomerIdProof>) findAll(dCriteria);
				}

				
				@Override
				public List<CountryMasterDesc> getNationalityCode(BigDecimal languageId, String countryCode) {
					 DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
					
					// Join Language Type table
						detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
						detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
						
						// Add Language Condition
						detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
						
						
						// Join Country Master Table
						detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
						detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
                      
						detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryCode", countryCode));

					 
						detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					 return (List<CountryMasterDesc>) findAll(detachedCriteria);
				}

				@Override
				public void saveSmartCardDeatils(DmsMas smartCardDetails) {
					//save DMSMas while Smart Card// added by nazish on 4-August-2015
					DetachedCriteria dCriteria = DetachedCriteria.forClass(DmsMas.class, "dms");
					dCriteria.add(Restrictions.eq("dmsIdMasId", smartCardDetails.getDmsIdMasId()));

					
					List<DmsMas> lstDmsMas = ((List<DmsMas>)findAll(dCriteria));
					if(lstDmsMas.size()>0){
						DmsMas DmsMas=lstDmsMas.get(0);
						if(smartCardDetails.getIdno()!=null){

							if(DmsMas.getIdno().equalsIgnoreCase(smartCardDetails.getIdno())){
								getSession().merge(smartCardDetails);
								//getSession().update(dmsMasSave);
							}
						}
					}else{
						getSession().save(smartCardDetails);

					}
				}

				
				@Override
				public void saveCustomerRegistration(HashMap<String, Object> saveMapInfo) throws Exception{
					
					Customer customerinfo = (Customer)saveMapInfo.get("customerinfo");
					List<ContactDetail> lstCustContactDetails =  (List<ContactDetail>)saveMapInfo.get("customerContactDetails");
					Boolean objExists = (Boolean)saveMapInfo.get("custIdProofexists");
					Boolean custEmpExists = (Boolean)saveMapInfo.get("customerUnEmployee");
					CustomerSponsor customerDependants = (CustomerSponsor)saveMapInfo.get("customerDepandantDetails");

					try{
						//save the Customer Registration
						if(customerinfo != null){
							getSession().saveOrUpdate(customerinfo);
						}
						
						//save the Customer Employee Information with Customer ID
						if( custEmpExists != null ){
							if(custEmpExists.booleanValue()){
								CustomerEmploymentInfo custEmployeeInfo = (CustomerEmploymentInfo)saveMapInfo.get("customerEmployeeInfo");
								//custEmployeeInfo.setFsCustomer(customerinfo);
								getSession().saveOrUpdate(custEmployeeInfo);
							}
						}
						
						//save the Customer Contact Information with Customer ID
						if( lstCustContactDetails != null ){
							for(ContactDetail lstContact : lstCustContactDetails){
								//lstContact.setFsCustomer(customerinfo);
								getSession().saveOrUpdate(lstContact);
							}
						}
						
                  //save or update CustomerDependant
						
						if(customerDependants !=null){
							
							getSession().saveOrUpdate(customerDependants);
						}
						//save the Customer ID Proof Information with Customer ID
						if( objExists != null ){
							if(objExists.booleanValue()){
								List<CustomerIdProof> customerIdProof = (List<CustomerIdProof>)saveMapInfo.get("customerIdProof");
								for(CustomerIdProof lstIdProof : customerIdProof){
									if(lstIdProof.getFsCustomer().getCustomerId() == null){
										lstIdProof.setFsCustomer(customerinfo);
									}
									getSession().saveOrUpdate(lstIdProof);
								}
							}
						}
						
						
					}catch(Exception e){
						log.info("Problem to redirect: " + e);
					}
				}

				@Override
				public void deleteCustContactDetails(BigDecimal custConstantPK, String userName) {
					
					ContactDetail contactDetails=(ContactDetail) getSession().get(ContactDetail.class, custConstantPK);
					//contactDetails.setActiveStatus(Constants.No);
					//Modified by Rabil as per Gini Ma'am 
					contactDetails.setActiveStatus(Constants.D);
					contactDetails.setUpdatedBy(userName);
					contactDetails.setLastUpdated(new Date());
					getSession().update(contactDetails);
				}

				@Override
				public List<CountryMaster> getCountryAlpha2Code(BigDecimal appcountryId) {
					DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
					dCriteria.add(Restrictions.eq("countryMaster.countryId", appcountryId));
					dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					return (List<CountryMaster>) findAll(dCriteria);
				}

				@Override
				public void saveSmartCardCustomerRegistration(HashMap<String, Object> saveMapInfo) throws Exception {
					

					
					Customer customerinfo = (Customer)saveMapInfo.get("customerinfo");
					List<ContactDetail> lstCustContactDetails =  (List<ContactDetail>)saveMapInfo.get("customerContactDetails");
					
					DmsMas dmsMasSave = (DmsMas)saveMapInfo.get("customerBySmartCard");
					Boolean objExists = (Boolean)saveMapInfo.get("custIdProofexists");
					Boolean custEmpExists = (Boolean)saveMapInfo.get("customerUnEmployee");
					CustomerSponsor customerDependants = (CustomerSponsor)saveMapInfo.get("customerDepandantDetails");

					try{
						//save the Customer Registration
						if(customerinfo != null){
							getSession().saveOrUpdate(customerinfo);
						}
						
						//save the Customer Employee Information with Customer ID
						if( custEmpExists != null ){
							if(custEmpExists.booleanValue()){
								CustomerEmploymentInfo custEmployeeInfo = (CustomerEmploymentInfo)saveMapInfo.get("customerEmployeeInfo");
								//custEmployeeInfo.setFsCustomer(customerinfo);
								getSession().saveOrUpdate(custEmployeeInfo);
							}
						}
						
						//save the Customer Contact Information with Customer ID
						if( lstCustContactDetails != null ){
							for(ContactDetail lstContact : lstCustContactDetails){
								//lstContact.setFsCustomer(customerinfo);
								getSession().saveOrUpdate(lstContact);
							}
						}
						
						//save or update CustomerDependant
						
						if(customerDependants !=null){
							
							getSession().saveOrUpdate(customerDependants);
						}
						
						
						//save the Customer ID Proof Information with Customer ID
						if( objExists != null ){
							if(objExists.booleanValue()){
								List<CustomerIdProof> customerIdProof = (List<CustomerIdProof>)saveMapInfo.get("customerIdProof");
								for(CustomerIdProof lstIdProof : customerIdProof){
									if(lstIdProof.getFsCustomer().getCustomerId() == null){
										lstIdProof.setFsCustomer(customerinfo);
									}
									getSession().saveOrUpdate(lstIdProof);
								}
							}
						}
						
						//save DMSMas while Smart Card// added by nazish on 16- April- 2015
						DetachedCriteria dCriteria = DetachedCriteria.forClass(DmsMas.class, "dms");
						dCriteria.add(Restrictions.eq("dmsIdMasId", dmsMasSave.getDmsIdMasId()));

						
						List<DmsMas> lstDmsMas = ((List<DmsMas>)findAll(dCriteria));
						if(lstDmsMas.size()>0){
							DmsMas DmsMas=lstDmsMas.get(0);
							if(dmsMasSave.getIdno()!=null){

								if(DmsMas.getIdno().equalsIgnoreCase(dmsMasSave.getIdno())){
									getSession().merge(dmsMasSave);
									//getSession().update(dmsMasSave);
								}
							}
						}else{
							getSession().save(dmsMasSave);

						}

						
					}catch(Exception e){
						log.info("Problem to redirect: " + e);
					}
				
				}
				
				@Override
				public void deleteEmployeeInfo(BigDecimal employeeInfoPK) {
					
					
					CustomerEmploymentInfo custEmpInfo=(CustomerEmploymentInfo) getSession().get(CustomerEmploymentInfo.class, employeeInfoPK);					
					getSession().delete(custEmpInfo);
					
				}
				
				@Override
				public List<String> getCustomerRefOrSave(BigDecimal custIDType, String custNumber, String custType) throws AMGException {
					
					List<String> outLst = new ArrayList<String>();
					
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
						cs.setString(2, custNumber);
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

				@Override
				public List<CustomerChangeLog> findCustomerChangeLog(BigDecimal customerId, String verificationToken) {
					DetachedCriteria critiria = DetachedCriteria.forClass(CustomerChangeLog.class,"customerChangeLog");
					critiria.add(Restrictions.eq("customerChangeLog.customerId", customerId));
					critiria.add(Restrictions.eq("customerChangeLog.verificationToken", verificationToken));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CustomerChangeLog> lstcustChangelog = (List<CustomerChangeLog>) findAll(critiria);
					return lstcustChangelog;
				}
				
				@Override
				public List<CustomerInfoView> findCustomerRegistration(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CustomerInfoView.class,"customerInfoView");
					
					critiria.add(Restrictions.eq("customerInfoView.customerId", customerId));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CustomerInfoView> customerList = (List<CustomerInfoView>) findAll(critiria);
					return customerList;
				}
				
				
				
				@Override
				public List<CustomerContactDetailView> findCustomerContactDetails(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CustomerContactDetailView.class,"customerContactDetailView");
					
					critiria.add(Restrictions.eq("customerContactDetailView.customerId", customerId));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CustomerContactDetailView> customerList = (List<CustomerContactDetailView>) findAll(critiria);
					return customerList;
				}
				
				@Override
				public List<CustomerEmployeeInfoView> findCustomerEmployeeInfo(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CustomerEmployeeInfoView.class,"customerEmployeeInfoView");
					
					critiria.add(Restrictions.eq("customerEmployeeInfoView.customerId", customerId));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CustomerEmployeeInfoView> customerList = (List<CustomerEmployeeInfoView>) findAll(critiria);
					return customerList;
				}
				
				@Override
				public List<CustomerIdproofView> findCustomerIdProof(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CustomerIdproofView.class,"customerIdproofView");
					
					critiria.add(Restrictions.eq("customerIdproofView.customerId", customerId));
					critiria.add(Restrictions.ge("customerIdproofView.idProofExpiredDate", new Date()));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CustomerIdproofView> customerList = (List<CustomerIdproofView>) findAll(critiria);
					return customerList;
				}
				
				@Override
				public List<CorpatePartnerInfoView> findCorporatePartnerInfo(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CorpatePartnerInfoView.class,"corpatePartnerInfoView");
					critiria.add(Restrictions.eq("corpatePartnerInfoView.customerId", customerId));
					
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CorpatePartnerInfoView> customerList = (List<CorpatePartnerInfoView>) findAll(critiria);
					return customerList;
				}
				
				@Override
				public List<CorporateCustomerInfoView> findCorporateCustomerInfo(BigDecimal customerId){
					DetachedCriteria critiria = DetachedCriteria.forClass(CorporateCustomerInfoView.class,"corporateCustomerInfoView");
					
					
					critiria.add(Restrictions.eq("corporateCustomerInfoView.customerId", customerId));
				critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<CorporateCustomerInfoView> customerList = (List<CorporateCustomerInfoView>) findAll(critiria);
					return customerList;
				}
				
				@Override
				public void updateVerificationToken(BigDecimal customerId) {
					Connection conn = null;
					PreparedStatement pstmt = null;
					try {

						//conn = DBConnection.getdataconnection();
						conn =getDataSourceFromHibernateSession();
						String query = "update FS_CUSTOMER set verification_token =? where customer_id = ? ";
						pstmt = conn.prepareStatement(query);
						pstmt.setBigDecimal(1, null);
						pstmt.setBigDecimal(2, customerId);
						pstmt.executeUpdate();

					} catch (Exception e) {

					} finally {
						if (conn != null) {
							try {
								conn.close();
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					}
					
				}
				
				
				@Override
				public List<IdentityTypeMaster> getOcrList(BigDecimal idTypeId) {
					 DetachedCriteria dCriteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");
						dCriteria.add(Restrictions.eq("identityTypeMaster.businessComponentId",idTypeId));
						dCriteria.add(Restrictions.eq("identityTypeMaster.isActive",Constants.Yes));
						return (List<IdentityTypeMaster>)findAll(dCriteria);
				}
				
				
				@Override
				public List<CustomerIdProof> getActiveIdProofList(BigDecimal customerId) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
					
					// Join FS_CUSTOMER table
					detachedCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
					detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", CriteriaSpecification.INNER_JOIN);
					
					// Add Restriction by Customer
					detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
					
					detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus", "Y"));
					
					detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId",  FetchMode.JOIN);
					detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", 
																																			JoinType.LEFT_OUTER_JOIN);
					
					detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",  FetchMode.JOIN);
					detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", 
																																			JoinType.LEFT_OUTER_JOIN);
					
					detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityFor",  FetchMode.JOIN);
					detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityFor", "fsBizComponentDataByIdentityFor", 
																																			JoinType.LEFT_OUTER_JOIN);
					
					// Join FS_IDENTITY_TYPE Table
					detachedCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
					detachedCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", CriteriaSpecification.INNER_JOIN);
					
					//detachedCriteria.addOrder(Order.desc("customerIdProof.imgUploadDate"));
					
					detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					return (List<CustomerIdProof>)findAll(detachedCriteria);
				}

				@Override
				public void saveCustomerEmos(BigDecimal customerId,
						String emosCustomer) {
					Customer customer=(Customer) getSession().get(Customer.class, customerId);
					customer.setEmosCustomer(emosCustomer);
					getSession().update(customer);
					
				}

				@Override
				public List<String> callProcedureUpdate(BigDecimal customerId) throws AMGException {

					List<String> output = new ArrayList<String>();
					
					Connection connection=null;
					try {
						connection =getDataSourceFromHibernateSession();
					} catch (Exception e) {
						e.printStackTrace();
					}
					CallableStatement cs;
					try {
						
						String call = " { call EX_POPULATE_CUSMAS (?,?,?) } ";
						cs = connection.prepareCall(call);
						cs.setBigDecimal(1, customerId);
						cs.registerOutParameter(2, java.sql.Types.VARCHAR);
						cs.registerOutParameter(3, java.sql.Types.VARCHAR);
						cs.execute();
						String out1=cs.getString(2);
						String out2=cs.getString(3);
						output.add(out1);
						output.add(out2);
						connection.close();
					}catch (SQLException e) {
						throw new AMGException(e.getMessage());
					}
	
					return output;
				}

				
				@Override
				public List<Customer> getCustomerData(BigDecimal countryId,String mobileNo) {

					DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,"customer");
					
					criteria.setFetchMode("customer.fsArticleDetails", FetchMode.JOIN);
					criteria.createAlias("customer.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
					criteria.setFetchMode("customer.fsCountryMasterByCountryId", FetchMode.JOIN);
					criteria.createAlias("customer.fsCountryMasterByCountryId", "fsCountryMasterByCountryId", JoinType.INNER_JOIN);
					
					criteria.add(Restrictions.eq("fsCountryMasterByCountryId.countryId", countryId));
					
					criteria.add(Restrictions.eq("mobile", mobileNo));
					
					//criteria.add(Restrictions.eq("isActive", Constants.Yes));
					
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					return (List<Customer>)findAll(criteria);
				}

				
				@Override
				public List<ArticleMasterDesc> getArtilcesThroughCode(String articleCode,BigDecimal languageId) {
					DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMasterDesc");
					dCriteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
					dCriteria.createAlias("articleMasterDesc.articleMaster", "articleMaster", JoinType.INNER_JOIN);
					dCriteria.add(Restrictions.eq("articleMaster.articleCode", articleCode));
					dCriteria.setFetchMode("articleMasterDesc.languageType", FetchMode.JOIN);
					dCriteria.createAlias("articleMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
					dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
					dCriteria.add(Restrictions.eq( "articleMaster.isActive",Constants.Yes));
					dCriteria.add(Restrictions.eq("articleMaster.customerType", Constants.Individual));
				
					dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
					return (List<ArticleMasterDesc>) findAll(dCriteria);
				}

				
				@Override
				public List<RelationsDescription> getRelationsDescriptionList(BigDecimal languageId) {
					DetachedCriteria criteria = DetachedCriteria.forClass(RelationsDescription.class,"relationsDescription");
					
					criteria.setFetchMode("relationsDescription.relations",FetchMode.JOIN);
					criteria.createAlias("relationsDescription.relations", "relations", JoinType.INNER_JOIN);
					criteria.add(Restrictions.ne("relations.relationsCode", "1000"));
					
					criteria.setFetchMode("relationsDescription.languageType", FetchMode.JOIN);
					criteria.createAlias("relationsDescription.languageType", "languageType",JoinType.INNER_JOIN);
					
					
					criteria.add(Restrictions.eq("relations.isActive",Constants.Yes));
					criteria.add(Restrictions.eq("languageType.languageId",languageId));
					
					criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
					return (List<RelationsDescription>) findAll(criteria);
				}

				
				@Override
				public List<CustomerIdProof> getRegisterId(String idNumber, BigDecimal countryId, BigDecimal customerType) {
					DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
					dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
					dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
					dCriteria.add(Restrictions.eq("fsCustomer.fsCountryMasterByCountryId.countryId", countryId));
					dCriteria.add(Restrictions.eq("identityInt", idNumber));
					dCriteria.add(Restrictions.eq("fsCustomer.activatedInd", Constants.Yes));
					
					dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
					dCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
					
					dCriteria.add(Restrictions.eq("fsBizComponentDataByCustomerTypeId.componentDataId", customerType));
					
					
					dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					return (List<CustomerIdProof>) findAll(dCriteria);

				}

				
				@Override
				public List<CustomerDependant> getCustomerDependantList(
						BigDecimal customerId) {
					DetachedCriteria criteria = DetachedCriteria.forClass(CustomerDependant.class, "customerDependant");
					
					criteria.setFetchMode("customerDependant.fsCustomer",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
					criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
					
					criteria.add(Restrictions.eq("customerDependant.isActive",Constants.Yes));
					
					criteria.setFetchMode("customerDependant.fsCustomerByRefCustomerId",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.fsCustomerByRefCustomerId", "fsCustomerByRefCustomerId", JoinType.LEFT_OUTER_JOIN);
					
					criteria.setFetchMode("customerDependant.exRelations",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.exRelations", "exRelations", JoinType.INNER_JOIN);
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
					return (List<CustomerDependant>) findAll(criteria);
					
				}

				
				@Override
				public List<ArticleMaster> getArticleMasterList(BigDecimal articleId) {
					DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
					criteria.add(Restrictions.eq("articleId", articleId));
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
					return (List<ArticleMaster>) findAll(criteria);
				}

				
				@Override
				public List<CustomerDependant> getDependantList(BigDecimal customerId,BigDecimal dependantCustomerId) {
					DetachedCriteria criteria = DetachedCriteria.forClass(CustomerDependant.class, "customerDependant");
					
					criteria.setFetchMode("customerDependant.fsCustomer",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
					criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
					
					criteria.setFetchMode("customerDependant.fsCustomerByRefCustomerId",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.fsCustomerByRefCustomerId", "fsCustomerByRefCustomerId", JoinType.INNER_JOIN);
					
					
					criteria.add(Restrictions.eq("fsCustomerByRefCustomerId.customerId", dependantCustomerId));
					
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
					return (List<CustomerDependant>) findAll(criteria);
				}

				
				@Override
				public List<CustomerDependant> checkDependantList(BigDecimal customerId,BigDecimal dependantCustomerRefNo) {
					
                   DetachedCriteria criteria = DetachedCriteria.forClass(CustomerDependant.class, "customerDependant");
					
					
					criteria.add(Restrictions.eq("dependantCustomerRefNo", dependantCustomerRefNo));
					
					criteria.setFetchMode("customerDependant.fsCustomer",  FetchMode.JOIN);
					criteria.createAlias("customerDependant.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
					criteria.add(Restrictions.ne("fsCustomer.customerId", customerId));
					
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
					return (List<CustomerDependant>) findAll(criteria);
				}

				@Override
				public void updateDependant(BigDecimal customerDependantId) {
					getSession().update(customerDependantId);
					
				}

				@Override
				public void deleteDependant(BigDecimal customerDependantId) {
				CustomerDependant custDependant=(CustomerDependant) getSession().get(CustomerDependant.class, customerDependantId);		
				getSession().delete(custDependant);
					
				}

				@Override
				public BigDecimal callProcedureCustReferenceNumber(BigDecimal companyCode, String documentCode,String docum_Fin_Year, String branchId) {
					
					System.out.println("!!!!!!callProcedureCustReferenceNumber UPDNXT" + companyCode);
					BigDecimal outLst = null;
					Connection connection = null;
					try {
						connection = getDataSourceFromHibernateSession();
					} catch (Exception e) {
						e.printStackTrace();
					}
					CallableStatement cs;

					try {
						String call = " { call UPDNXT (?, ?, ?, ?, ?) } ";
						cs = connection.prepareCall(call);
						cs.setBigDecimal(1, companyCode);
						cs.setBigDecimal(2, new BigDecimal(documentCode));
						cs.setBigDecimal(3, new BigDecimal(2001)); // hard coded 2001
						cs.registerOutParameter(4, java.sql.Types.INTEGER);
						if(companyCode!= null && (companyCode.compareTo(new BigDecimal(20))==0)){
						cs.setBigDecimal(5, new BigDecimal(1)); // hard coded branch id 1 //For KUWAIT
						}else if(companyCode!= null && (companyCode.compareTo(new BigDecimal(21))==0)){
							cs.setBigDecimal(5, new BigDecimal(99)); // hard coded branch id 99 //FOR OMAN
						}
						cs.execute();
						outLst = cs.getBigDecimal(4);
						connection.close();

					} catch (SQLException e) {
						try {
							throw new AMGException(e.getMessage());
						} catch (AMGException e1) {
							e1.printStackTrace();
						}
					}
					System.out.println("!!!!!!outLst out!!!!!!!!!" + outLst);
					return outLst;
				}

				
				@Override
				public List<CustomerSponsor> getCustomerSponsorList(
						BigDecimal customerId) {
                    DetachedCriteria criteria = DetachedCriteria.forClass(CustomerSponsor.class, "customerSponsor");
					
					criteria.setFetchMode("customerSponsor.fsCustomer",  FetchMode.JOIN);
					criteria.createAlias("customerSponsor.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
					criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
					
					criteria.add(Restrictions.eq("customerSponsor.isActive",Constants.Yes));
					
					criteria.setFetchMode("customerSponsor.exRelations",  FetchMode.JOIN);
					criteria.createAlias("customerSponsor.exRelations", "exRelations", JoinType.INNER_JOIN);
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);		
					return (List<CustomerSponsor>) findAll(criteria);

				}
				

				@Override
				public String getCustomerIdentity(BigDecimal cusRefNumber) {
					
					String hqlQuery="select a.identityInt from  CustomerIdProof a inner join a.fsCustomer b where b.customerReference =  :customerRefNumber";
					
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("customerRefNumber", cusRefNumber);
					
					List<String> lstIdentity =query.list();
					
					String rtnIdentity="";
					if(lstIdentity.size()>0)
					{
						rtnIdentity=lstIdentity.get(0);
					}
					
					return rtnIdentity;
				}

				@Override
				public void updateCustomerArticle(BigDecimal pkCustomerId,
						BigDecimal articleId, BigDecimal incomeRangeId) {
					
					Customer customer=(Customer) getSession().get(Customer.class, pkCustomerId);
					IncomeRangeMaster incomeRange = new IncomeRangeMaster();
					incomeRange.setIncomeRangeId(incomeRangeId);
					customer.setFsIncomeRangeMaster(incomeRange);

					ArticleDetails articleDetail = new ArticleDetails();
					articleDetail.setArticleDetailId(articleId);
					customer.setFsArticleDetails(articleDetail);
					customer.setFsArticleDetails(articleDetail);
					getSession().update(customer);
				}

				/*@Override
				public void updateCustomer(BigDecimal pkCustomerId, String signature) {
					
					Customer customer=(Customer) getSession().get(Customer.class, pkCustomerId);
					customer.setIsActive(Constants.Yes);
					try {
						customer.setSignatureSpecimenClob(stringToClob(signature));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					getSession().update(customer);
				}*/

				@Override
				public List<String> getArea(String query1) {
					/*DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmpInfo");
					criteria.add(Restrictions.like("customerEmpInfo.area", query, MatchMode.ANYWHERE).ignoreCase());
					criteria.setProjection(Projections.property("customerEmpInfo.area"));
					criteria.addOrder(Order.asc("customerEmpInfo.area"));
					criteria.setResultTransformer(DetachedCriteria.);*/


					String hqlQuery="select distinct a.areaDesc from ViewArea a where a.areaDesc like ?";


					Query query = getSession().createQuery(hqlQuery);
					query.setParameter(0, "%"+query1.toUpperCase()+"%");


					List<String> lstIdentity =query.list();


					return lstIdentity;
				}

				@Override
				public void updateEmployment(BigDecimal pkEmploymentId) {
					CustomerEmploymentInfo employee=(CustomerEmploymentInfo) getSession().get(CustomerEmploymentInfo.class, pkEmploymentId);
					employee.setIsActive(Constants.No);
					getSession().update(employee);
					
				}

				@Override
				public void updateSponsor(BigDecimal pkSponsorId) {
					CustomerSponsor sponsor=(CustomerSponsor) getSession().get(CustomerSponsor.class, pkSponsorId);
					sponsor.setIsActive(Constants.No);
					getSession().update(sponsor);
					
				}

				@Override
				public HashMap<String, BigDecimal> getCustomerArticleInfo(BigDecimal customerId) {
				
					String hqlQuery="select a.fsArticleDetails.articleDetailId , a.fsArticleDetails.fsArticleMaster.articleId,a.fsIncomeRangeMaster.incomeRangeId from  Customer a inner join a.fsArticleDetails b  inner join a.fsIncomeRangeMaster c  "
							+ " inner join  a.fsArticleDetails.fsArticleMaster d where a.customerId =  :customerRefNumber";
					
					
					
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("customerRefNumber", customerId);
					
					//query.list();
					HashMap<String, BigDecimal> rtnMap = new HashMap<String, BigDecimal>();
					
					 List<Object[]> list = query.list();
					 
					 for (Object object : list) {
				            Object[] li = (Object[])object;
				            if(li.length>0)
				            {
				            	rtnMap.put("ArticalDetailsID", (BigDecimal) li[0]);
				            	rtnMap.put("ArticalMasterID", (BigDecimal) li[1]);
				            	rtnMap.put("IncomerangeID", (BigDecimal) li[2]);
				            }
				            
				           
				        }
					return rtnMap;
				}

				@Override
				public BigDecimal getCustomerReference(String customerIndentity) {
					
					String hqlQuery="select a.fsCustomer.customerReference from  CustomerIdProof a inner join a.fsCustomer b where a.identityInt =  :customerIdnetityNumber";
					
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("customerIdnetityNumber", customerIndentity);
					
					List<BigDecimal> lstIdentity =query.list();
					
					BigDecimal customerReference=BigDecimal.ZERO;
					if(lstIdentity.size()>0)
					{
						customerReference=lstIdentity.get(0);
					}
					
					return customerReference;
				}

				@Override
				public Map<BigDecimal, String> getAllComponentComboData(
						BigDecimal componentConfId, BigDecimal languageId,
						String CustomerType ) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Map<BigDecimal, String> getAllComponentComboDataForCustomer(
						BigDecimal languageId,
						String CustomerType,String identitiyType) {
					Map<BigDecimal, String> mapComponentComboData = new LinkedHashMap<BigDecimal, String>();
					Map<BigDecimal, String> mapComponentComboDataTemp = new LinkedHashMap<BigDecimal, String>();
					DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
					criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
					criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
					criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
										
					criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData.fsBusinessComponent", FetchMode.JOIN);
					criteria.createAlias("bizComponentDataDesc.fsBizComponentData.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
					criteria.add(Restrictions.eq("fsBusinessComponent.componentName", identitiyType));
					
					
					criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
					criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
					criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
					DetachedCriteria subCriteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
					subCriteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
					subCriteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
					subCriteria.add(Restrictions.eq("bizComponentDataRef.active", "Y"));
					//subCriteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
					subCriteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
					subCriteria.createAlias("bizComponentDataRef.fsBizComponentData", "bizComponentData", JoinType.INNER_JOIN);
					subCriteria.add(Restrictions.eqProperty("bizComponentData.componentDataId", "fsBizComponentData.componentDataId"));
					subCriteria.setProjection(Projections.distinct(Projections.property("bizComponentData.componentDataId")));
					criteria.add(Subqueries.propertyIn("fsBizComponentData.componentDataId", subCriteria));
					ProjectionList projectionList = Projections.projectionList();
					projectionList.add(Projections.property("fsBizComponentData.componentDataId"));
					projectionList.add(Projections.property("bizComponentDataDesc.dataDesc"));
					projectionList.add(Projections.property("fsBusinessComponent.componentId"));
					criteria.setProjection(projectionList);
					criteria.addOrder(Order.asc("bizComponentDataDesc.dataDesc"));
					criteria.getExecutableCriteria(getSession()).setCacheable(true);
					List<Object[]> tempList = (List<Object[]>) findAll(criteria);
					
					for (Object[] row : tempList) {
						
						String idType=getIdentityTypeMaster((BigDecimal) row[0]);
						if(idType.equalsIgnoreCase(CustomerType))
						{
							mapComponentComboData.put((BigDecimal) row[0], (String) row[1]);
						}
						
						
					}
					return mapComponentComboData;
				}
				
				
				public String getIdentityTypeMaster(BigDecimal componentId) {
					
                  			
					String hqlQuery="select  a.customerType from  IdentityTypeMaster a inner join a.businessComponent b where a.businessComponentId =  :componentID";
					
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("componentID", componentId);
					
					List<String> lstIdentity =query.list();
					
					String rtnIdentity="";
					if(lstIdentity.size()>0)
					{
						rtnIdentity=lstIdentity.get(0);
					}
					
					return rtnIdentity;
				}

				@Override
				public List<String> getAreaforCorporate(String queryString) {
					/*DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmpInfo");
					criteria.add(Restrictions.like("customerEmpInfo.area", query, MatchMode.ANYWHERE).ignoreCase());
					criteria.setProjection(Projections.property("customerEmpInfo.area"));
					criteria.addOrder(Order.asc("customerEmpInfo.area"));
					criteria.setResultTransformer(DetachedCriteria.);*/


					String hqlQuery="select distinct a.area from ContactDetail a where a.area like ?";


					Query query = getSession().createQuery(hqlQuery);
					query.setParameter(0, "%"+queryString+"%");


					List<String> lstIdentity =query.list();


					return lstIdentity;
				}

				@Override
				public String getStateDistrictIDForSmart(BigDecimal countryID ,String arbicDistrict) {
					
					SQLQuery sqlQuery = super.getSession().createSQLQuery("select EX_FN_GET_DISTRICT(:ARABIC_DISTRIC,:COUNTRY_ID) as STATEDISTRICT from Dual");
					sqlQuery.setString("ARABIC_DISTRIC", arbicDistrict);
					sqlQuery.setBigDecimal("COUNTRY_ID", countryID);
					String resultString=sqlQuery.uniqueResult().toString();
					//
					return resultString;
				}

				@Override
				public String checkAdressChangeForSmartCard(String civilID) {
					
					SQLQuery sqlQuery = super.getSession().createSQLQuery("select EX_FN_CHK_SMTCRD_ADDR_CHNG(:CIVIL_ID) as ADDRESSCHANGE from Dual");
					sqlQuery.setString("CIVIL_ID", civilID);
					String resultString=sqlQuery.uniqueResult().toString();
					return resultString;
				}

				@Override
				public boolean checkDashBoardDisplay(String civilID, BigDecimal countryId) throws AMGException{
					
					boolean isCivilIdExist=false;
					try{
						String hqlQuery="select distinct a.fsCustomer.customerId from  CustomerIdProof a inner join a.fsCustomer b where a.identityInt =  :customerIdnetityNumber and b.signatureSpecimen is not null";
						
						Query query = getSession().createQuery(hqlQuery);
						query.setParameter("customerIdnetityNumber", civilID);
						
						BigDecimal lstIdentity =(BigDecimal) query.uniqueResult();
						if(lstIdentity==null)
						{
							isCivilIdExist=false;
						}else
						{
							isCivilIdExist=true;
						}
						
					}catch(NonUniqueResultException ne)
					{
						isCivilIdExist=false;
						 throw new AMGException("Mulitple civil ids avialble in db");
					}catch(Exception e)
					{
						isCivilIdExist=false;
						throw new AMGException("Error");
					}
					
					
					
					
					return isCivilIdExist;
					
				}

				@Override
				public HashMap<String,String> checkIntroducedByActive(BigDecimal countryId,String identitity) {
					
					BigDecimal custRef= getCustomerReference(identitity);
					
					String hqlQuery="select a.fsCustomer.customerReference,a.fsCustomer.isActive from  CustomerIdProof a inner join a.fsCustomer b where a.fsCustomer.customerReference =  :customerRef "
							+ " and a.fsCustomer.fsCountryMasterByCountryId.countryId =  :countryId  and a.identityStatus = :customerIsActive";
					
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("customerRef", custRef);
					query.setParameter("countryId", countryId);
					query.setParameter("customerIsActive", Constants.Yes);
					
					HashMap<String, String> rtnMap = new HashMap<String, String>();
					
					 List<Object[]> list = query.list();
					 
					 for (Object object : list) {
				            Object[] li = (Object[])object;
				            if(li.length>0)
				            {
				            	if(li[0]!=null && li[1]!=null)
				            	{
				            		rtnMap.put("CustomerReference",   li[0].toString());
					            	rtnMap.put("CustomerActive",  li[1].toString());
				            	}
				            	
				            	
				            }
				            
				           
				        }
					return rtnMap;
					
					
				}
				
				
				public String fetchCustomerNameBasedonCustomerId(BigDecimal customerId){
					String hqlQuery="select a.firstName from  Customer a where a.customerId =  :customerIdNumber";
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("customerIdNumber", customerId);
					
					List<String> lstCustomer =query.list();
					
					String firstName="";
					if(lstCustomer.size()>0)
					{
						firstName=lstCustomer.get(0);
					}
					
					return firstName;
				}
				
				
				/**
				 * * @author		   : Rabil 
				 */
				
			public  java.sql.Clob stringToClob(String source) throws Exception
				{
				    try
				    {
				        return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
				    }
				    catch (Exception e)
				    {
				        return null;
				    }
				}

				@Override
				public BigDecimal fetchNamelength(BigDecimal countryId) {
					
					String hqlQuery="select a.nameLenght from  ViewCustomerNameCheck a where a.countryId =  :countryId";
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("countryId", countryId);
					
					List<BigDecimal> lstName=query.list();
					
					BigDecimal nameLength=BigDecimal.ZERO;
					if(lstName.size()>0)
					{
						nameLength=lstName.get(0);
					}
					return nameLength;
					
				}
				
				
				public boolean checkCustomerIdExist(String civilID) throws AMGException{
					
					boolean isCivilIdExist=false;
					try{
						String hqlQuery="select distinct a.fsCustomer.customerId from  CustomerIdProof a inner join a.fsCustomer b where a.identityInt =  :customerIdnetityNumber";
						
						Query query = getSession().createQuery(hqlQuery);
						query.setParameter("customerIdnetityNumber", civilID);
						
						BigDecimal lstIdentity =(BigDecimal) query.uniqueResult();
						if(lstIdentity==null)
						{
							isCivilIdExist=false;
						}else
						{
							isCivilIdExist=true;
						}
						
					}catch(NonUniqueResultException ne)
					{
						isCivilIdExist=false;
						 throw new AMGException("Mulitple civil ids avialble in db");
					}catch(Exception e)
					{
						isCivilIdExist=false;
						throw new AMGException("Error");
					}
					
					
					
					
					return isCivilIdExist;
					
				}

				@Override
				public void callSaveBlobDocsDob(BigDecimal blobId,
						BigDecimal docFinYear) throws AMGException {
					Connection connection = null;
					
					CallableStatement cs;
					try {
						connection = getDataSourceFromHibernateSession();
						String call = " { call SAVE_BLOB_DOCS_DB_JAVA(?, ?) } ";
						cs = connection.prepareCall(call);

						cs.setBigDecimal(1, blobId);
						cs.setBigDecimal(2, docFinYear);
						
						cs.execute();// teUpdate();
					
						
					} catch (SQLException e) {
						String erromsg = "SAVE_BLOB_DOCS_DB_JAVA" + " : " +e.getMessage();
						throw new AMGException(erromsg);
					}finally{
						try {
							connection.close();
						} catch (SQLException e) {
							String erromsg = "SAVE_BLOB_DOCS_DB_JAVA" + " : " +e.getMessage();
							throw new AMGException(erromsg);
						}
					}
					
				}

				@Override
				public List<ArcmateScanMaster> fetchArcmateMasterData(String modeOfOperation, String scanType) {
					DetachedCriteria criteria = DetachedCriteria.forClass(ArcmateScanMaster.class,"arcmateScanMaster");
					criteria.add(Restrictions.eq("modeOfOperation", modeOfOperation).ignoreCase());	
					criteria.add(Restrictions.eq("scanType", scanType).ignoreCase());
					criteria.add(Restrictions.eq("isActive", Constants.Yes));
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					return (List<ArcmateScanMaster>)findAll(criteria);
				}

				@Override
				public List<ScanIdTypeMaster> fetchScanIdTypeMasterData(BigDecimal idTypeId) {
					DetachedCriteria criteria = DetachedCriteria.forClass(ScanIdTypeMaster.class,"scanIdTypeMaster");
					criteria.add(Restrictions.eq("idTypeId", idTypeId));	
					criteria.add(Restrictions.eq("isActive", Constants.Yes));
					criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					return (List<ScanIdTypeMaster>)findAll(criteria);
				}
				
				@Override
				public BigDecimal callTogenerateBlobID(BigDecimal docFinYear)
						throws AMGException {
					
					Connection connection = null;
					BigDecimal blobId =null;
					CallableStatement cs;
					try {
						connection = getDataSourceFromHibernateSession();
						String call = " { call NEXT_AMG_DOC_SRNO_LCK (?, ?,?) } ";
						cs = connection.prepareCall(call);

						cs.setString(1, "BLID");
						cs.setBigDecimal(2, docFinYear);
						cs.registerOutParameter(3, java.sql.Types.INTEGER);
						
						cs.execute();
						blobId = cs.getBigDecimal(3);
						
					} catch (SQLException e) {
						String erromsg = "NEXT_AMG_DOC_SRNO_LCK" + " : " +e.getMessage();
						throw new AMGException(erromsg);
					}finally{
						try {
							connection.close();
						} catch (SQLException e) {
							String erromsg = "NEXT_AMG_DOC_SRNO_LCK" + " : " +e.getMessage();
							throw new AMGException(erromsg);
						}
					}
					
					
					return blobId;
				}

				@Override
				public List<ViewExDmsApplMap> checkImageAvailability(BigDecimal customerId,String identityInt,BigDecimal identityTypeId, Date idExpiryDate,BigDecimal docFinYear) {
                     DetachedCriteria critiria = DetachedCriteria.forClass(ViewExDmsApplMap.class,"viewExDmsApplMap");
					
					critiria.add(Restrictions.eq("viewExDmsApplMap.customerId", customerId));
					critiria.add(Restrictions.eq("viewExDmsApplMap.identityInt", identityInt));
					critiria.add(Restrictions.eq("viewExDmsApplMap.identityTypeId", identityTypeId));
					critiria.add(Restrictions.eq("viewExDmsApplMap.idExpiryDate", idExpiryDate));
					critiria.add(Restrictions.eq("viewExDmsApplMap.documentFinYear", docFinYear));
					critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					List<ViewExDmsApplMap> listImage = (List<ViewExDmsApplMap>) findAll(critiria);
					return listImage;
				}

				@Override
				public List<BigDecimal> viewImage(BigDecimal customerRef ,String imageId, String idNumber ,Date idExpiryDate) throws AMGException {
	            List<BigDecimal> imgList = new ArrayList<BigDecimal>();
	             
	            java.sql.Date sqlDate = new java.sql.Date(idExpiryDate.getTime());
	           System.out.println("SQL DATE:  "+sqlDate);
					Connection connection=null;
					try {
						connection =getDataSourceFromHibernateSession();
					} catch (Exception e) {
						e.printStackTrace();
					}
					CallableStatement cs;
					try {
						
						String call = " { call EX_P_LAST_IMGREF(?, ?, ?, ?, ?, ?) } ";
						cs = connection.prepareCall(call);
						cs.setBigDecimal(1, customerRef);
						cs.setString(2, imageId);
						cs.setString(3, idNumber);
						cs.setDate(4,sqlDate);
						cs.registerOutParameter(5, java.sql.Types.NUMERIC);
						cs.registerOutParameter(6, java.sql.Types.NUMERIC);
						
						cs.execute();//teUpdate();
						BigDecimal out1=cs.getBigDecimal(5);
						BigDecimal out2=cs.getBigDecimal(6);
						
						imgList.add(out1);
						imgList.add(out2);
						
						connection.close();
					}catch (SQLException e) {
						throw new AMGException(e.getMessage());
					}
					return imgList;
				}

				@Override
				public String getImageId(BigDecimal idTypeId) {
					String hqlQuery="select a.imageId from  ViewIdType a where a.componentDataId =  :idTypeId";
					Query query = getSession().createQuery(hqlQuery);
					query.setParameter("idTypeId", idTypeId);
					
					List<String> lstImgId=query.list();
					
					String imageId="";
					if(lstImgId.size()>0)
					{
						imageId=lstImgId.get(0);
					}
					return imageId;
				}

				@Override
				public List<DmsDocBlobUpload> getListDocTemData(BigDecimal scanYear, BigDecimal docBlobId) {
					  DetachedCriteria critiria = DetachedCriteria.forClass(DmsDocBlobUpload.class,"dmsDocBlobUpload");
						
						//critiria.add(Restrictions.eq("dmsDocBlobUpload.docFinanceYear", scanYear));
						critiria.add(Restrictions.eq("dmsDocBlobUpload.documentBlob_Id", docBlobId));
		
						critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
						List<DmsDocBlobUpload> listImage = (List<DmsDocBlobUpload>) findAll(critiria);
						return listImage;
				}

			 
				@Override
				public void saveOrUpdateContactDetails(ContactDetail contactDetails) {
					log.info("==================save called contatct info=============");
					/*BigDecimal contactDetailsPk =	getContactPk(contactDetails.getFsCustomer().getCustomerId(), contactDetails.getFsBizComponentDataByContactTypeId().getComponentDataId());
					if(contactDetailsPk!=null){
						contactDetails.setContactDetailId(contactDetailsPk );
						log.info("==================save called contatct info============="+contactDetails);
					}*/
					
					//contactDetails.setContactDetailId(new BigDecimal(810) );
					getSession().saveOrUpdate(contactDetails);
					
				}
					
				
				
		 	public  BigDecimal getContactPk(BigDecimal customerId,BigDecimal contactId){
		 		BigDecimal contactDetailsId=null;
		 		
		 		
					/*DetachedCriteria creteria = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
					
					creteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
					creteria.createAlias("contactDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
					
					
					creteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
					creteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", JoinType.INNER_JOIN);
					
					creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
					
					creteria.add(Restrictions.eq("fsBizComponentDataByContactTypeId.componentDataId", contactId));
					
					creteria.add(Restrictions.eq("contactDetail.activeStatus", Constants.CUST_ACTIVE_INDICATOR));
					 
					creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
					
					List<ContactDetail> listDetails	=(List<ContactDetail>) findAll(creteria);
					if(listDetails.size()>0){
						contactDetailsId = listDetails.get(0).getContactDetailId();
						}*/
		 		
		 		String hqlQuery="select  a.contactDetailId  from  ContactDetail a inner join a.fsCustomer b  inner join a.fsBizComponentDataByContactTypeId c "
		 				+ " where b.customerId =  :pcustomerId and  c.componentDataId= :pcontactId and a.activeStatus = 'Y' ";
				
				Query query = getSession().createQuery(hqlQuery);
				query.setParameter("pcustomerId", customerId);
				query.setParameter("pcontactId", contactId);
				
				contactDetailsId =(BigDecimal) query.uniqueResult();
						 	 
			    return contactDetailsId;
				}

			@Override
			public List<CustomerImageVerification> getImageStatus(
					BigDecimal customerId, String idNumber) {

				DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerImageVerification.class,"customerImageVerification");

				dCriteria.setFetchMode("customerImageVerification.customer", FetchMode.JOIN);
				dCriteria.createAlias("customerImageVerification.customer", "customer", JoinType.INNER_JOIN);
				
				dCriteria.add(Restrictions.eq("customer.customerId", customerId));
				dCriteria.add(Restrictions.eq("idNumber", idNumber));
				dCriteria.addOrder(Order.desc("customerImageVerification.customerImageVerificationIdId"));
				//dCriteria.add(Restrictions.eq("complianceStatus", Constants.REJECT_VALUE));
				
				dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				
				return (List<CustomerImageVerification>)findAll(dCriteria);
			}

			@Override
			public String getBlackListCustomer(BigDecimal countryId, String engName, String localName, BigDecimal customerTypeId,
					BigDecimal identityTypeId, String identityInt) throws AMGException{
				String out=null;
				Connection connection =null; 
				try {
					// connection = DBConnection.getdataconnection();
					connection = getDataSourceFromHibernateSession();
				} catch (Exception e) {
					e.printStackTrace();
				}
				log.info("countryId :"+countryId);
				log.info("engName :"+engName);
				log.info("localName :"+localName);
				log.info("customerTypeId :"+customerTypeId);
				log.info("identityTypeId :"+identityTypeId);
				log.info("identityInt :"+identityInt);
				
				CallableStatement cs;
				try {
					String call = " { call EX_CBK_BLACK_LIST_CHECK (?, ?, ?, ?, ?, ?, ?) } ";
					cs = connection.prepareCall(call);
					cs.setBigDecimal(1, countryId);
					cs.setString(2, engName);
					cs.setString(3, localName);
					cs.setBigDecimal(4, customerTypeId);
					cs.setBigDecimal(5, identityTypeId);
					cs.setString(6, identityInt);
					cs.registerOutParameter(7, java.sql.Types.VARCHAR);
					cs.execute();
					 out = cs.getString(7);
					 log.info("EX_CBK_BLACK_LIST_CHECK  out Value :"+out);
				} catch (SQLException e) {
					log.info("Problem Occured When Procedure Calling="+e.getMessage());
					String erromsg = "EX_CBK_BLACK_LIST_CHECK" + " : " + e.getMessage();
					throw new AMGException(erromsg);
				} finally {
					try {
						connection.close();
					} catch (SQLException e) {
						log.info("Problem Occured When Procedure Calling="+e.getMessage());
						String erromsg = "EX_GET_EXCHANGE_RATE" + " : " + e.getMessage();
						throw new AMGException(erromsg);
					}
				}
				log.info("!!!!!!outLst out!!!!!!!!!" + out);
				
				return out;
			}

			@Override
			public void delete(BigDecimal blobseqId) {
				DmsDocumentBlobTemparory blobTable=(DmsDocumentBlobTemparory) getSession().get(DmsDocumentBlobTemparory.class, blobseqId);
		        getSession().delete(blobTable);
				
			}

			@Override
			public List<DMSApplMap> viewImage(BigDecimal customerId,
					String identityInt, BigDecimal identityTypeId,
					Date idExpiryDate) {
				//List<DMSApplMap> lstDetails=null;
			//	try{
					
					 String hql=" from DMSApplMap as a  where  trunc(a.identityExpiryDate)= trunc(:pidexpirydate) and a.customerId = :pcustomerId and a.identityTypeId = :pidentityTypeId and a.identityInt = :pidentityInt";
						
						Query query = getSession().createQuery(hql); 

						query.setParameter("pidexpirydate", idExpiryDate);
						query.setParameter("pcustomerId", customerId);
						query.setParameter("pidentityTypeId", identityTypeId);
						query.setParameter("pidentityInt", identityInt);
						List<DMSApplMap> lstDetails = query.list();
						/*List<Object[]> lst=query.list();
						
						if(lst.size()>0)
						{
							for(Object[] obj:lst)
							{
								DMSApplMap c=(DMSApplMap) obj[0];
								lstDetails.add(c);
							}
						}*/
						
						
			/*			
				}catch(Exception e)
				{
					e.printStackTrace();
					
				}*/
				return lstDetails;
				
			}

			@Override
			public void callBlobRemote(BigDecimal blobId, BigDecimal docFinYear)
					throws AMGException {
				Connection connection = null;
				
				CallableStatement cs;
				try {
					connection = getDataSourceFromHibernateSession();
					String call = " { call SAVE_BLOB_DOCS_REMOTE_JAVA(?, ?) } ";
					cs = connection.prepareCall(call);

					cs.setBigDecimal(1, blobId);
					cs.setBigDecimal(2, docFinYear);
					
					cs.execute();// teUpdate();
				
					
				} catch (SQLException e) {
					String erromsg = "SAVE_BLOB_DOCS_REMOTE_JAVA" + " : " +e.getMessage();
					throw new AMGException(erromsg);
				}finally{
					try {
						connection.close();
					} catch (SQLException e) {
						String erromsg = "SAVE_BLOB_DOCS_REMOTE_JAVA" + " : " +e.getMessage();
						throw new AMGException(erromsg);
					}
				}
				
			}

			@Override
			public HashMap<String, String> callScanTableInsert(
					HashMap<String, String> inputMap, Date idExpiryDate)
					throws AMGException {
				HashMap<String, String> outputUserMap = new HashMap<String, String>();
				try{
					Connection con = null;
					CallableStatement cs;
					
					System.out.println(inputMap);
					con = getDataSourceFromHibernateSession();
					con.setAutoCommit(false);
					String call = " { call SCAN_TABLES_INSERT(?, ?, ?, ?, ?, ?, ?) } ";
					cs = con.prepareCall(call);
					cs.setBigDecimal(1,new BigDecimal(inputMap.get("BLOBID")));
					cs.setBigDecimal(2, new BigDecimal(inputMap.get("FINYEAR")));
					cs.setString(3, inputMap.get("IDNO")); 
					cs.setDate(4, new java.sql.Date(idExpiryDate.getTime()));
					cs.setBigDecimal(5, new BigDecimal(inputMap.get("DOCTYPE")));
					cs.setString(6, inputMap.get("IDDESC")); 
					cs.registerOutParameter(7, Types.VARCHAR); 
					cs.execute();
					String out = cs.getString(7);
					outputUserMap.put("ERROR_MESSAGE", out == null ? "" : out);
					if (out==null ) {
						con.commit();
					} else {
						con.rollback();
					}
					
					}catch(SQLException e){
						throw new AMGException(e.getMessage());
					}
				
				return outputUserMap;   
			}

			@Override
			public HashMap<String, String> callDeleteUploadImage(
					BigDecimal blobId) throws AMGException {
				HashMap<String, String> outputUserMap = new HashMap<String, String>();
				try{
					Connection con = null;
					CallableStatement cs;
					
					con = getDataSourceFromHibernateSession();
					con.setAutoCommit(false);
					String call = " { call SCAN_IMAGE_VIEW_REMOVE(?, ?) } ";
					cs = con.prepareCall(call);
					cs.setBigDecimal(1,blobId);
					cs.registerOutParameter(2, Types.VARCHAR); 
					cs.execute();
					String out = cs.getString(2);
					outputUserMap.put("ERROR_MESSAGE", out == null ? "" : out);
					if (out==null ) {
						con.commit();
					} else {
						con.rollback();
					}
					
					}catch(SQLException e){
						throw new AMGException(e.getMessage());
					}
				
				return outputUserMap;   
			}
			
			@Override
			public HashMap<String, String> checkCustomerContactDetailsExist(
					BigDecimal countryId, BigDecimal stateId,
					BigDecimal districtId, BigDecimal cityId) {

				HashMap<String, String> rtnMap= new HashMap<String, String>();
				String hqlQuery=null;
				Query query;

				if(countryId!=null)
				{
					hqlQuery="select count(*) from CountryMasterDesc a inner join a.fsCountryMaster b where b.countryId =:pcountryId";

					query = getSession().createQuery(hqlQuery);
					query.setParameter("pcountryId", countryId);

					Long countryCount = (Long)query.uniqueResult();

					if(countryCount!=null && countryCount.compareTo(Long.valueOf("0"))==0)
					{
						rtnMap.put("COUNTRYEXIST", "NO");
					}else
					{
						rtnMap.put("COUNTRYEXIST", "YES");

						if(stateId!=null)
						{
							hqlQuery="select count(*) from StateMasterDesc a  inner join a.fsStateMaster b inner join  b.fsCountryMaster c  where b.stateId =:pstateId and c.countryId =:pcountryId";
							query = getSession().createQuery(hqlQuery);
							query.setParameter("pstateId", stateId);
							query.setParameter("pcountryId", countryId);

							Long stateCount = (Long)query.uniqueResult();

							if(stateCount!=null && stateCount.compareTo(Long.valueOf("0"))==0)
							{
								rtnMap.put("STATEEXIST", "NO");
							}else
							{
								rtnMap.put("STATEEXIST", "YES");

								if(districtId!=null)
								{
									hqlQuery="select count(*) from DistrictMasterDesc a inner join  a.fsDistrictMaster b inner join  b.fsStateMaster c where  b.districtId = :pdistrictId and c.stateId=:pstateId";
									query = getSession().createQuery(hqlQuery);
									query.setParameter("pdistrictId", districtId);
									query.setParameter("pstateId", stateId);

									Long districtCount = (Long)query.uniqueResult();

									if(districtCount!=null && districtCount.compareTo(Long.valueOf("0"))==0)
									{
										rtnMap.put("DISTRICTEXIST", "NO");
									}else
									{
										rtnMap.put("DISTRICTEXIST", "YES");
										if(cityId!=null)
										{
											hqlQuery="select count(*) from CityMasterDesc a inner join a.fsCityMaster b  inner join b.fsDistrictMaster c inner join  c.fsStateMaster d   where b.cityId =:pcityId and c.districtId=:pdistrictId and d.stateId=:pstateId ";
											query = getSession().createQuery(hqlQuery);
											query.setParameter("pcityId", cityId);
											query.setParameter("pdistrictId", districtId);
											query.setParameter("pstateId", stateId);

											Long cityCount = (Long)query.uniqueResult();

											if(cityCount!=null && cityCount.compareTo(Long.valueOf("0"))==0)
											{
												rtnMap.put("CITYEXIST", "NO");
											}else
											{
												rtnMap.put("CITYEXIST", "YES");
											}

										}
									}

								}

							}

						}

					}

				}



				return rtnMap;
			}

			@Override
	public List<Customer> checkDuplicateFSCustomer(String idNumber) {
		log.info("Entering into checkDuplicateCustomer method");
		log.info("idNumber " + idNumber);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		dCriteria.add(Restrictions.eq("civilId", idNumber));
		dCriteria.add(Restrictions.ne("isActive", Constants.D));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exit into checkDuplicateCustomer method");
		return (List<Customer>) findAll(dCriteria);
	}

	@Override
	public List<EMOSCustomer> checkDuplicateEMOSCustomer(String idNumber,String componentCode) {
		log.info("Entering into checkDuplicateEMOSCustomer method");
		log.info("idNumber " + idNumber);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(EMOSCustomer.class, "eMOSCustomer");
		
		if(componentCode.equalsIgnoreCase(Constants.IDTYPE_CIVILID) || componentCode.equalsIgnoreCase(Constants.IDTYPE_CIVILID_NEW)){
		   dCriteria.add(Restrictions.eq("civilid", idNumber));
		}else if(componentCode.equalsIgnoreCase(Constants.IDTYPE_PASSPORT)){
			dCriteria.add(Restrictions.eq("passport", idNumber));
		}else{
		    dCriteria.add(Restrictions.eq("idno", idNumber));
		    dCriteria.add(Restrictions.eq("idtyp", componentCode));
		}
		dCriteria.add(Restrictions.isNull("recsts"));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exit into checkDuplicateEMOSCustomer method");
		return (List<EMOSCustomer>) findAll(dCriteria);
	}

	@Override
	public String getComponentCode(BigDecimal identityTpeId) {

		log.info("Entering into getComponentCode method");
		log.info("idNumber " + identityTpeId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentData.class, "BizComponentData");
		dCriteria.add(Restrictions.eq("componentDataId", identityTpeId));
		dCriteria.setFetchMode("BizComponentData.fsBusinessComponent", FetchMode.JOIN);
		dCriteria.createAlias("BizComponentData.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsBusinessComponent.componentId", new BigDecimal(69)));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exit into getComponentCode method");

		List<BizComponentData> list = (List<BizComponentData>) findAll(dCriteria);

		if (list != null && list.size() != 0) {
			return list.get(0).getComponentCode();
		}

		return null;
	}

	@Override
	public List<ContactDetail> getCustomerContactDetailsForDuplicateCheck(BigDecimal customerId){

		log.info("Entering into getCustomerContactDetailsForDuplicateCheck method");
		log.info("customerId " + customerId);
		DetachedCriteria creteria = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
		creteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
		
		creteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", JoinType.LEFT_OUTER_JOIN);
		
		creteria.createAlias("contactDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		creteria.add(Restrictions.eq("contactDetail.activeStatus", Constants.CUST_ACTIVE_INDICATOR));
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		log.info("Exit into getCustomerContactDetailsForDuplicateCheck method");

		return (List<ContactDetail>) findAll(creteria);
	}

	@Override
	public String callProcedureUpdateCustomerFromIdProof(BigDecimal pkCustomerId) throws AMGException {
	
		log.info("EX_UPDATE_CUSTOMER_IDNO  pkCustomerId :" + pkCustomerId);
		
		Connection connection = null;
		
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		String errMsg;
		try {
			cs = connection.prepareCall(" { call EX_UPDATE_CUSTOMER_IDNO (?,?)}");
			cs.setBigDecimal(1, pkCustomerId);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			errMsg = cs.getString(2);
			log.info("EX_UPDATE_CUSTOMER_IDNO errMsg Value :" + errMsg);

		} catch (SQLException e) {
			log.info("Problem while calling EX_UPDATE_CUSTOMER_IDNO " + e);
			throw new AMGException(e.getMessage());

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info("Problem while calling EX_UPDATE_CUSTOMER_ID: " + e);
				throw new AMGException(e.getMessage());
			}
		}
		return errMsg;
	}
	
	@Override
	public boolean getOtpdetails(BigDecimal customerPk) throws AMGException {
		
		boolean rtnValue=false;
		
		// OTP only for Individual only
		//String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL AND CUSTOMER_TYPE_ID = '95'";
		String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER_MOBILE_LOG WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL AND IS_ACTIVE = 'Y'" ;
		Query query = getSession().createSQLQuery(queryString);
		query.setParameter(0, customerPk);
		
		BigDecimal otpCount =(BigDecimal) query.uniqueResult();
		if(otpCount!=null && otpCount.compareTo(BigDecimal.ZERO)!=0)
		{
			rtnValue=true;
		}else
		{
			rtnValue=false;
		}
		
		return rtnValue;
	}

	@Override
	public String verifyOtpNo(BigDecimal optNo,BigDecimal customerPkId,  String userName) throws AMGException {
		
		String rtnMessage=null;
		//String queryString = "SELECT OTP_NO FROM FS_CUSTOMER WHERE OTP_NO=? and CUSTOMER_ID =?";
		String queryString = "SELECT OTP_NO FROM FS_CUSTOMER_MOBILE_LOG WHERE OTP_NO=? and CUSTOMER_ID =? AND IS_ACTIVE = 'Y'"  ;
		Query query = getSession().createSQLQuery(queryString);
		query.setBigDecimal(0, optNo);
		query.setBigDecimal(1, customerPkId);
		BigDecimal dbOtpNo = (BigDecimal)query.uniqueResult();
		
		if(dbOtpNo!=null)
		{
			if(dbOtpNo.compareTo(optNo)==0)
			{
				rtnMessage = "MATCH";
			}else
			{
				rtnMessage= "OTPNOTMATCH";
			}
		}else
		{
			rtnMessage= "OTPNOTMATCH";
		}
		
		
		return rtnMessage;
	}

	@Override
	public int updateOTPRetry(BigDecimal optNo, BigDecimal customerPkId, BigDecimal mobilePin)
			throws AMGException {
		
		/*String SQl="Update FS_CUSTOMER set OTP_NO = "+mobilePin+", OTP_VERIFIED_BY = '' , OTP_RETRY= (SELECT NVL(OTP_RETRY,0) + 1 FROM FS_CUSTOMER where CUSTOMER_ID= "+customerPkId+") , OTP_RETRY_DATE=SYSDATE "
						+" where CUSTOMER_ID= "+customerPkId;*/
		
		String SQl="Update FS_CUSTOMER_MOBILE_LOG set OTP_NO = "+mobilePin+", OTP_VERIFIED_BY = '' , OTP_RETRY= (SELECT NVL(OTP_RETRY,0) + 1 FROM FS_CUSTOMER where CUSTOMER_ID= "+customerPkId+") , OTP_RETRY_DATE=SYSDATE "
				+" where CUSTOMER_ID= "+customerPkId+" AND IS_ACTIVE = 'Y'" ;
		
		int ui=getSession().createSQLQuery(SQl).executeUpdate();
		
		return ui;
	}

	@Override
	public int getOTPRetryAttempts(BigDecimal optNo, BigDecimal customerPkId)
			throws AMGException {
		
		//String queryString = "SELECT OTP_RETRY FROM FS_CUSTOMER WHERE CUSTOMER_ID =?";
		
		String queryString = "SELECT OTP_RETRY FROM FS_CUSTOMER_MOBILE_LOG WHERE CUSTOMER_ID =? AND IS_ACTIVE = 'Y'" ;
		Query query = getSession().createSQLQuery(queryString);
		//query.setBigDecimal(0, optNo);
		query.setBigDecimal(0, customerPkId);
		int otpRetry=0;
		BigDecimal dbOtpRetry = (BigDecimal)query.uniqueResult();
		if(dbOtpRetry!=null)
		{
			otpRetry=dbOtpRetry.intValue();
		}else
		{
			otpRetry=0;
		}
		return otpRetry;
	}

	@Override
	public int clearOldOTPRetry(BigDecimal customerPkId) throws AMGException {
		
		
		/*String SQl="Update FS_CUSTOMER set OTP_RETRY= null , OTP_RETRY_DATE = null "
				+" where CUSTOMER_ID= "+customerPkId+" and trunc(OTP_RETRY_DATE)<=trunc(SYSDATE-1) and NVL(OTP_RETRY,0)>0 ";*/
		
		String SQl="Update FS_CUSTOMER_MOBILE_LOG set OTP_RETRY= null , OTP_RETRY_DATE = null "
				+" where CUSTOMER_ID= "+customerPkId+" and trunc(OTP_RETRY_DATE)<=trunc(SYSDATE-1) and NVL(OTP_RETRY,0)>0 AND IS_ACTIVE = 'Y'";

		int ui=getSession().createSQLQuery(SQl).executeUpdate();

		return ui;
	}

	@Override
	public HashMap<String, String> callOTPSendProcedure(
			HashMap<String, String> inputValues) throws AMGException {

		log.info("Entered into callOTPSendProcedure(HashMap<String, String> inputValue) Method ");
		log.info("Procedure Name = INSERT_SMS_OTP_SENDQUEUE_new");
		log.info("INSERT_SMS_OTP_SENDQUEUE_new--- Input values == "+inputValues.toString());

		HashMap<String, String> outputValues = new HashMap<String, String>();

		Connection connection = null;
		BigDecimal sequenceNo = null;

		try {
			BigDecimal languageId = new BigDecimal(inputValues.get("P_LANGUAGE")==null ? "1":inputValues.get("P_LANGUAGE"));
			int nNo = 0; // Indicates that the Language is English
			if(languageId.compareTo(new BigDecimal(1))==0){
				nNo=0;
			}else if(languageId.compareTo(new BigDecimal(2))==0){
				nNo=1;
			}

			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			//String call = " { call INSERT_SMS_OTP_SENDQUEUE2  (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			String call = " { call INSERT_SMS_OTP_SENDQUEUE_new (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, inputValues.get("P_SENDER_ID"));
			cs.setString(2, inputValues.get("P_MOBILE"));
			cs.setString(3, inputValues.get("P_MESSAGE"));
			cs.setString(4, "EMOS");
			cs.setString(5, "61");
			cs.setInt(6, 71);
			cs.setString(7, "ORS_USER");
			cs.setInt(8, nNo);
			cs.setNull(9, Types.INTEGER);
			cs.setNull(10, Types.INTEGER);
			cs.setNull(11, Types.INTEGER);
			cs.setNull(12, Types.INTEGER);
			cs.registerOutParameter(13, Types.INTEGER);
			cs.execute();
			sequenceNo = cs.getBigDecimal(13);
			log.info("INSERT_SMS_OTP_SENDQUEUE_new--- OUTPUT values == "+sequenceNo);
			if (sequenceNo!= null) {
				outputValues.put("P_SEQUENCE", sequenceNo==null ? "0" : sequenceNo.toPlainString());
				outputValues.put("P_ERROR_MESSAGE", null);
			} else {
				String erromsg = "Insert SMS OTP Send Queue un-available generate sequence";
				outputValues.put("P_ERROR_MESSAGE", erromsg);
			}
		} catch (SQLException e) {
			String erromsg = "INSERT_SMS_OTP_SENDQUEUE_new" + " : " + e.getMessage();
			outputValues.put("P_ERROR_MESSAGE", erromsg);
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "INSERT_SMS_OTP_SENDQUEUE_new" + " : " + e.getMessage();
				outputValues.put("P_ERROR_MESSAGE", erromsg);
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}

	@Override
	public String getMobileNoBasedOnCustomerId(BigDecimal customerPkId)
			throws AMGException {
		String queryString = "SELECT MOBILE FROM FS_CUSTOMER WHERE CUSTOMER_ID =?";
		Query query = getSession().createSQLQuery(queryString);
		
		query.setBigDecimal(0, customerPkId);
		
		String dbMobileNo = (String)query.uniqueResult();
		
		return dbMobileNo;
	
	}
	
	@Override
	public boolean checkOtpVerified(BigDecimal customerPk) throws AMGException {
		
		boolean rtnValue=false;
		
		//String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NOT NULL";
		
		String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER_MOBILE_LOG WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NOT NULL AND IS_ACTIVE = 'Y'";
		Query query = getSession().createSQLQuery(queryString);
		query.setParameter(0, customerPk);
		
		BigDecimal otpCount =(BigDecimal) query.uniqueResult();
		if(otpCount!=null && otpCount.compareTo(BigDecimal.ZERO)!=0)
		{
			rtnValue=true;
		}else
		{
			rtnValue=false;
		}
		
		return rtnValue;
	}
	
	@Override
	public List<String> callProcedureToGenerateKioskPin() throws AMGException {
		List<String> output = new ArrayList<String>();

		Connection connection=null;
		try {
			connection =getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {

			String call = " { call EX_P_GENERATE_KIOSK_PIN (?,?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.execute();
			String out1=cs.getString(1);
			String out2=cs.getString(2);
			output.add(out1);
			output.add(out2);
		}catch (SQLException e) {
			throw new AMGException(e.getMessage());
		}finally{
			try {
				connection.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return output;
	}
	
	@Override
	public void updateCustomer(BigDecimal pkCustomerId, String signature) {
		
		Customer customer=(Customer) getSession().get(Customer.class, pkCustomerId);
		customer.setIsActive(Constants.Yes);		
		if(signature != null){
			try {
				customer.setSignatureSpecimenClob(stringToClob(signature));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		getSession().update(customer);
	}

	@Override
	public void saveAuthorizedLogForOTP(AuthorizedLog authorizedLog) {
		// Authorized Log Insert and Fs_Customer Update OTP Verified By and Verified Date
		getSession().save(authorizedLog);
		
		/*SimpleDateFormat simple = new SimpleDateFormat("dd-MMM-yyyy");
		
		if(authorizedLog.getCustomerId().getCustomerId() != null && authorizedLog.getAuthorizedBy() != null && authorizedLog.getDocumentDate() != null){
			String SQl="Update FS_CUSTOMER set OTP_VERIFIED_BY = '"+authorizedLog.getAuthorizedBy()+"' , OTP_VERIFIED_DT = '"+simple.format(new Date())+"' "
					+" where CUSTOMER_ID= "+authorizedLog.getCustomerId().getCustomerId()+" ";
			
			String SQl="Update FS_CUSTOMER_MOBILE_LOG set OTP_VERIFIED_BY = '"+authorizedLog.getAuthorizedBy()+"' , OTP_VERIFIED_DT = '"+simple.format(new Date())+"' "
					+" where CUSTOMER_ID= "+authorizedLog.getCustomerId().getCustomerId()+" AND IS_ACTIVE = 'Y'";
			
			int ui=getSession().createSQLQuery(SQl).executeUpdate();
		}*/
		
	}
	
	@Override
	public List<ViewOMIDTemp> getOMANSamrCardDetails(String userName)
			throws AMGException {
		
		DetachedCriteria creteria = DetachedCriteria.forClass(ViewOMIDTemp.class, "viewOMIDTemp");
		creteria.add(Restrictions.eq("viewOMIDTemp.createBy", userName));
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewOMIDTemp> lstViewOMIDTemp=(List<ViewOMIDTemp>) findAll(creteria);
		return lstViewOMIDTemp;
	}

	@Override
	public int deleteRecordBeforeSmarCardReader(String userNmae) {
		
		String hqlQuery="DELETE FROM EX_ID_TEMP WHERE CREATOR = '"+userNmae+"'";
		int ui=getSession().createSQLQuery(hqlQuery).executeUpdate();
		return ui;
	}
	
@Override
	public HashMap<String, Object> checkOTPVerify(BigDecimal customerPk) throws AMGException {

		log.info("Entering into checkOTPVerify method");
		log.info("customerPk " + customerPk);
		Customer cust = null;
		BigDecimal otpNumber = BigDecimal.ZERO;
		HashMap<String, Object> lstOTPCust = new HashMap<String, Object>();
		
		boolean rtnValue=false;
		
		//String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL";
		
		String queryString = "SELECT COUNT(*) FROM FS_CUSTOMER_MOBILE_LOG WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL AND IS_ACTIVE = 'Y'";
		Query query = getSession().createSQLQuery(queryString);
		query.setParameter(0, customerPk);
		
		BigDecimal otpCount =(BigDecimal) query.uniqueResult();
		if(otpCount!=null && otpCount.compareTo(BigDecimal.ZERO)!=0)
		{
			rtnValue=true;
		}else
		{
			rtnValue=false;
		}
		
		if(rtnValue){
			
			//String queryString1 = "SELECT OTP_NO FROM FS_CUSTOMER WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL";
			
			String queryString1 = "SELECT OTP_NO FROM FS_CUSTOMER_MOBILE_LOG WHERE CUSTOMER_ID=? AND NVL(OTP_NO,0) > 0 AND OTP_VERIFIED_BY IS NULL AND IS_ACTIVE = 'Y'";
			Query query1 = getSession().createSQLQuery(queryString1);
			query1.setParameter(0, customerPk);
			
			otpNumber =(BigDecimal) query1.uniqueResult();
			if(otpNumber!=null && otpNumber.compareTo(BigDecimal.ZERO)!=0)
			{
				lstOTPCust.put("OTP_NO", otpNumber);
			}
			
			DetachedCriteria creteria = DetachedCriteria.forClass(Customer.class, "customer");
			
			creteria.add(Restrictions.eq("customer.customerId", customerPk));

			creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			log.info("Exit into getCustomerContactDetailsForDuplicateCheck method");
			
			List<Customer> customer = (List<Customer>) findAll(creteria);
			
			if(customer != null && customer.size() != 0){
				cust = customer.get(0);
			}
			lstOTPCust.put("CUSTOMER", cust);
		}else{
			lstOTPCust.put("OTP_NO", otpNumber);
			lstOTPCust.put("CUSTOMER", cust);
		}
		
		return lstOTPCust;
	}

	@Override
	public void updateCustomerSignOTP(BigDecimal pkCustomerId,String signature, BigDecimal otpNo, String authorizedBy, String authorizedRemarks) {
		
		String mobileNumber = null;

		// deactivate all mobile number of customer in fs_custome_mobile_log
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
		dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", pkCustomerId));
		dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
		List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);

		if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
			for (CustomerMobileLogModel customerMobileLogModel : lstCustomerMobileLog) {
				CustomerMobileLogModel custMobLog = (CustomerMobileLogModel) getSession().get(CustomerMobileLogModel.class, customerMobileLogModel.getCustomerMobileId());

				if(custMobLog != null && custMobLog.getOtpNo() != null && custMobLog.getOtpVerifiedBy() == null && authorizedBy == null && custMobLog.getOtpNo().compareTo(otpNo)==0){
					custMobLog.setOtpVerifiedBy(sessionStateManage.getUserName());
					custMobLog.setOtpVerifiedDate(new Date());
				}else if(custMobLog != null && custMobLog.getOtpNo() != null && custMobLog.getOtpVerifiedBy() == null && authorizedBy != null){
					custMobLog.setOtpVerifiedBy(authorizedBy);
					custMobLog.setOtpVerifiedDate(new Date());
					custMobLog.setOtpVerifiedRemarks(authorizedRemarks);
				}else{
					// no need to update otp details
					System.out.println("NO OTP");
				}
				mobileNumber = custMobLog.getMobile();
				custMobLog.setModifiedBy(sessionStateManage.getUserName());
				custMobLog.setModifiedDate(new Date());
				getSession().update(custMobLog);
			}
		}

		Customer customer = (Customer) getSession().get(Customer.class, pkCustomerId);
		customer.setIsActive(Constants.Yes);
		if(mobileNumber != null && lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
			customer.setMobile(mobileNumber);
			customer.setUpdatedBy(sessionStateManage.getUserName());
			customer.setLastUpdated(new Date());
		}
		if(signature != null){
			try {
				customer.setSignatureSpecimenClob(stringToClob(signature));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		getSession().update(customer);
		
		// if mobile number of customer modified then fs_customer_contact_details local number should be updated
		if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
			DetachedCriteria dCriteria1 = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
			
			dCriteria1.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
			dCriteria1.createAlias("contactDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			dCriteria1.add(Restrictions.eq("fsCustomer.customerId", pkCustomerId));
			
			dCriteria1.add(Restrictions.eq("contactDetail.activeStatus", Constants.Yes));
			
			dCriteria1.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
			dCriteria1.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", JoinType.INNER_JOIN);
			dCriteria1.add(Restrictions.eq("fsBizComponentDataByContactTypeId.componentDataId", new BigDecimal(Constants.localConstactTypeId)));
			
			List<ContactDetail> lstContactDetail = (List<ContactDetail>) findAll(dCriteria1);
			
			for (ContactDetail contactDetail : lstContactDetail) {
				ContactDetail custContactDet = (ContactDetail) getSession().get(ContactDetail.class, contactDetail.getContactDetailId());
				custContactDet.setMobile(mobileNumber);
				custContactDet.setUpdatedBy(sessionStateManage.getUserName());
				custContactDet.setLastUpdated(new Date());
				getSession().update(custContactDet);
			}
		}

	}

	@Override
	public List<CustomerMobileLogModel> fetchOTPDetailByCustomer(BigDecimal customerId) {

		// deactivate all mobile number of customer in fs_custome_mobile_log
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
		dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", customerId));
		dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
		List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);

		return lstCustomerMobileLog;
	}
	
	@Override
	public void updateCustomerEmailVerifiedOn(BigDecimal customerId) {
		
		/*Customer customer=(Customer) getSession().get(Customer.class, customerId);
		//customer.setEmailVerifiedOn(null);
		
		getSession().saveOrUpdate(customer);
		*/
		
		String SQl="Update FS_CUSTOMER set EMAIL_VERIFIED_ON= null  "
				+" where CUSTOMER_ID= "+customerId+" AND ISACTIVE = 'Y'";

		int ui=getSession().createSQLQuery(SQl).executeUpdate();
		
	}
	
	
	@Override
	public void updateCustomerEmailAndVerifiedOn(BigDecimal customerId,String customerEmailId) {
		
		/*Customer customer=(Customer) getSession().get(Customer.class, customerId);
		customer.setEmail(customerEmailId);
		customer.setEmailVerifiedOn(null);
		
		getSession().saveOrUpdate(customer);*/
		
		String SQl="Update FS_CUSTOMER set EMAIL ='"+customerEmailId+"' ,EMAIL_VERIFIED_ON= null  "
				+" where CUSTOMER_ID= "+customerId+" AND ISACTIVE = 'Y'";

		int ui=getSession().createSQLQuery(SQl).executeUpdate();
		
	}
	
	public List<String> getCustomerEmailDetails(BigDecimal customerId)
	{
		List<String> custList= new ArrayList<String>();
		String queryString = "SELECT EMAIL,EMAIL_VERIFIED_ON from FS_CUSTOMER WHERE CUSTOMER_ID =?  AND ISACTIVE = 'Y'";
		Query query = getSession().createSQLQuery(queryString);
		
		query.setBigDecimal(0, customerId);
		
		List<Object[]> lstEmail =query.list();
		
		if(lstEmail!=null && lstEmail.size()>0)
		{
			Object[] obj=lstEmail.get(0);
			if(obj[0]!=null)
			{
				String email=obj[0].toString();
				custList.add(email);
			}
			
			if(obj[1]!=null)
			{
				String veryFiedOn=obj[1].toString();
				custList.add(veryFiedOn);
			}
			
			
			
		}
		return custList;
	}
	
}

