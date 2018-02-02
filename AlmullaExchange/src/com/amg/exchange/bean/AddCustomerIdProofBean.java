package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

public class AddCustomerIdProofBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idForName;
    private String idTypeName;
    private String idNumber;
    private String idExpiryDate;
    private Boolean checked = false;
    
    private BigDecimal idForId; 
    private BigDecimal idTypeId;
    
    private String createdByIdProof;
    private Date createdDateIdProof;
    
    private Blob downloadFile;
    private BigDecimal imageId;
    private BigDecimal customerIdProofId = null;
    //TODO
    
	public String getIdForName() {
		return idForName;
	}
	public String getCreatedByIdProof() {
		return createdByIdProof;
	}
	public void setCreatedByIdProof(String createdByIdProof) {
		this.createdByIdProof = createdByIdProof;
	}
	public Date getCreatedDateIdProof() {
		return createdDateIdProof;
	}
	public void setCreatedDateIdProof(Date createdDateIdProof) {
		this.createdDateIdProof = createdDateIdProof;
	}
	public BigDecimal getIdForId() {
		return idForId;
	}
	public void setIdForId(BigDecimal idForId) {
		this.idForId = idForId;
	}
	public BigDecimal getIdTypeId() {
		return idTypeId;
	}
	public void setIdTypeId(BigDecimal idTypeId) {
		this.idTypeId = idTypeId;
	}
	public void setIdForName(String idForName) {
		this.idForName = idForName;
	}
	public String getIdTypeName() {
		return idTypeName;
	}
	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIdExpiryDate() {
		return idExpiryDate;
	}
	public void setIdExpiryDate(String idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Blob getDownloadFile() {
		return downloadFile;
	}
	public void setDownloadFile(Blob downloadFile) {
		this.downloadFile = downloadFile;
	}
	public BigDecimal getImageId() {
		return imageId;
	}
	public void setImageId(BigDecimal imageId) {
		this.imageId = imageId;
	}
	public BigDecimal getCustomerIdProofId() {
		return customerIdProofId;
	}
	public void setCustomerIdProofId(BigDecimal customerIdProofId) {
		this.customerIdProofId = customerIdProofId;
	}
    

}
