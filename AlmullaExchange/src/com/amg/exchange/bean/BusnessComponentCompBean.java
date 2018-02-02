package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;

public class BusnessComponentCompBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal serialid;
	private BigDecimal bussComponentComboDataId;
	private String componentData;
	private String createdBy;
	private Date createdTime;
	private String componentid;
	private String[] languageId;
	private BizComponentData bizComponentData;
	private BizComponentDataDesc bizComponentDataDesc;
	private String[] lstComponentData;
	private String[] lstComponentDataDir;
	private Object[] objDescArray;
	private BizComponentDataRef bizComponentDataRef;

	public BusnessComponentCompBean() {
	}

	public BusnessComponentCompBean(BigDecimal serialid, BigDecimal bussComponentComboDataId, String componentData, String createdBy, Date createdTime, String componentid, String[] languageId, BizComponentData bizComponentData, BizComponentDataDesc bizComponentDataDesc, String[] lstComponentData) {

		this.serialid = serialid;
		this.bussComponentComboDataId = bussComponentComboDataId;
		this.componentData = componentData;
		this.createdBy = createdBy;
		this.createdTime = createdTime;
		this.componentid = componentid;
		this.languageId = languageId;
		this.bizComponentData = bizComponentData;
		this.bizComponentDataDesc = bizComponentDataDesc;
		this.lstComponentData = lstComponentData;
	}

	
	
	public String[] getLstComponentDataDir() {
		return lstComponentDataDir;
	}

	public void setLstComponentDataDir(String[] lstComponentDataDir) {
		this.lstComponentDataDir = lstComponentDataDir;
	}

	public BizComponentDataRef getBizComponentDataRef() {
		return bizComponentDataRef;
	}

	public void setBizComponentDataRef(BizComponentDataRef bizComponentDataRef) {
		this.bizComponentDataRef = bizComponentDataRef;
	}

	public Object[] getObjDescArray() {
		return objDescArray;
	}

	public void setObjDescArray(Object[] objDescArray) {
		this.objDescArray = objDescArray;
	}

	public String[] getLstComponentData() {
		return lstComponentData;
	}

	public void setLstComponentData(String[] lstComponentData) {
		this.lstComponentData = lstComponentData;
	}

	public BizComponentData getBizComponentData() {
		return bizComponentData;
	}

	public void setBizComponentData(BizComponentData bizComponentData) {
		this.bizComponentData = bizComponentData;
	}

	public BizComponentDataDesc getBizComponentDataDesc() {
		return bizComponentDataDesc;
	}

	public void setBizComponentDataDesc(BizComponentDataDesc bizComponentDataDesc) {
		this.bizComponentDataDesc = bizComponentDataDesc;
	}

	public String[] getLanguageId() {
		return languageId;
	}

	public void setLanguageId(String[] languageId) {
		this.languageId = languageId;
	}

	public BigDecimal getBussComponentComboDataId() {
		return bussComponentComboDataId;
	}

	public void setBussComponentComboDataId(BigDecimal bussComponentComboDataId) {
		this.bussComponentComboDataId = bussComponentComboDataId;
	}

	public BigDecimal getSerialid() {
		return serialid;
	}

	public void setSerialid(BigDecimal serialid) {
		this.serialid = serialid;
	}

	public String getComponentData() {
		return componentData;
	}

	public void setComponentData(String componentData) {
		this.componentData = componentData;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getComponentid() {
		return componentid;
	}

	public void setComponentid(String componentid) {
		this.componentid = componentid;
	}

	@Override
	public String toString() {
		return "BusnessComponentCompBean [serialid=" + serialid + ", bussComponentComboDataId=" + bussComponentComboDataId + ", componentData=" + componentData + ", createdBy=" + createdBy + ", createdTime=" + createdTime + ", componentid=" + componentid + ", languageId=" + Arrays.toString(languageId) + ", bizComponentData=" + bizComponentData + ", bizComponentDataDesc=" + bizComponentDataDesc + ", lstComponentData=" + Arrays.toString(lstComponentData) + ", objDescArray=" + Arrays.toString(objDescArray) + "]";
	}
	
}