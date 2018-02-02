package com.amg.exchange.registration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.registration.dao.IGoogleMapDao;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.registration.model.GoogleMapAddress;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.IGoogleMapService;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;

@SuppressWarnings("serial")
@Service("googleMapServiceImpl")
public class GoogleMapServiceImpl<T>  implements IGoogleMapService<T>, Serializable{

	@Autowired
	IGoogleMapDao<T> iGoogleMapDao;
	
	@Override
	@Transactional
	public List<GoogleMapAddress> getLongitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		return iGoogleMapDao.getLongitude(countryId, stateId, districtId, cityId);
	}

	@Override
	@Transactional
	public List<GoogleMapAddress> getLattitude(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId) {
		return iGoogleMapDao.getLattitude(countryId, stateId, districtId, cityId);
	}

	@Override
	@Transactional
	public List<GoogleMapAddress> getAddress(BigDecimal countryId,BigDecimal stateId, BigDecimal districtId, BigDecimal cityId, BigDecimal languageId) {
		return iGoogleMapDao.getAddress(countryId, stateId, districtId, cityId,languageId);
	}

	@Override
	@Transactional
	public String getLongitudeStr(BigDecimal countryId, BigDecimal stateId,
			BigDecimal districtId, BigDecimal cityId) {
		// TODO Auto-generated method stub
		return iGoogleMapDao.getLongitudeStr(countryId, stateId, districtId, cityId);
	}

	@Override
	@Transactional
	public String getLattitudeStr(BigDecimal countryId, BigDecimal stateId,
			BigDecimal districtId, BigDecimal cityId) {
		// TODO Auto-generated method stub
		return iGoogleMapDao.getLattitudeStr(countryId, stateId, districtId, cityId);
	}

	@Override
	@Transactional
	public String getAddressStr(BigDecimal countryId, BigDecimal stateId,BigDecimal districtId, BigDecimal cityId,BigDecimal languageId) {
		return iGoogleMapDao.getAddressStr(countryId, stateId, districtId, cityId,languageId);
	}

	

	@Override
	@Transactional
	public List<GoogleMapAddress> getCountryLongLatt(BigDecimal countryId) {
		return iGoogleMapDao.getCountryLongLatt(countryId);
	}

	

	@Override
	@Transactional
	public String getCountryLongLattStr(BigDecimal countryId) {
		return iGoogleMapDao.getCountryLongLattStr(countryId);
	}
	
}

	
