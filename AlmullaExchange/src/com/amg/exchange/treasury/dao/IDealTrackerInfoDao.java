package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.bean.BulkDealApprovalDataTable;
import com.amg.exchange.treasury.bean.DealTrackerInfoDTBean;
import com.amg.exchange.treasury.bean.DealTrackerViewTicketDataTable;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.ViewCorrespondingBankCountry;
import com.amg.exchange.treasury.model.VwExBulkFxDeal;
import com.amg.exchange.treasury.viewModel.DealTrackerTicketView;
import com.amg.exchange.util.AMGException;

public interface IDealTrackerInfoDao<T> {

	public List<DealTrackerTicketView> getDealTrackerTicketViewInfo(Date dealDate);
	public HashMap<String, Object> getIDsFromCode(DealTrackerInfoDTBean dealTrackerInfoDTBean);
	public List<DealTrackerTicketView> getDealTrackerTicketView(Date dealDate,BigDecimal countryId,String currencyCode,String bankCode) throws AMGException;
	public HashMap<String, Object> getIDsFromCodeDealTickets(DealTrackerViewTicketDataTable dealTrackerInfoDTBean,BigDecimal countryId,BigDecimal companyId);
	public List<CurrencyMaster> getCurrencyNameandCurrencyIdBasedonQuoteName(String quoteName);
	//saving all Fx_Deal Bank with rollback
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo)throws Exception;
	
	public List<BankAccountDetails> getBankAccountDetlsBasedOnCode(String bankCode,String currencyCode);
	
	public List<BankMaster> getBankMasterInfo(String bankCode);
	
	public List<TreasuryDealHeader> getTreasuryHrdForDealTracker(Date dealDate,String dealID) ;
	
	public String getSplitIndicatorFromBankMaster(String bankCode);
	public HashMap< String, String>  getForeignCurrrencyAmountFrmSpclCustomer( BigDecimal docFinYear, BigDecimal docNo);
	public BigDecimal getBankId(String bankCode);
	public BigDecimal getCurrencyId(String currencyCode);
	public BigDecimal getFinancialYearId(BigDecimal finacialYear);
	public  BigDecimal getCustomerRefBasedOnCustomerId(BigDecimal customerId); 

	public List<ViewCorrespondingBankCountry> getCorrespondingbankCountryList();
	public List<DealTrackerTicketView> getDealTrackerTicketwithNativeQuery(Date currentDate, BigDecimal countryId, String currencyCode, String bankCode) throws AMGException;
	public HashMap<String, Object> ValidateDealIDWhileUpdate(String dealID, Date dealDate) throws AMGException;
	public List<TreasuryDealDetail> getBulkFXDealUnApprovalRecords(BigDecimal companyId,BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId);
	public List<BulkDealApprovalDataTable> getBulkUnApproveDealHrDetails(BigDecimal companyId,BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId);
	public String bulkDealApprove(List<BigDecimal> lstTreasuryHrdId,String userName);
	public String bulkDealDelete(List<BigDecimal> lstTreasuryHrdId,String userName);
	public List<VwExBulkFxDeal> getViewBulckFxDDealApproval(BigDecimal companyId,BigDecimal countryId,BigDecimal bankCountryId,BigDecimal bankId);
	public String bulkDealApproveWithProcedure(BigDecimal companyId,BigDecimal contryId, List<BigDecimal> lstTreasuryHrdId,String userName)throws Exception;
}
