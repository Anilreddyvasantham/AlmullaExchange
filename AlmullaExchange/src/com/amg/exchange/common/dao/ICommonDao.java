package com.amg.exchange.common.dao;

import java.sql.Connection;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface ICommonDao<T> {
	
	void save(T entity);
	void update(T entity);
	void saveOrUpdate(T entity);
	void delete(T entity);

	List<T> findByName(String name); 
	List<T> findById(int id); 
	List<T> findAll();
	List<T> findAll(DetachedCriteria criteria);
	
	public Connection getJavaSqlConnectionFromHibernateSession();
	public Connection getDataSourceFromHibernateSession();
	void saveOrUpdateWithPartialSave(T entity);
}


/*package com.amg.exchange.common.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

public interface ICommonDao<T> {
	
	void save(T entity);
	void update(T entity);
	void saveOrUpdate(T entity);
	void delete(T entity);

	List<T> findByName(String name); 
	List<T> findById(int id); 
	List<T> findAll();
	List<T> findAll(DetachedCriteria criteria);
	void saveOrUpdateWithPartialSave(T entity);
}
*/