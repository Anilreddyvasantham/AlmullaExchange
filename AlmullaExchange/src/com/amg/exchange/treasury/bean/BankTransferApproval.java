package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IStandardInstructionsService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


@Component("bankTransferApproval")
@Scope("session")
public class BankTransferApproval<T> implements Serializable  {


	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(BankTransferApproval.class);
	private String companyListFromDB;
	private BigDecimal companyId;
	private String documentDescription;
	private int finaceYear=0;
	private BigDecimal documentSerialIdNumber;
	private String bnkInfoDate;
	private String bnkAttention;
	private String bnkDescription;
	private Date date3;
	private Date effectiveMinDate = new Date();
	private BigDecimal bnkTrFromBankId;
	private String bnkTrFromBankName;
	private String bnkTrFromCurrencyName;
	private BigDecimal bnkTrFromCurrencyId;
	private String bnkTrFromAccNo;
	private BigDecimal bnkTrFromFCAmount;
	private BigDecimal bnkTrFromExchangeRate;
	private BigDecimal bnkTrFromLocalAmount;
	private String bankTrFromInstrunction;
	private String bankTrFromInstructionDesc;
	private boolean purchaseCheckbox;
	private String bnkTrToBankName;
	private BigDecimal bnkTrToBankId;
	private String bnkTrToCurrencyName;
	private BigDecimal bnkTrToCurrencyId;
	private BigDecimal bnkTrToAccountNumber;
	private BigDecimal bnkTrToFCAmount;
	private BigDecimal bnkTrToExchangeRate;
	private BigDecimal bnkTrToLocalAmount;
	private BigDecimal bankTrToInstrunction;
	private String bankTrToInstructionDesc;
	private boolean salesCheckbox;
	public boolean tobankStandardInstruction=true;
	private BigDecimal documentNo;
	private BigDecimal financialYearId;
	private BigDecimal tresuryHdPK;
	private BigDecimal tresuryDtPK;
	private BigDecimal tresurySdPK;
	private String createdBy;
	
	private List<BankTransferBeanDataTable> bankTransferBeanDataTableLst=new ArrayList<BankTransferBeanDataTable>();
	private Map<BigDecimal,String> bankMap= new HashMap<BigDecimal,String>();
	private List<StandardInstructionDetails> checkboxstandardInstrnDetailsFROM = new ArrayList<StandardInstructionDetails>();
	private List<StandardInstructionDetails> checkboxstandardInstrnDetailsTO = new ArrayList<StandardInstructionDetails>();

	@Autowired
	IGeneralService<T> igeneralService;

	@Autowired
	IBankTransferService<T> bankTransferService;

	@Autowired
	IFundEstimationService<T> fundEstimationService;

	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;

	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	
	@Autowired
	IStandardInstructionsService<T> standardInstructionsService;

	private SessionStateManage sessionStateManage=new SessionStateManage();

	public String getCompanyListFromDB() {
		return companyListFromDB;
	}


	public void setCompanyListFromDB(String companyListFromDB) {
		this.companyListFromDB = companyListFromDB;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public String getDocumentDescription() {
		return documentDescription;
	}


	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}

