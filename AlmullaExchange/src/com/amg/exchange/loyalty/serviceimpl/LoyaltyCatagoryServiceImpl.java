package com.amg.exchange.loyalty.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyCatagoryDao;
import com.amg.exchange.loyalty.model.LoyaltyCatagoryMaster;
import com.amg.exchange.loyalty.model.LoyaltyCatergoryMasterDesc;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyCatagoryService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("loyaltyCatagoryServiceImpl")
public class LoyaltyCatagoryServiceImpl<T> implements ILoyaltyCatagoryService<T>{
	
	@Autowired
	ILoyaltyCatagoryDao<T> loyaltyCatagoryDao;
	
	

	public ILoyaltyCatagoryDao<T> getLoyaltyCatagoryDao() {
		return loyaltyCatagoryDao;
	}

	public void setLoyaltyCatagoryDao(ILoyaltyCatagoryDao<T> loyaltyCatagoryDao) {
		this.loyaltyCatagoryDao = loyaltyCatagoryDao;
	}

	@Override
	@Transactional
	public void saveAndUpdateLoyaltyCatagoryMaster(LoyaltyCatagoryMaster loyaltyCatagoryMaster) {
		getLoyaltyCatagoryDao().saveAndUpdateLoyaltyCatagoryMaster(loyaltyCatagoryMaster);		
		
	}

	@Override
	@Transactional
	public void saveAndUpdateLoyaltyCatergoryMasterDesc(LoyaltyCatergoryMasterDesc loyaltyCatergoryMasterDesc) {
		getLoyaltyCatagoryDao().saveAndUpdateLoyaltyCatergoryMasterDesc(loyaltyCatergoryMasterDesc);	
		
	}

	@Override
	@Transactional
	public Boolean isCatagoryCodeExist(String categoryCode) {
		// TODO Auto-generated method stub
		return getLoyaltyCatagoryDao().isCatagoryCodeExist(categoryCode);
	}

	@Override
	@Transactional
	public List<LoyaltyCatergoryMasterDesc> displayAllLoyaltyCatergoryMasterDesc(BigDecimal loyaltyCatagoryId) {
		
		return getLoyaltyCatagoryDao().displayAllLoyaltyCatergoryMasterDesc(loyaltyCatagoryId);
	}

	@Override
	@Transactional
	public List<LoyaltyCatagoryMaster> displayAllLoyaltyCatagoryToView(BigDecimal applicationCountryId,String categoryType) {
		
		return  getLoyaltyCatagoryDao().displayAllLoyaltyCatagoryToView(applicationCountryId, categoryType);
	}

	@Override
	@Transactional
	public void activateLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, String userName) {
		getLoyaltyCatagoryDao().activateLoyaltyCatagoryMaster(loyaltyCatagoryId, userName);
		
		
	}

	@Override
	@Transactional
	public void deleteLoyaltyCatagoryMaster(BigDecimal loyaltyCatagoryId, BigDecimal englishDescId, BigDecimal arabicDescId) {
		getLoyaltyCatagoryDao().deleteLoyaltyCatagoryMaster(loyaltyCatagoryId, englishDescId, arabicDescId);
		
	}

	@Override
	@Transactional
	public List<String> autoCompleteList(String query) {
		// TODO Auto-generated method stub
		return getLoyaltyCatagoryDao().autoCompleteList(query);
	}

	@Override
	@Transactional
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryForApprove(BigDecimal appCountryId, String inActive) {
		// TODO Auto-generated method stub
		return getLoyaltyCatagoryDao().displayLoyaltyCatagoryForApprove(appCountryId, inActive);
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal loyaltyCatagoryId, String userName, String isActive) {
		getLoyaltyCatagoryDao().approveRecord(loyaltyCatagoryId, userName, isActive);
		
	}

	@Override
	@Transactional
	public List<LoyaltyCatagoryMaster> displayLoyaltyCatagoryBasedOnCatagoryCode(String categoryCode) {
		// TODO Auto-generated method stub
		return getLoyaltyCatagoryDao().displayLoyaltyCatagoryBasedOnCatagoryCode(categoryCode);
	}

	@Override
	@Transactional
	public List<LoyaltyCatergoryMasterDesc> displayLoyaltyCatagoryForLoyaltyCreation(BigDecimal appCountryId, BigDecimal languageId) {
		// TODO Auto-generated method stub
		return getLoyaltyCatagoryDao().displayLoyaltyCatagoryForLoyaltyCreation(appCountryId, languageId);
	}
	@Override
	@Transactional
	public List<LoyaltyTypeDesc> getLoyaltyTypeDescList( BigDecimal languageId){
		
		return getLoyaltyCatagoryDao().getLoyaltyTypeDescList(languageId);
	}
	@Override
	@Transactional
	public List<LoyaltyTypeDesc> getLoyaltyTypeMasterDesc(BigDecimal loyaltyTypeId,BigDecimal languageId){
		return getLoyaltyCatagoryDao().getLoyaltyTypeMasterDesc(loyaltyTypeId, languageId);
	}
	@Override
	@Transactional
	public List<LoyaltyTypeDesc> getLoyaltyTypeId(String categoryType, BigDecimal languageId){
		return getLoyaltyCatagoryDao().getLoyaltyTypeId(categoryType, languageId);
	}

	@Override
	@Transactional
	public void saveLoyaltyCataegory(HashMap<String, Object> saveMapInfo)
			throws AMGException {
		getLoyaltyCatagoryDao().saveLoyaltyCataegory(saveMapInfo);
		
	}
}
