/**
 * 
 */
package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IDistrictMasterDao;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.ICommonService;
import com.amg.exchange.common.service.IDistrictMasterService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.ServiceMaster;

/**
 * @author Subramaniam
 *
 */
@SuppressWarnings("serial")
@Service("districtMasterServiceImpl")
public class DistrictMasterServiceImpl<T> implements java.io.Serializable, ICommonService<T>, IDistrictMasterService<T> {

	/**
	 * 
	 */
	@Autowired
	IDistrictMasterDao districtMasterDao;
	
	
	
	public DistrictMasterServiceImpl() {
		// TODO Auto-generated constructor stub
	}



	public IDistrictMasterDao<T> getDistrictMasterDao() {
		return districtMasterDao;
	}



	public void setDistrictMasterDao(IDistrictMasterDao<T> districtMasterDao) {
		this.districtMasterDao = districtMasterDao;
	}
	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(T entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public void saveRecord(DistrictMaster districtMaster){
		getDistrictMasterDao().saveRecord(districtMaster);		
	}
	@Override
	@Transactional
	public void save(DistrictMasterDesc districtMasterDesc){
		getDistrictMasterDao().save(districtMasterDesc);
	}
	
	@Override
	@Transactional
	public String getStateName(BigDecimal languageId,BigDecimal stateId) {
		return getDistrictMasterDao().getStateName(languageId, stateId);
	}
	@Override
	@Transactional
	public void delete(BigDecimal districtId,BigDecimal districtMasterEngDescPk,BigDecimal districtMasterArbDescPk){
		
		getDistrictMasterDao().delete(districtId, districtMasterEngDescPk, districtMasterArbDescPk);
	}
	@Override
	@Transactional
	public List<DistrictMasterDesc> getDistrictList(){
		return getDistrictMasterDao().getDistrictList();
	}
	@Override
	@Transactional
	public void approveRecord(BigDecimal districtMasterId, String userName){
		getDistrictMasterDao().approveRecord(districtMasterId, userName);
	}
	
	@Override
	public void deleteRecordDesc(DistrictMasterDesc districtMasterDesc) {
		//getSession().delete(districtMasterDesc);

	}

	@Override
	public void deleteRecord(DistrictMaster districtMaster) {
	//	getSession().delete(districtMaster);

	}
	@Override
	@Transactional
	public List<DistrictMasterDesc> getDistrictList(BigDecimal districtId){
		return getDistrictMasterDao().getDistrictList(districtId);
	}
	@Override
	@Transactional
	public void modifyRecord(BigDecimal districtMasterId, String userName,String remarks){
		getDistrictMasterDao().modifyRecord(districtMasterId,userName,remarks);
	}
	@Override
	@Transactional
	public List<DistrictMasterDesc> getDistrictListBasedOnDistrictCode(String districtCode,String stateCode,String districtName) {
		
		return getDistrictMasterDao().getDistrictListBasedOnDistrictCode(districtCode,stateCode,districtName);
	}
	@Override
	@Transactional
	public List<StateMasterDesc> getStateName(String stateCode){
		return getDistrictMasterDao().getStateName(stateCode);
		
		
	}
	@Override
	@Transactional
	public Boolean isDistrictListBasedOnDistrictCode(String districtCode){
		
		return getDistrictMasterDao().isDistrictListBasedOnDistrictCode(districtCode);
	}
	@Override
	@Transactional
	public List<StateMasterDesc> getStateList(String stateId) {
		return getDistrictMasterDao().getStateList(stateId);
	}
	@Override
	@Transactional
	public List<StateMaster> getCountryFromState(BigDecimal stateId){
		return getDistrictMasterDao().getCountryFromState(stateId);
	}
	@Override
	@Transactional
	public List<DistrictMaster> viewMasterRecords(BigDecimal countryId){
		return getDistrictMasterDao().viewMasterRecords(countryId);
	}
	@Override
	@Transactional
	public List<DistrictMasterDesc> viewById(BigDecimal districtId) {
		return getDistrictMasterDao().viewById(districtId);
	}
	@Override
	@Transactional
	public void activateRecord(BigDecimal districtMaster, String userName){
		getDistrictMasterDao().activateRecord(districtMaster, userName);
	}
	@Override
	@Transactional
	public List<StateMaster> getStateMasterId(BigDecimal countryId,BigDecimal stateId){
		return getDistrictMasterDao().getStateMasterId(countryId,stateId);
	}
	
	@Override
	@Transactional
	public List<DistrictMasterDesc> checkEnglishDescription(String englishDistrictName,BigDecimal districtId) {
		// TODO Auto-generated method stub
		return getDistrictMasterDao().checkEnglishDescription(englishDistrictName,districtId);
	}
	
	@Override
	@Transactional
	public List<DistrictMasterDesc> checkLocalDescription(String localDistrictName,BigDecimal districtId) {
		// TODO Auto-generated method stub
		return getDistrictMasterDao().checkLocalDescription(localDistrictName,districtId);
	}

	@Override
	@Transactional
	public List<DistrictMaster> getDistrictBasedOnDistrictCode(String districtCode, BigDecimal stateId) {
		// TODO Auto-generated method stub
		return getDistrictMasterDao().getDistrictBasedOnDistrictCode(districtCode,stateId);
	}
	@Override
	@Transactional
	public List<DistrictMaster> viewMasterRecords(){
		return getDistrictMasterDao().viewMasterRecords();
	}
	
	@Override
	@Transactional
	public List<DistrictMaster> approveViewMasterRecords(BigDecimal stateId, String actvice) {
		// TODO Auto-generated method stub
		return getDistrictMasterDao().approveViewMasterRecords(stateId, actvice);
	}
	
	@Override
	@Transactional
	public List<StateMaster> getStateListBasedOnCountryId(BigDecimal countryId, String stateCode) {
		return getDistrictMasterDao().getStateListBasedOnCountryId(countryId, stateCode);
	}
	
	@Override
	@Transactional
	public List<StateMasterDesc> getStateNameBasedOnId(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return getDistrictMasterDao().getStateNameBasedOnId(stateId);
	}
	
	@Override
	@Transactional
	public String approveRecord(List<BigDecimal> districtId,String userName){
		return getDistrictMasterDao().approveRecord(districtId,userName);
	}

	@Override
	@Transactional
	public String getDistrictName(BigDecimal languageId, BigDecimal districtId) {
		return getDistrictMasterDao().getDistrictName(languageId, districtId);
	}

	@Override
	@Transactional
	public String getCityName(BigDecimal languageId, BigDecimal cityId) {
		return getDistrictMasterDao().getCityName(languageId, cityId);
	}
}
