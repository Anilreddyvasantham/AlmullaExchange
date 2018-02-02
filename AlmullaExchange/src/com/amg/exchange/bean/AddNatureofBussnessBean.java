package com.amg.exchange.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.amg.exchange.common.model.BizComponentDataDesc;

public class AddNatureofBussnessBean implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private String bussNature;

	private String obj;
	
	private BigDecimal  bussNatureId;
	
	
	private boolean modified;
	
	private boolean objStatus;
	
	private BigDecimal natureOfBussinessId;
	
	private List<BizComponentDataDesc> fsBizComponentDataDesc = new ArrayList<BizComponentDataDesc>();
	
	

	public AddNatureofBussnessBean() {
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}

	public boolean isObjStatus() {
		return objStatus;
	}

	public void setObjStatus(boolean objStatus) {
		this.objStatus = objStatus;
	}

	public String getBussNature() {
		return bussNature;
	}

	public void setBussNature(String bussNature) {
		this.bussNature = bussNature;
	}

	public String getObj() {
		return obj;
	}

	public void setObj(String obj) {
		this.obj = obj;
	}
	
	/*AddNatureofBussnessBean(
			bussinessNatureHashaMap.get(this.bussNature), this.obj, true,
			true, this.bussNature);	*/

	public AddNatureofBussnessBean(String bussNature,String obj, boolean modified,boolean objStatus,BigDecimal  bussNatureId){
		this.bussNature = bussNature;
		this.obj = obj;
		this.modified = modified;
		this.objStatus = objStatus;
		this.bussNatureId =  bussNatureId;
	}

	public BigDecimal getBussNatureId() {
		return bussNatureId;
	}

	public void setBussNatureId(BigDecimal bussNatureId) {
		this.bussNatureId = bussNatureId;
	}

	public BigDecimal getNatureOfBussinessId() {
		return natureOfBussinessId;
	}

	public void setNatureOfBussinessId(BigDecimal natureOfBussinessId) {
		this.natureOfBussinessId = natureOfBussinessId;
	}

	public List<BizComponentDataDesc> getFsBizComponentDataDesc() {
		return fsBizComponentDataDesc;
	}

	public void setFsBizComponentDataDesc(
			List<BizComponentDataDesc> fsBizComponentDataDesc) {
		this.fsBizComponentDataDesc = fsBizComponentDataDesc;
	}

	

}
