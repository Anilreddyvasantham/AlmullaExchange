package com.amg.exchange.remittance.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.remittance.dao.IRelationsDao;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.service.IRelationsTypeService;

@Service("ralationsService")
public class RelationsTypeServiceImpl implements IRelationsTypeService {
	@Autowired
	IRelationsDao relationsDao;

	@Override
	@Transactional
	public void saveRecord(Relations relations) {
		relationsDao.saveRecord(relations);
	}

	@Override
	@Transactional
	public void saveRecordForDesc(RelationsDescription relationsDescription) {
		relationsDao.saveRecordForDesc(relationsDescription);
	}

	@Override
	@Transactional
	public List<Relations> getRealtion(String relationid) {
		return relationsDao.getRealtion(relationid);
	}

	@Override
	@Transactional
	public List<Relations> getRelationList(String relationsCode) {
		return relationsDao.getRelationList(relationsCode);
	}

	@Override
	@Transactional
	public List<RelationsDescription> getRelationsDescriptionList(
			BigDecimal relationsId) {
		return relationsDao.getRelationsDescriptionList(relationsId);
	}

	@Override
	@Transactional
	public List<Relations> getAllComponent(String query) {
		return relationsDao.getAllComponent(query);
	}

	@Override
	@Transactional
	public List<RelationsDescription> getAllRelationList() {
		return relationsDao.getAllRelationList();
	}

	@Override
	@Transactional
	public String getEngRelation(BigDecimal relationsId) {
		return relationsDao.getEngRelation(relationsId);
	}

	@Override
	@Transactional
	public String getArabicRelation(BigDecimal ArabicId) {
		return relationsDao.getArabicRelation(ArabicId);
	}

	@Override
	@Transactional
	public List<Relations> getRelationListApproval() {
		return relationsDao.getRelationListApproval();
	}

	@Override
	@Transactional
	public String getCreatedName() {
		return relationsDao.getCreatedBy();
	}

	@Override
@Transactional
	public List<RelationsDescription> getAllActiveInActive(String englishDesc) {
		return relationsDao.getAllActiveInActive(englishDesc);
	}

	@Override
	@Transactional
	public void delete(Relations relation) {
		 relationsDao.delete(relation);
		
	}

	@Override
	@Transactional
	public void delete(RelationsDescription relationsdesc) {
		 relationsDao.delete(relationsdesc);
		
	}

	@Override
	@Transactional
	public List<RelationsDescription> getCheckRelationCode(String relationsCode) {
		 
		return relationsDao.getCheckRelationCode(relationsCode);
	}

	@Override
	@Transactional
	public String approveRecord(BigDecimal relationPk, String userName) {
		 
		return relationsDao.approveRecord(relationPk,userName);
	}

	@Override
	@Transactional
	public List<Relations> getAllRecords() {
		return relationsDao.getAllRecords();
	}

}
