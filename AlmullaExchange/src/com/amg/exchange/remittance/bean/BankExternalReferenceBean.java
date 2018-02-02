package com.amg.exchange.remittance.bean;

import groovyjarjarasm.asm.tree.IntInsnNode;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.service.IBankExternalReferenceservice;
import com.amg.exchange.remittance.model.BankExternalReferenceDetail;
import com.amg.exchange.remittance.model.BankExternalReferenceHead;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankBranch;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankExternalReferenceBean")
@Scope("session")
public class BankExternalReferenceBean<T> implements Serializable {
	private static final Logger LOGGER = Logger.getLogger(BankExternalReferenceBean.class);
	private static final long serialVersionUID = 1L;
	private BigDecimal bankCountryId;
	private String bankCountryName;
	private BigDecimal bankId;
	private String bankName;
	private BigDecimal benfiBankId;
	private String benfiBankName;
	private String externalId;
	private String branchExternalId;
	private String branchExternalName;
	private Boolean booRenderDataTablePanel = false;
	private Boolean booRenderSavePanel = false;
	private String dynamicLabelForActivateDeactivate;
	private String isActive;
	private boolean booSubmitPanel = false;
	private Date approvedDate;
	private String approvedBy;
	private String remarks;
	private String modifiedBy;
	private Date modifiedDate;
	private boolean editFalg = false;
	private BigDecimal bankExtRefSeqId = null;
	private String createdBy = null;
	private Date createdOn = null;
	boolean oneTime = false;
	boolean booRenderApproveScreen = true;
	boolean booRenderApproveDataScreen;
	//Added by Rabil on 06/03/2016
	private String flexField1;
	private String flexField2;
	private String flexField3;

	//new varibles
	private BigDecimal databeneFiciaryBankId;
	private  String dataFlexFiled1;
	private  String dataFlexFiled2;
	private  String dataFlexFiled3;
	private String dataExternalId;
	public  String getDataBranchExternalId() {
		return dataBranchExternalId;
	}

	public void setDataBranchExternalId(String dataBranchExternalId) {
		this.dataBranchExternalId = dataBranchExternalId;
	}

	public String getDataBranchExternalYesOrNo() {
		return dataBranchExternalYesOrNo;
	}

	public void setDataBranchExternalYesOrNo(String dataBranchExternalYesOrNo) {
		this.dataBranchExternalYesOrNo = dataBranchExternalYesOrNo;
	}
	private String dataBranchExternalId;
	private String dataBranchExternalYesOrNo;











	/*	BankExternalReferenceDataTable approveBean = null;*/
	private boolean hideEdit;
	private Boolean isdisable = false;
	private boolean iscleardisable = false;
	private List<CountryMasterDesc> bankCountryList = new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal, String> bankCountryMap = new HashMap<BigDecimal, String>();
	private List<BankApplicability> bankList = new ArrayList<BankApplicability>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankCodeMap = new HashMap<BigDecimal, String>();
	private List<BankApplicability> beneficaryBankList = new ArrayList<BankApplicability>();
	private Map<BigDecimal, String> beneficaryBankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> beneficaryBankCodeMap = new HashMap<BigDecimal, String>();

	private List<BankExternalReferenceDailogDataTable> bankRefDataTableList;// = null;
	private List<BankExternalReferenceDailogDataTable> bankRefDailogDataTable;// = new CopyOnWriteArrayList<BankExternalReferenceDailogDataTable>();
	private List<BankExternalReferenceDataTable> bankRefDataTableList2 = new ArrayList<BankExternalReferenceDataTable>();
	private List<BankExternalReferenceDailogDataTable> popupinDataTable = new CopyOnWriteArrayList<BankExternalReferenceDailogDataTable>();
	private Map<BigDecimal, String> branchExternalMap = new HashMap<BigDecimal, String>();
	private List<BankExternalReferenceDataTable> mainDataTable = new CopyOnWriteArrayList<BankExternalReferenceDataTable>();
	private List<BankExternalReferenceDataTable> mainNewListDataTable = new ArrayList<BankExternalReferenceDataTable>();

	private List<BankExternalReferenceDataTable> approveList = new ArrayList<BankExternalReferenceDataTable>();
	private SessionStateManage session = new SessionStateManage();
	private List<BankExternalReferenceDataTable>  removeList = new ArrayList<BankExternalReferenceDataTable>();
	private List<BankExternalReferenceDataTable>  nonRemoveList ;
	private List<BankExternalReferenceDailogDataTable> bankRefDailogDataAfterRemove;

	private BankExternalReferenceDataTable bankExtrnaRefDataTable;
	private List<BankExternalReferenceDailogDataTable>  approvalBranchDetailsList =new ArrayList<BankExternalReferenceDailogDataTable>();
	private List<BankExternalReferenceDailogDataTable> bankRefDataTableListTemp;




	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	IBankExternalReferenceservice bankExternalReferenceservice;
	@Autowired
	IBankIndicatorService bankIndicatorService;
	public BigDecimal getDatabeneFiciaryBankId() {
		return databeneFiciaryBankId;
	}

	public void setDatabeneFiciaryBankId(BigDecimal databeneFiciaryBankId) {
		this.databeneFiciaryBankId = databeneFiciaryBankId;
	}

	public String getDataFlexFiled1() {
		return dataFlexFiled1;
	}

	public void setDataFlexFiled1(String dataFlexFiled1) {
		this.dataFlexFiled1 = dataFlexFiled1;
	}

	public String getDataFlexFiled2() {
		return dataFlexFiled2;
	}

	public void setDataFlexFiled2(String dataFlexFiled2) {
		this.dataFlexFiled2 = dataFlexFiled2;
	}

	public String getDataFlexFiled3() {
		return dataFlexFiled3;
	}

	public void setDataFlexFiled3(String dataFlexFiled3) {
		this.dataFlexFiled3 = dataFlexFiled3;
	}

	public String getDataExternalId() {
		return dataExternalId;
	}

	public void setDataExternalId(String dataExternalId) {
		this.dataExternalId = dataExternalId;
	}


	public boolean isIscleardisable() {
		return iscleardisable;
	}

	public void setIscleardisable(boolean iscleardisable) {
		this.iscleardisable = iscleardisable;
	}

