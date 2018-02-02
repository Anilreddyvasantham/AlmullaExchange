package com.amg.exchange.treasury.serviceimpl;



import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.treasury.dao.ICurrencyDenominationDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ICurrencyDenominationService;
@Service("currencyDenominationService")
public class CurrencyDenominationServiceImpl implements ICurrencyDenominationService {
	
	@Autowired
	ICurrencyDenominationDao currencyDenominationDao;

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getAllListToDb(BigDecimal countryId,BigDecimal currencyId) {
		return currencyDenominationDao.getAllListToDb(countryId,currencyId);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getAllValidateBean(BigDecimal countryId, BigDecimal currencyId, BigDecimal denomonationAmount) {
		return currencyDenominationDao.getAllValidateBean(countryId,currencyId,denomonationAmount);
	}

	@Override
	@Transactional
	public void save(CurrencyWiseDenomination currencyWiseDenomination) {
		currencyDenominationDao.save(currencyWiseDenomination);
	}

	@Override
	@Transactional
	public void deleteRecordPermanetly(BigDecimal denominationid) {
		currencyDenominationDao.deleteRecordPermanetly(denominationid);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getAllRecordsTofetchDB() {
		return currencyDenominationDao.getAllRecordsTofetchDB();
	}

	@Override
	@Transactional
	public String CountryNameList(BigDecimal countryId, BigDecimal languageId) {
		return currencyDenominationDao.CountryNameList(countryId,languageId);
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal denominationid, String userName) {
		currencyDenominationDao.approveRecord(denominationid,userName);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getBasedOnCountyCurrency(BigDecimal countryId) {
		return currencyDenominationDao.getBasedOnCountyCurrency(countryId);
	}

	@Override
	@Transactional
	public String getAllDenominationDesc(BigDecimal countryId,BigDecimal currencyId) {
		return currencyDenominationDao.getAllDenominationDesc(countryId,currencyId);
	}

	@Override
	@Transactional
	public String getAllDenominationcurrencyDesc(BigDecimal countryId, BigDecimal currencyId) {
		return currencyDenominationDao.getAllDenominationcurrencyDesc(countryId,currencyId);
	}

	@Override
	@Transactional
	public List<CurrencyWiseDenomination> getAllRecordsToApproveFromDb(BigDecimal countryId, BigDecimal currencyId) {
				return currencyDenominationDao.getAllRecordsToApproveFromDb(countryId,currencyId);
	}

	@Override
	@Transactional
	public void saveRecorsForApproved(CurrencyWiseDenomination currencyWiseDenomination) {
		currencyDenominationDao.saveRecorsForApproved(currencyWiseDenomination);
	}

	@Override
	@Transactional
	public void updateApproveRecord(List<BigDecimal> lstApproved,String userName) {
		currencyDenominationDao.updateApproveRecord(lstApproved,userName);
	}

	@Override
	@Transactional
	public String approvalAllRecord(List<BigDecimal> lstApproved,String userName) {
		return currencyDenominationDao.approvalAllRecord(lstApproved,userName);
	}

	

	

	

}
