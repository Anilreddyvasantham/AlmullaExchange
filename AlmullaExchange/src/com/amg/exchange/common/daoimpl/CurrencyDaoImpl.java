package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.ICurrencyDao;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.FimsCurmas;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.viewModel.CurrencyCountryLevelView;
import com.amg.exchange.treasury.viewModel.CurrencyGLLevelView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
@SuppressWarnings("rawtypes")
@Repository
public class CurrencyDaoImpl extends CommonDaoImpl implements ICurrencyDao {

	@Override
	public void saveOrUpdate(CurrencyMaster currencyMaster) {
		getSession().saveOrUpdate( currencyMaster);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyOtherInformation> getAllRecords(String  currencyCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");

		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.isNotNull( "fsCountryMaster.countryId"));
		if(currencyCode!=null){
			criteria.add(Restrictions.ilike("exCurrencyMaster.currencyCode", currencyCode, MatchMode.ANYWHERE));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyOtherInformation>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyOtherInformation> getAllUnApprovedRecords() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");
		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exCurrencyMaster.isactive", "U"));
		criteria.add(Restrictions.isNull("exCurrencyMaster.approvedBy"));
		criteria.add(Restrictions.isNull("exCurrencyMaster.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return  (List<CurrencyOtherInformation>) findAll(criteria);
	}

	@Override
	public void deleteRecordFromDB(BigDecimal currencyMasterPk) {
		CurrencyMaster currencyMaster=(CurrencyMaster)getSession().get(CurrencyMaster.class, currencyMasterPk);
		getSession().delete(currencyMaster); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyOtherInformation> getRecordFromDB(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");
		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exCurrencyMaster.currencyCode", currencyCode));

		return (List<CurrencyOtherInformation>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String>  getCurrencyCodeFromDB(String query) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");

		criteria.add(Restrictions.like("currencyMaster.currencyCode", query, MatchMode.ANYWHERE).ignoreCase());

		criteria.setProjection(Projections.property("currencyMaster.currencyCode"));
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<String>) findAll(criteria);

	}
	
	@Override
	public List<CurrencyMaster> getCurrencyList(){
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
	}
	
	@Override
	public String getCountryName(BigDecimal countryId,BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType",FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@Override
	public String approveRecord( BigDecimal currencyMasterPk,String userName,BigDecimal currencyOthInfoPk) {
		String approveMsg;
		CurrencyMaster currencyMaster=(CurrencyMaster) getSession().get(CurrencyMaster.class, currencyMasterPk);
		CurrencyOtherInformation currencyOtherInformation=(CurrencyOtherInformation) getSession().get(CurrencyOtherInformation.class, currencyOthInfoPk);
		String approvedUser=currencyMaster.getApprovedBy();
		if(approvedUser==null)
		{
			currencyMaster.setApprovedBy(userName);
			currencyMaster.setApprovedDate(new Date());
			currencyMaster.setIsactive(Constants.Yes);
			currencyOtherInformation.setIsActive(Constants.Yes);
			getSession().saveOrUpdate( currencyOtherInformation);
			getSession().saveOrUpdate(currencyMaster);
			
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;

	}

	@Override
	public void saveOrUpdate(CurrencyOtherInformation currencyOtherInfo) {
		getSession().saveOrUpdate(currencyOtherInfo);

	}

	@Override
	public void delteRecordFromOtherInfo(BigDecimal currencyOtherInformationPk) {
		CurrencyOtherInformation currencyOtherInformation=(CurrencyOtherInformation)getSession().get(CurrencyOtherInformation.class, currencyOtherInformationPk);
		getSession().delete(currencyOtherInformation);  

	}

	@Override
	public List<CurrencyMaster> getRecordToCheckDuplicate(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");

		criteria.setFetchMode("currencyMaster.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyMaster.currencyCode", currencyCode));

		return (List<CurrencyMaster>) findAll(criteria);
	}

	@Override
	public List<CurrencyOtherInformation> search(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");
		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);


		criteria.add(Restrictions.ilike("exCurrencyMaster.currencyCode", currencyCode, MatchMode.ANYWHERE));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyOtherInformation> lstEmployee = (List<CurrencyOtherInformation>) findAll(criteria);
		return lstEmployee;
	}

	@Override
	public void saveDeactiveRec(BigDecimal currencyMasterPk, String remarks,String userName,BigDecimal currencyOtherInformationPk) {
		CurrencyMaster currencyMaster=(CurrencyMaster) getSession().get(CurrencyMaster.class, currencyMasterPk);
		CurrencyOtherInformation currencyOtherInformation=(CurrencyOtherInformation) getSession().get(CurrencyOtherInformation.class, currencyOtherInformationPk);
		if(remarks!=null){
			currencyMaster.setIsactive( Constants.D);
			currencyMaster.setModifiedBy(userName );
			currencyMaster.setModifiedDate( new Date());
			currencyMaster.setRemarks(remarks );
			currencyOtherInformation.setIsActive( Constants.D);
			getSession().update(currencyOtherInformation);
			getSession().update( currencyMaster);
		}else{
			currencyMaster.setApprovedBy(null);
			currencyMaster.setApprovedDate(null);
			currencyMaster.setIsactive( Constants.U);
			currencyMaster.setModifiedBy(userName );
			currencyMaster.setModifiedDate( new Date());
			currencyOtherInformation.setIsActive( Constants.U);
			getSession().update(currencyOtherInformation);
			getSession().update( currencyMaster);
		}


	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyGLLevelView> fetchAllCurrencyGLRecords(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyGLLevelView.class,"currencyGLLevelView");
		if(currencyCode != null){
			criteria.add(Restrictions.eq("currencyGLLevelView.currencyCode", currencyCode));
		}
		criteria.addOrder(Order.asc("currencyGLLevelView.bankCode"));
		criteria.addOrder(Order.asc("currencyGLLevelView.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyGLLevelView> lstGLRecords = (List<CurrencyGLLevelView>) findAll(criteria);
		
		return lstGLRecords;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyCountryLevelView> fetchAllCurrencyCountryLevelRecords(String currencyCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyCountryLevelView.class,"currencyCountryLevelView");
		if(currencyCode != null){
			criteria.add(Restrictions.eq("currencyCountryLevelView.currencyCode", currencyCode));
		}
		criteria.addOrder(Order.asc("currencyCountryLevelView.currencyCode"));
		criteria.addOrder(Order.asc("currencyCountryLevelView.countryName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyCountryLevelView> lstCountryRecords = (List<CurrencyCountryLevelView>) findAll(criteria);
		
		return lstCountryRecords;
	}

	@Override
	public List<FimsCurmas> getFimsCurrencyCode(String fimsCurCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(FimsCurmas.class,"fimsCurmas");
		criteria.add(Restrictions.eq("fimsCurmas.currencyCode", fimsCurCode));
		List<FimsCurmas> lstFimsCode = (List<FimsCurmas>) findAll(criteria);
		return lstFimsCode;
	}

	@Override
	public HashMap<String, String> callPopulateCurmasProcedure(
			HashMap<String, String> inputValues)
			throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		String errString = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " {call EX_P_POPULATE_CURMAS (?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("APP_COUNTRYID")));
			cs.setString(2, inputValues.get("CURRENCY_CODE"));
 			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(3);
 			outputValues.put("P_ERROR_MESSAGE", errString);
 			
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_CURMAS" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_CURMAS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return outputValues;
	}

	@Override
	public List<CurrencyOtherInformation> searchByCurrencyId(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");
		
		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("currencyMasterInfo.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyOtherInformation> lstCurrencyOtherInformation = (List<CurrencyOtherInformation>) findAll(criteria);
		
		return lstCurrencyOtherInformation;
	}





}
