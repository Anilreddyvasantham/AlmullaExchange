package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.model.CashRate;
import com.amg.exchange.remittance.model.UnApprovalCashRate;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.ICashRateService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashRateBean")
@Scope("session")
public class CashRateBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log=Logger.getLogger(CashRateBean.class);

	private BigDecimal unAppCashRateId;
	private BigDecimal appCountryId;
	private BigDecimal baseCurrencyId;
	private String baseCurrencyCode;
	private BigDecimal alternateCurrencyId;
	private String alternateCurrencyCode;
	private BigDecimal countryBranchId;
	private BigDecimal saleMinRate;
	private BigDecimal saleMaxRate;
	private BigDecimal buyRateMin;
	private BigDecimal buyRateMax;
	private String locCode;
	private Date uploadDate;
	private String isActive;
	private Date createdDate;
	private String createdBy;
	private Date modifiedDate;
	private String modifiedBy;
	private Date approvedDate;
	private String approvedBy;
	private String remarks;
	private String baseCurrencyDescription;
	private String altenativeCurrencyDescrption;
	private String countryBranchName;

	private Boolean booEdit = false;
	private boolean isdisable = false;

	private String dynamicLabelForActivateDeactivate;

	private Boolean booInputPanel = false;
	private Boolean booDataTablePanel = false;
	
	private Boolean editClicked=false;

	SessionStateManage sessionStateManage = new SessionStateManage();

	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	List<CountryBranch> lstofCountryBranch = new ArrayList<CountryBranch>();

	private List<CashRateDataTableBean> lstFromUser = new CopyOnWriteArrayList<CashRateDataTableBean>();
	private List<CashRateDataTableBean> lstFromUserView = new ArrayList<CashRateDataTableBean>();

	@Autowired
	IGeneralService<T> iGeneralService;

	@Autowired
	ICashRateService cashRateService;

	public List<CashRateDataTableBean> getLstFromUserView() {
		return lstFromUserView;
	}

	public void setLstFromUserView(List<CashRateDataTableBean> lstFromUserView) {
		this.lstFromUserView = lstFromUserView;
	}

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public BigDecimal getUnAppCashRateId() {
		return unAppCashRateId;
	}

	public void setUnAppCashRateId(BigDecimal unAppCashRateId) {
		this.unAppCashRateId = unAppCashRateId;
	}

	public BigDecimal getAppCountryId() {
		return appCountryId;
	}

	public void setAppCountryId(BigDecimal appCountryId) {
		this.appCountryId = appCountryId;
	}

	public BigDecimal getBaseCurrencyId() {
		return baseCurrencyId;
	}

	public void setBaseCurrencyId(BigDecimal baseCurrencyId) {
		this.baseCurrencyId = baseCurrencyId;
	}

	public List<CountryBranch> getLstofCountryBranch() {
		  try{
		lstofCountryBranch = getiGeneralService().getBranchDetails(sessionStateManage.getCountryId());
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		  }
		return lstofCountryBranch;
	}

	public void setLstofCountryBranch(List<CountryBranch> lstofCountryBranch) {
		this.lstofCountryBranch = lstofCountryBranch;
	}

	public String getBaseCurrencyCode() {
		  try{
		 List<CurrencyMaster> lstCurrencyMaster=getiGeneralService().getCountryCurrencyList(sessionStateManage.getCountryId());
		 if(lstCurrencyMaster.size()>0)
		 {
			 CurrencyMaster currencyMaster=lstCurrencyMaster.get(0);
			 
			 baseCurrencyCode=currencyMaster.getCurrencyCode();
		 }
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		  }
		return baseCurrencyCode;
	}

	public void setBaseCurrencyCode(String baseCurrencyCode) {
		this.baseCurrencyCode = baseCurrencyCode;
	}

	public BigDecimal getAlternateCurrencyId() {
		return alternateCurrencyId;
	}

	public void setAlternateCurrencyId(BigDecimal alternateCurrencyId) {
		this.alternateCurrencyId = alternateCurrencyId;
	}

	public String getAlternateCurrencyCode() {
		return alternateCurrencyCode;
	}

	public void setAlternateCurrencyCode(String alternateCurrencyCode) {
		this.alternateCurrencyCode = alternateCurrencyCode;
	}

	public ICashRateService getCashRateService() {
		return cashRateService;
	}

	public void setCashRateService(ICashRateService cashRateService) {
		this.cashRateService = cashRateService;
	}

	public BigDecimal getCountryBranchId() {
		return countryBranchId;
	}

	public void setCountryBranchId(BigDecimal countryBranchId) {
		this.countryBranchId = countryBranchId;
	}

	public BigDecimal getSaleMinRate() {
		return saleMinRate;
	}

	public void setSaleMinRate(BigDecimal saleMinRate) {
		this.saleMinRate = saleMinRate;
	}

	public BigDecimal getSaleMaxRate() {
		return saleMaxRate;
	}

	public void setSaleMaxRate(BigDecimal saleMaxRate) {
		this.saleMaxRate = saleMaxRate;
	}

	public BigDecimal getBuyRateMin() {
		return buyRateMin;
	}

	public void setBuyRateMin(BigDecimal buyRateMin) {
		this.buyRateMin = buyRateMin;
	}

	public BigDecimal getBuyRateMax() {
		return buyRateMax;
	}

	public void setBuyRateMax(BigDecimal buyRateMax) {
		this.buyRateMax = buyRateMax;
	}

	public String getLocCode() {
		return locCode;
	}

	public void setLocCode(String locCode) {
		this.locCode = locCode;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBaseCurrencyDescription() {
		return baseCurrencyDescription;
	}

	public void setBaseCurrencyDescription(String baseCurrencyDescription) {
		this.baseCurrencyDescription = baseCurrencyDescription;
	}

	public String getAltenativeCurrencyDescrption() {
		return altenativeCurrencyDescrption;
	}

	public void setAltenativeCurrencyDescrption(String altenativeCurrencyDescrption) {
		this.altenativeCurrencyDescrption = altenativeCurrencyDescrption;
	}

	public String getCountryBranchName() {
		return countryBranchName;
	}

	public void setCountryBranchName(String countryBranchName) {
		this.countryBranchName = countryBranchName;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Boolean getBooInputPanel() {
		return booInputPanel;
	}

	public void setBooInputPanel(Boolean booInputPanel) {
		this.booInputPanel = booInputPanel;
	}

	public Boolean getBooDataTablePanel() {
		return booDataTablePanel;
	}

	public void setBooDataTablePanel(Boolean booDataTablePanel) {
		this.booDataTablePanel = booDataTablePanel;
	}

	public List<CurrencyMaster> getListCurrency() {
		return listCurrency = cashRateService.getCurrencyList(new BigDecimal(sessionStateManage.getCurrencyId()));
	}

	public void setListCurrency(List<CurrencyMaster> listCurrency) {
		this.listCurrency = listCurrency;
	}

	public Boolean getBooEdit() {
		return booEdit;
	}

	public void setBooEdit(Boolean booEdit) {
		this.booEdit = booEdit;
	}

	public boolean isIsdisable() {
		return isdisable;
	}

	public void setIsdisable(boolean isdisable) {
		this.isdisable = isdisable;
	}

	public List<CashRateDataTableBean> getLstFromUser() {
		return lstFromUser;
	}

	public void setLstFromUser(List<CashRateDataTableBean> lstFromUser) {
		this.lstFromUser = lstFromUser;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "cashrateentry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/cashrateentry.xhtml");
			setBooInputPanel(true);
			setBooDataTablePanel(false);
			lstFromUser.clear();
			clearAllFields();
			lstFromUserView.clear();
		}catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }
	}

	public void pageNavigationApprove() {
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/cashrateapproval.xhtml");
			setBooInputPanel(false);
			setBooDataTablePanel(true);
			approveView();
		} catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }
	}

	public void addToDataTable() {
		  if(lstFromUser.size()==0){
		try{
				  
		CashRateDataTableBean cashRateDataTableBean = new CashRateDataTableBean();

		if (getUnAppCashRateId() == null) {
			cashRateDataTableBean.setCreatedBy(sessionStateManage.getUserName());
			cashRateDataTableBean.setCreatedDate(new Date());
			cashRateDataTableBean.setModifiedBy(null);
			cashRateDataTableBean.setModifiedDate(null);
			cashRateDataTableBean.setApprovedBy(null);
			cashRateDataTableBean.setApprovedDate(null);
			cashRateDataTableBean.setIsActive(Constants.U);

		} else {
			cashRateDataTableBean.setCashRatePk(getUnAppCashRateId());
			cashRateDataTableBean.setCreatedBy(getCreatedBy());
			cashRateDataTableBean.setCreatedDate(getCreatedDate());
			cashRateDataTableBean.setModifiedBy(sessionStateManage.getUserName());
			cashRateDataTableBean.setModifiedDate(new Date());
			cashRateDataTableBean.setIsActive(Constants.U);
			cashRateDataTableBean.setApprovedBy(null);
			cashRateDataTableBean.setApprovedDate(null);

		}
		cashRateDataTableBean.setApplicationCountryId(sessionStateManage.getCountryId());

		cashRateDataTableBean.setAltenateCurrencyId(getAlternateCurrencyId());
		cashRateDataTableBean.setAltenativeCurrencyDescrption(cashRateService.getQuoteName(getAlternateCurrencyId()));
		cashRateDataTableBean.setAltenateCurrencyCode(cashRateService.getCurrencyCode(getAlternateCurrencyId()));

		cashRateDataTableBean.setBaseCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
		cashRateDataTableBean.setBaseCurrencyCode(cashRateService.getCurrencyCode(new BigDecimal(sessionStateManage.getCurrencyId())));

		cashRateDataTableBean.setBaseCurrencyDescription(cashRateService.getQuoteName(new BigDecimal(sessionStateManage.getCurrencyId())));
		
		if(getCountryBranchId()!=null)
		{
			cashRateDataTableBean.setCountryBranchId(getCountryBranchId());
			List<CountryBranch> countryBranchList = cashRateService.getCountryBranchList(getCountryBranchId());
			if (countryBranchList != null) {
				if(countryBranchList.get(0).getBranchId() != null){
				cashRateDataTableBean.setLocCode(countryBranchList.get(0).getBranchId().toString());
				}
				cashRateDataTableBean.setBranchDescriprion(countryBranchList.get(0).getBranchName());

			}
		}
		

		cashRateDataTableBean.setUploadDate(new Date());

		cashRateDataTableBean.setMaxBuyRate(getBuyRateMax());
		cashRateDataTableBean.setMinBuyRate(getBuyRateMin());
		cashRateDataTableBean.setMaxSellRate(getSaleMaxRate());
		cashRateDataTableBean.setMinSellRate(getSaleMinRate());
		setBooInputPanel(true);
		setBooDataTablePanel(true);
	List<UnApprovalCashRate> unappList	=cashRateService.getUnApprovalCashRateWithoutBranch( getAlternateCurrencyId(),  new BigDecimal(sessionStateManage.getCurrencyId()),  sessionStateManage.getCountryId(),  getCountryBranchId());
if(unappList.size()==1&&getEditClicked()){
		lstFromUser.add(cashRateDataTableBean);
		if (getUnAppCashRateId() == null)
		{
			lstFromUserView.add(cashRateDataTableBean);
		}
}else if(unappList.size()==0){
	lstFromUser.add(cashRateDataTableBean);
	if (getUnAppCashRateId() == null)
	{
		lstFromUserView.add(cashRateDataTableBean);
	}
}else{	
	setBooInputPanel( true);
	setBooDataTablePanel(false);
	clearAllFields();
	RequestContext.getCurrentInstance().execute("duplicaterecord.show();");
	return;
	
}
		clearAllFields();
		  }catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	  }
		  }else{
			  boolean booAdd=false;
			  for(CashRateDataTableBean cashRateDataTableBeanobj1:lstFromUser){
			
				  if(cashRateDataTableBeanobj1.getCountryBranchId().toPlainString().equalsIgnoreCase(getCountryBranchId().toPlainString())&&cashRateDataTableBeanobj1.getAltenateCurrencyId().toPlainString().equalsIgnoreCase( getAlternateCurrencyId().toPlainString())){
					clearAllFields(); 
					 RequestContext.getCurrentInstance().execute("duplicaterecord.show();");
					  return;
				  }else{
					 booAdd=true;
				  }
				 
			  }
			  if(booAdd){
				  CashRateDataTableBean cashRateDataTableBeanObj = new CashRateDataTableBean();

					if (getUnAppCashRateId() == null) {
						cashRateDataTableBeanObj.setCreatedBy(sessionStateManage.getUserName());
						cashRateDataTableBeanObj.setCreatedDate(new Date());
						cashRateDataTableBeanObj.setModifiedBy(null);
						cashRateDataTableBeanObj.setModifiedDate(null);
						cashRateDataTableBeanObj.setApprovedBy(null);
						cashRateDataTableBeanObj.setApprovedDate(null);
						cashRateDataTableBeanObj.setIsActive(Constants.U);

					} else {
						cashRateDataTableBeanObj.setCashRatePk(getUnAppCashRateId());
						cashRateDataTableBeanObj.setCreatedBy(getCreatedBy());
						cashRateDataTableBeanObj.setCreatedDate(getCreatedDate());
						cashRateDataTableBeanObj.setModifiedBy(sessionStateManage.getUserName());
						cashRateDataTableBeanObj.setModifiedDate(new Date());
						cashRateDataTableBeanObj.setIsActive(Constants.U);
						cashRateDataTableBeanObj.setApprovedBy(null);
						cashRateDataTableBeanObj.setApprovedDate(null);

					}
					cashRateDataTableBeanObj.setApplicationCountryId(sessionStateManage.getCountryId());

					cashRateDataTableBeanObj.setAltenateCurrencyId(getAlternateCurrencyId());
					cashRateDataTableBeanObj.setAltenativeCurrencyDescrption(cashRateService.getQuoteName(getAlternateCurrencyId()));
					cashRateDataTableBeanObj.setAltenateCurrencyCode(cashRateService.getCurrencyCode(getAlternateCurrencyId()));

					cashRateDataTableBeanObj.setBaseCurrencyId(new BigDecimal(sessionStateManage.getCurrencyId()));
					cashRateDataTableBeanObj.setBaseCurrencyCode(cashRateService.getCurrencyCode(new BigDecimal(sessionStateManage.getCurrencyId())));

					cashRateDataTableBeanObj.setBaseCurrencyDescription(cashRateService.getQuoteName(new BigDecimal(sessionStateManage.getCurrencyId())));
					
					if(getCountryBranchId()!=null)
					{
						cashRateDataTableBeanObj.setCountryBranchId(getCountryBranchId());
						List<CountryBranch> countryBranchList = cashRateService.getCountryBranchList(getCountryBranchId());
						if (countryBranchList != null) {
							if(countryBranchList.get(0).getBranchId() != null){
								cashRateDataTableBeanObj.setLocCode(countryBranchList.get(0).getBranchId().toString());
							}
							cashRateDataTableBeanObj.setBranchDescriprion(countryBranchList.get(0).getBranchName());

						}
					}
					

					cashRateDataTableBeanObj.setUploadDate(new Date());

					cashRateDataTableBeanObj.setMaxBuyRate(getBuyRateMax());
					cashRateDataTableBeanObj.setMinBuyRate(getBuyRateMin());
					cashRateDataTableBeanObj.setMaxSellRate(getSaleMaxRate());
					cashRateDataTableBeanObj.setMinSellRate(getSaleMinRate());

					setBooInputPanel(true);
					setBooDataTablePanel(true);
					

					lstFromUser.add(cashRateDataTableBeanObj);
					clearAllFields();
				  }
		  }
	}

	public void Save() {
		  try{
		for (CashRateDataTableBean cashRateDataTableBean : lstFromUser) {

			UnApprovalCashRate unApprovalCashRate = new UnApprovalCashRate();

			unApprovalCashRate.setUnAppCashRateId(cashRateDataTableBean.getCashRatePk());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(cashRateDataTableBean.getApplicationCountryId());
			unApprovalCashRate.setAppCountryId(countryMaster);

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(cashRateDataTableBean.getAltenateCurrencyId());
			unApprovalCashRate.setAlternateCurrencyId(currencyMaster);
			unApprovalCashRate.setAlternateCurrencyCode(cashRateDataTableBean.getAltenateCurrencyCode());

			unApprovalCashRate.setCreatedBy(sessionStateManage.getUserName());
			unApprovalCashRate.setCreatedDate(new Date());

			CurrencyMaster currencyMaster1 = new CurrencyMaster();
			currencyMaster1.setCurrencyId(cashRateDataTableBean.getBaseCurrencyId());
			unApprovalCashRate.setBaseCurrencyId(currencyMaster1);
			unApprovalCashRate.setBaseCurrencyCode(cashRateDataTableBean.getBaseCurrencyCode());

			CountryBranch countryBranch = new CountryBranch();
			if(cashRateDataTableBean.getCountryBranchId()!=null)
			{
				countryBranch.setCountryBranchId(cashRateDataTableBean.getCountryBranchId());
				unApprovalCashRate.setCountryBranchId(countryBranch);
			}
			
			
			unApprovalCashRate.setLocCode(cashRateDataTableBean.getLocCode());

			unApprovalCashRate.setSaleMaxRate(cashRateDataTableBean.getMaxSellRate());
			unApprovalCashRate.setSaleMinRate(cashRateDataTableBean.getMinSellRate());
			unApprovalCashRate.setBuyRateMax(cashRateDataTableBean.getMaxBuyRate());
			unApprovalCashRate.setBuyRateMin(cashRateDataTableBean.getMinBuyRate());

			unApprovalCashRate.setUploadDate(cashRateDataTableBean.getUploadDate());
			unApprovalCashRate.setCreatedBy(cashRateDataTableBean.getCreatedBy());
			unApprovalCashRate.setCreatedDate(cashRateDataTableBean.getCreatedDate());
			unApprovalCashRate.setModifiedBy(cashRateDataTableBean.getModifiedBy());
			unApprovalCashRate.setModifiedDate(cashRateDataTableBean.getModifiedDate());
			unApprovalCashRate.setIsActive(cashRateDataTableBean.getIsActive());

			cashRateService.save(unApprovalCashRate);
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("complete.show();");

		}
		  }catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::Save"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		  }catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }
	}

	public void view() {
		  try{
		lstFromUser.clear();
		List<UnApprovalCashRate> objList = cashRateService.getAllUnAppCashRate();
		if (objList.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("isEmpty.show();");
		} else {
			
			for (UnApprovalCashRate cashRateObj : objList) {
				CashRateDataTableBean cashRateDTObj = new CashRateDataTableBean();
				cashRateDTObj.setCashRatePk(cashRateObj.getUnAppCashRateId());

				cashRateDTObj.setAltenateCurrencyCode(cashRateObj.getAlternateCurrencyCode());
				cashRateDTObj.setAltenateCurrencyId(cashRateObj.getAlternateCurrencyId().getCurrencyId());
				cashRateDTObj.setAltenativeCurrencyDescrption(cashRateService.getQuoteName(cashRateObj.getAlternateCurrencyId().getCurrencyId()));

				cashRateDTObj.setBaseCurrencyId(cashRateObj.getBaseCurrencyId().getCurrencyId());
				cashRateDTObj.setBaseCurrencyCode(cashRateObj.getBaseCurrencyCode());
				cashRateDTObj.setBaseCurrencyDescription(cashRateService.getQuoteName(cashRateObj.getBaseCurrencyId().getCurrencyId()));

				cashRateDTObj.setMinBuyRate(cashRateObj.getBuyRateMin());
				cashRateDTObj.setMaxBuyRate(cashRateObj.getBuyRateMax());
				cashRateDTObj.setMinSellRate(cashRateObj.getSaleMinRate());
				cashRateDTObj.setMaxSellRate(cashRateObj.getSaleMaxRate());

				cashRateDTObj.setLocCode(cashRateObj.getLocCode());
				if(cashRateObj.getCountryBranchId()!=null)
				{
					cashRateDTObj.setCountryBranchId(cashRateObj.getCountryBranchId().getCountryBranchId());
					List<CountryBranch> lstCountryBranch=cashRateService.getCountryBranchList(cashRateObj.getCountryBranchId().getCountryBranchId());
					if(lstCountryBranch.size()!=0)
					{
						cashRateDTObj.setBranchDescriprion(lstCountryBranch.get(0).getBranchName());
					}
				}
					
				

				cashRateDTObj.setCreatedBy(cashRateObj.getCreatedBy());
				cashRateDTObj.setCreatedDate(cashRateObj.getCreatedDate());
				cashRateDTObj.setModifiedBy(cashRateObj.getModifiedBy());
				cashRateDTObj.setModifiedDate(cashRateObj.getModifiedDate());
				cashRateDTObj.setIsActive(cashRateObj.getIsActive());

				cashRateDTObj.setApplicationCountryId(cashRateObj.getAppCountryId().getCountryId());
				lstFromUser.add(cashRateDTObj);
				setBooInputPanel(true);
				setBooDataTablePanel(true);
				clearAllFields();

			}

		}
		
		lstFromUser.addAll(lstFromUserView);
		  }catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::view"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		  }catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }
	}

	public void edit(CashRateDataTableBean cashRateDataTableBean)throws Exception {
		 setEditClicked( true);
		setUnAppCashRateId(cashRateDataTableBean.getCashRatePk());
		getListCurrency();
		setAlternateCurrencyId(cashRateDataTableBean.getAltenateCurrencyId());
		setBaseCurrencyId(cashRateDataTableBean.getBaseCurrencyId());
		if(cashRateDataTableBean.getCountryBranchId()!=null)
		{
			setCountryBranchId(cashRateDataTableBean.getCountryBranchId());
		}
		
		setSaleMaxRate(cashRateDataTableBean.getMaxSellRate());
		setSaleMinRate(cashRateDataTableBean.getMinSellRate());
		setBuyRateMax(cashRateDataTableBean.getMaxBuyRate());
		setBuyRateMin(cashRateDataTableBean.getMinBuyRate());
		setAppCountryId(cashRateDataTableBean.getApplicationCountryId());
		setBaseCurrencyCode(cashRateDataTableBean.getBaseCurrencyCode());
		setAlternateCurrencyCode(cashRateDataTableBean.getAltenateCurrencyCode());
		setLocCode(cashRateDataTableBean.getLocCode());
		setCreatedBy(cashRateDataTableBean.getCreatedBy());
		setCreatedDate(cashRateDataTableBean.getCreatedDate());
		setModifiedBy(cashRateDataTableBean.getModifiedBy());
		setModifiedDate(cashRateDataTableBean.getModifiedDate());
		setIsActive(cashRateDataTableBean.getIsActive());

		lstFromUser.remove(cashRateDataTableBean);
		lstFromUserView.remove(cashRateDataTableBean);
		
	}

	List<CashRate> objList = new ArrayList<CashRate>();

	public void approvedSave() {
		try{  
		CashRate cashRate = new CashRate();

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(getAppCountryId());
		cashRate.setAppCountryId(countryMaster);

		CurrencyMaster currencyMaster = new CurrencyMaster();
		currencyMaster.setCurrencyId(getAlternateCurrencyId());
		cashRate.setAlternateCurrencyId(currencyMaster);
		cashRate.setAlternateCurrencyCode(getAlternateCurrencyCode());

		CurrencyMaster currencyMaster1 = new CurrencyMaster();
		currencyMaster1.setCurrencyId(getBaseCurrencyId());
		cashRate.setBaseCurrencyId(currencyMaster1);
		cashRate.setBaseCurrencyCode(getBaseCurrencyCode());

		if(getCountryBranchId()!=null)
		{
			CountryBranch countryBranch = new CountryBranch();
			countryBranch.setCountryBranchId(getCountryBranchId());
			cashRate.setCountryBranchId(countryBranch);
			
		}
		cashRate.setLocCode(getLocCode());

		cashRate.setSaleMaxRate(getSaleMaxRate());
		cashRate.setSaleMinRate(getSaleMinRate());
		cashRate.setBuyRateMax(getBuyRateMax());
		cashRate.setBuyRateMin(getBuyRateMin());

		cashRate.setUploadDate(getUploadDate());
		cashRate.setCreatedBy(getCreatedBy());
		cashRate.setCreatedDate(getCreatedDate());
		cashRate.setModifiedBy(getModifiedBy());
		cashRate.setModifiedDate(getModifiedDate());
		cashRate.setIsActive(Constants.Yes);
		cashRate.setApprovedBy(sessionStateManage.getUserName());
		cashRate.setApprovedDate(new Date());

		objList = cashRateService.isExistAllUnAppCashRate(getAlternateCurrencyId(),getBaseCurrencyId(),getAppCountryId(), getCountryBranchId());

		if (objList != null && objList.size()!=0) {

			cashRate.setCashRateId(objList.get(0).getCashRateId());
			cashRateService.cashRateSave(cashRate);

		} else {
			cashRateService.cashRateSave(cashRate);
		}

		deleteUnApproveRecord(getUnAppCashRateId());
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("complete.show();");
		}catch(NullPointerException ne){
		  log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("Method Name::saveDataTableRecods"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
        	  }catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }

	}

	public void deleteUnApproveRecord(BigDecimal unapprovalId) {
		cashRateService.delete(unapprovalId);

	}

	public void confirmDelete(CashRateDataTableBean CashRateDataTableBean)throws Exception {

		if (CashRateDataTableBean.getCashRatePk() != null) {
			deleteUnApproveRecord(CashRateDataTableBean.getCashRatePk());
			lstFromUser.remove(CashRateDataTableBean);
		} else {
			lstFromUser.remove(CashRateDataTableBean);
		}

	}

	public void approvalOk() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/cashrateapproval.xhtml");
			setBooInputPanel(false);
			setBooDataTablePanel(true);
			approveView();
			clearAllFields();
		}catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		  }

	}

	public void clickOnOK() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/cashrateentry.xhtml");
			setBooInputPanel(true);
			setBooDataTablePanel(false);
			clearAllFields();
			lstFromUserView.clear();
			lstFromUser.clear();
		}catch(Exception exception){
        	    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
        	    setErrorMessage(exception.getMessage()); 
        	    RequestContext.getCurrentInstance().execute("error.show();");
        	    return;       
        	  }

	}

	public void clearAllFields() {

		setAlternateCurrencyId(null);
		setBaseCurrencyCode(null);
		setCountryBranchId(null);
		setBuyRateMax(null);
		setBuyRateMin(null);
		setSaleMaxRate(null);
		setSaleMinRate(null);
		setAppCountryId(null);
		setBaseCurrencyId(null);
		setAlternateCurrencyCode(null);
		setLocCode(null);
		setUploadDate(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setUnAppCashRateId(null);
		setEditClicked(false);
	}

	public void checkMin() {
		if (getSaleMaxRate() != null && getSaleMinRate() != null) {
			if (getSaleMaxRate().compareTo(getSaleMinRate())<0) {
				setSaleMinRate(null);
				//setSaleMaxRate(null);
				RequestContext.getCurrentInstance().execute("lesserthanmax.show();");
			}
		}
	}

	public void checkMax() {
		if (getSaleMaxRate() != null && getSaleMinRate() != null) {
			if (getSaleMaxRate().compareTo(getSaleMinRate())<0) {
				setSaleMaxRate(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}
		}
	}

	public void checkBuyMin() {
		if (getBuyRateMax() != null && getBuyRateMin() != null) {
			if (getBuyRateMax().compareTo(getBuyRateMin())<0) {
				setBuyRateMin(null);
				//setBuyRateMax(null);
				RequestContext.getCurrentInstance().execute("lesserthanmax.show();");
			}
		}
	}

	public void checkBuyMax() {
		if (getBuyRateMax() != null && getBuyRateMin() != null) {
			if (getBuyRateMax().compareTo(getBuyRateMin())<0) {
				setBuyRateMax(null);
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}
		}
	}

	public void approve(CashRateDataTableBean cashRateDataTableBean) {
		  try{
		//if (!cashRateDataTableBean.getCreatedBy().equalsIgnoreCase(sessionStateManage.getUserName())) {

		if(!(cashRateDataTableBean.getModifiedBy()==null ? cashRateDataTableBean.getCreatedBy() : cashRateDataTableBean.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
			setUnAppCashRateId(cashRateDataTableBean.getCashRatePk());

			setAlternateCurrencyId(cashRateDataTableBean.getAltenateCurrencyId());
			setAltenativeCurrencyDescrption(cashRateDataTableBean.getAltenativeCurrencyDescrption());
			setAlternateCurrencyCode(cashRateDataTableBean.getAltenateCurrencyCode());

			setBaseCurrencyId(cashRateDataTableBean.getBaseCurrencyId());
			setBaseCurrencyDescription(cashRateDataTableBean.getBaseCurrencyDescription());
			setBaseCurrencyCode(cashRateDataTableBean.getBaseCurrencyCode());
			if(cashRateDataTableBean.getCountryBranchId()!=null)
			{
				setCountryBranchId(cashRateDataTableBean.getCountryBranchId());
			}
			
			setCountryBranchName(cashRateDataTableBean.getBranchDescriprion());
			setLocCode(cashRateDataTableBean.getLocCode());

			setSaleMaxRate(cashRateDataTableBean.getMaxSellRate());
			setSaleMinRate(cashRateDataTableBean.getMinSellRate());
			setBuyRateMax(cashRateDataTableBean.getMaxBuyRate());
			setBuyRateMin(cashRateDataTableBean.getMinBuyRate());

			setAppCountryId(cashRateDataTableBean.getApplicationCountryId());
			setCreatedBy(cashRateDataTableBean.getCreatedBy());
			setCreatedDate(cashRateDataTableBean.getCreatedDate());
			setModifiedBy(cashRateDataTableBean.getModifiedBy());
			setModifiedDate(cashRateDataTableBean.getModifiedDate());
			setIsActive(cashRateDataTableBean.getIsActive());
			setBooInputPanel(true);
			setBooDataTablePanel(false);

		} else {
			RequestContext.getCurrentInstance().execute("notValidUser.show();");
			setBooInputPanel(false);
			setBooDataTablePanel(true);
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }

	}

	public void approveView() {
		  try{
		lstFromUser.clear();
		List<UnApprovalCashRate> inquiryList = cashRateService.getCashRateForApprove(Constants.U);
		for (UnApprovalCashRate cashRateObj : inquiryList) {

			CashRateDataTableBean cashRateDTObj = new CashRateDataTableBean();

			cashRateDTObj.setCashRatePk(cashRateObj.getUnAppCashRateId());

			cashRateDTObj.setAltenateCurrencyCode(cashRateObj.getAlternateCurrencyCode());
			cashRateDTObj.setAltenateCurrencyId(cashRateObj.getAlternateCurrencyId().getCurrencyId());
			cashRateDTObj.setAltenativeCurrencyDescrption(cashRateService.getQuoteName(cashRateObj.getAlternateCurrencyId().getCurrencyId()));

			cashRateDTObj.setBaseCurrencyId(cashRateObj.getBaseCurrencyId().getCurrencyId());
			cashRateDTObj.setBaseCurrencyCode(cashRateObj.getBaseCurrencyCode());
			cashRateDTObj.setBaseCurrencyDescription(cashRateService.getQuoteName(cashRateObj.getBaseCurrencyId().getCurrencyId()));

			cashRateDTObj.setMinBuyRate(cashRateObj.getBuyRateMin());
			cashRateDTObj.setMaxBuyRate(cashRateObj.getBuyRateMax());
			cashRateDTObj.setMinSellRate(cashRateObj.getSaleMinRate());
			cashRateDTObj.setMaxSellRate(cashRateObj.getSaleMaxRate());

			cashRateDTObj.setLocCode(cashRateObj.getLocCode());
			if(cashRateObj.getCountryBranchId()!=null)
			{
				cashRateDTObj.setCountryBranchId(cashRateObj.getCountryBranchId().getCountryBranchId());
				List<CountryBranch> lstCountryBranch=cashRateService.getCountryBranchList(cashRateObj.getCountryBranchId().getCountryBranchId());
				if(lstCountryBranch.size()!=0)
				{
					cashRateDTObj.setBranchDescriprion(lstCountryBranch.get(0).getBranchName());
				}
			}
			

			cashRateDTObj.setCreatedBy(cashRateObj.getCreatedBy());
			cashRateDTObj.setCreatedDate(cashRateObj.getCreatedDate());
			cashRateDTObj.setModifiedBy(cashRateObj.getModifiedBy());
			cashRateDTObj.setModifiedDate(cashRateObj.getModifiedDate());
			cashRateDTObj.setIsActive(Constants.U);

			cashRateDTObj.setApplicationCountryId(cashRateObj.getAppCountryId().getCountryId());
			lstFromUser.add(cashRateDTObj);

		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		  }

	}

	public void exit() {

		try {
			List<RoleMaster> rolList = getiGeneralService().getRoleList(new BigDecimal(sessionStateManage.getRoleId()));

			if (rolList.get(0).getRoleName() != null && rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");

				clearAllFields();
				lstFromUserView.clear();
				lstFromUser.clear();
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");

				clearAllFields();
				lstFromUserView.clear();
				lstFromUser.clear();
			}

		}catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		  }

	}

	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }

	public Boolean getEditClicked() {
		return editClicked;
	}

	public void setEditClicked(Boolean editClicked) {
		this.editClicked = editClicked;
	}
	
	
}
