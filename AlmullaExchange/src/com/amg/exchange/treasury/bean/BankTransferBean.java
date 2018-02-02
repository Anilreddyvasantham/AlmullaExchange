package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.AccountBalance;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.model.StandardInstruction;
import com.amg.exchange.treasury.model.StandardInstructionDetails;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.model.TreasuryDealHeader;
import com.amg.exchange.treasury.model.TreasuryStandardInstruction;
import com.amg.exchange.treasury.model.ViewTreasuryDeal;
import com.amg.exchange.treasury.service.IBankMasterService;
import com.amg.exchange.treasury.service.IBankTransferService;
import com.amg.exchange.treasury.service.IFXDetailInformationService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.treasury.service.IStandardInstructionsService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankTransferBean")
@Scope("session")
public class BankTransferBean<T> implements Serializable {
	 
	/*
	 * serialVersionUID is used to ensure that during de serialization the same class (that was used during serialize process) is loaded.
	*/
	private static final long serialVersionUID = 1L;
	
	
	public static final Logger log = Logger.getLogger(BankTransferBean.class);
	
	
	private BigDecimal teasuryDealHeaderId = null; // PK
	private BigDecimal treasuryInstructionId ;
	private BigDecimal teasuryDealDetailId = null; // PK
	private BigDecimal treasurytandardInstructionTo;
	private BigDecimal treasuryStandardInstructionId;
	
	private Date date3;
	private BigDecimal companyId=null;
	private String companyName;
	private String documentDescription=null;
	private BigDecimal documentNo=null;
	private BigDecimal bankId=null;
	private String bankName="";
	private String bankNameTo="";
	private BigDecimal bankCurrencyId = null;
	private BigDecimal bankCurrencyIdTO = null;
	private String bankCurrencyName = null;
	private String bankCurrencyNameTO = null;
	private BigDecimal currencyId = null;
	private BigDecimal currencyIdTO = null;
	private String currencyName="";
	private BigDecimal bankIdTO=null;
	private String currencyNameTO="";
	private BigDecimal financialYearId=null;
	private int finaceYear=0;
	private BigDecimal documentSerialIdNumber=null;
	
	private String processIn=Constants.Yes;
	private BigDecimal docSerialIdNumberForSave;
	private BigDecimal bnkTOFCAmt=null;
	
	private String createdBy=null;
	private Date createdDate=null;
	private Boolean disableDataTableEdit=false;
	
	//Bank Transfer Info 
	private String bnkAttention;
	private String bnkDescription;
	private String bnkValueDate;
	private Date bnkTrInfoValueDate;
	
	//Bank Transfer FROM
	private String bnkTrFromAccNo=null;
	private BigDecimal bnkTrFromFCAmount;
	private BigDecimal bnkTrFromFCAmountTemp;
	private BigDecimal bnkTrFromExchangeRate; 
	private BigDecimal bnkTrFromLocalAmount;
	private String bankTrFromInstructionDesc;
	private String bankTrFromInstrunction;
	private String bnkInfoDate;
	private String bnkTrFromAccName;
	private boolean readonly = false;
	
	//Bank Transfer TO
	private String bnkTrToAccNo;
	private BigDecimal bnkTrToFCAmount;
	private BigDecimal bnkTrToExchangeRate;
	private BigDecimal bnkTrToLocalAmount;
	private String bankTrToInstrunction;
	private String bankTrToInstructionDesc;
	private String bnkTrToAccName;
	private BigDecimal banlanceAmonut;
	private BigDecimal banFCAmtForCal;
	private String bankTrToInstrunctionName;
	private BigDecimal showAvilableBalance;
	
	private List<BigDecimal> updateBankID;
	private String bnkInfoCrDtForUpdate;
	private String bnkInfoCrByForUpdate;
	
	//CR added
	private String isActiveforSales=Constants.Yes;
	private boolean salesCheckbox;
	//CR added
	private String isActiveforPurchase=Constants.Yes;
	private boolean purchaseCheckbox;
	
	//render
	private Boolean boobankCurrency=true;
	private Boolean booSelectbankCurrency=false;
	
	private Boolean boobankCurrencyTO=true;
	private Boolean booSelectbankCurrencyTO=false;
	
	private boolean successRender = false;
	private boolean mainRenderPanel = false;
	
	private Boolean renderDataTableDelete=false;
	
	
	//For iNput fileds
	private String bankIdForUpdate=null;
	private String currencyIdUpdate=null;
	private String bnkTrFromAccNoForUpdate=null;
	private String bankTrFromInstrunctionForUpdate=null;
	private String bankIdTOForUpdate=null;
	private String currencyIdTOForUpdate=null;
	private String bnkTrToAccNoForUpdate=null;
	private String bankTrToInstrunctionForUpdate=null;
	private Date bnkValueDateForUpdate=null;
	private BigDecimal bankTransferEditableRef=null;
	
	//For BOOlean values
	private Boolean booRenderBankId=false;
	private Boolean booRenderBankIdForUpdate=false;
	private Boolean booRenderCurrencyId=false;
	private Boolean booRenderCurrencyIdForUpdate=false;
	private Boolean booRenderBnkTrFromAccNo=false;
	private Boolean booRenderBnkTrFromAccNoForUpdate=false;
	private Boolean booRenderBankTrFromInstrunction=false;
	private Boolean booRenderBankTrFromInstrunctionForUpdate=false;
	private Boolean booRenderBankIdTO=false;
	private Boolean booRenderBankIdTOForUpdate=false;
	private Boolean booRenderCurrencyIdTO=false;
	private Boolean booRenderCurrencyIdTOForUpdate=false;
	private Boolean booRenderBnkTrToAccNo=false;
	private Boolean booRenderBnkTrToAccNoForUpdate=false;
	private Boolean booRenderBankTrToInstrunction=false;
	private Boolean booRenderBankTrToInstrunctionForUpdate=false;
	private Boolean booRenderbnkValueDate=false;
	private Boolean booRenderbnkValueDateForUpdate=false;
	private Boolean booRenderUpdate=false;
	private Boolean booRenderAdd=false;
	private Boolean booBankTransferRef=false;
	private Boolean booBankTransferEditableRef=false;
	private Boolean booRenderDataTable=false;
	private Boolean booRenderSavePanel=false;
	private Boolean booRenderExit=false;
	private Boolean booRenderCancel=false;
	private Boolean booRenderBankTransferToPanel=false;
	private Boolean booRenderAddPanel;
	private Boolean booRenderUpdatePanel=false;
	private Boolean booRenderEditColumn=false;
	private Boolean booRenderAddPanelForUpdate=false;
	private boolean booRenderEditColumnForFirstTimeSave=false;
	//to work checkbox functionality
	public boolean bankStandardInstruction=true;
	public boolean tobankStandardInstruction=true;
	//Added By Nazish for delete data table hide datat table
	private Boolean booRenderFCAmount= false;
	private BigDecimal tresuryHdPK=null;
	private BigDecimal tresuryDtPK=null;
	private BigDecimal tresurySdPK=null;
	private BigDecimal totalBalance=null;
	
	private Boolean displayDynamicColor=false;
	private Boolean booRenderOffEditIcon=false;
	private Boolean booDisplayAddNewTrasLink=false;
	private Boolean booRenderBalancePanelPanel=false;
	private Boolean disableAddNewTransactionLink=false;
	private int bankcurrencySize=0;
	
	private BigDecimal instructionBankFormPk;
	private BigDecimal instructionBankToPk;
	
	private BigDecimal treasuryDetailBrFromPkId;
	private List<BigDecimal> treasuryStdBrFromPkId;
	
	
	private Boolean booDocVisble;
	private String errmsg;
	
	//CR getting all Instruction Numbers from DB
	private List<StandardInstructionDetails> checkboxstandardInstrnDetailsFROM = new ArrayList<StandardInstructionDetails>();
	private List<StandardInstructionDetails> checkboxstandardInstrnDetailsTO = new ArrayList<StandardInstructionDetails>();
	private List<CompanyMasterDesc> companyListFromDB=new ArrayList<CompanyMasterDesc>();
	private List<BankAccountDetails> currencyListFromDB=new ArrayList<BankAccountDetails>();
	private List<BankTransferInfo> treasuryDealInfoList=new CopyOnWriteArrayList<BankTransferInfo>();
	private List<TreasuryDealInfo> saleInfoList=new CopyOnWriteArrayList<TreasuryDealInfo>();
	private List<Document> documentDesc=new ArrayList<Document>();
	private SessionStateManage sessionStateManage=new SessionStateManage();
	private List<BankApplicability> bankLst=new ArrayList<BankApplicability>();
	private List<BankAccountDetails> currencyOfBank=new ArrayList<BankAccountDetails>();
	private List<BankApplicability> bankLstTO=new ArrayList<BankApplicability>();
	private List<BankAccountDetails> currencyOfBankTO=new ArrayList<BankAccountDetails>();
	private List<BankAccountDetails> lstAccountNumber = new ArrayList<BankAccountDetails>();
	private List<StandardInstruction> pstandardInstrnforPurchase = new ArrayList<StandardInstruction>();
	private List<BankAccountDetails> lstBnkToAccountNumber = new ArrayList<BankAccountDetails>();
	private List<StandardInstruction> pstandardInstrnforBnkTrTo = new ArrayList<StandardInstruction>();
	private List<BankTransferBeanDataTable> bankTransferBeanDataTableLst=new ArrayList<BankTransferBeanDataTable>();
	private List<BankTransferBeanDataTable> bankTransferFromBeanLst=new ArrayList<BankTransferBeanDataTable>();
	private List<BankApplicability> rmBankLstTOMaintain=new ArrayList<BankApplicability>();
	
	private Map<String, String> mapAountNumberMaintain = new HashMap<String,String>();
	
	private Map<BigDecimal, String> mapBankListMaintain = new HashMap<BigDecimal,String>();
	
	
	@Autowired
	ForeignLocalCurrencyDenominationService foreignLocalCurrencyDenominationService;
	
	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	@Autowired
	IGeneralService<T> igeneralService;
	@Autowired
	IBankTransferService<T> bankTransferService;
	
	@Autowired
	ICashTransferLToLService cashTransferLToLService;
	
	@Autowired
	IFXDetailInformationService<T> fXDetailInformationService;
	
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	
	@Autowired
	IBankMasterService<T> bankMasterInfoService;
	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;

	@Autowired
	IStandardInstructionsService<T> standardInstructionsService;
	


	
	


	public Boolean getBooDocVisble() {
		return booDocVisble;
	}

	public void setBooDocVisble(Boolean booDocVisble) {
		this.booDocVisble = booDocVisble;
	}

	public String getErrmsg() {
		return errmsg;
	}

	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public BigDecimal getTreasuryDetailBrFromPkId() {
		return treasuryDetailBrFromPkId;
	}

	public void setTreasuryDetailBrFromPkId(BigDecimal treasuryDetailBrFromPkId) {
		this.treasuryDetailBrFromPkId = treasuryDetailBrFromPkId;
	}

	public List<BigDecimal> getTreasuryStdBrFromPkId() {
		return treasuryStdBrFromPkId;
	}

	public void setTreasuryStdBrFromPkId(List<BigDecimal> treasuryStdBrFromPkId) {
		this.treasuryStdBrFromPkId = treasuryStdBrFromPkId;
	}

	public Boolean getDisableDataTableEdit() {
		return disableDataTableEdit;
	}

	public void setDisableDataTableEdit(Boolean disableDataTableEdit) {
		this.disableDataTableEdit = disableDataTableEdit;
	}

	public Boolean getRenderDataTableDelete() {
		return renderDataTableDelete;
	}

	public void setRenderDataTableDelete(Boolean renderDataTableDelete) {
		this.renderDataTableDelete = renderDataTableDelete;
	}

	public BigDecimal getInstructionBankFormPk() {
		return instructionBankFormPk;
	}

	public BigDecimal getInstructionBankToPk() {
		return instructionBankToPk;
	}

	public void setInstructionBankFormPk(BigDecimal instructionBankFormPk) {
		this.instructionBankFormPk = instructionBankFormPk;
	}

	public void setInstructionBankToPk(BigDecimal instructionBankToPk) {
		this.instructionBankToPk = instructionBankToPk;
	}

	public Map<String, String> getMapAountNumberMaintain() {
		return mapAountNumberMaintain;
	}

	public void setMapAountNumberMaintain(Map<String, String> mapAountNumberMaintain) {
		this.mapAountNumberMaintain = mapAountNumberMaintain;
	}
	
	

	public Map<BigDecimal, String> getMapBankListMaintain() {
		return mapBankListMaintain;
	}

	public void setMapBankListMaintain(Map<BigDecimal, String> mapBankListMaintain) {
		this.mapBankListMaintain = mapBankListMaintain;
	}

	public Boolean getDisableAddNewTransactionLink() {
		return disableAddNewTransactionLink;
	}

	public void setDisableAddNewTransactionLink(Boolean disableAddNewTransactionLink) {
		this.disableAddNewTransactionLink = disableAddNewTransactionLink;
	}

	public Boolean getBooRenderBalancePanelPanel() {
		return booRenderBalancePanelPanel;
	}

	public void setBooRenderBalancePanelPanel(Boolean booRenderBalancePanelPanel) {
		this.booRenderBalancePanelPanel = booRenderBalancePanelPanel;
	}

	public Boolean getBooDisplayAddNewTrasLink() {
		return booDisplayAddNewTrasLink;
	}

	public void setBooDisplayAddNewTrasLink(Boolean booDisplayAddNewTrasLink) {
		this.booDisplayAddNewTrasLink = booDisplayAddNewTrasLink;
	}

	public Boolean getBooRenderOffEditIcon() {
		return booRenderOffEditIcon;
	}

	public void setBooRenderOffEditIcon(Boolean booRenderOffEditIcon) {
		this.booRenderOffEditIcon = booRenderOffEditIcon;
	}


	public Boolean getDisplayDynamicColor() {
		return displayDynamicColor;
	}

	public void setDisplayDynamicColor(Boolean displayDynamicColor) {
		this.displayDynamicColor = displayDynamicColor;
	}
	
	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
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

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Boolean getBooRenderFCAmount() {
		return booRenderFCAmount;
	}

	public void setBooRenderFCAmount(Boolean booRenderFCAmount) {
		this.booRenderFCAmount = booRenderFCAmount;
	}
	public boolean isBankStandardInstruction() {
		return bankStandardInstruction;
	}

	public void setBankStandardInstruction(boolean bankStandardInstruction) {
		this.bankStandardInstruction = bankStandardInstruction;
	}

	public boolean isTobankStandardInstruction() {
		return tobankStandardInstruction;
	}

	public void setTobankStandardInstruction(boolean tobankStandardInstruction) {
		this.tobankStandardInstruction = tobankStandardInstruction;
	}
	private Date effectiveMinDate = new Date();

	public Date getEffectiveMinDate() {
		return effectiveMinDate;
	}

	public void setEffectiveMinDate(Date effectiveMinDate) {
		this.effectiveMinDate = effectiveMinDate;
	}
	public boolean isBooRenderEditColumnForFirstTimeSave() {
		return booRenderEditColumnForFirstTimeSave;
	}

	public void setBooRenderEditColumnForFirstTimeSave(
			boolean booRenderEditColumnForFirstTimeSave) {
		this.booRenderEditColumnForFirstTimeSave = booRenderEditColumnForFirstTimeSave;
	}
	public boolean isSuccessRender() {
		return successRender;
	}

	public void setSuccessRender(boolean successRender) {
		this.successRender = successRender;
	}

	public boolean isMainRenderPanel() {
		return mainRenderPanel;
	}

	public void setMainRenderPanel(boolean mainRenderPanel) {
		this.mainRenderPanel = mainRenderPanel;
	}
	public Boolean getBooRenderAddPanelForUpdate() {
		return booRenderAddPanelForUpdate;
	}

	public void setBooRenderAddPanelForUpdate(Boolean booRenderAddPanelForUpdate) {
		this.booRenderAddPanelForUpdate = booRenderAddPanelForUpdate;
	}

	public Boolean getBooRenderEditColumn() {
		return booRenderEditColumn;
	}

	public void setBooRenderEditColumn(Boolean booRenderEditColumn) {
		this.booRenderEditColumn = booRenderEditColumn;
	}

	public Boolean getBooRenderUpdatePanel() {
		return booRenderUpdatePanel;
	}

	public void setBooRenderUpdatePanel(Boolean booRenderUpdatePanel) {
		this.booRenderUpdatePanel = booRenderUpdatePanel;
	}

	public Boolean getBooRenderAddPanel() {
		return booRenderAddPanel;
	}

	public void setBooRenderAddPanel(Boolean booRenderAddPanel) {
		this.booRenderAddPanel = booRenderAddPanel;
	}

	public Boolean getBooRenderBankTransferToPanel() {
		return booRenderBankTransferToPanel;
	}

	public void setBooRenderBankTransferToPanel(Boolean booRenderBankTransferToPanel) {
		this.booRenderBankTransferToPanel = booRenderBankTransferToPanel;
	}

	public Date getBnkValueDateForUpdate() {
		return bnkValueDateForUpdate;
	}

	public void setBnkValueDateForUpdate(Date bnkValueDateForUpdate) {
		this.bnkValueDateForUpdate = bnkValueDateForUpdate;
	}

	public Boolean getBooRenderbnkValueDate() {
		return booRenderbnkValueDate;
	}

	public void setBooRenderbnkValueDate(Boolean booRenderbnkValueDate) {
		this.booRenderbnkValueDate = booRenderbnkValueDate;
	}

	public Boolean getBooRenderbnkValueDateForUpdate() {
		return booRenderbnkValueDateForUpdate;
	}

	public void setBooRenderbnkValueDateForUpdate(
			Boolean booRenderbnkValueDateForUpdate) {
		this.booRenderbnkValueDateForUpdate = booRenderbnkValueDateForUpdate;
	}

	public String getBankIdForUpdate() {
		return bankIdForUpdate;
	}

	public void setBankIdForUpdate(String bankIdForUpdate) {
		this.bankIdForUpdate = bankIdForUpdate;
	}

	public String getCurrencyIdUpdate() {
		return currencyIdUpdate;
	}

	public void setCurrencyIdUpdate(String currencyIdUpdate) {
		this.currencyIdUpdate = currencyIdUpdate;
	}

	public String getBnkTrFromAccNoForUpdate() {
		return bnkTrFromAccNoForUpdate;
	}

	public void setBnkTrFromAccNoForUpdate(String bnkTrFromAccNoForUpdate) {
		this.bnkTrFromAccNoForUpdate = bnkTrFromAccNoForUpdate;
	}

	public String getBankTrFromInstrunctionForUpdate() {
		return bankTrFromInstrunctionForUpdate;
	}

	public void setBankTrFromInstrunctionForUpdate(
			String bankTrFromInstrunctionForUpdate) {
		this.bankTrFromInstrunctionForUpdate = bankTrFromInstrunctionForUpdate;
	}

	public String getBankIdTOForUpdate() {
		return bankIdTOForUpdate;
	}

	public void setBankIdTOForUpdate(String bankIdTOForUpdate) {
		this.bankIdTOForUpdate = bankIdTOForUpdate;
	}

	public String getCurrencyIdTOForUpdate() {
		return currencyIdTOForUpdate;
	}

	public void setCurrencyIdTOForUpdate(String currencyIdTOForUpdate) {
		this.currencyIdTOForUpdate = currencyIdTOForUpdate;
	}

	public String getBnkTrToAccNoForUpdate() {
		return bnkTrToAccNoForUpdate;
	}

	public void setBnkTrToAccNoForUpdate(String bnkTrToAccNoForUpdate) {
		this.bnkTrToAccNoForUpdate = bnkTrToAccNoForUpdate;
	}

	public String getBankTrToInstrunctionForUpdate() {
		return bankTrToInstrunctionForUpdate;
	}

	public void setBankTrToInstrunctionForUpdate(
			String bankTrToInstrunctionForUpdate) {
		this.bankTrToInstrunctionForUpdate = bankTrToInstrunctionForUpdate;
	}

	public Boolean getBooRenderBankId() {
		return booRenderBankId;
	}

	public void setBooRenderBankId(Boolean booRenderBankId) {
		this.booRenderBankId = booRenderBankId;
	}

	public Boolean getBooRenderBankIdForUpdate() {
		return booRenderBankIdForUpdate;
	}

	public void setBooRenderBankIdForUpdate(Boolean booRenderBankIdForUpdate) {
		this.booRenderBankIdForUpdate = booRenderBankIdForUpdate;
	}

	public Boolean getBooRenderCurrencyId() {
		return booRenderCurrencyId;
	}

	public void setBooRenderCurrencyId(Boolean booRenderCurrencyId) {
		this.booRenderCurrencyId = booRenderCurrencyId;
	}

	public Boolean getBooRenderCurrencyIdForUpdate() {
		return booRenderCurrencyIdForUpdate;
	}

	public void setBooRenderCurrencyIdForUpdate(Boolean booRenderCurrencyIdForUpdate) {
		this.booRenderCurrencyIdForUpdate = booRenderCurrencyIdForUpdate;
	}

	public Boolean getBooRenderBnkTrFromAccNo() {
		return booRenderBnkTrFromAccNo;
	}

	public void setBooRenderBnkTrFromAccNo(Boolean booRenderBnkTrFromAccNo) {
		this.booRenderBnkTrFromAccNo = booRenderBnkTrFromAccNo;
	}

	public Boolean getBooRenderBnkTrFromAccNoForUpdate() {
		return booRenderBnkTrFromAccNoForUpdate;
	}

	public void setBooRenderBnkTrFromAccNoForUpdate(
			Boolean booRenderBnkTrFromAccNoForUpdate) {
		this.booRenderBnkTrFromAccNoForUpdate = booRenderBnkTrFromAccNoForUpdate;
	}

	public Boolean getBooRenderBankTrFromInstrunction() {
		return booRenderBankTrFromInstrunction;
	}

	public void setBooRenderBankTrFromInstrunction(
			Boolean booRenderBankTrFromInstrunction) {
		this.booRenderBankTrFromInstrunction = booRenderBankTrFromInstrunction;
	}

	public Boolean getBooRenderBankTrFromInstrunctionForUpdate() {
		return booRenderBankTrFromInstrunctionForUpdate;
	}

	public void setBooRenderBankTrFromInstrunctionForUpdate(
			Boolean booRenderBankTrFromInstrunctionForUpdate) {
		this.booRenderBankTrFromInstrunctionForUpdate = booRenderBankTrFromInstrunctionForUpdate;
	}

	public Boolean getBooRenderBankIdTO() {
		return booRenderBankIdTO;
	}

