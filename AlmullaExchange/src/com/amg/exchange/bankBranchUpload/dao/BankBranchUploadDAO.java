package com.amg.exchange.bankBranchUpload.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.bankBranchUpload.bean.BankBranchUploadExcelList;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.model.BankMaster;

public interface BankBranchUploadDAO { 
	
	public List<CountryMasterDesc> getAllCountryList(BigDecimal langId);

	public List<BankMaster> getBankList(BigDecimal countryId);
	
	public String getBankCode(BigDecimal bankid);

	public HashMap<String, List<BankBranchUploadExcelList>> checkAndSaveOrUpdateBankBranch(BigDecimal countryId,BigDecimal bankId,List<BankBranchUploadExcelList> neftBranchUploadExcelList,	BigDecimal langId,String userName);
}
