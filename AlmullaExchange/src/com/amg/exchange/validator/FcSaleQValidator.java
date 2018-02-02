package com.amg.exchange.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.primefaces.context.RequestContext;

import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchaseBean;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencySaleBean;

@FacesValidator("com.amg.exchange.validator.FcSaleQValidator")
public class FcSaleQValidator  implements Validator{
	 
		private static final String FCQ_PATTERN = "^[0-9-]+";
	 
		private Pattern pattern;
		private Matcher matcher;
	 
		public FcSaleQValidator(){
			  pattern = Pattern.compile(FCQ_PATTERN);
		}
	 
		@SuppressWarnings({ "rawtypes" })
		@Override
		public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
			
			
			if(value!=null && value.toString().charAt(0)=='-'){
				RequestContext.getCurrentInstance().execute("dlgNegNotAllowed.show();");
				FacesMessage msg =	new FacesMessage("Please Enter valid Quantity.", "Quantity Should be Valid.");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);
			} else {
				matcher = pattern.matcher(value.toString());
				
				if(!matcher.matches()) {
					FacesMessage msg =	new FacesMessage("Invalid No of Quantity.", "Invalid No of Quantity.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				} 
				
				ForeignCurrencySaleBean foreignCurrencySaleBean = (ForeignCurrencySaleBean)context.getApplication().evaluateExpressionGet(context, "#{foreignCurrencySaleBean}", ForeignCurrencySaleBean.class);
				String result = foreignCurrencySaleBean.checkStockAvailability(value, foreignCurrencySaleBean.getDenomIdCheckFor());
				/*if(result.equalsIgnoreCase("Y")){
					FacesMessage msg =	new FacesMessage("Enter Quantity within Available Stock", "Enter Quantity within Available Stock.");
					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				}*/
				foreignCurrencySaleBean.setValidNoNotes(value.toString());
			}
			}
}
