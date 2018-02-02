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
@Table(name = "EX_PARAMETER_DETAILS")
public class ParameterDetails implements Serializable {
	
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal parameterDetailsId;
	/*private ParameterDefinition parameterDefinitionId;*/
	private ParameterMaster parameterMasterId;
	private String recordId;
	private String paramCodeDef;
	private String fullDesc;
	private String shortDesc;
	private String arFullDesc;
	private String arShortDesc;
	private BigDecimal numericField1;
	private BigDecimal numericField2;
	private BigDecimal numericField3;
	private BigDecimal numericField4;
	private BigDecimal numericField5;
	private BigDecimal numericField6;
	private BigDecimal numericField7;
	private BigDecimal numericField8;
	private BigDecimal numericField9;
	private String charField1;
	private String charField2;
	private String charField3;
	private String charField4;
	private String charField5;
	private String charField6;
	private String charField7;
	private String charField8;
	private String charField9;
	private Date dateField1;
	private Date dateField2;
	private Date dateField3;
	private Date dateField4;
	private Date dateField5;
	private Date dateField6;
	private Date dateField7;
	private Date dateField8;
	private Date dateField9;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	public ParameterDetails() {
		super();
	}

	public ParameterDetails(BigDecimal parameterDetailsId, ParameterDefinition parameterDefinitionId, ParameterMaster parameterMasterId, String recordId, String paramCodeDef, String fullDesc,
			String shortDesc, String arFullDesc, String arShortDesc, BigDecimal numericField1, BigDecimal numericField2, BigDecimal numericField3, BigDecimal numericField4, BigDecimal numericField5,
			BigDecimal numericField6, BigDecimal numericField7, BigDecimal numericField8, BigDecimal numericField9, String charField1, String charField2, String charField3, String charField4,
			String charField5, String charField6, String charField7, String charField8, String charField9, Date dateField1, Date dateField2, Date dateField3, Date dateField4, Date dateField5,
			Date dateField6, Date dateField7, Date dateField8, Date dateField9, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy,
			Date approvedDate, String remarks) {
		super();
		this.parameterDetailsId = parameterDetailsId;
	/*	this.parameterDefinitionId = parameterDefinitionId;*/
		this.parameterMasterId = parameterMasterId;
		this.recordId = recordId;
		this.paramCodeDef = paramCodeDef;
		this.fullDesc = fullDesc;
		this.shortDesc = shortDesc;
		this.arFullDesc = arFullDesc;
		this.arShortDesc = arShortDesc;
		this.numericField1 = numericField1;
		this.numericField2 = numericField2;
		this.numericField3 = numericField3;
		this.numericField4 = numericField4;
		this.numericField5 = numericField5;
		this.numericField6 = numericField6;
		this.numericField7 = numericField7;
		this.numericField8 = numericField8;
		this.numericField9 = numericField9;
		this.charField1 = charField1;
		this.charField2 = charField2;
		this.charField3 = charField3;
		this.charField4 = charField4;
		this.charField5 = charField5;
		this.charField6 = charField6;
		this.charField7 = charField7;
		this.charField8 = charField8;
		this.charField9 = charField9;
		this.dateField1 = dateField1;
		this.dateField2 = dateField2;
		this.dateField3 = dateField3;
		this.dateField4 = dateField4;
		this.dateField5 = dateField5;
		this.dateField6 = dateField6;
		this.dateField7 = dateField7;
		this.dateField8 = dateField8;
		this.dateField9 = dateField9;
		this.isActive = isActive;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
		this.approvedBy = approvedBy;
		this.approvedDate = approvedDate;
		this.remarks = remarks;
	}

	@Id
	@GeneratedValue(generator = "ex_parameter_details_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_parameter_details_seq", sequenceName = "EX_PARAMETER_DETAILS_SEQ", allocationSize = 1)
	@Column(name = "PARAMETER_DETAILS_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getParameterDetailsId() {
		return parameterDetailsId;
	}

	public void setParameterDetailsId(BigDecimal parameterDetailsId) {
		this.parameterDetailsId = parameterDetailsId;
	}

	/*@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARAM_MASTER_DEF_ID")
	public ParameterDefinition getParameterDefinitionId() {
		return parameterDefinitionId;
	}

	public void setParameterDefinitionId(ParameterDefinition parameterDefinitionId) {
		this.parameterDefinitionId = parameterDefinitionId;
	}*/

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

	@Column(name = "PARAM_CODE_DEF")
	public String getParamCodeDef() {
		return paramCodeDef;
	}

