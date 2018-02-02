package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.BankContactDetails;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.Zone;
import com.amg.exchange.treasury.model.ZoneMasterDesc;
import com.amg.exchange.treasury.service.IBankMasterContactDetailsService;
import com.amg.exchange.util.SessionStateManage;

@Component("bankMasterContactDetails")
@Scope("session")
public class BankMasterContactDetailsBean<T> implements Serializable {
	
	/**
	 * @author   
	 */
	private static final long serialVersionUID = -8824991218131457383L;
	
	private BigDecimal contactIdInternal= null;
	private BigDecimal contactBankId = null;
	private String bankName=null;
	private String contactZone = null;
	private String contactPersonForContactDetails = null;
	private String contactDep = null;
	private String contactDesignation = null;
	private String contactMobile = null;
	private String localContactPersonForContactDetails = null;
	private String localContactDepartment = null;
	private String localContactDesignation = null;
	private BigDecimal zoneId= null;
	private String ZoneName =null;
	private Map<BigDecimal, String> bankInfo = new HashMap<BigDecimal, String>(); 
	private Map<BigDecimal, String> zoneInfo = new HashMap<BigDecimal, String>(); 
	private List<BankMasterContactDetails> lstBankMasterContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankMasterContactDetails> lstBankMasterDeletedContactDetails = new ArrayList<BankMasterContactDetails>();
	private List<BankContactDetails> contactDetails = new ArrayList<BankContactDetails>();
	private List<Zone> lstZoneLists = new ArrayList<Zone>() ;
	private List<ZoneMasterDesc> lstZoneDesc=new ArrayList<ZoneMasterDesc>();
	private Boolean booLocal = true;
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	public BigDecimal getContactIdInternal() {
		return contactIdInternal;
	}
	public void setContactIdInternal(BigDecimal contactIdInternal) {
		this.contactIdInternal = contactIdInternal;
	}
	
	public BigDecimal getContactBankId() {
		return contactBankId;
	}
	public void setContactBankId(BigDecimal contactBankId) {
		this.contactBankId = contactBankId;
	}
	
	public String getContactZone() {
		return contactZone;
	}
	public void setContactZone(String contactZone) {
		this.contactZone = contactZone;
	}
	

	
	public List<ZoneMasterDesc> getLstZoneDesc() {
		return lstZoneDesc;
	}
	public void setLstZoneDesc(List<ZoneMasterDesc> lstZoneDesc) {
		this.lstZoneDesc = lstZoneDesc;
	}
	public String getContactPersonForContactDetails() {
		return contactPersonForContactDetails;
	}
	public void setContactPersonForContactDetails(
			String contactPersonForContactDetails) {
		this.contactPersonForContactDetails = contactPersonForContactDetails;
	}
	public String getContactDep() {
		return contactDep;
	}
	public void setContactDep(String contactDep) {
		this.contactDep = contactDep;
	}
	
	public String getContactDesignation() {
		return contactDesignation;
	}
	public void setContactDesignation(String contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	public String getContactMobile() {
		return contactMobile;
	}
	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}
	
	
	
	public String getLocalContactPersonForContactDetails() {
		return localContactPersonForContactDetails;
	}
	public void setLocalContactPersonForContactDetails(
			String localContactPersonForContactDetails) {
		this.localContactPersonForContactDetails = localContactPersonForContactDetails;
	}
	public String getLocalContactDepartment() {
		return localContactDepartment;
	}
	public void setLocalContactDepartment(String localContactDepartment) {
		this.localContactDepartment = localContactDepartment;
	}
	
	public String getLocalContactDesignation() {
		return localContactDesignation;
	}
	public void setLocalContactDesignation(String localContactDesignation) {
		this.localContactDesignation = localContactDesignation;
	}
	
	public List<BankMasterContactDetails> getLstBankMasterContactDetails() {
		return lstBankMasterContactDetails;
	}
	
