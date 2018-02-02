package com.amg.exchange.remittance.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.bean.CustomerAlmTrasactionCheckProcedure;

public interface ICustomerAlmTransationCheckService<T> {
	
	public CustomerAlmTrasactionCheckProcedure getCustomerAlmTrasactionCheckService(BigDecimal CountryId,BigDecimal CustomerNo);

}
