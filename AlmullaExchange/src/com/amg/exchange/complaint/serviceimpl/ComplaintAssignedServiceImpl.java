package com.amg.exchange.complaint.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.dao.IComplaintAssignedDao;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.service.IComplaintAssignedService;

@Service("complaintAssignedService")
public class ComplaintAssignedServiceImpl implements IComplaintAssignedService {

	  @Autowired
	  IComplaintAssignedDao complaintAssignedDao;

	  @Override
	  @Transactional
	  public List<String> getComplaintAssignedCodeList(String query) {
		    return complaintAssignedDao.getComplaintAssignedCodeList(query);
	  }

	  @Override
	  @Transactional(rollbackFor=Exception.class)
	  public void saveAllComplaintAssigenedRecords(ComplaintAssignedDTO complaintAssignedDTO)throws Exception{
		    complaintAssignedDao.saveAllComplaintAssigenedRecords(complaintAssignedDTO);
		    
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getComPlaintAssignedAllValues(String complaintAssignedCode) {
		    return complaintAssignedDao.getComPlaintAssignedAllValues(complaintAssignedCode);
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getcomplaintAssignedCodeBasedOnComplaintDescription(BigDecimal complaintAssignedId) {
		    return complaintAssignedDao.getcomplaintAssignedCodeBasedOnComplaintDescription(complaintAssignedId);
	  }

	  @Override
	  @Transactional(rollbackFor=Exception.class)
	  public void getComplientAssignedUpdateRecord(ComplaintAssignedDTO complaintAssignedDTO)throws Exception {
		    complaintAssignedDao.getComplientAssignedUpdateRecord(complaintAssignedDTO);
		    
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getviewRecords() {
		    return complaintAssignedDao.getviewRecords();
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssigned(BigDecimal complaintAssignedId) {
		    return complaintAssignedDao.getAllRecordsRelatedComplaintAssigned(complaintAssignedId);
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getfetchRecordForApproval() {
		    return complaintAssignedDao.getfetchRecordForApproval();
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getAllRecordsRelatedComplaintAssignedApproval(BigDecimal complaintAssignedId) {
		    return complaintAssignedDao.getAllRecordsRelatedComplaintAssignedApproval(complaintAssignedId);
	  }

	  @Override
	  @Transactional
	  public String getApproveRecord(BigDecimal complaintAssignedId, String userName) {
		    return complaintAssignedDao.getApproveRecord(complaintAssignedId,userName);
	  }

	  @Override
	  @Transactional
	  public void deActivateRecord(BigDecimal complaintAssignedId, String userName) {
		    complaintAssignedDao.deActivateRecord(complaintAssignedId,userName);
	  }

	  @Override
	  @Transactional
	  public void deleteRecordPermentely(BigDecimal complaintAssignedId, BigDecimal complaintEnglishFullDescriptionPK, BigDecimal complaintArabicFullDescriptionPK) {
		    complaintAssignedDao.deleteRecordPermentely(complaintAssignedId,complaintEnglishFullDescriptionPK,complaintArabicFullDescriptionPK); 
	  }
	  
	  @Override
	  @Transactional
	  public List<ComplaintAssignedDesc> getAllComplaintAssignedForComplaintCreation(BigDecimal countryId ,BigDecimal languageId) {
		  return complaintAssignedDao.getAllComplaintAssignedForComplaintCreation(countryId , languageId);
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> getComPlaintAssignedIdDuplicateChecking(String complaintAssignedId) {
		    return complaintAssignedDao.getComPlaintAssignedIdDuplicateChecking(complaintAssignedId);
	  }

	  
}
