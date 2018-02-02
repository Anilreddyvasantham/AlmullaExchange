package com.amg.exchange.wuh2h.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.TempCollectDetail;
import com.amg.exchange.foreigncurrency.model.TempCollection;
import com.amg.exchange.foreigncurrency.model.TempForeignCurrencyAdjust;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.bean.ReceiveReceiptView;
import com.amg.exchange.wuh2h.bean.WUStateCityModel;
import com.amg.exchange.wuh2h.bean.WUTermsAndConditions;
import com.amg.exchange.wuh2h.dao.IWUH2HDao;
import com.amg.exchange.wuh2h.model.DynamicFileds;
import com.amg.exchange.wuh2h.model.DynamicPurposeCode;
import com.amg.exchange.wuh2h.model.ViewWUSendTransaction;
import com.amg.exchange.wuh2h.model.WUCorridorCountry;
import com.amg.exchange.wuh2h.model.WUCorridorCurrency;
import com.amg.exchange.wuh2h.model.WURemittanceApplicationView;
import com.amg.exchange.wuh2h.model.WUReportTermsAndConditions;

@SuppressWarnings("unchecked")
@Repository
public class WUH2HDaoImpl<T> extends CommonDaoImpl<T> implements IWUH2HDao,Serializable { 
	
	private static final long serialVersionUID = 1L;
	public static final Logger log=Logger.getLogger(WUH2HDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public void saveCollection(Collect collection, List<CollectDetail> collectionDetailList){		
		BigDecimal collectionId =null;
	}
	
	@Override
	public BigDecimal saveTempCollectionwithDetailsandTempCurrencyAdjust(TempCollection tempCollection, List<TempCollectDetail> tempDetailsList,
			List<TempForeignCurrencyAdjust> tempAdjustList) throws Exception {
		BigDecimal collectionId;
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
			
		} catch (Exception e) {
			log.info("Exception occured" + e);
			throw new Exception(e);
		}
		return collectionId;
	}
	
	public BigDecimal saveCollection(TempCollection tempCollection) {
		log.info("Entering into saveCollection method");
		return (BigDecimal) getSession().save(tempCollection);
	}

	public void saveDetail(TempCollectDetail TempCollectDetail) {
		log.info("Entering into saveDetail method");
		getSession().saveOrUpdate(TempCollectDetail);
		log.info("Exit into saveDetail method");
	}

	public void saveAdjust(TempForeignCurrencyAdjust tempForeignCurrencyAdjust) {
		log.info("Entering into saveAdjust method");
		getSession().saveOrUpdate(tempForeignCurrencyAdjust);
		log.info("Exit into saveAdjust method");
	}
	
