package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import org.apache.poi.hssf.record.crypto.Biff8DecryptingStream;
import org.codehaus.groovy.antlr.SourceInfo;

import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;

public interface ISourceOfIncomeService {
	
	public void saveOrUpdate(SourceOfIncome sourceOfIncome,SourceOfIncomeDescription sourceOfIncomeEngDesc,SourceOfIncomeDescription sourceOfIncomeArabicDesc);
	//public void saveOrUpdateDesc(SourceOfIncomeDescription sourceOfIncomeDesc);
	public  List<SourceOfIncome> getAllRecords();
	public List<SourceOfIncomeDescription>  getSourceOfIncomeDesc(BigDecimal sourceOfIncId);
	public void deleteRecordPermanently(BigDecimal  sourceOfIncomePk,BigDecimal srcIncomeDescEnglishPk,BigDecimal srcIncomeArabicPk);
	public void activateRecord(BigDecimal  sourceOfIncomePk,String userName);
	public List<SourceOfIncome> getSourceOfIncomeRecordsBanedOncode(String  sourceCode);
	public List<SourceOfIncomeDescription> getSourceOfIncomeRecordsBanedOnCode(String  sourceCode);
	public List<String> getAutoCompleteSourceOfIncomeCode(String query);
	public List<SourceOfIncomeDescription> getAllUnApprovedRecords();
	public String approveRecord(BigDecimal sourceOfOIncomePk,String userName);
	
	public String getSourceOfIncome(BigDecimal sourceId);
	public String getPurposeOfTransaction(BigDecimal purposeId);
	
	
	public BigDecimal getSourceOfIncomeIdBasedOnName(String sourceDesc,BigDecimal languageId);
	
	
	
	
}
