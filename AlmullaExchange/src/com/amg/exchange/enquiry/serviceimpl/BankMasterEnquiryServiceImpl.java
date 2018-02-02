package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.IBankMasterEnquiryDao;
import com.amg.exchange.enquiry.service.IBankMasterEnquiryService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankIndicatorDescription;

@Service
public class BankMasterEnquiryServiceImpl implements IBankMasterEnquiryService{

	@Autowired
	IBankMasterEnquiryDao bankMasterEnquiryDao;
	
	
	@Override
	@Transactional
	public List<BankIndicatorDescription> getAllBankIndicators(BigDecimal languageId){
		return bankMasterEnquiryDao.getAllBankIndicators(languageId);
	}
	
	@Override
	@Transactional
	public List<BankAccountLength> getAllBankLengthRecords(BigDecimal bankId) {
		return bankMasterEnquiryDao.getAllBankLengthRecords(bankId);
	}
	
	@Override
	@Transactional
	public List<BankContactDetails> getAllBankContactDetailsRecords(BigDecimal bankId){
		return bankMasterEnquiryDao.getAllBankContactDetailsRecords(bankId);
	}
	
	@Override
	@Transactional
	public List<BankAccountDetails> getAllBankAccountDetailsRecords(BigDecimal bankId){
		return bankMasterEnquiryDao.getAllBankAccountDetailsRecords(bankId);
	}
	
	@Override
	@Transactional
	public String getZoneName(BigDecimal zoneId,BigDecimal languageId){
		return bankMasterEnquiryDao.getZoneName(zoneId,languageId);
	}
	
	@Override
	@Transactional
	public String getBankIndicatorName(BigDecimal bankIndId,BigDecimal languageId){
		return bankMasterEnquiryDao.getBankIndicatorName(bankIndId,languageId);
	}
	
	@Override
	@Transactional
	public List<BankApplicability> getBankMasterDetails(BigDecimal bankCountryId,BigDecimal bankIndId){
		return bankMasterEnquiryDao.getBankMasterDetails(bankCountryId,bankIndId);
	}
	
	@Override
	@Transactional
	public String getBankAccountTypeName(BigDecimal accounTypeId,BigDecimal languageId){
		return bankMasterEnquiryDao.getBankAccountTypeName(accounTypeId,languageId);
	}

}
