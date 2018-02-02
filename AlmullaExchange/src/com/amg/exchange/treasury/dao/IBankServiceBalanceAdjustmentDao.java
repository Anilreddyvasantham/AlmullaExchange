package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryTransfer;
import com.amg.exchange.treasury.viewModel.ViewAccountBalance;
import com.amg.exchange.util.AMGException;

public interface IBankServiceBalanceAdjustmentDao {
	public List<ViewAccountBalance> getAllRecordsFrmAccountBalance(BigDecimal countryId, BigDecimal currencyId, BigDecimal bankId) ;
	public BigDecimal getAccountBalanceIdBasedOnGlno(String glsalNumber);
	public void update(BigDecimal accBalPk,BigDecimal eft,BigDecimal tt,BigDecimal cash);
	public void saveTreasuryHeader(TreasuryDealHeader tresuryHeader);
	public void saveTreasuryDetails(TreasuryDealDetail  tresuryDealDetail);
	public void saveTreasuryTransfer(TreasuryTransfer  treasuryTransfer)  throws AMGException;
	
}
