package com.amg.exchange.jvprocess.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.jvprocess.bean.DayBookDetailsWithDenomination;
import com.amg.exchange.jvprocess.bean.JVReasonDTO;
import com.amg.exchange.jvprocess.dao.IJVProcessDao;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewGeneralValidationGlNo;
import com.amg.exchange.jvprocess.model.ViewSlNumber;
import com.amg.exchange.remittance.model.ParameterDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
@SuppressWarnings("unchecked")
@Repository
public class JVProcessDaoImpl<T>  extends CommonDaoImpl<T> implements IJVProcessDao<T>,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<CurrencyMasterDTO> getCurrencyMaster() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		//dCriteria.addOrder(Order.asc("currencyMaster.currencyName"));
		dCriteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		
		ProjectionList columns = Projections.projectionList();
		
		//columns.add(Projections.distinct(Projections.property("currencyId")), "currencyId");
		
		columns.add(Projections.distinct(Projections.property("currencyCode")), "currencyCode");
		columns.add(Projections.property("currencyId"), "currencyId");
		//columns.add(Projections.property("currencyCode"),"currencyCode");
		columns.add(Projections.property("currencyName"), "currencyDecs");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(CurrencyMasterDTO.class) );
		
		List<CurrencyMasterDTO> lstCurrencyMasterDTO=  (List<CurrencyMasterDTO>) findAll(dCriteria);
		
		
		return lstCurrencyMasterDTO;
	}

	@Override
	public List<CurrencyMasterDTO> getCurrencyOfBank(BigDecimal bankId) {
		
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.addOrder(Order.asc("fsCurrencyMaster.currencyName"));/**NAG APPLY ASCENDING ORDER 04/02/2014**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		ProjectionList columns = Projections.projectionList();
		
		//columns.add(Projections.distinct(Projections.property("fsCurrencyMaster.currencyId")), "currencyId");
		columns.add(Projections.distinct(Projections.property("fsCurrencyMaster.currencyCode")), "currencyCode");
		columns.add(Projections.property("fsCurrencyMaster.currencyId"), "currencyId");
		//columns.add(Projections.property("fsCurrencyMaster.currencyCode"),"currencyCode");
		columns.add(Projections.property("fsCurrencyMaster.currencyName"), "currencyDecs");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(CurrencyMasterDTO.class) );
		
		List<CurrencyMasterDTO> lstCurrencyMasterDTO=  (List<CurrencyMasterDTO>) findAll(dCriteria);
		
		return lstCurrencyMasterDTO;
	}

	
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAcoountDetails"); 
		
		dCriteria.setFetchMode("bankAcoountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		
		dCriteria.setFetchMode("bankAcoountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyId));
		
		dCriteria.addOrder(Order.asc("bankAcoountDetails.bankAcctNo"));	/** NAG APPLY ASCENDING 04/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(dCriteria);
		List<BankAccountDetails> finalData = new ArrayList<BankAccountDetails>();
		
		for (BankAccountDetails bankAccountDetails : data) {
			//if(new SimpleDateFormat("dd/MM/yyyy").format(bankAccountDetails.getCreateDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MM/yyyy").format(new Date()))) {
				finalData.add(bankAccountDetails);
			//}
		}
		
		return finalData;
	}

	@Override
	public List<DayBookHeader> getDocumentNo(BigDecimal documnetId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");

		dCriteria.setFetchMode("dayBookHeader.doucDocumentId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookHeader.doucDocumentId", "doucDocumentId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("doucDocumentId.documentID", documnetId));
		dCriteria.add(Restrictions.eq("dayBookHeader.isActive",Constants.U));
		//dCriteria.setProjection(Projections.property("dayBookHeader.documentNumber"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DayBookHeader> dayBookList = (List<DayBookHeader>) findAll(dCriteria);

		return dayBookList;
	}

	@Override
	public List<DayBookHeader> getDayBookHeaderRecord(BigDecimal documentId,BigDecimal documentNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");
		
		dCriteria.setFetchMode("dayBookHeader.doucDocumentId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookHeader.doucDocumentId", "doucDocumentId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("doucDocumentId.documentID", documentId));
		dCriteria.add(Restrictions.eq("dayBookHeader.documentNumber", documentNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DayBookHeader>) findAll(dCriteria);
	}

	@Override
	public List<DayBookDetails> getDayBookDetailRecord(BigDecimal daybookheaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookDetails.class, "dayBookDetails");
		dCriteria.setFetchMode("dayBookDetails.dayBookHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookHeaderId", "dayBookHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dayBookHeaderId.daybookHeaderId", daybookheaderId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DayBookDetails>) findAll(dCriteria);
	}

	@Override
	public List<String> getGlACNOAutoComplete(String position, String inputValue) {
		
		List<String> rtnList=new ArrayList<String>();
		if(position.equalsIgnoreCase("One"))
		{
			
			
			String sql="select  * from FS_COMPANY_MASTER companymas0_ where companymas0_.COMPANY_CODE like '"+inputValue +"%'";

			SQLQuery ss=getSession().createSQLQuery(sql);
			List<CompanyMaster> lstCompanyMaster=ss.addEntity(CompanyMaster.class).list(); 
			
			for(CompanyMaster companyMaster: lstCompanyMaster)
			{
				rtnList.add(companyMaster.getCompanyCode().toPlainString());
			}
			/*String hqlQuery="select distinct a.companyCode from CompanyMaster a where a.companyCode like " +"' "+ ":componentID"+" %'";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("componentID", new BigDecimal(inputValue));


			List<BigDecimal> temp =	query.list();
			
			for(BigDecimal value:temp)
			{
				rtnList.add(value.toString());
			}*/
			/*DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
			//criteria.add(Restrictions.like("companyMaster.companyCode", new BigDecimal(inputValue) , MatchMode.ANYWHERE).ignoreCase());
			criteria.add(Restrictions.like("companyMaster.companyCode", new BigDecimal(inputValue)));
			
			//criteria.setProjection(Projections.property("companyMaster.companyCode"));
			criteria.addOrder(Order.asc("companyMaster.companyCode"));
			//criteria.add(Restrictions.eq("serviceMaster.isActive",  "N"));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			
			ProjectionList columns = Projections.projectionList();
			columns.add(Projections.distinct(Projections.property("companyMaster.companyCode")), "currencyId");
			criteria.setProjection(columns);
			criteria.setResultTransformer( new AliasToBeanResultTransformer(CurrencyMasterDTO.class) );
			
			List<CurrencyMasterDTO> temp1 = (List<CurrencyMasterDTO>)findAll(criteria);
			
			for(CurrencyMasterDTO value:temp1)
			{
				rtnList.add(value.getCurrencyId().toPlainString());
			}
			
			
			List<BigDecimal> temp = (List<BigDecimal>)findAll(criteria);
			
			for(BigDecimal value:temp)
			{
				rtnList.add(value.toString());
			}*/
			
		}else if(position.equalsIgnoreCase("Two"))
		{
			String hqlQuery="select distinct a.al1Cod from ViewmainActivityALev1 a where a.al1Cod like ?";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter(0, "%"+inputValue+"%");


			rtnList =	query.list();
			
		}else if(position.equalsIgnoreCase("Three"))
		{

			String hqlQuery="select distinct a.al2Cod from ViewBusinessoperationAlev2 a where a.al2Cod like ?";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter(0, "%"+inputValue+"%");
			rtnList =	query.list();
			
		}else if(position.equalsIgnoreCase("Four"))
		{
			String hqlQuery="select distinct a.al3Cod from ViewFranchiseAlev3 a where a.al3Cod like ?";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter(0, "%"+inputValue+"%");


			rtnList =	query.list();
			
		}else if(position.equalsIgnoreCase("Five"))
		{
			String hqlQuery="select distinct a.al4Cod from ViewProductAlev4 a where a.al4Cod like ?";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter(0, "%"+inputValue+"%");


			rtnList =	query.list();
		}else if(position.equalsIgnoreCase("Six"))
		{
			/*String hqlQuery="select distinct a.acntcod from ViewActivitycenterAcnt a where a.acntcod like ?";


			Query query = getSession().createQuery(hqlQuery);
			query.setParameter(0, "%"+inputValue+"%");


			rtnList =	query.list();*/
			String sql="select  * from V_ACNT a where a.ACNTCOD like '"+inputValue +"%'";

			SQLQuery ss=getSession().createSQLQuery(sql);
			List<ViewActivitycenterAcnt> lstViewActivitycenterAcnt=ss.addEntity(ViewActivitycenterAcnt.class).list(); 
			for(ViewActivitycenterAcnt viewActivitycenterAcnt: lstViewActivitycenterAcnt)
			{
				rtnList.add(viewActivitycenterAcnt.getAcntcod().toPlainString());
			}
			
		}else if(position.equalsIgnoreCase("Seven"))
		{
			
		}
		return rtnList;
	}

	@Override
	public HashMap<String, String> glAccNoValidation(
			HashMap<String, String> inputValues) throws AMGException{
		Connection connection = null;
		CallableStatement cs;
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		try{

			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_JV_GL_VALIDATION (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("PApplCntyId")));
			cs.setString(2, inputValues.get("PComcod"));
			cs.setString(3, inputValues.get("PAl1cod"));
			cs.setString(4, inputValues.get("PAl2cod"));
			cs.setString(5, inputValues.get("PAl3cod"));
			cs.setString(6, inputValues.get("PAl4cod"));
			cs.setString(7, inputValues.get("PAcntcod"));
			cs.setString(8, inputValues.get("PAclscod"));
			cs.setString(9, inputValues.get("PBankid"));
			cs.setBigDecimal(10, new BigDecimal(inputValues.get("PCurrencyid")));
			cs.setString(11, inputValues.get("PBankAccountNo"));
			
			Date uDate=new SimpleDateFormat("dd/MM/yyyy").parse(inputValues.get("p_jv_date"));
			java.sql.Date sDate = new java.sql.Date(uDate.getTime());

			cs.setDate(12, sDate);
			
			cs.setString(13, inputValues.get("PBankCashId"));
			cs.registerOutParameter(14, java.sql.Types.VARCHAR);
			cs.registerOutParameter(15, java.sql.Types.VARCHAR);
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);
			cs.registerOutParameter(17, java.sql.Types.VARCHAR);
			cs.registerOutParameter(18, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			String pGldes=cs.getString(14);
			String pSlno=cs.getString(15);
			String pSlid=cs.getString(16);
			String pSubcre=cs.getString(17);
			String pErrorMesg=cs.getString(18);
			rtnValues.put("pGldes", pGldes);
			rtnValues.put("pSlno", pSlno);
			rtnValues.put("pSlid", pSlid);
			rtnValues.put("pSubcre", pSubcre);
			rtnValues.put("pErrorMesg", pErrorMesg);
			connection.close();
			
		}catch (SQLException e) {
			
			throw new AMGException();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtnValues;
	}
	@Override
	public HashMap<String, String> jvSubCodeValidation(HashMap<String, String> inputValues) throws AMGException
	{
		Connection connection = null;
		CallableStatement cs;
		HashMap<String, String> rtnValues = new HashMap<String, String>();
		try{

			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_JV_SLNO_VALIDATION (?, ?, ?, ?, ?, ?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_APPL_CNTY_ID")));
			cs.setString(2, inputValues.get("P_COMCOD"));
			cs.setString(3, inputValues.get("P_AL1COD"));
			cs.setString(4, inputValues.get("P_AL2COD"));
			cs.setString(5, inputValues.get("P_AL3COD"));
			cs.setString(6, inputValues.get("P_AL4COD"));
			cs.setString(7, inputValues.get("P_ACNTCOD"));
			cs.setString(8, inputValues.get("P_ACLSCOD"));
			cs.setString(9, inputValues.get("P_SUBCOD"));
		
			Date uDate=new SimpleDateFormat("dd/MM/yyyy").parse(inputValues.get("P_JV_DATE"));
			java.sql.Date sDate = new java.sql.Date(uDate.getTime());

			cs.setDate(10, sDate);
			
			cs.setString(11, inputValues.get("P_CURRENCY_CODE"));
			cs.registerOutParameter(12, java.sql.Types.VARCHAR);
			cs.registerOutParameter(13, java.sql.Types.VARCHAR);
			
			cs.executeUpdate();
			String pSlNoDesc=cs.getString(12);
			String pErrorMesg=cs.getString(13);
			rtnValues.put("pSlNoDesc", pSlNoDesc);
			rtnValues.put("pErrorMesg", pErrorMesg);
			connection.close();
			
		}catch (SQLException e) {
			
			throw new AMGException();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rtnValues;
		
	}

	@Override
	public List<ForeignCurrencyAdjust> getCurrencyAdjustRecord(BigDecimal companyId, BigDecimal countryId,BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo, BigDecimal documentLineNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ForeignCurrencyAdjust.class, "foreignCurrencyAdjust");
		dCriteria.setFetchMode("foreignCurrencyAdjust.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("foreignCurrencyAdjust.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setFetchMode("foreignCurrencyAdjust.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("foreignCurrencyAdjust.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		dCriteria.add(Restrictions.eq("foreignCurrencyAdjust.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("foreignCurrencyAdjust.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("foreignCurrencyAdjust.documentFinanceYear", documentFinanceYr));
		dCriteria.add(Restrictions.eq("foreignCurrencyAdjust.documentLineNumber", documentLineNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ForeignCurrencyAdjust>) findAll(dCriteria);
	}

	@Override
	public void approve(BigDecimal daybookheaderId,String approveBy, Date approveDate) {
		DayBookHeader dayBookHeader=(DayBookHeader) getSession().get(DayBookHeader.class, daybookheaderId);
		dayBookHeader.setIsActive(Constants.Yes);
		dayBookHeader.setApprovedDate(approveDate);
		dayBookHeader.setApprovedBy(approveBy);
		getSession().update(dayBookHeader);
		
	}
	
	@Override
	public List<String> getDocumentLst(String name){
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewActivitycenterAcnt.class, "viewActivitycenterAcnt");
		criteria.add(Restrictions.like("viewActivitycenterAcnt.acntcodAsString", name, MatchMode.ANYWHERE).ignoreCase());
		 
		criteria.setProjection(Projections.property("viewActivitycenterAcnt.acntcodAsString"));
		criteria.addOrder(Order.asc("viewActivitycenterAcnt.acntcodAsString"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 
		return (List<String>)findAll(criteria);
	}
	
	@Override
	public BigDecimal getBanlAccDtlsIDBasedOnAccountNo(String accountNo){
		

		String hqlQuery="select a.bankAcctDetId from  BankAccountDetails a  where a.bankAcctNo =  :bnkAccNo";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("bnkAccNo", accountNo);

		List<BigDecimal> lstIdentity =query.list();

		BigDecimal bankAcctDetId=BigDecimal.ZERO;
		if(lstIdentity.size()>0)
		{
			bankAcctDetId=lstIdentity.get(0);
		}
		return bankAcctDetId;
	}

	@Override
	public void saveJVProcess(HashMap<String, Object> saveMap) throws Exception {
		
		DayBookHeader dayBookHeader=(DayBookHeader) saveMap.get("DayBookHeader");
		List<DayBookDetailsWithDenomination> lstDayBookDetailsWithDenomination=(List<DayBookDetailsWithDenomination>) saveMap.get("DayBookDetailsWithDenomination");
		try{
			getSession().saveOrUpdate(dayBookHeader);
			
			for(DayBookDetailsWithDenomination dayBookDetailsWithDenomination :lstDayBookDetailsWithDenomination)
			{
				DayBookDetails dayBookDetails= dayBookDetailsWithDenomination.getDayBookDetails();
				dayBookDetails.setDayBookHeaderId(dayBookHeader);
				
				getSession().saveOrUpdate(dayBookDetails);
				
				List <ForeignCurrencyAdjust> lstForeignCurrencyAdjust=dayBookDetailsWithDenomination.getLstForeignCurrencyAdjust();
				if(lstForeignCurrencyAdjust!=null)
				{
					for(ForeignCurrencyAdjust foreignCurrencyAdjust :lstForeignCurrencyAdjust)
					{
						getSession().saveOrUpdate(foreignCurrencyAdjust);
					}
				}
				
				
			}
		}catch(Exception ex)
		{
			throw new Exception();
		}
		
	}
	

	@Override
	public List<Object[]> getCurrencyDenomination(BigDecimal currencyId,String userName,Date jvRefDate){
		
		List<Object[]> objList = new ArrayList<Object[]>();
		String queryString = "SELECT B.CURRENCY_ID, B.DENOMINATION_ID, B.DENOMINATION_DESC,  B.DENOMINATION_AMOUNT, "
       +"(SELECT (OPEN_QTY + PURCHASE_QTY + SALE_QTY - TRANF_QTY + RECEIVED_QTY) FROM EX_STOCK A "
       +"WHERE A.DENOMINATION_ID = B.DENOMINATION_ID AND TRUNC(LOG_DATE) = TRUNC(?) AND ORACLE_USER=?) "   
       +"CLOSE_QTY  from EX_CURRENCY_DENOMINATION B WHERE CURRENCY_ID=?" ;
		objList = getSession().createSQLQuery(queryString).setDate(0, jvRefDate).setString(1, userName).setBigDecimal(2, currencyId).list();
		return objList;
	}

	@Override
	public List<CurrencyWiseDenomination> getDenominationList(
			BigDecimal denominationId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyWiseDenomination.class, "currencyWiseDenomination");
		
		dCriteria.add(Restrictions.eq("currencyWiseDenomination.denominationId", denominationId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CurrencyWiseDenomination> denominationList = (List<CurrencyWiseDenomination>) findAll(dCriteria);

		return denominationList;
	}

	@Override
	public String getBankAccountNo(BigDecimal bankAccountId) {

		String hqlQuery="select bankAcctNo from  BankAccountDetails   where bankAcctDetId =  :bankAccountId";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("bankAccountId", bankAccountId);

		List<String> lstIdentity =query.list();

		String bankAcctNo=null;
		if(lstIdentity.size()>0)
		{
			bankAcctNo=lstIdentity.get(0);
		}
		return bankAcctNo;
	}
	
	@Override
	public List<DayBookHeader> getDocumentNoForEnquiry(BigDecimal documnetId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");

		dCriteria.setFetchMode("dayBookHeader.doucDocumentId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookHeader.doucDocumentId", "doucDocumentId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("doucDocumentId.documentID", documnetId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DayBookHeader> dayBookList = (List<DayBookHeader>) findAll(dCriteria);

		return dayBookList;
	}

	@Override
	public List<JVReasonDTO> getReasonDetails() {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ParameterDetails.class, "parameterDetails");
		dCriteria.add(Restrictions.eq("parameterDetails.recordId", Constants.JV_REASON));
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.distinct(Projections.property("parameterDetailsId")), "parmeterDetailsId");
		columns.add(Projections.property("recordId"), "recordId");
		columns.add(Projections.property("paramCodeDef"), "paramCodeDef");
		columns.add(Projections.property("fullDesc"), "paramFullDesc");
		
		dCriteria.setProjection(columns);
		
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(JVReasonDTO.class) );
		
		
		List<JVReasonDTO> lstJVReasonDTO=  (List<JVReasonDTO>) findAll(dCriteria);
		
		
		return lstJVReasonDTO;
	}

	@Override
	public String getGlDescription(String glNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewGeneralValidationGlNo.class, "viewGeneralValidationGlNo");
		dCriteria.add(Restrictions.eq("viewGeneralValidationGlNo.glno", glNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ViewGeneralValidationGlNo> lstViewGeneralValidationGlNo=  (List<ViewGeneralValidationGlNo>) findAll(dCriteria);
		
		String glDes="";
		if(lstViewGeneralValidationGlNo.size()>0)
		{
			glDes=lstViewGeneralValidationGlNo.get(0).getGldes();
		}
		
		//return ((ViewGeneralValidationGlNo) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getGldes();
		return glDes;
	}

	@Override
	public String getSlDescription(String slNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewSlNumber.class, "viewSlNumber");
		dCriteria.add(Restrictions.eq("viewSlNumber.slNo", slNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ViewSlNumber> lstViewSlNumber=  (List<ViewSlNumber>) findAll(dCriteria);
		String slDesc="";
		if(lstViewSlNumber.size()>0)
		{
			 slDesc=lstViewSlNumber.get(0).getSlDesc();
		}
		//return ((ViewSlNumber) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getSlDesc();
		return slDesc;
	}

	@Override
	public void deleteCurrencyAdjust(BigDecimal companyId,
			BigDecimal countryId, BigDecimal documentCode,
			BigDecimal documentFinanceYr, BigDecimal documentNo,
			BigDecimal documentLineNo) {
		
		List<ForeignCurrencyAdjust> lstForeignCurrencyAdjust =getCurrencyAdjustRecord(companyId,countryId,documentCode,documentFinanceYr,documentNo,documentLineNo);
		if(lstForeignCurrencyAdjust!=null)
		{
			for(ForeignCurrencyAdjust foreignCurrencyAdjust :lstForeignCurrencyAdjust)
			{
				getSession().delete(foreignCurrencyAdjust);
			}
		}
		
	}

	@Override
	public void deleteDayBookDetails(BigDecimal dayBooDtlsId) {
		
		DayBookDetails dayBookDetails=(DayBookDetails) getSession().get(DayBookDetails.class, dayBooDtlsId);
		if(dayBookDetails!=null)
		{
			getSession().delete(dayBookDetails);
		}
			
	}
	
	
}
