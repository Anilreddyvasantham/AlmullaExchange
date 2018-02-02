package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IAmiecAndBankMappingDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.treasury.model.BankMaster;

@Service
public class AmiecAndBankMappingServiceImpl implements
		IAmiecAndBankMappingService {
	@Autowired
	IAmiecAndBankMappingDao amiecAndBankMappingDao;

	@Override
	@Transactional
	public void save(AmiecAndBankMapping amiecAndBankMapping) {
		amiecAndBankMappingDao.save(amiecAndBankMapping);
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping> getAllAdditionalBankDataList() {
		return amiecAndBankMappingDao.getAllAdditionalBankDataList();
	}
	
	@Override
	@Transactional
	public List<AmiecAndBankMapping> getFlexFieldByCountry(BigDecimal countryId, BigDecimal bankId, String flexField) {
		return amiecAndBankMappingDao.getFlexFieldByCountry(countryId, bankId, flexField);
	}
	
	@Override
	@Transactional
	public List<AmiecAndBankMapping> getAmiecCodeByCountry(BigDecimal countryId, BigDecimal bankId, String amiecCode) {
		return amiecAndBankMappingDao.getAmiecCodeByCountry(countryId, bankId, amiecCode);
	}
	
	@Override
	@Transactional
	public List<AmiecAndBankMapping> getBankCodeByCountry(BigDecimal countryId, BigDecimal bankId, String bankCode) {
		return amiecAndBankMappingDao.getBankCodeByCountry(countryId, bankId,bankCode);
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping> getBankAmiecAndBankMapping() {
		return amiecAndBankMappingDao.getBankAmiecAndBankMapping();
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getAmielist(BigDecimal countryId,
			String flexId) {
		
		return amiecAndBankMappingDao.getAmielist(countryId, flexId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getListBankCode(
			BigDecimal countryId, BigDecimal bankId, String flexId) {
		// TODO Auto-generated method stub
		return amiecAndBankMappingDao.getListBankCode(countryId, bankId, flexId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getpopDescription(String amieCode) {
		// TODO Auto-generated method stub
		return amiecAndBankMappingDao.getListDesc(amieCode);
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping> getDataForApproval() {
		return amiecAndBankMappingDao.getDataForApproval();
	}

	@Override
	@Transactional
	public String getFlexFieldName(String fieldValue) {
		return amiecAndBankMappingDao.getFlexFieldName(fieldValue);
	}
	
	@Override
	@Transactional
	public String approveRecord(BigDecimal amiecPk,String userName) {
		return amiecAndBankMappingDao.approveRecord(amiecPk,userName);
	}

	@Override
	@Transactional
	public List<BankMaster> getBanklist(BigDecimal coutryId) {
		// TODO Auto-generated method stub
		return amiecAndBankMappingDao.listofBanks(coutryId);
	}
	
	@Override
	@Transactional
	public String getFieldName(String feild,BigDecimal countryId) {
		// TODO Auto-generated method stub
		return amiecAndBankMappingDao.filedName(feild,countryId);
	}

	//added by kani for Enquiry screen begin
	
	@Override
	@Transactional
	public List<AmiecAndBankMapping> getBankAmiecAndBankMappingEnquiry() {
		return amiecAndBankMappingDao.getBankAmiecAndBankMappingEnquiry();
	}
	
	
	//Added by kani for Enquiry screen end
	
	
	@Override
	@Transactional
	public void activateRecord(BigDecimal bankAmiecPk,String userName){
		amiecAndBankMappingDao.activateRecord(bankAmiecPk,userName);
	}
	
	@Override
	@Transactional
	public void deleteRecord(BigDecimal bankAmiecPk){
		amiecAndBankMappingDao.deleteRecord(bankAmiecPk);
	}
	
	@Override
	@Transactional
	public void remarkRecord(BigDecimal bankAmiecPk,String remarkedText,String userName){
		amiecAndBankMappingDao.remarkRecord(bankAmiecPk,remarkedText,userName);
	}
	
	@Override
	@Transactional
	public List<AmiecAndBankMapping> dupliacteRecordCheckInDB(BigDecimal countryId,BigDecimal bankId,String flexField,String amiecCode,String bankCode){
		return amiecAndBankMappingDao.dupliacteRecordCheckInDB(countryId,bankId,flexField,amiecCode,bankCode);
	}
	
}