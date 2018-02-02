package com.amg.exchange.cashtransfer.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cashtransfer.bean.CashTransferForLToLBean;
import com.amg.exchange.cashtransfer.dao.ICashTransferLToLDao;
import com.amg.exchange.cashtransfer.model.CashDetails;
import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.currency.inquiry.model.BranchDayTransactionView;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("rawtypes")
@Repository
public class CsahTransferLToLDaoImpl extends CommonDaoImpl implements ICashTransferLToLDao{

	
	private static final Logger log=Logger.getLogger(CsahTransferLToLDaoImpl.class);
	private SessionStateManage session=new SessionStateManage();

	@Override
	public List<Stock> getAllCurrencyDenominationFromStock(BigDecimal appCountryId,String userName,BigDecimal branchId,BigDecimal companyId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Stock.class, "stock");
		
		criteria.setFetchMode("stock.currencyId", FetchMode.JOIN);
		criteria.createAlias("stock.currencyId", "currencyId",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("stock.oracleUser", userName));
		
		criteria.setFetchMode("stock.countryBranch", FetchMode.JOIN);
		criteria.createAlias("stock.countryBranch", "countryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", branchId));
		
		criteria.setFetchMode("denominationId.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("denominationId.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));
		
		criteria.setFetchMode("stock.denominationId", FetchMode.JOIN);
		criteria.createAlias("stock.denominationId", "denominationId",JoinType.INNER_JOIN);
		criteria.addOrder(Order.desc("denominationId.denominationAmount"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<Stock> data = (List<Stock>)findAll(criteria);
		List<Stock> finalData = new ArrayList<Stock>();
		for (Stock Stock : data) {
			if(new SimpleDateFormat("dd/MMM/yy").format(Stock.getLogDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(Stock);
			}
		}
		
		return finalData;
	}

	@Override
	public List<CurrencyWiseDenomination> getAllCurrencyWiseDenomiantionList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyWiseDenomination>)findAll(criteria);
	}
	
	@Override
	public List<Employee> getAllCashierList(BigDecimal branchId,BigDecimal roleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
	
		criteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		criteria.createAlias("employee.fsCountryBranch", "fsCountryBranch",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryBranch.countryBranchId", branchId));
		
		criteria.setFetchMode("employee.fsRoleMaster", FetchMode.JOIN);
		criteria.createAlias("employee.fsRoleMaster","fsRoleMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsRoleMaster.roleId", roleId));
		criteria.addOrder(Order.asc("employee.employeeName"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>)findAll(criteria);
	}
	
	
	public List<CountryBranch> getBranchName(BigDecimal branchId){
	DetachedCriteria criteria=DetachedCriteria.forClass(CountryBranch.class,"countryBranch");
	criteria.add(Restrictions.eq("countryBranch.countryBranchId", branchId));
	criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	List<CountryBranch> branchList=findAll(criteria);
	return branchList;	
	}
	
	public List<CashHeader> getCashDataForApprove(String userName,BigDecimal branchId){
		DetachedCriteria criteria = DetachedCriteria.forClass(CashHeader.class, "cashHeader");
		criteria.setFetchMode("cashHeader.countryBranchId", FetchMode.JOIN);
		criteria.createAlias("cashHeader.countryBranchId", "countryBranchId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranchId.countryBranchId",branchId));
		criteria.add(Restrictions.isNull("cashHeader.receivedDate"));
	//	criteria.add(Restrictions.eq("cashHeader.toCashier",userName));
		criteria.add(Restrictions.eq("cashHeader.isActive",Constants.U));
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date fromDate = calendar.getTime();
		System.out.println(fromDate);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();
		System.out.println(toDate);
		
		criteria.add(Restrictions.between("cashHeader.createdDate", fromDate, toDate));
		
		return findAll(criteria);
	}
	
	@Override
	public void approveRecord(BigDecimal cashHeaderPk,String approvedName) throws Exception{
		
		try{
			// cash header - EX_CASH_HEADER
			CashHeader cashHeader=(CashHeader) getSession().get(CashHeader.class, cashHeaderPk);
			cashHeader.setCashHeaderId(cashHeaderPk);
			cashHeader.setIsActive(Constants.Yes);
			cashHeader.setReceivedDate(new Date());
			cashHeader.setModifiedBy(session.getUserName());
			cashHeader.setModifiedDate(new Date());
			
			getSession().update(cashHeader);
			
			// cash Details - EX_CASH_DETAILS
			DetachedCriteria criteria = DetachedCriteria.forClass(CashDetails.class, "cashDetails");
			criteria.setFetchMode("cashDetails.cashHeaderId", FetchMode.JOIN);
			criteria.createAlias("cashDetails.cashHeaderId", "cashHeaderId",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("cashHeaderId.cashHeaderId",cashHeaderPk));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<CashDetails> cashDetails = findAll(criteria);
			
			if(cashDetails != null && !cashDetails.isEmpty()){
				for (CashDetails cashDetails2 : cashDetails) {
					CashDetails cashDTUpdate = (CashDetails) getSession().get(CashDetails.class, cashDetails2.getCashDetailsId());
					cashDTUpdate.setIsActive(Constants.Yes);
					cashDTUpdate.setModifiedBy(session.getUserName());
					cashDTUpdate.setModifiedDate(new Date());
					getSession().update(cashDTUpdate);
				}
			}
			
			// Foreign Currency Adjustment
			DetachedCriteria criteria1 = DetachedCriteria.forClass(ForeignCurrencyAdjust.class, "foreignCurrencyAdjust");
			criteria1.add(Restrictions.eq("foreignCurrencyAdjust.documentCode",cashHeader.getDocumentCode()));
			criteria1.add(Restrictions.eq("foreignCurrencyAdjust.documentFinanceYear",cashHeader.getFinancialYear()));
			criteria1.add(Restrictions.eq("foreignCurrencyAdjust.documentNo",cashHeader.getDocumentNo()));
			criteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<ForeignCurrencyAdjust> lstForeignCurrencyAdjust = findAll(criteria1);
			
			if(lstForeignCurrencyAdjust != null && !lstForeignCurrencyAdjust.isEmpty()){
				for (ForeignCurrencyAdjust foreignCurrencyAdjust : lstForeignCurrencyAdjust) {
					ForeignCurrencyAdjust foreignCurrencyAdjustUpdate = (ForeignCurrencyAdjust) getSession().get(ForeignCurrencyAdjust.class, foreignCurrencyAdjust.getForeignCurrencyAdjustId());
					//foreignCurrencyAdjustUpdate.setTransactionType(Constants.R); // R to Approve For Branch to Branch or Float
					//foreignCurrencyAdjustUpdate.setModifiedBy(session.getUserName());
					//foreignCurrencyAdjustUpdate.setModifiedDate(new Date());
					foreignCurrencyAdjustUpdate.setDocumentStatus(Constants.P);
					foreignCurrencyAdjustUpdate.setApprovalBy(session.getUserName());
					foreignCurrencyAdjustUpdate.setApprovalDate(new Date());
					getSession().update(foreignCurrencyAdjustUpdate);
				}
			}
		}catch (HibernateException e) {
			throw e;
		}catch(Exception e){
			log.error("Error Occured While Saving" +e.getMessage());
			throw new Exception(e);
		}
	
	}

	@Override
	public void saveAllRecords(CashHeader cashHeader,List<CashDetails> cashDetailsList,List<ForeignCurrencyAdjust> foreignCurrencyAdjustList) throws Exception{
		try{
			getSession().saveOrUpdate(cashHeader);
			for(CashDetails cashDetails:cashDetailsList){
				cashDetails.setCashHeaderId(cashHeader);
			getSession().saveOrUpdate(cashDetails);
			}
			for(ForeignCurrencyAdjust foreignCurrencyAdjust:foreignCurrencyAdjustList){
			getSession().saveOrUpdate(foreignCurrencyAdjust);
			}
		}catch(Exception e){
			log.error("Error Occured While Saving" +e.getMessage());
		}
		
	}

	@Override
	public String procedurePopulateCashTransfer(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentcode,BigDecimal documentyear,BigDecimal documentNo) throws Exception {

		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_CASH_TRANSFER  (?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentcode);
			cs.setBigDecimal(4, documentyear);
			cs.setBigDecimal(5, documentNo);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			erromsg = cs.getString(6);
			System.out.println("Success Query and Error Message" + erromsg);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_CASH_TRANSFER" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			erromsg = "EX_P_POPULATE_CASH_TRANSFER" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_CASH_TRANSFER" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public String procedurePopulateCashTransferApproval(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentcode, BigDecimal documentyear,
			BigDecimal documentNo) throws Exception {

		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POP_CASH_TRANSFER_APPROVE  (?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentcode);
			cs.setBigDecimal(4, documentyear);
			cs.setBigDecimal(5, documentNo);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			erromsg = cs.getString(6);
			System.out.println("Success Query and Error Message" + erromsg);
		} catch (SQLException e) {
			erromsg = "EX_P_POP_CASH_TRANSFER_APPROVE" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			erromsg = "EX_P_POP_CASH_TRANSFER_APPROVE" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POP_CASH_TRANSFER_APPROVE" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public String procedurePValidateCashTransferStock(BigDecimal appCountryId,
			BigDecimal companyId, BigDecimal documentcode,
			BigDecimal documentyear, BigDecimal documentNo) throws Exception {

		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_VALIDATE_CASH_TRANS_STOCK  (?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentcode);
			cs.setBigDecimal(4, documentyear);
			cs.setBigDecimal(5, documentNo);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			erromsg = cs.getString(6);
			System.out.println("Success Query and Error Message" + erromsg);
		} catch (SQLException e) {
			erromsg = "EX_P_VALIDATE_CASH_TRANS_STOCK" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} catch (Exception e) {
			erromsg = "EX_P_VALIDATE_CASH_TRANS_STOCK" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_VALIDATE_CASH_TRANS_STOCK" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}
	
	
	public List<BigDecimal> getStockCurrency(BigDecimal countryBranchId) {
        List<BigDecimal> objList = new ArrayList<BigDecimal>();
        List<BigDecimal> objList1 = new ArrayList<BigDecimal>();
        String queryString = "select currency_id from VW_EX_STOCK where country_branch_id=? group by currency_id";
        objList = getSession().createSQLQuery(queryString).setBigDecimal(0, countryBranchId).list();
        
           
        
        if (objList.isEmpty())
            return null;
        return objList;
    }
	
	
	@SuppressWarnings("unchecked")
	public List<UserStockView> getCurrencyDenominationFromStock(BigDecimal appCountryId,String userName,BigDecimal branchId,BigDecimal companyId, BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(UserStockView.class, "userStockView");

	
		criteria.add(Restrictions.eq("userStockView.countryBranchId", branchId));
		criteria.add(Restrictions.eq("userStockView.oracleUser", userName));
		criteria.add(Restrictions.eq("userStockView.currencyId", currencyId));
	
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<UserStockView>) findAll(criteria);
	}
	
	@Override
	public String updateCashdeposit(BigDecimal applicationCountryId, BigDecimal companyId, String cashier, BigDecimal countryBrasnchId,BigDecimal currencyId ){
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		log.info("EX_P_INSERT_CASH_DEPOSIT applicationCountryId  " + applicationCountryId);
		log.info("EX_P_INSERT_CASH_DEPOSIT documentCode  " + companyId);
		log.info("EX_P_INSERT_CASH_DEPOSIT documentFinanceYr  " + cashier);
		log.info("EX_P_INSERT_CASH_DEPOSIT countryBrasnchId  " + countryBrasnchId);
		log.info("EX_P_INSERT_CASH_DEPOSIT currencyId  " + currencyId);
		
		
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_INSERT_CASH_DEPOSIT (?, ?, ?, ?,?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setString(3, cashier);
			cs.setBigDecimal(4, countryBrasnchId);
			cs.setBigDecimal(5, currencyId);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_INSERT_CASH_DEPOSIT" + " : " + e.getMessage();
			log.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_INSERT_CASH_DEPOSIT" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}
	
	@Override
	public String getNextToken() {
		SQLQuery sqlQuery = super.getSession().createSQLQuery("SELECT EX_CASH_TRANSFER_TEMP_SEQ.NEXTVAL FROM DUAL");
		return sqlQuery.uniqueResult().toString();
	}

	public List<CashHeader> isCheckCashTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal branchId,Date documentDate,String fromCashier, String toCashier,BigDecimal toCountryBranchCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(CashHeader.class, "cashHeader");
		
		//criteria.add(Restrictions.eq("cashHeader.documentDate",documentDate));
		
		criteria.setFetchMode("cashHeader.countryBranchId", FetchMode.JOIN);
		criteria.createAlias("cashHeader.countryBranchId", "countryBranchId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryBranchId.countryBranchId",branchId));
		
		criteria.setFetchMode("cashHeader.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("cashHeader.applicationCountryId", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", applicationCountryId));
		
		criteria.setFetchMode("cashHeader.companyId", FetchMode.JOIN);
		criteria.createAlias("cashHeader.companyId", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		criteria.add(Restrictions.eq("cashHeader.fromCashier",fromCashier));
		criteria.add(Restrictions.eq("cashHeader.toCashier",toCashier));
		criteria.add(Restrictions.eq("cashHeader.toCountryBranchCode",toCountryBranchCode));
		criteria.add(Restrictions.isNull("cashHeader.receivedDate"));
		
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String documentDate1 = dateformat.format(documentDate);
		criteria.add(Restrictions.sqlRestriction("TRUNC(DOCUMENT_DATE) = TO_DATE('" + documentDate1 + "','dd/MM/yy')"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashHeader> cashheaderlist = findAll(criteria);
		
		if (cashheaderlist.isEmpty()){
            return null;
		}else{
			return cashheaderlist;
		}
	}
	
	
	public List<CashDetails> getCashDetailsById(BigDecimal headerId){
		DetachedCriteria criteria = DetachedCriteria.forClass(CashDetails.class, "cashDetails");
		
		criteria.setFetchMode("cashDetails.cashHeaderId", FetchMode.JOIN);
		criteria.createAlias("cashDetails.cashHeaderId", "cashDetailsalias",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("cashDetailsalias.cashHeaderId",headerId));				
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashDetails> cashdetailslist = findAll(criteria);
		
		if (cashdetailslist.isEmpty())
            return null;
        return cashdetailslist;
	}
	
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjust(BigDecimal documentNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(ForeignCurrencyAdjust.class, "foreignCurrencyAdjust");
		
		criteria.add(Restrictions.eq("foreignCurrencyAdjust.documentNo",documentNo));				
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ForeignCurrencyAdjust> currencyAdjustlist = findAll(criteria);
		
		if (currencyAdjustlist.isEmpty())
            return null;
        return currencyAdjustlist;
	}
	
	public void deleteCashHeader(CashHeader cashHeader){
		getSession().delete(cashHeader);
	}
	public void deleteCashDetails(CashDetails cashDetails){
		getSession().delete(cashDetails);
	}
	
	public void deleteCurrencyAdjustDetails(ForeignCurrencyAdjust foreignCurrencyAdjust){
		getSession().delete(foreignCurrencyAdjust);
	}
	
	public List<BigDecimal> getUpdateCurrency(BigDecimal documentNo){
		List<BigDecimal> objList = new ArrayList<BigDecimal>();
        List<BigDecimal> objList1 = new ArrayList<BigDecimal>();
        String queryString = "select currency_id from EX_CURRENCY_ADJUST where DOCUMENT_NO=? group by currency_id";
        objList = getSession().createSQLQuery(queryString).setBigDecimal(0, documentNo).list();
        
           
        
        if (objList.isEmpty())
            return null;
        return objList;
	}
	
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjustDetails(BigDecimal documentNo,BigDecimal currencyId, BigDecimal denaminationAmount){
		DetachedCriteria criteria = DetachedCriteria.forClass(ForeignCurrencyAdjust.class, "foreignCurrencyAdjust");
		
		criteria.add(Restrictions.eq("foreignCurrencyAdjust.documentNo",documentNo));
		
		criteria.setFetchMode("foreignCurrencyAdjust.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("foreignCurrencyAdjust.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("foreignCurrencyAdjust.denaminationAmount",denaminationAmount));	
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ForeignCurrencyAdjust> currencyAdjustlist = findAll(criteria);
		
		if (currencyAdjustlist.isEmpty())
            return null;
        return currencyAdjustlist;
	}
	
}
