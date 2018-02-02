package com.amg.exchange.loyalty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.StateMaster;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.RemittanceModeMaster;

@Entity
@Table(name = "EX_LTY_PARAMETER_SETTINGS")
public class LoyaltyParameterSetting implements Serializable {

	  private static final long serialVersionUID = 1L;

	  private BigDecimal loyaltyParameterId;
	  private String templateCode;
	  private LoyaltyCatagoryMaster loyaltyCatagoryMaster;
	  private LoyaltyType loyaltyType;
	  private CountryMaster fsCountryMaster;
	  private StateMaster fsStateMaster;
	  private BankMaster exBankMaster;
	  private CurrencyMaster exCurrencyMaster;
	  private BigDecimal productId;
	  private String cummulativeOperator;
	  private BigDecimal cummulativeFigure;
	  private String fromTime;
	  private String toTime;
	  private String dateOfTheWeek;
	  private BigDecimal points;
	  private String templateCriteria;
	  private BigDecimal fromAmount;
	  private BigDecimal toAmount;
	  private BigDecimal remittanceModeMaster;
	  private BigDecimal deliveryMode;
	  private BigDecimal noOfDays;
	  private String referenceIntrodution;
	  private CountryMaster applicationCountry;
	  private String isActive;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;

	  public LoyaltyParameterSetting() {
		    super();
	  }

	  public LoyaltyParameterSetting(BigDecimal loyaltyParameterId, String templateCode, LoyaltyCatagoryMaster loyaltyCatagoryMaster, LoyaltyType loyaltyType, CountryMaster fsCountryMaster, StateMaster fsStateMaster, BankMaster exBankMaster, CurrencyMaster exCurrencyMaster,
			      BigDecimal productId, String cummulativeOperator, BigDecimal cummulativeFigure, String fromTime, String toTime, String dateOfTheWeek, BigDecimal points, String templateCriteria, BigDecimal fromAmount, BigDecimal toAmount, BigDecimal remittanceModeMaster,
			      BigDecimal deliveryMode, BigDecimal noOfDays, String referenceIntrodution, CountryMaster applicationCountry, String isActive, String createdBy, Date createdDate, String modifiedBy, Date modifiedDate, String approvedBy, Date approvedDate, String remarks) {
		    super();
		    this.loyaltyParameterId = loyaltyParameterId;
		    this.templateCode = templateCode;
		    this.loyaltyCatagoryMaster = loyaltyCatagoryMaster;
		    this.loyaltyType = loyaltyType;
		    this.fsCountryMaster = fsCountryMaster;
		    this.fsStateMaster = fsStateMaster;
		    this.exBankMaster = exBankMaster;
		    this.exCurrencyMaster = exCurrencyMaster;
		    this.productId = productId;
		    this.cummulativeOperator = cummulativeOperator;
		    this.cummulativeFigure = cummulativeFigure;
		    this.fromTime = fromTime;
		    this.toTime = toTime;
		    this.dateOfTheWeek = dateOfTheWeek;
		    this.points = points;
		    this.templateCriteria = templateCriteria;
		    this.fromAmount = fromAmount;
		    this.toAmount = toAmount;
		    this.remittanceModeMaster = remittanceModeMaster;
		    this.deliveryMode = deliveryMode;
		    this.noOfDays = noOfDays;
		    this.referenceIntrodution = referenceIntrodution;
		    this.applicationCountry = applicationCountry;
		    this.isActive = isActive;
		    this.createdBy = createdBy;
		    this.createdDate = createdDate;
		    this.modifiedBy = modifiedBy;
		    this.modifiedDate = modifiedDate;
		    this.approvedBy = approvedBy;
		    this.approvedDate = approvedDate;
		    this.remarks = remarks;
	  }

