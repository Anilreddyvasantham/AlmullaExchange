package com.amg.exchange.loyalty.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;








import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyTypeDao;
import com.amg.exchange.loyalty.model.LoyaltyType;
import com.amg.exchange.loyalty.model.LoyaltyTypeDesc;
import com.amg.exchange.loyalty.service.ILoyaltyTypeService;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("loyaltyTypeServiceImpl")
public class LoyaltyTypeServiceImpl<T> implements ILoyaltyTypeService<T>,Serializable {
	
	@Autowired
	ILoyaltyTypeDao<T> loyaltyTypeDao;

	public ILoyaltyTypeDao<T> getLoyaltyTypeDao() {
		return loyaltyTypeDao;
	}

	public void setLoyaltyTypeDao(ILoyaltyTypeDao<T> loyaltyTypeDao) {
		this.loyaltyTypeDao = loyaltyTypeDao;
	}

	@Override
	@Transactional
	public List<LoyaltyType> displayList(String loyaltyType,BigDecimal applicationCountryId) {
		return getLoyaltyTypeDao().displayList(loyaltyType,applicationCountryId);
	}

	@Override
	@Transactional
	public List<LoyaltyType> displayListById(BigDecimal id) {
		return getLoyaltyTypeDao().displayListById(id);
	}
	
	@Override
	@Transactional
	public List<LoyaltyType> displayListByCode(String code) {
		return getLoyaltyTypeDao().displayListByCode(code);
	}

	@Override
	@Transactional
	public List<LoyaltyTypeDesc> getDescriptionById(BigDecimal id) {
		// TODO Auto-generated method stub
		return getLoyaltyTypeDao().getDescriptionById(id);
	}

	@Override
	@Transactional
	public void saveMaster(LoyaltyType loyalityType) {
		getLoyaltyTypeDao().saveMaster(loyalityType);
		
	}

	@Override
	@Transactional
	public void saveMasterDescriptionEnglish(LoyaltyTypeDesc loyalityTypeDesc) {
		getLoyaltyTypeDao().saveMasterDescriptionEnglish(loyalityTypeDesc);
		
	}
	
	@Override
	@Transactional
	public void saveMasterDescriptionLocal(LoyaltyTypeDesc loyalityTypeDesc) {
		getLoyaltyTypeDao().saveMasterDescriptionLocal(loyalityTypeDesc);
		
	}
	
	@Override
	@Transactional
	public List<String> autoCompleteList(String query){
		return getLoyaltyTypeDao().autoCompleteList(query);
	}
	
	@Override
	@Transactional
	public void activate(BigDecimal id, String userName){
		getLoyaltyTypeDao().activate(id,userName);
	}
	
	@Override
	@Transactional
	public void deleteLoyaltyType(BigDecimal loyaltyTypeId, BigDecimal loyaltyTypeDescId, BigDecimal loyaltyTypeArabicDescId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void saveLoyalty(HashMap<String, Object> saveMapInfo)
			throws AMGException {
		getLoyaltyTypeDao().saveLoyalty(saveMapInfo);
		
	}

}
