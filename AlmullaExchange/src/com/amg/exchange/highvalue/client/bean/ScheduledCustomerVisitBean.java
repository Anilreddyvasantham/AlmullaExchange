package com.amg.exchange.highvalue.client.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.highvalue.client.model.HighValueTransDetailView;
import com.amg.exchange.highvalue.client.service.IHighValueClientService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("scheduledCustomerVisitBean")
@Scope("session")
public class ScheduledCustomerVisitBean<T> implements Serializable {

	  private static final long serialVersionUID = 1L;
	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal customerReferenceNo;
	  private String customerName;
	  private BigDecimal mobileNumber;
	  private Date visitDate;
	  private String visitBy;
	  private Date minDate;
	  private Date effectiveMaxDate;
	  private String employeeName;
	  private BigDecimal employeeId;
	  private Date formDate;
	  private Date toDate;
	  private String help;

	  // customer information related variables
	  private BigDecimal customerId;
	  private BigDecimal totalTrancation;
	  private BigDecimal totalRemittance;
	  private String followupBy;
	  private Date followUpDate;
	  private String profileUpBy;
	  private Date profileDate;
	  private String conclusionRemarks;
	  private String employeeNo;
	  private String firstNameLocal;
	  private String secondNameLocal;
	  private String thirdNameLocal;
	  private String gender;
	  private Date dateOfBrith;
	  private BigDecimal nationality;
	  private String alternateEmail;
	  private String email;
	  private String placeOfBrith;
	  private String countryOfBrith;
	  private String fatherName;
	  private BigDecimal contactNumber;
	  private String nationlityName;
	  
	  private String errorMessage;

	  // Boolean Variables
	  private Boolean booSaveOrExit = false;
	  private Boolean booRenderDataTable = false;
	  private Boolean booRead = false;

	  private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	  private List<ScheduledCustomerVisitDataTable> scheduledCustomerVisitDtList = new CopyOnWriteArrayList<ScheduledCustomerVisitDataTable>();
	  Map<BigDecimal, String> mapCountryBranchList = new HashMap<BigDecimal, String>();
	  SessionStateManage session = new SessionStateManage();

	  @Autowired
	  IGeneralService<T> generalService;

	  @Autowired
	  IHighValueClientService highValueClientService;
	  

	  public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BigDecimal getLocationId() {
		    return locationId;
	  }

	  public void setLocationId(BigDecimal locationId) {
		    this.locationId = locationId;
	  }

	  public String getLocationName() {
		    return locationName;
	  }

	  public void setLocationName(String locationName) {
		    this.locationName = locationName;
	  }

	  public List<CountryBranch> getCountryBranch() {
		    return countryBranch;
	  }

	  public void setCountryBranch(List<CountryBranch> countryBranch) {
		    this.countryBranch = countryBranch;
	  }

	  public Map<BigDecimal, String> getMapCountryBranchList() {
		    return mapCountryBranchList;
	  }

	  public void setMapCountryBranchList(Map<BigDecimal, String> mapCountryBranchList) {
		    this.mapCountryBranchList = mapCountryBranchList;
	  }

	  public List<ScheduledCustomerVisitDataTable> getScheduledCustomerVisitDtList() {
		    return scheduledCustomerVisitDtList;
	  }

	  public void setScheduledCustomerVisitDtList(List<ScheduledCustomerVisitDataTable> scheduledCustomerVisitDtList) {
		    this.scheduledCustomerVisitDtList = scheduledCustomerVisitDtList;
	  }

