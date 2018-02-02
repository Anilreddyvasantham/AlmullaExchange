package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IAdditionalBankRuleAmiecDao;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAmiecService;

@Service
public class AdditionalBankRuleAmiecServiceImpl implements
		IAdditionalBankRuleAmiecService {
	
	@Autowired
	IAdditionalBankRuleAmiecDao additionalBankRuleAmiecDao;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void save(AdditionalBankRuleAmiec additionalBankRuleAmiec) throws Exception{
		additionalBankRuleAmiecDao.save(additionalBankRuleAmiec);
	}

	@Override
	@Transactional
	public BigDecimal getMasterPk(String flexfield) {
		return additionalBankRuleAmiecDao.getMasterPk(flexfield);
	}
	
	
	
	
	
	//added by kani begin
	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getamiecCodeList(){

		return additionalBankRuleAmiecDao.getamiecCodeList();

	}
	
			
/*public List<AdditionalBankRuleAmiec>  getamiecDescriptionList(String amiecDescription){
			
			return additionalBankRuleAmiecDao.getamiecCodeList(amiecDescription);
			
		}*/
		
		
		

		@Override
		@Transactional
		public List<String> getAllComponent(String query,BigDecimal countryId,String flexfield) {
			return additionalBankRuleAmiecDao.getAllComponent(query,countryId,flexfield);
		}
		

		@Override
		@Transactional
		public List<String> getComponentadditionalData(String query,BigDecimal countryId,String flexField,BigDecimal bankId) {
			return additionalBankRuleAmiecDao.getComponentadditionalData(query,countryId,flexField,bankId);
		}
		
		@Override
		@Transactional
		public List<AdditionalBankRuleAmiec> populateAmiecDescription(BigDecimal countryId,String flexField,String amiecCode) {
			return additionalBankRuleAmiecDao.populateAmiecDescription( countryId,flexField, amiecCode);
		}
		
		@Override
		@Transactional
		public List<AdditionalBankRuleAddData> populateAdditionalDescription(BigDecimal countryId,BigDecimal bankId,String flexField,String additionalData) {
			return additionalBankRuleAmiecDao.populateAdditionalDescription( countryId, bankId,flexField, additionalData);
		}

		@Override
		@Transactional
		public List<AdditionalBankRuleMap> getLstofFlexFields(BigDecimal countryId) {
			// TODO Auto-generated method stub
			return additionalBankRuleAmiecDao.getLstofFlexFields(countryId);
		}

		@Override
		@Transactional
		public List<AdditionalBankRuleMap> getAllRecordbyCountryFlex(BigDecimal countryId, String flexfield) {
			// TODO Auto-generated method stub
			return additionalBankRuleAmiecDao.getAllRecordbyCountryFlex(countryId,flexfield);
		}

		@Override
		@Transactional
		public List<AdditionalBankRuleAmiec> getAllDetailsbyCountryFlex(BigDecimal countryId, String flexfield) {
			// TODO Auto-generated method stub
			return additionalBankRuleAmiecDao.getAllDetailsbyCountryFlex(countryId,flexfield);
		}

		/*@Override
		@Transactional
		public List<AdditionalBankRuleAmiec> deleteIsActiveN(AdditionalBankRuleAmiec additionalBankRuleAmiec) {
			// TODO Auto-generated method stub
			return additionalBankRuleAmiecDao.deleteIsActiveN(additionalBankRuleAmiec);
		}*/
		
		@Override
		@Transactional
		public List<AdditionalBankRuleAddData> populateBankRuleData(BigDecimal countryId,String flexfield,BigDecimal bankId,String additionalData) {
			return additionalBankRuleAmiecDao.populateBankRuleData(countryId,flexfield,bankId,additionalData);
		}

		@Override
		@Transactional
		public BigDecimal getAdditionalBankRuleMapMasterPk(BigDecimal countryId, String flexfield) {
			return additionalBankRuleAmiecDao.getAdditionalBankRuleMapMasterPk(countryId, flexfield);
		}
		
		
	

}
