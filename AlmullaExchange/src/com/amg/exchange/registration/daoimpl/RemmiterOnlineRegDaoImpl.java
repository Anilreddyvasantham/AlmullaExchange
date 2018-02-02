package com.amg.exchange.registration.daoimpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.amg.exchange.common.daoimpl.CommonDaoImpl;
import com.amg.exchange.common.model.Amlstatus;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.registration.dao.IRemmiterOnlineRegDao;
import com.amg.exchange.registration.model.ContactDetail;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerLogin;
import com.amg.exchange.registration.model.DocumentImg;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;

@SuppressWarnings("serial")
@Repository
public class RemmiterOnlineRegDaoImpl<T> extends CommonDaoImpl<T> implements IRemmiterOnlineRegDao<T>, Serializable{

	@Override
	public List<ContactDetail> getAllEmployees(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomer(Customer customer, String custId) {
		save((T)customer);
	}
	
	@Override
	public void saveCustomer(Customer customer) {
		getSession().saveOrUpdate(customer);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId) {
		save((T)customerEmp);
	}
	
	@Override
	public void saveCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp) {
		getSession().saveOrUpdate(customerEmp);;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void saveCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId) {
		save((T)customerEmployeeProof);
	}
	
	@Override
	public void saveCustomerEmploymentProofInfo(CustomerIdProof proof) {
		getSession().saveOrUpdate(proof);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> getCustomerIdProof(String civilId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		dCriteria.add(Restrictions.eq("customerIdProof.identityInt", civilId));
		return (List<CustomerIdProof>) findAll(dCriteria);
	}

	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(String userName, 	String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactDetail> getFsContactDetails(BigDecimal customerId) {
		DetachedCriteria creteria = DetachedCriteria.forClass(ContactDetail.class, "contactDetail");
		
		creteria.setFetchMode("contactDetail.fsCustomer", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		creteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		creteria.add(Restrictions.eq("contactDetail.activeStatus", Constants.CUST_ACTIVE_INDICATOR));
		
		creteria.setFetchMode("contactDetail.fsBizComponentDataByContactTypeId", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsBizComponentDataByContactTypeId", "fsBizComponentDataByContactTypeId", JoinType.LEFT_OUTER_JOIN);

		creteria.setFetchMode("contactDetail.fsCountryMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCountryMaster", "fsCountryMaster", JoinType.LEFT_OUTER_JOIN);

		creteria.setFetchMode("contactDetail.fsStateMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsStateMaster", "fsStateMaster", JoinType.LEFT_OUTER_JOIN);

		creteria.setFetchMode("contactDetail.fsDistrictMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsDistrictMaster", "fsDistrictMaster", JoinType.LEFT_OUTER_JOIN);

		creteria.setFetchMode("contactDetail.fsCityMaster", FetchMode.JOIN);
		creteria.createAlias("contactDetail.fsCityMaster", "fsCityMaster", JoinType.LEFT_OUTER_JOIN);
		
		creteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		
		return (List<ContactDetail>) findAll(creteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> getFsCustomer(BigDecimal customerId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Customer.class, "customer");
		criteria.add(Restrictions.eq("customer.customerId", customerId));
		
		/*criteria.setFetchMode("customer.fsCustomerEmploymentInfos", FetchMode.SELECT);
		criteria.createAlias("customer.fsCustomerEmploymentInfos", "fsCustomerEmploymentInfos", JoinType.LEFT_OUTER_JOIN);*/
		
		/*criteria.setFetchMode("customer.fsCustomerIdProofs", FetchMode.JOIN);
		criteria.createAlias("customer.fsCustomerIdProofs", "fsCustomerIdProofs", JoinType.LEFT_OUTER_JOIN);
		
		criteria.setFetchMode("fsCustomerIdProofs.fsDocumentImg", FetchMode.JOIN);
		criteria.createAlias("fsCustomerIdProofs.fsDocumentImg", "fsDocumentImg", JoinType.LEFT_OUTER_JOIN);*/
		
		/*criteria.setFetchMode("customer.fsContactDetails", FetchMode.JOIN);
		criteria.createAlias("customer.fsContactDetails", "fsContactDetails", JoinType.LEFT_OUTER_JOIN);*/
		
		//criteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<Customer>)findAll(criteria);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<CustomerEmploymentInfo> getFsCustEmpInfo(BigDecimal customerId) {
		
		DetachedCriteria criteria = DetachedCriteria.forClass(CustomerEmploymentInfo.class, "customerEmploymentInfo");
			
			criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId",  FetchMode.JOIN);
			criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByEmploymentTypeId", "fsBizComponentDataByEmploymentTypeId", 
																																	JoinType.LEFT_OUTER_JOIN);
			
			criteria.setFetchMode("customerEmploymentInfo.fsBizComponentDataByOccupationId",  FetchMode.JOIN);
			criteria.createAlias("customerEmploymentInfo.fsBizComponentDataByOccupationId", "fsBizComponentDataByOccupationId", 
																																	JoinType.LEFT_OUTER_JOIN);
			
			criteria.setFetchMode("customerEmploymentInfo.fsCustomer",  FetchMode.JOIN);
			criteria.createAlias("customerEmploymentInfo.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
			criteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		return (List<CustomerEmploymentInfo>)findAll(criteria);
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public List<CustomerIdProof> getFsCustIdProof(BigDecimal customerId, BigDecimal identityType) {
		System.out.println("Customer Id : "+customerId);
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		
		dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", identityType));
		
		/*dCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);
*/		
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		return (List<CustomerIdProof>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Amlstatus> getAMLStatus(String name) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Amlstatus.class);
		dCriteria.add(Restrictions.eq("remName", name));
		return (List<Amlstatus>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CityMaster> getCity(BigDecimal district) {
		return (List<CityMaster>) getSession().createQuery("from CityMaster").list();
	}

	@Override
	public List<CountryMaster> getCountry() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StateMaster> getState() {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(StateMaster.class);
		return (List<StateMaster>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateCustomer(Customer customer, String custId) {
		Customer customerFromDb = (Customer)getSession().get(Customer.class, new BigDecimal(custId));
		customerFromDb.setUpdatedBy(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("userName").toString());
		customerFromDb.setEmail(customer.getEmail());
		customerFromDb.setLastUpdated(new Date());
		update((T)customerFromDb); 
	}

	@Override
	public void updateCustomerEmploymentInfo(CustomerEmploymentInfo customerEmp, String custId) {
	
	}

	@Override
	public void updateCustomerEmploymentProofInfo(CustomerIdProof customerEmployeeProof, String custId) {
		
	}

	@Override
	public void saveImage(DocumentImg document) {
		getSession().saveOrUpdate(document);
	}
	
	@SuppressWarnings({ "unchecked"})
	@Override
	public void updateImage(DocumentImg document, BigDecimal custId) {/*
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", custId));
		
		List<CustomerIdProof> proof = (List<CustomerIdProof>) findAll(dCriteria);
		
		DocumentImg docImage = proof.get(0).getFsDocumentImg();
		docImage.setImage(document.getImage());
		docImage.setImageName(document.getImageName());
		docImage.setUploadDate(new Date());
		save((T)docImage);
	*/}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerLogin> getLoginInfoForCustomer(String userName) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerLogin.class);
		dCriteria.add(Restrictions.eq("userName", userName));
		return (List<CustomerLogin>) findAll(dCriteria);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentImg> getImage(BigDecimal imageId) {
		/*DetachedCriteria dCriteria = DetachedCriteria.forClass(DocumentImg.class, "docImage");
		dCriteria.add(Restrictions.eq("docImage.imgId", imageId));
		return (List<DocumentImg>) findAll(dCriteria);*/System.out.println("Dao ImageId"+imageId);
		return  (List<DocumentImg>) getSession().createQuery("from DocumentImg where imgId="+imageId).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> CheckTokenAvailable(String token) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(Customer.class);
		dCriteria.add(Restrictions.eq("tokenKey", token));
		return (List<Customer>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void updateLoginCustomerId(String userName, BigDecimal customerId, BigDecimal companyId) {
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerLogin.class, "customerLogin");
		dCriteria.add(Restrictions.eq("userName", userName));
		
		try{
			CustomerLogin customerLogin = ((List<CustomerLogin>)findAll(dCriteria)).get(0);
			customerLogin.setFsCustomer(new Customer(customerId));
			customerLogin.setUpdatedBy(userName);
			customerLogin.setLastUpdated(new Date());
			customerLogin.setFsCompanyMaster(new CompanyMaster(companyId));
			getSession().saveOrUpdate(customerLogin);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void saveContactDetails(ContactDetail contactDetail) {
		getSession().saveOrUpdate(contactDetail);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> getProofDetails(BigDecimal customerId) {
	DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		
		dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
		
		
		/*dCriteria.setFetchMode("customerIdProof.fsDocumentImg", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsDocumentImg", "fsDocumentImg", JoinType.INNER_JOIN);
*/		
		dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
		dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
		
		dCriteria.add(Restrictions.eq("fsCustomer.customerId", customerId));
		
		return (List<CustomerIdProof>) findAll(dCriteria);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CountryBranch> getCountryBranch(BigDecimal branchId) {
		
		DetachedCriteria dCriteria = DetachedCriteria.forClass(CountryBranch.class, "countryBranch");

		dCriteria.add(Restrictions.eq("countryBranch.branchId", branchId));
								
		dCriteria.setResultTransformer(DetachedCriteria.DISTINCT_ROOT_ENTITY);
		
		return (List<CountryBranch>)findAll(dCriteria);
	}

	@Override
	public List<String> getMigrationData(String idNumber, BigDecimal mobile,
			String email) throws AMGException {
	
		List<String> outLst = new ArrayList<String>();
		
		Connection connection=null;
		try {
			//connection = DBConnection.getdataconnection();
			connection =getDataSourceFromHibernateSession();
		} catch (Exception e) {
			e.printStackTrace();
		}
		CallableStatement cs;
		try {
			
			String call = " { call FS_VALIDATE_ONLINE_CUSTOMER (?, ?, ?, ?) } ";
			cs = connection.prepareCall(call);
			cs.setString(1, idNumber);
			cs.setBigDecimal(2, mobile);
			cs.setString(3, email);
			cs.registerOutParameter(4, java.sql.Types.VARCHAR);
			
			cs.execute();
			String out1=cs.getString(4);
			
			outLst.add(out1);
			
			connection.close();
		}catch (SQLException e) {
			throw new AMGException(e.getMessage());
		}
		System.out.println("!!!!!!outLst out!!!!!!!!!"+outLst);
		return outLst;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerIdProof> getIdproofDetails(BigDecimal idType,String idNumber) {
		 DetachedCriteria dCriteria = DetachedCriteria.forClass(CustomerIdProof.class, "customerIdProof");
		 
		    dCriteria.setFetchMode("customerIdProof.fsCustomer", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsCustomer", "fsCustomer", JoinType.INNER_JOIN);
			
	        dCriteria.setFetchMode("customerIdProof.fsBizComponentDataByIdentityTypeId", FetchMode.JOIN);
			dCriteria.createAlias("customerIdProof.fsBizComponentDataByIdentityTypeId", "fsBizComponentDataByIdentityTypeId", JoinType.INNER_JOIN);
			
			dCriteria.add(Restrictions.eq("fsBizComponentDataByIdentityTypeId.componentDataId", idType));
			
			
			dCriteria.add(Restrictions.eq("customerIdProof.identityInt", idNumber));
			
			dCriteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
			return (List<CustomerIdProof>) findAll(dCriteria);
	}

}
