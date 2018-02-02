package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.dao.IStateMasterDao;
import com.amg.exchange.treasury.model.DeliveryModeDesc;

@SuppressWarnings("unchecked")
@Repository
public class StateMasterDaoImpl<T> extends CommonDaoImpl<T> implements IStateMasterDao<T> {
	  private static final Logger log = Logger.getLogger(StateMasterDaoImpl.class);

	  @Override
	  public List<String> getStateListCode(String query) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    criteria.add(Restrictions.like("stateMaster.stateCode", query, MatchMode.START).ignoreCase());
		    criteria.setProjection(Projections.property("stateMaster.stateCode"));
		    criteria.addOrder(Order.asc("stateMaster.stateCode"));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<String>) findAll(criteria);
	  }

	  @Override
	  public void saveRecord(StateMaster stateMaster, StateMasterDesc stateMasterDes1, StateMasterDesc stateMasterDes2) {
		    try {
			      getSession().saveOrUpdate(stateMaster);
			      getSession().saveOrUpdate(stateMasterDes1);
			      getSession().saveOrUpdate(stateMasterDes2);
		    } catch (Exception exception) {
			      log.error("Error Occured While child description time  :" + exception.getMessage());
		    }
	  }

	  @Override
	  public List<StateMaster> getStateList(String stateCode,BigDecimal countryId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    detachedCriteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    detachedCriteria.add(Restrictions.eq("stateMaster.stateCode", stateCode));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<StateMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<StateMasterDesc> getstateDescList(BigDecimal stateId) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    detachedCriteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    detachedCriteria.add(Restrictions.eq("fsStateMaster.stateId", stateId));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<StateMasterDesc>) findAll(detachedCriteria);

	  }

	  @Override
	  public List<StateMasterDesc> getAllStateList() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    criteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    // criteria.add(Restrictions.ne("fsStateMaster.isActive",
		    // "N"));
		    return (List<StateMasterDesc>) findAll(criteria);

	  }

	  @Override
	  public void save(StateMaster stateMaster) {
		    getSession().saveOrUpdate(stateMaster);

	  }

	  @Override
	  public void saverRecordDesc(StateMasterDesc stateMasterDes1) {
		    getSession().saveOrUpdate(stateMasterDes1);

	  }

	  @Override
	  public void deleteRecordDesc(StateMasterDesc stateMasterDes1) {
		    getSession().delete(stateMasterDes1);
	  }

	  @Override
	  public void delete(StateMaster stateMaster) {
		    getSession().delete(stateMaster);
	  }

	  @Override
	  public void approvedRecord(BigDecimal stateId, String userName) {
		    StateMaster stateMaster = (StateMaster) getSession().get(StateMaster.class, stateId);
		    stateMaster.setIsActive("Y");
		    stateMaster.setApprovedBy(userName);
		    stateMaster.setApprovedDate(new Date());
		    getSession().update(stateMaster);

	  }

	  @Override
	  public List<StateMasterDesc> getAllStateApproveList() {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		   /* criteria.setFetchMode("stateMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);*/
		    criteria.add(Restrictions.eq("fsStateMaster.isActive", "U"));
		    return (List<StateMasterDesc>) findAll(criteria);
	  }

	  @Override
	  public List<CountryMasterDesc> getAllCountryMasterList(BigDecimal languageId, BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		    criteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    criteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    return (List<CountryMasterDesc>) findAll(criteria);
	  }

	  @Override
	  public void saveRecordForFileupload(StateMaster stateMaster) {
		    getSession().saveOrUpdate(stateMaster);
	  }

	  @Override
	  public void saveRecordForFileUploadDesc(StateMasterDesc stateMasterDes1) {
		    getSession().saveOrUpdate(stateMasterDes1);
	  }

	  @Override
	  public List<StateMaster> getstateMasterList(String stateCode) {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    detachedCriteria.add(Restrictions.eq("stateMaster.stateCode", stateCode));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<StateMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public List<StateMaster> getAllStateMasterList() {
		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    detachedCriteria.setFetchMode("stateMaster.fsStateMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    //detachedCriteria.add(Restrictions.ne("stateMaster.isActive", "N"));
		    detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<StateMaster>) findAll(detachedCriteria);
	  }

	  @Override
	  public String approvRecord(BigDecimal stateId, String userName) {
		    String approveMsg;
		    StateMaster stateMaster = (StateMaster) getSession().get(StateMaster.class, stateId);
		    String approvedUser = stateMaster.getApprovedBy();
		    if (approvedUser == null) {
			      stateMaster.setIsActive("Y");
			      stateMaster.setApprovedBy(userName);
			      stateMaster.setApprovedDate(new Date());
			      getSession().update(stateMaster);
			      approveMsg = "Success";
		    } else {
			      approveMsg = "Fail";
		    }
		    return approveMsg;
	  }

	  @Override
	  public List<String> getStateListCode(String query, BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");

		    criteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));

		    criteria.add(Restrictions.like("stateMaster.stateCode", query, MatchMode.ANYWHERE).ignoreCase());
		    criteria.setProjection(Projections.property("stateMaster.stateCode"));
		    criteria.addOrder(Order.asc("stateMaster.stateCode"));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<String>) findAll(criteria);
	  }

	  @Override
	  public List<StateMasterDesc> getAllStateList(BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    criteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    //criteria.add(Restrictions.ne("fsStateMaster.isActive", "N"));
		    return (List<StateMasterDesc>) findAll(criteria);
	  }

	  @Override
	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("stateMasterDesc.stateName", englishStateName));
		    List<StateMasterDesc> lstStateMasterDescs=(List<StateMasterDesc>) findAll(criteria);
		    if(lstStateMasterDescs.size() !=0){
			     // BigDecimal countryId=toCheckState(lstStateMasterDescs.get(0).getStateDescId());
			      return lstStateMasterDescs;      
		    }else{
			      return null;
		    }
	  }

	  private BigDecimal toCheckState(BigDecimal stateId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    criteria.add(Restrictions.eq("stateMaster.stateId", stateId));
		    /*criteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));*/
		    List<StateMaster> list = (List<StateMaster>) findAll(criteria);
		    if(list != null && list.size() !=0){
			      return  list.get(0).getFsCountryMaster().getCountryId();   
		    }else{
			return null;      
		    }
		   
		    
	  }

	  @Override
	  public List<StateMaster> toFetchDetailsForView(BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    criteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    return (List<StateMaster>) findAll(criteria);
	  }

	  @Override
	  public List<StateMaster> toCheckBasedOnCountryAndStateCode(BigDecimal countryId, String stateCode) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    criteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    criteria.add(Restrictions.eq("stateMaster.stateCode", stateCode));
		    return (List<StateMaster>) findAll(criteria);
	  }

	  

	  @Override
	  public List<StateMasterDesc> toFetchStateDesc(String englishStateName, BigDecimal countryId) {
		    /*DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster",FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster","fsStateMaster", JoinType.INNER_JOIN);
		     
		    criteria.setFetchMode("stateMasterDesc.fsCountryMaster",FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsCountryMaster","fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    criteria.add(Restrictions.eq("stateMasterDesc.stateName", englishStateName));

		    log.info("Exited from  getAllActiveInActive() method");

		    return (List<StateMasterDesc>) findAll(criteria);*/
		    List<StateMasterDesc> returnValue = null;
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("stateMasterDesc.stateName", englishStateName));
		    List<StateMasterDesc> lstStateMasterDescs=(List<StateMasterDesc>) findAll(criteria);
		    if(lstStateMasterDescs.size() !=0){
			      for(StateMasterDesc stateMasObj :lstStateMasterDescs){
			      BigDecimal countryMId=toCheckState(stateMasObj.getFsStateMaster().getStateId());
			      if(countryMId != null && countryMId.equals(countryId)){
					returnValue = lstStateMasterDescs;  
			      	}
			      }
			      
		    }else{
			      returnValue = null;	      
		    }
		    
		    
		    return returnValue;	      
		    
	  }

	  @Override
	  public List<StateMaster> toFetchForApprovalView(BigDecimal countryId) {
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMaster.class, "stateMaster");
		    criteria.setFetchMode("stateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    criteria.add(Restrictions.eq("stateMaster.isActive", "U"));
		    criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		    List<StateMaster> lstStateMasters=(List<StateMaster>) findAll(criteria);
		    return lstStateMasters;
	  }

	  @Override
	  public List<StateMasterDesc> toFetchAllStateDescBasedOnDesc(String englishStateName, BigDecimal countryId) {
		    List<StateMasterDesc> returnValue = null;
		    DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("stateMasterDesc.stateName", englishStateName));
		    List<StateMasterDesc> lstStateMasterDescs=(List<StateMasterDesc>) findAll(criteria);
		    if(lstStateMasterDescs.size() !=0){
			      for(StateMasterDesc stateMasObj :lstStateMasterDescs){
			      BigDecimal countryMId=toCheckState(stateMasObj.getFsStateMaster().getStateId());
			      if(countryMId != null && countryMId.equals(countryId)){
					returnValue = lstStateMasterDescs;  
			      	}
			      }
			      
		    }else{
			      returnValue = null;	      
		    }
		    
		    
		    return returnValue;
	  }

	@Override
	public List<StateMasterDesc> getAllStateListBasedOnLanguae(BigDecimal languageId, BigDecimal countryId) {
		  DetachedCriteria criteria = DetachedCriteria.forClass(StateMasterDesc.class, "stateMasterDesc");
		    criteria.setFetchMode("stateMasterDesc.fsLanguageType", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		    criteria.setFetchMode("stateMasterDesc.fsStateMaster", FetchMode.JOIN);
		    criteria.createAlias("stateMasterDesc.fsStateMaster", "fsStateMaster", JoinType.INNER_JOIN);
		    criteria.setFetchMode("fsStateMaster.fsCountryMaster", FetchMode.JOIN);
		    criteria.createAlias("fsStateMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		    criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		    //criteria.add(Restrictions.ne("fsStateMaster.isActive", "N"));
		    return (List<StateMasterDesc>) findAll(criteria);
	}

}
