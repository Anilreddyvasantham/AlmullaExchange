package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import sun.util.logging.resources.logging;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;

import com.amg.exchange.remittance.bean.KnetTranxFileUploadDatatable;
import com.amg.exchange.remittance.dao.IKnetUploadFileDao;
import com.amg.exchange.remittance.model.CustomerDBCardDetailsView;
import com.amg.exchange.remittance.model.KnetDownload;
import com.amg.exchange.remittance.model.ServiceApplicabilityRule;
import com.amg.exchange.remittance.model.ViewKnetTrnxRelease;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("rawtypes")
@Repository
public class KnetUploadFileDaoImpl extends CommonDaoImpl implements IKnetUploadFileDao {

	Logger LOGGER = Logger.getLogger(KnetUploadFileDaoImpl.class);

	@Override
	public List<KnetDownload> checkDupliateKnetTrnx(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable) {
		DetachedCriteria criteria = DetachedCriteria.forClass(KnetDownload.class, "knetDownloadModel");
		criteria.add(Restrictions.eq("merchantTranckId", knetTranxFileUploadDatatable.getCustomerReference()));
		criteria.add(Restrictions.eq("approvalNumber", knetTranxFileUploadDatatable.getAuthorizationCode()));
		criteria.add(Restrictions.eq("trnxAmount", knetTranxFileUploadDatatable.getAmount()));
		criteria.add(Restrictions.eq("referenceId", knetTranxFileUploadDatatable.getReferenceId()));
		criteria.add(Restrictions.eq("transactionId", knetTranxFileUploadDatatable.getTransactionId()));
		return (List<KnetDownload>) findAll(criteria);
	}

