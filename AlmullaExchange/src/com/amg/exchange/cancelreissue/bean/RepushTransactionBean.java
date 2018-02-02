package com.amg.exchange.cancelreissue.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.primefaces.model.SelectableDataModel;
import com.amg.exchange.beneficiary.model.BanksView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.cancelreissue.model.ViewRepushBankTrnxList;
import com.amg.exchange.cancelreissue.service.ICancelReissueService;
import com.amg.exchange.common.dto.BankMasterDTO;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.foreigncurrency.model.UserFinancialYear;
import com.amg.exchange.foreigncurrency.service.IForeignCurrencyPurchaseService;
import com.amg.exchange.treasury.bean.BankCountryPopulationBean;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component(value = "repushBean")
@Scope("session")
public class RepushTransactionBean<T> implements Serializable {
	Logger log = Logger.getLogger(RepushTransactionBean.class);

	private SessionStateManage session=new SessionStateManage();

	private String errMsg;
	private BigDecimal benifisCountryId;
	private BigDecimal serviceGroupId;
	private BigDecimal  benifisBankId;
	private String benifisBankCode;
	private BigDecimal finaceYear;
	private BigDecimal documentNumber ;
	private BigDecimal customerId ;
	String errorMessage;

	//VW_EX_CHANNEL_BANKS

	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<BankCountryPopulationBean> bankCountryList = new ArrayList<BankCountryPopulationBean>();
	private List<BanksView> lstBankFromView = new ArrayList<BanksView>();
	private List<ViewRepushBankTrnxList> viewRepushBankTrnxList = new ArrayList<ViewRepushBankTrnxList>();
	private List<RepushBankTrnxDataTable> repushBankTrnxDataTableList = new ArrayList<RepushBankTrnxDataTable>();
	private List<RepushBankTrnxDataTable> repushBankTrnxDataTableSelectedList = new ArrayList<RepushBankTrnxDataTable>();
	

	@Autowired 
	IGeneralService  generalService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	
	@Autowired
	IBeneficaryCreation beneficaryCreation;
	
	@Autowired
	 IForeignCurrencyPurchaseService<T> foreignCurrencyPurchaseService;
	
