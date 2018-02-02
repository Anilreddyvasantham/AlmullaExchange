package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

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

@Entity
@Table(name = "EX_PARAMETER_DEFINITION")
public class ParameterDefinition implements Serializable {
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal parameterDefinitionId;
	private ParameterMaster parameterMasterId;
	private String recordId;
	private String fieldName;
	private BigDecimal fieldLength;
	private String fullDesc;
	private String shortDesc;
	private String nullIndication;
	private String valueIndicator;
	private String valueFrom;
	private String valueTo;
	private String tableName;
	private String tableField;
	private String parameterId;
	private BigDecimal displayOrder;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String localFullDesc;
	private String localShortDesc;
	
	public ParameterDefinition() {
		super();
	}

	public ParameterDefinition(BigDecimal parameterDefinitionId, ParameterMaster parameterMasterId, String recordId, String fieldName, BigDecimal fieldLength, String fullDesc, String shortDesc,
			String nullIndication, String valueIndicator, String valueFrom, String valueTo, String tableName, String tableField, String parameterId, BigDecimal displayOrder, String isActive,
			String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String localFullDesc, String localShortDesc) {
		super();
		this.parameterDefinitionId = parameterDefinitionId;
		this.parameterMasterId = parameterMasterId;
		this.recordId = recordId;
		this.fieldName = fieldName;
		this.fieldLength = fieldLength;
		this.fullDesc = fullDesc;
		this.shortDesc = shortDesc;
		this.nullIndication = nullIndication;
		this.valueIndicator = valueIndicator;
		this.valueFrom = valueFrom;
		this.valueTo = valueTo;
		this.tableName = tableName;
		this.tableField = tableField;
		this.parameterId = parameterId;
		this.displayOrder = displayOrder;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.localFullDesc = localFullDesc;
		this.localShortDesc = localShortDesc;
	}

	@Id
	@GeneratedValue(generator = "ex_parameter_definition_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_parameter_definition_seq", sequenceName = "EX_PARAMETER_DEFINITION_SEQ", allocationSize = 1)
	@Column(name = "PARAM_MASTER_DEF_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getParameterDefinitionId() {
		return parameterDefinitionId;
	}

	public void setParameterDefinitionId(BigDecimal parameterDefinitionId) {
		this.parameterDefinitionId = parameterDefinitionId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARAM_MASTER_ID")
	public ParameterMaster getParameterMasterId() {
		return parameterMasterId;
	}

	public void setParameterMasterId(ParameterMaster parameterMasterId) {
		this.parameterMasterId = parameterMasterId;
	}
	
	@Column(name = "RECORD_ID")
	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}
	
	@Column(name = "FIELD_NAME")
	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	@Column(name = "FIELD_LENGTH")
	public BigDecimal getFieldLength() {
		return fieldLength;
	}

	public void setFieldLength(BigDecimal fieldLength) {
		this.fieldLength = fieldLength;
	}

	@Column(name = "FULL_DESC")
	public String getFullDesc() {
		return fullDesc;
	}

	public void setFullDesc(String fullDesc) {
		this.fullDesc = fullDesc;
	}

	@Column(name = "SHORT_DESC")
	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	@Column(name = "NULL_INDICATOR")
	public String getNullIndication() {
		return nullIndication;
	}

	public void setNullIndication(String nullIndication) {
		this.nullIndication = nullIndication;
	}

	@Column(name = "VALUE_INDICATOR")
	public String getValueIndicator() {
		return valueIndicator;
	}

	public void setValueIndicator(String valueIndicator) {
		this.valueIndicator = valueIndicator;
	}

	@Column(name = "VALUE_FROM")
	public String getValueFrom() {
		return valueFrom;
	}

	public void setValueFrom(String valueFrom) {
		this.valueFrom = valueFrom;
	}

	@Column(name = "VALUE_TO")
	public String getValueTo() {
		return valueTo;
	}

	public void setValueTo(String valueTo) {
		this.valueTo = valueTo;
	}

	@Column(name = "TABLE_NAME")
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	@Column(name = "TABLE_FIELD")
	public String getTableField() {
		return tableField;
	}

	public void setTableField(String tableField) {
		this.tableField = tableField;
	}

	@Column(name = "PARAMETER_ID")
	public String getParameterId() {
		return parameterId;
	}

	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}

	@Column(name = "DISPLAY_ORDER")
	public BigDecimal getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(BigDecimal displayOrder) {
		this.displayOrder = displayOrder;
	}

	@Column(name = "ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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
	}

	@Column(name = "MODIFIED_BY")
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

	@Column(name = "LOCAL_FULL_DESC")
	public String getLocalFullDesc() {
		return localFullDesc;
	}

	public void setLocalFullDesc(String localFullDesc) {
		this.localFullDesc = localFullDesc;
	}

	@Column(name = "LOCAL_SHORT_DESC")
	public String getLocalShortDesc() {
		return localShortDesc;
	}

	public void setLocalShortDesc(String localShortDesc) {
		this.localShortDesc = localShortDesc;
	}
	
	

}
