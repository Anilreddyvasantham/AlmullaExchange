package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IStandardInstructionsDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.service.IStandardInstructionsService;

@SuppressWarnings("serial")
@Service("standardInstructionsServiceImpl")
public class StandardInstructionsServiceImpl<T> implements IStandardInstructionsService<T>, Serializable {

	@Autowired
	IStandardInstructionsDao<T> standardInstructionsDao;

	@Transactional
	@Override
	public List<StandardInstruction> getParentStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String instructionType,BigDecimal accountDetId) {
		return standardInstructionsDao.getParentStandardInstruction(companyId, bankId, currencyId,instructionType,accountDetId);
	}
	
	@Transactional
	@Override
	public List<StandardInstructionDetails> getStandardInstruction(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,String isActive,BigDecimal instructionRefNumber) {
		return standardInstructionsDao.getStandardInstruction(companyId, bankId, currencyId, isActive,instructionRefNumber);
	}
	
	@Transactional
	@Override
	public List<StandardInstruction> getValues(BigDecimal companyId,BigDecimal bankId,BigDecimal currencyId,BigDecimal accountDetId,String instructionType) {
		return standardInstructionsDao.getValues(companyId, bankId, currencyId,accountDetId,instructionType);
	}
	
	@Transactional
	@Override
	public void savestandardInstruction(StandardInstruction standardInstruction) {
		standardInstructionsDao.savestandardInstruction(standardInstruction);
	}
	
	@Transactional
	@Override
	public void savestandardInstructionDetails(StandardInstructionDetails standardInstructionDetails) {
		standardInstructionsDao.savestandardInstructionDetails(standardInstructionDetails);
	}
	
	@Transactional
	@Override
	public void updatestandardInstructionDetails(StandardInstructionDetails standardInstructionDetails) {
		standardInstructionsDao.updatestandardInstructionDetails(standardInstructionDetails);
	}
	
	@Transactional
	@Override
	public void delete(BigDecimal pkofStndInstDetails,String username) {
		standardInstructionsDao.delete(pkofStndInstDetails,username);
	}

	@Transactional
	@Override
	public List<StandardInstruction> getStanddardDesc() {
		// TODO Auto-generated method stub
		return standardInstructionsDao.getStanddardDesc();
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void finalSaveOrUpdate(HashMap<String, Object> saveMapInfo) throws Exception {
		// TODO Auto-generated method stub
		standardInstructionsDao.finalSaveOrUpdate(saveMapInfo);
	}

	@Override
	@Transactional
	public List<StandardInstruction> getAllParentStandardInstruction() {
		// TODO Auto-generated method stub
		return standardInstructionsDao.getAllParentStandardInstruction();
	}

	@Override
	@Transactional
	public BigDecimal getAllCountofStndInstrnDetails(BigDecimal pkofStandardInstrn) {
		// TODO Auto-generated method stub
		return standardInstructionsDao.getAllCountofStndInstrnDetails(pkofStandardInstrn);
	}

	@Override
	@Transactional
	public void deleteStndInstrn(BigDecimal pkofStndInstId, String username) {
		// TODO Auto-generated method stub
		standardInstructionsDao.deleteStndInstrn(pkofStndInstId,username);
	}
	
	
	@Override
	@Transactional
	public List<StandardInstructionDetails> getStandardInstructionDesc(BigDecimal standardPk){
		// TODO Auto-generated method stub
		return standardInstructionsDao.getStandardInstructionDesc(standardPk);
	}
	@Override
	@Transactional
	public void approveRecord(BigDecimal instructionPk,String userName){
		standardInstructionsDao.approveRecord(instructionPk,userName);
	}

	@Override
	@Transactional
	public List<BankApplicability> getAllBankApplicablity() {
		return standardInstructionsDao.getAllBankApplicablity();
	}

	@Override
	@Transactional
	public List<StandardInstructionDetails> getStandardInstructionDetils(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId,String instructionType,BigDecimal accountDetId) {
		return standardInstructionsDao.getStandardInstructionDetils(companyId, bankId, currencyId, instructionType,accountDetId);
	}

	@Override
	@Transactional
	public List<StandardInstruction> getAllListBasedonCombitiion(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId) {
		return standardInstructionsDao.getAllListBasedonCombitiion(companyId,bankId,currencyId);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> populateAccountNumber(BigDecimal companyId, BigDecimal bankId, BigDecimal currencyId){
		return standardInstructionsDao.populateAccountNumber(companyId,bankId,currencyId);
	}
	
	@Override
	@Transactional
	public String getfundGlno(BigDecimal bankAccountDetId){
		return standardInstructionsDao.getfundGlno(bankAccountDetId);
	}
	
	@Override
	@Transactional
	public String getAccountNo(BigDecimal bankAccountDetId){
		return standardInstructionsDao.getAccountNo(bankAccountDetId);
	}
	
	@Override
	@Transactional
	public List<StandardInstructionDetails> getStandInstrDetailList(BigDecimal standardInstrutpk){
		return standardInstructionsDao.getStandInstrDetailList(standardInstrutpk);
	}
	
	@Override
	@Transactional
	public List<StandardInstruction> getStandInstrList(BigDecimal standardInstrutpk){
		return standardInstructionsDao.getStandInstrList(standardInstrutpk);
	}
	
	@Override
	@Transactional
	public Boolean checkAlreadyApprove(BigDecimal standardInstrutpk){
		return standardInstructionsDao.checkAlreadyApprove(standardInstrutpk);
	}
	
	@Override
	@Transactional
	public void activateRecord(BigDecimal standardDelPk,String userName,BigDecimal lineNumber){
		 standardInstructionsDao.activateRecord(standardDelPk,userName,lineNumber);
	}
	
	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal standardDelPk,String userName){
		 standardInstructionsDao.deleteRecordPermanently(standardDelPk,userName);
	}
	
	@Override
	@Transactional
	public void deletePartially(BigDecimal standardDelPk,String userName, String remarks,BigDecimal lineNumber){
		 standardInstructionsDao.deletePartially(standardDelPk,userName,remarks,lineNumber);
	}

	@Override
	@Transactional
	public void updateRecord(BigDecimal standardDelPk, String InstructDescription,BigDecimal lineNumber) {
		standardInstructionsDao.updateRecord(standardDelPk,InstructDescription,lineNumber);
		
	}
	
	
}

