package com.amg.exchange.treasury.daoimpl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.treasury.dao.IBankDDPrintLocationDao;
import com.amg.exchange.treasury.model.AgentMaster;
import com.amg.exchange.treasury.model.BankDdPrintLoc;
@SuppressWarnings("serial")
@Repository
public class BankDDPrintLocationDaoImpl<T> extends CommonDaoImpl<T> implements IBankDDPrintLocationDao<T>, Serializable {

	@SuppressWarnings("unchecked")
	@Override
	public void saveOrUpdateBankDdPrintLoc(BankDdPrintLoc bankDdPrintLoc) {
		save( (T) bankDdPrintLoc );
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AgentMaster> getAgentMasterList() {
		DetachedCriteria detachedCriteria =DetachedCriteria.forClass(AgentMaster.class,"agent");
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<AgentMaster>)findAll(detachedCriteria);
	}
}
