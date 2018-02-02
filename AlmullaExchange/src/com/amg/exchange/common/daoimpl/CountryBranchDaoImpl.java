package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.ICountryBranchDao;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.loyalty.model.LoyaltyPromotionSettings;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class CountryBranchDaoImpl<T> extends CommonDaoImpl<T> implements Serializable, ICountryBranchDao<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void saveAndUpdateCountryBranch(CountryBranch countryBranch) {
		getSession().saveOrUpdate(countryBranch);

	}

	@Override
	public Boolean isCountryBranchCodeExist(BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.branchId", branchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);
		return CommonUtil.checkSizeOfRecords(objList);
	}

	@Override
	public void activateCountryBranch(BigDecimal countryBranchId, String userName) {
		CountryBranch countryBranch = (CountryBranch) getSession().get(CountryBranch.class, countryBranchId);
		countryBranch.setIsActive(Constants.U);
		countryBranch.setModifiedBy(userName);
		countryBranch.setModifiedDate(new Date());
		countryBranch.setApprovedBy(null);
		countryBranch.setApprovedDate(null);
		countryBranch.setRemarks(null);
		getSession().update(countryBranch);

	}

	@Override
	public List<String> autoCompleteList(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.like("countryBranch.branchId", query, MatchMode.ANYWHERE).ignoreCase());
		criteria.setProjection(Projections.property("countryBranch.branchId"));
		criteria.addOrder(Order.asc("countryBranch.branchId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<String> lstCountryBranchCode = (List<String>) findAll(criteria);
		return lstCountryBranchCode;
	}

	@Override
	public void deleteCountryBranch(BigDecimal countryBranchId) {
		CountryBranch countryBranch = (CountryBranch) getSession().get(CountryBranch.class, countryBranchId);
		getSession().delete(countryBranch);

	}

	@Override
	public List<CountryBranch> displayAllCountryBranchToView(BigDecimal appCountryId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", appCountryId));

		criteria.addOrder(Order.desc("countryBranch.branchId"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);

		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<CountryBranch> displayCountryBranchBasedOnCountryBranchCode(BigDecimal branchId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.branchId", branchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public void approveRecord(BigDecimal countryBranchId, String userName, String isActive) {
		CountryBranch countryBranch = (CountryBranch) getSession().get(CountryBranch.class, countryBranchId);
		countryBranch.setIsActive(isActive);
		countryBranch.setApprovedBy(userName);
		countryBranch.setApprovedDate(new Date());
		getSession().update(countryBranch);
	}

	@Override
	public List<CountryBranch> displayCountryBranchForApprove(BigDecimal appCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.setFetchMode("countryBranch.countryMaster", FetchMode.JOIN);
		criteria.createAlias("countryBranch.countryMaster", "countryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("countryMaster.countryId", appCountryId));
		criteria.add(Restrictions.eq("countryBranch.isActive", Constants.U));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		criteria.getExecutableCriteria(getSession()).setCacheable(true);
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);
		if (objList.isEmpty())
			return null;
		return objList;
	}

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal countryBranchId, String remarks, String userName) {
		    CountryBranch countryBranch = (CountryBranch) getSession().get(CountryBranch.class, countryBranchId);
		    countryBranch.setRemarks(remarks);
		    countryBranch.setModifiedBy(userName);
		    countryBranch.setModifiedDate(new Date());
		    countryBranch.setApprovedBy(null);
		    countryBranch.setApprovedDate(null);
		    countryBranch.setIsActive(Constants.D);
		    getSession().update(countryBranch);
	  }

	  @Override
	  public String checkCountryBranchApproveMultiUser(BigDecimal countryBranchId, String userName) {
		    String approvalMsg;
		    CountryBranch countryBranch = (CountryBranch) getSession().get(CountryBranch.class, countryBranchId);
		    String approvalUser = countryBranch.getApprovedBy();
		    if (approvalUser == null) {
			      countryBranch.setIsActive(Constants.Yes);
			      countryBranch.setApprovedBy(userName);
			      countryBranch.setApprovedDate(new Date());
			      getSession().update(countryBranch);
			      approvalMsg = "Success";
		    } else {
			      approvalMsg = "Fail";
		    }
		    return approvalMsg;
	  }

	  @Override
	  public List<String> toFetchAllCountryBranchCode(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
			criteria.add(Restrictions.like("countryBranch.branchId", query, MatchMode.ANYWHERE).ignoreCase());
			criteria.setProjection(Projections.property("countryBranch.branchId"));
			criteria.addOrder(Order.asc("countryBranch.branchId"));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<String> lstcountryBranch = (List<String>) findAll(criteria);
			return lstcountryBranch;
	  }

	  @Override
	  public List<CountryBranch> toCompareBranchCode(BigDecimal branchCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
			criteria.add(Restrictions.eq("countryBranch.branchId", branchCode));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<CountryBranch> lstCountryBranch = (List<CountryBranch>) findAll(criteria);
			return lstCountryBranch;
	  }

}
