package com.amg.exchange.registration.dao;

import java.math.BigDecimal;

import com.amg.exchange.common.model.OwsSchedule;


public interface IOwsDao {

	void save(OwsSchedule owsSchedule);

	OwsSchedule populateData(BigDecimal countryId, BigDecimal bankId, BigDecimal appcountryId);

}
