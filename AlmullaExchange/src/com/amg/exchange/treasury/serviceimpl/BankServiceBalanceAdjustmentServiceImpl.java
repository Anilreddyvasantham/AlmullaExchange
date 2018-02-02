package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankServiceBalanceAdjustmentDao;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryTransfer;
import com.amg.exchange.treasury.service.IBankServiceBalanceAdjustmentService;
import com.amg.exchange.treasury.viewModel.ViewAccountBalance;
import com.amg.exchange.util.AMGException;
@Service
public class BankServiceBalanceAdjustmentServiceImpl implements IBankServiceBalanceAdjustmentService {

	@Autowired
	IBankServiceBalanceAdjustmentDao ibankBalanceAdjustmentDao;
	
	@Override
	@Transactional
	public List<ViewAccountBalance> getAllRecordsFrmAccountBalance(
			BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId) {
 
		return ibankBalanceAdjustmentDao.getAllRecordsFrmAccountBalance(countryId,currencyId,bankId);
	}

	@Override
	@Transactional
	public BigDecimal getAccountBalanceIdBasedOnGlno(String glsalNumber) {
 
		return ibankBalanceAdjustmentDao.getAccountBalanceIdBasedOnGlno(glsalNumber);
	}

	@Override
	@Transactional
	public void update(BigDecimal accBalPk,BigDecimal eft,BigDecimal tt,BigDecimal cash) {
		ibankBalanceAdjustmentDao.update(accBalPk,eft,tt,cash);
		
	}

	@Override
	@Transactional
	public void saveTreasuryHeader(TreasuryDealHeader tresuryHeader) {
		ibankBalanceAdjustmentDao.saveTreasuryHeader(tresuryHeader);
		
	}

	@Override
	@Transactional
	public void saveTreasuryDetails(TreasuryDealDetail tresuryDealDetail) {
		ibankBalanceAdjustmentDao.saveTreasuryDetails(tresuryDealDetail);
		
	}

	@Override
	@Transactional
	public void saveTreasuryTransfer(TreasuryTransfer treasuryTransfer) throws AMGException{
		ibankBalanceAdjustmentDao.saveTreasuryTransfer(treasuryTransfer);
	}

}
