package com.amg.exchange.associationtagging.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.associationtagging.model.AssociationTaggingVW;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CustCorporateAddlDetail;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.remittance.bean.PopulateData;

public interface AssociationTaggingService {
	
	public List<AssociationTaggingVW> getCustomerList(BigDecimal cusRefNum);	
	public List<AssociationTaggingVW> getCustomerCivilIdList(String civilId);	
	public void updateAssCode(BigDecimal assCode, BigDecimal cusIdPK);
	public String getNationality(BigDecimal coutryId , BigDecimal langId);
	public String countryName(BigDecimal countryId,BigDecimal langId);
	public String stateName(BigDecimal stateId,BigDecimal langId);
	public String districtName(BigDecimal districtId,BigDecimal langId);
	public String cityName(BigDecimal cityId,BigDecimal langId);
	public List<CustomerEmploymentInfo> getEmployeeList(BigDecimal cusId);
	public List<PopulateData> getAssociationTagList();
	public List<Customer> getCompanyNames(BigDecimal cusId);
	public String getComponentValues(BigDecimal componentDataId , BigDecimal langId);
	public List<AssociationTaggingVW> getCustomerListByMobile(String mobileNum);

}
