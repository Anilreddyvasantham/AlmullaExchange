package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.IkonRateView;
import com.amg.exchange.treasury.dao.IFxDealWithSupplierDao;
import com.amg.exchange.treasury.deal.supplier.model.ApplicationSetup;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.util.Constants;

/**
 * @author Mohan last modified on - 27/02/2015
 * @param <T>
 */
@SuppressWarnings("serial")
@Repository
public class FxDealwithSupplierDaoImpl<T> extends CommonDaoImpl<T> implements IFxDealWithSupplierDao<T>, Serializable {

	Logger log = Logger.getLogger(FxDealwithSupplierDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierBankList() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerSupplier.class, "treasuryCustomerSupplier");

		dCriteria.setFetchMode("treasuryCustomerSupplier.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryCustomerSupplier>) findAll(dCriteria);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierCurrencyList(BigDecimal sdBankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerSupplier.class, "treasuryCustomerSupplier");

		dCriteria.setFetchMode("treasuryCustomerSupplier.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.bankId", sdBankId));

		dCriteria.setFetchMode("treasuryCustomerSupplier.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.currencyId", "currencyId", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryCustomerSupplier>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryCustomerSupplier> getFxDealWithSupplierAccNoList(BigDecimal sdBankId, BigDecimal sdCurrencyId) {
		// TODO Auto-generated method stub
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerSupplier.class, "treasuryCustomerSupplier");

		dCriteria.setFetchMode("treasuryCustomerSupplier.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.bankId", sdBankId));

