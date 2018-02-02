package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.kioskpingeneration.bean.KioskPinGenerationBean;
import com.amg.exchange.registration.bean.SearchEntityBean;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ISearchCustomerService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("cusRegistrationCustomerSearchBean")
@Scope("session")
public class CusRegistrationCustomerSearchBean<T> implements Serializable {

	Logger log = Logger.getLogger(CusRegistrationCustomerSearchBean.class);
	private SessionStateManage sessionStateManage=new SessionStateManage();

	private static final long serialVersionUID = 1L;
	private String firstName=null;
	private String middleName=null;
	private String idNumber=null;
	private String lastName=null;
	private BigDecimal nationality=null;
	private BigDecimal idType=null;
	private String idTypeName=null;

	private String mob=null;
	private Date dob=null;
	private String cust_id=null;
	private BigDecimal countryId=null;

	private Boolean booPass;



	private List<CustomerIdProof> lstCustomer = new ArrayList<CustomerIdProof>();
	private Map<BigDecimal,String> mapNationalityList = new HashMap<BigDecimal, String>();
	private List<BizComponentDataDesc> idList;
	private List<CreateSearchTable> finalData=new CopyOnWriteArrayList<CreateSearchTable>();

	@Autowired
	ISearchCustomerService<T> isearchCustomerService;

	@Autowired
	IGeneralService<T> igeneralService;

	/*@Autowired*/


	CustomerRegistrationBranchBean<T> customerRegistrationBranchBean;

	@Autowired
	KioskPinGenerationBean<T> kioskPinGeneration;


	@PostConstruct
	public void createCustomerBean() {
		customerRegistrationBranchBean = new CustomerRegistrationBranchBean();
	}


	public String getIdTypeName() {
		return idTypeName;
	}

	public void setIdTypeName(String idTypeName) {
		this.idTypeName = idTypeName;
	}

	public Map<BigDecimal, String> getMapNationalityList() {
		return mapNationalityList;
	}

	public void setMapNationalityList(Map<BigDecimal, String> mapNationalityList) {
		this.mapNationalityList = mapNationalityList;
	}

	public List<CustomerIdProof> getLstCustomer() {
		return lstCustomer;
	}

	public void setLstCustomer(List<CustomerIdProof> lstCustomer) {
		this.lstCustomer = lstCustomer;
	}

	public ISearchCustomerService<T> getIsearchCustomerService() {
		return isearchCustomerService;
	}