	public Boolean getIsdisable() {
		return isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	public List<BankExternalReferenceDataTable> getMainDataTable() {
		return mainDataTable;
	}

	public void setMainDataTable(List<BankExternalReferenceDataTable> mainDataTable) {
		this.mainDataTable = mainDataTable;
	}

	public boolean isHideEdit() {
		return hideEdit;
	}

	public void setHideEdit(boolean hideEdit) {
		this.hideEdit = hideEdit;
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

	public boolean isBooSubmitPanel() {
		return booSubmitPanel;
	}

	public void setBooSubmitPanel(boolean booSubmitPanel) {
		this.booSubmitPanel = booSubmitPanel;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public List<BankExternalReferenceDailogDataTable> getPopupinDataTable() {
		return popupinDataTable;
	}

	public void setPopupinDataTable(List<BankExternalReferenceDailogDataTable> popupinDataTable) {
		this.popupinDataTable = popupinDataTable;
	}

	public List<BankExternalReferenceDailogDataTable> getBankRefDailogDataTable() {
		return bankRefDailogDataTable;
	}

	public void setBankRefDailogDataTable(List<BankExternalReferenceDailogDataTable> bankRefDailogDataTable) {
		this.bankRefDailogDataTable = bankRefDailogDataTable;
	}

	public List<BankExternalReferenceDataTable> getBankRefDataTableList2() {
		return bankRefDataTableList2;
	}

	public void setBankRefDataTableList2(List<BankExternalReferenceDataTable> bankRefDataTableList2) {
		this.bankRefDataTableList2 = bankRefDataTableList2;
	}

	public Boolean getBooRenderDataTablePanel() {
		return booRenderDataTablePanel;
	}

	public void setBooRenderDataTablePanel(Boolean booRenderDataTablePanel) {
		this.booRenderDataTablePanel = booRenderDataTablePanel;
	}

	public Boolean getBooRenderSavePanel() {
		return booRenderSavePanel;
	}

	public void setBooRenderSavePanel(Boolean booRenderSavePanel) {
		this.booRenderSavePanel = booRenderSavePanel;
	}

	public List<CountryMasterDesc> getBankCountryList() {
		try{
			bankCountryList = generalService.getCountryList(session.getLanguageId());
			for (CountryMasterDesc bankMaster : bankCountryList) {
				bankCountryMap.put(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getCountryName());
			}
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
		return bankCountryList;
	}

	public void setBankCountryList(List<CountryMasterDesc> bankCountryList) {
		this.bankCountryList = bankCountryList;
	}

	public BigDecimal getBankCountryId() {
		return bankCountryId;
	}

	public void setBankCountryId(BigDecimal bankCountryId) {
		this.bankCountryId = bankCountryId;
	}

	public String getBankCountryName() {
		return bankCountryName;
	}

	public void setBankCountryName(String bankCountryName) {
		this.bankCountryName = bankCountryName;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public BigDecimal getBenfiBankId() {
		return benfiBankId;
	}

	public void setBenfiBankId(BigDecimal benfiBankId) {
		this.benfiBankId = benfiBankId;
	}

	public String getBenfiBankName() {
		return benfiBankName;
	}

	public void setBenfiBankName(String benfiBankName) {
		this.benfiBankName = benfiBankName;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getBranchExternalId() {
		return branchExternalId;
	}

	public void setBranchExternalId(String branchExternalId) {
		this.branchExternalId = branchExternalId;
	}

	public String getBranchExternalName() {
		return branchExternalName;
	}

	public void setBranchExternalName(String branchExternalName) {
		this.branchExternalName = branchExternalName;
	}

	public List<BankApplicability> getBeneficaryBankList() {
		return beneficaryBankList;
	}

	public void setBeneficaryBankList(List<BankApplicability> beneficaryBankList) {
		this.beneficaryBankList = beneficaryBankList;
	}

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	public List<BankExternalReferenceDailogDataTable> getBankRefDataTableListTemp() {
		return bankRefDataTableListTemp;
	}

	public void setBankRefDataTableListTemp(
			List<BankExternalReferenceDailogDataTable> bankRefDataTableListTemp) {
		this.bankRefDataTableListTemp = bankRefDataTableListTemp;
	}

	// modified on 03/02/2015 - done by mohan
	public void getBankBasedOnCountry() {

		clearAllFieldsforADD();
		setBankId(null);
		bankList.clear();
		beneficaryBankList.clear();
		setMainDataTable(null);
		bankMap.clear();
		bankCodeMap.clear();
		setBankRefDataTableList(null);

		try{
			BigDecimal pkCorresBankInd = null;
			BigDecimal pkServProBankInd = null;
			List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);
			if (corrBankIndlist.size() != 0) {
				pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
			}
			List<BankIndicator> serviceBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);
			if (serviceBankIndlist.size() != 0) {
				pkServProBankInd = serviceBankIndlist.get(0).getBankIndicatorId();
			}
			if (pkCorresBankInd != null && pkServProBankInd != null) {
				bankList = bankExternalReferenceservice.getBankListbyIndicators(getBankCountryId(), pkCorresBankInd, pkServProBankInd);
			}
			for (BankApplicability bankMaster : bankList) {
				bankMap.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankFullName());
				bankCodeMap.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankCode());
				LOGGER.info(bankMaster.getBankMaster().getBankId());
				LOGGER.info(bankMaster.getBankMaster().getBankCode());
				LOGGER.info(bankMaster.getBankMaster().getBankFullName());
			}
			setBankList(bankList);
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getBankBasedOnCountry"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	// ends here
	public List<BankApplicability> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankApplicability> bankList) {
		this.bankList = bankList;
	}

	public List<BankExternalReferenceDailogDataTable> getBankRefDataTableList() {
		return bankRefDataTableList;
	}

	public void setBankRefDataTableList(List<BankExternalReferenceDailogDataTable> bankRefDataTableList) {
		this.bankRefDataTableList = bankRefDataTableList;
	}

	// modified on 03/02/2015 - done by mohan
	public void getBeneficaryBankBasedOnBank() {

		clearAllFieldsforADD();

		beneficaryBankList.clear();
		setMainDataTable(null);;
		beneficaryBankMap.clear();
		beneficaryBankCodeMap.clear();

		try{
			BigDecimal pkBeneBankInd = null;
			List<BankApplicability> allbenebankList = new ArrayList<BankApplicability>();
			//beneficaryBankList = new ArrayList<BankApplicability>();
			//beneficaryBankList.addAll(bankList);
			List<BankIndicator> beneBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_BENI_BANK);
			if (beneBankIndlist.size() != 0) {
				pkBeneBankInd = beneBankIndlist.get(0).getBankIndicatorId();
			}
			LOGGER.info(getBankId());
			/*int countIndex = 0;
			int removeIndex = 0;
			for (BankApplicability bankAppl : beneficaryBankList) {
				if (!bankAppl.getBankMaster().getBankId().equals(getBankId())) {
					beneficaryBankMap.put(bankAppl.getBankMaster().getBankId(), bankAppl.getBankMaster().getBankFullName());
					beneficaryBankCodeMap.put(bankAppl.getBankMaster().getBankId(), bankAppl.getBankMaster().getBankCode());
				} else {
					LOGGER.info("Removed this object from the Beneficiary Bank* list " + bankAppl.getBankMaster().getBankId());
					removeIndex = countIndex;
				}
				setBeneficaryBankList(beneficaryBankList);
				countIndex++;
			}
			beneficaryBankList.remove(removeIndex);*/
			// All Beneficiary Banks of the bank country added
			if(pkBeneBankInd != null){
				List<BankApplicability> benebankList = generalService.getBankListbyIndicator(getBankCountryId(), pkBeneBankInd);
				if(benebankList.size() != 0){
					allbenebankList.addAll(benebankList);
				}
			}

			if(bankList != null && !bankList.isEmpty())
			{
				allbenebankList.addAll(bankList);
			}

			for (BankApplicability bankAppl : allbenebankList) {
				if (!bankAppl.getBankMaster().getBankId().equals(getBankId())) {
					beneficaryBankList.add(bankAppl);
					beneficaryBankMap.put(bankAppl.getBankMaster().getBankId(), bankAppl.getBankMaster().getBankFullName());
					beneficaryBankCodeMap.put(bankAppl.getBankMaster().getBankId(), bankAppl.getBankMaster().getBankCode());
				} 
			}
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::getBeneficaryBankBasedOnBank"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void externalTypeAlreadyExsist() {
		try{
			//bankRefDataTableList = null;
			//popupinDataTable.clear();
			setBankRefDataTableList(null);
			List<BankExternalReferenceDailogDataTable> addbankRefDTList = new ArrayList<BankExternalReferenceDailogDataTable>();
			LOGGER.info(getBranchExternalId());

			int exterId = Integer.valueOf(getBranchExternalId()==null?"0":getBranchExternalId());
			List<BankBranch> bankbranchspecific = new ArrayList<BankBranch>();
			bankbranchspecific = bankExternalReferenceservice.getBankBranchListFromCountryBank(getBankCountryId(), getBenfiBankId());
			if (exterId == 1) {
				if (bankbranchspecific.size() > 0) {
					for (BankBranch bankbranch : bankbranchspecific) {
						BankExternalReferenceDailogDataTable bkExlRefDT = new BankExternalReferenceDailogDataTable();
						bkExlRefDT.setBranchCode(bankbranch.getBranchCode());
						bkExlRefDT.setBranchDescription(bankbranch.getBranchFullName());
						bkExlRefDT.setBankId(bankbranch.getExBankMaster().getBankId());
						bkExlRefDT.setBeneficaryBankId(getBenfiBankId());
						bkExlRefDT.setBranchId(bankbranch.getBankBranchId());
						bkExlRefDT.setBankCode(bankCodeMap.get(bankbranch.getExBankMaster().getBankId()));
						bkExlRefDT.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
						bkExlRefDT.setBranchExternalId(null);
						bkExlRefDT.setFlexField1( null);
						bkExlRefDT.setFlexField2(null );
						bkExlRefDT.setFlexField3(null );
						bkExlRefDT.setCreatedBy(session.getUserName());
						bkExlRefDT.setCreatedDate(new Date());
						bkExlRefDT.setIsActive(Constants.U);
						//bkExlRefDT.setDynamicLabelActivateDeactivate(Constants.REMOVE);
						addbankRefDTList.add(bkExlRefDT);
					}
				}
				setBankRefDataTableList(addbankRefDTList);
				RequestContext.getCurrentInstance().execute("alreadyExist.show();");
				//popupinDataTable.clear();
			}
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clickOnOk() {
		setNullValues();
		setBooRenderDataTablePanel(false);
		setBooRenderSavePanel(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}

	public void clickDailogOk() {
		try{
			setBooSubmitPanel(false);
			List<BankExternalReferenceDailogDataTable> bankRefDataTableList= getBankRefDataTableList();
			List<BankExternalReferenceDailogDataTable> addBankRefDailogDataTable = new ArrayList<BankExternalReferenceDailogDataTable>();
			for (BankExternalReferenceDailogDataTable dialog : bankRefDataTableList) { 
				//if(  dialog.getBranchExternalId()!=null&& dialog.getBranchExternalId().trim().equalsIgnoreCase("")||(dialog.getFlexField1()!=null&&!dialog.getFlexField1().trim().equalsIgnoreCase(""))||(dialog.getFlexField2()!=null&&!dialog.getFlexField3().trim().equalsIgnoreCase(""))||(dialog.getFlexField3()!=null&&!dialog.getFlexField3().trim().equalsIgnoreCase("")))
				if ( null != dialog.getBranchExternalId() && !dialog.getBranchExternalId().trim().equals("")) {

					BankExternalReferenceDailogDataTable bankExternalRefDTObj = new BankExternalReferenceDailogDataTable();
					bankExternalRefDTObj.setBranchCode(dialog.getBranchCode());
					bankExternalRefDTObj.setBranchDescription(dialog.getBranchDescription());
					bankExternalRefDTObj.setBankId(dialog.getBankId());
					bankExternalRefDTObj.setBeneficaryBankId(dialog.getBeneficaryBankId());
					bankExternalRefDTObj.setBranchId(dialog.getBranchId());
					bankExternalRefDTObj.setBankCode(dialog.getBankCode());
					bankExternalRefDTObj.setBeneficaryBankCode(dialog.getBeneficaryBankCode());
					bankExternalRefDTObj.setBankExtRefDetailSeqId(dialog.getBankExtRefDetailSeqId());
					bankExternalRefDTObj.setIsActive(dialog.getIsActive());
					bankExternalRefDTObj.setBankExtRefSeqId(dialog.getBankExtRefSeqId());
					bankExternalRefDTObj.setBranchExternalId(dialog.getBranchExternalId());
					bankExternalRefDTObj.setBranchExternalYesNo(getDataBranchExternalYesOrNo());
					//Added by Rabil on 06/03/2016
					bankExternalRefDTObj.setFlexField1(dialog.getFlexField1());
					bankExternalRefDTObj.setFlexField2(dialog.getFlexField2());
					bankExternalRefDTObj.setFlexField3(dialog.getFlexField3());
					bankExternalRefDTObj.setCreatedBy(dialog.getCreatedBy());
					bankExternalRefDTObj.setCreatedDate(dialog.getCreatedDate());
					bankExternalRefDTObj.setUpdateStatus(true);
					LOGGER.info(bankExternalRefDTObj);
					addBankRefDailogDataTable.add(bankExternalRefDTObj);

				}
			}
			setBankRefDailogDataTable(addBankRefDailogDataTable);
			/*try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
			} catch (Exception e) {
				setErrorMessage(e.getMessage()); 
			}
*/		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clickOnExit() throws IOException {
		mainNewListDataTable.clear();
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void bankExternalRefPageNavigation() {
		setNullValues();
		setBooRenderDataTablePanel(false);
		setBooRenderSavePanel(false);
		branchExternalMap.put(new BigDecimal(1), "Yes");
		branchExternalMap.put(new BigDecimal(2), "No");
		bankRefDataTableList2.clear();
		setMainDataTable(null);;
		mainNewListDataTable.clear();
		//bankRefDailogDataTable.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "Bankexternalreference.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		} catch (Exception exception) {
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}
	}

	public void addRecordsToDataTable(){
		try{
			setIsdisable(false);
			setHideEdit(false);
			setIscleardisable(false);
			boolean duplicate = false;
			List<BankExternalReferenceDataTable> addMainDataTable = new ArrayList<BankExternalReferenceDataTable>();

			List<BankExternalReferenceDataTable>   exstingList=  getMainDataTable();
			if(exstingList!=null && exstingList.size()>0){
				//Duplicate checking with Data Table Records
				for (BankExternalReferenceDataTable checkDuplicate : exstingList) {
					if (checkDuplicate.getCountryId().equals(getBankCountryId()) && checkDuplicate.getBankId().equals(getBankId()) && checkDuplicate.getBeneficaryBankId().equals(getBenfiBankId())  ) {
						clearAllFieldsforADD();
						RequestContext.getCurrentInstance().execute("detailsexists.show();");
						duplicate = true;
						return;
					}
				}
			} 
			if(!editFalg){
				//Duplicate checking with Database Records
				List<BankExternalReferenceHead> headerList =	bankExternalReferenceservice.getDuplicateCheckList(getBankCountryId(), getBankId(), session.getCountryId(),getBenfiBankId());
				if(headerList.size()>0){
					clearAllFieldsforADD();
					RequestContext.getCurrentInstance().execute("databasedetailsexists.show();");
					duplicate = true;
					return;
				}
			}

			//Record is not duplicate then add to data table
			if(!duplicate){
				BankExternalReferenceDataTable bankExternalReferenceDataTable = new BankExternalReferenceDataTable();
				bankExternalReferenceDataTable.setBeneficaryBankId(getBenfiBankId());
				bankExternalReferenceDataTable.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
				bankExternalReferenceDataTable.setBeneficaryBankName(beneficaryBankMap.get(getBenfiBankId()));
				bankExternalReferenceDataTable.setBankExternalId(getExternalId());

				// bankExternalReferenceDataTable.setBranchExternalId(getBranchExternalId());
				bankExternalReferenceDataTable.setBranchExternalName(branchExternalMap.get(getBranchExternalId()));
				bankExternalReferenceDataTable.setBankId(getBankId());
				bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
				bankExternalReferenceDataTable.setBankExtRefSeqId(getBankExtRefSeqId());

				if (null == getBankExtRefSeqId() || "".equals(getBankExtRefSeqId())) {
					bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				}
				//Added by Rabil on 06/03/2016
				bankExternalReferenceDataTable.setFlexField1(getFlexField1());
				bankExternalReferenceDataTable.setFlexField2(getFlexField2());
				bankExternalReferenceDataTable.setFlexField3(getFlexField3());

				bankExternalReferenceDataTable.setBankName(bankMap.get(getBankId()));
				bankExternalReferenceDataTable.setBankCode(bankCodeMap.get(getBankId()));
				bankExternalReferenceDataTable.setCountryId(getBankCountryId());
				bankExternalReferenceDataTable.setCountryName(bankCountryMap.get(getBankCountryId()));
				bankExternalReferenceDataTable.setModifiedBy(getModifiedBy());
				bankExternalReferenceDataTable.setModifiedDate(getModifiedDate());
				if (bankExternalReferenceDataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE) ) {
					if (bankExternalReferenceDataTable.getModifiedBy() == null && bankExternalReferenceDataTable.getModifiedDate() == null) {
						bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					} else {
						bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate("");
					}
				}
				bankExternalReferenceDataTable.setApprovedBy(getApprovedBy());
				bankExternalReferenceDataTable.setApprovedDate(getApprovedDate());
				bankExternalReferenceDataTable.setRemarks(getRemarks());
				bankExternalReferenceDataTable.setIndex(String.valueOf(getBenfiBankId()) + getExternalId());
				LOGGER.info("********************bankExternalReferenceDataTable****************************************");
				LOGGER.info(bankExternalReferenceDataTable);
				LOGGER.info("********************bankExternalReferenceDataTable****************************************");
				if (!editFalg) {
					bankExternalReferenceDataTable.setIsActive(Constants.U);
					bankExternalReferenceDataTable.setCreatedBy(session.getUserName());
					bankExternalReferenceDataTable.setCreatedOn(new Date());
				} else {
					bankExternalReferenceDataTable.setIsActive(getIsActive());
					bankExternalReferenceDataTable.setCreatedBy(getCreatedBy());
					bankExternalReferenceDataTable.setCreatedOn(getCreatedOn());
				}
				bankExternalReferenceDataTable.setBranchExternalYesNo(getBranchExternalId());
				List<BankExternalReferenceDailogDataTable> newAbankRefDailogDataTable=getBankRefDailogDataTable();
				bankExternalReferenceDataTable.setDialogTable(newAbankRefDailogDataTable);
				if (getBranchExternalId() != null && getBranchExternalId().equals("1") && newAbankRefDailogDataTable.isEmpty()) {
					RequestContext.getCurrentInstance().execute("atleast.show();");
					setBranchExternalId(null);
				} else {

					addMainDataTable.add(bankExternalReferenceDataTable);

					if(getBankExtRefSeqId()==null){
						mainNewListDataTable.add(bankExternalReferenceDataTable );
					}
					editFalg=false;
					//bankRefDailogDataTable .clear();

					setBooRenderDataTablePanel(true);
					setBooRenderSavePanel(true);
					clearAllFieldsforADD();

				}
			}
			List<BankExternalReferenceDataTable> alreadyExistedMainDataTable = getMainDataTable();
			if(alreadyExistedMainDataTable!=null && alreadyExistedMainDataTable.size()>0)
			{
				addMainDataTable.addAll(alreadyExistedMainDataTable);
			}
			setMainDataTable(addMainDataTable);

		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name:: addRecordsToDataTable"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public void addRecordsWhenEdit(){

		setIsdisable(false);
		setHideEdit(false);
		setIscleardisable(false);	
		//bankRefDailogDataTable = new ArrayList<BankExternalReferenceDailogDataTable>();
		List<BankExternalReferenceDailogDataTable> addBankRefDailogDataTable= new ArrayList<BankExternalReferenceDailogDataTable>();
		List<BankExternalReferenceDailogDataTable> bankRefDataTableList=getBankRefDataTableList();
		if(bankRefDataTableList!=null && bankRefDataTableList.size()>0)
		{
			for (BankExternalReferenceDailogDataTable dialog : bankRefDataTableList) {
				if (dialog.getBranchExternalId()!=null &&  !dialog.getBranchExternalId().trim().equals("")) {

					BankExternalReferenceDailogDataTable bankExternalRefDTObj = new BankExternalReferenceDailogDataTable();
					bankExternalRefDTObj.setBranchCode(dialog.getBranchCode());
					bankExternalRefDTObj.setBranchDescription(dialog.getBranchDescription());
					bankExternalRefDTObj.setBankId(dialog.getBankId());
					bankExternalRefDTObj.setBeneficaryBankId(dialog.getBeneficaryBankId());
					bankExternalRefDTObj.setBranchId(dialog.getBranchId());
					bankExternalRefDTObj.setBankCode(dialog.getBankCode());
					bankExternalRefDTObj.setBeneficaryBankCode(dialog.getBeneficaryBankCode());
					bankExternalRefDTObj.setBankExtRefDetailSeqId(dialog.getBankExtRefDetailSeqId());
					if(dialog.getUpdateStatus())
					{
						bankExternalRefDTObj.setIsActive(Constants.U);
						if(dialog.getBankExtRefDetailSeqId()==null)
						{
							bankExternalRefDTObj.setCreatedBy(session.getUserName());
							bankExternalRefDTObj.setCreatedDate(new Date());
							bankExternalRefDTObj.setDynamicLabelActivateDeactivate(Constants.REMOVE);
						}else
						{
							bankExternalRefDTObj.setCreatedBy(dialog.getCreatedBy());
							bankExternalRefDTObj.setCreatedDate(dialog.getCreatedDate());
							bankExternalRefDTObj.setModifiedBy(session.getUserName());
							bankExternalRefDTObj.setModifiedDate(new Date());
							bankExternalRefDTObj.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
						}
						
					}else
					{
						bankExternalRefDTObj.setIsActive(dialog.getIsActive());
						bankExternalRefDTObj.setCreatedBy(dialog.getCreatedBy());
						bankExternalRefDTObj.setCreatedDate(dialog.getCreatedDate());
					}
					
					bankExternalRefDTObj.setBankExtRefSeqId(dialog.getBankExtRefSeqId());
					bankExternalRefDTObj.setBranchExternalId(dialog.getBranchExternalId());
					bankExternalRefDTObj.setBranchExternalYesNo(getDataBranchExternalYesOrNo());
					//Added by Rabil on 06/03/2016
					bankExternalRefDTObj.setFlexField1(dialog.getFlexField1());
					bankExternalRefDTObj.setFlexField2(dialog.getFlexField2());
					bankExternalRefDTObj.setFlexField3(dialog.getFlexField3());
					bankExternalRefDTObj.setUpdateStatus(dialog.getUpdateStatus());
					bankExternalRefDTObj.setApprovedBy(dialog.getApprovedBy());
					bankExternalRefDTObj.setApprovedDate(dialog.getApprovedDate());
					
					//bankExternalRefDTObj.setBranchExternalId(getDataBranchExternalYesOrNo());
					LOGGER.info(bankExternalRefDTObj);
					addBankRefDailogDataTable.add(bankExternalRefDTObj);
				}
			}
		}
		
		
		setBankRefDailogDataTable(addBankRefDailogDataTable);

		BankExternalReferenceDataTable bankExternalReferenceDataTable = null;

		bankExternalReferenceDataTable = new BankExternalReferenceDataTable();
		bankExternalReferenceDataTable.setBeneficaryBankId(getDatabeneFiciaryBankId());
		bankExternalReferenceDataTable.setBeneficaryBankCode(beneficaryBankCodeMap.get(getDatabeneFiciaryBankId()));
		bankExternalReferenceDataTable.setBeneficaryBankName(beneficaryBankMap.get(getDatabeneFiciaryBankId()));
		bankExternalReferenceDataTable.setBankExternalId(getDataExternalId());

		// bankExternalReferenceDataTable.setBranchExternalId(getBranchExternalId());
		bankExternalReferenceDataTable.setBranchExternalName(branchExternalMap.get(getDataBranchExternalId()));
		bankExternalReferenceDataTable.setBankId(getBankId());
		bankExternalReferenceDataTable.setBankCode(bankCodeMap.get(getBankId()));
		bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
		//bankExternalReferenceDataTable.setBankExtRefSeqId(getBankExtRefSeqId());
		bankExternalReferenceDataTable.setBranchExternalYesNo(getDataBranchExternalYesOrNo());
		/*if (null == getBankExtRefSeqId() || "".equals(getBankExtRefSeqId())) {
			bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		}*/
		//Added by Rabil on 06/03/2016
		bankExternalReferenceDataTable.setFlexField1(getDataFlexFiled1());
		bankExternalReferenceDataTable.setFlexField2(getDataFlexFiled2());
		bankExternalReferenceDataTable.setFlexField3(getDataFlexFiled3());

		bankExternalReferenceDataTable.setBankName(bankMap.get(getBankId()));
		bankExternalReferenceDataTable.setBankCode(bankCodeMap.get(getBankId()));
		bankExternalReferenceDataTable.setCountryId(getBankCountryId());
		bankExternalReferenceDataTable.setCountryName(bankCountryMap.get(getBankCountryId()));
		/*bankExternalReferenceDataTable.setModifiedBy(getModifiedBy());
		bankExternalReferenceDataTable.setModifiedDate(getModifiedDate());*/
		/*if (bankExternalReferenceDataTable.getDynamicLabelForActivateDeactivate()!=null && bankExternalReferenceDataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE) ) {
			if (bankExternalReferenceDataTable.getModifiedBy() == null && bankExternalReferenceDataTable.getModifiedDate() == null) {
			} else {
				bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
			}
		}*/
		//bankExternalReferenceDataTable.setApprovedBy(getApprovedBy());
		//bankExternalReferenceDataTable.setApprovedDate(getApprovedDate());
		bankExternalReferenceDataTable.setRemarks(getRemarks());
		bankExternalReferenceDataTable.setIsActive(Constants.U);
		bankExternalReferenceDataTable.setCreatedBy(getCreatedBy());
		bankExternalReferenceDataTable.setCreatedOn(getCreatedOn());
		bankExternalReferenceDataTable.setApprovedBy(getApprovedBy());
		bankExternalReferenceDataTable.setApprovedDate(getApprovedDate());
		bankExternalReferenceDataTable.setModifiedBy(getModifiedBy());
		bankExternalReferenceDataTable.setModifiedDate(getModifiedDate());
		bankExternalReferenceDataTable.setBankExtRefSeqId(getBankExtRefSeqId());
		bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
		

		bankExternalReferenceDataTable.setIndex(String.valueOf(getDatabeneFiciaryBankId()) + getDataExternalId());
		bankExternalReferenceDataTable.setUpdateStatus(true);
		/*if (!editFalg) {
			bankExternalReferenceDataTable.setIsActive(Constants.U);
			bankExternalReferenceDataTable.setCreatedBy(session.getUserName());
			bankExternalReferenceDataTable.setCreatedOn(new Date());
		} else {
			bankExternalReferenceDataTable.setIsActive(getIsActive());
			bankExternalReferenceDataTable.setCreatedBy(getCreatedBy());
			bankExternalReferenceDataTable.setCreatedOn(getCreatedOn());
		}
		 */
		bankExternalReferenceDataTable.setDialogTable(getBankRefDailogDataTable());
		if (getDataBranchExternalId() != null && getDataBranchExternalId().equals("1") && getBankRefDailogDataTable().isEmpty()) {
			RequestContext.getCurrentInstance().execute("atleast.show();");
			setDataBranchExternalId( null);
			//setBranchExternalId(null);
		} else {

			mainDataTable.add(bankExternalReferenceDataTable);

			editFalg=false;
			//bankRefDailogDataTable.clear();
			setBooRenderDataTablePanel(true);
			setBooRenderSavePanel(true);
			clearAllFieldsforADD();
			editClearValues();

		}	

	}






	/*public void addrecordsToDataTable() {
		try{
			setIsdisable(false);
			setHideEdit(false);
			setIscleardisable(false);
			LOGGER.info("*****************************************input fields************************************************************");
			LOGGER.info("getBankCountryId() " + getBankCountryId());
			LOGGER.info("getBankId" + getBankId());
			LOGGER.info("getBenfiBankId" + getBenfiBankId());
			LOGGER.info("getExternalId" + getExternalId());
			LOGGER.info("getBranchExternalId" + getBranchExternalId());
			LOGGER.info("getBankExtRefSeqId()" + getBankExtRefSeqId());
			LOGGER.info("Flexi field :"+getFlexField1()+"\t :"+getFlexField2()+"\t :"+getFlexField3());
			LOGGER.info("*****************************************input fields************************************************************");
			boolean duplicate = false;
			for (BankExternalReferenceDataTable checkDuplicate : mainDataTable) {
				if (checkDuplicate.getCountryId().equals(getBankCountryId()) && checkDuplicate.getBankId().equals(getBankId()) && checkDuplicate.getBeneficaryBankId().equals(getBenfiBankId())  ) {
					clearAllFieldsforADD();
					RequestContext.getCurrentInstance().execute("detailsexists.show();");
					duplicate = true;
				}
			}
			if (!duplicate) {

				if (!editFalg) {
					hList = bankExternalReferenceservice.getAllRecordsfromHead(session.getCountryId(), getBankCountryId(), getBankId(), getBenfiBankId(), getExternalId());
					boolean falg = false;
					if (!hList.isEmpty()) {
						RequestContext.getCurrentInstance().execute("detailsexists.show();");
						falg = true;
					}
					// adding new record check if existing record is thr or not then
					if (falg == false) {
						hList = null;
						falg = true;
					}
					// if getAllRecords is empty list then
					if (!falg) {
						if (hList.isEmpty()) {
							hList = null;
						}
					}
				}
				if (editFalg) {
					hList = null;
				}
				BankExternalReferenceDataTable bankExternalReferenceDataTable = null;
				if (hList == null) {
					bankExternalReferenceDataTable = new BankExternalReferenceDataTable();
					bankExternalReferenceDataTable.setBeneficaryBankId(getBenfiBankId());
					bankExternalReferenceDataTable.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
					bankExternalReferenceDataTable.setBeneficaryBankName(beneficaryBankMap.get(getBenfiBankId()));
					bankExternalReferenceDataTable.setBankExternalId(getExternalId());

					// bankExternalReferenceDataTable.setBranchExternalId(getBranchExternalId());
					bankExternalReferenceDataTable.setBranchExternalName(branchExternalMap.get(getBranchExternalId()));
					bankExternalReferenceDataTable.setBankId(getBankId());
					bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					bankExternalReferenceDataTable.setBankExtRefSeqId(getBankExtRefSeqId());

					if (null == getBankExtRefSeqId() || "".equals(getBankExtRefSeqId())) {
						bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					}
					//Added by Rabil on 06/03/2016
					bankExternalReferenceDataTable.setFlexField1(getFlexField1());
					bankExternalReferenceDataTable.setFlexField2(getFlexField2());
					bankExternalReferenceDataTable.setFlexField3(getFlexField3());



					LOGGER.info("getDynamicLabelForActivateDeactivate" + getDynamicLabelForActivateDeactivate());
					LOGGER.info("beneficaryBankMap" + beneficaryBankMap);
					LOGGER.info("bankCodeMap" + bankCodeMap);
					bankExternalReferenceDataTable.setBankName(bankMap.get(getBankId()));
					bankExternalReferenceDataTable.setBankCode(bankCodeMap.get(getBankId()));
					bankExternalReferenceDataTable.setCountryId(getBankCountryId());
					bankExternalReferenceDataTable.setCountryName(bankCountryMap.get(getBankCountryId()));
					bankExternalReferenceDataTable.setModifiedBy(getModifiedBy());
					bankExternalReferenceDataTable.setModifiedDate(getModifiedDate());
					if (bankExternalReferenceDataTable.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE) ) {
						if (bankExternalReferenceDataTable.getModifiedBy() == null && bankExternalReferenceDataTable.getModifiedDate() == null) {
						} else {
							bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate("");
						}
					}
					bankExternalReferenceDataTable.setApprovedBy(getApprovedBy());
					bankExternalReferenceDataTable.setApprovedDate(getApprovedDate());
					bankExternalReferenceDataTable.setRemarks(getRemarks());
					bankExternalReferenceDataTable.setIndex(String.valueOf(getBenfiBankId()) + getExternalId());
					LOGGER.info("********************bankExternalReferenceDataTable****************************************");
					LOGGER.info(bankExternalReferenceDataTable);
					LOGGER.info("********************bankExternalReferenceDataTable****************************************");
					if (!editFalg) {
						bankExternalReferenceDataTable.setIsActive(Constants.U);
						bankExternalReferenceDataTable.setCreatedBy(session.getUserName());
						bankExternalReferenceDataTable.setCreatedOn(new Date());
					} else {
						bankExternalReferenceDataTable.setIsActive(getIsActive());
						bankExternalReferenceDataTable.setCreatedBy(getCreatedBy());
						bankExternalReferenceDataTable.setCreatedOn(getCreatedOn());
					}
					if (getBranchExternalId() != null && getBranchExternalId().equals("1")) {
						LOGGER.info(bankRefDailogDataTable.size());
						for (BankExternalReferenceDailogDataTable test : bankRefDailogDataTable) {
							LOGGER.info(test);
						}
					}
					bankExternalReferenceDataTable.setDialogTable(bankRefDailogDataTable);
					if (getBranchExternalId() != null && getBranchExternalId().equals("1") && bankRefDailogDataTable.isEmpty()) {
						RequestContext.getCurrentInstance().execute("atleast.show();");
						setBranchExternalId(null);
					} else {
						mainDataTable.add(bankExternalReferenceDataTable);
						bankRefDailogDataTable = null;
						setBooRenderDataTablePanel(true);
						setBooRenderSavePanel(true);
						clearAllFieldsforADD();
						bankExternalReferenceDataTable = null;
					}
				}
			} 
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}*/

	public void setNullValues() {
		setBankCountryId(null);
		setBankCountryName(null);
		setBankId(null);
		setBankName(null);
		setBenfiBankId(null);
		setBenfiBankName(null);
		setExternalId(null);
		setBranchExternalId(null);
		setBranchExternalName(null);
		setBankExtRefSeqId(null);

		setDatabeneFiciaryBankId( null);
		setDataBranchExternalId( null);
		setDataBranchExternalYesOrNo( null);
		setDataFlexFiled2( null);
		setDataFlexFiled1( null);
		setDataFlexFiled3( null);
		setDataExternalId( null);
	}

	public void saveDataTableRecords() {
		
		if(mainDataTable!=null  && mainDataTable.size()!=0)
		{
			try{


				for (BankExternalReferenceDataTable bankRefDataTable : mainDataTable) {
					BankExternalReferenceHead bankExternalReferenceHead = new BankExternalReferenceHead();
					CountryMaster countryMaster = new CountryMaster();
					countryMaster.setCountryId(session.getCountryId());
					bankExternalReferenceHead.setApplicationCountry(countryMaster);
					CountryMaster bankCountryMaster = new CountryMaster();
					bankCountryMaster.setCountryId(bankRefDataTable.getCountryId());
					LOGGER.info("bankRefDataTable.getCountryId()" + bankRefDataTable.getCountryId());
					BankMaster bankMaster = new BankMaster();
					bankMaster.setBankId(bankRefDataTable.getBankId());
					bankExternalReferenceHead.setBank(bankMaster);
					BankMaster bankMaster1 = new BankMaster();
					bankMaster1.setBankId(bankRefDataTable.getBeneficaryBankId());
					bankExternalReferenceHead.setBeneficaryBank(bankMaster1);
					bankExternalReferenceHead.setBeneficaryBankCode(bankRefDataTable.getBeneficaryBankCode());
					bankExternalReferenceHead.setBankCode(bankCodeMap.get(bankRefDataTable.getBankId()));
					bankExternalReferenceHead.setBankExternalId(bankRefDataTable.getBankExternalId());
					LOGGER.info("Branch External ID" + bankRefDataTable.getBranchExternalId());
					bankExternalReferenceHead.setBankExtRefSeqId(bankRefDataTable.getBankExtRefSeqId());
					bankExternalReferenceHead.setCreatedBy(bankRefDataTable.getCreatedBy());
					bankExternalReferenceHead.setCreatedDate(bankRefDataTable.getCreatedOn());
					bankExternalReferenceHead.setIsActive(bankRefDataTable.getIsActive());
					// countryMaster.setCountryId(bankRefDataTable.getCountryId());
					CountryMaster bankcountryMaster = new CountryMaster();
					bankcountryMaster.setCountryId(bankRefDataTable.getCountryId());
					bankExternalReferenceHead.setBankCountry(bankcountryMaster);
					bankExternalReferenceHead.setIsActive(bankRefDataTable.getIsActive());
					bankExternalReferenceHead.setRemarks(bankRefDataTable.getRemarks());
					bankExternalReferenceHead.setModifiedBy(bankRefDataTable.getModifiedBy());
					bankExternalReferenceHead.setModifiedDate(bankRefDataTable.getModifiedDate());
					bankExternalReferenceHead.setApprovedDate(bankRefDataTable.getApprovedDate());
					bankExternalReferenceHead.setApprovedBy(bankRefDataTable.getApprovedBy());
					bankExternalReferenceHead.setRemarks(bankRefDataTable.getRemarks());
					//Added by Rabil on 06/03/2016
					bankExternalReferenceHead.setFlexField1(bankRefDataTable.getFlexField1());
					bankExternalReferenceHead.setFlexField2(bankRefDataTable.getFlexField2());
					bankExternalReferenceHead.setFlexField3(bankRefDataTable.getFlexField3());


					LOGGER.info(bankRefDataTable);
					if (null == bankRefDataTable.getBankExtRefSeqId()) {
						bankExternalReferenceHead.setCreatedBy(session.getUserName());
						bankExternalReferenceHead.setCreatedDate(new Date());
						bankExternalReferenceHead.setIsActive(Constants.U);
					}
					bankExternalReferenceservice.saveHeaderData(bankExternalReferenceHead);
					List<BankExternalReferenceDailogDataTable> dialog = bankRefDataTable.getDialogTable();
					LOGGER.info(dialog == null);
					if (null != dialog) {
						for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : dialog) {
							BankExternalReferenceDetail bankExternalReferenceDetail = new BankExternalReferenceDetail();
							bankExternalReferenceDetail.setBankExtRefDetailSeqId(bankExternalReferenceDailogDataTable.getBankExtRefDetailSeqId());
							LOGGER.info("bankExternalReferenceDailogDataTable.getBankExtRefDetailSeqId()" + bankExternalReferenceDailogDataTable.getBankExtRefDetailSeqId());
							countryMaster = new CountryMaster();
							countryMaster.setCountryId(session.getCountryId());
							bankExternalReferenceDetail.setApplicationCountry(countryMaster);
							LOGGER.info(bankCountryMaster.getCountryId());
							bankExternalReferenceDetail.setCountryId(bankCountryMaster);
							bankMaster = new BankMaster();
							bankMaster.setBankId(bankRefDataTable.getBankId());
							bankExternalReferenceDetail.setBank(bankMaster);
							bankMaster1 = new BankMaster();
							bankMaster1.setBankId(bankRefDataTable.getBeneficaryBankId());
							bankExternalReferenceDetail.setBeneficaryBank(bankMaster1);
							BankBranch bankBranch = new BankBranch();
							bankBranch.setBankBranchId(bankExternalReferenceDailogDataTable.getBranchId());
							bankExternalReferenceDetail.setBeneficaryBranch(bankBranch);
							bankExternalReferenceDetail.setBeneficaryBankCode(bankExternalReferenceHead.getBeneficaryBankCode());
							bankExternalReferenceDetail.setBankCode(bankCodeMap.get(bankRefDataTable.getBankId()));
							bankExternalReferenceDetail.setBeneficaryBranchCode(bankExternalReferenceDailogDataTable.getBranchCode());
							LOGGER.info("bankExternalReferenceDailogDataTable.getBranchCode()" + bankExternalReferenceDailogDataTable.getBranchCode());
							LOGGER.info("bankRefDataTable.getBranchExternalId()" + bankExternalReferenceDailogDataTable.getBranchExternalId());
							bankExternalReferenceDetail.setBankBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
							bankExternalReferenceDetail.setIsActive(bankExternalReferenceDailogDataTable.getIsActive());
							bankExternalReferenceDetail.setCreatedBy(bankExternalReferenceDailogDataTable.getCreatedBy());
							bankExternalReferenceDetail.setCreatedDate(bankExternalReferenceDailogDataTable.getCreatedDate());
							bankExternalReferenceDetail.setModifiedBy(bankExternalReferenceDailogDataTable.getModifiedBy());
							bankExternalReferenceDetail.setModifiedDate(bankExternalReferenceDailogDataTable.getModifiedDate());
							bankExternalReferenceDetail.setRemarks(bankExternalReferenceHead.getRemarks());
							bankExternalReferenceDetail.setApprovedDate(bankExternalReferenceDailogDataTable.getApprovedDate());
							bankExternalReferenceDetail.setApprovedBy(bankExternalReferenceDailogDataTable.getApprovedBy());
							bankExternalReferenceDetail.setBankExternalReferenceHead(bankExternalReferenceHead);
							//Added Rabil 03/03/2016
							bankExternalReferenceDetail.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
							bankExternalReferenceDetail.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
							bankExternalReferenceDetail.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());


							LOGGER.info(bankRefDataTable);
							bankExternalReferenceservice.saveDetailData(bankExternalReferenceDetail);
						}
					}
				}
				RequestContext.getCurrentInstance().execute("save.show();");
				mainDataTable.clear();
				mainNewListDataTable.clear();
				setBooRenderDataTablePanel(false);
				setBooRenderSavePanel(false);
				setNullValues();
				editFalg = false;
				bankRefDataTableList2.clear();
			}catch(NullPointerException ne){
				LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::saveDataTableRecords"); 
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return; 
			}catch(Exception exception){
				LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
			}
		}else
		{
			RequestContext.getCurrentInstance().execute("norecord.show();");
		}
		
	}

	public void setBranchExternalIdForDT(BankExternalReferenceDailogDataTable bankExternalReferenceDataTable) {
		//bankExternalReferenceDataTable.setBranchExternalId(null);
		if(bankExternalReferenceDataTable.getBranchExternalId()!=null&&!bankExternalReferenceDataTable.getBranchExternalId().trim().equalsIgnoreCase("" )){
			List<BankExternalReferenceDailogDataTable> lstbankRefDailogDataTable =getBankRefDataTableList();
			if(lstbankRefDailogDataTable.size()!=0)
			{
				for (int i = 0; i < lstbankRefDailogDataTable.size(); i++) {
					BankExternalReferenceDailogDataTable bankExternalRefMainDataTable=lstbankRefDailogDataTable.get(i);
					if(bankExternalRefMainDataTable.getBranchId().compareTo(bankExternalReferenceDataTable.getBranchId())==0)
					{
						lstbankRefDailogDataTable.remove(i);
						break;
					}
				} 
				/*
		 for(BankExternalReferenceDailogDataTable bankExternalRefMainDataTable  :bankRefDailogDataTable)
				{
					if(bankExternalRefMainDataTable.getBranchId().compareTo(bankExternalReferenceDataTable.getBranchId())==0)
					{
						bankRefDailogDataTable.remove(bankExternalRefMainDataTable);
						break;
					}
				}*/
				BankExternalReferenceDailogDataTable bankExternalRefDataTable = new BankExternalReferenceDailogDataTable();
				bankExternalRefDataTable.setBranchExternalId(bankExternalReferenceDataTable.getBranchExternalId());
				bankExternalRefDataTable.setBankId(bankExternalReferenceDataTable.getBankId());
				bankExternalRefDataTable.setBeneficaryBankId(bankExternalReferenceDataTable.getBeneficaryBankId());
				bankExternalRefDataTable.setBranchCode(bankExternalReferenceDataTable.getBranchCode());
				bankExternalRefDataTable.setBranchId(bankExternalReferenceDataTable.getBranchId());
				bankExternalRefDataTable.setBranchDescription(bankExternalReferenceDataTable.getBranchDescription());
				bankExternalRefDataTable.setBankCode(bankExternalReferenceDataTable.getBankCode());
				bankExternalRefDataTable.setBeneficaryBankCode(bankExternalReferenceDataTable.getBeneficaryBankCode());
				bankExternalRefDataTable.setCountryId(bankExternalReferenceDataTable.getCountryId());

				//Added by Rabil on 06/03/2016
				bankExternalRefDataTable.setFlexField1(bankExternalReferenceDataTable.getFlexField1());
				bankExternalRefDataTable.setFlexField2(bankExternalReferenceDataTable.getFlexField2());
				bankExternalRefDataTable.setFlexField3(bankExternalReferenceDataTable.getFlexField3());

				lstbankRefDailogDataTable.add(bankExternalRefDataTable);

			}else
			{
				BankExternalReferenceDailogDataTable bankExternalRefDataTable = new BankExternalReferenceDailogDataTable();
				bankExternalRefDataTable.setBranchExternalId(bankExternalReferenceDataTable.getBranchExternalId());
				bankExternalRefDataTable.setBankId(bankExternalReferenceDataTable.getBankId());
				bankExternalRefDataTable.setBeneficaryBankId(bankExternalReferenceDataTable.getBeneficaryBankId());
				bankExternalRefDataTable.setBranchCode(bankExternalReferenceDataTable.getBranchCode());
				bankExternalRefDataTable.setBranchId(bankExternalReferenceDataTable.getBranchId());
				bankExternalRefDataTable.setBranchDescription(bankExternalReferenceDataTable.getBranchDescription());
				bankExternalRefDataTable.setBankCode(bankExternalReferenceDataTable.getBankCode());
				bankExternalRefDataTable.setBeneficaryBankCode(bankExternalReferenceDataTable.getBeneficaryBankCode());
				bankExternalRefDataTable.setCountryId(bankExternalReferenceDataTable.getCountryId());

				//Added by Rabil on 06/03/2016
				bankExternalRefDataTable.setFlexField1(bankExternalReferenceDataTable.getFlexField1());
				bankExternalRefDataTable.setFlexField2(bankExternalReferenceDataTable.getFlexField2());
				bankExternalRefDataTable.setFlexField3(bankExternalReferenceDataTable.getFlexField3());

				lstbankRefDailogDataTable.add(bankExternalRefDataTable);
			}
			setBankRefDailogDataTable(null);
			setBankRefDailogDataTable(lstbankRefDailogDataTable);
			setBankRefDataTableListTemp(lstbankRefDailogDataTable);
		}



	} 

	public void clearAllFields() {
		setBankCountryId(null);
		setBankCountryName(null);
		setBankId(null);
		setBankName(null);
		setBenfiBankId(null);
		setBenfiBankName(null);
		setExternalId(null);
		setBranchExternalId(null);
		setBranchExternalName(null);
		setRemarks(null);
		setModifiedBy(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setModifiedDate(null);
		setRemarks(null);
		setFlexField1(null);
		setFlexField2(null);
		setFlexField3(null);
		setBooRenderSavePanel( false);
		setBooRenderDataTablePanel( false);
		if(bankList!=null)
		{
			bankList.clear();
		}
		
		if(beneficaryBankList!=null)
		{
			beneficaryBankList.clear();
		}
		
		//mainDataTable.clear();
		if(mainNewListDataTable!=null)
		{
			mainNewListDataTable.clear();
		}
		if(bankRefDailogDataTable!=null)
		{
			bankRefDailogDataTable.clear();
		}
		
		setMainDataTable(null);
		setBankExtRefSeqId(null);
		setBankRefDataTableList(null);
	}

	public void clearAllFieldsforADD() {
		setBenfiBankId(null);
		setBenfiBankName(null);
		setExternalId(null);
		setBranchExternalId(null);
		setBranchExternalName(null);
		setRemarks(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);
		setFlexField1(null);
		setFlexField2(null);
		setFlexField3(null);

		setDatabeneFiciaryBankId( null);
		setDataBranchExternalId( null);
		setDataBranchExternalYesOrNo( null);
		setDataFlexFiled2( null);
		setDataFlexFiled1( null);
		setDataFlexFiled3( null);
		setDataExternalId( null);

	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public BigDecimal getBankExtRefSeqId() {
		return bankExtRefSeqId;
	}

	public void setBankExtRefSeqId(BigDecimal bankExtRefSeqId) {
		this.bankExtRefSeqId = bankExtRefSeqId;
	}

	public void editRecord(BankExternalReferenceDataTable bean) {
		try{
			editClearValues();
			LOGGER.info("Entering into editRecord method");
			editFalg = true;
			setHideEdit(true);
			setIscleardisable(true);
			LOGGER.info(bean);
			String bankBrachExtrnalId=null;
			if(bean.getBranchExternalYesNo()!=null)
			{
				if(bean.getBranchExternalYesNo().equalsIgnoreCase("Yes"))
				{
					bankBrachExtrnalId="Yes";
				}else
				{
					bankBrachExtrnalId="No";
				}
			}else
			{
				bankBrachExtrnalId="No";
			}

			setDataBranchExternalYesOrNo(bankBrachExtrnalId);
			//String bankBrachExtrnalId=bean.getBranchExternalId();

			
			setBankCountryId(bean.getCountryId());

			setBankCountryName(generalService.getCountryName( session.getLanguageId(), bean.getCountryId()));
			setBenfiBankName( generalService.getBankName(bean.getBeneficaryBankId()));
			setBankId(bean.getBankId());
			setBankName(bankMap.get(bean.getBankId()));
			setDatabeneFiciaryBankId(bean.getBeneficaryBankId() );
			setDataFlexFiled1(bean.getFlexField1());
			setDataFlexFiled2(bean.getFlexField2() );
			setDataFlexFiled3(bean.getFlexField3());
			setDataExternalId( bean.getBankExternalId() );
			setBankExtRefSeqId(bean.getBankExtRefSeqId());
			setCreatedBy(bean.getCreatedBy());
			setCreatedOn(bean.getCreatedOn());
			setApprovedBy(bean.getApprovedBy());
			setApprovedDate(bean.getApprovedDate());
			
			if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.Yes))
			{
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				setIsActive(Constants.Yes);

			}else if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.D))
			{
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				setIsActive(Constants.No);
			}else if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.U) && bean.getModifiedBy()!=null && bean.getModifiedDate()!=null)
			{
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				setIsActive(Constants.U);

			}else
			{
				setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				setIsActive(Constants.U);
			}
			/*if(bean.getBankExtRefSeqId()!=null){
 				//setModifiedBy(session.getUserName());
 				//setModifiedDate(new Date());
 				if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.Yes))
 				{
 					setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);

 				}else if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.D))
 				{
 					setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
 				}else if(bean.getIsActive()!=null && bean.getIsActive().equalsIgnoreCase(Constants.U) && bean.getModifiedBy()!=null && bean.getModifiedDate()!=null)
 				{
 					setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

 				}else
 				{
 					setDynamicLabelForActivateDeactivate(Constants.REMOVE);
 				}


 			}else{
 				setDynamicLabelForActivateDeactivate(Constants.REMOVE);
 			}*/

			setBankExtRefSeqId(bean.getBankExtRefSeqId());

			if (bean.getBankExtRefSeqId() != null) {/*
				BankExternalReferenceHead headRecord = bankExternalReferenceservice.findHeaderById(bean.getBankExtRefSeqId());
				List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(headRecord.getBankExtRefSeqId());
				if (null != detailList && !detailList.isEmpty()) {

					for (BankExternalReferenceDetail detail : detailList) {
						LOGGER.info("************************" + detail.getBankExtRefDetailSeqId());
						BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
						dialogTable.setBranchCode(detail.getBeneficaryBranchCode());
						dialogTable.setBranchDescription(detail.getBeneficaryBranch().getBranchFullName());

						dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
						setBankCountryId(detail.getCountryId().getCountryId());
						dialogTable.setCountryId(detail.getCountryId().getCountryId());
						dialogTable.setBankExtRefDetailSeqId(detail.getBankExtRefDetailSeqId());
						dialogTable.setBankExtRefSeqId(bean.getBankExtRefSeqId());
						dialogTable.setBranchId(detail.getBeneficaryBranch().getBankBranchId());
						dialogTable.setIsActive(detail.getIsActive());
						dialogTable.setModifiedBy(detail.getModifiedBy());
						dialogTable.setModifiedDate(detail.getModifiedDate());
						dialogTable.setApprovedBy(detail.getApprovedBy());
						dialogTable.setApprovedDate(detail.getApprovedDate());
						dialogTable.setRemarks(detail.getRemarks());

						if(detail.getIsActive()!=null && detail.getIsActive().equalsIgnoreCase(Constants.Yes))
		 				{
		 					setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);

		 				}else if(detail.getIsActive()!=null && detail.getIsActive().equalsIgnoreCase(Constants.D))
		 				{
		 					setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);

		 				}else if(detail.getIsActive()!=null && detail.getIsActive().equalsIgnoreCase(Constants.U) && detail.getModifiedBy()!=null && detail.getModifiedDate()!=null)
		 				{
		 					setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);

		 				}else
		 				{
		 					setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		 				}


						//Added by Rabil 06/03/2016
						dialogTable.setFlexField1(detail.getFlexField1());
						dialogTable.setFlexField2(detail.getFlexField2());
						dialogTable.setFlexField3(detail.getFlexField3());
						dialogTable.setBranchExternalYesNo(getBranchExternalId());
						bankRefDataTableList.add(dialogTable);
					}

				}else
				{
					setBranchExternalId(String.valueOf("2"));
					setBankRefDataTableList(null);
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				}
				setIsActive(Constants.U);
				setApprovedBy(headRecord.getApprovedBy());
				setApprovedDate(headRecord.getApprovedDate());
				setBankId(headRecord.getBank().getBankId());
				//setExternalId(headRecord.getBankExternalId());
				setBankName(bankMap.get(headRecord.getBank().getBankId()));
				//setBenfiBankId(headRecord.getBeneficaryBank().getBankId());
				if(bean.getBankExtRefSeqId()!=null){
				setModifiedBy(session.getUserName());
				setModifiedDate(new Date());
				setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
				}else{
				setDynamicLabelForActivateDeactivate(Constants.REMOVE);
				}
				setCreatedBy(headRecord.getCreatedBy());
				setCreatedOn(headRecord.getCreatedDate());
				setRemarks(headRecord.getRemarks());
				mainDataTable.remove(bean);
				if (null != detailList && !detailList.isEmpty()) {
					setDataBranchExternalId( String.valueOf("1"));
					setDataBranchExternalYesOrNo("Yes");
					//setBranchExternalId(String.valueOf("1"));
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				} else {
					//setBranchExternalId(String.valueOf("2"));
					setDataBranchExternalId( String.valueOf("2"));
					setDataBranchExternalYesOrNo("No");
				}*/

				setModifiedBy(session.getUserName());
				setModifiedDate(new Date());
				BankExternalReferenceHead headRecord = bankExternalReferenceservice.findHeaderById(bean.getBankExtRefSeqId());
				List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(headRecord.getBankExtRefSeqId());

				if(detailList!=null && detailList.size()>0)
				{
					setDataBranchExternalYesOrNo("Yes");
					List<BankExternalReferenceDailogDataTable> existAddbankRefDataTableList=bean.getDialogTable();
					List<BankExternalReferenceDailogDataTable> addbankRefDataTableList =editRecordExistedBranches(detailList,existAddbankRefDataTableList);
					setBankRefDataTableList(addbankRefDataTableList);
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				}else
				{
					List<BankExternalReferenceDailogDataTable> existAddbankRefDataTableList=bean.getDialogTable();
					if(existAddbankRefDataTableList!=null && existAddbankRefDataTableList.size()>0)
					{
						List<BankExternalReferenceDailogDataTable> addbankRefDataTableList=editAllBranchData(existAddbankRefDataTableList);
						setBankRefDataTableList(addbankRefDataTableList);
					}else
					{
						setBankRefDataTableList(null);
					}
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				}

				mainDataTable.remove(bean);
			} else {
				popupinDataTable.clear();
				//setBranchExternalId(String.valueOf("1"));
				//setDataBranchExternalId(bankBrachExtrnalId);


				setIsActive( Constants.U);
				List<BankExternalReferenceDailogDataTable> dailogBankRefDataTableList = null;
				for (int i = 0; i < mainDataTable.size(); i++) {
					BankExternalReferenceDataTable element = mainDataTable.get(i);
					if (element.getIndex().equals(bean.getIndex())) {
						if (element.getIndex().equals(bean.getIndex())) {
							dailogBankRefDataTableList = mainDataTable.get(i).getDialogTable();
							break;
						}
					}
				}

				if (null != dailogBankRefDataTableList && dailogBankRefDataTableList.size()>0) {
					setBankRefDataTableList(null);
					/*for (BankExternalReferenceDailogDataTable newRecods : bankRefDataTableList) {
						BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
						dialogTable.setBranchCode(newRecods.getBranchCode());
						dialogTable.setBranchDescription(newRecods.getBranchDescription());

						dialogTable.setBranchExternalId(newRecods.getBranchExternalId());
						dialogTable.setBankExtRefDetailSeqId(newRecods.getBankExtRefDetailSeqId());
						dialogTable.setBranchId(newRecods.getBranchId());
						//Added by Rabil on 06/03/2016
						dialogTable.setFlexField1(newRecods.getFlexField1());
						dialogTable.setFlexField2(newRecods.getFlexField2());
						dialogTable.setFlexField3(newRecods.getFlexField3());
						dialogTable.setIsActive(Constants.U);
						dialogTable.setCountryId(newRecods.getCountryId());

						LOGGER.info("newRecods.getBankExtRefDetailSeqId()" + newRecods.getBankExtRefDetailSeqId());
						popupinDataTable.add(dialogTable);
					}*/

					List<BankExternalReferenceDailogDataTable> addbankRefDataTableList=editAllBranchData(dailogBankRefDataTableList);
					setBankRefDataTableList(addbankRefDataTableList);
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				} else {
					setBranchExternalId(String.valueOf("2"));
					setBankRefDataTableList(null);
					RequestContext.getCurrentInstance().execute("editalreadyExist.show();");
				}
				mainDataTable.remove(bean);
			}
			LOGGER.info("Exit into editRecord method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::editRecord"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}
	List<BankExternalReferenceDetail> list = null;
	List<BankExternalReferenceHead> headerList = null;

	/* public void view() {
		try{
			LOGGER.info("Entering into view method");
			List<BankExternalReferenceDataTable> detailZero = new ArrayList<BankExternalReferenceDataTable>();
			List<BankExternalReferenceDataTable> detailnotZero = new ArrayList<BankExternalReferenceDataTable>();
			setIsdisable(true);
			setHideEdit(false);
			setIscleardisable(false);
			LOGGER.info("getCountryId " + getBankCountryId());
			LOGGER.info("get bank id " + getBankId());
			if (((getBankCountryId() != null && getBankId() != null))) {
				bankRefDataTableList2.clear();
				mainDataTable.clear();
				hList = bankExternalReferenceservice.viewBean(getBankCountryId(), getBankId(), session.getCountryId(),getBenfiBankId());
				// editFalg = true;
				if (hList.size() > 0) {
					BigDecimal detailSeqId = null;
					int count = 0;
					LOGGER.info("Size of the headdd" + hList.size());
					for (BankExternalReferenceHead head : hList) {
						BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
						dataTableRecord.setCountryId(getBankCountryId());
						dataTableRecord.setCountryName(bankCountryMap.get(getBankCountryId()));
						dataTableRecord.setBankId(head.getBank().getBankId());
						dataTableRecord.setBankName(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankCode(bankCodeMap.get(head.getBank().getBankId()));
						dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
						dataTableRecord.setBeneficaryBankName(beneficaryBankMap.get(head.getBeneficaryBank().getBankId()));
						dataTableRecord.setBeneficaryBankCode(beneficaryBankCodeMap.get(head.getBeneficaryBank().getBankId()));
						// dataTableRecord.setBranchCode(head.get);
						dataTableRecord.setBranchDescription(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankExternalId(head.getBankExternalId());
						dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
						dataTableRecord.setModifiedBy(head.getModifiedBy());
						dataTableRecord.setModifiedDate(head.getModifiedDate());
						dataTableRecord.setDynamicLabelForActivateDeactivate(setreverActiveStatus(head.getIsActive()));
						if (dataTableRecord.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
							if (dataTableRecord.getModifiedBy() == null && dataTableRecord.getModifiedDate() == null) {
							} else {
								dataTableRecord.setDynamicLabelForActivateDeactivate("");
							}
						}
						dataTableRecord.setBankExtRefDetailSeqId(detailSeqId);
						dataTableRecord.setIsActive(head.getIsActive());
						dataTableRecord.setCreatedBy(head.getCreatedBy());
						dataTableRecord.setCreatedOn(head.getCreatedDate());
						dataTableRecord.setCountryId(head.getBankCountry().getCountryId());
						dataTableRecord.setApprovedBy(head.getApprovedBy());
						dataTableRecord.setApprovedDate(head.getApprovedDate());
						dataTableRecord.setRemarks(head.getRemarks());
						dataTableRecord.setIndex(String.valueOf(head.getBeneficaryBank().getBankId()) + head.getBankExternalId());

						//Added by Rabil on 06 Mar 2016
						dataTableRecord.setFlexField1(head.getFlexField1());
						dataTableRecord.setFlexField2(head.getFlexField2());
						dataTableRecord.setFlexField3(head.getFlexField3());



						LOGGER.info(dataTableRecord);
						List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(head.getBankExtRefSeqId());
						if (null != detailList && detailList.isEmpty()) {
							detailZero.add(dataTableRecord);
						} else {
							detailnotZero.add(dataTableRecord);
						}
						// mainDataTable.add(dataTableRecord);
						List<BankExternalReferenceDailogDataTable> popup = new ArrayList<BankExternalReferenceDailogDataTable>();
						if (null != detailList && !detailList.isEmpty()) {
							for (BankExternalReferenceDetail bankExternalReferenceDetail : detailList) {
								BankExternalReferenceDailogDataTable detailDialog = new BankExternalReferenceDailogDataTable();
								detailDialog.setBankCode(bankExternalReferenceDetail.getBankCode());
								detailDialog.setBankExtRefDetailSeqId(bankExternalReferenceDetail.getBankExtRefDetailSeqId());
								detailDialog.setBankExtRefSeqId(bankExternalReferenceDetail.getBankExternalReferenceHead().getBankExtRefSeqId());
								detailDialog.setBankId(bankExternalReferenceDetail.getBank().getBankId());
								detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
								detailDialog.setBeneficaryBankId(bankExternalReferenceDetail.getBeneficaryBank().getBankId());
								detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
								detailDialog.setBranchDescription(bankExternalReferenceDetail.getBeneficaryBranch().getBranchFullName());
								detailDialog.setBranchExternalId(bankExternalReferenceDetail.getBankBranchExternalId());
								detailDialog.setBranchId(bankExternalReferenceDetail.getBeneficaryBranch().getBankBranchId());
								detailDialog.setBranchCode(bankExternalReferenceDetail.getBeneficaryBranchCode());
								//Added by Rabil on 06 Mar 2016
								detailDialog.setFlexField1(bankExternalReferenceDetail.getFlexField1());
								detailDialog.setFlexField2(bankExternalReferenceDetail.getFlexField2());
								detailDialog.setFlexField3(bankExternalReferenceDetail.getFlexField3());

								LOGGER.info(bankExternalReferenceDetail.getBeneficaryBranchCode());
								// detailDialog.setBranchCode(bankExternalReferenceDetail.);
								popup.add(detailDialog);
							}
							detailnotZero.get(count).setDialogTable(popup);
							count++;
						}
					}
					if (detailZero != null) {
						LOGGER.info(detailZero.size());
					}
					if (detailnotZero != null) {
						LOGGER.info(detailnotZero.size());
					}
					mainDataTable.addAll(detailZero);
					mainDataTable.addAll(detailnotZero);
					if (!mainDataTable.isEmpty()) {
						setBooRenderDataTablePanel(true);
						setBooRenderSavePanel(true);
					}
				} else {
					RequestContext.getCurrentInstance().execute("norecord.show();");
					setBooRenderDataTablePanel(false);
					setBooRenderSavePanel(false);
				}
			} else {
				RequestContext.getCurrentInstance().execute("chooseAnyOne.show();");
			}
			popupinDataTable.clear();
			LOGGER.info("Exit into view method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	} */

	public void view(){
		try{
			setIsdisable(true);
			setHideEdit(false);
			setIscleardisable(false);
			setBankRefDataTableList(null);
			setMainDataTable(null);
			
			List<BankExternalReferenceDataTable>  totalViewList= new ArrayList<BankExternalReferenceDataTable>();
			if (((getBankCountryId() != null && getBankId() != null))) {

				headerList = bankExternalReferenceservice.viewBean(getBankCountryId(), getBankId(), session.getCountryId(),getBenfiBankId());
				if(headerList.size()>0){
					BigDecimal detailSeqId = null;

					for (BankExternalReferenceHead head : headerList) {
						BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
						dataTableRecord.setCountryId(getBankCountryId());
						dataTableRecord.setCountryName(bankCountryMap.get(getBankCountryId()));
						dataTableRecord.setBankId(head.getBank().getBankId());
						dataTableRecord.setBankName(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankCode(bankCodeMap.get(head.getBank().getBankId()));
						dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
						dataTableRecord.setBeneficaryBankName(beneficaryBankMap.get(head.getBeneficaryBank().getBankId()));
						dataTableRecord.setBeneficaryBankCode(beneficaryBankCodeMap.get(head.getBeneficaryBank().getBankId()));
						// dataTableRecord.setBranchCode(head.get);
						dataTableRecord.setBranchDescription(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankExternalId(head.getBankExternalId());
						dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
						dataTableRecord.setModifiedBy(head.getModifiedBy());
						dataTableRecord.setModifiedDate(head.getModifiedDate());
						dataTableRecord.setDynamicLabelForActivateDeactivate(setreverActiveStatus(head.getIsActive()));
						if (dataTableRecord.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
							if (dataTableRecord.getModifiedBy() == null && dataTableRecord.getModifiedDate() == null) {
							} else {
								dataTableRecord.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}
						}




						dataTableRecord.setBankExtRefDetailSeqId(detailSeqId);
						dataTableRecord.setIsActive(head.getIsActive());
						dataTableRecord.setCreatedBy(head.getCreatedBy());
						dataTableRecord.setCreatedOn(head.getCreatedDate());
						dataTableRecord.setCountryId(head.getBankCountry().getCountryId());
						dataTableRecord.setApprovedBy(head.getApprovedBy());
						dataTableRecord.setApprovedDate(head.getApprovedDate());
						dataTableRecord.setRemarks(head.getRemarks());
						dataTableRecord.setIndex(String.valueOf(head.getBeneficaryBank().getBankId()) + head.getBankExternalId());

						//Added by Rabil on 06 Mar 2016
						dataTableRecord.setFlexField1(head.getFlexField1());
						dataTableRecord.setFlexField2(head.getFlexField2());
						dataTableRecord.setFlexField3(head.getFlexField3());



						LOGGER.info(dataTableRecord);
						List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(head.getBankExtRefSeqId());

						List<BankExternalReferenceDailogDataTable> popupView = new ArrayList<BankExternalReferenceDailogDataTable>();
						if (null != detailList && !detailList.isEmpty()) {
							for (BankExternalReferenceDetail bkExternalReferDetl : detailList) {
								BankExternalReferenceDailogDataTable detailDialogView = new BankExternalReferenceDailogDataTable();
								detailDialogView.setBankCode(bkExternalReferDetl.getBankCode());
								detailDialogView.setBankExtRefDetailSeqId(bkExternalReferDetl.getBankExtRefDetailSeqId());
								detailDialogView.setBankExtRefSeqId(bkExternalReferDetl.getBankExternalReferenceHead().getBankExtRefSeqId());
								//detailDialog.setBranchExternalId(bankExternalReferenceDetail.getBankBranchExternalId());
								detailDialogView.setBankId(bkExternalReferDetl.getBank().getBankId());
								detailDialogView.setBeneficaryBankCode(bkExternalReferDetl.getBeneficaryBankCode());
								detailDialogView.setBeneficaryBankId(bkExternalReferDetl.getBeneficaryBank().getBankId());
								detailDialogView.setBeneficaryBankCode(bkExternalReferDetl.getBeneficaryBankCode());
								detailDialogView.setBranchDescription(bkExternalReferDetl.getBeneficaryBranch().getBranchFullName());
								detailDialogView.setBranchExternalId(bkExternalReferDetl.getBankBranchExternalId());
								detailDialogView.setBranchId(bkExternalReferDetl.getBeneficaryBranch().getBankBranchId());
								detailDialogView.setBranchCode(bkExternalReferDetl.getBeneficaryBranchCode());
								//Added by Rabil on 06 Mar 2016
								detailDialogView.setFlexField1(bkExternalReferDetl.getFlexField1());
								detailDialogView.setFlexField2(bkExternalReferDetl.getFlexField2());
								detailDialogView.setFlexField3(bkExternalReferDetl.getFlexField3());
								detailDialogView.setIsActive(bkExternalReferDetl.getIsActive());
								detailDialogView.setCreatedBy(bkExternalReferDetl.getCreatedBy());
								detailDialogView.setCreatedDate(bkExternalReferDetl.getCreatedDate());
								detailDialogView.setModifiedBy(bkExternalReferDetl.getModifiedBy());
								detailDialogView.setModifiedDate(bkExternalReferDetl.getModifiedDate());
								detailDialogView.setApprovedBy(bkExternalReferDetl.getApprovedBy());
								detailDialogView.setApprovedDate(bkExternalReferDetl.getApprovedDate());
								LOGGER.info(bkExternalReferDetl.getBeneficaryBranchCode());
								popupView.add(detailDialogView);
							}
							dataTableRecord.setDialogTable(popupView);

						}
						totalViewList.add( dataTableRecord);
					}
					//mainDataTable.addAll(totalViewList);
					if(mainNewListDataTable.size()>0){
						setIsdisable(false);	
						//totalViewList.addAll( mainNewListDataTable);
					}else{
						setIsdisable(true);	
					}
					setMainDataTable(totalViewList);



					if (getMainDataTable()!=null && getMainDataTable().size()>0) {
						setBooRenderDataTablePanel(true);
						setBooRenderSavePanel(true);
					}else{
						setBooRenderDataTablePanel( false);
						setBooRenderSavePanel( false);
					}
				}else{
					List<BankExternalReferenceDataTable>   exstingList=  getMainDataTable();
					if(exstingList!=null && exstingList.size()==0)
					{
						RequestContext.getCurrentInstance().execute("norecord.show();");
						setBooRenderDataTablePanel(false);
						setBooRenderSavePanel(false);
					}else
					{
						setBooRenderDataTablePanel(true);
						setBooRenderSavePanel(true);
					}

				}
			}else{
				RequestContext.getCurrentInstance().execute("chooseAnyOne.show();"); 
			}



		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public List<BankExternalReferenceDataTable>  viewAllForGettingDataBaseRecords(){

		List<BankExternalReferenceDataTable>  totalViewList= new ArrayList<BankExternalReferenceDataTable>();
		try{
			//setIsdisable(true);
			setHideEdit(false);
			setIscleardisable(false);


			if (((getBankCountryId() != null && getBankId() != null))) {

				List<BankExternalReferenceHead> headerList1  = bankExternalReferenceservice.viewBean(getBankCountryId(), getBankId(), session.getCountryId(),getBenfiBankId());
				if(headerList1.size()>0){
					BigDecimal detailSeqId = null;

					for (BankExternalReferenceHead head : headerList1) {
						BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
						dataTableRecord.setCountryId(getBankCountryId());
						dataTableRecord.setCountryName(bankCountryMap.get(getBankCountryId()));
						dataTableRecord.setBankId(head.getBank().getBankId());
						dataTableRecord.setBankName(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankCode(bankCodeMap.get(head.getBank().getBankId()));
						dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
						dataTableRecord.setBeneficaryBankName(beneficaryBankMap.get(head.getBeneficaryBank().getBankId()));
						dataTableRecord.setBeneficaryBankCode(beneficaryBankCodeMap.get(head.getBeneficaryBank().getBankId()));
						// dataTableRecord.setBranchCode(head.get);
						dataTableRecord.setBranchDescription(bankMap.get(head.getBank().getBankId()));
						dataTableRecord.setBankExternalId(head.getBankExternalId());
						dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
						dataTableRecord.setModifiedBy(head.getModifiedBy());
						dataTableRecord.setModifiedDate(head.getModifiedDate());
						dataTableRecord.setDynamicLabelForActivateDeactivate(setreverActiveStatus(head.getIsActive()));
						if (dataTableRecord.getDynamicLabelForActivateDeactivate().equals(Constants.DELETE)) {
							if (dataTableRecord.getModifiedBy() == null && dataTableRecord.getModifiedDate() == null) {
							} else {
								dataTableRecord.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}
						}




						dataTableRecord.setBankExtRefDetailSeqId(detailSeqId);
						dataTableRecord.setIsActive(head.getIsActive());
						dataTableRecord.setCreatedBy(head.getCreatedBy());
						dataTableRecord.setCreatedOn(head.getCreatedDate());
						dataTableRecord.setCountryId(head.getBankCountry().getCountryId());
						dataTableRecord.setApprovedBy(head.getApprovedBy());
						dataTableRecord.setApprovedDate(head.getApprovedDate());
						dataTableRecord.setRemarks(head.getRemarks());
						dataTableRecord.setIndex(String.valueOf(head.getBeneficaryBank().getBankId()) + head.getBankExternalId());

						//Added by Rabil on 06 Mar 2016
						dataTableRecord.setFlexField1(head.getFlexField1());
						dataTableRecord.setFlexField2(head.getFlexField2());
						dataTableRecord.setFlexField3(head.getFlexField3());



						LOGGER.info(dataTableRecord);
						List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(head.getBankExtRefSeqId());

						List<BankExternalReferenceDailogDataTable> popup = new ArrayList<BankExternalReferenceDailogDataTable>();
						if (null != detailList && !detailList.isEmpty()) {
							for (BankExternalReferenceDetail bankExternalReferenceDetail : detailList) {
								BankExternalReferenceDailogDataTable detailDialog = new BankExternalReferenceDailogDataTable();
								detailDialog.setBankCode(bankExternalReferenceDetail.getBankCode());
								detailDialog.setBankExtRefDetailSeqId(bankExternalReferenceDetail.getBankExtRefDetailSeqId());
								detailDialog.setBankExtRefSeqId(bankExternalReferenceDetail.getBankExternalReferenceHead().getBankExtRefSeqId());
								detailDialog.setBankId(bankExternalReferenceDetail.getBank().getBankId());
								detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
								detailDialog.setBeneficaryBankId(bankExternalReferenceDetail.getBeneficaryBank().getBankId());
								detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
								detailDialog.setBranchDescription(bankExternalReferenceDetail.getBeneficaryBranch().getBranchFullName());
								detailDialog.setBranchExternalId(bankExternalReferenceDetail.getBankBranchExternalId());
								detailDialog.setBranchId(bankExternalReferenceDetail.getBeneficaryBranch().getBankBranchId());
								detailDialog.setBranchCode(bankExternalReferenceDetail.getBeneficaryBranchCode());
								//Added by Rabil on 06 Mar 2016
								detailDialog.setFlexField1(bankExternalReferenceDetail.getFlexField1());
								detailDialog.setFlexField2(bankExternalReferenceDetail.getFlexField2());
								detailDialog.setFlexField3(bankExternalReferenceDetail.getFlexField3());
								LOGGER.info(bankExternalReferenceDetail.getBeneficaryBranchCode());
								popup.add(detailDialog);
							}
							dataTableRecord.setDialogTable(popup );

						}
						totalViewList.add( dataTableRecord);
					}

					//setMainDataTable(totalViewList);


					if (totalViewList!=null && totalViewList.size()>0) {
						setBooRenderDataTablePanel(true);
						setBooRenderSavePanel(true);
					}
				} 
			}



		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::view"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return totalViewList; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return totalViewList;        
		}
		return totalViewList; 
	}





























	public void searchingRecordsinDataBase() {
		try{
			LOGGER.info("Entering into searchingRecordsinDataBase method");
			LOGGER.info("*****************************************searchingRecordsinDataBase************************************************************");
			LOGGER.info("getBankCountryId() " + getBankCountryId());
			LOGGER.info("getBankId" + getBankId());
			LOGGER.info("getBenfiBankId" + getBenfiBankId());
			LOGGER.info("getExternalId" + getExternalId());
			LOGGER.info("getBranchExternalId" + getBranchExternalId());
			LOGGER.info("****************************************searchingRecordsinDataBase***********************************************************");
			// bankRefDataTableList2.clear();
			headerList = bankExternalReferenceservice.getAllRecordsfromHead(session.getCountryId(), getBankCountryId(), getBankId(), getBenfiBankId(), getExternalId());
			LOGGER.info("Size of the headdd" + headerList.size());
			for (BankExternalReferenceHead head : headerList) {
				if (head.getBankExternalId().equals(getExternalId()) && !head.getIsActive().equals(Constants.U)) {
					BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
					dataTableRecord.setCountryId(getBankCountryId());
					dataTableRecord.setCountryName(bankCountryMap.get(getBankCountryId()));
					dataTableRecord.setBankId(head.getBank().getBankId());
					dataTableRecord.setBankName(bankMap.get(head.getBank().getBankId()));
					dataTableRecord.setBankCode(bankCodeMap.get(head.getBank().getBankId()));
					dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
					dataTableRecord.setBeneficaryBankName(beneficaryBankMap.get(head.getBeneficaryBank().getBankId()));
					dataTableRecord.setBeneficaryBankCode(beneficaryBankCodeMap.get(head.getBeneficaryBank().getBankId()));
					// dataTableRecord.setBranchCode(head.get);
					dataTableRecord.setBranchDescription(bankMap.get(head.getBank().getBankId()));
					dataTableRecord.setBankExternalId(head.getBankExternalId());
					dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
					dataTableRecord.setDynamicLabelForActivateDeactivate(setreverActiveStatus(head.getIsActive()));
					// dataTableRecord.setBankExtRefDetailSeqId(detailSeqId);
					mainDataTable.add(dataTableRecord);
					LOGGER.info(dataTableRecord);
					setBooRenderDataTablePanel(true);
					setBooRenderSavePanel(true);
				}
			}
			LOGGER.info("Exit into searchingRecordsinDataBase method");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	} 

	// pop dataTable when clicked on Branch Applicabilty in Data Table
	public void detailsBranchData(BankExternalReferenceDataTable event ) {
		try{

			//removeList.clear();
			List<BankExternalReferenceDataTable>  removeListTemp = new ArrayList<BankExternalReferenceDataTable>();
			List<BankExternalReferenceDataTable>    mainList=getMainDataTable();
			for(BankExternalReferenceDataTable  mainObj:mainList){
				if(mainObj.getDynamicLabelForActivateDeactivate().equals( Constants.REMOVE) ){
					removeListTemp.add(mainObj);
				}

			}
			setRemoveList(removeListTemp);
			setBankExtrnaRefDataTable( event);



			popupinDataTable.clear();
			//popupinDataTable = null;
			popupinDataTable = new ArrayList<BankExternalReferenceDailogDataTable>();
			LOGGER.info(event.getBankExtRefDetailSeqId());
			LOGGER.info(event.getBankExtRefSeqId());
			LOGGER.info("event.getCountryId()" + event.getCountryId());
			LOGGER.info("event.getBankId()" + event.getBankId());
			LOGGER.info("event.getBankExtRefSeqId()" + event.getBankExtRefSeqId());

			if (event.getBankExtRefSeqId() != null) {
				if (editFalg) {
					LOGGER.info(event.getBankExternalId());
					List<BankExternalReferenceDailogDataTable> dialogList = null;
					for (int i = 0; i < mainDataTable.size(); i++) {
						BankExternalReferenceDataTable element = mainDataTable.get(i);
						if (element.getIndex().equals(event.getIndex())) {
							if (element.getIndex().equals(event.getIndex())) {
								dialogList = mainDataTable.get(i).getDialogTable();
								break;
							}
						}
					}
					if (null != dialogList) {
						for (BankExternalReferenceDailogDataTable newRecods : dialogList) {
							LOGGER.info(newRecods.getBranchId());
							BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
							dialogTable.setBranchCode(newRecods.getBranchCode());
							dialogTable.setBranchDescription(newRecods.getBranchDescription());
							LOGGER.info("newRecods.getBranchCode()" + newRecods.getBranchCode());
							LOGGER.info("newRecods.getBranchDescription()" + newRecods.getBranchDescription());
							LOGGER.info(newRecods.getBranchExternalId());
							dialogTable.setBranchExternalId(newRecods.getBranchExternalId());

							//added by Rabil 0n 06 MAr 2016
							dialogTable.setFlexField1(newRecods.getFlexField1());
							dialogTable.setFlexField2(newRecods.getFlexField2());
							dialogTable.setFlexField3(newRecods.getFlexField3());

							dialogTable.setBankExtRefDetailSeqId(newRecods.getBankExtRefDetailSeqId());

							if(newRecods.getIsActive() != null){
								if (newRecods.getIsActive().equalsIgnoreCase(Constants.Yes)) {
									dialogTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
								} else if (newRecods.getIsActive().equalsIgnoreCase(Constants.D)) {
									dialogTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
								} /*else if (newRecods.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
										&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
									loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
								}*/ else {
									dialogTable.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
								}

							}else
							{

								dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
							}


							popupinDataTable.add(dialogTable);
						}
					}
				} else {
					List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(event.getBankExtRefSeqId());
					LOGGER.info(detailList.size());
					for (BankExternalReferenceDetail detail : detailList) {
						BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
						dialogTable.setBranchCode(detail.getBeneficaryBranch().getBranchCode());
						dialogTable.setBranchDescription(detail.getBeneficaryBranch().getBranchFullName());
						LOGGER.info(detail.getBeneficaryBranch().getBranchCode());
						LOGGER.info(detail.getBeneficaryBranch().getBranchFullName());
						LOGGER.info(detail.getBankBranchExternalId());
						dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
						//added by Rabil 0n 06 MAr 2016
						dialogTable.setFlexField1(detail.getFlexField1());
						dialogTable.setFlexField2(detail.getFlexField2());
						dialogTable.setFlexField3(detail.getFlexField3());

						dialogTable.setBankExtRefDetailSeqId(detail.getBankExtRefDetailSeqId());


						if(detail.getIsActive() != null){
							if (detail.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
							} else if (detail.getIsActive().equalsIgnoreCase(Constants.D)) {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
							} else if (detail.getIsActive().equalsIgnoreCase(Constants.U) && detail.getModifiedBy() == null && detail.getModifiedDate() == null && detail.getApprovedBy() == null
									&& detail.getApprovedDate() == null && detail.getRemarks() == null) {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.DELETE);
							} else {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}

						}else
						{
							dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
						}


						popupinDataTable.add(dialogTable);
					}
				}
			} else {
				LOGGER.info(event.getBankExternalId());
				List<BankExternalReferenceDailogDataTable> dialogList = null;
				for (int i = 0; i < mainDataTable.size(); i++) {
					BankExternalReferenceDataTable element = mainDataTable.get(i);
					if (element.getIndex().equals(event.getIndex())) {
						if (element.getIndex().equals(event.getIndex())) {
							dialogList = mainDataTable.get(i).getDialogTable();
							break;
						}
					}
				}
				if (null != dialogList) {
					for (BankExternalReferenceDailogDataTable newRecods : dialogList) {
						LOGGER.info(newRecods.getBranchId());
						BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
						dialogTable.setBranchCode(newRecods.getBranchCode());
						dialogTable.setBranchDescription(newRecods.getBranchDescription());
						LOGGER.info("newRecods.getBranchCode()" + newRecods.getBranchCode());
						LOGGER.info("newRecods.getBranchDescription()" + newRecods.getBranchDescription());
						LOGGER.info(newRecods.getBranchExternalId());
						dialogTable.setBranchExternalId(newRecods.getBranchExternalId());

						//added by Rabil 0n 06 MAr 2016
						dialogTable.setFlexField1(newRecods.getFlexField1());
						dialogTable.setFlexField2(newRecods.getFlexField2());
						dialogTable.setFlexField3(newRecods.getFlexField3());

						dialogTable.setBankExtRefDetailSeqId(newRecods.getBankExtRefDetailSeqId());

						if(newRecods.getIsActive() != null){
							if (newRecods.getIsActive().equalsIgnoreCase(Constants.Yes)) {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
							} else if (newRecods.getIsActive().equalsIgnoreCase(Constants.D)) {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
							} /*else if (newRecods.getIsActive().equalsIgnoreCase(Constants.U) && loyaltyParameterSettingDtobj.getModifiedBy() == null && loyaltyParameterSettingDtobj.getModifiedDate() == null && loyaltyParameterSettingDtobj.getApprovedBy() == null
									&& loyaltyParameterSettingDtobj.getApprovedDate() == null && loyaltyParameterSettingDtobj.getRemarks() == null) {
								loyaltyParameterSettingDtobj.setDynamicLabelForActivateDeactivate(Constants.DELETE);
							}*/ else {
								dialogTable.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}

						}else
						{
							dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
						}



						popupinDataTable.add(dialogTable);
					}
				}
			}
			if (!popupinDataTable.isEmpty()) {
				try {
					//bankRefDailogDataAfterRemove.clear();
					//bankRefDailogDataAfterRemove.addAll(popupinDataTable);
					setBankRefDailogDataAfterRemove(popupinDataTable);
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankexternalreferencedetailsactivatedeactvate.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}  
				//RequestContext.getCurrentInstance().execute("datatabledetails.show();");
			} else {
				RequestContext.getCurrentInstance().execute("nobranchdetails.show();");
			}
			LOGGER.info("Exit into detailsBranchData method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::detailsBranchData"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	BankExternalReferenceDataTable removebean = null;

	public void removeRecord(BankExternalReferenceDataTable bean) throws Exception{
		LOGGER.info("Entering into removeRecord method");
		LOGGER.info(bean.getDynamicLabelForActivateDeactivate());
		setApprovedBy(null);
		setApprovedDate(null);
		setRemarks(null);
		LOGGER.info(bean.getApprovedBy());
		LOGGER.info(bean.getApprovedDate());
		LOGGER.info(bean.getIsActive());
		LOGGER.info(bean.getBankExtRefSeqId());
		if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			mainDataTable.remove(bean);
			if (mainDataTable.isEmpty()) {
				setBooRenderDataTablePanel(false);
				setBooSubmitPanel(false);
			}
		} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && bean.getModifiedBy() == null && bean.getModifiedDate() == null && bean.getApprovedBy() == null && bean.getApprovedDate() == null && bean.getRemarks() == null) {
			List<BankExternalReferenceDetail> list = bankExternalReferenceservice.findDetailById(bean.getBankExtRefSeqId());
			if(list.size()==0){
				bean.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			}else{
				RequestContext.getCurrentInstance().execute("branchdetailsexisted.show();");
				return;
			}

		} if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		}

		else {
			if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				setApprovedBy(bean.getApprovedBy());
				setApprovedDate(bean.getApprovedDate());
				RequestContext.getCurrentInstance().execute("remarks.show();");
				removebean = bean;
			} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				remove(bean);
				view();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}
				removebean = null;

			}
		}
		LOGGER.info("Exit into removeRecord method");
	}

	public void clickOnUpdateRemarks() throws Exception{
		LOGGER.info(getApprovedBy());
		LOGGER.info(getApprovedDate());
		LOGGER.info(getRemarks());
		removebean.setRemarks(getRemarks());

		if(getRemarks()==null || getRemarks().trim().length()==0)
		{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
			return;
		}

		remove(removebean);
		view();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void remove(BankExternalReferenceDataTable bean) {
		try{
			LOGGER.info("Entering into remove method");
			LOGGER.info("" + bean.getBankExtRefSeqId());
			LOGGER.info("head id" + bean.getBankExtRefSeqId());
			LOGGER.info("detail id " + bean.getBankExtRefDetailSeqId());
			LOGGER.info(bean);
			LOGGER.info("bean.getDynamicLabelForActivateDeactivate()" + bean.getDynamicLabelForActivateDeactivate());
			BankExternalReferenceHead header = bankExternalReferenceservice.findHeaderById(bean.getBankExtRefSeqId());
			List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(bean.getBankExtRefSeqId());
			BankExternalReferenceHead delete = new BankExternalReferenceHead();
			delete.setApplicationCountry(header.getApplicationCountry());
			delete.setBank(header.getBank());
			delete.setBankCode(header.getBankCode());
			delete.setBankExternalId(header.getBankExternalId());
			delete.setBankExtRefSeqId(header.getBankExtRefSeqId());
			delete.setBeneficaryBank(header.getBeneficaryBank());
			delete.setBeneficaryBankCode(header.getBeneficaryBankCode());
			LOGGER.info("header.getBeneficaryBankCode()" + header.getBeneficaryBankCode());
			delete.setCreatedBy(header.getCreatedBy());
			delete.setCreatedDate(header.getCreatedDate());
			delete.setBankCountry(header.getBankCountry());
			delete.setModifiedBy(session.getUserName());
			delete.setModifiedDate(new Date());
			if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
				delete.setIsActive(Constants.U);
				delete.setApprovedBy(null);
				delete.setApprovedDate(null);
			} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
				delete.setIsActive(Constants.D);
				delete.setRemarks(bean.getRemarks());
			}
			delete.setModifiedBy(session.getUserName());
			delete.setModifiedDate(new Date());
			bankExternalReferenceservice.saveHeaderData(delete);
			/*for (BankExternalReferenceDetail list : detailList) {
				BankExternalReferenceDetail deleteObject = new BankExternalReferenceDetail();
				deleteObject.setApplicationCountry(list.getApplicationCountry());
				deleteObject.setBank(list.getBank());
				deleteObject.setBankBranchExternalId(list.getBankBranchExternalId());
				deleteObject.setBankCode(list.getBankCode());
				deleteObject.setBankExternalReferenceHead(list.getBankExternalReferenceHead());
				deleteObject.setBeneficaryBank(list.getBeneficaryBank());
				deleteObject.setBeneficaryBankCode(list.getBeneficaryBankCode());
				deleteObject.setCreatedBy(list.getCreatedBy());
				deleteObject.setCreatedDate(list.getCreatedDate());
				if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
					deleteObject.setIsActive(Constants.U);
					deleteObject.setModifiedBy(session.getUserName());
					deleteObject.setModifiedDate(new Date());
					bankRefDataTableList2.remove(bean);
				} else if (bean.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
					deleteObject.setIsActive(Constants.D);
					deleteObject.setModifiedBy(session.getUserName());
					deleteObject.setModifiedDate(new Date());
				}
				deleteObject.setCountryId(list.getCountryId());
				deleteObject.setModifiedBy(session.getUserName());
				deleteObject.setModifiedDate(new Date());
				deleteObject.setBankExtRefDetailSeqId(list.getBankExtRefDetailSeqId());
				deleteObject.setBeneficaryBranch(list.getBeneficaryBranch());
				deleteObject.setBeneficaryBranchCode(list.getBeneficaryBranchCode());
				deleteObject.setRemarks(bean.getRemarks());
				bankExternalReferenceservice.saveDetailData(deleteObject);
			}*/
			LOGGER.info("Exit into remove method");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}


	}

	private String setreverActiveStatus(String status) {
		String strStatus = null;
		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = Constants.DELETE;
		}
		if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = Constants.ACTIVATE;
		}
		if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = Constants.DEACTIVATE;
		}
		LOGGER.info("strStatus value" + strStatus);
		return strStatus;
	}

	public void removeOne() {
		bankRefDataTableList2.clear();
		setBooRenderDataTablePanel(true);
		setBooRenderSavePanel(true);
	}

	public List<BankExternalReferenceDataTable> getApproveList()throws Exception {
		if (!oneTime) {

			approveServices();
		}
		return approveList;
	}

	private void approveServices() {
		try{
			LOGGER.info("Entering into approveServices method");
			if (null != approveList) {
				approveList.clear();
			}
			oneTime = true;
			approveList = new ArrayList<BankExternalReferenceDataTable>();
			boolean fetchAll = false;
			BigDecimal bankid = null;
			BigDecimal countryid = null;
			List<BankExternalReferenceHead> hList = bankExternalReferenceservice.getAllRecordsfromHead(fetchAll, countryid, bankid);
			LOGGER.info(hList.size());
			for (BankExternalReferenceHead head : hList) {
				BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
				dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
				dataTableRecord.setBankId(head.getBank().getBankId());
				dataTableRecord.setBankCode(head.getBankCode());
				dataTableRecord.setBankName(generalService.getBankName(head.getBank().getBankId()));
				dataTableRecord.setBankExternalId(head.getBankExternalId());
				BigDecimal countryId = head.getBankCountry().getCountryId();
				dataTableRecord.setCountryId(countryId);
				dataTableRecord.setCountryName(generalService.getCountryName(new BigDecimal(1), countryId));
				dataTableRecord.setBeneficaryBankCode(head.getBeneficaryBankCode());
				dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
				dataTableRecord.setBeneficaryBankName(generalService.getBankName(head.getBeneficaryBank().getBankId()));
				dataTableRecord.setCreatedBy(head.getCreatedBy());
				dataTableRecord.setCreatedOn(head.getCreatedDate());
				dataTableRecord.setModifiedBy(head.getModifiedBy());
				dataTableRecord.setModifiedDate(head.getModifiedDate());

				dataTableRecord.setFlexField1(head.getFlexField1());
				dataTableRecord.setFlexField2(head.getFlexField2());
				dataTableRecord.setFlexField3(head.getFlexField3());



				LOGGER.info(dataTableRecord);
				approveList.add(dataTableRecord);
			}
			LOGGER.info("Exit into approveServices method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveServices"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void setApproveList(List<BankExternalReferenceDataTable> approveList) {
		this.approveList = approveList;
	}

	public boolean isBooRenderApproveDataScreen() {
		return booRenderApproveDataScreen;
	}

	public void setBooRenderApproveDataScreen(boolean booRenderApproveDataScreen) {
		this.booRenderApproveDataScreen = booRenderApproveDataScreen;
	}

	public boolean isBooRenderApproveScreen() {
		return booRenderApproveScreen;
	}

	public void setBooRenderApproveScreen(boolean booRenderApproveScreen) {
		this.booRenderApproveScreen = booRenderApproveScreen;
	}

	public void populateValues() {
		oneTime = false;
		setBooRenderApproveDataScreen(false);
		setBooRenderApproveScreen(true);
		setProceErrorUnApproveLst(null);
		setBankId(null);
		setBenfiBankId(null);
		approveList.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "BankexternalreferenceApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankexternalreferenceApproval.xhtml");
		} catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void clearDatatabledetails() {
		popupinDataTable.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		} catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}



	public void gotoServiceApproval(BankExternalReferenceDataTable event) {
		try{
			LOGGER.info("Entering into gotoServiceApproval method");
			boolean approveFlag = false;
			if(event.getModifiedBy()==null && event.getModifiedDate()==null)
			{
				if (event.getCreatedBy().equalsIgnoreCase(session.getUserName())) {
					RequestContext.getCurrentInstance().execute("notEligible.show();");
				}
				else
				{
					approveFlag = true;
				}
			}
			else if (event.getModifiedBy()!= null && event.getModifiedBy().equalsIgnoreCase(session.getUserName())) {
				RequestContext.getCurrentInstance().execute("notEligible.show();");
			} else {
				approveFlag = true;
			}
			if (approveFlag) {
				approvalBranchDetailsList.clear();
				/*approveBean = new BankExternalReferenceDataTable();*/
				setBooRenderApproveDataScreen(true);
				setBooRenderApproveScreen(false);
				setBankCountryName(event.getCountryName());
				setBankName(event.getBankName());
				setBenfiBankName(event.getBeneficaryBankName());
				setExternalId(event.getBankExternalId());
				setBankExtRefSeqId(event.getBankExtRefSeqId());
				setFlexField1( event.getFlexField1());
				setFlexField2(event.getFlexField2() );
				setFlexField3(event.getFlexField3() );
				setBankId(event.getBankId());
				setBenfiBankId(event.getBeneficaryBankId());
				//List<BankExternalReferenceDetail> popup = bankExternalReferenceservice.findDetailById(event.getBankExtRefSeqId());
				List<BankExternalReferenceDetail> popup = bankExternalReferenceservice.findDetailByIdUnApproved(event.getBankExtRefSeqId());

				List<BankExternalReferenceDailogDataTable>  branchDetailsList = new ArrayList<BankExternalReferenceDailogDataTable>();
				approveList.remove(event);
				for(BankExternalReferenceDetail bankExternalReferenceDetail:popup){
					BankExternalReferenceDailogDataTable detailDialog = new BankExternalReferenceDailogDataTable();
					detailDialog.setBankCode(bankExternalReferenceDetail.getBankCode());
					detailDialog.setBankExtRefDetailSeqId(bankExternalReferenceDetail.getBankExtRefDetailSeqId());
					detailDialog.setBankExtRefSeqId(bankExternalReferenceDetail.getBankExternalReferenceHead().getBankExtRefSeqId());
					detailDialog.setBankId(bankExternalReferenceDetail.getBank().getBankId());
					detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
					detailDialog.setBeneficaryBankId(bankExternalReferenceDetail.getBeneficaryBank().getBankId());
					detailDialog.setBeneficaryBankCode(bankExternalReferenceDetail.getBeneficaryBankCode());
					detailDialog.setBranchDescription(bankExternalReferenceDetail.getBeneficaryBranch().getBranchFullName());
					detailDialog.setBranchExternalId(bankExternalReferenceDetail.getBankBranchExternalId());
					detailDialog.setBranchId(bankExternalReferenceDetail.getBeneficaryBranch().getBankBranchId());
					detailDialog.setBranchCode(bankExternalReferenceDetail.getBeneficaryBranchCode());
					//Added by Rabil on 06 Mar 2016
					detailDialog.setFlexField1(bankExternalReferenceDetail.getFlexField1());
					detailDialog.setFlexField2(bankExternalReferenceDetail.getFlexField2());
					detailDialog.setFlexField3(bankExternalReferenceDetail.getFlexField3());
					LOGGER.info(bankExternalReferenceDetail.getBeneficaryBranchCode());
					branchDetailsList.add(detailDialog);



				}
				approvalBranchDetailsList.addAll(branchDetailsList );





				/*	approveBean = event;*/
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankexternalreferenceApproval.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			LOGGER.info("Exit into gotoServiceApproval method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::gotoServiceApproval"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void approveExternal() {
		try{
			LOGGER.info("Entering into approveExternal method");
			/* finalApproval(approveBean); */
			BankExternalReferenceDataTable event = new BankExternalReferenceDataTable();
			/*event = approveBean;*/
			LOGGER.info(event.getBankExtRefSeqId());
			LOGGER.info(event.getBankId());
			LOGGER.info(event.getBankExternalId());
			LOGGER.info("" + event.getBankExtRefSeqId());
			LOGGER.info("head id" + event.getBankExtRefSeqId());
			LOGGER.info("detail id " + event.getBankExtRefDetailSeqId());
			LOGGER.info(event);
			//LOGGER.info("bean.getDynamicLabelForActivateDeactivate()" + event.getDynamicLabelForActivateDeactivate());
			//	BankExternalReferenceHead header = bankExternalReferenceservice.findHeaderById(event.getBankExtRefSeqId());
			boolean approveFlag = true;
			if (approveFlag) {

				String approveMsg  =bankExternalReferenceservice.approveRecord(getBankExtRefSeqId(),session.getUserName());

				if(approveMsg.equalsIgnoreCase("Success")){
					//List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(getBankExtRefSeqId());
					List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailByIdUnApproved(getBankExtRefSeqId());
					proceErrorUnApproveLst=new ArrayList<BigDecimal>();
					for (BankExternalReferenceDetail list : detailList) {
						proceErrorUnApproveLst.add(list.getBankExtRefDetailSeqId());
						bankExternalReferenceservice.approveDetailsRecord(list.getBankExtRefDetailSeqId(), session.getUserName());
					}
					setProceErrorUnApproveLst(proceErrorUnApproveLst);
					//END OF PROCEDURE CALL
					HashMap<String, String>  approveRecord = new HashMap<String, String>();
					approveRecord.put("APPL_COUNTRY_ID",  session.getCountryId().toString());
					approveRecord.put("BANK_ID",  getBankId().toString());
					approveRecord.put("BENE_BANK_ID",  getBenfiBankId().toString());
					HashMap<String, String>   ouputValues =bankExternalReferenceservice.callPopulateBankExternalRef(approveRecord);
					if(ouputValues.get("P_ERROR_MESSAGE")!=null){
						setErrorMessage(ouputValues.get("P_ERROR_MESSAGE"));
						bankExternalReferenceservice.procErrorToUnApprove(getBankExtRefSeqId(),getProceErrorUnApproveLst());
						setProceErrorUnApproveLst(null);
					}
					RequestContext.getCurrentInstance().execute("approve.show();");
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				}
			}

			LOGGER.info("Exit into approveExternal method");
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::approveExternal"); 
			bankExternalReferenceservice.procErrorToUnApprove(getBankExtRefSeqId(),getProceErrorUnApproveLst());
			setProceErrorUnApproveLst(null);
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			bankExternalReferenceservice.procErrorToUnApprove(getBankExtRefSeqId(),getProceErrorUnApproveLst());
			setProceErrorUnApproveLst(null);
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void cancelApproval() {
		oneTime = false;
		populateValues();
	}

	public void clickOnOkApproval() {
		oneTime = false;
		populateValues();
	}

	public void confirmPermanentDelete()throws Exception {
		if (mainDataTable.size() > 0) {
			BankExternalReferenceDataTable dataTable = null;
			List<BankExternalReferenceDataTable> tempist = new CopyOnWriteArrayList<BankExternalReferenceDataTable>();
			tempist.clear();
			for (BankExternalReferenceDataTable dTable : mainDataTable) {
				tempist.add(dTable);
			}
			Iterator<BankExternalReferenceDataTable> iter = tempist.iterator();
			while (iter.hasNext()) {
				dataTable = iter.next();
				if (dataTable.getPermanetDeleteCheck() != null && dataTable.getPermanetDeleteCheck().equals(true)) {

					delete(dataTable);
					tempist.remove(dataTable);
				}
			}
			mainDataTable.clear();

			for (BankExternalReferenceDataTable dTable2 : tempist) {
				mainDataTable.add(dTable2);
			}
		}
	}

	public void delete(BankExternalReferenceDataTable bankExternalReferenceDataTable) {

		BankExternalReferenceHead bankExternalReferenceHead = new BankExternalReferenceHead();
		bankExternalReferenceHead.setBankExtRefSeqId(bankExternalReferenceDataTable.getBankExtRefSeqId());
		LOGGER.info(bankExternalReferenceHead.getBankExtRefSeqId());
		LOGGER.info(bankExternalReferenceHead.getBankExtRefSeqId());
		try {
			List<BankExternalReferenceDetail> list = bankExternalReferenceservice.findDetailById(bankExternalReferenceHead.getBankExtRefSeqId());

			if (list.isEmpty()) {
				bankExternalReferenceservice.delete(bankExternalReferenceHead);
			} else {
				RequestContext.getCurrentInstance().execute("branchdetailsexisted.show();");
				return;
				//bankExternalReferenceservice.delete(bankExternalReferenceHead, list);
			}
			return;
		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
	}

	public void cancelRemarks() {
		LOGGER.info("Entering into cancelRemarks method");
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		} catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}
		LOGGER.info("Exit into cancelRemarks method");
	}

	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getFlexField1() {
		return flexField1;
	}

	public void setFlexField1(String flexField1) {
		this.flexField1 = flexField1;
	}

	public String getFlexField2() {
		return flexField2;
	}

	public void setFlexField2(String flexField2) {
		this.flexField2 = flexField2;
	}

	public String getFlexField3() {
		return flexField3;
	}

	public void setFlexField3(String flexField3) {
		this.flexField3 = flexField3;
	}


	public void checkStatusType(BankExternalReferenceDailogDataTable dataTable)
	{

		if (dataTable.getDynamicLabelActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			RequestContext.getCurrentInstance().execute("pending.show();");
			return;
		} else if (dataTable.getDynamicLabelActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			bankRefDailogDataAfterRemove.clear();
			popupinDataTable.remove(dataTable);
			bankRefDailogDataAfterRemove.addAll( popupinDataTable);
			System.out.println(popupinDataTable.size());

		} else if (dataTable.getDynamicLabelActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			dataTable.setRemarksCheck(true);
			setApprovedBy(dataTable.getApprovedBy());
			setApprovedDate(dataTable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarksDetails.show();");
		} else if (dataTable.getDynamicLabelActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			dataTable.setActiveRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}  else if (dataTable.getDynamicLabelActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && dataTable.getModifiedBy() == null && dataTable.getModifiedDate() == null && dataTable.getApprovedBy() == null && dataTable.getApprovedDate() == null
				&& dataTable.getRemarks() == null) {
			dataTable.setPermentDeleteCheck(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
		} 
		/*if (popupinDataTable.size() == 0) {
			setBooRenderDataTable(false);
			setBooSaveOrExit(false);
			setBooAdd(true); 
			setBooApproval(false);
			setBooRead(false);

		}*/
	}

	// click on Active link
	public void activateRecord() throws Exception{
		if (popupinDataTable.size() > 0) {
			for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : popupinDataTable) {
				if (bankExternalReferenceDailogDataTable.getActiveRecordCheck() != null) {
					if (bankExternalReferenceDailogDataTable.getActiveRecordCheck().equals(true)) {
						conformActiveRecordToPendingForApproval(bankExternalReferenceDailogDataTable);
						//detailsBranchData( getBankExtrnaRefDataTable());
						viewBranchData(getBankExtrnaRefDataTable());
						
						List<BankExternalReferenceDataTable> lstBankExternalReferenceDataTable=getNonRemoveList();
						List<BankExternalReferenceDataTable> localMainDataTable = new CopyOnWriteArrayList<BankExternalReferenceDataTable>();
						for(BankExternalReferenceDataTable bankExternalReferenceDataTable: lstBankExternalReferenceDataTable)
						{
							if(bankExternalReferenceDataTable.getBankExtRefSeqId().compareTo(bankExternalReferenceDailogDataTable.getBankExtRefSeqId())==0)
							{
								bankExternalReferenceDataTable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
							}
							localMainDataTable.add(bankExternalReferenceDataTable);
						}
						setNonRemoveList(null);
						setNonRemoveList(localMainDataTable);
					}
				}
			}
			
			
		}
	}

	public void conformActiveRecordToPendingForApproval(BankExternalReferenceDailogDataTable dataTable) {
		try{
			bankExternalReferenceservice.deactiveRecordToPendingForApproval(dataTable.getBankExtRefDetailSeqId(), session.getUserName());

		}catch(Exception exception){
			//log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}

	// click on DeActive link
	public void clickOkRemarks() throws Exception{
		if (getRemarks() != null && !getRemarks().equals("")) {
			for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : popupinDataTable) {
				if (bankExternalReferenceDailogDataTable.getRemarksCheck() != null) {
					if (bankExternalReferenceDailogDataTable.getRemarksCheck().equals(true)) {
						bankExternalReferenceDailogDataTable.setRemarks(getRemarks());
						updateBankExternalRefDetails(bankExternalReferenceDailogDataTable);
						//detailsBranchData( getBankExtrnaRefDataTable());
						viewBranchData(getBankExtrnaRefDataTable());
						setRemarks(null);
					}
				}
			}

		} else {
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
	}

	public void updateBankExternalRefDetails(BankExternalReferenceDailogDataTable dataTable) {
		try{
			bankExternalReferenceservice.upDateActiveRecordtoDeActive(dataTable.getBankExtRefDetailSeqId(), dataTable.getRemarks(), session.getUserName());
		}catch(Exception exception){

			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;     
		}
	}
	//my code started here
	public void exitFromDetailsActivateDectivate(){
		//setIsdisable(true);
		//List<BankExternalReferenceDataTable> mainViewList=getMainDataTable();
		List<BankExternalReferenceDataTable> tempList=new ArrayList<BankExternalReferenceDataTable>();
		List<BankExternalReferenceDailogDataTable> tempBranchList	=getPopupinDataTable();

		BankExternalReferenceDataTable selectedobj=getBankExtrnaRefDataTable( );
		List<BankExternalReferenceDataTable>  removeListLocal = new ArrayList<BankExternalReferenceDataTable>();
		List<BankExternalReferenceDataTable > lstBankExternalReferenceDataTable = getRemoveList();
		if(selectedobj.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))
		{

			if(lstBankExternalReferenceDataTable!=null && lstBankExternalReferenceDataTable.size()>0){
				for(BankExternalReferenceDataTable mainDtObj:lstBankExternalReferenceDataTable){

					if(mainDtObj.getBeneficaryBankId().equals(selectedobj.getBeneficaryBankId())&& mainDtObj.getCountryId().equals( selectedobj.getCountryId())&&mainDtObj.getBankId().equals(selectedobj.getBankId())){
						mainDtObj.setDialogTable(null);
						mainDtObj.setDialogTable(getBankRefDailogDataAfterRemove());
					}
					removeListLocal.add(mainDtObj);

				}

			} 



		} else{
			removeListLocal.addAll( lstBankExternalReferenceDataTable);
		}

		List<BankExternalReferenceDataTable> addViewList = new ArrayList<BankExternalReferenceDataTable>();
		addViewList.addAll(removeListLocal);

		//List<BankExternalReferenceDataTable> mainViewList=viewAllForGettingDataBaseRecords();
		List<BankExternalReferenceDataTable> mainViewList=getNonRemoveList();
		if(mainViewList!=null && mainViewList.size()>0)
		{
			addViewList.addAll(mainViewList);
		}
		setMainDataTable(addViewList);

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
			setIsdisable(false);
		} catch (Exception exception) {
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}

	}






	public BankExternalReferenceDataTable getBankExtrnaRefDataTable() {
		return bankExtrnaRefDataTable;
	}

	public void setBankExtrnaRefDataTable(BankExternalReferenceDataTable bankExtrnaRefDataTable) {
		this.bankExtrnaRefDataTable = bankExtrnaRefDataTable;
	}

	public List<BankExternalReferenceDataTable> getRemoveList() {
		return removeList;
	}

	public void setRemoveList(List<BankExternalReferenceDataTable> removeList) {
		this.removeList = removeList;
	}


	public List<BankExternalReferenceDataTable> getNonRemoveList() {
		return nonRemoveList;
	}

	public void setNonRemoveList(List<BankExternalReferenceDataTable> nonRemoveList) {
		this.nonRemoveList = nonRemoveList;
	}

	public List<BankExternalReferenceDailogDataTable> getBankRefDailogDataAfterRemove() {
		return bankRefDailogDataAfterRemove;
	}

	public void setBankRefDailogDataAfterRemove(
			List<BankExternalReferenceDailogDataTable> bankRefDailogDataAfterRemove) {
		this.bankRefDailogDataAfterRemove = bankRefDailogDataAfterRemove;
	}

	public void confirmPermanentDeleteBranchDetails(){

		if(popupinDataTable.size()>0){
			for(BankExternalReferenceDailogDataTable bankExternalReferenceDailogDtObj: popupinDataTable){
				if(bankExternalReferenceDailogDtObj.getPermentDeleteCheck()!=null){
					if(bankExternalReferenceDailogDtObj.getPermentDeleteCheck().equals(true)){
						deleteRecordPermanently(bankExternalReferenceDailogDtObj);
						removeFromMainTable(popupinDataTable);
						popupinDataTable.remove(bankExternalReferenceDailogDtObj);
						viewBranchData(getBankExtrnaRefDataTable());

					}
				}
			}
		}
	}
	public void removeFromMainTable(List<BankExternalReferenceDailogDataTable> popupinDataTable )
	{
		List<BankExternalReferenceDataTable>    mainList=getMainDataTable();
		
		int size=mainList.size();
		int index=0;
		for(int i=0;i<size ;i++)
		{			
			BankExternalReferenceDataTable bankExternalReferenceDataTable=mainList.get(i);
			
			List<BankExternalReferenceDailogDataTable> dailogTable=bankExternalReferenceDataTable.getDialogTable();
			if(dailogTable!=null)
			{
				for(BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable :dailogTable)
				{
					for(BankExternalReferenceDailogDataTable subTabel: popupinDataTable)
					{
						if(bankExternalReferenceDailogDataTable.getBranchId().compareTo(subTabel.getBranchId())==0)
						{
							bankExternalReferenceDataTable.setDialogTable(null);
							break;
						}
					}
					
				}
			}
			
		}
		setMainDataTable(null);
		setMainDataTable(mainList);
	}
	public void deleteRecordPermanently(BankExternalReferenceDailogDataTable bankExternalReferenceDailogDtObj){
		try{
			bankExternalReferenceservice.deleteBranchRecord( bankExternalReferenceDailogDtObj.getBankExtRefDetailSeqId()  ) ;
		}catch (NullPointerException ne) {
			setErrorMessage("Method Name::deleteRecordPermanently");
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return;
		} catch (Exception exception) {
			// LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage());
			RequestContext.getCurrentInstance().execute("error.show();");
			return;
		}
	}

	public void viewBranchData(BankExternalReferenceDataTable event ) 
	{
		popupinDataTable.clear();
		setRemoveList(null);
		setNonRemoveList(null);
		List<BankExternalReferenceDataTable>  removeListTemp = new ArrayList<BankExternalReferenceDataTable>();
		List<BankExternalReferenceDataTable>  nonRemoveListTemp = new ArrayList<BankExternalReferenceDataTable>();
		List<BankExternalReferenceDataTable>    mainList=getMainDataTable();
		for(BankExternalReferenceDataTable  mainObj:mainList){
			if(mainObj.getDynamicLabelForActivateDeactivate().equals( Constants.REMOVE) ){
				removeListTemp.add(mainObj);

			}else
			{
				nonRemoveListTemp.add(mainObj);
			}
		}
		setRemoveList(removeListTemp);
		setNonRemoveList(nonRemoveListTemp);

		setBankExtrnaRefDataTable( event);
		List<BankExternalReferenceDailogDataTable> branchDetailsDataTable = new CopyOnWriteArrayList<BankExternalReferenceDailogDataTable>();

		if(event.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE))
		{
			List<BankExternalReferenceDailogDataTable> newBranchDetailsDataTable =event.getDialogTable();
			if(newBranchDetailsDataTable== null || newBranchDetailsDataTable.size()==0 )
			{
				RequestContext.getCurrentInstance().execute("nobranchdetails.show();");
				return;
			}
			for(BankExternalReferenceDailogDataTable   dialogDatObj: newBranchDetailsDataTable){
				BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
				dialogTable.setBranchCode(dialogDatObj.getBranchCode());
				dialogTable.setBranchDescription(dialogDatObj.getBranchDescription());

				dialogTable.setBranchExternalId(dialogDatObj.getBranchExternalId());
				dialogTable.setBranchId( dialogDatObj.getBranchId());
				dialogTable.setFlexField1(dialogDatObj.getFlexField1());
				dialogTable.setFlexField2(dialogDatObj.getFlexField2());
				dialogTable.setFlexField3(dialogDatObj.getFlexField3());

				dialogTable.setBankExtRefDetailSeqId(dialogDatObj.getBankExtRefDetailSeqId());
				/*if(dialogDatObj.getIsActive() != null){
					if (dialogDatObj.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
					} else if (dialogDatObj.getIsActive().equalsIgnoreCase(Constants.D)) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
					}else if (dialogDatObj.getIsActive().equalsIgnoreCase(Constants.U) && dialogDatObj.getModifiedBy() == null && dialogDatObj.getModifiedDate() == null && dialogDatObj.getApprovedBy() == null
							&& dialogDatObj.getApprovedDate() == null && dialogDatObj.getRemarks() == null) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.DELETE);
					} else {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}

				}else{
					dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
				}*/
				dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
				dialogTable.setIsActive(dialogDatObj.getIsActive());
				dialogTable.setCreatedBy(dialogDatObj.getCreatedBy());
				dialogTable.setCreatedDate(dialogDatObj.getCreatedDate());
				dialogTable.setModifiedBy(dialogDatObj.getModifiedBy());
				dialogTable.setModifiedDate(dialogDatObj.getModifiedDate());
				dialogTable.setApprovedBy(dialogDatObj.getApprovedBy());
				dialogTable.setApprovedDate(dialogDatObj.getApprovedDate());

				branchDetailsDataTable.add(dialogTable);

			}			
		}else
		{
			List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(event.getBankExtRefSeqId());
			List<BankExternalReferenceDailogDataTable> detailList1=event.getDialogTable();
			for (BankExternalReferenceDetail detail : detailList) {
				BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
				dialogTable.setBranchCode(detail.getBeneficaryBranch().getBranchCode());
				dialogTable.setBranchDescription(detail.getBeneficaryBranch().getBranchFullName());


				dialogTable.setBranchId( detail.getBeneficaryBranch().getBankBranchId());
				dialogTable.setBankExtRefSeqId(detail.getBankExternalReferenceHead().getBankExtRefSeqId());
				boolean verifyValues=false;
				for(BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable :detailList1)
				{
					if(bankExternalReferenceDailogDataTable.getBranchId().compareTo(detail.getBeneficaryBranch().getBankBranchId())==0)
					{
						dialogTable.setBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
						dialogTable.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
						dialogTable.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
						dialogTable.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());
						
						dialogTable.setIsActive(bankExternalReferenceDailogDataTable.getIsActive());
						dialogTable.setCreatedBy(bankExternalReferenceDailogDataTable.getCreatedBy());
						dialogTable.setCreatedDate(bankExternalReferenceDailogDataTable.getCreatedDate());
						dialogTable.setModifiedBy(bankExternalReferenceDailogDataTable.getModifiedBy());
						dialogTable.setModifiedDate(bankExternalReferenceDailogDataTable.getModifiedDate());
						dialogTable.setApprovedBy(bankExternalReferenceDailogDataTable.getApprovedBy());
						dialogTable.setApprovedDate(bankExternalReferenceDailogDataTable.getApprovedDate());
						verifyValues=true;
						break;
					}
				}
				if(!verifyValues)
				{
					dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
					dialogTable.setFlexField1(detail.getFlexField1());
					dialogTable.setFlexField2(detail.getFlexField2());
					dialogTable.setFlexField3(detail.getFlexField3());

					dialogTable.setIsActive(detail.getIsActive());
					dialogTable.setCreatedBy(detail.getCreatedBy());
					dialogTable.setCreatedDate(detail.getCreatedDate());
					dialogTable.setModifiedBy(detail.getModifiedBy());
					dialogTable.setModifiedDate(detail.getModifiedDate());
					dialogTable.setApprovedBy(detail.getApprovedBy());
					dialogTable.setApprovedDate(detail.getApprovedDate());

				}

				dialogTable.setBankExtRefDetailSeqId(detail.getBankExtRefDetailSeqId());


				if(detail.getIsActive() != null){
					if (detail.getIsActive().equalsIgnoreCase(Constants.Yes)) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.DEACTIVATE);
					} else if (detail.getIsActive().equalsIgnoreCase(Constants.D)) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.ACTIVATE);
					} else if (detail.getIsActive().equalsIgnoreCase(Constants.U) && detail.getModifiedBy() == null && detail.getModifiedDate() == null && detail.getApprovedBy() == null
							&& detail.getApprovedDate() == null && detail.getRemarks() == null) {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.DELETE);
					} else {
						dialogTable.setDynamicLabelActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					}

				}else
				{
					dialogTable.setDynamicLabelActivateDeactivate(Constants.REMOVE);
				}
				branchDetailsDataTable.add(dialogTable);
			}
			if(detailList== null || detailList.size()==0)
			{
				if(detailList1!=null && detailList1.size()>0)
				{
					branchDetailsDataTable.addAll(detailList1);
				}
			}
			
		}



		if (!branchDetailsDataTable.isEmpty()) {
			try {
				popupinDataTable.addAll( branchDetailsDataTable);
				//setPopupinDataTable(branchDetailsDataTable);
				setBankRefDailogDataAfterRemove(branchDetailsDataTable);
				//branchDetailsDataTable.clear();
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankexternalreferencedetailsactivatedeactvate.xhtml");
			} catch (Exception e) {
				e.printStackTrace();
			}  

		} else {
			RequestContext.getCurrentInstance().execute("nobranchdetails.show();");
		}

	}

	public List<BankExternalReferenceDataTable> getMainNewListDataTable() {
		return mainNewListDataTable;
	}

	public void setMainNewListDataTable(List<BankExternalReferenceDataTable> mainNewListDataTable) {
		this.mainNewListDataTable = mainNewListDataTable;
	}

	public void populateExistedValues(){
		List<BankExternalReferenceHead> headerList =	bankExternalReferenceservice.getDuplicateCheckList(getBankCountryId(), getBankId(), session.getCountryId(),getBenfiBankId());
		if(headerList.size()>0){
			setExternalId(headerList.get( 0).getBankExternalId() );
			setFlexField1(headerList.get( 0). getFlexField1());
			setFlexField2(headerList.get( 0).getFlexField2() );
			setFlexField3( headerList.get( 0).getFlexField3());




		}


	}

	public List<BankExternalReferenceDailogDataTable> getApprovalBranchDetailsList() {
		return approvalBranchDetailsList;
	}

	public void setApprovalBranchDetailsList(List<BankExternalReferenceDailogDataTable> approvalBranchDetailsList) {
		this.approvalBranchDetailsList = approvalBranchDetailsList;
	}


	public List<BankExternalReferenceDailogDataTable> editAllBranchData(List<BankExternalReferenceDailogDataTable> popupinDataTable )
	{
		List<BankBranch> bankbranchspecific = new ArrayList<BankBranch>();
		List<BankExternalReferenceDailogDataTable> addEditAllbankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();
		//List<BankExternalReferenceDailogDataTable> finalbankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();
		bankbranchspecific = bankExternalReferenceservice.getBankBranchListFromCountryBank(getBankCountryId(), getDatabeneFiciaryBankId());

		/*if (bankbranchspecific.size() > 0) {
			for (BankBranch bankbranch : bankbranchspecific) {
				BankExternalReferenceDailogDataTable bankExternalRefDTObj = new BankExternalReferenceDailogDataTable();
				bankExternalRefDTObj.setBranchCode(bankbranch.getBranchCode());
				bankExternalRefDTObj.setBranchDescription(bankbranch.getBranchFullName());
				bankExternalRefDTObj.setBankId(bankbranch.getExBankMaster().getBankId());
				bankExternalRefDTObj.setBeneficaryBankId(getBenfiBankId());
				bankExternalRefDTObj.setBranchId(bankbranch.getBankBranchId());
				bankExternalRefDTObj.setBankCode(bankCodeMap.get(bankbranch.getExBankMaster().getBankId()));
				bankExternalRefDTObj.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
				bankExternalRefDTObj.setBranchExternalId(null);
				bankExternalRefDTObj.setFlexField1( null);
				bankExternalRefDTObj.setFlexField2(null );
				bankExternalRefDTObj.setFlexField3(null );


				addbankRefDataTableList.add(bankExternalRefDTObj);
			}
		}*/
		if (bankbranchspecific.size() > 0)
		{
			for (BankBranch bankbranch : bankbranchspecific)
			{

				BankExternalReferenceDailogDataTable bkExteRefDTObj = new BankExternalReferenceDailogDataTable();
				bkExteRefDTObj.setBranchCode(bankbranch.getBranchCode());
				bkExteRefDTObj.setBranchDescription(bankbranch.getBranchFullName());
				bkExteRefDTObj.setBankId(bankbranch.getExBankMaster().getBankId());
				bkExteRefDTObj.setBeneficaryBankId(getBenfiBankId());
				bkExteRefDTObj.setBranchId(bankbranch.getBankBranchId());
				bkExteRefDTObj.setBankCode(bankCodeMap.get(bankbranch.getExBankMaster().getBankId()));
				bkExteRefDTObj.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
				//bkExteRefDTObj.setCreatedBy(session.getUserName());
				//bkExteRefDTObj.setCreatedDate(new Date());
				//bkExteRefDTObj.setIsActive(Constants.U);
				//bankExternalRefDTObj.setBranchExternalYesNo(branchExternalYesNo);
				boolean enterValue=false;
				for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : popupinDataTable)
				{

					if(bankbranch.getBankBranchId().compareTo(bankExternalReferenceDailogDataTable.getBranchId())==0)
					{
						bkExteRefDTObj.setBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
						bkExteRefDTObj.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
						bkExteRefDTObj.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
						bkExteRefDTObj.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());
						bkExteRefDTObj.setUpdateStatus(bankExternalReferenceDailogDataTable.getUpdateStatus());
						bkExteRefDTObj.setCreatedBy(bankExternalReferenceDailogDataTable.getCreatedBy());
						bkExteRefDTObj.setCreatedDate(bankExternalReferenceDailogDataTable.getCreatedDate());
						bkExteRefDTObj.setModifiedBy(bankExternalReferenceDailogDataTable.getModifiedBy());
						bkExteRefDTObj.setModifiedDate(bankExternalReferenceDailogDataTable.getModifiedDate());
						bkExteRefDTObj.setIsActive(bankExternalReferenceDailogDataTable.getIsActive());
						bkExteRefDTObj.setApprovedBy(bankExternalReferenceDailogDataTable.getApprovedBy());
						bkExteRefDTObj.setApprovedDate(bankExternalReferenceDailogDataTable.getApprovedDate());
						enterValue=true;
						break;
					}

				}
				if(!enterValue)
				{
					bkExteRefDTObj.setBranchExternalId(null);
					bkExteRefDTObj.setFlexField1( null);
					bkExteRefDTObj.setFlexField2(null );
					bkExteRefDTObj.setFlexField3(null );
					bkExteRefDTObj.setCreatedBy(session.getUserName());
					bkExteRefDTObj.setCreatedDate(new Date());
					bkExteRefDTObj.setIsActive(Constants.U);

				}
				addEditAllbankRefDataTableList.add(bkExteRefDTObj);
			}
		}
		return addEditAllbankRefDataTableList;
	}


