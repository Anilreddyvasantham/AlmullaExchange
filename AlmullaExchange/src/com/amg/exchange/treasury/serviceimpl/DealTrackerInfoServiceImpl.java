package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.bean.BulkDealApprovalDataTable;
import com.amg.exchange.treasury.bean.DealTrackerInfoDTBean;
import com.amg.exchange.treasury.bean.DealTrackerViewTicketDataTable;
import com.amg.exchange.treasury.dao.IDealTrackerInfoDao;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.ViewCorrespondingBankCountry;
import com.amg.exchange.treasury.model.VwExBulkFxDeal;
import com.amg.exchange.treasury.service.IDealTrackerInfoService;
import com.amg.exchange.treasury.viewModel.DealTrackerTicketView;
import com.amg.exchange.util.AMGException;


@SuppressWarnings("serial")
@Service("dealTrackerInfoServiceImpl")
public class DealTrackerInfoServiceImpl<T> implements IDealTrackerInfoService<T>, Serializable{

	@Autowired
	IDealTrackerInfoDao<T> dealTrackerInfoDao;

	@Override
	@Transactional
	public List<DealTrackerTicketView> getDealTrackerTicketViewInfo(Date dealDate) {
		return dealTrackerInfoDao.getDealTrackerTicketViewInfo(dealDate);
	}

	@Override
	@Transactional
	public HashMap<String, Object> getIDsFromCode(DealTrackerInfoDTBean dealTrackerInfoDTBean) {
		return dealTrackerInfoDao.getIDsFromCode(dealTrackerInfoDTBean);
	}

	@Override
	@Transactional
	public List<DealTrackerTicketView> getDealTrackerTicketView(Date dealDate,BigDecimal countryId,String currencyCode,String bankCode) throws AMGException {
		return dealTrackerInfoDao.getDealTrackerTicketView(dealDate, countryId, currencyCode, bankCode);
	}

	@Override
	@Transactional
	public HashMap<String, Object> getIDsFromCodeDealTickets(DealTrackerViewTicketDataTable dealTrackerInfoDTBean,BigDecimal countryId,BigDecimal companyId) {
		return dealTrackerInfoDao.getIDsFromCodeDealTickets(dealTrackerInfoDTBean, countryId, companyId);
	}
	
	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyNameandCurrencyIdBasedonQuoteName(String quoteName) {
		return dealTrackerInfoDao.getCurrencyNameandCurrencyIdBasedonQuoteName(quoteName);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveAllFXDealBank(HashMap<String, Object> saveMapInfo)throws Exception {
		dealTrackerInfoDao.saveAllFXDealBank(saveMapInfo);
	}

	@Override
	@Transactional
	public List<BankAccountDetails> getBankAccountDetlsBasedOnCode(
			String bankCode, String currencyCode) {
		
		return dealTrackerInfoDao.getBankAccountDetlsBasedOnCode(bankCode, currencyCode);
	}

	@Override
	@Transactional
	public List<BankMaster> getBankMasterInfo(String bankCode) {
		
		return dealTrackerInfoDao.getBankMasterInfo(bankCode);
	}

	@Override
	@Transactional
	public List<TreasuryDealHeader> getTreasuryHrdForDealTracker(Date dealDate,
			String dealID) {
		
		return dealTrackerInfoDao.getTreasuryHrdForDealTracker(dealDate, dealID);
	}

	@Override
	@Transactional
	public String getSplitIndicatorFromBankMaster( String bankCode) {
 
		return dealTrackerInfoDao.getSplitIndicatorFromBankMaster(bankCode);
	}

	@Override
	@Transactional
	public HashMap< String, String>  getForeignCurrrencyAmountFrmSpclCustomer(
			BigDecimal docFinYear, BigDecimal docNo) {
	 
		return dealTrackerInfoDao.getForeignCurrrencyAmountFrmSpclCustomer(docFinYear,docNo);
	}

	@Override
	@Transactional
	public BigDecimal getBankId(String bankCode) {
	 
		return dealTrackerInfoDao.getBankId(bankCode);
	}

	@Override
	@Transactional
	public BigDecimal getCurrencyId(String currencyCode) {
		// TODO Auto-generated method stub
		return dealTrackerInfoDao.getCurrencyId(currencyCode);
	}

	@Override
	@Transactional
	public BigDecimal getFinancialYearId(BigDecimal finacialYear) {
		 
		return dealTrackerInfoDao.getFinancialYearId(finacialYear);
	}

	@Override
	@Transactional
	public  BigDecimal getCustomerRefBasedOnCustomerId(BigDecimal customerId) {
		 
		return dealTrackerInfoDao.getCustomerRefBasedOnCustomerId(customerId);
	}

	@Override
	@Transactional
	public List<ViewCorrespondingBankCountry> getCorrespondingbankCountryList() {
		
		return dealTrackerInfoDao.getCorrespondingbankCountryList();
	}

	@Override
	@Transactional
	public List<DealTrackerTicketView> getDealTrackerTicketwithNativeQuery(Date currentDate, BigDecimal countryId, String currencyCode, String bankCode) throws AMGException {
		return dealTrackerInfoDao.getDealTrackerTicketwithNativeQuery(currentDate, countryId, currencyCode, bankCode);
	}

	@Override
	@Transactional
	public HashMap<String, Object> ValidateDealIDWhileUpdate(String dealID,
			Date dealDate) throws AMGException {
		
		return dealTrackerInfoDao.ValidateDealIDWhileUpdate(dealID, dealDate);
	}

	@Override
	@Transactional
	public List<TreasuryDealDetail> getBulkFXDealUnApprovalRecords(
			BigDecimal companyId, BigDecimal countryId, BigDecimal currencyId,
			BigDecimal bankId) {
		
		return dealTrackerInfoDao.getBulkFXDealUnApprovalRecords(companyId, countryId, currencyId, bankId);
	}

	@Override
	@Transactional
	public List<BulkDealApprovalDataTable> getBulkUnApproveDealHrDetails(
			BigDecimal companyId, BigDecimal countryId, BigDecimal currencyId,
			BigDecimal bankId) {
		
		return dealTrackerInfoDao.getBulkUnApproveDealHrDetails(companyId, countryId, currencyId, bankId);
	}

	@Override
	@Transactional
	public String bulkDealApprove(List<BigDecimal> lstTreasuryHrdId,
			String userName) {
		return dealTrackerInfoDao.bulkDealApprove(lstTreasuryHrdId, userName);
		
	}

	@Override
	@Transactional
	public String bulkDealDelete(List<BigDecimal> lstTreasuryHrdId,
			String userName) {
		return dealTrackerInfoDao.bulkDealDelete(lstTreasuryHrdId, userName);
	}

	@Override
	@Transactional
	public List<VwExBulkFxDeal> getViewBulckFxDDealApproval(
			BigDecimal companyId, BigDecimal countryId, BigDecimal bankCountryId,
			BigDecimal bankId) {
		return dealTrackerInfoDao.getViewBulckFxDDealApproval(companyId, countryId, bankCountryId, bankId);
	}

	@Override
	@Transactional
	public String bulkDealApproveWithProcedure(BigDecimal companyId,
			BigDecimal contryId, List<BigDecimal> lstTreasuryHrdId,
			String userName)throws Exception {
		
		return dealTrackerInfoDao.bulkDealApproveWithProcedure(companyId, contryId, lstTreasuryHrdId, userName);
	}
}
