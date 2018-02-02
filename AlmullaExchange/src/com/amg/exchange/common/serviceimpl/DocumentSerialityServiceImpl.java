package com.amg.exchange.common.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IDocumentSerialityDao;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IDocumentSerialityService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.DocumentSeriality;

@SuppressWarnings("serial")
@Service("documentSerialityServiceImpl")
public class DocumentSerialityServiceImpl<T> implements IDocumentSerialityService<T>, Serializable {

	@Autowired
	IDocumentSerialityDao<T> documentSerialityDao;

	public IDocumentSerialityDao<T> getDocumentSerialityDao() {
		return documentSerialityDao;
	}

	public void setDocumentSerialityDao(IDocumentSerialityDao<T> documentSerialityDao) {
		this.documentSerialityDao = documentSerialityDao;
	}

	@Transactional
	@Override
	public void save(DocumentSeriality documentSeriality) {
		getDocumentSerialityDao().save(documentSeriality);

	}

	@Transactional
	@Override
	public void delete(BigDecimal documentSeriality) {
		getDocumentSerialityDao().delete(documentSeriality);
	}

	@Transactional
	@Override
	public List<Document> getDocumentList(BigDecimal languageId) {
		return getDocumentSerialityDao().getDocumentList(languageId);
	}

	@Override
	@Transactional
	public List<UserFinancialYear> getAllUserFinancialYear(Date currentDate) {
		return getDocumentSerialityDao().getAllUserFinancialYear(currentDate);
	}

	@Override
	@Transactional
	public List<DocumentSeriality> getAllDocumentSerialityList() {
		return getDocumentSerialityDao().getAllDocumentSerialityList();
	}

	@Override
	@Transactional
	public List<Document> getDocumentCode(BigDecimal documentId) {
		return getDocumentSerialityDao().getDocumentCode(documentId);
	}

	@Override
	@Transactional
	public void activateRecord(BigDecimal documentSeriality, String userName) {
		getDocumentSerialityDao().activateRecord(documentSeriality, userName);
	}

	@Override
	@Transactional
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId, BigDecimal languageId) {

		return getDocumentSerialityDao().getCompanyList(companyId, languageId);
	}

	@Transactional
	@Override
	public List<CountryMasterDesc> getCountryListAppCountry(BigDecimal languageId, BigDecimal countryId) {

		return getDocumentSerialityDao().getCountryListAppCountry(languageId, countryId);
	}
	@Transactional
	@Override
	public Boolean isExist(BigDecimal countryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal financialYear,String active){
		
		return getDocumentSerialityDao().isExist(countryId, companyId, documentCode, financialYear,active);
	}
	
	@Override
	@Transactional
	public List<DocumentSeriality> getDocumentSeriality(BigDecimal documentId) {
		// TODO Auto-generated method stub
		return getDocumentSerialityDao().getDocumentSeriality(documentId);//(countryId, companyId, documentCode, financialYear,active);
	}

	@Override
	@Transactional
	public List<Document> getDocumentCodeByDocId(BigDecimal documentId) {
		return getDocumentSerialityDao().getDocumentCodeByDocId(documentId);
	}
}
