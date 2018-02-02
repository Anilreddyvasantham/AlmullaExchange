package com.amg.exchange.common.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/*******************************************************************************************************************

		 File		: BizComponentDataRef.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 3:42:52 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 3:42:52 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@SuppressWarnings("serial")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_BIZ_COMPONENT_DATA_REF")
public class BizComponentDataRef implements java.io.Serializable {

	private BigDecimal componentDataRefId;
	private BizComponentData fsBizComponentData;
	private BusinessComponentConf fsBusinessComponentConf;
	private String active;
	private String createdBy;
	private Date createdTime;
	private String updatedBy;
	private Date updatedTime;

	public BizComponentDataRef() {
	}

	public BizComponentDataRef(BigDecimal componentDataRefId,
			BusinessComponentConf fsBusinessComponentConf,
			Date createdTime, Date updatedTime) {
		this.componentDataRefId = componentDataRefId;
		this.fsBusinessComponentConf = fsBusinessComponentConf;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
	}

	public BizComponentDataRef(BigDecimal componentDataRefId,
			BizComponentData fsBizComponentData,
			BusinessComponentConf fsBusinessComponentConf, String active,
			String createdBy, Date createdTime, String updatedBy,
			Date updatedTime) {
		this.componentDataRefId = componentDataRefId;
		this.fsBizComponentData = fsBizComponentData;
		this.fsBusinessComponentConf = fsBusinessComponentConf;
		this.active = active;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.updatedBy = updatedBy;
		this.updatedTime = updatedTime;
	}

	@Id
	/*@TableGenerator(name="componentDataRefId",table="fs_componentDataRefIdpk",pkColumnName="componentDataRefIdkey",pkColumnValue="componentDataRefIdValue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="componentDataRefId")
	*/
	@GeneratedValue(generator="fs_biz_component_data_ref_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_biz_component_data_ref_seq" ,sequenceName="FS_BIZ_COMPONENT_DATA_REF_SEQ",allocationSize=1)
	@Column(name = "COMPONENT_DATA_REF_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentDataRefId() {
		return this.componentDataRefId;
	}

	public void setComponentDataRefId(BigDecimal componentDataRefId) {
		this.componentDataRefId = componentDataRefId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_DATA_ID")
	public BizComponentData getFsBizComponentData() {
		return this.fsBizComponentData;
	}

	public void setFsBizComponentData(BizComponentData fsBizComponentData) {
		this.fsBizComponentData = fsBizComponentData;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_CONF_ID", nullable = false)
	public BusinessComponentConf getFsBusinessComponentConf() {
		return this.fsBusinessComponentConf;
	}

	public void setFsBusinessComponentConf(
			BusinessComponentConf fsBusinessComponentConf) {
		this.fsBusinessComponentConf = fsBusinessComponentConf;
	}

	@Column(name = "ACTIVE", length = 1)
	public String getActive() {
		return this.active;
	}

	public void setActive(String active) {
		this.active = active;
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

}
