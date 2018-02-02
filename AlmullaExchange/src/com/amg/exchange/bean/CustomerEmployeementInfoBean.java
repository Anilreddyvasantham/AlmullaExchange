package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BizComponentData;
import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerEmploymentInfo;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.CustomerSponsor;
import com.amg.exchange.registration.model.IncomeRangeMaster;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.Relations;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.treasury.model.ArticleDetails;
import com.amg.exchange.treasury.model.ArticleMaster;
import com.amg.exchange.treasury.model.ArticleMasterDesc;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("customerEmployeeInfoBean")
@Scope("session")
public class CustomerEmployeementInfoBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// All Screen Fildes
	private String additinal1=null;
	private String articleDesCard=null;
	private BigDecimal article = null;
	private BigDecimal level = null;
	private BigDecimal incomeRange = null;
	private String dependantIdNumber=null;
	private String dependentName=null;
	private BigDecimal dependentRelationship=null;
//	private String dependentIdExpireDate =null;
	private BigDecimal employmentType = null;
	private BigDecimal occupation = null;
	private String employer = null;
	private BigDecimal empStateId = null;
	private BigDecimal empDistrictId = null;
	private BigDecimal empCityId = null;
	private String empInfoBlock = null;
	private String empInfoStreet = null;
	private String emparea = null;
	private String officeTel = null;
	private String postalCode = null;
	private String department = null;
	
	// all fileds getter and setters
	public String getAdditinal1() {
		return additinal1;
	}

	public void setAdditinal1(String additinal1) {
		this.additinal1 = additinal1;
	}

	public String getArticleDesCard() {
		return articleDesCard;
	}

	public void setArticleDesCard(String articleDesCard) {
		this.articleDesCard = articleDesCard;
	}

	public BigDecimal getArticle() {
		return article;
	}

	public void setArticle(BigDecimal article) {
		this.article = article;
	}

	public BigDecimal getLevel() {
		return level;
	}

	public void setLevel(BigDecimal level) {
		this.level = level;
	}

	public BigDecimal getIncomeRange() {
		return incomeRange;
	}

	public void setIncomeRange(BigDecimal incomeRange) {
		this.incomeRange = incomeRange;
	}

	public String getDependantIdNumber() {
		return dependantIdNumber;
	}

	public void setDependantIdNumber(String dependantIdNumber) {
		this.dependantIdNumber = dependantIdNumber;
	}

	public String getDependentName() {
		return dependentName;
	}

	public void setDependentName(String dependentName) {
		this.dependentName = dependentName;
	}

	public BigDecimal getDependentRelationship() {
		return dependentRelationship;
	}

	public void setDependentRelationship(BigDecimal dependentRelationship) {
		this.dependentRelationship = dependentRelationship;
	}

