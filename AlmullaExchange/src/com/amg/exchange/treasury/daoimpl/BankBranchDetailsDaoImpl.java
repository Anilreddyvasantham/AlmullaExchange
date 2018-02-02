/**
 * author Arul JayaSingh
 */
package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.daoimpl.GeneralDaoImpl;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.treasury.dao.IBankBranchDetailsDao;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BankBranchDetailsDaoImpl<T> extends CommonDaoImpl<T> implements
		IBankBranchDetailsDao<T>, Serializable {
	private static final Logger LOG = Logger.getLogger(BankBranchDetailsDaoImpl.class);
	public void saveBankBranchDetail(BankBranch bankBranch) {
		try {
			System.out.println("save or update branch..");
			getSession().saveOrUpdate(bankBranch);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class);
		return (List<BankBranch>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchByBranchCode(String branchCode) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
				BankBranch.class, "BankBranch");

		detachedCriteria.add(Restrictions.eq("branchCode", branchCode));

		detachedCriteria.setFetchMode("BankBranch.fsCountryMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsStateMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsStateMaster",
				"fsStateMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsDistrictMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsDistrictMaster",
				"fsDistrictMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsCityMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsCityMaster", "fsCityMaster",JoinType.LEFT_OUTER_JOIN);
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankBranch>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchByBranchID(BigDecimal branchID) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(BankBranch.class, "BankBranch");

		detachedCriteria.add(Restrictions.eq("bankBranchId", branchID));
		
		/*detachedCriteria.setFetchMode("BankBranch.fsCountryMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsStateMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsStateMaster",
				"fsStateMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsDistrictMaster",
				FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsDistrictMaster",
				"fsDistrictMaster", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("BankBranch.fsCityMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("BankBranch.fsCityMaster", "fsCityMaster",JoinType.LEFT_OUTER_JOIN);*/
		
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class,"bankMaster");
		
		criteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> checkBranchCode(String branchCode) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class,
				"bankBranch");
		criteria.add(Restrictions.eq("bankBranch.branchCode", branchCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getData(BigDecimal bankid, BigDecimal branchcode) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		dCriteria.add(Restrictions.eq("bankBranch.branchCode", branchcode));

		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankid));

		dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankBranch.fsStateMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsStateMaster", "fsStateMaster",JoinType.LEFT_OUTER_JOIN);

		dCriteria.setFetchMode("bankBranch.fsDistrictMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsDistrictMaster","fsDistrictMaster", JoinType.LEFT_OUTER_JOIN);

		dCriteria.setFetchMode("bankBranch.fsCityMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCityMaster", "fsCityMaster",JoinType.LEFT_OUTER_JOIN);

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankBranch>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getCountrybyBank(BigDecimal bankid) {
		// TODO Auto-generated method stub
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		
		dCriteria.setFetchMode("bankMaster.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankid));
		
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<BankMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBranchCodebyBank(BigDecimal bankid) {
		// TODO Auto-generated method stub
		LOG.info("Start getBranchCodebyBank method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankid));
		Criteria outer = getSession().createCriteria(BankBranch.class);
		outer.add(Subqueries.propertyIn("bankId", dCriteria));
		outer.setFirstResult(0);
		outer.setMaxResults(5);
		// Cr to get Bank Branch 
		/*dCriteria.add(Restrictions.disjunction()
				.add( Restrictions.eq("bankBranch.isactive", Constants.U))
				.add(Restrictions.eq("bankBranch.isactive", "D")));*/
		
		dCriteria.addOrder(Order.asc("bankBranch.branchCode"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> listBranch = (List<BankBranch>) findAll(dCriteria);
		LOG.info("END getBranchCodebyBank method");
		LOG.info("SIZE OD CODE: "+listBranch.size());
		return listBranch;
	}
	
	
	// Added by kani begin
	@SuppressWarnings("unchecked")
	@Override
	public List<String> checkSwift(String swiftBic) {
	System.out.println("Entering into checkSwift method DAO impl ");
	DetachedCriteria criteria = DetachedCriteria.forClass(SwiftMaster.class, "swiftMaster");
	criteria.setProjection( Projections.projectionList().add( Projections.property("swiftBIC")));
	criteria.add(Restrictions.eq("swiftMaster.swiftBIC", swiftBic));
	List<T> swift = findAll(criteria);
	System.out.println("The Size of Swift in DB KKKKKKK -->"+swift.size());
	        return (List<String>) swift;
	 
	}
	// Added by kani End

	
	@SuppressWarnings({ "unchecked" })
	@Override
	public List<BankMaster> getIFSCLength(BigDecimal bankId) {

		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("bankmaster.bankId", bankId));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", "Y"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(detachedCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranchView> getBranchListfromView(BigDecimal bankId) {
		LOG.info("Entering into getBranchListfromView method");
		LOG.info("benifisBankId " + bankId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class, "bankBranchView");
		criteria.add(Restrictions.eq("bankBranchView.bankId", bankId));
		criteria.addOrder(Order.asc("bankBranchView.branchCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getBranchListfromView method");
		return (List<BankBranchView>) findAll(criteria);
	}

	@Override
	public List<BankBranch> checkIfsc(String branchIfc) {
		LOG.info("Start checkIfsc method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("branchIfsc", branchIfc));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> listBranch = (List<BankBranch>) findAll(dCriteria);
		LOG.info("END checkIfsc method");
		LOG.info("SIZE OD CODE: "+listBranch.size());
		return listBranch;
	}

	
	@Override
	public List<BankBranch> checkIfscByBank(BigDecimal bankId, String branchIfc) {
		LOG.info("Start checkIfsc method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		dCriteria.add(Restrictions.eq("branchIfsc", branchIfc));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> listBranch = (List<BankBranch>) findAll(dCriteria);
		LOG.info("END checkIfsc method");
		LOG.info("SIZE OD CODE: "+listBranch.size());
		return listBranch;
	}
	
	@Override
	public HashMap<String, String> callPopulateBankBranch(
			HashMap<String, String> inputValues) throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		String errString = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " {call EX_P_POPULATE_BANK_BRANCH (?,?,?,?,? ) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, inputValues.get("BANK_CODE"));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("BANK_ID")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("BANK_BRANCH_ID")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("BANK_BRANCH_CODE")));
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(5);
 			outputValues.put("P_ERROR_MESSAGE", errString);
 			
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_BANK_BRANCH" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_BANK_BRANCH" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return outputValues;
	 
	}

	@Override
	public BigDecimal toFetchMaxBranchCodeOfBranchFromBank(BigDecimal bankID) {
		BigDecimal maxBranchCode=null;
		String hqlQuery="select distinct Max(a.branchCode) from  BankBranch a where a.exBankMaster.bankId =  :pbankID";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("pbankID", bankID);
        List<BigDecimal> lstBankBranch =query.list();
        if(lstBankBranch.size()>0){
        	maxBranchCode=lstBankBranch.get(0);
        }
		return maxBranchCode;
	}

	@Override
	public void toApproveRecordsBankBranch(BigDecimal pkBankBranch,String userName) {
		BankBranch bankBranch=(BankBranch) getSession().get(BankBranch.class, pkBankBranch);
		bankBranch.setIsactive(Constants.Yes);
		bankBranch.setApprovedBy(userName);
		bankBranch.setApprovedDate(new Date());
		getSession().update(bankBranch);
	}

	@Override
	public String toFetchBranchName(BigDecimal bankBranchId) {
		String branchName=null;
		String hqlQuery="select distinct a.branchFullName from  BankBranch a where a.bankBranchId =  :pBankBranchId";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("pBankBranchId", bankBranchId);
        List<String> lstBranchName =query.list();
        if(lstBranchName.size()>0){
        	branchName=lstBranchName.get(0);
        }
		return branchName;
	}

	@Override
	public List<BankBranchView> toFetchAllDetailsFromBankBranch(BigDecimal bankId, BigDecimal bankBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranchView.class,"bankBranchView");

		criteria.add(Restrictions.eq("bankBranchView.bankId", bankId));
		criteria.add(Restrictions.eq("bankBranchView.bankBranchId", bankBranchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranchView> lstBankBranchViews=(List<BankBranchView>) findAll(criteria);
		return lstBankBranchViews;
	}
	
	@Override
	public void unapproveProcedureExcep(BigDecimal pkBankBranch) {
		BankBranch bankBranch=(BankBranch) getSession().get(BankBranch.class, pkBankBranch);
		bankBranch.setIsactive(Constants.U);
		bankBranch.setApprovedBy(null);
		bankBranch.setApprovedDate(null);
		getSession().update(bankBranch);
		
	}

}
