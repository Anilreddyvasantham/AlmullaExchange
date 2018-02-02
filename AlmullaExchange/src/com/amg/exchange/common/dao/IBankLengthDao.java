package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankAccountLength;

public interface IBankLengthDao {

	public void saveBankLengthRecord(BankAccountLength bankLength);
	public List<BankAccountLength>  findBankId(BigDecimal bankId);
	public List<BankAccountLength>  checkBankLength(BigDecimal bankId, BigDecimal bankLength);
	public void deleteBankLengthRecord(BigDecimal bankLengthId,String username,Boolean delete);
	public List<BankAccountLength>  findAllLengthByBankId(BigDecimal bankId);
	/*koti start activate and deactivate service bank length ############ 17062016*/
	public void DeActiveRecordToPendingForApprovalOfBankLength(BigDecimal bankLengthId, String userName);
	public void upDateActiveRecordtoDeActive(BigDecimal bankLengthId, String remarks,String userName);
	public void DeActiveRecordToPendingForApprovalOfBankMaster(BigDecimal bankIdInternal, String userName);
	public void upDateActiveRecordtoDeActiveBankMaster(BigDecimal bankIdInternal, String userName);
	public List<BankAccountLength> findBankIdApprovalBankLength(BigDecimal bankIdInternal);
	public void deleteRecordBankAccountLength(BigDecimal bankLengthId);
	/*koti End activate and deactivate service bank length ############ 17062016*/
}