	public int getFinaceYear() {
		return finaceYear;
	}


	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}


	public BigDecimal getDocumentSerialIdNumber() {
		return documentSerialIdNumber;
	}

	public void setDocumentSerialIdNumber(BigDecimal documentSerialIdNumber) {
		this.documentSerialIdNumber = documentSerialIdNumber;
	}

	public String getBnkInfoDate() {
		return bnkInfoDate;
	}

	public void setBnkInfoDate(String bnkInfoDate) {
		this.bnkInfoDate = bnkInfoDate;
	}


	public String getBnkAttention() {
		return bnkAttention;
	}

	public void setBnkAttention(String bnkAttention) {
		this.bnkAttention = bnkAttention;
	}

	public String getBnkDescription() {
		return bnkDescription;
	}

	public void setBnkDescription(String bnkDescription) {
		this.bnkDescription = bnkDescription;
	}

	public Date getDate3() {
		return date3;
	}

	public void setDate3(Date date3) {
		this.date3 = date3;
	}

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}

	public BigDecimal getBnkTrFromBankId() {
		return bnkTrFromBankId;
	}

	public void setBnkTrFromBankId(BigDecimal bnkTrFromBankId) {
		this.bnkTrFromBankId = bnkTrFromBankId;
	}

	public String getBnkTrFromBankName() {
		return bnkTrFromBankName;
	}

	public void setBnkTrFromBankName(String bnkTrFromBankName) {
		this.bnkTrFromBankName = bnkTrFromBankName;
	}

	public String getBnkTrFromCurrencyName() {
		return bnkTrFromCurrencyName;
	}

	public void setBnkTrFromCurrencyName(String bnkTrFromCurrencyName) {
		this.bnkTrFromCurrencyName = bnkTrFromCurrencyName;
	}

	public BigDecimal getBnkTrFromCurrencyId() {
		return bnkTrFromCurrencyId;
	}

	public void setBnkTrFromCurrencyId(BigDecimal bnkTrFromCurrencyId) {
		this.bnkTrFromCurrencyId = bnkTrFromCurrencyId;
	}

	public String getBnkTrFromAccNo() {
		return bnkTrFromAccNo;
	}

	public void setBnkTrFromAccNo(String bnkTrFromAccNo) {
		this.bnkTrFromAccNo = bnkTrFromAccNo;
	}

	public BigDecimal getBnkTrFromFCAmount() {
		return bnkTrFromFCAmount;
	}

	public void setBnkTrFromFCAmount(BigDecimal bnkTrFromFCAmount) {
		this.bnkTrFromFCAmount = bnkTrFromFCAmount;
	}

	public BigDecimal getBnkTrFromExchangeRate() {
		return bnkTrFromExchangeRate;
	}

	public void setBnkTrFromExchangeRate(BigDecimal bnkTrFromExchangeRate) {
		this.bnkTrFromExchangeRate = bnkTrFromExchangeRate;
	}

	public BigDecimal getBnkTrFromLocalAmount() {
		return bnkTrFromLocalAmount;
	}

	public void setBnkTrFromLocalAmount(BigDecimal bnkTrFromLocalAmount) {
		this.bnkTrFromLocalAmount = bnkTrFromLocalAmount;
	}

	public String getBankTrFromInstrunction() {
		return bankTrFromInstrunction;
	}

	public void setBankTrFromInstrunction(String bankTrFromInstrunction) {
		this.bankTrFromInstrunction = bankTrFromInstrunction;
	}

	public String getBankTrFromInstructionDesc() {
		return bankTrFromInstructionDesc;
	}

	public void setBankTrFromInstructionDesc(String bankTrFromInstructionDesc) {
		this.bankTrFromInstructionDesc = bankTrFromInstructionDesc;
	}

	public boolean isPurchaseCheckbox() {
		return purchaseCheckbox;
	}

	public void setPurchaseCheckbox(boolean purchaseCheckbox) {
		this.purchaseCheckbox = purchaseCheckbox;
	}

	public String getBnkTrToBankName() {
		return bnkTrToBankName;
	}

	public void setBnkTrToBankName(String bnkTrToBankName) {
		this.bnkTrToBankName = bnkTrToBankName;
	}

	public BigDecimal getBnkTrToBankId() {
		return bnkTrToBankId;
	}

	public void setBnkTrToBankId(BigDecimal bnkTrToBankId) {
		this.bnkTrToBankId = bnkTrToBankId;
	}

	public String getBnkTrToCurrencyName() {
		return bnkTrToCurrencyName;
	}

	public void setBnkTrToCurrencyName(String bnkTrToCurrencyName) {
		this.bnkTrToCurrencyName = bnkTrToCurrencyName;
	}

	public BigDecimal getBnkTrToCurrencyId() {
		return bnkTrToCurrencyId;
	}

	public void setBnkTrToCurrencyId(BigDecimal bnkTrToCurrencyId) {
		this.bnkTrToCurrencyId = bnkTrToCurrencyId;
	}

	public BigDecimal getBnkTrToAccountNumber() {
		return bnkTrToAccountNumber;
	}

	public void setBnkTrToAccountNumber(BigDecimal bnkTrToAccountNumber) {
		this.bnkTrToAccountNumber = bnkTrToAccountNumber;
	}

	public BigDecimal getBnkTrToFCAmount() {
		return bnkTrToFCAmount;
	}

	public void setBnkTrToFCAmount(BigDecimal bnkTrToFCAmount) {
		this.bnkTrToFCAmount = bnkTrToFCAmount;
	}

	public BigDecimal getBnkTrToExchangeRate() {
		return bnkTrToExchangeRate;
	}

	public void setBnkTrToExchangeRate(BigDecimal bnkTrToExchangeRate) {
		this.bnkTrToExchangeRate = bnkTrToExchangeRate;
	}

	public BigDecimal getBnkTrToLocalAmount() {
		return bnkTrToLocalAmount;
	}

	public void setBnkTrToLocalAmount(BigDecimal bnkTrToLocalAmount) {
		this.bnkTrToLocalAmount = bnkTrToLocalAmount;
	}

	public BigDecimal getBankTrToInstrunction() {
		return bankTrToInstrunction;
	}

	public void setBankTrToInstrunction(BigDecimal bankTrToInstrunction) {
		this.bankTrToInstrunction = bankTrToInstrunction;
	}

	public String getBankTrToInstructionDesc() {
		return bankTrToInstructionDesc;
	}

	public void setBankTrToInstructionDesc(String bankTrToInstructionDesc) {
		this.bankTrToInstructionDesc = bankTrToInstructionDesc;
	}

	public boolean isSalesCheckbox() {
		return salesCheckbox;
	}

	public void setSalesCheckbox(boolean salesCheckbox) {
		this.salesCheckbox = salesCheckbox;
	}

	public boolean isTobankStandardInstruction() {
		return tobankStandardInstruction;
	}

	public void setTobankStandardInstruction(boolean tobankStandardInstruction) {
		this.tobankStandardInstruction = tobankStandardInstruction;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}


	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}


	public List<BankTransferBeanDataTable> getBankTransferBeanDataTableLst() {
		return bankTransferBeanDataTableLst;
	}


	public void setBankTransferBeanDataTableLst(
			List<BankTransferBeanDataTable> bankTransferBeanDataTableLst) {
		this.bankTransferBeanDataTableLst = bankTransferBeanDataTableLst;
	}


	public BigDecimal getTresuryHdPK() {
		return tresuryHdPK;
	}


	public void setTresuryHdPK(BigDecimal tresuryHdPK) {
		this.tresuryHdPK = tresuryHdPK;
	}


	public BigDecimal getTresuryDtPK() {
		return tresuryDtPK;
	}


	public void setTresuryDtPK(BigDecimal tresuryDtPK) {
		this.tresuryDtPK = tresuryDtPK;
	}


	public BigDecimal getTresurySdPK() {
		return tresurySdPK;
	}


	public void setTresurySdPK(BigDecimal tresurySdPK) {
		this.tresurySdPK = tresurySdPK;
	}


	public String getCreatedBy() {
		return createdBy;
	}


	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}


	public List<StandardInstructionDetails> getCheckboxstandardInstrnDetailsFROM() {
		return checkboxstandardInstrnDetailsFROM;
	}


	public void setCheckboxstandardInstrnDetailsFROM(
			List<StandardInstructionDetails> checkboxstandardInstrnDetailsFROM) {
		this.checkboxstandardInstrnDetailsFROM = checkboxstandardInstrnDetailsFROM;
	}


	public void onDateSelect(SelectEvent event) {

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		//setBnkValueDate(format.format(event.getObject()));

	}

	public List<StandardInstructionDetails> getCheckboxstandardInstrnDetailsTO() {
		return checkboxstandardInstrnDetailsTO;
	}


	public void setCheckboxstandardInstrnDetailsTO(
			List<StandardInstructionDetails> checkboxstandardInstrnDetailsTO) {
		this.checkboxstandardInstrnDetailsTO = checkboxstandardInstrnDetailsTO;
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void callBnktrApproval()
	{
		try {
			
			setDocumentSerialIdNumber(null);
			getCompanyNameFromDB();
			getDocumentDescriptionFromDB();
			getFinaceYearFromDB();
			clearCache();
			getBankLst();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "BankTransferApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/BankTransferApproval.xhtml");
			
		} catch(Exception exception){
		  log.info("exception.getMessage ():::::::::::::::::::::::::::::::"+exception.getMessage());
		      setErrorMessage(exception.getMessage()); 
		      RequestContext.getCurrentInstance().execute("error.show();");
		      return;       
		      }

	}


	public void getCompanyNameFromDB() {
		  try{
		List<CompanyMasterDesc> data=igeneralService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		if(data.size()!=0)
		{
			setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
			setCompanyListFromDB(data.get(0).getCompanyName());
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}
	}
	public void getDocumentDescriptionFromDB() {
		  try{
		List<Document> documentDesc=igeneralService.getDocument(new BigDecimal(71),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for(Document des:documentDesc)
		{
			setDocumentNo(des.getDocumentID());
			setDocumentDescription(des.getDocumentDesc());
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}
	}

	public void getFinaceYearFromDB() {

		try{
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());

			if(financialYearList!=null && financialYearList.size()>0)
			{
				setFinaceYear(Integer.parseInt(financialYearList.get(0).getFinancialYear().toString()));
				setFinancialYearId(financialYearList.get(0).getFinancialYearID());
			}

		}catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}

	}

	public List<BankApplicability> getBankLst() {
		  List<BankApplicability> lstBankApplicability=null;
		  try{
			    lstBankApplicability=igeneralService.getCorrespondingLocalFundingBanks(sessionStateManage.getCountryId());
			    if(lstBankApplicability.size()>0){
		for(BankApplicability bankApplicability:lstBankApplicability){

			bankMap.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankFullName());

		}
			    }
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				}
		return lstBankApplicability;
	}

	public void populateAllFiledsValues()
	{
		  try{
			  clearExchange();
		if(getDocumentSerialIdNumber()!=null)
		{
			bankTransferBeanDataTableLst.clear();
			clearCache();
			TreasuryDealHeader	treasuryDealH=bankTransferService.getPopulateRecord(getDocumentSerialIdNumber(),getDocumentNo(), new BigDecimal(getFinaceYear()),sessionStateManage.getCompanyId());
			if(treasuryDealH!=null)
			{
				setTresuryHdPK(treasuryDealH.getTreasuryDealHeaderId());
				setCompanyId(treasuryDealH.getFsCompanyMaster().getCompanyId());
				setDocumentNo(treasuryDealH.getExDocument().getDocumentID());
				setFinaceYear(Integer.parseInt(treasuryDealH.getUserFinanceYear().toPlainString()));
				setBnkInfoDate(new SimpleDateFormat("dd/MM/yyyy").format(treasuryDealH.getDocumentDate()));
				setBnkAttention(treasuryDealH.getAttention());
				setBnkDescription(treasuryDealH.getRemarks());
				setDate3(treasuryDealH.getValueDate());
				setCreatedBy(treasuryDealH.getCreatedBy());
				List<TreasuryDealDetail> 	treasuryDeallsList=bankTransferService.getTreasuryDealDetailInfo(getDocumentSerialIdNumber(),getDocumentNo(), new BigDecimal(getFinaceYear()),sessionStateManage.getCompanyId());
				for(TreasuryDealDetail treasuryDealDetl:treasuryDeallsList){
					if(treasuryDealDetl.getLineType().equals(Constants.BF)){
						setTreasuryDetailBrFromPkId(treasuryDealDetl.getTreasuryDealDetailId());
						setBnkTrFromBankName(bankMap.get(treasuryDealDetl.getTreasuryDealBankMaster().getBankId()));
						setCurrencyId(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
						setBankId(treasuryDealDetl.getTreasuryDealBankMaster().getBankId());
						setFaAccountNo(treasuryDealDetl.getFaAccountNo());
						// List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(treasuryDealDetl.getTreasuryDealBankMaster().getBankId());
						setBnkTrFromCurrencyName(iSpecialCustomerDealRequestService.getCurrencyForUpdate(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId()));
						setBnkTrFromAccNo(iSpecialCustomerDealRequestService.getBankAccountNumberForUpdate(treasuryDealDetl.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId()));
						setBnkTrFromFCAmount(treasuryDealDetl.getFcAmount());
						setBnkTrFromExchangeRate(treasuryDealDetl.getLocalExchangeRate());
						setBnkTrFromLocalAmount(treasuryDealDetl.getLocalAmount());
						BigDecimal accountDetId =null;
						BigDecimal standInstId = null;
						String instructionType = null;
						List<TreasuryStandardInstruction> 	treasuryStandardInstrucList=bankTransferService.getTreasuryStandardInstructionInfo(getDocumentSerialIdNumber(),getDocumentNo(),treasuryDealDetl.getTreasuryDealDetailId(),sessionStateManage.getCompanyId());
						for(TreasuryStandardInstruction treasuryStdInstruction:treasuryStandardInstrucList){
							if(treasuryStdInstruction.getLineType().equals(Constants.BF)){	
								
							
						List<StandardInstruction>	standardInstr =standardInstructionsService.getStandInstrList(treasuryStdInstruction.getStandardInstructionNumber());
							if(standardInstr!=null && standardInstr.size()>0){	
								StandardInstruction standInstruct = standardInstr.get(0);
								setBankTrFromInstrunction(standInstruct.getIntructionType());
								setBankTrFromInstructionDesc(standInstruct.getInstructionDescription());
								accountDetId = standInstruct.getBankAccountDetailsId().getBankAcctDetId();
								standInstId = standInstruct.getStandardInstructionId();
								instructionType = standInstruct.getIntructionType();
							}
							
							}
						}

						getDataTableForBnkTrnsFrmDetails(treasuryDealDetl.getTreasuryDealBankMaster().getBankId(),treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId(),instructionType,accountDetId,standInstId);

					}

				//	if(treasuryDealDetl.getLineType().equals(Constants.PT)){
						if(treasuryDealDetl.getLineType().equals(Constants.BT)){
						BankTransferBeanDataTable bankTransferBeanDataTable=new BankTransferBeanDataTable();
						bankTransferBeanDataTable.setTresuryDtPK(treasuryDealDetl.getTreasuryDealDetailId());
						bankTransferBeanDataTable.setBankId(new BigDecimal((treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString()).toString()));
						bankTransferBeanDataTable.setBankName((iSpecialCustomerDealRequestService.getBankNameForUpdate(new BigDecimal(treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString())).toString()));
						bankTransferBeanDataTable.setAccountDtlId(treasuryDealDetl.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId());
						bankTransferBeanDataTable.setAccountNo((iSpecialCustomerDealRequestService.getBankAccountNumberForUpdate(treasuryDealDetl.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId())));
						bankTransferBeanDataTable.setFcAmount(treasuryDealDetl.getFcAmount());
						bankTransferBeanDataTable.setFcAmt(treasuryDealDetl.getFcAmount().toString());
						bankTransferBeanDataTable.setExChangeRate(treasuryDealDetl.getLocalExchangeRate().toString());
						bankTransferBeanDataTable.setLocalAmount(treasuryDealDetl.getLocalAmount());
						bankTransferBeanDataTable.setLocalAmt(treasuryDealDetl.getLocalAmount().toString());
						bankTransferBeanDataTable.setCreatedBy(treasuryDealDetl.getCreatedBy());
						bankTransferBeanDataTable.setCreatedDate(treasuryDealDetl.getCreatedDate());
						bankTransferBeanDataTable.setCurrencyId(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId());

						bankTransferBeanDataTable.setCurrencyId(new BigDecimal(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString()));
						List<TreasuryStandardInstruction> 	treasuryStandardInstrucLst=bankTransferService.getTreasuryStandardInstructionInfo(getDocumentSerialIdNumber(),getDocumentNo(),treasuryDealDetl.getTreasuryDealDetailId(),sessionStateManage.getCompanyId());
						for(TreasuryStandardInstruction treasuryStdInstion:treasuryStandardInstrucLst){									
							if(treasuryStdInstion.getLineType().equals("BT")){
								bankTransferBeanDataTable.setTresurySdPK(treasuryStdInstion.getTreasuryStandardInstructionId());		
								bankTransferBeanDataTable.setLineType(treasuryStdInstion.getLineType());
								

								List<StandardInstruction>	standardInstr =standardInstructionsService.getStandInstrList(treasuryStdInstion.getStandardInstructionNumber());
								if(standardInstr!=null && standardInstr.size()>0){	
									StandardInstruction standInstruct = standardInstr.get(0);
									bankTransferBeanDataTable.setBankTrToInstrunction(standInstruct.getIntructionType());
									bankTransferBeanDataTable.setBankTrToInstrunctionName(standInstruct.getIntructionType());
									bankTransferBeanDataTable.setInstDescription(standInstruct.getInstructionDescription());
									bankTransferBeanDataTable.setAccountDetId(standInstruct.getBankAccountDetailsId().getBankAcctDetId());
									bankTransferBeanDataTable.setStandardInsId(standInstruct.getStandardInstructionId());
								}
							}
						}
						bankTransferBeanDataTableLst.add(bankTransferBeanDataTable);
					}
				}
			}else{

				RequestContext.getCurrentInstance().execute("DocNoNotFound.show();");
				clearCache();
				setDocumentSerialIdNumber(null);
			}
		}
		  }catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }


	}

	public void getDataTableForBnkTrnsFrmDetails(BigDecimal bankId,BigDecimal currencyId,String instructionType,BigDecimal accountDetId,BigDecimal standInstId) {

		  try{
		if(bankId!=null && currencyId!=null && instructionType!=null && accountDetId!=null && standInstId!=null){

		//	List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,bnkTrfrmInstNo);

			List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,standInstId,accountDetId,instructionType);

			
			if(cinstrndetailsfromDB.size() != 0){
				setCheckboxstandardInstrnDetailsFROM(cinstrndetailsfromDB);

			}
			if(purchaseCheckbox){
				RequestContext.getCurrentInstance().execute("PF('puchasedetails').show();");
			}

		}
		  }catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }

	}

