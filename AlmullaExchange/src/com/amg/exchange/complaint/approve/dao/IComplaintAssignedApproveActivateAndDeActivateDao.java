package com.amg.exchange.complaint.approve.dao;

import java.math.BigDecimal;
import java.util.List;

import com.amg.exchange.complaint.DTO.ComplaintAssignedDTO;

public interface IComplaintAssignedApproveActivateAndDeActivateDao {
		
		public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterToView();
		
		public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterFromDesc(BigDecimal complaintAssignedId);
		
		public List<ComplaintAssignedDTO> displayAllComplaintAssinedMasterForApproval();
		
		public List<ComplaintAssignedDTO> displayComplaintAssinedMasterAllFieldForApproval(BigDecimal complaintAssignedId);
		
		public void UpdateComplaintAssinedMaster(ComplaintAssignedDTO complaintAssignedDTO)throws Exception;
		
		public String checkComplaintAssinedMasterApproveMultiUser(BigDecimal complaintAssignedId, String userName);
		
		public void deActivateComplaintAssinedMaster(BigDecimal complaintAssignedId, String userName);
		
		public void deleteComplaintAssinedMaster(BigDecimal complaintAssignedId, BigDecimal complaintEnglishFullDescriptionPK, BigDecimal complaintArabicFullDescriptionPK);
		
}
