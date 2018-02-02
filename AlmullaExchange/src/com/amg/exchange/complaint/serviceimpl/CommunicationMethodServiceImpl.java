package com.amg.exchange.complaint.serviceimpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.common.dto.CommunicationMethodDTO;
import com.amg.exchange.complaint.dao.ICommunicationMethodDao;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.service.ICommunicationMethodService;

@Service("communicationMethodService")
public class CommunicationMethodServiceImpl implements ICommunicationMethodService{


	
	@Autowired
	ICommunicationMethodDao communicationMethodDao;

	@Override
	@Transactional
	public List<CommunicationMethod> getAllRecordsForApproval(){
	return	communicationMethodDao.getAllRecordsForApproval();
	}
	
	@Override
	@Transactional
	public List<CommunicationMethodDesc> getallRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId){
	return	communicationMethodDao.getallRecordsRelatedCommunicationMethod(communicationMethodId);
	}
	
	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveRecords(CommunicationMethod communicationMethod,CommunicationMethodDesc communicationMethodDesc1,CommunicationMethodDesc communicationMethodDesc2)throws Exception{
	communicationMethodDao.saveRecords(communicationMethod,communicationMethodDesc1,communicationMethodDesc2);
	}
	
	@Override
	@Transactional
	public String approveReocrd(BigDecimal communicationMethodPk,String userName){
	 return communicationMethodDao.approveReocrd(communicationMethodPk,userName);
	}

	@Override
	@Transactional
	public List<CommunicationMethod> viewAllRecords(){
	return communicationMethodDao.viewAllRecords();
	}
	
	@Override
	@Transactional
	public void deleteRecordPermanently(BigDecimal communicationMethodPk,BigDecimal communicationMethodDescPk,BigDecimal communicationMethodLocalDescPk){
		communicationMethodDao.deleteRecordPermanently(communicationMethodPk,communicationMethodDescPk,communicationMethodLocalDescPk);
	}
	
	@Override
	@Transactional
	public List<String> getCommunicationMethodCodeList(String query){
		return communicationMethodDao.getCommunicationMethodCodeList(query);
		
	}
	
	@Override
	@Transactional
	public List<CommunicationMethod> fetchAllRecords(String communicationMethodCode){
		return communicationMethodDao.fetchAllRecords(communicationMethodCode);
		
	}
	
	@Override
	@Transactional
	public void activateRecord(BigDecimal communicationMethodPk,String userName){
		 communicationMethodDao.activateRecord(communicationMethodPk,userName);
		
	}

	@Override
	@Transactional(rollbackFor=Exception.class)
	public void saveAllRecords(CommunicationMethodDTO communicationMethodDTOList)throws Exception {
		communicationMethodDao.saveAllRecords(communicationMethodDTOList);
		
	}

	@Override
	@Transactional
	public List<CommunicationMethodDTO> viewRecords(){
		return communicationMethodDao.viewRecords();
	}

	@Override
	@Transactional
	public List<CommunicationMethodDTO> getAllRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId){
		return communicationMethodDao.getAllRecordsRelatedCommunicationMethod(communicationMethodId);
	}

	@Override
	@Transactional
	public List<CommunicationMethodDTO> fetchCommunicationMethodCodeRecord(String communicationMethodCode){
		return communicationMethodDao.fetchCommunicationMethodCodeRecord(communicationMethodCode);
	}

@Override
@Transactional
public List<CommunicationMethodDTO> ApproveRecords() {
	  return communicationMethodDao.ApproveRecords();
}

@Override
@Transactional
public List<CommunicationMethodDTO> getAllRecordsApproveRelatedCommunicationMethod(BigDecimal communicationMethodId) {
	  return communicationMethodDao.getAllRecordsApproveRelatedCommunicationMethod(communicationMethodId);
}


}
