package com.amg.exchange.blacklist.daoImpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.blacklist.dao.IBlackListDao;
import com.amg.exchange.blacklist.model.BlackListDetails;
import com.amg.exchange.blacklist.model.BlackListMaster;
import com.amg.exchange.blacklist.model.BlackListView;
import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.IdentityTypeMaster;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository
public class BlackListDaoImpl extends CommonDaoImpl implements IBlackListDao {

	  @Override
	  public List<BlackListView> toFetchOnFormLoadingForAllViewData() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(BlackListView.class);
		    List<BlackListView> lstBlackListViews = (List<BlackListView>) findAll(criteria);
		    return lstBlackListViews;
	  }

	  @Override
	  public List<BlackListView> toFetchOnFormLoadingForAllViewData(BigDecimal idnumber) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(BlackListView.class, "blackListView");
		    criteria.add(Restrictions.eq("blackListView.idNo", idnumber));
		    List<BlackListView> lstBlackListViews = (List<BlackListView>) findAll(criteria);
		    return lstBlackListViews;
	  }

	  @Override
	  public List<IdentityTypeMaster> toFetchIdentityTypeTypeBasedOnApplicatonCountryId(BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(IdentityTypeMaster.class, "identityTypeMaster");
		    /*criteria.setFetchMode("identityTypeMaster.languageId", FetchMode.JOIN);
		    criteria.createAlias("identityTypeMaster.languageId", "languageId", JoinType.INNER_JOIN);*/
		    criteria.add(Restrictions.eq("identityTypeMaster.applicationCountryId", countryId));
		    List<IdentityTypeMaster> lstIdentityTypeMaster = (List<IdentityTypeMaster>) findAll(criteria);
		    return lstIdentityTypeMaster;
	  }

	  @Override
	  public BigDecimal tofetchBlackListPkBasedOnEnglishName(String englishName) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(BlackListMaster.class, "blackListMaster");
		    criteria.add(Restrictions.eq("blackListMaster.fullName", englishName));
		    List<BlackListMaster> lstBlackListMaster = (List<BlackListMaster>) findAll(criteria);
		    return lstBlackListMaster.get(0).getBlackListMasterId();
	  }

	  @Override
	  public void saveBlackListMaster(BlackListMaster blackListMaster) {
		   getSession().saveOrUpdate(blackListMaster);
	  }

	  @Override
	  public void saveBlackListDetails(BlackListDetails blackListDetails) {
		    getSession().saveOrUpdate(blackListDetails);   
	  }

	  @Override
	  public void saveBlackListDetailsAndBlackListMaster(BlackListDetails blackListDetails, BigDecimal blackMasterPk) {
		  try {
		    
	  } catch (Exception e) {
		   e.printStackTrace();
	  }
	  }

}
