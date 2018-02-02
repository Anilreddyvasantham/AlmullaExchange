package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.bean.BranchDataTable;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IDistrictMasterService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AccountTypeFromView;
import com.amg.exchange.remittance.model.BeneficaryContact;
import com.amg.exchange.remittance.model.BenificiaryListView;
import com.amg.exchange.remittance.model.RelationsDescription;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IServiceGroupMasterService;
import com.amg.exchange.treasury.model.ServiceGroupMasterDesc;
import com.amg.exchange.treasury.service.IStateMasterService;
import com.amg.exchange.treasury.service.ServiceCodeMasterService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;
@Component("beneficiaryEditBean")
@Scope("session")
public class BeneficiaryEditBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(BeneficiaryEditBean.class);

	private String beneficiaryName;
	private String beneficiaryArabicName;
	private String beneficiaryBank;
	private BigDecimal beneficiaryAccountType;
	private BigDecimal beneficiaryStateId;
	private String beneficiaryStateName;
	private BigDecimal beneficiaryDistId;
	private String beneficiaryDistName;
	private BigDecimal beneficiaryCityId;
	private String beneficiaryCityName;
	private BigDecimal beneficiaryCountryId;
	private BigDecimal countryId;
	private BigDecimal beneficiaryMCountryId;
	private String beneficiaryCountryName;
	private String beneficiaryMCountryName;
	private String beneficiaryCountryTelePhoneNumber;
	private BigDecimal beneficiaryCountryMobilePhoneNumber;
	private Boolean booRenderCountryPanel=false;
	private String errorMessage;
	private BigDecimal beneficiaryAccountSeqId;
	private BigDecimal beneficaryMasterSeqId;
	private Boolean booRenderFirstPanel=false;
	private String telePhoneNum;
	private String telePhoneCode;
	private String mobileCode;
	private BigDecimal telePhoneSeqId;
	private BigDecimal telephoneNumberSelect;
	private BigDecimal mobileNumberSelect;
	private Boolean booRenderTeleSelect=false;
	private BigDecimal commonTeleSeqId;
	private boolean boorenderAccounttype;
	private Boolean mandatoryOptional = true;
	private BigDecimal nationality = null;
	
	private Boolean beneNameModification = false;
	private String bankAccountNumber;
	private BigDecimal customerId;
	private String oldFirstName;
	private String oldFirstNameLocal;
	private String oldSecondName;
	private String oldSecondNameLocal;
	private String oldThirdName;
	private String oldThirdNameLocal;
	private String oldFourthName;
	private String oldFourthNameLocal;
	private String oldFifthName;
	private String newFirstName;
	private String newFirstNameLocal;
	private String newSecondName;
	private String newSecondNameLocal;
	private String newThirdName;
	private String newThirdNameLocal;
	private String newFourthName;
	private String newFourthNameLocal;
	private String newFifthName;
	//Added by Rabil
	private String newFifthNameLocal;
	
	/*added koti @23/08/2016 Bene Edit Changes*/
	private BigDecimal servicebankBranchId;
	private String bankBranchFullName;
	private Boolean booDisbleChoseeBranchButton;
	//private String bankAccountNumber;
	private String beneSwiftCode;
	private BigDecimal relationId;
	private BigDecimal benifisBankCountryId;
	private String benifisBankCountryName;
	private BigDecimal benifisBankId;
	private BigDecimal bankBranchCode;
	private String bankBranchName;
	private String beneficaryBankState;
	private String beneficaryBankDistrict;
	private String beneficaryBankCity;
	private BigDecimal beneficiaryRelationShipSeqId;
	private List<RelationsDescription> relationDescList;

	private List<StateMasterDesc> lstStateMasterDescs=new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistrictMasterDescs=new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCityMasterDescs=new ArrayList<CityMasterDesc>();
	private List<CountryMasterDesc> lstCountryMasterDescs=new ArrayList<CountryMasterDesc>();
	private List<AccountTypeFromView> lstBankAccountTypeFromView = new ArrayList<AccountTypeFromView>();
	private List<BeneficaryContact> lstBeneficaryContacts=new ArrayList<BeneficaryContact>();
	private List<BeneficaryContact> lstMobileContacts=new ArrayList<BeneficaryContact>();
	private SessionStateManage sessionStateManage=new SessionStateManage();
	private PersonalRemmitanceBeneficaryDataTable personalRemmitanceBeneficaryDataTables;
	private List<CountryMasterDesc> nationalityList = new ArrayList<CountryMasterDesc>();
	private List<CountryMasterDesc> beneCountryList = new ArrayList<CountryMasterDesc>();

	@Autowired
	IPaymentService ipaymentService;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	ServiceCodeMasterService serviceMasterService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IServiceGroupMasterService serviceGroupMasterService;
	@Autowired
	IStateMasterService<T> stateMasterService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	IDistrictMasterService<T> districtMasterService;
	
	
	public Boolean getBeneNameModification() {
		return beneNameModification;
	}
	public void setBeneNameModification(Boolean beneNameModification) {
		this.beneNameModification = beneNameModification;
	}
	
	public String getBankAccountNumber() {
		return bankAccountNumber;
	}
	public void setBankAccountNumber(String bankAccountNumber) {
		this.bankAccountNumber = bankAccountNumber;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getOldFirstName() {
		return oldFirstName;
	}
	public void setOldFirstName(String oldFirstName) {
		this.oldFirstName = oldFirstName;
	}
	
	public String getOldFirstNameLocal() {
		return oldFirstNameLocal;
	}
	public void setOldFirstNameLocal(String oldFirstNameLocal) {
		this.oldFirstNameLocal = oldFirstNameLocal;
	}

	public String getOldSecondName() {
		return oldSecondName;
	}
	public void setOldSecondName(String oldSecondName) {
		this.oldSecondName = oldSecondName;
	}

	public String getOldSecondNameLocal() {
		return oldSecondNameLocal;
	}
	public void setOldSecondNameLocal(String oldSecondNameLocal) {
		this.oldSecondNameLocal = oldSecondNameLocal;
	}

	public String getOldThirdName() {
		return oldThirdName;
	}
	public void setOldThirdName(String oldThirdName) {
		this.oldThirdName = oldThirdName;
	}

	public String getOldThirdNameLocal() {
		return oldThirdNameLocal;
	}
	public void setOldThirdNameLocal(String oldThirdNameLocal) {
		this.oldThirdNameLocal = oldThirdNameLocal;
	}

	public String getOldFourthName() {
		return oldFourthName;
	}
	public void setOldFourthName(String oldFourthName) {
		this.oldFourthName = oldFourthName;
	}

	public String getOldFourthNameLocal() {
		return oldFourthNameLocal;
	}
	public void setOldFourthNameLocal(String oldFourthNameLocal) {
		this.oldFourthNameLocal = oldFourthNameLocal;
	}

	public String getOldFifthName() {
		return oldFifthName;
	}
	public void setOldFifthName(String oldFifthName) {
		this.oldFifthName = oldFifthName;
	}

	public String getNewFirstName() {
		return newFirstName;
	}
	public void setNewFirstName(String newFirstName) {
		this.newFirstName = newFirstName;
	}

	public String getNewFirstNameLocal() {
		return newFirstNameLocal;
	}
	public void setNewFirstNameLocal(String newFirstNameLocal) {
		this.newFirstNameLocal = newFirstNameLocal;
	}

	public String getNewSecondName() {
		return newSecondName;
	}
	public void setNewSecondName(String newSecondName) {
		this.newSecondName = newSecondName;
	}

	public String getNewSecondNameLocal() {
		return newSecondNameLocal;
	}
	public void setNewSecondNameLocal(String newSecondNameLocal) {
		this.newSecondNameLocal = newSecondNameLocal;
	}

	public String getNewThirdName() {
		return newThirdName;
	}
	public void setNewThirdName(String newThirdName) {
		this.newThirdName = newThirdName;
	}

	public String getNewThirdNameLocal() {
		return newThirdNameLocal;
	}
	public void setNewThirdNameLocal(String newThirdNameLocal) {
		this.newThirdNameLocal = newThirdNameLocal;
	}

	public String getNewFourthName() {
		return newFourthName;
	}
	public void setNewFourthName(String newFourthName) {
		this.newFourthName = newFourthName;
	}

	public String getNewFourthNameLocal() {
		return newFourthNameLocal;
	}
	public void setNewFourthNameLocal(String newFourthNameLocal) {
		this.newFourthNameLocal = newFourthNameLocal;
	}

	public String getNewFifthName() {
		return newFifthName;
	}
	public void setNewFifthName(String newFifthName) {
		this.newFifthName = newFifthName;
	}

	public String getBeneficiaryArabicName() {
		return beneficiaryArabicName;
	}
	public void setBeneficiaryArabicName(String beneficiaryArabicName) {
		this.beneficiaryArabicName = beneficiaryArabicName;
	}
	
	public Boolean getMandatoryOptional() {
		return mandatoryOptional;
	}
	public void setMandatoryOptional(Boolean mandatoryOptional) {
		this.mandatoryOptional = mandatoryOptional;
	}
	
	public BigDecimal getNationality() {
		return nationality;
	}
	public void setNationality(BigDecimal nationality) {
		this.nationality = nationality;
	}
	
	public List<CountryMasterDesc> getNationalityList() {
		return nationalityList;
	}
	public void setNationalityList(List<CountryMasterDesc> nationalityList) {
		this.nationalityList = nationalityList;
	}
	
	public List<CountryMasterDesc> getBeneCountryList() {
		return beneCountryList;
	}
	public void setBeneCountryList(List<CountryMasterDesc> beneCountryList) {
		this.beneCountryList = beneCountryList;
	}
	
	public BigDecimal getCommonTeleSeqId() {
		return commonTeleSeqId;
	}
	public void setCommonTeleSeqId(BigDecimal commonTeleSeqId) {
		this.commonTeleSeqId = commonTeleSeqId;
	}
	
	public BigDecimal getBeneficaryMasterSeqId() {
		return beneficaryMasterSeqId;
	}
	public void setBeneficaryMasterSeqId(BigDecimal beneficaryMasterSeqId) {
		this.beneficaryMasterSeqId = beneficaryMasterSeqId;
	}
	
	public String getBeneficiaryName() {
		return beneficiaryName;
	}
	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}
	
	public String getBeneficiaryBank() {
		return beneficiaryBank;
	}
	public void setBeneficiaryBank(String beneficiaryBank) {
		this.beneficiaryBank = beneficiaryBank;
	}
	
	public BigDecimal getBeneficiaryAccountType() {
		return beneficiaryAccountType;
	}
	public void setBeneficiaryAccountType(BigDecimal beneficiaryAccountType) {
		this.beneficiaryAccountType = beneficiaryAccountType;
	}
	
	public BigDecimal getBeneficiaryStateId() {
		return beneficiaryStateId;
	}
	public void setBeneficiaryStateId(BigDecimal beneficiaryStateId) {
		this.beneficiaryStateId = beneficiaryStateId;
	}
	
	public String getBeneficiaryStateName() {
		return beneficiaryStateName;
	}
	public void setBeneficiaryStateName(String beneficiaryStateName) {
		this.beneficiaryStateName = beneficiaryStateName;
	}
	
	public BigDecimal getBeneficiaryDistId() {
		return beneficiaryDistId;
	}
	public void setBeneficiaryDistId(BigDecimal beneficiaryDistId) {
		this.beneficiaryDistId = beneficiaryDistId;
	}
	
	public String getBeneficiaryDistName() {
		return beneficiaryDistName;
	}
	public void setBeneficiaryDistName(String beneficiaryDistName) {
		this.beneficiaryDistName = beneficiaryDistName;
	}
	
	public BigDecimal getBeneficiaryCityId() {
		return beneficiaryCityId;
	}
	public void setBeneficiaryCityId(BigDecimal beneficiaryCityId) {
		this.beneficiaryCityId = beneficiaryCityId;
	}
	
	public String getBeneficiaryCityName() {
		return beneficiaryCityName;
	}
	public void setBeneficiaryCityName(String beneficiaryCityName) {
		this.beneficiaryCityName = beneficiaryCityName;
	}
	
	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}
	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}
	
	public BigDecimal getBeneficiaryMCountryId() {
		return beneficiaryMCountryId;
	}
	public void setBeneficiaryMCountryId(BigDecimal beneficiaryMCountryId) {
		this.beneficiaryMCountryId = beneficiaryMCountryId;
	}
	
	public String getBeneficiaryCountryName() {
		return beneficiaryCountryName;
	}
	public void setBeneficiaryCountryName(String beneficiaryCountryName) {
		this.beneficiaryCountryName = beneficiaryCountryName;
	}
	
	public String getBeneficiaryMCountryName() {
		return beneficiaryMCountryName;
	}
	public void setBeneficiaryMCountryName(String beneficiaryMCountryName) {
		this.beneficiaryMCountryName = beneficiaryMCountryName;
	}
	
	public String getBeneficiaryCountryTelePhoneNumber() {
		return beneficiaryCountryTelePhoneNumber;
	}
	public void setBeneficiaryCountryTelePhoneNumber(String beneficiaryCountryTelePhoneNumber) {
		this.beneficiaryCountryTelePhoneNumber = beneficiaryCountryTelePhoneNumber;
	}
	
	public BigDecimal getBeneficiaryCountryMobilePhoneNumber() {
		return beneficiaryCountryMobilePhoneNumber;
	}
	public void setBeneficiaryCountryMobilePhoneNumber(BigDecimal beneficiaryCountryMobilePhoneNumber) {
		this.beneficiaryCountryMobilePhoneNumber = beneficiaryCountryMobilePhoneNumber;
	}
	
	public Boolean getBooRenderCountryPanel() {
		return booRenderCountryPanel;
	}
	public void setBooRenderCountryPanel(Boolean booRenderCountryPanel) {
		this.booRenderCountryPanel = booRenderCountryPanel;
	}
	
	public List<StateMasterDesc> getLstStateMasterDescs() {
		return lstStateMasterDescs;
	}
	public void setLstStateMasterDescs(List<StateMasterDesc> lstStateMasterDescs) {
		this.lstStateMasterDescs = lstStateMasterDescs;
	}
	
	public List<DistrictMasterDesc> getLstDistrictMasterDescs() {
		return lstDistrictMasterDescs;
	}
	public void setLstDistrictMasterDescs(List<DistrictMasterDesc> lstDistrictMasterDescs) {
		this.lstDistrictMasterDescs = lstDistrictMasterDescs;
	}
	
	public List<CityMasterDesc> getLstCityMasterDescs() {
		return lstCityMasterDescs;
	}
	public void setLstCityMasterDescs(List<CityMasterDesc> lstCityMasterDescs) {
		this.lstCityMasterDescs = lstCityMasterDescs;
	}
	
	public List<CountryMasterDesc> getLstCountryMasterDescs() {
		return lstCountryMasterDescs;
	}
	public void setLstCountryMasterDescs(List<CountryMasterDesc> lstCountryMasterDescs) {
		this.lstCountryMasterDescs = lstCountryMasterDescs;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<AccountTypeFromView> getLstBankAccountTypeFromView() {
		return lstBankAccountTypeFromView;
	}
	public void setLstBankAccountTypeFromView(List<AccountTypeFromView> lstBankAccountTypeFromView) {
		this.lstBankAccountTypeFromView = lstBankAccountTypeFromView;
	}

	public BigDecimal getBeneficiaryAccountSeqId() {
		return beneficiaryAccountSeqId;
	}
	public void setBeneficiaryAccountSeqId(BigDecimal beneficiaryAccountSeqId) {
		this.beneficiaryAccountSeqId = beneficiaryAccountSeqId;
	}

	/*public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}
	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderCountryPanel = booRenderFirstPanel;
	}*/
	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}
	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	
	public PersonalRemmitanceBeneficaryDataTable getPersonalRemmitanceBeneficaryDataTables() {
		return personalRemmitanceBeneficaryDataTables;
	}
	public void setPersonalRemmitanceBeneficaryDataTables(PersonalRemmitanceBeneficaryDataTable personalRemmitanceBeneficaryDataTables) {
		this.personalRemmitanceBeneficaryDataTables = personalRemmitanceBeneficaryDataTables;
	}
	
	public String getTelePhoneNum() {
		return telePhoneNum;
	}
	public void setTelePhoneNum(String telePhoneNum) {
		this.telePhoneNum = telePhoneNum;
	}
	
	public BigDecimal getTelePhoneSeqId() {
		return telePhoneSeqId;
	}
	public void setTelePhoneSeqId(BigDecimal telePhoneSeqId) {
		this.telePhoneSeqId = telePhoneSeqId;
	}
	
	public List<BeneficaryContact> getLstBeneficaryContacts() {
		return lstBeneficaryContacts;
	}
	public void setLstBeneficaryContacts(List<BeneficaryContact> lstBeneficaryContacts) {
		this.lstBeneficaryContacts = lstBeneficaryContacts;
	}
	
	public BigDecimal getTelephoneNumberSelect() {
		return telephoneNumberSelect;
	}
	public void setTelephoneNumberSelect(BigDecimal telephoneNumberSelect) {
		this.telephoneNumberSelect = telephoneNumberSelect;
	}
	
	public Boolean getBooRenderTeleSelect() {
		return booRenderTeleSelect;
	}
	public void setBooRenderTeleSelect(Boolean booRenderTeleSelect) {
		this.booRenderTeleSelect = booRenderTeleSelect;
	}
	
	public BigDecimal getMobileNumberSelect() {
		return mobileNumberSelect;
	}
	public void setMobileNumberSelect(BigDecimal mobileNumberSelect) {
		this.mobileNumberSelect = mobileNumberSelect;
	}
	
	public List<BeneficaryContact> getLstMobileContacts() {
		return lstMobileContacts;
	}
	public void setLstMobileContacts(List<BeneficaryContact> lstMobileContacts) {
		this.lstMobileContacts = lstMobileContacts;
	}
	
	public boolean isBoorenderAccounttype() {
		return boorenderAccounttype;
	}
	public void setBoorenderAccounttype(boolean boorenderAccounttype) {
		this.boorenderAccounttype = boorenderAccounttype;
	}
	
	public String getTelePhoneCode() {
		return telePhoneCode;
	}
	public void setTelePhoneCode(String telePhoneCode) {
		this.telePhoneCode = telePhoneCode;
	}
	
	public String getMobileCode() {
		return mobileCode;
	}
	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public void toFetchStateListBasedOnCountry(){
		try{
			lstStateMasterDescs.clear();
			List<StateMasterDesc> stateMasterDescs = generalService.getStateList(sessionStateManage.getLanguageId(),getBeneficiaryCountryId());
			if(stateMasterDescs.size()>0){
				lstStateMasterDescs.addAll(stateMasterDescs);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchStateListBasedOnCountry");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void toFetchDistrictListBeasedOnCountryAndState(){
		try{
			lstDistrictMasterDescs.clear();
			lstCityMasterDescs.clear();
			List<DistrictMasterDesc> districtMasterDescs=generalService.getDistrictList(sessionStateManage.getLanguageId(),getBeneficiaryCountryId(),getBeneficiaryStateId());
			if(districtMasterDescs.size()>0){
				lstDistrictMasterDescs.addAll(districtMasterDescs);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchDistrictListBeasedOnCountryAndState");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}
	
	public void toFetchCityBasedOnDist(){
		lstCityMasterDescs.clear();
		try{
			List<CityMasterDesc> cityMasterDescs=generalService.getCityList(sessionStateManage.getLanguageId(),getBeneficiaryCountryId(),getBeneficiaryStateId(),getBeneficiaryDistId());
			if(cityMasterDescs.size()>0){
				lstCityMasterDescs.addAll(cityMasterDescs);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchCityBasedOnDist");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void toFetchAllCountry(){
		lstCountryMasterDescs.clear();
		try{
			List<CountryMasterDesc> countryMasterDescs=generalService.getCountryList(sessionStateManage.getLanguageId());
			if(countryMasterDescs.size()>0){
				lstCountryMasterDescs.addAll(countryMasterDescs);
				String teleCountryId = generalService.getTelephoneCountryBasedOnNationality(getBeneficiaryCountryId());
				if(teleCountryId != null){
					setCountryId(new BigDecimal(teleCountryId));
					setBeneficiaryMCountryId(new BigDecimal(teleCountryId));
				}
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAllCountry");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void toFetchAccountType(){
		try{
			if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
				lstBankAccountTypeFromView.clear();
			}

			//List<BankAccountTypeDesc> bankAccountTypeDescs=beneficaryCreation.getBankAccountType(sessionStateManage.getLanguageId());
			if(getBenifisBankCountryId() != null){
				// bank Country Id
				//List<AccountTypeFromView> lstBankAccountTypeFromViewDB = beneficaryCreation.getAccountTypeFromView(getPersonalRemmitanceBeneficaryDataTables().getCountryId());
				List<AccountTypeFromView> lstBankAccountTypeFromViewDB = beneficaryCreation.getAccountTypeFromView(getBenifisBankCountryId());
				if(lstBankAccountTypeFromViewDB.size()>0){
					lstBankAccountTypeFromView.addAll(lstBankAccountTypeFromViewDB);
				}
			}

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchAccountType");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void clear(){
		setBeneficiaryName(null);
		setBeneficiaryArabicName(null);
		setBeneficiaryBank(null);
		setBeneficiaryAccountType(null);
		setBeneficiaryStateId(null);
		setBeneficiaryDistId(null);
		setBeneficiaryCityId(null);
		setBeneficiaryCountryId(null);
		setCountryId(null);
		setBeneficiaryMCountryId(null);
		setBeneficiaryCountryMobilePhoneNumber(null);
		setBeneficiaryCountryTelePhoneNumber(null);
		setTelephoneNumberSelect(null);
		setMobileNumberSelect(null);
		setCommonTeleSeqId(null);
		setServicebankBranchId(null);
		setBankBranchFullName(null);
		//private Boolean booDisbleChoseeBranchButton;
		//private String bankAccountNumber;
		setBeneSwiftCode(null);
		setRelationId(null);
		//setBenifisCountryId(null);
		//setBenifisCountryName(null);
		setBenifisBankId(null);
		setBankBranchCode(null);
		setBankBranchName(null);
		setBeneficaryBankState(null);
		setBeneficaryBankDistrict(null);
		setBeneficaryBankCity(null);
		setBeneficiaryRelationShipSeqId(null);
		setRelationDescList(null);
		//setBooRenderCountryPanel(false);
		setBenifisBankCountryId(null);
		setBenifisBankCountryName(null);
	}

	public void save(){
		try{
			if((getBeneficiaryCountryTelePhoneNumber() == null || getBeneficiaryCountryTelePhoneNumber().trim().equalsIgnoreCase("")) && getBeneficiaryCountryMobilePhoneNumber() == null){
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				return;
			}
			
			if(getServicebankBranchId() == null || getBankBranchCode() == null){
				RequestContext.getCurrentInstance().execute("branchMand.show();");
				return;
			}

			HashMap<String, String> inputValues = new HashMap<String, String>();

			if(getBooRenderCountryPanel() && !getBooRenderFirstPanel())
			{
				inputValues.put("BeneficiaryAccountSeqId", getBeneficiaryAccountSeqId()==null?"":getBeneficiaryAccountSeqId().toPlainString());
				inputValues.put("BeneficaryMasterSeqId", getBeneficaryMasterSeqId()==null?"":getBeneficaryMasterSeqId().toPlainString());
				inputValues.put("ApplicationCountryId", sessionStateManage.getCountryId()==null?"":sessionStateManage.getCountryId().toPlainString());
				inputValues.put("UseId", sessionStateManage.getUserName());
				if(getTelePhoneCode() != null && getTelePhoneCode().length() != 0 && getTelePhoneCode().length() <= 10){
					inputValues.put("TeleCode", getTelePhoneCode());
				}else if(getMobileCode() != null && getMobileCode().length() != 0 && getMobileCode().length() <= 10){
					inputValues.put("TeleCode", getMobileCode());
				}else{
					inputValues.put("TeleCode", null);
				}

				if(getBeneficiaryDistId()!=null)
				{
					inputValues.put("BeneficiaryDistId", getBeneficiaryDistId().toPlainString());
					String districtName = districtMasterService.getDistrictName(sessionStateManage.getLanguageId(), getBeneficiaryDistId());
					if(districtName != null){
						inputValues.put("BeneficiaryDistName", districtName);
					}
					inputValues.put("BeneficiaryDistIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryDistIdValue", "NO");
				}
				if(getBeneficiaryCityId()!=null)
				{
					inputValues.put("BeneficiaryCityId", getBeneficiaryCityId().toPlainString());
					String cityName = districtMasterService.getCityName(sessionStateManage.getLanguageId(), getBeneficiaryCityId());
					if(cityName != null){
						inputValues.put("BeneficiaryCityName", cityName);
					}
					inputValues.put("BeneficiaryCityIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryCityIdValue", "NO");
				}

				if(getBeneficiaryCountryMobilePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumber", getBeneficiaryCountryMobilePhoneNumber().toPlainString());
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}

				}else
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "NO");
				}

				if(getBeneficiaryCountryTelePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumber", getBeneficiaryCountryTelePhoneNumber().trim());
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}
				}else
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "NO");
				}
				
				// if beneficiary name is editable
				if(getBeneNameModification()){
					inputValues.put("NewFirstName", getNewFirstName());
					inputValues.put("NewSecondName", getNewSecondName());
					inputValues.put("NewThirdName", getNewThirdName());
					inputValues.put("NewFourthName", getNewFourthName());
					inputValues.put("NewFifthName", getNewFifthName());
					inputValues.put("NewFirstNameLocal", getNewFirstNameLocal());
					inputValues.put("NewSecondNameLocal", getNewSecondNameLocal());
					inputValues.put("NewThirdNameLocal", getNewThirdNameLocal());
					inputValues.put("NewFourthNameLocal", getNewFourthNameLocal());
					//Added by Rabil
					inputValues.put("NewFifthNameLocal", getNewFifthNameLocal());
					inputValues.put("BeneficaryNameModified", Constants.YES);
				}else{
					inputValues.put("BeneficaryNameModified", Constants.NO);
				}
				
				inputValues.put("NewSwiftCode", getBeneSwiftCode());
				
			/*	// beneficiary master log table
				BeneficaryMasterLog beneficaryMasterLog = saveBeneficaryMasterLog();*/

				iPersonalRemittanceService.saveBeneficiaryContactEdit(inputValues);

				String errorMessage = null;
				if(getPersonalRemmitanceBeneficaryDataTables() != null){
					errorMessage = beneficaryCreation.getBeneDetailProce(getPersonalRemmitanceBeneficaryDataTables().getBeneficaryMasterSeqId(),getPersonalRemmitanceBeneficaryDataTables().getBankId(), getPersonalRemmitanceBeneficaryDataTables().getBranchId(),getPersonalRemmitanceBeneficaryDataTables().getBeneficiaryAccountSeqId(),getPersonalRemmitanceBeneficaryDataTables().getCurrencyId(), getPersonalRemmitanceBeneficaryDataTables().getCustomerId());
				}
				if (errorMessage == null) {
					RequestContext.getCurrentInstance().execute("remittanceSave.show();");
				} else {
					log.info("EX_POPULATE_BENE_DT:::::::errorMessage::::::::::::::::::::::::"+errorMessage);
					setErrorMessage("EX_POPULATE_BENE_DT " + errorMessage); 
					RequestContext.getCurrentInstance().execute("exception.show();");
				}

			}else if(getBooRenderFirstPanel() && getBooRenderCountryPanel())
			{
				inputValues.put("BeneficiaryAccountSeqId", getBeneficiaryAccountSeqId()==null?"":getBeneficiaryAccountSeqId().toPlainString());
				inputValues.put("BeneficaryMasterSeqId", getBeneficaryMasterSeqId()==null?"":getBeneficaryMasterSeqId().toPlainString());
				inputValues.put("BeneficiaryStateId", getBeneficiaryStateId()==null?"":getBeneficiaryStateId().toPlainString());
				String stateName = districtMasterService.getStateName(sessionStateManage.getLanguageId(), getBeneficiaryStateId());
				if(stateName != null){
					inputValues.put("BeneficiaryStateName", stateName);
				}
				inputValues.put("BeneficiaryAccountType", getBeneficiaryAccountType() == null ? null : getBeneficiaryAccountType().toPlainString());
				inputValues.put("ApplicationCountryId", sessionStateManage.getCountryId()==null?"":sessionStateManage.getCountryId().toPlainString());
				inputValues.put("UseId", sessionStateManage.getUserName());
				if(getTelePhoneCode() != null && getTelePhoneCode().length() != 0 && getTelePhoneCode().length() <= 10){
					inputValues.put("TeleCode", getTelePhoneCode());
				}else if(getMobileCode() != null &&  getMobileCode().length() != 0 && getMobileCode().length() <= 10){
					inputValues.put("TeleCode", getMobileCode());
				}else{
					inputValues.put("TeleCode", null);
				}

				if(getBeneficiaryDistId()!=null)
				{
					inputValues.put("BeneficiaryDistId", getBeneficiaryDistId().toPlainString());
					String districtName = districtMasterService.getDistrictName(sessionStateManage.getLanguageId(), getBeneficiaryDistId());
					if(districtName != null){
						inputValues.put("BeneficiaryDistName", districtName);
					}
					inputValues.put("BeneficiaryDistIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryDistIdValue", "NO");
				}
				if(getBeneficiaryCityId()!=null)
				{
					inputValues.put("BeneficiaryCityId", getBeneficiaryCityId().toPlainString());
					String cityName = districtMasterService.getCityName(sessionStateManage.getLanguageId(), getBeneficiaryCityId());
					if(cityName != null){
						inputValues.put("BeneficiaryCityName", cityName);
					}
					inputValues.put("BeneficiaryCityIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryCityIdValue", "NO");
				}

				if(getBeneficiaryCountryMobilePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumber", getBeneficiaryCountryMobilePhoneNumber().toPlainString());
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}

				}else
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "NO");
				}

				if(getBeneficiaryCountryTelePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumber", getBeneficiaryCountryTelePhoneNumber().trim());
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}
				}else
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "NO");
				}
				
				// if beneficiary name is editable
				if(getBeneNameModification()){
					inputValues.put("NewFirstName", getNewFirstName());
					inputValues.put("NewSecondName", getNewSecondName());
					inputValues.put("NewThirdName", getNewThirdName());
					inputValues.put("NewFourthName", getNewFourthName());
					inputValues.put("NewFifthName", getNewFifthName());
					inputValues.put("NewFirstNameLocal", getNewFirstNameLocal());
					inputValues.put("NewSecondNameLocal", getNewSecondNameLocal());
					inputValues.put("NewThirdNameLocal", getNewThirdNameLocal());
					inputValues.put("NewFourthNameLocal", getNewFourthNameLocal());
					inputValues.put("NewFifthNameLocal", getNewFifthNameLocal());
					inputValues.put("BeneficaryNameModified", Constants.YES);
				}else{
					inputValues.put("BeneficaryNameModified", Constants.NO);
				}
				
				inputValues.put("SwiftCode", getBeneSwiftCode());
				
				// beneficiary master log table
			//	BeneficaryMasterLog beneficaryMasterLog = saveBeneficaryMasterLog();

				iPersonalRemittanceService.saveBeneficiaryEdit(inputValues);
				
				String errorMessage = null;
				if(getPersonalRemmitanceBeneficaryDataTables() != null){
					errorMessage = beneficaryCreation.getBeneDetailProce(getPersonalRemmitanceBeneficaryDataTables().getBeneficaryMasterSeqId(),getPersonalRemmitanceBeneficaryDataTables().getBankId(), getPersonalRemmitanceBeneficaryDataTables().getBranchId(),getPersonalRemmitanceBeneficaryDataTables().getBeneficiaryAccountSeqId(),getPersonalRemmitanceBeneficaryDataTables().getCurrencyId(), getPersonalRemmitanceBeneficaryDataTables().getCustomerId());
				}
				if (errorMessage == null) {
					RequestContext.getCurrentInstance().execute("remittanceSave.show();");
				} else {
					log.info("EX_POPULATE_BENE_DT:::::::errorMessage::::::::::::::::::::::::"+errorMessage);
					setErrorMessage("EX_POPULATE_BENE_DT " + errorMessage); 
					RequestContext.getCurrentInstance().execute("exception.show();");
				}

			}

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void clickOnOkSave(){
		gotoRemittance();
		setBeneficiaryName(null);
		setBeneficiaryArabicName(null);
		setBeneficiaryBank(null);
		setBeneficiaryAccountType(null);
		setBeneficiaryStateId(null);
		setBeneficiaryDistId(null);
		setBeneficiaryCityId(null);
		setBeneficiaryCountryId(null);
		setBeneficiaryMCountryId(null);
		setBeneficiaryCountryMobilePhoneNumber(null);
		setBeneficiaryCountryTelePhoneNumber(null);
		setTelePhoneSeqId(null);
	}
	
	/*public void fetchBeneNameModification(){
		setBeneNameModification(false);
		// only Branch Manager or chief cashier have permission to modify the beneficiary name
		if(sessionStateManage.getRoleId() != null && (sessionStateManage.getRoleId().equalsIgnoreCase("2") || sessionStateManage.getRoleId().equalsIgnoreCase("18"))){
			setBeneNameModification(true);
		}else{
			setBeneNameModification(false);
		}
	}*/
	
	public void populateSearchValue(){
		clearBeneDetails();
		fetchAllBeneficiaryCountry();
		setBeneNameModification(true);
		toFetchAccountType();
		toFetchStateListBasedOnCountry();
		toFetchAllCountry();
		toFetchContactMasterId();
		toFetchMobileContacts();
		checkingmandatoryOptional();
		toFetchAllRelations();
	
		// nationality from fs_country master
		if(nationalityList!=null && nationalityList.isEmpty()){
			nationalityList = generalService.getNationalityList(sessionStateManage.getLanguageId());
		}
		
		if(getPersonalRemmitanceBeneficaryDataTables() != null){
			if(getPersonalRemmitanceBeneficaryDataTables().getServiceGroupId() != null){
				BigDecimal paymentModeCashId = ipaymentService.fetchPaymodeMasterId(Constants.CASHNAME,new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"));
				if(paymentModeCashId != null && getPersonalRemmitanceBeneficaryDataTables().getServiceGroupId().compareTo(paymentModeCashId)==0){
					//cash product
					setBoorenderAccounttype(false);
					if(getPersonalRemmitanceBeneficaryDataTables().getStateId() != null){
						setBeneficiaryStateId(getPersonalRemmitanceBeneficaryDataTables().getStateId());
						toFetchDistrictListBeasedOnCountryAndState();
					}
					if(getPersonalRemmitanceBeneficaryDataTables().getDistrictId() != null){
						setBeneficiaryDistId(getPersonalRemmitanceBeneficaryDataTables().getDistrictId());
						toFetchCityBasedOnDist();
					}
					if(getPersonalRemmitanceBeneficaryDataTables().getCityId() != null){
						setBeneficiaryCityId(getPersonalRemmitanceBeneficaryDataTables().getCityId());
					}

				}else{
					//Banking product
					setBoorenderAccounttype(true);
					if(getPersonalRemmitanceBeneficaryDataTables().getBankAccountTypeId() != null){
						setBeneficiaryAccountType(getPersonalRemmitanceBeneficaryDataTables().getBankAccountTypeId());
					}
					if(getPersonalRemmitanceBeneficaryDataTables().getStateId() != null){
						setBeneficiaryStateId(getPersonalRemmitanceBeneficaryDataTables().getStateId());
						toFetchDistrictListBeasedOnCountryAndState();
					}
					if(getPersonalRemmitanceBeneficaryDataTables().getDistrictId() != null){
						setBeneficiaryDistId(getPersonalRemmitanceBeneficaryDataTables().getDistrictId());
						toFetchCityBasedOnDist();
					}
					if(getPersonalRemmitanceBeneficaryDataTables().getCityId() != null){
						setBeneficiaryCityId(getPersonalRemmitanceBeneficaryDataTables().getCityId());
					}
				}
				
				// set bene names if mandatory to modify
				if(getBeneNameModification()){
					setBankAccountNumber(getPersonalRemmitanceBeneficaryDataTables().getBankAccountNumber());
					setBeneficaryMasterSeqId(getPersonalRemmitanceBeneficaryDataTables().getBeneficaryMasterSeqId());
					setCustomerId(getPersonalRemmitanceBeneficaryDataTables().getCustomerId());
					// old data
					setOldFirstName(getPersonalRemmitanceBeneficaryDataTables().getFirstName());
					setOldFirstNameLocal(getPersonalRemmitanceBeneficaryDataTables().getFirstNameLocal());
					setOldSecondName(getPersonalRemmitanceBeneficaryDataTables().getSecondName());
					setOldSecondNameLocal(getPersonalRemmitanceBeneficaryDataTables().getSecondNameLocal());
					setOldThirdName(getPersonalRemmitanceBeneficaryDataTables().getThirdName());
					setOldThirdNameLocal(getPersonalRemmitanceBeneficaryDataTables().getThirdNameLocal());
					setOldFourthName(getPersonalRemmitanceBeneficaryDataTables().getFourthName());
					setOldFourthNameLocal(getPersonalRemmitanceBeneficaryDataTables().getFourthNameLocal());
					setOldFifthName(getPersonalRemmitanceBeneficaryDataTables().getFiftheName());
					// newly modified
					setNewFirstName(getPersonalRemmitanceBeneficaryDataTables().getFirstName());
					setNewFirstNameLocal(getPersonalRemmitanceBeneficaryDataTables().getFirstNameLocal());
					setNewSecondName(getPersonalRemmitanceBeneficaryDataTables().getSecondName());
					setNewSecondNameLocal(getPersonalRemmitanceBeneficaryDataTables().getSecondNameLocal());
					setNewThirdName(getPersonalRemmitanceBeneficaryDataTables().getThirdName());
					setNewThirdNameLocal(getPersonalRemmitanceBeneficaryDataTables().getThirdNameLocal());
					setNewFourthName(getPersonalRemmitanceBeneficaryDataTables().getFourthName());
					setNewFourthNameLocal(getPersonalRemmitanceBeneficaryDataTables().getFourthNameLocal());
					setNewFifthName(getPersonalRemmitanceBeneficaryDataTables().getFiftheName());
					//Added by Rabil
					setNewFifthNameLocal(getPersonalRemmitanceBeneficaryDataTables().getFifthNameLocal());
				}
			}
		}
	}
	
	public void clearBeneDetails(){
		setBankAccountNumber(null);
		setBeneficiaryAccountType(null);
		setCustomerId(null);
		// old data
		setOldFirstName(null);
		setOldFirstNameLocal(null);
		setOldSecondName(null);
		setOldSecondNameLocal(null);
		setOldThirdName(null);
		setOldThirdNameLocal(null);
		setOldFourthName(null);
		setOldFourthNameLocal(null);
		setOldFifthName(null);
		// newly modified
		setNewFirstName(null);
		setNewFirstNameLocal(null);
		setNewSecondName(null);
		setNewSecondNameLocal(null);
		setNewThirdName(null);
		setNewThirdNameLocal(null);
		setNewFourthName(null);
		setNewFourthNameLocal(null);
		setNewFifthName(null);
		//Added by Rabil
		setNewFifthNameLocal(null);
	}
	
	public void checkingBacktoRemittance(){
		RequestContext.getCurrentInstance().execute("backcheckforBeneCreation.show();");
	}

	public void exit(){
		try {
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			if(objectPersonalRemittance.getBooRenderCorporateBack()){
				objectPersonalRemittance.setBooRenderBenificaryFirstPanel(false);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void toFetchContactMasterId(){
		lstBeneficaryContacts.clear();
		try{
			/*List<BeneficaryContact> beneficaryContacts=iPersonalRemittanceService.getTelePhoneSeqId(getBeneficaryMasterSeqId());
			if(beneficaryContacts.size()>0){
				lstBeneficaryContacts.addAll(beneficaryContacts);
				if(lstBeneficaryContacts.size()==0){
					setBeneficiaryCountryTelePhoneNumber(null);
					setBooRenderTeleSelect(false);
					setTelePhoneCode(null);
					setMobileCode(null);
				}else if (lstBeneficaryContacts.size()==1) {
					setBeneficiaryCountryTelePhoneNumber(lstBeneficaryContacts.get(0).getTelephoneNumber());
					setBeneficiaryCountryMobilePhoneNumber(lstBeneficaryContacts.get(0).getMobileNumber());
					setTelePhoneCode(lstBeneficaryContacts.get(0).getCountryTelCode());
					setMobileCode(lstBeneficaryContacts.get(0).getCountryTelCode());
					setCommonTeleSeqId(lstBeneficaryContacts.get(0).getBeneficaryTelephoneSeqId());
					setBooRenderTeleSelect(false);
				}else{
					setBooRenderTeleSelect(true);
				}
			}else{
				throw new Exception("beneficaryMasterSeqId comming null ::beneficaryMasterSeqId");
			}*/
			
			
			List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(getBeneficaryMasterSeqId());

			System.out.println("telePhone :"+telePhone.toString());
			if (telePhone != null && telePhone.size() != 0) {
				if(telePhone.size() == 1){
					if(telePhone.get(0).getTelephoneNumber()!=null){
						setBeneficiaryCountryTelePhoneNumber(telePhone.get(0).getTelephoneNumber());
					}
					if(telePhone.get(0).getMobileNumber() !=null){
						setBeneficiaryCountryMobilePhoneNumber(telePhone.get(0).getMobileNumber()); 
					}
					
					setTelePhoneCode(telePhone.get(0).getCountryTelCode());
					setMobileCode(telePhone.get(0).getCountryTelCode());
					setCommonTeleSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
					setBooRenderTeleSelect(false);
				}else{
					BeneficaryContact beneficaryContact = telePhone.get(0);
					if(beneficaryContact.getTelephoneNumber()!=null){
						setBeneficiaryCountryTelePhoneNumber(beneficaryContact.getTelephoneNumber());
					}
					if(telePhone.get(0).getMobileNumber() !=null){
						setBeneficiaryCountryMobilePhoneNumber(beneficaryContact.getMobileNumber()); 
					}
					
					setTelePhoneCode(telePhone.get(0).getCountryTelCode());
					setMobileCode(telePhone.get(0).getCountryTelCode());
					setCommonTeleSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
					setBooRenderTeleSelect(false);
				}
			}else{
				setBeneficiaryCountryTelePhoneNumber(null);
				setBooRenderTeleSelect(false);
				setTelePhoneCode(null);
				setMobileCode(null);
			}
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchContactMasterId()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void toFetchMobileContacts(){
		lstMobileContacts.clear();
		try{
			List<BeneficaryContact> beneficaryMContacts=iPersonalRemittanceService.getMobileSeqId(getBeneficaryMasterSeqId());
			if(beneficaryMContacts.size()>0){
				lstMobileContacts.addAll(beneficaryMContacts);
				if(lstMobileContacts.size()==0){
					setBeneficiaryCountryTelePhoneNumber(null);
					setBooRenderTeleSelect(false);
				}else if (lstMobileContacts.size()==1) {
					if(lstMobileContacts.get(0).getTelephoneNumber() != null){
						setBeneficiaryCountryTelePhoneNumber(lstMobileContacts.get(0).getTelephoneNumber());
					}
					setBeneficiaryCountryMobilePhoneNumber(lstMobileContacts.get(0).getMobileNumber());
					setCommonTeleSeqId(lstMobileContacts.get(0).getBeneficaryTelephoneSeqId());
					setBooRenderTeleSelect(false);
				}else{
					setBooRenderTeleSelect(true);
				}
			}/*else{
				throw new Exception("beneficaryMasterSeqId comming null ::beneficaryMasterSeqId");
			}*/
		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::toFetchContactMasterId()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}

	public void gotoRemittance(){

		try {
			Boolean editBene = true;
			PersonalRemmitanceBeneficaryDataTable personalRBDataTable = null;
			
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			
			BigDecimal beneAccountSeqid = null;
			if(personalRemmitanceBeneficaryDataTables.getBankAccountNumber() != null){
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(personalRemmitanceBeneficaryDataTables.getBeneficaryMasterSeqId(),personalRemmitanceBeneficaryDataTables.getBankId(),personalRemmitanceBeneficaryDataTables.getBranchId(),personalRemmitanceBeneficaryDataTables.getBankAccountNumber(),personalRemmitanceBeneficaryDataTables.getCurrencyId());
			}else{
				//beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(getBeneficarymasterId(),getAgentMaster(),getAgentBranch(),getBankAccountNumber(),getBenifisCurrencyId());
				beneAccountSeqid= beneficaryCreation.getbeneAccountSeqIdForCash(personalRemmitanceBeneficaryDataTables.getBeneficaryMasterSeqId(),personalRemmitanceBeneficaryDataTables.getBankId(),personalRemmitanceBeneficaryDataTables.getBranchId(),personalRemmitanceBeneficaryDataTables.getBankAccountNumber(),personalRemmitanceBeneficaryDataTables.getCurrencyId());
			}

			//BigDecimal beneAccountSeqid= beneficaryCreation.getbeneAccountSeqId(personalRemmitanceBeneficaryDataTables.getBeneficaryMasterSeqId(),personalRemmitanceBeneficaryDataTables.getBankId(),personalRemmitanceBeneficaryDataTables.getBranchId(),personalRemmitanceBeneficaryDataTables.getBankAccountNumber(),personalRemmitanceBeneficaryDataTables.getCurrencyId());
			
			if(beneAccountSeqid != null){
				List<BenificiaryListView> isCoustomerExist = beneficaryCreation.fetchBeneficiaryNewRecordUpdated(personalRemmitanceBeneficaryDataTables.getCustomerId(),personalRemmitanceBeneficaryDataTables.getBeneficaryMasterSeqId(),beneAccountSeqid,editBene,personalRemmitanceBeneficaryDataTables.getCurrencyId(),personalRemmitanceBeneficaryDataTables.getServiceGroupId());

				if(isCoustomerExist.size() != 0){

					BenificiaryListView rel = isCoustomerExist.get(0);

					personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();

					personalRBDataTable.setAccountStatus(rel.getAccountStatus());
					personalRBDataTable.setAge(rel.getAge());
					personalRBDataTable.setApplicationCountryId(rel.getApplicationCountryId());
					personalRBDataTable.setArbenificaryName(rel.getArbenificaryName());
					personalRBDataTable.setBankAccountNumber(rel.getBankAccountNumber());
					personalRBDataTable.setBankBranchName(rel.getBankBranchName());
					personalRBDataTable.setBankCode(rel.getBankCode());
					personalRBDataTable.setBankId(rel.getBankId());
					personalRBDataTable.setBankName(rel.getBankName());
					personalRBDataTable.setBeneficaryMasterSeqId(rel.getBeneficaryMasterSeqId());
					personalRBDataTable.setBeneficiaryAccountSeqId(rel.getBeneficiaryAccountSeqId());
					personalRBDataTable.setBeneficiaryRelationShipSeqId(rel.getBeneficiaryRelationShipSeqId());
					personalRBDataTable.setBenificaryCountry(rel.getCountryId()); // bene country Id
					personalRBDataTable.setBenificaryCountryName(rel.getCountryName()); // bene country Name
					personalRBDataTable.setBenificaryName(rel.getBenificaryName());
					personalRBDataTable.setBenificaryStatusId(rel.getBenificaryStatusId());
					personalRBDataTable.setBenificaryStatusName(rel.getBenificaryStatusName());
					personalRBDataTable.setBooDisabled(rel.getBankAccountNumber()!=null ? true: false);
					personalRBDataTable.setBranchCode(rel.getBranchCode());
					personalRBDataTable.setBranchId(rel.getBranchId());
					personalRBDataTable.setCityName(rel.getCityName());
					personalRBDataTable.setCountryId(rel.getBenificaryCountry()); // bank country Id
					personalRBDataTable.setCountryName(rel.getBenificaryBankCountryName());  // bank country Name
					personalRBDataTable.setCreatedBy(rel.getCreatedBy());
					personalRBDataTable.setCreatedDate(rel.getCreatedDate());
					personalRBDataTable.setCurrencyId(rel.getCurrencyId());
					personalRBDataTable.setCurrencyName(rel.getCurrencyName());
					personalRBDataTable.setCurrencyQuoteName(rel.getCurrencyQuoteName()==null?"":rel.getCurrencyQuoteName());
					personalRBDataTable.setCustomerId(rel.getCustomerId());
					personalRBDataTable.setDateOfBirth(rel.getDateOfBirth());
					personalRBDataTable.setDistrictName(rel.getDistrictName());
					personalRBDataTable.setFiftheName(rel.getFiftheName());
					personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
					personalRBDataTable.setFirstName(rel.getFirstName());
					personalRBDataTable.setFirstNameLocal(rel.getFirstNameLocal());
					personalRBDataTable.setFourthName(rel.getFourthName());
					personalRBDataTable.setFourthNameLocal(rel.getFourthNameLocal());
					//Added by Rabil
					personalRBDataTable.setFifthNameLocal(rel.getFifthNameLocal());
					//Code end here 
					personalRBDataTable.setIsActive(rel.getIsActive());
					personalRBDataTable.setLocation(rel.getNationalityName());
					personalRBDataTable.setModifiedBy(rel.getModifiedBy());
					personalRBDataTable.setModifiedDate(rel.getModifiedDate());
					personalRBDataTable.setNationality(rel.getNationality());
					personalRBDataTable.setNationalityName(rel.getNationalityName());
					personalRBDataTable.setNoOfRemittance(rel.getNoOfRemittance());
					personalRBDataTable.setOccupation(rel.getOccupation());
					personalRBDataTable.setRelationShipId(rel.getRelationShipId());
					personalRBDataTable.setRelationShipName(rel.getRelationShipName());
					personalRBDataTable.setRemarks(rel.getRemarks());
					personalRBDataTable.setSecondNameLocal(rel.getSecondNameLocal());
					personalRBDataTable.setSecondName(rel.getSecondName());
					personalRBDataTable.setServiceGroupCode(rel.getServiceGroupCode());
					personalRBDataTable.setServiceGroupId(rel.getServiceGroupId());
					personalRBDataTable.setServiceProvider(rel.getServiceProvider());
					personalRBDataTable.setStateName(rel.getStateName());
					personalRBDataTable.setStateId(rel.getStateId());
					personalRBDataTable.setDistrictId(rel.getDistrictId());
					personalRBDataTable.setCityId(rel.getCityId());
					personalRBDataTable.setMapSequenceId(rel.getMapSequenceId());
					personalRBDataTable.setBankAccountTypeId(rel.getBankAccountTypeId());
					
					if(rel.getLastEmosRemittance()==null){
						personalRBDataTable.setTransactinStatus(Constants.NO);
					}else{
						personalRBDataTable.setTransactinStatus(Constants.YES);
					}

					List<ServiceGroupMasterDesc> lstServiceGroupMasterDesc = serviceMasterService.LocalServiceGroupDescription(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"),rel.getServiceGroupId());
					if (lstServiceGroupMasterDesc.size() > 0) {
						personalRBDataTable.setServiceGroupName(lstServiceGroupMasterDesc.get(0).getServiceGroupDesc());
					}

					/*String telePhone = beneficaryCreation.getTelePhoneNumberString(rel.getBeneficaryMasterSeqId());
					if (telePhone != null) {
						personalRBDataTable.setTelphoneNum(telePhone);
					}*/
					List<BeneficaryContact> telePhone = beneficaryCreation.getTelephoneDetails(rel.getBeneficaryMasterSeqId());
					if (telePhone != null && telePhone.size() != 0) {
						String telNumber = null;
						if(telePhone.size() == 1){
							if(telePhone.get(0).getTelephoneNumber()!=null){
								telNumber = telePhone.get(0).getTelephoneNumber();
							}else if(telePhone.get(0).getMobileNumber() !=null){
								telNumber = telePhone.get(0).getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(telePhone.get(0).getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(telePhone.get(0).getBeneficaryTelephoneSeqId());
						}else{
							BeneficaryContact beneficaryContact = telePhone.get(0);
							if(beneficaryContact.getTelephoneNumber()!=null){
								telNumber = beneficaryContact.getTelephoneNumber();
							}else if(beneficaryContact.getMobileNumber()!=null){
								telNumber = beneficaryContact.getMobileNumber().toPlainString();
							}else{
								telNumber = null;
							}
							personalRBDataTable.setTelphoneNum(telNumber);
							personalRBDataTable.setTelphoneCode(beneficaryContact.getCountryTelCode());
							personalRBDataTable.setBeneficiaryContactSeqId(beneficaryContact.getBeneficaryTelephoneSeqId());
						}
					}

					personalRBDataTable.setThirdName(rel.getThirdName());
					personalRBDataTable.setThirdNameLocal(rel.getThirdNameLocal());
					personalRBDataTable.setYearOfBirth(rel.getYearOfBirth());

					System.out.println("***************************************************************************");
					System.out.println(personalRBDataTable);
					System.out.println("***************************************************************************");
					log.info("Page redirect to bankacccountdetails page");
					
					if(personalRBDataTable != null && !personalRBDataTable.getBankCode().equalsIgnoreCase(Constants.WU_BANK_CODE)){
						// check routing setup
						HashMap<String, String> inputRoutingBankSetUpdetails = new HashMap<String, String>();

						inputRoutingBankSetUpdetails.put("P_APPLICATION_COUNTRY_ID", sessionStateManage.getCountryId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_COUNTRY_ID", personalRBDataTable.getCountryId().toPlainString()); // beneficiary bank Country Id
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_ID", personalRBDataTable.getBankId().toPlainString());
						inputRoutingBankSetUpdetails.put("P_BENE_BANK_BRANCH_ID",personalRBDataTable.getBranchId().toPlainString());

						List<ServiceGroupMasterDesc> lstServiceCode = serviceGroupMasterService.getServiceGroupDescList(personalRBDataTable.getServiceGroupId());
						if (lstServiceCode != null) {
							ServiceGroupMasterDesc ServiceCode = lstServiceCode.get(0);
							inputRoutingBankSetUpdetails.put("P_SERVICE_GROUP_CODE", ServiceCode.getServiceGroupMasterId().getServiceGroupCode());
						}

						inputRoutingBankSetUpdetails.put("P_CURRENCY_ID", personalRBDataTable.getCurrencyId().toPlainString());
						if( (sessionStateManage.getBranchId()!=null || sessionStateManage.getCustomerType().equals("E"))){ // && sessionStateManage.getRoleId().equals("1")
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","BRANCH");
							//setBooSingleService(true);
						}else if(sessionStateManage.getBranchId()!=null  &&  sessionStateManage.getUserType().equalsIgnoreCase("K")){
							inputRoutingBankSetUpdetails.put("P_USER_TYPE","KIOSK");
							//setBooMultipleService(false);
						}else{
							//setBooMultipleService(false);
						}
						
						HashMap<String, String> outputRoutingBankSetUpdetails = iPersonalRemittanceService.getRoutingBankSetupDetails(inputRoutingBankSetUpdetails);

						System.out.println("P_ERROR_MESSAGE"+outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
						System.out.println("output RoutingBankSetUpdetails : "+outputRoutingBankSetUpdetails.toString());

						if (outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE") != null) {
							setErrmsg("EX_GET_ROUTING_SET_UP" + " : " +outputRoutingBankSetUpdetails.get("P_ERROR_MESSAGE"));
							RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
							return;
						} else {
							
							String proceValiMessage =iPersonalRemittanceService.getValidateBeneficiaryProcedure(sessionStateManage.getCountryId(), personalRBDataTable.getCustomerId(), sessionStateManage.getUserName(), personalRBDataTable.getBeneficaryMasterSeqId(), personalRBDataTable.getBeneficiaryAccountSeqId());

							if(proceValiMessage!=null && proceValiMessage.length()>0){
								setErrmsg(" EX_P_VALIDATE_BENEFICIARY : "+proceValiMessage);
								RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
								return;
							}else{
								if(personalRBDataTable.getBankId() != null){
									
									//setBeneficaryBankId(personalRBDataTable.getBankId());

									HashMap<String ,String> bannedBankProcedureOut = iPersonalRemittanceService.getBannedBankCheckProcedure(sessionStateManage.getCountryId(),personalRBDataTable.getBankId(),personalRBDataTable.getBeneficaryMasterSeqId());

									if(bannedBankProcedureOut.get("P_ERROR_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ERROR_MESSAGE").equals("")){
										System.out.println("P_error_message :"+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
										setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ERROR_MESSAGE"));
										RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
										return;
									}else if(bannedBankProcedureOut.get("P_ALERT_MESSAGE")!=null && !bannedBankProcedureOut.get("P_ALERT_MESSAGE").equals("")){
										System.out.println("P_ALERT_MESSAGE :"+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
										setErrmsg("EX_P_BANNED_BANK_CHECK "+bannedBankProcedureOut.get("P_ALERT_MESSAGE"));
										RequestContext.getCurrentInstance().execute("servicenotAvailable.show();");
									}else{
										objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
										FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
									}
									
								}else{
									objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
									FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
								}
							}
						}
					}else{
						objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
					}

				}
			}

			
			/* List<PersonalRemmitanceBeneficaryDataTable> lstPersonalRemmitanceBeneficaryDataTable=objectPersonalRemittance.getCoustomerBeneficaryDTList();
			
			 int index=0;
			 for(int i =0;i<lstPersonalRemmitanceBeneficaryDataTable.size();i++)
			 {
				 PersonalRemmitanceBeneficaryDataTable personalRemmitanceBeneficaryDataTable= lstPersonalRemmitanceBeneficaryDataTable.get(i);
				 
				 if(personalRemmitanceBeneficaryDataTable.getBeneficaryMasterSeqId().compareTo(personalRBDataTable.getBeneficaryMasterSeqId())==0 && personalRemmitanceBeneficaryDataTable.getBeneficiaryAccountSeqId().compareTo(personalRBDataTable.getBeneficiaryAccountSeqId())==0)
				 {
					 index=i;
					 break;
				 }
			 }
			 lstPersonalRemmitanceBeneficaryDataTable.remove(index);
			 
			 lstPersonalRemmitanceBeneficaryDataTable.add(personalRBDataTable);
			 
			 objectPersonalRemittance.personalRemittanceBackFromBene(personalRBDataTable);
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");*/


		} catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::gotoRemittance()");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	}
	
	public void backToBenificaryListScreens() {
		try {
			log.info("Page redirect to bankacccountdetails page");
			//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fromBeneficary", "yes");
			PersonalRemittanceBean<T> objectPersonalRemittance = (PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
			objectPersonalRemittance.personalRemittanceBackFromBene(null);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (Exception e) {
			System.out.println("Exception occured" + e);
		}
	}
	
	private String errmsg;
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public void putTelephoneField(){
		if(getTelephoneNumberSelect() != null && getBeneficaryMasterSeqId() !=null){
			List<BeneficaryContact> beneficaryContacts=iPersonalRemittanceService.getTelePhoneSeqIdBasedOnNum(getTelephoneNumberSelect(),getBeneficaryMasterSeqId());
			if(beneficaryContacts.size()>0){
				setBeneficiaryCountryTelePhoneNumber(beneficaryContacts.get(0).getTelephoneNumber());
				setBeneficiaryCountryMobilePhoneNumber(lstBeneficaryContacts.get(0).getMobileNumber());
				setCommonTeleSeqId(beneficaryContacts.get(0).getBeneficaryTelephoneSeqId());
			}
		}
	}

	public void putMobileField(){
		if(getMobileNumberSelect() != null && getBeneficaryMasterSeqId() !=null){
			List<BeneficaryContact> beneficaryContacts=iPersonalRemittanceService.getTelePhoneSeqIdBasedOnNum(getMobileNumberSelect(),getBeneficaryMasterSeqId());
			if(beneficaryContacts.size()>0){
				setBeneficiaryCountryTelePhoneNumber(beneficaryContacts.get(0).getTelephoneNumber());
				setBeneficiaryCountryMobilePhoneNumber(beneficaryContacts.get(0).getMobileNumber());
				setCommonTeleSeqId(beneficaryContacts.get(0).getBeneficaryTelephoneSeqId());
			}
		}
	}

	public void fetchTelcodetoMobileCode(){
		if(getTelePhoneCode() != null){
			setMobileCode(getTelePhoneCode());
		}
	}

	public void fetchMobilecodetoTelCode(){
		if(getMobileCode() != null){
			setTelePhoneCode(getMobileCode());
		}
	}

	// checking mandatory Optional - state and district
	public void checkingmandatoryOptional(){
		setMandatoryOptional(true);
		if(getBeneficiaryCountryId() != null){
			String countryCode = generalService.getCountryCode(getBeneficiaryCountryId());
			if(countryCode != null){
				if(countryCode.equalsIgnoreCase(Constants.IND_CODE)){
					setMandatoryOptional(false);
				}else{
					setMandatoryOptional(true);
				}
			}
		}
	}
	
	/*public BeneficaryMasterLog saveBeneficaryMasterLog(){

		try{
			BeneficaryMasterLog beneMasterLog = new BeneficaryMasterLog();

			//beneMasterLog.setBeneficaryMasterLogId(null);
			beneMasterLog.setApplicationCountryId(sessionStateManage.getCountryId());
			beneMasterLog.setBankAccountNumber(getBankAccountNumber());
			beneMasterLog.setBeneficaryMasterSeqId(getBeneficaryMasterSeqId());
			if(sessionStateManage.getBranchId() != null){
				beneMasterLog.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
			}
			beneMasterLog.setCreatedBy(sessionStateManage.getUserName());
			beneMasterLog.setCreatedDate(new Date());
			beneMasterLog.setCustomerId(getCustomerId());

			beneMasterLog.setNewFirstName(getNewFirstName());
			beneMasterLog.setNewFirstNameLocal(getNewFirstNameLocal());
			beneMasterLog.setNewSecondName(getNewSecondName());
			beneMasterLog.setNewSecondNameLocal(getNewSecondNameLocal());
			beneMasterLog.setNewThirdName(getNewThirdName());
			beneMasterLog.setNewThirdNameLocal(getNewThirdNameLocal());
			beneMasterLog.setNewFourthName(getNewFourthName());
			beneMasterLog.setNewFourthNameLocal(getNewFourthNameLocal());
			beneMasterLog.setNewFifthName(getNewFifthName());

			beneMasterLog.setOldFirstName(getOldFirstName());
			beneMasterLog.setOldFirstNameLocal(getOldFirstNameLocal());
			beneMasterLog.setOldSecondName(getOldSecondName());
			beneMasterLog.setOldSecondNameLocal(getOldSecondNameLocal());
			beneMasterLog.setOldThirdName(getOldThirdName());
			beneMasterLog.setOldThirdNameLocal(getOldThirdNameLocal());
			beneMasterLog.setOldFourthName(getOldFourthName());
			beneMasterLog.setOldFourthNameLocal(getOldFourthNameLocal());
			beneMasterLog.setOldFifthName(getOldFifthName());

			return beneMasterLog;
		}catch(Exception e){
			return null;
		}
	}*/

	public SessionStateManage getSessionStateManage() {
		return sessionStateManage;
	}
	public void setSessionStateManage(SessionStateManage sessionStateManage) {
		this.sessionStateManage = sessionStateManage;
	}
	public BigDecimal getServicebankBranchId() {
		return servicebankBranchId;
	}
	public void setServicebankBranchId(BigDecimal servicebankBranchId) {
		this.servicebankBranchId = servicebankBranchId;
	}
	public String getBankBranchFullName() {
		return bankBranchFullName;
	}
	public void setBankBranchFullName(String bankBranchFullName) {
		this.bankBranchFullName = bankBranchFullName;
	}
	public Boolean getBooDisbleChoseeBranchButton() {
		return booDisbleChoseeBranchButton;
	}
	public void setBooDisbleChoseeBranchButton(Boolean booDisbleChoseeBranchButton) {
		this.booDisbleChoseeBranchButton = booDisbleChoseeBranchButton;
	}
	public String getBeneSwiftCode() {
		return beneSwiftCode;
	}
	public void setBeneSwiftCode(String beneSwiftCode) {
		this.beneSwiftCode = beneSwiftCode;
	}
	public BigDecimal getRelationId() {
		return relationId;
	}
	public void setRelationId(BigDecimal relationId) {
		this.relationId = relationId;
	}
	/*public BigDecimal getBenifisCountryId() {
		return benifisCountryId;
	}
	public void setBenifisCountryId(BigDecimal benifisCountryId) {
		this.benifisCountryId = benifisCountryId;
	}
	public String getBenifisCountryName() {
		return benifisCountryName;
	}
	public void setBenifisCountryName(String benifisCountryName) {
		this.benifisCountryName = benifisCountryName;
	}*/
	
	
	public BigDecimal getBenifisBankId() {
		return benifisBankId;
	}
	public BigDecimal getBenifisBankCountryId() {
		return benifisBankCountryId;
	}
	public void setBenifisBankCountryId(BigDecimal benifisBankCountryId) {
		this.benifisBankCountryId = benifisBankCountryId;
	}
	public String getBenifisBankCountryName() {
		return benifisBankCountryName;
	}
	public void setBenifisBankCountryName(String benifisBankCountryName) {
		this.benifisBankCountryName = benifisBankCountryName;
	}
	public void setBenifisBankId(BigDecimal benifisBankId) {
		this.benifisBankId = benifisBankId;
	}
	public List<RelationsDescription> getRelationDescList() {
		return relationDescList;
	}
	public void setRelationDescList(List<RelationsDescription> relationDescList) {
		this.relationDescList = relationDescList;
	}
	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getBeneficaryBankState() {
		return beneficaryBankState;
	}
	public void setBeneficaryBankState(String beneficaryBankState) {
		this.beneficaryBankState = beneficaryBankState;
	}
	public String getBeneficaryBankDistrict() {
		return beneficaryBankDistrict;
	}
	public void setBeneficaryBankDistrict(String beneficaryBankDistrict) {
		this.beneficaryBankDistrict = beneficaryBankDistrict;
	}
	public String getBeneficaryBankCity() {
		return beneficaryBankCity;
	}
	public void setBeneficaryBankCity(String beneficaryBankCity) {
		this.beneficaryBankCity = beneficaryBankCity;
	}
	
	public BigDecimal getBeneficiaryRelationShipSeqId() {
		return beneficiaryRelationShipSeqId;
	}
	public void setBeneficiaryRelationShipSeqId(
			BigDecimal beneficiaryRelationShipSeqId) {
		this.beneficiaryRelationShipSeqId = beneficiaryRelationShipSeqId;
	}
	//fetch Relation
	public void toFetchAllRelations(){
		List<RelationsDescription> lstRelationsDescriptions=iPersonalRemittanceService.getRelationsDescriptionList(sessionStateManage.getLanguageId());
		if(lstRelationsDescriptions.size()>0){
			setRelationDescList(lstRelationsDescriptions);
		}
	}
	// search bank branch moving from bank account to bank branch search
	public void searchClickedEditbene() {
		try {
			SearchSwiftCodeBeniEdit searchSwiftCodeBeniEdit=(SearchSwiftCodeBeniEdit)appContext.getBean("searchSwiftCodeBeniEdit");
			//searchSwiftCodeBeniEdit.setBeneficiaryBankCountryName(getBenifisCountryName());
			searchSwiftCodeBeniEdit.setBeneficiaryBankCountryName(getBenifisBankCountryName());
			searchSwiftCodeBeniEdit.setBeneficiaryBankName(getBeneficiaryBank());
			//searchSwiftCodeBeniEdit.setBeneCountryId(getBeneficiaryCountryId());
			searchSwiftCodeBeniEdit.setBeneCountryId(getBenifisBankCountryId());
			searchSwiftCodeBeniEdit.setBeneBankId(getBenifisBankId());
			searchSwiftCodeBeniEdit.pageNavigation();
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	public void selectBranchfromViewWindowforEdit(BranchDataTable branchDataTable) {
		setServicebankBranchId(branchDataTable.getBankBranchId());
		setBankBranchCode(branchDataTable.getBankBranchCode());
		setBankBranchFullName(branchDataTable.getBankFullName());
		//populateSwiftCode();
		setBankBranchName(branchDataTable.getBankBranchCode() + " - " + branchDataTable.getBankFullName());
		setBankBranchFullName(branchDataTable.getBankBranchCode() + " - " + branchDataTable.getBankFullName());
		if (branchDataTable.getStateName() != null) {
			setBeneficaryBankState(branchDataTable.getStateName());
		}else{
			setBeneficaryBankState(null);
		}
		if (branchDataTable.getDistrictName() != null) {
			setBeneficaryBankDistrict(branchDataTable.getDistrictName());
		}else{
			setBeneficaryBankDistrict(null);
		}
		if (branchDataTable.getCityName() != null) {
			setBeneficaryBankCity(branchDataTable.getCityName());
		}else{
			setBeneficaryBankCity(null);
		}
		if(branchDataTable.getSwiftCode() != null){
			setBeneSwiftCode(branchDataTable.getSwiftCode());
		}else{
			setBeneSwiftCode(null);
		}

		if(getBenifisBankCountryId() != null){
			try {
				
				if(lstBankAccountTypeFromView != null || !lstBankAccountTypeFromView.isEmpty()){
					lstBankAccountTypeFromView.clear();
				}
				// bank Country Id
				//lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBeneficiaryCountryId());
				lstBankAccountTypeFromView = beneficaryCreation.getAccountTypeFromView(getBenifisBankCountryId());
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BeneficiaryEdit.xhtml");
			} catch (Exception exception) {
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
		}
		
	}
	
	//save Branch Manager
	public void branchManagerSave(){
		try{
			
			// bank account type check
			boolean chkAccTypeValue = chkBeneficiaryAccountType();
			if(!chkAccTypeValue){
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBenifisBankCountryId());
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getBeneficiaryAccountType());
				log.info("exception.getMessage():::::::::::chkBeneficiaryAccountType::::::::::::::::::::"+getCustomerId());
				setErrorMessage("Account Type Mismatch");
				RequestContext.getCurrentInstance().execute("exception.show();");
				return; 
			}
			
			if((getBeneficiaryCountryTelePhoneNumber() == null || getBeneficiaryCountryTelePhoneNumber().trim().equalsIgnoreCase("")) && getBeneficiaryCountryMobilePhoneNumber() == null){
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				return;
			}
			
			if(getServicebankBranchId() == null || getBankBranchCode() == null){
				RequestContext.getCurrentInstance().execute("branchMand.show();");
				return;
			}

			HashMap<String, String> inputValues = new HashMap<String, String>();

			if(getBooRenderCountryPanel() && !getBooRenderFirstPanel())
			{
				inputValues.put("BeneficiaryAccountSeqId", getBeneficiaryAccountSeqId()==null?"":getBeneficiaryAccountSeqId().toPlainString());
				inputValues.put("BeneficaryMasterSeqId", getBeneficaryMasterSeqId()==null?"":getBeneficaryMasterSeqId().toPlainString());
				inputValues.put("ApplicationCountryId", sessionStateManage.getCountryId()==null?"":sessionStateManage.getCountryId().toPlainString());
				inputValues.put("UseId", sessionStateManage.getUserName());
				if(getTelePhoneCode() != null && getTelePhoneCode().length() != 0  && getTelePhoneCode().length() <= 10){
					inputValues.put("TeleCode", getTelePhoneCode());
				}else if(getMobileCode() != null && getMobileCode().length() != 0  && getMobileCode().length() <= 10){
					inputValues.put("TeleCode", getMobileCode());
				}else{
					inputValues.put("TeleCode", null);
				}

				if(getBeneficiaryDistId()!=null)
				{
					inputValues.put("BeneficiaryDistId", getBeneficiaryDistId().toPlainString());
					String districtName = districtMasterService.getDistrictName(sessionStateManage.getLanguageId(), getBeneficiaryDistId());
					if(districtName != null){
						inputValues.put("BeneficiaryDistName", districtName);
					}
					inputValues.put("BeneficiaryDistIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryDistIdValue", "NO");
				}
				if(getBeneficiaryCityId()!=null)
				{
					inputValues.put("BeneficiaryCityId", getBeneficiaryCityId().toPlainString());
					String cityName = districtMasterService.getCityName(sessionStateManage.getLanguageId(), getBeneficiaryCityId());
					if(cityName != null){
						inputValues.put("BeneficiaryCityName", cityName);
					}
					inputValues.put("BeneficiaryCityIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryCityIdValue", "NO");
				}

				if(getBeneficiaryCountryMobilePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumber", getBeneficiaryCountryMobilePhoneNumber().toPlainString());
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}

				}else
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "NO");
				}

				if(getBeneficiaryCountryTelePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumber", getBeneficiaryCountryTelePhoneNumber().trim());
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}
				}else
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "NO");
				}
				
				// if beneficiary name is editable
				if(getBeneNameModification()){
					inputValues.put("NewFirstName", getNewFirstName()==null?null:getNewFirstName().toUpperCase());
					inputValues.put("NewSecondName", getNewSecondName()==null?null:getNewSecondName().toUpperCase());
					inputValues.put("NewThirdName", getNewThirdName()==null?null:getNewThirdName().toUpperCase());
					inputValues.put("NewFourthName", getNewFourthName()==null?null:getNewFourthName().toUpperCase());
					inputValues.put("NewFifthName", getNewFifthName()==null?null:getNewFifthName().toUpperCase());
					inputValues.put("NewFirstNameLocal", getNewFirstNameLocal());
					inputValues.put("NewSecondNameLocal", getNewSecondNameLocal());
					inputValues.put("NewThirdNameLocal", getNewThirdNameLocal());
					inputValues.put("NewFourthNameLocal", getNewFourthNameLocal());
					//Added by Rabil
					inputValues.put("NewFifthNameLocal", getNewFifthNameLocal());
					//Code end here
					inputValues.put("BeneficaryNameModified", Constants.YES);
				}else{
					inputValues.put("BeneficaryNameModified", Constants.NO);
				}
				
				if(getNationality() != null){
					inputValues.put("Nationality", getNationality().toPlainString());
				}
				
				inputValues.put("NewSwiftCode", getBeneSwiftCode());
				
				/*// beneficiary master log table
				BeneficaryMasterLog beneficaryMasterLog = saveBeneficaryMasterLog();*/
				
				String errMsg = checkBeneficiaryDetailsWithProc();
				
				if(errMsg != null){
					setErrorMessage(errMsg); 
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}else{
					
					iPersonalRemittanceService.saveBeneficiaryContactEdit(inputValues);

					String errorMessage = null;
					if(getPersonalRemmitanceBeneficaryDataTables() != null){
						errorMessage = beneficaryCreation.getBeneDetailProce(getPersonalRemmitanceBeneficaryDataTables().getBeneficaryMasterSeqId(),getPersonalRemmitanceBeneficaryDataTables().getBankId(), getPersonalRemmitanceBeneficaryDataTables().getBranchId(),getPersonalRemmitanceBeneficaryDataTables().getBeneficiaryAccountSeqId(),getPersonalRemmitanceBeneficaryDataTables().getCurrencyId(), getPersonalRemmitanceBeneficaryDataTables().getCustomerId());
					}
					if (errorMessage == null) {
						RequestContext.getCurrentInstance().execute("remittanceSave.show();");
					} else {
						log.info("EX_POPULATE_BENE_DT:::::::errorMessage::::::::::::::::::::::::"+errorMessage);
						setErrorMessage("EX_POPULATE_BENE_DT " + errorMessage); 
						RequestContext.getCurrentInstance().execute("exception.show();");
					}
				}

			}else if(getBooRenderFirstPanel() && getBooRenderCountryPanel())
			{
				inputValues.put("BeneficiaryAccountSeqId", getBeneficiaryAccountSeqId()==null?"":getBeneficiaryAccountSeqId().toPlainString());
				inputValues.put("BeneficaryMasterSeqId", getBeneficaryMasterSeqId()==null?"":getBeneficaryMasterSeqId().toPlainString());
				inputValues.put("P_BENE_RELE_SEQ", getBeneficiaryRelationShipSeqId()==null ? "0" : getBeneficiaryRelationShipSeqId().toPlainString());
				inputValues.put("BeneficiaryStateId", getBeneficiaryStateId()==null?"":getBeneficiaryStateId().toPlainString());
				inputValues.put("BeneficiaryAccountType", getBeneficiaryAccountType() == null ? null : getBeneficiaryAccountType().toPlainString());
				inputValues.put("BeneficiaryBranch", getServicebankBranchId() == null ? null : getServicebankBranchId().toPlainString());
				inputValues.put("BeneficiaryBranchCode", getBankBranchCode() == null ? null : getBankBranchCode().toPlainString());
				inputValues.put("BeneSwiftCode", getBeneSwiftCode() == null ? null : getBeneSwiftCode());
				inputValues.put("ApplicationCountryId", sessionStateManage.getCountryId()==null?"":sessionStateManage.getCountryId().toPlainString());
				inputValues.put("UseId", sessionStateManage.getUserName());
				if(getTelePhoneCode() != null && getTelePhoneCode().length() != 0  && getTelePhoneCode().length() <= 10){
					inputValues.put("TeleCode", getTelePhoneCode());
				}else if(getMobileCode() != null &&  getMobileCode().length() != 0 && getMobileCode().length() <= 10){
					inputValues.put("TeleCode", getMobileCode());
				}else{
					inputValues.put("TeleCode", null);
				}
				
				if(getBeneficiaryCountryId() != null){
					inputValues.put("BeneficiaryCountryId", getBeneficiaryCountryId()==null?"":getBeneficiaryCountryId().toPlainString());
					/*String countryName = generalService.getCountryName(sessionStateManage.getLanguageId(), getBeneficiaryCountryId());
					if(countryName != null){
						inputValues.put("BeneficiaryCountryName", countryName);
					}*/
				}
				
				if(getBeneficiaryStateId() != null){
					inputValues.put("BeneficiaryStateId", getBeneficiaryStateId()==null?"":getBeneficiaryStateId().toPlainString());
					String stateName = districtMasterService.getStateName(sessionStateManage.getLanguageId(), getBeneficiaryStateId());
					if(stateName != null){
						inputValues.put("BeneficiaryStateName", stateName);
					}
				}

				if(getBeneficiaryDistId()!=null)
				{
					inputValues.put("BeneficiaryDistId", getBeneficiaryDistId().toPlainString());
					String districtName = districtMasterService.getDistrictName(sessionStateManage.getLanguageId(), getBeneficiaryDistId());
					if(districtName != null){
						inputValues.put("BeneficiaryDistName", districtName);
					}
					inputValues.put("BeneficiaryDistIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryDistIdValue", "NO");
				}
				
				if(getBeneficiaryCityId()!=null)
				{
					inputValues.put("BeneficiaryCityId", getBeneficiaryCityId().toPlainString());
					String cityName = districtMasterService.getCityName(sessionStateManage.getLanguageId(), getBeneficiaryCityId());
					if(cityName != null){
						inputValues.put("BeneficiaryCityName", cityName);
					}
					inputValues.put("BeneficiaryCityIdValue", "YES");
				}else
				{
					inputValues.put("BeneficiaryCityIdValue", "NO");
				}

				if(getBeneficiaryCountryMobilePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumber", getBeneficiaryCountryMobilePhoneNumber().toPlainString());
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}

				}else
				{
					inputValues.put("BeneficiaryCountryMobilePhoneNumberValue", "NO");
				}

				if(getBeneficiaryCountryTelePhoneNumber()!=null)
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumber", getBeneficiaryCountryTelePhoneNumber().trim());
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "YES");
					if( getCommonTeleSeqId()!=null)
					{
						inputValues.put("TelePhoneSeqId", getCommonTeleSeqId().toPlainString());
						inputValues.put("TelePhoneSeqIdValue", "YES");
					}else
					{
						inputValues.put("TelePhoneSeqIdValue", "NO");
					}
				}else
				{
					inputValues.put("BeneficiaryCountryTelePhoneNumberValue", "NO");
				}
				//Relation Ship Added @24/08/2016
				if(getRelationId() != null){
					inputValues.put("BeneficiaryRelationId", getRelationId().toPlainString());
					inputValues.put("BeneficiaryCountryRelationValue", "YES");
					if(getBeneficiaryRelationShipSeqId() != null){
						inputValues.put("BeneRelationSeqId", getBeneficiaryRelationShipSeqId().toPlainString());
						inputValues.put("BeneRelationSeqValue", "YES");
					}else{
						inputValues.put("BeneRelationSeqValue", "NO");
					}
				}else{
					inputValues.put("BeneficiaryCountryRelationValue", "NO");
				}
				
				// if beneficiary name is editable
				if(getBeneNameModification()){
					inputValues.put("NewFirstName", getNewFirstName()==null?null:getNewFirstName().toUpperCase());
					inputValues.put("NewSecondName", getNewSecondName()==null?null:getNewSecondName().toUpperCase());
					inputValues.put("NewThirdName", getNewThirdName()==null?null:getNewThirdName().toUpperCase());
					inputValues.put("NewFourthName", getNewFourthName()==null?null:getNewFourthName().toUpperCase());
					inputValues.put("NewFifthName", getNewFifthName()==null?null:getNewFifthName().toUpperCase());
					inputValues.put("NewFirstNameLocal", getNewFirstNameLocal());
					inputValues.put("NewSecondNameLocal", getNewSecondNameLocal());
					inputValues.put("NewThirdNameLocal", getNewThirdNameLocal());
					inputValues.put("NewFourthNameLocal", getNewFourthNameLocal());
					//Added by Rabil
					inputValues.put("NewFifthNameLocal", getNewFifthNameLocal());
					//
					inputValues.put("BeneficiaryRelationId", getRelationId().toPlainString());
					inputValues.put("BeneficaryNameModified", Constants.YES);
				}else{
					inputValues.put("BeneficaryNameModified", Constants.NO);
				}
				
				if(getNationality() != null){
					inputValues.put("Nationality", getNationality().toPlainString());
				}
				
				inputValues.put("NewSwiftCode", getBeneSwiftCode());
				
				/*// beneficiary master log table
				BeneficaryMasterLog beneficaryMasterLog = saveBeneficaryMasterLog();*/
				
				String errMsg = checkBeneficiaryDetailsWithProc();
				
				if(errMsg != null){
					setErrorMessage(errMsg); 
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}else{
					
					iPersonalRemittanceService.saveBeneficiaryEditManager(inputValues);
					
					String errorMessage = null;
					if(getPersonalRemmitanceBeneficaryDataTables() != null){
						errorMessage = beneficaryCreation.getBeneDetailProce(getPersonalRemmitanceBeneficaryDataTables().getBeneficaryMasterSeqId(),getPersonalRemmitanceBeneficaryDataTables().getBankId(), getPersonalRemmitanceBeneficaryDataTables().getBranchId(),getPersonalRemmitanceBeneficaryDataTables().getBeneficiaryAccountSeqId(),getPersonalRemmitanceBeneficaryDataTables().getCurrencyId(), getPersonalRemmitanceBeneficaryDataTables().getCustomerId());
					}
					if (errorMessage == null) {
						RequestContext.getCurrentInstance().execute("remittanceSave.show();");
					} else {
						log.info("EX_POPULATE_BENE_DT:::::::errorMessage::::::::::::::::::::::::"+errorMessage);
						setErrorMessage("EX_POPULATE_BENE_DT " + errorMessage); 
						RequestContext.getCurrentInstance().execute("exception.show();");
					}
				}

			}

		}catch(NullPointerException ne){
			log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("MethodName::save");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;       
		}
	
	}
	
	// call check beneficiary for validate the beneficiary details EX_CHK_BENEFICIARY
	public String checkBeneficiaryDetailsWithProc(){

		String errMsg = null;
		HashMap<String, Object> checkBeneDetails = new HashMap<String, Object>();

		try{
			
			if(getPersonalRemmitanceBeneficaryDataTables() != null){
				
				PersonalRemmitanceBeneficaryDataTable datatabledetails = getPersonalRemmitanceBeneficaryDataTables();
				
				checkBeneDetails.put("BeneBankCountryId", datatabledetails.getCountryId()); // bene bank country id
				//checkBeneDetails.put("BeneBankAccountType", getBeneficiaryAccountType());
				checkBeneDetails.put("BeneServiceGroupId", datatabledetails.getServiceGroupId());
				checkBeneDetails.put("BeneBankId", datatabledetails.getBankId());
				checkBeneDetails.put("BeneBankBranchId", datatabledetails.getBranchId());
				checkBeneDetails.put("BeneCurrencyId", datatabledetails.getCurrencyId());
				checkBeneDetails.put("BeneAccountNumber", datatabledetails.getBankAccountNumber());
				checkBeneDetails.put("BeneBankServiceProvider", datatabledetails.getServiceProvider());
				checkBeneDetails.put("BeneFirstName", datatabledetails.getFirstName());
				checkBeneDetails.put("BeneSecondName", datatabledetails.getSecondName());
				checkBeneDetails.put("BeneThirdName", datatabledetails.getThirdName());
				checkBeneDetails.put("BeneFourthName", datatabledetails.getFourthName());
				checkBeneDetails.put("BeneFifthName", datatabledetails.getFiftheName());
				checkBeneDetails.put("BeneArabicFirstName", datatabledetails.getFirstNameLocal());
				checkBeneDetails.put("BeneArabicSecondName", datatabledetails.getSecondNameLocal());
				checkBeneDetails.put("BeneArabicThirdName", datatabledetails.getThirdNameLocal());
				checkBeneDetails.put("BeneArabicFourthName", datatabledetails.getFourthNameLocal());
				//Added by Rabil
				checkBeneDetails.put("BeneArabicFifthName", datatabledetails.getFifthNameLocal());
				//code end here
				checkBeneDetails.put("BeneTelephone", getBeneficiaryCountryTelePhoneNumber().trim());
				checkBeneDetails.put("BeneMobileNumber", getBeneficiaryCountryMobilePhoneNumber());
				//checkBeneDetails.put("BeneCountryId", datatabledetails.getBenificaryCountry());
				checkBeneDetails.put("BeneCountryId", getBeneficiaryCountryId());
				checkBeneDetails.put("BeneStateId", getBeneficiaryStateId());
				checkBeneDetails.put("BeneDistrictId", getBeneficiaryDistId());
				checkBeneDetails.put("BeneCityId", getBeneficiaryCityId());
				
			}
			
			log.info("checkBeneficiaryDetailsWithProc : " + checkBeneDetails.toString());

			errMsg = beneficaryCreation.checkBeneDetailsValidation(checkBeneDetails);

		}catch(Exception e){
			log.info("exception.getMessage():::::::::::checkBeneficiaryDetailsWithProc::::::::::::::::::::"+e.getMessage());
			errMsg = e.getMessage();
		}

		return errMsg;
	}
	
	public String getNewFifthNameLocal() {
		return newFifthNameLocal;
	}
	public void setNewFifthNameLocal(String newFifthNameLocal) {
		this.newFifthNameLocal = newFifthNameLocal;
	}
	


	public void fetchAllBeneficiaryCountry(){
		beneCountryList.clear();
		beneCountryList = generalService.getCountryList(sessionStateManage.getLanguageId());
	}
	
	// check beneficiary account type
	public boolean chkBeneficiaryAccountType(){
		
		boolean chkAccType = false;
		if(getBankAccountNumber() != null && getBenifisBankCountryId() != null && getBeneficiaryAccountType() != null){
			int valueAvail = 0;
			List<AccountTypeFromView> lstAccType = beneficaryCreation.getAccountTypeFromView(getBenifisBankCountryId());
			if(lstAccType != null && lstAccType.size() != 0){
				for (AccountTypeFromView accountTypeFromView : lstAccType) {
					if(accountTypeFromView.getAdditionalAmiecId() != null && accountTypeFromView.getAdditionalAmiecId().compareTo(getBeneficiaryAccountType())==0){
						valueAvail = 1;
						break;
					}
				}
				
				if(valueAvail == 1){
					chkAccType = true;
				}else{
					chkAccType = false;
				}
			}else{
				chkAccType = false;
			}
		}else{
			chkAccType = true;
		}
		
		return chkAccType;
	}
	
}
