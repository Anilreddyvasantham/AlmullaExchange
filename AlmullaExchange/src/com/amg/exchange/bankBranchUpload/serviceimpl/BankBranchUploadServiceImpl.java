package com.amg.exchange.bankBranchUpload.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.bankBranchUpload.bean.BankBranchUploadExcelList;
import com.amg.exchange.bankBranchUpload.dao.BankBranchUploadDAO;
import com.amg.exchange.bankBranchUpload.service.BankBranchUploadService;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.model.BankMaster;

@Service("bankBranchUploadServiceImpl")
public class BankBranchUploadServiceImpl implements BankBranchUploadService { 

	@Autowired
	BankBranchUploadDAO bankBranchUploadDAO;

	@Override
	@Transactional
	public List<CountryMasterDesc> getAllCountryList(BigDecimal langId) {
		return bankBranchUploadDAO.getAllCountryList(langId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankList(BigDecimal countryId) {
		return bankBranchUploadDAO.getBankList(countryId);
	}

	@Override
	@Transactional
	public String getBankCode(BigDecimal bankid) {
		return bankBranchUploadDAO.getBankCode(bankid);
	}

	@Override
	@Transactional
	public HashMap<String, List<BankBranchUploadExcelList>> checkAndSaveOrUpdateBankBranch(BigDecimal countryId,BigDecimal bankId,List<BankBranchUploadExcelList> neftBranchUploadExcelList,BigDecimal langId,String userName) {
		return bankBranchUploadDAO.checkAndSaveOrUpdateBankBranch(countryId,bankId,neftBranchUploadExcelList,langId,userName);
	}

}
