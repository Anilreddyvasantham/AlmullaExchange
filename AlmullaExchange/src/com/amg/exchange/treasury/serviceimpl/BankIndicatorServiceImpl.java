package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.model.RoutingAgent;
import com.amg.exchange.remittance.model.RoutingAgentView;
import com.amg.exchange.treasury.dao.BankIndicatorDao;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.service.IBankIndicatorService;

@Service
public class BankIndicatorServiceImpl implements IBankIndicatorService {

	@Autowired
	BankIndicatorDao bankIndicatorDao;
	

	@Override
	@Transactional
	public void saveOrUpdate(BankIndicator bankIndicatorObj) {
		bankIndicatorDao.saveOrUpdate(bankIndicatorObj);
 	}


	@Override
	@Transactional
	public List<BankIndicatorDescription> getAllRecordsFromDB() { 
		return bankIndicatorDao.getAllRecordsFromDB();
	}


	@Override
	@Transactional
	public List<BankIndicator> getAllUnApprovedRecordsFrmDB() {
		 
		return bankIndicatorDao.getAllUnApprovedRecordsFrmDB();
	}


	@Override
	@Transactional
	public List<String> getBankIndicatorCodeFromDB(String query) {
		 
		return bankIndicatorDao.getBankIndicatorCodeFromDB(query);
	}


	@Override
	@Transactional
	public String getBankIndicatorInEnglish(BigDecimal bankIndicatorId) {
	 
		return bankIndicatorDao.getBankIndicatorInEnglish(bankIndicatorId);
	}


	@Override
	@Transactional
	public String getBankIndicatorInLocal(BigDecimal bankIndicatorId) {
		
		return bankIndicatorDao.getBankIndicatorInLocal(bankIndicatorId);
	}


	@Override
	@Transactional
	public void saveOrUpdate(BankIndicatorDescription bankIndicatorDesc) {
		bankIndicatorDao.saveOrUpdate(bankIndicatorDesc);
	}


	@Override
	@Transactional
	public List<BankIndicatorDescription> getAllRecordsForDuplicateDescCheck(String bankInddesc) {
		 
		return bankIndicatorDao.getAllRecordsForDuplicateDescCheck(bankInddesc);
	}


	@Override
	@Transactional
	public void delete(BankIndicatorDescription bankIndicatorDesc) {
		bankIndicatorDao.delete(bankIndicatorDesc);
		
	}


	@Override
	@Transactional
	public void delete(BankIndicator bankIndicator) {
		bankIndicatorDao.delete(bankIndicator);
		
	}


	@Override
	@Transactional
	public List<BankIndicator> getRecordFromDB(String bankIndicatorCode) {
		return bankIndicatorDao.getRecordFromDB(bankIndicatorCode);
	}


	@Override
	@Transactional
	public List<BankIndicatorDescription> getDescriptionRecordFromDB( BigDecimal bankIndicatorCode) {
		return bankIndicatorDao.getDescriptionRecordFromDB(bankIndicatorCode);
	}


	@Override
	@Transactional
	public List<BankIndicatorDescription> getAllRecordsForApproval() {
		 
		return bankIndicatorDao.getAllRecordsForApproval();
	}


	@Override
	@Transactional
	public String approve(BigDecimal  bankIndicatorInPk,String userName) {
		 
		return bankIndicatorDao.approve(bankIndicatorInPk,userName);
	}

	@Override
	@Transactional
	public void saveOrUpdate(RoutingAgent routingAgent) {
		bankIndicatorDao.saveOrUpdate(routingAgent);
	}


	@Override
	@Transactional
	public List<RoutingAgentView> getAgentListFromView(BigDecimal bankIndicatorId) {
		return bankIndicatorDao.getAgentListFromView(bankIndicatorId);
	}
	

}
