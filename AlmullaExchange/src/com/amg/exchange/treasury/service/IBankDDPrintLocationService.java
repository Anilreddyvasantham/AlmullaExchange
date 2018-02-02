package com.amg.exchange.treasury.service;

import java.util.List;

import com.amg.exchange.treasury.model.AgentMaster;
import com.amg.exchange.treasury.model.BankDdPrintLoc;

public interface IBankDDPrintLocationService<T> {

 public	void saveOrUpdateBankDdPrintLoc(BankDdPrintLoc bankDdPrintLoc);
 public List<AgentMaster> getAgentMasterList();
}
