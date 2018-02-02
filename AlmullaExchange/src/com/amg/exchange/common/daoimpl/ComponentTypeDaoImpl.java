package com.amg.exchange.common.daoimpl;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IComponentTypeDao;
import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.ComponentTypeDetail;
import com.amg.exchange.common.model.HighValueAMLAuthViewFC;
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Repository
public class ComponentTypeDaoImpl <T> extends CommonDaoImpl<T> implements IComponentTypeDao<T>, Serializable {
	
	private static final Logger LOG = Logger.getLogger(ComponentTypeDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<ComponentType> getComponentTypeList() {
		LOG.info("Entering into getComponentTypeList method");
		DetachedCriteria criteria = DetachedCriteria.forClass(ComponentType.class, "componentType");
		criteria.setFetchMode("componentType.componentTypeDetail", FetchMode.JOIN);
		criteria.createAlias("componentType.componentTypeDetail", "componentTypeDetail", JoinType.LEFT_OUTER_JOIN);
		criteria.addOrder(Order.asc("componentType.componentTypeName"));
		LOG.info("Exit into getComponentTypeList method");
		return (List<ComponentType>) findAll(criteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void storeData(List<ComponentTypeDetail> componentTypeList) {
		LOG.info("Entering into storeData method");
		
		ComponentType componentType;
		for (ComponentTypeDetail componentTypeDetail : componentTypeList) {
			componentType = componentTypeDetail.getComponentType();
			saveOrUpdate((T) componentType);
			componentTypeDetail.setComponentType(componentType);
			saveOrUpdate((T) componentTypeDetail);
		
		}
		LOG.info("Exit into storeData method");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<HighValueAMLAuthViewLocal> getHighValueAmlAuthRecords(){
		LOG.info("Entering into getHighValueAmlAuthRecords method");
		DetachedCriteria criteria = DetachedCriteria.forClass(HighValueAMLAuthViewLocal.class, "highValueAMLAuthViewLocal");
		criteria.addOrder(Order.asc("highValueAMLAuthViewLocal.documentDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getHighValueAmlAuthRecords method");
		List<HighValueAMLAuthViewLocal> lstHighValueAMLAuthViewLocal=(List<HighValueAMLAuthViewLocal>) findAll(criteria);
		
		return lstHighValueAMLAuthViewLocal;
	}
	
	@SuppressWarnings("unchecked")
	public List<HighValueAMLAuthViewFC> getHighValueAmlAuthRecordsForFC(){
		LOG.info("Entering into getHighValueAmlAuthRecords method");
		DetachedCriteria criteria = DetachedCriteria.forClass(HighValueAMLAuthViewFC.class, "highValueAMLAuthViewFC");
		criteria.addOrder(Order.asc("highValueAMLAuthViewFC.documentDate"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOG.info("Exit into getHighValueAmlAuthRecords method");
		return (List<HighValueAMLAuthViewFC>) findAll(criteria);
	}
	
	public Boolean saveHighValueAmlAuth(List<HighValueAMLAuthViewLocal> highvalueAmlList, String userName,String remaks) throws AMGException{
		 Boolean returnStr = false;
		 Connection connection = null;
		 try{
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			for(HighValueAMLAuthViewLocal highValueObj:highvalueAmlList){
				cs = connection.prepareCall(" { call EX_UPDATE_AML_AUTH(?,?,?,?,?)}");
				cs.setBigDecimal(1, highValueObj.getIdNo());
				cs.setString(2, remaks);
				cs.setString(3, userName);
				cs.setString(4, "Y");
				cs.setString(5, null);
				cs.execute();
			}
			returnStr = true;
		 }catch(Exception ex){
			 returnStr = false;
			 throw new AMGException(ex.getMessage());
		 }finally {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		 } 
			return returnStr;
	}
	
	public Boolean saveHighValueAmlAuthForFC(List<HighValueAMLAuthViewFC> highvalueAmlList, String userName,String remaks) throws AMGException{
		 Boolean returnStr = false;
		 Connection connection = null;
		 try{
			connection = getDataSourceFromHibernateSession();
			CallableStatement cs;
			for(HighValueAMLAuthViewFC highValueObj:highvalueAmlList){
				cs = connection.prepareCall(" { call EX_UPDATE_AML_AUTH(?,?,?,?,?)}");
				cs.setBigDecimal(1, highValueObj.getIdNo());
				cs.setString(2, remaks);
				cs.setString(3, userName);
				cs.setString(4, null);
				cs.setString(5, "Y");
				cs.execute();
			}
			returnStr = true;
		 }catch(Exception ex){
			 returnStr = false;
			 throw new AMGException(ex.getMessage());
		 }finally {
					try {
						connection.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
		 } 
			return returnStr;
	}
	
	
}
