package com.amg.exchange.treasury.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.treasury.dao.IGLTransactionForADocumentDao;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IGLTransactionForADocument;
import com.amg.exchange.treasury.viewModel.GLTransactionView;
@Service
public class GLTransactionServiceImpl implements IGLTransactionForADocument {
	
	@Autowired 
	IGLTransactionForADocumentDao iglTransaionService;
	
	@Override
	@Transactional
	public List<GLTransactionView> getAllGlTransactionForADocument(
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal documentId, BigDecimal documentYear,
			BigDecimal documentNumber) {
		 
		return iglTransaionService.getAllGlTransactionForADocument(applicationCountryId, companyId, documentId, documentYear, documentNumber);
	}

	@Override
	@Transactional
	public List<Document> getAllDocumentTypeList() {
		 
		return iglTransaionService.getAllDocumentTypeList();
	}

	@Override
	@Transactional
	public String getQuoteName(BigDecimal currencyId) {
		 
		return iglTransaionService.getQuoteName(currencyId);
	}

	@Override
	@Transactional
	public BigDecimal getCustomeReference(BigDecimal customerId) {
		 
		return iglTransaionService.getCustomeReference(customerId);
	}

}
