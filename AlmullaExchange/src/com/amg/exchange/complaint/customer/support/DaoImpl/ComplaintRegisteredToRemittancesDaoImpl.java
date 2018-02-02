package com.amg.exchange.complaint.customer.support.DaoImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.complaint.customer.support.Dao.IComplaintRegisteredToRemittancesDao;
import com.amg.exchange.complaint.customer.support.model.ComplaintFollowup;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.model.CommunicationMethod;
import com.amg.exchange.complaint.model.CommunicationMethodDesc;
import com.amg.exchange.complaint.model.ComplaintActionDesc;
import com.amg.exchange.complaint.model.ComplaintAssigned;
import com.amg.exchange.complaint.model.ComplaintAssignedDesc;
import com.amg.exchange.complaint.model.ComplaintMatrix;
import com.amg.exchange.complaint.model.ComplaintRemarksDesc;
import com.amg.exchange.complaint.model.ComplaintRemarksMaster;
import com.amg.exchange.complaint.model.EmployeeAuthorization;
import com.amg.exchange.registration.model.CountryBranch;

@SuppressWarnings("rawtypes")
@Repository
public class ComplaintRegisteredToRemittancesDaoImpl<T> extends CommonDaoImpl<T> implements IComplaintRegisteredToRemittancesDao<T>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public void saveOrUpdate(ComplaintFollowup complaintFollowup) {
		// TODO Auto-generated method stub
		getSession().saveOrUpdate(complaintFollowup);
	}

	@Override
	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal countryId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal bankId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class, "complaintLog");

		String[] assignedCode = { "2", "4", "5" };

		criteria.setFetchMode("complaintLog.country", FetchMode.JOIN);
		criteria.createAlias("complaintLog.country", "country", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("country.countryId", countryId));

		criteria.setFetchMode("complaintLog.service", FetchMode.JOIN);
		criteria.createAlias("complaintLog.service", "service", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("service.serviceId", serviceId));

		criteria.setFetchMode("complaintLog.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintLog.complaintType", "complaintType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.complaintTypeId", complaintTypeId));

		criteria.setFetchMode("complaintLog.bank", FetchMode.JOIN);
		criteria.createAlias("complaintLog.bank", "bank", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bank.bankId", bankId));

		criteria.add(Restrictions.isNull("complaintLog.closedDate"));
		criteria.add(Restrictions.in("complaintLog.assignedToCode", assignedCode));

		List<ComplaintLog> objList = (List<ComplaintLog>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<CountryBranch> getCountryBranchList(BigDecimal locationCode) {
		DetachedCriteria criteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");
		criteria.add(Restrictions.eq("countryBranch.branchId", locationCode));
		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CountryBranch> objList = (List<CountryBranch>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<ComplaintMatrix> getComplaintMatrixs(BigDecimal appCountryId, BigDecimal countryId, BigDecimal serviceId, BigDecimal complaintTypeId, BigDecimal bankId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintMatrix.class, "complaintMatrix");

		criteria.setFetchMode("complaintMatrix.applicationCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.applicationCountry", "applicationCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("applicationCountry.countryId", appCountryId));

		criteria.setFetchMode("complaintMatrix.fsCountry", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.fsCountry", "fsCountry", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("fsCountry.countryId", countryId));

		criteria.setFetchMode("complaintMatrix.serviceMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));

		criteria.setFetchMode("complaintMatrix.complaintTypeMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintTypeMaster", "complaintTypeMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintTypeMaster.complaintTypeId", complaintTypeId));

		criteria.setFetchMode("complaintMatrix.bankMaster", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.bankMaster", "bankMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("bankMaster.bankId", bankId));

		criteria.setFetchMode("complaintMatrix.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintMatrix.complaintAction", "complaintAction", JoinType.INNER_JOIN);

		criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<ComplaintMatrix> objList = (List<ComplaintMatrix>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<ComplaintLog> displayRemittancesRegisteredFromComplaintLog(BigDecimal companyId, BigDecimal remittanceDocNo, BigDecimal remittanceFinancialYr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class, "complaintLog");

		String[] assignedCode = { "2", "4", "5" };

		criteria.setFetchMode("complaintLog.country", FetchMode.JOIN);
		criteria.createAlias("complaintLog.country", "country", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("country.countryId", companyId));

		criteria.setFetchMode("complaintLog.service", FetchMode.JOIN);
		criteria.createAlias("complaintLog.service", "service", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("service.serviceId", remittanceDocNo));

		criteria.setFetchMode("complaintLog.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintLog.complaintType", "complaintType", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintType.complaintTypeId", remittanceFinancialYr));

		criteria.add(Restrictions.isNull("complaintLog.closedDate"));
		criteria.add(Restrictions.in("complaintLog.assignedToCode", assignedCode));

		List<ComplaintLog> objList = (List<ComplaintLog>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;
	}

	@Override
	public List<ComplaintActionDesc> getComplaintActionDesc(BigDecimal actionId, BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintActionDesc.class, "complaintActionDesc");

		criteria.setFetchMode("complaintActionDesc.complaintAction", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.complaintAction", "complaintAction", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAction.complaintActionId", actionId));

		criteria.setFetchMode("complaintActionDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintActionDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		List<ComplaintActionDesc> objList = (List<ComplaintActionDesc>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<ComplaintRemarksDesc> getComplaintRemarksDescList(BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksDesc.class, "complaintRemarksDesc");

		criteria.setFetchMode("complaintRemarksDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintRemarksDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		List<ComplaintRemarksDesc> objList = (List<ComplaintRemarksDesc>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<ComplaintRemarksMaster> getComplaintRemarksDescCode(BigDecimal complaintRemarksCodeId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintRemarksMaster.class, "complaintRemarksMaster");

		criteria.add(Restrictions.eq("complaintRemarksMaster.complaintRemarksId", complaintRemarksCodeId));

		List<ComplaintRemarksMaster> objList = (List<ComplaintRemarksMaster>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<ComplaintAssignedDesc> getComplaintAssignedDescList(BigDecimal complaintAssignedId, BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintAssignedDesc.class, "complaintAssignedDesc");

		criteria.setFetchMode("complaintAssignedDesc.complaintAssigned", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.complaintAssigned", "complaintAssigned", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("complaintAssigned.complaintAssignedId", complaintAssignedId));

		criteria.setFetchMode("complaintAssignedDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("complaintAssignedDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		List<ComplaintAssignedDesc> objList = (List<ComplaintAssignedDesc>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}

	@Override
	public List<CommunicationMethodDesc> getCommunicationMethodDescList(BigDecimal communicationMethodId, BigDecimal languageId) {

		DetachedCriteria criteria = DetachedCriteria.forClass(CommunicationMethodDesc.class, "communicationMethodDesc");

		criteria.setFetchMode("communicationMethodDesc.communicationMethodId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.communicationMethodId", "communicationMethodId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("communicationMethodId.comMethodId", communicationMethodId));

		criteria.setFetchMode("communicationMethodDesc.languageId", FetchMode.JOIN);
		criteria.createAlias("communicationMethodDesc.languageId", "languageId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("languageId.languageId", languageId));

		List<CommunicationMethodDesc> objList = (List<CommunicationMethodDesc>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

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

		if (objList.isEmpty()) {
			return null;
		} else {
			return objList.get(0).getComplaintType();
		}
	}
	
	public List<ComplaintFollowup> getComplaintFollowUp(BigDecimal companyId, BigDecimal complaintReference, BigDecimal complaintFinancialYr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintFollowup.class, "complaintFollowup");

		criteria.setFetchMode("complaintFollowup.companyId", FetchMode.JOIN);
		criteria.createAlias("complaintFollowup.companyId", "companyId", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyId.companyId", companyId));

		criteria.add(Restrictions.eq("complaintFollowup.complaintReference", complaintReference));

		criteria.add(Restrictions.eq("complaintFollowup.complaintFinanceYear", complaintFinancialYr));

		criteria.addOrder(Order.desc("complaintFollowup.followupDate"));
/*
		ProjectionList projList = Projections.projectionList();
		projList.add(Projections.max("complaintFollowup.complaintFollowupId"));
		projList.add(Projections.groupProperty("complaintFollowup.followupDate"));
		criteria.setProjection(projList);*/
	

		List<ComplaintFollowup> objList = (List<ComplaintFollowup>) criteria.getExecutableCriteria(getSession()).list();

		System.out.println("objList.size() ======== > " + objList.size());

		if (objList.isEmpty()) {
			return null;
		} else {
			//return objList.get(0).getComplaintFollowupId();
			return objList;
		}

	}
	@Override
	public List<ComplaintFollowup> getComplaintFollowUpList(BigDecimal companyId, BigDecimal complaintReference, BigDecimal complaintFinancialYr) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintFollowup.class, "complaintFollowup");
		
		criteria.add(Restrictions.eq("complaintFollowup.complaintFollowupId", getComplaintFollowUp(companyId, complaintReference, complaintFinancialYr)));
	

		List<ComplaintFollowup> objList = (List<ComplaintFollowup>) criteria.getExecutableCriteria(getSession()).list();

		System.out.println("objList.size() ======== > " + objList.size());

		if (objList.isEmpty()) {
			return null;
		} else {
			return objList;
		}

	}
	
	@Override
	public List<ComplaintLog> searchFromComplaintLog(BigDecimal companyId, BigDecimal complaintRefernceNo,BigDecimal remittanceFinanceYear) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class,"complaintLog");
		
		
		criteria.setFetchMode("complaintLog.country", FetchMode.JOIN);
		criteria.createAlias("complaintLog.country", "country", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("complaintLog.service", FetchMode.JOIN);
		criteria.createAlias("complaintLog.service", "service", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintLog.complaintType", FetchMode.JOIN);
		criteria.createAlias("complaintLog.complaintType", "complaintType", JoinType.INNER_JOIN);

		criteria.setFetchMode("complaintLog.bank", FetchMode.JOIN);
		criteria.createAlias("complaintLog.bank", "bank", JoinType.INNER_JOIN);
		
		criteria.setFetchMode("complaintLog.companyMaster", FetchMode.JOIN);
		criteria.createAlias("complaintLog.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		criteria.add(Restrictions.eq("companyMaster.companyId", companyId));

		
		criteria.add(Restrictions.eq("complaintLog.remittanceDocumentNo", complaintRefernceNo));
		criteria.add(Restrictions.eq("complaintLog.remittanceDocumentFinancialYear", remittanceFinanceYear));

		List<ComplaintLog> objList = (List<ComplaintLog>) findAll(criteria);
		
		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}
}
