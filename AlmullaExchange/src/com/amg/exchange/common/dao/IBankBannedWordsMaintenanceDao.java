package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.model.BankBannedWordsMaintenance;

public interface IBankBannedWordsMaintenanceDao {
	public void saveOrUpdate(BankBannedWordsMaintenance bankBannedWordMaintenanceObj);
	public List<BankBannedWordsMaintenance> getSpecificReords(BigDecimal bankId,BigDecimal countryId,String selectionMode);
	public List<BankBannedWordsMaintenance> getAllBannedWordsList(String selectionMode);
	public List<BankBannedWordsMaintenance> duplicateWordCheck(String bannedName);
	public void deleteRecord(BigDecimal bankBannedId, String userName) ;
	public void activateWord(BigDecimal bankBannedId, String userName);
}
