/**         Customer Activate Deactivate Purpose
 * 
 */
package com.amg.exchange.registration.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bean.CreateSearchTable;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.registration.service.ISearchCustomerService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Nagarjuna
 *
 */
@Component("customerActivateDeActivateBean")
@Scope("session")
public class CustomerActivateDeActivateBean<T> implements Serializable {

	 	private static final long serialVersionUID = 1L;
	 	
	 	SessionStateManage sessionStateManage = new SessionStateManage();
		Logger log = Logger.getLogger(CustomerActivateDeActivateBean.class);
		
		private String firstName = null;
		private String middleName = null;
		private String idNumber = null;
		private BigDecimal customerReference = null;
		private String lastName = null;
		private BigDecimal nationality = null;
		private BigDecimal idType = null;
		private String idTypeName = null;
		private String signatureSpecimen = null;

		private String mob = null;
		private Date dob = null;
		private String cust_id = null;
		private BigDecimal countryId = null;
 		private String custType = null; 
 		private String remarks;
 		private String approvedDate;
		private String approvedBy;
		private BigDecimal customerPk;
		private BigDecimal custIdProofPk;
		private String errorMessage;
		
		
		List<CreateSearchTable>  finalData = new ArrayList<CreateSearchTable>();
		List<CustomerIdProof>	lstCustomer = new ArrayList<CustomerIdProof>();
		List<CustomerIdProof>	lstCustomerExp = new ArrayList<CustomerIdProof>();
		private Map<BigDecimal, String> mapNationalityList = new HashMap<BigDecimal, String>();
		private Map<BigDecimal, String> idTypeValues = new HashMap<BigDecimal, String>();
				

		@Autowired
		ISearchCustomerService<T> isearchCustomerService;
		
		@Autowired
		IGeneralService<T> igeneralService;
		
		@Autowired
		ICustomerRegistrationBranchService<T> icustomerRegistrationService;
		
		
		public BigDecimal getCustomerPk() {
			return customerPk;
		}
		public void setCustomerPk(BigDecimal customerPk) {
			this.customerPk = customerPk;
		}
		public BigDecimal getCustIdProofPk() {
			return custIdProofPk;
		}
		public void setCustIdProofPk(BigDecimal custIdProofPk) {
			this.custIdProofPk = custIdProofPk;
		}
		
		public List<CreateSearchTable> getFinalData() {
			return finalData;
		}
		public void setFinalData(List<CreateSearchTable> finalData) {
			this.finalData = finalData;
		}
				
		public Map<BigDecimal, String> getIdTypeValues() {
			return idTypeValues;
		}
		public void setIdTypeValues(Map<BigDecimal, String> idTypeValues) {
			this.idTypeValues = idTypeValues;
		}

