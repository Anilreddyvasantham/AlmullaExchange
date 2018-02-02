package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.remittance.bean.RoutingSetupBankDetails;
import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.remittance.model.ViewHODirectInDirect;
import com.amg.exchange.remittance.model.ViewRoutingAgentLocations;
import com.amg.exchange.remittance.model.ViewRoutingAgents;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

public interface IRoutingSetUpDetailsService<T> {

	public List<ServiceMasterDesc> getServiceMaster(BigDecimal languageId);

	public List<DeliveryModeDesc> getDeliveryMode(BigDecimal languageId, BigDecimal serviceid);

	public List<RemittanceModeDescription> getRemittanceModeMaster(BigDecimal languageId, BigDecimal serviceid);

	public List<ServiceMasterDesc> getServiceCode(BigDecimal languageId, BigDecimal serviceid);

	public List<DeliveryModeDesc> getDeliveryModeDetails(BigDecimal languageId, BigDecimal deliveryid);

	public List<RemittanceModeDescription> getRemittanceMode(BigDecimal languageId, BigDecimal remittanceid);

	public void saveRoutingHeader(RoutingHeader routingHeader);

	public void saveRoutingDetails(RoutingDetails routingDetails);

	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid, BigDecimal remittanceid, BigDecimal deliveryid);

	public List<RoutingHeader> getAllRecordsIfExist(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid, BigDecimal remittanceid, BigDecimal deliveryid,BigDecimal routingCountryId,BigDecimal routingBankId);

	public List<RoutingHeader> getAllRecordsFromDB(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid);

	public List<RoutingHeader> getAllRecordsFromDBRH(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid);

	public List<RoutingDetails> getAllRecordsOfRoutingDetails(BigDecimal headerid);

	public void deleteRoutingDetails(BigDecimal detailsId, String username);

	// To get the All Type of Banks From Bank Applicability According to
	// CountryId and BanksIndicator
	public List<RoutingSetupBankDetails> getBankListbyIndicators(BigDecimal countryId, BigDecimal correspIndicator, BigDecimal servProvIndicator);

	// public List<AgentMaster> getAgentdetails();

	// public void deleteRoutingSetUpIsActive();
	public BigDecimal fetchDeliveryIdByDesc(String DeliveryDesc, BigDecimal langId);

	// add to Approving purpose toFetch Records 17/06/15
	public List<RoutingHeader> getAllRecordsToFetchForApproval(BigDecimal countryid, BigDecimal currencyid, BigDecimal serviceid, BigDecimal remittanceid, BigDecimal deliveryid);

	public List<RoutingDetails> getAllReCordsFormHeader(BigDecimal routingSetUpHeaderId);

	public void approveRecord(BigDecimal routingSetUpHeaderId, String userName);

	public void getAllDealDetilasList(BigDecimal routingDetailsId, String userName);

	public void upDateActiveRecordtoDeActive(BigDecimal routingSetUpHeaderId, String userName);

	public void upDateActiveRecordDeActive(BigDecimal routingDetailsId, String userName);

	public void DeActiveRecordToPendingForApprovalOfRoutingDetails(BigDecimal routingDetailsId, String userName);

	public void DeActiveRecordToPendingApprovalOfRoutingHeader(BigDecimal routingSetUpHeaderId, String userName);

	public List<RoutingAgentView> getAgentDetails(BigDecimal bankIndicatorId);

	public void saveRoutingAgent(RoutingAgent routingAgent);

	public List<RoutingSetupBankDetails> getAgentBankListbyIndicators(BigDecimal countryId, BigDecimal agentBankIndicator);

	public boolean checkCorresonpondingBankBasedOnBankId(BigDecimal bankId) throws Exception;

	public List<RoutingDetails> toFetchAllBranches(BigDecimal countryId,BigDecimal routingcurrencyId, BigDecimal routingServiceId,BigDecimal routingRemittanceId, BigDecimal routingDeliveryId,BigDecimal routingCountryId, BigDecimal routingbankId);

	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(BigDecimal countryId, BigDecimal routingcurrencyId, BigDecimal routingServiceId, BigDecimal routingRemittanceId, BigDecimal routingDeliveryId);

	public List<RoutingHeader> toFetchHeadervalues(BigDecimal routingHeaderId);

	public List<RoutingDetails> getAllReCordsFormHeaderAndDetais(BigDecimal routingSetupHeaderId);

	public List<RoutingDetails> getAllReCordsFormHeaderAgentDeActivate(BigDecimal routingSetupHeaderId);

	public List<RoutingDetails> getAllReCordsFormHeaderAgentActivate(BigDecimal routingSetupHeaderId);

	public List<ViewRoutingAgents> fetchAllRoutingAgents(BigDecimal appcountryId,BigDecimal countryId,BigDecimal serviceGoupId,BigDecimal spBanKId,BigDecimal currencyId);
	
	public List<ViewRoutingAgentLocations> fetchAllRoutingAgentLocations(BigDecimal appcountryId,BigDecimal countryId,BigDecimal serviceGoupId,BigDecimal spBanKId,BigDecimal currencyId,BigDecimal agentMasterId);

	public List<BankBranch> getSearchBankBranchList(BigDecimal routingCountryId, BigDecimal routingbankId,String serchBranchName);
	
	public List<PopulateDataWithCode> FetchAllBankListbyBankCountry(BigDecimal countryId);
	
	public List<BankBranch> FetchAllBankBranchesTaggedDelete(List<BigDecimal> branchId);
	
	public List<ViewHODirectInDirect> fetchAllHODirectInDirect(BigDecimal appcountryId,BigDecimal countryId,BigDecimal serviceGoupId,BigDecimal spBanKId,BigDecimal currencyId);
	
	public void updateRecord(BigDecimal routingPk);
	
	public List<RoutingDetails> toFetchAllApprovalRecordsFromDetails(BigDecimal countryId, BigDecimal routingcurrencyId, BigDecimal routingServiceId, BigDecimal routingRemittanceId, BigDecimal routingDeliveryId,BigDecimal bankBranchId,BigDecimal routingBankId,BigDecimal ruotingHeaderId);
	
	public List<RoutingDetails> getRoutDtsBasedOnHeaderIdBranchId(BigDecimal routingSetUpHeaderId,BigDecimal routingBranchId);
	

}
