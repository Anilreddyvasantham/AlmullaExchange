package com.amg.exchange.treasury.bean;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("breakOdLocBankBalForFund")
@Scope("session")
public class BreakupOfLocalBankBalanceForFundingBean {
	
	
	private  static Map<String, String> bankList;

static  {
	        bankList  = new HashMap<String, String>();
	        bankList.put("CBK", "cbk");
	        bankList.put("BB", "bb");
	 }
	         

	public Map<String, String> getBankList() {
		return bankList;
	}

	public void setBankList(Map<String, String> bankList) {
		this.bankList = bankList;
	}
	

}