	  @Id
	  @GeneratedValue(generator = "ex_lty_parameter_settings_seq", strategy = GenerationType.SEQUENCE)
	  @SequenceGenerator(name = "ex_lty_parameter_settings_seq", sequenceName = "EX_LTY_PARAMETER_SETTINGS_SEQ", allocationSize = 1)
	  @Column(name = "LTY_PARAMETER_ID", nullable = false)
	  public BigDecimal getLoyaltyParameterId() {
		    return loyaltyParameterId;
	  }

	  public void setLoyaltyParameterId(BigDecimal loyaltyParameterId) {
		    this.loyaltyParameterId = loyaltyParameterId;
	  }

	  @Column(name = "TEMPLATE_CODE")
	  public String getTemplateCode() {
		    return templateCode;
	  }

	  public void setTemplateCode(String templateCode) {
		    this.templateCode = templateCode;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "LTY_CATEGORY_ID")
	  public LoyaltyCatagoryMaster getLoyaltyCatagoryMaster() {
		    return loyaltyCatagoryMaster;
	  }

	  public void setLoyaltyCatagoryMaster(LoyaltyCatagoryMaster loyaltyCatagoryMaster) {
		    this.loyaltyCatagoryMaster = loyaltyCatagoryMaster;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "LTY_TYPE_ID")
	  public LoyaltyType getLoyaltyType() {
		    return loyaltyType;
	  }

	  public void setLoyaltyType(LoyaltyType loyaltyType) {
		    this.loyaltyType = loyaltyType;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "COUNTRY_ID")
	  public CountryMaster getFsCountryMaster() {
		    return fsCountryMaster;
	  }

