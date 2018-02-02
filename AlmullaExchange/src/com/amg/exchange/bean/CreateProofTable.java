package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.util.Date;

public class CreateProofTable implements Serializable {
		 
    private static final long serialVersionUID = 1L;
    
    private String id_for;
    private String id_type;
    private String id_number;
    private String date_expiary;
    private Boolean checked=false;
    
    private String idFor; 
    private String idType;
    
    private String saveOrUpdate;
    private int pk;
    
    private Blob downloadFile;
    private BigDecimal imageId;
    private BigDecimal customerIdProofId = null;
    
    private String createdByIdProof;
    private Date createdDateIdProof;
    private String modifiedByIdProof;
    private Date modifiedDateIdProof;
    
    private String imgDate;
    private String scanReq;
    private String isActive;
    private String scanSystem;
    private String scanImage=null;
    
    private boolean booRenderAImageView;
	private boolean booRenderDImageView;
	
	private boolean booCheckDup;
	private Boolean renderReScan=false;
	 private Boolean checkedScanned=false;
	private String existRecord;
	private Date expiaryDate;
    
    public CreateProofTable (String id_for, String id_type, String id_number, String date_exp, String saveOrUpdate, int primaryKey, 
    						String idFor, String idType) {}

	public CreateProofTable() {
	}

	public String getId_for() {
		return id_for;
	}

	public void setId_for(String id_for) {
		this.id_for = id_for;
	}

	public String getId_type() {
		return id_type;
	}

	public void setId_type(String id_type) {
		this.id_type = id_type;
	}

	public String getId_number() {
		return id_number;
	}

	public void setId_number(String id_number) {
		this.id_number = id_number;
	}

	public String getDate_expiary() {
		return date_expiary;
	}

	public void setDate_expiary(String date_expiary) {
		this.date_expiary = date_expiary;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+checked);
		this.checked = checked;
	}

	public String getSaveOrUpdate() {
		return saveOrUpdate;
	}

	public void setSaveOrUpdate(String saveOrUpdate) {
		this.saveOrUpdate = saveOrUpdate;
	}

	public int getPk() {
		return pk;
	}

	public void setPk(int pk) {
		this.pk = pk;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdFor() {
		return idFor;
	}

	public void setIdFor(String idFor) {
		this.idFor = idFor;
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

	public String getImgDate() {
		return imgDate;
	}

	public void setImgDate(String imgDate) {
		this.imgDate = imgDate;
	}

	public String getScanReq() {
		return scanReq;
	}

	public void setScanReq(String scanReq) {
		this.scanReq = scanReq;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getScanSystem() {
		return scanSystem;
	}

	public void setScanSystem(String scanSystem) {
		this.scanSystem = scanSystem;
	}

	public String getScanImage() {
		return scanImage;
	}

	public void setScanImage(String scanImage) {
		this.scanImage = scanImage;
	}
	
	public boolean isBooRenderAImageView() {
		return booRenderAImageView;
	}

	public void setBooRenderAImageView(boolean booRenderAImageView) {
		this.booRenderAImageView = booRenderAImageView;
	}

	public boolean isBooRenderDImageView() {
		return booRenderDImageView;
	}

	public void setBooRenderDImageView(boolean booRenderDImageView) {
		this.booRenderDImageView = booRenderDImageView;
	}

	public boolean isBooCheckDup() {
		return booCheckDup;
	}

	public void setBooCheckDup(boolean booCheckDup) {
		this.booCheckDup = booCheckDup;
	}

	public String getModifiedByIdProof() {
		return modifiedByIdProof;
	}

	public void setModifiedByIdProof(String modifiedByIdProof) {
		this.modifiedByIdProof = modifiedByIdProof;
	}

	public Date getModifiedDateIdProof() {
		return modifiedDateIdProof;
	}

	public void setModifiedDateIdProof(Date modifiedDateIdProof) {
		this.modifiedDateIdProof = modifiedDateIdProof;
	}

	public Boolean getRenderReScan() {
		return renderReScan;
	}

	public void setRenderReScan(Boolean renderReScan) {
		this.renderReScan = renderReScan;
	}

	public Boolean getCheckedScanned() {
		return checkedScanned;
	}

	public void setCheckedScanned(Boolean checkedScanned) {
		this.checkedScanned = checkedScanned;
	}

	public String getExistRecord() {
		return existRecord;
	}

	public void setExistRecord(String existRecord) {
		this.existRecord = existRecord;
	}

	public Date getExpiaryDate() {
		return expiaryDate;
	}

	public void setExpiaryDate(Date expiaryDate) {
		this.expiaryDate = expiaryDate;
	}
	
	
	
}
