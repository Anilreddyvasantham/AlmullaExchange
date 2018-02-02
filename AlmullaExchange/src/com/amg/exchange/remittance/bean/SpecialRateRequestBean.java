package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.ForeignLocalCurrencyDenominationService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.SpecialRateRequest;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.remittance.service.ISpecialRateRequestService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.CurrencyOtherInformation;
import com.amg.exchange.treasury.model.Document;
import com.amg.exchange.treasury.service.IRoutingSetUpService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("specialRateRequestBean")
@Scope("session")
public class SpecialRateRequestBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(SpecialRateRequestBean.class);
	private SessionStateManage sessionStateManage = new SessionStateManage();

	private List<SpecialRateRequestDatatable> specialRatedT;
	private String errorMessage;
	private BigDecimal fundMinRateDis;
	private BigDecimal fundMaxRateDis;
	private BigDecimal searchCustomer;
	private BigDecimal searchBranch;
	private List<CountryBranch> countryBranch;
	
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IRoutingSetUpService<T> iroutingSetUpService;

	@Autowired
	IPersonalRemittanceService personelService;

	@Autowired
	ISpecialRateRequestService specialRateService;

	@Autowired 
	ISpecialCustomerDealRequestService<T> specialCustomerReqSer;

	@Autowired
	ForeignLocalCurrencyDenominationService<T> foreignLocalCurrencyDenominationService;


	
	
	public List<CountryBranch> getCountryBranch() {
		return countryBranch;
	}
	public void setCountryBranch(List<CountryBranch> countryBranch) {
		this.countryBranch = countryBranch;
	}
	public BigDecimal getSearchCustomer() {
		return searchCustomer;
	}
	public void setSearchCustomer(BigDecimal searchCustomer) {
		this.searchCustomer = searchCustomer;
	}
	public BigDecimal getSearchBranch() {
		return searchBranch;
	}
	public void setSearchBranch(BigDecimal searchBranch) {
		this.searchBranch = searchBranch;
	}
	public List<SpecialRateRequestDatatable> getSpecialRatedT() {
		return specialRatedT;
	}
	public void setSpecialRatedT(List<SpecialRateRequestDatatable> specialRatedT) {
		this.specialRatedT = specialRatedT;
	}

	public BigDecimal getFundMinRateDis() {
		return fundMinRateDis;
	}
	public void setFundMinRateDis(BigDecimal fundMinRateDis) {
		this.fundMinRateDis = fundMinRateDis;
	}

	public BigDecimal getFundMaxRateDis() {
		return fundMaxRateDis;
	}
	public void setFundMaxRateDis(BigDecimal fundMaxRateDis) {
		this.fundMaxRateDis = fundMaxRateDis;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public void getSpecialRateRequestDatatable() {
		try{
			List<SpecialRateRequest> list = specialRateService.getSpecialRateRequestList();
			List<SpecialRateRequestDatatable> specialRateList =null;

			if (list.size() != 0) {
				setSpecialRatedT(null);
				specialRateList =new ArrayList<SpecialRateRequestDatatable>();
				for (SpecialRateRequest specialRate : list) {

					SpecialRateRequestDatatable specialDt = new SpecialRateRequestDatatable();

					specialDt.setSpecialRateReqId(specialRate.getSpecialRateRequestId());

					specialDt.setCustomerId(specialRate.getFsCustomer().getCustomerId());
					if(specialRate.getFsCustomer().getCustomerId()!=null){
						specialDt.setCustomerName(generalService.getCustomerNameCustomerId(specialRate.getFsCustomer().getCustomerId()) );
					}
					specialDt.setBankName(specialRate.getFsBankMaster().getBankFullName());
					specialDt.setBeneficarybankId(specialRate.getFsBankMaster().getBankId());
					specialDt.setDocumentCode(specialRate.getFsDocument().getDocumentID());
					specialDt.setBankName(specialRate.getFsBankMaster().getBankFullName());
					specialDt.setDocumentFinancialYear(specialRate.getFsFinanceYear().getFinancialYear());
					specialDt.setDocumentFinancialYearID(specialRate.getFsFinanceYear().getFinancialYearID());

					specialDt.setCompanyCode(specialRate.getCompanyMaster().getCompanyCode().toString());
					specialDt.setCompanyId(specialRate.getCompanyMaster().getCompanyId());
					if(specialRate.getCompanyMaster().getCompanyId()!=null){
						specialDt.setCompanyName(specialCustomerReqSer.getCompanyNameForUpdate(specialRate.getCompanyMaster().getCompanyId()));
					}

					if(specialRate.getFcAmount()!=null && specialRate.getFsCurrencyMaster().getCurrencyId()!=null){
						BigDecimal amount=GetRound.roundBigDecimal(specialRate.getFcAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(specialRate.getFsCurrencyMaster().getCurrencyId()));
						specialDt.setFcAmount(amount);
					}

					specialDt.setSellRate(specialRate.getSellRate());

					specialDt.setCurrencyName(specialRate.getFsCurrencyMaster().getCurrencyName());
					specialDt.setCurrencyId(specialRate.getFsCurrencyMaster().getCurrencyId());
					specialDt.setIsActive(specialRate.getIsActive());
					specialDt.setDocumentNo(specialRate.getDocumentNo());
					specialDt.setCreatedBy(specialRate.getCreatedBy());
					specialDt.setCreatedDate(specialRate.getCreatedDate());
					specialDt.setModifiedBy(specialRate.getModifiedBy());
					specialDt.setModifiedDate(specialRate.getModifiedDate());
					specialDt.setApplicationCountryId(specialRate.getApplicationCountryId());

					specialDt.setBeneficiaryId(specialRate.getBeneficiaryId());
					specialDt.setBeneficiaryBankId(specialRate.getBeneficiaryBankId());
					specialDt.setBeneficiaryBankBranchId(specialRate.getBeneficiaryBankBranchId());

					specialRateList.add(specialDt);

				}
			}

			if(specialRateList!=null){
				setSpecialRatedT(specialRateList);
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: getSpecialRateRequestDatatable "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// method called from menubar - navigation
	public void specialRateNavigation() {
		setSpecialRatedT(null);
		setFundMinRateDis(null);
		setFundMaxRateDis(null);
		setCountryBranch(null);
		setSearchCustomer(null);
		setSearchBranch(null);
		try {
			List<CountryBranch> lstCountryBranch=LoadCountryBranch();
			setCountryBranch(lstCountryBranch);
			getSpecialRateRequestDatatable();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "specialRateRequest.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/specialRateRequest.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// exit to Home Page
	public void exit() {
		try {
			setFundMinRateDis(null);
			setFundMaxRateDis(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//update the sellrate 
	public void updateRecords() {
		try{	
			Boolean checkUserName = false; 
			int size=0;

			if (getSpecialRatedT()!=null) {
				List<SpecialRateRequestDatatable> specialRatedT = getSpecialRatedT();
				int dataTableSize = specialRatedT.size();
				for (SpecialRateRequestDatatable specialData : specialRatedT) {

					if(specialData.getSellRate()!=null){
						if((specialData.getModifiedBy() == null ? specialData.getCreatedBy() : specialData.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
							checkUserName = true; 
							break;
						}else{
							checkUserName = false; 
						}
					}else{
						size = size+1;
					}
				}

				if(dataTableSize==size){
					RequestContext.getCurrentInstance().execute("pleaseSelect.show();");
					return;
				}else if(checkUserName){
					RequestContext.getCurrentInstance().execute("notApproved.show();");
					return;
				}else{
					for (SpecialRateRequestDatatable specialreq : specialRatedT) {

						if(specialreq.getSellRate()!=null){
							SpecialRateRequest specialRateReqBean = new SpecialRateRequest();

							specialRateReqBean.setSpecialRateRequestId(specialreq.getSpecialRateReqId());

							specialRateReqBean.setSellRate(specialreq.getSellRate());

							Customer custom = new Customer();
							custom.setCustomerId(specialreq.getCustomerId());
							specialRateReqBean.setFsCustomer(custom);

							Document document = new Document();
							document.setDocumentID(specialreq.getDocumentCode());
							specialRateReqBean.setFsDocument(document);

							BankMaster bankMaster = new BankMaster();
							bankMaster.setBankId(specialreq.getBeneficarybankId());
							specialRateReqBean.setFsBankMaster(bankMaster);

							CurrencyMaster currency = new CurrencyMaster();
							currency.setCurrencyId(specialreq.getCurrencyId());
							specialRateReqBean.setFsCurrencyMaster(currency);

							CompanyMaster companyMaster = new CompanyMaster();
							companyMaster.setCompanyId(specialreq.getCompanyId());
							specialRateReqBean.setCompanyMaster(companyMaster);

							UserFinancialYear userFinancialYear1 = new UserFinancialYear();
							userFinancialYear1.setFinancialYearID(specialreq.getDocumentFinancialYearID());

							specialRateReqBean.setFsFinanceYear(userFinancialYear1);

							specialRateReqBean.setFcAmount(specialreq.getFcAmount());

							specialRateReqBean.setApplicationCountryId(specialreq.getApplicationCountryId());

							specialRateReqBean.setDocumentNo(specialreq.getDocumentNo());

							specialRateReqBean.setCreatedBy(specialreq.getCreatedBy());
							specialRateReqBean.setCreatedDate(specialreq.getCreatedDate());
							specialRateReqBean.setModifiedBy(specialreq.getModifiedBy());
							specialRateReqBean.setModifiedDate(specialreq.getModifiedDate());
							specialRateReqBean.setIsActive(Constants.Yes);

							specialRateReqBean.setApprovedBy(sessionStateManage.getUserName());
							specialRateReqBean.setApprovedDate(new Date());

							specialRateReqBean.setBeneficiaryId(specialreq.getBeneficiaryId());
							specialRateReqBean.setBeneficiaryBankId(specialreq.getBeneficiaryBankId());
							specialRateReqBean.setBeneficiaryBankBranchId(specialreq.getBeneficiaryBankBranchId());

							specialRateService.updateRecord(specialRateReqBean);
						}

					}
					RequestContext.getCurrentInstance().execute("complete.show();");
				}

			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name: updateRecords  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void checkMinMax(SpecialRateRequestDatatable specialRateRequestDatatable)
	{
		try{
			setFundMinRateDis(null);
			setFundMaxRateDis(null);
			BigDecimal sellRate= specialRateRequestDatatable.getSellRate();
			if(sellRate!=null)
			{
				List<CurrencyOtherInformation> lstCurrencyOtherInformation=specialRateService.getMinMaxRate(specialRateRequestDatatable.getCurrencyId());

				if(lstCurrencyOtherInformation!=null && lstCurrencyOtherInformation.size()>0)
				{
					CurrencyOtherInformation currencyOtherInformation=lstCurrencyOtherInformation.get(0);

					BigDecimal fundMaxRate= currencyOtherInformation.getFundMaxRate();
					BigDecimal fundMinRate=currencyOtherInformation.getFundMinRate();
					setFundMinRateDis(fundMinRate);
					setFundMaxRateDis(fundMaxRate);
					if(sellRate.compareTo(fundMinRate)>=0)
					{
						if(!(sellRate.compareTo(fundMaxRate)<=0))
						{
							specialRateRequestDatatable.setSellRate(null);
							RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
							return;
						}

					}else
					{
						specialRateRequestDatatable.setSellRate(null);
						RequestContext.getCurrentInstance().execute("minmaxcheck.show();");
						return;
					}
				}
			}
		} catch(NullPointerException  e){
			setErrorMessage("Method Name: updateRecords  "+e.getMessage());
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void searhRecords()
	{
		if(getSearchCustomer()!=null ||  getSearchBranch()!=null)
		{
			try{
				List<SpecialRateRequest> list = specialRateService.fetchAllDetailsFromCustomerAndBranch(getSearchCustomer(), getSearchBranch());
				List<SpecialRateRequestDatatable> specialRateList =null;
				setSpecialRatedT(null);
				if (list.size() != 0) {
					
					specialRateList =new ArrayList<SpecialRateRequestDatatable>();
					for (SpecialRateRequest specialRate : list) {

						SpecialRateRequestDatatable specialDt = new SpecialRateRequestDatatable();

						specialDt.setSpecialRateReqId(specialRate.getSpecialRateRequestId());

						specialDt.setCustomerId(specialRate.getFsCustomer().getCustomerId());
						if(specialRate.getFsCustomer().getCustomerId()!=null){
							specialDt.setCustomerName(generalService.getCustomerNameCustomerId(specialRate.getFsCustomer().getCustomerId()) );
						}
						specialDt.setBankName(specialRate.getFsBankMaster().getBankFullName());
						specialDt.setBeneficarybankId(specialRate.getFsBankMaster().getBankId());
						specialDt.setDocumentCode(specialRate.getFsDocument().getDocumentID());
						specialDt.setBankName(specialRate.getFsBankMaster().getBankFullName());
						specialDt.setDocumentFinancialYear(specialRate.getFsFinanceYear().getFinancialYear());
						specialDt.setDocumentFinancialYearID(specialRate.getFsFinanceYear().getFinancialYearID());

						specialDt.setCompanyCode(specialRate.getCompanyMaster().getCompanyCode().toString());
						specialDt.setCompanyId(specialRate.getCompanyMaster().getCompanyId());
						if(specialRate.getCompanyMaster().getCompanyId()!=null){
							specialDt.setCompanyName(specialCustomerReqSer.getCompanyNameForUpdate(specialRate.getCompanyMaster().getCompanyId()));
						}

						if(specialRate.getFcAmount()!=null && specialRate.getFsCurrencyMaster().getCurrencyId()!=null){
							BigDecimal amount=GetRound.roundBigDecimal(specialRate.getFcAmount(), foreignLocalCurrencyDenominationService.getDecimalPerCurrency(specialRate.getFsCurrencyMaster().getCurrencyId()));
							specialDt.setFcAmount(amount);
						}

						specialDt.setSellRate(specialRate.getSellRate());

						specialDt.setCurrencyName(specialRate.getFsCurrencyMaster().getCurrencyName());
						specialDt.setCurrencyId(specialRate.getFsCurrencyMaster().getCurrencyId());
						specialDt.setIsActive(specialRate.getIsActive());
						specialDt.setDocumentNo(specialRate.getDocumentNo());
						specialDt.setCreatedBy(specialRate.getCreatedBy());
						specialDt.setCreatedDate(specialRate.getCreatedDate());
						specialDt.setModifiedBy(specialRate.getModifiedBy());
						specialDt.setModifiedDate(specialRate.getModifiedDate());
						specialDt.setApplicationCountryId(specialRate.getApplicationCountryId());

						specialDt.setBeneficiaryId(specialRate.getBeneficiaryId());
						specialDt.setBeneficiaryBankId(specialRate.getBeneficiaryBankId());
						specialDt.setBeneficiaryBankBranchId(specialRate.getBeneficiaryBankBranchId());

						specialRateList.add(specialDt);

					}
				}

				if(specialRateList!=null){
					setSpecialRatedT(specialRateList);
				}
			}catch(NullPointerException  e){
				setErrorMessage("Method Name: searhRecords "+e.getMessage());
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
			catch(Exception e){
				setErrorMessage(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		}

		
	}
	Map<BigDecimal, String> countryBranchList = new HashMap<BigDecimal, String>();
	public List<CountryBranch> LoadCountryBranch() {
		
		List<CountryBranch> countryBranch = new ArrayList<CountryBranch>();
		countryBranch.addAll(generalService.getBranchDetails(sessionStateManage.getCountryId()));
		for (CountryBranch countryBranch1 : countryBranch) {
			countryBranchList.put(countryBranch1.getCountryBranchId(), countryBranch1.getBranchName());
		}
		
		return countryBranch;
	}
} 
