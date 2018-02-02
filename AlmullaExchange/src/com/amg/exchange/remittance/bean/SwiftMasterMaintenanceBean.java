package com.amg.exchange.remittance.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.SwiftMasterCountryView;
import com.amg.exchange.remittance.service.ISwiftMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("swiftMasterMaintenanceBean")
@Scope("session")
public class SwiftMasterMaintenanceBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(SwiftMasterMaintenanceBean.class);

	private BigDecimal swiftCountryId;
	private String swiftCountryName;
	private String swiftCountryCode;

	private BigDecimal bankId;
	private String bankCode;
	private BigDecimal branchId;
	private BigDecimal swiftId;

	private String swiftBank;
	private String swiftLocation;
	private BigDecimal branchCode;
	private String branchName;
	private String bankName;
	private String cityName;
	private String region;
	private String address;
	private String swiftBIC;
	private String chipsUID;
	private String acronymID;
	private String fedwireID;
	private String abaNumber;
	private String isActive;
	private String firstAddress;
	private String secondAddress;
	private String thirdAddress;
	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String errorMessage;
	private Boolean booCheckDelete=false;
	private Boolean booCheckUpdate;

	//rendering Components
	private Boolean renderDataTablePanel = false;
	private Boolean renderSaveExitButtonPanel = false;
	private String dynamicLabelForActivateDeactivate;
	private Boolean checkSave;
	private Boolean renderEditButton = false;

	private Boolean booApprove = false;
	private Boolean booSubmit;
	private Boolean booClear;
	private Boolean booApproveButton = false;
	private Boolean booSwiftBank = true;
	private Boolean booSwiftCountry = true;
	private Boolean booSwiftBranch = true;

	private Boolean booSwiftBankInput = false;
	private Boolean booSwiftBranchInput = false;
	private Boolean booSwiftCountryInput = false;
	private Boolean booSavePanel = true;
	private Boolean booApprovePanel = false;
	private boolean booDisableSubmit = false;
	private Boolean disableClear;
	private Boolean disableEdit;
	private Boolean booActivate=false;
	 

	

	private Map<BigDecimal, String> swiftCountryMap = new HashMap<BigDecimal, String>();
	private Map<String, String> swiftCountryMap1 = new HashMap<String, String>();
	private Map<BigDecimal, String> swiftCountryMapCode = new HashMap<BigDecimal, String>();
	//private List<CountryMasterDesc> allCountryList = new ArrayList<CountryMasterDesc>();
	private List<SwiftMasterMaintenanceDataTableBean> swiftMasterDataTableList = new CopyOnWriteArrayList<>();
	private List<BankBranch> branchList = new ArrayList<BankBranch>();
	private List<SwiftMaster> poplateSwiftList = new ArrayList<SwiftMaster>();
	private List<SwiftMaster> viewSwiftList = new ArrayList<SwiftMaster>();
	private List<SwiftMasterMaintenanceDataTableBean> viewDataTableList = new CopyOnWriteArrayList<>();

	SessionStateManage session = new SessionStateManage();
	
	private List<SwiftMasterCountryView> allCountryList = new ArrayList<SwiftMasterCountryView>();

	@SuppressWarnings("rawtypes")
	@Autowired
	IGeneralService generalService;

	@Autowired
	ISwiftMasterService iSwiftMasterService;

	public void hideSubmitButton() {
		setBooSubmit(true);
	}

	public List<SwiftMasterMaintenanceDataTableBean> getSwiftMasterDataTableList() {
		return swiftMasterDataTableList;
	}

	public void setSwiftMasterDataTableList(
			List<SwiftMasterMaintenanceDataTableBean> swiftMasterDataTableList) {
		this.swiftMasterDataTableList = swiftMasterDataTableList;
	}
	
	
	/*public void   populateCountryList(){
		swiftCountryMapCode.clear();
			log.info( "=====================countrylist");
			allCountryList.clear();
		List<CountryMasterDesc>	 list=generalService.getCountryList(session.getLanguageId()) ;
		setAllCountryList(list );
		for (CountryMasterDesc countryMasterDesc : list) {
			swiftCountryMap.put(countryMasterDesc.getFsCountryMaster()
					.getCountryId(), countryMasterDesc.getCountryName());
			swiftCountryMapCode.put( countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryCode());
	}
	}*/
	@SuppressWarnings("unchecked")
	public List<SwiftMasterCountryView> getAllCountryList() {
		SwiftMasterCountryView countryView=null;
		/*swiftCountryMapCode.clear();
		allCountryList = generalService.getCountryList(session.getLanguageId());
		for (CountryMasterDesc countryMasterDesc : allCountryList) {
			swiftCountryMap.put(countryMasterDesc.getFsCountryMaster()
					.getCountryId(), countryMasterDesc.getCountryName());
			swiftCountryMapCode.put( countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getFsCountryMaster().getCountryCode());
		}*/
		
		allCountryList = iSwiftMasterService.getAllSwiftCountry(getSwiftBank());
		if(allCountryList!=null){
			for (SwiftMasterCountryView countryView1 : allCountryList) {
				countryView = new SwiftMasterCountryView();
				countryView.setCountryCode(countryView1.getCountryCode());
				countryView.setCountryFullName(countryView1.getCountryFullName());
				//swiftCountryMap1.put(countryView1.getCountryCode(),countryView1.getCountryCode());
				//swiftCountryMap1.put(countryView1.getCountryFullName(), countryView1.getCountryFullName());
			}
			allCountryList.add(countryView);
		}
		return allCountryList;
	}

	public void setAllCountryList(List<SwiftMasterCountryView> allCountryList) {
		this.allCountryList = allCountryList;
	}

	@SuppressWarnings("unchecked")
	public List<BankMaster> getBankListFromDB() {
		return generalService.getAllBankListFromBankMaster();
	}

	@SuppressWarnings("unchecked")
	public void populateBranch() {
		branchList.clear();
		 log.info( "@@@@@@@@@@@"+generalService.getBankBranchList(getSwiftCountryId(),
				getBankId()).size()+"");
		 log.info( "Countrid==========="+getSwiftCountryId());
		 log.info( "BankId==========="+getBankId());
		setBranchList(generalService.getBankBranchList(getSwiftCountryId(),
				getBankId()));

	}

	public BigDecimal getSwiftCountryId() {
		return swiftCountryId;
	}

	public void setSwiftCountryId(BigDecimal swiftCountryId) {
		this.swiftCountryId = swiftCountryId;
	}

	public String getSwiftCountryName() {
		return swiftCountryName;
	}

	public void setSwiftCountryName(String swiftCountryName) {
		this.swiftCountryName = swiftCountryName;
	}

	public String getSwiftBank() {
		return swiftBank;
	}

	public void setSwiftBank(String swiftBank) {
		this.swiftBank = swiftBank;
	}

	public String getSwiftLocation() {
		return swiftLocation;
	}

	public void setSwiftLocation(String swiftLocation) {
		this.swiftLocation = swiftLocation;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSwiftBIC() {
		return swiftBIC;
	}

	public void setSwiftBIC(String swiftBIC) {
		this.swiftBIC = swiftBIC;
	}

	public String getChipsUID() {
		return chipsUID;
	}

	public void setChipsUID(String chipsUID) {
		this.chipsUID = chipsUID;
	}

	public String getAcronymID() {
		return acronymID;
	}

	public void setAcronymID(String acronymID) {
		this.acronymID = acronymID;
	}

	public String getFedwireID() {
		return fedwireID;
	}

	public void setFedwireID(String fedwireID) {
		this.fedwireID = fedwireID;
	}

	public String getAbaNumber() {
		return abaNumber;
	}

	public void setAbaNumber(String abaNumber) {
		this.abaNumber = abaNumber;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Boolean getRenderDataTablePanel() {
		return renderDataTablePanel;
	}

	public void setRenderDataTablePanel(Boolean renderDataTablePanel) {
		this.renderDataTablePanel = renderDataTablePanel;
	}

	public Boolean getRenderSaveExitButtonPanel() {
		return renderSaveExitButtonPanel;
	}

	public void setRenderSaveExitButtonPanel(Boolean renderSaveExitButtonPanel) {
		this.renderSaveExitButtonPanel = renderSaveExitButtonPanel;
	}

	public String getFirstAddress() {
		return firstAddress;
	}

	public void setFirstAddress(String firstAddress) {
		this.firstAddress = firstAddress;
	}

	public String getSecondAddress() {
		return secondAddress;
	}

	public void setSecondAddress(String secondAddress) {
		this.secondAddress = secondAddress;
	}

	public String getThirdAddress() {
		return thirdAddress;
	}

	public void setThirdAddress(String thirdAddress) {
		this.thirdAddress = thirdAddress;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public List<BankBranch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<BankBranch> branchList) {
		this.branchList = branchList;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getSwiftId() {
		return swiftId;
	}

	public void setSwiftId(BigDecimal swiftId) {
		this.swiftId = swiftId;
	}

	public List<SwiftMasterMaintenanceDataTableBean> getViewDataTableList() {
		return viewDataTableList;
	}

	public void setViewDataTableList(
			List<SwiftMasterMaintenanceDataTableBean> viewDataTableList) {
		this.viewDataTableList = viewDataTableList;
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

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getCheckSave() {
		return checkSave;
	}

	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}

	public Boolean getBooApprove() {
		return booApprove;
	}

	public void setBooApprove(Boolean booApprove) {
		this.booApprove = booApprove;
	}

	public Boolean getBooSubmit() {
		return booSubmit;
	}

	public void setBooSubmit(Boolean booSubmit) {
		this.booSubmit = booSubmit;
	}

	public Boolean getBooClear() {
		return booClear;
	}

	public void setBooClear(Boolean booClear) {
		this.booClear = booClear;
	}

	public Boolean getBooApproveButton() {
		return booApproveButton;
	}

	public void setBooApproveButton(Boolean booApproveButton) {
		this.booApproveButton = booApproveButton;
	}

	public Boolean getBooSwiftBank() {
		return booSwiftBank;
	}

	public void setBooSwiftBank(Boolean booSwiftBank) {
		this.booSwiftBank = booSwiftBank;
	}

	public Boolean getBooSwiftCountry() {
		return booSwiftCountry;
	}

	public void setBooSwiftCountry(Boolean booSwiftCountry) {
		this.booSwiftCountry = booSwiftCountry;
	}

	public Boolean getBooSwiftBranch() {
		return booSwiftBranch;
	}

	public void setBooSwiftBranch(Boolean booSwiftBranch) {
		this.booSwiftBranch = booSwiftBranch;
	}

	public Boolean getBooSwiftBankInput() {
		return booSwiftBankInput;
	}

	public void setBooSwiftBankInput(Boolean booSwiftBankInput) {
		this.booSwiftBankInput = booSwiftBankInput;
	}

	public Boolean getBooSwiftBranchInput() {
		return booSwiftBranchInput;
	}

	public void setBooSwiftBranchInput(Boolean booSwiftBranchInput) {
		this.booSwiftBranchInput = booSwiftBranchInput;
	}

	public Boolean getBooSwiftCountryInput() {
		return booSwiftCountryInput;
	}

	public void setBooSwiftCountryInput(Boolean booSwiftCountryInput) {
		this.booSwiftCountryInput = booSwiftCountryInput;
	}

	public Boolean getBooSavePanel() {
		return booSavePanel;
	}

	public boolean isBooDisableSubmit() {
		return booDisableSubmit;
	}

	public void setBooDisableSubmit(boolean booDisableSubmit) {
		this.booDisableSubmit = booDisableSubmit;
	}

	public void setBooSavePanel(Boolean booSavePanel) {
		this.booSavePanel = booSavePanel;
	}

	public Boolean getBooApprovePanel() {
		return booApprovePanel;
	}

	public void setBooApprovePanel(Boolean booApprovePanel) {
		this.booApprovePanel = booApprovePanel;
	}
	public Boolean getDisableClear() {
		return disableClear;
	}

	public void setDisableClear(Boolean disableClear) {
		this.disableClear = disableClear;
	}

	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
	}
	public Boolean getBooActivate() {
		return booActivate;
	}

	public void setBooActivate(Boolean booActivate) {
		this.booActivate = booActivate;
	}
	public Boolean getBooCheckDelete() {
		return booCheckDelete;
	}

	public void setBooCheckDelete(Boolean booCheckDelete) {
		this.booCheckDelete = booCheckDelete;
	}

	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}

	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}


	// TO ADD THE RECORDS TO THE DataTable
	public void addFieldsToDataTable() {
		log.info( "Entered into addFieldsToDataTable() Method");
		try{
		setBooDisableSubmit(false);
		
		System.out.println("########################"+ swiftBank);
		System.out.println("########################"+ swiftBank);

		System.out.println("########################"+ swiftBank);

		

		if (viewDataTableList.size() == 0) {

			SwiftMasterMaintenanceDataTableBean swiftDatatableObj = new SwiftMasterMaintenanceDataTableBean();

			swiftDatatableObj.setSwiftId(getSwiftId());
			swiftDatatableObj.setSwiftCountryId(getSwiftCountryId());
			swiftDatatableObj.setSwiftCountryName(swiftCountryMap.get(getSwiftCountryId()));
			List<CountryMaster>  listAlpha=generalService.getCountryAlphaList(getSwiftCountryId());
			
			String swiftBankCode ="";
			String swiftBankName =""; 
			int temp = getSwiftBank().indexOf("-");
			if(getSwiftBank()!=null && temp !=-1) {
			//System.out.println(str.substring(0,str.lastIndexOf("-")).trim());
			
			 swiftBankCode = getSwiftBank().substring(0,getSwiftBank().lastIndexOf("-")).trim();
			
			 swiftBankName = getSwiftBank().substring(getSwiftBank().indexOf("-")+1,getSwiftBank().length()).trim();
			
			}
			swiftDatatableObj.setSwiftCountryCode(getSwiftCountryCode());
			swiftDatatableObj.setSwiftBankName(getSwiftBank());
			
			swiftDatatableObj.setSwiftBankCode(getSwiftBank());;
			swiftDatatableObj.setSwiftLocation(getSwiftLocation());
			
			swiftDatatableObj.setSwiftBranchCode(getBranchName());
			swiftDatatableObj.setCityName(getCityName());
			swiftDatatableObj.setRegion(getRegion());
			swiftDatatableObj.setBankName(getBankName());
			swiftDatatableObj.setFirstAddress(getFirstAddress());
			swiftDatatableObj.setSecondAddress(getSecondAddress());
			swiftDatatableObj.setThirdAddress(getThirdAddress());
			
			//if(getSwiftBIC()==null||getSwiftBIC().length()==0){
			setSwiftBIC(getSwiftBank()+getSwiftCountryCode()+getSwiftLocation()+getBranchName());
			/*}else{
				swiftDatatableObj.setSwiftBIC(getSwiftBIC());
			}*/
			swiftDatatableObj.setSwiftBIC(getSwiftBank()+getSwiftCountryCode()+getSwiftLocation()+getBranchName());
			swiftDatatableObj.setChipsUID(getChipsUID());
			swiftDatatableObj.setAcronymID(getAcronymID());
			swiftDatatableObj.setFedwireID(getFedwireID());
			swiftDatatableObj.setAbaNumber(getAbaNumber());
	 
			
 
			if (getSwiftId() != null) {
				swiftDatatableObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				swiftDatatableObj.setRenderEditButton(getRenderEditButton());
				swiftDatatableObj.setIsActive(getIsActive());
				swiftDatatableObj.setRemarks(getRemarks());
				swiftDatatableObj.setCheckSave(getCheckSave());
				swiftDatatableObj.setCreatedBy(getCreatedBy());
				swiftDatatableObj.setCreatedDate(getCreatedDate());
				swiftDatatableObj.setIsActive(getIsActive());
				swiftDatatableObj.setCreatedBy(session.getUserName());
				swiftDatatableObj.setCreatedDate(new Date());
				swiftDatatableObj.setBooCheckUpdate(getBooCheckUpdate());
			} else {
				swiftDatatableObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				swiftDatatableObj.setRenderEditButton(true);
				swiftDatatableObj.setIsActive(Constants.U);
				swiftDatatableObj.setCheckSave(true);
				swiftDatatableObj.setBooCheckUpdate(true);
				swiftDatatableObj.setCreatedBy(session.getUserName());
				swiftDatatableObj.setCreatedDate(new Date());
				swiftDatatableObj.setBooCheckDelete(false);
			}
			if (swiftMasterDataTableList.size() > 0) {
				for (SwiftMasterMaintenanceDataTableBean swift : swiftMasterDataTableList) {
					if (swift.getSwiftBankId().equals(getBankId())
							&& swift.getSwiftCountryId().equals(
									getSwiftCountryId())
							&& swift.getSwiftLocation().equalsIgnoreCase(
									getSwiftLocation())
							&& swift.getBranchId().equals(getBranchId())) {

						clearFields();
						swiftDatatableObj = null;
						RequestContext context = RequestContext
								.getCurrentInstance();
						context.execute("dataexist.show();");
					}

				}
				if (swiftDatatableObj != null) {
					swiftMasterDataTableList.add(swiftDatatableObj);
				}
			} else {
				swiftMasterDataTableList.add(swiftDatatableObj);

			}

		}

		if (swiftMasterDataTableList.size() > 0) {
			if (viewDataTableList.size() > 0) {
				for (SwiftMasterMaintenanceDataTableBean swiftDt : swiftMasterDataTableList) {
					for (SwiftMasterMaintenanceDataTableBean swiftListDt : viewDataTableList) {
						if ((swiftDt.getSwiftLocation()
								.equalsIgnoreCase(swiftListDt
										.getSwiftLocation()))
								&& (swiftDt.getSwiftCountryName()
										.equalsIgnoreCase(swiftListDt
												.getSwiftCountryName()))
								&& (swiftDt.getBranchName()
										.equalsIgnoreCase(swiftListDt
												.getBranchName()))
								&& (swiftDt.getSwiftBankId().equals(swiftListDt
										.getSwiftBankId()))) {
							viewDataTableList.remove(swiftListDt);
						}
					}
				}
			}
			swiftMasterDataTableList.addAll(viewDataTableList);
		}

		else {
			swiftMasterDataTableList.addAll(viewDataTableList);

		}

		clearAllFields();
		setRenderSaveExitButtonPanel(true);
		setRenderDataTablePanel(true);
		setDisableClear(false);
		setDisableEdit(false);
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: addFieldsToDataTable"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	public void activateRecord(){
		if(swiftMasterDataTableList.size()>0){
			for(SwiftMasterMaintenanceDataTableBean swiftMasterObj:swiftMasterDataTableList){
				if(swiftMasterObj.getBooActivate()){
				deleteSwift(swiftMasterObj);
				swiftMasterDataTableList.clear();
				view();
				swiftMasterDataTableList.addAll(viewDataTableList);
				
				}
			}
			
			
		}
		
		
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToSwiftMasterMaintenancePage() throws IOException {
		log.info("Entered into navigateToSwiftMasterMaintenancePage() method ");
		setRenderSaveExitButtonPanel(false);
		setRenderDataTablePanel(false);
		setBooApprove(false);
		setBooSwiftBank(true);
		setBooSwiftBranch(true);
		setBooSwiftCountry(true);
		allCountryList.clear();
		setBooSwiftBankInput(false);
		setBooSwiftBranchInput(false);
		setBooSwiftCountryInput(false);
		setBooApprovePanel(false);
		setBooSavePanel(true);
		setDisableClear(false);
		setDisableEdit(false);

		swiftMasterDataTableList.clear();
		clearFields();
		setBankId( null);
		setSwiftCountryId( null);
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "SwiftMasterMaintenance.xhtml");
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../remittance/SwiftMasterMaintenance.xhtml");
	}

	// This Method Is Used To Edit The Record From DataTable
	public void editRecord(SwiftMasterMaintenanceDataTableBean swiftDataTableObj) {
		log.info("Entered into editRecord() method");
		setSwiftId(swiftDataTableObj.getSwiftId());
		setBankId(swiftDataTableObj.getSwiftBankId());
		setSwiftCountryId(swiftDataTableObj.getSwiftCountryId());
		populateBranch();
 
		setBranchId(swiftDataTableObj.getBranchId());
		log.info("SwiftCountryId========" +swiftDataTableObj.getSwiftCountryId());
		log.info("BranchId========" +swiftDataTableObj.getBranchId());
		setSwiftBank(swiftDataTableObj.getSwiftBankName());
		setSwiftCountryName(swiftDataTableObj.getSwiftCountryName());
		setSwiftCountryCode(swiftDataTableObj.getSwiftCountryCode());

		setSwiftLocation(swiftDataTableObj.getSwiftLocation());
		setBranchName(swiftDataTableObj.getSwiftBranchCode());
		
		setCityName(swiftDataTableObj.getCityName());
		setRegion(swiftDataTableObj.getRegion());
		//setDynamicLabelForActivateDeactivate(swiftDataTableObj.getDynamicLabelForActivateDeactivate());
		setRenderEditButton(swiftDataTableObj.getRenderEditButton());
		
		setFirstAddress(swiftDataTableObj.getFirstAddress());
		setSecondAddress(swiftDataTableObj.getSecondAddress());
		setThirdAddress(swiftDataTableObj.getThirdAddress());
		
		setBankName(swiftDataTableObj.getBankName());
		setSwiftBIC(swiftDataTableObj.getSwiftBIC());
		setChipsUID(swiftDataTableObj.getChipsUID());
		setAcronymID(swiftDataTableObj.getAcronymID());
		setFedwireID(swiftDataTableObj.getFedwireID());
		setAbaNumber(swiftDataTableObj.getAbaNumber());
		setCreatedBy(swiftDataTableObj.getCreatedBy() );
		setCreatedDate( swiftDataTableObj.getCreatedDate());
		 setDynamicLabelForActivateDeactivate( Constants.PENDING_FOR_APPROVE);
		setDisableClear(true);
		setIsActive(Constants.U);
		setDisableEdit(true);
		setBooCheckUpdate(true);
		setCheckSave(swiftDataTableObj.getCheckSave());

		swiftMasterDataTableList.remove(swiftDataTableObj);
		setBooDisableSubmit(true);

	}

	// This Method Is Used To Remove The Record From DataTable
	public void removeRecord(SwiftMasterMaintenanceDataTableBean swiftDataTableObj) {
		log.info("Entered into removeRecord() method");
		if (swiftDataTableObj.getSwiftId() == null) {

		  if (swiftDataTableObj.getCheckSave().equals(true)) {

			swiftMasterDataTableList.remove(swiftDataTableObj);
			if (swiftMasterDataTableList.size() <= 0) {
				 setRenderSaveExitButtonPanel(false);
				 setRenderDataTablePanel(false);
			}
		  }

		} else {
			if (swiftDataTableObj.getIsActive().equalsIgnoreCase(Constants.U)) {

			}

			else {
				deleteSwift(swiftDataTableObj);
				swiftMasterDataTableList.clear();
				view();
				swiftMasterDataTableList.addAll(viewDataTableList);
			}
		}
		swiftMasterDataTableList.remove(swiftDataTableObj);
		if (swiftMasterDataTableList.size() == 0) {
			setRenderSaveExitButtonPanel(false);
			setRenderDataTablePanel(false);
		}
	}
 

	public void saveOrUpdateSwift() {
		log.info("================Entered into saveOrUpdateSwift() method=======");
	try{
		if (swiftMasterDataTableList.isEmpty()) {
			log.info("============ No Records Availble with Datatable =========");
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {
			SwiftMaster swiftMaster = null;
			for (SwiftMasterMaintenanceDataTableBean swiftDT : swiftMasterDataTableList) {
				swiftMaster = new SwiftMaster();
				swiftMaster.setSwiftId(swiftDT.getSwiftId());

				//swiftMaster.setSwiftBankId(swiftDT.getSwiftBankId());

				swiftMaster.setFirstAddress(swiftDT.getFirstAddress());
				swiftMaster.setSecondAddress(swiftDT.getSecondAddress());
				swiftMaster.setThirdAddress(swiftDT.getThirdAddress());

				swiftMaster.setAbaId(swiftDT.getAbaNumber());
				swiftMaster.setAcronymId(swiftDT.getAcronymID());

				swiftMaster.setChipsUID(swiftDT.getChipsUID());
				swiftMaster.setFedwireId(swiftDT.getFedwireID());
				
				swiftMaster.setSwiftBIC(swiftDT.getSwiftBankCode()+swiftDT.getSwiftCountryCode()+swiftDT.getSwiftLocation()+swiftDT.getSwiftBranchCode());
				
				/*if(getSwiftBIC()==null||getSwiftBIC().length()==0){
					swiftMaster.setSwiftBIC(swiftDT.getSwiftBankCode()+swiftDT.getSwiftCountryCode()+swiftDT.getSwiftLocation()+swiftDT.getSwiftBranchCode());
				}else{
					swiftMaster.setSwiftBIC(swiftDT.getSwiftBIC());
				}*/
				
			 
				//BankMaster bankMaster = new BankMaster();
				//bankMaster.setBankId(swiftDT.getBankId());
				//swiftMaster.setBankId(bankMaster);
				
				swiftMaster.setBankCode(swiftDT.getSwiftBankCode());
				
				//CountryMaster countryMaster = new CountryMaster();
				//countryMaster.setCountryId(swiftDT.getSwiftCountryId());
				//swiftMaster.setSwiftCountryId(countryMaster);
				//swiftMaster.setSwiftCountryCode( generalService.getCountryCode(swiftDT.getSwiftCountryId()));
				swiftMaster.setSwiftCountryCode(swiftDT.getSwiftCountryCode());
				//BankBranch bankBranch = new BankBranch();
				//bankBranch.setBankBranchId(swiftDT.getBranchId());
				//swiftMaster.setSwiftBranchId(bankBranch);
				swiftMaster.setSwiftBranchCode(swiftDT.getSwiftBranchCode() );

				swiftMaster.setSwiftLocation(swiftDT.getSwiftLocation());
				swiftMaster.setBankName(swiftDT.getBankName());
				swiftMaster.setCityName(swiftDT.getCityName());
				swiftMaster.setRegion(swiftDT.getRegion());
				if(swiftDT.getSwiftId()!=null){
					swiftMaster.setCreatedBy(swiftDT.getCreatedBy());
					swiftMaster.setCreatedDate(swiftDT.getCreatedDate());
					swiftMaster.setModifiedBy(session.getUserName());
					swiftMaster.setModifiedDate(new Date());
					swiftMaster.setIsActive(Constants.U);
				}
				else{
				swiftMaster.setCreatedBy(session.getUserName());
				swiftMaster.setCreatedDate(new Date());
				swiftMaster.setModifiedBy(swiftDT.getModifiedBy());
				swiftMaster.setModifiedDate( swiftDT.getModifiedDate());
				swiftMaster.setIsActive(Constants.U);
				}
				
				
				swiftMaster.setApprovedBy(swiftDT.getApprovedBy());
				swiftMaster.setApprovedDate(swiftDT.getApprovedDate());
				swiftMaster.setRemarks(swiftDT.getRemarks());
			
				
				//if(swiftDT.getBooCheckUpdate()){
				iSwiftMasterService.saveOrUpdate(swiftMaster);
				//}
				 
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			log.info("SaveOrUpdate() method executed  successfully");
		}
	}catch(NullPointerException  e){
		setErrorMessage("Method Name: saveOrUpdateSwift "+e.getMessage());
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch(Exception e){
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}

	public void deleteSwift(SwiftMasterMaintenanceDataTableBean swiftMasterDtObj) {
		log.info("Entered into deleteSwift() method ");
		try {
			SwiftMaster swiftMaster = new SwiftMaster();

			swiftMaster.setSwiftId(swiftMasterDtObj.getSwiftId());

			swiftMaster.setSwiftBankId(swiftMasterDtObj.getSwiftBankId());

			swiftMaster.setFirstAddress(swiftMasterDtObj.getFirstAddress());
			swiftMaster.setSecondAddress(swiftMasterDtObj.getSecondAddress());
			swiftMaster.setThirdAddress(swiftMasterDtObj.getThirdAddress());

			swiftMaster.setAbaId(swiftMasterDtObj.getAbaNumber());
			swiftMaster.setAcronymId(swiftMasterDtObj.getAcronymID());

			swiftMaster.setChipsUID(swiftMasterDtObj.getChipsUID());
			swiftMaster.setFedwireId(swiftMasterDtObj.getFedwireID());
			swiftMaster.setSwiftBIC(swiftMasterDtObj.getSwiftBIC());

			//BankMaster bankMaster = new BankMaster();
			//bankMaster.setBankId(swiftMasterDtObj.getBankId());
			//swiftMaster.setBankId(bankMaster);
			swiftMaster.setBankCode(swiftMasterDtObj.getSwiftBankCode()  );
			
			//CountryMaster countryMaster = new CountryMaster();
			//countryMaster.setCountryId(swiftMasterDtObj.getSwiftCountryId());
			//swiftMaster.setSwiftCountryId(countryMaster);
			swiftMaster.setSwiftCountryCode( swiftMasterDtObj.getSwiftCountryCode() );
			
			//BankBranch bankBranch = new BankBranch();
			//bankBranch.setBankBranchId(swiftMasterDtObj.getBranchId());
			//swiftMaster.setSwiftBranchId(bankBranch);
			swiftMaster.setSwiftBranchCode( swiftMasterDtObj.getSwiftBranchCode());

			swiftMaster.setSwiftLocation(swiftMasterDtObj.getSwiftLocation());
			swiftMaster.setBankName(swiftMasterDtObj.getBankName());
			swiftMaster.setCityName(swiftMasterDtObj.getCityName());
			swiftMaster.setRegion(swiftMasterDtObj.getRegion());

			swiftMaster.setCreatedBy(swiftMasterDtObj.getCreatedBy());
			swiftMaster.setCreatedDate(swiftMasterDtObj.getCreatedDate());

			swiftMaster.setModifiedBy(session.getUserName());
			swiftMaster.setModifiedDate(new Date());

			if (swiftMasterDtObj.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.ACTIVATE)) {
				swiftMaster.setIsActive(Constants.U);

			} else if (swiftMasterDtObj.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.DEACTIVATE)) {
				swiftMaster.setIsActive(Constants.D);
				swiftMaster.setRemarks(swiftMasterDtObj.getRemarks());
				 

			}

 			iSwiftMasterService.saveOrUpdate(swiftMaster);
			log.info(" deleteSwift()  Executed Successfully");
		} catch(NullPointerException  e){
			setErrorMessage("Method Name: deleteSwift "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	
	public void clearExisting(){
		setBranchId(null);
	}

	public void populateAllSwiftFields() {
		log.info("===========Entered into populateAllSwiftFields() method ==============");
	try{
		poplateSwiftList.clear();
		setSwiftId(null);
		setSwiftLocation(null);
		setBankName(null);
		setCityName(null);
		setRegion(null);
		setFirstAddress(null);
		setSecondAddress(null);
		setThirdAddress(null);
		setSwiftBIC(null);
		setChipsUID(null);

		setAcronymID(null);
		setFedwireID(null);
		setAbaNumber(null);
		setIsActive(null);
		List<CountryMaster>    alphaCodeList =generalService.getCountryAlphaList(getSwiftCountryId());
		log.info("=====Country Alpha2Code===="+alphaCodeList.get( 0).getCountryAlpha2Code());
		log.info("========BankCode========="+generalService.getBankCode(getBankId()));
		log.info("========Branch Code======"+generalService.getBankBranchCode(getBranchId() ));
		poplateSwiftList = iSwiftMasterService.getSwiftRecord(alphaCodeList.get(0).getCountryAlpha2Code(), generalService.getBankCode(getBankId()), generalService.getBankBranchCode(getBranchId() ).toString());
		
		if (poplateSwiftList.size() > 0) {

	 
			
			RequestContext.getCurrentInstance().execute("alredyexistedrec.show();");
			return;
		/*	if (poplateSwiftList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
				//setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
			} else if (poplateSwiftList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
				//setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				setRenderEditButton(true);
				setCheckSave(false);
			} else if (poplateSwiftList.get(0).getIsActive().equalsIgnoreCase(Constants.U)) {
				//setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				setRenderEditButton(true);
				setCheckSave(false);
				setCreatedBy(poplateSwiftList.get(0).getCreatedBy());
				setCreatedDate(poplateSwiftList.get(0).getCreatedDate());
			}
			setBooCheckUpdate(true);
			setIsActive(Constants.U);
			setSwiftId(poplateSwiftList.get(0).getSwiftId());
			setSwiftLocation(poplateSwiftList.get(0).getSwiftLocation());
			setBankName(poplateSwiftList.get(0).getBankName());
			setFirstAddress(poplateSwiftList.get(0).getFirstAddress());
			setSecondAddress(poplateSwiftList.get(0).getSecondAddress());
			setThirdAddress(poplateSwiftList.get(0).getThirdAddress());
			setCityName(poplateSwiftList.get(0).getCityName());
			setRegion(poplateSwiftList.get(0).getRegion());
			setAcronymID(poplateSwiftList.get(0).getAcronymId());
			setAbaNumber(poplateSwiftList.get(0).getAbaId());
			setChipsUID(poplateSwiftList.get(0).getChipsUID());
			setFedwireID(poplateSwiftList.get(0).getFedwireId());
			setSwiftBIC(poplateSwiftList.get(0).getSwiftBIC());
			setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE );
*/
		}
	}catch(Exception e){
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}

	}

	public void view() {
		//swiftMasterDataTableList.clear();
		log.info("===========Entered into view() method================== ");
			if(getSwiftBank()!=null&&getSwiftCountryCode()!=null&&getSwiftLocation()!=null&&getBranchName()!=null){
				try{
			
			//List<CountryMaster>    alphaCodeList =generalService.getCountryAlphaList(getSwiftCountryId());
			//log.info("=====Country Alpha2Code===="+alphaCodeList.get( 0).getCountryAlpha2Code());
				//	viewSwiftList = iSwiftMasterService.getAllActiveDeActiveSwift();
		//viewSwiftList = iSwiftMasterService.getSwiftEnquiry(alphaCodeList.get( 0).getCountryAlpha2Code(), generalService.getBankCode(getBankId()));
		//System.out.println("SIZE OF THE LIST ="+viewSwiftList.size());
		
		
		viewSwiftList = iSwiftMasterService.getSwiftInfo(getSwiftBank(),getSwiftCountryCode() , getSwiftLocation(), getBranchName());
		
		if (viewSwiftList!=null && viewSwiftList.size() > 0) { 
			for (SwiftMaster swiftMaster : viewSwiftList) {
				SwiftMasterMaintenanceDataTableBean swiftDT = new SwiftMasterMaintenanceDataTableBean();
				swiftDT.setSwiftId(swiftMaster.getSwiftId());

				/*swiftDT.setBankId(swiftMaster.getBankId().getBankId());
				swiftDT.setSwiftBankName(swiftMaster.getBankId()
						.getBankFullName());
				swiftDT.setSwiftBankId(swiftMaster.getSwiftBankId());

				swiftDT.setBranchId(swiftMaster.getSwiftBranchId()
						.getBankBranchId());
				// swiftDT.setBranchName(iSwiftMasterService.getBranchFullName(swiftMaster.getSwiftBranchId().getBankBranchId()));
				swiftDT.setBranchName(swiftMaster.getSwiftBranchId()
						.getBranchFullName());

				swiftDT.setSwiftCountryId(swiftMaster.getSwiftCountryId()
						.getCountryId());
				swiftDT.setSwiftCountryName(swiftMaster.getSwiftCountryId()
						.getFsCountryMasterDescs().get(0).getCountryName());
*/				
				//swiftDT.setSwiftBankId(iSwiftMasterService.getBankId(swiftMaster.getBankCode() ) );
				//swiftDT.setSwiftBankName(iSwiftMasterService.getBankFullNameBasedOnCode(swiftMaster.getBankCode() ))	;
			
				//swiftDT.setSwiftCountryId(iSwiftMasterService.getCountryId(swiftMaster.getSwiftCountryCode()) );
				//swiftDT.setSwiftCountryName(iSwiftMasterService.getCountryNameBasedOnCountryAlphaCode( swiftMaster.getSwiftCountryCode()));
				
				swiftDT.setBranchId(iSwiftMasterService.getBranchIdForPerticular(swiftMaster.getSwiftBranchCode(), iSwiftMasterService.getBankId(swiftMaster.getBankCode() )));
				log.info( "============"+iSwiftMasterService.getBranchId(swiftMaster.getSwiftBranchCode()));
				//swiftDT.setBranchName(iSwiftMasterService.getBranchName(swiftMaster.getSwiftBranchCode())) ;
				swiftDT.setSwiftBankName(swiftMaster.getBankCode());
				swiftDT.setBranchName(swiftMaster.getSwiftBranchCode()) ;
				swiftDT.setSwiftBankCode(swiftMaster.getBankCode() );
				swiftDT.setSwiftCountryCode(swiftMaster.getSwiftCountryCode() );
				swiftDT.setSwiftBranchCode( swiftMaster.getSwiftBranchCode());
				swiftDT.setSwiftLocation(swiftMaster.getSwiftLocation());
				swiftDT.setBankName(swiftMaster.getBankName());
				swiftDT.setCityName(swiftMaster.getCityName());
				swiftDT.setFirstAddress(swiftMaster.getFirstAddress());
				swiftDT.setSecondAddress(swiftMaster.getSecondAddress());
				swiftDT.setThirdAddress(swiftMaster.getThirdAddress());
				swiftDT.setChipsUID(swiftMaster.getChipsUID());
				swiftDT.setAcronymID(swiftMaster.getAcronymId());
				swiftDT.setFedwireID(swiftMaster.getFedwireId());
				swiftDT.setRegion(swiftMaster.getRegion());
				swiftDT.setSwiftBIC(swiftMaster.getSwiftBIC());
				swiftDT.setAbaNumber(swiftMaster.getAbaId());
				swiftDT.setCreatedBy(swiftMaster.getCreatedBy());
				swiftDT.setCreatedDate(swiftMaster.getCreatedDate());
				swiftDT.setApprovedBy(swiftMaster.getApprovedBy());
				swiftDT.setApprovedDate(swiftMaster.getApprovedDate());
				swiftDT.setModifiedBy(swiftMaster.getModifiedBy() );
				swiftDT.setModifiedDate(swiftMaster.getModifiedDate() );
				swiftDT.setIsActive(swiftMaster.getIsActive());
				setDisableClear(false);
				setDisableEdit(false);
				if (swiftMaster.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					swiftDT.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					swiftDT.setRenderEditButton(true);
					swiftDT.setCheckSave(false);
					swiftDT.setBooCheckDelete(false);
					swiftDT.setBooCheckUpdate(false);
					swiftDT.setBooActivate(false);
				} else if (swiftMaster.getIsActive().equalsIgnoreCase(Constants.D)) {
					swiftDT.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					swiftDT.setRemarks(swiftMaster.getRemarks());
					swiftDT.setRenderEditButton(true);
					swiftDT.setCheckSave(false);
					swiftDT.setBooCheckDelete(false);
					swiftDT.setBooCheckUpdate(false);
					swiftDT.setBooActivate(false);
				}
				else if (swiftMaster.getIsActive().equalsIgnoreCase(Constants.U)&&swiftMaster.getModifiedBy()==null&&swiftMaster.getModifiedDate()==null&&swiftMaster.getRemarks()==null&&swiftMaster.getApprovedBy()==null&&swiftMaster.getApprovedDate()==null) {
					swiftDT.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					swiftDT.setRenderEditButton(true);
					swiftDT.setCheckSave(false);
					swiftDT.setBooCheckDelete(false);
					swiftDT.setBooCheckUpdate(false);
					swiftDT.setBooActivate(false);
				}else{
					swiftDT.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					swiftDT.setRenderEditButton(true);
					swiftDT.setCheckSave(false);
					swiftDT.setBooCheckDelete(false);
					swiftDT.setBooCheckUpdate(false);
					swiftDT.setBooActivate(false);
				}
				viewDataTableList.add(swiftDT);

			}

		}
		if (viewDataTableList.size() > 0) {
			addFieldsToDataTable();
			viewDataTableList.clear();
		} else {
			RequestContext.getCurrentInstance().execute("exist.show();");
		}
				}catch(Exception exception){
					setErrorMessage( exception.getMessage());
					log.info("e.getMessage():::::::::::::::::::::::::::::::::::::::::::"+exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();"); 
					return;
					
				}
		}else{ RequestContext.getCurrentInstance().execute("pleaseselect.show();"); 
		return ;
		}

	}

	public void clickOnOKSave() {
		log.info("Entered into clickOnOKSave() method");
		swiftMasterDataTableList.clear();
		setSwiftId(null);
		setSwiftCountryId(null);
		setBankId(null);
		setSwiftCountryName(null);
		setSwiftBank(null);
		setSwiftLocation(null);
		setBranchName(null);
		setBranchId(null);
		setBankName(null);
		setCityName(null);
		setRegion(null);
		setFirstAddress(null);
		setSecondAddress(null);
		setThirdAddress(null);
		setSwiftBIC(null);
		setChipsUID(null);
		setAcronymID(null);
		setFedwireID(null);
		setAbaNumber(null);
		setIsActive(null);
		setRenderDataTablePanel(false);
		setRenderSaveExitButtonPanel(false);
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterMaintenance.xhtml");
		} catch (IOException e) {
			log.error("IOException Occured  due to navigation problem" + e);
		}
	}

	// This Method Is Used To Clear All The Fields
	public void clearFields() {
		log.info("Entered into clearFields() method");
	 
		allCountryList.clear();
		branchList.clear();
		setBooDisableSubmit(false);
		setSwiftId(null);
		setSwiftCountryId(null);
		setBankId(null);
		setSwiftCountryName(null);
		setSwiftBank(null);
		setSwiftLocation(null);
		setBranchName(null);
		setBranchId(null);
		setBankName(null);
		setCityName(null);
		setRegion(null);
		setFirstAddress(null);
		setSecondAddress(null);
		setThirdAddress(null);
		setSwiftBIC(null);
		setChipsUID(null);
		setAcronymID(null);
		setFedwireID(null);
		setAbaNumber(null);
		setIsActive(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setSwiftCountryCode(null);
		setSwiftCountryName(null);
	}
	
	
	public void clearAllFields() {
		log.info("Entered into clearFields() method");

		setBooDisableSubmit(false);
		setSwiftId(null);
 
 
		setSwiftCountryName(null);
		setSwiftBank(null);
		setSwiftLocation(null);
		setBranchName(null);
		setBranchId(null);
		setBankName(null);
		setCityName(null);
		setRegion(null);
		setFirstAddress(null);
		setSecondAddress(null);
		setThirdAddress(null);
		setSwiftBIC(null);
		setChipsUID(null);
		setAcronymID(null);
		setFedwireID(null);
		setAbaNumber(null);
		setIsActive(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setSwiftCountryCode(null);
		setSwiftCountryName(null);
	}

	/** APPROVAL CODING STARTED **/

	List<SwiftMaster> approvList = new ArrayList<>();
	List<SwiftMasterMaintenanceDataTableBean> swiftApprvDTList = new ArrayList<>();

	public List<SwiftMasterMaintenanceDataTableBean> getSwiftApprvDTList() {
		return swiftApprvDTList;
	}

	public  void approvalList() {
		try{
	 log.info( "Enterded into approvalList()=============");
		swiftApprvDTList.clear();
		approvList = iSwiftMasterService.getAllSwiftForApprove();

		if (approvList.size() > 0) {
			for (SwiftMaster swiftMasterApprv : approvList) {
				SwiftMasterMaintenanceDataTableBean swiftData = new SwiftMasterMaintenanceDataTableBean();

				swiftData.setSwiftId(swiftMasterApprv.getSwiftId());
				swiftData.setSwiftBankCode(swiftMasterApprv.getBankCode() );
				//swiftData.setBankId(swiftMasterApprv.getBankId().getBankId());
				
				String bankFullName = iSwiftMasterService.getBankFullNameBasedOnCode( swiftMasterApprv.getBankCode());
				if(bankFullName!=null)
				{
					swiftData.setSwiftBankName(bankFullName);
				}
				else
				{
					swiftData.setSwiftBankName(swiftMasterApprv.getBankCode());
				}
				
				swiftData.setSwiftBankId(swiftMasterApprv.getSwiftBankId());
			//	swiftData.setSwiftBankName(iSwiftMasterService.getBankFullNameBasedOnCode(swiftMasterApprv.getBankCode() ) );
				
				//swiftData.setBranchId(swiftMasterApprv.getSwiftBranchId().getBankBranchId());
				//swiftData.setBranchName(swiftMasterApprv.getSwiftBranchId().getBranchFullName());
				swiftData.setSwiftBranchCode(swiftMasterApprv.getSwiftBranchCode() );
				swiftData.setBranchName(swiftMasterApprv.getSwiftBranchCode());
				//swiftData.setSwiftCountryId(swiftMasterApprv.getSwiftCountryId().getCountryId());
				//swiftData.setSwiftCountryName(swiftMasterApprv.getSwiftCountryId().getFsCountryMasterDescs().get(0).getCountryName());
				swiftData.setSwiftCountryCode( swiftMasterApprv.getSwiftCountryCode());
				System.out.println("Country Name========="+iSwiftMasterService.getCountryNameBasedOnCountryAlphaCode(swiftMasterApprv.getSwiftCountryCode()));
				swiftData.setSwiftCountryName( iSwiftMasterService.getCountryNameBasedOnCountryAlphaCode(swiftMasterApprv.getSwiftCountryCode()));
				swiftData.setSwiftLocation(swiftMasterApprv.getSwiftLocation());
				swiftData.setBankName(swiftMasterApprv.getBankName());
				swiftData.setCityName(swiftMasterApprv.getCityName());
				swiftData.setFirstAddress(swiftMasterApprv.getFirstAddress());
				swiftData.setSecondAddress(swiftMasterApprv.getSecondAddress());
				swiftData.setThirdAddress(swiftMasterApprv.getThirdAddress());
				swiftData.setChipsUID(swiftMasterApprv.getChipsUID());
				swiftData.setAcronymID(swiftMasterApprv.getAcronymId());
				swiftData.setFedwireID(swiftMasterApprv.getFedwireId());
				swiftData.setRegion(swiftMasterApprv.getRegion());
				swiftData.setSwiftBIC(swiftMasterApprv.getSwiftBIC());
				swiftData.setAbaNumber(swiftMasterApprv.getAbaId());
				swiftData.setCreatedBy(swiftMasterApprv.getCreatedBy());
				swiftData.setCreatedDate(swiftMasterApprv.getCreatedDate());
				swiftData.setModifiedBy(swiftMasterApprv.getModifiedBy());
				swiftData.setModifiedDate(swiftMasterApprv.getModifiedDate());
				swiftApprvDTList.add(swiftData);
			}
		}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: approvalList "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		 
	}

	public void setSwiftApprvDTList(
			List<SwiftMasterMaintenanceDataTableBean> swiftApprvDTList) {
		this.swiftApprvDTList = swiftApprvDTList;
	}

	public void navigationEdit(SwiftMasterMaintenanceDataTableBean dataTable) {
		log.info("===============Entered into navigationEdit() method==============");
		// relationsDT.clear();
		swiftApprvDTList.clear();
		if((dataTable.getModifiedBy()==null?dataTable.getCreatedBy():dataTable.getModifiedBy()).equalsIgnoreCase( session.getUserName())){
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../remittance/SwiftMasterMaintenance.xhtml");
			} catch (IOException e) {
				log.error(" IOException due to navigation problem navigationEdit()" + e);
			}
			setBooApprove(true);
			setBooSwiftBankInput(true);
			setBooSwiftCountryInput(true);
			setBooSwiftBranchInput(true);

			setBooSwiftBranch(false);
			setBooSwiftBank(false);
			setBooSwiftCountry(false);
			setBooSavePanel(false);
			setBooApprovePanel(true);
			setRenderSaveExitButtonPanel(false);

			setRenderDataTablePanel(false);
			setSwiftBank(dataTable.getSwiftBankName());
			setSwiftCountryName(dataTable.getSwiftCountryName());
			setBranchName(dataTable.getBranchName());
			setSwiftCountryId(dataTable.getSwiftCountryId());
			setSwiftId(dataTable.getSwiftId());
			setBankId(dataTable.getBankId());
			setBranchId(dataTable.getBranchId());

			setBankName(dataTable.getBankName());
			setSwiftLocation(dataTable.getSwiftLocation());
			setRegion(dataTable.getRegion());
			setCityName(dataTable.getCityName());
			setFirstAddress(dataTable.getFirstAddress());
			setSecondAddress(dataTable.getSecondAddress());
			setThirdAddress(dataTable.getThirdAddress());
			setAcronymID(dataTable.getAcronymID());
			setSwiftBIC(dataTable.getSwiftBIC());
			setAbaNumber(dataTable.getAbaNumber());
			setFedwireID(dataTable.getFedwireID());
			setChipsUID(dataTable.getChipsUID());
			setCreatedBy(dataTable.getCreatedBy());
			setCreatedDate(dataTable.getCreatedDate());
			setModifiedBy(dataTable.getModifiedBy() );
			setModifiedDate(dataTable.getModifiedDate());
			setIsActive(dataTable.getIsActive());
		}
	}

	// WHEN CLICK ON APPROVE BUTTON THIS METHOD CALLED
	public void swiftApprove() {
		log.info("==========Entered into swiftApprove() method============");

		 
		try{
		String approveMsg=iSwiftMasterService.approveRecord(getSwiftId(), session.getUserName());
		if(approveMsg.equalsIgnoreCase("Success")){
			RequestContext.getCurrentInstance().execute("approve.show();");
		}
		else{
			RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
		}
 
		}catch(Exception e){
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void cancel() {

		swiftApprvDTList.clear();
		approvalList() ;
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterApproval.xhtml");
		} catch (Exception e) {
			log.error("This is Navigation problem when click cancel()" + e.getMessage());
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void goHome() throws IOException {
		log.info( "Entered into goHome() method ");
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	public void clickOnOk() {
		swiftApprvDTList.clear();
		approvalList();
		log.info( "Entered into clickOnOk() method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterApproval.xhtml");
		} catch (Exception e) {
			log.error("This is Navigation problem in clickOnOk()");
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void swiftPageApproval() {
		approvalList();
		log.info( "Entered into swiftPageApproval() method ");
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "SwiftMasterApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterApproval.xhtml");
		} catch (Exception e) {
			log.info("This is Navigation problem in swiftPageApproval()");
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void checkStatusType(
			SwiftMasterMaintenanceDataTableBean swiftMasterDTObj)
			throws IOException {
		log.info("===============Entered into checkStatusType() method================");
try{
		if (swiftMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			swiftMasterDTObj.setRemarkCheck(true);
			swiftMasterDTObj.setBooCheckDelete(swiftMasterDTObj.getBooCheckDelete());
			setApprovedBy(swiftMasterDTObj.getApprovedBy());
			setApprovedDate(swiftMasterDTObj.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");

		}else  if(swiftMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			swiftMasterDTObj.setBooCheckDelete(swiftMasterDTObj.getBooCheckDelete());
			swiftMasterDTObj.setBooActivate(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
		}
		 else if(swiftMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&swiftMasterDTObj.getModifiedBy()==null&&swiftMasterDTObj.getModifiedDate()==null&&swiftMasterDTObj.getApprovedBy()==null&&swiftMasterDTObj.getApprovedDate()==null){
			 swiftMasterDTObj.setBooCheckDelete(true);
			 RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			//removeRecordFromDB(swiftMasterDTObj);
		}else if(swiftMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
			
			removeRecord(swiftMasterDTObj);
		} 
		else if(swiftMasterDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
			RequestContext.getCurrentInstance().execute("couldnot.show();");
		} 

}catch(Exception e){
	setErrorMessage( e.getMessage());
	RequestContext.getCurrentInstance().execute("csp.show();");
}

	}
	public void confirmPermanentDelete(){
		
		if(swiftMasterDataTableList.size()>0){
			for (SwiftMasterMaintenanceDataTableBean  swiftDatatableObj : swiftMasterDataTableList) {
				if(swiftDatatableObj.getBooCheckDelete()){
				removeRecordFromDB(swiftDatatableObj);
				swiftMasterDataTableList.clear();
				view();
				swiftMasterDataTableList.addAll(viewDataTableList);
				
				}
				if(swiftMasterDataTableList.size()<=0){
					setRenderSaveExitButtonPanel(false);
					setRenderDataTablePanel(false);
					
				}
			}
		}
	 }
	//perminent delete
	public void removeRecordFromDB(SwiftMasterMaintenanceDataTableBean swiftMasterDTObj){
		try{
		SwiftMaster swiftMaster=new SwiftMaster();
		swiftMaster.setSwiftId(swiftMasterDTObj.getSwiftId());

		swiftMaster.setSwiftBankId(swiftMasterDTObj.getBankId());

		swiftMaster.setFirstAddress(swiftMasterDTObj.getFirstAddress());
		swiftMaster.setSecondAddress(swiftMasterDTObj.getSecondAddress());
		swiftMaster.setThirdAddress(swiftMasterDTObj.getThirdAddress());

		swiftMaster.setAbaId(swiftMasterDTObj.getAbaNumber());
		swiftMaster.setAcronymId(swiftMasterDTObj.getAcronymID());

		swiftMaster.setChipsUID(swiftMasterDTObj.getChipsUID());
		swiftMaster.setFedwireId(swiftMasterDTObj.getFedwireID());
		swiftMaster.setSwiftBIC(swiftMasterDTObj.getSwiftBIC());

		//BankMaster bankMaster = new BankMaster();
		//bankMaster.setBankId(swiftMasterDTObj.getBankId());
		//swiftMaster.setBankId(bankMaster);
		swiftMaster.setBankCode( swiftMasterDTObj.getSwiftBankCode() );
		
		//CountryMaster countryMaster = new CountryMaster();
		//countryMaster.setCountryId(swiftMasterDTObj.getSwiftCountryId());
		//swiftMaster.setSwiftCountryId(countryMaster);
		swiftMaster.setSwiftCountryCode( swiftMasterDTObj.getSwiftCountryCode() );

		//BankBranch bankBranch = new BankBranch();
		//bankBranch.setBankBranchId(swiftMasterDTObj.getBranchId());
		//swiftMaster.setSwiftBranchId(bankBranch);
		swiftMaster.setSwiftBranchCode(  swiftMasterDTObj.getSwiftBranchCode());
		
		swiftMaster.setSwiftLocation(swiftMasterDTObj.getSwiftLocation());
		swiftMaster.setBankName(swiftMasterDTObj.getBankName());
		swiftMaster.setCityName(swiftMasterDTObj.getCityName());
		swiftMaster.setRegion(swiftMasterDTObj.getRegion());

		swiftMaster.setCreatedBy(swiftMasterDTObj.getCreatedBy());
		swiftMaster.setCreatedDate(swiftMasterDTObj.getCreatedDate());
		swiftMaster.setApprovedBy(swiftMasterDTObj.getApprovedBy());
		swiftMaster.setApprovedDate(swiftMasterDTObj.getApprovedDate());

		swiftMaster.setModifiedBy(swiftMasterDTObj.getModifiedBy());
		swiftMaster.setModifiedDate(swiftMasterDTObj.getModifiedDate());
		iSwiftMasterService.delete(swiftMaster);
	
		swiftMasterDataTableList.remove(swiftMasterDTObj);
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: removeRecordFromDB"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		}
	//this is called when clickok of remarks dialog
	public void remarkSelectedRecord() throws IOException {
		log.info("Entered into remarkSelectedRecord() method");
		for (SwiftMasterMaintenanceDataTableBean swiftMasterdtObj : swiftMasterDataTableList) {
			if (swiftMasterdtObj.getRemarkCheck() != null) {
				if (swiftMasterdtObj.getRemarkCheck().equals(true)) {
					swiftMasterdtObj.setRemarks(getRemarks());
					removeRecord(swiftMasterdtObj);
					setRemarks(null);
				}
			}
		}

	}

	public void cancelRemarks(){
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterMaintenance.xhtml");
		}  
			catch(Exception e){
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	 
	}
	public void clickOKButton(){
		
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../remittance/SwiftMasterApproval.xhtml");
		} catch (Exception e) {
			log.info("This is Navigation problem in swiftPageApproval()");
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

		
	}

	public Map<BigDecimal, String> getSwiftCountryMapCode() {
		return swiftCountryMapCode;
	}

	public void setSwiftCountryMapCode(Map<BigDecimal, String> swiftCountryMapCode) {
		this.swiftCountryMapCode = swiftCountryMapCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public List<String> autoCompleteBankCode(String query){
		 List<String> results =null;
	
		 if (query.length() > 0) {
		        List<String>   list = iSwiftMasterService.getAllSwiftBank(query);		        
		        results = new ArrayList<String>();  
		        if(list!=null){
			        for (String bank:list) {  
			            results.add(bank.toString());  
			        }  
		        }
		        
		       }
		return results;
	}
	
	public List<String> autoCompleteLocation(String query){
		 List<String> results =null;
		
		 if (query.length() > 0) {
		        List<String>  swiftMasterList = iSwiftMasterService.getAllSwiftLocation(getSwiftBank(), getSwiftCountryCode(),query);
		        results = new ArrayList<String>();  
		        if(swiftMasterList!=null){
			        for (String swift:swiftMasterList) {  
			            results.add(swift.toString());  
			        } 
		        }
		        
		       }
		return results;
	}
	
	public List<String> autoCompleteBranch(String query){
		 List<String> results =null;
		
		 if (query.length() > 0) {
		        List<String>  swiftMasterList = iSwiftMasterService.getAllSwiftBranch(getSwiftBank(), getSwiftCountryCode(),getSwiftLocation(),query);
		        results = new ArrayList<String>();  
		        if(swiftMasterList!=null){			        
			        for (String swift:swiftMasterList) {  
			            results.add(swift.toString());  
			        }
		        }
		        
		       }
		return results;
	}
	
	public void fetchSwiftInfo() throws IOException{	
		clear();
		List<SwiftMaster> list = iSwiftMasterService.getSwiftInfo(getSwiftBank(),getSwiftCountryCode() , getSwiftLocation(), getBranchName());		
		if(list!=null){
			
			setSwiftId(list.get(0).getSwiftId());
			setBranchName(list.get(0).getSwiftBranchCode());			
			setSwiftLocation(list.get(0).getSwiftLocation());
			setSwiftBank(list.get(0).getBankCode());
			setCityName(list.get(0).getCityName());
			setRegion(list.get(0).getRegion());
			setFirstAddress(list.get(0).getFirstAddress());
			setSecondAddress(list.get(0).getSecondAddress());
			setThirdAddress(list.get(0).getThirdAddress());
			
			setChipsUID(list.get(0).getChipsUID());
			setAcronymID(list.get(0).getAcronymId());
			setFedwireID(list.get(0).getFedwireId());
			setAbaNumber(list.get(0).getAbaId());
			setBankName(list.get(0).getBankName());
			
			if(list.get(0).getSwiftBIC().trim()==null||list.get(0).getSwiftBIC().trim().length()==0){
				setSwiftBIC(getSwiftBank()+getSwiftCountryCode()+getSwiftLocation()+getBranchName());
			}else{
				setSwiftBIC(list.get(0).getSwiftBIC());
			}
			
		}else{	
			if(getSwiftBank()!=null&&getSwiftBank().length()>0&&getSwiftCountryCode()!=null&&getSwiftCountryCode().length()>0&&getSwiftLocation()!=null&&getSwiftLocation().length()>0&&getBranchName()!=null&&getBranchName().length()>0){				
				setSwiftBIC(getSwiftBank()+getSwiftCountryCode()+getSwiftLocation()+getBranchName());
			}/*else{
				if(getSwiftBank()==null||getSwiftBank().length()==0||getSwiftCountryCode()==null||getSwiftCountryCode().length()==0||getSwiftLocation()==null||getSwiftLocation().length()==0||getBranchName()==null||getBranchName().length()==0){				
					setSwiftBIC(null);
				}else{
					setSwiftBIC(null);
				}
				
			}*/
			
			
		}
		/*FacesContext.getCurrentInstance().getExternalContext()
		.redirect("../remittance/SwiftMasterMaintenance.xhtml");*/
	}
	
	public void clear() {
		setBankName(null);
		setCityName(null);
		setRegion(null);
		setFirstAddress(null);
		setSecondAddress(null);
		setThirdAddress(null);
		setSwiftBIC(null);
		setChipsUID(null);
		setAcronymID(null);
		setFedwireID(null);
		setAbaNumber(null);
		setIsActive(null);
		
	}


	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getSwiftCountryCode() {
		return swiftCountryCode;
	}

	public void setSwiftCountryCode(String swiftCountryCode) {
		this.swiftCountryCode = swiftCountryCode;
	}
	
	
}
