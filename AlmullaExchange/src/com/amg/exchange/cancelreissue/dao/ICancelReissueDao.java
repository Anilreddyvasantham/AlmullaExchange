package com.amg.exchange.cancelreissue.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.cancelreissue.bean.RepushBankTrnxDataTable;
import com.amg.exchange.cancelreissue.model.RemittanceView;
import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.cancelreissue.model.ViewRepushBankTrnxList;
import com.amg.exchange.remittance.model.AdditionalInstructionData;
import com.amg.exchange.remittance.model.RemitApplAml;
import com.amg.exchange.remittance.model.RemittanceAppBenificiary;
import com.amg.exchange.remittance.model.RemittanceApplication;
import com.amg.exchange.util.AMGException;

public interface ICancelReissueDao<T> {
	public String getCompanyName(BigDecimal companyId,BigDecimal languageId);
	public List<ViewRemiitanceInfo> fetchRemiitanceDetails(BigDecimal appCountryId,BigDecimal companyId, BigDecimal documentCode,BigDecimal documentFinanceYear,BigDecimal remiitanceRefNo);
	public List<ViewRemiitanceInfo> fetchRemittanceDetailsFileUpload(
			BigDecimal appCountryId, BigDecimal companyId,BigDecimal documentCode,
			BigDecimal documentFinanceYear, BigDecimal documentNo,
			BigDecimal bankId);
	
	public void callCancelReIssueProcedure(HashMap <String,BigDecimal> inputMap)throws AMGException; 
	public List<RemittanceApplication> remittanceApplicationList(BigDecimal remittanceAppFinYear , BigDecimal remittanceAppDoc);
	public List<AdditionalInstructionData> remittanceAppAdditionalDataList(BigDecimal remittanceAppId);
	public List<RemitApplAml> remittanceAppAmlList(BigDecimal remittanceAppId);
	public List<RemittanceAppBenificiary> remittanceApplicationBeneficiaryList(BigDecimal remittanceAppId);
	public void saveRemittanceCancel(HashMap<String , Object> saveMapInfo) throws AMGException;
	public String getFutherInstruction(BigDecimal remittanceTransactionId) throws AMGException;
	
	public void deleteApplTrx(BigDecimal pkRemittanceApplicationId);
	public void deleteApplAMl(BigDecimal pkRemitApplAmlId);
	public void deleteApplBene(BigDecimal pkRemittanceAppBenificiaryId);
	public void deleteApplAddlData(BigDecimal pkAdditionalInstructionDataId);
	public  BigDecimal procedureCallForSave(HashMap<String, String> saveMapInfo)throws AMGException;

	/** Added by Rabil on 10/01/2017 */
	public List<ViewRepushBankTrnxList> getBankRejectedTrnxListFromView(HashMap<String, String> inputParametrs);
	public Boolean updateBankRejectedTrnxList(List<RepushBankTrnxDataTable> list,HashMap<String, String> inputParametrs);
	
}
