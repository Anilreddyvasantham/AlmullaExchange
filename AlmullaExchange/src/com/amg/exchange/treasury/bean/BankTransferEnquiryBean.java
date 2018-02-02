package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
//
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryDealHeaderDTO;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewTreasuryDeal;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IStandardInstructionsService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
//import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Component("bankTransferEnquiryBean")
@Scope("session")
public class BankTransferEnquiryBean<T> implements Serializable {

	 private static final long serialVersionUID = 1L;
	 private static final Logger log=Logger.getLogger(BankTransferEnquiryBean.class);
	 
	 public String getDocumentDescription() {
		return documentDescription;
	}
	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDocumentDate() {
		return documentDate;
	}
	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}
	
	
	 private String finacialYear;
	 private String attention;
	 private String documentDescription;
	 private String description;
	 private Date documentDate;
	 
	 private  Date valueDate;
	 private BigDecimal referenceNo;
	 private String frmBank;
	 private String frmAccount;
	 private String frmCurrency;
	 private BigDecimal frmfcAmount;
	 private BigDecimal frmlocalFcAmount;
	 private BigDecimal frmExchangeRate;
	 private String frmInstructionNumber;
	 private String frmInstructionDescription;
	 private String toInstructionNumber;
	 private String toInstructionDescription;
	 
	 private String toBank;
	 private String toAccount;
	 private String toCurrency;
	 private BigDecimal  TofcAmount;
	 private BigDecimal toLocalFcAmount;
	 private BigDecimal toExchangeRate;
	 

	    @Autowired
	    IBankTransferService ibankTransferService;

	    @Autowired
		IStandardInstructionsService<T> standardInstructionsService;
	    
	     List<TreasuryDealHeader>   enquiryList=new ArrayList<TreasuryDealHeader>();
	     private List<BankTransferEnquiryDataTable> bankTrnsferList=new ArrayList<BankTransferEnquiryDataTable>();
	 
	 
	 private String companyName;
	 public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFinacialYear() {
		return finacialYear;
	}
	public void setFinacialYear(String finacialYear) {
		this.finacialYear = finacialYear;
	}
	public String getAttention() {
		return attention;
	}
	public void setAttention(String attention) {
		this.attention = attention;
	}
	 
	 
	public BigDecimal getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(BigDecimal referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getFrmBank() {
		return frmBank;
	}
	public void setFrmBank(String frmBank) {
		this.frmBank = frmBank;
	}
	public String getFrmAccount() {
		return frmAccount;
	}
	public void setFrmAccount(String frmAccount) {
		this.frmAccount = frmAccount;
	}
	public String getFrmCurrency() {
		return frmCurrency;
	}
	public void setFrmCurrency(String frmCurrency) {
		this.frmCurrency = frmCurrency;
	}
	public BigDecimal getFrmfcAmount() {
		return frmfcAmount;
	}
	public void setFrmfcAmount(BigDecimal frmfcAmount) {
		this.frmfcAmount = frmfcAmount;
	}
	public BigDecimal getFrmlocalFcAmount() {
		return frmlocalFcAmount;
	}
	public void setFrmlocalFcAmount(BigDecimal frmlocalFcAmount) {
		this.frmlocalFcAmount = frmlocalFcAmount;
	}
	public BigDecimal getFrmExchangeRate() {
		return frmExchangeRate;
	}
	public void setFrmExchangeRate(BigDecimal frmExchangeRate) {
		this.frmExchangeRate = frmExchangeRate;
	}
	public String getToBank() {
		return toBank;
	}
	public void setToBank(String toBank) {
		this.toBank = toBank;
	}
	public String getToAccount() {
		return toAccount;
	}
	public void setToAccount(String toAccount) {
		this.toAccount = toAccount;
	}
	public String getToCurrency() {
		return toCurrency;
	}
	public void setToCurrency(String toCurrency) {
		this.toCurrency = toCurrency;
	}
	public BigDecimal getTofcAmount() {
		return TofcAmount;
	}
	public void setTofcAmount(BigDecimal tofcAmount) {
		TofcAmount = tofcAmount;
	}
	public BigDecimal getToLocalFcAmount() {
		return toLocalFcAmount;
	}
	public void setToLocalFcAmount(BigDecimal toLocalFcAmount) {
		this.toLocalFcAmount = toLocalFcAmount;
	}
	public BigDecimal getToExchangeRate() {
		return toExchangeRate;
	}
	public void setToExchangeRate(BigDecimal toExchangeRate) {
		this.toExchangeRate = toExchangeRate;
	}
	 
	  public List<TreasuryDealHeader> getEnquiryList() {
		return enquiryList;
	}
	public void setEnquiryList(List<TreasuryDealHeader> enquiryList) {
		this.enquiryList = enquiryList;
	}
	public Date getValueDate() {
		return valueDate;
	}
	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}
	public String getFrmInstructionNumber() {
		return frmInstructionNumber;
	}
	public void setFrmInstructionNumber(String frmInstructionNumber) {
		this.frmInstructionNumber = frmInstructionNumber;
	}
	public String getFrmInstructionDescription() {
		return frmInstructionDescription;
	}
	public void setFrmInstructionDescription(String frmInstructionDescription) {
		this.frmInstructionDescription = frmInstructionDescription;
	}
	public String getToInstructionNumber() {
		return toInstructionNumber;
	}
	public void setToInstructionNumber(String toInstructionNumber) {
		this.toInstructionNumber = toInstructionNumber;
	}
	public String getToInstructionDescription() {
		return toInstructionDescription;
	}
	public void setToInstructionDescription(String toInstructionDescription) {
		this.toInstructionDescription = toInstructionDescription;
	}
	 
	
  
  public List<BankTransferEnquiryDataTable> getBankTrnsferList() {
		return bankTrnsferList;
	}
	public void setBankTrnsferList(List<BankTransferEnquiryDataTable> bankTrnsferList) {
		this.bankTrnsferList = bankTrnsferList;
	}
	
	@Autowired
	 ISpecialCustomerDealRequestService<T>    iSpecialCustomerDealRequestService;
	
	private SessionStateManage sessionStateManage=new SessionStateManage();
	private List<BankTransferEnquiryDataTable> listForHead=new ArrayList<BankTransferEnquiryDataTable>();
	
    public List<BankTransferEnquiryDataTable> getListForHead() {
		return listForHead;
	}
	public void setListForHead(List<BankTransferEnquiryDataTable> listForHead) {
		this.listForHead = listForHead;
	}
