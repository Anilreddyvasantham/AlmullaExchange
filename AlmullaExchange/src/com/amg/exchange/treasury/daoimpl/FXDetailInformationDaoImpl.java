package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.RoutingHeader;
import com.amg.exchange.treasury.bean.FXDetailInfoBean;
import com.amg.exchange.treasury.dao.IFXDetailInformationDao;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
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
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("serial")
@Repository
public class FXDetailInformationDaoImpl<T> extends CommonDaoImpl<T> implements
		IFXDetailInformationDao<T>, Serializable {

	Logger log = Logger.getLogger(FXDetailInfoBean.class);
	SessionStateManage sessionManage = new SessionStateManage();

	@SuppressWarnings("unchecked")
	@Override
	public List<CompanyMasterDesc> getCompanyList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(
				CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster",
				FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster",
				"fsCompanyMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("companyMasterDesc.fsLanguageType",
				FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType",
				"fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CompanyMasterDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CurrencyMaster> getCurrencyList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				CurrencyMaster.class, "currencyMaster");

		criteria.addOrder(Order.asc("currencyMaster.currencyCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CurrencyMaster>) findAll(criteria);
	}

	// CR getting Instruction Number and Description from DB

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getInstrnNumberDesc(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId,String instrnNumber) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");


		dCriteria.setFetchMode("standardInstruction.exBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster","exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria .add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId","bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria .add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", bankAccountDetId));

		dCriteria.add(Restrictions.eq("standardInstruction.intructionType", instrnNumber));

		// dCriteria.add(Restrictions.eq("standardInstruction.isActive",
		// Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<StandardInstruction> pdata = (List<StandardInstruction>) findAll(dCriteria);

		
		return pdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getInstrnNumber(BigDecimal bankId,BigDecimal currencyId,BigDecimal bankAccountDetId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");

		dCriteria.setFetchMode("standardInstruction.exBankMaster",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster","exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId",	FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId","bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", bankAccountDetId));

		dCriteria.add(Restrictions.eq("standardInstruction.isActive",Constants.Yes));

		dCriteria.addOrder(Order.asc("standardInstruction.standardInstructionId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<StandardInstruction> pdata = (List<StandardInstruction>) findAll(dCriteria);


		return pdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstructionDetails> getInstructionsFromDetails(
			BigDecimal bankId, BigDecimal currencyId, String isActive,
			BigDecimal instrnNumber ,BigDecimal bankAccDetId,String instrnType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				StandardInstructionDetails.class, "standardInstructionDetails");

		System.out.println("***************" + bankId + " @@  " + currencyId + " @@ " + instrnNumber);

		/*
		 * dCriteria.setFetchMode("standardInstruction.fsCompanyMaster",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("standardInstruction.fsCompanyMaster",
		 * "fsCompanyMaster", JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId",
		 * companyId));
		 */

		dCriteria.setFetchMode("standardInstructionDetails.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("standardInstructionDetails.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId));

		
		
		
		dCriteria.add(Restrictions.eq("standardInstructionDetails.isActive",isActive));
		dCriteria.add(Restrictions.eq("standardInstructionDetails.intructionType",instrnType));

		dCriteria.setFetchMode("standardInstructionDetails.exstandardInstructionId",FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.exstandardInstructionId","exstandardInstructionId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exstandardInstructionId.standardInstructionId",instrnNumber));
		
		dCriteria.setFetchMode("standardInstructionDetails.bankAccountDetailsId",FetchMode.JOIN);
		dCriteria.createAlias("standardInstructionDetails.bankAccountDetailsId","bankAccountDetailsId", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId",bankAccDetId));
															   
		dCriteria.addOrder(Order.asc("standardInstructionDetails.standardInstrnDetailsId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<StandardInstructionDetails> pdata = (List<StandardInstructionDetails>) findAll(dCriteria);

		System.out
				.println("size ::::::::::::::::::getInstructionsFromDetails:::::::::::::::::::::::::"
						+ pdata.size());

		return pdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getDealBank(BigDecimal Id) {

		DetachedCriteria dCriteria1 = DetachedCriteria.forClass(
				BankApplicability.class, "applicabity");

		dCriteria1.setFetchMode("applicabity.bankMaster", FetchMode.JOIN);
		dCriteria1.createAlias("applicabity.bankMaster", "bankMaster",
				JoinType.INNER_JOIN);

		BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);

		dCriteria1.setFetchMode("applicabity.bankInd", FetchMode.JOIN);
		dCriteria1.createAlias("applicabity.bankInd", "bankInd",
				JoinType.INNER_JOIN);

		if (localBankIndicatorId != null) {
			dCriteria1.add(Restrictions.not(Restrictions.eq(
					"bankInd.bankIndicatorId", localBankIndicatorId)));
		}

		dCriteria1.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBankApplicability = (List<BankApplicability>) findAll(dCriteria1);

		List<BigDecimal> lstBankId = new ArrayList<BigDecimal>();

		for (BankApplicability bankApplicability : lstBankApplicability) {
			lstBankId.add(bankApplicability.getBankMaster().getBankId());
		}

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankMaster.class, "bankMaster");
		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	public BigDecimal fetchLocalBankIndicator(String bankIndicatorCode) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankIndicator.class, "bankIndicator");

		dCriteria.add(Restrictions.eq("bankIndicator.bankIndicatorCode",
				bankIndicatorCode));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankIndicator> lstBankIndId = (List<BankIndicator>) findAll(dCriteria);

		if (lstBankIndId.size() != 0) {
			return ((List<BankIndicator>) findAll(dCriteria)).get(0)
					.getBankIndicatorId();
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReq(
			String fundingOptionValue, BigDecimal bankId, BigDecimal currencyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CustomerSpecialDealRequest.class, "customerSpecialDealRequest");

		dCriteria
				.add(Restrictions.eq(
						"customerSpecialDealRequest.fundingOption",
						fundingOptionValue));

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.customerSpeacialDealReqBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.customerSpeacialDealReqBankMaster",
				"customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq(
				"customerSpeacialDealReqBankMaster.bankId", bankId));

		dCriteria
				.setFetchMode(
						"customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",
						FetchMode.JOIN);
		dCriteria
				.createAlias(
						"customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",
						"customerSpeacialDealReqCurrencyMaster",
						JoinType.INNER_JOIN);
		dCriteria
				.add(Restrictions.eq(
						"customerSpeacialDealReqCurrencyMaster.currencyId",
						currencyId));

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.customerSpeacialDealReqCustomer",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.customerSpeacialDealReqCustomer",
				"customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order
				.asc("customerSpecialDealRequest.customerSpecialDealReqId"));

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.customerSpeacialDealReqDocument",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.customerSpeacialDealReqDocument",
				"customerSpeacialDealReqDocument", JoinType.INNER_JOIN);

		dCriteria
				.setFetchMode(
						"customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster",
						FetchMode.JOIN);
		dCriteria
				.createAlias(
						"customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster",
						"customerSpeacialDealReqCompanyMaster",
						JoinType.INNER_JOIN);

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.documentFinancialYear",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.documentFinancialYear",
				"documentFinancialYear", JoinType.INNER_JOIN);

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CustomerSpecialDealRequest> pdata = (List<CustomerSpecialDealRequest>) findAll(dCriteria);

		System.out
				.println("size ::::::::::::::::::getDataTableFromCustomerSpecialDealReq:::::::::::::::::::::::::"
						+ pdata.size());

		return pdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getDataTableFromCustomerSpecialDealReqAccCurrency(
			String fundingOptionValue, BigDecimal currencyId)
			throws ParseException {

		List<CustomerSpecialDealRequest> finalList = new ArrayList<CustomerSpecialDealRequest>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CustomerSpecialDealRequest.class, "customerSpecialDealRequest");

		dCriteria
				.add(Restrictions.eq(
						"customerSpecialDealRequest.fundingOption",
						fundingOptionValue));

		/*
		 * dCriteria.setFetchMode(
		 * "customerSpecialDealRequest.customerSpeacialDealReqBankMaster",
		 * FetchMode.JOIN); dCriteria.createAlias(
		 * "customerSpecialDealRequest.customerSpeacialDealReqBankMaster",
		 * "customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		 * dCriteria.add
		 * (Restrictions.eq("customerSpeacialDealReqBankMaster.bankId",
		 * bankId));
		 */

		dCriteria
				.setFetchMode(
						"customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",
						FetchMode.JOIN);
		dCriteria
				.createAlias(
						"customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",
						"customerSpeacialDealReqCurrencyMaster",
						JoinType.INNER_JOIN);
		dCriteria
				.add(Restrictions.eq(
						"customerSpeacialDealReqCurrencyMaster.currencyId",
						currencyId));

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.customerSpeacialDealReqCustomer",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.customerSpeacialDealReqCustomer",
				"customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order
				.asc("customerSpecialDealRequest.customerSpecialDealReqId"));

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.customerSpeacialDealReqDocument",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.customerSpeacialDealReqDocument",
				"customerSpeacialDealReqDocument", JoinType.INNER_JOIN);

		dCriteria
				.setFetchMode(
						"customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster",
						FetchMode.JOIN);
		dCriteria
				.createAlias(
						"customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster",
						"customerSpeacialDealReqCompanyMaster",
						JoinType.INNER_JOIN);

		dCriteria.setFetchMode(
				"customerSpecialDealRequest.documentFinancialYear",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"customerSpecialDealRequest.documentFinancialYear",
				"documentFinancialYear", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions
				.isNotNull("customerSpecialDealRequest.approvedBy"));
		dCriteria.add(Restrictions
				.isNotNull("customerSpecialDealRequest.approvedDate"));

		dCriteria.add(Restrictions
				.isNull("customerSpecialDealRequest.dealCompanyId"));
		dCriteria.add(Restrictions.isNull("customerSpecialDealRequest.dealId"));
		dCriteria.add(Restrictions
				.isNull("customerSpecialDealRequest.dealFinanceYear"));
		dCriteria.add(Restrictions
				.isNull("customerSpecialDealRequest.dealApplicationNumber"));

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		// String currentDate = formatter.format(new Date());

		Date validUpto = formatter.parse(formatter.format(new Date()));

		dCriteria.add(Restrictions.ge("customerSpecialDealRequest.validUpto",
				validUpto));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CustomerSpecialDealRequest> pdata = (List<CustomerSpecialDealRequest>) findAll(dCriteria);

		List<BankApplicability> lstBeneBankList = checkingBankBeneficiary();

		for (CustomerSpecialDealRequest splcustlist : pdata) {

			for (BankApplicability bankApplicability : lstBeneBankList) {

				if (bankApplicability
						.getBankMaster()
						.getBankId()
						.compareTo(
								splcustlist
										.getCustomerSpeacialDealReqBankMaster()
										.getBankId()) == 0) {
					finalList.add(splcustlist);
					break;
				}

			}

		}

		return finalList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getFCAmountOfCommonPool(
			String fundingOptionValue) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CustomerSpecialDealRequest.class, "customerSpecialDealRequest");

		dCriteria
				.add(Restrictions.eq(
						"customerSpecialDealRequest.fundingOption",
						fundingOptionValue));

		dCriteria.addOrder(Order
				.asc("customerSpecialDealRequest.customerSpecialDealReqId"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CustomerSpecialDealRequest> pdata = (List<CustomerSpecialDealRequest>) findAll(dCriteria);

		return pdata;

	}

	@Override
	public void saveHeader(TreasuryDealHeader treasuryDealHeader) {
		getSession().saveOrUpdate(treasuryDealHeader);

	}

	@Override
	public void savePurchase(TreasuryDealDetail treasuryDetailPurchase) {
		getSession().saveOrUpdate(treasuryDetailPurchase);

	}

	@Override
	public void saveSale(TreasuryDealDetail treasuryDetailSale) {
		getSession().saveOrUpdate(treasuryDetailSale);

	}

	@Override
	public void savePurchaseStandardInst(
			TreasuryStandardInstruction standardInstructionPurchase) {
		getSession().saveOrUpdate(standardInstructionPurchase);

	}

	@Override
	public void saveSaleStandardInst(
			TreasuryStandardInstruction standardInstructionSale) {
		getSession().saveOrUpdate(standardInstructionSale);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBalance> getSaleAvgRate(BigDecimal bankId,
			BigDecimal currencyId, String accountNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				AccountBalance.class, "accountBalance");

		/*
		 * dCriteria.setFetchMode("accountBalance.bankMaster", FetchMode.JOIN);
		 * dCriteria.createAlias("accountBalance.bankMaster", "bankMaster",
		 * JoinType.INNER_JOIN);
		 * dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		 */

		SessionStateManage sessionManage = new SessionStateManage();
		dCriteria.setFetchMode("accountBalance.companyMaster", FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.companyMaster", "companyMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("companyMaster.companyId",
				sessionManage.getCompanyId()));

		dCriteria.setFetchMode("accountBalance.exCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exCurrencyMaster",
				"exCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions
				.eq("exCurrencyMaster.currencyId", currencyId));

		// dCriteria.add(Restrictions.eq("accountBalance.accountNo",
		// accountNo));
		// dCriteria.setFetchMode("accountBalance.exBankAccountDetails",
		// FetchMode.JOIN);
		// dCriteria.createAlias("accountBalance.exBankAccountDetails",
		// "exBankAccountDetails", JoinType.INNER_JOIN);
		
		// comparing with Fund GLNO
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber", accountNo));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<AccountBalance> data = (List<AccountBalance>) findAll(dCriteria);

		return data;
	}

	@Override
	public List<TreasuryDealHeader> getPopulateRecord(BigDecimal refNumber) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDeal");
		dCriteria.setFetchMode("treasuryDeal.exDealDetail", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDeal.exDealDetail", "exDealDetail",
				JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("treasuryDeal.treasuryDocumentNumber",
				refNumber));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealHeader>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,	BigDecimal refNumber, String dealWithType,BigDecimal documentYear,BigDecimal companyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");

		dCriteria.setFetchMode("treasuryDealHeader.exDealDetail",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDealDetail",
				"exDealDetail", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealHeader.exDocument", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDocument", "exDocument",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exDocument.documentID", documentNo));

		dCriteria.add(Restrictions.eq(
				"treasuryDealHeader.treasuryDocumentNumber", refNumber));

		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",
				dealWithType));
		
		dCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear",	documentYear));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealHeader>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getDetailsPurchase(BigDecimal headerId,
			String lineType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader",
				"treasuryDealHeader", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq(
				"treasuryDealHeader.treasuryDealHeaderId", headerId));

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMasterr",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster",
				"treasuryDealCompanyMaster", JoinType.INNER_JOIN);

		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , "treasuryDealUserFinanceYear", JoinType.INNER_JOIN);
		 */

		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				"treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);

		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				"treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions
				.isNull("treasuryDealDetail.specialRequestDocumentCode"));

		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", lineType));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealDetail>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getDetailsSpecialPool(BigDecimal headerId,
			String lineType,BigDecimal documentNo,BigDecimal documentYear,BigDecimal companyId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");

		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader",
		 * "treasuryDealHeader", JoinType.INNER_JOIN);
		 */

		dCriteria.setFetchMode("treasuryDealDetail.customerSpecialDealRequest",
				FetchMode.JOIN);// AAA
		dCriteria.createAlias("treasuryDealDetail.customerSpecialDealRequest",
				"customerSpecialDealRequest", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("treasuryDealDetail.documentNumber",
				headerId));

		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", lineType));

		dCriteria.add(Restrictions.eq("treasuryDealDetail.isActive",
				Constants.U));

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster",
				"treasuryDealCompanyMaster", JoinType.INNER_JOIN);

		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , "treasuryDealUserFinanceYear", JoinType.INNER_JOIN);
		 */
		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				"treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);

		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.accountBalance",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.accountBalance",
		 * "accountBalance", JoinType.INNER_JOIN);
		 */

		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				"treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealDetail>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getDetailsSale(BigDecimal headerId,
			String lineType) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader",
				"treasuryDealHeader", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq(
				"treasuryDealHeader.treasuryDealHeaderId", headerId));

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster",
				"treasuryDealCompanyMaster", JoinType.INNER_JOIN);

		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.treasuryDealUserFinanceYear"
		 * , "treasuryDealUserFinanceYear", JoinType.INNER_JOIN);
		 */
		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				"treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealDetail.accountBalance",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.accountBalance",
				"accountBalance", JoinType.INNER_JOIN);

		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				"treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType", lineType));
		dCriteria.addOrder(Order.asc("accountBalance.accountNo"));
		/** NAG CODE APPLY ASCENDING ORDER **/

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealDetail>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForPurchase(
			BigDecimal detailsId, String lineType, Boolean valueCheck) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryStandardInstruction.class, "treasuryStanInstruction");

		dCriteria.setFetchMode("treasuryStanInstruction.treasuryDealDetail",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryStanInstruction.treasuryDealDetail",
				"treasuryDealDetail", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.eq(
				"treasuryDealDetail.treasuryDealDetailId", detailsId));

		/*
		 * dCriteria.setFetchMode(
		 * "treasuryStanInstruction.treasuryDealDetail.standardInstructionId",
		 * FetchMode.JOIN); dCriteria.createAlias(
		 * "treasuryStanInstruction.treasuryDealDetail.standardInstructionId",
		 * "standardInstructionId", JoinType.INNER_JOIN);
		 */
		dCriteria.add(Restrictions.eq("treasuryStanInstruction.lineType",
				lineType));

		if (valueCheck) {
			dCriteria.add(Restrictions.eq(
					"treasuryStanInstruction.messageLineNumber",
					new BigDecimal(0)));
		} else {
			dCriteria.add(Restrictions.ne(
					"treasuryStanInstruction.messageLineNumber",
					new BigDecimal(0)));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryStandardInstruction>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryStandardInstruction> getTreasuryStandardInstructionsForSale(
			BigDecimal detailsId, String lineType, Boolean valueCheck) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryStandardInstruction.class, "treasuryStanInstruction");

		dCriteria.setFetchMode("treasuryStanInstruction.treasuryDealDetail",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryStanInstruction.treasuryDealDetail",
				"treasuryDealDetail", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq(
				"treasuryDealDetail.treasuryDealDetailId", detailsId));

		/*
		 * dCriteria.setFetchMode(
		 * "treasuryStanInstruction.treasuryDealDetail.standardInstructionId",
		 * FetchMode.JOIN); dCriteria.createAlias(
		 * "treasuryStanInstruction.treasuryDealDetail.standardInstructionId",
		 * "standardInstructionId", JoinType.INNER_JOIN);
		 */
		dCriteria.add(Restrictions.eq("treasuryStanInstruction.lineType",
				lineType));
		if (valueCheck) {
			dCriteria.add(Restrictions.eq(
					"treasuryStanInstruction.messageLineNumber",
					new BigDecimal(0)));
		} else {
			dCriteria.add(Restrictions.ne(
					"treasuryStanInstruction.messageLineNumber",
					new BigDecimal(0)));
		}

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryStandardInstruction>) findAll(dCriteria);
	}

	@Override
	public String getUserFinancialBeginDate(Date currentDate)
			throws ParseException {

		String dealYear = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				UserFinancialYear.class, "userFinancialYear");

		List<UserFinancialYear> pdata = (List<UserFinancialYear>) findAll(dCriteria);

		for (UserFinancialYear userFinancialYear : pdata) {

			Date date1 = currentDate;
			Date date2 = userFinancialYear.getFinancialYearBegin();
			Date date3 = userFinancialYear.getFinancialYearEnd();
			String d1 = format.format(date1);
			String d2 = format.format(date2);
			String d3 = format.format(date3);
			date1 = format.parse(d1);
			date2 = format.parse(d2);
			date3 = format.parse(d3);

			if (date1.after(date2)) {
				if (date1.before(date3)) {
					dealYear = userFinancialYear.getFinancialYear().toString();
				}
			}

		}

		return dealYear;
	}

	@Override
	public List<BankAccountDetails> getCurrencyBasedOnBankCountry(
			BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountDetails");

		dCriteria.setFetchMode("bankAccountDetails.exBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster",
				"exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster",
				"fsCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order.asc("fsCurrencyMaster.currencyName"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<BankAccountDetails>) findAll(dCriteria);

	}

	@Override
	public List<BankAccountDetails> getBankCountryBasedOnBank(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountDetails");
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster",
				"exBankMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("bankAccountDetails.fsCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.fsCurrencyMaster",
				"fsCurrencyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankAccountDetails> pdata = (List<BankAccountDetails>) findAll(dCriteria);

		System.out
				.println("size ::::::::::::::::::getBankCountryBasedOnBank:::::::::::::::::::::::::"
						+ pdata.size());

		return pdata;

	}

	@Override
	public List<RoutingHeader> checkBankisActiveinRoutingSetupMaster(
			BigDecimal bankcountryId, BigDecimal bankId, BigDecimal currencyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				RoutingHeader.class, "routingHeader");
		dCriteria.setFetchMode("routingHeader.exRoutingCountryId",
				FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingCountryId",
				"exRoutingCountryId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingCountryId.countryId",
				bankcountryId));

		dCriteria.setFetchMode("routingHeader.exRoutingBankId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exRoutingBankId",
				"exRoutingBankId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exRoutingBankId.bankId", bankId));

		dCriteria.setFetchMode("routingHeader.exCurrenyId", FetchMode.JOIN);
		dCriteria.createAlias("routingHeader.exCurrenyId", "exCurrenyId",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyId.currencyId", currencyId));

		dCriteria.add(Restrictions.eq("routingHeader.isActive", Constants.Yes));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RoutingHeader> pdata = (List<RoutingHeader>) findAll(dCriteria);

		System.out
				.println("size ::::::::::::::::::checkBankisActiveinRoutingSetupMaster:::::::::::::::::::::::::"
						+ pdata.size());

		return pdata;

	}

	@Override
	public List<CustomerSpecialDealRequest> getCustomerSpecialDealRequestFromID(
			BigDecimal custspreqstID) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		dCriteria.add(Restrictions
				.eq("customerSpecialDealReqId", custspreqstID));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CustomerSpecialDealRequest>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo) throws Exception {
		// TODO Auto-generated method stub
		TreasuryDealHeader saveTreasuryDH = (TreasuryDealHeader) saveMapInfo.get("TreasuryHeader");
		Boolean checkPurchaseTreasuryDetails = (Boolean) saveMapInfo.get("skipPurchaseTreasuryDetails");
		TreasuryDealDetail savePurchaseTDetails = (TreasuryDealDetail) saveMapInfo.get("PurchaseTreasuryDetails");
		// TreasuryStandardInstruction savePurchaseSI =
		// (TreasuryStandardInstruction)saveMapInfo.get("PurchaseStandInstrn");
		Boolean checkPurchaseCondition = (Boolean) saveMapInfo.get("skipUpdatingTresSIPurchase");
		List<TreasuryStandardInstruction> lstPurchaseTSIOld = (List<TreasuryStandardInstruction>) saveMapInfo.get("lstTresSIToDeletePurchase");
		List<TreasuryStandardInstruction> lstPurchaseTSI = (List<TreasuryStandardInstruction>) saveMapInfo.get("ListPurchaseTreasurySI");
		List<TreasuryDealDetail> lstPurchaseSplCustDt = (List<TreasuryDealDetail>) saveMapInfo.get("ListPurchaseSplCustomer");
		TreasuryDealDetail saveSaleTDetails = (TreasuryDealDetail) saveMapInfo.get("SaleTreasuryDetails");
		// TreasuryStandardInstruction saveSaleSI =
		// (TreasuryStandardInstruction)saveMapInfo.get("SaleTreasurySInstrn");
		Boolean checkSaleCondition = (Boolean) saveMapInfo.get("skipUpdatingTresSISale");
		List<TreasuryStandardInstruction> lstSaleTSIOld = (List<TreasuryStandardInstruction>) saveMapInfo.get("lstTresSIToDeleteSale");
		List<TreasuryStandardInstruction> lstSaleTSI = (List<TreasuryStandardInstruction>) saveMapInfo.get("ListSaleTreasurySI");
		Boolean checkDeleteRecordSplPool = (Boolean) saveMapInfo.get("skipDeleteSplPoolTrDls");
		List<BigDecimal> lstClearSplCust = (List<BigDecimal>) saveMapInfo.get("clearCusSplPoolDetails");
		List<BigDecimal> lstdeleteSplCust = (List<BigDecimal>) saveMapInfo.get("deleteCusSplPoolDetails");

		SessionStateManage sessionManage = new SessionStateManage();

		try {
			// save the TreasuryDealHeader
			getSession().saveOrUpdate(saveTreasuryDH);

			// save the TreasuryDealDetail For Purchase
			if (checkPurchaseTreasuryDetails) {
				getSession().saveOrUpdate(savePurchaseTDetails);
			}

			if (checkDeleteRecordSplPool != null && checkDeleteRecordSplPool) {
				// List<BigDecimal> lstdeleteSplCust =
				// (List<BigDecimal>)saveMapInfo.get("deleteCusSplPoolDetails");
				for (BigDecimal bigDecimaldelete : lstdeleteSplCust) {
					TreasuryDealDetail lstdel = (TreasuryDealDetail) getSession().get(TreasuryDealDetail.class, bigDecimaldelete);
					lstdel.setIsActive(Constants.D);
					lstdel.setModifiedBy(sessionManage.getUserName());
					lstdel.setModifiedDate(new Date());
					getSession().update(lstdel);
				}
				// List<BigDecimal> lstClearSplCust =
				// (List<BigDecimal>)saveMapInfo.get("clearCusSplPoolDetails");
				for (BigDecimal bigDecimalclear : lstClearSplCust) {

					CustomerSpecialDealRequest lstCustSplDlReq = (CustomerSpecialDealRequest) getSession().get(CustomerSpecialDealRequest.class,bigDecimalclear);

					lstCustSplDlReq.setDealCompanyId(null);
					lstCustSplDlReq.setDealId(null);
					lstCustSplDlReq.setDealFinanceYear(null);
					lstCustSplDlReq.setDealApplicationNumber(null);

					getSession().update(lstCustSplDlReq);

				}
			}

			// save the TreasuryDealDetail For Purchase of Special Customer
			if (lstPurchaseSplCustDt.size() != 0) {
				for (TreasuryDealDetail lstPSplCust : lstPurchaseSplCustDt) {
					getSession().saveOrUpdate(lstPSplCust);
				}
			}

			// Update the Customer Special Deal Request
			if (lstPurchaseSplCustDt.size() != 0) {

				for (TreasuryDealDetail treasuryDealDetail : lstPurchaseSplCustDt) {

					CustomerSpecialDealRequest lstCustSplDlReq = (CustomerSpecialDealRequest) getSession().get(CustomerSpecialDealRequest.class,treasuryDealDetail.getCustomerSpecialDealRequest().getCustomerSpecialDealReqId());

					lstCustSplDlReq.setDealCompanyId(treasuryDealDetail.getTreasuryDealCompanyMaster().getCompanyId());
					lstCustSplDlReq.setDealId(new BigDecimal(Constants.DOCUMENT_CODE_FOR_FX_DEAL_WITH_BANK));
					lstCustSplDlReq.setDealFinanceYear(treasuryDealDetail.getTreasuryDealUserFinanceYear());
					lstCustSplDlReq.setDealApplicationNumber(treasuryDealDetail.getDocumentNumber());
					lstCustSplDlReq.setBuyRate(treasuryDealDetail.getLocalExchangeRate());

					getSession().update(lstCustSplDlReq);

				}
			}

			// save the TreasuryStandardInstruction For Purchase
			// getSession().saveOrUpdate(savePurchaseSI);

			// save the TreasuryStandardInstruction For Purchase of Instructions

			if(checkPurchaseCondition != null){
				if (checkPurchaseCondition) {
					if (lstPurchaseTSIOld != null && lstPurchaseTSIOld.size() != 0) {
						for (TreasuryStandardInstruction treasuryStandardInstruction : lstPurchaseTSIOld) {
							TreasuryStandardInstruction pkvaluePurchase = (TreasuryStandardInstruction) getSession().get(TreasuryStandardInstruction.class,treasuryStandardInstruction.getTreasuryStandardInstructionId());
							getSession().delete(pkvaluePurchase);
						}
					}

					if (lstPurchaseTSI != null && lstPurchaseTSI.size() != 0) {
						for (TreasuryStandardInstruction lstPSI : lstPurchaseTSI) {
							getSession().saveOrUpdate(lstPSI);
						}
					}
				} else {
					if (lstPurchaseTSI != null && lstPurchaseTSI.size() != 0) {
						if (lstPurchaseTSI.size() != 0) {
							for (TreasuryStandardInstruction lstPSI : lstPurchaseTSI) {
								getSession().saveOrUpdate(lstPSI);
							}
						}

						if (lstPurchaseTSIOld != null && lstPurchaseTSIOld.size() != 0) {
							for (TreasuryStandardInstruction treasuryStandardInstruction : lstPurchaseTSIOld) {
								treasuryStandardInstruction.setModifiedBy(sessionManage.getUserName());
								treasuryStandardInstruction.setModifiedDate(new Date());
								getSession().saveOrUpdate(treasuryStandardInstruction);
							}
						}
					}
				}
			}

			// save the TreasuryDealDetail For Sale
			getSession().saveOrUpdate(saveSaleTDetails);

			// save the TreasuryStandardInstruction For Sale
			// getSession().saveOrUpdate(saveSaleSI);

			// save the TreasuryStandardInstruction For Sale of Instructions
			if (checkSaleCondition != null) {
				if (checkSaleCondition) {

					if (lstSaleTSIOld != null && lstSaleTSIOld.size() != 0) {
						for (TreasuryStandardInstruction treasuryStandardInstruction : lstSaleTSIOld) {
							TreasuryStandardInstruction pkvalueSale = (TreasuryStandardInstruction) getSession().get(TreasuryStandardInstruction.class,treasuryStandardInstruction.getTreasuryStandardInstructionId());
							getSession().delete(pkvalueSale);
						}
					}

					if (lstSaleTSI != null && lstSaleTSI.size() != 0) {
						for (TreasuryStandardInstruction lstSSI : lstSaleTSI) {
							getSession().saveOrUpdate(lstSSI);
						}
					}
				} else {
					if (lstSaleTSI != null && lstSaleTSI.size() != 0) {
						if (lstSaleTSI.size() != 0) {
							for (TreasuryStandardInstruction lstSSI : lstSaleTSI) {
								getSession().saveOrUpdate(lstSSI);
							}
						}

						if (lstSaleTSIOld != null && lstSaleTSIOld.size() != 0) {
							for (TreasuryStandardInstruction treasuryStandardInstruction : lstSaleTSIOld) {
								treasuryStandardInstruction.setModifiedBy(sessionManage.getUserName());
								treasuryStandardInstruction.setModifiedDate(new Date());
								getSession().saveOrUpdate(treasuryStandardInstruction);
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		}

	}

	@Override
	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheader(String Fx_BankDealType, String status,BigDecimal documentYear) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",Fx_BankDealType));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear",documentYear));
		if(status != null){
			if(status.equalsIgnoreCase(Constants.U)){
				dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", status));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedBy"));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedDate"));
			}
		}
		dCriteria.addOrder(Order.asc("treasuryDealHeader.treasuryDocumentNumber"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> pdata = (List<TreasuryDealHeader>) findAll(dCriteria);

		return pdata;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> fetchDocumentNumber() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");

		dCriteria.add(Restrictions
				.eq("dealWithType", Constants.Fx_BankDealType));
		dCriteria.add(Restrictions.eq("isActive", Constants.U));
		dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedBy"));
		dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedDate"));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealHeader>) findAll(dCriteria);

	}

	@Override
	public void approveHeader(BigDecimal treasuryDealHeader, String userName,
			Date currentDate) {
		TreasuryDealHeader treasuryHeader = (TreasuryDealHeader) getSession()
				.get(TreasuryDealHeader.class, treasuryDealHeader);
		treasuryHeader.setIsActive(Constants.Yes);
		treasuryHeader.setApprovedBy(userName);
		treasuryHeader.setApprovedDate(currentDate);
		getSession().update(treasuryHeader);

	}

	@Override
	public void approveDetailsPurchase(BigDecimal treasuryDealDetail,
			String userName, Date currentDate) {
		TreasuryDealDetail treasuryDetail = (TreasuryDealDetail) getSession()
				.get(TreasuryDealDetail.class, treasuryDealDetail);
		treasuryDetail.setIsActive(Constants.Yes);

		getSession().update(treasuryDetail);

	}

	@Override
	public void approveStandardInstructionForPurchase(
			BigDecimal standardInstunction, String userName, Date currentDate) {
		TreasuryStandardInstruction treasuryStaInst = (TreasuryStandardInstruction) getSession()
				.get(TreasuryStandardInstruction.class, standardInstunction);
		treasuryStaInst.setIsActive(Constants.Yes);

		getSession().update(treasuryStaInst);
	}

	@Override
	public void approveStandardInstructionForsale(
			BigDecimal standardInstunction, String userName, Date currentDate) {
		TreasuryStandardInstruction treasuryStaInst = (TreasuryStandardInstruction) getSession()
				.get(TreasuryStandardInstruction.class, standardInstunction);
		treasuryStaInst.setIsActive(Constants.Yes);

		getSession().update(treasuryStaInst);

	}

	@Override
	public void approveDetailsSale(BigDecimal treasuryDealDetail,
			String userName, Date currentDate) {
		TreasuryDealDetail treasuryDetail = (TreasuryDealDetail) getSession()
				.get(TreasuryDealDetail.class, treasuryDealDetail);
		treasuryDetail.setIsActive(Constants.Yes);
		getSession().update(treasuryDetail);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewTreasuryDeal> viewTreauryDealwithBank(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewTreasuryDeal.class, "viewTreasuryDeal");
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.documentFinanceYear",documentYear));
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.documentNumber",documentNumber));
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.lineType", lineType));
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.dealWithType",dealWithType));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewTreasuryDeal> lstView = (List<ViewTreasuryDeal>) findAll(dCriteria);

		return lstView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewTreasuryDeal> viewTreauryDealwithBankTransfer(BigDecimal documentYear,BigDecimal documentNumber, String lineType, String dealWithType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewTreasuryDeal.class, "viewTreasuryDeal");
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.documentFinanceYear",documentYear));
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.documentNumber",documentNumber));
		dCriteria.add(Restrictions.eq("viewTreasuryDeal.lineType", lineType));
	//	dCriteria.add(Restrictions.isNull("viewTreasuryDeal.dealWithType"));
		//dCriteria.add(Restrictions.eq("viewTreasuryDeal.dealWithType",dealWithType));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ViewTreasuryDeal> lstView = (List<ViewTreasuryDeal>) findAll(dCriteria);

		return lstView;
	}

	@Override
	public HashMap<String, Object> ValidateDealID(String dealID, Date dealDate) {

		HashMap<String, Object> retDelaMap = new HashMap<String, Object>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.reutersReference",
				dealID));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.documentDate",
				dealDate));
		List<TreasuryDealHeader> lstTreasuryDealHeader = (List<TreasuryDealHeader>) findAll(dCriteria);
		if (lstTreasuryDealHeader.size() > 0) {
			retDelaMap.put("DealID", Boolean.TRUE);
		} else {
			retDelaMap.put("DealID", Boolean.FALSE);
		}

		return retDelaMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getAllDetailsFromDb(
			BigDecimal treasuryDealHeaderId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealDetail.class, "treasuryDealDetail");

		// Tresure Header
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader",
				"treasuryDealHeader", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions
				.eq("treasuryDealHeader.treasuryDealHeaderId",
						treasuryDealHeaderId));
		// BankMaster
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster",
				"treasuryDealBankMaster", JoinType.INNER_JOIN);
		// CurrencyMaster
		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailCurrencyMaster",
				"treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);
		// CountryMaster
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCountryMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCountryMaster",
				"treasuryDealCountryMaster", JoinType.INNER_JOIN);
		// CompanyMaster
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealCompanyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealCompanyMaster",
				"treasuryDealCompanyMaster", JoinType.INNER_JOIN);
		// Document Master
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDocument",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDocument",
				"treasuryDealDocument", JoinType.INNER_JOIN);
		// BankAccount Master
		dCriteria.setFetchMode(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				FetchMode.JOIN);
		dCriteria.createAlias(
				"treasuryDealDetail.treasuryDealDetailBankAccountDetails",
				"treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);
		// CustomerSpicialDealReq Master
		/*
		 * dCriteria.setFetchMode("treasuryDealDetail.customerSpecialDealRequest"
		 * , FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.customerSpecialDealRequest"
		 * , "customerSpecialDealRequest", JoinType.INNER_JOIN);
		 * 
		 * //AccountBalance
		 * dCriteria.setFetchMode("treasuryDealDetail.accountBalance",
		 * FetchMode.JOIN);
		 * dCriteria.createAlias("treasuryDealDetail.accountBalance",
		 * "accountBalance", JoinType.INNER_JOIN);
		 */
		// dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType",
		// Constants.PD));
		dCriteria.add(Restrictions.or(Restrictions
				.ilike("treasuryDealDetail.lineType", Constants.PD,
						MatchMode.ANYWHERE), Restrictions
				.ilike("treasuryDealDetail.lineType", Constants.SD,
						MatchMode.ANYWHERE)));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryDealDetail>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> getAllDetailsFromDbHeader() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");

		// CompanyMaster
		dCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsCompanyMaster",
				"fsCompanyMaster", JoinType.INNER_JOIN);
		// CountryMaster
		dCriteria.setFetchMode("treasuryDealHeader.fsCountryMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsCountryMaster",
				"fsCountryMaster", JoinType.INNER_JOIN);
		// BankMaster
		dCriteria.setFetchMode("treasuryDealHeader.exBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exBankMaster",
				"exBankMaster", JoinType.INNER_JOIN);
		// Language
		dCriteria.setFetchMode("treasuryDealHeader.fsLanguageType",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsLanguageType",
				"fsLanguageTypes", JoinType.INNER_JOIN);
		// Document
		dCriteria.setFetchMode("treasuryDealHeader.exDocument", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDocument", "exDocument",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.or(Restrictions.ilike(
				"treasuryDealHeader.dealWithType", "B", MatchMode.ANYWHERE),
				Restrictions.ilike("treasuryDealHeader.dealWithType", "S",
						MatchMode.ANYWHERE)));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryDealHeader>) findAll(dCriteria);
	}

	@Override
	public List<TreasuryDealHeader> getHeaderDetails(BigDecimal documentNo,
			BigDecimal companyId, BigDecimal dealYear, String dealWithType) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");

		dCriteria.setFetchMode("treasuryDealHeader.exDealDetail",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDealDetail",
				"exDealDetail", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealHeader.fsCompanyMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.fsCompanyMaster",
				"fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));

		dCriteria.setFetchMode("treasuryDealHeader.exDocument", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDocument", "exDocument",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq(
				"treasuryDealHeader.treasuryDocumentNumber", documentNo));

		dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear",
				dealYear));
		dCriteria.add(Restrictions.or(Restrictions.ilike(
				"treasuryDealHeader.dealWithType", "B", MatchMode.ANYWHERE),
				Restrictions.ilike("treasuryDealHeader.dealWithType", "S",
						MatchMode.ANYWHERE)));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealHeader>) findAll(dCriteria);

	}

	@Override
	public List<TreasuryDealHeaderDTO> getfxdealEnqSelectDate(Date documentDate) {
		String hql = "select treasuryDealHeader.treasuryDealHeaderId as treasuryDealHeaderId ,treasuryDealHeader.dealWithType as dealWithType ,"
				+ "treasuryDealHeader.exDocument.documentID as documentID  , treasuryDealHeader.treasuryDocumentNumber as treasuryDocumentNumber , "
				+ "treasuryDealHeader.fsCompanyMaster.companyId as companyId, "
				+ "treasuryDealHeader.reutersReference as reutersReference,"
				+ "  treasuryDealHeader.fsCompanyMaster.companyCode as companyCode , treasuryDealHeader.createdBy as createdBy, "
				+ " treasuryDealHeader.createdDate as createdDate , treasuryDealHeader.concludedBy as concludedBy ,"
				+ " treasuryDealHeader.contactName as contactName , treasuryDealHeader.userFinanceYear as documentFinanceYear ,"
				+ "  treasuryDealHeader.isActive as isActive , treasuryDealHeader.exBankMaster.bankId as dealwithBankId , treasuryDealHeader.dealWithCustomer as dealWithCustomer "
				+ "from "
				+ "TreasuryDealHeader as treasuryDealHeader inner join treasuryDealHeader.exDocument  "
				+ "where to_date(to_char(treasuryDealHeader.accyymm, 'DD-MON-YY'), 'DD-MON-YY') = :date  "
				+ "and treasuryDealHeader.dealWithType = :dealWithType1 order by treasuryDealHeader.createdDate DESC";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", documentDate);
		query.setParameter("dealWithType1", "B");
		query.setResultTransformer(Transformers
				.aliasToBean(TreasuryDealHeaderDTO.class));
		// query.setParameter("returnRef", dealID);
		List<TreasuryDealHeaderDTO> lstTreasuryDealHeader1 = query.list();

		return lstTreasuryDealHeader1;
	}

	@Override
	public void callApproveProcedure(BigDecimal applicationCountryId,
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal financialYear, BigDecimal documentNumber) {
		Connection connection = null;

		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection
					.prepareCall(" { call EX_TREASURY_APPV_UGL(?,?,?,?,?)}");
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYear);
			cs.setBigDecimal(5, documentNumber);
			cs.executeUpdate();

			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("=============" + e);

		}
	}

	@Override
	public String getCustomerName(BigDecimal dealWithCustomer) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,
				"fscustomer");
		criteria.add(Restrictions.eq("fscustomer.customerReference",
				dealWithCustomer));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		@SuppressWarnings("unchecked")
		String name=null;
		List<Customer> custList = (List<Customer>) findAll(criteria);
		if(custList.size() !=0){
			  name=	custList.get(0).getFirstName();  
		}
		return name;
	}

	@Override
	public void callUnApproveProcedure(BigDecimal applicationCountryId,
			BigDecimal companyId, BigDecimal documentId,
			BigDecimal financialYear, BigDecimal documentNumber) throws AMGException{
		Connection connection = null;

		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL(?,?,?,?,?)}");
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentId);
			cs.setBigDecimal(4, financialYear);
			cs.setBigDecimal(5, documentNumber);
			cs.executeUpdate();
		}  catch (SQLException e) {
			log.info("Problem Occured While Procedure calling time "+ e.getMessage());
			String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e1) {
			log.info("Problem Occured While Procedure calling time "+ e1.getMessage());
			String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e1.getMessage();
			throw new AMGException(erromsg);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				log.info("Problem Occured While Procedure calling time "+ e.getMessage());
				String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} catch (Exception e1) {
				log.info("Problem Occured While Procedure calling time "+ e1.getMessage());
				String erromsg = "EX_TREASURY_UAPPV_UGL" + " : " + e1.getMessage();
				throw new AMGException(erromsg);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getSplDealFromCustomerSplDeal(BigDecimal bankId, String fundingOptionValue, BigDecimal currencyId) {

		List<CustomerSpecialDealRequest> pdata = new ArrayList<CustomerSpecialDealRequest>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");

		dCriteria.add(Restrictions.eq("customerSpecialDealRequest.fundingOption",fundingOptionValue));

		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster","customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("customerSpeacialDealReqBankMaster.bankId", bankId));

		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster","customerSpeacialDealReqCurrencyMaster",JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("customerSpeacialDealReqCurrencyMaster.currencyId",currencyId));

		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer","customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);

		dCriteria.addOrder(Order.asc("customerSpecialDealRequest.customerSpecialDealReqId"));

		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqDocument",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqDocument","customerSpeacialDealReqDocument", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCompanyMaster","customerSpeacialDealReqCompanyMaster",JoinType.INNER_JOIN);

		dCriteria.setFetchMode("customerSpecialDealRequest.documentFinancialYear",FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.documentFinancialYear","documentFinancialYear", JoinType.INNER_JOIN);

		dCriteria.add(Restrictions.isNotNull("customerSpecialDealRequest.approvedBy"));
		dCriteria.add(Restrictions.isNotNull("customerSpecialDealRequest.approvedDate"));

		dCriteria.add(Restrictions.isNull("customerSpecialDealRequest.dealCompanyId"));
		dCriteria.add(Restrictions.isNull("customerSpecialDealRequest.dealId"));
		dCriteria.add(Restrictions.isNull("customerSpecialDealRequest.dealFinanceYear"));
		dCriteria.add(Restrictions.isNull("customerSpecialDealRequest.dealApplicationNumber"));

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Date validUpto = null;
		try {
			validUpto = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		dCriteria.add(Restrictions.ge("customerSpecialDealRequest.validUpto",validUpto));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		Boolean lstCorrespBank = checkingBankCorresponding(bankId);

		if (lstCorrespBank) {
			pdata = (List<CustomerSpecialDealRequest>) findAll(dCriteria);
			return pdata;
		} else {
			return pdata;
		}

	}

	@SuppressWarnings("unchecked")
	public Boolean checkingBankCorresponding(BigDecimal bankId) {

		Boolean returnValue = false;

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",
				JoinType.INNER_JOIN);

		BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (corresBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",
					corresBankIndicatorId));
		}

		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria
				.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
																				// 18/12/2014
																				// Record
																				// Status
																				// Condition
																				// added

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBanklist = (List<BankApplicability>) findAll(dCriteria);

		if (lstBanklist.size() != 0) {
			returnValue = true;
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public List<BankApplicability> checkingBankBeneficiary() {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankApplicability.class, "bankApplicability");

		// to get Bank CountryID From FsCountry Master
		dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",
				JoinType.INNER_JOIN);
		/* dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId)); */

		dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		dCriteria.createAlias("bankApplicability.bankInd", "bankInd",
				JoinType.INNER_JOIN);

		BigDecimal beneficiaryBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_BENI_BANK);

		Disjunction lstjunction = Restrictions.disjunction();

		if (beneficiaryBankIndicatorId != null) {
			lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",
					beneficiaryBankIndicatorId));
		}

		dCriteria.add(lstjunction);

		dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

		dCriteria
				.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));// CR
																				// 18/12/2014
																				// Record
																				// Status
																				// Condition
																				// added

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<BankApplicability> lstBanklist = (List<BankApplicability>) findAll(dCriteria);

		return lstBanklist;
	}

	@Override
	public HashMap<String, Object> ValidateDealIDWhileUpdate(
			BigDecimal documentNumber, String dealID, Date dealDate) {

		HashMap<String, Object> retDelaMap = new HashMap<String, Object>();

		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.reutersReference",
				dealID));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.documentDate",
				dealDate));
		List<TreasuryDealHeader> lstTreasuryDealHeader = (List<TreasuryDealHeader>) findAll(dCriteria);

		if (lstTreasuryDealHeader.size() != 0) {
			for (TreasuryDealHeader treasuryDealHeader : lstTreasuryDealHeader) {
				if (treasuryDealHeader.getTreasuryDocumentNumber().compareTo(
						documentNumber) == 0) {
					retDelaMap.put("DealID", Boolean.FALSE);
					break;
				} else {
					retDelaMap.put("DealID", Boolean.TRUE);
				}
			}
		} else {
			retDelaMap.put("DealID", Boolean.FALSE);
		}

		return retDelaMap;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> getHeaderDetailsList(BigDecimal documentNo,
			BigDecimal refNumber, String dealWithType) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				TreasuryDealHeader.class, "treasuryDealHeader");

		dCriteria.setFetchMode("treasuryDealHeader.exDealDetail",
				FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDealDetail",
				"exDealDetail", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealHeader.exDocument", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealHeader.exDocument", "exDocument",
				JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exDocument.documentID", documentNo));

		dCriteria.add(Restrictions.eq(
				"treasuryDealHeader.treasuryDocumentNumber", refNumber));

		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",
				dealWithType));

		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<TreasuryDealHeader>) findAll(dCriteria);
	}

	@Override
	public List<TreasuryDealHeaderDTO> getfxdealSupplierEnqSelectDate(
			Date documentDate) {
		String hql = "select treasuryDealHeader.treasuryDealHeaderId as treasuryDealHeaderId ,treasuryDealHeader.dealWithType as dealWithType ,"
				+ "treasuryDealHeader.exDocument.documentID as documentID  , treasuryDealHeader.treasuryDocumentNumber as treasuryDocumentNumber , "
				+ "treasuryDealHeader.fsCompanyMaster.companyId as companyId, "
				+ "  treasuryDealHeader.fsCompanyMaster.companyCode as companyCode , treasuryDealHeader.createdBy as createdBy, "
				+ " treasuryDealHeader.createdDate as createdDate , treasuryDealHeader.concludedBy as concludedBy ,"
				+ " treasuryDealHeader.contactName as contactName , treasuryDealHeader.userFinanceYear as documentFinanceYear ,"
				+ "  treasuryDealHeader.isActive as isActive , treasuryDealHeader.exBankMaster.bankId as dealwithBankId , treasuryDealHeader.dealWithCustomer as dealWithCustomer "
				+ "from "
				+ "TreasuryDealHeader as treasuryDealHeader inner join treasuryDealHeader.exDocument  "
				+ "where to_date(to_char(treasuryDealHeader.accyymm, 'DD-MON-YY'), 'DD-MON-YY') = :date  "
				+ "and treasuryDealHeader.dealWithType = :dealWithType1";
		Query query = getSession().createQuery(hql);
		query.setParameter("date", documentDate);
		query.setParameter("dealWithType1", "S");
		query.setResultTransformer(Transformers
				.aliasToBean(TreasuryDealHeaderDTO.class));
		// query.setParameter("returnRef", dealID);
		List<TreasuryDealHeaderDTO> lstTreasuryDealHeader1 = query.list();

		return lstTreasuryDealHeader1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DayBookHeader> getAllDetailsListFromDB(BigDecimal documentNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(
				DayBookHeader.class, "dayBookHeader");
		criteria.add(Restrictions.eq("dayBookHeader.refNumber", documentNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DayBookHeader> lstDayBookHeaderlist = (List<DayBookHeader>) findAll(criteria);
		return lstDayBookHeaderlist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DayBookDetails> getAllDetailsDayBookDetails(
			BigDecimal dayBookHeaderId, String lineType) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				DayBookDetails.class, "dayBookDetails");
		dCriteria
				.setFetchMode("dayBookDetails.dayBookHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookHeaderId",
				"dayBookHeaderId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("dayBookHeaderId.daybookHeaderId",
				dayBookHeaderId));
		dCriteria.add(Restrictions.eq("dayBookDetails.dayBookLineType",
				lineType));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DayBookDetails> lstDayBookDetailList = (List<DayBookDetails>) findAll(dCriteria);
		return lstDayBookDetailList;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankAccountDetails> getAccountNumberBasedOnBank(
			String accountNumber) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				BankAccountDetails.class, "bankAccountDetails");
		dCriteria.setFetchMode("bankAccountDetails.exBankMaster",
				FetchMode.JOIN);
		dCriteria.createAlias("bankAccountDetails.exBankMaster",
				"exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetails.fundGlno",
				accountNumber));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> lstBankList = (List<BankAccountDetails>) findAll(dCriteria);
		return lstBankList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AccountBalance> getBankBanceBasedOnAccNO(
			String saleAccountNumber) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(
				AccountBalance.class, "accountBalance");
		/*dCriteria.setFetchMode("accountBalance.exBankAccountDetails",
				FetchMode.JOIN);
		dCriteria.createAlias("accountBalance.exBankAccountDetails",
				"exBankAccountDetails", JoinType.INNER_JOIN);*/
		dCriteria.add(Restrictions.eq("accountBalance.glSlNumber",
				saleAccountNumber));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<AccountBalance> lstAccountBalance = (List<AccountBalance>) findAll(dCriteria);
		return lstAccountBalance;
	}

	@Override
	public void saveUnApprovedGLEntry(TreasuryDealHeader treasuryDealHeader) throws Exception{
		// TODO Auto-generated method stub
		// call procedure
		/*
		 * getSession().getNamedQuery("EX_TREASURY_UAPPV_UGL")
		 * .setBigDecimal("applicationCountryId",
		 * sessionManage.getCountryId()) .setBigDecimal("companyId",
		 * sessionManage.getCompanyId()) .setBigDecimal("documentID",
		 * saveTreasuryDH.getExDocument().getDocumentID())
		 * .setBigDecimal("userFinanceYear",
		 * saveTreasuryDH.getUserFinanceYear())
		 * .setBigDecimal("treasuryDocumentNumber",
		 * saveTreasuryDH.getTreasuryDocumentNumber()) .executeUpdate();
		 */
		
		Connection connection = null;

		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			//cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL(?,?,?,?,?)}");
			cs = connection.prepareCall(" { call EX_TREASURY_UAPPV_UGL_BULK(?,?,?,?,?)}");
			cs.setBigDecimal(1, sessionManage.getCountryId());
			cs.setBigDecimal(2, sessionManage.getCompanyId());
			cs.setBigDecimal(3, treasuryDealHeader.getExDocument().getDocumentID());
			cs.setBigDecimal(4, treasuryDealHeader.getUserFinanceYear());
			cs.setBigDecimal(5, treasuryDealHeader.getTreasuryDocumentNumber());
			cs.executeUpdate();

		} catch (SQLException e) {
			  log.error( "Error While Approving  "+ e.getMessage());
				String erromsg = "Error While Approving " + " : " + e.getMessage();
				throw new Exception(erromsg);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> getfxDealWithSupplierUnApprovedRecords(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive",Constants.U));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherCompanyId"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherId"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherFinanceyYear"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherNumber"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> treasuryDealHeaderList = (List<TreasuryDealHeader>) findAll(dCriteria);
		
		return treasuryDealHeaderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealHeader> fecthTreasurydealHeaderRecordsForSupplierApprove(BigDecimal documentNo,String Fx_BankDealType, String status,BigDecimal documentYear){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDocumentNumber",documentNo));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",Fx_BankDealType));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear",documentYear));
		if(status != null){
			if(status.equalsIgnoreCase(Constants.U)){
				dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", status));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedBy"));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedDate"));
			}
		}
		
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> treasuryDealHeaderList = (List<TreasuryDealHeader>) findAll(dCriteria);
		
		return treasuryDealHeaderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPD(BigDecimal tresuryDealHeaderPk){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader", "treasuryDealHeader", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster", "treasuryDealBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster", "treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailBankAccountDetails", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailBankAccountDetails", "treasuryDealDetailBankAccountDetails", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId",tresuryDealHeaderPk));
		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType",Constants.PD));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> treasuryDealDetailList = (List<TreasuryDealDetail>) findAll(dCriteria);
		
		return treasuryDealDetailList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> fecthTreasuryDealDetailRecordsForPY(BigDecimal tresuryDealHeaderPk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader", "treasuryDealHeader", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId",tresuryDealHeaderPk));
		dCriteria.add(Restrictions.eq("treasuryDealDetail.lineType",Constants.PY));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> treasuryDealDetailList = (List<TreasuryDealDetail>) findAll(dCriteria);
		
		return treasuryDealDetailList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DayBookHeader> fecthDayBookHeaderForSupplierApprove(BigDecimal payMentVocherNumber){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookHeader.class, "dayBookHeader");
		
		dCriteria.add(Restrictions.eq("dayBookHeader.documentNumber",payMentVocherNumber));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DayBookHeader> dayBookHeaderList = (List<DayBookHeader>) findAll(dCriteria);
		
		return dayBookHeaderList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DayBookDetails> fecthDayBookDetailsRecordsForPY(BigDecimal dayBookHeaderPk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookDetails.class, "dayBookDetails");
		dCriteria.setFetchMode("dayBookDetails.dayBookHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookHeaderId", "dayBookHeaderId", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("dayBookHeaderId.daybookHeaderId",dayBookHeaderPk));
		dCriteria.add(Restrictions.eq("dayBookDetails.dayBookLineType",Constants.PY));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DayBookDetails> dayBookDeailsList = (List<DayBookDetails>) findAll(dCriteria);
		
		return dayBookDeailsList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DayBookDetails> fecthDayBookDetailsRecordsForSD(BigDecimal dayBookHeaderPk){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(DayBookDetails.class, "dayBookDetails");
		
		
		dCriteria.setFetchMode("dayBookDetails.dayBookDetailsBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookDetailsBankMaster", "dayBookDetailsBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("dayBookDetails.dayBookCurrencyId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookCurrencyId", "dayBookCurrencyId", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("dayBookDetails.dayBookDetailsBankAccountDetails", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookDetailsBankAccountDetails", "dayBookDetailsBankAccountDetails", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("dayBookDetails.dayBookHeaderId", FetchMode.JOIN);
		dCriteria.createAlias("dayBookDetails.dayBookHeaderId", "dayBookHeaderId", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("dayBookHeaderId.daybookHeaderId",dayBookHeaderPk));
		dCriteria.add(Restrictions.eq("dayBookDetails.dayBookLineType",Constants.SD));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<DayBookDetails> dayBookDeailsList = (List<DayBookDetails>) findAll(dCriteria);
		
		return dayBookDeailsList;
	}
	
	
	
	@Override
	public void approveRecords(String userName,BigDecimal treasuryHeaderPk,BigDecimal dayBookHeaderPk,
			List<BigDecimal> treasuryDealDetailPkList, List<BigDecimal> dayBookDealDetailsPkList)throws Exception{
		//Approving Treasury Header Record
		
		try{
			
			TreasuryDealHeader treasuryDealHeaderObj = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class,treasuryHeaderPk);
			
			treasuryDealHeaderObj.setIsActive(Constants.Yes);
			treasuryDealHeaderObj.setApprovedBy(userName);
			treasuryDealHeaderObj.setApprovedDate(new Date());
			getSession().update(treasuryDealHeaderObj);
			
			//Approving Treasury Deal Detail Record
			for(BigDecimal trDealObj:treasuryDealDetailPkList){
			TreasuryDealDetail treasuryDealDetailObj = (TreasuryDealDetail) getSession().get(TreasuryDealDetail.class,trDealObj);
			
			treasuryDealDetailObj.setIsActive(Constants.Yes);
			getSession().update(treasuryDealDetailObj);
			}
			
			//Approving Day Book Header Record
			DayBookHeader dayBookHeaderObj = (DayBookHeader) getSession().get(DayBookHeader.class,dayBookHeaderPk);
			dayBookHeaderObj.setIsActive(Constants.Yes);
			dayBookHeaderObj.setApprovedBy(userName);
			dayBookHeaderObj.setApprovedDate(new Date());
			getSession().update(dayBookHeaderObj);
			
			//Approving Day Book Detail Record
			for(BigDecimal dayBookdetailObj:dayBookDealDetailsPkList){
			DayBookDetails dealBookDeatailObj = (DayBookDetails) getSession().get(DayBookDetails.class,dayBookdetailObj);
			
			dealBookDeatailObj.setApprovedBy(userName);
			dealBookDeatailObj.setApprovedDate(new Date());
			getSession().update(dealBookDeatailObj);
			
			}
			
		} catch (Exception e) {
			log.error( "Error While Approving  "+ e.getMessage());
			String erromsg = "Error While Approving " + " : " + e.getMessage();
			throw new Exception(erromsg);
		} 
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String getAccountNoBasedOnAccDetId(BigDecimal accDetId){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankAccountDetails.class, "bankAccountDetails");
		
		dCriteria.add(Restrictions.eq("bankAccountDetails.bankAcctDetId",accDetId));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankAccountDetails> bankAccountDetailsList = (List<BankAccountDetails>) findAll(dCriteria);
		
		
		if(bankAccountDetailsList.size()>0){
			return bankAccountDetailsList.get(0).getFundGlno();
		}else{
		return null;
		}
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StandardInstruction> getValues(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,BigDecimal accountDetId,String instructionType) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StandardInstruction.class, "standardInstruction");
		
		
		dCriteria.setFetchMode("standardInstruction.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.setFetchMode("standardInstruction.exBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exBankMaster.bankId", bankId)); 
		
		dCriteria.setFetchMode("standardInstruction.exCurrenyMaster", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.exCurrenyMaster", "exCurrenyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("exCurrenyMaster.currencyId", currencyId)); 
		
		dCriteria.setFetchMode("standardInstruction.bankAccountDetailsId", FetchMode.JOIN);
		dCriteria.createAlias("standardInstruction.bankAccountDetailsId", "bankAccountDetailsId", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("bankAccountDetailsId.bankAcctDetId", accountDetId)); 
		
		
		dCriteria.add(Restrictions.eq("standardInstruction.isActive", Constants.Yes)); 
		
		dCriteria.add(Restrictions.eq("standardInstruction.intructionType", instructionType)); 
		
		dCriteria.addOrder(Order.asc("standardInstruction.intructionType"));
	
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<StandardInstruction> pdata = (List<StandardInstruction>)findAll(dCriteria);
		
		
	 	return pdata;
		
	}

	@Override
	public List<TreasuryDealRegisterView> fetchTreasuryDealRegisterFromView(HashMap<String, Object> lstTreasuryDealRegister) {
		
		BigDecimal companyId = (BigDecimal) lstTreasuryDealRegister.get("COMPANY_ID");
		BigDecimal bankId = (BigDecimal) lstTreasuryDealRegister.get("BANK_ID");
		BigDecimal pdCurrencyId = (BigDecimal) lstTreasuryDealRegister.get("PD_CURRENCY");
		BigDecimal sdCurrencyId = (BigDecimal) lstTreasuryDealRegister.get("SD_CURRENCY");
		Date fromDocDate = (Date) lstTreasuryDealRegister.get("FROM_DOC_DATE");
		Date toDocDate = (Date) lstTreasuryDealRegister.get("TO_DOC_DATE");
		BigDecimal status = (BigDecimal) lstTreasuryDealRegister.get("STATUS");
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealRegisterView.class, "treasuryDealRegisterView");
		
		if(companyId != null){
			dCriteria.add(Restrictions.eq("treasuryDealRegisterView.companyId", companyId));
		}
		if(bankId != null){
			dCriteria.add(Restrictions.eq("treasuryDealRegisterView.pdBankId", bankId));
		}
		if(pdCurrencyId != null){
			dCriteria.add(Restrictions.eq("treasuryDealRegisterView.pdCurrencyId", pdCurrencyId));
		}
		if(sdCurrencyId != null){
			dCriteria.add(Restrictions.eq("treasuryDealRegisterView.sdCurrencyId", sdCurrencyId));
		}
		if(fromDocDate != null && toDocDate != null){
			//dCriteria.add(Restrictions.ge("treasuryDealRegisterView.documentDate", fromDocDate)); 
			//dCriteria.add(Restrictions.lt("treasuryDealRegisterView.documentDate", toDocDate));
			dCriteria.add(Restrictions.between("treasuryDealRegisterView.documentDate", fromDocDate, toDocDate));
		}
		if(status != null && status.compareTo(BigDecimal.ZERO)!=0 && status.compareTo(BigDecimal.ONE)!=0){
			if(status.compareTo(new BigDecimal(2))==0){
				dCriteria.add(Restrictions.eq("treasuryDealRegisterView.isActive", Constants.Yes));
			}else if(status.compareTo(new BigDecimal(3))==0){
				dCriteria.add(Restrictions.eq("treasuryDealRegisterView.isActive", Constants.U));
			}else{
				// wrong way two enter
			}
		}
		
		dCriteria.addOrder(Order.asc("treasuryDealRegisterView.documentNumber"));
		
		List<TreasuryDealRegisterView> treasuryDealRegisterRec = (List<TreasuryDealRegisterView>)findAll(dCriteria);
		
		System.out.println(" treasuryDealRegisterRec : "+treasuryDealRegisterRec.size());
		
		return treasuryDealRegisterRec;
	}
	
	
	@Override
	public String getSplitIndicatorFromBankMaster(BigDecimal bankId) {
		String splitIndcator=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( BankMaster.class, "bankmaster");

		detachedCriteria.setFetchMode("bankmaster.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("bankmaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("bankmaster.bankId", bankId));
		detachedCriteria.add(Restrictions.eq("bankmaster.recordStatus", Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> bankList = (List<BankMaster>)findAll(detachedCriteria);
		if(bankList.size()>0){
		return bankList.get(0).getSplitIndicator();
		}else{
		return splitIndcator;
		}
	}
	
	
	
	@Override
	public List<TreasuryCustomerDeal> getTreasuryCustomerDealAndPaymentValues(BigDecimal companyId, BigDecimal documentId, BigDecimal documentFinaceYear, BigDecimal documentNo){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryCustomerDeal.class, "treasuryCustomerDeal");
		dCriteria.add(Restrictions.eq("treasuryCustomerDeal.companyId", companyId));
		dCriteria.add(Restrictions.eq("treasuryCustomerDeal.documentCode", documentId));
		dCriteria.add(Restrictions.eq("treasuryCustomerDeal.documentFinanceYear", documentFinaceYear));
		dCriteria.add(Restrictions.eq("treasuryCustomerDeal.documentNumber", documentNo));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<TreasuryCustomerDeal>) findAll(dCriteria);

	}
	
	@Override
	public void deleteAllFXDealBank(HashMap<String, Object> saveMapInfo,String userName) throws Exception {

		try {

			BigDecimal treasuryHeaderID = (BigDecimal) saveMapInfo.get("TreasureyHeader");
			BigDecimal documentId = (BigDecimal) saveMapInfo.get("DOCUMNET_NO");
			BigDecimal companyId = (BigDecimal) saveMapInfo.get("COMPANY_ID");
			BigDecimal dealYear = (BigDecimal) saveMapInfo.get("DEAL_YEAR");

			TreasuryDealHeader treasuryDealHeader = (TreasuryDealHeader) getSession().get(TreasuryDealHeader.class, treasuryHeaderID);

			if(treasuryDealHeader!=null)
			{
				treasuryDealHeader.setModifiedBy(userName);
				treasuryDealHeader.setModifiedDate(new Date());
				treasuryDealHeader.setIsActive(Constants.D);
				getSession().saveOrUpdate(treasuryDealHeader);
			}


			List<TreasuryDealDetail> lstTreasuryDealDetail= getTreasuryDealDetailRecords(treasuryHeaderID);
			if(lstTreasuryDealDetail!=null && lstTreasuryDealDetail.size()>0)
			{
				for(TreasuryDealDetail treasuryDealDetail:lstTreasuryDealDetail)
				{
					treasuryDealDetail.setModifiedBy(userName);
					treasuryDealDetail.setModifiedDate(new Date());
					treasuryDealDetail.setIsActive(Constants.D);
					getSession().saveOrUpdate(treasuryDealDetail);
				}
			}


		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
			throw new AMGException(e.getMessage());
		}

	}
	
	public List<TreasuryDealDetail> getTreasuryDealDetailRecords(BigDecimal tresuryDealHeaderPk){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealHeader", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealHeader", "treasuryDealHeader", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("treasuryDealHeader.treasuryDealHeaderId",tresuryDealHeaderPk));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealDetail> treasuryDealDetailList = (List<TreasuryDealDetail>) findAll(dCriteria);
		
		return treasuryDealDetailList;
	}
	
	
	@Override
	public List<BankApplicability> getCorrespondingLocalFundingBanks(BigDecimal countryId) {

	List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();

	List<BankApplicability> lstBankApplicabity = new ArrayList<BankApplicability>();

	lstBankApplicabity.clear();

	DetachedCriteria dCriteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

	// to get Bank CountryID From FsCountry Master
	dCriteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
	dCriteria.createAlias("bankApplicability.bankMaster", "bankMaster",JoinType.INNER_JOIN);
	dCriteria.add(Restrictions.eq("bankMaster.recordStatus", Constants.Yes));
	// CR 18/12/2014 RecordStatus Condition added
	dCriteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
	dCriteria.createAlias("bankApplicability.bankInd", "bankInd",JoinType.INNER_JOIN);

	// to get Application CountryID From FsCountry Master
	dCriteria.setFetchMode("bankApplicability.fsCountryMaster",FetchMode.JOIN);
	dCriteria.createAlias("bankApplicability.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
	dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

	BigDecimal localBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_LOCAL_BANK);
	BigDecimal corresBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_CORR_BANK);
	BigDecimal fundingBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_FUND_BANK);
	BigDecimal serviceProviderBankIndicatorId = fetchLocalBankIndicator(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

	Disjunction lstjunction = Restrictions.disjunction();

	if (localBankIndicatorId != null) {
	lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",localBankIndicatorId));
	}

	if (corresBankIndicatorId != null) {
	lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",corresBankIndicatorId));
	}

	if (fundingBankIndicatorId != null) {
	lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",fundingBankIndicatorId));
	}
	if (serviceProviderBankIndicatorId != null) {
	lstjunction.add(Restrictions.eq("bankInd.bankIndicatorId",serviceProviderBankIndicatorId));
	}

	dCriteria.add(lstjunction);

	dCriteria.addOrder(Order.asc("bankMaster.bankFullName"));

	dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

	List<BankApplicability> lstBankApp = (List<BankApplicability>) findAll(dCriteria);

	for (BankApplicability bankApplicability : lstBankApp) {
	if (!duplicateCheck.contains(bankApplicability.getBankMaster()
	.getBankId())) {
	duplicateCheck.add(bankApplicability.getBankMaster()
	.getBankId());
	lstBankApplicabity.add(bankApplicability);
	}
	}

	return lstBankApplicabity;
	}

	public List<TreasuryDealHeader> fetchDocumentNumberFromTreasDealheaderForSupplier(String Fx_BankDealType, String status,BigDecimal documentYear) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		dCriteria.add(Restrictions.eq("treasuryDealHeader.dealWithType",Fx_BankDealType));
		dCriteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear",documentYear));
		if(status != null){
			if(status.equalsIgnoreCase(Constants.U)){
				dCriteria.add(Restrictions.eq("treasuryDealHeader.isActive", status));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedBy"));
				dCriteria.add(Restrictions.isNull("treasuryDealHeader.approvedDate"));
			}
		}
		
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherCompanyId"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherId"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherFinanceyYear"));
		dCriteria.add(Restrictions.isNotNull("treasuryDealHeader.paymentVoucherNumber"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<TreasuryDealHeader> pdata = (List<TreasuryDealHeader>) findAll(dCriteria);

		return pdata;

	}
	
	
	//Anil (19/01/2018)
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BankMaster> getBankName(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		
		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankMaster>) findAll(dCriteria);
	}
}
