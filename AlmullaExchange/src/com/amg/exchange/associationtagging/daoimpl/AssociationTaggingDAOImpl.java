package com.amg.exchange.associationtagging.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.associationtagging.dao.AssociationTaggingDAO;
import com.amg.exchange.associationtagging.model.AssociationTaggingVW;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings("unchecked")
public class AssociationTaggingDAOImpl<T> extends CommonDaoImpl<T> implements AssociationTaggingDAO {		
	
	@Override
	public List<AssociationTaggingVW> getCustomerList(BigDecimal cusRefNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssociationTaggingVW.class, "associationTaggingVW");
		
		criteria.add(Restrictions.eq("associationTaggingVW.cusRefNum", cusRefNum));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AssociationTaggingVW>) findAll(criteria);			
	}
	
	@Override
	public List<AssociationTaggingVW> getCustomerCivilIdList(String civilId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssociationTaggingVW.class, "associationTaggingVW");
		
		criteria.add(Restrictions.eq("associationTaggingVW.civilId", civilId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AssociationTaggingVW>) findAll(criteria);			
	}	
	
	@Override
	public void updateAssCode(BigDecimal assCode, BigDecimal cusIdPK) {
		Customer customer = (Customer) getSession()
				.get(Customer.class, cusIdPK);
		
		customer.setAssocod(assCode);
		getSession().saveOrUpdate(customer);
	}
	
	@Override
	public String getNationality(BigDecimal coutryId , BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
		
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", coutryId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getNationality();
	}	
	
	@Override
	public String countryName(BigDecimal countryId , BigDecimal langId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}
	
	@Override
	public String stateName(BigDecimal stateId,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		
		criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("stateMasterDesc.stateDescId", stateId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((StateMasterDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getStateName();
	}
	
	@Override
	public String districtName(BigDecimal districtId,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		
		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("districtMasterDesc.districtDescId", districtId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((DistrictMasterDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getDistrict();
	}
	
	@Override
	public String cityName(BigDecimal cityId,BigDecimal langId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");
		
		criteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("cityMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("cityMasterDesc.cityMasterId", cityId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((CityMasterDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getCityName();
	}	
	
	@Override
	public List<CustomerEmploymentInfo> getEmployeeList(BigDecimal cusId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmploymentInfo");
		
		criteria.setFetchMode("customerEmploymentInfo.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCustomer","fsCustomer", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId","fsBizComponentDataByEmploymentTypeId", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByOccupationId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByOccupationId","fsBizComponentDataByOccupationId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", cusId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<CustomerEmploymentInfo>) findAll(criteria);		
	}
	
	@Override
	public List<PopulateData> getAssociationTagList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustCorporateAddlDetail.class, "custCorporateAddlDetail");
		
		criteria.setFetchMode("custCorporateAddlDetail.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("custCorporateAddlDetail.fsCustomer","fsCustomer", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("custCorporateAddlDetail.fsBizComponentDataByObjectiveId", FetchMode.JOIN);
		criteria.createAlias("custCorporateAddlDetail.fsBizComponentDataByObjectiveId","fsBizComponentDataByObjectiveId", JoinType.INNER_JOIN);
		//For ASSOCIATION TAG
		criteria.add(Restrictions.eq("fsBizComponentDataByObjectiveId.componentDataId", Constants.ASSO_ID));
		
		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.distinct(Projections.property("fsCustomer.customerId")), "populateId");
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);		
		return lstPopulateData;
	}
	
	@Override
	public List<Customer> getCompanyNames(BigDecimal cusId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");
		
		criteria.add(Restrictions.eq("customer.customerId", cusId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(criteria);		
	}
	
	@Override
	public String getComponentValues(BigDecimal componentDataId , BigDecimal langId) {
		
		String componentValue = null;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
		
		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData","fsBizComponentData", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsBizComponentData.componentDataId", componentDataId));
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<BizComponentDataDesc> lstBiz = (List<BizComponentDataDesc>) findAll(criteria);
		if(lstBiz != null && lstBiz.size() != 0){
			BizComponentDataDesc bizComponent = lstBiz.get(0);
			componentValue = bizComponent.getDataDesc();
		}
		
		return componentValue;
		
	}

	@Override
	public List<AssociationTaggingVW> getCustomerListByMobile(String mobileNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AssociationTaggingVW.class, "associationTaggingVW");
		
		criteria.add(Restrictions.eq("associationTaggingVW.mobile", mobileNum));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AssociationTaggingVW>) findAll(criteria);			
	}

}
