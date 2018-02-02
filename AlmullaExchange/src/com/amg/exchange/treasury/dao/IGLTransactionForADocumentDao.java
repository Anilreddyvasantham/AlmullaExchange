package com.amg.exchange.treasury.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.viewModel.GLTransactionView;

public interface IGLTransactionForADocumentDao {
	
	
	public List<GLTransactionView> getAllGlTransactionForADocument(
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal documentIds, BigDecimal documentYear,
			BigDecimal documentNumber);
	public List<Document> getAllDocumentTypeList() ;
	public String getQuoteName(BigDecimal currencyId);
	public BigDecimal getCustomeReference(BigDecimal customerId);

}
