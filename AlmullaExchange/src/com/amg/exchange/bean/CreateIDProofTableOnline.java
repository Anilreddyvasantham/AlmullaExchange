package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import org.primefaces.model.StreamedContent;

@SuppressWarnings({ "unused", "serial" })
public class CreateIDProofTableOnline implements Serializable {
	
	 private String id_type;
	 private String id_number;
	 private String dateOfExp;
	 private String idFor;
	 private BigDecimal imageId;
	 
	 
	public CreateIDProofTableOnline() {
	}

	public CreateIDProofTableOnline(String idType, String idNumber, String date, String idFor, BigDecimal imageId){
		 this.id_type = idType;
		 this.id_number = idNumber;
		 this.dateOfExp = date;
		 this.idFor = idFor;
		 this.imageId = imageId;
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

	public String getDateOfExp() {
		return dateOfExp;
	}

	public void setDateOfExp(String dateOfExp) {
		this.dateOfExp = dateOfExp;
	}

	public String getIdFor() {
		return idFor;
	}

	public void setIdFor(String idFor) {
		this.idFor = idFor;
	}
	public BigDecimal getImageId() {
		return imageId;
	}

	public void setImageId(BigDecimal imageId) {
		this.imageId = imageId;
	}
}
