package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IAdditionalBankRuleAddDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.FlexFiledView;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class AdditionalBankRuleAddDaoImpl extends CommonDaoImpl implements IAdditionalBankRuleAddDao {
	
	
	
	private static Logger LOGGER = Logger.getLogger(AdditionalBankRuleAddDaoImpl.class);
	
	@Override
	public void save(AdditionalBankRuleAddData additionalBankRuleAddData) {
		getSession().saveOrUpdate(additionalBankRuleAddData);
	}

	@Override
	public List<AdditionalBankRuleAddData> getDBCountryFlexBank(BigDecimal countryId, String FlexId, BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRuleAddData");
		detachedCriteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.countryId", "countryId");
		detachedCriteria.add(Restrictions.eq("countryId.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.flexField", FlexId));
		detachedCriteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.bankId", "bankId");
		detachedCriteria.add(Restrictions.eq("bankId.bankId", bankId));
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.isActive", Constants.Yes));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(detachedCriteria);
	}

	public List<AdditionalBankRuleAddData> getAdditionalBankList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRuleAddData");
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(detachedCriteria);
	}

	@Override
	public List<AdditionalBankRuleAddData> getBankDescription(String bankCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRuleAddData");
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.additionalData", bankCode));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(detachedCriteria);
	}

	@Override
	public List<AdditionalBankRuleAddData> getDataForApproval() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRuleAddData");
		detachedCriteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.countryId", "countryId", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.bankId", "bankId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.isActive", Constants.U));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAddData.approvedDate"));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAddData.approvedBy"));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(detachedCriteria);
	}

	@Override
	public String approveRecord(BigDecimal addtionalBankRulePk, String userName) {
		String approveMsg;
		AdditionalBankRuleAddData addtionalBankRule = (AdditionalBankRuleAddData) getSession().get(AdditionalBankRuleAddData.class, addtionalBankRulePk);
		String approvedUser = addtionalBankRule.getApprovedBy();
		if (approvedUser == null) {
			addtionalBankRule.setIsActive(Constants.Yes);
			addtionalBankRule.setApprovedBy(userName);
			addtionalBankRule.setApprovedDate(new Date());
			addtionalBankRule.setRemarks("");
			getSession().update(addtionalBankRule);
			approveMsg = "Sucess";
		} else {
			approveMsg = "Fail";
		}
		return approveMsg;
	}
	
	@Override
	public String approveRecord(List<BigDecimal> addtionalBankRulePk,String userName) {
		String list = null;
		int i=0;
		for (BigDecimal bigDecimal : addtionalBankRulePk) {
			AdditionalBankRuleAmiec addtionalBankRule = (AdditionalBankRuleAmiec) getSession().get(AdditionalBankRuleAmiec.class, bigDecimal);
			String approvedUser=addtionalBankRule.getApprovedBy();
			if(approvedUser==null)
			{
				addtionalBankRule.setApprovedBy(userName);
				addtionalBankRule.setApprovedDate(new Date());
				addtionalBankRule.setIsActive(Constants.Yes);
				getSession().update(addtionalBankRule);
				i++;
			}
		}
		if(i != 0){
			list="Success";
		}else{
			list="Fail";
		}
		return list;
	}

	@Override
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class, "additionalBankRuleAmiec");
		detachedCriteria.setFetchMode("additionalBankRuleAmiec.countryId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAmiec.countryId", "countryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAmiec.isActive", Constants.U));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAmiec.approvedDate"));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAmiec.approvedBy"));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAmiec>) findAll(detachedCriteria);
	}
	
	@Override
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla(BigDecimal countryId, String FlexField) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class, "additionalBankRuleAmiec");
		if(countryId != null){
			detachedCriteria.setFetchMode("additionalBankRuleAmiec.countryId", FetchMode.JOIN);
			detachedCriteria.createAlias("additionalBankRuleAmiec.countryId", "countryId", JoinType.INNER_JOIN);
			detachedCriteria.add(Restrictions.eq("countryId.countryId", countryId));
		}
		if(FlexField != null){
			detachedCriteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField", FlexField));
		}
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAmiec.isActive", Constants.U));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAmiec.approvedDate"));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleAmiec.approvedBy"));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAmiec>) findAll(detachedCriteria);
	}

	@Override
	public String approveRecordForAlmulla(BigDecimal addtionalBankRulePk, String userName) {
		String approveMsg;
		AdditionalBankRuleAmiec addtionalBankRule = (AdditionalBankRuleAmiec) getSession().get(AdditionalBankRuleAmiec.class, addtionalBankRulePk);
		String approvedUser = addtionalBankRule.getApprovedBy();
		if (approvedUser == null) {
			addtionalBankRule.setIsActive(Constants.Yes);
			addtionalBankRule.setApprovedBy(userName);
			addtionalBankRule.setApprovedDate(new Date());
			addtionalBankRule.setRemarks("");
			getSession().update(addtionalBankRule);
			approveMsg = "Sucess";
		} else {
			approveMsg = "Fail";
		}
		return approveMsg;
	}

	@Override
	public List<AdditionalBankRuleMap> getDataForApprovalForBankRuleMap() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class, "additionalBankRuleMap");
		detachedCriteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleMap.countryId", "countryId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("additionalBankRuleMap.isActive", Constants.U));
		/*
		 * //Criterion isActive
		 * =Restrictions.eq("additionalBankRuleMap.isActive","U"); Criterion
		 * approveBy =
		 * Restrictions.isNotNull("additionalBankRuleMap.approvedBy"); Criterion
		 * approveDate =
		 * Restrictions.isNotNull("additionalBankRuleMap.approvedDate");
		 * 
		 * // To get records matching with AND condistions LogicalExpression
		 * orExp = Restrictions.and(approveBy, approveDate);
		 * detachedCriteria.add( orExp );
		 */
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleMap.approvedDate"));
		// detachedCriteria.add(Restrictions.isNotNull("additionalBankRuleMap.approvedBy"));
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleMap>) findAll(detachedCriteria);
	}

	@Override
	public String approveRecordForBankRuleMap(BigDecimal addtionalBankRuleMapPk, String userName) {
		String approveMsg;
		AdditionalBankRuleMap addtionalBankRule = (AdditionalBankRuleMap) getSession().get(AdditionalBankRuleMap.class, addtionalBankRuleMapPk);
		String approvedUser = addtionalBankRule.getApprovedBy();
		if (approvedUser == null) {
			addtionalBankRule.setIsActive(Constants.Yes);
			addtionalBankRule.setApprovedBy(userName);
			addtionalBankRule.setApprovedDate(new Date());
			addtionalBankRule.setRemarks("");
			getSession().update(addtionalBankRule);
			approveMsg = "Sucess";
		} else {
			approveMsg = "Fail";
		}
		return approveMsg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FlexFiledView> getflexiFieldList() {
		LOGGER.info("Entering into getflexiFieldList method");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(FlexFiledView.class, "flexFiledView");
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getflexiFieldList method");
		return (List<FlexFiledView>) findAll(detachedCriteria);
	}

	@Override
	public List<AdditionalBankRuleAddData> getDataForApprovalList(
			BigDecimal countryId, String flexFiled, BigDecimal bankId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class, "additionalBankRuleAddData");
		detachedCriteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.countryId", "countryId", JoinType.INNER_JOIN);
		if(countryId!=null){
		detachedCriteria.add(Restrictions.eq("countryId.countryId", countryId));
		}
		detachedCriteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.bankId", "bankId", JoinType.INNER_JOIN);
		if(bankId!=null){
		detachedCriteria.add(Restrictions.eq("bankId.bankId", bankId));
		}
		detachedCriteria.setFetchMode("additionalBankRuleAddData.additionalBankFieldId", FetchMode.JOIN);
		detachedCriteria.createAlias("additionalBankRuleAddData.additionalBankFieldId", "additionalBankFieldId", JoinType.INNER_JOIN);
			if(flexFiled!=null){
			detachedCriteria.add(Restrictions.eq("additionalBankFieldId.flexField", flexFiled));
			}
		
		detachedCriteria.add(Restrictions.eq("additionalBankRuleAddData.isActive", Constants.U));
		 
		detachedCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(detachedCriteria);
 
	}

}
