package com.amg.exchange.cashtransfer.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cashtransfer.dao.ICashTransferLToLDao;
import com.amg.exchange.cashtransfer.model.CashDetails;
import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;

@Service
public class CashTransferLToLServiceImpl implements ICashTransferLToLService{
	
	@Autowired
	ICashTransferLToLDao cashTransferLToLDao;

	@Override
	@Transactional
	public List<Stock> getAllCurrencyDenominationFromStock( BigDecimal appCountryId, String userName, BigDecimal branchId,
			BigDecimal companyId) {
		return cashTransferLToLDao.getAllCurrencyDenominationFromStock(appCountryId, userName, branchId, companyId);
	}
	
	
	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getAllCurrencyWiseDenomiantionList() {
		return cashTransferLToLDao.getAllCurrencyWiseDenomiantionList();
	}
	
	@Override
	@Transactional
	public List<Employee> getAllCashierList(BigDecimal branchId,BigDecimal roleId) {
		return cashTransferLToLDao.getAllCashierList(branchId,roleId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getBranchName(BigDecimal branchId) {
		return cashTransferLToLDao.getBranchName(branchId);
	}
	
	@Override
	@Transactional
	public List<CashHeader> getCashDataForApprove(String userName,BigDecimal branchId) {
		return cashTransferLToLDao.getCashDataForApprove(userName,branchId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void approveRecord(BigDecimal cashHeaderPk,String approvedName) throws Exception{
		cashTransferLToLDao.approveRecord(cashHeaderPk,approvedName);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveAllRecords(CashHeader cashHeader,List<CashDetails> cashDetailsList,List<ForeignCurrencyAdjust> foreignCurrencyAdjustList)throws Exception{
		cashTransferLToLDao.saveAllRecords(cashHeader,cashDetailsList,foreignCurrencyAdjustList);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String procedurePopulateCashTransfer(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentcode,BigDecimal documentyear,BigDecimal documentNo) throws Exception {
		return cashTransferLToLDao.procedurePopulateCashTransfer(appCountryId, companyId, documentcode, documentyear, documentNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String procedurePopulateCashTransferApproval(BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentcode, BigDecimal documentyear,BigDecimal documentNo) throws Exception {
		return cashTransferLToLDao.procedurePopulateCashTransferApproval(appCountryId, companyId, documentcode, documentyear, documentNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public String procedurePValidateCashTransferStock(BigDecimal appCountryId,BigDecimal companyId, BigDecimal documentcode,BigDecimal documentyear, BigDecimal documentNo) throws Exception {
		return cashTransferLToLDao.procedurePValidateCashTransferStock(appCountryId, companyId, documentcode, documentyear, documentNo);
	}	
	
	@Override
	@Transactional
	public List<UserStockView> getCurrencyDenominationFromStock(BigDecimal appCountryId,String userName,BigDecimal branchId,BigDecimal companyId,BigDecimal currencyId){
		return cashTransferLToLDao.getCurrencyDenominationFromStock(appCountryId, userName, branchId, companyId,currencyId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public List<BigDecimal> getStockCurrency(BigDecimal countryBranchId){
		return cashTransferLToLDao.getStockCurrency(countryBranchId);
	}
	
	@Override
	@Transactional
	public String updateCashdeposit(BigDecimal applicationCountryId, BigDecimal companyId, String cashier, BigDecimal countryBrasnchId,BigDecimal currencyId ){
		return cashTransferLToLDao.updateCashdeposit(applicationCountryId, companyId, cashier, countryBrasnchId,currencyId );
	}
	
	@Override
	@Transactional
	public String getNextToken() {
		return cashTransferLToLDao.getNextToken();
	}
	@Override
	@Transactional
	public List<CashHeader> isCheckCashTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal branchId,Date documentDate,String fromCashier, String toCashier,BigDecimal toCountryBranchCode){
		return cashTransferLToLDao.isCheckCashTransfer(applicationCountryId,companyId,branchId,documentDate,fromCashier,toCashier,toCountryBranchCode);
	}
	
	@Override
	@Transactional
	public List<CashDetails> getCashDetailsById(BigDecimal headerId){
		return cashTransferLToLDao.getCashDetailsById(headerId);

	}
	
	@Override
	@Transactional
	public void deleteCashHeader(CashHeader cashHeader){
		cashTransferLToLDao.deleteCashHeader(cashHeader);
	}
	
	@Override
	@Transactional
	public void deleteCashDetails(CashDetails cashDetails){
		cashTransferLToLDao.deleteCashDetails(cashDetails);
	}
	
	@Override
	@Transactional
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjust(BigDecimal documentNo){
		return cashTransferLToLDao.getForeignCurrencyAdjust(documentNo);
	}
	
	@Override
	@Transactional
	public void deleteCurrencyAdjustDetails(ForeignCurrencyAdjust foreignCurrencyAdjust){
		cashTransferLToLDao.deleteCurrencyAdjustDetails(foreignCurrencyAdjust);
	}
	
	@Override
	@Transactional
	public List<BigDecimal> getUpdateCurrency(BigDecimal documentNo){
		return cashTransferLToLDao.getUpdateCurrency(documentNo);
	}
	
	@Override
	@Transactional
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjustDetails(BigDecimal documentNo,BigDecimal currencyId,BigDecimal dinomAmount){
		return cashTransferLToLDao.getForeignCurrencyAdjustDetails(documentNo,currencyId,dinomAmount);
	}
}
