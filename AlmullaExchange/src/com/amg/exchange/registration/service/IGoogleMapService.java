package com.amg.exchange.registration.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.GoogleMapAddress;

public interface IGoogleMapService<T> {
	
	 public List<GoogleMapAddress> getLongitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId);
	 public List<GoogleMapAddress> getLattitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId);
	 public List<GoogleMapAddress> getAddress(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId,BigDecimal languageId);
	 public List<GoogleMapAddress> getCountryLongLatt(BigDecimal countryId);
	 
	 public String getLongitudeStr(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId);
	 public String getLattitudeStr(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId);
	 public String getAddressStr(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId,BigDecimal languageId);
	 public String getCountryLongLattStr(BigDecimal countryId);

}
