package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;

public interface IZoneMasterDao {

	public void saveRecord(Zone zones);

	public void saveRecordDesc(ZoneMasterDesc zoneMasterDesc);

	public List<String> getZoneListCode(String query);

	public List<Zone> getZoneList(String zoneCode);

	public List<ZoneMasterDesc> getZoneDescriptionList(BigDecimal zoneId);

	public List<ZoneMasterDesc> getAllZoneList();

	public List<ZoneMasterDesc> getZoneMasterApprovel();

	public String approveRecord(BigDecimal zoneIdpk, String userName);

	public void deleteRecordDesc(ZoneMasterDesc zoneMasterDesc);

	public void deleteRecord(Zone zone);

	public List<ZoneMasterDesc> getZoneMasterList(BigDecimal languageId);

	public List<Zone> getAllZoneListOnly();

	

	
}
