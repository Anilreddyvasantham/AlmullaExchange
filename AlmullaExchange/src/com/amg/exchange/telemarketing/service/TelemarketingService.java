package com.amg.exchange.telemarketing.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.telemarketing.bean.TelemarketingBranchWiseTable;
import com.amg.exchange.telemarketing.model.TelemartCustomer;
import com.amg.exchange.telemarketing.model.TelemartFolwUp;
import com.amg.exchange.telemarketing.model.VwExTelemartFolwUpCode;

public interface TelemarketingService {
	
	public List<Customer> getCustomersList();
	
	public void saveTelemarkCustomers(TelemartCustomer telemarketing);
	
	public String getNationality(BigDecimal languageId,BigDecimal coutryId);	
	
    public List<Employee> getEmployeesSameNationality(BigDecimal countryId,BigDecimal countryBranchId);
	
	public List<Employee> getEmployeesDiffNationality(BigDecimal countryId,BigDecimal countryBranchId);
	
	//public void saveTelemartEmployee(TelemartEmployee telemartEmployee,BigDecimal telmartBranchwiseTableId,BigDecimal telemartEmpPK);
	
	public List<Employee> getTelemartEmployeeName(BigDecimal employeeId);
	
	public List<Customer> getCustomerList(BigDecimal cusId);
	
	//public List<TelemartEmployee> getTelemartEmployeeList();
	
	public List<TelemartCustomer> getTelemartCustomerList(BigDecimal countryBranchId);
	
	public BigDecimal getCountryBranchId(BigDecimal loginBranchId);
	
	public void saveTelemartFolwup(TelemartFolwUp telemartFolwUp,BigDecimal telemartCusPK);
	
	//public List<TelemartEmployee> getTelemartEmpPk(BigDecimal cusId);
	
	public List<TelemartCustomer> getTelCusList(BigDecimal empId);
	
	public List<Employee> getEmployeeNames(BigDecimal countryBranchId);
	
	public List<TelemartFolwUp> getTelemartFollowupPk(BigDecimal cusId);
	
	public List<TelemartCustomer> getFollowUpTableList(BigDecimal branchId,BigDecimal empId);
	
	public List<VwExTelemartFolwUpCode> getTelemartFollowUpCodes();
	
	public List<Object []> getCustomersCount(BigDecimal branchId);
	
	public String getTelemartFollowUpCodes(String followUpCode);
	
	public List<TelemartCustomer> getFollowUpTableListInquiry(BigDecimal branchId,BigDecimal empId);
	
	public List<TelemartCustomer> getTelemartEmpPk(BigDecimal cusId);
	
	public void saveTelemartEmployee(List<TelemarketingBranchWiseTable> lstTeleCust,BigDecimal empId);
	
	public List<TelemartCustomer> getTelemartCustomerDataByStaff(BigDecimal countryBranchId);
	
	public void saveBranchTransfer(TelemartCustomer telemartCustomer,BigDecimal toBranchId,BigDecimal fromBranchId);
	
	public BigDecimal branchTransferProcedure(BigDecimal toBranchId,BigDecimal fromBranchId,BigDecimal nationality);

}
