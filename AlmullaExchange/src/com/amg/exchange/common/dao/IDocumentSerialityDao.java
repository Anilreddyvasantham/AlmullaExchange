package com.amg.exchange.common.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.DocumentSeriality;

public interface IDocumentSerialityDao<T> {
	
	public void save(DocumentSeriality documentSeriality);
	
	public void delete(BigDecimal documentSeriality);

	public List<Document> getDocumentList(BigDecimal languageId);
	
	public List<UserFinancialYear> getAllUserFinancialYear(Date currentDate);
	
	public List<DocumentSeriality> getAllDocumentSerialityList();
	
	public List<Document> getDocumentCode(BigDecimal documentId);
	
	public void activateRecord(BigDecimal documentSeriality , String userName);
	
	public List<CountryMasterDesc> getCountryListAppCountry(BigDecimal languageId, BigDecimal countryId);
	
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId,BigDecimal languageId);
	
	public Boolean isExist(BigDecimal countryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal financialYear,String active);
	
	public List<DocumentSeriality> getDocumentSeriality(BigDecimal documentId);
	
	public List<Document> getDocumentCodeByDocId(BigDecimal documentId);
}
