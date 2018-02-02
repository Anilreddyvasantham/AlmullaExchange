package com.amg.exchange.blacklist.service;


import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.blacklist.model.BlackListDetails;
import com.amg.exchange.blacklist.model.BlackListMaster;
import com.amg.exchange.blacklist.model.BlackListView;
import com.amg.exchange.registration.model.IdentityTypeMaster;

public interface IBlackListService {

	  public List<BlackListView> toFetchOnFormLoadingForAllViewData();

	  public List<BlackListView> toFetchSequenceNumberBasedOnEnglishName(BigDecimal idnumber);

	  public List<IdentityTypeMaster> toFetchIdentityTypeTypeBasedOnApplicatonCountryId(BigDecimal countryId);

	  public BigDecimal tofetchBlackListPkBasedOnEnglishName(String englishName);

	  public void saveBlackListMaster(BlackListMaster blackListMaster);

	  public void saveBlackListDetails(BlackListDetails blackListDetails);

	  public void saveBlackListDetailsAndBlackListMaster(BlackListDetails blackListDetails, BigDecimal blackMasterPk);

}
