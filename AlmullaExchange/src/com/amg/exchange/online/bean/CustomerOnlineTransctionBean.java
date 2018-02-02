package com.amg.exchange.online.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.SourceOfIncomeDescription;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.online.model.RatePlaceOrder;
import com.amg.exchange.online.model.RatePlaceOrderAddlData;
import com.amg.exchange.online.service.IBranchStaffGSMRateService;
import com.amg.exchange.online.service.IPlaceOrederBranchSupportService;
import com.amg.exchange.remittance.bean.AddAdditionalBankData;
import com.amg.exchange.remittance.bean.AddDynamicLebel;
import com.amg.exchange.remittance.model.AdditionalBankDetailsView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.AdditionalDataDisplayView;
import com.amg.exchange.remittance.model.BeneficaryAccount;
import com.amg.exchange.remittance.model.BeneficaryMaster;
import com.amg.exchange.remittance.model.PaymentMode;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("customerOnlineTransctionBean")
@Scope("session")
public class CustomerOnlineTransctionBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger
			.getLogger(CustomerOnlineTransctionBean.class);

	// page level variables
	private String beneficiaryCountryId;
	private String beneficiaryName;
	private String beneficiaryBankId;
	private BigDecimal currencyId;
	private String beneficiaryCurrencyId;
	private BigDecimal tranxAmount;
	private BigDecimal specialRateOffer;
	private BigDecimal rateOfferedPk;
	private BigDecimal documentNumber;
	private BigDecimal documentFinanceYear;
	private BigDecimal beneAccountSeqId;
	private BigDecimal routingCountry;
	private BigDecimal routingBank;
	private BigDecimal remitRemittanceId;
	private BigDecimal remitDeliveryId;
	private String paymentModeCode;
	private BigDecimal customerId;
	private BigDecimal beneficiaryMasterSeqId;
	private String beneAccountNumber;
	private BigDecimal dataserviceid;
	private BigDecimal routingBranch;
	// addtional & source of income Details
	private BigDecimal sourceId;
	private String exceptionMessage;
	private boolean additionalCheck = true;
	private BigDecimal minLenght;
	private BigDecimal maxLenght;

	// common Variables
	private String errorMessage;

	private List<SourceOfIncomeDescription> lstSourceofIncome = new ArrayList<SourceOfIncomeDescription>();
	private List<AddDynamicLebel> listDynamicLebel = new ArrayList<AddDynamicLebel>();
	private List<PaymentMode> lstFetchAllPayMode=new ArrayList<PaymentMode>();
	private List<AddAdditionalBankData> listAdditionalBankDataTable = new ArrayList<AddAdditionalBankData>();

	SessionStateManage session = new SessionStateManage();

	@Autowired
	IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	IPlaceOrederBranchSupportService placeOrederBranchSupportService;
	
	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	private ApplicationContext appContext;
	
	@Autowired
	IBranchStaffGSMRateService branchStaffGSMRateService;

	public String getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}

	public void setBeneficiaryCountryId(String beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBeneficiaryBankId() {
		return beneficiaryBankId;
	}

	public void setBeneficiaryBankId(String beneficiaryBankId) {
		this.beneficiaryBankId = beneficiaryBankId;
	}

	public String getBeneficiaryCurrencyId() {
		return beneficiaryCurrencyId;
	}

	public void setBeneficiaryCurrencyId(String beneficiaryCurrencyId) {
		this.beneficiaryCurrencyId = beneficiaryCurrencyId;
	}

	public BigDecimal getTranxAmount() {
		return tranxAmount;
	}

	public void setTranxAmount(BigDecimal tranxAmount) {
		this.tranxAmount = tranxAmount;
	}

	public BigDecimal getSpecialRateOffer() {
		return specialRateOffer;
	}

	public void setSpecialRateOffer(BigDecimal specialRateOffer) {
		this.specialRateOffer = specialRateOffer;
	}

	public BigDecimal getRateOfferedPk() {
		return rateOfferedPk;
	}

	public void setRateOfferedPk(BigDecimal rateOfferedPk) {
		this.rateOfferedPk = rateOfferedPk;
	}

	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}

	public BigDecimal getDocumentFinanceYear() {
		return documentFinanceYear;
	}

	public void setDocumentFinanceYear(BigDecimal documentFinanceYear) {
		this.documentFinanceYear = documentFinanceYear;
	}

	public BigDecimal getSourceId() {
		return sourceId;
	}

	public void setSourceId(BigDecimal sourceId) {
		this.sourceId = sourceId;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public boolean isAdditionalCheck() {
		return additionalCheck;
	}

	public void setAdditionalCheck(boolean additionalCheck) {
		this.additionalCheck = additionalCheck;
	}

	public BigDecimal getMinLenght() {
		return minLenght;
	}

	public void setMinLenght(BigDecimal minLenght) {
		this.minLenght = minLenght;
	}

	public BigDecimal getMaxLenght() {
		return maxLenght;
	}

	public void setMaxLenght(BigDecimal maxLenght) {
		this.maxLenght = maxLenght;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<SourceOfIncomeDescription> getLstSourceofIncome() {
		return lstSourceofIncome;
	}

	public void setLstSourceofIncome(
			List<SourceOfIncomeDescription> lstSourceofIncome) {
		this.lstSourceofIncome = lstSourceofIncome;
	}

	public List<AddDynamicLebel> getListDynamicLebel() {
		return listDynamicLebel;
	}

	public void setListDynamicLebel(List<AddDynamicLebel> listDynamicLebel) {
		this.listDynamicLebel = listDynamicLebel;
	}

	public List<AddAdditionalBankData> getListAdditionalBankDataTable() {
		return listAdditionalBankDataTable;
	}

	public void setListAdditionalBankDataTable(
			List<AddAdditionalBankData> listAdditionalBankDataTable) {
		this.listAdditionalBankDataTable = listAdditionalBankDataTable;
	}

	public BigDecimal getRoutingCountry() {
		return routingCountry;
	}

	public void setRoutingCountry(BigDecimal routingCountry) {
		this.routingCountry = routingCountry;
	}

	public BigDecimal getRoutingBank() {
		return routingBank;
	}

	public void setRoutingBank(BigDecimal routingBank) {
		this.routingBank = routingBank;
	}

	public BigDecimal getRemitRemittanceId() {
		return remitRemittanceId;
	}

	public void setRemitRemittanceId(BigDecimal remitRemittanceId) {
		this.remitRemittanceId = remitRemittanceId;
	}

	public BigDecimal getRemitDeliveryId() {
		return remitDeliveryId;
	}

	public void setRemitDeliveryId(BigDecimal remitDeliveryId) {
		this.remitDeliveryId = remitDeliveryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getBeneAccountSeqId() {
		return beneAccountSeqId;
	}

	public void setBeneAccountSeqId(BigDecimal beneAccountSeqId) {
		this.beneAccountSeqId = beneAccountSeqId;
	}

	public List<PaymentMode> getLstFetchAllPayMode() {
		return lstFetchAllPayMode;
	}

	public void setLstFetchAllPayMode(List<PaymentMode> lstFetchAllPayMode) {
		this.lstFetchAllPayMode = lstFetchAllPayMode;
	}
	
	

	public String getPaymentModeCode() {
		return paymentModeCode;
	}

	public void setPaymentModeCode(String paymentModeCode) {
		this.paymentModeCode = paymentModeCode;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	public BigDecimal getBeneficiaryMasterSeqId() {
		return beneficiaryMasterSeqId;
	}

	public void setBeneficiaryMasterSeqId(BigDecimal beneficiaryMasterSeqId) {
		this.beneficiaryMasterSeqId = beneficiaryMasterSeqId;
	}

	public String getBeneAccountNumber() {
		return beneAccountNumber;
	}

	public void setBeneAccountNumber(String beneAccountNumber) {
		this.beneAccountNumber = beneAccountNumber;
	}

	public BigDecimal getDataserviceid() {
		return dataserviceid;
	}

	public void setDataserviceid(BigDecimal dataserviceid) {
		this.dataserviceid = dataserviceid;
	}

	public BigDecimal getRoutingBranch() {
		return routingBranch;
	}

	public void setRoutingBranch(BigDecimal routingBranch) {
		this.routingBranch = routingBranch;
	}

	// Source income details
	public void getSourceofIncomeDetails() {
		lstSourceofIncome.clear();
		List<SourceOfIncomeDescription> lstSource = foreignCurrencyPurchaseService
				.getSourceofIncome(session.getLanguageId());
		if (lstSource.size() != 0) {
			lstSourceofIncome.addAll(lstSource);
		}
	}

	// Additional Bank Details Starts
	public void dynamicLevel() throws AMGException {
		listDynamicLebel.clear();
		setExceptionMessage("");
		List<AdditionalDataDisplayView> serviceAppRuleList = iPersonalRemittanceService
				.getAdditionalDataFromServiceApplicability(
						session.getCountryId(), getRoutingCountry(),
						getCurrencyId(), getRemitRemittanceId(),
						getRemitDeliveryId());
		if (serviceAppRuleList.size() > 0) {
			for (AdditionalDataDisplayView serviceRule : serviceAppRuleList) {
				AddDynamicLebel addDynamic = new AddDynamicLebel();
				addDynamic.setLebelId(serviceRule
						.getServiceApplicabilityRuleId());
				addDynamic.setFieldLength(serviceRule.getFieldLength());
				if (serviceRule.getFieldDescription() != null) {
					addDynamic.setLebelDesc(serviceRule.getFieldDescription());
				}

				addDynamic.setFlexiField(serviceRule.getFlexField());
				addDynamic.setValidation(serviceRule.getValidationsReq());
				addDynamic.setNavicable(serviceRule.getIsRendered());
				addDynamic.setMinLenght(serviceRule.getMinLength());
				addDynamic.setMaxLenght(serviceRule.getMaxLength());

				if (serviceRule.getIsRequired() != null
						&& serviceRule.getIsRequired().equalsIgnoreCase(
								Constants.Yes)) {
					addDynamic.setMandatory("*");
					addDynamic.setRequired(true);
				}

				listDynamicLebel.add(addDynamic);
			}

			setAdditionalCheck(true);

		} else {
			setAdditionalCheck(false);
		}
	}

	public void matchData() {
		setExceptionMessage(null);
		listAdditionalBankDataTable.clear();
		try {
			for (AddDynamicLebel dyamicLabel : listDynamicLebel) {
				AddAdditionalBankData adddata = new AddAdditionalBankData();
				if (dyamicLabel.getValidation() != null
						&& dyamicLabel.getValidation().equalsIgnoreCase(
								Constants.Yes)) {
					List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
							.getDynamicLevelMatch(getRoutingCountry(),
									dyamicLabel.getFlexiField());
					if (listAdditinalBankfield.size() > 0) {
						for (AdditionalBankRuleMap listAdd : listAdditinalBankfield) {

							List<AdditionalBankDetailsView> listAdditionaView = iPersonalRemittanceService
									.getAmiecDetails(getCurrencyId(),
											getRoutingBank(),
											getRemitRemittanceId(),
											getRemitDeliveryId(),
											getRoutingCountry(),
											listAdd.getFlexField());

							if (listAdditionaView.size() > 0) {

								// setting dynamic functionality
								adddata.setMandatory(dyamicLabel.getMandatory());
								if (dyamicLabel.getMinLenght() != null) {
									adddata.setMinLenght(dyamicLabel
											.getMinLenght().intValue());
								} else {
									adddata.setMinLenght(0);
								}
								if (dyamicLabel.getMaxLenght() != null) {
									adddata.setMaxLenght(dyamicLabel
											.getMaxLenght());
								} else {
									adddata.setMaxLenght(new BigDecimal(50));
								}
								adddata.setFieldLength(dyamicLabel
										.getFieldLength());
								adddata.setRequired(dyamicLabel.getRequired());

								adddata.setAdditionalBankRuleFiledId(listAdd
										.getAdditionalBankRuleId());
								adddata.setFlexiField(listAdd.getFlexField());
								if (listAdd.getFieldName() != null) {
									adddata.setAdditionalDesc(listAdd
											.getFieldName());
								} else {
									setExceptionMessage((getExceptionMessage()
											.equalsIgnoreCase("") ? "" : ",")
											+ dyamicLabel.getFlexiField());
								}
								adddata.setRenderInputText(false);
								adddata.setRenderSelect(true);
								adddata.setRenderOneSelect(false);
								adddata.setListadditionAmiecData(listAdditionaView);

							}
						}

						if (getExceptionMessage() != null
								&& !getExceptionMessage().equalsIgnoreCase("")) {
							setAdditionalCheck(false);
							setExceptionMessage(getExceptionMessage());
							RequestContext.getCurrentInstance().execute(
									"dataexception.show();");
						} else {
							setAdditionalCheck(true);
							setExceptionMessage(null);
						}
					}
				} else {
					/*
					 * if (dyamicLabel.getValidation()!=null &&
					 * dyamicLabel.getValidation
					 * ().equalsIgnoreCase(Constants.No)) {
					 */
					adddata.setMandatory(dyamicLabel.getMandatory());
					if (dyamicLabel.getMinLenght() != null) {
						adddata.setMinLenght(dyamicLabel.getMinLenght()
								.intValue());
					} else {
						adddata.setMinLenght(0);
					}
					if (dyamicLabel.getMaxLenght() != null) {
						adddata.setMaxLenght(dyamicLabel.getMaxLenght());
					} else {
						adddata.setMaxLenght(new BigDecimal(50));
					}

					adddata.setFieldLength(dyamicLabel.getFieldLength());
					adddata.setRequired(dyamicLabel.getRequired());
					adddata.setRenderInputText(true);
					adddata.setRenderSelect(false);
					adddata.setRenderOneSelect(false);
					adddata.setFlexiField(dyamicLabel.getFlexiField());
					if (dyamicLabel.getLebelDesc() != null) {
						adddata.setAdditionalDesc(dyamicLabel.getLebelDesc());
					} else {
						List<AdditionalBankRuleMap> listAdditinalBankfield = iPersonalRemittanceService
								.getDynamicLevelMatch(getRoutingCountry(),
										dyamicLabel.getFlexiField());
						if (listAdditinalBankfield.size() > 0) {
							if (listAdditinalBankfield.get(0).getFieldName() != null) {
								adddata.setAdditionalDesc(listAdditinalBankfield
										.get(0).getFieldName());
							} else {
								setExceptionMessage((getExceptionMessage()
										.equalsIgnoreCase("") ? "" : ",")
										+ dyamicLabel.getFlexiField());
							}
						} else {
							setExceptionMessage((getExceptionMessage()
									.equalsIgnoreCase("") ? "" : ",")
									+ dyamicLabel.getFlexiField());
						}
					}

				}
				listAdditionalBankDataTable.add(adddata);
			}

			if (getExceptionMessage() != null
					&& !getExceptionMessage().equalsIgnoreCase("")) {
				setAdditionalCheck(false);
				setExceptionMessage(getExceptionMessage());
				RequestContext.getCurrentInstance().execute(
						"dataexception.show();");
			} else {
				setAdditionalCheck(true);
				setExceptionMessage(null);
			}

		} catch (Exception e) {
			log.info(e);
		}
	}

	// Additional Bank Details Starts

			//Payment Details
			public void toFetchPaymentDetails(){
				lstFetchAllPayMode.clear();
				List<PaymentMode> list=placeOrederBranchSupportService.fetchPaymodeDescForOnlineCustomer(Constants.Yes);
				if(list.size() !=0){
					lstFetchAllPayMode.addAll(list); 
					setPaymentModeCode(lstFetchAllPayMode.get(0).getPaymentCode());
				}

			}
	// Save Starts
	public void save() {
		toFetchPaymentDetails();
		RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
		ratePlaceOrder.setRatePlaceOrderId(getRateOfferedPk());
		ratePlaceOrder.setSourceOfincomeId(getSourceId());
		ratePlaceOrder.setCollectionMode(getPaymentModeCode());
		BeneficaryMaster beneficaryMaster=new BeneficaryMaster();
		beneficaryMaster.setBeneficaryMasterSeqId(getBeneficiaryMasterSeqId());
		ratePlaceOrder.setBeneficiaryMasterId(beneficaryMaster);
		BeneficaryAccount beneficaryAccount=new BeneficaryAccount();
		beneficaryAccount.setBeneficaryAccountSeqId(getBeneAccountSeqId());
		ratePlaceOrder.setAccountSeqquenceId(beneficaryAccount);
		ratePlaceOrder.setBeneficiaryAccountNo(getBeneAccountNumber());
		ratePlaceOrder.setApprovedBy(session.getUserName());
		//procedure variables
		ratePlaceOrder.setRoutingCountryId(getRoutingCountry());
		ratePlaceOrder.setRoutingBankId(getRoutingBank());
		ratePlaceOrder.setServiceMasterId(getDataserviceid());
		ratePlaceOrder.setRemittanceModeId(getRemitRemittanceId());
		ratePlaceOrder.setDeliveryModeId(getRemitDeliveryId());
		ratePlaceOrder.setRoutingBranchId(getRoutingBranch());
		
		HashMap<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put("PLACE_ORDER_PK", getRateOfferedPk());
		//saveMap.put("CUSTOMER_VISIT_TIME", getVisitTime());
		saveMap.put("SOURCE_OF_INCOME", getSourceId());
		saveMap.put("PAYMENT_MODE", getPaymentModeCode());
		saveMap.put("BENEFICIARY_MASTER_ID", getBeneficiaryMasterSeqId());
		saveMap.put("BENEFICIARY_ACC_SEQ_ID", getBeneAccountSeqId());
		saveMap.put("BENEFICIARY_ACC_NO", getBeneAccountNumber());
		saveMap.put("USER_NAME", session.getUserName());
		//procedure variables 
		saveMap.put("ROUTING_COUNTRY_ID",getRoutingCountry());
		saveMap.put("ROUTING_BANK_ID", getRoutingBank());
		saveMap.put("DATA_SERVICE_ID",getDataserviceid());
		saveMap.put("REMIT_MODE", getRemitRemittanceId());
		saveMap.put("DELIVERY_MODE", getRemitDeliveryId());
		saveMap.put("ROUTING_BRANCH", getRoutingBranch());
		
		List<RatePlaceOrderAddlData> lstRatePlaceOrderAddlData = saveAdditionalInstnData();

		saveMap.put("PLACE_ORDER_ADDL_DATA", lstRatePlaceOrderAddlData);

		try {
			//placeOrederBranchSupportService.saveOrUpdatePlaceOrderAddlData(saveMap);
			branchStaffGSMRateService.saveOrUpdatePlaceOrderAddlData(saveMap);
		} catch (Exception e) {
			setErrorMessage(e.getMessage());
			
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		// RequestContext.getCurrentInstance().execute("complete.show();");
	
		createRemittanceApplicationForPlaceOrder();
	}
	
	public void clearFields(){
		//setRateOfferedPk(null);
		setBeneficiaryName(null);
		setBeneficiaryCountryId(null);
		setBeneficiaryBankId(null);
		setBeneAccountSeqId(null);
		setBeneficiaryCurrencyId(null);
		setCurrencyId(null);
		setSpecialRateOffer(null);
		setTranxAmount(null);
		setSourceId(null);
		setRemitRemittanceId(null);
		setRemitDeliveryId(null);
		
	}
	
	//save Additional Data
	public List<RatePlaceOrderAddlData> saveAdditionalInstnData()
	{
		List<RatePlaceOrderAddlData> lstAddInstrData = new ArrayList<RatePlaceOrderAddlData>();
		Document document = new Document();
		document.setDocumentID(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),
				new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1")).get(0).getDocumentID());
		// company Id
		CompanyMaster companymaster = new CompanyMaster();
		companymaster.setCompanyId(session.getCompanyId());
		// Application Country
		CountryMaster countrymaster = new CountryMaster();
		countrymaster.setCountryId(session.getCountryId());
		
		for (AddAdditionalBankData dynamicList : listAdditionalBankDataTable) {

			RatePlaceOrderAddlData ratePlaceOrderAddlData = new RatePlaceOrderAddlData();
			/*AdditionalBankRuleMap additionalBank = new AdditionalBankRuleMap();
			if(dynamicList.getAdditionalBankRuleFiledId()!=null){
				additionalBank.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());
				ratePlaceOrderAddlData.setAdditionalBankFieldsId(additionalBank);
			}*/

			// System.out.println("dynamicList.getFlexiField() :"+dynamicList.getFlexiField()+"\t dynamicList.getAmicCode() :"+dynamicList.getAmicCode()); 
			RatePlaceOrder ratePlaceOrder = new RatePlaceOrder();
			ratePlaceOrder.setRatePlaceOrderId(getRateOfferedPk());
			ratePlaceOrderAddlData.setRatePlaceOrder(ratePlaceOrder);
			
			ratePlaceOrderAddlData.setAdditionalBankRuleId(dynamicList.getAdditionalBankRuleFiledId());
			

			ratePlaceOrderAddlData.setFlexFiled(dynamicList.getFlexiField());
			if(dynamicList.getAdditionalBankRuleFiledId()!=null){
				String amiecdec = dynamicList.getVariableName();
				String amicCode=null;
				String amicDesc=null;
				if(amiecdec!=null)
				{

					String [] amiecdecValues =amiecdec.split("-");
					if(amiecdecValues.length>0)
					{
						amicCode=amiecdecValues[0];		

					}
					if(amiecdecValues.length>1)
					{
						amicDesc=amiecdecValues[1];		

					}

				}

				ratePlaceOrderAddlData.setAmiecCode(amicCode);
				ratePlaceOrderAddlData.setFlexFiledValue(amicDesc);
			}else{
				ratePlaceOrderAddlData.setAmiecCode(Constants.AMIEC_CODE);
				ratePlaceOrderAddlData.setFlexFiledValue(dynamicList.getVariableName());
			}

			ratePlaceOrderAddlData.setDocumentId(generalService.getDocument(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER),
				new BigDecimal(session.isExists("languageId") ? session.getSessionValue("languageId") : "1")).get(0).getDocumentID());
			ratePlaceOrderAddlData.setCompanyId(session.getCompanyId());
			ratePlaceOrderAddlData.setApplicationCountryId(session.getCountryId());
			ratePlaceOrderAddlData.setDocumentCode(new BigDecimal(Constants.DOCUMENT_CODE_FOR_PLACEORDER));
			ratePlaceOrderAddlData.setDocumentFinanceYear(getDocumentFinanceYear());
			ratePlaceOrderAddlData.setDocumentNo(getDocumentNumber());
			
			ratePlaceOrderAddlData.setCreatedBy(session.getUserName());
			ratePlaceOrderAddlData.setCreatedDate(new Date());
			

			// iPersonalRemittanceService.saveAdditionalInsData(additionalInsData);

			lstAddInstrData.add(ratePlaceOrderAddlData);
		}
		return lstAddInstrData;
	}
	
	public  void createRemittanceApplicationForPlaceOrder() {
		try{
			HashMap<String,String> inputValues = new HashMap<String,String>();
			inputValues.put("P_COMP_ID", session.getCompanyId().toPlainString());
			inputValues.put("PLACE_ORDER_PK", getRateOfferedPk().toPlainString());
			
			HashMap<String,String> outPutValues=placeOrederBranchSupportService.createRemitAppProcedure(inputValues);
			
			String errMsg=outPutValues.get("P_ERROR_MESG");
			if(errMsg!=null && errMsg.equalsIgnoreCase(""))
			{
				clearFields();
				setRateOfferedPk(null);
				PlaceOrderShoppingCartBean placeOrderShoppingCartBean = (PlaceOrderShoppingCartBean) appContext.getBean("placeOrderShoppingCartBean");
				
				placeOrderShoppingCartBean.pageNavigationCustomerOnlineTrnx(getCustomerId());
				
			}else
			{
				setErrorMessage(errMsg);
				log.error("clickOnOKSave  :"+ errMsg);
				RequestContext.getCurrentInstance().execute("error.show();");
			}
			
			
			
		}catch(Exception exception)
		{
			setErrorMessage(exception.getMessage());
			log.error("clickOnOKSave  :"+ exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
		}
		
	}
	
	public void exit(){
		setCustomerId(null);
		 clearFields();
		    try {
		    	FacesContext.getCurrentInstance().getExternalContext().redirect(session.getMenuPage());
			} catch (NullPointerException ne) {
				setErrorMessage("Method Name::exit");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				RequestContext.getCurrentInstance().execute("error.show();");
				return;
			}  
	}
	

}
