package com.amg.exchange.remittance.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;

public interface IRelationsDao {
	public void saveRecord(Relations relations);

	public void saveRecordForDesc(RelationsDescription relationsDescription);

	public List<Relations> getRealtion(String relationid);

	/* added to populate the DB Values added @Koti 20/02/2015* */
	public List<Relations> getRelationList(String relationsCode);

	public List<RelationsDescription> getRelationsDescriptionList(BigDecimal relationsId);

	public List<Relations> getAllComponent(String query);

	public List<RelationsDescription> getAllRelationList();

	public List<Relations> getRelationListApproval();

	public String getEngRelation(BigDecimal relationsId);

	public String getArabicRelation(BigDecimal arabicId);

	public String getCreatedBy();

	public List<RelationsDescription> getAllActiveInActive(String englishDesc);

	public void delete(Relations relation);

	public void delete(RelationsDescription relationsdesc);

	public List<RelationsDescription> getCheckRelationCode(String relationsCode);

	public String approveRecord(BigDecimal relationPk, String userName);

	public List<Relations> getAllRecords();
}
