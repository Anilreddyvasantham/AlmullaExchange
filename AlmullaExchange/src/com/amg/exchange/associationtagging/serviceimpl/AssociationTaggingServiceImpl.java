package com.amg.exchange.associationtagging.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.associationtagging.dao.AssociationTaggingDAO;
import com.amg.exchange.associationtagging.model.AssociationTaggingVW;
import com.amg.exchange.associationtagging.service.AssociationTaggingService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.remittance.bean.PopulateData;

@Service("associationTaggingServiceImpl")
public class AssociationTaggingServiceImpl implements AssociationTaggingService {
	
	@Autowired
	AssociationTaggingDAO associationTaggingDAO;	
	
	
	
	@Override
	@Transactional
	public List<AssociationTaggingVW> getCustomerList(BigDecimal cusRefNum) {
		return associationTaggingDAO.getCustomerList(cusRefNum);
	}
	
	@Override
	@Transactional
	public List<AssociationTaggingVW> getCustomerCivilIdList(String civilId) {
		return associationTaggingDAO.getCustomerCivilIdList(civilId);
	}
	
	@Override
	@Transactional
	public void updateAssCode(BigDecimal assCode, BigDecimal cusIdPK) {
		associationTaggingDAO.updateAssCode(assCode,cusIdPK);
	}
	
	@Override
	@Transactional
	public String getNationality(BigDecimal coutryId , BigDecimal langId) {
		return associationTaggingDAO.getNationality(coutryId,langId);
	}
	
	@Override
	@Transactional
	public String countryName(BigDecimal countryId,BigDecimal langId) {
		return associationTaggingDAO.countryName(countryId,langId);
	}
	
	@Override
	@Transactional
	public String stateName(BigDecimal stateId,BigDecimal langId) {
		return associationTaggingDAO.stateName(stateId,langId);
	}
	
	@Override
	@Transactional
	public String districtName(BigDecimal districtId,BigDecimal langId) {
		return associationTaggingDAO.districtName(districtId,langId);
	}
	
	@Override
	@Transactional
	public String cityName(BigDecimal cityId,BigDecimal langId) {
		return associationTaggingDAO.cityName(cityId,langId);
	}
	
	@Override
	@Transactional
	public List<CustomerEmploymentInfo> getEmployeeList(BigDecimal cusId) {
		return associationTaggingDAO.getEmployeeList(cusId);
	}
	
	@Override
	@Transactional
	public List<PopulateData> getAssociationTagList() {
		return associationTaggingDAO.getAssociationTagList();
	}
	
	@Override
	@Transactional
	public List<Customer> getCompanyNames(BigDecimal cusId) {
		return associationTaggingDAO.getCompanyNames(cusId);
	}
	
	@Override
	@Transactional
	public String getComponentValues(BigDecimal componentDataId , BigDecimal langId) {
		return associationTaggingDAO.getComponentValues(componentDataId,langId);
	}

	@Override
	@Transactional
	public List<AssociationTaggingVW> getCustomerListByMobile(String mobileNum) {
		return associationTaggingDAO.getCustomerListByMobile(mobileNum);
	}

}
