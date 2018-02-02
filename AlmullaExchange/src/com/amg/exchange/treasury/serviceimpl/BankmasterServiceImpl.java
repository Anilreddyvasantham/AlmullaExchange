package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.treasury.dao.IBankMasterContactDetailsDao;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IBankMasterContactDetailsService;

@SuppressWarnings("serial")
@Service("bankMasterServiceImpl")
public class BankmasterServiceImpl<T> implements IBankMasterContactDetailsService<T>, Serializable{
	
	@Autowired
	IBankMasterContactDetailsDao<T> bankMasterContactDetailsDao;
	public IBankMasterContactDetailsDao<T> getBankMasterContactDetailsDao() {
		return bankMasterContactDetailsDao;
	}
	public void setBankMasterContactDetailsDao(
			IBankMasterContactDetailsDao<T> bankMasterContactDetailsDao) {
		this.bankMasterContactDetailsDao = bankMasterContactDetailsDao;
	}

	@Override
	@Transactional
	public List<BankMaster> getBankMasterInfo() {
		return getBankMasterContactDetailsDao().getBankMasterInfo();
	}
	
	@Transactional
	@Override
	public void saveBankMasterContactDetails(BankContactDetails bankContact) {
		getBankMasterContactDetailsDao().saveBankMasterContactDetails(bankContact);
		
	}
	
	@Transactional
	@Override
	public List<BankContactDetails> getbankContactInfo(BigDecimal bankId) {
		return getBankMasterContactDetailsDao().getbankContactInfo(bankId);
	}
	
	@Transactional
	@Override
	public List<BankMaster> getbankCountryInfo(BigDecimal bankId) {
		return getBankMasterContactDetailsDao().getbankCountryInfo(bankId);
	}
	@Override
	@Transactional
	public List<ZoneMasterDesc> getZoneList(BigDecimal languageId) {
		return getBankMasterContactDetailsDao().getZoneList(languageId);
	}
	
	
	@Override
	@Transactional
	public void removeContactDetails(BigDecimal bankContactDetailsId,String userName,boolean delete) {
		 getBankMasterContactDetailsDao().removeContactDetails(bankContactDetailsId,userName,delete);
	}
	@Override
	@Transactional
	public String getZoneName(BigDecimal zoneId) {
		// TODO Auto-generated method stub
		return getBankMasterContactDetailsDao().getZoneName(zoneId);
	}
	
	@Override
	@Transactional
	public List<BankContactDetails> getContactId(BigDecimal bankId, BigDecimal zoneId) {
		// TODO Auto-generated method stub
		return getBankMasterContactDetailsDao().getpkContactId(bankId, zoneId);
	}
	
	@Override
	@Transactional
	public List<BankContactDetails> getContactslist(BigDecimal bankId) {
		// TODO Auto-generated method stub
		return getBankMasterContactDetailsDao().getContactList(bankId);
	}
	
	@Override
	@Transactional
	public List<BankAccountLength> getBanklength(BigDecimal banid) {
		// TODO Auto-generated method stub
		return getBankMasterContactDetailsDao().getlistBanklength(banid);
	}
	
	

} 
