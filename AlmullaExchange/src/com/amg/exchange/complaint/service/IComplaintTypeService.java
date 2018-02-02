package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;

public interface IComplaintTypeService<T> {
	
	public void saveAndUpdateComplaintType(ComplaintTypeMaster complaintTypeMaster);
	public void saveAndUpdateComplaintTypeDesc(ComplaintTypeDesc complaintTypeDesc);
	public Boolean isComplaintTypeCodeExist(String complaintCode);
	public List<ComplaintTypeDesc> displayAllComplaintTypeDesc(BigDecimal complaintTypeId);
	public List<ComplaintTypeMaster> displayAllComplaintTypeToView(BigDecimal appCountryId);
	public void activateComplaintType(BigDecimal complaintTypeId, String userName);
	public void deleteComplaintType(BigDecimal complaintTypeId, BigDecimal englishDescId, BigDecimal arabicDescId);
	public List<String> autoCompleteList(String query);
	
	public List<ComplaintTypeMaster> displayComplaintTypeForApprove(BigDecimal appCountryId,String inActive);
	public void approveRecord(BigDecimal complaintTypeId, String userName,String isActive);
	public List<ComplaintTypeMaster> displayComplaintTypeBasedOnComplaintTypeCode(String complaintCode);
	public List<ComplaintTypeDesc> displayComplaintTypeForComplaintCreation(BigDecimal appCountryId , BigDecimal languageId);
	
	/*public void saveComplaintTypeMethod(ComplaintTypeMaster complaintTypeMaster);
	
	public void saveComplaintTypeDescMethod(ComplaintTypeDesc complaintTypeDesc);
	
	public void activateComplaintTypeMethod(BigDecimal complaintTypeId, String userName);
	
	public List<ComplaintTypeDesc> getAllComplaintTypeDesc(BigDecimal ComplaintTypeId);
	
	public List<ComplaintTypeMaster> getAllComplaintType(BigDecimal appCountryId);
	
	public List<ComplaintTypeMaster> getComplaintTypeForApprove(BigDecimal appCountryId,String inActive);
	
	public Boolean isComplaintCodeExist(String complaintCode);
	
	public void approveRecord(BigDecimal complaintTypeId, String userName,String isActive);
	
	public void deleteComplaintTypeMethod(BigDecimal complaintTypeId, BigDecimal englishDescId, BigDecimal arabicDescId);
	
	public List<String> autoCompleteList(String query);
	
	public List<ComplaintTypeMaster> getComplaintTypeBasedOnComplaintCode(String complaintCode);
	
	public List<ComplaintTypeDesc> getComplaintTypeForComplaintCreation(BigDecimal appCountryId , BigDecimal languageId);
*/
}
