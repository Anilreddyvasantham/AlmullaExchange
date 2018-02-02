package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;

public interface IComplaintAssignedService {

	public List<String> getComplaintAssignedCodeList(String query);

	public void saveAllComplaintAssigenedRecords(ComplaintAssignedDTO complaintAssignedDTO)throws Exception;

	public List<ComplaintAssignedDTO> getComPlaintAssignedAllValues(String complaintAssignedCode);
	
	public List<ComplaintAssignedDTO> getcomplaintAssignedCodeBasedOnComplaintDescription(BigDecimal complaintAssignedId);

	public void getComplientAssignedUpdateRecord(ComplaintAssignedDTO complaintAssignedDTO)throws Exception;

	public List<ComplaintAssignedDTO> getviewRecords();

	public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssigned(BigDecimal complaintAssignedId);

	public List<ComplaintAssignedDTO> getfetchRecordForApproval();

	public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssignedApproval(BigDecimal complaintAssignedId);

	public String getApproveRecord(BigDecimal complaintAssignedId, String userName);

	public void deActivateRecord(BigDecimal complaintAssignedId, String userName);

	public void deleteRecordPermentely(BigDecimal complaintAssignedId, BigDecimal complaintEnglishFullDescriptionPK, BigDecimal complaintArabicFullDescriptionPK);
	
	public List<ComplaintAssignedDesc> getAllComplaintAssignedForComplaintCreation(BigDecimal countryId , BigDecimal languageId);

        public List<ComplaintAssignedDTO> getComPlaintAssignedIdDuplicateChecking(String complaintAssignedId);


}
