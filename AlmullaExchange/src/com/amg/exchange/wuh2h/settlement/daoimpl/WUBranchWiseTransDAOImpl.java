package com.amg.exchange.wuh2h.settlement.daoimpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.wuh2h.model.VwBrnc;
import com.amg.exchange.wuh2h.model.WUH2HTransactionView;
import com.amg.exchange.wuh2h.settlement.dao.IWUBranchWiseTransDAO;

@Repository
@SuppressWarnings("unchecked")
public class WUBranchWiseTransDAOImpl extends CommonDaoImpl implements IWUBranchWiseTransDAO {


	@Override
	public List<WUH2HTransactionView> getBranchwiseTransactionCountList(BigDecimal sendDocumentCode,BigDecimal recvDocumentCode,BigDecimal companyId,BigDecimal applicationCountryId,Date transactionFromDate,Date transactionToDate){

		List<WUH2HTransactionView> listTransaction= new ArrayList<WUH2HTransactionView>();
		WUH2HTransactionView view = new WUH2HTransactionView();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		//String txndatenew = dateformat.format(txndate);	
		String transactionFromDate1 = dateformat.format(transactionFromDate);	
		String transactionToDate1 = dateformat.format(transactionToDate);	


		/*String queryString = "select branch_name as branch_name , sum(send_count) as send_count,sum(recv_count) as recv_count, sum(send_tranx_amount) as send_tranx_amount," 
				+" sum(recv_tranx_amount) as recv_tranx_amount "
				+" from (  "
				+ "   	select count(*) send_count, sum(m.LOCAL_TRaNX_AMOUNT) send_tranx_amount,  "
				+"   	(select branch_name from ex_country_branch  where country_branch_id =m.country_branch_id) branch_name,  "
				+"     null as recv_count,  "
				+"     null as recv_tranx_amount  "
				+"     from VW_EX_WU_CUSTOMER_SEND_TXN m  "
				+"		WHERE"
				+" trunc(m.DOCUMENT_DATE) between to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
				+"     AND m.application_country_id ="+applicationCountryId
				+"     AND m.company_id ="+companyId
				+"     AND m.COLLECTION_DOC_CODE="+sendDocumentCode    
				+"     AND m.country_branch_id = ANY (select country_branch_id from ex_country_branch)  "
				+"     group by m.country_branch_id  "

							 +" UNION ALL  "

							 +"   select "
							 +"   null as send_count,  "
							 +"   null as send_tranx_amount,  "
							 +"   (select branch_name from ex_country_branch  where country_branch_id =m.country_branch_id) branch_name,  "
							 +"   count(*) recv_count,  "
							 +"  sum(m.LOCAL_TRNX_AMOUNT) recv_tranx_amount	 "						    
							 +"   from vw_ex_receive_transaction m  "
							 +"   WHERE"
							 +"   trunc(m.DOCUMENT_DATE) between to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
							 +"   AND m.application_country_id = "+applicationCountryId
							 +"   AND m.company_id =" +companyId
							 +"   AND m.DOCUMENT_CODE= "+recvDocumentCode
							 +"   AND m.country_branch_id = ANY (select country_branch_id from ex_country_branch) "
							 +"   group by m.country_branch_id "
							 +" ) m "
							 +" group by branch_name "
							 +" order by branch_name ";*/

		String queryString = "SELECT A.LOCCOD, (SELECT C.FUDESC FROM V_BRNC C WHERE C.LOCCOD = A.LOCCOD) AS BRANCH_NAME,"
				+"A.SEND_COUNT, A.SEND_AMOUNT,"
				+"B.RECEIVED_COUNT, B.RECEIVED_AMOUNT"
				+"FROM (SELECT LOCCOD,"
				+"COUNT (REMITTANCE_TRANSACTION_ID) SEND_COUNT,"
				+"SUM (LOCAL_NET_TRANX_AMOUNT) SEND_AMOUNT"
				+"FROM EX_REMIT_TRNX"
				+"WHERE ACCOUNT_MMYYYY >= to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and <= to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
				+"AND TRUNC (DOCUMENT_DATE) BETWEEN to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
				+"AND WESTERN_UNION_MTCNO IS NOT NULL"
				+"AND WU_TERMINAL_ADDRESS IS NOT NULL"
				+"GROUP BY LOCCOD) A,"
				+"(SELECT LOCCOD,"
				+"COUNT (RECEIPT_ID) RECEIVED_COUNT,"
				+"SUM (LOCAL_NET_AMOUNT) RECEIVED_AMOUNT"
				+"FROM EX_RECEIPT_PAYMENT"
				+"WHERE ACCOUNT_MMYYYY >= to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and <= to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
				+"AND TRUNC (DOCUMENT_DATE) BETWEEN to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"
				+"AND WESTERN_UNION_MTCNO IS NOT NULL"
				+"AND WU_TERMINAL_ADDRESS IS NOT NULL"
				+"GROUP BY LOCCOD) B"
				+"WHERE A.LOCCOD = B.LOCCOD(+)";

		SQLQuery query = getSession().createSQLQuery(queryString);
		List<Object[]> listSummaryObject = query.list();

		if (listSummaryObject == null || listSummaryObject.isEmpty()){
			view = new WUH2HTransactionView();
			view.setBranchName(null);
			view.setSendCount(new BigDecimal(0));
			view.setSendTotalTxnAmount(new BigDecimal(0));
			view.setReceiveCount(new BigDecimal(0));
			view.setReceiveTotalTxnAmount(new BigDecimal(0));

			listTransaction.add(view);	
		}else{
			for (Object object : listSummaryObject) {
				Object[] list = (Object[]) object;
				if (list.length > 0) {
					view = new WUH2HTransactionView();

					if(list[0]!=null){
						view.setBranchName(list[0].toString());
					}else{
						view.setBranchName(null);
					}
					if(list[1]!=null){
						view.setSendCount(new BigDecimal(list[1].toString()));
					}else{
						view.setSendCount(new BigDecimal(0));
					}
					if(list[2]!=null){
						view.setReceiveCount(new BigDecimal(list[2].toString()));
					}else{
						view.setReceiveCount(new BigDecimal(0));
					}	
					if(list[3]!=null){
						view.setSendTotalTxnAmount(new BigDecimal(list[3].toString()));
					}else{
						view.setSendTotalTxnAmount(new BigDecimal(0));
					}	
					if(list[4]!=null){
						view.setReceiveTotalTxnAmount(new BigDecimal(list[4].toString()));
					}else{
						view.setReceiveTotalTxnAmount(new BigDecimal(0));
					}	
				}
				listTransaction.add(view);	
			}
		}
		return listTransaction;
	}


