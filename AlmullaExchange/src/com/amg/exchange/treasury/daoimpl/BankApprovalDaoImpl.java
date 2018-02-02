package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.bean.BankBranchDataTable;
import com.amg.exchange.treasury.dao.IBankApprovalDao;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Repository
public class BankApprovalDaoImpl<T> extends CommonDaoImpl<T> implements
IBankApprovalDao {
	
	Logger LOGGER = Logger.getLogger(BankApprovalDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class,
				"bankMaster");

		//criteria.add(Restrictions.isNull("bankMaster.approvedBy"));
		//criteria.add(Restrictions.isNull("bankMaster.approvedDate"));
		criteria.add(Restrictions.eq("bankMaster.recordStatus","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankListForActiveInactive(BigDecimal countryId,BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class,
				"bankMaster");		
		
		criteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId",countryId));
		criteria.add(Restrictions.eq("bankMaster.bankId",bankId));

		List<String>  activeStatuslist = new ArrayList<String>();
		activeStatuslist.add("Y");
		activeStatuslist.add("D");
		activeStatuslist.add("U");

		//criteria.add(Restrictions.isNotNull("bankMaster.approvedBy"));
		//criteria.add(Restrictions.isNotNull("bankMaster.approvedDate"));
		criteria.add(Restrictions.in("bankMaster.recordStatus", activeStatuslist));		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class,	"bankBranch");

		//criteria.add(Restrictions.isNull("bankBranch.approvedBy"));
		//criteria.add(Restrictions.isNull("bankBranch.approvedDate"));
		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);

		criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("bankBranch.fsStateMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsStateMaster", "fsStateMaster",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("bankBranch.fsDistrictMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsDistrictMaster", "fsDistrictMaster",JoinType.LEFT_OUTER_JOIN);

		criteria.setFetchMode("bankBranch.fsCityMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCityMaster", "fsCityMaster",JoinType.LEFT_OUTER_JOIN);

		criteria.add(Restrictions.eq("bankBranch.isactive","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankBranch>) findAll(criteria);
	}

	//Bank Branch Active/Deactive

	@SuppressWarnings("unchecked")
	@Override
	public List<BankBranch> getBankBranchListForActiveInactive(BigDecimal bankId, String  branchFullName,String ifscCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");


		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster",	JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));	
		if(branchFullName!=null){
		criteria.add(Restrictions.eq("bankBranch.branchFullName", branchFullName));	
		}
		if(ifscCode!=null&&!ifscCode.equals("")){
			criteria.add(Restrictions.eq("bankBranch.branchIfsc", ifscCode));	
		}
		 
		/*List<String>  activeStatuslist = new ArrayList<String>();
			activeStatuslist.add("Y");
			activeStatuslist.add("D");*/

		//scriteria.add(Restrictions.ne("bankBranch.isactive", Constants.U));		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> listBranch = (List<BankBranch>) findAll(criteria);
		System.out.println("SIZE BRANCH:  "+listBranch);
		return listBranch;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankApplicabilityListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BankApplicability.class, "bankApplicability");
		//Added @koti 26/03/2015
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		//criteria.add(Restrictions.isNull("bankApplicability.approvedBy"));
		//criteria.add(Restrictions.isNull("bankApplicability.approvedDate"));
		criteria.add(Restrictions.eq("bankApplicability.isActive","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankApplicability>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getBankApplicabilityListForActiveInactive() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class,
				"bankApplicability");
		//added @koti to get the Ind Desc 25/03/2015
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);

		//ended @koti to get the ind desc 25/03/2015
		List<String>  activeStatuslist = new ArrayList<String>();
		activeStatuslist.add("Y");
		activeStatuslist.add("D");

		criteria.add(Restrictions.in("bankApplicability.isActive", activeStatuslist));		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankApplicability>) findAll(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccount> getBankAccountListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BankAccount.class, "bankAccount");

		criteria.add(Restrictions.isNull("bankAccount.approvedBy"));
		criteria.add(Restrictions.isNull("bankAccount.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccount>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getBankAccountDetailListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountDetails");

		//criteria.add(Restrictions.isNull("bankAccountDetails.approvedBy"));
		//criteria.add(Restrictions.isNull("babankAccountDetailsnkAccountDetails.approvedDate"));

		criteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster",	JoinType.INNER_JOIN);


		criteria.add(Restrictions.eq("bankAccountDetails.recordStatus","U"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountLength> getBankAccountLengthListForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountLength");

		criteria.add(Restrictions.isNull("bankAccountLength.approvedBy"));
		criteria.add(Restrictions.isNull("bankAccountLength.approvedDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountLength>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getBankAccountForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class,
				"banAccount");


		criteria.add(Restrictions.isNull("banAccount.approvedBy"));
		criteria.add(Restrictions.isNull("banAccount.approvedDate"));
		criteria.add(Restrictions.eq("banAccount.recordStatus","U"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getBankAccountForActiveInactive(BigDecimal countryId,BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankAccountDetails.class,
				"banAccount");
		criteria.setFetchMode("banAccount.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("banAccount.exBankMaster", "exBankMaster",	JoinType.INNER_JOIN);
		criteria.setFetchMode("banAccount.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("banAccount.fsCountryMaster", "fsCountryMaster",JoinType.INNER_JOIN);
		criteria.setFetchMode("banAccount.fsCurrencyMaster", FetchMode.JOIN);
		criteria.createAlias("banAccount.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId",countryId));
		criteria.add(Restrictions.eq("exBankMaster.bankId",bankId));

		List<String>  activeStatuslist = new ArrayList<String>();
		activeStatuslist.add("Y");
		activeStatuslist.add("D");

		criteria.add(Restrictions.in("banAccount.recordStatus", activeStatuslist));		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>) findAll(criteria);
	}

	@Override
	public void upDateRemarks(String remarks,BigDecimal branchPk,String userName) {
		BankBranch bankBranch=(BankBranch) getSession().get(BankBranch.class, branchPk);
		bankBranch.setIsactive("D");
		bankBranch.setRemarks(remarks);
		bankBranch.setModifiedBy(userName);
		bankBranch.setModifiedDate(new Date());
		getSession().update(bankBranch);
	}

	@Override
	public void activateRecord(BigDecimal branchPk,String userName) {
		BankBranch bankBranch=(BankBranch) getSession().get(BankBranch.class, branchPk);
		bankBranch.setIsactive("U");
		bankBranch.setModifiedBy(userName);
		bankBranch.setModifiedDate(new Date());
		getSession().update(bankBranch);
	}

	@Override
	public String branchActivateDeActivatemoveJavatoEMOS(HashMap<String, Object> activateDeactiveRec) throws AMGException {

		//EX_BANK_BRANCH_ACTIVE_DEACTIVE(P_BANK_CODE,P_BANK_ID,P_BANK_BRANCH_ID,P_BANK_BRANCH_CODE.P_ERROR_MESSAGE)

		//String bankCode = (String) activateDeactiveRec.get("BankCode");
		BigDecimal bankId = (BigDecimal) activateDeactiveRec.get("BankId");
		//String bankBranchCode = (String) activateDeactiveRec.get("BankBranchCode");
		BigDecimal bankBranchId = (BigDecimal) activateDeactiveRec.get("BankBranchId");
		
		Connection connection = null;
		String errString = null;
		
		try {
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call EX_BANK_BRANCH_ACTIVE_DEACTIVE (?, ?, ?) } ";
			cs = connection.prepareCall(call);
			//cs.setString(1, bankCode);
			cs.setBigDecimal(1, bankId);
			//cs.setString(3, bankBranchCode);
			cs.setBigDecimal(2, bankBranchId);
			cs.registerOutParameter(3, java.sql.Types.VARCHAR);
			cs.execute();
			errString = cs.getString(3);
		} catch (SQLException e) {
			String erromsg = "EX_BANK_BRANCH_ACTIVE_DEACTIVE " + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			String erromsg = "EX_BANK_BRANCH_ACTIVE_DEACTIVE " + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Problem Occured While Procedure calling EX_BANK_BRANCH_ACTIVE_DEACTIVE : "+e.getMessage());
				String erromsg = "EX_BANK_BRANCH_ACTIVE_DEACTIVE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}

		return errString;
	}

	@Override
	public List<BankBranch> toSearchBranchName(BigDecimal countryId,BigDecimal bankID,String searchBranchName, String searchBranchIfsc,String searchSwiftIfsc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");

		criteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.exBankMaster", "exBankMaster",	JoinType.INNER_JOIN);
		
		criteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster",	JoinType.INNER_JOIN);		
		
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankID));
		if(searchBranchName != null && searchBranchName.length()>=2){
			criteria.add(Restrictions.ilike("bankBranch.branchFullName", searchBranchName, MatchMode.ANYWHERE));
		}
		
		if(searchBranchIfsc != null && searchBranchIfsc.length()>=2){
			criteria.add(Restrictions.like("bankBranch.branchIfsc", searchBranchIfsc.toUpperCase(), MatchMode.ANYWHERE));
		}
		
		if(searchSwiftIfsc != null && searchSwiftIfsc.length()>=2){
			criteria.add(Restrictions.like("bankBranch.swiftBic", searchSwiftIfsc.toUpperCase(), MatchMode.ANYWHERE));
		}
			

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> listBranch = (List<BankBranch>) findAll(criteria);
		return listBranch;
	}

	@Override
	public void bulkApprovalForBankBranch(List<BankBranchDataTable> bankBranchDetails) {
		if(bankBranchDetails != null && bankBranchDetails.size() != 0){
			for (BankBranchDataTable bankBranchDataTable : bankBranchDetails) {
				BankBranch bankBranch=(BankBranch) getSession().get(BankBranch.class, bankBranchDataTable.getBranchPk());
				bankBranch.setIsactive(Constants.Yes);
				bankBranch.setRemarks(null);
				bankBranch.setApprovedBy(sessionStateManage.getUserName());
				bankBranch.setApprovedDate(new Date());
				getSession().update(bankBranch);
			}
		}
	}
}
