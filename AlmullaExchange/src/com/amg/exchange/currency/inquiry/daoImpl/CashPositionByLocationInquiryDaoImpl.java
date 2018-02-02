package com.amg.exchange.currency.inquiry.daoImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.currency.inquiry.dao.ICashPositionByLocationInquiryDao;
import com.amg.exchange.currency.inquiry.model.CashDetailsView;
import com.amg.exchange.currency.inquiry.model.CashPositionByLocationView;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.Constants;
@Repository
@SuppressWarnings("serial")
public class CashPositionByLocationInquiryDaoImpl<T> extends CommonDaoImpl<T> implements Serializable, ICashPositionByLocationInquiryDao {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public List<CountryBranch> getCountryBranchList(BigDecimal appCountryId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", appCountryId));
		criteria.add(Restrictions.eq("countryBranch.isActive", Constants.Yes));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<CashPositionByLocationView> getCashBalanceList(BigDecimal branchId) {
		
		System.out.println("Branch Id ======== > "+branchId);
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CashPositionByLocationView.class, "cashPositionByLocationView");

		
		criteria.add(Restrictions.eq("cashPositionByLocationView.branchId", branchId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashPositionByLocationView> objList = (List<CashPositionByLocationView>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<CashPositionByLocationView> getCashBalanceBasedOnCtoBList(BigDecimal branchId,BigDecimal currencyId) {
		
		System.out.println("Branch Id ======== > "+branchId);
		System.out.println("Currency Id ======== > "+currencyId);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CashPositionByLocationView.class, "cashPositionByLocationView");

		
		criteria.add(Restrictions.eq("cashPositionByLocationView.branchId", branchId));
		
		criteria.add(Restrictions.eq("cashPositionByLocationView.currencyId", currencyId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashPositionByLocationView> objList = (List<CashPositionByLocationView>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	@Override
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierList(BigDecimal branchId,BigDecimal currencyId,String createdBy) {
		
		System.out.println("Branch Id ======== > "+branchId);
		System.out.println("Currency Id ======== > "+currencyId);
		System.out.println("createdBy ======== > "+createdBy);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CashDetailsView.class, "cashDetailsView");

		
		criteria.add(Restrictions.eq("cashDetailsView.documentBranchId", branchId));
		
		criteria.add(Restrictions.eq("cashDetailsView.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("cashDetailsView.createdBy", createdBy));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashDetailsView> objList = (List<CashDetailsView>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}
	
	@Override
	public List<CashDetailsView> getCashBalanceBasedOnCtoBandCashierandModeList(BigDecimal branchId,BigDecimal currencyId,String createdBy,String mode) {
		
		System.out.println("Branch Id ======== > "+branchId);
		System.out.println("Currency Id ======== > "+currencyId);
		System.out.println("createdBy ======== > "+createdBy);
		System.out.println("mode ======== > "+mode);
		
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CashDetailsView.class, "cashDetailsView");

		
		criteria.add(Restrictions.eq("cashDetailsView.documentBranchId", branchId));
		
		criteria.add(Restrictions.eq("cashDetailsView.currencyId", currencyId));
		
		criteria.add(Restrictions.eq("cashDetailsView.createdBy", createdBy));
		
		criteria.add(Restrictions.eq("cashDetailsView.collectionMode", mode));
		
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CashDetailsView> objList = (List<CashDetailsView>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}


}
