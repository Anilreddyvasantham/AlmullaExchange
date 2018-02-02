package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.enquiry.service.IReceiptEnquiryService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.IParameterSecurityMaintainceService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;
@Component("employeeAssignmentBean")
@Scope("session")
public class EmployeeAssignmentBean implements Serializable {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;
	  private static final Logger log=Logger.getLogger(EmployeeAssignmentBean.class);
	  private BigDecimal employeePk;
	  private BigDecimal applicationCountryId;
	  private String applicationCountryName;
	  private BigDecimal locationId;
	  private String locationName;
	  private BigDecimal employeeId;
	  private String employeeName;
	  private String currentLocation;
	  private BigDecimal toLocationId;
	  private String toLocationName;
	  private BigDecimal webServiceUserName;
	  private String webServicePassword;
	  private String currentLocationIpAddress;
	  private String toLocationIpAddress;
	  private Boolean booRenderIp=false;
	  private Boolean booRenderAccept=false;
	  private Boolean booEmpPanel=false;
	  private String errorMessage;
	  private String resetWebPassword;
	  private String userName;
	  private SessionStateManage sessionStateManage=new SessionStateManage();
	  
	  private List<CountryBranch> lstCountryBranch=new ArrayList<CountryBranch>();
	  private List<CountryBranch> countryBranchList=new ArrayList<CountryBranch>();
	  private List<Employee> lstEmployee=new ArrayList<Employee>();
	  private List<CountryMasterDesc> lstCountryMasterDescs=new ArrayList<CountryMasterDesc>();
	  public BigDecimal getApplicationCountryId() {
	  	  return applicationCountryId;
	  }
	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
	  	  this.applicationCountryId = applicationCountryId;
	  }
	  public String getApplicationCountryName() {
	  	  return applicationCountryName;
	  }
	  public void setApplicationCountryName(String applicationCountryName) {
	  	  this.applicationCountryName = applicationCountryName;
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
	  public BigDecimal getEmployeeId() {
	  	  return employeeId;
	  }
	  public void setEmployeeId(BigDecimal employeeId) {
	  	  this.employeeId = employeeId;
	  }
	  public String getEmployeeName() {
	  	  return employeeName;
	  }
	  public void setEmployeeName(String employeeName) {
	  	  this.employeeName = employeeName;
	  }
	  public String getCurrentLocation() {
	  	  return currentLocation;
	  }
	  public void setCurrentLocation(String currentLocation) {
	  	  this.currentLocation = currentLocation;
	  }
	  public BigDecimal getToLocationId() {
	  	  return toLocationId;
	  }
	  public void setToLocationId(BigDecimal toLocationId) {
	  	  this.toLocationId = toLocationId;
	  }
	  public String getToLocationName() {
	  	  return toLocationName;
	  }
	  public void setToLocationName(String toLocationName) {
	  	  this.toLocationName = toLocationName;
	  }
	  public BigDecimal getWebServiceUserName() {
	  	  return webServiceUserName;
	  }
	  public void setWebServiceUserName(BigDecimal webServiceUserName) {
	  	  this.webServiceUserName = webServiceUserName;
	  }
	  public String getWebServicePassword() {
	  	  return webServicePassword;
	  }
	  public void setWebServicePassword(String webServicePassword) {
	  	  this.webServicePassword = webServicePassword;
	  }
	  public String getCurrentLocationIpAddress() {
	  	  return currentLocationIpAddress;
	  }
	  public void setCurrentLocationIpAddress(String currentLocationIpAddress) {
	  	  this.currentLocationIpAddress = currentLocationIpAddress;
	  }
	  public String getToLocationIpAddress() {
	  	  return toLocationIpAddress;
	  }
	  public void setToLocationIpAddress(String toLocationIpAddress) {
	  	  this.toLocationIpAddress = toLocationIpAddress;
	  }
	  public Boolean getBooRenderIp() {
	  	  return booRenderIp;
	  }
	  public void setBooRenderIp(Boolean booRenderIp) {
	  	  this.booRenderIp = booRenderIp;
	  }
	  public Boolean getBooRenderAccept() {
	  	  return booRenderAccept;
	  }
	  public void setBooRenderAccept(Boolean booRenderAccept) {
	  	  this.booRenderAccept = booRenderAccept;
	  }
	  public List<CountryBranch> getLstCountryBranch() {
	  	  return lstCountryBranch;
	  }
	  public void setLstCountryBranch(List<CountryBranch> lstCountryBranch) {
	  	  this.lstCountryBranch = lstCountryBranch;
	  }
	  public List<Employee> getLstEmployee() {
	  	  return lstEmployee;
	  }
	  public void setLstEmployee(List<Employee> lstEmployee) {
	  	  this.lstEmployee = lstEmployee;
	  }
	  public String getErrorMessage() {
	  	  return errorMessage;
	  }
	  public void setErrorMessage(String errorMessage) {
	  	  this.errorMessage = errorMessage;
	  }
	  public List<CountryBranch> getCountryBranchList() {
	  	  return countryBranchList;
	  }
	  public void setCountryBranchList(List<CountryBranch> countryBranchList) {
	  	  this.countryBranchList = countryBranchList;
	  }
	  public Boolean getBooEmpPanel() {
	  	  return booEmpPanel;
	  }
	  public void setBooEmpPanel(Boolean booEmpPanel) {
	  	  this.booEmpPanel = booEmpPanel;
	  }
	  public List<CountryMasterDesc> getLstCountryMasterDescs() {
	  	  return lstCountryMasterDescs;
	  }
	  public void setLstCountryMasterDescs(List<CountryMasterDesc> lstCountryMasterDescs) {
	  	  this.lstCountryMasterDescs = lstCountryMasterDescs;
	  }
	  public BigDecimal getEmployeePk() {
	  	  return employeePk;
	  }
	  public void setEmployeePk(BigDecimal employeePk) {
	  	  this.employeePk = employeePk;
	  }
	  public String getResetWebPassword() {
	  	  return resetWebPassword;
	  }
	  public void setResetWebPassword(String resetWebPassword) {
	  	  this.resetWebPassword = resetWebPassword;
	  }
	  public String getUserName() {
	  	  return userName;
	  }
	  public void setUserName(String userName) {
	  	  this.userName = userName;
	  }
	  public void employeeAssignmentPageNavigation(){
		    lstCountryBranch.clear();
		    lstCountryMasterDescs.clear();
		    setBooRenderIp(false);
		    setBooRenderAccept(false);
		    setBooEmpPanel(false);
		    setApplicationCountryId(null);
		    clearAll();
		    toFetchLocation();
		    toFetchCountry();
		    try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/EmployeeAssignment.xhtml");
			} catch (Exception exception) {
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        		      setErrorMessage(exception.getMessage());
        		      RequestContext.getCurrentInstance().execute("error.show();");
        			return;
			}
	  }
	  
	  public void clearAll(){
		    setLocationId(null);
		    setEmployeeId(null);
		    setCurrentLocation(null);
		    setToLocationId(null);
		    setBooRenderIp(false);
		    setBooRenderAccept(false);
		    setBooEmpPanel(false);
		    countryBranchList.clear();
	  }
	  
	  public void clearPanel(){
		    setEmployeeId(null);
		    setCurrentLocation(null);
		    setToLocationId(null);
		    setBooRenderIp(false);
		    setBooRenderAccept(false);
		    setBooEmpPanel(false);
		    setWebServiceUserName(null);
		    setWebServicePassword(null);
		    setCurrentLocationIpAddress(null);
		    setToLocationIpAddress(null);
		    countryBranchList.clear();
	  }
	  
	  @Autowired
	  IGeneralService<T> generalService;
	  
		  
	  @Autowired
	  IEmployeeService employeeService;
	  
	  @Autowired
	  IParameterSecurityMaintainceService parameterSecurityMaintainceService;
	  
	  
	  public void toFetchLocation(){
		    List<CountryBranch> countryBranchs=generalService.getBranchDetails(sessionStateManage.getCountryId());
		    if(countryBranchs.size() !=0){
			      lstCountryBranch.addAll(countryBranchs);
		    }
	  }
	  
	  public void toFetchEmployee(){
		    lstEmployee.clear();
		    clearPanel();
		    List<Employee> employeeList=parameterSecurityMaintainceService.getAllBranchList(getLocationId());
		    if(employeeList.size() !=0){
			      lstEmployee.addAll(employeeList);
		    }
	  }
	  
	  public void toFetchCountry(){
		    List<CountryMasterDesc> countryMasterDescs=employeeService.getBusinessCounty(sessionStateManage.getLanguageId(), sessionStateManage.getCountryId());
		    if(countryMasterDescs.size() !=0){
			      setApplicationCountryId(countryMasterDescs.get(0).getFsCountryMaster().getCountryId());
			      setApplicationCountryName(countryMasterDescs.get(0).getCountryName());
		    }
	  }
	  
	 public void toLoctionAllDetails(){
		   countryBranchList.clear();
		   setToLocationId(null);
		   List<CountryBranch> CountryBranchLt=employeeService.getAllBranchDetails(sessionStateManage.getCountryId(),getLocationId());
		    if(CountryBranchLt.size() !=0){
			      countryBranchList.addAll(CountryBranchLt); 
		    } 
	 }
	 
	 public void renderIpPanel(){
		   if(getToLocationId() != null){
			     setBooRenderIp(true);
		   }else{
			     setBooRenderIp(false);
		   }
	 }
	 public void goEmpDeatils(){
		   setBooRenderIp(false);
		    setBooRenderAccept(true);
		    setBooEmpPanel(true); 
		    toLoctionAllDetails();
		    String wuPassWord=null;
		    List<Employee> employeeList=employeeService.toFetchEmployeeDetils(sessionStateManage.getCountryId(),getLocationId(),getEmployeeId());
		    if(employeeList.size() !=0){
			      for (Employee employee : employeeList) {
					setEmployeePk(employee.getEmployeeId());
					//setEmployeeId(employee.getEmployeeId());
					setEmployeeName(employee.getEmployeeName());
					setLocationId(employee.getFsCountryBranch().getCountryBranchId());
					setCurrentLocation(employee.getFsCountryBranch().getBranchName());
					setUserName(employee.getUserName());
					iCypherSecurity cypherSecurity = new CypherSecurityImpl();
					if(employee.getWuPassword() != null){
						  StringBuffer secretKeys = new StringBuffer().reverse();
						  wuPassWord=cypherSecurity.encodePassword(employee.getWuPassword(), secretKeys.toString());
						  //setWebServicePassword(employee.getWuPassword());
						  setResetWebPassword(employee.getWuPassword());
						  
						  
					}
					setWebServiceUserName(employee.getWuUsername());
					System.out.println("wuPassWord::::::::::::::::"+employee.getWuPassword());
					setCurrentLocationIpAddress(employee.getIpAddress());
			      }
		    }
		  
	 }
	 
	
	
	public void save(){
		  try{
		if(getToLocationId() != null){
			  
			  if(getWebServicePassword() !=null && !getWebServicePassword().isEmpty()){
				    iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				    String userNames = getUserName();
					String userName1 = new StringBuffer(userNames).reverse().toString().toUpperCase();
				    String localPw =cypherSecurity.encodePassword(getWebServicePassword(),userName1); 
				    if(getResetWebPassword().equalsIgnoreCase(localPw)){
				    employeeService.saveAllValues(getEmployeePk(),getToLocationId(),getWebServiceUserName(),getResetWebPassword(),getToLocationIpAddress());
				    RequestContext.getCurrentInstance().execute("complete.show();");
					  return;
				    }else{
				      employeeService.saveAllValues(getEmployeePk(),getToLocationId(),getWebServiceUserName(),localPw,getToLocationIpAddress());
					  RequestContext.getCurrentInstance().execute("complete.show();");
					  return;      
				    }
				    
			  }else{
				      employeeService.saveAllValues(getEmployeePk(),getToLocationId(),getWebServiceUserName(),getResetWebPassword(),getToLocationIpAddress());
					  RequestContext.getCurrentInstance().execute("complete.show();");
					  return;      
				    
			  }
		}else{
			  if(getWebServicePassword() !=null && !getWebServicePassword().isEmpty()){
				    iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				    String userNames = getUserName();
					String userName1 = new StringBuffer(userNames).reverse().toString().toUpperCase();
				    String localPw =cypherSecurity.encodePassword(getWebServicePassword(),userName1); 
				    if(getResetWebPassword().equalsIgnoreCase(localPw)){
				    employeeService.saveAllValues(getEmployeePk(),getLocationId(),getWebServiceUserName(),getResetWebPassword(),getCurrentLocationIpAddress());
				    RequestContext.getCurrentInstance().execute("complete.show();");
					  return;
				    }else{
				      employeeService.saveAllValues(getEmployeePk(),getLocationId(),getWebServiceUserName(),localPw,getCurrentLocationIpAddress());
					  RequestContext.getCurrentInstance().execute("complete.show();");
					  return;      
				    }
			  }else{
		  employeeService.saveAllValues(getEmployeePk(),getLocationId(),getWebServiceUserName(),getResetWebPassword(),getCurrentLocationIpAddress());
		  RequestContext.getCurrentInstance().execute("complete.show();");
		  return;
			  }
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage());
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;
		  }
	}

	public void clickOnOKSave(){
		  clearPanel();
		   clearAll();
		   setBooRenderIp(false);
		   setBooEmpPanel(false);
		   setBooRenderAccept(false);
		
	}
}
