package com.amg.exchange.common.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IBusinessComponentConfDao;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.service.IBusinessComponentConfService;
import com.amg.exchange.common.service.ICommonService;

@SuppressWarnings("serial")
@Service("businessComponentConfServiceImpl")
public class BusinessComponentConfServiceImpl <T> implements IBusinessComponentConfService<T>, ICommonService<T>, Serializable {
	
	@Autowired
	IBusinessComponentConfDao<T> businessComponentConfDao; 

	public IBusinessComponentConfDao<T> getBusinessComponentConfDao() {
		return businessComponentConfDao;
	}

	public void setBusinessComponentConfDao(
			IBusinessComponentConfDao<T> businessComponentConfDao) {
		this.businessComponentConfDao = businessComponentConfDao;
	}

	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CompanyMaster> getCompany(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateMaster> getState(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DistrictMaster> getDistrict(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return null;
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
	public void delete(T entity) {

		getBusinessComponentConfDao().delete(entity);
	}

	@Override
	@Transactional
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId) {
		
		return getBusinessComponentConfDao().getBusinessComponent(componentId);
	}

	@Override
	@Transactional
	public BizComponentConfDetail getBusinessComponent(BigDecimal componentId,
			BigDecimal applicationId, BigDecimal companyId, 
			BigDecimal countryId, BigDecimal pageId) { 
		
		return getBusinessComponentConfDao().getBusinessComponent(componentId, applicationId, companyId, countryId, pageId);
	}

	@Override
	@Transactional
	public List<BizComponentDataDesc> getBusinessComponentDropDownData(
			BigDecimal componentConfId, BigDecimal languageId) {
		
		return getBusinessComponentConfDao().getBusinessComponentDropDownData(componentConfId, languageId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(T entity) {  
		  
		getBusinessComponentConfDao().saveOrUpdate(entity);
	}

	@Override
	@Transactional	
	public List<Object[]> getBusinessComponentTree(BigDecimal componentId) {

		return getBusinessComponentConfDao().getBusinessComponentTree(componentId);
	}
 
	@Override
	@Transactional
	public List<BizComponentDataRef> getBusinessComponentDataRef(BigDecimal componentConfId, BigDecimal languageId) {

		return getBusinessComponentConfDao().getBusinessComponentDataRef(componentConfId, languageId);
	}

	@Override
	@Transactional
	public Map<String, BigDecimal> getBusinessComponentData(BigDecimal componentId, BigDecimal languageId) {

		return getBusinessComponentConfDao().getBusinessComponentData(componentId, languageId);
	}
}