	  public Boolean getBooSaveOrExit() {
		    return booSaveOrExit;
	  }

	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
		    this.booSaveOrExit = booSaveOrExit;
	  }

	  public Boolean getBooRenderDataTable() {
		    return booRenderDataTable;
	  }

	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
		    this.booRenderDataTable = booRenderDataTable;
	  }

	  public Date getLogDate() {
		    return logDate;
	  }

	  public void setLogDate(Date logDate) {
		    this.logDate = logDate;
	  }

	  public BigDecimal getCustomerReferenceNo() {
		    return customerReferenceNo;
	  }

	  public void setCustomerReferenceNo(BigDecimal customerReferenceNo) {
		    this.customerReferenceNo = customerReferenceNo;
	  }

	  public String getCustomerName() {
		    return customerName;
	  }

	  public void setCustomerName(String customerName) {
		    this.customerName = customerName;
	  }

	  public BigDecimal getMobileNumber() {
		    return mobileNumber;
	  }

	  public void setMobileNumber(BigDecimal mobileNumber) {
		    this.mobileNumber = mobileNumber;
	  }

	  public Date getVisitDate() {
		    return visitDate;
	  }

	  public void setVisitDate(Date visitDate) {
		    this.visitDate = visitDate;
	  }

	  public String getVisitBy() {
		    return visitBy;
	  }

	  public void setVisitBy(String visitBy) {
		    this.visitBy = visitBy;
	  }

	  public Date getMinDate() {
		    return new Date();
	  }

	  public void setMinDate(Date minDate) {
		    this.minDate = minDate;
	  }

	  public String getEmployeeName() {
		    return employeeName;
	  }

	  public void setEmployeeName(String employeeName) {
		    this.employeeName = employeeName;
	  }

	  public BigDecimal getEmployeeId() {
		    return employeeId;
	  }

	  public void setEmployeeId(BigDecimal employeeId) {
		    this.employeeId = employeeId;
	  }

	  public Boolean getBooRead() {
		    return booRead;
	  }

	  public void setBooRead(Boolean booRead) {
		    this.booRead = booRead;
	  }

	  public Date getEffectiveMaxDate() {
		    Date now = new Date();

			Calendar cal = Calendar.getInstance();

			cal.setTime(now);

			cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.FX_DEAL_WITH_SUPPLIER_DEAL_DATE_ALLOW));

			Date tomorrow = cal.getTime();
			effectiveMaxDate=tomorrow;
			return effectiveMaxDate;
		   
	  }

	  public void setEffectiveMaxDate(Date effectiveMaxDate) {
		    this.effectiveMaxDate = effectiveMaxDate;
	  }

	  public Date getFormDate() {
		    return formDate;
	  }

	  public void setFormDate(Date formDate) {
		    this.formDate = formDate;
	  }

	  public Date getToDate() {
		    return toDate;
	  }

	  public void setToDate(Date toDate) {
		    this.toDate = toDate;
	  }

	  public String getHelp() {
		    return help;
	  }

	  public void setHelp(String help) {
		    this.help = help;
	  }

	  // customer information related setters And getters
	  public BigDecimal getCustomerId() {
		    return customerId;
	  }

	  public void setCustomerId(BigDecimal customerId) {
		    this.customerId = customerId;
	  }

	  public BigDecimal getTotalTrancation() {
		    return totalTrancation;
	  }

	  public void setTotalTrancation(BigDecimal totalTrancation) {
		    this.totalTrancation = totalTrancation;
	  }

	  public BigDecimal getTotalRemittance() {
		    return totalRemittance;
	  }

	  public void setTotalRemittance(BigDecimal totalRemittance) {
		    this.totalRemittance = totalRemittance;
	  }

	  public String getFollowupBy() {
		    return followupBy;
	  }

	  public void setFollowupBy(String followupBy) {
		    this.followupBy = followupBy;
	  }

	  public Date getFollowUpDate() {
		    return followUpDate;
	  }

	  public void setFollowUpDate(Date followUpDate) {
		    this.followUpDate = followUpDate;
	  }

	  public String getProfileUpBy() {
		    return profileUpBy;
	  }

	  public void setProfileUpBy(String profileUpBy) {
		    this.profileUpBy = profileUpBy;
	  }

	  public Date getProfileDate() {
		    return profileDate;
	  }

	  public void setProfileDate(Date profileDate) {
		    this.profileDate = profileDate;
	  }

	  public String getConclusionRemarks() {
		    return conclusionRemarks;
	  }

	  public void setConclusionRemarks(String conclusionRemarks) {
		    this.conclusionRemarks = conclusionRemarks;
	  }

	  public String getEmployeeNo() {
		    return employeeNo;
	  }

	  public void setEmployeeNo(String employeeNo) {
		    this.employeeNo = employeeNo;
	  }

	  public String getFirstNameLocal() {
		    return firstNameLocal;
	  }

	  public void setFirstNameLocal(String firstNameLocal) {
		    this.firstNameLocal = firstNameLocal;
	  }

	  public String getSecondNameLocal() {
		    return secondNameLocal;
	  }

	  public void setSecondNameLocal(String secondNameLocal) {
		    this.secondNameLocal = secondNameLocal;
	  }

	  public String getThirdNameLocal() {
		    return thirdNameLocal;
	  }

	  public void setThirdNameLocal(String thirdNameLocal) {
		    this.thirdNameLocal = thirdNameLocal;
	  }

	  public String getGender() {
		    return gender;
	  }

	  public void setGender(String gender) {
		    this.gender = gender;
	  }

	  public Date getDateOfBrith() {
		    return dateOfBrith;
	  }

	  public void setDateOfBrith(Date dateOfBrith) {
		    this.dateOfBrith = dateOfBrith;
	  }

	  public BigDecimal getNationality() {
		    return nationality;
	  }

	  public void setNationality(BigDecimal nationality) {
		    this.nationality = nationality;
	  }

	  public String getAlternateEmail() {
		    return alternateEmail;
	  }

	  public void setAlternateEmail(String alternateEmail) {
		    this.alternateEmail = alternateEmail;
	  }

	  public String getEmail() {
		    return email;
	  }

	  public void setEmail(String email) {
		    this.email = email;
	  }

	  public String getPlaceOfBrith() {
		    return placeOfBrith;
	  }

	  public void setPlaceOfBrith(String placeOfBrith) {
		    this.placeOfBrith = placeOfBrith;
	  }

	  public String getCountryOfBrith() {
		    return countryOfBrith;
	  }

	  public void setCountryOfBrith(String countryOfBrith) {
		    this.countryOfBrith = countryOfBrith;
	  }

	  public String getFatherName() {
		    return fatherName;
	  }

	  public void setFatherName(String fatherName) {
		    this.fatherName = fatherName;
	  }

	  public BigDecimal getContactNumber() {
		    return contactNumber;
	  }

	  public void setContactNumber(BigDecimal contactNumber) {
		    this.contactNumber = contactNumber;
	  }

	  public String getNationlityName() {
		    return nationlityName;
	  }

	  public void setNationlityName(String nationlityName) {
		    this.nationlityName = nationlityName;
	  }

	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	  // page Navigation for scheduledCustomer
	  public void scheduledCustomerVisitPageNavigation() {
		    scheduledCustomerVisitDtList.clear();
		    setBooRenderDataTable(false);
		    setBooRead(true);
		    setBooSaveOrExit(false);
		    fetchAllBranches();
		    clearAllFields();
		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "ScheduledCustomerVisit.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/ScheduledCustomerVisit.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }

	  }

	  // clear All Values
	  public void clearAllFields() {
		    setLocationId(null);
		    setLocationName(null);
		    setVisitDate(null);
	  }

	  // fetch all branches from loading
	  public void fetchAllBranches() {
		    mapCountryBranchList.clear();
		    countryBranch = new ArrayList<CountryBranch>();
		    countryBranch.addAll(generalService.getBranchDetails(session.getCountryId()));
		    for (CountryBranch countryBranch1 : countryBranch) {
			      mapCountryBranchList.put(countryBranch1.getCountryBranchId(), countryBranch1.getBranchName());
		    }
	  }

	  // ajax calling form check box
	  public void viewScheduledCustomer(ScheduledCustomerVisitDataTable dataTable) {
		    if (dataTable.getSelectCheckBox().equals(true)) {
			      RequestContext.getCurrentInstance().execute("selectRecrd.show();");
			      return;
		    }

	  }

	  // add records to data table
	  public void addRecordsToDataTable() {
		    scheduledCustomerVisitDtList.clear();
		    try {
			      List<HighValueTransDetailView> lstHighValueTransDetailView = highValueClientService.toFetchAllDetailsFromView(getLocationId(), getVisitDate(), session.getCountryId());
			      if (lstHighValueTransDetailView.size() > 0) {
					for (HighValueTransDetailView highValueTransDetailView : lstHighValueTransDetailView) {
						  ScheduledCustomerVisitDataTable scheduledCustomerVisitDtobj = new ScheduledCustomerVisitDataTable();
						  scheduledCustomerVisitDtobj.setLogDate(highValueTransDetailView.getLogDate());
						  scheduledCustomerVisitDtobj.setCustomerReferenceNo(highValueTransDetailView.getCustomerReference());
						  scheduledCustomerVisitDtobj.setCustomerName(highValueTransDetailView.getFirstName());
						  scheduledCustomerVisitDtobj.setMobileNumber(new BigDecimal(highValueTransDetailView.getMobile()));
						  scheduledCustomerVisitDtobj.setVisitDate(highValueTransDetailView.getVisitDate());
						  scheduledCustomerVisitDtobj.setVisitBy(highValueTransDetailView.getVisitBy());
						  scheduledCustomerVisitDtobj.setHelp("Help");
						  // customer Related
						  scheduledCustomerVisitDtobj.setTotalTrancation(highValueTransDetailView.getTotalTransaction());
						  scheduledCustomerVisitDtobj.setTotalRemittance(highValueTransDetailView.getTotalRemittance());
						  scheduledCustomerVisitDtobj.setCustomerId(highValueTransDetailView.getCustomerId());
						  scheduledCustomerVisitDtobj.setFollowupBy(highValueTransDetailView.getFollowUpBy());
						  scheduledCustomerVisitDtobj.setFollowUpDate(highValueTransDetailView.getFollowUpDate());
						  scheduledCustomerVisitDtobj.setProfileUpBy(highValueTransDetailView.getProfileBy());
						  scheduledCustomerVisitDtobj.setProfileDate(highValueTransDetailView.getProfileDate());
						  scheduledCustomerVisitDtobj.setConclusionRemarks(highValueTransDetailView.getConclusionRemarks());
						  scheduledCustomerVisitDtobj.setEmployeeNo(highValueTransDetailView.getEmployeeNo());
						  scheduledCustomerVisitDtobj.setFirstNameLocal(highValueTransDetailView.getFirstNameLocal());
						  scheduledCustomerVisitDtobj.setSecondNameLocal(highValueTransDetailView.getMiddleNameLocal());
						  scheduledCustomerVisitDtobj.setThirdNameLocal(highValueTransDetailView.getLastNameLocal());
						  scheduledCustomerVisitDtobj.setGender(highValueTransDetailView.getGender());
						  scheduledCustomerVisitDtobj.setDateOfBrith(highValueTransDetailView.getDateOfBirth());
						  scheduledCustomerVisitDtobj.setNationality(highValueTransDetailView.getNationality());
						  scheduledCustomerVisitDtobj.setAlternateEmail(highValueTransDetailView.getAlterEmailId());
						  scheduledCustomerVisitDtobj.setEmail(highValueTransDetailView.getEmail());
						  scheduledCustomerVisitDtobj.setPlaceOfBrith(highValueTransDetailView.getPlaceOfBirth());
						  scheduledCustomerVisitDtobj.setCountryOfBrith(highValueTransDetailView.getCountryOfBirth());
						  scheduledCustomerVisitDtobj.setFatherName(highValueTransDetailView.getFatherName());
						  scheduledCustomerVisitDtobj.setContactNumber(highValueTransDetailView.getContactNumber());

						  scheduledCustomerVisitDtList.add(scheduledCustomerVisitDtobj);
						  setBooRenderDataTable(true);
						  setBooSaveOrExit(true);
						  clearAllFields();
					}
			      } else {
					if (scheduledCustomerVisitDtList.size() > 0) {
						  RequestContext.getCurrentInstance().execute("noRecords.show();");
						  setBooRenderDataTable(true);
						  setBooSaveOrExit(true);
						  clearAllFields();
						  return;
					} else {
						  RequestContext.getCurrentInstance().execute("noRecords.show();");
						  setBooRenderDataTable(false);
						  setBooSaveOrExit(false);
						  clearAllFields();
					}
			      }
		    } catch (Exception e) {
		    	RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				return ;
		    }
	  }

	  // click on help button From DataTable
	  public void clickOnHelpButtonFromDataTable(ScheduledCustomerVisitDataTable dataTable) {
		    setCustomerId(dataTable.getCustomerId());
		    setCustomerName(dataTable.getCustomerName());
		    setFirstNameLocal(dataTable.getFirstNameLocal());
		    setSecondNameLocal(dataTable.getSecondNameLocal());
		    setThirdNameLocal(dataTable.getThirdNameLocal());
		    setCustomerReferenceNo(dataTable.getCustomerReferenceNo());
		    setMobileNumber(dataTable.getMobileNumber());
		    setGender(dataTable.getGender());
		    setDateOfBrith(dataTable.getDateOfBrith());
		    setNationality(dataTable.getNationality());
		    setPlaceOfBrith(dataTable.getPlaceOfBrith());
		    setCountryOfBrith(dataTable.getCountryOfBrith());
		    setEmail(dataTable.getEmail());
		    setAlternateEmail(dataTable.getAlternateEmail());
		    setFatherName(dataTable.getFatherName());
		    setContactNumber(dataTable.getContactNumber());
		    setNationlityName(highValueClientService.toFetchnationalityNameBasedOnNationalityId(dataTable.getNationality()));
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/CustomerInformationForHelp.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

	  // click on accept button from dialog box
	  public void clickonAccept() {
		    for (ScheduledCustomerVisitDataTable scheduledCustomerVisitDtOBJ : scheduledCustomerVisitDtList) {
			      scheduledCustomerVisitDtOBJ.setFormDate(getFormDate());
			      scheduledCustomerVisitDtOBJ.setToDate(getToDate());
			      scheduledCustomerVisitDtOBJ.setSelectCheckBox(false);
		    }
	  }

	  // validation from dialog box
	  public void checkFromDateValidator() {
		    if (getFormDate() != null) {
			      setToDate(null);
		    }
	  }

	  public void checkToDateValidator() {
		    if (getFormDate() != null) {
			      if (getFormDate().after(getToDate())) {
					setToDate(null);
					RequestContext.getCurrentInstance().execute("datevalid.show();");
					return;
			      }
		    }
	  }

	  // after save click on OK
	  public void clickOnOKSave() {
		    scheduledCustomerVisitDtList.clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooRead(false);
		    clearAllFields();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/ScheduledCustomerVisit.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

	  // cancel from dialog box
	  public void cancel() {
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    clearAllFields();
		    setFormDate(null);
		    setToDate(null);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/ScheduledCustomerVisit.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

	  // Customer Information Cancel Help
	  public void cancelOfCustomerInformationHelp() {
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    clearAllFields();
		    setFormDate(null);
		    setToDate(null);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/ScheduledCustomerVisit.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

	  // exit
	  public void exit() {
		    scheduledCustomerVisitDtList.clear();
		    clearAllFields();
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }

}
