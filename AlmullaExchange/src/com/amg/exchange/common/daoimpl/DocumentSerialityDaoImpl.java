package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IDocumentSerialityDao;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.DocumentSeriality;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class DocumentSerialityDaoImpl<T> extends CommonDaoImpl<T> implements IDocumentSerialityDao<T> {

	@Override
	public void save(DocumentSeriality documentSeriality) {

		getSession().saveOrUpdate(documentSeriality);
	}

	@Override
	public List<Document> getDocumentList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");

		criteria.setFetchMode("document.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("document.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		criteria.addOrder(Order.asc("document.documentCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> objList = (List<Document>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	/*public List<UserFinancialYear> getUserFinancialYear(Date currentDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		 if(currentDate!=null){
			
			 detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", currentDate))
					 .add(Restrictions.ge("userFinancialYear.financialYearEnd", currentDate));
			 
			 detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
			
		 }
		 //log.info("UserFinancialYear Dao end");
			 
			return (List<UserFinancialYear>) findAll(detachedCriteria);
		}*/
	@Override
	public List<UserFinancialYear> getAllUserFinancialYear(Date currentDate) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");

		DateFormat formatter = new SimpleDateFormat("dd/MM/yy");
		try {
			Date S = formatter.parse(formatter.format(currentDate));
			System.out.println("Today : " + S);

			if(currentDate!=null){

				detachedCriteria.add(Restrictions.le("userFinancialYear.financialYearBegin", S))
				.add(Restrictions.ge("userFinancialYear.financialYearEnd", S));

			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		return (List<UserFinancialYear>) findAll(detachedCriteria);
	}

	@Override
	public List<DocumentSeriality> getAllDocumentSerialityList() {

		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentSeriality.class, "documentSeriality");

		criteria.addOrder(Order.desc("documentSeriality.documentSerialityId"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DocumentSeriality> objList = (List<DocumentSeriality>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<Document> getDocumentCode(BigDecimal documentId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");

		criteria.add(Restrictions.eq("document.documentCode", documentId));

		criteria.addOrder(Order.asc("document.documentCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> objList = (List<Document>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public void delete(BigDecimal documentSeriality) {

		DocumentSeriality document = (DocumentSeriality) getSession().get(DocumentSeriality.class, documentSeriality);
		getSession().delete(document);

	}

	@Override
	public void activateRecord(BigDecimal documentSeriality, String userName) {
		DocumentSeriality document = (DocumentSeriality) getSession().get(DocumentSeriality.class, documentSeriality);
		document.setIsActive(Constants.U);
		document.setModifiedBy(userName);
		document.setModifiedDate(new Date());
		document.setApprovedBy(null);
		document.setApprovedDate(null);
		document.setRemarks(null);
		getSession().update(document);

	}

	@Override
	public List<CountryMasterDesc> getCountryListAppCountry(BigDecimal languageId, BigDecimal countryId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");

		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);

		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));

		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		detachedCriteria.addOrder(Order.asc("fsCountryMaster.fsCountryMasterDescs"));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CountryMasterDesc> objList = (List<CountryMasterDesc>) findAll(detachedCriteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<CompanyMasterDesc> getCompanyList(BigDecimal companyId, BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CompanyMasterDesc.class, "companyMasterDesc");
		criteria.setFetchMode("companyMasterDesc.fsCompanyMaster", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsCompanyMaster", "fsCompanyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCompanyMaster.companyId", companyId));
		criteria.setFetchMode("companyMasterDesc.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("companyMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		criteria.addOrder(Order.asc("companyMasterDesc.companyName"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<CompanyMasterDesc> objList = (List<CompanyMasterDesc>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public Boolean isExist(BigDecimal countryId, BigDecimal companyId, BigDecimal documentCode, BigDecimal financialYear,String active) {

		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentSeriality.class, "documentSeriality");

		criteria.setFetchMode("documentSeriality.docCountry", FetchMode.JOIN);
		criteria.createAlias("documentSeriality.docCountry", "docCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("docCountry.countryId", countryId));
		
		criteria.setFetchMode("documentSeriality.docCompany", FetchMode.JOIN);
		criteria.createAlias("documentSeriality.docCompany", "docCompany", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("docCompany.companyId", companyId));
		
		criteria.add(Restrictions.eq("documentSeriality.docFinancialYear", financialYear));
		criteria.add(Restrictions.eq("documentSeriality.exDocument", documentCode));
	//	criteria.add(Restrictions.eq("documentSeriality.isActive", active));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DocumentSeriality> objList = (List<DocumentSeriality>) findAll(criteria);
		
		if (objList.isEmpty())
			return false;

		return true;

	}
	
	@Override
	public List<DocumentSeriality> getDocumentSeriality(BigDecimal documentId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(DocumentSeriality.class, "document");

		criteria.add(Restrictions.eq("document.exDocument", documentId));

		//criteria.addOrder(Order.asc("document.documentCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<DocumentSeriality> objList = (List<DocumentSeriality>) findAll(criteria);

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<Document> getDocumentCodeByDocId(BigDecimal documentId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class, "document");

		criteria.add(Restrictions.eq("document.documentID", documentId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<Document> objList = (List<Document>) findAll(criteria);

		return objList;
	}

}
