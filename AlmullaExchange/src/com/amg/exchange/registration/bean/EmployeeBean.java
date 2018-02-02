package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.registration.model.ViewArea;
import com.amg.exchange.registration.model.ViewMgrt;
import com.amg.exchange.registration.model.ViewUserType;
import com.amg.exchange.registration.service.IEmployeeService;
import com.amg.exchange.registration.service.ILoginService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;
import com.amg.exchange.util.iCypherSecurity;
import com.amg.exchange.util.impl.CypherSecurityImpl;

@Controller("employeeBean")
@Scope("session")
public class EmployeeBean<T> implements Serializable{

	private static final Logger LOGGER = Logger.getLogger(EmployeeBean.class);
	private static final long serialVersionUID = 1L;

	@Autowired
	IEmployeeService employeeService;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	ILoginService<T> loginService;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private BigDecimal pkEmployeeId;
	private String employeeNumber;
	private String employeeName;
	private String userName;
	private String password;
	private String location;
	private String telephone;
	private BigDecimal countryId;
	private BigDecimal branchId;
	private BigDecimal tempbranchId;
	private BigDecimal roleId;
	private boolean booCheckSave = true;
	private boolean booCheckUpdate = false;
	private String manageOnBlueEmployeeNumber;
	private String userNameForPasswordUpadte;
	private String newPassword;
	private String retypePassword;
	private String userNameCheck;
	private Employee employeeObj;
	private Employee empployeeObjForPopulate;
	private Boolean signatureCaptureRender= true;
	private Boolean signatureSpecimenRender = false;
	private String digitalSignature;
	private BigDecimal companyId;
	private String allowFCTransaction;
	private String ipAddress;
	private boolean booipAddress = false;
	private String cashierOption;
	private BigDecimal wuUserName;
	private String wuPassword;
	private String userType;
	private String email;
	private String statusEmp;
	private String designation;
	private String isActive;
	private String semployeeNumber;
	private String semployeeName;
	private String suserName;
	private BigDecimal sbranchId;
	private BigDecimal sroleId;
	private String slocation;
	private String stelephone;
	private Boolean renderEmployeeList= false;
	private String custActivateDeactivate;
	private String modifiedBy;
	private String createdBy;
	private Date createdDate;
	private Date modifiedDate;
	private BigDecimal employeeCountryId;
	private String allocateInd;
	
	private String wuTerminalId;
	private String wuForeignTerminalId;
	private String wuNaid;
	private String wuAccId;
	
	private String civilId;
	private Date passwordExpiryDate;

	private List<CountryBranch> searchBranchList = new ArrayList<CountryBranch>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<CountryBranch> lstBranch = new ArrayList<CountryBranch>();
	private List<RoleMaster> lstRole = new ArrayList<RoleMaster>();
	private List<CompanyMasterDesc> listCompany = new ArrayList<CompanyMasterDesc>();
	private List<Employee> employeeList;
	private List<ViewArea> listArea = new ArrayList<ViewArea>();
	private List<ViewMgrt> listDesignation = new ArrayList<ViewMgrt>();
	private List<ViewUserType> listViewUserType = new ArrayList<ViewUserType>();
	private List<CountryMasterDesc> lstEmpCountry = new ArrayList<CountryMasterDesc>();
	//private List<Employee> lstEmployeesECNO = new ArrayList<Employee>();

	/*public List<Employee> getLstEmployeesECNO() {
		return lstEmployeesECNO;
	}
	public void setLstEmployeesECNO(List<Employee> lstEmployeesECNO) {
		this.lstEmployeesECNO = lstEmployeesECNO;
	}*/

	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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

	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public IEmployeeService getEmployeeService() {
		return employeeService;
	}
	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
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

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStelephone() {
		return stelephone;
	}
	public void setStelephone(String stelephone) {
		this.stelephone = stelephone;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getTempbranchId() {
		return tempbranchId;
	}
	public void setTempbranchId(BigDecimal tempbranchId) {
		this.tempbranchId = tempbranchId;
	}

	public BigDecimal getRoleId() {
		return roleId;
	}
	public void setRoleId(BigDecimal roleId) {
		this.roleId = roleId;
	}

	public BigDecimal getPkEmployeeId() {
		return pkEmployeeId;
	}
	public void setPkEmployeeId(BigDecimal pkEmployeeId) {
		this.pkEmployeeId = pkEmployeeId;
	}

	public List<ViewUserType> getListViewUserType() {
		return listViewUserType;
	}
	public void setListViewUserType(List<ViewUserType> listViewUserType) {
		this.listViewUserType = listViewUserType;
	}
	public List<CountryMasterDesc> getLstCountry() {
		//return IGeneralService.getBusinessCountryList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):""+1));
		List<CountryMasterDesc> listCountry =  generalService.getBusinessCountryList(sessionStateManage.getLanguageId());
		//setLstBranch(generalService.getBranchDetails(sessionStateManage.getCountryId()));
		return generalService.getBusinessCountryList(sessionStateManage.getLanguageId());
	}

	public List<CountryBranch> getLstBranch() {
		return lstBranch;
	}

	public List<CountryMasterDesc> getLstEmpCountry() {
		return lstEmpCountry;
	}
	public void setLstEmpCountry(List<CountryMasterDesc> lstEmpCountry) {
		this.lstEmpCountry = lstEmpCountry;
	}

	public void setLstBranch(List<CountryBranch> lstBranch) {
		this.lstBranch = lstBranch;
	}

	public List<RoleMaster> getLstRole() {
		return generalService.getRole();
	}

	public boolean isBooCheckSave() {
		return booCheckSave;
	}
	public void setBooCheckSave(boolean booCheckSave) {
		this.booCheckSave = booCheckSave;
	}

	public boolean isBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}

