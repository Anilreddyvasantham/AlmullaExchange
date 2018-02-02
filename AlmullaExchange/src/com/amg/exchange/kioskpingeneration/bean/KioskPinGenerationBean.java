package com.amg.exchange.kioskpingeneration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.kioskpingeneration.service.IKioskPinGenerationService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;


/**
 * @author nazish
 *
 */
@Component(value = "kioskPinGeneration")
@Scope("session")
public class KioskPinGenerationBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final Logger LOG = Logger.getLogger(KioskPinGenerationBean.class);
	private static final long serialVersionUID = 1L;

	private BigDecimal customerReference=null;
	private BigDecimal customerId=null;
	private String customerName;
	private String mobile;
	private Date idExpiryDate;
	private BigDecimal identityTypeId;
	private BigDecimal customerTypeId;
	private String kioskPin;
	private String kioskPinEncrypt;

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Date getIdExpiryDate() {
		return idExpiryDate;
	}

	public void setIdExpiryDate(Date idExpiryDate) {
		this.idExpiryDate = idExpiryDate;
	}

	public BigDecimal getIdentityTypeId() {
		return identityTypeId;
	}

	public void setIdentityTypeId(BigDecimal identityTypeId) {
		this.identityTypeId = identityTypeId;
	}

	public BigDecimal getCustomerTypeId() {
		return customerTypeId;
	}

	public void setCustomerTypeId(BigDecimal customerTypeId) {
		this.customerTypeId = customerTypeId;
	}

	public String getKioskPin() {
		return kioskPin;
	}

	public void setKioskPin(String kioskPin) {
		this.kioskPin = kioskPin;
	}

	public String getKioskPinEncrypt() {
		return kioskPinEncrypt;
	}

	public void setKioskPinEncrypt(String kioskPinEncrypt) {
		this.kioskPinEncrypt = kioskPinEncrypt;
	}



	SessionStateManage sessionStateManage = new SessionStateManage();


	@Autowired
	ICustomerRegistrationBranchService<T> customerRegBranchservice;
	@Autowired
	IKioskPinGenerationService<T> kioskPinGenerationService;
	@Autowired
	IGeneralService<T> generalService;


	public void navigateToPingGeneration(){
		clear();
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();
		try {
			context.redirect("../kioskpingeneration/kioskpingeneration.xhtml");
		} catch (IOException e) {
			LOG.info("Redirect problem:= "+e);
		}

	}

	public void exit() throws IOException {

		FacesContext.getCurrentInstance().getExternalContext().redirect(sessionStateManage.getMenuPage());

	}

	public void fetchCustomerDetails() throws ParseException{
		boolean verify = true;
		List<Customer> customerList = kioskPinGenerationService.getCustomerDetails(getCustomerReference());

		if(customerList.size()>0){
			setCustomerId(customerList.get(0).getCustomerId());
			BigDecimal cutomerTypeId = generalService.getComponentId(Constants.CUSTOMERTYPE_INDU, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();

			if(cutomerTypeId.intValue() == customerList.get(0).getFsBizComponentDataByCustomerTypeId().getComponentDataId().intValue()){

				List<CustomerIdProof> idproofList = kioskPinGenerationService.getIdProofDetails(getCustomerId());

				for(CustomerIdProof regIdList:idproofList){
					SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy");
					if (regIdList.getIdentityExpiryDate()!=null && dateformat.parse(dateformat.format(regIdList.getIdentityExpiryDate())).compareTo(dateformat.parse(dateformat.format(new Date()))) > 0) {
						verify = true;
						break;
					}else{
						verify = false;
					}
				}
				setCustomerName(nullCheck(customerList.get(0).getFirstName()) + " " + nullCheck(customerList.get(0).getMiddleName()) + " " + nullCheck(customerList.get(0).getLastName()));
				setMobile(customerList.get(0).getMobile());

				if(verify){

					if(customerList.get(0).getKioskPin()==null){

						if(!(customerList.get(0).getMobile()!=null)){
							clear();
							RequestContext.getCurrentInstance().execute("mobileShoulbeUpdate.show();");
						}else{
							setMobile(customerList.get(0).getMobile());
						}
					}else{
						RequestContext.getCurrentInstance().execute("alreadyKioskPin.show();");
					}
				}else{
					clear();
					RequestContext.getCurrentInstance().execute("idexpired.show();");
				}
			}else{
				clear();
				RequestContext.getCurrentInstance().execute("notindividualCustomer.show();");
			}

		}else{
			clear();
			RequestContext.getCurrentInstance().execute("norecord.show();");
		}

	}


	public void pinGeneration(){

		setKioskPin(null);
		setKioskPinEncrypt(null);

		try {
			List<String> outputvalues = kioskPinGenerationService.callProcedureToGenerateKioskPin();
			setKioskPin((outputvalues.get(0)) == null ? "" : (outputvalues.get(0)));
			setKioskPinEncrypt((outputvalues.get(1)) == null ? "" : (outputvalues.get(1)));
		} catch (AMGException e) {

			LOG.info("Pin generation Procedure Exception: "+e);
			RequestContext.getCurrentInstance().execute("procedureexception.show();");
		}
		try{
			kioskPinGenerationService.updateKioskPin(getCustomerId(), getKioskPinEncrypt());
			RequestContext.getCurrentInstance().execute("updatesuccess.show();");

		} catch (Exception e) {

			LOG.info("Pin generation not saving: "+e);
			RequestContext.getCurrentInstance().execute("updatepinexception.show();");
		}

	}

	public void clear(){
		setCustomerId(null);
		setCustomerName(null);
		setCustomerReference(null);
		setCustomerTypeId(null);
		setIdentityTypeId(null);
		setIdExpiryDate(null);
		setKioskPin(null);
		setKioskPinEncrypt(null);
		setMobile(null);
	}

	public void searchClicked() {
		try {
			HttpSession session = sessionStateManage.getSession();
			session.setAttribute("smartcardCheck", false);
			session.setAttribute("kioskPin", true);
			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			context.redirect("../search/searrchcustomer.xhtml");

		} catch (Exception e) {
			LOG.info("Problem to Redirect the page : " + e);
		}

	}


	public void populateSearchValue(String idNumber, BigDecimal customerReference) throws ParseException {
		clear();
		setCustomerReference(customerReference);
		fetchCustomerDetails();
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	
}
