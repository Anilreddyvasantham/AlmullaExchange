package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.ICurrencyDao;
import com.amg.exchange.common.model.FimsCurmas;
import com.amg.exchange.common.service.ICurrencyService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.viewModel.CurrencyCountryLevelView;
import com.amg.exchange.treasury.viewModel.CurrencyGLLevelView;
import com.amg.exchange.util.AMGException;
@Service
public class CurrencyServiceImpl implements ICurrencyService {
	
	@Autowired
	ICurrencyDao icurrencydao;

	@Override
	@Transactional
	public void saveOrUpdate(CurrencyMaster currencyMaster) {
		icurrencydao.saveOrUpdate(currencyMaster);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> getAllRecords(String currencyCode) {
		return icurrencydao.getAllRecords(currencyCode);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> getAllUnApprovedRecords() {
		return icurrencydao.getAllUnApprovedRecords();
	}
	
	@Override
	@Transactional
	public void deleteRecordFromDB(BigDecimal currencyMasterPk)
	{
		icurrencydao.deleteRecordFromDB(currencyMasterPk);
	}

	@Override
	@Transactional
	public List<String> getCurrencyCodeFromDB(String query) {
		return icurrencydao.getCurrencyCodeFromDB(query);
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal currencyMasterPk,String userName,BigDecimal currencyOthInfoPk) {
		return icurrencydao.approveRecord(currencyMasterPk,userName,currencyOthInfoPk);
	}

	@Override
	@Transactional
	public void saveOrUpdate(CurrencyOtherInformation currencyOtherInfo) {
		 icurrencydao.saveOrUpdate(currencyOtherInfo);
	}

	@Override
	@Transactional
	public void delteRecordFromOtherInfo(BigDecimal currencyOtherInformationPk) {
		icurrencydao.delteRecordFromOtherInfo(currencyOtherInformationPk);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> getRecordFromDB(String currencyCode) {
		return icurrencydao.getRecordFromDB(currencyCode);
	}

	@Override
	@Transactional
	public List<CurrencyMaster> getRecordToCheckDuplicate(String currencyCode) {
		return icurrencydao.getRecordToCheckDuplicate(currencyCode);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> search(String currencyCode) {
		return icurrencydao.search(currencyCode);
	}

	@Override
	@Transactional
	public void saveDeactiveRec(BigDecimal currencyMasterPk, String remarks,String userName,BigDecimal currencyOtherInformationPk) {
	  icurrencydao.saveDeactiveRec(currencyMasterPk,remarks,userName,currencyOtherInformationPk);
	}

	@Override
	@Transactional
	public List<CurrencyGLLevelView> fetchAllCurrencyGLRecords(String currencyCode) {
		return icurrencydao.fetchAllCurrencyGLRecords(currencyCode);
	}

	@Override
	@Transactional
	public List<CurrencyCountryLevelView> fetchAllCurrencyCountryLevelRecords(String currencyCode) {
		return icurrencydao.fetchAllCurrencyCountryLevelRecords(currencyCode);
	}

	@Override
	@Transactional
	public List<FimsCurmas> getFimsCurrencyCode(String fimsCurCode) {
	 
		return icurrencydao.getFimsCurrencyCode(fimsCurCode);
	}

	@Override
	@Transactional
	public HashMap<String, String> callPopulateCurmasProcedure(
			HashMap<String, String> inputValues)
			throws AMGException {
 
		return icurrencydao.callPopulateCurmasProcedure(inputValues);
	}
	
	@Override
	@Transactional
	public List<CurrencyMaster> getCurrencyList() {
	 
		return icurrencydao.getCurrencyList();
	}
	
	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId,BigDecimal languageId) {
	 
		return icurrencydao.getCountryName(countryId,languageId);
	}

	@Override
	@Transactional
	public List<CurrencyOtherInformation> searchByCurrencyId(BigDecimal currencyId) {
		return icurrencydao.searchByCurrencyId(currencyId);
	}
	 
}
