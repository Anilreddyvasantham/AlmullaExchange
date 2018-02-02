package com.amg.exchange.intercompany.daoImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.intercompany.dao.IntraCompanyDao;
import com.amg.exchange.intercompany.model.IntraTrnxModel;
import com.amg.exchange.miscellaneous.model.Payment;
import com.amg.exchange.miscellaneous.model.PaymentDetail;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Repository
public class IntraComapnyDaoImpl extends CommonDaoImpl implements IntraCompanyDao {
	
	Logger LOGGER = Logger.getLogger(IntraComapnyDaoImpl.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Override
	public List<IntraTrnxModel> fetchIntraTrnxBasedonCountry(String countryName) {
		LOGGER.info("Entering into fetchIntraTrnxBasedonCountry(BigDecimal countryId) Method  STARTED ");
		LOGGER.info(" countryName = " + countryName);
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(IntraTrnxModel.class, "intraTrnxModel");
		detachedCriteria.add(Restrictions.eq("intraTrnxModel.sendCountryName", countryName));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<IntraTrnxModel> lstIntraTrnx  = (List<IntraTrnxModel>) findAll(detachedCriteria);
		LOGGER.info("Exit from  fetchIntraTrnxBasedonCountry(String identityType) method");
		return lstIntraTrnx;
	}
	
	@Override
	public void saveRecords(HashMap<String, Object> saveRecords) throws AMGException {

		ReceiptPayment receiptPayment = (ReceiptPayment)saveRecords.get("RECEIPTPAYMENT");
		String documentCode = (String)saveRecords.get("DOCUMENTCODE");
		String userName = (String)saveRecords.get("USERNAME");
		Payment payment = (Payment)saveRecords.get("PAYMENT");
		List<ForeignCurrencyAdjust> foreignCurrencyAdjusRefundtList = (List<ForeignCurrencyAdjust>)saveRecords.get("FOREIGNCURRENCYADJUSTREFUND");
		List<ForeignCurrencyAdjust> foreignCurrencyAdjusDenomtList = (List<ForeignCurrencyAdjust>)saveRecords.get("FOREIGNCURRENCYADJUSTDENOMINATION");

		//update Receipt Payment record
		if(receiptPayment !=null && documentCode!=null && userName!=null){
			getSession().saveOrUpdate(receiptPayment);
		}

		//save Payment Table
		if(payment!=null){
			getSession().saveOrUpdate(payment);
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

}