	@Override
	public String getNameDescription(BigDecimal id, BigDecimal languageId, String option) {
		DetachedCriteria dCriteria = null;
		String nameDescription=null;
		//Get City Name
		if(option.equalsIgnoreCase("city")){
			dCriteria = DetachedCriteria.forClass(CityMasterDesc.class, "cityMasterDesc");
			dCriteria.setFetchMode("cityMasterDesc.fsCityMaster",FetchMode.JOIN);
			dCriteria.createAlias("cityMasterDesc.fsCityMaster","fsCityMaster", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("cityMasterDesc.fsLanguageType.languageId", languageId));
			dCriteria.add(Restrictions.eq("cityMasterDesc.cityMasterId", id));
			
			nameDescription = ((CityMasterDesc) dCriteria
					.getExecutableCriteria(getSession()).list().get(0))
					.getCityName();			
		}
		//Get State Name
		if(option.equalsIgnoreCase("state")){
			dCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
			dCriteria.setFetchMode("stateMasterDesc.fsStateMaster",FetchMode.JOIN);
			dCriteria.createAlias("stateMasterDesc.fsStateMaster","fsStateMaster", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("stateMasterDesc.fsLanguageType.languageId", languageId));
			dCriteria.add(Restrictions.eq("stateMasterDesc.stateDescId", id));
			
			nameDescription = ((StateMasterDesc) dCriteria
					.getExecutableCriteria(getSession()).list().get(0))
					.getStateName();					
		}
		//Get District Name
		if(option.equalsIgnoreCase("district")){
			dCriteria = DetachedCriteria.forClass(DistrictMasterDesc.class, "districtMasterDesc");
			dCriteria.setFetchMode("districtMasterDesc.fsDistricteMaster",FetchMode.JOIN);
			dCriteria.createAlias("districtMasterDesc.fsDistricteMaster","fsDistricteMaster", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("districtMasterDesc.fsLanguageType.languageId", languageId));
			dCriteria.add(Restrictions.eq("districtMasterDesc.districtDescId", id));
			
			nameDescription = ((DistrictMasterDesc) dCriteria
					.getExecutableCriteria(getSession()).list().get(0))
					.getDistrict();			
		}
		//Get Country Name
		if(option.equalsIgnoreCase("country")){
			dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
			dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",FetchMode.JOIN);
			dCriteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("countryMasterDesc.fsLanguageType.languageId", languageId));
			dCriteria.add(Restrictions.eq("countryMasterDesc.fsCountryMaster.countryId", id));
			
			nameDescription = ((CountryMasterDesc) dCriteria
					.getExecutableCriteria(getSession()).list().get(0))
					.getCountryName();					
		}
		if(option.equalsIgnoreCase("countryalpha")){
			dCriteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
			dCriteria.add(Restrictions.eq("countryMaster.countryId", id));
			
			nameDescription = ((CountryMaster) dCriteria
					.getExecutableCriteria(getSession()).list().get(0))
					.getCountryAlpha2Code();					
		}
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		if (nameDescription != null && nameDescription.length() > 0) {
			return nameDescription;
		} else {
			return "";
		}
	}
	
	
	@Override
	public void saveAllApplTransaction(HashMap<String, Object> mapWUH2HAllAppl) throws AMGException {
		log.info("Entered into saveAllApplTransaction() method ");
		RemittanceApplication saveApplTrnx = (RemittanceApplication) mapWUH2HAllAppl.get("EX_APPL_TRNX");
		RemittanceAppBenificiary saveApplBene = (RemittanceAppBenificiary) mapWUH2HAllAppl.get("EX_APPL_BENE");
		BigDecimal financialYear = (BigDecimal) mapWUH2HAllAppl.get("FinancialYear");
		List<RemitApplAml> saveApplAmlList = (List<RemitApplAml>) mapWUH2HAllAppl.get("EX_APPL_AML");		
		List<AdditionalInstructionData> saveApplAddlData = (List<AdditionalInstructionData>) mapWUH2HAllAppl.get("EX_APPL_ADDL_DATA");
		
		/*
		List<AdditionalInstructionData> saveApplAddlData = (List<AdditionalInstructionData>) mapAllDetailPersonalRemittanceApplSave.get("EX_APPL_ADDL_DATA");
		BigDecimal spotRatePk = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("SPOT_RATE_PK");
		BigDecimal financialYear = (BigDecimal) mapAllDetailPersonalRemittanceApplSave.get("FinancialYear");
		// spl customer deal
		List<CustomerSpecialDealAppl> lstCustSplDealReq = (List<CustomerSpecialDealAppl>) mapAllDetailPersonalRemittanceApplSave.get("EX_CUSTOMER_SPECIAL_DEAL_APPL");

		List<AuthorizedLog> lstAuthorizedLog= (List<AuthorizedLog>)mapAllDetailPersonalRemittanceApplSave.get("AMLLOGSAVE");

		// mobile number saving in fs_customer , fs_customer_contacts_id
		String mobileChange = (String) mapAllDetailPersonalRemittanceApplSave.get("Cust_Mobile_Change");*/


		try {

			// save the EX_APPL_TRNX
			if (saveApplTrnx != null) {
				String processIn = Constants.U;
			/*	String docrefNumber = getNextDocumentReferenceNumber(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),
						new BigDecimal(Constants.DOCUMENT_CODE_FOR_WU), financialYear, processIn,
						sessionStateManage.getCountryBranchCode());*/
				
				String docrefNumber = getNextDocumentReferenceNumber(sessionStateManage.getCountryId(), sessionStateManage.getCompanyId(),
						new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMITTANCE_APPLICATION), financialYear, processIn,
						sessionStateManage.getCountryBranchCode());	
				saveApplTrnx.setDocumentNo(new BigDecimal(docrefNumber));
				getSession().saveOrUpdate(saveApplTrnx);
			}
			
			// save the EX_APPL_BENE
			if (saveApplBene != null) {
				saveApplBene.setDocumentNo(saveApplTrnx.getDocumentNo());
				getSession().saveOrUpdate(saveApplBene);
			}
			// save the EX_APPL_AML
			if (saveApplAmlList != null) {
				for (RemitApplAml remitApplAml : saveApplAmlList) {
					getSession().saveOrUpdate(remitApplAml);
				}
			}			
			
			// save the EX_APPL_ADDL_DATA
			if (saveApplAddlData != null && saveApplAddlData.size() != 0) {
				for (AdditionalInstructionData additionalInstructionData : saveApplAddlData) {
					additionalInstructionData.setDocumentNo(saveApplTrnx.getDocumentNo());
					getSession().saveOrUpdate(additionalInstructionData);
				}
			}
			
			log.info("Exit into saveTransaction method");

		} catch (Exception e) {
			log.info("Problem to redirect: " + e.getMessage());
			throw new AMGException(e.getMessage());
		}
	}
	
	public String getNextDocumentReferenceNumber(BigDecimal countryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYear,
			String processIn, BigDecimal branchId) {

		int out = 0;
		// Added by Rabil 18072015
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO countryId :" + countryId);
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO companyId :" + companyId);
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO documentId :" + documentId);
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO financialYear :" + financialYear);
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO processIn :" + processIn);
		log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO branchId :" + branchId);

		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, countryId);
			cs.setBigDecimal(2, branchId);
			cs.setBigDecimal(3, companyId);
			cs.setBigDecimal(4, documentId);
			cs.setBigDecimal(5, financialYear);
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

			log.info("EX_TO_GEN_NEXT_DOC_SERIAL_NO out Value :" + out);

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
	
	
	public  List<CurrencyMaster> getWUh2HCurrencyList(BigDecimal applicationCurrencyId, BigDecimal customerCurrencyId){
		
		List<BigDecimal> currencyListid = new ArrayList<BigDecimal>();
		currencyListid.add(applicationCurrencyId);
		currencyListid.add(customerCurrencyId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");		
		criteria.add(Restrictions.in("currencyMaster.currencyId", currencyListid));	
		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CurrencyMaster>) findAll(criteria);
		
	}
	
	public BigDecimal getRemitModeForCash()
	{
		BigDecimal remitModeid=BigDecimal.ZERO;
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		
		criteria.setFetchMode("remittanceModeMaster.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("serviceMaste.serviceGroupId", FetchMode.JOIN);
		criteria.createAlias("serviceMaster.serviceGroupId", "serviceGroupId", JoinType.INNER_JOIN);		
		criteria.add(Restrictions.eq("serviceGroupId.serviceGroupCode", Constants.CASHFORONLINE));		
		
		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("remittanceModeId")), "populateId");
		
		//columns.add(Projections.property("bankId"), "populateId");
		//columns.add(Projections.property("bankCode"),"populateCode");
		//columns.add(Projections.property("bankName"), "populateName");

		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);
		
		if(lstPopulateData.size()>0)
		{
			remitModeid=lstPopulateData.get(0).getPopulateId();
		}
		
		/*criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceModeMaster> lstRemittanceModeMaster=(List<RemittanceModeMaster>) findAll(criteria);*/
				
		return remitModeid;		
	}
	
	
	public List<ReceiveReceiptView> getReceiveReceiptData(BigDecimal documentNo, BigDecimal documentFinanceYear,BigDecimal documentCode,BigDecimal companyId,BigDecimal applicatioCountryId){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiveReceiptView.class, "receiveReceiptView");
		
		dCriteria.add(Restrictions.eq("receiveReceiptView.documentFinanceYear", documentFinanceYear));
		dCriteria.add(Restrictions.eq("receiveReceiptView.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("receiveReceiptView.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiveReceiptView.companyId", companyId));
		dCriteria.add(Restrictions.eq("receiveReceiptView.applicationCountryId", applicatioCountryId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ReceiveReceiptView>) findAll(dCriteria);
	}
	
	public List<WUStateCityModel> getWUStateList(String countryISO){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(WUStateCityModel.class, "wuStateCityModel");		
		dCriteria.add(Restrictions.eq("wuStateCityModel.wuCountryISOCode", countryISO));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUStateCityModel>) findAll(dCriteria);
	}
	
	public List<WUStateCityModel> getWUCityList(String countryISO, String stateCode){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(WUStateCityModel.class, "wuStateCityModel");
		
		dCriteria.add(Restrictions.eq("wuStateCityModel.wuCountryISOCode", countryISO));
		dCriteria.add(Restrictions.eq("wuStateCityModel.wuStateCode", stateCode));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUStateCityModel>) findAll(dCriteria);
	}

	@Override
	public void saveCurrAdjustReceiptAndPayment(
			List<ForeignCurrencyAdjust> foreignCurrencyList,
			ReceiptPayment receiptPaymnet, List<Payment> payment)
			throws Exception {
		
		try{
			//saveCollect(collection);
			/*collectionId = saveCollection(collection);
			System.out.println("Test R :"+collectionId);
			for(CollectDetail collectDetail :collectionDetailList){
				Collect collect =new Collect();
				collect.setCollectionId(collectionId);
				collectDetail.setCashCollectionId(collect);
				saveDetail(collectDetail);
			}*/
			for(ForeignCurrencyAdjust  currecnyAdjust :foreignCurrencyList){
				/*Collect collect =new Collect();
				collect.setCollectionId(collectionId);
				currecnyAdjust.setCollect(collect);*/
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
	
	public List<WUTermsAndConditions> getWUTermsAndConditions(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(WUTermsAndConditions.class, "wuTermsAndConditions");
		
		dCriteria.add(Restrictions.eq("wuTermsAndConditions.isActive", "Y"));		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUTermsAndConditions>) findAll(dCriteria);

	}
	
	public String getWUEnrollReference(BigDecimal customerId)
	{
		String wuEnrollReference=null;
		String sql = "SELECT C.WU_ENROLLMENT_REFERENCE  FROM FS_CUSTOMER C  WHERE C.CUSTOMER_ID =:pcustomerId ";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.setParameter("pcustomerId", customerId);
		List data = query.list();
		if(data!=null && data.size()>0)
		{
			wuEnrollReference=(String) data.get(0);
		}
		
		return wuEnrollReference;
	}
	
	public BigDecimal getCountryIDFromCode(String countryCode)
	{
		BigDecimal countryId=BigDecimal.ZERO;
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");
		criteria.add(Restrictions.eq("countryMaster.countryAlpha2Code", countryCode));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryMaster> countryNameList = (List<CountryMaster>) findAll(criteria);
		if (countryNameList!=null &&  countryNameList.size() > 0) {
			countryId= countryNameList.get(0).getCountryId();
		} 
		return countryId;
	}
	
	public String getCountryName(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryMasterDesc> lstCountryMasterDesc =  (List<CountryMasterDesc>) findAll(dCriteria);
		String countryName=null;
		if(lstCountryMasterDesc!=null && lstCountryMasterDesc.size()>0)
		{
			countryName=lstCountryMasterDesc.get(0).getCountryName();
		}
		return countryName;
	}
	public int updateWUEnrollCardNo(BigDecimal customerId,String wucardno)
	{
		String SQl="Update FS_CUSTOMER set WU_ENROLLMENT_REFERENCE = '"+wucardno+"' " +" where CUSTOMER_ID= "+customerId ;
		int ui=getSession().createSQLQuery(SQl).executeUpdate();
		System.out.println("ui");
		return ui;
	}
	
	public List<WUCorridorCountry> getWUDynamicFieldCountry(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				WUCorridorCountry.class, "wuCorridorCountry");
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUCorridorCountry>) findAll(dCriteria);
	}
	
	public List<DynamicFileds> getWUDynamicFields(String isoCountry){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				DynamicFileds.class, "dynamicFileds");
		dCriteria.add(Restrictions.eq("dynamicFileds.countryISOCode", isoCountry));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DynamicFileds>) findAll(dCriteria);
		
	}
	
	public List<DynamicPurposeCode> getWUDynamicPurposeCode(String isoCountry){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				DynamicPurposeCode.class, "dynamicPurposeCode");
		dCriteria.add(Restrictions.eq("dynamicPurposeCode.isoCountryCode", isoCountry));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DynamicPurposeCode>) findAll(dCriteria);
	}
	
	public List<WUCorridorCurrency> getWUCorridorCurrency(String isoCountry){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				WUCorridorCurrency.class, "wuCorridorCurrency");
		dCriteria.add(Restrictions.eq("wuCorridorCurrency.countryISOCode", isoCountry));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUCorridorCurrency>) findAll(dCriteria);
	}
	
	@Override
	public String getNextReferenceNo() {
		SQLQuery sqlQuery = super.getSession().createSQLQuery("SELECT EX_WU_H2H_REFERENCE_SEQ.NEXTVAL FROM DUAL");
		return sqlQuery.uniqueResult().toString();
	}

	@Override
	public List<RemittanceApplication> getRemittanceAppBasedonMtcNo(String mtcNo) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				RemittanceApplication.class, "remittanceApplication");
		dCriteria.add(Restrictions.eq("remittanceApplication.westernUnionMtcno", mtcNo));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<RemittanceApplication> lstRemittanceApplication= (List<RemittanceApplication>) findAll(dCriteria);
		
		return lstRemittanceApplication;
	}

	@Override
	public void updateWuSendMoneyStoreSts(String mtcNo,String newpoints) {
		List<RemittanceApplication> lstRemittanceApplication= getRemittanceAppBasedonMtcNo(mtcNo);
		
		if(lstRemittanceApplication!=null && lstRemittanceApplication.size()>0)
		{
			RemittanceApplication remittanceApplication =lstRemittanceApplication.get(0);
			remittanceApplication.setWuSendMoneyStoreSts(Constants.Yes);
			remittanceApplication.setWuNewPointsEarned(newpoints);
			
			getSession().saveOrUpdate(remittanceApplication);
		}
	}
	
	@Override
	public List<ViewWUSendTransaction> getWUTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear) {
		
		List<ViewWUSendTransaction> list = new ArrayList<ViewWUSendTransaction>();
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewWUSendTransaction.class, "viewWUSendTransaction");
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.customerReference", customerNo));
			dCriteria.add(Restrictions.isNotNull("transactionTypeDesc"));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.bankCode","WU"));
			dCriteria.addOrder(Order.desc("viewWUSendTransaction.documentNumber"));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.applicationCountryId", applicationCountryId));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.collectionDocumentCode", documentCode));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.documentFinanceYear", documentFinanceYear));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.companyId", companyId));
			dCriteria.addOrder(Order.desc("viewWUSendTransaction.documentNumber"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			list = (List<ViewWUSendTransaction>) findAll(dCriteria);
		} catch (Exception e) {
			
		}
		return list;
	}
	
	
	
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryList(BigDecimal customerNo,BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear){
		
		List<ReceiveReceiptView> list = new ArrayList<ReceiveReceiptView>();
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiveReceiptView.class, "receiveReceiptView");
			dCriteria.add(Restrictions.eq("receiveReceiptView.customerReference", customerNo));
			dCriteria.add(Restrictions.eq("receiveReceiptView.applicationCountryId", applicationCountryId));
			dCriteria.add(Restrictions.eq("receiveReceiptView.documentCode", documentCode));
			dCriteria.add(Restrictions.eq("receiveReceiptView.documentFinanceYear", documentFinanceYear));
			dCriteria.add(Restrictions.eq("receiveReceiptView.companyId", companyId));
			
			/*if(fromdate!=null&&todate==null){
				//dCriteria.add(Restrictions.ge("receiveReceiptView.documentDate",fromdate));
				
				SimpleDateFormat fromdateformat = new SimpleDateFormat("dd/MM/yyyy");
				String fromdate1 = fromdateformat.format(fromdate);				
				dCriteria.add(Restrictions.sqlRestriction("TRUNC(DOCUMENT_DATE) >= TO_DATE('" + fromdate1 + "','dd/mm/yyyy')"));				  
				
			}else if(fromdate==null&&todate!=null){				
				SimpleDateFormat todateformat = new SimpleDateFormat("dd/MM/yyyy");
				String todate1 = todateformat.format(todate);				
				dCriteria.add(Restrictions.le("receiveReceiptView.documentDate",todate1));
			}else if(fromdate!=null&&todate!=null){
				dCriteria.add(Restrictions.between("DATE(receiveReceiptView.documentDate)", fromdate, todate));
			}*/
			
			dCriteria.addOrder(Order.desc("receiveReceiptView.documentNo"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			list = (List<ReceiveReceiptView>) findAll(dCriteria);
		} catch (Exception e) {
			
		}
		return list;
		
	}
	
	@Override
	public List<ViewWUSendTransaction> getWUSendTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress) {
		
		List<ViewWUSendTransaction> list = new ArrayList<ViewWUSendTransaction>();
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewWUSendTransaction.class, "viewWUSendTransaction");
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.applicationCountryId", applicationCountryId));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.collectionDocumentCode", documentCode));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.documentFinanceYear", documentFinanceYear));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.companyId", companyId));
			
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.terminalAddress", terminalAddress));
			dCriteria.add(Restrictions.isNotNull("transactionTypeDesc"));
			dCriteria.add(Restrictions.eq("viewWUSendTransaction.bankCode","WU"));
			dCriteria.addOrder(Order.desc("viewWUSendTransaction.documentNumber"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			list = (List<ViewWUSendTransaction>) findAll(dCriteria);
		} catch (Exception e) {
			
		}
		return list;
	}	
	
	public List<ReceiveReceiptView> getWUReceiveTxnInquiryListByTerminal(BigDecimal companyId,BigDecimal applicationCountryId, BigDecimal documentCode, BigDecimal documentFinanceYear,String terminalAddress){
		
		List<ReceiveReceiptView> list = new ArrayList<ReceiveReceiptView>();
		try {
			DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiveReceiptView.class, "receiveReceiptView");
			dCriteria.add(Restrictions.eq("receiveReceiptView.terminalAddress", terminalAddress));
			dCriteria.add(Restrictions.eq("receiveReceiptView.applicationCountryId", applicationCountryId));
			dCriteria.add(Restrictions.eq("receiveReceiptView.documentCode", documentCode));
			dCriteria.add(Restrictions.eq("receiveReceiptView.documentFinanceYear", documentFinanceYear));
			dCriteria.add(Restrictions.eq("receiveReceiptView.companyId", companyId));
			
			/*if(fromdate!=null&&todate==null){
				//dCriteria.add(Restrictions.ge("receiveReceiptView.documentDate",fromdate));
				
				SimpleDateFormat fromdateformat = new SimpleDateFormat("dd/MM/yyyy");
				String fromdate1 = fromdateformat.format(fromdate);				
				dCriteria.add(Restrictions.sqlRestriction("TRUNC(DOCUMENT_DATE) >= TO_DATE('" + fromdate1 + "','dd/mm/yyyy')"));				  
				
			}else if(fromdate==null&&todate!=null){				
				SimpleDateFormat todateformat = new SimpleDateFormat("dd/MM/yyyy");
				String todate1 = todateformat.format(todate);				
				dCriteria.add(Restrictions.le("receiveReceiptView.documentDate",todate1));
			}else if(fromdate!=null&&todate!=null){
				dCriteria.add(Restrictions.between("DATE(receiveReceiptView.documentDate)", fromdate, todate));
			}*/
			
			dCriteria.addOrder(Order.desc("receiveReceiptView.documentNo"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			list = (List<ReceiveReceiptView>) findAll(dCriteria);
		} catch (Exception e) {
			
		}
		return list;
		
	}
	
	@Override
	public HashMap<String, Object> saveAllRemittanceTransaction(HashMap<String, Object> mapAllDetailPersonalRemittanceSave) throws AMGException {
		log.info("Entered into saveAllRemittanceTransaction() method ");
		HashMap<String, Object> lstOutParam = new HashMap<String, Object>();
		Connection connection = null;
		CallableStatement cs;
		String errormsg = null;
		BigDecimal cFinYear = null;
		BigDecimal collectionNumber = null;

		try {
			BigDecimal countryId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CountryId");
			BigDecimal companyId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CompanyId");
			BigDecimal customerId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("CustomerId");
			String userName = (String) mapAllDetailPersonalRemittanceSave.get("UserName");
			BigDecimal noofTrnx = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("NoofTrnx");
			BigDecimal tempDocCode = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("TempDocCode");
			BigDecimal tempCollectionId = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("TempCollectionId");
			BigDecimal mtcNo = (BigDecimal) mapAllDetailPersonalRemittanceSave.get("MtcNo");

			try {
				connection= getDataSourceFromHibernateSession();
			} catch (Exception e) {
				e.printStackTrace();
			}

			log.info("Parameter 1 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCountryId(): "+countryId);
			log.info("Parameter 2 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionStateManage.getCompanyId(): "+companyId);
			log.info("Parameter 3 saveRemittance EX_INSERT_REMITTANCE_TRANX getCustomerNo(): "+customerId);
			log.info("Parameter 4 saveRemittance EX_INSERT_REMITTANCE_TRANX sessionmanage.getUserName(): "+userName);
			log.info("Parameter 5 saveRemittance EX_INSERT_REMITTANCE_TRANX getColremittanceNo() :"+noofTrnx);
			log.info("Parameter 6 saveRemittance EX_INSERT_REMITTANCE_TRANX tempCollection.getDocumentCode(): "+tempDocCode);
			log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX collectionId: "+tempCollectionId);
			log.info("Parameter 7 saveRemittance EX_INSERT_REMITTANCE_TRANX MtcNo: "+mtcNo);

			// calling procedure
			cs = connection.prepareCall(" { call WU_EX_INSERT_REMITTANCE_TRANX (?,?,?,?,?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, countryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, customerId);
			cs.setString(4, userName);
			cs.setBigDecimal(5, noofTrnx);
			cs.setBigDecimal(6, tempDocCode);
			cs.setBigDecimal(7,tempCollectionId);
			cs.setBigDecimal(8,mtcNo);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.INTEGER);
			cs.registerOutParameter(11, java.sql.Types.VARCHAR);
			cs.execute();

			cFinYear = cs.getBigDecimal(9);
			collectionNumber = cs.getBigDecimal(10);
			errormsg = cs.getString(11);

			log.info("Out1 -----> cFinYear : "+cFinYear);
			log.info("Out2------>collectionNumber : "+collectionNumber);
			log.info("Out3------->errormsg : "+errormsg);

			lstOutParam.put("CollectionDocNo", collectionNumber);
			lstOutParam.put("CollectionFinYear", cFinYear);
			lstOutParam.put("ErrorMsg", errormsg);

		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				throw new AMGException(e.getMessage());
			} catch (Exception e) {
				throw new AMGException(e.getMessage());
			}
		}

		return lstOutParam;
	}
	
	@Override
	public List<WURemittanceApplicationView> getRecordsForWURemittanceReceiptReport(BigDecimal documentNo, BigDecimal financeYear, String documentCode, BigDecimal applicationCountryId,BigDecimal companyId) {
		log.info("Entered into getRecordsRemittanceReceipt(BigDecimal documentNo) Method");
		log.info("documentNo =" + documentNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(WURemittanceApplicationView.class, "wuRemittanceApplicationView");
		criteria.add(Restrictions.eq("wuRemittanceApplicationView.collectionDocumentNo", documentNo));
		criteria.add(Restrictions.eq("wuRemittanceApplicationView.collectionDocFinanceYear", financeYear));
		criteria.add(Restrictions.eq("wuRemittanceApplicationView.collectionDocCode", new BigDecimal(documentCode)));
		criteria.add(Restrictions.eq("wuRemittanceApplicationView.applicationCountryId", applicationCountryId));
		criteria.add(Restrictions.eq("wuRemittanceApplicationView.companyId", companyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		log.info("Exited from getRecordsRemittanceReceipt Method ");
		return (List<WURemittanceApplicationView>) findAll(criteria);
	}
	
	@Override
	public List<WUReportTermsAndConditions> getWUReportTermsAndConditions(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(WUReportTermsAndConditions.class, "wuReportTermsAndConditions");
		
		dCriteria.add(Restrictions.eq("wuReportTermsAndConditions.isActive", "Y"));	
		dCriteria.addOrder(Order.asc("wuReportTermsAndConditions.lineNo"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<WUReportTermsAndConditions>) findAll(dCriteria);

	}
	
	@Override
	public List<ReceiptPayment> getReceiveMtcNo(String mtcNo) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				ReceiptPayment.class, "receiptPayment");
		dCriteria.add(Restrictions.eq("receiptPayment.westernUnoinMycNo", mtcNo));		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ReceiptPayment> lst= (List<ReceiptPayment>) findAll(dCriteria);
		
		return lst;
	}
	
	@Override
	public String saveReceivetoOldByProc(BigDecimal countryId, BigDecimal companyId, BigDecimal documentId, BigDecimal financialYear,BigDecimal documentno) {

		String out = null;
		log.info("EX_INSERT_WU_REMITTANCE_PAYOUT countryId :" + countryId);
		log.info("EX_INSERT_WU_REMITTANCE_PAYOUT companyId :" + companyId);
		log.info("EX_INSERT_WU_REMITTANCE_PAYOUT documentCode :" + documentId);
		log.info("EX_INSERT_WU_REMITTANCE_PAYOUT financialYear :" + financialYear);
		log.info("EX_INSERT_WU_REMITTANCE_PAYOUT documentno :" + documentno);

		Connection connection = null;
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_INSERT_WU_PAYOUT(?,?,?,?,?,?)}");
			cs.setBigDecimal(1, countryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYear);
			cs.setBigDecimal(5, documentno);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.execute();
			out = cs.getString(6);

			log.info("EX_INSERT_WU_PAYOUT out Value :" + out);

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
	
	@Override
	public List<RemittanceTransaction> getRemittanceTrnxBasedonMtcNo(String mtcNo) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				RemittanceTransaction.class, "remittanceTransaction");
		dCriteria.add(Restrictions.eq("remittanceTransaction.westernUnionMtcno", mtcNo));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<RemittanceTransaction> lstRemittanceTransaction= (List<RemittanceTransaction>) findAll(dCriteria);
		
		return lstRemittanceTransaction;
	}


}
