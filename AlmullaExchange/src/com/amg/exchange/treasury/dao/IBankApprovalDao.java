package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.bean.BankBranchDataTable;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;

public interface IBankApprovalDao {

	public List<BankMaster> getBankListForApproval();
	
	public List<BankMaster> getBankListForActiveInactive(BigDecimal countryId,BigDecimal bankId);

	public List<BankBranch> getBankBranchListForApproval();	
	
	public List<BankBranch> getBankBranchListForActiveInactive(BigDecimal bankId, String  branchFullName,String ifscCode);	

	public List<BankAccountLength> getBankAccountLengthListForApproval();
	
	public List<BankApplicability> getBankApplicabilityListForApproval();
	
	public List<BankApplicability> getBankApplicabilityListForActiveInactive();
	
	public List<BankAccount> getBankAccountListForApproval();
	
	public List<BankAccountDetails> getBankAccountDetailListForApproval();
	
	public List<BankAccountDetails> getBankAccountForApproval();
	
	public List<BankAccountDetails> getBankAccountForActiveInactive(BigDecimal countryId,BigDecimal bankId);
	
	public void upDateRemarks(String remarks,BigDecimal branchPk,String userName);
	
	public void activateRecord(BigDecimal branchPk,String userName);
	
	public String branchActivateDeActivatemoveJavatoEMOS(HashMap<String, Object> activateDeactiveRec) throws AMGException;
	
	public List<BankBranch> toSearchBranchName(BigDecimal countryId,BigDecimal bankID,String searchBranchName, String searchBranchIfsc,String searchSwiftIfsc);
	
	public void bulkApprovalForBankBranch(List<BankBranchDataTable> bankBranchDetails);

}