/*	public void getDataTableForBnkTrnsToDetails(BigDecimal bankId,BigDecimal currencyId,BigDecimal bnkTrfrmInstNo) {


		if(bankId!=null && currencyId!=null && bnkTrfrmInstNo!=null){

			List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,bnkTrfrmInstNo);

			if(cinstrndetailsfromDB.size() != 0){
				setCheckboxstandardInstrnDetailsFROM(cinstrndetailsfromDB);

			}
			if(purchaseCheckbox){
				RequestContext.getCurrentInstance().execute("PF('puchasedetails').show();");
			}

		}
	}*/

	public void showBnkTrFrmIns()
	{
		if(purchaseCheckbox){
			RequestContext.getCurrentInstance().execute("PF('puchasedetails').show();");
		}
	}

	public void showBnkTrToIns(BankTransferBeanDataTable bankTransferBeanDataTable)
	{
		  try{
		if(bankTransferBeanDataTable.getSelectedrecord()){

			if(bankTransferBeanDataTable.getBankId()!=null && bankTransferBeanDataTable.getCurrencyId()!=null && bankTransferBeanDataTable.getStandardInsId()!=null && bankTransferBeanDataTable.getAccountDetId()!=null && bankTransferBeanDataTable.getBankTrToInstrunction()!=null){

				List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(bankTransferBeanDataTable.getBankId(),bankTransferBeanDataTable.getCurrencyId(),Constants.Yes,bankTransferBeanDataTable.getStandardInsId(),bankTransferBeanDataTable.getAccountDetId(),bankTransferBeanDataTable.getBankTrToInstrunction());

				if(cinstrndetailsfromDB.size() != 0){
					setCheckboxstandardInstrnDetailsTO(cinstrndetailsfromDB);

				}
				RequestContext.getCurrentInstance().execute("PF('salesdetails').show();");

			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        		setErrorMessage(exception.getMessage()); 
        		RequestContext.getCurrentInstance().execute("error.show();");
        		return;       
        		}
	}
	
	private void clearCache()
	{
		  try{
		setBnkInfoDate(null);
		setBnkInfoDate(null);
		setBnkDescription(null);
		setBnkAttention(null);
		setDate3(null);
		setBnkTrFromBankName(null);
		setBnkTrFromCurrencyName(null);
		setBnkTrFromAccNo(null);
		setBnkTrFromFCAmount(null);
		setBnkTrFromExchangeRate(null);
		setBnkTrFromLocalAmount(null);
		setBankTrFromInstrunction(null);
		setBankTrFromInstructionDesc(null);
		setPurchaseCheckbox(false);
		bankTransferBeanDataTableLst.clear();
		lstofUnApproved.clear();
		List<TreasuryDealHeader> lstofUnApprovedTDlHeader = bankTransferService.fetchDocumentNumberFromTreasDealheaderForBankTransfer(Constants.U,new BigDecimal(getFinaceYear()),getDocumentNo(),sessionStateManage.getCompanyId());
		if(lstofUnApprovedTDlHeader.size()!=0){
			setLstofUnApproved(lstofUnApprovedTDlHeader);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
			}

	}
	
	public void exit() throws IOException {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	public void approveBnkTransfer()
	{
		try{
		if(getDocumentSerialIdNumber()!=null){
		if(!getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName()))
		{
			 boolean exchangeRateCheck=getExchangeRate();
			 if(!exchangeRateCheck)
			 {
				 RequestContext.getCurrentInstance().execute("exchangerateDiff.show();");
				 return;
			 }
			boolean appoveCheck=false;
			if(purchaseCheckbox)
			{
				for(BankTransferBeanDataTable bankTransferBeanDataTable :bankTransferBeanDataTableLst)
				{
					if(!bankTransferBeanDataTable.getSelectedrecord())
					{
						appoveCheck=true;
					}
				}
			}else
			{
				appoveCheck=true;
			}
			if(!appoveCheck)
			{
				approveRecord();
			}else
			{
				RequestContext.getCurrentInstance().execute("verifyInstruction.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}
		}else{
			RequestContext.getCurrentInstance().execute("documentMandatory.show();");
		}
		}catch(Exception exception){
		      log.info("exception.getMessage ():::::::::::::::::::::::::::::::"+exception.getMessage());
        	      setErrorMessage(exception.getMessage()); 
        	      RequestContext.getCurrentInstance().execute("error.show();");
        	      return;       
        	      }

		
	}
	
	private void approveRecord()
	{
		  try{
			 BigDecimal tresuryHdPK= getTresuryHdPK();
			 BigDecimal brTransFromTrDeatislID= getTreasuryDetailBrFromPkId();
			 List<BigDecimal> lstBrTOTrDetailsId= new ArrayList<BigDecimal>();
			 
			 for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
			 {
				 lstBrTOTrDetailsId.add(bnkTransBeanDataTable.getTresuryDtPK());
			 }
			 
		Map<String, Object> bnkTrsfrApproval = new HashMap<String, Object> ();
		bnkTrsfrApproval.put("TreasuryDealHeaderDocNo", getDocumentSerialIdNumber());
		bnkTrsfrApproval.put("TreasuryDealHeaderPK", tresuryHdPK);
		bnkTrsfrApproval.put("TreasuryDealBrFromDeatislID", brTransFromTrDeatislID);
		bnkTrsfrApproval.put("TreasuryDealBrToDeatislID", lstBrTOTrDetailsId);
		
		
		bankTransferService.bankTransferApprorval(bnkTrsfrApproval,sessionStateManage.getUserName());
		RequestContext.getCurrentInstance().execute("complete.show();");
		  }catch(NullPointerException ne){
		    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::saveDataTableRecods");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		    }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
	}
	
	public void callYes()throws Exception
	{
		approveRecord();
	}
	
	  private List<TreasuryDealHeader> lstofUnApproved = new ArrayList<TreasuryDealHeader>();

	  public List<TreasuryDealHeader> getLstofUnApproved() {
		    return lstofUnApproved;
	  }

	  public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		    this.lstofUnApproved = lstofUnApproved;
	  }

	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	  
	  
	  private boolean getExchangeRate()
	  {
		  boolean exchangeRateCheck=false;
		  BigDecimal currentExchangeRate=BigDecimal.ZERO;
		  if(getFaAccountNo()!=null &&getBankId()!=null && getCurrencyId()!=null){
			  if(getBnkTrFromAccNo()!=null){
				  List<AccountBalance>    accBalList= igeneralService.getExchangeRateFromAccBal(getBankId(), getCurrencyId(),getFaAccountNo());
				  if(accBalList.size()!=0){
					  currentExchangeRate=accBalList.get(0).getAverageRate();
				  }
			  }
		  }
		  
		  BigDecimal bfExchangeRate=getBnkTrFromExchangeRate();
		  if(currentExchangeRate.compareTo(BigDecimal.ZERO)!=0)
		  {
			  if(bfExchangeRate!=null)
			  {
				  if(currentExchangeRate.compareTo(bfExchangeRate)==0)
				  {
					  exchangeRateCheck=true;
				  }
			  }
		  }
		  return exchangeRateCheck;
	  }
	  
	  private BigDecimal bankId;
	  private BigDecimal currencyId;
	  private String faAccountNo;
	  private BigDecimal treasuryDetailBrFromPkId;

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


	  public String getFaAccountNo() {
		  return faAccountNo;
	  }


	  public void setFaAccountNo(String faAccountNo) {
		  this.faAccountNo = faAccountNo;
	  }

	  
	  public BigDecimal getTreasuryDetailBrFromPkId() {
		return treasuryDetailBrFromPkId;
	}


	public void setTreasuryDetailBrFromPkId(BigDecimal treasuryDetailBrFromPkId) {
		this.treasuryDetailBrFromPkId = treasuryDetailBrFromPkId;
	}


	private void clearExchange()
	  {
		  setBankId(null);
		  setCurrencyId(null);
		  setFaAccountNo(null);
		  setTreasuryDetailBrFromPkId(null);
		  setTresuryHdPK(null);
	  }
}
