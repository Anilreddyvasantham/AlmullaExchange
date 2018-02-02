package com.amg.exchange.stoppayment.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRemittanceInquiryTransaction;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.ExchangeRateApprovalDetModel;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.remittance.model.RemittanceTxnView;
import com.amg.exchange.stoppayment.bean.StoppaymentCollectionBean;
import com.amg.exchange.stoppayment.dao.IStopPaymentDao;
import com.amg.exchange.stoppayment.model.RefundInquiryView;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.model.RemittanceTranxBenificiary;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Repository
@SuppressWarnings("unchecked")
public class StopPaymentDaoImpl<T> extends CommonDaoImpl<T> implements IStopPaymentDao<T> {


	private static final Logger LOGGER = Logger.getLogger(StoppaymentCollectionBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public List<RemittanceApplication> viewDetails(BigDecimal docNo, BigDecimal appCountryId) {
		System.out.println("docNo" + docNo);
		System.out.println("appCountryId" + appCountryId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		criteria.setFetchMode("remittanceApplication.fsCountryMasterByApplicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCountryMasterByApplicationCountryId", "fsCountryMasterapplicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMasterByApplicationCountryId.countryId", appCountryId));
		criteria.add(Restrictions.eq("documentNo", docNo));
		criteria.setFetchMode("remittanceApplication.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("remittanceApplication.fsCustomer", "fsCustomer", JoinType.LEFT_OUTER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceApplication>) findAll(criteria);
	}

	@Override
	public List<RemittanceTransaction> viewDetailsTransaction(BigDecimal transferNo, BigDecimal appCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.add(Restrictions.eq("documentNo", transferNo));
		criteria.add(Restrictions.eq("applicationCountryId", appCountryId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceTransaction>) findAll(criteria);
	}

	@Override
	public List<RemittanceAppBenificiary> viewDetailsBeneficiary(BigDecimal remiitaceAppId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceAppBenificiary.class, "remittanceBenificiary");
		criteria.setFetchMode("remittanceBenificiary.exRemittanceAppfromBenfi", FetchMode.JOIN);
		criteria.createAlias("remittanceBenificiary.exRemittanceAppfromBenfi", "exRemittanceAppfromBenfi", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("exRemittanceAppfromBenfi.remittanceApplicationId", remiitaceAppId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceAppBenificiary>) findAll(criteria);
	}

	@Override
	public List<RemittanceTranxBenificiary> viewDetailsTranxBeneficiary(BigDecimal remiitaceTrxId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTranxBenificiary.class, "remittanceTranxBenificiary");
		criteria.add(Restrictions.eq("remittanceTranxBenificiary.remittanceTranxId", remiitaceTrxId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceTranxBenificiary>) findAll(criteria);
	}

	@Override
	public List<ReceiptPayment> viewDetailsPayment(BigDecimal docNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		criteria.add(Restrictions.eq("receiptPayment.documentNo", docNo));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ReceiptPayment>) findAll(criteria);
	}

	@Override
	public void saveOrUpdate(T entity) {
		super.saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdate(BigDecimal remiTrxId, String transactionStatus, String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remitterTrx");
		dCriteria.add(Restrictions.eq("remittanceTransactionId", remiTrxId));
		try {
			RemittanceTransaction remitTransaction = ((List<RemittanceTransaction>) findAll(dCriteria)).get(0);
			remitTransaction.setModifiedBy(userName);
			remitTransaction.setModifiedDate(new Date());
			remitTransaction.setTransactionStatus(transactionStatus);
			getSession().saveOrUpdate(remitTransaction);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public RemittanceTransaction viewTransactiondetailsbyDocumentNo(BigDecimal transferNo, BigDecimal appCountryId, BigDecimal financeYear, BigDecimal companyId) {
		System.out.println("Document no" + transferNo);
		System.out.println("appCountryId" + appCountryId);
		System.out.println("financeYear" + financeYear);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		// criteria.add(Restrictions.eq("remittanceTransaction.documentNo",
		// transferNo));
		criteria.add(Restrictions.eq("remittanceTransaction.documentNo", transferNo));


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

		criteria.setFetchMode("remittanceTransaction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountryId.countryId", appCountryId));

		criteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.companyId", "companyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyId.companyId", companyId));

		criteria.add(Restrictions.eq("remittanceTransaction.applicationFinanceYear", financeYear));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> remitapp = (List<RemittanceTransaction>) findAll(criteria);
		if (null != remitapp) {
			if (!remitapp.isEmpty()) {
				return remitapp.get(0);
			}
		}
		return null;
	}

	@Override
	public RemittanceComplaint viewRemittanceComplaintbyDocumentNo(BigDecimal documentNo, BigDecimal financialYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", documentNo));
		criteria.add(Restrictions.eq("remittanceComplaint.documentFinanceYear", financialYear));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> remitCompliant = (List<RemittanceComplaint>) findAll(criteria);
		if (null != remitCompliant) {
			if (!remitCompliant.isEmpty()) {
				return remitCompliant.get(0);
			}
		}
		return null;
	}

	@Override
	public void updateTransactiondetailsbyTransactionId(BigDecimal remittanceTransactionId, String status, String userName) {
		RemittanceTransaction   remitTransaction=(RemittanceTransaction) getSession().get(RemittanceTransaction.class, remittanceTransactionId);
		//DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remitterTrx");
		//dCriteria.add(Restrictions.eq("remittanceTransactionId", remittanceTransactionId));
		//RemittanceTransaction remitTransaction = ((List<RemittanceTransaction>) findAll(dCriteria)).get(0);
		remitTransaction.setTransactionUpdatedBy(userName);
		remitTransaction.setTransactionUpdatedDate(new Date());
		remitTransaction.setModifiedBy(userName);
		remitTransaction.setModifiedDate(new Date());
		remitTransaction.setTransactionStatus(status);
		getSession().saveOrUpdate(remitTransaction);
	}


	public void updateCompliant(BigDecimal documentYearId,BigDecimal docuemtnNo, String finaceYear, int documentCodeForStoppayment, BigDecimal receiptNo, String status, String userName,BigDecimal applicationCountryId,BigDecimal companyId,String documentSerial) throws Exception {


		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");
		//BigDecimal b=new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT);
		criteria.add(Restrictions.eq("document.documentCode", new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT)));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> docuList = (List<Document>) findAll(criteria);

		if(docuList==null || (docuList!=null && docuList.isEmpty()) )
		{
			throw new Exception("Document Seriality missing for Stop Payment Request");
		}

		BigDecimal documentId = docuList.get(0).getDocumentID();

		criteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");

		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CompanyMaster> compList = (List<CompanyMaster>) findAll(criteria);

		if(compList==null || (compList!=null && compList.isEmpty()) )
		{
			throw new Exception("Company Code is missing");
		}


		BigDecimal companyCode = compList.get(0).getCompanyCode();


		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");

		detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", new Date()))
		.add(Restrictions.ge("userFinancialYear.financialYearEnd", new Date()));


		List<UserFinancialYear> listUserFinancialYear =  (List<UserFinancialYear>) findAll(detachedCriteria);

		if(!listUserFinancialYear.isEmpty())
		{

		}

		DetachedCriteria dCriteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remitterCom");
		dCriteria.add(Restrictions.eq("documentFinanceYear", documentYearId));
		dCriteria.add(Restrictions.eq("documentNo", docuemtnNo));
		RemittanceComplaint remittanceComplaint = null;

		List<RemittanceComplaint> list = ((List<RemittanceComplaint>) findAll(dCriteria));

		if (list == null || (list != null && list.isEmpty())) {
			remittanceComplaint = new RemittanceComplaint();
			//remittanceComplaint.setStopDocumentNumber(receiptNo);
			remittanceComplaint.setProblemStatus(status);
			remittanceComplaint.setCreatedBy(userName);
			remittanceComplaint.setCreatedDate(new Date());
			//remittanceComplaint.setStopDocumentDate(new Date());
			remittanceComplaint.setDocumentNo(docuemtnNo);
			/*if(!listUserFinancialYear.isEmpty())
			{
				remittanceComplaint.setDocumentFinanceYear(listUserFinancialYear.get(0).getFinancialYearID());
			}*/

			remittanceComplaint.setDocumentFinanceYear(documentYearId);
			remittanceComplaint.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REMMITANCE_COMPLIANT));
			remittanceComplaint.setDocumentId(documentId);
			remittanceComplaint.setIsactive(Constants.Yes);
			remittanceComplaint.setApplicationCountryId(applicationCountryId);
			remittanceComplaint.setCompanyId(companyId);
			remittanceComplaint.setCompanyCode(companyCode);
			remittanceComplaint.setStopDocumentCode(Constants.DOCUMENT_CODE_FOR_STOPPAYMENT);
			remittanceComplaint.setStopDocumentDate(new Date());
			remittanceComplaint.setStopDocumentFinanceYear(new BigDecimal(finaceYear));
			remittanceComplaint.setStopDocumentNumber(new BigDecimal(documentSerial));

		} else {
			remittanceComplaint = list.get(0);
			remittanceComplaint.setModifiedBy(userName);
			remittanceComplaint.setModifiedDate(new Date());
		}

		remittanceComplaint.setDocumentFinanceYear(documentYearId);
		remittanceComplaint.setStopDocumentFinanceYear(new BigDecimal(finaceYear));
		//remittanceComplaint.setStopDocumentNumber(receiptNo);
		remittanceComplaint.setProblemStatus(status);
		remittanceComplaint.setStopDocumentDate(new Date());
		getSession().saveOrUpdate(remittanceComplaint);

	}

	@Override
	public BigDecimal getWUbankId(String WU) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		criteria.add(Restrictions.eq("bankMaster.bankCode",WU));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<BankMaster> bankMaster = (List<BankMaster>) findAll(criteria);
		if (null != bankMaster) {
			if (!bankMaster.isEmpty()) {
				return bankMaster.get(0).getBankId();
			}
		}
		return null;
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
			BigDecimal transferYearId = (BigDecimal) documentSerial.get("transferYearId");
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
			updateCompliant(transferYearId, transferNo,  dealYear.toString(), documentId, receiptNo, status, userName, new BigDecimal(countryId), new BigDecimal(companyId), docuemntSeriality);

			for (ForeignCurrencyAdjust foreignCurrencyAdjust : foreignCurrencyAdjustList) {
				foreignCurrencyAdjust.setDocumentNo(new BigDecimal(docuemntSeriality));
				foreignCurrencyAdjust.setCollect(collect);
				getSession().saveOrUpdate(foreignCurrencyAdjust);
			}


		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn,BigDecimal branchId) {

		int out=0;

		Connection connection=null;
		//connection = DBConnection.getdataconnection();
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
			//cs.executeUpdate();
			cs.execute();
			out =cs.getInt(7);
			String a=cs.getString(8);
			String b=cs.getString(9);



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}finally{
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
		BigDecimal transferYearId = (BigDecimal) documentSerial.get("transferYearId");
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
			updateCompliant(transferYearId,transferNo, new String(dealYear.toString()), documentId, receiptNo, status, userName, new BigDecimal(countryId), new BigDecimal(companyId), docuemntSeriality);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public BigDecimal getcashRemittenceCode(String  cash) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		criteria.add(Restrictions.eq("remittanceModeMaster.remittance", cash));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		List<RemittanceModeMaster> remittanceModeMaster = (List<RemittanceModeMaster>) findAll(criteria);
		if (null != remittanceModeMaster) {
			if (!remittanceModeMaster.isEmpty()) {
				return remittanceModeMaster.get(0).getRemittanceModeId();
			}
		}
		return null;
	}

