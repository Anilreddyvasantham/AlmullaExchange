package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.bean.PopulateData;
import com.amg.exchange.remittance.dao.IMobileAppVivaServiceDao;
import com.amg.exchange.remittance.model.AlternateChannels;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.ServiceProviderView;
import com.amg.exchange.remittance.service.IMobileAppVivaService;
import com.amg.exchange.util.AMGException;

@Service("mobileAppVivaServiceImpl")
public class MobileAppVivaServiceImpl<T> implements IMobileAppVivaService<T>{

	@Autowired
	IMobileAppVivaServiceDao<T> iMobileAppVivaServiceDao;

	@Override
	@Transactional
	public List<PopulateData> getBeneficiarNameList(BigDecimal customerId,
			BigDecimal beneCountryId, BigDecimal customerRef) {
		return iMobileAppVivaServiceDao.getBeneficiarNameList(customerId, beneCountryId, customerRef);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneficiarBankList(String beneName,
			BigDecimal beneCountryId, BigDecimal customerId) {
		return iMobileAppVivaServiceDao.getBeneficiarBankList(beneName, beneCountryId, customerId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneAccountNumber(String beneName,BigDecimal beneCountryId,BigDecimal beneBankId ,BigDecimal customerId,BigDecimal beneBranchId) {
		return iMobileAppVivaServiceDao.getBeneAccountNumber(beneName, beneCountryId, beneBankId, customerId,beneBranchId);
	}

	@Override
	@Transactional
	public List<PopulateData> getCountryList(BigDecimal customerId,
			BigDecimal languageId) {
		
		return iMobileAppVivaServiceDao.getCountryList(customerId, languageId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBenNameList(BigDecimal customerId,
			BigDecimal countryId) {
	
		return iMobileAppVivaServiceDao.getBenNameList(customerId, countryId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneBankList(BigDecimal customerId,
			BigDecimal countryId, String customerName) {
		
		return iMobileAppVivaServiceDao.getBeneBankList(customerId, countryId, customerName);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneAccountNoList(BigDecimal customerId, BigDecimal countryId, BigDecimal bankId, String customerName,BigDecimal beneBankId) {
		
		return iMobileAppVivaServiceDao.getBeneAccountNoList(customerId, countryId, bankId, customerName,beneBankId);
	}

	@Override
	@Transactional
	public List<PopulateData> getBeneAccountNoList(BigDecimal bankId,
			BigDecimal customerId, BigDecimal countryId,String beneName) {
		
		return iMobileAppVivaServiceDao.getBeneAccountNoList(bankId, customerId, countryId,beneName);
	}

	@Override
	@Transactional
	public List<AlternateChannels> findExistSericeProvider(BigDecimal customerId) {
	
		return iMobileAppVivaServiceDao.findExistSericeProvider(customerId);
	}

	@Override
	@Transactional
	public BeneficaryAccount getRelionShipDetails(BigDecimal beneCountryId,
			BigDecimal beneBankId, BigDecimal customerId,
			BigDecimal beneBranchId, String bankAccountNumber) {
		
		return iMobileAppVivaServiceDao.getRelionShipDetails(beneCountryId, beneBankId, customerId, beneBranchId, bankAccountNumber);
	}

	@Override
	@Transactional
	public List<AlternateChannels> checkDuplicateSericeProviderBankBeneAcc(
			BigDecimal customerId, BigDecimal beneCountryId,
			BigDecimal beneBankid, String accountNumber) {
		
		return iMobileAppVivaServiceDao.checkDuplicateSericeProviderBankBeneAcc(customerId, beneCountryId, beneBankid, accountNumber);
	}

	@Override
	@Transactional
	public String verifyOtpNo(BigDecimal optNo, BigDecimal customerPkId,
			String userName) throws AMGException {
		
		return iMobileAppVivaServiceDao.verifyOtpNo(optNo, customerPkId, userName);
	}

	@Override
	@Transactional
	public void saveAlternateChanlesDetails(
			HashMap<String, AlternateChannels> saveMap, String userName,
			BigDecimal customerId, String availOnline, String avialKiosk,
			String avialMobile,String avialServiceProvider)throws AMGException {
		iMobileAppVivaServiceDao.saveAlternateChanlesDetails(saveMap, userName, customerId, availOnline, avialKiosk, avialMobile,avialServiceProvider);
		
	}

	@Override
	@Transactional
	public HashMap<String, String> getAvialServices(BigDecimal customerId) {
		
		return iMobileAppVivaServiceDao.getAvialServices(customerId);
	}

	@Override
	@Transactional
	public void deactivateExistServiceProvider(BigDecimal alternateChannelsID,String userName) {
		iMobileAppVivaServiceDao.deactivateExistServiceProvider(alternateChannelsID,userName);
		
	}

	@Override
	@Transactional
	public int updateOTPDetails(BigDecimal customerId, String otpNumber,
			String userName) throws AMGException {
		
		return iMobileAppVivaServiceDao.updateOTPDetails(customerId, otpNumber, userName);
	}

	@Override
	@Transactional
	public List<AlternateChannels> checkExistRecordUpdateToDeativate(
			BigDecimal customerId, String channelId, String userName) {
		
		return iMobileAppVivaServiceDao.checkExistRecordUpdateToDeativate(customerId, channelId, userName);
	}

	@Override
	@Transactional
	public List<LanguageType> getLanguageType(BigDecimal languageId) {
		
		return iMobileAppVivaServiceDao.getLanguageType(languageId);
	}

	@Override
	@Transactional
	public HashMap<String, String> callOTPSendProcedure(
			HashMap<String, String> inputValues) throws AMGException {
		
		return iMobileAppVivaServiceDao.callOTPSendProcedure(inputValues);
	}

	@Override
	@Transactional
	public List<ServiceProviderView> getSrviceProvider() {
		
		return iMobileAppVivaServiceDao.getSrviceProvider();
	}

	@Override
	@Transactional
	public List<BenificiaryListView> getBeneficirarylist(
			BigDecimal beneMasterId, BigDecimal beneAccSeqId,
			BigDecimal beneRelSeqId) {
		
		return iMobileAppVivaServiceDao.getBeneficirarylist(beneMasterId, beneAccSeqId, beneRelSeqId);
	}
}
