package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Controller("systemAllocationBean")
@Scope("session")
public class SystemAllocationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBranchPageService<T> branchPageService;

	private BigDecimal branchSystemInventoryId;
	private BigDecimal branchId;
	private String ipaddress;
	private String systemNumber;
	private String system;
	private boolean renderSystemDetails;
	private String errorMessage;
	private boolean booRenderEdit;
	private String createdBy;
	private Date createdDate;
	private String remarks;

	private List<CountryBranch> searchBranchList = new ArrayList<CountryBranch>();
	private HashMap<BigDecimal, String> mapIpAddress = new HashMap<BigDecimal, String>();
	private HashMap<BigDecimal, String> mapBranchName = new HashMap<BigDecimal, String>();
	private List<SystemAllocationDataTable> lstSystemDetails = new ArrayList<SystemAllocationDataTable>();
	private List<SystemAllocationDataTable> templstSystemDetails = new ArrayList<SystemAllocationDataTable>();
	private List<BranchSystemInventory> lstBranchSysteminventory = new ArrayList<BranchSystemInventory>(); 
	private SystemAllocationDataTable systemAllocateDT = new SystemAllocationDataTable();

	// page navigation
	public void systemAllocationPageNavigation(){
		try {
			clear();
			populateBranch();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/systemAllocation.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// clearing fields
	public void clear(){
		setRenderSystemDetails(Boolean.FALSE);
		setBranchId(null);
		setIpaddress(null);
		setSystem(null);
		setSystemNumber(null);
		setLstBranchSysteminventory(null);
		setRemarks(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setBooRenderEdit(Boolean.FALSE);
		lstSystemDetails.clear();
		templstSystemDetails.clear();
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
		mapIpAddress.clear();
		mapBranchName.clear();
		List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(sessionStateManage.getCountryId());
		if(listSearchBranch != null && listSearchBranch.size() != 0){
			setSearchBranchList(listSearchBranch);

			for (CountryBranch countryBranch : listSearchBranch) {
				mapIpAddress.put(countryBranch.getCountryBranchId(), countryBranch.getIpAddress());
				mapBranchName.put(countryBranch.getCountryBranchId(), countryBranch.getBranchName());
			}
		}
	}

	// fetching Ip address from selected Branch
	public void fetchIpaddress(){
		if(getBranchId() != null){
			setIpaddress(mapIpAddress.get(getBranchId()));
			setSystem(mapBranchName.get(getBranchId()));
			fetchSystemNumber();
		}
	}

	// check system Ip address already exists in system for that branch
	public HashMap<String, Object> checkIpAddressDuplicate(){

		HashMap<String, Object> mapStatus = new HashMap<String, Object>();
		boolean ipDuplicate = Boolean.FALSE;
		String systemName = null;
		if(getIpaddress() != null && getLstBranchSysteminventory() != null && !isBooRenderEdit()){
			for (BranchSystemInventory branchSystem : getLstBranchSysteminventory()) {
				if(branchSystem.getIpAddress().trim().equalsIgnoreCase(getIpaddress().trim())){
					// ip address already allocated to system
					ipDuplicate = Boolean.TRUE;
					systemName = branchSystem.getSystemName();
					break;
				}
			}
		}

		if(getLstSystemDetails() != null){
			for (SystemAllocationDataTable systemAllocation : getLstSystemDetails()) {
				if(systemAllocation.getIpaddress().trim().equalsIgnoreCase(getIpaddress().trim())){
					// ip address already allocated to system
					ipDuplicate = Boolean.TRUE;
					systemName = systemAllocation.getSystem();
					break;
				}
			}
		}

		if(!ipDuplicate){
			List<BranchSystemInventory> lstIpAddress = branchPageService.fetchSystemAllocationByIp(getIpaddress());

			for (BranchSystemInventory branchSystem : lstIpAddress) {
				if(branchSystem.getIpAddress().trim().equalsIgnoreCase(getIpaddress().trim())){
					// ip address already allocated to system
					ipDuplicate = Boolean.TRUE;
					systemName = branchSystem.getSystemName();
					break;
				}
			}
		}

		mapStatus.put("IpDuplicate", ipDuplicate);
		mapStatus.put("SystemName", systemName);

		return mapStatus;
	}

	// check system number already exists in system for that branch
	public HashMap<String, Object> checkSystemNumberDuplicate(){

		HashMap<String, Object> mapStatus = new HashMap<String, Object>();
		boolean seqNumberDuplicate = Boolean.FALSE;
		String systemName = null;
		if(getSystemNumber() != null && getLstBranchSysteminventory() != null && !isBooRenderEdit()){
			for (BranchSystemInventory branchSystem : getLstBranchSysteminventory()) {
				String[] parts = branchSystem.getSystemName().split("-");
				String part2 = parts[1];
				if(part2 != null){
					if(part2.equalsIgnoreCase(getSystemNumber())){
						// ip address already allocated to system
						seqNumberDuplicate = Boolean.TRUE;
						systemName = branchSystem.getSystemName();
						break;
					}
				}
			}
		}

		if(getLstSystemDetails() != null){
			for (SystemAllocationDataTable systemAllocation : getLstSystemDetails()) {
				if(systemAllocation.getSystemNumber().equalsIgnoreCase(getSystemNumber())){
					// ip address already allocated to system
					seqNumberDuplicate = Boolean.TRUE;
					systemName = systemAllocation.getSystem();
					break;
				}
			}
		}

		mapStatus.put("SeqNumDuplicate", seqNumberDuplicate);
		mapStatus.put("SystemName", systemName);

		return mapStatus;
	}

	// fetch new system number to allocate for that branch
	public void fetchSystemNumber(){
		setLstBranchSysteminventory(null);
		List<BranchSystemInventory> lstBranchDetails = branchPageService.fetchSystemAllocation(getBranchId());
		if(lstBranchDetails != null){
			int seqNo = lstBranchDetails.size();
			BigDecimal seqNumber = new BigDecimal(seqNo).add(BigDecimal.ONE);
			DecimalFormat formatter = new DecimalFormat("00");
			String aFormatted = formatter.format(seqNumber);
			if(aFormatted != null){
				setSystemNumber(aFormatted);
			}

			setLstBranchSysteminventory(lstBranchDetails);
		}
	}

	// add the system number to data table
	public void addSystemDetails(){
		HashMap<String, Object> ipStatus = checkIpAddressDuplicate();
		if(ipStatus != null && (boolean)ipStatus.get("IpDuplicate")){
			setErrorMessage("IP Address already exists for : " + (String)ipStatus.get("SystemName"));
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}else{
			HashMap<String, Object> seqNumStatus = checkSystemNumberDuplicate();

			if(seqNumStatus != null && (boolean)seqNumStatus.get("SeqNumDuplicate")){
				setErrorMessage("System Number already exists for : " + (String)seqNumStatus.get("SystemName"));
				RequestContext.getCurrentInstance().execute("alertmsg.show();");
			}else{
				setRenderSystemDetails(Boolean.TRUE);

				SystemAllocationDataTable systemAllocation = new SystemAllocationDataTable();

				systemAllocation.setBranchSystemInventoryId(getBranchSystemInventoryId());
				systemAllocation.setBranchId(getBranchId());
				systemAllocation.setBranchName(getSystem());
				systemAllocation.setIpaddress(getIpaddress());
				systemAllocation.setSystem(getSystem().concat("-").concat(getSystemNumber()));
				systemAllocation.setSystemNumber(getSystemNumber());
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
		setBranchSystemInventoryId(null);
		setBranchId(null);
		setIpaddress(null);
		setSystem(null);
		setSystemNumber(null);
		setRemarks(null);
		setCreatedBy(null);
		setCreatedDate(null);
	}

	// view the system details to data table based on branch
	public void viewSystemDetails(){
		lstSystemDetails.clear();
		if(getBranchId() != null){
			setRenderSystemDetails(Boolean.TRUE);

			if(getLstBranchSysteminventory() != null){
				for (BranchSystemInventory branchSystem : getLstBranchSysteminventory()) {

					SystemAllocationDataTable systemAllocation = new SystemAllocationDataTable();

					systemAllocation.setBranchSystemInventoryId(branchSystem.getBranchSystemInventoryId());
					systemAllocation.setBranchId(branchSystem.getCountryBranchId());
					systemAllocation.setBranchName(branchSystem.getBranchName());
					systemAllocation.setIpaddress(branchSystem.getIpAddress());
					systemAllocation.setSystem(branchSystem.getSystemName());
					if(branchSystem.getSystemName() != null){
						String[] parts = branchSystem.getSystemName().split("-");
						String part2 = parts[1];
						if(part2 != null){
							systemAllocation.setSystemNumber(part2);
						}
					}
					if(branchSystem.getIsActive() != null && branchSystem.getIsActive().equalsIgnoreCase(Constants.Y)){
						systemAllocation.setStatus(Constants.ACTIVE);
					}else if(branchSystem.getIsActive() != null && branchSystem.getIsActive().equalsIgnoreCase(Constants.D)){
						systemAllocation.setStatus(Constants.IN_ACTIVE);
					}else if(branchSystem.getIsActive() != null && branchSystem.getIsActive().equalsIgnoreCase(Constants.U)){
						systemAllocation.setStatus(Constants.PENDING);
					}

					systemAllocation.setCreatedBy(branchSystem.getCreatedBy());
					systemAllocation.setCreatedDate(branchSystem.getCreatedDate());

					lstSystemDetails.add(systemAllocation);
				}
			}

			if(templstSystemDetails != null && templstSystemDetails.size() != 0){
				lstSystemDetails.addAll(templstSystemDetails);
			}
		}else{
			setErrorMessage("Select the branch to view records");
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// edit the system details
	public void editSystemDetails(SystemAllocationDataTable systemDt){
		setBranchId(systemDt.getBranchId());
		setBranchSystemInventoryId(systemDt.getBranchSystemInventoryId());
		setIpaddress(systemDt.getIpaddress());
		setSystem(systemDt.getBranchName());
		setSystemNumber(systemDt.getSystemNumber());
		setCreatedBy(systemDt.getCreatedBy());
		setCreatedDate(systemDt.getCreatedDate());

		setBooRenderEdit(Boolean.TRUE);
		lstSystemDetails.remove(systemDt);
		templstSystemDetails.remove(systemDt);
	}

	// delete D the system details
	public void deleteSystemDetails(SystemAllocationDataTable systemDt){
		setSystemAllocateDT(null);
		setRemarks(null);
		if(systemDt.getBranchSystemInventoryId() != null){
			if(systemDt.getStatus().equalsIgnoreCase(Constants.ACTIVE)){
				setSystemAllocateDT(systemDt);
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

	// delete in database on yes
	public void deleteSystemDetailsOkButton(){
		if(getSystemAllocateDT() != null){
			lstSystemDetails.remove(getSystemAllocateDT());
			templstSystemDetails.remove(getSystemAllocateDT());
			branchPageService.deleteBranchSystemInventory(getSystemAllocateDT().getBranchSystemInventoryId(), getRemarks());
			clear();
		}
	}

	// submit button action
	public void saveSystemDetails(){
		List<SystemAllocationDataTable> lstSysAllDt = new ArrayList<SystemAllocationDataTable>();
		List<BranchSystemInventory> lstSaveBranchSystemInv = new ArrayList<BranchSystemInventory>();

		try{
			if(getLstSystemDetails() != null){
				for (SystemAllocationDataTable sysAlloc : getLstSystemDetails()) {
					if(sysAlloc.getStatus().equalsIgnoreCase(Constants.PENDING)){
						lstSysAllDt.add(sysAlloc);
					}
				}
			}

			if(lstSysAllDt != null && lstSysAllDt.size() != 0){
				for (SystemAllocationDataTable systemAllocationDataTable : lstSysAllDt) {
					BranchSystemInventory branchSystemInv = new BranchSystemInventory();

					branchSystemInv.setBranchSystemInventoryId(systemAllocationDataTable.getBranchSystemInventoryId());
					branchSystemInv.setBranchName(systemAllocationDataTable.getBranchName());
					branchSystemInv.setCountryBranchId(systemAllocationDataTable.getBranchId());
					branchSystemInv.setIpAddress(systemAllocationDataTable.getIpaddress());
					branchSystemInv.setIsActive(Constants.Yes);
					branchSystemInv.setSystemName(systemAllocationDataTable.getSystem());
					branchSystemInv.setRemarks(null);
					if(systemAllocationDataTable.getBranchSystemInventoryId() != null){
						branchSystemInv.setCreatedBy(systemAllocationDataTable.getCreatedBy());
						branchSystemInv.setCreatedDate(systemAllocationDataTable.getCreatedDate());
						branchSystemInv.setUpdatedBy(sessionStateManage.getUserName());
						branchSystemInv.setUpdatedDate(new Date());
					}else{
						branchSystemInv.setCreatedBy(sessionStateManage.getUserName());
						branchSystemInv.setCreatedDate(new Date());
					}

					lstSaveBranchSystemInv.add(branchSystemInv);
				}

				boolean saveStatus = branchPageService.saveSystemInventory(lstSaveBranchSystemInv);
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
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("alertmsg.show();");
		}
	}

	// getters and setters
	public BigDecimal getBranchSystemInventoryId() {
		return branchSystemInventoryId;
	}
	public void setBranchSystemInventoryId(BigDecimal branchSystemInventoryId) {
		this.branchSystemInventoryId = branchSystemInventoryId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}
	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public String getIpaddress() {
		return ipaddress;
	}
	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	public String getSystemNumber() {
		return systemNumber;
	}
	public void setSystemNumber(String systemNumber) {
		this.systemNumber = systemNumber;
	}

	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
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

	public List<SystemAllocationDataTable> getTemplstSystemDetails() {
		return templstSystemDetails;
	}
	public void setTemplstSystemDetails(List<SystemAllocationDataTable> templstSystemDetails) {
		this.templstSystemDetails = templstSystemDetails;
	}

	public List<BranchSystemInventory> getLstBranchSysteminventory() {
		return lstBranchSysteminventory;
	}
	public void setLstBranchSysteminventory(List<BranchSystemInventory> lstBranchSysteminventory) {
		this.lstBranchSysteminventory = lstBranchSysteminventory;
	}

	public SystemAllocationDataTable getSystemAllocateDT() {
		return systemAllocateDT;
	}
	public void setSystemAllocateDT(SystemAllocationDataTable systemAllocateDT) {
		this.systemAllocateDT = systemAllocateDT;
	}

}
