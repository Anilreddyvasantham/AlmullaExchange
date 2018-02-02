package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.model.ServiceMasterDesc;

public interface IRemittanceDao {
	
	public List<RemittanceModeMaster> getRemittanceCheck(String remittance);
	
	public void saveRecord(RemittanceModeMaster remittanceModeMaster);
	
	public void saveRecordForDesc(RemittanceModeDescription remittanceModeDescription);

	public List<String> getremittancecodelist(String query, BigDecimal serviceId);

	public List<RemittanceModeMaster> getremittanceDesc(String remiId,BigDecimal serviceId);

	public List<RemittanceModeDescription> getRemittancDesc(BigDecimal remiId);

	public List<RemittanceModeDescription> getListRemittance();

	public String getRemittanceDesc(BigDecimal remitId);

	public String getRemittanceArbDesc(BigDecimal remitId);

	public BigDecimal getRemittanceDescArbPk(String localArb);

	public BigDecimal getRemittanceDescEngPk(String localEng);

	public List<Object> getlistRemittance();

	public List<RemittanceModeMaster> getRemittanceModeApproval();
	
	public String getEngRemitDes(BigDecimal engId);
	
	public String getArbRemiDes(BigDecimal araId);
	
	public String getCreatedBy();
	
	public List<RemittanceModeMaster> getRemitCheck(String remitance);

	public  String approveRecord(BigDecimal remittancePk,String userName);
	
	public List<ServiceMasterDesc> getServiceMastersActivateList(BigDecimal languageId);
	
	public void delete(RemittanceModeMaster remittanceModeMaster) ;
	
	public void delete(RemittanceModeDescription remittanceModeDescription) ;
	
	public List<RemittanceModeDescription> getCheckRemittCode(String remittanceCode);
	
	public String getRemittanceDesc(BigDecimal remitId , BigDecimal languageId);

	// Method for view by subramanian
	public List<RemittanceModeMaster> viewAllRemittanceMode();

	public List<RemittanceModeDescription> viewRemittanceDescBasedOnRemittanceId(BigDecimal remittanceId);
	// End by subramanian

}