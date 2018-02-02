package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.service.IStateMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("stateMasterBeanFileupload")
@Scope("session")
public class StateMasterBeanFileupload<T> implements Serializable {
	
	private static final long serialVersionUID = 5613023821059903778L;
	private static final Logger LOGGER=Logger.getLogger(StateMasterBeanFileupload.class);
	private String stateCode;
	private BigDecimal countryId;
	private String countryName;
	private BigDecimal stateId;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private String englishStateName;
	private String arabicStateName;
	private String dynamicLabelForActivateDeactivate;
	private Boolean renderEditButton = false;
	private BigDecimal englishStateDescPK;
	private BigDecimal arabicStateDescPK;
	private Boolean booSaveExit;
	private Boolean booAdd;
	private Boolean booRenderDataTable;
	private Boolean submt = false;
	private Boolean submitPanel;
	private boolean booEditButton;
	private Boolean clearPanel = false;
	private Boolean boohideSecod;
	private Boolean booReadonly;
	private Boolean IfFileUpload=false;
	private UploadedFile uploadedFile;
	private Boolean booRenderFileUploadPanel;
	private List<StateMasterDataTable> stateMasterDTList = new CopyOnWriteArrayList<StateMasterDataTable>();
	private List<StateMasterDataTable> stateMasterNewDTList = new CopyOnWriteArrayList<StateMasterDataTable>();
	private List<CountryMaster> countryMasterList = new ArrayList<CountryMaster>();
	private List<CountryMasterDesc> countryMasterDescLT = new ArrayList<CountryMasterDesc>();
	private List<StateMaster> stateMasters = new ArrayList<StateMaster>();
	private List<StateMasterDesc> stateDesc = new ArrayList<StateMasterDesc>();
	private SessionStateManage session = new SessionStateManage();
	StateMaster stateMaster = new StateMaster();

	@Autowired
	IStateMasterService<T> stateMasterService;

	

	public String getStateCode() {
		return stateCode;
	}

	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public BigDecimal getStateId() {
		return stateId;
	}

