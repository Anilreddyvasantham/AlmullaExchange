package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IAdditionalBankRuleAmiecDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class AdditionalBankRuleAmiecDaoImpl extends CommonDaoImpl implements
		IAdditionalBankRuleAmiecDao {

	@Override
	public void save(AdditionalBankRuleAmiec additionalBankRuleAmiec) throws Exception{
		getSession().saveOrUpdate(additionalBankRuleAmiec);
	}

	@Override
	public BigDecimal getMasterPk(String flexfield) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		criteria.add(Restrictions.eq("additionalBankRuleMap.flexField", flexfield));
		List<AdditionalBankRuleMap> list=findAll(criteria);
		 
		return list.get(0).getAdditionalBankRuleId();
	}

	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAmiec> getamiecCodeList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class, "additionalamic");
		//detachedCriteria.add(Restrictions.eq("relations.relationsCode", 				relationsCode));
		return (List<AdditionalBankRuleAmiec>) findAll(detachedCriteria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllComponent(String query,BigDecimal countryId,String flexfield) {

		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		
		criteria.setFetchMode("additionalBankRuleAmiec.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId");
		
		if(countryId !=null){
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		}
		
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexfield));
		
		criteria.add(Restrictions.like("additionalBankRuleAmiec.amiecCode", query,MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("additionalBankRuleAmiec.amiecCode"));
		criteria.setProjection(Projections.property("additionalBankRuleAmiec.amiecCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 
		
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<String>) findAll(criteria);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getComponentadditionalData(String query,BigDecimal countryId,String flexField,BigDecimal bankId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		
		criteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId");	
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexField));
		
		criteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId");	
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
	
		criteria.add(Restrictions.like("additionalBankRuleAddData.additionalData", query,MatchMode.ANYWHERE).ignoreCase());
		criteria.addOrder(Order.asc("additionalData"));
		criteria.setProjection(Projections.property("additionalBankRuleAddData.additionalData"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<String>) findAll(criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAmiec> populateAmiecDescription(BigDecimal countryId,String flexField,String amiecCode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		
		criteria.setFetchMode("additionalBankRuleAmiec.countryId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexField));
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.amiecCode",amiecCode));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		return (List<AdditionalBankRuleAmiec>) findAll(criteria);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAddData> populateAdditionalDescription(BigDecimal countryId,BigDecimal bankId,String flexField,String additionalData) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		
		criteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId");		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);	
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId");		
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
				
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexField));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.additionalData",additionalData));
		
		
		
		
		
		/*DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("fsCountryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("fsCountryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId", appliationCountry)));
		dCriteria.addOrder(Order.asc("fsCountryMasterDescs.countryName"));
		*/
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleMap> getLstofFlexFields(BigDecimal countryId) {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		
		criteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId");		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.add(Restrictions.eq("additionalBankRuleMap.isActive",Constants.Yes));
		
		criteria.addOrder(Order.desc("additionalBankRuleMap.fieldName"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<AdditionalBankRuleMap>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleMap> getAllRecordbyCountryFlex(BigDecimal countryId, String flexfield) {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		
		criteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId");		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
				
		criteria.add(Restrictions.eq("additionalBankRuleMap.flexField",flexfield));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<AdditionalBankRuleMap>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAmiec> getAllDetailsbyCountryFlex(BigDecimal countryId, String flexfield) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		
		criteria.setFetchMode("additionalBankRuleAmiec.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId");		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
				
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexfield));
		
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.isActive",Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<AdditionalBankRuleAmiec>) findAll(criteria);
	}

	/*@Override
	public List<AdditionalBankRuleAmiec> deleteIsActiveN(AdditionalBankRuleAmiec additionalBankRuleAmiec) {
		// TODO Auto-generated method stub
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.additionalBankRuleDetailId",addBankRulepk));
		
		
		
		return null;
	}
	*/
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAddData> populateBankRuleData(BigDecimal countryId,String flexfield,BigDecimal bankId,String additionalData){
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		
		criteria.setFetchMode("additionalBankRuleAddData.countryId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);	
		
		criteria.setFetchMode("additionalBankRuleAddData.bankId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("additionalBankRuleAddData.additionalBankFieldId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.additionalBankFieldId", "additionalBankFieldId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexfield));
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.additionalData",additionalData));
		
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@Override
	public BigDecimal getAdditionalBankRuleMapMasterPk(BigDecimal countryId,String flexfield) {
		
		BigDecimal additionalBankRuleMapPk = null;
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		
		criteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);		
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId");		
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.add(Restrictions.eq("additionalBankRuleMap.flexField", flexfield));
		
		List<AdditionalBankRuleMap> list = findAll(criteria);
		
		if(list.size() != 0){
			AdditionalBankRuleMap lstrecord = list.get(0);
			additionalBankRuleMapPk = lstrecord.getAdditionalBankRuleId();
		}
		 
		return additionalBankRuleMapPk;
	}
	
}
