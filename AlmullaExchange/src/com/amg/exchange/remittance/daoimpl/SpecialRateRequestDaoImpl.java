package com.amg.exchange.remittance.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.dao.ISpecialRateRequestDao;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.util.Constants;

@Repository
public class SpecialRateRequestDaoImpl<T> extends CommonDaoImpl<T> implements ISpecialRateRequestDao<T>, Serializable {

	  private static final long serialVersionUID = 1L;

	  @Override
	  public void updateRecords(SpecialRateRequest specialRateRequest) {

		    getSession().update(specialRateRequest);
	  }

	  @SuppressWarnings("unchecked")
	  @Override
	  public List<SpecialRateRequest> getSpecialRateRequestList() {

		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");

		    detachedCriteria.setFetchMode("specialRateRequest.fsBankMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.fsBankMaster", "fsBankMaster", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("specialRateRequest.fsCurrencyMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("specialRateRequest.fsFinanceYear", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.fsFinanceYear", "fsFinanceYear", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("specialRateRequest.companyMaster", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("specialRateRequest.fsCustomer", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		    detachedCriteria.setFetchMode("specialRateRequest.fsDocument", FetchMode.JOIN);
		    detachedCriteria.createAlias("specialRateRequest.fsDocument", "fsDocument", JoinType.INNER_JOIN);

		    detachedCriteria.add(Restrictions.isNull("specialRateRequest.sellRate"));
		    detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationCompanyId"));
		    detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationDocumentId"));
		    detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationFinanceYear"));
		    detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationDocumentNo"));

		    detachedCriteria.add(Restrictions.eq("specialRateRequest.isActive", Constants.U));
		    
		    Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			Date fromDate = calendar.getTime();
			System.out.println(fromDate);

			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			Date toDate = calendar.getTime();
			System.out.println(toDate);
			
			detachedCriteria.add(Restrictions.between("specialRateRequest.createdDate", fromDate, toDate));
		    
		    List<SpecialRateRequest> lstSpecialRateRequests = (List<SpecialRateRequest>) findAll(detachedCriteria);
		    
		    
		    return lstSpecialRateRequests;

	  }

	  @Override
	  public Boolean isSpotRate(BigDecimal documentNo) {

		    DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");
		    detachedCriteria.add(Restrictions.eq("specialRateRequest.documentNo", documentNo));
		    detachedCriteria.add(Restrictions.isNotNull("specialRateRequest.sellRate"));

		    List<SpecialRateRequest> sellRateList = (List<SpecialRateRequest>) findAll(detachedCriteria);
		    if (sellRateList.size() <= 0) {
			      return false;
		    } else {
			      return true;
		    }
	  }

	  @Override
	  public String getNextDocumentSerialNumber(int countryId, int companyId, int documentId, int financialYear, String processIn) {
		    int out = 0;
		    /*
		     * String a=null; String b=null; SQLQuery sqlQuery =
		     * super.getSession().createSQLQuery(
		     * " { call DP_FS_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?)}  "
		     * ); sqlQuery.setInteger(0, countryId);
		     * sqlQuery.setInteger(1, companyId); sqlQuery.setInteger(2,
		     * documentId); sqlQuery.setInteger(3, financialYear);
		     * sqlQuery.setString(4, processIn);
		     * sqlQuery.setParameter(5, out); sqlQuery.setParameter(6,
		     * ""); sqlQuery.setParameter(7, "");
		     * 
		     * sqlQuery.executeUpdate();
		     * 
		     * log.info("getNextDocumentSerialNumber  output :"+out+":a:"
		     * +a+":b:"+b);
		     */

		    Connection connection = null;
		    try {
			      // connection = DBConnection.getdataconnection();
			      connection = getDataSourceFromHibernateSession();
		    } catch (Exception e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
		    }
		    CallableStatement cs;
		    try {
			      cs = connection.prepareCall(" { call EX_TO_GEN_NEXT_DOC_SERIAL_NO(?,?,?,?,?,?,?,?)}");
			      cs.setInt(1, countryId);
			      cs.setInt(2, companyId);
			      cs.setInt(3, documentId);
			      cs.setInt(4, financialYear);
			      cs.setString(5, processIn);
			      cs.registerOutParameter(6, java.sql.Types.INTEGER);
			      cs.registerOutParameter(7, java.sql.Types.VARCHAR);
			      cs.registerOutParameter(8, java.sql.Types.VARCHAR);
			      // cs.executeUpdate();
			      cs.execute();
			      out = cs.getInt(6);
			      String a = cs.getString(7);
			      String b = cs.getString(8);
			      connection.close();
		    } catch (SQLException e) {
			      // TODO Auto-generated catch block
			      e.printStackTrace();
		    }

		    return String.valueOf(out);
	  }

	  @Override
	  @SuppressWarnings("unchecked")
	  public List<SpecialRateRequest> toFetchAllDetialsFromSpecialRateReq(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId) {

		  SimpleDateFormat formate = new SimpleDateFormat("dd-MM-yyyy");
		  Date currentDate = null;
		  try {
			  currentDate = formate.parse(createdDate);
			  System.out.println("current Date : " +currentDate);
		  } catch (ParseException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }

		  String hql = "from SpecialRateRequest as specialRateRequest where to_date(to_char(specialRateRequest.createdDate, 'dd-MM-yyyy'), 'dd-MM-yyyy') = :currentDate"
				  + " and specialRateRequest.fsCustomer.customerId = :customerId"
				  + " and specialRateRequest.fcAmount = :fcAmount"
				  + " and specialRateRequest.beneficiaryId = :beneficiaryId"
				  + " and specialRateRequest.beneficiaryBankId = :beneficiaryBankId";
				  //+ " and specialRateRequest.sellRate is not null";
		  Query query = getSession().createQuery(hql); 
		  if(currentDate != null){
			  query.setParameter("currentDate", currentDate);
		  }
		  query.setParameter("customerId", customerId);
		  query.setParameter("fcAmount", fcAmount); 
		  query.setParameter("beneficiaryId", beneficiaryId);
		  query.setParameter("beneficiaryBankId", beneficiaryBankId); 
		  List<SpecialRateRequest> lstSpecialRateRequests = query.list();

		  return lstSpecialRateRequests;
	  }

	@Override
	@SuppressWarnings("unchecked")
	public List<SpecialRateRequest> toFetchSpotRate(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId) {
		
		
		/*String hql = "from SpecialRateRequest as specialRateRequest where trunc(specialRateRequest.createdDate) = trunc(sysdate) "
				+ " and specialRateRequest.fsCustomer.customerId = :customerId"
				+ " and specialRateRequest.fcAmount = :fcAmount"
				+ " and specialRateRequest.beneficiaryId = :beneficiaryId"
				+ " and specialRateRequest.applicationCompanyId is null"
				+ " and specialRateRequest.applicationDocumentId is null" 
				+ " and specialRateRequest.applicationDocumentNo is null"
				+ " and specialRateRequest.applicationFinanceYear is null"
				+ " and specialRateRequest.isActive = :pisActive"
				+ " and specialRateRequest.beneficiaryBankId = :beneficiaryBankId";
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("fcAmount", fcAmount); 
		query.setParameter("beneficiaryId", beneficiaryId);
		query.setParameter("beneficiaryBankId", beneficiaryBankId); 
		query.setParameter("pisActive", Constants.Yes); 
		List<SpecialRateRequest> lstSpecialRateRequests = query.list();*/
		
		List<SpecialRateRequest> lstSpecialRateRequests =getActiveSpecialRateRequest(customerId, fcAmount, createdDate, beneficiaryId, beneficiaryBankId);
		
		List<SpecialRateRequest> lstSpecialRateRequestsRtn = new ArrayList<SpecialRateRequest>();
		
		if(lstSpecialRateRequests.size()>0)
		{
			SpecialRateRequest specialRateRequest=lstSpecialRateRequests.get(0);
			BigDecimal specialRateRequestId= specialRateRequest.getSpecialRateRequestId();
			SpecialRateRequest specialRateRequest1 = (SpecialRateRequest) getSession().get(SpecialRateRequest.class, specialRateRequestId);
			specialRateRequest1.setIsActive(Constants.D);
			getSession().update(specialRateRequest1);
			
			lstSpecialRateRequestsRtn =getActiveSpecialRateRequest(customerId, fcAmount, createdDate, beneficiaryId, beneficiaryBankId);
		}else
		{
			lstSpecialRateRequestsRtn.clear();
			lstSpecialRateRequestsRtn.addAll(lstSpecialRateRequests);
		}
		
		

		return lstSpecialRateRequestsRtn;
	}

	@Override
	public List<SpecialRateRequest> getActiveSpecialRateRequest(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId)
	{
		 
		String hql = "from SpecialRateRequest as specialRateRequest where trunc(specialRateRequest.createdDate) = trunc(sysdate) "
				+ " and specialRateRequest.fsCustomer.customerId = :customerId"
				+ " and specialRateRequest.fcAmount = :fcAmount"
				+ " and specialRateRequest.isActive in (:pisActive,:pisActive1)"
				+ " and specialRateRequest.beneficiaryBankId = :beneficiaryBankId"
				+ " and specialRateRequest.beneficiaryId = :beneficiaryId"
				+ " and specialRateRequest.applicationCompanyId is null"
				+ " and specialRateRequest.applicationDocumentId is null" 
				+ " and specialRateRequest.applicationDocumentNo is null"
				+ " and specialRateRequest.applicationFinanceYear is null"
	;
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("fcAmount", fcAmount); 
		query.setParameter("beneficiaryId", beneficiaryId);
		query.setParameter("beneficiaryBankId", beneficiaryBankId); 
		query.setParameter("pisActive", Constants.Yes); 
		query.setParameter("pisActive1", Constants.U); 
		List<SpecialRateRequest> lstSpecialRateRequests = query.list();
		
		return lstSpecialRateRequests;
	}
	
	
/*	@Override
	public List<SpecialRateRequest> getActiveSpecialRateRequest(BigDecimal customerId, BigDecimal fcAmount, String createdDate, BigDecimal beneficiaryId, BigDecimal beneficiaryBankId)
	{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");
		detachedCriteria.add(Restrictions.eq("specialRateRequest.fcAmount", fcAmount));
		detachedCriteria.add(Restrictions.eq("specialRateRequest.fsCustomer.customerId", customerId));
		detachedCriteria.add(Restrictions.eq("specialRateRequest.beneficiaryId", beneficiaryId));
		detachedCriteria.add(Restrictions.eq("specialRateRequest.beneficiaryBankId", beneficiaryBankId));
		detachedCriteria.add(Restrictions.eq("specialRateRequest.isActive", Constants.Yes));
		detachedCriteria.add(Restrictions.isNotNull("specialRateRequest.companyMaster.companyId") );
		detachedCriteria.add(Restrictions.isNotNull("specialRateRequest.fsFinanceYear.financialYear") );
		detachedCriteria.add(Restrictions.isNotNull("specialRateRequest.") );
		detachedCriteria.add(Restrictions.isNotNull("") );
		
		
		
		
		String hql = "from SpecialRateRequest as specialRateRequest where trunc(specialRateRequest.createdDate) = trunc(sysdate) "
				+ " and specialRateRequest.fsCustomer.customerId = :customerId"
				+ " and specialRateRequest.fcAmount = :fcAmount"
				+ " and specialRateRequest.beneficiaryId = :beneficiaryId"
				+ " and specialRateRequest.applicationCompanyId is null"
				+ " and specialRateRequest.applicationDocumentId is null" 
				+ " and specialRateRequest.applicationDocumentNo is null"
				+ " and specialRateRequest.applicationFinanceYear is null"
				+ " and specialRateRequest.isActive = :pisActive"
				+ " and specialRateRequest.beneficiaryBankId = :beneficiaryBankId";
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("fcAmount", fcAmount); 
		query.setParameter("beneficiaryId", beneficiaryId);
		query.setParameter("beneficiaryBankId", beneficiaryBankId); 
		query.setParameter("pisActive", Constants.Yes); 
		List<SpecialRateRequest> lstSpecialRateRequests = query.list();
		
		return lstSpecialRateRequests;
	}
	*/
	

	@Override
	public List<CurrencyOtherInformation> getMinMaxRate(BigDecimal currencyId) {
		
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(CurrencyOtherInformation.class, "currencyOtherInformation");
		detachedCriteria.add(Restrictions.eq("currencyOtherInformation.exCurrencyMaster.currencyId", currencyId));
		
		List<CurrencyOtherInformation> lsCurrencyOtherInformationt = (List<CurrencyOtherInformation>) findAll(detachedCriteria);

		return lsCurrencyOtherInformationt;
	}

	@Override
	public void updateSpecialRateRequest(BigDecimal SpecialRatePk,BigDecimal fcAmount) {
		SpecialRateRequest specialRateRequest=(SpecialRateRequest) getSession().get(SpecialRateRequest.class, SpecialRatePk);
		if(fcAmount!=null){
		specialRateRequest.setFcAmount(fcAmount);
		specialRateRequest.setSellRate(null);
		specialRateRequest.setIsActive(Constants.U);
		}else{
			specialRateRequest.setIsActive(Constants.D);
		}
    	 getSession().update(specialRateRequest);
	}

	@Override
	public List<SpecialRateRequest> fetchSpotRateRecords(BigDecimal customerId,
			String createdDate, BigDecimal beneficiaryId,
			BigDecimal beneficiaryBankId) {
		String hql = "from SpecialRateRequest as specialRateRequest where trunc(specialRateRequest.createdDate) = trunc(sysdate) "
				+ " and specialRateRequest.fsCustomer.customerId = :customerId"
				+ " and specialRateRequest.isActive = :pisActive"
				+ " or specialRateRequest.isActive = :pisActive1"
				+ " and specialRateRequest.beneficiaryBankId = :beneficiaryBankId"
				+ " and specialRateRequest.beneficiaryId = :beneficiaryId"
				+ " and specialRateRequest.applicationCompanyId is null"
				+ " and specialRateRequest.applicationDocumentId is null" 
				+ " and specialRateRequest.applicationDocumentNo is null"
				+ " and specialRateRequest.applicationFinanceYear is null"
	;
		Query query = getSession().createQuery(hql); 

		query.setParameter("customerId", customerId);
		query.setParameter("beneficiaryId", beneficiaryId);
		query.setParameter("beneficiaryBankId", beneficiaryBankId); 
 		query.setParameter("pisActive", Constants.Yes); 
 		query.setParameter("pisActive1", Constants.U); 
		List<SpecialRateRequest> lstSpecialRateRequests = query.list();
		
		return lstSpecialRateRequests;
 
	}

	@Override
	public List<SpecialRateRequest> fetchAllDetailsFromCustomerAndBranch(BigDecimal customerReferenceNo, BigDecimal branchId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SpecialRateRequest.class, "specialRateRequest");
		detachedCriteria.setFetchMode("specialRateRequest.fsBankMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.fsBankMaster", "fsBankMaster", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("specialRateRequest.fsCurrencyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.fsCurrencyMaster", "fsCurrencyMaster", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("specialRateRequest.fsFinanceYear", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.fsFinanceYear", "fsFinanceYear", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("specialRateRequest.companyMaster", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.companyMaster", "companyMaster", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("specialRateRequest.fsCustomer", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		detachedCriteria.setFetchMode("specialRateRequest.fsDocument", FetchMode.JOIN);
		detachedCriteria.createAlias("specialRateRequest.fsDocument", "fsDocument", JoinType.INNER_JOIN);

		detachedCriteria.add(Restrictions.isNull("specialRateRequest.sellRate"));
		detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationCompanyId"));
		detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationDocumentId"));
		detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationFinanceYear"));
		detachedCriteria.add(Restrictions.isNull("specialRateRequest.applicationDocumentNo"));

		detachedCriteria.add(Restrictions.eq("specialRateRequest.isActive", Constants.U));

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date fromDate = calendar.getTime();
		System.out.println(fromDate);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date toDate = calendar.getTime();
		System.out.println(toDate);

		detachedCriteria.add(Restrictions.between("specialRateRequest.createdDate", fromDate, toDate));

		if(branchId != null){
			detachedCriteria.add(Restrictions.eq("specialRateRequest.countryBranchId", branchId));
		}
		if(customerReferenceNo!=null){
			BigDecimal customerIdNo=toFetchCustomerId(customerReferenceNo);
			detachedCriteria.add(Restrictions.eq("fsCustomer.customerId", customerIdNo));
		}
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<SpecialRateRequest> lstSpecialRateRequest= (List<SpecialRateRequest>) findAll(detachedCriteria);

		return lstSpecialRateRequest;

	}

	private BigDecimal toFetchCustomerId(BigDecimal customerId) {
		BigDecimal custId=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "customer");
		detachedCriteria.add(Restrictions.eq("customer.customerReference", customerId));
		 List<Customer> lstCustomer= (List<Customer>) findAll(detachedCriteria);
		 if(lstCustomer.size()>0){
			 custId= lstCustomer.get(0).getCustomerId();
		 }
		return custId;
		
	}
 
}
