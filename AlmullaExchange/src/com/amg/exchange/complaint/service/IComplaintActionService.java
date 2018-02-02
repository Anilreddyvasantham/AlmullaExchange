package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.dto.ComplaintActionDTO;
import com.amg.exchange.complaint.model.ComplaintAction;
import com.amg.exchange.complaint.model.ComplaintActionDesc;

public interface IComplaintActionService {
	
	public  List<ComplaintActionDTO> getAllRecords();
	public List<ComplaintActionDTO> getAllComplaintActionChildRecords(BigDecimal complaintActionId);
	public void saveRecords(ComplaintAction complaintAction,ComplaintActionDesc complaintDescEng,ComplaintActionDesc complaintDescArabic);
	public List<String> getAutoComplete(String actionCode);
	public void deleteRecordPermanently(BigDecimal complaintActionPk,BigDecimal complaintDescEnglishPk,BigDecimal complaintArabicPk);
	public List<ComplaintActionDTO> getComplaintActionRecord(String complaintActionCode);
	public void activateRecord(BigDecimal complaintActionPk,String userName);
	public List<ComplaintActionDTO> getAllUnApprovedRecords();
	public List<ComplaintActionDTO> getAllUnApprovedRecordsFromDesc(BigDecimal complaintActionId);
	public String approveRecord(BigDecimal complaintActionPk,String userName);
	public List<ComplaintActionDTO>  getParentRecordForPopulate(String complaintActionCode);
 
}
