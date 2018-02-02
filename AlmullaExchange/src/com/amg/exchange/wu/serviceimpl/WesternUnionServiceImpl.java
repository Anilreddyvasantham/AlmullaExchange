package com.amg.exchange.wu.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CollectDetailWU;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjustWU;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.BeneficaryRelationship;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.wu.bean.WUTranxFileUploadDatatable;
import com.amg.exchange.wu.dao.IWesternUnionDao;
import com.amg.exchange.wu.model.VoyagerExceptionModel;
import com.amg.exchange.wu.model.WesternUnionTransfer;
import com.amg.exchange.wu.service.IWesternUnionService;

@Service
@Transactional
public class WesternUnionServiceImpl implements IWesternUnionService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	IWesternUnionDao wuDao;

	@Override
	@Transactional
	public String getNextToken() {
		return wuDao.getNextToken();
	}

	@Override
	@Transactional
	public void saveWesternUnionTransfer(WesternUnionTransfer westernUnionTransfer) {
		wuDao.saveWesternUnionTransfer(westernUnionTransfer);
	}

	@Override
	@Transactional
	public String updateWUTransfer(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		return wuDao.updateWUTransfer(companyCode, documentCode, documentFinanceYr, documentNo);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		return wuDao.getWUData(companyCode, documentCode, documentFinanceYr, tokenNo);
	}

	@Override
	@Transactional
	public List<CollectDetail> getCollectionData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		return wuDao.getCollectionData(companyCode, documentCode, documentFinanceYr, tokenNo);
	}

	@Override
	@Transactional
	public String transferToOldSystem(BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		return wuDao.transferToOldSystem(companyId, documentCode, documentFinanceYr, documentNo);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUDataByMtcNo(String mtcNo) {
		return wuDao.getWUDataByMtcNo(mtcNo);
	}

	@Override
	@Transactional
	public List<RemittanceTransaction> getRemittanceDataByMtcNo(String mtcNo) {
		return wuDao.getRemittanceDataByMtcNo(mtcNo);
	}

	@Override
	@Transactional
	public void saveForeignCurrencyAdjust(List<ForeignCurrencyAdjustWU> foreignCurrencyAdjust) {
		wuDao.saveForeignCurrencyAdjust(foreignCurrencyAdjust);
	}

	@Override
	@Transactional
	public List<CollectDetailWU> getCollectionDataWU(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo) {
		return wuDao.getCollectionDataWU(companyCode, documentCode, documentFinanceYr, tokenNo);
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> getBeniRelationship(BigDecimal relationId) {
		return wuDao.getBeniRelationship(relationId);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUDataReceive(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, String mtcno,String trnxType) {
		return wuDao.getWUDataReceive(companyCode, documentCode, documentFinanceYr, mtcno,trnxType);
	}

	@Override
	@Transactional
	public BigDecimal getCollectionAmount(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		return wuDao.getCollectionAmount(companyCode, documentCode, documentFinanceYr, documentNo);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo) {
		return wuDao.getBeneficaryList(customerNo);
	}

	@Override
	@Transactional
	public BankMaster getWUBank(String tRANSACTION_STATUS_FOR_WESTERN_UNION) {
		return wuDao.getWUBank(tRANSACTION_STATUS_FOR_WESTERN_UNION);
	}

	@Override
	@Transactional
	public void saveBeneficary(BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship) {
		 wuDao.saveBeneficary(beneficaryAccount,relationship);
	}

	@Override
	@Transactional
	public List<BenificiaryListView> fetchAllBeneficiaryCountrys() {
		return wuDao.fetchAllBeneficiaryCountrys();
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> fetchBeneficiaryRelationShip(BigDecimal beneficiaryMasterSeqId,BigDecimal beneficiaryAccSeqId,BigDecimal customerId) {
		return wuDao.fetchBeneficiaryRelationShip(beneficiaryMasterSeqId, beneficiaryAccSeqId, customerId);
	}

	@Override
	@Transactional
	public Boolean checkBeneficaryAccountDetailsForWUnion(BigDecimal bankCountry, BigDecimal bankId, BigDecimal bankBranchId,BigDecimal currency, BigDecimal beneMasterSeqId) {
		return wuDao.checkBeneficaryAccountDetailsForWUnion(bankCountry, bankId, bankBranchId, currency, beneMasterSeqId);
	}
	
	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUPendingTransactionList(
			Date createDate, BigDecimal branchId, String userName) {
		
		return  wuDao.getWUPendingTransactionList(createDate, branchId, userName);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUTransactionWithOutDenomination(String username,String location,BigDecimal customerRef) {
		return wuDao.getWUTransactionWithOutDenomination(username, location, customerRef);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUTransactionWithOutDenominationlist() {
		return wuDao.getWUTransactionWithOutDenominationlist();
	}
	
	@Override
	@Transactional
	public List<Customer> getCustomerDetail(BigDecimal customerId){
		return wuDao.getCustomerDetail(customerId);
	}
	
	@Override
	@Transactional
	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId,String bankCode,BigDecimal bankCountryId,BigDecimal currencyId) {
		return wuDao.getBeneficaryAccountDetails(beneficiaryMasterSeqId, bankCode, bankCountryId, currencyId);
	}

	@Override
	@Transactional
	public List<BeneficaryMaster> fetchBeneMasterRecForWU(Map<String, Object> mapBeneMasterCheck) {
		return wuDao.fetchBeneMasterRecForWU(mapBeneMasterCheck);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> fetchWesternUnionTransfer(Map<String, String> lstWuTransfer) {
		return wuDao.fetchWesternUnionTransfer(lstWuTransfer);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getAllWUException(Date createDate,String branchCode, String userName) {
		return wuDao.getAllWUException(createDate, branchCode, userName);
	}

	@Override
	@Transactional
	public List<VoyagerExceptionModel> getAllWUExceptionErrorMsg(WesternUnionTransfer lstWURec) {
		return wuDao.getAllWUExceptionErrorMsg(lstWURec);
	}

	@Override
	@Transactional
	public String updateWUTransferForApprovalException(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo) {
		return wuDao.updateWUTransferForApprovalException(companyCode, documentCode, documentFinanceYr, documentNo);
	}

	@Override
	@Transactional
	public void deleteFromExTempCurrencyAdjust(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,
			String mtcNo) {
		wuDao.deleteFromExTempCurrencyAdjust(companyCode, documentCode, documentFinanceYr, documentNo, mtcNo);
		
	}

	@Override
	@Transactional
	public List<BeneficaryRelationship> fetchBeneficiaryRelationShipForWU(BigDecimal beneficiaryMasterSeqId, String bankCode,BigDecimal customerId) {
		return wuDao.fetchBeneficiaryRelationShipForWU(beneficiaryMasterSeqId, bankCode, customerId);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUTransactionDenomination(String location,BigDecimal operationId) {
		return wuDao.getWUTransactionDenomination(location, operationId);
	}

	@Override
	@Transactional
	public List<WUTranxFileUploadDatatable> checkWuMoneyTransferMTCNO(List<WUTranxFileUploadDatatable> lstWUTranxFileUploadData,BigDecimal docyear) {
		return wuDao.checkWuMoneyTransferMTCNO(lstWUTranxFileUploadData, docyear);
	}

	@Override
	@Transactional
	public List<WesternUnionTransfer> getWUDataByMtcNo(BigDecimal documentFinanceYr, String mtcNo) {
		return wuDao.getWUDataByMtcNo(documentFinanceYr, mtcNo);
	}
	
}
