package com.amg.exchange.enquiry.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.enquiry.model.CashDepositInquiryModelView;
import com.amg.exchange.enquiry.model.FundTransferInquiryModelView;

public interface IFundTransferInquiryService {
	
	public List<FundTransferInquiryModelView> getAllFundTransferInquiry();
	
	public List<FundTransferInquiryModelView> getFundTransferInquiryBasedonFormDetails(BigDecimal currencyId , String locationName , BigDecimal docCode , String docName , BigDecimal finYear , BigDecimal docNum , Date docDate , String cashTransfer);
	
	public List<CashDepositInquiryModelView> getAllCashDepositInquiry();
	
	public List<CashDepositInquiryModelView> getCashDepositInquiryBasedonFormDetails(String cashTransfer , BigDecimal currencyId , Date fromdocDate , Date todocDate , String locationName );

}
