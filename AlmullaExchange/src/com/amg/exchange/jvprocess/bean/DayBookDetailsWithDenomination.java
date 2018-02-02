package com.amg.exchange.jvprocess.bean;

import java.io.Serializable;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;

public class DayBookDetailsWithDenomination implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DayBookDetails dayBookDetails;
	
	private List<ForeignCurrencyAdjust> lstForeignCurrencyAdjust;

	public DayBookDetails getDayBookDetails() {
		return dayBookDetails;
	}

	public void setDayBookDetails(DayBookDetails dayBookDetails) {
		this.dayBookDetails = dayBookDetails;
	}

	public List<ForeignCurrencyAdjust> getLstForeignCurrencyAdjust() {
		return lstForeignCurrencyAdjust;
	}

	public void setLstForeignCurrencyAdjust(
			List<ForeignCurrencyAdjust> lstForeignCurrencyAdjust) {
		this.lstForeignCurrencyAdjust = lstForeignCurrencyAdjust;
	}
	
	
	

}
