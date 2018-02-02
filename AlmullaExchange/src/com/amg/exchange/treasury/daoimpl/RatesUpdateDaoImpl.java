package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.GeneratedValue;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.daoimpl.PersonalRemittanceDaoImpl;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.AuthorizationExchangeRateApprovalView;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.model.UnApprovalCashRate;
import com.amg.exchange.remittance.model.UnApprovedExchangeRatesView;
import com.amg.exchange.remittance.model.ViewExchangeRateAppDetails;
import com.amg.exchange.treasury.dao.IRatesUpdateDao;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.ExchangeRate;
import com.amg.exchange.treasury.model.ExchangeRateApproval;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.model.ViewUnApprovedCashRate;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

@Repository
public class RatesUpdateDaoImpl<T> extends CommonDaoImpl<T> implements IRatesUpdateDao,Serializable{

	Logger LOGGER = Logger.getLogger(PersonalRemittanceDaoImpl.class);

	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CountryMasterDesc.class,"countryMasterDesc");
		criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("countryMasterDesc.fsCountryMaster","fsCountryMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		List<CountryMasterDesc> countryName=(List<CountryMasterDesc>) findAll(criteria);
		if(countryName.size()>0){
			return countryName.get(0).getCountryName();
		}else{
			return null;
		}
	}

	/*@Override
	public String getServiceName(BigDecimal serviceId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceIndicator.class,"serviceIndicator");
		criteria.add(Restrictions.eq("serviceIndicator.serviceIndicatorId", serviceId));
		List<ServiceIndicator> serviceName=(List<ServiceIndicator>) findAll(criteria);
		return serviceName.get(0).getServiceFullDesc();
	}*/

	@Override
	public String getBranchName(BigDecimal branchId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CountryBranch.class,"countryBranch");
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", branchId));
		List<CountryBranch> branchName=(List<CountryBranch>) findAll(criteria);
		return branchName.get(0).getBranchName();
	}

	/*@Override
	public List<ServiceIndicator> getServiceIndicatorList() {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceIndicator.class);
		return (List<ServiceIndicator>) findAll(criteria);
	}*/

	@Override
	public void saveRecord(ExchangeRate exchageRateObj) {
		getSession().saveOrUpdate(exchageRateObj);
	}

	@Override
	public void saveExchangeRateApproval(ExchangeRateApproval exchangeApp) {
		getSession().saveOrUpdate(exchangeApp);
	}

	@Override
	public void deleteExchangeRecord(BigDecimal exchageRateObj) {
		ExchangeRate exchangeRatetemp = (ExchangeRate) getSession().get(ExchangeRate.class, exchageRateObj);
		getSession().delete(exchangeRatetemp);
		getSession().flush();
	}

	@Override
	public List<CountryBranch> getCountryBranchList(BigDecimal countryId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CountryBranch.class,"countryBranch");

		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster","countryMaster",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		//criteria.addOrder(Order.asc("countryBranch.branchName"));/** NAG APPLY ASCENDING ORDER 05/02/2015**/
		criteria.addOrder(Order.asc("countryBranch.branchId"));/** NAG APPLY ASCENDING ORDER 05/02/2015**/
		
		return (List<CountryBranch>) findAll(criteria);
	}

	@Override
	public List<BankApplicability> getAllCorespodingBankList() {
		DetachedCriteria criteria=DetachedCriteria.forClass(BankApplicability.class,"bankApplicability");
		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankInd.bankIndicatorId",  new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankApplicability>)findAll(criteria);
	}

	//koti
	@Override
	public String getServiceName(BigDecimal languageId,BigDecimal serviceId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class,"serviceMasterDesc");

		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",  languageId));

		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		List<ServiceMasterDesc> serviceName=(List<ServiceMasterDesc>) findAll(criteria);
		return serviceName.get(0).getLocalServiceDescription();

	}

	@Override
	public List<ServiceMasterDesc> getServiceMasterList(BigDecimal languageId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		criteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster",  JoinType.INNER_JOIN);
		criteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("serviceMasterDesc.languageType", "languageType",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId",  languageId));
		criteria.add(Restrictions.eq("serviceMaster.isActive",  Constants.Yes));

		criteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		return (List<ServiceMasterDesc>) findAll(criteria);
	}

	@Override
	public List<RemittanceModeDescription> getRemittanceMastersList(BigDecimal langugeId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(RemittanceModeDescription.class,"remittanceModeDescription");

		criteria.setFetchMode("remittanceModeDescription.remittanceModeMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.remittanceModeMaster", "remittanceModeMaster",  JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeDescription.languageType.languageId", langugeId));
		criteria.addOrder(Order.asc("remittanceModeDescription.remittanceDescription"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/

		return (List<RemittanceModeDescription>) findAll(criteria);
	}

	@Override
	public List<DeliveryMode> getDeliveryModeMastersList() {
		DetachedCriteria criteria=DetachedCriteria.forClass(DeliveryMode.class);
		return (List<DeliveryMode>) findAll(criteria);
	}

	@Override
	public List<DeliveryModeDesc> lstDeliveryMode(BigDecimal languageId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		dCriteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.languageType", "languageType",  JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryModeDesc.languageType.languageId", languageId));
		dCriteria.addOrder(Order.asc("deliveryModeDesc.englishDeliveryName"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<DeliveryModeDesc>) findAll(dCriteria);
	}

	@Override
	public String getdeliveryName(BigDecimal deliveryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		dCriteria.add(Restrictions.eq("deliveryModeDesc.deliveryMode.deliveryModeId", deliveryId));

		dCriteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
		dCriteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));

		List<DeliveryModeDesc> lsdelivryDesc=(List<DeliveryModeDesc>)findAll(dCriteria);
		return lsdelivryDesc.get(0).getEnglishDeliveryName();
	}

	@Override
	public List<ExchangeRate> exchangeRateList(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal deliveryMode){

		DetachedCriteria dCriteria=DetachedCriteria.forClass(ExchangeRate.class,"exchangeRate");

		if(countryId!=null){
			dCriteria.setFetchMode("exchangeRate.applicationCountryId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("applicationCountryId.countryId" , countryId)); 
		}

		if(currencyId!=null){
			dCriteria.setFetchMode("exchangeRate.currencyId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.currencyId", "currencyId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("currencyId.currencyId" , currencyId));
		}

		if(bankId!=null){
			dCriteria.setFetchMode("exchangeRate.bankMaster", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.bankMaster", "bankMaster",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankMaster.bankId" , bankId));
		}

		if(serviceId!=null){
			dCriteria.setFetchMode("exchangeRate.serviceId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.serviceId", "serviceId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("serviceId.serviceId" , serviceId)); 
		}

		if(branchId!=null){
			dCriteria.setFetchMode("exchangeRate.countryBranchId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.countryBranchId", "countryBranchId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("countryBranchId.countryBranchId" ,branchId));
		}

		if(deliveryMode!=null){
			dCriteria.setFetchMode("exchangeRate.deliveryMode", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.deliveryMode", "deliveryMode",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId" , deliveryMode));
		}

		if(remittanceMode!=null){
			dCriteria.setFetchMode("exchangeRate.remitanceMode", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.remitanceMode", "remitanceMode",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq( "remitanceMode.remittanceModeId", remittanceMode));
		}

		dCriteria.add(Restrictions.eq("recStatus",Constants.Yes));

		return (List<ExchangeRate>)findAll(dCriteria) ;
	}



	public List<ExchangeRate> getAllExchageRates(){
		DetachedCriteria dCriteria=DetachedCriteria.forClass(ExchangeRate.class,"echangeRate");
		dCriteria.add(Restrictions.eq("echangeRate.recStatus",Constants.U));
		return (List<ExchangeRate>)findAll(dCriteria) ;
	}


	@Override
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrency(BigDecimal currencyId,BigDecimal countryId) {

		DetachedCriteria dCriteria=DetachedCriteria.forClass(ExchangeRateApprovalDetModel.class,"exchangeRateApprovalDetModel");

		if(countryId!=null){
			dCriteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryId" , countryId)); 
		}

		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("exchangeRateApprovalDetModel.currencyId" , currencyId));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ExchangeRateApprovalDetModel> exchangeRateList=(List<ExchangeRateApprovalDetModel>)findAll(dCriteria); 

		return exchangeRateList ;
	}

	@Override
	public BigDecimal getExchageRateApprovalPk(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId,BigDecimal serviceId,BigDecimal branchId,BigDecimal remittanceMode,BigDecimal deliveryMode){

		DetachedCriteria dCriteria=DetachedCriteria.forClass(ExchangeRateApproval.class,"exchangeRate");

		if(countryId!=null){
			dCriteria.setFetchMode("exchangeRate.applicationCountryId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("applicationCountryId.countryId" , countryId)); 
		}

		if(currencyId!=null){
			dCriteria.setFetchMode("exchangeRate.currencyId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.currencyId", "currencyId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("currencyId.currencyId" , currencyId));
		}

		if(bankId.compareTo(BigDecimal.ZERO)!=0){
			dCriteria.setFetchMode("exchangeRate.bankMaster", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.bankMaster", "bankMaster",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("bankMaster.bankId" , bankId));
		}

		if(serviceId.compareTo(BigDecimal.ZERO)!=0){
			dCriteria.setFetchMode("exchangeRate.serviceId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.serviceId", "serviceId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("serviceId.serviceId" , serviceId)); 
		}

		if(branchId.compareTo(BigDecimal.ZERO)!=0){
			dCriteria.setFetchMode("exchangeRate.countryBranchId", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.countryBranchId", "countryBranchId",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("countryBranchId.countryBranchId" ,branchId));
		}

		if(deliveryMode.compareTo(BigDecimal.ZERO)!=0){
			dCriteria.setFetchMode("exchangeRate.deliveryMode", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.deliveryMode", "deliveryMode",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId" , deliveryMode));
		}

		if(remittanceMode.compareTo(BigDecimal.ZERO)!=0){
			dCriteria.setFetchMode("exchangeRate.remitanceMode", FetchMode.JOIN);
			dCriteria.createAlias("exchangeRate.remitanceMode", "remitanceMode",  JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq( "remitanceMode.remittanceModeId", remittanceMode));
		}

		List<ExchangeRateApproval> exchangeRateApprovalList=(List<ExchangeRateApproval>)findAll(dCriteria); 
		if(exchangeRateApprovalList.size()>0){
			return exchangeRateApprovalList.get(0).getExchangeRateMasterAprId();
		}else{
			return null;
		}
	}

	@Override
	public List<UnApprovedExchangeRatesView> getAllExchageRatesFromUnApprovedExchangeRateView() {
		DetachedCriteria dCriteria=DetachedCriteria.forClass(UnApprovedExchangeRatesView.class,"unApprovedExchangeRatesView");
		dCriteria.addOrder(Order.asc("unApprovedExchangeRatesView.countryId"));
		dCriteria.addOrder(Order.asc("unApprovedExchangeRatesView.currencyId"));
		dCriteria.addOrder(Order.asc("unApprovedExchangeRatesView.bankId"));
		dCriteria.addOrder(Order.asc("unApprovedExchangeRatesView.countryBranchId"));
		dCriteria.addOrder(Order.asc("unApprovedExchangeRatesView.serviceMasterId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<UnApprovedExchangeRatesView> lstExchangeRate = (List<UnApprovedExchangeRatesView>)findAll(dCriteria);
		return lstExchangeRate;
	}

	@Override
	public List<AuthorizationExchangeRateApprovalView> getAllExchageRatesAuthorizedList() {
		DetachedCriteria dCriteria=DetachedCriteria.forClass(AuthorizationExchangeRateApprovalView.class,"authorizationExchangeRateApprovalView");
		dCriteria.addOrder(Order.asc("authorizationExchangeRateApprovalView.oraUser"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AuthorizationExchangeRateApprovalView> lstAuthorized = (List<AuthorizationExchangeRateApprovalView>)findAll(dCriteria);
		return lstAuthorized;
	}

	@Override
	public void saveApprovedExchangedRate(HashMap<String, Object> saveAprroveRecords) throws AMGException {

		LOGGER.info("Entered into saveApprovedExchangedRate() method ");
		List<ExchangeRateApprovalDetModel> saveExchangeRateApprovalDet = (List<ExchangeRateApprovalDetModel>) saveAprroveRecords.get("lstRecordsToSave");
		HashMap<BigDecimal, BigDecimal> deleteExchangeRateMaster = (HashMap<BigDecimal, BigDecimal>) saveAprroveRecords.get("exchangeRateMaster");

		Session session = getSession();
		session.clear();
		try {

			if(saveExchangeRateApprovalDet != null && saveExchangeRateApprovalDet.size() != 0){
				for (ExchangeRateApprovalDetModel exchangeRateApprovalDetModel : saveExchangeRateApprovalDet) {
					if(exchangeRateApprovalDetModel.getExchangeRateMasterAprDetId() != null){
						//session.update(exchangeRateApprovalDetModel);
						exchangeRateApprovalDetModel.setExchangeRateMasterAprDetId(exchangeRateApprovalDetModel.getExchangeRateMasterAprDetId());
						//getSession().merge(exchangeRateApprovalDetModel);
						session.saveOrUpdate(exchangeRateApprovalDetModel);
					}else{
						session.save(exchangeRateApprovalDetModel);
					}
				}
			}

			if(deleteExchangeRateMaster != null && deleteExchangeRateMaster.size() != 0){
				Iterator<Map.Entry<BigDecimal, BigDecimal>> exchangeMasterId = deleteExchangeRateMaster.entrySet().iterator();
				while (exchangeMasterId.hasNext()) {
					Map.Entry<BigDecimal, BigDecimal> exchId = exchangeMasterId.next();
					System.out.println("Key = " + exchId.getKey() + ", Value = " + exchId.getValue());
					ExchangeRate exchangeMasterPk = (ExchangeRate) getSession().get(ExchangeRate.class, exchId.getKey());
					session.delete(exchangeMasterPk);
				}
			}

		}catch(Exception e){
			LOGGER.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		}


	}

	@Override
	public List<ViewUnApprovedCashRate> getAllCashExchageRatesFromViewUnApprovedCashRate() {
		DetachedCriteria dCriteria=DetachedCriteria.forClass(ViewUnApprovedCashRate.class,"viewUnApprovedCashRate");
		dCriteria.addOrder(Order.asc("viewUnApprovedCashRate.cashRateId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewUnApprovedCashRate> lstCashExchangeRate = (List<ViewUnApprovedCashRate>)findAll(dCriteria);
		return lstCashExchangeRate;
	}

	@Override
	public void saveApprovedCashExchangedRate(HashMap<String, Object> saveAprroveRecords) throws AMGException {

		LOGGER.info("Entered into saveApprovedExchangedRate() method ");
		List<CashRate> saveExchangeRateApprovalDet = (List<CashRate>) saveAprroveRecords.get("lstRecordsToSave");
		HashMap<BigDecimal, BigDecimal> deleteExchangeRateMaster = (HashMap<BigDecimal, BigDecimal>) saveAprroveRecords.get("exchangeRateMaster");
		Session session = getSession();
		session.clear();
		try {

			if(saveExchangeRateApprovalDet != null && saveExchangeRateApprovalDet.size() != 0){
				for (CashRate cashrate : saveExchangeRateApprovalDet) {
					if(cashrate.getCashRateId() != null){
						//session.update(exchangeRateApprovalDetModel);
						cashrate.setCashRateId(cashrate.getCashRateId());
						//getSession().merge(exchangeRateApprovalDetModel);
						getSession().saveOrUpdate(cashrate);
					}else{
						getSession().save(cashrate);
					}
				}
			}

			if(deleteExchangeRateMaster != null && deleteExchangeRateMaster.size() != 0){
				Iterator<Map.Entry<BigDecimal, BigDecimal>> exchangeMasterId = deleteExchangeRateMaster.entrySet().iterator();
				while (exchangeMasterId.hasNext()) {
					Map.Entry<BigDecimal, BigDecimal> exchId = exchangeMasterId.next();
					System.out.println("Key = " + exchId.getKey() + ", Value = " + exchId.getValue());
					UnApprovalCashRate exchangeMasterPk = (UnApprovalCashRate) getSession().get(UnApprovalCashRate.class, exchId.getKey());
					getSession().delete(exchangeMasterPk);
				}
			}

		}catch(Exception e){
			LOGGER.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	public List<ViewExchangeRateAppDetails> getAllExchageRatesBasedOnCountryandCurrencyAndBrancWise(BigDecimal currencyId, BigDecimal countryId,
			BigDecimal branchId) {
		DetachedCriteria dCriteria=DetachedCriteria.forClass(ViewExchangeRateAppDetails.class,"viewExchangeRate");

		if(countryId!=null){
			dCriteria.add(Restrictions.eq("viewExchangeRate.countryId" , countryId)); 
		}

		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("viewExchangeRate.currencyId" , currencyId));
		}

		if(branchId!=null){
			dCriteria.add(Restrictions.eq("viewExchangeRate.countryBranchId" , branchId));
		}
		
		dCriteria.addOrder(Order.asc("viewExchangeRate.serviceId"));


		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ViewExchangeRateAppDetails> exchangeRateList=(List<ViewExchangeRateAppDetails>)findAll(dCriteria); 

		return exchangeRateList ;
	}

	@Override
	public List<ViewExchangeRateAppDetails> getAllExchageRatesCountryBasedOnCurrency(BigDecimal currencyId) {
		DetachedCriteria dCriteria=DetachedCriteria.forClass(ViewExchangeRateAppDetails.class,"viewExchangeRate");

		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("viewExchangeRate.currencyId" , currencyId));
		}
		ProjectionList projection = Projections.projectionList();
		projection.add(Projections.distinct(Projections.property( "viewExchangeRate.countryId")));
		projection.add(Projections.property( "viewExchangeRate.countryName"));
		dCriteria.setProjection(projection);

		List<ViewExchangeRateAppDetails> exchangeRateList=(List<ViewExchangeRateAppDetails>)findAll(dCriteria); 
		return exchangeRateList ;

	}

	@Override
	public List<ExchangeRateApprovalDetModel> getAllExchageRatesBasedOnCountryandCurrencyandBranch(
			BigDecimal currencyId, BigDecimal countryId, BigDecimal branchId) {

		DetachedCriteria dCriteria=DetachedCriteria.forClass(ExchangeRateApprovalDetModel.class,"exchangeRateApprovalDetModel");

		if(countryId!=null){
			dCriteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryId" , countryId)); 
		}

		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("exchangeRateApprovalDetModel.currencyId" , currencyId));
		}
		
		if(branchId!=null){
			dCriteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryBranchId" , branchId));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ExchangeRateApprovalDetModel> exchangeRateList=(List<ExchangeRateApprovalDetModel>)findAll(dCriteria); 

		return exchangeRateList ;
	}



}
