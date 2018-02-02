package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.model.TreasuryDealDetail;
import com.amg.exchange.treasury.service.ISpecifySellingRateService;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("specifySellingRateBean")
@Scope("session")
public class SpecifySellingRateBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(SpecifySellingRateBean.class);

	private String approvedBy;
	private Date approvedDate;
	private String userName;
	private BigDecimal pkSpecialCustomer = null;
	private String errorMsg;
	private Boolean dialogDisplay=false;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public BigDecimal getPkSpecialCustomer() {
		return pkSpecialCustomer;
	}

	public void setPkSpecialCustomer(BigDecimal pkSpecialCustomer) {
		this.pkSpecialCustomer = pkSpecialCustomer;
	}

	List<SpecifySellingRateDataTable> specifySellingRateList = new ArrayList<SpecifySellingRateDataTable>();
	List<CustomerSpecialDealRequest> customerSpecialDealList = new ArrayList<CustomerSpecialDealRequest>();

	SessionStateManage sessionStateManage = new SessionStateManage();

	public List<SpecifySellingRateDataTable> getSpecifySellingRateList() {
		return specifySellingRateList;
	}

	public void setSpecifySellingRateList(
			List<SpecifySellingRateDataTable> specifySellingRateList) {
		this.specifySellingRateList = specifySellingRateList;
	}

	@Autowired
	ISpecifySellingRateService<T> specifySellingRateService;

	TreasuryDealDetail treasurydealdetails = new TreasuryDealDetail();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Retrieve The Records From EX_Special_Deal_Req table when dealId is not NULL
	public void fetchRecord() {
    setDialogDisplay( false);
		try {
			specifySellingRateList.clear();
			customerSpecialDealList = specifySellingRateService.getSpecialDealData(sessionStateManage.getCountryId());
			// specifySellingRateList = new
			for (CustomerSpecialDealRequest customerSpecialReq : customerSpecialDealList) {

				SpecifySellingRateDataTable specifySellingData = new SpecifySellingRateDataTable();

				if (customerSpecialReq.getApprovedBy() != null) {

					specifySellingData.setPk(customerSpecialReq.getCustomerSpecialDealReqId());
					specifySellingData.setCustomer(customerSpecialReq.getCustomerSpeacialDealReqCustomer().getFirstName());
					specifySellingData.setBank(customerSpecialReq.getCustomerSpeacialDealReqBankMaster().getBankFullName());
					specifySellingData.setProjectiondate(new SimpleDateFormat("dd/MM/yyyy").format(customerSpecialReq.getProjectionDate()));
					specifySellingData.setCurrency(customerSpecialReq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyName());
					specifySellingData.setFcAmount(customerSpecialReq.getForeignCurrencyAmount());
					specifySellingData.setValuedate(new SimpleDateFormat("dd/MM/yyyy").format(customerSpecialReq.getValidUpto()));
					specifySellingData.setBankId(customerSpecialReq.getCustomerSpeacialDealReqBankMaster().getBankId());
					specifySellingData.setCurrencyId(customerSpecialReq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId());
					specifySellingData.setCustomerId(customerSpecialReq.getCustomerSpeacialDealReqCustomer().getCustomerId());
					specifySellingData.setDocumentNo(customerSpecialReq.getDocumentNumber());

					if (customerSpecialReq.getAvarageRate() != null) {
						specifySellingData.setAvarageRate(customerSpecialReq.getAvarageRate());
					}

					if (customerSpecialReq.getSellRate() != null) {

						specifySellingData.setSellRate(customerSpecialReq.getSellRate());

					}

					if (customerSpecialReq.getFundingOption() != null) {
						specifySellingData.setFundingOption(customerSpecialReq.getFundingOption());
						
						if(customerSpecialReq.getDealId() != null && customerSpecialReq.getDealCompanyId() != null && customerSpecialReq.getDealFinanceYear() != null && customerSpecialReq.getDealApplicationNumber() != null){
							specifySellingData.setDisablefundingOption(true);
						}else{
							specifySellingData.setDisablefundingOption(false);
						}
					}
					//if (customerSpecialReq.getBuyRate() != null) {
						// specifySellingData.setEditable(true);

						List<TreasuryDealDetail> treasurydetaillist = specifySellingRateService.getBuyRate(customerSpecialReq.getCustomerSpecialDealReqId(),customerSpecialReq.getCustomerSpeacialDealReqBankMaster()
												.getBankId(),customerSpecialReq.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyId(), "PD");

                      if(treasurydetaillist.size()>0){
						for (TreasuryDealDetail treasury : treasurydetaillist) {

							specifySellingData.setBuyRate(treasury.getLocalExchangeRate());

							if (customerSpecialReq.getSellRate() != null) {
								specifySellingData.setDispWaterMark(true);
								specifySellingData.setReadOnlySellRate(false);

							} else {
								specifySellingData.setReadOnlySellRate(false);
								specifySellingData.setDispWaterMark(true);
							}

						}
                      }

					//}

					specifySellingRateList.add(specifySellingData);

				}
			}
		} catch(NullPointerException  e){ 
				setErrorMsg("Method:fetchRecord ");
				setDialogDisplay(true);
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			setDialogDisplay(true);
			RequestContext.getCurrentInstance().execute("csp.show();");
			}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "specifysellingrate.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/specifysellingrate.xhtml");
		} catch (IOException e) {
		LOG.info("redirect problem: "+e);
		setErrorMsg(e.getMessage());
		setDialogDisplay(true);

		RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public String clearCache() {

		specifySellingRateList.clear();

		return "specifySellingRate";

	}

	public void calculateAvarage(SpecifySellingRateDataTable specifySellingData) {
try{
		if (specifySellingData.getSellRate().compareTo(BigDecimal.ZERO) == 0) {
			specifySellingData.setSellRate(BigDecimal.ZERO);
		} else {
			if (specifySellingData.getSellRate().compareTo(specifySellingData.getBuyRate()) <= 0) {

				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("confirm.show();");
				specifySellingData.setSellRate(BigDecimal.ZERO);
				specifySellingData.setAvarageRate(BigDecimal.ZERO);

			} else {
				specifySellingData.setAvarageRate(GetRound.roundBigDecimal(specifySellingData.getBuyRate().add(specifySellingData.getSellRate()), 3) .divide(new BigDecimal(2)) );
			}
		}
}catch(NullPointerException  e){ 
	 
		setErrorMsg("Method:calculateAvarage ");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}

	}

	public void makeEditable(SpecifySellingRateDataTable specifyDataTable) {
		specifyDataTable.setEditable(true);
	}


	List<BigDecimal> listUpdate = new ArrayList<BigDecimal>();

	// To save sellrate and BuyRate the Records into the Customer Special Deal
	// Req table
	public void save() {
		try{
		boolean checksellrate = true;
		for (SpecifySellingRateDataTable specifySellingRateDataTable : specifySellingRateList) {
			if (specifySellingRateDataTable.getSellRate() != null) {
				checksellrate = true;
				break;
			}else{
				checksellrate = false;
			}
		}
		if (checksellrate) {
			 			specifySellingRateService.updateApprove(specifySellingRateList,
					sessionStateManage.getUserName());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("saved.show();");
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("succ.show();");
		}
		}catch(NullPointerException  e){ 
			 
			setErrorMsg("Method:save ");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void exit() throws IOException {
		setDialogDisplay(false);
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {

			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");

		} else {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/branchhome.xhtml");

		}
	}

	public void clickOnOK() throws IOException {

		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			specifySellingRateList.clear();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");

		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public Boolean getDialogDisplay() {
		return dialogDisplay;
	}

	public void setDialogDisplay(Boolean dialogDisplay) {
		this.dialogDisplay = dialogDisplay;
	}

}
