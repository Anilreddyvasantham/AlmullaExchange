package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
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

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/*******************************************************************************************************************

		 File		: BusinessComponent.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:36:16 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:36:16 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/

@SuppressWarnings("serial")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_BUSINESS_COMPONENT")
public class BusinessComponent implements java.io.Serializable {

	private BigDecimal componentId;
	private String componentName;
	private ComponentType fsComponentType;
	private String isMultiple;
	private String isActive;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	private List<BusinessComponentConf> fsBusinessComponentConfs = new ArrayList<BusinessComponentConf>();

	public BusinessComponent() {
	}

	public BusinessComponent(BigDecimal componentId, Date createdTime,
			Date updatedTime) {
		this.componentId = componentId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}
	
	public BusinessComponent(BigDecimal componentId, String componentName,
			ComponentType fsComponentType, String isMultiple, String isActive,
			String createdBy, Date createdTime, String updatedBy,
			Date updatedTime,
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.componentId = componentId;
		this.componentName = componentName;
		this.fsComponentType = fsComponentType;
		this.isMultiple = isMultiple;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

	@Id
	/*@TableGenerator(name = "componentid", table = "fs_componentidpk", pkColumnName = "componentidkey", pkColumnValue = "componentidvale", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "componentid")
	*/
	@GeneratedValue(generator="fs_business_component_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_business_component_seq" ,sequenceName="FS_BUSINESS_COMPONENT_SEQ",allocationSize=1)		
	@Column(name = "COMPONENT_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentId() {
		return this.componentId;
	}

	public void setComponentId(BigDecimal componentId) {
		this.componentId = componentId;
	}

	@Column(name = "COMPONENT_NAME", length = 30, unique=true)
	public String getComponentName() {
		return this.componentName;
	}

	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	/*
	 * @Column(name = "COMPONENT_TYPE_ID", precision = 22, scale = 0) public
	 * BigDecimal getComponentTypeId() { return this.componentTypeId; }
	 * 
	 * public void setComponentTypeId(BigDecimal componentTypeId) {
	 * this.componentTypeId = componentTypeId; }
	 */
	@Column(name = "IS_MULTIPLE", length = 1)
	public String getIsMultiple() {
		return this.isMultiple;
	}

	public void setIsMultiple(String isMultiple) {
		this.isMultiple = isMultiple;
	}

	@Column(name = "IS_ACTIVE", length = 1)
	public String getIsActive() {
		return this.isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	@Column(name = "CREATED_BY", length = 30)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Column(name = "CREATED_TIME", nullable = false)
	public Date getCreatedTime() {
		return this.createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	@Column(name = "UPDATED_BY", length = 30)
	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Column(name = "UPDATED_TIME", nullable = false)
	public Date getUpdatedTime() {
		return this.updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsBusinessComponent")
	public List<BusinessComponentConf> getFsBusinessComponentConfs() {
		return fsBusinessComponentConfs;
	}

	public void setFsBusinessComponentConfs(
			List<BusinessComponentConf> fsBusinessComponentConfs) {
		this.fsBusinessComponentConfs = fsBusinessComponentConfs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_TYPE_ID")
	public ComponentType getFsComponentType() {
		return fsComponentType;
	}

	public void setFsComponentType(ComponentType fsComponentType) {
		this.fsComponentType = fsComponentType;
	}

}
