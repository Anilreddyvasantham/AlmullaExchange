package com.amg.exchange.complaint.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.service.IComplaintRemarksService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("complaintRemarks")
@Scope("session")
public class ComplaintRemarksBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ComplaintRemarksBean.class);

	private BigDecimal complaintRemarksId;
	private BigDecimal complaintRemarksDescId;
	private BigDecimal engComplaintRemarksDescId;
	private BigDecimal localComplaintRemarksDescId;
	private String fullEngComplaintRemarksDesc;
	private String fullLocalComplaintRemarksDesc;
	private String shortEngComplaintRemarksDesc;
	private String shortLocalComplaintRemarksDesc;
	private BigDecimal appCountryId;
	private String complaintRemarksCode;
	private String tagRemittance;
	private String tagRemittanceName;
	private String tagBenificiary;
	private String tagBenificiaryName;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks = "";
	private BigDecimal localLanguageId;
	private BigDecimal englishLanguageId;
	private String dynamicLabelForActivateDeactivate;
	
	private Boolean ifEditClicked=false;

	private Boolean booEdit;
	private Boolean isdisable;
	private Boolean btnClear;

	private Boolean booComplaintRemarksDetail;
	private Boolean booComplaintRemarksDataTable;

	private List<ComplaintRemarksDataTable> lstComplaintRemarksNewDataTables = new ArrayList<ComplaintRemarksDataTable>();
	private List<ComplaintRemarksDataTable> lstComplaintRemarksDataTables = new CopyOnWriteArrayList<ComplaintRemarksDataTable>();

	private ComplaintRemarksDataTable complaintRemarksDataTable = null;

	private List<ComplaintRemarksMaster> lstofComplaintRemarks = new ArrayList<ComplaintRemarksMaster>();
	private List<ComplaintRemarksDesc> lstofComplaintRemarksDesc = new ArrayList<ComplaintRemarksDesc>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IComplaintRemarksService<T> complaintRemarksService;

	public ComplaintRemarksBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getComplaintRemarksId() {
		return complaintRemarksId;
	}

	public void setComplaintRemarksId(BigDecimal complaintRemarksId) {
		this.complaintRemarksId = complaintRemarksId;
	}

	public BigDecimal getComplaintRemarksDescId() {
		return complaintRemarksDescId;
	}

	public void setComplaintRemarksDescId(BigDecimal complaintRemarksDescId) {
		this.complaintRemarksDescId = complaintRemarksDescId;
	}

	public BigDecimal getEngComplaintRemarksDescId() {
		return engComplaintRemarksDescId;
	}

	public void setEngComplaintRemarksDescId(BigDecimal engComplaintRemarksDescId) {
		this.engComplaintRemarksDescId = engComplaintRemarksDescId;
	}

	public BigDecimal getLocalComplaintRemarksDescId() {
		return localComplaintRemarksDescId;
	}

	public void setLocalComplaintRemarksDescId(BigDecimal localComplaintRemarksDescId) {
		this.localComplaintRemarksDescId = localComplaintRemarksDescId;
	}

	public String getFullEngComplaintRemarksDesc() {
		return fullEngComplaintRemarksDesc;
	}

	public void setFullEngComplaintRemarksDesc(String fullEngComplaintRemarksDesc) {
		this.fullEngComplaintRemarksDesc = fullEngComplaintRemarksDesc;
	}

	public String getFullLocalComplaintRemarksDesc() {
		return fullLocalComplaintRemarksDesc;
	}

	public void setFullLocalComplaintRemarksDesc(String fullLocalComplaintRemarksDesc) {
		this.fullLocalComplaintRemarksDesc = fullLocalComplaintRemarksDesc;
	}

	public String getShortEngComplaintRemarksDesc() {
		return shortEngComplaintRemarksDesc;
	}

	public void setShortEngComplaintRemarksDesc(String shortEngComplaintRemarksDesc) {
		this.shortEngComplaintRemarksDesc = shortEngComplaintRemarksDesc;
	}

	public String getShortLocalComplaintRemarksDesc() {
		return shortLocalComplaintRemarksDesc;
	}

	public void setShortLocalComplaintRemarksDesc(String shortLocalComplaintRemarksDesc) {
		this.shortLocalComplaintRemarksDesc = shortLocalComplaintRemarksDesc;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public String getComplaintRemarksCode() {
		return complaintRemarksCode;
	}

	public void setComplaintRemarksCode(String complaintRemarksCode) {
		this.complaintRemarksCode = complaintRemarksCode;
	}

	public String getTagRemittance() {
		return tagRemittance;
	}

	public void setTagRemittance(String tagRemittance) {
		this.tagRemittance = tagRemittance;
	}

	public String getTagRemittanceName() {
		return tagRemittanceName;
	}

	public void setTagRemittanceName(String tagRemittanceName) {
		this.tagRemittanceName = tagRemittanceName;
	}

	public String getTagBenificiary() {
		return tagBenificiary;
	}

	public void setTagBenificiary(String tagBenificiary) {
		this.tagBenificiary = tagBenificiary;
	}

	public String getTagBenificiaryName() {
		return tagBenificiaryName;
	}

	public void setTagBenificiaryName(String tagBenificiaryName) {
		this.tagBenificiaryName = tagBenificiaryName;
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

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	public Boolean getBtnClear() {
		return btnClear;
	}

	public void setBtnClear(Boolean btnClear) {
		this.btnClear = btnClear;
	}
	

	public Boolean getIfEditClicked() {
		return ifEditClicked;
	}

	public void setIfEditClicked(Boolean ifEditClicked) {
		this.ifEditClicked = ifEditClicked;
	}

	public Boolean getBooComplaintRemarksDetail() {
		return booComplaintRemarksDetail;
	}

	public void setBooComplaintRemarksDetail(Boolean booComplaintRemarksDetail) {
		this.booComplaintRemarksDetail = booComplaintRemarksDetail;
	}

	public Boolean getBooComplaintRemarksDataTable() {
		return booComplaintRemarksDataTable;
	}

	public void setBooComplaintRemarksDataTable(Boolean booComplaintRemarksDataTable) {
		this.booComplaintRemarksDataTable = booComplaintRemarksDataTable;
	}

	public BigDecimal getLocalLanguageId() {
		return localLanguageId;
	}

	public void setLocalLanguageId(BigDecimal localLanguageId) {
		this.localLanguageId = localLanguageId;
	}

	public BigDecimal getEnglishLanguageId() {
		return englishLanguageId;
	}

	public void setEnglishLanguageId(BigDecimal englishLanguageId) {
		this.englishLanguageId = englishLanguageId;
	}

	public List<ComplaintRemarksDataTable> getLstComplaintRemarksDataTables() {
		return lstComplaintRemarksDataTables;
	}

	public void setLstComplaintRemarksDataTables(List<ComplaintRemarksDataTable> lstComplaintRemarksDataTables) {
		this.lstComplaintRemarksDataTables = lstComplaintRemarksDataTables;
	}

	public IComplaintRemarksService<T> getComplaintRemarksService() {
		return complaintRemarksService;
	}

	public void setComplaintRemarksService(IComplaintRemarksService<T> complaintRemarksService) {
		this.complaintRemarksService = complaintRemarksService;
	}

	public List<ComplaintRemarksDataTable> getLstComplaintRemarksNewDataTables() {
		return lstComplaintRemarksNewDataTables;
	}

	public void setLstComplaintRemarksNewDataTables(List<ComplaintRemarksDataTable> lstComplaintRemarksNewDataTables) {
		this.lstComplaintRemarksNewDataTables = lstComplaintRemarksNewDataTables;
	}

	public ComplaintRemarksDataTable getComplaintRemarksDataTable() {
		return complaintRemarksDataTable;
	}

	public void setComplaintRemarksDataTable(ComplaintRemarksDataTable complaintRemarksDataTable) {
		this.complaintRemarksDataTable = complaintRemarksDataTable;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void complaintRemarksCreationNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintremarksmaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintremarksmaster.xhtml");
			setBooComplaintRemarksDataTable(false);
			setBooComplaintRemarksDetail(true);
			lstComplaintRemarksDataTables.clear();
			lstComplaintRemarksNewDataTables.clear();
			clearAll();

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void complaintRemarksApprovalNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "complaintremarksapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintremarksapproval.xhtml");
			setBooComplaintRemarksDataTable(true);
			setBooComplaintRemarksDetail(false);

			approveViewComplaintRemarksMethod();

		} catch (Exception e) {
			// TODO: handle exception complaintRemarksApprovalNavigation
		}
	}

	public void duplicateChekingComplaintRemarksCode() {
		if (lstComplaintRemarksDataTables.size() > 0) {
			for (ComplaintRemarksDataTable dataTable : lstComplaintRemarksDataTables) {
				if (dataTable.getComplaintRemarksCode().equalsIgnoreCase(getComplaintRemarksCode())) {
					clearAll();
					RequestContext.getCurrentInstance().execute("datatable.show();");
					return;
				}
			}
		}

		if (getComplaintRemarksCode() != null) {
			addRecordsToDataTable();
		}
	}

	public void addRecordsToDataTable() {

		ComplaintRemarksDataTable complaintRemarksDataTable = new ComplaintRemarksDataTable();
		complaintRemarksDataTable.setComplaintRemarksId(getComplaintRemarksId());
		complaintRemarksDataTable.setAppCountryId(sessionStateManage.getCountryId());
		complaintRemarksDataTable.setComplaintRemarksCode(getComplaintRemarksCode());
		complaintRemarksDataTable.setComplaintRemarksDescId(getComplaintRemarksDescId());

		if (getTagBenificiary() != null) {
			if (getTagBenificiary().equals(Constants.Yes)) {
				complaintRemarksDataTable.setTagBenificiary(Constants.Yes);
				complaintRemarksDataTable.setTagBenificiaryName(Constants.YES);
			} else if (getTagBenificiary().equals(Constants.No)) {
				complaintRemarksDataTable.setTagBenificiary(Constants.No);
				complaintRemarksDataTable.setTagBenificiaryName(Constants.NO);
			}
		}

		if (getTagRemittance() != null) {
			if (getTagRemittance().equals(Constants.Yes)) {
				complaintRemarksDataTable.setTagRemittance(Constants.Yes);
				complaintRemarksDataTable.setTagRemittanceName(Constants.YES);
			} else if (getTagRemittance().equals(Constants.No)) {
				complaintRemarksDataTable.setTagRemittance(Constants.No);
				complaintRemarksDataTable.setTagRemittanceName(Constants.NO);
			}
		}

		complaintRemarksDataTable.setEngComplaintRemarksDescId(getEngComplaintRemarksDescId());
		complaintRemarksDataTable.setLocalComplaintRemarksDescId(getLocalComplaintRemarksDescId());

		complaintRemarksDataTable.setLocalLanguageId(getLocalLanguageId());
		complaintRemarksDataTable.setEnglishLanguageId(getEnglishLanguageId());
		complaintRemarksDataTable.setFullEngComplaintRemarksDesc(getFullEngComplaintRemarksDesc());
		complaintRemarksDataTable.setFullLocalComplaintRemarksDesc(getShortLocalComplaintRemarksDesc());
		complaintRemarksDataTable.setShortEngComplaintRemarksDesc(getShortEngComplaintRemarksDesc());
		complaintRemarksDataTable.setShortLocalComplaintRemarksDesc(getShortLocalComplaintRemarksDesc());

		complaintRemarksDataTable.setCreatedBy(getCreatedBy());
		complaintRemarksDataTable.setCreatedDate(getCreatedDate());

		if (getComplaintRemarksId() != null) {
			/*if (lstComplaintRemarksNewDataTables.size() == 0 && (lstComplaintRemarksDataTables.size() != 0 || getComplaintRemarksDataTable() != null)) {
				complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

				complaintRemarksDataTable.setIsActive(Constants.U);
				complaintRemarksDataTable.setModifiedBy(sessionStateManage.getUserName());
				complaintRemarksDataTable.setModifiedDate(new Date());
				complaintRemarksDataTable.setApprovedBy(null);
				complaintRemarksDataTable.setApprovedDate(null);
				complaintRemarksDataTable.setRemarks(null);*/
				 if(getIfEditClicked().equals(true)){
					 
				 
				//if (getComplaintRemarksDataTable() != null) {
					if ((complaintRemarksDataTable.getComplaintRemarksCode().equalsIgnoreCase(getComplaintRemarksCode()) && complaintRemarksDataTable.getTagBenificiary().equals(getTagBenificiary()) && complaintRemarksDataTable.getTagRemittance().equals(getTagRemittance())
							&& complaintRemarksDataTable.getFullEngComplaintRemarksDesc().equalsIgnoreCase(getFullEngComplaintRemarksDesc()) && complaintRemarksDataTable.getShortEngComplaintRemarksDesc().equalsIgnoreCase(getShortEngComplaintRemarksDesc())
							&& complaintRemarksDataTable.getFullLocalComplaintRemarksDesc().equalsIgnoreCase(getFullLocalComplaintRemarksDesc()) && complaintRemarksDataTable.getShortLocalComplaintRemarksDesc().equalsIgnoreCase(getShortLocalComplaintRemarksDesc()))) {

						complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						complaintRemarksDataTable.setIsActive(getIsActive());
						complaintRemarksDataTable.setModifiedBy(getModifiedBy());
						complaintRemarksDataTable.setModifiedDate(getModifiedDate());
						complaintRemarksDataTable.setApprovedBy(getApprovedBy());
						complaintRemarksDataTable.setApprovedDate(getApprovedDate());
						complaintRemarksDataTable.setRemarks(getRemarks());

					} else {
						complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

						complaintRemarksDataTable.setIsActive(Constants.U);
						complaintRemarksDataTable.setModifiedBy(sessionStateManage.getUserName());
						complaintRemarksDataTable.setModifiedDate(new Date());
						complaintRemarksDataTable.setApprovedBy(null);
						complaintRemarksDataTable.setApprovedDate(null);
						complaintRemarksDataTable.setRemarks(null);

					}
				}
			/*} else {
				complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				complaintRemarksDataTable.setIsActive(getIsActive());
				complaintRemarksDataTable.setModifiedBy(getModifiedBy());
				complaintRemarksDataTable.setModifiedDate(getModifiedDate());
				complaintRemarksDataTable.setApprovedBy(getApprovedBy());
				complaintRemarksDataTable.setApprovedDate(getApprovedDate());
				complaintRemarksDataTable.setRemarks(getRemarks());
			}*/
		} else {
			complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			complaintRemarksDataTable.setIsActive(Constants.U);
			complaintRemarksDataTable.setCreatedBy(sessionStateManage.getUserName());
			complaintRemarksDataTable.setCreatedDate(new Date());

		}
		lstComplaintRemarksDataTables.add(complaintRemarksDataTable);

		if (getComplaintRemarksId() == null) {
			lstComplaintRemarksNewDataTables.add(complaintRemarksDataTable);
		}
		setBooComplaintRemarksDataTable(true);
		setBooComplaintRemarksDetail(true);

		clearAll();

	}

	public void saveAllComplaintRemarksMethod() {

		for (ComplaintRemarksDataTable complaintRemarksDataTable : lstComplaintRemarksDataTables) {

			ComplaintRemarksMaster complaintRemarksMaster = new ComplaintRemarksMaster();
			complaintRemarksMaster.setComplaintRemarksId(complaintRemarksDataTable.getComplaintRemarksId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(complaintRemarksDataTable.getAppCountryId());
			complaintRemarksMaster.setAppCountryId(countryMaster);

			complaintRemarksMaster.setComplaintRemarksCode(complaintRemarksDataTable.getComplaintRemarksCode());
			complaintRemarksMaster.setTagBenificiary(complaintRemarksDataTable.getTagBenificiary());
			complaintRemarksMaster.setTagRemittance(complaintRemarksDataTable.getTagRemittance());

			complaintRemarksMaster.setCreatedBy(complaintRemarksDataTable.getCreatedBy());
			complaintRemarksMaster.setCreatedDate(complaintRemarksDataTable.getCreatedDate());
			complaintRemarksMaster.setModifiedBy(complaintRemarksDataTable.getModifiedBy());
			complaintRemarksMaster.setModifiedDate(complaintRemarksDataTable.getModifiedDate());
			complaintRemarksMaster.setApprovedBy(complaintRemarksDataTable.getApprovedBy());
			complaintRemarksMaster.setApprovedDate(complaintRemarksDataTable.getApprovedDate());
			complaintRemarksMaster.setIsActive(complaintRemarksDataTable.getIsActive());
			complaintRemarksMaster.setRemarks(complaintRemarksDataTable.getRemarks());

			try {

				getComplaintRemarksService().saveComplaintRemarksMethod(complaintRemarksMaster);

			} catch (Exception e) {
				// TODO: handle exception
			}

			ComplaintRemarksDesc complaintRemarksEnglishDesc = new ComplaintRemarksDesc();

			LanguageType englishLanguageRemarks = new LanguageType();
			englishLanguageRemarks.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			complaintRemarksEnglishDesc.setLanguageId(englishLanguageRemarks);
			complaintRemarksEnglishDesc.setFullDesc(complaintRemarksDataTable.getFullEngComplaintRemarksDesc());
			complaintRemarksEnglishDesc.setShortDesc(complaintRemarksDataTable.getShortEngComplaintRemarksDesc());
			complaintRemarksEnglishDesc.setComplaintRemarksDescId(complaintRemarksDataTable.getEngComplaintRemarksDescId());
			complaintRemarksEnglishDesc.setComplaintRemarks(complaintRemarksMaster);

			try {

				getComplaintRemarksService().saveComplaintRemarksDescMethod(complaintRemarksEnglishDesc);
			} catch (Exception e) {
				// TODO: handle exception
			}

			ComplaintRemarksDesc complaintRemarksArbicDesc = new ComplaintRemarksDesc();

			LanguageType arabicLanguageRemarks = new LanguageType();
			arabicLanguageRemarks.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			complaintRemarksArbicDesc.setLanguageId(arabicLanguageRemarks);

			complaintRemarksArbicDesc.setFullDesc(complaintRemarksDataTable.getFullLocalComplaintRemarksDesc());
			complaintRemarksArbicDesc.setShortDesc(complaintRemarksDataTable.getShortLocalComplaintRemarksDesc());
			complaintRemarksArbicDesc.setComplaintRemarksDescId(complaintRemarksDataTable.getLocalComplaintRemarksDescId());
			complaintRemarksArbicDesc.setComplaintRemarks(complaintRemarksMaster);

			try {

				getComplaintRemarksService().saveComplaintRemarksDescMethod(complaintRemarksArbicDesc);

			} catch (Exception e) {
				// TODO: handle exception
			}
			RequestContext.getCurrentInstance().execute("complete.show();");

		}

	}

	public void viewComplaintRemarksMethod() {

		Boolean childRecordCheck = false;
		lstComplaintRemarksDataTables.clear();
		clearAll();
		lstofComplaintRemarks = getComplaintRemarksService().getAllComplaintRemarks(sessionStateManage.getCountryId());

		if (lstofComplaintRemarks != null) {
			setBooComplaintRemarksDataTable(true);
			setBooComplaintRemarksDetail(true);

			for (ComplaintRemarksMaster complaintRemarksMaster : lstofComplaintRemarks) {
				ComplaintRemarksDataTable complaintRemarksDataTable = new ComplaintRemarksDataTable();

				complaintRemarksDataTable.setComplaintRemarksId(complaintRemarksMaster.getComplaintRemarksId());
				complaintRemarksDataTable.setComplaintRemarksCode(complaintRemarksMaster.getComplaintRemarksCode());

				if (complaintRemarksMaster.getTagBenificiary() != null) {
					if (complaintRemarksMaster.getTagBenificiary().equals(Constants.Yes)) {
						complaintRemarksDataTable.setTagBenificiary(Constants.Yes);
						complaintRemarksDataTable.setTagBenificiaryName(Constants.YES);
					} else if (complaintRemarksMaster.getTagBenificiary().equals(Constants.No)) {
						complaintRemarksDataTable.setTagBenificiary(Constants.No);
						complaintRemarksDataTable.setTagBenificiaryName(Constants.NO);
					}
				}

				if (complaintRemarksMaster.getTagRemittance() != null) {
					if (complaintRemarksMaster.getTagRemittance().equals(Constants.Yes)) {
						complaintRemarksDataTable.setTagRemittance(Constants.Yes);
						complaintRemarksDataTable.setTagRemittanceName(Constants.YES);
					} else if (complaintRemarksMaster.getTagRemittance().equals(Constants.No)) {
						complaintRemarksDataTable.setTagRemittance(Constants.No);
						complaintRemarksDataTable.setTagRemittanceName(Constants.NO);
					}
				}

				complaintRemarksDataTable.setAppCountryId(complaintRemarksMaster.getAppCountryId().getCountryId());

				complaintRemarksDataTable.setModifiedBy(complaintRemarksMaster.getModifiedBy());
				complaintRemarksDataTable.setModifiedDate(complaintRemarksMaster.getModifiedDate());
				complaintRemarksDataTable.setCreatedBy(complaintRemarksMaster.getCreatedBy());
				complaintRemarksDataTable.setCreatedDate(complaintRemarksMaster.getCreatedDate());
				complaintRemarksDataTable.setApprovedBy(complaintRemarksMaster.getApprovedBy());
				complaintRemarksDataTable.setApprovedDate(complaintRemarksMaster.getApprovedDate());
				complaintRemarksDataTable.setIsActive(complaintRemarksMaster.getIsActive());

				if (complaintRemarksDataTable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				} else if (complaintRemarksDataTable.getIsActive().equalsIgnoreCase(Constants.D)) {
					complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				} else if (complaintRemarksDataTable.getIsActive().equalsIgnoreCase(Constants.U) && complaintRemarksDataTable.getModifiedBy() == null && complaintRemarksDataTable.getModifiedDate() == null && complaintRemarksDataTable.getApprovedBy() == null
						&& complaintRemarksDataTable.getApprovedDate() == null && complaintRemarksDataTable.getRemarks() == null) {
					complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				} else {
					complaintRemarksDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}

				lstofComplaintRemarksDesc = getComplaintRemarksService().getAllComplaintRemarksDesc(complaintRemarksMaster.getComplaintRemarksId());
				if (lstofComplaintRemarksDesc != null) {
					for (ComplaintRemarksDesc complaintRemarksDesc : lstofComplaintRemarksDesc) {
						childRecordCheck = true;
						if (complaintRemarksDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							complaintRemarksDataTable.setEnglishLanguageId(complaintRemarksDesc.getLanguageId().getLanguageId());
							complaintRemarksDataTable.setFullEngComplaintRemarksDesc(complaintRemarksDesc.getFullDesc());
							complaintRemarksDataTable.setShortEngComplaintRemarksDesc(complaintRemarksDesc.getShortDesc());
							complaintRemarksDataTable.setEngComplaintRemarksDescId(complaintRemarksDesc.getComplaintRemarksDescId());

						} else if (complaintRemarksDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							complaintRemarksDataTable.setLocalLanguageId(complaintRemarksDesc.getLanguageId().getLanguageId());
							complaintRemarksDataTable.setFullLocalComplaintRemarksDesc(complaintRemarksDesc.getFullDesc());
							complaintRemarksDataTable.setShortLocalComplaintRemarksDesc(complaintRemarksDesc.getShortDesc());
							complaintRemarksDataTable.setLocalComplaintRemarksDescId(complaintRemarksDesc.getComplaintRemarksDescId());

						}

					}
				} else {
					childRecordCheck = false;
				}

				if (childRecordCheck == true) {
					lstComplaintRemarksDataTables.add(complaintRemarksDataTable);
				}
			}

			lstComplaintRemarksDataTables.addAll(lstComplaintRemarksNewDataTables);
			clearAll();

		} else {
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			return;
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../complaint/complaintremarksmaster.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void editComplaintRemarksMethod(ComplaintRemarksDataTable complaintRemarksDataTable) {

		setComplaintRemarksId(complaintRemarksDataTable.getComplaintRemarksId());
		setAppCountryId(complaintRemarksDataTable.getAppCountryId());
		setComplaintRemarksCode(complaintRemarksDataTable.getComplaintRemarksCode());

		if (complaintRemarksDataTable.getTagBenificiary() != null) {
			if (complaintRemarksDataTable.getTagBenificiary().equals(Constants.Yes)) {
				setTagBenificiary(Constants.Yes);
			} else if (complaintRemarksDataTable.getTagBenificiary().equals(Constants.No)) {
				setTagBenificiary(Constants.No);
			}
		}

		if (complaintRemarksDataTable.getTagRemittance() != null) {
			if (complaintRemarksDataTable.getTagRemittance().equals(Constants.Yes)) {
				setTagRemittance(Constants.Yes);
			} else if (complaintRemarksDataTable.getTagRemittance().equals(Constants.No)) {
				setTagRemittance(Constants.No);
			}
		}

		setCreatedBy(complaintRemarksDataTable.getCreatedBy());
		setCreatedDate(complaintRemarksDataTable.getCreatedDate());
		setModifiedBy(complaintRemarksDataTable.getModifiedBy());
		setModifiedDate(complaintRemarksDataTable.getModifiedDate());
		setApprovedBy(complaintRemarksDataTable.getApprovedBy());
		setApprovedDate(complaintRemarksDataTable.getApprovedDate());
		setIsActive(complaintRemarksDataTable.getIsActive());
		setRemarks(complaintRemarksDataTable.getRemarks());

		setEngComplaintRemarksDescId(complaintRemarksDataTable.getEngComplaintRemarksDescId());
		setEnglishLanguageId(complaintRemarksDataTable.getEnglishLanguageId());
		setFullEngComplaintRemarksDesc(complaintRemarksDataTable.getFullEngComplaintRemarksDesc());
		setShortEngComplaintRemarksDesc(complaintRemarksDataTable.getShortEngComplaintRemarksDesc());

		setDynamicLabelForActivateDeactivate(complaintRemarksDataTable.getDynamicLabelForActivateDeactivate());

		setLocalComplaintRemarksDescId(complaintRemarksDataTable.getLocalComplaintRemarksDescId());
		setLocalLanguageId(complaintRemarksDataTable.getLocalLanguageId());
		setFullLocalComplaintRemarksDesc(complaintRemarksDataTable.getFullLocalComplaintRemarksDesc());
		setShortLocalComplaintRemarksDesc(complaintRemarksDataTable.getShortLocalComplaintRemarksDesc());
		setIfEditClicked(true);
		lstComplaintRemarksDataTables.remove(complaintRemarksDataTable);
		setBooEdit(true);
		setBtnClear(true);
		setIsdisable(true);

	}

	public void approveViewComplaintRemarksMethod() {
		lstComplaintRemarksDataTables.clear();

		lstofComplaintRemarks = getComplaintRemarksService().getComplaintRemarksForApprove(sessionStateManage.getCountryId(), Constants.U);

		if (lstofComplaintRemarks != null) {

			for (ComplaintRemarksMaster complaintRemarksMaster : lstofComplaintRemarks) {

				ComplaintRemarksDataTable complaintRemarksDataTable = new ComplaintRemarksDataTable();

				complaintRemarksDataTable.setComplaintRemarksId(complaintRemarksMaster.getComplaintRemarksId());
				complaintRemarksDataTable.setComplaintRemarksCode(complaintRemarksMaster.getComplaintRemarksCode());

				if (complaintRemarksMaster.getTagBenificiary() != null) {
					if (complaintRemarksMaster.getTagBenificiary().equals(Constants.Yes)) {
						complaintRemarksDataTable.setTagBenificiary(Constants.Yes);
						complaintRemarksDataTable.setTagBenificiaryName(Constants.YES);
					} else if (complaintRemarksMaster.getTagBenificiary().equals(Constants.No)) {
						complaintRemarksDataTable.setTagBenificiary(Constants.No);
						complaintRemarksDataTable.setTagBenificiaryName(Constants.NO);
					}
				}

				if (complaintRemarksMaster.getTagRemittance() != null) {
					if (complaintRemarksMaster.getTagRemittance().equals(Constants.Yes)) {
						complaintRemarksDataTable.setTagRemittance(Constants.Yes);
						complaintRemarksDataTable.setTagRemittanceName(Constants.YES);
					} else if (complaintRemarksMaster.getTagRemittance().equals(Constants.No)) {
						complaintRemarksDataTable.setTagRemittance(Constants.No);
						complaintRemarksDataTable.setTagRemittanceName(Constants.NO);
					}
				}

				complaintRemarksDataTable.setAppCountryId(complaintRemarksMaster.getAppCountryId().getCountryId());

				complaintRemarksDataTable.setModifiedBy(complaintRemarksMaster.getModifiedBy());
				complaintRemarksDataTable.setModifiedDate(complaintRemarksMaster.getModifiedDate());
				complaintRemarksDataTable.setCreatedBy(complaintRemarksMaster.getCreatedBy());
				complaintRemarksDataTable.setCreatedDate(complaintRemarksMaster.getCreatedDate());
				complaintRemarksDataTable.setApprovedBy(complaintRemarksMaster.getApprovedBy());
				complaintRemarksDataTable.setApprovedDate(complaintRemarksMaster.getApprovedDate());
				complaintRemarksDataTable.setIsActive(complaintRemarksMaster.getIsActive());

				complaintRemarksDataTable.setRemarks(complaintRemarksMaster.getRemarks());

				lstofComplaintRemarksDesc = getComplaintRemarksService().getAllComplaintRemarksDesc(complaintRemarksMaster.getComplaintRemarksId());
				if (lstofComplaintRemarksDesc != null) {
					for (ComplaintRemarksDesc complaintRemarksDesc : lstofComplaintRemarksDesc) {

						if (complaintRemarksDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							complaintRemarksDataTable.setEnglishLanguageId(complaintRemarksDesc.getLanguageId().getLanguageId());
							complaintRemarksDataTable.setFullEngComplaintRemarksDesc(complaintRemarksDesc.getFullDesc());
							complaintRemarksDataTable.setShortEngComplaintRemarksDesc(complaintRemarksDesc.getShortDesc());
							complaintRemarksDataTable.setEngComplaintRemarksDescId(complaintRemarksDesc.getComplaintRemarksDescId());

						} else if (complaintRemarksDesc.getLanguageId().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							complaintRemarksDataTable.setLocalLanguageId(complaintRemarksDesc.getLanguageId().getLanguageId());
							complaintRemarksDataTable.setFullLocalComplaintRemarksDesc(complaintRemarksDesc.getFullDesc());
							complaintRemarksDataTable.setShortLocalComplaintRemarksDesc(complaintRemarksDesc.getShortDesc());
							complaintRemarksDataTable.setLocalComplaintRemarksDescId(complaintRemarksDesc.getComplaintRemarksDescId());

						}

					}
				}

				lstComplaintRemarksDataTables.add(complaintRemarksDataTable);
				setBooComplaintRemarksDataTable(true);
				setBooComplaintRemarksDetail(false);

				clearAll();

			}
		}

	}

	public void editComplaintRemarksForApproveMethod(ComplaintRemarksDataTable complaintRemarksDataTable) {

		if ((complaintRemarksDataTable.getModifiedBy() == null ? complaintRemarksDataTable.getCreatedBy() : complaintRemarksDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");
		} else {

			setComplaintRemarksId(complaintRemarksDataTable.getComplaintRemarksId());
			setAppCountryId(complaintRemarksDataTable.getAppCountryId());
			setComplaintRemarksCode(complaintRemarksDataTable.getComplaintRemarksCode());

			if (complaintRemarksDataTable.getTagBenificiary() != null) {
				if (complaintRemarksDataTable.getTagBenificiary().equals(Constants.Yes)) {
					setTagBenificiary(Constants.Yes);
				} else if (complaintRemarksDataTable.getTagBenificiary().equals(Constants.No)) {
					setTagBenificiary(Constants.No);
				}
			}

			if (complaintRemarksDataTable.getTagRemittance() != null) {
				if (complaintRemarksDataTable.getTagRemittance().equals(Constants.Yes)) {
					setTagRemittance(Constants.Yes);
				} else if (complaintRemarksDataTable.getTagRemittance().equals(Constants.No)) {
					setTagRemittance(Constants.No);
				}
			}

			setCreatedBy(complaintRemarksDataTable.getCreatedBy());
			setCreatedDate(complaintRemarksDataTable.getCreatedDate());
			setModifiedBy(complaintRemarksDataTable.getModifiedBy());
			setModifiedDate(complaintRemarksDataTable.getModifiedDate());
			setApprovedBy(complaintRemarksDataTable.getApprovedBy());
			setApprovedDate(complaintRemarksDataTable.getApprovedDate());
			setIsActive(complaintRemarksDataTable.getIsActive());
			setRemarks(complaintRemarksDataTable.getRemarks());

			setEngComplaintRemarksDescId(complaintRemarksDataTable.getEngComplaintRemarksDescId());
			setEnglishLanguageId(complaintRemarksDataTable.getEnglishLanguageId());
			setFullEngComplaintRemarksDesc(complaintRemarksDataTable.getFullEngComplaintRemarksDesc());
			setShortEngComplaintRemarksDesc(complaintRemarksDataTable.getShortEngComplaintRemarksDesc());

			setLocalComplaintRemarksDescId(complaintRemarksDataTable.getLocalComplaintRemarksDescId());
			setLocalLanguageId(complaintRemarksDataTable.getLocalLanguageId());
			setFullLocalComplaintRemarksDesc(complaintRemarksDataTable.getFullLocalComplaintRemarksDesc());
			setShortLocalComplaintRemarksDesc(complaintRemarksDataTable.getShortLocalComplaintRemarksDesc());

			lstComplaintRemarksDataTables.remove(complaintRemarksDataTable);
			setBooComplaintRemarksDataTable(false);
			setBooComplaintRemarksDetail(true);
		}

	}

	public void approveComplaintRemarksMethod() {

		getComplaintRemarksService().approveRecord(getComplaintRemarksId(), sessionStateManage.getUserName(), Constants.Yes);
		RequestContext.getCurrentInstance().execute("complete.show();");

	}

	public void clearAll() {

		setAppCountryId(null);
		setComplaintRemarksCode(null);
		setComplaintRemarksDescId(null);
		setComplaintRemarksId(null);
		setComplaintRemarksDescId(null);
		setEngComplaintRemarksDescId(null);
		setLocalComplaintRemarksDescId(null);
		setTagBenificiary(null);
		setTagRemittance(null);
		setFullEngComplaintRemarksDesc(null);
		setFullLocalComplaintRemarksDesc(null);
		setShortEngComplaintRemarksDesc(null);
		setShortLocalComplaintRemarksDesc(null);
		setRemarks(null);
		setBooEdit(false);
		setBtnClear(false);
		setIsdisable(false);
		setIfEditClicked(false);

	}

	public void checkStatusType(ComplaintRemarksDataTable complaintRemarksDataTable) throws IOException {
		if (complaintRemarksDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (complaintRemarksDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {

			complaintRemarksDataTable.setRemarksCheck(true);
			setRemarks("");
			setApprovedBy(complaintRemarksDataTable.getApprovedBy());
			setApprovedDate(complaintRemarksDataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} else if (complaintRemarksDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
			complaintRemarksDataTable.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		} else if (complaintRemarksDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			complaintRemarksDataTable.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		} else if (complaintRemarksDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstComplaintRemarksDataTables.remove(complaintRemarksDataTable);
			lstComplaintRemarksNewDataTables.remove(complaintRemarksDataTable);

		}
	}

	public void confirmPermanentDelete() {
		if (lstComplaintRemarksDataTables.size() > 0) {
			for (ComplaintRemarksDataTable complaintRemarksDataTable : lstComplaintRemarksDataTables) {
				if (complaintRemarksDataTable.getBooCheckDelete() != null) {
					if (complaintRemarksDataTable.getBooCheckDelete().equals(true)) {

						delete(complaintRemarksDataTable);

					}
				}
			}
		}

	}

	public void conformToDeActivteCompliantRemarks(ComplaintRemarksDataTable complaintRemarksDataTable) {
		getComplaintRemarksService().activateComplaintRemarksMethod(complaintRemarksDataTable.getComplaintRemarksId(), sessionStateManage.getUserName());
		RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void activateRecord() {

		if (lstComplaintRemarksDataTables.size() > 0) {
			for (ComplaintRemarksDataTable complaintRemarksDataTable : lstComplaintRemarksDataTables) {
				if (complaintRemarksDataTable.getActivateRecordCheck() != null) {
					if (complaintRemarksDataTable.getActivateRecordCheck().equals(true)) {

						conformToDeActivteCompliantRemarks(complaintRemarksDataTable);

					}
				}
			}
		}

	}

	public void remarkSelectedRecord() throws IOException {

		for (ComplaintRemarksDataTable complaintRemarksDataTable : lstComplaintRemarksDataTables) {
			if (complaintRemarksDataTable.getRemarksCheck().equals(true)) {
				if (!getRemarks().equals("")) {
					complaintRemarksDataTable.setRemarks(getRemarks());
					complaintRemarksDataTable.setApprovedBy(null);
					complaintRemarksDataTable.setApprovedDate(null);
					complaintRemarksDataTable.setRemarksCheck(true);
					update(complaintRemarksDataTable);
					lstComplaintRemarksDataTables.clear();

				} else {
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;

				}
			}

		}

	}

	public void delete(ComplaintRemarksDataTable complaintRemarksDataTable) {

		getComplaintRemarksService().deleteComplaintRemarksMethod(complaintRemarksDataTable.getComplaintRemarksId(), complaintRemarksDataTable.getEngComplaintRemarksDescId(), complaintRemarksDataTable.getLocalComplaintRemarksDescId());

		RequestContext.getCurrentInstance().execute("update.show();");
	}

	public void update(ComplaintRemarksDataTable complaintRemarksDataTable) throws IOException {

		try {

			ComplaintRemarksMaster complaintRemarksMaster = new ComplaintRemarksMaster();

			complaintRemarksMaster.setComplaintRemarksId(complaintRemarksDataTable.getComplaintRemarksId());
			complaintRemarksMaster.setComplaintRemarksCode(complaintRemarksDataTable.getComplaintRemarksCode());
			complaintRemarksMaster.setTagBenificiary(complaintRemarksDataTable.getTagBenificiary());
			complaintRemarksMaster.setTagRemittance(complaintRemarksDataTable.getTagRemittance());
			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(complaintRemarksDataTable.getAppCountryId());
			complaintRemarksMaster.setAppCountryId(countryMaster);

			complaintRemarksMaster.setCreatedBy(complaintRemarksDataTable.getCreatedBy());
			complaintRemarksMaster.setCreatedDate(complaintRemarksDataTable.getCreatedDate());
			complaintRemarksMaster.setRemarks(complaintRemarksMaster.getRemarks());

			complaintRemarksMaster.setModifiedBy(sessionStateManage.getUserName());
			complaintRemarksMaster.setModifiedDate(new Date());
			complaintRemarksMaster.setApprovedBy(null);
			complaintRemarksMaster.setApprovedDate(null);
			complaintRemarksMaster.setRemarks(complaintRemarksDataTable.getRemarks());
			complaintRemarksMaster.setIsActive(Constants.D);
			complaintRemarksMaster.setCreatedBy(complaintRemarksDataTable.getCreatedBy());
			complaintRemarksMaster.setCreatedDate(complaintRemarksDataTable.getCreatedDate());

			getComplaintRemarksService().saveComplaintRemarksMethod(complaintRemarksMaster);

			RequestContext.getCurrentInstance().execute("update.show();");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {
		log.info("Enter in exit method ");

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			lstComplaintRemarksDataTables.clear();
			lstComplaintRemarksNewDataTables.clear();
			setBooComplaintRemarksDataTable(false);
			setBooComplaintRemarksDetail(false);
			clearAll();

		} catch (Exception e) {
			log.info("Problem to redirect:" + e);
		}

		log.info("Exit from exit method ");
	}

	public List<String> autoComplete(String query) {
		if (query.length() > 0) {
			return getComplaintRemarksService().autoCompleteList(query);
		} else {
			return null;
		}
	}

	public void autoCompletePopulateValue() {

		lstofComplaintRemarks = getComplaintRemarksService().getComplaintRemarksBasedOnComplaintCode(getComplaintRemarksCode());

		if (lstofComplaintRemarks != null) {
			setComplaintRemarksCode(null);

			RequestContext.getCurrentInstance().execute("codeExist.show();");

		} else {

			setFullEngComplaintRemarksDesc(null);
			setFullLocalComplaintRemarksDesc(null);
			setShortEngComplaintRemarksDesc(null);
			setShortLocalComplaintRemarksDesc(null);
			setTagBenificiary(null);
			setTagRemittance(null);
			setRemarks(null);
		}

	}

}
