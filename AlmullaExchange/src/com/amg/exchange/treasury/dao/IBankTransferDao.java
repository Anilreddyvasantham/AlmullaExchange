package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.util.AMGException;

public interface IBankTransferDao<T> {

	public  List<BankMaster> getOtherBankList(BigDecimal bankID);
	public TreasuryDealHeader getPopulateRecord(BigDecimal documentNo,BigDecimal documentID ,BigDecimal financeYear,BigDecimal companyId);
	public List<TreasuryDealDetail> getTreasuryDealDetailInfo(BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId);
	public List<TreasuryStandardInstruction>  getTreasuryStandardInstructionInfo(BigDecimal documentNo,BigDecimal documentID,BigDecimal treasuryDtlId,BigDecimal companyId);
	public List<StandardInstruction>  getStandardInstructionInfo(BigDecimal documentNo);
	public List<StandardInstruction>  getStandardInstructionNumber(BigDecimal standInstructionId);
	public  List<BankMaster> getBankListWithCurrency(BigDecimal bankID,BigDecimal currencyID);
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryID,BigDecimal bankID,BigDecimal currencyID);

	//Bank transfer approval update
	public void bankTransferApprorval(Map<String ,Object> bnkTrsfrApproval,String userName);

	public String getAmountNameInWords(String arg1, String arg2, BigDecimal arg3, BigDecimal arg4);
	public List<TreasuryDealDetail> getAllRecordsOfBankTransfer(BigDecimal treasuryHeadId);
	public List<TreasuryDealHeader> getAllRecordsFromHeader();
	public List<TreasuryStandardInstruction> getTreasuryStandardInstruction(BigDecimal documentYear, BigDecimal documentNo, BigDecimal treasuryDetailsId);

	//get primarykey from bank account details table based on FA Account Number
	public BigDecimal getBankAccountDeatilsPk(String faAccountNo);

	public BigDecimal getAccountBalancePk(String glaccountNo);

	public String getAccountNoBasedOnGlSlNumber(String glslno);
	public List<BankAccountDetails> getAccountDetailsListBasedOnSelectedCurrency(List<BigDecimal> bankList,BigDecimal currencyId);
	public void callProcedureForBankTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId, BigDecimal financialYear, BigDecimal documentNumber)throws AMGException;

	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForBankTransfer(String status,BigDecimal financeYear,BigDecimal documentId,BigDecimal companyId);

	public void saveBankTransferFrom(BigDecimal treasuryBrFromPk,List<BigDecimal> lstTreasuryStdBrFromPk, String userName);
	public List<TreasuryDealHeaderDTO> getBankTransferEnqSelectDate(
			Date documentDate);
	
	public void deleteBrantransferRecords(BigDecimal treasuryHeaderPk, BigDecimal treasuryBrFromPk, List<BigDecimal> lstTreasuryStdBrFromPk, List<BigDecimal> lstTreasuryDetailPk, List<BigDecimal> lstTreasuryStdBrToPk,String userName)throws Exception;
	public void deleteTreasuryStandardInstruction(BigDecimal documentYear,BigDecimal documentNo, BigDecimal treasuryDetailsId);
	public void deleteTreasuryDetaill(BigDecimal treasuryDetailPk ,String UserName,BigDecimal documentNo,BigDecimal documentID,BigDecimal financeYear,BigDecimal companyId);
}