	public void setStateId(BigDecimal stateId) {
		this.stateId = stateId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getEnglishStateName() {
		return englishStateName;
	}

	public void setEnglishStateName(String englishStateName) {
		this.englishStateName = englishStateName;
	}

	public String getArabicStateName() {
		return arabicStateName;
	}

	public void setArabicStateName(String arabicStateName) {
		this.arabicStateName = arabicStateName;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public BigDecimal getEnglishStateDescPK() {
		return englishStateDescPK;
	}

	public void setEnglishStateDescPK(BigDecimal englishStateDescPK) {
		this.englishStateDescPK = englishStateDescPK;
	}

	public BigDecimal getArabicStateDescPK() {
		return arabicStateDescPK;
	}

	public void setArabicStateDescPK(BigDecimal arabicStateDescPK) {
		this.arabicStateDescPK = arabicStateDescPK;
	}

	public Boolean getBooSaveExit() {
		return booSaveExit;
	}

	public void setBooSaveExit(Boolean booSaveExit) {
		this.booSaveExit = booSaveExit;
	}

	public Boolean getBooAdd() {
		return booAdd;
	}

	public void setBooAdd(Boolean booAdd) {
		this.booAdd = booAdd;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public boolean isBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}

	public Boolean getClearPanel() {
		return clearPanel;
	}

	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}

	public Boolean getBoohideSecod() {
		return boohideSecod;
	}

	public void setBoohideSecod(Boolean boohideSecod) {
		this.boohideSecod = boohideSecod;
	}

	public Boolean getBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(Boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	public List<StateMasterDataTable> getStateMasterDTList() {
		return stateMasterDTList;
	}

	public void setStateMasterDTList(List<StateMasterDataTable> stateMasterDTList) {
		this.stateMasterDTList = stateMasterDTList;
	}

	public List<StateMasterDataTable> getStateMasterNewDTList() {
		return stateMasterNewDTList;
	}

	public void setStateMasterNewDTList(List<StateMasterDataTable> stateMasterNewDTList) {
		this.stateMasterNewDTList = stateMasterNewDTList;
	}

	public List<CountryMaster> getCountryMasterList() {
		return countryMasterList;
	}

	public void setCountryMasterList(List<CountryMaster> countryMasterList) {
		this.countryMasterList = countryMasterList;
	}

	public List<CountryMasterDesc> getCountryMasterDescLT() {
		return countryMasterDescLT;
	}

	public void setCountryMasterDescLT(List<CountryMasterDesc> countryMasterDescLT) {
		this.countryMasterDescLT = countryMasterDescLT;
	}

	public List<StateMasterDesc> getStateDesc() {
		return stateDesc;
	}

	public void setStateDesc(List<StateMasterDesc> stateDesc) {
		this.stateDesc = stateDesc;
	}

	public Boolean getSubmt() {
		return submt;
	}

	public void setSubmt(Boolean submt) {
		this.submt = submt;
	}

	public Boolean getSubmitPanel() {
		return submitPanel;
	}

	public void setSubmitPanel(Boolean submitPanel) {
		this.submitPanel = submitPanel;
	}

	public List<StateMaster> getStateMasters() {
		return stateMasters;
	}

	public void setStateMasters(List<StateMaster> stateMasters) {
		this.stateMasters = stateMasters;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Boolean getBooRenderFileUploadPanel() {
		return booRenderFileUploadPanel;
	}

	public void setBooRenderFileUploadPanel(Boolean booRenderFileUploadPanel) {
		this.booRenderFileUploadPanel = booRenderFileUploadPanel;
	}

	
	public Boolean getIfFileUpload() {
	  return IfFileUpload;
}

public void setIfFileUpload(Boolean ifFileUpload) {
	  IfFileUpload = ifFileUpload;
}

public void handleFileUpload(FileUploadEvent event)   {
		LOGGER.info("Entering into handleFileUpload method");
		try {
			uploadedFile = event.getFile();
			uploadedFile.getFileName();
			setIfFileUpload(true);
		} catch (Exception e) {
			  e.printStackTrace();
		}
		LOGGER.info("Exit into handleFileUpload method");
	}

	public void addRecordsToDataTable() {
		  LOGGER.info("Entering into addRecordsToDataTable method");
			
		  if(getIfFileUpload().equals(false)){
			    RequestContext.getCurrentInstance().execute("noFileUploded.show();");
				return;    
		  }else if (!uploadedFile.getFileName().equalsIgnoreCase("StateMaster.xls")) {
			    RequestContext.getCurrentInstance().execute("changeFile.show();");
				setIfFileUpload(false);
				setUploadedFile(null);
	  }else{
		    stateMasterDTList.clear();
		try {
			Workbook workbook = null;
			workbook = new HSSFWorkbook(uploadedFile.getInputstream());
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				Iterator<Row> rowIterator = sheet.iterator();
				int rowNumber = 1;
				
				while (rowIterator.hasNext()) {

					Row row = rowIterator.next();
					if (rowNumber == 1) {
						rowNumber++;
						continue;
					}
					
					Iterator<Cell> cellIterator = row.cellIterator();

					StateMasterDataTable stateMasterDTObj = new StateMasterDataTable();
					
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getCellType()) {
						case Cell.CELL_TYPE_STRING:

							if (cell.getColumnIndex() == 2) {
								String englishStateName = cell.getStringCellValue().trim();
								System.out.println("englishStateName............................" + englishStateName);
								stateMasterDTObj.setEnglishStateName(englishStateName);
							}
							if (cell.getColumnIndex() == 3) {
								String arabicStateName = cell.getStringCellValue().trim();
								System.out.println("arabicStateName............................" + arabicStateName);
								stateMasterDTObj.setArabicStateName(arabicStateName);
							}
							break;
						case Cell.CELL_TYPE_NUMERIC:
							/*
							 * if(cell.getColumnIndex()==0){ Double d =
							 * cell.getNumericCellValue(); System.out.println(
							 * "State_id    :::::::::::::::::::::::::::::::::::::::::::::::="
							 * +d.intValue()); stateMasterDTObj.setStateId(new
							 * BigDecimal(d.intValue()));}
							 */

							if (cell.getColumnIndex() == 0) {
								Double d = cell.getNumericCellValue();
								System.out.println("Country Id =" + d.intValue());
								stateMasterDTObj.setCountryId(new BigDecimal(d.intValue()));
							}
							if (cell.getColumnIndex() == 1) {
								Double d = cell.getNumericCellValue();
								System.out.println("stateCode for CELL_TYPE_NUMERIC ....................=" + d.intValue());
								stateMasterDTObj.setStateCode(new BigDecimal(d.intValue()).toPlainString());
							}
							break;
						case Cell.CELL_TYPE_BLANK:
						}
					}
					boolean duplicateRecordCheck=false;
					if(stateMasterDTList != null && stateMasterDTList.size() !=0){
						  for (StateMasterDataTable stateMaster : stateMasterDTList) {
							 if(stateMaster.getStateCode().equalsIgnoreCase(stateMasterDTObj.getStateCode())&&stateMaster.getCountryId().equals(stateMasterDTObj.getCountryId())){
								   duplicateRecordCheck=true;
								   stateMasterDTObj.setDynamicLabelForActivateDeactivate("Duplicate"); 
							 }
						  }
					}
					if(!duplicateRecordCheck){
						  List<StateMaster> stateMasterDesList = stateMasterService.getStateList(stateMasterDTObj.getStateCode(), stateMasterDTObj.getCountryId());
							List<StateMasterDesc> englishStateDesc=stateMasterService.toFetchAllStateDescBasedOnDesc(stateMasterDTObj.getEnglishStateName(), stateMasterDTObj.getCountryId());
							/*List<StateMasterDesc> arabicStateDesc=stateMasterService.toFetchStateDesc(stateMasterDTObj.getArabicStateName(), stateMasterDTObj.getCountryId());*/
							if (stateMasterDesList != null && stateMasterDesList.size() != 0 || englishStateDesc !=null && englishStateDesc.size() !=0 ) {
								  stateMasterDTObj.setDynamicLabelForActivateDeactivate("Duplicate");
							} else {
								 
								  stateMasterDTObj.setDynamicLabelForActivateDeactivate(Constants.New_Record);
								
							}
					}
					//List<StateMaster> stateMasterDesList = stateMasterService.getstateMasterList(stateMasterDTObj.getStateCode());
					
					
					stateMasterDTList.add(stateMasterDTObj);
					setBooRenderDataTable(true);
					setBooSaveExit(true);
				}

			}
		} catch (Exception e) {
			  e.printStackTrace();
		}
	  }
		LOGGER.info("Exit into addRecordsToDataTable method");
	}

