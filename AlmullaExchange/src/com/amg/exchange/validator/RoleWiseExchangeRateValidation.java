package com.amg.exchange.validator;

import java.math.BigDecimal;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchaseBean;
import com.amg.exchange.util.SessionStateManage;

@FacesValidator("com.amg.exchange.validator.RoleWiseExchangeRateValidation")
public class RoleWiseExchangeRateValidation implements Validator {
	
	@SuppressWarnings("rawtypes")
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException { 
	SessionStateManage sessionStateManage = new SessionStateManage();
		ForeignCurrencyPurchaseBean foreignCurrencyPurchaseBean = (ForeignCurrencyPurchaseBean)context.getApplication().evaluateExpressionGet(context, "#{foreignCurrencyPurchaseBean}", ForeignCurrencyPurchaseBean.class);
		BigDecimal roleId = new BigDecimal(sessionStateManage.getRoleId());
		List<String> result = foreignCurrencyPurchaseBean.getForeignCurrencyPurchaseService().getBetweenRolewiseExchangeRate(value,roleId);
       // System.out.println("Validation ------------ 1>> "+result.get(1)+"validation ----------- 2 >>"+result.get(2)); 
		
         if(result.get(0).equalsIgnoreCase("Y")){
        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2), ""
        	 		+ "Rate Should be Between"+ result.get(1) +" and "+result.get(2));
			 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			 
			 
			 throw new ValidatorException(msg);
         } 
	}

}
