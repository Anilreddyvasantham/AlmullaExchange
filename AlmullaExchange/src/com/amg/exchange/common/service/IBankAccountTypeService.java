package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.dto.BankAccountTypeDTO;
import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;

public interface IBankAccountTypeService {

	public List<BankAccountType> getAllRecordsForApproval();
	public List<BankAccountTypeDesc> getallRecordsRelatedBankAccountType(BigDecimal bankAccountTypeId);
	public void saveRecords(BankAccountType bankAccountType,BankAccountTypeDesc bankAccountTypedesc1,BankAccountTypeDesc bankAccountTypedesc2)throws Exception;
	public String approveReocrd(BigDecimal bankaccountTypePk,String userName);
	public List<BankAccountType> viewAllRecords();
	public void deleteRecordPermanently(BigDecimal accountTypePk,BigDecimal accountTypedescPk,BigDecimal accountTypeLocalDescPk);
	public List<String> getServiceCodeList(String query);
	public List<BankAccountType> fetchAllRecords(String accountTypeCode);
	public void activateRecord(BigDecimal bankaccountTypePk,String userName);
	
	public void saveAllRecords(BankAccountTypeDTO bankAccountTypeVOList)throws Exception;
	
	public List<BankAccountTypeDTO> viewRecords();
	public List<BankAccountTypeDTO> getAllRecordsRelatedBankAccountType(BigDecimal bankAccountTypeId);
	
	public List<BankAccountTypeDTO> fetchAccTypeCodeRecord(String accountTypeCode);

	
}
