package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import KNET_WinEPTS_API.Receipt;
import KNET_WinEPTS_API.WinEPTS_Wrapper;

public class IposPinPad {
	/**
	 * @param args
	 */
	WinEPTS_Wrapper API = new WinEPTS_Wrapper();

	@SuppressWarnings("static-access")
	public IposPinPad() {
		// PinPad Initialization
		try {
			// Initiate the pin pad initialization process.
			API.PinpadInit();
			Receipt receipt = API.getReceipt();
			//System.out.println(receipt.receiptData);
			PaymentInitiation(new BigDecimal(1000));
		} catch (UnsupportedEncodingException e) {
			System.out.println("Error Occurs while Pin Pad Initialization:\t" + e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error Occurs while Pin Pad Initialization:\t" + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("static-access")
	public void PaymentInitiation(BigDecimal tranxAmount) {
		// Payment Initialization from the User
		try {
			// Initiate a Payment transaction for 100 fils, Cashier# and
			// receipt# = 0
			// Multiply the amount with 1000 to convert it to fils.
			int trnxAmount = (int) (tranxAmount.intValue() * 1000);
			System.out.println(trnxAmount);
			API.Payment(trnxAmount, "0", "0");
			Receipt receipt = API.getReceipt();
			// We check if there was a receipt in the previous transaction, if
			// there is, then view it in a pop up MessageBox window.
			if (receipt != null)
				System.out.println(receipt.receiptData);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		IposPinPad iposPinPad = new IposPinPad();
	}
}
