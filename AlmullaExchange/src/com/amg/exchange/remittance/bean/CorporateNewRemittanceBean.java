package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.bean.CorpRegisterManageBean;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.service.ICorporateRegService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
@Component("corporateNewRemittanceBean")
@Scope("session")
public class CorporateNewRemittanceBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	Logger log = Logger.getLogger(CorporateNewRemittanceBean.class);
	
	private String idNumber ;
	private String errorMessage;

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	private List<CustomerIdProof> customerDetailsList=new ArrayList<CustomerIdProof>();
	
	public List<CustomerIdProof> getCustomerDetailsList() {
		return customerDetailsList;
	}

	public void setCustomerDetailsList(List<CustomerIdProof> customerDetailsList) {
		this.customerDetailsList = customerDetailsList;
	}
	
	//Services
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService personalRemittanceService;
	@Autowired
	PersonalRemittanceBean<T> personalRemittanceBean;
	@Autowired
	ApplicationContext appContext;
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	@Autowired
	ICorporateRegService<T> corpRegService;
	
	SessionStateManage sessionStateManage=new SessionStateManage();
	
	
	public void corporateRemittancePageNavigation() {
		try {
			setIdNumber(null);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "newCorporateRemittance.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/newCorporateRemittance.xhtml");
		} catch (NullPointerException ne) {
			setErrorMessage("Method Name: saveAll  "+ne.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void goFromOldSmartCardpanel(){
		try {
			BigDecimal customerId = null;
			String userType = "BRANCH";
			BigDecimal idNumber=generalService.getComponentId(Constants.COMPANY_REGISTRATION_DOC, sessionStateManage.getLanguageId()).getFsBizComponentData().getComponentDataId();
			personalRemittanceBean.setIdNumber(getIdNumber());
			personalRemittanceBean.setSelectCard(idNumber);
			/*List<CustomerIdProof> lstCustomerIdProofs=generalService.getCustomerIdBasedOnCivilId(getIdNumber());
			if(lstCustomerIdProofs.size()>0){
				if(lstCustomerIdProofs.get(0).getFsCustomer() != null){
					personalRemittanceBean.setCustomerNo(lstCustomerIdProofs.get(0).getFsCustomer().getCustomerId());
					customerId = lstCustomerIdProofs.get(0).getFsCustomer().getCustomerId();
				}
			}*/
			
			List<Customer> lstCustomer = corpRegService.getCompanyRegistrationStatusIsActive(sessionStateManage.getCountryId(),getIdNumber());
			if(lstCustomer.size() != 0){
				if(lstCustomer.size() == 1){
					Customer customer = lstCustomer.get(0);
					if(customer.getCustomerId() != null){
						customerId = customer.getCustomerId();
					}
				}else{
					// more records available for customer reference
					String dupCustRef = "";
					for (Customer customer2 : lstCustomer) {
						dupCustRef = dupCustRef == "" ? dupCustRef.concat(customer2.getCustomerReference().toPlainString()) : dupCustRef.concat(",").concat(customer2.getCustomerReference().toPlainString());
					}
					setErrorMessage("Duplicate customer reference available "+dupCustRef);
					RequestContext.getCurrentInstance().execute("error.show();");
					return;
				}
			}else{
				setErrorMessage("No active records available for this customer");
				RequestContext.getCurrentInstance().execute("error.show();");
				return;				
			}

			List<CustomerIdProof> lstCustomerIdProof = personalRemittanceService.getCustomerDetailsActiveRec(getIdNumber().toUpperCase(), idNumber);
			if(lstCustomerIdProof.size() > 0){
				//CustomerIdProof customerIdProof = lstCustomerIdProof.get(0);
				personalRemittanceBean.setCustomerDetailsList(lstCustomerIdProof);
				personalRemittanceBean.getCustomerDetails();
			}else{
				setIdNumber(null);
				RequestContext.getCurrentInstance().execute("crNumExpired.show();");
				return;
			}

			log.info("getCustomerNo() :"+customerId+"\t sessionStateManage.getCountryId():"+sessionStateManage.getCountryId()+"\t sessionStateManage.getUserName() :"+sessionStateManage.getUserName());
			HashMap<String,String> customerValiMessage = personalRemittanceService.getValidateCustomerProcedure(sessionStateManage.getCountryId(),customerId,sessionStateManage.getUserName(),userType);
			log.info("customerValiMessage :"+customerValiMessage);
			log.info("INDICATOR===="+customerValiMessage.get("INDICATOR"));
			//customerValiMessage.put("ERROR_MESSAGE", null); //for Testing purpose
			if(customerValiMessage.get("ERROR_MESSAGE")!=null){
				setErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
				RequestContext.getCurrentInstance().execute("expiredCustomer.show();");
				return;
			}else if(customerValiMessage!=null&&customerValiMessage.get("INDICATOR")!=null){
				setErrorMessage(customerValiMessage.get("ERROR_MESSAGE"));
				if(customerValiMessage.get("INDICATOR").equalsIgnoreCase(Constants.Yes)){
					RequestContext.getCurrentInstance().execute("customerregproceed.show();");
				}
			}else{
				personalRemittanceBean.corporateRemitNavtoPersonalRemit();
			}

		} catch (NullPointerException ne) {
			setErrorMessage("Method Name: goFromOldSmartCardpanel  "+ne.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
	public void redirectcorporateRemittancePageNavigation() {
		try {
			CorpRegisterManageBean corporatereg = (CorpRegisterManageBean)appContext.getBean("corpregisterBean");
			corporatereg.resetValues();
			corporatereg.setCrno(getIdNumber());
			corporatereg.checkingOldDbData();
		} catch (NullPointerException ne) {
			setErrorMessage("Method Name: saveAll  "+ne.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			return;
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}
	
}
