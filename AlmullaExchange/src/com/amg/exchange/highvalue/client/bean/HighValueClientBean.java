package com.amg.exchange.highvalue.client.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.highvalue.client.model.HighValueClient;
import com.amg.exchange.highvalue.client.model.HighValueTransView;
import com.amg.exchange.highvalue.client.service.IHighValueClientService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("highValueClientBean")
@Scope("session")
public class HighValueClientBean<T> implements Serializable {
	  private static final Logger LOG = Logger
				.getLogger(HighValueClientBean.class);
	  private static final long serialVersionUID = 1L;

	  private BigDecimal locationId;
	  private String locationName;
	  private Date logDate;
	  private BigDecimal customerReferenceNo;
	  private String customerName;
	  private BigDecimal mobileNumber;
	  private Date visitDate;
	  private String visitBy;
	  private BigDecimal totalTranscation;
	  private BigDecimal transcationAmount;
	  private Date minDate;
	  private String employeeName;
	  private BigDecimal employeeId;

	  // Boolean Variables
	  private Boolean booSaveOrExit = false;
	  private Boolean booRenderDataTable = false;
	  private Boolean booRead = false;

	  private List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
	  private List<HighValueClientDataTable> highValueClientDtList = new CopyOnWriteArrayList<HighValueClientDataTable>();
	  private List<Employee> employeList = new ArrayList<Employee>();
	  Map<BigDecimal, String> mapCountryBranchList = new HashMap<BigDecimal, String>();
	  SessionStateManage session = new SessionStateManage();

	  @Autowired
	  IGeneralService<T> generalService;

	  @Autowired
	  IHighValueClientService highValueClientService;
	  
	  
	  private String errorMessage;

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

	  public List<HighValueClientDataTable> getHighValueClientDtList() {
		    return highValueClientDtList;
	  }