	@Override
	public int checkCustomerReference(KnetTranxFileUploadDatatable knetTranxFileUploadDatatable) {

		/*
		 * String startDateStr= null; String endDateStr =null;
		 * 
		 * SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		 * if(knetTranxFileUploadDatatable.getTrnxDate()!= null ){
		 * startDateStr=dateformat
		 * .format(knetTranxFileUploadDatatable.getTrnxDate()); }else{
		 * startDateStr=dateformat.format(new Date()); }
		 */

		int i = 0;
		List<Object> list = null;

		BigDecimal fYear = knetTranxFileUploadDatatable.getFinYear();
		String fYearStr = null;

		if (knetTranxFileUploadDatatable.getCustomerReference() != null) {
			fYearStr = knetTranxFileUploadDatatable.getCustomerReference().substring(0, 4);
		}

		System.out.println("fYearStr :" + knetTranxFileUploadDatatable.getCustomerReference() + "\t Test :" + fYearStr + "\t Doc no :"
				+ knetTranxFileUploadDatatable.getCustomerReference().substring(4));

		String hqlQry = "SELECT COMPANY_ID,APPLICATION_COUNTRY_ID,CUSTOMER_REFERENCE,COLLECTION_DOCUMENT_NO,COLLECTION_DOC_CODE," + " COLLECTION_DOC_FINANCE_YEAR FROM EX_REMIT_TRNX A " + " WHERE "
				+ " ACCOUNT_MMYYYY = trunc(TO_DATE('" + knetTranxFileUploadDatatable.getTrnxDate() + "','dd/MM/yyyy'),'MONTH')" + " AND CUSTOMER_REFERENCE ="
				+ knetTranxFileUploadDatatable.getCustomerReference() + " AND TRUNC(DOCUMENT_DATE)=to_date('" + knetTranxFileUploadDatatable.getTrnxDate() + "','dd/MM/yyyy') "
				+ " AND (A.COMPANY_ID ,A.COLLECTION_DOCUMENT_NO,A.COLLECTION_DOC_CODE,A.COLLECTION_DOC_FINANCE_YEAR)"
				+ " IN(SELECT COMPANY_ID, DOCUMENT_NO,DOCUMENT_CODE,DOCUMENT_FINANCE_YEAR FROM EX_COLLECT_DETAIL B " + " WHERE B.COLLECTION_MODE='K' " + " AND B.COLLAMT="
				+ knetTranxFileUploadDatatable.getAmount() + " " + " AND B.APPROVAL_NO =" + knetTranxFileUploadDatatable.getAuthorizationCode() + ") " + " AND NVL(A.DELIVERY_IND,' ')='H'	";
		System.out.println("SQL :" + hqlQry);

		SQLQuery query = getSession().createSQLQuery(hqlQry);
		list = query.list();
		System.out.println("list with cust :" + list.size());
		if (list != null && list.size() > 0) {
			i = 1;
		} else {
			i = 0;
		}

		if (fYearStr != null && fYearStr.equalsIgnoreCase(knetTranxFileUploadDatatable.getFinYear().toString())) {

			if (i == 0) {
				String hqlQryKiosk = "SELECT COMPANY_ID,APPLICATION_COUNTRY_ID,CUSTOMER_REFERENCE,COLLECTION_DOCUMENT_NO,COLLECTION_DOC_CODE," + " COLLECTION_DOC_FINANCE_YEAR FROM EX_REMIT_TRNX A "
						+ " WHERE " + " ACCOUNT_MMYYYY = trunc(TO_DATE('" + knetTranxFileUploadDatatable.getTrnxDate() + "','dd/MM/yyyy'),'MONTH')" + " AND APPLICATION_FINANCE_YEAR="
						+ knetTranxFileUploadDatatable.getFinYear() + " " + " AND APPLICATION_DOCUMENT_NO =" + knetTranxFileUploadDatatable.getCustomerReference().substring(4)
						+ " AND TRUNC(DOCUMENT_DATE)=to_date('" + knetTranxFileUploadDatatable.getTrnxDate() + "','dd/MM/yyyy') "
						+ " AND (A.COMPANY_ID ,A.COLLECTION_DOCUMENT_NO,A.COLLECTION_DOC_CODE,A.COLLECTION_DOC_FINANCE_YEAR)"
						+ " IN(SELECT COMPANY_ID, DOCUMENT_NO,DOCUMENT_CODE,DOCUMENT_FINANCE_YEAR FROM EX_COLLECT_DETAIL B " + " WHERE B.COLLECTION_MODE='K' " + " AND B.COLLAMT="
						+ knetTranxFileUploadDatatable.getAmount() + " " + " AND B.APPROVAL_NO =" + knetTranxFileUploadDatatable.getAuthorizationCode() + ") " + " AND NVL(A.DELIVERY_IND,' ')='H'	";
				System.out.println("SQL :" + hqlQryKiosk);

				SQLQuery queryk = getSession().createSQLQuery(hqlQryKiosk);
				list = queryk.list();
				if (list != null && list.size() > 0) {
					i = 1;
				} else {
					i = 0;
				}
			}
		}

		return i;
	}

	@Override
	public void saveKnetDownLoadFileDetails(KnetDownload knetDownload) {
		getSession().saveOrUpdate(knetDownload);

	}

	@Override
	public HashMap<String, Object> callKnetTransferProcedureForUpdate(KnetTranxFileUploadDatatable knetTrnxDataTable) throws AMGException {

		String statusMsg = null;
		String errMsg = null;
		BigDecimal finYear = null;
		BigDecimal collectionNumber = null;

		HashMap<String, Object> outputValues = new HashMap<String, Object>();
		Connection connection = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_KNET_TRANSFER (?,?,?,?,?,?,?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);

			cs.setString(1, knetTrnxDataTable.getTrnxDate());
			cs.setString(2, knetTrnxDataTable.getAuthorizationCode().toString());
			cs.setBigDecimal(3, knetTrnxDataTable.getAmount());
			cs.setString(4, knetTrnxDataTable.getCardNumber());
			cs.setBigDecimal(5, new BigDecimal(knetTrnxDataTable.getCustomerReference()));
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);
			cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			cs.registerOutParameter(8, java.sql.Types.INTEGER);
			cs.registerOutParameter(9, java.sql.Types.INTEGER);
			cs.registerOutParameter(10, java.sql.Types.INTEGER);
			cs.registerOutParameter(11, java.sql.Types.INTEGER);
			cs.registerOutParameter(12, java.sql.Types.INTEGER);

			cs.executeUpdate();// teUpdate();

