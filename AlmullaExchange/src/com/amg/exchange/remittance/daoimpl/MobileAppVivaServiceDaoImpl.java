package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.dao.IMobileAppVivaServiceDao;
import com.amg.exchange.remittance.model.AlternateChannels;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.ServiceProviderView;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@Repository
public class MobileAppVivaServiceDaoImpl<T> extends CommonDaoImpl<T> implements IMobileAppVivaServiceDao<T>{

	Logger log = Logger.getLogger(MobileAppVivaServiceDaoImpl.class);
	@Override
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId,BigDecimal customerRef) {
		List<PopulateData> lstPopulateData=  new ArrayList<PopulateData>();
		
		String hqlQuery="select distinct a.benificaryName from  BenificiaryListView a where a.customerId =  :customerIdNumber  and a.benificaryCountry = :pBenificaryCountry";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerIdNumber", customerId);
		query.setParameter("pBenificaryCountry", beneCountryId);
		//query.setParameter("pcustomerRef", customerRef);
		
		List<String> lstbeneName =query.list();
		
		
		if(lstbeneName.size()>0)
		{
			for(String beneName:lstbeneName)
			{
				PopulateData populateData= new PopulateData();
				populateData.setPopulateName(beneName);
				lstPopulateData.add(populateData);
			}
			
		}
		
		return lstPopulateData;
	}

	@Override
	public List<PopulateData> getBeneficiarBankList(String beneName,BigDecimal beneCountryId,BigDecimal customerId) {

		DetachedCriteria criteria=DetachedCriteria.forClass(BenificiaryListView.class,"benificiaryListView");

		criteria.add(Restrictions.eq("benificiaryListView.benificaryName",beneName));
		criteria.add(Restrictions.eq("benificiaryListView.benificaryCountry",beneCountryId));
		criteria.add(Restrictions.eq("benificiaryListView.customerId",customerId));
		
		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("bankId")), "populateId");
		
		//columns.add(Projections.property("bankId"), "populateId");
		columns.add(Projections.property("bankCode"),"populateCode");
		columns.add(Projections.property("bankName"), "populateName");


		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);


		// TODO Auto-generated method stub
		return lstPopulateData;
	}
	
	
	@Override
	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId)
	{
	 
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		criteria.add(Restrictions.eq("benificiaryListView.benificaryName", beneName));
		criteria.add(Restrictions.eq("benificiaryListView.bankId", beneBankId));
		
		criteria.add(Restrictions.eq("benificiaryListView.customerId",customerId));
		
		criteria.add(Restrictions.eq("benificiaryListView.benificaryCountry", beneCountryId));
		
		criteria.add(Restrictions.eq("benificiaryListView.branchId", beneBranchId));
		
		criteria.add(Restrictions.isNotNull("benificiaryListView.bankAccountNumber"));
		
		
		
		ProjectionList columns = Projections.projectionList();

		
		columns.add(Projections.distinct(Projections.property("beneficiaryAccountSeqId")), "populateId");
		columns.add(Projections.property("benificiaryListView.bankAccountNumber"),"populateCode");
		columns.add(Projections.property("benificiaryListView.currencyQuoteName"), "populateName");


		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );
		
		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);
		
		return lstPopulateData;
	}

	
	public List<PopulateData> getCountryList(BigDecimal customerId,BigDecimal languageId) {

		String hqlQuery = "Select Distinct BENEFICARY_COUNTRY , COUNTRY_CODE , BENEFICARY_BANK_COUNTRY_NAME from Vw_Beneficiary_Bank where CUSTOMER_ID = :customerId ORDER BY BENEFICARY_BANK_COUNTRY_NAME Asc";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);

		List<Object[]> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (Object object : list) {
			Object[] li = (Object[]) object;
			if (li.length > 0) {
				if (li[0] != null && li[1] != null) {
					PopulateData lstRBankData = new PopulateData();
					lstRBankData.setPopulateId(new BigDecimal(li[0].toString()));
					lstRBankData.setPopulateCode(li[1].toString());
					lstRBankData.setPopulateName(li[2].toString());
					lstRemittanceDetails.add(lstRBankData);
				}
			}
		}

		return lstRemittanceDetails;
	}
	
	
	@Override
	public List<PopulateData> getBenNameList(BigDecimal customerId, BigDecimal countryId) {

		/*DetachedCriteria dCriteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		dCriteria.add(Restrictions.eq("benificiaryListView.customerId", customerId));
		dCriteria.add(Restrictions.eq("benificiaryListView.benificaryCountry", countryId));
		// testing purpose
		//dCriteria.add(Restrictions.isNotNull("benificiaryListView.lastEmosRemittance"));
		dCriteria.addOrder(Order.asc("benificiaryListView.benificaryName"));
		
		ProjectionList columns = Projections.projectionList();
		
		columns.add(Projections.distinct(Projections.property("benificaryName")), "populateName");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );
		
		List<PopulateData> lst = (List<PopulateData>) findAll(dCriteria);

		return lst;*/

		//String hqlQuery = "select distinct a.benificaryCountry,a.benificaryBankCountryName from  BenificiaryListView a where a.customerId =  :customerId ORDER BY a.benificaryBankCountryName asc";
		String hqlQuery = "Select BENE_NAME from Vw_Beneficiary_Names where CUSTOMER_ID = :customerId and BENEFICARY_COUNTRY = :beneBankCountry ORDER BY BENE_NAME Asc";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		query.setParameter("beneBankCountry", countryId);

		List<Object> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (Object obj: list) {
			PopulateData lstRBankData = new PopulateData();
			String beneName = (String) obj;
			lstRBankData.setPopulateName(beneName);
			lstRemittanceDetails.add(lstRBankData);
		}

		return lstRemittanceDetails;
	
	
	}
	
	
	@Override
	public List<PopulateData> getBeneBankList(BigDecimal customerId, BigDecimal countryId, String customerName) {
		
		
		String hqlQuery = "Select BANK_ID,BANK_CODE,BANK_FULL_NAME from Vw_Beneficiary_Bank where CUSTOMER_ID = :customerId and BENEFICARY_COUNTRY = :beneBankCountry and BENE_NAME = :beneName ORDER BY BANK_FULL_NAME Asc";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		query.setParameter("beneBankCountry", countryId);
		query.setParameter("beneName", customerName);

		List<Object[]> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (Object object : list) {
			Object[] li = (Object[]) object;
			if (li.length > 0) {
				if (li[0] != null && li[1] != null && li[2] != null) {
					PopulateData lstRBankData = new PopulateData();
					lstRBankData.setPopulateId(new BigDecimal(li[0].toString()));
					lstRBankData.setPopulateCode(li[1].toString());
					lstRBankData.setPopulateName(li[2].toString());
					lstRemittanceDetails.add(lstRBankData);
				}
			}
		}

		return lstRemittanceDetails;
	}
	
	@Override
	public List<PopulateData> getBeneAccountNoList(BigDecimal customerId, BigDecimal countryId, BigDecimal bankId, String customerName,BigDecimal beneBankId) {
		
		
		
		String hqlQuery = "Select BANK_ACCOUNT_NUMBER from Vw_Beneficiary_Bank_Account where CUSTOMER_ID = :customerId and BENEFICARY_COUNTRY = :beneBankCountry "
				+ "and BENE_NAME = :beneName and BANK_ID = :bankId  and BANK_BRANCH_ID =:pbeneBranchId ORDER BY BANK_ACCOUNT_NUMBER Asc";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		query.setParameter("beneBankCountry", countryId);
		query.setParameter("beneName", customerName);
		query.setParameter("bankId", bankId);
		query.setParameter("pbeneBranchId", beneBankId);
		
		List<Object> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		if(list!=null && list.size()>0)
		{
			for (Object obj: list) {
				
				String accNo = (String) obj;
				List<PopulateData> lstPopulateData=getBenficaryAccountDetails(countryId, bankId, customerId, beneBankId, accNo);
				if(lstPopulateData != null && lstPopulateData.size() != 0){
					lstRemittanceDetails.add(lstPopulateData.get(0));
				}
			}
		}
		
		return lstRemittanceDetails;
	}
	
	
	@Override
	public List<PopulateData> getBeneAccountNoList(BigDecimal bankId,BigDecimal customerId, BigDecimal countryId,String beneName) {
		String hqlQuery = "Select Distinct BANK_BRANCH_ID,BANK_BRANCH_NAME from Vw_Beneficiary_Bank_Account where CUSTOMER_ID = :customerId and BENEFICARY_COUNTRY = :beneBankCountry "
				+ "and BANK_ID = :bankId and BENE_NAME =:pbeneName ORDER BY BENEFICARY_COUNTRY Asc";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		query.setParameter("beneBankCountry", countryId);
		query.setParameter("bankId", bankId);
		query.setParameter("pbeneName", beneName);


		List<Object> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (Object obj: list) {
			Object[] li = (Object[]) obj;
			if (li.length > 0) {
				PopulateData lstRBankData = new PopulateData();
				if (li[0] != null) {
					lstRBankData.setPopulateId(new BigDecimal(li[0].toString()));
					lstRBankData.setPopulateCode(getBankBranchCode(new BigDecimal(li[0].toString())).toString());
				}
				
				if(li[1] != null){
					lstRBankData.setPopulateName(li[1].toString());
				}
				lstRemittanceDetails.add(lstRBankData);
			}
		}

		return lstRemittanceDetails;
	}
	
	public BigDecimal getBankBranchCode(BigDecimal bankBranchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankBranch.class, "bankBranch");

		dCriteria.add(Restrictions.eq("bankBranch.bankBranchId", bankBranchId));

		return ((BankBranch) dCriteria.getExecutableCriteria(getSession())
				.list().get(0)).getBranchCode();
	}
	
	public List<PopulateData> getBenficaryAccountDetails(BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId,String bankAccountNumber)
	{
		
		
		String hqlQuery=" Select Distinct BENEFICARY_ACCOUNT_SEQ_ID  from EX_BENEFICARY_ACCOUNT where BENEFICARY_COUNTRY = :beneBankCountry and BANK_ID=:beneBank and BANK_BRANCH_ID=:beneBankBranch and BANK_ACCOUNT_NUMBER= :beneBankAccNo";
		
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("beneBankCountry", beneCountryId);
		query.setParameter("beneBank", beneBankId);
		query.setParameter("beneBankBranch", beneBranchId);
		query.setParameter("beneBankAccNo", bankAccountNumber);
		
		List<BigDecimal> list = query.list();
		List<PopulateData> lstPopulateData =new ArrayList<>();
		if(list!=null  && list.size()>0)
		{
			for(BigDecimal seqId:list)
			{
				PopulateData lstRBankData = new PopulateData();
				lstRBankData.setPopulateId(seqId);
				lstRBankData.setPopulateCode(bankAccountNumber);
				lstPopulateData.add(lstRBankData);
			}
		}
		/*DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		
		
		criteria.setFetchMode("beneficaryAccount.beneficaryCountry", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.beneficaryCountry", "beneficaryCountry", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("beneficaryCountry.countryId", beneCountryId));
		
		criteria.setFetchMode("beneficaryAccount.bank", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bank", "bank", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bank.bankId", beneBankId));
		
		criteria.setFetchMode("beneficaryAccount.bankBranch", FetchMode.JOIN);
		criteria.createAlias("beneficaryAccount.bankBranch", "bankBranch", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankBranch.bankBranchId", beneBranchId));
		
		criteria.add(Restrictions.eq("beneficaryAccount.bankAccountNumber", bankAccountNumber));
		
		//criteria.add(Restrictions.isNotNull("beneficaryAccount.bankAccountNumber"));
				
		ProjectionList columns = Projections.projectionList();

																
		columns.add(Projections.distinct(Projections.property("beneficaryAccountSeqId")), "populateId");
		columns.add(Projections.property("beneficaryAccount.bankAccountNumber"),"populateCode");
		columns.add(Projections.property("beneficaryAccount.currencyId.quoteName"), "populateName");


		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );
		
		List<BeneficaryAccount> lstPopulateData1=  (List<BeneficaryAccount>) findAll(criteria);*/
		
		
		return lstPopulateData;
		
	}

	@Override
	public List<AlternateChannels> findExistSericeProvider(BigDecimal customerId) {
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AlternateChannels.class, "alternateChannels");
		
		
		criteria.setFetchMode("alternateChannels.customerId", FetchMode.JOIN);
		criteria.createAlias("alternateChannels.customerId", "customerId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerId.customerId", customerId));
		criteria.add(Restrictions.eq("alternateChannels.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("alternateChannels.channelId", "MOBILE"));
		criteria.add(Restrictions.isNotNull("alternateChannels.serviceProviderId"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<AlternateChannels> lstAlternateChannels=  (List<AlternateChannels>) findAll(criteria);
		
		return lstAlternateChannels;
	}
	
	public BeneficaryAccount getRelionShipDetails(BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId,String bankAccountNumber)
	{
		String hqlQuery=" Select Distinct BENEFICARY_ACCOUNT_SEQ_ID  from EX_BENEFICARY_ACCOUNT where BENEFICARY_COUNTRY = :beneBankCountry and BANK_ID=:beneBank and BANK_BRANCH_ID=:beneBankBranch and BANK_ACCOUNT_NUMBER= :beneBankAccNo";
		
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("beneBankCountry", beneCountryId);
		query.setParameter("beneBank", beneBankId);
		query.setParameter("beneBankBranch", beneBranchId);
		query.setParameter("beneBankAccNo", bankAccountNumber);
		
		List<BigDecimal> list = query.list();
		BigDecimal beneAccountSeqId=BigDecimal.ZERO;
		if(list!=null  && list.size()>0)
		{
			for(BigDecimal seqId:list)
			{
				beneAccountSeqId=seqId;
			}
		}
		
		BeneficaryAccount beneficaryAccount=(BeneficaryAccount) getSession().get(BeneficaryAccount.class, beneAccountSeqId);
		return beneficaryAccount;
	}
	
	public List<AlternateChannels> checkDuplicateSericeProviderBankBeneAcc(BigDecimal customerId ,BigDecimal beneCountryId,BigDecimal beneBankid,String accountNumber) {
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(AlternateChannels.class, "alternateChannels");
		
		
		criteria.setFetchMode("alternateChannels.customerId", FetchMode.JOIN);
		criteria.createAlias("alternateChannels.customerId", "customerId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerId.customerId", customerId));
		
		criteria.setFetchMode("alternateChannels.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("alternateChannels.beneCountryId", "beneCountryId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("beneCountryId.countryId", beneCountryId));
		
		criteria.setFetchMode("alternateChannels.beneBankId", FetchMode.JOIN);
		criteria.createAlias("alternateChannels.beneBankId", "beneBankId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("beneBankId.bankId", beneBankid));
		
		//criteria.add(Restrictions.eq("alternateChannels.accountNumber", accountNumber));
		
		
		criteria.add(Restrictions.eq("alternateChannels.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("alternateChannels.channelId", "MOBILE"));
		criteria.add(Restrictions.isNotNull("alternateChannels.serviceProviderId"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<AlternateChannels> lstAlternateChannels=  (List<AlternateChannels>) findAll(criteria);
		
		return lstAlternateChannels;
	}
	
	public String verifyOtpNo(BigDecimal optNo,BigDecimal customerPkId,  String userName) throws AMGException {
		
		String rtnMessage=null;
		String queryString = "SELECT OTP_NO FROM FS_CUSTOMER WHERE OTP_NO=? and CUSTOMER_ID =?";
		Query query = getSession().createSQLQuery(queryString);
		query.setBigDecimal(0, optNo);
		query.setBigDecimal(1, customerPkId);
		BigDecimal dbOtpNo = (BigDecimal)query.uniqueResult();
		
		if(dbOtpNo!=null)
		{
			if(dbOtpNo.compareTo(optNo)==0)
			{
				String SQl="Update FS_CUSTOMER set OTP_VERIFIED_BY = '"+userName+"',"
						+ "  OTP_VERIFIED_DT= SYSDATE "
								+" where CUSTOMER_ID= "+customerPkId+""
						 + " AND OTP_NO = "+optNo;
				
				int ui=getSession().createSQLQuery(SQl).executeUpdate();
				if(ui==1)
				{
					rtnMessage= "SUCCESS";
				}else
				{
					rtnMessage= "FAIL";
				}
				
			}else
			{
				rtnMessage= "OTPNOTMATCH";
			}
		}else
		{
			rtnMessage= "OTPNOTMATCH";
		}
		
		
		return rtnMessage;
	}


	@Override
	public void saveAlternateChanlesDetails(
			HashMap<String, AlternateChannels> saveMap, String userName,
			BigDecimal customerId, String availOnline, String avialKiosk,
			String avialMobile,String avialServiceProvider) throws AMGException {
		
		AlternateChannels alternateChannelsOnline=saveMap.get("ONLINE");
		AlternateChannels alternateChannelsKiosk=saveMap.get("KIOSK");
		AlternateChannels alternateChannelsMobileApp=saveMap.get("MOBILEAPP");
		AlternateChannels alternateChannelsMobileAppService=saveMap.get("MOBILEAPPSERVICE");
		
		
		if(alternateChannelsOnline!=null)
		{
			getSession().saveOrUpdate(alternateChannelsOnline);
		}
		if(alternateChannelsKiosk!=null)
		{
			getSession().saveOrUpdate(alternateChannelsKiosk);
		}
		if(alternateChannelsMobileApp!=null)
		{
			getSession().saveOrUpdate(alternateChannelsMobileApp);
		}
		if(alternateChannelsMobileAppService!=null)
		{
			getSession().saveOrUpdate(alternateChannelsMobileAppService);
		}
		updateCustomerAvilaServices(userName, customerId, availOnline, avialKiosk, avialMobile,avialServiceProvider);
	}
	public int updateCustomerAvilaServices(String userName,
			BigDecimal customerId, String availOnline, String avialKiosk,
			String avialMobile,String avialServiceProvider) throws AMGException {
		
		
		String SQl="Update FS_CUSTOMER set IS_KIOSK_USER = '"+avialKiosk+"' , IS_MOBILE_USER = '"+avialMobile+"' , IS_ONLINE_USER = '"+availOnline+"' , IS_SERVICE_PROVIDER = '"+avialServiceProvider+"'"
				+" where CUSTOMER_ID= "+customerId; 
		
		int ui=getSession().createSQLQuery(SQl).executeUpdate();

		return ui;
	}
	
	public HashMap<String, String> getAvialServices(BigDecimal customerId)
	{
		String hqlQuery = "Select IS_KIOSK_USER,IS_MOBILE_USER,IS_ONLINE_USER,IS_SERVICE_PROVIDER from FS_CUSTOMER  where CUSTOMER_ID = :customerId ";
		Query query = getSession().createSQLQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		HashMap<String, String> rtnMap= new HashMap<String, String>();
		List<Object> list = query.list();
		List<PopulateData> lstRemittanceDetails = new ArrayList<PopulateData>();

		for (Object obj: list) {
			
			if(obj!=null)
			{
				Object[] li = (Object[]) obj;
				if(li.length>0)
				{
					if(li[0]!=null)
					{
						String kiosk=li[0].toString();
						rtnMap.put("KIOSK", kiosk);
					}
					if(li[1]!=null)
					{
						String mobile=li[1].toString();
						rtnMap.put("MOBILE", mobile);
					}
					if(li[2]!=null)
					{
						String online=li[2].toString();
						rtnMap.put("ONLINE", online);
					}
					if(li[3]!=null)
					{
						String online=li[3].toString();
						rtnMap.put("SERVICEPROVIDER", online);
					}
				}
			}
			
			
		}
		return rtnMap;
	}
	
	public void deactivateExistServiceProvider(BigDecimal alternateChannelsID,String userName) 
	{
		AlternateChannels alternateChannels=(AlternateChannels) getSession().get(AlternateChannels.class, alternateChannelsID);
		alternateChannels.setIsActive(Constants.D);
		alternateChannels.setModifiedBy(userName);
		alternateChannels.setModifiedDate(new Date());
		getSession().saveOrUpdate(alternateChannels);
	}
	
	public int updateOTPDetails(BigDecimal customerId,String otpNumber,String userName) throws AMGException {
		
		
		String SQl="Update FS_CUSTOMER set OTP_NO=  "+otpNumber+", UPDATED_BY= '"+userName+"' , LAST_UPDATED = SYSDATE "
				+" where CUSTOMER_ID= "+customerId;
		
		int ui=getSession().createSQLQuery(SQl).executeUpdate();

		return ui;
	}

	public List<AlternateChannels> checkExistRecordUpdateToDeativate(BigDecimal customerId,String channelId,String userName)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(AlternateChannels.class, "alternateChannels");
		
		
		criteria.setFetchMode("alternateChannels.customerId", FetchMode.JOIN);
		criteria.createAlias("alternateChannels.customerId", "customerId", JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerId.customerId", customerId));
		
		criteria.add(Restrictions.eq("alternateChannels.channelId", channelId));
		criteria.add(Restrictions.eq("alternateChannels.isActive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<AlternateChannels> lstAlternateChannels=  (List<AlternateChannels>) findAll(criteria);
		
		if(lstAlternateChannels!=null && lstAlternateChannels.size()>0)
		{
			AlternateChannels alternateChannels=lstAlternateChannels.get(0);
			deactivateExistServiceProvider(alternateChannels.getAlternateCheanelId(), userName);
		}
		
		return lstAlternateChannels;
		
	}
	public List<LanguageType> getLanguageType(BigDecimal languageId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(LanguageType.class, "languageType");
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<LanguageType> lstLanguageType=  (List<LanguageType>) findAll(criteria);
		return lstLanguageType;
	}
	
	@Override
	public HashMap<String, String> callOTPSendProcedure(
			HashMap<String, String> inputValues) throws AMGException {
		
		log.info("Entered into callOTPSendProcedure(HashMap<String, String> inputValue) Method ");
		log.info("Procedure Name = INSERT_SMS_OTP_SENDQUEUE1");
		log.info("INSERT_SMS_OTP_SENDQUEUE1--- Input values == "+inputValues.toString());
	
		HashMap<String, String> outputValues = new HashMap<String, String>();

		Connection connection = null;
		BigDecimal sequenceNo = null;
		

		try {
			BigDecimal languageId = new BigDecimal(inputValues.get("P_LANGUAGE")==null ? "0":inputValues.get("P_LANGUAGE"));
			int nNo = 0; // Indicates that the Language is English
			if(languageId.compareTo(new BigDecimal(1))==0){
			nNo=0;
			}else if(languageId.compareTo(new BigDecimal(2))==0){
			nNo=1;
			}

			
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			String call = " { call INSERT_SMS_OTP_SENDQUEUE1 (?, ?, ?, ?, ? ,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, inputValues.get("P_SENDER_ID"));
			cs.setString(2, inputValues.get("P_MOBILE"));
			cs.setString(3, inputValues.get("P_MESSAGE"));
			cs.setString(4, "EMOS");
			cs.setString(5, "61");
			cs.setInt(6, 71);
			cs.setString(7, "ORS_USER");
			cs.setInt(8, nNo);
			cs.setNull(9, Types.INTEGER);
			cs.setNull(10, Types.INTEGER);
			cs.setNull(11, Types.INTEGER);
			cs.setNull(12, Types.INTEGER);
			cs.registerOutParameter(13, Types.INTEGER);
			cs.execute();
			sequenceNo = cs.getBigDecimal(13);
			outputValues.put("P_SEQUENCE", sequenceNo==null ? "0" : sequenceNo.toPlainString());
			/*if ( sequenceNo!= null) {
				outputValues.put("P_SEQUENCE", sequenceNo.toPlainString());
				
			} else {
				errString = "Customer Contact Details Not Available, Please Update Contact Details in Customer Registration";
				outputValues.put("P_ERROR_MESSAGE", errString);
			}
*/
		} catch (SQLException e) {
			String erromsg = "INSERT_SMS_OTP_SENDQUEUE1" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info("Problem Occured While Procedure calling " + e.getMessage());
				String erromsg = "INSERT_SMS_OTP_SENDQUEUE1" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return outputValues;
	}

	@Override
	public List<ServiceProviderView> getSrviceProvider() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceProviderView.class, "serviceProviderView");
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ServiceProviderView> lstServiceProviderView=  (List<ServiceProviderView>) findAll(criteria);
		
		return lstServiceProviderView;
	}
	
	public List<BenificiaryListView> getBeneficirarylist(BigDecimal beneMasterId,BigDecimal beneAccSeqId,BigDecimal beneRelSeqId)
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");
		
		criteria.add(Restrictions.eq("benificiaryListView.beneficaryMasterSeqId", beneMasterId));
		criteria.add(Restrictions.eq("benificiaryListView.beneficiaryAccountSeqId", beneAccSeqId));
		criteria.add(Restrictions.eq("benificiaryListView.beneficiaryRelationShipSeqId", beneRelSeqId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<BenificiaryListView> lstBenificiaryListView=  (List<BenificiaryListView>) findAll(criteria);
		
		return lstBenificiaryListView;
		
	}
}
