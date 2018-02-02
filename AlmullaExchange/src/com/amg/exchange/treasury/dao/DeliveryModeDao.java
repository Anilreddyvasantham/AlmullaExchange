package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;

public interface DeliveryModeDao {
	
	public void save(DeliveryMode deliveryMode);
	
	public void saveRecord(DeliveryModeDesc deliveryModeDesc);
	
	public List<DeliveryMode> getDeliveryMode(String deliveryMode);

	public List<DeliveryModeDesc> getDeliveryDescriptionList(BigDecimal deliveryModeId);
	
	public List<String> getAllData(String query);
	
	public List<Object> getDeliveryDescriptionList();
	
	public List<DeliveryMode> getDeliveryModeList(BigDecimal deliveryModeId);
	
	public List<DeliveryMode> getDeliverylist();
	
	public String  getDeliveryDesc(BigDecimal engId);
	
	public String getArbDelivery(BigDecimal arbId);
	
	public BigDecimal getDeliveryPk(String englang);
	
	public BigDecimal getDeliveryarbPk(String arblang);
	
	public List<DeliveryMode> getDeliveryNlist();
	
	public String getCreatedBy();
	
	public void saveDelivery(DeliveryMode deliverymode, DeliveryModeDesc desc,DeliveryModeDesc desc2);
	
	public List<DeliveryModeDesc> getAllDescList(BigDecimal deliveryModeId);
	
	public List<DeliveryMode> getDeliveryApprovel();
	
	public String getDeliveryDesc(BigDecimal deliveryModeId , BigDecimal languageId);
	
	public void deleteRecordPermentily(BigDecimal deliveryPk,BigDecimal deliveryEnglishPk, BigDecimal deliveryLocalPk);
	
	public String approveRecord(BigDecimal deliveryPk, String userName);

	  public void activeRecordToPendingForApproval(BigDecimal deliveryPk, String userName);

	  public void upDateActiveRecordtoDeActive(BigDecimal deliveryPk, String remarks, String userName);
}
