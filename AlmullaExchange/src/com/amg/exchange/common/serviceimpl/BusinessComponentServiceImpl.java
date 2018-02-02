package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IBusinessComponentDao;
import com.amg.exchange.common.model.BizComponentConfDetail;
import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.BizComponentDataRef;
import com.amg.exchange.common.model.BusinessComponent;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.ComponentType;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.service.IBusinessComponentService;
import com.amg.exchange.common.service.ICommonService;

@Service("businessComponentServiceImpl")
public class BusinessComponentServiceImpl <T> implements IBusinessComponentService<T>, ICommonService<T> {
	
	@Autowired
	IBusinessComponentDao<T> businessComponentDao;

	public IBusinessComponentDao<T> getBusinessComponentDao() {
		return businessComponentDao;
	}

	public void setBusinessComponentDao(
			IBusinessComponentDao<T> businessComponentDao) {
		this.businessComponentDao = businessComponentDao;
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
	public void delete(T entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional
	public List<ComponentType> getComponentType() {
	
		return getBusinessComponentDao().getComponentType();
	}

	@Override
	@Transactional
	public void saveOrUpdate(T entity) {   
		
		getBusinessComponentDao().saveOrUpdate(entity);
	}

	@Override
	@Transactional 
	public List<BusinessComponent> getAllBusinessComponent() {

		return getBusinessComponentDao().getAllBusinessComponent();
	}

	@Override
	@Transactional
	public BizComponentConfDetail getBusinessComponent(String componentName) {

		return getBusinessComponentDao().getBusinessComponent(componentName);
	}
 
	@Override
	@Transactional
	public List<BizComponentDataDesc> getBusinessComponentDropDownData( BigDecimal componentConfId, BigDecimal languageId) {
		
		return getBusinessComponentDao().getBusinessComponentDropDownData(componentConfId, languageId);
	}

	@Override
	@Transactional
	public List<String> getAllBusinessComponent(String query) {
		
		return getBusinessComponentDao().getAllBusinessComponent(query);
	}

	@Override
	@Transactional
	public Map<BigDecimal, BizComponentDataRef> getAllComponentDataRef(BigDecimal componentConfId) {
	
		return getBusinessComponentDao().getAllComponentDataRef(componentConfId);
	}

	@Override
	@Transactional
	public List<BizComponentData> getBusinessComponentData(BigDecimal componentConfId) {
		 
		return getBusinessComponentDao().getBusinessComponentData(componentConfId);
	}	 
}
