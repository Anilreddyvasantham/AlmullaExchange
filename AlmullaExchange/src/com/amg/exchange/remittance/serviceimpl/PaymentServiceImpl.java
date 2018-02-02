package com.amg.exchange.remittance.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.PaymentModeDao;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.service.IPaymentService;

@Service
public class PaymentServiceImpl implements
		IPaymentService, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Autowired
	PaymentModeDao  paymentModeDao;

	@Override
	@Transactional
	public void save(PaymentMode  paymentMode) {
		paymentModeDao.save(paymentMode);

	}

	@Override
	@Transactional
	public void saveRecord(
			PaymentModeDesc  paymentModeDesc) {
		paymentModeDao.saveRecord(paymentModeDesc);

	}

	@Override
	@Transactional
	public List<PaymentMode> searchRecord(String paymentcode) {
		
		return paymentModeDao.searchRecord(paymentcode);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> paymentDescRec(BigDecimal paymentId) {
		// TODO Auto-generated method stub
		return paymentModeDao.paymentDescRec(paymentId);
	}

	@Override
	@Transactional
	public List<String> getPaymntcodelist(String qry) {
		// TODO Auto-generated method stub
		return paymentModeDao.getPaymntcodelist(qry);
	}

	@Override
	@Transactional
	public List<PaymentMode> getPayment() {
		// TODO Auto-generated method stub
		return paymentModeDao.getPayment();
	}

	@Override
	@Transactional
	public String getPaymentDesc(BigDecimal arbId) {
		// TODO Auto-generated method stub
		return paymentModeDao.getPaymentDesc(arbId);
	}

	@Override
	@Transactional
	public String getCreatedBy() {
		// TODO Auto-generated method stub
		return paymentModeDao.getCreatedBy();
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> viewRecord() {
	
		return paymentModeDao.viewRecord();
	}

	@Override
	@Transactional
	public BigDecimal getPaymentPk(String englang) {
		// TODO Auto-generated method stub
		return paymentModeDao.getPaymentPk(englang);
	}

	@Override
	@Transactional
	public BigDecimal getPaymentarbPk(String arblang) {
		// TODO Auto-generated method stub
		return paymentModeDao.getPaymentarbPk(arblang);
	}

	@Override
	@Transactional
	public List<PaymentMode> getPaymentMode() {
		// TODO Auto-generated method stub
		return paymentModeDao.getPaymentMode();
	}
	@Transactional
	@Override
	public String getArbPaymnetName(BigDecimal paymentModeId) {
		
		return paymentModeDao.getArbPaymnetName(paymentModeId);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> getListOfPayment() {
		// TODO Auto-generated method stub
		return paymentModeDao.getListOfPayment();
	}

	@Override
	@Transactional
	public List<PaymentMode> getPaymentCheck(String paymentcode) {
		return paymentModeDao.getPaymentCheck(paymentcode);
	}

	@Override
	@Transactional
	public String getPaymentDisc(BigDecimal engid) {
		return paymentModeDao.getPaymentDisc(engid);
	}

	@Override
	@Transactional
	public void delete(PaymentMode paymentMode) {
		paymentModeDao.delete(paymentMode);
		
	}

	@Override
	@Transactional
	public void delete(PaymentModeDesc paymentModeDesc) {
		paymentModeDao.delete(paymentModeDesc);
		
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> fetchPaymodeDesc(BigDecimal langId,String isActive) {
		// TODO Auto-generated method stub
		return paymentModeDao.fetchPaymodeDesc(langId,isActive);
	}

	@Override
	@Transactional
	public BigDecimal fetchPaymodeMasterId(String paymentModeDesc,BigDecimal langId) {
		// TODO Auto-generated method stub
		return paymentModeDao.fetchPaymodeMasterId(paymentModeDesc,langId);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> getRecordsFromDb(BigDecimal paymentModeId) {
		return paymentModeDao.getRecordsFromDb(paymentModeId);
	}

	@Override
	@Transactional
	public void deleteRecordPermrntely(BigDecimal dtpymPk,BigDecimal dtpymDescpken, BigDecimal dtpymDescpkarb) {
		paymentModeDao.deleteRecordPermrntely(dtpymPk,dtpymDescpken,dtpymDescpkarb);
		
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> getPaymentDescLangList(BigDecimal languageId) {
		return paymentModeDao.getPaymentDescLangList(languageId);
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal paymentModePk, String userName) {
		 
		return paymentModeDao.approveRecord(paymentModePk,userName);
	}

	@Override
	@Transactional
	public String paymentModeDescription(BigDecimal paymentId, BigDecimal langId) {
		  // TODO Auto-generated method stub
		  return paymentModeDao.paymentModeDescription(paymentId, langId);
	}

	@Override
	@Transactional
	public List<PaymentMode> getAllRecordsFrmPaymentMode() {
		// TODO Auto-generated method stub
		return paymentModeDao.getAllRecordsFrmPaymentMode();
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> getAllRecordsBasedOnPaymentModeId(
			BigDecimal paymentModeId) {
		 
		return paymentModeDao.getAllRecordsBasedOnPaymentModeId(paymentModeId);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> fetchPaymodeDescForStopPayment(
			BigDecimal langId, String isActive) {
		 
		return paymentModeDao.fetchPaymodeDescForStopPayment(langId,isActive);
	}

	@Override
	@Transactional
	public List<PaymentModeDesc> searchRecordByCode(String paymentcode) {
		return paymentModeDao.searchRecordByCode(paymentcode);
	}





}
