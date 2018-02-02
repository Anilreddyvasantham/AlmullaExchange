package com.amg.exchange.online.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.dao.IPlaceOrderPendingTransctionDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class PlaceOrderPendingTransctionDaoImpl extends CommonDaoImpl implements IPlaceOrderPendingTransctionDao {

	Logger LOGGER = Logger.getLogger(PlaceOrderPendingTransctionDaoImpl.class);
	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.rateOffered"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.collectionMode"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.applDocumentFinanceYear"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.chequeClearanceInd"));
		
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		if(customerId != null){
			criteria.add(Restrictions.eq("ratePlaceOrder.customerId",customerId));
		}
		criteria.add(Restrictions.isNull("ratePlaceOrder.transactionConcluded"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public BigDecimal toFetchPaymentId(String collectionMode) {
		BigDecimal PaymentModeID=null;
		String hqlQuery="select distinct a.paymentModeId from  PaymentMode a where a.paymentCode =  :collectionMode and a.isActive= 'Y'";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("collectionMode", collectionMode);
        List<BigDecimal> lstPayment =query.list();
        if(lstPayment.size()>0){
        	PaymentModeID=lstPayment.get(0);
        }
		return PaymentModeID;
	}

	@Override
	public String toFetchPaymentName(BigDecimal paymentId, BigDecimal languageId) {
		String PaymentMode=null;
		String hqlQuery="select distinct a.paymentModeId from  PaymentMode a where a.paymentCode =  :paymentId and a.isActive= :languageId";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("paymentId", paymentId);
        query.setParameter("languageId", languageId);
        List<String> lstPayment =query.list();
        if(lstPayment.size()>0){
        	PaymentMode=lstPayment.get(0);
        }
		return PaymentMode;
	}

	@Override
	public HashMap<String, Object>  updatePlaceOrderPaymentDetails(HashMap<String, Object> inputValues)
	{
		HashMap<String, Object> outPutValues = new HashMap<String, Object>();
		
		//BigDecimal placeOrderPk=(BigDecimal) inputValues.get("PLACE_ORDER_PK");
		List<BigDecimal> lstRatePlaceOrderPk=(List<BigDecimal>) inputValues.get("PLACE_ORDER_PK");
		
		BigDecimal cheqBankCode=(BigDecimal) inputValues.get("CHEQUE_BANK_CODE");
		Date cheqDate= (Date) inputValues.get("CHEQUE_DATE");
		String cheqRefNo=(String) inputValues.get("CHEQUE_REFERENCE");
		String collectionMode=(String)inputValues.get("COLLECTION_MODE");
		BigDecimal approvalNo=(BigDecimal) inputValues.get("APPROVAL_NO");
		
		
		String dbCardName=(String) inputValues.get("DB_CARD_NAME");
		BigDecimal dbCard=(BigDecimal) inputValues.get("DEBIT_CARD");
		String knetReceipt=(String) inputValues.get("KNET_RECEIPT");
		String knetReceiptDateTime=(String) inputValues.get("KNET_RECEIPT_DATE_TIME");
		//BigDecimal knetApprovalNo =(BigDecimal) inputValues.get("KNET_APPROVAL_NO");
		
		if(lstRatePlaceOrderPk!=null)
		{
			for(BigDecimal placeOrderPk :lstRatePlaceOrderPk)
			{
				RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
				
				if(collectionMode.equalsIgnoreCase("B"))
				{
					ratePlaceOrder.setChequeBankCode(cheqBankCode);
					ratePlaceOrder.setChequeDate(cheqDate);
					ratePlaceOrder.setChequeReference(cheqRefNo);
					ratePlaceOrder.setApprovalNo(approvalNo.toString());
					ratePlaceOrder.setChequeClearanceInd(Constants.No);
					ratePlaceOrder.setCollectionMode(collectionMode);
				}else
				{
					ratePlaceOrder.setDbCardName(dbCardName);
					ratePlaceOrder.setDebitCard(dbCard.toPlainString());
					ratePlaceOrder.setKnetReceipt(knetReceipt);
					ratePlaceOrder.setKnetReceiptDateTime(knetReceiptDateTime);
					ratePlaceOrder.setCollectionMode(collectionMode);
					//ratePlaceOrder.setApprovalNo(knetApprovalNo.toString());
				}
				
				getSession().saveOrUpdate(ratePlaceOrder);
				
				outPutValues.put("RATE_PLACE_ORDER", ratePlaceOrder);
			}
		}
		
		
		return outPutValues;
	}
	
	
	
	@Override
	public HashMap<String, String> placeOrderRemitTranxProcedure(
			HashMap<String, Object> inputValues) throws Exception {

		HashMap<String, String> outputValues = new HashMap<String, String>();
		BigDecimal placeOrderPk=(BigDecimal) inputValues.get("PLACE_ORDER_PK_TR");
		if(placeOrderPk!=null)
		{
			//BigDecimal placeOrderPk= new BigDecimal(strPlaceOrderPk);

			RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);


			LOGGER.info("Cretate Remittance application for Place an order");

			Connection connection = null;
			try {
				// connection = DBConnection.getdataconnection();
				connection = getDataSourceFromHibernateSession();
			} catch (Exception e) {
				LOGGER.error( "PROBLEM OCCURED WHILE GETTTING CONECTION");
				e.printStackTrace();
			}
			LOGGER.info("!!!!!! createRemitAppProcedure IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
			LOGGER.info("!Calling  EX_P_PO_REMITTANCE Procedure with INPUT VALUES  !!!!!");
			LOGGER.info(inputValues.toString());
			CallableStatement cs;
			try {
				String call = " { call EX_P_PO_REMITTANCE (?,?,?,?) } ";
				cs = connection.prepareCall(call);
				cs.setBigDecimal(1, ratePlaceOrder.getCustomerId());
				
				/*cs.setBigDecimal(2, ratePlaceOrder.getDocumentCode());
				cs.setBigDecimal(3, ratePlaceOrder.getDocumentFinanceYear());
				cs.setBigDecimal(4, ratePlaceOrder.getDocumentNumber());*/

				cs.registerOutParameter(2, java.sql.Types.INTEGER);
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				cs.registerOutParameter(4, java.sql.Types.VARCHAR);

				cs.execute();// 
				
				outputValues.put("P_COLL_DOCFYR", cs.getBigDecimal(2) == null ? "" : cs.getBigDecimal(2).toPlainString());
				outputValues.put("P_COLL_DOCNO", cs.getBigDecimal(3) == null ? "" : cs.getBigDecimal(3).toPlainString());

				outputValues.put("P_ERROR_MESG", cs.getString(4) == null ? "" : cs.getString(4));

				System.out.println("!!!!!! createRemitAppProcedure outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
				LOGGER.info("!After Calling  EX_P_PO_REMITTANCE Procedure returns OUTPUT VALUES  !!!!!");
				LOGGER.info(outputValues.toString());
			} catch (SQLException e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_P_PO_REMITTANCE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} catch (Exception e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_P_PO_REMITTANCE" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
					String erromsg = "EX_P_ONLINE_REMITTANCE" + " : " + e.getMessage();
					throw new AMGException(erromsg);
				}
			}

		}

		return outputValues;
	}
	@Override
	public List<RemittanceApplication> getTransactionDetails(BigDecimal ratePlaceOrderId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceApplication.class, "remittanceApplication");
		
		criteria.add(Restrictions.eq("remittanceApplication.ratePlaceOrderId",ratePlaceOrderId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceApplication> lstRemittanceApplication=(List<RemittanceApplication>)findAll(criteria);
		return lstRemittanceApplication;
	}
}
