package com.amg.exchange.cashtransfer.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.cashtransfer.model.CashDetails;
import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;


public interface ICashTransferLToLDao {

	public List<Stock> getAllCurrencyDenominationFromStock(BigDecimal appCountryId,String userName, BigDecimal branchId,BigDecimal companyId);

	//for getting all denomination list
	public List<CurrencyWiseDenomination> getAllCurrencyWiseDenomiantionList();

	//get all casheir list from FsEmployee table
	public List<Employee> getAllCashierList(BigDecimal branchId,BigDecimal roleId);

	//get branchName based On Branch Id
	public List<CountryBranch> getBranchName(BigDecimal branchId);

	//get cash Transfer data for approve
	public List<CashHeader> getCashDataForApprove(String userName,BigDecimal branchId);	

	//for approving record
	public void approveRecord(BigDecimal cashHeaderPk,String approvedName) throws Exception;

	//to save all 
	public void saveAllRecords(CashHeader cashHeader,List<CashDetails> cashDetailsList,List<ForeignCurrencyAdjust> foreignCurrencyAdjustList)throws Exception;

	// call Procedure EX_P_POPULATE_CASH_TRANSFER 
	public String procedurePopulateCashTransfer(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentcode,BigDecimal documentyear,BigDecimal documentNo) throws Exception;
	
	// call Procedure EX_P_POP_CASH_TRANSFER_APPROVE  
	public String procedurePopulateCashTransferApproval(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentcode,BigDecimal documentyear,BigDecimal documentNo) throws Exception;
	
	// call Procedure EX_P_VALIDATE_CASH_TRANS_STOCK 
	public String procedurePValidateCashTransferStock(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentcode,BigDecimal documentyear,BigDecimal documentNo) throws Exception;
	
	//Method for all currency stock
	public List<UserStockView> getCurrencyDenominationFromStock(BigDecimal appCountryId,String userName, BigDecimal branchId,BigDecimal companyId,BigDecimal currencyId);
	public List<BigDecimal> getStockCurrency(BigDecimal countryBranchId);
	
	public String updateCashdeposit(BigDecimal applicationCountryId, BigDecimal companyId, String cashier, BigDecimal countryBrasnchId,BigDecimal currencyId );
	
	public String getNextToken();
	
	public List<CashHeader> isCheckCashTransfer(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal branchId,Date documentDate,String fromCashier, String toCashier,BigDecimal toCountryBranchCode);

	public List<CashDetails> getCashDetailsById(BigDecimal headerId);
	
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjust(BigDecimal documentNo);
	
	public void deleteCashHeader(CashHeader cashHeader);
	public void deleteCashDetails(CashDetails cashDetails);
	public void deleteCurrencyAdjustDetails(ForeignCurrencyAdjust foreignCurrencyAdjust);
	
	public List<BigDecimal> getUpdateCurrency(BigDecimal documentNo);
	
	public List<ForeignCurrencyAdjust> getForeignCurrencyAdjustDetails(BigDecimal documentNo,BigDecimal currencyId,BigDecimal dinomAmount);

}