	public String getManageOnBlueEmployeeNumber() {
		return manageOnBlueEmployeeNumber;
	}
	public void setManageOnBlueEmployeeNumber(String manageOnBlueEmployeeNumber) {
		this.manageOnBlueEmployeeNumber = manageOnBlueEmployeeNumber;
	}

	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getRetypePassword() {
		return retypePassword;
	}
	public void setRetypePassword(String retypePassword) {
		this.retypePassword = retypePassword;
	}

	public String getUserNameForPasswordUpadte() {
		return userNameForPasswordUpadte;
	}
	public void setUserNameForPasswordUpadte(String userNameForPasswordUpadte) {
		this.userNameForPasswordUpadte = userNameForPasswordUpadte;
	}

	public String getUserNameCheck() {
		return userNameCheck;
	}
	public void setUserNameCheck(String userNameCheck) {
		this.userNameCheck = userNameCheck;
	}

	public Boolean getSignatureCaptureRender() {
		return signatureCaptureRender;
	}
	public void setSignatureCaptureRender(Boolean signatureCaptureRender) {
		this.signatureCaptureRender = signatureCaptureRender;
	}
	public Boolean getSignatureSpecimenRender() {
		return signatureSpecimenRender;
	}
	public void setSignatureSpecimenRender(Boolean signatureSpecimenRender) {
		this.signatureSpecimenRender = signatureSpecimenRender;
	}
	public String getDigitalSignature() {
		return digitalSignature;
	}
	public void setDigitalSignature(String digitalSignature) {
		this.digitalSignature = digitalSignature;
	}
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	public String getAllowFCTransaction() {
		return allowFCTransaction;
	}
	public void setAllowFCTransaction(String allowFCTransaction) {
		this.allowFCTransaction = allowFCTransaction;
	}

	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public boolean isBooipAddress() {
		return booipAddress;
	}
	public void setBooipAddress(boolean booipAddress) {
		this.booipAddress = booipAddress;
	}

	public String getCashierOption() {
		return cashierOption;
	}
	public void setCashierOption(String cashierOption) {
		this.cashierOption = cashierOption;
	}
	public BigDecimal getWuUserName() {
		return wuUserName;
	}
	public void setWuUserName(BigDecimal wuUserName) {
		this.wuUserName = wuUserName;
	}
	public String getWuPassword() {
		return wuPassword;
	}
	public void setWuPassword(String wuPassword) {
		this.wuPassword = wuPassword;
	}

	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatusEmp() {
		return statusEmp;
	}
	public void setStatusEmp(String statusEmp) {
		this.statusEmp = statusEmp;
	}

	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public List<ViewArea> getListArea() {
		return employeeService.getArea();
	}
	public List<ViewMgrt> getListDesignation() {
		return employeeService.getDesignation();
	}

	public BigDecimal getEmployeeCountryId() {
		return employeeCountryId;
	}
	public void setEmployeeCountryId(BigDecimal employeeCountryId) {
		this.employeeCountryId = employeeCountryId;
	}
	
	public String getSemployeeNumber() {
		return semployeeNumber;
	}
	public void setSemployeeNumber(String semployeeNumber) {
		this.semployeeNumber = semployeeNumber;
	}
	public String getSemployeeName() {
		return semployeeName;
	}
	public void setSemployeeName(String semployeeName) {
		this.semployeeName = semployeeName;
	}
	public String getSuserName() {
		return suserName;
	}
	public void setSuserName(String suserName) {
		this.suserName = suserName;
	}
	public BigDecimal getSbranchId() {
		return sbranchId;
	}
	public void setSbranchId(BigDecimal sbranchId) {
		this.sbranchId = sbranchId;
	}
	public BigDecimal getSroleId() {
		return sroleId;
	}
	public void setSroleId(BigDecimal sroleId) {
		this.sroleId = sroleId;
	}

	public String getSlocation() {
		return slocation;
	}
	public void setSlocation(String slocation) {
		this.slocation = slocation;
	}


	public Boolean getRenderEmployeeList() {
		return renderEmployeeList;
	}
	public void setRenderEmployeeList(Boolean renderEmployeeList) {
		this.renderEmployeeList = renderEmployeeList;
	}
	public List<CountryBranch> getSearchBranchList() {
		return searchBranchList;
	}
	public void setSearchBranchList(List<CountryBranch> searchBranchList) {
		this.searchBranchList = searchBranchList;
	}
	public List<CompanyMasterDesc> getListCompany() {
		return generalService.getAllCompanyList(sessionStateManage.getLanguageId());
	}
	
	public String getAllocateInd() {
		return allocateInd;
	}
	public void setAllocateInd(String allocateInd) {
		this.allocateInd = allocateInd;
	}
	
