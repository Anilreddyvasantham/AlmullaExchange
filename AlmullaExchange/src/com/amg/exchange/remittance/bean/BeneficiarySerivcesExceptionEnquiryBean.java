/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CityMasterDesc;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.DistrictMasterDesc;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */

@Component("beneficiarySerivcesExceptionEnquiry")
@Scope("session")
public class BeneficiarySerivcesExceptionEnquiryBean<T> implements Serializable {

	/**
	 * 
	 */

	Logger log = Logger.getLogger(PersonalRemittanceBean.class);

	private static final long serialVersionUID = 1L;

	private BigDecimal country;
	private BigDecimal state;
	private BigDecimal district;
	private BigDecimal city;
	private BigDecimal currency;
	private BigDecimal bank;
	private BigDecimal fromBankBranch;
	private BigDecimal toBankBranch;
	private BigDecimal modeOfBranch;
	private String bankCode = null;
	private BigDecimal bankBranchCode = null;
	private String stateName;
	private String cityName;
	private String districtName;
	private boolean isFlag = false;
	private Boolean selectedrecord = false;
	private BigDecimal beneServiceExcepSetup;
	private String countryCode;

	

	private List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankBranch> lstBankbranch = new ArrayList<BankBranch>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private List<StateMasterDesc> lststate = new ArrayList<StateMasterDesc>();
	private List<DistrictMasterDesc> lstDistict = new ArrayList<DistrictMasterDesc>();
	private List<CityMasterDesc> lstCity = new ArrayList<CityMasterDesc>();
	private boolean booViewdatatTablePanel;
	private List<BeneServiceExceptionDataTable> beneServiceList = new ArrayList<BeneServiceExceptionDataTable>();

	SessionStateManage sessionmanage = new SessionStateManage();

	private Boolean boodatatTablePanel = false;
	
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	IFundEstimationService<T> ifundservice;

	public BeneficiarySerivcesExceptionEnquiryBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public BigDecimal getState() {
		return state;
	}

	public void setState(BigDecimal state) {
		this.state = state;
	}

	public BigDecimal getDistrict() {
		return district;
	}

	public void setDistrict(BigDecimal district) {
		this.district = district;
	}

	public BigDecimal getCity() {
		return city;
	}

	public void setCity(BigDecimal city) {
		this.city = city;
	}

	public BigDecimal getCurrency() {
		return currency;
	}

	public void setCurrency(BigDecimal currency) {
		this.currency = currency;
	}

	public BigDecimal getBank() {
		return bank;
	}

	public void setBank(BigDecimal bank) {
		this.bank = bank;
	}

	public BigDecimal getFromBankBranch() {
		return fromBankBranch;
	}

	public void setFromBankBranch(BigDecimal fromBankBranch) {
		this.fromBankBranch = fromBankBranch;
	}

	public BigDecimal getToBankBranch() {
		return toBankBranch;
	}

	public void setToBankBranch(BigDecimal toBankBranch) {
		this.toBankBranch = toBankBranch;
	}

	public BigDecimal getModeOfBranch() {
		return modeOfBranch;
	}

