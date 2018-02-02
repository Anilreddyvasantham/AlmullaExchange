package com.amg.exchange.highvalue.client.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.highvalue.client.model.HighValueClient;
import com.amg.exchange.highvalue.client.model.HighValueTransDetailView;
import com.amg.exchange.highvalue.client.model.HighValueTransView;
import com.amg.exchange.registration.model.Employee;

public interface IHighValueClientDao {

	  public List<Employee> toFetchBasedOnLocationFromEmployee(BigDecimal locationId);

	  public List<HighValueTransView> toFetchAllDetilsBasedOnLocationId(BigDecimal locationId);

	  public void save(HighValueClient highValueClient);

	  public BigDecimal toFetchHighValuePkBasedOnCustomerReferenceNo(BigDecimal customerReferenceNo);

	  public void saveOrUpdate(BigDecimal highValueClientPk,Date visitDate,String visitBy);

	  public String toFetchBasedOnIdTogetName(BigDecimal visitBy);
	  
	  /*Scheduled Customer Visit Service Statred*/
	  public List<HighValueTransDetailView> toFetchAllDetailsFromView(BigDecimal locationId, Date visitDate, BigDecimal countryId);

	  public String toFetchnationalityNameBasedOnNationalityId(BigDecimal nationality);


}
