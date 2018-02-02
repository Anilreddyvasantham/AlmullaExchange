package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IAdditionalBankRuleAddDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.FlexFiledView;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;

@Service
public class AdditionalBankRuleAddServiceImpl implements IAdditionalBankRuleAddService {
	@Autowired
	IAdditionalBankRuleAddDao additionalBankRuleAddDao;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void save(AdditionalBankRuleAddData additionalBankRuleAddData) throws Exception{
		additionalBankRuleAddDao.save(additionalBankRuleAddData);
	}
	
	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getAdditionalBankList(){
		
		return additionalBankRuleAddDao.getAdditionalBankList();
		
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getBankDescription(String bankCode) {
		return additionalBankRuleAddDao.getBankDescription(bankCode);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getDBCountryFlexBank(BigDecimal countryId, String FlexId, BigDecimal bankId) {
		// TODO Auto-generated method stub
		return additionalBankRuleAddDao.getDBCountryFlexBank(countryId,FlexId,bankId);
	}
	
	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getDataForApproval() {
		return additionalBankRuleAddDao.getDataForApproval();
	}
	
	@Override
	@Transactional
	public String approveRecord(BigDecimal addtionalBankRulePk,String userName) {
		return additionalBankRuleAddDao.approveRecord(addtionalBankRulePk,userName);
	}
	
	@Override
	@Transactional
	public String approveRecord(List<BigDecimal> addtionalBankRulePk,String userName) {
		return additionalBankRuleAddDao.approveRecord(addtionalBankRulePk, userName);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla() {
		return additionalBankRuleAddDao.getDataForApprovalForAmMulla();
	}

	@Override
	@Transactional
	public String approveRecordForAlmulla(BigDecimal addtionalBankRulePk,String userName) {
	 return	additionalBankRuleAddDao.approveRecordForAlmulla(addtionalBankRulePk,userName);
		
	}
	
	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getDataForApprovalForBankRuleMap() {
		return additionalBankRuleAddDao.getDataForApprovalForBankRuleMap();
	}

	@Override
	@Transactional
	public String approveRecordForBankRuleMap(BigDecimal addtionalBankRuleMapPk,String userName) {
		return additionalBankRuleAddDao.approveRecordForBankRuleMap(addtionalBankRuleMapPk,userName);
		
	}

	@Override
	@Transactional
	public List<FlexFiledView> getflexiFieldList() {
		return additionalBankRuleAddDao.getflexiFieldList();
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getDataForApprovalList(BigDecimal countryId, String flexFiled, BigDecimal bankId) {
		return additionalBankRuleAddDao.getDataForApprovalList(countryId,flexFiled,bankId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getDataForApprovalForAmMulla(BigDecimal countryId, String FlexField) {
		return additionalBankRuleAddDao.getDataForApprovalForAmMulla(countryId, FlexField);
	}


}
