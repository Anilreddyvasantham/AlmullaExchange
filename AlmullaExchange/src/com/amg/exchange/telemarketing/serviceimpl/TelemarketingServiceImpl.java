package com.amg.exchange.telemarketing.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.telemarketing.bean.TelemarketingBranchWiseTable;
import com.amg.exchange.telemarketing.dao.TelemarketingDAO;
import com.amg.exchange.telemarketing.model.TelemartCustomer;
import com.amg.exchange.telemarketing.model.TelemartFolwUp;
import com.amg.exchange.telemarketing.model.VwExTelemartFolwUpCode;
import com.amg.exchange.telemarketing.service.TelemarketingService;


@Service("telemarketingService")
public class TelemarketingServiceImpl implements TelemarketingService {
	
	@Autowired
	TelemarketingDAO telemarketingDAO;
	
	
	@Override
	@Transactional
	public List<Customer> getCustomersList() {
		return telemarketingDAO.getCustomersList();
	}
	
	@Override
	@Transactional
	public void saveTelemarkCustomers(TelemartCustomer telemarketing) {
		telemarketingDAO.saveTelemarkCustomers(telemarketing);
	}
	
	@Override
	@Transactional
	public List<Employee> getEmployeesSameNationality(BigDecimal countryId,BigDecimal countryBranchId) {
		return telemarketingDAO.getEmployeesSameNationality(countryId,countryBranchId);
	}
	
	@Override
	@Transactional
	public List<Employee> getEmployeesDiffNationality(BigDecimal countryId,BigDecimal countryBranchId) {
		return telemarketingDAO.getEmployeesDiffNationality(countryId,countryBranchId);
	}
	
	@Override
	@Transactional
	public String getNationality(BigDecimal languageId,BigDecimal coutryId) {
		return telemarketingDAO.getNationality(languageId,coutryId);
	}	
	
	/*@Override
	@Transactional
	public void saveTelemartEmployee(TelemartEmployee telemartEmployee,BigDecimal telmartBranchwiseTableId,BigDecimal telemartEmpPK) {
		telemarketingDAO.saveTelemartEmployee(telemartEmployee,telmartBranchwiseTableId,telemartEmpPK);
	}*/
	
	@Override
	@Transactional
	public List<Employee> getTelemartEmployeeName(BigDecimal employeeId) {
		return telemarketingDAO.getTelemartEmployeeName(employeeId);
	}
	
	@Override
	@Transactional
	public List<Customer> getCustomerList(BigDecimal cusId) {
		return telemarketingDAO.getCustomerList(cusId);
	}
	
	/*@Override
	@Transactional
	public List<TelemartEmployee> getTelemartEmployeeList() {
		return telemarketingDAO.getTelemartEmployeeList();
	}*/
	
	@Override
	@Transactional
	public List<TelemartCustomer> getTelemartCustomerList(BigDecimal countryBranchId) {
		return telemarketingDAO.getTelemartCustomerList(countryBranchId);
	}
	
	@Override
	@Transactional
	public BigDecimal getCountryBranchId(BigDecimal loginBranchId) {
		return telemarketingDAO.getCountryBranchId(loginBranchId);
	}
	
	@Override
	@Transactional
	public void saveTelemartFolwup(TelemartFolwUp telemartFolwUp,BigDecimal telemartCusPK) {
		telemarketingDAO.saveTelemartFolwup(telemartFolwUp,telemartCusPK);
	}
	
	@Override
	@Transactional
	public List<TelemartCustomer> getTelemartEmpPk(BigDecimal cusId) {
		return telemarketingDAO.getTelemartEmpPk(cusId);
	}
	
	@Override
	@Transactional
	public List<TelemartCustomer> getTelCusList(BigDecimal empId) {
		return telemarketingDAO.getTelCusList(empId);
	}
	
	@Override
	@Transactional
	public List<Employee> getEmployeeNames(BigDecimal countryBranchId) {
		return telemarketingDAO.getEmployeeNames(countryBranchId);
	}
	
	@Override
	@Transactional
	public List<TelemartFolwUp> getTelemartFollowupPk(BigDecimal cusId) {
		return telemarketingDAO.getTelemartFollowupPk(cusId);
	}
	
	@Override
	@Transactional
	public List<TelemartCustomer> getFollowUpTableList(BigDecimal branchId,BigDecimal empId) {
		return telemarketingDAO.getFollowUpTableList(branchId,empId);
	}
	
	@Override
	@Transactional
	public List<VwExTelemartFolwUpCode> getTelemartFollowUpCodes() {
		return telemarketingDAO.getTelemartFollowUpCodes();
	}
	
	@Override
	@Transactional
	public List<Object []> getCustomersCount(BigDecimal branchId) {
		return telemarketingDAO.getCustomersCount(branchId);
	}
	
	@Override
	@Transactional
	public String getTelemartFollowUpCodes(String followUpCode) {
		return telemarketingDAO.getTelemartFollowUpCodes(followUpCode);
	}

	@Override
	@Transactional
	public List<TelemartCustomer> getFollowUpTableListInquiry(BigDecimal branchId, BigDecimal empId) {
		return telemarketingDAO.getFollowUpTableListInquiry(branchId, empId);
	}

	@Override
	@Transactional
	public void saveTelemartEmployee(List<TelemarketingBranchWiseTable> lstTeleCust,BigDecimal empId) {
		telemarketingDAO.saveTelemartEmployee(lstTeleCust,empId);
	}

	@Override
	@Transactional
	public List<TelemartCustomer> getTelemartCustomerDataByStaff(BigDecimal countryBranchId) {
		return telemarketingDAO.getTelemartCustomerDataByStaff(countryBranchId);
	}
	
	@Override
	@Transactional
	public void saveBranchTransfer(TelemartCustomer telemartCustomer,BigDecimal toBranchId,BigDecimal fromBranchId) {
		telemarketingDAO.saveBranchTransfer(telemartCustomer,toBranchId,fromBranchId);
	}
	
	@Override
	@Transactional
	public BigDecimal branchTransferProcedure(BigDecimal toBranchId,BigDecimal fromBranchId,BigDecimal nationality){
		return telemarketingDAO.branchTransferProcedure(toBranchId, fromBranchId, nationality);
	}

}
