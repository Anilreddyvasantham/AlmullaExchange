package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.dao.IBankServiceRuleMasterDao;
import com.amg.exchange.remittance.model.BankCharges;
import com.amg.exchange.remittance.model.BankChargesMasterLog;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.TransferMode;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class BankServiceRuleMasterDaoImpl extends CommonDaoImpl implements IBankServiceRuleMasterDao{

	public void saveBankRule(BankServiceRule bankServiceRule) {
		getSession().saveOrUpdate(bankServiceRule);
	}


	public void saveBankCharges(BankCharges bankCharges) {
		getSession().saveOrUpdate(bankCharges);

	}


	@Override
	public List<CurrencyMaster> checkCurrencyCodeExist(String currencyCode,BigDecimal countryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");


		criteria.add(Restrictions.eq("currencyMaster.currencyCode", currencyCode));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyMaster>) findAll(criteria);
	}


	@Override
	public List<RemittanceModeDescription> getRemittance(BigDecimal appCountryID,BigDecimal countryId,BigDecimal languageId) {

		List<RemittanceModeDescription> lstRemittanceModeDescription = new ArrayList<RemittanceModeDescription>();

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");

		criteria.add(Restrictions.eq("beneCountryService.isActive", Constants.Yes));

		criteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryID));


		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("beneCountryId.countryId", countryId));



		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);



		List<BeneCountryService> lstBeneCountryService= (List<BeneCountryService>) findAll(criteria);

		if(lstBeneCountryService.size()>0)
		{

			HashSet<BigDecimal>  setRemittId= new HashSet<BigDecimal>();

			for(BeneCountryService beneCountryService :lstBeneCountryService)
			{

				BigDecimal remitanceId=beneCountryService.getRemitanceId().getRemittanceModeId();

				setRemittId.add(remitanceId);

				//lstRemittanceModeDescription=(List<RemittanceModeDescription>)findAll(dCriteria);

			}

			for(BigDecimal remitanceId:setRemittId)
			{

				DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDesc");

				dCriteria.setFetchMode("remittanceModeDesc.remittanceModeMaster", FetchMode.JOIN);
				dCriteria.createAlias("remittanceModeDesc.remittanceModeMaster", "remittanceModeMaster",  JoinType.INNER_JOIN);

				dCriteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId", remitanceId));

				dCriteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.Yes));


				dCriteria.add(Restrictions.eq("remittanceModeDesc.languageType.languageId", languageId));

				// start by subramanian for asc remittance description
				dCriteria.addOrder(Order.asc("remittanceModeDesc.remittanceDescription"));
				//end by subramanian for asc remittance description

				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

				List<RemittanceModeDescription> remittanceDescList=(List<RemittanceModeDescription>)findAll(dCriteria); 

				//	RemittanceModeDescription remittanceModeDescription=(RemittanceModeDescription)dCriteria.getExecutableCriteria(getSession()).uniqueResult();
				if(remittanceDescList.size()>0)
				{
					for(RemittanceModeDescription remittanceModeDescription:remittanceDescList){
						lstRemittanceModeDescription.add(remittanceModeDescription);
					}

				}

			}

		}

		return lstRemittanceModeDescription;

	}


	@Override
	public List<DeliveryModeDesc> getDeliverFromRemittance(BigDecimal appCountryID, BigDecimal countryId,BigDecimal remittanceId,BigDecimal languageId) {



		List<DeliveryModeDesc> lstDeliveryModeDesc = new ArrayList<DeliveryModeDesc>();

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");

		criteria.add(Restrictions.eq("beneCountryService.isActive", Constants.Yes));

		criteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryID));


		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("beneCountryId.countryId", countryId));


		criteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.remitanceId", "remitanceId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remitanceId.remittanceModeId", remittanceId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BeneCountryService> lstBeneCountryService= (List<BeneCountryService>) findAll(criteria);

		if(lstBeneCountryService.size()>0)
		{
			HashSet<BigDecimal>  setDeliverId= new HashSet<BigDecimal>();

			for(BeneCountryService beneCountryService :lstBeneCountryService)
			{

				BigDecimal deliverModeId=beneCountryService.getDeliveryId().getDeliveryModeId();

				setDeliverId.add(deliverModeId);



			}
			for(BigDecimal deliverModeId:setDeliverId)
			{
				DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

				dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
				dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",  JoinType.INNER_JOIN);

				dCriteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));


				dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliverModeId));

				dCriteria.add(Restrictions.eq("deliveryModeDesc.languageType.languageId", languageId));

				dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));

				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

				List<DeliveryModeDesc> deliveryModeDescList=(List<DeliveryModeDesc>)findAll(dCriteria); 

				//	DeliveryModeDesc deliveryModeDesc=(DeliveryModeDesc)dCriteria.getExecutableCriteria(getSession()).uniqueResult();
				if(deliveryModeDescList.size()>0)
				{
					for(DeliveryModeDesc deliveryModeDesc:deliveryModeDescList){
						lstDeliveryModeDesc.add(deliveryModeDesc);
					}

				}
			}
		}




		return lstDeliveryModeDesc;
	}


	@Override
	public List<BankServiceRule> getBankServiceRule(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal remittanceId,BigDecimal deliverId,String isActive) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankServiceRule.class, "bankServiceRule");

		criteria.setFetchMode("bankServiceRule.countryId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.countryId", "countryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("countryId.countryId", countryId));


		criteria.setFetchMode("bankServiceRule.currencyId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.currencyId", "currencyId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));

		criteria.setFetchMode("bankServiceRule.bankId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.bankId", "bankId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankId.bankId", bankId));

		criteria.setFetchMode("bankServiceRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.remittanceModeId", "remittanceModeId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceId));

		criteria.setFetchMode("bankServiceRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.deliveryModeId", "deliveryModeId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliverId));
		
		if(isActive != null){
			criteria.add(Restrictions.eq("bankServiceRule.isActive", isActive));
		}


		return (List<BankServiceRule>) findAll(criteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<TransferMode> getTransferMode() {
		DetachedCriteria criteria = DetachedCriteria.forClass(TransferMode.class, "transferMode");

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TransferMode> transfermodelst = (List<TransferMode>) findAll(criteria);

		return transfermodelst;
	}


	@Override
	public List<BankCharges> getBankCharges(BigDecimal bankServiceRuleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankCharges.class, "bankCharges");


		criteria.setFetchMode("bankCharges.bankServiceRuleId", FetchMode.JOIN);
		criteria.createAlias("bankCharges.bankServiceRuleId", "bankServiceRuleId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankServiceRuleId.bankServiceRuleId", bankServiceRuleId));

		criteria.add(Restrictions.ne("bankCharges.isActive", Constants.U));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankCharges>) findAll(criteria);
	}


	@Override
	public List<BankCharges> getBankChargesApprove(BigDecimal bankServiceRuleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankCharges.class, "bankCharges");


		criteria.setFetchMode("bankCharges.bankServiceRuleId", FetchMode.JOIN);
		criteria.createAlias("bankCharges.bankServiceRuleId", "bankServiceRuleId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankServiceRuleId.bankServiceRuleId", bankServiceRuleId));

		criteria.add(Restrictions.eq("bankCharges.isActive", Constants.U));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankCharges>) findAll(criteria);
	}


	@Override
	public List<BankCharges> getBankChargesForAllRecords(
			BigDecimal bankServiceRuleId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankCharges.class, "bankCharges");


		criteria.setFetchMode("bankCharges.bankServiceRuleId", FetchMode.JOIN);
		criteria.createAlias("bankCharges.bankServiceRuleId", "bankServiceRuleId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankServiceRuleId.bankServiceRuleId", bankServiceRuleId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankCharges>) findAll(criteria);
	}


	@Override
	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove) {

		for(BigDecimal bnkChrsId:lstBnkCrgsApprove)
		{
			DetachedCriteria criteria = DetachedCriteria.forClass(BankCharges.class, "bankCharges");
			criteria.add(Restrictions.eq("bankCharges.bankChargeId", bnkChrsId));
			List<BankCharges> lstBankCharges=(List<BankCharges>) findAll(criteria);

			if(lstBankCharges.size()>0)
			{
				BankCharges bankCharges=lstBankCharges.get(0);
				bankCharges.setIsActive(Constants.Yes);
				getSession().saveOrUpdate(bankCharges);
			}
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<BankCharges> getBankChargesEnry(BigDecimal bankServiceRuleId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankCharges.class, "bankCharges");


		criteria.setFetchMode("bankCharges.bankServiceRuleId", FetchMode.JOIN);
		criteria.createAlias("bankCharges.bankServiceRuleId", "bankServiceRuleId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("bankServiceRuleId.bankServiceRuleId", bankServiceRuleId));

		//criteria.add(Restrictions.ne("bankCharges.isActive", "U"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankCharges>) findAll(criteria);
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<BankServiceRule> getBankServiceRuleEnqryFrmDB(
			BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankServiceRule.class, "bankServiceRule");

		criteria.setFetchMode("bankServiceRule.countryId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.countryId", "countryId",  JoinType.INNER_JOIN);

		criteria.setFetchMode("bankServiceRule.currencyId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.currencyId", "currencyId",  JoinType.INNER_JOIN);

		criteria.setFetchMode("bankServiceRule.bankId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.bankId", "bankId",  JoinType.INNER_JOIN);

		criteria.setFetchMode("bankServiceRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.remittanceModeId", "remittanceModeId",  JoinType.INNER_JOIN);

		criteria.setFetchMode("bankServiceRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.deliveryModeId", "deliveryModeId",  JoinType.INNER_JOIN);

		if(countryId!=null){
			criteria.add(Restrictions.eq("countryId.countryId", countryId));
		}
		if( currencyId!=null){
			criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		}
		if(bankId!=null){
			criteria.add(Restrictions.eq("bankId.bankId", bankId));
		}
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return  (List<BankServiceRule>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveApproveList(List<BigDecimal> lstBnkCrgsApprove,String userName,BigDecimal bankServiceRuleId) {
		
		if(bankServiceRuleId != null){
			BankServiceRule bankService = (BankServiceRule) getSession().get(BankServiceRule.class, bankServiceRuleId);
			bankService.setIsActive(Constants.Yes);
			bankService.setApprovedBy(userName);
			bankService.setApprovedDate(new Date());
			getSession().update(bankService);
		}
		
		for (BigDecimal bigDecimal : lstBnkCrgsApprove) {
			BankCharges bankCharges = (BankCharges) getSession().get(BankCharges.class, bigDecimal);
			
			bankCharges.setIsActive(Constants.Yes);
			bankCharges.setApprovedBy(userName);
			bankCharges.setApprovedDate(new Date());
			getSession().update(bankCharges);
		}
	}


	@Override
	public boolean saveAllDetails(HashMap<String, Object> saveAllMap) throws Exception{
		try{
			BankServiceRule bankServiceRule = (BankServiceRule) saveAllMap.get("BankService");
			List<BankCharges> bankCharges = (List<BankCharges>) saveAllMap.get("BankCharges");

			getSession().saveOrUpdate(bankServiceRule);

			for (BankCharges lstbankCharges : bankCharges) {
				lstbankCharges.setBankServiceRuleId(bankServiceRule);
				getSession().saveOrUpdate(lstbankCharges);
			}

		}catch (Exception e) {
			throw e;
		}
		return false;
	}
	
	public void deleteRecord(BigDecimal bankServicePk){
		BankCharges bankCharges = (BankCharges) getSession().get(BankCharges.class, bankServicePk);
		getSession().delete(bankCharges);
	}
	
	@Override
	public void deleteRecordsFrmDB(BigDecimal ChargesPk) {
		BankCharges   bankChargesObj=(BankCharges) getSession().get(BankCharges.class, ChargesPk);
		getSession().delete(bankChargesObj);	
		
	}


	@Override
	public void saveBankChargesLog(BankChargesMasterLog bankChargesLogObj) {
		getSession().save(bankChargesLogObj );
		
	}


	@Override
	public List<DeliveryModeDesc> getDeliveryFromRemittanceAndService(
			BigDecimal appCountryID, BigDecimal countryId,
			BigDecimal remittanceId, BigDecimal languageId, BigDecimal serviceId) {

		List<DeliveryModeDesc> lstDeliveryModeDesc = new ArrayList<DeliveryModeDesc>();

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneCountryService.class, "beneCountryService");

		criteria.add(Restrictions.eq("beneCountryService.isActive", Constants.Yes));

		criteria.setFetchMode("beneCountryService.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryID));


		criteria.setFetchMode("beneCountryService.beneCountryId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.beneCountryId", "beneCountryId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("beneCountryId.countryId", countryId));


		criteria.setFetchMode("beneCountryService.remitanceId", FetchMode.JOIN);
		criteria.createAlias("beneCountryService.remitanceId", "remitanceId",  JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remitanceId.remittanceModeId", remittanceId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BeneCountryService> lstBeneCountryService= (List<BeneCountryService>) findAll(criteria);

		if(lstBeneCountryService.size()>0)
		{
			HashSet<BigDecimal>  setDeliverId= new HashSet<BigDecimal>();

			for(BeneCountryService beneCountryService :lstBeneCountryService)
			{
				BigDecimal deliverModeId=beneCountryService.getDeliveryId().getDeliveryModeId();
				setDeliverId.add(deliverModeId);
			}
			for(BigDecimal deliverModeId:setDeliverId)
			{
				DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

				dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
				dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",  JoinType.INNER_JOIN);

				dCriteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));


				dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliverModeId));

				dCriteria.add(Restrictions.eq("deliveryModeDesc.languageType.languageId", languageId));

				dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));

				dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

				List<DeliveryModeDesc> deliveryModeDescList=(List<DeliveryModeDesc>)findAll(dCriteria); 

				//	DeliveryModeDesc deliveryModeDesc=(DeliveryModeDesc)dCriteria.getExecutableCriteria(getSession()).uniqueResult();
				if(deliveryModeDescList.size()>0)
				{
					for(DeliveryModeDesc deliveryModeDesc:deliveryModeDescList){
						lstDeliveryModeDesc.add(deliveryModeDesc);
					}

				}
			}
		}else{

			DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

			dCriteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.JOIN);
			dCriteria.createAlias("deliveryModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

			dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
			dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",JoinType.INNER_JOIN);

			dCriteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));

			BigDecimal eftID =  fetchServiceIdByDesc(Constants.EFTNAME,languageId);
			BigDecimal ttID =  fetchServiceIdByDesc(Constants.TTNAME,languageId);
			BigDecimal bankingChannelID =  fetchDeliveryIdByDesc(Constants.BankingChannelNAME ,languageId);

			if(eftID != null){
				if(serviceId.compareTo(eftID) == 0){
					if(bankingChannelID != null){
						dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", bankingChannelID));
					}else{
						return new ArrayList<DeliveryModeDesc>();
					}
				}
			}else if(ttID != null){
				if(serviceId.compareTo(ttID) == 0){
					if(bankingChannelID != null){
						dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", bankingChannelID));
					}else{
						return new ArrayList<DeliveryModeDesc>();
					}
				}
			}

			//dCriteria.addOrder(Order.asc("deliveryMode.deliveryModeId"));
			dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));
			dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

			List<DeliveryModeDesc> lstDeliveryMode = (List<DeliveryModeDesc>) findAll(dCriteria);

			return lstDeliveryMode;
		
		}

		return lstDeliveryModeDesc;
	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal fetchDeliveryIdByDesc(String DeliveryDesc,BigDecimal langId){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		dCriteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryMode.isActive", Constants.Yes));

		dCriteria.add(Restrictions.eq("deliveryModeDesc.englishDeliveryName",DeliveryDesc));

		dCriteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", langId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DeliveryModeDesc> lstofID = (List<DeliveryModeDesc>) findAll(dCriteria);

		if(lstofID.size() != 0){
			return ((List<DeliveryModeDesc>) findAll(dCriteria)).get(0).getDeliveryMode().getDeliveryModeId();
		}


		return null;

	}
	
	@SuppressWarnings("unchecked")
	public BigDecimal fetchServiceIdByDesc(String serviceDesc,BigDecimal langId){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");

		dCriteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));

		dCriteria.add(Restrictions.eq("serviceMasterDesc.localServiceDescription",serviceDesc));

		dCriteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", langId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ServiceMasterDesc> lstofID = (List<ServiceMasterDesc>) findAll(dCriteria);

		if(lstofID.size()!=0){
			return ((List<ServiceMasterDesc>) findAll(dCriteria)).get(0).getServiceMaster().getServiceId();
		}

		return null;

	}



}
