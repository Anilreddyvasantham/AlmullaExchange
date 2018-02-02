package com.amg.exchange.treasury.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.LanguageType;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.RoleMaster;
import com.amg.exchange.remittance.model.RoutingDetails;
import com.amg.exchange.remittance.model.ViewBankDetails;
import com.amg.exchange.remittance.service.ICustomerBankService;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.model.BankPrefix;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;
import com.amg.exchange.util.WarningHandler;


@Component(value = "bankPrefixBean")
@Scope("session")
public class BankPrefixBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(BankPrefixBean.class);

	SessionStateManage sessionStateManage = new SessionStateManage();

	private String bankCode;
	private BigDecimal bankId;
	private String bankPrefix;

	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private String approvedBy;
	private Date approvedDate;
	private String remarks;
	private boolean hideEdit = false;
	private String errorMessage;
	private boolean booRenderSave;
	private boolean booRenderDatatable;
	private Boolean booSubmit;
	private boolean booSubmitHide = false;
	private String dynamicLabelForActivateDeactivate;
	private Boolean booRenderEditButton;
	private Boolean checkSave;
	private Boolean booCheckUpdate = false;
	private String currenctStatus;

	private List<BankPrefixDataTable> lstMainBankPrefixDataTable;
	private List<BankPrefixDataTable> lstViewBankPrefixDataTable;

	private List<BankPrefixDataTable> lstApprovalBankPrefixDataTable;
	private String bankCodeApproval;
	private Boolean booRenderDatatableApproval;

	private List<ViewBankDetails> localbankList;

	private BigDecimal bankPrefixPkId;

	@Autowired
	IGeneralService<T> iGeneralService;

	@Autowired
	ICustomerBankService icustomerBankService;



	public Boolean getBooRenderDatatableApproval() {
		return booRenderDatatableApproval;
	}
	public void setBooRenderDatatableApproval(Boolean booRenderDatatableApproval) {
		this.booRenderDatatableApproval = booRenderDatatableApproval;
	}
	
	public String getBankCodeApproval() {
		return bankCodeApproval;
	}
	public void setBankCodeApproval(String bankCodeApproval) {
		this.bankCodeApproval = bankCodeApproval;
	}

	public List<BankPrefixDataTable> getLstApprovalBankPrefixDataTable() {
		return lstApprovalBankPrefixDataTable;
	}
	public void setLstApprovalBankPrefixDataTable(List<BankPrefixDataTable> lstApprovalBankPrefixDataTable) {
		this.lstApprovalBankPrefixDataTable = lstApprovalBankPrefixDataTable;
	}
	
	public Boolean getCheckSave() {
		return checkSave;
	}
	public void setCheckSave(Boolean checkSave) {
		this.checkSave = checkSave;
	}
	
	public Boolean getBooCheckUpdate() {
		return booCheckUpdate;
	}
	public void setBooCheckUpdate(Boolean booCheckUpdate) {
		this.booCheckUpdate = booCheckUpdate;
	}
	
	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}
	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}
	
	public Boolean getBooRenderEditButton() {
		return booRenderEditButton;
	}
	public void setBooRenderEditButton(Boolean booRenderEditButton) {
		this.booRenderEditButton = booRenderEditButton;
	}
	
	public BigDecimal getBankPrefixPkId() {
		return bankPrefixPkId;
	}
	public void setBankPrefixPkId(BigDecimal bankPrefixPkId) {
		this.bankPrefixPkId = bankPrefixPkId;
	}
	
	public boolean isBooSubmitHide() {
		return booSubmitHide;
	}
	public void setBooSubmitHide(boolean booSubmitHide) {
		this.booSubmitHide = booSubmitHide;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	public boolean isBooRenderSave() {
		return booRenderSave;
	}
	
	public void setBooRenderSave(boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}
	public boolean isBooRenderDatatable() {
		return booRenderDatatable;
	}
	
	public void setBooRenderDatatable(boolean booRenderDatatable) {
		this.booRenderDatatable = booRenderDatatable;
	}
	public Boolean getBooSubmit() {
		return booSubmit;
	}
	
	public void setBooSubmit(Boolean booSubmit) {
		this.booSubmit = booSubmit;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	public boolean isHideEdit() {
		return hideEdit;
	}
	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
	}
	
	public List<ViewBankDetails> getLocalbankList() {
		return localbankList;
	}
	public void setLocalbankList(List<ViewBankDetails> localbankList) {
		this.localbankList = localbankList;
	}

	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	public BigDecimal getBankId() {
		return bankId;
	}
	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}
	
	public String getBankPrefix() {
		return bankPrefix;
	}
	public void setBankPrefix(String bankPrefix) {
		this.bankPrefix = bankPrefix;
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
	
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
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
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<BankPrefixDataTable> getLstMainBankPrefixDataTable() {
		return lstMainBankPrefixDataTable;
	}
	public void setLstMainBankPrefixDataTable(List<BankPrefixDataTable> lstMainBankPrefixDataTable) {
		this.lstMainBankPrefixDataTable = lstMainBankPrefixDataTable;
	}
	
	public List<BankPrefixDataTable> getLstViewBankPrefixDataTable() {
		return lstViewBankPrefixDataTable;
	}
	public void setLstViewBankPrefixDataTable(List<BankPrefixDataTable> lstViewBankPrefixDataTable) {
		this.lstViewBankPrefixDataTable = lstViewBankPrefixDataTable;
	}
	
	public String getCurrenctStatus() {
		return currenctStatus;
	}
	public void setCurrenctStatus(String currenctStatus) {
		this.currenctStatus = currenctStatus;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationforBankPrefix()
	{
		clearValues();
		setBooSubmit(false);
		loadLocalBanksFromView();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankprefix.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankprefix.xhtml");
		} catch (Exception e) {
			log.error("Problem Occured in pageNavigationforBankPrefix() method");
		}
	}

	private void loadLocalBanksFromView()
	{
		List<ViewBankDetails> lstViewBankDetails = iGeneralService.getLocalBankListFromView(sessionStateManage.getCountryId());
		setLocalbankList(lstViewBankDetails);
	}

	public void populateBankPrefix()
	{

	}

	public void addRecordsToDataTable()
	{

		if(getBankCode()== null)
		{
			RequestContext.getCurrentInstance().execute("bankCodeEmpty.show();");
			return;
		}

		if(getBankPrefix()== null)
		{
			RequestContext.getCurrentInstance().execute("bankPrefixEmpty.show();");
			return;
		}

		if (getBankPrefixPkId()  == null)
		{
			List<BankPrefix> lstBankPrefix=icustomerBankService.getBankPrefixBasedOnPrefix(getBankCode(), getBankPrefix());
			if(lstBankPrefix!=null && lstBankPrefix.size()>0)
			{
				RequestContext.getCurrentInstance().execute("bankPrefixExistDb.show();");
				return;
			}
		}


		boolean duplicateCheck=checkDuplicate(getBankCode() ,getBankPrefix());

		if(duplicateCheck)
		{
			RequestContext.getCurrentInstance().execute("duplicatePrefix.show();");
			return;
		}
		setBooSubmitHide(false);
		List<BankPrefixDataTable> lstView=getLstViewBankPrefixDataTable();

		List<BankPrefixDataTable> addBankPrefix= new ArrayList<BankPrefixDataTable>();

		if(lstView== null || lstView.size()==0)
		{
			BankPrefixDataTable bankPrefixDataTable = new BankPrefixDataTable();
			List<ViewBankDetails> listViewBankDetails =icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getBankCode());

			if(listViewBankDetails!=null && listViewBankDetails.size()>0)
			{
				ViewBankDetails viewBankDetails =listViewBankDetails.get(0);
				bankPrefixDataTable.setBankName(viewBankDetails.getBankShortName()+"-"+viewBankDetails.getBankFullName());
				bankPrefixDataTable.setBankId(viewBankDetails.getChequeBankId());

			}

			bankPrefixDataTable.setBankPrefix(getBankPrefix());
			bankPrefixDataTable.setBankCode(getBankCode());


			if (getBankPrefixPkId()  != null) {
				bankPrefixDataTable.setBankPrefixPkId(getBankPrefixPkId());
				bankPrefixDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				bankPrefixDataTable.setCurrenctStatus(getCurrenctStatus());
				bankPrefixDataTable.setRenderEditButton(getBooRenderEditButton());
				bankPrefixDataTable.setIsActive(getIsActive());
				bankPrefixDataTable.setRemarks(getRemarks());
				bankPrefixDataTable.setCheckSave(getCheckSave());
				bankPrefixDataTable.setCreatedBy(getCreatedBy());
				bankPrefixDataTable.setCreatedDate(getCreatedDate());
				bankPrefixDataTable.setApprovedBy(getApprovedBy());
				bankPrefixDataTable.setApprovedDate(getApprovedDate());
				bankPrefixDataTable.setBooCheckUpdate(getBooCheckUpdate());
				bankPrefixDataTable.setModifiedBy(sessionStateManage.getUserName());
				bankPrefixDataTable.setModifiedDate(new Date());
				bankPrefixDataTable.setBooCheckDelete(false);
			} else {
				bankPrefixDataTable.setIsActive(Constants.U);
				bankPrefixDataTable.setCurrenctStatus(null);
				bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				bankPrefixDataTable.setRenderEditButton(true);
				bankPrefixDataTable.setCheckSave(true);
				bankPrefixDataTable.setCreatedBy(sessionStateManage.getUserName());
				bankPrefixDataTable.setCreatedDate(new Date());
				bankPrefixDataTable.setBooCheckUpdate(true);
				bankPrefixDataTable.setBooCheckDelete(false);
			}


			addBankPrefix.add(bankPrefixDataTable);

			List<BankPrefixDataTable> lstbankPrefixDataTable = getLstMainBankPrefixDataTable();

			if(lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size()>0)
			{
				addBankPrefix.addAll(lstbankPrefixDataTable);
			}
			setLstMainBankPrefixDataTable(null);
			setLstMainBankPrefixDataTable(addBankPrefix);
		}else
		{

		}
		setBankCode(null);
		setBankPrefix(null);
		setBooRenderDatatable(true);

		setBooSubmit(true);
		setHideEdit(false);
		clearEdit();
		//setBooClear(false);
	}

	private boolean checkDuplicate(String bankCode,String bankPrefix)
	{
		boolean checkDuplicate=false;
		List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();
		if(lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size()>0)
		{
			for(BankPrefixDataTable bankPrefixDt :lstbankPrefixDataTable)
			{
				if(bankPrefixDt.getBankCode().equalsIgnoreCase(bankCode))
				{ 
					if(bankPrefixDt.getBankPrefix().equalsIgnoreCase(bankPrefix))
					{
						checkDuplicate=true;
						break;
					}else
					{
						checkDuplicate=false;
					}
				}else
				{
					checkDuplicate=false;
				}
			}
		}

		return checkDuplicate;

	}

	public void viewDetails()
	{
		if(getBankCode()== null)
		{
			RequestContext.getCurrentInstance().execute("bankCodeEmpty.show();");
			return;
		}
		List<BankPrefix> lstBankPrefix= icustomerBankService.getBankPrefix(getBankCode());
		List<BankPrefixDataTable> addBankPrefix= new ArrayList<BankPrefixDataTable>();
		//List<BankPrefixDataTable> lstbankPrefixDataTable = getLstMainBankPrefixDataTable();

		if(lstBankPrefix!=null && lstBankPrefix.size()>0)
		{

			for(BankPrefix bankPrefix :lstBankPrefix)
			{
				BankPrefixDataTable bankPrefixDataTable = new BankPrefixDataTable();
				List<ViewBankDetails> listViewBankDetails =icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getBankCode());
				bankPrefixDataTable.setBankPrefix(bankPrefix.getBankPrefix());
				bankPrefixDataTable.setBankCode(getBankCode());
				bankPrefixDataTable.setBankPrefixPkId(bankPrefix.getBankPrefixId());
				bankPrefixDataTable.setIsActive(bankPrefix.getIsActive());
				if(listViewBankDetails!=null && listViewBankDetails.size()>0)
				{
					ViewBankDetails viewBankDetails =listViewBankDetails.get(0);
					bankPrefixDataTable.setBankName(viewBankDetails.getBankShortName()+"-"+viewBankDetails.getBankFullName());
					bankPrefixDataTable.setBankId(viewBankDetails.getChequeBankId());

				}
				if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					bankPrefixDataTable.setCurrenctStatus(Constants.ACTIVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setCheckSave(false);
					bankPrefixDataTable.setBooCheckDelete(false);
				} else if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.D)) {
					bankPrefixDataTable.setCurrenctStatus(Constants.IN_ACTIVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					bankPrefixDataTable.setRemarks(bankPrefix.getRemarks());
					bankPrefixDataTable.setRenderEditButton(false);
					bankPrefixDataTable.setCheckSave(false);
					bankPrefixDataTable.setBooCheckDelete(false);
				} else if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.U) && bankPrefix.getModifiedBy() == null && bankPrefix.getModifiedDate() == null && bankPrefix.getApprovedBy() == null
						&& bankPrefix.getApprovedDate() == null && bankPrefix.getRemarks() == null) {
					bankPrefixDataTable.setCurrenctStatus(Constants.PENDING_FOR_APPROVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					// relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setCheckSave(false);
					bankPrefixDataTable.setBooCheckDelete(false);
				}else 
				{
					bankPrefixDataTable.setCurrenctStatus(Constants.PENDING_FOR_APPROVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setBooCheckDelete(false);
					bankPrefixDataTable.setCheckSave(false);
				}

				if (bankPrefix.getBankPrefixId() != null) {
					bankPrefixDataTable.setModifiedBy(bankPrefix.getModifiedBy());
					bankPrefixDataTable.setModifiedDate(bankPrefix.getModifiedDate());
					bankPrefixDataTable.setBooCheckUpdate(false);
				}

				bankPrefixDataTable.setApprovedBy(bankPrefix.getApprovedBy());
				bankPrefixDataTable.setApprovedDate(bankPrefix.getApprovedDate());
				bankPrefixDataTable.setCreatedBy(bankPrefix.getCreatedBy());
				bankPrefixDataTable.setCreatedDate(bankPrefix.getCreatedDate());

				addBankPrefix.add(bankPrefixDataTable);

			}

			//List<BankPrefixDataTable> localLstbankPrefixDataTable = new CopyOnWriteArrayList <BankPrefixDataTable>();
			List<BankPrefixDataTable> addLstbankPrefixDataTable = new ArrayList <BankPrefixDataTable>();


			/*if(lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size()>0 )
			{
				localLstbankPrefixDataTable.addAll(lstbankPrefixDataTable);
				for(BankPrefixDataTable lbankPrefixDataTable :addBankPrefix)
				{
					for(int i=0; i<localLstbankPrefixDataTable.size() ; i++)
					{
						BankPrefixDataTable localBankPrefixDataTable=localLstbankPrefixDataTable.get(i);
						if(localBankPrefixDataTable.getBankCode().equalsIgnoreCase(lbankPrefixDataTable.getBankCode()) && localBankPrefixDataTable.getBankPrefix().equalsIgnoreCase(lbankPrefixDataTable.getBankPrefix()))
						{
							localLstbankPrefixDataTable.remove(i);
						}
					}
				}
			}

			if(localLstbankPrefixDataTable!=null && localLstbankPrefixDataTable.size()>0)
			{
				addLstbankPrefixDataTable.addAll(localLstbankPrefixDataTable);
			}*/
			if(addBankPrefix!=null && addBankPrefix.size()>0)
			{
				addLstbankPrefixDataTable.addAll(addBankPrefix);
			}

			if(addLstbankPrefixDataTable.size()==0)
			{
				return;
			}else
			{
				setLstMainBankPrefixDataTable(null);
				setLstMainBankPrefixDataTable(addLstbankPrefixDataTable);
			}

			setBankCode(null);
			setBankPrefix(null);
			setBooRenderDatatable(true);
			setBooSubmit(true);
			setHideEdit(false);
		}else
		{
			setBooSubmit(false);
			setBooRenderDatatable(false);
			setHideEdit(false);
			setLstMainBankPrefixDataTable(null);
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			return;
		}
	}

	public void editRecord(BankPrefixDataTable bankPrefixDataTable)
	{
		log.info("::::::::::::::::::::::::::::Entered into editRecord()::::::::::::::::::");
		List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();

		setBankPrefixPkId(bankPrefixDataTable.getBankPrefixPkId());
		setBankCode(bankPrefixDataTable.getBankCode());
		setBankPrefix(bankPrefixDataTable.getBankPrefix());
		setDynamicLabelForActivateDeactivate(bankPrefixDataTable.getDynamicLabelForActivateDeactivate());
		//setRemarks(bankPrefixDataTable.getRemarks());
		setBooRenderEditButton(bankPrefixDataTable.getRenderEditButton());
		setIsActive(Constants.U);
		setCurrenctStatus(Constants.PENDING_FOR_APPROVE);
		setCheckSave(bankPrefixDataTable.getCheckSave());
		setBooCheckUpdate(true);
		//setBooCheckDelete(true);
		setCreatedBy(bankPrefixDataTable.getCreatedBy());
		setCreatedDate(bankPrefixDataTable.getCreatedDate());
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);

		lstbankPrefixDataTable.remove(bankPrefixDataTable);
		setHideEdit(true);
		setBooSubmit(true);
		setBooSubmitHide(true);

		if (lstbankPrefixDataTable.size() == 0) {
			setBooRenderSave(true);
			setBooRenderDatatable(true);
		}
		setLstMainBankPrefixDataTable(null);
		setLstMainBankPrefixDataTable(lstbankPrefixDataTable);
	}

	public void checkStatusType(BankPrefixDataTable bankPrefixDataTable)
	{
		try {
			setRemarks(null);
			if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				bankPrefixDataTable.setRemarkCheck(true);
				setApprovedBy(bankPrefixDataTable.getApprovedBy());
				setApprovedDate(bankPrefixDataTable.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
			} else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				removeRecord(bankPrefixDataTable);
			}
			else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && bankPrefixDataTable.getRemarks() == null && bankPrefixDataTable.getApprovedBy() == null && bankPrefixDataTable.getApprovedDate() == null
					&& bankPrefixDataTable.getModifiedBy() == null && bankPrefixDataTable.getModifiedDate() == null && bankPrefixDataTable.getRemarks() == null) {
				bankPrefixDataTable.setBooCheckDelete(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			}
			else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && bankPrefixDataTable.getRemarks() == null && bankPrefixDataTable.getApprovedBy() == null && bankPrefixDataTable.getApprovedDate() == null && bankPrefixDataTable.getModifiedBy() == null
					&& bankPrefixDataTable.getModifiedDate() == null) {
				removeRecordFromDB(bankPrefixDataTable);
			} else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
				removeRecord(bankPrefixDataTable);
			} else if (bankPrefixDataTable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)) {
				RequestContext.getCurrentInstance().execute("couldnot.show();");
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}

	public void removeRecord(BankPrefixDataTable bankPrefixDataTable) 
	{
		if (bankPrefixDataTable.getBankPrefixPkId() == null) {
			if (bankPrefixDataTable.getCheckSave().equals(true)) {
				List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();

				lstbankPrefixDataTable.remove(bankPrefixDataTable);
				if (lstbankPrefixDataTable.size() <= 0) {
					setBooRenderSave(true);
					setBooRenderDatatable(false);
					setBooSubmit(false);
				}
			}
		} else {
			deActiveRecord(bankPrefixDataTable);
			//setLstMainBankPrefixDataTable(null);
			//viewDetails();a
			loadBankPrefix(bankPrefixDataTable);
			//bankIndDTList.addAll(banIndViewList);
		}
	}

	public void removeRecordFromDB(BankPrefixDataTable bankPrefixDataTable) throws Exception {


		icustomerBankService.deleteFromDb(bankPrefixDataTable.getBankPrefixPkId());
	}

	public void deActiveRecord(BankPrefixDataTable bankPrefixDataTable) {

		icustomerBankService.updateBankPrefix(bankPrefixDataTable);
	}

	public void saveBankPrefix()
	{
		List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();

		if(lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size()>0)
		{
			for(BankPrefixDataTable bankPrefixDataTable :lstbankPrefixDataTable)
			{
				BankPrefix bankPrefix = new BankPrefix();
				bankPrefix.setBankPrefixId(bankPrefixDataTable.getBankPrefixPkId());
				bankPrefix.setBankCode(bankPrefixDataTable.getBankCode());
				bankPrefix.setBankPrefix(bankPrefixDataTable.getBankPrefix());

				BankMaster bankMaster =  new BankMaster();
				bankMaster.setBankId(bankPrefixDataTable.getBankId());
				bankPrefix.setFsBankMaster(bankMaster);

				bankPrefix.setIsActive(bankPrefixDataTable.getIsActive());
				bankPrefix.setCreatedBy(bankPrefixDataTable.getCreatedBy());
				bankPrefix.setCreatedDate(bankPrefixDataTable.getCreatedDate());

				bankPrefix.setApprovedBy(bankPrefixDataTable.getApprovedBy());
				bankPrefix.setApprovedDate(bankPrefixDataTable.getApprovedDate());

				bankPrefix.setModifiedBy(bankPrefixDataTable.getModifiedBy());
				bankPrefix.setModifiedDate(bankPrefixDataTable.getModifiedDate());
				bankPrefix.setRemarks(bankPrefixDataTable.getRemarks());
				icustomerBankService.saveOrUpdate(bankPrefix);


			}
			RequestContext.getCurrentInstance().execute("complete.show();");
		}
	}

	public void remarkSelectedRecord() throws IOException {
		log.info("::::::::::::::::::remarkSelectedRecord() method called:::::::::::::::");

		List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();

		for (BankPrefixDataTable bankPrefixDataTable : lstbankPrefixDataTable) {
			if (bankPrefixDataTable.getRemarkCheck() != null) {
				if (bankPrefixDataTable.getRemarkCheck().equals(true)) {
					if (getRemarks() != null && !getRemarks().trim().equals("")) {
						bankPrefixDataTable.setRemarks(getRemarks());
						removeRecord(bankPrefixDataTable);
						setRemarks(null);
					} else {

						setErrorMessage(WarningHandler.showWarningMessage("lbl.art.remarks", new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId") : "1")));
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
				}
			}
		}
	}

	public void cancelRemarks() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankprefix.xhtml");
		} catch (Exception e) {
			log.error(":::::::::::::Problem oocured in cancelRemarks()::::::::::" + e.getCause());
		}
	}
	public void confirmPermanentDelete() {
		List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();
		List<BankPrefixDataTable> localLstbankPrefixDataTable = new CopyOnWriteArrayList <BankPrefixDataTable>();
		//List<BankPrefixDataTable> newLstbankPrefixDataTable = new ArrayList<BankPrefixDataTable>();

		if (lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size() > 0) {
			localLstbankPrefixDataTable.addAll(lstbankPrefixDataTable);
			for (int i=0;i< lstbankPrefixDataTable.size(); i++) {

				BankPrefixDataTable bankPrefixDataTable=lstbankPrefixDataTable.get(i);
				if (bankPrefixDataTable.getBooCheckDelete()) {
					try {

						removeRecordFromDB(bankPrefixDataTable);
						localLstbankPrefixDataTable.remove(i);
						setLstMainBankPrefixDataTable(localLstbankPrefixDataTable);
						loadBankPrefix(bankPrefixDataTable);
					} catch (Exception e) {

						setErrorMessage(e.toString());
						RequestContext.getCurrentInstance().execute("error.show();");
						return;
					}
				}
			}
		}
	} 

	public void clearValues()
	{
		setBankCode(null);
		setBankPrefix(null);
		setLstMainBankPrefixDataTable(null);
		setBooSubmitHide(false);
		setBooRenderDatatable(false);

		setBooSubmit(false);
	}

	public void clickOnOKSave() {
		setLstMainBankPrefixDataTable(null);
		setBankCode(null);
		setBankPrefix(null);
		setBooRenderDatatable(false);

		setBooSubmit(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankprefix.xhtml");
		} catch (Exception e) {
			log.error("::::::::::::::::::Problem Occured in clickOnOKSave()::::::::::::::");
		}
	}

	public void loadBankPrefix(BankPrefixDataTable pBankPrefixDataTable)
	{

		List<BankPrefix> lstBankPrefix= icustomerBankService.getBankPrefix(pBankPrefixDataTable.getBankCode());
		List<BankPrefixDataTable> addBankPrefix= new ArrayList<BankPrefixDataTable>();


		if(lstBankPrefix!=null && lstBankPrefix.size()>0)
		{


			for(BankPrefix bankPrefix :lstBankPrefix)
			{

				BankPrefixDataTable bankPrefixDataTable = new BankPrefixDataTable();
				List<ViewBankDetails> listViewBankDetails =icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), pBankPrefixDataTable.getBankCode());
				bankPrefixDataTable.setBankPrefix(bankPrefix.getBankPrefix());
				bankPrefixDataTable.setBankCode(bankPrefix.getBankCode());
				bankPrefixDataTable.setBankPrefixPkId(bankPrefix.getBankPrefixId());
				bankPrefixDataTable.setIsActive(bankPrefix.getIsActive());
				if(listViewBankDetails!=null && listViewBankDetails.size()>0)
				{
					ViewBankDetails viewBankDetails =listViewBankDetails.get(0);
					bankPrefixDataTable.setBankName(viewBankDetails.getBankShortName()+"-"+viewBankDetails.getBankFullName());
					bankPrefixDataTable.setBankId(viewBankDetails.getChequeBankId());

				}
				if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					bankPrefixDataTable.setCurrenctStatus(Constants.ACTIVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setCheckSave(false);
				} else if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.D)) {
					bankPrefixDataTable.setCurrenctStatus(Constants.IN_ACTIVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
					bankPrefixDataTable.setRemarks(bankPrefix.getRemarks());
					bankPrefixDataTable.setRenderEditButton(false);
					bankPrefixDataTable.setCheckSave(false);
				} else if (bankPrefix.getIsActive().equalsIgnoreCase(Constants.U) && bankPrefix.getModifiedBy() == null && bankPrefix.getModifiedDate() == null && bankPrefix.getApprovedBy() == null
						&& bankPrefix.getApprovedDate() == null && bankPrefix.getRemarks() == null) {
					bankPrefixDataTable.setCurrenctStatus(Constants.PENDING_FOR_APPROVE);
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					// relationsDT.setRemarks(relationsDesc.getRelations().getRemarks());
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setCheckSave(false);
					bankPrefixDataTable.setBooCheckDelete(false);
				}else 
				{
					bankPrefixDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					bankPrefixDataTable.setRenderEditButton(true);
					bankPrefixDataTable.setBooCheckDelete(false);
					bankPrefixDataTable.setCheckSave(false);
				}

				if (bankPrefix.getBankPrefixId() != null) {
					bankPrefixDataTable.setModifiedBy(bankPrefix.getModifiedBy());
					bankPrefixDataTable.setModifiedDate(bankPrefix.getModifiedDate());
					bankPrefixDataTable.setBooCheckUpdate(false);
				}

				bankPrefixDataTable.setApprovedBy(bankPrefix.getApprovedBy());
				bankPrefixDataTable.setApprovedDate(bankPrefix.getApprovedDate());
				bankPrefixDataTable.setCreatedBy(bankPrefix.getCreatedBy());
				bankPrefixDataTable.setCreatedDate(bankPrefix.getCreatedDate());

				addBankPrefix.add(bankPrefixDataTable);
			}

			List<BankPrefixDataTable> lstbankPrefixDataTable =getLstMainBankPrefixDataTable();
			List<BankPrefixDataTable> localLstbankPrefixDataTable = new CopyOnWriteArrayList <BankPrefixDataTable>();
			List<BankPrefixDataTable> addLstbankPrefixDataTable = new ArrayList <BankPrefixDataTable>();


			if(lstbankPrefixDataTable!=null && lstbankPrefixDataTable.size()>0 )
			{
				localLstbankPrefixDataTable.addAll(lstbankPrefixDataTable);
				for(BankPrefixDataTable bankPrefixDataTable :addBankPrefix)
				{
					for(int i=0; i<localLstbankPrefixDataTable.size() ; i++)
					{
						BankPrefixDataTable localBankPrefixDataTable=localLstbankPrefixDataTable.get(i);
						if(localBankPrefixDataTable.getBankCode().equalsIgnoreCase(bankPrefixDataTable.getBankCode()) && localBankPrefixDataTable.getBankPrefix().equalsIgnoreCase(bankPrefixDataTable.getBankPrefix()))
						{
							localLstbankPrefixDataTable.remove(i);
						}
					}
				}
			}

			addLstbankPrefixDataTable.addAll(localLstbankPrefixDataTable);
			addLstbankPrefixDataTable.addAll(addBankPrefix);

			setLstMainBankPrefixDataTable(null);
			setLstMainBankPrefixDataTable(addLstbankPrefixDataTable);

		}else
		{
			if(getLstMainBankPrefixDataTable()==null)
			{
				RequestContext.getCurrentInstance().execute("noRecords.show();");
				return;
			}


		}
	}
	public void clearEdit()
	{
		setBankPrefixPkId(null);
		setBankCode(null);
		setBankPrefix(null);
		setDynamicLabelForActivateDeactivate(null);

		//setBooRenderEditButton(bankPrefixDataTable.getRenderEditButton());
		setIsActive(null);
		setCheckSave(null);
		setBooCheckUpdate(false);
		//setBooCheckDelete(true);
		setCreatedBy(null);
		setCreatedDate(null);
	}

	public void exit() {
		try {
			List<RoleMaster> rolList = iGeneralService.getRoleList(new BigDecimal(sessionStateManage.getRoleId()));
			if (rolList != null && !rolList.isEmpty() && rolList.get(0).getRoleName().equalsIgnoreCase(Constants.USER_ROLE_MANAGER)) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error(":::::::::::::::::::::::::::::::::::::::::::Problem Occured in exit():::::::::::::::::::::::::::");
		}
	}

	public void pageNavigationforBankPrefixApproval()
	{
		setBooRenderDatatableApproval(false);
		setBankCodeApproval(null);
		loadLocalBanksFromView();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "bankprefixapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankprefixapproval.xhtml");
		} catch (Exception e) {
			log.error("Problem Occured in pageNavigationforBankPrefix() method");
		}
	}

	public void approveRecord(BankPrefixDataTable pBankPrefixDataTable)
	{
		if(!(pBankPrefixDataTable.getModifiedBy() == null ?  pBankPrefixDataTable.getCreatedBy() : pBankPrefixDataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())){
			try {
				if (icustomerBankService.approve(pBankPrefixDataTable.getBankPrefixPkId(), sessionStateManage.getUserName()).equalsIgnoreCase("Success")) {
					RequestContext.getCurrentInstance().execute("approv.show();");
				} else {
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				}
			} catch (Exception e) {
				setErrorMessage(e.toString());
				RequestContext.getCurrentInstance().execute("error.show();");
			}
		}else{
			RequestContext.getCurrentInstance().execute("sameUser.show();");
			return;
		}
	}

	public void cancel() {

		approvalList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankprefixapproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void loadApproval()
	{
		if(getBankCodeApproval()==null)
		{
			RequestContext.getCurrentInstance().execute("bankCodeEmpty.show();");
			return;
		}
		approvalList();
	}
	public void approvalList()
	{


		List<BankPrefix> lstBankPrefix= icustomerBankService.getBankPrefixApproval(getBankCodeApproval());
		List<BankPrefixDataTable> addBankPrefix= new ArrayList<BankPrefixDataTable>();


		if(lstBankPrefix!=null && lstBankPrefix.size()>0)
		{

			for(BankPrefix bankPrefix :lstBankPrefix)
			{

				BankPrefixDataTable bankPrefixDataTable = new BankPrefixDataTable();
				List<ViewBankDetails> listViewBankDetails =icustomerBankService.getCustomerLocalBankListFromView(sessionStateManage.getCountryId(), getBankCodeApproval());
				bankPrefixDataTable.setBankPrefix(bankPrefix.getBankPrefix());
				bankPrefixDataTable.setBankCode(bankPrefix.getBankCode());
				bankPrefixDataTable.setBankPrefixPkId(bankPrefix.getBankPrefixId());
				bankPrefixDataTable.setIsActive(bankPrefix.getIsActive());
				if(listViewBankDetails!=null && listViewBankDetails.size()>0)
				{
					ViewBankDetails viewBankDetails =listViewBankDetails.get(0);
					bankPrefixDataTable.setBankName(viewBankDetails.getBankShortName()+"-"+viewBankDetails.getBankFullName());
					bankPrefixDataTable.setBankId(viewBankDetails.getChequeBankId());

				}
				bankPrefixDataTable.setModifiedBy(bankPrefix.getModifiedBy());
				bankPrefixDataTable.setModifiedDate(bankPrefix.getModifiedDate());
				bankPrefixDataTable.setCreatedBy(bankPrefix.getCreatedBy());
				bankPrefixDataTable.setCreatedDate(bankPrefix.getCreatedDate());

				addBankPrefix.add(bankPrefixDataTable);
			}
			setLstApprovalBankPrefixDataTable(null);
			setLstApprovalBankPrefixDataTable(addBankPrefix);
			setBooRenderDatatableApproval(true);
		}else
		{
			setLstApprovalBankPrefixDataTable(null);
			RequestContext.getCurrentInstance().execute("noRecords.show();");
			return;

		}
	}
}