	  public void setHighValueClientDtList(List<HighValueClientDataTable> highValueClientDtList) {
		    this.highValueClientDtList = highValueClientDtList;
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

	  public BigDecimal getTotalTranscation() {
		    return totalTranscation;
	  }

	  public void setTotalTranscation(BigDecimal totalTranscation) {
		    this.totalTranscation = totalTranscation;
	  }

	  public BigDecimal getTranscationAmount() {
		    return transcationAmount;
	  }

	  public void setTranscationAmount(BigDecimal transcationAmount) {
		    this.transcationAmount = transcationAmount;
	  }

	  public Date getMinDate() {
		    return new Date();
	  }

	  public void setMinDate(Date minDate) {
		    this.minDate = minDate;
	  }

	  public List<Employee> getEmployeList() {
		    return employeList;
	  }

	  public void setEmployeList(List<Employee> employeList) {
		    this.employeList = employeList;
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
	  @Autowired
	  LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	  public void highValueClientPageNavigation() {
		    highValueClientDtList.clear();
		    setBooRenderDataTable(false);
		    setBooRead(true);
		    setBooSaveOrExit(false);
		    fetchAllBranches();
		    setLocationId(null);
		    setLocationName(null);
		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "HighValueClient.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/HighValueClient.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }

	  }

	  public void fetchAllBranches() {
		    mapCountryBranchList.clear();
		    countryBranch = new ArrayList<CountryBranch>();
		    countryBranch.addAll(generalService.getBranchDetails(session.getCountryId()));
		    for (CountryBranch countryBranch1 : countryBranch) {
			      mapCountryBranchList.put(countryBranch1.getCountryBranchId(), countryBranch1.getBranchName());
		    }
	  }

	  public void toEnbleForVisitBy(HighValueClientDataTable dataTable) {
		    if (dataTable.getVisitDate() != null) {
			      setBooRead(false);
		    } else {
			      setBooRead(true);
		    }
	  }

	  public void addRecordsToDataTable() {
		    try {
				highValueClientDtList.clear();
				employeList = highValueClientService.toFetchBasedOnLocationFromEmployee(getLocationId());
				List<HighValueTransView> highValueTransViewList = highValueClientService.toFetchAllDetilsBasedOnLocationId(getLocationId());
				if (highValueTransViewList.size() > 0) {
				      for (HighValueTransView highValueTransView : highValueTransViewList) {
						HighValueClientDataTable highValueClientDtObj = new HighValueClientDataTable();
						highValueClientDtObj.setLogDate(highValueTransView.getLogDate());
						highValueClientDtObj.setCustomerReferenceNo(highValueTransView.getCustomerReference());
						highValueClientDtObj.setCustomerName(highValueTransView.getCustomerName());
						highValueClientDtObj.setMobileNumber(highValueTransView.getMobileNumber());
						highValueClientDtObj.setVisitDate(highValueTransView.getVisitDate());
						highValueClientDtObj.setVisitBy(highValueTransView.getVisitBy());
						highValueClientDtObj.setTotalTranscation(highValueTransView.getTotalRemittance());
						highValueClientDtObj.setTranscationAmount(highValueTransView.getTotalTransaction());
						highValueClientDtList.add(highValueClientDtObj);
						setBooRead(true);
						setBooRenderDataTable(true);
						setBooSaveOrExit(true);
				      }
				} else {
				      RequestContext.getCurrentInstance().execute("noRecords.show();");
				      setBooRead(false);
				      setBooRenderDataTable(false);
				      setBooSaveOrExit(false);
				      return;
				}
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				return ;
			}

	  }

	  public void saveDataTableRecords() {
		   
		    try {

			      for (HighValueClientDataTable highValueClientDtOBJ : highValueClientDtList) {
					HighValueClient highValueClient = new HighValueClient();
					highValueClient.setLogDate(highValueClientDtOBJ.getLogDate());
					highValueClient.setCustomerReferenceNo(highValueClientDtOBJ.getCustomerReferenceNo());
					BigDecimal highValueClientPk = highValueClientService.toFetchHighValuePkBasedOnCustomerReferenceNo(highValueClientDtOBJ.getCustomerReferenceNo());
					if (highValueClientPk != null) {
						  if(highValueClientDtOBJ.getVisitDate() != null){
						  highValueClient.setVisitDate(highValueClientDtOBJ.getVisitDate());
						  highValueClient.setVisitBy(highValueClientDtOBJ.getVisitBy());
						 // String visitBy=highValueClientService.toFetchBasedOnIdTogetName(new BigDecimal(highValueClientDtOBJ.getVisitBy()));
						//  highValueClient.setVisitBy(visitBy);
						  highValueClientService.saveOrUpdate(highValueClientPk, highValueClientDtOBJ.getVisitDate(),highValueClientDtOBJ.getVisitBy());
						  }else{
							    highValueClient.setVisitDate(highValueClientDtOBJ.getVisitDate());  
							    highValueClient.setVisitBy(highValueClientDtOBJ.getVisitBy());
							    highValueClientService.saveOrUpdate(highValueClientPk, highValueClientDtOBJ.getVisitDate(), highValueClientDtOBJ.getVisitBy());   
						  }
						  
					} else {
						  RequestContext.getCurrentInstance().execute("noRecords.show();");
						  setBooRead(false);
						  setBooRenderDataTable(false);
						  setBooSaveOrExit(false);
						  return;
					}

					// highValueClientService.save(highValueClient);
			      }
			      highValueClientDtList.clear();
			      RequestContext.getCurrentInstance().execute("complete.show();");
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      setBooRead(false);
			      setLocationId(null);
			      setLocationName(null);
		    } catch (Exception e) {
			      LOG.info("e.getMessage:::::::::::::::::::::::::::::::::::::::"+e.getMessage());
			      RequestContext.getCurrentInstance().execute("error.show();");
					setErrorMessage(e.toString());
					return ;
		    }

	  }

	  public void clickOnOKSave() {
		    highValueClientDtList.clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooRead(false);
		    setLocationId(null);
		    setLocationName(null);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/HighValueClient.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  
	  public void cancel(){
		    highValueClientDtList.clear();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    setBooRead(false);
		    setLocationId(null);
		    setLocationName(null);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../highValue/HighValueClient.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  public void exit(){
		    highValueClientDtList.clear();
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}    
	  }

}
