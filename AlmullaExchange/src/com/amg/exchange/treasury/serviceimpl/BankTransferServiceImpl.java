package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankTransferDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("bankTransferServiceImpl")
public class BankTransferServiceImpl<T> implements IBankTransferService<T> , Serializable {

	@Autowired
	IBankTransferDao<T>  bankTransferDao;

	@Transactional
	@Override
	public List<BankMaster> getOtherBankList(BigDecimal bankID) {
		return bankTransferDao.getOtherBankList(bankID);
	}

	@Override
	@Transactional
	public TreasuryDealHeader getPopulateRecord(BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId) {
		return bankTransferDao.getPopulateRecord(documentNo,documentID,financeYear,companyId);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getTreasuryDealDetailInfo(BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId) {
		return bankTransferDao.getTreasuryDealDetailInfo(documentNo,documentID,financeYear,companyId);
	}

	@Override
	@Transactional
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionInfo(
			BigDecimal documentNo,BigDecimal documentID,BigDecimal treasuryDtlId,BigDecimal companyId) {
		return bankTransferDao.getTreasuryStandardInstructionInfo(documentNo,documentID,treasuryDtlId,companyId);
	}

	@Override
	@Transactional
	public List<StandardInstruction> getStandardInstructionInfo(
			BigDecimal documentNo) {
		return bankTransferDao.getStandardInstructionInfo(documentNo);
	}

	@Override
	@Transactional
	public List<StandardInstruction> getStandardInstructionNumber(BigDecimal standInstructionId) {
		return bankTransferDao.getStandardInstructionNumber(standInstructionId);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankListWithCurrency(BigDecimal bankID,
			BigDecimal currencyID) {
		return bankTransferDao.getBankListWithCurrency(bankID, currencyID);
	}

	@Override
	@Transactional
	public List<BankApplicability> getCorrespondingLocalFundingBanks(
			BigDecimal countryID, BigDecimal bankID, BigDecimal currencyID) {
		
		return bankTransferDao.getCorrespondingLocalFundingBanks(countryID, bankID, currencyID);
	}

	@Override
	@Transactional
	public void bankTransferApprorval(Map<String, Object> bnkTrsfrApproval,String userName) {
		
		bankTransferDao.bankTransferApprorval(bnkTrsfrApproval,userName);
	}
	
	public String getAmountNameInWords(String arg1, String arg2, BigDecimal arg3, BigDecimal arg4){
		return bankTransferDao.getAmountNameInWords(arg1,arg2, arg3, arg4);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getAllRecordsOfBankTransfer(BigDecimal treasuryHeaderId) {
		 
		return bankTransferDao.getAllRecordsOfBankTransfer(treasuryHeaderId);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getAllRecordsFromHeader() {
		 
		return bankTransferDao.getAllRecordsFromHeader();
	}

	@Override
	@Transactional
	public List<TreasuryStandardInstruction> getTreasuryStandardInstruction(BigDecimal documentYear,BigDecimal documentNo, BigDecimal treasuryDetailsId) {
		return bankTransferDao.getTreasuryStandardInstruction(documentYear,documentNo, treasuryDetailsId);
	}
	
	@Override
	@Transactional
	public BigDecimal getBankAccountDeatilsPk(String faAccountNo){
		return bankTransferDao.getBankAccountDeatilsPk(faAccountNo);
	}
	
	@Override
	@Transactional
	public BigDecimal getAccountBalancePk(String glslNo){
		return bankTransferDao.getAccountBalancePk(glslNo);
	}
	
	@Override
	@Transactional
	public String getAccountNoBasedOnGlSlNumber(String glslno){
		return bankTransferDao.getAccountNoBasedOnGlSlNumber(glslno);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getAccountDetailsListBasedOnSelectedCurrency(List<BigDecimal> bankList,BigDecimal currencyId){
		return bankTransferDao.getAccountDetailsListBasedOnSelectedCurrency(bankList,currencyId);
	}
	

	@Transactional
	@Override
	public void callProcedureForBankTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId, BigDecimal financialYear, BigDecimal documentNumber)throws AMGException{
		bankTransferDao.callProcedureForBankTransfer(applicationCountryId, companyId, documentId, financialYear, documentNumber);
	}
	
	  @Override
	  @Transactional
	  public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForBankTransfer(String status,BigDecimal financeYear,BigDecimal documentId,BigDecimal companyId) {
		    return bankTransferDao.fetchDocumentNumberFromTreasDealheaderForBankTransfer(status, financeYear, documentId, companyId);
	  }

	@Override
	@Transactional
	public void saveBankTransferFrom(BigDecimal treasuryBrFromPk,
			List<BigDecimal> lstTreasuryStdBrFromPk, String userName) {
		bankTransferDao.saveBankTransferFrom(treasuryBrFromPk, lstTreasuryStdBrFromPk, userName);
		
	}

	@Override
	@Transactional
	public List<TreasuryDealHeaderDTO> getBankTransferEnqSelectDate(
			Date documentDate) {
 
		return bankTransferDao.getBankTransferEnqSelectDate(documentDate);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBrantransferRecords(BigDecimal treasuryHeaderPk,
			BigDecimal treasuryBrFromPk,
			List<BigDecimal> lstTreasuryStdBrFromPk,
			List<BigDecimal> lstTreasuryDetailPk,
			List<BigDecimal> lstTreasuryStdBrToPk, String userName) throws Exception{
		bankTransferDao.deleteBrantransferRecords(treasuryHeaderPk, treasuryBrFromPk, lstTreasuryStdBrFromPk, lstTreasuryDetailPk, lstTreasuryStdBrToPk, userName);
		
	}

	@Override
	@Transactional
	public void deleteTreasuryStandardInstruction(BigDecimal documentYear,
			BigDecimal documentNo, BigDecimal treasuryDetailsId) {
		bankTransferDao.deleteTreasuryStandardInstruction(documentYear, documentNo, treasuryDetailsId);
		
	}

	@Override
	@Transactional
	public void deleteTreasuryDetaill(BigDecimal treasuryDetailPk ,String UserName,BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId) {
		bankTransferDao.deleteTreasuryDetaill(treasuryDetailPk, UserName, documentNo, documentID, financeYear, companyId);;
	}
	
}
