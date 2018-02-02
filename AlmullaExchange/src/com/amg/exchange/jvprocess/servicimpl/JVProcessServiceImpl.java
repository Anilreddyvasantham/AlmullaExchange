package com.amg.exchange.jvprocess.servicimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.jvprocess.bean.JVReasonDTO;
import com.amg.exchange.jvprocess.dao.IJVProcessDao;
import com.amg.exchange.jvprocess.service.IJVProcessService;
import com.amg.exchange.treasury.deal.supplier.model.DayBookDetails;
import com.amg.exchange.treasury.deal.supplier.model.DayBookHeader;
import com.amg.exchange.util.AMGException;

@Service("jVProcessServiceImpl")
public class JVProcessServiceImpl<T> implements Serializable,IJVProcessService<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IJVProcessDao<T> iJVProcessDao;
	
	@Override
	@Transactional
	public List<CurrencyMasterDTO> getCurrencyMaster() {
		
		return iJVProcessDao.getCurrencyMaster();
	}

	@Override
	@Transactional
	public List<CurrencyMasterDTO> getCurrencyOfBank(BigDecimal bankId) {
	
		return iJVProcessDao.getCurrencyOfBank(bankId);
	}

	@Override
	@Transactional
	public List<DayBookHeader> getDocumentNo(BigDecimal documnetId) {
		return iJVProcessDao.getDocumentNo(documnetId);
	}

	@Override
	@Transactional
	public List<DayBookHeader> getDayBookHeaderRecord(BigDecimal documentId,BigDecimal documentNo) {
		return iJVProcessDao.getDayBookHeaderRecord(documentId, documentNo);
	}

	@Override
	@Transactional
	public List<DayBookDetails> getDayBookDetailRecord(BigDecimal daybookheaderId) {
		return iJVProcessDao.getDayBookDetailRecord(daybookheaderId);
	}

	@Override
	@Transactional
	public List<String> getGlACNOAutoComplete(String position, String inputValue) {
		
		return iJVProcessDao.getGlACNOAutoComplete(position, inputValue);
	}

	@Override
	@Transactional
	public HashMap<String, String> glAccNoValidation(
			HashMap<String, String> inputValues) throws AMGException{
		
		return iJVProcessDao.glAccNoValidation(inputValues);
	}

	@Override
	@Transactional
	public HashMap<String, String> jvSubCodeValidation(
			HashMap<String, String> inputValues) throws AMGException {
		
		return iJVProcessDao.jvSubCodeValidation(inputValues);
	}

	@Override
	@Transactional
	public List<ForeignCurrencyAdjust> getCurrencyAdjustRecord(BigDecimal companyId, BigDecimal countryId,BigDecimal documentCode, BigDecimal documentFinanceYr,
			BigDecimal documentNo, BigDecimal documentLineNo) {
		return iJVProcessDao.getCurrencyAdjustRecord(companyId, countryId, documentCode, documentFinanceYr, documentNo, documentLineNo);
	}

	@Override
	@Transactional
	public void approve(BigDecimal daybookheaderId,String approveBy, Date approveDate) {
		iJVProcessDao.approve(daybookheaderId, approveBy, approveDate);
	}
	

	@Override
	@Transactional
	public List<String> getDocumentLst(String name){
		return iJVProcessDao.getDocumentLst(name);
	}

	@Override
	@Transactional
	public BigDecimal getBanlAccDtlsIDBasedOnAccountNo(String accountNo) {
		
		return iJVProcessDao.getBanlAccDtlsIDBasedOnAccountNo(accountNo);
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveJVProcess(HashMap<String, Object> saveMap) throws Exception {
		iJVProcessDao.saveJVProcess(saveMap);
		
	}

	@Override
	@Transactional
	public List<Object[]> getCurrencyDenomination(BigDecimal currencyId,String userName,Date jvRefDate){
		return iJVProcessDao.getCurrencyDenomination(currencyId,userName,jvRefDate);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getDenominationList(
			BigDecimal denominationId) {
		return iJVProcessDao.getDenominationList(denominationId);
	}

	@Override
	@Transactional
	public String getBankAccountNo(BigDecimal bankAccountId) {
		return iJVProcessDao.getBankAccountNo(bankAccountId);
	}
	
	@Override
	@Transactional
	public List<DayBookHeader> getDocumentNoForEnquiry(BigDecimal documnetId) {
		return iJVProcessDao.getDocumentNoForEnquiry(documnetId);
	}

	@Override
	@Transactional
	public List<JVReasonDTO> getReasonDetails() {
		
		return iJVProcessDao.getReasonDetails();
	}

	@Override
	@Transactional
	public String getGlDescription(String glNo) {
		return iJVProcessDao.getGlDescription(glNo);
	}

	@Override
	@Transactional
	public String getSlDescription(String slNo) {
		return iJVProcessDao.getSlDescription(slNo);
	}

	@Override
	@Transactional
	public void deleteCurrencyAdjust(BigDecimal companyId,
			BigDecimal countryId, BigDecimal documentCode,
			BigDecimal documentFinanceYr, BigDecimal documentNo,
			BigDecimal documentLineNo) {
		iJVProcessDao.deleteCurrencyAdjust(companyId, countryId, documentCode, documentFinanceYr, documentNo, documentLineNo);
		
	}

	@Override
	@Transactional
	public void deleteDayBookDetails(BigDecimal dayBooDtlsId) {
		iJVProcessDao.deleteDayBookDetails(dayBooDtlsId);
		
	}

}
