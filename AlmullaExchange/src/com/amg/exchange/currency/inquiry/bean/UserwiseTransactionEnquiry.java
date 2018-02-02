/**
 * 
 */
package com.amg.exchange.currency.inquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;


import org.codehaus.groovy.tools.shell.commands.ClearCommand;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.currency.inquiry.model.BranchDayTransactionView;
import com.amg.exchange.currency.inquiry.service.ICurrencyEnquiryService;
import com.amg.exchange.foreigncurrency.model.CollectDetail;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Employee;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("userwiseTransactionEnquiry")
@Scope("session")
public class UserwiseTransactionEnquiry<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private static final Logger LOG = Logger.getLogger(UserwiseTransactionEnquiry.class);

	private BigDecimal countryBranchId;
	private String username;
	private Date transactionDate;
	private BigDecimal customerId;
	
	private BigDecimal totalCash;
	private BigDecimal totalCheque;
	private BigDecimal totalKnet;
	private BigDecimal totalOthers;
	private BigDecimal totalRefund;

	private String errorMessage;
	private Boolean booVisible;
	private Boolean booReadOnly;
	
	private Date currentDate = new Date();

	private List<CountryBranch> lstCountryBranchForLocation = new ArrayList<CountryBranch>();
	private List<Employee> cashierList = new ArrayList<Employee>();
	List<UserwiseTransactionDataTable> userwiseTransactionDataTableList = new ArrayList<UserwiseTransactionDataTable>();
	List<CollectDetail> branchDayCoolectionDetailList = new ArrayList<CollectDetail>();
	List<BranchDayTransactionView> branchDayTransactionList = new ArrayList<BranchDayTransactionView>(); 

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ICurrencyEnquiryService currencyEnquiryService;
	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;
	
	
	
	

	public List<CollectDetail> getBranchDayCoolectionDetailList() {
		return branchDayCoolectionDetailList;
	}

	public void setBranchDayCoolectionDetailList(List<CollectDetail> branchDayCoolectionDetailList) {
		this.branchDayCoolectionDetailList = branchDayCoolectionDetailList;
	}

	public List<BranchDayTransactionView> getBranchDayTransactionList() {
		return branchDayTransactionList;
	}

	public void setBranchDayTransactionList(List<BranchDayTransactionView> branchDayTransactionList) {
		this.branchDayTransactionList = branchDayTransactionList;
	}

	public ForeignLocalCurrencyDenominationService<T> getForeignLocalCurrencyDenominationService() {
		return foreignLocalCurrencyDenominationService;
	}

	public void setForeignLocalCurrencyDenominationService(ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService) {
		this.foreignLocalCurrencyDenominationService = foreignLocalCurrencyDenominationService;
	}

	
	public BigDecimal getTotalRefund() {
		return totalRefund;
	}

	public void setTotalRefund(BigDecimal totalRefund) {
		this.totalRefund = totalRefund;
	}

	public BigDecimal getTotalCash() {
		//totalCash = new BigDecimal(300);
		return totalCash;
	}

	public void setTotalCash(BigDecimal totalCash) {
		this.totalCash = totalCash;
	}

	public BigDecimal getTotalCheque() {
		return totalCheque;
	}

	public void setTotalCheque(BigDecimal totalCheque) {
		this.totalCheque = totalCheque;
	}

	public BigDecimal getTotalKnet() {
		return totalKnet;
	}

	public void setTotalKnet(BigDecimal totalKnet) {
		this.totalKnet = totalKnet;
	}

	public BigDecimal getTotalOthers() {
		return totalOthers;
	}

	public void setTotalOthers(BigDecimal totalOthers) {
		this.totalOthers = totalOthers;
	}

	public Boolean getBooReadOnly() {
		return booReadOnly;
	}

	public void setBooReadOnly(Boolean booReadOnly) {
		this.booReadOnly = booReadOnly;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

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

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public void setLstCountryBranchForLocation(List<CountryBranch> lstCountryBranchForLocation) {
		this.lstCountryBranchForLocation = lstCountryBranchForLocation;
	}

	public List<Employee> getCashierList() {
		return cashierList;
	}

	public void setCashierList(List<Employee> cashierList) {
		this.cashierList = cashierList;
	}

	public BigDecimal getCustomerId() {
		return customerId;
	}

	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public ICurrencyEnquiryService getCurrencyEnquiryService() {
		return currencyEnquiryService;
	}

	public void setCurrencyEnquiryService(ICurrencyEnquiryService currencyEnquiryService) {
		this.currencyEnquiryService = currencyEnquiryService;
	}

	public List<CountryBranch> getLstCountryBranchForLocation() {	
		return lstCountryBranchForLocation;
	}

	public void getAllCasheirs() {
		try {

			cashierList.clear();
			List<Employee> cashierList1 = currencyEnquiryService.getAllCashierList(getCountryBranchId());
			for (Employee employee : cashierList1) {

				cashierList.add(employee);
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		//resetFilters("form:dataTable");
		userwiseTransactionDataTableList.clear();
		setTransactionDate(new Date());
	//	setCountryBranchId(null);
		//setUsername(null);
		setTotalCash(null);
		setTotalCheque(null);
		setTotalKnet(null);
		setTotalOthers(null);
		setTotalRefund(null);
		setCustomerId(sessionStateManage.getEmployeeId());
		populateBranch();
		populateUser();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "userwisetransactionenquiry.xhtml");
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

			context.redirect("../enquiry/userwisetransactionenquiry.xhtml");

		} catch (Exception e) {
			e.getMessage();
		}

	}

	public List<UserwiseTransactionDataTable> getUserwiseTransactionDataTableList() {
		return userwiseTransactionDataTableList;
	}

	public void setUserwiseTransactionDataTableList(List<UserwiseTransactionDataTable> userwiseTransactionDataTableList) {
		this.userwiseTransactionDataTableList = userwiseTransactionDataTableList;
	}
	
	
	public void onDateSelectFrom(SelectEvent event) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		try {
			setTransactionDate(format.parse(format.format(event.getObject())));
		}catch(ParseException exception){
			 
			RequestContext.getCurrentInstance().execute("dataexception.show();");
			return;
		}
	}
	double tempTotalCash = 0.0;
	double tempTotalCheque = 0.0;
	double tempTotalKnet = 0.0;
	double tempTotalOthers = 0.0;
	double tempTotalRefund = 0.0;
	
	public void search() {
		userwiseTransactionDataTableList.clear();
		HashMap<String, Object> lstbranchDayTransactionInput = new HashMap<String, Object>();
		lstbranchDayTransactionInput.put("COUNTRY_BRANCH_ID", getCountryBranchId());
		lstbranchDayTransactionInput.put("CUSTOMER_ID", getCustomerId());
		lstbranchDayTransactionInput.put("TRANSACTION_DATE", getTransactionDate());

		try {

			double tempTotalCash1 = 0.0;
			double tempTotalCheque1 = 0.0;
			double tempTotalKnet1 = 0.0;
			double tempTotalOthers1 = 0.0;
			double tempTotalRefund1 = 0.0;

			branchDayTransactionList = currencyEnquiryService.getBranchDayTransaction(lstbranchDayTransactionInput);

			if (branchDayTransactionList != null) {

				for (BranchDayTransactionView branchDayTransactionView : branchDayTransactionList) {

					UserwiseTransactionDataTable userwiseTransactionDataTable = new UserwiseTransactionDataTable();
					userwiseTransactionDataTable.setCustomerRef(branchDayTransactionView.getCustomerRef());
					userwiseTransactionDataTable.setTransactionType(branchDayTransactionView.getTransactionType());
					userwiseTransactionDataTable.setLocalTranxAmount(branchDayTransactionView.getLocalTranxAmount());
					userwiseTransactionDataTable.setOldEmos(branchDayTransactionView.getOldEmos());
					userwiseTransactionDataTable.setMtcNo(branchDayTransactionView.getMtcNo());
					userwiseTransactionDataTable.setDocumentFinanceYear(branchDayTransactionView.getDocumentFinanceYear());
					userwiseTransactionDataTable.setDocumentNo(branchDayTransactionView.getDocumentNo());
					userwiseTransactionDataTable.setRefundAmount(branchDayTransactionView.getRefundAmount());

					branchDayCoolectionDetailList = currencyEnquiryService.getBranchDayCollectionDetail(branchDayTransactionView.getCollectionDocNumber(), branchDayTransactionView.getCollectionDocCode(), branchDayTransactionView.getCollectionDocFinanceYear());

					if (branchDayCoolectionDetailList != null) {
						for (CollectDetail collectDetail : branchDayCoolectionDetailList) {
							if (collectDetail.getCollectionMode() != null) {
								if (collectDetail.getCollectionMode().equalsIgnoreCase("C")) {
									userwiseTransactionDataTable.setCash(branchDayTransactionView.getLocalTranxAmount());
									tempTotalCash = +branchDayTransactionView.getLocalTranxAmount().doubleValue();
									tempTotalCash1 = tempTotalCash1 + tempTotalCash;
								} else if (collectDetail.getCollectionMode().equalsIgnoreCase("K")) {
									userwiseTransactionDataTable.setkNet(branchDayTransactionView.getLocalTranxAmount());
									tempTotalKnet = +branchDayTransactionView.getLocalTranxAmount().doubleValue();
									tempTotalKnet1 = tempTotalKnet1 + tempTotalKnet;
								} else if (collectDetail.getCollectionMode().equalsIgnoreCase("B")) {
									userwiseTransactionDataTable.setCheque(branchDayTransactionView.getLocalTranxAmount());
									tempTotalCheque = +branchDayTransactionView.getLocalTranxAmount().doubleValue();
									tempTotalCheque1 = tempTotalCheque1 + tempTotalCheque;
								} else {
									userwiseTransactionDataTable.setOthers(branchDayTransactionView.getLocalTranxAmount());
									tempTotalOthers = +branchDayTransactionView.getLocalTranxAmount().doubleValue();
									tempTotalOthers1 = tempTotalOthers1 + tempTotalOthers;
								}
								
							}

							
						}
					}
					userwiseTransactionDataTableList.add(userwiseTransactionDataTable);
					setTotalCash(GetRound.roundBigDecimal(new BigDecimal(tempTotalCash1), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setTotalCheque(GetRound.roundBigDecimal(new BigDecimal(tempTotalCheque1), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setTotalKnet(GetRound.roundBigDecimal(new BigDecimal(tempTotalKnet1), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					setTotalOthers(GetRound.roundBigDecimal(new BigDecimal(tempTotalOthers1), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
					tempTotalRefund = +branchDayTransactionView.getRefundAmount().doubleValue();
					tempTotalRefund1 = tempTotalRefund1+tempTotalRefund;
					setTotalRefund(GetRound.roundBigDecimal(new BigDecimal(tempTotalRefund1), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(new BigDecimal(sessionStateManage.getCurrencyId()))));
				}

			} else {
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				return;
			}

		} catch (Exception e) {
			// setExceptionMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("dataexception.show();");
		}

	}
	
	public void exit() {
		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				userwiseTransactionDataTableList.clear();
				setTransactionDate(null);
				setCountryBranchId(null);
				setUsername(null);
				setTotalCash(null);
				setTotalCheque(null);
				setTotalKnet(null);
				setTotalOthers(null);
				setTotalRefund(null);
				
			

		} catch (NullPointerException ne) {
			
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}

		
	}
	
	public void populateBranch() {

		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			List<CountryBranch> lstCountryBranch = generalService.getBranchDetails(sessionStateManage.getCountryId());

			if (lstCountryBranch.size() != 0) {

				setLstCountryBranchForLocation(lstCountryBranch);
			}
			setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));

		} else {
			List<CountryBranch> lstCountryBranch = currencyEnquiryService.getBranchDetails(sessionStateManage.getCountryId(), new BigDecimal(sessionStateManage.getBranchId()));
			if (lstCountryBranch.size() != 0) {

				setLstCountryBranchForLocation(lstCountryBranch);
			}
			setCountryBranchId(new BigDecimal(sessionStateManage.getBranchId()));
		}

	}
	
	public void populateUser() {
		try {

			if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
				cashierList.clear();
				List<Employee> cashierList1 = currencyEnquiryService.getAllCashierList(getCountryBranchId());
				for (Employee employee : cashierList1) {
					cashierList.add(employee);
				}

			} else {
				cashierList.clear();
				List<Employee> cashierList1 = currencyEnquiryService.getCashierList(getCountryBranchId(), new BigDecimal(sessionStateManage.getRoleId()));
				for (Employee employee : cashierList1) {
					if(sessionStateManage.getEmployeeId() != null && employee.getEmployeeId().compareTo(sessionStateManage.getEmployeeId())==0){
						cashierList.add(employee);
					}
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}

	}
	public void resetFilters(String clearDataTable) {
		DataTable dataTable = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(clearDataTable);
		if (dataTable != null) {
			dataTable.reset();
		}
	}

}
