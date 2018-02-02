package com.amg.exchange.cancelreissue.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.cancelreissue.bean.RepushBankTrnxDataTable;
import com.amg.exchange.cancelreissue.dao.ICancelReissueDao;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRepushBankTrnxList;
import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.util.AMGException;

@SuppressWarnings("serial")
@Service("cancelReissueServiceImpl")
public class CancelReissueServiceImpl<T> implements ICancelReissueService<T>, Serializable {

	@Autowired
	ICancelReissueDao<T> cancelReissueDao;

	@Override
	@Transactional
	public String getCompanyName(BigDecimal companyId, BigDecimal languageId) {
		return cancelReissueDao.getCompanyName(companyId, languageId);
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> fetchRemiitanceDetails(BigDecimal appCountryId,
			BigDecimal companyId, BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal remiitanceRefNo) {
		return cancelReissueDao.fetchRemiitanceDetails(appCountryId, companyId, documentCode, documentFinanceYear, remiitanceRefNo);
	}

	@Override
	@Transactional
	public List<ViewRemiitanceInfo> fetchRemittanceDetailsFileUpload(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo,
			BigDecimal bankId) {
		 
		return cancelReissueDao.fetchRemittanceDetailsFileUpload(appCountryId,companyId,documentCode,documentFinanceYear,documentNo,bankId);
	}

	@Override
	@Transactional(rollbackFor = AMGException.class)
	public void callCancelReIssueProcedure(HashMap<String, BigDecimal> inputMap) throws AMGException{
		
		cancelReissueDao.callCancelReIssueProcedure(inputMap);
	}

	@Override
	@Transactional
	public List<RemittanceApplication> remittanceApplicationList(BigDecimal remittanceAppFinYear , BigDecimal remittanceAppDoc) {
		return cancelReissueDao.remittanceApplicationList(remittanceAppFinYear , remittanceAppDoc);
	}

	@Override
	@Transactional
	public List<AdditionalInstructionData> remittanceAppAdditionalDataList(BigDecimal remittanceAppId) {
		return cancelReissueDao.remittanceAppAdditionalDataList(remittanceAppId);
	}

	@Override
	@Transactional
	public List<RemitApplAml> remittanceAppAmlList(BigDecimal remittanceAppId) {
		return cancelReissueDao.remittanceAppAmlList(remittanceAppId);
	}

	@Override
	@Transactional
	public List<RemittanceAppBenificiary> remittanceApplicationBeneficiaryList(BigDecimal remittanceAppId) {
		return cancelReissueDao.remittanceApplicationBeneficiaryList(remittanceAppId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveRemittanceCancel(HashMap<String, Object> saveMapInfo)
			throws AMGException {
		cancelReissueDao.saveRemittanceCancel(saveMapInfo);
	}

	@Override
	@Transactional
	public String getFutherInstruction(BigDecimal remittanceTransactionId)
			throws AMGException {
		return cancelReissueDao.getFutherInstruction(remittanceTransactionId);
		
	}

	@Override
	@Transactional
	public void deleteApplTrx(BigDecimal pkRemittanceApplicationId) {
		cancelReissueDao.deleteApplTrx(pkRemittanceApplicationId);
		
	}

	@Override
	@Transactional
	public void deleteApplAMl(BigDecimal pkRemitApplAmlId) {
		cancelReissueDao.deleteApplAMl(pkRemitApplAmlId);
		
	}

	@Override
	@Transactional
	public void deleteApplBene(BigDecimal pkRemittanceAppBenificiaryId) {
		cancelReissueDao.deleteApplBene(pkRemittanceAppBenificiaryId);
		
	}

	@Override
	@Transactional
	public void deleteApplAddlData(BigDecimal pkAdditionalInstructionDataId) {
		cancelReissueDao.deleteApplAddlData(pkAdditionalInstructionDataId);
		
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public  BigDecimal procedureCallForSave(HashMap<String, String> saveMapInfo)throws AMGException {
		return cancelReissueDao.procedureCallForSave(saveMapInfo);
		
	}

	@Override
	@Transactional
	public List<ViewRepushBankTrnxList> getBankRejectedTrnxListFromView(HashMap<String, String> inputParametrs) {
		return cancelReissueDao.getBankRejectedTrnxListFromView(inputParametrs);
	}

	@Override
	@Transactional
	public Boolean updateBankRejectedTrnxList(List<RepushBankTrnxDataTable> list, HashMap<String, String> inputParametrs) {
		return cancelReissueDao.updateBankRejectedTrnxList(list, inputParametrs);
	}
}
