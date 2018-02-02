package com.amg.exchange.telemarketing.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.remittance.bean.PopulateDataWithCode;
import com.amg.exchange.telemarketing.model.TelemartCustomer;
import com.amg.exchange.telemarketing.model.TelemartFolwUp;
import com.amg.exchange.telemarketing.model.VwExTelemartFolwUpCode;
import com.amg.exchange.telemarketing.service.TelemarketingService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("telemarketingBean")
@Scope("session")
public class TelemarketingBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(TelemarketingBean.class);

	private String errorMessage;
	private String alloCustomerName;
	private String alloNationality;
	private BigDecimal nationalityId;
	private Date alloLastTransactionDate;
	private BigDecimal selectedStaff;
	private BigDecimal selectAllocateCus;
	private BigDecimal applicationCountryId;
	private String sameOrDiffNationality;
	private BigDecimal telmartBranchwiseTableId;
	private BigDecimal selectEnquiryStaff;
	private BigDecimal staffFilter;

	private String followupCode;
	private Date followupDate;
	private Date nextFollowupDate;
	private String remarks;
	private BigDecimal followupTableId;
	private BigDecimal followupCusId;
	private BigDecimal followupEmpId;
	private BigDecimal followupAppCountryId;
	private String lastFollowUp = null;
	private BigDecimal teleMartCustomerId;
	private BigDecimal noofCustomerSelected;
	private String searchNationality;

	private Boolean branchShow = true;
	private Boolean allcationShow = false;
	private Boolean staffDisplay = false;
	private Boolean teleEnquiryTable = true;
	private Boolean teleDrillDownTable = false;
	private Boolean teleEnquiryStaffInput = false;
	private Boolean teleEnquiryStaffDD = false;	

	private SessionStateManage session = new SessionStateManage();
	private String loginUsername;
	private BigDecimal nationalitypopId;
	
	private BigDecimal teleEnquirySearchCO;
	private String teleEnquirySearchCN;
	private String teleEnquirySearchMO;

	@Autowired
	TelemarketingService telemarketingService;
	@Autowired
	IGeneralService<T> generalService;

	private List<TelemarketingBranchWiseTable> selectedCustomers = null;
	private List<TelemartCustomer> getTelemartCustomerList = null;
	private List<PopulateDataWithCode> getEmployeeList = null;
	private List<Employee> employeeNames = null;
	private List<VwExTelemartFolwUpCode> followUpCodes = null;

	private List<TelemarketingBranchWiseTable> telemarketingBranchWiseTable = null;
	private List<TelemarketingBranchWiseTable> tempTelemarketingBranchWiseTable = null;
	private List<TelemarketingAllocationDataTable> telemarketingDataTable = null;
	private List<TelemarketingFollowUpDataTable> telemarketingFollowUpDataTable = null;
	private List<TelemarketingEnquiryDataTable> telemarketingEnquiryDataTable = new ArrayList<TelemarketingEnquiryDataTable>();
	private List<TelemarketingEnquiryDataTable> tempTelemarketingEnquiryDataTable = new ArrayList<TelemarketingEnquiryDataTable>();
	private List<TelemarketingFollowUpDataTable> selectedFollowUp = null;
	private List<TelemarketingDrillDownTable> telemarketingDrillDownTable = null;
	private Date nextFolwDate = new Date();
	private Date nextFolwDateMax = new Date();
	private List<TelemarketingBranchWiseTable> lstSelectedTeleMarBrWsData = new ArrayList<TelemarketingBranchWiseTable>();
	private List<TelemarketingFollowUpDataTable> lstSelectedTeleFollUpData = new ArrayList<TelemarketingFollowUpDataTable>();

	private Map<BigDecimal,String> lstStaffDetails = new HashMap<BigDecimal, String>();
	private List<PopulateDataWithCode> lstNationalityPopulate = new ArrayList<PopulateDataWithCode>();
	private List<CountryMasterDesc> lstAllNationality = new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal,String> mapNationalityCode = new HashMap<BigDecimal, String>();
	private Map<BigDecimal,String> mapNationalityName = new HashMap<BigDecimal, String>();

	// REDIRECT TO TELEMARKETING CUSTOMER LIST SCREEN.
	public void pageNavigation() {
		try {

			setSelectedCustomers(null);
			setBranchShow(true);
			setAllcationShow(false);
			setStaffDisplay(false);
			setNationalityId(null);
			setLstSelectedTeleFollUpData(null);
			setLstSelectedTeleMarBrWsData(null);
			setTelemarketingBranchWiseTable(null);
			setTempTelemarketingBranchWiseTable(null);
			setNationalitypopId(null);

			searchNationalityRecord();
			// Not done transactions in the last 3 month records list.
			/*List<Customer> teleCustomerList = telemarketingService.getCustomersList();

			// Save Telemarketing customers.
			for (Customer customer : teleCustomerList) {
				TelemartCustomer telemartCustomer = new TelemartCustomer();
				telemartCustomer.setApplicationCountryId(customer.getFsCountryMasterByCountryId().getCountryId());
				telemartCustomer.setCustomerId(customer.getCustomerId());
				telemartCustomer.setCountryBranchId(customer.getBranchCode());
				telemartCustomer.setRemarks(customer.getRemarks());
				telemartCustomer.setCreatedDate(customer.getCreationDate());
				telemartCustomer.setCreatedBy(customer.getCreatedBy());
				telemartCustomer.setModifiedDate(new Date());
				telemartCustomer.setModifiedBy(session.getUserName());
				telemartCustomer.setIsActive(customer.getIsActive());

				telemarketingService.saveTelemarkCustomers(telemartCustomer);
			}*/

			// Find out countryBranchId.
			//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());			

			// Branch Wise display the records.
			List<TelemartCustomer> branchWiseList = telemarketingService.getTelemartCustomerList(new BigDecimal(session.getBranchId()));
			List<TelemarketingBranchWiseTable> telemarketingBranchWiseList = new ArrayList<TelemarketingBranchWiseTable>();

			List<BigDecimal> duplicateCheckNationality = new ArrayList<BigDecimal>();
			lstNationalityPopulate.clear();

			for (TelemartCustomer customerList : branchWiseList) {

				TelemarketingBranchWiseTable telemarketingBranchWiseTable = new TelemarketingBranchWiseTable();

				if(customerList.getCustomerId() != null){

					System.out.println("Customer Id : " + customerList.getCustomerId());

					//if(customerList.getCustomerId().compareTo(new BigDecimal(656283))==0){
					// Fetch the customer details.
					List<Customer> customerDetails = telemarketingService.getCustomerList(customerList.getCustomerId());

					if(customerDetails != null && customerDetails.size() != 0){

						Customer customer = customerDetails.get(0);

						telemarketingBranchWiseTable.setTelmartBranchwiseTableId(customerList.getTelemartCustomerId());
						telemarketingBranchWiseTable.setCustomerRefNum(customer.getCustomerReference());
						telemarketingBranchWiseTable.setCusId(customerList.getCustomerId());
						telemarketingBranchWiseTable.setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
						telemarketingBranchWiseTable.setMobileNum(customer.getMobile());

						if(customer.getFsCountryMasterByNationality() != null){
							telemarketingBranchWiseTable.setNationality(customer.getFsCountryMasterByNationality().getCountryId());
							String nationality = telemarketingService.getNationality(session.getLanguageId(),customer.getFsCountryMasterByNationality().getCountryId());
							if(nationality != null){
								telemarketingBranchWiseTable.setNationalityName(nationality);
							}
						}
						telemarketingBranchWiseTable.setLastTransDate(customer.getLastTransactionDate());

						telemarketingBranchWiseList.add(telemarketingBranchWiseTable);

						// nationality drop down
						if (customer.getFsCountryMasterByNationality() != null && !duplicateCheckNationality.contains(customer.getFsCountryMasterByNationality().getCountryId())) {
							duplicateCheckNationality.add(customer.getFsCountryMasterByNationality().getCountryId());
							PopulateDataWithCode popData = new PopulateDataWithCode();
							popData.setId(customer.getFsCountryMasterByNationality().getCountryId());
							popData.setCode(mapNationalityCode.get(customer.getFsCountryMasterByNationality().getCountryId()));
							popData.setName(mapNationalityName.get(customer.getFsCountryMasterByNationality().getCountryId()));
							lstNationalityPopulate.add(popData);
						}
					}else{
						// customer problem
					}
					//}
				}else{
					// customer problem
				}
			}
			setTelemarketingBranchWiseTable(telemarketingBranchWiseList);
			setTempTelemarketingBranchWiseTable(telemarketingBranchWiseList);

			telemartAllo();

			FacesContext.getCurrentInstance().getExternalContext().redirect("../telemarketing/telemarketing.xhtml");
			
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());			
		}
	}

	// populate selected country records
	public void selectedNationality(){
		if(getNationalitypopId() != null){
			List<TelemarketingBranchWiseTable> telemarketingBranchWiseList = new ArrayList<TelemarketingBranchWiseTable>();
			for (TelemarketingBranchWiseTable customerList : getTempTelemarketingBranchWiseTable()) {
				if(customerList.getNationality() != null && customerList.getNationality().compareTo(getNationalitypopId())==0){
					TelemarketingBranchWiseTable telemarketingBranchWiseTable = new TelemarketingBranchWiseTable();

					// Fetch the customer details.
					List<Customer> customerDetails = telemarketingService.getCustomerList(customerList.getCusId());

					if(customerDetails != null && customerDetails.size() != 0){

						Customer customer = customerDetails.get(0);

						telemarketingBranchWiseTable.setTelmartBranchwiseTableId(customerList.getTelmartBranchwiseTableId());
						telemarketingBranchWiseTable.setCustomerRefNum(customer.getCustomerReference());
						telemarketingBranchWiseTable.setCusId(customerList.getCusId());
						telemarketingBranchWiseTable.setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
						telemarketingBranchWiseTable.setMobileNum(customer.getMobile());

						if(customer.getFsCountryMasterByNationality() != null){
							telemarketingBranchWiseTable.setNationality(customer.getFsCountryMasterByNationality().getCountryId());
							String nationality = telemarketingService.getNationality(session.getLanguageId(),customer.getFsCountryMasterByNationality().getCountryId());
							if(nationality != null){
								telemarketingBranchWiseTable.setNationalityName(nationality);
							}
						}
						telemarketingBranchWiseTable.setLastTransDate(customer.getLastTransactionDate());

						telemarketingBranchWiseList.add(telemarketingBranchWiseTable);
					}else{
						// customer problem
					}
				}
			}
			setTelemarketingBranchWiseTable(null);
			setTelemarketingBranchWiseTable(telemarketingBranchWiseList);

			telemartAllo();
		}else{
			setTelemarketingBranchWiseTable(null);
			setTelemarketingBranchWiseTable(getTempTelemarketingBranchWiseTable());
		}
	}

	public void onRowSelect(TelemarketingBranchWiseTable event) {

		TelemarketingBranchWiseTable selectdValue = ((TelemarketingBranchWiseTable) event);

		if(getLstSelectedTeleMarBrWsData() != null && getLstSelectedTeleMarBrWsData().size() != 0){
			boolean chkNat = true;

			for (TelemarketingBranchWiseTable allSelRec1 : getTelemarketingBranchWiseTable()) {
				if(allSelRec1.isSelectedRecords()){
					if(allSelRec1.getNationality().compareTo(selectdValue.getNationality())!=0){
						chkNat = false;
						break;
					}
				}
			}

			if(selectdValue.getNationality() != null){
				if(chkNat){
					List<TelemarketingBranchWiseTable> fetchRec = new ArrayList<TelemarketingBranchWiseTable>();
					for (TelemarketingBranchWiseTable allSelRec : getTelemarketingBranchWiseTable()) {
						if(allSelRec.isSelectedRecords()){
							allSelRec.setSelectedRecords(true);
							fetchRec.add(allSelRec);
						}
					}
					setNationalityId(selectdValue.getNationality());
					setLstSelectedTeleMarBrWsData(fetchRec);
				}else{
					// stop due to different nation
					selectdValue.setSelectedRecords(false);
				}
			}else{
				// stop due to nation is empty
				selectdValue.setSelectedRecords(false);
			}
		}else{
			List<TelemarketingBranchWiseTable> firstRec = new ArrayList<TelemarketingBranchWiseTable>();
			firstRec.add(selectdValue);
			setNationalityId(selectdValue.getNationality());

			setLstSelectedTeleMarBrWsData(firstRec);
		}

		//setTelmartBranchwiseTableId(selectdValue.getTelmartBranchwiseTableId());
		//setSelectAllocateCus(selectdValue.getCusId());
		//setApplicationCountryId(selectdValue.getNationality());
		//setAlloCustomerName(selectdValue.getCustomerName());
		//String nationality = telemarketingService.getNationality(selectdValue.getNationality());
		//setAlloNationality(nationality);
		//setAlloLastTransactionDate(selectdValue.getLastTransDate());	

		setSelectedStaff(null);
		setGetEmployeeList(null);
		setStaffFilter(null);
		setNoofCustomerSelected(new BigDecimal(getLstSelectedTeleMarBrWsData().size()));
		//setBranchShow(false);
		//setAllcationShow(true);
	}

	// checking the check boxes and then proceeding to staff allocation
	public void nextToStaffAllocation(){
		if(getLstSelectedTeleMarBrWsData() != null && getLstSelectedTeleMarBrWsData().size() != 0){
			setBranchShow(false);
			setAllcationShow(true);
			// nationality
			List<CountryMasterDesc> lstNationalityName = generalService.getNationalityList(session.getLanguageId());
			for (CountryMasterDesc countryMasterDesc : lstNationalityName) {
				lstStaffDetails.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}
		}else{
			setErrorMessage("Please select minimum one record");
			RequestContext.getCurrentInstance().execute("error.show();");	
		}
	}

	private void telemartAllo() {
		// setSelectedStaff(null);
		List<TelemarketingAllocationDataTable> telemarketingEmployeeList = new ArrayList<TelemarketingAllocationDataTable>();
		//List<TelemartEmployee> cusAndEmpIds = telemarketingService.getTelemartEmployeeList();
		//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());	

		List<TelemartCustomer> cusAndEmpIds = telemarketingService.getTelemartCustomerDataByStaff(new BigDecimal(session.getBranchId()));
		if (cusAndEmpIds.size() > 0) {
			List<Object []> customerCount = telemarketingService.getCustomersCount(new BigDecimal(session.getBranchId()));

			HashMap<BigDecimal, BigDecimal> rtnMap = new HashMap<BigDecimal, BigDecimal>();
			if(customerCount!=null && customerCount.size()>0)
			{
				for(Object [] obj:customerCount)
				{
					Object empid=obj[0];
					Object count=obj[1];
					if(empid!=null && count!=null)
					{
						TelemarketingAllocationDataTable telemarkDataTable = new TelemarketingAllocationDataTable();

						List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(new BigDecimal(empid.toString()));	

						if(employeeName != null && employeeName.size() != 0){
							telemarkDataTable.setDisplayEmployeeName(employeeName.get(0).getEmployeeName());
						}

						if(count != null){
							telemarkDataTable.setCusCount(new BigDecimal(count.toString()));
						}
						
						telemarketingEmployeeList.add(telemarkDataTable);
					}

				}
			}			
		}
		setTelemarketingDataTable(telemarketingEmployeeList);
	}



	public void staffFilterSelected() {

		setSelectedStaff(null);
		setStaffDisplay(false);
		setGetEmployeeList(null);
		if (getStaffFilter().equals(new BigDecimal(1))) {
			List<Employee> employeeSameNationalityList = telemarketingService
					.getEmployeesSameNationality(getNationalityId(),new BigDecimal(session.getBranchId()));
			if (employeeSameNationalityList != null && employeeSameNationalityList.size() > 0) {
				List<PopulateDataWithCode> lstPdata = new ArrayList<PopulateDataWithCode>();
				for (Employee employee : employeeSameNationalityList) {
					PopulateDataWithCode pdata = new PopulateDataWithCode();
					pdata.setId(employee.getEmployeeId());
					pdata.setCode(lstStaffDetails.get(employee.getEmployeeCountryId()));
					pdata.setName(employee.getEmployeeName() + " - " + employee.getUserName());

					lstPdata.add(pdata);
				}
				setGetEmployeeList(lstPdata);
				setStaffDisplay(true);
			}
		} else if (getStaffFilter().equals(new BigDecimal(2))) {
			// Find out countryBranchId.
			//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());
			List<Employee> employeeDiffNationalityList = telemarketingService.getEmployeesDiffNationality(session.getCountryId(),new BigDecimal(session.getBranchId()));
			if (employeeDiffNationalityList != null && employeeDiffNationalityList.size() > 0) {
				List<PopulateDataWithCode> lstPdata = new ArrayList<PopulateDataWithCode>();
				for (Employee employee : employeeDiffNationalityList) {
					PopulateDataWithCode pdata = new PopulateDataWithCode();
					pdata.setId(employee.getEmployeeId());
					pdata.setCode(lstStaffDetails.get(employee.getEmployeeCountryId()));
					pdata.setName(employee.getEmployeeName() + " - " + employee.getUserName());

					lstPdata.add(pdata);
				}
				setGetEmployeeList(lstPdata);
				setStaffDisplay(true);
			}

		} else {
			if (getStaffFilter().equals(new BigDecimal(0))) {
				setGetEmployeeList(null);
				setStaffDisplay(false);
			}			
		}
	}	

	public void saveTelemartEmployee() {
		try {
			if (getStaffFilter() == null || getStaffFilter().equals(new BigDecimal(0))) {
				RequestContext.getCurrentInstance().execute("staffFilter.show();");
			} else if (getLstSelectedTeleMarBrWsData() != null && getLstSelectedTeleMarBrWsData().size() != 0) {

				telemarketingService.saveTelemartEmployee(getLstSelectedTeleMarBrWsData(),getSelectedStaff());

				RequestContext.getCurrentInstance().execute("saveSelected.show();");
			} else {
				RequestContext.getCurrentInstance().execute("staffToAllocate.show();");
			}

		} catch (Exception e) {
			setErrorMessage(e.toString());
			RequestContext.getCurrentInstance().execute("error.show();");			
		}

	}


	public void okSelectedInSave() {
		//telemarketingAllocationTable();
		telemartAllo();
		setSelectedStaff(null);
		setStaffFilter(null);
		setStaffDisplay(false);
	}

	public void exit() {
		try {
			setSelectedCustomers(null);
			setBranchShow(true);
			setAllcationShow(false);
			setStaffDisplay(false);
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	

	public void backSelected() {
		setSelectedCustomers(null);
		setSelectedStaff(null);
		setBranchShow(true);
		setAllcationShow(false);
		setStaffDisplay(false);
		setLstSelectedTeleMarBrWsData(null);
		pageNavigation();
	}

	// REDIRECT TO TELEMARKETING FOLLOW UP SCREEN.
	public void teleMarkFollowUpPageNavigation() {
		try {
			setSelectedFollowUp(null);
			setNationalityId(null);
			setLstSelectedTeleFollUpData(null);
			setLstSelectedTeleMarBrWsData(null);
			clear();
			Date cDate = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(cDate); 
			c.add(Calendar.DATE, 1);
			nextFolwDate = c.getTime();

			//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());
			BigDecimal empId = session.getEmployeeId();

			List<VwExTelemartFolwUpCode> folwupCodes = telemarketingService.getTelemartFollowUpCodes();
			setFollowUpCodes(folwupCodes);

			List<TelemartCustomer> telCusList = telemarketingService.getFollowUpTableList(new BigDecimal(session.getBranchId()) ,empId);
			List<TelemarketingFollowUpDataTable> telemarketingFollowUpTableList = new ArrayList<TelemarketingFollowUpDataTable>();


			for (TelemartCustomer telemartCustomer : telCusList) {

				TelemarketingFollowUpDataTable telemarketingFollowUp = new TelemarketingFollowUpDataTable();

				if(telemartCustomer.getCustomerId() != null){

					// Fetch the customer details.
					List<Customer> customerDetails = telemarketingService.getCustomerList(telemartCustomer.getCustomerId());

					if(customerDetails != null && customerDetails.size() != 0){

						Customer customer = customerDetails.get(0);

						telemarketingFollowUp.setTelmartFollowupTableId(telemartCustomer.getTelemartCustomerId());
						telemarketingFollowUp.setAppCountryId(telemartCustomer.getApplicationCountryId());
						telemarketingFollowUp.setCusId(telemartCustomer.getCustomerId());
						telemarketingFollowUp.setCustomerRefNum(customer.getCustomerReference());
						telemarketingFollowUp.setTelmartFollowupTableId(telemartCustomer.getTelemartCustomerId());
						telemarketingFollowUp.setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
						telemarketingFollowUp.setEmpId(telemartCustomer.getEmployeeId());
						telemarketingFollowUp.setMobileNum(customer.getMobile());
						if(telemartCustomer.getFolwUpCode()!=null)
						{
							List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(telemartCustomer.getEmployeeId());
							if(employeeName != null && employeeName.size() != 0){
								telemarketingFollowUp.setLastFollowUp(employeeName.get(0).getEmployeeName());
							}
						}
						//telemarketingFollowUp.setLastFollowUp(session.getUserName());
						telemarketingFollowUp.setLastFollowUpDate(telemartCustomer.getFolwUpDate());
						telemarketingFollowUp.setRemarks(telemartCustomer.getRemarks());
						telemarketingFollowUp.setTeleMartCustomerId(telemartCustomer.getTelemartCustomerId());
						telemarketingFollowUp.setNextFollowUpDate(telemartCustomer.getNextFolwUpDate());
						
						telemarketingFollowUpTableList.add(telemarketingFollowUp);

						/*if(telemartCustomer.getNextFolwUpDate() != null){
							SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
							Date today = new Date();
							Date currenctDate = sdf.parse(sdf.format(today));
							Date nextFollowDate = sdf.parse(sdf.format(telemartCustomer.getNextFolwUpDate()));

							if(nextFollowDate.before(currenctDate) || nextFollowDate.equals(currenctDate)){
								telemarketingFollowUpTableList.add(telemarketingFollowUp);
							}else{
								// skip
							}
						}else{
							telemarketingFollowUpTableList.add(telemarketingFollowUp);
						}*/
					}else{
						// customer problem
					}
				}else{
					// customer problem
				}
			}
			setTelemarketingFollowUpDataTable(telemarketingFollowUpTableList);

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../telemarketing/telemarketingFollowUp.xhtml");
		} catch (Exception e) {
			setErrorMessage(e.toString());
			RequestContext.getCurrentInstance().execute("error.show();");			
		}
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public void onRowFollowupSelect(TelemarketingFollowUpDataTable event) {

		TelemarketingFollowUpDataTable selectedFollowupValue = ((TelemarketingFollowUpDataTable) event);

		if(getLstSelectedTeleFollUpData() != null && getLstSelectedTeleFollUpData().size() != 0){

			List<TelemarketingFollowUpDataTable> fetchRec = new ArrayList<TelemarketingFollowUpDataTable>();
			for (TelemarketingFollowUpDataTable allSelRec : getTelemarketingFollowUpDataTable()) {
				if(allSelRec.getTelmartFollowupTableId().compareTo(selectedFollowupValue.getTelmartFollowupTableId())==0){
					if(allSelRec.isSelectedRecords()){
						allSelRec.setSelectedRecords(true);
						fetchRec.add(allSelRec);
					}else{
						allSelRec.setSelectedRecords(false);
					}
				}else{
					allSelRec.setSelectedRecords(false);
				}
			}
			setLstSelectedTeleFollUpData(fetchRec);
		}else{
			List<TelemarketingFollowUpDataTable> firstRec = new ArrayList<TelemarketingFollowUpDataTable>();
			firstRec.add(selectedFollowupValue);

			setLstSelectedTeleFollUpData(firstRec);
		}


		setFollowupTableId(selectedFollowupValue.getTelmartFollowupTableId());
		setFollowupCusId(selectedFollowupValue.getCusId());
		setFollowupEmpId(selectedFollowupValue.getEmpId());
		setFollowupAppCountryId(selectedFollowupValue.getAppCountryId());
		setFollowupDate(new Date());
		setTeleMartCustomerId(selectedFollowupValue.getTeleMartCustomerId());

	}	

	public void saveTelemartFollowUp() {
		try {
			if(getTelemarketingFollowUpDataTable() != null && getTelemarketingFollowUpDataTable().size() < 1){
				RequestContext.getCurrentInstance().execute("followupDataTable.show();");
			}else if(getLstSelectedTeleFollUpData() != null && getLstSelectedTeleFollUpData().size() > 1){
				RequestContext.getCurrentInstance().execute("checkBoxCount.show();");
			} else if(getFollowupCode()==null || getFollowupCode().trim().equalsIgnoreCase("")){
				RequestContext.getCurrentInstance().execute("folwUpCd.show();");
			} else if(getNextFollowupDate()==null || getNextFollowupDate().equals("")){
				// no need to check follow up date while saving with follow up code is 900
				if(getFollowupCode() != null && getFollowupCode().equalsIgnoreCase(Constants.TeleFollowUpCodeClose)){
					if(getLstSelectedTeleFollUpData() != null && getLstSelectedTeleFollUpData().size() == 1){

						TelemarketingFollowUpDataTable selectedObj = getLstSelectedTeleFollUpData().get(0);

						if(selectedObj.isSelectedRecords()){
							TelemartFolwUp telemartFolwUp = new TelemartFolwUp();

							telemartFolwUp.setApplicationCountryId(getFollowupAppCountryId());
							telemartFolwUp.setCustomerId(getFollowupCusId()); 
							telemartFolwUp.setEmployeeId(session.getEmployeeId());
							telemartFolwUp.setFolwUpCode(getFollowupCode()); 
							telemartFolwUp.setFolwUpDate(getFollowupDate());
							telemartFolwUp.setNextFolwUpDate(getNextFollowupDate()); 
							telemartFolwUp.setRemarks(getRemarks());
							telemartFolwUp.setCreatedBy(session.getUserName()); 		 
							telemartFolwUp.setIsActive(Constants.Y);
							telemartFolwUp.setTeleMartCustomerId(getTeleMartCustomerId());
							telemarketingService.saveTelemartFolwup(telemartFolwUp,getFollowupTableId());					 

							RequestContext.getCurrentInstance().execute("saveSelectedFol.show();");	
						}else{
							RequestContext.getCurrentInstance().execute("checkBox.show();");
						}
					}else{
						RequestContext.getCurrentInstance().execute("checkBox.show();");
					}

				}else{
					RequestContext.getCurrentInstance().execute("nextFolwDT.show();");
				}
			} else{
				if(getLstSelectedTeleFollUpData() != null && getLstSelectedTeleFollUpData().size() == 1){

					TelemarketingFollowUpDataTable selectedObj = getLstSelectedTeleFollUpData().get(0);
					if(selectedObj.isSelectedRecords()){
						TelemartFolwUp telemartFolwUp = new TelemartFolwUp();

						telemartFolwUp.setApplicationCountryId(getFollowupAppCountryId());
						telemartFolwUp.setCustomerId(getFollowupCusId()); 
						telemartFolwUp.setEmployeeId(session.getEmployeeId());
						telemartFolwUp.setFolwUpCode(getFollowupCode()); 
						telemartFolwUp.setFolwUpDate(getFollowupDate());
						telemartFolwUp.setNextFolwUpDate(getNextFollowupDate()); 
						telemartFolwUp.setRemarks(getRemarks());
						telemartFolwUp.setCreatedBy(session.getUserName()); 		 
						telemartFolwUp.setIsActive(Constants.Y);
						telemartFolwUp.setTeleMartCustomerId(getTeleMartCustomerId());
						telemarketingService.saveTelemartFolwup(telemartFolwUp,getFollowupTableId());					 

						RequestContext.getCurrentInstance().execute("saveSelectedFol.show();");	
					}else{
						RequestContext.getCurrentInstance().execute("checkBox.show();");
					}
				}else{
					RequestContext.getCurrentInstance().execute("checkBox.show();");
				}
			}					
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");			
		}
	}

	public void okSelectedInFollowUp() {
		clear();		
		teleMarkFollowUpPageNavigation();
	}

	public void clear(){
		setSelectedFollowUp(null);
		setFollowupCode(null);
		setNextFollowupDate(null);
		setRemarks(null);
	}

	// REDIRECT TO TELEMARKETING ENQUIRY SCREEN.
	public void teleMarkEnquiryPageNavigation() {
		try {			
			setSelectEnquiryStaff(null);
			setTelemarketingEnquiryDataTable(null);
			// Find out countryBranchId.
			//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());
			List<Employee> employeeNames = telemarketingService
					.getEmployeeNames(new BigDecimal(session.getBranchId()));
			setEmployeeNames(employeeNames);
			if(session.getRoleId().equalsIgnoreCase("1") || session.getRoleId().equalsIgnoreCase("9") || session.getRoleId().equalsIgnoreCase("3")){
				setTeleEnquiryStaffDD(true);
				setTeleEnquiryStaffInput(false);

			}else if(session.getRoleId().equalsIgnoreCase("2")){
				setTeleEnquiryStaffDD(false);
				setTeleEnquiryStaffInput(true);
				setLoginUsername(session.getUserName());
				//staffDsplay();
				setSelectEnquiryStaff(session.getEmployeeId());
				staffAllocatedEnquiry();
			}else{
				setTeleEnquiryStaffDD(false);
				setTeleEnquiryStaffInput(true);
				setLoginUsername(session.getUserName());
				//staffDsplay();
				setSelectEnquiryStaff(session.getEmployeeId());
				staffAllocatedEnquiry();
			}

			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../telemarketing/telemarketingEnquiry.xhtml");
		} catch (Exception e) {
			setErrorMessage(e.toString());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	private void staffDsplay(){
		//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());
		List<TelemartCustomer> telCusList = telemarketingService
				.getFollowUpTableList(new BigDecimal(session.getBranchId()),session.getEmployeeId());

		List<TelemarketingEnquiryDataTable> telemarketingEnquiryDataTableList = new ArrayList<TelemarketingEnquiryDataTable>();

		for (TelemartCustomer telemartCustomer : telCusList) {

			TelemarketingEnquiryDataTable telemarketingEnquiryDataTable = new TelemarketingEnquiryDataTable();

			if(telemartCustomer.getCustomerId() != null){
				// Fetch the customer details.
				List<Customer> customerDetails = telemarketingService.getCustomerList(telemartCustomer.getCustomerId());

				if (customerDetails != null && customerDetails.size() > 0) {
					Customer customer = customerDetails.get(0);
					List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(telemartCustomer.getEmployeeId());
					telemarketingEnquiryDataTable.setCusId(telemartCustomer.getCustomerId());
					telemarketingEnquiryDataTable.setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
					telemarketingEnquiryDataTable.setCustomerRefNum(customerDetails.get(0).getCustomerReference());
					telemarketingEnquiryDataTable.setMobileNum(customer.getMobile());
					telemarketingEnquiryDataTable.setLastFollowUpDate(telemartCustomer.getFolwUpDate());
					telemarketingEnquiryDataTable.setLastFollowUpCode(telemartCustomer.getFolwUpCode());
					telemarketingEnquiryDataTable.setEmpId(telemartCustomer.getEmployeeId());
					telemarketingEnquiryDataTable.setRemarks(telemartCustomer.getRemarks());
					if (employeeName != null && employeeName.size() != 0) {
						telemarketingEnquiryDataTable.setEmployeeName(employeeName.get(0).getEmployeeName());
					}
					telemarketingEnquiryDataTable.setAllocatedDate(telemartCustomer.getAllocatedDate());
					telemarketingEnquiryDataTable.setNextFollowUpDate(telemartCustomer.getNextFolwUpDate());
					telemarketingEnquiryDataTableList.add(telemarketingEnquiryDataTable);
				}
			}else{
				// customer problem
			}
		}

		setTelemarketingEnquiryDataTable(telemarketingEnquiryDataTableList);
		setTempTelemarketingEnquiryDataTable(telemarketingEnquiryDataTableList);
	}



	public void staffAllocatedEnquiry() {

		//List<TelemartCustomer> telCusList = telemarketingService.getTelCusList(getSelectEnquiryStaff());
		//BigDecimal countryBranchId = telemarketingService.getCountryBranchId(session.getCountryBranchCode());
		List<TelemartCustomer> telCusList = telemarketingService.getFollowUpTableListInquiry(new BigDecimal(session.getBranchId()),getSelectEnquiryStaff());

		List<TelemarketingEnquiryDataTable> telemarketingEnquiryDataTableList = new ArrayList<TelemarketingEnquiryDataTable>();

		for (TelemartCustomer telemartCustomer : telCusList) {

			TelemarketingEnquiryDataTable telemarketingEnquiryDataTable = new TelemarketingEnquiryDataTable();

			if(telemartCustomer.getCustomerId() != null){
				// Fetch the customer details.
				List<Customer> customerDetails = telemarketingService.getCustomerList(telemartCustomer.getCustomerId());

				if (customerDetails != null && customerDetails.size() > 0) {
					Customer customer = customerDetails.get(0);
					List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(telemartCustomer.getEmployeeId());
					telemarketingEnquiryDataTable.setCusId(telemartCustomer.getCustomerId());
					telemarketingEnquiryDataTable.setCustomerName(nullCheck(customer.getFirstName()) + " " + nullCheck(customer.getMiddleName()) + " " + nullCheck(customer.getLastName()));
					telemarketingEnquiryDataTable.setCustomerRefNum(customer.getCustomerReference());
					telemarketingEnquiryDataTable.setMobileNum(customer.getMobile());
					telemarketingEnquiryDataTable.setLastFollowUpDate(telemartCustomer.getFolwUpDate());
					if(telemartCustomer.getFolwUpCode() != null){
						String tmFwDesc = telemarketingService.getTelemartFollowUpCodes(telemartCustomer.getFolwUpCode());
						telemarketingEnquiryDataTable.setLastFollowUpCode(tmFwDesc);
					}
					telemarketingEnquiryDataTable.setEmpId(telemartCustomer.getEmployeeId());
					telemarketingEnquiryDataTable.setRemarks(telemartCustomer.getRemarks());
					if (employeeName != null && employeeName.size() != 0) {
						telemarketingEnquiryDataTable.setEmployeeName(employeeName.get(0).getEmployeeName());
					}
					telemarketingEnquiryDataTable.setAllocatedDate(telemartCustomer.getAllocatedDate());
					telemarketingEnquiryDataTable.setNextFollowUpDate(telemartCustomer.getNextFolwUpDate());
					telemarketingEnquiryDataTableList.add(telemarketingEnquiryDataTable);
				}
			}else{
				// customer problem
			}
		}
		setTelemarketingEnquiryDataTable(telemarketingEnquiryDataTableList);
		setTempTelemarketingEnquiryDataTable(telemarketingEnquiryDataTableList);
	}

	public void drillDownSelected(TelemarketingEnquiryDataTable telemarketingEnquiryDataTable){
		List<TelemartFolwUp> telemartFolwupList = telemarketingService.getTelemartFollowupPk(telemarketingEnquiryDataTable.getCusId());
		List<TelemarketingDrillDownTable> telemarketingDrillDownTableList = new ArrayList<TelemarketingDrillDownTable>();

		for(TelemartFolwUp telemartFolwUp : telemartFolwupList) {

			TelemarketingDrillDownTable telemarketingDrillDown = new TelemarketingDrillDownTable();

			if(telemartFolwUp.getCustomerId() != null){
				List<Customer> customerDetails = telemarketingService.getCustomerList(telemartFolwUp.getCustomerId());

				if(customerDetails != null && customerDetails.size() != 0){

					List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(telemartFolwUp.getEmployeeId());

					telemarketingDrillDown.setCustomerName(nullCheck(customerDetails.get(0).getFirstName()) + " " + nullCheck(customerDetails.get(0).getMiddleName()) + " " + nullCheck(customerDetails.get(0).getLastName()));
					telemarketingDrillDown.setCusId(customerDetails.get(0).getCustomerId());
					telemarketingDrillDown.setCustomerRefNum(customerDetails.get(0).getCustomerReference());
					telemarketingDrillDown.setMobileNum(customerDetails.get(0).getMobile());
					telemarketingDrillDown.setLastFollowUpDate(telemartFolwUp.getFolwUpDate());
					if(telemartFolwUp.getFolwUpCode() != null){
						String tmFwDesc = telemarketingService.getTelemartFollowUpCodes(telemartFolwUp.getFolwUpCode());
						telemarketingDrillDown.setLastFollowUpCode(tmFwDesc);
					}

					telemarketingDrillDown.setEmployeeName(employeeName.get(0).getEmployeeName());
					List<TelemartCustomer> telEmpPK = telemarketingService.getTelemartEmpPk(telemartFolwUp.getCustomerId());
					telemarketingDrillDown.setAllocatedDate(telEmpPK.get(0).getAllocatedDate());
					telemarketingDrillDown.setRemarks(telemartFolwUp.getRemarks());
					telemarketingDrillDown.setNextFollowUpDate(telemartFolwUp.getNextFolwUpDate());

					telemarketingDrillDownTableList.add(telemarketingDrillDown);

				}else{
					// customer problem
				}
			}else{
				// customer problem
			}
		}
		setTelemarketingDrillDownTable(telemarketingDrillDownTableList);
		setTeleDrillDownTable(true);
		setTeleEnquiryTable(false);		
	}

	public void drillDownBackSelected(){
		setTeleDrillDownTable(false);
		setTeleEnquiryTable(true);
	}


	public void searchNationalityRecord(){
		List<CountryMasterDesc> lstNationality = new ArrayList<CountryMasterDesc>();

		lstNationality = generalService.getCountryList(session.getLanguageId());
		if(lstNationality != null && lstNationality.size() != 0){
			for (CountryMasterDesc countryMasterDesc : lstNationality) {
				mapNationalityCode.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryCode());
				mapNationalityName.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getCountryName());
			}

			lstAllNationality.addAll(lstNationality);
		}
	}

	public void popUpHistoryDetails(TelemarketingFollowUpDataTable telemarketingEnquiryDataTable){
		List<TelemartFolwUp> telemartFolwupList = telemarketingService.getTelemartFollowupPk(telemarketingEnquiryDataTable.getCusId());
		List<TelemarketingDrillDownTable> telemarketingDrillDownTableList = new ArrayList<TelemarketingDrillDownTable>();

		for(TelemartFolwUp telemartFolwUp : telemartFolwupList) {

			TelemarketingDrillDownTable telemarketingDrillDown = new TelemarketingDrillDownTable();

			if(telemartFolwUp.getCustomerId() != null){
				List<Customer> customerDetails = telemarketingService.getCustomerList(telemartFolwUp.getCustomerId());
				if(customerDetails != null && customerDetails.size() != 0){
					telemarketingDrillDown.setCustomerName(nullCheck(customerDetails.get(0).getFirstName()) + " " + nullCheck(customerDetails.get(0).getMiddleName()) + " " + nullCheck(customerDetails.get(0).getLastName()));
					telemarketingDrillDown.setCusId(customerDetails.get(0).getCustomerId());
					telemarketingDrillDown.setCustomerRefNum(customerDetails.get(0).getCustomerReference());
					telemarketingDrillDown.setMobileNum(customerDetails.get(0).getMobile());
					telemarketingDrillDown.setLastFollowUpDate(telemartFolwUp.getFolwUpDate());
					if(telemartFolwUp.getFolwUpCode() != null){
						String tmFwDesc = telemarketingService.getTelemartFollowUpCodes(telemartFolwUp.getFolwUpCode());
						telemarketingDrillDown.setLastFollowUpCode(tmFwDesc);
					}

					List<Employee> employeeName = telemarketingService.getTelemartEmployeeName(telemartFolwUp.getEmployeeId());
					if(employeeName != null && employeeName.size() != 0){
						telemarketingDrillDown.setEmployeeName(employeeName.get(0).getEmployeeName());
					}

					List<TelemartCustomer> telEmpPK = telemarketingService.getTelemartEmpPk(telemartFolwUp.getCustomerId());
					telemarketingDrillDown.setAllocatedDate(telEmpPK.get(0).getAllocatedDate());
					telemarketingDrillDown.setRemarks(telemartFolwUp.getRemarks());
					telemarketingDrillDown.setNextFollowUpDate(telemartFolwUp.getNextFolwUpDate());

					telemarketingDrillDownTableList.add(telemarketingDrillDown);
				}else{
					// customer problem
				}
			}else{
				// customer problem
			}
		}
		
		setTelemarketingDrillDownTable(telemarketingDrillDownTableList);
		if(telemarketingDrillDownTableList != null && telemarketingDrillDownTableList.size() != 0){
			RequestContext.getCurrentInstance().execute("inquiryDlg.show();");
		}else{
			RequestContext.getCurrentInstance().execute("followupDataTable.show();");
		}
	}
	
	public void checkByCustomerBeneDt(String check){
		setTelemarketingDrillDownTable(null);
		List<TelemarketingEnquiryDataTable> telemarketingDrillDownTable1 = new ArrayList<TelemarketingEnquiryDataTable>();
		if(check != null){
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
			for (TelemarketingEnquiryDataTable matchPlaceOrder : getTempTelemarketingEnquiryDataTable()) {

				// Customer No - CO or Customer Name - CN or Mobile No - MO check
				if(check.equalsIgnoreCase("CO") && getTeleEnquirySearchCO() != null){
					if(matchPlaceOrder.getCustomerRefNum() != null && matchPlaceOrder.getCustomerRefNum().toPlainString().contains(getTeleEnquirySearchCO().toPlainString())){
						if(!duplicateCheck.contains(matchPlaceOrder.getCusId())){
							duplicateCheck.add(matchPlaceOrder.getCusId());
							telemarketingDrillDownTable1.add(matchPlaceOrder);
						}
					}
				}else if(check.equalsIgnoreCase("CN") && getTeleEnquirySearchCN() != null && !getTeleEnquirySearchCN().equalsIgnoreCase("")){
					if(matchPlaceOrder.getCustomerName() != null && matchPlaceOrder.getCustomerName().contains(getTeleEnquirySearchCN().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getCusId())){
							duplicateCheck.add(matchPlaceOrder.getCusId());
							telemarketingDrillDownTable1.add(matchPlaceOrder);
						}
					}
				}else if(check.equalsIgnoreCase("MO") && getTeleEnquirySearchMO() != null && !getTeleEnquirySearchMO().equalsIgnoreCase("")){
					if(matchPlaceOrder.getMobileNum() != null && matchPlaceOrder.getMobileNum().contains(getTeleEnquirySearchMO().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getCusId())){
							duplicateCheck.add(matchPlaceOrder.getCusId());
							telemarketingDrillDownTable1.add(matchPlaceOrder);
						}
					}
				}else{
					// not a check
				}
			}
			
			if(telemarketingDrillDownTable1 != null && telemarketingDrillDownTable1.size() != 0){
				setTelemarketingEnquiryDataTable(telemarketingDrillDownTable1);
			}else{
				setTelemarketingEnquiryDataTable(getTempTelemarketingEnquiryDataTable());
			}
		}else{
			setTelemarketingEnquiryDataTable(getTempTelemarketingEnquiryDataTable());
		}
	}

	// Getters and setters.

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBranchShow() {
		return branchShow;
	}
	public void setBranchShow(Boolean branchShow) {
		this.branchShow = branchShow;
	}

	public Boolean getAllcationShow() {
		return allcationShow;
	}
	public void setAllcationShow(Boolean allcationShow) {
		this.allcationShow = allcationShow;
	}

	public TelemarketingService getTelemarketingService() {
		return telemarketingService;
	}
	public void setTelemarketingService(
			TelemarketingService telemarketingService) {
		this.telemarketingService = telemarketingService;
	}

	public String getAlloCustomerName() {
		return alloCustomerName;
	}
	public void setAlloCustomerName(String alloCustomerName) {
		this.alloCustomerName = alloCustomerName;
	}

	public String getAlloNationality() {
		return alloNationality;
	}
	public void setAlloNationality(String alloNationality) {
		this.alloNationality = alloNationality;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}
	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public Date getAlloLastTransactionDate() {
		return alloLastTransactionDate;
	}
	public void setAlloLastTransactionDate(Date alloLastTransactionDate) {
		this.alloLastTransactionDate = alloLastTransactionDate;
	}

	public SessionStateManage getSession() {
		return session;
	}
	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	/*public List<TelemartEmployee> getTelemartEmployeeList() {
		return telemartEmployeeList;
	}
	public void setTelemartEmployeeList(
			List<TelemartEmployee> telemartEmployeeList) {
		this.telemartEmployeeList = telemartEmployeeList;
	}*/

	public List<TelemartCustomer> getGetTelemartCustomerList() {
		return getTelemartCustomerList;
	}
	public void setGetTelemartCustomerList(
			List<TelemartCustomer> getTelemartCustomerList) {
		this.getTelemartCustomerList = getTelemartCustomerList;
	}

	public List<PopulateDataWithCode> getGetEmployeeList() {
		return getEmployeeList;
	}
	public void setGetEmployeeList(List<PopulateDataWithCode> getEmployeeList) {
		this.getEmployeeList = getEmployeeList;
	}

	public BigDecimal getSelectedStaff() {
		return selectedStaff;
	}
	public void setSelectedStaff(BigDecimal selectedStaff) {
		this.selectedStaff = selectedStaff;
	}

	public BigDecimal getSelectAllocateCus() {
		return selectAllocateCus;
	}
	public void setSelectAllocateCus(BigDecimal selectAllocateCus) {
		this.selectAllocateCus = selectAllocateCus;
	}

	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}

	public List<TelemarketingAllocationDataTable> getTelemarketingDataTable() {
		return telemarketingDataTable;
	}
	public void setTelemarketingDataTable(
			List<TelemarketingAllocationDataTable> telemarketingDataTable) {
		this.telemarketingDataTable = telemarketingDataTable;
	}

	public List<TelemarketingBranchWiseTable> getTelemarketingBranchWiseTable() {
		return telemarketingBranchWiseTable;
	}
	public void setTelemarketingBranchWiseTable(
			List<TelemarketingBranchWiseTable> telemarketingBranchWiseTable) {
		this.telemarketingBranchWiseTable = telemarketingBranchWiseTable;
	}

	public List<TelemarketingBranchWiseTable> getSelectedCustomers() {
		return selectedCustomers;
	}
	public void setSelectedCustomers(
			List<TelemarketingBranchWiseTable> selectedCustomers) {
		this.selectedCustomers = selectedCustomers;
	}

	public String getSameOrDiffNationality() {
		return sameOrDiffNationality;
	}
	public void setSameOrDiffNationality(String sameOrDiffNationality) {
		this.sameOrDiffNationality = sameOrDiffNationality;
	}

	public BigDecimal getTelmartBranchwiseTableId() {
		return telmartBranchwiseTableId;
	}
	public void setTelmartBranchwiseTableId(BigDecimal telmartBranchwiseTableId) {
		this.telmartBranchwiseTableId = telmartBranchwiseTableId;
	}

	public List<TelemarketingFollowUpDataTable> getTelemarketingFollowUpDataTable() {
		return telemarketingFollowUpDataTable;
	}
	public void setTelemarketingFollowUpDataTable(
			List<TelemarketingFollowUpDataTable> telemarketingFollowUpDataTable) {
		this.telemarketingFollowUpDataTable = telemarketingFollowUpDataTable;
	}

	public List<TelemarketingEnquiryDataTable> getTelemarketingEnquiryDataTable() {
		return telemarketingEnquiryDataTable;
	}
	public void setTelemarketingEnquiryDataTable(List<TelemarketingEnquiryDataTable> telemarketingEnquiryDataTable) {
		this.telemarketingEnquiryDataTable = telemarketingEnquiryDataTable;
	}

	public List<Employee> getEmployeeNames() {
		return employeeNames;
	}
	public void setEmployeeNames(List<Employee> employeeNames) {
		this.employeeNames = employeeNames;
	}

	public BigDecimal getSelectEnquiryStaff() {
		return selectEnquiryStaff;
	}
	public void setSelectEnquiryStaff(BigDecimal selectEnquiryStaff) {
		this.selectEnquiryStaff = selectEnquiryStaff;
	}

	public List<TelemarketingFollowUpDataTable> getSelectedFollowUp() {
		return selectedFollowUp;
	}
	public void setSelectedFollowUp(
			List<TelemarketingFollowUpDataTable> selectedFollowUp) {
		this.selectedFollowUp = selectedFollowUp;
	}

	public String getFollowupCode() {
		return followupCode;
	}
	public void setFollowupCode(String followupCode) {
		this.followupCode = followupCode;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}	

	public BigDecimal getFollowupTableId() {
		return followupTableId;
	}
	public void setFollowupTableId(BigDecimal followupTableId) {
		this.followupTableId = followupTableId;
	}

	public Date getNextFollowupDate() {
		return nextFollowupDate;
	}
	public void setNextFollowupDate(Date nextFollowupDate) {
		this.nextFollowupDate = nextFollowupDate;
	}

	public BigDecimal getStaffFilter() {
		return staffFilter;
	}
	public void setStaffFilter(BigDecimal staffFilter) {
		this.staffFilter = staffFilter;
	}

	public Boolean getStaffDisplay() {
		return staffDisplay;
	}
	public void setStaffDisplay(Boolean staffDisplay) {
		this.staffDisplay = staffDisplay;
	}

	public BigDecimal getFollowupCusId() {
		return followupCusId;
	}
	public void setFollowupCusId(BigDecimal followupCusId) {
		this.followupCusId = followupCusId;
	}

	public BigDecimal getFollowupEmpId() {
		return followupEmpId;
	}
	public void setFollowupEmpId(BigDecimal followupEmpId) {
		this.followupEmpId = followupEmpId;
	}

	public BigDecimal getFollowupAppCountryId() {
		return followupAppCountryId;
	}
	public void setFollowupAppCountryId(BigDecimal followupAppCountryId) {
		this.followupAppCountryId = followupAppCountryId;
	}

	public Date getFollowupDate() {
		return followupDate;
	}
	public void setFollowupDate(Date followupDate) {
		this.followupDate = followupDate;
	}

	public Boolean getTeleEnquiryTable() {
		return teleEnquiryTable;
	}
	public void setTeleEnquiryTable(Boolean teleEnquiryTable) {
		this.teleEnquiryTable = teleEnquiryTable;
	}

	public Boolean getTeleDrillDownTable() {
		return teleDrillDownTable;
	}
	public void setTeleDrillDownTable(Boolean teleDrillDownTable) {
		this.teleDrillDownTable = teleDrillDownTable;
	}

	public List<TelemarketingDrillDownTable> getTelemarketingDrillDownTable() {
		return telemarketingDrillDownTable;
	}
	public void setTelemarketingDrillDownTable(List<TelemarketingDrillDownTable> telemarketingDrillDownTable) {
		this.telemarketingDrillDownTable = telemarketingDrillDownTable;
	}
	
	public List<TelemarketingEnquiryDataTable> getTempTelemarketingEnquiryDataTable() {
		return tempTelemarketingEnquiryDataTable;
	}
	public void setTempTelemarketingEnquiryDataTable(List<TelemarketingEnquiryDataTable> tempTelemarketingEnquiryDataTable) {
		this.tempTelemarketingEnquiryDataTable = tempTelemarketingEnquiryDataTable;
	}

	public Date getNextFolwDate() {
		return nextFolwDate;
	}
	public void setNextFolwDate(Date nextFolwDate) {
		this.nextFolwDate = nextFolwDate;
	}

	public Date getNextFolwDateMax() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		cal.add(Calendar.DAY_OF_YEAR, Integer.parseInt(Constants.TeleFollowUpCodeDate));
		Date tomorrow = cal.getTime();
		nextFolwDateMax = tomorrow;

		return nextFolwDateMax;
	}
	public void setNextFolwDateMax(Date nextFolwDateMax) {
		this.nextFolwDateMax = nextFolwDateMax;
	}

	public String getLastFollowUp() {
		return lastFollowUp;
	}
	public void setLastFollowUp(String lastFollowUp) {
		this.lastFollowUp = lastFollowUp;
	}

	public List<VwExTelemartFolwUpCode> getFollowUpCodes() {
		return followUpCodes;
	}
	public void setFollowUpCodes(List<VwExTelemartFolwUpCode> followUpCodes) {
		this.followUpCodes = followUpCodes;
	}

	public Boolean getTeleEnquiryStaffInput() {
		return teleEnquiryStaffInput;
	}
	public void setTeleEnquiryStaffInput(Boolean teleEnquiryStaffInput) {
		this.teleEnquiryStaffInput = teleEnquiryStaffInput;
	}

	public Boolean getTeleEnquiryStaffDD() {
		return teleEnquiryStaffDD;
	}
	public void setTeleEnquiryStaffDD(Boolean teleEnquiryStaffDD) {
		this.teleEnquiryStaffDD = teleEnquiryStaffDD;
	}

	public String getLoginUsername() {
		return loginUsername;
	}
	public void setLoginUsername(String loginUsername) {
		this.loginUsername = loginUsername;
	}

	public BigDecimal getTeleMartCustomerId() {
		return teleMartCustomerId;
	}
	public void setTeleMartCustomerId(BigDecimal teleMartCustomerId) {
		this.teleMartCustomerId = teleMartCustomerId;
	}

	public List<TelemarketingBranchWiseTable> getLstSelectedTeleMarBrWsData() {
		return lstSelectedTeleMarBrWsData;
	}
	public void setLstSelectedTeleMarBrWsData(List<TelemarketingBranchWiseTable> lstSelectedTeleMarBrWsData) {
		this.lstSelectedTeleMarBrWsData = lstSelectedTeleMarBrWsData;
	}

	public BigDecimal getNoofCustomerSelected() {
		return noofCustomerSelected;
	}
	public void setNoofCustomerSelected(BigDecimal noofCustomerSelected) {
		this.noofCustomerSelected = noofCustomerSelected;
	}

	public Map<BigDecimal, String> getLstStaffDetails() {
		return lstStaffDetails;
	}
	public void setLstStaffDetails(Map<BigDecimal, String> lstStaffDetails) {
		this.lstStaffDetails = lstStaffDetails;
	}

	public List<TelemarketingFollowUpDataTable> getLstSelectedTeleFollUpData() {
		return lstSelectedTeleFollUpData;
	}
	public void setLstSelectedTeleFollUpData(List<TelemarketingFollowUpDataTable> lstSelectedTeleFollUpData) {
		this.lstSelectedTeleFollUpData = lstSelectedTeleFollUpData;
	}

	public String getSearchNationality() {
		return searchNationality;
	}
	public void setSearchNationality(String searchNationality) {
		this.searchNationality = searchNationality;
	}

	public List<CountryMasterDesc> getLstAllNationality() {
		return lstAllNationality;
	}
	public void setLstAllNationality(List<CountryMasterDesc> lstAllNationality) {
		this.lstAllNationality = lstAllNationality;
	}

	public List<PopulateDataWithCode> getLstNationalityPopulate() {
		return lstNationalityPopulate;
	}
	public void setLstNationalityPopulate(List<PopulateDataWithCode> lstNationalityPopulate) {
		this.lstNationalityPopulate = lstNationalityPopulate;
	}

	public Map<BigDecimal, String> getMapNationalityCode() {
		return mapNationalityCode;
	}
	public void setMapNationalityCode(Map<BigDecimal, String> mapNationalityCode) {
		this.mapNationalityCode = mapNationalityCode;
	}

	public Map<BigDecimal, String> getMapNationalityName() {
		return mapNationalityName;
	}
	public void setMapNationalityName(Map<BigDecimal, String> mapNationalityName) {
		this.mapNationalityName = mapNationalityName;
	}

	public BigDecimal getNationalitypopId() {
		return nationalitypopId;
	}
	public void setNationalitypopId(BigDecimal nationalitypopId) {
		this.nationalitypopId = nationalitypopId;
	}

	public List<TelemarketingBranchWiseTable> getTempTelemarketingBranchWiseTable() {
		return tempTelemarketingBranchWiseTable;
	}
	public void setTempTelemarketingBranchWiseTable(List<TelemarketingBranchWiseTable> tempTelemarketingBranchWiseTable) {
		this.tempTelemarketingBranchWiseTable = tempTelemarketingBranchWiseTable;
	}

	public BigDecimal getTeleEnquirySearchCO() {
		return teleEnquirySearchCO;
	}
	public void setTeleEnquirySearchCO(BigDecimal teleEnquirySearchCO) {
		this.teleEnquirySearchCO = teleEnquirySearchCO;
	}

	public String getTeleEnquirySearchCN() {
		return teleEnquirySearchCN;
	}
	public void setTeleEnquirySearchCN(String teleEnquirySearchCN) {
		this.teleEnquirySearchCN = teleEnquirySearchCN;
	}
	
	public String getTeleEnquirySearchMO() {
		return teleEnquirySearchMO;
	}
	public void setTeleEnquirySearchMO(String teleEnquirySearchMO) {
		this.teleEnquirySearchMO = teleEnquirySearchMO;
	}


}
