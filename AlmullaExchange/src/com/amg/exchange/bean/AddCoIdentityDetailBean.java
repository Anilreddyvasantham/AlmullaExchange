package com.amg.exchange.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Part;
import javax.sql.rowset.serial.SerialException;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.amg.exchange.common.model.BizComponentDataDesc;



public class AddCoIdentityDetailBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private String idno;

	private String idtype;

	private String idExpDate;

	private Part part;

	private Boolean checked;

	private boolean modified;

	private boolean objStatus;

	private BigDecimal idTypeId;

	private BigDecimal customerIdProofId;

	private BigDecimal imageId;
	
	 private Boolean renderReScan=false;

	private List<BizComponentDataDesc> fsBizComponentDataDescList = new ArrayList<BizComponentDataDesc>();

	private List<String> images = new ArrayList<String>();
	private StreamedContent downloadFile;

	private UploadedFile file;

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isObjstatus() {
		return objStatus;
	}

	public void setObjstatus(boolean objstatus) {
		this.objStatus = objstatus;
	}

	public BigDecimal getCustomerIdProofId() {
		return customerIdProofId;
	}

	public void setCustomerIdProofId(BigDecimal customerIdProofId) {
		this.customerIdProofId = customerIdProofId;
	}

	public String getIdno() {
		return idno;
	}

	public void setIdno(String idno) {
		this.idno = idno;
	}

	public String getIdtype() {
		return idtype;
	}

	public void setIdtype(String idtype) {
		this.idtype = idtype;
	}

	public String getIdExpDate() {
		return idExpDate;
	}

	public void setIdExpDate(String idExpDate) {
		this.idExpDate = idExpDate;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public AddCoIdentityDetailBean() {

	}

	public AddCoIdentityDetailBean(String idno, String idtype,
			String idExpDate, boolean modified, boolean objStatus,
			BigDecimal idTypeId, BigDecimal imgId) {

		this.idno = idno;
		this.idtype = idtype;
		this.idExpDate = idExpDate;
		// this.part = part;
		this.modified = modified;
		this.idTypeId = idTypeId;
		this.objStatus = objStatus;
		//this.file = file;
		this.imageId = imgId;
	}

	public Part getPart() {
		return part;
	}

	public void setPart(Part part) {
		this.part = part;
	}

	public BigDecimal getIdTypeId() {
		return idTypeId;
	}

	public void setIdTypeId(BigDecimal idTypeId) {
		this.idTypeId = idTypeId;
	}

	public Blob storeImage() throws IOException, SerialException, SQLException {

		InputStream stream = null;

		try {
			stream = file.getInputstream();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new javax.sql.rowset.serial.SerialBlob(readFully(stream));
	}

	public byte[] readFully(InputStream input) throws IOException {
		byte[] buffer = new byte[8192];
		int bytesRead;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		while ((bytesRead = input.read(buffer)) != -1) {
			output.write(buffer, 0, bytesRead);
		}
		return output.toByteArray();
	}

	// Getter method
	public UploadedFile getFile() {
		return file;
	}

	// Setter method
	public List<String> getImages() {
		return images;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public StreamedContent downloadFile(AddCoIdentityDetailBean bean)
			throws IOException, SQLException {
		
		return downloadFile;
	}


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

	public Boolean getRenderReScan() {
		return renderReScan;
	}

	public void setRenderReScan(Boolean renderReScan) {
		this.renderReScan = renderReScan;
	}

	
}