	@Override
	public BeneficaryAccount getAccountDetails(BigDecimal beneficiaryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryAccount.class, "beneficaryAccount");
		criteria.add(Restrictions.eq("beneficaryAccount.beneficaryAccountSeqId", beneficiaryId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<BeneficaryAccount> beneficaryAccountList = (List<BeneficaryAccount>) findAll(criteria);

		if (null != beneficaryAccountList) {
			if (!beneficaryAccountList.isEmpty()) {
				return beneficaryAccountList.get(0);
			}
		}
		return null;


	}

	@Override
	public BeneficaryRelationship getRelationship(BigDecimal beneficiaryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BeneficaryRelationship.class, "beneficaryRelationship");
		criteria.add(Restrictions.eq("beneficaryRelationship.beneficaryRelationshipId", beneficiaryId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<BeneficaryRelationship> beneficaryAccountList = (List<BeneficaryRelationship>) findAll(criteria);

		if (null != beneficaryAccountList) {
			if (!beneficaryAccountList.isEmpty()) {
				return beneficaryAccountList.get(0);
			}
		}
		return null;

	}

	@Override
	public RemittanceTxnView getRemittanceTransactionDetailsfromView(BigDecimal transferNo, BigDecimal countryId, BigDecimal fyear, BigDecimal companyId,BigDecimal documentCode) {
		LOGGER.info("Entering into getRemittanceTransactionDetailsfromView method");
		LOGGER.info("transferNo " + transferNo);
		LOGGER.info("countryId " + countryId);
		LOGGER.info("fyear " + fyear);
		LOGGER.info("companyId " + companyId);
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTxnView.class, "remittanceTxnView");
		criteria.add(Restrictions.eq("remittanceTxnView.documentNo", transferNo));
		criteria.add(Restrictions.eq("remittanceTxnView.applicationCountryId", countryId));
		criteria.add(Restrictions.eq("remittanceTxnView.companyId", companyId));
		if(fyear!=null){
			criteria.add(Restrictions.eq("remittanceTxnView.documentFinancialYear", fyear));
		}
		if(documentCode != null){
			criteria.add(Restrictions.eq("remittanceTxnView.documentCode", documentCode));
		}
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTxnView> remittanceTxnViewList = (List<RemittanceTxnView>) findAll(criteria);
		LOGGER.info("Entering into getRemittanceTransactionDetailsfromView method");
		if (remittanceTxnViewList != null && !remittanceTxnViewList.isEmpty()) {
			return remittanceTxnViewList.get(0);
		}
		return null;
	}

	@Override
	public CollectDetail getCollectionDetails(BigDecimal collectionDocId, BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo ){
		LOGGER.info("Entering into getCollectionDetails method");
		LOGGER.info("collectionDocId " + collectionDocId);
		LOGGER.info("collectionDocFinanceYear " + collectionDocFinanceYear);
		LOGGER.info("collectionDocumentNo " + collectionDocumentNo);

		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetail.class, "collectDetail");
		criteria.add(Restrictions.eq("collectDetail.documentCode", collectionDocId));
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", collectionDocFinanceYear));
		criteria.add(Restrictions.eq("collectDetail.documentNo", collectionDocumentNo));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CollectDetail> collectDetail = (List<CollectDetail>) findAll(criteria);
		LOGGER.info("Entering into getRemittanceTransactionDetailsfromView method");
		if (collectDetail != null && !collectDetail.isEmpty()) {
			return collectDetail.get(0);
		}
		return null;


	}

