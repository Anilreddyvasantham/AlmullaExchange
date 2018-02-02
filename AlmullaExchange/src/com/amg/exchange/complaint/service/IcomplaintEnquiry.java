package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.model.ComplaintEnquiryRemittanceView;
import com.amg.exchange.complaint.model.ComplaintPendingEnquiryView;


public interface IcomplaintEnquiry {

	public  List<ComplaintPendingEnquiryView> tofetchRecorsFromcomplaintView(BigDecimal locationId, BigDecimal receiptNumber);
	public  List<ComplaintPendingEnquiryView> fetchRecorsFromcomplaintPendingView(BigDecimal complainReference);
	public  List<ComplaintEnquiryRemittanceView> fetchRecorsFromcomplaintFollowupEnquiryView(BigDecimal complainReference);


}
