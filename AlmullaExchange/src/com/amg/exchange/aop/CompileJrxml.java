package com.amg.exchange.aop;

import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileJrxml {
	public static void main(String[] args) {
		
		
		try {
			
		//	RemittanceReceipt.jrxml
		//	decleration2500reportenglish.jrxml
		//	decleration1000reportenglish.jrxml
		//	dealticketfaxprint.jrxml
		//	dealticket.jrxml
		//	dealpayment.jrxml
		//RefundRequestOfficeCopy.jrxml
		//RefundRequestCustomerCopy
			
		    String reportName ="D:\\AlmullaExchange_Kuwait\\AlmullaExchangeService_Kuwait\\WebContent\\reports\\design\\fxDealRegisterInquiry.jrxml";
		    String str[]=reportName.split("\\.");
		    String reportName_jasper = str[0] + ".jasper";
		    // compiles jrxml
		    JasperCompileManager.compileReportToFile(reportName, reportName_jasper);
		    System.out.println("Jasper Compiled Successfully");
		  } catch (Exception e) {
		   throw new RuntimeException("It's not possible to generate the jasper report.", e);
		  }
		
			
		
		
		
		}
}
