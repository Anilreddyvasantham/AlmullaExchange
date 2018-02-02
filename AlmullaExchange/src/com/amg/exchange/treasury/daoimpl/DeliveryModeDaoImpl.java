package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.DeliveryModeDao;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.util.Constants;

@Repository
public class DeliveryModeDaoImpl<T> extends CommonDaoImpl<T> implements DeliveryModeDao {

	@Override
	public void save(DeliveryMode deliveryMode) {
		getSession().saveOrUpdate(deliveryMode);
	}

	@Override
	public void saveRecord(DeliveryModeDesc deliveryModeDesc) {
		getSession().saveOrUpdate(deliveryModeDesc);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryMode> getDeliveryMode(String deliveryMode) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");
		
		criteria.add(Restrictions.eq("deliveryMode.deliveryMode", deliveryMode).ignoreCase());
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DeliveryMode>) findAll(criteria);

	}
	
	/*added to populate the DB Values added @Koti 20/02/2015**/ 
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> getDeliveryDescriptionList( BigDecimal deliveryModeId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( DeliveryModeDesc.class, "deliveryModeDesc");
		
		detachedCriteria.setFetchMode("deliveryModeDesc.deliveryMode",FetchMode.SELECT);
		detachedCriteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);

		detachedCriteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
		detachedCriteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		
		detachedCriteria.add(Restrictions.eq("deliveryMode.deliveryModeId",deliveryModeId));

		List<DeliveryModeDesc> dsclist = (List<DeliveryModeDesc>) findAll(detachedCriteria);

		return dsclist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllData(String query) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class,"deliveryMode");
		
		criteria.add(Restrictions.like("deliveryMode.deliveryMode", query, MatchMode.ANYWHERE).ignoreCase());

		criteria.setProjection(Projections.property("deliveryMode.deliveryMode"));
		
		criteria.addOrder(Order.asc("deliveryMode.deliveryMode"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<String>) findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getDeliveryDescriptionList() {

		List<Object> objList=new ArrayList<Object>();
		
		String queryString="SELECT C.DELIVERY_CODE,A.DELIVERY_NAME_ENGLISH DELIVERY_NAME_ENGLISH, B.DELIVERY_NAME_ENGLISH DELIVERY_NAME_LOCAL,A.DELIVERY_MODE_ID FROM ( SELECT DELIVERY_MODE_ID, DELIVERY_NAME_ENGLISH  FROM EX_DELIVERY_MODE_DESC WHERE LANGUAGE_ID = 1 ) a, (SELECT DELIVERY_MODE_ID, DELIVERY_NAME_ENGLISH  FROM EX_DELIVERY_MODE_DESC WHERE LANGUAGE_ID = 2) B , EX_DELIVERY_MODE C WHERE A.DELIVERY_MODE_ID = B.DELIVERY_MODE_ID and a.DELIVERY_MODE_ID = C.DELIVERY_MODE_ID and C.ISACTIVE = 'Y'";

		objList =  getSession().createSQLQuery(queryString).list();
		
		return objList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryMode> getDeliveryModeList(BigDecimal deliveryModeId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass( DeliveryMode.class, "deliveryMode");
		
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", deliveryModeId));
		
		//criteria.add(Restrictions.ne("deliveryMode.isActive", "U"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DeliveryMode>) findAll(criteria);
	}

	@Override
	public List<DeliveryMode> getDeliverylist() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");
		
		//criteria.add(Restrictions.ne("deliveryMode.isActive", Constants.U));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<DeliveryMode>)findAll(criteria);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getDeliveryDesc(BigDecimal engId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		
		criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", engId));
		
		criteria.setFetchMode("deliveryModeDesc.languageType", FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ENGLISH_LANGUAGE_ID)));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		List<DeliveryModeDesc> list = (List<DeliveryModeDesc>) findAll(criteria);
		
		if (list != null && !list.isEmpty()) {
			return list.get(0).getEnglishDeliveryName();
		}
		
		return null;
	}



	@Override
	public String getArbDelivery(BigDecimal arbId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		criteria.setFetchMode("deliveryModeDesc.deliveryMode",FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId", arbId));

		criteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", new BigDecimal(Constants.ARABIC_LANGUAGE_ID)));

		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		return ((DeliveryModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getEnglishDeliveryName();
	}

	@Override
	public BigDecimal getDeliveryPk(String englang) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");

		criteria.add(Restrictions.eq("deliveryModeDesc.englishDeliveryName", englang));

		return ((DeliveryModeDesc)criteria.getExecutableCriteria(getSession()).list().get(0)).getDeliveryModeDescId();
	}

	@Override
	public BigDecimal getDeliveryarbPk(String arblang) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass( DeliveryModeDesc.class, "deliveryModeDesc");

		criteria.add(Restrictions.eq("deliveryModeDesc.englishDeliveryName",arblang));

		return ((DeliveryModeDesc) criteria.getExecutableCriteria(getSession()).list().get(0)).getDeliveryModeDescId();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryMode> getDeliveryNlist() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");

		criteria.add(Restrictions.eq("deliveryMode.isActive", Constants.U));
		criteria.add(Restrictions.isNull("deliveryMode.appovedBy"));
		criteria.add(Restrictions.isNull("deliveryMode.approveDate"));

		return (List<DeliveryMode>)findAll(criteria);

	}

	@Override
	public String getCreatedBy() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");
		
		return ((DeliveryMode)criteria.getExecutableCriteria(getSession()).list().get(0)).getCreatedBy();
	}

	@Override
	public void saveDelivery(DeliveryMode deliverymode, DeliveryModeDesc desc,DeliveryModeDesc desc2) {
		try{
			getSession().saveOrUpdate(deliverymode);
			getSession().saveOrUpdate(desc);
			getSession().saveOrUpdate(desc2);
		}catch(Exception e){}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryModeDesc> getAllDescList(BigDecimal deliveryModeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",JoinType.INNER_JOIN);
		criteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId",deliveryModeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<DeliveryModeDesc>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DeliveryMode> getDeliveryApprovel() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryMode.class, "deliveryMode");
		criteria.add(Restrictions.eq("deliveryMode.isActive",Constants.U));
		criteria.add(Restrictions.isNull("deliveryMode.appovedBy"));
		criteria.add(Restrictions.isNull("deliveryMode.approveDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		/*	criteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
	        criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
	        criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
			criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",JoinType.INNER_JOIN);
			criteria.add(Restrictions.eq("deliveryMode.isActive", Constants.U));*/
		return (List<DeliveryMode>) findAll(criteria);
	}

	@Override
	public void deleteRecordPermentily(BigDecimal deliveryPk,BigDecimal deliveryEnglishPk, BigDecimal deliveryLocalPk) {
		DeliveryMode deliveryMode=(DeliveryMode) getSession().get(DeliveryMode.class, deliveryPk);
		DeliveryModeDesc deliveryModedescEng=(DeliveryModeDesc) getSession().get(DeliveryModeDesc.class, deliveryEnglishPk);
		DeliveryModeDesc deliveryModedescarb=(DeliveryModeDesc) getSession().get(DeliveryModeDesc.class, deliveryLocalPk);
		getSession().delete(deliveryModedescEng);
		getSession().delete(deliveryModedescarb);
		getSession().delete(deliveryMode);
	}

	@Override
	public String approveRecord(BigDecimal deliveryPk, String userName) {
		String approveMsg;
		DeliveryMode deliveryMode=(DeliveryMode) getSession().get(DeliveryMode.class, deliveryPk);
		String approvedUser=deliveryMode.getAppovedBy();
		if(approvedUser==null)
		{
			deliveryMode.setIsActive(Constants.Yes);
			deliveryMode.setAppovedBy(userName);
			deliveryMode.setApproveDate(new Date());
			getSession().update(deliveryMode);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;

	}

	@SuppressWarnings("unchecked")
	@Override
	public String getDeliveryDesc(BigDecimal deliveryModeId,BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DeliveryModeDesc.class, "deliveryModeDesc");
		
		criteria.setFetchMode("deliveryModeDesc.deliveryMode", FetchMode.JOIN);
		criteria.createAlias("deliveryModeDesc.deliveryMode", "deliveryMode",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("deliveryMode.deliveryModeId",deliveryModeId));
		
		criteria.setFetchMode("deliveryModeDesc.languageType",FetchMode.SELECT);
		criteria.createAlias("deliveryModeDesc.languageType", "languageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageType.languageId", languageId));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return ((List<DeliveryModeDesc>) findAll(criteria)).get(0).getEnglishDeliveryName();
	}

	  @Override
	  public void activeRecordToPendingForApproval(BigDecimal deliveryPk, String userName) {
		    DeliveryMode deliveryMode=(DeliveryMode) getSession().get(DeliveryMode.class, deliveryPk);
		    deliveryMode.setModifiedBy(userName);
		    deliveryMode.setModifiedDate(new Date());
		    deliveryMode.setAppovedBy(null);
		    deliveryMode.setApproveDate(null);
		    deliveryMode.setIsActive(Constants.U);
		    deliveryMode.setRemrks(null);
		    getSession().update(deliveryMode);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal deliveryPk, String remarks, String userName) {
		    DeliveryMode deliveryMode=(DeliveryMode) getSession().get(DeliveryMode.class, deliveryPk);
		    deliveryMode.setModifiedBy(userName);
		    deliveryMode.setModifiedDate(new Date());
		    deliveryMode.setAppovedBy(null);
		    deliveryMode.setApproveDate(null);
		    deliveryMode.setIsActive(Constants.D);
		    deliveryMode.setRemrks(remarks);
		    getSession().update(deliveryMode);   
		    
	  }	
	
	
}
