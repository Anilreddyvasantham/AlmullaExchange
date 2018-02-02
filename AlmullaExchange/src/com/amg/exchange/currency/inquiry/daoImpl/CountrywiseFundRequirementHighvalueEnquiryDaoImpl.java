package com.amg.exchange.currency.inquiry.daoImpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.currency.inquiry.dao.ICountrywiseFundRequirementHighvalueEnquiryDao;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.sun.xml.rpc.processor.modeler.j2ee.xml.javaIdentifierType;

@Repository
public class CountrywiseFundRequirementHighvalueEnquiryDaoImpl<T> extends CommonDaoImpl<T> implements ICountrywiseFundRequirementHighvalueEnquiryDao{
	@Override
	public List<CustomerSpecialDealRequest> getCustomerDealRequest(BigDecimal applicationCountryId,Date requestDate, BigDecimal bankCountryId,String option){
		//objList =  null;TO_CHAR(sysdate, 'MM/DD/YYYY'),
		//CREATED_DATE =to_date(to_char('"+sysdate+"','dd/MM/yy'),'dd/MM/yy')"
	//	java.sql.Date sqlDate = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		
		//SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		//if(requestDate != null){
		//String sysdate=dateformat.format(requestDate);
		java.sql.Date sqlDate = new java.sql.Date(requestDate.getTime());//"PROJECTION_DATE =to_date('"+sysdate+"','dd/MM/yy')";
		//}
		criteria.setFetchMode("customerSpecialDealRequest.applicationCountryCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.applicationCountryCountryMaster", "applicationCountryCountryMaster",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("applicationCountryCountryMaster.countryId", applicationCountryId));
		
		if(bankCountryId != null){
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster", FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCountryMaster", "customerSpeacialDealReqCountryMaster",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerSpeacialDealReqCountryMaster.countryId", bankCountryId));
		}
		
		/*if(sqlDate!= null)
			criteria.add(Restrictions.sqlRestriction(sqlDate));
		*/
		if(requestDate!=null)
			criteria.add(Restrictions.eq("customerSpecialDealRequest.projectionDate",sqlDate));
		
		if(option != null)
			criteria.add(Restrictions.eq("customerSpecialDealRequest.fundingOption",option));
		
		List<CustomerSpecialDealRequest> objList = (List<CustomerSpecialDealRequest>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
		
		
		
		
	}
	
	@Override
	public List<RemittanceTransaction> getRemittanceDetails(BigDecimal applicationCountryId,BigDecimal bankId, BigDecimal bankCountryId,BigDecimal customerId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceTransaction.class, "remittanceTransaction");
		
		criteria.setFetchMode("remittanceTransaction.applicationCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.applicationCountryId", "applicationCountryId",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("applicationCountryId.countryId", applicationCountryId));
		
		criteria.setFetchMode("remittanceTransaction.bankCountryId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.bankCountryId", "bankCountryId",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankCountryId.countryId", bankCountryId));
		
		criteria.setFetchMode("remittanceTransaction.bankId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.bankId", "bankId",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("bankId.bankId", bankId));
		
		criteria.setFetchMode("remittanceTransaction.customerId", FetchMode.JOIN);
		criteria.createAlias("remittanceTransaction.customerId", "customerId",  JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerId.customerId", customerId));
		
		List<RemittanceTransaction> objList = (List<RemittanceTransaction>) findAll(criteria);
		
		if(objList.isEmpty())
			return null;
		
		return objList;
		
	}
	@Override
	public BankMaster getBankDetails(BigDecimal bankId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		List<BankMaster> objList = (List<BankMaster>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList.get(0);

	}
	
	@Override
	public CurrencyMaster getCurrencyDetails(BigDecimal currencyId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class, "currencyMaster");
		
		

		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));

		List<CurrencyMaster> objList = (List<CurrencyMaster>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList.get(0);

	}
	@Override
	public CountryMaster getCountryDetails(BigDecimal countryId){
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryMaster.class, "countryMaster");

		criteria.add(Restrictions.eq("countryMaster.countryId", countryId));

		List<CountryMaster> objList = (List<CountryMaster>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList.get(0);
	}
	@Override
	public TreasuryDealHeader getTreasuryDealDetails(BigDecimal dealDocumentNo, BigDecimal DealDocumentFinanceYr){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(TreasuryDealHeader.class, "treasuryDealHeader");
		
		
		criteria.add(Restrictions.eq("treasuryDealHeader.treasuryDocumentNumber", dealDocumentNo));
		criteria.add(Restrictions.eq("treasuryDealHeader.userFinanceYear", DealDocumentFinanceYr));


		List<TreasuryDealHeader> objList = (List<TreasuryDealHeader>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList.get(0);
	}

}
