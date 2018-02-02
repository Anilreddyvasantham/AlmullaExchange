package com.amg.exchange.treasury.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.DeliveryModeDao;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;

@Service
public class DeliveryModeServiceImpl implements DeliveryModeService,Serializable {

	@Autowired
	DeliveryModeDao deliveryModeDao;
	
	@Override
	@Transactional
	public void save(DeliveryMode deliverymode) {
	 
		deliveryModeDao.save(deliverymode);

	}
	
	@Override
	@Transactional
	public List<DeliveryMode> getDeliveryMode(String deliveryMode){
		return deliveryModeDao.getDeliveryMode(deliveryMode);
	
	}
	
	@Override
	@Transactional
	public void saveRecord(DeliveryModeDesc deliveryModeDesc){
		
		deliveryModeDao.saveRecord(deliveryModeDesc);
	}
	/*added to populate the DB Values added @Koti 20/02/2015**/ 
	@Override
	@Transactional
	public List<DeliveryModeDesc> getDeliveryDescriptionList(
			BigDecimal deliveryModeId) {
		
		return deliveryModeDao.getDeliveryDescriptionList(deliveryModeId);
	}

	@Override
	@Transactional
	public List<String> getAllData(String query) {
		return deliveryModeDao.getAllData(query);
	}
	/*ended to populate the DB Values added @Koti 20/02/2015**/ 
	@Override
	@Transactional
	public List<Object> getDeliveryDescriptionList(){
		return deliveryModeDao.getDeliveryDescriptionList();
	}
	@Override
	@Transactional
	public List<DeliveryMode> getDeliveryModeList(BigDecimal deliveryModeId){
		return deliveryModeDao. getDeliveryModeList(deliveryModeId);
		
	}

	@Override
	@Transactional
	public List<DeliveryMode> getDelivertylist() {
		// TODO Auto-generated method stub
		return deliveryModeDao.getDeliverylist();
	}

	@Override
	@Transactional
	public String getEngDelivery(BigDecimal engId) {
		// TODO Auto-generated method stub
		return deliveryModeDao.getDeliveryDesc(engId);
	}

	@Override
	@Transactional
	public String getArbDelivery(BigDecimal arbId) {
		// TODO Auto-generated method stub
		return deliveryModeDao.getArbDelivery(arbId);
	}

	@Override
	@Transactional
	public BigDecimal getDeliveryPk(String englang) {
		// TODO Auto-generated method stub
		return deliveryModeDao.getDeliveryPk(englang);
	}

	@Override
    @Transactional
	public BigDecimal getDeliveryarbPk(String arblang) {
		// TODO Auto-generated method stub
		return deliveryModeDao.getDeliveryarbPk(arblang);
	}

	@Override
	@Transactional
	public List<DeliveryMode> getDeliveryNlist() {
		// TODO Auto-generated method stub
		return deliveryModeDao.getDeliveryNlist();
	}

	@Override
	@Transactional
	public String getCreatedName() {
		// TODO Auto-generated method stub
		return deliveryModeDao.getCreatedBy();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveDelivery(DeliveryMode deliverymode, DeliveryModeDesc desc,DeliveryModeDesc desc2) {
		deliveryModeDao.saveDelivery(deliverymode,desc,desc2);	
	}

	@Override
	@Transactional
	public List<DeliveryModeDesc> getAllDescList(BigDecimal deliveryModeId) {
		return deliveryModeDao.getAllDescList(deliveryModeId);
	}

	@Override
	@Transactional
	public List<DeliveryMode> getDeliveryApprovel() {
				return deliveryModeDao.getDeliveryApprovel();
	}

	@Override
	@Transactional
	public void deleteRecordPermentily(BigDecimal deliveryPk,BigDecimal deliveryEnglishPk, BigDecimal deliveryLocalPk) {
		deliveryModeDao.deleteRecordPermentily(deliveryPk,deliveryEnglishPk,deliveryLocalPk);
		
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal deliveryPk, String userName) {
		 
		return deliveryModeDao.approveRecord(deliveryPk,userName);
	}

	@Override
	@Transactional
	public String getDeliveryDesc(BigDecimal deliveryModeId , BigDecimal languageId) {
		return deliveryModeDao.getDeliveryDesc(deliveryModeId , languageId);
	}

	  @Override
	  @Transactional
	  public void activeRecordToPendingForApproval(BigDecimal deliveryPk, String userName) {
		    deliveryModeDao.activeRecordToPendingForApproval(deliveryPk,userName);
	  }

	  @Override
	  @Transactional
	  public void upDateActiveRecordtoDeActive(BigDecimal deliveryPk, String remarks, String userName) {
		    deliveryModeDao.upDateActiveRecordtoDeActive(deliveryPk,remarks,userName); 
	  }
}
