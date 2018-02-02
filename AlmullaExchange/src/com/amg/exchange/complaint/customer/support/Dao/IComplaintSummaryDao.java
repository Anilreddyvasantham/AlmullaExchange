package com.amg.exchange.complaint.customer.support.Dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.model.ComplaintSummary;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;

public interface IComplaintSummaryDao<T> {

	public List<ComplaintSummary> displayComplaintSummary();

	public List<ComplaintLog> getTotalComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId);
	
	public List<ComplaintLog> getPendingComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId);

	public List<ComplaintLog> searchFromComplaintLog(BigDecimal companyId, BigDecimal complaintRefernceNo, BigDecimal remittanceFinanceYear);

	public String getCountryName(BigDecimal countryId,BigDecimal languageId);

	public String getComplaintTypeDesc(BigDecimal complaintTypeDescId,BigDecimal languageId);

	public String getServiceDesc(BigDecimal serviceId,BigDecimal languageId);
	
	public List<UserFinancialYear> getAllDocumentYear();

}