		public IGeneralService<T> getIgeneralService() {
			return igeneralService;
		}
		public void setIgeneralService(IGeneralService<T> igeneralService) {
			this.igeneralService = igeneralService;
		}
		public Map<BigDecimal, String> getMapNationalityList() {
			return mapNationalityList;
		}
		public void setMapNationalityList(Map<BigDecimal, String> mapNationalityList) {
			this.mapNationalityList = mapNationalityList;
		}
		public ISearchCustomerService<T> getIsearchCustomerService() {
			return isearchCustomerService;
		}
		public void setIsearchCustomerService(
				ISearchCustomerService<T> isearchCustomerService) {
			this.isearchCustomerService = isearchCustomerService;
		}
		public SessionStateManage getSessionStateManage() {
			return sessionStateManage;
		}
		public void setSessionStateManage(SessionStateManage sessionStateManage) {
			this.sessionStateManage = sessionStateManage;
		}
		public Logger getLog() {
			return log;
		}
		public void setLog(Logger log) {
			this.log = log;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getMiddleName() {
			return middleName;
		}
		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}
		public String getIdNumber() {
			return idNumber;
		}
		public void setIdNumber(String idNumber) {
			this.idNumber = idNumber;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public BigDecimal getNationality() {
			return nationality;
		}
		public void setNationality(BigDecimal nationality) {
			this.nationality = nationality;
		}
		public BigDecimal getIdType() {
			return idType;
		}
		public void setIdType(BigDecimal idType) {
			this.idType = idType;
		}
		public BigDecimal getCustomerReference() {
			return customerReference;
		}
		public void setCustomerReference(BigDecimal customerReference) {
			this.customerReference = customerReference;
		}
		public String getIdTypeName() {
			return idTypeName;
		}
		public void setIdTypeName(String idTypeName) {
			this.idTypeName = idTypeName;
		}
		public String getSignatureSpecimen() {
			return signatureSpecimen;
		}
		public void setSignatureSpecimen(String signatureSpecimen) {
			this.signatureSpecimen = signatureSpecimen;
		}
		public String getMob() {
			return mob;
		}
		public void setMob(String mob) {
			this.mob = mob;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		public String getCust_id() {
			return cust_id;
		}
		public void setCust_id(String cust_id) {
			this.cust_id = cust_id;
		}
		public BigDecimal getCountryId() {
			return countryId;
		}
		public void setCountryId(BigDecimal countryId) {
			this.countryId = countryId;
		}
		 
		public String getCustType() {
			return custType;
		}
		public void setCustType(String custType) {
			this.custType = custType;
		}
		public String getApprovedDate() {
			return approvedDate;
		}
		public void setApprovedDate(String approvedDate) {
			this.approvedDate = approvedDate;
		}
		public String getApprovedBy() {
			return approvedBy;
		}
		public void setApprovedBy(String approvedBy) {
			this.approvedBy = approvedBy;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}		
		public String getErrorMessage() {
			return errorMessage;
		}
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		

		public static long getSerialversionuid() {
			return serialVersionUID;
		}
		public void setNationalityListMap() {

			if (mapNationalityList.size() == 0) {
				List<CountryMasterDesc> lstCountryMasterDesc = getIgeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("lanaguageId") : "1"));
				for (CountryMasterDesc countryMasterDesc : lstCountryMasterDesc) {
					mapNationalityList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getNationality());
				}
			}
		}
			
		public List<CountryMasterDesc> getNationalityNameList() {
			SessionStateManage sessionStateManage = new SessionStateManage();
			return getIgeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		}
		
		
		@Autowired
		LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
		public void pageNavigation(){
			Boolean isEmpAuth=getIsearchCustomerService().checkAuthorizationOfEmp(sessionStateManage.getEmployeeId());
			log.info( "::::::::::::::::::::::::::::::::::isEmpAuth"+isEmpAuth);
			if(!isEmpAuth){
			clearAll();
			idTypeListPop();
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "customeractivatedeactivate.xhtml");
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/customeractivatedeactivate.xhtml");
			} catch (Exception e) {
				log.error("Error in Page Navigation to Customer ActivateDeactivate");
			}
			}else{ 	RequestContext.getCurrentInstance().execute("unauthorizeduser.show();");   }
		}
		
		
		public void idTypeListPop(){
			// Individual Customers
			Map<BigDecimal, String> idTypeValues1 = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), Constants.Individual,Constants.COMPANYIDTYPE);
			// Corporate Customers
			Map<BigDecimal, String> idTypeValues2 = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), Constants.C, Constants.COMPANYIDTYPE);
			
			Map<BigDecimal, String> idTypeValuesData = new HashMap<BigDecimal, String>();
			idTypeValuesData.putAll(idTypeValues1);
			idTypeValuesData.putAll(idTypeValues2);
						
			setIdTypeValues(idTypeValuesData);
		}
		
		
		public void search(){
			finalData.clear();
			setRemarks(null);
			setNationalityListMap();

			if(getCustomerReference() != null){
				// continue
			}else if(getIdType() != null && getIdNumber() != null && !getIdNumber().trim().equalsIgnoreCase("")){
				// continue
			}else{
				// stop to enter details
				setErrorMessage("Please Enter Id Type and Id No (or) Customer Reference");
				RequestContext.getCurrentInstance().execute("errormsg.show();");
				return;
			}

			SearchEntityBean searchEntityBean = new SearchEntityBean();

			searchEntityBean.setNationalityId(getNationality());
			searchEntityBean.setIdType(getIdType());
			if(getIdNumber() != null){
				searchEntityBean.setIdNum(getIdNumber().trim());
			}

			searchEntityBean.setCustomerRef(getCustomerReference());

			log.info( "Nationality============"+getNationality());
			log.info("Id Type==============="+getIdType());
			log.info("Id Number============="+getIdNumber());
			log.info("Customer Reference============="+getCustomerReference());

			lstCustomer = getIsearchCustomerService().searchAllCustomers(searchEntityBean);
			log.info("lstCustomer============"+lstCustomer.size());


			Set<BigDecimal> set = new HashSet<BigDecimal>();
			if (lstCustomer.size() > 0) {
				for (CustomerIdProof customerIdProof : lstCustomer) {
					set.add(customerIdProof.getFsCustomer().getCustomerId());
				}
				
				CustomerIdProof activetempIDProof = null;
				if (set.size() == 1) {
					int i = 0;
					for (CustomerIdProof customerIdProof : lstCustomer) {
						if(customerIdProof.getIdentityStatus() != null && customerIdProof.getIdentityStatus().equalsIgnoreCase(Constants.Y)){
							i = i+1;
							if(i == 1){
								activetempIDProof = customerIdProof;
							}else{
								activetempIDProof = null;
							}
						}
					}

					if(i == 1){
						if(activetempIDProof != null){
							lstCustomer.clear();
							lstCustomer.add(activetempIDProof);
						}else{
							CustomerIdProof tempIDProof = Collections.max(lstCustomer, new CustomerIdProoofComp());
							lstCustomer.clear();
							lstCustomer.add(tempIDProof);
						}
					}else{
						//show all records which are active 
					}

				} else {
					ArrayList<ArrayList<CustomerIdProof>> listofDuplicates = new ArrayList<ArrayList<CustomerIdProof>>();
					// create an iterator
					Iterator iterator = set.iterator();
					// check values
					while (iterator.hasNext()) {
						BigDecimal tempSet = (BigDecimal) iterator.next();
						ArrayList<CustomerIdProof> duplidateList = new ArrayList<CustomerIdProof>();
						for (CustomerIdProof tempIDProof : lstCustomer) {
							if (tempSet.compareTo(tempIDProof.getFsCustomer().getCustomerId()) == 0) {
								duplidateList.add(tempIDProof);
							}
						}

						listofDuplicates.add(duplidateList);
					}

					lstCustomer.clear();

					for (ArrayList<CustomerIdProof> arrayList : listofDuplicates) {
						CustomerIdProof tempIDProof = Collections.max(arrayList, new CustomerIdProoofComp());
						lstCustomer.add(tempIDProof);

					}

				}

				for(CustomerIdProof customerIdProof : lstCustomer) {

					/*CustomerIdProof customerIdProof =	lstCustomer.get(0);*/

					CreateSearchTable createSearchTable = new CreateSearchTable();

					try {
						createSearchTable = new CreateSearchTable();
						createSearchTable.setIdNumber(customerIdProof.getIdentityInt());
						log.info("ID PROOF====================="+customerIdProof.getIdentityInt());
						createSearchTable.setCustIdProofPk( customerIdProof.getCustProofId());
						log.info("CUST_ID_PROOF_PK============"+customerIdProof.getCustProofId());
						createSearchTable.setCustomerTyId(customerIdProof.getFsCustomer().getCustomerId());
						createSearchTable.setIdType(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
						createSearchTable.setIdTypeName(igeneralService.getSearchIdType(sessionStateManage.getLanguageId(), customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
						createSearchTable.setFirstName(customerIdProof.getFsCustomer().getFirstName()+" "+customerIdProof.getFsCustomer().getLastName());
						createSearchTable.setShortName(customerIdProof.getFsCustomer().getShortName());
						createSearchTable.setMiddleName(customerIdProof.getFsCustomer().getMiddleName());
						createSearchTable.setSundryDebtorRef(customerIdProof.getFsCustomer().getSundryDebtorReference());
						createSearchTable.setCustomerPk(customerIdProof.getFsCustomer().getCustomerId());
						if(customerIdProof.getFsCustomer().getFsCountryMasterByNationality() != null){
							createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));
							createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
						}

						try {
							createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
						} catch (Exception e1) {
							log.error("Exception occured" +e1);
						}
						createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
						createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());

						createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
						createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
						if(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId()!=null){
							createSearchTable.setCusTypeIdForSpeclcus(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue());
						}

						createSearchTable.setCreatedBy(customerIdProof.getFsCustomer().getCreatedBy());
						try {
							if(customerIdProof.getFsCustomer().getCreationDate()!=null){
								createSearchTable.setCreatedDate(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getCreationDate()));
							}
						} catch (Exception e) {
							log.error("Exception occured" +e);
						}
						createSearchTable.setModifiedBy(customerIdProof.getFsCustomer().getUpdatedBy());
						try {
							if(customerIdProof.getFsCustomer().getLastUpdated()!=null){
								createSearchTable.setModifiedDate(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getLastUpdated()));
							}
						} catch (Exception e) {
							log.error("Exception occured" +e);
						}
						createSearchTable.setAMLStatus( customerIdProof.getFsCustomer().getAmlStatus());
						try {
							if(customerIdProof.getIdentityExpiryDate()!=null){
								createSearchTable.setIdExpiryDate(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getIdentityExpiryDate()) );
							}
						} catch (Exception e) {
							log.error("Exception occured" +e);
						}
						createSearchTable.setIsActive(customerIdProof.getFsCustomer().getIsActive());
						createSearchTable.setIdStatus(customerIdProof.getIdentityStatus());
						if(customerIdProof.getIdentityStatus()!=null){
							if(customerIdProof.getIdentityStatus().equalsIgnoreCase(Constants.Yes)){
								createSearchTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
								createSearchTable.setCurrentStatus(Constants.ACTIVATE);
							} 
							else if(customerIdProof.getIdentityStatus().equalsIgnoreCase(Constants.D)){
								createSearchTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
								createSearchTable.setCurrentStatus(Constants.DELETE);
							} 
							else {
								createSearchTable.setDynamicLabelActivateDeactivate( Constants.DELETE);
								createSearchTable.setCurrentStatus(Constants.PARTIAL);
							} 
						}
						
						finalData.add(createSearchTable );

					}catch(Exception e){
						setErrorMessage("Error Occured:"+e.getMessage());
						RequestContext.getCurrentInstance().execute("errormsg.show();");

						log.info("Error Occured While Searching Records::::::::::::::::::::");}

				}
			}else{
				RequestContext.getCurrentInstance().execute("recnotfound.show();");

			}
		}

	public void customerActivateDeactivate(CreateSearchTable createSearchTable) {
		log.info("::::::::::::::::::::::::: customerActivateDeactivate(   METHOD CALLED :::::::::::::::::::::::");
		log.info("Customer Status:::::::::::::" + createSearchTable.getIsActive());
		log.info("Customer Id Proof:::::::::::" + createSearchTable.getIdStatus());

		if (createSearchTable.getIdStatus().equalsIgnoreCase(Constants.Yes)
				|| createSearchTable.getIdStatus().equalsIgnoreCase(Constants.No)) {
			setApprovedBy(createSearchTable.getCreatedBy());
			setApprovedDate(createSearchTable.getCreatedDate());
			setCustomerPk(createSearchTable.getCustomerPk());
			setCustIdProofPk(createSearchTable.getCustIdProofPk());
			createSearchTable.setModifiedBy(sessionStateManage.getUserName());
			log.info("CustomerPk:::::::::::::::::::::::::::::::::::::::::::::::" + createSearchTable.getCustomerPk());
			log.info("CustomerIdProofPk::::::::::::::::::::::::::::::::::::" + createSearchTable.getCustIdProofPk());
			RequestContext.getCurrentInstance().execute("remarks.show();");

		} else if (createSearchTable.getIdStatus().equalsIgnoreCase(Constants.D)) {

			for (CustomerIdProof customerIdProof : lstCustomer) {

				if (customerIdProof.getFsCustomer().getIsActive() != null
						&& (customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Y))
						&& createSearchTable.getCustomerPk()
								.compareTo(customerIdProof.getFsCustomer().getCustomerId()) != 0) {

					setErrorMessage("Duplicate Customer activation not allowed.");
					RequestContext.getCurrentInstance().execute("errormsg.show();");
					return;
				}
			}

			setApprovedBy(createSearchTable.getCreatedBy());
			setApprovedDate(createSearchTable.getCreatedDate());
			setCustomerPk(createSearchTable.getCustomerPk());
			setCustIdProofPk(createSearchTable.getCustIdProofPk());
			RequestContext.getCurrentInstance().execute("remarksactivate.show();");

		}

	}

