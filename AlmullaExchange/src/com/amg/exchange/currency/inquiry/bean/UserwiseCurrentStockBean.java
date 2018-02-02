package com.amg.exchange.currency.inquiry.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.dto.CurrencyMasterDTO;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.currency.inquiry.service.ICurrencyEnquiryService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("userStock")
@Scope("session")
public class UserwiseCurrentStockBean<T> implements Serializable {
	private static final Logger LOG = Logger.getLogger(UserwiseCurrentStockBean.class);
	/*
	 * Autowire Declaration start here
	 */
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICurrencyEnquiryService currencyEnquiryService;
	private static final long serialVersionUID = 1L;
	/*
	 * Properties declaration start here
	 */
	private BigDecimal countryBranchId;
	private String username;
	private BigDecimal currencyId;
	private String currencyName;
	private BigDecimal totalFcAmount;
	private Boolean booDataTable;
	private Boolean booExit;
	List<StockDataTable> denominationList = new ArrayList<StockDataTable>();
	private List<CountryBranch> lstCountryBranchForLocation = new ArrayList<CountryBranch>();
	private List<Employee> cashierList = new ArrayList<Employee>();
	private List<CurrencyMasterDTO> currencyList = new ArrayList<CurrencyMasterDTO>();
	// Create Session
	SessionStateManage sessionStateManage = new SessionStateManage();

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<StockDataTable> getDenominationList() {
		return denominationList;
	}

	public Boolean getBooExit() {
		return booExit;
	}

	public void setBooExit(Boolean booExit) {
		this.booExit = booExit;
	}

	public Boolean getBooDataTable() {
		return booDataTable;
	}

	public void setBooDataTable(Boolean booDataTable) {
		this.booDataTable = booDataTable;
	}

	public void setDenominationList(List<StockDataTable> denominationList) {
		this.denominationList = denominationList;
	}

	public List<CurrencyMasterDTO> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<CurrencyMasterDTO> currencyList) {
		this.currencyList = currencyList;
	}

	public List<Employee> getCashierList() {
		return cashierList;
	}

	public void setCashierList(List<Employee> cashierList) {
		this.cashierList = cashierList;
	}

	public List<CountryBranch> getLstCountryBranchForLocation() {
		lstCountryBranchForLocation.clear();
		List<CountryBranch> lstCountryBranch = generalService.getBranchDetails(sessionStateManage.getCountryId());
		if (lstCountryBranch.size() != 0) {
			lstCountryBranchForLocation.addAll(lstCountryBranch);
		}
		return lstCountryBranchForLocation;
	}

	public void setLstCountryBranchForLocation(List<CountryBranch> lstCountryBranchForLocation) {
		this.lstCountryBranchForLocation = lstCountryBranchForLocation;
	}

	public void locationtoLocationByCountry() {
		lstCountryBranchForLocation.clear();
		List<CountryBranch> lstCountryBranch = generalService.getBranchDetails(sessionStateManage.getCountryId());
		if (lstCountryBranch.size() != 0) {
			lstCountryBranchForLocation.addAll(lstCountryBranch);
		}
	}
	
	
	private String errorMessage;
	private Boolean booVisible;
	
	
	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void getAllCasheirs() {
		try {
			setBooVisible(false);
			cashierList.clear();
			List<Employee> cashierList1 = currencyEnquiryService.getAllCashierList(getCountryBranchId());
			for (Employee employee : cashierList1) {
				/*
				 * if(!employee.getUserName().equalsIgnoreCase(sessionStateManage.
				 * getUserName())){ cashierList.add(employee); }
				 */
				cashierList.add(employee);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void getUserCurrency() {
		try {
			setBooVisible(false);
			currencyList.clear();
			List<CurrencyMasterDTO> currencyList1 = new ArrayList<CurrencyMasterDTO>();
			List<UserStockView> currencyList2 = new ArrayList<UserStockView>();
			currencyList1 = currencyEnquiryService.getCurrencyListByUser(getCountryBranchId(), getUsername());
			for (CurrencyMasterDTO userStockView : currencyList1) {
				currencyList.add(userStockView);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void getDenominationListByCurrency() {
		try {
			StockDataTable stockDataTable = null;
			denominationList.clear();
			BigDecimal total = new BigDecimal(0);
			List<UserStockView> list = currencyEnquiryService.getDenominationListByUserCurrency(getCountryBranchId(), getUsername(), getCurrencyId());
			for (UserStockView userStockView : list) {
				stockDataTable = new StockDataTable();
				stockDataTable.setDenominationDesc(userStockView.getDenomonationDesc());
				stockDataTable.setDenominationAmount(userStockView.getDenominationAmount());
				stockDataTable.setOpenQuantity(userStockView.getOpenQuantity());
				stockDataTable.setClosureQuantity(userStockView.getClosureQuantity());
				stockDataTable.setPurchaseQuantity(userStockView.getPurchaseQuantity());
				stockDataTable.setSaleQuantity(userStockView.getSaleQuantity());
				stockDataTable.setReceivedQuantity(userStockView.getReceivedQuantity());
				stockDataTable.setAccountYearMonth(userStockView.getAccountYearMonth());
				stockDataTable.setLogDate(userStockView.getLogDate());
				stockDataTable.setBranchName(userStockView.getBranchName());
				stockDataTable.setTransactionQuantity(userStockView.getTransactionQuantity());
				stockDataTable.setFcAmount(userStockView.getFcAmount());
				denominationList.add(stockDataTable);
			}
			setTotalFcAmount(calcTotalFcAmount());
			setBooDataTable(true);
			setBooExit(true);
		} catch (Exception e) {
		RequestContext.getCurrentInstance().execute("error.show();");
		setErrorMessage(e.toString());
		setBooVisible(true);
		return;}
	}

	public BigDecimal calcTotalFcAmount() {
		StockDataTable stockDataTable = null;
		BigDecimal total = new BigDecimal(0);
		BigDecimal subtotal = new BigDecimal(0);
		List<UserStockView> list = currencyEnquiryService.getDenominationListByUserCurrency(getCountryBranchId(), getUsername(), getCurrencyId());
		for (UserStockView userStockView : list) {
			stockDataTable = new StockDataTable();
			if (stockDataTable.getFcAmount() == null) {
				subtotal = new BigDecimal("0");
			} else {
				subtotal = stockDataTable.getFcAmount();
			}
			total = total.add(subtotal);
		}
		if (total == null || total == new BigDecimal("0") || total == new BigDecimal(0)) {
			total = new BigDecimal("0");
		}
		return total;
	}

	public BigDecimal getTotalFcAmount() {
		return totalFcAmount;
	}

	public void setTotalFcAmount(BigDecimal totalFcAmount) {
		this.totalFcAmount = totalFcAmount;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void userStockNavigation() {
		try {
			setBooDataTable(false);
			setBooExit(false);
			denominationList.clear();
			setCountryBranchId(null);
			setUsername(null);
			setCurrencyId(null);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "userwisecurrentstock.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/userwisecurrentstock.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
}
