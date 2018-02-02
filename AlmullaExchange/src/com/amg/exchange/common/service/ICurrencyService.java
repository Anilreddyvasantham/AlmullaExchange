package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.amg.exchange.common.model.FimsCurmas;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.viewModel.CurrencyCountryLevelView;
import com.amg.exchange.treasury.viewModel.CurrencyGLLevelView;
import com.amg.exchange.util.AMGException;

public interface ICurrencyService {
	
	public void saveOrUpdate(CurrencyMaster currencyMaster);
	public String approveRecord(BigDecimal currencyMasterPk,String userName,BigDecimal currencyOthInfoPk);
	public List<CurrencyOtherInformation> getAllRecords(String currencyCode);
	public List<CurrencyOtherInformation> search(String currencyCode);
	public List<CurrencyOtherInformation> getAllUnApprovedRecords();
	public void deleteRecordFromDB(BigDecimal currencyMasterPk);
	public void delteRecordFromOtherInfo(BigDecimal currencyOtherInformationPk);
	public List<CurrencyOtherInformation>  getRecordFromDB(String currencyCode);
	public List<CurrencyMaster> getRecordToCheckDuplicate(String currencyCode);
	public List<String>  getCurrencyCodeFromDB(String query);
	public void saveOrUpdate(CurrencyOtherInformation currencyOtherInfo);
	public void saveDeactiveRec(BigDecimal currencyMasterPk, String remarks,String userName,BigDecimal currencyOtherInformationPk);
	public List<CurrencyGLLevelView> fetchAllCurrencyGLRecords(String currencyCode);
	public List<CurrencyCountryLevelView> fetchAllCurrencyCountryLevelRecords(String currencyCode);
	public List<FimsCurmas> getFimsCurrencyCode(String fimsCurCode);
	public HashMap<String, String> callPopulateCurmasProcedure(HashMap<String, String> inputValues)throws AMGException;
	public List<CurrencyMaster> getCurrencyList();
	public String getCountryName(BigDecimal countryId,BigDecimal languageId);
	public List<CurrencyOtherInformation> searchByCurrencyId(BigDecimal currencyId);
	
}
