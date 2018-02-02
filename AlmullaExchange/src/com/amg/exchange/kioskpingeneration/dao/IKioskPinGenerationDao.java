package com.amg.exchange.kioskpingeneration.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.util.AMGException;

public interface IKioskPinGenerationDao<T> {
	
	public List<Customer> getCustomerDetails(BigDecimal customerReference);
	public List<CustomerIdProof> getIdProofDetails(BigDecimal customerId);
	public List<String> callProcedureToGenerateKioskPin() throws AMGException;
	public void updateKioskPin(BigDecimal customerId, String kioskPin);

}
