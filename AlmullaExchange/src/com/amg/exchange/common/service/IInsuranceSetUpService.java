package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.InsuranceMaster;
import com.amg.exchange.common.model.InsuranceMasterDescription;

public interface IInsuranceSetUpService {
	
	public void saveOrUpadate(InsuranceMaster insuranceSetUp);
	public void saveOrUpdate(InsuranceMasterDescription insuranceMasterDescription);
	public List<InsuranceMasterDescription> getAllUnApprovedRecords();
	public String approveRecordRecord(BigDecimal InsuranceSetupPk,String userName);
	public List<InsuranceMasterDescription> medicalInsuranceInquiry(Date fromDate,Date toDate);
	
	public void deleteRecordFromParent(BigDecimal insuranceSetupMasterPk);
	public void deleteRecordFromChild(BigDecimal insuranceSetupDescPk);
	public List<InsuranceMasterDescription> getAllRecordsFromDB();
	
}