	// save Records for FileUpload

	public void saveDataTableRecods() {
		LOGGER.info("Entering into saveDataTableRecods method");
		CountryMaster countryMaster = new CountryMaster();
		try {
			for (StateMasterDataTable StateMasterDtObj : stateMasterDTList) {
				if (StateMasterDtObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.New_Record)) {
					StateMaster stateMaster = new StateMaster();
					stateMaster.setStateCode(StateMasterDtObj.getStateCode());
					countryMaster.setCountryId(StateMasterDtObj.getCountryId());
					stateMaster.setFsCountryMaster(countryMaster);
					stateMaster.setIsActive(Constants.U);

					stateMaster.setCreatedBy(session.getUserName());
					stateMaster.setCreatedDate(new Date());

					stateMasterService.saveRecordForFileupload(stateMaster);

					// Eng
					StateMasterDesc stateMasterDes1 = new StateMasterDesc();
					stateMasterDes1.setStateDescId(StateMasterDtObj.getEnglishStateDescPK());
					stateMasterDes1.setFsStateMaster(stateMaster);
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(1));
					stateMasterDes1.setFsLanguageType(languageType);
					stateMasterDes1.setStateName(StateMasterDtObj.getEnglishStateName());
					stateMasterService.saveRecordForFileUploadDesc(stateMasterDes1);
					// Ara
					StateMasterDesc stateMasterDes2 = new StateMasterDesc();
					stateMasterDes2.setStateDescId(StateMasterDtObj.getArabicStateDescPK());
					stateMasterDes2.setFsStateMaster(stateMaster);
					LanguageType languageType2 = new LanguageType();
					languageType2.setLanguageId(new BigDecimal(2));
					stateMasterDes2.setFsLanguageType(languageType2);
					stateMasterDes2.setStateName(StateMasterDtObj.getArabicStateName());
					stateMasterService.saveRecordForFileUploadDesc(stateMasterDes2);

				}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			clearAllFields();
			stateMasterDTList.clear();
			stateMasterNewDTList.clear();
		} catch (Exception e) {
		}
		LOGGER.info("Exit into saveDataTableRecods method");
	}



	// Clear All Fields

	public void clearAllFields() {
		LOGGER.info("Entering into clearAllFields method");
		setBooRenderDataTable(false);
		setBooSaveExit(false);
		LOGGER.info("Exit into clearAllFields method");
	}

	public void clickOnOKFileuploadSave() {
		LOGGER.info("Entering into clickOnOKFileuploadSave method");
		setIfFileUpload(true);
		setBooSaveExit(false);

		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/StateMasterFileupload.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into clickOnOKFileuploadSave method");	
	}
	public void exit() {
		LOGGER.info("Entering into exit method");
		try{
			  stateMasterDTList.clear();
			stateMasterNewDTList.clear();
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		LOGGER.info("Exit into exit method");
	}
}
