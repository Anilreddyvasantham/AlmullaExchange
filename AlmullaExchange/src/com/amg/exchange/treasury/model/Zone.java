package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.LanguageType;
@Entity
@Table(name ="EX_ZONE")
public class Zone implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal zoneId;
	private String zoneCode;
	private String isactive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	/*private LanguageType fsLanguageType;*/
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private List<ZoneMasterDesc> zoneMasterDesc=new ArrayList<ZoneMasterDesc>();
	

	public Zone(){
		
	}
	
	



	/**
	 * @param zoneId
	 * @param zoneName
	 * @param zoneDesc
	 * @param isactive
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 * @param fsLanguageType
	 * @param localZoneName
	 * @param localZoneDesc
	 */
	public Zone(BigDecimal zoneId, String isactive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String zoneCode,String approvedBy,
			Date approvedDate,List<ZoneMasterDesc> zoneMasterDesc,String remarks	) {
	
		this.zoneId = zoneId;
		
		this.isactive = isactive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.zoneCode=zoneCode;
		this.approvedBy=approvedBy;
		this.approvedDate=approvedDate;
		this.zoneMasterDesc=zoneMasterDesc;
		this.remarks=remarks;
		
	}



	@Id
	@GeneratedValue(generator="ex_zone_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_zone_seq",sequenceName="EX_ZONE_SEQ",allocationSize=1)
	@Column(name ="ZONE_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getZoneId() {
		return zoneId;
	}
	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	
	@Column(name = "ZONE_CODE")
	public String getZoneCode() {
		return zoneCode;
	}
	public void setZoneCode(String zoneCode) {
		this.zoneCode = zoneCode;
	}

	@Column(name = "ISACTIVE")
	public String getIsactive() {
		return isactive;
	}
	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	/*@ManyToOne
	@JoinColumn(name ="LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return fsLanguageType;
	}
	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}*/
	
	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "zone")
	public List<ZoneMasterDesc> getZoneMasterDesc() {
		return zoneMasterDesc;
	}

	public void setZoneMasterDesc(List<ZoneMasterDesc> zoneMasterDesc) {
		this.zoneMasterDesc = zoneMasterDesc;
	}
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}

