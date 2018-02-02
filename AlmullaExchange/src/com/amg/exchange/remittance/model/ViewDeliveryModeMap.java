package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="VW_EX_DELIVERY_MODE_MAP")
public class ViewDeliveryModeMap implements Serializable {

	private static final long serialVersionUID = 1L;


	//private BigDecimal idNo;
	private BigDecimal deliveryModeId;
	//private BigDecimal remittanceModeId;
	private String deliveryModeDesc;
	private String deliveryCode;

	/*@Id
	@Column(name = "IDNO")
	public BigDecimal getIdNo() {
		return idNo;
	}
	public void setIdNo(BigDecimal idNo) {
		this.idNo = idNo;
	}*/
	@Id
	@Column(name = "DELIVERY_MODE_ID")
	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}
	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	/*@Column(name = "REMITTANCE_MODE_ID")
	public BigDecimal getRemittanceModeId() {
		return remittanceModeId;
	}
	public void setRemittanceModeId(BigDecimal remittanceModeId) {
		this.remittanceModeId = remittanceModeId;
	}*/
	
	@Column(name = "DELIVERY_DESCRIPTION")
	public String getDeliveryModeDesc() {
		return deliveryModeDesc;
	}
	
	public void setDeliveryModeDesc(String deliveryModeDesc) {
		this.deliveryModeDesc = deliveryModeDesc;
	}
	
	@Column(name = "DELIVERY_CODE")
	public String getDeliveryCode() {
		return deliveryCode;
	}
	public void setDeliveryCode(String deliveryCode) {
		this.deliveryCode = deliveryCode;
	}




}
