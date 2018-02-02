package com.amg.exchange.online.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.online.bean.ChequeClearPlaceOrderBean;
import com.amg.exchange.online.bean.RatePlaceOrderDTO;
import com.amg.exchange.online.dao.IChequeClearPlaceOrderDao;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.util.AMGException;
@Repository
@SuppressWarnings({"rawtypes","unchecked"})
public class ChequeClearPlaceOrderDaoImpl extends CommonDaoImpl implements IChequeClearPlaceOrderDao {
	private static final Logger LOGGER=Logger.getLogger(ChequeClearPlaceOrderBean.class);
	@Override
	public List<RatePlaceOrderDTO> toFetchAllRecordsFromDb(BigDecimal customerId) {
		List<RatePlaceOrderDTO> listRate=new ArrayList<RatePlaceOrderDTO>();
		String hqlQuery="SELECT SUM(TRANSACTION_AMOUNT) TRNX_AMOUNT, SUM(ROUND(NVL(RATE_OFFERED,0) * NVL(TRANSACTION_AMOUNT,0),3)) LC_AMOUNT, CHEQUE_BANK_CODE, CHEQUE_REFERENCE, CHEQUE_DATE, APPROVAL_NO, CUSTOMER_ID, CUSTOMER_REFERENCE ,CUSTOMER_EMAIL_ID,RATE_PLACE_ORDER_ID FROM EX_RATE_PLACE_ORDER WHERE NVL(COLLECTION_MODE,' ') = 'B' AND NVL(CHEQUE_CLEARANCE_IND,'N') = 'N' and CHEQUE_BANK_CODE IS NOT NULL and CHEQUE_REFERENCE IS NOT NULL AND CHEQUE_DATE IS NOT NULL GROUP BY CHEQUE_BANK_CODE, CHEQUE_REFERENCE, CHEQUE_DATE, APPROVAL_NO, CUSTOMER_ID, CUSTOMER_REFERENCE,CUSTOMER_EMAIL_ID ,RATE_PLACE_ORDER_ID";
        Query query = getSession().createSQLQuery(hqlQuery);
        List<Object[]> lstRateOrder =query.list();
        if(lstRateOrder.size()>0){
        	
        	for(Object[] obj:lstRateOrder)
        	{
        		BigDecimal obj1=(BigDecimal) obj[0];
        		BigDecimal obj2=(BigDecimal) obj[1];
        		BigDecimal obj3=(BigDecimal) obj[2];
        		String obj4=(String) obj[3];
        		Date obj5=(Date) obj[4];
        		BigDecimal obj7=(BigDecimal) obj[6];
        		BigDecimal obj8=(BigDecimal) obj[7];
        		String emailId=(String) obj[8];
        		BigDecimal ratePlaceorderPk=(BigDecimal) obj[9];
        		
        		RatePlaceOrderDTO ratePlaceOrderDTO= new RatePlaceOrderDTO();
        		ratePlaceOrderDTO.setTranctionAmount(obj1);
        		ratePlaceOrderDTO.setChequeBankCode(obj3);
        		ratePlaceOrderDTO.setChequeReference(obj4);
        		ratePlaceOrderDTO.setChequeDate(obj5);
        		ratePlaceOrderDTO.setCustomerId(obj7);
        		ratePlaceOrderDTO.setCustomerReference(obj8);
        		ratePlaceOrderDTO.setEmailId(emailId);
        		ratePlaceOrderDTO.setRateOfferedPk(ratePlaceorderPk);
        		listRate.add(ratePlaceOrderDTO);
        		
        	}
        	
        }
       
		return listRate;
		
	}

	@Override
	public void saveOrUpdatePlaceOrder(List<BigDecimal> lstPkUpdates,String userName) {
		for (BigDecimal bigDecimal : lstPkUpdates) {
			RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class,bigDecimal);
			//ratePlaceOrder.setChequeClearanceInd("Y");
			getSession().saveOrUpdate(ratePlaceOrder);
		}
		
		
	}



	@Override
	public String getBankNamebasedonBankCode(String bankCode) {
		String bankName=null;
		String hqlQuery = "select distinct a.bankFullName from ViewBankDetails a where a.chequeBankCode= :bankCode";
		Query query = getSession().createQuery(hqlQuery);
		 query.setParameter("bankCode", bankCode);
		 List<String> lstbeneName =query.list();
	        if(lstbeneName.size()>0){
	        	bankName=lstbeneName.get(0);
	        }
		return bankName;
	}
	
	@Override
	public HashMap<String, String> placeOrderRemitTranxProcedure(HashMap<String, Object> inputValues) throws Exception {

		HashMap<String, String> outputValues = new HashMap<String, String>();
		String CustomerId=(String) inputValues.get("Customer_Id");
		if(CustomerId !=null)
		{
			//BigDecimal placeOrderPk= new BigDecimal(strPlaceOrderPk);

			//RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class, placeOrderPk);


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
				cs.setBigDecimal(1, new BigDecimal(CustomerId));
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
	public String upadteCheckClearence(HashMap<String, Object> inputValues) {
		
		String rtnValue=null;
		BigDecimal customerId= (BigDecimal) inputValues.get("CUSTOMER_ID");
		BigDecimal chequeBankCode= (BigDecimal)inputValues.get("CHEQUE_BANK_CODE");
		String chequeRefNo= (String)inputValues.get("CHEQUE_REFERENCE");
		BigDecimal rateOfferedPk= (BigDecimal)inputValues.get("RATE_OFFERED_PK");
		
		RatePlaceOrder ratePlaceOrder=(RatePlaceOrder) getSession().get(RatePlaceOrder.class,rateOfferedPk);
		if(ratePlaceOrder.getRemitDocumentNumber()!=null && ratePlaceOrder.getRemitDocumentYear()!=null)
		{
			rtnValue="Fail";
		}else
		{
			String chqreNo="";
			if(chequeRefNo!=null)
			{
				chqreNo=chequeRefNo.trim();
			}
			Date chequeDate=(Date) inputValues.get("CHEQUE_DATE");
			
			
			String SQl="UPDATE EX_RATE_PLACE_ORDER   SET CHEQUE_CLEARANCE_IND = 'Y'  "
							+ " WHERE CUSTOMER_ID = "+customerId+ " "
							+ "  AND COLLECTION_MODE = 'B'  "
							+ "  AND CHEQUE_BANK_CODE = "+chequeBankCode + " "
							+ "  AND CHEQUE_REFERENCE = '"+chqreNo+ "'"
							/*+ "  AND CHEQUE_DATE      = "+ chequeDate +" "*/
							+ "  AND CHEQUE_CLEARANCE_IND = 'N' "
							+" AND RATE_PLACE_ORDER_ID = " +rateOfferedPk+ "";
				
			int ui=getSession().createSQLQuery(SQl).executeUpdate();
			System.out.println("ui");
			rtnValue="Success";
		}
		return rtnValue;
		
		
	}

}
