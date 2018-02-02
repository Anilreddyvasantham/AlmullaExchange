package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.amg.exchange.common.model.BizComponentDataDesc;



public class AddPartnerDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String pidno;

	private  String pidtype;

	private String pidExpDate;

	private String partName;
	
	private String partNameLocal;

	private Boolean checked;
	
	private boolean modified;
	
	private boolean objStatus;
	
	private BigDecimal pIdTypeId;
	
	private BigDecimal customerIdProofId = null;
	
	private BigDecimal partnerCustomerId;
	
	private BigDecimal patnerPercentage;
	
    private Date effectiveDate;
  
    private Date validUpTo;
  
    private Boolean checkedPartner = false;
    
    private Boolean checkedAuthorized = false;
    
    private Boolean transactionBehalf = false;
    
    private Boolean editablePartner = false;
    
	private Boolean notEditablePartner = true;
    
    private Boolean editableAuthorized = false;
    
    private Boolean notEditableAuthorized = true;
    
    private String effectiveDateString;
    
    private String validUpToString;
    
    private String partnerContactNumber;
    
    private String partnerEmail;
    
    private Boolean ispartnerMobileAlreadyExist;
    
    private Boolean ispartneremailAlreadyExist;
    
    private Boolean highLightCustomer=false; 
    

	private List<BizComponentDataDesc> fsBizComponentDataDescList = new ArrayList<BizComponentDataDesc>();
	private BigDecimal imageId;
	private List<String> images = new ArrayList<String>();
	private StreamedContent downloadFile;

	private UploadedFile file;
	private String occupation;
	
	private String createdBy;
	private Date createdDate;
	
	
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

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Boolean getIspartnerMobileAlreadyExist() {
		return ispartnerMobileAlreadyExist;
	}

	public void setIspartnerMobileAlreadyExist(Boolean ispartnerMobileAlreadyExist) {
		this.ispartnerMobileAlreadyExist = ispartnerMobileAlreadyExist;
	}

	public Boolean getIspartneremailAlreadyExist() {
		return ispartneremailAlreadyExist;
	}

	public void setIspartneremailAlreadyExist(Boolean ispartneremailAlreadyExist) {
		this.ispartneremailAlreadyExist = ispartneremailAlreadyExist;
	}

	public BigDecimal getCustomerIdProofId() {
		return customerIdProofId;
	}

	public BigDecimal getPartnerCustomerId() {
		return partnerCustomerId;
	}

	public void setPartnerCustomerId(BigDecimal partnerCustomerId) {
		this.partnerCustomerId = partnerCustomerId;
	}

	public void setCustomerIdProofId(BigDecimal customerIdProofId) {
		this.customerIdProofId = customerIdProofId;
	}

	public AddPartnerDetailBean() {
		
	}

	public String getPidno() {
		return pidno;
	}

	public void setPidno(String pidno) {
		this.pidno = pidno;
	}

	public String getPidtype() {
		return pidtype;
	}

	public void setPidtype(String pidtype) {
		this.pidtype = pidtype;
	}

	public String getPidExpDate() {
		return pidExpDate;
	}

	public void setPidExpDate(String pidExpDate) {
		this.pidExpDate = pidExpDate;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	
	public String getPartNameLocal() {
		return partNameLocal;
	}

	public void setPartNameLocal(String partNameLocal) {
		this.partNameLocal = partNameLocal;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	
	
	
	public BigDecimal getPatnerPercentage() {
		return patnerPercentage;
	}

	public void setPatnerPercentage(BigDecimal patnerPercentage) {
		this.patnerPercentage = patnerPercentage;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getValidUpTo() {
		return validUpTo;
	}

	public void setValidUpTo(Date validUpTo) {
		this.validUpTo = validUpTo;
	}

	public Boolean getCheckedPartner() {
		return checkedPartner;
	}

	public void setCheckedPartner(Boolean checkedPartner) {
		this.checkedPartner = checkedPartner;
	}

	public Boolean getCheckedAuthorized() {
		return checkedAuthorized;
	}

	public void setCheckedAuthorized(Boolean checkedAuthorized) {
		this.checkedAuthorized = checkedAuthorized;
	}

	public Boolean getTransactionBehalf() {
		return transactionBehalf;
	}

	public void setTransactionBehalf(Boolean transactionBehalf) {
		this.transactionBehalf = transactionBehalf;
	}

	
	public Boolean getEditablePartner() {
		return editablePartner;
	}

	public void setEditablePartner(Boolean editablePartner) {
		this.editablePartner = editablePartner;
	}

	public Boolean getEditableAuthorized() {
		return editableAuthorized;
	}

	public void setEditableAuthorized(Boolean editableAuthorized) {
		this.editableAuthorized = editableAuthorized;
	}

	
	
	public Boolean getNotEditablePartner() {
		return notEditablePartner;
	}

	public void setNotEditablePartner(Boolean notEditablePartner) {
		this.notEditablePartner = notEditablePartner;
	}

	public Boolean getNotEditableAuthorized() {
		return notEditableAuthorized;
	}

	public void setNotEditableAuthorized(Boolean notEditableAuthorized) {
		this.notEditableAuthorized = notEditableAuthorized;
	}

	
	public String getEffectiveDateString() {
		return effectiveDateString;
	}

	public void setEffectiveDateString(String effectiveDateString) {
		this.effectiveDateString = effectiveDateString;
	}

	public String getValidUpToString() {
		return validUpToString;
	}

	public void setValidUpToString(String validUpToString) {
		this.validUpToString = validUpToString;
	}

	public AddPartnerDetailBean(String partName, String partNameLocal,String pidno, String pIdType,
			String pidExpDate,boolean modified, boolean objStatus, BigDecimal pIdTypeId,BigDecimal imageId) {

		this.partName = partName;
		this.partNameLocal = partNameLocal;
		this.pidno = pidno;
		this.pidtype = pIdType;
		this.pidExpDate = pidExpDate;
        this.modified  = modified;
        this.objStatus = objStatus;
        this.pIdTypeId = pIdTypeId;
        this.imageId=imageId;
       
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isObjStatus() {
		return objStatus;
	}

	public void setObjStatus(boolean objStatus) {
		this.objStatus = objStatus;
	}

	public BigDecimal getpIdTypeId() {
		return pIdTypeId;
	}

	public void setpIdTypeId(BigDecimal pIdTypeId) {
		this.pIdTypeId = pIdTypeId;
	}

	
	//image upload

	public List<BizComponentDataDesc> getFsBizComponentDataDescList() {
		return fsBizComponentDataDescList;
	}

	public void setFsBizComponentDataDescList(
			List<BizComponentDataDesc> fsBizComponentDataDescList) {
		this.fsBizComponentDataDescList = fsBizComponentDataDescList;
	}

	public BigDecimal getImageId() {
		return imageId;
	}

	public void setImageId(BigDecimal imageId) {
		this.imageId = imageId;
	}

	public List<String> getImages() {
		return images;
	}

	public void setImages(List<String> images) {
		this.images = images;
	}

	public StreamedContent getDownloadFile() {
		return downloadFile;
	}

	public void setDownloadFile(StreamedContent downloadFile) {
		this.downloadFile = downloadFile;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getPartnerContactNumber() {
		return partnerContactNumber;
	}

	public void setPartnerContactNumber(String partnerContactNumber) {
		this.partnerContactNumber = partnerContactNumber;
	}

	public String getPartnerEmail() {
		return partnerEmail;
	}

	public void setPartnerEmail(String partnerEmail) {
		this.partnerEmail = partnerEmail;
	}

	public Boolean getHighLightCustomer() {
		return highLightCustomer;
	}

	public void setHighLightCustomer(Boolean highLightCustomer) {
		this.highLightCustomer = highLightCustomer;
	}
	
	
	
}