			statusMsg = cs.getString(6);
			errMsg = cs.getString(7);

			outputValues.put("P_ERROR_MESSAGE", errMsg);
			outputValues.put("P_STATUS", statusMsg);
			outputValues.put("P_COL_DOCFYR", cs.getBigDecimal(8));
			outputValues.put("P_COL_DOCNO", cs.getBigDecimal(9));
			outputValues.put("P_CUSREF", cs.getBigDecimal(10));
			outputValues.put("P_LOCCOD", cs.getBigDecimal(11));
			outputValues.put("P_COMPANY_CODE", cs.getBigDecimal(12));

			System.out.println("outputValues  " + outputValues);
		} catch (SQLException e) {
			errMsg = "EX_KNET_TRANSFER " + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				errMsg = "EX_KNET_TRANSFER " + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

		return outputValues;
	}

	@Override
	public HashMap<String, Object> callKnetTransferProcedureForRefund(BigDecimal compId, BigDecimal docCode, BigDecimal docFyr, BigDecimal docNo, String pind, String userName) throws AMGException {
		String statusMsg = null;
		String errMsg = null;
		BigDecimal finYear = null;
		BigDecimal collectionNumber = null;

		LOGGER.info("EX_P_KNETREFUND_JAVA compId :" + compId);
		LOGGER.info("docCode :" + docCode);
		LOGGER.info("docFyr :" + docFyr);
		LOGGER.info("docNo :" + docNo);
		LOGGER.info("pind :" + pind);

		HashMap<String, Object> outputValues = new HashMap<String, Object>();
		Connection connection = null;
		CallableStatement cs;
		try {
			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_P_KNETREFUND_JAVA(?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);

			cs.setBigDecimal(1, compId);
			cs.setBigDecimal(2, docCode);
			cs.setBigDecimal(3, docFyr);
			cs.setBigDecimal(4, docNo);
			cs.setString(5, pind);
			cs.setString(6, userName);

			cs.executeUpdate();// teUpdate();

			System.out.println("outputValues  " + outputValues);
		} catch (SQLException e) {
			errMsg = "EX_P_KNETREFUND_JAVA " + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				errMsg = "EX_P_KNETREFUND_JAVA " + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

		return outputValues;
	}

	@Override
	public List<CustomerDBCardDetailsView> customerBanksView(BigDecimal customerId, String cardNumber, KnetTranxFileUploadDatatable knetTrnxDataTable) {
		System.out.println("customerId :" + customerId + "\t cardNumber :" + cardNumber);
		List<CustomerDBCardDetailsView> listCardDetails = null;

		BigDecimal fYear = knetTrnxDataTable.getFinYear();
		String fYearStr = null;

		if (knetTrnxDataTable.getCustomerReference() != null) {
			fYearStr = knetTrnxDataTable.getCustomerReference().substring(0, 4);
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerDBCardDetailsView.class, "customerDBCardDetailsView");
		criteria.add(Restrictions.eq("customerDBCardDetailsView.customerReference", customerId));
		criteria.add(Restrictions.eq("customerDBCardDetailsView.debitHideCard", cardNumber));
		listCardDetails = (List<CustomerDBCardDetailsView>) findAll(criteria);

		if (listCardDetails.isEmpty()) {
			if (fYearStr != null && fYearStr.equalsIgnoreCase(knetTrnxDataTable.getFinYear().toString())) {

				BigDecimal customerRef = null;

				String hqlQryKiosk = "SELECT CUSTOMER_REFERENCE FROM EX_REMIT_TRNX A " + " WHERE " + " ACCOUNT_MMYYYY = trunc(TO_DATE('"
						+ knetTrnxDataTable.getTrnxDate() + "','dd/MM/yyyy'),'MONTH')" + " AND APPLICATION_FINANCE_YEAR=" + knetTrnxDataTable.getFinYear() + " " + " AND APPLICATION_DOCUMENT_NO ="
						+ knetTrnxDataTable.getCustomerReference().substring(4) + " AND TRUNC(DOCUMENT_DATE)=to_date('" + knetTrnxDataTable.getTrnxDate() + "','dd/MM/yyyy') "
						+ " AND NVL(A.DELIVERY_IND,' ')='H'	";
				System.out.println("customerBanksView SQL :" + hqlQryKiosk);

				SQLQuery queryk = getSession().createSQLQuery(hqlQryKiosk);
				List list = queryk.list();
				if(list!= null){
					customerRef = (BigDecimal)list.get(0);
				}

				System.out.println("customerRef :" + customerRef + "\t knetTrnxDataTable.getCustomerReference() :" + knetTrnxDataTable.getCustomerReference() +"\t customerRef :"+customerRef);
				if (listCardDetails.isEmpty() && customerRef != null) {
					DetachedCriteria criteria1 = DetachedCriteria.forClass(CustomerDBCardDetailsView.class, "customerDBCardDetailsView");
					criteria1.add(Restrictions.eq("customerDBCardDetailsView.customerReference", customerRef));
					criteria1.add(Restrictions.eq("customerDBCardDetailsView.debitHideCard", cardNumber));
					listCardDetails = (List<CustomerDBCardDetailsView>) findAll(criteria1);
				}

			}

		}

		return listCardDetails;

	}

	@Override
	public List<ViewKnetTrnxRelease> getKnetTrnxOnHoldListfromView(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ViewKnetTrnxRelease.class, "viewKnetTrnxRelease");
		criteria.add(Restrictions.eq("viewKnetTrnxRelease.customerRefernce", customerId));
		List<ViewKnetTrnxRelease> listonHoldTrnxDetails = (List<ViewKnetTrnxRelease>) findAll(criteria);
		return listonHoldTrnxDetails;
	}

	@Override
	public List<CustomerDBCardDetailsView> duplicateCustomerBanksViewCheck(BigDecimal customerId, String cardNumber, String bankCode) {
		System.out.println("customerId :" + customerId + "\t cardNumber :" + cardNumber + "\t Bank Code :" + bankCode);

		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerDBCardDetailsView.class, "customerDBCardDetailsView");
		criteria.add(Restrictions.eq("customerDBCardDetailsView.customerReference", customerId));
		criteria.add(Restrictions.eq("customerDBCardDetailsView.debitFullCard", cardNumber));
		criteria.add(Restrictions.eq("customerDBCardDetailsView.bankCode", bankCode));
		List<CustomerDBCardDetailsView> listCardDetails = (List<CustomerDBCardDetailsView>) findAll(criteria);
		return listCardDetails;
	}

	@Override
	public String callKnetReleasePro(String trnxDate, BigDecimal collDocCode, BigDecimal collDoFyr, BigDecimal collDocNo, String user) throws AMGException {

		String errMsg = null;

		HashMap<String, Object> outputValues = new HashMap<String, Object>();
		Connection connection = null;
		CallableStatement cs;

		try {

			/*
			 * DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); String
			 * docDate = formatter.format(trnxDate);
			 */

			LOGGER.info("EX_KNET_RELAESE compId :" + trnxDate + "\t docDate :" + trnxDate);
			LOGGER.info("docCode :" + collDocCode);
			LOGGER.info("docFyr :" + collDoFyr);
			LOGGER.info("docNo :" + collDocNo);
			LOGGER.info("user :" + user);

			connection = getDataSourceFromHibernateSession();
			String call = " { call EX_KNET_RELAESE(?,?,?,?,?,?) } ";
			cs = connection.prepareCall(call);

			cs.setString(1, trnxDate);
			cs.setBigDecimal(2, collDocCode);
			cs.setBigDecimal(3, collDoFyr);
			cs.setBigDecimal(4, collDocNo);
			cs.setString(5, user);
			cs.registerOutParameter(6, java.sql.Types.VARCHAR);

			cs.executeUpdate();

			errMsg = cs.getString(6);

			System.out.println("errMsg  " + errMsg);
			return errMsg;
		} catch (SQLException e) {
			errMsg = "EX_P_KNETREFUND_JAVA " + " : " + e.getMessage();
			throw new AMGException(errMsg);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				errMsg = "EX_P_KNETREFUND_JAVA " + " : " + e.getMessage();
				throw new AMGException(errMsg);
			}
		}

	}

}
