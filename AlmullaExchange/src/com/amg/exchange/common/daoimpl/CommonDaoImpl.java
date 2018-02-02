package com.amg.exchange.common.daoimpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.internal.SessionImpl;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;
import org.springframework.orm.hibernate4.SessionFactoryUtils;

import com.amg.exchange.common.dao.ICommonDao;

public class CommonDaoImpl<T> extends CustomHibernateDaoSupport implements ICommonDao<T> {

	@Override
	public void save(T entity) {
		
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		
		getSession().update(entity);
	}

	@Override
	public void delete(T entity) {
		
		getSession().delete(entity);
	}

	@Override
	public List<T> findByName(String name) {
		
		return null;
	}

	@Override
	public List<T> findById(int id) {

		return null;
	}

	@Override
	public List<T> findAll() {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		
		return ((List<T>)criteria.getExecutableCriteria(getSession()).list());
	}

	@Override
	public void saveOrUpdate(T entity) {
		
		getSession().saveOrUpdate(entity);
	}
	
	@Override
	public Connection getJavaSqlConnectionFromHibernateSession() {

		Session session = getSession();

		SessionFactoryImplementor sessionFactoryImplementor = null;

		ConnectionProvider connectionProvider = null;

		java.sql.Connection connection = null;

		try {
			sessionFactoryImplementor = (SessionFactoryImplementor) session.getSessionFactory();

			connectionProvider = (ConnectionProvider) sessionFactoryImplementor.getConnectionProvider();

			connection = connectionProvider.getConnection();

		} catch (SQLException e) {

			e.printStackTrace();

		}
		return connection;

	}

	@Override
	public Connection getDataSourceFromHibernateSession() {

		//Session session = getSession();
		java.sql.Connection connection = null;
		try {
			/*DataSource ds =SessionFactoryUtils.getDataSource(session.getSessionFactory());
			connection =ds.getConnection();*/
			
			connection= ((SessionImpl) getSession()).connection();

		} catch (Exception e) {

			e.printStackTrace();
		}
		return connection;
	}

	
	@Override
	public void saveOrUpdateWithPartialSave(T entity) {
		try{
		getSession().saveOrUpdate(entity);
		}catch(RuntimeException e){
			
			try{
				getSession().getTransaction().rollback();
    		}catch(RuntimeException rbe){
    			
    		}
    		throw e;

		}
	}
}

/*package com.amg.exchange.common.daoimpl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;

import com.amg.exchange.common.dao.ICommonDao;

public class CommonDaoImpl<T> extends CustomHibernateDaoSupport implements ICommonDao<T> {
	private static final Logger LOG = Logger.getLogger(CommonDaoImpl.class);


	@Override
	public void save(T entity) {
		
		getSession().save(entity);
	}

	@Override
	public void update(T entity) {
		
		getSession().update(entity);
	}

	@Override
	public void delete(T entity) {
		
		getSession().delete(entity);
	}

	@Override
	public List<T> findByName(String name) {
		
		return null;
	}

	@Override
	public List<T> findById(int id) {

		return null;
	}

	@Override
	public List<T> findAll() {
		
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll(DetachedCriteria criteria) {
		
		return ((List<T>)criteria.getExecutableCriteria(getSession()).list());
	}

	@Override
	public void saveOrUpdate(T entity) {
		
		getSession().saveOrUpdate(entity);
	}
	
	@Override
	public void saveOrUpdateWithPartialSave(T entity) {
		try{
		getSession().saveOrUpdate(entity);
		}catch(RuntimeException e){
			
			try{
				getSession().getTransaction().rollback();
    		}catch(RuntimeException rbe){
    			LOG.error("Couldn’t roll back transaction"+rbe);	
    		}
    		throw e;

		}
	}
}
*/