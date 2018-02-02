package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.bean.CustomerAlmTrasactionCheckProcedure;

public interface ICustomerAlmTrasactionCheckDao<T> {
	
	public CustomerAlmTrasactionCheckProcedure getCustomerAlmTrasactionCheckService(BigDecimal CountryId,BigDecimal CustomerNo);

}