public void bankTransferEnquiry(){
	  try{
	  bankTrnsferList.clear();
	  
	  List<TreasuryDealHeaderDTO> treasuryDealHeaderList = ibankTransferService.getBankTransferEnqSelectDate( new SimpleDateFormat("dd/MM/yy").parse(getAcyymm()));
		log.info("==============treasuryDealHeaderList============="+treasuryDealHeaderList.size());
	  //enquiryList= ibankTransferService.getAllRecordsFromHeader();
	  System.out.println("==============enquiry============="+enquiryList.size());
	  for(TreasuryDealHeaderDTO treasuryHeadObj:treasuryDealHeaderList){
	  BankTransferEnquiryDataTable bankTransferDTObj=new BankTransferEnquiryDataTable();
	  bankTransferDTObj.setCompany("ALMULLA EXCHANGE SERVICE");
	  bankTransferDTObj.setCreatedBy( treasuryHeadObj.getCreatedBy());
	  bankTransferDTObj.setCreatedDate(treasuryHeadObj.getCreatedDate());
	  bankTransferDTObj.setApprovedBy(treasuryHeadObj.getApprovedBy());
	  if(treasuryHeadObj.getIsActive().equalsIgnoreCase(Constants.U)){
		  bankTransferDTObj.setIsActive("UN_APPROVED");
	  }else if( treasuryHeadObj.getIsActive().equalsIgnoreCase(Constants.Y) ){
		  bankTransferDTObj.setIsActive("APPROVED"); 
	  }else if( treasuryHeadObj.getIsActive().equalsIgnoreCase(Constants.D) ){
		  
		  bankTransferDTObj.setIsActive("DELETED"); 
	  }
	  bankTransferDTObj.setDocumentDate(treasuryHeadObj.getDocumentDate());
	  bankTransferDTObj.setValueDate(treasuryHeadObj.getValueDate());
	  bankTransferDTObj.setAttention(treasuryHeadObj.getAttention());
	  bankTransferDTObj.setBankTransferYear(treasuryHeadObj.getDocumentFinanceYear().toPlainString());
	  bankTransferDTObj.setBankTransferReference(treasuryHeadObj.getTreasuryDocumentNumber().toPlainString());
 
	  bankTransferDTObj.setDocumentDecsrption(treasuryHeadObj.getDocumentDesc());
	  bankTransferDTObj.setDescription(treasuryHeadObj.getRemarks());
	  List<TreasuryDealDetail> treasuryDetailsList=ibankTransferService.getAllRecordsOfBankTransfer(treasuryHeadObj.getTreasuryDealHeaderId()) ;
	   if(treasuryDetailsList.size()>0){
		   
		   List<BankTransferEnquiryDataTable> bankTrnsferListTo=new ArrayList<BankTransferEnquiryDataTable>();
		   List<BankTransferEnquiryDataTable> bankTrnsferListFrom=new ArrayList<BankTransferEnquiryDataTable>();
		   
		for(TreasuryDealDetail treasuryDetailObj:treasuryDetailsList){
			
			 if(treasuryDetailObj.getLineType().equalsIgnoreCase(Constants.BT)){
				  
				  
				  
				  BankTransferEnquiryDataTable bankTransferDTObjTo=new BankTransferEnquiryDataTable();
				  bankTransferDTObjTo.setToPk(treasuryDetailObj.getTreasuryDealDetailId());
				  bankTransferDTObjTo.setToBank(treasuryDetailObj.getTreasuryDealBankMaster().getBankFullName() );
				  bankTransferDTObjTo.setToBankId(treasuryDetailObj.getTreasuryDealBankMaster().getBankId());
				  bankTransferDTObjTo.setToCurrency(treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyName());
				  bankTransferDTObjTo.setToCurrencyId(treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
				  bankTransferDTObjTo.setToAccount(treasuryDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo() );
				  //bankTransferDTObjTo.setToAccount((iSpecialCustomerDealRequestService.getBankAccountNumberForUpdate(treasuryDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId())) );
				  bankTransferDTObjTo.setTofcAmount(treasuryDetailObj.getFcAmount());
				  bankTransferDTObjTo.setToLocalFcAmount( treasuryDetailObj.getLocalAmount());
				  bankTransferDTObjTo.setToExchangeRate(treasuryDetailObj.getLocalExchangeRate());
				  
				  bankTrnsferListTo.add(bankTransferDTObjTo);
				 
				  
				/*  bankTransferDTObj.setFrmBank(treasuryDetailObj.getTreasuryDealBankMaster().getBankFullName() );
				  bankTransferDTObj.setFrmCurrency( treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyName());
				  bankTransferDTObj.setFrmAccount(treasuryDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo() );
				  bankTransferDTObj.setFrmfcAmount(treasuryDetailObj.getFcAmount());
			      bankTransferDTObj.setFrmlocalFcAmount( treasuryDetailObj.getLocalAmount());
			      bankTransferDTObj.setFrmExchangeRate(treasuryDetailObj.getExchange()); 
			     List<TreasuryStandardInstruction> instructionList=ibankTransferService.getTreasuryStandardInstruction(treasuryDetailObj.getDocumentNumber(), treasuryDetailObj.getTreasuryDealDetailId());
			     for(TreasuryStandardInstruction treasuryInstructionDetails:instructionList){
			    	 bankTransferDTObj.setFrmInstructionNumber(treasuryInstructionDetails.getStandardInstructionNumber().toPlainString());
			    	 bankTransferDTObj.setFrmInstructionDescription(treasuryInstructionDetails.getMessageDescription());
			     }*/
			   
			  }
			  if(treasuryDetailObj.getLineType().equalsIgnoreCase(Constants.BF)){
				  
				  
				
				  
				  BankTransferEnquiryDataTable bankTransferDTObjFrom=new BankTransferEnquiryDataTable();
				  bankTransferDTObjFrom.setFrmPk(treasuryDetailObj.getTreasuryDealDetailId());
				  bankTransferDTObjFrom.setFrmBank(treasuryDetailObj.getTreasuryDealBankMaster().getBankFullName() );
				  bankTransferDTObjFrom.setFromBankId(treasuryDetailObj.getTreasuryDealBankMaster().getBankId());
				  bankTransferDTObjFrom.setFrmCurrency(treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyName() );
				  bankTransferDTObjFrom.setFromCurrencyId(treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyId());
				  bankTransferDTObjFrom.setFrmAccount(treasuryDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo() );
				  bankTransferDTObjFrom.setFrmfcAmount(treasuryDetailObj.getFcAmount());
				  bankTransferDTObjFrom.setFrmlocalFcAmount( treasuryDetailObj.getLocalAmount());
				  bankTransferDTObjFrom.setFrmExchangeRate(treasuryDetailObj.getLocalExchangeRate());
				  
				  
				  bankTrnsferListFrom.add(bankTransferDTObjFrom);
				  
				  /*bankTransferDTObj.setToBank(treasuryDetailObj.getTreasuryDealBankMaster().getBankFullName());
			 
				  bankTransferDTObj.setToCurrency( treasuryDetailObj.getTreasuryDealDetailCurrencyMaster().getCurrencyName());
				  bankTransferDTObj.setToAccount(treasuryDetailObj.getTreasuryDealDetailBankAccountDetails().getBankAcctNo() );
			 
			      bankTransferDTObj.setTofcAmount(treasuryDetailObj.getFcAmount());
			      bankTransferDTObj.setToLocalFcAmount(treasuryDetailObj.getLocalAmount());
			      bankTransferDTObj.setToExchangeRate(treasuryDetailObj.getExchange()); 
			  
			     List<TreasuryStandardInstruction> instructionList=ibankTransferService.getTreasuryStandardInstruction(treasuryDetailObj.getDocumentNumber(), treasuryDetailObj.getTreasuryDealDetailId());
			     for(TreasuryStandardInstruction treasuryInstructionDetails:instructionList){
			    	 bankTransferDTObj.setToInstructionNumber(treasuryInstructionDetails.getStandardInstructionNumber().toPlainString());
			    	 bankTransferDTObj.setToInstructionDescription(treasuryInstructionDetails.getMessageDescription());
			     }
			  */
			  }
			  
			 
			
		}
		 bankTransferDTObj.setToList(bankTrnsferListTo);
		 bankTransferDTObj.setFromList(bankTrnsferListFrom);
		 bankTrnsferList.add(bankTransferDTObj);   
		   
		   
		 
	  
	  
	  
	   }
	  
	 
	  
	  
	   
	  
	 
	  }
	  }catch(NullPointerException ne){
	    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	    setErrorMessage("MethodName::bankTransferEnquiry");
	    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    return; 
	  }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	  }
	  
  }

