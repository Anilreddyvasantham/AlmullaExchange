package com.amg.exchange.complaint.customer.support.serviceImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.customer.support.Dao.IComplaintSummaryDao;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.model.ComplaintSummary;
import com.amg.exchange.complaint.customer.support.service.IComplaintSummaryService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;

@Service("complaintSummaryServiceImple")
public class ComplaintSummaryServiceImple<T> implements IComplaintSummaryService<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
	IComplaintSummaryDao<T> complaintSummaryDao;

	public IComplaintSummaryDao<T> getComplaintSummaryDao() {
		return complaintSummaryDao;
	}

	public void setComplaintSummaryDao(IComplaintSummaryDao<T> complaintSummaryDao) {
		this.complaintSummaryDao = complaintSummaryDao;
	}

	@Override
	@Transactional
	public List<ComplaintSummary> displayComplaintSummary() {
		return getComplaintSummaryDao().displayComplaintSummary();
	}
	
	
	@Override
	@Transactional	
	public List<ComplaintLog> getTotalComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId){
		return getComplaintSummaryDao().getTotalComplaints(countryId, serviceId, bankId, complaintTypeId);
	}
	
	@Override
	@Transactional
	public List<ComplaintLog> getPendingComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId){
		return getComplaintSummaryDao().getPendingComplaints(countryId, serviceId, bankId, complaintTypeId);
	}

	
	@Override
	@Transactional
	public List<ComplaintLog> searchFromComplaintLog(BigDecimal companyId, BigDecimal complaintRefernceNo,BigDecimal remittanceFinanceYear){
		return getComplaintSummaryDao().searchFromComplaintLog(companyId, complaintRefernceNo, remittanceFinanceYear);
	}
	
	@Override
	@Transactional
	public String getComplaintTypeDesc(BigDecimal complaintTypeId,BigDecimal languageId) {
		return getComplaintSummaryDao().getComplaintTypeDesc(complaintTypeId,languageId);
	}
	
	@Override
	@Transactional
	public String getCountryName(BigDecimal countryId,BigDecimal languageId) {
		return getComplaintSummaryDao().getCountryName(countryId,languageId);
	}
	
	@Override
	@Transactional
	public String getServiceDesc(BigDecimal serviceId,BigDecimal languageId) {
		return getComplaintSummaryDao().getServiceDesc(serviceId,languageId);
	}
	@Override
	@Transactional
	public List<UserFinancialYear> getAllDocumentYear(){
		return getComplaintSummaryDao().getAllDocumentYear();
	}

}