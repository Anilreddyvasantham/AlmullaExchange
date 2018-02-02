package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
 
 

public interface ISourceOfIncomeDao {

	public void saveOrUpdate(SourceOfIncome sourceOfIncome,SourceOfIncomeDescription sourceOfIncomeEngDesc,SourceOfIncomeDescription sourceOfIncomeArabicDesc);
	public  List<SourceOfIncome> getAllRecords();
	public void deleteRecordPermanently(BigDecimal  sourceOfIncomePk,BigDecimal srcIncomeDescEnglishPk,BigDecimal srcIncomeArabicPk);
	public void activateRecord(BigDecimal  sourceOfIncomePk,String userName);
	public List<SourceOfIncomeDescription> getSourceOfIncomeDesc(BigDecimal sourceOfIncId);
	public List<SourceOfIncome> getSourceOfIncomeRecordsBanedOncode(
			String sourceCode);
	public List<SourceOfIncomeDescription> getSourceOfIncomeRecordsBanedOnCode(
			String sourceCode);
	public List<String> getAutoCompleteSourceOfIncomeCode(String query);
	public List<SourceOfIncomeDescription> getAllUnApprovedRecords() ;
	public String approveRecord(BigDecimal sourceOfOIncomePk, String userName);
 
	public String getSourceOfIncome(BigDecimal sourceId);
	public String getPurposeOfTransaction(BigDecimal purposeId);
	public BigDecimal getSourceOfIncomeIdBasedOnName(String sourceDesc,BigDecimal languageId);
}
