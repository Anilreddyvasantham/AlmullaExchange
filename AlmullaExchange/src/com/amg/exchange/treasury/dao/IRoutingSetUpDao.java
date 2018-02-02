package com.amg.exchange.treasury.dao;

import com.amg.exchange.treasury.model.RoutingSetupMaster;

public interface IRoutingSetUpDao<T> {
	
	public void save(RoutingSetupMaster routingSetupMaster);

}
