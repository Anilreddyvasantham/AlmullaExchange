package com.amg.exchange.treasury.dao;

import java.util.List;

import com.amg.exchange.treasury.model.AgentMaster;
import com.amg.exchange.treasury.model.BankDdPrintLoc;

public interface IBankDDPrintLocationDao<T> {

   public void saveOrUpdateBankDdPrintLoc(BankDdPrintLoc bankDdPrintLoc);
   public List<AgentMaster> getAgentMasterList();
}
