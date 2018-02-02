/**
 * "@author Arul JayaSingh
 */
package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.treasury.dao.IBankBranchDetailsDao;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.util.AMGException;


/**
 * @author exim07
 *
 */
@SuppressWarnings("serial")
@Service("bankBranchDetailsServiceImpl")
public class BankBranchDetailsServiceImpl<T> implements IBankBranchDetailsService<T>, Serializable {

	
	@Autowired
	IBankBranchDetailsDao<T> iBankBranchDetailsDao;
	
	public IBankBranchDetailsDao<T> getiBankBranchDetailsDao() {
		return this.iBankBranchDetailsDao;
	}

	public void setiBankBranchDetailsDao(IBankBranchDetailsDao<T> iBankBranchDetailsDao) {
		this.iBankBranchDetailsDao = iBankBranchDetailsDao;
	}

	@Transactional
	public void saveBankBranchDetail(BankBranch bankBranch) {
		getiBankBranchDetailsDao().saveBankBranchDetail(bankBranch);
	}

	@Transactional
	@Override
	public List<BankBranch> getBankBranchList() {
		return getiBankBranchDetailsDao().getBankBranchList();
	}

	@Transactional
	@Override
	public List<BankBranch> getBankBranchByBranchCode(String branchCode) {
		return getiBankBranchDetailsDao().getBankBranchByBranchCode(branchCode);
	}

	@Transactional
	@Override
	public List<BankBranch> getBankBranchByBranchID(BigDecimal branchID) {
		return getiBankBranchDetailsDao().getBankBranchByBranchID(branchID);
	}

	@Transactional
	@Override
	public List<BankMaster> getBankList() {
		return getiBankBranchDetailsDao().getBankList();
	}

	@Transactional
	@Override
	public List<BankBranch> checkBranchCode(String BranchCode) {
		return getiBankBranchDetailsDao().checkBranchCode(BranchCode);
	}
	
	@Transactional
	@Override
	public List<BankBranch> getData(BigDecimal bankid, BigDecimal branchcode) {
		return getiBankBranchDetailsDao().getData(bankid,branchcode);
	}

	@Transactional
	@Override
	public List<BankMaster> getCountrybyBank(BigDecimal bankid) {
		return getiBankBranchDetailsDao().getCountrybyBank(bankid);
	}

	@Transactional
	@Override
	public List<BankBranch> getBranchCodebyBank(BigDecimal bankid) {
		return getiBankBranchDetailsDao().getBranchCodebyBank(bankid);
	}
	
	@Transactional
	@Override
	public List<String> checkSwift(String swiftBic) {		
		return getiBankBranchDetailsDao().checkSwift(swiftBic);
	}

	@Transactional
	@Override
	public List<BankMaster> getIFSCLength(BigDecimal bankId) {
		return getiBankBranchDetailsDao().getIFSCLength(bankId);
	}

	@Override
	@Transactional
	public List<BankBranchView> getBranchListfromView(BigDecimal bankId) {
		return  getiBankBranchDetailsDao().getBranchListfromView(bankId);
	}

	@Override
	@Transactional
	public List<BankBranch> checkIfsc(String branchIfc) {
		return getiBankBranchDetailsDao().checkIfsc(branchIfc);
	}
	
	@Override
	@Transactional
	public List<BankBranch> checkIfscByBank(BigDecimal bankId, String branchIfc) {
		return getiBankBranchDetailsDao().checkIfscByBank(bankId, branchIfc);
	}

	@Override
	@Transactional
	public HashMap<String, String> callPopulateBankBranch(HashMap<String, String> inputValues) throws AMGException {
		return getiBankBranchDetailsDao().callPopulateBankBranch(inputValues);
	}

	@Override
	@Transactional
	public BigDecimal toFetchMaxBranchCodeOfBranchFromBank(BigDecimal bankID) {
		return getiBankBranchDetailsDao().toFetchMaxBranchCodeOfBranchFromBank(bankID);
	}

	@Override
	@Transactional
	public void toApproveRecordsBankBranch(BigDecimal pkBankBranch,String userName) {
		getiBankBranchDetailsDao().toApproveRecordsBankBranch(pkBankBranch, userName);
	}

	@Override
	@Transactional
	public String toFetchBranchName(BigDecimal bankBranchId) {
		return getiBankBranchDetailsDao().toFetchBranchName(bankBranchId);
	}

	@Override
	@Transactional
	public List<BankBranchView> toFetchAllDetailsFromBankBranch(BigDecimal bankId, BigDecimal bankBranchId) {
		return getiBankBranchDetailsDao().toFetchAllDetailsFromBankBranch(bankId, bankBranchId);
	}

	@Override
	@Transactional
	public void unapproveProcedureExcep(BigDecimal pkBankBranch) {
		getiBankBranchDetailsDao().unapproveProcedureExcep(pkBankBranch);
		
	}

}
