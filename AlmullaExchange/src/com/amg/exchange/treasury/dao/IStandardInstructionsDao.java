package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;

public interface IStandardInstructionsDao<T> {
	
	public List<StandardInstruction> getParentStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String instructionType,BigDecimal accountDetId);
	public List<StandardInstructionDetails> getStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String isActive,BigDecimal instructionRefNumber);
	public List<StandardInstruction> getValues(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,BigDecimal accountDetId,String instructionType);
	public void savestandardInstruction(StandardInstruction standardInstruction);
	public void savestandardInstructionDetails(StandardInstructionDetails standardInstructionDetails);
	public void updatestandardInstructionDetails(StandardInstructionDetails standardInstructionDetails);
	public void delete(BigDecimal pkofStndInstDetails,String username);
	public void finalSaveOrUpdate(HashMap<String , Object> saveMapInfo) throws Exception;
	public List<StandardInstruction> getStanddardDesc();
	public List<StandardInstruction> getAllParentStandardInstruction();
	public BigDecimal getAllCountofStndInstrnDetails(BigDecimal pkofStandardInstrn);
	public void deleteStndInstrn(BigDecimal pkofStndInstId,String username);
	
	public List<StandardInstructionDetails> getStandardInstructionDesc(BigDecimal standardPk);
	public void approveRecord(BigDecimal instructionPk, String userName);
	//localBank
	public List<BankApplicability> getAllBankApplicablity();
	public List<StandardInstructionDetails> getStandardInstructionDetils(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId,String instructionType,BigDecimal accountDetId);
	public List<StandardInstruction> getAllListBasedonCombitiion(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId);
	public List<BankAccountDetails> populateAccountNumber(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId);
	
	public String getfundGlno(BigDecimal bankAccountDetId);
	public String getAccountNo(BigDecimal bankAccountDetId);
	
	public List<StandardInstructionDetails> getStandInstrDetailList(BigDecimal standardInstrutpk);
	public List<StandardInstruction> getStandInstrList(BigDecimal standardInstrutpk);
	public Boolean checkAlreadyApprove(BigDecimal standardInstrutpk);
	
	
	public void activateRecord(BigDecimal standardDelPk,String userName,BigDecimal lineNumber);
	public void deleteRecordPermanently(BigDecimal standardDelPk,String userName);
	public void deletePartially(BigDecimal standardDelPk,String userName, String remarks,BigDecimal lineNumber);
	public void updateRecord(BigDecimal standardDelPk,String InstructDescription,BigDecimal lineNumber);
}
