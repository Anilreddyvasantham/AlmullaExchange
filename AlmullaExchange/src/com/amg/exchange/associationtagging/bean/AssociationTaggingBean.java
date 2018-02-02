package com.amg.exchange.associationtagging.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.associationtagging.model.AssociationTaggingVW;
import com.amg.exchange.associationtagging.service.AssociationTaggingService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.util.SessionStateManage;

@Component("associationTaggingBean")
@Scope("session")
public class AssociationTaggingBean {
	
	private String mobileNumSearch;
	private BigDecimal cusRefNumSearch;
	private String civilIDSearch;
	private String title;
	private String name;
	private String nameLocal;
	private String postOffNum;
	private String pinNum;
	private String postalArea1;
	private String postalArea2;
	private String localArea1;
	private String localArea2;
	private String block1;
	private String block2;
	private String streetNum;
	private String buildingNum;
	private String flatNum;
	private String phoneOff;
	private String mobileNum;
	private String residence;
	private String email;
	private String fax;
	private String hpin;
	private String motherMaidenName;
	private BigDecimal nationality1;
	private String nationality2;
	private String employedYN;
	private BigDecimal empType1;
	private String empType2;
	private String profession1;
	private String profession2;
	private String position1;
	private String position2;
	private String employerName;
	private BigDecimal homeCountryId;
	private String homeCountryName;
	private BigDecimal homeStateId;
	private String homeStateName;
	private BigDecimal homeDistrictId;
	private String homeDistrictName;
	private BigDecimal homeCityId;
	private String homeCityName;
	
	private BigDecimal locCountryId;
	private String locCountryName;
	private BigDecimal locStateId;
	private String locStateName;
	private BigDecimal locDistrictId;
	private String locDistrictName;
	private BigDecimal locCityId;
	private String locCityName;
	private BigDecimal customerType1;
	private String customerType2;
	
	private BigDecimal associationCode;
	private String errorMessage;
	private BigDecimal cusRefNumPK = null;
	private BigDecimal civilIdPK = null;
	
	private List<Customer> companyNames = null;
	
	@Autowired
	AssociationTaggingService associationTaggingService;
	
	@Autowired
	IGeneralService generalService;
	
	private SessionStateManage session = new SessionStateManage();;
	
	
	
