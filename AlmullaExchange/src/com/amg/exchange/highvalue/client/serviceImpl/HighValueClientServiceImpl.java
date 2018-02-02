package com.amg.exchange.highvalue.client.serviceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.highvalue.client.dao.IHighValueClientDao;
import com.amg.exchange.highvalue.client.model.HighValueClient;
import com.amg.exchange.highvalue.client.model.HighValueTransDetailView;
import com.amg.exchange.highvalue.client.model.HighValueTransView;
import com.amg.exchange.highvalue.client.service.IHighValueClientService;
import com.amg.exchange.registration.model.Employee;
@Service("highValueClientService")
public class HighValueClientServiceImpl implements IHighValueClientService {
	  
	  @Autowired
	  IHighValueClientDao highValueClientDao;

	  @Override
	  @Transactional
	  public List<Employee> toFetchBasedOnLocationFromEmployee(BigDecimal locationId) {
		    return highValueClientDao.toFetchBasedOnLocationFromEmployee(locationId);
	  }

	  @Override
	  @Transactional
	  public List<HighValueTransView> toFetchAllDetilsBasedOnLocationId(BigDecimal locationId) {
		    return highValueClientDao.toFetchAllDetilsBasedOnLocationId(locationId);
	  }

	  @Override
	  @Transactional
	  public void save(HighValueClient highValueClient) {
		    highValueClientDao.save(highValueClient);    
	  }

	  @Override
	  @Transactional
	  public BigDecimal toFetchHighValuePkBasedOnCustomerReferenceNo(BigDecimal customerReferenceNo) {
		    return highValueClientDao.toFetchHighValuePkBasedOnCustomerReferenceNo(customerReferenceNo);
	  }

	  @Override
	  @Transactional
	  public void saveOrUpdate(BigDecimal highValueClientPk,Date visitDate,String visitBy) {
		    highValueClientDao.saveOrUpdate(highValueClientPk,visitDate,visitBy);
	  }

	  @Override
	  @Transactional
	  public String toFetchBasedOnIdTogetName(BigDecimal visitBy) {
		    return highValueClientDao.toFetchBasedOnIdTogetName(visitBy);
	  }

	  /*Scheduled Customer Visit Service Statred*/
	  @Override
	  @Transactional
	  public List<HighValueTransDetailView> toFetchAllDetailsFromView(BigDecimal locationId, Date visitDate, BigDecimal countryId) {
		    return highValueClientDao.toFetchAllDetailsFromView(locationId,visitDate,countryId);
	  }

	  @Override
	  @Transactional
	  public String toFetchnationalityNameBasedOnNationalityId(BigDecimal nationality) {
		    return highValueClientDao.toFetchnationalityNameBasedOnNationalityId(nationality);
	  }

}