	public HashMap<String,Object> branchWiseSendRecieveList(Date transactionFromDate,Date transactionToDate) {

		HashMap<String, Object> totalListMap = new HashMap<String, Object>();

		//Location code;
		//List<VwBrnc> locList = locationList();
		//totalListMap.put("BRANCHLIST", locList);

		//Send Transaction.
		HashMap<BigDecimal, WUH2HTransactionView> lstWUH2HTransactionView= sendTransactionList(transactionFromDate,transactionToDate);
		totalListMap.put("SENDTRANSACTION", lstWUH2HTransactionView);

		//Receive Transaction.
		HashMap<BigDecimal, WUH2HTransactionView> recTrns = receiveTransactionList(transactionFromDate, transactionToDate);
		totalListMap.put("RECEIVETRANSACTION", recTrns);		

		return totalListMap;

	}


	private List<VwBrnc> locationList(){
		DetachedCriteria criteria = DetachedCriteria.forClass(VwBrnc.class,"vwBrnc");		

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<VwBrnc>)findAll(criteria);
	}


	private HashMap<BigDecimal, WUH2HTransactionView> sendTransactionList(Date transactionFromDate,Date transactionToDate){

		HashMap<BigDecimal, WUH2HTransactionView> lstWUH2HTransactionView= new HashMap<BigDecimal, WUH2HTransactionView>();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String transactionFromDate1 = dateformat.format(transactionFromDate);	
		String transactionToDate1 = dateformat.format(transactionToDate);

		String sql = "SELECT A.COUNTRY_BRANCH_ID, (SELECT BRANCH_NAME FROM EX_COUNTRY_BRANCH C WHERE C.COUNTRY_BRANCH_ID = A.COUNTRY_BRANCH_ID) BRNC,"
				+"COUNT (REMITTANCE_TRANSACTION_ID) SEND_COUNT,"+" "
				+"SUM (LOCAL_NET_TRANX_AMOUNT) SEND_AMOUNT"+" "
				+" FROM EX_REMIT_TRNX A"+" "
				+" WHERE ACCOUNT_MMYYYY >= trunc(to_date('" + transactionFromDate1 + "','dd/MM/yyyy'),'MONTH')"+" "		          
				+" And ACCOUNT_MMYYYY <= TRUNC(to_date('" + transactionToDate1 + "','dd/MM/yyyy'),'MONTH')"+" "
				+" AND TRUNC (DOCUMENT_DATE) BETWEEN to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"+" "
				+" AND WESTERN_UNION_MTCNO IS NOT NULL"+" "
				+" AND WU_TERMINAL_ADDRESS IS NOT NULL"+" "
				+" GROUP BY COUNTRY_BRANCH_ID";

		SQLQuery query = getSession().createSQLQuery(sql);		

		List<Object[]> lstSendTrans = query.list();
		if(lstSendTrans!=null && lstSendTrans.size()>0)
		{
			for(Object[] obj:lstSendTrans)
			{
				Object objLoc=obj[0];
				Object objBranchName=obj[1];
				Object objSendCnt=obj[2];
				Object objSendAmt=obj[3];
				
				if(objLoc != null && objBranchName != null && objSendCnt != null && objSendAmt != null){
					lstWUH2HTransactionView.put((BigDecimal)objLoc, new WUH2HTransactionView((String)objBranchName, (BigDecimal)objSendCnt, (BigDecimal)objSendAmt));
				}
			}
		}		
		return lstWUH2HTransactionView;
	}



	private HashMap<BigDecimal, WUH2HTransactionView> receiveTransactionList(Date transactionFromDate,Date transactionToDate){

		HashMap<BigDecimal, WUH2HTransactionView> lstWUH2HTransactionView= new HashMap<BigDecimal, WUH2HTransactionView>();

		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String transactionFromDate1 = dateformat.format(transactionFromDate);	
		String transactionToDate1 = dateformat.format(transactionToDate);

		String sql = "SELECT A.COUNTRY_BRANCH_ID, (SELECT BRANCH_NAME FROM EX_COUNTRY_BRANCH C WHERE C.COUNTRY_BRANCH_ID = A.COUNTRY_BRANCH_ID) BRNC,"
				+" COUNT (RECEIPT_ID) RECEIVED_COUNT,"+" "
				+" SUM (LOCAL_NET_AMOUNT) RECEIVED_AMOUNT"+" "
				+" FROM EX_RECEIPT_PAYMENT A"+" "
				+" WHERE ACCOUNT_MMYYYY >= TRUNC(to_date('" + transactionFromDate1 + "','dd/MM/yyyy'),'MONTH')"+" "		          
				+" And ACCOUNT_MMYYYY <= TRUNC(to_date('" + transactionToDate1 + "','dd/MM/yyyy'),'MONTH')"+" "
				+" AND TRUNC (DOCUMENT_DATE) BETWEEN to_date('" + transactionFromDate1 + "','dd/MM/yyyy') and to_date('" + transactionToDate1 + "','dd/MM/yyyy')"+" "
				+" AND WESTERN_UNION_MTCNO IS NOT NULL"+" "
				+" AND WU_TERMINAL_ADDRESS IS NOT NULL"+" "
				+" GROUP BY COUNTRY_BRANCH_ID";

		SQLQuery query = getSession().createSQLQuery(sql);

		List<Object[]> lstReceiveTrans = query.list();
		if(lstReceiveTrans!=null && lstReceiveTrans.size()>0)
		{
			for(Object[] obj:lstReceiveTrans)
			{
				Object objLoc=obj[0];
				Object objBranchName=obj[1];
				Object objReceiveCnt=obj[2];
				Object objReceiveAmt=obj[3];

				if(objLoc != null && objBranchName != null && objReceiveCnt != null && objReceiveAmt != null){
					lstWUH2HTransactionView.put((BigDecimal)objLoc, new WUH2HTransactionView((String)objBranchName, (BigDecimal)objReceiveCnt, (BigDecimal)objReceiveAmt));
				}				
			}
		}		

		return lstWUH2HTransactionView;
	}

}
