package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BizComponentDataDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.bean.ForeignCurrencyPurchaseBean;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.intercompany.bean.IntraCompanyTrnxBean;
import com.amg.exchange.online.bean.BranchPlaceOrder;
import com.amg.exchange.registration.bean.SearchEntityBean;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ISearchCustomerService;
import com.amg.exchange.remittance.bean.PersonalRemittanceBean;
import com.amg.exchange.treasury.bean.SpecialCustomerDealRequestBean;
import com.amg.exchange.treasury.bean.SupplierBean;
import com.amg.exchange.treasury.bean.SupplierDataTableBean;
import com.amg.exchange.treasury.deal.supplier.model.TreasuryCustomerSupplier;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.ISupplierService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.wu.bean.WUTranxFileUploadBean;
import com.amg.exchange.wu.bean.WesternUnionTransferBean;

@Component("searchCustomer")
/*@SessionScoped*/
@Scope("session")
public class SearchCustomerManageBean<T> implements Serializable {

	Logger log = Logger.getLogger(SearchCustomerManageBean.class);
	private SessionStateManage sessionStateManage = new SessionStateManage();

	private static final long serialVersionUID = 1L;
	private String firstName = null;
	private String middleName = null;
	private String idNumber = null;
	private String lastName = null;
	private BigDecimal nationality = null;
	private BigDecimal idType = null;
	private String idTypeName = null;
	private String signatureSpecimen = null;

	private String mob = null;
	private Date dob = null;
	private String cust_id = null;
	private BigDecimal countryId = null;
	private Boolean booPass = false;
	private String custType = null;

	private List<CustomerIdProof> lstCustomer = new ArrayList<CustomerIdProof>();

	private Map<BigDecimal, String> mapNationalityList = new HashMap<BigDecimal, String>();
	private List<BizComponentDataDesc> idList;
	private List<CreateSearchTable> finalData = new ArrayList<CreateSearchTable>();

	private List<Customer> enquiryList = new ArrayList<Customer>();

	@Autowired
	ISearchCustomerService<T> isearchCustomerService;

	@Autowired
	IGeneralService<T> igeneralService;

	@Autowired
	ISupplierService<T> iSupplierService;

	/*
	 * @Autowired CustomerRegistrationBranchBean<T>
	 * customerRegistrationBranchBean;
	 */

	@PostConstruct
	public void createBeans() {
		//foreignCurrencyPurchaseBean = new ForeignCurrencyPurchaseBean<T>();
		specialCustomerDealRequestBean = new SpecialCustomerDealRequestBean<T>();
		supplierBean = new SupplierBean<T>();
	}

	/* @Autowired */
	//ForeignCurrencyPurchaseBean<T> foreignCurrencyPurchaseBean;

	/* @Autowired */
	SpecialCustomerDealRequestBean<T> specialCustomerDealRequestBean;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	/*@Autowired */
	SupplierBean<T> supplierBean;

	@Autowired
	ApplicationContext appContext;

	/*
	 * @Autowired //ForeignCurrencySaleBean<T> foreignCurrencySaleBean;
	 */

	private Boolean booforeignCurrencyPurchase = false;
	private Boolean boospecialCustomerDealRequest = false;
	private Boolean boosupplier = false;
	private Boolean boopersonalRemittanceBean = false;
	private Boolean boowuBean = false;
	private Boolean booFirstTime;
	private Boolean booInterCompanyTrnx = false;
	private String searchrequestType;
	private Boolean booPlaceOrder=false;
	private Boolean booWUFileUpload=false;





	public Boolean getBooWUFileUpload() {
		return booWUFileUpload;
	}

	public void setBooWUFileUpload(Boolean booWUFileUpload) {
		this.booWUFileUpload = booWUFileUpload;
	}

	public Boolean getBooPlaceOrder() {
		return booPlaceOrder;
	}

	public void setBooPlaceOrder(Boolean booPlaceOrder) {
		this.booPlaceOrder = booPlaceOrder;
	}

	public Boolean getBoowuBean() {
		return boowuBean;
	}

	public void setBoowuBean(Boolean boowuBean) {
		this.boowuBean = boowuBean;
	}

	public Boolean getBoopersonalRemittanceBean() {
		return boopersonalRemittanceBean;
	}

	public void setBoopersonalRemittanceBean(Boolean boopersonalRemittanceBean) {
		this.boopersonalRemittanceBean = boopersonalRemittanceBean;
	}

	public Boolean getBooFirstTime() {
		return booFirstTime;
	}

	public void setBooFirstTime(Boolean booFirstTime) {
		this.booFirstTime = booFirstTime;
	}

	public Boolean getBooInterCompanyTrnx() {
		return booInterCompanyTrnx;
	}

