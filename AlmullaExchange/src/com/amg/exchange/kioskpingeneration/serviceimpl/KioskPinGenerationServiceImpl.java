package com.amg.exchange.kioskpingeneration.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.kioskpingeneration.dao.IKioskPinGenerationDao;
import com.amg.exchange.kioskpingeneration.service.IKioskPinGenerationService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.util.AMGException;
@SuppressWarnings("serial")
@Service("kioskPinGenerationServiceImpl")
public class KioskPinGenerationServiceImpl<T> implements IKioskPinGenerationService<T>, Serializable{

	@Autowired
	IKioskPinGenerationDao<T> ikioskPinGenerationDao;

	@Override
	@Transactional
	public List<Customer> getCustomerDetails(BigDecimal customerReference) {
		return ikioskPinGenerationDao.getCustomerDetails(customerReference);
	}

	@Override
	@Transactional
	public List<CustomerIdProof> getIdProofDetails(BigDecimal customerId) {
		return ikioskPinGenerationDao.getIdProofDetails(customerId);
	}

	@Override
	@Transactional
	public List<String> callProcedureToGenerateKioskPin() throws AMGException {
		return ikioskPinGenerationDao.callProcedureToGenerateKioskPin();
	}

	@Override
	@Transactional
	public void updateKioskPin(BigDecimal customerId, String kioskPin) {
		ikioskPinGenerationDao.updateKioskPin(customerId, kioskPin);
	}
}
