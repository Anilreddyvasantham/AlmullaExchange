package com.amg.exchange.complaint.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.complaint.DTO.ComplaintMatrixDTO;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.dao.IComplaintDao;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.ComplaintRemittanceViewModel;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.complaint.model.EmployeeAuthorization;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.stoppayment.model.RemittanceComplaint;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;

@SuppressWarnings({"serial" ,"unchecked"})
@Repository
public class ComplaintDaoImpl<T> extends CommonDaoImpl<T> implements IComplaintDao<T>, Serializable {

	public List<ComplaintTypeDesc> getComplaintTypeList(BigDecimal languageId){

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintTypeDesc.class, "complaintTypeDesc");

		criteria.setFetchMode("complaintTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintTypeDesc.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.complaintType", "complaintType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.isActive", Constants.Yes));

		criteria.addOrder(Order.asc("complaintType.complaintTypeCode"));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);		

		return (List<ComplaintTypeDesc>) findAll(criteria);
	}


	@Override
	public void saveComplaintMatrix(ComplaintMatrix complaintMatrix) {

		try {
			saveOrUpdate((T) complaintMatrix);
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			System.out.println("Procedure Exception===================="+ e.getMessage());
		}
	}


	@Override
	public List<CommunicationMethodDesc> getCommunicationMethodList(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethodDesc.class, "communicationMethodDesc");


		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("communicationMethodId.isActive", Constants.Yes));
		
		criteria.addOrder(Order.asc("communicationMethodId.comMethodCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		return (List<CommunicationMethodDesc>) findAll(criteria);
	}

	@Override
	public List<ComplaintAssignedDesc> getComplaintAssignedList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintAssignedDesc.class, "complaintAssignedDesc");

		criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAssigned.isActive", Constants.Yes));
		
		criteria.addOrder(Order.asc("complaintAssigned.complaintAssignedCode"));

		return (List<ComplaintAssignedDesc>) findAll(criteria);
	}

	@Override
	public List<ComplaintActionDesc> getComplaintActionList(BigDecimal languageId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintActionDesc.class, "complaintActionDesc");

		criteria.setFetchMode("complaintActionDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintActionDesc.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.complaintAction", "complaintAction", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.isActive", Constants.Yes));
		criteria.add(Restrictions.eq("complaintAction.actionGroup", Constants.COMPLAINT_ACTION_GROUP_OPEN));
		
		criteria.addOrder(Order.asc("complaintAction.complaintActionCode"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);


		return (List<ComplaintActionDesc>) findAll(criteria);
	}

	@Override
	public List<ComplaintMatrix> getComplaintMatrixList(BigDecimal countryId,BigDecimal bankId, BigDecimal setrviceId,BigDecimal appCountryId,BigDecimal complaintTypeId){

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");
		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountry.countryId", countryId));

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", appCountryId));

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", setrviceId));

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeId", complaintTypeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ComplaintMatrix> lstComplaintMatrix=(List<ComplaintMatrix>) findAll(criteria);
		return lstComplaintMatrix;

	}


	@Override
	public List<ComplaintRemittanceViewModel> getComplaintRemittanceForComplaintCreation(BigDecimal companyId, BigDecimal remittanceYearId,BigDecimal remittanceDocNum) {

		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintRemittanceViewModel.class,"complaintRemittanceViewModel");

		criteria.add(Restrictions.eq("complaintRemittanceViewModel.companyId", companyId));
		criteria.add(Restrictions.eq("complaintRemittanceViewModel.documentFinanceYear", remittanceYearId));
		criteria.add(Restrictions.eq("complaintRemittanceViewModel.documentNo", remittanceDocNum));

		List<ComplaintRemittanceViewModel> lstdata = (List<ComplaintRemittanceViewModel>) findAll(criteria);

		return lstdata;
	}

	@Override
	public List<ComplaintMatrix> getCheckForDbForRecord(BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintTakenById, BigDecimal complaintActionId, BigDecimal countryId2) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");
		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountry.countryId", countryId));

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", countryId2));

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeId", complaintTypeId));

		criteria.setFetchMode("complaintMatrix.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId", complaintTakenById));

		//criteria.setFetchMode("complaintMatrix.complaintDestinationId", FetchMode.JOIN);
		//criteria.createAlias("complaintMatrix.complaintDestinationId", "complaintDestinationId", JoinType.INNER_JOIN);


		criteria.setFetchMode("complaintMatrix.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAction", "complaintAction", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.complaintActionId", complaintActionId));

		//criteria.setFetchMode("complaintMatrix.communicationMethod", FetchMode.JOIN);
		//criteria.createAlias("complaintMatrix.communicationMethod", "communicationMethod", JoinType.INNER_JOIN);


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ComplaintMatrix> lstComplaintMatrix=(List<ComplaintMatrix>) findAll(criteria);
		return lstComplaintMatrix;
	}	

	@Override
	public List<ComplaintMatrixDTO> getViewAllRecorsFromDb() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");
		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintDestinationId", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintDestinationId", "complaintDestinationId", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAction", "complaintAction", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.communicationMethod", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.communicationMethod", "communicationMethod", JoinType.INNER_JOIN);

		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("complaintMatrixId"), "compalintMatrixPk");
		columns.add(Projections.property("applicationCountry.countryId"), "appCountryId");
		columns.add(Projections.property("complaintTypeMaster.complaintTypeId"), "complaintTypeId");
		columns.add(Projections.property("fsCountry.countryId"), "countryId");
		columns.add(Projections.property("bankMaster.bankId"), "bankId");
		columns.add(Projections.property("serviceMaster.serviceId"), "serviceId");
		columns.add(Projections.property("complaintAction.complaintActionId"), "complaintActionId");
		columns.add(Projections.property("complaintAssigned.complaintAssignedId"), "complaintTakenById");
		columns.add(Projections.property("complaintDestinationId.complaintAssignedId"), "complaintDestionId");
		columns.add(Projections.property("communicationMethod.comMethodId"), "communicationMethodId");
		columns.add(Projections.property("isActive"), "isActive");
		columns.add(Projections.property("createdBy"), "createdBy");
		columns.add(Projections.property("createdDate"), "createdDate");
		columns.add(Projections.property("modifiedBy"), "modifiedBy");
		columns.add(Projections.property("modifiedDate"), "modifiedDate");
		columns.add(Projections.property("approvedBy"), "approvedBy");
		columns.add(Projections.property("approvedDate"), "approvedDate");
		columns.add(Projections.property("remarks"), "remarks");

		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintMatrixDTO.class) );


		List<ComplaintMatrixDTO> lstComplaintMatrixDto=(List<ComplaintMatrixDTO>) findAll(criteria);
		return lstComplaintMatrixDto; 
	}


	@Override
	public List<ComplaintAssignedDesc> getComplaintAssignedDesc(BigDecimal languageId, BigDecimal complaintDestionId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssignedDesc.class,"complaintAssignedDesc");
		criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId", complaintDestionId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintAssignedDesc> lstComplaintAssignedDesc=(List<ComplaintAssignedDesc>) findAll(criteria);
		return lstComplaintAssignedDesc;
	}


	@Override
	public List<ComplaintTypeDesc> getComplaintTypeDescDb(BigDecimal languageId, BigDecimal complaintTypeId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintTypeDesc.class,"complaintTypeDesc");
		criteria.setFetchMode("complaintTypeDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintTypeDesc.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintTypeDesc.complaintType", "complaintType",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.complaintTypeId", complaintTypeId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintTypeDesc> lstComplaintTypeDesc=(List<ComplaintTypeDesc>) findAll(criteria);
		return lstComplaintTypeDesc;
	}


	@Override
	public List<CommunicationMethodDesc> getCommunicationMethodDescDb(BigDecimal languageId, BigDecimal communicationMethodId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(CommunicationMethodDesc.class,"communicationMethodDesc");
		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("communicationMethodId.comMethodId", communicationMethodId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CommunicationMethodDesc> lstCommunicationMethodDesc=(List<CommunicationMethodDesc>) findAll(criteria);
		return lstCommunicationMethodDesc;
	}


	@Override
	public List<ComplaintActionDesc> getCompalintDescDb(BigDecimal languageId, BigDecimal complaintActionId) {
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintActionDesc.class,"complaintActionDesc");
		criteria.setFetchMode("complaintActionDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.languageId", "languageId",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		criteria.setFetchMode("complaintActionDesc.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.complaintAction", "complaintAction",JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.complaintActionId", complaintActionId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintActionDesc> lstComplaintActionDesc=(List<ComplaintActionDesc>) findAll(criteria);
		return lstComplaintActionDesc;
	}


	@Override
	public void deActivateRecord(BigDecimal complaintMatrixPk, String userName) {
		ComplaintMatrix complaintMatrix=(ComplaintMatrix) getSession().get(ComplaintMatrix.class, complaintMatrixPk);
		complaintMatrix.setIsActive(Constants.U);
		complaintMatrix.setModifiedBy(userName);
		complaintMatrix.setModifiedDate(new Date());
		complaintMatrix.setApprovedBy(null);
		complaintMatrix.setApprovedDate(null);
		complaintMatrix.setRemarks(null);
		getSession().saveOrUpdate(complaintMatrix);

	}


	@Override
	public void deleteRecordPermentely(BigDecimal complaintMatrixPk) {
		ComplaintMatrix complaintMatrix=(ComplaintMatrix) getSession().get(ComplaintMatrix.class, complaintMatrixPk);
		getSession().delete(complaintMatrix);
	}


	@Override
	public List<ComplaintMatrixDTO> displayAllComplaintMatrixMasterForApproval() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");
		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintDestinationId", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintDestinationId", "complaintDestinationId", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAction", "complaintAction", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintMatrix.communicationMethod", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.communicationMethod", "communicationMethod", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("complaintMatrix.isActive", Constants.U));

		ProjectionList columns = Projections.projectionList();
		columns.add(Projections.property("complaintMatrixId"), "compalintMatrixPk");
		columns.add(Projections.property("applicationCountry.countryId"), "appCountryId");
		columns.add(Projections.property("complaintTypeMaster.complaintTypeId"), "complaintTypeId");
		columns.add(Projections.property("fsCountry.countryId"), "countryId");
		columns.add(Projections.property("bankMaster.bankId"), "bankId");
		columns.add(Projections.property("serviceMaster.serviceId"), "serviceId");
		columns.add(Projections.property("complaintAction.complaintActionId"), "complaintActionId");
		columns.add(Projections.property("complaintAssigned.complaintAssignedId"), "complaintTakenById");
		columns.add(Projections.property("complaintDestinationId.complaintAssignedId"), "complaintDestionId");
		columns.add(Projections.property("communicationMethod.comMethodId"), "communicationMethodId");
		columns.add(Projections.property("isActive"), "isActive");
		columns.add(Projections.property("createdBy"), "createdBy");
		columns.add(Projections.property("createdDate"), "createdDate");
		columns.add(Projections.property("modifiedBy"), "modifiedBy");
		columns.add(Projections.property("modifiedDate"), "modifiedDate");
		columns.add(Projections.property("approvedBy"), "approvedBy");
		columns.add(Projections.property("approvedDate"), "approvedDate");
		columns.add(Projections.property("remarks"), "remarks");

		criteria.setProjection(columns);
		criteria.setResultTransformer( new AliasToBeanResultTransformer(ComplaintMatrixDTO.class) );


		List<ComplaintMatrixDTO> lstComplaintMatrixDto=(List<ComplaintMatrixDTO>) findAll(criteria);
		return lstComplaintMatrixDto;
	}


	@Override
	public String checkComplaintMatrixMasterApproveMultiUser(BigDecimal complaintMatrixPk, String userName) {
		String approvalMsg;
		ComplaintMatrix complaintMatrix= (ComplaintMatrix) getSession().get(ComplaintMatrix.class, complaintMatrixPk);
		String approvalUser=complaintMatrix.getApprovedBy();
		if(approvalUser==null){
			complaintMatrix.setIsActive(Constants.Yes);
			complaintMatrix.setApprovedBy(userName);
			complaintMatrix.setApprovedDate(new Date());
			getSession().update(complaintMatrix);
			approvalMsg="Success";
		}else{
			approvalMsg="Fail";      
		}
		return approvalMsg;
	}


	@Override
	public void saveComplaintLog(ComplaintLog complaintLog) {
		getSession().saveOrUpdate(complaintLog);  
	}


	@Override
	public List<ComplaintRemarksMaster> getComplaintRemarksCodeBasedOnId(BigDecimal remarksCodeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");
		criteria.add(Restrictions.eq("complaintRemarksMaster.complaintRemarksId", remarksCodeId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintRemarksMaster> lstComplaintRemarksMaster=(List<ComplaintRemarksMaster>) findAll(criteria);
		return lstComplaintRemarksMaster;
	}


	@Override
	public List<CountryBranch> getCountryBranchCodeBasedOnBranchId(BigDecimal locationId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.countryBranchId", locationId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> lstCountryBranch=(List<CountryBranch>) findAll(criteria);
		return lstCountryBranch;
	}


	@Override
	public List<ComplaintLog> getDuplicateCHeckForCompalintCreation(BigDecimal countryId, BigDecimal remitdealYear, BigDecimal remitdealReference, BigDecimal companyId, Date closedDate) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class, "complaintLog");
		//application country
		criteria.setFetchMode("complaintLog.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintLog.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", countryId));
		//Remittance Deal year
		criteria.add(Restrictions.eq("complaintLog.remittanceDocumentFinancialYear", remitdealYear));
		//Remittance Doc number
		criteria.add(Restrictions.eq("complaintLog.remittanceDocumentNo", remitdealReference));
		// Company Code
		criteria.setFetchMode("complaintLog.remittanceCompany", FetchMode.JOIN);
		criteria.createAlias("complaintLog.remittanceCompany", "remittanceCompany", JoinType.INNER_JOIN); 
		criteria.add(Restrictions.eq("remittanceCompany.companyId", companyId));
		//Closed Date
		criteria.add(Restrictions.isNull("complaintLog.closedDate"));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintLog> lstComplaintLog=(List<ComplaintLog>) findAll(criteria);
		return lstComplaintLog;
	}


	@Override
	public List<BankApplicability> getCorrespondentBankList(BigDecimal countryId, BigDecimal applicationCountryId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankApplicability.class, "bankApplicability");

		criteria.setFetchMode("bankApplicability.bankMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankMaster", "bankMaster", JoinType.INNER_JOIN);



		criteria.add(Restrictions.eq("bankMaster.fsCountryMaster.countryId", countryId));

		criteria.setFetchMode("bankApplicability.bankInd", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.bankInd", "bankInd", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankInd.bankIndicatorId", new BigDecimal(103)));

		criteria.setFetchMode("bankApplicability.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankApplicability.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", applicationCountryId));
		//Remittance Deal year
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankApplicability> bankApplicabilityList=(List<BankApplicability>) findAll(criteria);
		return bankApplicabilityList;
	}


	@Override
	public List<CountryMasterDesc> getBusinessCountryList(BigDecimal languageId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		// Join Language Type table
		detachedCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add Language Condition
		detachedCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		// Add Bussness of country Condition
		detachedCriteria.add(Restrictions.eq("fsCountryMaster.countryActive", Constants.Yes));
		//detachedCriteria.add(Restrictions.eq("fsCountryMaster.businessCountry", Constants.Yes));
		// Join Country Master Table
		detachedCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		detachedCriteria.addOrder(Order.asc("countryMasterDesc.countryName"));

		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		//detachedCriteria.getExecutableCriteria(getSession()).setCacheable(true);

		return (List<CountryMasterDesc>) findAll(detachedCriteria);
	}

	@Override
	public String basedonCountryandBankTogetEmail(BigDecimal countryId, BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(BankMaster.class, "bankMaster");

		criteria.setFetchMode("bankMaster.fsCountryMaster", FetchMode.JOIN);
		criteria.createAlias("bankMaster.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<BankMaster> lstBankMaster=(List<BankMaster>) findAll(criteria);
		return lstBankMaster.get(0).getEmail();
	}


	@Override
	public String toCheckComplaintTypeFromUserProfile(BigDecimal employeeId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(EmployeeAuthorization.class, "employeeAuthorization");

		criteria.setFetchMode("employeeAuthorization.fsEmployee", FetchMode.JOIN);
		criteria.createAlias("employeeAuthorization.fsEmployee", "fsEmployee", JoinType.INNER_JOIN);

		criteria.add(Restrictions.eq("fsEmployee.employeeId", employeeId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<EmployeeAuthorization> objList = (List<EmployeeAuthorization>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty()){
			return null; 
		}else{
			return objList.get(0).getComplaintType();	  
		}


		/*	

	List<String> employeeList=(List<String>) findAll(criteria);

			if(CommonUtil.checkSizeOfRecords(employeeList)){
				return null;
			}else{
				return ((EmployeeAuthorization) criteria.getExecutableCriteria(getSession()).list().get(0)).getComplaintType();
			}*/

	}


	@Override
	public List<ComplaintMatrix> toFetchBasedOnTheseCombination(BigDecimal applicationId, BigDecimal countryId, BigDecimal bankId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal complaintAssignedId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");
		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountry.countryId", countryId));

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", applicationId));

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeId", complaintTypeId));

		criteria.setFetchMode("complaintMatrix.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId", complaintAssignedId));


		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);

		List<ComplaintMatrix> lstComplaintMatrix=(List<ComplaintMatrix>) findAll(criteria);
		return lstComplaintMatrix;

	}


	@Override
	public String tofetchEmployeeIdBasedOnEmployeeCode(BigDecimal employeeId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Employee.class, "employee");
		criteria.add(Restrictions.eq("employee.employeeId", employeeId));
		List<Employee> lstEmployee=(List<Employee>) findAll(criteria);
		return lstEmployee.get(0).getEmail();
	}

	@Override
	public Boolean emailcheck(BigDecimal complaintMethodId) {
		// TODO Auto-generated method stub
		boolean checkemail = false;
		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethod.class, "communicationMethod");

		criteria.add(Restrictions.eq("communicationMethod.comMethodId", complaintMethodId));
		criteria.add(Restrictions.eq("communicationMethod.email", Constants.Yes));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CommunicationMethod> objList = (List<CommunicationMethod>) findAll(criteria);

		if(objList.size() != 0){
			checkemail = true; 
		}else{
			checkemail = false; 
		}


		return checkemail;
	}


	@Override
	public void updateRemitComplaintProblemStatus(BigDecimal remitDocNumber) {
		// TODO Auto-generated method stub
		BigDecimal lstofRemitDocNumber = remitDocNumber;

		List<RemittanceComplaint> lstDocNumPK = fetchRemitComplaintPK(lstofRemitDocNumber);
		if(lstDocNumPK.size() != 0 ){
			RemittanceComplaint remiComplaint = (RemittanceComplaint) getSession().get(RemittanceComplaint.class, lstDocNumPK.get(0).getRemittanceComplaintId());
			remiComplaint.setProblemStatus(Constants.E);

			getSession().update(remiComplaint);
		}

	}

	public List<RemittanceComplaint> fetchRemitComplaintPK(BigDecimal remiDocNo){
		DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "RemittanceComplaint");

		criteria.add(Restrictions.eq("RemittanceComplaint.documentNo", remiDocNo));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<RemittanceComplaint> objList = (List<RemittanceComplaint>) findAll(criteria);

		return objList;
	}


	@Override
	public void updateAssignToAssignDate(BigDecimal complaintRef , BigDecimal assignTo , String assignToCode , Date assignDate) {
		// TODO Auto-generated method stub

		List<ComplaintLog> lstDocNumPK = fetchComplaintLogPK(complaintRef);
		if(lstDocNumPK.size() != 0 ){
			ComplaintLog complaintlog = (ComplaintLog) getSession().get(ComplaintLog.class, lstDocNumPK.get(0).getComplaintLogId());
			ComplaintAssigned complaintAssigned = new ComplaintAssigned();
			complaintAssigned.setComplaintAssignedId(assignTo);
			complaintlog.setAssignedToId(complaintAssigned);
			complaintlog.setAssignedToCode(assignToCode);
			complaintlog.setAssignedDate(assignDate);
			
			getSession().update(complaintlog);
		}
	}
	
	public List<ComplaintLog> fetchComplaintLogPK(BigDecimal complaintRef){
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class, "complaintLog");

		criteria.add(Restrictions.eq("complaintLog.complaintReference", complaintRef));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintLog> objList = (List<ComplaintLog>) findAll(criteria);

		return objList;
	}
	 @Override
	        public String tofetchDocumentIdBasedOnDocumentCode(BigDecimal remittanceDocNo) {
	        	  DetachedCriteria criteria = DetachedCriteria.forClass(RemittanceComplaint.class, "RemittanceComplaint");
	        
	        		criteria.add(Restrictions.eq("RemittanceComplaint.documentNo", remittanceDocNo));
	        
	        		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
	        		List<RemittanceComplaint> lstRemittanceComplaint = (List<RemittanceComplaint>) findAll(criteria);
	        
	        		return lstRemittanceComplaint.get(0).getRemittanceComplaintId().toPlainString();
	        }


	        @Override
	        public void updateRemiTransctionPrijectStatus(BigDecimal remittanceDocNo, String userName) {
	        	  RemittanceComplaint remittanceComplaint=(RemittanceComplaint) getSession().get(RemittanceComplaint.class,remittanceDocNo);
	        	  remittanceComplaint.setProblemStatus("E");
	        	  remittanceComplaint.setModifiedBy(userName);
	        	  remittanceComplaint.setModifiedDate(new Date());
	        	  getSession().saveOrUpdate(remittanceComplaint);
	        }
	        
	public Boolean isRemittanceStatus(BigDecimal remarksId,
			BigDecimal documentNo) {

		String remitanceStatus = null;
		String problemStatus = null;

		DetachedCriteria criteria = DetachedCriteria.forClass(
				ComplaintRemarksMaster.class, "complaintRemarks");
		boolean status = false;
		criteria.add(Restrictions.eq("complaintRemarks.complaintRemarksId",
				remarksId));

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		// List<>remitanceStatus = ((ComplaintRemarksMaster)
		// criteria.getExecutableCriteria(getSession()).list().get(0)).getTagRemittance();

		List<ComplaintRemarksMaster> remarksList = (List<ComplaintRemarksMaster>) findAll(criteria);

		DetachedCriteria criteria1 = DetachedCriteria.forClass(
				RemittanceComplaint.class, "remittanceComplaint");
		criteria1.add(Restrictions.eq("remittanceComplaint.documentNo",
				documentNo));
		List<RemittanceComplaint> remittanceComplaintList = (List<RemittanceComplaint>) findAll(criteria1);
		// problemStatus = ((RemittanceComplaint)
		// criteria.getExecutableCriteria(getSession()).list().get(0)).getProblemStatus();

		if (remarksList.size() != 0) {
			remitanceStatus = remarksList.get(0).getTagRemittance();
		}
		if (remittanceComplaintList.size() != 0) {
			problemStatus = remittanceComplaintList.get(0).getProblemStatus();
		}
		
		System.out.println("remitanceStatus="+remitanceStatus+"problemStatus="+problemStatus);

		if (remitanceStatus.equals(Constants.Yes) && (problemStatus == null)) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinancialYear> getAllDocumentYear() {
		DetachedCriteria criteria=DetachedCriteria.forClass(UserFinancialYear.class,"userFinancialYear");
		
		criteria.addOrder(Order.desc("userFinancialYear.financialYear"));
		
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		return (List<UserFinancialYear>) criteria.getExecutableCriteria(getSession()).setMaxResults(3).list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintLog> getComplaintRemittance(BigDecimal complaintReference){
		
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintLog.class,"complaintLog");
		criteria.add(Restrictions.eq("complaintLog.complaintReference", complaintReference));
		
		List<ComplaintLog> objList = (List<ComplaintLog>) findAll(criteria);
		
		return objList;

		
	}
	
	@Override
	public void updateCloseComplaint(ComplaintLog complaintLog) {

		try {
			saveOrUpdate((T) complaintLog);
		} catch (Exception e) {
			getSession().getTransaction().rollback();
			System.out.println("Procedure Exception===================="+ e.getMessage());
		}
	}
	
	public void saveComplaintFollowup(ComplaintFollowup complaintFollowup) {
		// TODO Auto-generated method stub
		saveOrUpdate((T) complaintFollowup);

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintAssigned> getComplaintAssigned(BigDecimal complaintAssignedId){
		
		DetachedCriteria criteria=DetachedCriteria.forClass(ComplaintAssigned.class,"complaintAssigned");
		criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId", complaintAssignedId));
		
		List<ComplaintAssigned> objList = (List<ComplaintAssigned>) findAll(criteria);
		
		return objList;

		
	}
	@Override
	public String getMobileBasedOnCustomerId(BigDecimal customerId) {
		String mobileNo = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class,
				"fscustomer");

		criteria.add(Restrictions.eq("fscustomer.customerId", customerId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<Customer> idproofList = (List<Customer>) findAll(criteria);

		if (idproofList.size() != 0) {
			Customer custData = idproofList.get(0);
			String mobile = custData.getMobile() == null ? "" : custData
					.getMobile();
			
			mobileNo = mobile;
		}

		return mobileNo;
	}
	@Override
	public String getBranchNameBasedOnBranchCode(BigDecimal branchId) {
		String branchName = null;
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class,
				"countryBranch");

		criteria.add(Restrictions.eq("countryBranch.branchId", branchId));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> idproofList = (List<CountryBranch>) findAll(criteria);

		if (idproofList.size() != 0) {
			CountryBranch branchData = idproofList.get(0);
			String branch = branchData.getBranchName() == null ? "" : branchData.getBranchName();
			
			branchName = branch;
		}

		return branchName;
	}

}