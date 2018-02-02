package com.amg.exchange.treasury.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.viewModel.GLTransactionView;

public interface IGLTransactionForADocument {
	
	public List<GLTransactionView> getAllGlTransactionForADocument(BigDecimal applicationCountryId,BigDecimal companyId,BigDecimal documentId,BigDecimal documentYear,BigDecimal documentNumber);
	//public List<> getAllGlTransactionForADocument(BigDecimal applicationCountryId,BigDecimal companyCode,BigDecimal documentCode,BigDecimal documentYear,BigDecimal documentNumber);
	public List<Document> getAllDocumentTypeList();
	public String getQuoteName(BigDecimal currencyId);
	public BigDecimal getCustomeReference(BigDecimal customerId);
	 
	
	
	

}
