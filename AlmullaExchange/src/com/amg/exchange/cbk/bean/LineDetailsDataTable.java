package com.amg.exchange.cbk.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.jvprocess.model.ViewAccountCategory;
import com.amg.exchange.jvprocess.model.ViewAccountClass;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewBusinessoperationAlev2;
import com.amg.exchange.jvprocess.model.ViewFranchiseAlev3;
import com.amg.exchange.jvprocess.model.ViewProductAlev4;
import com.amg.exchange.jvprocess.model.ViewmainActivityALev1;

public class LineDetailsDataTable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String companyCode;
	private String activityCode;
	private BigDecimal activityCenter;
	private String acountClass;
	private String acountCategory;
	private String description;
	private BigDecimal lineNo;
	private BigDecimal lineIndex;
	private String lineType;
	
	private List<ViewmainActivityALev1> valev1 = null;
	private List<ViewBusinessoperationAlev2> valev2 = null;
	private List<ViewFranchiseAlev3> valev3 = null;
	private List<ViewProductAlev4> valev4 = null;
	//private List<LineDetailsDataTable> lineDetailsDataTable = null;
	private List<ViewActivitycenterAcnt> viewActivitycenterAcnt = null;
	List<ViewAccountCategory> lstViewAccountCategory;
	List<ViewAccountClass> lstViewAccountClass;

	private String selectedValev1Code;
	private String selectedValev2Code;
	private String selectedValev3Code;
	private String selectedValev4Code;
	
	private BigDecimal selectedActCenter;
	private String selectedAccClass;
	private String selectedAccCategory;
	
	
	private BigDecimal cbkLineSeqId;
	private BigDecimal cbkHdrSeqId;
	private BigDecimal cbkDetalisSeqId;
	private String cbkDetailsCreatedBy;
	private Date cbkDetailsCreatedDate;
	
	
	private String cbkDetailsModifiedBy;
	private Date cbkDetailsModifiedDate;
	
	
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public BigDecimal getActivityCenter() {
		return activityCenter;
	}

	public void setActivityCenter(BigDecimal activityCenter) {
		this.activityCenter = activityCenter;
	}

	public String getAcountClass() {
		return acountClass;
	}

	public void setAcountClass(String acountClass) {
		this.acountClass = acountClass;
	}

	public String getAcountCategory() {
		return acountCategory;
	}

	public void setAcountCategory(String acountCategory) {
		this.acountCategory = acountCategory;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getLineNo() {
		return lineNo;
	}

	public void setLineNo(BigDecimal lineNo) {
		this.lineNo = lineNo;
	}

	public BigDecimal getLineIndex() {
		return lineIndex;
	}

	public void setLineIndex(BigDecimal lineIndex) {
		this.lineIndex = lineIndex;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
	}

	public List<ViewmainActivityALev1> getValev1() {
		return valev1;
	}

	public void setValev1(List<ViewmainActivityALev1> valev1) {
		this.valev1 = valev1;
	}

	public List<ViewBusinessoperationAlev2> getValev2() {
		return valev2;
	}

	public void setValev2(List<ViewBusinessoperationAlev2> valev2) {
		this.valev2 = valev2;
	}

	public List<ViewFranchiseAlev3> getValev3() {
		return valev3;
	}

	public void setValev3(List<ViewFranchiseAlev3> valev3) {
		this.valev3 = valev3;
	}

	public List<ViewProductAlev4> getValev4() {
		return valev4;
	}

	public void setValev4(List<ViewProductAlev4> valev4) {
		this.valev4 = valev4;
	}

	public List<ViewActivitycenterAcnt> getViewActivitycenterAcnt() {
		return viewActivitycenterAcnt;
	}

	public void setViewActivitycenterAcnt(
			List<ViewActivitycenterAcnt> viewActivitycenterAcnt) {
		this.viewActivitycenterAcnt = viewActivitycenterAcnt;
	}

	public String getSelectedValev1Code() {
		return selectedValev1Code;
	}

	public void setSelectedValev1Code(String selectedValev1Code) {
		this.selectedValev1Code = selectedValev1Code;
	}

	public String getSelectedValev2Code() {
		return selectedValev2Code;
	}

	public void setSelectedValev2Code(String selectedValev2Code) {
		this.selectedValev2Code = selectedValev2Code;
	}

	public String getSelectedValev3Code() {
		return selectedValev3Code;
	}

	public void setSelectedValev3Code(String selectedValev3Code) {
		this.selectedValev3Code = selectedValev3Code;
	}

	public String getSelectedValev4Code() {
		return selectedValev4Code;
	}

	public void setSelectedValev4Code(String selectedValev4Code) {
		this.selectedValev4Code = selectedValev4Code;
	}

	public BigDecimal getSelectedActCenter() {
		return selectedActCenter;
	}

	public void setSelectedActCenter(BigDecimal selectedActCenter) {
		this.selectedActCenter = selectedActCenter;
	}

	public String getSelectedAccClass() {
		return selectedAccClass;
	}

	public void setSelectedAccClass(String selectedAccClass) {
		this.selectedAccClass = selectedAccClass;
	}

	public String getSelectedAccCategory() {
		return selectedAccCategory;
	}

	public void setSelectedAccCategory(String selectedAccCategory) {
		this.selectedAccCategory = selectedAccCategory;
	}

	public List<ViewAccountCategory> getLstViewAccountCategory() {
		return lstViewAccountCategory;
	}

	public void setLstViewAccountCategory(
			List<ViewAccountCategory> lstViewAccountCategory) {
		this.lstViewAccountCategory = lstViewAccountCategory;
	}

	public List<ViewAccountClass> getLstViewAccountClass() {
		return lstViewAccountClass;
	}

	public void setLstViewAccountClass(List<ViewAccountClass> lstViewAccountClass) {
		this.lstViewAccountClass = lstViewAccountClass;
	}

	public BigDecimal getCbkLineSeqId() {
		return cbkLineSeqId;
	}

	public void setCbkLineSeqId(BigDecimal cbkLineSeqId) {
		this.cbkLineSeqId = cbkLineSeqId;
	}

	public BigDecimal getCbkHdrSeqId() {
		return cbkHdrSeqId;
	}

	public void setCbkHdrSeqId(BigDecimal cbkHdrSeqId) {
		this.cbkHdrSeqId = cbkHdrSeqId;
	}

	public BigDecimal getCbkDetalisSeqId() {
		return cbkDetalisSeqId;
	}

	public void setCbkDetalisSeqId(BigDecimal cbkDetalisSeqId) {
		this.cbkDetalisSeqId = cbkDetalisSeqId;
	}

	public String getCbkDetailsCreatedBy() {
		return cbkDetailsCreatedBy;
	}

	public void setCbkDetailsCreatedBy(String cbkDetailsCreatedBy) {
		this.cbkDetailsCreatedBy = cbkDetailsCreatedBy;
	}

	public Date getCbkDetailsCreatedDate() {
		return cbkDetailsCreatedDate;
	}

	public void setCbkDetailsCreatedDate(Date cbkDetailsCreatedDate) {
		this.cbkDetailsCreatedDate = cbkDetailsCreatedDate;
	}

	public String getCbkDetailsModifiedBy() {
		return cbkDetailsModifiedBy;
	}

	public void setCbkDetailsModifiedBy(String cbkDetailsModifiedBy) {
		this.cbkDetailsModifiedBy = cbkDetailsModifiedBy;
	}

	public Date getCbkDetailsModifiedDate() {
		return cbkDetailsModifiedDate;
	}

	public void setCbkDetailsModifiedDate(Date cbkDetailsModifiedDate) {
		this.cbkDetailsModifiedDate = cbkDetailsModifiedDate;
	}
	
	

}