	@Override
	public PaymentModeDesc getPaymentDescRec(BigDecimal paymentId , BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");

		dCriteria.setFetchMode("paymentModeDesc.paymentMode",FetchMode.SELECT);
		dCriteria.createAlias("paymentModeDesc.paymentMode", "paymentMode", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentId));

		dCriteria.setFetchMode("paymentModeDesc.languageType",FetchMode.SELECT);
		dCriteria.createAlias("paymentModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("languageType.languageId", paymentId));
		List<PaymentModeDesc> paymentModeDesc = (List<PaymentModeDesc>) findAll(dCriteria);

		if (paymentModeDesc != null && !paymentModeDesc.isEmpty()) {
			return paymentModeDesc.get(0);
		}
		return null;


	}

	@Override
	public BankMaster getArabicBankName(BigDecimal bankId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");


		dCriteria.add(Restrictions.eq("bankMaster.bankId", bankId));	

		dCriteria.add(Restrictions.eq("bankMaster.languageInd", Constants.ARABIC_LANGUAGE_ID));
		List<BankMaster> bankMaster = (List<BankMaster>) findAll(dCriteria);

		if (bankMaster != null && !bankMaster.isEmpty()) {
			return bankMaster.get(0);
		}
		return null;


	}
	@Override
	public List<CollectDetail> getCollectionDetail(BigDecimal collectionDocId,
			BigDecimal collectionDocFinanceYear, BigDecimal collectionDocumentNo) {
		LOGGER.info("Entering into getCollectionDetails method");
		LOGGER.info("collectionDocId " + collectionDocId);
		LOGGER.info("collectionDocFinanceYear " + collectionDocFinanceYear);
		LOGGER.info("collectionDocumentNo " + collectionDocumentNo);

		DetachedCriteria criteria = DetachedCriteria.forClass(CollectDetail.class, "collectDetail");
		criteria.add(Restrictions.eq("collectDetail.documentCode", collectionDocId));
		criteria.add(Restrictions.eq("collectDetail.documentFinanceYear", collectionDocFinanceYear));
		criteria.add(Restrictions.eq("collectDetail.documentNo", collectionDocumentNo));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CollectDetail> collectDetail = (List<CollectDetail>) findAll(criteria);
		LOGGER.info("Entering into getRemittanceTransactionDetailsfromView method");
		if (collectDetail != null && !collectDetail.isEmpty()) {
			return collectDetail;
		}else{
			return   null;
		}

	}

