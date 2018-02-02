package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.model.AlternateChannels;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.ServiceProviderView;
import com.amg.exchange.util.AMGException;

public interface IMobileAppVivaServiceDao<T> {

	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,BigDecimal beneCountryId,BigDecimal customerRef);
	public List<PopulateData> getBeneficiarBankList(String beneName,BigDecimal beneCountryId,BigDecimal customerId);
	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId);
	
	public List<PopulateData> getCountryList(BigDecimal customerId,BigDecimal languageId) ;
	public List<PopulateData> getBenNameList(BigDecimal customerId, BigDecimal countryId);
	public List<PopulateData> getBeneBankList(BigDecimal customerId, BigDecimal countryId, String customerName);
	public List<PopulateData> getBeneAccountNoList(BigDecimal customerId, BigDecimal countryId, BigDecimal bankId, String customerName,BigDecimal beneBankId);
	public List<PopulateData> getBeneAccountNoList(BigDecimal bankId,BigDecimal customerId, BigDecimal countryId,String beneName);
	public List<AlternateChannels> findExistSericeProvider(BigDecimal customerId);
	public BeneficaryAccount getRelionShipDetails(BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId,String bankAccountNumber);
	public List<AlternateChannels> checkDuplicateSericeProviderBankBeneAcc(BigDecimal customerId ,BigDecimal beneCountryId,BigDecimal beneBankid,String accountNumber);
	public String verifyOtpNo(BigDecimal optNo,BigDecimal customerPkId,  String userName) throws AMGException;
	public void saveAlternateChanlesDetails(HashMap<String, AlternateChannels> saveMap,String userName,BigDecimal customerId ,String availOnline,String avialKiosk,String avialMobile,String avialServiceProvider)throws AMGException;
	public HashMap<String, String> getAvialServices(BigDecimal customerId);
	public void deactivateExistServiceProvider(BigDecimal alternateChannelsID,String userName);
	public int updateOTPDetails(BigDecimal customerId,String otpNumber,String userName) throws AMGException;
	public List<AlternateChannels> checkExistRecordUpdateToDeativate(BigDecimal customerId,String channelId,String userName);
	public List<LanguageType> getLanguageType(BigDecimal languageId);
	public HashMap<String, String> callOTPSendProcedure(HashMap<String, String> inputValues) throws AMGException;
	public List<ServiceProviderView> getSrviceProvider();
	public List<BenificiaryListView> getBeneficirarylist(BigDecimal beneMasterId,BigDecimal beneAccSeqId,BigDecimal beneRelSeqId);
}
