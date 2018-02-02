package com.amg.exchange.currency.inquiry.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.currency.inquiry.service.IBranchInterchangeService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("branchInterchangeBean")
@Scope("session")
public class BranchInterchangeBean<T> {

	private BigDecimal employeeId;
	private BigDecimal branchId;
	private BigDecimal countryBrachId;
	private String employeeName;
	private String userName;
	private String existLocation;
	private String errorMessage;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<Employee> lstOfEmployees = new ArrayList<Employee>();
	private List<CountryBranch> lstOfCountryBranch = new ArrayList<CountryBranch>();

	@Autowired
	IBranchInterchangeService branchInterchangeService;
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public BranchInterchangeBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public List<Employee> getLstOfEmployees() {
		return lstOfEmployees;
	}

	public void setLstOfEmployees(List<Employee> lstOfEmployees) {
		this.lstOfEmployees = lstOfEmployees;
	}

	public List<CountryBranch> getLstOfCountryBranch() {
		return lstOfCountryBranch;
	}

	public void setLstOfCountryBranch(List<CountryBranch> lstOfCountryBranch) {
		this.lstOfCountryBranch = lstOfCountryBranch;
	}

	public IBranchInterchangeService getBranchInterchangeService() {
		return branchInterchangeService;
	}

	public void setBranchInterchangeService(IBranchInterchangeService branchInterchangeService) {
		this.branchInterchangeService = branchInterchangeService;
	}

	public BigDecimal getCountryBrachId() {
		return countryBrachId;
	}

	public void setCountryBrachId(BigDecimal countryBrachId) {
		this.countryBrachId = countryBrachId;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

	public String getExistLocation() {
		return existLocation;
	}

	public void setExistLocation(String existLocation) {
		this.existLocation = existLocation;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void branchInterchangeNavigation() {

		try {
			clearAll();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "branchinterchange.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/branchinterchange.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Employee> getEmployeeList() {

		lstOfEmployees = getBranchInterchangeService().getAllEmployeeList(sessionStateManage.getCountryId());

		return lstOfEmployees;
	}

	public List<CountryBranch> getcountryBranchList() {

		try {
			lstOfEmployees = getBranchInterchangeService().getEmployeeList(getEmployeeId());

			if (lstOfEmployees != null) {
				for (Employee employee : lstOfEmployees) {
					setCountryBrachId(employee.getFsCountryBranch().getCountryBranchId());
					setEmployeeName(employee.getEmployeeName());
					setUserName(employee.getUserName());
					setExistLocation(employee.getLocation());
				}
			}

			lstOfCountryBranch = getBranchInterchangeService().getCountryBranchList(sessionStateManage.getCountryId(), getCountryBrachId());

			return lstOfCountryBranch;
		} catch (Exception e) {
			
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return null;
		}
	}
	
	private String newLocation;
	
	

	public String getNewLocation() {
		return newLocation;
	}

	public void setNewLocation(String newLocation) {
		this.newLocation = newLocation;
	}

	public void save() {
		
		for (CountryBranch country:lstOfCountryBranch) {
			
			if(country.getCountryBranchId().equals(getBranchId()))
			{
				setNewLocation(country.getBranchName());
			}
		}

		try {
			getBranchInterchangeService().updateRecord(getEmployeeId(), getBranchId(),getNewLocation());

			RequestContext.getCurrentInstance().execute("complete.show();");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return ;
		}

	}

	public void clearAll() {
		setBranchId(null);
		setEmployeeId(null);
		setUserName(null);
		setEmployeeName(null);
		setExistLocation(null);
		

	}
	
	public void exit() {
		

		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				
				clearAll();
			

		} catch (Exception e) {
			//log.info("Problem to redirect:" + e);
		}

		
	}

}
