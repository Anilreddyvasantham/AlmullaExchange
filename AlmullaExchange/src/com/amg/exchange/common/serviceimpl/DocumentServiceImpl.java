package com.amg.exchange.common.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dao.IDocumentDao;
import com.amg.exchange.common.service.IDocumentService;
 
import com.amg.exchange.treasury.model.Document;
@Service
@Transactional
public class DocumentServiceImpl implements IDocumentService {
	
	@Autowired
	IDocumentDao idocumentdao;

	@Override
	public void saveRecords(Document document) {
		 
		idocumentdao.saveRecords(document);
	}

	@Override
	public List<Document> getAllRecordsFromDB() {
		 
		return idocumentdao.getAllRecordsFromDB();
	}

	@Override
	public void delete(BigDecimal documentPk) {
		
		idocumentdao.delete(documentPk);
		
	}

	@Override
	public List<Document> getAllUnApprovedRecordsFromDB() {
		return idocumentdao.getAllUnApprovedRecordsFromDB();
		
	}
	@Override
	public List<String> getAllComponent(String query) {
		return idocumentdao.getAllComponent(query);
	}

	@Override
	public List<Document> getRecordFrmDB(BigDecimal documentCode) {
		 
		return idocumentdao.getRecordFrmDB(documentCode);
	}

	@Override
	public String approveRecord(BigDecimal documentPk, String userName) {
		 
		return idocumentdao.approveRecord(documentPk,userName);
	}

	

}
