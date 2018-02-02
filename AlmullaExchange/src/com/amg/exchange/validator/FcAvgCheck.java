package com.amg.exchange.validator;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchaseBean;
 
@FacesValidator("com.amg.exchange.validator.FcAvgCheck")
public class FcAvgCheck implements Validator{
 
	@SuppressWarnings("rawtypes")
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		ForeignCurrencyPurchaseBean foreignCurrencyPurchaseBean = (ForeignCurrencyPurchaseBean)context.getApplication().evaluateExpressionGet(context, "#{foreignCurrencyPurchaseBean}", ForeignCurrencyPurchaseBean.class);
		List<String> result = foreignCurrencyPurchaseBean.getForeignCurrencyPurchaseService().getFsAvg(value, foreignCurrencyPurchaseBean.getCurrencyId());
         
         if(result.get(0).equalsIgnoreCase("Y")){
        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2), ""
        	 		+ "Rate Should be Between"+ result.get(1) +" and "+result.get(2));
			 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			 throw new ValidatorException(msg);
         } 
	} 
}