package com.amg.exchange.remittance.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.remittance.bean.RoutingSetupBankDetails;
import com.amg.exchange.remittance.dao.IRoutingSetUpDetailsDao;
import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewRoutingAgentLocations;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.remittance.service.IRoutingSetUpDetailsService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

@SuppressWarnings("serial")
@Service("routingSetUpDetailsServiceimpl")
public class RoutingSetUpDetailsServiceimpl<T> implements IRoutingSetUpDetailsService<T>, Serializable  {

	@Autowired
	IRoutingSetUpDetailsDao<T> iroutingSetUpDetailsDao;

	@Transactional
	@Override
	public List<ServiceMasterDesc> getServiceMaster(BigDecimal languageId) {
		return iroutingSetUpDetailsDao.getServiceMaster(languageId);
	}

	@Transactional
	@Override
	public List<DeliveryModeDesc> getDeliveryMode(BigDecimal languageId, BigDecimal serviceid) {
		return iroutingSetUpDetailsDao.getDeliveryMode(languageId,serviceid);
	}

	@Transactional
	@Override
	public List<RemittanceModeDescription> getRemittanceModeMaster(BigDecimal languageId, BigDecimal serviceid) {
		return iroutingSetUpDetailsDao.getRemittanceModeMaster(languageId,serviceid);
	}

	@Transactional
	@Override
	public List<ServiceMasterDesc> getServiceCode(BigDecimal languageId,BigDecimal serviceid) {
		return iroutingSetUpDetailsDao.getServiceCode(languageId,serviceid);
	}

	@Transactional
	@Override
	public List<DeliveryModeDesc> getDeliveryModeDetails(BigDecimal languageId,BigDecimal deliveryid) {
		return iroutingSetUpDetailsDao.getDeliveryModeDetails(languageId,deliveryid);
	}

	@Transactional
	@Override
	public List<RemittanceModeDescription> getRemittanceMode(BigDecimal languageId,BigDecimal remittanceid) {
		return iroutingSetUpDetailsDao.getRemittanceMode(languageId,remittanceid);
	}

	@Transactional
	@Override
	public void saveRoutingHeader(RoutingHeader routingHeader) {
		iroutingSetUpDetailsDao.saveRoutingHeader(routingHeader);
	}

	@Transactional
	@Override
	public void saveRoutingDetails(RoutingDetails routingDetails) {
		iroutingSetUpDetailsDao.saveRoutingDetails(routingDetails);
	}