	public void pageNavigation() {
		try {	
			clearAll();
			List<PopulateData> cusIdList = associationTaggingService.getAssociationTagList();
			List<Customer> customer = new ArrayList<Customer>();
			
			if(cusIdList != null || cusIdList.size() > 0){
				for(PopulateData custCorDet : cusIdList) {
					List<Customer> comNames = associationTaggingService.getCompanyNames(custCorDet.getPopulateId());
					customer.addAll(comNames);
				}
			}
			
			if(customer != null || customer.size() > 0) {
				setCompanyNames(customer);
			}			
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../associationtagging/associationTagging.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void fetchCusRefData() {
		List<AssociationTaggingVW> cusRefList = new ArrayList<AssociationTaggingVW>();
		
		if(getMobileNumSearch()!=null){
			clearCusRef();
			//cusRefList = associationTaggingService.getCustomerList(getCusRefNumSearch());
			List<AssociationTaggingVW> cusMobList = associationTaggingService.getCustomerListByMobile(getMobileNumSearch());
			if (cusMobList != null && cusMobList.size() > 0) {
				if(cusMobList.size() != 1){
					clearAll();
					RequestContext.getCurrentInstance().execute("error.show();");
					setErrorMessage("Multiple Records Available");
				}else{
					cusRefList = cusMobList;
				}
			}
		}
		
		if(cusRefList!= null  && cusRefList.size()>0){
			AssociationTaggingVW conDetails = cusRefList.get(0);
			setCusRefNumPK(conDetails.getCustomerId());
			setCivilIDSearch(conDetails.getCivilId());
			setMobileNumSearch(conDetails.getMobile());
			setCusRefNumSearch(conDetails.getCusRefNum());
			if(conDetails.getTitle() != null){
				String titleVal = associationTaggingService.getComponentValues(new BigDecimal(conDetails.getTitle()),session.getLanguageId());
				setTitle(titleVal);
			}
			setName(conDetails.getName());
			setNameLocal(conDetails.getNameLocal());
			setMobileNum(conDetails.getMobile());
			setEmail(conDetails.getEmail());
			setNationality1(conDetails.getNationality());
			String nationality = associationTaggingService.getNationality(getNationality1(),session.getLanguageId());
			setNationality2(nationality);
			
			setLocCountryId(conDetails.getLocalCountryId());
			if(getLocCountryId()!= null){
				setLocCountryName(associationTaggingService.countryName(getLocCountryId(),session.getLanguageId()));
			}
			
			setLocStateId(conDetails.getLocalStateId());
			if(getLocStateId()!=null){
				setLocStateName(associationTaggingService.stateName(getLocStateId(),session.getLanguageId()));
			}	
			
			setLocDistrictId(conDetails.getLocalDistrictId());
			if(getLocDistrictId()!=null){
				setLocDistrictName(associationTaggingService.districtName(getLocDistrictId(),session.getLanguageId()));
			}				
			setLocCityId(conDetails.getLocalCityId());
			if(getLocCityId()!=null){
				setLocCityName(associationTaggingService.cityName(getLocCityId(),session.getLanguageId()));
			}
			
			setHomeCountryId(conDetails.getHomeCountryId());
			
			if(getHomeCountryId()!=null){
				setHomeCountryName(associationTaggingService.countryName(getHomeCountryId(),session.getLanguageId()));
			}				
			setHomeStateId(conDetails.getHomeStateId());
			if(getHomeStateId()!=null){
				setHomeStateName(associationTaggingService.stateName(getHomeStateId(),session.getLanguageId()));
			}				
			setHomeDistrictId(conDetails.getHomeDistrictId());
			if(getHomeDistrictId()!=null){
				setHomeDistrictName(associationTaggingService.districtName(getHomeDistrictId(),session.getLanguageId()));
			}				
			setHomeCityId(conDetails.getHomeCityId());
			if(getHomeCityId()!=null){
				setHomeCityName(associationTaggingService.cityName(getHomeCityId(),session.getLanguageId()));
			}
			
			setAssociationCode(conDetails.getAssoCode());	
			
		} else {
			clearAll();
			RequestContext.getCurrentInstance().execute("noRecords.show();");
		}
	}
	
	
	public void fetchCivilIdData(){
		List<AssociationTaggingVW> cusRefList = new ArrayList<AssociationTaggingVW>();
		
		if(getCivilIDSearch()!=null){
			clearCivilId();
			cusRefList = associationTaggingService.getCustomerCivilIdList(getCivilIDSearch());
		}

		if(cusRefList!= null  && cusRefList.size()>0){
			AssociationTaggingVW conDetails = cusRefList.get(0);
			setCusRefNumPK(conDetails.getCustomerId());
			setCivilIDSearch(conDetails.getCivilId());
			setMobileNumSearch(conDetails.getMobile());
			setCusRefNumSearch(conDetails.getCusRefNum());
			if(conDetails.getTitle() != null){
				String titleVal = associationTaggingService.getComponentValues(new BigDecimal(conDetails.getTitle()),session.getLanguageId());
				setTitle(titleVal); 
			}
			setName(conDetails.getName());
			setNameLocal(conDetails.getNameLocal());
			setMobileNum(conDetails.getMobile());
			setEmail(conDetails.getEmail());
			setNationality1(conDetails.getNationality());
			String nationality = associationTaggingService.getNationality(getNationality1(),session.getLanguageId());
			setNationality2(nationality);
			
			setLocCountryId(conDetails.getLocalCountryId());
			if(getLocCountryId()!= null){
				setLocCountryName(associationTaggingService.countryName(getLocCountryId(),session.getLanguageId()));
			}
			
			setLocStateId(conDetails.getLocalStateId());
			if(getLocStateId()!=null){
				setLocStateName(associationTaggingService.stateName(getLocStateId(),session.getLanguageId()));
			}	
			
			setLocDistrictId(conDetails.getLocalDistrictId());
			if(getLocDistrictId()!=null){
				setLocDistrictName(associationTaggingService.districtName(getLocDistrictId(),session.getLanguageId()));
			}				
			setLocCityId(conDetails.getLocalCityId());
			if(getLocCityId()!=null){
				setLocCityName(associationTaggingService.cityName(getLocCityId(),session.getLanguageId()));
			}
			
			setHomeCountryId(conDetails.getHomeCountryId());
			
			if(getHomeCountryId()!=null){
				setHomeCountryName(associationTaggingService.countryName(getHomeCountryId(),session.getLanguageId()));
			}				
			setHomeStateId(conDetails.getHomeStateId());
			if(getHomeStateId()!=null){
				setHomeStateName(associationTaggingService.stateName(getHomeStateId(),session.getLanguageId()));
			}				
			setHomeDistrictId(conDetails.getHomeDistrictId());
			if(getHomeDistrictId()!=null){
				setHomeDistrictName(associationTaggingService.districtName(getHomeDistrictId(),session.getLanguageId()));
			}				
			setHomeCityId(conDetails.getHomeCityId());
			if(getHomeCityId()!=null){
				setHomeCityName(associationTaggingService.cityName(getHomeCityId(),session.getLanguageId()));
			}
			
			setAssociationCode(conDetails.getAssoCode());	
			
		}else {
			clearAll();
			RequestContext.getCurrentInstance().execute("noRecords.show();");
		}
	}
	
	
	public void updateAssoCode() {
		try {
			if (cusRefNumSearch == null && civilIDSearch == null) {
				RequestContext.getCurrentInstance().execute("enterAnyOne.show();");
			} else {
				if (cusRefNumSearch != null) {
					associationTaggingService.updateAssCode(getAssociationCode(), getCusRefNumPK());
					
					RequestContext.getCurrentInstance().execute("updateSuccess.show();");
					clearAll();
				} else if (civilIDSearch != null) {
					associationTaggingService.updateAssCode(getAssociationCode(), getCivilIdPK());
					
					RequestContext.getCurrentInstance().execute("updateSuccess.show();");
					clearAll();
				}
			}

		} catch (Exception e) {
			setErrorMessage(e.toString());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	public void exit() {
		try {			
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	
	public void clearAll() {
		setCusRefNumSearch(null);
		setMobileNumSearch(null);
		setCivilIDSearch(null);
		setTitle(null);
		setName(null);
		setNameLocal(null);
		setPostOffNum(null);
		setPinNum(null);
		setPostalArea1(null);
		setPostalArea2(null);
		setLocalArea1(null);
		setLocalArea2(null);
		setBlock1(null);
		setBlock2(null);
		setStreetNum(null);
		setBuildingNum(null);
		setFlatNum(null);
		setPhoneOff(null);
		setMobileNum(null);
		setResidence(null);
		setEmail(null);
		setFax(null);
		setHpin(null);
		setMotherMaidenName(null);
		setNationality1(null);
		setNationality2(null);
		setEmployedYN(null);
		setEmpType1(null);
		setEmpType2(null);
		setProfession1(null);
		setProfession2(null);
		setPosition1(null);
		setPosition2(null);
		setEmployerName(null);
		setHomeCountryId(null);
		setHomeCountryName(null);
		setHomeStateId(null);
		setHomeStateName(null);
		setHomeDistrictId(null);
		setHomeDistrictName(null);
		setHomeCityId(null);
		setHomeCityName(null);		
		setLocCountryId(null);
		setLocCountryName(null);
		setLocStateId(null);
		setLocStateName(null);
		setLocDistrictId(null);
		setLocDistrictName(null);
		setLocCityId(null);
		setLocCityName(null);
		setCustomerType1(null);
		setCustomerType2(null);
		setAssociationCode(null);
	}
	
	public void clearCusRef(){
		setCivilIDSearch(null);
		setTitle(null);
		setName(null);
		setNameLocal(null);
		setPostOffNum(null);
		setPinNum(null);
		setPostalArea1(null);
		setPostalArea2(null);
		setLocalArea1(null);
		setLocalArea2(null);
		setBlock1(null);
		setBlock2(null);
		setStreetNum(null);
		setBuildingNum(null);
		setFlatNum(null);
		setPhoneOff(null);
		setMobileNum(null);
		setResidence(null);
		setEmail(null);
		setFax(null);
		setHpin(null);
		setMotherMaidenName(null);
		setNationality1(null);
		setNationality2(null);
		setEmployedYN(null);
		setEmpType1(null);
		setEmpType2(null);
		setProfession1(null);
		setProfession2(null);
		setPosition1(null);
		setPosition2(null);
		setEmployerName(null);
		setHomeCountryId(null);
		setHomeCountryName(null);
		setHomeStateId(null);
		setHomeStateName(null);
		setHomeDistrictId(null);
		setHomeDistrictName(null);
		setHomeCityId(null);
		setHomeCityName(null);		
		setLocCountryId(null);
		setLocCountryName(null);
		setLocStateId(null);
		setLocStateName(null);
		setLocDistrictId(null);
		setLocDistrictName(null);
		setLocCityId(null);
		setLocCityName(null);
		setCustomerType1(null);
		setCustomerType2(null);
		setAssociationCode(null);
		
	}
	
	public void clearCivilId(){
		setCusRefNumSearch(null);
		setMobileNumSearch(null);
		setTitle(null);
		setName(null);
		setNameLocal(null);
		setPostOffNum(null);
		setPinNum(null);
		setPostalArea1(null);
		setPostalArea2(null);
		setLocalArea1(null);
		setLocalArea2(null);
		setBlock1(null);
		setBlock2(null);
		setStreetNum(null);
		setBuildingNum(null);
		setFlatNum(null);
		setPhoneOff(null);
		setMobileNum(null);
		setResidence(null);
		setEmail(null);
		setFax(null);
		setHpin(null);
		setMotherMaidenName(null);
		setNationality1(null);
		setNationality2(null);
		setEmployedYN(null);
		setEmpType1(null);
		setEmpType2(null);
		setProfession1(null);
		setProfession2(null);
		setPosition1(null);
		setPosition2(null);
		setEmployerName(null);
		setHomeCountryId(null);
		setHomeCountryName(null);
		setHomeStateId(null);
		setHomeStateName(null);
		setHomeDistrictId(null);
		setHomeDistrictName(null);
		setHomeCityId(null);
		setHomeCityName(null);		
		setLocCountryId(null);
		setLocCountryName(null);
		setLocStateId(null);
		setLocStateName(null);
		setLocDistrictId(null);
		setLocDistrictName(null);
		setLocCityId(null);
		setLocCityName(null);
		setCustomerType1(null);
		setCustomerType2(null);
		setAssociationCode(null);
		
	}
	
	
	
	//Getters and Setters.	

	public AssociationTaggingService getAssociationTaggingService() {
		return associationTaggingService;
	}

	public BigDecimal getCusRefNumSearch() {
		return cusRefNumSearch;
	}

	public void setCusRefNumSearch(BigDecimal cusRefNumSearch) {
		this.cusRefNumSearch = cusRefNumSearch;
	}

	public String getCivilIDSearch() {
		return civilIDSearch;
	}

	public void setCivilIDSearch(String civilIDSearch) {
		this.civilIDSearch = civilIDSearch;
	}

	public void setAssociationTaggingService(
			AssociationTaggingService associationTaggingService) {
		this.associationTaggingService = associationTaggingService;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostOffNum() {
		return postOffNum;
	}

	public void setPostOffNum(String postOffNum) {
		this.postOffNum = postOffNum;
	}

	public String getPinNum() {
		return pinNum;
	}

	public void setPinNum(String pinNum) {
		this.pinNum = pinNum;
	}

	public String getPostalArea1() {
		return postalArea1;
	}

	public void setPostalArea1(String postalArea1) {
		this.postalArea1 = postalArea1;
	}

	public String getPostalArea2() {
		return postalArea2;
	}

	public void setPostalArea2(String postalArea2) {
		this.postalArea2 = postalArea2;
	}

	public String getLocalArea1() {
		return localArea1;
	}

	public void setLocalArea1(String localArea1) {
		this.localArea1 = localArea1;
	}

	public String getLocalArea2() {
		return localArea2;
	}

	public void setLocalArea2(String localArea2) {
		this.localArea2 = localArea2;
	}

	public String getBlock1() {
		return block1;
	}

	public void setBlock1(String block1) {
		this.block1 = block1;
	}

	public String getBlock2() {
		return block2;
	}

	public void setBlock2(String block2) {
		this.block2 = block2;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getBuildingNum() {
		return buildingNum;
	}

	public void setBuildingNum(String buildingNum) {
		this.buildingNum = buildingNum;
	}

	public String getFlatNum() {
		return flatNum;
	}

	public void setFlatNum(String flatNum) {
		this.flatNum = flatNum;
	}

	public String getPhoneOff() {
		return phoneOff;
	}

	public void setPhoneOff(String phoneOff) {
		this.phoneOff = phoneOff;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}	

	public String getHpin() {
		return hpin;
	}

	public void setHpin(String hpin) {
		this.hpin = hpin;
	}

	public String getMotherMaidenName() {
		return motherMaidenName;
	}

	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}

	public BigDecimal getNationality1() {
		return nationality1;
	}

	public void setNationality1(BigDecimal nationality1) {
		this.nationality1 = nationality1;
	}

	public String getNationality2() {
		return nationality2;
	}

	public void setNationality2(String nationality2) {
		this.nationality2 = nationality2;
	}

	public String getEmployedYN() {
		return employedYN;
	}

	public void setEmployedYN(String employedYN) {
		this.employedYN = employedYN;
	}

	public BigDecimal getEmpType1() {
		return empType1;
	}

	public void setEmpType1(BigDecimal empType1) {
		this.empType1 = empType1;
	}

	public String getEmpType2() {
		return empType2;
	}

	public void setEmpType2(String empType2) {
		this.empType2 = empType2;
	}

	public String getProfession1() {
		return profession1;
	}

	public void setProfession1(String profession1) {
		this.profession1 = profession1;
	}

	public String getProfession2() {
		return profession2;
	}

	public void setProfession2(String profession2) {
		this.profession2 = profession2;
	}

	public String getPosition1() {
		return position1;
	}

	public void setPosition1(String position1) {
		this.position1 = position1;
	}

	public String getPosition2() {
		return position2;
	}

	public void setPosition2(String position2) {
		this.position2 = position2;
	}

	public String getEmployerName() {
		return employerName;
	}

	public void setEmployerName(String employerName) {
		this.employerName = employerName;
	}		

	public BigDecimal getHomeCountryId() {
		return homeCountryId;
	}

	public void setHomeCountryId(BigDecimal homeCountryId) {
		this.homeCountryId = homeCountryId;
	}

	public String getHomeCountryName() {
		return homeCountryName;
	}

	public void setHomeCountryName(String homeCountryName) {
		this.homeCountryName = homeCountryName;
	}

	public BigDecimal getHomeStateId() {
		return homeStateId;
	}

	public void setHomeStateId(BigDecimal homeStateId) {
		this.homeStateId = homeStateId;
	}

	public String getHomeStateName() {
		return homeStateName;
	}

	public void setHomeStateName(String homeStateName) {
		this.homeStateName = homeStateName;
	}

	public BigDecimal getHomeDistrictId() {
		return homeDistrictId;
	}

	public void setHomeDistrictId(BigDecimal homeDistrictId) {
		this.homeDistrictId = homeDistrictId;
	}

	public String getHomeDistrictName() {
		return homeDistrictName;
	}

	public void setHomeDistrictName(String homeDistrictName) {
		this.homeDistrictName = homeDistrictName;
	}

	public BigDecimal getHomeCityId() {
		return homeCityId;
	}

	public void setHomeCityId(BigDecimal homeCityId) {
		this.homeCityId = homeCityId;
	}

	public String getHomeCityName() {
		return homeCityName;
	}

	public void setHomeCityName(String homeCityName) {
		this.homeCityName = homeCityName;
	}

	public BigDecimal getCustomerType1() {
		return customerType1;
	}

	public void setCustomerType1(BigDecimal customerType1) {
		this.customerType1 = customerType1;
	}

	public String getCustomerType2() {
		return customerType2;
	}

	public void setCustomerType2(String customerType2) {
		this.customerType2 = customerType2;
	}	

	public BigDecimal getAssociationCode() {
		return associationCode;
	}

	public void setAssociationCode(BigDecimal associationCode) {
		this.associationCode = associationCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getCusRefNumPK() {
		return cusRefNumPK;
	}

	public void setCusRefNumPK(BigDecimal cusRefNumPK) {
		this.cusRefNumPK = cusRefNumPK;
	}

	public BigDecimal getCivilIdPK() {
		return civilIdPK;
	}

	public void setCivilIdPK(BigDecimal civilIdPK) {
		this.civilIdPK = civilIdPK;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	public IGeneralService getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService generalService) {
		this.generalService = generalService;
	}

	public List<Customer> getCompanyNames() {
		return companyNames;
	}

	public void setCompanyNames(List<Customer> companyNames) {
		this.companyNames = companyNames;
	}

	public String getNameLocal() {
		return nameLocal;
	}

	public void setNameLocal(String nameLocal) {
		this.nameLocal = nameLocal;
	}

	public BigDecimal getLocCountryId() {
		return locCountryId;
	}

	public void setLocCountryId(BigDecimal locCountryId) {
		this.locCountryId = locCountryId;
	}

	public String getLocCountryName() {
		return locCountryName;
	}

	public void setLocCountryName(String locCountryName) {
		this.locCountryName = locCountryName;
	}

	public BigDecimal getLocStateId() {
		return locStateId;
	}

	public void setLocStateId(BigDecimal locStateId) {
		this.locStateId = locStateId;
	}

	public String getLocStateName() {
		return locStateName;
	}

	public void setLocStateName(String locStateName) {
		this.locStateName = locStateName;
	}

	public BigDecimal getLocDistrictId() {
		return locDistrictId;
	}

	public void setLocDistrictId(BigDecimal locDistrictId) {
		this.locDistrictId = locDistrictId;
	}

	public String getLocDistrictName() {
		return locDistrictName;
	}

	public void setLocDistrictName(String locDistrictName) {
		this.locDistrictName = locDistrictName;
	}

	public BigDecimal getLocCityId() {
		return locCityId;
	}

	public void setLocCityId(BigDecimal locCityId) {
		this.locCityId = locCityId;
	}

	public String getLocCityName() {
		return locCityName;
	}

	public void setLocCityName(String locCityName) {
		this.locCityName = locCityName;
	}	
	
	public String getMobileNumSearch() {
		return mobileNumSearch;
	}

	public void setMobileNumSearch(String mobileNumSearch) {
		this.mobileNumSearch = mobileNumSearch;
	}


}
