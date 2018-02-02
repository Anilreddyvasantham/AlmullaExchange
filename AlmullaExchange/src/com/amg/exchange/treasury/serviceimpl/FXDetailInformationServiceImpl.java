package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.dao.IFXDetailInformationDao;
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
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("fXDetailInformationServiceImpl")
public class FXDetailInformationServiceImpl<T> implements IFXDetailInformationService<T>, Serializable{

	@Autowired
	IFXDetailInformationDao<T> fXDetailInformationDao;

	@Override
	@Transactional
	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId) {

		return fXDetailInformationDao.getCompanyList(languageId);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {
		
		return fXDetailInformationDao.getCurrencyList();
	}
	
	//CR getting Instruction Number and Description from DB 
	
	@Transactional
	@Override
	public List<StandardInstruction> getInstrnNumberDesc(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId,String instrnNumber) {
		return fXDetailInformationDao.getInstrnNumberDesc(bankId, currencyId,bankAccountDetId, instrnNumber);
	}
	
	@Transactional
	@Override
	public List<StandardInstruction> getInstrnNumber(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId) {
		return fXDetailInformationDao.getInstrnNumber(bankId, currencyId,bankAccountDetId);
	}
	
	@Transactional
	@Override
	public List<StandardInstructionDetails> getInstructionsFromDetails(BigDecimal bankId, BigDecimal currencyId, String isActive,BigDecimal instrnNumber ,BigDecimal bankAccDetId,String instrnType) {
		return fXDetailInformationDao.getInstructionsFromDetails(bankId, currencyId, isActive, instrnNumber, bankAccDetId, instrnType);
	}
	
	@Transactional
	@Override
	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReq(String fundingOptionValue,BigDecimal bankId,BigDecimal currencyId) {
		return fXDetailInformationDao.getDataTableFromCustomerSpecialDealReq(fundingOptionValue,bankId,currencyId);
	}
	
	@Transactional
	@Override
	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReqAccCurrency(String fundingOptionValue,BigDecimal currencyId) throws ParseException {
		return fXDetailInformationDao.getDataTableFromCustomerSpecialDealReqAccCurrency(fundingOptionValue,currencyId);
	}
	
	@Transactional
	@Override
	public List<CustomerSpecialDealRequest> getFCAmountOfCommonPool(String fundingOptionValue) {
		return fXDetailInformationDao.getFCAmountOfCommonPool(fundingOptionValue);
	}
	
	@Transactional
	@Override
	public List<BankMaster> getDealBank(BigDecimal Id) {
		return fXDetailInformationDao.getDealBank(Id);
	}
	
	@Transactional
	@Override
	public void saveHeader(TreasuryDealHeader treasuryDealHeader) {
		fXDetailInformationDao.saveHeader(treasuryDealHeader);
	}
	
	@Transactional
	@Override
	public void savePurchase(TreasuryDealDetail treasuryDetailPurchase) {
		fXDetailInformationDao.savePurchase(treasuryDetailPurchase);
	}
	
	@Transactional
	@Override
	public void saveSale(TreasuryDealDetail treasuryDetailSale) {
		fXDetailInformationDao.saveSale(treasuryDetailSale);	
	}
	
	@Transactional
	@Override
	public void savePurchaseStandardInst(TreasuryStandardInstruction standardInstructionPurchase) {
		fXDetailInformationDao.savePurchaseStandardInst(standardInstructionPurchase);
	}
	
	@Transactional
	@Override
	public void saveSaleStandardInst(TreasuryStandardInstruction standardInstructionSale) {
	 fXDetailInformationDao.saveSaleStandardInst(standardInstructionSale);
	}
	
	@Transactional
	@Override
	public List<AccountBalance> getSaleAvgRate(BigDecimal bankId,BigDecimal currencyId, String accountNo) {
		return fXDetailInformationDao.getSaleAvgRate(bankId, currencyId, accountNo);
	}
	
	@Override
	@Transactional
	public List<TreasuryDealHeader> getPopulateRecord(BigDecimal refNumber) {
		return fXDetailInformationDao.getPopulateRecord(refNumber);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,BigDecimal refNumber,String dealWithType,BigDecimal documentYear,BigDecimal companyId) {
		return fXDetailInformationDao.getHeaderDetails(documentNo,refNumber,dealWithType,documentYear,companyId);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getDetailsPurchase(BigDecimal headerId,String lineType) {
		return fXDetailInformationDao.getDetailsPurchase(headerId, lineType);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getDetailsSpecialPool(BigDecimal headerId,String lineType,BigDecimal documentNo,BigDecimal documentYear,BigDecimal companyId) {
		return fXDetailInformationDao.getDetailsSpecialPool(headerId, lineType,documentNo,documentYear,companyId);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getDetailsSale(BigDecimal headerId,String lineType) {
		return fXDetailInformationDao.getDetailsSale(headerId, lineType);
	}

	@Override
	@Transactional
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForPurchase(BigDecimal detailsId,String lineType,Boolean valueCheck) {
	
		return fXDetailInformationDao.getTreasuryStandardInstructionsForPurchase(detailsId,lineType,valueCheck);
	}

	@Override
	@Transactional
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForSale(BigDecimal detailsId,String lineType,Boolean valueCheck) {

		return fXDetailInformationDao.getTreasuryStandardInstructionsForSale(detailsId,lineType,valueCheck);
	}
	
	@Override
	@Transactional
	public String getUserFinancialBeginDate(Date currentDate) throws ParseException{

		return fXDetailInformationDao.getUserFinancialBeginDate(currentDate);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getCurrencyBasedOnBankCountry(BigDecimal bankId) {
		return fXDetailInformationDao.getCurrencyBasedOnBankCountry(bankId);
	}
	
	@Override
	@Transactional
	public List<BankAccountDetails> getBankCountryBasedOnBank(BigDecimal bankId) {
		return fXDetailInformationDao.getBankCountryBasedOnBank(bankId);
	}
	
	@Override
	@Transactional
	public List<RoutingHeader> checkBankisActiveinRoutingSetupMaster(BigDecimal bankcountryId,BigDecimal bankId,BigDecimal currencyId) {
		return fXDetailInformationDao.checkBankisActiveinRoutingSetupMaster(bankcountryId,bankId,currencyId);
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getCustomerSpecialDealRequestFromID(BigDecimal custspreqstID) {
		return fXDetailInformationDao.getCustomerSpecialDealRequestFromID(custspreqstID);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo)throws Exception {
		// TODO Auto-generated method stub
		 fXDetailInformationDao.saveAllFXDealBank(saveMapInfo);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheader(String Fx_BankDealType, String status,BigDecimal documentYear) {
		return fXDetailInformationDao.fetchDocumentNumberFromTreasDealheader(Fx_BankDealType,status,documentYear);
	}
	
	@Override
	@Transactional
	public List<TreasuryDealHeader> fetchDocumentNumber(){
		return fXDetailInformationDao.fetchDocumentNumber();
	}

	@Override
	@Transactional
	public void approveHeader(BigDecimal treasuryDealHeader, String userName,Date currentDate) {
	   fXDetailInformationDao.approveHeader(treasuryDealHeader, userName, currentDate);	
	}

	@Override
	@Transactional
	public void approveDetailsPurchase(BigDecimal treasuryDealDetail,
			String userName, Date currentDate) {
		fXDetailInformationDao.approveDetailsPurchase(treasuryDealDetail, userName, currentDate);
		
	}

	@Override
	@Transactional
	public void approveStandardInstructionForPurchase(
			BigDecimal standardInstunction, String userName, Date currentDate) {
		fXDetailInformationDao.approveStandardInstructionForPurchase(standardInstunction, userName, currentDate);
		
	}

	@Override
	@Transactional
	public void approveStandardInstructionForsale(
			BigDecimal standardInstunction, String userName, Date currentDate) {
		fXDetailInformationDao.approveStandardInstructionForsale(standardInstunction, userName, currentDate);		
	}

	@Override
	@Transactional
	public void approveDetailsSale(BigDecimal treasuryDealDetail,
			String userName, Date currentDate) {
		fXDetailInformationDao.approveDetailsSale(treasuryDealDetail, userName, currentDate);
		
	}
	
	@Override
	@Transactional
	public List<ViewTreasuryDeal> viewTreauryDealwithBank(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType){
		return fXDetailInformationDao.viewTreauryDealwithBank(documentYear,documentNumber, lineType, dealWithType);
	}
	
	@Override
	@Transactional
	public List<ViewTreasuryDeal> viewTreauryDealwithBankTransfer(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType){
		return fXDetailInformationDao.viewTreauryDealwithBankTransfer(documentYear,documentNumber, lineType, dealWithType);
	}

	@Override
	@Transactional
	public HashMap<String, Object> ValidateDealID(String dealID,Date dealDate) {
		
		return fXDetailInformationDao.ValidateDealID(dealID,dealDate);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getAllDetailsFromDb(BigDecimal treasuryDealHeaderId) {
		return fXDetailInformationDao.getAllDetailsFromDb(treasuryDealHeaderId);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getAllDetailsFromDbHeader() {
		return fXDetailInformationDao.getAllDetailsFromDbHeader();
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,
			BigDecimal companyId, BigDecimal dealYear, String dealWithType) {
		
		return fXDetailInformationDao.getHeaderDetails(documentNo,companyId,dealYear,dealWithType);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeaderDTO> getfxdealEnqSelectDate(Date documentDate) {
		// TODO Auto-generated method stub
		return fXDetailInformationDao.getfxdealEnqSelectDate(documentDate);
	}
	
	@Override
	@Transactional
	public void callApproveProcedure(BigDecimal applicationCountryId,
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal financialYear, BigDecimal documentNumber) {
		
		fXDetailInformationDao.callApproveProcedure(applicationCountryId,companyId,documentId,financialYear,documentNumber);
		
	}

	@Override
	@Transactional
	public String getCustomerName(BigDecimal dealWithCustomer) {
		
		return fXDetailInformationDao.getCustomerName(dealWithCustomer);
	}
	
	@Override
	@Transactional
	public void callUnApproveProcedure(BigDecimal applicationCountryId,
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal financialYear, BigDecimal documentNumber) throws AMGException {
		
		fXDetailInformationDao.callUnApproveProcedure(applicationCountryId,companyId,documentId,financialYear,documentNumber);
		
	}

	@Override
	@Transactional
	public List<CustomerSpecialDealRequest> getSplDealFromCustomerSplDeal(BigDecimal bankId,String fundingOptionValue, BigDecimal currencyId) {
		// TODO Auto-generated method stub
		return fXDetailInformationDao.getSplDealFromCustomerSplDeal(bankId, fundingOptionValue, currencyId);
	}

	@Override
	@Transactional
	public HashMap<String, Object> ValidateDealIDWhileUpdate(BigDecimal documentNumber, String dealID, Date dealDate) {
		// TODO Auto-generated method stub
		return fXDetailInformationDao.ValidateDealIDWhileUpdate(documentNumber, dealID, dealDate);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getHeaderDetailsList(BigDecimal documentNo,BigDecimal refNumber, String dealWithType) {
		return fXDetailInformationDao.getHeaderDetailsList(documentNo,refNumber,dealWithType);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeaderDTO> getfxdealSupplierEnqSelectDate(Date documentDate) {
		return fXDetailInformationDao.getfxdealSupplierEnqSelectDate(documentDate);
	}

	@Override
	@Transactional
	public List<DayBookHeader> getAllDetailsListFromDB(BigDecimal documentNo) {
		return fXDetailInformationDao.getAllDetailsListFromDB(documentNo);
	}

	@Override
	@Transactional
	public List<DayBookDetails> getAllDetailsDayBookDetails(BigDecimal dayBookHeaderId, String lineType) {
		return fXDetailInformationDao.getAllDetailsDayBookDetails(dayBookHeaderId,lineType);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getAccountNumberBasedOnBank(String accountNumber) {
		return fXDetailInformationDao.getAccountNumberBasedOnBank(accountNumber);
	}

	@Override
	@Transactional
	public List<AccountBalance> getBankBanceBasedOnAccNO(String saleAccountNumber) {
		return fXDetailInformationDao.getBankBanceBasedOnAccNO(saleAccountNumber);
	}

	@Override
	@Transactional
	public void saveUnApprovedGLEntry(TreasuryDealHeader treasuryDealHeader) throws Exception{
		
		fXDetailInformationDao.saveUnApprovedGLEntry(treasuryDealHeader);
	}
	
	
	@Override
	@Transactional
	public List<TreasuryDealHeader> getfxDealWithSupplierUnApprovedRecords(){
		return fXDetailInformationDao.getfxDealWithSupplierUnApprovedRecords();
	}
	
	@Override
	@Transactional
	public List<TreasuryDealHeader> fecthTreasurydealHeaderRecordsForSupplierApprove(BigDecimal documentNo,String Fx_BankDealType, String status,BigDecimal documentYear){
		return fXDetailInformationDao.fecthTreasurydealHeaderRecordsForSupplierApprove(documentNo, Fx_BankDealType, status, documentYear);
	}
	
	@Override
	@Transactional
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPD(BigDecimal tresuryDealHeaderPk){
		return fXDetailInformationDao.fecthTreasuryDealDetailRecordsForPD(tresuryDealHeaderPk);
	}
	
	@Override
	@Transactional
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPY(BigDecimal tresuryDealHeaderPk){
		return fXDetailInformationDao.fecthTreasuryDealDetailRecordsForPY(tresuryDealHeaderPk);
	}
	
	@Override
	@Transactional
	public List<DayBookHeader> fecthDayBookHeaderForSupplierApprove(BigDecimal payMentVocherNumber){
		return fXDetailInformationDao.fecthDayBookHeaderForSupplierApprove(payMentVocherNumber);
	}
	
	@Override
	@Transactional
	public List<DayBookDetails> fecthDayBookDetailsRecordsForSD(BigDecimal dayBookHeaderPk){
		return fXDetailInformationDao.fecthDayBookDetailsRecordsForSD(dayBookHeaderPk);
	}
	
	@Override
	@Transactional
	public List<DayBookDetails> fecthDayBookDetailsRecordsForPY(BigDecimal dayBookHeaderPk){
		return fXDetailInformationDao.fecthDayBookDetailsRecordsForPY(dayBookHeaderPk);
	}
	
	
	@Override
	@Transactional (rollbackFor = Exception.class)
	public void approveRecords(String userName,BigDecimal treasuryHeaderPk,BigDecimal dayBookHeaderPk,
			List<BigDecimal> treasuryDealDetailPkList, List<BigDecimal> dayBookDealDetailsPkList)throws Exception{
		fXDetailInformationDao.approveRecords(userName,treasuryHeaderPk,dayBookHeaderPk,treasuryDealDetailPkList,dayBookDealDetailsPkList);
	}
	
	@Override
	@Transactional
	public String getAccountNoBasedOnAccDetId(BigDecimal accDetId){
		return fXDetailInformationDao.getAccountNoBasedOnAccDetId(accDetId);
	}

	@Override
	@Transactional
	public List<StandardInstruction> getValues(BigDecimal companyId,
			BigDecimal bankId, BigDecimal currencyId, BigDecimal accountDetId,
			String instructionType) {
		
		return fXDetailInformationDao.getValues(companyId, bankId, currencyId, accountDetId, instructionType);
	}

	@Override
	@Transactional
	public List<TreasuryDealRegisterView> fetchTreasuryDealRegisterFromView(HashMap<String, Object> lstTreasuryDealRegister) {
		return fXDetailInformationDao.fetchTreasuryDealRegisterFromView(lstTreasuryDealRegister);
	}
	
	@Override
	@Transactional
	public String getSplitIndicatorFromBankMaster(BigDecimal bankId) {
 
		return fXDetailInformationDao.getSplitIndicatorFromBankMaster(bankId);
	}
	
	@Override
	@Transactional
	public List<TreasuryCustomerDeal> getTreasuryCustomerDealAndPaymentValues(BigDecimal companyId, BigDecimal documentId, BigDecimal documentFinaceYear, BigDecimal documentNo){
		return fXDetailInformationDao.getTreasuryCustomerDealAndPaymentValues(companyId, documentId, documentFinaceYear, documentNo);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteAllFXDealBank(HashMap<String, Object> saveMapInfo,
			String userName) throws Exception {
		fXDetailInformationDao.deleteAllFXDealBank(saveMapInfo, userName);
		
	}

	@Override
	@Transactional
	public List<BankApplicability> getCorrespondingLocalFundingBanks(
			BigDecimal countryId) {
		
		return fXDetailInformationDao.getCorrespondingLocalFundingBanks(countryId);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForSupplier(
			String Fx_BankDealType, String status, BigDecimal documentYear) {
		
		return fXDetailInformationDao.fetchDocumentNumberFromTreasDealheaderForSupplier(Fx_BankDealType, status, documentYear);
	}
	
	//Anil (19/01/2018)
	public List<BankMaster> getBankName(BigDecimal bankId){
		return fXDetailInformationDao.getBankName(bankId);
	}
	
}
