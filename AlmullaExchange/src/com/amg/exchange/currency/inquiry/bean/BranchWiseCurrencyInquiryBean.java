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

import com.amg.exchange.currency.inquiry.model.BranchWiseCurrencyStock;
import com.amg.exchange.currency.inquiry.service.ICurrencyEnquiryService;
import com.amg.exchange.foreigncurrency.model.CurrencyWiseDenomination;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("branchWiseCurrencyInquiry")
@Scope("session")
public class BranchWiseCurrencyInquiryBean<T> implements Serializable {
	
	/*
	 * Autowire Declaration start here
	 */
	@Autowired
	ICurrencyEnquiryService currencyEnquiryService;
	
	/*
	 * Properties declaration start here
	 */
	private SessionStateManage session = new SessionStateManage();

	private static final long serialVersionUID = 1L;
	private BigDecimal currencyId;
	private boolean booDataTable = false;

	private List<CurrencyMaster> lstOfCurrency = new ArrayList<CurrencyMaster>();
	private List<BranchWiseCurrencyInquiryDataTable> lstOfBranchWiseCurrencyInquiryDataTable = new ArrayList<BranchWiseCurrencyInquiryDataTable>();
	private List<BranchWiseCurrencyStock> lstOfUserStockView = new ArrayList<BranchWiseCurrencyStock>();

	private List<CountryBranch> countryBranchList = new ArrayList<CountryBranch>();
	private List<CountryBranchDataTable> countryBranchDataList = new ArrayList<CountryBranchDataTable>();

