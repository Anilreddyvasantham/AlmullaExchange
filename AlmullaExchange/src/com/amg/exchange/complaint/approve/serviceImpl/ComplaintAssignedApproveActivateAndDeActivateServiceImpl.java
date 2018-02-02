package com.amg.exchange.complaint.approve.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;
import com.amg.exchange.complaint.approve.dao.IComplaintAssignedApproveActivateAndDeActivateDao;
import com.amg.exchange.complaint.approve.service.IComplaintAssignedApproveActivateAndDeActivateService;

@Service("complaintAssignedApproveService")
public class ComplaintAssignedApproveActivateAndDeActivateServiceImpl implements IComplaintAssignedApproveActivateAndDeActivateService {

	  @Autowired
	  IComplaintAssignedApproveActivateAndDeActivateDao complaintAssignedApproveActivateAndDeActivateDao;

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterToView() {
		    return complaintAssignedApproveActivateAndDeActivateDao.displayAllComplaintAssinedMasterToView();
	  }
	  
	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterFromDesc(BigDecimal complaintAssignedId) {
		    return complaintAssignedApproveActivateAndDeActivateDao.displayAllComplaintAssinedMasterFromDesc(complaintAssignedId);
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterForApproval() {
		    return complaintAssignedApproveActivateAndDeActivateDao.displayAllComplaintAssinedMasterForApproval();
	  }

	  @Override
	  @Transactional
	  public List<ComplaintAssignedDTO> displayComplaintAssinedMasterAllFieldForApproval(BigDecimal complaintAssignedId) {
		    return complaintAssignedApproveActivateAndDeActivateDao.displayComplaintAssinedMasterAllFieldForApproval(complaintAssignedId);
	  }
	  
	  @Override
	  @Transactional(rollbackFor=Exception.class)
	  public void UpdateComplaintAssinedMaster(ComplaintAssignedDTO complaintAssignedDTO)throws Exception {
		    complaintAssignedApproveActivateAndDeActivateDao.UpdateComplaintAssinedMaster(complaintAssignedDTO);
		    
	  }

	  @Override
	  @Transactional
	  public String checkComplaintAssinedMasterApproveMultiUser(BigDecimal complaintAssignedId, String userName) {
		    return complaintAssignedApproveActivateAndDeActivateDao.checkComplaintAssinedMasterApproveMultiUser(complaintAssignedId,userName);
	  }

	  @Override
	  @Transactional
	  public void deActivateComplaintAssinedMaster(BigDecimal complaintAssignedId, String userName) {
		    complaintAssignedApproveActivateAndDeActivateDao.deActivateComplaintAssinedMaster(complaintAssignedId,userName);
	  }

	  @Override
	  @Transactional
	  public void deleteComplaintAssinedMaster(BigDecimal complaintAssignedId, BigDecimal complaintEnglishFullDescriptionPK, BigDecimal complaintArabicFullDescriptionPK) {
		    complaintAssignedApproveActivateAndDeActivateDao.deleteComplaintAssinedMaster(complaintAssignedId,complaintEnglishFullDescriptionPK,complaintArabicFullDescriptionPK); 
	  }
	  
}
