package com.amg.exchange.blacklist.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.blacklist.dao.IBlackListDao;
import com.amg.exchange.blacklist.model.BlackListDetails;
import com.amg.exchange.blacklist.model.BlackListMaster;
import com.amg.exchange.blacklist.model.BlackListView;
import com.amg.exchange.blacklist.service.IBlackListService;
import com.amg.exchange.registration.model.IdentityTypeMaster;

@Service("blackListService")
public class BlackListServiceImpl implements IBlackListService {

	  @Autowired
	  IBlackListDao blackListDao;

	  @Override
	  @Transactional
	  public List<BlackListView> toFetchOnFormLoadingForAllViewData() {
		    return blackListDao.toFetchOnFormLoadingForAllViewData();
	  }

	  @Override
	  @Transactional
	  public List<BlackListView> toFetchSequenceNumberBasedOnEnglishName(BigDecimal idnumber) {
		    return blackListDao.toFetchOnFormLoadingForAllViewData(idnumber);
	  }

	  @Override
	  @Transactional
	  public List<IdentityTypeMaster> toFetchIdentityTypeTypeBasedOnApplicatonCountryId(BigDecimal countryId) {
		    return blackListDao.toFetchIdentityTypeTypeBasedOnApplicatonCountryId(countryId);
	  }

	  @Override
	  @Transactional
	  public BigDecimal tofetchBlackListPkBasedOnEnglishName(String englishName) {
		    return blackListDao.tofetchBlackListPkBasedOnEnglishName(englishName);
	  }

	  @Override
	  @Transactional
	  public void saveBlackListMaster(BlackListMaster blackListMaster) {
		    blackListDao.saveBlackListMaster(blackListMaster);
	  }

	  @Override
	  @Transactional
	  public void saveBlackListDetails(BlackListDetails blackListDetails) {
		    blackListDao.saveBlackListDetails(blackListDetails);
	  }

	  @Override
	  public void saveBlackListDetailsAndBlackListMaster(BlackListDetails blackListDetails, BigDecimal blackMasterPk) {
		    blackListDao.saveBlackListDetailsAndBlackListMaster(blackListDetails,blackMasterPk);
	  }

}
