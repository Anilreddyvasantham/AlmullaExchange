package com.amg.exchange.enquiry.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.BankBranch;

public interface IBankBranchEnquiryDao {

	public List<BankBranch> getBranchDetails(BigDecimal bankCountry,BigDecimal bankId);
}
