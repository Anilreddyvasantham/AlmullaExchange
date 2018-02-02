package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.amg.exchange.common.dao.IMutualDao;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.service.IMutualService;

public class MutualServiceImpl<T> implements IMutualService<T> {
	@Autowired
	IMutualDao mutualDao;
	public IMutualDao getMutualDao() {
		return mutualDao;
	}

	public void setMutualDao(IMutualDao mutualDao) {
		this.mutualDao = mutualDao;
	}

	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return getMutualDao().getCountry();
	}
/*
	@Override
	public List<DistrictMaster> getDistrict(String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}
*/
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

	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}
}
