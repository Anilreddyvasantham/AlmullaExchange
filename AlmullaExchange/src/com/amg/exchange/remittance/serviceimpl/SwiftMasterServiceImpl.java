package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.ISwiftMasterDao;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.SwiftMasterCountryView;
import com.amg.exchange.remittance.service.ISwiftMasterService;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

@Service("swiftMasterService")
public class SwiftMasterServiceImpl implements ISwiftMasterService {

	  @Autowired
	  ISwiftMasterDao iswiftDao;

	  @Transactional
	  public void saveOrUpdate(SwiftMaster swiftMaster) {
		    iswiftDao.saveOrUpdate(swiftMaster);

	  }

	  @Transactional
	  public List<SwiftMaster> getSwiftRecord(String swiftCountryId, String swiftBankId, String branchId) {
		    return iswiftDao.getSwiftRecord(swiftCountryId, swiftBankId, branchId);
	  }

	  @Transactional
	  public List<SwiftMaster> getAllActiveDeActiveSwift() {
		    return iswiftDao.getAllActiveDeActiveSwift();
	  }

	  @Transactional
	  public String getBranchFullName(BigDecimal branchId) {
		    return iswiftDao.getBranchFullName(branchId);

	  }

	  @Override
	  @Transactional
	  public List<SwiftMaster> getAllSwiftForApprove() {

		    return iswiftDao.getAllSwiftForApprove();
	  }

	  @Transactional
	  public String getBankFullName(BigDecimal bankId) {
		    return iswiftDao.getBankFullName(bankId);
	  }

	  @Override
	  @Transactional
	  public List<SwiftMaster> getSwiftEnquiry(String swiftCountryCode, String swiftBankCode) {

		    return iswiftDao.getSwiftEnquiry(swiftCountryCode, swiftBankCode);
	  }

	  @Override
	  @Transactional
	  public void delete(SwiftMaster swiftMaster) {
		    iswiftDao.delete(swiftMaster);

	  }

	  @Override
	  @Transactional
	  public String approveRecord(BigDecimal swiftMasterPk, String userName) {

		    return iswiftDao.approveRecord(swiftMasterPk, userName);
	  }

	  @Override
	  @Transactional
	  public String getBranchName(String branchCode) {

		    return iswiftDao.getBranchName(branchCode);
	  }

	  @Override
	  @Transactional
	  public String getBankFullNameBasedOnCode(String bankCode) {

		    return iswiftDao.getBankFullNameBasedOnCode(bankCode);
	  }

	  @Override
	  @Transactional
	  public BigDecimal getBankId(String bankCode) {

		    return iswiftDao.getBankId(bankCode);
	  }

	  @Override
	  @Transactional
	  public BigDecimal getCountryId(String countryCode) {

		    return iswiftDao.getCountryId(countryCode);
	  }

	  @Override
	  @Transactional
	  public BigDecimal getBranchId(String branchCode) {

		    return iswiftDao.getBranchId(branchCode);
	  }

	  @Override
	  @Transactional
	  public List<SwiftMaster> toFetchAllActiveRecords() {
		    return iswiftDao.toFetchAllActiveRecords();
	  }

 
	  @Override
	  @Transactional
	public String getSwiftBicFromBankBranch(BigDecimal branchId,
			BigDecimal bankId) {
 
		return iswiftDao.getSwiftBicFromBankBranch( branchId,bankId);
			
	}

	@Override
	 @Transactional
	public String getCountryNameBasedOnCountryAlphaCode(String alphaCode) {
 
		return iswiftDao.getCountryNameBasedOnCountryAlphaCode(alphaCode);
	}

	@Override
	 @Transactional
	public BigDecimal getBranchIdForPerticular(String branchCode,
			BigDecimal BankId) {
 
		return iswiftDao.getBranchIdForPerticular(branchCode,BankId);
	}

	@Override
	@Transactional
	public List<BankBranch> getBranchList( ) {
 
		return iswiftDao.getBranchList( );
	}

	@Override
	 @Transactional
	public List<BankMaster> getBankMasterListBasedOnCode( ) {
 
		return  iswiftDao.getBankMasterListBasedOnCode( );
	}
	
	@Override
	@Transactional
	public List<String> getAllSwiftBank(String swiftBank){
		return  iswiftDao.getAllSwiftBank(swiftBank);
	}
	@Override
	@Transactional
	 public List<String> getAllSwiftLocation(String swiftBankId, String swiftCountryId,String swiftLocation){
		 return  iswiftDao.getAllSwiftLocation(swiftBankId,swiftCountryId,swiftLocation);
	 }
	
	@Override
	@Transactional
	 public List<String> getAllSwiftBranch(String swiftBankId, String swiftCountryId,String swiftLocationId,String swiftBranch){
		 return  iswiftDao.getAllSwiftBranch(swiftBankId,swiftCountryId,swiftLocationId,swiftBranch);
	 }
	
	@Override
	@Transactional
	public List<SwiftMaster> getSwiftInfo(String swiftBankCode,String swiftCountryCode, String location, String branchCode){
		return  iswiftDao.getSwiftInfo(swiftBankCode, swiftCountryCode,location,branchCode);
	}
	
	@Override
	@Transactional
	public List<SwiftMasterCountryView> getAllSwiftCountry(String swiftBankCode){
		return  iswiftDao.getAllSwiftCountry(swiftBankCode);
	}
}
