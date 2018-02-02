package com.amg.exchange.remittance.model;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.amg.exchange.treasury.model.RemittanceModeDescription;


@Entity
@Table(name="EX_RELATIONS")
public class Relations implements Serializable {
	private static final long serialVersionUID = 2315791709068216697L;
	private BigDecimal relationsId;
	private String relationsCode;
	private String  englishRelationsTypeDesc;
	private String localRelationsTypeDesc;
	private String createdBy;
	private Date   createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate; 
	private String remarks;
	private List<RelationsDescription> relationsDescription=new ArrayList<RelationsDescription>();
	
	
	public Relations(){
		
	}
	
	public Relations(BigDecimal relationsId,String englishRelationsTypeDesc,
			List<RelationsDescription> relationsDescription,
			String localRelationsTypeDesc,String createdBy,Date createdDate,String relationsCode,String modifiedBy,Date modifiedDate,String isActivate,
			String approvedBy,Date approvedDate,String remarks){
	this.relationsId=relationsId;	
	this.englishRelationsTypeDesc=englishRelationsTypeDesc;
	this.localRelationsTypeDesc=localRelationsTypeDesc;
	this.createdBy=createdBy;
	this.createdDate=createdDate;
	this.modifiedBy=modifiedBy;
	this.modifiedDate=modifiedDate;
	this.isActive=isActivate;
	this.relationsDescription=relationsDescription;
	this.relationsCode=relationsCode;
	this.approvedBy=approvedBy;
	this.approvedDate=approvedDate;
	this.remarks=remarks;
	}
	

	
	@Id
	@GeneratedValue(generator="ex_relations_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_relations_seq",sequenceName="EX_RELATIONS_SEQ",allocationSize=1)
	@Column(name ="RELATIONS_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRelationsId() {
		return relationsId;
	}
	
	public void setRelationsId(BigDecimal relationsId) {
		this.relationsId = relationsId;
	}
	@Column(name ="RELATIONS_TYPE_DESC_ENGLISH")
	public String getEnglishRelationsTypeDesc() {
		return englishRelationsTypeDesc;
	}
	public void setEnglishRelationsTypeDesc(String englishRelationsTypeDesc) {
		this.englishRelationsTypeDesc = englishRelationsTypeDesc;
	}
	@Column(name ="RELATIONS_TYPE_DESC_ARABIC")
	public String getLocalRelationsTypeDesc() {
		return localRelationsTypeDesc;
	}

	public void setLocalRelationsTypeDesc(String localRelationsTypeDesc) {
		this.localRelationsTypeDesc = localRelationsTypeDesc;
	}
			
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "relations")
	public List<RelationsDescription> getRelationsDescription() {
		return relationsDescription;
	}

	public void setRelationsDescription(
			List<RelationsDescription> relationsDescription) {
		this.relationsDescription = relationsDescription;
	}

	@Column(name="RELATIONS_CODE")
	public String getRelationsCode() {
		return relationsCode;
	}

	public void setRelationsCode(String relationsCode) {
		this.relationsCode = relationsCode;
	}

	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

		
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
 
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	@Column(name="REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
 

}
