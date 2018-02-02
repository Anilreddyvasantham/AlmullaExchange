package com.amg.exchange.cbk.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cbk.dao.CBKReportsDAO;
import com.amg.exchange.cbk.model.CBKDetails;
import com.amg.exchange.cbk.model.CBKHeader;
import com.amg.exchange.cbk.model.CBKLines;
import com.amg.exchange.cbk.model.CBKTotals;
import com.amg.exchange.cbk.model.ViewExUnmappedGLS;
import com.amg.exchange.cbk.service.CBKReportsService;
import com.amg.exchange.jvprocess.model.ViewAccountCategory;
import com.amg.exchange.jvprocess.model.ViewAccountClass;
import com.amg.exchange.jvprocess.model.ViewActivitycenterAcnt;
import com.amg.exchange.jvprocess.model.ViewBusinessoperationAlev2;
import com.amg.exchange.jvprocess.model.ViewFranchiseAlev3;
import com.amg.exchange.jvprocess.model.ViewProductAlev4;
import com.amg.exchange.jvprocess.model.ViewmainActivityALev1;

@Service("cBKReportsServiceImpl")
public class CBKReportsServiceImpl implements CBKReportsService {
	
	@Autowired
	CBKReportsDAO cBKReportsDAO;
	
	
	@Override
	@Transactional
	public List<ViewmainActivityALev1> getViewAlev1List() {
		return cBKReportsDAO.getViewAlev1List();
	}
	
	@Override
	@Transactional
	public List<ViewBusinessoperationAlev2> getViewAlev2List(String alev1Code) {
		return cBKReportsDAO.getViewAlev2List(alev1Code);
	}
	
	@Override
	@Transactional
	public List<ViewFranchiseAlev3> getViewAlev3List(String alev2Code) {
		return cBKReportsDAO.getViewAlev3List(alev2Code);
	}
	
	@Override
	@Transactional
	public List<ViewProductAlev4> getViewAlev4List(String alev3Code) {
		return cBKReportsDAO.getViewAlev4List(alev3Code);
	}
	
	@Override
	@Transactional
	public List<ViewActivitycenterAcnt> getViewActivitycenterAcntList() {
		return cBKReportsDAO.getViewActivitycenterAcntList();
	}
	
	@Override
	@Transactional
	public HashMap<String, BigDecimal> saveCBKHeaderAndLines(
			CBKHeader cBKHeader, CBKLines cBKLines) {
		
		return cBKReportsDAO.saveCBKHeaderAndLines(cBKHeader, cBKLines);
	}

	@Override
	@Transactional
	public HashMap<String, BigDecimal> saveMainCBKHeaderAndLines(
			CBKHeader cBKHeader, List<CBKLines> lstCBKLines) {
		return cBKReportsDAO.saveMainCBKHeaderAndLines(cBKHeader, lstCBKLines);
	}

	@Override
	@Transactional
	public List<CBKLines> getcbkHdrLinesDetails(String reportNo,
			BigDecimal applicationCountryId) {
		
		return cBKReportsDAO.getcbkHdrLinesDetails(reportNo, applicationCountryId);
	}

	@Override
	@Transactional
	public String checkCbkDetailsAndTotalCount(BigDecimal cbkHdrSeqiId,
			BigDecimal cbkLineSeqId, String lineType,String userName) {
		
		return cBKReportsDAO.checkCbkDetailsAndTotalCount(cbkHdrSeqiId, cbkLineSeqId, lineType,userName);
	}

	@Override
	@Transactional
	public List<ViewAccountClass> getViewAccountClassList() {
		
		return cBKReportsDAO.getViewAccountClassList();
	}

	@Override
	@Transactional
	public List<ViewAccountCategory> getViewAccountCategoryList() {
		
		return cBKReportsDAO.getViewAccountCategoryList();
	}

	@Override
	@Transactional
	public void saveCbkDetails(List<CBKDetails> lstCBKDetails) {
		cBKReportsDAO.saveCbkDetails(lstCBKDetails);
	}

	@Override
	@Transactional
	public List<CBKDetails> getCbkDetails(BigDecimal cbkHdrSeqId, BigDecimal cbkLineSeqId) {
		return cBKReportsDAO.getCbkDetails(cbkHdrSeqId, cbkLineSeqId);
	}

	@Override
	@Transactional
	public boolean deleteCbkDetails(BigDecimal cbkDetailsId, String userName) {
		
		return cBKReportsDAO.deleteCbkDetails(cbkDetailsId, userName);
	}

	@Override
	@Transactional
	public void saveCbkTotals(List<CBKTotals> lstCBKTotals) {
		cBKReportsDAO.saveCbkTotals(lstCBKTotals);
		
	}

	@Override
	@Transactional
	public List<CBKTotals> getCBKTotals(BigDecimal cbkHdrSeqId,
			BigDecimal cbkLineSeqId) {
		
		return cBKReportsDAO.getCBKTotals(cbkHdrSeqId, cbkLineSeqId);
	}

	@Override
	@Transactional
	public boolean deleteCbkTotals(BigDecimal cbkTotalSeqId, String userName) {
		
		return cBKReportsDAO.deleteCbkTotals(cbkTotalSeqId, userName);
	}
	
	@Override
	@Transactional
	public String getCBKReportProcedure(String reportFrequency,Date weekFromDate,Date weekToDate,Date monthFromDate,Date monthToDate,Date quarFromDate,Date quarToDate,String loginUser) {
		
		return cBKReportsDAO.getCBKReportProcedure(reportFrequency, weekFromDate,weekToDate,monthFromDate,monthToDate,quarFromDate,quarToDate,loginUser);
	}
	
	@Override
	@Transactional
	public List<ViewExUnmappedGLS> getViewExUnmappedGLSList() {
		return cBKReportsDAO.getViewExUnmappedGLSList();
	}

	@Override
	@Transactional
	public void deleteConfirmation(BigDecimal cbkHdrSeqiId,
			BigDecimal cbkLineSeqId, String userName) {
		cBKReportsDAO.deleteConfirmation(cbkHdrSeqiId, cbkLineSeqId, userName);
		
	}

	@Override
	@Transactional
	public List<CBKHeader> getcbkHdrDetails(String reportNo,
			BigDecimal applicationCountryId) {
		// TODO Auto-generated method stub
		return cBKReportsDAO.getcbkHdrDetails(reportNo, applicationCountryId);
	}
}
