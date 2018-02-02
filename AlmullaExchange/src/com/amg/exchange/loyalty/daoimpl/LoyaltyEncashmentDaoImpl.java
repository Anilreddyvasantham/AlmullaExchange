package com.amg.exchange.loyalty.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;


import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;

import com.amg.exchange.loyalty.dao.ILoyaltyEncashmentDao;
import com.amg.exchange.loyalty.model.LoyaltyMasterEncashment;

import com.amg.exchange.util.Constants;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class LoyaltyEncashmentDaoImpl extends CommonDaoImpl implements ILoyaltyEncashmentDao {

	  @Override
	  public void saveOrUpdate(LoyaltyMasterEncashment loyaltyMaster) {
		 getSession().saveOrUpdate(loyaltyMaster);
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> getViewAllDetails() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyMasterEncashment.class, "loyaltyMasterEncashment");
		    criteria.setFetchMode("loyaltyMasterEncashment.applicationCountryId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyMasterEncashment.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyMasterEncashment> lstLoyaltyMasterEncashment = (List<LoyaltyMasterEncashment>) findAll(criteria);
		    return lstLoyaltyMasterEncashment;
	  }

	  @Override
	  public void deleteRecordPermentelyFromLoyaltyEncashment(BigDecimal encashmentPk) {
		    LoyaltyMasterEncashment loyaltyMasterEncashment = (LoyaltyMasterEncashment) getSession().get(LoyaltyMasterEncashment.class, encashmentPk);
		    getSession().delete(loyaltyMasterEncashment);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal encashmentPk, String remarks, String userName) {
		    LoyaltyMasterEncashment loyaltyMasterEncashment = (LoyaltyMasterEncashment) getSession().get(LoyaltyMasterEncashment.class, encashmentPk);
		    loyaltyMasterEncashment.setRemarks(remarks);
		    loyaltyMasterEncashment.setModifiedBy(userName);
		    loyaltyMasterEncashment.setModifiedDate(new Date());
		    loyaltyMasterEncashment.setApprovedBy(null);
		    loyaltyMasterEncashment.setApprovedDate(null);
		    loyaltyMasterEncashment.setIsActive(Constants.D);
		    getSession().update(loyaltyMasterEncashment);
	  }

	  @Override
	  public void DeActiveRecordToPendingForApprovalOfLoyaltyEncashment(BigDecimal encashmentPk, String userName) {
		    LoyaltyMasterEncashment loyaltyMasterEncashment = (LoyaltyMasterEncashment) getSession().get(LoyaltyMasterEncashment.class, encashmentPk);
		    loyaltyMasterEncashment.setModifiedBy(userName);
		    loyaltyMasterEncashment.setModifiedDate(new Date());
		    loyaltyMasterEncashment.setApprovedBy(null);
		    loyaltyMasterEncashment.setApprovedDate(null);
		    loyaltyMasterEncashment.setRemarks(null);
		    loyaltyMasterEncashment.setIsActive(Constants.U);
		    getSession().update(loyaltyMasterEncashment);
	  }

	  @Override
	  public List<String> toFetchAllLoyaltyPoinsList(String query) {
		    List<String> rtnList = new ArrayList<String>();
		    String sql = "select  * from EX_LTY_MST_ENCASHMENT companymas0_ where companymas0_.POINTS like '" + query + "%'";
		    SQLQuery ss = getSession().createSQLQuery(sql);
		    List<LoyaltyMasterEncashment> lstLoyaltyMasterEncashment = ss.addEntity(LoyaltyMasterEncashment.class).list();
		    for (LoyaltyMasterEncashment loyaltyMasterEncashment : lstLoyaltyMasterEncashment) {
			      rtnList.add(loyaltyMasterEncashment.getPoints().toPlainString());
		    }

		    return rtnList;
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> toCompareThepointValues(BigDecimal points) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyMasterEncashment.class, "loyaltyMasterEncashment");
			criteria.add(Restrictions.eq("loyaltyMasterEncashment.points", points));
			criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			List<LoyaltyMasterEncashment> lstEncashments = (List<LoyaltyMasterEncashment>) findAll(criteria);
			return lstEncashments;
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> toFetchAllApprovalDetails() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(LoyaltyMasterEncashment.class, "loyaltyMasterEncashment");
		    criteria.setFetchMode("loyaltyMasterEncashment.applicationCountryId", FetchMode.JOIN);
		    criteria.createAlias("loyaltyMasterEncashment.applicationCountryId", "applicationCountryId", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("loyaltyMasterEncashment.isActive", Constants.U));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<LoyaltyMasterEncashment> lstLoyaltyMasterEncashment = (List<LoyaltyMasterEncashment>) findAll(criteria);
		    return lstLoyaltyMasterEncashment;
	  }

	  @Override
	  public String toFetchAllApprovalDetails(BigDecimal encashmentPk, String userName) {
		    String approvalMsg;
		    LoyaltyMasterEncashment loyaltyMasterEncashment = (LoyaltyMasterEncashment) getSession().get(LoyaltyMasterEncashment.class, encashmentPk);
		    String approvalUser = loyaltyMasterEncashment.getApprovedBy();
		    if (approvalUser == null) {
			      loyaltyMasterEncashment.setIsActive(Constants.Yes);
			      loyaltyMasterEncashment.setApprovedBy(userName);
			      loyaltyMasterEncashment.setApprovedDate(new Date());
			      getSession().update(loyaltyMasterEncashment);
			      approvalMsg = "Success";
		    } else {
			      approvalMsg = "Fail";
		    }
		    return approvalMsg;
	  }

}
