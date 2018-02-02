package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankDDPrintLocationDao;
import com.amg.exchange.treasury.model.AgentMaster;
import com.amg.exchange.treasury.model.BankDdPrintLoc;
import com.amg.exchange.treasury.service.IBankDDPrintLocationService;
@SuppressWarnings("serial")
@Service("bankDDPrintLocationServiceImpl")
public class BankDDPrintLocationServiceImpl<T> implements IBankDDPrintLocationService<T>, Serializable {
	
	@SuppressWarnings("rawtypes")
	@Autowired
	IBankDDPrintLocationDao bankDDPrintLocationDao;
    
	
	@SuppressWarnings("rawtypes")
	public IBankDDPrintLocationDao getBankDDPrintLocationDao() {
		return bankDDPrintLocationDao;
	}

	@SuppressWarnings("rawtypes")
	public void setBankDDPrintLocationDao(
			IBankDDPrintLocationDao bankDDPrintLocationDao) {
		this.bankDDPrintLocationDao = bankDDPrintLocationDao;
	}

    @Transactional
	@Override
	public void saveOrUpdateBankDdPrintLoc(BankDdPrintLoc bankDdPrintLoc) {
       getBankDDPrintLocationDao().saveOrUpdateBankDdPrintLoc(bankDdPrintLoc);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<AgentMaster> getAgentMasterList() {
		return getBankDDPrintLocationDao().getAgentMasterList();
	}

}
