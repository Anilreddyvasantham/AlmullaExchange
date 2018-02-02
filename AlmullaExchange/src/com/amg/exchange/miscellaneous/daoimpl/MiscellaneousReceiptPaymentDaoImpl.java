package com.amg.exchange.miscellaneous.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.cancelreissue.model.RemittanceTrnxViewStopMiscModel;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.ReceiptPaymentApp;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.model.ViewReceiptPayment;
import com.amg.exchange.miscellaneous.dao.IMiscellaneousReceiptPaymentDao;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.model.PaymentDetail;
import com.amg.exchange.miscellaneous.model.ViewVwRemittanceTransaction;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerInfoView;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"serial",  "unchecked" })
@Repository
public class MiscellaneousReceiptPaymentDaoImpl<T> extends CommonDaoImpl<T> implements IMiscellaneousReceiptPaymentDao<T>, Serializable {

	@Override
	public List<ViewVwRemittanceTransaction> fetchTransactionalDetails(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal remittanceYear,
			BigDecimal remiitanceNo) {
       DetachedCriteria critiria = DetachedCriteria.forClass(ViewVwRemittanceTransaction.class,"viewVwRemittanceTransaction");
		
		critiria.add(Restrictions.eq("viewVwRemittanceTransaction.applicationCountryId", appCountryId));
		critiria.add(Restrictions.eq("viewVwRemittanceTransaction.companyId", companyId));
		critiria.add(Restrictions.eq("viewVwRemittanceTransaction.documentCode", documentCode));
		critiria.add(Restrictions.eq("viewVwRemittanceTransaction.documentFinYear", remittanceYear));
		critiria.add(Restrictions.eq("viewVwRemittanceTransaction.documentNo", remiitanceNo));
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ViewVwRemittanceTransaction> remittanceList = (List<ViewVwRemittanceTransaction>) findAll(critiria);
		return remittanceList;
	}

