package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.bean.RoutingCountry;
import com.amg.exchange.common.dao.IGeneralDao;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.RuleApplicationMaster;
import com.amg.exchange.common.model.RulePageMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.model.RateAlertFrequency;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerIdproofView;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.CustomerMobileLogModel;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.model.LoginLogoutHistory;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.SuccessMessage;
import com.amg.exchange.registration.model.ViewActiveCustomerCheck;
import com.amg.exchange.remittance.bean.AmtbCouponDT;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.ViewAmtbCouponReport;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleDetailsDesc;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.DateUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class GeneralDaoImpl<T> extends CommonDaoImpl<T> implements IGeneralDao<T>, Serializable {

	private static final Logger LOG = Logger.getLogger(GeneralDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		LOG.info("Entering into getCountryList method");
		LOG.info("languageId == " + languageId);
		/*
		 * DetachedCriteria detachedCriteria =
		 * DetachedCriteria.forClass(CountryMasterDesc.class,
		 * "countryMasterDesc"); // Join Language Type table
		 * detachedCriteria.setFetchMode
		 * ("countryMasterDesc.fsLanguageType",FetchMode.JOIN);
		 * detachedCriteria.
		 * createAlias("countryMasterDesc.fsLanguageType","fsLanguageType",
		 * JoinType.INNER_JOIN); // Add Language Condition
		 * detachedCriteria.add(Restrictions
		 * .eq("fsLanguageType.languageId",languageId)); // Join Country Master
		 * Table
		 * detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster"
		 * ,FetchMode.JOIN);
		 * detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster"
		 * ,"fsCountryMaster", JoinType.INNER_JOIN);
		 * detachedCriteria.add(Restrictions
		 * .eq("fsCountryMaster.countryActive",Constants.Yes));
		 * detachedCriteria.
		 * setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY); //
		 * detachedCriteria
		 * .getExecutableCriteria(getSession()).setCacheable(true);
		 * detachedCriteria.addOrder(Order.asc("fsCountryMaster.countryCode"));
		 * LOG.info("Exit into getCountryList method");
		 */

		String hqlQuery = " from CountryMasterDesc a inner join a.fsLanguageType b  inner join a.fsCountryMaster c where c.countryActive= ? and b.languageId = ? order by a.fsCountryMaster.countryCode asc";

		Query query = getSession().createQuery(hqlQuery);

		query.setString(0, Constants.Yes);// Parameter("pbusinessCountry", 'Y');
		query.setBigDecimal(1, languageId);// ("planguageId", languageId);

		List<CountryMasterDesc> lstCountryMasterDesc = new ArrayList<CountryMasterDesc>();

		List<Object[]> lstObject = query.list();
		if (lstObject != null) {
			for (Object[] obj : lstObject) {
				CountryMasterDesc countryMasterDesc = (CountryMasterDesc) obj[0];
				LanguageType languageType = (LanguageType) obj[1];
				CountryMaster countryMaster = (CountryMaster) obj[2];

				countryMasterDesc.setFsCountryMaster(countryMaster);
				countryMasterDesc.setFsLanguageType(languageType);
				lstCountryMasterDesc.add(countryMasterDesc);

			}
		}

		// return (List<CountryMasterDesc>) findAll(detachedCriteria);
		return lstCountryMasterDesc;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CountryMasterDesc> getNationalityList(BigDecimal languageId) {
		LOG.info("Entering into getNationalityList method");
		LOG.info("languageId == " + languageId);

		/*
		 * DetachedCriteria detachedCriteria =
		 * DetachedCriteria.forClass(CountryMasterDesc.class,
		 * "countryMasterDesc"); // Join Language Type table
		 * detachedCriteria.setFetchMode
		 * ("countryMasterDesc.fsLanguageType",FetchMode.JOIN);
		 * detachedCriteria.
		 * createAlias("countryMasterDesc.fsLanguageType","fsLanguageType",
		 * JoinType.INNER_JOIN); // Add Language Condition
		 * detachedCriteria.add(Restrictions
		 * .eq("fsLanguageType.languageId",languageId)); // Join Country Master
		 * Table
		 * detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster"
		 * ,FetchMode.JOIN);
		 * detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster"
		 * ,"fsCountryMaster", JoinType.INNER_JOIN);
		 * detachedCriteria.add(Restrictions
		 * .isNotNull("countryMasterDesc.nationality")); // start by subramanian
		 * for asc nationality
		 * detachedCriteria.addOrder(Order.asc("countryMasterDesc.nationality"
		 * )); // end by subramanian for asc nationality
		 * detachedCriteria.setResultTransformer
		 * (DetachedCriteria.DISTINCT_ROOT_ENTITY); //
		 * detachedCriteria.getExecutableCriteria
		 * (getSession()).setCacheable(true);
		 * LOG.info("Exit into getNationalityList method");
		 */

		String hqlQuery = " from CountryMasterDesc a inner join a.fsLanguageType b  inner join a.fsCountryMaster c where c.countryActive= ? and b.languageId = ? and a.nationality is not null ";

		Query query = getSession().createQuery(hqlQuery);

		query.setString(0, Constants.Yes);// Parameter("pbusinessCountry", 'Y');
		query.setBigDecimal(1, languageId);// ("planguageId", languageId);

		List<CountryMasterDesc> lstCountryMasterDesc = new ArrayList<CountryMasterDesc>();

		List<Object[]> lstObject = query.list();
		if (lstObject != null) {
			for (Object[] obj : lstObject) {
				CountryMasterDesc countryMasterDesc = (CountryMasterDesc) obj[0];
				LanguageType languageType = (LanguageType) obj[1];
				CountryMaster countryMaster = (CountryMaster) obj[2];

				countryMasterDesc.setFsCountryMaster(countryMaster);
				countryMasterDesc.setFsLanguageType(languageType);
				lstCountryMasterDesc.add(countryMasterDesc);

			}
		}

		// return (List<CountryMasterDesc>) findAll(detachedCriteria);
		return lstCountryMasterDesc;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<StateMasterDesc> getStateList(BigDecimal languageId, BigDecimal countryId) {
		LOG.info("Entering into getStateList method");
		LOG.info("languageId == " + countryId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		// Join FS_LANGUAGE_TYPE table
		detachedCriteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join FS_STATE_MASTER table
		detachedCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		// Join FS_COUNTRY_MASTER table
		detachedCriteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		// Add FS_COUNTRY_MASTER Filter Condition
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("fsStateMaster.isActive", Constants.Yes));
		detachedCriteria.addOrder(Order.asc("stateMasterDesc.stateName"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// /detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getStateList method");
		return (List<StateMasterDesc>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId) {
		LOG.info("Entering into getDistrictList method");
		LOG.info("languageId == " + languageId);
		LOG.info("countryId == " + countryId);
		LOG.info("stateId == " + stateId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		// Join FS_LANGUAGE_TYPE table
		detachedCriteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join FS_DISTRICT_MASTER table
		detachedCriteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		// Join FS_STATE_MASTER table
		detachedCriteria.setFetchMode("fsDistrictMaster.fsStateMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsDistrictMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		// Join FS_COUNTRY_MASTER table
		detachedCriteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		// Add FS_COUNTRY_MASTER Filter Condition
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.add(Restrictions.eq("fsDistrictMaster.districtActive", Constants.Yes));
		detachedCriteria.addOrder(Order.asc("districtMasterDesc.district"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getDistrictList method");
		return (List<DistrictMasterDesc>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CityMasterDesc> getCityList(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId, BigDecimal districtId) {
		LOG.info("Entering into getCityList method");
		LOG.info("languageId == " + languageId);
		LOG.info("countryId == " + countryId);
		LOG.info("stateId == " + stateId);
		LOG.info("districtId == " + districtId);

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");
		// Join FS_LANGUAGE_TYPE table
		detachedCriteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join FS_CITY_MASTER table
		detachedCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
		// Join FS_DISTRICT_MASTER table
		detachedCriteria.setFetchMode("fsCityMaster.fsDistrictMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsCityMaster.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		// Join FS_STATE_MASTER table
		detachedCriteria.setFetchMode("fsDistrictMaster.fsStateMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsDistrictMaster.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		// Add FS_LANGUAGE_TYPE Condition
		detachedCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		// Join FS_COUNTRY_MASTER table
		detachedCriteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		// Add FS_COUNTRY_MASTER Filter Condition
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		detachedCriteria.add(Restrictions.eq("fsCityMaster.isActive", Constants.Yes));

		detachedCriteria.addOrder(Order.asc("cityMasterDesc.cityName"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getCityList method");
		return (List<CityMasterDesc>) findAll(detachedCriteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<StateMasterDesc> getStateList(BigDecimal languageId) {
		LOG.info("Entering into getStateList method");
		LOG.info("languageId == " + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// criteria.getExecutableCriteria(getSession()).setCacheable(true);
		return (List<StateMasterDesc>) findAll(criteria);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<DistrictMasterDesc> getDistrictList(BigDecimal languageId) {
		LOG.info("Entering into getDistrictList method");
		LOG.info("languageId == " + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
		criteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getDistrictList method");
		return (List<DistrictMasterDesc>) findAll(criteria);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public List<CityMasterDesc> getCityList(BigDecimal languageId) {
		LOG.info("Entering into getCityList method");
		LOG.info("languageId == " + languageId);
		DetachedCriteria criteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");
		criteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getCityList method");
		return (List<CityMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BusinessComponent> getAllComponentList() {
		LOG.info("Entering into getAllComponentList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(BusinessComponent.class, "businessComponent");
		criteria.setFetchMode("businessComponent.fsComponentType", FetchMode.JOIN);
		criteria.createAlias("businessComponent.fsComponentType", "fsComponentType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("businessComponent.isActive", "Y"));
		criteria.addOrder(Order.asc("businessComponent.componentName"));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getAllComponentList method");
		return (List<BusinessComponent>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RuleApplicationMaster> getAllApplicationList() {
		LOG.info("Entering into getAllApplicationList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(RuleApplicationMaster.class, "ruleApplicationMaster");
		criteria.addOrder(Order.asc("ruleApplicationMaster.applicationName"));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getAllApplicationList method");
		return (List<RuleApplicationMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyMasterDesc> getAllCompanyList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");

		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CompanyMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RulePageMaster> getAllPageList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(RulePageMaster.class, "rulePageMaster");
		criteria.addOrder(Order.asc("rulePageMaster.pageName"));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		return (List<RulePageMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getPageId(String pageName) {
		BigDecimal pageId = null;

		DetachedCriteria criteria = DetachedCriteria.forClass(RulePageMaster.class, "rulePageMaster");
		criteria.add(Restrictions.eq("rulePageMaster.pageCode", pageName));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);

		List<RulePageMaster> lstRulePageMaster = (List<RulePageMaster>) findAll(criteria);
		if (lstRulePageMaster != null && lstRulePageMaster.size() != 0) {
			pageId = lstRulePageMaster.get(0).getPageId();
		}

		return pageId;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BizComponentConfDetail> getComponentBehaviorNew(List<String> componentList, BigDecimal levelId, BigDecimal applicationId,
			BigDecimal companyId, BigDecimal countryId, BigDecimal pageId) {
		String alias = "fsbusiness1_";
		Map<String, BizComponentConfDetail> mapReturn = new HashMap<String, BizComponentConfDetail>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentConfDetail.class, "bizComponentConfDetail");
		criteria.setFetchMode("bizComponentConfDetail.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentConfDetail.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.setFetchMode("fsBusinessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		// Left join Application Master
		criteria.setFetchMode("fsBusinessComponentConf.fsRuleApplicationMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsRuleApplicationMaster", "fsRuleApplicationMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join Company Master
		criteria.setFetchMode("fsBusinessComponentConf.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join Country Master
		criteria.setFetchMode("fsBusinessComponentConf.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join page master
		criteria.setFetchMode("fsBusinessComponentConf.fsRulePageMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsRulePageMaster", "fsRulePageMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.in("fsBusinessComponent.componentName", componentList));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		StringBuilder sbf = new StringBuilder("(CASE ").append(alias).append(".LEVEL_ID ");
		sbf.append(" WHEN 0 THEN 1");
		sbf.append(" WHEN 1 THEN (CASE WHEN ").append(alias).append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 2 THEN (CASE WHEN ").append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 3 THEN (CASE WHEN ").append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias)
				.append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 4 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 5 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 6 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 7 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=").append(pageId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 8 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 9 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 10 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 11 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=")
				.append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 12 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 13 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=")
				.append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 14 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".COUNTRY_ID=")
				.append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 15 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".COUNTRY_ID=")
				.append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=").append(pageId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append("ELSE 1 END)=1");
		criteria.add(Restrictions.sqlRestriction(sbf.toString()));
		criteria.addOrder(Order.desc("fsBusinessComponentConf.levelId"));

		List<BizComponentConfDetail> lst = null;
		try {

			lst = (List<BizComponentConfDetail>) findAll(criteria);
			for (BizComponentConfDetail bean : lst) {
				if (mapReturn.containsKey(bean.getFsBusinessComponentConf().getFsBusinessComponent().getComponentName())) {
				} else {
					mapReturn.put(bean.getFsBusinessComponentConf().getFsBusinessComponent().getComponentName(), bean);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// Logger.getLogger(this.getClass()).log(Level.ERROR,
			// "Component List "+componentList+" behaviour not available !" ,
			// null);
		}
		return mapReturn;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, BizComponentConfDetail> getComponentBehavior(List<String> componentList, BigDecimal levelId, BigDecimal applicationId,
			BigDecimal companyId, BigDecimal countryId, BigDecimal pageId) {
		String alias = "fsbusiness1_";
		Map<String, BizComponentConfDetail> mapReturn = new HashMap<String, BizComponentConfDetail>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentConfDetail.class, "bizComponentConfDetail");
		criteria.setFetchMode("bizComponentConfDetail.fsBusinessComponentConf", FetchMode.JOIN);
		criteria.createAlias("bizComponentConfDetail.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		criteria.setFetchMode("fsBusinessComponentConf.fsBusinessComponent", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsBusinessComponent", "fsBusinessComponent", JoinType.INNER_JOIN);
		// Left join Application Master
		criteria.setFetchMode("fsBusinessComponentConf.fsRuleApplicationMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsRuleApplicationMaster", "fsRuleApplicationMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join Company Master
		criteria.setFetchMode("fsBusinessComponentConf.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join Country Master
		criteria.setFetchMode("fsBusinessComponentConf.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);
		// Left join page master
		criteria.setFetchMode("fsBusinessComponentConf.fsRulePageMaster", FetchMode.JOIN);
		criteria.createAlias("fsBusinessComponentConf.fsRulePageMaster", "fsRulePageMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.in("fsBusinessComponent.componentName", componentList));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		StringBuilder sbf = new StringBuilder("(CASE ").append(alias).append(".LEVEL_ID ");
		sbf.append(" WHEN 0 THEN 1");
		sbf.append(" WHEN 1 THEN (CASE WHEN ").append(alias).append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 2 THEN (CASE WHEN ").append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 3 THEN (CASE WHEN ").append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias)
				.append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 4 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 5 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 6 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 7 THEN (CASE WHEN ").append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias)
				.append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=").append(pageId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 8 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 9 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".PAGE_ID=").append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 10 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 11 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COUNTRY_ID=").append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=")
				.append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 12 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 13 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=")
				.append(pageId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 14 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".COUNTRY_ID=")
				.append(countryId.intValue()).append(" THEN 1 ELSE 0 END)");
		sbf.append(" WHEN 15 THEN (CASE WHEN ").append(alias).append(".APPLICATION_ID=").append(applicationId.intValue()).append(" AND ")
				.append(alias).append(".COMPANY_ID=").append(companyId.intValue()).append(" AND ").append(alias).append(".COUNTRY_ID=")
				.append(countryId.intValue()).append(" AND ").append(alias).append(".PAGE_ID=").append(pageId.intValue())
				.append(" THEN 1 ELSE 0 END)");
		sbf.append("ELSE 1 END)=1");
		criteria.add(Restrictions.sqlRestriction(sbf.toString()));
		criteria.addOrder(Order.desc("fsBusinessComponentConf.levelId"));

		List<BizComponentConfDetail> lst = null;
		try {

			lst = (List<BizComponentConfDetail>) findAll(criteria);
			for (BizComponentConfDetail bean : lst) {
				if (mapReturn.containsKey(bean.getFsBusinessComponentConf().getFsBusinessComponent().getComponentName())) {
				} else {
					mapReturn.put(bean.getFsBusinessComponentConf().getFsBusinessComponent().getComponentName(), bean);
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
			// Logger.getLogger(this.getClass()).log(Level.ERROR,
			// "Component List "+componentList+" behaviour not available !" ,
			// null);
		}
		return mapReturn;
	}

	public Map<BigDecimal, String> getComponentData(String component, BigDecimal levelId, BigDecimal applicationId, BigDecimal companyId,
			BigDecimal countryId, BigDecimal pageId, BigDecimal languageId) {
		List<String> componentList = new ArrayList<String>();
		componentList.add(component);
		Map<String, BizComponentConfDetail> mapData = getComponentBehavior(componentList, levelId, applicationId, companyId, countryId, pageId);
		return getAllComponentComboData(mapData.get(component).getFsBusinessComponentConf().getComponentConfId(), languageId);
	}

	@Override
	@SuppressWarnings("unchecked")
	public Map<BigDecimal, String> getAllComponentComboData(BigDecimal componentConfId, BigDecimal languageId) {
		Map<BigDecimal, String> mapComponentComboData = new LinkedHashMap<BigDecimal, String>();
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentData.active", "Y"));
		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		DetachedCriteria subCriteria = DetachedCriteria.forClass(BizComponentDataRef.class, "bizComponentDataRef");
		subCriteria.setFetchMode("bizComponentDataRef.fsBusinessComponentConf", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBusinessComponentConf", "fsBusinessComponentConf", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eq("bizComponentDataRef.active", "Y"));
		subCriteria.add(Restrictions.eq("fsBusinessComponentConf.componentConfId", componentConfId));
		subCriteria.setFetchMode("bizComponentDataRef.fsBizComponentData", FetchMode.JOIN);
		subCriteria.createAlias("bizComponentDataRef.fsBizComponentData", "bizComponentData", JoinType.INNER_JOIN);
		subCriteria.add(Restrictions.eqProperty("bizComponentData.componentDataId", "fsBizComponentData.componentDataId"));
		subCriteria.setProjection(Projections.distinct(Projections.property("bizComponentData.componentDataId")));
		criteria.add(Subqueries.propertyIn("fsBizComponentData.componentDataId", subCriteria));
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("fsBizComponentData.componentDataId"));
		projectionList.add(Projections.property("bizComponentDataDesc.dataDesc"));
		criteria.setProjection(projectionList);
		criteria.addOrder(Order.asc("bizComponentDataDesc.dataDesc"));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<Object[]> tempList = (List<Object[]>) findAll(criteria);
		for (Object[] row : tempList) {
			mapComponentComboData.put((BigDecimal) row[0], (String) row[1]);
		}
		return mapComponentComboData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<BigDecimal, String> getAllCountry(BigDecimal languageId) {
		LOG.info("Entering into getAllCountry method");
		LOG.info("languageId == " + languageId);
		Map<BigDecimal, String> mapCountryList = new TreeMap<BigDecimal, String>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("fsCountryMaster.countryId"));
		projectionList.add(Projections.property("countryMasterDesc.countryName"));
		detachedCriteria.setProjection(projectionList);
		detachedCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));
		detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		for (Object[] row : (List<Object[]>) findAll(detachedCriteria)) {
			mapCountryList.put((BigDecimal) row[0], (String) row[1]);
		}
		return mapCountryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<BigDecimal, String> getAllNationality(BigDecimal languageId) {
		LOG.info("Entering into getAllNationality method");
		LOG.info("languageId == " + languageId);
		Map<BigDecimal, String> mapCountryList = new LinkedHashMap<BigDecimal, String>();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		/*
		 * // Add Bussness of country Condition
		 * detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryActive",
		 * "Y"));
		 */
		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.property("fsCountryMaster.countryId"));
		projectionList.add(Projections.property("countryMasterDesc.nationality"));
		detachedCriteria.setProjection(projectionList);
		detachedCriteria.addOrder(Order.asc("countryMasterDesc.nationality"));
		detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);
		for (Object[] row : (List<Object[]>) findAll(detachedCriteria)) {
			mapCountryList.put((BigDecimal) row[0], (String) row[1]);
		}
		LOG.info("Exit into getAllNationality method");
		return mapCountryList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LanguageType> getAllLanguages() {
		LOG.info("Entering into getAllLanguages method");
		DetachedCriteria criteria = DetachedCriteria.forClass(LanguageType.class, "languageType");
		LOG.info("Exit into getAllLanguages method");
		return (List<LanguageType>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getAllBankList(BigDecimal languageId, BigDecimal countryId) {
		LOG.info("Entering into getAllBankList method");
		LOG.info("languageId == " + languageId);
		LOG.info("countryId == " + countryId);
		String languageInd = languageId.toString();
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		// dCriteria.add(Restrictions.eq("languageInd", languageInd));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getAllBankList method");
		return (List<BankMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchList(BigDecimal countryId, BigDecimal bankId) {
		LOG.info("Entering into getBankBranchList method");
		LOG.info("countryId == " + countryId);
		LOG.info("bankId == " + bankId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		dCriteria.addOrder(Order.asc("bankBranch.branchFullName"));
		dCriteria.add(Restrictions.eq("bankBranch.isactive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBankBranchList method");
		return (List<BankBranch>) findAll(dCriteria);
	}

	@Override
	public String getCivilIdStatus(BigDecimal civilID) {
		LOG.info("Entering into getCivilIdStatus method");
		LOG.info("civilID == " + civilID);
		SQLQuery sqlQuery = super.getSession().createSQLQuery("select CIVILID_VALIDATE(:civilId) as STATUS from Dual");
		sqlQuery.setBigDecimal("civilId", civilID);
		LOG.info("uniqueResult ==" + sqlQuery.uniqueResult().toString());
		return sqlQuery.uniqueResult().toString();
	}

	@Override
	public BizComponentDataDesc getComponentId(String inputString, BigDecimal langId) {
		LOG.info("Entering into getComponentId method");
		LOG.info("inputString == " + inputString);
		LOG.info("langId == " + langId);

		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");
		criteria.add(Restrictions.eq("bizComponentDataDesc.dataDesc", inputString));
		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);

		BizComponentDataDesc bizComponentDataDesc = null;

		List<BizComponentDataDesc> bizComp = (List<BizComponentDataDesc>) findAll(criteria);

		if (bizComp.size() != 0) {
			bizComponentDataDesc = bizComp.get(0);
		}

		LOG.info("Exit into getComponentId method");
		return bizComponentDataDesc;
	}

	@Override
	public BizComponentData getComponentDesc(BigDecimal inputId, BigDecimal langId) {
		LOG.info("Entering into getComponentDesc method");
		LOG.info("inputId == " + inputId);
		LOG.info("langId == " + langId);
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentData.class, "bizComponentData");
		criteria.add(Restrictions.eq("bizComponentData.componentDataId", inputId));
		criteria.setFetchMode("bizComponentData.fsBizComponentDataDescs", FetchMode.JOIN);
		criteria.createAlias("bizComponentData.fsBizComponentDataDescs", "fsBizComponentDataDescs", JoinType.INNER_JOIN);
		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", langId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		LOG.info("Exit into getComponentDesc method");
		return (BizComponentData) findAll(criteria).get(0);
	}

	// added by Nazish For list of business country
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId) {
		LOG.info("Entering into getBusinessCountryList method");
		LOG.info("languageId == " + languageId);
		/*
		 * DetachedCriteria detachedCriteria =
		 * DetachedCriteria.forClass(CountryMasterDesc.class,
		 * "countryMasterDesc"); // Join Language Type table
		 * detachedCriteria.setFetchMode
		 * ("countryMasterDesc.fsLanguageType",FetchMode.JOIN);
		 * detachedCriteria.
		 * createAlias("countryMasterDesc.fsLanguageType","fsLanguageType",
		 * JoinType.INNER_JOIN); // Add Language Condition
		 * detachedCriteria.add(Restrictions
		 * .eq("fsLanguageType.languageId",languageId)); // Add Bussness of
		 * country Condition
		 * detachedCriteria.add(Restrictions.eq("fsCountryMaster.businessCountry"
		 * , "Y")); // Join Country Master Table
		 * detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",
		 * FetchMode.JOIN);
		 * detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster"
		 * ,"fsCountryMaster", JoinType.INNER_JOIN); // start by subramanian for
		 * asc countryName
		 * detachedCriteria.addOrder(Order.asc("countryMasterDesc.countryName"
		 * )); // End by subramanian for asc countryName
		 * detachedCriteria.setResultTransformer
		 * (DetachedCriteria.DISTINCT_ROOT_ENTITY); //
		 * detachedCriteria.getExecutableCriteria
		 * (getSession()).setCacheable(true);
		 * LOG.info("Exit into getBusinessCountryList method");
		 */

		String hqlQuery = " from CountryMasterDesc a inner join a.fsLanguageType b  inner join a.fsCountryMaster c where c.businessCountry= ? and b.languageId = ?  ";

		Query query = getSession().createQuery(hqlQuery);

		query.setString(0, Constants.Yes);// Parameter("pbusinessCountry", 'Y');
		query.setBigDecimal(1, languageId);// ("planguageId", languageId);

		List<CountryMasterDesc> lstCountryMasterDesc = new ArrayList<CountryMasterDesc>();

		List<Object[]> lstObject = query.list();
		if (lstObject != null) {
			for (Object[] obj : lstObject) {
				CountryMasterDesc countryMasterDesc = (CountryMasterDesc) obj[0];
				LanguageType languageType = (LanguageType) obj[1];
				CountryMaster countryMaster = (CountryMaster) obj[2];

				countryMasterDesc.setFsCountryMaster(countryMaster);
				countryMasterDesc.setFsLanguageType(languageType);
				lstCountryMasterDesc.add(countryMasterDesc);

			}
		}

		return lstCountryMasterDesc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getBankAccountDetailsList(BigDecimal bankId) {
		LOG.info("Entering into getBankAccountDetailsList method");
		LOG.info("bankId == " + bankId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		detachedCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountDetails>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMessage(BigDecimal countryId, BigDecimal languageId) {
		LOG.info("Entering into getMessage method");
		LOG.info("countryId == " + countryId);
		LOG.info("languageId == " + languageId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(SuccessMessage.class, "successMsg");
		dCriteria.setFetchMode("successMsg.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("successMsg.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		dCriteria.setFetchMode("successMsg.fsCountryMessage", FetchMode.JOIN);
		dCriteria.createAlias("successMsg.fsCountryMessage", "fsCountryMessage", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMessage.countryId", countryId));
		List<SuccessMessage> message = (List<SuccessMessage>) findAll(dCriteria);
		LOG.info("Exit into getMessage method");
		if (message != null && message.size() > 0) {
			return message.get(0).getSeccessMsg();
		} else {
			return "";
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyList() {
		LOG.info("Entering into getCurrencyList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		// criteria.addOrder(Order.asc("currencyMaster.currencyName"));
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		LOG.info("Exit into getCurrencyList method");
		return (List<CurrencyMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankList(BigDecimal countryId) {
		LOG.info("Entering into getCurrencyList method");
		LOG.info("countryId == " + countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		List<BankMaster> data = (List<BankMaster>) findAll(dCriteria);
		LOG.info("Exit into getCurrencyList method");
		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getBranchDetails(BigDecimal countryId) {
		LOG.info("Entering into getBranchDetails method");
		LOG.info("countryId == " + countryId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("countryBranch.branchName"));
		LOG.info("Exit into getBranchDetails method");
		return (List<CountryBranch>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getBranchDetailsForToLocation(BigDecimal countryId, BigDecimal countryBranchId) {
		LOG.info("Entering into getBranchDetailsForToLocation method");
		LOG.info("countryId == " + countryId);
		LOG.info("countryBranchId == " + countryBranchId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.ne("countryBranch.countryBranchId", countryBranchId));
		dCriteria.addOrder(Order.asc("countryBranch.branchName"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchDetailsForToLocation method");
		return (List<CountryBranch>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getBranchDetailsFromBeneStatus(BigDecimal countryId, BigDecimal countryBranchId) {
		LOG.info("Entering into getBranchDetailsFromBeneStatus method");
		LOG.info("countryId == " + countryId);
		LOG.info("countryBranchId == " + countryBranchId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchDetailsFromBeneStatus method");
		return (List<CountryBranch>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleMaster> getRole() {
		return (List<RoleMaster>) findAll(DetachedCriteria.forClass(RoleMaster.class));
	}

	@Override
	public BigDecimal callSaleProjection(BigDecimal companyId, BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal currency,
			BigDecimal bankId, BigDecimal noOfDays) {
		LOG.info("Entering into callSaleProjection method");
		LOG.info("companyId == " + companyId);
		LOG.info("componentId == " + exchangeCountry);
		LOG.info("bankCountry == " + bankCountry);
		LOG.info("currency == " + currency);
		LOG.info("bankId == " + bankId);
		LOG.info("noOfDays == " + noOfDays);
		BigDecimal out = null;
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();;
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call SALES_PROJECTION (?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setInt(2, 10);
			cs.setString(3, "I");
			cs.setInt(4, 10);
			cs.setString(5, "K");
			cs.setInt(6, noOfDays.intValueExact());
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.executeUpdate();
			out = cs.getBigDecimal(1);
			connection.close();
		} catch (SQLException e) {
			LOG.error("Exception occured in GeneralDaoImpl ::callSaleProjection method ", e);
		}
		LOG.info("noOfDays == " + out);
		LOG.info("Exit into callSaleProjection method");
		return out;
	}

	@Override
	public BigDecimal callValueDatedTransaction(BigDecimal companyId, BigDecimal exchangeCountry, BigDecimal bankCountry, BigDecimal currency,
			BigDecimal bankId, BigDecimal noOfDays) {

		LOG.info("Entering into callValueDatedTransaction method");
		LOG.info("companyId == " + companyId);
		LOG.info("componentId == " + exchangeCountry);
		LOG.info("bankCountry == " + bankCountry);
		LOG.info("currency == " + currency);
		LOG.info("bankId == " + bankId);
		LOG.info("noOfDays == " + noOfDays);
		BigDecimal out = null;
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call VALUE_DATED_TRANSACTION (?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setInt(2, exchangeCountry.intValue());
			cs.setString(3, "I");
			cs.setInt(4, 10);
			cs.setString(5, "K");
			cs.setString(6, noOfDays.toPlainString());
			cs.setString(7, "3 days");
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.executeUpdate();
			out = cs.getBigDecimal(1);
			connection.close();
		} catch (SQLException e) {
			LOG.error("Exception occured in GeneralDaoImpl ::callValueDatedTransaction method ", e);
		}
		LOG.info("noOfDays == " + out);
		LOG.info("Exit into callValueDatedTransaction method");
		return out;
	}

	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	/**
	 * This method is responsible to return all the bank country irrespective of
	 * application country
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BankCountryPopulationBean> getAllBankContry() {
		BankCountryPopulationBean bankCountryPopulationBean = null;
		List<BankCountryPopulationBean> finalData = new ArrayList<BankCountryPopulationBean>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("fsCountryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("fsCountryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		// dCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId",
		// countryId)));

		List<BankMaster> lstBankmaster = dCriteria.getExecutableCriteria(getSession()).list();

		for (BankMaster bankMaster : lstBankmaster) {
			// bankCountryPopulationBean = new
			// BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(),
			// bankMaster.getFsCountryMaster().getFsCountryMasterDescs().get(0).getCountryName());
			bankCountryPopulationBean = new BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getFsCountryMaster()
					.getFsCountryMasterDescs().get(0).getCountryName(), bankMaster.getFsCountryMaster().getCountryCode());
			if (lstBankmaster.size() != 0) {
				if (!duplicateCheck.contains(bankCountryPopulationBean.getBankCountryId())) {
					duplicateCheck.add(bankCountryPopulationBean.getBankCountryId());
					finalData.add(bankCountryPopulationBean);
				}
			}
		}

		return finalData;
	}

	/**
	 * This method is responsible to return all country as bank country from
	 * bank master, except the application country
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BankCountryPopulationBean> getBankContry(BigDecimal appliationCountry) {
		BankCountryPopulationBean bankCountryPopulationBean = null;
		List<BankCountryPopulationBean> finalData = new ArrayList<BankCountryPopulationBean>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("fsCountryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria.createAlias("fsCountryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId", appliationCountry)));
		dCriteria.addOrder(Order.asc("fsCountryMasterDescs.countryName"));
		/** NAG CODE 04/02/2015 **/
		List<BankMaster> lstBankmaster = dCriteria.getExecutableCriteria(getSession()).list();

		for (BankMaster bankMaster : lstBankmaster) {
			// bankCountryPopulationBean = new
			// BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(),
			// bankMaster.getFsCountryMaster().getFsCountryMasterDescs().get(0).getCountryName());
			bankCountryPopulationBean = new BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getFsCountryMaster()
					.getFsCountryMasterDescs().get(0).getCountryName(), bankMaster.getFsCountryMaster().getCountryCode());
			if (!duplicateCheck.contains(bankCountryPopulationBean.getBankCountryId())) {
				duplicateCheck.add(bankCountryPopulationBean.getBankCountryId());
				finalData.add(bankCountryPopulationBean);
			}
		}
		return finalData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCountryCurrencyList(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.addOrder(Order.asc("currencyMaster.currencyCode"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(dCriteria);

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountry, BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		/*
		 * 18/10/2014 we removed FS_Application_CountryID from Bank Master Table
		 * dCriteria.setFetchMode("bankMaster.fsApplicationCountryMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("bankMaster.fsApplicationCountryMaster",
		 * "fsApplicationCountryMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("fsApplicationCountryMaster.countryId",
		 * applicationCountry));
		 */

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return dCriteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getAllBankListFromBankMaster() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		//dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		dCriteria.addOrder(Order.asc("bankMaster.bankCode"));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		return dCriteria.getExecutableCriteria(getSession()).list();
	}

	@Override
	public String getCountryName(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		// Add Language Condition
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ArticleMaster> getArtilces() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
		criteria.add(Restrictions.eq("customerType", "I"));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<ArticleMaster> data = (List<ArticleMaster>) findAll(criteria);
		return data;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<ArticleDetails> getLevelData(BigDecimal articleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ArticleDetails.class, "articleDetails");

		criteria.setFetchMode("articleDetails.fsArticleMaster", FetchMode.JOIN);
		criteria.createAlias("articleDetails.fsArticleMaster", "fsArticleMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsArticleMaster.articleId", articleId));
		criteria.getExecutableCriteria(getSession()).setCacheable(true);

		return (List<ArticleDetails>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IncomeRangeMaster> getIncomeRange(BigDecimal countryId, BigDecimal articleDetailId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");

		criteria.setFetchMode("incomeRangeMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		criteria.setFetchMode("incomeRangeMaster.articleDetail", FetchMode.JOIN);
		criteria.createAlias("incomeRangeMaster.articleDetail", "articleDetail", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("articleDetail.articleDetailId", articleDetailId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);

		return (List<IncomeRangeMaster>) findAll(criteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getAllBankCountryList() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		detachedCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return detachedCriteria.getExecutableCriteria(getSession()).list();
	}

	// Added by Nazish 13-11-2014 for FX Deal

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId, BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CompanyMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Document> getDocument(BigDecimal id, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "documentIdDetalis");
		criteria.add(Restrictions.eq("documentIdDetalis.documentCode", id));

		criteria.setFetchMode("documentIdDetalis.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("documentIdDetalis.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		// Add Language Condition
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<Document>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinancialYear> getDealYear(Date currentDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			Date S = formatter.parse(formatter.format(currentDate));
			System.out.println("Today : " + S);

			if (currentDate != null) {

				detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", S)).add(
						Restrictions.ge("userFinancialYear.financialYearEnd", S));

			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return (List<UserFinancialYear>) findAll(detachedCriteria);
	}

	@Override
	public String getNextDocumentReferenceNumber(int countryId, int companyId, int documentId, int financialYear, String processIn,
			BigDecimal branchId) {

		int out = 0;
		// Added by Rabil 18072015
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO countryId :" + countryId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO companyId :" + companyId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO documentId :" + documentId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO financialYear :" + financialYear);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO processIn :" + processIn);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO branchId :" + branchId);

		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setBigDecimal(2, branchId);
			cs.setInt(3, companyId);
			cs.setInt(4, documentId);
			cs.setInt(5, financialYear);
			cs.setString(6, processIn);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			out = cs.getInt(7);
			String a = cs.getString(8);
			String b = cs.getString(9);

			System.out.println("out :" + out + "\t a :" + a + "\t b:" + b);

			LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + out);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return String.valueOf(out);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAcoountDetails");

		dCriteria.setFetchMode("bankAcoountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankAcoountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyId));

		dCriteria.add(Restrictions.eq("bankAcoountDetails.recordStatus", Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(dCriteria);
		List<BankAccountDetails> finalData = new ArrayList<BankAccountDetails>();

		for (BankAccountDetails bankAccountDetails : data) {
			// if(new
			// SimpleDateFormat("dd/MM/yyyy").format(bankAccountDetails.getCreateDate()).equalsIgnoreCase(new
			// SimpleDateFormat("dd/MM/yyyy").format(new Date()))) {
			finalData.add(bankAccountDetails);
			// }
		}

		return finalData;
	}

	/*
	 * Start Name : RamMohanReddy Purpose :TO get FC Amount Date: 18-Nov-2014
	 */
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getForeignCurrencyBalanceFromAccDetailID(String accountDetailId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");

		// dCriteria.setFetchMode("accountBalance.exBankAccountDetails",
		// FetchMode.JOIN);
		// dCriteria.createAlias("accountBalance.exBankAccountDetails",
		// "exBankAccountDetails", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountDetailId));

		List<AccountBalance> data = (List<AccountBalance>) findAll(dCriteria);

		if (data.size() != 0) {
			// if(new
			// SimpleDateFormat("dd/MM/yyyy").format(data.get(0).getCreatedDate()).equalsIgnoreCase(new
			// SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
			if (data.get(0).getForeignBalance() != null) {
				return data.get(0).getForeignBalance();
			} else {
				return null;
			}
			// } else {
			// return null;
			// }
		} else {
			return null;
		}

		/*
		 * End Name : RamMohanReddy Purpose :TO get FC Amount Date: 18-Nov-2014
		 */

	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getAvgRateFromAccountBalanceTable(BigDecimal accountDetailId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		dCriteria.createAlias("accountBalance.exBankAccountDetails", "exBankAccountDetails", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankAccountDetails.bankAcctDetId", accountDetailId));

		List<AccountBalance> data = (List<AccountBalance>) findAll(dCriteria);
		if (data.size() != 0) {
			// if(new
			// SimpleDateFormat("dd/MM/yyyy").format(data.get(0).getCreatedDate()).equalsIgnoreCase(new
			// SimpleDateFormat("dd/MM/yyyy").format(new Date()))){
			if (data.get(0).getAverageRate() != null) {
				return data.get(0).getAverageRate();
			} else {
				return null;
			}
			// } else {
			// return null;
			// }
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getMobileCheck(BigDecimal countryId, String mobileNo) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");

		criteria.setFetchMode("customer.fsCountryMasterByCountryId", FetchMode.JOIN);
		criteria.createAlias("customer.fsCountryMasterByCountryId", "fsCountryMasterByCountryId", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMasterByCountryId.countryId", countryId));
		criteria.add(Restrictions.eq("customer.mobile", mobileNo));

		return (List<Customer>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getEmailCheck(String email) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");

		criteria.add(Restrictions.eq("customer.email", email));
		return (List<Customer>) findAll(criteria);
	}

	@Override
	public String getStateName(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");

		dCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<StateMasterDesc> lstStateMasterDesc = (List<StateMasterDesc>) findAll(dCriteria);

		String stateName = null;
		if (lstStateMasterDesc.size() > 0) {
			stateName = lstStateMasterDesc.get(0).getStateName();
		}

		return stateName;
	}

	@Override
	public String getDistrictName(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId, BigDecimal districtId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");

		dCriteria.setFetchMode("districtMasterDesc.fsDistrictMaster", FetchMode.JOIN);
		dCriteria.createAlias("districtMasterDesc.fsDistrictMaster", "fsDistrictMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("districtMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("districtMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.add(Restrictions.eq("fsDistrictMaster.districtId", districtId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DistrictMasterDesc> lstDistrictMasterDesc = (List<DistrictMasterDesc>) findAll(dCriteria);

		String districtName = null;
		if (lstDistrictMasterDesc.size() > 0) {
			districtName = lstDistrictMasterDesc.get(0).getDistrict();

		}
		return districtName;
	}

	@Override
	public String getCityName(BigDecimal languageId, BigDecimal countryId, BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");

		dCriteria.setFetchMode("cityMasterDesc.fsCityMaster", FetchMode.JOIN);
		dCriteria.createAlias("cityMasterDesc.fsCityMaster", "fsCityMaster", JoinType.INNER_JOIN);

		if (languageId != null) {
			dCriteria.setFetchMode("cityMasterDesc.fsLanguageType", FetchMode.JOIN);
			dCriteria.createAlias("cityMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		}

		dCriteria.add(Restrictions.eq("fsCityMaster.cityId", cityId));

		return ((CityMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCityName();
	}

	@Override
	public String getTitleName(BigDecimal languageId, BigDecimal title) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentdatadesc");

		dCriteria.setFetchMode("bizcomponentdatadesc.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentdatadesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", title));

		return ((BizComponentDataDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getDataDesc();
	}

	@Override
	public String getNationalityName(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getNationality();
	}

	/*
	 * @Override public String getArticleName(BigDecimal articleId) {
	 * DetachedCriteria dCriteria =
	 * DetachedCriteria.forClass(ArticleMaster.class, "articleMaster");
	 * 
	 * 
	 * dCriteria.add(Restrictions.eq("articleMaster.articleId", articleId));
	 * 
	 * return ((ArticleMaster)
	 * dCriteria.getExecutableCriteria(getSession()).list
	 * ().get(0)).getArticleName(); }
	 */
	@Override
	public String getArticleName(BigDecimal articleId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleMasterDesc.class, "articleMaster");

		dCriteria.add(Restrictions.eq("articleMaster.articleId", articleId));

		return ((ArticleMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getArticleeDescription();
	}

	/*
	 * @Override public String getLevelName(BigDecimal level) { DetachedCriteria
	 * dCriteria = DetachedCriteria.forClass(ArticleDetails.class,
	 * "articleDetails");
	 * 
	 * 
	 * dCriteria.add(Restrictions.eq("articleDetails.articleDetailId", level));
	 * 
	 * return ((ArticleDetails)
	 * dCriteria.getExecutableCriteria(getSession()).list
	 * ().get(0)).getArticleDesc(); }
	 */

	@Override
	public String getLevelName(BigDecimal level) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ArticleDetailsDesc.class, "articleDetails");

		dCriteria.add(Restrictions.eq("articleDetails.articleDetailId", level));

		return ((ArticleDetailsDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getArticleDetailDesc();
	}

	@Override
	public String getIncomeRangeName(BigDecimal income) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(IncomeRangeMaster.class, "incomeRangeMaster");

		dCriteria.add(Restrictions.eq("incomeRangeMaster.incomeRangeId", income));

		return ((IncomeRangeMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getMonthlyIncome();
	}

	@Override
	public String getEmplTypeName(BigDecimal languageId, BigDecimal employment) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentdatadesc");

		dCriteria.setFetchMode("bizcomponentdatadesc.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentdatadesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", employment));

		return ((BizComponentDataDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getDataDesc();
	}

	@Override
	public String getProfessionName(BigDecimal languageId, BigDecimal profession) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentdatadesc");

		dCriteria.setFetchMode("bizcomponentdatadesc.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentdatadesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", profession));

		return ((BizComponentDataDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getDataDesc();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLogin> getEmailCheckUser(String email) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");

		criteria.add(Restrictions.eq("customerLogin.email", email));
		return (List<CustomerLogin>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSearchIdType(BigDecimal languageId, BigDecimal idType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentdatadesc");

		dCriteria.setFetchMode("bizcomponentdatadesc.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentdatadesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bizcomponentdatadesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentdatadesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", idType));

		List<BizComponentDataDesc> data = (List<BizComponentDataDesc>) findAll(dCriteria);
		return data.get(0).getDataDesc();

	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<BankApplicability>
	 * getCoresBankLocalBankInterBankList(BigDecimal countryId,BigDecimal
	 * getBankCountry) {
	 * 
	 * DetachedCriteria dCriteria =
	 * DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
	 * 
	 * //to get Bank CountryID From FsCountry Master
	 * dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",
	 * JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",
	 * getBankCountry));
	 * dCriteria.add(Restrictions.eq("bankMaster.recordStatus", "Y"));//CR
	 * 18/12/2014 Record Status Condition added
	 * 
	 * dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.bankInd", "bankInd",
	 * JoinType.INNER_JOIN);
	 * 
	 * //to get Application CountryID From FsCountry Master
	 * dCriteria.setFetchMode("bankApplicability.fsCountryMaster",
	 * FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.fsCountryMaster",
	 * "fsCountryMaster", JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
	 * 
	 * dCriteria.add(Restrictions.disjunction() .add(
	 * Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(2)))
	 * .add(Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(1) ))
	 * .add(Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(3))) );
	 * dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
	 *//** NAG APPLY ASCENDING 04/02/2015 **/
	/*
	 * dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	 * 
	 * return (List<BankApplicability>)findAll(dCriteria); }
	 */

	// To fetch All Local , Beneficiary , Corresponding
	@Override
	public List<BankApplicability> getCoresBankLocalBankInterBankList(BigDecimal countryId, BigDecimal getBankCountry) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", getBankCountry));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
		// 18/12/2014
		// Record
		// Status
		// Condition
		// added

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		// to get Application CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);
		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal fundingBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_FUND_BANK);
		// BigDecimal beneficiaryBankIndicatorId =
		// fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (corresBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// corresBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));
		}

		if (localBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// localBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId));
		}

		if (fundingBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// fundingBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", fundingBankIndicatorId));
		}

		/*
		 * if(beneficiaryBankIndicatorId != null){
		 * lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",
		 * beneficiaryBankIndicatorId)); }
		 */

		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankApplicability>) findAll(dCriteria);
	}

	@Override
	public List<BankApplicability> getCoresBankList(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal serviceProviderBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (corresBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));
		}

		if (serviceProviderBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", serviceProviderBankIndicatorId));
		}

		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		return data;

		/*
		 * DetachedCriteria dCriteria =
		 * DetachedCriteria.forClass(BankApplicability.class,
		 * "bankApplicability");
		 * 
		 * //to get Bank CountryID From FsCountry Master
		 * dCriteria.setFetchMode("bankApplicability.bankMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",
		 * JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",
		 * getBankCountry));
		 * dCriteria.add(Restrictions.eq("bankMaster.recordStatus",
		 * Constants.Yes));//CR 18/12/2014 Record Status Condition added
		 * 
		 * dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		 * dCriteria.createAlias("bankApplicability.bankInd", "bankInd",
		 * JoinType.INNER_JOIN);
		 * 
		 * //to get Application CountryID From FsCountry Master
		 * dCriteria.setFetchMode("bankApplicability.fsCountryMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("bankApplicability.fsCountryMaster",
		 * "fsCountryMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("fsCountryMaster.countryId",
		 * countryId));
		 * 
		 * 
		 * BigDecimal corresBankIndicatorId =
		 * fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		 * 
		 * dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId",
		 * corresBankIndicatorId));
		 * 
		 * 
		 * dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		 * 
		 * dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 * 
		 * return (List<BankApplicability>)findAll(dCriteria);
		 */
	}
	
	
	@Override
	public List<BankApplicability> getBeneBankList(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		BigDecimal beneBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (beneBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", beneBankIndicatorId));
		}
		
		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		return data;
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public List<BankApplicability>
	 * getCorrespondingLocalFundingBanks(BigDecimal countryId) {
	 * 
	 * DetachedCriteria dCriteria =
	 * DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
	 * 
	 * //to get Bank CountryID From FsCountry Master
	 * dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",
	 * JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("bankMaster.recordStatus", "Y"));//CR
	 * 18/12/2014 Record Status Condition added
	 * 
	 * dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.bankInd", "bankInd",
	 * JoinType.INNER_JOIN);
	 * 
	 * //to get Application CountryID From FsCountry Master
	 * dCriteria.setFetchMode("bankApplicability.fsCountryMaster",
	 * FetchMode.JOIN);
	 * dCriteria.createAlias("bankApplicability.fsCountryMaster",
	 * "fsCountryMaster", JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
	 * 
	 * dCriteria.add(Restrictions.disjunction() .add(
	 * Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(2)))
	 * .add(Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(1) ))
	 * .add(Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(3))) );
	 * dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
	 *//** NAG CODE ASCENDING ORDER APPLY 04/02/2015 **/
	/*
	 * 
	 * dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	 * 
	 * return (List<BankApplicability>)findAll(dCriteria); }
	 */

	@Override
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryId) {

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		List<BankApplicability> lstBankApplicabity = new ArrayList<BankApplicability>();

		lstBankApplicabity.clear();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
		// 18/12/2014
		// Record
		// Status
		// Condition
		// added

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		// to get Application CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);
		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal fundingBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_FUND_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (localBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// corresBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId));
		}

		if (corresBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// localBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));
		}

		if (fundingBankIndicatorId != null) {
			// dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",
			// fundingBankIndicatorId)));
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId", fundingBankIndicatorId));
		}

		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBankApp = (List<BankApplicability>) findAll(dCriteria);

		for (BankApplicability bankApplicability : lstBankApp) {
			if (!duplicateCheck.contains(bankApplicability.getBankMaster().getBankId())) {
				duplicateCheck.add(bankApplicability.getBankMaster().getBankId());
				lstBankApplicabity.add(bankApplicability);
			}
		}

		return lstBankApplicabity;
	}

	@Override
	public List<BankMaster> getCorrespondingBanks(BigDecimal countryId) {

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankMaster> lstBank = new ArrayList<BankMaster>();
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		
		dCriteria.addOrder(Order.asc("bankMaster.bankCode"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> lstBankMaster = (List<BankMaster>) findAll(dCriteria);
		
		for (BankMaster bankMaster : lstBankMaster) {
			if (!duplicateCheck.contains(bankMaster.getBankId())) {
				duplicateCheck.add(bankMaster.getBankId());
				lstBank.add(bankMaster);
			}
		}

		return lstBank;
	}

	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "bankIndicator");

		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);

		if (lstBankIndId.size() != 0) {
			return ((List<BankIndicator>) findAll(dCriteria)).get(0).getBankIndicatorId();
		}

		return null;
	}

	@Override
	public List<BankApplicability> getLocalBankList(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
		// 18/12/2014
		// Record
		// Status
		// Condition
		// added

		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBalance> getBankBalance(String accountNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountNo));

		return (List<AccountBalance>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankListbyIndicator(BigDecimal countryId, BigDecimal indicator) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		System.out.println("!indicator!!!" + countryId + "indicator" + indicator);

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));

		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", indicator));

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.add(Restrictions.eq("bankApplicability.isActive", Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);
		System.out.println("!!!!!!!!!All Type Bank Based on Indicator Size!!!!!!!!!!!!!" + data.size());

		return data;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankListbyIndicatoronly(BigDecimal indicator, BigDecimal companyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");
		System.out.println("indicator" + indicator);

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankApplicability.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", indicator));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);
		System.out.println("!!!!!!!!!All Type Bank Based on Indicator Size!!!!!!!!!!!!!" + data.size());

		return data;
	}

	@Override
	public String getBankName(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		return ((BankMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getBankFullName();
	}

	@Override
	public String getCurrencyName(BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		dCriteria.add(Restrictions.eq("currencyMaster.isactive", Constants.Yes));
		return ((CurrencyMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCurrencyName();
	}

	@Override
	public String getValidity(BigDecimal countryId, BigDecimal businessComponentId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");

		// dCriteria.add(Restrictions.eq("identityTypeMaster.languageId",languageId));
		dCriteria.add(Restrictions.eq("identityTypeMaster.applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("identityTypeMaster.businessComponentId", businessComponentId));

		return ((IdentityTypeMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getValidity();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceModeDescription> getRemittanceList(BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDesc");

		dCriteria.setFetchMode("remittanceModeDesc.remittanceModeMaster", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDesc.remittanceModeMaster", "remittanceModeMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("remittanceModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		// start by subramanian for asc remittance description
		dCriteria.addOrder(Order.asc("remittanceModeDesc.remittanceDescription"));
		// end by subramanian for asc remittance description

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<RemittanceModeDescription>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> getDeliveryModeList(BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryModeDesc.languageType.languageId", languageId));

		// start by subramanian for asc englishDeliveryName
		dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));
		// end by subramanian for asc englishDeliveryName

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<DeliveryModeDesc>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizComponentDataDesc> getComponentNameIndividual(BigDecimal languageId, BigDecimal componentId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentData");

		dCriteria.setFetchMode("bizcomponentData.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentData.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setFetchMode("bizcomponentData.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentData.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", componentId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BizComponentDataDesc>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BizComponentDataDesc> getComponentNameCorporate(BigDecimal languageId, BigDecimal componentId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizcomponentData");

		dCriteria.setFetchMode("bizcomponentData.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentData.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setFetchMode("bizcomponentData.fsBizComponentData", FetchMode.JOIN);
		dCriteria.createAlias("bizcomponentData.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsBizComponentData.componentDataId", componentId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BizComponentDataDesc>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingCountry> getAllRoutingCountryList(BigDecimal beneficaryCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal bankId) {

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		List<RoutingCountry> routingCountrylist = new ArrayList<RoutingCountry>();

		String sql = "select DISTINCT  * from  EX_BENE_COUNTRY_SERVICE a ,  EX_ROUTING_HEADER b " + "  where   b.COUNTRY_ID  =   a.BENE_COUNTRY_ID  "
				+ " and  b.CURRENCY_ID   =  a.CURRENCY_ID  "
				+ " and  a.SERVICE_MASTER_ID  IN ( SELECT DISTINCT SERVICE_MASTER_ID FROM EX_SERVICE_MASTER WHERE SERVICE_GROUP_ID = " + serviceId
				+ " )" + " and  b.SERVICE_MASTER_ID    =  a.SERVICE_MASTER_ID "
				// + " and  b.DELIVERY_MODE_ID    =  a.DELIVERY_MODE_ID "
				+ " and  a.ISACTIVE = '" + Constants.Yes + "'" + " and  b.ISACTIVE = a.ISACTIVE";

		SQLQuery query = getSession().createSQLQuery(sql).addEntity(RoutingHeader.class);
		List<RoutingHeader> beneficaryCountryList = query.list();
		for (RoutingHeader benfeservie : beneficaryCountryList) {
			if ((benfeservie.getExCountryId().getCountryId().compareTo(beneficaryCountryId) == 0)
					&& (benfeservie.getExCurrenyId().getCurrencyId().compareTo(beneficaryCurrencyId) == 0)) {
				if (!duplicateCheck.contains(benfeservie.getExCountryId().getCountryId())) {
					duplicateCheck.add(benfeservie.getExCountryId().getCountryId());
					RoutingCountry routingCountry = new RoutingCountry(benfeservie.getExCountryId().getCountryId(), benfeservie.getExCountryId()
							.getFsCountryMasterDescs().get(0).getCountryName());
					routingCountrylist.add(routingCountry);
				}
			}
		}

		System.out.println("$$$$$$$routingCountrylist routingCountrylist$$$$$$$$" + routingCountrylist.size());
		return routingCountrylist;
	}

	@SuppressWarnings("unchecked")
	public BigDecimal bankFetchBankIndicator(BigDecimal bankId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBankIndId = (List<BankApplicability>) findAll(dCriteria);

		if (lstBankIndId.size() != 0) {
			return ((List<BankApplicability>) findAll(dCriteria)).get(0).getBankInd().getBankIndicatorId();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingCountry> getAllRoutingBankList(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal bankId) {

		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<RoutingCountry> routingBanklist = new ArrayList<RoutingCountry>();

		String sql = "select DISTINCT  * from  EX_BENE_COUNTRY_SERVICE a ,  EX_ROUTING_HEADER b " + "  where   b.COUNTRY_ID  =   a.BENE_COUNTRY_ID  "
				+ " and  b.CURRENCY_ID   =  a.CURRENCY_ID  "
				+ " and  a.SERVICE_MASTER_ID  IN ( SELECT DISTINCT SERVICE_MASTER_ID FROM EX_SERVICE_MASTER WHERE SERVICE_GROUP_ID = " + serviceId
				+ " )" + " and  b.SERVICE_MASTER_ID    =  a.SERVICE_MASTER_ID "
				// + " and  b.DELIVERY_MODE_ID    =  a.DELIVERY_MODE_ID "
				+ " and  a.ISACTIVE = '" + Constants.Yes + "'" + " and  b.ISACTIVE = a.ISACTIVE";

		SQLQuery query = getSession().createSQLQuery(sql).addEntity(RoutingHeader.class);
		List<RoutingHeader> beneficaryCountryList = query.list();
		for (RoutingHeader benfeservie : beneficaryCountryList) {
			if ((benfeservie.getExCountryId().getCountryId().compareTo(routingCountryId) == 0)
					&& (benfeservie.getExCurrenyId().getCurrencyId().compareTo(beneficaryCurrencyId) == 0)) {
				if (!duplicateCheck.contains(benfeservie.getExRoutingBankId().getBankId())) {
					duplicateCheck.add(benfeservie.getExRoutingBankId().getBankId());
					RoutingCountry routingBank = new RoutingCountry(benfeservie.getExRoutingBankId().getBankId(), benfeservie.getExRoutingBankId()
							.getBankFullName());
					routingBanklist.add(routingBank);
				}
			}
		}

		return routingBanklist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllSpecificList(BigDecimal routingCountryId, BigDecimal beneficaryCurrencyId, BigDecimal serviceId,
			BigDecimal routingbankId) {
		System.out.println("%%%" + routingCountryId + "&&&" + beneficaryCurrencyId + "@@@" + serviceId + "" + routingbankId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exRoutingCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingCountryId", "exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId", routingCountryId));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", beneficaryCurrencyId));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceId));

		dCriteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingBankId", "exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId", routingbankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> beneficaryBankBranchList = (List<RoutingHeader>) findAll(dCriteria);

		return beneficaryBankBranchList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getAllServiceDesc(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");

		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));

		criteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getServiceDesc(BigDecimal languageId, BigDecimal serviceId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");

		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		criteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ServiceMasterDesc> objList = (List<ServiceMasterDesc>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BigDecimal> getAllServiceDescByCountry(BigDecimal countryid) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");

		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("beneCountryId.countryId", countryid));

		criteria.setFetchMode("beneCountryService.serviceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.serviceId", "serviceId", JoinType.INNER_JOIN);

		criteria.setProjection(Projections.distinct(Projections.property("serviceId.serviceId")));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BigDecimal> objList = (List<BigDecimal>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SpecialRateRequest> getSpotRateDetails(BigDecimal customerId, BigDecimal bankId, BigDecimal companyId, BigDecimal documentId,
			BigDecimal financialyear, String documentNo, BigDecimal appcountryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");

		criteria.setFetchMode("specialRateRequest.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("specialRateRequest.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));

		criteria.setFetchMode("specialRateRequest.fsBankMaster", FetchMode.JOIN);
		criteria.createAlias("specialRateRequest.fsBankMaster", "fsBankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBankMaster.bankId", bankId));

		criteria.setFetchMode("specialRateRequest.fsFinanceYear", FetchMode.JOIN);
		criteria.createAlias("specialRateRequest.fsFinanceYear", "fsFinanceYear", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsFinanceYear.financialYearID", financialyear));

		criteria.setFetchMode("specialRateRequest.companyMaster", FetchMode.JOIN);
		criteria.createAlias("specialRateRequest.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));

		criteria.setFetchMode("specialRateRequest.fsDocument", FetchMode.JOIN);
		criteria.createAlias("specialRateRequest.fsDocument", "fsDocument", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsDocument.documentID", documentId));

		criteria.add(Restrictions.eq("specialRateRequest.documentNo", new BigDecimal(documentNo)));

		criteria.add(Restrictions.eq("specialRateRequest.applicationCountryId", appcountryId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<SpecialRateRequest>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getEmployeelist(BigDecimal countryId, BigDecimal branchId, BigDecimal roleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.add(Restrictions.eq("employee.fsRoleMaster.roleId", roleId));
		criteria.add(Restrictions.eq("employee.fsCountryBranch.countryBranchId", branchId));
		criteria.add(Restrictions.eq("employee.countryId", countryId));

		return (List<Employee>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RateAlertFrequency> frequencyDetailsLst(BigDecimal languageId) {
		// TODO Auto-generated method stub
		DetachedCriteria criteria = DetachedCriteria.forClass(RateAlertFrequency.class, "rateAlertFrequency");

		/*
		 * criteria.setFetchMode("rateAlertFrequency.languageType",
		 * FetchMode.JOIN);
		 * criteria.createAlias("rateAlertFrequency.languageType",
		 * "languageType", JoinType.INNER_JOIN);
		 * criteria.add(Restrictions.eq("languageType.languageId", languageId));
		 */

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<RateAlertFrequency>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryMasterDesc> getCountryListExceptAppCountry(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.addOrder(Order.asc("fsCountryMaster.fsCountryMasterDescs"));
		detachedCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId", countryId)));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public Date getValidExpiryDate(String civilId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		criteria.add(Restrictions.eq("customerIdProof.identityInt", civilId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> idproofList = (List<CustomerIdProof>) findAll(criteria);
		if (idproofList.size() > 0) {
			Date idexpiry = null;

			for (CustomerIdProof regIdList : idproofList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					if (regIdList.getIdentityExpiryDate() != null
							&& dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(
									dateformat.parse(dateformat.format(new Date()))) >= 0) {
						idexpiry = regIdList.getIdentityExpiryDate();
						break;
					} else {
						idexpiry = regIdList.getIdentityExpiryDate();
					}
				} catch (ParseException e) {

					e.printStackTrace();
				}
			}
			return idexpiry;
		} else {
			return null;
		}

	}

	@Override
	public List<CustomerIdProof> getCustomerIdBasedOnCivilId(String civilId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");

		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("customerIdProof.identityInt", civilId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> idproofList = (List<CustomerIdProof>) findAll(criteria);
		return idproofList;
	}

	@Override
	public String getCustomerNameBasedOnCustomerId(BigDecimal customerId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "fscustomer");

		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList = (List<Customer>) findAll(criteria);
		return idproofList.get(0).getShortName();
	}

	/*@Override
	public List<BankMaster> getAllBankCodeFromBankMaster(String bankCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.like("bankMaster.bankCode", bankCode, MatchMode.EXACT).ignoreCase());
		criteria.addOrder(Order.asc("bankMaster.bankCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(criteria);
	}*/
	@Override
	public List<BankMaster> getAllBankCodeFromBankMaster(String bankCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class,"bankMaster");
		
		Disjunction lstjunction = Restrictions.disjunction();

		lstjunction.add(Restrictions.like("bankMaster.bankCode", bankCode,MatchMode.ANYWHERE).ignoreCase());
		lstjunction.add(Restrictions.like("bankMaster.bankFullName", bankCode,MatchMode.ANYWHERE).ignoreCase());
		
		criteria.add(lstjunction);
		
		criteria.addOrder(Order.asc("bankMaster.bankCode"));
		
		criteria.setFetchMode("bankMaster.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(criteria);
	}


	@Override
	public String getTelephoneCountryBasedOnNationality(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		List<CountryMaster> countyList = (List<CountryMaster>) findAll(criteria);
		return countyList.get(0).getCountryTelCode();
	}

	@Override
	public BigDecimal getOccupationId(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmploymentInfo");

		criteria.setFetchMode("customerEmploymentInfo.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerEmploymentInfo.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));

		List<CustomerEmploymentInfo> countyList = (List<CustomerEmploymentInfo>) findAll(criteria);
		if (countyList.size() > 0) {
			if (countyList.get(0).getFsBizComponentDataByOccupationId() != null) {
				return countyList.get(0).getFsBizComponentDataByOccupationId().getComponentDataId();
			} else {
				return null;
			}
		} else {
			return null;
		}

	}

	@Override
	public String getOccupationDesc(BigDecimal occupationID, BigDecimal languageId) {
		String customerOccuption = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(BizComponentDataDesc.class, "bizComponentDataDesc");

		criteria.setFetchMode("bizComponentDataDesc.fsBizComponentData", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsBizComponentData", "fsBizComponentData", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsBizComponentData.componentDataId", occupationID));

		criteria.setFetchMode("bizComponentDataDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("bizComponentDataDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		List<BizComponentDataDesc> countyList = (List<BizComponentDataDesc>) findAll(criteria);

		if (countyList.size() != 0) {
			BizComponentDataDesc empOccupation = countyList.get(0);
			if (empOccupation.getDataDesc() != null) {
				customerOccuption = empOccupation.getDataDesc();
			} else {
				customerOccuption = null;
			}
		} else {
			customerOccuption = null;
		}
		return customerOccuption;
	}

	// Start by subramanian
	@Override
	public String getCountryCode(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");

		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));

		return ((CountryMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryCode();
	}

	@Override
	public String getCurrencyCode(BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));

		return ((CurrencyMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCurrencyCode();
	}

	@Override
	public String getBankCode(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		return ((BankMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getBankCode();
	}

	@Override
	public BigDecimal getBankBranchCode(BigDecimal bankBranchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		dCriteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));

		return ((BankBranch) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getBranchCode();
	}

	@Override
	public String getRemittanceCode(BigDecimal remittanceModeId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceMode");

		dCriteria.add(Restrictions.eq("remittanceMode.remittanceModeId", remittanceModeId));

		return ((RemittanceModeMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getRemittance();
	}

	@Override
	public String getDeliveryCode(BigDecimal deliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");

		dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryId));

		return ((DeliveryMode) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getDeliveryMode();
	}

	// End Subramanian

	@SuppressWarnings("unchecked")
	@Override
	public List<Stock> stockByCountryBranch(BigDecimal countryBranchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Stock.class, "stock");

		dCriteria.setFetchMode("stock.countryBranch", FetchMode.JOIN);
		dCriteria.createAlias("stock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));

		dCriteria.setFetchMode("stock.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("stock.currencyId", "currencyId", JoinType.INNER_JOIN);

		return (List<Stock>) findAll(dCriteria);
	}

	@Override
	public void saveOrUpdate(T entity) {

		super.saveOrUpdate(entity);
	}

	// by subramanian for sysdate based on application country
	@Override
	public List<Date> getSysDate(BigDecimal appCountryId) {
		List<Date> objList = new ArrayList<Date>();

		String queryString = "select DF_GET_SYSDATE(?) from dual";

		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, appCountryId).list();

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public Date getSysDateTimestamp(BigDecimal countryId) {

		List<Timestamp> objList = new ArrayList<Timestamp>();

		String queryString = "SELECT TO_TIMESTAMP(TO_CHAR(CAST(DF_GET_SYSDATE(?) AS TIMESTAMP ), 'DD-MM-YY HH24:MI:SS '), 'DD-MM-YY HH24:MI:SS ' )FROM dual";

		objList = getSession().createSQLQuery(queryString).setBigDecimal(0, countryId).list();

		if (objList.isEmpty()) {
			return null;
		} else {
			Timestamp tempTimeStamp = objList.get(0);
			java.sql.Date dat = new java.sql.Date(tempTimeStamp.getTime());
			Date currentTime = DateUtil.convertFromSQLDateToJAVADate(dat);
			return currentTime;
		}

	}

	/*
	 * @Override public Connection getDataSourceFromHibernateSession() {
	 * 
	 * Session session = getSession(); java.sql.Connection connection = null;
	 * try { DataSource ds = SessionFactoryUtils.getDataSource(session
	 * .getSessionFactory()); connection = ds.getConnection();
	 * 
	 * } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } return connection; }
	 */

	@Override
	public List<AccountBalance> getExchangeRateFromAccBal(BigDecimal bankId, BigDecimal currencyId, String accountNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		// dCriteria.add(Restrictions.eq("accountBalance.bankMaster.bankId",
		// bankId));
		dCriteria.add(Restrictions.eq("accountBalance.exCurrencyMaster.currencyId", currencyId));
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountNo));

		return (List<AccountBalance>) findAll(dCriteria);
	}

	public void saveOrUpdateWithPartialSave(T entity) {
		super.saveOrUpdateWithPartialSave(entity);
	}

	@Override
	public List<RoleMaster> getRoleList(BigDecimal roleId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoleMaster.class, "roleMaster");

		dCriteria.add(Restrictions.eq("roleMaster.roleId", roleId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<RoleMaster>) findAll(dCriteria);
	}

	@Override
	public List<CountryMaster> getCountryAlphaList(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");

		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CountryMaster>) findAll(dCriteria);
	}

	@Override
	public BigDecimal getCountryIdBasedOnCountryAlpha2Code(String alphaCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		dCriteria.add(Restrictions.eq("countryMaster.countryAlpha2Code", alphaCode));
		List<CountryMaster> customerList = (List<CountryMaster>) findAll(dCriteria);
		return customerList.get(0).getCountryId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getApplicationIdBasedApplicationCode(String applicationCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RuleApplicationMaster.class, "ruleApplicationMaster");
		dCriteria.add(Restrictions.eq("ruleApplicationMaster.applicationCode", applicationCode));
		List<RuleApplicationMaster> applicationList = (List<RuleApplicationMaster>) findAll(dCriteria);
		return applicationList.get(0).getApplicationId();
	}

	@Override
	public String getCurrencyQuote(BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));

		return ((CurrencyMaster) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getQuoteName();
	}

	@Override
	public String getCurrencyQuoteBasedOnAlphaCode(String alphaTwoCode) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.add(Restrictions.like("currencyMaster.fimsCurrencyCode", alphaTwoCode, MatchMode.EXACT).ignoreCase());
		// dCriteria.add(Restrictions.eq("currencyMaster.fimsCurrencyCode",alphaTwoCode.toString()));
		List<CurrencyMaster> currencyList = (List<CurrencyMaster>) findAll(dCriteria);
		if (currencyList.size() > 0) {
			return currencyList.get(0).getQuoteName();
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrency(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		return (List<CurrencyMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String callGetAmountInTextFunction(String arg1, BigDecimal arg2, BigDecimal arg3) {
		String out = null;
		Connection connection = null;
		try {
			// connection = DBConnection.getdataconnection();;
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_GET_AMT_IN_TEXT_FORMAT (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.setString(2, null);
			cs.setString(3, arg1);
			cs.setBigDecimal(4, arg2);
			cs.setBigDecimal(5, arg3);
			cs.executeUpdate();
			out = cs.getString(1);
			connection.close();
		} catch (SQLException e) {
			LOG.error("Exception occured in GeneralDaoImpl ::callSaleProjection method ", e);
		}
		return out;

	}

	@Override
	public String validateMobileTelephone(String countryAlphaCode, String contactNo, String noType) {
		SQLQuery sqlQuery = super.getSession().createSQLQuery(
				"select CONTACT_NUMBER_VALIDATE(:APPLN_COUNTRY,:CONTACT_NO,:CONTACT_NO_TYPE) as STATUS from Dual");
		sqlQuery.setString("APPLN_COUNTRY", countryAlphaCode);
		sqlQuery.setString("CONTACT_NO", contactNo);
		sqlQuery.setString("CONTACT_NO_TYPE", noType);
		LOG.info("uniqueResult ==" + sqlQuery.uniqueResult().toString());
		return sqlQuery.uniqueResult().toString();

	}

	@Override
	public BigDecimal getDenomiationId(String quoteName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.quoteName", quoteName));
		List<CurrencyMaster> currencyList = (List<CurrencyMaster>) findAll(criteria);
		if (currencyList.size() > 0) {
			return currencyList.get(0).getDecinalNumber();
		} else {
			return null;
		}
	}

	public List<Employee> getEmployeeDetail(BigDecimal employeeId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.add(Restrictions.eq("employee.employeeId", employeeId));

		return (List<Employee>) findAll(criteria);

	}

	@Override
	public List<BankMasterDTO> getCoresBankListForApplicationCountry(BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		// dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",
		// getBankCountry));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
		// 18/12/2014
		// Record
		// Status
		// Condition
		// added

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		// to get Application CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.property("bankMaster.bankId"), "bankId");
		columns.add(Projections.property("bankMaster.bankCode"), "bankCode");
		columns.add(Projections.property("bankMaster.bankFullName"), "bankDecs");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer(new AliasToBeanResultTransformer(BankMasterDTO.class));

		List<BankMasterDTO> lstBankMasterDTO = (List<BankMasterDTO>) findAll(dCriteria);

		// dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return lstBankMasterDTO;
	}

	@Override
	public BigDecimal getCompanyIdFromCode(BigDecimal companyCode) {

		String hqlQuery = "select  a.companyId from  CompanyMaster a  where a.companyCode =  :companyCode";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("companyCode", companyCode);

		List<BigDecimal> lstCompanyId = query.list();

		BigDecimal companyID = BigDecimal.ZERO;
		if (lstCompanyId.size() > 0) {
			companyID = lstCompanyId.get(0);
		}
		return companyID;
	}

	@Override
	public BigDecimal getCountriIdFromCode(String countryCode) {

		String hqlQuery = "select  a.countryId from  CountryMaster a  where a.countryCode =  :countryCode";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("countryCode", countryCode);

		List<BigDecimal> lstCountryID = query.list();

		BigDecimal countryID = BigDecimal.ZERO;
		if (lstCountryID.size() > 0) {
			countryID = lstCountryID.get(0);
		}

		return countryID;
	}

	@Override
	public String getCountryName(String countryCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMaster.countryCode", countryCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMasterDesc> countryNameList = (List<CountryMasterDesc>) findAll(criteria);

		if (countryNameList != null && countryNameList.size() > 0) {
			return countryNameList.get(0).getCountryName();
		} else {
			return null;
		}
	}

	@Override
	public String getBankName(String bankCode) {
		/*
		 * DetachedCriteria criteria =
		 * DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		 * List<BankMaster> bankList= (List<BankMaster>)findAll(criteria);
		 */
		String hqlQuery = "select distinct a.bankFullName from BankMaster a";

		Query query = getSession().createQuery(hqlQuery);

		List<String> bankList = query.list();

		String bankName = null;

		for (String bankMas : bankList) {
			if (bankMas.equalsIgnoreCase(bankCode)) {
				bankName = bankMas;
			}
		}

		return bankName;

	}

	@Override
	public String getCustomerNameCustomerId(BigDecimal customerId) {
		String customerName = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "fscustomer");

		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList = (List<Customer>) findAll(criteria);

		if (idproofList.size() != 0) {
			Customer custData = idproofList.get(0);
			String fname = custData.getFirstName() == null ? "" : custData
					.getFirstName();
			String mname = custData.getMiddleName() == null ? "" : custData
					.getMiddleName();
			String lname = custData.getLastName() == null ? "" : custData
					.getLastName();
			customerName = fname + " " + mname + " " + lname;
		}

		return customerName;
	}

	@Override
	public List<BankMasterDTO> getBeneBankListForApplicationCountry(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		// dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",
		// getBankCountry));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
		// 18/12/2014
		// Record
		// Status
		// Condition
		// added

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		// to get Application CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId));

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.property("bankMaster.bankId"), "bankId");
		columns.add(Projections.property("bankMaster.bankCode"), "bankCode");
		columns.add(Projections.property("bankMaster.bankFullName"), "bankDecs");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer(new AliasToBeanResultTransformer(BankMasterDTO.class));

		List<BankMasterDTO> lstBankMasterDTO = (List<BankMasterDTO>) findAll(dCriteria);

		// dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return lstBankMasterDTO;
	}

	@Override
	public List<ViewBankDetails> getLocalBankListFromView(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewBankDetails.class, "viewBankDetails");
		dCriteria.add(Restrictions.eq("viewBankDetails.applicationCountryId", countryId));
		List<ViewBankDetails> listBankDetails = (List<ViewBankDetails>) findAll(dCriteria);
		return listBankDetails;
	}

	@Override
	public List<ViewActiveCustomerCheck> getActiveCustomerFromView(BigDecimal countryId, BigDecimal customerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewActiveCustomerCheck.class, "viewActiveCutomer");
		dCriteria.add(Restrictions.eq("viewActiveCutomer.customerId", customerId));
		dCriteria.add(Restrictions.eq("viewActiveCutomer.countryId", countryId));
		List<ViewActiveCustomerCheck> activeCustomerList = (List<ViewActiveCustomerCheck>) findAll(dCriteria);
		return activeCustomerList;
	}

	@Override
	public HashMap<String, String> getNextDocumentReferenceNumbers(int countryId, int companyId, int documentId, int dealYear, String processIn,
			BigDecimal countryBranchCode) throws AMGException {
		int docNo = 0;
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO countryId :" + countryId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO companyId :" + companyId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO documentId :" + documentId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO financialYear :" + dealYear);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO processIn :" + processIn);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO branchId :" + countryBranchCode);

		HashMap<String, String> outPutValues = new HashMap<String, String>();

		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setBigDecimal(2, countryBranchCode);
			cs.setInt(3, companyId);
			cs.setInt(4, documentId);
			cs.setInt(5, dealYear);
			cs.setString(6, processIn);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			docNo = cs.getInt(7);
			String errorFlag = cs.getString(8);
			String errorMsg = cs.getString(9);

			outPutValues.put("DOCNO", String.valueOf(docNo));
			outPutValues.put("PROCE_ERORRFLAG", errorFlag);
			outPutValues.put("PROCE_ERORRMSG", errorMsg);

			LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + docNo);

		} catch (SQLException e) {
			throw new AMGException(e.getMessage());

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new AMGException(e.getMessage());
			}
		}

		return outPutValues;
	}

	@Override
	public HashMap<String, String> getNextDocumentRefNumber(int countryId, int companyId, int documentId, int financialYear, String processIn,
			BigDecimal branchId) throws AMGException {

		int docNo = 0;
		// Added by Rabil 18072015
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO countryId :" + countryId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO companyId :" + companyId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO documentId :" + documentId);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO financialYear :" + financialYear);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO processIn :" + processIn);
		LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO branchId :" + branchId);

		HashMap<String, String> outPutValues = new HashMap<String, String>();

		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setBigDecimal(2, branchId);
			cs.setInt(3, companyId);
			cs.setInt(4, documentId);
			cs.setInt(5, financialYear);
			cs.setString(6, processIn);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			docNo = cs.getInt(7);
			String errorFlag = cs.getString(8);
			String errorMsg = cs.getString(9);

			outPutValues.put("DOCNO", String.valueOf(docNo));
			outPutValues.put("PROCE_ERORRFLAG", errorFlag);
			outPutValues.put("PROCE_ERORRMSG", errorMsg);

			LOG.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + docNo);

		} catch (SQLException e) {
			throw new AMGException(e.getMessage());

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new AMGException(e.getMessage());
			}
		}

		return outPutValues;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Date getValidExpiryDateforFCSale(String idNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> idproofList = (List<CustomerIdProof>) findAll(criteria);
		if (idproofList.size() > 0) {
			Date idexpiry = null;
			if (idproofList.size() == 1) {
				return idproofList.get(0).getIdentityExpiryDate();
			}
			for (CustomerIdProof regIdList : idproofList) {
				SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
				try {
					if (regIdList.getIdentityExpiryDate() != null
							&& dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(
									dateformat.parse(dateformat.format(new Date()))) <= 0) {
						idexpiry = idproofList.get(0).getIdentityExpiryDate();
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return idexpiry;
		} else {
			return null;
		}
	}

	@Override
	public List<Customer> getCustomerDeatilsBasedOnRef(BigDecimal custRef) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customerDetails");

		criteria.setFetchMode("customerDetails.fsArticleDetails", FetchMode.JOIN);
		criteria.createAlias("customerDetails.fsArticleDetails", "fsArticleDetails", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("customerReference", custRef));

		// criteria.addOrder(Order.asc("Customer.fsArticleDetails.fsArticleMaster.articleId"));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return (List<Customer>) findAll(criteria);

	}

	@Override
	public List<Employee> getAllEmployeeListBasedonLocation(BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));

		/*
		 * criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		 * criteria.createAlias("employee.fsRoleMaster","fsRoleMaster",
		 * JoinType.INNER_JOIN);
		 * criteria.add(Restrictions.eq("fsRoleMaster.roleId", roleId));
		 */

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}

	@Override
	public List<BankMaster> getBankDetailsList(BigDecimal languageId, BigDecimal countryId, BigDecimal bankId) {
		LOG.info("Entering into getBankDetailsList method");
		LOG.info("languageId == " + languageId);
		LOG.info("countryId == " + countryId);
		LOG.info("bankId == " + bankId);
		String languageInd = languageId.toString();
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		// for banks - LANGUAGE_IND should not check for session language
		// dCriteria.add(Restrictions.eq("languageInd", languageInd));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBankDetailsList method");
		return (List<BankMaster>) findAll(dCriteria);
	}

	@Override
	public List<CountryBranch> getCountryBranchLocCode(BigDecimal countryBranchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		dCriteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		LOG.info("Exit into getBranchDetails method");
		return (List<CountryBranch>) findAll(dCriteria);

	}

	@Override
	public List<ViewBankDetails> getLocalBankListFromView(BigDecimal countryId, BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewBankDetails.class, "viewBankDetails");
		dCriteria.add(Restrictions.eq("viewBankDetails.applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("viewBankDetails.chequeBankId", bankId));
		List<ViewBankDetails> listBankDetails = (List<ViewBankDetails>) findAll(dCriteria);
		return listBankDetails;

	}

	@Override
	public List<CustomerIdproofView> getCustomerIdProofDetailsFromView(BigDecimal customerId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdproofView.class, "customerIdproofView");
		dCriteria.add(Restrictions.eq("customerIdproofView.customerId", customerId));
		List<CustomerIdproofView> listBankDetails = (List<CustomerIdproofView>) findAll(dCriteria);
		return listBankDetails;
	}

	@Override
	public List<BankBranch> getBranchListBasedOnBank(BigDecimal bankid) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankid));
		dCriteria.addOrder(Order.asc("bankBranch.branchFullName"));
		// dCriteria.add(Restrictions.eq("bankBranch.isactive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBankBranchList method");
		return (List<BankBranch>) findAll(dCriteria);

	}

	@Override
	public void saveOrUpdateCustomerAndIdProof(Customer customerinfo, CustomerIdProof idProof) throws Exception {

		if (customerinfo != null && idProof != null) {
			getSession().saveOrUpdate(customerinfo);
			Customer customer = new Customer();
			customer.setCustomerId(customerinfo.getCustomerId());
			idProof.setFsCustomer(customer);
			//getSession().saveOrUpdate(idProof);
			// duplicate check for Customer Id Proof
			List<CustomerIdProof> lstCustomerIdProof = checkIdProofExist(idProof);
			if(lstCustomerIdProof==null || lstCustomerIdProof.size()==0)
			{
				getSession().saveOrUpdate(idProof);
			}
		}

	}

	@Override
	public void saveOrUpdateLoginLogoutHistory(LoginLogoutHistory loginLogoutHistory) {

		getSession().saveOrUpdate(loginLogoutHistory);
	}

	/*
	 * @Override public void saveOrUpdateLoginLogoutHistoryDetails(BigDecimal
	 * countryId, String loginType, String userName, String visitedPage,
	 * Timestamp currentTime) { LoginLogoutHistory loginLogoutHistory = new
	 * LoginLogoutHistory(); loginLogoutHistory.setLoginTime(currentTime);
	 * loginLogoutHistory.setUserName(userName);
	 * loginLogoutHistory.setLoginType(loginType);
	 * loginLogoutHistory.setVisitedPage(visitedPage);
	 * getSession().saveOrUpdate(loginLogoutHistory);
	 * 
	 * }
	 */

	/*@Override
	public void saveOrUpdateLoginLogoutHistoryDetails(BigDecimal countryId,
			String loginType, String userName, String visitedPage,
			Timestamp currentTime) {
		LoginLogoutHistory loginLogoutHistory = new LoginLogoutHistory();
		loginLogoutHistory.setLoginTime(currentTime);
		loginLogoutHistory.setUserName(userName);
		loginLogoutHistory.setLoginType(loginType);
		loginLogoutHistory.setVisitedPage(visitedPage);
		getSession().saveOrUpdate(loginLogoutHistory);
		
		
	}*/
	
	@Override
	public BigDecimal getCurrencyIDFromQuote(String currencyQuote) {
		BigDecimal currencyId=BigDecimal.ZERO;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CurrencyMaster.class, "currencyMaster");
		
		dCriteria.add(Restrictions.eq("currencyMaster.quoteName", currencyQuote));
		List<CurrencyMaster> lstCurrencyMaster=(List<CurrencyMaster>) findAll(dCriteria);
		
		if(lstCurrencyMaster!=null && lstCurrencyMaster.size()!=0)
		{
			currencyId=lstCurrencyMaster.get(0).getCurrencyId();
		}
		return currencyId;
	}

	@Override
	public List<ViewAmtbCouponReport> getListFromAmtbCouponReport(AmtbCouponDT amtb) {
		
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fromDate =null,toDate=null;
		if(amtb.getFromDate()!=null){
		 fromDate = dateFormat.format(amtb.getFromDate());
		}
		if(amtb.getToDate()!=null){
			toDate = dateFormat.format(amtb.getToDate());
			}
		
		
		String hqlSql ="trunc(DOCUMENT_DATE) between to_Date('"+fromDate+"','dd/MM/yyyy') and to_date('"+toDate+"','dd/mm/yyyy')";
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewAmtbCouponReport.class, "viewAmtbCouponReport");
		dCriteria.add(Restrictions.eq("viewAmtbCouponReport.countryBranchId", amtb.getCountryBranchId()));
		if(fromDate!=null && toDate!=null){
			dCriteria.add(Restrictions.sqlRestriction(hqlSql));
		}
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchDetails method");
		return (List<ViewAmtbCouponReport>) findAll(dCriteria);
	}
	
	@Override
	public void saveOrUpdateCustomerAndIdProofAndMobile(Customer customerinfo,
			CustomerIdProof idProof, CustomerMobileLogModel customerMobile)
			throws Exception {
		if(customerinfo !=null && idProof !=null){
			
			// Customer
			getSession().saveOrUpdate(customerinfo);
			
			// Customer Id proof
			Customer customer = new Customer();
			customer.setCustomerId(customerinfo.getCustomerId());
			idProof.setFsCustomer(customer);
			
			List<CustomerIdProof> lstCustomerIdProof = checkIdProofExist(idProof);
			if(lstCustomerIdProof==null || lstCustomerIdProof.size()==0)
			{
				getSession().saveOrUpdate(idProof);
			}
			//getSession().saveOrUpdate(idProof);
			
			// deactivate all mobile number of customer in fs_custome_mobile_log
			DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
			dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", customerinfo.getCustomerId()));
			dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
			List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);

			if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
				for (CustomerMobileLogModel customerMobileLogModel : lstCustomerMobileLog) {
					CustomerMobileLogModel custMobLog = (CustomerMobileLogModel) getSession().get(CustomerMobileLogModel.class, customerMobileLogModel.getCustomerMobileId());
					custMobLog.setIsActive(Constants.D);
					custMobLog.setModifiedBy(sessionStateManage.getUserName());
					custMobLog.setModifiedDate(new Date());
					getSession().update(custMobLog);
				}
			}
			
			// Customer Mobile
			customerMobile.setCustomerId(customerinfo.getCustomerId());
			customerMobile.setCustomerReference(customerinfo.getCustomerReference());
			getSession().saveOrUpdate(customerMobile);
		}
		
	}

	@Override
	public void saveOrUpdateCustomerAndMobile(Customer customerinfo,
			CustomerMobileLogModel customerMobile) throws Exception {
		if(customerinfo !=null && customerMobile !=null){
			
			// Customer
			getSession().saveOrUpdate(customerinfo);
			
			// deactivate all mobile number of customer in fs_custome_mobile_log
			DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerMobileLogModel.class, "customerMobileLogModel");
			dCriteria.add(Restrictions.eq("customerMobileLogModel.customerId", customerinfo.getCustomerId()));
			dCriteria.add(Restrictions.eq("customerMobileLogModel.isActive", Constants.Yes));
			List<CustomerMobileLogModel> lstCustomerMobileLog = (List<CustomerMobileLogModel>) findAll(dCriteria);
			
			if(lstCustomerMobileLog != null && lstCustomerMobileLog.size() != 0){
				for (CustomerMobileLogModel customerMobileLogModel : lstCustomerMobileLog) {
					CustomerMobileLogModel custMobLog = (CustomerMobileLogModel) getSession().get(CustomerMobileLogModel.class, customerMobileLogModel.getCustomerMobileId());
					custMobLog.setIsActive(Constants.D);
					custMobLog.setModifiedBy(sessionStateManage.getUserName());
					custMobLog.setModifiedDate(new Date());
					getSession().update(custMobLog);
				}
			}
			
			// Customer Mobile
			customerMobile.setCustomerId(customerinfo.getCustomerId());
			customerMobile.setCustomerReference(customerinfo.getCustomerReference());
			getSession().saveOrUpdate(customerMobile);
		}
	}
	
	public List<CustomerIdProof> checkIdProofExist(CustomerIdProof idProof)
	{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		
		if(idProof.getFsCustomer() != null){
			dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsCustomer.customerId", idProof.getFsCustomer().getCustomerId()));
		}
		
		if(idProof.getFsLanguageType()!=null)
		{
			dCriteria.setFetchMode("customerIdProof.fsLanguageType", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsLanguageType.languageId", idProof.getFsLanguageType().getLanguageId()));
		}
		
		if(idProof.getFsBizComponentDataByCustomerTypeId()!=null)
		{
			dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsBizComponentDataByCustomerTypeId.componentDataId", idProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId()));
		}
		
		if(idProof.getFsBizComponentDataByIdentityFor()!=null)
		{
			dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityFor", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityFor", "fsBizComponentDataByIdentityFor", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityFor.componentDataId", idProof.getFsBizComponentDataByIdentityFor().getComponentDataId()));
		}
		
		if(idProof.getFsBizComponentDataByIdentityTypeId()!=null)
		{
			dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", idProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
		}
		
		dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idProof.getIdentityInt()));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	
		List<CustomerIdProof> lstCustomerIdProof=(List<CustomerIdProof>) findAll(dCriteria);
		
		return lstCustomerIdProof;
	}

	@Override
	public List<Employee> getEmployeeDetailByEmpID(BigDecimal employeeId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		
		criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("employee.employeeId", employeeId));

		return (List<Employee>) findAll(criteria);

	}

	@Override
	public boolean checkCorporateUser(BigDecimal countryId,BigDecimal countryBranchId, String userName) {

		LOG.info("Entering into getBranchDetailsFromBeneStatus method");
		LOG.info("countryId == " + countryId);
		LOG.info("countryBranchId == " + countryBranchId);
		boolean checkStatus = Boolean.TRUE;
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryBranch> lstToCountryBranch = (List<CountryBranch>) findAll(dCriteria);
		if(lstToCountryBranch != null && lstToCountryBranch.size() != 0){
			CountryBranch countryBranch = lstToCountryBranch.get(0);
			if(countryBranch.getCorporateStatus() != null && countryBranch.getCorporateStatus().equalsIgnoreCase(Constants.Yes)){
				checkStatus = Boolean.FALSE;
			}
		}
		
		if(checkStatus){
			DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
			criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
			criteria.createAlias("employee.fsRoleMaster", "fsRoleMaster", JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("employee.userName", userName));
			criteria.add(Restrictions.eq("employee.employeeId", sessionStateManage.getEmployeeId()));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<Employee> lstToEmployee = (List<Employee>) findAll(criteria);
			if(lstToEmployee != null && lstToEmployee.size() != 0){
				Employee employee = lstToEmployee.get(0);
				if(employee.getFsRoleMaster() != null && employee.getFsRoleMaster().getRoleName().trim().equalsIgnoreCase(Constants.USER_ROLE_CORPORATE)){
					checkStatus = Boolean.FALSE;
				}
			}
		}
		
		
		LOG.info("Exit into getBranchDetailsFromBeneStatus method");
		
		return checkStatus;
	}
	
	
 
}