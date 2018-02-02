package com.amg.exchange.cbk.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.cbk.model.CBKDetails;
import com.amg.exchange.cbk.model.CBKHeader;
import com.amg.exchange.cbk.model.CBKLines;
import com.amg.exchange.cbk.model.CBKTotals;
import com.amg.exchange.cbk.model.ViewExUnmappedGLS;
import com.amg.exchange.jvprocess.model.ViewAccountCategory;
import com.amg.exchange.jvprocess.model.ViewAccountClass;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewBusinessoperationAlev2;
import com.amg.exchange.jvprocess.model.ViewFranchiseAlev3;
import com.amg.exchange.jvprocess.model.ViewProductAlev4;
import com.amg.exchange.jvprocess.model.ViewmainActivityALev1;

public interface CBKReportsService {
	
	public List<ViewmainActivityALev1> getViewAlev1List();
	public List<ViewBusinessoperationAlev2> getViewAlev2List(String alev1Code);
	public List<ViewFranchiseAlev3> getViewAlev3List(String alev2Code);
	public List<ViewProductAlev4> getViewAlev4List(String alev3Code);
	
	public List<ViewActivitycenterAcnt> getViewActivitycenterAcntList();
	public List<ViewAccountClass> getViewAccountClassList();
	public List<ViewAccountCategory> getViewAccountCategoryList();
	public void saveCbkDetails(List<CBKDetails> lstCBKDetails);
	public List<CBKDetails> getCbkDetails(BigDecimal cbkHdrSeqId,BigDecimal cbkLineSeqId);
	public boolean deleteCbkDetails(BigDecimal cbkDetailsId,String userName);
	public void saveCbkTotals(List<CBKTotals> lstCBKTotals);
	public List<CBKTotals> getCBKTotals(BigDecimal cbkHdrSeqId,BigDecimal cbkLineSeqId);
	public boolean deleteCbkTotals(BigDecimal cbkTotalSeqId,String userName);
	
	public java.util.HashMap<String ,BigDecimal> saveCBKHeaderAndLines(CBKHeader cBKHeader,CBKLines cBKLines);
	public java.util.HashMap<String ,BigDecimal> saveMainCBKHeaderAndLines(CBKHeader cBKHeader,List<CBKLines> lstCBKLines);
	public List<CBKLines> getcbkHdrLinesDetails(String reportNo, BigDecimal applicationCountryId);
	public String checkCbkDetailsAndTotalCount(BigDecimal cbkHdrSeqiId,BigDecimal cbkLineSeqId,String lineType,String userName);
	
	public String getCBKReportProcedure(String reportFrequency,Date weekFromDate,Date weekToDate,Date monthFromDate,Date monthToDate,Date quarFromDate,Date quarToDate,String loginUser);
	
	public List<ViewExUnmappedGLS> getViewExUnmappedGLSList();
	
	public void deleteConfirmation(BigDecimal cbkHdrSeqiId,BigDecimal cbkLineSeqId,String userName);
	public List<CBKHeader> getcbkHdrDetails(String reportNo, BigDecimal applicationCountryId);

}
