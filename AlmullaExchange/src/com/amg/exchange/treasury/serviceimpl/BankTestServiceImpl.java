/*package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IBankTestDao;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IBankTestService;


@SuppressWarnings("serial")
@Service("banktestservice")
public class BankTestServiceImpl<T> implements IBankTestService<T>, Serializable{

	
	@Autowired
	IBankTestDao<T> ibanktestdao;
	


	@Transactional
	@Override
	public List<BankApplicability> getBankListDB() {
		
		return ibanktestdao.fromBankListDB();
	}


    @Transactional
	@Override
	public List<BankApplicability> gettoBankListDB(BigDecimal bankapplId) {
		// TODO Auto-generated method stub
		return ibanktestdao.toBankListDB(bankapplId);
	}

    @Transactional
	@Override
	public String getCurrencyName(BigDecimal currencyName) {
		// TODO Auto-generated method stub
		return ibanktestdao.getCurrencyName(currencyName);
	}

}
*/