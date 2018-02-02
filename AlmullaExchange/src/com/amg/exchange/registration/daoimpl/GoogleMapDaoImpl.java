package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.IGoogleMapDao;
import com.amg.exchange.registration.model.GoogleMapAddress;


@SuppressWarnings("serial")
@Repository
public class GoogleMapDaoImpl<T> extends CommonDaoImpl<T> implements IGoogleMapDao<T>, Serializable{

	@SuppressWarnings("unchecked")
	@Override
	public List<GoogleMapAddress> getLongitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		
	
		return (List<GoogleMapAddress>) findAll(creteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoogleMapAddress> getLattitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		
      DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		

		
		return (List<GoogleMapAddress>) findAll(creteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoogleMapAddress> getAddress(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId,BigDecimal languageId) {
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
			
		 creteria.add(Restrictions.eq("countryId", countryId));
		 creteria.add(Restrictions.eq("stateId", stateId));
		 creteria.add(Restrictions.eq("districtId", districtId));
		 creteria.add(Restrictions.eq("cityId", cityId));
		 creteria.add(Restrictions.eq("languageId", languageId));
			
			return (List<GoogleMapAddress>) findAll(creteria);
	}

	@Override
	public String getLongitudeStr(BigDecimal countryId, BigDecimal stateId,
			BigDecimal districtId, BigDecimal cityId) {
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		 

		 creteria.add(Restrictions.eq("countryId", countryId));
		 creteria.add(Restrictions.eq("stateId", stateId));
		 creteria.add(Restrictions.eq("districtId", districtId));
		 creteria.add(Restrictions.eq("cityId", cityId));
			
			
			return ((GoogleMapAddress) creteria.getExecutableCriteria(getSession()).list().get(0)).getLongitude();
	}

	@Override
	public String getLattitudeStr(BigDecimal countryId, BigDecimal stateId,
			BigDecimal districtId, BigDecimal cityId) {
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		 

		 creteria.add(Restrictions.eq("countryId", countryId));
		 creteria.add(Restrictions.eq("stateId", stateId));
		 creteria.add(Restrictions.eq("districtId", districtId));
		 creteria.add(Restrictions.eq("cityId", cityId));
			
			
			return ((GoogleMapAddress) creteria.getExecutableCriteria(getSession()).list().get(0)).getLatitude();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getAddressStr(BigDecimal countryId, BigDecimal stateId,
			BigDecimal districtId, BigDecimal cityId,BigDecimal languageId) {
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		 
		 creteria.add(Restrictions.eq("countryId", countryId));
		 creteria.add(Restrictions.eq("stateId", stateId));
		 creteria.add(Restrictions.eq("districtId", districtId));
		 creteria.add(Restrictions.eq("cityId", cityId));
		 creteria.add(Restrictions.eq("languageId", languageId));
			
			
			List<GoogleMapAddress> data = (List<GoogleMapAddress>)findAll(creteria);
			return data.get(0).getAddress();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoogleMapAddress> getCountryLongLatt(BigDecimal countryId) {
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		 creteria.add(Restrictions.eq("countryId", countryId));
		return (List<GoogleMapAddress>) findAll(creteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getCountryLongLattStr(BigDecimal countryId) {
		
		 DetachedCriteria creteria = DetachedCriteria.forClass(GoogleMapAddress.class, "googleMapAdd");
		 
		 creteria.add(Restrictions.eq("countryId", countryId));
		List<GoogleMapAddress> data = (List<GoogleMapAddress>)findAll(creteria);
		return data.get(0).getCountryLongLatt();
	}
	

}