	public void setModeOfBranch(BigDecimal modeOfBranch) {
		this.modeOfBranch = modeOfBranch;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public BigDecimal getBankBranchCode() {
		return bankBranchCode;
	}

	public void setBankBranchCode(BigDecimal bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getDistrictName() {
		return districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
	}

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	public BigDecimal getBeneServiceExcepSetup() {
		return beneServiceExcepSetup;
	}

	public void setBeneServiceExcepSetup(BigDecimal beneServiceExcepSetup) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(
			IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public List<BankAccountDetails> getListCurrencyAccountDetails() {
		return listCurrencyAccountDetails;
	}

	public void setListCurrencyAccountDetails(
			List<BankAccountDetails> listCurrencyAccountDetails) {
		this.listCurrencyAccountDetails = listCurrencyAccountDetails;
	}

	public List<CurrencyMaster> getListCurrency() {
		return listCurrency;
	}

	public void setListCurrency(List<CurrencyMaster> listCurrency) {
		this.listCurrency = listCurrency;
	}

	public List<BankMaster> getLstBank() {
		return lstBank;
	}

	public void setLstBank(List<BankMaster> lstBank) {
		this.lstBank = lstBank;
	}

	public List<BankBranch> getLstBankbranch() {
		return lstBankbranch;
	}

	public void setLstBankbranch(List<BankBranch> lstBankbranch) {
		this.lstBankbranch = lstBankbranch;
	}

	public List<CountryMasterDesc> getLstCountry() {
		  List<CountryMasterDesc> lstCountry=null;
		  try{
		lstCountry = getGeneralService().getCountryList(sessionmanage
				.getLanguageId());
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		  }
		return lstCountry;
	}

	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}

	public List<StateMasterDesc> getLststate() {
		return lststate;
	}

	public void setLststate(List<StateMasterDesc> lststate) {
		this.lststate = lststate;
	}

	public List<DistrictMasterDesc> getLstDistict() {
		return lstDistict;
	}

	public void setLstDistict(List<DistrictMasterDesc> lstDistict) {
		this.lstDistict = lstDistict;
	}

	public List<CityMasterDesc> getLstCity() {
		return lstCity;
	}

	public void setLstCity(List<CityMasterDesc> lstCity) {
		this.lstCity = lstCity;
	}
	
	
	
	public IGeneralService<T> getGeneralService() {
		return generalService;
	}

	public void setGeneralService(IGeneralService<T> generalService) {
		this.generalService = generalService;
	}

	public IBankBranchDetailsService<T> getBankBranchDetailsService() {
		return bankBranchDetailsService;
	}

	public void setBankBranchDetailsService(
			IBankBranchDetailsService<T> bankBranchDetailsService) {
		this.bankBranchDetailsService = bankBranchDetailsService;
	}

	public IFundEstimationService<T> getIfundservice() {
		return ifundservice;
	}

	public void setIfundservice(IFundEstimationService<T> ifundservice) {
		this.ifundservice = ifundservice;
	}

	public void popbanklist() {
		  try{
		// lstBank=getiPersonalRemittanceService().getbanklist(getBenifisCurrencyId());
		lstBank = getGeneralService().getBankList(getCountry());

		//popStatelist();
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }


	}
	public void popCurrencylistBank()throws Exception {
		listCurrencyAccountDetails = getIfundservice().getCurrencyOfBank(getBank());
		System.out.println("*********************" + getBank());
		System.out.println("*********************"
				+ listCurrencyAccountDetails.size());
		  
	}

	public void popbranchlist() {
		  try{
		popCurrencylistBank();
		lstBankbranch = getiPersonalRemittanceService().getBankbranchlist(
				getBank());
		for (BankMaster bankMaster : lstBank) {
			if (bankMaster.getBankId().compareTo(getBank()) == 0) {
				setBankCode(bankMaster.getBankCode());
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
	}



	public void popCurrencylist() {
		  try{
		listCurrency = getiPersonalRemittanceService().getCurrencyList(
				getCountry());
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }


	}

	/*public void popStatelist() throws Exception{
		lststate = getGeneralService().getStateList(sessionmanage.getLanguageId(),
				getCountry());
	}*/

	/*public void popDistict() {
		  try{
		lstDistict = getGeneralService().getDistrictList(
				sessionmanage.getLanguageId(), getCountry(),
				getState());
		for (StateMasterDesc stateMaster : lststate) {
			if (stateMaster.getStateDescId().compareTo(getState()) == 0) {
				setStateName(stateMaster.getStateName());
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }
	}

	public void popCitylist() {
		  try{
		lstCity = getGeneralService().getCityList(sessionmanage.getLanguageId(),
				getCountry(), getState(), getDistrict());
		for (DistrictMasterDesc districtMaster : lstDistict) {
			if (districtMaster.getDistrictDescId().compareTo(getDistrict()) == 0) {
				setDistrictName(districtMaster.getDistrict());
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }
	}

	public void cityNameset() {
		  try{
		for (CityMasterDesc cityMaster : lstCity) {
			if (cityMaster.getCityMasterId().compareTo(getCity()) == 0) {
				setCityName(cityMaster.getCityName());
			}
		}
		  }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }
	}
*/
	public List<BeneServiceExceptionDataTable> getBeneServiceList() {
		return beneServiceList;
	}

	public void setBeneServiceList(
			List<BeneServiceExceptionDataTable> beneServiceList) {
		this.beneServiceList = beneServiceList;
	}

	public boolean isBooViewdatatTablePanel() {
		return booViewdatatTablePanel;
	}

	public void setBooViewdatatTablePanel(boolean booViewdatatTablePanel) {
		this.booViewdatatTablePanel = booViewdatatTablePanel;
	}
	
	public void clear(){
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		try {
			context.redirect("../remittance/beneficiaryservicesexceptionenquiry.xhtml");
			
			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			setState(null);
			setCurrency(null);
			setDistrict(null);
			setCity(null);
			

			setLstCity(null);
			setLstBank(null);
			setLstDistict(null);
			setLstCity(null);
			setListCurrency(null);
			setLststate(null);
			setListCurrencyAccountDetails(null);
			setBooViewdatatTablePanel(false);
		} catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void beneficiaryExceptionPageNavigation() {
		System.out
				.println("callToBeneficiaryServicesExceptionSetup  ================== > ");
		ExternalContext context = FacesContext.getCurrentInstance()
				.getExternalContext();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "beneficiaryservicesexceptionenquiry.xhtml");
			context.redirect("../remittance/beneficiaryservicesexceptionenquiry.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			setCurrency(null);
			setState(null);
			setDistrict(null);
			setCity(null);

			setLstCity(null);
			setLstBank(null);
			setLstDistict(null);
			setLstCity(null);
			setListCurrency(null);
			setLststate(null);
			setListCurrencyAccountDetails(null);
			// setBoodatatTablePanel(false);

			setBooViewdatatTablePanel(false);

			// setBoodatatTablePanel(false);
        	} catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }

	}

	public void view() {
		  try{
		beneServiceList.clear();

		List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = getiPersonalRemittanceService().getExceptionSetupListForEnquiry(getCountry(), getBank(), getCurrency());

		if (null != beneServiceExceptionSetupList && !beneServiceExceptionSetupList.isEmpty()) {

			for (BeneServiceExceptionSetup beneServiceExceptionSetup : beneServiceExceptionSetupList) {

				BeneServiceExceptionDataTable beneServiceExceptionDataTable = new BeneServiceExceptionDataTable();

				beneServiceExceptionDataTable.setBeneServiceExcepSetup(beneServiceExceptionSetup.getBeneServiceExcepSetup());
				beneServiceExceptionDataTable.setAppCountryId(beneServiceExceptionSetup.getAppCountryId().getCountryId());
				beneServiceExceptionDataTable.setBankBranchCode(beneServiceExceptionSetup.getBankBranchCode());
				beneServiceExceptionDataTable.setBankBranchId(beneServiceExceptionSetup.getBankBranchId().getBankBranchId());
				beneServiceExceptionDataTable.setBankCode(beneServiceExceptionSetup.getBankCode());
				beneServiceExceptionDataTable.setBankId(beneServiceExceptionSetup.getBankId().getBankId());
				beneServiceExceptionDataTable.setRemittanceCode(beneServiceExceptionSetup.getRemittanceCode());
				beneServiceExceptionDataTable.setDeliveryModeCode(beneServiceExceptionSetup.getDeliveryModeCode());
				beneServiceExceptionDataTable.setRemittanceModeId(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId());
				beneServiceExceptionDataTable.setDeliveryId(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId());
				beneServiceExceptionDataTable.setIsActive(beneServiceExceptionSetup.getIsActive());
				beneServiceExceptionDataTable.setCurrency(beneServiceExceptionSetup.getCurrency().getCurrencyId());
				beneServiceExceptionDataTable.setCurrencyCode(beneServiceExceptionSetup.getCurrencyCode());
				beneServiceExceptionDataTable.setIsActive(setRecordStatus(beneServiceExceptionSetup.getIsActive()));
				String remittanceDesc = null;
				List<RemittanceModeDescription> remittanceDescList = getiPersonalRemittanceService().listRemittanceDesc(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId(), sessionmanage.getLanguageId());
				List<DeliveryModeDesc> deliveryModeDecList = getiPersonalRemittanceService().lstDeliveryMode(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId(), sessionmanage.getLanguageId());
				System.out.println("" + deliveryModeDecList.size());
				if (remittanceDescList.size() > 0) {

					beneServiceExceptionDataTable.setRemittanceDescription(remittanceDescList.get(0).getRemittanceDescription());
				} else {
					beneServiceExceptionDataTable.setRemittanceDescription(remittanceDesc);
				}

				if (deliveryModeDecList.size() > 0) {
					beneServiceExceptionDataTable.setDeliveryDescription(deliveryModeDecList.get(0).getEnglishDeliveryName());
				} else {
					beneServiceExceptionDataTable.setDeliveryDescription(null);
				}

				beneServiceExceptionDataTable.setCreatedBy(beneServiceExceptionSetup.getCreatedBy());
				beneServiceExceptionDataTable.setCreatedDate(beneServiceExceptionSetup.getCreatedDate());
				beneServiceList.add(beneServiceExceptionDataTable);

				setBooViewdatatTablePanel(true);

				// setBoodatatTablePanel(false);
				setCountry(null);
				setBank(null);
				setFromBankBranch(null);
				setToBankBranch(null);
				setCity(null);
				setState(null);
				setDistrict(null);
				setCurrency(null);
				setModeOfBranch(null);

				setLstCity(null);
				setLstBank(null);
				setLstDistict(null);
				setLstCity(null);
				setListCurrency(null);
				setLststate(null);

				setListCurrencyAccountDetails(null);

			}

		} else {
			RequestContext.getCurrentInstance().execute("viewData.show();");
		}
		  }catch(NullPointerException ne){
		    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::view");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		    }catch(Exception exception){
		    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return; 
		  }

	}
	
	private String setRecordStatus(String status) {

		String strStatus = null;

		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus="Un_ Approve";
		}
		if (status.equalsIgnoreCase(Constants.D)) {

			strStatus = "Deleted";
		}
		if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = "Activated";
		}
		return strStatus;

	}
	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	

}
