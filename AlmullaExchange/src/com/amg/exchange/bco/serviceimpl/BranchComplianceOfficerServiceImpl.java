package com.amg.exchange.bco.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.bco.dao.IBranchComplianceOfficerDao;
import com.amg.exchange.bco.model.BranchComplianceOfficer;
import com.amg.exchange.bco.service.IBranchComplianceOfficerService;
import com.amg.exchange.registration.model.IdentityTypeMaster;

@Service
public class BranchComplianceOfficerServiceImpl implements
		IBranchComplianceOfficerService, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	IBranchComplianceOfficerDao bcoDao;

	@Override
	@Transactional
	public List<BranchComplianceOfficer> getBranchComplianceList(
			BigDecimal countryBranchId) {
		return bcoDao.getBranchComplianceList(countryBranchId);
	}

	@Override
	@Transactional
	public List<BranchComplianceOfficer> getHeadOfficeComplianceList(
			BigDecimal countryBranchId) {
		return bcoDao.getHeadOfficeComplianceList(countryBranchId);
	}

	@Override
	@Transactional
	public void saveBranchCompliance(
			BranchComplianceOfficer branchComplianceOfficer) {
		bcoDao.saveBranchCompliance(branchComplianceOfficer);
	}

	@Override
	@Transactional
	public void saveHeadOfficeCompliance(
			BranchComplianceOfficer branchComplianceOfficer) {
		bcoDao.saveHeadOfficeCompliance(branchComplianceOfficer);
	}

	@Override
	@Transactional
	public String getNationality(BigDecimal languageId, BigDecimal countryId) {
		return bcoDao.getNationality(languageId, countryId);
	}

	@Override
	@Transactional
	public List<IdentityTypeMaster> getIdentityList(BigDecimal identityId) {
		return bcoDao.getIdentityList(identityId);
	}

}
