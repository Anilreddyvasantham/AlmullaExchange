package com.amg.exchange.common.daoimpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.dao.IDocumentDao;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class DocumentDaoImpl extends CommonDaoImpl implements IDocumentDao {

	@Override
	public void saveRecords(Document document) {

		getSession().saveOrUpdate(document);
		
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<Document> getAllRecordsFromDB() {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class,"document");
		criteria.setFetchMode("document.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("document.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<Document>)findAll(criteria);
	}


	@Override
	public void delete(BigDecimal documentPk) {
		Document document=(Document)getSession().get(Document.class, documentPk);
		getSession().delete(document); 
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Document> getAllUnApprovedRecordsFromDB(){
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class,"document");
		criteria.setFetchMode("document.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("document.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("document.isActive", Constants.U));
		criteria.add(Restrictions.isNull("document.approvedBy"));
		criteria.add(Restrictions.isNull("document.approvedDate"));
		return (List<Document>)findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getAllComponent(String query) {
		String queryString = "select document_code from ex_document where document_code like '%" + query + "%'";
		return getSession().createSQLQuery(queryString).list();
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<Document> getRecordFrmDB(BigDecimal documentCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Document.class,"document");
		criteria.setFetchMode("document.fsLanguageType", FetchMode.JOIN);
		criteria.createAlias("document.fsLanguageType","fsLanguageType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("documentCode", documentCode));
		return (List<Document>)findAll(criteria);
	}


	@Override
	public String approveRecord(BigDecimal documentPk, String userName) {
		String approveMsg;
		Document documentObj=(Document) getSession().get(Document.class, documentPk);
		String approvedUser=documentObj.getApprovedBy();
		if(approvedUser==null)
		{
			documentObj.setApprovedBy(userName );
			documentObj.setApprovedDate(new Date());
			documentObj.setIsActive(Constants.Yes);
			getSession().saveOrUpdate(documentObj);
			approveMsg="Success";
		}
		else{
			approveMsg="Fail";
		}
		return  approveMsg;
	}

	
	

}
