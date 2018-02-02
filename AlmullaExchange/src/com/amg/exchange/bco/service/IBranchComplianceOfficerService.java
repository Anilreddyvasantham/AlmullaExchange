package com.amg.exchange.bco.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.bco.model.BranchComplianceOfficer;
import com.amg.exchange.registration.model.IdentityTypeMaster;

public interface IBranchComplianceOfficerService {

	public List<BranchComplianceOfficer> getBranchComplianceList(
			BigDecimal countryBranchId);

	public List<BranchComplianceOfficer> getHeadOfficeComplianceList(
			BigDecimal countryBranchId);

	public void saveBranchCompliance(
			BranchComplianceOfficer branchComplianceOfficer);

	public void saveHeadOfficeCompliance(
			BranchComplianceOfficer branchComplianceOfficer);

	public String getNationality(BigDecimal languageId, BigDecimal countryId);

	public List<IdentityTypeMaster> getIdentityList(BigDecimal identityId);

}
