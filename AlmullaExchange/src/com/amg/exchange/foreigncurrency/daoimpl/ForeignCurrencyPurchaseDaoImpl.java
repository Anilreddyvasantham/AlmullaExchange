package com.amg.exchange.foreigncurrency.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.foreigncurrency.dao.IForeignCurrencyPurchaseDao;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyAdjustView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustApp;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyPurchaseReport;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentView;
import com.amg.exchange.foreigncurrency.model.RoleWiseExchangeRate;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@Repository
public class ForeignCurrencyPurchaseDaoImpl<T> extends CommonDaoImpl<T> implements IForeignCurrencyPurchaseDao<T>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final Logger log=Logger.getLogger(ForeignCurrencyPurchaseDaoImpl.class);

	@SuppressWarnings("unchecked")
	public List<CurrencyMaster> getAllCurrency(BigDecimal countryId){
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		detachedCriteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId", countryId)));
		detachedCriteria.add(Restrictions.isNotNull("currencyMaster.currencyCode"));
		detachedCriteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		detachedCriteria.add(Restrictions.eq("currencyMaster.isactive", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyMaster>)findAll(detachedCriteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyWiseDenomination> getDenominationByCurrencyID(BigDecimal currencyId) {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(CurrencyWiseDenomination.class,"currencyWiseDenomination");

		detachedCriteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster",JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));

		// To Make it in order of denominationDesc @ Chiranjeevi
		detachedCriteria.addOrder(Order.desc("currencyWiseDenomination.denominationAmount"));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyWiseDenomination>)findAll(detachedCriteria); 

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceOfIncome> getAllSourceOfIncome() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SourceOfIncome.class, "sourceOfIncome");
		detachedCriteria.add(Restrictions.eq("sourceOfIncome.isActive", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<SourceOfIncome>)findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PurposeOfTransaction> getAllPurposeOfTransaction() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(PurposeOfTransaction.class);
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<PurposeOfTransaction>)findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> dataCust(String id) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", id));
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		/*criteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
			criteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);*/
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerIdProof>) findAll(criteria);

	}

	@Override
	public void delete(T entity) {
		super.delete(entity);

	}


	public void save(T entity){
		super.save(entity);
	}

	@Override
	public void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
	}

	@Override
	public void saveCollect(Collect collect) {
		getSession().save(collect);
	}











	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public List<UserFinancialYear> getUserFinancialYear(Date currentDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			Date S = formatter.parse(formatter.format(currentDate));
			System.out.println("Today : " + S);

			if(currentDate!=null){

				detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", S))
				.add(Restrictions.ge("userFinancialYear.financialYearEnd", S));

			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return (List<UserFinancialYear>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyById(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
	}

	/*@SuppressWarnings("unchecked")
	@Override
	public List<String> getFsAvg(Object obj, BigDecimal currencyId) {
		List<String> data1 = new ArrayList<String>();
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class, "currencyOtherInformation");
		criteria.add(Restrictions.eq("currencyOtherInformation.exCurrencyMaster.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyOtherInformation> data = criteria.getExecutableCriteria(getSession()).list();


		if((Double.parseDouble(obj.toString()) > data.get(0).getFundMaxRate().doubleValue()) || (Double.parseDouble(obj.toString()) < data.get(0).getFundMinRate().doubleValue())) {
			data1.add(Constants.Yes);
			data1.add(data.get(0).getFundMinRate().toPlainString());
			data1.add(data.get(0).getFundMaxRate().toPlainString());
			return data1;
		} else {
			data1.add(Constants.No);
			data1.add(data.get(0).getFundMinRate().toPlainString());
			data1.add(data.get(0).getFundMaxRate().toPlainString());
			return data1;
		}

	}*/

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getFsAvg(Object obj, BigDecimal currencyId) {
		List<String> data1 = new ArrayList<String>();

		DetachedCriteria criteria = DetachedCriteria.forClass(CashRate.class, "currencyOtherInformation");

		criteria.add(Restrictions.eq("currencyOtherInformation.alternateCurrencyId.currencyId", currencyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CashRate> data = criteria.getExecutableCriteria(getSession()).list();

		if(data.size()>0){	 
			if((Double.parseDouble(obj.toString()) > data.get(0).getBuyRateMax().doubleValue()) || (Double.parseDouble(obj.toString()) < data.get(0).getBuyRateMin().doubleValue())) {
				data1.add(Constants.Yes);
				data1.add(data.get(0).getBuyRateMax().toPlainString());
				data1.add(data.get(0).getBuyRateMin().toPlainString());
				return data1;
			} else {
				data1.add(Constants.No);
				data1.add(data.get(0).getBuyRateMax().toPlainString());
				data1.add(data.get(0).getBuyRateMin().toPlainString());
				return data1;
			}
		}else{
			return null;
		}

	}

	/*@Override
	public String getNextDocumentSerialNumber(int countryId, int companyId,
			int documentId, int financialYear,String processIn) {
		int out=0;

		String a=null;
		String b=null;
		SQLQuery sqlQuery = super.getSession().createSQLQuery(" { call DP_FS_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?)}  ");
		sqlQuery.setInteger(0, countryId);
		sqlQuery.setInteger(1, companyId);
		sqlQuery.setInteger(2, documentId);
		sqlQuery.setInteger(3, financialYear);
		sqlQuery.setString(4, processIn);
		sqlQuery.setParameter(5, out);
		sqlQuery.setParameter(6, "");
		sqlQuery.setParameter(7, "");

		sqlQuery.executeUpdate();

		log.info("getNextDocumentSerialNumber  output :"+out+":a:"+a+":b:"+b);

		Connection connection=null;

			//connection = DBConnection.getdataconnection();
			connection= getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setInt(2, companyId);
			cs.setInt(3, documentId);
			cs.setInt(4, financialYear);
			cs.setString(5, processIn);
			cs.registerOutParameter(6, java.sql.Types.INTEGER);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			//cs.executeUpdate();
			cs.execute();
			out =cs.getInt(6);
			String a=cs.getString(7);
			String b=cs.getString(8);
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return String.valueOf(out);
	}*/

	// Rolewise Exchange Rate by subramanian 04-Dec-2014


	@SuppressWarnings("unchecked")
	@Override	
	public List<CashRate> getRoleWiseExchangeRateByRoleId(BigDecimal locationId, BigDecimal saleCurrencyId) {
		log.info("Entering into getRoleWiseExchangeRateByRoleId method");
		log.info("locationId " + locationId);
		log.info("saleCurrencyId " + saleCurrencyId);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CashRate.class, "cashRate");
		detachedCriteria.setFetchMode("cashRate.countryBranchId", FetchMode.JOIN);
		detachedCriteria.createAlias("cashRate.countryBranchId", "location", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("location.countryBranchId", locationId));
		detachedCriteria.setFetchMode("cashRate.alternateCurrencyId", FetchMode.JOIN);
		detachedCriteria.createAlias("cashRate.alternateCurrencyId", "alternateCurrencyId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("alternateCurrencyId.currencyId", saleCurrencyId));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CashRate> lstCashRate = (List<CashRate>) findAll(detachedCriteria);
		// return null;

		log.info("Exit into getRoleWiseExchangeRateByRoleId method");
		return lstCashRate;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getBetweenRolewiseExchangeRate(Object obj, BigDecimal roleId){

		List<String> data1 = new ArrayList<String>();

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(RoleWiseExchangeRate.class, "roleWiseExchangeRate");

		detachedCriteria.setFetchMode("roleWiseExchangeRate.role", FetchMode.JOIN);
		detachedCriteria.createAlias("roleWiseExchangeRate.role", "roleExchangeRate", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("roleExchangeRate.roleId", roleId));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoleWiseExchangeRate> data = detachedCriteria.getExecutableCriteria(getSession()).list();

		if((Double.parseDouble(obj.toString()) > data.get(0).getRoleMaxRate().doubleValue()) || (Double.parseDouble(obj.toString()) < data.get(0).getRoleMinRate().doubleValue())) {
			data1.add(Constants.Yes);
			data1.add(data.get(0).getRoleMinRate().toPlainString());
			data1.add(data.get(0).getRoleMaxRate().toPlainString());
			return data1;
		} else {
			data1.add(Constants.No);
			data1.add(data.get(0).getRoleMinRate().toPlainString());
			data1.add(data.get(0).getRoleMaxRate().toPlainString());
			return data1;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getSignature(BigDecimal id) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class,"customer");
		detachedCriteria.add(Restrictions.eq("customer.customerId", id));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> data = (List<Customer>) findAll(detachedCriteria);
		return data.get(0).getSignatureSpecimen();

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyWiseDenomination> getDenominationByCountryIDCurrencyID(
			BigDecimal countryId, BigDecimal currencyId) {
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(CurrencyWiseDenomination.class,"currencyWiseDenomination");

		detachedCriteria.setFetchMode("currencyWiseDenomination.exCurrencyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("currencyWiseDenomination.exCurrencyMaster", "exCurrencyMaster",JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));

		detachedCriteria.setFetchMode("currencyWiseDenomination.fsCountryMasterr", FetchMode.JOIN);
		detachedCriteria.createAlias("currencyWiseDenomination.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		// To Make it in order of denominationDesc @ Chiranjeevi
		detachedCriteria.addOrder(Order.desc("currencyWiseDenomination.denominationAmount"));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyWiseDenomination>)findAll(detachedCriteria);  

	}

	@Override
	public List<ForeignCurrencyAdjustApp> getAllValuesForReportGenaration(BigDecimal customerIdNumber,String documentId, BigDecimal docYear) {


		DetachedCriteria criteria=DetachedCriteria.forClass(ForeignCurrencyAdjustApp.class,"foreignCurrencyAdjustApp");

		/*criteria.setFetchMode("foreignCurrencyAdjustApp.fsDenominationId", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsDenominationId", "fsDenominationId",JoinType.INNER_JOIN);

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsBankBranchMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsBankBranchMaster", "fsBankBranchMaster",JoinType.INNER_JOIN);

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCurrencyMaster", "fsCurrencyMaster",JoinType.INNER_JOIN);*/

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCustomer.customerId", customerIdNumber));
		
		criteria.add(Restrictions.eq("foreignCurrencyAdjustApp.documentFinanceYear", docYear.intValue()));

		criteria.add(Restrictions.eq("foreignCurrencyAdjustApp.documentNo", Integer.parseInt(documentId)));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList=(List<ForeignCurrencyAdjustApp>) findAll(criteria);

		return foreignCurrencyAdjustAppList;
	}

	@Override
	public List<ReceiptPaymentApp> getReceiptPaymentForReportGeneration(BigDecimal documentNo) {

		DetachedCriteria criteria=DetachedCriteria.forClass(ReceiptPaymentApp.class,"receiptPaymentApp");

		/*criteria.setFetchMode("receiptPaymentApp.foreignFsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.foreignFsCountryMaster", "foreignFsCountryMaster",JoinType.INNER_JOIN);*/

		//criteria.add(Restrictions.eq("receiptPaymentApp.countryId", appcountryId));
		//criteria.add(Restrictions.eq("receiptPaymentApp.companyId", companyId));
		//criteria.add(Restrictions.eq("receiptPaymentApp.documentCode", documentcode));
		//criteria.add(Restrictions.eq("receiptPaymentApp.documentFinanceYear", financialYr));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", documentNo));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<ReceiptPaymentApp>) findAll(criteria);
	}

	@Override
	public BigDecimal getLocalCurrencyId(BigDecimal countryId){

		DetachedCriteria criteria=DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster");

		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> currencyList=(List<CurrencyMaster>) findAll(criteria);
		return currencyList.get(0).getCurrencyId();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<ForeignCurrencyPurchaseReport> getFcPurchaseReportList(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentcode, BigDecimal financialYr, BigDecimal documentNo) {

		DetachedCriteria criteria=DetachedCriteria.forClass(ForeignCurrencyPurchaseReport.class,"foreignCurrencyPurchaseReport");

		criteria.add(Restrictions.eq("foreignCurrencyPurchaseReport.applicationCountryId", appcountryId));
		criteria.add(Restrictions.eq("foreignCurrencyPurchaseReport.companyID", companyId));
		criteria.add(Restrictions.eq("foreignCurrencyPurchaseReport.documentCode", documentcode));
		criteria.add(Restrictions.eq("foreignCurrencyPurchaseReport.docFynYear", financialYr));
		criteria.add(Restrictions.eq("foreignCurrencyPurchaseReport.documentNo", documentNo));

		List<ForeignCurrencyPurchaseReport> reportList=(List<ForeignCurrencyPurchaseReport>) findAll(criteria);	

		return reportList;

	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CollectDetail> getCustomerEnquery(BigDecimal documentYear,BigDecimal documentNo) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CollectDetail.class,"collectDetail");
		// get collect data
		criteria.setFetchMode("collectDetail.cashCollectionId", FetchMode.JOIN);
		criteria.createAlias("collectDetail.cashCollectionId", "cashCollectionId",JoinType.INNER_JOIN);
		//get branch
		criteria.setFetchMode("collectDetail.exBankBranch", FetchMode.JOIN);
		criteria.createAlias("collectDetail.exBankBranch", "exBankBranch",JoinType.INNER_JOIN);
		//currency
		criteria.setFetchMode("collectDetail.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("collectDetail.exCurrencyMaster", "exCurrencyMaster",JoinType.INNER_JOIN);
		//customer Details
		criteria.setFetchMode("collectDetail.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("collectDetail.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", documentYear));
		criteria.add(Restrictions.eq("collectDetail.documentNo", documentNo));
		criteria.add(Restrictions.eq("collectDetail.documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_COLLECT_TRANSACTION)));
		return (List<CollectDetail>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinancialYear> getAllDocumentYear() {
		DetachedCriteria criteria=DetachedCriteria.forClass(UserFinancialYear.class,"userFinancialYear");
		criteria.addOrder(Order.desc("userFinancialYear.financialYear"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<UserFinancialYear>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getCustomerAllDetails(BigDecimal customerId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Customer.class,"customer");
		criteria.add(Restrictions.eq("customer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptPayment> getReceipetData(BigDecimal documentFinanceYear,BigDecimal documentNo) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ReceiptPayment.class,"receiptPayment");
		//SourceId
		criteria.setFetchMode("receiptPayment.sourceOfIncome", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.sourceOfIncome", "sourceOfIncome",JoinType.INNER_JOIN);
		//purposeId
		criteria.setFetchMode("receiptPayment.purposeOfTransaction", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.purposeOfTransaction", "purposeOfTransaction",JoinType.INNER_JOIN);
		//Forigien Currency
		criteria.setFetchMode("receiptPayment.foreignFsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.foreignFsCountryMaster", "foreignFsCountryMaster",JoinType.INNER_JOIN);
		//Local Currency
		criteria.setFetchMode("receiptPayment.localFsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.localFsCountryMaster", "localFsCountryMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("receiptPayment.countryBranch", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.countryBranch", "countryBranch",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentFinanceYear));
		criteria.add(Restrictions.eq("receiptPayment.documentNo", documentNo));
		return (List<ReceiptPayment>) findAll(criteria);
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<ReceiptPaymentApp> getReceipetDataApp(BigDecimal documentFinanceYear,BigDecimal documentNo) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ReceiptPaymentApp.class,"receiptPaymentApp");
		/*//SourceId
		criteria.setFetchMode("receiptPaymentApp.sourceOfIncome", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.sourceOfIncome", "sourceOfIncome",JoinType.INNER_JOIN);
		//purposeId
		criteria.setFetchMode("receiptPaymentApp.purposeOfTransaction", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.purposeOfTransaction", "purposeOfTransaction",JoinType.INNER_JOIN);
		//Forigien Currency
		criteria.setFetchMode("receiptPaymentApp.foreignFsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.foreignFsCountryMaster", "foreignFsCountryMaster",JoinType.INNER_JOIN);
		//Local Currency
		criteria.setFetchMode("receiptPaymentApp.localFsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.localFsCountryMaster", "localFsCountryMaster",JoinType.INNER_JOIN);*/

		criteria.add(Restrictions.eq("receiptPaymentApp.documentFinanceYear", documentFinanceYear));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", documentNo));
		return (List<ReceiptPaymentApp>) findAll(criteria);
	}

	@Override
	public List<ForeignCurrencyAdjustApp> getCurrencyAdjustRecords(
			BigDecimal documentFinanceYear, BigDecimal documentNo) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ForeignCurrencyAdjustApp.class,"foreignCurrencyAdjustApp");

		criteria.setFetchMode("foreignCurrencyAdjustApp.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjustApp.fsCustomer", "fsCustomer",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("foreignCurrencyAdjustApp.documentFinanceYear", documentFinanceYear.intValue()));
		criteria.add(Restrictions.eq("foreignCurrencyAdjustApp.documentNo", documentNo.intValue()));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ForeignCurrencyAdjustApp> foreignCurrencyAdjustAppList=(List<ForeignCurrencyAdjustApp>) findAll(criteria);

		return foreignCurrencyAdjustAppList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SourceOfIncomeDescription> getSourceofIncome(BigDecimal languageId) {

		log.info("Entering into getSourceofIncome method");
		log.info("languageId "  + languageId);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		dCriteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncomeId", JoinType.INNER_JOIN);


		dCriteria.setFetchMode("sourceOfIncomeDescription.languageId", FetchMode.JOIN);
		dCriteria.createAlias("sourceOfIncomeDescription.languageId", "fsLanguageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		log.info("Exit into getSourceofIncome method");
		return (List<SourceOfIncomeDescription>)findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CollectDetail> getFCPurchageCollectionDetails(String documentCode, BigDecimal documentFinanceYear, BigDecimal documentNo) {
		log.info("Entering  into getFCPurchageCollectionDetails method");
		log.info("documentCode " + documentCode);
		log.info("documentFinanceYear " + documentFinanceYear);
		log.info("documentNo " + documentNo);

		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetail.class, "collectDetail");
		// get collect data
		criteria.setFetchMode("collectDetail.cashCollectionId", FetchMode.JOIN);
		criteria.createAlias("collectDetail.cashCollectionId", "cashCollectionId", JoinType.INNER_JOIN);
		// get branch
		criteria.setFetchMode("collectDetail.exBankBranch", FetchMode.JOIN);
		criteria.createAlias("collectDetail.exBankBranch", "exBankBranch", JoinType.INNER_JOIN);
		// currency
		criteria.setFetchMode("collectDetail.exCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("collectDetail.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);
		// customer Details
		criteria.setFetchMode("collectDetail.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("collectDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", documentFinanceYear));
		criteria.add(Restrictions.eq("collectDetail.documentNo", documentNo));
		criteria.add(Restrictions.eq("collectDetail.documentCode", new BigDecimal(documentCode)));
		log.info("Exit into getFCPurchageCollectionDetails method");
		return (List<CollectDetail>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ForeignCurrencyAdjust> getforeignCurrencyAdjustList(BigDecimal collectionId) {
		log.info("Entering  into getforeignCurrencyAdjustList method");
		log.info("documentCode " + collectionId);
		DetachedCriteria criteria = DetachedCriteria.forClass(ForeignCurrencyAdjust.class, "foreignCurrencyAdjust");
		criteria.setFetchMode("foreignCurrencyAdjust.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsCustomer", "Customer", JoinType.INNER_JOIN);
		criteria.setFetchMode("foreignCurrencyAdjust.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsCountryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("foreignCurrencyAdjust.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsCompanyMaster", "companyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("foreignCurrencyAdjust.countryBranch", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.setFetchMode("foreignCurrencyAdjust.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsCurrencyMaster", "currencyMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("foreignCurrencyAdjust.fsDenominationId", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsDenominationId", "denomination", JoinType.INNER_JOIN);

		criteria.setFetchMode("foreignCurrencyAdjust.collect", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.collect", "collect", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("collect.collectionId", collectionId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		log.info("Exit  into getforeignCurrencyAdjustList method");
		return (List<ForeignCurrencyAdjust>) findAll(criteria);
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String getsourceofIncomeDesc(BigDecimal languageId, BigDecimal sourceId) {
		log.info("Entering  into getsourceofIncomeDesc method");
		DetachedCriteria criteria = DetachedCriteria.forClass(SourceOfIncomeDescription.class, "sourceOfIncomeDescription");
		criteria.setFetchMode("sourceOfIncomeDescription.sourceOfIncomeId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.sourceOfIncomeId", "sourceOfIncome", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("sourceOfIncome.sourceId", sourceId));
		criteria.setFetchMode("sourceOfIncomeDescription.languageId", FetchMode.JOIN);
		criteria.createAlias("sourceOfIncomeDescription.languageId", "language", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("language.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<SourceOfIncomeDescription> list = (List<SourceOfIncomeDescription>) findAll(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0).getSourceOfIncomeFullDesc();
		}
		log.info("Exit  into getsourceofIncomeDesc method");
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getPurposeofTransaction(BigDecimal purposeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfTransaction.class, "purposeOfTransaction");
		criteria.add(Restrictions.eq("purposeOfTransaction.purposeId", purposeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PurposeOfTransaction> list = (List<PurposeOfTransaction>) findAll(criteria);

		if(list!= null && !list.isEmpty())
		{
			return list.get(0).getPurposeFullDesc();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getcountrybasedonCurrency(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		criteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMaster> list = (List<CurrencyMaster>) findAll(criteria);
		if (list != null) {
			return list.get(0).getFsCountryMaster().getCountryId();
		}
		return null;
	}

	@Override
	public  String getInsertEmosFcPurchase(BigDecimal appcountryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYr, BigDecimal documentNo) throws AMGException {

		log.info("Procedure Name =  EX_INSERT_EMOS_FC_PURCHASE  ");
		log.info("!!!!!!appcountryId!!!!!!!!!" + appcountryId);
		log.info("!!!!!!companyId!!!!!!!!!" + companyId);
		log.info("!!!!!!documentId!!!!!!!!!" + documentId);
		log.info("!!!!!!financialYr!!!!!!!!!" + financialYr);
		log.info("!!!!!!documentNo!!!!!!!!!" + documentNo);
		String outMessage = new String();
		Connection connection = null;
		CallableStatement cs;
		try {

			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_INSERT_EMOS_FC_PURCHASE (?, ?, ?, ?, ?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appcountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYr);
			cs.setBigDecimal(5, documentNo);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.execute();
			outMessage = cs.getString(6);
			log.info("outMessage =" + outMessage);
		} catch (SQLException e) {
			log.info("Problem Occured When Procedure Calling=" + e.getMessage());
			String erromsg = "EX_INSERT_EMOS_FC_PURCHASE" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info("Problem Occured When Procedure Calling=" + e.getMessage());
				String erromsg = "EX_INSERT_EMOS_FC_PURCHASE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		log.info("!!!!!!outLst out!!!!!!!!!" + outMessage);
		log.info("Exited from the EX_INSERT_EMOS_FC_PURCHASE  Method ");
		return outMessage;

	}

	/**
	 * Added by Rabil on 21/02/2016
	 */
	@Override
	public BigDecimal saveCollectCollecDetailCurrAdjustAndFinalReceipt(Collect collection, List<CollectDetail> collectionDetailList,List<ForeignCurrencyAdjust> foreignCurrencyList,ReceiptPayment receiptPaymnet, List<Payment> payment) throws Exception {
		// TODO Auto-generated method stub

		BigDecimal collectionId =null;
		try{
			//saveCollect(collection);
			collectionId = saveCollection(collection);
			System.out.println("Test R :"+collectionId);
			for(CollectDetail collectDetail :collectionDetailList){
				Collect collect =new Collect();
				collect.setCollectionId(collectionId);
				collectDetail.setCashCollectionId(collect);
				saveDetail(collectDetail);
			}
			for(ForeignCurrencyAdjust  currecnyAdjust :foreignCurrencyList){
				Collect collect =new Collect();
				collect.setCollectionId(collectionId);
				currecnyAdjust.setCollect(collect);
				saveAdjust(currecnyAdjust);
			}

			for (Payment payment2 : payment) {
				getSession().saveOrUpdate(payment2);
			}

			saveReceipt(receiptPaymnet);


		} catch (HibernateException e) {
			throw e;
		}catch (Exception e) {
			log.info("Exception occured"+e);
			throw new Exception(e);
		}

		return collectionId;

		/**
		 * BigDecimal collectionId;
		try {
			collectionId = saveCollection(tempCollection);
			for (TempCollectDetail detail : tempDetailsList) {
				detail.setCashCollectionId(tempCollection);
				saveDetail(detail);
			}
			for (TempForeignCurrencyAdjust adjust : tempAdjustList) {
				adjust.setTempCollection(tempCollection);
				saveAdjust(adjust);
			}
			for (AuthorizedLog authorizedlog : lstAuthenStoredRecords) {
				LOGGER.info("Entering into AuthorizedLog method");
				getSession().save(authorizedlog);
				LOGGER.info("Exit into AuthorizedLog method"); 
			}
		} catch (Exception e) {
			LOGGER.info("Exception occured"+e);
			throw new Exception(e);
		}
		return collectionId;
		 */

	}


	public BigDecimal saveCollection(Collect collection) {
		log.info("Entering into saveCollection method");
		return (BigDecimal) getSession().save(collection);
	}


	public void saveDetail(CollectDetail collectDetail) {
		log.info("Entering into saveDetail method");
		getSession().saveOrUpdate(collectDetail);
		log.info("Exit into saveDetail method"); 
	}
	public void saveAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust) {
		log.info("Entering into saveAdjust method");
		getSession().saveOrUpdate(foreignCurrencyAdjust);
		log.info("Exit into saveAdjust method"); 
	}

	public void saveReceipt(ReceiptPayment receiptPaymnet){
		log.info("Entering into saveAdjust method");
		getSession().saveOrUpdate(receiptPaymnet);
		log.info("Exit into saveAdjust method"); 
	}


	@Override
	public List<PaymentMode> getPaymentModeDetails(String paymentCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");

		criteria.add(Restrictions.eq("paymentMode.paymentCode", paymentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PaymentMode> size = (List<PaymentMode>) findAll(criteria);

		if (size.isEmpty())
			return null;

		return size;


		//return size;
	}

	@Override
	public List<CurrencyAdjustView> getCurrencyAdjustViewRecords(
			BigDecimal documentNo, BigDecimal documentYear, BigDecimal companyId,
			BigDecimal documentCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CurrencyAdjustView.class,"currencyAdjustView");

		criteria.add(Restrictions.eq("currencyAdjustView.documentNo", documentNo));
		criteria.add(Restrictions.eq("currencyAdjustView.documentFinanceYear", documentYear));
		criteria.add(Restrictions.eq("currencyAdjustView.fsCompanyMaster", companyId));
		criteria.add(Restrictions.eq("currencyAdjustView.documentCode", documentCode ));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CurrencyAdjustView>  currencyAdjustList=(List<CurrencyAdjustView>) findAll(criteria);

		return currencyAdjustList;

	}

	@Override
	public List<ReceiptPaymentView> getReceiptPaymentView(BigDecimal documentNo,
			BigDecimal documentYear, BigDecimal companyId, BigDecimal documentCode) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ReceiptPaymentView.class,"receiptPaymentView");

		criteria.add(Restrictions.eq("receiptPaymentView.documentNo", documentNo));
		criteria.add(Restrictions.eq("receiptPaymentView.documentFinanceYear", documentYear));
		criteria.add(Restrictions.eq("receiptPaymentView.fsCompanyMaster", companyId));
		criteria.add(Restrictions.eq("receiptPaymentView.documentCode", documentCode ));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ReceiptPaymentView>  currencyAdjustList=(List<ReceiptPaymentView>) findAll(criteria);

		return currencyAdjustList;
	}

	@Override
	public BigDecimal getRoundedSaleAmountByFunc(BigDecimal tsaleAmount) {

		BigDecimal outRoundedSaleAmount = null;
		log.info("F_ROUND_LOCAL_AMT_LOWER - totalSaleAmount :" + tsaleAmount);

		Connection connection = null;
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			String call = " { ? = call F_ROUND_LOCAL_AMT_LOWER (?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.NUMERIC);
			cs.setBigDecimal(2, tsaleAmount);
			cs.executeUpdate();
			outRoundedSaleAmount = cs.getBigDecimal(1);

			log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + outRoundedSaleAmount);

		} catch (SQLException e) {
			e.getMessage();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.getMessage();
			}
		}

		return outRoundedSaleAmount;
	}

	@Override
	public List<CustomerIdProof> fetchCustomerByIdentityInt(String idno) {
		log.info("Entered into getCustomerDetails(String customerId, BigDecimal cardType) method ");
		log.info("CustomerId =" + idno);
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		criteria.add(Restrictions.eq("customerIdProof.identityInt", idno));
		criteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.isActive", Constants.Yes));
		criteria.addOrder(Order.desc("customerIdProof.creationDate"));
		criteria.add(Restrictions.eq("customerIdProof.identityStatus", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdPrrof = (List<CustomerIdProof>) findAll(criteria);
		log.info("Exited from getCustomerDetails(String customerId, BigDecimal cardType) method");
		return lstCustomerIdPrrof;
	}

	@Override
	public String getBranchName(BigDecimal countryBranchId) {
		
		String branchName = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<CountryBranch> countryBranchList = (List<CountryBranch>) findAll(criteria);
		if(countryBranchList!=null && countryBranchList.size() > 0){
			return branchName = countryBranchList.get(0).getBranchName();
		}else{
			return branchName;
		}		
	}

}


