package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;

public interface IMutualService<T>  {
	
	public List<CityMaster> getCity(BigDecimal districtId);
    public List<CountryMaster> getCountry();
	public List<StateMaster> getState();
	public List<CompanyMaster> getCompany(BigDecimal countryId);
	public List<StateMaster> getState(BigDecimal countryId);
	public List<DistrictMaster> getDistrict(BigDecimal stateId);
}
