package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryCustomerDeal;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.model.TreasuryDealRegisterView;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewTreasuryDeal;
import com.amg.exchange.util.AMGException;

public interface IFXDetailInformationService<T> {

	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId);
	public List<CurrencyMaster> getCurrencyList();

	//CR getting Instruction Number and Description from DB
	public List<StandardInstruction> getInstrnNumberDesc(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId,String instrnNumber);
	public List<StandardInstruction> getInstrnNumber(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId);
	public List<StandardInstructionDetails> getInstructionsFromDetails(BigDecimal bankId, BigDecimal currencyId, String isActive,
			BigDecimal instrnNumber ,BigDecimal bankAccDetId,String instrnType);
	//Getting Banklist from Bank Master except local Bank
	public List<BankMaster> getDealBank(BigDecimal Id);
	//save EX_TREASURY_DEAL_HEADER
	public void saveHeader(TreasuryDealHeader treasuryDealHeader);
	public void savePurchase(TreasuryDealDetail treasuryDetailPurchase);
	public void saveSale(TreasuryDealDetail treasuryDetailSale);
	public void savePurchaseStandardInst(TreasuryStandardInstruction standardInstructionPurchase);
	public void saveSaleStandardInst(TreasuryStandardInstruction standardInstructionSale);
	//public List<TreasuryDealHeader> getDealHeaderData();

	//CR getting Purchase Requirement for Special Pool
	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReq(String fundingOptionValue,BigDecimal bankId,BigDecimal currencyId);
	public List<CustomerSpecialDealRequest> getFCAmountOfCommonPool(String fundingOptionValue);
	public List<AccountBalance> getSaleAvgRate(BigDecimal bankId,BigDecimal currencyId,String accountNo);

	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReqAccCurrency(String fundingOptionValue,BigDecimal currencyId) throws ParseException;

	public List<TreasuryDealHeader> getPopulateRecord(BigDecimal refNumber);
	
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,BigDecimal refNumber,String dealWithType,BigDecimal documentYear,BigDecimal companyId);
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,BigDecimal companyId, BigDecimal dealYear, String dealWithType);
	
	public List<TreasuryDealDetail> getDetailsPurchase(BigDecimal headerId,String lineType);
	public List<TreasuryDealDetail> getDetailsSpecialPool(BigDecimal headerId,String lineType,BigDecimal documentNo,BigDecimal documentYear,BigDecimal companyId);
	public List<TreasuryDealDetail> getDetailsSale(BigDecimal headerId,String lineType);
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForPurchase(BigDecimal detailsId,String lineType,Boolean valueCheck);
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForSale(BigDecimal detailsId,String lineType,Boolean valueCheck);

	//CR getting User Financial Year Begin Date from DB
	public String getUserFinancialBeginDate(Date currentDate) throws ParseException;

	//for getting Currency
	public List<BankAccountDetails> getCurrencyBasedOnBankCountry(BigDecimal bankId);
	//for getting Bank Country Based on bank Id
	public List<BankAccountDetails> getBankCountryBasedOnBank(BigDecimal bankId);
	//for getting bank is active "Y" or not
	public List<RoutingHeader> checkBankisActiveinRoutingSetupMaster(BigDecimal bankcountryId,BigDecimal bankId,BigDecimal currencyId);
	public List<CustomerSpecialDealRequest> getCustomerSpecialDealRequestFromID(BigDecimal custspreqstID);

	//saving all Fx_Deal Bank with rollback
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo)throws Exception;
	public void saveUnApprovedGLEntry(TreasuryDealHeader treasuryDealHeader)throws Exception ;
	//fetch all unapproved documents numbers from treasury deal header
	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheader(String Fx_BankDealType, String status,BigDecimal documentYear);

	//added by nazish for approval on 28-May-2018

	public List<TreasuryDealHeader> fetchDocumentNumber();
	public void approveHeader(BigDecimal treasuryDealHeader, String userName,Date currentDate);
	public void approveDetailsPurchase(BigDecimal treasuryDealDetail, String userName,Date currentDate);
	public void approveStandardInstructionForPurchase(BigDecimal standardInstunction, String userName,Date currentDate);
	public void approveStandardInstructionForsale(BigDecimal standardInstunction, String userName,Date currentDate);
	public void approveDetailsSale(BigDecimal treasuryDealDetail, String userName,Date currentDate);

	public List<ViewTreasuryDeal> viewTreauryDealwithBank(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType);

	public List<ViewTreasuryDeal> viewTreauryDealwithBankTransfer(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType);

	//Server Side validation to check deal id exist or not
	public HashMap<String, Object> ValidateDealID(String dealID,Date dealDate);

	public HashMap<String, Object> ValidateDealIDWhileUpdate(BigDecimal documentNumber,String dealID,Date dealDate);

	//Added Enquiry 18/06/15 @Koti
	public List<TreasuryDealDetail> getAllDetailsFromDb(BigDecimal treasuryDealHeaderId);
	public List<TreasuryDealHeader> getAllDetailsFromDbHeader();
	

	public List<TreasuryDealHeaderDTO> getfxdealEnqSelectDate(Date documentDate);
	public void callApproveProcedure(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId,BigDecimal financialYear,BigDecimal documentNumber );
	//to get Customer Name
	public String getCustomerName(BigDecimal dealWithCustomer);
	public void callUnApproveProcedure(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId,BigDecimal financialYear,BigDecimal documentNumber ) throws AMGException;

	//to fetch the corresponding bank spl customer deal
	public List<CustomerSpecialDealRequest> getSplDealFromCustomerSplDeal(BigDecimal bankId , String fundingOptionValue , BigDecimal currencyId);

	//to be added for Enq Purpose
	public List<TreasuryDealHeader> getHeaderDetailsList(BigDecimal documentNo,BigDecimal refNumber,String dealWithType);
	public List<TreasuryDealHeaderDTO> getfxdealSupplierEnqSelectDate(Date documentDate);

	public List<DayBookHeader> getAllDetailsListFromDB(BigDecimal documentNo);
	public List<DayBookDetails> getAllDetailsDayBookDetails(BigDecimal dayBookHeaderId, String lineType);
	public List<BankAccountDetails> getAccountNumberBasedOnBank(String accountNumber);
	public List<AccountBalance> getBankBanceBasedOnAccNO(String saleAccountNumber);

	// Services For FX Deal With Approval screen
	public List<TreasuryDealHeader> getfxDealWithSupplierUnApprovedRecords();

	public List<TreasuryDealHeader> fecthTreasurydealHeaderRecordsForSupplierApprove(BigDecimal documentNo,String Fx_BankDealType, String status,BigDecimal documentYear);
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPD(BigDecimal tresuryDealHeaderPk);
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPY(BigDecimal tresuryDealHeaderPk);
	public List<DayBookHeader> fecthDayBookHeaderForSupplierApprove(BigDecimal payMentVocherNumber);
	public List<DayBookDetails> fecthDayBookDetailsRecordsForSD(BigDecimal dayBookHeaderPk);
	public List<DayBookDetails> fecthDayBookDetailsRecordsForPY(BigDecimal dayBookHeaderPk);

	public void approveRecords(String userName,BigDecimal treasuryHeaderPk,BigDecimal dayBookHeaderPk,
			List<BigDecimal> treasuryDealDetailPkList, List<BigDecimal> dayBookDealDetailsPkList)throws Exception;

	public String getAccountNoBasedOnAccDetId(BigDecimal accDetId);
	public List<StandardInstruction> getValues(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,BigDecimal accountDetId,String instructionType);
	
	// treasury deal register Inquiry
	public List<TreasuryDealRegisterView> fetchTreasuryDealRegisterFromView(HashMap<String, Object> lstTreasuryDealRegister);
	
	public String getSplitIndicatorFromBankMaster(BigDecimal  bankId);
	
	public List<TreasuryCustomerDeal> getTreasuryCustomerDealAndPaymentValues(BigDecimal companyId, BigDecimal documentId, BigDecimal documentFinaceYear, BigDecimal documentNo);
	public void deleteAllFXDealBank(HashMap<String, Object> saveMapInfo,String userName) throws Exception;
	
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryId);
	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForSupplier(String Fx_BankDealType, String status,BigDecimal documentYear);
	
	
	//Anil (19/01/2018)
	public List<BankMaster> getBankName(BigDecimal bankId);
	
}
