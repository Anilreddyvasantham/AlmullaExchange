package com.amg.exchange.jvprocess.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.jvprocess.bean.JVReasonDTO;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.util.AMGException;

public interface IJVProcessDao<T> {

	public List <CurrencyMasterDTO> getCurrencyMaster();
	public List<CurrencyMasterDTO> getCurrencyOfBank(BigDecimal bankId);
	public List<String> getGlACNOAutoComplete(String position,String inputValue);
	
	public HashMap<String, String> glAccNoValidation(HashMap<String, String> inputValues)throws AMGException;
	public HashMap<String, String> jvSubCodeValidation(HashMap<String, String> inputValues) throws AMGException;
	public BigDecimal getBanlAccDtlsIDBasedOnAccountNo(String accountNo);
	public void saveJVProcess(HashMap<String, Object> saveMap)throws Exception;
	public List<JVReasonDTO> getReasonDetails();
	public void deleteCurrencyAdjust(BigDecimal companyId, BigDecimal countryId,BigDecimal documentCode,
			BigDecimal documentFinanceYr,BigDecimal documentNo, BigDecimal documentLineNo);
	public void deleteDayBookDetails(BigDecimal dayBooDtlsId);
	//Approval Related
	public List<DayBookHeader> getDocumentNo(BigDecimal documnetId);
	public List<DayBookHeader> getDayBookHeaderRecord(BigDecimal documentId,BigDecimal documentNo);
	public List<DayBookDetails> getDayBookDetailRecord(BigDecimal daybookheaderId);
	public List<ForeignCurrencyAdjust> getCurrencyAdjustRecord(BigDecimal companyId, BigDecimal countryId,BigDecimal documentCode,
			BigDecimal documentFinanceYr,BigDecimal documentNo, BigDecimal documentLineNo);
	public void approve(BigDecimal daybookheaderId,String approveBy, Date approveDate);
	public List<CurrencyWiseDenomination> getDenominationList(BigDecimal denominationId);
	public String getBankAccountNo(BigDecimal bankAccountId);
	public String getGlDescription(String glNo);
	public String getSlDescription(String slNo);
	//Approval Related
	
	public List<String> getDocumentLst(String name);
	public List<Object[]> getCurrencyDenomination(BigDecimal currencyId,String userName,Date jvRefDate);
	
	public List<DayBookHeader> getDocumentNoForEnquiry(BigDecimal documnetId);
}
