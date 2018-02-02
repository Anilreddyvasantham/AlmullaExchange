package com.amg.exchange.validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.amg.exchange.treasury.bean.BankTransferBean;
import com.amg.exchange.treasury.bean.FXDealSupplierBean;
import com.amg.exchange.treasury.bean.FXDetailInfoBean;

@FacesValidator("com.amg.exchange.validator.fx_DealRateValidator")
public class Fx_DealRateValidator implements Validator{
	
	private Pattern pattern;
	private Matcher matcher;
	String Component1,Component2,ComponentValue,str;
	
	private final static String ZeroChecking_PATTERN = "^[0]+(\\." +"[0]+)*$";
	
	public Fx_DealRateValidator(){
		  pattern = Pattern.compile(ZeroChecking_PATTERN);
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		
		ComponentValue=component.getClientId();

		try {
			String[] parts = ComponentValue.split(":");
			Component1 = parts[0];
			Component2 = parts[1];
			System.out.println("Components:"+Component1+"$$$$$$$$$"+Component2);
			} catch (Exception nme) 
			{
				System.out.println("No Validation Component");
			}
		
		if ("localexchangerateid".equals(Component2)) {
			System.out.println("Validation Component"+Component2);
			FXDetailInfoBean fXDetailInfoBean = (FXDetailInfoBean)context.getApplication().evaluateExpressionGet(context, "#{fxdetailinfobean}", FXDetailInfoBean.class);
			
			if(fXDetailInfoBean.getPurchaseCurrency()!=null){
				
			List<String> result = fXDetailInfoBean.getForeignCurrencyPurchaseService().getFsAvg(value, fXDetailInfoBean.getPurchaseCurrency());
	         
	         if(result.get(0).equalsIgnoreCase("Y")){
	        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result.get(1) +" and "+result.get(2), ""
	        	 		+ "Rate Should be Between"+ result.get(1) +" and "+result.get(2));
	        	 System.out.println("Came Here if"+msg);
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 throw new ValidatorException(msg);
	         }
	         
			}else{
				FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
	        	 System.out.println("Came Here if else"+msg);
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 throw new ValidatorException(msg);
			}
		}else if("averagerateid".equals(Component2)) {
			System.out.println("Validation Component"+Component2);
			FXDetailInfoBean fXDetailInfoBean = (FXDetailInfoBean)context.getApplication().evaluateExpressionGet(context, "#{fxdetailinfobean}", FXDetailInfoBean.class);
			if(fXDetailInfoBean.getSaleCurrency()!=null){

					List<String> result1 = fXDetailInfoBean.getForeignCurrencyPurchaseService().getFsAvg(value, fXDetailInfoBean.getSaleCurrency());
			        
					if(result1.get(0).equalsIgnoreCase("Y")){
			        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result1.get(1) +" and "+result1.get(2), ""
			        	 		+ "Rate Should be Between"+ result1.get(1) +" and "+result1.get(2));
			        	 System.out.println("Came Here else"+msg);
						 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						 throw new ValidatorException(msg);
						 }
			}else{
				FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
	        	 System.out.println("Came Here else else"+msg);
				 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				 throw new ValidatorException(msg);
			}
			
	     }else if("saleAmountId".equals(Component2)){
	    	 System.out.println("Validation Component"+Component2);
	    	 matcher = pattern.matcher(value.toString());
	    	 str = value.toString();
	    	 System.out.println("Validation Component for saleAmountId"+str);
				if(matcher.matches()){

					FacesMessage msg = new FacesMessage("Invalid Zero.",
							"Invalid Zero for " +Component2+ ". Shouldn't be zero");

					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				}
	    	    
	     }else if("salesLocalAmountId".equals(Component2)){
	    	 System.out.println("Validation Component"+Component2);
	    	 matcher = pattern.matcher(value.toString());
	    	 str = value.toString();
	    	 System.out.println("Validation Component for saleAmountId"+str);
				if(matcher.matches()){

					FacesMessage msg = new FacesMessage("Invalid Zero.",
							"Invalid Zero for " +Component2+ ". Shouldn't be zero");

					msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					throw new ValidatorException(msg);
				}
	    	    
	     }else if("bnkTrToExchangeRateid".equals(Component2)) {
				System.out.println("Validation Component"+Component2);
				BankTransferBean bankTransferBean = (BankTransferBean)context.getApplication().evaluateExpressionGet(context, "#{bankTransferBean}", BankTransferBean.class);
				if(bankTransferBean.getCurrencyIdTO()!=null){

						List<String> result1 = bankTransferBean.getForeignCurrencyPurchaseService().getFsAvg(value, bankTransferBean.getCurrencyIdTO());
				        
						if(result1.get(0).equalsIgnoreCase("Y")){
				        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result1.get(1) +" and "+result1.get(2), ""
				        	 		+ "Rate Should be Between"+ result1.get(1) +" and "+result1.get(2));
				        	 System.out.println("Came Here else"+msg);
							 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							 throw new ValidatorException(msg);
							 }
				}else{
					FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
		        	 System.out.println("Came Here else else"+msg);
					 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					 throw new ValidatorException(msg);
				}
				
		     }else if("bnkTrFromExchangeRateid".equals(Component2)) {
				System.out.println("Validation Component"+Component2);
				BankTransferBean bankTransferBean = (BankTransferBean)context.getApplication().evaluateExpressionGet(context, "#{bankTransferBean}", BankTransferBean.class);
				if(bankTransferBean.getCurrencyId()!=null){

						List<String> result1 = bankTransferBean.getForeignCurrencyPurchaseService().getFsAvg(value, bankTransferBean.getCurrencyId());
				        
						if(result1.get(0).equalsIgnoreCase("Y")){
				        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result1.get(1) +" and "+result1.get(2), ""
				        	 		+ "Rate Should be Between"+ result1.get(1) +" and "+result1.get(2));
				        	 System.out.println("Came Here else"+msg);
							 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
							 throw new ValidatorException(msg);
							 }
				}else{
					FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
		        	 System.out.println("Came Here else else"+msg);
					 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
					 throw new ValidatorException(msg);
				}
				
		     }else if("pdExchangeRate".equals(Component2)) {
					System.out.println("Validation Component"+Component2);
					FXDealSupplierBean fXDealSupplierBean = (FXDealSupplierBean)context.getApplication().evaluateExpressionGet(context, "#{fXDealSupplierBean}", FXDealSupplierBean.class);
					if(fXDealSupplierBean.getPdCurrencyId()!=null){

							List<String> result1 = fXDealSupplierBean.getForeignCurrencyPurchaseService().getFsAvg(value, fXDealSupplierBean.getPdCurrencyId());
					        
							if(result1.get(0).equalsIgnoreCase("Y")){
					        	 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result1.get(1) +" and "+result1.get(2), ""
					        	 		+ "Rate Should be Between"+ result1.get(1) +" and "+result1.get(2));
					        	 System.out.println("Came Here else"+msg);
								 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
								 throw new ValidatorException(msg);
								 }
					}else{
						FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
			        	 System.out.println("Came Here else else"+msg);
						 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
						 throw new ValidatorException(msg);
					}
					
		     }else if("sdAverageRate".equals(Component2)) {
		    	 System.out.println("Validation Component"+Component2);
		    	 FXDealSupplierBean fXDealSupplierBean = (FXDealSupplierBean)context.getApplication().evaluateExpressionGet(context, "#{fXDealSupplierBean}", FXDealSupplierBean.class);
		    	 if(fXDealSupplierBean.getPdCurrencyId()!=null){

		    		 List<String> result1 = fXDealSupplierBean.getForeignCurrencyPurchaseService().getFsAvg(value, fXDealSupplierBean.getPdCurrencyId());

		    		 if(result1.get(0).equalsIgnoreCase("Y")){
		    			 FacesMessage msg = new FacesMessage("Rate Should be Between"+ result1.get(1) +" and "+result1.get(2), ""
		    					 + "Rate Should be Between"+ result1.get(1) +" and "+result1.get(2));
		    			 System.out.println("Came Here else"+msg);
		    			 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    			 throw new ValidatorException(msg);
		    		 }
		    	 }else{
		    		 FacesMessage msg = new FacesMessage("Please Select Bank and Currency","Please Select Bank and Currency");
		    		 System.out.println("Came Here else else"+msg);
		    		 msg.setSeverity(FacesMessage.SEVERITY_ERROR);
		    		 throw new ValidatorException(msg);
		    	 }

}
	}
}