	public List<BankMasterContactDetails> getLstBankMasterDeletedContactDetails() {
		return lstBankMasterDeletedContactDetails;
	}
	
	public List<BankContactDetails> getContactDetails() {
		return contactDetails;
	}
	
	public Boolean getBooLocal() {
		return booLocal;
	}
	public void setBooLocal(Boolean booLocal) {
		this.booLocal = booLocal;
	}
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	
	public BigDecimal getZoneId() {
		return zoneId;
	}
	public void setZoneId(BigDecimal zoneId) {
		this.zoneId = zoneId;
	}
	public String getZoneName() {
		return ZoneName;
	}
	public void setZoneName(String zoneName) {
		ZoneName = zoneName;
	}
	public List<Zone> getLstZoneLists() {
		return lstZoneLists;
	}
	public void setLstZoneLists(List<Zone> lstZoneLists) {
		this.lstZoneLists = lstZoneLists;
	}
	public List<ZoneMasterDesc> getZoneLists() {
		SessionStateManage sessionStateManage = new SessionStateManage();
		lstZoneDesc = getBankMasterContactDetailsService().getZoneList(new BigDecimal(sessionStateManage
				.isExists("languageId") ? sessionStateManage
				.getSessionValue("languageId") : "" + 1));
		System.out.println("lstZone  List :"+lstZoneLists.size());
		for(ZoneMasterDesc ZoneMasterDesc : lstZoneDesc){
			zoneInfo.put(ZoneMasterDesc.getZone().getZoneId(),ZoneMasterDesc.getZoneDescription());
		//	zoneInfo.put(zone.getZoneId(), zone.getZoneName());
			
			//System.out.println("id :"+zone.getZoneId()  +":   name : "+zone.getZoneName());
		//	setZoneId(zone.getZoneId());
			setZoneId(ZoneMasterDesc.getZone().getZoneId());
			System.out.println("setZoneId    :"+getZoneId());
		}
		
		return lstZoneDesc;
	}
	


	@Autowired
	IBankMasterContactDetailsService<T> bankMasterContactDetailsService; 
	public IBankMasterContactDetailsService<T> getBankMasterContactDetailsService() {
		return bankMasterContactDetailsService;
	}
	public void setBankMasterContactDetailsService(IBankMasterContactDetailsService<T> bankMasterContactDetailsService) {
		this.bankMasterContactDetailsService = bankMasterContactDetailsService;
	}
	
	/**
	 * Responsible to populate Bank name what is already saved from bank master page 
	 * @return
	 */
	public List<BankMaster> getBankDetails() {
		List<BankMaster> lstCountry = getBankMasterContactDetailsService().getBankMasterInfo();
		for (BankMaster bankMaster : lstCountry) {
			bankInfo.put(bankMaster.getBankId(), bankMaster.getBankFullName());
		}
		return lstCountry;
	}
	
	public List<ZoneMasterDesc> getZoneDetails(){
		List<ZoneMasterDesc> lstZone = getBankMasterContactDetailsService().getZoneList(new BigDecimal("1"));
		
		return lstZone;
	}
	
	/**
	 * This method is responsible to ass data in datatable
	 */
/*	public void addBankMasterContactList() {
		System.out.println("getzone Id :"+getZoneId()  +"zoneInfo :"+zoneInfo.get(new BigDecimal("1")));
		BankMasterContactDetails contactDetails = new BankMasterContactDetails(bankInfo.get(getContactBankId()), getContactBankId(), zoneInfo.get(getZoneId()),getZoneId(), 
																														getContactPersonForContactDetails(),  getContactDep(), getContactDesignation(), 
																														getContactMobile(), getLocalContactPersonForContactDetails(), getLocalContactDepartment(), 
																														getLocalContactDesignation(), new BigDecimal(0));
		
		lstBankMasterContactDetails.add(contactDetails);
		clearForContactDetails();
		
	}*/
	
