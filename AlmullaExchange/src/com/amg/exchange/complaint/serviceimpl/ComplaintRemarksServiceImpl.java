package com.amg.exchange.complaint.serviceimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.dao.IComplaintRemarksDao;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.service.IComplaintRemarksService;

@SuppressWarnings("serial")
@Service("complaintRemarksServiceImpl")
public class ComplaintRemarksServiceImpl<T> implements IComplaintRemarksService<T>,Serializable {

	@Autowired
	IComplaintRemarksDao<T> complaintRemarksDao;

	public ComplaintRemarksServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	public IComplaintRemarksDao<T> getComplaintRemarksDao() {
		return complaintRemarksDao;
	}

	public void setComplaintRemarksDao(IComplaintRemarksDao<T> complaintRemarksDao) {
		this.complaintRemarksDao = complaintRemarksDao;
	}

	@Override
	@Transactional
	public void saveComplaintRemarksMethod(ComplaintRemarksMaster complaintRemarksMaster) {
		getComplaintRemarksDao().saveComplaintRemarksMethod(complaintRemarksMaster);

	}

	@Override
	@Transactional
	public void saveComplaintRemarksDescMethod(ComplaintRemarksDesc complaintRemarksDesc) {
		getComplaintRemarksDao().saveComplaintRemarksDescMethod(complaintRemarksDesc);

	}

	@Override
	@Transactional
	public void activateComplaintRemarksMethod(BigDecimal complaintRemarksId, String userName) {
		getComplaintRemarksDao().activateComplaintRemarksMethod(complaintRemarksId, userName);

	}

	@Override
	@Transactional
	public List<ComplaintRemarksDesc> getAllComplaintRemarksDesc(BigDecimal ComplaintRemarksId) {
		return getComplaintRemarksDao().getAllComplaintRemarksDesc(ComplaintRemarksId);
	}

	@Override
	@Transactional
	public List<ComplaintRemarksMaster> getAllComplaintRemarks(BigDecimal appCountryId) {
		return getComplaintRemarksDao().getAllComplaintRemarks(appCountryId);
	}

	@Override
	@Transactional
	public List<ComplaintRemarksMaster> getComplaintRemarksForApprove(BigDecimal appCountryId, String inActive) {
		return getComplaintRemarksDao().getComplaintRemarksForApprove(appCountryId, inActive);
	}

	@Override
	@Transactional
	public Boolean isComplaintCodeExist(String complaintCode) {
		return getComplaintRemarksDao().isComplaintCodeExist(complaintCode);
	}

	@Override
	@Transactional
	public void approveRecord(BigDecimal complaintRemarksId, String userName, String isActive) {
		getComplaintRemarksDao().approveRecord(complaintRemarksId, userName, isActive);

	}

	@Override
	@Transactional
	public void deleteComplaintRemarksMethod(BigDecimal complaintRemarksId, BigDecimal englishDescId, BigDecimal arabicDescId) {
		getComplaintRemarksDao().deleteComplaintRemarksMethod(complaintRemarksId, englishDescId, arabicDescId);

	}

	@Override
	@Transactional
	public List<String> autoCompleteList(String query) {
		return getComplaintRemarksDao().autoCompleteList(query);
	}

	@Override
	@Transactional
	public List<ComplaintRemarksMaster> getComplaintRemarksBasedOnComplaintCode(String complaintCode) {
		return getComplaintRemarksDao().getComplaintRemarksBasedOnComplaintCode(complaintCode);
	}

	@Override
	@Transactional
	public List<ComplaintRemarksDesc> getAllComplaintRemarksForComplaintCreation(BigDecimal countryId, BigDecimal languageId) {
		// TODO Auto-generated method stub
		return getComplaintRemarksDao().getAllComplaintRemarksForComplaintCreation(countryId, languageId);
	}

}
