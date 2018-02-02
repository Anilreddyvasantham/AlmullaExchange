package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IInsuranceDao;
import com.amg.exchange.common.model.InsuranceMaster;
import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.common.service.IInsuranceSetUpService;
@Service
public class InsuranceSetUpServiceImpl implements IInsuranceSetUpService {

	@Autowired
	IInsuranceDao iInsuranceDao;
	
	@Override
	@Transactional
	public void saveOrUpadate(InsuranceMaster insuranceSetUp) {
		iInsuranceDao.saveOrUpadate(insuranceSetUp);
		
	}

	@Override
	@Transactional
	public void saveOrUpdate(InsuranceMasterDescription insuranceMasterDescription) {
		iInsuranceDao.saveOrUpdate(insuranceMasterDescription );
	}

	@Override
	@Transactional
	public List<InsuranceMasterDescription> getAllUnApprovedRecords() {
		 
		return iInsuranceDao.getAllUnApprovedRecords();
	}

	@Override
	@Transactional
	public String approveRecordRecord(BigDecimal InsuranceSetupPk,
			String userName) {
		 
		return iInsuranceDao.approveRecordRecord(InsuranceSetupPk,userName);
	}

	@Override
	@Transactional
	public List<InsuranceMasterDescription> medicalInsuranceInquiry(
			Date fromDate, Date toDate) {
		 
		return iInsuranceDao.medicalInsuranceInquiry(fromDate,toDate);
	}

	@Override
	@Transactional
	public void deleteRecordFromParent(BigDecimal insuranceSetupMasterPk) {
		
		iInsuranceDao.deleteRecordFromParent(insuranceSetupMasterPk);
		
	}

	@Override
	@Transactional
	public void deleteRecordFromChild(BigDecimal insuranceSetupDescPk) {
		
		iInsuranceDao.deleteRecordFromChild(insuranceSetupDescPk); 
		
	}

	@Override
	@Transactional
	public List<InsuranceMasterDescription> getAllRecordsFromDB() {
		 
		return iInsuranceDao.getAllRecordsFromDB();
	}
	

}