	/**
	 * This method is responsible to fetch data according to bank selection 
	 */
	public void fetchBankContactInfo() {
		lstBankMasterContactDetails.clear();
		//clear();
		if(getContactBankId()!=null && getZoneId()!=null) {
			setBankName(bankInfo.get(getContactBankId()));
			setZoneName(zoneInfo.get(getZoneId()));
			System.out.println("zoneInfo.get(getZoneId())    :"+zoneInfo.get(getZoneId()));
			//Responsible to local panel on off
			List<BankMaster> countryOfBank = getBankMasterContactDetailsService().getbankCountryInfo(getContactBankId());
			if(countryOfBank.get(0).getFsCountryMaster().getCountryId().intValue() != sessionStateManage.getCountryId().intValue()) {
				setBooLocal(false);
			} else {
				setBooLocal(true);
			}
			
			//Responsible to fetch contact details 
			/*contactDetails = getBankMasterContactDetailsService().getbankContactInfo(getContactBankId());
			if(contactDetails!=null && contactDetails.size() > 0){
				for (BankContactDetails element : contactDetails) {
					System.out.println("Name :"+ zoneInfo.get(element.getExZone().getZoneId())  +" : id :"+element.getExZone().getZoneId());
					BankMasterContactDetails  contactDetails = new BankMasterContactDetails(bankInfo.get(element.getExBankMaster().getBankId()), 
																												element.getExBankMaster().getBankId(), zoneInfo.get(element.getExZone().getZoneId()),element.getExZone().getZoneId(), 
																												element.getContactPerson(), element.getContactDept(), 
																												element.getContactDesg(), element.getMobile(), 
																												element.getContactPersonAr(), element.getContactDeptAr(),
																												element.getContactDesgAr(), element.getBankContactId());
					lstBankMasterContactDetails.add(contactDetails);
				}
			}*/
		}
	}
	
	/**
	 * This method is responsible to manage deleted objects
	 * @param contactDetails
	 */
	public void remove(BankMasterContactDetails contactDetails){
		lstBankMasterContactDetails.remove(contactDetails);
		lstBankMasterDeletedContactDetails.add(contactDetails);
	}
	
	/**
	 * get zone List
	 */
	/*public List<Zone> getZonesList(){
		
		zoneLists = getBankMasterContactDetailsService().getZoneList(new BigDecimal("1"));
		System.out.println("zoneList  :"+zoneLists.size());
		return zoneLists;
	}*/
	
	/**
	 * This method is responsible to save the data 
	 */
	public void saveForContactDetails() {
		System.out.println(".........................................");
		BankContactDetails contactDetails;
		BankMaster bankMaster;
		Zone zone;
		
		//Responsible to add active objects from contact details
		for (BankMasterContactDetails element : lstBankMasterContactDetails) {
			contactDetails = new BankContactDetails();
			zone = new Zone();
			zone.setZoneId(element.getZoneId());
		//	contactDetails.setExZone(zone);
		//	contactDetails.setRegion(element.getZone());
			contactDetails.setContactPerson(element.getContactPerson());
			contactDetails.setContactDept(element.getContactDep());
			contactDetails.setContactDesg(element.getContactDesignation());
			contactDetails.setContactPersonAr(element.getLocalContactPerson());
			contactDetails.setContactDeptAr(element.getLocalContactDepartment());
			contactDetails.setContactDesgAr(element.getLocalContactDesignation());
			contactDetails.setMobile(element.getMobile());
			contactDetails.setRecordStatus("1");
			
			bankMaster = new BankMaster();
			bankMaster.setBankId(element.getBankId());
			
			//if condition Going to update, else section going to insert
			if(element.getInternalContactId().intValue() != 0) {
				contactDetails.setBankContactId(element.getInternalContactId());
				contactDetails.setModifier("Tanumoy");
				contactDetails.setUpdateDate(new Date());
			} else {
				contactDetails.setCreateDate(new Date());
				contactDetails.setCreator("Tanumoy");
			}
			
			contactDetails.setExBankMaster(bankMaster);
			contactDetails.setBankContactId(contactDetails.getBankContactId());
			getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);
			/*After Add we need to store the primary key, unless this if user press add multiple time, it will insert multiple times in database, but if we save
			primary key, it will update*/
			element.setInternalContactId(contactDetails.getBankContactId());
		}
		
