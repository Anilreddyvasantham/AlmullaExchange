package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.model.ViewPIPSType;
import com.amg.exchange.treasury.dao.IPipsMasterDao;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.PipsMaster;
import com.amg.exchange.treasury.service.IPipsMasterService;


@Service("pipsMasterService")
public class PipsMasterServiceImpl implements IPipsMasterService{
	@Autowired
	IPipsMasterDao pipsMasterDao;

	@Override
	@Transactional
	public void saveRecord(PipsMaster pipsMasterObj) {
		pipsMasterDao.saveRecord(pipsMasterObj);
		
	}
	
	@Override
	@Transactional
	public List<PipsMaster> pipsListFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal branchId,BigDecimal serviceId,BigDecimal bankId){
		return pipsMasterDao.pipsListFrmDB( countryId, currencyId, branchId,serviceId, bankId);
	}
	
	@Override
	@Transactional
	public void update(PipsMaster pipsMasterObj){
		pipsMasterDao.update(pipsMasterObj);
	}
	@Override
	@Transactional
	public List<PipsMaster> getAllPipsList(){
		
		return pipsMasterDao.getAllPipsList();
	}

	@Override
	@Transactional
	public List<PipsMaster> getAllPipsApprove() {
	 
		return pipsMasterDao.getAllPipsApprove();
	}

	@Override
	@Transactional
	public List<BeneCountryService> getCurrencyMaster(BigDecimal countryId) {
		
		return pipsMasterDao.getCurrencyMaster(countryId);
	}

	@Override
	@Transactional
	public List<PipsMaster> populatePipsListFrmDB(BigDecimal countryId,
			BigDecimal currencyId, BigDecimal bankId) {
		
		return pipsMasterDao.populatePipsListFrmDB(countryId, currencyId, bankId);
	}
	@Override
	@Transactional
	public List<PipsMaster> pipsListForEnquiry(BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal serviceId,BigDecimal bankId){
		return pipsMasterDao.pipsListForEnquiry(countryId, currencyId, countryBranchId, serviceId, bankId);
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal pipsMasterPk, String userName) {
		 
		return pipsMasterDao.approveRecord(pipsMasterPk,userName);
	}

	@Override
	@Transactional
	public List<ViewPIPSType> toFetchAllPipsIndParamView() {
		return pipsMasterDao.toFetchAllPipsIndParamView();
	}

	@Override
	@Transactional
	public String toFetchFullNameTypeCode(String pipsTypeCode) {
		return pipsMasterDao.toFetchFullNameTypeCode(pipsTypeCode);
	}

}