//Ram Start

private List<BankTransferBeanDataTable> bankTransferBeanDataTableLst=new ArrayList<BankTransferBeanDataTable>();
private Map<BigDecimal,String> bankMap= new HashMap<BigDecimal,String>();
private List<StandardInstructionDetails> checkboxstandardInstrnDetailsFROM = new ArrayList<StandardInstructionDetails>();
private List<StandardInstructionDetails> checkboxstandardInstrnDetailsTO = new ArrayList<StandardInstructionDetails>();
private BigDecimal documentNo;
private String bankTrFromInstrunction;
private String bankTrFromInstructionDesc;
private boolean purchaseCheckbox;


public boolean isPurchaseCheckbox() {
	return purchaseCheckbox;
}
public void setPurchaseCheckbox(boolean purchaseCheckbox) {
	this.purchaseCheckbox = purchaseCheckbox;
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
public BigDecimal getDocumentNo() {
	return documentNo;
}
public void setDocumentNo(BigDecimal documentNo) {
	this.documentNo = documentNo;
}
public List<BankTransferBeanDataTable> getBankTransferBeanDataTableLst() {
	return bankTransferBeanDataTableLst;
}
public void setBankTransferBeanDataTableLst(
		List<BankTransferBeanDataTable> bankTransferBeanDataTableLst) {
	this.bankTransferBeanDataTableLst = bankTransferBeanDataTableLst;
}
public Map<BigDecimal, String> getBankMap() {
	return bankMap;
}
public void setBankMap(Map<BigDecimal, String> bankMap) {
	this.bankMap = bankMap;
}
public List<StandardInstructionDetails> getCheckboxstandardInstrnDetailsFROM() {
	return checkboxstandardInstrnDetailsFROM;
}
public void setCheckboxstandardInstrnDetailsFROM(
		List<StandardInstructionDetails> checkboxstandardInstrnDetailsFROM) {
	this.checkboxstandardInstrnDetailsFROM = checkboxstandardInstrnDetailsFROM;
}
public List<StandardInstructionDetails> getCheckboxstandardInstrnDetailsTO() {
	return checkboxstandardInstrnDetailsTO;
}
public void setCheckboxstandardInstrnDetailsTO(
		List<StandardInstructionDetails> checkboxstandardInstrnDetailsTO) {
	this.checkboxstandardInstrnDetailsTO = checkboxstandardInstrnDetailsTO;
}

@Autowired
IBankTransferService<T> bankTransferService;

@Autowired
IGeneralService<T> igeneralService;

@Autowired
IFundEstimationService<T> fundEstimationService;


@Autowired
IFXDetailInformationService<T> fXDetailInformationService;

public void getDocumentDescriptionFromDB() {
	  try{
	List<Document> documentDesc=igeneralService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_BANK_TRANSFER),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
	for(Document des:documentDesc)
	{
		setDocumentNo(des.getDocumentID());
		setDocumentDescription(des.getDocumentDesc());
	}
	  }catch(NullPointerException ne){
	    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	    setErrorMessage("MethodName::getDocumentDescriptionFromDB");
	    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    return; 
	    }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	    }

}

