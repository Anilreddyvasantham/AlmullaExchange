package com.amg.exchange.reprint.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.RemittanceApplicationView;


public interface IReprintDao {

	List<RemittanceApplicationView> getRecordsRemittanceReceipt(BigDecimal documentNumber, BigDecimal documentYear, BigDecimal applicationCountryId);

	List<RemittanceApplicationView> getRemittancefromCollectionNumber(BigDecimal transactionNo, BigDecimal rEMITTANCE_DOCUMENT_CODE, BigDecimal documentYear, BigDecimal applicationCountryId);

}