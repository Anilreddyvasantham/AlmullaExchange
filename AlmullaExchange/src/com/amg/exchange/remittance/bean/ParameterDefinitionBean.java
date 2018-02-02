package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("parameterDefinitionBean")
@Scope("session")
public class ParameterDefinitionBean<T> implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	Logger log = Logger.getLogger(ParameterDefinitionBean.class);
	
	// variables declaration
	
	private BigDecimal parameterMasterId;
	private String parameterId;
	private String recordId;
	private String fieldName;
	private BigDecimal fieldLength;
	private String parameterFullDesc;
	private String parameterShortDesc;
	private String nullIndicator;
	private String valueIndicator;
	private String valueFrom;
	private String valueTo;
	private String tableName;
	private String tableField;
	private BigDecimal displayOrder;
	
	
	// get and set methods

	public String getParameterFullDesc() {
		return parameterFullDesc;
	}

	public void setParameterFullDesc(String parameterFullDesc) {
		this.parameterFullDesc = parameterFullDesc;
	}

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getParameterShortDesc() {
		return parameterShortDesc;
	}

	public void setParameterShortDesc(String parameterShortDesc) {
		this.parameterShortDesc = parameterShortDesc;
	}

	public BigDecimal getParameterMasterId() {
		return parameterMasterId;
	}

	public void setParameterMasterId(BigDecimal parameterMasterId) {
		this.parameterMasterId = parameterMasterId;
	}

	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public BigDecimal getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getNullIndicator() {
		return nullIndicator;
	}

	public void setNullIndicator(String nullIndicator) {
		this.nullIndicator = nullIndicator;
	}

	public String getValueIndicator() {
		return valueIndicator;
	}

	public void setValueIndicator(String valueIndicator) {
		this.valueIndicator = valueIndicator;
	}

	public String getValueFrom() {
		return valueFrom;
	}

	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	public String getValueTo() {
		return valueTo;
	}

	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	public void save(){

	}
	
	private List<T> lstParameterMaster = new ArrayList<T>();
	
	
public void pageNavigation() {
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/parameterdefinition.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
