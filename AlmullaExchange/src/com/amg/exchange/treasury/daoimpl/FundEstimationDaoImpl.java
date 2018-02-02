package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.bean.CountryCurrencyPopulationBean;
import com.amg.exchange.treasury.dao.IFundEstimationDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.FundConsolidatedView;
import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationAlert;
import com.amg.exchange.treasury.model.FundEstimationDays;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.viewModel.TreasuryBankCountryView;
import com.amg.exchange.treasury.viewModel.TreasuryCurrencyView;
import com.amg.exchange.treasury.viewModel.TreasuryEstimationDaysView;
import com.amg.exchange.treasury.viewModel.TreasuryFundEstimationDealAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationAlertView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationView;
import com.amg.exchange.treasury.viewModel.TreasuryFundestimationViewSummary;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class FundEstimationDaoImpl<T> extends CommonDaoImpl<T> implements IFundEstimationDao<T>, Serializable {
	
	Logger LOGGER = Logger.getLogger(FundEstimationDaoImpl.class);

	@Override
	public String getCountryName(BigDecimal countryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return ((CountryMasterDesc) dCriteria.getExecutableCriteria(getSession()).list().get(0)).getCountryName();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankCountryPopulationBean> getBankContry(BigDecimal counryId) {
		BankCountryPopulationBean bankCountryPopulationBean = null;
		List<BankCountryPopulationBean> finalData = new ArrayList<BankCountryPopulationBean>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "applicabity");

		dCriteria.setFetchMode("applicabity.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("applicabity.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("applicabity.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("applicabity.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", counryId));
		dCriteria.addOrder(Order.asc("fsCountryMaster.fsCountryMasterDescs"));
		/** NAG APPLY ASCENDING 04/02/2015 **/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> lstBankApplicability = (List<BankApplicability>) findAll(dCriteria);

		List<BigDecimal> lstBankId = new ArrayList<BigDecimal>();

		for (BankApplicability bankApplicability : lstBankApplicability) {
			lstBankId.add(bankApplicability.getBankMaster().getBankId());
		}

		DetachedCriteria dCriteria1 = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		/*
		 * 18/10/2014 We removed FS_Application_CountryID from Bank Master Table
		 * dCriteria.setFetchMode("bankMaster.fsApplicationCountryMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("bankMaster.fsApplicationCountryMaster",
		 * "fsApplicationCountryMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("fsApplicationCountryMaster.countryId",
		 * counryId));
		 */

		dCriteria1.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria1.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		// dCriteria1.add(Restrictions.eq("fsCountryMaster.countryId",
		// counryId));

		dCriteria1.setFetchMode("fsCountryMaster.fsCountryMasterDescs", FetchMode.JOIN);
		dCriteria1.createAlias("fsCountryMaster.fsCountryMasterDescs", "fsCountryMasterDescs", JoinType.INNER_JOIN);
		dCriteria1.add(Restrictions.not(Restrictions.eq("fsCountryMaster.countryId", counryId)));

		dCriteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> lstBankmaster = dCriteria1.getExecutableCriteria(getSession()).list();

		for (BankMaster bankMaster : lstBankmaster) {
			if (lstBankId.contains(bankMaster.getBankId())) {
				bankCountryPopulationBean = new BankCountryPopulationBean(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getFsCountryMaster().getFsCountryMasterDescs().get(0).getCountryName(),bankMaster.getFsCountryMaster().getCountryCode());
				if (!duplicateCheck.contains(bankCountryPopulationBean.getBankCountryId())) {
					duplicateCheck.add(bankCountryPopulationBean.getBankCountryId());
					finalData.add(bankCountryPopulationBean);
				}
			}
		}

		return finalData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankAccordingToBankCountry(BigDecimal applicationCountry, BigDecimal countryId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		/*
		 * 18/10/2014 we removed FS_Application_CountryID from Bank Master Table
		 * dCriteria.setFetchMode("bankMaster.fsApplicationCountryMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("bankMaster.fsApplicationCountryMaster",
		 * "fsApplicationCountryMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("fsApplicationCountryMaster.countryId",
		 * applicationCountry));
		 */

		dCriteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return dCriteria.getExecutableCriteria(getSession()).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getCurrencyOfBank(BigDecimal bankId) {

		List<BankAccountDetails> finalData = new ArrayList<BankAccountDetails>();
		List<BigDecimal> lstCurrency = new ArrayList<BigDecimal>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");

		dCriteria.setFetchMode("bankAccountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.addOrder(Order.asc("fsCurrencyMaster.currencyName"));
		/** NAG APPLY ASCENDING ORDER 04/02/2014 **/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(dCriteria);

		for (BankAccountDetails bankAccountDetails : data) {
			if (!lstCurrency.contains(bankAccountDetails.getFsCurrencyMaster().getCurrencyId())) {
				finalData.add(bankAccountDetails);
				lstCurrency.add(bankAccountDetails.getFsCurrencyMaster().getCurrencyId());
			}
		}

		return finalData;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void save(FundEstimation fundEstimation) {
		getSession().save(fundEstimation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAndUpadate(FundEstimation fundEstimation) {
		getSession().saveOrUpdate(fundEstimation);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveFundEstimtaionDetails(FundEstimationDetails fundEstimationDetails) {
		getSession().save(fundEstimationDetails);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDetails> getFundEstimationData(Date projectionDate, BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDetails.class, "fundEstimationDetails");

		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDeatilsForBankCountry", "exFundEstimtaionDeatilsForBankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimtaionDeatilsForBankCountry.countryId", countryId));

		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimtaionDetailsForApplicationCountry", "exFundEstimtaionDetailsForApplicationCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimtaionDetailsForApplicationCountry.countryId", applicationCountryId));

		dCriteria.setFetchMode("fundEstimationDetails.exFundEstimationDetails", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exFundEstimationDetails", "exFundEstimationDetails", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimationDetails.bankAcctDetId", accountNumber));

		dCriteria.setFetchMode("fundEstimationDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("fundEstimationDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));

		dCriteria.setFetchMode("fundEstimationDetails.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDetails.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order.asc("fundEstimationDetails.fundEstimtaionDetailsId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<FundEstimationDetails> data = (List<FundEstimationDetails>) findAll(dCriteria);

		List<FundEstimationDetails> finalData = new ArrayList<FundEstimationDetails>();

		for (FundEstimationDetails fundEstimationDetails : data) {
			if (new SimpleDateFormat("dd/MMM/yy").format(fundEstimationDetails.getProjectionDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(fundEstimationDetails);
			}
		}

		// dCriteria.add(Restrictions.eq("fundEstimationDetails.projectionDate",
		// projectionDate));

		return finalData;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDays> getFundEstimationNoOfDays(Date projectionDate, BigDecimal countryId, BigDecimal bankId, BigDecimal currencyId, BigDecimal applicationCountryId, BigDecimal accountNumber) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimationDays.class, "fundEstimationDays");

		dCriteria.setFetchMode("fundEstimationDays.exFundEstimationDaysBankCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDays.exFundEstimationDaysBankCountry", "exFundEstimationDaysBankCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimationDaysBankCountry.countryId", countryId));

		dCriteria.setFetchMode("fundEstimationDays.exFundEstimationDaysApplicationCountry", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDays.exFundEstimationDaysApplicationCountry", "exFundEstimationDaysApplicationCountry", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exFundEstimationDaysApplicationCountry.countryId", applicationCountryId));

		/*
		 * dCriteria.setFetchMode("fundEstimationDays.exFundEstimationDetails",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("fundEstimationDays.exFundEstimationDetails",
		 * "exFundEstimationDetails", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions
		 * .eq("exFundEstimationDetails.bankAcctDetId", accountNumber));
		 */

		dCriteria.setFetchMode("fundEstimationDays.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDays.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("fundEstimationDays.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDays.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));

		dCriteria.setFetchMode("fundEstimationDays.fundEstimtaionId", FetchMode.JOIN);
		dCriteria.createAlias("fundEstimationDays.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order.asc("fundEstimationDays.fundEstimtaionDaysId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<FundEstimationDays> data = (List<FundEstimationDays>) findAll(dCriteria);

		List<FundEstimationDays> finalData = new ArrayList<FundEstimationDays>();

		for (FundEstimationDays fundEstimationDays : data) {
			if (new SimpleDateFormat("dd/MMM/yy").format(fundEstimationDays.getProjectionDate()).equalsIgnoreCase(new SimpleDateFormat("dd/MMM/yy").format(new Date()))) {
				finalData.add(fundEstimationDays);
			}
		}

		return finalData;

	}

	@Override
	public void saveFundEstimationDays(FundEstimationDays fundEstimationDays) {
		getSession().save(fundEstimationDays);
	}

	@Override
	public void saveAndUpdateFundEstimationDays(FundEstimationDays fundEstimationDays) {
		getSession().saveOrUpdate(fundEstimationDays);
	}

	/*
	 * @SuppressWarnings("unchecked")
	 * 
	 * @Override public String getCurrentBalance(BigDecimal
	 * applicationCountryId, BigDecimal bankCountry, BigDecimal bankId,
	 * BigDecimal currencyId) { DetachedCriteria dCriteria =
	 * DetachedCriteria.forClass(DailyBalance.class, "dailyBalance");
	 * dCriteria.setFetchMode("dailyBalance.applicationCountry",
	 * FetchMode.JOIN); dCriteria.createAlias("dailyBalance.applicationCountry",
	 * "applicationCountry", JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("applicationCountry.countryId",
	 * applicationCountryId)); dCriteria.setFetchMode("dailyBalance.bankMaster",
	 * FetchMode.JOIN); dCriteria.createAlias("dailyBalance.bankMaster",
	 * "bankMaster", JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
	 * dCriteria.setFetchMode("dailyBalance.currencyMaster", FetchMode.JOIN);
	 * dCriteria.createAlias("dailyBalance.currencyMaster", "currencyMaster",
	 * JoinType.INNER_JOIN);
	 * dCriteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
	 * List<DailyBalance> data = (List<DailyBalance>)findAll(dCriteria);
	 * if(data.size()!=0){ if(new
	 * SimpleDateFormat("dd/MM/yyyy").format(data.get(
	 * 0).getCreatedDate()).equalsIgnoreCase(new
	 * SimpleDateFormat("dd/MM/yyyy").format(new Date()))){ return
	 * data.get(0).getFundForeignCurrencyBalance().toPlainString(); } else {
	 * return null; } } else { return null; } }
	 */

	@SuppressWarnings("unchecked")
	@Override
	public String getCurrentBalance(String glSlNumber) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(AccountBalance.class, "accountBalance");
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", glSlNumber));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AccountBalance> data = (List<AccountBalance>) findAll(dCriteria);

		if (data.size() != 0) {
			if (data.get(0).getForeignBalance() != null) {
				return data.get(0).getForeignBalance().toPlainString();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getAccountNumber(BigDecimal bankId, BigDecimal currencyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAcoountDetails");

		dCriteria.setFetchMode("bankAcoountDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankAcoountDetails.fsCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankAcoountDetails.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCurrencyMaster.currencyId", currencyId));

		dCriteria.add(Restrictions.eq("bankAcoountDetails.recordStatus", Constants.Yes));
		dCriteria.addOrder(Order.asc("bankAcoountDetails.bankAcctNo"));
		/** NAG APPLY ASCENDING 04/02/2015 **/
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> data = (List<BankAccountDetails>) findAll(dCriteria);
		List<BankAccountDetails> finalData = new ArrayList<BankAccountDetails>();

		for (BankAccountDetails bankAccountDetails : data) {
			// if(new
			// SimpleDateFormat("dd/MM/yyyy").format(bankAccountDetails.getCreateDate()).equalsIgnoreCase(new
			// SimpleDateFormat("dd/MM/yyyy").format(new Date()))) {
			finalData.add(bankAccountDetails);
			// }
		}

		return finalData;
	}

	// getFundestimationDays

	@SuppressWarnings("unchecked")
	@Override
	public List<FundEstimationDays> getDaysList(BigDecimal companyId, BigDecimal applicationId, BigDecimal bankcountryId, BigDecimal ccrrencyId, BigDecimal saleprofundId, BigDecimal saleprojfundetailsId) {

		DetachedCriteria dcriteria = DetachedCriteria.forClass(FundEstimationDays.class, "FundEstimationDays");

		dcriteria.setFetchMode("FundEstimationDays.fsCompanyMaster", FetchMode.JOIN);
		dcriteria.createAlias("FundEstimationDays.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));

		dcriteria.setFetchMode("FundEstimationDays.exFundEstimationDaysApplicationCountry", FetchMode.JOIN);
		dcriteria.createAlias("FundEstimationDays.exFundEstimationDaysApplicationCountry", "exFundEstimationDaysApplicationCountry", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("exFundEstimationDaysApplicationCountry.countryId", applicationId));

		dcriteria.setFetchMode("FundEstimationDays.exFundEstimationDaysBankCountry", FetchMode.JOIN);
		dcriteria.createAlias("FundEstimationDays.exFundEstimationDaysBankCountry", "exFundEstimationDaysBankCountry", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("exFundEstimationDaysBankCountry.countryId", bankcountryId));

		dcriteria.setFetchMode("FundEstimationDays.exCurrenyMaster", FetchMode.JOIN);
		dcriteria.createAlias("FundEstimationDays.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", ccrrencyId));

		dcriteria.setFetchMode("FundEstimationDays.fundEstimtaionId", FetchMode.JOIN);
		dcriteria.createAlias("FundEstimationDays.fundEstimtaionId", "fundEstimtaionId", JoinType.INNER_JOIN);
		dcriteria.add(Restrictions.eq("fundEstimtaionId.fundEstimtaionId", saleprofundId));

		/*
		 * dcriteria.setFetchMode("FundEstimationDays.fundEstimationDetailsId",
		 * FetchMode.JOIN);
		 * dcriteria.createAlias("FundEstimationDays.fundEstimationDetailsId",
		 * "fundEstimationDetailsId", JoinType.INNER_JOIN);
		 * dcriteria.add(Restrictions
		 * .eq("fundEstimationDetailsId.fundEstimtaionDetailsId",
		 * saleprojfundetailsId));
		 */
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<FundEstimationDays> lstDetails = (List<FundEstimationDays>) findAll(dcriteria);

		return lstDetails;
	}

	// get FC Amount from TreasuryDealHeader for country, bank , currency and
	// acc no
	@SuppressWarnings("unchecked")
	@Override
	public BigDecimal getFCAmountfromFxDealDetails(BigDecimal bankId, BigDecimal currencyId, String accountNumberId) {

		BigDecimal accumulatedFcAmount = new BigDecimal(0);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster", "treasuryDealBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("treasuryDealBankMaster.bankId", bankId));

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster", "treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("treasuryDealDetailCurrencyMaster.currencyId", currencyId));

		/*
		 * dCriteria.setFetchMode(
		 * "treasuryDealDetail.treasuryDealDetailBankAccountDetails",
		 * FetchMode.JOIN); dCriteria.createAlias(
		 * "treasuryDealDetail.treasuryDealDetailBankAccountDetails",
		 * "treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);
		 * dCriteria
		 * .add(Restrictions.eq("treasuryDealDetailBankAccountDetails.bankAcctDetId"
		 * , accountNumberId));
		 */

		dCriteria.add(Restrictions.eq("treasuryDealDetail.faAccountNo", accountNumberId));

		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", "SD"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> data = (List<TreasuryDealDetail>) findAll(dCriteria);

		if (data.size() != 0) {
			for (TreasuryDealDetail treasuryDealDetail : data) {
				accumulatedFcAmount = accumulatedFcAmount.add(treasuryDealDetail.getFcAmount());
			}
		}
		return accumulatedFcAmount;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCountryNameDesc(BigDecimal countryId, BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CountryMasterDesc> countrylist = (List<CountryMasterDesc>) findAll(dCriteria);

		return countrylist.get(0).getCountryName();
	}

	/**
	 * Author :rabil Date :11/08/2015 Purpose:To get exchange bank country
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BankCountryPopulationBean> getBankContryFromView(BigDecimal countryId) {
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankCountryPopulationBean> lstBankCountryPopulationBean = new ArrayList<BankCountryPopulationBean>();
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryBankCountryView.class, "treasuryBankCountryView");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));

		List<TreasuryBankCountryView> lstViewBankCountry = (List<TreasuryBankCountryView>) findAll(dCriteria);
		for (TreasuryBankCountryView treasuryBankCountryView : lstViewBankCountry) {
			BankCountryPopulationBean bankCountryPopulationBean = new BankCountryPopulationBean(treasuryBankCountryView.getBankCountryId(), treasuryBankCountryView.getBankCountryName(),treasuryBankCountryView.getBankCountryCode());
			lstBankCountryPopulationBean.add(bankCountryPopulationBean);
		}

		return lstBankCountryPopulationBean;
	}

	/**
	 * Author :rabil Date :11/08/2015 Purpose:To get exchange bank country
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<CountryCurrencyPopulationBean> getBankCurrencyFromView(BigDecimal countryId, BigDecimal bankCountryId) {
		List<CountryCurrencyPopulationBean> lstCountryCurrencyPopulationBean = new ArrayList<CountryCurrencyPopulationBean>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCurrencyView.class, "treasuryBankCurrencyView");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("bankCountryId", bankCountryId));
		List<TreasuryCurrencyView> lstViewBankCurrency = (List<TreasuryCurrencyView>) findAll(dCriteria);

		for (TreasuryCurrencyView treasuryBankCurrencyView : lstViewBankCurrency) {
			CountryCurrencyPopulationBean bankCurrencyPopulationBean = new CountryCurrencyPopulationBean(treasuryBankCurrencyView.getCurrencyId(), treasuryBankCurrencyView.getCurrencyName(), treasuryBankCurrencyView.getCurrencyCode());
			lstCountryCurrencyPopulationBean.add(bankCurrencyPopulationBean);
		}
		return lstCountryCurrencyPopulationBean;
	}

	/**
	 * Author :rabil Date :11/08/2015 Purpose:To get exchange bank country
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryFundestimationView> getFundEstimationFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryFundestimationView.class, "treasuryFundestimationView");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("bankCountryId", bankCountryId));
		dCriteria.add(Restrictions.eq("currencyId", currencyId));
		List<TreasuryFundestimationView> lstFundEstimation = (List<TreasuryFundestimationView>) findAll(dCriteria);
		return lstFundEstimation;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryEstimationDaysView> getEstimationDaysFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate) {
		
		System.out.println("projectionDate  :"+projectionDate);
		String sqlDate =" PROJECTION_DATE=to_date('"+projectionDate+"','dd/MM/yyyy')";
		System.out.println("sqlDate :"+sqlDate);
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryEstimationDaysView.class, "treasuryFundestimationDaysView");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("bankCountryId", bankCountryId));
		dCriteria.add(Restrictions.eq("currencyId", currencyId));
		//dCriteria.add(Restrictions.sqlRestriction(sqlDate));
		dCriteria.add(Restrictions.eq("projectionDate", projectionDate));
		List<TreasuryEstimationDaysView> lstFundEstimationDays = (List<TreasuryEstimationDaysView>) findAll(dCriteria);
		return lstFundEstimationDays;
	}

	/**
	 * Author: Rabil Date :20/08/2015
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryFundestimationAlertView> getEstimationDealAlertFromView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId, String projectionDate) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryFundestimationAlertView.class, "treasuryFundestimationDealAlertView");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("bankCountryId", bankCountryId));
		dCriteria.add(Restrictions.eq("currencyId", currencyId));
		dCriteria.add(Restrictions.eq("projectionDate", projectionDate));
		List<TreasuryFundestimationAlertView> lstFundEstimationDealAlert = (List<TreasuryFundestimationAlertView>) findAll(dCriteria);
		return lstFundEstimationDealAlert;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryFundEstimationDealAlertView> getEstimationDealAlert(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal currencyId, String projectionDate, BigDecimal fundEstimationId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryFundEstimationDealAlertView.class, "fundEstimationAlert");
		dCriteria.add(Restrictions.eq("applicationCountryId", countryId));
		dCriteria.add(Restrictions.eq("bankCountryId", bankCountryId));
		dCriteria.add(Restrictions.eq("bankId", bankId));
		dCriteria.add(Restrictions.eq("currencyId", currencyId));
		// dCriteria.add(Restrictions.eq("projectionDate", projectionDate));
		dCriteria.add(Restrictions.eq("fundEstimtaionId", fundEstimationId));
		dCriteria.add(Restrictions.eq("isActive", "Y"));
		List<TreasuryFundEstimationDealAlertView> lstFundEstimationDealAlert = (List<TreasuryFundEstimationDealAlertView>) findAll(dCriteria);
		return lstFundEstimationDealAlert;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveFundEstimationAlert(FundEstimationAlert fundEstimationAlert) {
		getSession().save(fundEstimationAlert);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAndUpadateFundEstimationAlert(FundEstimationAlert fundEstimationAlert) {
		getSession().saveOrUpdate(fundEstimationAlert);

	}
	
	public List<TreasuryFundestimationView> getDetaillsForFundEstimationView(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal currencyId){
			DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryFundestimationView.class, "treasuryFundestimationView");
			dCriteria.add(Restrictions.eq("treasuryFundestimationView.applicationCountryId", countryId));
				if(bankCountryId!=null){
				dCriteria.add(Restrictions.eq("treasuryFundestimationView.bankCountryId", bankCountryId));
				}
				if(currencyId!=null){
				dCriteria.add(Restrictions.eq("treasuryFundestimationView.currencyId", currencyId));
				}
			List<TreasuryFundestimationView> lstFundEstimation = (List<TreasuryFundestimationView>) findAll(dCriteria);
			return lstFundEstimation;
	}

	@Override
	public List<TreasuryFundestimationViewSummary> getDetaillsForFundEstimationViewSummary(BigDecimal countryId, BigDecimal bankCountryId,BigDecimal currencyId,Date projectionDate) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryFundestimationViewSummary.class, "treasuryFundestimationViewSummary");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String sysdate=dateformat.format(projectionDate);
		String sqlDate =" PROJECTION_DATE=to_date('"+sysdate+"','dd/MM/yy')";
		
		dCriteria.add(Restrictions.eq("treasuryFundestimationViewSummary.applicationCountryId", countryId));
		if(bankCountryId!=null){
			dCriteria.add(Restrictions.eq("treasuryFundestimationViewSummary.bankCountryId", bankCountryId));
		}
		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("treasuryFundestimationViewSummary.currencyId", currencyId));
		}
		dCriteria.add(Restrictions.sqlRestriction(sqlDate));
		
		List<TreasuryFundestimationViewSummary> lstFundEstimation = (List<TreasuryFundestimationViewSummary>) findAll(dCriteria);
		return lstFundEstimation;
	}

	@Override
	public BigDecimal fetchIKONRate() {
		LOGGER.info("fetchIKONRate method");
		LOGGER.info("RECORD_ID :" + Constants.IKON);
		LOGGER.info("PARAM_CODE_DEF :" + Constants.USD);
		BigDecimal ikonRateValue = null;
		String sqlQuery ="SELECT NUMERIC_FIELD1 FROM EX_PARAMETER_dETAILS WHERE RECORD_ID='"+Constants.IKON+"' AND PARAM_CODE_DEF='"+Constants.USD+"' ";
		LOGGER.info("fetchIKONRate :"+sqlQuery);
		SQLQuery query = getSession().createSQLQuery(sqlQuery.trim());
		List<BigDecimal> rows = query.list();
		List list = query.list();
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			BigDecimal row = (BigDecimal) iterator.next();
			ikonRateValue = row;
		}
		LOGGER.info("IKONRate Value USD :" + ikonRateValue);
		return ikonRateValue;
	}

	@Override
	public List<FundConsolidatedView> fetchFundConsolidatedViewRecords(Date projectionDate, BigDecimal exchangeCountryId,BigDecimal bankCountryId, BigDecimal bankId, BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundConsolidatedView.class, "fundConsolidatedView");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String sysdate=dateformat.format(projectionDate);
		String sqlDate =" PROJECTION_DATE=to_date('"+sysdate+"','dd/MM/yy')";
		
		dCriteria.add(Restrictions.eq("fundConsolidatedView.applicationCountryId", exchangeCountryId));
		
		if(bankCountryId!=null){
			dCriteria.add(Restrictions.eq("fundConsolidatedView.bankCountryId", bankCountryId));
		}
		if(bankId!=null){
			dCriteria.add(Restrictions.eq("fundConsolidatedView.bankId", bankId));
		}
		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("fundConsolidatedView.currencyId", currencyId));
		}
		dCriteria.add(Restrictions.sqlRestriction(sqlDate));
		
		List<FundConsolidatedView> lstFundEstimation = (List<FundConsolidatedView>) findAll(dCriteria);
		
		return lstFundEstimation;
	}

	@Override
	public HashMap<String, String> getExProjectionFutureProcedure(BigDecimal exchangeCountryId, BigDecimal bankCountryId, BigDecimal bankId,
			BigDecimal currencyId, BigDecimal serviceMasterId,Date projectionDate) throws AMGException 
		{
			// TODO Auto-generated method stub
			HashMap<String, String> hashMap = null;
			LOGGER.info("!!!!!!EX_P_GET_PROJECTIONS  exchangeCountryId  IN!!!!!!!!!" + exchangeCountryId);
			LOGGER.info("!!!!!! bankCountryId  IN!!!!!!!!!" + bankCountryId);
			LOGGER.info("!!!!!! bankId  IN!!!!!!!!!" + bankId);
			LOGGER.info("!!!!!! currencyId  IN!!!!!!!!!" + currencyId);
			LOGGER.info("!!!!!! serviceMasterId  IN!!!!!!!!!" + serviceMasterId);
			LOGGER.info("!!!!!! projectionDate  IN!!!!!!!!!" + projectionDate);
			
			
			Connection connection = null;
			try {
			
				connection = getDataSourceFromHibernateSession();
			} catch (Exception e) {
				e.printStackTrace();
			}
			CallableStatement cs;
			try {
				
				hashMap  = new HashMap<String, String>();
				
				String call = " { call EX_P_GET_PROJECTIONS (?, ?, ?, ?, ?, ?, ?, ?, ?) } ";
				cs = connection.prepareCall(call);
				cs.setBigDecimal(1, exchangeCountryId);
				cs.setBigDecimal(2, bankCountryId);
				cs.setBigDecimal(3, currencyId);
				cs.setBigDecimal(4, serviceMasterId);
				cs.setBigDecimal(5, bankId);
				cs.setDate(6, new java.sql.Date(projectionDate.getTime()));
				
				cs.registerOutParameter(7, java.sql.Types.INTEGER);
				cs.registerOutParameter(8, java.sql.Types.INTEGER);
				cs.registerOutParameter(9, java.sql.Types.INTEGER);
				
				cs.execute();// teUpdate();
				BigDecimal out1 = cs.getBigDecimal(7);
				BigDecimal out2 = cs.getBigDecimal(8);
				BigDecimal out3 = cs.getBigDecimal(9);
			
				hashMap.put("P_FUND_ESTIMATION_ID",out1.toString());
				hashMap.put("P_Sales_projection_amt",out2.toString());
				hashMap.put("P_USD_VAL_SALES_PROJECTION",out3.toString());
				
			} catch (SQLException e) {
				LOGGER.info("Problem Occured When Procedure Calling="+e.getMessage());
				String erromsg = "EX_P_GET_PROJECTIONS" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} finally {
				try {
					connection.close();
				} catch (SQLException e) {
					LOGGER.info("Problem Occured When Procedure Calling="+e.getMessage());
					String erromsg = "EX_P_GET_PROJECTIONS" + " : " + e.getMessage();
					throw new AMGException(erromsg);
				}
			}
			LOGGER.info("!!!!!!outLst out!!!!!!!!!" + hashMap);
			LOGGER.info("Exited from the getExchangeRateAllValues()  Method ");
		
			
			return hashMap;
		}

	@Override
	public List<FundEstimation> getFundEstimationDataForTomCash(
			BigDecimal appCountryId, BigDecimal bankCountryId,
			BigDecimal currencyId, BigDecimal bankId, Date projectionDate,
			BigDecimal fundEstimationId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundEstimation.class, "fundEstimation");
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		String sysdate=dateformat.format(projectionDate);
		String sqlDate =" PROJECTION_DATE=to_date('"+sysdate+"','dd/MM/yy')";
		
		dCriteria.add(Restrictions.eq("fundEstimation.applicationCountryId", appCountryId));
		
		if(bankCountryId!=null){
			dCriteria.add(Restrictions.eq("fundEstimation.bankCountryId", bankCountryId));
		}
		if(bankId!=null){
			dCriteria.add(Restrictions.eq("fundEstimation.bankId", bankId));
		}
		if(currencyId!=null){
			dCriteria.add(Restrictions.eq("fundEstimation.currencyId", currencyId));
		}
		dCriteria.add(Restrictions.eq("fundEstimation.fundEstimtaionId", fundEstimationId));
	 
		
		dCriteria.add(Restrictions.sqlRestriction(sqlDate));
		
		List<FundEstimation> lstFundEstimation = (List<FundEstimation>) findAll(dCriteria);
 return lstFundEstimation;
	}
	
}
