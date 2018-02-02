package com.amg.exchange.cashtransfer.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.model.CashDetails;
import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CompanyMasterDesc;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.model.UserStockView;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.foreigncurrency.model.ForeignCurrencyAdjust;
import com.amg.exchange.foreigncurrency.model.Stock;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashTransferFromCToCBean")
@Scope("session")
public class CashTransferFromCounterToCounterBean<T> implements Serializable {

	private static final Logger log = Logger
			.getLogger(CashTransferFromCounterToCounterBean.class);
	private static final long serialVersionUID = 1L;

	private BigDecimal fromLocation;
	private String fromLocationFullName;
	private String toLocation;
	private String fromCashier;
	private String toCashier;
	private String toCashierName;
	private String transferOption;
	// for making dataTable input fields read only
	private String warningMessage;
	private BigDecimal companyCode;
	private BigDecimal currentStock = null;
	private BigDecimal lineNo = BigDecimal.ZERO;
	BigDecimal stock = null;
	// currency id for removing all related currencies
	BigDecimal currencyForList;
	private List<StockCurrency> stockcurrencyList = new CopyOnWriteArrayList<StockCurrency>();
	private Map<BigDecimal, BigDecimal> denominationMap = new HashMap<BigDecimal, BigDecimal>();
	private List<CashTransferFromCounterToCounterDataTable> cashTransferSampleList = new ArrayList<CashTransferFromCounterToCounterDataTable>(); // sample
																																					// list
																																					// for
																																					// displaying

	// for total transfer amount calculation
	private BigDecimal calTotalTransfer = null;

	// SessionStateManage Declaration
	private SessionStateManage sessionManage = new SessionStateManage();
	private Constants constants = new Constants();

	private List<CountryBranch> lstCountryBranchForLocation = new ArrayList<CountryBranch>();
	private List<UserFinancialYear> financialYearList = new ArrayList<UserFinancialYear>();
	private List<Employee> cashierList = new ArrayList<Employee>();
	private List<CashTransferFromCounterToCounterDataTable> cashTransferCToCDataTableList = new ArrayList<CashTransferFromCounterToCounterDataTable>();
	private List<Stock> lstStockCouBranch = new ArrayList<Stock>();

	private Map<BigDecimal, String> branchMap = new HashMap<BigDecimal, String>();

	@Autowired
	ICashTransferLToLService cashTransferLToLService;

	@Autowired
	ISpecialCustomerDealRequestService<T> specialCustomerDealRequestService;

	public BigDecimal getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(BigDecimal companyCode) {
		this.companyCode = companyCode;
	}

	public String getToCashierName() {
		return toCashierName;
	}

	public void setToCashierName(String toCashierName) {
		this.toCashierName = toCashierName;
	}

	public List<CashTransferFromCounterToCounterDataTable> getCashTransferCToCDataTableList() {
		return cashTransferCToCDataTableList;
	}

	public void setCashTransferCToCDataTableList(
			List<CashTransferFromCounterToCounterDataTable> cashTransferCToCDataTableList) {
		this.cashTransferCToCDataTableList = cashTransferCToCDataTableList;
	}

