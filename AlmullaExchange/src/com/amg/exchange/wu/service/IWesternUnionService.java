package com.amg.exchange.wu.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
import com.amg.exchange.wu.model.VoyagerExceptionModel;
import com.amg.exchange.wu.model.WesternUnionTransfer;

public interface IWesternUnionService {

	public String getNextToken();

	public void saveWesternUnionTransfer(WesternUnionTransfer westernUnionTransfer);

	public String updateWUTransfer(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo);

	public List<WesternUnionTransfer> getWUData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo);

	public List<CollectDetail> getCollectionData(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo);

	public String transferToOldSystem(BigDecimal companyId, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo);

	public List<WesternUnionTransfer> getWUDataByMtcNo(String mtcNo);

	public List<RemittanceTransaction> getRemittanceDataByMtcNo(String mtcNo);

	public void saveForeignCurrencyAdjust(List<ForeignCurrencyAdjustWU> foreignCurrencyAdjust);

	public List<CollectDetailWU> getCollectionDataWU(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal tokenNo);

	public List<WesternUnionTransfer> getWUDataReceive(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, String mtcno,String trnxType);

	public List<BeneficaryRelationship> getBeniRelationship(BigDecimal relationId);

	public BigDecimal getCollectionAmount(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo);

	List<BenificiaryListView> getBeneficaryList(BigDecimal customerNo);

	public BankMaster getWUBank(String tRANSACTION_STATUS_FOR_WESTERN_UNION);

	public void saveBeneficary(BeneficaryAccount beneficaryAccount, BeneficaryRelationship relationship);

	public List<BenificiaryListView> fetchAllBeneficiaryCountrys();

	public List<BeneficaryRelationship> fetchBeneficiaryRelationShip(BigDecimal beneficiaryMasterSeqId,BigDecimal beneficiaryAccSeqId,BigDecimal customerId);

	public Boolean checkBeneficaryAccountDetailsForWUnion(BigDecimal bankCountry,BigDecimal bankId,BigDecimal bankBranchId,BigDecimal currency,BigDecimal beneMasterSeqId);

	public List<WesternUnionTransfer> getWUPendingTransactionList(Date createDate, BigDecimal branchId,String userName);

	public List<WesternUnionTransfer> getWUTransactionWithOutDenomination(String username,String location,BigDecimal customerRef);

	public List<WesternUnionTransfer> getWUTransactionWithOutDenominationlist();

	public List<Customer> getCustomerDetail(BigDecimal customerId);

	public List<BeneficaryAccount> getBeneficaryAccountDetails(BigDecimal beneficiaryMasterSeqId,String bankCode,BigDecimal bankCountryId,BigDecimal currencyId);

	public List<BeneficaryMaster> fetchBeneMasterRecForWU(Map<String, Object> mapBeneMasterCheck);

	public List<WesternUnionTransfer> fetchWesternUnionTransfer(Map<String, String> lstWuTransfer);

	public List<WesternUnionTransfer> getAllWUException(Date createDate, String branchCode,String userName);

	public List<VoyagerExceptionModel> getAllWUExceptionErrorMsg(WesternUnionTransfer lstWURec);

	public String updateWUTransferForApprovalException(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo);

	public void deleteFromExTempCurrencyAdjust(BigDecimal companyCode, BigDecimal documentCode, BigDecimal documentFinanceYr, BigDecimal documentNo,String mtcNo);
	
	public List<BeneficaryRelationship> fetchBeneficiaryRelationShipForWU(BigDecimal beneficiaryMasterSeqId,String bankCode,BigDecimal customerId);
	
	public List<WesternUnionTransfer> getWUTransactionDenomination(String location,BigDecimal operationId);
	
	public List<WUTranxFileUploadDatatable> checkWuMoneyTransferMTCNO(List<WUTranxFileUploadDatatable> lstWUTranxFileUploadData,BigDecimal docyear);
	
	public List<WesternUnionTransfer> getWUDataByMtcNo(BigDecimal documentFinanceYr,String mtcNo);

}
