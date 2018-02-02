package com.amg.exchange.complaint.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintRemittanceViewModel;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.BankApplicability;

public interface IComplaintDao<T> {

	public void saveComplaintMatrix(ComplaintMatrix complaintMatrix);

	public List<ComplaintTypeDesc> getComplaintTypeList(BigDecimal lagnuageId);

	public List<CommunicationMethodDesc> getCommunicationMethodList(BigDecimal lagnuageId);

	public List<ComplaintAssignedDesc> getComplaintAssignedList(BigDecimal lagnuageId);

	public List<ComplaintActionDesc> getComplaintActionList(BigDecimal lagnuageId);

	public List<ComplaintMatrix> getComplaintMatrixList(BigDecimal countryId,BigDecimal bankId, BigDecimal setrviceId,BigDecimal appCountryId,BigDecimal complaintTypeId);

	public List<ComplaintRemittanceViewModel> getComplaintRemittanceForComplaintCreation(BigDecimal companyId,BigDecimal remittanceYearId, BigDecimal remittanceDocNum);

	public List<ComplaintMatrix> getCheckForDbForRecord(BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintTakenById, BigDecimal complaintActionId,BigDecimal countryId2);
	
	public List<ComplaintMatrixDTO> getViewAllRecorsFromDb();

	public List<ComplaintAssignedDesc> getComplaintAssignedDesc(BigDecimal languageId, BigDecimal complaintDestionId);

	public List<ComplaintTypeDesc> getComplaintTypeDescDb(BigDecimal languageId, BigDecimal complaintTypeId);
	
	public List<CommunicationMethodDesc> getCommunicationMethodDescDb(BigDecimal languageId, BigDecimal communicationMethodId);
	
	public List<ComplaintActionDesc> getCompalintDescDb(BigDecimal languageId, BigDecimal complaintActionId);
	
	public void deActivateRecord(BigDecimal complaintMatrixPk, String userName);
	
	public void deleteRecordPermentely(BigDecimal complaintMatrixPk);
	
	public List<ComplaintMatrixDTO> displayAllComplaintMatrixMasterForApproval();
	
	public String checkComplaintMatrixMasterApproveMultiUser(BigDecimal complaintMatrixPk, String userName);
	
	public List<ComplaintRemarksMaster> getComplaintRemarksCodeBasedOnId(BigDecimal remarksCodeId);
	
	public List<CountryBranch> getCountryBranchCodeBasedOnBranchId(BigDecimal locationId);
	
	public List<BankApplicability> getCorrespondentBankList(BigDecimal countryId, BigDecimal applicationCountryId);
	
	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId);

	public List<ComplaintLog> getDuplicateCHeckForCompalintCreation(BigDecimal countryId, BigDecimal remitdealYear, BigDecimal remitdealReference, BigDecimal companyId, Date closedDate);
	
	public void saveComplaintLog(ComplaintLog complaintLog);
	
	public String toCheckComplaintTypeFromUserProfile(BigDecimal employeeId);

	public String basedonCountryandBankTogetEmail(BigDecimal countryId, BigDecimal bankId);

	public List<ComplaintMatrix> toFetchBasedOnTheseCombination(BigDecimal applicationId, BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintAssignedId);

	public String tofetchEmployeeIdBasedOnEmployeeCode(BigDecimal employeeId);
	
	public Boolean emailcheck(BigDecimal complaintMethodId);
	
	public void updateRemitComplaintProblemStatus(BigDecimal remitDocNumber);

	public void updateAssignToAssignDate(BigDecimal complaintRef , BigDecimal assignTo , String assignToCode , Date assignDate);
	
	public String tofetchDocumentIdBasedOnDocumentCode(BigDecimal remittanceDocNo);

	public void updateRemiTransctionPrijectStatus(BigDecimal remittanceDocNo, String userName);
	
	public Boolean isRemittanceStatus(BigDecimal remarksId,BigDecimal documentNo);
	
	public List<UserFinancialYear> getAllDocumentYear();
	
	public List<ComplaintLog> getComplaintRemittance(BigDecimal complaintReference);
	
	public void updateCloseComplaint(ComplaintLog complaintLog);
	
	public void saveComplaintFollowup(ComplaintFollowup complaintFollowup);
	
	public List<ComplaintAssigned> getComplaintAssigned(BigDecimal complaintAssignedId);
	
	public String getMobileBasedOnCustomerId(BigDecimal customerId);
	
	public String getBranchNameBasedOnBranchCode(BigDecimal branchId);
	
}
