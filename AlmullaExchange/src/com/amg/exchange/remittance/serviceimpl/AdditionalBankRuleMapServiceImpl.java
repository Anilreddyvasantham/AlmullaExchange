package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.dao.IAdditionalBankRuleMapDao;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;

@Service
public class AdditionalBankRuleMapServiceImpl implements
IAdditionalBankRuleMapService {
	@Autowired
	IAdditionalBankRuleMapDao additionalBankRuleMapDao;

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void save(AdditionalBankRuleMap additionalBankRuleMap) throws Exception{
		additionalBankRuleMapDao.save(additionalBankRuleMap);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getAllFlexField() {
		return additionalBankRuleMapDao.getAllFlexField();
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getAdditionalFlexField(BigDecimal countryId) {

		return additionalBankRuleMapDao.getAdditionalFlexField(countryId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForView() {

		return additionalBankRuleMapDao.getAdditionBankRuleMapRecordsForView();
	}
	//nag changed
	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForView(BigDecimal countryId,String flexifield) {

		return additionalBankRuleMapDao.getAdditionBankAlmullaCodeRecordsForView(countryId,flexifield);
	}


	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForView(BigDecimal countryId,String flexifieild,BigDecimal bankId)
	{

		return additionalBankRuleMapDao.getAdditionBankRuleRecordsForView(countryId,flexifieild,bankId);
	}

	//added by kani for Enquiry Screen begin

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> getAdditionBankRuleMapRecordsForEnquiry() {

		return additionalBankRuleMapDao.getAdditionBankRuleMapRecordsForEnquiry();
	}

	//public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForEnquiry();


	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> getAdditionBankAlmullaCodeRecordsForEnquiry() {

		return additionalBankRuleMapDao.getAdditionBankAlmullaCodeRecordsForEnquiry();
	}



	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getAdditionBankRuleRecordsForEnquiry() {

		return additionalBankRuleMapDao.getAdditionBankRuleRecordsForEnquiry();
	}

	@Override
	@Transactional
	public void activateBankRuleMapRecord(BigDecimal bankRuleMapPk,String userName){
		additionalBankRuleMapDao.activateBankRuleMapRecord(bankRuleMapPk,userName);
	}

	@Override
	@Transactional
	public void deleteBankRuleMapRecord(BigDecimal bankRuleMapPk){
		additionalBankRuleMapDao.deleteBankRuleMapRecord(bankRuleMapPk);
	}

	@Override
	@Transactional
	public void remarkBankRuleMapRecord(BigDecimal bankRuleMapPk,String remarkedText,String userName){
		additionalBankRuleMapDao.remarkBankRuleMapRecord(bankRuleMapPk,remarkedText,userName);
	}


	@Override
	@Transactional
	public void activateBankRuleAlmullaCode(BigDecimal bankRuleMapPk,String userName){
		additionalBankRuleMapDao.activateBankRuleAlmullaCode(bankRuleMapPk,userName);
	}

	@Override
	@Transactional
	public void deleteBankRuleAlmullaCode(BigDecimal bankRuleMapPk){
		additionalBankRuleMapDao.deleteBankRuleAlmullaCode(bankRuleMapPk);
	}

	@Override
	@Transactional
	public void remarkBankRuleAlmullaCode(BigDecimal bankRuleMapPk,String remarkedText,String userName){
		additionalBankRuleMapDao.remarkBankRuleAlmullaCode(bankRuleMapPk,remarkedText,userName);
	}


	@Override
	@Transactional
	public void activateBankRule(BigDecimal bankRuleMapPk,String userName){
		additionalBankRuleMapDao.activateBankRule(bankRuleMapPk,userName);
	}

	@Override
	@Transactional
	public void deleteBankRule(BigDecimal bankRuleMapPk){
		additionalBankRuleMapDao.deleteBankRule(bankRuleMapPk);
	}

	@Override
	@Transactional
	public void remarkBankRule(BigDecimal bankRuleMapPk,String remarkedText,String userName){
		additionalBankRuleMapDao.remarkBankRule(bankRuleMapPk,remarkedText,userName);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldID,String flexFieldName,BigDecimal orderNo){
		return additionalBankRuleMapDao.duplicateCheckInDBBankMap(countryId,bFlexFieldID,flexFieldID,flexFieldName,orderNo);
	}
	
	@Override
	@Transactional
	public List<AdditionalBankRuleMap> duplicateCheckInDBBankMap(BigDecimal countryId,String bFlexFieldID,String flexFieldName,BigDecimal orderNo){
		return additionalBankRuleMapDao.duplicateCheckInDBBankMap(countryId,bFlexFieldID,flexFieldName,orderNo);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAmiec> duplicateCheckInDBBankAlmullaCode(BigDecimal countryId,String flexFieldID,String amiecCode,String amiecDescription){
		return additionalBankRuleMapDao.duplicateCheckInDBBankAlmullaCode(countryId,flexFieldID,amiecCode,amiecDescription);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> duplicateCheckInDBBankRule(BigDecimal countryId,String flexFieldID,BigDecimal bankId,String additionalData,String additionalDataDesc){
		return additionalBankRuleMapDao.duplicateCheckInDBBankRule(countryId,flexFieldID,bankId,additionalData,additionalDataDesc);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getCountryList(BigDecimal languageId) {
		return additionalBankRuleMapDao.getCountryList(languageId);
	}

	@Override
	@Transactional
	public List<BankMasterDTO> getBanlList(BigDecimal countryId,String flexField) {
		return additionalBankRuleMapDao.getBanlList(countryId, flexField);
	}

	@Override
	@Transactional
	public List<AdditionalBankDetailsView> getAmiecDetails(BigDecimal countryId, String flexField) {
		return additionalBankRuleMapDao.getAmiecDetails(countryId, flexField);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getBankDetails(BigDecimal bankId) {
		return additionalBankRuleMapDao.getBankDetails(bankId);
	}

	@Override
	@Transactional
	public String getBankCode(String bankDesc) {
		return additionalBankRuleMapDao.getBankCode(bankDesc);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getAllBankDetails() {
		return additionalBankRuleMapDao.getAllBankDetails();
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping> fetchDataFromAmiecTable(BigDecimal countryId, String flexField, String amiecCode,BigDecimal bankId) {

		return additionalBankRuleMapDao.fetchDataFromAmiecTable(countryId, flexField, amiecCode, bankId);
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping>   checkRecordAvailableInAmiecAndBankMappingTable(
			BigDecimal countryId, String flexiField, String amiecCode, String amiecDesc,BigDecimal bankId){

		return additionalBankRuleMapDao.checkRecordAvailableInAmiecAndBankMappingTable(countryId, flexiField, amiecCode, amiecDesc, bankId);
	}

	@Override
	@Transactional
	public void updateRecord(List<AmiecAndBankMapping> amiecAndBankMappingUpdateList,String userName){
		additionalBankRuleMapDao.updateRecord(amiecAndBankMappingUpdateList,userName);
	}

	@Override
	@Transactional
	public void saveRecord(List<AmiecAndBankMapping> amiecAndBankMappingList){
		additionalBankRuleMapDao.saveRecord(amiecAndBankMappingList);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> toFetchAdditionBankRuleMapRecordsForView(BigDecimal countryId) {
		return additionalBankRuleMapDao.toFetchAdditionBankRuleMapRecordsForView(countryId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleMap> tofetchAll(BigDecimal countryId) {
		return additionalBankRuleMapDao.tofetchAll(countryId);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleFlexFieldView> tofetchAllFlexFieldsFromView() {
		return additionalBankRuleMapDao.tofetchAllFlexFieldsFromView();
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> getBankDetailsAndDescription(BigDecimal countryId,BigDecimal bankId, String flexiField) {
		return additionalBankRuleMapDao.getBankDetailsAndDescription(countryId, bankId, flexiField);
	}

	@Override
	@Transactional
	public List<AmiecAndBankMapping> fetchAllDataFromAmiecTableByCountryFlexField(BigDecimal countryId, String flexField) {
		return additionalBankRuleMapDao.fetchAllDataFromAmiecTableByCountryFlexField(countryId, flexField);
	}

	@Override
	@Transactional
	public List<AdditionalBankRuleAddData> fetchAllDataFromAdditionalBankRule(BigDecimal countryId, String flexiField) {
		return additionalBankRuleMapDao.fetchAllDataFromAdditionalBankRule(countryId, flexiField);
	}



}
