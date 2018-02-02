package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.remittance.model.BankExternalReferenceDetail;
import com.amg.exchange.remittance.model.BankExternalReferenceHead;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.util.AMGException;

public interface IBankExternalReferenceDao {

	public void saveHeaderData(BankExternalReferenceHead bankExternalReferenceHead);

	public void saveDetailData(BankExternalReferenceDetail bankExternalReferenceDetail);

	public List<BankExternalReferenceHead> viewBean(BigDecimal countryId, BigDecimal bankId, BigDecimal appContryId,BigDecimal beneBankId);

	public BankExternalReferenceHead findHeaderById(BigDecimal bankExtRefSeqId);

	public List<BankExternalReferenceDetail> findDetailById(BigDecimal bankExtRefSeqId);

	public List<BankExternalReferenceDetail> getAllRecords(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal benfiBankId, String bankExternalId);

	public List<BankExternalReferenceHead> getAllRecordsfromHead(BigDecimal countryId, BigDecimal bankCountryId, BigDecimal bankId, BigDecimal benfiBankId, String externalId);

	public List<BankExternalReferenceDetail> getAllRecords();

	public List<BankExternalReferenceHead> getAllRecordsfromHead(boolean fetchAll, BigDecimal bankCountryId, BigDecimal bankId);

	public List<BankApplicability> getBankListbyIndicators(BigDecimal bankCountryId, BigDecimal correspondingbankindicator, BigDecimal serviceproviderbankindicator);

	public void delete(BankExternalReferenceHead bankExternalReferenceHead, List<BankExternalReferenceDetail> list);

	public void delele(BankExternalReferenceHead bankExternalReferenceHead);

	public String approveRecord(BigDecimal bankExtRefSeqId, String userName);

	public void approveDetailsRecord(BigDecimal bankExtRefSeqId, String userName);

    public boolean saveAllDetails(HashMap<String, Object> saveMapInfo)throws Exception;
    
    public List<BankBranch> getBankBranchListFromCountryBank(BigDecimal countryId,BigDecimal bankId);
    
    public void deactiveRecordToPendingForApproval(BigDecimal bankExtranlDetlId, String userName);
    public void upDateActiveRecordtoDeActive(BigDecimal bankExtranlDetlId, String remarks, String userName);
	public void deleteBranchRecord(BigDecimal branchDetailsPk) ;
	public List<BankExternalReferenceHead> getDuplicateCheckList(
			BigDecimal countryId, BigDecimal bankId, BigDecimal appContryId,
			BigDecimal beneBankId);
	
	
	public HashMap<String, String> callPopulateBankExternalRef(HashMap<String, String> approveRecord) throws AMGException;

	public void procErrorToUnApprove(BigDecimal bankExtRefSeqId,List<BigDecimal> list);

	public List<BankExternalReferenceDetail> findDetailByIdUnApproved(BigDecimal bankExtRefSeqId);
}
