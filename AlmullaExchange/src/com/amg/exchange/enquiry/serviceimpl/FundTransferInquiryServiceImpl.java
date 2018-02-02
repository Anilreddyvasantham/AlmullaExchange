package com.amg.exchange.enquiry.serviceimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.enquiry.dao.IFundTransferInquiryDao;
import com.amg.exchange.enquiry.model.CashDepositInquiryModelView;
import com.amg.exchange.enquiry.model.FundTransferInquiryModelView;
import com.amg.exchange.enquiry.service.IFundTransferInquiryService;

@Service
public class FundTransferInquiryServiceImpl implements IFundTransferInquiryService{
	
	@Autowired
	IFundTransferInquiryDao ifundTransferInquiryDao;

	@Override
	@Transactional
	public List<FundTransferInquiryModelView> getAllFundTransferInquiry() {
		return ifundTransferInquiryDao.getAllFundTransferInquiry();
	}

	@Override
	@Transactional
	public List<FundTransferInquiryModelView> getFundTransferInquiryBasedonFormDetails(BigDecimal currencyId, String locationName, BigDecimal docCode, String docName, BigDecimal finYear, BigDecimal docNum, Date docDate, String cashTransfer) {
		return ifundTransferInquiryDao.getFundTransferInquiryBasedonFormDetails(currencyId, locationName, docCode, docName, finYear, docNum, docDate, cashTransfer);
	}

	@Override
	@Transactional
	public List<CashDepositInquiryModelView> getAllCashDepositInquiry() {
		return ifundTransferInquiryDao.getAllCashDepositInquiry();
	}

	@Override
	@Transactional
	public List<CashDepositInquiryModelView> getCashDepositInquiryBasedonFormDetails(String cashTransfer, BigDecimal currencyId, Date fromdocDate,Date todocDate, String locationName) {
		return ifundTransferInquiryDao.getCashDepositInquiryBasedonFormDetails(cashTransfer, currencyId, fromdocDate, todocDate, locationName);
	}

}
