package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IZoneMasterDao;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IZoneMasterService;

@Service("zoneMasterService")
@Transactional
public class ZoneMasterServiceImpl implements IZoneMasterService {

	@Autowired
	IZoneMasterDao zoneMasterDao;

	@Override
	public void saveRecord(Zone zones) {
		zoneMasterDao.saveRecord(zones);

	}

	@Override
	public void saveRecordDesc(ZoneMasterDesc zoneMasterDesc) {
		zoneMasterDao.saveRecordDesc(zoneMasterDesc);
	}

	@Override
	public List<String> getZoneListCode(String query) {

		return zoneMasterDao.getZoneListCode(query);
	}

	@Override
	public List<Zone> getZoneList(String zoneCode) {
		return zoneMasterDao.getZoneList(zoneCode);
	}

	@Override
	public List<ZoneMasterDesc> getZoneDescriptionList(BigDecimal zoneId) {
		return zoneMasterDao.getZoneDescriptionList(zoneId);
	}

	@Override
	public List<ZoneMasterDesc> getAllZoneList() {
		return zoneMasterDao.getAllZoneList();
	}

	@Override
	public List<ZoneMasterDesc> getZoneMasterApprovel() {
		return zoneMasterDao.getZoneMasterApprovel();
	}

	@Override
	public String approveRecord(BigDecimal zoneIdpk, String userName) {
		return zoneMasterDao.approveRecord(zoneIdpk, userName);

	}

	@Override
	public void deleteRecordDesc(ZoneMasterDesc zoneMasterDesc) {
		zoneMasterDao.deleteRecordDesc(zoneMasterDesc);

	}

	@Override
	public void deleteRecord(Zone zone) {
		zoneMasterDao.deleteRecord(zone);

	}

	@Override
	public List<ZoneMasterDesc> getZoneMasterList(BigDecimal languageId) {
		return zoneMasterDao.getZoneMasterList(languageId);
	}

	@Override
	public List<Zone> getAllZonesList() {
		return zoneMasterDao.getAllZoneListOnly();
	}

}
