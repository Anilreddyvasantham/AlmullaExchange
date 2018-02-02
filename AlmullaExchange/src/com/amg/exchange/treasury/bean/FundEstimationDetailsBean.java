package com.amg.exchange.treasury.bean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.FundEstimationDetailsBeanService;
import com.amg.exchange.util.SessionStateManage;

@Component("fundEstimationDetailsBean")
@Scope("session")
public class FundEstimationDetailsBean<T> {
	
	SessionStateManage session = new SessionStateManage();
	List<FundEstimtaionDetailsDatatable> lstData = new ArrayList<FundEstimtaionDetailsDatatable>();
	
	@Autowired
	FundEstimationDetailsBeanService fundEstimationDetailsBeanService;
	
	@Autowired
	IGeneralService<T> iGeneralService;
	
	
	//public String getCountryName(BigDecimal languageId,BigDecimal countryId);
	
	public List<FundEstimtaionDetailsDatatable> getLstData() {
		return lstData;
	}
	public void setLstData(List<FundEstimtaionDetailsDatatable> lstData) {
		this.lstData = lstData;
	}
	
	
	
	
/*	
	public String populateValue() {
		
		 lstData.clear();
		List<FundEstimationDetails> data = fundEstimationDetailsBeanService.getData(session.getCountryId());
		int i =1;
		
		for (FundEstimationDetails fundEstimationDetails : data) {
			lstData.add(new FundEstimtaionDetailsDatatable(i++, new SimpleDateFormat("dd/MM/yyyy").format(fundEstimationDetails.getProjectionDate()) , 
					//fundEstimationDetailsBeanService.getCountryName(fundEstimationDetails.getExFundEstimtaionDeatilsForBankCountry().getCountryId()), 
					//Added by kani for 
					iGeneralService.getCountryName(session.getLanguageId(),fundEstimationDetails.getExFundEstimtaionDeatilsForBankCountry().getCountryId()), 
					//Added by kani end
					
					fundEstimationDetails.getExBankMaster().getBankFullName(), 
					round(fundEstimationDetails.getFundEstimtaionId().getUsdCurrentBalanace().doubleValue(), fundEstimationDetailsBeanService.getDecimalValue(fundEstimationDetails.getExCurrenyMaster().getCurrencyId())),
					0.0, 
					fundEstimationDetails.getSalesForeignCurrencyProjection().doubleValue(),
					fundEstimationDetails.getIkonRate().doubleValue(),
					round(fundEstimationDetails.getUsdValue().doubleValue(), fundEstimationDetailsBeanService.getDecimalValue(fundEstimationDetails.getExCurrenyMaster().getCurrencyId())),fundEstimationDetails.getExBankMaster().getBankCode()));
		}
		
		return "fundEstimationDetails";
	}*/
	
	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}
	
}
