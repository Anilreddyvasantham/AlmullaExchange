package com.amg.exchange.remittance.daoimpl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.remittance.bean.CustomerAlmTrasactionCheckProcedure;
import com.amg.exchange.remittance.dao.ICustomerAlmTrasactionCheckDao;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestApprovService;

@Repository
public class CustomerAlmTransationCheckDaoImpl<T> extends CommonDaoImpl<T> implements ICustomerAlmTrasactionCheckDao<T>  {
	
	@Autowired
	ISpecialCustomerDealRequestApprovService<T> CustomerSpecialDealRequest;


	@Override
	public CustomerAlmTrasactionCheckProcedure getCustomerAlmTrasactionCheckService(BigDecimal CountryId,
			BigDecimal CustomerNo) {
		CustomerAlmTrasactionCheckProcedure amlProcedure=new CustomerAlmTrasactionCheckProcedure();

			int out=0;
		
			Connection connection=null;
			try {
				//connection = DBConnection.getdataconnection();
				connection = getDataSourceFromHibernateSession();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			CallableStatement cs;
			try {
				cs = connection.prepareCall(" { call DP_CUSTOMER_AML_TRNX_CHECKOLD(?,?,?,?,?,?)}");
				cs.setBigDecimal(1, CountryId);
				cs.setBigDecimal(2, CustomerNo);
				
				cs.registerOutParameter(3, java.sql.Types.INTEGER);
				cs.registerOutParameter(4, java.sql.Types.INTEGER);
				cs.registerOutParameter(5, java.sql.Types.INTEGER);
				cs.registerOutParameter(6, java.sql.Types.INTEGER);
				//cs.executeUpdate();
				cs.execute();
				amlProcedure.setCountryId(CountryId);
				amlProcedure.setCustomerId(CustomerNo);
				amlProcedure.setSlab1(cs.getInt(3));
				amlProcedure.setSlab2(cs.getInt(4));
				amlProcedure.setSlab3(cs.getInt(5));
				amlProcedure.setSlab4(cs.getInt(6));
				//amlProcedure.setCountryName(CustomerSpecialDealRequest.getCountryName(CountryId));
				System.out.println("slab1-  "+cs.getInt(3));
				System.out.println("slab2-  "+cs.getInt(4));
				System.out.println("slab3-  "+cs.getInt(5));
				System.out.println("slab4-  "+cs.getInt(6));
				
				
				
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return (CustomerAlmTrasactionCheckProcedure) amlProcedure;
		}
	}
	
	


