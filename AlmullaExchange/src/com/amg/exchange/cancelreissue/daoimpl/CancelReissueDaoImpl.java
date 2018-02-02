package com.amg.exchange.cancelreissue.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cancelreissue.bean.RepushBankTrnxDataTable;
import com.amg.exchange.cancelreissue.dao.ICancelReissueDao;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRepushBankTrnxList;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"serial",  "unchecked" })
@Repository
public class CancelReissueDaoImpl<T> extends CommonDaoImpl<T> implements ICancelReissueDao<T>, Serializable {
	@Override
	public String getCompanyName(BigDecimal companyId, BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CompanyMasterDesc.class,"companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId",companyId));

		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId",languageId));

		List<CompanyMasterDesc> data = (List<CompanyMasterDesc>) findAll(criteria);
		return data.get(0).getCompanyName();
	}

	@Override
	public List<ViewRemiitanceInfo> fetchRemiitanceDetails(BigDecimal appCountryId,
			BigDecimal companyId, BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal remiitanceRefNo) {
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceView");

		critiria.add(Restrictions.eq("remittanceView.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("remittanceView.companyId", companyId));
		critiria.add(Restrictions.eq("remittanceView.documentCode", documentCode));
		critiria.add(Restrictions.eq("remittanceView.documentFinYear", documentFinanceYear));
		critiria.add(Restrictions.eq("remittanceView.documentNo", remiitanceRefNo));


		Criterion criteria1=Restrictions.eq("remittanceView.transactionStatus" , "P");
		Criterion criteria2=Restrictions.isNull("remittanceView.transactionStatus");
		LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		critiria.add( orExp );

		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
		return remittanceList;
	}

	@Override
	public List<ViewRemiitanceInfo> fetchRemittanceDetailsFileUpload(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo,
			BigDecimal bankId) {
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceView");

		critiria.add(Restrictions.eq("remittanceView.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("remittanceView.companyId", companyId));
		critiria.add(Restrictions.eq("remittanceView.documentCode", documentCode));
		critiria.add(Restrictions.eq("remittanceView.documentFinYear", documentFinanceYear));
		critiria.add(Restrictions.eq("remittanceView.documentNo", documentNo));
		critiria.add(Restrictions.eq("remittanceView.bankId", bankId));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
		return remittanceList;

	}

	@Override
	public void callCancelReIssueProcedure(HashMap<String, BigDecimal> inputMap)
			throws AMGException {

		Connection connection = null;
		CallableStatement cs;
		try{

			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_REMIT_CANCEL_REISSUE (?, ?, ?, ?, ?, ?,?,?,?,?) } ";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, inputMap.get("ApplicationCountryID"));
			cs.setBigDecimal(2, inputMap.get("NewApplicationCompanyID"));
			cs.setBigDecimal(3, inputMap.get("NewApplicationDocumentID"));
			cs.setBigDecimal(4, inputMap.get("NewApplicationFinanceYear"));
			cs.setBigDecimal(5, inputMap.get("NewApplicationDocumentNo"));
			cs.setBigDecimal(6, inputMap.get("OldRemittanceCompanyID"));
			cs.setBigDecimal(7, inputMap.get("OldRemittanceDocumentID"));
			cs.setBigDecimal(8, inputMap.get("OldRemittanceDocumentFinancialYear"));
			cs.setBigDecimal(9, inputMap.get("OldRemittanceDocumentNo"));
			//cs.setBigDecimal(10, inputMap.get("RemitAppId"));
			cs.setBigDecimal(10, inputMap.get("CountryBranchID"));

			cs.executeUpdate();
			connection.close();

		}catch (SQLException e) {
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	public List<RemittanceApplication> remittanceApplicationList(BigDecimal remittanceAppFinYear , BigDecimal remittanceAppDoc) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.add(Restrictions.eq("remittanceApplication.documentFinancialyear", remittanceAppFinYear));
		criteria.add(Restrictions.eq("remittanceApplication.documentNo", remittanceAppDoc));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceApplication>) findAll(criteria);
	}

	@Override
	public List<AdditionalInstructionData> remittanceAppAdditionalDataList(BigDecimal remittanceAppId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AdditionalInstructionData.class, "additionalInstructionData");
		criteria.setFetchMode("additionalInstructionData.exRemittanceApplication", FetchMode.JOIN);
		criteria.createAlias("additionalInstructionData.exRemittanceApplication", "exRemittanceApplication", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRemittanceApplication.remittanceApplicationId", remittanceAppId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AdditionalInstructionData>) findAll(criteria);
	}

	@Override
	public List<RemitApplAml> remittanceAppAmlList(BigDecimal remittanceAppId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemitApplAml.class, "remitApplAml");
		criteria.setFetchMode("remitApplAml.exRemittanceAppfromAml", FetchMode.JOIN);
		criteria.createAlias("remitApplAml.exRemittanceAppfromAml", "exRemittanceAppfromAml", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRemittanceAppfromAml.remittanceApplicationId", remittanceAppId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemitApplAml>) findAll(criteria);
	}

	@Override
	public List<RemittanceAppBenificiary> remittanceApplicationBeneficiaryList(BigDecimal remittanceAppId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceAppBenificiary.class, "remittanceAppBenificiary");
		criteria.setFetchMode("remittanceAppBenificiary.exRemittanceAppfromBenfi", FetchMode.JOIN);
		criteria.createAlias("remittanceAppBenificiary.exRemittanceAppfromBenfi", "exRemittanceAppfromBenfi", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRemittanceAppfromBenfi.remittanceApplicationId", remittanceAppId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceAppBenificiary>) findAll(criteria);
	}

	@Override
	public void saveRemittanceCancel(HashMap<String, Object> saveMapInfo)
			throws AMGException {

		try{

			RemittanceApplication remitApp = (RemittanceApplication)saveMapInfo.get("saveRemitAppTransaction");
			AdditionalInstructionData addData = (AdditionalInstructionData)saveMapInfo.get("saveAdditionalData");
			RemittanceAppBenificiary remitBen = (RemittanceAppBenificiary)saveMapInfo.get("saveRemitAppBen");
			RemitApplAml remitAml = (RemitApplAml)saveMapInfo.get("saveRemitAml");
			//HashMap<String, BigDecimal> hashMapProcesure = (HashMap<String, BigDecimal>) saveMapInfo.get("callProcedure");

			if(remitApp!=null){
				getSession().save(remitApp);
			}

			if(addData.getCreatedBy()!=null){
				addData.setExRemittanceApplication(remitApp);

				getSession().save(addData);
			}
			if(remitBen!=null){
				remitBen.setExRemittanceAppfromBenfi(remitApp);
				getSession().save(remitBen);
			}
			if(remitAml!=null){
				remitAml.setExRemittanceAppfromAml(remitApp);
				getSession().save(remitAml);
			}

			/*if(hashMapProcesure!=null){

				hashMapProcesure.put("RemitAppId", remitApp.getRemittanceApplicationId());
				callCancelReIssueProcedure(hashMapProcesure);
			}*/

		}catch(Exception ae)
		{
			throw new AMGException(ae.getMessage());
		}



	}

	@Override
	public String getFutherInstruction(BigDecimal remittanceTransactionId)
			throws AMGException {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.add(Restrictions.eq("remittanceTransaction.remittanceTransactionId", remittanceTransactionId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		String rtnInstruction="";
		try{
			String hqlQuery="select a.instruction from  RemittanceTransaction a  where a.remittanceTransactionId =  :premittanceTransactionId";

			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("premittanceTransactionId", remittanceTransactionId);

			List<String> lstInstruction = query.list();;

			if(lstInstruction!=null && lstInstruction.size()>0)
			{
				rtnInstruction=lstInstruction.get(0);
			}

		}catch(Exception e)
		{
			throw new AMGException("Error");
		}

		return rtnInstruction;

	}

	@Override
	public void deleteApplTrx(BigDecimal pkRemittanceApplicationId) {
		RemittanceApplication remiApplication=(RemittanceApplication) getSession().get(RemittanceApplication.class, pkRemittanceApplicationId);
		remiApplication.setIsactive(Constants.D);
		getSession().update(remiApplication);
	}

	@Override
	public void deleteApplAMl(BigDecimal pkRemitApplAmlId) {
		RemitApplAml applAml=(RemitApplAml) getSession().get(RemitApplAml.class, pkRemitApplAmlId);
		applAml.setIsactive(Constants.D);
		getSession().update(applAml);
	}

	@Override
	public void deleteApplBene(BigDecimal pkRemittanceAppBenificiaryId) {
		RemittanceAppBenificiary remitApplBene=(RemittanceAppBenificiary) getSession().get(RemittanceAppBenificiary.class, pkRemittanceAppBenificiaryId);
		remitApplBene.setIsactive(Constants.D);
		getSession().update(remitApplBene);
	}

	@Override
	public void deleteApplAddlData(BigDecimal pkAdditionalInstructionDataId) {
		AdditionalInstructionData additionalData=(AdditionalInstructionData) getSession().get(AdditionalInstructionData.class, pkAdditionalInstructionDataId);
		additionalData.setIsactive(Constants.D);
		getSession().update(additionalData);
	}

	@Override
	public  BigDecimal procedureCallForSave(HashMap<String, String> saveMapInfo)throws AMGException {
		Connection connection = null;
		CallableStatement cs;
		BigDecimal TranxNum=null;
 		try{
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_CANCEL_REISSUE_APPL (?, ?, ?, ?, ?, ?,?,?)} ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(saveMapInfo.get("ApplicationCountryID")));
			cs.setBigDecimal(2, new BigDecimal(saveMapInfo.get("OldRemittanceCompanyID")));
			cs.setBigDecimal(3, new BigDecimal(saveMapInfo.get("OldRemittanceDocumentID")));
			cs.setBigDecimal(4, new BigDecimal(saveMapInfo.get("OldRemittanceDocumentFinancialYear")));
			cs.setBigDecimal(5, new BigDecimal(saveMapInfo.get("OldRemittanceDocumentNo")));
 			cs.setBigDecimal(6, new BigDecimal(saveMapInfo.get("CountryBranchID")));
 			cs.setString(7, saveMapInfo.get("loginUser"));
 			cs.registerOutParameter(8, java.sql.Types.NUMERIC);
 			cs.execute();
 		 TranxNum=cs.getBigDecimal(8);
   
 		} catch (SQLException e) {
			String erromsg = "EX_P_CANCEL_REISSUE_APPL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
		 
				String erromsg = "EX_P_CANCEL_REISSUE_APPL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
 			
 			
		return TranxNum;
 			
 			
 			
			/*} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new AMGException(e.getMessage());
			}*/
	
	}

	@Override
	public List<ViewRepushBankTrnxList> getBankRejectedTrnxListFromView(HashMap<String, String> inputParametrs) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRepushBankTrnxList.class, "viewRepushBankTrnxList");
		if(inputParametrs.get("DOCFYR") !=null && inputParametrs.get("BNKCODE")!=null){
		criteria.add(Restrictions.eq("viewRepushBankTrnxList.docfyr", new BigDecimal(inputParametrs.get("DOCFYR"))));
		criteria.add(Restrictions.eq("viewRepushBankTrnxList.bankCode", inputParametrs.get("BNKCODE")));
		if(inputParametrs.get("DOC_NO")!= null){
			criteria.add(Restrictions.eq("viewRepushBankTrnxList.docNumber", new BigDecimal(inputParametrs.get("DOC_NO"))));
		}
				
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ViewRepushBankTrnxList>) findAll(criteria);
		}else{
			return null;
		}

	}

	@Override
	public Boolean updateBankRejectedTrnxList(List<RepushBankTrnxDataTable> list, HashMap<String, String> inputParametrs) {
		Boolean status= false;
		if(list!= null && list.size()>0){
			
			for(RepushBankTrnxDataTable viewRepushBankTrnx : list){
				if(viewRepushBankTrnx.getDocNumber()!= null && viewRepushBankTrnx.getBankCode()!=null){
				String hqlQuery = "update transfer set delvind=null ,INT_REMARK =null ,UPDDAT= sysdate , MODIFIER ='"+inputParametrs.get("USER_NAME")+"'  "
						+ " where  docfyr="+viewRepushBankTrnx.getDocfyr()+" "
						+ " and corsbnk='"+viewRepushBankTrnx.getBankCode()+"' and DOCNO ="+viewRepushBankTrnx.getDocNumber()+" "
						+ " and PRINTMOD='W' and  FILECRT='N' and NVL(DELVIND,' ')='R' and NVL(TRNFSTS, ' ')<>'P' and NVL(CANSTS,' ')   <> 'C'";
				System.out.println("hqlQuery :"+hqlQuery);
				SQLQuery query = getSession().createSQLQuery(hqlQuery);
				int i = query.executeUpdate();
				status=true;
				}
			}
			
		}
			return status;
		
	}

}
