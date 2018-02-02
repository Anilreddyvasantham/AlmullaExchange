package com.amg.exchange.online.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.dao.IBranchStaffGSMRateDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.RatePlaceOrderAddlData;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class BranchStaffGSMRateDaoImpl extends CommonDaoImpl implements IBranchStaffGSMRateDao{

	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal branchId) {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		//criteria.add(Restrictions.eq("ratePlaceOrder.branchSupportIndicator",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",branchId));
		criteria.add(Restrictions.isNull("ratePlaceOrder.branchSupportIndicator"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.appointmentTime"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.supportBranchId"));
		criteria.add(Restrictions.eq("ratePlaceOrder.createdDate",new Date()));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);*/
		
		String hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
				+ " and ratePlaceOrder.isActive = :pisActive "
				//+ " and ratePlaceOrder.countryBranchId = :pcountryBranchId"
				+ " and ratePlaceOrder.branchSupportIndicator is null"
				+ " and ratePlaceOrder.applDocumentNumber is null" 
				+ " and ratePlaceOrder.applDocumentFinanceYear is null"
				+ " and ratePlaceOrder.appointmentTime is null"
				+ " and ratePlaceOrder.supportBranchId is null"
				+ " and ratePlaceOrder.approvedBy is not null"
				+ " and ratePlaceOrder.approvedDate is not null";
		Query query = getSession().createQuery(hql); 

		query.setParameter("pisActive", Constants.Yes); 
		//query.setParameter("pcountryBranchId", branchId);
		//query.setParameter("pisActive1", Constants.U); 
		List<RatePlaceOrder> lstRatePlaceOrders = query.list();
		
		return lstRatePlaceOrders;
	}

	@Override
	public List<RatePlaceOrder> tofetchCustomerPlaceOrderRecords(
			BigDecimal customerId, BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		//criteria.add(Restrictions.eq("ratePlaceOrder.branchSupportIndicator",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",branchId));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.isNull("ratePlaceOrder.branchSupportIndicator"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.appointmentTime"));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public void toRejectRateForCustomer(BigDecimal rateOfferedPk,String userName) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		ratePlaceOrder.setIsActive(Constants.D);
		ratePlaceOrder.setApprovedBy(null);
		ratePlaceOrder.setApprovedDate(null);
		ratePlaceOrder.setModifiedBy(userName);
		ratePlaceOrder.setModifiedDate(new Date());
		getSession().update(ratePlaceOrder);
	}

	@Override
	public String checkPlaceOrderStatusForAccept(BigDecimal rateOfferedPk) {
		
		String rtnValue=null;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		
		String isActive=ratePlaceOrder.getIsActive();
		BigDecimal applDocNo=ratePlaceOrder.getApplDocumentNumber();
		BigDecimal applFinYear=ratePlaceOrder.getApplDocumentFinanceYear();
		if(isActive.equalsIgnoreCase(Constants.D))
		{
			rtnValue=Constants.D;
			return rtnValue;
		}
		
		if(isActive.equalsIgnoreCase(Constants.Yes) && applDocNo==null &&  applFinYear==null)
		{
			rtnValue=Constants.Yes;
		}else if(isActive.equalsIgnoreCase(Constants.Yes) && applDocNo!=null &&  applFinYear!=null)
		{
			rtnValue=Constants.No;
		}
		
		return rtnValue;
	}
	@Override
	public String checkPlaceOrderStatusForReject(BigDecimal rateOfferedPk) {
		
		String rtnValue=null;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		
		String isActive=ratePlaceOrder.getIsActive();
		BigDecimal applDocNo=ratePlaceOrder.getApplDocumentNumber();
		BigDecimal applFinYear=ratePlaceOrder.getApplDocumentFinanceYear();
		if(isActive.equalsIgnoreCase(Constants.D))
		{
			rtnValue=Constants.D;
			return rtnValue;
		}
		
		if(isActive.equalsIgnoreCase(Constants.Yes) && applDocNo==null &&  applFinYear==null)
		{
			rtnValue=Constants.Yes;
		}else if(isActive.equalsIgnoreCase(Constants.Yes) && applDocNo!=null &&  applFinYear!=null)
		{
			rtnValue=Constants.No;
		}
		
		return rtnValue;
	}
	
	@Override
	public void createPlaceOrderForNegotiate(BigDecimal rateOfferedPk,String userName,BigDecimal documentNumber)
	{
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		ratePlaceOrder.setIsActive(Constants.U);
		ratePlaceOrder.setApprovedBy(null);
		ratePlaceOrder.setApprovedDate(null);
		ratePlaceOrder.setModifiedBy(userName);
		ratePlaceOrder.setModifiedDate(new Date());
		//ratePlaceOrder.setDocumentNumber(documentNumber);
		ratePlaceOrder.setNegotiateSts(Constants.Yes);
		getSession().update(ratePlaceOrder);
		
		/*RatePlaceOrder ratePlaceOrderObj = new RatePlaceOrder();

		ratePlaceOrderObj.setBeneficiaryBankId(ratePlaceOrder.getBeneficiaryBankId());//getBeneficiaryBankId()
		ratePlaceOrderObj.setCustomerId(ratePlaceOrder.getCustomerId());//getCustomerId()
		ratePlaceOrderObj.setCreatedBy(userName);//session.getUserName()
		ratePlaceOrderObj.setCreatedDate(new Date());
		ratePlaceOrderObj.setBeneficiaryCountryId(ratePlaceOrder.getBeneficiaryCountryId());//getBeneficiaryCountryId()
		ratePlaceOrderObj.setBeneficiaryAccountNo(ratePlaceOrder.getBeneficiaryAccountNo());//getAccountNumber()
		ratePlaceOrderObj.setRemitType(ratePlaceOrder.getRemitType());//getServiceGroupId()
		ratePlaceOrderObj.setCustomerEmail(ratePlaceOrder.getCustomerEmail());
		ratePlaceOrderObj.setTransactionAmount(ratePlaceOrder.getTransactionAmount());//getFcOrLocalAmount() 
		ratePlaceOrderObj.setIsActive(Constants.U);
		ratePlaceOrderObj.setRequestCurrencyId(ratePlaceOrder.getRequestCurrencyId());

		if(ratePlaceOrder.getAccountSeqquenceId()!=null){
			BeneficaryAccount beneAccountObj=new BeneficaryAccount();
			beneAccountObj.setBeneficaryAccountSeqId(ratePlaceOrder.getAccountSeqquenceId().getBeneficaryAccountSeqId());
			ratePlaceOrderObj.setAccountSeqquenceId(beneAccountObj );
		}
		if(ratePlaceOrder.getBeneficiaryMasterId()!=null)
		{
			BeneficaryMaster beneMasterObj=new BeneficaryMaster();
			beneMasterObj.setBeneficaryMasterSeqId(ratePlaceOrder.getBeneficiaryMasterId().getBeneficaryMasterSeqId());
			ratePlaceOrderObj.setBeneficiaryMasterId(beneMasterObj);
		}
		if(ratePlaceOrder.getDestinationCurrenyId()!=null)
		{
			ratePlaceOrderObj.setDestinationCurrenyId(ratePlaceOrder.getDestinationCurrenyId());
		}

		ratePlaceOrderObj.setCountryBranchId(ratePlaceOrder.getCountryBranchId());
		
		ratePlaceOrderObj.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER));
		ratePlaceOrderObj.setDocumentId(ratePlaceOrder.getDocumentId());
		ratePlaceOrderObj.setDocumentNumber(documentNumber);
		ratePlaceOrderObj.setDocumentFinanceYear(ratePlaceOrder.getDocumentFinanceYear());
		ratePlaceOrderObj.setCompanyId(ratePlaceOrder.getCompanyId());
		ratePlaceOrderObj.setApplicationCountryId(ratePlaceOrder.getApplicationCountryId());
		ratePlaceOrderObj.setCustomerreference(ratePlaceOrder.getCustomerreference());
		ratePlaceOrderObj.setValueDate(ratePlaceOrder.getValueDate());
		ratePlaceOrderObj.setAreaName(ratePlaceOrder.getAreaName());
		ratePlaceOrderObj.setNegotiateSts(Constants.Yes);
		ratePlaceOrderObj.setRateOffered(ratePlaceOrder.getRateOffered());
		getSession().saveOrUpdate(ratePlaceOrderObj);*/
		
	}

	@Override
	public String saveOrUpdatePlaceOrderAddlData(
			HashMap<String, Object> inputValues) throws Exception {
		String approvalMsg=null;
		BigDecimal placeOrderPk=(BigDecimal) inputValues.get("PLACE_ORDER_PK");
		Date visitTime= (Date) inputValues.get("CUSTOMER_VISIT_TIME");
		
		BigDecimal sourceId=(BigDecimal) inputValues.get("SOURCE_OF_INCOME");
		String paymentMode= (String) inputValues.get("PAYMENT_MODE");
		String userName = (String) inputValues.get("USER_NAME");
		
		BigDecimal dataSericeId= (BigDecimal)inputValues.get("DATA_SERVICE_ID");
		BigDecimal routingCountryId=(BigDecimal) inputValues.get("ROUTING_COUNTRY_ID");
		BigDecimal remitMode=(BigDecimal) inputValues.get("REMIT_MODE");
		BigDecimal deliverMode=(BigDecimal)inputValues.get("DELIVERY_MODE");
		BigDecimal rountingBranch=(BigDecimal)inputValues.get("ROUTING_BRANCH");
		BigDecimal beneMasterSeqid=(BigDecimal)inputValues.get("BENEFICIARY_MASTER_ID");
		BigDecimal beneAccSeqId=(BigDecimal)inputValues.get("BENEFICIARY_ACC_SEQ_ID");
		String beneAccno=(String)inputValues.get("BENEFICIARY_ACC_NO");
		BigDecimal routingBankId=(BigDecimal)inputValues.get("ROUTING_BANK_ID");
		
		
		List<RatePlaceOrderAddlData>  lstRatePlaceOrderAddlData= (List<RatePlaceOrderAddlData>) inputValues.get("PLACE_ORDER_ADDL_DATA");
		
		
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
		BigDecimal applDocNumder=ratePlaceOrder.getApplDocumentNumber();
		if(applDocNumder == null){
		//ratePlaceOrder.setApprovedBy(userName);
		//ratePlaceOrder.setApprovedDate(new Date());
		ratePlaceOrder.setAppointmentTime(visitTime);
		ratePlaceOrder.setSourceOfincomeId(sourceId);
		ratePlaceOrder.setCollectionMode(paymentMode);
		
		
		/*ratePlaceOrder.setApprovedBy(userName);
		ratePlaceOrder.setApprovedDate(new Date());*/
		
		ratePlaceOrder.setServiceMasterId(dataSericeId);
		ratePlaceOrder.setRemittanceModeId(remitMode);
		ratePlaceOrder.setRoutingCountryId(routingCountryId);
		
		ratePlaceOrder.setDeliveryModeId(deliverMode);
		ratePlaceOrder.setRoutingBranchId(rountingBranch);
		
		if(beneAccSeqId!=null){
			BeneficaryAccount beneAccountObj=new BeneficaryAccount();
			beneAccountObj.setBeneficaryAccountSeqId(beneAccSeqId);
			ratePlaceOrder.setAccountSeqquenceId(beneAccountObj );
		}
		if(beneMasterSeqid!=null)
		{
			BeneficaryMaster beneMasterObj=new BeneficaryMaster();
			beneMasterObj.setBeneficaryMasterSeqId(beneMasterSeqid);
			ratePlaceOrder.setBeneficiaryMasterId(beneMasterObj);
		}
		ratePlaceOrder.setBeneficiaryAccountNo(beneAccno);
		ratePlaceOrder.setRoutingBankId(routingBankId);
	/*	ratePlaceOrder.setRoutingBranchId(rountingBranch);
		ratePlaceOrder.setRoutingBranchId(rountingBranch);*/
		
		getSession().update(ratePlaceOrder);
		
		for(RatePlaceOrderAddlData ratePlaceOrderAddlData:lstRatePlaceOrderAddlData)
		{
			getSession().saveOrUpdate(ratePlaceOrderAddlData);
		}
		approvalMsg="Success";
		}else{
			approvalMsg="Fail";
		}
		return approvalMsg;
		
		
	}

	@Override
	public List<RatePlaceOrder> toFetchAllBranchStaffGsmBasedOnCustId(BigDecimal branchId, BigDecimal customerId) {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		//criteria.add(Restrictions.eq("ratePlaceOrder.branchSupportIndicator",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",branchId));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.isNull("ratePlaceOrder.branchSupportIndicator"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.appointmentTime"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.supportBranchId"));
		criteria.add(Restrictions.eq("ratePlaceOrder.createdDate",new Date()));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);*/
		
		String hql = "from RatePlaceOrder as ratePlaceOrder where trunc(ratePlaceOrder.valueDate) = trunc(sysdate) "
				+ " and ratePlaceOrder.customerId = :customerId"
				+ " and ratePlaceOrder.isActive = :pisActive "
				//+ " and ratePlaceOrder.countryBranchId = :pcountryBranchId"
				+ " and ratePlaceOrder.branchSupportIndicator is null"
				+ " and ratePlaceOrder.applDocumentNumber is null" 
				+ " and ratePlaceOrder.applDocumentFinanceYear is null"
				+ " and ratePlaceOrder.appointmentTime is null"
				+ " and ratePlaceOrder.supportBranchId is null"
				+ " and ratePlaceOrder.approvedBy is not null"
				+ " and ratePlaceOrder.approvedDate is not null";
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("pisActive", Constants.Yes); 
		//query.setParameter("pcountryBranchId", branchId);
		//query.setParameter("pisActive1", Constants.U); 
		List<RatePlaceOrder> lstRatePlaceOrders = query.list();
		
		//List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public String checkPlaceOrderStatusForNegotiate(BigDecimal rateOfferedPk) {
		String rtnValue=null;
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		
		String isActive=ratePlaceOrder.getIsActive();
		String negotiate=ratePlaceOrder.getNegotiateSts();
		if(isActive.equalsIgnoreCase(Constants.D) && negotiate.equalsIgnoreCase(Constants.No))
		{
			rtnValue=Constants.D;
			return rtnValue;
		}
		
		
		return rtnValue;
	}
	
	@Override
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId,BigDecimal serviceGroupId,BigDecimal customerRef,BigDecimal beneBankId) {
		List<PopulateData> lstPopulateData=  new ArrayList<PopulateData>();
		
		String hqlQuery="select distinct a.benificaryName from  PlaceOrderBeneListView a where a.customerId =  :customerIdNumber  and a.benificaryCountry = :pBenificaryCountry and a.serviceGroupId = :pServiceGroupId and a.customerRef= :pcustomerRef  and a.bankId = :pbankId ";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerIdNumber", customerId);
		query.setParameter("pBenificaryCountry", beneCountryId);
		query.setParameter("pServiceGroupId", serviceGroupId);
		query.setParameter("pcustomerRef", customerRef);
		query.setParameter("pbankId", beneBankId);
		
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
}
