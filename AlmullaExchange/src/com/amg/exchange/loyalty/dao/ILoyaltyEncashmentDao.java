package com.amg.exchange.loyalty.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.loyalty.model.LoyaltyMasterEncashment;

public interface ILoyaltyEncashmentDao {

	  public void saveOrUpdate(LoyaltyMasterEncashment loyaltyMaster);

	  public List<LoyaltyMasterEncashment> getViewAllDetails();

	  public void deleteRecordPermentelyFromLoyaltyEncashment(BigDecimal encashmentPk);

	  public void upDateActiveRecordtoDeActive(BigDecimal encashmentPk, String remarks, String userName);

	  public void DeActiveRecordToPendingForApprovalOfLoyaltyEncashment(BigDecimal encashmentPk, String userName);

	  public List<String> toFetchAllLoyaltyPoinsList(String query);

	  public List<LoyaltyMasterEncashment> toCompareThepointValues(BigDecimal points);

	  public List<LoyaltyMasterEncashment> toFetchAllApprovalDetails();

	  public String toFetchAllApprovalDetails(BigDecimal encashmentPk, String userName);

}
