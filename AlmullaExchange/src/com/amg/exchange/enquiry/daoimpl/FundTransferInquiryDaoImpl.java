package com.amg.exchange.enquiry.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.enquiry.dao.IFundTransferInquiryDao;
import com.amg.exchange.enquiry.model.CashDepositInquiryModelView;
import com.amg.exchange.enquiry.model.FundTransferInquiryModelView;

@Repository
public class FundTransferInquiryDaoImpl extends CommonDaoImpl implements IFundTransferInquiryDao,Serializable{

	@SuppressWarnings("unchecked")
	@Override
	public List<FundTransferInquiryModelView> getAllFundTransferInquiry() {
		DetachedCriteria criteria = DetachedCriteria.forClass(FundTransferInquiryModelView.class,"fundTransferInquiryModelView");
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<FundTransferInquiryModelView> lstFundTranInq = findAll(criteria);
		return lstFundTranInq;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FundTransferInquiryModelView> getFundTransferInquiryBasedonFormDetails(BigDecimal currencyId, String locationName, BigDecimal docCode, String docName, BigDecimal finYear, BigDecimal docNum, Date docDate, String cashTransfer) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(FundTransferInquiryModelView.class,"fundTransferInquiryModelView");
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.currencyId", currencyId));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.fromBranchName", locationName));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.documentCode", docCode));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.documentDesc", docName));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.documentFinancialYear", finYear));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.documentNo", docNum));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.documentDate", docDate));
		dCriteria.add(Restrictions.eq("fundTransferInquiryModelView.toCashier", cashTransfer));
		dCriteria.addOrder(Order.asc("fundTransferInquiryModelView.denominationId"));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<FundTransferInquiryModelView> lstFundTranInq = findAll(dCriteria);
		return lstFundTranInq;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashDepositInquiryModelView> getAllCashDepositInquiry() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CashDepositInquiryModelView.class,"cashDepositInquiryModelView");
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CashDepositInquiryModelView> lstCashDepositInq = findAll(criteria);
		return lstCashDepositInq;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CashDepositInquiryModelView> getCashDepositInquiryBasedonFormDetails(String cashTransfer, BigDecimal currencyId, Date fromdocDate,Date todocDate, String locationName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CashDepositInquiryModelView.class,"cashDepositInquiryModelView");
		dCriteria.add(Restrictions.eq("cashDepositInquiryModelView.toCashier", cashTransfer));
		dCriteria.add(Restrictions.eq("cashDepositInquiryModelView.currencyId", currencyId));
		dCriteria.add(Restrictions.ge("cashDepositInquiryModelView.documentDate", fromdocDate));
		dCriteria.add(Restrictions.le("cashDepositInquiryModelView.documentDate", todocDate));
		dCriteria.add(Restrictions.eq("cashDepositInquiryModelView.fromBranchName", locationName));	
		dCriteria.addOrder(Order.asc("cashDepositInquiryModelView.denominationId"));
		dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<CashDepositInquiryModelView> lstCashDepositInq = findAll(dCriteria);
		return lstCashDepositInq;
	}

}
