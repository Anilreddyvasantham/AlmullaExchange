package com.amg.exchange.complaint.customer.support.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.registration.model.CountryBranch;

public interface IComplaintRegisteredToRemittancesService<T> {

	public void saveOrUpdate(ComplaintFollowup complaintFollowup);

	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal countryId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal bankId);

	public List<CountryBranch> getCountryBranchList(BigDecimal locationCode);

	public List<ComplaintMatrix> getComplaintMatrixs(BigDecimal appCountryId, BigDecimal countryId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal bankId);

	public List<ComplaintActionDesc> getComplaintActionDesc(BigDecimal actionId, BigDecimal languageId);

	public List<ComplaintRemarksDesc> getComplaintRemarksDescList(BigDecimal languageId);

	public List<ComplaintAssignedDesc> getComplaintAssignedDescList(BigDecimal complaintAssignedId, BigDecimal languageId);

	public List<CommunicationMethodDesc> getCommunicationMethodDescList(BigDecimal communicationMethodId, BigDecimal languageId);

	public List<ComplaintRemarksMaster> getComplaintRemarksDescCode(BigDecimal complaintRemarksCodeId);

	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal companyId, BigDecimal remittanceDocNo, BigDecimal remittanceFinancialYr);

	public String toCheckComplaintTypeFromUserProfile(BigDecimal employeeId);

	public List<ComplaintFollowup> getComplaintFollowUp(BigDecimal companyId, BigDecimal complaintReference, BigDecimal complaintFinancialYr);
	
	public List<ComplaintFollowup> getComplaintFollowUpList(BigDecimal companyId, BigDecimal complaintReference, BigDecimal complaintFinancialYr);
	
	public List<ComplaintLog> searchFromComplaintLog(BigDecimal companyId, BigDecimal complaintRefernceNo,BigDecimal remittanceFinanceYear);

}
