package com.amg.exchange.registration.serviceimpl;

/**
 * "@author Arul JayaSingh
 */
	

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.bean.NomineeBean;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.registration.dao.INomineeDao;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.service.INomineeService;
import com.amg.exchange.treasury.model.Nominee;

/**
 * @author exim07
 *
 */
@Service("nomineeService")
@SuppressWarnings("rawtypes")
public class NomineeServiceImpl implements INomineeService,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public NomineeServiceImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	INomineeDao nomineeDao;
	
	/**
	 * @return the nomineeDao
	 */
	public INomineeDao getNomineeDao() {
		return this.nomineeDao;
	}

	/**
	 * @param nomineeDao the nomineeDao to set
	 */
	public void setNomineeDao(INomineeDao nomineeDao) {
		this.nomineeDao = nomineeDao;
	}

	

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getCity(java.math.BigDecimal)
	 */
	@Override
	public List<CityMaster> getCity(BigDecimal districtId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getCountry()
	 */
	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getState()
	 */
	@Override
	public List<StateMaster> getState() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getCompany(java.math.BigDecimal)
	 */
	@Override
	public List<CompanyMaster> getCompany(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getState(java.math.BigDecimal)
	 */
	@Override
	public List<StateMaster> getState(BigDecimal countryId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.IMutualService#getDistrict(java.math.BigDecimal)
	 */
	@Override
	public List<DistrictMaster> getDistrict(BigDecimal stateId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.INomineeService#getNomineeDetail(java.lang.String)
	 */
	@Override
	@Transactional
	public List<Customer> getNomineeDetail(String civilID) {
		
		return getNomineeDao().getNomineeDetail(civilID);
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.INomineeService#saveNomineeDetail(com.amg.exchange.model.Nominee)
	 */
	@Transactional
	@Override
	public void saveNomineeDetail(Nominee nominee) {
		getNomineeDao().saveNomineeDetail(nominee);
		
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.INomineeService#getNomineeList()
	 */
	@Transactional
	@Override
	public List<Nominee> getNomineeList(BigDecimal nominatorId) {
		
		return getNomineeDao().getNomineeList(nominatorId);
	}

	/* (non-Javadoc)
	 * @see com.amg.exchange.service.INomineeService#deleteNominee(com.amg.exchange.model.Nominee)
	 */
	@Transactional
	@Override
	public void deleteNominee(NomineeBean nominee) {
		getNomineeDao().deleteNominee(nominee);
		
	}

}
