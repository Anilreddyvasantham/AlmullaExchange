package com.amg.exchange.enquiry.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankBranch;

public interface IBankBranchEnquiryService {

	public List<BankBranch> getBranchDetails(BigDecimal bankCountry,BigDecimal bankId);
}
