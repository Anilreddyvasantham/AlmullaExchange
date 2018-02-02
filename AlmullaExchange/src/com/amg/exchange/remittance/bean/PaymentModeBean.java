package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.service.IPaymentService;
import com.amg.exchange.treasury.bean.BankIndicatorBean;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("paymentModeBean")
@Scope("session")
public class PaymentModeBean<T> implements Serializable {
	private static Logger log = Logger.getLogger(BankIndicatorBean.class);
 	private static final long serialVersionUID = 1L;
 	private BigDecimal paymentModeId;
	private String paymentCode=null;
	private String paymentDescEnglish=null;
	private String paymentDescArabic=null;
	private String createdBy=null;
	private Date createdDate=null;
	private String modifiedBy=null;
	private Date modifiedDate=null;
	private String isActive;
	private BigDecimal paymentModeDescId;

	private LanguageType languageType;
	private String localPaymentName;
	private Boolean booRenderDataTable=false;
	private Boolean booRenderSave=false;
	private Boolean booServiceApplicabilityCheck=false;
	private Boolean booServiceApplicabilityCheckForDataTable=false;
	private Boolean booHideClear=false;
	private Boolean booHideEdit=false;
	private BigDecimal pymPk=null;
	private BigDecimal pymDescpken=null;
	private BigDecimal pymDescpkarb=null;
	
