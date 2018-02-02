/*package com.amg.exchange.enquiry.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.bean.CountrywiseFundRequirementHighvalueEnquiryDataTable;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.registration.service.IBranchPageService;
import com.amg.exchange.remittance.bean.CustomerInquiryDataTable;
import com.amg.exchange.remittance.bean.RemittanceReceiptSubreport;
import com.amg.exchange.remittance.bean.RemittanceReportBean;
import com.amg.exchange.remittance.model.CollectionDetailView;
import com.amg.exchange.remittance.model.CollectionPaymentDetailsView;
import com.amg.exchange.remittance.model.PaymentModeDesc;
import com.amg.exchange.remittance.model.PurposeOfRemittanceView;
import com.amg.exchange.remittance.model.RemittanceApplicationView;
import com.amg.exchange.remittance.model.RemittanceTxnView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.IRelationsTypeService;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.treasury.service.IRemittanceModeService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.SessionStateManage;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component("remittanceEnquiryBean")
@Scope("session")
public class RemittanceEnquiryBean {
	private static final Logger LOGGER = Logger.getLogger(RemittanceEnquiryBean.class);
	private String aliasFirstName;
	private String aliasSecondName;
	private String aliasThirdName;
	private String aliasFourthName;
	private BigDecimal docYear;
	private BigDecimal docNumber;
	private BigDecimal customerReference;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private BigDecimal LoyaltyPoints;
	private String dob;
	private BigDecimal nationalityId;
	private String nationality;
	private String mobile;
	private String beneficiaryName;
	private String benenationality;
	private String benefirstName;
	private String beneSecondName;
	private String beneThirdName;
	private String beneFourthName;
	private String beneBankName;
	private String beneBankBranchName;
	private String beneficiaryAccountNumber;
	private String beneCountry;
	private String beneType;
	private String employmentType;
	private Boolean renderPanel;
	private String branchName;
	private String correspondingCountryName;
	private String service;
	private String product;
	private BigDecimal transferNo;
	private BigDecimal applicationNo;
	private Date documentDate;
	private String foreignCurrencyName;
	private String localCurrencyName;
	private String localChargeCurrencName;
	private String localDelivaryCurrencName;
	private String localNetCurrencName;
	private BigDecimal foreignCurrencyAmount;
	private BigDecimal localTransactionAmount;
	private BigDecimal localChargeAmount;
	private BigDecimal localDelivaryAmount;
	private BigDecimal localNetTransactionAmount;
	private String exchangeApplied;
	private String debitAccountNo;
	private String transactionStatus;
	private Date transactionlastUpdated;
	private String remittenceMode;
	private String delivaryMode;
	private String webServiceStatus;
	private String westionUnion;
	private String hvt;
	private String bli;
	private String dealyear;
	
	// Added by subramanian 
	private String collectionMode;
	private String localCommissionCurrencyName;
	private BigDecimal localCommissionAmount;
	private String paymentMode;
	private BigDecimal paymentId;
	private BigDecimal bankId;
	private String bankName;
	
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	
	private BigDecimal documentNo;
	
	private Boolean duplicateReportCheck=false;
	
	private SessionStateManage session = new SessionStateManage();
	private List<UserFinancialYear> userFinancialYearList = new ArrayList<UserFinancialYear>();
	@Autowired
	IStopPaymentService<T> stopPaymentService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBranchPageService<T> iBranchPageService;
	@Autowired
	IRelationsTypeService relationsTypeService;
	@Autowired
	IRemittanceModeService remittanceModeService;
	@Autowired
	DeliveryModeService deliveryModeService;
	@Autowired
	IForeignCurrencyPurchaseService<T> iForeignCurrencyPurchaseService;

	
	public Boolean getDuplicateReportCheck() {
		return duplicateReportCheck;
	}
	public void setDuplicateReportCheck(Boolean duplicateReportCheck) {
		this.duplicateReportCheck = duplicateReportCheck;
	}
	public List<UserFinancialYear> getUserFinancialYearList() {
		userFinancialYearList = iForeignCurrencyPurchaseService.getAllDocumentYear();
		return userFinancialYearList;
	}

	
	public BigDecimal getDocumentNo() {
		return documentNo;
	}
	public void setDocumentNo(BigDecimal documentNo) {
		this.documentNo = documentNo;
	}
	public void setUserFinancialYearList(List<UserFinancialYear> userFinancialYearList) {
		this.userFinancialYearList = userFinancialYearList;
	}

	public String getDealyear() {
		return dealyear;
	}

	public void setDealyear(String dealyear) {
		this.dealyear = dealyear;
	}

	public Date getDocumentDate() {
		return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
		this.documentDate = documentDate;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCorrespondingCountryName() {
		return correspondingCountryName;
	}

	public void setCorrespondingCountryName(String correspondingCountryName) {
		this.correspondingCountryName = correspondingCountryName;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public BigDecimal getTransferNo() {
		return transferNo;
	}

	public void setTransferNo(BigDecimal transferNo) {
		this.transferNo = transferNo;
	}

	public BigDecimal getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(BigDecimal applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getForeignCurrencyName() {
		return foreignCurrencyName;
	}

	public void setForeignCurrencyName(String foreignCurrencyName) {
		this.foreignCurrencyName = foreignCurrencyName;
	}

	public String getLocalCurrencyName() {
		return localCurrencyName;
	}

	public void setLocalCurrencyName(String localCurrencyName) {
		this.localCurrencyName = localCurrencyName;
	}

	public String getLocalChargeCurrencName() {
		return localChargeCurrencName;
	}

	public void setLocalChargeCurrencName(String localChargeCurrencName) {
		this.localChargeCurrencName = localChargeCurrencName;
	}

	public String getLocalDelivaryCurrencName() {
		return localDelivaryCurrencName;
	}

	public void setLocalDelivaryCurrencName(String localDelivaryCurrencName) {
		this.localDelivaryCurrencName = localDelivaryCurrencName;
	}

	public String getLocalNetCurrencName() {
		return localNetCurrencName;
	}

	public void setLocalNetCurrencName(String localNetCurrencName) {
		this.localNetCurrencName = localNetCurrencName;
	}

	public BigDecimal getForeignCurrencyAmount() {
		return foreignCurrencyAmount;
	}

	public void setForeignCurrencyAmount(BigDecimal foreignCurrencyAmount) {
		this.foreignCurrencyAmount = foreignCurrencyAmount;
	}

	public BigDecimal getLocalTransactionAmount() {
		return localTransactionAmount;
	}

	public void setLocalTransactionAmount(BigDecimal localTransactionAmount) {
		this.localTransactionAmount = localTransactionAmount;
	}

	public BigDecimal getLocalChargeAmount() {
		return localChargeAmount;
	}

	public void setLocalChargeAmount(BigDecimal localChargeAmount) {
		this.localChargeAmount = localChargeAmount;
	}

	public BigDecimal getLocalDelivaryAmount() {
		return localDelivaryAmount;
	}

	public void setLocalDelivaryAmount(BigDecimal localDelivaryAmount) {
		this.localDelivaryAmount = localDelivaryAmount;
	}

	public BigDecimal getLocalNetTransactionAmount() {
		return localNetTransactionAmount;
	}

	public void setLocalNetTransactionAmount(BigDecimal localNetTransactionAmount) {
		this.localNetTransactionAmount = localNetTransactionAmount;
	}

	public String getExchangeApplied() {
		return exchangeApplied;
	}

	public void setExchangeApplied(String exchangeApplied) {
		this.exchangeApplied = exchangeApplied;
	}

	public String getDebitAccountNo() {
		return debitAccountNo;
	}

	public void setDebitAccountNo(String debitAccountNo) {
		this.debitAccountNo = debitAccountNo;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public Date getTransactionlastUpdated() {
		return transactionlastUpdated;
	}

	public void setTransactionlastUpdated(Date transactionlastUpdated) {
		this.transactionlastUpdated = transactionlastUpdated;
	}

	public String getRemittenceMode() {
		return remittenceMode;
	}

	public void setRemittenceMode(String remittenceMode) {
		this.remittenceMode = remittenceMode;
	}

	public String getDelivaryMode() {
		return delivaryMode;
	}

	public void setDelivaryMode(String delivaryMode) {
		this.delivaryMode = delivaryMode;
	}

	public String getWebServiceStatus() {
		return webServiceStatus;
	}

	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	public String getWestionUnion() {
		return westionUnion;
	}

	public void setWestionUnion(String westionUnion) {
		this.westionUnion = westionUnion;
	}

	public String getHvt() {
		return hvt;
	}

	public void setHvt(String hvt) {
		this.hvt = hvt;
	}

	public String getBli() {
		return bli;
	}

	public void setBli(String bli) {
		this.bli = bli;
	}

	public String getBenenationality() {
		return benenationality;
	}

	public void setBenenationality(String benenationality) {
		this.benenationality = benenationality;
	}

	public String getBeneCountry() {
		return beneCountry;
	}

	public void setBeneCountry(String beneCountry) {
		this.beneCountry = beneCountry;
	}

	public String getBeneType() {
		return beneType;
	}

	public void setBeneType(String beneType) {
		this.beneType = beneType;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	public String getBeneficiaryName() {
		return beneficiaryName;
	}

	public void setBeneficiaryName(String beneficiaryName) {
		this.beneficiaryName = beneficiaryName;
	}

	public String getBenefirstName() {
		return benefirstName;
	}

	public void setBenefirstName(String benefirstName) {
		this.benefirstName = benefirstName;
	}

	public String getBeneSecondName() {
		return beneSecondName;
	}

	public void setBeneSecondName(String beneSecondName) {
		this.beneSecondName = beneSecondName;
	}

	public String getBeneThirdName() {
		return beneThirdName;
	}

	public void setBeneThirdName(String beneThirdName) {
		this.beneThirdName = beneThirdName;
	}

	public String getBeneFourthName() {
		return beneFourthName;
	}

	public void setBeneFourthName(String beneFourthName) {
		this.beneFourthName = beneFourthName;
	}

	public String getBeneBankName() {
		return beneBankName;
	}

	public void setBeneBankName(String beneBankName) {
		this.beneBankName = beneBankName;
	}

	public String getBeneBankBranchName() {
		return beneBankBranchName;
	}

	public void setBeneBankBranchName(String beneBankBranchName) {
		this.beneBankBranchName = beneBankBranchName;
	}

	public String getBeneficiaryAccountNumber() {
		return beneficiaryAccountNumber;
	}

	public void setBeneficiaryAccountNumber(String beneficiaryAccountNumber) {
		this.beneficiaryAccountNumber = beneficiaryAccountNumber;
	}

	public BigDecimal getCustomerReference() {
		return customerReference;
	}

	public void setCustomerReference(BigDecimal customerReference) {
		this.customerReference = customerReference;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public BigDecimal getLoyaltyPoints() {
		return LoyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		LoyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getNationalityId() {
		return nationalityId;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public void setNationalityId(BigDecimal nationalityId) {
		this.nationalityId = nationalityId;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getDocNumber() {
		return docNumber;
	}

	public void setDocNumber(BigDecimal docNumber) {
		this.docNumber = docNumber;
	}

	public Boolean getRenderPanel() {
		return renderPanel;
	}

	public void setRenderPanel(Boolean renderPanel) {
		this.renderPanel = renderPanel;
	}

	public BigDecimal getDocYear() {
		return docYear;
	}

	public void setDocYear(BigDecimal docYear) {
		this.docYear = docYear;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAliasFirstName() {
		return aliasFirstName;
	}

	public void setAliasFirstName(String aliasFirstName) {
		this.aliasFirstName = aliasFirstName;
	}

	public String getAliasSecondName() {
		return aliasSecondName;
	}

	public void setAliasSecondName(String aliasSecondName) {
		this.aliasSecondName = aliasSecondName;
	}

	public String getAliasThirdName() {
		return aliasThirdName;
	}

	public void setAliasThirdName(String aliasThirdName) {
		this.aliasThirdName = aliasThirdName;
	}

	public String getAliasFourthName() {
		return aliasFourthName;
	}

	public void setAliasFourthName(String aliasFourthName) {
		this.aliasFourthName = aliasFourthName;
	}
	
	
	// Add By Subramanian 

	
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
	public String getPaymentMode() {
		return paymentMode;
	}

	

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public String getCollectionMode() {
		return collectionMode;
	}
	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public BigDecimal getLocalCommissionAmount() {
		return localCommissionAmount;
	}

	public void setLocalCommissionAmount(BigDecimal localCommissionAmount) {
		this.localCommissionAmount = localCommissionAmount;
	}
	

	public BigDecimal getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(BigDecimal paymentId) {
		this.paymentId = paymentId;
	}
	
	public String getLocalCommissionCurrencyName() {
		return localCommissionCurrencyName;
	}

	public void setLocalCommissionCurrencyName(String localCommissionCurrencyName) {
		this.localCommissionCurrencyName = localCommissionCurrencyName;
	}
	//End By subramanian




	

	public void fetchData() {
		LOGGER.info("Entering into fetchData method");
		System.out.println(session);
		RemittanceTxnView beanTransaction = null;
		
		CollectDetail collectionDetail = null;
		PaymentModeDesc paymentModeDesc = null;
		BankMaster bankMaster = null;
	//	beanTransaction = stopPaymentService.viewTransactiondetailsbyDocumentNo(getDocNumber(), session.getCountryId(), getDocYear(), session.getCompanyId());
		
		beanTransaction = stopPaymentService.getRemittanceTransactionDetailsfromView(getDocNumber(), session.getCountryId(), getDocYear(), session.getCompanyId());
		
		if (beanTransaction == null) {
			RequestContext.getCurrentInstance().execute("datanotfound.show();");
			clearEnquiryBean();
		} else {
			setRenderPanel(true);
			LOGGER.info("Data Availble in Database");
			List<Customer> customerList = null;
			if (beanTransaction.getCustomerId() != null) {
				customerList = iBranchPageService.getCustomerInfo(beanTransaction.getCustomerId());
				if (customerList != null && !customerList.isEmpty()) {
					setCustomerReference(customerList.get(0).getCustomerReference());
					setFirstName(customerList.get(0).getFirstName());
					setMiddleName(customerList.get(0).getMiddleName());
					setLastName(customerList.get(0).getLastName());
					setMobile(customerList.get(0).getMobile());
					setGender(customerList.get(0).getGender());
					setNationalityId(customerList.get(0).getFsCountryMasterByNationality().getCountryId());
					// need
					if(customerList.get(0).getFsCountryMasterByNationality().getCountryId()!=null) {
					setNationality(generalService.getNationalityName(session.getLanguageId(), customerList.get(0).getFsCountryMasterByNationality().getCountryId()));
					}
					if (customerList.get(0).getDateOfBirth() != null) {
						String pattern = "dd/MM/yyyy";
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						try {
							setDob(format.format(customerList.get(0).getDateOfBirth()));
						} catch (Exception e) {
						}
					}
				}
				
				
				
				
					//setBeneficiaryName(beanTransaction.getBenefeciaryFirstName());
					setBenefirstName(beanTransaction.getBenefeciaryFirstName());
					setBeneSecondName(beanTransaction.getBenefeciarySecondName());
					setBeneThirdName(beanTransaction.getBenefeciaryThirdName());
					setBeneFourthName(beanTransaction.getBenefeciaryFourthName());
					setBeneBankName(beanTransaction.getBeneficiaryBank());
					setBeneBankBranchName(beanTransaction.getBenefeciaryBranch());
					setBeneficiaryAccountNumber(beanTransaction.getBenefeciaryAccountNo());
						setAliasFirstName(beanTransaction.getBenefeciaryFirstName());
						setAliasSecondName(beanTransaction.getBenefeciaryFirstName());
						setAliasThirdName(beanTransaction.getBenefeciaryFirstName());
						setAliasFourthName(beanTransaction.getBenefeciaryFirstName());
						
						setApplicationNo(beanTransaction.getApplicationDocumentNo());
						setTransferNo(beanTransaction.getDocumentNo());
						
						//setCorrespondingCountryName(generalService.getCountryName(session.getLanguageId(),beanTransaction.getCosespondingCountryId()));
						
						String localCurrencyName =generalService.getCurrencyName(beanTransaction.getLocalChargeCurrencyId());
						
						String foreignCurrencyName =generalService.getCurrencyName(beanTransaction.getForeignCurrencyId());
						
						setForeignCurrencyName(foreignCurrencyName);
						
						setLocalCurrencyName(localCurrencyName);
						
						setLocalChargeCurrencName(localCurrencyName);
						
						setLocalDelivaryCurrencName(localCurrencyName);
						
						setLocalNetCurrencName(localCurrencyName);
						
						setLocalCommissionCurrencyName(localCurrencyName);
						
						setLocalCommissionAmount(beanTransaction.getLocalCommissionAmount());
						
						setLocalNetTransactionAmount(beanTransaction.getLocalNetTransactionAmount());
						setExchangeApplied(beanTransaction.getExchangeRateApplied().toString());
						setDebitAccountNo(beanTransaction.getDebitAccountNo());
						
						setRemittenceMode(beanTransaction.getRemittanceDescription());
						
						setDelivaryMode(beanTransaction.getDeliveryDescription());
						setForeignCurrencyAmount(beanTransaction.getForeignTransactionAmount());
						setDealyear(getDocYear().toPlainString());
						setWebServiceStatus(null);
						setWestionUnion(null);
						setHvt(null);
						setBli(null);
						setTransactionlastUpdated(null);
						setTransactionStatus(beanTransaction.getTranscationStatus());
						setLocalTransactionAmount(beanTransaction.getLocalTransactionAmount());
						setLocalChargeAmount(beanTransaction.getLocalChargeAmount());
						setLocalDelivaryAmount(beanTransaction.getLocalDeliveryAmount());
						
						collectionDetail = stopPaymentService.getCollectionDetails(beanTransaction.getCollectionDocCode(), beanTransaction.getCollectionDocFinanceYear(), beanTransaction.getCollectionDocumentNo());
								
						if(collectionDetail !=null){
							setCollectionMode(collectionDetail.getCollectionMode());
							
							paymentModeDesc = stopPaymentService.getPaymentDescRec(collectionDetail.getPaymentModeId(),session.getLanguageId());
							
							if(paymentModeDesc !=null){
								setPaymentMode(paymentModeDesc.getLocalPaymentName());
							}
							
						}
						
						bankMaster = stopPaymentService.getArabicBankName(beanTransaction.getBankId());
						
						if(bankMaster != null){
							setBankName(bankMaster.getBankFullName());
						}
						
						
						
						
						
				//		setBenenationality(generalService.getNationalityName(session.getLanguageId(), new BigDecimal(beanTransaction.getCustomerId()`)));
				//		setBeneCountry(generalService.getCountryName(session.getLanguageId(),acoountDetails.getBeneficaryCountry().getCountryId()));
				//			setBeneType(relationsTypeService.getEngRelation(relationship.getRelations().getRelationsId()));
						} else if (session.getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							setBeneType(relationsTypeService.getArabicRelation(relationship.getRelations().getRelationsId()));
						}
				List<CountryBranch> cBranch = null;
				if (beanTransaction.getBranchId().getBranchId() != null && beanTransaction.getApplicationCountryId().getCountryId() != null) {
					cBranch = generalService.getBranchDetailsForToLocation(beanTransaction.getApplicationCountryId().getCountryId(), beanTransaction.getBranchId().getBranchId());
				}
				if (cBranch != null && !cBranch.isEmpty()) {
					setBranchName(cBranch.get(0).getBranchName());
				}
				if (beanTransaction.getCorespondingCountryId().getCountryId() != null) {
					
				}
				
				
				setDocumentDate(beanTransaction.getDocumentDate());
				setDealyear(beanTransaction.getDocumentFinanceYear().toString());
				if (beanTransaction.getForeignCurrencyId().getCurrencyId() != null) {
					
				}
				setForeignCurrencyAmount(beanTransaction.getForeignTranxAmount());
				if (beanTransaction.getLocalTranxCurrencyId().getCurrencyId() != null) {
					
				}
				setLocalTransactionAmount(beanTransaction.getLocalTranxAmount());
				if (beanTransaction.getLocalChargeCurrencyId().getCurrencyId() != null) {
				}
				setLocalChargeAmount(beanTransaction.getLocalChargeAmount());
				if (beanTransaction.getLocalDeliveryCurrencyId().getCurrencyId() != null) {
				
				}
				setLocalDelivaryAmount(beanTransaction.getLocalDeliveryAmount());
				if (beanTransaction.getLocalNetCurrencyId().getCurrencyId() != null) {
			
				}
				
				String remittenceMode = null;
				setTransactionStatus(beanTransaction.getTransactionStatus());
				if (beanTransaction.getRemittanceModeId() != null) {
					remittenceMode = remittanceModeService.getRemittanceDesc(beanTransaction.getRemittanceModeId().getRemittanceModeId());
				}
				setRremittenceMode(remittenceMode == null ? "" : remittenceMode);
				String delivaryMode = null;
				if (beanTransaction.getDeliveryModeId().getDeliveryModeId()!= null) {
					delivaryMode = deliveryModeService.getEngDelivery(beanTransaction.getDeliveryModeId().getDeliveryModeId());
				}
				setDelivaryMode(delivaryMode == null ? "" : delivaryMode);
				setWebServiceStatus(beanTransaction.getWebServiceStatus());
				setWestionUnion(beanTransaction.getWesternUnionMtcno());
				setHvt(beanTransaction.getHighValueTranx());
				setBli(beanTransaction.getBlackListIndicator());
				setTransactionlastUpdated(beanTransaction.getTransactionUpdatedDate());
			}
		}
		LOGGER.info("Exit into fetchData method");
	}

	public void clearEnquiryBean() {
		setDocNumber(null);
		setDocYear(null);
		setRenderPanel(false);
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void remittancetEnquirypageNavigation() {
		clearEnquiryBean();
		LOGGER.info("Entering into stopPaymentCollectionpageNavigation method");
		setRenderPanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittenceEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit into stopPaymentCollectionpageNavigation method");
	}
	
	
	public void populateValue()
	{
		System.out.println("Entering into populateValue method");
		if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("customerData") != null) {
			CustomerInquiryDataTable customerData = (CustomerInquiryDataTable)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("customerData");
			clearAllFields();
			setDocNumber(customerData.getTransactionNumber());
			setDocYear(customerData.getDocumentFinanceYear());
			setDocumentNo(customerData.getTransactionNumber());
	//		setDocNumber(new BigDecimal(99999900));
			
			fetchData();
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("customerData");
			System.out.println(customerData);
		}
			
	}
	
	
	
	
	
	public void clearAllFields() {
		setAliasFirstName(null);
		setAliasSecondName(null);
		setAliasThirdName(null);
		setAliasFourthName(null);
		setDocYear(null);
		setDocNumber(null);
		setCustomerReference(null);
		setFirstName(null);
		setMiddleName(null);
		setLastName(null);
		setGender(null);
		setLoyaltyPoints(null);
		setDob(null);
		setNationalityId(null);
		setNationality(null);
		setMobile(null);
		setBeneficiaryName(null);
		setBenenationality(null);
		setBenefirstName(null);
		setBeneSecondName(null);
		setBeneThirdName(null);
		setBeneFourthName(null);
		setBeneBankName(null);
		setBeneBankBranchName(null);
		setBeneficiaryAccountNumber(null);
		setBeneCountry(null);
		setBeneType(null);
		setEmploymentType(null);
		setBranchName(null);
		setCorrespondingCountryName(null);
		setService(null);
		setProduct(null);
		setTransferNo(null);
		setApplicationNo(null);
		setDocumentDate(null);
		setForeignCurrencyName(null);
		setLocalCurrencyName(null);
		setLocalChargeCurrencName(null);
		setLocalDelivaryCurrencName(null);
		setLocalNetCurrencName(null);
		setForeignCurrencyAmount(null);
		setLocalTransactionAmount(null);
		setLocalChargeAmount(null);
		setLocalDelivaryAmount(null);
		setLocalNetTransactionAmount(null);
		setExchangeApplied(null);
		setDebitAccountNo(null);
		setTransactionStatus(null);
		setTransactionlastUpdated(null);
		setRemittenceMode(null);
		setDelivaryMode(null);
		setWebServiceStatus(null);
		setWestionUnion(null);
		setHvt(null);
		setBli(null);
		setDealyear(null);
		setLocalCommissionAmount(null);
		setPaymentMode(null);
		setPaymentId(null);
		setLocalCommissionCurrencyName(null);
		setBankName(null);
		setBankId(null);
		
	}
	
	
/////////////////////////////////////////////////// REPORT CODE  ///////////////////////////////////////////////
//Jasper Report generation
private JasperPrint jasperPrint;
@Autowired
ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;
@Autowired
ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

@Autowired
IPersonalRemittanceService iPersonalRemittanceService;
private List<RemittanceReceiptSubreport> remittanceReceiptSubreportList;
private List<CollectionDetailView> collectionViewList = new CopyOnWriteArrayList<CollectionDetailView>();
public void generatePersonalRemittanceReceiptReport(ActionEvent actionEvent){
try {
fetchRemittanceReceiptReportData(getDocumentNo());
remittanceReceiptReportInit();
HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
httpServletResponse.addHeader("Content-disposition","attachment; filename=RemittanceReceiptReport.pdf");
ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
FacesContext.getCurrentInstance().responseComplete();
} catch (Exception e) {
System.out.println("EXCEPTION :"+e.getMessage());
}

}

public void remittanceReceiptReportInit() throws JRException {
ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
String realPath = ctx.getRealPath("//");

JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(remittanceReceiptSubreportList);
//String reportPath = FacesContext.getCurrentInstance().getExternalContext().getRealPath("reports/design/RemittanceReceiptNewReport.jasper");
String reportPath = realPath + "\\reports\\design\\RemittanceReceiptNewReport.jasper";
jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);


}

public List<RemittanceReportBean> calculateCollectionMode(BigDecimal documentNumber,BigDecimal currencyId){	
List<RemittanceReportBean> collectionDetailList = new ArrayList<RemittanceReportBean>();
List<CollectionPaymentDetailsView> collectionPaymentDetailList= iPersonalRemittanceService.getCollectionPaymentDetailForRemittanceReceipt(documentNumber);

for(CollectionPaymentDetailsView viewObj: collectionPaymentDetailList){
RemittanceReportBean obj = new RemittanceReportBean();

obj.setCollectionMode(viewObj.getCollectionModeDesc());

BigDecimal collectAmount=GetRound.roundBigDecimal((viewObj.getCollectAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(currencyId));
obj.setCollectAmount(collectAmount);

collectionDetailList.add(obj);
}
return collectionDetailList;
}
private void fetchRemittanceReceiptReportData(BigDecimal documentNumber) throws Exception{

collectionViewList.clear();
remittanceReceiptSubreportList = new ArrayList<RemittanceReceiptSubreport>();



List<RemittanceApplicationView> remittanceApplicationList = new ArrayList<RemittanceApplicationView>();

List<RemittanceApplicationView> fcsaleList = new ArrayList<RemittanceApplicationView>();

List<RemittanceReportBean> remittanceApplList = new ArrayList<RemittanceReportBean>();

List<RemittanceReportBean> fcsaleAppList = new ArrayList<RemittanceReportBean>();

ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
String realPath = ctx.getRealPath("//");
String subReportPath = realPath + Constants.SUB_REPORT_PATH;
String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
String currentDate = dateFormat.format(new Date());
int noOfTransactions = 0;

String currencyQuoteName = generalService.getCurrencyQuote(new BigDecimal(session.getCurrencyId()));

	
List<Employee> employeeList = iPersonalRemittanceService.getEmployeeDetails(session.getUserName());

List<RemittanceApplicationView> remittanceViewlist = iPersonalRemittanceService.getRecordsRemittanceReceipt(documentNumber);

if (remittanceViewlist.size() > 0) {

for (RemittanceApplicationView remittanceAppview : remittanceViewlist) {

if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("REMITTANCE")) {
remittanceApplicationList.add(remittanceAppview);
noOfTransactions= noOfTransactions+1;
} else if (remittanceAppview.getApplicationTypeDesc().equalsIgnoreCase("FOREIGN CURRENCY SALE")) {
fcsaleList.add(remittanceAppview);
noOfTransactions= noOfTransactions+1;
}
}

//remittance List
for (RemittanceApplicationView view : remittanceApplicationList) {

RemittanceReportBean obj = new RemittanceReportBean();

if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getFirstName() + " " + view.getMiddleName()+" "+view.getLastName());
} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null && view.getLastName()!=null) {
obj.setFirstName(view.getFirstName() + " "+ view.getMiddleName()+" "+view.getLastName());
} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
obj.setFirstName(view.getMiddleName()+" "+view.getLastName());
} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null && view.getLastName()!=null) {
obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName()+" "+view.getLastName());
} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
obj.setFirstName(view.getFirstName()+" "+view.getLastName());
} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null && view.getLastName()==null) {
obj.setFirstName(view.getCustomerReference().toString());
} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null && view.getLastName()!=null) {
obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName()+" "+view.getLastName());
}
if(view.getContactNumber()!=null){
obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
}
obj.setCivilId(view.getIdentityInt());
Date sysdate = view.getIdentityExpiryDate();
obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyy").format(sysdate));
obj.setLocation(session.getLocation());

if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
}else if(view.getCollectionDocumentNo()!=null){
obj.setReceiptNo(view.getCollectionDocumentNo().toString());
}


if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
}else if(view.getDocumentNo()!=null){
obj.setTransactionNo(view.getDocumentNo().toString());
}

obj.setDate(currentDate);
obj.setBeneficiaryName(view.getBeneficiaryName());
obj.setBenefeciaryBankName(view.getBeneficiaryBank());
obj.setBenefeciaryBranchName(view.getBenefeciaryBranch());
obj.setBenefeciaryAccountNumber(view.getBenefeciaryAccountNo());
obj.setNoOfTransaction(new BigDecimal(noOfTransactions));
obj.setPhoneNumber(view.getPhoneNumber());
obj.setUserName(session.getUserName());
obj.setPinNo(null);



HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
}else if(!prLtyStr1.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr1);
}else if(!prLtyStr2.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr2);
}


if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
obj.setInsurence1(prInsStr1+"  \n"+prInsStrAr1);
}else if(!prInsStr1.trim().equals("")){
obj.setInsurence1(prInsStr1);
}else if(!prInsStrAr1.trim().equals("")){
obj.setInsurence1(prInsStrAr1);
}


if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
obj.setInsurence2(prInsStr2+"  \n"+prInsStrAr2);
}else if(!prInsStr2.trim().equals("")){
obj.setInsurence2(prInsStr2);
}else if(!prInsStrAr2.trim().equals("")){
obj.setInsurence2(prInsStrAr2);
}





if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
obj.setAddress(view.getBeneCityName() + "," + view.getBeneDistrictName() + "," + view.getBeneStateName());
} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() != null) {
obj.setAddress(view.getBeneDistrictName() + "," + view.getBeneStateName());
} else if (view.getBeneCityName() == null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
obj.setAddress(view.getBeneStateName());
} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() != null) {
obj.setAddress(view.getBeneCityName() + ", " + view.getBeneStateName());
} else if (view.getBeneCityName() != null && view.getBeneDistrictName() == null && view.getBeneStateName() == null) {
obj.setAddress(view.getBeneCityName());
} else if (view.getBeneCityName() == null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
obj.setAddress(view.getBeneDistrictName());
} else if (view.getBeneCityName() != null && view.getBeneDistrictName() != null && view.getBeneStateName() == null) {
obj.setAddress(view.getBeneCityName() + ", " + view.getBeneDistrictName());
}



if (view.getRemittanceDescription() != null && view.getDeliveryDescription() != null) {
obj.setPaymentChannel(view.getRemittanceDescription()+ " - " + view.getDeliveryDescription());
} else if (view.getRemittanceDescription() == null && view.getDeliveryDescription() != null) {
obj.setPaymentChannel(view.getDeliveryDescription());
} else if (view.getRemittanceDescription() != null && view.getDeliveryDescription() == null) {
obj.setPaymentChannel(view.getRemittanceDescription());
}

String currencyAndAmount=null;
BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));
if(view.getCurrencyQuoteName()!=null && foreignTransationAmount!=null){
currencyAndAmount = view.getCurrencyQuoteName()+"     ******"+foreignTransationAmount;
}
obj.setCurrencyAndAmount(currencyAndAmount);
List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
	obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
}
String purposeOfRemittance = "";
for(PurposeOfRemittanceView purposeObj :purposeOfRemittanceList){
	if(purposeOfRemittance.equals("")){
		purposeOfRemittance  = purposeObj.getAmiecDescription();
	}else{
		purposeOfRemittance  = purposeOfRemittance+", "+purposeObj.getAmiecDescription();
	}
}

if(purposeOfRemittance != null && !purposeOfRemittance.equalsIgnoreCase("")){
	obj.setPerposeOfRemittance(purposeOfRemittance);
}

if(view.getCurrencyQuoteName()!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
obj.setExchangeRate(view.getCurrencyQuoteName()+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
}

if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
}

if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
}

if(view.getLocalChargeAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal localChargeAmount=GetRound.roundBigDecimal((view.getLocalChargeAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setOtherCharges(currencyQuoteName+"     ******"+localChargeAmount.toString());
}

if(view.getLocalNetTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal netAmount=GetRound.roundBigDecimal((view.getLocalNetTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setTotalAmount(currencyQuoteName+"     ******"+netAmount.toString());
}

obj.setFutherInstructions(view.getInstructions());
obj.setSourceOfIncome(view.getSourceOfIncomeDesc());
obj.setIntermediataryBank(view.getBenefeciaryInterBank1());


List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(documentNumber);

CollectionDetailView collectionDetailView = collectionDetailList1.get(0);

if(collectionDetailView.getNetAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
}

if(collectionDetailView.getPaidAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
}

if(collectionDetailView.getRefundedAmount()!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
}

obj.setSubReport(subReportPath);
obj.setCollectionDetailList(calculateCollectionMode(documentNumber,view.getLocalTransactionCurrencyId()));

//obj.setSignature(view.getCustomerSignature()); 
// Rabil

// Added by Rabil
try {
if (view.getCustomerSignatureClob() != null) {
obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
}
} catch (SQLException e) {
e.printStackTrace();
}

if(employeeList!=null && employeeList.size()>0){
try {
Employee employee = employeeList.get(0);
if (employee.getSignatureSpecimenClob() != null) {
obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
}
} catch (SQLException e) {
e.printStackTrace();
}
}


remittanceApplList.add(obj);
}

// for foreign currency Sale report
for (RemittanceApplicationView view : fcsaleList) {

RemittanceReportBean obj = new RemittanceReportBean();

if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() != null) {
obj.setFirstName(view.getCustomerReference().toString() + "/" + view.getFirstName() + " " + view.getMiddleName());
} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() != null) {
obj.setFirstName(view.getFirstName() + " " + view.getMiddleName());
} else if (view.getCustomerReference() == null && view.getFirstName() == null && view.getMiddleName() != null) {
obj.setFirstName(view.getMiddleName());
} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() != null) {
obj.setFirstName(view.getCustomerReference().toString() + " / " + view.getMiddleName());
} else if (view.getCustomerReference() == null && view.getFirstName() != null && view.getMiddleName() == null) {
obj.setFirstName(view.getFirstName());
} else if (view.getCustomerReference() != null && view.getFirstName() == null && view.getMiddleName() == null) {
obj.setFirstName(view.getCustomerReference().toString());
} else if (view.getCustomerReference() != null && view.getFirstName() != null && view.getMiddleName() == null) {
obj.setFirstName(view.getCustomerReference().toString() + " " + view.getFirstName());
}

if(view.getContactNumber()!=null){
obj.setMobileNo(new BigDecimal(view.getContactNumber().trim()));
}
obj.setCivilId(view.getIdentityInt());
Date sysdate = view.getIdentityExpiryDate();
obj.setIdExpiryDate(new SimpleDateFormat("dd/MM/yyyy").format(sysdate));


HashMap<String, String> loyaltiPoints  =iPersonalRemittanceService.getloyalityPointsFromProcedure(view.getCustomerReference(), view.getDocumentDate());

String prLtyStr1 =loyaltiPoints.get("P_LTY_STR1");
String prLtyStr2 =loyaltiPoints.get("P_LTY_STR2");
String prInsStr1 =loyaltiPoints.get("P_INS_STR1");
String prInsStr2 =loyaltiPoints.get("P_INS_STR2");
String prInsStrAr1 =loyaltiPoints.get("P_INS_STR_AR1");
String prInsStrAr2 =loyaltiPoints.get("P_INS_STR_AR2");

if(!prLtyStr1.trim().equals("") && !prLtyStr2.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr1+"  \n"+prLtyStr2);
}else if(!prLtyStr1.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr1);
}else if(!prLtyStr2.trim().equals("")){
obj.setLoyalityPointExpiring(prLtyStr2);
}

String insurence1 ="";

if(!prInsStr1.trim().equals("") && !prInsStrAr1.trim().equals("")){
insurence1 = prInsStr1+"  \n"+prInsStrAr1;
}else if(!prInsStr1.trim().equals("")){
insurence1 = prInsStr1;
}else if(!prInsStrAr1.trim().equals("")){
insurence1 = prInsStrAr1;
}

String insurence2 ="";

if(!prInsStr2.trim().equals("") && !prInsStrAr2.trim().equals("")){
insurence2 = prInsStr2+"  \n"+prInsStrAr2;
}else if(!prInsStr2.trim().equals("")){
insurence2 = prInsStr2;
}else if(!prInsStrAr2.trim().equals("")){
insurence2 = prInsStrAr2;
}

if(!insurence1.trim().equals("") && !insurence2.trim().equals("")){
obj.setInsurence1(insurence1+" \n"+insurence2);
}else if(!insurence1.trim().equals("")){
obj.setInsurence1(insurence1);
}else if(!insurence2.trim().equals("")){
obj.setInsurence1(insurence2);
}


obj.setLocation(view.getCountryBranchName());
obj.setPhoneNumber(view.getPhoneNumber());
obj.setDate(currentDate);
obj.setUserName(session.getUserName());

if(view.getDocumentFinancialYear()!=null && view.getCollectionDocumentNo()!=null){
obj.setReceiptNo(view.getDocumentFinancialYear()+" / "+view.getCollectionDocumentNo());
}else if(view.getCollectionDocumentNo()!=null){
obj.setReceiptNo(view.getCollectionDocumentNo().toString());
}
String saleCurrency = specialCustomerDealRequestService.getCurrencyForUpdate(view.getForeignCurrencyId());


obj.setCurrencyQuoteName(saleCurrency);

String saleCurrencyCode = generalService.getCurrencyQuote(view.getForeignCurrencyId());

if(view.getDocumentFinancialYear()!=null && view.getDocumentNo()!=null){
obj.setTransactionNo(view.getDocumentFinancialYear()+" / "+view.getDocumentNo());
}else if(view.getDocumentNo()!=null){
obj.setTransactionNo(view.getDocumentNo().toString());
}

if (view.getForeignTransactionAmount() != null && saleCurrencyCode != null) {
BigDecimal foreignTransationAmount=GetRound.roundBigDecimal((view.getForeignTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getForeignCurrencyId()));	
obj.setSaleAmount( saleCurrencyCode+"     ******"+foreignTransationAmount.toString());
} 



if( view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setPurchageAmount(currencyQuoteName+"     ******"+transationAmount.toString());
}

if(saleCurrencyCode!=null && currencyQuoteName!=null && view.getExchangeRateApplied()!=null){
obj.setExchangeRate(saleCurrencyCode+" / "+currencyQuoteName+"     "+view.getExchangeRateApplied().toString());
}

if(view.getLocalTransactionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
BigDecimal transationAmount=GetRound.roundBigDecimal((view.getLocalTransactionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setLocalTransactionAmount(currencyQuoteName+"     ******"+transationAmount.toString());
}

if(view.getLocalCommissionAmount()!=null && view.getLocalTransactionCurrencyId()!=null && currencyQuoteName!=null){
BigDecimal localCommitionAmount=GetRound.roundBigDecimal((view.getLocalCommissionAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setCommision(currencyQuoteName+"     ******"+localCommitionAmount.toString());
}

List<PurposeOfRemittanceView>  purposeOfRemittanceList =   iPersonalRemittanceService.getPurposeOfRemittance(view.getDocumentNo(),view.getDocumentFinancialYear());

if(purposeOfRemittanceList!=null && purposeOfRemittanceList.size()>0){
	obj.setPerposeOfRemittance(purposeOfRemittanceList.get(0).getAmiecDescription());
}
obj.setSourceOfIncome(view.getSourceOfIncomeDesc());



List<CollectionDetailView> collectionDetailList1= iPersonalRemittanceService.getCollectionDetailForRemittanceReceipt(documentNumber);
if(collectionDetailList1.size()>0){
CollectionDetailView collectionDetailView = collectionDetailList1.get(0);
if(collectionDetailView.getNetAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectNetAmount=GetRound.roundBigDecimal((collectionDetailView.getNetAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setNetAmount(currencyQuoteName+"     ******"+collectNetAmount);
}
if(collectionDetailView.getPaidAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectPaidAmount=GetRound.roundBigDecimal((collectionDetailView.getPaidAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setPaidAmount(currencyQuoteName+"     ******"+collectPaidAmount);
}
if(collectionDetailView.getRefundedAmount()!=null && currencyQuoteName!=null && view.getLocalTransactionCurrencyId()!=null){
BigDecimal collectRefundAmount=GetRound.roundBigDecimal((collectionDetailView.getRefundedAmount()),foreignLocalCurrencyDenominationService.getDecimalPerCurrency(view.getLocalTransactionCurrencyId()));
obj.setRefundedAmount(currencyQuoteName+"     ******"+collectRefundAmount);
}
obj.setCollectionDetailList(calculateCollectionMode(documentNumber,view.getLocalTransactionCurrencyId()));
}

obj.setSubReport(subReportPath);
obj.setUserName(session.getUserName());

//	obj.setSignature(view.getCustomerSignature());

try {
if (view.getCustomerSignatureClob() != null) {
obj.setSignature(view.getCustomerSignatureClob().getSubString(1,(int) view.getCustomerSignatureClob().length()));
}
} catch (SQLException e) {
e.printStackTrace();
}

if(employeeList!=null && employeeList.size()>0){
try {
Employee employee = employeeList.get(0);
if (employee.getSignatureSpecimenClob() != null) {
obj.setCashierSignature(employee.getSignatureSpecimenClob().getSubString(1,(int) employee.getSignatureSpecimenClob().length()));
}
} catch (SQLException e) {
e.printStackTrace();
}
}


fcsaleAppList.add(obj);


}

//for Main Remittance Receipt report (Remittance Receipt and Fc Sale Application)
RemittanceReceiptSubreport remittanceObj = new RemittanceReceiptSubreport();

remittanceObj.setWaterMarkLogoPath(waterMark);
remittanceObj.setWaterMarkCheck(true);
remittanceObj.setFcsaleAppList(fcsaleAppList);
remittanceObj.setRemittanceApplList(remittanceApplList);
remittanceObj.setSubReport(subReportPath);

if (fcsaleAppList!=null && fcsaleAppList.size()>0) {
remittanceObj.setFcsaleApplicationCheck(true);
} else {
remittanceObj.setFcsaleApplicationCheck(false);
}
if(remittanceApplList!=null && remittanceApplList.size()>0){
remittanceObj.setRemittanceReceiptCheck(true);
}else{
remittanceObj.setRemittanceReceiptCheck(false);
}

remittanceReceiptSubreportList.add(remittanceObj);

} else {
RequestContext.getCurrentInstance().execute("noDataForReport.show();");
return;
}


}



public void populateSearchValue() {
	HttpSession session = sessionStateManage.getSession();
	
	CountrywiseFundRequirementHighvalueEnquiryDataTable remittanceEnquiryBean =(CountrywiseFundRequirementHighvalueEnquiryDataTable) session.getAttribute("remittanceEnquiryObject");
	
	if(remittanceEnquiryBean != null){
		clearAllFields();
		setDocNumber(remittanceEnquiryBean.getRemittanceNo());
		setDocYear(remittanceEnquiryBean.getRemittanceYear());
		fetchData();
		session.removeAttribute("remittanceEnquiryObject");
	}
		
}
	
	
}
*/