package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="V_EX_IDTY")
public class ViewIdType implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal componentDataId;
	
	private String componenetCode;
	
	private String dataDesc;
	
	private String imageId;

	@Id
	@Column(name="COMPONENT_DATA_ID")
	public BigDecimal getComponentDataId() {
		return componentDataId;
	}

	public void setComponentDataId(BigDecimal componentDataId) {
		this.componentDataId = componentDataId;
	}
	
	@Column(name="COMPONENT_CODE")
	public String getComponenetCode() {
		return componenetCode;
	}

	public void setComponenetCode(String componenetCode) {
		this.componenetCode = componenetCode;
	}

	@Column(name="DATA_DESC")
	public String getDataDesc() {
		return dataDesc;
	}

	public void setDataDesc(String dataDesc) {
		this.dataDesc = dataDesc;
	}

	@Column(name="IMG_ID")
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	

}
