package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.amg.exchange.common.dao.IMutualDao;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;

public class MutualDaoImpl implements IMutualDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return sessionFactory.getCurrentSession().createQuery("from CountryMaster").list();
	}



	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyMaster> getCompany(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistrictMaster> getDistrict(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return null;
	}
/*
	@Override
	public List<CompanyMaster> getCompany(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState(String country) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistrictMaster> getDistrict(String state) {
		// TODO Auto-generated method stub
		return null;
	}*/
}
