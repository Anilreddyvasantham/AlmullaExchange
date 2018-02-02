package com.amg.exchange.treasury.daoimpl;

import java.math.BigDecimal;
import java.util.List;


import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.treasury.dao.IGLTransactionForADocumentDao;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.viewModel.GLTransactionView;
import com.amg.exchange.util.Constants;
 
@SuppressWarnings("rawtypes")
@Repository
public class GLTransactionForADocumentDaoImpl extends CommonDaoImpl implements
		IGLTransactionForADocumentDao {

	@Override
	public List<GLTransactionView> getAllGlTransactionForADocument(
			BigDecimal applicationCountryId, BigDecimal companyId,
			BigDecimal documentId, BigDecimal documentYear,
			BigDecimal documentNumber) {
		DetachedCriteria criteria = DetachedCriteria.forClass(GLTransactionView.class,"glTransactionView");
		
		criteria.add(Restrictions.eq("glTransactionView.applicationCountryId", applicationCountryId));
		criteria.add(Restrictions.eq("glTransactionView.companyId", companyId));
		criteria.add(Restrictions.eq("glTransactionView.documentId", documentId));
		criteria.add(Restrictions.eq("glTransactionView.documentFinancialYear",documentYear));
		criteria.add(Restrictions.eq("glTransactionView.documentNo", documentNumber));
		
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		 return (List<GLTransactionView>)findAll(criteria);
	}

	@Override
	public List<Document> getAllDocumentTypeList() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class,"document");
		criteria.setFetchMode("document.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("document.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		//criteria.add(Restrictions.eq("isActive",Constants.Yes));
		return (List<Document>)findAll(criteria);
		 
	}

	@Override
	public String getQuoteName(BigDecimal currencyId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CurrencyMaster.class,"currencyMaster"); 
		criteria.add(Restrictions.eq("currencyMaster.currencyId", currencyId));
		List<CurrencyMaster> data = (List<CurrencyMaster>) findAll(criteria);
		return data.get(0).getQuoteName();
		 
	}
	
	
	@Override
	public BigDecimal getCustomeReference(BigDecimal customerId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "fscustomer");
		
		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList= (List<Customer>)findAll(criteria);
		return idproofList.get(0).getCustomerReference();
	}
	

}
