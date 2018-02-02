package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IAmiecAndBankMappingDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class AmiecAndBankMappingDaoImpl extends CommonDaoImpl implements
		IAmiecAndBankMappingDao {

	@Override
	public void save(AmiecAndBankMapping amiecAndBankMapping) {
		getSession().saveOrUpdate(amiecAndBankMapping);
	}

	@Override
	public List<AmiecAndBankMapping> getAllAdditionalBankDataList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}
	
	@Override
	public List<AmiecAndBankMapping> getFlexFieldByCountry(BigDecimal countryId, BigDecimal bankId, String flexField) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		detachedCriteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.countryId",countryId));
		
		detachedCriteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.bankId",bankId));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.flexField",flexField));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}
	
	@Override
	public List<AmiecAndBankMapping> getAmiecCodeByCountry(BigDecimal countryId, BigDecimal bankId, String amiecCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		detachedCriteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.countryId",countryId));
		
		detachedCriteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.bankId",bankId));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.amiecCode",amiecCode));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}
	
	@Override
	public List<AmiecAndBankMapping> getBankCodeByCountry(BigDecimal countryId, BigDecimal bankId, String bankCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.countryId",countryId));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.bankId",bankId));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.amiecCode",bankCode));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}

	@Override
	public List<AmiecAndBankMapping> getBankAmiecAndBankMapping() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}

	@Override
	public List<AdditionalBankRuleAmiec> getAmielist(BigDecimal countryId,
			String flexId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		detachedCriteria.setFetchMode("additionalBankRuleAmiec.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAmiec.countryId", "countryId",JoinType.INNER_JOIN);		
		detachedCriteria.add(Restrictions.eq("countryId.countryId",countryId));
		
			
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexId));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AdditionalBankRuleAmiec>)findAll(detachedCriteria);
	}

	@Override
	public List<AdditionalBankRuleAddData> getListBankCode(BigDecimal countryId,BigDecimal bankId,String flexId) {
		
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		detachedCriteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);		
		detachedCriteria.add(Restrictions.eq("countryId.countryId",countryId));
		
			
		detachedCriteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);		
		detachedCriteria.add(Restrictions.eq("bankId.bankId",bankId));
		
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexId));
		
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<AdditionalBankRuleAddData>)findAll(detachedCriteria);
		
	}

	@Override
	public List<AdditionalBankRuleAmiec> getListDesc(String amieCode) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		
		
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAmiec.amiecCode",amieCode));
		// TODO Auto-generated method stub
		return (List<AdditionalBankRuleAmiec>)findAll(detachedCriteria);
	}

	@Override
	public List<AmiecAndBankMapping> getDataForApproval() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		
		detachedCriteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);
		
		detachedCriteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.isActive",Constants.U));
	//	detachedCriteria.add(Restrictions.isNotNull("amiecAndBankMapping.approvedDate"));
		//detachedCriteria.add(Restrictions.isNotNull("amiecAndBankMapping.approvedBy"));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}
	
	@Override
	public String getFlexFieldName(String fieldValue) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class,"AdditionalBankRuleMap");
		detachedCriteria.add(Restrictions.eq("AdditionalBankRuleMap.flexField",fieldValue));
		List<AdditionalBankRuleMap> addBankList=(List<AdditionalBankRuleMap>)findAll(detachedCriteria);
		if(addBankList.size()>0){
		return addBankList.get(0).getFieldName();
		}else{
			return null;
		}
		
	}

	@Override
	public String approveRecord(BigDecimal amiecPk,String userName) {
		String approveMsg;
		AmiecAndBankMapping amiecBankMap=(AmiecAndBankMapping) getSession().get(AmiecAndBankMapping.class, amiecPk);
		String approvedUser=amiecBankMap.getApprovedBy();

		if(approvedUser==null)
		{
			amiecBankMap.setIsActive(Constants.Yes);
			amiecBankMap.setApprovedBy(userName);
			amiecBankMap.setApprovedDate(new Date());
			amiecBankMap.setRemarks("");
			getSession().update(amiecBankMap);
			approveMsg="Sucess";
		}else{
			approveMsg="Fail";
		}
		return approveMsg;
	}
	

	
	
		@Override
	public List<BankMaster> listofBanks(BigDecimal coutryId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(BankMaster.class,"bankmaster");
		criteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster");
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", coutryId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<BankMaster>)findAll(criteria);
	}

	

	@Override
	public String filedName(String  fieldsId,BigDecimal countryId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalbankrule");
		
		criteria.add(Restrictions.eq("additionalbankrule.flexField",fieldsId));
		
		criteria.setFetchMode("additionalbankrule.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalbankrule.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
	
		return ((AdditionalBankRuleMap)criteria.getExecutableCriteria(getSession()).list().get(0)).getFieldName();
	}

	
	//Added by kani for Eniquiry screen Begin
	

	@Override
	public List<AmiecAndBankMapping> getBankAmiecAndBankMappingEnquiry() {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		
	    
		//detachedCriteria.add(Restrictions.or(Restrictions.eq("amiecAndBankMapping.isActive","Y"), Restrictions.eq("amiecAndBankMapping.isActive","D")));
		
	    detachedCriteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);		
		//detachedCriteria.add(Restrictions.eq("countryId.countryId",countryId));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		
		//conjunction.add((Criterion) detachedCriteria);
		
		//detachedCriteria.add((Criterion) detachedCriteria);
		
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
	}
	
	
	
	//Added by kanifor Enquiry Screen End
	
	
	@Override
	public void activateRecord(BigDecimal bankAmiecPk,String userName){
		
		AmiecAndBankMapping amiecAndBankMapping=(AmiecAndBankMapping) getSession().get(AmiecAndBankMapping.class, bankAmiecPk);
		amiecAndBankMapping.setIsActive(Constants.U);
		amiecAndBankMapping.setModifiedBy(userName);
		amiecAndBankMapping.setModifiedDate(new Date());
		amiecAndBankMapping.setApprovedBy(null);
		amiecAndBankMapping.setApprovedDate(null);
		amiecAndBankMapping.setRemarks(null);
		getSession().update(amiecAndBankMapping);
		
	}
	
	
	@Override
	public void deleteRecord(BigDecimal bankAmiecPk){
		AmiecAndBankMapping amiecAndBankMapping=(AmiecAndBankMapping) getSession().get(AmiecAndBankMapping.class, bankAmiecPk);
		getSession().delete(amiecAndBankMapping);		
	}
	
	
	@Override
	public void remarkRecord(BigDecimal bankAmiecPk,String remarkedText,String userName){
		
		AmiecAndBankMapping amiecAndBankMapping=(AmiecAndBankMapping) getSession().get(AmiecAndBankMapping.class, bankAmiecPk);
		amiecAndBankMapping.setModifiedBy(userName);
		amiecAndBankMapping.setModifiedDate(new Date());
		amiecAndBankMapping.setIsActive(Constants.D);
		amiecAndBankMapping.setRemarks(remarkedText);
		amiecAndBankMapping.setApprovedBy(null);
		amiecAndBankMapping.setApprovedDate(null);
		getSession().update(amiecAndBankMapping);
		
	}
	
	
	@Override
	public List<AmiecAndBankMapping> dupliacteRecordCheckInDB(BigDecimal countryId,BigDecimal bankId,String flexField,String amiecCode,String bankCode){
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");
		
	    detachedCriteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		detachedCriteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		detachedCriteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("bankId.bankId",bankId));
		
		
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.flexField",flexField));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.amiecCode",amiecCode));
		detachedCriteria.add(Restrictions.eq("amiecAndBankMapping.bankCode",bankCode));
		
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AmiecAndBankMapping>)findAll(detachedCriteria);
		
	}
	
	

}
