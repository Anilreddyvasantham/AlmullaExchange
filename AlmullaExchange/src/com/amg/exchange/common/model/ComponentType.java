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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: ComponentType.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 4:45:00 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 4:45:00 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@SuppressWarnings("serial")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_COMPONENT_TYPE")
public class ComponentType implements java.io.Serializable {

	private BigDecimal componentTypeId;
	private String componentTypeName;

	private String isActive;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;
	private List<BusinessComponent> fsBusinessComponents = new ArrayList<BusinessComponent>();
	private ComponentTypeDetail componentTypeDetail;

	public ComponentType() {
	}

	public ComponentType(BigDecimal componentTypeId, Date createdTime,
			Date updatedTime) {
		this.componentTypeId = componentTypeId;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	public ComponentType(BigDecimal componentTypeId, String componentTypeName,
			String isActive, String createdBy, Date createdTime,
			String updatedBy, Date updatedTime,
			List<BusinessComponent> fsBusinessComponents) {
		this.componentTypeId = componentTypeId;
		this.componentTypeName = componentTypeName;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
		this.fsBusinessComponents = fsBusinessComponents;
	}

	@Id
	/*@TableGenerator(name = "componenttypeid", table = "fs_componenttypeidpk", pkColumnName = "componenttypeidkey", pkColumnValue = "componenttypeidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "componenttypeid")FS_COMPONENT_TYPE
	*/
	@GeneratedValue(generator="fs_component_type_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_component_type_seq" ,sequenceName="FS_COMPONENT_TYPE_SEQ",allocationSize=1)
	@Column(name = "COMPONENT_TYPE_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentTypeId() {
		return this.componentTypeId;
	}

	public void setComponentTypeId(BigDecimal componentTypeId) {
		this.componentTypeId = componentTypeId;
	}

	@Column(name = "COMPONENT_TYPE_NAME", length = 30)
	public String getComponentTypeName() {
		return this.componentTypeName;
	}

	public void setComponentTypeName(String componentTypeName) {
		this.componentTypeName = componentTypeName;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fsComponentType")
	public List<BusinessComponent> getFsBusinessComponents() {
		return fsBusinessComponents;
	}

	public void setFsBusinessComponents(
			List<BusinessComponent> fsBusinessComponents) {
		this.fsBusinessComponents = fsBusinessComponents;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "componentType")
	public ComponentTypeDetail getComponentTypeDetail() {
		return componentTypeDetail;
	}

	public void setComponentTypeDetail(ComponentTypeDetail componentTypeDetail) {
		this.componentTypeDetail = componentTypeDetail;
	}
}
