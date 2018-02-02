package com.amg.exchange.online.daoimpl;

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
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.bean.GSMPlaceOrderDataTable;
import com.amg.exchange.online.dao.IGSMPlaceOrderRateFeedDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class GSMPlaceOrderRateFeedDaolmpl extends CommonDaoImpl implements IGSMPlaceOrderRateFeedDao {

	SessionStateManage session= new SessionStateManage();
	Logger LOGGER = Logger.getLogger(GSMPlaceOrderRateFeedDaolmpl.class);

	@Override
	public List<RatePlaceOrder> fetchAllRecrds(BigDecimal customerId,BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		//criteria.add(Restrictions.between("ratePlaceOrder.createdDate",dateFormat.format(new Date()),dateFormat.format(new Date().getMinutes())));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.addOrder(Order.desc("ratePlaceOrder.createdDate"));
		if(countryBranchId != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",countryBranchId));	
		}
		//List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)criteria.getExecutableCriteria(getSession()).setMaxResults(5).list();
		return lstRatePlaceOrders;
	}

	@Override
	public String approveAllRecords(BigDecimal rateOfferedIdPk,BigDecimal rateOffered, String custIndicator,BigDecimal dataserviceid, BigDecimal routingCountry,BigDecimal routingBank, BigDecimal remitMode,
			BigDecimal deliveryMode, BigDecimal routingBranch, String userName,String customerUniqueNumber) {
		String approveMsg;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedIdPk);
		String approvedUser = ratePlaceOrder.getApprovedBy();
		if (approvedUser == null) {
			ratePlaceOrder.setIsActive(Constants.Yes);
			ratePlaceOrder.setApprovedBy(userName);
			ratePlaceOrder.setApprovedDate(new Date());
			ratePlaceOrder.setRateOffered(rateOffered);
			ratePlaceOrder.setCustomerUnqiueNumber(customerUniqueNumber);
			ratePlaceOrder.setCustomerIndicator(custIndicator);
			ratePlaceOrder.setServiceMasterId(dataserviceid);
			ratePlaceOrder.setRemittanceModeId(remitMode);
			ratePlaceOrder.setRoutingCountryId(routingCountry);
			ratePlaceOrder.setRoutingBankId(routingBank);
			ratePlaceOrder.setDeliveryModeId(deliveryMode);
			ratePlaceOrder.setRoutingBranchId(routingBranch);
			getSession().update(ratePlaceOrder);
			approveMsg = "Success";
		} else {
			approveMsg = "Fail";
		}
		return approveMsg;
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsforUnApproved(BigDecimal countryBranchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.U));
		if(countryBranchId != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",countryBranchId));	
		}
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public String toFetchServiceGroupDesc(BigDecimal languageId,BigDecimal remitType) {
		String serviceGroupDesc=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceGroupMasterDesc.class, "serviceGroupMasterDesc");
		//language id
		criteria.setFetchMode("serviceGroupMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",languageId));
		//serviceGroupId
		criteria.setFetchMode("serviceGroupMasterDesc.serviceGroupMasterId", FetchMode.JOIN);
		criteria.createAlias("serviceGroupMasterDesc.serviceGroupMasterId", "serviceGroupMasterId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceGroupMasterId.serviceGroupId",remitType));
		//IsActive check
		criteria.add(Restrictions.eq("serviceGroupMasterId.isActive",Constants.Yes));
		List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc=(List<ServiceGroupMasterDesc>)findAll(criteria);
		if(lstServiceGroupMasterDesc.size()>0){
			serviceGroupDesc=lstServiceGroupMasterDesc.get(0).getServiceGroupDesc();
		}
		return serviceGroupDesc;
	}

	@Override
	public String toFetchBeneficiaryName(BigDecimal beneficiaryMasterId) {
		String beneName=null;
		String hqlQuery="select distinct a.benificaryName from  PlaceOrderBeneListView a where a.beneficaryMasterSeqId =  :beneficiaryMasterId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("beneficiaryMasterId", beneficiaryMasterId);
		List<String> lstbeneName =query.list();
		if(lstbeneName.size()>0){
			beneName=lstbeneName.get(0);
		}
		return beneName;
	}

	@Override
	public String toFetchCurrencyQtyName(BigDecimal currencyId) {
		String currencyQtyName=null;
		String hqlQuery="select distinct a.quoteName from  CurrencyMasterView a where a.currencyId =  :currencyId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("currencyId", currencyId);
		List<String> lstbeneName =query.list();
		if(lstbeneName.size()>0){
			currencyQtyName=lstbeneName.get(0);
		}
		return currencyQtyName;
	}

	@Override
	public List<ExchangeRateApprovalDetModel> getMinMaxRate(BigDecimal countryId,BigDecimal bankId,BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExchangeRateApprovalDetModel.class, "exchangeRateApprovalDetModel");
		//country Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryId",countryId));
		//bank id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.bankId",bankId));
		//currency Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.currencyId",currencyId));
		//IsActive check
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.isActive",Constants.Yes));
		List<ExchangeRateApprovalDetModel> lstExchangeRateApprovalDetModel=(List<ExchangeRateApprovalDetModel>)findAll(criteria);
		return lstExchangeRateApprovalDetModel;
	}

	@Override
	public List<RatePlaceOrder> CheckUnqiueNumber(String customerUniqueKeyNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.eq("ratePlaceOrder.customerUnqiueNumber",customerUniqueKeyNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public String toFetchBranchName(BigDecimal countryBranchId) {
		String branchName=null;
		String hqlQuery="select distinct a.branchName from  CountryBranch a where a.countryBranchId =  :countryBranchId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("countryBranchId", countryBranchId);
		List<String> lstbeneName =query.list();
		if(lstbeneName.size()>0){
			branchName=lstbeneName.get(0);
		}
		return branchName;
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsUnapprovedRecrdsBasedonCusterId(BigDecimal customerId, BigDecimal countryBranchid) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.U));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		if(countryBranchid != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",countryBranchid));	
		}
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public BigDecimal toFetchCustomerRef(BigDecimal customerId) {
		BigDecimal custrRef=null;
		String hqlQuery="select distinct a.customerReference from  Customer a where a.customerId =  :customerId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerId", customerId);
		List<BigDecimal> lstCustRef =query.list();
		if(lstCustRef.size()>0){
			custrRef=lstCustRef.get(0);
		}
		return custrRef;
	}

	@Override
	public BigDecimal toFetchBankBranchId(BigDecimal beneficiaryBankId,BigDecimal beneficaryMasterSeqId, BigDecimal beneficaryAccountSeqId) {
		BigDecimal beneBankBranchName=null;
		String hqlQuery="select distinct a.branchId from  PlaceOrderBeneListView a where a.bankId = :beneficiaryBankId and  a.beneficaryMasterSeqId =  :beneficaryMasterSeqId and a.beneficiaryAccountSeqId = :beneficaryAccountSeqId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("beneficiaryBankId", beneficiaryBankId);
		query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
		query.setParameter("beneficaryAccountSeqId", beneficaryAccountSeqId);
		List<BigDecimal> lstbeneBankBranchName =query.list();
		if(lstbeneBankBranchName.size()>0){
			beneBankBranchName=lstbeneBankBranchName.get(0);
		}
		return beneBankBranchName;
	}
	@Override
	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,String isActive) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");

		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("paymentModeDesc.languageType",FetchMode.JOIN);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", langId));

		dCriteria.add(Restrictions.eq("paymentMode.isActive", isActive));

		Criterion criteria1=Restrictions.eq("paymentMode.paymentCode" , Constants.ChequeCode);
		Criterion criteria2=Restrictions.eq("paymentMode.paymentCode",Constants.KNETCode);
		LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		dCriteria.add( orExp );


		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return ((List<PaymentModeDesc>)findAll(dCriteria));
	}

	@Override
	public void rejectPlaceorder(BigDecimal placeorderPk) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeorderPk);

		ratePlaceOrder.setIsActive(Constants.D);

		getSession().update(ratePlaceOrder);
	}

	@Override
	public BigDecimal getBeneBranchId(BigDecimal beneficiaryBankId,
			BigDecimal beneficaryMasterSeqId, String beneName) {
		BigDecimal beneBankBranchName=null;
		String hqlQuery="select distinct a.branchId from  PlaceOrderBeneListView a where a.bankId = :beneficiaryBankId and  a.beneficaryMasterSeqId =  :beneficaryMasterSeqId and a.benificaryName = :pbenificaryName";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("beneficiaryBankId", beneficiaryBankId);
		query.setParameter("beneficaryMasterSeqId", beneficaryMasterSeqId);
		query.setParameter("pbenificaryName", beneName);
		List<BigDecimal> lstbeneBankBranchName =query.list();
		if(lstbeneBankBranchName.size()>0){
			beneBankBranchName=lstbeneBankBranchName.get(0);
		}
		return beneBankBranchName;
	}

	//new changes services starts 19052016
	@Override
	public List<Employee> toFetchEmployeeArea() {
		List<Employee> listEmployeeDto=new ArrayList<Employee>();
		String hqlQuery="select distinct a.location from  Employee a where a.isActive= :pIsActive";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pIsActive", Constants.Yes);
		List<String> lstEmployee =query.list();
		if(lstEmployee.size()>0){
			for (String string : lstEmployee) {
				//String string2=string;
				if(string!=null)
				{
					Employee employeeDto=new Employee();
					employeeDto.setLocation(string);
					listEmployeeDto.add(employeeDto);
				}

			}
		}
		return listEmployeeDto;
	}

	@Override
	public List<RatePlaceOrder> toFetchRoutingDetails(String area,BigDecimal countryBranchid, BigDecimal currencyid,
			BigDecimal customerReference, BigDecimal beneficiaryBankid,String fixRateid,BigDecimal beneficiaryCountryId,List<PopulateData> lstPopDat,String eftorTT) {
		/*	DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.U));
		if(area != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.areaName",area));	
		}
		if(countryBranchid != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",countryBranchid));	
		}
		if(currencyid != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.currencyId",currencyid));
		}
		if(customerReference != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.customerreference",customerReference));
		}
		if(beneficiaryBankid != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.beneficiaryBankId",beneficiaryBankid));
		}
		//criteria.add(Restrictions.eq("ratePlaceOrder.createdDate",new Date()));

		if(fixRateid != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.negotiateSts",fixRateid));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);*/
		
		String benebankLst = null;
		if(lstPopDat != null && lstPopDat.size() != 0){
			for (PopulateData populateData : lstPopDat) {
				if(beneficiaryBankid != null){
					if(populateData.getPopulateId().compareTo(beneficiaryBankid) != 0){
						benebankLst = (benebankLst != null ? benebankLst.concat(",") : "").concat(populateData.getPopulateId().toPlainString());
					}
				}else{
					benebankLst = (benebankLst != null ? benebankLst.concat(",") : "").concat(populateData.getPopulateId().toPlainString());
				}
			}
		}
		
		String hql = null;
		
		if(benebankLst != null && !benebankLst.equalsIgnoreCase("") && eftorTT != null){
			if(eftorTT.equalsIgnoreCase(Constants.EFTNAME)){
				hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
						+ " and ratePlaceOrder.isActive = :pisActive "
						+ " and ratePlaceOrder.approvedBy is null"
						+ " and ratePlaceOrder.approvedDate is null"
						+ " and ratePlaceOrder.beneficiaryAccountNo is not null"
						+ " and ratePlaceOrder.beneficiaryBankId not in ("+benebankLst+")";
			}else if(eftorTT.equalsIgnoreCase(Constants.TTNAME)){
				hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
						+ " and ratePlaceOrder.isActive = :pisActive "
						+ " and ratePlaceOrder.approvedBy is null"
						+ " and ratePlaceOrder.approvedDate is null"
						+ " and ratePlaceOrder.beneficiaryAccountNo is not null"
						+ " and ratePlaceOrder.beneficiaryBankId in ("+beneficiaryBankid+")";
			}else if(eftorTT.equalsIgnoreCase(Constants.CASHNAME)){
				hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
						+ " and ratePlaceOrder.isActive = :pisActive "
						+ " and ratePlaceOrder.approvedBy is null"
						+ " and ratePlaceOrder.approvedDate is null"
						+ " and ratePlaceOrder.beneficiaryAccountNo is null";
			}else{
				hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
						+ " and ratePlaceOrder.isActive = :pisActive "
						+ " and ratePlaceOrder.approvedBy is null"
						+ " and ratePlaceOrder.approvedDate is null";
			}
		}else{
			hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
					+ " and ratePlaceOrder.isActive = :pisActive "
					+ " and ratePlaceOrder.approvedBy is null"
					+ " and ratePlaceOrder.approvedDate is null";
		}

		String lArea="";
		if(area != null)
		{
			lArea=" and ratePlaceOrder.areaName = :pareaName";
		}
		String lCountryBranchid="";
		if(countryBranchid != null){
			lCountryBranchid=" and ratePlaceOrder.countryBranchId = :pcountryBranchId";
		}
		String lCurrencyid="";
		if(currencyid != null){
			lCurrencyid=" and ratePlaceOrder.destinationCurrenyId = :pcurrencyId";
		}
		String	lCustomerReference="";
		if(customerReference != null){
			lCustomerReference=" and ratePlaceOrder.customerreference = :pcustomerreference";
		}
		String	lBeneficiaryBankid="";		
		if(beneficiaryBankid != null){
			lBeneficiaryBankid=" and ratePlaceOrder.beneficiaryBankId = :pbeneficiaryBankId";
		}

		String lbeneficiaryCountryId="";
		if(beneficiaryCountryId!=null)
		{
			lbeneficiaryCountryId=" and ratePlaceOrder.beneficiaryCountryId = :pbeneficiaryCountryId";
		}

		String mainHql=hql+lArea+lCountryBranchid+lCurrencyid+lCustomerReference+lBeneficiaryBankid+lbeneficiaryCountryId;

		Query query = getSession().createQuery(mainHql.trim()); 

		query.setParameter("pisActive", Constants.U); 
		if(area != null)
		{
			query.setParameter("pareaName", area);
		}
		
		if(countryBranchid != null){
			query.setParameter("pcountryBranchId", countryBranchid); 
		}

		if(currencyid != null){
			query.setParameter("pcurrencyId", currencyid);
		}
		
		if(customerReference != null){
			query.setParameter("pcustomerreference", customerReference); 
		}

		if(beneficiaryBankid != null){
			query.setParameter("pbeneficiaryBankId", beneficiaryBankid);
		}
		
		if(beneficiaryCountryId != null){
			query.setParameter("pbeneficiaryCountryId", beneficiaryCountryId);
		}


		//query.setParameter("pisActive1", Constants.U); 
		List<RatePlaceOrder> lstRatePlaceOrders = query.list();

		return lstRatePlaceOrders;
	}

	@Override
	public List<PopulateData> toFtechAllRoutingCountry(BigDecimal currencyid,BigDecimal languaueId,BigDecimal servicegroupId,BigDecimal benecountryId) {
		List<PopulateData> lstRouting=new ArrayList<PopulateData>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		lstRouting.clear();
		//BigDecimal routingCountryId=null;
		String hqlQuery = null;
		Query query = null;

		// servicegroupId == 1 -- cash // servicegroupId == 2 BankingChannel
		if(servicegroupId != null){

			//cash Product - CASH  -- // banking Channel - EFT and TT
			List<ServiceMaster> lstServiceMaster = fetchServiceAllValues(servicegroupId);
			
			if(lstServiceMaster != null && lstServiceMaster.size() != 0){
				
				DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

				dCriteria.setFetchMode("routingHeader.exCurrenyId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId", JoinType.INNER_JOIN);
				dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));
				
				dCriteria.setFetchMode("routingHeader.exCountryId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exCountryId", "exCountryId", JoinType.INNER_JOIN);
				dCriteria.add(Restrictions.eq("exCountryId.countryId", benecountryId));

				dCriteria.add(Restrictions.eq("routingHeader.isActive", Constants.Yes));

				Disjunction lstjunction = Restrictions.disjunction();
				
				dCriteria.setFetchMode("routingHeader.exServiceId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exServiceId", "exServiceId", JoinType.INNER_JOIN);
				for (ServiceMaster serviceMaster : lstServiceMaster) {
					lstjunction.add(Restrictions.eq("exServiceId.serviceId",serviceMaster.getServiceId()));
				}
				
				dCriteria.add(lstjunction);
				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				
				List<RoutingHeader> lstRoutingHeader = (List<RoutingHeader>)findAll(dCriteria);
				
				for (RoutingHeader routingHeader : lstRoutingHeader) {
					if(!duplicateCheck.contains(routingHeader.getExRoutingCountryId().getCountryId())){
						duplicateCheck.add(routingHeader.getExRoutingCountryId().getCountryId());
						
						PopulateData data=new PopulateData();
						data.setPopulateId(routingHeader.getExRoutingCountryId().getCountryId());
						//String countryCode=toFetchCountryCode(routingHeader.getExRoutingCountryId().getCountryId());
						data.setPopulateCode(routingHeader.getExRoutingCountryId().getCountryCode());
						String countryName=toFtechCountryName(routingHeader.getExRoutingCountryId().getCountryId(),languaueId);
						data.setPopulateName(countryName);
						lstRouting.add(data);
						
					}
				}
			}

		}else{
			hqlQuery="select distinct a.exRoutingCountryId.countryId from  RoutingHeader a where a.exCurrenyId.currencyId = :pCurrencyId and a.exCountryId.countryId = :pCountryId and a.isActive = 'Y'";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pCurrencyId", currencyid);
			query.setParameter("pCountryId", benecountryId);
			
			List<BigDecimal> lstRoutingCountryId =query.list();
			if(lstRoutingCountryId.size()>0){
				//routingCountryId=lstRoutingCountryId.get(0);
				for (BigDecimal bigDecimal : lstRoutingCountryId) {
					PopulateData data=new PopulateData();
					data.setPopulateId(bigDecimal);
					String countryCode=toFetchCountryCode(bigDecimal);
					data.setPopulateCode(countryCode);
					String countryName=toFtechCountryName(bigDecimal,languaueId);
					data.setPopulateName(countryName);
					lstRouting.add(data);
				}
			}
		}

		

		return lstRouting;
	}

	public List<ServiceMaster> fetchServiceAllValues(BigDecimal serviceGroupId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ServiceMaster.class, "serviceMaster");

		criteria.setFetchMode("serviceMaster.serviceGroupId", FetchMode.JOIN);
		criteria.createAlias("serviceMaster.serviceGroupId", "serviceGroupId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceGroupId.serviceGroupId",serviceGroupId));
		
		criteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));

		List<ServiceMaster> lstServiceMaster = (List<ServiceMaster>) findAll(criteria);
		return lstServiceMaster;
	}

	private String toFtechCountryName(BigDecimal countryId,BigDecimal languaueId) {
		String routingCountryName=null;
		String hqlQuery="select distinct a.countryName from  CountryMasterDesc a where a.fsCountryMaster.countryId = :pcountryId and a.fsLanguageType.languageId= :pLanguageId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pcountryId", countryId);
		query.setParameter("pLanguageId", languaueId);
		List<String> lstRoutingCountryCode =query.list();
		if (lstRoutingCountryCode.size()>0) {
			routingCountryName=lstRoutingCountryCode.get(0);
		}
		return routingCountryName;
	}

	private String toFetchCountryCode(BigDecimal countryId) {
		String routingCountryCode=null;
		String hqlQuery="select distinct a.countryCode from  CountryMaster a where a.countryId = :pcountryId and a.isActive= :pIsActive";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pcountryId", countryId);
		query.setParameter("pIsActive", Constants.Yes);
		List<String> lstRoutingCountryCode =query.list();
		if (lstRoutingCountryCode.size()>0) {
			routingCountryCode=lstRoutingCountryCode.get(0);
		}
		return routingCountryCode;
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanks(BigDecimal currencyid,BigDecimal routingCountry) {
		List<PopulateData> lstRoutingBank=new ArrayList<PopulateData>();
		lstRoutingBank.clear();
		//BigDecimal routingCountryId=null;
		String hqlQuery="select distinct a.exRoutingBankId.bankId from  RoutingHeader a where a.exCurrenyId.currencyId = :pCurrencyId and a.exRoutingCountryId.countryId=:pCountryId";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("pCurrencyId", currencyid);
		query.setParameter("pCountryId", routingCountry);
		List<BigDecimal> lstRoutingCountryId =query.list();
		if(lstRoutingCountryId.size()>0){
			//routingCountryId=lstRoutingCountryId.get(0);
			for (BigDecimal bigDecimal : lstRoutingCountryId) {
				PopulateData data=new PopulateData();
				data.setPopulateId(bigDecimal);
				//String bankCode=toFetchCountryCode(bigDecimal);
				List<BankMaster> lsBankMasters=toFetchBankCodeandName(bigDecimal);
				if(lsBankMasters.size()>0){
					BankMaster bankMaster=lsBankMasters.get(0);
					data.setPopulateCode(bankMaster.getBankCode());
					data.setPopulateName(bankMaster.getBankFullName());
				}
				//data.setPopulateCode(countryCode);
				//String bankName=toFtechCountryName(bigDecimal);
				//data.setPopulateName(bankName);
				lstRoutingBank.add(data);
			}
		}
		return lstRoutingBank;
	}

	private List<BankMaster> toFetchBankCodeandName(BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankId",bankId));
		criteria.add(Restrictions.eq("bankMaster.recordStatus",Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> lstBankMaster=(List<BankMaster>)findAll(criteria);
		return lstBankMaster;
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrency(BigDecimal currencyId, BigDecimal beneficiaryCountryId,BigDecimal servicegroupId) {
		List<PopulateData> lstRoutingBank=new ArrayList<PopulateData>();
		lstRoutingBank.clear();
		
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		String hqlQuery = null;
		Query query = null;
		
		if(servicegroupId != null){
			
			//cash Product - CASH  -- // banking Channel - EFT and TT
			List<ServiceMaster> lstServiceMaster = fetchServiceAllValues(servicegroupId);
			
			if(lstServiceMaster != null && lstServiceMaster.size() != 0){
				
				DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

				dCriteria.setFetchMode("routingHeader.exCurrenyId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId", JoinType.INNER_JOIN);
				dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyId));

				dCriteria.add(Restrictions.eq("routingHeader.isActive", Constants.Yes));
				
				dCriteria.setFetchMode("routingHeader.exRoutingCountryId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exRoutingCountryId", "exRoutingCountryId", JoinType.INNER_JOIN);
				dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId", beneficiaryCountryId));

				Disjunction lstjunction = Restrictions.disjunction();
				
				dCriteria.setFetchMode("routingHeader.exServiceId",FetchMode.JOIN);
				dCriteria.createAlias("routingHeader.exServiceId", "exServiceId", JoinType.INNER_JOIN);
				for (ServiceMaster serviceMaster : lstServiceMaster) {
					lstjunction.add(Restrictions.eq("exServiceId.serviceId",serviceMaster.getServiceId()));
				}
				
				dCriteria.add(lstjunction);
				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
				
				List<RoutingHeader> lstRoutingHeader = (List<RoutingHeader>)findAll(dCriteria);
				
				for (RoutingHeader routingHeader : lstRoutingHeader) {
					if(!duplicateCheck.contains(routingHeader.getExRoutingBankId().getBankId())){
						duplicateCheck.add(routingHeader.getExRoutingBankId().getBankId());
					
						PopulateData data=new PopulateData();
						data.setPopulateId(routingHeader.getExRoutingBankId().getBankId());
						data.setPopulateCode(routingHeader.getExRoutingBankId().getBankCode());
						data.setPopulateName(routingHeader.getExRoutingBankId().getBankFullName());
						
						lstRoutingBank.add(data);
					}
				}
			}
			
		}else{
			hqlQuery="select distinct a.exRoutingBankId.bankId from  RoutingHeader a where a.exCurrenyId.currencyId = :pCurrencyId and a.isActive = :pIsActive  and a.exRoutingCountryId.countryId = :pbeneficaryCountryId";
			query = getSession().createQuery(hqlQuery);
			query.setParameter("pCurrencyId", currencyId);
			query.setParameter("pIsActive", Constants.Yes);
			query.setParameter("pbeneficaryCountryId", beneficiaryCountryId);

			List<BigDecimal> lstRoutingCountryId =query.list();
			if(lstRoutingCountryId.size()>0){
				for (BigDecimal bigDecimal : lstRoutingCountryId) {
					PopulateData data=new PopulateData();
					data.setPopulateId(bigDecimal);
					List<BankMaster> lsBankMasters=toFetchBankCodeandName(bigDecimal);
					if(lsBankMasters.size()>0){
						BankMaster bankMaster=lsBankMasters.get(0);
						data.setPopulateCode(bankMaster.getBankCode());
						data.setPopulateName(bankMaster.getBankFullName());
					}
					lstRoutingBank.add(data);
				}
			}
		}
		
		return lstRoutingBank;
	}

	@Override
	public String saveOrUpDate(BigDecimal rateOfferedPk,BigDecimal routingBankId, String specialOrCommonPoolIndicator,BigDecimal rateOffered,String userName) {
		String approveMsg=null;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		ratePlaceOrder.setIsActive(Constants.Yes);
		ratePlaceOrder.setApprovedBy(userName);
		ratePlaceOrder.setApprovedDate(new Date());
		ratePlaceOrder.setRateOffered(rateOffered);
		ratePlaceOrder.setCustomerIndicator(specialOrCommonPoolIndicator);
		ratePlaceOrder.setRoutingBankId(routingBankId);
		getSession().update(ratePlaceOrder);
		approveMsg="Success";
		return approveMsg;
	}
	@Override
	public List<ViewArea> getAreaPlace()
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewArea.class, "viewArea");

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewArea> lstViewArea=(List<ViewArea>)findAll(criteria);
		return lstViewArea;
	}

	@Override
	public Boolean toCheckStatus(BigDecimal rateOfferedPk) {
		Boolean pendingForApproval=false;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		String isActiveRecordCheck = ratePlaceOrder.getIsActive();
		if (isActiveRecordCheck.equalsIgnoreCase(Constants.Yes)) {
			pendingForApproval=true;
		}
		return pendingForApproval;
	}

	@Override
	public Boolean toCheckRateOfferedForNegotiat(BigDecimal rateOfferedPk,BigDecimal newRateOffered) {
		Boolean pendingForApproval=false;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		BigDecimal prevRateOffered = ratePlaceOrder.getRateOffered();
		if (prevRateOffered.compareTo(newRateOffered)==0) {
			pendingForApproval=false;
		}else
		{
			pendingForApproval=true;
		}
		return pendingForApproval;
	}

	public List<PopulateData> getPopulateBeneficiaryBanks(BigDecimal appCountryId,BigDecimal beneCountryId,BigDecimal serviceGroupId)
	{
		/*DetachedCriteria criteria=DetachedCriteria.forClass(PlaceOrderBeneListView.class,"placeOrderBeneListView");

		//criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryName",beneName));
		criteria.add(Restrictions.eq("placeOrderBeneListView.serviceGroupId",serviceGroupId));
		criteria.add(Restrictions.eq("placeOrderBeneListView.benificaryCountry",beneCountryId));
		//criteria.add(Restrictions.eq("placeOrderBeneListView.customerRef",customerRef));
		 * 
		 * 
		 */		

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BanksView.class, "banksView");

		dCriteria.add(Restrictions.eq("banksView.applicationCountryId", appCountryId));
		dCriteria.add(Restrictions.eq("banksView.bankCountryId", beneCountryId));
		dCriteria.add(Restrictions.eq("banksView.serviceGroupId", serviceGroupId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BanksView> bankList = (List<BanksView>) findAll(dCriteria);

		ProjectionList columns = Projections.projectionList();

		columns.add(Projections.distinct(Projections.property("bankId")), "populateId");

		//columns.add(Projections.property("bankId"), "populateId");
		columns.add(Projections.property("bankCode"),"populateCode");
		columns.add(Projections.property("bankFullName"), "populateName");


		dCriteria.setProjection(columns);
		dCriteria.setResultTransformer( new AliasToBeanResultTransformer(PopulateData.class) );

		List<PopulateData> lstPopulateData=  (List<PopulateData>) findAll(dCriteria);

		return lstPopulateData;
	}

	@Override
	public List<PopulateData> getBasedOnCountyCurrency(BigDecimal countryId ,BigDecimal currencyID) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.setFetchMode("currencyMaster.fsCountryMaster",FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyMaster.isactive", Constants.Yes));
		//dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyID));

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
	public String saveOrUpDateRatePlaceOrder(List<GSMPlaceOrderDataTable> lstSaveGSM) {
		String approveMsg=null;
		try{
			for (GSMPlaceOrderDataTable gsmPlaceOrderDataTable : lstSaveGSM) {
				RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, gsmPlaceOrderDataTable.getRateOfferedPk());
				ratePlaceOrder.setIsActive(Constants.Yes);
				ratePlaceOrder.setApprovedBy(session.getUserName());
				ratePlaceOrder.setApprovedDate(new Date());
				ratePlaceOrder.setRateOffered(gsmPlaceOrderDataTable.getRateOffered());
				ratePlaceOrder.setCustomerIndicator(gsmPlaceOrderDataTable.getSpecialOrCommonPoolIndicator());
				ratePlaceOrder.setRoutingBankId(gsmPlaceOrderDataTable.getRoutingBankId());
				ratePlaceOrder.setRoutingCountryId(gsmPlaceOrderDataTable.getRoutingCountry());
				getSession().update(ratePlaceOrder);
			}
			approveMsg="Success";
		}catch(Exception e){
			System.out.println(e.getMessage());
		}

		return approveMsg;
	}

	@Override
	public HashMap<String, String> fetchActualRate(HashMap<String, Object> fetchRate) throws AMGException {
		
		/*BigDecimal exchangeRateDt = null;

		BigDecimal appCountryId = (BigDecimal) fetchRate.get("APPLICATION_COUNTRY_ID");
		BigDecimal routingCountryId = (BigDecimal) fetchRate.get("COUNTRY_ID");
		BigDecimal currencyId = (BigDecimal) fetchRate.get("CURRENCY_ID");
		BigDecimal countryBranchId = (BigDecimal) fetchRate.get("COUNTRY_BRANCH_ID");
		BigDecimal routingBankId = (BigDecimal) fetchRate.get("BANK_ID");
		BigDecimal serviceMasterId = (BigDecimal) fetchRate.get("SERVICE_INDICATOR_ID");

		DetachedCriteria criteria = DetachedCriteria.forClass(ExchangeRateApprovalDetModel.class, "exchangeRateApprovalDetModel");
		//app country Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.applicationCountryId",appCountryId));
		//Routing Country Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryId",routingCountryId));
		//currency Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.currencyId",currencyId));
		//Country Branch Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryBranchId",countryBranchId));
		//Routing Bank Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.bankId",routingBankId));
		//Service Master Id
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.serviceId",serviceMasterId));
		//IsActive check
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.isActive",Constants.Yes));
		
		List<ExchangeRateApprovalDetModel> lstExchangeRateApprovalDetModel=(List<ExchangeRateApprovalDetModel>)findAll(criteria);
		
		if(lstExchangeRateApprovalDetModel != null && lstExchangeRateApprovalDetModel.size() != 0){
			ExchangeRateApprovalDetModel exchangeRate  = lstExchangeRateApprovalDetModel.get(0);
			exchangeRateDt = exchangeRate.getSellRateMax();
		}
		
		return exchangeRateDt;*/

		LOGGER.info("Entered into getRemitExchangeEquivalentAount(HashMap<String, String> fetchRate) Method");
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("PROBLEM OCCURED WHILE GETTTING CONECTION");
			e.printStackTrace();
		}
		LOGGER.info("!!!!!! getRemitExchangeEquivalentAount IN PUT VALUES  !!!!!!!!! ==  " + fetchRate.toString());
		LOGGER.info("!Calling  EX_P_GET_REMIT_EQUIV_AM Procedure with INPUT VALUES  !!!!!");
		LOGGER.info(fetchRate.toString());
		
		BigDecimal appCountryId = (BigDecimal) fetchRate.get("APPLICATION_COUNTRY_ID");
		BigDecimal routingCountryId = (BigDecimal) fetchRate.get("COUNTRY_ID");
		BigDecimal currencyId = (BigDecimal) fetchRate.get("CURRENCY_ID");
		BigDecimal countryBranchId = (BigDecimal) fetchRate.get("COUNTRY_BRANCH_ID");
		BigDecimal routingBankId = (BigDecimal) fetchRate.get("BANK_ID");
		BigDecimal serviceMasterId = (BigDecimal) fetchRate.get("SERVICE_MASTER_ID");
		BigDecimal remittanceMasterId = null;
		BigDecimal deliveryMasterId = null;
		BigDecimal foreignCurrencyId = (BigDecimal) fetchRate.get("P_FOREIGN_CURRENCY_ID");
		BigDecimal selectedCurrencyId = (BigDecimal) fetchRate.get("P_SELECTED_CURRENCY_ID");
		String customerType = (String) fetchRate.get("P_CUSTOMER_TYPE");
		BigDecimal selectedCurrencyAmount = (BigDecimal) fetchRate.get("P_SELECTED_CURRENCY_AMOUNT");
		
		CallableStatement cs;
		try {
			String call = " { call EX_P_GET_REMIT_EQUIV_AMT_RATE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, appCountryId);
			cs.setBigDecimal(2, routingCountryId);
			cs.setBigDecimal(3, countryBranchId);
			cs.setBigDecimal(4, session.getCompanyId());
			cs.setBigDecimal(5, routingBankId);
			cs.setBigDecimal(6, serviceMasterId);
			cs.setBigDecimal(7, deliveryMasterId);
			cs.setBigDecimal(8, remittanceMasterId);
			cs.setBigDecimal(9, foreignCurrencyId);
			cs.setBigDecimal(10, selectedCurrencyId);
			cs.setString(11, customerType);
			cs.setBigDecimal(12, selectedCurrencyAmount);
			cs.registerOutParameter(13, java.sql.Types.INTEGER);
			cs.registerOutParameter(14, java.sql.Types.INTEGER);
			cs.registerOutParameter(15, java.sql.Types.INTEGER);
			cs.registerOutParameter(16, java.sql.Types.VARCHAR);
			cs.execute();
			outputValues.put("P_EXCHANGE_RATE", cs.getBigDecimal(13) == null ? "0" : cs.getBigDecimal(13).toPlainString());
			outputValues.put("P_EQUIV_CURRENCY_ID", cs.getBigDecimal(14) == null ? "0" : cs.getBigDecimal(14).toPlainString());
			outputValues.put("P_EQUIV_GROSS_AMOUNT", cs.getBigDecimal(15) == null ? "0" : cs.getBigDecimal(15).toPlainString());
			outputValues.put("P_ERROR_MESSAGE", cs.getString(16) == null ? "" : cs.getString(16));
			System.out.println("!!!!!! getRemitExchangeEquivalentAount outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
			LOGGER.info("!After Calling  EX_P_GET_REMIT_EQUIV_AM Procedure returns OUTPUT VALUES  !!!!!");
			LOGGER.info(outputValues.toString());
		} catch (SQLException e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			LOGGER.error("Error While Procedure Calling " + e.getMessage());
			String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.error("Error While Procedure Calling " + e.getMessage());
				String erromsg = "EX_P_GET_REMIT_EQUIV_AM" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		
		return outputValues;
	
	}

	@Override
	public List<PopulateData> toFtechAllRoutingBanksBasedOncurrencyServiceId(
			BigDecimal currencyId, BigDecimal beneficiaryCountryid,
			BigDecimal serviceMasterId) {
		
		List<PopulateData> lstRoutingBank = new ArrayList<PopulateData>();
		lstRoutingBank.clear();
		
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCurrenyId",FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyId));

		dCriteria.add(Restrictions.eq("routingHeader.isActive", Constants.Yes));
		
		dCriteria.setFetchMode("routingHeader.exRoutingCountryId",FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingCountryId", "exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId", beneficiaryCountryid));

		dCriteria.setFetchMode("routingHeader.exServiceId",FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId",serviceMasterId));
				
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<RoutingHeader> lstRoutingHeader = (List<RoutingHeader>)findAll(dCriteria);
		
		for (RoutingHeader routingHeader : lstRoutingHeader) {
			if(!duplicateCheck.contains(routingHeader.getExRoutingBankId().getBankId())){
				duplicateCheck.add(routingHeader.getExRoutingBankId().getBankId());
			
				PopulateData data=new PopulateData();
				data.setPopulateId(routingHeader.getExRoutingBankId().getBankId());
				data.setPopulateCode(routingHeader.getExRoutingBankId().getBankCode());
				data.setPopulateName(routingHeader.getExRoutingBankId().getBankFullName());
				
				lstRoutingBank.add(data);
			}
		}
		
		return lstRoutingBank;
	}
}