	public void setParamCodeDef(String paramCodeDef) {
		this.paramCodeDef = paramCodeDef;
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

	@Column(name = "AR_FULL_DESC")
	public String getArFullDesc() {
		return arFullDesc;
	}

	public void setArFullDesc(String arFullDesc) {
		this.arFullDesc = arFullDesc;
	}

	@Column(name = "AR_SHORT_DESC")
	public String getArShortDesc() {
		return arShortDesc;
	}

	public void setArShortDesc(String arShortDesc) {
		this.arShortDesc = arShortDesc;
	}

	@Column(name = "NUMERIC_FIELD1")
	public BigDecimal getNumericField1() {
		return numericField1;
	}

	public void setNumericField1(BigDecimal numericField1) {
		this.numericField1 = numericField1;
	}

	@Column(name = "NUMERIC_FIELD2")
	public BigDecimal getNumericField2() {
		return numericField2;
	}

	public void setNumericField2(BigDecimal numericField2) {
		this.numericField2 = numericField2;
	}

	@Column(name = "NUMERIC_FIELD3")
	public BigDecimal getNumericField3() {
		return numericField3;
	}

	public void setNumericField3(BigDecimal numericField3) {
		this.numericField3 = numericField3;
	}

	@Column(name = "NUMERIC_FIELD4")
	public BigDecimal getNumericField4() {
		return numericField4;
	}

	public void setNumericField4(BigDecimal numericField4) {
		this.numericField4 = numericField4;
	}

	@Column(name = "NUMERIC_FIELD5")
	public BigDecimal getNumericField5() {
		return numericField5;
	}

	public void setNumericField5(BigDecimal numericField5) {
		this.numericField5 = numericField5;
	}

	@Column(name = "NUMERIC_FIELD6")
	public BigDecimal getNumericField6() {
		return numericField6;
	}

	public void setNumericField6(BigDecimal numericField6) {
		this.numericField6 = numericField6;
	}

	@Column(name = "NUMERIC_FIELD7")
	public BigDecimal getNumericField7() {
		return numericField7;
	}

	public void setNumericField7(BigDecimal numericField7) {
		this.numericField7 = numericField7;
	}

	@Column(name = "NUMERIC_FIELD8")
	public BigDecimal getNumericField8() {
		return numericField8;
	}

	public void setNumericField8(BigDecimal numericField8) {
		this.numericField8 = numericField8;
	}

	@Column(name = "NUMERIC_FIELD9")
	public BigDecimal getNumericField9() {
		return numericField9;
	}

	public void setNumericField9(BigDecimal numericField9) {
		this.numericField9 = numericField9;
	}

	@Column(name = "CHAR_FIELD1")
	public String getCharField1() {
		return charField1;
	}

	public void setCharField1(String charField1) {
		this.charField1 = charField1;
	}

	@Column(name = "CHAR_FIELD2")
	public String getCharField2() {
		return charField2;
	}

	public void setCharField2(String charField2) {
		this.charField2 = charField2;
	}

	@Column(name = "CHAR_FIELD3")
	public String getCharField3() {
		return charField3;
	}

	public void setCharField3(String charField3) {
		this.charField3 = charField3;
	}

	@Column(name = "CHAR_FIELD4")
	public String getCharField4() {
		return charField4;
	}

	public void setCharField4(String charField4) {
		this.charField4 = charField4;
	}

	@Column(name = "CHAR_FIELD5")
	public String getCharField5() {
		return charField5;
	}

	public void setCharField5(String charField5) {
		this.charField5 = charField5;
	}

	@Column(name = "CHAR_FIELD6")
	public String getCharField6() {
		return charField6;
	}

	public void setCharField6(String charField6) {
		this.charField6 = charField6;
	}

	@Column(name = "CHAR_FIELD7")
	public String getCharField7() {
		return charField7;
	}

	public void setCharField7(String charField7) {
		this.charField7 = charField7;
	}

	@Column(name = "CHAR_FIELD8")
	public String getCharField8() {
		return charField8;
	}

	public void setCharField8(String charField8) {
		this.charField8 = charField8;
	}

	@Column(name = "CHAR_FIELD9")
	public String getCharField9() {
		return charField9;
	}

	public void setCharField9(String charField9) {
		this.charField9 = charField9;
	}

	@Column(name = "DATE_FIELD1")
	public Date getDateField1() {
		return dateField1;
	}

	public void setDateField1(Date dateField1) {
		this.dateField1 = dateField1;
	}

	@Column(name = "DATE_FIELD2")
	public Date getDateField2() {
		return dateField2;
	}

	public void setDateField2(Date dateField2) {
		this.dateField2 = dateField2;
	}

	@Column(name = "DATE_FIELD3")
	public Date getDateField3() {
		return dateField3;
	}

	public void setDateField3(Date dateField3) {
		this.dateField3 = dateField3;
	}

	@Column(name = "DATE_FIELD4")
	public Date getDateField4() {
		return dateField4;
	}

	public void setDateField4(Date dateField4) {
		this.dateField4 = dateField4;
	}

	@Column(name = "DATE_FIELD5")
	public Date getDateField5() {
		return dateField5;
	}

	public void setDateField5(Date dateField5) {
		this.dateField5 = dateField5;
	}

	@Column(name = "DATE_FIELD6")
	public Date getDateField6() {
		return dateField6;
	}

	public void setDateField6(Date dateField6) {
		this.dateField6 = dateField6;
	}

	@Column(name = "DATE_FIELD7")
	public Date getDateField7() {
		return dateField7;
	}

	public void setDateField7(Date dateField7) {
		this.dateField7 = dateField7;
	}

	@Column(name = "DATE_FIELD8")
	public Date getDateField8() {
		return dateField8;
	}

	public void setDateField8(Date dateField8) {
		this.dateField8 = dateField8;
	}

	@Column(name = "DATE_FIELD9")
	public Date getDateField9() {
		return dateField9;
	}

	public void setDateField9(Date dateField9) {
		this.dateField9 = dateField9;
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

	@Column(name = "APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	@Column(name = "APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	@Column(name = "REMARKS")
	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
