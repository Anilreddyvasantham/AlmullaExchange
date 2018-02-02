package com.amg.exchange.currency.inquiry.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.currency.inquiry.model.CashDetailsView;
import com.amg.exchange.currency.inquiry.model.CashPositionByLocationView;
import com.amg.exchange.currency.inquiry.service.ICashPositionByLocationInquiryService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashPositionByLocationInquiry")
@Scope("session")
public class CashPositionByLocationInquiryBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal modeId;
	private BigDecimal currencyId;
	private BigDecimal cashierId;
	private BigDecimal branchId;
	private BigDecimal branchCode;
	private String branchName;
	private String currencyCode;
	private String currencyName;
	private String cashierCode;
	private String cashierName;
	private String modeName;
	private String modeCode;
	private String createdBy;
	private String collectionMode;
	
	private String errorMessage;
	private Boolean booVisible;

	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<CountryBranch> lstOfCountryBranch = new ArrayList<CountryBranch>();
	private List<CashPositionByLocationInquiryDataTable> lstOfCashPositionByLocationInquiryDataTable = new ArrayList<CashPositionByLocationInquiryDataTable>();
	private List<CashDetailsViewDataTable> lstOfCashDetailsViewDataTable = new ArrayList<CashDetailsViewDataTable>();

	private List<CashPositionByLocationView> lstOfCashPositionByLocationViews = new ArrayList<CashPositionByLocationView>();
	private List<CashDetailsView> lstOfCashDetailsViews = new ArrayList<CashDetailsView>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	ICashPositionByLocationInquiryService cashPositionByLocationInquiryService;

	public CashPositionByLocationInquiryBean() {
		// TODO Auto-generated constructor stub
	}
	
	
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

	public BigDecimal getModeId() {
		return modeId;
	}

	public void setModeId(BigDecimal modeId) {
		this.modeId = modeId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getCashierId() {
		return cashierId;
	}

	public void setCashierId(BigDecimal cashierId) {
		this.cashierId = cashierId;
	}

	public BigDecimal getBranchId() {
		return branchId;
	}

	public void setBranchId(BigDecimal branchId) {
		this.branchId = branchId;
	}

	public BigDecimal getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(BigDecimal branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getCashierCode() {
		return cashierCode;
	}

	public void setCashierCode(String cashierCode) {
		this.cashierCode = cashierCode;
	}

	public String getCashierName() {
		return cashierName;
	}

	public void setCashierName(String cashierName) {
		this.cashierName = cashierName;
	}

	public String getModeName() {
		return modeName;
	}

	public void setModeName(String modeName) {
		this.modeName = modeName;
	}

	public String getModeCode() {
		return modeCode;
	}

	public void setModeCode(String modeCode) {
		this.modeCode = modeCode;
	}

	public String getCollectionMode() {
		return collectionMode;
	}

	public void setCollectionMode(String collectionMode) {
		this.collectionMode = collectionMode;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<CountryBranch> getCountryBranchList() {
		return countryBranchList;
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	public List<CountryBranch> getLstOfCountryBranch() {
		return lstOfCountryBranch;
	}

	public void setLstOfCountryBranch(List<CountryBranch> lstOfCountryBranch) {
		this.lstOfCountryBranch = lstOfCountryBranch;
	}

	public ICashPositionByLocationInquiryService getCashPositionByLocationInquiryService() {
		return cashPositionByLocationInquiryService;
	}

	public void setCashPositionByLocationInquiryService(ICashPositionByLocationInquiryService cashPositionByLocationInquiryService) {
		this.cashPositionByLocationInquiryService = cashPositionByLocationInquiryService;
	}

	public List<CashPositionByLocationView> getLstOfCashPositionByLocationViews() {
		return lstOfCashPositionByLocationViews;
	}

	public void setLstOfCashPositionByLocationViews(List<CashPositionByLocationView> lstOfCashPositionByLocationViews) {
		this.lstOfCashPositionByLocationViews = lstOfCashPositionByLocationViews;
	}

	public List<CashDetailsView> getLstOfCashDetailsViews() {
		return lstOfCashDetailsViews;
	}

	public void setLstOfCashDetailsViews(List<CashDetailsView> lstOfCashDetailsViews) {
		this.lstOfCashDetailsViews = lstOfCashDetailsViews;
	}

	public List<CashPositionByLocationInquiryDataTable> getLstOfCashPositionByLocationInquiryDataTable() {
		return lstOfCashPositionByLocationInquiryDataTable;
	}

	public void setLstOfCashPositionByLocationInquiryDataTable(List<CashPositionByLocationInquiryDataTable> lstOfCashPositionByLocationInquiryDataTable) {
		this.lstOfCashPositionByLocationInquiryDataTable = lstOfCashPositionByLocationInquiryDataTable;
	}

	public List<CashDetailsViewDataTable> getLstOfCashDetailsViewDataTable() {
		return lstOfCashDetailsViewDataTable;
	}

	public void setLstOfCashDetailsViewDataTable(List<CashDetailsViewDataTable> lstOfCashDetailsViewDataTable) {
		this.lstOfCashDetailsViewDataTable = lstOfCashDetailsViewDataTable;
	}

	public List<CountryBranch> getCountryBranch() {

		lstOfCountryBranch = getCashPositionByLocationInquiryService().getCountryBranchList(sessionStateManage.getCountryId());

		return lstOfCountryBranch;
	}

	public void getCashPositionByLocationViewList() {
		try {
			setBooVisible(false);
			lstOfCashPositionByLocationInquiryDataTable.clear();
			lstOfCashPositionByLocationViews = getCashPositionByLocationInquiryService().getCashBalanceList(getBranchId());
			if (lstOfCashPositionByLocationViews != null) {
				for (CashPositionByLocationView cashPositionByLocationView : lstOfCashPositionByLocationViews) {
					CashPositionByLocationInquiryDataTable cashPositionByLocationInquiryDataTable = new CashPositionByLocationInquiryDataTable();

					cashPositionByLocationInquiryDataTable.setBranchId(cashPositionByLocationView.getBranchId());
					cashPositionByLocationInquiryDataTable.setCashAmount(cashPositionByLocationView.getCashAmount());
					cashPositionByLocationInquiryDataTable.setNonCashAmount(cashPositionByLocationView.getNonCashAmount());
					cashPositionByLocationInquiryDataTable.setNetAmount(cashPositionByLocationView.getNetAmount());
					cashPositionByLocationInquiryDataTable.setCurrencyId(cashPositionByLocationView.getCurrencyId());
					cashPositionByLocationInquiryDataTable.setCurrencyName(cashPositionByLocationView.getCurrencyName());
					cashPositionByLocationInquiryDataTable.setCreatedBY(cashPositionByLocationView.getCreatedBY());
					cashPositionByLocationInquiryDataTable.setQuoteName(cashPositionByLocationView.getQuoteName());
					cashPositionByLocationInquiryDataTable.setBranchName(cashPositionByLocationView.getBranchName());
					lstOfCashPositionByLocationInquiryDataTable.add(cashPositionByLocationInquiryDataTable);

				}
			} else {
				 RequestContext.getCurrentInstance().execute("noRecords.show();");
				 return;
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
	public void cashPositionByLocationInquiryNavigation() {
		try {

			setBranchId(null);
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "cashpositionbylocationinquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashpositionbylocationinquiry.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void backCashPositionByLocationInquiryNavigation() {
		try {
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashpositionbylocationinquiry.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cashPositionByLocationInquiryCtoBNavigation() {
		try {
			// lstOfCashPositionByLocationInquiryDataTable.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctob.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cashPositionByLocationInquiryCtoBandCashierNavigation() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctobandcashier.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cashPositionByLocationInquiryCtoBandCashierAndCashNavigation() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctobandcashierandcash.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() {

		try {
			lstOfCashPositionByLocationInquiryDataTable.clear();
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public void cashPositionByLocationInquiryCtoBNavigation(CashPositionByLocationInquiryDataTable cashPositionByLocationInquiryDataTable) {
		lstOfCashPositionByLocationInquiryDataTable.clear();
		setBranchName(cashPositionByLocationInquiryDataTable.getBranchName());
		setBranchCode(cashPositionByLocationInquiryDataTable.getBranchId());
		setCurrencyCode(cashPositionByLocationInquiryDataTable.getQuoteName());
		setCurrencyName(cashPositionByLocationInquiryDataTable.getCurrencyName());
		setCurrencyId(cashPositionByLocationInquiryDataTable.getCurrencyId());

		lstOfCashPositionByLocationViews = getCashPositionByLocationInquiryService().getCashBalanceBasedOnCtoBList(cashPositionByLocationInquiryDataTable.getBranchId(), cashPositionByLocationInquiryDataTable.getCurrencyId());
		if (lstOfCashPositionByLocationViews != null) {
			for (CashPositionByLocationView cashPositionByLocationView : lstOfCashPositionByLocationViews) {
				CashPositionByLocationInquiryDataTable cashPositionByLocationInquiry = new CashPositionByLocationInquiryDataTable();

				cashPositionByLocationInquiry.setBranchId(cashPositionByLocationView.getBranchId());
				cashPositionByLocationInquiry.setCashAmount(cashPositionByLocationView.getCashAmount());
				cashPositionByLocationInquiry.setNonCashAmount(cashPositionByLocationView.getNonCashAmount());
				cashPositionByLocationInquiry.setNetAmount(cashPositionByLocationView.getNetAmount());
				cashPositionByLocationInquiry.setCurrencyId(cashPositionByLocationView.getCurrencyId());
				cashPositionByLocationInquiry.setCurrencyName(cashPositionByLocationView.getCurrencyName());
				cashPositionByLocationInquiry.setCreatedBY(cashPositionByLocationView.getCreatedBY());
				cashPositionByLocationInquiry.setQuoteName(cashPositionByLocationView.getQuoteName());
				cashPositionByLocationInquiry.setBranchName(cashPositionByLocationView.getBranchName());
				cashPositionByLocationInquiry.setCreatedBY(cashPositionByLocationView.getCreatedBY());
				lstOfCashPositionByLocationInquiryDataTable.add(cashPositionByLocationInquiry);

			}
		} else {

		}

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctob.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cashPositionByLocationInquiryCtoBanCashierNavigation(CashPositionByLocationInquiryDataTable cashPositionByLocationInquiryDataTable) {

		System.out.println("cashPositionByLocationInquiryDataTable.getCreatedBY() ====== > " + cashPositionByLocationInquiryDataTable.getCreatedBY());
		lstOfCashPositionByLocationInquiryDataTable.clear();
		setBranchName(cashPositionByLocationInquiryDataTable.getBranchName());
		setBranchCode(cashPositionByLocationInquiryDataTable.getBranchId());
		setCurrencyCode(cashPositionByLocationInquiryDataTable.getQuoteName());
		setCurrencyName(cashPositionByLocationInquiryDataTable.getCurrencyName());
		setCurrencyId(cashPositionByLocationInquiryDataTable.getCurrencyId());
		setCashierName(cashPositionByLocationInquiryDataTable.getCreatedBY());

		lstOfCashDetailsViews = getCashPositionByLocationInquiryService().getCashBalanceBasedOnCtoBandCashierList(cashPositionByLocationInquiryDataTable.getBranchId(), cashPositionByLocationInquiryDataTable.getCurrencyId(), cashPositionByLocationInquiryDataTable.getCreatedBY());

		if (lstOfCashDetailsViews != null) {
			for (CashDetailsView cashPositionByLocationView : lstOfCashDetailsViews) {

				CashDetailsViewDataTable cashDetailsViewDataTable = new CashDetailsViewDataTable();

				cashDetailsViewDataTable.setCollectionMode(cashPositionByLocationView.getCollectionMode());
				cashDetailsViewDataTable.setCollectionAmount(cashPositionByLocationView.getCollectionAmount());
				cashDetailsViewDataTable.setRefundAmount(cashPositionByLocationView.getRefundAmount());
				cashDetailsViewDataTable.setNetAmount(cashPositionByLocationView.getNetAmount());
				cashDetailsViewDataTable.setDocumentBranchId(cashPositionByLocationView.getDocumentBranchId());
				cashDetailsViewDataTable.setCurrencyId(cashPositionByLocationView.getCurrencyId());
				cashDetailsViewDataTable.setCreatedBy(cashPositionByLocationView.getCreatedBy());

				lstOfCashDetailsViewDataTable.add(cashDetailsViewDataTable);

			}
		} else {

		}

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctobandcashier.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void cashPositionByLocationInquiryCtoBanCashierandModeNavigation(CashDetailsViewDataTable cashDetailsViewDataTable) {

		//lstOfCashDetailsViewDataTable.clear();
		setModeName(cashDetailsViewDataTable.getCollectionMode());

		lstOfCashDetailsViews = getCashPositionByLocationInquiryService().getCashBalanceBasedOnCtoBandCashierandModeList(cashDetailsViewDataTable.getDocumentBranchId(), cashDetailsViewDataTable.getCurrencyId(), cashDetailsViewDataTable.getCreatedBy(),cashDetailsViewDataTable.getCollectionMode());

		if (lstOfCashDetailsViews != null) {

			for (CashDetailsView cashPositionByLocationView : lstOfCashDetailsViews) {
				CashDetailsViewDataTable cashDetailsView = new CashDetailsViewDataTable();

				cashDetailsView.setDocumentType(cashPositionByLocationView.getDocumentType());
				cashDetailsView.setDocumentNo(cashPositionByLocationView.getDocumentNo());

				cashDetailsView.setDocumentDate(cashPositionByLocationView.getDocumentDate());
				cashDetailsView.setChequeBankReference(cashPositionByLocationView.getChequeBankReference());
				cashDetailsView.setApproveNo(cashPositionByLocationView.getApproveNo());
				cashDetailsView.setDocumentBranchId(cashPositionByLocationView.getDocumentBranchId());
				cashDetailsView.setCurrencyId(cashPositionByLocationView.getCurrencyId());
				cashDetailsView.setCreatedBy(cashPositionByLocationView.getCreatedBy());
				cashDetailsView.setCollectionAmount(cashPositionByLocationView.getCollectionAmount());
				cashDetailsView.setNetAmount(cashPositionByLocationView.getNetAmount());

				lstOfCashDetailsViewDataTable.add(cashDetailsView);

			}
		} else {

		}

		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/cashbypositionlocationctobandcashierandcash.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void clearAll(){
		
	}

}
