package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.bean.SpecifySellingRateDataTable;
import com.amg.exchange.treasury.dao.ISpecifySellingRateDao;
import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.util.SessionStateManage;

@Repository
 class SpecifySellingRateDaoImpl<T> extends CommonDaoImpl<T> implements ISpecifySellingRateDao<T> {

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerSpecialDealRequest> getSpecialDealData(BigDecimal appcountryId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerSpecialDealRequest.class, "customerSpecialDealRequest");
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqBankMaster", "customerSpeacialDealReqBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCurrencyMaster", "customerSpeacialDealReqCurrencyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customerSpecialDealRequest.customerSpeacialDealReqCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.customerSpeacialDealReqCustomer", "customerSpeacialDealReqCustomer", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("customerSpecialDealRequest.applicationCountryCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("customerSpecialDealRequest.applicationCountryCountryMaster", "applicationCountryCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "applicationCountryCountryMaster.countryId", appcountryId ));
		
		dCriteria.add(Restrictions.isNotNull("approvedBy"));
		dCriteria.add(Restrictions.isNotNull("approvedDate"));
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date validUpto = null;
		try {
			validUpto = formatter.parse(formatter.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dCriteria.add(Restrictions.ge("customerSpecialDealRequest.validUpto",validUpto));
		dCriteria.addOrder(Order.desc("customerSpecialDealRequest.documentNumber"));
		//dCriteria.add(Restrictions.isNull("dealId"));
		//dCriteria.add(Restrictions.isNotNull("dealId"));
 		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<CustomerSpecialDealRequest>)findAll(dCriteria);
	}

	@Override
	public void saveSpecialCustomer(CustomerSpecialDealRequest customerSpecial){
		getSession().update(customerSpecial);
		
		
	}

	@Override
	public void updateApprove(List<SpecifySellingRateDataTable> listUpdate, String userName) {
		for (SpecifySellingRateDataTable specifySellingRateDataTable : listUpdate) {
			CustomerSpecialDealRequest customerSpecialDealRequest = (CustomerSpecialDealRequest)getSession().get(CustomerSpecialDealRequest.class, specifySellingRateDataTable.getPk());
			customerSpecialDealRequest.setFundingOption(specifySellingRateDataTable.getFundingOption());
			customerSpecialDealRequest.setBuyRate(specifySellingRateDataTable.getBuyRate()==null ? new BigDecimal(0) : specifySellingRateDataTable.getBuyRate().setScale(5, 4));
			customerSpecialDealRequest.setSellRate(specifySellingRateDataTable.getSellRate()==null ? new BigDecimal(0) : specifySellingRateDataTable.getSellRate().setScale(5,4));	
			customerSpecialDealRequest.setModifiedBy(userName);
			customerSpecialDealRequest.setModifiedDate(new Date());
			customerSpecialDealRequest.setAvarageRate(specifySellingRateDataTable.getAvarageRate()==null ? new BigDecimal(0) : specifySellingRateDataTable.getAvarageRate().setScale(5,4));
			
			getSession().update(customerSpecialDealRequest);
		
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getBuyRate(BigDecimal customerSpecialDealReqId,BigDecimal bankId,BigDecimal currencyId,String lineType){
    DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster", "treasuryDealBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster", "treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.customerSpecialDealRequest", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.customerSpecialDealRequest", "customerSpecialDealRequest", JoinType.INNER_JOIN);
		
		//dCriteria.setFetchMode("treasuryDealDetail.lineType", FetchMode.JOIN);
		//dCriteria.createAlias("treasuryDealDetail.lineType", "", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "customerSpecialDealRequest.customerSpecialDealReqId", customerSpecialDealReqId ));
		dCriteria.add(Restrictions.eq( "treasuryDealBankMaster.bankId", bankId ));
		dCriteria.add(Restrictions.eq( "treasuryDealDetailCurrencyMaster.currencyId", currencyId));
		dCriteria.add(Restrictions.eq( "treasuryDealDetail.lineType",  lineType));
		
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		//dCriteria.setProjection(Projections.property("localExchangeRate"));
		return (List<TreasuryDealDetail>)findAll(dCriteria);
		
	

		}
	@SuppressWarnings("unchecked")
	@Override
	public List<TreasuryDealDetail> getSellRate(BigDecimal bankId,BigDecimal currencyId,String lineType){
    DetachedCriteria dCriteria = DetachedCriteria.forClass(TreasuryDealDetail.class, "treasuryDealDetail");
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealBankMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealBankMaster", "treasuryDealBankMaster", JoinType.INNER_JOIN);
		
		dCriteria.setFetchMode("treasuryDealDetail.treasuryDealDetailCurrencyMaster", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.treasuryDealDetailCurrencyMaster", "treasuryDealDetailCurrencyMaster", JoinType.INNER_JOIN);

		dCriteria.setFetchMode("treasuryDealDetail.customerSpecialDealRequest", FetchMode.JOIN);
		dCriteria.createAlias("treasuryDealDetail.customerSpecialDealRequest", "customerSpecialDealRequest", JoinType.INNER_JOIN);
		
		//dCriteria.setFetchMode("treasuryDealDetail.lineType", FetchMode.JOIN);
		//dCriteria.createAlias("treasuryDealDetail.lineType", "", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq( "treasuryDealBankMaster.bankId", bankId ));
		dCriteria.add(Restrictions.eq( "treasuryDealDetailCurrencyMaster.currencyId", currencyId));
		dCriteria.add(Restrictions.eq( "treasuryDealDetail.lineType",  lineType));
		//dCriteria.add(Restrictions.isNotNull("customerSpecialDealRequest.customerSpecialDealReqId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		//dCriteria.setProjection(Projections.property("localExchangeRate"));
		return (List<TreasuryDealDetail>)findAll(dCriteria);
		
	

		}
	
}
