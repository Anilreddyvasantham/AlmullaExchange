package com.amg.exchange.loyalty.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.loyalty.dao.ILoyaltyEncashmentDao;
import com.amg.exchange.loyalty.model.LoyaltyMasterEncashment;
import com.amg.exchange.loyalty.service.ILoyaltyEncashmentService;
@Service("loyaltyEncashmentService")
@Transactional
public class LoyaltyEncashmentServiceImpl implements ILoyaltyEncashmentService{

	  @Autowired
	  ILoyaltyEncashmentDao loyaltyEncashmentDao;

	  @Override
	  public void saveOrUpdate(LoyaltyMasterEncashment loyaltyMaster) {
		    loyaltyEncashmentDao.saveOrUpdate(loyaltyMaster);   
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> getViewAllDetails() {
		    return loyaltyEncashmentDao.getViewAllDetails();
	  }

	  @Override
	  public void deleteRecordPermentelyFromLoyaltyEncashment(BigDecimal encashmentPk) {
		    loyaltyEncashmentDao.deleteRecordPermentelyFromLoyaltyEncashment(encashmentPk);
	  }

	  @Override
	  public void upDateActiveRecordtoDeActive(BigDecimal encashmentPk, String remarks, String userName) {
		    loyaltyEncashmentDao.upDateActiveRecordtoDeActive(encashmentPk,remarks,userName);
	  }

	  @Override
	  public void DeActiveRecordToPendingForApprovalOfLoyaltyEncashment(BigDecimal encashmentPk, String userName) {
		    loyaltyEncashmentDao.DeActiveRecordToPendingForApprovalOfLoyaltyEncashment(encashmentPk,userName); 
	  }

	  @Override
	  public List<String> toFetchAllLoyaltyPoinsList(String query) {
		    return loyaltyEncashmentDao.toFetchAllLoyaltyPoinsList(query);
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> toCompareThepointValues(BigDecimal points) {
		    return loyaltyEncashmentDao.toCompareThepointValues(points);
	  }

	  @Override
	  public List<LoyaltyMasterEncashment> toFetchAllApprovalDetails() {
		    return loyaltyEncashmentDao.toFetchAllApprovalDetails();
	  }

	  @Override
	  public String checkTestKeyMasterApproveMultiUser(BigDecimal encashmentPk, String userName) {
		    return loyaltyEncashmentDao.toFetchAllApprovalDetails(encashmentPk,userName);
	  }
}
