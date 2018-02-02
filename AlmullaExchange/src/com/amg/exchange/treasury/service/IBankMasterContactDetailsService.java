package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;

public interface IBankMasterContactDetailsService<T> {
	
	public List<BankMaster> getBankMasterInfo(); 
	
	public void saveBankMasterContactDetails(BankContactDetails bankConatct);
	
	public List<BankContactDetails> getbankContactInfo(BigDecimal bankId); 
	
	public  List<BankMaster> getbankCountryInfo(BigDecimal id); 
	
	public List<ZoneMasterDesc> getZoneList(BigDecimal languageId);
	
	//Delete Contact Details From DB
	public void removeContactDetails(BigDecimal bankContactDetailsId,String userName,boolean delete);
	
	public String getZoneName(BigDecimal zoneId);
	
	public List<BankContactDetails> getContactId(BigDecimal bankId,BigDecimal zoneId);
	
	public List<BankContactDetails> getContactslist(BigDecimal bankId);
	
	public List<BankAccountLength> getBanklength(BigDecimal banid);

	
	
}