	public void setBooInterCompanyTrnx(Boolean booInterCompanyTrnx) {
		this.booInterCompanyTrnx = booInterCompanyTrnx;
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

	public void setIsearchCustomerService(ISearchCustomerService<T> isearchCustomerService) {
		this.isearchCustomerService = isearchCustomerService;
	}

	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
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

	public Boolean getBooPass() {
		return booPass;
	}

	public void setBooPass(Boolean booPass) {
		this.booPass = booPass;
	}

	private BigDecimal individualIdType = new BigDecimal(1);

	public List<CountryMasterDesc> getNationalityNameList() {
		List<CountryMasterDesc> list=null;
		try{
			SessionStateManage sessionStateManage = new SessionStateManage();
			list=getIgeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
		}
		return list;
	}

	/*
	 * public void popIdType(AjaxBehaviorEvent e) { {
	 * 
	 * idList = new ArrayList<BizComponentDataDesc>();
	 * 
	 * SessionStateManage sessionStateManage = new SessionStateManage();
	 * setIdList(getIsearchCustomerService() .getIdentityTypes( new BigDecimal(
	 * sessionStateManage.isExists("languageId") ? sessionStateManage
	 * .getSessionValue("languageId") : "" + 1), individualIdType));
	 * 
	 * }
	 * 
	 * this.idType = null; finalData = new FinalArrayList<CreateSearchTable>();
	 * setBooPass(true); }
	 */

	public void setNationalityListMap() {
		try{
			SessionStateManage sessionStateManage = new SessionStateManage();
			if (mapNationalityList.size() == 0) {
				List<CountryMasterDesc> lstCountryMasterDesc = getIgeneralService().getNationalityList(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("lanaguageId") : "1"));
				for (CountryMasterDesc countryMasterDesc : lstCountryMasterDesc) {
					mapNationalityList.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getNationality());
				}
			}
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void getDataForAllCustomer() {
		try {

			boolean search = false;
			if (getNationality() != null && getFirstName().length() != 0 && getFirstName() != null) {
				search = true;
			} else if (getNationality() != null && getLastName().length() != 0 && getLastName() != null) {
				search = true;
			} else if (getNationality() != null && getDob() != null) {
				search = true;
			} else if (getMob() != null && getMob().length() != 0 && getMob() != null) {
				search = true;
			} else if (getIdType() != null && getIdNumber().length() != 0 && getIdNumber() != null) {
				search = true;
			} else {
				search = false;
			}

			if (search) {
				// if(getNationality().intValue()!=0 || getIdType().intValue()!=0 ||
				// getDob()!=null ){

				setNationalityListMap();
				SearchEntityBean searchEntityBean = new SearchEntityBean();
				searchEntityBean.setNationalityId(getNationality());
				searchEntityBean.setIdType(getIdType());
				if(getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")){
					searchEntityBean.setIdNumber(getIdNumber().trim());
				}
				searchEntityBean.setFirstName(getFirstName());
				searchEntityBean.setLastName(getLastName());
				searchEntityBean.setDob(getDob());
				searchEntityBean.setMobileNumber(getMob());

				finalData = new ArrayList<CreateSearchTable>();

				lstCustomer = new ArrayList<CustomerIdProof>();

				lstCustomer = getIsearchCustomerService().searchCustomerEnquiryForAllCustomer(searchEntityBean);
				//lstCustomer = getIsearchCustomerService().searchAllCustomer(searchEntityBean);

				if (lstCustomer.size() > 0) {
					CreateSearchTable createSearchTable = new CreateSearchTable();
					System.out.println(":::::::::::::::::::::Customer List Size="+lstCustomer.size());
					//CustomerIdProof customerIdProof = lstCustomer.get(0);
					if((getNationality()!=null && (getFirstName()!=null&&getFirstName().length()!=0||getLastName()!=null&&getLastName().length()!=0)) || (getDob()!=null && getNationality()!=null) || (getIdType() != null && getIdNumber() != null)){				
						System.out.println(":::::::::::::::::::::if loop::::::::::::::::::::::");
						for (CustomerIdProof customerIdProof : lstCustomer) {
							int count=0;
							try {

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
								createSearchTable.setSundryDebtorRef(customerIdProof.getFsCustomer().getSundryDebtorReference());
								createSearchTable.setCustomerPk(customerIdProof.getFsCustomer().getCustomerId());
								createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));
								createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
								createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
								createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
								createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
								createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
								createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
								createSearchTable.setCusTypeIdForSpeclcus(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue());

								if(customerIdProof.getIdentityExpiryDate() != null){

									DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
									Date currentDate = null;
									Date custExipreDate = null;
									try {
										currentDate = formatter1.parse(formatter1.format(new Date()));
										custExipreDate = formatter1.parse(formatter1.format(customerIdProof.getIdentityExpiryDate()));
									} catch (ParseException e) {
										e.printStackTrace();
									}

									if(custExipreDate.after(currentDate) || custExipreDate.equals(currentDate)){
										if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
											createSearchTable.setCurrentStatus(Constants.ACTIVE);
											createSearchTable.setDisableLink(false);
										}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
											createSearchTable.setCurrentStatus(Constants.DELETE);
											createSearchTable.setDisableLink(true);
										}else{
											createSearchTable.setCurrentStatus("");
											createSearchTable.setDisableLink(true);
										}
									}else{
										DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
										String expiryDate = null;
										expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
										createSearchTable.setCurrentStatus("Date Expired :" + expiryDate);
										createSearchTable.setDisableLink(true);
									}

								}else{
									// not available Identity Expire Date
									createSearchTable.setCurrentStatus("");
									createSearchTable.setDisableLink(true);
								}

								finalData.add(createSearchTable); 

								/*if(finalData.size()>0){
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
								}*/
							}catch(Exception exception){
								log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
								setErrorMessage(exception.getMessage());
								RequestContext.getCurrentInstance().execute("error.show();");
								return;
							}
							//finalData.add(createSearchTable);

						}
					}else{
						CustomerIdProof customerIdProof = lstCustomer.get(0);
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
						createSearchTable.setSundryDebtorRef(customerIdProof.getFsCustomer().getSundryDebtorReference());
						createSearchTable.setCustomerPk(customerIdProof.getFsCustomer().getCustomerId());
						createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));
						createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
						createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
						createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
						createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
						createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
						createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
						createSearchTable.setCusTypeIdForSpeclcus(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue());