	public void setIsearchCustomerService(
			ISearchCustomerService<T> isearchCustomerService) {
		this.isearchCustomerService = isearchCustomerService;
	}

	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
	}




	public Boolean getBooPass() {
		return booPass;
	}

	public void setBooPass(Boolean booPass) {
		this.booPass = booPass;
	}

	public List<CreateSearchTable> getFinalData() {
		return finalData;
	}

	public void setFinalData(List<CreateSearchTable> finalData) {
		this.finalData = finalData;
	}

	public List<BizComponentDataDesc> getIdList() {
		return idList;
	}

	public void setIdList(List<BizComponentDataDesc> idList) {
		this.idList = idList;
	}

	public BigDecimal getIdType() {
		return idType;
	}
	public void setIdType(BigDecimal idType) {
		this.idType = idType;
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


	public List<CountryMasterDesc> getNationalityNameList() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		return getIgeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("languageId"):"1"));
	}


	public void setNationalityListMap(){

		SessionStateManage sessionStateManage = new SessionStateManage();
		if(mapNationalityList.size()==0){
			List<CountryMasterDesc> lstCountryMasterDesc = getIgeneralService().getNationalityList(
					new BigDecimal(sessionStateManage.isExists("languageId")?sessionStateManage.getSessionValue("lanaguageId"):"1")
					);
			for(CountryMasterDesc countryMasterDesc: lstCountryMasterDesc){
				mapNationalityList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getNationality());
			}
		}
	}

	public void getDataForAllCustomer() {
		finalData.clear();

		try{

			boolean search = false;
			if(getNationality()!=null && getFirstName().length()!=0 && getFirstName()!=null){
				search = true;
			}else if(getNationality()!=null && getLastName().length()!=0 && getLastName()!=null){
				search = true;
			}else if(getNationality()!=null && getDob()!=null){
				search = true;
			}else if(getMob()!=null && getMob().length()!=0 && getMob()!=null){
				search = true;
			}else if(getIdType()!=null && getIdNumber().length()!=0 && getIdNumber()!=null){
				search = true;
			}else{
				search = false;
			}

			if(search){
				//if(getNationality().intValue()!=0 || getIdType().intValue()!=0  || getDob()!=null ){

				setNationalityListMap();
				SearchEntityBean searchEntityBean = new SearchEntityBean();
				searchEntityBean.setNationalityId(getNationality());
				searchEntityBean.setIdType(getIdType());
				searchEntityBean.setIdNumber(getIdNumber());
				searchEntityBean.setFirstName(getFirstName());
				searchEntityBean.setLastName(getLastName());
				searchEntityBean.setDob(getDob());
				searchEntityBean.setMobileNumber(getMob());

				//lstCustomer =  getIsearchCustomerService().searchAllCustomer(searchEntityBean);
				lstCustomer = getIsearchCustomerService().searchCustomerEnquiryForAllCustomer(searchEntityBean);

				if(lstCustomer.size()>0){
					CreateSearchTable createSearchTable = new CreateSearchTable();

					if(((getNationality()!=null)&&(getFirstName()!=null&&getFirstName().length()!=0||getLastName()!=null&&getLastName().length()!=0))||(getDob()!=null&&getNationality()!=null)){		
						for(CustomerIdProof customerIdProof: lstCustomer){
							int count=0;


							createSearchTable = new CreateSearchTable();
							createSearchTable.setIdNumber(customerIdProof.getIdentityInt());
							createSearchTable.setCustomerTyId(customerIdProof.getFsCustomer().getCustomerId());
							createSearchTable.setIdType(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
							createSearchTable.setIdTypeName(igeneralService.getSearchIdType(sessionStateManage.getLanguageId(), customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
							createSearchTable.setFirstName(customerIdProof.getFsCustomer().getFirstName());
							createSearchTable.setLastName(customerIdProof.getFsCustomer().getLastName());
							createSearchTable.setShortName(customerIdProof.getFsCustomer().getShortName());
							createSearchTable.setMiddleName(customerIdProof.getFsCustomer().getMiddleName());
							createSearchTable.setCustomerName(nullCheck(customerIdProof.getFsCustomer().getFirstName()) + " " + nullCheck(customerIdProof.getFsCustomer().getMiddleName()) + " " + nullCheck(customerIdProof.getFsCustomer().getLastName()));
							createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));

							try{
								createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
							}catch(Exception e){}
							createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
							createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
							createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
							createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
							createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
							
							if(customerIdProof.getIdentityExpiryDate() != null){
								DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								String expiryDate = null;
								expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
								createSearchTable.setIdExpiryDate(expiryDate);
							}

							if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
								createSearchTable.setCurrentStatus(Constants.ACTIVE);
								createSearchTable.setDisableLink(false);
							}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
								createSearchTable.setCurrentStatus(Constants.DELETE);
								createSearchTable.setDisableLink(true);
							}else{
								createSearchTable.setCurrentStatus("");
								createSearchTable.setDisableLink(false);
							}

							if(finalData.size()>0){
								for(CreateSearchTable createSearch :finalData){
									if(createSearch.getIdNumber().equalsIgnoreCase(customerIdProof.getIdentityInt())){
										count++;
									}
								}
								if(count==0){
									finalData.add(createSearchTable);  
								}
							}
							else{
								finalData.add(createSearchTable); 
							}

						}

					}else{  
						CustomerIdProof	customerIdProof=lstCustomer.get(0);
						createSearchTable = new CreateSearchTable();
						createSearchTable.setIdNumber(customerIdProof.getIdentityInt());
						createSearchTable.setCustomerTyId(customerIdProof.getFsCustomer().getCustomerId());
						createSearchTable.setIdType(customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId());
						createSearchTable.setIdTypeName(igeneralService.getSearchIdType(sessionStateManage.getLanguageId(), customerIdProof.getFsBizComponentDataByIdentityTypeId().getComponentDataId()));
						createSearchTable.setFirstName(customerIdProof.getFsCustomer().getFirstName());
						createSearchTable.setLastName(customerIdProof.getFsCustomer().getLastName());
						createSearchTable.setShortName(customerIdProof.getFsCustomer().getShortName());
						createSearchTable.setMiddleName(customerIdProof.getFsCustomer().getMiddleName());
						createSearchTable.setCustomerName(nullCheck(customerIdProof.getFsCustomer().getFirstName()) + " " + nullCheck(customerIdProof.getFsCustomer().getMiddleName()) + " " + nullCheck(customerIdProof.getFsCustomer().getLastName()));
						createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));

						try{
							createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
						}catch(Exception e){}
						createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
						createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
						createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
						createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
						createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
						
						if(customerIdProof.getIdentityExpiryDate() != null){
							DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
							String expiryDate = null;
							expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
							createSearchTable.setIdExpiryDate(expiryDate);
						}

						if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
							createSearchTable.setCurrentStatus(Constants.ACTIVE);
							createSearchTable.setDisableLink(false);
						}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
							createSearchTable.setCurrentStatus(Constants.DELETE);
							createSearchTable.setDisableLink(true);
						}else{
							createSearchTable.setCurrentStatus("");
							createSearchTable.setDisableLink(false);
						}

						finalData.add(createSearchTable);    
					}
				}else{
					RequestContext.getCurrentInstance().execute("empty.show();");
				}

				if(finalData.size()>0){ 
					setBooPass(true);
				}else{ 
					setBooPass(false);
				}
			}else{
				RequestContext.getCurrentInstance().execute("searchCriteria.show();");
			}

		}catch(Exception e){

		}
	}

	public void showCustomerRegistrationManualData(){


	}

	public void showCustomerRegistrationSmartCardData(){

	}


	public void clearValues() {  
		this.nationality = null ;
		this.idType = null;
		this.idNumber = null;  
		this.firstName = null;  
		this.lastName = null;
		this.mob = null;
		this.dob = null;
		setBooPass(false);
		finalData.clear();

	}
	/*public void exit() throws IOException {
		clearValues();
		 setBooPass(false);
	    finalData.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationbranch.xhtml");
	}*/

	public String exit(){

		HttpSession session = sessionStateManage.getSession();
		Boolean smartcardCheck=(Boolean)session.getAttribute("smartcardCheck");
		Boolean kioskPin=(Boolean)session.getAttribute("kioskPin");
		String returnString = "";
		clearValues();
		setBooPass(false);
		finalData.clear();
		if(smartcardCheck!=null && smartcardCheck){
			returnString = "customerSmartCardPage";
		}else  if(kioskPin!=null && kioskPin){
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			try {
				context.redirect("../kioskpingeneration/kioskpingeneration.xhtml");
			} catch (IOException e) {
				log.info("redirect problem: "+e);
			}

		}else{
			returnString = "customerManualPage";
		}
		session.removeAttribute("smartcardCheck");
		session.removeAttribute("kioskPin");
		return returnString;

		// FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationbranch.xhtml");
	}


	public String showCustomerDataManual(CreateSearchTable createSearchTable) throws ParseException{
		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("customerId", createSearchTable.getIdNumber().toString());
		session.setAttribute("customerReference", createSearchTable.getCustomerRef());
		Boolean smartcardCheck=(Boolean)session.getAttribute("smartcardCheck");
		Boolean kioskPin=(Boolean)session.getAttribute("kioskPin");
		String returnString = "";
		clearValues();
		setBooPass(false);
		finalData.clear();
		if(smartcardCheck!=null && smartcardCheck){
			returnString = "customerSmartCardPage";

		}else  if(kioskPin!=null && kioskPin){
			showData(createSearchTable);

		}else{
			returnString = "customerManualPage";
		}
		session.removeAttribute("smartcardCheck");
		session.removeAttribute("kioskPin");
		return returnString;
	}


	public void showCustomerDetails(){
		getDataForAllCustomer();
	}

	public void validateName(FacesContext context, UIComponent component, Object value) throws ValidatorException {

		if(value.toString().length()!=0){
			if (value.toString().length()>=2) {

			} else {

				FacesMessage msg = new FacesMessage("atlease2", "Name must be atleast two charcter");
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}
		}

	}


	public void showData(CreateSearchTable obj) throws ParseException { 

		clearValues();
		kioskPinGeneration.populateSearchValue(obj.getIdNumber(), obj.getCustomerRef());
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			context.redirect("../kioskpingeneration/kioskpingeneration.xhtml");
		} catch (IOException e) {
			log.info("redirect problem: "+e);
		}
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}


}
