package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;

public interface IBankIndicatorService {
	
	public void saveOrUpdate(BankIndicator bankIndicatorObj);
	public  String approve(BigDecimal  bankIndicatorPk,String userName);
	public void saveOrUpdate(BankIndicatorDescription bankIndicatorDesc);
	public List<BankIndicatorDescription> getAllRecordsForDuplicateDescCheck(String desc);
	public List<BankIndicator> getRecordFromDB(String bankIndicatorCode);
	public List<BankIndicatorDescription> getDescriptionRecordFromDB(BigDecimal bankIndicatorId);

	public List<BankIndicatorDescription>  getAllRecordsFromDB();
	public List<BankIndicator> getAllUnApprovedRecordsFrmDB();
	public List<BankIndicatorDescription> getAllRecordsForApproval();
	public List<String> getBankIndicatorCodeFromDB(String query);
	
	public String getBankIndicatorInEnglish(BigDecimal bankIndicatorId);
	public String getBankIndicatorInLocal(BigDecimal bankIndicatorId);
	
	public void delete(BankIndicatorDescription bankIndicatorDesc);
	public void delete(BankIndicator bankIndicator);
	 

	/** Routing agent added by Rabil on 14/12/2015 **/
	public void saveOrUpdate(RoutingAgent routingAgent) ;
	public List<RoutingAgentView> getAgentListFromView(BigDecimal bankIndicatorId); 
	

}
