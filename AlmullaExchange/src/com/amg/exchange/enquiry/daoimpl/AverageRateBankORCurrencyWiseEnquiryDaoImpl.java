package com.amg.exchange.enquiry.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.enquiry.dao.IAverageRateBankORCurrencyWiseEnquiryDao;
import com.amg.exchange.enquiry.model.BankAccountDetailsView;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.util.Constants;

@Repository
public class AverageRateBankORCurrencyWiseEnquiryDaoImpl extends CommonDaoImpl implements IAverageRateBankORCurrencyWiseEnquiryDao{

	@Override
	public List<BankAccountDetails> getallBanksFromBankAccountDetailsList(BigDecimal countryId,BigDecimal bankId) {
		
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountDetails.class,"bankAccountDetails");
		
		criteria.setFetchMode("bankAccountDetails.exBankMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.exBankMaster","exBankMaster", JoinType.INNER_JOIN);
		if(bankId!=null){
		criteria.add(Restrictions.eq("exBankMaster.bankId", bankId));
		}
		criteria.add(Restrictions.eq("exBankMaster.recordStatus", Constants.Yes));
		
		criteria.setFetchMode("bankAccountDetails.fsCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.fsCurrencyMaster","fsCurrencyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCurrencyMaster.isactive", Constants.Yes));
		
		criteria.setFetchMode("bankAccountDetails.fsCountryMaster",FetchMode.JOIN);
		criteria.createAlias("bankAccountDetails.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		if(countryId!=null){
		criteria.add(Restrictions.eq("fsCountryMaster.countryId",  countryId));
		}
		criteria.add(Restrictions.eq("bankAccountDetails.recordStatus", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountDetails>) findAll(criteria);
	}
	
	
	
	@Override
	public List<BankAccountDetailsView> getBankAccountDeatailsView(BigDecimal bankId,BigDecimal currencyId){
		
		DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountDetailsView.class,"bankAccountDetailsView");
		
		if(bankId!=null){
			criteria.add(Restrictions.eq("bankAccountDetailsView.bankId", bankId));
		}
		if(currencyId!=null){
			criteria.add(Restrictions.eq("bankAccountDetailsView.currencyId", currencyId));
		}
		 
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountDetailsView>) findAll(criteria);
	}

	
	@Override
	public List<CustomerSpecialDealRequest> getCustomerRecords(BigDecimal bankID, BigDecimal currencnyId){
		
		DetachedCriteria criteria=DetachedCriteria.forClass(CustomerSpecialDealRequest.class,"customerSpecialDealRequest");
		

		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster",FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", "customerSpeacialDealReqBankMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster",FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", "customerSpeacialDealReqCurrencyMaster",JoinType.INNER_JOIN);
		
		criteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer",FetchMode.JOIN);
		criteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer",JoinType.INNER_JOIN);
		
		criteria.add(Restrictions.eq("customerSpeacialDealReqBankMaster.bankId", bankID));
		criteria.add(Restrictions.eq("customerSpeacialDealReqCurrencyMaster.currencyId", currencnyId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<CustomerSpecialDealRequest>) findAll(criteria);
	}



	@Override
	public List<BankAccountDetailsView> getBankAccountDeatailsView(
			BigDecimal bankId, BigDecimal currencyId, BigDecimal countryId) {
		 
	DetachedCriteria criteria=DetachedCriteria.forClass(BankAccountDetailsView.class,"bankAccountDetailsView");
		
		if(bankId!=null){
			criteria.add(Restrictions.eq("bankAccountDetailsView.bankId", bankId));
		}
		if(currencyId!=null){
			criteria.add(Restrictions.eq("bankAccountDetailsView.currencyId", currencyId));
		}
		if(countryId!=null){
			criteria.add(Restrictions.eq("bankAccountDetailsView.countryId", countryId));
		}
		 
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<BankAccountDetailsView>) findAll(criteria);
	}
}
