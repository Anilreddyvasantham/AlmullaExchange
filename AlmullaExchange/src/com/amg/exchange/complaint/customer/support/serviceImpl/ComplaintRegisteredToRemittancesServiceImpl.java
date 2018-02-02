package com.amg.exchange.complaint.customer.support.serviceImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.customer.support.Dao.IComplaintRegisteredToRemittancesDao;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.service.IComplaintRegisteredToRemittancesService;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.registration.model.CountryBranch;

@Service("complaintRegisteredToRemittancesService")
public class ComplaintRegisteredToRemittancesServiceImpl<T> implements IComplaintRegisteredToRemittancesService<T> , Serializable{
	private static final long serialVersionUID = 1L;
	@Autowired
	IComplaintRegisteredToRemittancesDao<T> complaintRegisteredToRemittancesDao;
	
	

	public IComplaintRegisteredToRemittancesDao<T> getComplaintRegisteredToRemittancesDao() {
		return complaintRegisteredToRemittancesDao;
	}



	public void setComplaintRegisteredToRemittancesDao(IComplaintRegisteredToRemittancesDao<T> complaintRegisteredToRemittancesDao) {
		this.complaintRegisteredToRemittancesDao = complaintRegisteredToRemittancesDao;
	}
	
	@Override
	@Transactional
	public void saveOrUpdate(ComplaintFollowup complaintFollowup){
		getComplaintRegisteredToRemittancesDao().saveOrUpdate(complaintFollowup);
	}
	
	@Override
	@Transactional
	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal countryId,BigDecimal serviceId,BigDecimal complaintTypeId,BigDecimal bankId) {
		return getComplaintRegisteredToRemittancesDao().displayRemittancesRegisteredFromComplaintLog(countryId, serviceId, complaintTypeId, bankId);
	}
	@Override
	@Transactional
	 public List<CountryBranch> getCountryBranchList(BigDecimal locationCode){
		 return getComplaintRegisteredToRemittancesDao().getCountryBranchList(locationCode);
	 }
	@Override
	@Transactional
	public List<ComplaintMatrix> getComplaintMatrixs(BigDecimal appCountryId,BigDecimal countryId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal bankId){
		 return getComplaintRegisteredToRemittancesDao().getComplaintMatrixs(appCountryId,countryId, serviceId, complaintTypeId, bankId);
	}
	@Override
	@Transactional
	public List<ComplaintActionDesc> getComplaintActionDesc(BigDecimal actionId,BigDecimal languageId){
		return getComplaintRegisteredToRemittancesDao().getComplaintActionDesc(actionId, languageId);
	}
	@Override
	@Transactional
	public List<ComplaintRemarksDesc> getComplaintRemarksDescList(BigDecimal languageId){
		return getComplaintRegisteredToRemittancesDao().getComplaintRemarksDescList(languageId);
	}
	@Override
	@Transactional
	public List<ComplaintAssignedDesc> getComplaintAssignedDescList(BigDecimal complaintAssignedId,BigDecimal languageId){
		return getComplaintRegisteredToRemittancesDao().getComplaintAssignedDescList(complaintAssignedId,languageId);
	}
	@Override
	@Transactional
	public List<CommunicationMethodDesc> getCommunicationMethodDescList(BigDecimal communicationMethodId,BigDecimal languageId){
		return getComplaintRegisteredToRemittancesDao().getCommunicationMethodDescList(communicationMethodId,languageId);
	}
	@Override
	@Transactional
	public List<ComplaintRemarksMaster> getComplaintRemarksDescCode(BigDecimal complaintRemarksCodeId){
		return getComplaintRegisteredToRemittancesDao().getComplaintRemarksDescCode(complaintRemarksCodeId);
	}
	@Override
	@Transactional
	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal companyId, BigDecimal remittanceDocNo, BigDecimal remittanceFinancialYr){
		return getComplaintRegisteredToRemittancesDao().displayRemittancesRegisteredFromComplaintLog(companyId, remittanceDocNo, remittanceFinancialYr);
	}

	
	@Override
	@Transactional
	public String toCheckComplaintTypeFromUserProfile(BigDecimal employeeId){
		return getComplaintRegisteredToRemittancesDao().toCheckComplaintTypeFromUserProfile(employeeId);
	}
	@Override
	@Transactional
	public List<ComplaintFollowup> getComplaintFollowUp(BigDecimal companyId, BigDecimal complaintReference , BigDecimal complaintFinancialYr){
		return getComplaintRegisteredToRemittancesDao().getComplaintFollowUp(companyId, complaintReference, complaintFinancialYr);
	}
	@Override
	@Transactional
	public List<ComplaintFollowup> getComplaintFollowUpList(BigDecimal companyId, BigDecimal complaintReference, BigDecimal complaintFinancialYr){
		return getComplaintRegisteredToRemittancesDao().getComplaintFollowUpList(companyId,complaintReference,complaintFinancialYr);
	}
	@Override
	@Transactional
	public List<ComplaintLog> searchFromComplaintLog(BigDecimal companyId, BigDecimal complaintRefernceNo,BigDecimal remittanceFinanceYear){
		return getComplaintRegisteredToRemittancesDao().searchFromComplaintLog(companyId, complaintRefernceNo, remittanceFinanceYear);
	}
}
