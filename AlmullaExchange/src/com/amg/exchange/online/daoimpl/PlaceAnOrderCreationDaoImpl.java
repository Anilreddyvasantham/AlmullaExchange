package com.amg.exchange.online.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.bean.BranchStaffGSMRateDataTable;
import com.amg.exchange.online.dao.IPlaceAnOrderCreationDao;
import com.amg.exchange.online.model.DestinationCurrencyView;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.SourceCurrencyView;
import com.amg.exchange.online.model.ViewPlaceOnOrderFullInquiry;
import com.amg.exchange.online.model.ViewPlaceOnOrderInquiry;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.PlaceOrderBeneListView;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"rawtypes","unchecked"})
@Repository
public class PlaceAnOrderCreationDaoImpl extends CommonDaoImpl  implements
IPlaceAnOrderCreationDao {

	@Override
	public List<PopulateData> getServiceGroupList(
			BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));
		//criteria.add(Restrictions.eq("serviceMaster.serviceGroupCode",Constants.BANKCHANNEL));
		/*Criterion criteria1=Restrictions.eq("serviceMaster.serviceGroupCode" , Constants.CASHFORONLINE);
		Criterion criteria2=Restrictions.eq("serviceMaster.serviceGroupCode",Constants.BANKCHANNEL);
		LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		criteria.add(orExp);*/

		criteria.addOrder(Order.asc("serviceGroupMasterDesc.serviceGroupDesc"));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.property("serviceMaster.serviceGroupId"), "populateId");
		columns.add(Projections.property("serviceMaster.serviceGroupCode"),"populateCode");
		columns.add(Projections.property("serviceGroupDesc"), "populateName");
		criteria.setProjection(columns);
		criteria.setResultTransformer(new AliasToBeanResultTransformer(PopulateData.class));

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);

		return lstPopulateData;

	}

	@Override
	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal serviceGroupId,BigDecimal customerRef)
	{

		DetachedCriteria criteria = DetachedCriteria.forClass(PlaceOrderBeneListView.class, "placeOrderBeneListView");
		criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryName", beneName));
		criteria.add(Restrictions.eq("placeOrderBeneListView.bankId", beneBankId));
		criteria.add(Restrictions.eq("placeOrderBeneListView.serviceGroupId", serviceGroupId));
		criteria.add(Restrictions.eq("placeOrderBeneListView.customerRef",customerRef));

		criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryCountry", beneCountryId));

		criteria.add(Restrictions.isNotNull("placeOrderBeneListView.bankAccountNumber"));

		//criteria.addOrder(Order.desc("placeOrderBeneListView.createdDate"));
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		//List<BenificiaryListView> lstBeneView = (List<BenificiaryListView>) findAll(criteria);


		ProjectionList columns = Projections.projectionList();

		//columns.add(Projections.property("placeOrderBeneListView.beneficiaryAccountSeqId"), "populateId");
		columns.add(Projections.distinct(Projections.property("beneficiaryAccountSeqId")), "populateId");
		columns.add(Projections.property("placeOrderBeneListView.bankAccountNumber"),"populateCode");
		columns.add(Projections.property("placeOrderBeneListView.currencyQuoteName"), "populateName");


		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(criteria);

		return lstPopulateData;
	}

	@Override
	public void saveRecord(RatePlaceOrder ratePlaceOrderObj) {
		getSession().save( ratePlaceOrderObj);

	}

	@Override
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId,BigDecimal serviceGroupId,BigDecimal customerRef) {
		List<PopulateData> lstPopulateData=  new ArrayList<PopulateData>();

		String hqlQuery="select distinct a.benificaryName from  PlaceOrderBeneListView a where a.customerId =  :customerIdNumber  and a.benificaryCountry = :pBenificaryCountry and a.serviceGroupId = :pServiceGroupId and a.customerRef= :pcustomerRef";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerIdNumber", customerId);
		query.setParameter("pBenificaryCountry", beneCountryId);
		query.setParameter("pServiceGroupId", serviceGroupId);
		query.setParameter("pcustomerRef", customerRef);

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
	public List<PopulateData> getBeneficiarBankList(String beneName,BigDecimal serviceGroupId,BigDecimal beneCountryId,BigDecimal customerRef) {

		DetachedCriteria criteria=DetachedCriteria.forClass(PlaceOrderBeneListView.class,"placeOrderBeneListView");

		criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryName",beneName));
		criteria.add(Restrictions.eq("placeOrderBeneListView.serviceGroupId",serviceGroupId));
		criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryCountry",beneCountryId));
		criteria.add(Restrictions.eq("placeOrderBeneListView.customerRef",customerRef));

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
	public HashMap<String, BigDecimal> getBeneMasterIdCurrencyId(
			String beneName, BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId,BigDecimal customerRef,String accountNo) {

		String hqlQuery="select a.beneficaryMasterSeqId,a.currencyId,a.currencyQuoteName from  PlaceOrderBeneListView a  where a.benificaryCountry = :pBenificaryCountry and a.serviceGroupId = :pServiceGroupId  "
				+ " and a.benificaryName  =  :pBenificaryName  and a.bankId = :pBankId and a.customerRef= :pcustomerRef and a.bankAccountNumber = :paccountNumber";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pBenificaryCountry", beneCountryId);
		query.setParameter("pServiceGroupId", serviceGroupId);
		query.setParameter("pBenificaryName", beneName);
		query.setParameter("pBankId", beneBankId);
		query.setParameter("pcustomerRef", customerRef);
		query.setParameter("paccountNumber", accountNo);


		HashMap<String, BigDecimal> rtnMap = new HashMap<String, BigDecimal>();

		List<Object[]> list = query.list();

		for (Object object : list) {
			Object[] li = (Object[])object;
			if(li.length>0)
			{
				if(li[0]!=null && li[1]!=null)
				{
					rtnMap.put("BeneficaryMasterSeqId", new BigDecimal(li[0]==null ? "0" :li[0].toString()));
					rtnMap.put("CurrencyId", new BigDecimal(li[1]==null ? "0" :li[1].toString()));
					//rtnMap.put("CurrencyQuoteName", new BigDecimal(li[2]==null ? "0" :li[2].toString()));
				}

			}

		}
		return rtnMap;

	}

	@Override
	public boolean checkPlaceanOrderCreatedForCustomer(BigDecimal customerId) {

		boolean rtnCheck=false;
		DetachedCriteria criteria=DetachedCriteria.forClass(RatePlaceOrder.class,"ratePlaceOrder");

		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.U));

		List<RatePlaceOrder> lstRatePlaceOrder=  (List<RatePlaceOrder>) findAll(criteria);
		if(lstRatePlaceOrder.size()>0)
		{
			rtnCheck=true;
		}else
		{
			rtnCheck=false;
		}

		return rtnCheck;
	}

	@Override
	public String toFetchCurrencyQtyName(BigDecimal beneficiaryMasterId,
			BigDecimal beneficaryAccountSeqId,BigDecimal customerRef) {


		String currencyQtyName=null;

		String hqlQuery="select distinct a.currencyQuoteName from  PlaceOrderBeneListView a where a.beneficaryMasterSeqId =  :beneficiaryMasterId and a.beneficiaryAccountSeqId = :beneficaryAccountSeqId and a.customerRef= :pcustomerRef ";

		Query query = getSession().createQuery(hqlQuery);

		query.setParameter("beneficiaryMasterId", beneficiaryMasterId);

		query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
		query.setParameter("pcustomerRef", customerRef);

		List<String> lstbeneName =query.list();

		if(lstbeneName.size()>0){

			currencyQtyName=lstbeneName.get(0);

		}

		return currencyQtyName;


	}

	@Override
	public List<RatePlaceOrder> duplicatecheckRecord(BigDecimal customerId,BigDecimal customerRef, BigDecimal beneficiaryBankId,Date createdDate, BigDecimal beneficiaryCountryId,String accountNumber, BigDecimal serviceGroupId,BigDecimal fcOrLocalAmount,BigDecimal beneficaryMasterSeqId,BigDecimal beneficaryAccountSeqId) {
		
		List<RatePlaceOrder> lstRatePlaceOrder = new ArrayList<RatePlaceOrder>();

		if(accountNumber != null){
			String hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"trunc(a.createdDate) = trunc(:date) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.beneficiaryAccountNo = :accountNumber and "
					+"a.remitType = :serviceGroupId and "
					+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId and "
					+"a.isActive = 'U' ";
			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("customerId", customerId);
			query.setParameter("customerRef", customerRef);
			query.setParameter("beneficiaryBankId", beneficiaryBankId);
			query.setParameter("date", createdDate);
			query.setParameter("beneficiaryCountryId", beneficiaryCountryId);
			query.setParameter("accountNumber", accountNumber);
			query.setParameter("serviceGroupId", serviceGroupId);
			query.setParameter("fcOrLocalAmount", fcOrLocalAmount);
			query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
			query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
			lstRatePlaceOrder= query.list();
		}else{
			String hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"trunc(a.createdDate) = trunc(:date) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.remitType = :serviceGroupId and "
					+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId and "
					+"a.isActive = 'U' ";
			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("customerId", customerId);
			query.setParameter("customerRef", customerRef);
			query.setParameter("beneficiaryBankId", beneficiaryBankId);
			query.setParameter("date", createdDate);
			query.setParameter("beneficiaryCountryId", beneficiaryCountryId);
			query.setParameter("serviceGroupId", serviceGroupId);
			query.setParameter("fcOrLocalAmount", fcOrLocalAmount);
			query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
			query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
			lstRatePlaceOrder= query.list();
		}


		return lstRatePlaceOrder;
	}

	@Override
	public List<RatePlaceOrder> fetchSpotRateRecords(BigDecimal customerNo,Date createdDate, BigDecimal masterId, BigDecimal beneficaryBankId,
			BigDecimal beneficiaryAccountSeqId, BigDecimal dataserviceid,BigDecimal databenificarycountry, BigDecimal foriegnCurrency,
			BigDecimal amountToRemit, BigDecimal customerrefno,String accountNumber,BigDecimal countryBranchId) {
		String hqlQuery="from RatePlaceOrder a "
				+ "where a.customerId =  :customerId and "
				+"a.customerreference = :customerRef and "
				+"a.beneficiaryBankId = :beneficiaryBankId and "
				+"a.destinationCurrenyId = :foriegnCurrency and "
				+"trunc(a.createdDate) = trunc(:date) and "
				+"a.beneficiaryCountryId = :beneficiaryCountryId and "
				+"a.beneficiaryAccountNo = :accountNumber and "
				+"a.remitType = :serviceGroupId and "
				//	+"a.transactionAmount = :fcOrLocalAmount and "
				+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
				+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId and "
				+"a.applDocumentNumber is null and "
				+"a.applDocumentFinanceYear is null and "
				+"a.isActive in (:pisActive,:pisActive1)  and  "
				+" a.countryBranchId= :pcountryBranchId";
		//+"a.isActive = 'U' or "
		//+"a.isActive = 'Y' ";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerId", customerNo);
		query.setParameter("customerRef", customerrefno);
		query.setParameter("beneficiaryBankId", beneficaryBankId);
		query.setParameter("foriegnCurrency", foriegnCurrency);
		query.setParameter("date", createdDate);
		query.setParameter("beneficiaryCountryId", databenificarycountry);
		query.setParameter("accountNumber", accountNumber);
		query.setParameter("serviceGroupId", dataserviceid);
		//query.setParameter("fcOrLocalAmount", amountToRemit);
		query.setParameter("beneficaryMasterSeqId", masterId);
		query.setParameter("beneficaryAccountSeqId", beneficiaryAccountSeqId);
		query.setParameter("pisActive", Constants.U);
		query.setParameter("pisActive1", Constants.Yes);
		query.setParameter("pcountryBranchId", countryBranchId);
		List<RatePlaceOrder> lstRatePlaceOrder= query.list();
		return lstRatePlaceOrder;
	}

	@Override
	public void updateSpecialRateRequest(BigDecimal ratePlaceOrderId,BigDecimal amountToRemit) {
		RatePlaceOrder ratePlaceOrder = (RatePlaceOrder) getSession().get(RatePlaceOrder.class, ratePlaceOrderId);
		if(amountToRemit != null){
			ratePlaceOrder.setTransactionAmount(amountToRemit);
			ratePlaceOrder.setRateOffered(null);
			ratePlaceOrder.setIsActive(Constants.U);
			ratePlaceOrder.setApprovedBy(null);
			ratePlaceOrder.setApprovedDate(null);
		}else{
			ratePlaceOrder.setIsActive(Constants.D);
			ratePlaceOrder.setApprovedBy(null);
			ratePlaceOrder.setApprovedDate(null);
		}
		getSession().update(ratePlaceOrder);
	}

	@Override
	public List<RatePlaceOrder> getActiveSpecialRateRequest(BigDecimal customerNo, Date cerateDate, BigDecimal masterId,
			BigDecimal beneficaryBankId, BigDecimal beneficiaryAccountSeqId,BigDecimal dataserviceid, BigDecimal databenificarycountry,
			BigDecimal foriegnCurrency, BigDecimal amountToRemit,BigDecimal customerrefno, String dataAccountnum,BigDecimal countryBranchId) {
		
		String hqlQuery = null;
		
		if(dataAccountnum != null){
			hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"a.destinationCurrenyId = :foriegnCurrency and "
					+"trunc(a.createdDate) = trunc(sysdate) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.beneficiaryAccountNo = :accountNumber and "
					+"a.remitType = :serviceGroupId and "
					+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId and "
					+"a.applDocumentNumber is null and "
					+"a.applDocumentFinanceYear is null and "
					+"a.isActive in (:pisActive,:pisActive1)  and  "
					+" a.countryBranchId= :pcountryBranchId";
		}else{
			hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"a.destinationCurrenyId = :foriegnCurrency and "
					+"trunc(a.createdDate) = trunc(sysdate) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.remitType = :serviceGroupId and "
					+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId and "
					+"a.applDocumentNumber is null and "
					+"a.applDocumentFinanceYear is null and "
					+"a.isActive in (:pisActive,:pisActive1)  and  "
					+" a.countryBranchId= :pcountryBranchId";
		}

		

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerId", customerNo);
		query.setParameter("customerRef", customerrefno);
		query.setParameter("beneficiaryBankId", beneficaryBankId);
		query.setParameter("foriegnCurrency", foriegnCurrency);
		//query.setParameter("date", cerateDate);
		query.setParameter("beneficiaryCountryId", databenificarycountry);
		if(dataAccountnum != null){
			query.setParameter("accountNumber", dataAccountnum);
		}
		query.setParameter("serviceGroupId", dataserviceid);
		query.setParameter("fcOrLocalAmount", amountToRemit);
		query.setParameter("beneficaryMasterSeqId", masterId);
		query.setParameter("beneficaryAccountSeqId", beneficiaryAccountSeqId);
		query.setParameter("pisActive", Constants.U);
		query.setParameter("pisActive1", Constants.Yes);
		query.setParameter("pcountryBranchId", countryBranchId);

		List<RatePlaceOrder> lstRatePlaceOrder= query.list();
		return lstRatePlaceOrder;
	}

	@Override
	public List<RatePlaceOrder> checkPlaceAnorderExist(BigDecimal customerId,
			BigDecimal countryBranchId) throws AMGException{

		/*DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));

		criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",countryBranchId));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.isNull("ratePlaceOrder.branchSupportIndicator"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.appointmentTime"));


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);*/

		String hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
				+ " and ratePlaceOrder.customerId = :customerId"
				+ " and ratePlaceOrder.isActive = :pisActive "
				//+ " and ratePlaceOrder.countryBranchId = :pcountryBranchId"
				+ " and ratePlaceOrder.branchSupportIndicator is null"
				+ " and ratePlaceOrder.applDocumentNumber is null" 
				+ " and ratePlaceOrder.applDocumentFinanceYear is null"
				+ " and ratePlaceOrder.appointmentTime is null"
				+ " and ratePlaceOrder.approvedBy is not null"
				+ " and ratePlaceOrder.approvedDate is not null" ;
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("pisActive", Constants.Yes); 
		//query.setParameter("pcountryBranchId", countryBranchId);


		//query.setParameter("pisActive1", Constants.U); 
		List<RatePlaceOrder> lstRatePlaceOrders = query.list();


		return lstRatePlaceOrders;
	}

	@Override
	public void updatePlaceOrderRemitDocumentNo(BigDecimal ratePlaceOrderId,
			BigDecimal remittDocNo, BigDecimal remittFinYear) {
		RatePlaceOrder ratePlaceOrder = (RatePlaceOrder) getSession().get(RatePlaceOrder.class, ratePlaceOrderId);
		ratePlaceOrder.setRemitDocumentYear(remittFinYear);
		ratePlaceOrder.setRemitDocumentNumber(remittDocNo);
		ratePlaceOrder.setTransactionConcluded(Constants.Yes);
		getSession().update(ratePlaceOrder);

	}

	@Override
	public BigDecimal toFetchAccountSeqId(BigDecimal beneficaryMasterSeqId,String beneficiaryName, BigDecimal beneficiaryBankId) {
		BigDecimal accountSeq=null;
		String hqlQuery="select distinct a.beneficiaryAccountSeqId from  PlaceOrderBeneListView a where a.beneficaryMasterSeqId =  :pbeneficaryMasterSeqId  and a.benificaryName = :pbeneficiaryName and a.bankId = :pbeneficiaryBankId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pbeneficaryMasterSeqId", beneficaryMasterSeqId);
		query.setParameter("pbeneficiaryName", beneficiaryName);
		query.setParameter("pbeneficiaryBankId", beneficiaryBankId);
		List<BigDecimal> lstbeneName =query.list();
		if(lstbeneName.size()>0){
			accountSeq=lstbeneName.get(0);
		}
		return accountSeq;
	}

	@Override
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.setFetchMode("currencyMaster.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyMaster.isactive", Constants.Yes));
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CurrencyMaster> lstCurrencyMaster=(List<CurrencyMaster>) findAll(dCriteria);

		List<PopulateData> lstofCurrency = new ArrayList<PopulateData>();

		for(CurrencyMaster currencyMaster : lstCurrencyMaster)
		{
			PopulateData populateData= new PopulateData();
			populateData.setPopulateCode(currencyMaster.getCurrencyCode());
			populateData.setPopulateId(currencyMaster.getCurrencyId());
			populateData.setPopulateName(currencyMaster.getCurrencyName());
			lstofCurrency.add(populateData);
		}
		return lstofCurrency;
	}


	@Override
	public List<PopulateData> getBeneCorespondingBankListBasedOnCountru(
			BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",	JoinType.INNER_JOIN);
		// dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", getBankCountry));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));

		BigDecimal beneBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId",corresBankIndicatorId));

		Disjunction lstjunction = Restrictions.disjunction();
		if (corresBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",corresBankIndicatorId));
		}
		if (beneBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",beneBankIndicatorId));
		}
		dCriteria.add(lstjunction);
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.property("bankMaster.bankId"), "populateId");
		//columns.add(Projections.property("bankId"), "populateId");
		columns.add(Projections.property("bankMaster.bankCode"),"populateCode");
		columns.add(Projections.property("bankMaster.bankFullName"), "populateName");
		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		//dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PopulateData> lstPopulateData =  (List<PopulateData>) findAll(dCriteria);

		return lstPopulateData;
	}

	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankIndicator.class, "bankIndicator");

		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode",
				bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);

		if (lstBankIndId.size() != 0) {
			return ((List<BankIndicator>) findAll(dCriteria)).get(0)
					.getBankIndicatorId();
		}

		return null;
	}

	@Override
	public List<PopulateData> getRequestCurrency(BigDecimal bankId)
	{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				SourceCurrencyView.class, "sourceCurrencyView");

		dCriteria.add(Restrictions.eq("sourceCurrencyView.bankId",
				bankId));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("currencyId")), "populateId");

		//columns.add(Projections.property("bankId"), "populateId");
		columns.add(Projections.property("currencyCode"),"populateCode");
		columns.add(Projections.property("currencyName"), "populateName");


		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(dCriteria);

		/*dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<SourceCurrencyView> lstSourceCurrencyView = (List<SourceCurrencyView>) findAll(dCriteria);*/
		return lstPopulateData;
	}
	@Override
	public List<PopulateData> getDestinationCurrency(BigDecimal bankId)
	{
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				DestinationCurrencyView.class, "destinationCurrencyView");

		dCriteria.add(Restrictions.eq("destinationCurrencyView.bankId",
				bankId));

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("currencyId")), "populateId");

		//columns.add(Projections.property("bankId"), "populateId");
		columns.add(Projections.property("currencyCode"),"populateCode");
		columns.add(Projections.property("currencyName"), "populateName");


		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(dCriteria);

		/*dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DestinationCurrencyView> lstDestinationCurrencyView = (List<DestinationCurrencyView>) findAll(dCriteria);
		return lstDestinationCurrencyView;*/
		return lstPopulateData;
	}

	@Override
	public List<PopulateData> fetchBeneMasterDetails(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerRef, String AccountNo) {

		List<PopulateData> lstofBeneDt = new ArrayList<PopulateData>();

		String hqlQuery="select a.beneficaryMasterSeqId,a.currencyId,a.currencyQuoteName from  PlaceOrderBeneListView a  where a.benificaryCountry = :pBenificaryCountry and a.serviceGroupId = :pServiceGroupId  "
				+ " and a.benificaryName  =  :pBenificaryName  and a.bankId = :pBankId and a.customerRef= :pcustomerRef and a.bankAccountNumber = :paccountNumber";

		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pBenificaryCountry", beneCountryId);
		query.setParameter("pServiceGroupId", serviceGroupId);
		query.setParameter("pBenificaryName", beneName);
		query.setParameter("pBankId", beneBankId);
		query.setParameter("pcustomerRef", customerRef);
		query.setParameter("paccountNumber", AccountNo);


		HashMap<String, BigDecimal> rtnMap = new HashMap<String, BigDecimal>();

		List<Object[]> list = query.list();

		for (Object object : list) {
			Object[] li = (Object[])object;
			if(li.length>0)
			{
				PopulateData populateData= new PopulateData();
				populateData.setPopulateId(new BigDecimal(li[0]==null ? "0" :li[0].toString()));  //bene master Id
				populateData.setPopulateCode(li[1]==null ? "" :li[1].toString());                 //currency Id
				populateData.setPopulateName(li[2]==null ? "" :li[2].toString());                 //currency Quote
				lstofBeneDt.add(populateData);
			}

		}

		return lstofBeneDt;

	}

	@Override
	public List<PopulateData> fetchBeneMasterDetailsfromView(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerRef, String AccountNo) {

		List<PopulateData> lstofBeneDt = new ArrayList<PopulateData>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(PlaceOrderBeneListView.class, "placeOrderBeneListView");

		if(beneName != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.benificaryName",beneName));
		}

		if(beneCountryId != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.benificaryCountry",beneCountryId));
		}

		if(beneBankId != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.bankId",beneBankId));
		}

		if(serviceGroupId != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.serviceGroupId",serviceGroupId));
		}

		if(customerRef != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.customerRef",customerRef));
		}

		if(AccountNo != null){
			dCriteria.add(Restrictions.eq("placeOrderBeneListView.bankAccountNumber",AccountNo));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PlaceOrderBeneListView> lstPlaceOrderBeneListView =  (List<PlaceOrderBeneListView>) findAll(dCriteria);

		for (PlaceOrderBeneListView placeOrderBeneListView : lstPlaceOrderBeneListView) {
			PopulateData populateData= new PopulateData();
			populateData.setPopulateId(placeOrderBeneListView.getBeneficaryMasterSeqId());  //bene master Id
			if(placeOrderBeneListView.getCurrencyId() != null){
				populateData.setPopulateCode(placeOrderBeneListView.getCurrencyId().toPlainString());   //currency Id  
			}
			populateData.setPopulateName(placeOrderBeneListView.getCurrencyQuoteName());                 //currency Quote
			lstofBeneDt.add(populateData);
		}


		return lstofBeneDt;
	}

	@Override
	public List<PopulateData> getBeneficiaryAgents(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId) {

		List<PopulateData> lstofBeneDt = new ArrayList<PopulateData>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");

		if(beneName != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryName",beneName));
		}

		if(beneCountryId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryCountry",beneCountryId));
		}

		if(beneBankId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceProvider",beneBankId));
		}

		if(serviceGroupId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceGroupId",serviceGroupId));
		}

		if(customerId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.customerId",customerId));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> benificiaryListViewDt =  (List<BenificiaryListView>) findAll(dCriteria);

		for (BenificiaryListView benificiaryListViewDtValue : benificiaryListViewDt) {
			
			if(!duplicateCheck.contains(benificiaryListViewDtValue.getBankId())){
				duplicateCheck.add(benificiaryListViewDtValue.getBankId());

				PopulateData populateData= new PopulateData();
				populateData.setPopulateId(benificiaryListViewDtValue.getBankId());       //bene Bank Agent Id
				populateData.setPopulateCode(benificiaryListViewDtValue.getBankCode());   //bene Bank Agent code
				populateData.setPopulateName(benificiaryListViewDtValue.getBankName());   //bene Bank Agent name
				lstofBeneDt.add(populateData);
			}
		}

		return lstofBeneDt;
	}

	@Override
	public List<PopulateData> getBeneficiaryAgentsBranch(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId, BigDecimal agentId) {

		List<PopulateData> lstofBeneDt = new ArrayList<PopulateData>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");

		if(beneName != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryName",beneName));
		}

		if(beneCountryId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryCountry",beneCountryId));
		}

		if(beneBankId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceProvider",beneBankId));
		}

		if(serviceGroupId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceGroupId",serviceGroupId));
		}

		if(customerId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.customerId",customerId));
		}

		if(agentId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.bankId",agentId));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> benificiaryListViewDt =  (List<BenificiaryListView>) findAll(dCriteria);

		for (BenificiaryListView benificiaryListViewDtValue : benificiaryListViewDt) {
			
			if(!duplicateCheck.contains(benificiaryListViewDtValue.getBranchId())){
				duplicateCheck.add(benificiaryListViewDtValue.getBranchId());
				
				PopulateData populateData= new PopulateData();
				populateData.setPopulateId(benificiaryListViewDtValue.getBranchId());           //bene Agent Bank Branch  Id
				if(benificiaryListViewDtValue.getBranchCode() != null){
					populateData.setPopulateCode(benificiaryListViewDtValue.getBranchCode().toPlainString());       //bene Agent Bank Branch code
				}
				populateData.setPopulateName(benificiaryListViewDtValue.getBankBranchName());   //bene Agent Bank Branch name
				lstofBeneDt.add(populateData);
			}
		}

		return lstofBeneDt;
	}

	@Override
	public List<BenificiaryListView> getBeneficiaryDetails(String beneName,
			BigDecimal beneCountryId, BigDecimal beneBankId,
			BigDecimal serviceGroupId, BigDecimal customerId,
			BigDecimal agentId, BigDecimal agentbranchId, String accountNumber, BigDecimal currencyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");

		if(beneName != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryName",beneName));
		}

		if(beneCountryId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryCountry",beneCountryId));
		}

		if(serviceGroupId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceGroupId",serviceGroupId));
		}

		if(customerId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.customerId",customerId));
		}

		if(currencyId != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.currencyId",currencyId));
		}

		if(accountNumber != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.bankAccountNumber",accountNumber));

			if(beneBankId != null){
				dCriteria.add(Restrictions.eq("benificiaryListView.bankId",beneBankId));
			}
		}else{
			if(beneBankId != null){
				dCriteria.add(Restrictions.eq("benificiaryListView.serviceProvider",beneBankId));
			}

			if(agentId != null){
				dCriteria.add(Restrictions.eq("benificiaryListView.bankId",agentId));
			}

			if(agentbranchId != null){
				dCriteria.add(Restrictions.eq("benificiaryListView.branchId",agentbranchId));
			}
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> benificiaryListViewDt =  (List<BenificiaryListView>) findAll(dCriteria);

		return benificiaryListViewDt;
	}

	@Override
	public List<BenificiaryListView> getBeneficiaryDetailsForAccept(
			BranchStaffGSMRateDataTable dataTable) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BenificiaryListView.class, "benificiaryListView");

		if(dataTable.getBeneficiaryName() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryName",dataTable.getBeneficiaryName()));
		}

		if(dataTable.getBeneficiaryCountryId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.benificaryCountry",dataTable.getBeneficiaryCountryId()));
		}

		if(dataTable.getRemittType() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.serviceGroupId",dataTable.getRemittType()));
		}

		if(dataTable.getCustomerId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.customerId",dataTable.getCustomerId()));
		}

		if(dataTable.getBeneCurrencyId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.currencyId",dataTable.getBeneCurrencyId()));
		}
		
		if(dataTable.getBeneficiaryBankId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.bankId",dataTable.getBeneficiaryBankId()));
		}

		if(dataTable.getBeneficiaryAccountNo() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.bankAccountNumber",dataTable.getBeneficiaryAccountNo()));
		}
		
		if(dataTable.getBeneficiaryMasterId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.beneficaryMasterSeqId",dataTable.getBeneficiaryMasterId()));
		}

		if(dataTable.getBeneficiaryAccountSeqId() != null){
			dCriteria.add(Restrictions.eq("benificiaryListView.beneficiaryAccountSeqId",dataTable.getBeneficiaryAccountSeqId()));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BenificiaryListView> benificiaryListViewDt =  (List<BenificiaryListView>) findAll(dCriteria);

		return benificiaryListViewDt;
	}

	@Override
	public List<ViewPlaceOnOrderInquiry> fetchplaceOnOrderInquiry(BigDecimal countryBranchId,String userName) throws AMGException {

		/*String hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
				+ " and ratePlaceOrder.customerId = :customerId"
				//+ " and ratePlaceOrder.isActive = :pisActive "
				+ " and ratePlaceOrder.countryBranchId = :pcountryBranchId"
				+ " and ratePlaceOrder.branchSupportIndicator is null"
				+ " and ratePlaceOrder.applDocumentNumber is null" 
				+ " and ratePlaceOrder.applDocumentFinanceYear is null"
				+ " and ratePlaceOrder.appointmentTime is null" ;
				//+ " and ratePlaceOrder.approvedBy is not null"
				//+ " and ratePlaceOrder.approvedDate is not null" ;
		Query query = getSession().createQuery(hql); 

		//query.setParameter("pisActive", Constants.Yes); 
		query.setParameter("pcountryBranchId", countryBranchId);*/
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewPlaceOnOrderInquiry.class, "viewPlaceOnOrderInquiry");
		
		dCriteria.add(Restrictions.eq("viewPlaceOnOrderInquiry.countryBranchId",countryBranchId));
		
		if(userName != null){
			dCriteria.add(Restrictions.eq("viewPlaceOnOrderInquiry.createdBy",userName));
		}
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewPlaceOnOrderInquiry> viewPlaceOnOrderInquiry =  (List<ViewPlaceOnOrderInquiry>) findAll(dCriteria);

		return viewPlaceOnOrderInquiry;
	}

	@Override
	public List<CurrencyOtherInformation> fetchCurrencyMasterOthInfo(BigDecimal currencyId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyOtherInformation.class,"currencyMasterInfo");
		criteria.setFetchMode("currencyMasterInfo.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("currencyMasterInfo.exCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("currencyMasterInfo.exCurrencyMaster","exCurrencyMaster", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("exCurrencyMaster.isactive", Constants.Yes));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyOtherInformation> lstcurrency = (List<CurrencyOtherInformation>) findAll(criteria);	
		
		return lstcurrency;
	}

	@Override
	public List<RatePlaceOrder> authLimitCheck(BigDecimal customerId,
			BigDecimal customerRef, BigDecimal beneficiaryBankId,
			Date createdDate, BigDecimal beneficiaryCountryId,
			String accountNumber, BigDecimal serviceGroupId,
			BigDecimal fcOrLocalAmount, BigDecimal beneficaryMasterSeqId,
			BigDecimal beneficaryAccountSeqId) {
		
		List<RatePlaceOrder> lstRatePlaceOrder = new ArrayList<RatePlaceOrder>();

		if(accountNumber != null){
			String hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"trunc(a.createdDate) = trunc(:date) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.beneficiaryAccountNo = :accountNumber and "
					+"a.remitType = :serviceGroupId and "
					//+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId ";
					//+"a.isActive = 'U' ";
			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("customerId", customerId);
			query.setParameter("customerRef", customerRef);
			query.setParameter("beneficiaryBankId", beneficiaryBankId);
			query.setParameter("date", createdDate);
			query.setParameter("beneficiaryCountryId", beneficiaryCountryId);
			query.setParameter("accountNumber", accountNumber);
			query.setParameter("serviceGroupId", serviceGroupId);
			//query.setParameter("fcOrLocalAmount", fcOrLocalAmount);
			query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
			query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
			lstRatePlaceOrder= query.list();
		}else{
			String hqlQuery="from RatePlaceOrder a "
					+ "where a.customerId =  :customerId and "
					+"a.customerreference = :customerRef and "
					+"a.beneficiaryBankId = :beneficiaryBankId and "
					+"trunc(a.createdDate) = trunc(:date) and "
					+"a.beneficiaryCountryId = :beneficiaryCountryId and "
					+"a.remitType = :serviceGroupId and "
					//+"a.transactionAmount = :fcOrLocalAmount and "
					+"a.beneficiaryMasterId.beneficaryMasterSeqId = :beneficaryMasterSeqId and "
					+"a.accountSeqquenceId.beneficaryAccountSeqId = :beneficaryAccountSeqId ";
					//+"a.isActive = 'U' ";
			Query query = getSession().createQuery(hqlQuery);
			query.setParameter("customerId", customerId);
			query.setParameter("customerRef", customerRef);
			query.setParameter("beneficiaryBankId", beneficiaryBankId);
			query.setParameter("date", createdDate);
			query.setParameter("beneficiaryCountryId", beneficiaryCountryId);
			query.setParameter("serviceGroupId", serviceGroupId);
			//query.setParameter("fcOrLocalAmount", fcOrLocalAmount);
			query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
			query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
			lstRatePlaceOrder= query.list();
		}

		return lstRatePlaceOrder;
	}

	@Override
	public List<ViewPlaceOnOrderFullInquiry> fetchplaceOnOrderInquiryDetails(BigDecimal countryBranchId,String userName,Date placeOrderDt1,Date placeOrderDt2)
					throws AMGException {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewPlaceOnOrderFullInquiry.class, "ratePlaceOrder");

		if(countryBranchId != null){
			dCriteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId", countryBranchId));
		}

		if(userName != null){
			dCriteria.add(Restrictions.eq("ratePlaceOrder.createdBy", userName));
		}

		if(placeOrderDt1 != null){
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(placeOrderDt1);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			System.out.println(fromDate);

			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(placeOrderDt2);
			calendar1.set(Calendar.HOUR_OF_DAY, 23);
			calendar1.set(Calendar.MINUTE, 59);
			calendar1.set(Calendar.SECOND, 59);
			Date toDate = calendar1.getTime();
			System.out.println(toDate);

			dCriteria.add(Restrictions.between("ratePlaceOrder.valueDate", fromDate, toDate));
		}
		
		dCriteria.addOrder(Order.desc("ratePlaceOrder.createdDate"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewPlaceOnOrderFullInquiry> lstplaceOrder = (List<ViewPlaceOnOrderFullInquiry>) findAll(dCriteria);

		return lstplaceOrder;	
	}
}