	  public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		    this.fsCountryMaster = fsCountryMaster;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "STATE_ID")
	  public StateMaster getFsStateMaster() {
		    return fsStateMaster;
	  }

	  public void setFsStateMaster(StateMaster fsStateMaster) {
		    this.fsStateMaster = fsStateMaster;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "BANK_ID")
	  public BankMaster getExBankMaster() {
		    return exBankMaster;
	  }

	  public void setExBankMaster(BankMaster exBankMaster) {
		    this.exBankMaster = exBankMaster;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "CURRENCY_ID")
	  public CurrencyMaster getExCurrencyMaster() {
		    return exCurrencyMaster;
	  }

	  public void setExCurrencyMaster(CurrencyMaster exCurrencyMaster) {
		    this.exCurrencyMaster = exCurrencyMaster;
	  }

	  @Column(name = "PRODUCT_ID")
	  public BigDecimal getProductId() {
		    return productId;
	  }

	  public void setProductId(BigDecimal productId) {
		    this.productId = productId;
	  }

	  @Column(name = "CUMMULATIVE_OPERATOR")
	  public String getCummulativeOperator() {
		    return cummulativeOperator;
	  }

	  public void setCummulativeOperator(String cummulativeOperator) {
		    this.cummulativeOperator = cummulativeOperator;
	  }

	  @Column(name = "CUMMULATIVE_FIGURE")
	  public BigDecimal getCummulativeFigure() {
		    return cummulativeFigure;
	  }

	  public void setCummulativeFigure(BigDecimal cummulativeFigure) {
		    this.cummulativeFigure = cummulativeFigure;
	  }

	  @Column(name = "PT_TIME_FROM")
	  public String getFromTime() {
		    return fromTime;
	  }

	  public void setFromTime(String fromTime) {
		    this.fromTime = fromTime;
	  }

	  @Column(name = "PT_TIME_TO")
	  public String getToTime() {
		    return toTime;
	  }

	  public void setToTime(String toTime) {
		    this.toTime = toTime;
	  }

	  @Column(name = "DATE_OF_WEEK")
	  public String getDateOfTheWeek() {
		    return dateOfTheWeek;
	  }

	  public void setDateOfTheWeek(String dateOfTheWeek) {
		    this.dateOfTheWeek = dateOfTheWeek;
	  }

	  @Column(name = "POINTS")
	  public BigDecimal getPoints() {
		    return points;
	  }

	  public void setPoints(BigDecimal points) {
		    this.points = points;
	  }

	  @Column(name = "TEMPLATE_CRITERIA")
	  public String getTemplateCriteria() {
		    return templateCriteria;
	  }

	  public void setTemplateCriteria(String templateCriteria) {
		    this.templateCriteria = templateCriteria;
	  }

	  @Column(name = "FROM_AMOUNT")
	  public BigDecimal getFromAmount() {
		    return fromAmount;
	  }

	  public void setFromAmount(BigDecimal fromAmount) {
		    this.fromAmount = fromAmount;
	  }

	  @Column(name = "TO_AMOUNT")
	  public BigDecimal getToAmount() {
		    return toAmount;
	  }

	  public void setToAmount(BigDecimal toAmount) {
		    this.toAmount = toAmount;
	  }

	  /*@ManyToOne(fetch = FetchType.LAZY)*/
	  @Column(name = "REMITANCE_MODE")
	  public BigDecimal getRemittanceModeMaster() {
		    return remittanceModeMaster;
	  }

	  public void setRemittanceModeMaster(BigDecimal remittanceModeMaster) {
		    this.remittanceModeMaster = remittanceModeMaster;
	  }

	 /* @ManyToOne(fetch = FetchType.LAZY)*/
	  @Column(name = "DELIVERY_MODE")
	  public BigDecimal getDeliveryMode() {
		    return deliveryMode;
	  }

	  public void setDeliveryMode(BigDecimal deliveryMode) {
		    this.deliveryMode = deliveryMode;
	  }

	  @Column(name = "NO_OF_DAYS")
	  public BigDecimal getNoOfDays() {
		    return noOfDays;
	  }

	  public void setNoOfDays(BigDecimal noOfDays) {
		    this.noOfDays = noOfDays;
	  }

	  @Column(name = "REFERENCE_INTRODUCTION")
	  public String getReferenceIntrodution() {
		    return referenceIntrodution;
	  }

	  public void setReferenceIntrodution(String referenceIntrodution) {
		    this.referenceIntrodution = referenceIntrodution;
	  }

	  @ManyToOne(fetch = FetchType.LAZY)
	  @JoinColumn(name = "APPLICATION_COUNTRY_ID")
	  public CountryMaster getApplicationCountry() {
		    return applicationCountry;
	  }

	  public void setApplicationCountry(CountryMaster applicationCountry) {
		    this.applicationCountry = applicationCountry;
	  }

	  @Column(name = "ISACTIVE")
	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  @Column(name = "CREATED_BY")
	  public String getCreatedBy() {
		    return createdBy;
	  }

	  public void setCreatedBy(String createdBy) {
		    this.createdBy = createdBy;
	  }

	  @Column(name = "CREATED_DATE")
	  public Date getCreatedDate() {
		    return createdDate;
	  }

	  public void setCreatedDate(Date createdDate) {
		    this.createdDate = createdDate;
	  }

	  @Column(name = "MODIFIED_BY")
	  public String getModifiedBy() {
		    return modifiedBy;
	  }

	  public void setModifiedBy(String modifiedBy) {
		    this.modifiedBy = modifiedBy;
	  }

	  @Column(name = "MODIFIED_DATE")
	  public Date getModifiedDate() {
		    return modifiedDate;
	  }

	  public void setModifiedDate(Date modifiedDate) {
		    this.modifiedDate = modifiedDate;
	  }

	  @Column(name = "APPROVED_BY")
	  public String getApprovedBy() {
		    return approvedBy;
	  }

	  public void setApprovedBy(String approvedBy) {
		    this.approvedBy = approvedBy;
	  }

	  @Column(name = "APPROVED_DATE")
	  public Date getApprovedDate() {
		    return approvedDate;
	  }

	  public void setApprovedDate(Date approvedDate) {
		    this.approvedDate = approvedDate;
	  }

	  @Column(name = "REMARKS")
	  public String getRemarks() {
		    return remarks;
	  }

	  public void setRemarks(String remarks) {
		    this.remarks = remarks;
	  }

}