	private boolean readOnly=false;
	private boolean hideButton=true;
	private boolean shwApprovbutton=false;
	private Boolean renderEditButton=false;
	private String dynamicLabelForActivateDeactivate;
	private String remarks;
	private String approvedBy;
	private Date approvedDate;
	private Boolean submit = false;
	private Boolean booUpdateCheck=false;
	private String errorMessage;

	
	private SessionStateManage session=new SessionStateManage();
	private List<PaymentModeDatatable> listData=new CopyOnWriteArrayList <PaymentModeDatatable>();
	private List<PaymentModeDatatable> listData2=new CopyOnWriteArrayList <PaymentModeDatatable>();
	private List<PaymentModeDatatable> listDataApproval=new CopyOnWriteArrayList <PaymentModeDatatable>();
	private List<PaymentMode> listPayment=new ArrayList<PaymentMode>();
	
	
	public List<PaymentModeDatatable> getListData() {
		return listData;
	}
	public void setListData(List<PaymentModeDatatable> listData) {
		this.listData = listData;
	}
	public BigDecimal getPaymentModeId() {
		return paymentModeId;
	}
	public void setPaymentModeId(BigDecimal paymentModeId) {
		this.paymentModeId = paymentModeId;
	}
	public String getPaymentCode() {
		return paymentCode;
	}
	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}
	public String getPaymentDescEnglish() {
		return paymentDescEnglish;
	}
	public void setPaymentDescEnglish(String paymentDescEnglish) {
		this.paymentDescEnglish = paymentDescEnglish;
	}
	public String getPaymentDescArabic() {
		return paymentDescArabic;
	}
	public void setPaymentDescArabic(String paymentDescArabic) {
		this.paymentDescArabic = paymentDescArabic;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	public BigDecimal getPaymentModeDescId() {
		return paymentModeDescId;
	}
	public void setPaymentModeDescId(BigDecimal paymentModeDescId) {
		this.paymentModeDescId = paymentModeDescId;
	}
	 
	public LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	public String getLocalPaymentName() {
		return localPaymentName;
	}
	public void setLocalPaymentName(String localPaymentName) {
		this.localPaymentName = localPaymentName;
	}
	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}
	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}
	public Boolean getBooRenderSave() {
		return booRenderSave;
	}
	public void setBooRenderSave(Boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}
	public Boolean getBooServiceApplicabilityCheck() {
		return booServiceApplicabilityCheck;
	}
	public void setBooServiceApplicabilityCheck(Boolean booServiceApplicabilityCheck) {
		this.booServiceApplicabilityCheck = booServiceApplicabilityCheck;
	}
	public Boolean getBooServiceApplicabilityCheckForDataTable() {
		return booServiceApplicabilityCheckForDataTable;
	}
	public void setBooServiceApplicabilityCheckForDataTable(
			Boolean booServiceApplicabilityCheckForDataTable) {
		this.booServiceApplicabilityCheckForDataTable = booServiceApplicabilityCheckForDataTable;
	}
	public Boolean getBooHideClear() {
		return booHideClear;
	}
	public void setBooHideClear(Boolean booHideClear) {
		this.booHideClear = booHideClear;
	}
	public Boolean getBooHideEdit() {
		return booHideEdit;
	}
	public void setBooHideEdit(Boolean booHideEdit) {
		this.booHideEdit = booHideEdit;
	}
	public Boolean getBooUpdateCheck() {
		return booUpdateCheck;
	}
	public void setBooUpdateCheck(Boolean booUpdateCheck) {
		this.booUpdateCheck = booUpdateCheck;
	}
	
 
	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}
	
	
	public void clearAllFields() {
		 setPaymentCode(null);
		 setPaymentDescEnglish(null);
		 setPaymentDescArabic(null);
		 setLocalPaymentName(null);
		 setPymPk(null);
		 setPymDescpken(null);
		 setPymDescpkarb(null);
		}
	
	public void editRecord(PaymentModeDatatable paymentDTObj) {
	 
		 setPaymentCode(paymentDTObj.getModeOfPayment());
		 setPaymentDescEnglish(paymentDTObj.getModeOfPaymentDesc());
		 setPaymentDescArabic(paymentDTObj.getModeOfPaymentDescInLocal());
		 setLocalPaymentName(paymentDTObj.getModeOfPaymentInLocal());
		setPymPk(paymentDTObj.getDtpymPk());
		setPymDescpken(paymentDTObj.getDtpymDescpken());
		 setPymDescpkarb(paymentDTObj.getDtpymDescpkarb());
		 setDynamicLabelForActivateDeactivate(paymentDTObj.getDynamicLabelForActivateDeactivate());
		 setRenderEditButton(paymentDTObj.getRenderEditButton());
		 setIsActive(Constants.U);
		 setCreatedBy(paymentDTObj.getCreatedBy());
		setCreatedDate(paymentDTObj.getCreatedDate());
		setModifiedBy(paymentDTObj.getModifiedBy() );
		setModifiedDate( paymentDTObj.getModifiedDate());
			setSubmit(true);
			setBooHideClear(true);
			setBooHideEdit(true);
		 listData.remove(paymentDTObj);
		 if(listData.size()<=0){
			 setBooRenderSave(false);
				setBooRenderDataTable(false);
		 }
	}

	public void removeRecord(PaymentModeDatatable paymentDTObj) throws IOException {
		
		 /*PaymentMode paymentMode=new PaymentMode();
		 if(paymentDTObj.getDtpymPk()!=null){
			 paymentMode.setPaymentModeId(paymentDTObj.getDtpymPk());
		
			 
			 paymentMode.setPaymentCode( paymentDTObj.getModeOfPayment()); 
			 //paymentMode.setPaymentDescEnglish(paymentDTObj.getModeOfPaymentDesc());
			 //paymentMode.setPaymentDescArabic(paymentDTObj.getModeOfPaymentDescInLocal());
			 paymentMode.setCreatedBy(paymentDTObj.getCreatedBy());
			 paymentMode.setCreatedDate(paymentDTObj.getCreatedDate());
			 paymentMode.setModifiedBy(session.getUserName());
			 paymentMode.setModifiedDate(new Date());
			 paymentMode.setIsActive("N");
			 paymentService.save(paymentMode);
		 }
	listData.remove(paymentDTObj);*/
		try{
		 if(paymentDTObj.getDtpymPk()==null){
			 listData.remove(paymentDTObj);
			 if(listData.size()<0){
				 setBooRenderSave(false);
					setBooRenderDataTable(false);
			 }
			 }/*else{
				 
				 if(paymentDTObj.getIsActive().equalsIgnoreCase("Y")){
					 setApprovedBy(paymentDTObj.getApprovedBy());
					 setApprovedDate(paymentDTObj.getApprovedDate());
					 RequestContext.getCurrentInstance().execute("remarks.show();");
				 }*/else{
					 delete(paymentDTObj);
					 listData.clear();
					 view();
					 listData.addAll(listData2);
				 }
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: removeRecord"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
			 }
	
		 
		
	
	
	public void delete(PaymentModeDatatable paymentDTObj) throws IOException{
		
		listData.clear();
		try{				
			PaymentMode paymentMode=new PaymentMode();
			 
			 paymentMode.setPaymentCode( paymentDTObj.getModeOfPayment()); 
			 if(paymentDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
				 paymentMode.setIsActive(Constants.U);
			 }else if (paymentDTObj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				 setRenderEditButton(true);
				 paymentMode.setRemarks(paymentDTObj.getRemarks());
				 paymentMode.setIsActive(Constants.D);
			}
			 if(paymentDTObj.getDtpymPk()!=null){
				 paymentMode.setPaymentModeId(paymentDTObj.getDtpymPk());
				 paymentMode.setCreatedBy(paymentDTObj.getCreatedBy());
				 paymentMode.setCreatedDate(paymentDTObj.getCreatedDate());
				 paymentMode.setModifiedBy(session.getUserName());
				 paymentMode.setModifiedDate(new Date());
			}else{
				 paymentMode.setCreatedBy(session.getUserName());
				 paymentMode.setCreatedDate(new Date());
			}
			 paymentService.save(paymentMode);
			
		   PaymentModeDesc  paymentModeDesc=new  PaymentModeDesc();
		  
		   
		   LanguageType languageType=new LanguageType();
		   languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
		   paymentModeDesc.setLanguageType(languageType);
		   paymentModeDesc.setLocalPaymentName(paymentDTObj.getModeOfPaymentDesc());
		   paymentModeDesc.setPaymentModeDescId(paymentDTObj.getDtpymDescpken());
			   paymentModeDesc.setPaymentMode(paymentMode);  
			   paymentService.saveRecord(paymentModeDesc);
		 
		   
		  PaymentModeDesc  paymentModeDesc2=new  PaymentModeDesc();
		   LanguageType languageType2=new LanguageType();
		   languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
		   paymentModeDesc2.setLanguageType(languageType2);
		   paymentModeDesc2.setLocalPaymentName(paymentDTObj.getModeOfPaymentDescInLocal());
		   paymentModeDesc2.setPaymentModeDescId(paymentDTObj.getDtpymDescpkarb());
		  
			   paymentModeDesc2.setPaymentMode(paymentMode);
			   
			   paymentService.saveRecord(paymentModeDesc2);
		   
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: delete"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
 	public void paymentModeMasterPageNavigation() {
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		setReadOnly(false);
		setHideButton(true);
		setShwApprovbutton(false);
		setBooHideClear(false);
		setBooHideEdit(false);
		
		listData.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "paymentMode.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
		} catch (Exception e) {
			 log.error("Problem Occured paymentModeMasterPageNavigation" +e);
		}

	}
 	
 	public void clickOnOKSave() {
		listData.clear();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
	/*	setPymPk(null);
		setPymDescpken(null);
		setPymDescpkarb(null);*/
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public   void addRecordsToDataTable(){
		setSubmit(false);
		if(listData2.size()==0){
			
		PaymentModeDatatable paymentDt=new PaymentModeDatatable();
        
           
        paymentDt.setModeOfPayment(getPaymentCode());
        paymentDt.setModeOfPaymentDesc(getPaymentDescEnglish());
        paymentDt.setModeOfPaymentDescInLocal(getPaymentDescArabic());
        paymentDt.setModeOfPaymentInLocal(getLocalPaymentName());
        
       paymentDt.setDtpymPk(getPymPk());
       
        if(getPymPk()!=null){
        	paymentDt.setCreatedBy(getCreatedBy());
        	paymentDt.setCreatedDate(getCreatedDate());
        	paymentDt.setIsActive(Constants.U);
        	paymentDt.setModifiedBy(session.getUserName());
         	paymentDt.setModifiedDate(new Date());
        	paymentDt.setRenderEditButton(getRenderEditButton());
        	paymentDt.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
        }
        else{
        	paymentDt.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
        	paymentDt.setRenderEditButton(true);
        	paymentDt.setCreatedBy(session.getUserName());
        	paymentDt.setCreatedDate(new Date());
        	paymentDt.setIsActive(Constants.U);
        	
        }
       paymentDt.setDtpymDescpken(getPymDescpken());
        
        
        paymentDt.setDtpymDescpkarb(getPymDescpkarb());
       
        listData.add(paymentDt);
		}
		if(listData.size()>0){
			if(listData2.size()>0){
				for (PaymentModeDatatable payDt : listData) {
					for (PaymentModeDatatable PtData : listData2) {
						if(payDt.getModeOfPayment().equals(PtData.getModeOfPayment())){
							listData2.remove(PtData);
						}
					}
				}
				
			}
			listData.addAll(listData2);
		}
			else{
				listData.addAll(listData2);
			}
			clearAllFields();
			 setBooRenderDataTable(true);
			 setBooRenderSave(true);
			 setBooHideEdit(false);
			 setBooHideClear(false);
		 
		
	}
	
	@Autowired
	IPaymentService paymentService;
	
	public void saveDataTableRecords(){
		 		setSubmit(true);	
		if( listData.isEmpty()){
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		}else{
try{
		for(PaymentModeDatatable  paymentDTObj:listData){
			 
			 PaymentMode paymentMode=new PaymentMode();
		
			 if(paymentDTObj.getDtpymPk()!=null){
			 paymentMode.setPaymentModeId(paymentDTObj.getDtpymPk());
			 paymentMode.setRemarks(paymentDTObj.getRemarks());
		}
			 
			 paymentMode.setPaymentCode( paymentDTObj.getModeOfPayment()); 
			 paymentMode.setIsActive(paymentDTObj.getIsActive());
			 //paymentMode.setPaymentDescEnglish(paymentDTObj.getModeOfPaymentDesc());
			 //paymentMode.setPaymentDescArabic(paymentDTObj.getModeOfPaymentDescInLocal());
			 
		
			 if(paymentDTObj.getDtpymPk()!=null){
			 paymentMode.setModifiedBy(paymentDTObj.getModifiedBy());
			 paymentMode.setModifiedDate(paymentDTObj.getModifiedDate());
			 paymentMode.setCreatedBy(paymentDTObj.getCreatedBy());
			 paymentMode.setCreatedDate(paymentDTObj.getCreatedDate());
			 }
			 else{
				 paymentMode.setCreatedBy(session.getUserName());
				 paymentMode.setCreatedDate(new Date());
			 }
			 paymentMode.setApprovBy(paymentDTObj.getApprovedBy());
			 paymentMode.setApprovDate(paymentDTObj.getApprovedDate());

			// paymentMode.setIsActive("U");
			 paymentService.save(paymentMode);
			
		   PaymentModeDesc  paymentModeDesc=new  PaymentModeDesc();
		   LanguageType languageType=new LanguageType();
		   languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
		   paymentModeDesc.setLanguageType(languageType);
		   paymentModeDesc.setPaymentModeDescId(paymentDTObj.getDtpymDescpken());
		   paymentModeDesc.setLocalPaymentName(paymentDTObj.getModeOfPaymentDesc());
		   paymentModeDesc.setPaymentMode(paymentMode);  
		   paymentService.saveRecord(paymentModeDesc);
		   
		  /*if(paymentDTObj.getDtpymDescpken()==null){
			  paymentModeDesc.setLanguageType(languageType);
			   paymentModeDesc.setLocalPaymentName(paymentDTObj.getModeOfPaymentDesc()); 
			   paymentModeDesc.setPaymentMode(paymentMode);  
			   paymentService.saveRecord(paymentModeDesc);
		 
		   }*/
		  PaymentModeDesc  paymentModeDesc2=new  PaymentModeDesc();
		   LanguageType languageType2=new LanguageType();
		   languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
		   paymentModeDesc2.setLanguageType(languageType2);
		   paymentModeDesc2.setPaymentModeDescId(paymentDTObj.getDtpymDescpkarb());
		   paymentModeDesc2.setLocalPaymentName(paymentDTObj.getModeOfPaymentDescInLocal());
		   paymentModeDesc2.setPaymentMode(paymentMode);  
		   paymentService.saveRecord(paymentModeDesc2);
		   
		 /*  if(paymentDTObj.getDtpymDescpkarb()==null){
			   paymentModeDesc2.setLocalPaymentName(paymentDTObj.getModeOfPaymentDescInLocal()); 
			   paymentModeDesc2.setPaymentMode(paymentMode);
			   
			   paymentService.saveRecord(paymentModeDesc2);
			   }*/
		   }
		RequestContext.getCurrentInstance().execute("complete.show();");
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: saveDataTableRecords"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e)
		{
			log.info("Problem Occured While Saving =============");		setErrorMessage(e.getMessage());
			setErrorMessage("Saving Error : "+e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
 
}
//RequestContext.getCurrentInstance().execute("complete.show();");
	
		} 
	

		}
	
		public void checkDelivery(){
		if(listData.size()>0){
			for(PaymentModeDatatable reMoMasObj: listData){
				if(reMoMasObj.getModeOfPayment().equalsIgnoreCase((getPaymentCode()))){
					 setPaymentCode(null);
					 setPaymentDescEnglish(null);
					 setPaymentDescArabic(null);
					 setLocalPaymentName(null);
					 setPymPk(null);
					/*
					 setPymDescpken(null);
					 setPymDescpkarb(null);*/
					 RequestContext.getCurrentInstance().execute("datatable.show();");
					 
					return;
				}
			} 
		} 
		if(getPaymentCode()!=null){
			addRecordsToDataTable();
		}
	}
	
	/*
	 * Start
	 * Added by Ramakrishna
	 */

	List<PaymentMode> fetchExistRec=new ArrayList<PaymentMode>();
	List<PaymentModeDesc>  paytmDesc=new ArrayList<PaymentModeDesc>();
	public void autoPopRec() {
		//nag code 17/04/2015
		try{
		 setPaymentDescEnglish(null);
		 setPaymentDescArabic(null);
		 setLocalPaymentName(null);
		 setPymPk(null);
		 setPymDescpken(null);
		 setPymDescpkarb(null);
	   if(null!=getPaymentCode()){
		
		fetchExistRec=paymentService.searchRecord(getPaymentCode().trim());
		}
		if(fetchExistRec.size()>0)
		{
		 
			 RequestContext.getCurrentInstance().execute("duplicaterecord.show();");
			 return;
		/* setPymPk(fetchExistRec.get(0).getPaymentModeId());
		 setPaymentCode(fetchExistRec.get(0).getPaymentCode());
		 setCreatedBy(fetchExistRec.get(0).getCreatedBy());
		 setCreatedDate(fetchExistRec.get(0).getCreatedDate());
		 setModifiedBy(fetchExistRec.get(0).getModifiedBy());
		 setModifiedDate(fetchExistRec.get(0).getModifiedDate());
		// setPaymentDescEnglish(fetchExistRec.get(0).getPaymentDescEnglish());
		 //setPaymentDescArabic(fetchExistRec.get(0).getPaymentDescArabic());
		 paytmDesc=paymentService.paymentDescRec(fetchExistRec.get(0).getPaymentModeId());
		
		 if(paytmDesc.size()>0)
		 {
			 if(fetchExistRec.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
				 setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				 setRenderEditButton(true);
			 }else if (fetchExistRec.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
				 setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				 setRenderEditButton(true);
			}else if (fetchExistRec.get(0).getIsActive().equalsIgnoreCase(Constants.U)) {
				 setDynamicLabelForActivateDeactivate(Constants.DELETE);
				 setRenderEditButton(true);
				// setIsActive(fetchExistRec.get(0).getIsActive() );
				 
			}
			 for(PaymentModeDesc ptmDespk:paytmDesc)
			 {
				 int res=ptmDespk.getLanguageType().getLanguageId().intValueExact();
				 
				 if(res==1)
				 {
					 setPymDescpken(ptmDespk.getPaymentModeDescId()); 
			 
					setPaymentDescEnglish(ptmDespk.getLocalPaymentName());
					//setLocalPaymentName(ptmDespk.getLocalPaymentName());
				 }else if(res==2)
				 {
				 
					 setPymDescpkarb(ptmDespk.getPaymentModeDescId());
					// setLocalPaymentName(ptmDespk.getLocalPaymentName());
					setPaymentDescArabic(ptmDespk.getLocalPaymentName());
					 
				 }
					 
				 
			 }
		 
		 
		  
 		 setPymDescpken(paytmDesc.get(0).getPaymentModeDescId());
		 setPymDescpkarb(paytmDesc.get(1).getPaymentModeDescId());
		 } else
		 {
			 setPymDescpken(null); 
			 setPymDescpkarb(null);
		 } */
		
		}else{
			//checkDelivery();
			//setPymPk(null);
		}
		}catch(Exception e)
		{
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
 }
	}
	
	
	
	
	public BigDecimal getPymPk() {
		return pymPk;
	}
	public void setPymPk(BigDecimal pymPk) {
		this.pymPk = pymPk;
	}
	public BigDecimal getPymDescpken() {
		return pymDescpken;
	}
	public void setPymDescpken(BigDecimal pymDescpken) {
		this.pymDescpken = pymDescpken;
	}
	public BigDecimal getPymDescpkarb() {
		return pymDescpkarb;
	}
	public void setPymDescpkarb(BigDecimal pymDescpkarb) {
		this.pymDescpkarb = pymDescpkarb;
	}
	
	
	public List<String> autoCompleteData(String qry)
	{
		if(qry.length()>0)
		{
		return paymentService.getPaymntcodelist(qry);
		} else {
			
			return null;
		}
		
	}
	

	
public void update()
{
	
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
	} catch (IOException e) {
		e.printStackTrace();
	}
	
}
		
	public void navigationApproval()
	{
		approveList();
		try{
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "PaymentModeApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/PaymentModeApproval.xhtml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void navigaEdit(PaymentModeDatatable datatable)
	{
       log.info( "ENTERED INTO navigaEdit(PaymentModeDatatable datatable) METHOD");
		//listDataApproval.clear();
		/*if(session.getUserName().equalsIgnoreCase(datatable.getCreatedBy()))*/
		 if((datatable.getModifiedBy()==null?datatable.getCreatedBy():datatable.getModifiedBy()).equalsIgnoreCase(session.getUserName()))
		{
			RequestContext.getCurrentInstance().execute("username.show();");
			return;
		} else{
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		setPaymentCode(datatable.getModeOfPayment());
		setPaymentDescEnglish(datatable.getModeOfPaymentDesc());
		setPaymentDescArabic(datatable.getModeOfPaymentDescInLocal());
		setLocalPaymentName(datatable.getModeOfPaymentInLocal());
		setPymPk(datatable.getDtpymPk());
		setCreatedBy(datatable.getCreatedBy());
		setCreatedDate(datatable.getCreatedDate());
		setIsActive(datatable.getIsActive());
		setModifiedBy(datatable.getModifiedBy());
		setModifiedDate(datatable.getModifiedDate());
		setReadOnly(true);
		setHideButton(false);
		setShwApprovbutton(true);
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		}
	}
	
	public void cancel()
	{
		approveList();
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/PaymentModeApproval.xhtml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void clickOnOkButton(){
		approveList();
		try{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/PaymentModeApproval.xhtml");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	
	public void approv()
	{
		//listData2.clear();
		
	
		/* PaymentMode paymentMode=new PaymentMode();
			
		 paymentMode.setPaymentModeId(getPymPk());
		 
		 paymentMode.setPaymentCode(getPaymentCode()); 
		 //paymentMode.setPaymentDescEnglish(getLocalPaymentName());
		 //paymentMode.setPaymentDescArabic(getPaymentDescArabic());
		 paymentMode.setCreatedBy(getCreatedBy());
		 paymentMode.setCreatedDate(getCreatedDate());
		 paymentMode.setIsActive(Constants.Yes);
		 paymentMode.setApprovBy(session.getUserName());
		 paymentMode.setApprovDate(new Date());
		 
			 paymentMode.setModifiedBy(getModifiedBy());
			 paymentMode.setModifiedDate(getModifiedDate());
		 */
		try{
		 String approveMsg=paymentService.approveRecord(getPymPk(),session.getUserName());
		 if(approveMsg.equalsIgnoreCase("Success")){
		 
		 RequestContext.getCurrentInstance().execute("approv.show();");
		 }else{
			 RequestContext.getCurrentInstance().execute("alreadyapprov.show();");	
		 }
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: approv"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e)
		{
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
 
}
		}
		
		

	

	
    

	public  void approveList() {
		try{
		log.info( "Entered into approveList() method=======");
		listDataApproval.clear();
		listPayment=paymentService.getPayment();
		if(listPayment.size()>0){
		for(PaymentMode payment:listPayment)
		{
			PaymentModeDatatable datatable=new PaymentModeDatatable();
			datatable.setDtpymPk(payment.getPaymentModeId());
			datatable.setModeOfPayment(payment.getPaymentCode());
			//datatable.setModeOfPaymentDesc(payment.getPaymentDescEnglish());
			//datatable.setModeOfPaymentInLocal(payment.getPaymentDescArabic());
			datatable.setCreatedBy(payment.getCreatedBy());
			datatable.setCreatedDate(payment.getCreatedDate());
			datatable.setIsActive(payment.getIsActive());
			datatable.setModifiedBy(payment.getModifiedBy());
			datatable.setModifiedDate(payment.getModifiedDate());
			List<PaymentModeDesc> paymentModeDescList=paymentService.getRecordsFromDb(payment.getPaymentModeId());
			if(paymentModeDescList.size()>0){
				for (PaymentModeDesc paymentModeDesc : paymentModeDescList) {
					if (paymentModeDesc.getLanguageType().getLanguageId().intValue()==1) {
						datatable.setDtpymDescpken(paymentModeDesc.getPaymentModeDescId());
						datatable.setModeOfPaymentDesc(paymentModeDesc.getLocalPaymentName());
						datatable.setEngLanguageId(paymentModeDesc.getLanguageType().getLanguageId());
					}
					if (paymentModeDesc.getLanguageType().getLanguageId().intValue()==2) {
						datatable.setDtpymDescpkarb(paymentModeDesc.getPaymentModeDescId());
						datatable.setModeOfPaymentDescInLocal(paymentModeDesc.getLocalPaymentName());
						datatable.setEngLanguageId(paymentModeDesc.getLanguageType().getLanguageId());
					}
				}
			}else{
				continue;
			}
			/*datatable.setModeOfPaymentDesc(paymentService.getPaymentDisc(payment.getPaymentModeId()));
			datatable.setModeOfPaymentDescInLocal(paymentService.getArbPaymnetName(payment.getPaymentModeId()));*/
			listDataApproval.add(datatable);

		}
		
		
	 
		}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: approveList"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e)
		{
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
 
}
	}
	public void setListPayment(List<PaymentMode> listPayment) {
		this.listPayment = listPayment;
	}
	public List<PaymentModeDatatable> getListData2() {
		 
		return listData2;
	}
	public void setListData2(List<PaymentModeDatatable> listData2) {
		this.listData2 = listData2;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	public boolean isHideButton() {
		return hideButton;
	}
	public void setHideButton(boolean hideButton) {
		this.hideButton = hideButton;
	}
	public boolean isShwApprovbutton() {
		return shwApprovbutton;
	}
	public void setShwApprovbutton(boolean shwApprovbutton) {
		this.shwApprovbutton = shwApprovbutton;
	}
	private String Username;


	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	
	
	public void view(){
		try{		 
		Boolean childRecordCheck=false;
		List<PaymentMode> desList=paymentService.getAllRecordsFrmPaymentMode();
		listData2.clear();
		
		if(desList.size()>0){
			for (PaymentMode paymentMDDsc : desList) {
				PaymentModeDatatable datatable=new PaymentModeDatatable();
				datatable.setModeOfPayment(paymentMDDsc.getPaymentCode());
				datatable.setIsActive(paymentMDDsc.getIsActive());
				if(paymentMDDsc.getIsActive().equalsIgnoreCase(Constants.Yes)){
					datatable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					datatable.setApprovedBy(paymentMDDsc.getApprovBy());
					datatable.setApprovedDate(paymentMDDsc.getApprovDate());
					datatable.setRenderEditButton(true);
				}if(paymentMDDsc.getIsActive().equalsIgnoreCase(Constants.D)){
					datatable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					datatable.setRenderEditButton(true);
				}if(paymentMDDsc.getIsActive().equalsIgnoreCase(Constants.U)){
					if(paymentMDDsc.getModifiedBy()==null && paymentMDDsc.getModifiedDate()==null){
						datatable.setRenderEditButton(true);
						datatable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					}else{
						datatable.setRenderEditButton(true);
						datatable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}
					
				}
				if(paymentMDDsc.getPaymentModeId()!=null){
					datatable.setModifiedBy(paymentMDDsc. getModifiedBy());
					datatable.setModifiedDate( paymentMDDsc.getModifiedDate());
				}
				
				datatable.setCreatedBy(paymentMDDsc. getCreatedBy());
				datatable.setCreatedDate(paymentMDDsc. getCreatedDate());
				datatable.setDtpymPk(paymentMDDsc.getPaymentModeId());
				datatable.setRemarks( paymentMDDsc.getRemarks());
				
				List<PaymentModeDesc> payDescList =	paymentService.getAllRecordsBasedOnPaymentModeId( paymentMDDsc.getPaymentModeId());
				if(payDescList.size()>0){
					childRecordCheck=true;
					for(PaymentModeDesc sourceOfIncDesc:payDescList){
						if(Constants.ENGLISH_LANGUAGE_ID.equalsIgnoreCase(sourceOfIncDesc.getLanguageType().getLanguageId().toPlainString() )){
							datatable.setModeOfPaymentDesc(sourceOfIncDesc.getLocalPaymentName() );
							datatable.setEngLanguageId(sourceOfIncDesc.getLanguageType().getLanguageId() );
							datatable.setDtpymDescpken(sourceOfIncDesc.getPaymentModeDescId());
						}else{
							datatable.setDtpymDescpkarb( sourceOfIncDesc.getPaymentModeDescId());
							datatable.setAraLanguageId(sourceOfIncDesc.getLanguageType().getLanguageId() );
							datatable.setModeOfPaymentDescInLocal( sourceOfIncDesc.getLocalPaymentName());
						}
					}
				}else{
					childRecordCheck=false;
				}
			if(childRecordCheck){
				listData2.add(datatable);
			}
			}
		if (listData2.size()>0) {
			addRecordsToDataTable();
			listData2.clear();
		}
	}else{
		RequestContext.getCurrentInstance().execute("norecord.show();");
	}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: view"+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e)
		{
			setErrorMessage(e.getMessage());
			setErrorMessage( e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
 
}
	}
	
	

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}
	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	PaymentModeDatatable pymDT=new PaymentModeDatatable();
public void clickOnOKApproval() throws IOException{
	log.info( "Entered into clickOnOKApproval() method=======");
	if(getRemarks()!=null && !getRemarks().equals("")) {
	pymDT.setRemarks(getRemarks());
	delete(pymDT);
	listData.clear();
	view();
	listData.addAll(listData2);
		try {
	setRemarks(null);	
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}else{
		RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
	}
	}
public void checkStatusType(PaymentModeDatatable paymentModeDTObj)
		throws IOException {

	if (paymentModeDTObj.getDynamicLabelForActivateDeactivate()
			.equalsIgnoreCase(Constants.DEACTIVATE)) {
		//paymentModeDTObj.setIsActive("D");
		setIsActive(paymentModeDTObj.getIsActive());
		paymentModeDTObj.setRemarks(getRemarks());
		setApprovedBy(paymentModeDTObj.getApprovedBy() );
		setApprovedDate(paymentModeDTObj.getApprovedDate() );
		RequestContext.getCurrentInstance().execute("remarks.show();");
		pymDT=paymentModeDTObj;
	} else if(paymentModeDTObj.getDynamicLabelForActivateDeactivate()
			.equalsIgnoreCase(Constants.ACTIVATE)){
		removeRecord(paymentModeDTObj);
	}else if(paymentModeDTObj.getDynamicLabelForActivateDeactivate()
			.equalsIgnoreCase(Constants.DELETE)&&paymentModeDTObj.getModifiedBy()==null&&paymentModeDTObj.getModifiedDate()==null&&paymentModeDTObj.getApprovedBy()==null&&paymentModeDTObj.getRemarks()==null&&paymentModeDTObj.getApprovedDate()==null){
		paymentModeDTObj.setPermanetDeleteCheck(true);
		RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		return;
		
	}
	else if(paymentModeDTObj.getDynamicLabelForActivateDeactivate()
			.equalsIgnoreCase(Constants.REMOVE)){
		removeRecord(paymentModeDTObj);
	}else{
	
		RequestContext.getCurrentInstance().execute("couldnot.show();");
	}

}
public void removeRecordfromDB(PaymentModeDatatable paymentModeDTObj){
try{
 PaymentModeDesc paymentDesc=new PaymentModeDesc();
 paymentDesc.setPaymentModeDescId(paymentModeDTObj.getDtpymDescpken());
 paymentDesc.setLocalPaymentName(paymentModeDTObj.getModeOfPaymentDesc());
 paymentService.delete(paymentDesc);
 
 paymentDesc.setPaymentModeDescId(paymentModeDTObj.getDtpymDescpkarb());
 paymentDesc.setLocalPaymentName(paymentModeDTObj.getModeOfPaymentInLocal() );
 
 paymentService.delete(paymentDesc);
 
 PaymentMode paymentMode=new PaymentMode();

 paymentMode.setPaymentModeId(paymentModeDTObj.getDtpymPk());
 paymentMode.setPaymentCode(paymentModeDTObj.getModeOfPayment() );
 paymentMode.setIsActive( paymentModeDTObj.getIsActive());
 paymentMode.setCreatedBy(paymentModeDTObj.getCreatedBy());
 paymentMode.setCreatedDate(paymentModeDTObj.getCreatedDate() );
 
 paymentService.delete(paymentMode);
 listData.remove( paymentModeDTObj);
}  catch(NullPointerException  e){
	setErrorMessage("Method Name:removeRecordfromDB"+e.getMessage());
	RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
}
catch(Exception e)
{
	log.info("Problem Occured While Saving =============");		setErrorMessage(e.getMessage());
	setErrorMessage(e.getMessage());
	RequestContext.getCurrentInstance().execute("csp.show();");

}

}

public void remarkSelectedRecord() throws IOException {
	for (PaymentModeDatatable paymentModeDTObj : listData) {
		if (paymentModeDTObj.getRemarks() != null) {
			if (paymentModeDTObj.getRemarks().equals(true)) {
				paymentModeDTObj.setRemarks(getRemarks());
				removeRecord(paymentModeDTObj);
				setRemarks(null);
			}
		}
	}
}



public Boolean getSubmit() {
	return submit;
}

public void setSubmit(Boolean submit) {
	this.submit = submit;
}
public void hideSubmtButton() {

	if(getLocalServiceDescription()!=null || getPaymentDescEnglish()!=null){
		setSubmit(true);
		}else{
			setSubmit(false);
		}
}
private Object getLocalServiceDescription() {
	// TODO Auto-generated method stub
	return null;
}

public void clear(){
	setPaymentCode(null);
	setPaymentDescEnglish(null);
	setPaymentDescArabic(null);
	setPymDescpkarb(null);
	setPymDescpken(null);
	setPymPk(null);
	if(listData.size()>0){
		setSubmit(false);
	}
}
public void clickOnOk(){
	approveList();
	try{
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/PaymentModeApproval.xhtml");
	}
	catch(Exception e)
	{
		setErrorMessage(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");

	}
	
}
public void cancelRemarks(){
	setRemarks(null);
	try{
	FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/paymentMode.xhtml");
} catch (Exception e) {
	e.printStackTrace();
}
}



/*public void allDetailsToList() {

	if (listData.size() != 0) {
		for ( PaymentModeDatatable paymentDtList : listData) {
			if (paymentDtList.getModeOfPayment().equals(getPaymentCode())) {
				setPaymentCode( null);
				setPaymentDescEnglish(null);
				setPaymentDescArabic(null);
				RequestContext.getCurrentInstance().execute("succ.show();");
			} else {
				if (paymentDtList.getModeOfPaymentDesc().equals(
						getPaymentDescEnglish())) {
					
					setPaymentCode( null);
					setPaymentDescEnglish(null);
					setPaymentDescArabic(null);
					RequestContext.getCurrentInstance().execute(
							"succdesc.show();");
				}

			}
		}

	}
	if (getPymPk()!= null) {
		addRecordsToDataTable();
		listData2.clear();

	}

}*/

public void confirmPermanentDelete(){
	if(listData.size()>0){
		for (PaymentModeDatatable paymentModeDTObj : listData) {
			if(paymentModeDTObj.getActivateRecordCheck()!=null){
				if(paymentModeDTObj.getPermanetDeleteCheck().equals(true)){
					hardDelete(paymentModeDTObj);
					listData.remove(paymentModeDTObj);
				}
			}
			
		}
	
	}
}
public void confirmPermanentDeleteCancel(){
	  for (PaymentModeDatatable paymentModeDatatable : listData) {
		    if(paymentModeDatatable.getActivateRecordCheck()!=null){
				if(paymentModeDatatable.getPermanetDeleteCheck().equals(true)){
					paymentModeDatatable.setPermanetDeleteCheck(false);  
				}
		    }
	  }
}

public void hardDelete(PaymentModeDatatable paymentModeDTObj){
	paymentService.deleteRecordPermrntely(paymentModeDTObj.getDtpymPk(),paymentModeDTObj.getDtpymDescpken(),paymentModeDTObj.getDtpymDescpkarb());
}
public List<PaymentModeDatatable> getListDataApproval() {
	return listDataApproval;
}
public void setListDataApproval(List<PaymentModeDatatable> listDataApproval) {
	this.listDataApproval = listDataApproval;
}
public String getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(String errorMessage) {
	this.errorMessage = errorMessage;
}
}