	public void setBooRenderBankIdTO(Boolean booRenderBankIdTO) {
		this.booRenderBankIdTO = booRenderBankIdTO;
	}

	public Boolean getBooRenderBankIdTOForUpdate() {
		return booRenderBankIdTOForUpdate;
	}

	public void setBooRenderBankIdTOForUpdate(Boolean booRenderBankIdTOForUpdate) {
		this.booRenderBankIdTOForUpdate = booRenderBankIdTOForUpdate;
	}

	public Boolean getBooRenderCurrencyIdTO() {
		return booRenderCurrencyIdTO;
	}

	public void setBooRenderCurrencyIdTO(Boolean booRenderCurrencyIdTO) {
		this.booRenderCurrencyIdTO = booRenderCurrencyIdTO;
	}

	public Boolean getBooRenderCurrencyIdTOForUpdate() {
		return booRenderCurrencyIdTOForUpdate;
	}

	public void setBooRenderCurrencyIdTOForUpdate(
			Boolean booRenderCurrencyIdTOForUpdate) {
		this.booRenderCurrencyIdTOForUpdate = booRenderCurrencyIdTOForUpdate;
	}

	public Boolean getBooRenderBnkTrToAccNo() {
		return booRenderBnkTrToAccNo;
	}

	public void setBooRenderBnkTrToAccNo(Boolean booRenderBnkTrToAccNo) {
		this.booRenderBnkTrToAccNo = booRenderBnkTrToAccNo;
	}

	public Boolean getBooRenderBnkTrToAccNoForUpdate() {
		return booRenderBnkTrToAccNoForUpdate;
	}

	public void setBooRenderBnkTrToAccNoForUpdate(
			Boolean booRenderBnkTrToAccNoForUpdate) {
		this.booRenderBnkTrToAccNoForUpdate = booRenderBnkTrToAccNoForUpdate;
	}

	public Boolean getBooRenderBankTrToInstrunction() {
		return booRenderBankTrToInstrunction;
	}

	public void setBooRenderBankTrToInstrunction(
			Boolean booRenderBankTrToInstrunction) {
		this.booRenderBankTrToInstrunction = booRenderBankTrToInstrunction;
	}

	public Boolean getBooRenderBankTrToInstrunctionForUpdate() {
		return booRenderBankTrToInstrunctionForUpdate;
	}

	public void setBooRenderBankTrToInstrunctionForUpdate(
			Boolean booRenderBankTrToInstrunctionForUpdate) {
		this.booRenderBankTrToInstrunctionForUpdate = booRenderBankTrToInstrunctionForUpdate;
	}

	public Boolean getBooRenderExit() {
		return booRenderExit;
	}

	public void setBooRenderExit(Boolean booRenderExit) {
		this.booRenderExit = booRenderExit;
	}

	public Boolean getBooRenderCancel() {
		return booRenderCancel;
	}

