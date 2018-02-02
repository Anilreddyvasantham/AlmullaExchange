package com.amg.exchange.complaint.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.dao.IComplaintDao;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintRemittanceViewModel;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.service.IComplaintService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankApplicability;

@SuppressWarnings("serial")
@Service
public class ComplaintServiceImpl<T> implements IComplaintService<T>,Serializable {

	@Autowired
	IComplaintDao<T> complaintDao;

	@Override
	@Transactional
	public void saveComplaintMatrix(ComplaintMatrix complaintMatrix) {

		complaintDao.saveComplaintMatrix(complaintMatrix);
	}

	@Transactional
	@Override
	public List<ComplaintTypeDesc> getComplaintTypeList(BigDecimal languageId) { 

		return complaintDao.getComplaintTypeList(languageId);
	}

	@Transactional
	@Override
	public List<CommunicationMethodDesc> getCommunicationMethodList(BigDecimal languageId) { 

		return complaintDao.getCommunicationMethodList(languageId);
	}

	@Transactional
	@Override
	public List<ComplaintAssignedDesc> getComplaintAssignedList(BigDecimal languageId) { 

		return complaintDao.getComplaintAssignedList(languageId);
	}
	@Transactional
	@Override
	public List<ComplaintActionDesc> getComplaintActionList(BigDecimal languageId) { 

		return complaintDao.getComplaintActionList(languageId);
	}

	@Transactional
	@Override
	public List<ComplaintMatrix> getComplaintMatrixList(BigDecimal countryId,BigDecimal bankId, BigDecimal setrviceId,BigDecimal appCountryId,BigDecimal complaintTypeId) { 

		return complaintDao.getComplaintMatrixList(countryId,bankId,setrviceId,appCountryId,complaintTypeId);
	}

	@Transactional
	@Override
	public List<ComplaintRemittanceViewModel> getComplaintRemittanceForComplaintCreation(BigDecimal companyId, BigDecimal remittanceYearId,BigDecimal remittanceDocNum) {
		// TODO Auto-generated method stub
		return complaintDao.getComplaintRemittanceForComplaintCreation(companyId, remittanceYearId, remittanceDocNum);
	}

	@Transactional
	@Override
	public List<ComplaintMatrix> getCheckForDbForRecord(BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintTakenById, BigDecimal complaintActionId,BigDecimal countryId2) {
		return complaintDao.getCheckForDbForRecord(countryId,bankId,serviceId,complaintTypeId,complaintTakenById,complaintActionId,countryId2);
	}

	@Override
	@Transactional
	public List<ComplaintMatrixDTO> getViewAllRecorsFromDb() {
		return complaintDao.getViewAllRecorsFromDb();
	}

	@Override
	@Transactional
	public List<ComplaintAssignedDesc> getComplaintAssignedDesc(BigDecimal languageId, BigDecimal complaintDestionId) {
		return complaintDao.getComplaintAssignedDesc(languageId,complaintDestionId);
	}
	@Transactional
	@Override
	public List<ComplaintTypeDesc> getComplaintTypeDescDb(BigDecimal languageId, BigDecimal complaintTypeId) {
		return complaintDao.getComplaintTypeDescDb(languageId,complaintTypeId);
	}
	@Transactional
	@Override
	public List<CommunicationMethodDesc> getCommunicationMethodDescDb(BigDecimal languageId, BigDecimal communicationMethodId) {
		return complaintDao.getCommunicationMethodDescDb(languageId,communicationMethodId);
	}
	@Transactional
	@Override
	public List<ComplaintActionDesc> getCompalintDescDb(BigDecimal languageId, BigDecimal complaintActionId) {
		return complaintDao.getCompalintDescDb(languageId,complaintActionId);
	}
	@Transactional
	@Override
	public void deActivateRecord(BigDecimal complaintMatrixPk, String userName) {
		complaintDao.deActivateRecord(complaintMatrixPk,userName);
	}
	@Transactional
	@Override
	public void deleteRecordPermentely(BigDecimal complaintMatrixPk) {
		complaintDao.deleteRecordPermentely(complaintMatrixPk);

	}
	@Transactional
	@Override
	public List<ComplaintMatrixDTO> displayAllComplaintMatrixMasterForApproval() {
		return complaintDao.displayAllComplaintMatrixMasterForApproval();
	}
	@Transactional
	@Override
	public String checkComplaintMatrixMasterApproveMultiUser(BigDecimal complaintMatrixPk, String userName) {
		return complaintDao.checkComplaintMatrixMasterApproveMultiUser(complaintMatrixPk,userName);
	}

	@Override
	@Transactional
	public void saveComplaintLog(ComplaintLog complaintLog) {
		complaintDao.saveComplaintLog(complaintLog);
	}

	@Override
	@Transactional
	public List<ComplaintRemarksMaster> getComplaintRemarksCodeBasedOnId(BigDecimal remarksCodeId) {
		return complaintDao.getComplaintRemarksCodeBasedOnId(remarksCodeId);
	}

