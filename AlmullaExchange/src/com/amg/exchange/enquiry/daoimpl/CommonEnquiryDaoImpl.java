package com.amg.exchange.enquiry.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.enquiry.dao.ICommonEnquiryDao;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMasterDesc;

@Repository
public class CommonEnquiryDaoImpl extends CommonDaoImpl implements ICommonEnquiryDao {

	@Override
	public List<Customer> getallCustomer(BigDecimal customerId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Customer.class,"customer");
		
		criteria.setFetchMode("customer.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customer.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customer.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("customer.fsLanguageType", "fsLanguageType",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customer.fsCountryMasterByNationality", FetchMode.JOIN);
		criteria.createAlias("customer.fsCountryMasterByNationality", "fsCountryMasterByNationality",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customer.fsCountryMasterByCountryId", FetchMode.JOIN);
		criteria.createAlias("customer.fsCountryMasterByCountryId", "fsCountryMasterByCountryId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customer.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customer.fsArticleDetails", "fsArticleDetails",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customer.customerId", customerId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(criteria);
	}

	@Override
	public List<CustomerIdProof> getCustomerIdProofDetails(BigDecimal customerId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityFor", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityFor", "fsBizComponentDataByIdentityFor",JoinType.INNER_JOIN);
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerIdProof>) findAll(criteria);
	}

	@Override
	public List<ContactDetail> getContactDetails(BigDecimal customerId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(ContactDetail.class,"contactDetail");
		
		criteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("contactDetail.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("contactDetail.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsDistrictMaster", "fsDistrictMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("contactDetail.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsStateMaster", "fsStateMaster",JoinType.INNER_JOIN);
		
	/*	criteria.setFetchMode("contactDetail.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsCityMaster", "fsCityMaster",JoinType.INNER_JOIN);*/
		
		criteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("contactDetail.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return findAll(criteria);
	}

	@Override
	public List<CustomerEmploymentInfo> getEmployeementDetails( BigDecimal customerId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerEmploymentInfo.class,"customerEmploymentInfo");
		
		criteria.setFetchMode("customerEmploymentInfo.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setFetchMode("customerEmploymentInfo.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", "fsBizComponentDataByEmploymentTypeId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByOccupationId", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByOccupationId", "fsBizComponentDataByOccupationId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsLanguageType", "fsLanguageType",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsDistrictMaster", "fsDistrictMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsStateMaster", "fsStateMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerEmploymentInfo.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCityMaster", "fsCityMaster",JoinType.INNER_JOIN);
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerEmploymentInfo>) findAll(criteria);
	}

	@Override
	public String getArticalName(BigDecimal articalId, BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ArticleMasterDesc.class,"articleMasterDesc");
		
		criteria.setFetchMode("articleMasterDesc.articleMaster", FetchMode.JOIN);
		criteria.createAlias("articleMasterDesc.articleMaster", "articleMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("articleMaster.articleId", articalId));
		
		criteria.setFetchMode("articleMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("articleMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		
		List<ArticleMasterDesc> articalList = (List<ArticleMasterDesc>) findAll(criteria);
		
		if(articalList.size()>0){
			return articalList.get(0).getArticleeDescription();
			}else{
				return null;
			}
	}

	@Override
	public String getlevelName(BigDecimal levelId, BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ArticleDetailsDesc.class,"articleDetailsDesc");
		
		criteria.setFetchMode("articleDetailsDesc.articleDetails", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.articleDetails", "articleDetails",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("articleDetails.articleDetailId", levelId));
		
		criteria.setFetchMode("articleDetailsDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("articleDetailsDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));
		
		List<ArticleDetailsDesc> levelList = (List<ArticleDetailsDesc>) findAll(criteria);
		if(levelList.size()>0){
		return levelList.get(0).getArticleDetailDesc();
		}else{
			return null;
		}
	}

	@Override
	public String getIncomeRangeName(BigDecimal incomeRangeId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(IncomeRangeMaster.class,"incomeRangeMaster");
		
		criteria.setFetchMode("incomeRangeMaster.articleDetail", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.articleDetail", "articleDetail",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("articleDetail.articleDetailId", incomeRangeId));
		
	
		List<IncomeRangeMaster> incomeRangeList = (List<IncomeRangeMaster>) findAll(criteria);
		if(incomeRangeList.size()>0){
			return incomeRangeList.get(0).getMonthlyIncome();
			}else{
				return null;
			}
	}


}
