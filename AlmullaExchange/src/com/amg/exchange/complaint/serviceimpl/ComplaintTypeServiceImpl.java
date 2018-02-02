package com.amg.exchange.complaint.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.dao.IComplaintTypeDao;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.ComplaintTypeMaster;
import com.amg.exchange.complaint.service.IComplaintTypeService;

@SuppressWarnings("serial")
@Service("complaintTypeServiceImpl")
public class ComplaintTypeServiceImpl<T> implements IComplaintTypeService<T>,Serializable {

	@Autowired
	IComplaintTypeDao<T> complaintTypeDao;

	public IComplaintTypeDao<T> getComplaintTypeDao() {
		return complaintTypeDao;
	}

	public void setComplaintTypeDao(IComplaintTypeDao<T> complaintTypeDao) {
		this.complaintTypeDao = complaintTypeDao;
	}

	@Override
	@Transactional
	public void saveAndUpdateComplaintType(ComplaintTypeMaster complaintTypeMaster) {
		getComplaintTypeDao().saveAndUpdateComplaintType(complaintTypeMaster);
	}

	@Override
	@Transactional
	public void saveAndUpdateComplaintTypeDesc(ComplaintTypeDesc complaintTypeDesc) {
		getComplaintTypeDao().saveAndUpdateComplaintTypeDesc(complaintTypeDesc);
	}
	
	@Override
	@Transactional
	public Boolean isComplaintTypeCodeExist(String complaintCode){
		return getComplaintTypeDao().isComplaintTypeCodeExist(complaintCode);
	}
	
	@Override
	@Transactional
	public List<ComplaintTypeDesc> displayAllComplaintTypeDesc( BigDecimal ComplaintTypeId){
		return getComplaintTypeDao().displayAllComplaintTypeDesc(ComplaintTypeId);
	}
	@Override
	@Transactional
	public List<ComplaintTypeMaster> displayAllComplaintTypeToView(BigDecimal appCountryId){
		return getComplaintTypeDao().displayAllComplaintTypeToView(appCountryId);
	}
	@Override
	@Transactional
	public void activateComplaintType(BigDecimal complaintTypeId, String userName){
		getComplaintTypeDao().activateComplaintType(complaintTypeId, userName);
	}
	@Override
	@Transactional
	public void deleteComplaintType(BigDecimal complaintTypeId, BigDecimal englishDescId, BigDecimal arabicDescId){
		getComplaintTypeDao().deleteComplaintType(complaintTypeId, englishDescId, arabicDescId);
	}
	@Override
	@Transactional
	public List<String> autoCompleteList(String query){
		return getComplaintTypeDao().autoCompleteList(query);
	}
	@Override
	@Transactional
	public List<ComplaintTypeMaster> displayComplaintTypeForApprove(BigDecimal appCountryId,String inActive){
		return getComplaintTypeDao().displayComplaintTypeForApprove(appCountryId, inActive);
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal complaintTypeId, String userName,String isActive){
		getComplaintTypeDao().approveRecord(complaintTypeId, userName, isActive);
	}
	
	@Override
	@Transactional
	public List<ComplaintTypeMaster> displayComplaintTypeBasedOnComplaintTypeCode(String complaintCode){
		return getComplaintTypeDao().displayComplaintTypeBasedOnComplaintTypeCode(complaintCode);
	}

	@Override
	@Transactional
	public List<ComplaintTypeDesc> displayComplaintTypeForComplaintCreation(BigDecimal appCountryId, BigDecimal languageId) {
		return getComplaintTypeDao().displayComplaintTypeForComplaintCreation(appCountryId, languageId);
	}

}