	@Override
	public List<ReceiptPayment> fetchReceiptPayment(BigDecimal appCountryId,BigDecimal companyId,BigDecimal documentYear,BigDecimal documentNo,String documentCode) {

		BigDecimal misDocCode = new BigDecimal(documentCode);

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");

		dCriteria.setFetchMode("receiptPayment.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));

		dCriteria.setFetchMode("receiptPayment.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCompanyMaster","fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		dCriteria.add(Restrictions.ne("receiptPayment.isActive", Constants.D));

		if(misDocCode != null && misDocCode.compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))==0){  // doc code = 9 - Payment
			dCriteria.add(Restrictions.eq("receiptPayment.documentCode", misDocCode));
			dCriteria.add(Restrictions.eq("receiptPayment.receiptType", Constants.ReceiptType_FOR_PAYMENT));
		}else if(misDocCode != null && misDocCode.compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))==0){ // doc code = 2 - Receive
			dCriteria.add(Restrictions.eq("receiptPayment.documentCode", misDocCode));
			dCriteria.add(Restrictions.eq("receiptPayment.receiptType", Constants.ReceiptType_FOR_RECEIVE));
		}else{
			// both skipped
		}

		dCriteria.add(Restrictions.eq("receiptPayment.transferReference", documentNo));
		dCriteria.add(Restrictions.eq("receiptPayment.transferFinanceYear", documentYear));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);


		return (List<ReceiptPayment>) findAll(dCriteria); 
	}

	@Override
	public List<Customer> fetchCustomer(BigDecimal customerId) {

		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		dCriteria.add(Restrictions.eq("customer.customerId", customerId));
		dCriteria.add(Restrictions.eq("isActive", Constants.Yes));
		dCriteria.add(Restrictions.eq("activatedInd", Constants.Yes));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>) findAll(dCriteria);
	}

	@Override
	public List<ViewVwRemittanceTransaction> getAllTransactionList(BigDecimal customerReference) {
		   DetachedCriteria critiria = DetachedCriteria.forClass(ViewVwRemittanceTransaction.class,"viewVwRemittanceTransaction");
		   critiria.add(Restrictions.eq("viewVwRemittanceTransaction.customerReference", customerReference));
		   critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		   List<ViewVwRemittanceTransaction> remittanceList = (List<ViewVwRemittanceTransaction>) findAll(critiria);
		   return remittanceList;
	}

	@Override
	public List<ForeignCurrencyAdjust> getCollectionDetailsFromCurrencyAdjust(
			BigDecimal collectiondocId, BigDecimal collectDocYear,
			BigDecimal collectDocNo) {
		  DetachedCriteria critiria = DetachedCriteria.forClass(ForeignCurrencyAdjust.class,"foreignCurrencyAdjust");
		  critiria.add(Restrictions.eq("foreignCurrencyAdjust.documentCode", collectiondocId));
		  critiria.add(Restrictions.eq("foreignCurrencyAdjust.documentFinanceYear", collectDocYear));
		  critiria.add(Restrictions.eq("foreignCurrencyAdjust.documentNo", collectDocNo));
		  critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		   List<ForeignCurrencyAdjust> remittanceList = (List<ForeignCurrencyAdjust>) findAll(critiria);
		   return remittanceList;
	}

	@Override
	public  String getDenominationName(
			BigDecimal denominationId) {
		  DetachedCriteria critiria = DetachedCriteria.forClass(CurrencyWiseDenomination.class,"currencyWiseDenomination");
		  critiria.add(Restrictions.eq("currencyWiseDenomination.denominationId", denominationId));
		   List<CurrencyWiseDenomination> denominationList = (List<CurrencyWiseDenomination>) findAll(critiria);
		  if(denominationList.size()>0){
			  return denominationList.get( 0).getDenominationDesc();
		  }else{
			  return  null;
		  }
		 
	}

	@Override
	public void saveOrUpdate(ReceiptPayment receiptPaymentObj) {
		 
		getSession().saveOrUpdate(receiptPaymentObj );
		
	}

	@Override
	public BigDecimal getBankBranchIdBasedOnName(String branchName) {
	       DetachedCriteria critiria = DetachedCriteria.forClass( BankBranch.class,"bankBranch");
	     
	       critiria.add(Restrictions.eq("bankBranch.branchFullName", branchName));
			List<BankBranch> lstBranch=(List<BankBranch>)critiria.getExecutableCriteria(getSession()).list();
			if(lstBranch != null && lstBranch.size()!=0 ){
				return lstBranch.get(0).getBankBranchId();  
			}else{
				return null;
			}  
	       
	}

	@Override
	public BigDecimal getSourceIdBasedOnName(String sourceName,BigDecimal languageId) {

           DetachedCriteria critiria = DetachedCriteria.forClass( SourceOfIncomeDescription.class,"sourceOfIncomeDescription");
           critiria.setFetchMode("sourceOfIncomeDescription.languageId", FetchMode.JOIN);
           critiria.createAlias("sourceOfIncomeDescription.languageId", "languageId",JoinType.INNER_JOIN);
           critiria.add(Restrictions.eq( "sourceOfIncomeDescription.sourceName", sourceName));
 
	       
			List<SourceOfIncomeDescription> lstSource=(List<SourceOfIncomeDescription>)critiria.getExecutableCriteria(getSession()).list();
			
			if(lstSource != null && lstSource.size()!=0 ){
				return lstSource.get(0).getSourceOfIncomeId().getSourceId();
			}else{
				return null;
			}  
	}

	@Override
	public void saveOrUpdateApplReceiptPayment(ReceiptPaymentApp applRecPayObj) {
		getSession().saveOrUpdate(applRecPayObj );
		
	}

	@Override
	public void savePayment(Payment payment) {
		getSession().saveOrUpdate(payment);
		
	}

	@Override
	public void savePaymentDetails(PaymentDetail paymentDetails) {
	 getSession().saveOrUpdate(paymentDetails);
		
	}

	@Override
	public List<ViewRemiitanceInfo> fetchTransactionalDetail(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal remittanceYear,
			BigDecimal remiitanceNo) {
		
		DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceApplicationView");
			
			critiria.add(Restrictions.eq("remittanceApplicationView.applicationCountryId", appCountryId));
			critiria.add(Restrictions.eq("remittanceApplicationView.companyId", companyId));
			critiria.add(Restrictions.eq("remittanceApplicationView.documentCode", documentCode));
			critiria.add(Restrictions.eq("remittanceApplicationView.documentFinYear", remittanceYear));
			critiria.add(Restrictions.eq("remittanceApplicationView.documentNo", remiitanceNo));
			critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
			return remittanceList;
 
	}

	@Override
	public List<ViewRemiitanceInfo> getAllTransactionsList(
			BigDecimal customerReference) {
		  DetachedCriteria critiria = DetachedCriteria.forClass(ViewRemiitanceInfo.class,"remittanceApplicationView");
		   critiria.add(Restrictions.eq("remittanceApplicationView.customerRefNo", customerReference));
		   critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		   List<ViewRemiitanceInfo> remittanceList = (List<ViewRemiitanceInfo>) findAll(critiria);
		   return remittanceList;
 
	}
	
	@Override
	public List<ReceiptPayment> checkDocumentNumberExist(BigDecimal documentCode, BigDecimal documentNo){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		
		dCriteria.setFetchMode("receiptPayment.foreignFsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.foreignFsCountryMaster","foreignFsCountryMaster", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiptPayment.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<ReceiptPayment> remittanceList = (List<ReceiptPayment>) findAll(dCriteria);
		return remittanceList;
	}
	
	@Override
	public List<ViewVwRemittanceTransaction> checkTransactiontNumberExist(BigDecimal applicationCountry,BigDecimal companyId,BigDecimal documentCode,BigDecimal transactionYear,BigDecimal transactionNumber){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewVwRemittanceTransaction.class, "viewVwRemittanceTransaction");
		
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.applicationCountryId", applicationCountry)); 
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.companyId", companyId)); 
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.documentFinYear", transactionYear));
		dCriteria.add(Restrictions.eq("viewVwRemittanceTransaction.documentNo", transactionNumber));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<ViewVwRemittanceTransaction>) findAll(dCriteria);
	}

	@Override
	public List<Customer> getCustomerDetails(BigDecimal customerReference){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		
		dCriteria.add(Restrictions.eq("customer.customerReference", customerReference)); 
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>) findAll(dCriteria);
	}

	@Override
	public List<ReceiptPayment> fetchReceiptPaymentForUpdate(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		
		dCriteria.setFetchMode("receiptPayment.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));
		
		dCriteria.setFetchMode("receiptPayment.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCompanyMaster","fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		if(documentCode.compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PAYMENT))==0 )
		{
			
			dCriteria.add(Restrictions.eq("receiptPayment.receiptType", "99"));
			
		}else if(documentCode.compareTo(new BigDecimal(Constants.DOCUMENT_CODE_FOR_RECEIVE))==0 )
		{
			dCriteria.add(Restrictions.eq("receiptPayment.receiptType", "98"));
		}
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiptPayment.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 
	    
		return (List<ReceiptPayment>) findAll(dCriteria); 
	}

	@Override
	public List<ReceiptPayment> fetchReceiptPaymentUnApprovedRecords(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal DocumentYear) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		
		
		dCriteria.setFetchMode("receiptPayment.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", appCountryId));
		
		dCriteria.setFetchMode("receiptPayment.fsCompanyMaster", FetchMode.JOIN);
		dCriteria.createAlias("receiptPayment.fsCompanyMaster","fsCompanyMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U));
		dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", DocumentYear));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 
		return  (List<ReceiptPayment>) findAll(dCriteria);
	}

	@Override
	public String getTelephoneNumber(BigDecimal customerRef) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		dCriteria.add(Restrictions.eq("customer.customerReference", customerRef));
		   List<Customer>  custList = (List<Customer>) findAll(dCriteria);
		  String telNo= custList.get( 0).getMobile();
		   return telNo;
	}

	@Override
	public void updateRecord(BigDecimal recpayPk, BigDecimal comissionAmt,
			BigDecimal chargesAmt, BigDecimal deliverChargesAmt,
			BigDecimal rateAdjust, BigDecimal otherAdj, BigDecimal netAmt,String remarks) {
		ReceiptPayment receiptPayment=(ReceiptPayment) getSession().get(ReceiptPayment.class, recpayPk);
		
		   receiptPayment.setLocalChargeAmount(chargesAmt );
		   receiptPayment.setLocalCommisionAmoumnt(comissionAmt );
		   receiptPayment.setLocalNetAmount(netAmt );
		   receiptPayment.setLocalDeliveryAmount(deliverChargesAmt );
		   receiptPayment.setRemarks(remarks );
		   receiptPayment.setLocalRateAmount(rateAdjust );
		   receiptPayment.setLocalOtherAdjAmount( otherAdj);
		
			 
			getSession().update(receiptPayment);
		 
		
	}
	

	@Override
	public List<ReceiptPayment> getAllDocumentNumbers(BigDecimal documentCode,BigDecimal documentYear){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		
		if(documentYear!=null){
			dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear)); 
		}
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode)); 
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U)); 
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ReceiptPayment>) findAll(dCriteria);
	}
	
	
	public void saveRecords(HashMap<String, Object> saveRecords)throws AMGException{

		BigDecimal receiptPaymentPk = (BigDecimal)saveRecords.get("RECEIPTPAYMENTPK");
		BigDecimal documentCode = (BigDecimal)saveRecords.get("DOCUMENTCODE");
		String userName = (String)saveRecords.get("USERNAME");
		Collect collect =(Collect)saveRecords.get("COLLECT");
		List<CollectDetail> collectDetaillist = (List<CollectDetail>)saveRecords.get("COLLECTDETAILLIST");
		Payment payment = (Payment)saveRecords.get("PAYMENT");
		List<PaymentDetail> paymentDetaillist  = (List<PaymentDetail>) saveRecords.get("PAYMENTDETAILLIST");
		List<ForeignCurrencyAdjust> foreignCurrencyAdjusRefundtList = (List<ForeignCurrencyAdjust>)saveRecords.get("FOREIGNCURRENCYADJUSTREFUND");
		List<ForeignCurrencyAdjust> foreignCurrencyAdjusDenomtList = (List<ForeignCurrencyAdjust>)saveRecords.get("FOREIGNCURRENCYADJUSTDENOMINATION");

		//update Receipt Payment record
		if(receiptPaymentPk!=null && documentCode!=null && userName!=null){
			ReceiptPayment receipt=(ReceiptPayment) getSession().get(ReceiptPayment.class, receiptPaymentPk);
			// collection Details
			
			if(payment!=null){
				receipt.setCollectionMode(payment.getPaymentmode());
				receipt.setApprovalNo(payment.getApprovalNo());
			}
			
			receipt.setColDocCode(receipt.getDocumentCode());
			receipt.setColDocFyr(receipt.getDocumentFinanceYear());
			receipt.setColDocNo(receipt.getDocumentNo());			
			receipt.setDocumentCode(documentCode);
			receipt.setDocumentStatus("P");
			receipt.setModifiedBy(userName);
			receipt.setModifiedDate(new Date());
			receipt.setIsActive(Constants.Yes);

			getSession().update(receipt);
		}

		//save Collect table
		if(collect!=null){
			getSession().saveOrUpdate(collect);
		}

		//save Collect Detail table
		if(collectDetaillist!=null && collectDetaillist.size()>0 && collect!=null){
			for(CollectDetail collectdet:collectDetaillist){
				Collect collection = new Collect();
				collection.setCollectionId(collect.getCollectionId());
				collectdet.setCashCollectionId(collection);
				getSession().saveOrUpdate(collectdet);
			}
		}

		//save Payment Table
		if(payment!=null){
			getSession().saveOrUpdate(payment);
		}

		//save Payment Detail table
		if(paymentDetaillist!=null && paymentDetaillist.size()>0){
			for(PaymentDetail paymentDet:paymentDetaillist){
				getSession().saveOrUpdate(paymentDet);
			}
		}

		//save Foreign currency Adjust table for Refund 
		if(foreignCurrencyAdjusRefundtList!=null && foreignCurrencyAdjusRefundtList.size()>0){
			for(ForeignCurrencyAdjust foreignCurAdj:foreignCurrencyAdjusRefundtList){
				getSession().saveOrUpdate(foreignCurAdj);
			}
		}

		//save Foreign currency Adjust table for Denomination 
		if(foreignCurrencyAdjusDenomtList!=null && foreignCurrencyAdjusDenomtList.size()>0){
			for(ForeignCurrencyAdjust foreignCurAdj:foreignCurrencyAdjusDenomtList){
				getSession().saveOrUpdate(foreignCurAdj);
			}
		}

	}
	
	@Override
	public List<Object> moveToOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,BigDecimal oldRemitComId, BigDecimal oldRemitDocCode, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo)  {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_MISC_REQ (?, ?, ?, ?, ?, ?, ?, ? ,?, ?) } ";
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
			erromsg = "EX_P_POPULATE_MISC_REQ" + " : " + e.getMessage();
			//LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_MISC_REQ" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	
}
	
	@Override
	public List<Object> moveToApproveRecordFromCollectionOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo) throws AMGException {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_MISC_COLL (?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);			
			cs.setBigDecimal(6, oldRemitDocFinyr);
			cs.setBigDecimal(7, oldRemitDocNo);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e.getMessage();
			//LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			try {
				throw new AMGException(erromsg);
			} catch (AMGException e1) {
				erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e1.getMessage();
			}
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e.getMessage();
				try {
					throw new AMGException(erromsg);
				} catch (AMGException e1) {
					e1.printStackTrace();
				}
			}
		}
		return null;
	
}
	@Override
	public List<Object> moveToApproveRecordFromPaymentOldEmosSystem(BigDecimal applicationCountryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo, BigDecimal oldRemitDocFinyr, BigDecimal oldRemitDocNo) throws AMGException  {
		Connection connection = null;
		String erromsg = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_POPULATE_MISC_COLL_PAY (?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, applicationCountryId);
			cs.setBigDecimal(2, companyId);
			cs.setBigDecimal(3, documentCode);
			cs.setBigDecimal(4, documentFinanceYr);
			cs.setBigDecimal(5, documentNo);			
			cs.setBigDecimal(6, oldRemitDocFinyr);
			cs.setBigDecimal(7, oldRemitDocNo);
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.executeUpdate();// teUpdate();
			System.out.println("Success" + cs);
		} catch (SQLException e) {
			erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
			//LOGGER.info("Problem Occured  While Procedure calling  " + e.getMessage());
			
		}catch (Exception e1) {
			erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e1.getMessage();
			throw new AMGException(erromsg);
		}
		finally {
			try {
				connection.close();
			} catch (SQLException e) {
				erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e.getMessage();
					throw new AMGException(erromsg);
			}
		}
		return null;
	
}
	@Override
	public List<ReceiptPayment> getReceiptPaymentListById(BigDecimal receiptId){
		DetachedCriteria criteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		criteria.add(Restrictions.eq("receiptId", receiptId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ReceiptPayment> objectList = (List<ReceiptPayment>) findAll(criteria);
		
		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}
	@Override
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
		return objectList;
	}
	@Override
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
	
	@Override
	public List<Payment> getPaymentListById(BigDecimal paymentId,BigDecimal documentCode){
		DetachedCriteria criteria = DetachedCriteria.forClass(Payment.class, "payment");
		criteria.add(Restrictions.eq("paymentId", paymentId));
		criteria.add(Restrictions.eq("docCode", documentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<Payment> objectList = (List<Payment>) findAll(criteria);
		
		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}
	
	
	@Override
	public List<Document> getDocumentId(BigDecimal documentCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");
		criteria.add(Restrictions.eq("documentCode", documentCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> objectList = (List<Document>) findAll(criteria);

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}

	@Override
	public List<CompanyMaster> getCompanyCode(BigDecimal companyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMaster.class, "companyMaster");
		criteria.add(Restrictions.eq("companyId", companyId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CompanyMaster> objectList = (List<CompanyMaster>) findAll(criteria);

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
	}
	
	
	@Override
	public List<PaymentMode> fetchPaymentmode(BigDecimal paymentModeId) {
	
        DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
		dCriteria.add(Restrictions.eq("paymentMode.paymentModeId", paymentModeId));
	
        
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<PaymentMode> objectList = ((List<PaymentMode>)findAll(dCriteria));

		if (null != objectList) {
			if (!objectList.isEmpty()) {
				return objectList;
			}
		}
		return null;
		
	}
	
	@Override
	public List<BigDecimal> getAllDocumentFinanceYearNumbers(BigDecimal documentCode,BigDecimal documentYear){
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ReceiptPayment.class, "receiptPayment");
		
		if(documentYear!=null){
			dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear)); 
		}
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode)); 
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U));

		dCriteria.setProjection(Projections.distinct(Projections.property("receiptPayment.documentFinanceYear")));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<BigDecimal>) findAll(dCriteria);
	}
	
	
	@Override
	public List<ViewReceiptPayment> viewReceiptPaymentForUpdate(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewReceiptPayment.class, "receiptPayment");
		dCriteria.add(Restrictions.eq("receiptPayment.fsCountryMaster", appCountryId));
		
		dCriteria.add(Restrictions.eq("receiptPayment.fsCompanyMaster", companyId));
		
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiptPayment.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 
	    
		return (List<ViewReceiptPayment>) findAll(dCriteria); 
	}

	/*@Override
	public List<ViewReceiptPayment> getAllDocumentNumbersFromView(BigDecimal documentCode,BigDecimal documentYear){

		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewReceiptPayment.class, "receiptPayment");

		if(documentYear!=null){
			dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear)); 
		}

		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode)); 
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U)); 
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<ViewReceiptPayment>) findAll(dCriteria);
	}*/

	

	/*@Override
	public List<ViewReceiptPayment> getAllDocumentYearFromView(
			BigDecimal documentCode, BigDecimal documentYear) {
		List<ViewReceiptPayment> listMiscellaneousDto=new ArrayList<ViewReceiptPayment>();
		String hqlQuery="select distinct a.documentFinanceYear from  ViewReceiptPayment a where a.documentCode =  :pdocumentCode and a.isActive= :pIsActive";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("pdocumentCode", documentCode);
        query.setParameter("pIsActive", Constants.U);
        List<BigDecimal> lstUserFINYear =query.list();
        if(lstUserFINYear.size()>0){
        for (BigDecimal bigDecimal : lstUserFINYear) {
        	BigDecimal biDecimal=bigDecimal;
        	ViewReceiptPayment miscellaneousDto=new ViewReceiptPayment();
        	miscellaneousDto.setDocumentFinanceYear(biDecimal);
        	listMiscellaneousDto.add(miscellaneousDto);
		}
        }
		return listMiscellaneousDto;
	}*/

	@SuppressWarnings("finally")
	@Override
	public HashMap<String, String> moveToApproveRecordFromCollectionOldEmosSystem(HashMap<String, String> inputValues) throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			String call = " { call EX_P_POPULATE_MISC_COLL (?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("App_Country_Id")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("Company_Id")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("Doc_Code")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("Doc_Finan_year")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("Doc_No")));			
			if(!(inputValues.get("Old_Remit_Fin_Year").equals(""))&&!(inputValues.get("Old_Remit_Doc_No").equals(""))){
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("Old_Remit_Fin_Year")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("Old_Remit_Doc_No")));
			}else{
				cs.setBigDecimal(6, null);
				cs.setBigDecimal(7, null);
			}
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			
			outputValues.put("P_ERROR_MESSAGE", cs.getString(8) == null ? "" : cs.getString(8));
			
			System.out.println("!!!!!! createRemitAppProcedure outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			String erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_MISC_COLL" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		
	
	}
		return outputValues;
	}

	@Override
	public HashMap<String, String> moveToApproveRecordFromPaymentOldEmosSystem(HashMap<String, String> inputValues) throws AMGException {
		HashMap<String, String> outputValues = new HashMap<String, String>();
		Connection connection = null;
		try {
			connection = getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("!!!!!! EX_P_POPULATE_MISC_COLL_PAY input  VALUES  !!!!!!!!! ==  " + inputValues.toString());
		CallableStatement cs;
		try {
			String call = " { call EX_P_POPULATE_MISC_COLL_PAY (?, ?, ?, ?, ?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setBigDecimal(1, new BigDecimal(inputValues.get("App_Country_Id")));
			cs.setBigDecimal(2, new BigDecimal(inputValues.get("Company_Id")));
			cs.setBigDecimal(3, new BigDecimal(inputValues.get("Doc_Code")));
			cs.setBigDecimal(4, new BigDecimal(inputValues.get("Doc_Finan_year")));
			cs.setBigDecimal(5, new BigDecimal(inputValues.get("Doc_No")));	
			if(!(inputValues.get("Old_Remit_Fin_Year").equals(""))&&!(inputValues.get("Old_Remit_Doc_No").equals(""))){
			cs.setBigDecimal(6, new BigDecimal(inputValues.get("Old_Remit_Fin_Year")));
			cs.setBigDecimal(7, new BigDecimal(inputValues.get("Old_Remit_Doc_No")));
			}else{
				cs.setBigDecimal(6, null);
				cs.setBigDecimal(7, null);
			}
			cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			cs.executeUpdate();
			
			outputValues.put("P_ERROR_MESSAGE", cs.getString(8) == null ? "" : cs.getString(8));
			
			System.out.println("!!!!!! EX_P_POPULATE_MISC_COLL_PAY outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
		} catch (SQLException e) {
			String erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		} catch (Exception e) {
			String erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e.getMessage();
			throw new AMGException(erromsg);
		}finally {
			try {
				connection.close();
			} catch (SQLException e) {
				String erromsg = "EX_P_POPULATE_MISC_COLL_PAY" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}
		
	
	}
		return outputValues;
	}

	@Override
	public BigDecimal toFetchCompanyCode(BigDecimal companyId) {
		BigDecimal compCode=null;
		String hqlQuery="select distinct a.companyCode from  CompanyMaster a where a.companyId = :pcompanyId and a.isActive= :pIsActive";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("pcompanyId", companyId);
        query.setParameter("pIsActive", Constants.Yes);
        List<BigDecimal> lstCoMpCode =query.list();
        if (lstCoMpCode.size()>0) {
        	compCode=lstCoMpCode.get(0);
		}
		return compCode;
	}

	@Override
	public List<UserFinancialYear> getFinanacilYearId(BigDecimal year) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		dCriteria.add(Restrictions.eq("userFinancialYear.financialYear", year));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<UserFinancialYear>) findAll(dCriteria);
 
	}

	@Override
	public List<CustomerInfoView> getCustomerDetailsBasedOnRef(
			BigDecimal custRef) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerInfoView.class, "customerInfoView");
		dCriteria.add(Restrictions.eq("customerInfoView.customerReference", custRef)); 
  		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return (List<CustomerInfoView>) findAll(dCriteria);
	 
	}

	@Override
	public List<RemittanceTrnxViewStopMiscModel> fetchTransactionalDetailForMisc(
			BigDecimal appCountryId, BigDecimal companyId,
			BigDecimal documentCode, BigDecimal remittanceYear,
			BigDecimal remiitanceNo) {
		
		DetachedCriteria critiria = DetachedCriteria.forClass(RemittanceTrnxViewStopMiscModel.class,"remittanceTrnxViewStopMiscModel");
			
			critiria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.applicationCountryId", appCountryId));
			critiria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.companyId", companyId));
			critiria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentCode", documentCode));
			critiria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentFinYear", remittanceYear));
			critiria.add(Restrictions.eq("remittanceTrnxViewStopMiscModel.documentNo", remiitanceNo));
			critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List<RemittanceTrnxViewStopMiscModel> remittanceList = (List<RemittanceTrnxViewStopMiscModel>) findAll(critiria);
			return remittanceList;
 
	}

	@Override
	public List<CollectDetail> getBeanCorrespondingBankList(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,BigDecimal companyId){
		DetachedCriteria critiria = DetachedCriteria.forClass(CollectDetail.class,"collectDetail");
		
		critiria.setFetchMode("collectDetail.cashCollectionId", FetchMode.JOIN);
		critiria.createAlias("collectDetail.cashCollectionId", "cashCollectionId",JoinType.INNER_JOIN);
		
		critiria.setFetchMode("collectDetail.fsCompanyMaster", FetchMode.JOIN);
		critiria.createAlias("collectDetail.fsCompanyMaster", "fsCompanyMaster",JoinType.INNER_JOIN);
		
		critiria.add(Restrictions.eq("collectDetail.documentCode", docCode));
		critiria.add(Restrictions.eq("collectDetail.documentFinanceYear", docFinanceYear));
		critiria.add(Restrictions.eq("collectDetail.documentNo", docNo));
		critiria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		
		critiria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CollectDetail> lstCollectionDetail = (List<CollectDetail>) findAll(critiria);
		
		return lstCollectionDetail;
	}
	
	
	@Override
	public List<Payment> getPaymentDeatils(BigDecimal docNo,BigDecimal docFinanceYear,BigDecimal docCode,String recType,BigDecimal companyId){
		DetachedCriteria criteria = DetachedCriteria.forClass(Payment.class, "payment");
		criteria.add(Restrictions.eq("payment.docNumber", docNo));
		criteria.add(Restrictions.eq("payment.docCode", docCode));
		criteria.add(Restrictions.eq("payment.docYear", docFinanceYear));
		criteria.add(Restrictions.eq("payment.receiptType", recType));
		criteria.add(Restrictions.eq("payment.companyId", companyId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Payment> lstPayment = (List<Payment>) findAll(criteria);
		
		return lstPayment;
	}
	
	@Override
	public String paymentModeDescription(String paymentCode, BigDecimal langId) {
		String paymentDesc = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(PaymentModeDesc.class, "paymentModeDesc");
		criteria.setFetchMode("paymentModeDesc.languageType", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.languageType", "languageType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", langId));

		criteria.setFetchMode("paymentModeDesc.paymentMode", FetchMode.JOIN);
		criteria.createAlias("paymentModeDesc.paymentMode", "paymentMode",JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("paymentMode.paymentCode", paymentCode));

		List<PaymentModeDesc> lstPaymentMode = (List<PaymentModeDesc>) findAll(criteria);
		if (lstPaymentMode.size() != 0) {
			paymentDesc = lstPaymentMode.get(0).getLocalPaymentName();
		}
		return paymentDesc;
	}
	
	@Override
	public String bankDescription(String bankCode){
		String bankName=null;
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewBankDetails.class, "viewBankDetails");
		criteria.add(Restrictions.eq("viewBankDetails.chequeBankCode", bankCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);		
		List<ViewBankDetails> lstViewBankDetails = (List<ViewBankDetails>) findAll(criteria);		
		if(lstViewBankDetails.size()>0)
		{
			ViewBankDetails viewBankDetails=lstViewBankDetails.get(0);
			bankName=viewBankDetails.getBankFullName();
		}		
		return bankName;
	}
	
	public void deactivateRecord(BigDecimal recpayPk,String UserName)
	{
		ReceiptPayment receiptPayment=(ReceiptPayment) getSession().get(ReceiptPayment.class, recpayPk);
		receiptPayment.setModifiedBy(UserName);
		receiptPayment.setModifiedDate(new Date());
		receiptPayment.setIsActive(Constants.D);
		
		getSession().saveOrUpdate(receiptPayment);
		
		
	}
	
	@Override
	public List<ViewReceiptPayment> getReceiptPaymentForApproval(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentYear, BigDecimal documentNo) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ViewReceiptPayment.class, "receiptPayment");
		dCriteria.add(Restrictions.eq("receiptPayment.fsCountryMaster", appCountryId));
		
		dCriteria.add(Restrictions.eq("receiptPayment.fsCompanyMaster", companyId));
		
		
		dCriteria.add(Restrictions.eq("receiptPayment.documentCode", documentCode));
		dCriteria.add(Restrictions.eq("receiptPayment.documentNo", documentNo));
		dCriteria.add(Restrictions.eq("receiptPayment.documentFinanceYear", documentYear));
		dCriteria.add(Restrictions.eq("receiptPayment.isActive", Constants.U));
		
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 
	    
		return (List<ViewReceiptPayment>) findAll(dCriteria); 
	}
}
