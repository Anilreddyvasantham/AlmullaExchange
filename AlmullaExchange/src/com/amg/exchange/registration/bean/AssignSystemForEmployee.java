package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.BranchSystemInventory;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.EmployeeSystemsAssigned;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Controller("assignSystemForEmployee")
@Scope("session")
public class AssignSystemForEmployee implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IEmployeeService employeeService;
	@Autowired
	IBranchPageService<T> branchPageService;

	private BigDecimal employeeSystemsAssignId;
	private BigDecimal employeeId;
	private String userName;
	private BigDecimal employeeBranchId;
	private BigDecimal assignBranchId;
	private String assignBranchName;
	private String systemName;
	private boolean renderSystemDetails;
	private String createdBy;
	private Date createdDate;
	private String remarks;
	private boolean booRenderEdit;
	private String errorMessage;

	private List<CountryBranch> searchBranchList = new ArrayList<CountryBranch>();
	private HashMap<BigDecimal, String> mapBranchName = new HashMap<BigDecimal, String>();
	private List<SystemAllocationDataTable> lstSystemDetails = new ArrayList<SystemAllocationDataTable>();
	private List<SystemAllocationDataTable> templstSystemDetails = new ArrayList<SystemAllocationDataTable>();
	private List<Employee> lstEmployeeUserNames = new ArrayList<Employee>();
	private HashMap<BigDecimal, String> mapUserName = new HashMap<BigDecimal, String>();
	private List<BranchSystemInventory> lstBranchSysteminventory = new ArrayList<BranchSystemInventory>();
	private List<EmployeeSystemsAssigned> lstEmpSysAssignData = new ArrayList<EmployeeSystemsAssigned>();
	private SystemAllocationDataTable empSystemAssignDT = new SystemAllocationDataTable();

	// page navigation
	public void systemAssignmentPageNavigation(){
		try {
			clear();
			populateBranch();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/assignSystemForEmployee.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// fetch All userName based on branch
	public void fetchUserNameByBranch(){
		mapUserName.clear();
		if(getEmployeeBranchId() != null){
			List<Employee> lstEmployee = employeeService.fetchEmployeesByBranch(getEmployeeBranchId());
			if(lstEmployee != null && lstEmployee.size() != 0){
				setLstEmployeeUserNames(lstEmployee);

				for (Employee employee : lstEmployee) {
					mapUserName.put(employee.getEmployeeId(), employee.getUserName());
				}
			}
		}
	}

	// clearing fields
	public void clear(){
		setRenderSystemDetails(Boolean.FALSE);
		setEmployeeId(null);
		setUserName(null);
		setAssignBranchId(null);
		setEmployeeBranchId(null);
		setSystemName(null);
		lstSystemDetails.clear();
		templstSystemDetails.clear();
		setLstBranchSysteminventory(null);
		setLstEmployeeUserNames(null);
		setRemarks(null);
		setCreatedBy(null);
		setCreatedDate(null);
	}

	public void exit(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// populate All Active Branches
	public void populateBranch() {
		mapBranchName.clear();
		List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(sessionStateManage.getCountryId());
		if(listSearchBranch != null && listSearchBranch.size() != 0){
			setSearchBranchList(listSearchBranch);

			for (CountryBranch countryBranch : listSearchBranch) {
				mapBranchName.put(countryBranch.getCountryBranchId(), countryBranch.getBranchName());
			}
		}
	}

	// fetch new system number to allocate for that branch
	public void fetchSystemNumber(){
		setLstBranchSysteminventory(null);
		if(getAssignBranchId() != null){
			setAssignBranchName(mapBranchName.get(getAssignBranchId()));
			List<BranchSystemInventory> lstBranchDetails = branchPageService.fetchSystemAllocation(getAssignBranchId());
			if(lstBranchDetails != null){
				setLstBranchSysteminventory(lstBranchDetails);
			}
		}
	}

	// check Branch Name already exists in system for that branch
	public boolean checkBranchNameDuplicate(){

		boolean branchNameDuplicate = Boolean.FALSE;
		if(getSystemName() != null && getLstEmpSysAssignData() != null && !isBooRenderEdit()){
			for (EmployeeSystemsAssigned empSystem : getLstEmpSysAssignData()) {
				if(empSystem.getEmployeeName().equalsIgnoreCase(getUserName())){
					if(empSystem.getBranchName().equalsIgnoreCase(getAssignBranchName())){
						// Branch name already allocated to system
						branchNameDuplicate = Boolean.TRUE;
						break;
					}
				}
			}
		}

		if(getLstSystemDetails() != null){
			for (SystemAllocationDataTable systemAllocation : getLstSystemDetails()) {
				if(systemAllocation.getUserName().equalsIgnoreCase(getUserName())){
					if(systemAllocation.getBranchName().equalsIgnoreCase(getAssignBranchName())){
						// Branch name already allocated to system
						branchNameDuplicate = Boolean.TRUE;
						break;
					}
				}
			}
		}

		return branchNameDuplicate;
	}

	// check system number already exists in system for that branch
	public boolean checkSystemNameDuplicate(){

		boolean seqNameDuplicate = Boolean.FALSE;
		if(getSystemName() != null && getLstEmpSysAssignData() != null && !isBooRenderEdit()){
			for (EmployeeSystemsAssigned empSystem : getLstEmpSysAssignData()) {
				if(empSystem.getEmployeeName().equalsIgnoreCase(getUserName())){
					if(empSystem.getBranchName().equalsIgnoreCase(getAssignBranchName())){
						if(empSystem.getSystemName().equalsIgnoreCase(getSystemName())){
							// system name already allocated to system
							seqNameDuplicate = Boolean.TRUE;
							break;
						}
					}
				}
			}
		}

		if(getLstSystemDetails() != null){
			for (SystemAllocationDataTable systemAllocation : getLstSystemDetails()) {
				if(systemAllocation.getUserName().equalsIgnoreCase(getUserName())){
					if(systemAllocation.getBranchName().equalsIgnoreCase(getAssignBranchName())){
						if(systemAllocation.getSystem().equalsIgnoreCase(getSystemName())){
							// system name already allocated to system
							seqNameDuplicate = Boolean.TRUE;
							break;
						}
					}
				}
			}
		}

		return seqNameDuplicate;
	}

	// add the system number to data table
	public void addSystemDetails(){
		boolean branchNameStatus = checkBranchNameDuplicate();
		if(branchNameStatus){
			setErrorMessage("Branch name already exists for : " + getUserName());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			boolean systemNameStatus = checkSystemNameDuplicate();
			if(systemNameStatus){
				setErrorMessage("System name already exists for : " + getUserName());
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else{
				setRenderSystemDetails(Boolean.TRUE);

				SystemAllocationDataTable systemAllocation = new SystemAllocationDataTable();

				systemAllocation.setEmployeeSystemsAssignId(getEmployeeSystemsAssignId());
				systemAllocation.setEmployeeId(getEmployeeId());
				systemAllocation.setUserName(getUserName());
				systemAllocation.setBranchId(getAssignBranchId());
				systemAllocation.setBranchName(getAssignBranchName());
				systemAllocation.setSystem(getSystemName());
				systemAllocation.setStatus(Constants.PENDING);
				systemAllocation.setCreatedBy(getCreatedBy());
				systemAllocation.setCreatedDate(getCreatedDate());

				lstSystemDetails.add(systemAllocation);
				templstSystemDetails.add(systemAllocation);
				clearAfterAdd();
			}
		}
	}

	// clear specific fields
	public void clearAfterAdd(){
		setBooRenderEdit(Boolean.FALSE);
		setEmployeeSystemsAssignId(null);
		setAssignBranchId(null);
		setAssignBranchName(null);
		setSystemName(null);
		setRemarks(null);
		setCreatedBy(null);
		setCreatedDate(null);
	}

	// fetch employee system assign based on employee
	public void fetchEmployeeSystemAssignData(){
		setLstEmpSysAssignData(null);

		if(getEmployeeId() != null){
			setUserName(mapUserName.get(getEmployeeId()));
			List<EmployeeSystemsAssigned> lstEmpSysAssign = branchPageService.fetchEmployeeSystemAssignByUserName(getEmployeeId());
			if(lstEmpSysAssign != null && lstEmpSysAssign.size() != 0){
				setLstEmpSysAssignData(lstEmpSysAssign);
			}
		}
	}

	// view the system details to data table based on branch
	public void viewSystemDetails(){

		lstSystemDetails.clear();
		if(getEmployeeId() != null){
			setRenderSystemDetails(Boolean.TRUE);

			if(getLstEmpSysAssignData() != null){
				for (EmployeeSystemsAssigned empSysAssign : getLstEmpSysAssignData()) {

					SystemAllocationDataTable systemAssign = new SystemAllocationDataTable();

					systemAssign.setEmployeeSystemsAssignId(empSysAssign.getEmployeeSystemsAssignId());
					systemAssign.setEmployeeId(empSysAssign.getEmployeeId());
					systemAssign.setUserName(empSysAssign.getEmployeeName());
					systemAssign.setBranchId(empSysAssign.getCountryBranchId());
					systemAssign.setBranchName(empSysAssign.getBranchName());
					systemAssign.setSystem(empSysAssign.getSystemName());
					if(empSysAssign.getIsActive() != null && empSysAssign.getIsActive().equalsIgnoreCase(Constants.Y)){
						systemAssign.setStatus(Constants.ACTIVE);
					}else if(empSysAssign.getIsActive() != null && empSysAssign.getIsActive().equalsIgnoreCase(Constants.D)){
						systemAssign.setStatus(Constants.IN_ACTIVE);
					}else if(empSysAssign.getIsActive() != null && empSysAssign.getIsActive().equalsIgnoreCase(Constants.U)){
						systemAssign.setStatus(Constants.PENDING);
					}
					systemAssign.setCreatedBy(empSysAssign.getCreatedBy());
					systemAssign.setCreatedDate(empSysAssign.getCreatedDate());

					lstSystemDetails.add(systemAssign);
				}
			}

			if(templstSystemDetails != null && templstSystemDetails.size() != 0){
				lstSystemDetails.addAll(templstSystemDetails);
			}
		}else{
			setErrorMessage("Select the Employee to view records");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// edit the system details
	public void editSystemDetails(SystemAllocationDataTable systemDt){
		setAssignBranchId(systemDt.getBranchId());
		setEmployeeSystemsAssignId(systemDt.getEmployeeSystemsAssignId());
		setCreatedBy(systemDt.getCreatedBy());
		setCreatedDate(systemDt.getCreatedDate());
		fetchSystemNumber();
		setSystemName(systemDt.getSystem());
		setBooRenderEdit(Boolean.TRUE);
		lstSystemDetails.remove(systemDt);
		templstSystemDetails.remove(systemDt);
	}

	// delete D the system details
	public void deleteSystemDetailsOkButton(){
		if(getEmpSystemAssignDT() != null){
			lstSystemDetails.remove(getEmpSystemAssignDT());
			templstSystemDetails.remove(getEmpSystemAssignDT());
			branchPageService.deleteEmpSystemAssign(getEmpSystemAssignDT().getEmployeeSystemsAssignId(), getRemarks());
			clear();
		}
	}

	// delete D the system details
	public void deleteSystemDetails(SystemAllocationDataTable systemDt){
		setEmpSystemAssignDT(null);
		setRemarks(null);
		if(systemDt.getEmployeeSystemsAssignId() != null){
			if(systemDt.getStatus().equalsIgnoreCase(Constants.ACTIVE)){
				setEmpSystemAssignDT(systemDt);
				RequestContext.getCurrentInstance().execute("deletedlg.show();");
			}else{
				setErrorMessage("Record already In-active");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{
			lstSystemDetails.remove(systemDt);
			templstSystemDetails.remove(systemDt);
		}
	}

	// submit button action
	public void saveSystemDetails(){
		List<SystemAllocationDataTable> lstSysAllDt = new ArrayList<SystemAllocationDataTable>();
		List<EmployeeSystemsAssigned> lstSaveEmpSysAss = new ArrayList<EmployeeSystemsAssigned>();
		if(getLstSystemDetails() != null){
			for (SystemAllocationDataTable sysAlloc : getLstSystemDetails()) {
				if(sysAlloc.getStatus().equalsIgnoreCase(Constants.PENDING)){
					lstSysAllDt.add(sysAlloc);
				}
			}
		}

		if(lstSysAllDt != null && lstSysAllDt.size() != 0){
			for (SystemAllocationDataTable systemAllocationDataTable : lstSysAllDt) {
				EmployeeSystemsAssigned empSystemAssign = new EmployeeSystemsAssigned();

				empSystemAssign.setEmployeeSystemsAssignId(systemAllocationDataTable.getEmployeeSystemsAssignId());
				empSystemAssign.setEmployeeId(systemAllocationDataTable.getEmployeeId());
				empSystemAssign.setEmployeeName(systemAllocationDataTable.getUserName());
				empSystemAssign.setBranchName(systemAllocationDataTable.getBranchName());
				empSystemAssign.setCountryBranchId(systemAllocationDataTable.getBranchId());
				empSystemAssign.setIsActive(Constants.Yes);
				empSystemAssign.setSystemName(systemAllocationDataTable.getSystem());
				empSystemAssign.setRemarks(null);
				if(systemAllocationDataTable.getEmployeeSystemsAssignId() != null){
					empSystemAssign.setCreatedBy(systemAllocationDataTable.getCreatedBy());
					empSystemAssign.setCreatedDate(systemAllocationDataTable.getCreatedDate());
					empSystemAssign.setUpdatedBy(sessionStateManage.getUserName());
					empSystemAssign.setUpdatedDate(new Date());
				}else{
					empSystemAssign.setCreatedBy(sessionStateManage.getUserName());
					empSystemAssign.setCreatedDate(new Date());
				}

				lstSaveEmpSysAss.add(empSystemAssign);
			}

			boolean saveStatus = branchPageService.saveEmpSystemAssign(lstSaveEmpSysAss);
			if(saveStatus){
				clear();
				RequestContext.getCurrentInstance().execute("appSave.show();");
			}else{
				setErrorMessage("Save failed");
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}
		}else{
			setErrorMessage("No pending records to save");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}

	}

	// getters and setters
	public BigDecimal getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(BigDecimal employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getEmployeeBranchId() {
		return employeeBranchId;
	}
	public void setEmployeeBranchId(BigDecimal employeeBranchId) {
		this.employeeBranchId = employeeBranchId;
	}

	public BigDecimal getAssignBranchId() {
		return assignBranchId;
	}
	public void setAssignBranchId(BigDecimal assignBranchId) {
		this.assignBranchId = assignBranchId;
	}

	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public boolean isRenderSystemDetails() {
		return renderSystemDetails;
	}
	public void setRenderSystemDetails(boolean renderSystemDetails) {
		this.renderSystemDetails = renderSystemDetails;
	}

	public List<CountryBranch> getSearchBranchList() {
		return searchBranchList;
	}
	public void setSearchBranchList(List<CountryBranch> searchBranchList) {
		this.searchBranchList = searchBranchList;
	}

	public List<SystemAllocationDataTable> getLstSystemDetails() {
		return lstSystemDetails;
	}
	public void setLstSystemDetails(List<SystemAllocationDataTable> lstSystemDetails) {
		this.lstSystemDetails = lstSystemDetails;
	}

	public List<Employee> getLstEmployeeUserNames() {
		return lstEmployeeUserNames;
	}
	public void setLstEmployeeUserNames(List<Employee> lstEmployeeUserNames) {
		this.lstEmployeeUserNames = lstEmployeeUserNames;
	}

	public List<BranchSystemInventory> getLstBranchSysteminventory() {
		return lstBranchSysteminventory;
	}
	public void setLstBranchSysteminventory(List<BranchSystemInventory> lstBranchSysteminventory) {
		this.lstBranchSysteminventory = lstBranchSysteminventory;
	}

	public BigDecimal getEmployeeSystemsAssignId() {
		return employeeSystemsAssignId;
	}
	public void setEmployeeSystemsAssignId(BigDecimal employeeSystemsAssignId) {
		this.employeeSystemsAssignId = employeeSystemsAssignId;
	}

	public String getAssignBranchName() {
		return assignBranchName;
	}
	public void setAssignBranchName(String assignBranchName) {
		this.assignBranchName = assignBranchName;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isBooRenderEdit() {
		return booRenderEdit;
	}
	public void setBooRenderEdit(boolean booRenderEdit) {
		this.booRenderEdit = booRenderEdit;
	}

	public List<SystemAllocationDataTable> getTemplstSystemDetails() {
		return templstSystemDetails;
	}
	public void setTemplstSystemDetails(List<SystemAllocationDataTable> templstSystemDetails) {
		this.templstSystemDetails = templstSystemDetails;
	}

	public List<EmployeeSystemsAssigned> getLstEmpSysAssignData() {
		return lstEmpSysAssignData;
	}
	public void setLstEmpSysAssignData(List<EmployeeSystemsAssigned> lstEmpSysAssignData) {
		this.lstEmpSysAssignData = lstEmpSysAssignData;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public SystemAllocationDataTable getEmpSystemAssignDT() {
		return empSystemAssignDT;
	}
	public void setEmpSystemAssignDT(SystemAllocationDataTable empSystemAssignDT) {
		this.empSystemAssignDT = empSystemAssignDT;
	}

}
