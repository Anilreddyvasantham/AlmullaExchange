package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

public interface IFxDealWithSupplierDao<T>  {
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierBankList();
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierCurrencyList(BigDecimal sdBankId);
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierAccNoList(BigDecimal sdBankId,BigDecimal sdCurrencyId);
	public void saveOrUpdateAppSetUp(ApplicationSetup applicationSetup);
	public void saveOrUpdateDayBook(DayBookHeader dayBookHeader);
	public void saveOrUpdateDayBook(DayBookDetails dayBookDetails);
	//To Get Other Local bank list
	public List<BankApplicability> getLocalBankFromOtherBank(BigDecimal countryID,BigDecimal bankID);
	
	//To Get Bank balance 
	public List<AccountBalance> getBankBalance(BigDecimal bankID,BigDecimal currencyID);
	
	public List<AccountBalance> getBankBalance(String fundGlno);
	
	public AccountBalance getAverageRate(String accountNo);
	
	public List<ApplicationSetup> getApplicationSetupDetalis(BigDecimal appCountryID,BigDecimal companyId);
	
	public boolean saveAllFxDealWithSupplier(HashMap<String, Object> saveMapInfo)throws Exception;
	
	public void delete(BigDecimal treasuryDealHeaderIdPK,BigDecimal treasuryDealDetailPDPK,BigDecimal treasuryDealDetailPYPK,BigDecimal daybookHeaderIdPK,
			BigDecimal dayBookDetailsSDPK,BigDecimal dayBookDetailsPYPK,String username) throws Exception;
	
	public List<BankAccountDetails> getAccountDetailslist(BigDecimal bankId);
	
	public BigDecimal getIkonRateBasedOnCurrency(BigDecimal currencyId);
	
	public BigDecimal getLocalCurrencyId(BigDecimal appCountryId);

	  public List<DayBookHeader> fetchDocumentNumberFromDayBookheader(String status);
	  public List<DayBookHeader> toFetchRecordsFromDayBookHeader(BigDecimal documentNo);
	  public List<TreasuryDealHeader> toFetchRecordsFromTreasureHeaderForTreHeadId(BigDecimal refNumber);
	  public List<DayBookHeader> fetchDocumentNumberBasedOnAllDetails(BigDecimal documentSerialIdNumber);
}
