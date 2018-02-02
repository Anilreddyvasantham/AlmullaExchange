package com.amg.exchange.registration.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
/*******************************************************************************************************************

File		: ArcmateScanMaster.java

Project	: AlmullaExchange

Package	: com.amg.exchange.registration.model

Created	:	
				Date	: 02-Feb-2016
				By		: Nazish Ehsan Hashmi
				Revision:

 Last Change:
				Date	: 02-Feb-2016
				By		: Nazish Ehsan Hashmi
				Revision:



********************************************************************************************************************/
@Entity
@Table(name = "FS_ARCMATE_SCAN_MASTER" )
public class ArcmateScanMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal arcmateScanMasterId;
	private BigDecimal applicationCountryId;
	private BigDecimal companyId;
	private String modeOfOperation;
	private String scanType;
	private String url;
	private String ipAddress;
	private BigDecimal portNo;
	private String contextPath;
	private String urlParamField1;
	private String urlParamField2;
	private String urlParamField3;
	private String urlParamField4;
	private String urlParamField5;
	private String urlParamField6;
	private String urlParamField7;
	private String urlParamField8;
	private String urlParamField9;
	private String urlParamField10;
	private String urlParamField11;
	private String urlParamField12;
	private String urlParamField13;
	private String urlParamField14;
	private String urlParamField15;
	private String urlParamField16;
	private String fieldSeprator;
	private String fieldAssigner;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	
	
	public ArcmateScanMaster() {
	
	}
	public ArcmateScanMaster(BigDecimal arcmateScanMasterId,
			BigDecimal applicationCountryId, BigDecimal companyId,
			String modeOfOperation, String scanType, String url,
			String ipAddress, BigDecimal portNo, String contextPath,
			String urlParamField1, String urlParamField2,
			String urlParamField3, String urlParamField4,
			String urlParamField5, String urlParamField6,
			String urlParamField7, String urlParamField8,
			String urlParamField9, String urlParamField10,
			String urlParamField11, String urlParamField12,
			String urlParamField13, String urlParamField14,
			String urlParamField15, String urlParamField16,
			String fieldSeprator, String fieldAssigner, String isActive,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate, String approvedBy, Date approvedDate,
			String remarks) {
		super();
		this.arcmateScanMasterId = arcmateScanMasterId;
		this.applicationCountryId = applicationCountryId;
		this.companyId = companyId;
		this.modeOfOperation = modeOfOperation;
		this.scanType = scanType;
		this.url = url;
		this.ipAddress = ipAddress;
		this.portNo = portNo;
		this.contextPath = contextPath;
		this.urlParamField1 = urlParamField1;
		this.urlParamField2 = urlParamField2;
		this.urlParamField3 = urlParamField3;
		this.urlParamField4 = urlParamField4;
		this.urlParamField5 = urlParamField5;
		this.urlParamField6 = urlParamField6;
		this.urlParamField7 = urlParamField7;
		this.urlParamField8 = urlParamField8;
		this.urlParamField9 = urlParamField9;
		this.urlParamField10 = urlParamField10;
		this.urlParamField11 = urlParamField11;
		this.urlParamField12 = urlParamField12;
		this.urlParamField13 = urlParamField13;
		this.urlParamField14 = urlParamField14;
		this.urlParamField15 = urlParamField15;
		this.urlParamField16 = urlParamField16;
		this.fieldSeprator = fieldSeprator;
		this.fieldAssigner = fieldAssigner;
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
	@GeneratedValue(generator="fs_arcmate_scan_master_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_arcmate_scan_master_seq" ,sequenceName="FS_ARCMATE_SCAN_MASTER_SEQ",allocationSize=1)	
	@Column(name = "ARCMATE_SCAN_MASTER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getArcmateScanMasterId() {
		return arcmateScanMasterId;
	}
	public void setArcmateScanMasterId(BigDecimal arcmateScanMasterId) {
		this.arcmateScanMasterId = arcmateScanMasterId;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "COMPANY_ID")
	public BigDecimal getCompanyId() {
		return companyId;
	}
	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}
	@Column(name = "MODE_OF_OPERATION")
	public String getModeOfOperation() {
		return modeOfOperation;
	}
	public void setModeOfOperation(String modeOfOperation) {
		this.modeOfOperation = modeOfOperation;
	}
	
	@Column(name = "SCAN_TYPE")
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	@Column(name = "URL")
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "IP_ADDRESS")
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column(name = "PORT_NUMBER")
	public BigDecimal getPortNo() {
		return portNo;
	}
	public void setPortNo(BigDecimal portNo) {
		this.portNo = portNo;
	}
	
	@Column(name = "CONTEXT_PATH")
	public String getContextPath() {
		return contextPath;
	}
	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}
	@Column(name = "URL_PARAM_FIELD1")
	public String getUrlParamField1() {
		return urlParamField1;
	}
	public void setUrlParamField1(String urlParamField1) {
		this.urlParamField1 = urlParamField1;
	}
	
	@Column(name = "URL_PARAM_FIELD2")
	public String getUrlParamField2() {
		return urlParamField2;
	}
	public void setUrlParamField2(String urlParamField2) {
		this.urlParamField2 = urlParamField2;
	}
	
	@Column(name = "URL_PARAM_FIELD3")
	public String getUrlParamField3() {
		return urlParamField3;
	}
	public void setUrlParamField3(String urlParamField3) {
		this.urlParamField3 = urlParamField3;
	}
	
	@Column(name = "URL_PARAM_FIELD4")
	public String getUrlParamField4() {
		return urlParamField4;
	}
	public void setUrlParamField4(String urlParamField4) {
		this.urlParamField4 = urlParamField4;
	}
	
	@Column(name = "URL_PARAM_FIELD5")
	public String getUrlParamField5() {
		return urlParamField5;
	}
	public void setUrlParamField5(String urlParamField5) {
		this.urlParamField5 = urlParamField5;
	}
	
	@Column(name = "URL_PARAM_FIELD6")
	public String getUrlParamField6() {
		return urlParamField6;
	}
	public void setUrlParamField6(String urlParamField6) {
		this.urlParamField6 = urlParamField6;
	}
	
	@Column(name = "URL_PARAM_FIELD7")
	public String getUrlParamField7() {
		return urlParamField7;
	}
	public void setUrlParamField7(String urlParamField7) {
		this.urlParamField7 = urlParamField7;
	}
	
	@Column(name = "URL_PARAM_FIELD8")
	public String getUrlParamField8() {
		return urlParamField8;
	}
	public void setUrlParamField8(String urlParamField8) {
		this.urlParamField8 = urlParamField8;
	}
	
	@Column(name = "URL_PARAM_FIELD9")
	public String getUrlParamField9() {
		return urlParamField9;
	}
	public void setUrlParamField9(String urlParamField9) {
		this.urlParamField9 = urlParamField9;
	}
	
	@Column(name = "URL_PARAM_FIELD10")
	public String getUrlParamField10() {
		return urlParamField10;
	}
	public void setUrlParamField10(String urlParamField10) {
		this.urlParamField10 = urlParamField10;
	}
	
	@Column(name = "URL_PARAM_FIELD11")
	public String getUrlParamField11() {
		return urlParamField11;
	}
	public void setUrlParamField11(String urlParamField11) {
		this.urlParamField11 = urlParamField11;
	}
	
	@Column(name = "URL_PARAM_FIELD12")
	public String getUrlParamField12() {
		return urlParamField12;
	}
	public void setUrlParamField12(String urlParamField12) {
		this.urlParamField12 = urlParamField12;
	}
	
	@Column(name = "URL_PARAM_FIELD13")
	public String getUrlParamField13() {
		return urlParamField13;
	}
	public void setUrlParamField13(String urlParamField13) {
		this.urlParamField13 = urlParamField13;
	}
	
	@Column(name = "URL_PARAM_FIELD14")
	public String getUrlParamField14() {
		return urlParamField14;
	}
	public void setUrlParamField14(String urlParamField14) {
		this.urlParamField14 = urlParamField14;
	}
	
	@Column(name = "URL_PARAM_FIELD15")
	public String getUrlParamField15() {
		return urlParamField15;
	}
	public void setUrlParamField15(String urlParamField15) {
		this.urlParamField15 = urlParamField15;
	}
	
	@Column(name = "URL_PARAM_FIELD16")
	public String getUrlParamField16() {
		return urlParamField16;
	}
	public void setUrlParamField16(String urlParamField16) {
		this.urlParamField16 = urlParamField16;
	}
	
	@Column(name = "FIELD_SEPARATOR")
	public String getFieldSeprator() {
		return fieldSeprator;
	}
	public void setFieldSeprator(String fieldSeprator) {
		this.fieldSeprator = fieldSeprator;
	}
	
	@Column(name = "FIELD_ASSIGNER")
	public String getFieldAssigner() {
		return fieldAssigner;
	}
	public void setFieldAssigner(String fieldAssigner) {
		this.fieldAssigner = fieldAssigner;
	}
	
	@Column(name = "IS_ACTIVE")
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
