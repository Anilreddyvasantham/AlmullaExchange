package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;

public interface BankIndicatorDao {
	public List<BankIndicatorDescription> getAllRecordsForDuplicateDescCheck(String bankInddesc);
	public void saveOrUpdate(BankIndicator bankIndicatorObj) ;
	public void saveOrUpdate(BankIndicatorDescription bankIndicatorDesc) ;
	public List<BankIndicatorDescription> getAllRecordsFromDB();
	public List<BankIndicator> getAllUnApprovedRecordsFrmDB();
	public List<String> getBankIndicatorCodeFromDB(String query) ;
	
	public String getBankIndicatorInEnglish(BigDecimal bankIndicatorId);
	public String getBankIndicatorInLocal(BigDecimal bankIndicatorId);
	
	public void delete(BankIndicatorDescription bankIndicatorDesc);
	public void delete(BankIndicator bankIndicator);
	public List<BankIndicator> getRecordFromDB(String bankIndicatorCode);
	public List<BankIndicatorDescription> getDescriptionRecordFromDB(BigDecimal bankIndicatorId);
	public List<BankIndicatorDescription> getAllRecordsForApproval();
	public String approve(BigDecimal bankIndicatorPk,String userName);
	
	
	/** Routing agent added by Rabil on 14/12/2015 **/
	public void saveOrUpdate(RoutingAgent routingAgent) ;
	public List<RoutingAgentView> getAgentListFromView(BigDecimal bankIndicatorId); 
	
}
