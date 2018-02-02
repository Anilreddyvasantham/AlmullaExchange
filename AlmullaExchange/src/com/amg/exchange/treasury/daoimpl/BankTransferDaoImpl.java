package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.net.aso.b;

import org.apache.log4j.Logger;
import org.codehaus.groovy.syntax.Types;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.treasury.dao.IBankTransferDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class BankTransferDaoImpl<T> extends CommonDaoImpl<T> implements
		IBankTransferDao<T>, Serializable {

	Logger LOGGER = Logger.getLogger(BankTransferDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getOtherBankList(BigDecimal bankID) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankMaster.class, "bankMaster");
		dCriteria.add(Restrictions.ne("bankMaster.bankId", bankID));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> data = (List<BankMaster>) findAll(dCriteria);
		return data;
	}

	@Override
	public TreasuryDealHeader getPopulateRecord(BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasurydealHeader");

		dCriteria.setFetchMode("treasurydealHeader.exDocument", FetchMode.JOIN);
		dCriteria.createAlias("treasurydealHeader.exDocument", "exDocument",JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("treasurydealHeader.treasuryDocumentNumber", documentNo));
		dCriteria.add(Restrictions.eq("exDocument.documentID", documentID));
		dCriteria.add(Restrictions.eq("treasurydealHeader.userFinanceYear", financeYear));
		dCriteria.add(Restrictions.eq("treasurydealHeader.isActive", Constants.U));
		
		dCriteria.setFetchMode("treasurydealHeader.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasurydealHeader.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		// need allow for modification
		dCriteria.add(Restrictions.isNull("treasurydealHeader.approvedBy"));
		dCriteria.add(Restrictions.isNull("treasurydealHeader.approvedDate"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (TreasuryDealHeader) dCriteria.getExecutableCriteria(getSession()).uniqueResult();
	}

	@Override
	public List<TreasuryDealDetail> getTreasuryDealDetailInfo(
			BigDecimal documentNo, BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");
		dCriteria.add(Restrictions.eq("treasuryDealDetail.documentNumber",
				documentNo));

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDocument",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDocument",
				"treasuryDealDocument", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("treasuryDealDocument.documentID",
				documentID));
		
		dCriteria.add(Restrictions.eq("treasuryDealDetail.treasuryDealUserFinanceYear",
				financeYear));
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster", "treasuryDealCompanyMaster",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealCompanyMaster.companyId", companyId));
		
		dCriteria.add(Restrictions.eq("treasuryDealDetail.isActive", Constants.U));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryDealDetail>) findAll(dCriteria);

	}

	@Override
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionInfo(
			BigDecimal documentNo, BigDecimal documentID ,BigDecimal treasuryDtlId,BigDecimal companyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryStandardInstruction.class,
				"treasuryStandardInstruction");

		dCriteria.add(Restrictions.eq(
				"treasuryStandardInstruction.ducumentNumber", documentNo));

		dCriteria.setFetchMode(
				"treasuryStandardInstruction.treasurydocDoccument",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryStandardInstruction.treasurydocDocument",
				"treasurydocDocument", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("treasurydocDocument.documentID",
				documentID));
		
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.treasuryDealDetail.treasuryDealDetailId",
				treasuryDtlId));
		
		
		dCriteria.setFetchMode("treasuryStandardInstruction.treasurycomCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryStandardInstruction.treasurycomCompanyMaster", "treasurycomCompanyMaster",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasurycomCompanyMaster.companyId", companyId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryStandardInstruction>) findAll(dCriteria);
	}

	@Override
	public List<StandardInstruction> getStandardInstructionInfo(BigDecimal documentNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		dCriteria.add(Restrictions.eq("standardInstruction.ducumentNumber",documentNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return null;
	}

	@Override
	public List<StandardInstruction> getStandardInstructionNumber(BigDecimal standInstructionId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		dCriteria.add(Restrictions.eq("standardInstruction.standardInstructionId",standInstructionId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<StandardInstruction>) findAll(dCriteria);
	}

	@Override
	public List<BankMaster> getBankListWithCurrency(BigDecimal bankID,
			BigDecimal currencyID) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankMaster.class, "bankMaster");
		dCriteria.add(Restrictions.ne("bankMaster.bankId", bankID));

		dCriteria.setFetchMode("bankMaster.currencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.currencyMaster", "currencyMaster",
				JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyID));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> data = (List<BankMaster>) findAll(dCriteria);
		return data;
	}

	@Override
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryID, BigDecimal bankID, BigDecimal currencyID) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.ne("bankMaster.bankId", bankID));
		
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);

		// to get Application CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryID));
		
		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);
		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal fundingBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_FUND_BANK);

		dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId", corresBankIndicatorId)).add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId)).add(Restrictions.eq("bankInd.bankIndicatorId", fundingBankIndicatorId)));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankApplicability>) findAll(dCriteria);
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "BankIndicator");
		
		dCriteria.add(Restrictions.eq("BankIndicator.bankIndicatorCode", bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		BigDecimal pkBankIndictor = ((List<BankIndicator>)findAll(dCriteria)).get(0).getBankIndicatorId();
		
		return pkBankIndictor;
	}

	@Override
	public void bankTransferApprorval(Map<String, Object> bnkTrsfrApproval,String userName) {
		
		
		BigDecimal treasuryDealHeaderDocNo= (BigDecimal)bnkTrsfrApproval.get("TreasuryDealHeaderDocNo");
		
		BigDecimal treasuryDealHeaderPK= (BigDecimal)bnkTrsfrApproval.get("TreasuryDealHeaderPK");
		BigDecimal treasuryDealBrFromDeatislID= (BigDecimal)bnkTrsfrApproval.get("TreasuryDealBrFromDeatislID");
		List<BigDecimal> lstBrTOTrDetailsId= (List<BigDecimal>)bnkTrsfrApproval.get("TreasuryDealBrToDeatislID");
		
		if(treasuryDealHeaderPK!=null)
		{
			TreasuryDealHeader treasuryHeadObj=(TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryDealHeaderPK);
			treasuryHeadObj.setIsActive(Constants.Yes);
			treasuryHeadObj.setApprovedBy(userName);
			treasuryHeadObj.setApprovedDate(new Date());
			getSession().saveOrUpdate(treasuryHeadObj);
		}
		if(treasuryDealBrFromDeatislID!=null)
		{
			TreasuryDealDetail treasuryDealDetail=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryDealBrFromDeatislID);
			treasuryDealDetail.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(treasuryDealDetail);
		}
		
		if(lstBrTOTrDetailsId!=null && lstBrTOTrDetailsId.size()>0)
		{
			for(BigDecimal brTOTrDetailsId :lstBrTOTrDetailsId)
			{
				TreasuryDealDetail treasuryDealDetail=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, brTOTrDetailsId);
				treasuryDealDetail.setIsActive(Constants.Yes);
				getSession().saveOrUpdate(treasuryDealDetail);
			}
		}
		/*
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDocumentNumber", treasuryDealHeaderDocNo));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> lstTreasuryDealHeader=(List<TreasuryDealHeader>) findAll(dCriteria);
		for(TreasuryDealHeader treasuryDealHeader:lstTreasuryDealHeader)
		{
			TreasuryDealHeader treasuryHeadObj=(TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryDealHeader.getTreasuryDealHeaderId());
			treasuryHeadObj.setIsActive(Constants.Yes);
			treasuryHeadObj.setApprovedBy(userName);
			treasuryHeadObj.setApprovedDate(new Date());
			getSession().saveOrUpdate(treasuryHeadObj);
		}*/
		
	}
	

	
	/*@SuppressWarnings("unchecked")
	@Override
	public String getAmountNameInWords(String arg1, String arg2,
			BigDecimal arg3, BigDecimal arg4) {

		List<String> list = new ArrayList<String>();

		String queryString = "select EX_GET_AMT_IN_TEXT_FORMAT(?,?,?,?) from dual";

		list = getSession().createSQLQuery(queryString).setString(0, null)
				.setString(1, arg2).setBigDecimal(2, arg3)
				.setBigDecimal(3, arg4).list();

		if (list.isEmpty()) {
			return null;
		} else {
			String returnstring = list.get(0);
			return returnstring;
		}
		//Connection connection = getDataSourceFromHibernateSession();
		
		
		SQLQuery sqlQuery = getSession().createSQLQuery("select EX_GET_AMT_IN_TEXT_FORMAT(:arg1,:arg2,:arg3,:arg4) from dual");
		sqlQuery.setString("arg1", null);
		sqlQuery.setString("arg2", arg2);
		sqlQuery.setBigDecimal("arg3", arg3);
		sqlQuery.setBigDecimal("arg4", arg4);
		return sqlQuery.uniqueResult().toString();

	}
	*/	
	
	@Override
	public String getAmountNameInWords(String arg1, String arg2,
			BigDecimal arg3, BigDecimal arg4) {
		
		Connection connection=null;
		connection = getDataSourceFromHibernateSession();	
		CallableStatement callSt = null;
		String output = null;
		
		try {
				//String call = " { ? = call EX_GET_AMT_IN_TEXT_FORMAT (?, ?, ?, ?) } ";
			
			callSt = connection.prepareCall(" { call EX_GET_AMT_IN_TEXT_FORMAT(?,?,?,?)}");
			
				 callSt.registerOutParameter(1, Types.STRING);
				//callSt.setString(1, null);
				callSt.setString(2, arg2);
				callSt.setBigDecimal(3, arg3);
				callSt.setBigDecimal(4, arg4);
			
				//callSt.executeUpdate();
				callSt.execute();			
				
				output = callSt.getString(1);
				
				
				//return callSt.toString();
				/*
				
				
				callSt = connection.prepareCall("{?= call EX_GET_AMT_IN_TEXT_FORMAT(?,?,?,?)}");
	            callSt.setInt(1,200);
	            callSt.registerOutParameter(2, Types.DOUBLE);
	            callSt.execute();
	            Double output = callSt.getDouble(2);
	            System.out.println("The output returned from sql function: "+output);
			*/
			
			
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(connection!=null){
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		//return cs.toString();		
		return output;
		
}

	@Override
	public List<TreasuryDealDetail> getAllRecordsOfBankTransfer(BigDecimal treasuryDealHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");
	 
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDocument",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDocument","treasuryDealDocument", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster","treasuryDealCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader","treasuryDealHeader", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.isNull("treasuryDealHeader.dealWithType"));
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCountryMaster","treasuryDealCountryMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailBankAccountDetails",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailBankAccountDetails","treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster","treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster","treasuryDealBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId",treasuryDealHeaderId));
		//dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType",Constants.BT));
		
		//dCriteria.setFetchMode("treasuryDealDetail.customerSpecialDealRequest",FetchMode.JOIN);
		//dCriteria.createAlias("treasuryDealDetail.customerSpecialDealRequest","customerSpecialDealRequest", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.or(Restrictions.ilike("treasuryDealDetail.lineType",Constants.BT,MatchMode.ANYWHERE) ,Restrictions.ilike("treasuryDealDetail.lineType",Constants.BF,MatchMode.ANYWHERE)));
			
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryDealDetail>) findAll(dCriteria);
 
	}

	 

	@Override
	public List<TreasuryDealHeader> getAllRecordsFromHeader() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.setFetchMode("treasuryDealHeader.treasuryDealDocument",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDocument","exDocument", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster",FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsCompanyMaster","fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.isNull("treasuryDealHeader.dealWithType"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryDealHeader>) findAll(dCriteria);
	}

	@Override
	public List<TreasuryStandardInstruction> getTreasuryStandardInstruction(BigDecimal documentYear,BigDecimal documentNo, BigDecimal treasuryDetailsId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryStandardInstruction.class,"treasuryStandardInstruction");
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.treasDocumentFinancialYear", documentYear));
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.ducumentNumber", documentNo));
		dCriteria.addOrder(Order.asc("treasuryStandardInstruction.messageLineNumber"));
		dCriteria.add(Restrictions.eq("treasuryStandardInstruction.treasuryDealDetail.treasuryDealDetailId",treasuryDetailsId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
 
		return (List<TreasuryStandardInstruction>) findAll(dCriteria);
	}	

	@Override
	public BigDecimal getBankAccountDeatilsPk(String faAccountNo){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		dCriteria.add(Restrictions.eq("bankAccountDetails.fundGlno", faAccountNo));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<BankAccountDetails> lstBankAccountDetails=(List<BankAccountDetails>) findAll(dCriteria);
		if(lstBankAccountDetails.size()>0){
			return lstBankAccountDetails.get(0).getBankAcctDetId();
		}else{
			return null;
		}
	}
	
	@Override
	public BigDecimal getAccountBalancePk(String glslNo){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", glslNo));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<AccountBalance> lstBankAccountDetails=(List<AccountBalance>) findAll(dCriteria);
	if(lstBankAccountDetails.size()>0){
		return lstBankAccountDetails.get(0).getAccountId();
	}else{
		return null;
	}
	}
	
	@Override
	public String getAccountNoBasedOnGlSlNumber(String glslno){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		dCriteria.add(Restrictions.eq("bankAccountDetails.fundGlno", glslno));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<BankAccountDetails> lstBankAccountDetails=(List<BankAccountDetails>) findAll(dCriteria);
	if(lstBankAccountDetails.size()>0){
		return lstBankAccountDetails.get(0).getBankAcctNo();
	}else{
		return "nodata";
	}
	}
	
	@Override
	public List<BankAccountDetails> getAccountDetailsListBasedOnSelectedCurrency(List<BigDecimal> bankList,BigDecimal curencyId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.in("exBankMaster.bankId", bankList));
		
		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster",FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster","fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", curencyId));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<BankAccountDetails>) findAll(dCriteria);
	}
	
	@SuppressWarnings({ "unchecked" })
	@Override
	public void callProcedureForBankTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId, BigDecimal financialYear, BigDecimal documentNumber)throws AMGException{
		
		
		LOGGER.info("!Calling  callProcedureForBankTransfer Procedure with INPUT VALUES  !!!!!");
		
		LOGGER.info("callProcedureForBankTransfer appcountryId :" + applicationCountryId);
		LOGGER.info("callProcedureForBankTransfer companyId :" + companyId);
		LOGGER.info("callProcedureForBankTransfer documentId :" + documentId);
		LOGGER.info("callProcedureForBankTransfer financialYear :" + financialYear);
		LOGGER.info("callProcedureForBankTransfer documentNumber :" + documentNumber);
		
		Connection connection=null;
		connection = getDataSourceFromHibernateSession();

		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL(?,?,?,?,?)}");
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYear);
			cs.setBigDecimal(5, documentNumber);
			
			cs.execute();
			
			LOGGER.info("!After Calling  EX_TREASURY_UAPPV_UGL Procedure !!!!!");
			
			connection.close();
		} catch (SQLException e) {
			LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
			String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
	}
	
	 @Override
	  public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForBankTransfer(String status,BigDecimal financeYear,BigDecimal documentId,BigDecimal companyId) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
			dCriteria.add(Restrictions.isNull("treasuryDealHeader.dealWithType"));	  
			dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", status));
			dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear", financeYear));
			
			dCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster", FetchMode.JOIN);
			dCriteria.createAlias("treasuryDealHeader.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
			
			dCriteria.setFetchMode("treasuryDealHeader.exDocument", FetchMode.JOIN);
			dCriteria.createAlias("treasuryDealHeader.exDocument", "exDocument",JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("exDocument.documentID", documentId));
			
			dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedBy"));
			dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedDate"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<TreasuryDealHeader> pdata = (List<TreasuryDealHeader>) findAll(dCriteria);
			return pdata;
	  }

	@Override
	public void saveBankTransferFrom(BigDecimal treasuryBrFromPk,
			List<BigDecimal> lstTreasuryStdBrFromPk, String userName) {
		
		TreasuryDealDetail treasuryDealDetail=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryBrFromPk);
		treasuryDealDetail.setModifiedBy(userName);
		treasuryDealDetail.setModifiedDate(new Date());
		getSession().update(treasuryDealDetail);
		
		for(BigDecimal trasuryStdPk : lstTreasuryStdBrFromPk)
		{
			TreasuryStandardInstruction treasuryStandardInstruction=(TreasuryStandardInstruction) getSession().get(TreasuryStandardInstruction.class, trasuryStdPk);
			treasuryStandardInstruction.setModifiedBy(userName);
			treasuryStandardInstruction.setModifiedDate(new Date());
			getSession().update(treasuryStandardInstruction);
		}
			
		
	}
	
	public List<TreasuryDealHeaderDTO> getBankTransferEnqSelectDate(Date documentDate) {
		String hql = "select treasuryDealHeader.treasuryDealHeaderId as treasuryDealHeaderId , treasuryDealHeader.dealWithType as dealWithType , "
				+ "treasuryDealHeader.exDocument.documentID as documentID , treasuryDealHeader.exDocument.documentDesc  as documentDesc , treasuryDealHeader.treasuryDocumentNumber as treasuryDocumentNumber , "
				+ "treasuryDealHeader.fsCompanyMaster.companyId as companyId , "
				+ " treasuryDealHeader.fsCompanyMaster.companyCode as companyCode , treasuryDealHeader.createdBy as createdBy, "
				+ " treasuryDealHeader.createdDate as createdDate , "
				+ " treasuryDealHeader.userFinanceYear as documentFinanceYear ,"
				+ " treasuryDealHeader.isActive as isActive, "
				+ " treasuryDealHeader.documentDate as documentDate, "
				+ " treasuryDealHeader.approvedBy as approvedBy, "
				+ " treasuryDealHeader.valueDate as valueDate, "
				+ " treasuryDealHeader.remarks as  remarks, "
				+ " treasuryDealHeader.attention as attention "
				+ "from "
				+ "TreasuryDealHeader as treasuryDealHeader inner join treasuryDealHeader.exDocument  "
				+ "where to_date(to_char(treasuryDealHeader.accyymm, 'DD-MON-YY'), 'DD-MON-YY') = :date " 
				+ "and treasuryDealHeader.dealWithType is null";
 
		Query query = getSession().createQuery(hql);
		query.setParameter("date", documentDate);
 
		query.setResultTransformer(Transformers.aliasToBean(TreasuryDealHeaderDTO.class));
		// query.setParameter("returnRef", dealID);
		List<TreasuryDealHeaderDTO> lstTreasuryDealHeader1 = query.list();

		return lstTreasuryDealHeader1;
	}

	@Override
	public void deleteBrantransferRecords(BigDecimal treasuryHeaderPk,
			BigDecimal treasuryBrFromPk,
			List<BigDecimal> lstTreasuryStdBrFromPk,
			List<BigDecimal> lstTreasuryDetailPk,
			List<BigDecimal> lstTreasuryStdBrToPk, String userName) throws Exception{
		
		try{
			
			TreasuryDealHeader treasuryDealHeader=(TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHeaderPk);
			if(treasuryDealHeader!=null)
			{
				treasuryDealHeader.setModifiedBy(userName);
				treasuryDealHeader.setModifiedDate(new Date());
				treasuryDealHeader.setIsActive(Constants.D);
				getSession().saveOrUpdate(treasuryDealHeader);
			}
			
			TreasuryDealDetail treasuryDealDetailBkFrom=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryBrFromPk);
			if(treasuryDealDetailBkFrom!=null)
			{
				treasuryDealDetailBkFrom.setModifiedBy(userName);
				treasuryDealDetailBkFrom.setModifiedDate(new Date());
				treasuryDealDetailBkFrom.setIsActive(Constants.D);
				getSession().saveOrUpdate(treasuryDealDetailBkFrom);
			}
			
			/*if(lstTreasuryStdBrFromPk!=null && lstTreasuryStdBrFromPk.size()>0)
			{
				for(BigDecimal treasuryStdBrFromPk : lstTreasuryStdBrFromPk)
				{
					TreasuryStandardInstruction treasuryStandardInstruction=(TreasuryStandardInstruction) getSession().get(TreasuryStandardInstruction.class, treasuryStdBrFromPk);
					if(treasuryStandardInstruction!=null)
					{
						treasuryStandardInstruction.setModifiedBy(userName);
						treasuryStandardInstruction.setModifiedDate(new Date());
						treasuryStandardInstruction.setIsActive(Constants.D);
						getSession().saveOrUpdate(treasuryStandardInstruction);
					}
				}
			}*/
			
			if(lstTreasuryDetailPk!=null && lstTreasuryDetailPk.size()>0)
			{
				for(BigDecimal treasuryDetailPk :lstTreasuryDetailPk)
				{
					TreasuryDealDetail treasuryDealDetailBkTo=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryDetailPk);
					if(treasuryDealDetailBkTo!=null)
					{
						treasuryDealDetailBkTo.setModifiedBy(userName);
						treasuryDealDetailBkTo.setModifiedDate(new Date());
						treasuryDealDetailBkTo.setIsActive(Constants.D);
						getSession().saveOrUpdate(treasuryDealDetailBkTo);
					}
					
				}
			}
			
			/*if(lstTreasuryStdBrToPk!=null && lstTreasuryStdBrToPk.size()>0)
			{
				for(BigDecimal treasuryStdBrToPk: lstTreasuryStdBrToPk)
				{
					TreasuryStandardInstruction treasuryStandardInstruction=(TreasuryStandardInstruction) getSession().get(TreasuryStandardInstruction.class, treasuryStdBrToPk);
					if(treasuryStandardInstruction!=null)
					{
						treasuryStandardInstruction.setModifiedBy(userName);
						treasuryStandardInstruction.setModifiedDate(new Date());
						treasuryStandardInstruction.setIsActive(Constants.D);
						getSession().saveOrUpdate(treasuryStandardInstruction);
					}
				}
			}*/
		}catch (Exception e) {
			
			throw e;
		}

		
	}
	
	
	public void deleteTreasuryStandardInstruction(BigDecimal documentYear,BigDecimal documentNo, BigDecimal treasuryDetailsId)
	{
		List<TreasuryStandardInstruction> lstTreasuryStandardInstruction= getTreasuryStandardInstruction(documentYear,documentNo,treasuryDetailsId);
		
		if(lstTreasuryStandardInstruction!=null)
		{
			for(TreasuryStandardInstruction treasuryStandardInstruction:lstTreasuryStandardInstruction)
			{
				getSession().delete(treasuryStandardInstruction);
			}
		}
	}
	
	
	
	public void deleteTreasuryDetaill(BigDecimal treasuryDetailPk ,String UserName,BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId)
	{
		TreasuryDealDetail treasuryDealDetailBkTo=(TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryDetailPk);
		if(treasuryDealDetailBkTo!=null)
		{
			/*treasuryDealDetailBkTo.setModifiedBy(UserName);
			treasuryDealDetailBkTo.setModifiedDate(new Date());
			treasuryDealDetailBkTo.setIsActive(Constants.D);
			getSession().saveOrUpdate(treasuryDealDetailBkTo);*/
			
			getSession().delete(treasuryDealDetailBkTo);
		}
		deleteTreasuryStandardInstruction(financeYear, documentNo, treasuryDetailPk);
	}
	
	
	

}