public void gotoBankTransferEnquiry(BankTransferEnquiryDataTable bankTransferDTObj){
	  try{
	getDocumentDescriptionFromDB();
	bankTransferBeanDataTableLst.clear();
	if(bankTransferDTObj.getBankTransferReference()!=null)
	{

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransferviewenquiry.xhtml");
		}catch(Exception e){
			e.printStackTrace();
		}
		clearAll();
		setCompanyName(bankTransferDTObj.getCompany());
		setFinacialYear(bankTransferDTObj.getBankTransferYear());
		setReferenceNo( new BigDecimal(bankTransferDTObj.getBankTransferReference()));
		setAttention(bankTransferDTObj.getAttention() );
		setDocumentDate(bankTransferDTObj.getDocumentDate() );
		setDocumentDescription(bankTransferDTObj.getDocumentDecsrption());
		setDescription(bankTransferDTObj.getDescription());
		setValueDate( bankTransferDTObj.getValueDate());

		List<BankTransferEnquiryDataTable> bankTrnsferListFrom=bankTransferDTObj.getFromList();
		if(bankTrnsferListFrom.size()>0)
		{
			BankTransferEnquiryDataTable BankTransferEnquiryDataTableFrom =bankTrnsferListFrom.get(0);
			setFrmBank(BankTransferEnquiryDataTableFrom.getFrmBank() );
			setFrmCurrency(BankTransferEnquiryDataTableFrom.getFrmCurrency());
			if(BankTransferEnquiryDataTableFrom.getFrmlocalFcAmount()!=null){
				setFrmAccount(BankTransferEnquiryDataTableFrom.getFrmAccount());
			}
			setFrmExchangeRate( BankTransferEnquiryDataTableFrom.getFrmExchangeRate());
			setFrmfcAmount(BankTransferEnquiryDataTableFrom.getFrmfcAmount());
			setFrmlocalFcAmount(BankTransferEnquiryDataTableFrom.getFrmlocalFcAmount());

			BigDecimal accountDetId =null;
			BigDecimal standInstId = null;
			String instructionType = null;
			List<TreasuryStandardInstruction> 	treasuryStandardInstrucList=bankTransferService.getTreasuryStandardInstructionInfo(new BigDecimal(bankTransferDTObj.getBankTransferReference()),getDocumentNo(),BankTransferEnquiryDataTableFrom.getFrmPk(),sessionStateManage.getCompanyId());
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
			getDataTableForBnkTrnsFrmDetails(BankTransferEnquiryDataTableFrom.getFromBankId(),BankTransferEnquiryDataTableFrom.getFromCurrencyId(),instructionType,accountDetId,standInstId);
		}

		List<BankTransferEnquiryDataTable> bankTrnsferListTo=bankTransferDTObj.getToList();
		if(bankTrnsferListTo.size()>0)
		{
			for(BankTransferEnquiryDataTable bankTransferEnquiryDataTable :bankTrnsferListTo)
			{
				BankTransferBeanDataTable bankTransferBeanDataTable=new BankTransferBeanDataTable();
				bankTransferBeanDataTable.setTresuryDtPK(bankTransferEnquiryDataTable.getToPk());
				bankTransferBeanDataTable.setBankId(bankTransferEnquiryDataTable.getToBankId());
				bankTransferBeanDataTable.setBankName((bankTransferEnquiryDataTable.getToBank()));
				//bankTransferBeanDataTable.setAccountDtlId(treasuryDealDetl.getTreasuryDealDetailBankAccountDetails().getBankAcctDetId());
				bankTransferBeanDataTable.setAccountNo((bankTransferEnquiryDataTable.getToAccount()));
				bankTransferBeanDataTable.setFcAmount(bankTransferEnquiryDataTable.getTofcAmount());
				bankTransferBeanDataTable.setFcAmt(bankTransferEnquiryDataTable.getTofcAmount().toPlainString());
				bankTransferBeanDataTable.setExChangeRate(bankTransferEnquiryDataTable.getToExchangeRate().toString());
				bankTransferBeanDataTable.setLocalAmount(bankTransferEnquiryDataTable.getToLocalFcAmount());
				bankTransferBeanDataTable.setLocalAmt(bankTransferEnquiryDataTable.getToLocalFcAmount().toString());
				//bankTransferBeanDataTable.setCreatedBy(treasuryDealDetl.getCreatedBy());
				//bankTransferBeanDataTable.setCreatedDate(treasuryDealDetl.getCreatedDate());
				bankTransferBeanDataTable.setCurrencyId(bankTransferEnquiryDataTable.getToCurrencyId());

				List<TreasuryStandardInstruction> 	treasuryStandardInstrucLst=bankTransferService.getTreasuryStandardInstructionInfo(new BigDecimal(bankTransferDTObj.getBankTransferReference()),getDocumentNo(),bankTransferEnquiryDataTable.getToPk(),sessionStateManage.getCompanyId());
				for(TreasuryStandardInstruction treasuryStdInstion:treasuryStandardInstrucLst){									
					//	if(treasuryStdInstion.getLineType().equals(Constants.PT)){
					if(treasuryStdInstion.getLineType().equals(Constants.BT)){
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
	}
	  }catch(NullPointerException ne){
	    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	    setErrorMessage("MethodName::gotoBankTransferEnquiry");
	    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    return; 
	    }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	    }

}


public void getDataTableForBnkTrnsFrmDetails(BigDecimal bankId,BigDecimal currencyId,String instructionType,BigDecimal accountDetId,BigDecimal standInstId) throws Exception{


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
	  }catch(NullPointerException ne){
	    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	    setErrorMessage("MethodName::showBnkTrToIns");
	    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    return; 
	    }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	    }

}

public void showBnkTrFrmIns() throws Exception
{
	if(purchaseCheckbox){
		RequestContext.getCurrentInstance().execute("PF('puchasedetails').show();");
	}
}
//Ram End

