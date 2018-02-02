package com.amg.exchange.online.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.remittance.bean.PopulateData;

public interface IBranchStaffGSMRateDao {

	public List<RatePlaceOrder> toFetchAllRecordsFromDb(BigDecimal branchId);
	
	public List<RatePlaceOrder> tofetchCustomerPlaceOrderRecords(BigDecimal customerId,BigDecimal branchId);
	
	public void toRejectRateForCustomer(BigDecimal rateOfferedPk,String userName);
	
	public String checkPlaceOrderStatusForAccept(BigDecimal rateOfferedPk);
	
	public String checkPlaceOrderStatusForReject(BigDecimal rateOfferedPk);
	
	public void createPlaceOrderForNegotiate(BigDecimal rateOfferedPk,String userName,BigDecimal documentNumber);
	
	public String saveOrUpdatePlaceOrderAddlData(HashMap<String, Object> inputValues)throws Exception;
	
	public List<RatePlaceOrder> toFetchAllBranchStaffGsmBasedOnCustId(BigDecimal branchId,BigDecimal customerId);
	
	public String checkPlaceOrderStatusForNegotiate(BigDecimal rateOfferedPk);
	
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,BigDecimal beneCountryId,BigDecimal serviceGroupId,BigDecimal customerRef,BigDecimal beneBankId);

}
