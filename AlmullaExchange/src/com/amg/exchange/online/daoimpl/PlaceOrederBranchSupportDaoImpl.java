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
import com.amg.exchange.online.dao.IPlaceOrederBranchSupportDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.RatePlaceOrderAddlData;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class PlaceOrederBranchSupportDaoImpl extends CommonDaoImpl implements IPlaceOrederBranchSupportDao {

	Logger LOGGER = Logger.getLogger(PlaceOrederBranchSupportDaoImpl.class);
	
	@Override
	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal countryBranchCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(RatePlaceOrder.class, "ratePlaceOrder");
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedBy"));
		criteria.add(Restrictions.isNotNull("ratePlaceOrder.approvedDate"));
		criteria.add(Restrictions.eq("ratePlaceOrder.isActive",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.branchSupportIndicator",Constants.Yes));
		criteria.add(Restrictions.eq("ratePlaceOrder.supportBranchId",countryBranchCode));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentNumber"));
		criteria.add(Restrictions.isNull("ratePlaceOrder.applDocumentFinanceYear"));
		//criteria.add(Restrictions.isNull("ratePlaceOrder.appointmentTime"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RatePlaceOrder> lstRatePlaceOrders=(List<RatePlaceOrder>)findAll(criteria);
		return lstRatePlaceOrders;
	}

	@Override
	public String toFetchMobileNumBasedOnCustomerId(BigDecimal customerId) {
		String custrRef=null;
		String hqlQuery="select distinct a.mobile from  Customer a where a.customerId =  :customerId";
        Query query = getSession().createQuery(hqlQuery);
        query.setParameter("customerId", customerId);
        List<String> lstCust =query.list();
        if(lstCust.size()>0){
        	custrRef=lstCust.get(0);
        }
		return custrRef;
	}

	@Override
	public void saveAppointmentTime(BigDecimal placeOrderPk,Date appointmentTime,BigDecimal sourceId,String paymentId, String userName) {
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
		//ratePlaceOrder.setApprovedBy(userName);
		//ratePlaceOrder.setApprovedDate(new Date());
		ratePlaceOrder.setAppointmentTime(appointmentTime);
		ratePlaceOrder.setSourceOfincomeId(sourceId);
		ratePlaceOrder.setCollectionMode(paymentId);
		getSession().update(ratePlaceOrder);
	}

	@Override
	public HashMap<String, String> createRemitAppProcedure(
			HashMap<String, String> inputValues) throws Exception {
		
		String strPlaceOrderPk =inputValues.get("PLACE_ORDER_PK");
		if(strPlaceOrderPk!=null)
		{
			BigDecimal placeOrderPk= new BigDecimal(strPlaceOrderPk);
			
			RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
			

			LOGGER.info("Cretate Remittance application for Place an order");
			HashMap<String, String> outputValues = new HashMap<String, String>();
			Connection connection = null;
			try {
				// connection = DBConnection.getdataconnection();
				connection = getDataSourceFromHibernateSession();
			} catch (Exception e) {
				LOGGER.error( "PROBLEM OCCURED WHILE GETTTING CONECTION");
				e.printStackTrace();
			}
			LOGGER.info("!!!!!! createRemitAppProcedure IN PUT VALUES  !!!!!!!!! ==  " + inputValues.toString());
			LOGGER.info("!Calling  EX_P_PO_APPLICATION Procedure with INPUT VALUES  !!!!!");
			LOGGER.info(inputValues.toString());
			CallableStatement cs;
			try {
				String call = " { call EX_P_PO_APPLICATION (?,?,?, ?,?) } ";
				cs = connection.prepareCall(call);
				cs.setBigDecimal(1, new BigDecimal(inputValues.get("P_COMP_ID")));
				cs.setBigDecimal(2, ratePlaceOrder.getDocumentCode());
				cs.setBigDecimal(3, ratePlaceOrder.getDocumentFinanceYear());
				cs.setBigDecimal(4, ratePlaceOrder.getDocumentNumber());
				
				
				cs.registerOutParameter(5, java.sql.Types.VARCHAR);
				
				cs.execute();// 
				
				
				outputValues.put("P_ERROR_MESG", cs.getString(5) == null ? "" : cs.getString(5));
				
				System.out.println("!!!!!! createRemitAppProcedure outputValues VALUES  !!!!!!!!! ==  " + outputValues.toString());
				LOGGER.info("!After Calling  EX_P_PO_APPLICATION Procedure returns OUTPUT VALUES  !!!!!");
				LOGGER.info(outputValues.toString());
			} catch (SQLException e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_P_ONLINE_APPLICATION" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			} catch (Exception e) {
				LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
				String erromsg = "EX_P_ONLINE_APPLICATION" + " : " + e.getMessage();
				throw new AMGException(erromsg);
			}finally {
				try {
					connection.close();
				} catch (SQLException e) {
					LOGGER.error( "Error While Procedure Calling "+ e.getMessage());
					String erromsg = "EX_P_ONLINE_APPLICATION" + " : " + e.getMessage();
					throw new AMGException(erromsg);
				}
			}
			return outputValues;
		
		}
		
		
		return null;
	}

	@Override
	public String saveOrUpdatePlaceOrderAddlData(
			HashMap<String, Object> inputValues) throws Exception {
		String approvalMsg=null;
		BigDecimal placeOrderPk=(BigDecimal) inputValues.get("PLACE_ORDER_PK");
		Date visitTime= (Date) inputValues.get("CUSTOMER_VISIT_TIME");
		
		BigDecimal sourceId=(BigDecimal) inputValues.get("SOURCE_OF_INCOME");
		String paymentMode= (String) inputValues.get("PAYMENT_MODE");
		String userName = (String) inputValues.get("USER_NAME");
		List<RatePlaceOrderAddlData>  lstRatePlaceOrderAddlData= (List<RatePlaceOrderAddlData>) inputValues.get("PLACE_ORDER_ADDL_DATA");
		
		
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
		BigDecimal applDocNumder=ratePlaceOrder.getApplDocumentNumber();
		if(applDocNumder == null){
		//ratePlaceOrder.setApprovedBy(userName);
		//ratePlaceOrder.setApprovedDate(new Date());
		ratePlaceOrder.setAppointmentTime(visitTime);
		ratePlaceOrder.setSourceOfincomeId(sourceId);
		ratePlaceOrder.setCollectionMode(paymentMode);
		getSession().update(ratePlaceOrder);
		
		for(RatePlaceOrderAddlData ratePlaceOrderAddlData:lstRatePlaceOrderAddlData)
		{
			getSession().saveOrUpdate(ratePlaceOrderAddlData);
		}
		approvalMsg="Success";
		}else{
			approvalMsg="Fail";
		}
		return approvalMsg;
		
		
	}

	public void  updatePlaceOrderPaymentDetails(HashMap<String, Object> inputValues)
	{
		BigDecimal placeOrderPk=(BigDecimal) inputValues.get("PLACE_ORDER_PK");
		BigDecimal cheqBankCode=(BigDecimal) inputValues.get("CHEQUE_BANK_CODE");
		Date cheqDate= (Date) inputValues.get("CHEQUE_DATE");
		String cheqRefNo=(String) inputValues.get("CHEQUE_REFERENCE");
		String collectionMode=(String) inputValues.get("COLLECTION_MODE");
		String approvalNo=(String) inputValues.get("APPROVAL_NO");
		
		
		String dbCardName=(String) inputValues.get("DB_CARD_NAME");
		String dbCard=(String) inputValues.get("DEBIT_CARD");
		String knetReceipt=(String) inputValues.get("KNET_RECEIPT");
		String knetReceiptDateTime=(String) inputValues.get("KNET_RECEIPT_DATE_TIME");
		
		
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);
		
		if(collectionMode.equalsIgnoreCase("CHEQUE"))
		{
			ratePlaceOrder.setChequeBankCode(cheqBankCode);
			ratePlaceOrder.setChequeDate(cheqDate);
			ratePlaceOrder.setChequeReference(cheqRefNo);
			ratePlaceOrder.setApprovalNo(approvalNo);
		}else
		{
			ratePlaceOrder.setDbCardName(dbCardName);
			ratePlaceOrder.setDebitCard(dbCard);
			ratePlaceOrder.setKnetReceipt(knetReceipt);
			ratePlaceOrder.setKnetReceiptDateTime(knetReceiptDateTime);
		}
		
		getSession().saveOrUpdate(ratePlaceOrder);
	}

	@Override
	public List<PaymentMode> fetchPaymodeDescForOnlineCustomer(String isActive) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(PaymentMode.class, "paymentMode");
		
	     dCriteria.add(Restrictions.eq("paymentMode.isActive", isActive));
	     dCriteria.add(Restrictions.eq("paymentMode.paymentCode",Constants.KNETCode));      
       
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<PaymentMode> lstPaymentMode=(List<PaymentMode>)findAll(dCriteria);
		return lstPaymentMode;
	}
}