public void remarksEnteredActivate(){
	CreateSearchTable createSearchTable = new CreateSearchTable();
	createSearchTable.setIsActive(Constants.Yes);
 	createSearchTable.setModifiedBy(sessionStateManage.getUserName());
 	createSearchTable.setCustomerPk(getCustomerPk());
 	createSearchTable.setRemarks(getRemarks());
 	createSearchTable.setCustIdProofPk(getCustIdProofPk());
 	createSearchTable.setIdStatus(Constants.Yes);
 	createSearchTable.setModifiedBy(sessionStateManage.getUserName());
	getIsearchCustomerService().ActivateRecord(createSearchTable );
	RequestContext.getCurrentInstance().execute("successfullyactive.show();");
	clearAll();
}




public void remarksEntered(){
	
	if(getFinalData() != null && finalData.size() != 0){
		if(finalData.size() == 1){
			CreateSearchTable createSearchTable = new CreateSearchTable();
			createSearchTable.setIsActive(Constants.D);
			createSearchTable.setIdStatus(Constants.D);
			createSearchTable.setRemarks(getRemarks());
			createSearchTable.setDeactvatedBy(sessionStateManage.getUserName());
			createSearchTable.setDeactivatedDate(new Date());
			createSearchTable.setCustomerPk(getCustomerPk());
			createSearchTable.setCustIdProofPk(getCustIdProofPk());
			getIsearchCustomerService().update(createSearchTable);
		}else{
			Boolean checkCustStatus = true;
			for (CreateSearchTable deactive : finalData) {
				if(deactive.getCustIdProofPk() != null && deactive.getCustIdProofPk().compareTo(getCustIdProofPk()) != 0){
					if(deactive.getIdStatus() != null && deactive.getIdStatus().equalsIgnoreCase(Constants.Yes)){
						checkCustStatus = false;
						break;
					}
				}
			}
			
			// multiple need to keep one active
			CreateSearchTable createSearchTable = new CreateSearchTable();
			if(checkCustStatus){
				createSearchTable.setIsActive(Constants.D);
			}else{
				createSearchTable.setIsActive(Constants.Yes);
			}
			
			createSearchTable.setIdStatus(Constants.D);
			createSearchTable.setRemarks(getRemarks());
			createSearchTable.setDeactvatedBy(sessionStateManage.getUserName());
			createSearchTable.setDeactivatedDate(new Date());
			createSearchTable.setCustomerPk(getCustomerPk());
			createSearchTable.setCustIdProofPk(getCustIdProofPk());
			getIsearchCustomerService().update(createSearchTable);
		}
	}
	
	RequestContext.getCurrentInstance().execute("successfullydeactive.show();");
	clearAll();
}
public void clearRemarks(){
	setRemarks( null);
	
}

public void clearAll(){
	finalData.clear();
	setCountryId(null);
	setNationality(null);
	setIdNumber(null);
	setIdType(null);
	setCustomerReference(null);
	setApprovedBy(null);
	setApprovedDate(null);
	mapNationalityList.clear();
}
public void exit() {
	
	try {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	} catch (Exception e) {
		log.error(":::::::::::::::::::Problem Ocuured in Exit Button:::::::::::::");
	}

}
	 	

}

class CustomerIdProoofComp implements Comparator<CustomerIdProof> {

	@Override
	public int compare(CustomerIdProof e1, CustomerIdProof e2) {
		if(e1.getIdentityExpiryDate() != null && e2.getIdentityExpiryDate() != null){
			return e1.getIdentityExpiryDate().compareTo(e2.getIdentityExpiryDate());
		}
		return 1;
	}
}