		dCriteria.setFetchMode("treasuryCustomerSupplier.currencyId", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.currencyId", "currencyId", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("currencyId.currencyId", sdCurrencyId));

		dCriteria.setFetchMode("treasuryCustomerSupplier.bankAccountNo", FetchMode.JOIN);
		dCriteria.createAlias("treasuryCustomerSupplier.bankAccountNo", "bankAccountNo", JoinType.INNER_JOIN);

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryCustomerSupplier>) findAll(dCriteria);
	}

	@Override
	public void saveOrUpdateAppSetUp(ApplicationSetup applicationSetup) {
		getSession().saveOrUpdate(applicationSetup);

	}

	@Override
	public void saveOrUpdateDayBook(DayBookHeader dayBookHeader) {
		getSession().saveOrUpdate(dayBookHeader);

	}

	@Override
	public void saveOrUpdateDayBook(DayBookDetails dayBookDetails) {
		getSession().saveOrUpdate(dayBookDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankApplicability> getLocalBankFromOtherBank(BigDecimal countryID, BigDecimal bankID) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		// to fetch all banks , currency shound not match
		//dCriteria.add(Restrictions.ne("bankMaster.bankId", bankID));
		dCriteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryID));
		dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);

		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);

		dCriteria.add(Restrictions.eq("bankInd.bankIndicatorId", localBankIndicatorId));
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));
		/** NAG CODE APPLY ASCENDING ORDER **/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankApplicability>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankIndicator.class, "bankIndicator");

		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode", bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);

		if (lstBankIndId.size() != 0) {
			return ((List<BankIndicator>) findAll(dCriteria)).get(0).getBankIndicatorId();
		}

		return null;
	}

	@Override
	public List<AccountBalance> getBankBalance(BigDecimal bankID, BigDecimal currencyID) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");

		dCriteria.setFetchMode("accountBalance.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankID));

		dCriteria.setFetchMode("accountBalance.exCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("exCurrencyMaster.currencyId", currencyID));

		dCriteria.setFetchMode("accountBalance.exBankAccountDetails", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exBankAccountDetails", "exBankAccountDetails", JoinType.INNER_JOIN);
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AccountBalance>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBalance> getBankBalance(String fundGlno) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");

		dCriteria.setFetchMode("accountBalance.exCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exCurrencyMaster", "exCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", fundGlno));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AccountBalance>) findAll(dCriteria);
	}

	@Override
	public AccountBalance getAverageRate(String accountNo) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");

		/*
		 * dCriteria.setFetchMode("accountBalance.bankMaster", FetchMode.JOIN);
		 * dCriteria.createAlias("accountBalance.bankMaster", "bankMaster",
		 * JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("bankMaster.bankId", bankID));
		 * dCriteria.setFetchMode("accountBalance.exCurrencyMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("accountBalance.exCurrencyMaster",
		 * "exCurrencyMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("exCurrencyMaster.currencyId",
		 * currencyID));
		 */
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountNo));

		AccountBalance accountBalance = (AccountBalance) dCriteria.getExecutableCriteria(getSession()).uniqueResult();

		return accountBalance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApplicationSetup> getApplicationSetupDetalis(BigDecimal appCountryID, BigDecimal companyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ApplicationSetup.class, "applicationSetup");

		dCriteria.setFetchMode("applicationSetup.appSetupCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("applicationSetup.appSetupCountryMaster", "appSetupCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("appSetupCountryMaster.countryId", appCountryID));

		dCriteria.setFetchMode("applicationSetup.fsCompanyMasterr", FetchMode.JOIN);
		dCriteria.createAlias("applicationSetup.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ApplicationSetup>) findAll(dCriteria);
	}

	@Override
	public boolean saveAllFxDealWithSupplier(HashMap<String, Object> saveMapInfo) throws Exception {
		try {
			TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) saveMapInfo.get("TreasuryDealHeader");
			TreasuryDealDetail treasuryDealDetailForPD = (TreasuryDealDetail) saveMapInfo.get("TreasuryDealDetailForPD");
			TreasuryDealDetail treasuryDealDetailForPY = (TreasuryDealDetail) saveMapInfo.get("TreasuryDealDetailForPY");
			DayBookHeader dayBookHeader = (DayBookHeader) saveMapInfo.get("DayBookHeader");
			DayBookDetails dayBookSdDetailsForSD = (DayBookDetails) saveMapInfo.get("DayBookDetailsForSD");
			DayBookDetails dayBookSdDetailsForPY = (DayBookDetails) saveMapInfo.get("DayBookDetailsForPY");

			// save the TreasuryDealHeader
			getSession().saveOrUpdate(treasuryDealHeader);

			// save the TreasuryDealDetails for PD
			getSession().saveOrUpdate(treasuryDealDetailForPD);

			// save the TreasuryDealDetails for PY
			getSession().saveOrUpdate(treasuryDealDetailForPY);

			// save the DayBookHeader
			getSession().saveOrUpdate(dayBookHeader);

			// save the DayBookDetails For SD
			getSession().saveOrUpdate(dayBookSdDetailsForSD);

			// save the DayBookDetails For PY
			getSession().saveOrUpdate(dayBookSdDetailsForPY);

		} catch (Exception e) {
			log.info("Problem saving FX Deal with supplier: " + e);
			throw e;
		}

		return false;
	}
	
	@Override
	public void delete(BigDecimal treasuryDealHeaderIdPK,BigDecimal treasuryDealDetailPDPK,BigDecimal treasuryDealDetailPYPK,BigDecimal daybookHeaderIdPK,
			BigDecimal dayBookDetailsSDPK,BigDecimal dayBookDetailsPYPK,String username) throws Exception {
		
		try{
			
			// Delete the TreasuryDealHeader.
			if(treasuryDealHeaderIdPK!=null) {
				TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryDealHeaderIdPK);
				treasuryDealHeader.setIsActive(Constants.D);
				treasuryDealHeader.setModifiedBy(username);
				treasuryDealHeader.setModifiedDate(new Date());		
				getSession().saveOrUpdate(treasuryDealHeader);
			}		
			
			// Delete the TreasuryDealDetail for PD.
			if(treasuryDealDetailPDPK!=null) {
				TreasuryDealDetail treasuryDealDetailPD = (TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryDealDetailPDPK);
				treasuryDealDetailPD.setIsActive(Constants.D);
				treasuryDealDetailPD.setModifiedBy(username);
				treasuryDealDetailPD.setModifiedDate(new Date());		
				getSession().saveOrUpdate(treasuryDealDetailPD);
			}			
			
			// Delete the TreasuryDealDetail for PY.
			if(treasuryDealDetailPYPK!=null) {
				TreasuryDealDetail treasuryDealDetailPY = (TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, treasuryDealDetailPYPK);
				treasuryDealDetailPY.setIsActive(Constants.D);
				treasuryDealDetailPY.setModifiedBy(username);
				treasuryDealDetailPY.setModifiedDate(new Date());		
				getSession().saveOrUpdate(treasuryDealDetailPY);
			}			
			
			// Delete the DayBookHeader.
			if(daybookHeaderIdPK!=null) {
				DayBookHeader dayBookHeader = (DayBookHeader) getSession().get(DayBookHeader.class, daybookHeaderIdPK);
				dayBookHeader.setIsActive(Constants.D);
				dayBookHeader.setModifiedBy(username);
				dayBookHeader.setModifiedDate(new Date());		
				getSession().saveOrUpdate(dayBookHeader);
			}			
			
			// Delete the DayBookDetails For SD.
			if(dayBookDetailsSDPK!=null) {
				DayBookDetails dayBookDetailsSD = (DayBookDetails) getSession().get(DayBookDetails.class, dayBookDetailsSDPK);
				dayBookDetailsSD.setModifiedBy(username);
				dayBookDetailsSD.setModifiedDate(new Date());		
				getSession().saveOrUpdate(dayBookDetailsSD);
			}			
			
			// Delete the DayBookDetails For PY.
			if(dayBookDetailsPYPK!=null) {
				DayBookDetails dayBookDetailsPY = (DayBookDetails) getSession().get(DayBookDetails.class, dayBookDetailsPYPK);
				dayBookDetailsPY.setModifiedBy(username);
				dayBookDetailsPY.setModifiedDate(new Date());		
				getSession().saveOrUpdate(dayBookDetailsPY);
			}			
			
		} catch (Exception e) {
			log.info("Problem Deleting FX Deal with supplier: " + e);
			throw e;
		}			
	}

	@Override
	public List<BankAccountDetails> getAccountDetailslist(BigDecimal bankId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");

		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("bankAccountDetails.exBankMaster.bankId", bankId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getIkonRateBasedOnCurrency(BigDecimal currencyId) {
		System.out.println("currencyId :" + currencyId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(IkonRateView.class, "ikonRateView");
		dCriteria.add(Restrictions.eq("ikonRateView.currencyId", currencyId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<IkonRateView> ikonRateList = (List<IkonRateView>) findAll(dCriteria);

		if (ikonRateList.size() > 0) {
			return ikonRateList.get(0).getCurrencyPrice();
		} else {
			return null;
		}
	}

	@Override
	public BigDecimal getLocalCurrencyId(BigDecimal appCountryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");

		dCriteria.setFetchMode("currencyMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("currencyMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));

		List<CurrencyMaster> currencyList = (List<CurrencyMaster>) findAll(dCriteria);

		if (currencyList.size() > 0) {
			return currencyList.get(0).getCurrencyId();
		} else {
			return null;
		}
	}

	  @Override
	  public List<DayBookHeader> fetchDocumentNumberFromDayBookheader(String status) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");
		    dCriteria.add(Restrictions.eq("dayBookHeader.isActive", status));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<DayBookHeader> dayBookHeaderList = (List<DayBookHeader>) findAll(dCriteria);
		    return dayBookHeaderList;
	  }

	  @Override
	  public List<DayBookHeader> toFetchRecordsFromDayBookHeader(BigDecimal documentNo) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");
		    //DocumentId
		    dCriteria.setFetchMode("dayBookHeader.doucDocumentId", FetchMode.JOIN);
		    dCriteria.createAlias("dayBookHeader.doucDocumentId", "doucDocumentId", JoinType.INNER_JOIN);
		    //CompanyMaster
		    dCriteria.setFetchMode("dayBookHeader.dayBookCompanyMaster", FetchMode.JOIN);
		    dCriteria.createAlias("dayBookHeader.dayBookCompanyMaster", "dayBookCompanyMaster", JoinType.INNER_JOIN);
		    //CountryMaster
		    dCriteria.setFetchMode("dayBookHeader.dayBookCountryMaster", FetchMode.JOIN);
		    dCriteria.createAlias("dayBookHeader.dayBookCountryMaster", "dayBookCountryMaster", JoinType.INNER_JOIN);
		    
		    dCriteria.add(Restrictions.eq("dayBookHeader.documentNumber", documentNo));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<DayBookHeader> dayBookHeaderList = (List<DayBookHeader>) findAll(dCriteria);
		    return dayBookHeaderList;
	  }

	  @Override
	  public List<TreasuryDealHeader> toFetchRecordsFromTreasureHeaderForTreHeadId(BigDecimal refNumber) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		    dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDocumentNumber", refNumber));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<TreasuryDealHeader> treasuryDealHeaderList = (List<TreasuryDealHeader>) findAll(dCriteria);
		    return treasuryDealHeaderList;
	  }

	  @Override
	  public List<DayBookHeader> fetchDocumentNumberBasedOnAllDetails(BigDecimal documentSerialIdNumber) {
		    DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");
		    dCriteria.add(Restrictions.eq("dayBookHeader.documentNumber", documentSerialIdNumber));
		    dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<DayBookHeader> DayBookHeaderList = (List<DayBookHeader>) findAll(dCriteria);
		    return DayBookHeaderList;
	  }

}
