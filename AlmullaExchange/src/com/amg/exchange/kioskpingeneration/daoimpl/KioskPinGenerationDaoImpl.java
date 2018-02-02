package com.amg.exchange.kioskpingeneration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.kioskpingeneration.dao.IKioskPinGenerationDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;


@SuppressWarnings({"serial",  "unchecked" })
@Repository
public class KioskPinGenerationDaoImpl<T> extends CommonDaoImpl<T> implements IKioskPinGenerationDao<T>, Serializable{

	@Override
	public List<Customer> getCustomerDetails(BigDecimal customerReference) {
	
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,"customer");
		
		criteria.setFetchMode("customer.fsBizComponentDataByCustomerTypeId", FetchMode.JOIN);
		criteria.createAlias("customer.fsBizComponentDataByCustomerTypeId", "fsBizComponentDataByCustomerTypeId", JoinType.INNER_JOIN);
		
		
		criteria.add(Restrictions.eq("customerReference", customerReference));
		criteria.add(Restrictions.eq("isActive", Constants.Yes));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)findAll(criteria);
	}

	@Override
	public List<CustomerIdProof> getIdProofDetails(BigDecimal customerId) {
		
	   DetachedCriteria criteria = DetachedCriteria.forClass(CustomerIdProof.class,"customerIdProof");
		
		criteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		criteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		
		criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<CustomerIdProof>)findAll(criteria);
	}

	@Override
	public List<String> callProcedureToGenerateKioskPin() throws AMGException {
		List<String> output = new ArrayList<String>();
		
		Connection connection=null;
		try {
			connection =getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			
			String call = " { call EX_P_GENERATE_KIOSK_PIN (?,?) } ";
			cs = connection.prepareCall(call);
			cs.registerOutParameter(1, java.sql.Types.VARCHAR);
			cs.registerOutParameter(2, java.sql.Types.VARCHAR);
			cs.execute();
			String out1=cs.getString(1);
			String out2=cs.getString(2);
			output.add(out1);
			output.add(out2);
			connection.close();
		}catch (SQLException e) {
			throw new AMGException(e.getMessage());
		}

		return output;
	}


	@Override
	public void updateKioskPin(BigDecimal customerId, String kioskPin) {
		Customer customer=(Customer) getSession().get(Customer.class, customerId);
		customer.setKioskPin(kioskPin);
		
		getSession().update(customer);
		
	}

}
