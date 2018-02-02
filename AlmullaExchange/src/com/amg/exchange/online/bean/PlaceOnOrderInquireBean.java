package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.service.ICurrencyEnquiryService;
import com.amg.exchange.online.model.ViewPlaceOnOrderFullInquiry;
import com.amg.exchange.online.model.ViewPlaceOnOrderInquiry;
import com.amg.exchange.online.service.IPlaceAnOrderCreationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("placeOnOrderInquireBean")
@Scope("session")
public class PlaceOnOrderInquireBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(PlaceOnOrderInquireBean.class);
	SessionStateManage session = new SessionStateManage();


	private String errorMsg;
	private String placeOrderDetailsSN;
	private String placeOrderDetailsCO;
	private String placeOrderDetailsCN;
	private String placeOrderDetailsBN;

	private BigDecimal countryBranchId;
	private String username;
	private Date placeOrderDate1;
	private Date placeOrderDate2;
	private BigDecimal customerId;
	private String errorMessage;
	private Date currentDate = new Date();

	@Autowired
	IPlaceAnOrderCreationService iPlaceOnOrderCreationService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	ICustomerRegistrationBranchService<T> iCustomerRegistrationBranchService;
	@Autowired
	ICurrencyEnquiryService currencyEnquiryService;

	private List<PlaceOnOrderDatatable> lstRatePlaceOrderByBranch = new ArrayList<PlaceOnOrderDatatable>();
	private List<PlaceOnOrderDatatable> tempRatePlaceOrderByBranch = new ArrayList<PlaceOnOrderDatatable>();
	private List<CountryBranch> lstCountryBranchForLocation = new ArrayList<CountryBranch>();
	private List<Employee> cashierList = new ArrayList<Employee>();

	// methods calling
	//page navigation
	public void pageNavigationForPlaceOrderInquiry(){
		// clear all fields 
		clearAll();

		List<PlaceOnOrderDatatable> lstRatePlace = new ArrayList<PlaceOnOrderDatatable>();
		if(session.getBranchId()!=null){
			try {
				List<ViewPlaceOnOrderInquiry> lstRatePlaceOrder = iPlaceOnOrderCreationService.fetchplaceOnOrderInquiry(new BigDecimal(session.getBranchId()),null);
				if(lstRatePlaceOrder != null && lstRatePlaceOrder.size() != 0){

					for (ViewPlaceOnOrderInquiry ratePlaceOrder : lstRatePlaceOrder) {

						PlaceOnOrderDatatable lstPlaceOrder = new PlaceOnOrderDatatable();

						lstPlaceOrder.setBeneficiaryAccountNumber(ratePlaceOrder.getBeneficiaryAccountNumber());
						lstPlaceOrder.setBeneficiaryBankBranchName(ratePlaceOrder.getBeneficiaryBankBranchName());
						lstPlaceOrder.setBeneficiaryBankName(ratePlaceOrder.getBeneficiaryBankName());
						lstPlaceOrder.setBeneficiaryFullName(ratePlaceOrder.getBeneficiaryFullName());
						lstPlaceOrder.setCountryBranchId(ratePlaceOrder.getCountryBranchId());
						lstPlaceOrder.setCreatedBy(ratePlaceOrder.getCreatedBy());
						lstPlaceOrder.setCurrencyQuote(ratePlaceOrder.getCurrencyQuote());
						lstPlaceOrder.setCustomerIdType(ratePlaceOrder.getCustomerIdType());
						lstPlaceOrder.setCustomerNumber(ratePlaceOrder.getCustomerNumber());
						lstPlaceOrder.setIdNo(ratePlaceOrder.getIdNo());
						lstPlaceOrder.setCustomerFullName(ratePlaceOrder.getCustomerFullName());
						lstPlaceOrder.setTransactionAmount(ratePlaceOrder.getTransactionAmount());
						lstPlaceOrder.setRateOffered(ratePlaceOrder.getRateOffered());
						lstPlaceOrder.setNegotiate(ratePlaceOrder.getNegotiate());
						lstPlaceOrder.setIsActive(ratePlaceOrder.getIsActive());

						if(ratePlaceOrder.getNegotiate() != null && ratePlaceOrder.getNegotiate().equalsIgnoreCase(Constants.Yes)){
							if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.Yes)){
								lstPlaceOrder.setStatus(Constants.APPROVED);
							}else{
								lstPlaceOrder.setStatus(Constants.NEGOTIATED);
							}
						}else{
							if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.Yes)){
								lstPlaceOrder.setStatus(Constants.APPROVED);
							}else{
								lstPlaceOrder.setStatus(Constants.UNAPPROVED);
							}
						}


						lstRatePlace.add(lstPlaceOrder);
					}

					if(lstRatePlace != null && lstRatePlace.size() != 0){
						lstRatePlaceOrderByBranch.addAll(lstRatePlace);
						tempRatePlaceOrderByBranch.addAll(lstRatePlace);
					}
				}
			} catch (AMGException e) {
				setErrorMsg(e.getMessage());
			}
		}
	}

	public void checkByCustomerBeneDt(String check){
		lstRatePlaceOrderByBranch.clear();
		if(check != null){
			List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
			for (PlaceOnOrderDatatable matchPlaceOrder : tempRatePlaceOrderByBranch) {

				// staff name - S or Customer No - C or Customer Name - N or Beneficiary Name - B check
				if(check.equalsIgnoreCase("S") && getPlaceOrderDetailsSN() != null && !getPlaceOrderDetailsSN().equalsIgnoreCase("")){
					if(matchPlaceOrder.getCreatedBy() != null && matchPlaceOrder.getCreatedBy().contains(getPlaceOrderDetailsSN().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getIdNo())){
							duplicateCheck.add(matchPlaceOrder.getIdNo());
							lstRatePlaceOrderByBranch.add(matchPlaceOrder);
						}
					}
				}else if(check.equalsIgnoreCase("C") && getPlaceOrderDetailsCO() != null && !getPlaceOrderDetailsCO().equalsIgnoreCase("")){
					if(matchPlaceOrder.getCustomerNumber() != null && matchPlaceOrder.getCustomerNumber().contains(getPlaceOrderDetailsCO().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getIdNo())){
							duplicateCheck.add(matchPlaceOrder.getIdNo());
							lstRatePlaceOrderByBranch.add(matchPlaceOrder);
						}
					}
				}else if(check.equalsIgnoreCase("N") && getPlaceOrderDetailsCN() != null && !getPlaceOrderDetailsCN().equalsIgnoreCase("")){
					if(matchPlaceOrder.getCustomerFullName() != null && matchPlaceOrder.getCustomerFullName().contains(getPlaceOrderDetailsCN().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getIdNo())){
							duplicateCheck.add(matchPlaceOrder.getIdNo());
							lstRatePlaceOrderByBranch.add(matchPlaceOrder);
						}
					}
				}else if(check.equalsIgnoreCase("B") && getPlaceOrderDetailsBN() != null && !getPlaceOrderDetailsBN().equalsIgnoreCase("")){
					if(matchPlaceOrder.getBeneficiaryFullName() != null && matchPlaceOrder.getBeneficiaryFullName().contains(getPlaceOrderDetailsBN().toUpperCase())){
						if(!duplicateCheck.contains(matchPlaceOrder.getIdNo())){
							duplicateCheck.add(matchPlaceOrder.getIdNo());
							lstRatePlaceOrderByBranch.add(matchPlaceOrder);
						}
					}
				}else{
					// not a check
				}
			}
			
			if(lstRatePlaceOrderByBranch != null && lstRatePlaceOrderByBranch.size() != 0){
				// no need already added
			}else{
				lstRatePlaceOrderByBranch.addAll(tempRatePlaceOrderByBranch);
			}
		}else{
			lstRatePlaceOrderByBranch.addAll(tempRatePlaceOrderByBranch);
		}
	}

	// clear data
	public void clearAll(){
		lstRatePlaceOrderByBranch.clear();
		tempRatePlaceOrderByBranch.clear();
	}

	public void onDateSelectFrom(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setPlaceOrderDate1(format.parse(format.format(event.getObject())));
		}catch(ParseException exception){
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMsg(exception.getMessage());
			return;
		}
	}
	
	public void onDateSelectTo(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setPlaceOrderDate2(format.parse(format.format(event.getObject())));
		}catch(ParseException exception){
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMsg(exception.getMessage());
			return;
		}
	}

	public void getAllCasheirs() {
		try {
			cashierList.clear();
			List<Employee> cashierList1 = currencyEnquiryService.getAllCashierList(getCountryBranchId());
			for (Employee employee : cashierList1) {
				cashierList.add(employee);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMsg(e.getMessage());
			return;
		}
	}

	public void search() {
		// clear all fields 
		clearAll();

		List<PlaceOnOrderDatatable> lstRatePlace = new ArrayList<PlaceOnOrderDatatable>();
		if(session.getBranchId()!=null){
			try {
				List<ViewPlaceOnOrderFullInquiry> lstRatePlaceOrder = iPlaceOnOrderCreationService.fetchplaceOnOrderInquiryDetails(getCountryBranchId(),getUsername(),getPlaceOrderDate1(),getPlaceOrderDate2());
				if(lstRatePlaceOrder != null && lstRatePlaceOrder.size() != 0){

					for (ViewPlaceOnOrderFullInquiry ratePlaceOrder : lstRatePlaceOrder) {

						PlaceOnOrderDatatable lstPlaceOrder = new PlaceOnOrderDatatable();

						lstPlaceOrder.setBeneficiaryAccountNumber(ratePlaceOrder.getBeneficiaryAccountNumber());
						lstPlaceOrder.setBeneficiaryBankBranchName(ratePlaceOrder.getBeneficiaryBankBranchName());
						lstPlaceOrder.setBeneficiaryBankName(ratePlaceOrder.getBeneficiaryBankName());
						lstPlaceOrder.setBeneficiaryFullName(ratePlaceOrder.getBeneficiaryFullName());
						lstPlaceOrder.setCountryBranchId(ratePlaceOrder.getCountryBranchId());
						lstPlaceOrder.setCreatedBy(ratePlaceOrder.getCreatedBy());
						lstPlaceOrder.setCurrencyQuote(ratePlaceOrder.getCurrencyQuote());
						lstPlaceOrder.setCustomerIdType(ratePlaceOrder.getCustomerIdType());
						lstPlaceOrder.setCustomerNumber(ratePlaceOrder.getCustomerNumber());
						lstPlaceOrder.setIdNo(ratePlaceOrder.getIdNo());
						lstPlaceOrder.setCustomerFullName(ratePlaceOrder.getCustomerFullName());
						lstPlaceOrder.setTransactionAmount(ratePlaceOrder.getTransactionAmount());
						lstPlaceOrder.setRateOffered(ratePlaceOrder.getRateOffered());
						lstPlaceOrder.setNegotiate(ratePlaceOrder.getNegotiate());
						lstPlaceOrder.setIsActive(ratePlaceOrder.getIsActive());
						lstPlaceOrder.setCreatedBy(ratePlaceOrder.getCreatedBy());
						lstPlaceOrder.setValueDate(ratePlaceOrder.getValueDate());
						lstPlaceOrder.setApprovedBy(ratePlaceOrder.getApprovedBy());
						lstPlaceOrder.setDocumentNo(ratePlaceOrder.getDocumentNo());
						lstPlaceOrder.setDocumentYear(ratePlaceOrder.getDocumentYear());
						lstPlaceOrder.setTransactionAmountPaid(ratePlaceOrder.getTransactionAmountPaid());
						lstPlaceOrder.setTrnxdocumentNo(ratePlaceOrder.getTrnxdocumentNo());
						lstPlaceOrder.setTrnxdocumentYear(ratePlaceOrder.getTrnxdocumentYear());

						if(ratePlaceOrder.getNegotiate() != null && ratePlaceOrder.getNegotiate().equalsIgnoreCase(Constants.Yes)){
							if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.Yes)){
								lstPlaceOrder.setStatus(Constants.APPROVED);
							}else if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.U)){
								lstPlaceOrder.setStatus(Constants.UNAPPROVED);
							}else if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.D)){
								lstPlaceOrder.setStatus(Constants.DELETE);
							}else{
								lstPlaceOrder.setStatus(Constants.NEGOTIATED);
							}
						}else{
							if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.Yes)){
								lstPlaceOrder.setStatus(Constants.APPROVED);
							}else if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.U)){
								lstPlaceOrder.setStatus(Constants.UNAPPROVED);
							}else if(ratePlaceOrder.getIsActive() != null && ratePlaceOrder.getIsActive().equalsIgnoreCase(Constants.D)){
								lstPlaceOrder.setStatus(Constants.DELETE);
							}else{
								// no status
							}
						}

						lstRatePlace.add(lstPlaceOrder);
					}

					if(lstRatePlace != null && lstRatePlace.size() != 0){
						lstRatePlaceOrderByBranch.addAll(lstRatePlace);
						tempRatePlaceOrderByBranch.addAll(lstRatePlace);
					}
				}
			} catch (AMGException e) {
				setErrorMsg(e.getMessage());
			}
		}
	}
	
	public void populateBranch() {

		if (session.getRoleId().equalsIgnoreCase("1")) {
			List<CountryBranch> lstCountryBranch = generalService.getBranchDetails(session.getCountryId());

			if (lstCountryBranch.size() != 0) {

				setLstCountryBranchForLocation(lstCountryBranch);
			}
			setCountryBranchId(new BigDecimal(session.getBranchId()));

		} else {
			List<CountryBranch> lstCountryBranch = currencyEnquiryService.getBranchDetails(session.getCountryId(), new BigDecimal(session.getBranchId()));
			if (lstCountryBranch.size() != 0) {

				setLstCountryBranchForLocation(lstCountryBranch);
			}
			setCountryBranchId(new BigDecimal(session.getBranchId()));
		}

	}

	public void exitBranch(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}
	
	public void clearPO(){
		setCountryBranchId(null);
		setUsername(null);
		clearAll();
		cashierList.clear();
	}
	
	public void pageNavigationForPlaceOrderFullInquiry(){
		// clear all fields 
		clearAll();
		setPlaceOrderDate1(null);
		setPlaceOrderDate2(null);
		setUsername(null);
		setCountryBranchId(null);
		cashierList.clear();
		lstCountryBranchForLocation.clear();
		populateBranch();
		getAllCasheirs();
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setPlaceOrderDate1(format.parse(format.format(new Date())));
			setPlaceOrderDate2(format.parse(format.format(new Date())));
		}catch(ParseException exception){
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMsg(exception.getMessage());
			return;
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../onlinespecialrate/placeOrderRateInquiry.xhtml");
		}catch(Exception e){
			setErrorMsg( e.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}  
	}

	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public List<PlaceOnOrderDatatable> getLstRatePlaceOrderByBranch() {
		return lstRatePlaceOrderByBranch;
	}
	public void setLstRatePlaceOrderByBranch(List<PlaceOnOrderDatatable> lstRatePlaceOrderByBranch) {
		this.lstRatePlaceOrderByBranch = lstRatePlaceOrderByBranch;
	}

	public List<PlaceOnOrderDatatable> getTempRatePlaceOrderByBranch() {
		return tempRatePlaceOrderByBranch;
	}
	public void setTempRatePlaceOrderByBranch(List<PlaceOnOrderDatatable> tempRatePlaceOrderByBranch) {
		this.tempRatePlaceOrderByBranch = tempRatePlaceOrderByBranch;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}
	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public Date getPlaceOrderDate1() {
		return placeOrderDate1;
	}
	public void setPlaceOrderDate1(Date placeOrderDate1) {
		this.placeOrderDate1 = placeOrderDate1;
	}

	public Date getPlaceOrderDate2() {
		return placeOrderDate2;
	}
	public void setPlaceOrderDate2(Date placeOrderDate2) {
		this.placeOrderDate2 = placeOrderDate2;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}
	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Date getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<CountryBranch> getLstCountryBranchForLocation() {
		return lstCountryBranchForLocation;
	}
	public void setLstCountryBranchForLocation(List<CountryBranch> lstCountryBranchForLocation) {
		this.lstCountryBranchForLocation = lstCountryBranchForLocation;
	}

	public List<Employee> getCashierList() {
		return cashierList;
	}
	public void setCashierList(List<Employee> cashierList) {
		this.cashierList = cashierList;
	}

	public String getPlaceOrderDetailsSN() {
		return placeOrderDetailsSN;
	}
	public void setPlaceOrderDetailsSN(String placeOrderDetailsSN) {
		this.placeOrderDetailsSN = placeOrderDetailsSN;
	}

	public String getPlaceOrderDetailsCO() {
		return placeOrderDetailsCO;
	}
	public void setPlaceOrderDetailsCO(String placeOrderDetailsCO) {
		this.placeOrderDetailsCO = placeOrderDetailsCO;
	}

	public String getPlaceOrderDetailsCN() {
		return placeOrderDetailsCN;
	}
	public void setPlaceOrderDetailsCN(String placeOrderDetailsCN) {
		this.placeOrderDetailsCN = placeOrderDetailsCN;
	}

	public String getPlaceOrderDetailsBN() {
		return placeOrderDetailsBN;
	}
	public void setPlaceOrderDetailsBN(String placeOrderDetailsBN) {
		this.placeOrderDetailsBN = placeOrderDetailsBN;
	}
	
}
