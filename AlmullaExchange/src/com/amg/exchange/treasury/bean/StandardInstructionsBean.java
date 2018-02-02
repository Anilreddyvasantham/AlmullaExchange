package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IStandardInstructionsService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("standardInstructionsBean")
@Scope("session")
public class StandardInstructionsBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(StandardInstructionsBean.class);

	private BigDecimal companyId = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private BigDecimal standardInstructionId = null;
	private String localstandInstrCreatedBy;
	private Date localstandInstrCreatedDate;
	private String localstandInstrlnDesc;
	private String instructionType;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private String modifiedBy;
	private Date modifiedDate;
	private BigDecimal bankAccountDetailsId;
	
	private int parentTableSize;
	private BigDecimal id;
	private String remarks;
	private String activateBy;
	private Date activateDate;
	private String instructionDesc="";
	private BigDecimal standardIdForCheck;
	
	private String instructionDescForDisplay;
	private Boolean modiFicationCheckForSave=false;
	private String errorMsg;
	
	
	
	public Boolean getModiFicationCheckForSave() {
		return modiFicationCheckForSave;
	}

	public void setModiFicationCheckForSave(Boolean modiFicationCheckForSave) {
		this.modiFicationCheckForSave = modiFicationCheckForSave;
	}

	

	public BigDecimal getStandardIdForCheck() {
		return standardIdForCheck;
	}

	public void setStandardIdForCheck(BigDecimal standardIdForCheck) {
		this.standardIdForCheck = standardIdForCheck;
	}

	boolean save = true;
	boolean delete = false;



	
	
	private int nullCount;
	
	private Boolean booDataTableRender = false;
	
	private String instrndesc = null;
	private BigDecimal standardDeleteInstructionCheckId;
	
	private StandardInstructionDataTableForDetails standardInstructionDataTable;
	private StandardInstructionDataTableForDetails afterChangeStadardInstructionObj;
	
	
	
	
	
	public StandardInstructionDataTableForDetails getStandardInstructionDataTable() {
		return standardInstructionDataTable;
	}

	public void setStandardInstructionDataTable(
			StandardInstructionDataTableForDetails standardInstructionDataTable) {
		this.standardInstructionDataTable = standardInstructionDataTable;
	}

	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IStandardInstructionsService<T> standardInstructionsService;
	
	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	private List<BankAccountDetails> bankAccountDetailList;
	private List<BankApplicability> bankApplicablity;
	private List<CompanyMasterDesc> companyListFromDB;
	
	private List<StandardInstruction> parentTableData;
	private List<StandardInstructionDataTableForDetails> displayData = new CopyOnWriteArrayList<StandardInstructionDataTableForDetails>();
	private List<StandardInstructionDataTableForDetails> newDisplayData = new CopyOnWriteArrayList<StandardInstructionDataTableForDetails>();
	
	private List<StandardInstructionDataTableForDetails> displayDataforCheck = new CopyOnWriteArrayList<StandardInstructionDataTableForDetails>();
	
	private List<StandardInstruction> pstandardInstrn;
	
	private HashMap<String ,StandardInstruction> standardInstructionSave;
	private List<Integer> lineNumber = new CopyOnWriteArrayList<Integer>();
	
	SessionStateManage sessionManage = new SessionStateManage();
	
	private int pinstrnnumbersize;
	


	
	
	

	public String getRemarks() {
		return remarks;
	}

	public String getActivateBy() {
		return activateBy;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setActivateBy(String activateBy) {
		this.activateBy = activateBy;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public BigDecimal getStandardDeleteInstructionCheckId() {
		return standardDeleteInstructionCheckId;
	}

	public void setStandardDeleteInstructionCheckId(
			BigDecimal standardDeleteInstructionCheckId) {
		this.standardDeleteInstructionCheckId = standardDeleteInstructionCheckId;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}


	public List<StandardInstruction> getParentTableData() {
		return parentTableData;
	}

	public void setParentTableData(List<StandardInstruction> parentTableData) {
		this.parentTableData = parentTableData;
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
	
	private Boolean readOnlyInstrucDesc=false;
	 
	public Boolean getReadOnlyInstrucDesc() {
		return readOnlyInstrucDesc;
	}

	public void setReadOnlyInstrucDesc(Boolean readOnlyInstrucDesc) {
		this.readOnlyInstrucDesc = readOnlyInstrucDesc;
	}

	public void setCompanyListFromDB(List<CompanyMasterDesc> companyListFromDB) {
		this.companyListFromDB = companyListFromDB;
	}
	public List<CompanyMasterDesc> getCompanyListFromDB() {
		
		return companyListFromDB;
	}

	public BigDecimal getBankAccountDetailsId() {
		return bankAccountDetailsId;
	}
	public void setBankAccountDetailsId(BigDecimal bankAccountDetailsId) {
		this.bankAccountDetailsId = bankAccountDetailsId;
	}
	public String getInstructionType() {
		return instructionType;
	}

	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}

	public Boolean getBooDataTableRender() {
		return booDataTableRender;
	}

	public void setBooDataTableRender(Boolean booDataTableRender) {
		this.booDataTableRender = booDataTableRender;
	}


	public String getInstrndesc() {
		return instrndesc;
	}

	public void setInstrndesc(String instrndesc) {
		this.instrndesc = instrndesc;
	}

  

	public List<BankAccountDetails> getBankAccountDetailList() {
		return bankAccountDetailList;
	}

	public void setBankAccountDetailList(
			List<BankAccountDetails> bankAccountDetailList) {
		this.bankAccountDetailList = bankAccountDetailList;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getStandardInstructionId() {
		return standardInstructionId;
	}

	public void setStandardInstructionId(BigDecimal standardInstructionId) {
		this.standardInstructionId = standardInstructionId;
	}

	public int getParentTableSize() {
		return parentTableSize;
	}

	public void setParentTableSize(int parentTableSize) {
		this.parentTableSize = parentTableSize;
	}

	public List<StandardInstructionDataTableForDetails> getDisplayData() {
		return displayData;
	}

	public void setDisplayData(List<StandardInstructionDataTableForDetails> displayData) {
		this.displayData = displayData;
	}


	public String getLocalstandInstrlnDesc() {
		return localstandInstrlnDesc;
	}

	public void setLocalstandInstrlnDesc(String localstandInstrlnDesc) {
		this.localstandInstrlnDesc = localstandInstrlnDesc;
	}

	public String getLocalstandInstrCreatedBy() {
		return localstandInstrCreatedBy;
	}

	public void setLocalstandInstrCreatedBy(String localstandInstrCreatedBy) {
		this.localstandInstrCreatedBy = localstandInstrCreatedBy;
	}

	public Date getLocalstandInstrCreatedDate() {
		return localstandInstrCreatedDate;
	}

	public void setLocalstandInstrCreatedDate(Date localstandInstrCreatedDate) {
		this.localstandInstrCreatedDate = localstandInstrCreatedDate;
	}


	public List<BankApplicability> getBankApplicablity() {
		return bankApplicablity;
	}

	public void setBankApplicablity(List<BankApplicability> bankApplicablity) {
		this.bankApplicablity = bankApplicablity;
	}

	public List<CurrencyMaster> getCurrencyListFromDB() {
		return generalService.getCurrencyList();
	}
	
	private List<BankAccountDetails> lstofcurrency;
	
	public List<BankAccountDetails> getLstofcurrency() {
		return lstofcurrency;
	}

	public void setLstofcurrency(List<BankAccountDetails> lstofcurrency) {
		this.lstofcurrency = lstofcurrency;
	}

	
	
	/*public void standardInstrunctionDescription()
	{
		
		displayData.clear();
		
		if(getBankId()==null || getCurrencyId()==null || getCompanyId()==null){
			RequestContext.getCurrentInstance().execute("searching.show();");
			setInstructionRefNumber(null);
			setInstrndesc(null);
		}else{
		
		List<StandardInstruction> standrdInstrnDesc = standardInstructionsService.getParentStandardInstruction(getCompanyId(),getBankId(),getCurrencyId(),getInstructionRefNumber());
		
		if(standrdInstrnDesc.size() != 0){
			
			for (StandardInstruction standardInstruction : standrdInstrnDesc) {
				if(getInstructionRefNumber().equals(standardInstruction.getStandardInsructionNumber())){
					setInstrndesc(standardInstruction.getInstructionDescription());
				}
			} 	
			
			search();
		
		}else{
			setInstrndesc(null);
			setDisableAddButton(false);
			setBooDataTableRender(false);
			setBooRenderAccept(false);
			setParentTableSize(standrdInstrnDesc.size());
		}
		}
	}*/
	
	
	private BigDecimal countLineNumbers;

	public BigDecimal getCountLineNumbers() {
		return countLineNumbers;
	}

	public void setCountLineNumbers(BigDecimal countLineNumbers) {
		this.countLineNumbers = countLineNumbers;
	}
	
	
	
	
	public void  modifiInstructionDescrition(){
	    try{	
		if(!getInstructionDesc().trim().equalsIgnoreCase("")){
	    		   
	    		   for(StandardInstructionDataTableForDetails dbvalues: displayData){
	    			   if(dbvalues.getInstructionModiFiCheck().equals(true)){
	    				   if(!dbvalues.getLineDescription().trim().equalsIgnoreCase(getInstructionDesc().trim())){
	    				   dbvalues.setLineDescription(getInstructionDesc());
	    				   standardInstructionsService.updateRecord(dbvalues.getStandardInstrnDetailsId(),getInstructionDesc(),dbvalues.getLineNumber());
	    				   setInstructionDesc(null);
	    				   setStandardIdForCheck( null);
	    				   setInstructionDescForDisplay(null);
	    				   }else{
	    					   RequestContext.getCurrentInstance().execute("sameNotAllowed.show();");
	    					   setInstructionDesc(null);
		    				   setStandardIdForCheck( null);
		    				   setInstructionDescForDisplay(null);
		    				   return;
	    				   }
	    			   }
	    		   }
	    		   search();
	    		   RequestContext.getCurrentInstance().execute("editdialog.hide();");
	    	   }else{
	    		   RequestContext.getCurrentInstance().execute("descritionRequired.show();");
		       }
	    }catch(NullPointerException  e){ 
			 
				setErrorMsg("Method:modifiInstructionDescrition :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		
	}
	
public void search() {
		log.info( ":::::::::::::::::::::Search() Method called :::::::::::::::::::::::::::::");
		lineNumber.clear();
		setParentTableData(null);
		displayData.clear();
	
		setCountLineNumbers(null);
		try{		
		List<StandardInstruction> ptabledata = standardInstructionsService.getParentStandardInstruction(getCompanyId(),getBankId(),getCurrencyId(),getInstructionType(),getBankAccountDetailsId());		
		List<StandardInstructionDetails> data = standardInstructionsService.getStandardInstructionDetils(getCompanyId(),getBankId(),getCurrencyId(),getInstructionType(),getBankAccountDetailsId());
		if(ptabledata.size() != 0){
			setParentTableData(ptabledata);
			BigDecimal countStandInstrnDetails = standardInstructionsService.getAllCountofStndInstrnDetails(ptabledata.get(0).getStandardInstructionId());
			
			if(countStandInstrnDetails != null){
				setCountLineNumbers(countStandInstrnDetails);
			}
		}
		
		if(data.size() != 0){
			setBooDataTableRender(true);
			setReadOnlyInstrucDesc(true);
			int lineNo = 1;
			for (StandardInstructionDetails dbvalues : data) {
				
				StandardInstructionDataTableForDetails lstdetails = new StandardInstructionDataTableForDetails();
				lstdetails.setStandardInstrnDetailsId(dbvalues.getStandardInstrnDetailsId());
				lstdetails.setExstandardInstructionId(dbvalues.getExstandardInstructionId().getStandardInstructionId());
				lstdetails.setExStandardInstructionForAllicationCountry(dbvalues.getExStandardInstructionForAllicationCountry().getCountryId());
				lstdetails.setFsCompanyMaster(dbvalues.getFsCompanyMaster().getCompanyId());
				lstdetails.setExBankMaster(dbvalues.getExBankMaster().getBankId());
				lstdetails.setExCurrenyMaster(dbvalues.getExCurrenyMaster().getCurrencyId());
				lstdetails.setLineNumber(new BigDecimal(lineNo));
				lstdetails.setReadOnlyDescription(true);
				lstdetails.setEditRender(true);
				lstdetails.setInstructionModiFiCheck(false);
				lstdetails.setLineDescription(dbvalues.getLineDescription());
				if(dbvalues.getIsActive().equalsIgnoreCase(Constants.Yes)){
				 			
					lstdetails.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				 
				 }else if(dbvalues.getIsActive().equalsIgnoreCase(Constants.D)){
					 lstdetails.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				 }else if(dbvalues.getIsActive().equalsIgnoreCase(Constants.U)&& dbvalues.getModifiedBy()==null && dbvalues.getModifiedDate()==null
						&& dbvalues.getApproveBy()==null && dbvalues.getApproveDate()==null) {
					 lstdetails.setDynamicLabelForActivateDeactivate(Constants.DELETE);
				 }else{
					 lstdetails.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				 }
				lstdetails.setIsActive(dbvalues.getIsActive());
				lstdetails.setCreatedBy(dbvalues.getCreatedBy());
				lstdetails.setCreatedDate(dbvalues.getCreatedDate());
				lstdetails.setModifiedBy(dbvalues.getModifiedBy());
				lstdetails.setModifiedDate(dbvalues.getModifiedDate());
				lstdetails.setBankAccountDetailsId(dbvalues.getBankAccountDetailsId().getBankAcctDetId());
				lstdetails.setIntructionType(dbvalues.getIntructionType());
				lstdetails.setApproveBy(dbvalues.getApproveBy());
				lstdetails.setApproveDate(dbvalues.getApproveDate());
				lstdetails.setStatusModify(false);
				displayData.add(lstdetails);
				lineNumber.add(lineNo);
				lineNo = lineNo+1;
			}
				if(newDisplayData.size()>0){
				List<StandardInstructionDataTableForDetails> tempList = new ArrayList<StandardInstructionDataTableForDetails>();
				for(StandardInstructionDataTableForDetails lstdetails:newDisplayData){
					lstdetails.setLineNumber(new BigDecimal(lineNo));
					lineNumber.add(lineNo);
					tempList.add(lstdetails);
					lineNo = lineNo+1;
				}
				displayData.addAll(tempList); 
				}
			setDisplayDataforCheck( displayData);
			
		}else{
			
			setBooDataTableRender(false);
			setReadOnlyInstrucDesc(false);
		}
		int	parentTableSize = getParentTableData().size();
		setParentTableSize(parentTableSize);
	
		
		if(getParentTableSize() != 0){
			setStandardInstructionId(ptabledata.get(0).getStandardInstructionId());
			setLocalstandInstrCreatedBy(ptabledata.get(0).getCreatedBy());
			setLocalstandInstrCreatedDate(ptabledata.get(0).getCreatedDate());
			setLocalstandInstrlnDesc(ptabledata.get(0).getInstructionDescription());
			setIsActive(ptabledata.get(0).getIsActive());
			setApprovedBy(ptabledata.get(0).getApproveBy());
			setApprovedDate(ptabledata.get(0).getApproveDate());
			setModifiedBy(ptabledata.get(0).getModifiedBy());
			setModifiedDate(ptabledata.get(0).getModifiedDate());
		}
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("Method:search ");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}

public void cancelChange(){
	setStandardIdForCheck(null);
	setInstructionDesc( null);
}
	
	
	public void addEmptyRow() {
		boolean checkEmpty = false; 
		
		for (StandardInstructionDataTableForDetails element : displayData) {
			if(element.getLineDescription().trim().equals("")){
				checkEmpty = true;
			}
		} 

		if(checkEmpty){
			RequestContext.getCurrentInstance().execute("dlgNegNotAllowed.show();");
		}else{
			if(displayData.size()==0){
				lineNumber.clear();
				lineNumber.add(1);
			}else{
				int nextval = Collections.max(lineNumber);
				lineNumber.add(nextval+1);

			}
			StandardInstructionDataTableForDetails standardInstructionDetails= new StandardInstructionDataTableForDetails();  
		//	sizeincrement();
	        Integer nextVal  =	Collections.max(lineNumber);	
			standardInstructionDetails.setLineNumber(new BigDecimal(nextVal));
			standardInstructionDetails.setLineDescription("");
			standardInstructionDetails.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			standardInstructionDetails.setReadOnlyDescription(false);
			standardInstructionDetails.setStatusModify(true);
			standardInstructionDetails.setEditRender(false);
			displayData.add(standardInstructionDetails);
			newDisplayData.add(standardInstructionDetails);
		}
		
	}
	
	
	
	
	public StandardInstruction standardinstrn(){
		
		standardInstructionSave = new HashMap<String, StandardInstruction>();
		StandardInstruction standardInstruction=new StandardInstruction();
		standardInstruction.setStandardInstructionId(getStandardInstructionId());
		
		CountryMaster applicationCountryMaster = new CountryMaster();
		applicationCountryMaster.setCountryId(sessionManage.getCountryId());
		standardInstruction.setExStandardInstructionForAllicationCountry(applicationCountryMaster);

		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(getCompanyId());
		standardInstruction.setFsCompanyMaster(companyMaster);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(getCurrencyId());
		standardInstruction.setExCurrenyMaster(currencyMaster);

		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(getBankId());
		standardInstruction.setExBankMaster(bankMaster);
		
		BankAccountDetails bankAccDet = new BankAccountDetails();
		bankAccDet.setBankAcctDetId(getBankAccountDetailsId());
		standardInstruction.setBankAccountDetailsId(bankAccDet);
		
		
	//	standardInstruction.setStandardInsructionNumber(getInstructionRefNumber());
		standardInstruction.setIntructionType(getInstructionType());
		standardInstruction.setInstructionDescription(getInstrndesc());
		
		
		if(getModiFicationCheckForSave().equals(true) && getStandardInstructionId()!=null){
			standardInstruction.setCreatedBy(getLocalstandInstrCreatedBy());
			standardInstruction.setCreatedDate(getLocalstandInstrCreatedDate());
			standardInstruction.setIsActive(Constants.U);
			standardInstruction.setApproveBy(null);
			standardInstruction.setApproveDate(null);
			standardInstruction.setModifiedBy(sessionManage.getUserName());
			standardInstruction.setModifiedDate(new Date());
		}else if(getStandardInstructionId()==null){
			standardInstruction.setIsActive(Constants.U);
			standardInstruction.setCreatedBy(sessionManage.getUserName());
			standardInstruction.setCreatedDate(new Date());
		}else if(getStandardInstructionId()!=null){
				standardInstruction.setCreatedBy(getLocalstandInstrCreatedBy());
				standardInstruction.setCreatedDate(getLocalstandInstrCreatedDate());
				standardInstruction.setIsActive(getIsActive());
				standardInstruction.setApproveBy(getApprovedBy());
				standardInstruction.setApproveDate(getApprovedDate());
				standardInstruction.setModifiedBy(getModifiedBy());
				standardInstruction.setModifiedDate(getModifiedDate());
			}
			
			
		
			standardInstructionSave.put("StandardInstrutionObj", standardInstruction);
		
		//CR Partial Save Implemented
		//standardInstructionsService.savestandardInstruction(standardInstruction);
		
		return standardInstruction;
	}	
	
	public List<StandardInstructionDetails> standardinstrndetails(){
	
		List<StandardInstructionDetails> lstStndardInstrnDetails = new ArrayList<StandardInstructionDetails>();
 
		id = getCountLineNumbers()==null ? BigDecimal.ZERO : getCountLineNumbers();
		int lineNum = id.intValue();
		
		lstStndardInstrnDetails.clear();
		

		for (StandardInstructionDataTableForDetails element : displayData) {
			
			if(element.getStatusModify()){
				
				StandardInstructionDetails standardInstructionDetails = new StandardInstructionDetails();

				standardInstructionDetails.setStandardInstrnDetailsId(element.getStandardInstrnDetailsId());

				CountryMaster applicationCountryMaster = new CountryMaster();
				applicationCountryMaster.setCountryId(sessionManage.getCountryId());
				standardInstructionDetails.setExStandardInstructionForAllicationCountry(applicationCountryMaster);

				CompanyMaster companyMaster = new CompanyMaster();
				companyMaster.setCompanyId(getCompanyId());
				standardInstructionDetails.setFsCompanyMaster(companyMaster);

				CurrencyMaster currencyMaster = new CurrencyMaster();
				currencyMaster.setCurrencyId(getCurrencyId());
				standardInstructionDetails.setExCurrenyMaster(currencyMaster);
				
				BankAccountDetails bankAccDet = new BankAccountDetails();
				bankAccDet.setBankAcctDetId(getBankAccountDetailsId());
				standardInstructionDetails.setBankAccountDetailsId(bankAccDet);
				

				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(getBankId());
				standardInstructionDetails.setExBankMaster(bankMaster);

				standardInstructionDetails.setExstandardInstructionId(standardInstructionSave.get("StandardInstrutionObj"));
				
				standardInstructionDetails.setLineDescription(element.getLineDescription());
				
				 if (element.getStandardInstrnDetailsId() != null && element.getStandardInstrnDetailsId()!=null) {
					standardInstructionDetails.setLineNumber(element.getLineNumber());
					standardInstructionDetails.setCreatedBy(element.getCreatedBy());
					standardInstructionDetails.setCreatedDate(element.getCreatedDate());
					standardInstructionDetails.setModifiedBy(sessionManage.getUserName());
					standardInstructionDetails.setModifiedDate(new Date());
					standardInstructionDetails.setIsActive(Constants.U);

				} 
				
				if(element.getStandardInstrnDetailsId()== null ){
					lineNum = lineNum+1;
					standardInstructionDetails.setIsActive(Constants.U);
					standardInstructionDetails.setLineNumber(new BigDecimal(lineNum));
					standardInstructionDetails.setCreatedBy(sessionManage.getUserName());
					standardInstructionDetails.setCreatedDate(new Date());

				}
				
			
					

				standardInstructionDetails.setIntructionType(getInstructionType());
				
				lstStndardInstrnDetails.add(standardInstructionDetails);
				
				//CR Partial Save Implemented
				//standardInstructionsService.updatestandardInstructionDetails(standardInstructionDetails);
			}
		
		}
		
		
		return lstStndardInstrnDetails;
	
	}

	
	public void save() throws ParseException{

		nullCount=0;
		save = true;
		setModiFicationCheckForSave(false);

	/*	List<StandardInstruction> standrdInstrnChecking = standardInstructionsService.getParentStandardInstruction(getCompanyId(),getBankId(),getCurrencyId(),getInstructionType());

		if(standrdInstrnChecking.size() != 0){

			for (StandardInstruction standardInstruction : standrdInstrnChecking) {
				if(getInstructionType().equals(standardInstruction.getIntructionType())){
					instrnnum = false;
				}
			} 	

		}


		if(instrnnum){
			RequestContext.getCurrentInstance().execute("instrnNum.show();");
		}else */
		if(displayData.size()==0){ 
			RequestContext.getCurrentInstance().execute("dataNotFund.show();");
		}else{

			for (StandardInstructionDataTableForDetails element : displayData) {
				if(element.getLineDescription().trim().equals("")){
					nullCount=1;
				}
				
				if(element.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE) || element.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
					setModiFicationCheckForSave(true);
				}
			} 

			if(nullCount==1){
				save = false;
			} 

			if(save) {
				
				try{
					
					HashMap<String,Object> mapAllDetailForSave = new HashMap<String,Object>();
					
					// parent Table Storing 
					StandardInstruction saveStndInstrn = standardinstrn();

					mapAllDetailForSave.put("StandardInstruction", saveStndInstrn);

					// Child table Storing
					List<StandardInstructionDetails> saveStndInstrnDetails =  standardinstrndetails();

					mapAllDetailForSave.put("StandardInstructionDetails", saveStndInstrnDetails);
					
					standardInstructionsService.finalSaveOrUpdate(mapAllDetailForSave);

					setBooDataTableRender(false);
					RequestContext.getCurrentInstance().execute("complete.show();");
					


				}catch(Exception e){
					log.error("Exception Occured While Saving", e);
					setErrorMsg( e.getMessage());
					RequestContext.getCurrentInstance().execute("saveerror.show();");
				}
				

			}else {
				RequestContext.getCurrentInstance().execute("dlgNegNotAllowed.show();");
			}
		}
		
		setStandardDeleteInstructionCheckId(null);
	}
   
	
	
    public void removeStandardInstrn(StandardInstructionDataTableForDetails bean) {
    	setStandardDeleteInstructionCheckId(bean.getStandardInstrnDetailsId());
    	if(bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)){
    		RequestContext.getCurrentInstance().execute("pending.show();");
    		return;
    	}else if(bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)){
    		bean.setRemarkCheck(true);
    		setActivateDate(bean.getApproveDate());
    		setActivateBy(bean.getApproveBy());
    		RequestContext.getCurrentInstance().execute("remarks.show();");
    		return;
    		}else if(bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
    			bean.setBooCheckDelete(true);
    			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
    			bean.setStatusModify(true);
    			bean.setDeleteCheckForSave(true);
    			setStandardDeleteInstructionCheckId(bean.getStandardInstrnDetailsId());
    			return;
    		}else if(bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
    			bean.setActivateRecordCheck(true);
    			RequestContext.getCurrentInstance().execute("activateRecord.show();");
    			bean.setStatusModify(true);
    			bean.setDeleteCheckForSave(true);
    			setStandardDeleteInstructionCheckId(bean.getStandardInstrnDetailsId());
    			return;
    		}else if(bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)){
    			displayData.remove(bean);
    			newDisplayData.remove(bean);
    			
    			for(Integer inte:lineNumber){
    				if(inte.equals(bean.getLineNumber().intValue())){
    					lineNumber.remove(inte);
    				}
    			}
    			
    		}
    }
    
    
    public void activateRecord(){
    	if(displayData.size()>0){
    	for(StandardInstructionDataTableForDetails bean:displayData){
    		if(bean.getActivateRecordCheck()!=null){
    		if(bean.getActivateRecordCheck().equals(true)){
    			recordActivate(bean);
    			search();
    			return;
    		}
    		}
    	}
    	}
    }
    
    public void cancel(){
    	setRemarks("");
    	try {
    		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/standardinstructions.xhtml");
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    public void remarkSelectedRecord() throws IOException{
    	for(StandardInstructionDataTableForDetails bean:displayData){
    		if(bean.getRemarkCheck().equals(true)){
    			if(getRemarks()!=null && !getRemarks().equals("")){
    				bean.setRemarks(getRemarks());
    				bean.setActivateBy(null);
    				bean.setActivateDate(null);
    				deletePartially(bean);
	    			setRemarks("");
	    			search();
	    			cancel();
    		}else{
    			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
    			return;
    		}
    	}
    	}
    }
    
    public void confirmPermanentDelete(){
    	if(displayData.size()>0){
    		for (StandardInstructionDataTableForDetails bean : displayData) {
    			if(bean.getBooCheckDelete()!=null){
    			if(bean.getBooCheckDelete().equals(true)){
    			deleteRecordPermanently(bean);
    			search();
    			}
    		}
    	}
    	}
     }
    
    public void recordActivate(StandardInstructionDataTableForDetails bean){
    	
    	standardInstructionsService.activateRecord(bean.getStandardInstrnDetailsId(), sessionManage.getUserName(),bean.getLineNumber());
    }
    public void deleteRecordPermanently(StandardInstructionDataTableForDetails bean ){
    	standardInstructionsService.deleteRecordPermanently(bean.getStandardInstrnDetailsId(), sessionManage.getUserName());
    }
    public void deletePartially(StandardInstructionDataTableForDetails bean){
    	standardInstructionsService.deletePartially(bean.getStandardInstrnDetailsId(), sessionManage.getUserName(),bean.getRemarks(),bean.getLineNumber());
    }
    @Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
    public void showDetails() {
		try {
    	clearCache();
	    List<CompanyMasterDesc> companyList =	 iSpecialCustomerDealRequestService.getAllCompanyList(sessionManage.getCompanyId(),sessionManage.getLanguageId());
	     setCompanyListFromDB(companyList);
	     loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "standardinstructions.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/standardinstructions.xhtml");
			} catch(NullPointerException  e){ 
				 
					setErrorMsg("Method:showDetails ");
					RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
				}
			catch (Exception e) {
					setErrorMsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("csp.show();");
				}
	}
   
	public void clearCache(){
		setBankAccountDetailsId(null);
		setInstructionType(null);
		setCompanyId(null);
		setBankId(null);
		setCurrencyId(null);
		setInstrndesc(null);
		setStandardIdForCheck(null);
		setInstructionDescForDisplay(null);
		newDisplayData.clear();
		displayData.clear();
		lineNumber.clear();
		
		setBooDataTableRender(false);
		setStandardInstructionId(null);
		setLocalstandInstrCreatedBy(null);
		setLocalstandInstrCreatedDate(null);
		setLocalstandInstrlnDesc(null);
		setIsActive(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		
		setCountLineNumbers(null);
	
		setStandardDeleteInstructionCheckId(null);
		
	}
	
	public void datatableclear(){
		try{
		displayData.clear();
		pstandardInstrn = new ArrayList<StandardInstruction>();
		if(getCompanyId()!=null && getBankId()!=null && getCurrencyId()!=null && getBankAccountDetailsId()!=null && getInstructionType()!=null){
		    pstandardInstrn = standardInstructionsService.getValues(getCompanyId(),getBankId(),getCurrencyId(),getBankAccountDetailsId(),getInstructionType());
			pinstrnnumbersize = pstandardInstrn.size();
			if(pinstrnnumbersize==0){
				setReadOnlyInstrucDesc(false);
			}else if(pinstrnnumbersize==1){
				setReadOnlyInstrucDesc(true);
				setInstrndesc(pstandardInstrn.get(0).getInstructionDescription());
				search();
			}
	 }
		
		if(displayData!=null && displayData.size()>0){
			setBooDataTableRender(true);
		}else{
			setBooDataTableRender(false);
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("Method:datatableclear :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	 
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	


	public void exit() {
		clearCache();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void addDataTableForInstruction(){
		if(getInstrndesc().trim().equals("")){
			RequestContext.getCurrentInstance().execute("spaceNotAlloed.show();");
			return;
		}else{
			setBooDataTableRender(true);
		}
	}
	
	public void editDataTable(StandardInstructionDataTableForDetails objchange){
		log.info("=================editDataTable() method called =============");
 			setStandardIdForCheck(objchange.getStandardInstrnDetailsId());
 			setInstructionDescForDisplay( objchange.getLineDescription());
 			setInstructionDesc(null);
 			objchange.setInstructionModiFiCheck(true);
			RequestContext.getCurrentInstance().execute("editdialog.show();");
	}
	
	

	
	public void companyChange(){
		try{
		setBankApplicablity(null);
		setLstofcurrency(null);
		setBankAccountDetailList(null);
		setBankId(null);
		setCurrencyId(null);
		setBankAccountDetailsId(null);
		setInstructionType(null);
		setInstrndesc(null);
		List<BankApplicability> bankList = standardInstructionsService.getAllBankApplicablity();
		setBankApplicablity(bankList);
		newDisplayData.clear();
		displayData.clear();
		setBooDataTableRender(false);
 }catch(NullPointerException  e){ 
			setErrorMsg("Method:companyChange :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	 
		}
	catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void bankChange(){
		try{
		setLstofcurrency(null);
		setBankAccountDetailList(null);
		setCurrencyId(null);
		setBankAccountDetailsId(null);
		setInstructionType(null);
		setInstrndesc(null);
		List<BankAccountDetails> lstofcurrency = fundEstimationService.getCurrencyOfBank(getBankId());
		setLstofcurrency(lstofcurrency);
		newDisplayData.clear();
		displayData.clear();
		setBooDataTableRender(false);
		}catch(NullPointerException  e){ 
			setErrorMsg("Method:bankChange :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	 
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
		
	}
	
	public void currencyChange(){
		try{
		setBankAccountDetailList(null);
		setBankAccountDetailsId(null);
		setInstructionType(null);
		setInstrndesc(null);
		List<BankAccountDetails> bankAccountDetailList = standardInstructionsService.populateAccountNumber(getCompanyId(), getBankId(), getCurrencyId());
		setBankAccountDetailList(bankAccountDetailList);
		newDisplayData.clear();
		displayData.clear();
		setBooDataTableRender(false);
		}catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	
	public void accountNumberChange(){
		setInstructionType(null);
		setInstrndesc(null);
		newDisplayData.clear();
		displayData.clear();
		setBooDataTableRender(false);
	}
	
	public void instructionTypeChange(){
		setStandardInstructionId(null);
		setLocalstandInstrCreatedBy(null);
		setIsActive(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setLocalstandInstrCreatedDate(null);
		setLocalstandInstrlnDesc(null);
		newDisplayData.clear();
		displayData.clear();

		setCountLineNumbers(null);
		
		setInstrndesc(null);
		datatableclear();
	}

	public StandardInstructionDataTableForDetails getAfterChangeStadardInstructionObj() {
		return afterChangeStadardInstructionObj;
	}

	public void setAfterChangeStadardInstructionObj(
			StandardInstructionDataTableForDetails afterChangeStadardInstructionObj) {
		this.afterChangeStadardInstructionObj = afterChangeStadardInstructionObj;
	}

	public String getInstructionDesc() {
		return instructionDesc;
	}

	public void setInstructionDesc(String instructionDesc) {
		this.instructionDesc = instructionDesc;
	}

	public List<StandardInstructionDataTableForDetails> getDisplayDataforCheck() {
		return displayDataforCheck;
	}

	public void setDisplayDataforCheck(List<StandardInstructionDataTableForDetails> displayDataforCheck) {
		this.displayDataforCheck = displayDataforCheck;
	}

	public String getInstructionDescForDisplay() {
		return instructionDescForDisplay;
	}

	public void setInstructionDescForDisplay(String instructionDescForDisplay) {
		this.instructionDescForDisplay = instructionDescForDisplay;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}



	
}