	private List<CurrencyWiseDenomination> denominationList = new ArrayList<CurrencyWiseDenomination>();
	private List<DenominationDataTable> denominationDataList = new ArrayList<DenominationDataTable>();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void branchWiseCurrencyInquiryNavigation() {

		try {
			setBooDataTable(false);
			setCurrencyId(null);
			countryBranchDataList.clear();
			// denominationDataList.clear();
			lstOfBranchWiseCurrencyInquiryDataTable.clear();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "branchwisecurrencyinquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../currencyinquiry/branchwisecurrencyinquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CurrencyMaster> getCurrencyNameList() {

		lstOfCurrency = getCurrencyEnquiryService().getCurrencyList();

		return lstOfCurrency;
	}

	public void getUserStockViewList() {
		try {
			setBooDataTable(true);
			lstOfBranchWiseCurrencyInquiryDataTable.clear();
			BranchWiseCurrencyInquiryDataTable branchWiseCurrencyInquiryDataTable = new BranchWiseCurrencyInquiryDataTable();

			countryBranchList = getCurrencyEnquiryService().getCountryBranchByCurrency();
			for (CountryBranch countryBranch : countryBranchList) {
				branchWiseCurrencyInquiryDataTable = new BranchWiseCurrencyInquiryDataTable();

				branchWiseCurrencyInquiryDataTable.setCountryBranchName(countryBranch.getBranchName());

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.THOUSAND);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.THOUSAND) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setThousands(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setThousands(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setThousands(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.FIVE_HUNDRED);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.FIVE_HUNDRED) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setFiveHundreds(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setFiveHundreds(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setFiveHundreds(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.HUNDRED);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.HUNDRED) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setHundreds(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setHundreds(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setHundreds(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.FIFTY);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.FIFTY) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setFifties(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setFifties(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setFifties(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.TWENTY);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.TWENTY) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setTwenties(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setTwenties(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setTwenties(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.TEN);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.TEN) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setTens(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setTens(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setTens(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.FIVE);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.FIVE) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setFives(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setFives(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setFives(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), new BigDecimal(Constants.TWO));
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.TWO) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setTwos(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setTwos(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setTwos(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.ONE);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.ONE) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setOnes(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setOnes(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setOnes(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.POINT_FIVE);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.POINT_FIVE) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setPointfives(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setPointfives(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setPointfives(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfUserStockView = currencyEnquiryService.getBrachWiseCurrencyStockList(countryBranch.getCountryBranchId(), getCurrencyId(), Constants.POINT_TWO_FIVE);
				if (lstOfUserStockView.size() > 0) {
					if (lstOfUserStockView.get(0).getDenominationAmount().equals(Constants.POINT_TWO_FIVE) && lstOfUserStockView.get(0).getCountryBranchId().toString().equals(countryBranch.getCountryBranchId().toString())) {
						branchWiseCurrencyInquiryDataTable.setPointTwoFives(lstOfUserStockView.get(0).getCurrentStock());
						branchWiseCurrencyInquiryDataTable.setFcAmount(lstOfUserStockView.get(0).getFcAmount());
					} else {
						branchWiseCurrencyInquiryDataTable.setPointTwoFives(BigDecimal.ZERO);
						branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
					}
				} else {
					branchWiseCurrencyInquiryDataTable.setPointTwoFives(BigDecimal.ZERO);
					branchWiseCurrencyInquiryDataTable.setFcAmount(BigDecimal.ZERO);
				}

				lstOfBranchWiseCurrencyInquiryDataTable.add(branchWiseCurrencyInquiryDataTable);

			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			return ;
		}
	}
	
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void getAllCountryBranchList() {
		countryBranchDataList.clear();
		CountryBranchDataTable countryBranchDataTable = new CountryBranchDataTable();

		countryBranchList = getCurrencyEnquiryService().getCountryBranchByCurrency();
		for (CountryBranch countryBranch : countryBranchList) {

			countryBranchDataTable = new CountryBranchDataTable();
			countryBranchDataTable.setCountryBranchName(countryBranch.getBranchName());
			countryBranchDataList.add(countryBranchDataTable);
		}
	}

	public void getAllDenominationList() {
		denominationDataList.clear();
		DenominationDataTable denominationDataTable = new DenominationDataTable();

		denominationList = getCurrencyEnquiryService().getDenomonationByCurrency(getCurrencyId());
		for (CurrencyWiseDenomination currencyWiseDenomination : denominationList) {

			denominationDataTable = new DenominationDataTable();
			denominationDataTable.setDenomination(currencyWiseDenomination.getDenominationDesc());
			denominationDataList.add(denominationDataTable);
		}
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public List<CurrencyMaster> getLstOfCurrency() {
		return lstOfCurrency;
	}

	public void setLstOfCurrency(List<CurrencyMaster> lstOfCurrency) {
		this.lstOfCurrency = lstOfCurrency;
	}

	public List<BranchWiseCurrencyInquiryDataTable> getLstOfBranchWiseCurrencyInquiryDataTable() {
		return lstOfBranchWiseCurrencyInquiryDataTable;
	}

	public void setLstOfBranchWiseCurrencyInquiryDataTable(List<BranchWiseCurrencyInquiryDataTable> lstOfBranchWiseCurrencyInquiryDataTable) {
		this.lstOfBranchWiseCurrencyInquiryDataTable = lstOfBranchWiseCurrencyInquiryDataTable;
	}

	public ICurrencyEnquiryService getCurrencyEnquiryService() {
		return currencyEnquiryService;
	}

	public void setCurrencyEnquiryService(ICurrencyEnquiryService currencyEnquiryService) {
		this.currencyEnquiryService = currencyEnquiryService;
	}

	public List<BranchWiseCurrencyStock> getLstOfUserStockView() {
		return lstOfUserStockView;
	}

	public void setLstOfUserStockView(List<BranchWiseCurrencyStock> lstOfUserStockView) {
		this.lstOfUserStockView = lstOfUserStockView;
	}

	public List<CountryBranch> getCountryBranchList() {
		return countryBranchList;
	}

	public void setCountryBranchList(List<CountryBranch> countryBranchList) {
		this.countryBranchList = countryBranchList;
	}

	public List<CountryBranchDataTable> getCountryBranchDataList() {
		return countryBranchDataList;
	}

	public void setCountryBranchDataList(List<CountryBranchDataTable> countryBranchDataList) {
		this.countryBranchDataList = countryBranchDataList;
	}

	public List<CurrencyWiseDenomination> getDenominationList() {
		return denominationList;
	}

	public void setDenominationList(List<CurrencyWiseDenomination> denominationList) {
		this.denominationList = denominationList;
	}

	public List<DenominationDataTable> getDenominationDataList() {
		return denominationDataList;
	}

	public void setDenominationDataList(List<DenominationDataTable> denominationDataList) {
		this.denominationDataList = denominationDataList;
	}

	public boolean isBooDataTable() {
		return booDataTable;
	}

	public void setBooDataTable(boolean booDataTable) {
		this.booDataTable = booDataTable;
	}

}