	@Override
	@Transactional
	public List<CountryBranch> getCountryBranchCodeBasedOnBranchId(BigDecimal locationId) {
		return complaintDao.getCountryBranchCodeBasedOnBranchId(locationId);
	}

	@Override
	@Transactional
	public List<ComplaintLog> getDuplicateCHeckForCompalintCreation(BigDecimal countryId, BigDecimal remitdealYear, BigDecimal remitdealReference, BigDecimal companyId, Date closedDate) {
		return complaintDao.getDuplicateCHeckForCompalintCreation(countryId, remitdealYear, remitdealReference, companyId, closedDate);
	}

	@Override
	@Transactional
	public List<BankApplicability> getCorrespondentBankList(BigDecimal countryId, BigDecimal applicationCountryId){
		return complaintDao.getCorrespondentBankList(countryId, applicationCountryId);
	}

	@Override
	@Transactional
	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId) {
		return complaintDao.getBusinessCountryList(languageId);
	}

	@Override
	@Transactional
	public String toCheckComplaintTypeFromUserProfile(BigDecimal employeeId) {
		return complaintDao.toCheckComplaintTypeFromUserProfile(employeeId);
	}

	@Override
	@Transactional
	public String basedonCountryandBankTogetEmail(BigDecimal countryId, BigDecimal bankId) {
		return complaintDao.basedonCountryandBankTogetEmail(countryId,bankId);
	}

	@Override
	@Transactional
	public List<ComplaintMatrix> toFetchBasedOnTheseCombination(BigDecimal applicationId, BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintAssignedId) {
		return complaintDao.toFetchBasedOnTheseCombination(applicationId,countryId,bankId,serviceId,complaintTypeId,complaintAssignedId);
	}

	@Override
	@Transactional
	public String tofetchEmployeeIdBasedOnEmployeeCode(BigDecimal employeeId) {
		return complaintDao.tofetchEmployeeIdBasedOnEmployeeCode(employeeId);
	}

	@Override
	@Transactional
	public Boolean emailcheck(BigDecimal complaintMethodId) {
		// TODO Auto-generated method stub
		return complaintDao.emailcheck(complaintMethodId);
	}

	@Override
	@Transactional
	public void updateRemitComplaintProblemStatus(BigDecimal remitDocNumber) {
		// TODO Auto-generated method stub
		complaintDao.updateRemitComplaintProblemStatus(remitDocNumber);
	}

	@Override
	@Transactional
	public void updateAssignToAssignDate(BigDecimal complaintRef , BigDecimal assignTo , String assignToCode , Date assignDate) {
		// TODO Auto-generated method stub
		complaintDao.updateAssignToAssignDate(complaintRef , assignTo , assignToCode , assignDate);
	}
	
	 	@Override
	        @Transactional
	        public String tofetchDocumentIdBasedOnDocumentCode(BigDecimal remittanceDocNo) {
	        	  return complaintDao.tofetchDocumentIdBasedOnDocumentCode(remittanceDocNo);
	        }
	        
	        @Override
	        @Transactional
	        public void updateRemiTransctionPrijectStatus(BigDecimal remittanceDocNo, String userName) {
	        	   complaintDao.updateRemiTransctionPrijectStatus(remittanceDocNo,userName); 
	        }
	        @Override
	        @Transactional
	        public Boolean isRemittanceStatus(BigDecimal remarksId, BigDecimal documentNo) {
	        	  return complaintDao.isRemittanceStatus(remarksId,documentNo);
	        }
	        @Override
	        @Transactional
	        public List<UserFinancialYear> getAllDocumentYear(){
	        	return complaintDao.getAllDocumentYear();
	        }
	        
	        @Override
	        @Transactional
	        public List<ComplaintLog> getComplaintRemittance(BigDecimal complaintReference){
	        	return complaintDao.getComplaintRemittance(complaintReference);
	        }
	        
	        @Override
	        @Transactional
	        public void updateCloseComplaint(ComplaintLog complaintLog){
	        	complaintDao.updateCloseComplaint(complaintLog);
	        }
	        @Override
	        @Transactional
	        public void saveComplaintFollowup(ComplaintFollowup complaintFollowup){
	        	complaintDao.saveComplaintFollowup(complaintFollowup);
	        }
	        @Override
	        @Transactional
	        public List<ComplaintAssigned> getComplaintAssigned(BigDecimal complaintAssignedId) {
	        	return complaintDao.getComplaintAssigned(complaintAssignedId);
	        }
	        
	        @Override
	        @Transactional
	        public String getBranchNameBasedOnBranchCode(BigDecimal branchId) {
	        	return complaintDao.getBranchNameBasedOnBranchCode(branchId);
	        }
	        
	        @Override
	        @Transactional
	        public String getMobileBasedOnCustomerId(BigDecimal customerId) {
	        	return complaintDao.getMobileBasedOnCustomerId(customerId);
	        }
}
