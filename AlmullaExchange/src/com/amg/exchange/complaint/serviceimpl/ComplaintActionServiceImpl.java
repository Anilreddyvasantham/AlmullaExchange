package com.amg.exchange.complaint.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.complaint.dao.IComplaintActionDao;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.service.IComplaintActionService;

@Service
public class ComplaintActionServiceImpl implements IComplaintActionService {

	@Autowired 
	IComplaintActionDao iComplaintActionDao;
	
	@Transactional
	public  List<ComplaintActionDTO> getAllRecords(){
		return iComplaintActionDao.getAllRecords();
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveRecords(ComplaintAction complaintAction,
			ComplaintActionDesc complaintDescEng,
			ComplaintActionDesc complaintDescArabic) {
		iComplaintActionDao.saveRecords(complaintAction, complaintDescEng, complaintDescArabic);
		
	}

	@Override
	@Transactional
	public List<String> getAutoComplete(String actionCode) {
		 
		return iComplaintActionDao.getAutoComplete(actionCode);
	}

	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal complaintActionPk,
			BigDecimal complaintDescEnglishPk, BigDecimal complaintArabicPk) {
		iComplaintActionDao.deleteRecordPermanently(complaintActionPk,complaintDescEnglishPk,complaintArabicPk);
		
	}

	@Override
	@Transactional
	public List<ComplaintActionDTO> getAllComplaintActionChildRecords(
			BigDecimal complaintActionId) {
		 
		return iComplaintActionDao.getAllComplaintActionChildRecords(complaintActionId);
	}

	@Override
	@Transactional
	public List<ComplaintActionDTO> getComplaintActionRecord(
			 String complaintActionCode) {
		 
		return iComplaintActionDao.getComplaintActionRecord(complaintActionCode);
	}

	@Override
	@Transactional
	public void activateRecord(BigDecimal complaintActionPk, String userName) {
		iComplaintActionDao.activateRecord(complaintActionPk,userName);
		
	}

	@Override
	@Transactional
	public List<ComplaintActionDTO> getAllUnApprovedRecords() {
		 
		return iComplaintActionDao.getAllUnApprovedRecords();
	}

	@Override
	@Transactional
	public  String approveRecord(BigDecimal complaintActionPk, String userName) {
		
		return iComplaintActionDao.approveRecord(complaintActionPk,userName);
		
	}

	@Override
	@Transactional
	public List<ComplaintActionDTO> getAllUnApprovedRecordsFromDesc(
			BigDecimal complaintActionId) {
		 
		return iComplaintActionDao.getAllUnApprovedRecordsFromDesc(complaintActionId);
	}

	@Override
	@Transactional
	public List<ComplaintActionDTO> getParentRecordForPopulate(
			String complaintActionCode) {
		
		return iComplaintActionDao.getParentRecordForPopulate(complaintActionCode);
	}

	 
	
}
