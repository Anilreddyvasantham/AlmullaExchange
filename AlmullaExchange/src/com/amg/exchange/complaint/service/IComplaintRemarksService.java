package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;

public interface IComplaintRemarksService<T> {
	
	public void saveComplaintRemarksMethod(ComplaintRemarksMaster complaintRemarksMaster);
	
	public void saveComplaintRemarksDescMethod(ComplaintRemarksDesc complaintRemarksDesc);
	
	public void activateComplaintRemarksMethod(BigDecimal complaintRemarksId, String userName);
	
	public List<ComplaintRemarksDesc> getAllComplaintRemarksDesc(BigDecimal ComplaintRemarksId);
	
	public List<ComplaintRemarksMaster> getAllComplaintRemarks(BigDecimal appCountryId);
	
	public List<ComplaintRemarksMaster> getComplaintRemarksForApprove(BigDecimal appCountryId,String inActive);
	
	public Boolean isComplaintCodeExist(String complaintCode);
	
	public void approveRecord(BigDecimal complaintRemarksId, String userName,String isActive);
	
	public void deleteComplaintRemarksMethod(BigDecimal complaintRemarksId, BigDecimal englishDescId, BigDecimal arabicDescId);
	
	public List<String> autoCompleteList(String query);
	
	public List<ComplaintRemarksMaster> getComplaintRemarksBasedOnComplaintCode(String complaintCode);
	
	public List<ComplaintRemarksDesc> getAllComplaintRemarksForComplaintCreation(BigDecimal countryId , BigDecimal languageId);

}
