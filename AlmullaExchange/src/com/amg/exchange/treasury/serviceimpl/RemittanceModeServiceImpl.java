package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IRemittanceDao;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.treasury.service.IRemittanceModeService;

@Service("remittanceModeMasterService")
public class RemittanceModeServiceImpl implements IRemittanceModeService{
	
	@Autowired
	IRemittanceDao remittanceDao;

	@Override
	@Transactional
	public void saveRecord(RemittanceModeMaster remittanceModeMaster) {
		remittanceDao.saveRecord(remittanceModeMaster);
		
	}
	
	@Override
	@Transactional
	public void saveRecordForDesc(RemittanceModeDescription remittanceModeDescription) {
		remittanceDao.saveRecordForDesc(remittanceModeDescription);
		
	}

	@Override
	@Transactional
	public List<RemittanceModeMaster> getRemittanceCheck(String remittance) {
		return remittanceDao.getRemittanceCheck(remittance);
	}
	
	@Override
	@Transactional
	public List<String> getRemittancecodelist(String query,BigDecimal serviceId){
		return remittanceDao.getremittancecodelist(query,serviceId);
	}
	
	@Override
	@Transactional
	public List<RemittanceModeMaster> getRemittanceDesc(String remitId,BigDecimal serviceId) {
		// TODO Auto-generated method stub
		return remittanceDao.getremittanceDesc(remitId,serviceId);
	}
	
	@Override
	@Transactional
	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId) {
		// TODO Auto-generated method stub
		return remittanceDao.getRemittancDesc(remiId);
	}
	
	@Override
	@Transactional
	public String getRemittanceDesc(BigDecimal remitId) {
		// TODO Auto-generated method stub
		return remittanceDao.getRemittanceDesc(remitId);
	}
	
	@Override
	@Transactional
	public List<RemittanceModeDescription> getListRemittance() {
		// TODO Auto-generated method stub
		return remittanceDao.getListRemittance();
	}
	
	
	@Override
	@Transactional
	public String getRemittanceArbDesc(BigDecimal remitId)
	{
		return remittanceDao.getRemittanceArbDesc(remitId);
	}
	
	@Override
	@Transactional
	public List<Object> getRemittance() {
		// TODO Auto-generated method stub
		return remittanceDao.getlistRemittance();
	}
	
	@Override
	@Transactional
	public BigDecimal getRemittanceDescArbPk(String localArb) {
		// TODO Auto-generated method stub
		return remittanceDao.getRemittanceDescArbPk(localArb);
	}
	
	@Override
	@Transactional
	public BigDecimal getRemittanceDescEngPk(String localEng) {
		// TODO Auto-generated method stub
		return remittanceDao.getRemittanceDescEngPk(localEng);
	}
	
	@Override
	@Transactional
	public List<RemittanceModeMaster> getRemittanceModeApproval() {
		return remittanceDao.getRemittanceModeApproval();
	}
	
	@Override
	@Transactional
	public String getEngRemitDes(BigDecimal engId) {
		return remittanceDao.getEngRemitDes(engId);
	}
	
	@Override
	@Transactional
	public String getArbRemiDes(BigDecimal araId) {
		return remittanceDao.getArbRemiDes(araId);
	}
	
	@Override
	@Transactional
	public String getCreatedName() {
		return remittanceDao.getCreatedBy();
	}
	
	@Override
	@Transactional
	public List<RemittanceModeMaster> getRemitCheck(String remitance) {
		return remittanceDao.getRemitCheck(remitance);
	}
	
	@Override
	@Transactional
	public String approveRecord(BigDecimal remittancePk,String userName) {
		return remittanceDao.approveRecord(remittancePk,userName);
	}
	
	@Override
	@Transactional
	public List<ServiceMasterDesc>getServiceMastersActivateList(BigDecimal languageId) {
		return remittanceDao.getServiceMastersActivateList(languageId);
	}
	
	@Override
	@Transactional
	public void delete(RemittanceModeMaster remittanceModeMaster) {
		remittanceDao.delete(remittanceModeMaster);
	}
	
	@Override
	@Transactional
	public void delete(RemittanceModeDescription remittanceModeDescription) {
		remittanceDao.delete(remittanceModeDescription); 
	}
	
	@Override
	@Transactional
	public List<RemittanceModeDescription> getCheckRemittCode(String remittanceCode) {
		// TODO Auto-generated method stub
		return remittanceDao.getCheckRemittCode(remittanceCode);
	}

	@Override
	@Transactional
	public String getRemittanceDesc(BigDecimal remitId, BigDecimal languageId) {
		// TODO Auto-generated method stub
		return remittanceDao.getRemittanceDesc(remitId , languageId);
	}
	
	@Override
	@Transactional
	public List<RemittanceModeMaster> viewAllRemittanceMode() {
		// TODO Auto-generated method stub
		return remittanceDao.viewAllRemittanceMode();
	}
	
	@Override
	@Transactional
	public List<RemittanceModeDescription> viewRemittanceDescBasedOnRemittanceId(BigDecimal remittanceId) {
		// TODO Auto-generated method stub
		return remittanceDao.viewRemittanceDescBasedOnRemittanceId(remittanceId);
	}
	
}
