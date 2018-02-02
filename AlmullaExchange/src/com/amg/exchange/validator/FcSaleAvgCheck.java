package com.amg.exchange.validator;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.foreigncurrency.bean.ForeignCurrencySaleBean;
import com.amg.exchange.util.SessionStateManage;


@FacesValidator("com.amg.exchange.validator.FcSaleAvgCheck")
public class FcSaleAvgCheck implements Validator{
	 
		@SuppressWarnings("rawtypes")
		@Override
		public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
			SessionStateManage sessionStateManage = new SessionStateManage();
			ForeignCurrencySaleBean foreignCurrencySaleBean = (ForeignCurrencySaleBean)context.getApplication().evaluateExpressionGet(context, "#{foreignCurrencySaleBean}", ForeignCurrencySaleBean.class);
			List<String> result = foreignCurrencySaleBean.getForeignCurrencyPurchaseService().getFsAvg(value, foreignCurrencySaleBean.getCountryCurrencyId());
	         
	         if(result.get(0).equalsIgnoreCase("Y")){
	        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2), ""
	        	 		+ "Rate Should be Between"+ result.get(1) +" and "+result.get(2));
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 throw new ValidatorException(msg);
	         } 
		} 
}
