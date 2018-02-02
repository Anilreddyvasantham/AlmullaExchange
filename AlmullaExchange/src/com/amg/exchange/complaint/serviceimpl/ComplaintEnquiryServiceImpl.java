package com.amg.exchange.complaint.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.dao.IComplaintEnquiryDao;
import com.amg.exchange.complaint.model.ComplaintEnquiryRemittanceView;
import com.amg.exchange.complaint.model.ComplaintPendingEnquiryView;
import com.amg.exchange.complaint.service.IcomplaintEnquiry;


@Service("complaintEnquiry")
public class ComplaintEnquiryServiceImpl implements IcomplaintEnquiry {

	  @Autowired 
	  IComplaintEnquiryDao complaintEnquiryDao;

	  @Override
	  @Transactional
	  public List<ComplaintPendingEnquiryView> tofetchRecorsFromcomplaintView(BigDecimal locationId, BigDecimal receiptNumber) {
		    return complaintEnquiryDao.tofetchRecorsFromcomplaintView(locationId,receiptNumber);
	  }

	@Override
	@Transactional
	public List<ComplaintPendingEnquiryView> fetchRecorsFromcomplaintPendingView(
			BigDecimal complainReference) {
		return complaintEnquiryDao.fetchRecorsFromcomplaintPendingView(complainReference);
	}

	@Override
	@Transactional
	public List<ComplaintEnquiryRemittanceView> fetchRecorsFromcomplaintFollowupEnquiryView(
			BigDecimal complainReference) {
		return complaintEnquiryDao.fetchRecorsFromcomplaintFollowupEnquiryView(complainReference);
	}
}
