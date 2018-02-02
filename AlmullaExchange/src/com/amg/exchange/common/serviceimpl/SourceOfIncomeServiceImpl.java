package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.ISourceOfIncomeDao;
import com.amg.exchange.common.service.ISourceOfIncomeService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
 
@Service
public class SourceOfIncomeServiceImpl implements ISourceOfIncomeService {

	
	@Autowired
	ISourceOfIncomeDao isourceOfIncomeDao;
	
	
	 

	 
	@Override
	@Transactional
	public List<SourceOfIncome> getAllRecords() {
	 
		return isourceOfIncomeDao.getAllRecords();
	}

	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal sourceOfIncomePk,
			BigDecimal srcIncomeDescEnglishPk, BigDecimal srcIncomeArabicPk) {
	  isourceOfIncomeDao.deleteRecordPermanently(sourceOfIncomePk,srcIncomeDescEnglishPk,srcIncomeArabicPk);
		
	}

	@Override
	@Transactional
	public void activateRecord(BigDecimal  srcIncomePk, String userName) {
		isourceOfIncomeDao.activateRecord(srcIncomePk,userName);
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveOrUpdate(SourceOfIncome sourceOfIncome,
			SourceOfIncomeDescription sourceOfIncomeEngDesc,
			SourceOfIncomeDescription sourceOfIncomeArabicDesc) {
		   isourceOfIncomeDao.saveOrUpdate(sourceOfIncome,sourceOfIncomeEngDesc,sourceOfIncomeArabicDesc);
			 
		 }

	@Override
	@Transactional
	public List<SourceOfIncomeDescription> getSourceOfIncomeDesc(
			BigDecimal sourceOfIncId) {
 
		return isourceOfIncomeDao.getSourceOfIncomeDesc(sourceOfIncId);
	}

	@Override
	@Transactional
	public List<SourceOfIncome> getSourceOfIncomeRecordsBanedOncode(
			String sourceCode) {
	 
		return isourceOfIncomeDao.getSourceOfIncomeRecordsBanedOncode(sourceCode);
	}

	@Override
	@Transactional
	public List<String> getAutoCompleteSourceOfIncomeCode(String query) {
	 
		return isourceOfIncomeDao.getAutoCompleteSourceOfIncomeCode(query);
	}

	@Override
	@Transactional
	public List<SourceOfIncomeDescription> getAllUnApprovedRecords() {
		 
		return isourceOfIncomeDao.getAllUnApprovedRecords();
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal sourceOfOIncomePk, String userName) {
		 
		return isourceOfIncomeDao.approveRecord(sourceOfOIncomePk,userName) ;
	}

	 
	@Override
	@Transactional
	public String getSourceOfIncome(BigDecimal sourceId){
		 
		return isourceOfIncomeDao.getSourceOfIncome(sourceId) ;
	}

	@Override
	@Transactional
	public String getPurposeOfTransaction(BigDecimal purposeId){
		 
		return isourceOfIncomeDao.getPurposeOfTransaction(purposeId) ;
	}

	@Override
	@Transactional
	public BigDecimal getSourceOfIncomeIdBasedOnName(String sourceDesc,BigDecimal languageId) {
 
		return isourceOfIncomeDao.getSourceOfIncomeIdBasedOnName(sourceDesc,languageId);
	}

	@Override
	@Transactional
	public List<SourceOfIncomeDescription> getSourceOfIncomeRecordsBanedOnCode(
			String sourceCode) {
 
		return isourceOfIncomeDao.getSourceOfIncomeRecordsBanedOnCode(sourceCode);
	}

 


}
