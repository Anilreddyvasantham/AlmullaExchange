package com.amg.exchange.cashtransfer.bean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.model.NonConfirmCashFlow;
import com.amg.exchange.cashtransfer.model.NonConfirmCashFlowDetails;
import com.amg.exchange.cashtransfer.service.INonConformCashFlowService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("nonConfirmCashFlowBean")
@Scope("session")
public class NonConfirmCashFlowBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(NonConfirmCashFlowBean.class);
	
	private BigDecimal countryBranchId;
	private String countryBranchName;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private String fromCashier;
	private String toCashier;
	private Date documentDate;
	
	private List<NonConfirmCashFlowDetails> nonConfirmCashFlowDetailsList;
	private List<NonConfirmCashFlow> nonConfirmCashFlowList;
	
	
	INonConformCashFlowService nonConformCashFlowService;
	
	

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public List<NonConfirmCashFlow> getNonConfirmCashFlowList() {
		return nonConfirmCashFlowList;
	}

	public void setNonConfirmCashFlowList(List<NonConfirmCashFlow> nonConfirmCashFlowList) {
		this.nonConfirmCashFlowList = nonConfirmCashFlowList;
	}

	

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getCountryBranchName() {
		return countryBranchName;
	}

	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	public BigDecimal getDocumentFinancialYear() {
		return documentFinancialYear;
	}

	public void setDocumentFinancialYear(BigDecimal documentFinancialYear) {
		this.documentFinancialYear = documentFinancialYear;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public String getFromCashier() {
		return fromCashier;
	}

	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}

	public String getToCashier() {
		return toCashier;
	}

	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
	}

	public List<NonConfirmCashFlowDetails> getNonConfirmCashFlowDetailsList() {
		return nonConfirmCashFlowDetailsList;
	}

	public void setNonConfirmCashFlowDetailsList(List<NonConfirmCashFlowDetails> nonConfirmCashFlowDetailsList) {
		this.nonConfirmCashFlowDetailsList = nonConfirmCashFlowDetailsList;
	}

	public INonConformCashFlowService getNonConformCashFlowService() {
		return nonConformCashFlowService;
	}

	@Autowired
	public void setNonConformCashFlowService(INonConformCashFlowService nonConformCashFlowService) {
		this.nonConformCashFlowService = nonConformCashFlowService;
	}

	public void getAllNonConformCashFlowList() {
		setNonConfirmCashFlowList(getNonConformCashFlowService().getAllNonConformCashFlowList());
	}
	private SessionStateManage session = new SessionStateManage();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	public void pageNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "nonconfirmcash.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../cashtransfer/nonconfirmcash.xhtml");
		} catch (Exception e) {
			LOGGER.info("Problem to redirect");
		}
	}

	public void exit() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
	}
	
/*	
	private BigDecimal countryBranchId;
	private String countryBranchName;
	private BigDecimal documentFinancialYear;
	private BigDecimal documentNo;
	private String fromCashier;
	private String toCashier;
	private Date documentDate;*/

	public void lookUp(NonConfirmCashFlow datatable) {
		LOGGER.info("@############################################");
		LOGGER.info(datatable);
		
		setCountryBranchId(datatable.getCountryBranchId());
		setCountryBranchName(datatable.getFromBranchName());
		setDocumentFinancialYear(datatable.getDocumentFinancialYear());
		setDocumentNo(datatable.getDocumentNo());
		setDocumentDate(datatable.getDocumentDate());
		setFromCashier(datatable.getFromCashier());
		setToCashier(datatable.getToCashier());
		
		LOGGER.info("Cash header id " + datatable.getCashHeaderId());
		
		setNonConfirmCashFlowDetailsList(getNonConformCashFlowService().getdetails(datatable.getCashHeaderId()));
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../cashtransfer/nonconfirmcashdetails.xhtml");
		} catch (Exception e) {
			LOGGER.info("Problem to redirect");
		}
		LOGGER.info("@############################################");
	}
}
