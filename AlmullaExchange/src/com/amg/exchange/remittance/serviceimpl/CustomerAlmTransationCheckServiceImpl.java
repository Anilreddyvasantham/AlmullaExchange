package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.bean.CustomerAlmTrasactionCheckProcedure;
import com.amg.exchange.remittance.dao.ICustomerAlmTrasactionCheckDao;
import com.amg.exchange.remittance.service.ICustomerAlmTransationCheckService;

@Service("customerAlmTransationCheckServiceImpl")
public class CustomerAlmTransationCheckServiceImpl<T> implements ICustomerAlmTransationCheckService<T> {

	@Autowired
	ICustomerAlmTrasactionCheckDao<T> icustomerAlmTrasactionCheckDao;
	
	@Override
	@Transactional
	public CustomerAlmTrasactionCheckProcedure getCustomerAlmTrasactionCheckService(BigDecimal CountryId,
			BigDecimal CustomerNo) {
		// TODO Auto-generated method stub
		return icustomerAlmTrasactionCheckDao.getCustomerAlmTrasactionCheckService(CountryId, CustomerNo);
	}

}
