package com.amg.exchange.registration.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.ArcmateScanMaster;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.CustomerImageVerification;
import com.amg.exchange.registration.model.ScanIdTypeMaster;
import com.amg.exchange.registration.service.ICustomerImageVerification;
import com.amg.exchange.registration.service.ICustomerRegistrationBranchService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("custImageVerification")
@Scope("session")
public class CustomerImageVerificationBean<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CustomerImageVerificationBean.class);
	List<CustomerImageVerificationDatatable> list = new ArrayList<CustomerImageVerificationDatatable>();
	List<CustomerImageAcceptRejectDataTableBean> verificationList = new ArrayList<CustomerImageAcceptRejectDataTableBean>();
	List<CustomerImageVerificationDatatable> listCustomer = new ArrayList<CustomerImageVerificationDatatable>();
	
	@Autowired
	ICustomerImageVerification customerImageVerification;
	
	@Autowired
	ICustomerRegistrationBranchService<T> icustomerRegistrationService;
	
	@Autowired
	RuleEngine<T> ruleEngine;
	
	@Autowired
	IGeneralService<T> generalService;

	private Map<BigDecimal, String> idTypeMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> branchNameMap = new HashMap<BigDecimal, String>();
	public List<CustomerImageVerificationDatatable> getList() {
		return list;
	}

	public void setList(List<CustomerImageVerificationDatatable> list) {
		this.list = list;
	}

	public List<CustomerImageAcceptRejectDataTableBean> getVerificationList() {
		return verificationList;
	}

	public void setVerificationList(
			List<CustomerImageAcceptRejectDataTableBean> verificationList) {
		this.verificationList = verificationList;
	}

	public List<CustomerImageVerificationDatatable> getListCustomer() {
		return listCustomer;
	}

	public void setListCustomer(
			List<CustomerImageVerificationDatatable> listCustomer) {
		this.listCustomer = listCustomer;
	}

	// to check null
	private String nullCheck(String custname) {
		return custname == null ? "" : custname;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try {
			setUrl(null);
			setCreationDate(null);
			list.clear();
			lstApproved.clear();
			setSelectAll(false);
			reset();
			setRenderDataTableButton(false);
			/*List<CustomerImageVerification> verificationList = customerImageVerification.getList();
			
			if(verificationList!=null && !verificationList.isEmpty())
			{
				setRenderDataTableButton(true);
			}
			getIdTypeList();
			getCountryBranchList();
			for (CustomerImageVerification customerVerification : verificationList) {
				CustomerImageVerificationDatatable datatable = new CustomerImageVerificationDatatable();
				datatable.setCustomerName(nullCheck(customerVerification.getCustomer().getFirstName()) + " " + nullCheck(customerVerification.getCustomer().getMiddleName()) + " " + nullCheck(customerVerification.getCustomer().getLastName()));
				datatable.setCustomerNameLocal(nullCheck(customerVerification.getCustomer().getFirstNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getMiddleNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getLastNameLocal()));
				datatable.setCustomerReference(customerVerification.getCustomer().getCustomerReference() != null ? customerVerification.getCustomer().getCustomerReference().toPlainString() : " ");
				datatable.setIdNumber(customerVerification.getIdNumber());
				datatable.setIdExpireyDate(customerVerification.getIdExpiryDate());
				datatable.setFileId(customerVerification.getFileId());
				datatable.setCreatedBy(customerVerification.getCreatedBy());
				datatable.setCustomerImageVerificationId(customerVerification.getCustomerImageVerificationIdId());
				datatable.setIdType(customerVerification.getIdType());
				datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
				datatable.setBranchName(branchNameMap.get(customerVerification.getCountryBranchId()));
				datatable.setCustomerId(customerVerification.getCustomer().getCustomerId() != null ? customerVerification.getCustomer().getCustomerId() : BigDecimal.ZERO);
				//if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())) {
					datatable.setDisableCheck(true);
				} else {
					datatable.setDisableCheck(false);
				}
				datatable.setIsCheck(false);
				datatable.setPermanetRejectCheck(false);
				list.add(datatable);
			}
			setList(list);*/
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "customerImageVerification.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerImageVerification.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	Boolean selectAll = false;
	private Boolean renderDataTableButton = false;

	public Boolean getSelectAll() {
		return selectAll;
	}

	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	private List<BigDecimal> lstApproved = new CopyOnWriteArrayList<BigDecimal>();

	public Boolean getRenderDataTableButton() {
		return renderDataTableButton;
	}

	public void setRenderDataTableButton(Boolean renderDataTableButton) {
		this.renderDataTableButton = renderDataTableButton;
	}

	public void selecatAndDeselectAll() {
		if (selectAll) {
			for (CustomerImageVerificationDatatable approvalRecord : list) {
				if (!approvalRecord.getDisableCheck()) {
					approvalRecord.setIsCheck(true);
					lstApproved.add(approvalRecord.getCustomerImageVerificationId());
				}
			}
		} else {
			for (CustomerImageVerificationDatatable approvalRecord : list) {
				approvalRecord.setIsCheck(false);
			}
			if (lstApproved != null && !lstApproved.isEmpty()) {
				lstApproved.clear();
			}
		}
	}

	public void addingApprovalRecord(CustomerImageVerificationDatatable datatable) {
		//if (datatable.getIsCheck()) {
		if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())){
			datatable.setIsCheck(false);
			datatable.setPermanetRejectCheck(false);
			RequestContext.getCurrentInstance().execute("sameusernotapprove.show();");
		} else {
			datatable.setIsCheck(true);
			datatable.setPermanetRejectCheck(false);
			lstApproved.add(datatable.getCustomerImageVerificationId());
		}
		/*}else if(datatable.getPermanetRejectCheck()){
			datatable.setIsCheck(false);
			datatable.setPermanetRejectCheck(true);
			lstApproved.add(datatable.getCustomerImageVerificationId());
		} else {
			for (BigDecimal selectedRec : lstApproved) {
				if (selectedRec.compareTo(datatable.getCustomerImageVerificationId()) == 0) {
					lstApproved.remove(selectedRec);
				}
			}
		}*/
	}
	
	public void addingApprovalRecordReject(CustomerImageVerificationDatatable datatable) {
		//if (datatable.getIsCheck()) {
		if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())){
			datatable.setIsCheck(false);
			datatable.setPermanetRejectCheck(false);
			RequestContext.getCurrentInstance().execute("sameusernotapprove.show();");
		} else {
			datatable.setIsCheck(false);
			datatable.setPermanetRejectCheck(true);
			lstApproved.add(datatable.getCustomerImageVerificationId());
		}
		/*}else if(datatable.getPermanetRejectCheck()){
			lstApproved.add(datatable.getCustomerImageVerificationId());
		} else {
			for (BigDecimal selectedRec : lstApproved) {
				if (selectedRec.compareTo(datatable.getCustomerImageVerificationId()) == 0) {
					lstApproved.remove(selectedRec);
				}
			}
		}*/
	}
	
	SessionStateManage session=new SessionStateManage();
	
	
	
	public void approvalAllRecords(){
		if(list.size() != 0){
			//String list1 = "Fail";
			if(lstApproved.size() != 0){
				//list1=customerImageVerification.approveRecord(lstApproved,session.getUserName());
				for(CustomerImageVerificationDatatable data:list){

					  List<CustomerImageVerification> listAlraedy = customerImageVerification.getListApproval(data.getCustomerImageVerificationId());
	                  if(listAlraedy.size()!=0){
					if(data.getPermanetRejectCheck()){
						customerImageVerification.upDateRecord(data.getCustomerImageVerificationId(), session.getUserName(),Constants.REJECT_VALUE);
                       BigDecimal customerIdentityPk=null;
						
						customerIdentityPk=customerImageVerification.toFetchIdentityPk(data.getCustomerId(),data.getIdType(),data.getIdExpireyDate());
						if(customerIdentityPk != null ){
							customerImageVerification.rejectCustomerIdentityTypeStatus(customerIdentityPk, session.getUserName());
							customerImageVerification.rejectCustomerImageVeryfiedDate(data.getCustomerImageVerificationId(), session.getUserName());
						}
					}else if(data.getIsCheck()){

						customerImageVerification.upDateRecord(data.getCustomerImageVerificationId(), session.getUserName(),Constants.ACCEPT_VALUE);
					}
	
				}
				}
				
				//if(list1.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approvedsucc.show();");
					if(lstApproved != null && !lstApproved.isEmpty()){
						lstApproved.clear();
					}
					return;
				/*}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return;
				}*/
			}

			RequestContext.getCurrentInstance().execute("verifyatleastone.show();");
			return;
		}
	}
	
	public void viewImage(CustomerImageVerificationDatatable datatable) {
		setImageVerificationPk(null);
		listCustomer.clear();
		if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("sameusernotapprove.show();");
		} else {
		
			StringBuffer urlBuffer = new StringBuffer();
						
			List<ArcmateScanMaster> arcmateList = icustomerRegistrationService.fetchArcmateMasterData(Constants.VIEW, Constants.BOTH_VIEW);
			List<ScanIdTypeMaster> scanIdList = icustomerRegistrationService.fetchScanIdTypeMasterData(datatable.getIdType());
			

			if(arcmateList.size() != 0 && scanIdList.size() != 0){
				ArcmateScanMaster arcmateValue = arcmateList.get(0);
				ScanIdTypeMaster scanIdValue = scanIdList.get(0);
				String rootContext = "http://";
				urlBuffer.append(rootContext).append(arcmateValue.getIpAddress().trim()).append("/").append(arcmateValue.getContextPath().trim());
				if(arcmateValue.getUrlParamField1()!=null){
					urlBuffer.append(arcmateValue.getUrlParamField1().trim());
				}
				if(arcmateValue.getUrlParamField2()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField2().trim());
				}
				if(arcmateValue.getUrlParamField3()!=null && scanIdValue.getDocumentId()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField3().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getDocumentId());
				}
				
				if(arcmateValue.getUrlParamField4()!=null && scanIdValue.getFolderId()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField4().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFolderId());
				}
				
				if(arcmateValue.getUrlParamField5()!=null && scanIdValue.getUserName()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField5().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getUserName());
				}
				if(arcmateValue.getUrlParamField6()!=null && scanIdValue.getPassword()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField6().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getPassword());
				}
				
				if(arcmateValue.getUrlParamField7()!=null && scanIdValue.getFileCategoryId()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField7().trim()).append(arcmateValue.getFieldAssigner().trim()).append(scanIdValue.getFileCategoryId());
				}

		

				
				if(arcmateValue.getUrlParamField8()!=null){
					urlBuffer.append(arcmateValue.getFieldSeprator().trim()).append(arcmateValue.getUrlParamField8().trim()).append(arcmateValue.getFieldAssigner().trim()).append(datatable.getIdNumber());
				}

			System.out.println("SCANNED VIEW URL :  " + urlBuffer.toString());
			log.info("SCANNED VIEW URL :  " + urlBuffer.toString());

			try {

				/*ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

				context.redirect(urlBuffer.toString());*/
				datatable.setDisableCheck(false);
				setImageVerificationPk(datatable.getCustomerImageVerificationId());
				customerDetails();
				setUrl(urlBuffer.toString());
				RequestContext.getCurrentInstance().execute("ex.show();");
			} catch (Exception e) {

				datatable.setDisableCheck(true);
				log.info("Problem to redirect: " + e);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/errorscanning.xhtml");
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			}
			}else{
				RequestContext.getCurrentInstance().execute("arcmateTable.show();");
			}
		
		}
	}
	
	public void getIdTypeList() {
		idTypeMap = ruleEngine.getComponentData("Identity Type");
	}

	public void getCountryBranchList(){
		
		List<CountryBranch> branchList = generalService.getBranchDetails(session.getCountryId());
		if (branchList.size() != 0) {
			
			for (CountryBranch countryBranch : branchList) {
				branchNameMap.put(countryBranch.getCountryBranchId(), countryBranch.getBranchName());
			}
		}
	}
	
	public void rejectImage(CustomerImageVerificationDatatable datatable){
			 datatable.setPermanetRejectCheck(true);
		     RequestContext.getCurrentInstance().execute("reject.show();");
		     return;
		
	}
	
	public void conformReject(){
		try {
			if(list.size()>0){
				for (CustomerImageVerificationDatatable datatable : list) {
					if(datatable.getPermanetRejectCheck().equals(true)){
						RejectRecord(datatable);
						list.remove(datatable);
					}
				}
			}
		} catch (Exception exception) {
			 setErrorMessage(exception.getMessage());
				log.error("Reject Customer:"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}
	
	public void RejectRecord(CustomerImageVerificationDatatable datatable)throws Exception{
		BigDecimal customerIdentityPk=null;
		/*String Date=null;
		if(datatable.getIdExpireyDate() != null){
			SimpleDateFormat format=new SimpleDateFormat("dd/MM/yy");
			Date=format.format(datatable.getIdExpireyDate());
		}*/
		customerIdentityPk=customerImageVerification.toFetchIdentityPk(datatable.getCustomerId(),datatable.getIdType(),datatable.getIdExpireyDate());
		if(customerIdentityPk != null ){
			customerImageVerification.rejectCustomerIdentityTypeStatus(customerIdentityPk, session.getUserName());
			customerImageVerification.rejectCustomerImageVeryfiedDate(datatable.getCustomerImageVerificationId(), session.getUserName());
		}
	}
	
	public void conformRejectCancel(){
		try {
			if(list.size()>0){
				for (CustomerImageVerificationDatatable datatable : list) {
					if(datatable.getPermanetRejectCheck().equals(true)){
						datatable.setPermanetRejectCheck(false);
					}
				}
			}
		} catch (Exception exception) {
			 setErrorMessage(exception.getMessage());
				log.error("Reject Customer:"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}
	private BigDecimal customerId;
	private BigDecimal custIdentityTypeId;
	private String errorMessage;
	
	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public BigDecimal getCustIdentityTypeId() {
		return custIdentityTypeId;
	}

	public void setCustIdentityTypeId(BigDecimal custIdentityTypeId) {
		this.custIdentityTypeId = custIdentityTypeId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public void pageNavigationToAcceptReject() throws IOException {
			verificationList.clear();
			setRenderDataTableButton(false);
			setCreationDateLog(null);
			reset();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "customerimageacceptrejectlist.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerimageacceptrejectlist.xhtml");
		
	}
	
	public void exit() throws IOException {
		
			FacesContext.getCurrentInstance().getExternalContext().redirect(session.getMenuPage());
		
	}
	
	private String url;
	private BigDecimal imageVerificationPk=null;
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	public BigDecimal getImageVerificationPk() {
		return imageVerificationPk;
	}

	public void setImageVerificationPk(BigDecimal imageVerificationPk) {
		this.imageVerificationPk = imageVerificationPk;
	}

	public void accept(){
		try{
		for (CustomerImageVerificationDatatable imageverification : list) {
			if(imageverification.getCustomerImageVerificationId().intValue()==getImageVerificationPk().intValue()){
				List<CustomerImageVerification> listAlraedy = customerImageVerification.getListApproval(imageverification.getCustomerImageVerificationId());
				if(listAlraedy.size()!=0){
					customerImageVerification.upDateRecord(imageverification.getCustomerImageVerificationId(), session.getUserName(),Constants.ACCEPT_VALUE);
					RequestContext.getCurrentInstance().execute("approvedsucc.show();");
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					break;
				}
			}
		}
		
		}catch(NullPointerException ne){
	    	log.info("Method Name::accept"+ne.getMessage());
			  setErrorMessage("Method Name::accept"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::accept"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	
	public void reject(){
		try{
		for (CustomerImageVerificationDatatable imageverification : list) {
			if(imageverification.getCustomerImageVerificationId().intValue()==getImageVerificationPk().intValue()){
				List<CustomerImageVerification> listAlraedy = customerImageVerification.getListApproval(imageverification.getCustomerImageVerificationId());
				if(listAlraedy.size()!=0){

					customerImageVerification.upDateRecord(imageverification.getCustomerImageVerificationId(), session.getUserName(),Constants.REJECT_VALUE);
					BigDecimal customerIdentityPk=null;

					customerIdentityPk=customerImageVerification.toFetchIdentityPk(imageverification.getCustomerId(),imageverification.getIdType(),imageverification.getIdExpireyDate());
					if(customerIdentityPk != null ){
						customerImageVerification.rejectCustomerIdentityTypeStatus(customerIdentityPk, session.getUserName());
						customerImageVerification.rejectCustomerImageVeryfiedDate(imageverification.getCustomerImageVerificationId(), session.getUserName());
					}
					RequestContext.getCurrentInstance().execute("approvedsucc.show();");
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					break;
				}
			}
		}
		}catch(NullPointerException ne){
	    	log.info("Method Name::reject"+ne.getMessage());
			  setErrorMessage("Method Name::reject"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::reject"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	
	private Date creationDate=null;
	private Date creationDateLog=null;
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	public Date getCreationDateLog() {
		return creationDateLog;
	}

	public void setCreationDateLog(Date creationDateLog) {
		this.creationDateLog = creationDateLog;
	}

	public void onDateSelect(SelectEvent event) throws ParseException {

		try {
			setUrl(null);
			list.clear();
			lstApproved.clear();
			setSelectAll(false);
			setRenderDataTableButton(false);
			List<CustomerImageVerification> verificationList = customerImageVerification.getList(getCreationDate());
			
			if(verificationList!=null && !verificationList.isEmpty())
			{
				setRenderDataTableButton(true);
			}
			getIdTypeList();
			getCountryBranchList();
			for (CustomerImageVerification customerVerification : verificationList) {
				CustomerImageVerificationDatatable datatable = new CustomerImageVerificationDatatable();
				datatable.setCustomerName(nullCheck(customerVerification.getCustomer().getFirstName()) + " " + nullCheck(customerVerification.getCustomer().getMiddleName()) + " " + nullCheck(customerVerification.getCustomer().getLastName()));
				datatable.setCustomerNameLocal(nullCheck(customerVerification.getCustomer().getFirstNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getMiddleNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getLastNameLocal()));
				datatable.setCustomerReference(customerVerification.getCustomer().getCustomerReference() != null ? customerVerification.getCustomer().getCustomerReference().toPlainString() : " ");
				datatable.setIdNumber(customerVerification.getIdNumber());
				datatable.setIdExpireyDate(customerVerification.getIdExpiryDate());
				datatable.setFileId(customerVerification.getFileId());
				datatable.setCreatedBy(customerVerification.getCreatedBy());
				datatable.setCustomerImageVerificationId(customerVerification.getCustomerImageVerificationIdId());
				datatable.setIdType(customerVerification.getIdType());
				datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
				datatable.setBranchName(branchNameMap.get(customerVerification.getCountryBranchId()));
				datatable.setCustomerId(customerVerification.getCustomer().getCustomerId() != null ? customerVerification.getCustomer().getCustomerId() : BigDecimal.ZERO);
				//if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())) {
					datatable.setDisableCheck(true);
				/*} else {
					datatable.setDisableCheck(false);
				}*/
				datatable.setIsCheck(false);
				datatable.setPermanetRejectCheck(false);
				list.add(datatable);
			}
			setList(list);
			//FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerImageVerification.xhtml");
		}catch(NullPointerException ne){
	    	log.info("Method Name::onDateSelect"+ne.getMessage());
			  setErrorMessage("Method Name::onDateSelect"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::onDateSelect"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	

	
	public void customerDetails(){


		try {
			listCustomer.clear();
			for (CustomerImageVerificationDatatable customerVerification : list) {
				if(customerVerification.getCustomerImageVerificationId().intValue()==getImageVerificationPk().intValue()){
				CustomerImageVerificationDatatable datatable = new CustomerImageVerificationDatatable();
				datatable.setCustomerName(customerVerification.getCustomerName());
				datatable.setCustomerNameLocal(customerVerification.getCustomerNameLocal());
				datatable.setCustomerReference(customerVerification.getCustomerReference());
				datatable.setIdNumber(customerVerification.getIdNumber());
				datatable.setIdExpireyDate(customerVerification.getIdExpireyDate());
				datatable.setIdType(customerVerification.getIdType());
				datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
				listCustomer.add(datatable);
				}
			}
			setListCustomer(listCustomer);
			
		}catch(NullPointerException ne){
	    	log.info("Method Name::customerDetails"+ne.getMessage());
			  setErrorMessage("Method Name::customerDetails"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::customerDetails"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	
	
	}
	
	public void onDateLog(SelectEvent event){

		try {
			verificationList.clear();
			List<CustomerImageVerification> verificationListData = customerImageVerification.getAllVerifiedList(getCreationDateLog());
			
			getIdTypeList();
			getCountryBranchList();
			if(verificationListData.size()>0){
			for (CustomerImageVerification customerVerification : verificationListData) {
				CustomerImageAcceptRejectDataTableBean datatable = new CustomerImageAcceptRejectDataTableBean();
				datatable.setCustomerName(nullCheck(customerVerification.getCustomer().getFirstName()) + " " + nullCheck(customerVerification.getCustomer().getMiddleName()) + " " + nullCheck(customerVerification.getCustomer().getLastName()));
				datatable.setCustomerNameLocal(nullCheck(customerVerification.getCustomer().getFirstNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getMiddleNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getLastNameLocal()));
				datatable.setCustomerReference(customerVerification.getCustomer().getCustomerReference() != null ? customerVerification.getCustomer().getCustomerReference().toPlainString() : " ");
				datatable.setIdNumber(customerVerification.getIdNumber());
				datatable.setIdExpireyDate(customerVerification.getIdExpiryDate());
				datatable.setCreatedBy(customerVerification.getCreatedBy());
				if(customerVerification.getCreationDate()!=null){
				datatable.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy").format(customerVerification.getCreationDate()));
				}
				datatable.setIdType(customerVerification.getIdType());
				datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
				datatable.setBranchName(branchNameMap.get(customerVerification.getCountryBranchId()));
				if(customerVerification.getVerifiedDate()!=null){
					datatable.setVerifiedDate(new SimpleDateFormat("dd/MM/yyyy").format(customerVerification.getVerifiedDate()));
					}
				datatable.setVerifiedBy(customerVerification.getVerifiedBy());
				if(customerVerification.getComplianceStatus()!=null && customerVerification.getComplianceStatus().equalsIgnoreCase(Constants.ACCEPT_VALUE)){
					datatable.setComplianceStatus(Constants.ACCEPT);
				}else if(customerVerification.getComplianceStatus()!=null && customerVerification.getComplianceStatus().equalsIgnoreCase(Constants.REJECT_VALUE)){
					datatable.setComplianceStatus(Constants.REJECT);
				}else{
					datatable.setComplianceStatus(Constants.ACCEPT);
				}
				verificationList.add(datatable);
			}
			setVerificationList(verificationList);
			setRenderDataTableButton(true);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerimageacceptrejectlist.xhtml");
			}else{
				setRenderDataTableButton(false);
				RequestContext.getCurrentInstance().execute("norecord.show();");
				return;
			}
		}catch(NullPointerException ne){
	    	log.info("Method Name::pageNavigationToAcceptReject"+ne.getMessage());
			  setErrorMessage("Method Name::pageNavigationToAcceptReject"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Method Name::pageNavigationToAcceptReject"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}	
	}
	
	
	
	
	
	//Anil(01/10/2018)	
	private BigDecimal cusId;
	private String idNum;		
	
	
	public void reset(){
		setCreationDate(null);
		setCreationDateLog(null);
		setCusId(null);
		setIdNum(null);
		list.clear();
		verificationList.clear();
	}
	
	
	public void search(){
		try{
			if(getCreationDate()==null && getCusId()==null && (getIdNum()==null || getIdNum().equalsIgnoreCase(""))){
				RequestContext.getCurrentInstance().execute("pleaseEnterAnyOne.show();");
				return;
			}else{
				setUrl(null);
				list.clear();
				lstApproved.clear();
				setSelectAll(false);
				setRenderDataTableButton(false);
				List<CustomerImageVerification> verificationList = customerImageVerification.getCustomerDetails(getCreationDate(),getCusId(),getIdNum());
				
				if(verificationList!=null && verificationList.size() > 0)
				{
					setRenderDataTableButton(true);
					getIdTypeList();
					getCountryBranchList();
					for (CustomerImageVerification customerVerification : verificationList) {
						CustomerImageVerificationDatatable datatable = new CustomerImageVerificationDatatable();
						datatable.setCustomerName(nullCheck(customerVerification.getCustomer().getFirstName()) + " " + nullCheck(customerVerification.getCustomer().getMiddleName()) + " " + nullCheck(customerVerification.getCustomer().getLastName()));
						datatable.setCustomerNameLocal(nullCheck(customerVerification.getCustomer().getFirstNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getMiddleNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getLastNameLocal()));
						datatable.setCustomerReference(customerVerification.getCustomer().getCustomerReference() != null ? customerVerification.getCustomer().getCustomerReference().toPlainString() : " ");
						datatable.setIdNumber(customerVerification.getIdNumber());
						datatable.setIdExpireyDate(customerVerification.getIdExpiryDate());
						datatable.setFileId(customerVerification.getFileId());
						datatable.setCreatedBy(customerVerification.getCreatedBy());
						datatable.setCustomerImageVerificationId(customerVerification.getCustomerImageVerificationIdId());
						datatable.setIdType(customerVerification.getIdType());
						datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
						datatable.setBranchName(branchNameMap.get(customerVerification.getCountryBranchId()));
						datatable.setCustomerId(customerVerification.getCustomer().getCustomerId() != null ? customerVerification.getCustomer().getCustomerId() : BigDecimal.ZERO);
						datatable.setDisableCheck(true);
						datatable.setIsCheck(false);
						datatable.setPermanetRejectCheck(false);
						list.add(datatable);
					}
					setList(list);
				}else{
					list.clear();
					RequestContext.getCurrentInstance().execute("noRecords.show();");
					return;
				}
			}			
		}catch(Exception exception) {
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;			
		}
	}	
	
	
	public void searchLog(){
		try{
			if(getCreationDateLog()==null && getCusId()==null && (getIdNum()==null || getIdNum().equalsIgnoreCase(""))){
				RequestContext.getCurrentInstance().execute("pleaseEnterAnyOne.show();");
				return;
			}else{
				verificationList.clear();
				List<CustomerImageVerification> verificationListData = customerImageVerification.getCustomerDetailsLog(getCreationDateLog(),getCusId(),getIdNum());
				
				getIdTypeList();
				getCountryBranchList();
				if(verificationListData.size()>0){
				for (CustomerImageVerification customerVerification : verificationListData) {
					CustomerImageAcceptRejectDataTableBean datatable = new CustomerImageAcceptRejectDataTableBean();
					datatable.setCustomerName(nullCheck(customerVerification.getCustomer().getFirstName()) + " " + nullCheck(customerVerification.getCustomer().getMiddleName()) + " " + nullCheck(customerVerification.getCustomer().getLastName()));
					datatable.setCustomerNameLocal(nullCheck(customerVerification.getCustomer().getFirstNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getMiddleNameLocal()) + " " + nullCheck(customerVerification.getCustomer().getLastNameLocal()));
					datatable.setCustomerReference(customerVerification.getCustomer().getCustomerReference() != null ? customerVerification.getCustomer().getCustomerReference().toPlainString() : " ");
					datatable.setIdNumber(customerVerification.getIdNumber());
					datatable.setIdExpireyDate(customerVerification.getIdExpiryDate());
					datatable.setCreatedBy(customerVerification.getCreatedBy());
					if(customerVerification.getCreationDate()!=null){
					datatable.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy").format(customerVerification.getCreationDate()));
					}
					datatable.setIdType(customerVerification.getIdType());
					datatable.setIdTypeName(idTypeMap.get(customerVerification.getIdType()));
					datatable.setBranchName(branchNameMap.get(customerVerification.getCountryBranchId()));
					if(customerVerification.getVerifiedDate()!=null){
						datatable.setVerifiedDate(new SimpleDateFormat("dd/MM/yyyy").format(customerVerification.getVerifiedDate()));
						}
					datatable.setVerifiedBy(customerVerification.getVerifiedBy());
					if(customerVerification.getComplianceStatus()!=null && customerVerification.getComplianceStatus().equalsIgnoreCase(Constants.ACCEPT_VALUE)){
						datatable.setComplianceStatus(Constants.ACCEPT);
					}else if(customerVerification.getComplianceStatus()!=null && customerVerification.getComplianceStatus().equalsIgnoreCase(Constants.REJECT_VALUE)){
						datatable.setComplianceStatus(Constants.REJECT);
					}else{
						datatable.setComplianceStatus(Constants.ACCEPT);
					}
					verificationList.add(datatable);
				}
				setVerificationList(verificationList);
				setRenderDataTableButton(true);
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/customerimageacceptrejectlist.xhtml");
				}else{
					setRenderDataTableButton(false);
					RequestContext.getCurrentInstance().execute("norecord.show();");
					return;
				}
			}
		}catch(Exception exception) {
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;			
		}
	}
	
	
	
	//Getters and setters.
	public BigDecimal getCusId() {
		return cusId;
	}

	public void setCusId(BigDecimal cusId) {
		this.cusId = cusId;
	}

	public String getIdNum() {
		return idNum;
	}

	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	
}
