package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IRoutingSetUpDao;
import com.amg.exchange.treasury.model.RoutingSetupMaster;
import com.amg.exchange.treasury.service.IRoutingSetUpService;

@SuppressWarnings("serial")
@Service("routingSetUpServiceImpl")
public class RoutingSetUpServiceImpl<T> implements IRoutingSetUpService<T>, Serializable  {

	@Autowired
	IRoutingSetUpDao<T> iroutingSetUpDao;
	
	@Transactional
	@Override
	public void save(RoutingSetupMaster routingSetupMaster) {
		iroutingSetUpDao.save(routingSetupMaster);
	}

}
