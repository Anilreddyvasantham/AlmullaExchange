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
import com.amg.exchange.wuh2h.model.WUCorridorCurrency;
import com.amg.exchange.wuh2h.service.IWUH2HService;

@SuppressWarnings({ "unused"})
@Component("wuh2hCorridorCurrency")
@Scope("session")
public class WUCorridorCurrencyBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(WUCorridorCurrencyBean.class);

	/*
	 * Autowire Configuration
	 */

	@Autowired
	ApplicationContext context;

	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IWUH2HService iwuh2hService;

	public WUCorridorCurrencyBean() {
		
	}

	/*
	 * Western Union Page Navigation
	 */
	public void pageNavigation() {
		try {
			//clear();
			fetchWUCorridorCountry();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../wuh2h/wuh2hcorridorcurrency.xhtml");
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
	
	public void fetchCorridorCurrency(){
		
		if (corridorCurrencyDataTable != null || !corridorCurrencyDataTable.isEmpty()) {
			corridorCurrencyDataTable.clear();
		}
		
		WUCorridorCurrencyDataTable wuCorridorCurrencyDataTable = null;
		corridorCurrencyList = iwuh2hService.getWUCorridorCurrency(getIsoCountryCode());
		
		for(WUCorridorCurrency currency:corridorCurrencyList){
			wuCorridorCurrencyDataTable = new WUCorridorCurrencyDataTable();
			
			wuCorridorCurrencyDataTable.setCountryISOCode(currency.getCountryISOCode());
			wuCorridorCurrencyDataTable.setCountryNumberCode(currency.getCountryNumberCode());
			wuCorridorCurrencyDataTable.setCountryDescription(currency.getCountryDescription());
			wuCorridorCurrencyDataTable.setCurrencyNumberCode(currency.getCurrencyNumberCode());
			wuCorridorCurrencyDataTable.setCurrencyISOCode(currency.getCurrencyISOCode());
			wuCorridorCurrencyDataTable.setCurrencyDescription(currency.getCurrencyDescription());
			
			corridorCurrencyDataTable.add(wuCorridorCurrencyDataTable);
		}
		
		
	}
	
	private String isoCountryCode;
	private String errorMessage;
	private List<WUCorridorCurrencyDataTable> corridorCurrencyDataTable = new ArrayList<WUCorridorCurrencyDataTable>();
	
	private List<WUCorridorCurrency> corridorCurrencyList = new ArrayList<WUCorridorCurrency>();

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

	public List<WUCorridorCurrency> getCorridorCurrencyList() {
		return corridorCurrencyList;
	}

	public void setCorridorCurrencyList(
			List<WUCorridorCurrency> corridorCurrencyList) {
		this.corridorCurrencyList = corridorCurrencyList;
	}

	public List<WUCorridorCurrencyDataTable> getCorridorCurrencyDataTable() {
		return corridorCurrencyDataTable;
	}

	public void setCorridorCurrencyDataTable(
			List<WUCorridorCurrencyDataTable> corridorCurrencyDataTable) {
		this.corridorCurrencyDataTable = corridorCurrencyDataTable;
	}

	
	
}
