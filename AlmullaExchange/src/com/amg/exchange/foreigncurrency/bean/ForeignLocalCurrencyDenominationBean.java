/*package com.amg.exchange.foreigncurrency.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
 
@Component("fcLocalCurrencyDenominationBean")
@SessionScoped
public class ForeignLocalCurrencyDenominationBean<T> implements Serializable {
 
    
	private static final long serialVersionUID = 717035286130297250L;
	
	Responsible to show total amount of entered cash 
	private String totalValue = null;
	
	Responsible to set Total Sale value
	private String totalSale = null;
    private String holdTotalSale = null;
	
	Responsible to control all data in Datatable
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
   
    @Autowired
    ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
    
    public String getTotalValue() {
		return totalValue;
	}
	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}
	
	public String getTotalSale() {
		*//**
		 * This is needed, because for i18n implementation, form will be submitted, that time no parameter it will get as totalSale, then it will 
		 * reseted to 0, that I am managing here
		 *//*
		if(holdTotalSale == null){
			holdTotalSale = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("totalSale");
		}
		return holdTotalSale; 
	}
	public void setTotalSale(String totalSale) {
		this.totalSale = totalSale;
	}
	
    *//**
     * Responsible to hold datatable data, and send it back when called from page
     * @return
     *//*
    public ArrayList<ForeignLocalCurrencyDataTable> getLstData() {

    	Checking that it's first time or not, first time list size will be 0    	
    	if(lstData.size() == 0) {
    		Responsible to show serial number in datatable
    		int i=0;
    		Responsible to hold each row in different bean object of datatable 
    		ForeignLocalCurrencyDataTable item = null;
    		
    		TODO ...Country ID is Hard Coded  
    		List<CurrencyWiseDenomination> dataFromDb = foreignLocalCurrencyDenominationService.getLocalCurrencyDenomination(new BigDecimal(120));
    		
    		putting the value in list to show in datatable
    		for (CurrencyWiseDenomination element : dataFromDb) {
    			if(element.getExStock().getOpenQuantity()>0){
    				item = new ForeignLocalCurrencyDataTable(++i, element.getDenominationAmount(), "0", "", element.getExStock().getOpenQuantity());
    				lstData.add(item);
				}
			}
    		
    	}
    	
    	Responsible to keep sum of total amount of cash entered 
    	int totalSum = 0;
    	
    	Responsible to calculate sum of entered cash amount
    	for (ForeignLocalCurrencyDataTable element : lstData) {
    		if(element.getPrice().length()!=0) {
    			totalSum = totalSum + Integer.parseInt(element.getPrice());
    		}
		}
    	
    	setting the summation value in bean object
    	setTotalValue(String.valueOf(totalSum));
    	
    	Responsible to get the parameterised value, and populate here 
    	setTotalSale(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("totalSale"));
    	
        return lstData;
    }
    
    Custom method
    public void oncellEdit(ForeignLocalCurrencyDataTable bean) {
    	String result  = String.valueOf(Integer.parseInt((bean.getItem()).toString())*Integer.parseInt(bean.getQty()));
    	bean.setPrice(result);
    }
	
}*/