	@Transactional
	@Override
	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid,BigDecimal remittanceid,BigDecimal deliveryid) {
		return iroutingSetUpDetailsDao.getAllRecordsFromDB(countryid,currencyid,serviceid,remittanceid,deliveryid);
	}
	@Transactional
	@Override
	public List<RoutingHeader> getAllRecordsIfExist(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid, BigDecimal remittanceid, BigDecimal deliveryid,BigDecimal routingCountryId,BigDecimal routingBankId){
		return iroutingSetUpDetailsDao.getAllRecordsIfExist(countryid, currencyid, serviceid, remittanceid, deliveryid, routingCountryId, routingBankId);
	}

	@Transactional
	@Override
	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid){
		return iroutingSetUpDetailsDao.getAllRecordsFromDB(countryid,currencyid,serviceid);
	}


	@Transactional
	@Override
	public List<RoutingHeader> getAllRecordsFromDBRH(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid) {
		return iroutingSetUpDetailsDao.getAllRecordsFromDBRH(countryid,currencyid,serviceid);
	}

	@Transactional
	@Override
	public List<RoutingDetails> getAllRecordsOfRoutingDetails(BigDecimal headerid) {
		return iroutingSetUpDetailsDao.getAllRecordsOfRoutingDetails(headerid);
	}

	@Override
	@Transactional
	public List<RoutingSetupBankDetails> getBankListbyIndicators(BigDecimal countryId,BigDecimal correspIndicator,BigDecimal servProvIndicator)
	{
		return iroutingSetUpDetailsDao.getBankListbyIndicators(countryId,correspIndicator,servProvIndicator);
	}

	@Transactional
	@Override
	public void deleteRoutingDetails(BigDecimal detailsId, String username) {
		// TODO Auto-generated method stub
		iroutingSetUpDetailsDao.deleteRoutingDetails(detailsId,username);
	}

	@Override
	@Transactional
	public BigDecimal fetchDeliveryIdByDesc(String DeliveryDesc,BigDecimal langId) {
		// TODO Auto-generated method stub
		return iroutingSetUpDetailsDao.fetchDeliveryIdByDesc(DeliveryDesc,langId);
	}

	@Override
	@Transactional
	public List<RoutingHeader> getAllRecordsToFetchForApproval(BigDecimal countryid,BigDecimal currencyid,BigDecimal serviceid,BigDecimal remittanceid,BigDecimal deliveryid) {
		return iroutingSetUpDetailsDao.getAllRecordsToFetchForApproval(countryid,currencyid,serviceid,remittanceid,deliveryid);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getAllReCordsFormHeader(BigDecimal routingSetUpHeaderId) {
		return iroutingSetUpDetailsDao.getAllReCordsFormHeader(routingSetUpHeaderId);
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal routingSetUpHeaderId,String userName) {
		iroutingSetUpDetailsDao.approveRecord(routingSetUpHeaderId,userName);
	}

	@Override
	@Transactional
	public void getAllDealDetilasList(BigDecimal routingDetailsId, String userName) {
		iroutingSetUpDetailsDao.getAllDealDetilasList(routingDetailsId,userName);
	}

	@Override
	@Transactional
	public void upDateActiveRecordtoDeActive(BigDecimal routingSetUpHeaderId, String userName) {
		iroutingSetUpDetailsDao.upDateActiveRecordtoDeActive(routingSetUpHeaderId,userName);  
	}

	@Override
	@Transactional
	public void upDateActiveRecordDeActive(BigDecimal routingDetailsId, String userName) {
		iroutingSetUpDetailsDao.upDateActiveRecordDeActive(routingDetailsId,userName);	    
	}

	@Override
	@Transactional
	public void DeActiveRecordToPendingForApprovalOfRoutingDetails(BigDecimal routingDetailsId, String userName) {
		iroutingSetUpDetailsDao.DeActiveRecordToPendingForApprovalOfRoutingDetails(routingDetailsId,userName);  
	}

	@Override
	@Transactional
	public void DeActiveRecordToPendingApprovalOfRoutingHeader(BigDecimal routingSetUpHeaderId, String userName) {
		iroutingSetUpDetailsDao.DeActiveRecordToPendingApprovalOfRoutingHeader(routingSetUpHeaderId,userName);   
	}


	@Override
	@Transactional
	public List<RoutingAgentView> getAgentDetails(BigDecimal bankIndicatorId){
		return  iroutingSetUpDetailsDao.getAgentDetails(bankIndicatorId);   
	}



	@Transactional
	@Override
	public void saveRoutingAgent(RoutingAgent routingAgent){
		iroutingSetUpDetailsDao.saveRoutingAgent(routingAgent);
	}

	@Override
	@Transactional
	public List<RoutingSetupBankDetails> getAgentBankListbyIndicators(BigDecimal countryId, BigDecimal agentBankIndicator) {
		return iroutingSetUpDetailsDao.getAgentBankListbyIndicators(countryId, agentBankIndicator);
	}

	@Override
	@Transactional
	public boolean checkCorresonpondingBankBasedOnBankId(BigDecimal bankId)
			throws Exception {

		return iroutingSetUpDetailsDao.checkCorresonpondingBankBasedOnBankId(bankId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> toFetchAllBranches(BigDecimal countryId,
			BigDecimal routingcurrencyId, BigDecimal routingServiceId,
			BigDecimal routingRemittanceId, BigDecimal routingDeliveryId,
			BigDecimal routingCountryId, BigDecimal routingbankId) {
		return iroutingSetUpDetailsDao.toFetchAllBranches(countryId, routingcurrencyId, routingServiceId, routingRemittanceId, routingDeliveryId, routingCountryId, routingbankId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(BigDecimal countryId, BigDecimal routingcurrencyId, BigDecimal routingServiceId, BigDecimal routingRemittanceId, BigDecimal routingDeliveryId) {
		return iroutingSetUpDetailsDao.toFetchAllApprovalRecordsFromDetails(countryId, routingcurrencyId, routingServiceId, routingRemittanceId, routingDeliveryId);
	}

	@Override
	@Transactional
	public List<RoutingHeader> toFetchHeadervalues(BigDecimal routingHeaderId) {
		return iroutingSetUpDetailsDao.toFetchHeadervalues(routingHeaderId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getAllReCordsFormHeaderAndDetais(BigDecimal routingSetupHeaderId) {
		return iroutingSetUpDetailsDao.getAllReCordsFormHeaderAndDetais(routingSetupHeaderId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getAllReCordsFormHeaderAgentDeActivate(BigDecimal routingSetupHeaderId) {
		return iroutingSetUpDetailsDao.getAllReCordsFormHeaderAgentDeActivate(routingSetupHeaderId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getAllReCordsFormHeaderAgentActivate(BigDecimal routingSetupHeaderId) {
		return iroutingSetUpDetailsDao.getAllReCordsFormHeaderAgentActivate(routingSetupHeaderId);
	}

	@Override
	@Transactional
	public List<ViewRoutingAgents> fetchAllRoutingAgents(BigDecimal appcountryId, BigDecimal countryId,
			BigDecimal serviceGoupId, BigDecimal spBanKId, BigDecimal currencyId) {
		return iroutingSetUpDetailsDao.fetchAllRoutingAgents(appcountryId, countryId, serviceGoupId, spBanKId, currencyId);
	}

	@Override
	@Transactional
	public List<ViewRoutingAgentLocations> fetchAllRoutingAgentLocations(
			BigDecimal appcountryId, BigDecimal countryId,
			BigDecimal serviceGoupId, BigDecimal spBanKId,
			BigDecimal currencyId, BigDecimal agentMasterId) {
		return iroutingSetUpDetailsDao.fetchAllRoutingAgentLocations(appcountryId, countryId, serviceGoupId, spBanKId, currencyId, agentMasterId);
	}

	@Override
	@Transactional
	public List<BankBranch> getSearchBankBranchList(
			BigDecimal routingCountryId, BigDecimal routingbankId,
			String serchBranchName) {
		
		return iroutingSetUpDetailsDao.getSearchBankBranchList(routingCountryId, routingbankId, serchBranchName);
	}

	@Override
	@Transactional
	public List<PopulateDataWithCode> FetchAllBankListbyBankCountry(BigDecimal countryId) {
		return iroutingSetUpDetailsDao.FetchAllBankListbyBankCountry(countryId);
	}

	@Override
	@Transactional
	public List<BankBranch> FetchAllBankBranchesTaggedDelete(List<BigDecimal> branchId) {
		return iroutingSetUpDetailsDao.FetchAllBankBranchesTaggedDelete(branchId);
	}

	@Override
	public List<ViewHODirectInDirect> fetchAllHODirectInDirect(
			BigDecimal appcountryId, BigDecimal countryId,
			BigDecimal serviceGoupId, BigDecimal spBanKId, BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void updateRecord(BigDecimal routingDeatilsPk) {
		iroutingSetUpDetailsDao.updateRecord(routingDeatilsPk);
		
	}

	@Override
	@Transactional
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(
			BigDecimal countryId, BigDecimal routingcurrencyId,
			BigDecimal routingServiceId, BigDecimal routingRemittanceId,
			BigDecimal routingDeliveryId, BigDecimal bankBranchId,
			BigDecimal routingBankId, BigDecimal ruotingHeaderId) {
 
		return iroutingSetUpDetailsDao.toFetchAllApprovalRecordsFromDetails(  countryId,   routingcurrencyId,
				  routingServiceId,   routingRemittanceId,
				  routingDeliveryId,   bankBranchId,
				  routingBankId,   ruotingHeaderId);
	}

	@Override
	@Transactional
	public List<RoutingDetails> getRoutDtsBasedOnHeaderIdBranchId(
			BigDecimal routingSetUpHeaderId, BigDecimal routingBranchId) {
		
		return iroutingSetUpDetailsDao.getRoutDtsBasedOnHeaderIdBranchId(routingSetUpHeaderId, routingBranchId);
	}

	 

}
