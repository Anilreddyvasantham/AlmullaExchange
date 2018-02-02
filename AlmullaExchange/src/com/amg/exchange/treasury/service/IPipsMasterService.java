package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;




import com.amg.exchange.remittance.model.ViewPIPSType;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.PipsMaster;

public interface IPipsMasterService {

	public void saveRecord(PipsMaster pipsMasterObj);
	
	public List<PipsMaster> pipsListFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal branchId,BigDecimal serviceId,BigDecimal bankId);
	
	public void update(PipsMaster pipsMasterObj);
	
	public List<PipsMaster>  getAllPipsList();
	public List<PipsMaster> getAllPipsApprove();
	
	public List<BeneCountryService> getCurrencyMaster(BigDecimal countryId);
	
	public List<PipsMaster> populatePipsListFrmDB(BigDecimal countryId,BigDecimal currencyId,BigDecimal bankId);
	
	public List<PipsMaster> pipsListForEnquiry(BigDecimal countryId,BigDecimal currencyId,BigDecimal countryBranchId,BigDecimal serviceId,BigDecimal bankId);
	public String approveRecord(BigDecimal pipsMasterPk,String userName);

	public List<ViewPIPSType> toFetchAllPipsIndParamView();

	public String toFetchFullNameTypeCode(String pipsTypeCode);
	
}