	public String getWuTerminalId() {
		return wuTerminalId;
	}
	public void setWuTerminalId(String wuTerminalId) {
		this.wuTerminalId = wuTerminalId;
	}
	public String getWuForeignTerminalId() {
		return wuForeignTerminalId;
	}
	public void setWuForeignTerminalId(String wuForeignTerminalId) {
		this.wuForeignTerminalId = wuForeignTerminalId;
	}
	public String getWuNaid() {
		return wuNaid;
	}
	public void setWuNaid(String wuNaid) {
		this.wuNaid = wuNaid;
	}
	public String getWuAccId() {
		return wuAccId;
	}
	public void setWuAccId(String wuAccId) {
		this.wuAccId = wuAccId;
	}
	/**
	 * Responsible to populate Employee List
	 * @return
	 */
	public List<Employee> getEmployeeList() {

		return employeeList;
	}


	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	/**
	 * Responsible to Add Employee
	 */
	public void addEmployee() {

		try {
			// validate telephone
			if(getTelephone() != null){
				String dupEmpTelePhone = checkTelephoneNumber();
				if(dupEmpTelePhone != null && !dupEmpTelePhone.equalsIgnoreCase("")){
					setErrorMessage("Telephone Number already exists for user : " + dupEmpTelePhone);
					RequestContext.getCurrentInstance().execute("error.show();");
				}else{
					CountryBranch countryBranch = new CountryBranch();
					countryBranch.setCountryBranchId(getBranchId());

					RoleMaster roleMaster = new RoleMaster();
					roleMaster.setRoleId(getRoleId());

					Employee employee = new Employee();
					employee.setEmployeeId(getPkEmployeeId());

					employee.setEmployeeNumber(getEmployeeNumber());
					employee.setEmployeeName(getEmployeeName());
					employee.setEmployeeCountryId(getEmployeeCountryId());
					employee.setUserName(getUserName());
					if(getPkEmployeeId()!=null){
						employee.setPassword(getPassword());
						employee.setWuPassword(getWuPassword());
					}else{
						iCypherSecurity cypherSecurity = new CypherSecurityImpl();
						if(getUserName()!=null && !getUserName().equalsIgnoreCase("")){
							String secretKey = getUserName().trim().toUpperCase();
							StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
							if(getPassword() != null && !getPassword().trim().equalsIgnoreCase("")){
								employee.setPassword(cypherSecurity.encodePassword(getPassword(), secretKeys.toString()));
							}
							if(getWuPassword() != null && !getWuPassword().trim().equalsIgnoreCase("")){
								employee.setWuPassword(cypherSecurity.encodePassword(getWuPassword(), secretKeys.toString()));
							}

						}
					}
					employee.setLocation(getLocation());
					employee.setTelephoneNumber(getTelephone());
					employee.setFsCountryBranch(countryBranch);
					employee.setFsRoleMaster(roleMaster);
					employee.setCountryId(getCountryId());

					employee.setDeletedUser( getCustActivateDeactivate());
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(getCompanyId());
					employee.setFsCompanyMaster(companyMaster);
					if(getDigitalSignature()!=null){
						setSignatureSpecimenRender(true);
						setSignatureCaptureRender(false);
						try {
							employee.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));
						} catch (Exception e) {
							e.printStackTrace();
						}
					}else{
						setSignatureSpecimenRender(false);
						setSignatureCaptureRender(true);
					}
					employee.setAllowFcTransaction(getAllowFCTransaction());
					employee.setIpAddress(getIpAddress());
					employee.setCashierOpt(getCashierOption());
					employee.setWuUsername(getWuUserName());
					employee.setUserType(getUserType());
					employee.setEmail(getEmail());
					//employee.setStatus(Constants.ACTIVEEMPLOYEE);
					employee.setIsActive(Constants.Yes);
					employee.setDesignation(getDesignation());
					employee.setCreatedBy(sessionStateManage.getUserName());
					employee.setCreatedDate(new Date());
					
					employee.setAllocateInd(getAllocateInd());
					employee.setWunaId(getWuNaid());
					employee.setWuAccountId(getWuAccId());
					employee.setWuTerminalId(getWuTerminalId());
					employee.setWuForeignTerminalId(getWuForeignTerminalId());
					
					employee.setCivilId(getCivilId());
					employee.setPasswordDate(getPasswordExpiryDate());
					
					employeeService.addEmployee(employee);
					resetValues();
					setRenderEmployeeList(false);
					RequestContext.getCurrentInstance().execute("successreg.show();");

				}
			}else{
				setErrorMessage("Enter employee telephone number");
				RequestContext.getCurrentInstance().execute("error.show();");
			}
		} catch (NullPointerException ne) {
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}
	
	// check the telephone number exists
	public String checkTelephoneNumber(){
		String dupEmpTelePhone = "";
		if(getTelephone() != null){
			List<Employee> existTelephoneUsers = employeeService.checkTelephoneNumber(getTelephone(),getUserName());
			if(existTelephoneUsers != null && existTelephoneUsers.size() != 0){
				for (Employee employee : existTelephoneUsers) {
					dupEmpTelePhone = dupEmpTelePhone == "" ? dupEmpTelePhone.concat(employee.getUserName()) : dupEmpTelePhone.concat(",").concat(employee.getUserName());
				}
			}
		}
		
		return dupEmpTelePhone;
	}

	public void updateEmployee() {
		if(getUserName()!= null && getUserName().equalsIgnoreCase(sessionStateManage.getUserName())){
			RequestContext.getCurrentInstance().execute("notabletodelet.show();");
		}else{
			if(getTelephone() != null){
				String dupEmpTelePhone = checkTelephoneNumber();
				if(dupEmpTelePhone != null && !dupEmpTelePhone.equalsIgnoreCase("")){
					setErrorMessage("Telephone Number already exists for user : " + dupEmpTelePhone);
					RequestContext.getCurrentInstance().execute("error.show();");
				}else{
					if(getBranchId() != null && getTempbranchId() != null){
						if(getBranchId().compareTo(getTempbranchId())==0){
							//no need call dialog box for Tele-marketing
						}else{
							// pop up dialog box for YES / NO to confirm Tele-marketing
							RequestContext.getCurrentInstance().execute("callProcedureTele.show();");
							return;
						}
					}
					//addEmployee();
					update();
					setBooCheckSave(true);
					setBooCheckUpdate(false);
					setPkEmployeeId(null);
				}
			}else{
				setErrorMessage("Enter employee telephone number");
				RequestContext.getCurrentInstance().execute("error.show();");
			}
		}
	}

	// if Yes do call the Procedure and then update
	public void callProcedureForTeleMarketingYes(){
		// call procedure first
		try {
			HashMap<String, BigDecimal> inputForTeleMarketing = new HashMap<String, BigDecimal>();

			inputForTeleMarketing.put("EmployeeId", getPkEmployeeId());
			inputForTeleMarketing.put("OldBranchId", getTempbranchId());

			employeeService.callProcForTeleMarketingUpdate(inputForTeleMarketing);

			update();
			setBooCheckSave(true);
			setBooCheckUpdate(false);
			setPkEmployeeId(null);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// if No donot call the Procedure but update
	public void callProcedureForTeleMarketingNo(){
		// call procedure first
		try {
			update();
			setBooCheckSave(true);
			setBooCheckUpdate(false);
			setPkEmployeeId(null);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	// fetch employee details
	public void fetchStaffDetails(Employee employee){
		
		List<Employee> empDetails = generalService.getEmployeeDetailByEmpID(employee.getEmployeeId());
		if(empDetails != null && empDetails.size() != 0){
			Employee emp = empDetails.get(0);
			setEmpployeeObjForPopulate(emp);
		}
	}

	public void employeeUpdate(Employee employee) {
		resetValues();
		populateValues(employee);

		// checking condition if User Name is ARMS then Ip Address is mandatory to Enter.
		setBooipAddress(false);
		if(getUserName() != null){
			String userNameChk = getUserName().substring(0, 4);
			if(userNameChk.equalsIgnoreCase(Constants.ARMS)){
				setBooipAddress(true);
			}else{
				setBooipAddress(false);
			}
		}
	}

	public void populateValues(Employee employee) {
		if(employee !=null){
			//setEmpployeeObjForPopulate(employee);
			fetchStaffDetails(employee);
			if(employee.getIsActive().equalsIgnoreCase(Constants.Yes)){
				clickActivate();
			}else{

				setEmployeeNumber(employee.getEmployeeNumber());
				resetValues();
				RequestContext context1 = RequestContext.getCurrentInstance();
				context1.execute("inactiveuser.show();"); 
			}
		}else{

		}

	}

	public void resetValues() {
		setEmployeeNumber(null);
		setEmployeeCountryId(null);
		setEmployeeName(null);
		setUserName(null);
		setPassword(null);
		setLocation(null);
		setTelephone(null);
		setCountryId(null);
		setBranchId(null);
		setTempbranchId(null);
		setRoleId(null);
		setPkEmployeeId(null);
		setUserNameCheck(null);
		setEmail(null);
		setDigitalSignature(null);
		setCompanyId(null);
		setAllowFCTransaction(null);
		setCashierOption(null);
		setIpAddress(null);
		setWuUserName(null);
		setWuPassword(null);
		setUserType(null);
		setStatusEmp(null);
		setSignatureCaptureRender(true);
		setSignatureSpecimenRender(false);
		setDesignation(null);
		setCustActivateDeactivate(null);
		//setRenderEmployeeList(false);
		setAllocateInd(null);
		
		setWuNaid(null);
		setWuAccId(null);
		setWuTerminalId(null);
		setWuForeignTerminalId(null);
		setCivilId(null);
		setPasswordExpiryDate(null);
		
	}


	public void clearAll(){
		resetValues();
		setRenderEmployeeList(false);
		setEmployeeObj( null);
		setEmpployeeObjForPopulate( null);
		setRetypePassword(null);
		setNewPassword(null);
	}

	public void exit(){
		resetValues();
		setEmployeeObj( null);
		setEmpployeeObjForPopulate( null);
		setRetypePassword(null);
		setNewPassword(null);

	}

	public void clickOnYes(){

		if(sessionStateManage.getUserName().equalsIgnoreCase(getEmployeeObj().getUserName())){
			setEmployeeObj( null);
			RequestContext.getCurrentInstance().execute("notabletodelet.show();");

		}	else {	
			employeeService.deleteEmployee(getEmployeeObj());
			setEmployeeObj( null);
			searchEmployee();	
		}
	}

	public void clickNo(){
		System.out.println("=================== no called");
		setEmployeeObj(null);
	}



	public void deleteEmployee(Employee employee) {
		if(employee.getUserName()!= null && employee.getUserName().equalsIgnoreCase(sessionStateManage.getUserName())){
			RequestContext.getCurrentInstance().execute("notabletodelet.show();");

		}else{
			setEmployeeObj(employee);
			RequestContext context1 = RequestContext.getCurrentInstance();
			context1.execute("softdelete.show();"); 
		}
	}

	public void populateOnBlurEmployeeData() {
		setManageOnBlueEmployeeNumber(getEmployeeNumber());
		populateValues(employeeService.getEmployee(getEmployeeNumber(),getUserName()));

		// checking condition if User Name is ARMS then Ip Address is mandatory to Enter.
		setBooipAddress(false);
		if(getUserName() != null){
			String userNameChk = getUserName().substring(0, 4);
			if(userNameChk.equalsIgnoreCase(Constants.ARMS)){
				setBooipAddress(true);
			}else{
				setIpAddress(null);
				setBooipAddress(false);
			}
		}
	}

	public void forgotPassword(Employee employee) {
		System.out.println("=================forgotPassword called");
		setPkEmployeeId(employee.getEmployeeId());
		setUserNameForPasswordUpadte(employee.getUserName());
		setNewPassword(null);
		setRetypePassword(null);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("dilg.show();"); 

	}

	public void westernUnionforgotPassword(Employee employee) {
		System.out.println("=================Western Union forgot Password called");
		setPkEmployeeId(employee.getEmployeeId());
		setUserNameForPasswordUpadte(employee.getUserName());
		setNewPassword(null);
		setRetypePassword(null);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("wudilg.show();"); 
	}

	public void savePassword() {
		if(getNewPassword()!=null&&getRetypePassword()!=null){
			if(!getNewPassword().equalsIgnoreCase(getRetypePassword())) {
				System.out.println("=================savePassword() if called");
				RequestContext.getCurrentInstance().execute("dlgPasswordCheck.show();");
				return ;
			} else {
				System.out.println("=================savePassword() else called");
				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				String secretKey = getUserNameForPasswordUpadte().toUpperCase();
				StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
				password = cypherSecurity.encodePassword(getNewPassword(), secretKeys.toString());
				employeeService.savePassword(getPkEmployeeId(), password, Constants.PASSWORD);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("dilg.hide();"); 

				setPkEmployeeId(null);
				resetValues();
				context.execute("dlgPasswordSave.show();"); 
			}
		}
	}

	// western union save forget password
	public void wuSavePassword() {
		if(getNewPassword()!=null&&getRetypePassword()!=null){
			if(!getNewPassword().equalsIgnoreCase(getRetypePassword())) {
				System.out.println("=================savePassword() if called");
				RequestContext.getCurrentInstance().execute("dlgPasswordCheck.show();");
				return ;
			} else {
				System.out.println("=================savePassword() else called");
				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				String secretKey = getUserNameForPasswordUpadte().toUpperCase();
				StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
				password = cypherSecurity.encodePassword(getNewPassword(), secretKeys.toString());
				employeeService.savePassword(getPkEmployeeId(), password, Constants.WUPASSWORD);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("wudilg.hide();"); 

				setPkEmployeeId(null);
				resetValues();
				context.execute("dlgPasswordSave.show();"); 
			}
		}
	}

	/*
	 * method to get all users name from data base and to check the user name is 
	 * exist or not
	 * Added by Nazish on 13-07-2015
	 */

	private int flag = 0;
	private int status = 0;
	private String statusMsg;

	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}

	public String getStatusMsg() {
		return statusMsg;
	}
	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	public List<Employee> getUserList(String userName,String employeeNumber) {
		List<Employee>	employeeList = new ArrayList<Employee>();
		employeeList.addAll(employeeService.searchUser(userName,employeeNumber));
		return employeeList;
	}

	public void currentStatus() {
		/*try {
			setFlag(1);
			List<Employee> userList = getUserList(getUserName());
			if (userList.size() == 0) {
				setStatusMsg("");
				setStatus(1);
			}else if(getUserNameCheck()!=null && getUserNameCheck().equalsIgnoreCase(getUserName())){
				setStatusMsg("");
				setStatus(1);

			} else{
				setStatusMsg("User Already Exists...");
				//FacesContext.getCurrentInstance().addMessage("employeeregistration:userName", new FacesMessage(FacesMessage.SEVERITY_ERROR, "User Already Exists...", "User Already Exists..."));
				setUserName("");
				setStatus(0);
				setFlag(0);
			}
		} catch (HibernateException he) {
			he.printStackTrace();

		} catch (Exception e) {

		}*/

		List<Employee> userList = getUserList(getUserName(),getEmployeeNumber());
		if (userList.size() > 0) {

			clickActivate();

			//setUserName(null);
			//RequestContext context1 = RequestContext.getCurrentInstance();
			//context1.execute("userexist.show();"); 
		}else {

		}
	}

	public void navigateEmployeePage(){
		try {
			resetValues();
			clearSerach();
			setSemployeeNumber(null);
			populateBranch();
			populateUsertype();
			fetchAllEmployeeCountrys();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeeregistration.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
	}
	public Employee getEmployeeObj() {
		return employeeObj;
	}
	public void setEmployeeObj(Employee employeeObj) {
		this.employeeObj = employeeObj;
	}

	//added 
	public void clickActivate(){
		if(getEmpployeeObjForPopulate()!=null) {
			setPkEmployeeId(getEmpployeeObjForPopulate().getEmployeeId());
			setEmployeeNumber(getEmpployeeObjForPopulate().getEmployeeNumber());
			setEmployeeCountryId(getEmpployeeObjForPopulate().getEmployeeCountryId());
			setEmployeeName(getEmpployeeObjForPopulate().getEmployeeName());
			setUserName(getEmpployeeObjForPopulate().getUserName());
			setPassword(getEmpployeeObjForPopulate().getPassword());
			setLocation(getEmpployeeObjForPopulate().getLocation());
			setTelephone(String.valueOf(getEmpployeeObjForPopulate().getTelephoneNumber()==null?"":getEmpployeeObjForPopulate().getTelephoneNumber()));
			setCountryId(getEmpployeeObjForPopulate().getCountryId());
			//setLstBranch(generalService.getBranchDetails(getCountryId()));
			List<CountryBranch> lstSysAll = loginService.fetchBranchesForEmployee(getEmpployeeObjForPopulate().getEmployeeId());
			setLstBranch(lstSysAll);
			setBranchId(getEmpployeeObjForPopulate().getFsCountryBranch().getCountryBranchId());
			setTempbranchId(getEmpployeeObjForPopulate().getFsCountryBranch().getCountryBranchId());
			setRoleId(getEmpployeeObjForPopulate().getFsRoleMaster().getRoleId());
			setCountryId(getEmpployeeObjForPopulate().getCountryId());
			setBooCheckSave(true);
			setBooCheckUpdate(false);
			setUserNameCheck(getEmpployeeObjForPopulate().getUserName());
			setEmail(getEmpployeeObjForPopulate().getEmail());
			if(getEmpployeeObjForPopulate().getCreatedBy() != null){
				setCreatedBy(getEmpployeeObjForPopulate().getCreatedBy());
			}else{
				setCreatedBy(sessionStateManage.getUserName());
			}
			if(getEmpployeeObjForPopulate().getCreatedDate() != null){
				setCreatedDate(getEmpployeeObjForPopulate().getCreatedDate());
			}else{
				setCreatedDate(new Date());
			}
			setModifiedBy(sessionStateManage.getUserName());
			setModifiedDate(new Date());

			if (getEmpployeeObjForPopulate().getSignatureSpecimenClob() != null) {
				setSignatureCaptureRender(false);
				setSignatureSpecimenRender(true);
				try {
					setDigitalSignature(getEmpployeeObjForPopulate().getSignatureSpecimenClob()
							.getSubString(1, (int) getEmpployeeObjForPopulate().getSignatureSpecimenClob().length()));
				} catch (SQLException e) {

					e.printStackTrace();
				}
			}else{

				setSignatureCaptureRender(true);
				setSignatureSpecimenRender(false);
			}
			setCompanyId(getEmpployeeObjForPopulate().getFsCompanyMaster().getCompanyId());
			setAllowFCTransaction(getEmpployeeObjForPopulate().getAllowFcTransaction());
			setCashierOption(getEmpployeeObjForPopulate().getCashierOpt());
			setIpAddress(getEmpployeeObjForPopulate().getIpAddress());
			setWuUserName(getEmpployeeObjForPopulate().getWuUsername());
			setWuPassword(getEmpployeeObjForPopulate().getWuPassword());
			
			//Added on 09'th July 2017
			setWuNaid(getEmpployeeObjForPopulate().getWunaId());
			setWuAccId(getEmpployeeObjForPopulate().getWuAccountId());
			setWuTerminalId(getEmpployeeObjForPopulate().getWuTerminalId());
			setWuForeignTerminalId(getEmpployeeObjForPopulate().getWuForeignTerminalId());
			// Added End. 
			
			
			
			setUserType(getEmpployeeObjForPopulate().getUserType());
			setDesignation(getEmpployeeObjForPopulate().getDesignation());
			setCustActivateDeactivate(getEmpployeeObjForPopulate().getDeletedUser());
			
			setAllocateInd(getEmpployeeObjForPopulate().getAllocateInd());
			/*if(getEmpployeeObjForPopulate().getStatus()!=null){
				setStatusEmp(getEmpployeeObjForPopulate().getStatus());
			}*/
			if(getEmpployeeObjForPopulate().getIsActive()!=null){
				setStatusEmp(getEmpployeeObjForPopulate().getIsActive());
			}
			
			setCivilId(getEmpployeeObjForPopulate().getCivilId());
			setPasswordExpiryDate(getEmpployeeObjForPopulate().getPasswordDate());

			RequestContext context1 = RequestContext.getCurrentInstance();
			context1.execute("showrecord.show();"); 
		} else {

			setEmployeeNumber(getManageOnBlueEmployeeNumber());
			resetValues();
			setBooCheckSave(true);
			setBooCheckUpdate(false);
			setEmpployeeObjForPopulate( null);
		}
	}
	public void clickInactivate(){
		resetValues();
		//setEmployeeNumber(getManageOnBlueEmployeeNumber());
		setBooCheckSave(true);
		setBooCheckUpdate(false);
		setEmpployeeObjForPopulate(null);
	}
	public Employee getEmpployeeObjForPopulate() {
		return empployeeObjForPopulate;
	}
	public void setEmpployeeObjForPopulate(Employee empployeeObjForPopulate) {
		this.empployeeObjForPopulate = empployeeObjForPopulate;
	}


	public void populateInactiveUser(Employee employee) {
		setEmpployeeObjForPopulate(null);
		setEmpployeeObjForPopulate(employee);

		resetValues();

		RequestContext context1 = RequestContext.getCurrentInstance();
		context1.execute("inactiveuser1.show();"); 
	}

	public void makeActivate(){

		resetValues();

		setBooCheckSave(true);
		setBooCheckUpdate(false);
		employeeService.activateEmployee(getEmpployeeObjForPopulate());
		setEmpployeeObjForPopulate( null);
		searchEmployee();
	}

	public java.sql.Clob stringToClob(String source) throws Exception {
		try {
			return new javax.sql.rowset.serial.SerialClob(source.toCharArray());
		} catch (Exception e) {
			return null;
		}
	}

	public void update() {
		try{
			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(getBranchId());

			RoleMaster roleMaster = new RoleMaster();
			roleMaster.setRoleId(getRoleId());

			Employee employee = new Employee();
			employee.setEmployeeId(getPkEmployeeId());

			employee.setEmployeeNumber(getEmployeeNumber());
			employee.setEmployeeName(getEmployeeName());
			employee.setEmployeeCountryId(getEmployeeCountryId());
			employee.setUserName(getUserName());
			if(getPkEmployeeId()!=null){
				employee.setPassword(getPassword());
				employee.setWuPassword(getWuPassword());
			}else{
				iCypherSecurity cypherSecurity = new CypherSecurityImpl();
				if(getUserName()!=null){
					String secretKey = getUserName().toUpperCase();
					StringBuffer secretKeys = new StringBuffer(secretKey).reverse();
					employee.setPassword(cypherSecurity.encodePassword(getPassword(), secretKeys.toString()));
					employee.setWuPassword(cypherSecurity.encodePassword(getWuPassword(), secretKeys.toString()));
				}
			}
			employee.setLocation(getLocation());
			employee.setTelephoneNumber(getTelephone());
			employee.setFsCountryBranch(countryBranch);
			employee.setFsRoleMaster(roleMaster);
			employee.setCountryId(getCountryId());

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(getCompanyId());
			employee.setFsCompanyMaster(companyMaster);
			if(getDigitalSignature()!=null){
				setSignatureSpecimenRender(true);
				setSignatureCaptureRender(false);
				try {
					employee.setSignatureSpecimenClob(stringToClob(getDigitalSignature()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				setSignatureSpecimenRender(false);
				setSignatureCaptureRender(true);
			}
			employee.setAllowFcTransaction(getAllowFCTransaction());
			employee.setIpAddress(getIpAddress());
			employee.setCashierOpt(getCashierOption());
			employee.setWuUsername(getWuUserName());
			// Added on 09'th July 2017
			employee.setWuTerminalId(getWuTerminalId());
			employee.setWuForeignTerminalId(getWuForeignTerminalId());
			employee.setWuAccountId(getWuAccId());
			employee.setWunaId(getWuNaid());
			// Added End
			
			employee.setUserType(getUserType());
			employee.setEmail(getEmail());
			employee.setDeletedUser(getCustActivateDeactivate());
			//employee.setStatus(Constants.ACTIVEEMPLOYEE);
			employee.setIsActive(Constants.Yes);
			employee.setDesignation(getDesignation());

			employee.setCreatedBy(getCreatedBy());
			employee.setCreatedDate(getCreatedDate());
			employee.setModifiedBy(getModifiedBy());
			employee.setModifiedDate(getModifiedDate());
			
			employee.setAllocateInd(getAllocateInd());
			employee.setCivilId(getCivilId());
			employee.setPasswordDate(getPasswordExpiryDate());

			employeeService.addEmployee(employee);
			resetValues();
			clearSerach();
			RequestContext.getCurrentInstance().execute("showrecord.hide();");
		}catch (NullPointerException ne) {
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void searchEmployee(){
		resetValues();
		setRenderEmployeeList(false);
		if((getSemployeeNumber() !=null && getSemployeeNumber()!="" && getSemployeeNumber().length()!=0) || (getSuserName()!=null && getSuserName()!="" && getSuserName().length()!=0) || (getSemployeeName()!=null && getSemployeeName()!="" && getSemployeeName().length()!=0) || getSbranchId()!=null || getSroleId()!=null || getStelephone() != null || (getSlocation()!=null && getSlocation()!="")){
			List<Employee> employeeList = employeeService.getEmployeesList(getSemployeeNumber(), getSuserName(), getSemployeeName(), sessionStateManage.getCountryId(), getSbranchId(), getSroleId(), getSlocation(),getStelephone());
			if(employeeList.size()>0){
				setEmployeeList(employeeList);	
				setRenderEmployeeList(true);
			}else{
				setRenderEmployeeList(false);
				RequestContext.getCurrentInstance().execute("norecord.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("atleastsearch.show();");
		}
	}

	public void populateBranch() {
		if(getPkEmployeeId() != null){
			List<CountryBranch> listSearchBranch  = loginService.fetchBranchesForEmployee(getPkEmployeeId());
			if(listSearchBranch.size()>0){
				setSearchBranchList(listSearchBranch);
			}
		}else{
			List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(sessionStateManage.getCountryId());
			if(listSearchBranch.size()>0){
				setSearchBranchList(listSearchBranch);
			}
		}
		
	}

	public void clearSerach(){
		setSemployeeName(null);
		setEmployeeNumber(null);
		setSemployeeNumber(null);
		setSbranchId(null);
		setSroleId(null);
		setSlocation(null);
		setRenderEmployeeList(false);
		setSuserName(null);
		setStelephone(null);
		//setListViewUserType(null);
	}

	public void checkWUUser(){
		String wuuser = employeeService.searchWUUser(getWuUserName(),getUserName());
		if(wuuser != null && !wuuser.equalsIgnoreCase("")){
			setWuUserName(null);
			setErrorMessage(WarningHandler.showWarningMessage("lbl.wuuseralreadyexist", sessionStateManage.getLanguageId()) + " " + wuuser);
			RequestContext.getCurrentInstance().execute("error.show();");
			//RequestContext.getCurrentInstance().execute("wuuserexist.show();");
		}
	}

	public void checkEmail(){
		String email = employeeService.checkEmail(getEmail());
		if(email != ""){
			setEmail("");
			RequestContext.getCurrentInstance().execute("emailexist.show();");
		}
	}
	public String getCustActivateDeactivate() {
		return custActivateDeactivate;
	}
	public void setCustActivateDeactivate(String custActivateDeactivate) {
		this.custActivateDeactivate = custActivateDeactivate;
	}

	public void populateAreaDesc(){
		setLocation(null);
		BigDecimal areaCode=null;
		areaCode=employeeService.toFetchAreaCode(getBranchId());
		if(areaCode != null){
			String areaName=null;
			areaName=employeeService.toFetchAreaName(areaCode);
			if(areaName != null){
				setLocation(areaName);
			}
		}
	}

	public void populateUsertype()
	{
		List<ViewUserType> lstViewUserType=employeeService.getUserTyperFromView();
		setListViewUserType(lstViewUserType);
	}

	/*public void checkEmpCodeExist(){
		try {


			List<Employee> lstEmployees=employeeService.toCheckEmpCodeExist(getEmployeeNumber());
			if (lstEmployees.size() > 0) {
				setEmployeeNumber(null);
				RequestContext.getCurrentInstance().execute("empAlreadyExist.show();");
				return;
			}
		}  catch (NullPointerException ne) {
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}*/

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void clickOnSave(){
		try{
			resetValues();
			setRenderEmployeeList(false);
			clearSerach();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeeregistration.xhtml");
		} catch (NullPointerException ne) {
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// checking EcNo with V_HR_EMPLOYEE
	public void checkEcNoWithHR(){

		try {

			String checkEmpName = employeeService.checkECNUMBERwithHRView(getEmployeeNumber());
			if(checkEmpName != null){

				setEmployeeName(checkEmpName);

				if(getEmployeeNumber() != null && getUserName() != null){
					List<Employee> lstEmployeeDB = employeeService.toCheckEmpNoUserNameExist(getEmployeeNumber(),getUserName());
					if (lstEmployeeDB.size() > 0) {
						if(lstEmployeeDB.size() == 1){
							Employee empl = lstEmployeeDB.get(0);
							if(empl.getIsActive() != null && (empl.getIsActive().equalsIgnoreCase(Constants.Yes) || empl.getIsActive().equalsIgnoreCase(Constants.No))){
								setUserName(null);
								setErrorMessage("UserName already exists for Same Employee No , Kindly go to search employee option for modification");
								RequestContext.getCurrentInstance().execute("error.show();");
								return;
							}else{
								setEmpployeeObjForPopulate(empl);
								setErrorMessage("UserName already exists for Same Employee No , Do u want to Activate ?");
								RequestContext.getCurrentInstance().execute("deletedActive.show();");
								return;
							}
						}else{
							setUserName(null);
							setErrorMessage("Multiple UserName already exists for Same Employee No , Kindly Contact IT Department");
							RequestContext.getCurrentInstance().execute("error.show();");
						}
					}
				}
			}else{
				setEmployeeName(null);
				setErrorMessage("EC Number Not Available with HR Records"); 
				RequestContext.getCurrentInstance().execute("error.show();");
			}

		}  catch (NullPointerException ne) {
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::saveDataTableRecods");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// checking username and ec no already exist or Not
	public void checkEcNoAndUserNameWithEmployee(){

		if(getEmployeeNumber() != null && getUserName() != null){
			List<Employee> lstEmployeeDB = employeeService.toCheckEmpNoUserNameExist(getEmployeeNumber(),getUserName());
			if (lstEmployeeDB.size() > 0) {
				if(lstEmployeeDB.size() == 1){
					Employee empl = lstEmployeeDB.get(0);
					if(empl.getIsActive() != null && (empl.getIsActive().equalsIgnoreCase(Constants.Yes) || empl.getIsActive().equalsIgnoreCase(Constants.No))){
						setUserName(null);
						setErrorMessage("UserName already exists for Same Employee No , Kindly go to search employee option for modification");
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}else{
						setEmpployeeObjForPopulate(empl);
						setErrorMessage("UserName already exists for Same Employee No , Do u want to Activate ?");
						RequestContext.getCurrentInstance().execute("deletedActive.show();");
						return;
					}
				}else{
					setUserName(null);
					setErrorMessage("Multiple UserName already exists for Same Employee No , Kindly Contact IT Department");
					RequestContext.getCurrentInstance().execute("error.show();");
				}

			}
			
			// arms user check
			populateOnBlurEmployeeData();
		}

	}

	// deleted record make active
	public void activateDeletedRecord(){
		resetValues();
		setBooCheckSave(true);
		setBooCheckUpdate(false);
		employeeService.activateEmployee(getEmpployeeObjForPopulate());
		setEmpployeeObjForPopulate( null);
	}

	private static final String[] HEADERS_TO_TRY = { 
		"X-Forwarded-For",
		"Proxy-Client-IP",
		"WL-Proxy-Client-IP",
		"HTTP_X_FORWARDED_FOR",
		"HTTP_X_FORWARDED",
		"HTTP_X_CLUSTER_CLIENT_IP",
		"HTTP_CLIENT_IP",
		"HTTP_FORWARDED_FOR",
		"HTTP_FORWARDED",
		"HTTP_VIA",
		"REMOTE_ADDR" 
	};

	public static String getClientIpAddress(HttpServletRequest request) {
		for (String header : HEADERS_TO_TRY) {
			String ip = request.getHeader(header);
			if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
				return ip;
			}
		}
		return request.getRemoteAddr();
	}

	public void populateLockEmployee(Employee employee) {


		if(employee.getUserName() != null && employee.getUserName().equalsIgnoreCase(sessionStateManage.getUserName())){
			setErrorMessage("Same User Cannot LOCK or UNLOCK Employee ?");
			RequestContext.getCurrentInstance().execute("sameUserEnableLockUnlock.show();");
		}else{

			setEmpployeeObjForPopulate(null);
			setEmpployeeObjForPopulate(employee);

			resetValues();

			setErrorMessage("Do u want to LOCK Employee?");
			RequestContext context1 = RequestContext.getCurrentInstance();
			context1.execute("lockEmployee.show();"); 

		}
	}

	public void populateUnlockEmployee(Employee employee) {

		if(employee.getUserName()!= null && employee.getUserName().equalsIgnoreCase(sessionStateManage.getUserName())){
			setErrorMessage("Same User Cannot LOCK or UNLOCK Employee ?");
			RequestContext.getCurrentInstance().execute("sameUserEnableLockUnlock.show();");
		}else{
			setEmpployeeObjForPopulate(null);
			setEmpployeeObjForPopulate(employee);

			resetValues();

			setErrorMessage("Do u want to UNLOCK Employee?");
			RequestContext context1 = RequestContext.getCurrentInstance();
			context1.execute("unLockEmployee.show();"); 
		}
	}

	// unlock or lock employee
	public void unlockEmployee(){

		try{
			if(getEmpployeeObjForPopulate() != null){
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				System.out.println("Local HostAddress: "+request.getRemoteAddr());
				String hostname = getClientIpAddress(request);
				String localFullIpadress = hostname;
				System.out.println("Local host name: "+hostname);

				loginService.unLockEmployeeLogin(getEmpployeeObjForPopulate().getUserName(),localFullIpadress,getEmpployeeObjForPopulate().getEmployeeId(),Boolean.TRUE);
				// reload
				searchEmployee();
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}


	}

	// unlock or lock employee
	public void lockEmployee(){

		try{
			if(getEmpployeeObjForPopulate() != null){
				HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
				System.out.println("Local HostAddress: "+request.getRemoteAddr());
				String hostname = getClientIpAddress(request);
				String localFullIpadress = hostname;
				System.out.println("Local host name: "+hostname);

				loginService.unLockEmployeeLogin(getEmpployeeObjForPopulate().getUserName(),localFullIpadress,getEmpployeeObjForPopulate().getEmployeeId(),Boolean.FALSE);
				// reload
				searchEmployee();
			}

		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	// fetch all Countrys
	public void fetchAllEmployeeCountrys(){
		setLstEmpCountry(null);
		List<CountryMasterDesc> lstAllCountrys = generalService.getCountryList(sessionStateManage.getLanguageId());
		if(lstAllCountrys != null && lstAllCountrys.size() != 0){
			setLstEmpCountry(lstAllCountrys);
		}
	}
	
	public void checkWUDetails(){
		String wuuser = employeeService.searchWUForeignTerminalId(getWuForeignTerminalId(),getUserName());
		if(wuuser != null && !wuuser.equalsIgnoreCase("")){
			setWuForeignTerminalId(null);
			setErrorMessage(WarningHandler.showWarningMessage("lbl.wuTerminalalreadyexist", sessionStateManage.getLanguageId()) + " " + wuuser);
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}
	
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	
	public Date getPasswordExpiryDate() {
		return passwordExpiryDate;
	}
	public void setPasswordExpiryDate(Date passwordExpiryDate) {
		this.passwordExpiryDate = passwordExpiryDate;
	}
	
}