						if(customerIdProof.getIdentityExpiryDate() != null){

							DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
							Date currentDate = null;
							Date custExipreDate = null;
							try {
								currentDate = formatter1.parse(formatter1.format(new Date()));
								custExipreDate = formatter1.parse(formatter1.format(customerIdProof.getIdentityExpiryDate()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if(custExipreDate.after(currentDate) || custExipreDate.equals(currentDate)){
								if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
									createSearchTable.setCurrentStatus(Constants.ACTIVE);
									createSearchTable.setDisableLink(false);
								}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
									createSearchTable.setCurrentStatus(Constants.DELETE);
									createSearchTable.setDisableLink(true);
								}else{
									createSearchTable.setCurrentStatus("");
									createSearchTable.setDisableLink(true);
								}
							}else{
								DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								String expiryDate = null;
								expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
								createSearchTable.setCurrentStatus("Date Expired :" + expiryDate);
								createSearchTable.setDisableLink(true);
							}

						}else{
							// not available Identity Expire Date
							createSearchTable.setCurrentStatus("");
							createSearchTable.setDisableLink(true);
						}

						finalData.add(createSearchTable);

					}//new condition
				} else {
					RequestContext.getCurrentInstance().execute("empty.show();");
				}
				if (finalData.size() > 0) {
					setBooPass(true);
				} else {
					setBooPass(false);
				}
				setFinalData(finalData);

			} else {
				setFinalData(null);
				RequestContext.getCurrentInstance().execute("searchCriteria.show();");
			}
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	/*
	 * public String showData(CreateSearchTable obj) { resetData();
	 * getCustomerRegistrationBranchBean().ShowFromSearchPage(obj.getIdNumber(),
	 * obj.getIdType()); return "branchPage"; }
	 */
	public void resetData() {
		setNationality(null);
		setIdType(null);
		setIdTypeName(null);
		setIdNumber(null);
		setLastName(null);
		setFirstName(null);
		setMob(null);
		setDob(null);
		setBooPass(false);
		setFinalData(null);
		// finalData.clear();
	}

	public String showSpCustomerData(CreateSearchTable obj) {

		if (specialCustomerDealRequestBean.getIsFromSpecialCustomer() == true) {
			resetData();
		}
		
		specialCustomerDealRequestBean.setName(nullCheck(obj.getFirstName()) + " " + nullCheck(obj.getMiddleName()) + " " + nullCheck(obj.getLastName()));
		if(obj.getCustomerRef()!=null){
			specialCustomerDealRequestBean.setId(obj.getCustomerRef().toString());
		}
		if(obj.getCustomerTyId()!=null){
			specialCustomerDealRequestBean.setCustomerId(Integer.parseInt(obj.getCustomerTyId().toString()));
		}
		specialCustomerDealRequestBean.setCustomerTypeId(obj.getCusTypeIdForSpeclcus());
		if (specialCustomerDealRequestBean.getCustomerTypeId() == igeneralService.getComponentId(Constants.CUSTOMERTYPE_CORP, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
			custType = "C";
		} else if (specialCustomerDealRequestBean.getCustomerTypeId() == igeneralService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()) {
			custType = "I";
		}
		specialCustomerDealRequestBean.validDate(specialCustomerDealRequestService.getValidUpTo(custType));

		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("searchspecialCustomer", specialCustomerDealRequestBean);
		lstCustomer.clear();
		resetData();
		clearBooleanValues();
		return "spCustomerPage";
	}

	public void showCustomerRegistrationData(CreateSearchTable obj) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationbranch.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showSpplierData(CreateSearchTable obj) {
		try {
			if (supplierBean.getIsFromSupplierBean() == true) {
				resetData();
			}
			supplierBean.setCustomerName(obj.getFirstName());
			supplierBean.setCustomerId(obj.getCustomerRef().toString());
			supplierBean.setCustomerTypeId(obj.getCustomerTyId());
			supplierBean.setSundryDeptolRef(obj.getSundryDebtorRef());
			String customerName=obj.getFirstName()+" "+obj.getMiddleName()+" "+obj.getShortName()+" "+obj.getLastName();
			supplierBean.setLocalName(customerName);
			supplierBean.setMobileNumber(obj.getMob());
			supplierBean.setNationality(obj.getNationalityId());
			supplierBean.setNationallityName(obj.getNationality());
			supplierBean.setEmailId(obj.getEmail());
			supplierBean.setCustomerPK(obj.getCustomerPk());
			List<SupplierDataTableBean> lstSupplierDetails=new ArrayList<SupplierDataTableBean>();
			if(obj.getCustomerTyId()!=null){
				List<TreasuryCustomerSupplier> treasuryCustomerSupplierList=iSupplierService.getAllTreasuryCustomerSupplier(obj.getCustomerTyId());
				if(treasuryCustomerSupplierList.size()>0){
					supplierBean.lstSupplierDetails.clear();

					for(TreasuryCustomerSupplier treasuryCustomerSupplier:treasuryCustomerSupplierList){
						SupplierDataTableBean supplierDataTableBean=new SupplierDataTableBean();
						supplierDataTableBean.setTreasuryCustomerPk(treasuryCustomerSupplier.getDealCustomerSupplierId());//treasury customer supplier PK
						supplierDataTableBean.setCustomerRefId(treasuryCustomerSupplier.getDealSupplierCustomer().getCustomerReference().toString());
						supplierDataTableBean.setCustomerName(treasuryCustomerSupplier.getCustomerName());
						supplierDataTableBean.setMobileNumber(treasuryCustomerSupplier.getDealSupplierCustomer().getMobile());
						BigDecimal nationalityId=treasuryCustomerSupplier.getDealSupplierCustomer().getFsCountryMasterByNationality().getCountryId();
						String nationalityName = iSupplierService.getNationalityName(nationalityId);
						if(nationalityName!=null){
							supplierDataTableBean.setNationality(nationalityName);
						}
						supplierDataTableBean.setEmail(treasuryCustomerSupplier.getDealSupplierCustomer().getEmail());
						supplierDataTableBean.setSundryDebtorRef(treasuryCustomerSupplier.getDealSupplierCustomer().getSundryDebtorReference());
						supplierDataTableBean.setBankId(treasuryCustomerSupplier.getBankMaster().getBankId());
						supplierDataTableBean.setBankName(treasuryCustomerSupplier.getBankMaster().getBankFullName());
						supplierDataTableBean.setBankDescription(treasuryCustomerSupplier.getBankMaster().getBankFullName());
						supplierDataTableBean.setCurrencyId(treasuryCustomerSupplier.getDealSupplierCurrency().getCurrencyId());
						supplierDataTableBean.setCurrencyName(treasuryCustomerSupplier.getDealSupplierCurrency().getCurrencyName());
						supplierDataTableBean.setAccountNumber(treasuryCustomerSupplier.getBankAccountNo().toString());
						supplierDataTableBean.setCustomerPk(treasuryCustomerSupplier.getDealSupplierCustomer().getCustomerId());
						lstSupplierDetails.add(supplierDataTableBean);
					}



				}
			}

			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("searchSupplier", supplierBean);

			supplierBean.setData(lstSupplierDetails);
			lstCustomer.clear();
			resetData();

			clearBooleanValues();

			// specialCustomerDealRequestBean.setCustomerTypeId(Integer.parseInt(foreignCurrencyPurchaseService.dataCust(obj.getIdNumber()).get(0).getFsBizComponentDataByCustomerTypeId().getComponentDataId().toString()));
			/*
			 * if(supplierBean.getCustomerTypeId()==constants.
			 * CORPORATE_COMPONENT_ID_FOR_SUPPLIER){ custType="C"; }else
			 * if(supplierBean
			 * .getCustomerTypeId()==constants.INDIVIDUAL_COMPONENT_ID_FOR_SUPPLIER
			 * ){ custType="I"; }
			 */

			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/supplier.xhtml");

		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::" + exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;   
		}
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}

	public void showDataFcSale(CreateSearchTable obj) throws SQLException {/*
	 * InputStream
	 * stream
	 * =
	 * null
	 * ;
	 * Blob
	 * blob
	 * =
	 * null
	 * ;
	 * StreamedContent
	 * myImage
	 * ;
	 * 
	 * if(
	 * foreignCurrencySaleBean
	 * .
	 * getIsFromFCSale
	 * (
	 * )
	 * ==
	 * true
	 * )
	 * {
	 * resetData
	 * (
	 * )
	 * ;
	 * }
	 * foreignCurrencySaleBean
	 * .
	 * setMobile
	 * (
	 * obj
	 * .
	 * getMob
	 * (
	 * )
	 * )
	 * ;
	 * foreignCurrencySaleBean
	 * .
	 * setId
	 * (
	 * obj
	 * .
	 * getIdNumber
	 * (
	 * )
	 * )
	 * ;
	 * foreignCurrencySaleBean
	 * .
	 * setName
	 * (
	 * obj
	 * .
	 * getFirstName
	 * (
	 * )
	 * )
	 * ;
	 * foreignCurrencySaleBean
	 * .
	 * setCustomerId
	 * (
	 * Integer
	 * .
	 * parseInt
	 * (
	 * foreignCurrencyPurchaseService
	 * .
	 * dataCust
	 * (
	 * obj
	 * .
	 * getIdNumber
	 * (
	 * )
	 * )
	 * .
	 * get
	 * (
	 * 0
	 * )
	 * .
	 * getFsCustomer
	 * (
	 * )
	 * .
	 * getCustomerId
	 * (
	 * )
	 * .
	 * toString
	 * (
	 * )
	 * )
	 * )
	 * ;
	 * 
	 * //
	 * by
	 * subramanian
	 * id
	 * proof
	 * loading
	 * 
	 * blob
	 * =
	 * foreignCurrencyPurchaseService
	 * .
	 * dataCust
	 * (
	 * obj
	 * .
	 * getIdNumber
	 * (
	 * )
	 * )
	 * .
	 * get
	 * (
	 * 0
	 * )
	 * .
	 * getFsDocumentImg
	 * (
	 * )
	 * .
	 * getImage
	 * (
	 * )
	 * ;
	 * 
	 * String
	 * signature
	 * =
	 * foreignCurrencyPurchaseService
	 * .
	 * getSignature
	 * (
	 * obj
	 * .
	 * getCustomerTyId
	 * (
	 * )
	 * )
	 * ;
	 * 
	 * int
	 * blobLength
	 * =
	 * (
	 * int
	 * )
	 * blob
	 * .
	 * length
	 * (
	 * )
	 * ;
	 * byte
	 * [
	 * ]
	 * blobAsBytes
	 * =
	 * blob
	 * .
	 * getBytes
	 * (
	 * 1
	 * ,
	 * blobLength
	 * )
	 * ;
	 * stream
	 * =
	 * new
	 * ByteArrayInputStream
	 * (
	 * blobAsBytes
	 * )
	 * ;
	 * myImage
	 * =
	 * new
	 * DefaultStreamedContent
	 * (
	 * stream
	 * ,
	 * "image/jpg"
	 * )
	 * ;
	 * foreignCurrencySaleBean
	 * .
	 * setMyImage
	 * (
	 * myImage
	 * )
	 * ;
	 * foreignCurrencySaleBean
	 * .
	 * setSignatureSpecimen
	 * (
	 * signature
	 * )
	 * ;
	 * 
	 * try
	 * {
	 * FacesContext
	 * .
	 * getCurrentInstance
	 * (
	 * )
	 * .
	 * getExternalContext
	 * (
	 * )
	 * .
	 * redirect
	 * (
	 * "../foreigncurrency/foreigncurrencysale.xhtml"
	 * )
	 * ;
	 * }
	 * catch
	 * (
	 * Exception
	 * e
	 * )
	 * {
	 * e
	 * .
	 * printStackTrace
	 * (
	 * )
	 * ;
	 * }
	 * /
	 * /
	 * End
	 * id
	 * proof
	 * loading
	 * 
	 * //
	 * foreignCurrencyPurchaseBean
	 * .
	 * setDocumentId
	 * (
	 * Integer
	 * .
	 * parseInt
	 * (
	 * foreignCurrencyPurchaseService
	 * .
	 * dataCust
	 * (
	 * obj
	 * .
	 * getIdNumber
	 * (
	 * )
	 * )
	 * .
	 * get
	 * (
	 * 0
	 * )
	 * .
	 * getFsDocumentImg
	 * (
	 * )
	 * .
	 * getImgId
	 * (
	 * )
	 * .
	 * toString
	 * (
	 * )
	 * )
	 * )
	 * ;
	 * /
	 * /
	 * return
	 * "fcSalePage"
	 * ;
	 */
	}

	public void resetValue() {
		this.nationality = null;
		this.idType = null;
		this.idNumber = null;
		this.firstName = null;
		this.lastName = null;
		this.mob = null;
		this.dob = null;
		setBooPass(false);
	}

	/**
	 * For Exit Button
	 * 
	 * @throws IOException
	 */
	/*
	 * public void exit() throws IOException {
	 * 
	 * if(specialCustomerDealRequestBean.getIsFromSpecialCustomer()==true){
	 * resetData();
	 * FacesContext.getCurrentInstance().getExternalContext().redirect
	 * ("../treasury/specialCustomer.xhtml"); }else
	 * if(foreignCurrencyPurchaseBean.getIsFromFCPurchage()==true){ resetData();
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(
	 * "../foreigncurrency/foreigncurrencypurchase.xhtml"); }else
	 * if(supplierBean.getIsFromSupplierBean()==true){ resetData();
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(
	 * "../treasury/supplier.xhtml"); }else if(getIsFromSearchCustomer()==true){
	 * if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){ resetData();
	 * FacesContext.getCurrentInstance().getExternalContext().redirect(
	 * "../registration/employeehome.xhtml"); }else{ resetData();
	 * FacesContext.getCurrentInstance
	 * ().getExternalContext().redirect("../registration/branchhome.xhtml"); }
	 * }else{ resetData();
	 * FacesContext.getCurrentInstance().getExternalContext()
	 * .redirect("../registration/customerregistrationbranch.xhtml"); }
	 * specialCustomerDealRequestBean.setIsFromSpecialCustomer(false);
	 * foreignCurrencyPurchaseBean.setIsFromFCPurchage(false);
	 * supplierBean.setIsFromSupplierBean(false);
	 * setIsFromSearchCustomer(false);
	 * 
	 * }
	 */

	public void exit() throws IOException {

		if (getSearchrequestType() != null && getSearchrequestType().equals("specialCustomerDealRequest")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/specialCustomer.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("foreignCurrencyPurchase")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencypurchase.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("supplier")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/supplier.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("personalRemittanceBean")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("wuBean")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("wuFileUpload")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");
		} else if (getSearchrequestType() != null && getSearchrequestType().equals("interCompanyTrnx")) {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/InterCompanyTrnx.xhtml");
		} else if (getIsFromSearchCustomer() == true) {
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				resetData();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				resetData();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} else {
			resetData();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationbranch.xhtml");
		}
		specialCustomerDealRequestBean.setIsFromSpecialCustomer(false);
		//foreignCurrencyPurchaseBean.setIsFromFCPurchage(false);
		supplierBean.setIsFromSupplierBean(false);
		setIsFromSearchCustomer(false);

	}

	public void resetCache() {
		this.nationality = null;
		this.idType = null;
		this.idNumber = null;
		this.firstName = null;
		this.lastName = null;
		this.mob = null;
		this.dob = null;
		setBooPass(false);
		setIsFromSearchCustomer(true);
		setBooSearchCustomer(true);
		supplierBean.setBooSupplierBean(false);
		supplierBean.setIsFromSupplierCustref(true);
		specialCustomerDealRequestBean.setBooSpCustomer(false);
		//foreignCurrencyPurchaseBean.setBooFc(false);
		specialCustomerDealRequestBean.setIsFromSpecialCustomer(false);
		//foreignCurrencyPurchaseBean.setIsFromFCPurchage(false);
		supplierBean.setIsFromSupplierBean(false);

		HttpSession session = sessionStateManage.getSession();
		session.setAttribute("request", "searchCustomer");
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			context.redirect("../search/searchcustomer.xhtml");
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	private Boolean isFromSearchCustomer = false;
	private Boolean booSearchCustomer = false;

	public Boolean getIsFromSearchCustomer() {
		return isFromSearchCustomer;
	}

	public void setIsFromSearchCustomer(Boolean isFromSearchCustomer) {
		this.isFromSearchCustomer = isFromSearchCustomer;
	}

	public Boolean getBooSearchCustomer() {
		return booSearchCustomer;
	}

	public void setBooSearchCustomer(Boolean booSearchCustomer) {
		this.booSearchCustomer = booSearchCustomer;
	}

	public void showCustomerDetails() throws Exception{
		/*if (specialCustomerDealRequestBean.getIsFromSpecialCustomer() == true) {
			getDataForAllCustomer();

		 * setBooSearchCustomer(false);
		 * specialCustomerDealRequestBean.setBooSpCustomer(true);
		 * foreignCurrencyPurchaseBean.setBooFc(false);
		 * supplierBean.setBooSupplierBean(false);

		} else if (foreignCurrencyPurchaseBean.getIsFromFCPurchage() == true) {
			getDataForAllCustomer();

		 * setBooSearchCustomer(false);
		 * specialCustomerDealRequestBean.setBooSpCustomer(false);
		 * foreignCurrencyPurchaseBean.setBooFc(true);
		 * supplierBean.setBooSupplierBean(false);

		} else if (supplierBean.getIsFromSupplierBean() == true) {
			getDataForAllCustomer();

		 * setBooSearchCustomer(true);
		 * specialCustomerDealRequestBean.setBooSpCustomer(false);
		 * foreignCurrencyPurchaseBean.setBooFc(false);
		 * supplierBean.setBooSupplierBean(true);

		} else {
			getDataForAllCustomer();

		 * setBooSearchCustomer(true);
		 * supplierBean.setBooSupplierBean(false);
		 * specialCustomerDealRequestBean.setBooSpCustomer(false);
		 * foreignCurrencyPurchaseBean.setBooFc(false);

		}*/

		getDataForAllCustomer();

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void customerenquiry() {
		// finalData.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "customerenquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../search/customerenquiry.xhtml");
			setBooSearchCustomer(false);
			resetData();
			finalData.clear();
		} catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void getDataForAllCustomerEnquiry() {

		try {

			boolean search = false;
			if (getNationality() != null && getFirstName().length() != 0 && getFirstName() != null) {
				search = true;
			} else if (getNationality() != null && getLastName().length() != 0 && getLastName() != null) {
				search = true;
			} else if (getNationality() != null && getDob() != null) {
				search = true;
			} else if (getMob() != null && getMob().length() != 0 && getMob() != null) {
				search = true;
			} else if (getIdType() != null && getIdNumber().length() != 0 && getIdNumber() != null) {
				search = true;
			} else {
				search = false;
			}

			if (search) {

				setNationalityListMap();
				SearchEntityBean searchEntityBean = new SearchEntityBean();
				searchEntityBean.setNationalityId(getNationality());
				searchEntityBean.setIdType(getIdType());
				if(getIdNumber() != null && !getIdNumber().equalsIgnoreCase("")){
					searchEntityBean.setIdNumber(getIdNumber().trim());
				}
				searchEntityBean.setFirstName(getFirstName());
				searchEntityBean.setLastName(getLastName());
				searchEntityBean.setDob(getDob());
				searchEntityBean.setMobileNumber(getMob());

				finalData = new ArrayList<CreateSearchTable>();

				lstCustomer = new ArrayList<CustomerIdProof>();

				//lstCustomer = getIsearchCustomerService().searchAllCustomer(searchEntityBean);
				lstCustomer = getIsearchCustomerService().searchCustomerEnquiryForAllCustomer(searchEntityBean);

				if (lstCustomer.size() > 0) {
					CreateSearchTable createSearchTable = new CreateSearchTable();
					System.out.println(":::::::::::::::::::::Customer List Size="+lstCustomer.size());
					//CustomerIdProof customerIdProof = lstCustomer.get(0);
					if((getNationality()!=null&&(getFirstName()!=null&&getFirstName().length()!=0||getLastName()!=null&&getLastName().length()!=0))||(getDob()!=null&&getNationality()!=null)){				
						System.out.println(":::::::::::::::::::::if loop::::::::::::::::::::::");
						for (CustomerIdProof customerIdProof : lstCustomer) {
							int count=0;
							try {

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
								createSearchTable.setSundryDebtorRef(customerIdProof.getFsCustomer().getSundryDebtorReference());
								createSearchTable.setCustomerPk(customerIdProof.getFsCustomer().getCustomerId());
								createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));
								createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
								createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
								createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
								createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
								createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
								createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
								createSearchTable.setCusTypeIdForSpeclcus(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue());
								createSearchTable.setCreatedBy(customerIdProof.getFsCustomer().getCreatedBy());
								createSearchTable.setCreatedDate(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getCreationDate()));

								if(customerIdProof.getIdentityExpiryDate() != null){

									DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
									Date currentDate = null;
									Date custExipreDate = null;
									try {
										currentDate = formatter1.parse(formatter1.format(new Date()));
										custExipreDate = formatter1.parse(formatter1.format(customerIdProof.getIdentityExpiryDate()));
									} catch (ParseException e) {
										e.printStackTrace();
									}
									if(custExipreDate.after(currentDate) || custExipreDate.equals(currentDate)){
										if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
											createSearchTable.setCurrentStatus(Constants.ACTIVE);
										}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
											createSearchTable.setCurrentStatus(Constants.DELETE);
										}else{
											createSearchTable.setCurrentStatus("");
										}
									}else{
										DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
										String expiryDate = null;
										expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
										createSearchTable.setCurrentStatus(expiryDate);
									}

								}else{
									// not available Identity Expire Date
									createSearchTable.setCurrentStatus("");
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
							} catch(Exception exception){
								log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
								setErrorMessage(exception.getMessage()); 
								RequestContext.getCurrentInstance().execute("error.show();");
								return;       
							}

						}
					}else{
						CustomerIdProof customerIdProof = lstCustomer.get(0);
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
						createSearchTable.setSundryDebtorRef(customerIdProof.getFsCustomer().getSundryDebtorReference());
						createSearchTable.setCustomerPk(customerIdProof.getFsCustomer().getCustomerId());
						createSearchTable.setNationality(mapNationalityList.get(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId()));
						createSearchTable.setDob(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getDateOfBirth()));
						createSearchTable.setMob(customerIdProof.getFsCustomer().getMobile());
						createSearchTable.setEmail(customerIdProof.getFsCustomer().getEmail());
						createSearchTable.setNationalityId(customerIdProof.getFsCustomer().getFsCountryMasterByNationality().getCountryId());
						createSearchTable.setCustomerRef(customerIdProof.getFsCustomer().getCustomerReference());
						createSearchTable.setCompanyName(customerIdProof.getFsCustomer().getCompanyName());
						createSearchTable.setCusTypeIdForSpeclcus(customerIdProof.getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue());
						createSearchTable.setCreatedBy(customerIdProof.getFsCustomer().getCreatedBy());
						createSearchTable.setCreatedDate(new SimpleDateFormat("dd-MM-yyyy").format(customerIdProof.getFsCustomer().getCreationDate()));

						if(customerIdProof.getIdentityExpiryDate() != null){

							DateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
							Date currentDate = null;
							Date custExipreDate = null;
							try {
								currentDate = formatter1.parse(formatter1.format(new Date()));
								custExipreDate = formatter1.parse(formatter1.format(customerIdProof.getIdentityExpiryDate()));
							} catch (ParseException e) {
								e.printStackTrace();
							}
							if(custExipreDate.after(currentDate) || custExipreDate.equals(currentDate)){
								if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.Yes)){
									createSearchTable.setCurrentStatus(Constants.ACTIVE);
								}else if(customerIdProof.getFsCustomer() != null && customerIdProof.getFsCustomer().getIsActive().equalsIgnoreCase(Constants.D)){
									createSearchTable.setCurrentStatus(Constants.DELETE);
								}else{
									createSearchTable.setCurrentStatus("");
								}
							}else{
								DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
								String expiryDate = null;
								expiryDate = formatter.format(customerIdProof.getIdentityExpiryDate());
								createSearchTable.setCurrentStatus(expiryDate);
							}

						}else{
							// not available Identity Expire Date
							createSearchTable.setCurrentStatus("");
						}

						finalData.add(createSearchTable);

					}//new condition
				} else {
					RequestContext.getCurrentInstance().execute("empty.show();");
				}
				if (finalData.size() > 0) {
					setBooPass(true);
				} else {
					setBooPass(false);
				}
				setFinalData(finalData);

			} else {
				setFinalData(null);
				RequestContext.getCurrentInstance().execute("searchCriteria.show();");
			}

		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
	}

	public void showCustomerEnquiryDetails() {

		getDataForAllCustomerEnquiry();
		setBooSearchCustomer(true);
		supplierBean.setBooSupplierBean(false);
		specialCustomerDealRequestBean.setBooSpCustomer(false);
		//foreignCurrencyPurchaseBean.setBooFc(false);

	}

	public void exitFromCustomerEnquiry() {
		resetData();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
		}

	}

	public void showDatta(CreateSearchTable obj) throws IOException {

		resetData();
		// getCustomerRegistrationBranchBean().fromSearchPage(obj.getIdNumber(),
		// obj.getCustomerRef());
		FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerregistrationbranch.xhtml");
	}

	public void requestType() throws Exception {

		if (getBooFirstTime() == null) {
			resetValue();
			setBooFirstTime(false);
		}

		//
		HttpSession session = sessionStateManage.getSession();
		String requestType = (String) session.getAttribute("request");

		log.info("requestType " + requestType);

		if (getSearchrequestType() != null && !getSearchrequestType().equals(requestType)) {
			resetValue();
		}
		if (requestType != null && requestType.equals("specialCustomerDealRequest")) {
			log.info("specialCustomerDealRequest : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(true);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		} else if (requestType != null && requestType.equals("foreignCurrencyPurchase")) {
			log.info("foreignCurrencyPurchase : " + requestType);
			setBooforeignCurrencyPurchase(true);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		} else if (requestType != null && requestType.equals("supplier")) {
			log.info("supplier : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(true);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		} else if (requestType != null && requestType.equals("personalRemittanceBean")) {
			log.info("personalRemittanceBean : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(true);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooSearchCustomer(false);
			setBoospecialCustomerDealRequest(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		}  else if (requestType != null && requestType.equals("wuBean")) {
			log.info("wuBean : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(true);
			setBooSearchCustomer(false);
			setBoospecialCustomerDealRequest(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		} else if(requestType != null &&  requestType.equals("interCompanyTrnx")){
			log.info("personalRemittanceBean : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooSearchCustomer(false);
			setBoospecialCustomerDealRequest(false);
			setBooInterCompanyTrnx(true);
			setSearchrequestType(requestType);
		}else if(requestType != null &&  requestType.equals("branchPlaceOrder")){
			log.info("branchPlaceOrder : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(true);
			setBooWUFileUpload(false);
			setBoowuBean(false);
			setBooSearchCustomer(false);
			setBoospecialCustomerDealRequest(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		}else if(requestType != null &&  requestType.equals("wuFileUpload")){
			log.info("branchPlaceOrder : " + requestType);
			setBooforeignCurrencyPurchase(false);
			setBoospecialCustomerDealRequest(false);
			setBoosupplier(false);
			setBoopersonalRemittanceBean(false);
			setBooPlaceOrder(false);
			setBooWUFileUpload(true);
			setBoowuBean(false);
			setBooSearchCustomer(false);
			setBoospecialCustomerDealRequest(false);
			setBooInterCompanyTrnx(false);
			setSearchrequestType(requestType);
		} else if(requestType != null &&  requestType.equals("searchCustomer")) {
			log.info("from serach Customer menu");
		}

	}

	public void clearBooleanValues() {
		setBooforeignCurrencyPurchase(null);
		setBoospecialCustomerDealRequest(null);
		setBoosupplier(null);
		setBoopersonalRemittanceBean(null);
		setBoowuBean(null);
		setBooInterCompanyTrnx(null);
	}

	public String getSearchrequestType() {
		return searchrequestType;
	}

	public void setSearchrequestType(String searchrequestType) {
		this.searchrequestType = searchrequestType;
	}

	public Boolean getBooforeignCurrencyPurchase() {
		return booforeignCurrencyPurchase;
	}

	public void setBooforeignCurrencyPurchase(Boolean booforeignCurrencyPurchase) {
		this.booforeignCurrencyPurchase = booforeignCurrencyPurchase;
	}

	public Boolean getBoospecialCustomerDealRequest() {
		return boospecialCustomerDealRequest;
	}

	public void setBoospecialCustomerDealRequest(Boolean boospecialCustomerDealRequest) {
		this.boospecialCustomerDealRequest = boospecialCustomerDealRequest;
	}

	public Boolean getBoosupplier() {
		return boosupplier;
	}

	public void setBoosupplier(Boolean boosupplier) {
		this.boosupplier = boosupplier;
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

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void showDataPersonalRemittance(CreateSearchTable obj) throws SQLException {
		try {
			PersonalRemittanceBean<T> personalRemittanceBean = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			personalRemittanceBean.setSelectCard(obj.getIdType());
			personalRemittanceBean.setIdNumber(obj.getIdNumber());
			lstCustomer.clear();
			resetData();
			clearBooleanValues();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showDataInterCompanyTrnx(CreateSearchTable obj) throws SQLException {
		try {
			IntraCompanyTrnxBean<T> interCompanyTrnx = (IntraCompanyTrnxBean)appContext.getBean("interCompanyTrnxBean");
			interCompanyTrnx.setSelectCard(obj.getIdType());
			interCompanyTrnx.setIdNumber(obj.getIdNumber());
			lstCustomer.clear();
			resetData();
			clearBooleanValues();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/InterCompanyTrnx.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showDataWesternUnion(CreateSearchTable obj) throws SQLException {
		try {
			WesternUnionTransferBean<T> westernUnionTransferBean = (WesternUnionTransferBean)appContext.getBean("wuBean");
			westernUnionTransferBean.setSelectCard(obj.getIdType());
			westernUnionTransferBean.setIdNumber(obj.getIdNumber());
			lstCustomer.clear();
			resetData();
			clearBooleanValues();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/westernuniontransfer.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showDataFc(CreateSearchTable obj) throws SQLException {
		try {
			resetData();
			ForeignCurrencyPurchaseBean<T> foreignCurrencyPurchaseBean = (ForeignCurrencyPurchaseBean)appContext.getBean("foreignCurrencyPurchaseBean");
			foreignCurrencyPurchaseBean.setMobile(obj.getMob());
			foreignCurrencyPurchaseBean.setId(obj.getIdNumber());
			String customerName = nullCheck(obj.getFirstName()) + " " + nullCheck(obj.getMiddleName()) + " " + nullCheck(obj.getLastName());
			foreignCurrencyPurchaseBean.setName(customerName);
			foreignCurrencyPurchaseBean.setIdRef(String.valueOf(obj.getCustomerRef()));
			foreignCurrencyPurchaseBean.setCustomerId(obj.getCustomerTyId());
			String signature = foreignCurrencyPurchaseService.getSignature(obj.getCustomerTyId());
			foreignCurrencyPurchaseBean.setSignatureSpecimen(signature);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencypurchase.xhtml");
			lstCustomer.clear();
			resetData();
			clearBooleanValues();

			foreignCurrencyPurchaseBean.searchOperationtoFcP();

		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} 

	public void showDataPlaceOrder(CreateSearchTable obj) throws SQLException {
		try {
			BranchPlaceOrder<T> branchPlaceOrder = (BranchPlaceOrder)appContext.getBean("branchPlaceOrder");
			branchPlaceOrder.setSelectCard(obj.getIdType());
			branchPlaceOrder.setIdNumber(obj.getIdNumber());
			lstCustomer.clear();
			resetData();
			clearBooleanValues();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/BranchPlaceOrder.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void showWuFileUpload(CreateSearchTable obj) throws SQLException {
		try {
			WUTranxFileUploadBean<T> wuTranxFileUploadBean = (WUTranxFileUploadBean)appContext.getBean("wuTranxFileUploadBean");
			wuTranxFileUploadBean.setIdType(obj.getIdType());
			wuTranxFileUploadBean.setSelectCard(obj.getIdType());
			wuTranxFileUploadBean.setIdTypeName(obj.getIdTypeName());
			wuTranxFileUploadBean.setIdNo(obj.getIdNumber());
			wuTranxFileUploadBean.setCustomerName(obj.getCustomerName());
			wuTranxFileUploadBean.setCustomerID(obj.getCustomerTyId());
			wuTranxFileUploadBean.goFromOldSmartCardpanel();
			lstCustomer.clear();
			resetData();
			clearBooleanValues();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../wu/wutranxfileuploaddetail.xhtml");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