/*	public String getDependentIdExpireDate() {
		return dependentIdExpireDate;
	}

	public void setDependentIdExpireDate(String dependentIdExpireDate) {
		this.dependentIdExpireDate = dependentIdExpireDate;
	}*/

	public BigDecimal getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(BigDecimal employmentType) {
		this.employmentType = employmentType;
	}

	public BigDecimal getOccupation() {
		return occupation;
	}

	public void setOccupation(BigDecimal occupation) {
		this.occupation = occupation;
	}

	public String getEmployer() {
		return employer;
	}

	public void setEmployer(String employer) {
		this.employer = employer;
	}

	public BigDecimal getEmpStateId() {
		return empStateId;
	}

	public void setEmpStateId(BigDecimal empStateId) {
		this.empStateId = empStateId;
	}

	public BigDecimal getEmpDistrictId() {
		return empDistrictId;
	}

	public void setEmpDistrictId(BigDecimal empDistrictId) {
		this.empDistrictId = empDistrictId;
	}

	public BigDecimal getEmpCityId() {
		return empCityId;
	}

	public void setEmpCityId(BigDecimal empCityId) {
		this.empCityId = empCityId;
	}

	public String getEmpInfoBlock() {
		return empInfoBlock;
	}

	public void setEmpInfoBlock(String empInfoBlock) {
		this.empInfoBlock = empInfoBlock;
	}

	public String getEmpInfoStreet() {
		return empInfoStreet;
	}

	public void setEmpInfoStreet(String empInfoStreet) {
		this.empInfoStreet = empInfoStreet;
	}

	public String getEmparea() {
		return emparea;
	}

	public void setEmparea(String emparea) {
		this.emparea = emparea;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
	
	//panel rendering fileds
	private Boolean renManualArticle = true;
	private Boolean renCardArticle = false;
	private Boolean renderShowDependent = false;
//	private Boolean renderRegisterDependent = false;
	private Boolean booEmploymentPanel = true;
	
	//panel render getter and setters
	public Boolean getRenManualArticle() {
		return renManualArticle;
	}

	public void setRenManualArticle(Boolean renManualArticle) {
		this.renManualArticle = renManualArticle;
	}

	public Boolean getRenCardArticle() {
		return renCardArticle;
	}

	public void setRenCardArticle(Boolean renCardArticle) {
		this.renCardArticle = renCardArticle;
	}

	public Boolean getRenderShowDependent() {
		return renderShowDependent;
	}

	public void setRenderShowDependent(Boolean renderShowDependent) {
		this.renderShowDependent = renderShowDependent;
	}

	/*public Boolean getRenderRegisterDependent() {
		return renderRegisterDependent;
	}

	public void setRenderRegisterDependent(Boolean renderRegisterDependent) {
		this.renderRegisterDependent = renderRegisterDependent;
	}*/

	public Boolean getBooEmploymentPanel() {
		return booEmploymentPanel;
	}

	public void setBooEmploymentPanel(Boolean booEmploymentPanel) {
		this.booEmploymentPanel = booEmploymentPanel;
	}
	
	
	//Business purpose Fields
	private Boolean renDependent = false;
	private String dependentIdNoMsg =null;
	private String dependentIdExpireDateMsg =null;
	private BigDecimal dependentCustomerId=null;
	private String createdByDependent =null;
	private Date createdDateDependent =null;
	private BigDecimal dependentCustomerReferenceNo=null;
	private BigDecimal customerDependentId=null;
	public BigDecimal pkCustomerEmployeeId = null;
	private String additinal2=null;
	private Date currentTime=null;
	
	
	
	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	public String getAdditinal2() {
		return additinal2;
	}

	public void setAdditinal2(String additinal2) {
		this.additinal2 = additinal2;
	}

	public BigDecimal getPkCustomerEmployeeId() {
		return pkCustomerEmployeeId;
	}

	public void setPkCustomerEmployeeId(BigDecimal pkCustomerEmployeeId) {
		this.pkCustomerEmployeeId = pkCustomerEmployeeId;
	}

	public Boolean getRenDependent() {
	return renDependent;
	}
	
	
	public void setRenDependent(Boolean renDependent) {
	this.renDependent = renDependent;
	}
	
	public String getDependentIdNoMsg() {
	return dependentIdNoMsg;
	}
	
	public void setDependentIdNoMsg(String dependentIdNoMsg) {
	this.dependentIdNoMsg = dependentIdNoMsg;
	}
	
	public String getDependentIdExpireDateMsg() {
	return dependentIdExpireDateMsg;
	}
	
	public void setDependentIdExpireDateMsg(String dependentIdExpireDateMsg) {
	this.dependentIdExpireDateMsg = dependentIdExpireDateMsg;
	}
	
	public BigDecimal getDependentCustomerId() {
	return dependentCustomerId;
	}
	
	public void setDependentCustomerId(BigDecimal dependentCustomerId) {
	this.dependentCustomerId = dependentCustomerId;
	}
	
	public String getCreatedByDependent() {
	return createdByDependent;
	}
	
	public void setCreatedByDependent(String createdByDependent) {
	this.createdByDependent = createdByDependent;
	}
	
	public Date getCreatedDateDependent() {
	return createdDateDependent;
	}
	
	public void setCreatedDateDependent(Date createdDateDependent) {
	this.createdDateDependent = createdDateDependent;
	}
	
	public BigDecimal getDependentCustomerReferenceNo() {
	return dependentCustomerReferenceNo;
	}
	
	public void setDependentCustomerReferenceNo(
		BigDecimal dependentCustomerReferenceNo) {
	this.dependentCustomerReferenceNo = dependentCustomerReferenceNo;
	}
	
	public BigDecimal getCustomerDependentId() {
	return customerDependentId;
	}
	
	public void setCustomerDependentId(BigDecimal customerDependentId) {
	this.customerDependentId = customerDependentId;
	}


	
	
	//Session Declaration
	private SessionStateManage session = new SessionStateManage();
	
	//defining Logger Class
	private Logger log = Logger.getLogger(CustomerEmployeementInfoBean.class);
	

	private List<ArticleDetails> levelData;
	private List<ArticleMasterDesc> articleData;
	private List<RelationsDescription> relationDescList;
	private List<StateMasterDesc> lstEmpStateList;
	private List<DistrictMasterDesc> lstEmpDistrictList;
	private List<CityMasterDesc> lstEmpCityList;
	private List<IncomeRangeMaster> lstIncomeRange;
	private List<CustomerEmploymentInfo> customerEmploymentInfoList;
	
	//getters and setters for List 
	public List<StateMasterDesc> getLstEmpStateList() {
		return lstEmpStateList;
	}
	public List<ArticleDetails> getLevelData() {
		return levelData;
	}

	public void setLevelData(List<ArticleDetails> levelData) {
		this.levelData = levelData;
	}

	public List<RelationsDescription> getRelationDescList() {
		return relationDescList;
	}

	public void setRelationDescList(List<RelationsDescription> relationDescList) {
		this.relationDescList = relationDescList;
	}

	public List<IncomeRangeMaster> getLstIncomeRange() {
		return lstIncomeRange;
	}

	public void setLstIncomeRange(List<IncomeRangeMaster> lstIncomeRange) {
		this.lstIncomeRange = lstIncomeRange;
	}

	public List<CustomerEmploymentInfo> getCustomerEmploymentInfoList() {
		return customerEmploymentInfoList;
	}

	public void setCustomerEmploymentInfoList(
			List<CustomerEmploymentInfo> customerEmploymentInfoList) {
		this.customerEmploymentInfoList = customerEmploymentInfoList;
	}

	public List<ArticleMasterDesc> getArticleData() {
		return articleData;
	}

	public void setArticleData(List<ArticleMasterDesc> articleData) {
		this.articleData = articleData;
	}

	public void setLstEmpStateList(List<StateMasterDesc> lstEmpStateList) {
		this.lstEmpStateList = lstEmpStateList;
	}

	public List<DistrictMasterDesc> getLstEmpDistrictList() {
		return lstEmpDistrictList;
	}

	public void setLstEmpDistrictList(List<DistrictMasterDesc> lstEmpDistrictList) {
		this.lstEmpDistrictList = lstEmpDistrictList;
	}

	public List<CityMasterDesc> getLstEmpCityList() {
		return lstEmpCityList;
	}

	public void setLstEmpCityList(List<CityMasterDesc> lstEmpCityList) {
		this.lstEmpCityList = lstEmpCityList;
	}
	
	
	//Autowired Services
	
	@Autowired
	IBranchPageService<T> branchpageService;
	@Autowired
	ICustomerRegistrationBranchService<T> customerRegistrationService;
	@Autowired
	IGeneralService<T> generalService;
	
	
	
	
	//Business Methods

	public void generateLevel() {
		enableDependent();
		levelData = branchpageService.getLevelData(getArticle(),session.getLanguageId());
	}
	  public void enableDependent(){

		   List<ArticleMaster> articleList = customerRegistrationService.getArticleMasterList(getArticle());
		   if(articleList.size()>0){
		   if(articleList.get(0).getCustomerType().equalsIgnoreCase("D")){
			   setRenderShowDependent(true);
			   setDependantIdNumber(null);
			//   setDependentIdExpireDate(null);
			   setDependentRelationship(null);
			   setDependentName(null);
		   }else{
			   
			   setRenderShowDependent(false);
		   }
		   }else{
			  
			   setRenderShowDependent(false);
		   }

	   }

	public void generateIncomeRange() {

		lstIncomeRange = generalService.getIncomeRange(session.getCountryId(), getLevel());
	}
	
/*	public void clear() {
		clearFirst();
		clearRemitterInfo();
		clearContactDetail();
		clearEmploymentInfo();
		clearProofInfo();
		clearSmartCardInfo();
	}
	
	public void resetValues()  {

		setBooIdDetail(true);
		setBooRemitterInfo(false);
		setBooCustomerDetails(false);
		setBooContactDetails(false);
		setBooEmploymentDetails(false);
		setBooProof(false);
		clear();
		setContactlistcheck(false);
		setProoflistcheck(false);
		setBooManual(false);
		setBooSmartcard(false);
		setBooidproofDatatable(false);
		setRenderIdProofVissibility(false);
		contactList.clear();
		createProofList.clear();
		setApproved(false);
		setNominee(false);
		setBooContactDetailsDuplicate(false);
		setRenderverifiedTokenNumber(false);
		setRenderSignature(false);
		setBooVerifyAllRen(false);
		setRenderSavebutton(false);
		setRenderverication(false);
		setSignaturePanelRender(false);
		setRenderFinal(false);
		setRenderVerifyReport(false);
		setRenderUpdate(false);
		setVerifyToken(null);
		setDigitalSignature(null);
		setSuccessPanel(false);
		setUpdatePanel(false);
		setBooRenderFinalSave(false);
		setMinagevalidation(false);
		setNextUpdateRender(false);
		setRenderIntroducePanel(false);
		setBooManualGo(false);
		setBoofirstPanel(false);
		setBahrainCardPanel(false);


		try {
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../registration/customerregistrationbranch.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect: " + e);
		}
	}
	*/
	   public void checkDependentCustomer() throws ParseException{
		   setDependentIdNoMsg(null);
			setDependentIdExpireDateMsg(null);
			BigDecimal customerType = generalService.getComponentId(Constants.CUSTOMERTYPE_INDU,session.getLanguageId()).getFsBizComponentData().getComponentDataId();
			List<CustomerIdProof>  registerIdProofList= customerRegistrationService.getRegisterId(getDependantIdNumber(), session.getCountryId(), customerType);
			if(registerIdProofList.size()>0){
				//setRenderRegisterDependent(false);
				for(CustomerIdProof regIdList:registerIdProofList){
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");

					if(dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(dateformat.parse(dateformat.format(new Date())))<=0){
						setDependentIdNoMsg(regIdList.getIdentityInt());
						setDependentIdExpireDateMsg(dateformat.format(regIdList.getIdentityExpiryDate()));
						setDependentName(null);
					//	setDependentIdExpireDate(null);
						setDependentRelationship(null);
						setDependentCustomerReferenceNo(regIdList.getFsCustomer().getCustomerReference());

						RequestContext.getCurrentInstance().execute("dependentIdExpired.show();");
				}else{
					
					setDependentIdNoMsg(null);
					setDependentIdExpireDateMsg(null);
					setDependentName(regIdList.getFsCustomer().getFirstName());
				//	setDependentIdExpireDate(dateformat.format(regIdList.getIdentityExpiryDate()));
					setDependentCustomerId(regIdList.getFsCustomer().getCustomerId());
					setDependentCustomerReferenceNo(regIdList.getFsCustomer().getCustomerReference());
					break;
					/*List<CustomerDependant> listCustomerDep = customerRegistrationService.checkDependantList(getPkCustomerId(), getDependentCustomerReferenceNo());
					   if(listCustomerDep.size()>0){
						   setCustomerDependentId(listCustomerDep.get(0).getCustomerDependantId());
						   setRenDependent(true);
						   RequestContext.getCurrentInstance().execute("dependentchange.show();");
					   }*/
					
				}
				
				}
				
			}else{
			//	setRenderRegisterDependent(true);
			}
			
	   }
	   
	   public void getEmploymentStatus(AjaxBehaviorEvent event) {
			/* 53 means unemployed and 0 means Select */

			if (getEmploymentType().intValue() == generalService.getComponentId(Constants.EMPLOYMENTTYPE,session.getLanguageId()).getFsBizComponentData().getComponentDataId().intValue()
					|| getEmploymentType().intValue() == 0) {
				if(getPkCustomerEmployeeId()!=null && customerEmploymentInfoList.size() != 0){
					RequestContext.getCurrentInstance().execute("unemployeechange.show();");
				}else {
					setBooEmploymentPanel(false);
				}
			} else {
				setBooEmploymentPanel(true);
			}
			if(getAdditinal2()!=null){
			setEmployer(getAdditinal2());
			}

		}
	   
	   public void popEmpDistrict(AjaxBehaviorEvent e) {
			lstEmpDistrictList = generalService.getDistrictList(session.getLanguageId(),session.getCountryId(), getEmpStateId());
			setLstEmpDistrictList(lstEmpDistrictList);
			setEmpDistrictId(null);
			setEmpCityId(null);
			setLstEmpCityList(null);
		}
	   
	   public void popEmpCity(AjaxBehaviorEvent e) {
			lstEmpCityList = generalService.getCityList(session.getLanguageId(),session.getCountryId(), getEmpStateId(),getEmpDistrictId());
			setLstEmpCityList(lstEmpCityList);
			setEmpCityId(null);

		}
	   
	   
	   public void validateOfficeTelephone(FacesContext context, UIComponent component, Object value) throws ValidatorException {


			String returnString = generalService.validateMobileTelephone(session.getCountryAlphaTwoCode(), value.toString(), Constants.RESIDENCE_CONTACT);
			if (returnString.equalsIgnoreCase(Constants.Yes)) {
			} else {
				FacesMessage msg = new FacesMessage("Residence", returnString);
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				throw new ValidatorException(msg);

			}

		}
	   
	   
	   
		public void saveEmployeeDetailsPage(){
			try{
				saveCustomerSponcerInfo();
				saveEmployeeInfo();
				
				}catch(Exception e){
				e.printStackTrace();
				}
		}
		
		public void saveEmployeeInfo(){
			CustomerEmploymentInfo custEmp = new CustomerEmploymentInfo();
			custEmp.setCustEmpInfoId(getPkCustomerEmployeeId());
			Customer customer = new Customer();
		//	customer.setCustomerId(getPkCustomerId());
			//TODO
			customer.setCustomerId(new BigDecimal(146));
			custEmp.setFsCustomer(customer);

			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(session.getLanguageId());
			custEmp.setFsLanguageType(languageType);

			CompanyMaster companyMaster = new CompanyMaster();
			companyMaster.setCompanyId(session.getCompanyId());
			custEmp.setFsCompanyMaster(companyMaster);

			BizComponentData employemntType = new BizComponentData();
			employemntType.setComponentDataId(getEmploymentType());
			custEmp.setFsBizComponentDataByEmploymentTypeId(employemntType);

			BizComponentData occupation = new BizComponentData();
			occupation.setComponentDataId(getOccupation());
			custEmp.setFsBizComponentDataByOccupationId(occupation);

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(session.getCountryId());
			custEmp.setFsCountryMaster(countryMaster);

			StateMaster stateMaster = new StateMaster();
			stateMaster.setStateId(getEmpStateId());
			custEmp.setFsStateMaster(stateMaster);

			DistrictMaster districtMaster = new DistrictMaster();
			districtMaster.setDistrictId(getEmpDistrictId());
			custEmp.setFsDistrictMaster(districtMaster);

			if(getEmpCityId()!=null){
				CityMaster cityMaster = new CityMaster();
				cityMaster.setCityId(getEmpCityId());
				custEmp.setFsCityMaster(cityMaster);
			}

			custEmp.setEmployerName(getEmployer());
			custEmp.setDepartment(getDepartment());
			custEmp.setArea(getEmparea());
			custEmp.setBlock(getEmpInfoBlock());
			custEmp.setStreet(getEmpInfoStreet());
			custEmp.setOfficeTelephone(getOfficeTel());
			custEmp.setPostal(getPostalCode());


			if (getPkCustomerEmployeeId() != null) {
		//		custEmp.setCreatedBy(getCreatedByEmployee());
		//		custEmp.setCreationDate(getCreationDateEmployee());
				custEmp.setUpdatedBy(session.getUserName());
				custEmp.setLastUpdated(getCurrentTime());
			} else {
				custEmp.setCreatedBy(session.getUserName());
				custEmp.setCreationDate(getCurrentTime());
			}
			generalService.saveOrUpdate((T)custEmp);
			
		}
		
		public void saveCustomerSponcerInfo(){
			
				CustomerSponsor sponsorObj = new CustomerSponsor();
			
				Customer customer = new Customer();
				//	customer.setCustomerId(getPkCustomerId());
				//TODO
				customer.setCustomerId(new BigDecimal(146));
				sponsorObj.setFsCustomer(customer);
				sponsorObj.setSponsorName(getDependentName());
			
				Relations relation =new Relations();
				relation.setRelationsId(getDependentRelationship());
				sponsorObj.setExRelations(relation);
				sponsorObj.setCustomerRefrenceNo(new BigDecimal(123));
				sponsorObj.setIsActive(Constants.Yes);
				sponsorObj.setCreatedBy(session.getUserName());
				sponsorObj.setCreatedDate(getCurrentTime());
				generalService.saveOrUpdate((T)sponsorObj);
		}
		
		public void updateCustomerDetails(){
			
			Customer customer = new Customer();
			//	customer.setCustomerId(getPkCustomerId());
			//TODO
			customer.setCustomerId(new BigDecimal(146));
			//customer.set
		}
		
	/*	public void backContactDetails() {
		
			setBooRemitterInfo(false);
			setBooIdDetail(false);
			setBooCustomerDetails(false);
			setBooContactDetails(true);
			setBooEmploymentDetails(false);
			setBooProof(false);
			setRenderSavebutton(false);
			setRenderSignature(false);
			setBooVerifyAllRen(false);
			setRenderverication(false);
			setRenderVerifyReport(false);
			setRenderFinal(false);
			setNextSignaturePanel(false);
			setNextUpdateRender(false);
			setRenderIntroducePanel(false);
			setBooManualGo(false);
			setBoofirstPanel(false);
			setBahrainCardPanel(false);
			setContactDetails();

		}
*/
		
		public void clearEmploymentInfo() {

			setArticle(null);
			setLevel(null);
			setEmploymentType(null);
			setOccupation(null);
			setEmployer("");
			setEmpInfoBlock("");
			setEmparea("");
			setEmpStateId(null);
			setEmpCityId(null);
			setEmpDistrictId(null);
			setEmpInfoStreet("");
			setOfficeTel("");
			setPostalCode("");
			setDepartment("");
			setIncomeRange(null);
		/*	setEmpCountryId(null);
			setDailyLimit(null);
			setWeeklyLimit(null);
			setMonthlyLimit(null);
			setHalfyearly(null);
			setAnnualLimit(null);
			setQuarterlyLimit(null);*/
		}

		//pre loaded methods 
		public void popEmpState() {
			lstEmpStateList = generalService.getStateList( session.getLanguageId(), session.getCountryId());
			setLstEmpStateList(lstEmpStateList);
		}
		 public void populateRelationShip(){
			 relationDescList = customerRegistrationService.getRelationsDescriptionList(session.getLanguageId());
			   setRelationDescList(relationDescList);
		  }
		 public void populateArticleData(){
			 articleData = branchpageService.getArtilces(session.getCountryId(),session.getLanguageId());
				setArticleData(articleData);
		 }
		 public void populateCurrenctTime(){
			 Date objList = generalService.getSysDateTimestamp(session.getCountryId());
				setCurrentTime(objList);
		 }
		 
		 
		public void navigatingToCustomerEmployeeInfoPage(){
			populateArticleData();
			popEmpState();
			populateRelationShip();
			populateCurrenctTime();
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customeremploymentinfo.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	   
	   
	   
	   
	   
	   
	   
	   
}
