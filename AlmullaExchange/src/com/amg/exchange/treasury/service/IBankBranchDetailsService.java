/**
 * "@author Arul JayaSingh
 */
package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.AMGException;


/**
 * @author exim07
 *
 */
public interface IBankBranchDetailsService<T> {
	
	public void saveBankBranchDetail(BankBranch bankBranch);
	
	public List<BankBranch> getBankBranchList();
	
	public List<BankBranch> getBankBranchByBranchCode(String branchCode);
	
	public List<BankBranch> getBankBranchByBranchID(BigDecimal branchID);
	
	public List<BankMaster> getBankList();
	
	public List<BankBranch> checkBranchCode(String branchCode);
	
	public List<BankMaster> getCountrybyBank(BigDecimal bankid);
	
	public List<BankBranch> getData(BigDecimal bankid, BigDecimal branchcode);
	
	public List<BankBranch> getBranchCodebyBank(BigDecimal bankid);
	
	public List<BankMaster> getIFSCLength(BigDecimal bankId);
	
	public List<String> checkSwift(String swiftBic);
	
	public List<BankBranchView> getBranchListfromView(BigDecimal bankId);
	
	public List<BankBranch> checkIfsc(String branchIfc);
	
	public List<BankBranch> checkIfscByBank(BigDecimal bankId,String branchIfc);
	
	public HashMap<String, String> callPopulateBankBranch(HashMap<String, String> inputValues) throws AMGException;
	
	public BigDecimal toFetchMaxBranchCodeOfBranchFromBank(BigDecimal bankID);
	
	public void toApproveRecordsBankBranch(BigDecimal pkBankBranch,String userName);
	
	public String toFetchBranchName(BigDecimal bankBranchId);
	
	public List<BankBranchView> toFetchAllDetailsFromBankBranch(BigDecimal bankId, BigDecimal bankBranchId);
	
	public void unapproveProcedureExcep(BigDecimal pkBankBranch);
	
}
