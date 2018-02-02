package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;

public interface DeliveryModeService {

	public void save(DeliveryMode deliverymode);
	
	public List<DeliveryMode> getDeliveryMode(String deliveryMode);
	
	public void saveRecord(DeliveryModeDesc deliveryModeDesc);
	
	public List<DeliveryModeDesc> getDeliveryDescriptionList(BigDecimal deliveryModeId);
	
	public List<String> getAllData(String query);

	public List<Object> getDeliveryDescriptionList();
	
	public List<DeliveryMode> getDeliveryModeList(BigDecimal deliveryModeId);

	public String getEngDelivery(BigDecimal engId);

	public String getArbDelivery(BigDecimal arbId);

	public BigDecimal getDeliveryPk(String englang);

	public BigDecimal getDeliveryarbPk(String arblang);

	public List<DeliveryMode> getDeliveryNlist();

	public String getCreatedName();

	public void saveDelivery(DeliveryMode deliverymode, DeliveryModeDesc desc,DeliveryModeDesc desc2);

	public List<DeliveryMode> getDelivertylist();
	
	public List<DeliveryModeDesc> getAllDescList(BigDecimal deliveryModeId);
	
	public List<DeliveryMode> getDeliveryApprovel();
	
	public String getDeliveryDesc(BigDecimal deliveryModeId , BigDecimal languageId);
	
	public void deleteRecordPermentily(BigDecimal deliveryPk,BigDecimal deliveryEnglishPk, BigDecimal deliveryLocalPk);
	
	public String approveRecord(BigDecimal deliveryPk,String userName);

	  public void activeRecordToPendingForApproval(BigDecimal deliveryPk, String userName);

	  public void upDateActiveRecordtoDeActive(BigDecimal deliveryPk, String remarks, String userName);


}
