package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;

public interface IBankMasterDao<T> {

	public void saveBankMasterInfo(BankMaster bankMasterinfo);
	public List<BankMaster> getBankMasterInfo(String bankCode);	
	
	public List<BankMaster> getBankMasterInfoForApproval(String bankCode);
	
	public List<BankMaster> getAllBankMasterInfo(String bankCode);	
	
	public List<BankMaster> getBankMasterInfoById(BigDecimal bankId);
	
	public void saveAll(HashMap<String, Object> saveMap)throws Exception;
	
	public HashMap<String, String> callPopulateBankMaster(HashMap<String, String> inputValues) throws AMGException ;
	
	//01/09/2016
	public void procErrorToUnApprove(BigDecimal bankIdInternal,List<BigDecimal> contactList, List<BigDecimal> lengthLst);
	
	public List<BankMaster> fetchBankServiceProvider();
}
