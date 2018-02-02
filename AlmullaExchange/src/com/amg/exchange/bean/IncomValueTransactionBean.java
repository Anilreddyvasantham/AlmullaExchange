package com.amg.exchange.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.registration.model.IncomeRangeValue;
import com.amg.exchange.registration.service.IIncomeRangeValueService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("incomValueTransactionBean")
@Scope("session")
public class IncomValueTransactionBean<T> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(IncomeRangeBean.class);
	private static final long serialVersionUID = 1L;

	private BigDecimal incomeValueTransactionId;
	private BigDecimal appCountryId;
	private BigDecimal valuePerTranx;
	private BigDecimal valuePerDay;
	private BigDecimal valuePerWeek;
	private BigDecimal valuePerMonth;
	private BigDecimal valuePerAnnum;
	private BigDecimal noofTranxPerDay;
	private BigDecimal noofTranxPerWeek;
	private BigDecimal noofTranxPerMonth;
	private BigDecimal noofTranxPerAnnum;
	private BigDecimal incomeRangeFrom;
	private BigDecimal incomeRangeTo;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String remarks = "";

	private Boolean booEdit = false;
	private Boolean boodisable = false;
	private Boolean booBtnClear = false;

	private String dynamicLabelForActivateDeactivate;
	private Boolean booIncomeRangeValueTranx = false;
	private Boolean booIncomeRangeValueTranxDataTable = false;

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IIncomeRangeValueService incomeRangeValueService;

	private List<IncomValueTransactionDataTable> lstIncomeValueTranxDataTable = new ArrayList<IncomValueTransactionDataTable>();

	private List<IncomeRangeValue> lstOfIncomeRangeValue = new ArrayList<IncomeRangeValue>();

	public IncomValueTransactionBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getIncomeValueTransactionId() {
		return incomeValueTransactionId;
	}

	public void setIncomeValueTransactionId(BigDecimal incomeValueTransactionId) {
		this.incomeValueTransactionId = incomeValueTransactionId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public BigDecimal getValuePerTranx() {
		return valuePerTranx;
	}

	public void setValuePerTranx(BigDecimal valuePerTranx) {
		this.valuePerTranx = valuePerTranx;
	}

	public BigDecimal getValuePerDay() {
		return valuePerDay;
	}

	public void setValuePerDay(BigDecimal valuePerDay) {
		this.valuePerDay = valuePerDay;
	}

	public BigDecimal getValuePerWeek() {
		return valuePerWeek;
	}

	public void setValuePerWeek(BigDecimal valuePerWeek) {
		this.valuePerWeek = valuePerWeek;
	}

	public BigDecimal getValuePerMonth() {
		return valuePerMonth;
	}

	public void setValuePerMonth(BigDecimal valuePerMonth) {
		this.valuePerMonth = valuePerMonth;
	}

	public BigDecimal getValuePerAnnum() {
		return valuePerAnnum;
	}

	public void setValuePerAnnum(BigDecimal valuePerAnnum) {
		this.valuePerAnnum = valuePerAnnum;
	}

	public BigDecimal getNoofTranxPerDay() {
		return noofTranxPerDay;
	}

	public void setNoofTranxPerDay(BigDecimal noofTranxPerDay) {
		this.noofTranxPerDay = noofTranxPerDay;
	}

	public BigDecimal getNoofTranxPerWeek() {
		return noofTranxPerWeek;
	}

	public void setNoofTranxPerWeek(BigDecimal noofTranxPerWeek) {
		this.noofTranxPerWeek = noofTranxPerWeek;
	}

	public BigDecimal getNoofTranxPerMonth() {
		return noofTranxPerMonth;
	}

	public void setNoofTranxPerMonth(BigDecimal noofTranxPerMonth) {
		this.noofTranxPerMonth = noofTranxPerMonth;
	}

	public BigDecimal getNoofTranxPerAnnum() {
		return noofTranxPerAnnum;
	}

	public void setNoofTranxPerAnnum(BigDecimal noofTranxPerAnnum) {
		this.noofTranxPerAnnum = noofTranxPerAnnum;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public BigDecimal getIncomeRangeFrom() {
		return incomeRangeFrom;
	}

	public void setIncomeRangeFrom(BigDecimal incomeRangeFrom) {
		this.incomeRangeFrom = incomeRangeFrom;
	}

	public BigDecimal getIncomeRangeTo() {
		return incomeRangeTo;
	}

	public void setIncomeRangeTo(BigDecimal incomeRangeTo) {
		this.incomeRangeTo = incomeRangeTo;
	}

	public Boolean getBooIncomeRangeValueTranx() {
		return booIncomeRangeValueTranx;
	}

	public void setBooIncomeRangeValueTranx(Boolean booIncomeRangeValueTranx) {
		this.booIncomeRangeValueTranx = booIncomeRangeValueTranx;
	}

	public Boolean getBooIncomeRangeValueTranxDataTable() {
		return booIncomeRangeValueTranxDataTable;
	}

	public void setBooIncomeRangeValueTranxDataTable(Boolean booIncomeRangeValueTranxDataTable) {
		this.booIncomeRangeValueTranxDataTable = booIncomeRangeValueTranxDataTable;
	}

	public List<IncomValueTransactionDataTable> getLstIncomeValueTranxDataTable() {
		return lstIncomeValueTranxDataTable;
	}

	public void setLstIncomeValueTranxDataTable(List<IncomValueTransactionDataTable> lstIncomeValueTranxDataTable) {
		this.lstIncomeValueTranxDataTable = lstIncomeValueTranxDataTable;
	}

	public IIncomeRangeValueService getIncomeRangeValueService() {
		return incomeRangeValueService;
	}

	public void setIncomeRangeValueService(IIncomeRangeValueService incomeRangeValueService) {
		this.incomeRangeValueService = incomeRangeValueService;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public Boolean getBoodisable() {
		return boodisable;
	}

	public void setBoodisable(Boolean boodisable) {
		this.boodisable = boodisable;
	}

	public Boolean getBooBtnClear() {
		return booBtnClear;
	}

	public void setBooBtnClear(Boolean booBtnClear) {
		this.booBtnClear = booBtnClear;
	}

	public List<IncomeRangeValue> getLstOfIncomeRangeValue() {
		return lstOfIncomeRangeValue;
	}

	public void setLstOfIncomeRangeValue(List<IncomeRangeValue> lstOfIncomeRangeValue) {
		this.lstOfIncomeRangeValue = lstOfIncomeRangeValue;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "incomevaluetransaction.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomevaluetransaction.xhtml");
			lstIncomeValueTranxDataTable.clear();
			setBooIncomeRangeValueTranx(true);
			setBooIncomeRangeValueTranxDataTable(false);
			setBoodisable(false);
			setBooEdit(false);
			setBooBtnClear(false);

			clearAllFields();

		} catch (Exception e) {
			LOGGER.info("Problem to redirect:" + e);
		}

	}

	public void pageNavigationApproval() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "incomevaluetransactionapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomevaluetransactionapproval.xhtml");
			lstIncomeValueTranxDataTable.clear();
			approveView();
			setBooIncomeRangeValueTranx(false);
			setBooIncomeRangeValueTranxDataTable(true);
			setBoodisable(false);
			setBooEdit(false);
			setBooBtnClear(false);
			clearAllFields();

		} catch (Exception e) {
			LOGGER.info("Problem to redirect:" + e);
		}

	}

	public void clearAllFields() {
		setIncomeRangeFrom(null);
		setIncomeRangeTo(null);
		setValuePerTranx(null);
		setValuePerDay(null);
		setValuePerWeek(null);
		setValuePerMonth(null);
		setValuePerAnnum(null);
		setNoofTranxPerDay(null);
		setNoofTranxPerAnnum(null);
		setNoofTranxPerWeek(null);
		setNoofTranxPerMonth(null);
		setNoofTranxPerAnnum(null);
		setIncomeValueTransactionId(null);
		setBoodisable(false);
		setBooEdit(false);
		setBooBtnClear(false);

	}

	private String setreverActiveStatus(String status ) {

		LOGGER.info("Enter in active status method ");

		String strStatus = null;

		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.PENDING_FOR_APPROVE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.REMOVE;
		}
		LOGGER.info("Exit from active status method ");
		return strStatus;

	}

	public void addToDataTable() {

		IncomValueTransactionDataTable incomValueTransactionDataTable = new IncomValueTransactionDataTable();

		incomValueTransactionDataTable.setAppCountryId(sessionStateManage.getCountryId());
		incomValueTransactionDataTable.setIncomeValueTransactionId(getIncomeValueTransactionId());
		incomValueTransactionDataTable.setIncomeRangeFrom(getIncomeRangeFrom());
		incomValueTransactionDataTable.setIncomeRangeTo(getIncomeRangeTo());
		incomValueTransactionDataTable.setValuePerTranx(getValuePerTranx());
		incomValueTransactionDataTable.setValuePerDay(getValuePerDay());
		incomValueTransactionDataTable.setValuePerWeek(getValuePerWeek());
		incomValueTransactionDataTable.setValuePerMonth(getValuePerMonth());
		incomValueTransactionDataTable.setValuePerAnnum(getValuePerAnnum());
		incomValueTransactionDataTable.setNoofTranxPerDay(getNoofTranxPerDay());
		incomValueTransactionDataTable.setNoofTranxPerWeek(getNoofTranxPerWeek());
		incomValueTransactionDataTable.setNoofTranxPerMonth(getNoofTranxPerMonth());
		incomValueTransactionDataTable.setNoofTranxPerAnnum(getNoofTranxPerAnnum());
		if (getIncomeValueTransactionId() != null) {

			List<IncomeRangeValue> dataList = getIncomeRangeValueService().getIncomeFRangeValueListforDetail(sessionStateManage.getCountryId());

			double fromAmount = incomValueTransactionDataTable.getIncomeRangeFrom().doubleValue();

			double toAmount = incomValueTransactionDataTable.getIncomeRangeTo().doubleValue();

			for (IncomeRangeValue incomeRangeValue : dataList) {

				if (incomeRangeValue.getIncomeValueTransactionId().doubleValue() != getIncomeValueTransactionId().doubleValue()) {
					double lowerBound = incomeRangeValue.getIncomeRangeFrom().doubleValue();

					double upperBound = incomeRangeValue.getIncomeRangeTo().doubleValue();

					if (lowerBound <= fromAmount && fromAmount <= upperBound) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("isExist.show();");
						return;
					}

					if (lowerBound <= toAmount && toAmount <= upperBound) {
						RequestContext context = RequestContext.getCurrentInstance();
						context.execute("isExist.show();");
						return;
					}
	
				}
							}

			incomValueTransactionDataTable.setModifiedBy(sessionStateManage.getUserName());
			incomValueTransactionDataTable.setModifiedDate(new Date());
			incomValueTransactionDataTable.setCreatedBy(getCreatedBy());
			incomValueTransactionDataTable.setCreatedDate(getCreatedDate());
			incomValueTransactionDataTable.setApprovedBy(null);
			incomValueTransactionDataTable.setApprovedDate(null);
			incomValueTransactionDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(getIsActive()));
			incomValueTransactionDataTable.setIsActive(Constants.U);
		} else {

			List<IncomeRangeValue> dataList = getIncomeRangeValueService().getIncomeFRangeValueListforDetail(sessionStateManage.getCountryId());

			double fromAmount = incomValueTransactionDataTable.getIncomeRangeFrom().doubleValue();

			double toAmount = incomValueTransactionDataTable.getIncomeRangeTo().doubleValue();

			for (IncomeRangeValue incomeRangeValue : dataList) {

				double lowerBound = incomeRangeValue.getIncomeRangeFrom().doubleValue();

				double upperBound = incomeRangeValue.getIncomeRangeTo().doubleValue();

				if (lowerBound <= fromAmount && fromAmount <= upperBound) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}

				if (lowerBound <= toAmount && toAmount <= upperBound) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}
			}

			incomValueTransactionDataTable.setDynamicLabelForActivateDeactivate("Remove");
			incomValueTransactionDataTable.setCreatedBy(sessionStateManage.getUserName());
			incomValueTransactionDataTable.setCreatedDate(new Date());
			incomValueTransactionDataTable.setIsActive(Constants.U);
		}
		
		if(!lstIncomeValueTranxDataTable.isEmpty()){
			
			for(IncomValueTransactionDataTable incomValueDataTable :lstIncomeValueTranxDataTable){
				
				double infromAmount = incomValueDataTable.getIncomeRangeFrom().doubleValue();
				double intoAmount = incomValueDataTable.getIncomeRangeTo().doubleValue();
				if (infromAmount <= getIncomeRangeFrom().doubleValue() && getIncomeRangeFrom().doubleValue() <= intoAmount) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}
				if (infromAmount <= getIncomeRangeTo().doubleValue() && getIncomeRangeTo().doubleValue() <= intoAmount) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}
				
				/*double lowerBound = incomValueDataTable.getIncomeRangeFrom().doubleValue();

				double upperBound = incomValueDataTable.getIncomeRangeTo().doubleValue();

				if (lowerBound <= getIncomeRangeFrom().doubleValue() && getIncomeRangeFrom().doubleValue() <= upperBound) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}

				if (lowerBound <= getIncomeRangeTo().doubleValue() && getIncomeRangeTo().doubleValue() <= upperBound) {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("isExist.show();");
					return;
				}*/
				
			}
			
		}

		lstIncomeValueTranxDataTable.add(incomValueTransactionDataTable);
		setBooIncomeRangeValueTranx(true);
		setBooIncomeRangeValueTranxDataTable(true);
		setBoodisable(false);
		setBooEdit(false);
		setBooBtnClear(false);
		clearAllFields();

	}

	public void edit(IncomValueTransactionDataTable incomValueTransactionDataTable) {

		setIncomeValueTransactionId(incomValueTransactionDataTable.getIncomeValueTransactionId());
		setIncomeRangeFrom(incomValueTransactionDataTable.getIncomeRangeFrom());
		setIncomeRangeTo(incomValueTransactionDataTable.getIncomeRangeTo());
		setValuePerTranx(incomValueTransactionDataTable.getValuePerTranx());
		setValuePerDay(incomValueTransactionDataTable.getValuePerDay());
		setValuePerWeek(incomValueTransactionDataTable.getValuePerWeek());
		setValuePerMonth(incomValueTransactionDataTable.getValuePerMonth());
		setValuePerAnnum(incomValueTransactionDataTable.getValuePerAnnum());
		setNoofTranxPerDay(incomValueTransactionDataTable.getNoofTranxPerDay());
		setNoofTranxPerWeek(incomValueTransactionDataTable.getNoofTranxPerWeek());
		setNoofTranxPerMonth(incomValueTransactionDataTable.getNoofTranxPerMonth());
		setNoofTranxPerAnnum(incomValueTransactionDataTable.getNoofTranxPerAnnum());
		setCreatedBy(incomValueTransactionDataTable.getCreatedBy());
		setCreatedDate(incomValueTransactionDataTable.getCreatedDate());
		setModifiedBy(incomValueTransactionDataTable.getModifiedBy());
		setModifiedDate(incomValueTransactionDataTable.getModifiedDate());
		setApprovedBy(incomValueTransactionDataTable.getApprovedBy());
		setApprovedDate(incomValueTransactionDataTable.getApprovedDate());
		setIsActive(incomValueTransactionDataTable.getIsActive());

		lstIncomeValueTranxDataTable.remove(incomValueTransactionDataTable);
		setBoodisable(true);
		setBooEdit(true);
		setBooBtnClear(true);

	}

	public void save() {

		if (lstIncomeValueTranxDataTable.isEmpty()) {

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");

		} else {
			for (IncomValueTransactionDataTable incomValueTransactionDataTable : lstIncomeValueTranxDataTable) {

				IncomeRangeValue incomeRangeValue = new IncomeRangeValue();

				incomeRangeValue.setIncomeValueTransactionId(incomValueTransactionDataTable.getIncomeValueTransactionId());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(incomValueTransactionDataTable.getAppCountryId());
				incomeRangeValue.setAppCountryId(countryMaster);

				incomeRangeValue.setValuePerTranx(incomValueTransactionDataTable.getValuePerTranx());
				incomeRangeValue.setValuePerDay(incomValueTransactionDataTable.getValuePerDay());
				incomeRangeValue.setValuePerWeek(incomValueTransactionDataTable.getValuePerWeek());
				incomeRangeValue.setValuePerMonth(incomValueTransactionDataTable.getValuePerMonth());
				incomeRangeValue.setValuePerAnnum(incomValueTransactionDataTable.getValuePerAnnum());
				incomeRangeValue.setNoofTranxPerDay(incomValueTransactionDataTable.getNoofTranxPerDay());
				incomeRangeValue.setNoofTranxPerWeek(incomValueTransactionDataTable.getNoofTranxPerWeek());
				incomeRangeValue.setNoofTranxPerMonth(incomValueTransactionDataTable.getNoofTranxPerMonth());
				incomeRangeValue.setNoofTranxPerAnnum(incomValueTransactionDataTable.getNoofTranxPerAnnum());
				incomeRangeValue.setIncomeRangeFrom(incomValueTransactionDataTable.getIncomeRangeFrom());
				incomeRangeValue.setIncomeRangeTo(incomValueTransactionDataTable.getIncomeRangeTo());
				incomeRangeValue.setCreatedBy(incomValueTransactionDataTable.getCreatedBy());
				incomeRangeValue.setCreatedDate(incomValueTransactionDataTable.getCreatedDate());
				incomeRangeValue.setModifiedBy(incomValueTransactionDataTable.getModifiedBy());
				incomeRangeValue.setModifiedDate(incomValueTransactionDataTable.getModifiedDate());
				incomeRangeValue.setApprovedBy(incomValueTransactionDataTable.getApprovedBy());
				incomeRangeValue.setApprovedDate(incomValueTransactionDataTable.getApprovedDate());
				incomeRangeValue.setRemarks(incomValueTransactionDataTable.getRemarks());
				incomeRangeValue.setIsActive(incomValueTransactionDataTable.getIsActive());
				/*
				 * boolean isExist = false; isExist =
				 * getIncomeRangeValueService(
				 * ).isExistIncomeRange(sessionStateManage.getCountryId(),
				 * incomValueTransactionDataTable.getIncomeRangeFrom(),
				 * incomValueTransactionDataTable.getIncomeRangeTo());
				 */// if (isExist) {
				getIncomeRangeValueService().save(incomeRangeValue);
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("complete.show();");
				/*
				 * } else { RequestContext context =
				 * RequestContext.getCurrentInstance();
				 * context.execute("isExist.show();");
				 * 
				 * }
				 */

			}
		}

	}

	public void clickOnOK() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomevaluetransaction.xhtml");
			lstIncomeValueTranxDataTable.clear();
			setBooIncomeRangeValueTranx(true);
			setBooIncomeRangeValueTranxDataTable(false);
			clearAllFields();
			setBoodisable(false);
			setBooEdit(false);
			setBooBtnClear(false);

		} catch (Exception e) {
			LOGGER.info("Problem to redirect:" + e);
		}

	}
	public void clickOnOKButton() {
		try {
			clearAllFields();
		/*	FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomevaluetransaction.xhtml");
			//lstIncomeValueTranxDataTable.clear();
			setBooIncomeRangeValueTranx(true);
			setBooIncomeRangeValueTranxDataTable(false);
			
			setBoodisable(false);
			setBooEdit(false);
			setBooBtnClear(false);*/

		} catch (Exception e) {
			LOGGER.info("Problem to redirect:" + e);
		}

	}
	

	public void view() {
		lstIncomeValueTranxDataTable.clear();

		lstOfIncomeRangeValue = incomeRangeValueService.getIncomeRangeValueList();

		if (lstOfIncomeRangeValue == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("isEmpty.show();");
		} else {
			for (IncomeRangeValue incomeRangeValue : lstOfIncomeRangeValue) {

				IncomValueTransactionDataTable incomValueTransactionDataTable = new IncomValueTransactionDataTable();

				incomValueTransactionDataTable.setAppCountryId(sessionStateManage.getCountryId());
				incomValueTransactionDataTable.setIncomeValueTransactionId(incomeRangeValue.getIncomeValueTransactionId());
				incomValueTransactionDataTable.setIncomeRangeFrom(incomeRangeValue.getIncomeRangeFrom());
				incomValueTransactionDataTable.setIncomeRangeTo(incomeRangeValue.getIncomeRangeTo());
				incomValueTransactionDataTable.setValuePerTranx(incomeRangeValue.getValuePerTranx());
				incomValueTransactionDataTable.setValuePerDay(incomeRangeValue.getValuePerDay());
				incomValueTransactionDataTable.setValuePerWeek(incomeRangeValue.getValuePerWeek());
				incomValueTransactionDataTable.setValuePerMonth(incomeRangeValue.getValuePerMonth());
				incomValueTransactionDataTable.setValuePerAnnum(incomeRangeValue.getValuePerAnnum());
				incomValueTransactionDataTable.setNoofTranxPerDay(incomeRangeValue.getNoofTranxPerDay());
				incomValueTransactionDataTable.setNoofTranxPerWeek(incomeRangeValue.getNoofTranxPerWeek());
				incomValueTransactionDataTable.setNoofTranxPerMonth(incomeRangeValue.getNoofTranxPerMonth());
				incomValueTransactionDataTable.setNoofTranxPerAnnum(incomeRangeValue.getNoofTranxPerAnnum());
				incomValueTransactionDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(incomeRangeValue.getIsActive() ));

				incomValueTransactionDataTable.setModifiedBy(incomeRangeValue.getModifiedBy());
				incomValueTransactionDataTable.setModifiedDate(incomeRangeValue.getModifiedDate());
				incomValueTransactionDataTable.setApprovedBy(incomeRangeValue.getApprovedBy());
				incomValueTransactionDataTable.setApprovedDate(incomeRangeValue.getApprovedDate());

				incomValueTransactionDataTable.setIsActive(incomeRangeValue.getIsActive());

				incomValueTransactionDataTable.setCreatedBy(incomeRangeValue.getCreatedBy());
				incomValueTransactionDataTable.setCreatedDate(incomeRangeValue.getCreatedDate());
				// incomValueTransactionDataTable.setIsActive(Constants.U);

				lstIncomeValueTranxDataTable.add(incomValueTransactionDataTable);
				setBooIncomeRangeValueTranx(true);
				setBooIncomeRangeValueTranxDataTable(true);
				setBoodisable(false);
				setBooBtnClear(false);
				setBooEdit(false);
				clearAllFields();

			}

		}

	}

	public void exit() {
		LOGGER.info("Enter in exit method ");

		try {
			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				lstIncomeValueTranxDataTable.clear();

				setBoodisable(false);
				setBooBtnClear(false);
				setBooEdit(false);
				clearAllFields();
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				lstIncomeValueTranxDataTable.clear();

				clearAllFields();
			}
			/*
			 * setBooIncomeRangeDetails(false); setBooEdit(false);
			 */
		} catch (Exception e) {
			LOGGER.info("Problem to redirect:" + e);
		}

		LOGGER.info("Exit from exit method ");
	}

	public void checkStatusType(IncomValueTransactionDataTable incomValueTransactionDataTable) throws IOException {

		if (incomValueTransactionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {

			incomValueTransactionDataTable.setRemarksCheck(true);
			setApprovedBy(incomValueTransactionDataTable.getApprovedBy());
			setApprovedDate(incomValueTransactionDataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		} else if (incomValueTransactionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&incomValueTransactionDataTable.getModifiedBy()!=null&&incomValueTransactionDataTable.getModifiedDate()!=null) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (incomValueTransactionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			incomValueTransactionDataTable.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if (incomValueTransactionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)){
			incomValueTransactionDataTable.setBooCheckDelete(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
		}
		else if (incomValueTransactionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			lstIncomeValueTranxDataTable.remove(incomValueTransactionDataTable);

		}
	}

	public void confirmPermanentDelete() {
		if (lstIncomeValueTranxDataTable.size() > 0) {
			for (IncomValueTransactionDataTable incomValueTransactionDataTable : lstIncomeValueTranxDataTable) {
				if (incomValueTransactionDataTable.getBooCheckDelete() != null) {
					if (incomValueTransactionDataTable.getBooCheckDelete().equals(true)) {

						delete(incomValueTransactionDataTable);
						view();
						// view();

					}
				}
			}
		}

	}

	public void activateRecord() {

		if (lstIncomeValueTranxDataTable.size() > 0) {
			for (IncomValueTransactionDataTable incomValueTransactionDataTable : lstIncomeValueTranxDataTable) {
				if (incomValueTransactionDataTable.getActivateRecordCheck() != null) {
					if (incomValueTransactionDataTable.getActivateRecordCheck().equals(true)) {
						getIncomeRangeValueService().activateRecord(incomValueTransactionDataTable.getIncomeValueTransactionId(), sessionStateManage.getUserName());
						view();
						cancel();
						return;
					}
				}
			}
		}

	}
	public void clearRemarks(){
		setRemarks( null);
	}

	public void remarkSelectedRecord() throws IOException {

		for (IncomValueTransactionDataTable incomValueTransactionDataTable : lstIncomeValueTranxDataTable) {
			if (incomValueTransactionDataTable.getRemarksCheck().equals(true)) {
				if (!getRemarks().equals("")) {
					incomValueTransactionDataTable.setRemarks(getRemarks());
					incomValueTransactionDataTable.setApprovedBy(null);
					incomValueTransactionDataTable.setApprovedDate(null);
					incomValueTransactionDataTable.setRemarksCheck(true);
					update(incomValueTransactionDataTable);
					lstIncomeValueTranxDataTable.clear();
					view();
					cancel();
				} else {
					RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
					return;

				}
			}

		}

	}

	public void delete(IncomValueTransactionDataTable incomValueTransactionDataTable) {

		getIncomeRangeValueService().delete(incomValueTransactionDataTable.getIncomeValueTransactionId());

	}

	public void update(IncomValueTransactionDataTable incomValueTransactionDataTable) throws IOException {
		IncomeRangeValue incomeRangeValue = new IncomeRangeValue();
		try {

			incomeRangeValue.setIncomeValueTransactionId(incomValueTransactionDataTable.getIncomeValueTransactionId());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(incomValueTransactionDataTable.getAppCountryId());
			incomeRangeValue.setAppCountryId(countryMaster);

			incomeRangeValue.setValuePerTranx(incomValueTransactionDataTable.getValuePerTranx());
			incomeRangeValue.setValuePerDay(incomValueTransactionDataTable.getValuePerDay());
			incomeRangeValue.setValuePerWeek(incomValueTransactionDataTable.getValuePerWeek());
			incomeRangeValue.setValuePerMonth(incomValueTransactionDataTable.getValuePerMonth());
			incomeRangeValue.setValuePerAnnum(incomValueTransactionDataTable.getValuePerAnnum());
			incomeRangeValue.setNoofTranxPerDay(incomValueTransactionDataTable.getNoofTranxPerDay());
			incomeRangeValue.setNoofTranxPerWeek(incomValueTransactionDataTable.getNoofTranxPerWeek());
			incomeRangeValue.setNoofTranxPerMonth(incomValueTransactionDataTable.getNoofTranxPerMonth());
			incomeRangeValue.setNoofTranxPerAnnum(incomValueTransactionDataTable.getNoofTranxPerAnnum());
			incomeRangeValue.setIncomeRangeFrom(incomValueTransactionDataTable.getIncomeRangeFrom());
			incomeRangeValue.setIncomeRangeTo(incomValueTransactionDataTable.getIncomeRangeTo());
			incomeRangeValue.setCreatedBy(incomValueTransactionDataTable.getCreatedBy());
			incomeRangeValue.setCreatedDate(incomValueTransactionDataTable.getCreatedDate());

			incomeRangeValue.setModifiedBy(sessionStateManage.getUserName());
			incomeRangeValue.setModifiedDate(new Date());
			incomeRangeValue.setApprovedBy(null);
			incomeRangeValue.setApprovedDate(null);
			incomeRangeValue.setRemarks(incomValueTransactionDataTable.getRemarks());
			incomeRangeValue.setIsActive(Constants.D);
			incomeRangeValue.setCreatedBy(incomValueTransactionDataTable.getCreatedBy());
			incomeRangeValue.setCreatedDate(incomValueTransactionDataTable.getCreatedDate());

			getIncomeRangeValueService().save(incomeRangeValue);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void checkMin() {
		if (getIncomeRangeTo() != null && getIncomeRangeFrom() != null) {

			if (getIncomeRangeTo().intValue() < getIncomeRangeFrom().intValue()) {
				setIncomeRangeTo(null);
				// set
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	public void checkMax() {

		if (getIncomeRangeTo() != null && getIncomeRangeFrom() != null) {

			if (getIncomeRangeTo().intValue() < getIncomeRangeFrom().intValue()) {
				// setFromBankBranch(null);
				setIncomeRangeTo(null);

				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	public void cancel() {
		setRemarks("");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/incomevaluetransaction.xhtml");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void approveView() {

		lstOfIncomeRangeValue = incomeRangeValueService.getIncomeRangeValueList();

		if (lstOfIncomeRangeValue == null) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("isEmpty.show();");
		} else {
			for (IncomeRangeValue incomeRangeValue : lstOfIncomeRangeValue) {

				IncomValueTransactionDataTable incomValueTransactionDataTable = new IncomValueTransactionDataTable();

				if (incomeRangeValue.getIsActive().equalsIgnoreCase(Constants.U)) {
					incomValueTransactionDataTable.setAppCountryId(sessionStateManage.getCountryId());
					incomValueTransactionDataTable.setIncomeValueTransactionId(incomeRangeValue.getIncomeValueTransactionId());
					incomValueTransactionDataTable.setIncomeRangeFrom(incomeRangeValue.getIncomeRangeFrom());
					incomValueTransactionDataTable.setIncomeRangeTo(incomeRangeValue.getIncomeRangeTo());
					incomValueTransactionDataTable.setValuePerTranx(incomeRangeValue.getValuePerTranx());
					incomValueTransactionDataTable.setValuePerDay(incomeRangeValue.getValuePerDay());
					incomValueTransactionDataTable.setValuePerWeek(incomeRangeValue.getValuePerWeek());
					incomValueTransactionDataTable.setValuePerMonth(incomeRangeValue.getValuePerMonth());
					incomValueTransactionDataTable.setValuePerAnnum(incomeRangeValue.getValuePerAnnum());
					incomValueTransactionDataTable.setNoofTranxPerDay(incomeRangeValue.getNoofTranxPerDay());
					incomValueTransactionDataTable.setNoofTranxPerWeek(incomeRangeValue.getNoofTranxPerWeek());
					incomValueTransactionDataTable.setNoofTranxPerMonth(incomeRangeValue.getNoofTranxPerMonth());
					incomValueTransactionDataTable.setNoofTranxPerAnnum(incomeRangeValue.getNoofTranxPerAnnum());
					incomValueTransactionDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(incomeRangeValue.getIsActive()));

					incomValueTransactionDataTable.setModifiedBy(incomeRangeValue.getModifiedBy());
					incomValueTransactionDataTable.setModifiedDate(incomeRangeValue.getModifiedDate());
					incomValueTransactionDataTable.setApprovedBy(incomeRangeValue.getApprovedBy());
					incomValueTransactionDataTable.setApprovedDate(incomeRangeValue.getApprovedDate());
					incomValueTransactionDataTable.setCreatedBy(incomeRangeValue.getCreatedBy());

					incomValueTransactionDataTable.setCreatedDate(incomeRangeValue.getCreatedDate());

					lstIncomeValueTranxDataTable.add(incomValueTransactionDataTable);
					setBooIncomeRangeValueTranx(true);
					setBooIncomeRangeValueTranxDataTable(true);
					setBoodisable(false);
					setBooBtnClear(false);
					setBooEdit(false);
					clearAllFields();
				} else {
					RequestContext context = RequestContext.getCurrentInstance();
					context.execute("dataNotFund.show();");
				}

			}

		}

	}

	public void approve(IncomValueTransactionDataTable incomValueTransactionDataTable) {

		if (incomValueTransactionDataTable.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())) {

			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("notValidUser.show();");

		} else {

			setIncomeValueTransactionId(incomValueTransactionDataTable.getIncomeValueTransactionId());
			setAppCountryId(incomValueTransactionDataTable.getAppCountryId());
			setIncomeRangeFrom(incomValueTransactionDataTable.getIncomeRangeFrom());
			setIncomeRangeTo(incomValueTransactionDataTable.getIncomeRangeTo());
			setValuePerTranx(incomValueTransactionDataTable.getValuePerTranx());
			setValuePerDay(incomValueTransactionDataTable.getValuePerDay());
			setValuePerWeek(incomValueTransactionDataTable.getValuePerWeek());
			setValuePerMonth(incomValueTransactionDataTable.getValuePerMonth());
			setValuePerAnnum(incomValueTransactionDataTable.getValuePerAnnum());
			setNoofTranxPerDay(incomValueTransactionDataTable.getNoofTranxPerDay());
			setNoofTranxPerWeek(incomValueTransactionDataTable.getNoofTranxPerWeek());
			setNoofTranxPerMonth(incomValueTransactionDataTable.getNoofTranxPerMonth());
			setNoofTranxPerAnnum(incomValueTransactionDataTable.getNoofTranxPerAnnum());
			setCreatedBy(incomValueTransactionDataTable.getCreatedBy());
			setCreatedDate(incomValueTransactionDataTable.getCreatedDate());
			setModifiedBy(incomValueTransactionDataTable.getModifiedBy());
			setModifiedDate(incomValueTransactionDataTable.getModifiedDate());

			setBooIncomeRangeValueTranx(true);
			setBooIncomeRangeValueTranxDataTable(false);
			setBoodisable(false);
			setBooBtnClear(false);
			setBooEdit(false);
		}

	}

	public void approveSave() {

		IncomeRangeValue incomeRangeValue = new IncomeRangeValue();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getAppCountryId());
		incomeRangeValue.setAppCountryId(countryMaster);

		incomeRangeValue.setIncomeValueTransactionId(getIncomeValueTransactionId());
		incomeRangeValue.setIncomeRangeFrom(getIncomeRangeFrom());
		incomeRangeValue.setIncomeRangeTo(getIncomeRangeTo());
		incomeRangeValue.setValuePerTranx(getValuePerTranx());
		incomeRangeValue.setValuePerDay(getValuePerDay());
		incomeRangeValue.setValuePerWeek(getValuePerWeek());
		incomeRangeValue.setValuePerMonth(getValuePerMonth());
		incomeRangeValue.setValuePerAnnum(getValuePerAnnum());
		incomeRangeValue.setNoofTranxPerDay(getNoofTranxPerDay());
		incomeRangeValue.setNoofTranxPerWeek(getNoofTranxPerWeek());
		incomeRangeValue.setNoofTranxPerMonth(getNoofTranxPerMonth());
		incomeRangeValue.setNoofTranxPerAnnum(getNoofTranxPerAnnum());

		incomeRangeValue.setCreatedBy(getCreatedBy());
		incomeRangeValue.setCreatedDate(getCreatedDate());
		incomeRangeValue.setModifiedBy(getModifiedBy());
		incomeRangeValue.setModifiedDate(getModifiedDate());
		incomeRangeValue.setApprovedBy(sessionStateManage.getUserName());
		incomeRangeValue.setApprovedDate(new Date());
		incomeRangeValue.setRemarks(null);
		incomeRangeValue.setIsActive(Constants.Yes);

		getIncomeRangeValueService().save(incomeRangeValue);

		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("complete.show();");

	}

	public void comparingValuePerDay(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getValuePerDay() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Value Per Day and Then Value Per Week", "Please Enter Value Per Day and Then Value Per Week");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getValuePerDay().compareTo(weeklyValue) > 0 || getValuePerDay().compareTo(weeklyValue) == 0) {
			setValuePerWeek(null);
			setValuePerMonth(null);
			setValuePerAnnum(null);

			FacesMessage msg = new FacesMessage("Please Enter Greater Than Value Per Day", "Please Enter Greater Than Value Per Day");

			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}

	public void comparingValuePerWeekly(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getValuePerDay() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Value Per Day and Then Value Per Week", "Please Enter Value Per Day and Then Value Per Week");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getValuePerDay().compareTo(weeklyValue) > 0 || getValuePerDay().compareTo(weeklyValue) == 0) {

			FacesMessage msg = new FacesMessage("Please Enter Greater Than Value Per Day", "Please Enter Greater Than Value Per Day");
			setValuePerWeek(null);
			setValuePerMonth(null);
			setValuePerAnnum(null);

			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingValuePerMonthly(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getValuePerWeek() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Value Per Week and Then Value Per Month", "Please Enter Value Per Week and Then Value Per Month");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getValuePerWeek().compareTo(weeklyValue) > 0 || getValuePerWeek().compareTo(weeklyValue) == 0) {

			FacesMessage msg = new FacesMessage("Please Enter Greater Than Value Per Week", "Please Enter Greater Than Value Per Week");
			setValuePerMonth(null);
			setValuePerAnnum(null);

			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingValuePerAnnum(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getValuePerMonth() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Value Per Month and Then Value Per Annum", "Please Enter Value Per Month and Then Value Per Annum");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getValuePerMonth().compareTo(weeklyValue) > 0 || getValuePerMonth().compareTo(weeklyValue) == 0) {
			setValuePerAnnum(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Value Per Month", "Please Enter Greater Than Value Per Month");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingNoOfTranxPerWeek(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getNoofTranxPerDay() == null) {
			FacesMessage msg = new FacesMessage("Please Enter No of Tranx Per Day and Then No of Tranx Per Week", "Please Enter No of Tranx Per Day and Then No of Tranx Per Week");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getNoofTranxPerDay().compareTo(weeklyValue) > 0 || getNoofTranxPerDay().compareTo(weeklyValue) == 0) {
			setNoofTranxPerWeek(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than No of Tranx Per Day", "Please Enter Greater Than No of Tranx Per Day");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingNoOfTranxPerMonth(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getNoofTranxPerWeek() == null) {
			FacesMessage msg = new FacesMessage("Please Enter No of Tranx Per Week and Then No of Tranx Per Month", "Please Enter No of Tranx Per Week and Then No of Tranx Per Month");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getNoofTranxPerWeek().compareTo(weeklyValue) > 0 || getNoofTranxPerWeek().compareTo(weeklyValue) == 0) {
			setNoofTranxPerMonth(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than No of Tranx Per Week", "Please Enter Greater Than No of Tranx Per Week");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingNoOfTranxAnnum(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getNoofTranxPerMonth() == null) {
			FacesMessage msg = new FacesMessage("Please Enter No of Tranx Per Month and Then No of Tranx Per Annum", "Please Enter No of Tranx Per Month and Then No of Tranx Per Annum");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getNoofTranxPerMonth().compareTo(weeklyValue) > 0 || getNoofTranxPerMonth().compareTo(weeklyValue) == 0) {
			setNoofTranxPerAnnum(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than No of Tranx Per Month", "Please Enter Greater Than No of Tranx Per Month");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

	public void comparingIncomeRangeTo(FacesContext context, UIComponent component, Object value) {
		BigDecimal weeklyValue = (BigDecimal) value;

		if (getIncomeRangeFrom() == null) {
			FacesMessage msg = new FacesMessage("Please Enter Income Range From Amount and Then Income Range To Amount", "Please Enter Income Range From Amount and Then Income Range To Amount");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		} else if (getIncomeRangeFrom().compareTo(weeklyValue) > 0 || getIncomeRangeFrom().compareTo(weeklyValue) == 0) {
			setIncomeRangeTo(null);
			FacesMessage msg = new FacesMessage("Please Enter Greater Than Income Range From Amount", "Please Enter Greater Than No of Tranx Per Month");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}

}
