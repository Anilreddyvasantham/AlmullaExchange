package com.amg.exchange.foreigncurrency.bean;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.foreigncurrency.model.Collect;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.PurposeOfTransaction;
import com.amg.exchange.foreigncurrency.model.ReceiptPayment;
import com.amg.exchange.foreigncurrency.model.SourceOfIncome;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.CustomerIdProof;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;

@Component("foreignCurrencyPurchaseEnquiryBean")
@Scope("session")
public class ForeignCurrencyPurchaseEnquiryBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(ForeignCurrencyPurchaseEnquiryBean.class);
	private List<CurrencyMaster> lstcurrency;
	private List<CurrencyWiseDenomination> lstDenomination;
	private List<SourceOfIncomeDescription> lstSourceOfIncomes;
	private List<PurposeOfTransaction> lstPurposeOfTransactions;
	private PurposeOfTransaction purposeofTransaction;
	private SourceOfIncome sourceOfIncome;
	private String saleAmount = null;
	private String sourceOfIncomes;
	private String purposeOfTransactions;
	private int purposeId;
	private int sourceId;
	private boolean forNextPanels = false;
	private Boolean fcPurchageLabel = false;
	private Boolean localcashpaidtoCustomerLabel = false;
	private BigDecimal customerId;
	private Boolean booRenderSecondPanel;
	SessionStateManage sessionStateManage = new SessionStateManage();
	private int financeMonth;
	private String id = null;
	private String name = null;
	private String mobile = null;
	private Double neededPurchaseAmount;
	private String signatureSpecimen = null; // property :Signature Specimen
	private int documentId = Integer.parseInt(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE);
	private String processIn = Constants.Yes;
	private String documentSerialId;
	private Boolean booReg = false;
	private Boolean booFc = true;
	private String msgs = null;
	List<Employee> list = new ArrayList<>();
	private BigDecimal currencyId;
	private BigDecimal noNotes = BigDecimal.ZERO;
	private BigDecimal denomination;
	private BigDecimal purchaseAmount;
	private BigDecimal fundMaxRate;
	private double totalPurchaseAmount;
	private double exchangeRate;
	private String saleCurrencyCode;
	private double avgExchageRate = 0.0D;
	private String remarks;
	private String signature;
	private String foreignCurrencyName;
	private String digitalSign;// new property for digital sign
	private List<DenominationBean> denominationBeanList = new ArrayList<DenominationBean>();
	private List<DenominationBean> denominationCollectionBeanList = new ArrayList<DenominationBean>();
	private List<DenominationBean> denominationRefundBeanList = new ArrayList<DenominationBean>();
	private List<PurposeOfTransaction> lstPurposeOfTransaction = new ArrayList<>();
	private List<SourceOfIncome> lstSourceOfIncome = new ArrayList<>();
	private List<UserFinancialYear> financialYearList = new ArrayList<>();
	private boolean booPurchasePanel = true;
	private boolean booPanelDatatable = false;
	private boolean booRefundPanel = false;
	private boolean signaturePanel = false;
	private Collect collect;
	private CollectDetail collectDetail;
	private ForeignCurrencyAdjust foreignCurrencyAdjust;
	private CustomerIdProof customerIdProof;
	private CurrencyMaster currencyMaster;
	private BankMaster bankMaster;
	private CurrencyWiseDenomination denominationMaster;
	private ReceiptPayment receiptPayment;
	private String totalRefundCashRecipt = null;
	private String totalRefundLocalCashEntered = null;
	private BigDecimal denomIdCheckFor = null;
	private StreamedContent myImage;
	private String docSerialIdNumberForSave;
	private List<UserFinancialYear> userFinancialYearList = new ArrayList<UserFinancialYear>();
	private BigDecimal documentFinanceYear;
	private BigDecimal documentNo;
	private String totalValue = null;
	private ArrayList<ForeignLocalCurrencyDataTable> lstData = new ArrayList<ForeignLocalCurrencyDataTable>();
	private ArrayList<ForeignLocalCurrencyDataTable> lstRefundData = new ArrayList<ForeignLocalCurrencyDataTable>();
	List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = null;
	private Boolean booEnableCollectionPanel;
	private Boolean booEnableRefundPanel;
	private BigDecimal pkCollect = null;
	private Boolean isFromFCPurchage = false;
	private BigDecimal limitationAmount;
	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	


	public BigDecimal getLimitationAmount() {
		return limitationAmount;
	}

	public void setLimitationAmount(BigDecimal limitationAmount) {
		this.limitationAmount = limitationAmount;
	}

	public Boolean getBooEnableRefundPanel() {
		return booEnableRefundPanel;
	}

	public void setBooEnableRefundPanel(Boolean booEnableRefundPanel) {
		this.booEnableRefundPanel = booEnableRefundPanel;
	}

	public Boolean getBooEnableCollectionPanel() {
		return booEnableCollectionPanel;
	}

	public void setBooEnableCollectionPanel(Boolean booEnableCollectionPanel) {
		this.booEnableCollectionPanel = booEnableCollectionPanel;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public StreamedContent getMyImage() {
		return myImage;
	}

	public void setMyImage(StreamedContent myImage) {
		this.myImage = myImage;
	}

	public List<DenominationBean> getDenominationRefundBeanList() {
		return denominationRefundBeanList;
	}

	public void setDenominationRefundBeanList(List<DenominationBean> denominationRefundBeanList) {
		this.denominationRefundBeanList = denominationRefundBeanList;
	}

	public List<DenominationBean> getDenominationCollectionBeanList() {
		return denominationCollectionBeanList;
	}

	public void setDenominationCollectionBeanList(List<DenominationBean> denominationCollectionBeanList) {
		this.denominationCollectionBeanList = denominationCollectionBeanList;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}

	public List<UserFinancialYear> getUserFinancialYearList() {
		return userFinancialYearList;
	}

	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public Boolean getBooRenderSecondPanel() {
		return booRenderSecondPanel;
	}

	public void setBooRenderSecondPanel(Boolean booRenderSecondPanel) {
		this.booRenderSecondPanel = booRenderSecondPanel;
	}

	public IForeignCurrencyPurchaseService<T> getForeignCurrencyPurchaseService() {
		return foreignCurrencyPurchaseService;
	}

	public void setForeignCurrencyPurchaseService(IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService) {
		this.foreignCurrencyPurchaseService = foreignCurrencyPurchaseService;
	}

	public List<CurrencyMaster> getLstcurrency() {
		return lstcurrency;
	}

	public void setLstcurrency(List<CurrencyMaster> lstcurrency) {
		this.lstcurrency = lstcurrency;
	}

	public List<CurrencyWiseDenomination> getLstDenomination() {
		return lstDenomination;
	}

	public void setLstDenomination(List<CurrencyWiseDenomination> lstDenomination) {
		this.lstDenomination = lstDenomination;
	}

	public List<SourceOfIncomeDescription> getLstSourceOfIncomes() {
		return lstSourceOfIncomes;
	}

	public void setLstSourceOfIncomes(List<SourceOfIncomeDescription> lstSourceOfIncomes) {
		this.lstSourceOfIncomes = lstSourceOfIncomes;
	}

	public Boolean getFcPurchageLabel() {
		return fcPurchageLabel;
	}

	public void setFcPurchageLabel(Boolean fcPurchageLabel) {
		this.fcPurchageLabel = fcPurchageLabel;
	}

	public Boolean getLocalcashpaidtoCustomerLabel() {
		return localcashpaidtoCustomerLabel;
	}

	public void setLocalcashpaidtoCustomerLabel(Boolean localcashpaidtoCustomerLabel) {
		this.localcashpaidtoCustomerLabel = localcashpaidtoCustomerLabel;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransactions() {
		return lstPurposeOfTransactions;
	}

	public void setLstPurposeOfTransactions(List<PurposeOfTransaction> lstPurposeOfTransactions) {
		this.lstPurposeOfTransactions = lstPurposeOfTransactions;
	}

	public Boolean getIsFromFCPurchage() {
		return isFromFCPurchage;
	}

	public void setIsFromFCPurchage(Boolean isFromFCPurchage) {
		this.isFromFCPurchage = isFromFCPurchage;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getNoNotes() {
		return noNotes;
	}

	public void setNoNotes(BigDecimal noNotes) {
		this.noNotes = noNotes;
	}

	public BigDecimal getDenomination() {
		return denomination;
	}

	public void setDenomination(BigDecimal denomination) {
		this.denomination = denomination;
	}

	public BigDecimal getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(BigDecimal purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public BigDecimal getFundMaxRate() {
		return fundMaxRate;
	}

	public void setFundMaxRate(BigDecimal fundMaxRate) {
		this.fundMaxRate = fundMaxRate;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getProcessIn() {
		return processIn;
	}

	public void setProcessIn(String processIn) {
		this.processIn = processIn;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}

	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	public void setTotalPurchaseAmount(double totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public double getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public List<DenominationBean> getDenominationBeanList() {
		return denominationBeanList;
	}

	public void setDenominationBeanList(List<DenominationBean> denominationBeanList) {
		this.denominationBeanList = denominationBeanList;
	}

	public boolean isBooPurchasePanel() {
		return booPurchasePanel;
	}

	public void setBooPurchasePanel(boolean booPurchasePanel) {
		this.booPurchasePanel = booPurchasePanel;
	}

	public boolean isBooPanelDatatable() {
		return booPanelDatatable;
	}

	public void setBooPanelDatatable(boolean booPanelDatatable) {
		this.booPanelDatatable = booPanelDatatable;
	}

	public boolean isBooRefundPanel() {
		return booRefundPanel;
	}

	public void setBooRefundPanel(boolean booRefundPanel) {
		this.booRefundPanel = booRefundPanel;
	}

	public Collect getCollect() {
		return collect;
	}

	public void setCollect(Collect collect) {
		this.collect = collect;
	}

	public CollectDetail getCollectDetail() {
		return collectDetail;
	}

	public void setCollectDetail(CollectDetail collectDetail) {
		this.collectDetail = collectDetail;
	}

	public ForeignCurrencyAdjust getForeignCurrencyAdjust() {
		return foreignCurrencyAdjust;
	}

	public void setForeignCurrencyAdjust(ForeignCurrencyAdjust foreignCurrencyAdjust) {
		this.foreignCurrencyAdjust = foreignCurrencyAdjust;
	}

	public int getPurposeId() {
		return purposeId;
	}

	public void setPurposeId(int purposeId) {
		this.purposeId = purposeId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getMsgs() {
		return msgs;
	}

	public void setMsgs(String msgs) {
		this.msgs = msgs;
	}

	public CustomerIdProof getCustomerIdProof() {
		return customerIdProof;
	}

	public void setCustomerIdProof(CustomerIdProof customerIdProof) {
		this.customerIdProof = customerIdProof;
	}

	public CurrencyMaster getCurrencyMaster() {
		return currencyMaster;
	}

	public void setCurrencyMaster(CurrencyMaster currencyMaster) {
		this.currencyMaster = currencyMaster;
	}

	public BankMaster getBankMaster() {
		return bankMaster;
	}

	public void setBankMaster(BankMaster bankMaster) {
		this.bankMaster = bankMaster;
	}

	public CurrencyWiseDenomination getDenominationMaster() {
		return denominationMaster;
	}

	public void setDenominationMaster(CurrencyWiseDenomination denominationMaster) {
		this.denominationMaster = denominationMaster;
	}

	public List<PurposeOfTransaction> getLstPurposeOfTransaction() {
		return lstPurposeOfTransaction;
	}

	public void setLstPurposeOfTransaction(List<PurposeOfTransaction> lstPurposeOfTransaction) {
		this.lstPurposeOfTransaction = lstPurposeOfTransaction;
	}

	public List<SourceOfIncome> getLstSourceOfIncome() {
		return lstSourceOfIncome;
	}

	public void setLstSourceOfIncome(List<SourceOfIncome> lstSourceOfIncome) {
		this.lstSourceOfIncome = lstSourceOfIncome;
	}

	public String getPurposeOfTransactions() {
		return purposeOfTransactions;
	}

	public void setPurposeOfTransactions(String purposeOfTransactions) {
		this.purposeOfTransactions = purposeOfTransactions;
	}

	public String getSourceOfIncomes() {
		return sourceOfIncomes;
	}

	public void setSourceOfIncomes(String sourceOfIncomes) {
		this.sourceOfIncomes = sourceOfIncomes;
	}

	public BigDecimal getDenomIdCheckFor() {
		return denomIdCheckFor;
	}

	public void setDenomIdCheckFor(BigDecimal denomIdCheckFor) {
		this.denomIdCheckFor = denomIdCheckFor;
	}

	public Double getNeededPurchaseAmount() {
		return neededPurchaseAmount;
	}

	public void setNeededPurchaseAmount(Double neededPurchaseAmount) {
		this.neededPurchaseAmount = neededPurchaseAmount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getBooReg() {
		return booReg;
	}

	public void setBooReg(Boolean booReg) {
		this.booReg = booReg;
	}

	public Boolean getBooFc() {
		return booFc;
	}

	public void setBooFc(Boolean booFc) {
		this.booFc = booFc;
	}

	public int getFinanceMonth() {
		return financeMonth;
	}

	public void setFinanceMonth(int financeMonth) {
		this.financeMonth = financeMonth;
	}

	public String getLocation() {
		return sessionStateManage.getLocation();
	}

	public void setAvgExchageRate(double avgExchageRate) {
		this.avgExchageRate = avgExchageRate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ReceiptPayment getReceiptPayment() {
		return receiptPayment;
	}

	public void setReceiptPayment(ReceiptPayment receiptPayment) {
		this.receiptPayment = receiptPayment;
	}

	public List<UserFinancialYear> getFinancialYearList() {
		return financialYearList;
	}

	public void setFinancialYearList(List<UserFinancialYear> financialYearList) {
		this.financialYearList = financialYearList;
	}

	private String varToKeepSerial = null;

	public String getVarToKeepSerial() {
		return varToKeepSerial;
	}

	public void setVarToKeepSerial(String varToKeepSerial) {
		this.varToKeepSerial = varToKeepSerial;
	}

	public String getDocumentSerialId() {
		return documentSerialId;
	}

	public void setDocumentSerialId(String documentSerialId) {
		this.documentSerialId = documentSerialId;
	}

	public String getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(String totalValue) {
		this.totalValue = totalValue;
	}

	private String validNoOFQuantity = null;

	public boolean limitNo() {
		// clearCache();
		return false;
	}

	public String getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getSaleCurrencyCode() {
		return saleCurrencyCode;
	}

	public void setSaleCurrencyCode(String saleCurrencyCode) {
		this.saleCurrencyCode = saleCurrencyCode;
	}

	public PurposeOfTransaction getPurposeofTransaction() {
		return purposeofTransaction;
	}

	public void setPurposeofTransaction(PurposeOfTransaction purposeofTransaction) {
		this.purposeofTransaction = purposeofTransaction;
	}

	public SourceOfIncome getSourceOfIncome() {
		return sourceOfIncome;
	}

	public void setSourceOfIncome(SourceOfIncome sourceOfIncome) {
		this.sourceOfIncome = sourceOfIncome;
	}

	private String validNoNotes = null;

	public BigDecimal getPkCollect() {
		return pkCollect;
	}

	public void setPkCollect(BigDecimal pkCollect) {
		this.pkCollect = pkCollect;
	}

	public String getTotalRefundCashRecipt() {
		return totalRefundCashRecipt;
	}

	public void setTotalRefundCashRecipt(String totalRefundCashRecipt) {
		this.totalRefundCashRecipt = totalRefundCashRecipt;
	}

	public String getTotalRefundLocalCashEntered() {
		return totalRefundLocalCashEntered;
	}

	public void setTotalRefundLocalCashEntered(String totalRefundLocalCashEntered) {
		this.totalRefundLocalCashEntered = totalRefundLocalCashEntered;
	}

	

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getValidNoNotes() {
		return validNoNotes;
	}

	public void setValidNoNotes(String validNoNotes) {
		this.validNoNotes = validNoNotes;
	}

	public String getValidNoOFQuantity() {
		return validNoOFQuantity;
	}

	public void setValidNoOFQuantity(String validNoOFQuantity) {
		this.validNoOFQuantity = validNoOFQuantity;
	}

	public boolean isForNextPanels() {
		return forNextPanels;
	}

	public void setForNextPanels(boolean forNextPanels) {
		this.forNextPanels = forNextPanels;
	}

	public String redirectToFCPge() {
		return "fcPage";
	}

	public String getDigitalSign() {
		return digitalSign;
	}

	public void setDigitalSign(String digitalSign) {
		this.digitalSign = digitalSign;
	}

	public boolean isSignaturePanel() {
		return signaturePanel;
	}

	public void setSignaturePanel(boolean signaturePanel) {
		this.signaturePanel = signaturePanel;
	}

	public String getSignatureSpecimen() {
		return signatureSpecimen;
	}

	public void setSignatureSpecimen(String signatureSpecimen) {
		this.signatureSpecimen = signatureSpecimen;
	}

	public String getDocSerialIdNumberForSave() {
		return docSerialIdNumberForSave;
	}

	public void setDocSerialIdNumberForSave(String docSerialIdNumberForSave) {
		this.docSerialIdNumberForSave = docSerialIdNumberForSave;
	}

	private Boolean successPanel = false;
	private Boolean booRenderFirstPanel = false;

	public Boolean getBooRenderFirstPanel() {
		return booRenderFirstPanel;
	}

	public void setBooRenderFirstPanel(Boolean booRenderFirstPanel) {
		this.booRenderFirstPanel = booRenderFirstPanel;
	}

	public Boolean getSuccessPanel() {
		return successPanel;
	}

	public void setSuccessPanel(Boolean successPanel) {
		this.successPanel = successPanel;
	}

	public void completed() {
		enquiryPageNavigation();
	}

	

	public String getAvgExchageRate() {
		try {
			avgExchageRate = 0.0;
			int noteSize = 0;
			for (DenominationBean denominationBean : denominationBeanList) {
				if (denominationBean.getNoOfNotes() != null && !denominationBean.getNoOfNotes().equalsIgnoreCase("")) {
					avgExchageRate += Double.parseDouble(denominationBean.getExchangeRate().toString());
				} else {
					noteSize++;
				}
			}
			if (denominationBeanList.size() != 0) {
				avgExchageRate = avgExchageRate / (denominationBeanList.size() - noteSize);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (String.valueOf(avgExchageRate).equalsIgnoreCase("NaN")) {
			return null;
		} else {
			return new GetRound().round(avgExchageRate, foreignLocalCurrencyDenominationService.getDecimalPerCountry(sessionStateManage.getCountryId()));
		}
	}

	public void fetchData() {
		log.info("Entering into enquiry method");
		List<CollectDetail> CollectList = foreignCurrencyPurchaseService.getFCPurchageCollectionDetails(Constants.DOCUMENT_CODE_FOR_FCPURCHAGE, getDocumentFinanceYear(), getDocumentNo());
		List<ReceiptPayment> receiptPaymentList = foreignCurrencyPurchaseService.getReceipetData(getDocumentFinanceYear(), getDocumentNo());
		if (CollectList.size() > 0 && receiptPaymentList.size() > 0) {
			/* setBooRenderFirstPanel(false); */
			setBooRenderSecondPanel(true);
			setBooPurchasePanel(true);
			foreignCurrencyAdjustList = foreignCurrencyPurchaseService.getforeignCurrencyAdjustList(CollectList.get(0).getCashCollectionId().getCollectionId());
			log.info("foreignCurrencyAdjustList-----> " + foreignCurrencyAdjustList.size());
			denominationBeanList.clear();
			DenominationBean denominationObject = null;
			BigDecimal totalPurchageAmount = new BigDecimal(0);
			BigDecimal saleAmount = new BigDecimal(0);
			for (ForeignCurrencyAdjust adjust : foreignCurrencyAdjustList) {
				denominationObject = new DenominationBean();
				if (adjust.getTransactionType().equals(Constants.P)) {
					denominationObject.setDenominationID(adjust.getFsDenominationId().getDenominationId().intValue());
					denominationObject.setDenominationDesc(adjust.getFsDenominationId().getDenominationDesc());
					denominationObject.setDenominationAmount(adjust.getFsDenominationId().getDenominationAmount());
					denominationObject.setPurchaseAmount(adjust.getFsDenominationId().getDenominationAmount());
					denominationObject.setExchangeRate(adjust.getExchangeRate());
					denominationObject.setNoOfNotes(adjust.getNotesQuantity() == null ? "" : adjust.getNotesQuantity().toString());
					BigDecimal tempAmount = adjust.getNotesQuantity().multiply(adjust.getFsDenominationId().getDenominationAmount());
					BigDecimal tempSaleAmount = tempAmount.multiply(adjust.getExchangeRate());
					denominationObject.setSalesAmount(tempSaleAmount);
					saleAmount = saleAmount.add(tempSaleAmount);
					totalPurchageAmount = totalPurchageAmount.add(tempAmount);
					denominationBeanList.add(denominationObject);
				}
			}
			if (totalPurchageAmount != null) {
				setTotalPurchaseAmount(totalPurchageAmount.doubleValue());
			}
			if (saleAmount != null) {
				setSaleAmount(saleAmount.toPlainString());
			}
			setRemarks(receiptPaymentList.get(0).getRemarks());
			BigDecimal sourceId = receiptPaymentList.get(0).getSourceOfIncome().getSourceId();
			String SourceofIncomeDesc = foreignCurrencyPurchaseService.getsourceofIncomeDesc(sessionStateManage.getLanguageId(), sourceId);
			if (SourceofIncomeDesc != null) {
				setSourceOfIncomes(SourceofIncomeDesc);
			}
			String purposeOfTransaction = foreignCurrencyPurchaseService.getPurposeofTransaction(receiptPaymentList.get(0).getPurposeOfTransaction().getPurposeId());
			if (purposeOfTransaction != null) {
				setPurposeOfTransactions(purposeOfTransaction);
			}
			for (CollectDetail collect : CollectList) {
				log.info("CollectList-----> " + CollectList.size());
				setDocumentId(getDocumentNo().intValue());
				setDocumentSerialId(getDocumentNo().toPlainString());
				setCustomerId(collect.getFsCustomer().getCustomerId());
				setName(collect.getFsCustomer().getFirstName() + " " + collect.getFsCustomer().getLastName());
				setMobile(collect.getFsCustomer().getMobile());
				setCurrencyId(collect.getExCurrencyMaster().getCurrencyId());
				setForeignCurrencyName(collect.getExCurrencyMaster().getCurrencyName());
				setPurchaseAmount(collect.getCashCollectionId().getPaidAmount());
			}
		} else {
			RequestContext.getCurrentInstance().execute("noRecord.show();");
			return;
		}
		log.info("Exit into enquiry method");
	}

	public void showCollectionDetails() {
		setBooEnableCollectionPanel(true);
		denominationCollectionBeanList.clear();
		DenominationBean denominationObject = null;
		BigDecimal totalPurchageAmount = new BigDecimal(0);
		BigDecimal saleAmount = new BigDecimal(0);
		for (ForeignCurrencyAdjust adjust : foreignCurrencyAdjustList) {
			denominationObject = new DenominationBean();
			if (adjust.getTransactionType().equals(Constants.O)) {
				denominationObject.setDenominationID(adjust.getFsDenominationId().getDenominationId().intValue());
				denominationObject.setDenominationDesc(adjust.getFsDenominationId().getDenominationDesc());
				denominationObject.setDenominationAmount(adjust.getFsDenominationId().getDenominationAmount());
				denominationObject.setPurchaseAmount(adjust.getFsDenominationId().getDenominationAmount());
				denominationObject.setExchangeRate(adjust.getExchangeRate());
				denominationObject.setNoOfNotes(adjust.getNotesQuantity() == null ? "" : adjust.getNotesQuantity().toString());
				BigDecimal tempAmount = adjust.getNotesQuantity().multiply(adjust.getFsDenominationId().getDenominationAmount());
				BigDecimal tempSaleAmount = tempAmount.multiply(adjust.getExchangeRate());
				denominationObject.setSalesAmount(tempSaleAmount);
				saleAmount = saleAmount.add(tempSaleAmount);
				totalPurchageAmount = totalPurchageAmount.add(tempAmount);
				denominationCollectionBeanList.add(denominationObject);
			}
		}
	}

	public void showRefundDetails() {
		setBooEnableRefundPanel(true);
		denominationRefundBeanList.clear();
		DenominationBean denominationObject = null;
		BigDecimal totalPurchageAmount = new BigDecimal(0);
		BigDecimal saleAmount = new BigDecimal(0);
		for (ForeignCurrencyAdjust adjust : foreignCurrencyAdjustList) {
			denominationObject = new DenominationBean();
			if (adjust.getTransactionType().equals(Constants.R)) {
				denominationObject.setDenominationID(adjust.getFsDenominationId().getDenominationId().intValue());
				denominationObject.setDenominationDesc(adjust.getFsDenominationId().getDenominationDesc());
				denominationObject.setDenominationAmount(adjust.getFsDenominationId().getDenominationAmount());
				denominationObject.setPurchaseAmount(adjust.getFsDenominationId().getDenominationAmount());
				denominationObject.setExchangeRate(adjust.getExchangeRate());
				denominationObject.setNoOfNotes(adjust.getNotesQuantity() == null ? "" : adjust.getNotesQuantity().toString());
				BigDecimal tempAmount = adjust.getNotesQuantity().multiply(adjust.getFsDenominationId().getDenominationAmount());
				BigDecimal tempSaleAmount = tempAmount.multiply(adjust.getExchangeRate());
				denominationObject.setSalesAmount(tempSaleAmount);
				saleAmount = saleAmount.add(tempSaleAmount);
				totalPurchageAmount = totalPurchageAmount.add(tempAmount);
				denominationRefundBeanList.add(denominationObject);
			}
		}
	}
	
	public void clear() {
		setSuccessPanel(false);
		setFcPurchageLabel(true);
		setLocalcashpaidtoCustomerLabel(false);
		setBooPurchasePanel(true);
		setBooPanelDatatable(false);
		setBooRefundPanel(false);
		setSignaturePanel(false);
		setFinanceMonth(0);
		setId(null);
		setName(null);
		setMobile(null);
		setCurrencyId(null);
		setSaleCurrencyCode(null);
		denominationBeanList.clear();
		setTotalPurchaseAmount(0.0);
		setSaleAmount(null);
		// setAvgExchageRate(0.0);
		setRemarks(null);
		setSignature(null);
		setSourceOfIncomes(null);
		setPurposeOfTransactions(null);
		setForNextPanels(false);
		setVarToKeepSerial(null);
		setNeededPurchaseAmount(0.0);
		setMyImage(null);
		setDocumentFinanceYear(null);
		setDocumentNo(null);
		lstData.clear();
		lstRefundData.clear();
	}

	public void clearInputData() {
		enquiryPageNavigation();
	}

	public void enquiryPageNavigation() {
		clear();
		setSuccessPanel(false);
		setBooRenderFirstPanel(true);
		setBooRenderSecondPanel(false);
		setBooPurchasePanel(false);
		userFinancialYearList = foreignCurrencyPurchaseService.getAllDocumentYear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../foreigncurrency/foreigncurrencypurchaseenquiry.xhtml");
		} catch (Exception e) {
			log.info("Problem to redirect");
		}
	}

	public void clickOnExit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
}