public void gotoBankTransferEnquiry1(BankTransferEnquiryDataTable bankTransferDTObj){
	  try{
	clearAll();
	setCompanyName(bankTransferDTObj.getCompany());
	setFinacialYear(bankTransferDTObj.getBankTransferYear());
	setReferenceNo( new BigDecimal(bankTransferDTObj.getBankTransferReference()));
	setAttention(bankTransferDTObj.getAttention() );
	setDocumentDate(bankTransferDTObj.getDocumentDate() );
	setDocumentDescription(bankTransferDTObj.getDocumentDecsrption());
	setDescription(bankTransferDTObj.getDescription());
	setValueDate( bankTransferDTObj.getValueDate());
	
	List<BankTransferEnquiryDataTable> bankTrnsferListFrom=bankTransferDTObj.getFromList();
	if(bankTrnsferListFrom.size()>0)
	{
		BankTransferEnquiryDataTable BankTransferEnquiryDataTableFrom =bankTrnsferListFrom.get(0);
		setFrmBank(BankTransferEnquiryDataTableFrom.getFrmBank() );
		setFrmCurrency(BankTransferEnquiryDataTableFrom.getFrmCurrency());
		if(BankTransferEnquiryDataTableFrom.getFrmlocalFcAmount()!=null){
		setFrmAccount(BankTransferEnquiryDataTableFrom.getFrmAccount());
		}
		setFrmExchangeRate( BankTransferEnquiryDataTableFrom.getFrmExchangeRate());
		setFrmfcAmount(BankTransferEnquiryDataTableFrom.getFrmfcAmount());
		setFrmlocalFcAmount(BankTransferEnquiryDataTableFrom.getFrmlocalFcAmount());
		
	}
	setFrmInstructionDescription(bankTransferDTObj.getFrmInstructionDescription());
	setFrmInstructionNumber(bankTransferDTObj.getFrmInstructionNumber());
	
	List<BankTransferEnquiryDataTable> bankTrnsferListTo=bankTransferDTObj.getToList();
	
	if(bankTrnsferListTo.size()>0)
	{
		BankTransferEnquiryDataTable bankTransferEnquiryDataTableTo =bankTrnsferListTo.get(0);
		setToBank(bankTransferEnquiryDataTableTo.getToBank());
		setToCurrency(bankTransferEnquiryDataTableTo.getToCurrency());
		setToAccount(bankTransferEnquiryDataTableTo.getToAccount());
		setToExchangeRate(bankTransferEnquiryDataTableTo.getToExchangeRate());
		setTofcAmount(bankTransferEnquiryDataTableTo.getTofcAmount());
		setToLocalFcAmount(bankTransferEnquiryDataTableTo.getToLocalFcAmount());
	}
	setToInstructionNumber(bankTransferDTObj.getToInstructionNumber() );
	setToInstructionDescription(bankTransferDTObj.getToInstructionDescription());
	
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransferviewenquiry.xhtml");
	}catch(Exception e){
		e.printStackTrace();
	}
	  }catch(NullPointerException ne){
	    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
	    setErrorMessage("MethodName::gotoBankTransferEnquiry1");
	    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
	    return; 
	    }catch(Exception exception){
	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;       
	    }
	
}


@Autowired
LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
public void navigationToBankTransferPage(){
	//bankTransferEnquiry();
	setAcyymm( null);
	setDealDate( null);
	bankTrnsferList.clear();
	ExternalContext context = FacesContext.getCurrentInstance()
			.getExternalContext();
	
	try {
		loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "banktransferenquiry.xhtml");
		context.redirect("../treasury/banktransferenquiry.xhtml");
	}catch(Exception exception){
	  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;
	}
}
  
public void clearAll(){
	
	setFrmAccount(null);
	setFrmBank(null);
	setFrmBank(null);
	setFrmfcAmount(null);
	setFrmExchangeRate(null);
	setFrmlocalFcAmount(null);

	setToAccount(null);
	setToBank(null);
	setToCurrency(null);
	setToExchangeRate(null);
	setTofcAmount(null);
	setToLocalFcAmount(null);
	
	
}
public void exit(){
	//bankTransferEnquiry();
	
	try {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransferenquiry.xhtml");
	}catch(Exception exception){
	  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
	    setErrorMessage(exception.getMessage()); 
	    RequestContext.getCurrentInstance().execute("error.show();");
	    return;
	}
}
	public void goHome(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
		}
	
	
}
  
