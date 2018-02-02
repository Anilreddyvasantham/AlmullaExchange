package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.registration.dao.ICustomerImageVerificationDao;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.util.Constants;

@Repository
public class CustomerImageVerificationDaoImpl<T> extends CommonDaoImpl<T> implements ICustomerImageVerificationDao, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(CustomerImageVerificationDaoImpl.class);

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getList() {
		LOGGER.info("Entering into getList method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerImageVerification.class, "customerImageVerification");
		dCriteria.setFetchMode("customerImageVerification.customer", FetchMode.JOIN);
		dCriteria.createAlias("customerImageVerification.customer", "customer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.isNull("customerImageVerification.verifiedDate"));
		dCriteria.addOrder(Order.desc("customerImageVerification.customerImageVerificationIdId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<CustomerImageVerification>) findAll(dCriteria);
	}

	@Override
	public String approveRecord(List<BigDecimal> lstApproved, String userName) {
		String list = null;
		int i = 0;
		for (BigDecimal bigDecimal : lstApproved) {
			CustomerImageVerification customerImageVerification = (CustomerImageVerification) getSession().get(CustomerImageVerification.class, bigDecimal);
			String approvedUser = customerImageVerification.getVerifiedBy();
			if (approvedUser == null) {
				customerImageVerification.setVerifiedBy(userName);
				customerImageVerification.setVerifiedDate(new Date());
				getSession().update(customerImageVerification);
				i++;
			}
		}
		if (i != 0) {
			list = "Success";
		} else {
			list = "Fail";
		}
		return list;
	}

	@Override
	public void rejectCustomerIdentityTypeStatus(BigDecimal customerId,String userName) {
		CustomerIdProof customerIdProof=(CustomerIdProof) getSession().get(CustomerIdProof.class, customerId);
		customerIdProof.setUpdatedBy(userName);
		customerIdProof.setLastUpdatedDate(new Date());
		customerIdProof.setIdentityStatus(Constants.D);
		//customerIdProof.setRejectionStatus(Constants.Yes);
		getSession().update(customerIdProof);
	}

	@Override
	public BigDecimal toFetchIdentityPk(BigDecimal customerId, BigDecimal idType,Date idExpDate) {

		System.out.println("idExpDate:::::::::::::::::::::::::::::"+idExpDate);
		String idExpiryDate = new SimpleDateFormat("dd/MM/yyyy").format(idExpDate);
		Date expiryConver = null;
		try {
			expiryConver = new SimpleDateFormat("dd/MM/yyyy").parse(idExpiryDate);
			System.out.println("idExpDate:::::::::::::::::::::::::::::"+expiryConver);
		} catch (ParseException e) {

			e.printStackTrace();
		}

		String sqlDate =" IDENTITY_EXPIRY_DATE=to_date('"+idExpiryDate+"')";
		System.out.println("SQL DATE: "+sqlDate);

		BigDecimal custoIdentityPk=null;
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass( CustomerIdProof.class, "customerIdProof");

		detachedCriteria.setFetchMode("customerIdProof.fsCustomer",FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsCustomer.customerId",customerId));

		detachedCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId",FetchMode.JOIN);
		detachedCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		detachedCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId",idType));

		detachedCriteria.add(Restrictions.eq("customerIdProof.identityExpiryDate",expiryConver));
		//detachedCriteria.add(Restrictions.sqlRestriction(sqlDate));
		detachedCriteria.add(Restrictions.eq("customerIdProof.identityStatus",Constants.Yes));
		detachedCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		List<CustomerIdProof> lstCustomerIdProofs = (List<CustomerIdProof>) findAll(detachedCriteria);
		/*String hql = "from CustomerIdProof as customerIdProof where trunc(customerIdProof.identityExpiryDate) = trunc(:pidExpDate) "
                           + " and customerIdProof.fsCustomer.customerId = :pcustomerId"
                           + " and customerIdProof.fsBizComponentDataByIdentityTypeId.componentDataId= :pcomponentDataId";
              Query query = getSession().createQuery(hql);

              query.setParameter("pidExpDate", idExpDate);
              query.setParameter("pcustomerId", customerId);
              query.setParameter("pcomponentDataId", idType);
		   List<CustomerIdProof> lstCustomerIdProofs = query.list();*/


		if(lstCustomerIdProofs.size()>0){
			custoIdentityPk=lstCustomerIdProofs.get(0).getCustProofId();
		}
		return custoIdentityPk;
	}

	@Override
	public void rejectCustomerImageVeryfiedDate(BigDecimal customerImageVerificationId, String userName) {
		CustomerImageVerification customerImageVerification = (CustomerImageVerification) getSession().get(CustomerImageVerification.class, customerImageVerificationId);
		customerImageVerification.setVerifiedBy(userName);
		customerImageVerification.setVerifiedDate(new Date());
		getSession().update(customerImageVerification);
	}

	@Override
	public void upDateRecord(BigDecimal imageVerificationId, String userName,
			String comStatus) {
		CustomerImageVerification customerImageVerification = (CustomerImageVerification) getSession().get(CustomerImageVerification.class, imageVerificationId);
		String approvedUser = customerImageVerification.getVerifiedBy();
		//if (approvedUser == null) {
		customerImageVerification.setVerifiedBy(userName);
		customerImageVerification.setVerifiedDate(new Date());
		customerImageVerification.setComplianceStatus(comStatus);
		getSession().update(customerImageVerification);
		//}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getAllVerifiedList(Date creationDate) {
		LOGGER.info("Entering into getAllVerifiedList method");
		/*DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerImageVerification.class, "customerImageVerification");
		dCriteria.setFetchMode("customerImageVerification.customer", FetchMode.JOIN);
		dCriteria.createAlias("customerImageVerification.customer", "customer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.isNotNull("customerImageVerification.verifiedDate"));
		dCriteria.addOrder(Order.desc("customerImageVerification.customerImageVerificationIdId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into getAllVerifiedList method");
		return (List<CustomerImageVerification>) findAll(dCriteria);*/
		String hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.verifiedDate is not null";

		Query query = getSession().createQuery(hql); 

		query.setParameter("pcreationDate", creationDate);
		List<Object[]> lst=query.list();
		List<CustomerImageVerification> lstDetails=new ArrayList<CustomerImageVerification>();
		if(lst.size()>0)
		{
			for(Object[] obj:lst)
			{
				CustomerImageVerification c=(CustomerImageVerification) obj[0];
				lstDetails.add(c);
			}
		}


		return lstDetails;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getListApproval(
			BigDecimal imageVerificationId) {
		LOGGER.info("Entering into getListApproval method");
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerImageVerification.class, "customerImageVerification");
		dCriteria.add(Restrictions.eq("customerImageVerification.customerImageVerificationIdId",imageVerificationId));
		dCriteria.add(Restrictions.isNull("customerImageVerification.verifiedDate"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");
		return (List<CustomerImageVerification>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getList(Date creationDate) {
		LOGGER.info("Entering into getList method");

		/*	SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		//SimpleDateFormat dateformat1 = new SimpleDateFormat("yyyy/MM/dd");
		String sysdate=dateformat.format(creationDate);

		String sqlDate ="{alias}.CREATION_DATE=to_date('"+sysdate+"','dd/MM/yy')";

		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerImageVerification.class, "customerImageVerification");
		dCriteria.setFetchMode("customerImageVerification.customer", FetchMode.JOIN);
		dCriteria.createAlias("customerImageVerification.customer", "customer", JoinType.INNER_JOIN);
		dCriteria.add(Restrictions.isNull("customerImageVerification.verifiedDate"));
		//dCriteria.add(Restrictions.eq("customerImageVerification.creationDate",creationDate));
		dCriteria.add(Restrictions.sqlRestriction(sqlDate));
		dCriteria.addOrder(Order.desc("customerImageVerification.customerImageVerificationIdId"));
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		LOGGER.info("Exit into viewById method");*/
		String hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.verifiedDate is null";

		Query query = getSession().createQuery(hql); 

		query.setParameter("pcreationDate", creationDate);
		List<Object[]> lst=query.list();
		List<CustomerImageVerification> lstDetails=new ArrayList<CustomerImageVerification>();
		if(lst.size()>0)
		{
			for(Object[] obj:lst)
			{
				CustomerImageVerification c=(CustomerImageVerification) obj[0];
				lstDetails.add(c);
			}
		}

		//List<CustomerImageVerification> lstDetails = query.list();



		//List<CustomerImageVerification> lstDetails = (List<CustomerImageVerification>) findAll(dCriteria);

		return lstDetails;
	}


	//Anil(01/10/2018)

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getCustomerDetails(Date creationDate,BigDecimal cusId,String idNum){
				
		String hql = null;
		if(creationDate!=null && cusId==null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.verifiedDate is null";
		}
		
		if(creationDate==null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where b.customerId = :pCusId and a.verifiedDate is null";
		}
		
		if(creationDate==null && cusId==null && (idNum!=null || !idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where a.idNumber = :pIdNum and a.verifiedDate is null";
		}
		
		if(creationDate!=null && cusId!=null && idNum!=null){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and b.customerId = :pCusId and a.idNumber = :pIdNum and a.verifiedDate is null";
		}
		
		if(creationDate!=null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and b.customerId = :pCusId and a.verifiedDate is null";
		}
		
		if(cusId!=null && idNum!=null && creationDate==null){
			hql="from CustomerImageVerification a inner join a.customer b where b.customerId = :pCusId and a.idNumber = :pIdNum and a.verifiedDate is null";
		}
		
		if(creationDate!=null && idNum!=null && cusId==null){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.idNumber = :pIdNum and a.verifiedDate is null";
		}

		Query query = getSession().createQuery(hql); 

		if(creationDate!=null && cusId==null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pcreationDate", creationDate);
		}
		if(creationDate==null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pCusId", cusId);
		}
		if(creationDate==null && cusId==null && (idNum!=null || !idNum.equalsIgnoreCase(""))){
			query.setParameter("pIdNum", idNum);
		}	
		if(creationDate!=null && cusId!=null && idNum!=null){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pCusId", cusId);
			query.setParameter("pIdNum", idNum);
		}
		if(creationDate!=null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pCusId", cusId);
		}
		if(creationDate==null && cusId!=null && idNum!=null){
			query.setParameter("pCusId", cusId);
			query.setParameter("pIdNum", idNum);
		}
		if(creationDate!=null && cusId==null && idNum!=null){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pIdNum", idNum);
		}	
		
		List<Object[]> lst=query.list();
		List<CustomerImageVerification> lstDetails=new ArrayList<CustomerImageVerification>();
		if(lst.size()>0)
		{
			for(Object[] obj:lst)
			{
				CustomerImageVerification c=(CustomerImageVerification) obj[0];
				lstDetails.add(c);
			}
		}
		return lstDetails;	
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerImageVerification> getCustomerDetailsLog(Date creationDate,BigDecimal cusId,String idNum){
				
		String hql = null;
		if(creationDate!=null && cusId==null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.verifiedDate is not null";
		}
		
		if(creationDate==null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where b.customerId = :pCusId and a.verifiedDate is not null";
		}
		
		if(creationDate==null && cusId==null && (idNum!=null || !idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where a.idNumber = :pIdNum and a.verifiedDate is not null";
		}
		
		if(creationDate!=null && cusId!=null && idNum!=null){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and b.customerId = :pCusId and a.idNumber = :pIdNum and a.verifiedDate is not null";
		}
		
		if(creationDate!=null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and b.customerId = :pCusId and a.verifiedDate is not null";
		}
		
		if(cusId!=null && idNum!=null && creationDate==null){
			hql="from CustomerImageVerification a inner join a.customer b where b.customerId = :pCusId and a.idNumber = :pIdNum and a.verifiedDate is not null";
		}
		
		if(creationDate!=null && idNum!=null && cusId==null){
			hql="from CustomerImageVerification a inner join a.customer b where  trunc(a.creationDate)= trunc(:pcreationDate) and a.idNumber = :pIdNum and a.verifiedDate is not null";
		}

		Query query = getSession().createQuery(hql); 

		if(creationDate!=null && cusId==null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pcreationDate", creationDate);
		}
		if(creationDate==null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pCusId", cusId);
		}
		if(creationDate==null && cusId==null && (idNum!=null || !idNum.equalsIgnoreCase(""))){
			query.setParameter("pIdNum", idNum);
		}	
		if(creationDate!=null && cusId!=null && idNum!=null){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pCusId", cusId);
			query.setParameter("pIdNum", idNum);
		}
		if(creationDate!=null && cusId!=null && (idNum==null || idNum.equalsIgnoreCase(""))){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pCusId", cusId);
		}
		if(creationDate==null && cusId!=null && idNum!=null){
			query.setParameter("pCusId", cusId);
			query.setParameter("pIdNum", idNum);
		}
		if(creationDate!=null && cusId==null && idNum!=null){
			query.setParameter("pcreationDate", creationDate);
			query.setParameter("pIdNum", idNum);
		}	
		
		List<Object[]> lst=query.list();
		List<CustomerImageVerification> lstDetails=new ArrayList<CustomerImageVerification>();
		if(lst.size()>0)
		{
			for(Object[] obj:lst)
			{
				CustomerImageVerification c=(CustomerImageVerification) obj[0];
				lstDetails.add(c);
			}
		}
		return lstDetails;	
	}
}
