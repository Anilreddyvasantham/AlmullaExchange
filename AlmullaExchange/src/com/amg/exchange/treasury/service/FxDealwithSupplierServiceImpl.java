package com.amg.exchange.treasury.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IFxDealWithSupplierDao;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.TreasuryDealHeader;

/**
 * @author Mohan - last modified on 27/02/2015
 * @param <T>
 */
@SuppressWarnings("serial")
@Service("fxDealwithSupplierServiceImpl")
public class FxDealwithSupplierServiceImpl<T> implements IFxDealwithSupplierService<T>, Serializable {

	@Autowired
	IFxDealWithSupplierDao<T> fxDealWithSupplierDao;

	@Transactional
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierBankList() {
		return fxDealWithSupplierDao.getFxDealWithSupplierBankList();

	}

	@Transactional
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierCurrencyList(BigDecimal sdBankId) {

		return fxDealWithSupplierDao.getFxDealWithSupplierCurrencyList(sdBankId);
	}

	@Transactional
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierAccNoList(BigDecimal sdBankId, BigDecimal sdCurrencyId) {

		return fxDealWithSupplierDao.getFxDealWithSupplierAccNoList(sdBankId, sdCurrencyId);
	}

	@Transactional
	@Override
	public void saveOrUpdateAppSetUp(ApplicationSetup applicationSetup) {
		fxDealWithSupplierDao.saveOrUpdateAppSetUp(applicationSetup);

	}

	@Transactional
	@Override
	public void saveOrUpdateDayBook(DayBookHeader dayBookHeader) {
		fxDealWithSupplierDao.saveOrUpdateDayBook(dayBookHeader);

	}

	@Transactional
	@Override
	public void saveOrUpdateDayBook(DayBookDetails dayBookDetails) {
		fxDealWithSupplierDao.saveOrUpdateDayBook(dayBookDetails);

	}

	@Transactional
	@Override
	public List<BankApplicability> getLocalBankFromOtherBank(BigDecimal countryID, BigDecimal bankID) {
		return fxDealWithSupplierDao.getLocalBankFromOtherBank(countryID, bankID);
	}

	@Transactional
	@Override
	public List<AccountBalance> getBankBalance(BigDecimal bankID, BigDecimal currencyID) {
		return fxDealWithSupplierDao.getBankBalance(bankID, currencyID);
	}

	@Transactional
	@Override
	public List<AccountBalance> getBankBalance(String fundGlno) {
		return fxDealWithSupplierDao.getBankBalance(fundGlno);
	}

	@Transactional
	@Override
	public AccountBalance getAverageRate(String accountNo) {

		return fxDealWithSupplierDao.getAverageRate(accountNo);
	}

	@Transactional
	@Override
	public List<ApplicationSetup> getApplicationSetupDetalis(BigDecimal appCountryID, BigDecimal companyId) {

		return fxDealWithSupplierDao.getApplicationSetupDetalis(appCountryID, companyId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveAllFxDealWithSupplier(HashMap<String, Object> saveMapInfo) throws Exception {

		return fxDealWithSupplierDao.saveAllFxDealWithSupplier(saveMapInfo);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void delete(BigDecimal treasuryDealHeaderIdPK,BigDecimal treasuryDealDetailPDPK,BigDecimal treasuryDealDetailPYPK,BigDecimal daybookHeaderIdPK,
			BigDecimal dayBookDetailsSDPK,BigDecimal dayBookDetailsPYPK,String username) throws Exception {

		fxDealWithSupplierDao.delete(treasuryDealHeaderIdPK,treasuryDealDetailPDPK,treasuryDealDetailPYPK,daybookHeaderIdPK,dayBookDetailsSDPK,dayBookDetailsPYPK,username);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getAccountDetailslist(BigDecimal bankId) {

		return fxDealWithSupplierDao.getAccountDetailslist(bankId);
	}

	@Override
	@Transactional
	public BigDecimal getIkonRateBasedOnCurrency(BigDecimal currencyId) {
		return fxDealWithSupplierDao.getIkonRateBasedOnCurrency(currencyId);
	}

	@Override
	@Transactional
	public BigDecimal getLocalCurrencyId(BigDecimal appCountryId) {

		return fxDealWithSupplierDao.getLocalCurrencyId(appCountryId);
	}

	  @Override
	  @Transactional
	  public List<DayBookHeader> fetchDocumentNumberFromDayBookheader(String status) {
		    return fxDealWithSupplierDao.fetchDocumentNumberFromDayBookheader(status);
	  }

	  @Override
	  @Transactional
	  public List<DayBookHeader> toFetchRecordsFromDayBookHeader(BigDecimal documentNo) {
		    return fxDealWithSupplierDao.toFetchRecordsFromDayBookHeader(documentNo);
	  }

	  @Override
	  @Transactional
	  public List<TreasuryDealHeader> toFetchRecordsFromTreasureHeaderForTreHeadId(BigDecimal refNumber) {
		    return fxDealWithSupplierDao.toFetchRecordsFromTreasureHeaderForTreHeadId(refNumber);
	  }

	  @Override
	  @Transactional
	  public List<DayBookHeader> fetchDocumentNumberBasedOnAllDetails(BigDecimal documentSerialIdNumber) {
		    return fxDealWithSupplierDao.fetchDocumentNumberBasedOnAllDetails(documentSerialIdNumber);
	  }

}
