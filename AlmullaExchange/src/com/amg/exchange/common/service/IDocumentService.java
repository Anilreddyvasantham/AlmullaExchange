package com.amg.exchange.common.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.treasury.model.Document;

public interface IDocumentService {
	
	public void saveRecords(Document document);
	public List<Document> getAllRecordsFromDB();
	public void delete(BigDecimal document);
	public List<Document> getAllUnApprovedRecordsFromDB();
	public List<String> getAllComponent(String query);
	public List<Document> getRecordFrmDB(BigDecimal documentCode);
	public String approveRecord(BigDecimal documentPk,String userName);
	
	
	
}