/////////////////////////////////////////////////////////////////  Re - Report Generation Code  //////////////////////////////////////////////////
	
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	@Autowired
	ForeignLocalCurrencyDenominationService foreignLocalCurrencyDenominationService;
	
	private JasperPrint jasperPrint;
	private List<BankTransferInfo> treasuryDealInfoList=new CopyOnWriteArrayList<BankTransferInfo>();

	public void bankTransferDepositReportInit() throws JRException {
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/banktransfer_depositcopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	
	
	public void bankTransferFaxReportInit() throws JRException {
		
		JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(treasuryDealInfoList);
		String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/banktransfer_faxcopy.jasper");
		jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
	}
	
	public void generateBankTransferDepositReport(BankTransferEnquiryDataTable bankTransferDTObj) throws JRException, IOException {
		try {

			fetchBankTransferDepositInfo(new BigDecimal(bankTransferDTObj.getBankTransferYear()),new BigDecimal(bankTransferDTObj.getBankTransferReference()));
			bankTransferDepositReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=banktransfer_depositcopy.pdf");

			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println("exception" + e.getMessage());
		}

	}
    
	
	public void generateBankTransferFaxReport(BankTransferEnquiryDataTable bankTransferDTObj)
			throws JRException, IOException {
		
		try {

			fetchBankTransferFaxInfo(new BigDecimal(bankTransferDTObj.getBankTransferYear()),new BigDecimal(bankTransferDTObj.getBankTransferReference()));
			bankTransferFaxReportInit();
			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition","attachment; filename=banktransfer_faxcopy.pdf");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
			FacesContext.getCurrentInstance().responseComplete();
		} catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }

	}
	
	
	public void fetchBankTransferFaxInfo(BigDecimal documentYear,BigDecimal documentNumber){
		treasuryDealInfoList.clear();

		List<BankTransferInfo> bankToList = new ArrayList<BankTransferInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;
		
		List<ViewTreasuryDeal> saleList = new ArrayList<ViewTreasuryDeal>();
		List<ViewTreasuryDeal> treasuryDealInfo1 = new ArrayList<ViewTreasuryDeal>();
		String amountinWords = null;
		String totalAmountInWords=null;

	
		saleList = fXDetailInformationService.viewTreauryDealwithBankTransfer(documentYear,documentNumber,"BF",null);
		treasuryDealInfo1 = fXDetailInformationService.viewTreauryDealwithBankTransfer(documentYear,documentNumber,"BT",null);
		
		BankTransferInfo	treasuryDealInfoPurchase = new BankTransferInfo();
		treasuryDealInfoPurchase.setWaterMarkCheck(false);
		
		//set document date
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(treasuryDealInfo1.get( 0).getDocumentDate() );
		treasuryDealInfoPurchase.setDocumentDate(finalDate);
		
		//set branch name
		List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionStateManage.getBranchId()));
		if(branchList.size()>0){
		String branchName = branchList.get(0).getBranchName();
		treasuryDealInfoPurchase.setBranchName(branchName);
		}
		
		//set logo path
		treasuryDealInfoPurchase.setLogoPath(logoPath);
		treasuryDealInfoPurchase.setWaterMarkLogoPath(waterMark);
		treasuryDealInfoPurchase.setWaterMarkCheck(false);
		
		//set deal no
		if(saleList.size()>0){
				ViewTreasuryDeal treaDealObj = saleList.get(0);
			treasuryDealInfoPurchase.setDealNo(treaDealObj.getDealNo());	
			
			String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
			//set Company Header Name
			if(countryName!=null && !countryName.trim().equals("")){
			treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
			}
			
			//set fax number
			treasuryDealInfoPurchase.setFaxNo(treaDealObj.getFaxNo());
			
			if(treaDealObj.getBankAddress1()!=null && !treaDealObj.getBankAddress1().trim().equals("") && treaDealObj.getBankFullName()!=null && !treaDealObj.getBankFullName().trim().equals("")){
			String bankaddress = 	treaDealObj.getBankFullName()+" \n"+treaDealObj.getBankAddress1();
				treasuryDealInfoPurchase.setFromBankAddress(bankaddress);
			}else if(treaDealObj.getBankAddress1()!=null && !treaDealObj.getBankAddress1().trim().equals("")){
				treasuryDealInfoPurchase.setFromBankAddress(treaDealObj.getBankAddress1());	
			}else if(treaDealObj.getBankFullName()!=null && !treaDealObj.getBankFullName().trim().equals("")){
				treasuryDealInfoPurchase.setFromBankAddress(treaDealObj.getBankFullName());	
			}
			
			if(treaDealObj.getCurrencyname()!=null && treaDealObj.getAccountNumber()!=null){
			String debitAccountDetails = "PLEASE DEBIT OUR "+treaDealObj.getCurrencyname() +" A/C "+treaDealObj.getAccountNumber()+" AND TRANSFER: ";
			treasuryDealInfoPurchase.setDebitAccountDetails(debitAccountDetails);
			}
			
		}
		int lineNo = 1;
		for (ViewTreasuryDeal viewTreasuryDeal1 : treasuryDealInfo1) {
			
			BankTransferInfo	toBankTransfer = new BankTransferInfo();
			
			toBankTransfer.setLineNumber(new BigDecimal(lineNo));
			
			if(viewTreasuryDeal1.getCurrencyId()!=null && viewTreasuryDeal1.getFcAmount()!=null){
				 String currencyQuoteName = igeneralService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId());
				 BigDecimal toFcAmount = GetRound.roundBigDecimal((viewTreasuryDeal1.getFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
				 if(toFcAmount!=null){
					Double d =  toFcAmount.doubleValue();
					NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
					format.setMinimumFractionDigits(2);
					format.setMaximumFractionDigits(2);
					String capitalAmount = format.format(d).toString();
					String exchangeAndAmount = currencyQuoteName+" "+capitalAmount;
					toBankTransfer.setToBankAmount(exchangeAndAmount);
				 }
			}
			
			 List<CurrencyMaster> list = igeneralService.getCurrency(viewTreasuryDeal1.getCurrencyId());
				if(list.size()>0){
					amountinWords = igeneralService.callGetAmountInTextFunction(list.get(0).getDecimalName(),list.get(0).getDecinalNumber(),viewTreasuryDeal1.getFcAmount());
				}
				if(viewTreasuryDeal1.getCurrencyname()!=null && amountinWords!=null){
				totalAmountInWords = viewTreasuryDeal1.getCurrencyname()+" "+amountinWords;
				}
				String bankDetails = "( "+totalAmountInWords+" )";
				toBankTransfer.setToBankAmountInWords(bankDetails);
				
				
			/*	//set bank to address
				if(viewTreasuryDeal1.getBankAddress1()!=null && !viewTreasuryDeal1.getBankAddress1().trim().equals("") && viewTreasuryDeal1.getBankFullName()!=null && !viewTreasuryDeal1.getBankFullName().trim().equalsIgnoreCase("")){
					String bankName = "TO "+viewTreasuryDeal1.getBankFullName()+", ";
					String bankaddress = 	bankName+" \n"+viewTreasuryDeal1.getBankAddress1();
						bankAddressAndInstruction =bankaddress;
					}else if(viewTreasuryDeal1.getBankAddress1()!=null && !viewTreasuryDeal1.getBankAddress1().trim().equals("")){
						bankAddressAndInstruction = viewTreasuryDeal1.getBankAddress1();
					}else if(viewTreasuryDeal1.getBankFullName()!=null){
						String bankName = "TO "+viewTreasuryDeal1.getBankFullName()+", ";
						bankAddressAndInstruction = bankName;
					}*/
				
				
				
				//set bank to standard instruction
				String standardInstructionDesc = "";
				List<TreasuryStandardInstruction> standardinstruction =	bankTransferService.getTreasuryStandardInstruction(documentYear,documentNumber, viewTreasuryDeal1.getTreasuryDealDetailsId());
				if(standardinstruction.size()>0){
				String instructionDesc =""; 
				for(TreasuryStandardInstruction trStdIns:standardinstruction){
					if(instructionDesc.trim().equals("")){
						instructionDesc  = trStdIns.getMessageDescription().replace("#", "");
					}else{
						instructionDesc  = instructionDesc+" \n"+trStdIns.getMessageDescription().replace("#", "");
					}
				}
				standardInstructionDesc = instructionDesc;
				}
				
				toBankTransfer.setToBankAddress(standardInstructionDesc);
				
			bankToList.add(toBankTransfer);
			lineNo = lineNo+1;
		}
		
		List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
		if(companyDesc.size()>0){
		treasuryDealInfoPurchase.setCompanyName(companyDesc.get(0).getCompanyName());
		String cmpTelNo = companyDesc.get(0).getFsCompanyMaster().getTelephoneNo();
		List<CountryMaster> countryAlphaList = igeneralService.getCountryAlphaList(sessionStateManage.getCountryId());
			if(countryAlphaList.size()>0){
			String countryCode =countryAlphaList.get(0).getCountryTelCode();
			String finalTelCode = "( "+"00"+countryCode+" )";
			String contsctMsg ="IN CASE OF ANY DISCREPANCY, PLEASE CONTACT TEL NO:" +finalTelCode+"  "+cmpTelNo +" IMMEDIATELY";
			treasuryDealInfoPurchase.setContactMsg(contsctMsg);
			}
		}
		treasuryDealInfoPurchase.setToFaxTransactionList(bankToList);
		
		treasuryDealInfoList.add(treasuryDealInfoPurchase);
		
	}
	
	public void fetchBankTransferDepositInfo(BigDecimal documentYear,BigDecimal documentNumber) {
		treasuryDealInfoList.clear();

		List<BankTransferInfo> bankToList = new ArrayList<BankTransferInfo>();
		ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String realPath = ctx.getRealPath("//");
		//String logoPath = realPath + Constants.LOGO_PATH;
		
		String logoPath =null;
		if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.KUWAIT)){
			logoPath = realPath+Constants.LOGO_PATH;
		}else if(sessionStateManage.getCountryName().equalsIgnoreCase(Constants.OMAN)){
			logoPath = realPath+Constants.LOGO_PATH_OM;
		}else{
			logoPath =realPath+Constants.LOGO_PATH;
		}
		
		String waterMark =  realPath+Constants.REPORT_WATERMARK_LOGO;
		List<ViewTreasuryDeal> treasuryDealInfo1 = new ArrayList<ViewTreasuryDeal>();
		List<ViewTreasuryDeal> saleList = new ArrayList<ViewTreasuryDeal>();
		String amountinWords = null;

	
		treasuryDealInfo1 = fXDetailInformationService.viewTreauryDealwithBankTransfer(documentYear,documentNumber,"BT",null);
		saleList = fXDetailInformationService.viewTreauryDealwithBankTransfer(documentYear,documentNumber,"BF",null);
		
		BankTransferInfo treasuryDealInfoPurchase = new BankTransferInfo();
		
		
		//set document no
		if(documentNumber!=null && treasuryDealInfo1.get(0).getDocumentFinanceYear()!=null){
		treasuryDealInfoPurchase.setDocumentNo(treasuryDealInfo1.get(0).getDocumentFinanceYear().toString()+" / "+documentNumber.toString());
		}
		
		//set document date 
		
		SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
		String finalDate = form.format(treasuryDealInfo1.get( 0).getDocumentDate());
		treasuryDealInfoPurchase.setDocumentDate(finalDate);
		
		//set branch Name
		List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(sessionStateManage.getBranchId()));
		if(branchList.size()>0){
		String branchName = branchList.get(0).getBranchName();
		treasuryDealInfoPurchase.setBranchName(branchName);
		}
		//set logo path
		treasuryDealInfoPurchase.setLogoPath(logoPath);
		treasuryDealInfoPurchase.setWaterMarkLogoPath(waterMark);
		treasuryDealInfoPurchase.setWaterMarkCheck(false);
		
		List<CompanyMasterDesc> companyDesc =	igeneralService.getCompanyList(sessionStateManage.getCompanyId(), sessionStateManage.getLanguageId());
		String countryName =  igeneralService.getCountryName(sessionStateManage.getCountryId());
		//set Company Header Name
		if(countryName!=null && !countryName.equals("")){
		treasuryDealInfoPurchase.setCompanyHeaderName("ALMULLA EXCHANGE - "+countryName);
		}
		CompanyMasterDesc companyDesObj = companyDesc.get(0);
		
		//set company address
		if(companyDesObj!=null){
			StringBuffer companyAdds = new StringBuffer();
			if(companyDesObj.getFsCompanyMaster().getAddress1()!=null){
				companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress1());
				companyAdds.append(", ");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
				companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
				companyAdds.append(", ");
			}
			if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
				companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress3());
			}
			treasuryDealInfoPurchase.setCompanyAddress(companyAdds.toString());
		}
		
		//set company phone number
		treasuryDealInfoPurchase.setCompanyPhone(companyDesObj.getFsCompanyMaster().getTelephoneNo());
		
		//set company fax
		treasuryDealInfoPurchase.setCompanyFax(companyDesObj.getFsCompanyMaster().getFaxNo());
		
		//set company reg no
		treasuryDealInfoPurchase.setCompanyRegNo(companyDesObj.getFsCompanyMaster().getRegistrationNumber());
		
		//set company telex number
		treasuryDealInfoPurchase.setCompanyTelex(companyDesObj.getFsCompanyMaster().getTelexNo());
		
		//set company email 
		treasuryDealInfoPurchase.setCompanyEmail(companyDesObj.getFsCompanyMaster().getEmail());
		
		//set company share capital
		if(companyDesObj.getFsCompanyMaster().getCapitalAmount()!=null && companyDesObj.getFsCompanyMaster().getCurrencyId()!=null){
			 String currencyQuoteName = igeneralService.getCurrencyQuote(companyDesObj.getFsCompanyMaster().getCurrencyId());
			Integer d =  Integer.parseInt(companyDesObj.getFsCompanyMaster().getCapitalAmount());
			NumberFormat format = NumberFormat.getNumberInstance(new Locale("en", "in"));
			String capitalAmount = format.format(d).toString();
			
		treasuryDealInfoPurchase.setCompanyShareCaptal(currencyQuoteName+"   "+capitalAmount);
		}
		
		for (ViewTreasuryDeal viewTreasuryDeal : saleList){
		
		
			List<CurrencyMaster> list = igeneralService.getCurrency(viewTreasuryDeal.getCurrencyId());
			if(list.size()>0){
				amountinWords = igeneralService.callGetAmountInTextFunction(list.get(0).getDecimalName(),list.get(0).getDecinalNumber(),viewTreasuryDeal.getFcAmount());
			}
			if(viewTreasuryDeal.getCurrencyname()!=null && amountinWords!=null){
			String totalAmountInWords = viewTreasuryDeal.getCurrencyname()+" "+amountinWords;
			treasuryDealInfoPurchase.setTotalAmountInWords(totalAmountInWords);
			}
			
			if(viewTreasuryDeal.getBankCode()!=null && viewTreasuryDeal.getAccountNumber()!=null){
				String toBankDetails = viewTreasuryDeal.getBankCode()+" / "+viewTreasuryDeal.getAccountNumber();
			treasuryDealInfoPurchase.setFromBankDeatails(toBankDetails);
			}
		
			if(viewTreasuryDeal.getCurrencyId()!=null && viewTreasuryDeal.getFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null){
			 String currencyQuoteName = igeneralService.getCurrencyQuote(viewTreasuryDeal.getCurrencyId());
			 BigDecimal toFcAmount = GetRound.roundBigDecimal((viewTreasuryDeal.getFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal.getCurrencyId()));
			 String exchangeAndAmount = "( "+currencyQuoteName+" "+toFcAmount.toString()+" @ "+viewTreasuryDeal.getLocalExchangeRate()+" )";
			 treasuryDealInfoPurchase.setFromBankExchangeAndAmountDetails(exchangeAndAmount);
			}
			if(viewTreasuryDeal.getFaAccountNo()!=null){
				treasuryDealInfoPurchase.setFromFaFundAccountNo(viewTreasuryDeal.getFaAccountNo());
			}
			if(viewTreasuryDeal.getFcAmount()!=null && viewTreasuryDeal.getLocalExchangeRate()!=null){
				BigDecimal totalAmount = viewTreasuryDeal.getFcAmount().multiply(viewTreasuryDeal.getLocalExchangeRate());
				BigDecimal totalRoundAmount = GetRound.roundBigDecimal((totalAmount),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
				String currencyQuoteName = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
				String fromTotalAmount = currencyQuoteName+" "+totalRoundAmount.toString();
				treasuryDealInfoPurchase.setFromTotalAmount(fromTotalAmount);
			}
		}
		
		for (ViewTreasuryDeal viewTreasuryDeal1 : treasuryDealInfo1) {
			BankTransferInfo	toBankTransfer = new BankTransferInfo();
			if(viewTreasuryDeal1.getBankCode()!=null && viewTreasuryDeal1.getAccountNumber()!=null){
				String toBankDetails = viewTreasuryDeal1.getBankCode()+" / "+viewTreasuryDeal1.getAccountNumber();
				toBankTransfer.setToBankDeatails(toBankDetails);
			}
		
			if(viewTreasuryDeal1.getCurrencyId()!=null && viewTreasuryDeal1.getFcAmount()!=null && viewTreasuryDeal1.getLocalExchangeRate()!=null){
			 String currencyQuoteName = igeneralService.getCurrencyQuote(viewTreasuryDeal1.getCurrencyId());
			 BigDecimal toFcAmount = GetRound.roundBigDecimal((viewTreasuryDeal1.getFcAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(viewTreasuryDeal1.getCurrencyId()));
			 String exchangeAndAmount = "( "+currencyQuoteName+" "+toFcAmount.toString()+" @ "+viewTreasuryDeal1.getLocalExchangeRate()+" )";
			 toBankTransfer.setToBankExchangeAndAmountDetails(exchangeAndAmount);
			}
			if(viewTreasuryDeal1.getFaAccountNo()!=null){
				toBankTransfer.setToFaFundAccountNo(viewTreasuryDeal1.getFaAccountNo());
			}
			if(viewTreasuryDeal1.getFcAmount()!=null && viewTreasuryDeal1.getLocalExchangeRate()!=null){
				BigDecimal totalAmount = viewTreasuryDeal1.getFcAmount().multiply(viewTreasuryDeal1.getLocalExchangeRate());
				BigDecimal totalRoundAmount = GetRound.roundBigDecimal((totalAmount),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
				String currencyQuoteName = igeneralService.getCurrencyQuote(new BigDecimal(sessionStateManage.getCurrencyId()));
				String fromTotalAmount = currencyQuoteName+" "+totalRoundAmount.toString();
				toBankTransfer.setToTotalAmount(fromTotalAmount);
			}
			
			bankToList.add(toBankTransfer);
		}
		
		treasuryDealInfoPurchase.setToTransactionList(bankToList);
		
		treasuryDealInfoList.add(treasuryDealInfoPurchase);
		
		
	}

	  private String errorMessage;
		private String acyymm;
		private Date dealDate;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	public String getAcyymm() {
		return acyymm;
	}
	public void setAcyymm(String acyymm) {
		this.acyymm = acyymm;
	}
	public void onSelectDateSelect(SelectEvent event) {
		log.info("Entering into onSelectDateSelect method");
		try{
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
		setAcyymm(format.format(event.getObject()));
		bankTransferEnquiry();
	 
		}catch(NullPointerException ne){
			 log.info("Exit into onSelectDateSelect Db method"+ne.getMessage());
			  setErrorMessage("Method Name::fetchDataFrom"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			log.info("Exit into onSelectDateSelect Db method"+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		
	}
	public Date getDealDate() {
		return dealDate;
	}
	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}
	
}