	@Autowired
	ICancelReissueService<T> cancelReissueSevice;
	
	
	public void pageNavigationToRepushTrnx(){
		clearAll();
		//getApplicationYearFromdb();
		
		//lstCountry = generalService.getCountryList(sessionmanage.getLanguageId());
		
	 bankCountryList = fundEstimationService.getBankContryFromView(session.getCountryId());

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankRepushTrnx.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../cancelreissue/bankRepushTrnx.xhtml");
		} catch (Exception e) {
			log.error("Error in Page Navigation to cancelandreissue");
		}

	}
	
	
	// populate clearing all and fetch bank account type
		public void popbanklist() {
			if(lstBankFromView != null || !lstBankFromView.isEmpty()){
				lstBankFromView.clear();
			}
			
			
			
			
			List<BanksView> lstBanksView = beneficaryCreation.getBankListFromView(session.getCountryId(), getBenifisCountryId(),getServiceGroupId());
			if(lstBanksView.size() != 0){
				lstBankFromView.addAll(lstBanksView);
			}
			
		}
	
		
	public void searchRejectedTrnxList(){
			
				viewRepushBankTrnxList.clear();
				repushBankTrnxDataTableList.clear();
				repushBankTrnxDataTableSelectedList.clear();
				repushBankTrnxDataTableSelectedList = new ArrayList<RepushBankTrnxDataTable>();
				System.out.println("benifisBankCode :"+benifisBankCode+"\t getBenifisBankCode :"+getBenifisBankCode()+"\t Doc No :"+getDocumentNumber());
				
			HashMap<String, String> inputParameters = new HashMap<String, String>();
			inputParameters.put("DOCFYR", getFinaceYear().toString());
			inputParameters.put("BNKCODE", getBenifisBankCode());
			if(getDocumentNumber()!=null){
				inputParameters.put("DOC_NO",getDocumentNumber().toString());
			}
			
			viewRepushBankTrnxList = cancelReissueSevice.getBankRejectedTrnxListFromView(inputParameters);
			if(viewRepushBankTrnxList !=null && viewRepushBankTrnxList.size()>0){
				
				for(ViewRepushBankTrnxList viewRepushBankTrnx:viewRepushBankTrnxList){
					RepushBankTrnxDataTable repushBankTrnxDataTable= new RepushBankTrnxDataTable();
					repushBankTrnxDataTable.setDocfyr(viewRepushBankTrnx.getDocfyr());
					repushBankTrnxDataTable.setDocNumber(viewRepushBankTrnx.getDocNumber());
					repushBankTrnxDataTable.setCutomerRefernce(viewRepushBankTrnx.getCutomerRefernce());
					repushBankTrnxDataTable.setCutomerName(viewRepushBankTrnx.getCutomerName());
					repushBankTrnxDataTable.setBeneName(viewRepushBankTrnx.getBeneName());
					repushBankTrnxDataTable.setRemarks(viewRepushBankTrnx.getRemarks()==null?"":viewRepushBankTrnx.getRemarks().trim());
					repushBankTrnxDataTable.setAmountExch(viewRepushBankTrnx.getAmountExch());
					repushBankTrnxDataTable.setAmountTrans(viewRepushBankTrnx.getAmountTrans());
					repushBankTrnxDataTable.setDocDate(viewRepushBankTrnx.getDocDate());
					repushBankTrnxDataTable.setSelectedStatus(false);
					repushBankTrnxDataTable.setBankCode(viewRepushBankTrnx.getBankCode());
					repushBankTrnxDataTableList.add(repushBankTrnxDataTable);
				}
				
			}
			
		}
		
	// exit
		public void exit() throws IOException{
			clearAll();
			if(session.getRoleId().equalsIgnoreCase("1")){
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			}else{
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		}
	
		
		public BigDecimal getFinaceYear() {
			try {
				List<UserFinancialYear> financialYearList = foreignCurrencyPurchaseService.getUserFinancialYear(new Date());
				log.info("financialYearList :" + financialYearList.size());
				if (financialYearList != null){
					finaceYear = financialYearList.get(0).getFinancialYear();
					setFinaceYear(finaceYear);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return finaceYear;
		}
		
		
		
		public void updateSelectedRecords(){
			try{
				
				HashMap<String, String> inputParametrs = new HashMap<String, String>(); 
				
				inputParametrs.put("USER_NAME", session.getUserName());
				System.out.println("repushBankTrnxDataTableSelectedList :"+repushBankTrnxDataTableSelectedList.size());
				
					if(repushBankTrnxDataTableSelectedList!=null && repushBankTrnxDataTableSelectedList.size()>0){
						Boolean resultType = cancelReissueSevice.updateBankRejectedTrnxList(repushBankTrnxDataTableSelectedList, inputParametrs);
						if(resultType){
							RequestContext.getCurrentInstance().execute("complete.show();");
						}
					}else{
						setErrorMessage("Please select atleast One Record");
						RequestContext.getCurrentInstance().execute("showDailog.show();");
					}
				
			}catch(Exception ex){
				setErrorMessage("Exception While Saving :" +ex.getMessage());
				RequestContext.getCurrentInstance().execute("showDailog.show();");
			}
		}
		
		
		public void resetForm(){
			setBenifisCountryId(null);
			setBenifisBankCode(null);
			setDocumentNumber(null);
			viewRepushBankTrnxList.clear();
			repushBankTrnxDataTableList.clear();
		
		}

	public void clearAll(){
		bankCountryList.clear();
		setBenifisCountryId(null);
		setBenifisBankCode(null);
		setDocumentNumber(null);
		viewRepushBankTrnxList.clear();
		repushBankTrnxDataTableList.clear();
	}

	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}


	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<BankCountryPopulationBean> getBankCountryList() {
		return bankCountryList;
	}

	public void setBankCountryList(List<BankCountryPopulationBean> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}


	public List<BanksView> getLstBankFromView() {
		return lstBankFromView;
	}


	public void setLstBankFromView(List<BanksView> lstBankFromView) {
		this.lstBankFromView = lstBankFromView;
	}


	public BigDecimal getBenifisCountryId() {
		return benifisCountryId;
	}


	public void setBenifisCountryId(BigDecimal benifisCountryId) {
		this.benifisCountryId = benifisCountryId;
	}


	public BigDecimal getServiceGroupId() {
		return serviceGroupId;
	}


	public void setServiceGroupId(BigDecimal serviceGroupId) {
		this.serviceGroupId = serviceGroupId;
	}


	public BigDecimal getBenifisBankId() {
		return benifisBankId;
	}


	public void setBenifisBankId(BigDecimal benifisBankId) {
		this.benifisBankId = benifisBankId;
	}


	public List<ViewRepushBankTrnxList> getViewRepushBankTrnxList() {
		return viewRepushBankTrnxList;
	}


	public void setViewRepushBankTrnxList(List<ViewRepushBankTrnxList> viewRepushBankTrnxList) {
		this.viewRepushBankTrnxList = viewRepushBankTrnxList;
	}


	public String getBenifisBankCode() {
		return benifisBankCode;
	}


	public void setBenifisBankCode(String benifisBankCode) {
		this.benifisBankCode = benifisBankCode;
	}


	public void setFinaceYear(BigDecimal finaceYear) {
		this.finaceYear = finaceYear;
	}


	public List<RepushBankTrnxDataTable> getRepushBankTrnxDataTableList() {
		return repushBankTrnxDataTableList;
	}


	public void setRepushBankTrnxDataTableList(List<RepushBankTrnxDataTable> repushBankTrnxDataTableList) {
		this.repushBankTrnxDataTableList = repushBankTrnxDataTableList;
	}


	public String getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}


	public List<RepushBankTrnxDataTable> getRepushBankTrnxDataTableSelectedList() {
		return repushBankTrnxDataTableSelectedList;
	}


	public void setRepushBankTrnxDataTableSelectedList(List<RepushBankTrnxDataTable> repushBankTrnxDataTableSelectedList) {
		this.repushBankTrnxDataTableSelectedList = repushBankTrnxDataTableSelectedList;
	}


	public BigDecimal getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(BigDecimal documentNumber) {
		this.documentNumber = documentNumber;
	}


	public BigDecimal getCustomerId() {
		return customerId;
	}


	public void setCustomerId(BigDecimal customerId) {
		this.customerId = customerId;
	}
	
	
}
