package com.amg.exchange.common.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.ICountryBranchDao;
import com.amg.exchange.common.service.ICountryBranchService;
import com.amg.exchange.registration.model.CountryBranch;

@SuppressWarnings("serial")
@Service("countryBranchServiceImpl")
public class CountryBranchServiceImpl<T> implements ICountryBranchService<T>,Serializable {
	
	@Autowired 
	ICountryBranchDao<T> countryBranchDao;

	/*public CountryBranchServiceImpl() {
		// TODO Auto-generated constructor stub
	}
*/
	public ICountryBranchDao<T> getCountryBranchDao() {
		return countryBranchDao;
	}

	public void setCountryBranchDao(ICountryBranchDao<T> countryBranchDao) {
		this.countryBranchDao = countryBranchDao;
	}
	
	@Override
	@Transactional
	public void saveAndUpdateCountryBranch(CountryBranch countryBranch) {
		getCountryBranchDao().saveAndUpdateCountryBranch(countryBranch);
			
	}
	
	@Override
	@Transactional
	public void activateCountryBranch(BigDecimal countryBranchId, String userName) {
		getCountryBranchDao().activateCountryBranch(countryBranchId, userName);
				
	}
	
	@Override
	@Transactional
	public void approveRecord(BigDecimal countryBranchId, String userName, String isActive) {
		getCountryBranchDao().approveRecord(countryBranchId, userName, isActive);
				
	}
	
	@Override
	@Transactional
	public List<String> autoCompleteList(String query) {
		// TODO Auto-generated method stub
		return getCountryBranchDao().autoCompleteList(query);
	}
	
	@Override
	@Transactional
	public void deleteCountryBranch(BigDecimal countryBranchId) {
		getCountryBranchDao().deleteCountryBranch(countryBranchId);
		
	}
	
	@Override
	@Transactional
	public List<CountryBranch> displayAllCountryBranchToView(BigDecimal appCountryId) {
		
		return getCountryBranchDao().displayAllCountryBranchToView(appCountryId);
	}
	
	@Override
	@Transactional
	public List<CountryBranch> displayCountryBranchBasedOnCountryBranchCode(BigDecimal branchId) {
		// TODO Auto-generated method stub
		return getCountryBranchDao().displayCountryBranchBasedOnCountryBranchCode(branchId);
	}
	
	@Override
	@Transactional
	public List<CountryBranch> displayCountryBranchForApprove(BigDecimal appCountryId) {
		// TODO Auto-generated method stub
		return getCountryBranchDao().displayCountryBranchForApprove(appCountryId);
	}
	
	@Override
	@Transactional
	public Boolean isCountryBranchCodeExist(BigDecimal branchId) {
		// TODO Auto-generated method stub
		return getCountryBranchDao().isCountryBranchCodeExist(branchId);
	}

	  @Override
	  @Transactional
	  public void upDateActiveRecordtoDeActive(BigDecimal countryBranchId, String remarks, String userName) {
		    countryBranchDao.upDateActiveRecordtoDeActive(countryBranchId, remarks, userName);
	  }

	  @Override
	  @Transactional
	  public String checkCountryBranchApproveMultiUser(BigDecimal countryBranchId, String userName) {
		    return countryBranchDao.checkCountryBranchApproveMultiUser(countryBranchId,userName);
	  }

	  @Override
	  @Transactional
	  public List<String> toFetchAllCountryBranchCode(String query) {
		    return countryBranchDao.toFetchAllCountryBranchCode(query);
	  }

	  @Override
	  @Transactional
	  public List<CountryBranch> toCompareBranchCode(BigDecimal branchCode) {
		    return countryBranchDao.toCompareBranchCode(branchCode);
	  }
	
	
}
