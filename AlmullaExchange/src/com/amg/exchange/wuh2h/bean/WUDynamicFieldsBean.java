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
import com.amg.exchange.wuh2h.model.WUCorridorCountry;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@Component("wuh2hFields")
@Scope("session")
public class WUDynamicFieldsBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WUDynamicFieldsBean.class);

	/*
	 * Autowire Configuration
	 */

	@Autowired
	ApplicationContext context;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IWUH2HService iwuh2hService;

	public WUDynamicFieldsBean() {
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
					.redirect("../wuh2h/wuh2hdynamicfields.xhtml");
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
	
	public void fetchDynamicField(){
		if (fieldList != null || !fieldList.isEmpty()) {
			fieldList.clear();
		}
		
		WUDynamicFieldsDataTable wuDynamicFieldsDataTable = null;
		dynamicFieldList = iwuh2hService.getWUDynamicFields(getIsoCountryCode());
		
		for(DynamicFileds fields:dynamicFieldList){
			wuDynamicFieldsDataTable = new WUDynamicFieldsDataTable();
			
			wuDynamicFieldsDataTable.setCountryISOCode(fields.getCountryISOCode());
			wuDynamicFieldsDataTable.setFieldId(fields.getFieldId());
			wuDynamicFieldsDataTable.setFieldName(fields.getFieldName());
			wuDynamicFieldsDataTable.setFieldRequired(fields.getFieldRequired());
			wuDynamicFieldsDataTable.setFieldType(fields.getFieldType());
			wuDynamicFieldsDataTable.setFieldLength(fields.getFieldLength());
			
			fieldList.add(wuDynamicFieldsDataTable);
		}
		
		
	}
	
	private String isoCountryCode;
	private String errorMessage;
	private List<WUDynamicFieldsDataTable> fieldList = new ArrayList<WUDynamicFieldsDataTable>();
	
	private List<DynamicFileds> dynamicFieldList = new ArrayList<DynamicFileds>();

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

	public List<WUDynamicFieldsDataTable> getFieldList() {
		return fieldList;
	}

	public void setFieldList(List<WUDynamicFieldsDataTable> fieldList) {
		this.fieldList = fieldList;
	}
	
	
	
}
