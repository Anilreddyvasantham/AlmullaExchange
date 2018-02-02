package com.amg.exchange.registration.serviceimpl;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.OwsSchedule;
import com.amg.exchange.registration.dao.IOwsDao;
import com.amg.exchange.registration.service.IOwService;

@Service("owsservice")
public class OwsServiceImpl implements IOwService  {
	

	
	@Autowired
	IOwsDao owsservice;

	@Override
	@Transactional
	public void save(OwsSchedule owsSchedule) {
		owsservice.save(owsSchedule);
		
	}

	@Override
	@Transactional
	public OwsSchedule populateData(BigDecimal countryId, BigDecimal bankId, BigDecimal appcountryId) {
		return owsservice.populateData(countryId,bankId,appcountryId);
	}

	

}