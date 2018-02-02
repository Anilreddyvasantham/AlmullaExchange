package com.amg.exchange.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

import com.aciworldwide.commerce.gateway.plugins.e24PaymentPipe;

public class KnetUtils implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static HashMap<String, String> knetInitialize(String trckid, String udf1, String udf2, String udf3, String udf4, String udf5, String amount) {

		e24PaymentPipe pipe = new e24PaymentPipe();
		HashMap<String, String> hm = new HashMap<String, String>();
		try {

			ResourceBundle props = ResourceBundle.getBundle("com.amg.exchange.resource.config");
			// Standard properties to be set for KNET
			pipe.setAction(props.getString("action"));
			pipe.setCurrency(props.getString("currency"));
			pipe.setLanguage(props.getString("language"));
			pipe.setResponseURL(props.getString("responseURL"));
			pipe.setErrorURL(props.getString("responseURL"));
			pipe.setResourcePath(props.getString("resourcePath"));
			pipe.setAlias(props.getString("alias"));
			pipe.setAmt(amount);
			pipe.setTrackId(trckid); // Customer Reference.
			
			System.out.println("action"+props.getString("action"));
			System.out.println("currency"+props.getString("currency"));
			System.out.println("language"+props.getString("language"));
			System.out.println("response"+props.getString("responseURL"));
			System.out.println("error"+props.getString("responseURL"));
			System.out.println("resourcepath"+props.getString("resourcePath"));
			System.out.println("alias"+props.getString("alias"));
			System.out.println("amount"+amount);
			System.out.println("trackid"+trckid);
			
			
			if (udf1 != null) {
				pipe.setUdf1(udf1);
			}
			if (udf2 != null) {
				pipe.setUdf2(udf2);
			}
			if (udf3 != null) {
				pipe.setUdf3(udf3);
			}
			if (udf4 != null) {
				pipe.setUdf4(udf4);
			}
			if (udf5 != null) {
				pipe.setUdf5(udf5);
			}
			Short pipeValue = pipe.performPaymentInitialization();
			
						 
			if (pipeValue != pipe.SUCCESS) {
				System.out.println(pipe.getErrorMsg());
				System.out.println(pipe.getDebugMsg());
				throw new RuntimeException("Problem while sending transaction to KNET - Error Code KU-KNETINIT");
			}
			
			// get results
			String payID = pipe.getPaymentId();
			String payURL = pipe.getPaymentPage();
						
			hm.put("PayId", new String(payID));
			hm.put("PayUrl", new String(payURL));

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return hm;
	}

	public static void knetPay(String payurl, String payid) throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect(payurl + "?paymentId=" + payid);
	}

}
