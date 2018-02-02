/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.BeneServiceExceptionSetup;
import com.amg.exchange.remittance.service.IBeneServiceExceptionService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankAccountDetails;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BeneCountryService;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.model.RemittanceModeDescription;
import com.amg.exchange.treasury.model.RemittanceModeMaster;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("beneficiarySerivcesExceptionBean")
@Scope("session")
public class BeneficiarySerivcesExceptionBean<T> implements Serializable {

	/**
	 * 
	 */

	Logger log = Logger.getLogger(BeneficiarySerivcesExceptionBean.class);

	private static final long serialVersionUID = 1L;

	private BigDecimal country;
	private BigDecimal currency;
	private String currencyCode;
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
	//added by nazish for BUG 21-12-2015
	private Map<BigDecimal,String> mapBankCode  = new HashMap<BigDecimal,String>();

	private String countryCode;
	private String errorMessage;





	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;

	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	@Autowired
	IFundEstimationService<T> ifundservice;

	@Autowired
	IBeneServiceExceptionService beneServiceExceptionService;

	List<BankAccountDetails> listCurrencyAccountDetails = new ArrayList<BankAccountDetails>();
	private List<CurrencyMaster> listCurrency = new ArrayList<CurrencyMaster>();
	private List<BankMaster> lstBank = new ArrayList<BankMaster>();
	private List<BankBranch> lstBankbranch = new ArrayList<BankBranch>();
	private List<CountryMasterDesc> lstCountry = new ArrayList<CountryMasterDesc>();
	private Boolean boodatatTablePanel = false;

	SessionStateManage sessionmanage = new SessionStateManage();

	public BeneficiarySerivcesExceptionBean() {
		// TODO Auto-generated constructor stub
	}

	public Logger getLog() {
		return log;
	}
	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public void setLog(Logger log) {
		this.log = log;
	}

	public BigDecimal getCountry() {
		return country;
	}

