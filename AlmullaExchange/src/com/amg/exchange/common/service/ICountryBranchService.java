package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.CountryBranch;

public interface ICountryBranchService<T> {

	public void saveAndUpdateCountryBranch(CountryBranch countryBranch);
	public Boolean isCountryBranchCodeExist(BigDecimal branchId);
	public List<CountryBranch> displayAllCountryBranchToView(BigDecimal appCountryId);
	public void activateCountryBranch(BigDecimal countryBranchId, String userName);
	public void deleteCountryBranch(BigDecimal countryBranchId);
	public List<String> autoCompleteList(String query);
	
	public List<CountryBranch> displayCountryBranchForApprove(BigDecimal appCountryId);
	public void approveRecord(BigDecimal countryBranchId, String userName,String isActive);
	public List<CountryBranch> displayCountryBranchBasedOnCountryBranchCode(BigDecimal branchId);

	  public void upDateActiveRecordtoDeActive(BigDecimal countryBranchId, String remarks, String userName);
	  public String checkCountryBranchApproveMultiUser(BigDecimal countryBranchId, String userName);
	  public List<String> toFetchAllCountryBranchCode(String query);
	  public List<CountryBranch> toCompareBranchCode(BigDecimal branchCode);
	

}
