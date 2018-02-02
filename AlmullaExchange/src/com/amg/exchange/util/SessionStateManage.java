package com.amg.exchange.util;

import java.math.BigDecimal;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/*******************************************************************************************************************
 * File : SessionStateManage.java
 * 
 * Project : AlmullaExchange
 * 
 * Package : com.amg.exchange.util
 * 
 * Created : Date : 22-Apr-2014 3:46:42  : Justin Vincent Revision:
 * 
 * Last Change: Date : 2014-04-01 By : Justin Vincent Revision:
 * 
 * Description: TODO
 ********************************************************************************************************************/
public class SessionStateManage {

	public String getSessionValue(String sessionObjectName) {
		
		
		if (isExists(sessionObjectName)) {
			return FacesContext.getCurrentInstance().getExternalContext()
					.getSessionMap().get(sessionObjectName).toString();
		} else {
			return null;
		}
	}

	public void setSessionValue(String sessionObjectName,
			String sessionObjectValue) {

		FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.put(sessionObjectName, sessionObjectValue);
	}

	public boolean isExists(String sessionObjectName) {

		return FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().containsKey(sessionObjectName);
	}

	public BigDecimal getLevel() {

		StringBuffer sbf = new StringBuffer();

		sbf.append(isExists("applicationId") ? "1" : "0");
		sbf.append(isExists("companyId") ? "1" : "0");
		sbf.append(isExists("countryId") ? "1" : "0");
		sbf.append(isExists("pageId") ? "1" : "0");

		return new BigDecimal(Integer.parseInt(sbf.toString(), 2));
	}

	public BigDecimal getApplicationId() {

		return new BigDecimal(
				isExists("applicationId") ? getSessionValue("applicationId")
						: "0");
	}

	public BigDecimal getCompanyId() {

		return new BigDecimal(
				isExists("companyId") ? getSessionValue("companyId")
						: "0");
	}

	public BigDecimal getCountryId() {

		return new BigDecimal(
				isExists("countryId") ? getSessionValue("countryId")
						: "0");
	}

	public BigDecimal getPageId() {
		
		return new BigDecimal(
				isExists("pageId") ? getSessionValue("pageId")
						: "0");
	}
	
	public BigDecimal getLanguageId() {

		return new BigDecimal(
				isExists("languageId") ? getSessionValue("languageId")
						: "1");
	}
	
	public String getUserName() {

		return isExists("userName") ? getSessionValue("userName"): "root";
	}

	public String getLocation() {

		return isExists("location") ? getSessionValue("location"): "Kuwait";
	}
	
	public String getTelephoneNumber() {

		return isExists("telephoneNumber") ? getSessionValue("telephoneNumber"): "94415472";
	}
	
	public String getBranchId() {

		return isExists("branchId") ? getSessionValue("branchId"): "1";
	}
	
	public String getRoleId() {

		return isExists("roleId") ? getSessionValue("roleId"): "0";
	}
	
	public String getCurrencyId() {
		return isExists("currencyId") ? getSessionValue("currencyId"): "1";
	}
	
	public BigDecimal getEmployeeId() {

		return new BigDecimal(
				isExists("employeeId") ? getSessionValue("employeeId")
						: "0");
	}
	
	public String getEmail(){
		return isExists("email") ? getSessionValue("email"): "1";

		
	}
	
	public BigDecimal getCustomerId() {

		return new BigDecimal(
				isExists("customerId") ? getSessionValue("customerId")
						: "0");
	}
	
	public String getCountryAlphaTwoCode() {

		return isExists("countryAlphaTwoCode") ? getSessionValue("countryAlphaTwoCode"): "KW";
	}

	public String getCustomerType() {

		return isExists("customerType") ? getSessionValue("customerType"): "E";
	}
	
	public BigDecimal getCountryBranchCode() {

		return new BigDecimal(
				isExists("branchCode") ? getSessionValue("branchCode")
						: "0");
	}
	
	public BigDecimal getWUUsername() {

		return new BigDecimal(
				isExists("wuUsername") ? getSessionValue("wuUsername")
						: "0");
	}
	
	public String getMenuPage() {

		return isExists("menuPage") ? getSessionValue("menuPage"): "../registration/branchhome.xhtml";
	}
	
	@Override
	public String toString() {
		return "SessionStateManage [getLevel()=" + getLevel() + ", getApplicationId()=" + getApplicationId() + ", getCompanyId()=" + getCompanyId() + ", getCountryId()=" + getCountryId() + ", getPageId()=" + getPageId() + ", getLanguageId()=" + getLanguageId() + ", getUserName()=" + getUserName()
				+ ", getLocation()=" + getLocation() + ", getTelephoneNumber()=" + getTelephoneNumber() + ", getBranchId()=" + getBranchId() + ", getRoleId()=" + getRoleId() + ", getCurrencyId()=" + getCurrencyId() + ", getEmployeeId()=" + getEmployeeId() + ", getEmail()=" + getEmail()
				+ ", getCustomerId()=" + getCustomerId() + ", getCountryAlphaTwoCode()=" + getCountryAlphaTwoCode() + ",getCustomerType()=" +getCustomerType() + "]";
	}
	
	public  HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(true);
    }
	
	/**
	 * 
	 * @return :added by Rabil on 05/01/2015.
	 */
	public String getUserType(){
		return isExists("userType") ? getSessionValue("userType"): "1";
	}
	
	public String getCountryName(){
		return isExists("countryName") ? getSessionValue("countryName"): "1";
	}
	

	
	
	
}
