package com.amg.exchange.bco.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.treasury.bean.FundEstimationBean;
import com.amg.exchange.treasury.model.BankAccount;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IBankAccountService;
import com.amg.exchange.treasury.service.IBankApprovalService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.SessionStateManage;

@Component("hoco")
@Scope("session")
public class HeadOfficeComplianceOfficerBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private static final Logger LOG = Logger
			.getLogger(HeadOfficeComplianceOfficerBean.class);
	
	
	
	private boolean headOfficeRender=false;
	
	public void pageNavigation() {
		setHeadOfficeRender(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
				.redirect("../bco/headofficecomplianceofficer.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void submitBranchOfficer(){
		
	}
	
	
	//Properties get/set 
	public boolean isHeadOfficeRender() {
		return headOfficeRender;
	}

	public void setHeadOfficeRender(boolean headOfficeRender) {
		this.headOfficeRender = headOfficeRender;
	}

}
