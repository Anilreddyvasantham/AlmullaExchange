package com.amg.exchange.complaint.service;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.common.dto.CommunicationMethodDTO;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;

public interface ICommunicationMethodService {

	public List<CommunicationMethod> getAllRecordsForApproval();
	public List<CommunicationMethodDesc> getallRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId);
	public void saveRecords(CommunicationMethod communicationMethod,CommunicationMethodDesc communicationMethodDesc1,CommunicationMethodDesc communicationMethodDesc2)throws Exception;
	public String approveReocrd(BigDecimal communicationMethodPk,String userName);
	public List<CommunicationMethod> viewAllRecords();
	public void deleteRecordPermanently(BigDecimal communicationMethodPk,BigDecimal communicationMethodDescPk,BigDecimal communicationMethodLocalDescPk);
	public List<String> getCommunicationMethodCodeList(String query);
	public List<CommunicationMethod> fetchAllRecords(String communicationMethodCode);
	public void activateRecord(BigDecimal communicationMethodPk,String userName);
	
	public void saveAllRecords(CommunicationMethodDTO communicationMethodDTOList)throws Exception;
	
	public List<CommunicationMethodDTO> viewRecords();
	public List<CommunicationMethodDTO> getAllRecordsRelatedCommunicationMethod(BigDecimal communicationMethodId);
	
	public List<CommunicationMethodDTO> fetchCommunicationMethodCodeRecord(String communicationMethodCode);
        public List<CommunicationMethodDTO> ApproveRecords();
public List<CommunicationMethodDTO> getAllRecordsApproveRelatedCommunicationMethod(BigDecimal communicationMethodId);
}
