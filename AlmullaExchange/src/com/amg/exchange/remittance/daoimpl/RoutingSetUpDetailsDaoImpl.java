package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.remittance.bean.RoutingSetupBankDetails;
import com.amg.exchange.remittance.dao.IRoutingSetUpDetailsDao;
import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.ViewRoutingAgentLocations;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class RoutingSetUpDetailsDaoImpl<T> extends CommonDaoImpl<T> implements
IRoutingSetUpDetailsDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getServiceMaster(BigDecimal languageId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");

		dCriteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		dCriteria.setFetchMode("serviceMasterDesc.serviceMaster",FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.serviceMaster","serviceMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceMaster.isActive", Constants.Yes));

		//dCriteria.addOrder(Order.asc("serviceMaster.serviceId"));
		dCriteria.addOrder(Order.asc("serviceMasterDesc.localServiceDescription"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ServiceMasterDesc> lstServiceMaster = (List<ServiceMasterDesc>) findAll(dCriteria);

		return lstServiceMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> getDeliveryMode(BigDecimal languageId ,BigDecimal serviceid) {

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
			if(serviceid.compareTo(eftID) == 0){
				if(bankingChannelID != null){
					dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", bankingChannelID));
				}else{
					return new ArrayList<DeliveryModeDesc>();
				}
			}
		}else if(ttID != null){
			if(serviceid.compareTo(ttID) == 0){
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

	@SuppressWarnings("unchecked")
	@Override
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
	@Override
	public List<RemittanceModeDescription> getRemittanceModeMaster(BigDecimal languageId ,BigDecimal serviceid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "RemittanceModeDescription");

		dCriteria.setFetchMode("RemittanceModeDescription.remittanceModeMaster",FetchMode.JOIN);
		dCriteria.createAlias("RemittanceModeDescription.remittanceModeMaster","remittanceModeMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("remittanceModeMaster.serviceMaster.serviceId", serviceid));

		dCriteria.setFetchMode("RemittanceModeDescription.languageType", FetchMode.JOIN);
		dCriteria.createAlias("RemittanceModeDescription.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		dCriteria.add(Restrictions.eq("remittanceModeMaster.isActive", Constants.Yes));

		dCriteria.addOrder(Order.asc("remittanceModeMaster.remittanceModeId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceModeDescription> lstRemittanceModeMaster = (List<RemittanceModeDescription>) findAll(dCriteria);

		return lstRemittanceModeMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ServiceMasterDesc> getServiceCode(BigDecimal languageId,BigDecimal serviceid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");

		dCriteria.setFetchMode("serviceMasterDesc.serviceMaster",FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.serviceMaster","serviceMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceMaster.serviceId", serviceid));

		dCriteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		dCriteria.addOrder(Order.asc("serviceMaster.serviceId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ServiceMasterDesc> lstServiceMaster = (List<ServiceMasterDesc>) findAll(dCriteria);

		return lstServiceMaster;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> getDeliveryModeDetails(BigDecimal languageId,BigDecimal deliveryid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		dCriteria.setFetchMode("deliveryModeDesc.deliveryMode",FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.deliveryMode","deliveryMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryid));

		dCriteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("deliveryModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		dCriteria.addOrder(Order.asc("deliveryMode.deliveryModeId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DeliveryModeDesc> lstDeliveryMode = (List<DeliveryModeDesc>) findAll(dCriteria);

		return lstDeliveryMode;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RemittanceModeDescription> getRemittanceMode(BigDecimal languageId,BigDecimal remittanceid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");

		dCriteria.setFetchMode("remittanceModeDescription.remittanceModeMaster",FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDescription.remittanceModeMaster","remittanceModeMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId",	remittanceid));

		dCriteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		dCriteria.createAlias("remittanceModeDescription.languageType", "languageType",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));

		dCriteria.addOrder(Order.asc("remittanceModeMaster.remittanceModeId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceModeDescription> lstRemittanceModeMaster = (List<RemittanceModeDescription>) findAll(dCriteria);

		return lstRemittanceModeMaster;
	}

	@Override
	public void saveRoutingHeader(RoutingHeader routingHeader) {
		getSession().saveOrUpdate(routingHeader);
	}

	@Override
	public void saveRoutingDetails(RoutingDetails routingDetails) {
		getSession().saveOrUpdate(routingDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid,BigDecimal currencyid, BigDecimal serviceid,BigDecimal remittanceid, BigDecimal deliveryid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));

		if(remittanceid != null){
			dCriteria.setFetchMode("routingHeader.exRemittanceModeId",FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",remittanceid));
		}

		if(deliveryid != null){
			dCriteria.setFetchMode("routingHeader.exDeliveryModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",deliveryid));
		}

		dCriteria.addOrder(Order.asc("routingHeader.routingHeaderId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> lstAllData = (List<RoutingHeader>) findAll(dCriteria);

		return lstAllData;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllRecordsIfExist(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid, BigDecimal remittanceid, BigDecimal deliveryid,BigDecimal routingCountryId,BigDecimal routingBankId){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));

		if(remittanceid != null){
			dCriteria.setFetchMode("routingHeader.exRemittanceModeId",FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",remittanceid));
		}

		if(deliveryid != null){
			dCriteria.setFetchMode("routingHeader.exDeliveryModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",deliveryid));
		}

		dCriteria.setFetchMode("routingHeader.exRoutingCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingCountryId","exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId",routingCountryId));

		dCriteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId",routingBankId));


		dCriteria.addOrder(Order.asc("routingHeader.routingHeaderId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> lstAllData = (List<RoutingHeader>) findAll(dCriteria);

		return lstAllData;
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));


		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> lstAllData = (List<RoutingHeader>) findAll(dCriteria);

		return lstAllData;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllRecordsFromDBRH(BigDecimal countryid,BigDecimal currencyid, BigDecimal serviceid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));

		dCriteria.addOrder(Order.asc("routingHeader.routingHeaderId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> lstAllData = (List<RoutingHeader>) findAll(dCriteria);

		return lstAllData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingDetails> getAllRecordsOfRoutingDetails(BigDecimal headerid) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		dCriteria.setFetchMode("routingDetails.exRountingHeaderId",FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",headerid));

		dCriteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exBankBranchId","exBankBranchId", JoinType.INNER_JOIN);
		dCriteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.ne("routingDetails.isActive", "N"));
		dCriteria.addOrder(Order.asc("routingDetails.routingDetailsId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingDetails> lstDetailstable = (List<RoutingDetails>) findAll(dCriteria);

		return lstDetailstable;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingSetupBankDetails> getBankListbyIndicators(BigDecimal countryId, BigDecimal correspIndicator,BigDecimal servProvIndicator) {

		RoutingSetupBankDetails bankApplicabilitybean = null;
		List<RoutingSetupBankDetails> finalData = new ArrayList<RoutingSetupBankDetails>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",countryId));

		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.disjunction().add(Restrictions.eq("bankInd.bankIndicatorId",correspIndicator)).add(Restrictions.eq("bankInd.bankIndicatorId",servProvIndicator)));


		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		for (BankApplicability lstofBankApplicablity : data) {
			bankApplicabilitybean = new RoutingSetupBankDetails(lstofBankApplicablity.getBankMaster().getBankId(), lstofBankApplicablity.getBankMaster().getBankFullName(),lstofBankApplicablity.getBankMaster().getBankCode(),lstofBankApplicablity.getBankInd().getBankIndicatorId());
			if(!duplicateCheck.contains(bankApplicabilitybean.getBankId())) {
				duplicateCheck.add(bankApplicabilitybean.getBankId());
				finalData.add(bankApplicabilitybean);
			}
		}

		return finalData;
	}

	@Override
	public void deleteRoutingDetails(BigDecimal detailsId, String username) {
		// TODO Auto-generated method stub

		RoutingDetails routingDetails=(RoutingDetails) getSession().get(RoutingDetails.class, detailsId);
		routingDetails.setIsActive("D");
		routingDetails.setModifiedBy(username);
		routingDetails.setModifiedDate(new Date());
		getSession().update(routingDetails);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingHeader> getAllRecordsToFetchForApproval(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid,BigDecimal remittanceid,BigDecimal deliveryid) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));

		if(remittanceid!=null){
			dCriteria.setFetchMode("routingHeader.exRemittanceModeId",FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",remittanceid));
		}
		if(deliveryid!=null){
			dCriteria.setFetchMode("routingHeader.exDeliveryModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingHeader.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",deliveryid));
		}

		dCriteria.addOrder(Order.asc("routingHeader.routingHeaderId"));
		dCriteria.add(Restrictions.eq("routingHeader.isActive",Constants.U));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<RoutingHeader>) findAll(dCriteria);
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<RoutingDetails> getAllReCordsFormHeader(BigDecimal routingSetUpHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",routingSetUpHeaderId));
		dCriteria.add(Restrictions.ne("routingDetails.isActive", Constants.No));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingDetails>) findAll(dCriteria);
	}

	@Override
	public void approveRecord(BigDecimal routingSetUpHeaderId,String userName) {
		RoutingHeader routingHeader = (RoutingHeader) getSession().get(RoutingHeader.class, routingSetUpHeaderId);
		routingHeader.setIsActive(Constants.Yes);
		routingHeader.setApprovedBy(userName);
		routingHeader.setApprovedDate(new Date());
		getSession().update(routingHeader);
	}

	@Override
	public void getAllDealDetilasList(BigDecimal routingDetailsId, String userName) {
		RoutingDetails routingDetails = (RoutingDetails) getSession().get(RoutingDetails.class, routingDetailsId);
		routingDetails.setIsActive(Constants.Yes);
		routingDetails.setApprovedBy(userName);
		routingDetails.setApprovedDate(new Date());
		getSession().update(routingDetails);
	}

	@Override
	public void upDateActiveRecordtoDeActive(BigDecimal routingSetUpHeaderId,String userName) {
		RoutingHeader routingHeader = (RoutingHeader) getSession().get(RoutingHeader.class, routingSetUpHeaderId);
		routingHeader.setIsActive(Constants.D);
		routingHeader.setApprovedBy(null);
		routingHeader.setApprovedDate(null);
		routingHeader.setModifiedBy(userName);
		routingHeader.setModifiedDate(new Date());
		getSession().update(routingHeader);
	}

	@Override
	public void upDateActiveRecordDeActive(BigDecimal routingDetailsId, String userName) {
		RoutingDetails routingDetails = (RoutingDetails) getSession().get(RoutingDetails.class, routingDetailsId);
		routingDetails.setIsActive(Constants.D);
		routingDetails.setApprovedBy(null);
		routingDetails.setApprovedDate(null);
		routingDetails.setModifiedBy(userName);
		routingDetails.setModifiedDate(new Date());
		getSession().update(routingDetails);
	}

	@Override
	public void DeActiveRecordToPendingForApprovalOfRoutingDetails(BigDecimal routingDetailsId, String userName) {
		RoutingDetails routingDetails = (RoutingDetails) getSession().get(RoutingDetails.class, routingDetailsId);
		routingDetails.setIsActive(Constants.U);
		routingDetails.setApprovedBy(null);
		routingDetails.setApprovedDate(null);
		routingDetails.setModifiedBy(userName);
		routingDetails.setModifiedDate(new Date());
		getSession().update(routingDetails);
	}

	@Override
	public void DeActiveRecordToPendingApprovalOfRoutingHeader(BigDecimal routingSetUpHeaderId, String userName) {
		RoutingHeader routingHeader = (RoutingHeader) getSession().get(RoutingHeader.class, routingSetUpHeaderId);
		routingHeader.setIsActive(Constants.U);
		routingHeader.setApprovedBy(null);
		routingHeader.setApprovedDate(null);
		routingHeader.setModifiedBy(userName);
		routingHeader.setModifiedDate(new Date());
		getSession().update(routingHeader);
	}


	@Override
	public List<RoutingAgentView> getAgentDetails(BigDecimal bankIndicatorId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingAgentView.class, "routingAgentView");
		dCriteria.add(Restrictions.eq("routingAgentView.bankIndicatorId",bankIndicatorId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingAgentView>) findAll(dCriteria);
	}


	@Override
	public void saveRoutingAgent(RoutingAgent routingAgent) {
		getSession().saveOrUpdate(routingAgent);
	}


	@Override
	public List<RoutingSetupBankDetails> getAgentBankListbyIndicators(BigDecimal countryId, BigDecimal agentBankIndicator) {

		RoutingSetupBankDetails bankApplicabilitybean = null;
		List<RoutingSetupBankDetails> finalData = new ArrayList<RoutingSetupBankDetails>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",countryId));

		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId",agentBankIndicator));


		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		for (BankApplicability lstofBankApplicablity : data) {
			bankApplicabilitybean = new RoutingSetupBankDetails(lstofBankApplicablity.getBankMaster().getBankId(), lstofBankApplicablity.getBankMaster().getBankFullName()==null ? "" :lstofBankApplicablity.getBankMaster().getBankFullName().trim(),lstofBankApplicablity.getBankMaster().getBankCode(),lstofBankApplicablity.getBankInd().getBankIndicatorId());
			if(!duplicateCheck.contains(bankApplicabilitybean.getBankId())) {
				duplicateCheck.add(bankApplicabilitybean.getBankId());
				finalData.add(bankApplicabilitybean);
			}
		}

		return finalData;
	}

	@Override
	public boolean checkCorresonpondingBankBasedOnBankId(BigDecimal bankId)
			throws Exception {


		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",	JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.bankId",bankId));


		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);



		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId",corresBankIndicatorId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBankApplicability= (List<BankApplicability>) findAll(dCriteria);

		if(lstBankApplicability!=null && lstBankApplicability.size()>0)
		{
			return true;
		}else
		{
			return false;
		}

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
	public List<RoutingDetails> toFetchAllBranches(BigDecimal countryId,BigDecimal routingcurrencyId, BigDecimal routingServiceId,BigDecimal routingRemittanceId, BigDecimal routingDeliveryId,BigDecimal routingCountryId, BigDecimal routingbankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		dCriteria.setFetchMode("routingDetails.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryId));

		dCriteria.setFetchMode("routingDetails.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", routingcurrencyId));

		dCriteria.setFetchMode("routingDetails.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", routingServiceId));

		if(routingRemittanceId != null){
			dCriteria.setFetchMode("routingDetails.exRemittanceModeId",FetchMode.JOIN);
			dCriteria.createAlias("routingDetails.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",routingRemittanceId));
		}

		if(routingDeliveryId != null){
			dCriteria.setFetchMode("routingDetails.exDeliveryModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingDetails.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",routingDeliveryId));
		}

		dCriteria.setFetchMode("routingDetails.exRoutingCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingCountryId","exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId",routingCountryId));

		dCriteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId",routingbankId));


		dCriteria.addOrder(Order.asc("routingDetails.routingDetailsId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingDetails> lstAllData = (List<RoutingDetails>) findAll(dCriteria);

		return lstAllData;
	}

	@Override
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(BigDecimal countryId, BigDecimal routingcurrencyId, BigDecimal routingServiceId, BigDecimal routingRemittanceId, BigDecimal routingDeliveryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		dCriteria.setFetchMode("routingDetails.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCountryId", "exCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryId));

		dCriteria.setFetchMode("routingDetails.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCurrenyId", "exCurrenyId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", routingcurrencyId));

		dCriteria.setFetchMode("routingDetails.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exServiceId", "exServiceId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", routingServiceId));

		if (routingRemittanceId != null) {
			dCriteria.setFetchMode("routingDetails.exRemittanceModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingDetails.exRemittanceModeId", "exRemittanceModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId", routingRemittanceId));
		}

		if (routingDeliveryId != null) {
			dCriteria.setFetchMode("routingDetails.exDeliveryModeId", FetchMode.JOIN);
			dCriteria.createAlias("routingDetails.exDeliveryModeId", "exDeliveryModeId", JoinType.INNER_JOIN);
			dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId", routingDeliveryId));
		}
		dCriteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exBankBranchId", "exBankBranchId", JoinType.LEFT_OUTER_JOIN);
		dCriteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingBankId", "exRoutingBankId", JoinType.LEFT_OUTER_JOIN);
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.U));
		dCriteria.addOrder(Order.asc("routingDetails.routingDetailsId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingDetails> lstAllData = (List<RoutingDetails>) findAll(dCriteria);

		return lstAllData;
	}

	@Override
	public List<RoutingHeader> toFetchHeadervalues(BigDecimal routingHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingHeader.class, "routingHeader");

		dCriteria.setFetchMode("routingHeader.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exCountryId.countryId", countryid));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyid));

		dCriteria.setFetchMode("routingHeader.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exServiceId.serviceId", serviceid));


		dCriteria.setFetchMode("routingHeader.exRemittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",remittanceid));


		//if(deliveryid != null){
		dCriteria.setFetchMode("routingHeader.exDeliveryModeId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",deliveryid));
		//}

		dCriteria.setFetchMode("routingHeader.exRoutingCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingCountryId","exRoutingCountryId", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId",routingCountryId));

		dCriteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exRoutingBankId.bankId",routingBankId));


		dCriteria.addOrder(Order.asc("routingHeader.routingHeaderId"));
		dCriteria.add(Restrictions.eq("routingHeader.routingHeaderId",routingHeaderId));
		dCriteria.add(Restrictions.eq("routingHeader.isActive",Constants.U));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> lstAllData = (List<RoutingHeader>) findAll(dCriteria);

		return lstAllData;
	}

	@Override
	public List<RoutingDetails> getAllReCordsFormHeaderAndDetais(BigDecimal routingSetupHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",routingSetupHeaderId));
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.U));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingDetails>) findAll(dCriteria);
	}

	@Override
	public List<RoutingDetails> getAllReCordsFormHeaderAgentDeActivate(BigDecimal routingSetupHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",routingSetupHeaderId));
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingDetails>) findAll(dCriteria);
	}
	@Override
	public List<RoutingDetails> getAllReCordsFormHeaderAgentActivate(BigDecimal routingSetupHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",routingSetupHeaderId));
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.D));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RoutingDetails>) findAll(dCriteria);
	}

	@Override
	public List<ViewRoutingAgents> fetchAllRoutingAgents(BigDecimal appcountryId, BigDecimal countryId,
			BigDecimal serviceGoupId, BigDecimal spBanKId, BigDecimal currencyId) {
		List<BigDecimal> lstDuplicates = new ArrayList<BigDecimal>();
		List<ViewRoutingAgents> lstAllRoutingCountrys = new ArrayList<ViewRoutingAgents>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewRoutingAgents.class, "viewRoutingAgents");

		dCriteria.add(Restrictions.eq("viewRoutingAgents.applicationCountryId", appcountryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgents.routingCountryId", countryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgents.serviceGroupId", serviceGoupId));
		if(spBanKId != null){
			dCriteria.add(Restrictions.eq("viewRoutingAgents.routingBankId", spBanKId));
		}
		if(currencyId != null){
			dCriteria.add(Restrictions.eq("viewRoutingAgents.currencyId", currencyId));
		}

		dCriteria.addOrder(Order.asc("viewRoutingAgents.routingBankId"));
		List<ViewRoutingAgents> lstAllData = (List<ViewRoutingAgents>) findAll(dCriteria);
		
		for (ViewRoutingAgents viewRoutingAgents : lstAllData) {
			if(!lstDuplicates.contains(viewRoutingAgents.getAgentBankId())){
				lstDuplicates.add(viewRoutingAgents.getAgentBankId());
				lstAllRoutingCountrys.add(viewRoutingAgents);
			}
		}
		
		return lstAllRoutingCountrys;
	}
	@Override
	public List<ViewRoutingAgentLocations> fetchAllRoutingAgentLocations(BigDecimal appcountryId, BigDecimal countryId,	BigDecimal serviceGoupId, BigDecimal spBanKId,
			BigDecimal currencyId, BigDecimal agentMasterId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewRoutingAgentLocations.class, "viewRoutingAgentLocations");
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.applicationCountryId", appcountryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.routingCountryId", countryId));
		dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.serviceGroupId", serviceGoupId));
		if(spBanKId != null){
			dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.routingBankId", spBanKId));
		}
		if(currencyId != null){
			dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.currencyId", currencyId));
		}
		if(agentMasterId != null){
			dCriteria.add(Restrictions.eq("viewRoutingAgentLocations.agentBankId", agentMasterId));
		}

		dCriteria.addOrder(Order.asc("viewRoutingAgentLocations.routingBankId"));

		List<ViewRoutingAgentLocations> lstAllData = (List<ViewRoutingAgentLocations>) findAll(dCriteria);

		return lstAllData;
	}

	@Override
	public List<BankBranch> getSearchBankBranchList(
			BigDecimal routingCountryId, BigDecimal routingbankId,
			String serchBranchName) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankBranch.class, "bankBranch");
		dCriteria.setFetchMode("bankBranch.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.fsCountryMaster", "fsCountryMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", routingCountryId));
		dCriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", routingbankId));

		dCriteria.add(Restrictions.ilike("bankBranch.branchFullName", serchBranchName,MatchMode.ANYWHERE));

		dCriteria.addOrder(Order.asc("bankBranch.branchFullName"));
		dCriteria.add(Restrictions.eq("bankBranch.isactive", Constants.Yes));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankBranch>) findAll(dCriteria);
	}

	@Override
	public List<PopulateDataWithCode> FetchAllBankListbyBankCountry(BigDecimal countryId) {

		RoutingSetupBankDetails bankApplicabilitybean = null;
		List<PopulateDataWithCode> lstBanks = new ArrayList<PopulateDataWithCode>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId",countryId));

		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
		BigDecimal agentBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_AGENT_BANK);
		BigDecimal beneBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);
		BigDecimal serviceProviderBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_SERVICEPRO_BANK);
		
		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);
		
		Disjunction lstjunction = Restrictions.disjunction();
		
		if(corresBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",corresBankIndicatorId));
		}
		
		if(agentBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",agentBankIndicatorId));
		}

		
		if(beneBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",beneBankIndicatorId));
		}
		
		if(serviceProviderBankIndicatorId != null){
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",serviceProviderBankIndicatorId));
		}
		
		dCriteria.add(lstjunction);


		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));/** NAG CODE APPLY ASCENDING ORDER 05/02/2015**/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> data = (List<BankApplicability>) findAll(dCriteria);

		for (BankApplicability lstofBankApplicablity : data) {
			bankApplicabilitybean = new RoutingSetupBankDetails(lstofBankApplicablity.getBankMaster().getBankId(), lstofBankApplicablity.getBankMaster().getBankFullName()==null ? "" :lstofBankApplicablity.getBankMaster().getBankFullName().trim(),lstofBankApplicablity.getBankMaster().getBankCode(),lstofBankApplicablity.getBankInd().getBankIndicatorId());
			if(!duplicateCheck.contains(bankApplicabilitybean.getBankId())) {
				duplicateCheck.add(bankApplicabilitybean.getBankId());
				//finalData.add(bankApplicabilitybean);
				PopulateDataWithCode lstOfBanks = new PopulateDataWithCode();

				lstOfBanks.setId(lstofBankApplicablity.getBankMaster().getBankId());
				lstOfBanks.setCode(lstofBankApplicablity.getBankMaster().getBankCode());
				lstOfBanks.setName(lstofBankApplicablity.getBankMaster().getBankFullName());

				lstBanks.add(lstOfBanks);
			}
		}

		return lstBanks;
	}

	@Override
	public List<BankBranch> FetchAllBankBranchesTaggedDelete(List<BigDecimal> branchId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		
		Disjunction lstjunction = Restrictions.disjunction();
		
		for (BigDecimal bigDecimal : branchId) {
			lstjunction.add(Restrictions.eq("bankBranch.bankBranchId",bigDecimal));
		}
		
		dCriteria.add(lstjunction);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch> data = (List<BankBranch>) findAll(dCriteria);
		
		return data;
	}

	@Override
	public void updateRecord(BigDecimal routingDeatilsPk) {
		RoutingDetails  routingDetails=(RoutingDetails) getSession().get(RoutingDetails.class, routingDeatilsPk);
		routingDetails.setIsActive(Constants.No);
		getSession().saveOrUpdate( routingDetails);
	}

	@Override
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(
			BigDecimal countryId, BigDecimal routingcurrencyId,
			BigDecimal routingServiceId, BigDecimal routingRemittanceId,
			BigDecimal routingDeliveryId, BigDecimal bankBranchId,
			BigDecimal routingBankId, BigDecimal ruotingHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		dCriteria.setFetchMode("routingDetails.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryId));

		dCriteria.setFetchMode("routingDetails.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", routingcurrencyId));

		dCriteria.setFetchMode("routingDetails.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", routingServiceId));

		if(routingRemittanceId!=null){
		dCriteria.setFetchMode("routingDetails.exRemittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",routingRemittanceId));
		}

		if(routingDeliveryId!=null){
		dCriteria.setFetchMode("routingDetails.exDeliveryModeId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",routingDeliveryId));
		}

 

		dCriteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId",routingBankId));

		dCriteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exBankBranchId", "exBankBranchId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankBranchId.bankBranchId",bankBranchId));
		
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",ruotingHeaderId));
 
		 
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.No));
		dCriteria.add(Restrictions.eq("routingDetails.branchApplicability","S"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingDetails> lstAllData = (List<RoutingDetails>) findAll(dCriteria);

		return lstAllData;
 
	}

	@Override
	public List<RoutingDetails> getRoutDtsBasedOnHeaderIdBranchId(
			BigDecimal routingSetUpHeaderId, BigDecimal routingBranchId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");
		
		dCriteria.setFetchMode("routingDetails.exRountingHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRountingHeaderId","exRountingHeaderId", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("routingDetails.exBankBranchId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exBankBranchId","exBankBranchId", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("exRountingHeaderId.routingHeaderId",routingSetUpHeaderId));
		dCriteria.add(Restrictions.eq("exBankBranchId.bankBranchId",routingBranchId));
		dCriteria.add(Restrictions.ne("routingDetails.isActive", Constants.No));
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RoutingDetails> lstRoutingDetails=(List<RoutingDetails>) findAll(dCriteria);
		
		return lstRoutingDetails;
	}

	/*@Override
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(
			BigDecimal countryId, BigDecimal routingcurrencyId,
			BigDecimal routingServiceId, BigDecimal routingRemittanceId,
			BigDecimal routingDeliveryId, BigDecimal bankBranchId,BigDecimal routingBankId
			BigDecimal ruotingHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RoutingDetails.class, "routingDetails");

		dCriteria.setFetchMode("routingDetails.exCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCountryId", "exCountryId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCountryId.countryId", countryId));

		dCriteria.setFetchMode("routingDetails.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exCurrenyId", "exCurrenyId",JoinType.INNER_JOIN);
	dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", routingcurrencyId));

		dCriteria.setFetchMode("routingDetails.exServiceId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exServiceId", "exServiceId",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exServiceId.serviceId", routingServiceId));


		dCriteria.setFetchMode("routingDetails.exRemittanceModeId",FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRemittanceModeId","exRemittanceModeId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRemittanceModeId.remittanceModeId",remittanceid));


 
		dCriteria.setFetchMode("routingDetails.exDeliveryModeId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exDeliveryModeId","exDeliveryModeId", JoinType.INNER_JOIN);
		//dCriteria.add(Restrictions.eq("exDeliveryModeId.deliveryModeId",deliveryid));
	 

		dCriteria.setFetchMode("routingDetails.exRoutingCountryId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingCountryId","exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId",routingCountryId));

		dCriteria.setFetchMode("routingDetails.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingDetails.exRoutingBankId","exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId",routingBankId));


 
		dCriteria.add(Restrictions.eq("routingDetails.routingHeaderId",ruotingHeaderId));
		dCriteria.add(Restrictions.eq("routingDetails.isActive",Constants.No));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingDetails> lstAllData = (List<RoutingDetails>) findAll(dCriteria);

		return lstAllData;
 
 
	}*/

}
