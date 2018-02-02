package com.amg.exchange.wuh2h.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wuh2h.model.DynamicFileds;
import com.amg.exchange.wuh2h.model.DynamicPurposeCode;
import com.amg.exchange.wuh2h.model.WUCorridorCountry;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@SuppressWarnings({ "unused" })
@Component("wuh2hPurpose")
@Scope("session")
public class WUPurposeCodeBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WUPurposeCodeBean.class);

	/*
	 * Autowire Configuration
	 */

	@Autowired
	ApplicationContext context;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IWUH2HService iwuh2hService;

	public WUPurposeCodeBean() {
		fetchWUCorridorCountry();
	}

	/*
	 * Western Union Page Navigation
	 */
	public void pageNavigation() {
		try {
			//clear();
			fetchWUCorridorCountry();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hdynamicpurposecode.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	
	List<WUCorridorCountry> corridorCountryList = new ArrayList<WUCorridorCountry>();	
	public void fetchWUCorridorCountry() {

		if (corridorCountryList != null || !corridorCountryList.isEmpty()) {
			corridorCountryList.clear();
		}
		try {
			List<WUCorridorCountry> lstcountry = iwuh2hService.getWUDynamicFieldCountry();
			if (lstcountry != null && lstcountry.size() != 0) {
				corridorCountryList.addAll(lstcountry);
			}
		} catch (NullPointerException ne) {
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"
					+ ne.getMessage());
			setErrorMessage("Method Name::fetchWUCorridorCountry()" + ne.getMessage());
			RequestContext.getCurrentInstance()
					.execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"
					+ exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void fetchDynamicPurpose(){
		
		if (purposeList != null || !purposeList.isEmpty()) {
			purposeList.clear();
		}
		
		WUDynamicPurposeCodeDataTable wuDynamicPurposeCodeDataTable = null;
		dynamicPurposeList = iwuh2hService.getWUDynamicPurposeCode(getIsoCountryCode());
		
		for(DynamicPurposeCode purpose:dynamicPurposeList){
			wuDynamicPurposeCodeDataTable = new WUDynamicPurposeCodeDataTable();
			
			wuDynamicPurposeCodeDataTable.setFieldId(purpose.getFieldId());
			wuDynamicPurposeCodeDataTable.setFieldName(purpose.getFieldName());
			wuDynamicPurposeCodeDataTable.setPurposeCode(purpose.getPurposeCode());
			wuDynamicPurposeCodeDataTable.setPurposeCodeDesc(purpose.getPurposeCodeDesc());
			wuDynamicPurposeCodeDataTable.setIsoCountryCode(purpose.getIsoCountryCode());
			
			purposeList.add(wuDynamicPurposeCodeDataTable);
		}
		
		
	}
	
	private String isoCountryCode;
	private String errorMessage;
	private List<WUDynamicPurposeCodeDataTable> purposeList = new ArrayList<WUDynamicPurposeCodeDataTable>();
	
	private List<DynamicPurposeCode> dynamicPurposeList = new ArrayList<DynamicPurposeCode>();

	public String getIsoCountryCode() {
		return isoCountryCode;
	}

	public void setIsoCountryCode(String isoCountryCode) {
		this.isoCountryCode = isoCountryCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<WUCorridorCountry> getCorridorCountryList() {
		return corridorCountryList;
	}

	public void setCorridorCountryList(List<WUCorridorCountry> corridorCountryList) {
		this.corridorCountryList = corridorCountryList;
	}

	public List<WUDynamicPurposeCodeDataTable> getPurposeList() {
		return purposeList;
	}

	public void setPurposeList(List<WUDynamicPurposeCodeDataTable> purposeList) {
		this.purposeList = purposeList;
	}
	
	
}