		//Responsible to add inactive objects from contact details
		for (BankMasterContactDetails element : lstBankMasterDeletedContactDetails) {
			contactDetails = new BankContactDetails();
		//	contactDetails.setRegion(element.getZone());
			zone = new Zone();
			zone.setZoneId(element.getZoneId());
			contactDetails.setExZone(zone);
			contactDetails.setContactPerson(element.getContactPerson());
			contactDetails.setContactDept(element.getContactDep());
			contactDetails.setContactDesg(element.getContactDesignation());
			contactDetails.setContactPersonAr(element.getLocalContactPerson());
			contactDetails.setContactDeptAr(element.getLocalContactDepartment());
			contactDetails.setContactDesgAr(element.getLocalContactDesignation());
			contactDetails.setMobile(element.getMobile());
			contactDetails.setRecordStatus("0");
			
			bankMaster = new BankMaster();
			bankMaster.setBankId(element.getBankId());
			
			//if condition Going to update, else section going to insert
			if(element.getInternalContactId().intValue() != 0) {
				contactDetails.setBankContactId(element.getInternalContactId());
				contactDetails.setModifier("Tanumoy");
				contactDetails.setUpdateDate(new Date());
			} else {
				contactDetails.setCreateDate(new Date());
				contactDetails.setCreator("Tanumoy");
			}
			
			contactDetails.setExBankMaster(bankMaster);
			getBankMasterContactDetailsService().saveBankMasterContactDetails(contactDetails);
		}
		RequestContext.getCurrentInstance().execute("success.show();");
	
	}
	
	/**
	 * This method is responsible to clear the page
	 */
	public void clearForContactDetails() {
		setContactBankId(null);
		setBankName("");
		setContactZone("");
		setContactPersonForContactDetails("");
		setContactDep("");
		setContactDesignation("");
		setContactMobile("");
		setLocalContactPersonForContactDetails("");
		setLocalContactDepartment("");
		setLocalContactDesignation("");
	}
	
	/**
	 * This method is responsible when cancel button is pressed
	 */
	public void cancelForContactDetails() {
		try{
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext(); 
			context.redirect("../login/login.xhtml");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is calling from Go button of Dialogue Box, Box will appear after clicking Add button(for datatable ADD)    
	 */
	public void goFromPopUp() {
		//addBankMasterContactList();
	}
	
	/**
	 * This method will call from cancel button of Dialogue box, Box will appear after clicking Cancel button (for datatable ADD)
	 */
	public void cancelFromPopUp() {
		clearForContactDetails();
	}
	
	public void contactDetailsPageNavigation(){
		clearForContactDetails();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankcontactdetails.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void editRecord(BankMasterContactDetails contactDetails){
		
		setContactBankId(contactDetails.getBankId());
		setContactZone(contactDetails.getZoneName());
		setContactPersonForContactDetails(contactDetails.getContactPerson());
		setContactDep(contactDetails.getContactDep());
		setContactDesignation(contactDetails.getContactDesignation());
		setContactMobile(contactDetails.getMobile());
		setLocalContactPersonForContactDetails(contactDetails.getLocalContactPerson());
		setLocalContactDepartment(contactDetails.getLocalContactDepartment());
		setLocalContactDesignation(contactDetails.getLocalContactDesignation());
		
		lstBankMasterContactDetails.remove(contactDetails);
		
	}
	
	public void clickOnOKSave() throws IOException{
		clearForContactDetails();
		lstBankMasterContactDetails.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("bankcontactdetails.xhtml");
	}
	
}
