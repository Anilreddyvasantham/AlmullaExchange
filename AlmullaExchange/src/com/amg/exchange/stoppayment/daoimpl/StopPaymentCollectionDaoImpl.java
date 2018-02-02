package com.amg.exchange.stoppayment.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.daoimpl.PersonalRemittanceDaoImpl;
import com.amg.exchange.remittance.model.BankServiceRule;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.stoppayment.dao.StopPaymentColllectionDao;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.viewModel.CurrencyMasterView;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@Repository
@SuppressWarnings("unchecked")
public class StopPaymentCollectionDaoImpl extends CommonDaoImpl implements StopPaymentColllectionDao {
	Logger LOGGER = Logger.getLogger(StopPaymentCollectionDaoImpl.class);
	@Override
	public RemittanceApplication viewBean(BigDecimal transferNo) {
		System.out.println("Transfer No " + transferNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.fsCountryMasterByBankCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCountryMasterByBankCountryId", "fsCountryMasterByBankCountryId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exBankMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exBankMaster", "exBankMaster", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exCurrencyMasterByForeignCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exCurrencyMasterByForeignCurrencyId", "exCurrencyMasterByForeignCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exRemittanceAppBenificiary", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exRemittanceAppBenificiary", "exRemittanceAppBenificiary", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceApplication.exCountryBranch", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.exCountryBranch", "exCountryBranch", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceApplication.documentNo", transferNo));
		// transferNo
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> remitapp = (List<RemittanceApplication>) findAll(criteria);
		if (null != remitapp) {
			System.out.println("^^^^^^" + remitapp.size());
			if (!remitapp.isEmpty()) {
				return remitapp.get(0);
			} else {
				return null;
			}
		}
		return null;
	}

	@Override
	public RemittanceTransaction getTransactiondetailsbyDocumentNo(Long transferNo) {
		System.out.println("Document no" + transferNo);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.add(Restrictions.eq("remittanceTransaction.applicationDocumentNo", transferNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> remitapp = (List<RemittanceTransaction>) findAll(criteria);
		if (null != remitapp) {
			if (!remitapp.isEmpty()) {
				return remitapp.get(0);
			}
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getProductName(BigDecimal bankId, BigDecimal remittanceModeId, BigDecimal deliveryModeId, BigDecimal countryId, BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankServiceRule.class, "bankServiceRule");
		criteria.setFetchMode("bankServiceRule.countryId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.countryId", "countryId", JoinType.LEFT_OUTER_JOIN);
		// criteria.add(Restrictions.eq("countryId.countryId", countryId));
		criteria.setFetchMode("bankServiceRule.currencyId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.currencyId", "currencyId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("currencyId.currencyId", currencyId));
		criteria.setFetchMode("bankServiceRule.bankId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.bankId", "bankId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("bankId.bankId", bankId));
		criteria.setFetchMode("bankServiceRule.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.remittanceModeId", "remittanceModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("remittanceModeId.remittanceModeId", remittanceModeId));
		criteria.setFetchMode("bankServiceRule.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("bankServiceRule.deliveryModeId", "deliveryModeId", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("deliveryModeId.deliveryModeId", deliveryModeId));
		if (((List<BankServiceRule>) findAll(criteria)).isEmpty())
			return null;
		return ((List<BankServiceRule>) findAll(criteria)).get(0).getFullname();
	}

	@Override
	public List<Customer> getCustomerInfo(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customerDetails");
		criteria.add(Restrictions.eq("customerId", customerId));
		// criteria.addOrder(Order.asc("Customer.fsArticleDetails.fsArticleMaster.articleId"));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<Customer>) findAll(criteria);
	}

	public List<RemittanceTransaction> getRemittanceTransaction(BigDecimal countryId, BigDecimal finYear, BigDecimal documentCode, BigDecimal companyId, BigDecimal documentId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.add(Restrictions.eq("remittanceTransaction.applicationCountryId", countryId));
		criteria.add(Restrictions.eq("remittanceTransaction.applicationCountryId", countryId));
		criteria.add(Restrictions.eq("remittanceTransaction.applicationCountryId", countryId));
		return null;
	}

	@Override
	public List<RemittanceTransaction> getRemittanceTransaction(BigDecimal countryId, Integer finYear, BigDecimal documentCode, BigDecimal companyId, BigDecimal documentId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.setFetchMode("remittanceTransaction.foreignCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.foreignCurrencyId", "foreignCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.localTranxCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.localTranxCurrencyId", "localTranxCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.localCommisionCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.localCommisionCurrencyId", "localCommisionCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.localChargeCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.localChargeCurrencyId", "localChargeCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.localDeliveryCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.localDeliveryCurrencyId", "localDeliveryCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.localNetCurrencyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.localNetCurrencyId", "localNetCurrencyId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.customerId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.customerId", "customerId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.branchId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.branchId", "branchId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.remittanceModeId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.remittanceModeId", "remittanceModeId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.deliveryModeId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.deliveryModeId", "deliveryModeId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.bankId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.bankId", "bankId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", countryId));
		criteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.companyId", "companyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyId.companyId", companyId));
		criteria.add(Restrictions.eq("remittanceTransaction.applicationFinanceYear", new BigDecimal(finYear)));
		criteria.setFetchMode("remittanceTransaction.documentId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.documentId", "documentId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("documentId.documentID", documentId));
		criteria.add(Restrictions.eq("remittanceTransaction.documentNo", documentCode));
		// criteria.add(Restrictions.eq("remittanceTransaction.documentFinanceYear",
		// finYear.shortValue()));
		criteria.add(Restrictions.eq("remittanceTransaction.isactive", Constants.Yes));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> objList = (List<RemittanceTransaction>) findAll(criteria);
		System.out.println("objList.size() =========== > " + objList.isEmpty());
		if (objList.isEmpty())
			return null;
		return objList;
	}

	@Override
	public BigDecimal getBanKId(String WU) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankCode", WU));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<BankMaster> bankList = criteria.getExecutableCriteria(getSession()).list();
		if (bankList.isEmpty()) {
			return null;
		}
		return bankList.get(0).getBankId();
	}

	@Override
	public BigDecimal getRemittanceId(String cashProduct) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", cashProduct));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return ((RemittanceModeMaster) criteria.getExecutableCriteria(getSession()).list().get(0)).getRemittanceModeId();
	}

	@Override
	public void updateReceiptPaymentTableData(BigDecimal receiptPaymentPk) {
		ReceiptPaymentApp receiptPaymentApp = (ReceiptPaymentApp) getSession().get(ReceiptPaymentApp.class, receiptPaymentPk);
		receiptPaymentApp.setApplicationStatus("P");
		getSession().update(receiptPaymentApp);
	}

	@Override
	public BigDecimal getReceiptPaymentTablePk(BigDecimal customerId, BigDecimal documentNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPaymentApp.class, "receiptPaymentApp");
		criteria.setFetchMode("receiptPaymentApp.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("receiptPaymentApp.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		criteria.add(Restrictions.eq("receiptPaymentApp.documentNo", documentNo));
		List<ReceiptPaymentApp> receiptPaymentList = findAll(criteria);
		if (receiptPaymentList.isEmpty()) {
			return null;
		}
		return receiptPaymentList.get(0).getReceiptId();
	}

	@Override
	public void updateRemittanceCompliantTableData(BigDecimal receiptPaymentPk) {
		RemittanceComplaint remittanceComplaint = (RemittanceComplaint) getSession().get(RemittanceComplaint.class, receiptPaymentPk);
		remittanceComplaint.setProblemStatus("C");
		getSession().update(remittanceComplaint);
	}

	@Override
	public BigDecimal getRemittanceCompliantPk(BigDecimal documentNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", documentNo));
		List<RemittanceComplaint> receiptPaymentList = findAll(criteria);
		if (receiptPaymentList.isEmpty()) {
			return null;
		}
		return receiptPaymentList.get(0).getRemittanceComplaintId();
	}

	@Override
	public BigDecimal getRemittanceId(BigDecimal languageId, String cashProduct) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeDescription.class, "remittanceModeDescription");
		criteria.setFetchMode("remittanceModeDescription.languageType", FetchMode.JOIN);
		criteria.createAlias("remittanceModeDescription.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		criteria.add(Restrictions.eq("remittanceModeDescription.remittanceDescription", cashProduct));
		List<RemittanceModeDescription> remittanceModeDescriptionList = criteria.getExecutableCriteria(getSession()).list();
		if (remittanceModeDescriptionList.isEmpty()) {
			return null;
		}
		return remittanceModeDescriptionList.get(0).getRemittanceModeMaster().getRemittanceModeId();
	}

	@Override
	public List<CountryBranch> getRemittanceTransaction(BigDecimal countryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> remittanceTransactionList = criteria.getExecutableCriteria(getSession()).list();
		if (remittanceTransactionList.isEmpty()) {
			return null;
		}
		return remittanceTransactionList;
	}

	@Override
	public List<RemittanceTransaction> getHighValueCusotmerList(BigDecimal branchId, String isActive) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.setFetchMode("remittanceTransaction.branchId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.branchId", "branchId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("branchId.countryBranchId", branchId));
		criteria.add(Restrictions.eq("remittanceTransaction.highValueTranx", isActive));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> remittanceTransactionList = criteria.getExecutableCriteria(getSession()).list();
		if (remittanceTransactionList.isEmpty()) {
			return null;
		}
		return remittanceTransactionList;
	}

	@Override
	public void updateRemittanceTransaction(List<BigDecimal> remittanceTransactionId, String userName) {
		for (BigDecimal id : remittanceTransactionId) {
			System.out.println("updateRemittanceTransaction ======= > " + id);
			RemittanceTransaction remittanceTransaction = (RemittanceTransaction) getSession().get(RemittanceTransaction.class, id);
			remittanceTransaction.setHighValueTranx(null);
			remittanceTransaction.setHighValueAuthUser(userName);
			remittanceTransaction.setHighValueAuthDate(new Date());
			getSession().update(remittanceTransaction);
		}
	}

	@Override
	public RemittanceView getRemittanceTransactionFromView(BigDecimal countryId, Integer finYear, BigDecimal documentCode, BigDecimal companyId, BigDecimal documentId) {
		DetachedCriteria critiria = DetachedCriteria.forClass(RemittanceView.class, "remittanceView");
		critiria.add(Restrictions.eq("remittanceView.applicationCountryId", countryId));
		critiria.add(Restrictions.eq("remittanceView.companyId", companyId));
		critiria.add(Restrictions.eq("remittanceView.documentNo", documentCode));
		critiria.add(Restrictions.eq("remittanceView.documentFinYear", new BigDecimal(finYear.intValue())));
		critiria.add(Restrictions.eq("remittanceView.documentCode", documentId));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceView> remittanceList = (List<RemittanceView>) findAll(critiria);
		if (remittanceList.isEmpty())
			return null;
		return remittanceList.get(0);
	}

	@Override
	public void saveAlltheDeatailsforCard(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		HashMap<String, Object> documentSerial = new HashMap<String, Object>();
		documentSerial = (HashMap<String, Object>) mapAllDetailForSave.get("DocuemntSeriality");
		Integer countryId = (Integer) documentSerial.get("countryId");
		Integer companyId = (Integer) documentSerial.get("companyId");
		Integer documentId = (Integer) documentSerial.get("documentCode");
		Integer dealYear = (Integer) documentSerial.get("dealYear");
		String processIn = (String) documentSerial.get("processIn");
		BigDecimal remittanceTransactionId = (BigDecimal) documentSerial.get("remittanceTransactionId");
		String status = (String) documentSerial.get("status");
		String userName = (String) documentSerial.get("userName");
		BigDecimal transferNo = (BigDecimal) documentSerial.get("transferNo");
		BigDecimal receiptNo = (BigDecimal) documentSerial.get("receiptNo");
		ReceiptPayment receiptPayment = (ReceiptPayment) mapAllDetailForSave.get("PaymentReceipt");
		Collect collect = (Collect) mapAllDetailForSave.get("Collection");
		List<CollectDetail> collectDetailList = (List<CollectDetail>) mapAllDetailForSave.get("CollectionDetails");
		try {
			BigDecimal branchCode = (BigDecimal) mapAllDetailForSave.get("BranchCode");
			String docuemntSeriality = getNextDocumentSerialNumber(countryId, companyId, documentId, dealYear, processIn, branchCode);
			if (docuemntSeriality == null || (docuemntSeriality != null && docuemntSeriality.equals(""))) {
				throw new Exception("Document Seriality is empty");
			}
			receiptPayment.setDocumentNo(new BigDecimal(docuemntSeriality));
			receiptPayment.setColDocNo(new BigDecimal(docuemntSeriality));
			collect.setDocumentNo(new BigDecimal(docuemntSeriality));
			getSession().saveOrUpdate(collect);
			for (int i = 0; i < collectDetailList.size(); i++) {
				CollectDetail collectDetail = collectDetailList.get(i);
				collectDetail.setDocumentNo(new BigDecimal(docuemntSeriality));
				collectDetail.setCashCollectionId(collect);
				getSession().saveOrUpdate(collectDetail);
			}
			getSession().saveOrUpdate(receiptPayment);
			updateTransactiondetailsbyTransactionId(remittanceTransactionId, status, userName);
			updateCompliant(transferNo, new BigDecimal(dealYear), documentId, receiptNo, status, userName, new BigDecimal(countryId), new BigDecimal(companyId), docuemntSeriality);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void saveAlltheDeatailsforCash(HashMap<String, Object> mapAllDetailForSave) throws Exception {
		HashMap<String, Object> documentSerial = new HashMap<String, Object>();
		documentSerial = (HashMap<String, Object>) mapAllDetailForSave.get("DocuemntSeriality");
		try {
			Integer countryId = (Integer) documentSerial.get("countryId");
			Integer companyId = (Integer) documentSerial.get("companyId");
			Integer documentId = (Integer) documentSerial.get("documentCode");
			Integer dealYear = (Integer) documentSerial.get("dealYear");
			String processIn = (String) documentSerial.get("processIn");
			BigDecimal remittanceTransactionId = (BigDecimal) documentSerial.get("remittanceTransactionId");
			String status = (String) documentSerial.get("status");
			String userName = (String) documentSerial.get("userName");
			BigDecimal transferNo = (BigDecimal) documentSerial.get("transferNo");
			BigDecimal receiptNo = (BigDecimal) documentSerial.get("receiptNo");
			ReceiptPayment receiptPayment = (ReceiptPayment) mapAllDetailForSave.get("PaymentReceipt");
			Collect collect = (Collect) mapAllDetailForSave.get("Collection");
			List<CollectDetail> collectDetailList = (List<CollectDetail>) mapAllDetailForSave.get("CollectionDetails");
			List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = (List<ForeignCurrencyAdjust>) mapAllDetailForSave.get("ForeignCurrencyAdjust");
			BigDecimal branchCode = (BigDecimal) mapAllDetailForSave.get("BranchCode");
			String docuemntSeriality = getNextDocumentSerialNumber(countryId, companyId, documentId, dealYear, processIn, branchCode);
			if (docuemntSeriality == null || (docuemntSeriality != null && docuemntSeriality.equals("")) || (docuemntSeriality != null && docuemntSeriality.equals("0"))) {
				throw new Exception("Document Seriality is empty");
			}
			receiptPayment.setDocumentNo(new BigDecimal(docuemntSeriality));
			receiptPayment.setColDocNo(new BigDecimal(docuemntSeriality));
			collect.setDocumentNo(new BigDecimal(docuemntSeriality));
			getSession().saveOrUpdate(receiptPayment);
			getSession().saveOrUpdate(collect);
			for (int i = 0; i < collectDetailList.size(); i++) {
				CollectDetail collectDetail = collectDetailList.get(i);
				collectDetail.setDocumentNo(new BigDecimal(docuemntSeriality));
				collectDetail.setCashCollectionId(collect);
				getSession().saveOrUpdate(collectDetail);
			}
			updateTransactiondetailsbyTransactionId(remittanceTransactionId, status, userName);
			updateCompliant(transferNo, new BigDecimal(dealYear), documentId, receiptNo, status, userName, new BigDecimal(countryId), new BigDecimal(companyId), docuemntSeriality);
			for (ForeignCurrencyAdjust foreignCurrencyAdjust : foreignCurrencyAdjustList) {
				foreignCurrencyAdjust.setDocumentNo(new BigDecimal(docuemntSeriality));
				foreignCurrencyAdjust.setCollect(collect);
				getSession().saveOrUpdate(foreignCurrencyAdjust);
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remitterTrx");
		dCriteria.add(Restrictions.eq("remittanceTransactionId", remittanceTransactionId));
		RemittanceTransaction remitTransaction = ((List<RemittanceTransaction>) findAll(dCriteria)).get(0);
		remitTransaction.setTransactionUpdatedBy(userName);
		remitTransaction.setTransactionUpdatedDate(new Date());
		remitTransaction.setModifiedBy(userName);
		remitTransaction.setModifiedDate(new Date());
		remitTransaction.setTransactionStatus(status);
		getSession().saveOrUpdate(remitTransaction);
	}

	public void updateCompliant(BigDecimal docuemtnNo, BigDecimal finaceYear, int documentCodeForStoppayment, BigDecimal receiptNo, String status, String userName, BigDecimal applicationCountryId, BigDecimal companyId, String documentSerial) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");
		criteria.add(Restrictions.eq("document.documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT)));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Document> docuList = (List<Document>) findAll(criteria);
		if (docuList == null || (docuList != null && docuList.isEmpty())) {
			throw new Exception("Document Seriality missing for Stop Payment Request");
		}
		BigDecimal documentId = docuList.get(0).getDocumentID();
		criteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CompanyMaster> compList = (List<CompanyMaster>) findAll(criteria);
		if (compList == null || (compList != null && compList.isEmpty())) {
			throw new Exception("Company Code is missing");
		}
		BigDecimal companyCode = compList.get(0).getCompanyCode();
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		/*
		 * detachedCriteria.add(Restrictions.le(
		 * "userFinancialYear.financialYearBegin", new Date()))
		 * .add(Restrictions.ge("userFinancialYear.financialYearEnd", new
		 * Date()));
		 * 
		 * 
		 * List<UserFinancialYear> listUserFinancialYear =
		 * (List<UserFinancialYear>) findAll(detachedCriteria);
		 * 
		 * if(!listUserFinancialYear.isEmpty()) {
		 * 
		 * }
		 */
		List<UserFinancialYear> listUserFinancialYear = null;
		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		Date S = formatter.parse(formatter.format(new Date()));
		System.out.println("Today : " + S);
		if (S != null) {
			detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", S)).add(Restrictions.ge("userFinancialYear.financialYearEnd", S));
		}
		listUserFinancialYear = (List<UserFinancialYear>) findAll(detachedCriteria);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remitterCom");
		dCriteria.add(Restrictions.eq("documentFinanceYear", finaceYear));
		dCriteria.add(Restrictions.eq("documentNo", docuemtnNo));
		RemittanceComplaint remittanceComplaint = null;
		List<RemittanceComplaint> list = ((List<RemittanceComplaint>) findAll(dCriteria));
		if (list == null || (list != null && list.isEmpty())) {
			remittanceComplaint = new RemittanceComplaint();
			remittanceComplaint.setCancelDocumentFinanceYear(finaceYear);
			remittanceComplaint.setProblemStatus(status);
			remittanceComplaint.setCreatedBy(userName);
			remittanceComplaint.setCreatedDate(new Date());
			remittanceComplaint.setDocumentNo(docuemtnNo);
			if (listUserFinancialYear != null && !listUserFinancialYear.isEmpty()) {
				remittanceComplaint.setDocumentFinanceYear(listUserFinancialYear.get(0).getFinancialYearID());
			}
			remittanceComplaint.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT));
			remittanceComplaint.setDocumentId(documentId);
			remittanceComplaint.setIsactive(Constants.Yes);
			remittanceComplaint.setApplicationCountryId(applicationCountryId);
			remittanceComplaint.setCompanyId(companyId);
			remittanceComplaint.setCompanyCode(companyCode);
			remittanceComplaint.setCancelDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
			remittanceComplaint.setCancelDocumentDate(new Date());
			remittanceComplaint.setCancelDocumentFinanceYear(finaceYear);
			remittanceComplaint.setCancelDocumentNumber(new BigDecimal(documentSerial));
		} else {
			remittanceComplaint = list.get(0);
			remittanceComplaint.setModifiedBy(userName);
			remittanceComplaint.setModifiedDate(new Date());
		}
		//remittanceComplaint.setRemittanceTransactionId(getRemittanceTrxId());
		remittanceComplaint.setCancelDocumentFinanceYear(finaceYear);
		// remittanceComplaint.setStopDocumentNumber(receiptNo);
		remittanceComplaint.setProblemStatus(status);
		// remittanceComplaint.setStopDocumentDate(new Date());
		getSession().saveOrUpdate(remittanceComplaint);
	}

	public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn, BigDecimal branchId) {
		int out = 0;
		Connection connection = null;
		// connection = DBConnection.getdataconnection();
		connection = getDataSourceFromHibernateSession();
		CallableStatement cs;
		try {
			cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?,?)}");
			cs.setInt(1, countryId);
			cs.setBigDecimal(2, branchId);
			cs.setInt(3, companyId);
			cs.setInt(4, documentId);
			cs.setInt(5, financialYear);
			cs.setString(6, processIn);
			cs.registerOutParameter(7, java.sql.Types.INTEGER);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.registerOutParameter(9, java.sql.Types.VARCHAR);
			// cs.executeUpdate();
			cs.execute();
			out = cs.getInt(7);
			String a = cs.getString(8);
			String b = cs.getString(9);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return String.valueOf(out);
	}

	@Override
	public String getCompanyName(BigDecimal companyId, BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		List<CompanyMasterDesc> data = (List<CompanyMasterDesc>) findAll(criteria);
		return data.get(0).getCompanyName();
	}

	@Override
	public boolean checkRemittanceCancelationStatus(BigDecimal companyId, BigDecimal documentId, BigDecimal documentCode, BigDecimal documentNo, BigDecimal caneDocFinYear) {
		boolean statusCheck = false;
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		criteria.add(Restrictions.eq("remittanceComplaint.companyId", companyId));
		criteria.add(Restrictions.eq("remittanceComplaint.documentId", documentId));
		criteria.add(Restrictions.eq("remittanceComplaint.documentCode", documentCode));
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", documentNo));
		criteria.add(Restrictions.eq("remittanceComplaint.cancelDocumentFinanceYear", caneDocFinYear));
		criteria.add(Restrictions.ne("remittanceComplaint.cancellationStatus", Constants.D));
		
		List<RemittanceComplaint> data = (List<RemittanceComplaint>) findAll(criteria);
		if (data != null && data.size() > 0) {
			statusCheck = true;
		} else {
			statusCheck = false;
		}
		return statusCheck;
	}

	@Override
	public List<CurrencyMasterView> getCurrencyDetails(BigDecimal currencyId) {
		DetachedCriteria dcriteria = DetachedCriteria.forClass(CurrencyMasterView.class, "currencyMasterView");
		dcriteria.add(Restrictions.eq("currencyMasterView.currencyId", currencyId));
		// dcriteria.add(Restrictions.eq("currencyMasterView.isactive",
		// Constants.Yes));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CurrencyMasterView> lstCurrencyMasterView = (List<CurrencyMasterView>) findAll(dcriteria);
		return lstCurrencyMasterView;
	}

	@Override
	public List<ReceiptPayment> checkRecieptPaymentExist(BigDecimal transferYear, BigDecimal TransferrefNo) {
		//boolean statusCheck = false;
		String receiptType = "99";
		DetachedCriteria dcriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		dcriteria.add(Restrictions.eq("receiptPayment.transferReference", TransferrefNo));
		dcriteria.add(Restrictions.eq("receiptPayment.transferFinanceYear", transferYear));
		dcriteria.add(Restrictions.eq("receiptPayment.documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST) ));
		dcriteria.add(Restrictions.ne("receiptPayment.receiptType", receiptType));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ReceiptPayment> lstReceiptPayment = (List<ReceiptPayment>) findAll(dcriteria);
		/*if (lstReceiptPayment != null && lstReceiptPayment.size() > 0) {
			statusCheck = true;
		} else {
			statusCheck = false;
		}*/
		return lstReceiptPayment;
	}

	@Override
	public void saveOrUpDateAllValues(HashMap<String, Object> inputValues) throws AMGException{
		try {
			// Receipt Update
			String receiptPK = (String) inputValues.get("receiptPK");
			String user = (String) inputValues.get("user");
			ReceiptPayment receiptPayment = (ReceiptPayment) getSession().get(ReceiptPayment.class, new BigDecimal(receiptPK));
			receiptPayment.setModifiedDate(new Date());
			receiptPayment.setModifiedBy(user);
			receiptPayment.setDocumentStatus("P");
			getSession().saveOrUpdate(receiptPayment);
			// Remittance Update
			String remitTrnxId = (String) inputValues.get("remitTrnxId");
			if(remitTrnxId != null){
				RemittanceComplaint remittanceTransaction = (RemittanceComplaint) getSession().get(RemittanceComplaint.class, new BigDecimal(remitTrnxId));
				remittanceTransaction.setModifiedDate(new Date());
				remittanceTransaction.setModifiedBy(user);
				remittanceTransaction.setCancellationStatus("C");
				getSession().saveOrUpdate(remittanceTransaction);
			}
			
			List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = (List<ForeignCurrencyAdjust>) inputValues.get("ForeignCurrencyAdjust");
			for (ForeignCurrencyAdjust foreignCurrencyAdjust : foreignCurrencyAdjustList) {
				getSession().saveOrUpdate(foreignCurrencyAdjust);
			}

			List<Payment> paymentlst = (List<Payment>) inputValues.get("Payment");
			for (Payment payment : paymentlst) {
				getSession().saveOrUpdate(payment);
			}
			
		} catch (Exception e) {
			throw new AMGException(e.getMessage());
		}
	}

	@Override
	public BigDecimal toFetchRemitTrnxPk(BigDecimal companyId, BigDecimal countryId, BigDecimal documentId, BigDecimal documentNum, BigDecimal docFinYear) {
		BigDecimal remitTrnxPk = null;
		DetachedCriteria dcriteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		dcriteria.add(Restrictions.eq("remittanceComplaint.companyId", companyId));
		dcriteria.add(Restrictions.eq("remittanceComplaint.applicationCountryId", countryId));
		dcriteria.add(Restrictions.eq("remittanceComplaint.cancelDocumentCode", documentId));
		dcriteria.add(Restrictions.eq("remittanceComplaint.cancelDocumentNumber", documentNum));
		dcriteria.add(Restrictions.eq("remittanceComplaint.cancelDocumentFinanceYear", docFinYear));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> lstReceiptPayment = (List<RemittanceComplaint>) findAll(dcriteria);
		if (lstReceiptPayment.size() > 0) {
			remitTrnxPk = lstReceiptPayment.get(0).getRemittanceComplaintId();
		}
		return remitTrnxPk;
	}

	@Override
	public BigDecimal toFetchDocumentPk(BigDecimal documentCode) {
		BigDecimal documentPk = null;
		DetachedCriteria dcriteria = DetachedCriteria.forClass(Document.class, "document");
		dcriteria.add(Restrictions.eq("document.documentCode", documentCode));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Document> lstDocument = (List<Document>) findAll(dcriteria);
		if (lstDocument.size() > 0) {
			documentPk = lstDocument.get(0).getDocumentID();
		}
		return documentPk;
	}

	@Override
	public HashMap<String, Object> toFetchRefundDetails(HashMap<String, Object> inputParamters) throws AMGException {
		
		HashMap<String, Object> outPutParamters = new HashMap<String, Object>();
		Connection connection = null;
		
		try{
			
			CallableStatement cs;
			String confirmInd = null;
			String errormsg = null;

			BigDecimal companyId = (BigDecimal) inputParamters.get("P_REMIT_COMP_ID");
			BigDecimal documentId = (BigDecimal) inputParamters.get("P_REMIT_DOC_ID");
			BigDecimal docFinYear = (BigDecimal) inputParamters.get("P_REMIT_DOCFYR");
			BigDecimal docNumber = (BigDecimal) inputParamters.get("P_REMIT_DOCNO");
			BigDecimal locationId = (BigDecimal) inputParamters.get("P_LOGIN_BRANCH_ID");

			connection = getDataSourceFromHibernateSession();

			// calling procedure
			cs = connection.prepareCall(" { call EX_P_VALIDATE_REFUND (?,?,?,?,?,?,?)}");
			cs.setBigDecimal(1, companyId);
			cs.setBigDecimal(2, documentId);
			cs.setBigDecimal(3, docFinYear);
			cs.setBigDecimal(4, docNumber);
			cs.setBigDecimal(5, locationId);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.execute();
			confirmInd = cs.getString(6);
			errormsg = cs.getString(7);
			
			outPutParamters.put("P_CONFIRM_IND", confirmInd);
			outPutParamters.put("P_ERROR", errormsg);
			
		}catch (SQLException e) {
			String erromsg = "EX_P_VALIDATE_REFUND" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}catch(Exception e){

		}finally{
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return outPutParamters;
	}

	@Override
	public List<RemittanceComplaint> checkRemittanceComplaintExist(BigDecimal transferYear, BigDecimal TransferrefNo) {
		
		DetachedCriteria dcriteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		dcriteria.add(Restrictions.eq("remittanceComplaint.documentNo", TransferrefNo));
		dcriteria.add(Restrictions.eq("remittanceComplaint.documentFinanceYear", transferYear));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> lstremittanceComplaint = (List<RemittanceComplaint>) findAll(dcriteria);
		return lstremittanceComplaint;
	}
	
	@Override
	public List<RemittanceComplaint> getRefundRequestExist(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		criteria.add(Restrictions.eq("remittanceComplaint.companyId", companyID));
		criteria.add(Restrictions.eq("remittanceComplaint.documentId", documentID));
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", docNumber));
		criteria.add(Restrictions.eq("remittanceComplaint.documentFinanceYear", docFinanceYearId));
		criteria.add(Restrictions.eq("remittanceComplaint.cancellationStatus", cancelStatus));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> lstremittanceComplaint = (List<RemittanceComplaint>) findAll(criteria);
		return lstremittanceComplaint;
	}
	
	
	@Override
	public BigDecimal roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) throws AMGException {
		// TODO Auto-generated method stub
		LOGGER.info("Entered into roundingTotalNetAmountbyFunction(BigDecimal applcountryId, BigDecimal totalNetAmount, String roundstatus) Method");
		LOGGER.info("Procedure Name =  EX_FN_EXCH_AMOUNT_ROUND");
		LOGGER.info("applcountryId =" + applcountryId);
		LOGGER.info("totalNetAmount =" + totalNetAmount);
		LOGGER.info("roundstatus =" + roundstatus);
		BigDecimal roundvalue = null;
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			LOGGER.error("Error Occured in EX_FN_EXCH_AMOUNT_ROUND Procedure call");
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { ? = call EX_FN_EXCH_AMOUNT_ROUND (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.INTEGER);
			cs.setBigDecimal(2, applcountryId);
			cs.setBigDecimal(3, totalNetAmount);
			cs.setString(4, roundstatus);
			cs.setString(5, "R");
			cs.execute();
			roundvalue = cs.getBigDecimal(1);
			LOGGER.info("roundingTotalNetAmountbyFunction applcountryId:" + applcountryId);
			LOGGER.info("roundingTotalNetAmountbyFunction totalNetAmount:" + totalNetAmount);
			LOGGER.info("roundingTotalNetAmountbyFunction roundstatus:" + roundstatus);
			LOGGER.info("roundingTotalNetAmountbyFunction roundvalue:" + roundvalue);
		} catch (SQLException e) {
			LOGGER.info("Error while calling EX_FN_EXCH_AMOUNT_ROUND procedure ");
			String erromsg = "EX_FN_EXCH_AMOUNT_ROUND" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				LOGGER.info("Error while calling EX_FN_EXCH_AMOUNT_ROUND procedure ");
				String erromsg = "EX_FN_EXCH_AMOUNT_ROUND" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		LOGGER.info("Exited from the roundingTotalNetAmountbyFunction method");
		return roundvalue;
	}
	
	@Override
	public void saveOrUpdate(BigDecimal companyID,BigDecimal documentID,BigDecimal docNumber,BigDecimal docFinanceYearId,String cancelStatus,BigDecimal remittanceYear,String username){
		
		String hql="update RemittanceComplaint c set c.cancellationStatus= :pcancellationStatus,c.modifiedBy= :pmodifiedBy,c.modifiedDate =:pmodifiedDate where c.companyId = :pcompanyId and c.documentId = :pdocumentId and c.documentNo= :pdocumentNo and c.documentFinanceYear =:pdocumentFinanceYear";
		Query query = getSession().createQuery(hql);
		query.setParameter("pcancellationStatus", Constants.D);
		query.setParameter("pcompanyId", companyID);
		query.setParameter("pdocumentId", documentID);
		query.setParameter("pdocumentNo", docNumber);
		query.setParameter("pdocumentFinanceYear", docFinanceYearId);		
		query.setParameter("pmodifiedBy", username);
		query.setParameter("pmodifiedDate", new Date());
		int result = query.executeUpdate();
		
		String hqlQ="update ReceiptPayment r set r.documentStatus= :pcancellationStatus,r.isActive =:pisActive,r.modifiedBy=:pmodifiedBy,r.modifiedDate =:pmodifiedDate where r.transferReference= :pdocumentNo and r.transferFinanceYear= :pdocumentFinanceYear and r.documentStatus='U'";
		Query q = getSession().createQuery(hqlQ);
		q.setParameter("pdocumentNo", docNumber);
		q.setParameter("pdocumentFinanceYear", remittanceYear);		
		q.setParameter("pmodifiedBy", username);
		q.setParameter("pmodifiedDate", new Date());
		q.setParameter("pcancellationStatus", Constants.C);
		q.setParameter("pisActive", Constants.D);
		int results = q.executeUpdate();
	}
}