	@Override
	public BankMaster getBankMasterDetails(BigDecimal bankId) {
		LOGGER.info( "bank============="+bankId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");
		dcriteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		List<BankMaster> listbranch = ( List<BankMaster> )findAll(dcriteria);
		if (listbranch.size() > 0) {
			return listbranch.get(0);
		} else {
			return  null;
		}

	}

	@Override
	public BankBranch getBankBranchDetails(BigDecimal branchId) {
		LOGGER.info( "branchId============="+branchId);
		DetachedCriteria dcriteria = DetachedCriteria.forClass(BankBranch.class, "bankBranch");
		dcriteria.setFetchMode("bankBranch.exBankMaster", FetchMode.JOIN);
		dcriteria.createAlias("bankBranch.exBankMaster", "exBankMaster",JoinType.INNER_JOIN);

		dcriteria.add(Restrictions.eq("bankBranch.bankBranchId", branchId));
		dcriteria.addOrder(Order.asc("bankBranch.bankBranchId"));
		dcriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankBranch>  branchList =  (List<BankBranch>)findAll(dcriteria);
		return branchList.get(0);
	}

	@Override
	public List<RemittanceComplaint> getRemitaComplaintDetails(
			BigDecimal documentYear, BigDecimal docmentNo,
			BigDecimal documentCode, BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		if(documentYear!=null){
			criteria.add(Restrictions.eq("remittanceComplaint.documentFinanceYear", documentYear));
		}
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", docmentNo));
		criteria.add(Restrictions.eq("remittanceComplaint.documentCode", documentCode));
		criteria.add(Restrictions.eq("remittanceComplaint.companyId", companyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceComplaint>) findAll(criteria);

	}

	@Override
	public List<RemittanceApplication> getRemittanceApplication(
			BigDecimal documentYear, BigDecimal docmentNo,
			BigDecimal documentCode, BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");

		criteria.add(Restrictions.eq("remittanceApplication.transactionFinancialyear", documentYear));
		criteria.add(Restrictions.eq("remittanceApplication.transactionDocumentNo", docmentNo));
		//criteria.add(Restrictions.eq("remittanceApplication.documentCode", documentCode));
		criteria.add(Restrictions.eq("remittanceApplication.fsCompanyMaster.companyId", companyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<RemittanceApplication>) findAll(criteria);

	}

	@Override
	public RemittanceTxnView getRemittanceTransactionDetailsfromViewData(
			BigDecimal transferNo, BigDecimal docYear, String documentCode,BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTxnView.class, "remittanceTxnView");
		criteria.add(Restrictions.eq("remittanceTxnView.documentNo", transferNo));

		criteria.add(Restrictions.eq("remittanceTxnView.documentCode", new BigDecimal(documentCode ) ));
		if(docYear!=null){
			criteria.add(Restrictions.eq("remittanceTxnView.documentFinancialYear", docYear));
		}
		criteria.add(Restrictions.eq("remittanceTxnView.companyId", companyId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTxnView> remittanceTxnViewList = (List<RemittanceTxnView>) findAll(criteria);

		if(remittanceTxnViewList.size()>0){ 
			return remittanceTxnViewList.get( 0) ;
		}else{
			return null;
		}
	}

	@Override
	public ViewRemiitanceInfo getRemittanceTrnxDetailsFromView(
			BigDecimal transferNo, BigDecimal docYear, String documentCode,
			BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemiitanceInfo.class, "viewRemiitanceInfo");
		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentNo", transferNo));

		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentCode", new BigDecimal(documentCode ) ));
		if(docYear!=null){
			criteria.add(Restrictions.eq("viewRemiitanceInfo.documentFinYear", docYear));
		}
		criteria.add(Restrictions.eq("viewRemiitanceInfo.companyId", companyId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceViewListInfo = (List<ViewRemiitanceInfo>) findAll(criteria);

		if(remittanceViewListInfo.size()>0){ 
			return remittanceViewListInfo.get( 0) ;
		}else{
			return null;
		}

	}

	@Override
	public Clob getSignatureOfRemitter(BigDecimal transferNo,
			BigDecimal docYear, String documentCode, BigDecimal companyId) {
		Clob Signature= null;
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		criteria.setFetchMode("remittanceTransaction.documentId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.documentId", "documentId", JoinType.INNER_JOIN);
		criteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.companyId", "companyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("remittanceTransaction.documentNo", transferNo));

		criteria.add(Restrictions.eq("documentId.documentID", new BigDecimal(documentCode ) ));
		if(docYear!=null){
			criteria.add(Restrictions.eq("remittanceTransaction.documentFinanceYear", docYear));
		}
		criteria.add(Restrictions.eq("companyId.companyId", companyId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTransaction> remittanceViewListInfo = (List<RemittanceTransaction>) findAll(criteria);

		if(remittanceViewListInfo.size()>0){
			Signature=remittanceViewListInfo.get( 0).getCustomerSignatureClob();
		}
		return Signature;

	}

	@Override
	public PurposeOfRemittanceView getPurposeOfRemittanceView(
			BigDecimal docNumber, BigDecimal docYear,
			BigDecimal documentCode, BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(PurposeOfRemittanceView.class, "purposeOfRemittanceView");
		criteria.add(Restrictions.eq("purposeOfRemittanceView.documentNumber", docNumber));

		criteria.add(Restrictions.eq("purposeOfRemittanceView.documentId", documentCode));
		if(docYear!=null){
			criteria.add(Restrictions.eq("purposeOfRemittanceView.documentFinancialYear", docYear));
		}
		criteria.add(Restrictions.eq("purposeOfRemittanceView.companyId", companyId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<PurposeOfRemittanceView> purposeOfRemittanceView = (List<PurposeOfRemittanceView>) findAll(criteria);

		if(purposeOfRemittanceView.size()>0){ 
			return purposeOfRemittanceView.get( 0) ;
		}else{
			return null;
		}
	}

	@Override
	public BigDecimal toFetchFinancialYear() {
		BigDecimal currfinanceYear=null;
		int year = Calendar.getInstance().get(Calendar.YEAR);
		String hql="select DISTINCT receiptPayment.documentFinanceYear from ReceiptPayment receiptPayment where receiptPayment.documentFinanceYear=:finYear";
		Query query = getSession().createQuery(hql);
		query.setParameter("finYear", new BigDecimal(year));
		List<BigDecimal> lReceiptPayments = query.list();
		if(lReceiptPayments.size()>0){
			currfinanceYear=lReceiptPayments.get(0);
		}
		return currfinanceYear;
	}

	@Override
	public List<ReceiptPayment> toFetchAllDocumentNo(BigDecimal documentCode,BigDecimal remittanceYear) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		criteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		criteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", remittanceYear));
		criteria.add(Restrictions.isNotNull("receiptPayment.documentNo"));
		criteria.add(Restrictions.eq("receiptPayment.documentStatus", Constants.U));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		List<ReceiptPayment> lReceiptPayment = (List<ReceiptPayment>) findAll(criteria);
		return lReceiptPayment;
	}

	@Override
	public List<ReceiptPayment> toFetchAllRecordBasedOnDocYearAndDocNum(BigDecimal companyId, BigDecimal documentCode, BigDecimal docFinYear,BigDecimal documentNum) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		criteria.setFetchMode("receiptPayment.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("receiptPayment.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		criteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		criteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", docFinYear));
		criteria.add(Restrictions.eq("receiptPayment.documentNo", documentNum));
		criteria.add(Restrictions.eq("receiptPayment.documentStatus", Constants.U));
		criteria.add(Restrictions.isNull("receiptPayment.receiptType"));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		List<ReceiptPayment> lReceiptPayment = (List<ReceiptPayment>) findAll(criteria);
		return lReceiptPayment;
	}

	@Override
	public String toFetchCountryBranchName(BigDecimal countryId,BigDecimal countryBranchId) {
		String branchName=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", countryBranchId));
		criteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> lCountryBranch = (List<CountryBranch>) findAll(criteria);
		if(lCountryBranch.size()>0){
			branchName=lCountryBranch.get(0).getBranchName();
		}
		return branchName;
	}

	@Override
	public List<RemittanceView> toFetchRecordsFromView(BigDecimal companyId,BigDecimal documentCode, BigDecimal transferFinanceYear,BigDecimal transferReference) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceView.class, "remittanceView");
		criteria.add(Restrictions.eq("remittanceView.companyId", companyId));
		criteria.add(Restrictions.eq("remittanceView.documentCode", documentCode));
		criteria.add(Restrictions.eq("remittanceView.applicationFinYear", transferFinanceYear));
		criteria.add(Restrictions.eq("remittanceView.applicationDocumentNo", transferReference));
		criteria.setResultTransformer(criteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceView> lRemittanceView = (List<RemittanceView>) findAll(criteria);
		return lRemittanceView;
	}


	@Override
	public String moveToOldSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_STOP_PAYMENT (?, ?, ?, ?, ?, ?, ?, ? ,?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);
			cs.setBigDecimal(6, oldRemitComId);
			cs.setBigDecimal(7, oldRemitDocCode);			
			cs.setBigDecimal(8, oldRemitDocFinyr);
			cs.setBigDecimal(9, oldRemitDocNo);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_STOP_PAYMENT" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_STOP_PAYMENT" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	public List<Collect> getCollectionListById(BigDecimal collectionId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Collect.class, "collect");
		criteria.add(Restrictions.eq("collectionId", collectionId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Collect> objectList = (List<Collect>) findAll(criteria);

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}

	public List<RemittanceTransaction> getRemitTxnDetailsById(BigDecimal transactionId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");

		criteria.setFetchMode("remittanceTransaction.companyId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.companyId", "companyId",JoinType.INNER_JOIN);

		criteria.setFetchMode("remittanceTransaction.documentId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.documentId", "documentId",JoinType.INNER_JOIN);		

		criteria.add(Restrictions.eq("remittanceTransactionId", transactionId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RemittanceTransaction> objectList = (List<RemittanceTransaction>) findAll(criteria);

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}

	@Override
	public String moveToOldSystemRefundRequest(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_REFUND_REQ (?, ?, ?, ?, ?, ?, ?, ? ,?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);
			cs.setBigDecimal(6, oldRemitComId);
			cs.setBigDecimal(7, oldRemitDocCode);			
			cs.setBigDecimal(8, oldRemitDocFinyr);
			cs.setBigDecimal(9, oldRemitDocNo);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_REFUND_REQ" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_REFUND_REQ" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public String moveToOldSystemRefundPay(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, 
			 BigDecimal documentNo, BigDecimal oldRemitComId,BigDecimal oldRemitDocCode,BigDecimal oldRemitDocFinyr,BigDecimal oldRemitDocNo) {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			LOGGER.info("CALLING EX_P_POPULATE_REFUND_PAY DB PROCEDURE ");
			LOGGER.info("Document ==> "+ documentCode);
			LOGGER.info("Document Finance Year ==>"+documentFinanceYr);
			LOGGER.info("Document Number ==>"+documentNo);
			LOGGER.info("oldRemitComId ==>"+ oldRemitComId);
			LOGGER.info("oldRemitDocCode ===>"+ oldRemitDocCode);
			LOGGER.info("oldRemitDocFinyr ===>"+ oldRemitDocFinyr);
			LOGGER.info("oldRemitDocNo ===>"+ oldRemitDocNo);
			String call = " { call EX_P_POPULATE_REFUND_PAY (?, ?, ?, ?, ?, ?, ?, ? ,?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);
			cs.setBigDecimal(6, oldRemitComId);
			cs.setBigDecimal(7, oldRemitDocCode);			
			cs.setBigDecimal(8, oldRemitDocFinyr);
			cs.setBigDecimal(9, oldRemitDocNo);
			cs.registerOutParameter(10, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_REFUND_PAY" + " : " + e.getMessage();
			LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_REFUND_PAY" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return erromsg;
	}

	@Override
	public List<RemittanceTransaction> getRemitTxnDetailsFromViewById(BigDecimal transactionId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");

		criteria.add(Restrictions.eq("remittanceTransactionId", transactionId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<RemittanceTransaction> objectList = (List<RemittanceTransaction>) findAll(criteria);

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}

		return null;
	}

	@Override
	public List<UserFinancialYear> getTransferYearList(){
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		Calendar now = Calendar.getInstance();
		BigDecimal curryr = new BigDecimal(now.get(Calendar.YEAR));
		BigDecimal  prevYear = new BigDecimal(now.get(Calendar.YEAR)-5);

		//Criterion criteria1=Restrictions.eq("userFinancialYear.financialYear" , curryr);
		//Criterion criteria2=Restrictions.eq("userFinancialYear.financialYear",prevYear);
		//LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		//detachedCriteria.add( orExp );
		detachedCriteria.add(Restrictions.le("userFinancialYear.financialYear", curryr))
		.add(Restrictions.ge("userFinancialYear.financialYear", prevYear));
		detachedCriteria.addOrder(Order.desc("userFinancialYear.financialYear"));
		List<UserFinancialYear> objectList=(List<UserFinancialYear>)findAll(detachedCriteria);

		if(objectList.size()>0){
			return objectList ;
		}else{
			return objectList;
		}
	}

	@Override
	public void updateRemittanceComplaint(String stopPaymentStatus, BigDecimal complaintId,String userName){

		RemittanceComplaint remittanceComplaint=(RemittanceComplaint) getSession().get(RemittanceComplaint.class, complaintId);
		remittanceComplaint.setBankStatus(stopPaymentStatus);
		remittanceComplaint.setBankStatusUser(userName);
		remittanceComplaint.setBankStatusDate(new Date());	
		getSession().update(remittanceComplaint); 


	}

	/*@Override
	public RemittanceComplaint viewRemittanceComplaint(BigDecimal documentNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "remittanceComplaint");
		criteria.add(Restrictions.eq("remittanceComplaint.documentNo", documentNo));
	//	criteria.add(Restrictions.eq("remittanceComplaint.documentFinanceYear", new BigDecimal(financialYear)));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> remitCompliant = (List<RemittanceComplaint>) findAll(criteria);
		if (null != remitCompliant) {
			if (!remitCompliant.isEmpty()) {
				return remitCompliant.get(0);
			}
		}
		return null;
	}*/

	@Override
	public ViewRemiitanceInfo getStopPaymentTrnxDetailsFromView(BigDecimal transferNo,BigDecimal dealYear,BigDecimal companyId,BigDecimal documentId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemiitanceInfo.class, "viewRemiitanceInfo");
		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentNo", transferNo));
		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentFinYear", dealYear));
		criteria.add(Restrictions.eq("viewRemiitanceInfo.companyId", companyId));
		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentId", documentId));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemiitanceInfo> remittanceViewListInfo = (List<ViewRemiitanceInfo>) findAll(criteria);

		if(remittanceViewListInfo.size()>0){ 
			return remittanceViewListInfo.get(0) ;
		}else{
			return null;
		}

	}

	@Override
	public String moveToOldSystemStopPay(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) throws AMGException{	

		Connection connection = null;
		String erromsg = null;
		HashMap<String, String> outputValues = new HashMap<String, String>();
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_STOP_FEEDBACK(?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, companyCode);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentFinanceYr);
			cs.setBigDecimal(4, documentNo);
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);	


			cs.executeUpdate();// teUpdate();

			erromsg = cs.getString(5);
			// outputValues.put("P_ERROR_MESSAGE", erromsg);


			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_STOP_FEEDBACK" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_STOP_FEEDBACK" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return erromsg;
	}
	@Override
	public CompanyMaster getCompanyCode(BigDecimal companyId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");

		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CompanyMaster> compList = (List<CompanyMaster>) findAll(criteria);

		if (null != compList) {
			if (!compList.isEmpty()) {
				return compList.get(0);
			}
		}
		return null;

	}

	public Document getDocumentId(BigDecimal documentCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");

		criteria.add(Restrictions.eq("document.documentCode", documentCode));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> docList = (List<Document>) findAll(criteria);

		if (null != docList) {
			if (!docList.isEmpty()) {
				return docList.get(0);
			}
		}
		return null;
	}

	@Override
	public void updateRemitComplaint(BigDecimal cancelDocumentYear,	BigDecimal cancelDocumentNo, BigDecimal remitComplaintId) {
		SessionStateManage sessionStateManage = new SessionStateManage();
		RemittanceComplaint remitComplaint = (RemittanceComplaint) getSession().get(RemittanceComplaint.class, remitComplaintId);
		remitComplaint.setCancelDocumentDate(new Date());
		remitComplaint.setCancelDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_REFUND_REQUEST));
		remitComplaint.setCancelDocumentFinanceYear(cancelDocumentYear);
		remitComplaint.setCancelDocumentNumber(cancelDocumentNo);
		remitComplaint.setCancellationStatus(Constants.U);
		remitComplaint.setModifiedBy(sessionStateManage.getUserName());
		remitComplaint.setModifiedDate(new Date());
		getSession().update(remitComplaint);

	}

	@Override
	public List<RefundInquiryView> fetchRefundApprovedRec(BigDecimal companyId,
			BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RefundInquiryView.class, "refundInquiryView");
		criteria.add(Restrictions.eq("refundInquiryView.companyId", companyId));
		criteria.add(Restrictions.eq("refundInquiryView.documentCode", documentCode));
		criteria.add(Restrictions.eq("refundInquiryView.documentYear", documentFinanceYr));
		criteria.add(Restrictions.eq("refundInquiryView.documentNo", documentNo));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RefundInquiryView> refundInquiryView = (List<RefundInquiryView>) findAll(criteria);

		return refundInquiryView;
	}

	@Override
	public boolean checkTransferForStopPayment(BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal documentYear,
			BigDecimal documentNo) {
		boolean checkRec = false;
		String stopStatus = null;

		/*SELECT stopsts
		FROM   V_TRANSFER
		WHERE  COMCOD=20
		AND    DOCCOD=3
		AND    DOCFYR=2016
		AND    DOCNO=6089280   --- 6089280*/

		String hql1 = "Select stopsts from V_TRANSFER where COMCOD = :companyCode and DOCCOD = :documentCode and DOCFYR = :documentYear and DOCNO = :documentNo";
		Query query1 = getSession().createSQLQuery(hql1);
		query1.setParameter("companyCode", companyCode);
		query1.setParameter("documentCode", documentCode);
		query1.setParameter("documentYear", documentYear);
		query1.setParameter("documentNo", documentNo);
		List<Object> object = query1.list();
		if(object.size() != 0){
			Object status = object.get(0);
			System.out.println("StopPayment Status"+status);
			stopStatus = (String) status;

			if(stopStatus != null && stopStatus.equalsIgnoreCase(Constants.S)){
				checkRec = true;
			}
		}


		return checkRec;
	}

	@Override
	public HashMap<String, Object> fetchTransferForStopPayment(BigDecimal companyCode,
			BigDecimal documentCode, BigDecimal documentYear,
			BigDecimal documentNo, String formType) throws AMGException {
		String errMsg = null;

		HashMap<String, Object> outputValues = new HashMap<String, Object>();
		Connection connection = null;
		//RemittanceTxnOLDView remitTrnxOld = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call INSERT_JAVA_TRANSACTIONS (?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, companyCode);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentYear);
			cs.setBigDecimal(4, documentNo);
			cs.setString(5, formType);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);	

			cs.executeUpdate();// teUpdate();

			errMsg = cs.getString(6);

			outputValues.put("ERRMSG", errMsg);

			/*if(errMsg != null && !errMsg.equalsIgnoreCase("")){
				outputValues.put("ERRMSG", errMsg);
				outputValues.put("TRANSFER", null);
			}else{
				outputValues.put("ERRMSG", errMsg);

				LOGGER.info("Entering into fetchTransferForStopPayment method");
				LOGGER.info("transferNo " + documentNo);
				LOGGER.info("countryId " + sessionStateManage.getCountryId());
				LOGGER.info("fyear " + documentYear);
				LOGGER.info("companyId " + sessionStateManage.getCompanyId());

				DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTxnOLDView.class, "remittanceTxnOLDView");
				criteria.add(Restrictions.eq("remittanceTxnOLDView.documentNumber", documentNo));
				criteria.add(Restrictions.eq("remittanceTxnOLDView.applicationCountryId", sessionStateManage.getCountryId()));
				criteria.add(Restrictions.eq("remittanceTxnOLDView.documentCode", documentCode));
				criteria.add(Restrictions.eq("remittanceTxnOLDView.documentFinanceYear", documentYear));
				criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				List<RemittanceTxnOLDView> remittanceTxnOLDViewList = (List<RemittanceTxnOLDView>) findAll(criteria);
				LOGGER.info("Entering into getRemittanceTransactionDetailsfromView method");
				if (remittanceTxnOLDViewList != null && !remittanceTxnOLDViewList.isEmpty()) {
					remitTrnxOld = remittanceTxnOLDViewList.get(0);
				}
				outputValues.put("TRANSFER", remitTrnxOld);
			}*/

			System.out.println("ERROR MSG : " + errMsg);
		} catch (SQLException e) {
			errMsg = "INSERT_JAVA_TRANSACTIONS" + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				errMsg = "INSERT_JAVA_TRANSACTIONS" + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

		return outputValues;
	}

	@Override
	public RemittanceTrnxViewStopMiscModel getRemitTrnxFromView(
			BigDecimal transferNo, BigDecimal dealYear, BigDecimal companyId,
			BigDecimal documentId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTrnxViewStopMiscModel.class, "remittanceTrnxViewStopMiscModel");
		criteria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentNo", transferNo));
		criteria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentFinYear", dealYear));
		criteria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.companyId", companyId));
		criteria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentId", documentId));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceTrnxViewStopMiscModel> remittanceTrnxViewlst = (List<RemittanceTrnxViewStopMiscModel>) findAll(criteria);

		if(remittanceTrnxViewlst.size()>0){ 
			return remittanceTrnxViewlst.get(0) ;
		}else{
			return null;
		}

	}
	/**
	 * Added by Rabil for update Remit Feedback
	 */

	@Override
	public String updateRemitFeedBackProcedure(HashMap<String,Object> inputValues) throws AMGException{
		String errMsg = null;
		System.out.println("inputValues MSG : " + inputValues.values()+"\n VAlue  :"+inputValues);
		Connection connection = null;



		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_POPULATE_REMIT_FEEDBACK (?,?,?,?,?,?,?,?,?,?,?,?) }";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, (BigDecimal)inputValues.get("P_APPL_CNTY_ID"));
			cs.setBigDecimal(2, (BigDecimal)inputValues.get("P_COMPANY_ID"));
			cs.setBigDecimal(3, (BigDecimal)inputValues.get("P_DOCUMENT_CODE"));
			cs.setBigDecimal(4, (BigDecimal)inputValues.get("P_DOC_FINYR"));
			cs.setBigDecimal(5, (BigDecimal)inputValues.get("P_DOCUMENT_NO"));
			cs.setString(6, inputValues.get("P_REMIT_STAUS")==null?null:inputValues.get("P_REMIT_STAUS").toString());
			cs.setString(7, inputValues.get("P_BANK_REF")==null?null:inputValues.get("P_BANK_REF").toString());
			cs.setString(8, inputValues.get("P_PAID_DATE")==null?null:inputValues.get("P_PAID_DATE").toString());
			cs.setString(9, inputValues.get("P_RETURN_DATE")==null?null:inputValues.get("P_RETURN_DATE").toString());
			cs.setString(10, inputValues.get("P_REMARKS")==null?null:inputValues.get("P_REMARKS").toString());
			cs.setString(11, inputValues.get("USER_NAME")==null?null:inputValues.get("USER_NAME").toString());
			cs.registerOutParameter(12, java.sql.Types.VARCHAR);	

			cs.executeUpdate();// teUpdate();

			errMsg = cs.getString(12);

			System.out.println("ERROR MSG : " + errMsg);
		} catch (Exception e) {
			errMsg = "REMIT FEEDBACK UPDATION" + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				errMsg = "REMIT FEEDBACK UPDATION" + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

		return errMsg;
	}

	@Override
	public HashMap<String,String> validateRemitFeedBackProcedure(HashMap<String, Object> inputValues) throws AMGException {
		String errMsg = null,indictor =null;
		System.out.println("inputValues MSG : " + inputValues.values()+"\n VAlue  :"+inputValues);
		Connection connection = null;

		HashMap<String,String> outPutValues = new HashMap<String,String>();



		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_VALIDATE_REMIT_FEEDBACK (?, ?, ?, ?, ?) }";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, (BigDecimal)inputValues.get("P_REMIT_COMP_ID"));
			cs.setBigDecimal(2, (BigDecimal)inputValues.get("P_REMIT_DOC_ID"));
			cs.setBigDecimal(3, (BigDecimal)inputValues.get("P_REMIT_DOCFYR"));
			cs.setBigDecimal(4, (BigDecimal)inputValues.get("P_REMIT_DOCNO"));
			cs.registerOutParameter(5, java.sql.Types.VARCHAR);	
			//cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();

			errMsg = cs.getString(5);
			outPutValues.put("P_ERROR", errMsg);

			System.out.println("ERROR MSG : " + outPutValues);
		} catch (Exception e) {
			errMsg = "REMIT FEEDBACK UPDATION" + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (Exception e) {
				errMsg = "REMIT FEEDBACK UPDATION" + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

		return outPutValues;
	}

	@Override
	public String moveToOldSystemStopPayStatusModification(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo, String canStatus, String loginUSer) throws AMGException {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_STOP_STATUS_MODI(?, ?, ?, ?, ?,?,?) } ";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, companyCode);
			cs.setBigDecimal(2, documentCode);
			cs.setBigDecimal(3, documentFinanceYr);
			cs.setBigDecimal(4, documentNo);
			cs.setString(5, canStatus);
			cs.setString(6, loginUSer);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);	


			cs.executeUpdate();// teUpdate();

			erromsg = cs.getString(7);
			// outputValues.put("P_ERROR_MESSAGE", erromsg);


			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_STOP_STATUS_MODI" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_STOP_STATUS_MODI" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		}
		return erromsg;
	}

