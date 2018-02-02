package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountDetails;
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

@Component("standardInstructionApproval")
@Scope("session")
public class StandardInstructionApproval<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	SessionStateManage sessionManage = new SessionStateManage();

	private BigDecimal standardInstructionId = null;
	private BigDecimal standardInstructionDetailsId = null;
	private BigDecimal companyId = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private String instructionDescription;
	private String instructionType;
	private BigDecimal accountDetailsId;
	private String errorMsg;


	List<StandardInstruction> lstAllStndInst = new ArrayList<StandardInstruction>();

	List<StandardInstructionDetails> lstInstrnDesc = new ArrayList<StandardInstructionDetails>();

	List<CompanyMasterDesc> companyListFromDB = new ArrayList<CompanyMasterDesc>();

	List<CurrencyMaster> lstofcurrency = new ArrayList<CurrencyMaster>();

	List<BankMaster> lstofbank = new ArrayList<BankMaster>();
	
	private List<BankAccountDetails> lstOfcurrency = new ArrayList<BankAccountDetails>();
	
	

	/*HashMap<BigDecimal , String> lstcurrencyName = new HashMap<BigDecimal, String>();

	HashMap<BigDecimal , String> lstbankName = new HashMap<BigDecimal, String>();

	HashMap<BigDecimal , String> lstcompanyName = new HashMap<BigDecimal, String>();*/

	List<StandardInstructionApprovalDataTable> lstAllDupStndInstrn = new ArrayList<StandardInstructionApprovalDataTable>(); 

	CopyOnWriteArrayList<StandardInstructionApprovalDataTable> lstAllStndInstrn = new CopyOnWriteArrayList<StandardInstructionApprovalDataTable>();

	@Autowired
	IStandardInstructionsService<T> standardInstructionsService;

	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired 
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	ISpecialCustomerDealRequestService iSpecialCustomerDealRequestService;
	
	
	

	public String getInstructionType() {
		return instructionType;
	}

	public BigDecimal getAccountDetailsId() {
		return accountDetailsId;
	}

	public void setInstructionType(String instructionType) {
		this.instructionType = instructionType;
	}

	public void setAccountDetailsId(BigDecimal accountDetailsId) {
		this.accountDetailsId = accountDetailsId;
	}

	public List<BankAccountDetails> getLstOfcurrency() {
		return lstOfcurrency;
	}

	public void setLstOfcurrency(List<BankAccountDetails> lstOfcurrency) {
		this.lstOfcurrency = lstOfcurrency;
	}

	public List<StandardInstruction> getLstAllStndInst() {
		return lstAllStndInst;
	}

	public void setLstAllStndInst(List<StandardInstruction> lstAllStndInst) {
		this.lstAllStndInst = lstAllStndInst;
	}

	
	public void setCompanyListFromDB(List<CompanyMasterDesc> companyListFromDB) {
		this.companyListFromDB = companyListFromDB;
	}

	public List<CurrencyMaster> getLstofcurrency() {
		return lstofcurrency;
	}

	public void setLstofcurrency(List<CurrencyMaster> lstofcurrency) {
		this.lstofcurrency = lstofcurrency;
	}

	public List<BankMaster> getLstofbank() {
		return lstofbank;
	}

	public void setLstofbank(List<BankMaster> lstofbank) {
		this.lstofbank = lstofbank;
	}



	public CopyOnWriteArrayList<StandardInstructionApprovalDataTable> getLstAllStndInstrn() {
		return lstAllStndInstrn;
	}

	public void setLstAllStndInstrn(CopyOnWriteArrayList<StandardInstructionApprovalDataTable> lstAllStndInstrn) {
		this.lstAllStndInstrn = lstAllStndInstrn;
	}

	public List<StandardInstructionApprovalDataTable> getLstAllDupStndInstrn() {
		return lstAllDupStndInstrn;
	}

	public void setLstAllDupStndInstrn(List<StandardInstructionApprovalDataTable> lstAllDupStndInstrn) {
		this.lstAllDupStndInstrn = lstAllDupStndInstrn;
	}

	public List<StandardInstructionDetails> getLstInstrnDesc() {
		return lstInstrnDesc;
	}

	public void setLstInstrnDesc(List<StandardInstructionDetails> lstInstrnDesc) {
		this.lstInstrnDesc = lstInstrnDesc;
	}

	public BigDecimal getStandardInstructionId() {
		return standardInstructionId;
	}

	public void setStandardInstructionId(BigDecimal standardInstructionId) {
		this.standardInstructionId = standardInstructionId;
	}

	public BigDecimal getStandardInstructionDetailsId() {
		return standardInstructionDetailsId;
	}

	public void setStandardInstructionDetailsId(BigDecimal standardInstructionDetailsId) {
		this.standardInstructionDetailsId = standardInstructionDetailsId;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
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



	public String getInstructionDescription() {
		return instructionDescription;
	}

	public void setInstructionDescription(String instructionDescription) {
		this.instructionDescription = instructionDescription;
	}

	// go home action
	public void cancel() throws IOException {
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	//fetching all Standard Instruction to Approval - "U"
	public void fetchAllStndInstRecords(){
		try{
		lstAllDupStndInstrn.clear();
		lstAllStndInstrn.clear();
		int i=0;
		// fetch U Records From StandardInstruction
		lstAllStndInst = standardInstructionsService.getAllParentStandardInstruction();

		if(lstAllStndInst.size() != 0){

			for (StandardInstruction lstdetails : lstAllStndInst) {

				StandardInstructionApprovalDataTable lstAllStndInstrnData = new StandardInstructionApprovalDataTable();

				lstAllStndInstrnData.setStandardInstructionId(lstdetails.getStandardInstructionId());
				
				
				lstAllStndInstrnData.setFsCompanyMaster(lstdetails.getFsCompanyMaster().getCompanyId());
				
				lstAllStndInstrnData.setCompanyName(iSpecialCustomerDealRequestService.getCompanyNameForUpdate(lstdetails.getFsCompanyMaster().getCompanyId()));
				lstAllStndInstrnData.setExBankMaster(lstdetails.getExBankMaster().getBankId());
				
				lstAllStndInstrnData.setBankName(iSpecialCustomerDealRequestService.getBankNameForUpdate(lstdetails.getExBankMaster().getBankId()));
				lstAllStndInstrnData.setExCurrenyMaster(lstdetails.getExCurrenyMaster().getCurrencyId());
				
				lstAllStndInstrnData.setCurrencyName(iSpecialCustomerDealRequestService.getCurrencyForUpdate(lstdetails.getExCurrenyMaster().getCurrencyId()));
				
				if(lstdetails.getIntructionType()!=null){
					if(lstdetails.getIntructionType().equalsIgnoreCase(Constants.PD)){
						lstAllStndInstrnData.setInstructionType(Constants.PURCHASE);
					}else if(lstdetails.getIntructionType().equalsIgnoreCase(Constants.SD)){
						lstAllStndInstrnData.setInstructionType(Constants.SALE);
					}
				}
				
				lstAllStndInstrnData.setInstructionDescription(lstdetails.getInstructionDescription());
				lstAllStndInstrnData.setIsActive(lstdetails.getIsActive());
				lstAllStndInstrnData.setCreatedBy(lstdetails.getCreatedBy());
				lstAllStndInstrnData.setCreatedDate(lstdetails.getCreatedDate());
				lstAllStndInstrnData.setApproveBy(lstdetails.getApproveBy());
				lstAllStndInstrnData.setApproveDate(lstdetails.getApproveDate());
				lstAllStndInstrnData.setAccountDetailsId(lstdetails.getBankAccountDetailsId().getBankAcctDetId());
				
				String accountNumber = standardInstructionsService.getAccountNo(lstdetails.getBankAccountDetailsId().getBankAcctDetId());
				
				lstAllStndInstrnData.setAccountNumber(accountNumber);

				lstAllDupStndInstrn.add(lstAllStndInstrnData);

			}

		}

		if(lstAllDupStndInstrn.size() != 0){
			for (StandardInstructionApprovalDataTable lstdupRecord : lstAllDupStndInstrn) {
				if(lstAllStndInstrn.size() != 0){
					for (StandardInstructionApprovalDataTable lstReqRecord : lstAllStndInstrn) {
						if(lstdupRecord.getFsCompanyMaster().equals(lstReqRecord.getFsCompanyMaster())){
							if(lstdupRecord.getExBankMaster().equals(lstReqRecord.getExBankMaster())){
								if(lstdupRecord.getExCurrenyMaster().equals(lstReqRecord.getExCurrenyMaster())){
									if(lstdupRecord.getAccountDetailsId().equals(lstReqRecord.getAccountDetailsId())){
										if(lstdupRecord.getInstructionType().equals(lstReqRecord.getInstructionType())){
											i=1;
											break;
										}else{
											i=0;
										}
									}else{
										i=0;
									}
								}else{
									i=0;
								}
							}else{
								i=0;
							}
						}else{
							i=0;
						}
					}
					if(i==0){
						lstAllStndInstrn.add(lstdupRecord);	
					}
				}else{
					lstAllStndInstrn.add(lstdupRecord);	
				}
			}

		}
		}catch(NullPointerException  e){ 
				setErrorMsg("fetchAllStndInstRecords :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// navigation method
	public void showDetails() {
		fetchAllDetails();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "standardInstructionApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/standardInstructionApproval.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// fetching all details before 
	public void fetchAllDetails(){
		fetchAllStndInstRecords();
	}

	// fetching Instructions and Description from List to Dialogue
	public void fetchInstnDesc(StandardInstructionApprovalDataTable fetchInstr){
		try{
		int lineNo = 0;
		List<StandardInstructionDetails> lstInstrnDesc = new ArrayList<StandardInstructionDetails>();
		List<StandardInstructionDetails> standardInsDetList =  standardInstructionsService.getStandInstrDetailList(fetchInstr.getStandardInstructionId());
		if(standardInsDetList.size()>0){
			
				for (StandardInstructionDetails lstdialoginstrn : standardInsDetList) {
						lineNo = lineNo+1;
								StandardInstructionDetails lstdetails = new StandardInstructionDetails();
								lstdetails.setLineDescription(lstdialoginstrn.getLineDescription());
								lstdetails.setLineNumber(new BigDecimal(lineNo));
								if(lstdialoginstrn.getIsActive().equalsIgnoreCase(Constants.D)){
								lstdetails.setIntructionType("DELETED");
								}else if(lstdialoginstrn.getIsActive().equalsIgnoreCase(Constants.Yes)){
									lstdetails.setIntructionType("APPROVED");
									}else if(lstdialoginstrn.getIsActive().equalsIgnoreCase(Constants.U)){
										lstdetails.setIntructionType("UNAPPROVED");
									}
								lstInstrnDesc.add(lstdetails);				
				}
		
				if(lstInstrnDesc!=null && lstInstrnDesc.size() != 0){
					setLstInstrnDesc(lstInstrnDesc);
					RequestContext.getCurrentInstance().execute("datatabledetails.show();");
				}
		}else{
			RequestContext.getCurrentInstance().execute("noInstructions.show();");
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("fetchInstnDesc :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	// Approval Page Functions

	private List<StandardInstructionDataTableForDetails> displayData = new ArrayList<StandardInstructionDataTableForDetails>();

	private List<StandardInstruction> pstandardInstrn = new ArrayList<StandardInstruction>();

	private List<StandardInstruction> parentTableData = new ArrayList<StandardInstruction>();

	private Boolean booUpdateInstrnNum=true;
	private Boolean booSystemGenInstrnNum=false;
	private String localstandInstrCreatedBy;
	private Date localstandInstrCreatedDate;
	private String localstandInstrlnDesc;
	private Boolean disablecurrencyId=true;
	private Boolean disablecompanyId=true;
	private Boolean disablebankId=true;


	public List<StandardInstructionDataTableForDetails> getDisplayData() {
		return displayData;
	}

	public void setDisplayData(List<StandardInstructionDataTableForDetails> displayData) {
		this.displayData = displayData;
	}

	public List<StandardInstruction> getPstandardInstrn() {
		return pstandardInstrn;
	}

	public void setPstandardInstrn(List<StandardInstruction> pstandardInstrn) {
		this.pstandardInstrn = pstandardInstrn;
	}

	public Boolean getBooUpdateInstrnNum() {
		return booUpdateInstrnNum;
	}

	public void setBooUpdateInstrnNum(Boolean booUpdateInstrnNum) {
		this.booUpdateInstrnNum = booUpdateInstrnNum;
	}

	public Boolean getBooSystemGenInstrnNum() {
		return booSystemGenInstrnNum;
	}

	public void setBooSystemGenInstrnNum(Boolean booSystemGenInstrnNum) {
		this.booSystemGenInstrnNum = booSystemGenInstrnNum;
	}

	public List<StandardInstruction> getParentTableData() {
		return parentTableData;
	}

	public void setParentTableData(List<StandardInstruction> parentTableData) {
		this.parentTableData = parentTableData;
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

	public String getLocalstandInstrlnDesc() {
		return localstandInstrlnDesc;
	}

	public void setLocalstandInstrlnDesc(String localstandInstrlnDesc) {
		this.localstandInstrlnDesc = localstandInstrlnDesc;
	}

	public Boolean getDisablecurrencyId() {
		return disablecurrencyId;
	}

	public void setDisablecurrencyId(Boolean disablecurrencyId) {
		this.disablecurrencyId = disablecurrencyId;
	}

	public Boolean getDisablecompanyId() {
		return disablecompanyId;
	}

	public void setDisablecompanyId(Boolean disablecompanyId) {
		this.disablecompanyId = disablecompanyId;
	}

	public Boolean getDisablebankId() {
		return disablebankId;
	}

	public void setDisablebankId(Boolean disablebankId) {
		this.disablebankId = disablebankId;
	}
	
	private BigDecimal instructionPkForApproval;
	
	

	// redirecting to Approve page
	public BigDecimal getInstructionPkForApproval() {
		return instructionPkForApproval;
	}

	public void setInstructionPkForApproval(BigDecimal instructionPkForApproval) {
		this.instructionPkForApproval = instructionPkForApproval;
	}

	public void redirectToApprovePage(StandardInstructionApprovalDataTable selectedApproved){
		try{
		if(!selectedApproved.getCreatedBy().equalsIgnoreCase(sessionManage.getUserName())){

			try {
				fetchRecordsToApprove(selectedApproved);
			//	setInstructionNumber(selectedApproved.getStandardInsructionNumber());
				setInstructionType(selectedApproved.getInstructionType());
				setAccountDetailsId(selectedApproved.getAccountDetailsId());
				setInstructionDescription(selectedApproved.getInstructionDescription());
				setInstructionPkForApproval(selectedApproved.getStandardInstructionId());
				displayData.clear();
				List<StandardInstructionDetails> standInsDescList = standardInstructionsService.getStandardInstructionDesc(selectedApproved.getStandardInstructionId());
				for(StandardInstructionDetails standDesc:standInsDescList){
					StandardInstructionDataTableForDetails standInsDetail=new StandardInstructionDataTableForDetails();
					standInsDetail.setLineNumber(standDesc.getLineNumber());
					standInsDetail.setLineDescription(standDesc.getLineDescription());
					displayData.add(standInsDetail);
				}
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/standardInstrnApprovalPage.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("redirectToApprovePage :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void fetchRecordsToApprove(StandardInstructionApprovalDataTable selectedApproved) {
		fetchAllDetails();
		setCompanyId(selectedApproved.getFsCompanyMaster());
		setBankId(selectedApproved.getExBankMaster());
		setCurrencyId(selectedApproved.getExCurrenyMaster());
	}

/*	public void datatableclear(){
		displayData.clear();

		if(getCompanyId()==null || getBankId()==null || getCurrencyId()==null || getAccountDetailsId()==null || getInstructionType()==null){
			setInstructionDescription(null);
		}else{
			pstandardInstrn = standardInstructionsService.getValues(getCompanyId(),getBankId(),getCurrencyId(),getAccountDetailsId(),getInstructionType());
			pinstrnnumbersize = pstandardInstrn.size();
			if(pinstrnnumbersize==0){
				setBooSystemGenInstrnNum(false);
				setBooUpdateInstrnNum(true);
			}else if(pinstrnnumbersize==1){
				setBooSystemGenInstrnNum(false);
				setBooUpdateInstrnNum(true);
			//	setInstructionNumber(pstandardInstrn.get(0).getStandardInsructionNumber());
				setInstructionDescription(pstandardInstrn.get(0).getInstructionDescription());
				search();
			}else{
				setBooSystemGenInstrnNum(true);
				setBooUpdateInstrnNum(false);
				setPstandardInstrn(pstandardInstrn);
			}
		}
	}*/

	/*public void search() {

		parentTableData.clear();
		displayData.clear();

		List<StandardInstruction> ptabledata = standardInstructionsService.getParentStandardInstruction(getCompanyId(),getBankId(),getCurrencyId(),getInstructionType());

		List<StandardInstructionDetails> data = standardInstructionsService.getStandardInstruction(getCompanyId(),getBankId(),getCurrencyId(),Constants.U,BigDecimal.ONE);		

		if(ptabledata.size() != 0){
			setParentTableData(ptabledata);
			setStandardInstructionId(ptabledata.get(0).getStandardInstructionId());
			setLocalstandInstrCreatedBy(ptabledata.get(0).getCreatedBy());
			setLocalstandInstrCreatedDate(ptabledata.get(0).getCreatedDate());
			setLocalstandInstrlnDesc(ptabledata.get(0).getInstructionDescription());
		}

		if(data.size() != 0){

			for (StandardInstructionDetails dbvalues : data) {

				StandardInstructionDataTableForDetails lstdetails = new StandardInstructionDataTableForDetails();

				lstdetails.setStandardInstrnDetailsId(dbvalues.getStandardInstrnDetailsId());
				lstdetails.setExstandardInstructionId(dbvalues.getExstandardInstructionId().getStandardInstructionId());
				lstdetails.setExStandardInstructionForAllicationCountry(dbvalues.getExStandardInstructionForAllicationCountry().getCountryId());
				lstdetails.setFsCompanyMaster(dbvalues.getFsCompanyMaster().getCompanyId());
				lstdetails.setExBankMaster(dbvalues.getExBankMaster().getBankId());
				lstdetails.setExCurrenyMaster(dbvalues.getExCurrenyMaster().getCurrencyId());
				lstdetails.setLineNumber(dbvalues.getLineNumber());
				lstdetails.setLineDescription(dbvalues.getLineDescription());
				lstdetails.setIsActive(dbvalues.getIsActive());
				lstdetails.setCreatedBy(dbvalues.getCreatedBy());
				lstdetails.setCreatedDate(dbvalues.getCreatedDate());
				lstdetails.setModifiedBy(dbvalues.getModifiedBy());
				lstdetails.setModifiedDate(dbvalues.getModifiedDate());
				lstdetails.setStatusModify(false);

				displayData.add(lstdetails);

			}

		}else{

		}

	}*/

	public void approveRecord(StandardInstructionApprovalDataTable selectedApproved){
	try{
		Boolean checkApprove = standardInstructionsService.checkAlreadyApprove(selectedApproved.getStandardInstructionId());
			if(!(selectedApproved.getModifiedBy()==null ? selectedApproved.getCreatedBy() : selectedApproved.getModifiedBy()).equalsIgnoreCase(sessionManage.getUserName())){
				
				if(checkApprove){
					RequestContext.getCurrentInstance().execute("alreadyApproved.show();");
					lstAllStndInstrn.remove(selectedApproved);
				}else{
					standardInstructionsService.approveRecord(selectedApproved.getStandardInstructionId(), sessionManage.getUserName());
					lstAllStndInstrn.remove(selectedApproved);
					RequestContext.getCurrentInstance().execute("approve.show();");
				}
			}else if(checkApprove){
				RequestContext.getCurrentInstance().execute("alreadyApproved.show();");
				lstAllStndInstrn.remove(selectedApproved);
				}else{
					RequestContext.getCurrentInstance().execute("notApproved.show();");
				}
	}catch(NullPointerException  e){ 
		setErrorMsg("approveRecord :");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
	catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
		
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}


	
}
