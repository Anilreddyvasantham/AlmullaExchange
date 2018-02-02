package com.amg.exchange.complaint.customer.support.DaoImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.complaint.customer.support.Dao.IComplaintSummaryDao;
import com.amg.exchange.complaint.customer.support.model.ComplaintLog;
import com.amg.exchange.complaint.customer.support.model.ComplaintSummary;
import com.amg.exchange.complaint.model.ComplaintTypeDesc;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.ServiceMasterDesc;
import com.amg.exchange.util.CommonUtil;
import com.amg.exchange.util.Constants;

@SuppressWarnings("rawtypes")
@Repository
public class ComplaintSummaryDaoImpl<T> extends CommonDaoImpl<T> implements
		IComplaintSummaryDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<ComplaintSummary> displayComplaintSummary() {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintSummary.class,"complaintSummary");
		
		List<ComplaintSummary> objList = (List<ComplaintSummary>) findAll(criteria);

		System.out.println("" + objList.size());

		if (objList.isEmpty())
			return null;

		return objList;

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintLog> getTotalComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class,"complaintLog");
		
		criteria.add(Restrictions.eq("complaintLog.country.countryId", countryId));
		criteria.add(Restrictions.eq("complaintLog.service.serviceId", serviceId));
		criteria.add(Restrictions.eq("complaintLog.bank.bankId", bankId));
		criteria.add(Restrictions.eq("complaintLog.complaintType.complaintTypeId", complaintTypeId));
		
		criteria.add(Restrictions.isNull("complaintLog.closedDate"));
		List<String>  assignedTolist = new ArrayList<String>();
		assignedTolist.add(Constants.ASSIGNED_TO_CUSTOMER_SUPPORT_SERVICES);
		assignedTolist.add(Constants.ASSIGNED_TO_BANK);
		assignedTolist.add(Constants.ASSIGNED_TO_AGENT);	      
		criteria.add(Restrictions.in("complaintLog.assignedToCode", assignedTolist));	
		
		/*criteria.add((Criterion) Projections.groupProperty("complaintSummary.countryId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.serviceId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.bankId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.complaintTypeId"));*/
		
		return (List<ComplaintLog>) findAll(criteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComplaintLog> getPendingComplaints(BigDecimal countryId, BigDecimal serviceId, BigDecimal bankId, BigDecimal complaintTypeId){
		
		DetachedCriteria criteria = DetachedCriteria.forClass(ComplaintLog.class,"complaintLog");
		
		criteria.add(Restrictions.eq("complaintLog.country.countryId", countryId));
		criteria.add(Restrictions.eq("complaintLog.service.serviceId", serviceId));
		criteria.add(Restrictions.eq("complaintLog.bank.bankId", bankId));
		criteria.add(Restrictions.eq("complaintLog.complaintType.complaintTypeId", complaintTypeId));
		
		criteria.add(Restrictions.isNull("complaintLog.closedDate"));		
		criteria.add(Restrictions.not(Restrictions.in("complaintLog.assignedToCode",new String[] { Constants.ASSIGNED_TO_BANK, Constants.ASSIGNED_TO_AGENT})));
	    
		
		/*criteria.add((Criterion) Projections.groupProperty("complaintSummary.countryId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.serviceId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.bankId"));
		criteria.add((Criterion) Projections.groupProperty("complaintSummary.complaintTypeId"));*/
		
		
		return (List<ComplaintLog>) findAll(criteria);
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
	
	@Override
	public String getCountryName(BigDecimal countryId,BigDecimal languageId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryMasterDesc.class, "countryMasterDesc");
		
		dCriteria.setFetchMode("countryMasterDesc.fsLanguageType", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsLanguageType", "fsLanguageType", JoinType.INNER_JOIN);
		// Add Language Condition
		dCriteria.add(Restrictions.eq("fsLanguageType.languageId", languageId));
		dCriteria.setFetchMode("countryMasterDesc.fsCountryMaster", FetchMode.JOIN);
		dCriteria.createAlias("countryMasterDesc.fsCountryMaster", "fsCountryMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("fsCountryMaster.countryId", countryId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<CountryMasterDesc> objList = (List<CountryMasterDesc>) dCriteria.getExecutableCriteria(getSession()).list();
		
		if(objList.isEmpty()){
			return null;
		}else{
			return objList.get(0).getCountryName();
		}
		
		
	}
	
	@Override
	public String getComplaintTypeDesc(BigDecimal complaintTypeDescId,BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ComplaintTypeDesc.class, "complaintTypeDesc");
		
		dCriteria.setFetchMode("complaintTypeDesc.languageId", FetchMode.JOIN);
		dCriteria.createAlias("complaintTypeDesc.languageId", "languageId", JoinType.INNER_JOIN);
		// Add Language Condition
		dCriteria.add(Restrictions.eq("languageId.languageId", languageId));
		dCriteria.setFetchMode("complaintTypeDesc.complaintType", FetchMode.JOIN);
		dCriteria.createAlias("complaintTypeDesc.complaintType", "complaintType", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("complaintType.complaintTypeId", complaintTypeDescId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ComplaintTypeDesc> objList = (List<ComplaintTypeDesc>) dCriteria.getExecutableCriteria(getSession()).list();
		
		if(objList.isEmpty()){
			return null;
		}else{
			return objList.get(0).getFullDesc();
		}
		
		
	}
	
	@Override
	public String getServiceDesc(BigDecimal serviceId,BigDecimal languageId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(ServiceMasterDesc.class, "serviceMasterDesc");
		
		dCriteria.setFetchMode("serviceMasterDesc.languageType", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.languageType", "languageType", JoinType.INNER_JOIN);
		// Add Language Condition
		dCriteria.add(Restrictions.eq("languageType.languageId", languageId));
		dCriteria.setFetchMode("serviceMasterDesc.serviceMaster", FetchMode.JOIN);
		dCriteria.createAlias("serviceMasterDesc.serviceMaster", "serviceMaster", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.eq("serviceMaster.serviceId", serviceId));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		List<ServiceMasterDesc> objList = (List<ServiceMasterDesc>) dCriteria.getExecutableCriteria(getSession()).list();
		
		if(objList.isEmpty()){
			return null;
		}else{
			return objList.get(0).getLocalServiceDescription();
		}
		
		
	}


	@SuppressWarnings("unchecked")
	@Override
	public List<UserFinancialYear> getAllDocumentYear() {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(UserFinancialYear.class, "userFinancialYear");
		Calendar now = Calendar.getInstance();
		BigDecimal curryr = new BigDecimal(now.get(Calendar.YEAR));
		BigDecimal  prevYear = new BigDecimal(now.get(Calendar.YEAR)-1);
		
		Criterion criteria1=Restrictions.eq("userFinancialYear.financialYear" , curryr);
		Criterion criteria2=Restrictions.eq("userFinancialYear.financialYear",prevYear);
		LogicalExpression orExp = Restrictions.or(criteria1, criteria2);
		detachedCriteria.add( orExp );
		detachedCriteria.addOrder(Order.desc("userFinancialYear.financialYear"));
		List<UserFinancialYear> objectList=(List<UserFinancialYear>)findAll(detachedCriteria);
 
		 if(objectList.size()>0){
			return objectList ;
		 }else{
			 return objectList;
		 }
	
	}
	
}
