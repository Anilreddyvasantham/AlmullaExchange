package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.BankBranchDataTable;
import com.amg.exchange.treasury.dao.IBankApprovalDao;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.util.AMGException;

@Service
public class BankApprovalServiceImpl implements IBankApprovalService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	IBankApprovalDao bankApprovalDao;

	@Override
	@Transactional
	public List<BankMaster> getBankListForApproval() {
		return bankApprovalDao.getBankListForApproval();
	}

	@Override
	@Transactional
	public List<BankBranch> getBankBranchListForApproval() {
		return bankApprovalDao.getBankBranchListForApproval();
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankApplicabilityListForApproval() {
		return bankApprovalDao.getBankApplicabilityListForApproval();
	}

	@Override
	@Transactional
	public List<BankAccount> getBankAccountListForApproval() {
		return bankApprovalDao.getBankAccountListForApproval();
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountDetailListForApproval() {
		return bankApprovalDao.getBankAccountDetailListForApproval();
	}

	@Override
	@Transactional
	public List<BankAccountLength> getBankAccountLengthListForApproval() {
		return bankApprovalDao.getBankAccountLengthListForApproval();
	}
	
	@Override
	@Transactional
	public List<BankMaster> getBankListForActiveInactive(BigDecimal countryId,BigDecimal bankId) {
		return bankApprovalDao.getBankListForActiveInactive(countryId,bankId);
	}
	
	@Override
	@Transactional
	public List<BankBranch> getBankBranchListForActiveInactive(BigDecimal bankId, String  branchFullName,String ifscCode) {
		return bankApprovalDao.getBankBranchListForActiveInactive(bankId,branchFullName,ifscCode);
	}
	
	@Override
	@Transactional
	public List<BankApplicability> getBankApplicabilityListForActiveInactive() {
		return bankApprovalDao.getBankApplicabilityListForActiveInactive();
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountForApproval() {
		return bankApprovalDao.getBankAccountForApproval();
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountForActiveInactive(BigDecimal countryId,BigDecimal bankId) {
		return bankApprovalDao.getBankAccountForActiveInactive(countryId,bankId);
	}
	
	@Override
	@Transactional
	public void upDateRemarks(String remarks,BigDecimal branchPk,String userName) {
		bankApprovalDao.upDateRemarks(remarks,branchPk,userName);
	}
	
	
	@Override
	@Transactional
	public void activateRecord(BigDecimal branchPk,String userName) {
	   bankApprovalDao.activateRecord(branchPk,userName);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public String branchActivateDeActivatemoveJavatoEMOS(HashMap<String, Object> activateDeactiveRec) throws AMGException{
		return bankApprovalDao.branchActivateDeActivatemoveJavatoEMOS(activateDeactiveRec);
	}

	@Override
	@Transactional
	public List<BankBranch> toSearchBranchName(BigDecimal countryId,BigDecimal bankID,String searchBranchName, String searchBranchIfsc,String searchSwiftIfsc) {
		return bankApprovalDao.toSearchBranchName(countryId,bankID, searchBranchName, searchBranchIfsc, searchSwiftIfsc);
	}

	@Override
	@Transactional
	public void bulkApprovalForBankBranch(List<BankBranchDataTable> bankBranchDetails) {
		bankApprovalDao.bulkApprovalForBankBranch(bankBranchDetails);
	}

	
	
}
