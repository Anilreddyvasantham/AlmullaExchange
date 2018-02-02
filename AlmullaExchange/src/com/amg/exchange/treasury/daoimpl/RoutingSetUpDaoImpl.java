package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IRoutingSetUpDao;
import com.amg.exchange.treasury.model.RoutingSetupMaster;

@SuppressWarnings("serial")
@Repository
public class RoutingSetUpDaoImpl<T> extends CommonDaoImpl<T> implements IRoutingSetUpDao<T>, Serializable {

	@Override
	public void save(RoutingSetupMaster routingSetupMaster) {
		getSession().save(routingSetupMaster);
	}

}
