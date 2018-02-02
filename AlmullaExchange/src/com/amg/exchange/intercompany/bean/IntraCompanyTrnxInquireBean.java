package com.amg.exchange.intercompany.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;


/**
 * @author Chiranjeevi
 * 
 */
@Component("intraCompanyTrnxInquireBean")
@Scope("session")
public class IntraCompanyTrnxInquireBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(IntraCompanyTrnxInquireBean.class);

	// panel 1 Variables
	private String receiveCountryName;
	private BigDecimal receiveCountryId;
	private String receiveCompanyName;
	private BigDecimal receiveCompanyId;
	private BigDecimal selectCard;
	private String idNumber;
	private Date documentDate;
	private String errmsg;
	private BigDecimal customerId;

	private Date effectiveMaxDate = new Date();

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;

	private List<CountryMasterDesc> lstFetchSenderCountry = new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal, String> mapcomIdentityType = new HashMap<BigDecimal, String>();
	private List<RemittanceApplicationView> lstRemittanceTrnxDetails = new ArrayList<RemittanceApplicationView>();
	

	// page navigation
	public void pageNavigation(){
		try {
			fetchBussinessCountry();
			fetchBusinessCompany();
			fetchIdCardDefault();
			//fetchCurrentDate();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/IntraCompanyTrnxInquire.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@PostConstruct
	public void loadIdType() {
		mapcomIdentityType = icustomerRegistrationService.getAllComponentComboDataForCustomer(sessionStateManage.getLanguageId(), "I","Identity Type");
	}
	
	// setting civild id default
	public void fetchIdCardDefault(){
		BigDecimal idtypeCivilId = generalService.getComponentId(Constants.CIVILID,sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
		if(idtypeCivilId !=null){
			setSelectCard(idtypeCivilId);
		}
	}
	
	// setting default date as current date
	public void fetchCurrentDate(){
		setDocumentDate(new Date());
	}

	// fetch business country apart of local country
	public void fetchBussinessCountry(){
		lstFetchSenderCountry.clear();
		List<CountryMasterDesc> lstCountry = generalService.getCountryList(sessionStateManage.getLanguageId());
		if(lstCountry.size() != 0){
			for (CountryMasterDesc countryMasterDesc : lstCountry) {
				if(countryMasterDesc.getFsCountryMaster().getCountryAlpha2Code() != null && countryMasterDesc.getFsCountryMaster().getCountryId().compareTo(sessionStateManage.getCountryId()) == 0){
					setReceiveCountryName(countryMasterDesc.getCountryName());
					setReceiveCountryId(countryMasterDesc.getFsCountryMaster().getCountryId());
					lstFetchSenderCountry.add(countryMasterDesc);
				}
			}
		}
	}
	
	// fetch business country company
	public void fetchBusinessCompany(){
		List<CompanyMasterDesc> listCompany = generalService.getAllCompanyList(sessionStateManage.getLanguageId());
		if(listCompany != null && listCompany.size() != 0){
			for (CompanyMasterDesc companyMasterDesc : listCompany) {
				if(companyMasterDesc.getFsCompanyMaster().getCountryMaster().getCountryId().compareTo(sessionStateManage.getCountryId()) == 0){
					setReceiveCompanyName(companyMasterDesc.getCompanyName());
					setReceiveCompanyId(companyMasterDesc.getFsCompanyMaster().getCompanyId());
				}
			}
		}
	}


	// search customer details
	public void searchTrnxDetails(){

	}
	
	// clear the fields
	public void clear(){
		setSelectCard(null);
		setIdNumber(null);
		setDocumentDate(null);
	}

	// exit button
	public void exitButton(){
		try{
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}catch(Exception exception){
			exception.printStackTrace();
		}
	}


	// getters and setters
	public String getReceiveCountryName() {
		return receiveCountryName;
	}
	public void setReceiveCountryName(String receiveCountryName) {
		this.receiveCountryName = receiveCountryName;
	}

	public BigDecimal getReceiveCountryId() {
		return receiveCountryId;
	}
	public void setReceiveCountryId(BigDecimal receiveCountryId) {
		this.receiveCountryId = receiveCountryId;
	}

	public String getReceiveCompanyName() {
		return receiveCompanyName;
	}
	public void setReceiveCompanyName(String receiveCompanyName) {
		this.receiveCompanyName = receiveCompanyName;
	}

	public BigDecimal getReceiveCompanyId() {
		return receiveCompanyId;
	}
	public void setReceiveCompanyId(BigDecimal receiveCompanyId) {
		this.receiveCompanyId = receiveCompanyId;
	}

	public BigDecimal getSelectCard() {
		return selectCard;
	}
	public void setSelectCard(BigDecimal selectCard) {
		this.selectCard = selectCard;
	}

	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public Map<BigDecimal, String> getMapcomIdentityType() {
		return mapcomIdentityType;
	}
	public void setMapcomIdentityType(Map<BigDecimal, String> mapcomIdentityType) {
		this.mapcomIdentityType = mapcomIdentityType;
	}
	
	public List<RemittanceApplicationView> getLstRemittanceTrnxDetails() {
		return lstRemittanceTrnxDetails;
	}
	public void setLstRemittanceTrnxDetails(List<RemittanceApplicationView> lstRemittanceTrnxDetails) {
		this.lstRemittanceTrnxDetails = lstRemittanceTrnxDetails;
	}

	public Date getEffectiveMaxDate() {
		return effectiveMaxDate;
	}
	public void setEffectiveMaxDate(Date effectiveMaxDate) {
		this.effectiveMaxDate = effectiveMaxDate;
	}

}