	@Override
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsFromView(
			BigDecimal transferNo, BigDecimal docYear, String documentCode,
			BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemittanceInquiryTransaction.class, "viewRemiitanceInfo");
		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentNo", transferNo));

		criteria.add(Restrictions.eq("viewRemiitanceInfo.documentCode", new BigDecimal(documentCode ) ));
		if(docYear!=null){
			criteria.add(Restrictions.eq("viewRemiitanceInfo.documentFinYear", docYear));
		}
		criteria.add(Restrictions.eq("viewRemiitanceInfo.companyId", companyId));
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemittanceInquiryTransaction> remittanceViewListInfo = (List<ViewRemittanceInquiryTransaction>) findAll(criteria);

		if(remittanceViewListInfo.size()>0){ 
			return remittanceViewListInfo.get( 0) ;
		}else{
			return null;
		}
	}
	
	@Override
	public ViewRemittanceInquiryTransaction getRemittanceTrnxInqDetailsMTcNoView(String mtcNo,BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemittanceInquiryTransaction.class, "viewRemiitanceInfo");
		
		criteria.add(Restrictions.eq("viewRemiitanceInfo.westernUnionMTCNo", mtcNo));		
		criteria.add(Restrictions.eq("viewRemiitanceInfo.companyId", companyId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewRemittanceInquiryTransaction> remittanceViewListInfo = (List<ViewRemittanceInquiryTransaction>) findAll(criteria);

		if(remittanceViewListInfo.size()>0){ 
			return remittanceViewListInfo.get( 0) ;
		}else{
			return null;
		}
	}
	
	@Override
	public List<Employee> getBranchUserList(){
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Employee.class,"employee");
		
		dCriteria.setFetchMode("employee.fsCountryBranch", FetchMode.JOIN);
		dCriteria.createAlias("employee.fsCountryBranch", "fsCountryBranch",JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.isNotNull("employee.wuForeignTerminalId"));
		dCriteria.addOrder(Order.asc("employee.employeeId"));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<Employee>)findAll(dCriteria);	
	}
	
	@Override
	public List<ViewRemittanceInquiryTransaction> getWesternUnionList(BigDecimal locnId,String branchUser,String mtcnNum,Date fromDt,Date toDt){
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewRemittanceInquiryTransaction.class, "viewRemiitanceInfo");
		
		if(locnId!=null){
			criteria.add(Restrictions.eq("viewRemiitanceInfo.branchId", locnId));
		}		
		if(branchUser!=null){
			criteria.add(Restrictions.eq("viewRemiitanceInfo.createdBy", branchUser));
		}		
		if(mtcnNum!=null){
			criteria.add(Restrictions.eq("viewRemiitanceInfo.westernUnionMTCNo", mtcnNum));
		}		
		criteria.add(Restrictions.between("viewRemiitanceInfo.createdDate", fromDt, toDt));		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<ViewRemittanceInquiryTransaction>)findAll(criteria);		
	}
	
	
	@Override
	public BigDecimal getBuyRateMin(BigDecimal appCountryId,BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal bankId,BigDecimal serviceIndicatorId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ExchangeRateApprovalDetModel.class, "exchangeRateApprovalDetModel");
		
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.applicationCountryId",appCountryId));
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryId",countryId));
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.currencyId",currencyId));
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.countryBranchId",countryBranchId));
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.bankId",bankId));
		criteria.add(Restrictions.eq("exchangeRateApprovalDetModel.serviceId",serviceIndicatorId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ExchangeRateApprovalDetModel> exchangeRateApprovalDetModel = (List<ExchangeRateApprovalDetModel>) findAll(criteria);
		if (exchangeRateApprovalDetModel!=null && exchangeRateApprovalDetModel.size() > 0) {
			//return exchangeRateApprovalDetModel.get(0).getBuyRateMin();
			return exchangeRateApprovalDetModel.get(0).getBuyRateMax();
		}
		return null;
	}
	
	@Override
	public BigDecimal getServiceMasterId(BigDecimal remittanceModeId){
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceModeMaster.class, "remittanceModeMaster");
		
		criteria.setFetchMode("remittanceModeMaster.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("remittanceModeMaster.serviceMaster", "serviceMaster",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("remittanceModeMaster.remittanceModeId",remittanceModeId));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<RemittanceModeMaster> remittanceModeMaster = (List<RemittanceModeMaster>) findAll(criteria);
		if (remittanceModeMaster!=null && remittanceModeMaster.size() > 0) {
			return remittanceModeMaster.get(0).getServiceMaster().getServiceId();
		}
		return null;
	}
}
