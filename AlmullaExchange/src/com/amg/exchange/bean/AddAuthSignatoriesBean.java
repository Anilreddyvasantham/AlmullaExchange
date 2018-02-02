package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.amg.exchange.common.model.BizComponentDataDesc;

   

public class AddAuthSignatoriesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String sidno;

	private String sidtype;

	private String sidExpDate;

	private String effDate;

	private String endDate;
	
	private Part spart;
	
    private Boolean checked;
	  
	private boolean modified;
		
	private boolean objStatus;
	
	private BigDecimal sIdTypeId;
	
	private BigDecimal customerIdProofId = null;
	private BigDecimal ownerCustomerId;
	
	private List<BizComponentDataDesc> fsBizComponentDataDescList = new ArrayList<BizComponentDataDesc>();
	private List<String> images = new ArrayList<String>();
	private StreamedContent downloadFile;

	private UploadedFile file;
	private BigDecimal imageId;
	
	   

	public BigDecimal getOwnerCustomerId() {
		return ownerCustomerId;
	}

	public void setOwnerCustomerId(BigDecimal ownerCustomerId) {
		this.ownerCustomerId = ownerCustomerId;
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

	public AddAuthSignatoriesBean() {
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

	public String getSidno() {
		return sidno;
	}

	public void setSidno(String sidno) {
		this.sidno = sidno;
	}

	public String getSidtype() {
		return sidtype;
	}

	public void setSidtype(String sidtype) {
		this.sidtype = sidtype;
	}

	public String getSidExpDate() {
		return sidExpDate;
	}

	public void setSidExpDate(String sidExpDate) {
		this.sidExpDate = sidExpDate;
	}

	public Part getSpart() {
		return spart;
	}

	public void setSpart(Part spart) {
		this.spart = spart;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public String getEffDate() {
		return effDate;
	}

	public void setEffDate(String effDate) {
		this.effDate = effDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	
	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public AddAuthSignatoriesBean(String name, String sidno, String sidtype, String effDate, String endDate, String sidExpDate,boolean modified,boolean objStatus,BigDecimal sIdTypeId,BigDecimal imageId){
		
		this.name = name;
		this.sidno = sidno;
		this.sidtype = sidtype;
		this.effDate = effDate;
		this.endDate = endDate;
		this.sidExpDate = sidExpDate;
		this.modified = modified;
		this.objStatus = objStatus;
		this.sIdTypeId = sIdTypeId;
		this.imageId= imageId;
		
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

	public BigDecimal getsIdTypeId() {
		return sIdTypeId;
	}

	public void setsIdTypeId(BigDecimal sIdTypeId) {
		this.sIdTypeId = sIdTypeId;
	}

	public List<BizComponentDataDesc> getFsBizComponentDataDescList() {
		return fsBizComponentDataDescList;
	}

	public void setFsBizComponentDataDescList(
			List<BizComponentDataDesc> fsBizComponentDataDescList) {
		this.fsBizComponentDataDescList = fsBizComponentDataDescList;
	}

	
	
}
