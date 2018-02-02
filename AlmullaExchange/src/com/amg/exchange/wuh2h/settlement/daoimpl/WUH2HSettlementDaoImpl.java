package com.amg.exchange.wuh2h.settlement.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.settlement.dao.IWUH2HSettlementDao;
import com.amg.exchange.wuh2h.settlement.model.WUTxnSummaryView;

@SuppressWarnings("unchecked")
@Repository
public class WUH2HSettlementDaoImpl<T> extends CommonDaoImpl<T> implements IWUH2HSettlementDao,Serializable { 
	
	private static final long serialVersionUID = 1L;
	public static final Logger log=Logger.getLogger(WUH2HSettlementDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	
	
	@Override
	public List<WUTxnSummaryView> getWUTransactionSummary(Date txndate){
		
		List<WUTxnSummaryView> listSummary= new ArrayList<WUTxnSummaryView>();
		
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
		String txndatenew = dateformat.format(txndate);
	
		String queryString = " SELECT DISTINCT LOCCOD,CURCOD,TYP,UNSETT_AMT, SETT_AMT, UNSETT_CNT,SETT_CNT "
				+ " FROM VW_EX_WU_SUMMARY_REPORT "
				+ " WHERE "
				+ " ACYYMM = TRUNC(TO_DATE('" + txndatenew + "','dd/MM/yyyy'),'MONTH') AND" 
				+ "  CRTDAT =  to_date('" + txndatenew + "','dd/MM/yyyy')";
		
		SQLQuery query = getSession().createSQLQuery(queryString);
		List<Object[]> listSummaryObject = query.list();
		
		if (listSummaryObject == null || listSummaryObject.isEmpty()){
			// no records
		}else{
			for (Object object : listSummaryObject) {
				Object[] list = (Object[]) object;
				if (list.length > 0) {
					WUTxnSummaryView wuTxnSummaryView = new WUTxnSummaryView();
					wuTxnSummaryView.setLocationCode(new BigDecimal(list[0].toString()));
					wuTxnSummaryView.setCurrencyCode(list[1].toString());
					wuTxnSummaryView.setType(list[2].toString());
					
					wuTxnSummaryView.setUnsettledAmount(new BigDecimal(list[3].toString()));
					wuTxnSummaryView.setSettledAmount(new BigDecimal(list[4].toString()));
					wuTxnSummaryView.setUnsettledCount(new BigDecimal(list[5].toString()));
					wuTxnSummaryView.setSettledCount(new BigDecimal(list[6].toString()));
					
					listSummary.add(wuTxnSummaryView);				
				}
			}
		}
		return listSummary;		
	}


}
