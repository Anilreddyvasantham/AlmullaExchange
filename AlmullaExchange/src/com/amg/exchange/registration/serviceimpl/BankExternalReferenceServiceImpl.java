package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.registration.service.IBankExternalReferenceservice;
import com.amg.exchange.remittance.dao.IBankExternalReferenceDao;
import com.amg.exchange.remittance.model.BankExternalReferenceDetail;
import com.amg.exchange.remittance.model.BankExternalReferenceHead;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;

@Service("bankExternalReferanceService") 
public class BankExternalReferenceServiceImpl implements IBankExternalReferenceservice {
	@Autowired
	IBankExternalReferenceDao bankExternalReferenceDao;

	@Override
	@Transactional
	public void saveHeaderData(BankExternalReferenceHead bankExternalReferenceHead) {
		bankExternalReferenceDao.saveHeaderData(bankExternalReferenceHead);
	}

	@Override
	@Transactional
	public void saveDetailData(BankExternalReferenceDetail bankExternalReferenceDetail) {
		bankExternalReferenceDao.saveDetailData(bankExternalReferenceDetail);
	}

	@Override
	@Transactional
	public List<BankExternalReferenceHead> viewBean(BigDecimal countryId, BigDecimal bankId,BigDecimal appContryId,BigDecimal beneBankId) {
		return bankExternalReferenceDao.viewBean(countryId, bankId, appContryId,beneBankId);
	}

	@Override
	@Transactional
	public BankExternalReferenceHead findHeaderById(BigDecimal bankExtRefSeqId) {
		return bankExternalReferenceDao.findHeaderById(bankExtRefSeqId);
	}

	@Override
	@Transactional
	public List<BankExternalReferenceDetail> findDetailById(BigDecimal bankExtRefSeqId) {
		return bankExternalReferenceDao.findDetailById(bankExtRefSeqId);
	}

	@Override
	@Transactional
	public List<BankExternalReferenceDetail> getAllRecords(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal benfiBankId, String bankExternalId) {
		return bankExternalReferenceDao.getAllRecords(countryId, bankCountryId, bankId, benfiBankId, bankExternalId);
	}

	@Override
	@Transactional
	public List<BankExternalReferenceHead> getAllRecordsfromHead(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal benfiBankId, String externalId) {
		return bankExternalReferenceDao.getAllRecordsfromHead(countryId, bankCountryId, bankId, benfiBankId, externalId);
	}

	@Override
	@Transactional
	public List<BankExternalReferenceDetail> getAllRecords() {
		return bankExternalReferenceDao.getAllRecords();
	}

	@Override
	@Transactional
	public List<BankExternalReferenceHead> getAllRecordsfromHead(boolean fetchAll, BigDecimal bankCountryId, BigDecimal bankId) {
		return bankExternalReferenceDao.getAllRecordsfromHead(fetchAll, bankCountryId, bankId);
	}

	@Override
	@Transactional
	public List<BankApplicability> getBankListbyIndicators(BigDecimal bankCountryId, BigDecimal correspondingbankindicator, BigDecimal serviceproviderbankindicator) {
		return bankExternalReferenceDao.getBankListbyIndicators(bankCountryId, correspondingbankindicator, serviceproviderbankindicator);
	}

	@Override
	@Transactional
	public void delete(BankExternalReferenceHead bankExternalReferenceHead, List<BankExternalReferenceDetail> list) {
		bankExternalReferenceDao.delete(bankExternalReferenceHead, list);
	}

	@Override
	@Transactional
	public void delete(BankExternalReferenceHead bankExternalReferenceHead) {
		bankExternalReferenceDao.delele(bankExternalReferenceHead);
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal bankExtRefSeqId, String userName) {
		return bankExternalReferenceDao.approveRecord(bankExtRefSeqId,userName);
	}

	@Override
	@Transactional
	public void approveDetailsRecord(BigDecimal bankExtRefDetailSeqId, String userName) {
		bankExternalReferenceDao.approveDetailsRecord(bankExtRefDetailSeqId,userName);
		
	}

	@Override
	@Transactional(rollbackFor= Exception.class)
	public boolean saveAllDetails(HashMap<String, Object> saveMapInfo)throws Exception {
		return bankExternalReferenceDao.saveAllDetails(saveMapInfo);
	}

	@Override
	@Transactional
	public List<BankBranch> getBankBranchListFromCountryBank(
			BigDecimal countryId, BigDecimal bankId) {

		return bankExternalReferenceDao.getBankBranchListFromCountryBank(countryId, bankId);
	}

	@Override
	@Transactional
	public void deactiveRecordToPendingForApproval(
			BigDecimal bankExtranlDetlId, String userName) {
		bankExternalReferenceDao.deactiveRecordToPendingForApproval(bankExtranlDetlId, userName);
		
	}

	@Override
	@Transactional
	public void upDateActiveRecordtoDeActive(BigDecimal bankExtranlDetlId,
			String remarks, String userName) {
		bankExternalReferenceDao.upDateActiveRecordtoDeActive(bankExtranlDetlId, remarks, userName);
		
	}

	@Override
	@Transactional
	public void deleteBranchRecord(BigDecimal branchDetailsPk) {
		bankExternalReferenceDao.deleteBranchRecord(branchDetailsPk);
		
	}

	@Override
	@Transactional
	public List<BankExternalReferenceHead> getDuplicateCheckList(
			BigDecimal countryId, BigDecimal bankId, BigDecimal appContryId,
			BigDecimal beneBankId) {
		return bankExternalReferenceDao.viewBean(countryId, bankId, appContryId,beneBankId);
 
	}
	
	@Override
	@Transactional
	public HashMap<String, String> callPopulateBankExternalRef(HashMap<String, String> approveRecord)  throws AMGException{
		return bankExternalReferenceDao.callPopulateBankExternalRef(approveRecord);
	}

	@Override
	@Transactional
	public void procErrorToUnApprove(BigDecimal bankExtRefSeqId,List<BigDecimal> list) {
		bankExternalReferenceDao.procErrorToUnApprove(bankExtRefSeqId, list);
	}
	
	@Override
	@Transactional
	public List<BankExternalReferenceDetail> findDetailByIdUnApproved(BigDecimal bankExtRefSeqId) {
		return bankExternalReferenceDao.findDetailByIdUnApproved(bankExtRefSeqId);
	}
}