	public void setCountry(BigDecimal country) {
		this.country = country;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
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

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Boolean getBoodatatTablePanel() {
		return boodatatTablePanel;
	}

	public void setBoodatatTablePanel(Boolean boodatatTablePanel) {
		this.boodatatTablePanel = boodatatTablePanel;
	}

	public boolean isFlag() {
		return isFlag;
	}

	public void setFlag(boolean isFlag) {
		this.isFlag = isFlag;
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

	public void setBankBranchDetailsService(IBankBranchDetailsService<T> bankBranchDetailsService) {
		this.bankBranchDetailsService = bankBranchDetailsService;
	}

	public IPersonalRemittanceService getiPersonalRemittanceService() {
		return iPersonalRemittanceService;
	}

	public void setiPersonalRemittanceService(IPersonalRemittanceService iPersonalRemittanceService) {
		this.iPersonalRemittanceService = iPersonalRemittanceService;
	}

	public IFundEstimationService<T> getIfundservice() {
		return ifundservice;
	}

	public void setIfundservice(IFundEstimationService<T> ifundservice) {
		this.ifundservice = ifundservice;
	}


	public IBeneServiceExceptionService getBeneServiceExceptionService() {
		return beneServiceExceptionService;
	}

	public void setBeneServiceExceptionService(IBeneServiceExceptionService beneServiceExceptionService) {
		this.beneServiceExceptionService = beneServiceExceptionService;
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
		lstCountry = getGeneralService().getCountryList(sessionmanage.getLanguageId());
		return lstCountry;
	}

	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}


	public List<BankAccountDetails> getListCurrencyAccountDetails() {
		return listCurrencyAccountDetails;
	}

	public void setListCurrencyAccountDetails(List<BankAccountDetails> listCurrencyAccountDetails) {
		this.listCurrencyAccountDetails = listCurrencyAccountDetails;
	}

	public void popbanklist() {
		lstBank = getGeneralService().getBankList(getCountry());

		for(BankMaster bankmaster:lstBank){
			mapBankCode.put(bankmaster.getBankId(), bankmaster.getBankCode());

		}

	}

	public void popCurrencylistBank() {
		listCurrencyAccountDetails = getIfundservice().getCurrencyOfBank(getBank());
	}

	public void popbranchlist() {
		popCurrencylistBank();
		if(mapBankCode.get(getBank())!=null){
			setBankCode(mapBankCode.get(getBank()));
		}
		//added by nazish on 21-12-2015

		/*lstBankbranch = getBeneServiceExceptionService().getBankbranchlist(getBank());
		for (BankMaster bankMaster : lstBank) {
			if (bankMaster.getBankId().compareTo(getBank()) == 0) {
				setBankCode(bankMaster.getBankCode());
			}
		}*/
	}

	public void popCurrencylist() {
		try{
			listCurrency = getBeneServiceExceptionService().getCurrencyList(getCountry());
		}catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}



	List<BeneficiarySerivcesExceptionBean> lstExceptionSetup = new ArrayList<BeneficiarySerivcesExceptionBean>();

	public List<BeneficiarySerivcesExceptionBean> getLstExceptionSetup() {
		return lstExceptionSetup;
	}

	public void setLstExceptionSetup(List<BeneficiarySerivcesExceptionBean> lstExceptionSetup) {
		this.lstExceptionSetup = lstExceptionSetup;
	}

	List<BeneficiaryServiceExceptionDataTable> beneficiarySerivcesExceptionBeansList = new ArrayList<BeneficiaryServiceExceptionDataTable>();
	List<ServiceExceptionDataTable> exceptionServiceList = new ArrayList<ServiceExceptionDataTable>();

	public List<BeneficiaryServiceExceptionDataTable> getBeneficiarySerivcesExceptionBeansList() {
		return beneficiarySerivcesExceptionBeansList;
	}

	public void setBeneficiarySerivcesExceptionBeansList(List<BeneficiaryServiceExceptionDataTable> beneficiarySerivcesExceptionBeansList) {
		this.beneficiarySerivcesExceptionBeansList = beneficiarySerivcesExceptionBeansList;
	}

	public List<ServiceExceptionDataTable> getExceptionServiceList() {
		return exceptionServiceList;
	}

	public void setExceptionServiceList(List<ServiceExceptionDataTable> exceptionServiceList) {
		this.exceptionServiceList = exceptionServiceList;
	}

	public void cellEdit() {

		if (getModeOfBranch().intValue() != 0) {
			setFlag(true);
		} else {

			setFlag(false);
		}

	}

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	List<BeneCountryService> countryServiceList = new ArrayList<BeneCountryService>();
	List<Object> countryObjectlist = new ArrayList<Object>();
	List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = new ArrayList<BeneServiceExceptionSetup>();

	//SessionStateManage sessionStateManage = new SessionStateManage();

	public void onCellEdit(BeneficiaryServiceExceptionDataTable beneficiaryServiceExceptionDataTable) {

		try{
			exceptionServiceList.clear();
			setBoodatatTablePanel(false);
			boolean isExistSetup = false;


			BigDecimal countryId = beneficiaryServiceExceptionDataTable.getCountryId();
			BigDecimal bankId = beneficiaryServiceExceptionDataTable.getBankId();
			BigDecimal bankBranchId = beneficiaryServiceExceptionDataTable.getBankBranchId();

			if (Integer.parseInt(beneficiaryServiceExceptionDataTable.getModeOfOperation()) == 1) {
				countryServiceList = getBeneServiceExceptionService().getBeneCountryAllServiceList(countryId, Constants.Yes);

				for (BeneCountryService beneCountryService : countryServiceList) {

					ServiceExceptionDataTable serviceExceptionDataTable = new ServiceExceptionDataTable();

					serviceExceptionDataTable.setRemittanceModeId(beneCountryService.getRemitanceId().getRemittanceModeId());
					serviceExceptionDataTable.setRemittanceCode(beneCountryService.getRemitanceId().getRemittance());
					serviceExceptionDataTable.setDeliveryId(beneCountryService.getDeliveryId().getDeliveryModeId());
					serviceExceptionDataTable.setDeliveryCode(beneCountryService.getDeliveryId().getDeliveryMode());
					List<RemittanceModeDescription> remittanceModeDescriptions = getBeneServiceExceptionService().listRemittanceDesc(beneCountryService.getRemitanceId().getRemittanceModeId(), sessionmanage.getLanguageId(), Constants.Yes);

					List<DeliveryModeDesc> deliveryModeDescs = getBeneServiceExceptionService().lstDeliveryMode(beneCountryService.getDeliveryId().getDeliveryModeId(), sessionmanage.getLanguageId(), Constants.Yes);

					if (null != remittanceModeDescriptions) {
						for (RemittanceModeDescription remittanceModeDescription : remittanceModeDescriptions) {
							serviceExceptionDataTable.setRemittanceMode(remittanceModeDescription.getRemittanceDescription());
						}
					}
					
					if (null != deliveryModeDescs) {
						for (DeliveryModeDesc deliveryModeDesc : deliveryModeDescs) {
							serviceExceptionDataTable.setDeliveryMode(deliveryModeDesc.getEnglishDeliveryName());
						}
					}

					serviceExceptionDataTable.setCurrencyId(beneCountryService.getCurrencyId().getCurrencyId());
					if(beneCountryService.getCurrencyId() != null){
						String currencyName = generalService.getCurrencyName(beneCountryService.getCurrencyId().getCurrencyId());
						if(currencyName != null){
							serviceExceptionDataTable.setCurrencyName(currencyName);
						}
					}
					
					serviceExceptionDataTable.setCountryId(beneCountryService.getBeneCountryId().getCountryId());
					serviceExceptionDataTable.setBankId(bankId);
					serviceExceptionDataTable.setBankBranchId(bankBranchId);

					RequestContext.getCurrentInstance().execute("availableservice.show();");

					exceptionServiceList.add(serviceExceptionDataTable);
				}

			} else if (Integer.parseInt(beneficiaryServiceExceptionDataTable.getModeOfOperation()) == 2) {

				countryServiceList = getBeneServiceExceptionService().getBeneCountryAllServiceList(countryId, Constants.Yes);;

				for (BeneCountryService beneCountryService : countryServiceList) {

					ServiceExceptionDataTable serviceExceptionDataTable = new ServiceExceptionDataTable();

					serviceExceptionDataTable.setRemittanceModeId(beneCountryService.getRemitanceId().getRemittanceModeId());
					serviceExceptionDataTable.setRemittanceCode(beneCountryService.getRemitanceId().getRemittance());
					serviceExceptionDataTable.setDeliveryId(beneCountryService.getDeliveryId().getDeliveryModeId());
					serviceExceptionDataTable.setDeliveryCode(beneCountryService.getDeliveryId().getDeliveryMode());
					List<RemittanceModeDescription> remittanceModeDescriptions = getBeneServiceExceptionService().listRemittanceDesc(beneCountryService.getRemitanceId().getRemittanceModeId(), sessionmanage.getLanguageId(), Constants.Yes);

					List<DeliveryModeDesc> deliveryModeDescs = getBeneServiceExceptionService().lstDeliveryMode(beneCountryService.getDeliveryId().getDeliveryModeId(), sessionmanage.getLanguageId(), Constants.Yes);


					if (null != remittanceModeDescriptions) {

						for (RemittanceModeDescription remittanceModeDescription : remittanceModeDescriptions) {
							serviceExceptionDataTable.setRemittanceMode(remittanceModeDescription.getRemittanceDescription());
						}

					}
					if (null != deliveryModeDescs) {

						for (DeliveryModeDesc deliveryModeDesc : deliveryModeDescs) {
							serviceExceptionDataTable.setDeliveryMode(deliveryModeDesc.getEnglishDeliveryName());
						}

					}

					isExistSetup = getBeneServiceExceptionService().isExistBeneExceptionSetup(beneCountryService.getRemitanceId().getRemittanceModeId(), beneCountryService.getDeliveryId().getDeliveryModeId(),bankId, bankBranchId,Constants.Yes);

					if (isExistSetup) {
						serviceExceptionDataTable.setSelectedrecord(false);
					} else {

						serviceExceptionDataTable.setSelectedrecord(true);

					}

					serviceExceptionDataTable.setCurrencyId(beneCountryService.getCurrencyId().getCurrencyId());
					if(beneCountryService.getCurrencyId() != null){
						String currencyName = generalService.getCurrencyName(beneCountryService.getCurrencyId().getCurrencyId());
						if(currencyName != null){
							serviceExceptionDataTable.setCurrencyName(currencyName);
						}
					}
					
					serviceExceptionDataTable.setCountryId(beneCountryService.getBeneCountryId().getCountryId());
					serviceExceptionDataTable.setBankId(bankId);
					serviceExceptionDataTable.setBankBranchId(bankBranchId);

					RequestContext.getCurrentInstance().execute("availableservice.show();");

					exceptionServiceList.add(serviceExceptionDataTable);
				}

			} else if (Integer.parseInt(beneficiaryServiceExceptionDataTable.getModeOfOperation()) == 3) {
				countryServiceList = getBeneServiceExceptionService().getBeneCountryAllServiceList(countryId, Constants.Yes);;

				for (BeneCountryService beneCountryService : countryServiceList) {

					ServiceExceptionDataTable serviceExceptionDataTable = new ServiceExceptionDataTable();

					serviceExceptionDataTable.setRemittanceModeId(beneCountryService.getRemitanceId().getRemittanceModeId());
					serviceExceptionDataTable.setRemittanceCode(beneCountryService.getRemitanceId().getRemittance());
					serviceExceptionDataTable.setDeliveryId(beneCountryService.getDeliveryId().getDeliveryModeId());
					serviceExceptionDataTable.setDeliveryCode(beneCountryService.getDeliveryId().getDeliveryMode());
					List<RemittanceModeDescription> remittanceModeDescriptions = getBeneServiceExceptionService().listRemittanceDesc(beneCountryService.getRemitanceId().getRemittanceModeId(), sessionmanage.getLanguageId());

					List<DeliveryModeDesc> deliveryModeDescs = getBeneServiceExceptionService().lstDeliveryMode(beneCountryService.getRemitanceId().getRemittanceModeId(), sessionmanage.getLanguageId());

					if (null != remittanceModeDescriptions) {

						if (remittanceModeDescriptions.size() > 0) {
							for (RemittanceModeDescription remittanceModeDescription : remittanceModeDescriptions) {
								serviceExceptionDataTable.setRemittanceMode(remittanceModeDescription.getRemittanceDescription());
							}

						}

					}
					if (null != deliveryModeDescs) {

						if (deliveryModeDescs.size() > 0) {
							for (DeliveryModeDesc deliveryModeDesc : deliveryModeDescs) {
								serviceExceptionDataTable.setDeliveryMode(deliveryModeDesc.getEnglishDeliveryName());
							}

						}

					}

					isExistSetup = getBeneServiceExceptionService().isExistBeneExceptionSetup(beneCountryService.getRemitanceId().getRemittanceModeId(), beneCountryService.getDeliveryId().getDeliveryModeId(),bankId, bankBranchId,Constants.Yes);

					if (isExistSetup) {
						serviceExceptionDataTable.setSelectedrecord(true);
					} else {

						serviceExceptionDataTable.setSelectedrecord(false);

					}

					serviceExceptionDataTable.setCurrencyId(beneCountryService.getCurrencyId().getCurrencyId());
					if(beneCountryService.getCurrencyId() != null){
						String currencyName = generalService.getCurrencyName(beneCountryService.getCurrencyId().getCurrencyId());
						if(currencyName != null){
							serviceExceptionDataTable.setCurrencyName(currencyName);
						}
					}
					
					serviceExceptionDataTable.setCountryId(beneCountryService.getBeneCountryId().getCountryId());
					serviceExceptionDataTable.setBankId(bankId);
					serviceExceptionDataTable.setBankBranchId(bankBranchId);

					RequestContext.getCurrentInstance().execute("availableservice.show();");

					exceptionServiceList.add(serviceExceptionDataTable);
				}

			}
		}catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 

	}

	public void dialogCancel() {

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		try {
			context.redirect("../remittance/beneficiaryservicesexceptionsetup.xhtml");

			setBoodatatTablePanel(true);
			setBooViewdatatTablePanel(false);

		} catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 

	}

	public void search() {

		beneficiarySerivcesExceptionBeansList.clear();
		setBoodatatTablePanel(true);
		setBooViewdatatTablePanel(false);

		if (getToBankBranch() == null) {
			setToBankBranch(getFromBankBranch());
		}

		if (getModeOfBranch().intValue() == 1) {
			newExceptionSetup();
			return;

		} else if (getModeOfBranch().intValue() == 2) {
			liveExceptionSetup();
			return;

		} else if (getModeOfBranch().intValue() == 3) {
			deleteExceptionSetup();
			return;
		} else {

			RequestContext.getCurrentInstance().execute("modeNotSelect.show();");
		}
	}

	public void newExceptionSetup() {
		try{
			beneficiarySerivcesExceptionBeansList.clear();
			boolean isExist = false;
			List<BankBranch> objBankBranchList = new ArrayList<BankBranch>();
			objBankBranchList = getBeneServiceExceptionService().getBankBranchList(getCountry(), getBank(), getFromBankBranch(), getToBankBranch());

			if (objBankBranchList != null) {
				for (BankBranch bankBranch : objBankBranchList) {

					BeneficiaryServiceExceptionDataTable beneficiaryServiceExceptionDataTable = new BeneficiaryServiceExceptionDataTable();
					beneficiaryServiceExceptionDataTable.setBankBranchCode(bankBranch.getBranchCode().toPlainString());
					beneficiaryServiceExceptionDataTable.setBankBranchId(bankBranch.getBankBranchId());
					beneficiaryServiceExceptionDataTable.setBankBranchName(bankBranch.getBranchShortName());
					beneficiaryServiceExceptionDataTable.setBankId(bankBranch.getExBankMaster().getBankId());
					beneficiaryServiceExceptionDataTable.setCountryId(bankBranch.getFsCountryMaster().getCountryId());
					beneficiaryServiceExceptionDataTable.setModeOfOperation("1");

					beneficiarySerivcesExceptionBeansList.add(beneficiaryServiceExceptionDataTable);
					/*
				isExist = getBeneServiceExceptionService().getBanKBranchFromBeneExceptionSetup(bankBranch.getFsCountryMaster().getCountryId(), bankBranch.getExBankMaster().getBankId(), bankBranch.getBankBranchId());

				if (isExist) {
					beneficiarySerivcesExceptionBeansList.add(beneficiaryServiceExceptionDataTable);
				} else {

					beneficiarySerivcesExceptionBeansList.remove(beneficiaryServiceExceptionDataTable);

				}*/

					setBoodatatTablePanel(true);

				}
			} else {
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBoodatatTablePanel(false);
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:newExceptionSetup"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		} 
	}

	public void liveExceptionSetup() {
		System.out.println("Live Exception Setup ========= > ");
		try{
			beneficiarySerivcesExceptionBeansList.clear();

			List<BeneServiceExceptionSetup> objBankBranchList = new ArrayList<BeneServiceExceptionSetup>();
			objBankBranchList = getBeneServiceExceptionService().getBeneServiceExceptionSetupList(getCountry(), getBank(), getFromBankBranch(), getToBankBranch(),Constants.Yes);

			if (objBankBranchList != null) {

				for (BeneServiceExceptionSetup beneServiceExceptionSetup : objBankBranchList) {

					BeneficiaryServiceExceptionDataTable beneficiaryServiceExceptionDataTable = new BeneficiaryServiceExceptionDataTable();
					if (beneServiceExceptionSetup.getBankBranchCode() != null) {
						beneficiaryServiceExceptionDataTable.setBankBranchCode(beneServiceExceptionSetup.getBankBranchCode());
					}

					beneficiaryServiceExceptionDataTable.setBankBranchId(beneServiceExceptionSetup.getBankBranchId().getBankBranchId());
					beneficiaryServiceExceptionDataTable.setBankBranchName(beneServiceExceptionSetup.getBankBranchId().getBranchShortName());
					beneficiaryServiceExceptionDataTable.setBankId(beneServiceExceptionSetup.getBankId().getBankId());
					beneficiaryServiceExceptionDataTable.setCountryId(beneServiceExceptionSetup.getCountryId().getCountryId());
					beneficiaryServiceExceptionDataTable.setApprovedBy(beneServiceExceptionSetup.getApprovedBy());
					beneficiaryServiceExceptionDataTable.setApprovedDate(beneServiceExceptionSetup.getApprovedDate());

					beneficiaryServiceExceptionDataTable.setModeOfOperation("2");

					beneficiarySerivcesExceptionBeansList.add(beneficiaryServiceExceptionDataTable);

				}

			} else {
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBoodatatTablePanel(false);
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:liveExceptionSetup"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  

	}

	public void deleteExceptionSetup() {
		beneficiarySerivcesExceptionBeansList.clear();
		try{
			List<BeneServiceExceptionSetup> objBankBranchList = new ArrayList<BeneServiceExceptionSetup>();
			objBankBranchList = getBeneServiceExceptionService().getBeneDeleteServiceExceptionSetupList(getCountry(), getBank(), getFromBankBranch(), getToBankBranch(),Constants.Yes);

			if (objBankBranchList != null) {
				for (BeneServiceExceptionSetup beneServiceExceptionSetup : objBankBranchList) {

					BeneficiaryServiceExceptionDataTable beneficiaryServiceExceptionDataTable = new BeneficiaryServiceExceptionDataTable();

					if (beneServiceExceptionSetup.getBankBranchCode() != null) {
						beneficiaryServiceExceptionDataTable.setBankBranchCode(beneServiceExceptionSetup.getBankBranchCode());
					}
					beneficiaryServiceExceptionDataTable.setBankBranchId(beneServiceExceptionSetup.getBankBranchId().getBankBranchId());
					beneficiaryServiceExceptionDataTable.setBankBranchName(beneServiceExceptionSetup.getBankBranchId().getBranchShortName());
					beneficiaryServiceExceptionDataTable.setBankId(beneServiceExceptionSetup.getBankId().getBankId());
					beneficiaryServiceExceptionDataTable.setCountryId(beneServiceExceptionSetup.getCountryId().getCountryId());
					beneficiaryServiceExceptionDataTable.setModeOfOperation("3");

					beneficiarySerivcesExceptionBeansList.add(beneficiaryServiceExceptionDataTable);

				}

			} else {
				RequestContext.getCurrentInstance().execute("noRecord.show();");
				setBoodatatTablePanel(false);
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:deleteExceptionSetup"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}



	int count = 0;

	public void save(ServiceExceptionDataTable serviceExceptionDataTables) {
		try{	
			for (ServiceExceptionDataTable serviceDataTable : exceptionServiceList) {

				if (serviceDataTable.getSelectedrecord() == true) {

					BeneServiceExceptionSetup beneServiceExceptionSetup = new BeneServiceExceptionSetup();

					CountryMaster appCountry = new CountryMaster();
					appCountry.setCountryId(sessionmanage.getCountryId());
					beneServiceExceptionSetup.setAppCountryId(appCountry);

					CountryMaster country = new CountryMaster();
					country.setCountryId(getCountry());
					beneServiceExceptionSetup.setCountryId(country);

					beneServiceExceptionSetup.setCountryCode(getGeneralService().getCountryCode(getCountry()).toString());

					CurrencyMaster currencyMaster = new CurrencyMaster();
					currencyMaster.setCurrencyId(getCurrency());
					beneServiceExceptionSetup.setCurrency(currencyMaster);

					beneServiceExceptionSetup.setCurrencyCode(getGeneralService().getCurrencyCode(getCurrency()));

					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(serviceDataTable.getBankId());
					beneServiceExceptionSetup.setBankId(bankMaster);
					beneServiceExceptionSetup.setBankCode(getGeneralService().getBankCode(getBank()));

					RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
					remittanceModeMaster.setRemittanceModeId(serviceDataTable.getRemittanceModeId());
					beneServiceExceptionSetup.setRemittanceModeId(remittanceModeMaster);
					beneServiceExceptionSetup.setRemittanceCode(getGeneralService().getRemittanceCode(serviceDataTable.getRemittanceModeId()));

					DeliveryMode deliveryMode = new DeliveryMode();
					deliveryMode.setDeliveryModeId(serviceDataTable.getDeliveryId());
					beneServiceExceptionSetup.setDeliveryId(deliveryMode);
					beneServiceExceptionSetup.setDeliveryModeCode(getGeneralService().getDeliveryCode(serviceDataTable.getDeliveryId()));

					BankBranch bankBranch = new BankBranch();
					bankBranch.setBankBranchId(serviceDataTable.getBankBranchId());
					beneServiceExceptionSetup.setBankBranchId(bankBranch);
					beneServiceExceptionSetup.setBankBranchCode((getGeneralService().getBankBranchCode(serviceDataTable.getBankBranchId())).toString());

					beneServiceExceptionSetup.setRecordStatus("");

					beneServiceExceptionSetup.setIsActive(Constants.U);
					beneServiceExceptionSetup.setCreatedBy(sessionmanage.getUserName());
					beneServiceExceptionSetup.setCreatedDate(new Date());

					if (getiPersonalRemittanceService().getBeneServiceExceptionSetup(getCountry(), serviceDataTable.getBankBranchId(), serviceDataTable.getRemittanceModeId(), serviceDataTable.getDeliveryId())) {

						RequestContext.getCurrentInstance().execute("idExist.show();");
					} else {
						getiPersonalRemittanceService().saveBeneServiceExceptionSetup(beneServiceExceptionSetup);

						RequestContext.getCurrentInstance().execute("complete.show();");
					}

				}
				count++;
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:save"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}




	public void clickOk() {

		try {		

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/beneficiaryservicesexceptionsetup.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);

			setLstBank(null);
			setListCurrency(null);
			setListCurrencyAccountDetails(null);


			setRemarks(null);


		} catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void callToBeneficiaryServicesExceptionSetup() {


		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "beneficiaryservicesexceptionsetup.xhtml");
			context.redirect("../remittance/beneficiaryservicesexceptionsetup.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			setCurrency(null);
			/*setState(null);
			setDistrict(null);
			setCity(null);
			 */
			//	setLstCity(null);
			setLstBank(null);
			//setLstDistict(null);
			//setLstCity(null);
			setListCurrency(null);
			//	setLststate(null);
			setListCurrencyAccountDetails(null);
			setBoodatatTablePanel(false);

			setBooViewdatatTablePanel(false);

		} catch(Exception e){
			log.info( "Problem Occured While Redirect");
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}


	public void clear() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		try {
			context.redirect("../remittance/beneficiaryservicesexceptionsetup.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			//setState(null);
			setCurrency(null);
			/*setDistrict(null);
			setCity(null);
			 */
			//setLstCity(null);
			setLstBank(null);
			//setLstDistict(null);
			//setLstCity(null);
			setListCurrency(null);
			//setLststate(null);
			setListCurrencyAccountDetails(null);
			setBoodatatTablePanel(false);
			setBooViewdatatTablePanel(false);
		} catch(Exception e){
			log.info( "Problem Occured While Redirect"+e);
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}



	public void cancel() {
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		try {
			context.redirect("../remittance/beneficiaryservicesexceptionsetup.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			//setState(null);
			setCurrency(null);
			//setDistrict(null);
			//setCity(null);

			//setLstCity(null);
			setLstBank(null);
			//setLstDistict(null);
			//setLstCity(null);
			setListCurrency(null);
			//setLststate(null);
			setListCurrencyAccountDetails(null);

			setBoodatatTablePanel(false);
			setBooViewdatatTablePanel(false);
		}  catch(Exception e){
			log.info( "Problem Occured While Redirect"+e);
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  

	}

	private List<BeneServiceExceptionDataTable> beneServiceList = new ArrayList<BeneServiceExceptionDataTable>();

	public List<BeneServiceExceptionDataTable> getBeneServiceList() {
		return beneServiceList;
	}

	public void setBeneServiceList(List<BeneServiceExceptionDataTable> beneServiceList) {
		this.beneServiceList = beneServiceList;
	}

	public void getBeneServiceExceptionSetup() {
		try{
			beneServiceList.clear();

			List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = getiPersonalRemittanceService().getBeneExceptionSetupAllList();

			for (BeneServiceExceptionSetup beneServiceExceptionSetup : beneServiceExceptionSetupList) {

				BeneServiceExceptionDataTable beneServiceExceptionDataTable = new BeneServiceExceptionDataTable();

				beneServiceExceptionDataTable.setBeneServiceExcepSetup(beneServiceExceptionSetup.getBeneServiceExcepSetup());
				beneServiceExceptionDataTable.setAppCountryId(beneServiceExceptionSetup.getAppCountryId().getCountryId());
				beneServiceExceptionDataTable.setCountryId(beneServiceExceptionSetup.getCountryId().getCountryId());
				beneServiceExceptionDataTable.setCountryName(getiGeneralService().getCountryName(sessionmanage.getLanguageId(), beneServiceExceptionSetup.getCountryId().getCountryId()));
				beneServiceExceptionDataTable.setBankBranchCode(beneServiceExceptionSetup.getBankBranchCode());
				beneServiceExceptionDataTable.setBankBranchId(beneServiceExceptionSetup.getBankBranchId().getBankBranchId());
				beneServiceExceptionDataTable.setBranchName(getiPersonalRemittanceService().getBranchName(beneServiceExceptionSetup.getCountryId().getCountryId(), beneServiceExceptionSetup.getBankId().getBankId(), beneServiceExceptionSetup.getBankBranchId().getBankBranchId()));
				beneServiceExceptionDataTable.setBankCode(beneServiceExceptionSetup.getBankCode());
				beneServiceExceptionDataTable.setBankId(beneServiceExceptionSetup.getBankId().getBankId());
				beneServiceExceptionDataTable.setRemittanceCode(beneServiceExceptionSetup.getRemittanceCode());
				beneServiceExceptionDataTable.setDeliveryModeCode(beneServiceExceptionSetup.getDeliveryModeCode());
				beneServiceExceptionDataTable.setRemittanceModeId(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId());
				beneServiceExceptionDataTable.setDeliveryId(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId());
				beneServiceExceptionDataTable.setIsActive(beneServiceExceptionSetup.getIsActive());
				beneServiceExceptionDataTable.setCurrency(beneServiceExceptionSetup.getCurrency().getCurrencyId());
				beneServiceExceptionDataTable.setCurrencyCode(beneServiceExceptionSetup.getCurrencyCode());
				beneServiceExceptionDataTable.setIsActive(beneServiceExceptionSetup.getIsActive());
				String remittanceDesc = null;
				List<RemittanceModeDescription> remittanceDescList = getiPersonalRemittanceService().listRemittanceDesc(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId(), sessionmanage.getLanguageId());
				List<DeliveryModeDesc> deliveryModeDecList = getiPersonalRemittanceService().lstDeliveryMode(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId(), sessionmanage.getLanguageId());

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

			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:getBeneServiceExceptionSetup"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	public void callToExceptionSetupApproval() {

		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();

		try {
			getBeneServiceExceptionSetup();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "beneficiaryservicesexceptionsetupapproval.xhtml");
			context.redirect("../remittance/beneficiaryservicesexceptionsetupapproval.xhtml");

			setCountry(null);
			setBank(null);
			setFromBankBranch(null);
			setToBankBranch(null);
			setModeOfBranch(null);
			setCurrency(null);
			/*setState(null);
			setDistrict(null);
			setCity(null);*/

			setBoodatatTablePanel(true);
		} catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	public void gotoExceptionSetupApproval(BeneServiceExceptionDataTable beneServiceExceptionDataTable) {
		try{
			List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = getiPersonalRemittanceService().getBeneExceptionSetupList(beneServiceExceptionDataTable.getBeneServiceExcepSetup());

			if (beneServiceExceptionSetupList.size() > 0) {

				for (BeneServiceExceptionSetup beneServiceExcepSetup : beneServiceExceptionSetupList) {

					if (!beneServiceExcepSetup.getCreatedBy().equals(sessionmanage.getUserName())) {

						BeneServiceExceptionSetup beneServiceExceptionSetup = new BeneServiceExceptionSetup();

						beneServiceExceptionSetup.setBeneServiceExcepSetup(beneServiceExcepSetup.getBeneServiceExcepSetup());
						beneServiceExceptionSetup.setAppCountryId(beneServiceExcepSetup.getAppCountryId());
						beneServiceExceptionSetup.setCountryId(beneServiceExcepSetup.getCountryId());
						beneServiceExceptionSetup.setCountryCode(beneServiceExcepSetup.getCountryCode());

						beneServiceExceptionSetup.setCurrency(beneServiceExcepSetup.getCurrency());

						beneServiceExceptionSetup.setCurrencyCode(beneServiceExcepSetup.getCurrencyCode());

						beneServiceExceptionSetup.setBankId(beneServiceExcepSetup.getBankId());

						beneServiceExceptionSetup.setBankCode(beneServiceExcepSetup.getBankCode());

						beneServiceExceptionSetup.setRemittanceModeId(beneServiceExcepSetup.getRemittanceModeId());

						beneServiceExceptionSetup.setBankBranchCode(beneServiceExcepSetup.getBankBranchCode());

						beneServiceExceptionSetup.setDeliveryId(beneServiceExcepSetup.getDeliveryId());

						beneServiceExceptionSetup.setDeliveryModeCode(beneServiceExcepSetup.getDeliveryModeCode());

						beneServiceExceptionSetup.setBankBranchId(beneServiceExcepSetup.getBankBranchId());

						beneServiceExceptionSetup.setBankBranchCode(beneServiceExcepSetup.getBankBranchCode());

						beneServiceExceptionSetup.setRecordStatus("");

						beneServiceExceptionSetup.setIsActive(Constants.Yes);
						beneServiceExceptionSetup.setCreatedBy(beneServiceExcepSetup.getCreatedBy());
						beneServiceExceptionSetup.setCreatedDate(beneServiceExcepSetup.getCreatedDate());

						beneServiceExceptionSetup.setApprovedBy(sessionmanage.getUserName());
						beneServiceExceptionSetup.setApprovedDate(new Date());

						getiPersonalRemittanceService().saveBeneServiceExceptionSetup(beneServiceExceptionSetup);

						getBeneServiceExceptionSetup();
						RequestContext.getCurrentInstance().execute("completeapprove.show();");

					} else {
						RequestContext.getCurrentInstance().execute("notValidUser.show();");


					}
				}

			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:gotoExceptionSetupApproval"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  

	}

	private boolean booViewdatatTablePanel;

	@Autowired
	IGeneralService<T> iGeneralService;

	public IGeneralService<T> getiGeneralService() {
		return iGeneralService;
	}

	public void setiGeneralService(IGeneralService<T> iGeneralService) {
		this.iGeneralService = iGeneralService;
	}

	public boolean isBooViewdatatTablePanel() {
		return booViewdatatTablePanel;
	}

	public void setBooViewdatatTablePanel(boolean booViewdatatTablePanel) {
		this.booViewdatatTablePanel = booViewdatatTablePanel;
	}


	public void view() {
		try{
			beneServiceList.clear();

			if(getCountry() == null || getBank() == null){
				RequestContext context = RequestContext.getCurrentInstance();
				context.execute("viewSearch.show();");
				return;
			}else{
				List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = getBeneServiceExceptionService().getBeneServiceExceptionSetupList(getCountry(), getBank(), getFromBankBranch(),getToBankBranch(),Constants.Yes);

				if (null != beneServiceExceptionSetupList && !beneServiceExceptionSetupList.isEmpty()) {

					for (BeneServiceExceptionSetup beneServiceExceptionSetup : beneServiceExceptionSetupList) {

						BeneServiceExceptionDataTable beneServiceExceptionDataTable = new BeneServiceExceptionDataTable();

						beneServiceExceptionDataTable.setBeneServiceExcepSetup(beneServiceExceptionSetup.getBeneServiceExcepSetup());
						beneServiceExceptionDataTable.setAppCountryId(beneServiceExceptionSetup.getAppCountryId().getCountryId());
						beneServiceExceptionDataTable.setCountryId(beneServiceExceptionSetup.getCountryId().getCountryId());
						beneServiceExceptionDataTable.setCountryName(getiGeneralService().getCountryName(sessionmanage.getLanguageId(), beneServiceExceptionSetup.getCountryId().getCountryId()));

						beneServiceExceptionDataTable.setBankBranchCode(beneServiceExceptionSetup.getBankBranchCode());
						beneServiceExceptionDataTable.setBankBranchId(beneServiceExceptionSetup.getBankBranchId().getBankBranchId());
						beneServiceExceptionDataTable.setBranchName(getiPersonalRemittanceService().getBranchName(beneServiceExceptionSetup.getCountryId().getCountryId(), beneServiceExceptionSetup.getBankId().getBankId(), beneServiceExceptionSetup.getBankBranchId().getBankBranchId()));
						beneServiceExceptionDataTable.setBankCode(beneServiceExceptionSetup.getBankCode());
						beneServiceExceptionDataTable.setBankId(beneServiceExceptionSetup.getBankId().getBankId());
						beneServiceExceptionDataTable.setRemittanceCode(beneServiceExceptionSetup.getRemittanceCode());
						beneServiceExceptionDataTable.setDeliveryModeCode(beneServiceExceptionSetup.getDeliveryModeCode());
						beneServiceExceptionDataTable.setRemittanceModeId(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId());
						beneServiceExceptionDataTable.setDeliveryId(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId());
						beneServiceExceptionDataTable.setIsActive(beneServiceExceptionSetup.getIsActive());
						beneServiceExceptionDataTable.setCurrency(beneServiceExceptionSetup.getCurrency().getCurrencyId());
						beneServiceExceptionDataTable.setCurrencyCode(beneServiceExceptionSetup.getCurrencyCode());
						beneServiceExceptionDataTable.setDynamicLabelForActivateDeactivate(setreverActiveStatus(beneServiceExceptionSetup.getIsActive()));
						beneServiceExceptionDataTable.setActivatedBy(beneServiceExceptionSetup.getApprovedBy());
						beneServiceExceptionDataTable.setActivatedDate(beneServiceExceptionSetup.getApprovedDate());
						String remittanceDesc = null;
						List<RemittanceModeDescription> remittanceDescList = getiPersonalRemittanceService().listRemittanceDesc(beneServiceExceptionSetup.getRemittanceModeId().getRemittanceModeId(), sessionmanage.getLanguageId());
						List<DeliveryModeDesc> deliveryModeDecList = getiPersonalRemittanceService().lstDeliveryMode(beneServiceExceptionSetup.getDeliveryId().getDeliveryModeId(), sessionmanage.getLanguageId());

						if (remittanceDescList.size() > 0) {

							beneServiceExceptionDataTable.setRemittanceDescription(remittanceDescList.get(0).getRemittanceDescription());
							beneServiceExceptionDataTable.setRemittanceCode(remittanceDescList.get(0).getRemittanceModeMaster().getRemittance());
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

						setBoodatatTablePanel(false);
						setCountry(null);
						setBank(null);
						setFromBankBranch(null);
						setToBankBranch(null);
						/*setCity(null);
				setState(null);
				setDistrict(null);*/
						setCurrency(null);
						setModeOfBranch(null);

						//setLstCity(null);
						setLstBank(null);
						//setLstDistict(null);
						//setLstCity(null);
						setListCurrency(null);
						//setLststate(null);

						setListCurrencyAccountDetails(null);

					}

				} else {
					RequestContext.getCurrentInstance().execute("viewData.show();");
				}
			}
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:view"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  


	}

	private String remarks="";
	private Date activateDate;
	private String activateBy;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	private String dynamicLabelForActivateDeactivate;

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	public String getActivateBy() {
		return activateBy;
	}

	public void setActivateBy(String activateBy) {
		this.activateBy = activateBy;
	}

	public BigDecimal getBeneServiceExcepSetup() {
		return beneServiceExcepSetup;
	}

	public void setBeneServiceExcepSetup(BigDecimal beneServiceExcepSetup) {
		this.beneServiceExcepSetup = beneServiceExcepSetup;
	}

	public void getActiveInactive(BeneServiceExceptionDataTable datatable) {

		setActivateBy(datatable.getActivatedBy());
		setActivateDate(datatable.getActivatedDate());

		if (datatable.getIsActive().equalsIgnoreCase(Constants.Yes)) {
			RequestContext.getCurrentInstance().execute("remarks.show();");

		}
		if (datatable.getIsActive().equalsIgnoreCase(Constants.D)) {
			updateRemarks();

		}

	}

	public void updateRemarks() {

		try{
			List<BeneServiceExceptionSetup> beneServiceExceptionSetupList = getiPersonalRemittanceService().getBeneExceptionSetupList(getBeneServiceExcepSetup());

			if (beneServiceExceptionSetupList.size() > 0) {

				for (BeneServiceExceptionSetup beneServiceExcepSetup : beneServiceExceptionSetupList) {

					BeneServiceExceptionSetup beneServiceExceptionSetup = new BeneServiceExceptionSetup();

					beneServiceExceptionSetup.setBeneServiceExcepSetup(beneServiceExcepSetup.getBeneServiceExcepSetup());
					beneServiceExceptionSetup.setAppCountryId(beneServiceExcepSetup.getAppCountryId());
					beneServiceExceptionSetup.setCountryId(beneServiceExcepSetup.getCountryId());
					beneServiceExceptionSetup.setCountryCode(beneServiceExcepSetup.getCountryCode());

					beneServiceExceptionSetup.setCurrency(beneServiceExcepSetup.getCurrency());

					beneServiceExceptionSetup.setCurrencyCode(beneServiceExcepSetup.getCurrencyCode());

					beneServiceExceptionSetup.setBankId(beneServiceExcepSetup.getBankId());

					beneServiceExceptionSetup.setBankCode(beneServiceExcepSetup.getBankCode());

					beneServiceExceptionSetup.setRemittanceModeId(beneServiceExcepSetup.getRemittanceModeId());

					beneServiceExceptionSetup.setBankBranchCode(beneServiceExcepSetup.getBankBranchCode());

					beneServiceExceptionSetup.setDeliveryId(beneServiceExcepSetup.getDeliveryId());

					beneServiceExceptionSetup.setDeliveryModeCode(beneServiceExcepSetup.getDeliveryModeCode());

					beneServiceExceptionSetup.setBankBranchId(beneServiceExcepSetup.getBankBranchId());

					beneServiceExceptionSetup.setBankBranchCode(beneServiceExcepSetup.getBankBranchCode());

					beneServiceExceptionSetup.setRecordStatus("");

					beneServiceExceptionSetup.setCreatedBy(beneServiceExcepSetup.getCreatedBy());
					beneServiceExceptionSetup.setCreatedDate(beneServiceExcepSetup.getCreatedDate());
					beneServiceExceptionSetup.setModifiedBy(beneServiceExcepSetup.getModifiedBy());
					beneServiceExceptionSetup.setModifiedDate(beneServiceExcepSetup.getModifiedDate());

					if (beneServiceExcepSetup.getIsActive().equals(Constants.Yes)) {
						beneServiceExceptionSetup.setApprovedBy(null);
						beneServiceExceptionSetup.setApprovedDate(null);
						beneServiceExceptionSetup.setRemarks(getRemarks());
						beneServiceExceptionSetup.setIsActive(Constants.D);

					} else {
						beneServiceExceptionSetup.setApprovedBy(null);
						beneServiceExceptionSetup.setApprovedDate(null);
						beneServiceExceptionSetup.setRemarks(null);
						beneServiceExceptionSetup.setIsActive(Constants.U);
						// RequestContext.getCurrentInstance().execute("deactivate.show();");
					}

					getiPersonalRemittanceService().saveBeneServiceExceptionSetup(beneServiceExceptionSetup);

					getBeneServiceExceptionSetup();

				}
			}

			setBooViewdatatTablePanel(false);
			RequestContext.getCurrentInstance().execute("complete.show();");
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:updateRemarks"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	public void edit(BeneServiceExceptionDataTable beneServiceExceptionDataTable) {
		setCountry(beneServiceExceptionDataTable.getCountryId());
		setCurrency(beneServiceExceptionDataTable.getCurrency());
		setBank(beneServiceExceptionDataTable.getBankId());
		setFromBankBranch(beneServiceExceptionDataTable.getBankBranchId());
		setToBankBranch(beneServiceExceptionDataTable.getBankBranchId());		

	}	

	public void checkMin() {
		if (getToBankBranch() != null && getFromBankBranch() != null) {

			if (getToBankBranch().intValue() < getFromBankBranch().intValue()) {
				setFromBankBranch(null);
				// set
				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	public void checkMax() {

		if (getToBankBranch() != null && getFromBankBranch() != null) {

			if (getToBankBranch().intValue() < getFromBankBranch().intValue()) {
				// setFromBankBranch(null);
				setToBankBranch(null);

				RequestContext.getCurrentInstance().execute("maxgreater.show();");
			}

		}
	}

	private String setreverActiveStatus(String status) {
		log.info("Entering into setreverActiveStatus method");
		String strStatus = null;
		if (status == null) {
			strStatus = Constants.REMOVE;
		} else if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		} else if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		} else if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		log.info("strStatus " + strStatus);
		log.info("Exit into setreverActiveStatus method");

		return strStatus;
	}

	public void checkStatusType(BeneServiceExceptionDataTable beneServiceExceptionDataTable) throws IOException {
		try{
			if (beneServiceExceptionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				beneServiceExceptionDataTable.setRemarksCheck(true);
				setActivateBy(beneServiceExceptionDataTable.getActivatedBy());
				setActivateDate(beneServiceExceptionDataTable.getActivatedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				return;
			} else if (beneServiceExceptionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
				beneServiceExceptionDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			} else if (beneServiceExceptionDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				beneServiceExceptionDataTable.setActivateRecordCheck(true);
				RequestContext.getCurrentInstance().execute("activateRecord.show();");
				return;
			} 
		}catch(NullPointerException  e){
			setErrorMessage("Method Name:checkStatusType"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	public void remarkSelectedRecord() throws IOException {
		try{
			for (BeneServiceExceptionDataTable beneServiceExceptionDataTable : beneServiceList) {
				if (beneServiceExceptionDataTable.getRemarksCheck().equals(true)) {
					if (!getRemarks().equals("")) {
						beneServiceExceptionDataTable.setRemarks(getRemarks());
						beneServiceExceptionDataTable.setActivatedBy(null);
						beneServiceExceptionDataTable.setActivatedDate(null);
						beneServiceExceptionDataTable.setRemarksCheck(true);
						update(beneServiceExceptionDataTable);
						setRemarks(null);
						view();
						cancel();
					} else {
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;
					}
				}

			}
		}catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	public void update(BeneServiceExceptionDataTable beneServiceExceptionDataTable) throws IOException {
		BeneServiceExceptionSetup beneServiceExceptionSetup = new BeneServiceExceptionSetup();
		try {

			beneServiceExceptionSetup.setBeneServiceExcepSetup(beneServiceExceptionDataTable.getBeneServiceExcepSetup());

			CountryMaster countryMaster = new CountryMaster();
			countryMaster.setCountryId(beneServiceExceptionDataTable.getAppCountryId());
			beneServiceExceptionSetup.setAppCountryId(countryMaster);

			CountryMaster countryMaster1 = new CountryMaster();
			countryMaster1.setCountryId(beneServiceExceptionDataTable.getCountryId());
			beneServiceExceptionSetup.setCountryId(countryMaster1);
			if(beneServiceExceptionDataTable.getCountryCode() != null){
				beneServiceExceptionSetup.setCountryCode(beneServiceExceptionDataTable.getCountryCode());
			}else{
				String countryCode = generalService.getCountryCode(beneServiceExceptionDataTable.getCountryId());
				beneServiceExceptionSetup.setCountryCode(countryCode);
			}

			CurrencyMaster currencyMaster = new CurrencyMaster();
			currencyMaster.setCurrencyId(beneServiceExceptionDataTable.getCurrency());
			beneServiceExceptionSetup.setCurrency(currencyMaster);
			if(beneServiceExceptionDataTable.getCurrencyCode() != null){
				beneServiceExceptionSetup.setCurrencyCode(beneServiceExceptionDataTable.getCurrencyCode());
			}else{
				String currencyCode = generalService.getCurrencyCode(beneServiceExceptionDataTable.getCurrency());
				beneServiceExceptionSetup.setCurrencyCode(currencyCode);
			}
			

			BankMaster bankMaster = new BankMaster();
			bankMaster.setBankId(beneServiceExceptionDataTable.getBankId());
			beneServiceExceptionSetup.setBankId(bankMaster);

			beneServiceExceptionSetup.setBankCode(beneServiceExceptionDataTable.getBankCode());

			RemittanceModeMaster remittanceModeMaster = new RemittanceModeMaster();
			remittanceModeMaster.setRemittanceModeId(beneServiceExceptionDataTable.getRemittanceModeId());
			beneServiceExceptionSetup.setRemittanceModeId(remittanceModeMaster);

			DeliveryMode deliveryMode = new DeliveryMode();
			deliveryMode.setDeliveryModeId(beneServiceExceptionDataTable.getDeliveryId());
			beneServiceExceptionSetup.setDeliveryId(deliveryMode);

			beneServiceExceptionSetup.setDeliveryModeCode(beneServiceExceptionDataTable.getDeliveryModeCode());

			BankBranch bankBranch = new BankBranch();
			bankBranch.setBankBranchId(beneServiceExceptionDataTable.getBankBranchId());
			beneServiceExceptionSetup.setBankBranchId(bankBranch);

			beneServiceExceptionSetup.setBankBranchCode(beneServiceExceptionDataTable.getBankBranchCode());

			beneServiceExceptionSetup.setRecordStatus("");

			beneServiceExceptionSetup.setCreatedBy(beneServiceExceptionDataTable.getCreatedBy());
			beneServiceExceptionSetup.setCreatedDate(beneServiceExceptionDataTable.getCreatedDate());
			beneServiceExceptionSetup.setModifiedBy(beneServiceExceptionDataTable.getModifiedBy());
			beneServiceExceptionSetup.setModifiedDate(beneServiceExceptionDataTable.getModifiedDate());

			if (beneServiceExceptionDataTable.getIsActive().equals(Constants.Yes)) {
				beneServiceExceptionSetup.setModifiedBy(sessionmanage.getUserName());
				beneServiceExceptionSetup.setModifiedDate(new Date());
				beneServiceExceptionSetup.setApprovedBy(null);
				beneServiceExceptionSetup.setApprovedDate(null);
				beneServiceExceptionSetup.setRemarks(getRemarks());
				beneServiceExceptionSetup.setIsActive(Constants.D);

			} else {
				beneServiceExceptionSetup.setApprovedBy(null);
				beneServiceExceptionSetup.setApprovedDate(null);
				beneServiceExceptionSetup.setRemarks(null);
				beneServiceExceptionSetup.setIsActive(Constants.U);
				// RequestContext.getCurrentInstance().execute("deactivate.show();");
			}

			getBeneServiceExceptionService().saveBeneServiceExceptionSetup(beneServiceExceptionSetup);

		} catch(NullPointerException  e){
			setErrorMessage("Method Name:update"+e.getMessage()); 
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
		catch(Exception e){
			setErrorMessage(e.getMessage()); 
			RequestContext.getCurrentInstance().execute("csp.show();");
		}  
	}

	public void activateRecord(){
		if(beneServiceList.size()>0){
			for(BeneServiceExceptionDataTable beneServiceExceptionDataTable:beneServiceList){
				if(beneServiceExceptionDataTable.getActivateRecordCheck()!=null){
					if(beneServiceExceptionDataTable.getActivateRecordCheck().equals(true)){
						getBeneServiceExceptionService().activateRecord(beneServiceExceptionDataTable.getBeneServiceExcepSetup(), sessionmanage.getUserName());
						view();
						return;
					}
				}
			}
		}
	}

}
