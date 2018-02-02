package com.amg.exchange.currency.inquiry.daoImpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cashtransfer.bean.CashTransferForLToLBean;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.currency.inquiry.dao.ICurrencyEnquiryDao;
import com.amg.exchange.currency.inquiry.model.BranchDayTransactionView;
import com.amg.exchange.currency.inquiry.model.BranchWiseCurrencyStock;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class CurrencyEnquiryDaoImpl extends CommonDaoImpl implements ICurrencyEnquiryDao {

	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger(CashTransferForLToLBean.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getAllCashierList(BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));
		 criteria.addOrder(Order.asc("employee.employeeName"));

		/*
		 * criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		 * criteria.createAlias("employee.fsRoleMaster","fsRoleMaster",
		 * JoinType.INNER_JOIN);
		 * criteria.add(Restrictions.eq("fsRoleMaster.roleId", roleId));
		 */

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	public List<CurrencyMasterDTO> getCurrencyListByUser(BigDecimal branchId, String userName) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserStockView.class, "userStockView");

		
		criteria.add(Restrictions.eq("userStockView.countryBranchId", branchId));
		criteria.add(Restrictions.eq("userStockView.oracleUser", userName));
ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.distinct(Projections.property("userStockView.currencyId")), "currencyId");
		//columns.add(Projections.property("userStockView.currencyCode"),"currencyCode");
		columns.add(Projections.property("userStockView.currencyName"), "currencyDecs");
		//columns.add(Projections.distinct(Projections.property("currencyId.currencyId")), "currencyId"));
		
		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(CurrencyMasterDTO.class) );
		
		List<CurrencyMasterDTO> lstCurrencyMasterDTO=  (List<CurrencyMasterDTO>) findAll(criteria);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return lstCurrencyMasterDTO;
	}

	@SuppressWarnings("unchecked")
	public List<UserStockView> getDenominationListByUserCurrency(BigDecimal branchId, String userName, BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserStockView.class, "userStockView");

		// criteria.setProjection(Projections.distinct(Projections.property("userStockView.currencyId")));

		criteria.add(Restrictions.eq("userStockView.countryBranchId", branchId));
		criteria.add(Restrictions.eq("userStockView.oracleUser", userName));
		criteria.add(Restrictions.eq("userStockView.currencyId", currencyId));

		criteria.addOrder(Order.desc("userStockView.denominationId"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<UserStockView>) findAll(criteria);
	}

	@Override
	public List<CurrencyMaster> getCurrencyList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		criteria.addOrder(Order.asc("currencyMaster.currencyName"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<CurrencyMaster> objList = (List<CurrencyMaster>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<BranchWiseCurrencyStock> getBrachWiseCurrencyStockList(BigDecimal countryBranchId, BigDecimal currencyId, BigDecimal denomId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BranchWiseCurrencyStock.class, "branchWiseCurrencyStock");

		List<BigDecimal> currencyCodelist = new ArrayList<BigDecimal>();
		currencyCodelist.add(BigDecimal.ZERO);
		currencyCodelist.add(currencyId);

		// criteria.add(Restrictions.in("branchWiseCurrencyStock.currencyId",
		// currencyCodelist));

		criteria.add(Restrictions.eq("branchWiseCurrencyStock.countryBranchId", countryBranchId));
		criteria.add(Restrictions.eq("branchWiseCurrencyStock.currencyId", currencyId));
		criteria.add(Restrictions.eq("branchWiseCurrencyStock.denominationAmount", denomId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<BranchWiseCurrencyStock> objList = (List<BranchWiseCurrencyStock>) findAll(criteria);

		return objList;

	}

	public List<CountryBranch> getCountryBranchByCurrency() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		criteria.addOrder(Order.asc("countryBranch.countryBranchId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<CurrencyWiseDenomination> getDenomonationByCurrency(BigDecimal currencyId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		criteria.add(Restrictions.eq("currencyWiseDenomination.exCurrencyMaster.currencyId", currencyId));
		criteria.addOrder(Order.desc("currencyWiseDenomination.denominationId"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		@SuppressWarnings("unchecked")
		List<CurrencyWiseDenomination> objList = (List<CurrencyWiseDenomination>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BranchDayTransactionView> getBranchDayTransaction(HashMap<String, Object> lstbranchDayTransactionInput) {

		BigDecimal countryBranchId = (BigDecimal) lstbranchDayTransactionInput.get("COUNTRY_BRANCH_ID");
		BigDecimal customerId = (BigDecimal) lstbranchDayTransactionInput.get("CUSTOMER_ID");
		Date transactionDate = (Date) lstbranchDayTransactionInput.get("TRANSACTION_DATE");
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String documentDate = dateformat.format(transactionDate);
		String sql = " ACCOUNT_MMYYYY = trunc(TO_DATE('" + documentDate + "','dd/MM/yy'),'MONTH')";
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BranchDayTransactionView.class, "branchDayTransactionView");
		
		criteria.add(Restrictions.sqlRestriction("TRUNC(DOCUMENT_DATE) = TO_DATE('" + documentDate + "','dd/MM/yy')"));
		criteria.add(Restrictions.sqlRestriction(sql));
		criteria.add(Restrictions.eq("branchDayTransactionView.countryBranchId", countryBranchId));

		if (customerId != null)
			criteria.add(Restrictions.eq("branchDayTransactionView.employeeId", customerId));

		List<BranchDayTransactionView> lstDetails = (List<BranchDayTransactionView>) findAll(criteria);

		if (lstDetails.isEmpty())
			return null;

		return lstDetails;

	}
	
	@Override
	public List<CollectDetail> getBranchDayCollectionDetail(BigDecimal collectionDocNo, BigDecimal collectionDocCode, BigDecimal collectionDocfinanceYear) {
		
		

		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetail.class, "collectDetail");

		criteria.add(Restrictions.eq("collectDetail.documentNo", collectionDocNo));

		criteria.add(Restrictions.eq("collectDetail.documentCode", collectionDocCode));

		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", collectionDocfinanceYear));
		
		/*criteria.setFetchMode("collectDetail.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("collectDetail.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		*/

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		List<CollectDetail> objList = (List<CollectDetail>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Employee> getCashierList(BigDecimal branchId,BigDecimal roleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");

		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));

		
		 criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		 criteria.createAlias("employee.fsRoleMaster","fsRoleMaster",JoinType.INNER_JOIN);
		 criteria.add(Restrictions.eq("fsRoleMaster.roleId", roleId));
		 criteria.addOrder(Order.asc("employee.employeeName"));
		 
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>) findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getBranchDetails(BigDecimal countryId,BigDecimal countryBranchId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CountryBranch.class, "countryBranch");
		dCriteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryBranch.countryMaster", "countryMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		
		dCriteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		dCriteria.addOrder(Order.asc("countryBranch.branchName"));
		return (List<CountryBranch>) findAll(dCriteria);
	}
	
	

}
