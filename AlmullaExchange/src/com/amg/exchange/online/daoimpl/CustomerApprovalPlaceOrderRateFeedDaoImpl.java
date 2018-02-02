package com.amg.exchange.online.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.dao.ICustomerApprovalPlaceOrderRateFeedDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class CustomerApprovalPlaceOrderRateFeedDaoImpl extends CommonDaoImpl implements ICustomerApprovalPlaceOrderRateFeedDao {

	@Override
	public List<RatePlaceOrder> toFetchRecordsBasedOnCustomerId(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.customerRateAcceptance"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.eq("ratePlaceOrder.documentNumber",documentNumber));
		criteria.add(Restrictions.eq("ratePlaceOrder.documentFinanceYear",documentFinanceYear));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public String toFetchAccountNumber(BigDecimal beneficiaryAccountSeqId) {
		String beneAccountNum=null;
		String hqlQuery="select distinct a.bankAccountNumber from  PlaceOrderBeneListView a where a.beneficiaryAccountSeqId = :beneficiaryAccountSeqId";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("beneficiaryAccountSeqId", beneficiaryAccountSeqId);
        List<String> lstbeneAccountNum =query.list();
        if(lstbeneAccountNum.size()>0){
        	beneAccountNum=lstbeneAccountNum.get(0);
        }
		return beneAccountNum;
	}

	@Override
	public void toDeActivateRecord(BigDecimal rateOfferedPk, String userName) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		//ratePlaceOrder.setIsActive(Constants.D);
		ratePlaceOrder.setCustomerRateAcceptance("N");
		ratePlaceOrder.setApprovedBy(null);
		ratePlaceOrder.setApprovedDate(null);
		ratePlaceOrder.setModifiedBy(userName);
		ratePlaceOrder.setModifiedDate(new Date());
		getSession().update(ratePlaceOrder);
	}

	@Override
	public void toActivateRecord(BigDecimal rateOfferedPk,BigDecimal beneficiaryMasterId,BigDecimal accountSeqId,String accountNumber, String userName) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		ratePlaceOrder.setIsActive(Constants.Yes);
		ratePlaceOrder.setCustomerRateAcceptance(Constants.Yes);
		if(beneficiaryMasterId != null){
		BeneficaryMaster beneficaryMaster=new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(beneficiaryMasterId);
		ratePlaceOrder.setBeneficiaryMasterId(beneficaryMaster);
		}
		
		if (accountSeqId != null) {
			BeneficaryAccount beneficaryAccount=new BeneficaryAccount();
			beneficaryAccount.setBeneficaryAccountSeqId(accountSeqId);
			ratePlaceOrder.setAccountSeqquenceId(beneficaryAccount);
			ratePlaceOrder.setBeneficiaryAccountNo(accountNumber);
		}
		//ratePlaceOrder.setApprovedBy(null);
		//ratePlaceOrder.setApprovedDate(null);
		ratePlaceOrder.setModifiedBy(userName);
		ratePlaceOrder.setModifiedDate(new Date());
		getSession().update(ratePlaceOrder);
	}

	@Override
	public String toFetchEmailForBranchMgr(BigDecimal countryBranchId) {
		String emailIdForMgr=null;
		String hqlQuery="select distinct a.emailId from  CountryBranch a where a.countryBranchId = :countryBranchId";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("countryBranchId", countryBranchId);
        List<String> lstbeneAccountNum =query.list();
        if(lstbeneAccountNum.size()>0){
        	emailIdForMgr=lstbeneAccountNum.get(0);
        }
		return emailIdForMgr;
	}

	@Override
	public void toActivateRecordForBranchSupport(BigDecimal rateOfferedPk,BigDecimal countryBranchId,String userName) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, rateOfferedPk);
		ratePlaceOrder.setIsActive(Constants.Yes);
		ratePlaceOrder.setCustomerRateAcceptance(Constants.Yes);
		ratePlaceOrder.setBranchSupportIndicator(Constants.Yes);
		ratePlaceOrder.setSupportBranchId(countryBranchId);
		ratePlaceOrder.setModifiedBy(userName);
		ratePlaceOrder.setModifiedDate(new Date());
		getSession().update(ratePlaceOrder);
		
	}

	@Override
	public List<RatePlaceOrder> fetchAllRecrdsforUnApprovedFromCustomer(BigDecimal customerId,BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.eq("ratePlaceOrder.countryBranchId",branchId));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		criteria.add(Restrictions.isNull("ratePlaceOrder.customerRateAcceptance"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public List<RatePlaceOrder> toFetchRecordsUnApprovesgsm(BigDecimal customerId, BigDecimal documentNumber,BigDecimal documentFinanceYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.setFetchMode("ratePlaceOrder.accountSeqquenceId", FetchMode.JOIN);
		criteria.createAlias("ratePlaceOrder.accountSeqquenceId", "accountSeqquenceId",
				JoinType.INNER_JOIN);
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.U));
		criteria.add(Restrictions.isNull("ratePlaceOrder.customerRateAcceptance"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		criteria.add(Restrictions.eq("ratePlaceOrder.documentNumber",documentNumber));
		criteria.add(Restrictions.eq("ratePlaceOrder.documentFinanceYear",documentFinanceYear));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public BigDecimal toFetchBeneMasterId(String beneficiaryName,BigDecimal countryId) {
		BigDecimal beneMasterID=null;
		String hqlQuery="select distinct a.beneficaryMasterSeqId from  BeneficaryMaster a where a.beneficiaryAccountSeqId = :beneficiaryAccountSeqId";
        Query query = getSession().createQuery(hqlQuery);
      //  query.setParameter("beneficiaryAccountSeqId", beneficiaryAccountSeqId);
        List<BigDecimal> lstbeneMasterID =query.list();
        if(lstbeneMasterID.size()>0){
        	beneMasterID=lstbeneMasterID.get(0);
        }
		return beneMasterID;
	}
	
	@Override
	public BigDecimal getBeneficiarList(String beneficiaryName,BigDecimal countryId, BigDecimal remitType, BigDecimal customerRef,BigDecimal customerId) {
		
		BigDecimal beneMasterID = null;
		String hqlQuery="select distinct a.beneficaryMasterSeqId  from  PlaceOrderBeneListView a where a.customerId =  :customerIdNumber  and a.benificaryCountry = :pBenificaryCountry and a.serviceGroupId = :pServiceGroupId and a.customerRef= :pcustomerRef and a.benificaryName = :pBeneName";
		Query query = getSession().createQuery(hqlQuery);
		query.setParameter("customerIdNumber", customerId);
		query.setParameter("pBenificaryCountry", countryId);
		query.setParameter("pServiceGroupId", remitType);
		query.setParameter("pcustomerRef", customerRef);
		query.setParameter("pBeneName", beneficiaryName);
		List<BigDecimal> lstbeneName =query.list();
		
		if(lstbeneName.size()>0)
		{
			beneMasterID=lstbeneName.get(0);
			
		}
		
		return beneMasterID;
	}

	

}
