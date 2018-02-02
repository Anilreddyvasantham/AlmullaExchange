package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.dao.IAdditionalBankRuleMapDao;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class AdditionalBankRuleMapDaoImpl extends CommonDaoImpl implements
IAdditionalBankRuleMapDao {

	@Override
	public void save(AdditionalBankRuleMap additionalBankRuleMap)throws Exception {
		getSession().saveOrUpdate(additionalBankRuleMap);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<AdditionalBankRuleMap> getAllFlexField() {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleHeader");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<AdditionalBankRuleMap>)findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleMap> getAdditionalFlexField(BigDecimal countryId){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");

		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));

		criteria.add(Restrictions.eq("additionalBankRuleMap.isActive",Constants.Yes));
		criteria.addOrder(Order.desc("additionalBankRuleMap.fieldName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleMap>) findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForView(){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);

		//	criteria.add(Restrictions.ne("additionalBankRuleMap.isActive","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleMap>) findAll(criteria);

	}



	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForView(BigDecimal countryId,String flexfield){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		criteria.setFetchMode("additionalBankRuleAmiec.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAmiec.additionalBankFieldId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.additionalBankFieldId", "additionalBankFieldId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexfield));
		//criteria.add(Restrictions.ne("additionalBankRuleAmiec.isActive","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAmiec>) findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForView(BigDecimal countryId,String flexifieild,BigDecimal bankId){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAddData.additionalBankFieldId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.additionalBankFieldId", "additionalBankFieldId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexifieild));

		//	criteria.add(Restrictions.ne("additionalBankRuleAddData.isActive",Constants.U));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);

	}


	// Added by kani for Enquiry Screens Begin

	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForEnquiry(){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleMap>) findAll(criteria);

	}



	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForEnquiry(){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		criteria.setFetchMode("additionalBankRuleAmiec.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAmiec.additionalBankFieldId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.additionalBankFieldId", "additionalBankFieldId",JoinType.INNER_JOIN);


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAmiec>) findAll(criteria);

	}



	@SuppressWarnings("unchecked")
	@Override
	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForEnquiry(){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAddData.additionalBankFieldId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.additionalBankFieldId", "additionalBankFieldId",JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);

	}





	//Added  bykani for Enquiry screens End



	@Override
	public void activateBankRuleMapRecord(BigDecimal bankRuleMapPk,String userName) {

		AdditionalBankRuleMap additionalBankRuleMap=(AdditionalBankRuleMap) getSession().get(AdditionalBankRuleMap.class, bankRuleMapPk);
		additionalBankRuleMap.setIsActive(Constants.U);
		additionalBankRuleMap.setModifiedBy(userName);
		additionalBankRuleMap.setModifiedDate(new Date());
		additionalBankRuleMap.setApprovedBy(null);
		additionalBankRuleMap.setApprovedDate(null);
		additionalBankRuleMap.setRemarks(null);
		getSession().update(additionalBankRuleMap);

	}


	@Override
	public void deleteBankRuleMapRecord(BigDecimal bankRuleMapPk) {
		AdditionalBankRuleMap additionalBankRuleMap=(AdditionalBankRuleMap) getSession().get(AdditionalBankRuleMap.class, bankRuleMapPk);
		getSession().delete(additionalBankRuleMap);		
	}


	@Override
	public void remarkBankRuleMapRecord(BigDecimal bankRuleMapPk,String remarkedText,String userName){

		AdditionalBankRuleMap additionalBankRuleMap=(AdditionalBankRuleMap) getSession().get(AdditionalBankRuleMap.class, bankRuleMapPk);
		additionalBankRuleMap.setModifiedBy(userName);
		additionalBankRuleMap.setModifiedDate(new Date());
		additionalBankRuleMap.setIsActive(Constants.D);
		additionalBankRuleMap.setRemarks(remarkedText);
		additionalBankRuleMap.setApprovedBy(null);
		additionalBankRuleMap.setApprovedDate(null);
		getSession().update(additionalBankRuleMap);

	}





	@Override
	public void activateBankRuleAlmullaCode(BigDecimal bankRuleAlmullaCodePk,String userName){

		AdditionalBankRuleAmiec additionalBankRuleAmiec=(AdditionalBankRuleAmiec) getSession().get(AdditionalBankRuleAmiec.class, bankRuleAlmullaCodePk);
		additionalBankRuleAmiec.setIsActive(Constants.U);
		additionalBankRuleAmiec.setModifiedBy(userName);
		additionalBankRuleAmiec.setModifiedDate(new Date());
		additionalBankRuleAmiec.setApprovedBy(null);
		additionalBankRuleAmiec.setApprovedDate(null);
		additionalBankRuleAmiec.setRemarks(null);
		getSession().update(additionalBankRuleAmiec);

	}


	@Override
	public void deleteBankRuleAlmullaCode(BigDecimal bankRuleAlmullaCodePk){
		AdditionalBankRuleAmiec additionalBankRuleAmiec=(AdditionalBankRuleAmiec) getSession().get(AdditionalBankRuleAmiec.class, bankRuleAlmullaCodePk);
		getSession().delete(additionalBankRuleAmiec);		
	}


	@Override
	public void remarkBankRuleAlmullaCode(BigDecimal bankRuleAlmullaCodePk,String remarkedText,String userName){

		AdditionalBankRuleAmiec additionalBankRuleAmiec=(AdditionalBankRuleAmiec) getSession().get(AdditionalBankRuleAmiec.class, bankRuleAlmullaCodePk);
		additionalBankRuleAmiec.setModifiedBy(userName);
		additionalBankRuleAmiec.setModifiedDate(new Date());
		additionalBankRuleAmiec.setIsActive(Constants.D);
		additionalBankRuleAmiec.setRemarks(remarkedText);
		additionalBankRuleAmiec.setApprovedBy(null);
		additionalBankRuleAmiec.setApprovedDate(null);
		getSession().update(additionalBankRuleAmiec);

	}



	@Override
	public void activateBankRule(BigDecimal bankRuleAlmullaCodePk,String userName){

		AdditionalBankRuleAddData additionalBankRuleAddData=(AdditionalBankRuleAddData) getSession().get(AdditionalBankRuleAddData.class, bankRuleAlmullaCodePk);
		additionalBankRuleAddData.setIsActive(Constants.U);
		additionalBankRuleAddData.setModifiedBy(userName);
		additionalBankRuleAddData.setModifiedDate(new Date());
		additionalBankRuleAddData.setApprovedBy(null);
		additionalBankRuleAddData.setApprovedDate(null);
		additionalBankRuleAddData.setRemarks(null);
		getSession().update(additionalBankRuleAddData);

	}


	@Override
	public void deleteBankRule(BigDecimal bankRuleAlmullaCodePk){
		AdditionalBankRuleAddData additionalBankRuleAddData=(AdditionalBankRuleAddData) getSession().get(AdditionalBankRuleAddData.class, bankRuleAlmullaCodePk);
		getSession().delete(additionalBankRuleAddData);		
	}


	@Override
	public void remarkBankRule(BigDecimal bankRuleAlmullaCodePk,String remarkedText,String userName){

		AdditionalBankRuleAddData additionalBankRuleAddData=(AdditionalBankRuleAddData) getSession().get(AdditionalBankRuleAddData.class, bankRuleAlmullaCodePk);
		additionalBankRuleAddData.setModifiedBy(userName);
		additionalBankRuleAddData.setModifiedDate(new Date());
		additionalBankRuleAddData.setIsActive(Constants.D);
		additionalBankRuleAddData.setRemarks(remarkedText);
		additionalBankRuleAddData.setApprovedBy(null);
		additionalBankRuleAddData.setApprovedDate(null);
		getSession().update(additionalBankRuleAddData);

	}


	@Override
	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldName,BigDecimal orderNo){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		if(bFlexFieldID!=null){
			criteria.add(Restrictions.eq("additionalBankRuleMap.flexField",bFlexFieldID));
		}
		
		criteria.add(Restrictions.eq("additionalBankRuleMap.fieldName",flexFieldName));
		criteria.add(Restrictions.eq("additionalBankRuleMap.orderNo",orderNo));

		return findAll(criteria);
	}
	
	@Override
	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldID,String flexFieldName,BigDecimal orderNo){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		if(bFlexFieldID!=null){
			criteria.add(Restrictions.eq("additionalBankRuleMap.flexField",bFlexFieldID));
		}
		if(flexFieldID!=null){
			criteria.add(Restrictions.eq("additionalBankRuleMap.flexField",flexFieldID));
		}
		
		criteria.add(Restrictions.eq("additionalBankRuleMap.fieldName",flexFieldName));
		criteria.add(Restrictions.eq("additionalBankRuleMap.orderNo",orderNo));

		return findAll(criteria);
	}

	@Override
	public List<AdditionalBankRuleAmiec> duplicateCheckInDBBankAlmullaCode(BigDecimal countryId,String flexFieldID,String amiecCode,String amiecDescription){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAmiec.class,"additionalBankRuleAmiec");
		criteria.setFetchMode("additionalBankRuleAmiec.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAmiec.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.flexField",flexFieldID));
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.amiecCode",amiecCode));
		criteria.add(Restrictions.eq("additionalBankRuleAmiec.amiecDescription",amiecDescription));

		return findAll(criteria);
	}


	@Override
	public List<AdditionalBankRuleAddData> duplicateCheckInDBBankRule(BigDecimal countryId,String flexFieldID,BigDecimal bankId,String additionalData,String additionalDataDesc){
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId",bankId));

		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexFieldID));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.additionalData",additionalData));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.additionalDescription",additionalDataDesc));
		return findAll(criteria);
	}

	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleMap.class,"additionalBankRuleMap");

		criteria.setFetchMode("additionalBankRuleMap.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<AdditionalBankRuleMap> lstAdditionalCountry= (List<AdditionalBankRuleMap>)findAll(criteria);
		List<CountryMasterDesc> lstCountryMasterDesc= new ArrayList<CountryMasterDesc>();

		List<BigDecimal> duplicatCheck= new ArrayList<BigDecimal>();

		for(AdditionalBankRuleMap listCountry :lstAdditionalCountry)
		{

			BigDecimal countryId=listCountry.getCountryId().getCountryId();
			if(!duplicatCheck.contains(countryId))
			{
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");

				detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
				detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
				detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

				detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
				detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
				detachedCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));

				detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

				detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

				List<CountryMasterDesc> lstCountryMasterDesc1=(List<CountryMasterDesc>) findAll(detachedCriteria);
				lstCountryMasterDesc.addAll(lstCountryMasterDesc1);
				duplicatCheck.add(countryId);
			}

		}


		return lstCountryMasterDesc;
	}

	@Override
	public List<BankMasterDTO> getBanlList(BigDecimal countryId,String flexField) {

		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");

		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("additionalBankRuleAddData.isActive",Constants.Yes));
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("flexField",flexField));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("bankId.bankId")),"bankId");
		//columns.add(Projections.property("additionalBankRuleAddData.additionalData"),"bankCode");
		//columns.add(Projections.property("bankId.bankId"));
		//columns.add(Projections.property("currencyName"), "currencyDecs");
		//columns.add(Projections.property("bankId.bankId"));
		//criteria.setProjection(Projections.distinct(columns));
		//criteria.setProjection(columns);
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(BankMasterDTO.class) );

		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankMasterDTO>   addList = (List<BankMasterDTO>) findAll(criteria);
		return addList;
	}

	@Override
	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal countryId, String flexField) {

		DetachedCriteria critiria = DetachedCriteria.forClass(AdditionalBankDetailsView.class,"additionalBankDetailsView");

		critiria.add(Restrictions.eq("additionalBankDetailsView.countryId", countryId));
		critiria.add(Restrictions.eq("additionalBankDetailsView.flexField", flexField));

		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankDetailsView> additionalList = (List<AdditionalBankDetailsView>) findAll(critiria);
		return additionalList;
	}

	@Override
	public List<AdditionalBankRuleAddData> getBankDetails(BigDecimal bankId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("additionalBankRuleAddData.isActive",Constants.Yes));
		criteria.add(Restrictions.eq("bankId.bankId",bankId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@Override
	public String getBankCode(String bankDesc) {

		String hqlQuery="select additionalData from  AdditionalBankRuleAddData   where additionalDescription =  :bankDesc";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("bankDesc", bankDesc);

		List<String> lstIdentity =query.list();

		String bankCode=null;
		if(lstIdentity.size()>0)
		{
			bankCode=lstIdentity.get(0);
		}
		return bankCode;
	}

	@Override
	public List<AdditionalBankRuleAddData> getAllBankDetails() {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		criteria.add(Restrictions.eq("additionalBankRuleAddData.isActive",Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@Override
	public List<AmiecAndBankMapping> fetchDataFromAmiecTable(BigDecimal countryId, String flexField, String amiecCode,BigDecimal bankId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");

		criteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		criteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.add(Restrictions.eq("amiecAndBankMapping.flexField",flexField));

		criteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		criteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
		//criteria.add(Restrictions.eq("amiecAndBankMapping.amiecCode",amiecCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AmiecAndBankMapping>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AmiecAndBankMapping>   checkRecordAvailableInAmiecAndBankMappingTable(
			BigDecimal countryId, String flexiField, String amiecCode, String amiecDesc,BigDecimal bankId){
		DetachedCriteria criteria=DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");

		criteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		criteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));

		criteria.add(Restrictions.eq("amiecAndBankMapping.flexField",flexiField));

		criteria.setFetchMode("amiecAndBankMapping.bankId",FetchMode.JOIN);
		criteria.createAlias("amiecAndBankMapping.bankId", "bankId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId",bankId));

		criteria.add(Restrictions.eq("amiecAndBankMapping.amiecCode",amiecCode));
		// not need to check with desc becoz it can be updated
		//criteria.add(Restrictions.eq("amiecAndBankMapping.amiecDescription",amiecDesc));
		criteria.add(Restrictions.eq("amiecAndBankMapping.isActive",Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<AmiecAndBankMapping> lstAmiecAndBankMap = (List<AmiecAndBankMapping>)findAll(criteria);
		
		return lstAmiecAndBankMap;
	}

	@Override
	public void updateRecord(List<AmiecAndBankMapping> amiecAndBankMappingUpdateList,String userName){
		for(AmiecAndBankMapping amiecObj:amiecAndBankMappingUpdateList){
			AmiecAndBankMapping amiecAndBankMapping=(AmiecAndBankMapping) getSession().get(AmiecAndBankMapping.class, amiecObj.getAmiecAndBankMappingId());

			amiecAndBankMapping.setBankCode(amiecObj.getBankCode());
			amiecAndBankMapping.setBankDecription(amiecObj.getBankDecription());
			amiecAndBankMapping.setAmiecCode(amiecObj.getAmiecCode());
			amiecAndBankMapping.setAmiecDescription(amiecObj.getAmiecDescription());
			amiecAndBankMapping.setModifiedBy(userName);
			amiecAndBankMapping.setModifiedDate(new Date());

			getSession().update(amiecAndBankMapping);
		}

	}

	@Override
	public void saveRecord(List<AmiecAndBankMapping> amiecAndBankMappingList){
		for(AmiecAndBankMapping amiecObj:amiecAndBankMappingList){
			getSession().saveOrUpdate(amiecObj);
		}

	}


	@Override
	public List<AdditionalBankRuleMap> toFetchAdditionBankRuleMapRecordsForView(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class, "additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankRuleMap> lstAdditionalBankRuleMaps=(List<AdditionalBankRuleMap>)findAll(criteria);
		if(lstAdditionalBankRuleMaps != null && lstAdditionalBankRuleMaps.size() !=0){
			return lstAdditionalBankRuleMaps; 
		}else{
			return null;
		}
	}

	@Override
	public List<AdditionalBankRuleMap> tofetchAll(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleMap.class, "additionalBankRuleMap");
		criteria.setFetchMode("additionalBankRuleMap.countryId", FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleMap.countryId", "countryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankRuleMap> lstAdditionalBankRuleMaps = (List<AdditionalBankRuleMap>) findAll(criteria);
		return lstAdditionalBankRuleMaps;
	}

	@Override
	public List<AdditionalBankRuleFlexFieldView> tofetchAllFlexFieldsFromView() {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalBankRuleFlexFieldView.class, "additionalBankRuleFlexFieldView");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AdditionalBankRuleFlexFieldView> lstAdditionalBankRuleView = (List<AdditionalBankRuleFlexFieldView>) findAll(criteria);
		return lstAdditionalBankRuleView;
	}

	@Override
	public List<AdditionalBankRuleAddData> getBankDetailsAndDescription(BigDecimal countryId, BigDecimal bankId, String flexiField) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		
		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));

		criteria.setFetchMode("additionalBankRuleAddData.bankId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.bankId", "bankId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId",bankId));
		
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexiField));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.isActive",Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

	@Override
	public List<AmiecAndBankMapping> fetchAllDataFromAmiecTableByCountryFlexField(BigDecimal countryId, String flexField) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AmiecAndBankMapping.class,"amiecAndBankMapping");

		criteria.setFetchMode("amiecAndBankMapping.countryId",FetchMode.JOIN);
		criteria.createAlias("amiecAndBankMapping.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));

		criteria.add(Restrictions.eq("amiecAndBankMapping.flexField",flexField));
		
		criteria.add(Restrictions.eq("amiecAndBankMapping.isActive",Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<AmiecAndBankMapping> lstAmiecAndBankMap = (List<AmiecAndBankMapping>)findAll(criteria);
		
		return lstAmiecAndBankMap;
	}

	@Override
	public List<AdditionalBankRuleAddData> fetchAllDataFromAdditionalBankRule(BigDecimal countryId, String flexiField) {
		DetachedCriteria criteria=DetachedCriteria.forClass(AdditionalBankRuleAddData.class,"additionalBankRuleAddData");
		
		criteria.setFetchMode("additionalBankRuleAddData.countryId",FetchMode.JOIN);
		criteria.createAlias("additionalBankRuleAddData.countryId", "countryId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryId.countryId",countryId));
		
		criteria.add(Restrictions.eq("additionalBankRuleAddData.flexField",flexiField));
		criteria.add(Restrictions.eq("additionalBankRuleAddData.isActive",Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalBankRuleAddData>) findAll(criteria);
	}

}