	public void dailogExternalTypeAlreadyExsist()
	{
		try{
			//bankRefDataTableList = null;
			//popupinDataTable.clear();
			/*if(getDataBranchExternalYesOrNo()!=null && getDataBranchExternalYesOrNo().equalsIgnoreCase("No"))
			{}*/
			

			setDataBranchExternalYesOrNo("Yes");
			//setBankRefDataTableList(null);
			List<BankExternalReferenceDailogDataTable> dailogBankRefDataTableList=getBankRefDataTableList();

			List<BankExternalReferenceDailogDataTable> addDailogbankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();
			LOGGER.info(getBranchExternalId());

			//int exterId = Integer.valueOf(getDataBranchExternalYesOrNo()==null?"0":getDataBranchExternalYesOrNo());
			List<BankBranch> bankbranchspecific = new ArrayList<BankBranch>();
			bankbranchspecific = bankExternalReferenceservice.getBankBranchListFromCountryBank(getBankCountryId(), getDatabeneFiciaryBankId());
			if (bankbranchspecific!=null && bankbranchspecific.size()>0) {
				for (BankBranch bankbranch : bankbranchspecific)
				{

					BankExternalReferenceDailogDataTable bankExrnlRefDTObj = new BankExternalReferenceDailogDataTable();
					bankExrnlRefDTObj.setBranchCode(bankbranch.getBranchCode());
					bankExrnlRefDTObj.setBranchDescription(bankbranch.getBranchFullName());
					bankExrnlRefDTObj.setBankId(bankbranch.getExBankMaster().getBankId());
					bankExrnlRefDTObj.setBeneficaryBankId(getBenfiBankId());
					bankExrnlRefDTObj.setBranchId(bankbranch.getBankBranchId());
					bankExrnlRefDTObj.setBankCode(bankCodeMap.get(bankbranch.getExBankMaster().getBankId()));
					bankExrnlRefDTObj.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
					boolean enterValue=false;
					if(dailogBankRefDataTableList!=null && dailogBankRefDataTableList.size()>0)
					{
						for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : dailogBankRefDataTableList)
						{

							if(bankbranch.getBankBranchId().compareTo(bankExternalReferenceDailogDataTable.getBranchId())==0)
							{
								bankExrnlRefDTObj.setBankExtRefDetailSeqId(bankExternalReferenceDailogDataTable.getBankExtRefDetailSeqId());
								bankExrnlRefDTObj.setBankExtRefSeqId(bankExternalReferenceDailogDataTable.getBankExtRefSeqId());
								bankExrnlRefDTObj.setBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
								bankExrnlRefDTObj.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
								bankExrnlRefDTObj.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
								bankExrnlRefDTObj.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());
								bankExrnlRefDTObj.setUpdateStatus(bankExternalReferenceDailogDataTable.getUpdateStatus());
								bankExrnlRefDTObj.setCreatedBy(bankExternalReferenceDailogDataTable.getCreatedBy());
								bankExrnlRefDTObj.setCreatedDate(bankExternalReferenceDailogDataTable.getCreatedDate());
								bankExrnlRefDTObj.setModifiedBy(bankExternalReferenceDailogDataTable.getModifiedBy());
								bankExrnlRefDTObj.setModifiedDate(bankExternalReferenceDailogDataTable.getModifiedDate());
								bankExrnlRefDTObj.setApprovedBy(bankExternalReferenceDailogDataTable.getApprovedBy());
								bankExrnlRefDTObj.setApprovedDate(bankExternalReferenceDailogDataTable.getApprovedDate());
								bankExrnlRefDTObj.setIsActive(bankExternalReferenceDailogDataTable.getIsActive());
								enterValue=true;
								break;
							}

						}
					}

					if(!enterValue)
					{
						bankExrnlRefDTObj.setBranchExternalId(null);
						bankExrnlRefDTObj.setFlexField1( null);
						bankExrnlRefDTObj.setFlexField2(null );
						bankExrnlRefDTObj.setFlexField3(null );

					}
					bankExrnlRefDTObj.setBranchExternalYesNo(getDataBranchExternalYesOrNo());
					addDailogbankRefDataTableList.add(bankExrnlRefDTObj);
				}
				setBankRefDataTableList(null);
				setBankRefDataTableList(addDailogbankRefDataTableList);
				//RequestContext.getCurrentInstance().execute("alreadyExist.show();");
				//popupinDataTable.clear();

			}else
			{
				setDataBranchExternalYesOrNo("No");
				setErrorMessage("Branches are not existed"); 
				RequestContext.getCurrentInstance().execute("error.show();");
			}
		

		}catch(NullPointerException ne){
			LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			setErrorMessage("Method Name::saveDataTableRecods"); 
			RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			return; 
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("error.show();");
			return;       
		}

	}

	public List<BankExternalReferenceDailogDataTable> editRecordExistedBranches(List<BankExternalReferenceDetail> detailList,List<BankExternalReferenceDailogDataTable> existAddbankRefDataTableList)
	{
		List<BankExternalReferenceDailogDataTable> dbBankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();

		for(BankExternalReferenceDetail detail :detailList)
		{
			BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
			dialogTable.setBranchCode(detail.getBeneficaryBranchCode());
			dialogTable.setBranchDescription(detail.getBeneficaryBranch().getBranchFullName());
			dialogTable.setBankExtRefDetailSeqId(detail.getBankExtRefDetailSeqId());
			dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
			setBankCountryId(detail.getCountryId().getCountryId());
			dialogTable.setCountryId(detail.getCountryId().getCountryId());
			dialogTable.setBankExtRefDetailSeqId(detail.getBankExtRefDetailSeqId());
			//dialogTable.setBankExtRefSeqId(detail.getBankBranchExternalId());
			dialogTable.setBranchId(detail.getBeneficaryBranch().getBankBranchId());
			dialogTable.setIsActive(detail.getIsActive());
			dialogTable.setModifiedBy(detail.getModifiedBy());
			dialogTable.setModifiedDate(detail.getModifiedDate());
			dialogTable.setApprovedBy(detail.getApprovedBy());
			dialogTable.setApprovedDate(detail.getApprovedDate());
			dialogTable.setRemarks(detail.getRemarks());
			dialogTable.setCreatedBy(detail.getCreatedBy());
			dialogTable.setCreatedDate(detail.getCreatedDate());
			dialogTable.setBankExtRefSeqId(detail.getBankExternalReferenceHead().getBankExtRefSeqId());
			//if(detail.getIsActive()!=null && detail.getIsActive().equalsIgnoreCase(Constants.Yes))

			/*//Added by Rabil 06/03/2016
			dialogTable.setFlexField1(detail.getFlexField1());
			dialogTable.setFlexField2(detail.getFlexField2());
			dialogTable.setFlexField3(detail.getFlexField3());*/
			boolean enterValue=false;
			for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : existAddbankRefDataTableList)
			{

				if(dialogTable.getBranchId().compareTo(bankExternalReferenceDailogDataTable.getBranchId())==0)
				{
					dialogTable.setBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
					dialogTable.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
					dialogTable.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
					dialogTable.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());
					dialogTable.setUpdateStatus(bankExternalReferenceDailogDataTable.getUpdateStatus());
					enterValue=true;
					break;
				}

			}
			if(!enterValue)
			{
				dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
				dialogTable.setFlexField1(detail.getFlexField1());
				dialogTable.setFlexField2(detail.getFlexField2());
				dialogTable.setFlexField3(detail.getFlexField3());
				//dialogTable.setUpdateStatus(detail.getUpdateStatus());

			}

			dialogTable.setBranchExternalYesNo(getBranchExternalId());
			dbBankRefDataTableList.add(dialogTable);
		}

		List<BankBranch> bankbranchspecific = new ArrayList<BankBranch>();
		List<BankExternalReferenceDailogDataTable> addbankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();
		//List<BankExternalReferenceDailogDataTable> finalbankRefDataTableList = new ArrayList<BankExternalReferenceDailogDataTable>();
		bankbranchspecific = bankExternalReferenceservice.getBankBranchListFromCountryBank(getBankCountryId(), getDatabeneFiciaryBankId());
		if (bankbranchspecific.size() > 0)
		{
			for (BankBranch bankbranch : bankbranchspecific)
			{

				BankExternalReferenceDailogDataTable bankExternalRefDTObj = new BankExternalReferenceDailogDataTable();
				bankExternalRefDTObj.setBranchCode(bankbranch.getBranchCode());
				bankExternalRefDTObj.setBranchDescription(bankbranch.getBranchFullName());
				bankExternalRefDTObj.setBankId(bankbranch.getExBankMaster().getBankId());
				bankExternalRefDTObj.setBeneficaryBankId(getBenfiBankId());
				bankExternalRefDTObj.setBranchId(bankbranch.getBankBranchId());
				bankExternalRefDTObj.setBankCode(bankCodeMap.get(bankbranch.getExBankMaster().getBankId()));
				bankExternalRefDTObj.setBeneficaryBankCode(beneficaryBankCodeMap.get(getBenfiBankId()));
				//bankExternalRefDTObj.setBranchExternalYesNo(branchExternalYesNo);
				boolean enterValue=false;
				for (BankExternalReferenceDailogDataTable bankExternalReferenceDailogDataTable : existAddbankRefDataTableList)
				{

					if(bankbranch.getBankBranchId().compareTo(bankExternalReferenceDailogDataTable.getBranchId())==0)
					{
						bankExternalRefDTObj.setBankExtRefDetailSeqId(bankExternalReferenceDailogDataTable.getBankExtRefDetailSeqId());
						bankExternalRefDTObj.setBranchExternalId(bankExternalReferenceDailogDataTable.getBranchExternalId());
						bankExternalRefDTObj.setFlexField1(bankExternalReferenceDailogDataTable.getFlexField1());
						bankExternalRefDTObj.setFlexField2(bankExternalReferenceDailogDataTable.getFlexField2());
						bankExternalRefDTObj.setFlexField3(bankExternalReferenceDailogDataTable.getFlexField3());
						bankExternalRefDTObj.setUpdateStatus(bankExternalReferenceDailogDataTable.getUpdateStatus());
						
						bankExternalRefDTObj.setIsActive(bankExternalReferenceDailogDataTable.getIsActive());
						bankExternalRefDTObj.setModifiedBy(bankExternalReferenceDailogDataTable.getModifiedBy());
						bankExternalRefDTObj.setModifiedDate(bankExternalReferenceDailogDataTable.getModifiedDate());
						bankExternalRefDTObj.setApprovedBy(bankExternalReferenceDailogDataTable.getApprovedBy());
						bankExternalRefDTObj.setApprovedDate(bankExternalReferenceDailogDataTable.getApprovedDate());
						bankExternalRefDTObj.setRemarks(bankExternalReferenceDailogDataTable.getRemarks());
						bankExternalRefDTObj.setCreatedBy(bankExternalReferenceDailogDataTable.getCreatedBy());
						bankExternalRefDTObj.setCreatedDate(bankExternalReferenceDailogDataTable.getCreatedDate());
						
						enterValue=true;
						break;
					}

				}
				if(!enterValue)
				{
					bankExternalRefDTObj.setBranchExternalId(null);
					bankExternalRefDTObj.setFlexField1( null);
					bankExternalRefDTObj.setFlexField2(null );
					bankExternalRefDTObj.setFlexField3(null );

				}
				addbankRefDataTableList.add(bankExternalRefDTObj);
			}
		}

		return addbankRefDataTableList;
	}

	public void updateStatus(BankExternalReferenceDailogDataTable bankExtrnalisObj)
	{
		bankExtrnalisObj.setUpdateStatus(true);
		/*if(bankExtrnalisObj.getIsActive()!=null)
		{
			bankExtrnalisObj.setModifiedBy(session.getUserName());
			bankExtrnalisObj.setModifiedDate(new Date());
			bankExtrnalisObj.setIsActive(Constants.U);
		}else
		{
			bankExtrnalisObj.setIsActive(Constants.U);
			bankExtrnalisObj.setCreatedBy(session.getUserName());
			bankExtrnalisObj.setCreatedDate(new Date());
		}*/
	}
	
	private void editClearValues()
	{
		setBankExtRefSeqId(null);
		setCreatedBy(null);
		setCreatedOn(null);
		setApprovedBy(null);
		setApprovedDate(null);
		setModifiedBy(null);
		setModifiedDate(null);
		setDynamicLabelForActivateDeactivate(null);
	}
	public void clearDailogDataTable()
	{
		setBankRefDataTableList(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/Bankexternalreference.xhtml");
		}catch(Exception exception){
			LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrorMessage(exception.getMessage()); 
		}	
	}
	
	
	private List<BigDecimal> proceErrorUnApproveLst;
	public List<BigDecimal> getProceErrorUnApproveLst() {
		return proceErrorUnApproveLst;
	}

	public void setProceErrorUnApproveLst(List<BigDecimal> proceErrorUnApproveLst) {
		this.proceErrorUnApproveLst = proceErrorUnApproveLst;
	}
	public void redirctToNoRec()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/bankexternalreferencedetailsactivatedeactvate.xhtml");
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
}