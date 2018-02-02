package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.ProductGroup;
import com.amg.exchange.remittance.model.TransferMode;
import com.amg.exchange.remittance.service.IBankServiceRuleMasterService;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.CurrencyMaster;
import com.amg.exchange.treasury.service.IBankBranchDetailsService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


@Component("productGroupingSetupBean")
@Scope("session")
public class ProductGroupingSetupBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(ProductGroupingSetupBean.class);
	SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	IBankBranchDetailsService<T> bankBranchDetailsService;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IPersonalRemittanceService personalRemittanceService;
	@Autowired
	IBankServiceRuleMasterService bankServiceRuleMasterService;

	// variables
	private String errorMsg = null;
	private String alertMsg = null;
	private BigDecimal bankId = null;
	private BigDecimal currencyId = null;
	private BigDecimal productGroupId = null;
	private String productgroupdesc = null;
	private BigDecimal productGroupSerial;
	private String accountNo = null;
	private String productMode = null;
	private String productModeDesc = null;
	private BigDecimal transactionLimit = null;
	private BigDecimal bankLimit = null;
	private String createdBy;
	private Date createdDate;
	private String isActive;
	private String dynamicLabelForActivateDeactivate;

	private List<TransferMode> transferModeList = new ArrayList<TransferMode>();
	private Map<String,String> mapTransferMode = new HashMap<String,String>();
	private List<BankMaster> lstBankList = new ArrayList<BankMaster>();
	private Map<BigDecimal,String> mapBankCode = new HashMap<BigDecimal,String>();
	private List<CurrencyMaster> lstCurrencyMaster = new ArrayList<CurrencyMaster>();
	private Map<BigDecimal,String> mapCurrencyCode = new HashMap<BigDecimal,String>();
	private List<ProductGroupingDataTable> lstProductGroupingDataTable = new CopyOnWriteArrayList<ProductGroupingDataTable>();

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// navigation page to Product Grouping setup
	public void productGroupSetUpNavigation() {
		try {
			clearAll();
			fetchBankNameList();
			fetchCurrencyList();
			fetchTransferMode();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "ProductGroupingSetUp.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/ProductGroupingSetUp.xhtml");
		} catch(NullPointerException  e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}
		catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}
	}

	// fetch all banks from bank Master
	public void fetchBankNameList() {
		try{
			List<BankMaster> lstBank = bankBranchDetailsService.getBankList();
			if(lstBank.size() != 0){
				for (BankMaster bankMaster : lstBank) {
					mapBankCode.put(bankMaster.getBankId(), bankMaster.getBankCode());
				}
				setLstBankList(lstBank);
			}
		} catch (Exception e) {
			log.info("Bank Name List time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}
	}

	// fetch all Currency from Currency Master
	public void fetchCurrencyList() {
		try{
			List<CurrencyMaster> lstDbCurrencyMaster = generalService.getCurrencyList();
			if(lstDbCurrencyMaster.size() != 0){
				for (CurrencyMaster currencyMaster : lstDbCurrencyMaster) {
					mapCurrencyCode.put(currencyMaster.getCurrencyId(), currencyMaster.getCurrencyCode());
				}
				setLstCurrencyMaster(lstDbCurrencyMaster);
			}
		} catch (Exception e) {
			log.info("Currency Name List time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}
	}

	// fetch transfer mode
	public void fetchTransferMode(){
		
		try{
			List<TransferMode> lstTransferMode = bankServiceRuleMasterService.getTransferMode();
			if(lstTransferMode.size() != 0){
				for (TransferMode transferMode : lstTransferMode) {
					mapTransferMode.put(transferMode.getTransferMode(), transferMode.getTransferModeDesc());
				}
				setTransferModeList(lstTransferMode);
			}
		} catch (Exception e) {
			log.info("Transfer Mode List time Error:  "+e);
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}
	}


	// search operation based on view
	public void viewBankCurrency(){
		try {
			if(getBankId() != null && getCurrencyId() != null){
				List<ProductGroup> lstProductGrp = personalRemittanceService.fetchProductGroup(getBankId(), getCurrencyId());
				if(lstProductGrp.size() != 0){

					for (ProductGroup productGroup : lstProductGrp) {

						ProductGroupingDataTable productGroupingDataTable = new ProductGroupingDataTable();

						productGroupingDataTable.setProductGroupId(productGroup.getProductGroupId());
						productGroupingDataTable.setProductGroupSerial(productGroup.getProductGroupSerial());
						productGroupingDataTable.setProductgroupdesc(productGroup.getProductgroupdesc());
						productGroupingDataTable.setAccountNo(productGroup.getDebitAccountNo());
						productGroupingDataTable.setProductMode(productGroup.getPrintMode());
						if(productGroup.getPrintMode() != null){
							productGroupingDataTable.setProductModeDesc(mapTransferMode.get(productGroup.getPrintMode()));
						}
						productGroupingDataTable.setTransactionLimit(productGroup.getTransactionLimit());
						productGroupingDataTable.setBankLimit(productGroup.getBankLimit());
						productGroupingDataTable.setBankId(productGroup.getBankId());
						productGroupingDataTable.setBankCode(mapBankCode.get(productGroup.getBankId()));
						productGroupingDataTable.setCurrencyId(productGroup.getCurrencyId());
						productGroupingDataTable.setCurrencyCode(mapCurrencyCode.get(productGroup.getCurrencyId()));
						productGroupingDataTable.setCreatedBy(productGroup.getCreatedBy());
						productGroupingDataTable.setCreatedDate(productGroup.getCreatedDate());
						productGroupingDataTable.setIsActive(productGroup.getIsActive());

						if (productGroup.getIsActive().equalsIgnoreCase(Constants.Yes)) {
							productGroupingDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						} else if (productGroup.getIsActive().equalsIgnoreCase(Constants.D)) {
							productGroupingDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						} else{
							// newly added record to data table
						}

						lstProductGroupingDataTable.add(productGroupingDataTable);
					}				
				}
			}

			clearAddedFields();

		}catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}  
	}

	// exit to home page
	public void exit()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		}  
	}

	// add to data to table
	public void addRecordToDataTable(){

		ProductGroupingDataTable productGroupingDataTable = new ProductGroupingDataTable();
		int i = 0;
		if(lstProductGroupingDataTable.size() != 0){
			i = lstProductGroupingDataTable.size();
		}

		productGroupingDataTable.setProductGroupId(getProductGroupId());
		if(getProductGroupSerial() != null){
			productGroupingDataTable.setProductGroupSerial(getProductGroupSerial());
		}else{
			productGroupingDataTable.setProductGroupSerial(new BigDecimal(i).add(BigDecimal.ONE));
		}

		productGroupingDataTable.setProductgroupdesc(getProductgroupdesc());
		productGroupingDataTable.setAccountNo(getAccountNo());
		productGroupingDataTable.setProductMode(getProductMode());
		productGroupingDataTable.setProductModeDesc(getProductModeDesc());
		productGroupingDataTable.setTransactionLimit(getTransactionLimit());
		productGroupingDataTable.setBankLimit(getBankLimit());
		productGroupingDataTable.setBankId(getBankId());
		productGroupingDataTable.setBankCode(mapBankCode.get(getBankId()));
		productGroupingDataTable.setCurrencyId(getCurrencyId());
		productGroupingDataTable.setCurrencyCode(mapCurrencyCode.get(getCurrencyId()));
		productGroupingDataTable.setCreatedBy(getCreatedBy());
		productGroupingDataTable.setCreatedDate(getCreatedDate());
		productGroupingDataTable.setIsActive(getIsActive());
		if (getIsActive() != null && getIsActive().equalsIgnoreCase(Constants.Yes)) {
			productGroupingDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
		} else if (getIsActive() != null && getIsActive().equalsIgnoreCase(Constants.D)) {
			productGroupingDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
		} else{
			// newly added record to data table
			productGroupingDataTable.setDynamicLabelForActivateDeactivate("");
		}

		lstProductGroupingDataTable.add(productGroupingDataTable);

		clearAddedFields();

	}

	// fetch product mode desc after selecting product mode
	public void fetchProductModeDesc(){
		if(getProductMode() != null && transferModeList.size() != 0){
			for (TransferMode transferMode : transferModeList) {
				if(getProductMode().equalsIgnoreCase(transferMode.getTransferMode())){
					setProductModeDesc(transferMode.getTransferModeDesc());
					break;
				}else{
					setProductModeDesc(null);
				}
			}
		}
	}

	// save Product Group 
	public void saveProductGroup(){
		if(lstProductGroupingDataTable.size() != 0){

			try {

				List<ProductGroup> lstSaveProduct = new ArrayList<ProductGroup>();

				for (ProductGroupingDataTable lstProdGrp : lstProductGroupingDataTable) {

					ProductGroup productGroup = new ProductGroup();

					productGroup.setProductGroupId(lstProdGrp.getProductGroupId());
					productGroup.setProductGroupSerial(lstProdGrp.getProductGroupSerial());
					productGroup.setProductgroupdesc(lstProdGrp.getProductgroupdesc());
					productGroup.setBankId(lstProdGrp.getBankId());
					productGroup.setBankCode(lstProdGrp.getBankCode());
					productGroup.setBankLimit(lstProdGrp.getBankLimit());
					productGroup.setCurrencyId(lstProdGrp.getCurrencyId());
					productGroup.setCurrencyCode(lstProdGrp.getCurrencyCode());
					productGroup.setDebitAccountNo(lstProdGrp.getAccountNo());
					if(lstProdGrp.getIsActive() != null){
						productGroup.setIsActive(lstProdGrp.getIsActive());
					}else{
						productGroup.setIsActive(Constants.Yes);
					}
					productGroup.setPrintMode(lstProdGrp.getProductMode());
					productGroup.setTransactionLimit(lstProdGrp.getTransactionLimit());

					if(lstProdGrp.getProductGroupId() != null){
						productGroup.setCreatedBy(lstProdGrp.getCreatedBy());
						productGroup.setCreatedDate(lstProdGrp.getCreatedDate());
						productGroup.setModifiedBy(sessionStateManage.getUserName());
						productGroup.setModifiedDate(new Date());
					}else{
						productGroup.setCreatedBy(sessionStateManage.getUserName());
						productGroup.setCreatedDate(new Date());
					}


					lstSaveProduct.add(productGroup);

				}

				personalRemittanceService.saveProductGroup(lstSaveProduct);

				RequestContext.getCurrentInstance().execute("complete.show();");

			} catch (AMGException e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("errormsg.show();");
			}
		}
	}

	// clear product group all
	public void clearAddedFields(){
		setProductGroupId(null);
		setProductgroupdesc(null);
		setProductGroupSerial(null);
		setAccountNo(null);
		setTransactionLimit(null);
		setBankLimit(null);
		setProductMode(null);
		setProductModeDesc(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setIsActive(null);
		setDynamicLabelForActivateDeactivate(null);
	}

	// clear product group all
	public void clearAll(){
		setProductGroupId(null);
		setBankId(null);
		setCurrencyId(null);
		setProductgroupdesc(null);
		setProductGroupSerial(null);
		setAccountNo(null);
		setTransactionLimit(null);
		setBankLimit(null);
		setProductMode(null);
		setProductModeDesc(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setIsActive(null);
		setDynamicLabelForActivateDeactivate(null);
		if(lstProductGroupingDataTable != null || !lstProductGroupingDataTable.isEmpty()){
			lstProductGroupingDataTable.clear();
		}
		if(mapTransferMode != null || !mapTransferMode.isEmpty()){
			mapTransferMode.clear();
		}
		if(mapBankCode != null || !mapBankCode.isEmpty()){
			mapBankCode.clear();
		}
		if(mapCurrencyCode != null || !mapCurrencyCode.isEmpty()){
			mapCurrencyCode.clear();
		}
		
	}

	// edit dataTable record
	public void editDataTableRecord(ProductGroupingDataTable productGrpData){

		setProductGroupId(productGrpData.getProductGroupId());
		setProductgroupdesc(productGrpData.getProductgroupdesc());
		setProductGroupSerial(productGrpData.getProductGroupSerial());
		setAccountNo(productGrpData.getAccountNo());
		setProductMode(productGrpData.getProductMode());
		setProductModeDesc(productGrpData.getProductModeDesc());
		setTransactionLimit(productGrpData.getTransactionLimit());
		setBankLimit(productGrpData.getBankLimit());
		setCreatedBy(productGrpData.getCreatedBy());
		setCreatedDate(productGrpData.getCreatedDate());
		setIsActive(productGrpData.getIsActive());
		setDynamicLabelForActivateDeactivate(productGrpData.getDynamicLabelForActivateDeactivate());

		if(lstProductGroupingDataTable.size() != 0){
			lstProductGroupingDataTable.remove(productGrpData);
		}
	}

	public void checkStatusType(ProductGroupingDataTable productGrpData) {
		try{ 
			if (productGrpData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				personalRemittanceService.updateProductGroupIsActive(productGrpData.getProductGroupId(), Constants.D);
				if(lstProductGroupingDataTable != null || !lstProductGroupingDataTable.isEmpty()){
					lstProductGroupingDataTable.clear();
				}
				viewBankCurrency();
				setAlertMsg("Successfully De-Activated");
				RequestContext.getCurrentInstance().execute("activateDeactive.show();");
			} else if (productGrpData.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				personalRemittanceService.updateProductGroupIsActive(productGrpData.getProductGroupId(), Constants.Yes);
				if(lstProductGroupingDataTable != null || !lstProductGroupingDataTable.isEmpty()){
					lstProductGroupingDataTable.clear();
				}
				viewBankCurrency();
				setAlertMsg("Successfully Activated");
				RequestContext.getCurrentInstance().execute("activateDeactive.show();");
			} else{
				// newly added record to data table
			}
		} catch(Exception e){
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("errormsg.show();");
		} 
	}


	// click ok Button after save
	public void clickOnOK() {
		clearAll();
	}

	// getter and setters - objects
	public List<BankMaster> getLstBankList() {
		return lstBankList;
	}
	public void setLstBankList(List<BankMaster> lstBankList) {
		this.lstBankList = lstBankList;
	}

	public List<CurrencyMaster> getLstCurrencyMaster() {
		return lstCurrencyMaster;
	}
	public void setLstCurrencyMaster(List<CurrencyMaster> lstCurrencyMaster) {
		this.lstCurrencyMaster = lstCurrencyMaster;
	}

	public List<ProductGroupingDataTable> getLstProductGroupingDataTable() {
		return lstProductGroupingDataTable;
	}
	public void setLstProductGroupingDataTable(List<ProductGroupingDataTable> lstProductGroupingDataTable) {
		this.lstProductGroupingDataTable = lstProductGroupingDataTable;
	}

	public Map<BigDecimal, String> getMapBankCode() {
		return mapBankCode;
	}
	public void setMapBankCode(Map<BigDecimal, String> mapBankCode) {
		this.mapBankCode = mapBankCode;
	}

	public Map<BigDecimal, String> getMapCurrencyCode() {
		return mapCurrencyCode;
	}
	public void setMapCurrencyCode(Map<BigDecimal, String> mapCurrencyCode) {
		this.mapCurrencyCode = mapCurrencyCode;
	}

	public List<TransferMode> getTransferModeList() {
		return transferModeList;
	}
	public void setTransferModeList(List<TransferMode> transferModeList) {
		this.transferModeList = transferModeList;
	}

	public Map<String, String> getMapTransferMode() {
		return mapTransferMode;
	}
	public void setMapTransferMode(Map<String, String> mapTransferMode) {
		this.mapTransferMode = mapTransferMode;
	}

	// getter and setters - variables
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getAlertMsg() {
		return alertMsg;
	}
	public void setAlertMsg(String alertMsg) {
		this.alertMsg = alertMsg;
	}

	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public String getProductgroupdesc() {
		return productgroupdesc;
	}
	public void setProductgroupdesc(String productgroupdesc) {
		this.productgroupdesc = productgroupdesc;
	}

	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getProductMode() {
		return productMode;
	}
	public void setProductMode(String productMode) {
		this.productMode = productMode;
	}

	public BigDecimal getTransactionLimit() {
		return transactionLimit;
	}
	public void setTransactionLimit(BigDecimal transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	public BigDecimal getBankLimit() {
		return bankLimit;
	}
	public void setBankLimit(BigDecimal bankLimit) {
		this.bankLimit = bankLimit;
	}

	public String getProductModeDesc() {
		return productModeDesc;
	}
	public void setProductModeDesc(String productModeDesc) {
		this.productModeDesc = productModeDesc;
	}

	public BigDecimal getProductGroupId() {
		return productGroupId;
	}
	public void setProductGroupId(BigDecimal productGroupId) {
		this.productGroupId = productGroupId;
	}

	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getProductGroupSerial() {
		return productGroupSerial;
	}
	public void setProductGroupSerial(BigDecimal productGroupSerial) {
		this.productGroupSerial = productGroupSerial;
	}

	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}




}
