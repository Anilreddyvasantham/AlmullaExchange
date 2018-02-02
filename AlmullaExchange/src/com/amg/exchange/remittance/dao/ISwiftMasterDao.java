package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.model.SwiftMasterCountryView;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;

public interface ISwiftMasterDao {

	  public void saveOrUpdate(SwiftMaster swiftMaster);

	  public List<SwiftMaster> getSwiftRecord(String swiftCountryCode, String swiftBankCode, String branchCode);

	  public List<SwiftMaster> getAllActiveDeActiveSwift();

	  public String getBranchFullName(BigDecimal branchId);

	  public List<SwiftMaster> getAllSwiftForApprove();

	  public String getBankFullName(BigDecimal bankId);

	  public List<SwiftMaster> getSwiftEnquiry(String swiftCountryCode, String swiftBankCode);

	  public void delete(SwiftMaster swiftMaster);

	  public String approveRecord(BigDecimal swiftMasterPk, String userName);

	  public String getBranchName(String branchCode);

	  public String getBankFullNameBasedOnCode(String bankCode);

	  public BigDecimal getBankId(String bankCode);

	  public BigDecimal getCountryId(String countryCode);

	  public BigDecimal getBranchId(String branchCode);
	  public BigDecimal getBranchIdForPerticular(String branchCode,
				BigDecimal BankId);

	  public List<SwiftMaster> toFetchAllActiveRecords();
	  
	  public String getSwiftBicFromBankBranch(BigDecimal branchId,BigDecimal bankId);
	  
	  public String getCountryNameBasedOnCountryAlphaCode(String alphaCode);
	  public  List<BankBranch> getBranchList( );

	  public List<BankMaster> getBankMasterListBasedOnCode( );
	  
	  public List<String> getAllSwiftBank(String swiftBank);
	  public List<String> getAllSwiftLocation(String swiftBankId, String swiftCountryId, String swiftLocation);
	  public List<String> getAllSwiftBranch(String swiftBankId, String swiftCountryId,String swiftLocationId,String swiftBranch);	  
	  public List<SwiftMaster> getSwiftInfo(String swiftBankCode,String swiftCountryCode, String location, String branchCode);	  
	  public List<SwiftMasterCountryView> getAllSwiftCountry(String swiftBankCode);
			  
		 
}