	public void setBooRenderCancel(Boolean booRenderCancel) {
		this.booRenderCancel = booRenderCancel;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooRenderSavePanel() {
		return booRenderSavePanel;
	}

	public void setBooRenderSavePanel(Boolean booRenderSavePanel) {
		this.booRenderSavePanel = booRenderSavePanel;
	}

	public Boolean getBooRenderUpdate() {
		return booRenderUpdate;
	}

	public void setBooRenderUpdate(Boolean booRenderUpdate) {
		this.booRenderUpdate = booRenderUpdate;
	}

	public Boolean getBooRenderAdd() {
		return booRenderAdd;
	}

	public void setBooRenderAdd(Boolean booRenderAdd) {
		this.booRenderAdd = booRenderAdd;
	}

	public Boolean getBooBankTransferRef() {
		return booBankTransferRef;
	}

	public void setBooBankTransferRef(Boolean booBankTransferRef) {
		this.booBankTransferRef = booBankTransferRef;
	}

	public Boolean getBooBankTransferEditableRef() {
		return booBankTransferEditableRef;
	}

	public void setBooBankTransferEditableRef(Boolean booBankTransferEditableRef) {
		this.booBankTransferEditableRef = booBankTransferEditableRef;
	}

	public BigDecimal getBankTransferEditableRef() {
		return bankTransferEditableRef;
	}

	public void setBankTransferEditableRef(BigDecimal bankTransferEditableRef) {
		this.bankTransferEditableRef = bankTransferEditableRef;
	}
	
	public BigDecimal getTreasuryStandardInstructionId() {
		return treasuryStandardInstructionId;
	}

	public void setTreasuryStandardInstructionId(
			BigDecimal treasuryStandardInstructionId) {
		this.treasuryStandardInstructionId = treasuryStandardInstructionId;
	}

	public BigDecimal getTreasurytandardInstructionTo() {
		return treasurytandardInstructionTo;
	}

	public void setTreasurytandardInstructionTo(
			BigDecimal treasurytandardInstructionTo) {
		this.treasurytandardInstructionTo = treasurytandardInstructionTo;
	}

	public BigDecimal getTeasuryDealDetailId() {
		return teasuryDealDetailId;
	}

	public void setTeasuryDealDetailId(BigDecimal teasuryDealDetailId) {
		this.teasuryDealDetailId = teasuryDealDetailId;
	}

	public BigDecimal getTreasuryInstructionId() {
		return treasuryInstructionId;
	}

	public void setTreasuryInstructionId(BigDecimal treasuryInstructionId) {
		this.treasuryInstructionId = treasuryInstructionId;
	}

	public BigDecimal getTeasuryDealHeaderId() {
		return teasuryDealHeaderId;
	}

	public void setTeasuryDealHeaderId(BigDecimal teasuryDealHeaderId) {
		this.teasuryDealHeaderId = teasuryDealHeaderId;
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


	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}
	 
	
	public IGeneralService<T> getIgeneralService() {
		return igeneralService;
	}

	public void setIgeneralService(IGeneralService<T> igeneralService) {
		this.igeneralService = igeneralService;
	}

	public IFundEstimationService<T> getFundEstimationService() {
		return fundEstimationService;
	}

	public void setFundEstimationService(
			IFundEstimationService<T> fundEstimationService) {
		this.fundEstimationService = fundEstimationService;
	}
	
	public IBankTransferService<T> getBankTransferService() {
		return bankTransferService;
	}

	public void setBankTransferService(IBankTransferService<T> bankTransferService) {
		this.bankTransferService = bankTransferService;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public BigDecimal getCompanyId() {
		return companyId;
	}

	public void setCompanyId(BigDecimal companyId) {
		this.companyId = companyId;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public void setDocumentDescription(String documentDescription) {
		this.documentDescription = documentDescription;
	}
	
	
	public boolean isReadonly() {
		return readonly;
	}

	public void setReadonly(boolean readonly) {
		this.readonly = readonly;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	
	public String getBankNameTo() {
		return bankNameTo;
	}

	public void setBankNameTo(String bankNameTo) {
		this.bankNameTo = bankNameTo;
	}

	public BigDecimal getBankCurrencyId() {
		return bankCurrencyId;
	}

	public void setBankCurrencyId(BigDecimal bankCurrencyId) {
		this.bankCurrencyId = bankCurrencyId;
	}

	public String getBankCurrencyName() {
		return bankCurrencyName;
	}

	public void setBankCurrencyName(String bankCurrencyName) {
		this.bankCurrencyName = bankCurrencyName;
	}
	

	public Boolean getBoobankCurrency() {
		return boobankCurrency;
	}

	public void setBoobankCurrency(Boolean boobankCurrency) {
		this.boobankCurrency = boobankCurrency;
	}

	public Boolean getBooSelectbankCurrency() {
		return booSelectbankCurrency;
	}

	public void setBooSelectbankCurrency(Boolean booSelectbankCurrency) {
		this.booSelectbankCurrency = booSelectbankCurrency;
	}

	
	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}


	public List<BigDecimal> getUpdateBankID() {
		return updateBankID;
	}

	public void setUpdateBankID(List<BigDecimal> updateBankID) {
		this.updateBankID = updateBankID;
	}

	public String getBankCurrencyNameTO() {
		return bankCurrencyNameTO;
	}

	public void setBankCurrencyNameTO(String bankCurrencyNameTO) {
		this.bankCurrencyNameTO = bankCurrencyNameTO;
	}

	public BigDecimal getCurrencyIdTO() {
		return currencyIdTO;
	}

	public void setCurrencyIdTO(BigDecimal currencyIdTO) {
		this.currencyIdTO = currencyIdTO;
	}

	public Boolean getBoobankCurrencyTO() {
		return boobankCurrencyTO;
	}

	public void setBoobankCurrencyTO(Boolean boobankCurrencyTO) {
		this.boobankCurrencyTO = boobankCurrencyTO;
	}

	public Boolean getBooSelectbankCurrencyTO() {
		return booSelectbankCurrencyTO;
	}

	public void setBooSelectbankCurrencyTO(Boolean booSelectbankCurrencyTO) {
		this.booSelectbankCurrencyTO = booSelectbankCurrencyTO;
	}
	public BigDecimal getBankIdTO() {
		return bankIdTO;
	}

	public void setBankIdTO(BigDecimal bankIdTO) {
		this.bankIdTO = bankIdTO;
	}

	
	public void setBankLstTO(List<BankApplicability> bankLstTO) {
		this.bankLstTO = bankLstTO;
	}


	public List<BankApplicability> getRmBankLstTOMaintain() {
		return rmBankLstTOMaintain;
	}

	public void setRmBankLstTOMaintain(List<BankApplicability> rmBankLstTOMaintain) {
		this.rmBankLstTOMaintain = rmBankLstTOMaintain;
	}

	public String getCurrencyNameTO() {
		return currencyNameTO;
	}

	public void setCurrencyNameTO(String currencyNameTO) {
		this.currencyNameTO = currencyNameTO;
	}
	

	public BigDecimal getBankCurrencyIdTO() {
		return bankCurrencyIdTO;
	}

	public void setBankCurrencyIdTO(BigDecimal bankCurrencyIdTO) {
		this.bankCurrencyIdTO = bankCurrencyIdTO;
	}
	
	public BigDecimal getFinancialYearId() {
		return financialYearId;
	}

	public void setFinancialYearId(BigDecimal financialYearId) {
		this.financialYearId = financialYearId;
	}

	public boolean isSalesCheckbox() {
		return salesCheckbox;
	}

	public void setSalesCheckbox(boolean salesCheckbox) {
		this.salesCheckbox = salesCheckbox;
	}

	public boolean isPurchaseCheckbox() {
		return purchaseCheckbox;
	}

	public void setPurchaseCheckbox(boolean purchaseCheckbox) {
		this.purchaseCheckbox = purchaseCheckbox;
	}

	
	
	public BigDecimal getShowAvilableBalance() {
		return showAvilableBalance;
	}

	public void setShowAvilableBalance(BigDecimal showAvilableBalance) {
		this.showAvilableBalance = showAvilableBalance;
	}

	
	public BigDecimal getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(BigDecimal docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}


	public void setDocumentSerialIdNumber(BigDecimal documentSerialIdNumber) {
		this.documentSerialIdNumber = documentSerialIdNumber;
	}


	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}
	
	
	//Bank Transfer Info Start

	 public Date getDate3() {
	        return date3;
	    }
	 
	    public void setDate3(Date date3) {
	        this.date3 = date3;
	    }
	     
	   

	public BigDecimal getBnkTrFromExchangeRate() {
		return bnkTrFromExchangeRate;
	}

	public void setBnkTrFromExchangeRate(BigDecimal bnkTrFromExchangeRate) {
		this.bnkTrFromExchangeRate = bnkTrFromExchangeRate;
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

	public String getBnkValueDate() {
		return bnkValueDate;
	}

	public void setBnkValueDate(String bnkValueDate) {
		this.bnkValueDate = bnkValueDate;
	}
	
	public String getBnkTrFromAccNo() {
		return bnkTrFromAccNo;
	}

	public void setBnkTrFromAccNo(String  bnkTrFromAccNo) {
		this.bnkTrFromAccNo = bnkTrFromAccNo;
	}
	
	public BigDecimal getBnkTrFromFCAmount() {
		return bnkTrFromFCAmount;
	}

	public void setBnkTrFromFCAmount(BigDecimal bnkTrFromFCAmount) {
		this.bnkTrFromFCAmount = bnkTrFromFCAmount;
	}
	
	public BigDecimal getBnkTrFromFCAmountTemp() {
		return bnkTrFromFCAmountTemp;
	}

	public void setBnkTrFromFCAmountTemp(BigDecimal bnkTrFromFCAmountTemp) {
		this.bnkTrFromFCAmountTemp = bnkTrFromFCAmountTemp;
	}

	public BigDecimal getBnkTrFromLocalAmount() {
		return bnkTrFromLocalAmount;
	}

	public void setBnkTrFromLocalAmount(BigDecimal bnkTrFromLocalAmount) {
		this.bnkTrFromLocalAmount = bnkTrFromLocalAmount;
	}
	public String getBankTrFromInstructionDesc() {
		return bankTrFromInstructionDesc;
	}

	public void setBankTrFromInstructionDesc(String bankTrFromInstructionDesc) {
		this.bankTrFromInstructionDesc = bankTrFromInstructionDesc;
	}
	
	public String getBankTrFromInstrunction() {
		return bankTrFromInstrunction;
	}

	public void setBankTrFromInstrunction(String bankTrFromInstrunction) {
		this.bankTrFromInstrunction = bankTrFromInstrunction;
	}
	//Bank Transfer Info End

	public String getBankTrToInstructionDesc() {
		return bankTrToInstructionDesc;
	}

	public void setBankTrToInstructionDesc(String bankTrToInstructionDesc) {
		this.bankTrToInstructionDesc = bankTrToInstructionDesc;
	}

	public String getBnkTrToAccNo() {
		return bnkTrToAccNo;
	}

	public void setBnkTrToAccNo(String bnkTrToAccNo) {
		this.bnkTrToAccNo = bnkTrToAccNo;
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

	public String getBankTrToInstrunction() {
		return bankTrToInstrunction;
	}

	public void setBankTrToInstrunction(String bankTrToInstrunction) {
		this.bankTrToInstrunction = bankTrToInstrunction;
	}

	public String getBnkInfoDate() {
		return returnFormattedDate(new Date());
	}

	public void setBnkInfoDate(String bnkInfoDate) {
		this.bnkInfoDate = bnkInfoDate;
	}



	public String getBnkTrFromAccName() {
		return bnkTrFromAccName;
	}

	public void setBnkTrFromAccName(String bnkTrFromAccName) {
		this.bnkTrFromAccName = bnkTrFromAccName;
	}

	public String getBnkTrToAccName() {
		return bnkTrToAccName;
	}

	public void setBnkTrToAccName(String bnkTrToAccName) {
		this.bnkTrToAccName = bnkTrToAccName;
	}

	

	public Date getBnkTrInfoValueDate() {
		return bnkTrInfoValueDate;
	}

	public void setBnkTrInfoValueDate(Date bnkTrInfoValueDate) {
		this.bnkTrInfoValueDate = bnkTrInfoValueDate;
	}


	public BigDecimal getBanlanceAmonut() {
		return banlanceAmonut;
	}

	public void setBanlanceAmonut(BigDecimal banlanceAmonut) {
		this.banlanceAmonut = banlanceAmonut;
	}

	public BigDecimal getBanFCAmtForCal() {
		return banFCAmtForCal;
	}

	public void setBanFCAmtForCal(BigDecimal banFCAmtForCal) {
		this.banFCAmtForCal = banFCAmtForCal;
	}

	public String getBankTrToInstrunctionName() {
		return bankTrToInstrunctionName;
	}

	public void setBankTrToInstrunctionName(String bankTrToInstrunctionName) {
		this.bankTrToInstrunctionName = bankTrToInstrunctionName;
	}

	
	public List<BankTransferBeanDataTable> getBankTransferBeanDataTableLst() {
		return bankTransferBeanDataTableLst;
	}

	public void setBankTransferBeanDataTableLst(
			List<BankTransferBeanDataTable> bankTransferBeanDataTableLst) {
		this.bankTransferBeanDataTableLst = bankTransferBeanDataTableLst;
	}

	public List<StandardInstruction> getPstandardInstrnforBnkTrTo() {
		return pstandardInstrnforBnkTrTo;
	}

	public void setPstandardInstrnforBnkTrTo(
			List<StandardInstruction> pstandardInstrnforBnkTrTo) {
		this.pstandardInstrnforBnkTrTo = pstandardInstrnforBnkTrTo;
	}

	public List<StandardInstruction> getPstandardInstrnforPurchase() {
		return pstandardInstrnforPurchase;
	}

	public void setPstandardInstrnforPurchase(
			List<StandardInstruction> pstandardInstrnforPurchase) {
		this.pstandardInstrnforPurchase = pstandardInstrnforPurchase;
	}

	public List<BankAccountDetails> getLstAccountNumber() {
		return lstAccountNumber;
	}

	public void setLstAccountNumber(List<BankAccountDetails> lstAccountNumber) {
		this.lstAccountNumber = lstAccountNumber;
	}

	public List<BankAccountDetails> getCurrencyOfBankTO() {
		return currencyOfBankTO;
	}

	public void setCurrencyOfBankTO(List<BankAccountDetails> currencyOfBankTO) {
		this.currencyOfBankTO = currencyOfBankTO;
	}

	public List<BankAccountDetails> getCurrencyOfBank() {
		return currencyOfBank;
	}

	public void setCurrencyOfBank(List<BankAccountDetails> currencyOfBank) {
		this.currencyOfBank = currencyOfBank;
	}

	public void setBankLst(List<BankApplicability> bankLst) {
		this.bankLst = bankLst;
	}

	public List<BankAccountDetails> getLstBnkToAccountNumber() {
		return lstBnkToAccountNumber;
	}

	public void setLstBnkToAccountNumber(
			List<BankAccountDetails> lstBnkToAccountNumber) {
		this.lstBnkToAccountNumber = lstBnkToAccountNumber;
	}
	
	public void setCurrencyListFromDB(List<BankAccountDetails> currencyListFromDB) {
		this.currencyListFromDB = currencyListFromDB;
	}

	public List<BankAccountDetails> getCurrencyListFromDB() {
		return currencyListFromDB;
	}
	
	
	public String getBnkInfoCrDtForUpdate() {
		return bnkInfoCrDtForUpdate;
	}

	public void setBnkInfoCrDtForUpdate(String bnkInfoCrDtForUpdate) {
		this.bnkInfoCrDtForUpdate = bnkInfoCrDtForUpdate;
	}

	public String getBnkInfoCrByForUpdate() {
		return bnkInfoCrByForUpdate;
	}

	public void setBnkInfoCrByForUpdate(String bnkInfoCrByForUpdate) {
		this.bnkInfoCrByForUpdate = bnkInfoCrByForUpdate;
	}
	public String getCompanyListFromDB() {
		  List<CompanyMasterDesc> data=null;
		  try{
		data=igeneralService.getCompanyList(sessionStateManage.getCompanyId(),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		setCompanyId(data.get(0).getFsCompanyMaster().getCompanyId());
		setCompanyName(data.get(0).getCompanyName());
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return null;       
		    }
		return data.get(0).getCompanyName();
	}
	
	public String getDocumentDescription() {
		  try{
		documentDesc=igeneralService.getDocument(new BigDecimal(71),new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1"));
		for(Document des:documentDesc)
		{
			setDocumentNo(des.getDocumentID());
			setDocumentDescription(des.getDocumentDesc());
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return null;       
		    }
		return documentDescription;
	}

	

	public List<BankApplicability> getBankLst() {
		  List<BankApplicability> lstBankApplicability=null;
		  try{
			    lstBankApplicability=igeneralService.getCorrespondingLocalFundingBanks(sessionStateManage.getCountryId());
	if(lstBankApplicability.size()>0){
		for(BankApplicability bankApplicability:lstBankApplicability){
			if(bankApplicability.getBankMaster().getBankId().equals(getBankId())){
				setBankName(bankApplicability.getBankMaster().getBankFullName());
			}
			mapBankListMaintain.put(bankApplicability.getBankMaster().getBankId(), bankApplicability.getBankMaster().getBankFullName());
			
		}
}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return null;       
		    }
		return lstBankApplicability;
	}
	

	
	public int getFinaceYear() {
		
		try{
			List<UserFinancialYear> financialYearList = igeneralService.getDealYear(new Date());
			log.info("financialYearList :"+financialYearList.size());
			if(financialYearList!=null && financialYearList.size()>0){
				if(financialYearList.get(0).getFinancialYear()!=null){
			   finaceYear = Integer.parseInt(financialYearList.get(0).getFinancialYear().toString());
				}
			financialYearId=financialYearList.get(0).getFinancialYearID();
			setFinancialYearId(financialYearId);
			setFinaceYear(finaceYear);
			}
		}catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return 0;       
		    }
		return finaceYear;
	}


	public void setFinaceYear(int finaceYear) {
		this.finaceYear = finaceYear;
	}

	 public void onDateSelect(SelectEvent event) {
	        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	        setBnkValueDate(format.format(event.getObject()));
	       
	    }
	public BigDecimal getDocumentSerialIdNumber() {
		  String documentSerialIdNumber=null;
		  try{
			    documentSerialIdNumber=igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_BANK_TRANSFER), finaceYear,processIn,sessionStateManage.getCountryBranchCode());
			    setDocumentSerialIdNumber(new BigDecimal(documentSerialIdNumber));
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return null;       
			    }
		return new BigDecimal(documentSerialIdNumber);
	
	}
	
	public void noInstructionsFound(){
		
		setBankIdTO(null);
		setBnkTrToAccNo(null);
		setBnkTrToAccNoForUpdate(null);
		setBankId(null);
		setBnkTrFromAccNo(null);
		setBnkTrFromAccNoForUpdate(null);
		setCurrencyId(null);
		setCurrencyIdUpdate(null);
		setBnkTrFromExchangeRate(null);
		setPurchaseCheckbox(false);
		setBnkTrToExchangeRate(null);
		setSalesCheckbox(false);
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bankTrFromOther()
	{
		setBnkTrFromExchangeRate(null);
		setBnkTrToExchangeRate(null);
		setBankTrFromInstrunction(null);
		setBankTrFromInstrunctionForUpdate(null);
		setBnkTrFromAccNoForUpdate(null);
		setBankTrFromInstructionDesc(null);
		setBnkTrFromFCAmount(null);
		//setBnkTrFromExchangeRate(null);
		setBnkTrFromLocalAmount(null);
		setBankStandardInstruction(true);
		//check box update
		setBnkTrFromAccNo(null);
		setCurrencyId(null);
		setCurrencyIdUpdate(null);
		setCurrencyIdTOForUpdate(null);
		setCurrencyIdTO(null);
		setPurchaseCheckbox(false);
		setBnkTrToFCAmount(null);
		setBnkTrToExchangeRate(null);
		setBnkTrToLocalAmount(null);
		setBnkTrToAccNo(null);
		setBnkTrToAccNoForUpdate(null);
		setBankTrToInstrunction(null);
		setBankTrToInstrunctionForUpdate(null);
		setBankTrToInstructionDesc(null);
		setBankLstTO(null);
		setBankIdTO(null);
		setTobankStandardInstruction(true);
		pstandardInstrnforBnkTrTo.clear();
		currencyOfBank.clear();
		lstAccountNumber.clear();
		lstBnkToAccountNumber.clear();
		setSalesCheckbox(false);
		populateCurrencyBasedOnBankForFromPanel();
		populateBankToBank();
	}
	
	
	public void populateBankToBank(){
		try{
		List<BigDecimal> banklist = new ArrayList<BigDecimal>();
		List<BigDecimal> duplicateCheck = new ArrayList<BigDecimal>();
		List<BankApplicability> bankApplicabilityList =bankTransferService.getCorrespondingLocalFundingBanks(sessionStateManage.getCountryId(),getBankId(),getCurrencyId());
		if(bankApplicabilityList.size()>0){
		for(BankApplicability bankapp:bankApplicabilityList){
			banklist.add(bankapp.getBankMaster().getBankId());
		}
		}
		if(getCurrencyId()!=null){
		List<BankAccountDetails> bankAccounDetaiList = bankTransferService.getAccountDetailsListBasedOnSelectedCurrency(banklist, getCurrencyId());
		if(bankAccounDetaiList.size()>0){
		for(BankAccountDetails bankAccDet:bankAccounDetaiList){
			if(bankAccDet.getFsCurrencyMaster().getCurrencyId()!=null){
			if(bankAccDet.getFsCurrencyMaster().getCurrencyId().equals(getCurrencyId())){
			if(!duplicateCheck.contains(bankAccDet.getExBankMaster().getBankId())){
				duplicateCheck.add(bankAccDet.getExBankMaster().getBankId());
			}
			}
	 }
		}
		}
		
		List<BankApplicability> bankApplicabilityFinalList = new ArrayList<BankApplicability>();
		
		for(BankApplicability bankapp:bankApplicabilityList){
			if(duplicateCheck.contains(bankapp.getBankMaster().getBankId())){
				bankApplicabilityFinalList.add(bankapp);
			}
			
		}
		if(bankApplicabilityFinalList.size()>0){
		setBankLstTO(bankApplicabilityFinalList);
		}
		}
		}catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return ;       
			    }
	}
	
	
	public List<BankApplicability> getBankLstTO() {
		return bankLstTO;
	}
	
	public void bankChange() {

		setCurrencyId(null);
		setBoobankCurrency(true);
		setBooSelectbankCurrency(false);
		
	}
	
	public void bankTrFromStandardInstruction()
	{
		  try{
		setLstAccountNumber(null);
		if(lstAccountNumber!=null && lstAccountNumber.size()>0)
		{
			lstAccountNumber.clear();
		}
		populateAccountNumber();
		
		if(getBankId()!=null && getCurrencyId()!=null && getBnkTrFromAccNo()!=null){
		BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
		setPstandardInstrnforPurchase(standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankId(),getCurrencyId(),bankAccountDetId,Constants.SD));
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return ;       
			    }
		
	}
	
	public void populateAccountNumber() throws Exception {
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankId(), getCurrencyId());
		setLstAccountNumber(ptabledata);
	}
	
	public void standardInstrunctionDescription()
	{
		  try{
		if(getBankId()==null || getCurrencyId()==null || getBnkTrFromAccNo()==null || getBankTrFromInstrunction()==null){

		}else{
			
			 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
			 
			List<StandardInstruction> purchaseStandrdInstrnDesc = standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankId(),getCurrencyId(),bankAccountDetId,Constants.SD);
			if(purchaseStandrdInstrnDesc.size() != 0){
				//to work checkbox
				setBankStandardInstruction(false);
				for (StandardInstruction standardInstruction : purchaseStandrdInstrnDesc) {
					if(getBankTrFromInstrunction().equals(standardInstruction.getIntructionType())){
						setBankTrFromInstructionDesc(standardInstruction.getInstructionDescription());
						setPurchaseCheckbox(false);
						setBankStandardInstruction(false);
					}
				} 	
				fetchStandInstrnForBank();
			}else{
				setBankTrFromInstructionDesc(null);
				//to work checkbox
			}  // setPurchaseCheckbox(false);
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}
	
	public void standardInstrunctionDescriptionBnkTrTo()
	{
		  try{
		if(getBankIdTO()==null || getCurrencyIdTO()==null || getBnkTrToAccNo()==null || getBankTrToInstrunction()==null){
			
		}else{
		
			BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
		List<StandardInstruction> purchaseStandrdInstrnDesc = standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankIdTO(),getCurrencyIdTO(),bankAccountDetId,Constants.PD);
		
		if(purchaseStandrdInstrnDesc.size() != 0){
			
			for (StandardInstruction standardInstruction : purchaseStandrdInstrnDesc) {
				if(getBankTrToInstrunction()!=null){
				if(getBankTrToInstrunction().equals(standardInstruction.getIntructionType())){
					setBankTrToInstructionDesc(standardInstruction.getInstructionDescription());
					setBankTrToInstrunctionName(standardInstruction.getIntructionType());
					setTobankStandardInstruction(false);
					setSalesCheckbox(false);
				}
				}
			}
			
			fetchStandInstrnToBank();
			
		}else{
			setBankTrToInstructionDesc(null);
		}   //setSalesCheckbox(false);
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
		//addBankTransferFromRecordsToDataTable();
	}
	
	private BigDecimal accBal;
	
	public BigDecimal getAccBal() {
		return accBal;
	}

	public void setAccBal(BigDecimal accBal) {
		this.accBal = accBal;
	}

		//to validate fcamount based on fcbalance bynag
		public void checkFCAmount(AjaxBehaviorEvent event) {
			  try{
			boolean isFcAmtChange=true;//changeFCAmount();
			if(isFcAmtChange)
			{ 
				BigDecimal fcBalance=igeneralService.getForeignCurrencyBalanceFromAccDetailID(getBnkTrFromAccNo());
				BigDecimal enterFCAmt=getBnkTrFromFCAmount();
				if(fcBalance!=null && enterFCAmt!=null)
				{
					 if(enterFCAmt.compareTo(new BigDecimal(0))==0)
					 {
							RequestContext context = RequestContext.getCurrentInstance();
							setBnkTrFromFCAmount(null);
							context.execute("balanceNotAvialble.show();"); 
					 }else{
						 BigDecimal accBalShow = fcBalance;
							setAccBal(accBalShow);
							int result=getBnkTrFromFCAmount().compareTo(accBal);

							if(result==1){
								RequestContext context = RequestContext.getCurrentInstance();
								context.execute("succ.show();"); 
								setBnkTrFromFCAmount(null);	
							}
							setBnkTrFromFCAmountTemp(getBnkTrFromFCAmount());
							//nag today change below one line
							calculateLocalAmtForBankFrom();
							populateFCAmount();
							changeFcBalanceAmount();
					 }
					
				}else
				{
					RequestContext context = RequestContext.getCurrentInstance();
					setBnkTrFromFCAmount(null);
					setBnkTrFromLocalAmount(null);
					context.execute("balanceNotAvialble.show();"); 
				}
				
			}
			  }catch(Exception exception){
				    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				    setErrorMessage(exception.getMessage()); 
				    RequestContext.getCurrentInstance().execute("error.show();");
				    return;       
				    }	
		
		}
		public void checkFCAmount1(AjaxBehaviorEvent event) {
		populateFCAmountankTrTO();
		calculateLocalAmtForBankTrTO();
		calculateBalanceAmount();
			
		}
		
	public void populateFCAmount()
	{
		for(BankAccountDetails  bnkAcDtls:getLstAccountNumber())
		{
			if(bnkAcDtls.getFundGlno()!=null){
			if(bnkAcDtls.getFundGlno().equals(getBnkTrFromAccNo()))
			{
				setBnkTrFromAccName(bnkAcDtls.getFundGlno());
			}
			}
		}
	}
	
	public void populateFCAmountankTrTO()
	{
		for(BankAccountDetails  bnkAcDtls:getLstBnkToAccountNumber())
		{
			if(bnkAcDtls.getFundGlno()!=null){
			if(bnkAcDtls.getFundGlno().equals(getBnkTrToAccNo()))
			{
				setBnkTrToAccName(bnkAcDtls.getFundGlno());
			}
			}
		}
		
	}
	
	public void bankTrToStandardInstruction()
	{
		try{
		populateAccountNumberForBankTrTo();
		if(getBankId()!=null && getCurrencyId()!=null && getBnkTrToAccNo()!=null){
		 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
		setPstandardInstrnforBnkTrTo(standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankIdTO(),getCurrencyIdTO(),bankAccountDetId,Constants.PD));
		}
		
		for(BankApplicability bankApplicability:getBankLstTO()){
			if(bankApplicability.getBankMaster().getBankId()!=null){
			if(bankApplicability.getBankMaster().getBankId().equals(getBankIdTO())){
				setBankNameTo(bankApplicability.getBankMaster().getBankFullName());
		 }
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
	}
	
	public void populateAccountNumberForBankTrTo() throws Exception {
		List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankIdTO(), getCurrencyIdTO());
		setLstBnkToAccountNumber(ptabledata);
	}
	
	
	public void addRecordsToDataTable(){

		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			return;
		}
		boolean checkCurrency=currencyCheck();
		if(checkCurrency)
		{
			RequestContext.getCurrentInstance().execute("checkCurrency.show();");
			return;
		}
		if(isSalesCheckbox() && isPurchaseCheckbox()){
			setBooRenderDataTable(true);
			setBooDisplayAddNewTrasLink(false);
			setBooRenderBalancePanelPanel(true);
			setBooRenderSavePanel(true);
			addBankTransferToRecordsToDataTable();
			clearBankTODetails();
			setBanlanceAmonut(getBanFCAmtForCal());
			setRenderDataTableDelete(true);
			setBooRenderEditColumnForFirstTimeSave(true);
			setDisableDataTableEdit(false);
		} else {
			RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
		}
		
	}
	
	
	public boolean calculateBalanceAmount()
	{
		boolean isAmtAllowed=false;
		if(getBnkTrFromFCAmount()!=null && getBnkTrToFCAmount()!=null)
		{
			if(bankTransferBeanDataTableLst==null || bankTransferBeanDataTableLst.size()==0)
			{
				setBanlanceAmonut(null);
			}
			BigDecimal bnkFromFCAmt=getBnkTrFromFCAmount();
			BigDecimal bnkTrToFCAmount= getBnkTrToFCAmount();
			
			BigDecimal toTalAmount=null;
			BigDecimal addFcBalAmt=null;
			if(getBanlanceAmonut()!=null)
			{
				BigDecimal fcBalAmt=getBanlanceAmonut();
				if(bnkFromFCAmt!=null){
				toTalAmount=bnkFromFCAmt.subtract(fcBalAmt);
				addFcBalAmt=toTalAmount.add(bnkTrToFCAmount);
				}
				
			}else{
				addFcBalAmt = bnkTrToFCAmount;
			}
			
			
			int retval= bnkFromFCAmt.compareTo(addFcBalAmt);
			BigDecimal avilableBalance= bnkFromFCAmt.subtract(addFcBalAmt);//34000 -25000 = 9000   25000+ 9000 
			if(retval>=0)
			{
				setBanFCAmtForCal(avilableBalance);
				isAmtAllowed=true;
			}else
			{
				isAmtAllowed=false;
				setShowAvilableBalance(null);
				setShowAvilableBalance(bnkFromFCAmt);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("showFcBal.show();"); 
				setBnkTrToFCAmount(null);
				setBnkTrToLocalAmount(null);
			}
			
		}
		return isAmtAllowed;
	}
	
	public void addBankTransferFromRecordsToDataTable()
	{
		  try{
		BankTransferBeanDataTable bnkTransBeanDataTable = new BankTransferBeanDataTable();
		bnkTransBeanDataTable.setBankName(getBankName());
		bnkTransBeanDataTable.setBankId(getBankId());
		bnkTransBeanDataTable.setAccountNo(getBnkTrFromAccName());
		bnkTransBeanDataTable.setAccountNo(getBnkTrFromAccNo());
		bnkTransBeanDataTable.setCurrencyId(getCurrencyId());
		BigDecimal accountDetailsPk =bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
		if(accountDetailsPk!=null){
		bnkTransBeanDataTable.setAccountDtlId(accountDetailsPk);
		}

		bnkTransBeanDataTable.setFcAmt(getBnkTrFromFCAmount() == null  ? "0": getBnkTrFromFCAmount().toPlainString());
		bnkTransBeanDataTable.setFcAmount(getBnkTrFromFCAmount());
		bnkTransBeanDataTable.setExChangeRate(getBnkTrFromExchangeRate() == null  ? "0": getBnkTrFromExchangeRate().toPlainString());
		bnkTransBeanDataTable.setLocalAmount(getBnkTrFromLocalAmount());
		bnkTransBeanDataTable.setLocalAmt(getBnkTrFromLocalAmount() == null  ? "0": getBnkTrFromLocalAmount().toPlainString());
		bnkTransBeanDataTable.setBankTrFromInstrunction(getBankTrFromInstrunction() == null  ? "": getBankTrFromInstrunction());
		bnkTransBeanDataTable.setInstDescription(getBankTrFromInstructionDesc());
		bnkTransBeanDataTable.setLineType(Constants.BF);
		if(bankTransferFromBeanLst!=null)
		{
			bankTransferFromBeanLst.clear();
		}
		
		bankTransferFromBeanLst.add(bnkTransBeanDataTable);
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
		
	}
	
	public void addBankTransferToRecordsToDataTable()
	{
		  try{
		if(isSalesCheckbox()){
		
		BankTransferBeanDataTable bnkTransBeanDataTable = new BankTransferBeanDataTable();
		bnkTransBeanDataTable.setTresuryDtPK(getTresuryDtPK());
		bnkTransBeanDataTable.setBankName(mapBankListMaintain.get(getBankIdTO()));
		bnkTransBeanDataTable.setBankId(getBankIdTO());
		bnkTransBeanDataTable.setCurrencyId(getCurrencyIdTO());
		
		//bnkTransBeanDataTable.setAccountNo(getBnkTrToAccName() == null ? getBnkTrToAccNoForUpdate(): getBnkTrToAccName() );
		bnkTransBeanDataTable.setAccountNo(mapAountNumberMaintain.get(getBnkTrToAccNo()));
		bnkTransBeanDataTable.setGlslNo(getBnkTrToAccNo());
		BigDecimal accountDeatailId = bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
		if(accountDeatailId!=null){
			bnkTransBeanDataTable.setAccountDtlId(accountDeatailId);
		}
		bnkTransBeanDataTable.setFcAmt(getBnkTrToFCAmount() == null  ? "0": getBnkTrToFCAmount().toPlainString());
		bnkTransBeanDataTable.setFcAmount(getBnkTrToFCAmount());
		bnkTransBeanDataTable.setExChangeRate(getBnkTrToExchangeRate() == null  ? "0": getBnkTrToExchangeRate().toPlainString());
		bnkTransBeanDataTable.setLocalAmount(getBnkTrToLocalAmount());
		bnkTransBeanDataTable.setLocalAmt(getBnkTrToLocalAmount() == null  ? "0": getBnkTrToLocalAmount().toPlainString());
		bnkTransBeanDataTable.setBankTrToInstrunction(getBankTrToInstrunction() == null  ? "": getBankTrToInstrunction());
		bnkTransBeanDataTable.setLocalExchangeRate(getBnkTrToExchangeRate() == null  ? BigDecimal.ZERO: getBnkTrToExchangeRate());
		bnkTransBeanDataTable.setInstDescription(getBankTrToInstructionDesc());
		bnkTransBeanDataTable.setLineType("BT");
		bnkTransBeanDataTable.setBankTrToInstrunctionName(getBankTrToInstrunctionName() == null ? getBankTrToInstrunctionForUpdate(): getBankTrToInstrunctionName() );
		bnkTransBeanDataTable.setCreatedBy(getCreatedBy());
		bnkTransBeanDataTable.setCreatedDate(getCreatedDate());
		
		bankTransferBeanDataTableLst.add(bnkTransBeanDataTable);
		List<BankApplicability> removeBankLstTO=new ArrayList<BankApplicability>();
		List<BankApplicability> rmBankLstToMaintain;
		rmBankLstToMaintain=getRmBankLstTOMaintain();
		if (rmBankLstToMaintain==null)
		{
			 rmBankLstToMaintain=new ArrayList<BankApplicability>();
		}
		List<BankApplicability> currBankLstTO=getBankLstTO();
		removeBankLstTO.addAll(currBankLstTO);
		if(currBankLstTO!=null && currBankLstTO.size()>0)
		{
			for(int i=0 ;i<currBankLstTO.size();i++)
			{
				BankApplicability bankApplicability=currBankLstTO.get(i);
				if(bankApplicability.getBankMaster().getBankId().equals(getBankIdTO()))
				{
					if(removeBankLstTO.size()>i)
					{
						removeBankLstTO.remove(i);
						if(!rmBankLstToMaintain.contains(bankApplicability))
						{
							rmBankLstToMaintain.add(bankApplicability);
						}
						
					}
					
					
				}
			}
		}
		setBankLstTO(null);
		setBankLstTO(removeBankLstTO);
		setRmBankLstTOMaintain(rmBankLstToMaintain);
		setBooRenderDataTable(true);
		setBooRenderFCAmount(true);
		setBooRenderEditColumn(false);
		} else {
			setBooRenderDataTable(false);
			setBooRenderFCAmount(false);
			RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
		}
		  }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
		
	}
	
	public List<StandardInstructionDetails> fetchStandardInstrnDetails(BigDecimal bankId , BigDecimal currencyId , BigDecimal strndInstrnNum, BigDecimal accountDetailId, String instructionType) {
		  List<StandardInstructionDetails> cinstrndetailsfromDB=null;
		  try{
			    cinstrndetailsfromDB= fXDetailInformationService.getInstructionsFromDetails(bankId,currencyId,Constants.Yes,strndInstrnNum,accountDetailId,instructionType);
		}catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return null;       
			    }
		return cinstrndetailsfromDB;

	}

	public void saveDataTable()
	{
		  try{
		BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
		if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
		{
			return;
		}
		
		
		
		if(getBanlanceAmonut()!=null && getBanlanceAmonut().compareTo(new BigDecimal(0))==0)
		{
			
			if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
			{
				for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
				{
					if(getBankId().compareTo(bnkTransBeanDataTable.getBankId())==0)
					{
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("sameBankFromAndTo.show();");
						return;
					}
				}
			}
			
			if(getBankId() == null || getCurrencyId()==null || getBnkTrFromAccNo()==null || getBnkTrFromFCAmount()==null || getBnkTrFromLocalAmount()==null)
			{
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("mandatoryFields.show();");
				return;
			}
			
			if(isPurchaseCheckbox()){
				
			} else {
				RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
				return;
			}
			BigDecimal accBalShow =igeneralService.getForeignCurrencyBalanceFromAccDetailID(getBnkTrFromAccNo());
			setAccBal(accBalShow);
			int result=getBnkTrFromFCAmount().compareTo(accBal);

			if(result==1){
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("presentBalance.show();"); 
				setSuccessRender(false);
				setMainRenderPanel(true);
			}else
			{
				BigDecimal saveDocumentSerialID=null;
					if(getTresuryHdPK()==null)
					{
						saveDocumentSerialID= new BigDecimal(getDocumentSerialID(Constants.U));
						setDocSerialIdNumberForSave(saveDocumentSerialID);
					}
					else
					{
						saveDocumentSerialID=getBankTransferEditableRef();
						setDocSerialIdNumberForSave(saveDocumentSerialID);
					}
					
					TreasuryDealHeader treasuryDealHeader = new TreasuryDealHeader();

					// Save Application Country
					CountryMaster applicationCountry = new CountryMaster();
					applicationCountry.setCountryId(sessionStateManage.getCountryId());
					treasuryDealHeader.setFsCountryMaster(applicationCountry);

					// save Company Master
					CompanyMaster companyMaster = new CompanyMaster();
					companyMaster.setCompanyId(getCompanyId());
					treasuryDealHeader.setFsCompanyMaster(companyMaster);

					// save Document

					Document document = new Document();
					document.setDocumentID(getDocumentNo());
					treasuryDealHeader.setExDocument(document);

					// save Document Finanace year
				 
					treasuryDealHeader.setUserFinanceYear(new BigDecimal(getFinaceYear()));
			 

					treasuryDealHeader.setTreasuryDocumentNumber(saveDocumentSerialID);
					treasuryDealHeader.setDocumentDate(new SimpleDateFormat("dd/MM/yyyy").parse(getBnkInfoDate()));
					treasuryDealHeader.setAccyymm(new SimpleDateFormat("dd/MM/yyyy").parse(getCurrentDateWithFormat()));
					treasuryDealHeader.setRemarks(getBnkDescription());
					treasuryDealHeader.setValueDate(new SimpleDateFormat("dd/MM/yyyy").parse(getBnkValueDate()));

					treasuryDealHeader.setAttention(getBnkAttention());
					treasuryDealHeader.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
					// save Language Type
					LanguageType langType = new LanguageType();
					langType.setLanguageId(sessionStateManage.getLanguageId());
					treasuryDealHeader.setFsLanguageType(langType);
					treasuryDealHeader.setTreasuryDealHeaderId(getTresuryHdPK());

					

					if (getTresuryHdPK() != null
							&& getTresuryHdPK().intValue() != 0) {
						treasuryDealHeader.setCreatedBy(getTreasuryDetailBrFromCreadteBy());
						treasuryDealHeader.setCreatedDate(getTreasuryDetailBrFromCreadteDate());
						treasuryDealHeader.setModifiedBy(sessionStateManage.getUserName());
						treasuryDealHeader.setModifiedDate(new Date());
					} else {
						treasuryDealHeader.setCreatedBy(sessionStateManage.getUserName());
						treasuryDealHeader.setCreatedDate(new Date());

					}
					treasuryDealHeader.setBalanceAmount(getBanlanceAmonut());
					treasuryDealHeader.setIsActive(Constants.U);
					fXDetailInformationService.saveHeader(treasuryDealHeader);

					//SAVE BANK FROM
					int count=1;
					int stdInsMsgLineNo=1;
					for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferFromBeanLst)
					{
						TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();
						treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
						//save Bank 
						BankMaster bankMaster = new BankMaster();
						bankMaster.setBankId(getBankId());
						treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
						//save Currency
						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(getCurrencyId());
						treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
						//Save Account Number
						BigDecimal accountDetailsPk =bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
						if(accountDetailsPk!=null){
							BankAccountDetails bankAccountDetails = new BankAccountDetails();
							bankAccountDetails.setBankAcctDetId(accountDetailsPk);
						
							treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);
						}
						
						treasuryDealDetail.setFcAmount(getBnkTrFromFCAmount());
						//For Testing purpose added exchange rate one
						treasuryDealDetail.setExchange(new BigDecimal(1));
						
						
						//BigDecimal localAmountWithDecimal= GetRound.roundBigDecimal(bnkTransBeanDataTable.getLocalAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
						
						
						treasuryDealDetail.setLocalAmount(getBnkTrFromLocalAmount());
						
						treasuryDealDetail.setLocalExchangeRate(new BigDecimal(getBnkTrFromExchangeRate() == null  ? "0": getBnkTrFromExchangeRate().toPlainString()));
						
						treasuryDealDetail.setFaAccountNo(getBnkTrFromAccNo());
						treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);
						treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);
						treasuryDealDetail.setTreasuryDealDocument(document);
						treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

						treasuryDealDetail.setDocumentNumber(saveDocumentSerialID);
						treasuryDealDetail.setLineType(Constants.BF);
						treasuryDealDetail.setTreasuryDealDetailId(getTreasuryDetailBrFromPkId());

						if(getTreasuryDetailBrFromPkId()!=null && getTreasuryDetailBrFromPkId().intValue()!=0){
							treasuryDealDetail.setCreatedBy(getTreasuryDetailBrFromCreadteBy());
							treasuryDealDetail.setCreatedDate(getTreasuryDetailBrFromCreadteDate());
							treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
							treasuryDealDetail.setModifiedDate(new Date());

						}else{
							treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
							treasuryDealDetail.setCreatedDate(new Date());
						}
						List<StandardInstruction> standardInsList= standardInstructionsService.getValues(sessionStateManage.getCompanyId(), getBankId(), getCurrencyId(), accountDetailsPk, getBankTrFromInstrunction() == null  ? "": getBankTrFromInstrunction());
						if(standardInsList!=null && standardInsList.size()>0){
						treasuryDealDetail.setStandardInstruction(standardInsList.get(0).getStandardInstructionId());
						}
						//treasuryDealDetail.setStandardInstruction(getBankTrFromInstrunction());

						treasuryDealDetail.setValueDate(treasuryDealHeader.getValueDate());

						treasuryDealDetail.setLineNumber(new BigDecimal(count++));
						treasuryDealDetail.setIsActive(Constants.U);

						fXDetailInformationService.savePurchase(treasuryDealDetail);
						
						List<StandardInstructionDetails> cinstrndetailsfromDB = fetchStandardInstrnDetails(treasuryDealDetail.getTreasuryDealBankMaster().getBankId() , treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyId(), treasuryDealDetail.getStandardInstruction(),accountDetailsPk,Constants.SD);

						//int lineNumber = 1;
						bankTransferService.deleteTreasuryStandardInstruction(new BigDecimal(getFinaceYear()),saveDocumentSerialID, getTreasuryDetailBrFromPkId());
						
						for (StandardInstructionDetails stndInstrnDetails : cinstrndetailsfromDB) {
							
							TreasuryStandardInstruction treasurytandardInstruction  = new TreasuryStandardInstruction();
							//Treasury Standard Instruction Save
							//treasurytandardInstruction.setTreasuryStandardInstructionId(bnkTransBeanDataTable.getTresurySdPK());
							
							treasurytandardInstruction.setStandardInstructionNumber(treasuryDealDetail.getStandardInstruction());
							
							treasurytandardInstruction.setDucumentNumber(getDocumentNo());
							treasurytandardInstruction.setTreasurydocDocument(document);
							treasurytandardInstruction.setTreasurycomCompanyMaster(companyMaster);
							treasurytandardInstruction.setTreasDocumentFinancialYear(new BigDecimal(getFinaceYear()));
							treasurytandardInstruction.setTreasuryCountryMaster(applicationCountry);
							treasurytandardInstruction.setTreasuryLanguageType(langType);
							treasurytandardInstruction.setDucumentNumber(saveDocumentSerialID);
							treasurytandardInstruction.setLineType(Constants.BF);
							if(getTreasuryInstructionId()!=null && getTreasuryInstructionId().intValue()!=0){
								treasurytandardInstruction.setModifiedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setModifiedDate(new Date());

							}else{
								treasurytandardInstruction.setCreatedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setCreatedDate(new Date());
							}
							treasurytandardInstruction.setTreasuryDealDetail(treasuryDealDetail);

							treasurytandardInstruction.setMessageLineNumber(new BigDecimal(stdInsMsgLineNo++));
							treasurytandardInstruction.setIsActive(Constants.U);
							treasurytandardInstruction.setMessageDescription(stndInstrnDetails.getLineDescription());
							
							fXDetailInformationService.savePurchaseStandardInst(treasurytandardInstruction);

						}
						
					}

					stdInsMsgLineNo=1;
					for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
					{
						TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();
						treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
						//save Bank 
						BankMaster bankMaster = new BankMaster();
						bankMaster.setBankId(bnkTransBeanDataTable.getBankId());
						treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
						//save Currency
						CurrencyMaster currencyMaster = new CurrencyMaster();
						currencyMaster.setCurrencyId(bnkTransBeanDataTable.getCurrencyId());
						treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
						//Save Account Number
						BankAccountDetails bankAccountDetails = new BankAccountDetails();
						bankAccountDetails.setBankAcctDetId(bnkTransBeanDataTable.getAccountDtlId());
						treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);
						treasuryDealDetail.setFcAmount(bnkTransBeanDataTable.getFcAmount());
						//For Testing purpose added exchange rate one
						treasuryDealDetail.setExchange(new BigDecimal(1));
						
						//BigDecimal localAmountWithDecimal= GetRound.roundBigDecimal(bnkTransBeanDataTable.getLocalAmount(),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
						
						
						treasuryDealDetail.setLocalAmount(bnkTransBeanDataTable.getLocalAmount());

						//treasuryDealDetail.setLocalExchangeRate(new BigDecimal(bnkTransBeanDataTable.getExChangeRate()));
						treasuryDealDetail.setLocalExchangeRate(new BigDecimal(getBnkTrFromExchangeRate() == null  ? "0": getBnkTrFromExchangeRate().toPlainString()));
						
						
						treasuryDealDetail.setFaAccountNo(bnkTransBeanDataTable.getGlslNo());
						treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);
						treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);
						treasuryDealDetail.setTreasuryDealDocument(document);
						treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

						treasuryDealDetail.setDocumentNumber(saveDocumentSerialID);
						treasuryDealDetail.setLineType(bnkTransBeanDataTable.getLineType());
						treasuryDealDetail.setTreasuryDealDetailId(bnkTransBeanDataTable.getTresuryDtPK());

						if(bnkTransBeanDataTable.getTresuryDtPK()!=null && bnkTransBeanDataTable.getTresuryDtPK().intValue()!=0){
							treasuryDealDetail.setCreatedBy(bnkTransBeanDataTable.getCreatedBy());
							treasuryDealDetail.setCreatedDate(bnkTransBeanDataTable.getCreatedDate());
							treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
							treasuryDealDetail.setModifiedDate(new Date());

						}else{
							treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
							treasuryDealDetail.setCreatedDate(new Date());
						}
						
						List<StandardInstruction> standardInsList= standardInstructionsService.getValues(sessionStateManage.getCompanyId(), bnkTransBeanDataTable.getBankId(), bnkTransBeanDataTable.getCurrencyId(), bnkTransBeanDataTable.getAccountDtlId(), bnkTransBeanDataTable.getBankTrToInstrunction());
						if(standardInsList!=null && standardInsList.size()>0){
							treasuryDealDetail.setStandardInstruction(standardInsList.get(0).getStandardInstructionId());
						}
					//	treasuryDealDetail.setStandardInstruction(bnkTransBeanDataTable.getBankTrToInstrunctionName());

						treasuryDealDetail.setValueDate(treasuryDealHeader.getValueDate());
						treasuryDealDetail.setLineNumber(new BigDecimal(count++));
						treasuryDealDetail.setIsActive(Constants.U);

						fXDetailInformationService.savePurchase(treasuryDealDetail);
						
						List<StandardInstructionDetails> cinstrndetailsfromDB = fetchStandardInstrnDetails(treasuryDealDetail.getTreasuryDealBankMaster().getBankId() , treasuryDealDetail.getTreasuryDealDetailCurrencyMaster().getCurrencyId(), treasuryDealDetail.getStandardInstruction(),bnkTransBeanDataTable.getAccountDtlId(),Constants.PD);

						//int lineNumber = 1;
						bankTransferService.deleteTreasuryStandardInstruction(new BigDecimal(getFinaceYear()),saveDocumentSerialID, bnkTransBeanDataTable.getTresuryDtPK());
						
						for (StandardInstructionDetails stndInstrnDetails : cinstrndetailsfromDB) {
							
							TreasuryStandardInstruction treasurytandardInstruction  = new TreasuryStandardInstruction();
							//Treasury Standard Instruction Save
							//treasurytandardInstruction.setTreasuryStandardInstructionId(bnkTransBeanDataTable.getTresurySdPK());
							
						
							treasurytandardInstruction.setStandardInstructionNumber(treasuryDealDetail.getStandardInstruction());
							
							/*if(standardInsList!=null && standardInsList.size()>0){
								treasurytandardInstruction.setStandardInstructionNumber(standardInsList.get(0).getStandardInstructionId());
							}*/
							// treasurytandardInstruction.setStandardInstructionNumber(new BigDecimal(bnkTransBeanDataTable.getInstNumber()));
							treasurytandardInstruction.setMessageDescription(stndInstrnDetails.getLineDescription());
							treasurytandardInstruction.setDucumentNumber(getDocumentNo());
							treasurytandardInstruction.setTreasurydocDocument(document);
							treasurytandardInstruction.setTreasurycomCompanyMaster(companyMaster);
							treasurytandardInstruction.setTreasDocumentFinancialYear(new BigDecimal(getFinaceYear()));
							treasurytandardInstruction.setTreasuryCountryMaster(applicationCountry);
							treasurytandardInstruction.setTreasuryLanguageType(langType);
							treasurytandardInstruction.setDucumentNumber(saveDocumentSerialID);
							treasurytandardInstruction.setLineType(bnkTransBeanDataTable.getLineType());
							if(getTreasuryInstructionId()!=null && getTreasuryInstructionId().intValue()!=0){
								treasurytandardInstruction.setModifiedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setModifiedDate(new Date());

							}else{
								treasurytandardInstruction.setCreatedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setCreatedDate(new Date());
							}
							treasurytandardInstruction.setTreasuryDealDetail(treasuryDealDetail);
							treasurytandardInstruction.setMessageLineNumber(new BigDecimal(stdInsMsgLineNo++));
							treasurytandardInstruction.setIsActive(Constants.U);
							
							fXDetailInformationService.savePurchaseStandardInst(treasurytandardInstruction);
						}
						
					}
					
					if(getTresuryHdPK()==null)
					{
						bankTransferService.callProcedureForBankTransfer(sessionStateManage.getCountryId(), getCompanyId(), getDocumentNo(), new BigDecimal(getFinaceYear()), saveDocumentSerialID);
						
					}
					
					setSuccessRender(true);
					setMainRenderPanel(false);
					
					
					
			}
			

		}else
		{
			RequestContext.getCurrentInstance().execute("balanceCheck.show();");
			setSuccessRender(false);
			setMainRenderPanel(true);
		}
		  }catch(NullPointerException ne){
			    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("MethodName::saveDataTable");
			    RequestContext.getCurrentInstance().execute("nullPointerIdSave.show();");
			    return; 
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("errorSave.show();");
			    return;       
			    }

	}
	
	public void clearBankTODetails()
	{
		if(pstandardInstrnforBnkTrTo!=null)
		{
			pstandardInstrnforBnkTrTo.clear();
		}
		
		setBankIdTO(null);
		setBnkTrToAccNo(null);
		setBnkTrToFCAmount(null);
		//setBnkTrToExchangeRate(null);
		setBnkTrToLocalAmount(null);
		setBankTrToInstrunction(null);
		setBankTrToInstructionDesc(null);
		 setBnkTrToAccNoForUpdate(null);
		 setBankTrToInstrunctionForUpdate(null);
		 setSalesCheckbox(false);
	}
	public void clearBankFromDetails()
	{
		if(pstandardInstrnforPurchase!=null)
		{
			pstandardInstrnforPurchase.clear();
		}
		
		setBankId(null);
		setCurrencyId(null);
		setBnkTrFromAccNo(null);
		setBnkTrFromFCAmount(null);
		setBnkTrFromExchangeRate(null);
		setBnkTrFromLocalAmount(null);
		setBankTrFromInstrunction(null);
		setBankTrFromInstructionDesc(null);
		if(lstAccountNumber!=null)
		{
			lstAccountNumber.clear();
		}
		
		
	}
	public void clearBankInfoDetails()
	{
		setBnkDescription(null);
		setBnkValueDate(null);
		setBnkAttention(null);
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void clearCache(){
		clear();
		try {
			setBooRenderAddPanel(true);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "banktransfer.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void exit() throws IOException {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void exit1() throws IOException {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	private String returnFormattedDate(Date date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
		return simpleDateFormat.format(date);
	}
	

	public void calculateLocalAmtForBankFrom()
	{
	try{
		if(getBnkTrFromFCAmount()!=null && getBnkTrFromExchangeRate()!=null)
		{
			BigDecimal bnkFromExchaneRate=getBnkTrFromExchangeRate();
			BigDecimal bnkFromFCAmt=getBnkTrFromFCAmount();
 
			BigDecimal bnkFromLocalAmt=bnkFromFCAmt.multiply(bnkFromExchaneRate);
			
			BigDecimal localAmountFromWithDecimal= GetRound.roundBigDecimal(bnkFromLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
			
			setBnkTrFromLocalAmount(localAmountFromWithDecimal);
		 
		}
		  }catch(Exception exception){
		  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
	}
	
	public void calculateLocalAmtForBankTrTO()
	{
		  try{
		if(getBnkTrToFCAmount()!=null && getBnkTrToExchangeRate()!=null)
		{
			BigDecimal bnkToExchaneRate=getBnkTrToExchangeRate();
			BigDecimal bnkFromFCAmt=getBnkTrToFCAmount();
			
			BigDecimal bnkToLocalAmt=bnkToExchaneRate.multiply(bnkFromFCAmt);
			
			BigDecimal localAmountToWithDecimal= GetRound.roundBigDecimal(bnkToLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
			
			setBnkTrToLocalAmount(localAmountToWithDecimal);
		}
		  }catch(Exception exception){
				  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
		
	}

	public String getDocumentSerialID(String processIn)throws Exception{
			String documentSerialID = igeneralService.getNextDocumentReferenceNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_BANK_TRANSFER), finaceYear,processIn,sessionStateManage.getCountryBranchCode());
			return documentSerialID;
			
	}
	
	// to get the accoMMYY value
	@Deprecated
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}

		//String year = String.valueOf(new Date().getYear()).substring(1, 3);
		Date date = new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		String year =String.valueOf(calendar.get(Calendar.YEAR));

		System.out.println(Calendar.getInstance().get(Calendar.MONTH));
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH)) + "/" + year;
	}

	//CR getting Instruction and Line Number from DB
	public void getDataTableForPurchaseDetails() {

		if(getBankTrFromInstructionDesc()==null)
		{
			RequestContext.getCurrentInstance().execute("noInstructions.show();");
			setPurchaseCheckbox(false);
			return;
		}
		if(purchaseCheckbox){
			setBankStandardInstruction(true);
			RequestContext.getCurrentInstance().execute("PF('puchasedetails').show();");
		}else{
			setBankStandardInstruction(false);
		}

	}
	
	public void fetchStandInstrnForBank(){
		  try{
		if(getBankId()!=null && getCurrencyId()!=null && getBnkTrFromAccNo()!=null){

			BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
			List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(getBankId(),getCurrencyId(),isActiveforPurchase,getInstructionBankFormPk(),bankAccountDetId,Constants.SD);

			if(cinstrndetailsfromDB.size() != 0){
				setCheckboxstandardInstrnDetailsFROM(cinstrndetailsfromDB);
			}else{
				System.out.println("NO standard Instructions");
			}

		}else{
			setBankStandardInstruction(true);
		}
		  }catch(Exception exception){
				  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
	}

	
	//CR getting Instruction and Line Number from DB - Sales Details
	public void getDataTableForSalesDetails() {

		if(getBankTrToInstructionDesc()==null)
		{
			RequestContext.getCurrentInstance().execute("noInstructions1.show();");
			setSalesCheckbox(false);
			return;
		}
		if(salesCheckbox){
			setTobankStandardInstruction(true);
			RequestContext.getCurrentInstance().execute("PF('salesdetails').show();");
		}else{
			setTobankStandardInstruction(false);
		}

	}
	
	public void fetchStandInstrnToBank(){
		  try{
		if(getBankIdTO()!=null && getCurrencyIdTO()!=null && getBnkTrToAccNo()!=null){

			BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
			
			List<StandardInstructionDetails> cinstrndetailsfromDB = fXDetailInformationService.getInstructionsFromDetails(getBankIdTO(),getCurrencyIdTO(),isActiveforSales,getInstructionBankToPk(),bankAccountDetId,Constants.PD);

			if(cinstrndetailsfromDB.size() != 0){
				setCheckboxstandardInstrnDetailsTO(cinstrndetailsfromDB);
			}else{
				System.out.println("NO Standard Instructions");
			}

		}else{
			setTobankStandardInstruction(true);
		}
		  }catch(Exception exception){
				  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				  setErrorMessage(exception.getMessage()); 
				  RequestContext.getCurrentInstance().execute("error.show();");
				  return;       
				  }
	}
				
				
				
				
				

				public void setAllNullvalues(){
					setCompanyId(null);
					setCompanyName(null);
					setDocumentDescription(null);
					setBankId(null);
					setBankName(null);
					setBankCurrencyId(null);
					setBankCurrencyIdTO(null);
					setBankCurrencyName(null);
					setBankCurrencyNameTO(null);
					setCurrencyName(null);
					
					//Bank Transfer TO
					setBankIdTO(null);
					setCurrencyIdTO(null);
					setCurrencyNameTO(null);
					setBnkTrToAccNo(null);
					setBnkTrToFCAmount(null);
					setBnkTrToExchangeRate(null);
					setBnkTrToLocalAmount(null);
					setBankTrToInstrunction(null);
					setBankTrToInstructionDesc(null);
					setTresuryDtPK(null);
					//Bank Transfer From
					setBankId(null);
					setBankName("jdshfds");
					setCurrencyId(null);
					setBankCurrencyName(null);
					setBnkTrFromAccNo(null);
					setBnkTrFromFCAmount(null);
					setBnkTrFromExchangeRate(null);
					setBnkTrFromLocalAmount(null);
					setBankTrFromInstrunction(null);
					setBankTrFromInstructionDesc(null);	
							
					//For Update values null
					setBankIdForUpdate(null);
					setCurrencyIdUpdate(null);
					setBnkTrFromAccNoForUpdate(null);
					setBankTrFromInstrunctionForUpdate(null);
					setBankIdTOForUpdate(null);
					setCurrencyIdTOForUpdate(null);
					setBnkTrToAccNoForUpdate(null);
					setBankTrToInstrunctionForUpdate(null);
					setBnkValueDateForUpdate(null);
					setBnkValueDate(null);
					setBnkAttention(null);
					setBnkDescription(null);
					setTreasuryDetailBrFromPkId(null);
					setTreasuryDetailBrFromCreadteBy(null);
					setTreasuryDetailBrFromCreadteDate(null);
					setTreasuryStdBrFromPkId(null);
					setTresuryHdPK(null);
					setTresuryDtPK(null);
					setCreatedBy(null);
					setCreatedDate(null);
				}
			

				public void populateAllFiledsValues(){
					  try{
					if(bankTransferBeanDataTableLst!=null)
					{
						bankTransferBeanDataTableLst.clear();
					}
					setTreasuryDetailBrFromPkId(null);
					setTreasuryDetailBrFromCreadteBy(null);
					setTreasuryDetailBrFromCreadteDate(null);
					setTreasuryStdBrFromPkId(null);
					setDisableDataTableEdit(false);
					setPurchaseCheckbox(false);
					setSalesCheckbox(false);
					List<BigDecimal> addBankLstTO=new ArrayList<BigDecimal>();
					setAllNullvalues();
					setDisplayDynamicColor(true);
					setBooRenderBalancePanelPanel(true); 
					setBooRenderBankTransferToPanel(false);
					setUpdateBankID(null);
					TreasuryDealHeader	treasuryDealH = bankTransferService.getPopulateRecord(getBankTransferEditableRef(),getDocumentNo(),new BigDecimal(getFinaceYear()),sessionStateManage.getCompanyId());
					if(treasuryDealH!=null){
						//setBooRenderAddPanel(true);
							//Bank Transfer Info
							setTresuryHdPK( treasuryDealH.getTreasuryDealHeaderId());
							setCompanyId(treasuryDealH.getFsCompanyMaster().getCompanyId());
							setDocumentNo(treasuryDealH.getExDocument().getDocumentID());
							if(treasuryDealH.getUserFinanceYear()!=null){
							setFinaceYear(Integer.parseInt(treasuryDealH.getUserFinanceYear().toPlainString()));
							}
							setBnkInfoDate(treasuryDealH.getDocumentDate().toString());
							setBnkAttention(treasuryDealH.getAttention());
							setBnkDescription(treasuryDealH.getRemarks());
							SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
							if(treasuryDealH.getValueDate() != null){
								setBnkValueDate(format.format(treasuryDealH.getValueDate()));
							}
					        setBnkInfoCrByForUpdate(treasuryDealH.getCreatedBy());
					        if(treasuryDealH.getCreatedDate() != null){
					        	 setBnkInfoCrDtForUpdate(format.format(treasuryDealH.getCreatedDate()));
					        }
					       
							setBnkValueDateForUpdate((treasuryDealH.getValueDate()));
							setBankTransferEditableRef(getBankTransferEditableRef());
							setBanlanceAmonut(treasuryDealH.getBalanceAmount()==null ? new BigDecimal(0):treasuryDealH.getBalanceAmount());
						
						
						
						List<TreasuryDealDetail> 	treasuryDeallsList=bankTransferService.getTreasuryDealDetailInfo(getBankTransferEditableRef(),getDocumentNo(),new BigDecimal(getFinaceYear()),sessionStateManage.getCompanyId());
						for(TreasuryDealDetail treasuryDealDetl:treasuryDeallsList){
							if(treasuryDealDetl.getLineType().equals(Constants.BF)){
								setTreasuryDetailBrFromPkId(treasuryDealDetl.getTreasuryDealDetailId());
								setTreasuryDetailBrFromCreadteBy(treasuryDealDetl.getCreatedBy());
								setTreasuryDetailBrFromCreadteDate(treasuryDealDetl.getCreatedDate());
								
								setBankId(new BigDecimal((treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString()).toString()));
								List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(treasuryDealDetl.getTreasuryDealBankMaster().getBankId());
								setCurrencyListFromDB(pbankcurrency);
								setBooRenderCurrencyId(false);
								setBooRenderCurrencyIdForUpdate(true);
								setCurrencyId(new BigDecimal(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString()));
								setBnkTrFromAccNo(treasuryDealDetl.getFaAccountNo());
								setBankIdForUpdate((iSpecialCustomerDealRequestService.getBankNameForUpdate(new BigDecimal(treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString())).toString()));
								setCurrencyIdUpdate((iSpecialCustomerDealRequestService.getCurrencyForUpdate(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId())));
								String accountNumber1 = bankTransferService.getAccountNoBasedOnGlSlNumber(treasuryDealDetl.getFaAccountNo());
								//setCurrencyIdTO(new BigDecimal(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString()));
								//setCurrencyIdTOForUpdate(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString());
								if(!accountNumber1.equalsIgnoreCase("nodata")){
									setBnkTrFromAccNoForUpdate(accountNumber1);
								}
								
								setBnkTrFromFCAmount(treasuryDealDetl.getFcAmount());
								//setBnkTrFromExchangeRate(treasuryDealDetl.getLocalExchangeRate());
								//setBnkTrFromLocalAmount(treasuryDealDetl.getLocalAmount());
								populateExchangeRateFrom();
								calculateLocalAmtForBankFrom();
								List<BigDecimal> lstTreasuryStd= new ArrayList<BigDecimal>();
								
								List<TreasuryStandardInstruction> 	treasuryStandardInstrucList=bankTransferService.getTreasuryStandardInstructionInfo(getBankTransferEditableRef(),getDocumentNo(),treasuryDealDetl.getTreasuryDealDetailId(),sessionStateManage.getCompanyId());
								for(TreasuryStandardInstruction treasuryStdInstruction:treasuryStandardInstrucList){
									if(treasuryStdInstruction.getLineType().equals(Constants.BF)){	
										lstTreasuryStd.add(treasuryStdInstruction.getTreasuryStandardInstructionId());
								List<StandardInstruction>	standardInstr =standardInstructionsService.getStandInstrList(treasuryStdInstruction.getStandardInstructionNumber());
									if(standardInstr!=null && standardInstr.size()>0){	
										StandardInstruction standInstruct = standardInstr.get(0);
										setBankTrFromInstrunction(standInstruct.getIntructionType());
										setBankTrFromInstrunctionForUpdate(standInstruct.getIntructionType());
										setBankTrFromInstructionDesc(standInstruct.getInstructionDescription());
									}
									
									}
								}
								setTreasuryStdBrFromPkId(lstTreasuryStd);
								
								BankTransferBeanDataTable bankTransferBeanDataTable=new BankTransferBeanDataTable();
								bankTransferBeanDataTable.setTresuryDtPK(treasuryDealDetl.getTreasuryDealDetailId());
								bankTransferBeanDataTable.setBankId(new BigDecimal((treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString()).toString()));
								bankTransferBeanDataTable.setBankName((iSpecialCustomerDealRequestService.getBankNameForUpdate(new BigDecimal(treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString())).toString()));
								bankTransferBeanDataTable.setAccountDtlId(bankTransferService.getBankAccountDeatilsPk(treasuryDealDetl.getFaAccountNo()));
								String accountNumber = bankTransferService.getAccountNoBasedOnGlSlNumber(treasuryDealDetl.getFaAccountNo());
								if(!accountNumber.equalsIgnoreCase("nodata")){
								bankTransferBeanDataTable.setAccountNo(accountNumber);
								}
								bankTransferBeanDataTable.setFcAmount(treasuryDealDetl.getFcAmount());
								bankTransferBeanDataTable.setFcAmt(treasuryDealDetl.getFcAmount().toString());
								
								BigDecimal bnkToExchaneRate=getBnkTrFromExchangeRate();
								
								BigDecimal bnkToFCAmt=treasuryDealDetl.getFcAmount();
								if(bnkToExchaneRate != null){
									BigDecimal bnkToLocalAmt=bnkToFCAmt.multiply(bnkToExchaneRate);
									BigDecimal localAmountToithDecimal= GetRound.roundBigDecimal(bnkToLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
									bankTransferBeanDataTable.setLocalAmount(localAmountToithDecimal);
									bankTransferBeanDataTable.setLocalAmt(localAmountToithDecimal.toString());
								}
								
								bankTransferBeanDataTable.setExChangeRate(treasuryDealDetl.getExchange().toString());
								bankTransferBeanDataTable.setLocalExchangeRate(bnkToExchaneRate);
								
								bankTransferBeanDataTable.setCreatedBy(treasuryDealDetl.getCreatedBy());
								bankTransferBeanDataTable.setCreatedDate(treasuryDealDetl.getCreatedDate());
								if(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId()!=null){
								bankTransferBeanDataTable.setCurrencyId(new BigDecimal(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString()));
								}
								List<TreasuryStandardInstruction> 	treasuryStandardInstrucLst=bankTransferService.getTreasuryStandardInstructionInfo(getBankTransferEditableRef(),getDocumentNo(),treasuryDealDetl.getTreasuryDealDetailId(),sessionStateManage.getCompanyId());
								for(TreasuryStandardInstruction treasuryStdInstion:treasuryStandardInstrucLst){
									if(treasuryStdInstion.getLineType().equals("BF")){
										setInstructionBankFormPk(treasuryStdInstion.getStandardInstructionNumber());
										bankTransferBeanDataTable.setTresurySdPK(treasuryStdInstion.getTreasuryStandardInstructionId());		
										bankTransferBeanDataTable.setLineType(treasuryStdInstion.getLineType());
										
										List<StandardInstruction>	standardInstr =standardInstructionsService.getStandInstrList(treasuryStdInstion.getStandardInstructionNumber());
										if(standardInstr!=null && standardInstr.size()>0){	
											StandardInstruction standInstruct = standardInstr.get(0);
											bankTransferBeanDataTable.setBankTrToInstrunction(standInstruct.getIntructionType());
											bankTransferBeanDataTable.setBankTrToInstrunctionName(standInstruct.getIntructionType());
											bankTransferBeanDataTable.setInstDescription(standInstruct.getInstructionDescription());
										}
									}
								}
								
								bankTransferFromBeanLst.add(bankTransferBeanDataTable);

								fetchStandInstrnForBank();
								if(getInstructionBankFormPk()!=null)
								{
									setBankStandardInstruction(false);
								}
							}
							if(treasuryDealDetl.getLineType().equals("BT")){

								BankTransferBeanDataTable bankTransferBeanDataTable=new BankTransferBeanDataTable();
								bankTransferBeanDataTable.setTresuryDtPK(treasuryDealDetl.getTreasuryDealDetailId());
								bankTransferBeanDataTable.setBankId(new BigDecimal((treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString()).toString()));
								bankTransferBeanDataTable.setBankName((iSpecialCustomerDealRequestService.getBankNameForUpdate(new BigDecimal(treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString())).toString()));
					
								bankTransferBeanDataTable.setAccountDtlId(bankTransferService.getBankAccountDeatilsPk(treasuryDealDetl.getFaAccountNo()));
								String accountNumber = bankTransferService.getAccountNoBasedOnGlSlNumber(treasuryDealDetl.getFaAccountNo());
								if(!accountNumber.equalsIgnoreCase("nodata")){
									bankTransferBeanDataTable.setAccountNo(accountNumber);
								}
								bankTransferBeanDataTable.setGlslNo(treasuryDealDetl.getFaAccountNo());
								bankTransferBeanDataTable.setFcAmount(treasuryDealDetl.getFcAmount());
								bankTransferBeanDataTable.setFcAmt(treasuryDealDetl.getFcAmount().toString());
								bankTransferBeanDataTable.setExChangeRate(treasuryDealDetl.getExchange().toString());
								
								BigDecimal bnkToExchaneRate=getBnkTrFromExchangeRate();
								BigDecimal bnkToFCAmt=treasuryDealDetl.getFcAmount();
								if(bnkToExchaneRate != null){
									BigDecimal bnkToLocalAmt=bnkToFCAmt.multiply(bnkToExchaneRate);
									BigDecimal localAmountToithDecimal= GetRound.roundBigDecimal(bnkToLocalAmt,foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId())));
									bankTransferBeanDataTable.setLocalAmount(localAmountToithDecimal);
									bankTransferBeanDataTable.setLocalAmt(localAmountToithDecimal.toString());
								}
					 			
								
								bankTransferBeanDataTable.setCreatedBy(treasuryDealDetl.getCreatedBy());
								bankTransferBeanDataTable.setCreatedDate(treasuryDealDetl.getCreatedDate());
								bankTransferBeanDataTable.setLineNo(treasuryDealDetl.getLineNumber().intValue());
								bankTransferBeanDataTable.setLocalExchangeRate(bnkToExchaneRate);
								bankTransferBeanDataTable.setCurrencyId(new BigDecimal(treasuryDealDetl.getTreasuryDealDetailCurrencyMaster().getCurrencyId().toString()));
								
								List<BigDecimal> lstTreasuryStdBt= new ArrayList<BigDecimal>();
								
								List<TreasuryStandardInstruction> 	treasuryStandardInstrucLst=bankTransferService.getTreasuryStandardInstructionInfo(getBankTransferEditableRef(),getDocumentNo(),treasuryDealDetl.getTreasuryDealDetailId(),sessionStateManage.getCompanyId());
								for(TreasuryStandardInstruction treasuryStdInstion:treasuryStandardInstrucLst){									
									if(treasuryStdInstion.getLineType().equals("BT")){
										lstTreasuryStdBt.add(treasuryStdInstion.getTreasuryStandardInstructionId());
										bankTransferBeanDataTable.setTresurySdPK(treasuryStdInstion.getTreasuryStandardInstructionId());		
										bankTransferBeanDataTable.setLineType(treasuryStdInstion.getLineType());
										
										
										List<StandardInstruction>	standardInstr =standardInstructionsService.getStandInstrList(treasuryStdInstion.getStandardInstructionNumber());
										if(standardInstr!=null && standardInstr.size()>0){	
											StandardInstruction standInstruct = standardInstr.get(0);
											bankTransferBeanDataTable.setBankTrToInstrunction(standInstruct.getIntructionType());
											bankTransferBeanDataTable.setBankTrToInstrunctionName(standInstruct.getIntructionType());
											bankTransferBeanDataTable.setInstDescription(standInstruct.getInstructionDescription());
										}
									}
								}
								
								bankTransferBeanDataTable.setTreasuryStdBrToPkId(lstTreasuryStdBt);
								
								bankTransferBeanDataTableLst.add(bankTransferBeanDataTable);
								
								if(bankTransferBeanDataTableLst.size() != 0){
									setBooRenderDataTable(true);
									setBooRenderAddPanelForUpdate(false);
									setBooRenderUpdatePanel(true);
								}
								
								addBankLstTO.add(new BigDecimal((treasuryDealDetl.getTreasuryDealBankMaster().getBankId().toString()).toString()));
								
							}
						}
						setUpdateBankID(addBankLstTO);
						addBankListForTo();
						checkFCBalance();
					}else{
						setDisplayDynamicColor(false);
						RequestContext.getCurrentInstance().execute("DocNoNotFound.show();");
						setBankTransferEditableRef(null);
					}
					setRenderDataTableDelete(false);
					
					  }catch(NullPointerException ne){
						    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						    setErrorMessage("MethodName::populateAllFiledsValues");
						    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						    return; 
						    }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
				}
				
				public void updateRecord(){
					try{
					BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
					if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
					{
						return;
					}
					
					if(getBanlanceAmonut()!=null && getBanlanceAmonut().compareTo(new BigDecimal(0))==0)
					{
						try {
							TreasuryDealHeader treasuryDealHeader = new TreasuryDealHeader();
							
							treasuryDealHeader.setTreasuryDealHeaderId(getTresuryHdPK());
							// Save Application Country
							CountryMaster applicationCountry = new CountryMaster();
							applicationCountry.setCountryId(sessionStateManage.getCountryId());
							treasuryDealHeader.setFsCountryMaster(applicationCountry);

							// save Company Master
							CompanyMaster companyMaster = new CompanyMaster();
							companyMaster.setCompanyId(getCompanyId());
							treasuryDealHeader.setFsCompanyMaster(companyMaster);

							// save Document

							Document document = new Document();
							document.setDocumentID(getDocumentNo());
							treasuryDealHeader.setExDocument(document);

							treasuryDealHeader.setUserFinanceYear(new BigDecimal(getFinaceYear()));

							treasuryDealHeader .setTreasuryDocumentNumber(getBankTransferEditableRef());
							treasuryDealHeader.setDocumentDate(new SimpleDateFormat( "dd/MM/yyyy").parse(getBnkInfoDate()));
							treasuryDealHeader.setAccyymm(new SimpleDateFormat("dd/MM/yyyy") .parse(getCurrentDateWithFormat()));
							treasuryDealHeader.setRemarks(getBnkDescription());
							treasuryDealHeader.setValueDate(new SimpleDateFormat("dd/MM/yyyy") .parse(getBnkValueDate()) );
							treasuryDealHeader.setAttention(getBnkAttention());

							treasuryDealHeader.setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
							
							// save Language Type
							LanguageType langType = new LanguageType();
							langType.setLanguageId(sessionStateManage.getLanguageId());
							treasuryDealHeader.setFsLanguageType(langType);

							treasuryDealHeader.setCreatedBy(getBnkInfoCrByForUpdate());
							treasuryDealHeader.setCreatedDate(new SimpleDateFormat("dd/MM/yyyy")
							.parse(getBnkInfoCrDtForUpdate()) );
							treasuryDealHeader.setModifiedBy(sessionStateManage.getUserName());
							treasuryDealHeader.setModifiedDate(new Date());
							
							treasuryDealHeader.setBalanceAmount(getBanlanceAmonut());
							treasuryDealHeader.setIsActive(Constants.U);
							fXDetailInformationService.saveHeader(treasuryDealHeader);

							//SAVE BANK FROM
							BigDecimal treasuryBrFromPk= getTreasuryDetailBrFromPkId();
							List<BigDecimal> lstTreasuryStdBrFromPk= getTreasuryStdBrFromPkId();
							bankTransferService.saveBankTransferFrom(treasuryBrFromPk,lstTreasuryStdBrFromPk,sessionStateManage.getUserName());
							
						/*	for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferFromBeanLst)
							{
								TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();
								treasuryDealDetail.setTreasuryDealDetailId(bnkTransBeanDataTable.getTresuryDtPK());
								treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
								//save Bank 
								BankMaster bankMaster = new BankMaster();
								bankMaster.setBankId(getBankId());
								treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
								//save Currency
								CurrencyMaster currencyMaster = new CurrencyMaster();
								currencyMaster.setCurrencyId(getCurrencyId());
								treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
								//Save Account Number
								BankAccountDetails bankAccountDetails = new BankAccountDetails();
								bankAccountDetails.setBankAcctDetId(bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo()));
								treasuryDealDetail.setFaAccountNo(getBnkTrFromAccNo());
							
								treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);
								treasuryDealDetail.setFcAmount(getBnkTrFromFCAmount());
								treasuryDealDetail.setExchange(getBnkTrFromExchangeRate());
								treasuryDealDetail.setLocalAmount(getBnkTrFromLocalAmount());
								


								treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);
								treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);
								treasuryDealDetail.setTreasuryDealDocument(document);
								treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

								treasuryDealDetail.setDocumentNumber(getBankTransferEditableRef());
								treasuryDealDetail.setLineType(bnkTransBeanDataTable.getLineType());
								
								if(bnkTransBeanDataTable.getTresuryDtPK()!=null){
								treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
								treasuryDealDetail.setModifiedDate(new Date());
								treasuryDealDetail.setCreatedBy(bnkTransBeanDataTable.getCreatedBy());
								treasuryDealDetail.setCreatedDate(bnkTransBeanDataTable.getCreatedDate());
								}else{
									treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
									treasuryDealDetail.setCreatedDate(new Date());
								}
								
								List<StandardInstruction> standardInsList= standardInstructionsService.getValues(sessionStateManage.getCompanyId(), getBankId(), getCurrencyId(), bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo()), getBankTrFromInstrunction());
								if(standardInsList!=null && standardInsList.size()>0){
								treasuryDealDetail.setStandardInstruction(standardInsList.get(0).getStandardInstructionId());
								}
							//	treasuryDealDetail.setStandardInstruction(getBankTrToInstrunction());
								
								treasuryDealDetail.setValueDate(treasuryDealHeader.getValueDate());
								treasuryDealDetail.setLineNumber(new BigDecimal(1));
								treasuryDealDetail.setIsActive(Constants.U);
								
								fXDetailInformationService.savePurchase(treasuryDealDetail);
								
								TreasuryStandardInstruction treasurytandardInstruction  = new TreasuryStandardInstruction();
								//Treasury Standard Instruction Save
								treasurytandardInstruction.setTreasuryStandardInstructionId(bnkTransBeanDataTable.getTresurySdPK());
								
								if(standardInsList!=null && standardInsList.size()>0){
									treasurytandardInstruction.setStandardInstructionNumber(standardInsList.get(0).getStandardInstructionId());
								}
							//	treasurytandardInstruction.setStandardInstructionNumber(getBankTrFromInstrunction());
								treasurytandardInstruction.setMessageDescription(getBankTrFromInstructionDesc());
								treasurytandardInstruction.setDucumentNumber(getDocumentNo());
								treasurytandardInstruction.setTreasurydocDocument(document);
								treasurytandardInstruction.setTreasurycomCompanyMaster(companyMaster);
								treasurytandardInstruction.setTreasDocumentFinancialYear(new BigDecimal(getFinaceYear()));
								treasurytandardInstruction.setTreasuryCountryMaster(applicationCountry);
								treasurytandardInstruction.setTreasuryLanguageType(langType);
								treasurytandardInstruction.setDucumentNumber(getBankTransferEditableRef());
								treasurytandardInstruction.setLineType(bnkTransBeanDataTable.getLineType());
								
								if(bnkTransBeanDataTable.getTresurySdPK()!=null){
								treasurytandardInstruction.setCreatedBy(bnkTransBeanDataTable.getCreatedBy());
								treasurytandardInstruction.setCreatedDate(bnkTransBeanDataTable.getCreatedDate());
								treasurytandardInstruction.setModifiedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setModifiedDate(new Date());
								}else{
									treasurytandardInstruction.setCreatedBy(sessionStateManage.getUserName());
									treasurytandardInstruction.setCreatedDate(new Date());
								}
								treasurytandardInstruction.setTreasuryDealDetail(treasuryDealDetail);
								
								treasurytandardInstruction.setMessageLineNumber(new BigDecimal(bnkTransBeanDataTable.getLineNo()));
								treasurytandardInstruction.setIsActive(Constants.U);
								
								
								fXDetailInformationService.savePurchaseStandardInst(treasurytandardInstruction);
								
							}
							*/
							int count=2;
							int stdInsMsgLineNo=1;
							for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
							{
								TreasuryDealDetail treasuryDealDetail = new TreasuryDealDetail();
								treasuryDealDetail.setTreasuryDealHeader(treasuryDealHeader);
								//save Bank 
								treasuryDealDetail.setTreasuryDealDetailId(bnkTransBeanDataTable.getTresuryDtPK());
								BankMaster bankMaster = new BankMaster();
								bankMaster.setBankId(bnkTransBeanDataTable.getBankId());
								treasuryDealDetail.setTreasuryDealBankMaster(bankMaster);
								//save Currency
								CurrencyMaster currencyMaster = new CurrencyMaster();
								currencyMaster.setCurrencyId(bnkTransBeanDataTable.getCurrencyId());
								treasuryDealDetail.setTreasuryDealDetailCurrencyMaster(currencyMaster);
								//Save Account Number
								BankAccountDetails bankAccountDetails = new BankAccountDetails();
								bankAccountDetails.setBankAcctDetId(bnkTransBeanDataTable.getAccountDtlId());
								treasuryDealDetail.setTreasuryDealDetailBankAccountDetails(bankAccountDetails);
								
								treasuryDealDetail.setFcAmount(bnkTransBeanDataTable.getFcAmount());
								//treasuryDealDetail.setExchange(new BigDecimal(bnkTransBeanDataTable.getExChangeRate()));
								treasuryDealDetail.setExchange(new BigDecimal(1));
								treasuryDealDetail.setLocalAmount(bnkTransBeanDataTable.getLocalAmount());

								treasuryDealDetail.setFaAccountNo(bnkTransBeanDataTable.getGlslNo());
								treasuryDealDetail.setTreasuryDealCompanyMaster(companyMaster);
								treasuryDealDetail.setTreasuryDealCountryMaster(applicationCountry);
								treasuryDealDetail.setTreasuryDealDocument(document);
								treasuryDealDetail.setTreasuryDealUserFinanceYear(new BigDecimal(getFinaceYear()));

								treasuryDealDetail.setDocumentNumber(getBankTransferEditableRef());
								treasuryDealDetail.setLineType(bnkTransBeanDataTable.getLineType());
								if(bnkTransBeanDataTable.getTresuryDtPK()!=null){
								treasuryDealDetail.setModifiedBy(sessionStateManage.getUserName());
								treasuryDealDetail.setModifiedDate(new Date());
								treasuryDealDetail.setCreatedBy(bnkTransBeanDataTable.getCreatedBy());
								treasuryDealDetail.setCreatedDate(bnkTransBeanDataTable.getCreatedDate());
								}else{
									treasuryDealDetail.setCreatedBy(sessionStateManage.getUserName());
									treasuryDealDetail.setCreatedDate(new Date());
								}
								
								List<StandardInstruction> standardInsList= standardInstructionsService.getValues(sessionStateManage.getCompanyId(), bnkTransBeanDataTable.getBankId(), bnkTransBeanDataTable.getCurrencyId(), bnkTransBeanDataTable.getAccountDtlId(), bnkTransBeanDataTable.getBankTrToInstrunction());
								if(standardInsList!=null && standardInsList.size()>0){
								treasuryDealDetail.setStandardInstruction(standardInsList.get(0).getStandardInstructionId());
								}
								
							//	treasuryDealDetail.setStandardInstruction(getBankTrFromInstrunction());
								
								treasuryDealDetail.setValueDate(treasuryDealHeader.getValueDate());
								treasuryDealDetail.setLineNumber(new BigDecimal(count++));
								treasuryDealDetail.setIsActive(Constants.U);
								treasuryDealDetail.setLocalExchangeRate(new BigDecimal(getBnkTrFromExchangeRate() == null  ? "0": getBnkTrFromExchangeRate().toPlainString()));
								
								TreasuryStandardInstruction treasurytandardInstruction  = new TreasuryStandardInstruction();
								//Treasury Standard Instruction Save
								treasurytandardInstruction.setTreasuryStandardInstructionId(bnkTransBeanDataTable.getTresurySdPK());
								
								if(standardInsList!=null && standardInsList.size()>0){
									treasurytandardInstruction.setStandardInstructionNumber(standardInsList.get(0).getStandardInstructionId());
								}
								
							//	treasurytandardInstruction.setStandardInstructionNumber(new BigDecimal(bnkTransBeanDataTable.getInstNumber()));
								treasurytandardInstruction.setMessageDescription(bnkTransBeanDataTable.getInstDescription());
								treasurytandardInstruction.setDucumentNumber(getDocumentNo());
								treasurytandardInstruction.setTreasurydocDocument(document);
								treasurytandardInstruction.setTreasurycomCompanyMaster(companyMaster);
								treasurytandardInstruction.setTreasDocumentFinancialYear(new BigDecimal(getFinaceYear()));
								treasurytandardInstruction.setTreasuryCountryMaster(applicationCountry);
								treasurytandardInstruction.setTreasuryLanguageType(langType);
								treasurytandardInstruction.setDucumentNumber(getBankTransferEditableRef());
								treasurytandardInstruction.setLineType(bnkTransBeanDataTable.getLineType());
								
								if(bnkTransBeanDataTable.getTresurySdPK()!=null){
								treasurytandardInstruction.setModifiedBy(sessionStateManage.getUserName());
								treasurytandardInstruction.setModifiedDate(new Date());
								treasurytandardInstruction.setCreatedBy(bnkTransBeanDataTable.getCreatedBy());
								treasurytandardInstruction.setCreatedDate(bnkTransBeanDataTable.getCreatedDate());
								}else{
									treasurytandardInstruction.setCreatedBy(sessionStateManage.getUserName());
									treasurytandardInstruction.setCreatedDate(new Date());
								}
							
								treasurytandardInstruction.setTreasuryDealDetail(treasuryDealDetail);
								treasurytandardInstruction.setMessageLineNumber(new BigDecimal(stdInsMsgLineNo++));
								treasurytandardInstruction.setIsActive(Constants.U);
								
								
								fXDetailInformationService.savePurchase(treasuryDealDetail);
								fXDetailInformationService.savePurchaseStandardInst(treasurytandardInstruction);
								


							}
						} catch(Exception exception){
							  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
							  setErrorMessage(exception.getMessage()); 
						}
						
						if(bankTransferBeanDataTableLst!=null)
						{
							bankTransferBeanDataTableLst.clear();
						}
						
						
						setAllNullvalues();
					
						RequestContext.getCurrentInstance().execute("success.show();");
						
					}else
					{
						RequestContext.getCurrentInstance().execute("balanceCheck.show();");
						setBooRenderDataTable(true);
						return;
					}
					setBooRenderDataTable(true);
					setBankTransferEditableRef(null); 
					setTreasuryDetailBrFromPkId(null);
					setTreasuryDetailBrFromCreadteBy(null);
					setTreasuryDetailBrFromCreadteDate(null);
					setTreasuryStdBrFromPkId(null);
					
					}catch(NullPointerException ne){
						  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						  setErrorMessage("MethodName::updateRecord");
						  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						  return; 
						  }catch(Exception exception){
						  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						  setErrorMessage(exception.getMessage()); 
						  RequestContext.getCurrentInstance().execute("error.show();");
						  return;       
						  }
				}
				
				public void editInputForUpdate(){
					  try{
					setRenderDataTableDelete(false);
					setReadonly(true);
					setBooRenderEditColumn(true);
					setBooRenderEditColumnForFirstTimeSave(false);
					
					setBooDisplayAddNewTrasLink(true);
					setBooRenderOffEditIcon(false);
					setAllNullvalues();
					setBooRenderEditColumn(true);
					setBooRenderAddPanel(false);
					setBooRenderUpdatePanel(true);
					setBooRenderBankTransferToPanel(false);
					setBooBankTransferEditableRef(true);
					setBooBankTransferRef(false);
					setBooRenderAdd(false);
					setBooRenderUpdate(true);
					setBooRenderDataTable(true);
					setBooRenderSavePanel(false);
					setBooRenderExit(false);
					setBooRenderCancel(true);
					setBooRenderBalancePanelPanel(false); 
					setBooRenderBnkTrFromAccNo(false);
					setBooRenderBnkTrFromAccNoForUpdate(true);
					setBooRenderBankTrFromInstrunction(false);
					setBooRenderBankTrFromInstrunctionForUpdate(true);
					setBooRenderBnkTrToAccNo(false);
					setBooRenderBnkTrToAccNoForUpdate(true);
					setBooRenderBankTrToInstrunction(false);
					setBooRenderBankTrToInstrunctionForUpdate(true);
					setBooRenderbnkValueDate(false);
					setBooRenderbnkValueDateForUpdate(true);
				    bankTransferBeanDataTableLst.clear();	
					setBooRenderDataTable(false);
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
				
				
				public void cancelUpdateWindow(){
					setRenderDataTableDelete(true);
					setReadonly(false);
					setBooDisplayAddNewTrasLink(false);
					setBooRenderOffEditIcon(true);
					setDisplayDynamicColor(false);
					setBooRenderAddPanelForUpdate(false);
					setBooRenderEditColumn(false);
					
					setBooRenderUpdatePanel(false);
					setBooRenderBankTransferToPanel(true);
					setBooBankTransferEditableRef(false);
					setBooBankTransferRef(true);
					setBooRenderAdd(true);
					setBooRenderUpdate(false);
					setBooRenderExit(true);
					setBooRenderCancel(false);
					setBooRenderBankId(true);
					setBooRenderBankIdForUpdate(false);
					setBooRenderCurrencyId(true);
					setBooRenderCurrencyIdForUpdate(false);
					setBooRenderBnkTrFromAccNo(true);
					setBooRenderBnkTrFromAccNoForUpdate(false);
					setBooRenderBankTrFromInstrunction(true);
					setBooRenderBankTrFromInstrunctionForUpdate(false);
					setBooRenderBankIdTO(true);
					setBooRenderBankIdTOForUpdate(false);
					setBooRenderCurrencyIdTO(true);
					setBooRenderCurrencyIdTOForUpdate(false);
					setBooRenderBnkTrToAccNo(true);
					setBooRenderBnkTrToAccNoForUpdate(false);
					setBooRenderBankTrToInstrunction(true);
					setBooRenderBankTrToInstrunctionForUpdate(false);
					setBooRenderbnkValueDate(true);
					setBooRenderbnkValueDateForUpdate(false);
					setBooRenderDataTable(false);
					setBooRenderSavePanel(false);
					setBooRenderBalancePanelPanel(false);
					setAllNullvalues();
					setDate3(null);
					if(bankTransferBeanDataTableLst!=null)
					{
						bankTransferBeanDataTableLst.clear();
					}
					setBankTransferEditableRef(null);
					setPurchaseCheckbox(false);
					setSalesCheckbox(false);
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransfer.xhtml");
						setBooRenderAddPanel(true);
						setDisableDataTableEdit(false);
					}catch(Exception exception){
						  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						  setErrorMessage(exception.getMessage()); 
						  RequestContext.getCurrentInstance().execute("error.show();");
						  return;       
						  }
				}
				
				public void getUpdateForBankTransferTo(BankTransferBeanDataTable bTDataTable){
					try{
					//setBankLstTO(bankTransferService.getCorrespondingLocalFundingBanks(sessionStateManage.getCountryId(),getBankId(),getCurrencyId()));
					BigDecimal removeBankId =bTDataTable.getBankId();
					List<BankApplicability> addBankLstTO=new ArrayList<BankApplicability>();
					if(getBankLstTO()==null)
					{
						populateBankToBank();
					}
					
					addBankLstTO.addAll(getBankLstTO());
					List<BankApplicability> rmBankLstTO=getRmBankLstTOMaintain();
					List<BankApplicability> rmBankLstTOTemp=getRmBankLstTOMaintain();
					if(rmBankLstTO!=null && rmBankLstTO.size()>0)
					{
						for(int i=0 ;i<rmBankLstTO.size();i++)
						{
							BankApplicability bankApplicability=rmBankLstTO.get(i);
							if(bankApplicability.getBankMaster().getBankId().equals(removeBankId))
							{
								if(!addBankLstTO.contains(bankApplicability))
								{
									addBankLstTO.add(bankApplicability);
									rmBankLstTOTemp.remove(i);
								}
									
							}
						}
					}
					setRmBankLstTOMaintain(rmBankLstTOTemp);
					setBankLstTO(null);
					setBankLstTO(addBankLstTO);
					
					//Bank Transfer TO
					setTresuryDtPK(bTDataTable.getTresuryDtPK());
					setBankIdTO(bTDataTable.getBankId());
					setCurrencyIdTO(bTDataTable.getCurrencyId());
					populateAccountNumberDDForEdit();
					//setBnkTrToAccNo(bTDataTable.getAccountNo());
					setBnkTrToAccNo(bTDataTable.getGlslNo());
					setBankIdTOForUpdate(iSpecialCustomerDealRequestService.getBankNameForUpdate(bTDataTable.getBankId()));
					setCurrencyIdTOForUpdate((iSpecialCustomerDealRequestService.getCurrencyForUpdate(bTDataTable.getCurrencyId())));
					setBnkTrToAccNoForUpdate(bTDataTable.getAccountNo());
					setBnkTrToFCAmount(bTDataTable.getFcAmount());
					setBnkTrToExchangeRate(bTDataTable.getLocalExchangeRate());
					setBnkTrToLocalAmount(bTDataTable.getLocalAmount());
					setTresurySdPK(bTDataTable.getTresurySdPK());
					
					if(getBankIdTO()!=null && getCurrencyIdTO()!=null && getBnkTrToAccNo()!=null){
						 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
					setPstandardInstrnforBnkTrTo(standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankIdTO(),getCurrencyIdTO(),bankAccountDetId,Constants.PD));
					}
					setBankTrToInstrunction(bTDataTable.getBankTrToInstrunction());
					setBankTrToInstrunctionForUpdate(bTDataTable.getBankTrToInstrunction());
					setBankTrToInstructionDesc(bTDataTable.getInstDescription());
					setCreatedBy(bTDataTable.getCreatedBy());
					setCreatedDate(bTDataTable.getCreatedDate());
					
					BigDecimal avlBal=bTDataTable.getFcAmount().add(getBanlanceAmonut()==null ? new BigDecimal(0) : getBanlanceAmonut());
					setBanlanceAmonut(avlBal);
					}catch(NullPointerException ne){
						  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						  setErrorMessage("MethodName::getUpdateForBankTransferTo");
						  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						  return; 
						  }catch(Exception exception){
						  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						  setErrorMessage(exception.getMessage()); 
						  RequestContext.getCurrentInstance().execute("error.show();");
						  return;       
						  }

					}
				
				
				
				public void updateFCAmount(BankTransferBeanDataTable bTDataTable){
					  try{
			setBooRenderBankTransferToPanel(true);
			setDisableAddNewTransactionLink(true);
			populateBankToBank();
			List<BankApplicability> bankappliLIstForEdit = getBankLstTO();
			List<BankApplicability> bankappliLIstForEditTemp = new ArrayList<BankApplicability>();
			bankappliLIstForEditTemp.addAll(bankappliLIstForEdit);
			
			List<BankApplicability> bankappliTempLIstForEdit = getBankLstTO();
			
			List<BankApplicability> bankappliLIstForEditTempRemove = new ArrayList<BankApplicability>();
			bankappliLIstForEditTempRemove.addAll(bankappliTempLIstForEdit);
			
			List<BankApplicability> bankappliRemoveTempLIstForEdit = new ArrayList<BankApplicability>();
			List<BankTransferBeanDataTable> bankTransferBeanDataTableTempLst=new ArrayList<BankTransferBeanDataTable>();
			bankTransferBeanDataTableTempLst.addAll(bankTransferBeanDataTableLst);
			for (BankTransferBeanDataTable dataObj : bankTransferBeanDataTableTempLst) {
				for (BankApplicability bankAppObj : bankappliLIstForEditTemp) {
					if (dataObj.getBankId().compareTo(
							bankAppObj.getBankMaster().getBankId()) == 0) {
						bankappliLIstForEditTempRemove.remove(bankAppObj);
						bankappliRemoveTempLIstForEdit.add(bankAppObj);
					}
				}
			}
					setRmBankLstTOMaintain(bankappliRemoveTempLIstForEdit);
					setBankLstTO(bankappliLIstForEditTempRemove);
					getUpdateForBankTransferTo(bTDataTable);
					bankTransferBeanDataTableLst.remove(bTDataTable);
					setBooRenderAdd(true);
					setBooRenderAddPanelForUpdate(true);
					setBankIdTO(bTDataTable.getBankId());
					if(bankTransferBeanDataTableLst.size()==0){
						setBooRenderDataTable(false);
						setBooRenderFCAmount(false);
						setBooRenderBalancePanelPanel(false);
						setBooRenderSavePanel(false);
						setBooRenderUpdatePanel(false);
						setBooRenderUpdate(false);
					}else{
						setBooRenderDataTable(true);
						setBooRenderFCAmount(true);
						setBooRenderBalancePanelPanel(true);
						setBooRenderSavePanel(false);
						setBooRenderUpdatePanel(true);
					}
					setDisableDataTableEdit(true);
					getStandInstForBankTrTo(bTDataTable.getTresuryDtPK());
					if(getInstructionBankToPk()!=null)
					{
						setTobankStandardInstruction(false);
					}
					  }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
				}
				
		

				public void checkTransferAmount(){
					if(getTotalBalance()!=null&&getBnkTrToLocalAmount()!=null){
					if(!(getTotalBalance().intValue()<=getBnkTrToLocalAmount().intValue())){
						RequestContext.getCurrentInstance().execute("balCheck.show();");
					}
					}
				}
				
				public void addBankTransferToRecordsToDT()
				{
					  try{
					BigDecimal docRefNumber=getDocumentSerialIdNumber(Constants.Yes);
					if(docRefNumber.compareTo(BigDecimal.ZERO)==0)
					{
						return;
					}
					
					if(getBankIdTO()==null)
					{
						RequestContext.getCurrentInstance().execute("mandatoryFields.show();");
						return;
					}
					if(getCurrencyIdTO()==null)
					{
						RequestContext.getCurrentInstance().execute("mandatoryFields.show();");
						return;
					}
					
					if(getBnkTrToFCAmount()==null)
					{
						RequestContext.getCurrentInstance().execute("selectBnkTrToFcCheck.show();");
						return;
					}
					
					if(getBankTrToInstrunction()==null)
					{
						RequestContext.getCurrentInstance().execute("selectBnktrToInsCheck.show();");
						return;
					}
					boolean checkCurrency=currencyCheck();
					if(checkCurrency)
					{
						RequestContext.getCurrentInstance().execute("checkCurrency.show();");
						return;
					}

					if(isSalesCheckbox() && isPurchaseCheckbox()){
						
					} else {
						RequestContext.getCurrentInstance().execute("checkBoxVerify.show();");
						return;
					}

					
					calculateBalanceAmount();
					BankTransferBeanDataTable bnkTransBeanDataTable = new BankTransferBeanDataTable();
					bnkTransBeanDataTable.setTresuryDtPK(getTresuryDtPK());
					bnkTransBeanDataTable.setBankId(getBankIdTO());
					bnkTransBeanDataTable.setBankName(iSpecialCustomerDealRequestService.getBankNameForUpdate(bnkTransBeanDataTable.getBankId()));
					bnkTransBeanDataTable.setCurrencyId(getCurrencyIdTO());
					//bnkTransBeanDataTable.setAccountNo(getBnkTrToAccNo());
					String accountNumber = bankTransferService.getAccountNoBasedOnGlSlNumber(getBnkTrToAccNo());
					if(!accountNumber.equalsIgnoreCase("nodata")){
						bnkTransBeanDataTable.setAccountNo(accountNumber);
					}
					bnkTransBeanDataTable.setGlslNo(getBnkTrToAccNo());
					bnkTransBeanDataTable.setAccountDtlId(bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo()));
					bnkTransBeanDataTable.setFcAmt(getBnkTrToFCAmount().toString());
					bnkTransBeanDataTable.setFcAmount(getBnkTrToFCAmount());
					bnkTransBeanDataTable.setExChangeRate(getBnkTrToExchangeRate().toPlainString());
					bnkTransBeanDataTable.setLocalExchangeRate(getBnkTrToExchangeRate() == null  ? BigDecimal.ZERO: getBnkTrToExchangeRate());
					bnkTransBeanDataTable.setLocalAmount(getBnkTrToLocalAmount());
					if(getBnkTrToLocalAmount()!=null){
					bnkTransBeanDataTable.setLocalAmt(getBnkTrToLocalAmount().toString());
					}
					bnkTransBeanDataTable.setTresurySdPK(getTresurySdPK());
					bnkTransBeanDataTable.setBankTrToInstrunction(getBankTrToInstrunction());
					bnkTransBeanDataTable.setBankTrToInstrunctionName(getBankTrToInstrunction());
					bnkTransBeanDataTable.setInstDescription(getBankTrToInstructionDesc());
					bnkTransBeanDataTable.setLineType(Constants.BT);
					bnkTransBeanDataTable.setCreatedBy(getCreatedBy());
					bnkTransBeanDataTable.setCreatedDate(getCreatedDate());
					bankTransferBeanDataTableLst.add(bnkTransBeanDataTable);
					setTresuryDtPK(null);
					setTresurySdPK(null);
					
					List<BankApplicability> removeBankLstTO=new ArrayList<BankApplicability>();
					List<BankApplicability> rmBankLstToMaintain;
					rmBankLstToMaintain=getRmBankLstTOMaintain();
					if (rmBankLstToMaintain==null)
					{
						 rmBankLstToMaintain=new ArrayList<BankApplicability>();
					}
					List<BankApplicability> currBankLstTO=getBankLstTO();
					removeBankLstTO.addAll(currBankLstTO);
					if(currBankLstTO!=null && currBankLstTO.size()>0)
					{
						for(int i=0 ;i<currBankLstTO.size();i++)
						{
							BankApplicability bankApplicability=currBankLstTO.get(i);
							if(bankApplicability.getBankMaster().getBankId().equals(getBankIdTO()))
							{
								if(removeBankLstTO.size()>i)
								{
									removeBankLstTO.remove(i);
									if(!rmBankLstToMaintain.contains(bankApplicability))
									{
										rmBankLstToMaintain.add(bankApplicability);
									}
									
								}
								
								
							}
						}
					}
					setBankLstTO(null);
					setBankLstTO(removeBankLstTO);
					setRmBankLstTOMaintain(rmBankLstToMaintain);
					
					setBooRenderDataTable(true);
					setBooRenderFCAmount(true);
					setBankIdTOForUpdate(null);
				//	setCurrencyIdTO(null);
				//	setCurrencyIdTOForUpdate(null);
					setBnkTrToAccNoForUpdate(null);
					setBnkTrToFCAmount(null);
				//	setBnkTrToExchangeRate(null);
					setBankIdTO(null);
					setBankIdTOForUpdate(null);
					setBnkTrToAccNo(null);
					setBnkTrToLocalAmount(null);
					setBankTrToInstrunction(null);
					setBankTrToInstrunctionForUpdate(null);
					setBankTrToInstructionDesc(null);
					setBooRenderAddPanelForUpdate(false);
					setBooRenderBankTransferToPanel(false);
					setCreatedBy(null);
					setCreatedDate(null);
					setBanlanceAmonut(getBanFCAmtForCal());
					if(bankTransferBeanDataTableLst.size()==0){
						setBooRenderDataTable(false);
						setBooRenderFCAmount(false);
						setBooRenderBalancePanelPanel(false);
						setBooRenderSavePanel(false);
						setBooRenderUpdatePanel(false);
						setBooRenderUpdate(false);
					}else{
						setBooRenderDataTable(true);
						setBooRenderFCAmount(true);
						setBooRenderBalancePanelPanel(true);
						setBooRenderSavePanel(false);
						setBooRenderUpdatePanel(true);
						setBooRenderUpdate(true);
					}
					if(getBanlanceAmonut().intValue()==0){
					setDisableAddNewTransactionLink(false);
					setBooRenderBankTransferToPanel(false);
					setBooRenderAddPanelForUpdate(false);
					}else{
						setDisableAddNewTransactionLink(true);
						setBooRenderBankTransferToPanel(true);
						setBooRenderAddPanelForUpdate(true);
					}
					
					setDisableDataTableEdit(false);
					setTobankStandardInstruction(true);
					setSalesCheckbox(false);
					  }catch(NullPointerException ne){
						    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						    setErrorMessage("MethodName::addBankTransferToRecordsToDT");
						    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						    return; 
						    }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }

				}
				
				private int accNumberSize;

		public void populateAccountNumberDD() {
			  try{
					setBnkTrFromExchangeRate(null);
					setBnkTrToExchangeRate(null);
					setBnkTrFromAccNo(null);
					setBnkTrFromFCAmount(null);
					setBnkTrFromLocalAmount(null);
					setBankTrFromInstructionDesc(null);
					setPurchaseCheckbox(false);
					if(currencyOfBank.size()>0){
						setCurrencyIdTO(getCurrencyId());
						setCurrencyIdTOForUpdate(iSpecialCustomerDealRequestService.getCurrencyForUpdate(getCurrencyId())); 
						populateBankToBank();
					}

					List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankId(), getCurrencyId());

					accNumberSize = ptabledata.size();
					if(accNumberSize==1){

						for (BankAccountDetails element : ptabledata) {			
							setBnkTrFromAccNo(element.getFundGlno());
							setBnkTrFromAccNoForUpdate(element.getBankAcctNo()); 
						}
						if(getBnkTrFromAccNo()!=null){
							populateExchangeRateFrom();
						}
						setBooRenderBnkTrFromAccNoForUpdate(true);
						setBooRenderBnkTrFromAccNo(false);
					}else{
						setLstAccountNumber(ptabledata);
						setBooRenderBnkTrFromAccNoForUpdate(false);
						setBooRenderBnkTrFromAccNo(true);
					}


					findBankFromStandardInstructions();
			  }catch(NullPointerException ne){
				    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				    setErrorMessage("MethodName::populateAccountNumberDD");
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
				    }catch(Exception exception){
				    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				    setErrorMessage(exception.getMessage()); 
				    RequestContext.getCurrentInstance().execute("error.show();");
				    return;       
				    }

				}
				
				public void findBankFromStandardInstructions(){
					  try{
					if(getBankId()!=null && getCurrencyId()!=null && getBnkTrFromAccNo()!=null){
						 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
					List<StandardInstruction> standardInstruction=standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankId(),getCurrencyId(),bankAccountDetId,Constants.SD);
					accNumberSize = standardInstruction.size();
					if(accNumberSize!=0){
						if(accNumberSize==1){

							for (StandardInstruction element : standardInstruction) {	
								setInstructionBankFormPk(element.getStandardInstructionId());
								setBankTrFromInstrunction(element.getIntructionType());
								setBankTrFromInstrunctionForUpdate(element.getIntructionType());
								setBankTrFromInstructionDesc(element.getInstructionDescription());
								setBankStandardInstruction(false);
							}
							setBooRenderBankTrFromInstrunctionForUpdate(true);
							setBooRenderBankTrFromInstrunction(false);
							fetchStandInstrnForBank();
						}else{
							setPstandardInstrnforPurchase(standardInstruction);
							setBooRenderBankTrFromInstrunctionForUpdate(false);
							setBooRenderBankTrFromInstrunction(true);
						}

					}else{
						RequestContext.getCurrentInstance().execute("noInstructions.show();");
						return;
					}
					
				}
					  }catch(NullPointerException ne){
						    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						    setErrorMessage("MethodName::findBankFromStandardInstructions");
						    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						    return; 
						    }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
	
				}
				
				
				public void populateAccountNumberDDForUpdate() throws Exception {
					  try{
					if(getCurrencyIdTO()!=null && getCurrencyId() !=null && getCurrencyIdTO().compareTo(getCurrencyId())!=0)
					{
						RequestContext.getCurrentInstance().execute("currencyCheck.show();");
						setCurrencyIdTO(null);
						setCurrencyIdTOForUpdate(null);
					}else
					{
						//mapAountNumberMaintain.clear();
						List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankIdTO(), getCurrencyIdTO());
						for (BankAccountDetails element : ptabledata) {
							mapAountNumberMaintain.put(element.getFundGlno(), element.getBankAcctNo());

						}

						accNumberSize = ptabledata.size();
						if(accNumberSize==1){

							for (BankAccountDetails element : ptabledata) {			
								setBnkTrToAccNo(element.getFundGlno());
								setBnkTrToAccNoForUpdate(element.getBankAcctNo());
								setBnkTrToAccName(element.getFundGlno());
							}
							addBankTransferFromRecordsToDataTable();
							setBooRenderBnkTrToAccNoForUpdate(true);
							setBooRenderBnkTrToAccNo(false);

						}else{
							List<BankAccountDetails> removeList=new ArrayList<BankAccountDetails>();
							removeList.addAll(ptabledata);
							if(bankTransferBeanDataTableLst.size()>0){
								for(BankTransferBeanDataTable bankDTList:bankTransferBeanDataTableLst){
									for(int i=0;i<accNumberSize;i++){
										BankAccountDetails bankAc=removeList.get(i);
										if((bankDTList.getAccountDtlId()).equals(bankAc.getBankAcctDetId())){
											ptabledata.remove(i);
										}
									}
									addBankTransferFromRecordsToDataTable();
								}
							}

							setLstBnkToAccountNumber(ptabledata);
							setBooRenderBnkTrToAccNoForUpdate(false);
							setBooRenderBnkTrToAccNo(true);
						}

						for(BankApplicability bankApplicability: getBankLstTO()){
							if(bankApplicability.getBankMaster().getBankId().equals(getBankIdTO())){
								setBankNameTo(bankApplicability.getBankMaster().getBankFullName());
							}
						}
						findBankToStandardInstructions();

					}
					  }catch(NullPointerException ne){
						    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						    setErrorMessage("MethodName::populateAccountNumberDDForUpdate");
						    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						    return; 
						    }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
				}
				
				public void findBankToStandardInstructions(){
					try{
					if(getBankIdTO()!=null && getCurrencyIdTO()!=null && getBnkTrToAccNo()!=null){
						 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrToAccNo());
							List<StandardInstruction> standardInstruction=standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankIdTO(),getCurrencyIdTO(),bankAccountDetId,Constants.PD);
					accNumberSize = standardInstruction.size();
					if(accNumberSize!=0){
						if(accNumberSize==1){

							for (StandardInstruction element : standardInstruction) {	
								setInstructionBankToPk(element.getStandardInstructionId());
								setBankTrToInstrunction(element.getIntructionType());
								setBankTrToInstrunctionForUpdate(element.getIntructionType());
								setBankTrToInstructionDesc(element.getInstructionDescription());
								setTobankStandardInstruction(false);
							}
							addBankTransferFromRecordsToDataTable();
							/*	if(getBnkTrToAccNo()!=null){
						populateExchangeRateTo();
						}*/
							setBooRenderBankTrToInstrunctionForUpdate(true);
							setBooRenderBankTrToInstrunction(false);
							fetchStandInstrnToBank();
						}else{
							/*if(getBnkTrToAccNo()!=null){
						populateExchangeRateTo();
						}*/
							addBankTransferFromRecordsToDataTable();
							setPstandardInstrnforBnkTrTo(standardInstruction);
							setBooRenderBankTrToInstrunctionForUpdate(false);
							setBooRenderBankTrToInstrunction(true);

						}
					}else{
						RequestContext.getCurrentInstance().execute("noInstructions1.show();");
						return;
					}
					
					}
					}catch(Exception exception){
						  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						  setErrorMessage(exception.getMessage()); 
						  RequestContext.getCurrentInstance().execute("error.show();");
						  return;       
						  }
				}
				

				public void changeBankToValues()throws Exception{
					clearBankTransferTo();
					populateCurrencyBasedOnBankForToPanel();
					setSalesCheckbox(false);
					
				}
				//currency auto population make it as text field BY NAGARJUNA
				public void populateCurrency(){
					  try{
					List<CurrencyMaster> pbankcurrency=igeneralService.getCurrencyList();
					int bankcurrencySize = pbankcurrency.size();
						 
							if(bankcurrencySize>=0){
								for (CurrencyMaster element : pbankcurrency) {
							 
									if(element.getCurrencyId().equals(getCurrencyId())){
								    setCurrencyNameTO(element.getCurrencyName());	
									 
									}
									}
				}
					  }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
			} 
				
				
				public void populateCurrencyBasedOnBankForFromPanel(){
					  try{
					  List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getBankId());
						
						bankcurrencySize = pbankcurrency.size();
						if(bankcurrencySize==0){
							setCurrencyIdTOForUpdate(null);
							setCurrencyIdTO(null);
						}else if(bankcurrencySize==1){
							
							for (BankAccountDetails element : pbankcurrency) {
								setCurrencyId(element.getFsCurrencyMaster().getCurrencyId());
								setCurrencyIdUpdate(element.getFsCurrencyMaster().getCurrencyName());
								
								//for currency update in Bank TransfeTo panel
								setCurrencyIdTO(element.getFsCurrencyMaster().getCurrencyId());
								setCurrencyIdTOForUpdate(element.getFsCurrencyMaster().getCurrencyName()); 
								setBooRenderCurrencyIdTO(false);
								setBooRenderCurrencyIdTOForUpdate(true);
								
							}
							populateAccountNumberDD();
							//nag code today
							if(getBnkTrFromAccNo()!=null){
					        populateExchangeRateFrom();
							}
							setBooRenderCurrencyIdForUpdate(true);
							setBooRenderCurrencyId(false);
							
						}else{
							setCurrencyOfBank(pbankcurrency);
							setCurrencyIdTOForUpdate(null);
							setCurrencyIdTO(null);
							setBooRenderCurrencyIdForUpdate(false);
							setBooRenderCurrencyId(true);
							
						}
					  }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }
						
				}
				
				public void populateCurrencyBasedOnBankForToPanel()throws Exception{
			/*		  List<BankAccountDetails> pbankcurrency = fundEstimationService.getCurrencyOfBank(getBankIdTO());
						
						bankcurrencySize = pbankcurrency.size();
						if(bankcurrencySize==1){
							
							for (BankAccountDetails element : pbankcurrency) {
								if(element.getExBankMaster().getBankId().equals(getBankIdTO()))
								{
									setCurrencyIdTO(element.getFsCurrencyMaster().getCurrencyId());
									setCurrencyIdTOForUpdate(element.getFsCurrencyMaster().getCurrencyName()); 
								}
								
								
							}
						
							
						}else{
							setCurrencyIdTOForUpdate(null);
							setCurrencyListFromDB(null);
							setBooRenderCurrencyIdTO(true);
							setBooRenderCurrencyIdTOForUpdate(false);
							setCurrencyIdTO(null);
							setCurrencyListFromDB(pbankcurrency);	
							
						}
						*/
						populateAccountNumberDDForUpdate();
						
					/*	//nag code today
						if(getBnkTrToAccNo()!=null){
						populateExchangeRateTo();
						}*/
						setBooRenderCurrencyIdTO(false);
						setBooRenderCurrencyIdTOForUpdate(true);
								
				}
				
				public void createNewTransaction(){
					setBooRenderBankTransferToPanel(true);
					
					setBnkTrToAccNo(null);
					setBnkTrToAccNoForUpdate(null);
					setBnkTrToFCAmount(null);
					setBnkTrToExchangeRate(null);
					setBnkTrToLocalAmount(null);
					setBankTrToInstrunction(null);
					setBankTrToInstrunctionForUpdate(null);
					setBankTrToInstructionDesc(null);
					setDisableAddNewTransactionLink(true);
					setBooRenderAddPanelForUpdate(true);
				}
				
			//currency auto population make it as text field BY NAGARJUNA END
				
				
				public void clearBankTransferToRemoveDT()
				{
					setBnkTrToFCAmount(null);
				//	setBnkTrToExchangeRate(null);
					setBnkTrToLocalAmount(null);
					setBankTrToInstrunction(null);
					setBankTrToInstrunctionForUpdate(null);
					setBankTrToInstructionDesc(null);
			//		setCurrencyIdTOForUpdate(null);
					setBnkTrToAccNoForUpdate(null);
					setBnkTrToAccNo(null);
					setCurrencyListFromDB(null);
				//	setBnkTrToExchangeRate(null);
					setBankIdTO(null);
					setBankIdTOForUpdate(null);
				}
				public void removeBankTransferTo(BankTransferBeanDataTable bankTransBean) {
					  try{
					//clearBankTransferToRemoveDT();
					BigDecimal removeDTFCAmt =bankTransBean.getFcAmount();
					BigDecimal removeBankId =bankTransBean.getBankId();
					
					if(bankTransBean.getTresuryDtPK()!=null){
						bankTransferService.deleteTreasuryDetaill(bankTransBean.getTresuryDtPK(), sessionStateManage.getUserName(),getBankTransferEditableRef(),getDocumentNo(),new BigDecimal(getFinaceYear()),sessionStateManage.getCompanyId());
						if(bankTransBean.getLocalExchangeRate()!=null){
						setBnkTrToExchangeRate(bankTransBean.getLocalExchangeRate());
						}
						setCurrencyIdTOForUpdate(iSpecialCustomerDealRequestService.getCurrencyForUpdate(bankTransBean.getCurrencyId()));
						setCurrencyIdTO(bankTransBean.getCurrencyId());						
					}
					setBooRenderAddPanelForUpdate(true);
					
					BigDecimal fcBalAmt=getBanlanceAmonut();
					if(fcBalAmt!=null && fcBalAmt.compareTo(new BigDecimal(0))==0)
					{
						setBanlanceAmonut(removeDTFCAmt);
					}else
					{
						BigDecimal bnkFromFCAmt=getBnkTrFromFCAmount();
						BigDecimal addFCAmtDTRM=fcBalAmt.add(removeDTFCAmt);
						BigDecimal finalVlaue=bnkFromFCAmt.subtract(addFCAmtDTRM);
						setBanlanceAmonut(addFCAmtDTRM);
					}
					populateBankToBank();
					List<BankApplicability> bankLstTO1 = getBankLstTO();
					List<BankApplicability> bankLstTO = new CopyOnWriteArrayList<BankApplicability>();
					bankLstTO.addAll(bankLstTO1);
					
					List<BankTransferBeanDataTable> bankTransferBeanDataTableTempLst=new ArrayList<BankTransferBeanDataTable>();
					bankTransferBeanDataTableTempLst.addAll(bankTransferBeanDataTableLst);
					
					for(int i=0;i<bankLstTO.size();i++)
					{
						
						BankApplicability bankAppli=bankLstTO.get(i);
						
						if(bankTransBean.getBankId().compareTo(bankAppli.getBankMaster().getBankId())!=0)
						{
							for(BankTransferBeanDataTable bankDtObj : bankTransferBeanDataTableTempLst)
							{
								if(bankDtObj.getBankId().compareTo(bankAppli.getBankMaster().getBankId())==0)
								{
									bankLstTO.remove(i);
								}
							}
						}
						
						
					}
					
					setBankLstTO(null);
					setBankLstTO(bankLstTO);
					/*if(bankTransferBeanDataTableLst.size()>0 && bankTransBean.getTresuryDtPK()!=null){
						populateBankToBank();
						List<BankApplicability> bankLstTO = getBankLstTO();
						List<BankApplicability> totalToBank = new ArrayList<BankApplicability>();
						List<BankApplicability> rmBankLstTO = new ArrayList<BankApplicability>();
						List<BigDecimal> dataTableBankList = new ArrayList<BigDecimal>();
						for(BankTransferBeanDataTable bankDtObj : bankTransferBeanDataTableLst){
							
							for(BankApplicability bankAppli:bankLstTO)
							{
								if(bankDtObj.getBankId().compareTo(bankAppli.getBankMaster().getBankId())!=0)
								{
									totalToBank.add(bankAppli);
								}
							}
						}
						
						for(int i=0;i<bankLstTO.size();i++)
						{
							
							BankApplicability bankAppli=bankLstTO.get(i);
							if(bankTransBean.getBankId().compareTo(bankAppli.getBankMaster().getBankId())!=0)
							{
								for(BankTransferBeanDataTable bankDtObj : bankTransferBeanDataTableLst)
								{
									if(bankDtObj.getBankId().compareTo(bankAppli.getBankMaster().getBankId())==0)
									{
										bankLstTO.remove(i);
									}
								}
							}
							
							
						}
						
						setBankLstTO(null);
						setBankLstTO(bankLstTO);
						
						for(BankTransferBeanDataTable bankDtObj : bankTransferBeanDataTableLst){
							if(!bankDtObj.getBankId().equals(bankTransBean.getBankId())){
							dataTableBankList.add(bankDtObj.getBankId());
							}
						}
						for(BankApplicability bankAppli:bankLstTO){
							for(BigDecimal bankId:dataTableBankList){
							if(bankAppli.getBankMaster().getBankId().equals(bankId)){
								rmBankLstTO.remove(bankAppli);
							}else{
								totalToBank.add(bankAppli);
							}
							}
						}
						if(dataTableBankList.size()==0){
						setBankLstTO(null);
						setBankLstTO(bankLstTO);
						}else{
							setBankLstTO(null);
							setBankLstTO(totalToBank);
						}
						
					}else{
					List<BankApplicability> addBankLstTO=new ArrayList<BankApplicability>();
					addBankLstTO.addAll(getBankLstTO());
					List<BankApplicability> rmBankLstTO=getRmBankLstTOMaintain();
					List<BankApplicability> rmBankLstTOTemp=getRmBankLstTOMaintain();
					if(rmBankLstTO!=null && rmBankLstTO.size()>0)
					{
						for(int i=0 ;i<rmBankLstTO.size();i++)
						{
							BankApplicability bankApplicability=rmBankLstTO.get(i);
							if(bankApplicability.getBankMaster().getBankId().compareTo(removeBankId)==0)
							{
								if(!addBankLstTO.contains(bankApplicability))
								{
									addBankLstTO.add(bankApplicability);
									rmBankLstTOTemp.remove(i);
								}
									
							}
						}
					}
					setRmBankLstTOMaintain(rmBankLstTOTemp);
					setBankLstTO(null);
					setBankLstTO(addBankLstTO);
					}*/
					bankTransferBeanDataTableLst.remove(bankTransBean);
					
					if(bankTransferBeanDataTableLst.size()==0){
						setBooRenderDataTable(false);
						setBooRenderFCAmount(false);
						setBooRenderBalancePanelPanel(false);
						setBooRenderSavePanel(false);
						setBooRenderUpdatePanel(false);
						setBooRenderUpdate(false);
						setBooRenderAddPanelForUpdate(false);
						if(getBooBankTransferEditableRef())
						{
							setBooRenderAddPanelForUpdate(true);
						}else
						{
							setBooRenderAddPanelForUpdate(false);
						}
						
					}else{
						setBooRenderDataTable(true);
						setBooRenderFCAmount(true);
						setBooRenderBalancePanelPanel(true);
						
						if(getBooBankTransferEditableRef())
						{
							setBooRenderUpdatePanel(true);
							setBooRenderSavePanel(false);
							setBooRenderUpdate(true);
							setBooRenderAddPanelForUpdate(true);
						}else
						{
							setBooRenderAddPanelForUpdate(false);
							setBooRenderUpdatePanel(false);
							setBooRenderSavePanel(true);
							setBooRenderUpdate(false);
						}
					}
					
						setBooRenderBankTransferToPanel(true);
						setDisableDataTableEdit(false);
					  }catch(NullPointerException ne){
						    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
						    setErrorMessage("MethodName::removeBankTransferTo");
						    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
						    return; 
						    }catch(Exception exception){
						    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
						    setErrorMessage(exception.getMessage()); 
						    RequestContext.getCurrentInstance().execute("error.show();");
						    return;       
						    }

	

				}
				public void clearBankTransferTo()
				{
					setBnkTrToFCAmount(null);
				//	setBnkTrToExchangeRate(null);
					setBnkTrToLocalAmount(null);
					setBankTrToInstrunction(null);
					setBankTrToInstrunctionForUpdate(null);
					setBankTrToInstructionDesc(null);
				//	setCurrencyIdTOForUpdate(null);
					setBnkTrToAccNoForUpdate(null);
					setBnkTrToAccNo(null);
					setTobankStandardInstruction(true);
					pstandardInstrnforBnkTrTo.clear();
				}
				
				public static BigDecimal round(BigDecimal bd, int places) {
				    if (places < 0) throw new IllegalArgumentException();
				    bd = bd.setScale(places, RoundingMode.HALF_UP);
				    return bd;
				}
				
				
				public void afterUpdateRecord(){
					
					try{
						FacesContext.getCurrentInstance().getExternalContext().redirect("banktransfer.xhtml");
					} catch(Exception e) {
						log.info("Problem to redirect");
					}
				}
				
			
				public void completed()
				{
					clearCache();
				}
				public boolean changeFCAmount()
				{
					boolean isFcAmount=false;
					if(bankTransferBeanDataTableLst.size()>0)
					{
						if(getBnkTrFromFCAmount()!=null && getBnkTrFromFCAmountTemp()!=null)
						{
							if(getBnkTrFromFCAmount().compareTo(getBnkTrFromFCAmountTemp())!=0)
							{
								RequestContext.getCurrentInstance().execute("changeFCAmount.show();");
								isFcAmount=false;
								setBnkTrFromFCAmount(getBnkTrFromFCAmountTemp());
							}
									
						}
						
					}else
					{
						clearBankTransferFromForFCAmtChange();
						clearBankTransferToForFCAmtChange();
						isFcAmount=true;
					}
					return isFcAmount;
				}
				
				public void clearBankTransferToForFCAmtChange()
				{
					
					setBnkTrToFCAmount(null);
					setBnkTrToLocalAmount(null);
					
				}
				
				public void clearBankTransferFromForFCAmtChange()
				{
					setBnkTrFromLocalAmount(null);
				}
				
			public void populateExchangeRateFrom(){
				  try{
				setBnkTrFromExchangeRate(null);
				setBnkTrToExchangeRate(null);
				if(null!=getBnkTrFromAccNo()&&getBankId()!=null&&getCurrencyId()!=null){
					if(getBnkTrFromAccNo()!=null){
 				List<AccountBalance>    accBalList= igeneralService.getExchangeRateFromAccBal(getBankId(), getCurrencyId(),getBnkTrFromAccNo());
			   if(accBalList.size()!=0){
				setBnkTrFromExchangeRate(accBalList.get(0).getAverageRate());
				setBnkTrToExchangeRate(accBalList.get(0).getAverageRate() );
			   }else{
				   	setBnkTrFromExchangeRate(null);
					setBnkTrToExchangeRate(null);
					setBnkTrFromFCAmount(null);
					setBnkTrFromLocalAmount(null);
					setBankTrFromInstructionDesc(null);
					 RequestContext.getCurrentInstance().execute("noExchange.show();");
                	 return;
			   }
			}
				}
				
				findBankFromStandardInstructions();
				  }catch(Exception exception){
					    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					    setErrorMessage(exception.getMessage()); 
					    RequestContext.getCurrentInstance().execute("error.show();");
					    return;       
					    }
				
			}
	/*	public void populateExchangeRateTo(){
			setBnkTrToExchangeRate(null);
						if(null!=getBnkTrToAccNo()&&getBankIdTO()!=null&&getBnkTrToAccNo()!=null){
							if(getBnkTrToAccNo()!=null){
						List<AccountBalance>  accBalList=igeneralService.getExchangeRateFromAccBal( getBankIdTO(), getCurrencyIdTO(),getBnkTrToAccNo());
						if(accBalList.size()!=0){
		                     setBnkTrToExchangeRate(accBalList.get(0).getAverageRate());
		                 }else{
		                	 RequestContext.getCurrentInstance().execute("noExchange.show();");
		                	 return;
		                 }
						}
						}
		}*/
		
		
	public void editForFirstTimeSave(BankTransferBeanDataTable bTDataTable){
		
			getBankLstTO();
			getCurrencyListFromDB();
			getLstBnkToAccountNumber();
			getUpdateForBankTransferTo(bTDataTable);
			setSalesCheckbox(false);
			setTobankStandardInstruction(false);
			bankTransferBeanDataTableLst.remove(bTDataTable);
			if(bankTransferBeanDataTableLst.size()==0){
				setBooRenderDataTable(false);
				setBooRenderFCAmount(false);
				setBooRenderBalancePanelPanel(false);
				setBooRenderSavePanel(false);
				setBooRenderUpdatePanel(false);
			}else{
				setBooRenderDataTable(true);
				setBooRenderFCAmount(true);
				setBooRenderBalancePanelPanel(true);
				setBooRenderSavePanel(false);
				setBooRenderUpdatePanel(true);
			}
		
	
				setDisableDataTableEdit(true);
}
	
	
	// REPORTS INTEGRATION
	
		JasperPrint jasperPrint;

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
		
		public void generateBankTransferFaxReport(ActionEvent actionEvent)
				throws JRException, IOException, Exception {
			
			try {

				fetchBankTransferFaxInfo(new BigDecimal(getFinaceYear()),new BigDecimal(getDocumentNo().toString()),getDocSerialIdNumberForSave());
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

		public void generateBankTransferDepositReport(ActionEvent actionEvent) throws JRException, IOException, Exception {
			try {

				fetchBankTransferDepositInfo(new BigDecimal(getFinaceYear()), new BigDecimal(getDocumentNo().toString()),getDocSerialIdNumberForSave());
				bankTransferDepositReportInit();
				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.addHeader("Content-disposition","attachment; filename=banktransfer_depositcopy.pdf");

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

		public void fetchBankTransferFaxInfo(BigDecimal doucumentYear,BigDecimal doucumentId,BigDecimal documentNumber)throws Exception{
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

		
			saleList = fXDetailInformationService.viewTreauryDealwithBankTransfer(doucumentYear,documentNumber,"BF",null);
			treasuryDealInfo1 = fXDetailInformationService.viewTreauryDealwithBankTransfer(doucumentYear,documentNumber,"BT",null);
			
			BankTransferInfo	treasuryDealInfoPurchase = new BankTransferInfo();
			treasuryDealInfoPurchase.setWaterMarkCheck(true);
			
			//set document date
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			String finalDate = form.format(new Date());
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
					String bankDetails = "( "+totalAmountInWords+" )" ;
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
					List<TreasuryStandardInstruction> standardinstruction =	bankTransferService.getTreasuryStandardInstruction(doucumentYear,documentNumber, viewTreasuryDeal1.getTreasuryDealDetailsId());
					if(standardinstruction.size()>0){
					String instructionDesc =""; 
					for(TreasuryStandardInstruction trStdIns:standardinstruction){
						if(instructionDesc.trim().equals("")){
							instructionDesc  =  trStdIns.getMessageDescription().replace("#", "");
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
		
		public void fetchBankTransferDepositInfo(BigDecimal doucumentYear,BigDecimal doucumentId,BigDecimal documentNumber) {
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
			
			List<ViewTreasuryDeal> treasuryDealInfo1 = new ArrayList<ViewTreasuryDeal>();
			List<ViewTreasuryDeal> saleList = new ArrayList<ViewTreasuryDeal>();
			String amountinWords = null;

		
			treasuryDealInfo1 = fXDetailInformationService.viewTreauryDealwithBankTransfer(doucumentYear,documentNumber,"BT",null);
			saleList = fXDetailInformationService.viewTreauryDealwithBankTransfer(doucumentYear,documentNumber,"BF",null);
			
			BankTransferInfo	treasuryDealInfoPurchase = new BankTransferInfo();
			
			
			//set document no
			if(documentNumber!=null && treasuryDealInfo1.get(0).getDocumentFinanceYear()!=null){
			treasuryDealInfoPurchase.setDocumentNo(treasuryDealInfo1.get(0).getDocumentFinanceYear().toString()+" / "+documentNumber.toString());
			}
			
			//set document date 
			SimpleDateFormat form = new SimpleDateFormat("dd/MM/yyyy");
			String finalDate = form.format(new Date());
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
				}
				if(companyDesObj.getFsCompanyMaster().getAddress2()!=null){
					companyAdds.append(", ");
					companyAdds.append(companyDesObj.getFsCompanyMaster().getAddress2());
				}
				if(companyDesObj.getFsCompanyMaster().getAddress3()!=null){
					companyAdds.append(", ");
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

		public void clear(){
			setRenderDataTableDelete(true);
			setReadonly(false);
			setBooRenderEditColumn(false);
			setBooRenderEditColumnForFirstTimeSave(true);
			setDisableDataTableEdit(false);
			setBooRenderDataTable(false);
			setBooDisplayAddNewTrasLink(false);
			setBooRenderBalancePanelPanel(false);
			setBooRenderSavePanel(false);
			clearBankFromDetails();
			clearBankTODetails();
			clearBankInfoDetails();
			setDate3(null);
			setBnkValueDateForUpdate(null);
			bnkTOFCAmt=null;
			setBanlanceAmonut(null);
			if(bankTransferFromBeanLst!=null)
			{
				bankTransferFromBeanLst.clear();
			}
			
			
			setAllNullvalues();
			setBooDisplayAddNewTrasLink(false);
			setBooRenderOffEditIcon(true);
			setDisplayDynamicColor(false);
			setBooRenderAddPanelForUpdate(false);			
			setBooRenderUpdatePanel(false);
			setBooRenderBankTransferToPanel(true);
			setBooBankTransferEditableRef(false);
			setBooBankTransferRef(true);
			setBooRenderAdd(true);
			setBooRenderUpdate(false);
			setBooRenderExit(true);
			setBooRenderCancel(false);
			setBooRenderBankId(true);
			setBooRenderBankIdForUpdate(false);
			setBooRenderCurrencyId(true);
			setBooRenderCurrencyIdForUpdate(false);
			setBooRenderBnkTrFromAccNo(true);
			setBooRenderBnkTrFromAccNoForUpdate(false);
			setBooRenderBankTrFromInstrunction(true);
			setBooRenderBankTrFromInstrunctionForUpdate(false);
			setBooRenderBankIdTO(true);
			setBooRenderBankIdTOForUpdate(false);
			setBooRenderCurrencyIdTO(true);
			setBooRenderCurrencyIdTOForUpdate(false);
			setBooRenderBnkTrToAccNo(true);
			setBooRenderBnkTrToAccNoForUpdate(false);
			setBooRenderBankTrToInstrunction(true);
			setBooRenderBankTrToInstrunctionForUpdate(false);
			setBooRenderbnkValueDate(true);
			setBooRenderbnkValueDateForUpdate(false);
			setBankTransferEditableRef(null);
			setBnkValueDate(null);
			setBooRenderFCAmount(false);
			//checkbox disable
			setTobankStandardInstruction(true);
			setBankStandardInstruction(true);
			setPurchaseCheckbox(false);
			setSalesCheckbox(false);
			
			setSuccessRender(false);
			setMainRenderPanel(true);
			
			if(bankTransferBeanDataTableLst!=null)
			{
				bankTransferBeanDataTableLst.clear();
			}
			setTreasuryDetailBrFromPkId(null);
			setTreasuryDetailBrFromCreadteBy(null);
			setTreasuryDetailBrFromCreadteDate(null);
			setTreasuryStdBrFromPkId(null);
			setTresuryHdPK(null);
			//setTreasuryDetailBrFromPkId(null);
			setTresuryDtPK(null);
			setCreatedBy(null);
			setCreatedDate(null);
		}
	
		private void populateAccountNumberDDForEdit()throws Exception
		{
			List<BankAccountDetails> ptabledata = fundEstimationService.getAccountNumber(getBankIdTO(), getCurrencyIdTO());
			accNumberSize = ptabledata.size();
			if(accNumberSize==1){

				for (BankAccountDetails element : ptabledata) {			
					setBnkTrToAccNo(element.getFundGlno());
					setBnkTrToAccNoForUpdate(element.getBankAcctNo());
					setBnkTrToAccName(element.getFundGlno());
				}
				addBankTransferFromRecordsToDataTable();
				setBooRenderBnkTrToAccNoForUpdate(true);
				setBooRenderBnkTrToAccNo(false);


			}else{
				setLstBnkToAccountNumber(ptabledata);
				setBooRenderBnkTrToAccNoForUpdate(false);
				setBooRenderBnkTrToAccNo(true);
			}

			if(getBankId()!=null && getCurrencyId()!=null && getBnkTrFromAccNo()!=null){
				 BigDecimal bankAccountDetId =  bankTransferService.getBankAccountDeatilsPk(getBnkTrFromAccNo());
			List<StandardInstruction> standardInstruction=standardInstructionsService.getValues(sessionStateManage.getCompanyId(),getBankId(),getCurrencyId(),bankAccountDetId,Constants.SD);
			accNumberSize = standardInstruction.size();
			if(accNumberSize!=0){
				if(accNumberSize==1){

					for (StandardInstruction element : standardInstruction) {	
						setBankTrFromInstrunction(element.getIntructionType());
						setBankTrFromInstrunctionForUpdate(element.getIntructionType());
						setBankTrFromInstructionDesc(element.getInstructionDescription());
						setBankStandardInstruction(false);
					}
					setBooRenderBankTrFromInstrunctionForUpdate(true);
					setBooRenderBankTrFromInstrunction(false);
				}else{
					setPstandardInstrnforPurchase(standardInstruction);
					setBooRenderBankTrFromInstrunctionForUpdate(false);
					setBooRenderBankTrFromInstrunction(true);
				}
			}
			
		}
		}
		
		public void populateInstructionsForToBank(){
			if(getBankIdTO()!=null && getCurrencyIdTO()!=null && getBnkTrToAccNo()!=null){
				findBankToStandardInstructions();
				}
		}
		
	  private List<TreasuryDealHeader> lstofUnApproved = new ArrayList<TreasuryDealHeader>();

	  public List<TreasuryDealHeader> getLstofUnApproved() {
		    return lstofUnApproved;
	  }

	  public void setLstofUnApproved(List<TreasuryDealHeader> lstofUnApproved) {
		    this.lstofUnApproved = lstofUnApproved;
	  }
	  
	  public BigDecimal getDocumentSerialIdNumber(String processIn) {
			
			String documentSerialId="0";
			 try {
					HashMap<String, String> outPutValues= igeneralService.getNextDocumentRefNumber(sessionStateManage.getCountryId().intValue() ,sessionStateManage.getCompanyId().intValue(),Integer.parseInt(Constants.DOCUMENT_CODE_FOR_BANK_TRANSFER), finaceYear,processIn,sessionStateManage.getCountryBranchCode());
					
					//String proceErrorFlag=outPutValues.get("PROCE_ERORRFLAG");
					String proceErrorMsg=outPutValues.get("PROCE_ERORRMSG");
					if(proceErrorMsg!=null)
					{
						setBooDocVisble(Boolean.TRUE);
						setErrmsg(proceErrorMsg);
						RequestContext.getCurrentInstance().execute("proceErr.show();");
						return BigDecimal.ZERO;
					}else if(outPutValues.get("DOCNO") !=null && new BigDecimal(outPutValues.get("DOCNO")).compareTo(BigDecimal.ZERO)==0){
						setBooDocVisble(Boolean.TRUE);
						RequestContext.getCurrentInstance().execute("docZero.show();");
						return BigDecimal.ZERO;
						
					}else
					{
						setBooDocVisble(Boolean.FALSE);
						documentSerialId=outPutValues.get("DOCNO");
					}
				} catch (NumberFormatException | AMGException e) {
					
					setBooDocVisble(Boolean.TRUE);
					setErrmsg(e.getMessage());
					RequestContext.getCurrentInstance().execute("proceErr.show();");
					return BigDecimal.ZERO;
				}
			
			
			return new BigDecimal(documentSerialId);
		}
	  
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	  
	  public void deleteBankTransfer()
	  {
		  
		  try{
			  if(getBankTransferEditableRef()==null)
			  {
				  RequestContext.getCurrentInstance().execute("deleteCheck.show();");
				  return;
			  }
			  BigDecimal treasuryHeaderPk= getTresuryHdPK();
			  BigDecimal treasuryBrFromPk= getTreasuryDetailBrFromPkId();
			  List<BigDecimal> lstTreasuryStdBrFromPk= getTreasuryStdBrFromPkId();

			  List<BigDecimal> lstTreasuryDetailPk= new ArrayList<BigDecimal>();

			  List<BigDecimal> lstTreasuryStdBrToPk= new ArrayList<BigDecimal>();


			  if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
			  {
				  for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
				  {

					  lstTreasuryDetailPk.add(bnkTransBeanDataTable.getTresuryDtPK());
					  List<BigDecimal> lstTreasuryStdBankTrToPk=  bnkTransBeanDataTable.getTreasuryStdBrToPkId();
					  if(lstTreasuryStdBankTrToPk!=null && lstTreasuryStdBankTrToPk.size()>0)
					  {
						  for(BigDecimal treasuryStdBkToPk: lstTreasuryStdBankTrToPk)
						  {
							  lstTreasuryStdBrToPk.add(treasuryStdBkToPk);
						  }
					  }

				  }
				  lstTreasuryStdBrToPk.add(BigDecimal.ONE);
				  bankTransferService.deleteBrantransferRecords(treasuryHeaderPk, treasuryBrFromPk, lstTreasuryStdBrFromPk, lstTreasuryDetailPk, lstTreasuryStdBrToPk, sessionStateManage.getUserName());
				  if(bankTransferBeanDataTableLst!=null)
				  {
					  bankTransferBeanDataTableLst.clear();
				  }


				  setAllNullvalues();

				  RequestContext.getCurrentInstance().execute("success.show();");
			  }else
			  {
				  RequestContext.getCurrentInstance().execute("deleteCheck.show();");
			  }
			 
			  
		  }catch(Exception exception){
			  log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
		  
	  }
	  
	  private String treasuryDetailBrFromCreadteBy;
	  private Date treasuryDetailBrFromCreadteDate;







	public String getTreasuryDetailBrFromCreadteBy() {
		return treasuryDetailBrFromCreadteBy;
	}

	public void setTreasuryDetailBrFromCreadteBy(
			String treasuryDetailBrFromCreadteBy) {
		this.treasuryDetailBrFromCreadteBy = treasuryDetailBrFromCreadteBy;
	}

	public Date getTreasuryDetailBrFromCreadteDate() {
		return treasuryDetailBrFromCreadteDate;
	}

	public void setTreasuryDetailBrFromCreadteDate(
			Date treasuryDetailBrFromCreadteDate) {
		this.treasuryDetailBrFromCreadteDate = treasuryDetailBrFromCreadteDate;
	}
	  
	public void getStandInstForBankTrTo(BigDecimal treDetailPkID)
	{
		List<TreasuryStandardInstruction> 	treasuryStandardInstrucLst=bankTransferService.getTreasuryStandardInstructionInfo(getBankTransferEditableRef(),getDocumentNo(),treDetailPkID,sessionStateManage.getCompanyId());
		if(treasuryStandardInstrucLst!=null && treasuryStandardInstrucLst.size()>0)
		{
			TreasuryStandardInstruction treasuryStandardInstruction =treasuryStandardInstrucLst.get(0);
			setInstructionBankToPk(treasuryStandardInstruction.getStandardInstructionNumber());
			fetchStandInstrnToBank();
		}
	}
	public void changeFcBalanceAmount()
	{

		BigDecimal enterFCAmt=getBnkTrFromFCAmount();
		BigDecimal bnkTrnsToFcAmount=BigDecimal.ZERO;
		if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
		{
			for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
			{
				bnkTrnsToFcAmount=bnkTrnsToFcAmount.add(bnkTransBeanDataTable.getFcAmount());
			}
		}
		if(enterFCAmt.compareTo(bnkTrnsToFcAmount)==-1)
		{
			setBnkTrFromFCAmount(bnkTrnsToFcAmount);
			setBanlanceAmonut(BigDecimal.ZERO);
			RequestContext.getCurrentInstance().execute("fcAmtLess.show();");

		}else if(enterFCAmt.compareTo(bnkTrnsToFcAmount)==1)
		{
			BigDecimal balanceAmt=enterFCAmt.subtract(bnkTrnsToFcAmount);
			setBanlanceAmonut(balanceAmt);
			if(getBooBankTransferEditableRef())
			{
				setBooRenderAddPanelForUpdate(true);
				setBooRenderAddPanel(false);
			}else
			{
				setBooRenderAddPanelForUpdate(false);
				setBooRenderAddPanel(true);
			}
			
		}else if(enterFCAmt.compareTo(bnkTrnsToFcAmount)==0)
		{
			setBanlanceAmonut(BigDecimal.ZERO);
		}

	}
	
	public void checkFCBalance()
	{
		BigDecimal enterFCAmt=getBnkTrFromFCAmount();
		BigDecimal bnkTrnsToFcAmount=BigDecimal.ZERO;
		if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
		{
			for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
			{
				bnkTrnsToFcAmount=bnkTrnsToFcAmount.add(bnkTransBeanDataTable.getFcAmount());
			}
		}
		
		if(enterFCAmt.compareTo(bnkTrnsToFcAmount)==1)
		{
			BigDecimal balanceAmt=enterFCAmt.subtract(bnkTrnsToFcAmount);
			setBanlanceAmonut(balanceAmt);
			if(getBooBankTransferEditableRef())
			{
				setBooRenderAddPanelForUpdate(true);
				setBooRenderAddPanel(false);
			}else
			{
				setBooRenderAddPanelForUpdate(false);
				setBooRenderAddPanel(true);
			}
		}else if(enterFCAmt.compareTo(bnkTrnsToFcAmount)==0)
		{
			setBanlanceAmonut(BigDecimal.ZERO);
		}
	}
	
	public void addBankListForTo()
	{
		
		
		if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
		{
			
			populateBankToBank();
			List<BankApplicability> bankappliLIstForEdit = getBankLstTO();
			List<BankApplicability> bankappliLIstForEditTemp = new ArrayList<BankApplicability>();
			bankappliLIstForEditTemp.addAll(bankappliLIstForEdit);
			
			List<BankApplicability> bankappliTempLIstForEdit = getBankLstTO();
			
			List<BankApplicability> bankappliLIstForEditTempRemove = new ArrayList<BankApplicability>();
			bankappliLIstForEditTempRemove.addAll(bankappliTempLIstForEdit);
			
			List<BankApplicability> bankappliRemoveTempLIstForEdit = new ArrayList<BankApplicability>();
			List<BankTransferBeanDataTable> bankTransferBeanDataTableTempLst=new ArrayList<BankTransferBeanDataTable>();
			bankTransferBeanDataTableTempLst.addAll(bankTransferBeanDataTableLst);
			for (BankTransferBeanDataTable dataObj : bankTransferBeanDataTableTempLst) {
				for (BankApplicability bankAppObj : bankappliLIstForEditTemp) {
					if (dataObj.getBankId().compareTo(
							bankAppObj.getBankMaster().getBankId()) == 0) {
						bankappliLIstForEditTempRemove.remove(bankAppObj);
						bankappliRemoveTempLIstForEdit.add(bankAppObj);
					}
				}
			}
			setRmBankLstTOMaintain(bankappliRemoveTempLIstForEdit);
			setBankLstTO(bankappliLIstForEditTempRemove);
					
			List<BankApplicability> rmBankLstTO=getRmBankLstTOMaintain();
			List<BankApplicability> rmBankLstTOTemp=getRmBankLstTOMaintain();
			List<BankApplicability> addBankLstTO=new ArrayList<BankApplicability>();
			addBankLstTO.addAll(getBankLstTO());
			for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
			{
				/*BigDecimal removeBankId =bnkTransBeanDataTable.getBankId();
				
				if(rmBankLstTO!=null && rmBankLstTO.size()>0)
				{
					for(int i=0 ;i<rmBankLstTO.size();i++)
					{
						BankApplicability bankApplicability=rmBankLstTO.get(i);
						if(bankApplicability.getBankMaster().getBankId().equals(removeBankId))
						{
							if(!addBankLstTO.contains(bankApplicability))
							{
								addBankLstTO.add(bankApplicability);
								rmBankLstTOTemp.remove(i);
							}
								
						}
					}
				}*/
				setCurrencyIdTOForUpdate((iSpecialCustomerDealRequestService.getCurrencyForUpdate(bnkTransBeanDataTable.getCurrencyId())));
				setBnkTrToExchangeRate(bnkTransBeanDataTable.getLocalExchangeRate());
				setCurrencyIdTO(bnkTransBeanDataTable.getCurrencyId());
			}
			setRmBankLstTOMaintain(rmBankLstTOTemp);
			setBankLstTO(null);
			setBankLstTO(addBankLstTO);
			
			setBooRenderDataTable(true);
			setBooRenderFCAmount(true);
			setBooRenderBalancePanelPanel(true);
			
			if(getBooBankTransferEditableRef())
			{
				setBooRenderUpdatePanel(true);
				setBooRenderSavePanel(false);
				setBooRenderUpdate(true);
				setBooRenderAddPanelForUpdate(true);
			}else
			{
				setBooRenderAddPanelForUpdate(false);
				setBooRenderUpdatePanel(false);
				setBooRenderSavePanel(true);
				setBooRenderUpdate(false);
			}
			
		}else
		{
			populateBankToBank();
			setCurrencyIdTOForUpdate((iSpecialCustomerDealRequestService.getCurrencyForUpdate(getCurrencyId())));
			setBnkTrToExchangeRate(getBnkTrFromExchangeRate());
			setCurrencyIdTO(getCurrencyId());
			
			setBooRenderDataTable(false);
			setBooRenderFCAmount(false);
			setBooRenderBalancePanelPanel(false);
			setBooRenderSavePanel(false);
			setBooRenderUpdatePanel(false);
			setBooRenderUpdate(false);
			//setBooRenderAddPanelForUpdate(false);
			if(getBooBankTransferEditableRef())
			{
				setBooRenderAddPanelForUpdate(true);
			}else
			{
				setBooRenderAddPanelForUpdate(false);
			}
		}
		
		
	}
	
	public void noInstructionsFound1(){
		
		setBankIdTO(null);
		setBnkTrToAccNo(null);
		setBnkTrToAccNoForUpdate(null);
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/banktransfer.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public boolean currencyCheck()
	{
		boolean checkCurrency=false;
		if(bankTransferBeanDataTableLst!=null && bankTransferBeanDataTableLst.size()>0)
		{
			for(BankTransferBeanDataTable bnkTransBeanDataTable:bankTransferBeanDataTableLst)
			{
				if(bnkTransBeanDataTable.getCurrencyId().compareTo(getCurrencyId())!=0)
				{
					checkCurrency=true;
					break;
					
				}
			}
			
				
		}
		return checkCurrency;
	}
}