	public String getWarningMessage() {
		return warningMessage;
	}

	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}

	public BigDecimal getCurrentStock() {
		return currentStock;
	}

	public void setCurrentStock(BigDecimal currentStock) {
		this.currentStock = currentStock;
	}

	public List<Employee> getCashierList() {
		return cashierList;
	}

	public void setCashierList(List<Employee> cashierList) {
		this.cashierList = cashierList;
	}

	public List<CountryBranch> getLstCountryBranchForLocation() {
		return lstCountryBranchForLocation;
	}

	public void setLstCountryBranchForLocation(
			List<CountryBranch> lstCountryBranchForLocation) {
		this.lstCountryBranchForLocation = lstCountryBranchForLocation;
	}

	public List<Stock> getLstStockCouBranch() {
		return lstStockCouBranch;
	}

	public void setLstStockCouBranch(List<Stock> lstStockCouBranch) {
		this.lstStockCouBranch = lstStockCouBranch;
	}

	@Autowired
	IGeneralService<T> generalService;

	public BigDecimal getFromLocation() {
		return fromLocation;
	}

	public void setFromLocation(BigDecimal fromLocation) {
		this.fromLocation = fromLocation;
	}

	public String getFromLocationFullName() {
		return fromLocationFullName;
	}

	public void setFromLocationFullName(String fromLocationFullName) {
		this.fromLocationFullName = fromLocationFullName;
	}

	public String getToLocation() {
		return toLocation;
	}

	public void setToLocation(String toLocation) {
		this.toLocation = toLocation;
	}

	public String getFromCashier() {
		return fromCashier;
	}

	public void setFromCashier(String fromCashier) {
		this.fromCashier = fromCashier;
	}

	public String getToCashier() {
		return toCashier;
	}

	public void setToCashier(String toCashier) {
		this.toCashier = toCashier;
	}

	public String getTransferOption() {
		return transferOption;
	}

	public void setTransferOption(String transferOption) {
		this.transferOption = transferOption;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void cashTransferCToCNavigation() {
		clearAll();
		cashTransferCToCDataTableList.clear();
		currencyStockDataTableList.clear();
		lineNo = BigDecimal.ZERO;
		// addRecordsToDataTable();
		setRenderDataTable(true);
		preprocessingMethods();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionManage.getCountryId(), sessionManage.getUserType(), sessionManage.getUserName(), "CashTransferFromCounterToCounter.xhtml");
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"../cashtransfer/CashTransferFromCounterToCounter.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void locationtoLocationByCountry() {
		lstCountryBranchForLocation.clear();
		List<CountryBranch> lstCountryBranch = generalService
				.getBranchDetails(sessionManage.getCountryId());

		for (CountryBranch branch : lstCountryBranch) {
			branchMap.put(branch.getCountryBranchId(), branch.getBranchName());
		}
		if (lstCountryBranch.size() != 0) {
			lstCountryBranchForLocation.addAll(lstCountryBranch);
		}

		// setting from location and to location based on session bcoz with in a
		// branch location
		// setFromLocation(se);

	}

	public void preprocessingMethods() {
		// locationtoLocationByCountry();
		setFromLocation(new BigDecimal(sessionManage.getBranchId()));
		List<CountryBranch> lstCountryBranch = generalService
				.getBranchDetailsFromBeneStatus(sessionManage.getCountryId(),
						getFromLocation());
		for (CountryBranch countryBranch : lstCountryBranch) {
			setFromLocationFullName(countryBranch.getBranchName());
		}

		fromToLocationCountryBranch();

		setFromCashier(sessionManage.getUserName());
	}

	public void getAllCasheirs(BigDecimal branchId, BigDecimal roleId) {
		cashierList.clear();
		setToCashier(null);
		List<Employee> cashierList1 = cashTransferLToLService
				.getAllCashierList(branchId, roleId);
		for (Employee employee : cashierList1) {
			if (!employee.getUserName().equalsIgnoreCase(
					sessionManage.getUserName())) {
				cashierList.add(employee);
			}
		}
		/*
		 * if(cashierList.size()>0){ setCashierList(cashierList); }
		 */
	}

	public void fetchTocashier() {
		setToCashierName(null);
		if (cashierList != null && !cashierList.isEmpty()) {
			for (Employee employee : cashierList) {
				if (employee.getEmployeeId().compareTo(
						new BigDecimal(getToCashier())) == 0) {
					setToCashierName(employee.getUserName());
					break;
				}
			}
		}

		cashTransfer();

	}

	public void fromToLocationCountryBranch() {
		try {
			cashTransferCToCDataTableList.clear();
			// setToLocation(branchMap.get(getFromLocation()));
			setToLocation(getFromLocationFullName());
			BigDecimal roleId = new BigDecimal(sessionManage.getRoleId());
			getAllCasheirs(getFromLocation(), roleId);

			getAllCurrencyDenominationFromStock();
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public void clearAll() {
		lstCountryBranchForLocation.clear();
		branchMap.clear();
		setFromLocation(null);
		setToLocation(null);
		setFromCashier(null);
		setToCashier(null);
		setTransferOption(null);
		cashierList.clear();
		cashDetailList.clear();
		foreignCurrencyAdjustList.clear();
	}

	/*
	 * public void getAllCurrencyDenominationFromStock(){
	 * addRecordsToDataTable(); List<Stock>
	 * denominationStock=cashTransferLToLService
	 * .getAllCurrencyDenominationFromStock(sessionManage.getCountryId(),
	 * sessionManage.getUserName(), getFromLocation(),
	 * sessionManage.getCompanyId()); for(Stock stock:denominationStock){
	 * CashTransferForLToLBeanDataTable cashTransferDataTable=new
	 * CashTransferForLToLBeanDataTable();
	 * cashTransferDataTable.setCurencyId(stock
	 * .getCurrencyId().getCurrencyId());
	 * cashTransferDataTable.setCurrencyName(generalService
	 * .getCurrencyName(stock.getCurrencyId().getCurrencyId()));
	 * cashTransferDataTableList.add(cashTransferDataTable); } }
	 */

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;

	// getting all stock currency wise
	public void getAllCurrencyDenominationFromStock() {
		denominationMap.clear();
		getDenominationMap();
		stockcurrencyList.clear();
		cashTransferCToCDataTableList.clear();
		// service for getting stock from STOCK table
		List<Stock> denominationStock = cashTransferLToLService
				.getAllCurrencyDenominationFromStock(
						sessionManage.getCountryId(),
						sessionManage.getUserName(), getFromLocation(),
						sessionManage.getCompanyId());
		if (denominationStock.size() > 0) {

			for (Stock element : denominationStock) {
				// formula for calculating stock
				stock = new BigDecimal(element.getOpenQuantity())
						.add(new BigDecimal(element.getPurchaseQuantity()))
						.add(new BigDecimal(element.getReceivedQuantity()))
						.subtract(
								(new BigDecimal(element.getSaleQuantity())
										.add(new BigDecimal(element
												.getTransactionQuantity()))));
				StockCurrency stockCurrency = new StockCurrency();
				stockCurrency.setStockId(stock);
				stockCurrency.setDenominationId(element.getDenominationId()
						.getDenominationId());
				stockCurrency.setCurrencyId(element.getCurrencyId()
						.getCurrencyId());
				stockcurrencyList.add(stockCurrency);
			}
			// currency wise stock display
			for (int i = 0; i <= stockcurrencyList.size() - 1;) {
				CashTransferFromCounterToCounterDataTable cashTransferDataTable = new CashTransferFromCounterToCounterDataTable();
				for (int j = i; j <= stockcurrencyList.size() - 1; j++) {
					StockCurrency stock = stockcurrencyList.get(i);
					StockCurrency stock1 = stockcurrencyList.get(j);

					// checking currency one by one with "stockcurrencyList"
					if (stock.getCurrencyId().equals(stock1.getCurrencyId())) {

						// denominationMap.get(stock1.getDenominationId()) it
						// return equalent denomination amount with respect to
						// denomination id
						// ex:- if denomination id equal to 8 the respective
						// denomination amount is 1000
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.THOUSAND)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getThousands() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getThousands())
										.add(stock1.getStockId());
								cashTransferDataTable.setThousands(totalStock);
								cashTransferDataTable
										.setThousandsDeniminationId(stock1
												.getDenominationId());
							}

						} else {
							if (cashTransferDataTable.getThousands() == null) {
								cashTransferDataTable
										.setThousands(BigDecimal.ZERO);
								cashTransferDataTable
										.setThousandsDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.FIVE_HUNDRED)) {

							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getFiveHundreds() == null ? BigDecimal.ZERO
										: cashTransferDataTable
												.getFiveHundreds()).add(stock1
										.getStockId());
								cashTransferDataTable
										.setFiveHundreds(totalStock);
								cashTransferDataTable
										.setFiveHundredsDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getFiveHundreds() == null) {
								cashTransferDataTable
										.setFiveHundreds(BigDecimal.ZERO);
								cashTransferDataTable
										.setFiveHundredsDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.TWO_HUNDRED)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getTwoHundreds() == null ? BigDecimal.ZERO
										: cashTransferDataTable
												.getTwoHundreds()).add(stock1
										.getStockId());
								cashTransferDataTable
										.setTwoHundreds(totalStock);
								cashTransferDataTable
										.setTwoHundredsDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getTwoHundreds() == null) {
								cashTransferDataTable
										.setTwoHundreds(BigDecimal.ZERO);
								cashTransferDataTable
										.setTwoHundredsDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.HUNDRED)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getHundreds() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getHundreds())
										.add(stock1.getStockId());
								cashTransferDataTable.setHundreds(totalStock);
								cashTransferDataTable
										.setHundredsDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getHundreds() == null) {
								cashTransferDataTable
										.setHundreds(BigDecimal.ZERO);
								cashTransferDataTable
										.setHundredsDeniminationId(stock1
												.getDenominationId());
							}
						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.FIFTY)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getFifties() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getFifties())
										.add(stock1.getStockId());
								cashTransferDataTable.setFifties(totalStock);
								cashTransferDataTable
										.setFiftiesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getFifties() == null) {
								cashTransferDataTable
										.setFifties(BigDecimal.ZERO);
								cashTransferDataTable
										.setFiftiesDeniminationId(stock1
												.getDenominationId());
							}

						}

						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.TWENTY)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getTwenties() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getTwenties())
										.add(stock1.getStockId());
								cashTransferDataTable.setTwenties(totalStock);
								cashTransferDataTable
										.setTwentiesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getTwenties() == null) {
								cashTransferDataTable
										.setTwenties(BigDecimal.ZERO);
								cashTransferDataTable
										.setTwentiesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.TEN)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getTens() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getTens())
										.add(stock1.getStockId());
								cashTransferDataTable.setTens(totalStock);
								cashTransferDataTable
										.setTensDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getTens() == null) {
								cashTransferDataTable.setTens(BigDecimal.ZERO);
								cashTransferDataTable
										.setTensDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.FIVE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getFives() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getFives())
										.add(stock1.getStockId());
								cashTransferDataTable.setFives(totalStock);
								cashTransferDataTable
										.setFivesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getFives() == null) {
								cashTransferDataTable.setFives(BigDecimal.ZERO);
								cashTransferDataTable
										.setFivesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.ONE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getOnes() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getOnes())
										.add(stock1.getStockId());
								cashTransferDataTable.setOnes(totalStock);
								cashTransferDataTable
										.setOnesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getOnes() == null) {
								cashTransferDataTable.setOnes(BigDecimal.ZERO);
								cashTransferDataTable
										.setOnesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.POINT_FIVE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getPointfives() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getPointfives())
										.add(stock1.getStockId());
								cashTransferDataTable.setPointfives(totalStock);
								cashTransferDataTable
										.setPointfivesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getPointfives() == null) {
								cashTransferDataTable
										.setPointfives(BigDecimal.ZERO);
								cashTransferDataTable
										.setPointfivesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.POINT_TWO_FIVE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getPointTwoFives() == null ? BigDecimal.ZERO
										: cashTransferDataTable
												.getPointTwoFives()).add(stock1
										.getStockId());
								cashTransferDataTable
										.setPointTwoFives(totalStock);
								cashTransferDataTable
										.setPointTwoFivesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getPointTwoFives() == null) {
								cashTransferDataTable
										.setPointTwoFives(BigDecimal.ZERO);
								cashTransferDataTable
										.setPointTwoFivesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.POINT_ONE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getPointOnes() == null ? BigDecimal.ZERO
										: cashTransferDataTable.getPointOnes())
										.add(stock1.getStockId());
								cashTransferDataTable.setPointOnes(totalStock);
								cashTransferDataTable
										.setPointOnesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getPointOnes() == null) {
								cashTransferDataTable
										.setPointOnes(BigDecimal.ZERO);
								cashTransferDataTable
										.setPointOnesDeniminationId(stock1
												.getDenominationId());
							}

						}
						if (denominationMap.get(stock1.getDenominationId())
								.equals(Constants.POINT_ZERO_FIVE)) {
							if (stock1.getStockId() != null) {
								BigDecimal totalStock = (cashTransferDataTable
										.getPointZeroFives() == null ? BigDecimal.ZERO
										: cashTransferDataTable
												.getPointZeroFives())
										.add(stock1.getStockId());
								cashTransferDataTable
										.setPointZeroFives(totalStock);
								cashTransferDataTable
										.setPointZeroFivesDeniminationId(stock1
												.getDenominationId());
							}
						} else {
							if (cashTransferDataTable.getPointZeroFives() == null) {
								cashTransferDataTable
										.setPointZeroFives(BigDecimal.ZERO);
								cashTransferDataTable
										.setPointZeroFivesDeniminationId(stock1
												.getDenominationId());
							}

						}

						cashTransferDataTable.setCurrencyName(generalService
								.getCurrencyName(stock1.getCurrencyId()));
						cashTransferDataTable.setCurencyId(stock1
								.getCurrencyId());

						// this currency id setting for removing same currency
						// id that currency id not repeated again
						currencyForList = stock1.getCurrencyId();
					}

				}
				// after adding all stock related same currency removing that
				// Stock object From stockcurrencyList
				for (StockCurrency stock : stockcurrencyList) {
					if (stock.getCurrencyId().equals(currencyForList)) {
						stockcurrencyList.remove(stock);
					}
				}
				cashTransferSampleList.add(cashTransferDataTable);

				// calculating total transfer amount for single currency
				for (CashTransferFromCounterToCounterDataTable cashTransferDTObj : cashTransferSampleList) {
					BigDecimal calTotalTransfer = (cashTransferDTObj
							.getThousands() == null ? BigDecimal.ZERO
							: cashTransferDTObj.getThousands())
							.multiply(Constants.THOUSAND)
							.add((cashTransferDTObj.getFiveHundreds() == null ? BigDecimal.ZERO
									: cashTransferDTObj.getFiveHundreds())
									.multiply(Constants.FIVE_HUNDRED))
							.add((cashTransferDTObj.getTwoHundreds() == null ? BigDecimal.ZERO
									: cashTransferDTObj.getTwoHundreds())
									.multiply(Constants.TWO_HUNDRED))
							.add((cashTransferDTObj.getHundreds() == null ? BigDecimal.ZERO
									: cashTransferDTObj.getHundreds())
									.multiply(Constants.HUNDRED)
									.add((cashTransferDTObj.getFifties() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getFifties())
											.multiply(Constants.FIFTY))
									.add((cashTransferDTObj.getTwenties() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getTwenties())
											.multiply(Constants.TWENTY))
									.add((cashTransferDTObj.getTens() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getTens())
											.multiply(Constants.TEN))
									.add((cashTransferDTObj.getFives() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getFives())
											.multiply(Constants.FIVE))
									.add((cashTransferDTObj.getOnes() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getOnes())
											.multiply(Constants.ONE))
									.add((cashTransferDTObj.getPointfives() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getPointfives())
											.multiply(Constants.POINT_FIVE))
									.add((cashTransferDTObj.getPointTwoFives() == null ? BigDecimal.ZERO
											: cashTransferDTObj
													.getPointTwoFives())
											.multiply(Constants.POINT_TWO_FIVE))
									.add((cashTransferDTObj.getPointOnes() == null ? BigDecimal.ZERO
											: cashTransferDTObj.getPointOnes())
											.multiply(Constants.POINT_ONE))
									.add((cashTransferDTObj.getPointZeroFives() == null ? BigDecimal.ZERO
											: cashTransferDTObj
													.getPointZeroFives())
											.multiply(Constants.POINT_ZERO_FIVE)));
					cashTransferDataTable.setTotalTransfer(calTotalTransfer);
					calTotalTransfer = null;
				}

				// it add one one currency record to data table
				cashTransferCToCDataTableList.add(cashTransferDataTable);
				// this is temparory list for calculting total transfer amount
				cashTransferSampleList.clear();
			}

		} else {
			RequestContext.getCurrentInstance().execute("noStock.show();");
			// setFromLocation(null);
			// setToLocation(null);
			// setToCashier(null);
			return;
		}
	}

	public void getDenominationMap() {
		List<CurrencyWiseDenomination> denominationList = cashTransferLToLService
				.getAllCurrencyWiseDenomiantionList();
		for (CurrencyWiseDenomination denomination : denominationList) {
			denominationMap.put(denomination.getDenominationId(),
					denomination.getDenominationAmount());
		}
	}

	// calculation with 1000 values
	public void onCellEditThousands(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getThousandMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getThousandMap()
						.get(Constants.THOUSAND).multiply(Constants.THOUSAND);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getThousandMap().remove(Constants.THOUSAND);

			}

			if ((cashTransObj.getThousands().compareTo(
					cashTransObj.getInputThousands() == null ? BigDecimal.ZERO
							: cashTransObj.getInputThousands()) >= 0)
					|| (cashTransObj.getInputThousands() == null ? BigDecimal.ZERO
							: cashTransObj.getInputThousands())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputThousands() != null) {
					// putting data table input value into map
					cashTransObj.getThousandMap().clear();
					cashTransObj.getThousandMap().put(Constants.THOUSAND,
							cashTransObj.getInputThousands());
					addTotalTransfer = cashTransObj.getInputThousands()
							.multiply(Constants.THOUSAND);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputThousandsAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getThousandMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getThousandMap()
							.get(Constants.THOUSAND)
							.multiply(Constants.THOUSAND);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getThousandMap().remove(Constants.THOUSAND);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getThousands());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputThousands(null);
				if (cashTransObj.getThousandMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getThousandMap()
							.get(Constants.THOUSAND)
							.multiply(Constants.THOUSAND);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getThousandMap().remove(Constants.THOUSAND);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 500 values
	public void onCellEditFiveHundreds(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getFiveHundredMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getFiveHundredMap()
						.get(Constants.FIVE_HUNDRED)
						.multiply(Constants.FIVE_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getFiveHundredMap().remove(Constants.FIVE_HUNDRED);
			}

			if ((cashTransObj
					.getFiveHundreds()
					.compareTo(
							cashTransObj.getInputFiveHundreds() == null ? BigDecimal.ZERO
									: cashTransObj.getInputFiveHundreds()) >= 0)
					|| (cashTransObj.getInputFiveHundreds() == null ? BigDecimal.ZERO
							: cashTransObj.getInputFiveHundreds())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputFiveHundreds() != null) {
					// putting data table input value into map
					cashTransObj.getFiveHundredMap().clear();
					cashTransObj.getFiveHundredMap().put(
							Constants.FIVE_HUNDRED,
							cashTransObj.getInputFiveHundreds());
					addTotalTransfer = cashTransObj.getInputFiveHundreds()
							.multiply(Constants.FIVE_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj
							.setInputFiveHundredsAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getFiveHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiveHundredMap()
							.get(Constants.FIVE_HUNDRED)
							.multiply(Constants.FIVE_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiveHundredMap().remove(
							Constants.FIVE_HUNDRED);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getFiveHundreds());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputFiveHundreds(null);
				if (cashTransObj.getFiveHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiveHundredMap()
							.get(Constants.FIVE_HUNDRED)
							.multiply(Constants.FIVE_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiveHundredMap().remove(
							Constants.FIVE_HUNDRED);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 200 values
	public void onCellEditTwoHundreds(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getTwoHundredMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getTwoHundredMap()
						.get(Constants.TWO_HUNDRED)
						.multiply(Constants.TWO_HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getTwoHundredMap().remove(Constants.TWO_HUNDRED);
			}

			if ((cashTransObj
					.getTwoHundreds()
					.compareTo(
							cashTransObj.getInputTwoHundreds() == null ? BigDecimal.ZERO
									: cashTransObj.getInputTwoHundreds()) >= 0)
					|| (cashTransObj.getInputTwoHundreds() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTwoHundreds())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputTwoHundreds() != null) {
					// putting data table input value into map
					cashTransObj.getTwoHundredMap().clear();
					cashTransObj.getTwoHundredMap().put(Constants.TWO_HUNDRED,
							cashTransObj.getInputTwoHundreds());
					addTotalTransfer = cashTransObj.getInputTwoHundreds()
							.multiply(Constants.TWO_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputTwoHundredsAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getTwoHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTwoHundredMap()
							.get(Constants.TWO_HUNDRED)
							.multiply(Constants.TWO_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTwoHundredMap().remove(
							Constants.TWO_HUNDRED);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getTwoHundreds());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputTwoHundreds(null);
				if (cashTransObj.getTwoHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTwoHundredMap()
							.get(Constants.TWO_HUNDRED)
							.multiply(Constants.TWO_HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTwoHundredMap().remove(
							Constants.TWO_HUNDRED);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 100 values
	public void onCellEditHundreds(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getHundredMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getHundredMap()
						.get(Constants.HUNDRED).multiply(Constants.HUNDRED);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getHundredMap().remove(Constants.HUNDRED);
			}

			if ((cashTransObj.getHundreds().compareTo(
					cashTransObj.getInputHundreds() == null ? BigDecimal.ZERO
							: cashTransObj.getInputHundreds()) >= 0)
					|| (cashTransObj.getInputHundreds() == null ? BigDecimal.ZERO
							: cashTransObj.getInputHundreds())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputHundreds() != null) {
					// putting data table input value into map
					cashTransObj.getHundredMap().clear();
					cashTransObj.getHundredMap().put(Constants.HUNDRED,
							cashTransObj.getInputHundreds());
					addTotalTransfer = cashTransObj.getInputHundreds()
							.multiply(Constants.HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputHundredsAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getHundredMap()
							.get(Constants.HUNDRED).multiply(Constants.HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getHundredMap().remove(Constants.HUNDRED);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getHundreds());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputHundreds(null);
				if (cashTransObj.getHundredMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getHundredMap()
							.get(Constants.HUNDRED).multiply(Constants.HUNDRED);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getHundredMap().remove(Constants.HUNDRED);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 50 values
	public void onCellEditFifties(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getFiftieMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getFiftieMap()
						.get(Constants.FIFTY).multiply(Constants.FIFTY);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getFiftieMap().remove(Constants.FIFTY);
			}

			if ((cashTransObj.getFifties().compareTo(
					cashTransObj.getInputFifties() == null ? BigDecimal.ZERO
							: cashTransObj.getInputFifties()) >= 0)
					|| (cashTransObj.getInputFifties() == null ? BigDecimal.ZERO
							: cashTransObj.getInputFifties())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputFifties() != null) {
					// putting data table input value into map
					cashTransObj.getFiftieMap().clear();
					cashTransObj.getFiftieMap().put(Constants.FIFTY,
							cashTransObj.getInputFifties());
					addTotalTransfer = cashTransObj.getInputFifties().multiply(
							Constants.FIFTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputFiftiesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getFiftieMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiftieMap()
							.get(Constants.FIFTY).multiply(Constants.FIFTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiftieMap().remove(Constants.FIFTY);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getFifties());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputFifties(null);
				if (cashTransObj.getFiftieMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiftieMap()
							.get(Constants.FIFTY).multiply(Constants.FIFTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiftieMap().remove(Constants.FIFTY);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 20 values
	public void onCellEditTwenties(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getTwentieMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getTwentieMap()
						.get(Constants.TWENTY).multiply(Constants.TWENTY);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getTwentieMap().remove(Constants.TWENTY);
			}

			if ((cashTransObj.getTwenties().compareTo(
					cashTransObj.getInputTwenties() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTwenties()) >= 0)
					|| (cashTransObj.getInputTwenties() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTwenties())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputTwenties() != null) {
					// putting data table input value into map
					cashTransObj.getTwentieMap().clear();
					cashTransObj.getTwentieMap().put(Constants.TWENTY,
							cashTransObj.getInputTwenties());
					addTotalTransfer = cashTransObj.getInputTwenties()
							.multiply(Constants.TWENTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputTwentiesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getTwentieMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTwentieMap()
							.get(Constants.TWENTY).multiply(Constants.TWENTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTwentieMap().remove(Constants.TWENTY);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getTwenties());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputTwenties(null);
				if (cashTransObj.getTwentieMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTwentieMap()
							.get(Constants.TWENTY).multiply(Constants.TWENTY);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTwentieMap().remove(Constants.TWENTY);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 10 values
	public void onCellEditTens(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getTenMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getTenMap()
						.get(Constants.TEN).multiply(Constants.TEN);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getTenMap().remove(Constants.TEN);
			}

			if ((cashTransObj.getTens().compareTo(
					cashTransObj.getInputTens() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTens()) >= 0)
					|| (cashTransObj.getInputTens() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTens())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputTens() != null) {
					// putting data table input value into map
					cashTransObj.getTenMap().clear();
					cashTransObj.getTenMap().put(Constants.TEN,
							cashTransObj.getInputTens());
					addTotalTransfer = cashTransObj.getInputTens().multiply(
							Constants.TEN);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputTensAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getTenMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTenMap()
							.get(Constants.TEN).multiply(Constants.TEN);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTenMap().remove(Constants.TEN);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getTens());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputTens(null);
				if (cashTransObj.getTenMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getTenMap()
							.get(Constants.TEN).multiply(Constants.TEN);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getTenMap().remove(Constants.TEN);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 5 values
	public void onCellEditFives(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getFiveMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getFiveMap()
						.get(Constants.FIVE).multiply(Constants.FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getFiveMap().remove(Constants.FIVE);
			}

			if ((cashTransObj.getFives().compareTo(
					cashTransObj.getInputFives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputFives()) >= 0)
					|| (cashTransObj.getInputFives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputFives())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputFives() != null) {
					// putting data table input value into map
					cashTransObj.getFiveMap().clear();
					cashTransObj.getFiveMap().put(Constants.FIVE,
							cashTransObj.getInputFives());
					addTotalTransfer = cashTransObj.getInputFives().multiply(
							Constants.FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputFivesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiveMap()
							.get(Constants.FIVE).multiply(Constants.FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiveMap().remove(Constants.FIVE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getFives());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputFives(null);
				if (cashTransObj.getFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getFiveMap()
							.get(Constants.FIVE).multiply(Constants.FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getFiveMap().remove(Constants.FIVE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 1 values
	public void onCellEditOnes(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getOneMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getOneMap()
						.get(Constants.ONE).multiply(Constants.ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getOneMap().remove(Constants.ONE);
			}

			if ((cashTransObj.getOnes().compareTo(
					cashTransObj.getInputOnes() == null ? BigDecimal.ZERO
							: cashTransObj.getInputOnes()) >= 0)
					|| (cashTransObj.getInputOnes() == null ? BigDecimal.ZERO
							: cashTransObj.getInputOnes())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputOnes() != null) {
					// putting data table input value into map
					cashTransObj.getOneMap().clear();
					cashTransObj.getOneMap().put(Constants.ONE,
							cashTransObj.getInputOnes());
					addTotalTransfer = cashTransObj.getInputOnes().multiply(
							Constants.ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputOnesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getOneMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getOneMap()
							.get(Constants.ONE).multiply(Constants.ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getOneMap().remove(Constants.ONE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getOnes());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputOnes(null);
				if (cashTransObj.getOneMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getOneMap()
							.get(Constants.ONE).multiply(Constants.ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getOneMap().remove(Constants.ONE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 0.5 values
	public void onCellEditPointfives(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getPointfiveMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getPointfiveMap()
						.get(Constants.POINT_FIVE)
						.multiply(Constants.POINT_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
			}

			if ((cashTransObj.getPointfives().compareTo(
					cashTransObj.getInputPointfives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointfives()) >= 0)
					|| (cashTransObj.getInputPointfives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointfives())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputPointfives() != null) {
					// putting data table input value into map
					cashTransObj.getPointfiveMap().clear();
					cashTransObj.getPointfiveMap().put(Constants.POINT_FIVE,
							cashTransObj.getInputPointfives());
					addTotalTransfer = cashTransObj.getInputPointfives()
							.multiply(Constants.POINT_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputPointfivesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getPointfiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointfiveMap()
							.get(Constants.POINT_FIVE)
							.multiply(Constants.POINT_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getPointfives());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputPointfives(null);
				if (cashTransObj.getPointfiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointfiveMap()
							.get(Constants.POINT_FIVE)
							.multiply(Constants.POINT_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointfiveMap().remove(Constants.POINT_FIVE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 0.25 values
	public void onCellEditPointTwoFives(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getPointTwoFiveMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getPointTwoFiveMap()
						.get(Constants.POINT_TWO_FIVE)
						.multiply(Constants.POINT_TWO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getPointTwoFiveMap().remove(
						Constants.POINT_TWO_FIVE);
			}

			if ((cashTransObj
					.getPointTwoFives()
					.compareTo(
							cashTransObj.getInputPointTwoFives() == null ? BigDecimal.ZERO
									: cashTransObj.getInputPointTwoFives()) >= 0)
					|| (cashTransObj.getInputPointTwoFives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointTwoFives())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputPointTwoFives() != null) {
					// putting data table input value into map
					cashTransObj.getPointTwoFiveMap().clear();
					cashTransObj.getPointTwoFiveMap().put(
							Constants.POINT_TWO_FIVE,
							cashTransObj.getInputPointTwoFives());
					addTotalTransfer = cashTransObj.getInputPointTwoFives()
							.multiply(Constants.POINT_TWO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj
							.setInputPointTwoFivesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getPointTwoFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointTwoFiveMap()
							.get(Constants.POINT_TWO_FIVE)
							.multiply(Constants.POINT_TWO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointTwoFiveMap().remove(
							Constants.POINT_TWO_FIVE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getPointTwoFives());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputPointTwoFives(null);
				if (cashTransObj.getPointTwoFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointTwoFiveMap()
							.get(Constants.POINT_TWO_FIVE)
							.multiply(Constants.POINT_TWO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointTwoFiveMap().remove(
							Constants.POINT_TWO_FIVE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 0.1 values
	public void onCellEditPointOnes(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getPointOneMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getPointOneMap()
						.get(Constants.POINT_ONE).multiply(Constants.POINT_ONE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
			}

			if ((cashTransObj.getPointOnes().compareTo(
					cashTransObj.getInputPointOnes() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointOnes()) >= 0)
					|| (cashTransObj.getInputPointOnes() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointOnes())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputPointOnes() != null) {
					// putting data table input value into map
					cashTransObj.getPointOneMap().clear();
					cashTransObj.getPointOneMap().put(Constants.POINT_ONE,
							cashTransObj.getInputPointOnes());
					addTotalTransfer = cashTransObj.getInputPointOnes()
							.multiply(Constants.POINT_ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj.setInputPointOnesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getPointOneMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointOneMap()
							.get(Constants.POINT_ONE)
							.multiply(Constants.POINT_ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getPointOnes());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputPointOnes(null);
				if (cashTransObj.getPointOneMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointOneMap()
							.get(Constants.POINT_ONE)
							.multiply(Constants.POINT_ONE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointOneMap().remove(Constants.POINT_ONE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// calculation with 0.05 values
	public void onCellEditPointZeroFives(
			CashTransferFromCounterToCounterDataTable cashTransObj) {
		try {
			BigDecimal addTotalTransfer = null;
			BigDecimal subtractTotalTransfer = null;
			if (cashTransObj.getPointZeroFiveMap().size() > 0) {
				subtractTotalTransfer = cashTransObj.getPointZeroFiveMap()
						.get(Constants.POINT_ZERO_FIVE)
						.multiply(Constants.POINT_ZERO_FIVE);
				cashTransObj.setInputTotalTransfer((cashTransObj
						.getInputTotalTransfer() == null ? BigDecimal.ZERO
						: cashTransObj.getInputTotalTransfer())
						.subtract(subtractTotalTransfer));
				cashTransObj.getPointZeroFiveMap().remove(
						Constants.POINT_ZERO_FIVE);
			}

			if ((cashTransObj
					.getPointZeroFives()
					.compareTo(
							cashTransObj.getInputPointZeroFives() == null ? BigDecimal.ZERO
									: cashTransObj.getInputPointZeroFives()) >= 0)
					|| (cashTransObj.getInputPointZeroFives() == null ? BigDecimal.ZERO
							: cashTransObj.getInputPointZeroFives())
							.compareTo(BigDecimal.ZERO) == 0) {
				if (cashTransObj.getInputPointZeroFives() != null) {
					// putting data table input value into map
					cashTransObj.getPointZeroFiveMap().clear();
					cashTransObj.getPointZeroFiveMap().put(
							Constants.POINT_ZERO_FIVE,
							cashTransObj.getInputPointZeroFives());
					addTotalTransfer = cashTransObj.getInputPointZeroFives()
							.multiply(Constants.POINT_ZERO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.add(addTotalTransfer));
					cashTransObj
							.setInputPointZeroFivesAdjAmount(addTotalTransfer);
				} else if (cashTransObj.getPointZeroFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointZeroFiveMap()
							.get(Constants.POINT_ZERO_FIVE)
							.multiply(Constants.POINT_ZERO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointZeroFiveMap().remove(
							Constants.POINT_ZERO_FIVE);
				}
			} else {
				setCurrentStock(null);
				setCurrentStock(cashTransObj.getPointZeroFives());
				RequestContext.getCurrentInstance().execute(
						"stockcheck.show();");
				cashTransObj.setInputPointZeroFives(null);
				if (cashTransObj.getPointZeroFiveMap().size() > 0) {
					subtractTotalTransfer = cashTransObj.getPointZeroFiveMap()
							.get(Constants.POINT_ZERO_FIVE)
							.multiply(Constants.POINT_ZERO_FIVE);
					cashTransObj.setInputTotalTransfer((cashTransObj
							.getInputTotalTransfer() == null ? BigDecimal.ZERO
							: cashTransObj.getInputTotalTransfer())
							.subtract(subtractTotalTransfer));
					cashTransObj.getPointZeroFiveMap().remove(
							Constants.POINT_ZERO_FIVE);
				}

			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public void onCellEdit(CellEditEvent event) {
		try {
			Object oldValue = event.getOldValue();
			Object newValue = event.getNewValue();

			if (newValue != null && !newValue.equals(oldValue)) {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Cell Changed", "Old: " + oldValue + ", New:"
								+ newValue);
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public void callAddRecords() {

	}

	private Boolean renderDataTable = false;

	public Boolean getRenderDataTable() {
		return renderDataTable;
	}

	public void setRenderDataTable(Boolean renderDataTable) {
		this.renderDataTable = renderDataTable;
	}

	// set all data table input fields values to null
	public void makeAllValuesNull() {
		for (CashTransferFromCounterToCounterDataTable cashTransferDTObj : cashTransferCToCDataTableList) {
			cashTransferDTObj.setInputThousands(null);
			cashTransferDTObj.setInputFiveHundreds(null);
			cashTransferDTObj.setInputTwoHundreds(null);
			cashTransferDTObj.setInputHundreds(null);
			cashTransferDTObj.setInputFifties(null);
			cashTransferDTObj.setInputTwenties(null);
			cashTransferDTObj.setInputTens(null);
			cashTransferDTObj.setInputFives(null);
			cashTransferDTObj.setInputOnes(null);
			cashTransferDTObj.setInputPointfives(null);
			cashTransferDTObj.setInputPointTwoFives(null);
			cashTransferDTObj.setInputPointOnes(null);
			cashTransferDTObj.setInputPointZeroFives(null);
			cashTransferDTObj.setInputTotalTransfer(null);
			calTotalTransfer = null;
			cashTransferDTObj.getThousandMap().clear();
			cashTransferDTObj.getFiveHundredMap().clear();
			cashTransferDTObj.getTwoHundredMap().clear();
			cashTransferDTObj.getHundredMap().clear();
			cashTransferDTObj.getFiftieMap().clear();
			cashTransferDTObj.getTwentieMap().clear();
			cashTransferDTObj.getTenMap().clear();
			cashTransferDTObj.getFiveMap().clear();
			cashTransferDTObj.getOneMap().clear();
			cashTransferDTObj.getPointfiveMap().clear();
			cashTransferDTObj.getPointTwoFiveMap().clear();
			cashTransferDTObj.getPointOneMap().clear();
			cashTransferDTObj.getPointZeroFiveMap().clear();
		}
	}

	public void exit() throws IOException {
		if (sessionManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");
		}
	}

	/*
	 * //for getting document seriality Number public String
	 * getDocumentSerialID() { // TODO String documentSerialID =
	 * specialCustomerDealRequestService
	 * .getNextDocumentSerialNumber(Integer.parseInt
	 * (sessionManage.getSessionValue("countryId")),
	 * Integer.parseInt(sessionManage.getSessionValue("companyId")),
	 * Integer.parseInt(Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER),
	 * getFinaceYear(), Constants.Yes); return documentSerialID; }
	 */

	// for getting financial year
	public int getFinaceYear() {
		int finaceYear = 0;
		try {
			financialYearList = specialCustomerDealRequestService
					.getUserFinancialYear(new Date());
			log.info("financialYearList :" + financialYearList.size());
			if (financialYearList != null)
				finaceYear = Integer.parseInt(financialYearList.get(0)
						.getFinancialYear().toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return finaceYear;
	}

	List<CashDetails> cashDetailList = new ArrayList<CashDetails>();
	List<ForeignCurrencyAdjust> foreignCurrencyAdjustList = new ArrayList<ForeignCurrencyAdjust>();

	private int documentId = Integer
			.parseInt(Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER);

	public String getDocumentSerialID(String processIn) {

		String documentSerialId = "";
		log.info("process in :" + processIn);
		try {
			HashMap<String, String> outPutValues = generalService
					.getNextDocumentRefNumber(Integer.parseInt(sessionManage
							.getSessionValue("countryId")), Integer
							.parseInt(sessionManage
									.getSessionValue("companyId")), documentId,
							getFinaceYear(), processIn, sessionManage
									.getCountryBranchCode());
			String proceErrorMsg = outPutValues.get("PROCE_ERORRMSG");
			if (proceErrorMsg != null) {
				setWarningMessage(proceErrorMsg);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				documentSerialId = outPutValues.get("DOCNO");
			}
		} catch (Exception e) {
			setWarningMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}
		return documentSerialId;

		/*
		 * String documentSerialID =
		 * specialCustomerDealRequestService.getNextDocumentSerialNumber
		 * (Integer.parseInt(sessionManage.getSessionValue("countryId")),
		 * Integer.parseInt(sessionManage.getSessionValue("companyId")),
		 * documentId, getFinaceYear(), processIn); return documentSerialID;
		 */
	}

	// saving record
	/*
	 * public void saveInfoToDb(){ try{ String documentSerilId =
	 * getDocumentSerialID(Constants.U); if(!documentSerilId.trim().equals("")){
	 * BigDecimal saveDocumentSerialID = new BigDecimal(documentSerilId);
	 * 
	 * List<CompanyMasterDesc> companyCode =
	 * generalService.getCompanyList(sessionManage.getCompanyId(),
	 * sessionManage.getLanguageId()); if(companyCode != null &&
	 * !companyCode.isEmpty() && companyCode.get(0).getFsCompanyMaster() !=
	 * null){ BigDecimal companyCodeValue =
	 * companyCode.get(0).getFsCompanyMaster().getCompanyCode();
	 * setCompanyCode(companyCodeValue); }
	 * 
	 * lineNo = BigDecimal.ZERO; SimpleDateFormat DATE_FORMAT = new
	 * SimpleDateFormat("dd/MM/yyyy"); Date acc_Month = null;
	 * 
	 * try { acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat()); } catch
	 * (ParseException e) { e.printStackTrace(); }
	 * for(CashTransferFromCounterToCounterDataTable
	 * cashTransferDTObj1:cashTransferCToCDataTableList){ if(
	 * ((cashTransferDTObj1.getInputThousands()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputThousands()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputFiveHundreds()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputFiveHundreds()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputTwoHundreds()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputTwoHundreds()).compareTo(BigDecimal.ZERO)!=0)||
	 * ((cashTransferDTObj1.getInputHundreds()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputHundreds()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputFifties()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputFifties()).compareTo(BigDecimal.ZERO)!=0)||
	 * ((cashTransferDTObj1.getInputTwenties()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputTwenties()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputTens()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputTens()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputFives()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputFives()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputOnes()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputOnes()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputPointfives()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputPointfives()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputPointTwoFives()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputPointTwoFives()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputPointOnes()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputPointOnes()).compareTo(BigDecimal.ZERO)!=0) ||
	 * ((cashTransferDTObj1.getInputPointZeroFives()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj1
	 * .getInputPointZeroFives()).compareTo(BigDecimal.ZERO)!=0) ){ //save Cash
	 * Header information CashHeader cashHeader=
	 * saveCashHeader(saveDocumentSerialID); int i = 1;
	 * for(CashTransferFromCounterToCounterDataTable
	 * cashTransferDTObj:cashTransferCToCDataTableList){ //save Cash Details
	 * information if((cashTransferDTObj.getInputTotalTransfer()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputTotalTransfer()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * cashDetailList.add(saveCashDetail(cashTransferDTObj,cashHeader
	 * ,saveDocumentSerialID,i)); i++; } //save Foreign Currency Adjust
	 * information if((cashTransferDTObj.getInputThousands()==null ?
	 * BigDecimal.ZERO
	 * :cashTransferDTObj.getInputThousands()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputThousandsAdjAmount(),cashTransferDTObj
	 * .getInputThousands
	 * (),cashTransferDTObj.getThousandsDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputFiveHundreds()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputFiveHundreds()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * foreignCurrencyAdjustList.add(saveforeignCurrencyAdjust(cashHeader
	 * ,cashTransferDTObj
	 * .getCurencyId(),cashTransferDTObj.getInputFiveHundredsAdjAmount
	 * (),cashTransferDTObj
	 * .getInputFiveHundreds(),cashTransferDTObj.getFiveHundredsDeniminationId
	 * (),saveDocumentSerialID,acc_Month)); }
	 * if((cashTransferDTObj.getInputTwoHundreds()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj
	 * .getInputTwoHundreds()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputTwoHundredsAdjAmount(),cashTransferDTObj
	 * .getInputTwoHundreds
	 * (),cashTransferDTObj.getTwoHundredsDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputHundreds()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputHundreds()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * foreignCurrencyAdjustList.add(saveforeignCurrencyAdjust(cashHeader
	 * ,cashTransferDTObj
	 * .getCurencyId(),cashTransferDTObj.getInputHundredsAdjAmount
	 * (),cashTransferDTObj
	 * .getInputHundreds(),cashTransferDTObj.getHundredsDeniminationId
	 * (),saveDocumentSerialID,acc_Month)); }
	 * if((cashTransferDTObj.getInputFifties()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj
	 * .getInputFifties()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputFiftiesAdjAmount(),cashTransferDTObj
	 * .getInputFifties
	 * (),cashTransferDTObj.getFiftiesDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputTwenties()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputTwenties()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * foreignCurrencyAdjustList.add(saveforeignCurrencyAdjust(cashHeader
	 * ,cashTransferDTObj
	 * .getCurencyId(),cashTransferDTObj.getInputTwentiesAdjAmount
	 * (),cashTransferDTObj
	 * .getInputTwenties(),cashTransferDTObj.getTwentiesDeniminationId
	 * (),saveDocumentSerialID,acc_Month)); }
	 * if((cashTransferDTObj.getInputTens()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj
	 * .getInputTens()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputTensAdjAmount(),cashTransferDTObj
	 * .getInputTens(
	 * ),cashTransferDTObj.getTensDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputFives()==null ?
	 * BigDecimal.ZERO
	 * :cashTransferDTObj.getInputFives()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputFivesAdjAmount(),cashTransferDTObj
	 * .getInputFives
	 * (),cashTransferDTObj.getFivesDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputOnes()==null ?
	 * BigDecimal.ZERO
	 * :cashTransferDTObj.getInputOnes()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputOnesAdjAmount(),cashTransferDTObj
	 * .getInputOnes(
	 * ),cashTransferDTObj.getOnesDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputPointfives()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputPointfives()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * foreignCurrencyAdjustList.add(saveforeignCurrencyAdjust(cashHeader
	 * ,cashTransferDTObj
	 * .getCurencyId(),cashTransferDTObj.getInputPointfivesAdjAmount
	 * (),cashTransferDTObj
	 * .getInputPointfives(),cashTransferDTObj.getPointfivesDeniminationId
	 * (),saveDocumentSerialID,acc_Month)); }
	 * if((cashTransferDTObj.getInputPointTwoFives()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj
	 * .getInputPointTwoFives()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputPointTwoFivesAdjAmount(),cashTransferDTObj
	 * .getInputPointTwoFives
	 * (),cashTransferDTObj.getPointTwoFivesDeniminationId(
	 * ),saveDocumentSerialID,acc_Month)); }
	 * if((cashTransferDTObj.getInputPointOnes()==null ?
	 * BigDecimal.ZERO:cashTransferDTObj
	 * .getInputPointOnes()).compareTo(BigDecimal.ZERO)!=0){
	 * foreignCurrencyAdjustList
	 * .add(saveforeignCurrencyAdjust(cashHeader,cashTransferDTObj
	 * .getCurencyId()
	 * ,cashTransferDTObj.getInputPointOnesAdjAmount(),cashTransferDTObj
	 * .getInputPointOnes
	 * (),cashTransferDTObj.getPointOnesDeniminationId(),saveDocumentSerialID
	 * ,acc_Month)); } if((cashTransferDTObj.getInputPointZeroFives()==null ?
	 * BigDecimal
	 * .ZERO:cashTransferDTObj.getInputPointZeroFives()).compareTo(BigDecimal
	 * .ZERO)!=0){
	 * foreignCurrencyAdjustList.add(saveforeignCurrencyAdjust(cashHeader
	 * ,cashTransferDTObj
	 * .getCurencyId(),cashTransferDTObj.getInputPointZeroFivesAdjAmount
	 * (),cashTransferDTObj
	 * .getInputPointZeroFives(),cashTransferDTObj.getPointZeroFivesDeniminationId
	 * (),saveDocumentSerialID,acc_Month)); } } try {
	 * cashTransferLToLService.saveAllRecords
	 * (cashHeader,cashDetailList,foreignCurrencyAdjustList);
	 * 
	 * // call Procedure EX_P_POPULATE_CASH_TRANSFER String errmsg =
	 * cashTransferLToLService
	 * .procedurePopulateCashTransfer(sessionManage.getCountryId
	 * (),sessionManage.
	 * getCompanyId(),cashHeader.getDocumentCode(),cashHeader.getFinancialYear
	 * (),cashHeader.getDocumentNo());
	 * 
	 * if(errmsg != null){ setWarningMessage("Exception : "+errmsg);
	 * RequestContext.getCurrentInstance().execute("proceErr.show();"); break;
	 * }else{ RequestContext.getCurrentInstance().execute("success.show();");
	 * break; }
	 * 
	 * } catch (Exception e) { log.error("Error Occured While Saving :"
	 * +e.getMessage()); setWarningMessage("Exception : "+e.getMessage());
	 * RequestContext.getCurrentInstance().execute("proceErr.show();"); break; }
	 * }else{
	 * RequestContext.getCurrentInstance().execute("selectAmount.show();");
	 * break; } } }else{ setWarningMessage("Document Seriality Not Available ");
	 * RequestContext.getCurrentInstance().execute("proceErr.show();"); } }
	 * catch(Exception e){ if(e.getMessage()!=null){
	 * setWarningMessage("Exception :"+e.getMessage());
	 * RequestContext.getCurrentInstance().execute("proceErr.show();"); }else{
	 * setWarningMessage("Exception :"+e);
	 * RequestContext.getCurrentInstance().execute("proceErr.show();"); } } }
	 */
	public static String getCurrentDateWithFormat() {
		Map<Integer, String> data = new HashMap<Integer, String>();
		for (int i = 0; i < 12; i++) {
			if (i < 9) {
				data.put(i, "0" + String.valueOf(i + 1));
			} else {
				data.put(i, String.valueOf(i + 1));
			}
		}
		String year = String.valueOf(new Date().getYear()).substring(1, 3);
		return "01/" + data.get(Calendar.getInstance().get(Calendar.MONTH))
				+ "/" + year;
	}

	public CashHeader saveCashHeader(BigDecimal documentSeriality) {
		CashHeader cashHeader = new CashHeader();
		CountryMaster appCountry = new CountryMaster();
		appCountry.setCountryId(sessionManage.getCountryId()); // country from
																// session based
																// on country
																// login
		cashHeader.setApplicationCountryId(appCountry);

		CompanyMaster cmpMaster = new CompanyMaster();
		cmpMaster.setCompanyId(sessionManage.getCompanyId()); // company from
																// session based
																// on company
																// login
		if (saveType.equalsIgnoreCase(Constants.UPDATE)) {
			cashHeader.setCashHeaderId(getCashHeaderId());
		}
		cashHeader.setCompanyId(cmpMaster);

		Document document = new Document();
		document.setDocumentID(specialCustomerDealRequestService
				.getDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER))); // 12 is
																		// Document
																		// id
																		// for
																		// Cash
																		// Deposit
		cashHeader.setDocumentId(document);

		cashHeader.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER));
		cashHeader.setFinancialYear(new BigDecimal(getFinaceYear()));
		cashHeader.setDocumentNo(documentSeriality);

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId())); // country Branch from Session
		cashHeader.setCountryBranchId(countryBranch);
		List<CountryBranch> branchList = cashTransferLToLService
				.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		cashHeader.setCountryBranchCode(branchList.get(0).getBranchId());
		cashHeader.setDocumentDate(new Date());
		cashHeader.setFromCashier(getFromCashier());
		cashHeader.setToCashier(getToCashierName());
		cashHeader.setToCountryBranchCode(getFromLocation());
		cashHeader.setIsActive(Constants.U);
		cashHeader.setCreatedBy(sessionManage.getUserName()); // user Name from
																// session
		cashHeader.setCreatedDate(new Date());

		return cashHeader;
	}

	/*
	 * public CashDetails
	 * saveCashDetail(CashTransferFromCounterToCounterDataTable
	 * cashTransferDTObj,CashHeader cashHeader,BigDecimal saveDocumentSerialID,
	 * int i){ CashDetails cashDetails=new CashDetails();
	 * cashDetails.setCashHeaderId(cashHeader); //to set cash header primary key
	 * 
	 * CountryMaster appCountry=new CountryMaster();
	 * appCountry.setCountryId(sessionManage.getCountryId()); //country from
	 * session based on country login
	 * cashDetails.setApplicationCountryId(appCountry);
	 * 
	 * CompanyMaster cmpMaster=new CompanyMaster();
	 * cmpMaster.setCompanyId(sessionManage.getCompanyId()); // company from
	 * session based on company login cashDetails.setCompanyId(cmpMaster);
	 * 
	 * Document document=new Document();
	 * document.setDocumentID(specialCustomerDealRequestService
	 * .getDocumentPk(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER))); //12 is Document
	 * id for Cash Deposit cashDetails.setDocumentId(document);
	 * 
	 * cashDetails.setDocumentCode(new
	 * BigDecimal(Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER)); // 25 is Document
	 * code for Cash Deposit
	 * 
	 * CurrencyMaster currencyMaster=new CurrencyMaster();
	 * currencyMaster.setCurrencyId(cashTransferDTObj.getCurencyId());
	 * cashDetails.setCurrencyId(currencyMaster);
	 * 
	 * UserFinancialYear financialYear=new UserFinancialYear();
	 * financialYear.setFinancialYearID(new BigDecimal(getFinaceYear()));
	 * cashDetails.setDocumentFinancialYear(financialYear);
	 * 
	 * cashDetails.setDocumentNo(saveDocumentSerialID);
	 * 
	 * CountryBranch countryBranch=new CountryBranch();
	 * countryBranch.setCountryBranchId(new
	 * BigDecimal(sessionManage.getBranchId())); // country Branch from Session
	 * cashDetails.setCountryBranchId(countryBranch); List<CountryBranch>
	 * branchList = cashTransferLToLService.getBranchName(new
	 * BigDecimal(sessionManage.getBranchId()));
	 * cashDetails.setCountryBranchCode(branchList.get(0).getBranchId());
	 * cashDetails.setDocumentDate(new Date());
	 * cashDetails.setTotalValue(cashTransferDTObj.getInputTotalTransfer());
	 * //to set total transfer amount which is be sent
	 * 
	 * cashDetails.setCreatedBy(sessionManage.getUserName()); // user Name from
	 * session cashDetails.setCreatedDate(new Date());
	 * 
	 * cashDetails.setIsActive(Constants.U);
	 * 
	 * cashDetails.setDocumentLineNo(new BigDecimal(i));
	 * 
	 * return cashDetails; }
	 */

	public ForeignCurrencyAdjust saveforeignCurrencyAdjust(
			CashHeader cashHeader, BigDecimal currencyId,
			BigDecimal adjustmentAmount, BigDecimal notesQuantity,
			BigDecimal denominationId, BigDecimal documentNumber,
			Date acc_Month, BigDecimal foreignCurrencyAdjustid) {
		lineNo = lineNo.add(BigDecimal.ONE);
		ForeignCurrencyAdjust foreignCurrencyAdjust = new ForeignCurrencyAdjust();
		if (saveType.equalsIgnoreCase(Constants.UPDATE)) {
			foreignCurrencyAdjust
					.setForeignCurrencyAdjustId(foreignCurrencyAdjustid);
		}
		CompanyMaster companyMaster = new CompanyMaster();
		companyMaster.setCompanyId(new BigDecimal(sessionManage
				.getSessionValue("companyId")));
		foreignCurrencyAdjust.setFsCompanyMaster(companyMaster);

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(new BigDecimal(sessionManage
				.getSessionValue("countryId")));
		foreignCurrencyAdjust.setFsCountryMaster(countryMaster);

		foreignCurrencyAdjust.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER));
		foreignCurrencyAdjust.setDocumentFinanceYear(new BigDecimal(
				getFinaceYear()));
		foreignCurrencyAdjust.setDocumentNo(documentNumber);
		foreignCurrencyAdjust.setDocumentDate(new Date());

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId()));
		foreignCurrencyAdjust.setCountryBranch(countryBranch);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(currencyId);
		foreignCurrencyAdjust.setFsCurrencyMaster(currencyMaster);

		foreignCurrencyAdjust.setAdjustmentAmount(adjustmentAmount);
		foreignCurrencyAdjust.setNotesQuantity(notesQuantity);

		CurrencyWiseDenomination denominationMaster = new CurrencyWiseDenomination();
		denominationMaster.setDenominationId(denominationId);
		foreignCurrencyAdjust.setFsDenominationId(denominationMaster);

		foreignCurrencyAdjust.setProgNumber(Constants.CASH_TRANSFER_C_TO_C);
		// foreignCurrencyAdjust.setTransactionType(Constants.CT);
		// Modified "T" to Transfer stock
		foreignCurrencyAdjust.setTransactionType(Constants.T);
		// foreignCurrencyAdjust.setTransactionType(Constants.F);

		foreignCurrencyAdjust.setDocumentStatus(Constants.Yes);
		foreignCurrencyAdjust.setOracleUser(sessionManage
				.getSessionValue("userName"));
		// foreignCurrencyAdjust.setStockUpdated(Constants.Yes);
		foreignCurrencyAdjust.setDocumentLineNumber(lineNo);
		foreignCurrencyAdjust.setAccountmmyyyy(acc_Month);

		foreignCurrencyAdjust.setDenaminationAmount(denominationMap
				.get(denominationId));
		foreignCurrencyAdjust.setCreatedDate(new Date());
		foreignCurrencyAdjust.setCreatedBy(sessionManage
				.getSessionValue("userName"));

		foreignCurrencyAdjust.setDocumentId(cashHeader.getDocumentId()
				.getDocumentID());

		foreignCurrencyAdjust.setCompanyCode(getCompanyCode());

		return foreignCurrencyAdjust;

	}

	// Cash Transfer Datatable display

	private List<CurrencyStockDataTable> currencyStockDataTableList = new CopyOnWriteArrayList<CurrencyStockDataTable>();
	private List<BigDecimal> currencyList = new CopyOnWriteArrayList<BigDecimal>();
	private boolean booNilCurrency = false;
	private BigDecimal cashHeaderId;
	private String updateDocumentNo;

	private String saveType;

	List<CashHeader> cashTxnCheckList = new CopyOnWriteArrayList<CashHeader>();

	public void getCurrencyDenominationFromStock() {

		try {

			setRenderDataTable(true);
			BigDecimal zero = BigDecimal.ZERO;
			currencyStockDataTableList.clear();
			CurrencyStockDataTable currencyStockDataTable = null;

			currencyList = cashTransferLToLService
					.getStockCurrency(getFromLocation());

			if (currencyList == null) {
				setWarningMessage("Stock not available for this branch");
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return;
			}
			for (BigDecimal currency : currencyList) {
				List<UserStockView> denominationStock = cashTransferLToLService
						.getCurrencyDenominationFromStock(sessionManage
								.getCountryId(), sessionManage.getUserName(),
								getFromLocation(),
								sessionManage.getCompanyId(), new BigDecimal(
										currency.intValue()));
				{
					currencyStockDataTable = new CurrencyStockDataTable();

					for (UserStockView element : denominationStock) {

						if (element.getDenominationAmount().compareTo(
								Constants.ONE_LAKH) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setOneLakh(BigDecimal.ZERO);
								currencyStockDataTable
										.setOneLakhs(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setOneLakh(stock);
								currencyStockDataTable
										.setOneLakhDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setOneLakhs(stock);
								} else {
									currencyStockDataTable
											.setOneLakhs(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setOneLakhAdj(currencyStockDataTable
												.getOneLakhs().multiply(
														Constants.ONE_LAKH));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiftyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftyThousands(BigDecimal.ZERO);
							} else {
								initZero(element);

								currencyStockDataTable.setFiftyThousand(stock);
								currencyStockDataTable
										.setFiftyThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setFiftyThousands(stock);
								} else {
									currencyStockDataTable
											.setFiftyThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiftyThousandAdj(currencyStockDataTable
												.getFiftyThousands()
												.multiply(
														Constants.FIFTY_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwentyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentyThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwentyThousand(stock);
								currencyStockDataTable
										.setTwentyThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setTwentyThousands(stock);
								} else {
									currencyStockDataTable
											.setTwentyThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwentyThousandAdj(currencyStockDataTable
												.getTwentyThousands()
												.multiply(
														Constants.TWENTY_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TEN_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTenThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTenThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);

								currencyStockDataTable.setTenThousand(stock);
								currencyStockDataTable
										.setTenThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setTenThousands(stock);
								} else {
									currencyStockDataTable
											.setTenThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTenThousandAdj(currencyStockDataTable
												.getTenThousands().multiply(
														Constants.TEN_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiveThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFiveThousand(stock);
								currencyStockDataTable
										.setFiveThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setFiveThousands(stock);
								} else {
									currencyStockDataTable
											.setFiveThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiveThousandAdj(currencyStockDataTable
												.getFiveThousands()
												.multiply(
														Constants.FIVE_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwoThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwoThousand(stock);
								currencyStockDataTable
										.setTwoThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setTwoThousands(stock);
								} else {
									currencyStockDataTable
											.setTwoThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoThousandAdj(currencyStockDataTable
												.getTwoThousands().multiply(
														Constants.TWO_THOUSAND));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setThousand(stock);
								currencyStockDataTable
										.setThousandDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setThousands(stock);
								} else {
									currencyStockDataTable
											.setThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setThousandAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.THOUSAND));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_HUNDRED) == 0) {
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable
										.setFiveHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFiveHundred(stock);
								currencyStockDataTable
										.setFiveHundredDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setFiveHundreds(stock);
								} else {
									currencyStockDataTable
											.setFiveHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiveHundredAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.FIVE_HUNDRED));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_HUNDRED) == 0) {
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable
										.setTwoHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwoHundred(stock);
								currencyStockDataTable
										.setTwoHundredDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setTwoHundreds(stock);
								} else {
									currencyStockDataTable
											.setTwoHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoHundredAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.TWO_HUNDRED));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.HUNDRED) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setHundred(stock);
								currencyStockDataTable
										.setHundredDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setHundreds(stock);
								} else {
									currencyStockDataTable
											.setHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setHundredAdj(currencyStockDataTable
												.getHundreds().multiply(
														Constants.HUNDRED));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setFifty(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftys(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFifty(stock);
								currencyStockDataTable.setFiftyDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setFiftys(stock);
								} else {
									currencyStockDataTable
											.setFiftys(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiftyAdj(currencyStockDataTable
												.getFiftys().multiply(
														Constants.FIFTY));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwenty(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentys(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwenty(stock);
								currencyStockDataTable.setTwentyDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setTwentys(stock);
								} else {
									currencyStockDataTable
											.setTwentys(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwentyAdj(currencyStockDataTable
												.getTwentys().multiply(
														Constants.TWENTY));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TEN) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTen(BigDecimal.ZERO);
								currencyStockDataTable.setTens(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTen(stock);
								currencyStockDataTable.setTenDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setTens(stock);
								} else {
									currencyStockDataTable
											.setTens(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTenAdj(currencyStockDataTable
												.getTens().multiply(
														Constants.TEN));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFive(stock);
								currencyStockDataTable.setFiveDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setFives(stock);
								} else {
									currencyStockDataTable
											.setFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiveAdj(currencyStockDataTable
												.getFives().multiply(
														Constants.FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								new BigDecimal(Constants.TWO)) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTwo(BigDecimal.ZERO);
								currencyStockDataTable.setTwos(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwo(stock);
								currencyStockDataTable.setTwoDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setTwos(stock);
								} else {
									currencyStockDataTable
											.setTwos(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoAdj(currencyStockDataTable
												.getTwos().multiply(
														new BigDecimal(
																Constants.TWO)));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setOne(BigDecimal.ZERO);
								currencyStockDataTable.setOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setOne(stock);
								currencyStockDataTable.setOneDenomId(element
										.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setOnes(stock);
								} else {
									currencyStockDataTable
											.setOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setOneAdj(currencyStockDataTable
												.getOnes().multiply(
														Constants.ONE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointFive(stock);
								currencyStockDataTable
										.setPointFiveDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setPointFives(stock);
								} else {
									currencyStockDataTable
											.setPointFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointFiveAdj(currencyStockDataTable
												.getPointFives().multiply(
														Constants.POINT_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_TWO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointTwoFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointTwoFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointTwoFive(stock);
								currencyStockDataTable
										.setPointTwoFiveDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointTwoFives(stock);
								} else {
									currencyStockDataTable
											.setPointTwoFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointTwoFiveAdj(currencyStockDataTable
												.getPointTwoFives()
												.multiply(
														Constants.POINT_TWO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointOne(stock);
								currencyStockDataTable
										.setPointOneDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable.setPointOnes(stock);
								} else {
									currencyStockDataTable
											.setPointOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointOneAdj(currencyStockDataTable
												.getPointOnes().multiply(
														Constants.POINT_ONE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroFive(stock);
								currencyStockDataTable
										.setPointZeroFiveDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointZeroFives(stock);
								} else {
									currencyStockDataTable
											.setPointZeroFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroFiveAdj(currencyStockDataTable
												.getPointZeroFives()
												.multiply(
														Constants.POINT_ZERO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_TWO) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroTwo(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroTwos(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroTwo(stock);
								currencyStockDataTable
										.setPointZeroTwoDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointZeroTwos(stock);
								} else {
									currencyStockDataTable
											.setPointZeroTwos(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroTwoAdj(currencyStockDataTable
												.getPointZeroTwos()
												.multiply(
														Constants.POINT_ZERO_TWO));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroOne(stock);
								currencyStockDataTable
										.setPointZeroOneDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointZeroOnes(stock);
								} else {
									currencyStockDataTable
											.setPointZeroOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroOneAdj(currencyStockDataTable
												.getPointZeroOnes()
												.multiply(
														Constants.POINT_ZERO_ONE));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable
										.setPointZeroZeroFive(stock);
								currencyStockDataTable
										.setPointZeroZeroFiveDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointZeroZeroFives(stock);
								} else {
									currencyStockDataTable
											.setPointZeroZeroFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroZeroFiveAdj(currencyStockDataTable
												.getPointZeroZeroFives()
												.multiply(
														Constants.POINT_ZERO_ZERO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable
										.setPointZeroZeroOne(stock);
								currencyStockDataTable
										.setPointZeroZeroOneDenomId(element
												.getDenominationId());
								if (getTransferOption().equals("ALL")) {
									currencyStockDataTable
											.setPointZeroZeroOnes(stock);
								} else {
									currencyStockDataTable
											.setPointZeroZeroOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroZeroOneAdj(currencyStockDataTable
												.getPointZeroZeroOnes()
												.multiply(
														Constants.POINT_ZERO_ZERO_ONE));
							}
						}

						currencyStockDataTable.setCurrencyName(element
								.getCurrencyName());
						currencyStockDataTable.setCurrencyId(element
								.getCurrencyId());

						// Calculate currency
						getCalculateHeaderCurrency(currencyStockDataTable);
						getReadOnly(currencyStockDataTable);

					}
				}
				currencyStockDataTableList.add(currencyStockDataTable);
			}
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"../cashtransfer/CashTransferFromCounterToCounter.xhtml");
		} catch (Exception e) {
			if (e.getMessage() != null) {

				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// Calculate header currency total
	public void getCalculateHeaderCurrency(
			CurrencyStockDataTable currencyStockDataTable) {

		try {

			BigDecimal headerTotal = BigDecimal.ZERO;
			BigDecimal modifySubTotal = BigDecimal.ZERO;

			if (currencyStockDataTable.getOneLakh() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getOneLakh().multiply(Constants.ONE_LAKH));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOneLakhs().multiply(Constants.ONE_LAKH));
			}
			if (currencyStockDataTable.getFiftyThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiftyThousand().multiply(Constants.FIFTY_THOUSAND));
				modifySubTotal = modifySubTotal
						.add(currencyStockDataTable.getFiftyThousands()
								.multiply(Constants.FIFTY_THOUSAND));
			}

			if (currencyStockDataTable.getTwentyThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwentyThousand()
						.multiply(Constants.TWENTY_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentyThousands().multiply(
								Constants.TWENTY_THOUSAND));
			}

			if (currencyStockDataTable.getTenThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTenThousand().multiply(Constants.TEN_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTenThousands().multiply(Constants.TEN_THOUSAND));
			}
			if (currencyStockDataTable.getFiveThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiveThousand().multiply(Constants.FIVE_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveThousands().multiply(Constants.FIVE_THOUSAND));
			}
			if (currencyStockDataTable.getTwoThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwoThousand().multiply(Constants.TWO_THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoThousands().multiply(Constants.TWO_THOUSAND));
			}
			if (currencyStockDataTable.getThousand() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getThousand().multiply(Constants.THOUSAND));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getThousands().multiply(Constants.THOUSAND));
			}
			if (currencyStockDataTable.getFiveHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getFiveHundred().multiply(Constants.FIVE_HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveHundreds().multiply(Constants.FIVE_HUNDRED));
			}
			if (currencyStockDataTable.getTwoHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwoHundred().multiply(Constants.TWO_HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoHundreds().multiply(Constants.TWO_HUNDRED));
				currencyStockDataTable.setTwoHundredAdj(modifySubTotal);
			}
			if (currencyStockDataTable.getHundred() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getHundred().multiply(Constants.HUNDRED));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getHundreds().multiply(Constants.HUNDRED));
			}
			if (currencyStockDataTable.getFifty() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getFifty()
						.multiply(Constants.FIFTY));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftys().multiply(Constants.FIFTY));
			}
			if (currencyStockDataTable.getTwenty() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getTwenty().multiply(Constants.TWENTY));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentys().multiply(Constants.TWENTY));
			}
			if (currencyStockDataTable.getTen() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getTen()
						.multiply(Constants.TEN));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTens().multiply(Constants.TEN));
			}

			if (currencyStockDataTable.getFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getFive()
						.multiply(Constants.FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFives().multiply(Constants.FIVE));
			}

			if (currencyStockDataTable.getTwo() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getTwo()
						.multiply(new BigDecimal(Constants.TWO)));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwos().multiply(new BigDecimal(Constants.TWO)));
			}
			if (currencyStockDataTable.getOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable.getOne()
						.multiply(Constants.ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOnes().multiply(Constants.ONE));
			}
			if (currencyStockDataTable.getPointFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointFive().multiply(Constants.POINT_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointFives().multiply(Constants.POINT_FIVE));
			}
			if (currencyStockDataTable.getPointTwoFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointTwoFive().multiply(Constants.POINT_TWO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointTwoFives().multiply(Constants.POINT_TWO_FIVE));
			}
			if (currencyStockDataTable.getPointOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointOne().multiply(Constants.POINT_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointOnes().multiply(Constants.POINT_ONE));
			}
			if (currencyStockDataTable.getPointZeroFive() != null) {
				headerTotal = headerTotal
						.add(currencyStockDataTable.getPointZeroFive()
								.multiply(Constants.POINT_ZERO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroFives()
						.multiply(Constants.POINT_ZERO_FIVE));
			}
			if (currencyStockDataTable.getPointZeroTwo() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointZeroTwo().multiply(Constants.POINT_ZERO_TWO));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroTwos().multiply(Constants.POINT_ZERO_TWO));
			}
			if (currencyStockDataTable.getPointZeroOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointZeroOne().multiply(Constants.POINT_ZERO_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroOnes().multiply(Constants.POINT_ZERO_ONE));
			}
			if (currencyStockDataTable.getPointZeroZeroFive() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointZeroZeroFive().multiply(
								Constants.POINT_ZERO_ZERO_FIVE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroFives().multiply(
								Constants.POINT_ZERO_ZERO_FIVE));
			}
			if (currencyStockDataTable.getPointZeroZeroOne() != null) {
				headerTotal = headerTotal.add(currencyStockDataTable
						.getPointZeroZeroOne().multiply(
								Constants.POINT_ZERO_ZERO_ONE));
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroOnes().multiply(
								Constants.POINT_ZERO_ZERO_ONE));
			}
			currencyStockDataTable.setRowTotal(headerTotal);
			currencyStockDataTable.setModRowTotal(modifySubTotal);
			getReadOnly(currencyStockDataTable);

		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	// Calculate modify currency total
	public void getModifiedCurrencyValues(
			CurrencyStockDataTable currencyStockDataTable) {

		try {
			BigDecimal modifySubTotal = BigDecimal.ZERO;

			if (currencyStockDataTable.getOneLakhs() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOneLakhs().multiply(Constants.ONE_LAKH));
			}
			if (currencyStockDataTable.getFiftyThousands() != null) {
				modifySubTotal = modifySubTotal
						.add(currencyStockDataTable.getFiftyThousands()
								.multiply(Constants.FIFTY_THOUSAND));
			}
			if (currencyStockDataTable.getTwentyThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentyThousands().multiply(
								Constants.TWENTY_THOUSAND));
			}
			if (currencyStockDataTable.getTenThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTenThousands().multiply(Constants.TEN_THOUSAND));
			}
			if (currencyStockDataTable.getFiveThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveThousands().multiply(Constants.FIVE_THOUSAND));
			}
			if (currencyStockDataTable.getTwoThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoThousands().multiply(Constants.TWO_THOUSAND));
			}

			if (currencyStockDataTable.getThousands() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getThousands().multiply(Constants.THOUSAND));
			}

			if (currencyStockDataTable.getFiveHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiveHundreds().multiply(Constants.FIVE_HUNDRED));
			}
			if (currencyStockDataTable.getTwoHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwoHundreds().multiply(Constants.TWO_HUNDRED));
			}
			if (currencyStockDataTable.getHundreds() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getHundreds().multiply(Constants.HUNDRED));
			}
			if (currencyStockDataTable.getFiftys() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFiftys().multiply(Constants.FIFTY));
			}
			if (currencyStockDataTable.getTwentys() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwentys().multiply(Constants.TWENTY));
			}
			if (currencyStockDataTable.getTens() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTens().multiply(Constants.TEN));
			}
			if (currencyStockDataTable.getFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getFives().multiply(Constants.FIVE));
			}

			if (currencyStockDataTable.getTwos() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getTwos().multiply(new BigDecimal(Constants.TWO)));
			}
			if (currencyStockDataTable.getOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getOnes().multiply(Constants.ONE));
			}
			if (currencyStockDataTable.getPointFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointFives().multiply(Constants.POINT_FIVE));
			}
			if (currencyStockDataTable.getPointTwoFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointTwoFives().multiply(Constants.POINT_TWO_FIVE));
			}
			if (currencyStockDataTable.getPointOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointOnes().multiply(Constants.POINT_ONE));

			}
			if (currencyStockDataTable.getPointZeroFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroFives()
						.multiply(Constants.POINT_ZERO_FIVE));
			}

			if (currencyStockDataTable.getPointZeroTwo() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroTwos().multiply(Constants.POINT_ZERO_TWO));
			}
			if (currencyStockDataTable.getPointZeroOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroOnes().multiply(Constants.POINT_ZERO_ONE));
			}
			if (currencyStockDataTable.getPointZeroZeroFives() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroFives().multiply(
								Constants.POINT_ZERO_ZERO_FIVE));
			}

			if (currencyStockDataTable.getPointZeroZeroOnes() != null) {
				modifySubTotal = modifySubTotal.add(currencyStockDataTable
						.getPointZeroZeroOnes().multiply(
								Constants.POINT_ZERO_ZERO_ONE));
			}

			currencyStockDataTable.setModRowTotal(modifySubTotal);

		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public void saveInfoToDb() {
		try {
			// String documentSerilId = getDocumentSerialID(Constants.U);
			String documentSerilId = null;
			List<ForeignCurrencyAdjust> currencyAdjustListUpdate = null;
			if (saveType.equalsIgnoreCase(Constants.INSERT)) {
				documentSerilId = cashTransferLToLService.getNextToken();
			} else {
				documentSerilId = getUpdateDocumentNo();
			}

			if (!documentSerilId.trim().equals("")) {
				BigDecimal saveDocumentSerialID = new BigDecimal(
						documentSerilId);

				List<CompanyMasterDesc> companyCode = generalService
						.getCompanyList(sessionManage.getCompanyId(),
								sessionManage.getLanguageId());
				if (companyCode != null && !companyCode.isEmpty()
						&& companyCode.get(0).getFsCompanyMaster() != null) {
					BigDecimal companyCodeValue = companyCode.get(0)
							.getFsCompanyMaster().getCompanyCode();
					setCompanyCode(companyCodeValue);
				}

				lineNo = BigDecimal.ZERO;
				SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
						"dd/MM/yyyy");
				Date acc_Month = null;

				try {
					acc_Month = DATE_FORMAT.parse(getCurrentDateWithFormat());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				for (CurrencyStockDataTable cashTransferDTObj1 : currencyStockDataTableList) {

					// save Cash Header information
					CashHeader cashHeader = saveCashHeader(saveDocumentSerialID);
					int i = 1;
					int j = 1;

					for (CurrencyStockDataTable cashTransferDTObj : currencyStockDataTableList) {

						// save Cash Details information
						if ((cashTransferDTObj.getModRowTotal() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getModRowTotal())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								cashDetailList.add(saveCashDetail(
										cashTransferDTObj, cashHeader,
										saveDocumentSerialID, j, null));
							} else {
								List<CashDetails> cashDetailsList = cashTransferLToLService
										.getCashDetailsById(cashHeader
												.getCashHeaderId());
								cashDetailList.add(saveCashDetail(
										cashTransferDTObj, cashHeader,
										saveDocumentSerialID, j,
										cashDetailsList.get(j - 1)
												.getCashDetailsId()));

							}
							j++;
						}

						// save Foreign Currency Adjust information
						currencyAdjustListUpdate = new CopyOnWriteArrayList<ForeignCurrencyAdjust>();
						if (getSaveType().equalsIgnoreCase(Constants.UPDATE)) {
							currencyAdjustListUpdate = cashTransferLToLService
									.getForeignCurrencyAdjust(saveDocumentSerialID);
						}
						if ((cashTransferDTObj.getOneLakhs() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getOneLakhs())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getOneLakhAdj(),
												cashTransferDTObj.getOneLakhs(),
												cashTransferDTObj
														.getThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getOneLakhAdj(),
												cashTransferDTObj.getOneLakhs(),
												cashTransferDTObj
														.getThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getFiftyThousands() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getFiftyThousands())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getThousandAdj(),
												cashTransferDTObj
														.getFiftyThousands(),
												cashTransferDTObj
														.getFiftyThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getThousandAdj(),
												cashTransferDTObj
														.getFiftyThousands(),
												cashTransferDTObj
														.getFiftyThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getTenThousands() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getTenThousands())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTenThousandAdj(),
												cashTransferDTObj
														.getTenThousands(),
												cashTransferDTObj
														.getTenThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTenThousandAdj(),
												cashTransferDTObj
														.getTenThousands(),
												cashTransferDTObj
														.getTenThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getFiveThousands() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getFiveThousands())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getFiveThousandAdj(),
												cashTransferDTObj
														.getFiveThousands(),
												cashTransferDTObj
														.getFiveThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getFiveThousandAdj(),
												cashTransferDTObj
														.getFiveThousands(),
												cashTransferDTObj
														.getFiveThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getTwoThousands() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getTwoThousands())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwoThousandAdj(),
												cashTransferDTObj
														.getTwoThousands(),
												cashTransferDTObj
														.getTwoThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwoThousandAdj(),
												cashTransferDTObj
														.getTwoThousands(),
												cashTransferDTObj
														.getTwoThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}

						if ((cashTransferDTObj.getThousands() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getThousands())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getThousandAdj(),
												cashTransferDTObj
														.getThousands(),
												cashTransferDTObj
														.getThousandDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getThousandAdj(),
												cashTransferDTObj
														.getThousands(),
												cashTransferDTObj
														.getThousandDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getFiveHundreds() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getFiveHundreds())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getFiveHundredAdj(),
												cashTransferDTObj
														.getFiveHundreds(),
												cashTransferDTObj
														.getFiveHundredDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getFiveHundredAdj(),
												cashTransferDTObj
														.getFiveHundreds(),
												cashTransferDTObj
														.getFiveHundredDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getTwoHundreds() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getTwoHundreds())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwoHundredAdj(),
												cashTransferDTObj
														.getTwoHundreds(),
												cashTransferDTObj
														.getTwoHundredDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwoHundredAdj(),
												cashTransferDTObj
														.getTwoHundreds(),
												cashTransferDTObj
														.getTwoHundredDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getHundreds() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getHundreds())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getHundredAdj(),
												cashTransferDTObj.getHundreds(),
												cashTransferDTObj
														.getHundredDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getHundredAdj(),
												cashTransferDTObj.getHundreds(),
												cashTransferDTObj
														.getHundredDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getFiftys() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getFiftys())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getFiftyAdj(),
												cashTransferDTObj.getFiftys(),
												cashTransferDTObj
														.getFiftyDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getFiftyAdj(),
												cashTransferDTObj.getFiftys(),
												cashTransferDTObj
														.getFiftyDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getTwentys() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getTwentys())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwentyAdj(),
												cashTransferDTObj.getTwentys(),
												cashTransferDTObj
														.getTwentyDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getTwentyAdj(),
												cashTransferDTObj.getTwentys(),
												cashTransferDTObj
														.getTwentyDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getTens() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getTens())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getTenAdj(),
												cashTransferDTObj.getTens(),
												cashTransferDTObj
														.getTenDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getTenAdj(),
												cashTransferDTObj.getTens(),
												cashTransferDTObj
														.getTenDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getFives() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getFives())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getFiveAdj(),
												cashTransferDTObj.getFives(),
												cashTransferDTObj
														.getFiveDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getFiveAdj(),
												cashTransferDTObj.getFives(),
												cashTransferDTObj
														.getFiveDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getOnes() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getOnes())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getOneAdj(),
												cashTransferDTObj.getOnes(),
												cashTransferDTObj
														.getOneDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj.getOneAdj(),
												cashTransferDTObj.getOnes(),
												cashTransferDTObj
														.getOneDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointFives() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointFives())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointFiveAdj(),
												cashTransferDTObj
														.getPointFives(),
												cashTransferDTObj
														.getPointFiveDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointFiveAdj(),
												cashTransferDTObj
														.getPointFives(),
												cashTransferDTObj
														.getPointFiveDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointTwoFives() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointTwoFives())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointTwoFiveAdj(),
												cashTransferDTObj
														.getPointTwoFives(),
												cashTransferDTObj
														.getPointTwoFiveDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointTwoFiveAdj(),
												cashTransferDTObj
														.getPointTwoFives(),
												cashTransferDTObj
														.getPointTwoFiveDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointOnes() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointOnes())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader, cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointOneAdj(),
												cashTransferDTObj
														.getPointOnes(),
												cashTransferDTObj
														.getPointOneDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointOneAdj(),
												cashTransferDTObj
														.getPointOnes(),
												cashTransferDTObj
														.getPointOneDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointZeroFives() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointZeroFives())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroFiveAdj(),
												cashTransferDTObj
														.getPointZeroFives(),
												cashTransferDTObj
														.getPointZeroFiveDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroFiveAdj(),
												cashTransferDTObj
														.getPointZeroFives(),
												cashTransferDTObj
														.getPointZeroFiveDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}

						if ((cashTransferDTObj.getPointZeroTwos() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointZeroTwos())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroTwoAdj(),
												cashTransferDTObj
														.getPointZeroTwos(),
												cashTransferDTObj
														.getPointZeroTwoDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroTwoAdj(),
												cashTransferDTObj
														.getPointZeroTwos(),
												cashTransferDTObj
														.getPointZeroTwoDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointZeroOnes() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointZeroOnes())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroOneAdj(),
												cashTransferDTObj
														.getPointZeroOnes(),
												cashTransferDTObj
														.getPointZeroOneDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroOneAdj(),
												cashTransferDTObj
														.getPointZeroOnes(),
												cashTransferDTObj
														.getPointZeroOneDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointZeroZeroFives() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointZeroZeroFives())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroZeroFiveAdj(),
												cashTransferDTObj
														.getPointZeroZeroFives(),
												cashTransferDTObj
														.getPointZeroZeroFiveDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroZeroFiveAdj(),
												cashTransferDTObj
														.getPointZeroZeroFives(),
												cashTransferDTObj
														.getPointZeroZeroFiveDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
						}
						if ((cashTransferDTObj.getPointZeroZeroOnes() == null ? BigDecimal.ZERO
								: cashTransferDTObj.getPointZeroZeroOnes())
								.compareTo(BigDecimal.ZERO) != 0) {
							if (getSaveType()
									.equalsIgnoreCase(Constants.INSERT)) {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroZeroOneAdj(),
												cashTransferDTObj
														.getPointZeroZeroOnes(),
												cashTransferDTObj
														.getPointZeroZeroOneDenomId(),
												saveDocumentSerialID,
												acc_Month, null));
							} else {
								foreignCurrencyAdjustList
										.add(saveforeignCurrencyAdjust(
												cashHeader,
												cashTransferDTObj
														.getCurrencyId(),
												cashTransferDTObj
														.getPointZeroZeroOneAdj(),
												cashTransferDTObj
														.getPointZeroZeroOnes(),
												cashTransferDTObj
														.getPointZeroZeroOneDenomId(),
												saveDocumentSerialID,
												acc_Month,
												currencyAdjustListUpdate
														.get(i - 1)
														.getForeignCurrencyAdjustId()));
							}
							i++;
						}
					}
					try {
						cashTransferLToLService.saveAllRecords(cashHeader,
								cashDetailList, foreignCurrencyAdjustList);

						// call Procedure EX_P_POPULATE_CASH_TRANSFER
						String errmsg = null;
						if (getSaveType().equalsIgnoreCase(Constants.INSERT)) {
							errmsg = cashTransferLToLService
									.procedurePopulateCashTransfer(
											sessionManage.getCountryId(),
											sessionManage.getCompanyId(),
											cashHeader.getDocumentCode(),
											cashHeader.getFinancialYear(),
											cashHeader.getDocumentNo());
						}

						if (errmsg != null) {
							setWarningMessage("Exception : " + errmsg);
							RequestContext.getCurrentInstance().execute(
									"proceErr.show();");
							break;
						} else {
							if (saveType.equalsIgnoreCase(Constants.INSERT)) {
								RequestContext.getCurrentInstance().execute(
										"success.show();");
							} else {
								RequestContext.getCurrentInstance().execute(
										"successmod.show();");
							}
							break;
						}

					} catch (Exception e) {
						log.error("Error Occured While Saving :"
								+ e.getMessage());
						setWarningMessage("Exception : " + e.getMessage());
						RequestContext.getCurrentInstance().execute(
								"proceErr.show();");
						break;
					}
				}
			} else {
				setWarningMessage("Document Seriality Not Available ");
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public CashDetails saveCashDetail(CurrencyStockDataTable cashTransferDTObj,
			CashHeader cashHeader, BigDecimal saveDocumentSerialID, int i,
			BigDecimal cashdetailId) {
		CashDetails cashDetails = new CashDetails();
		cashDetails.setCashHeaderId(cashHeader); // to set cash header primary
													// key
		if (saveType.equalsIgnoreCase(Constants.UPDATE)) {
			cashDetails.setCashDetailsId(cashdetailId);
		}

		CountryMaster appCountry = new CountryMaster();
		appCountry.setCountryId(sessionManage.getCountryId()); // country from
																// session based
																// on country
																// login
		cashDetails.setApplicationCountryId(appCountry);

		CompanyMaster cmpMaster = new CompanyMaster();
		cmpMaster.setCompanyId(sessionManage.getCompanyId()); // company from
																// session based
																// on company
																// login
		cashDetails.setCompanyId(cmpMaster);

		Document document = new Document();
		document.setDocumentID(specialCustomerDealRequestService
				.getDocumentPk(new BigDecimal(
						Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER))); // 12 is
																		// Document
																		// id
																		// for
																		// Cash
																		// Deposit
		cashDetails.setDocumentId(document);

		cashDetails.setDocumentCode(new BigDecimal(
				Constants.DOCUMENT_CODE_FOR_CASH_TRANSFER)); // 25 is Document
																// code for Cash
																// Deposit

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(cashTransferDTObj.getCurrencyId());
		cashDetails.setCurrencyId(currencyMaster);

		UserFinancialYear financialYear = new UserFinancialYear();
		financialYear.setFinancialYearID(new BigDecimal(getFinaceYear()));
		cashDetails.setDocumentFinancialYear(financialYear);

		cashDetails.setDocumentNo(saveDocumentSerialID);

		CountryBranch countryBranch = new CountryBranch();
		countryBranch.setCountryBranchId(new BigDecimal(sessionManage
				.getBranchId())); // country Branch from Session
		cashDetails.setCountryBranchId(countryBranch);
		List<CountryBranch> branchList = cashTransferLToLService
				.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		cashDetails.setCountryBranchCode(branchList.get(0).getBranchId());
		cashDetails.setDocumentDate(new Date());
		cashDetails.setTotalValue(cashTransferDTObj.getModRowTotal()); // to set
																		// total
																		// transfer
																		// amount
																		// which
																		// is be
																		// sent

		cashDetails.setCreatedBy(sessionManage.getUserName()); // user Name from
																// session
		cashDetails.setCreatedDate(new Date());

		cashDetails.setIsActive(Constants.U);

		cashDetails.setDocumentLineNo(new BigDecimal(i));

		return cashDetails;
	}

	public void getReadOnly(CurrencyStockDataTable currencyStockDataTable) {
		if (saveType.equalsIgnoreCase(Constants.UPDATE)) {
			setTransferOption("SPECIFIC");
		}
		if (getTransferOption().equals("ALL")) {
			currencyStockDataTable.setBooOneLakhs(true);
			currencyStockDataTable.setBooFiftyThousands(true);
			currencyStockDataTable.setBooTwentyThousands(true);
			currencyStockDataTable.setBooTenThousands(true);
			currencyStockDataTable.setBooFiveThousands(true);
			currencyStockDataTable.setBooTwoThousands(true);
			currencyStockDataTable.setBooThousands(true);
			currencyStockDataTable.setBooFiveHundreds(true);
			currencyStockDataTable.setBooTwoHundreds(true);
			currencyStockDataTable.setBooHundreds(true);
			currencyStockDataTable.setBooFiftys(true);
			currencyStockDataTable.setBooTwentys(true);
			currencyStockDataTable.setBooTens(true);
			currencyStockDataTable.setBooFives(true);
			currencyStockDataTable.setBooTwos(true);
			currencyStockDataTable.setBooOnes(true);
			currencyStockDataTable.setBooPointFives(true);
			currencyStockDataTable.setBooPointTwoFives(true);
			currencyStockDataTable.setBooPointOnes(true);
			currencyStockDataTable.setBooPointZeroFives(true);
			currencyStockDataTable.setBooPointZeroTwos(true);
			currencyStockDataTable.setBooPointZeroOnes(true);
			currencyStockDataTable.setBooPointZeroZeroFives(true);
			currencyStockDataTable.setBooPointZeroZeroOnes(true);
		} else {
			if (currencyStockDataTable.getOneLakhs() != null) {
				currencyStockDataTable.setBooOneLakhs(false);
			} else {
				currencyStockDataTable.setBooOneLakhs(true);
			}
			if (currencyStockDataTable.getFiftyThousands() != null) {
				currencyStockDataTable.setBooFiftyThousands(false);
			} else {
				currencyStockDataTable.setBooFiftyThousands(true);
			}
			if (currencyStockDataTable.getTwentyThousands() != null) {
				currencyStockDataTable.setBooTwentyThousands(false);
			} else {
				currencyStockDataTable.setBooTwentyThousands(true);
			}
			if (currencyStockDataTable.getTenThousands() != null) {
				currencyStockDataTable.setBooTenThousands(false);
			} else {
				currencyStockDataTable.setBooTenThousands(true);
			}
			if (currencyStockDataTable.getFiveThousands() != null) {
				currencyStockDataTable.setBooFiveThousands(false);
			} else {
				currencyStockDataTable.setBooFiveThousands(true);
			}
			if (currencyStockDataTable.getTwoThousands() != null) {
				currencyStockDataTable.setBooTwoThousands(false);
			} else {
				currencyStockDataTable.setBooTwoThousands(true);
			}
			if (currencyStockDataTable.getThousands() != null) {
				currencyStockDataTable.setBooThousands(false);
			} else {
				currencyStockDataTable.setBooThousands(true);
			}
			if (currencyStockDataTable.getFiveHundreds() != null) {
				currencyStockDataTable.setBooFiveHundreds(false);
			} else {
				currencyStockDataTable.setBooFiveHundreds(true);
			}
			if (currencyStockDataTable.getTwoHundreds() != null) {
				currencyStockDataTable.setBooTwoHundreds(false);
			} else {
				currencyStockDataTable.setBooTwoHundreds(true);
			}
			if (currencyStockDataTable.getHundreds() != null) {
				currencyStockDataTable.setBooHundreds(false);
			} else {
				currencyStockDataTable.setBooHundreds(true);
			}
			if (currencyStockDataTable.getFiftys() != null) {
				currencyStockDataTable.setBooFiftys(false);
			} else {
				currencyStockDataTable.setBooFiftys(true);
			}
			if (currencyStockDataTable.getTwentys() != null) {
				currencyStockDataTable.setBooTwentys(false);
			} else {
				currencyStockDataTable.setBooTwentys(true);
			}
			if (currencyStockDataTable.getTens() != null) {
				currencyStockDataTable.setBooTens(false);
			} else {
				currencyStockDataTable.setBooTens(true);
			}
			if (currencyStockDataTable.getFives() != null) {
				currencyStockDataTable.setBooFives(false);
			} else {
				currencyStockDataTable.setBooFives(true);
			}
			if (currencyStockDataTable.getTwos() != null) {
				currencyStockDataTable.setBooTwos(false);
			} else {
				currencyStockDataTable.setBooTwos(true);
			}
			if (currencyStockDataTable.getOnes() != null) {
				currencyStockDataTable.setBooOnes(false);
			} else {
				currencyStockDataTable.setBooOnes(true);
			}
			if (currencyStockDataTable.getPointFives() != null) {
				currencyStockDataTable.setBooPointFives(false);
			} else {
				currencyStockDataTable.setBooPointFives(true);
			}
			if (currencyStockDataTable.getPointTwoFives() != null) {
				currencyStockDataTable.setBooPointTwoFives(false);
			} else {
				currencyStockDataTable.setBooPointTwoFives(true);
			}
			if (currencyStockDataTable.getPointOnes() != null) {
				currencyStockDataTable.setBooPointOnes(false);
			} else {
				currencyStockDataTable.setBooPointOnes(true);
			}
			if (currencyStockDataTable.getPointZeroFives() != null) {
				currencyStockDataTable.setBooPointZeroFives(false);
			} else {
				currencyStockDataTable.setBooPointZeroFives(true);
			}
			if (currencyStockDataTable.getPointZeroTwos() != null) {
				currencyStockDataTable.setBooPointZeroTwos(false);
			} else {
				currencyStockDataTable.setBooPointZeroTwos(true);
			}
			if (currencyStockDataTable.getPointZeroOnes() != null) {
				currencyStockDataTable.setBooPointZeroOnes(false);
			} else {
				currencyStockDataTable.setBooPointZeroOnes(true);
			}
			if (currencyStockDataTable.getPointZeroZeroFives() != null) {
				currencyStockDataTable.setBooPointZeroZeroFives(false);
			} else {
				currencyStockDataTable.setBooPointZeroZeroFives(true);
			}
			if (currencyStockDataTable.getPointZeroZeroOnes() != null) {
				currencyStockDataTable.setBooPointZeroZeroOnes(false);
			} else {
				currencyStockDataTable.setBooPointZeroZeroOnes(true);
			}
		}

	}

	public List<CurrencyStockDataTable> getCurrencyStockDataTableList() {
		return currencyStockDataTableList;
	}

	public void setCurrencyStockDataTableList(
			List<CurrencyStockDataTable> currencyStockDataTableList) {
		this.currencyStockDataTableList = currencyStockDataTableList;
	}

	public boolean isBooNilCurrency() {
		return booNilCurrency;
	}

	public void setBooNilCurrency(boolean booNilCurrency) {
		this.booNilCurrency = booNilCurrency;
	}

	public void cashTransfer() {
		List<CashHeader> isCashTxnCheck = null;
		isCashTxnCheck = new ArrayList<CashHeader>();
		List<CountryBranch> branchList = cashTransferLToLService
				.getBranchName(new BigDecimal(sessionManage.getBranchId()));
		isCashTxnCheck = cashTransferLToLService.isCheckCashTransfer(
				sessionManage.getCountryId(), sessionManage.getCompanyId(),
				getFromLocation(), new Date(), getFromCashier(),
				getToCashierName(), getFromLocation());
		setCashTxnCheckList(isCashTxnCheck);
		if (isCashTxnCheck != null) {
			setSaveType(Constants.UPDATE);
			setCashHeaderId(isCashTxnCheck.get(0).getCashHeaderId());
			RequestContext.getCurrentInstance().execute("updatedlg.show();");
			return;
		} else {
			setSaveType(Constants.INSERT);
			getCurrencyDenominationFromStock();
		}
	}

	public void getCurrencyDenominationForUpdate() {

		try {

			setRenderDataTable(true);
			BigDecimal zero = BigDecimal.ZERO;
			currencyStockDataTableList.clear();
			CurrencyStockDataTable currencyStockDataTable = null;
			List<CashHeader> cashList = new CopyOnWriteArrayList<CashHeader>();
			List<ForeignCurrencyAdjust> currencyAdjustList = new CopyOnWriteArrayList<ForeignCurrencyAdjust>();

			if (getCashTxnCheckList() != null) {
				cashList = getCashTxnCheckList();
				currencyAdjustList = cashTransferLToLService
						.getForeignCurrencyAdjust(cashList.get(0)
								.getDocumentNo());
				setUpdateDocumentNo(cashList.get(0).getDocumentNo()
						.toPlainString());
			}
			currencyList = cashTransferLToLService.getUpdateCurrency(cashList
					.get(0).getDocumentNo());
			if (currencyList == null) {
				setWarningMessage("Stock not available for this branch");
				RequestContext.getCurrentInstance().execute("proceErr.show();");
				return;
			}
			for (BigDecimal currency : currencyList) {
				List<UserStockView> denominationStock = cashTransferLToLService
						.getCurrencyDenominationFromStock(sessionManage
								.getCountryId(), sessionManage.getUserName(),
								getFromLocation(),
								sessionManage.getCompanyId(), new BigDecimal(
										currency.intValue()));
				{
					currencyStockDataTable = new CurrencyStockDataTable();

					for (UserStockView element : denominationStock) {
						if (element.getDenominationAmount().compareTo(
								Constants.ONE_LAKH) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setOneLakh(BigDecimal.ZERO);
								currencyStockDataTable
										.setOneLakhs(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setOneLakh(stock);
								currencyStockDataTable
										.setOneLakhDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.ONE_LAKH);
								if (list != null) {
									currencyStockDataTable.setOneLakhs(list
											.get(0).getAdjustmentAmount());
								} else {
									currencyStockDataTable
											.setOneLakhs(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setOneLakhAdj(currencyStockDataTable
												.getOneLakhs().multiply(
														Constants.ONE_LAKH));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiftyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftyThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);

								currencyStockDataTable.setFiftyThousand(stock);
								currencyStockDataTable
										.setFiftyThousandDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.FIFTY_THOUSAND);
								if (list != null) {
									currencyStockDataTable
											.setFiftyThousands(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setFiftyThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiftyThousandAdj(currencyStockDataTable
												.getFiftyThousands()
												.multiply(
														Constants.FIFTY_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwentyThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentyThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwentyThousand(stock);
								currencyStockDataTable
										.setTwentyThousandDenomId(element
												.getDenominationId());
								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TWENTY_THOUSAND);
								if (list != null) {
									currencyStockDataTable
											.setTwentyThousands(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTwentyThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwentyThousandAdj(currencyStockDataTable
												.getTwentyThousands()
												.multiply(
														Constants.TWENTY_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TEN_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTenThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTenThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);

								currencyStockDataTable.setTenThousand(stock);
								currencyStockDataTable
										.setTenThousandDenomId(element
												.getDenominationId());
								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TEN_THOUSAND);
								if (list != null) {
									currencyStockDataTable.setTenThousands(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTenThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTenThousandAdj(currencyStockDataTable
												.getTenThousands().multiply(
														Constants.TEN_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setFiveThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFiveThousand(stock);
								currencyStockDataTable
										.setFiveThousandDenomId(element
												.getDenominationId());
								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.FIVE_THOUSAND);
								if (list != null) {
									currencyStockDataTable
											.setFiveThousands(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setFiveThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiveThousandAdj(currencyStockDataTable
												.getFiveThousands()
												.multiply(
														Constants.FIVE_THOUSAND));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwoThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwoThousand(stock);
								currencyStockDataTable
										.setTwoThousandDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TWO_THOUSAND);
								if (list != null) {
									currencyStockDataTable.setTwoThousands(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTwoThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoThousandAdj(currencyStockDataTable
												.getTwoThousands().multiply(
														Constants.TWO_THOUSAND));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.THOUSAND) == 0) {
							if (element.getOpenQuantity() == BigDecimal.ZERO) {

								currencyStockDataTable
										.setThousand(BigDecimal.ZERO);
								currencyStockDataTable
										.setThousands(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setThousand(stock);
								currencyStockDataTable
										.setThousandDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.THOUSAND);
								if (list != null) {
									currencyStockDataTable.setThousands(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setThousands(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setThousandAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.THOUSAND));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE_HUNDRED) == 0) {
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable
										.setFiveHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiveHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFiveHundred(stock);
								currencyStockDataTable
										.setFiveHundredDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.FIVE_HUNDRED);
								if (list != null) {
									currencyStockDataTable.setFiveHundreds(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setFiveHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setFiveHundredAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.FIVE_HUNDRED));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.TWO_HUNDRED) == 0) {
							if (element.getOpenQuantity().compareTo(
									BigDecimal.ZERO) == 0) {
								currencyStockDataTable
										.setTwoHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwoHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwoHundred(stock);
								currencyStockDataTable
										.setTwoHundredDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TWO_HUNDRED);
								if (list != null) {
									currencyStockDataTable.setTwoHundreds(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTwoHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoHundredAdj(currencyStockDataTable
												.getThousands().multiply(
														Constants.TWO_HUNDRED));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.HUNDRED) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setHundred(BigDecimal.ZERO);
								currencyStockDataTable
										.setHundreds(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setHundred(stock);
								currencyStockDataTable
										.setHundredDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.HUNDRED);
								if (list != null) {
									currencyStockDataTable.setHundreds(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setHundreds(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setHundredAdj(currencyStockDataTable
												.getHundreds().multiply(
														Constants.HUNDRED));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIFTY) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setFifty(BigDecimal.ZERO);
								currencyStockDataTable
										.setFiftys(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFifty(stock);
								currencyStockDataTable.setFiftyDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.FIFTY);
								if (list != null) {
									currencyStockDataTable.setFiftys(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setFiftys(BigDecimal.ZERO);
								}

								currencyStockDataTable
										.setFiftyAdj(currencyStockDataTable
												.getFiftys().multiply(
														Constants.FIFTY));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TWENTY) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setTwenty(BigDecimal.ZERO);
								currencyStockDataTable
										.setTwentys(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwenty(stock);
								currencyStockDataTable.setTwentyDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TWENTY);
								if (list != null) {
									currencyStockDataTable.setTwentys(list.get(
											0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTwentys(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwentyAdj(currencyStockDataTable
												.getTwentys().multiply(
														Constants.TWENTY));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.TEN) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTen(BigDecimal.ZERO);
								currencyStockDataTable.setTens(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTen(stock);
								currencyStockDataTable.setTenDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.TEN);
								if (list != null) {
									currencyStockDataTable.setTens(list.get(0)
											.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTens(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTenAdj(currencyStockDataTable
												.getTens().multiply(
														Constants.TEN));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setFive(stock);
								currencyStockDataTable.setFiveDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.FIVE);
								if (list != null) {
									currencyStockDataTable.setFives(list.get(0)
											.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setFives(BigDecimal.ZERO);
								}

								currencyStockDataTable
										.setFiveAdj(currencyStockDataTable
												.getFives().multiply(
														Constants.FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								new BigDecimal(Constants.TWO)) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setTwo(BigDecimal.ZERO);
								currencyStockDataTable.setTwos(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setTwo(stock);
								currencyStockDataTable.setTwoDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												new BigDecimal(Constants.TWO));
								if (list != null) {
									currencyStockDataTable.setTwos(list.get(0)
											.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setTwos(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setTwoAdj(currencyStockDataTable
												.getTwos().multiply(
														new BigDecimal(
																Constants.TWO)));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable.setOne(BigDecimal.ZERO);
								currencyStockDataTable.setOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setOne(stock);
								currencyStockDataTable.setOneDenomId(element
										.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.ONE);
								if (list != null) {
									currencyStockDataTable.setOnes(list.get(0)
											.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setOneAdj(currencyStockDataTable
												.getOnes().multiply(
														Constants.ONE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointFive(stock);
								currencyStockDataTable
										.setPointFiveDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_FIVE);
								if (list != null) {
									currencyStockDataTable.setPointFives(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointFiveAdj(currencyStockDataTable
												.getPointFives().multiply(
														Constants.POINT_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_TWO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointTwoFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointTwoFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointTwoFive(stock);
								currencyStockDataTable
										.setPointTwoFiveDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_TWO_FIVE);
								if (list != null) {
									currencyStockDataTable
											.setPointTwoFives(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointTwoFives(BigDecimal.ZERO);
								}

								currencyStockDataTable
										.setPointTwoFiveAdj(currencyStockDataTable
												.getPointTwoFives()
												.multiply(
														Constants.POINT_TWO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointOne(stock);
								currencyStockDataTable
										.setPointOneDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ONE);
								if (list != null) {
									currencyStockDataTable.setPointOnes(list
											.get(0).getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointOneAdj(currencyStockDataTable
												.getPointOnes().multiply(
														Constants.POINT_ONE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroFive(stock);
								currencyStockDataTable
										.setPointZeroFiveDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ZERO_FIVE);
								if (list != null) {
									currencyStockDataTable
											.setPointZeroFives(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointZeroFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroFiveAdj(currencyStockDataTable
												.getPointZeroFives()
												.multiply(
														Constants.POINT_ZERO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_TWO) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroTwo(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroTwos(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroTwo(stock);
								currencyStockDataTable
										.setPointZeroTwoDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ZERO_TWO);
								if (list != null) {
									currencyStockDataTable
											.setPointZeroTwos(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointZeroTwos(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroTwoAdj(currencyStockDataTable
												.getPointZeroTwos()
												.multiply(
														Constants.POINT_ZERO_TWO));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable.setPointZeroOne(stock);
								currencyStockDataTable
										.setPointZeroOneDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ZERO_ONE);
								if (list != null) {
									currencyStockDataTable
											.setPointZeroOnes(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointZeroOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroOneAdj(currencyStockDataTable
												.getPointZeroOnes()
												.multiply(
														Constants.POINT_ZERO_ONE));
							}
						}
						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_FIVE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroFive(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroFives(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable
										.setPointZeroZeroFive(stock);
								currencyStockDataTable
										.setPointZeroZeroFiveDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ZERO_ZERO_FIVE);
								if (list != null) {
									currencyStockDataTable
											.setPointZeroZeroFives(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointZeroZeroFives(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroZeroFiveAdj(currencyStockDataTable
												.getPointZeroZeroFives()
												.multiply(
														Constants.POINT_ZERO_ZERO_FIVE));
							}
						}

						if (element.getDenominationAmount().compareTo(
								Constants.POINT_ZERO_ZERO_ONE) == 0) {

							if (element.getOpenQuantity() == BigDecimal.ZERO) {
								currencyStockDataTable
										.setPointZeroZeroOne(BigDecimal.ZERO);
								currencyStockDataTable
										.setPointZeroZeroOnes(BigDecimal.ZERO);
							} else {
								stock = initZero(element);
								currencyStockDataTable
										.setPointZeroZeroOne(stock);
								currencyStockDataTable
										.setPointZeroZeroOneDenomId(element
												.getDenominationId());

								List<ForeignCurrencyAdjust> list = cashTransferLToLService
										.getForeignCurrencyAdjustDetails(
												cashList.get(0).getDocumentNo(),
												element.getCurrencyId(),
												Constants.POINT_ZERO_ZERO_ONE);
								if (list != null) {
									currencyStockDataTable
											.setPointZeroZeroOnes(list.get(0)
													.getNotesQuantity());
								} else {
									currencyStockDataTable
											.setPointZeroZeroOnes(BigDecimal.ZERO);
								}
								currencyStockDataTable
										.setPointZeroZeroOneAdj(currencyStockDataTable
												.getPointZeroZeroOnes()
												.multiply(
														Constants.POINT_ZERO_ZERO_ONE));
							}
						}

						currencyStockDataTable.setCurrencyName(element
								.getCurrencyName());
						currencyStockDataTable.setCurrencyId(element
								.getCurrencyId());

						// Calculate currency
						getCalculateHeaderCurrency(currencyStockDataTable);
						getReadOnly(currencyStockDataTable);

					}
				}
				currencyStockDataTableList.add(currencyStockDataTable);
			}
			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"../cashtransfer/CashTransferFromCounterToCounter.xhtml");
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public void deleteCashDetails() {
		CashHeader cashHeader = null;
		List<CashHeader> cashList = getCashTxnCheckList();
		try {
			if (cashList.size() > 0) {
				cashHeader = new CashHeader();
				cashHeader.setCashHeaderId(cashList.get(0).getCashHeaderId());
				List<ForeignCurrencyAdjust> currencyAdjustList = cashTransferLToLService
						.getForeignCurrencyAdjust(cashList.get(0)
								.getDocumentNo());
				List<CashDetails> cashDetailsList = cashTransferLToLService
						.getCashDetailsById(cashHeader.getCashHeaderId());

				if (currencyAdjustList != null) {
					for (ForeignCurrencyAdjust foreignCurrencyAdjust1 : currencyAdjustList) {
						cashTransferLToLService
								.deleteCurrencyAdjustDetails(foreignCurrencyAdjust1);
					}
				}
				if (cashDetailsList != null) {
					for (CashDetails cashDetails : cashDetailsList) {
						cashTransferLToLService.deleteCashDetails(cashDetails);
					}
					cashTransferLToLService.deleteCashHeader(cashHeader);
				}
				RequestContext.getCurrentInstance().execute(
						"deletesuccess.show();");
			}
		} catch (Exception e) {
			if (e.getMessage() != null) {
				setWarningMessage("Exception :" + e.getMessage());
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			} else {
				setWarningMessage("Exception :" + e);
				RequestContext.getCurrentInstance().execute("proceErr.show();");
			}
		}
	}

	public BigDecimal initZero(UserStockView element) {
		if (element.getOpenQuantity() == null
				|| element.getOpenQuantity() == BigDecimal.ZERO) {
			element.setOpenQuantity(BigDecimal.ZERO);
		}
		if (element.getPurchaseQuantity() == null
				|| element.getPurchaseQuantity() == BigDecimal.ZERO) {
			element.setPurchaseQuantity(BigDecimal.ZERO);
		}
		if (element.getReceivedQuantity() == null
				|| element.getReceivedQuantity() == BigDecimal.ZERO) {
			element.setReceivedQuantity(BigDecimal.ZERO);
		}
		if (element.getSaleQuantity() == null
				|| element.getSaleQuantity() == BigDecimal.ZERO) {
			element.setSaleQuantity(BigDecimal.ZERO);
		}
		if (element.getTransactionQuantity() == null
				|| element.getTransactionQuantity() == BigDecimal.ZERO) {
			element.setTransactionQuantity(BigDecimal.ZERO);
		}
		stock = element.getOpenQuantity().add(element.getPurchaseQuantity())
				.add(element.getReceivedQuantity())
				.subtract(element.getSaleQuantity())
				.subtract(element.getTransactionQuantity());
		if (stock == null || stock == BigDecimal.ZERO) {
			return BigDecimal.ZERO;
		} else {
			return stock;
		}
	}

	public List<CashHeader> getCashTxnCheckList() {
		return cashTxnCheckList;
	}

	public void setCashTxnCheckList(List<CashHeader> cashTxnCheckList) {
		this.cashTxnCheckList = cashTxnCheckList;
	}

	public BigDecimal getCashHeaderId() {
		return cashHeaderId;
	}

	public void setCashHeaderId(BigDecimal cashHeaderId) {
		this.cashHeaderId = cashHeaderId;
	}

	public String getSaveType() {
		return saveType;
	}

	public void setSaveType(String saveType) {
		this.saveType = saveType;
	}

	public String getUpdateDocumentNo() {
		return updateDocumentNo;
	}

	public void setUpdateDocumentNo(String updateDocumentNo) {
		this.updateDocumentNo = updateDocumentNo;
	}

}
