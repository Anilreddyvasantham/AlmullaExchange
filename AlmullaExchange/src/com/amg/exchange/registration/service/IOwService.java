package com.amg.exchange.registration.service;

import java.math.BigDecimal;

import com.amg.exchange.common.model.OwsSchedule;

public interface IOwService {
	void save(OwsSchedule owsSchedule);

	OwsSchedule populateData(BigDecimal countryId, BigDecimal bankId, BigDecimal countryId2);
}
