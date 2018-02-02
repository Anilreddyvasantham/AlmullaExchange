package com.amg.exchange.remittance.bean;

import java.io.IOException;
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

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleAddData;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleFlexFieldView;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.model.FlexFiledView;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAmiecService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@SuppressWarnings("unused")
@Component("additionalBankRule")
@Scope("session")
public class AdditionalBankRuleBean<T> implements Serializable {

	private static final Logger log = Logger.getLogger(AdditionalBankRuleBean.class);
	@Autowired
	IGeneralService<T> generalService;

	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;

	@Autowired
	IAdditionalBankRuleAmiecService additionalBankRuleAmiecService;

	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;
	
	@Autowired
	IBankIndicatorService bankIndicatorService;

	private static final long serialVersionUID = 1L;
	SessionStateManage sessionStateManage = new SessionStateManage();

	private BigDecimal additionalBankRuleId = null;
	private String fieldName;
	private String flexField;
	private BigDecimal orderNo;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String isActive;
	private BigDecimal countryId;
	private int count=0;

	private BigDecimal additionalBankRuleDetailId;
	private String amiecCode;
	private String amiecDescription;
	private BigDecimal bankId;
	private String additionalData;
	private String additionalDescription;
	private Boolean editClikedBankMapRule=false;
	private Boolean editClikedAlmullaCode=false;
	private Boolean editClikedBankRule=false;
	private BigDecimal noOfSaveCount;

	private boolean additionalBankRuleMapRendered = false;
	private boolean additionalBankRuleAmiecRendered = false;
	private boolean additionalBankRuleAddDataRendered = false;
	private boolean saveAdditionalBankRule1 = false;
	private boolean saveAdditionalBankRule2 = false;
	private boolean saveAdditionalBankRule3 = false;
	
	private Boolean disableEditBankRuleMap=false;
	private Boolean disableEditBankWithAlmullaCode=false;
	private Boolean disableEditBankRule=false;
	
	private String dupRecord;
	private Boolean booDBFlexField = false;
	private Boolean booNewFlexField = true;
	private String dBflexField;
	private Boolean editButton;
	private BigDecimal additionabankrulemapPK;
	private Boolean deleteIsActive = false;
	private Boolean booRenderSaveOne = false;
	private Boolean booRenderSaveExit = false;
	private Boolean booRenderSave = false;

	private Boolean renderCountryIdForApprove = false;
	private Boolean renderCountryId = false;
	private Boolean renderFlexField = false;
	private Boolean renderFlexFieldForApprove = false;
	private Boolean renderBankId = false;
	private Boolean renderBankIdForApprove = false;
	private Boolean renderBankCode = false;
	private Boolean renderBankCodeForApprove = false;
	private Boolean renderBankDesc = false;
	private Boolean renderBankDescForApprove = false;

	private Boolean renderAddButtonPanel = false;
	private Boolean renderApproveCancelButtonPanel = false;

	private String createdByForApprove;
	private BigDecimal addtionBankRulePK;
	private String bankName;
	private String countryName;
	private String flexFieldName;

	private Boolean renderCountryIdBankMapForApprove = false;
	private Boolean renderCountryIdBankMap = false;
	private String countryNameForBankMap;
	private Boolean renderFlexFieldBankMapForApprove = false;
	private Boolean renderFlexFieldBankMap = false;
	private String flexFieldNameForBankMap;
	private String flexFieldForBankMap;
	private Boolean renderFlexNameBankMapForApprove = false;
	private Boolean renderFlexNameBankMap = false;
	private Boolean renderOrderNoBankMap = false;
	private Boolean renderOrderNoBankMapForApprove = false;
	private String orderNoForBankMap;
	private Boolean renderApproveCancelButtonPanelForBankMap = false;
	private Boolean renderAddButtonPanelForBankMap = false;

	private BigDecimal addtionalBankRuleMapPk;
	private String createdByForBankRuleMap;
	private String approveBy;
	private Date approveDate;
	private Date activateDate;
	private String activateBy;

	private Boolean renderCountryIdAlmulla = false;
	private Boolean renderCountryIdForApproveAlmulla = false;
	private String countryNameAlmulla;
	private Boolean renderFlexFieldForApproveAlmulla = false;
	private Boolean renderFlexFieldAlmulla = false;
	private String flexFieldNameAlmulla;
	private String almullaCodeAlmulla;
	private Boolean renderAlmullaCodeForApproveAlmulla = false;
	private Boolean renderAlmullaCodeAlmulla = false;
	private String almullaDescAlmulla;
	private Boolean renderAlmullaDescAlmulla = false;
	private Boolean renderAlmullaDescForApproveAlmulla = false;

	private Boolean renderApproveCancelButtonPanelAlmulla = false;
	private String createdByAlmulla;
	private BigDecimal almullaCodePK;

	private Boolean disableSubmitButtonForBankMap = false;
	private String dynamicLabelForActivateDeactivateForBankMap;
	private String remarksForBankMap;

	private Boolean disableSubmitButtonForAlmullaCode;
	private String dynamicLabelForActivateDeactivateForAlmullaCode;
	private String isActiveForAlmullaCode;
	private String createdByForAlmullaCode;
	private Date createdDateForAlmullaCode;
	private BigDecimal additionalBankRulaAlmullaCodePK;
	private String approveByForAlmullaCode;
	private Date approveDateForAlmullaCode;
	private String modifiedByForAlmullaCode;
	private Date modifiedDateForAlmullaCode;
	private BigDecimal additionalBankFieldId;

	private String remarksForAlmullaCode;

	private Boolean disableSubmitButtonForBankRule;
	private String dynamicLabelForActivateDeactivateForBankRule;
	private String isActiveForBankRule;
	private String createdByForBankRule;
	private Date createdDateForBankRule;
	private BigDecimal additionalBankRulePK;

	private String approveByForBankRule;
	private Date approveDateForBankRule;
	private String modifiedByForBankRule;
	private Date modifiedDateForBankRule;

	private BigDecimal additionalBankFieldIdForBankRule;
	private String remarksForBankRule;
	private String activeStatusInWord;

	int i = 0;

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<AdditionalBankRuleMap> flexFieldList = new ArrayList<AdditionalBankRuleMap>();
	private List<BankMaster> bankList = new ArrayList<BankMaster>();

	private List<AdditionalBankRuleDataTable> additionalBankRuleDataList1 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataList2 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataList3 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataNewList1 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataNewList2 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleDataTable> additionalBankRuleDataNewList3 = new CopyOnWriteArrayList<AdditionalBankRuleDataTable>();
	private List<AdditionalBankRuleMap> lstFlexFiledDB = new ArrayList<AdditionalBankRuleMap>();
	private List<AdditionalBankRuleMap> lstAllRecordsDB = new ArrayList<AdditionalBankRuleMap>();
	private List<AdditionalBankRuleAmiec> lstAllDBcountryFlex = new ArrayList<AdditionalBankRuleAmiec>();
	private List<AdditionalBankRuleAddData> lstAllDBcountryFlexBank = new ArrayList<AdditionalBankRuleAddData>();
	private List<AdditionalBankRuleMap> additionalBankRuleMaps = new ArrayList<AdditionalBankRuleMap>();
	
	
	
	
	private List<FlexFiledView> flexFiledViewList = new ArrayList<FlexFiledView>();

	private List<AdditionalBankRuleMap> addingtoDataTable = new ArrayList<AdditionalBankRuleMap>();
	Map<BigDecimal, String> mapCountryList = new HashMap<BigDecimal, String>();
	Map<BigDecimal, String> mapBankList = new HashMap<BigDecimal, String>();
	
	private Boolean booEditableRecord;
	private String errorMessage;

	public List<FlexFiledView> getFlexFiledViewList() {
		return flexFiledViewList;
	}

	public void setFlexFiledViewList(List<FlexFiledView> flexFiledViewList) {
		this.flexFiledViewList = flexFiledViewList;
	}

	public Boolean getBooEditableRecord() {
		return booEditableRecord;
	}

	public void setBooEditableRecord(Boolean booEditableRecord) {
		this.booEditableRecord = booEditableRecord;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	// Country List
	public List<CountryMasterDesc> getCountryList() {
		try{
		List<CountryMasterDesc> countryList = new ArrayList<>();
		countryList.addAll(generalService.getCountryList(sessionStateManage.getLanguageId()));
		for (CountryMasterDesc countryMaster : countryList) {
			mapCountryList.put(countryMaster.getCountryMasterId(),countryMaster.getCountryName());
		}
		return countryList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getCountryList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return countryList;
		}
		
		
	}

	// Bank List
	public List<BankApplicability> getBankList() {

		List<BankApplicability> bankList = new ArrayList<>();
		try{
			BigDecimal pkCorresBankInd = null;
			BigDecimal pkServProBankInd = null;

			List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);

			List<BankIndicator> servProBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);

			if(corrBankIndlist.size() != 0){
				pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
			}

			if(servProBankIndlist.size() != 0){
				pkServProBankInd = servProBankIndlist.get(0).getBankIndicatorId();
			}

			if(pkCorresBankInd != null){
				bankList.addAll(generalService.getBankListbyIndicator(getCountryId(), pkCorresBankInd));
				for (BankApplicability bankMaster : bankList) {
					mapBankList.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankFullName());
				}
			}
			
			if(pkServProBankInd != null){
				bankList.addAll(generalService.getBankListbyIndicator(getCountryId(), pkServProBankInd));
				for (BankApplicability bankMaster : bankList) {
					mapBankList.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankFullName());
				}
			}
			
			return bankList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getBankList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");  
			return bankList;
		}


	}

	// Flex Field List
	public List<AdditionalBankRuleMap> getFlexFieldList() {
		List<AdditionalBankRuleMap> flexFieldList = new ArrayList<>();
		try{
		flexFieldList.addAll(additionalBankRuleMapService.getAllFlexField());
		return flexFieldList;
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getFlexFieldList "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");  
			return flexFieldList;
		}
		
		
	}

	public void setFlexFieldList(List<AdditionalBankRuleMap> flexFieldList) {
		this.flexFieldList = flexFieldList;
	}

	// Add to Additional Bank Rule Data table
	public void addAdditionalBankRule1DataTable() {
		setDisableSubmitButtonForBankMap(false);
		setDisableEditBankRuleMap(false);
		try{
		List<AdditionalBankRuleMap> bankRuleMapList= additionalBankRuleMapService.duplicateCheckInDBBankMap(getCountryId(),getdBflexField(),getFlexField(),getFieldName(),getOrderNo());
		if(bankRuleMapList.size()>0 && getEditClikedBankMapRule().equals(false)){
			RequestContext.getCurrentInstance().execute("recordExistInDB.show();");
			clearAllRule1();
			return;
		}else{
		duplicateCheckInDataTabelBankMap();
		}
		}catch(NullPointerException ne){
			  log.info("Method Name::addAdditionalBankRule1DataTable"+ne.getMessage());
			  setErrorMessage("Method Name::addAdditionalBankRule1DataTable"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::addAdditionalBankRule1DataTable "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	
	public void duplicateCheckInDataTabelBankMap(){			
		if (additionalBankRuleDataList1.size() != 0) {
		i = 0;
		for (AdditionalBankRuleDataTable dtablefinal : additionalBankRuleDataList1) {
			if (dtablefinal.getFlexField().equalsIgnoreCase(getFlexField())) {
				i = 1;
				RequestContext.getCurrentInstance().execute("recordExists.show();");
				clearAllRule1();
				break;
			} else {
				i = 0;
			}
		}
	} else {
		i = 0;
	}
	if (i == 0) {
		finaladdtoSaveUpdate();
	}
	}
	

	public Boolean getEditClikedBankRule() {
		return editClikedBankRule;
	}

	public void setEditClikedBankRule(Boolean editClikedBankRule) {
		this.editClikedBankRule = editClikedBankRule;
	}

	public BigDecimal getNoOfSaveCount() {
		return noOfSaveCount;
	}

	public void setNoOfSaveCount(BigDecimal noOfSaveCount) {
		this.noOfSaveCount = noOfSaveCount;
	}

	public Boolean getEditClikedBankMapRule() {
		return editClikedBankMapRule;
	}

	public void setEditClikedBankMapRule(Boolean editClikedBankMapRule) {
		this.editClikedBankMapRule = editClikedBankMapRule;
	}

	public Boolean getDisableEditBankRule() {
		return disableEditBankRule;
	}

	public void setDisableEditBankRule(Boolean disableEditBankRule) {
		this.disableEditBankRule = disableEditBankRule;
	}

	public Boolean getDisableEditBankWithAlmullaCode() {
		return disableEditBankWithAlmullaCode;
	}

	public void setDisableEditBankWithAlmullaCode(
			Boolean disableEditBankWithAlmullaCode) {
		this.disableEditBankWithAlmullaCode = disableEditBankWithAlmullaCode;
	}

	public Boolean getEditClikedAlmullaCode() {
		return editClikedAlmullaCode;
	}

	public void setEditClikedAlmullaCode(Boolean editClikedAlmullaCode) {
		this.editClikedAlmullaCode = editClikedAlmullaCode;
	}

	public Boolean getDisableEditBankRuleMap() {
		return disableEditBankRuleMap;
	}

	public void setDisableEditBankRuleMap(Boolean disableEditBankRuleMap) {
		this.disableEditBankRuleMap = disableEditBankRuleMap;
	}

	// Final Save
	public void finaladdtoSaveUpdate() {
		
		try{
		AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
		additionalBankRuleDataTable.setAdditionalBankRuleMapPk(getAdditionabankrulemapPK());
		additionalBankRuleDataTable.setCountryId(getCountryId());
		additionalBankRuleDataTable.setCountryName(generalService.getCountryName(getCountryId()));
		additionalBankRuleDataTable.setFieldName(getFieldName());
		additionalBankRuleDataTable.setFlexField(getFlexField());
		additionalBankRuleDataTable.setOrderNo(getOrderNo());
	
		
		if (getAdditionabankrulemapPK() != null) {
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(null);
			additionalBankRuleDataTable.setIsActive(Constants.U);
			additionalBankRuleDataTable.setCreatedBy(getCreatedBy());
			additionalBankRuleDataTable.setCreatedDate(getCreatedDate());
			additionalBankRuleDataTable.setApproveBy(null);
			additionalBankRuleDataTable.setApproveDate(null);
			additionalBankRuleDataTable.setModifiedBy(sessionStateManage.getUserName());
			additionalBankRuleDataTable.setModifiedDate(new Date());
			additionalBankRuleDataTable.setRemarks(null);
			additionalBankRuleDataTable.setEditClikedBankMapRule(getEditClikedBankMapRule());
		} else {
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.REMOVE);
			additionalBankRuleDataTable.setIsActive(Constants.U);
			additionalBankRuleDataTable.setCreatedBy(sessionStateManage.getUserName());
			additionalBankRuleDataTable.setEditClikedBankMapRule(true);
			additionalBankRuleDataTable.setCreatedDate(new Date());
		}
		additionalBankRuleDataList1.add(additionalBankRuleDataTable);
		if (getAdditionabankrulemapPK() == null){
			additionalBankRuleDataNewList1.add(additionalBankRuleDataTable);
		}
		setAdditionalBankRuleMapRendered(true);
		setSaveAdditionalBankRule1(true);
		clearAllRule1();
		}catch(NullPointerException ne){
			  log.info("Method Name::finaladdtoSaveUpdate"+ne.getMessage());
			  setErrorMessage("Method Name::finaladdtoSaveUpdate"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::finaladdtoSaveUpdate "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void checkDTForAMIECMap() {
		setDisableEditBankWithAlmullaCode(false);
		setDisableSubmitButtonForAlmullaCode(false);
		setBooclearfanel(false);
		if(getBooEditableRecord()==null || getBooEditableRecord()!=null && getBooEditableRecord()==false) {
		List<AdditionalBankRuleAmiec>  bankRuleAlmullaCodeList= additionalBankRuleMapService.duplicateCheckInDBBankAlmullaCode(getCountryId(),getFlexField(),getAmiecCode(),getAmiecDescription());
		if(bankRuleAlmullaCodeList.size()>0 && getEditClikedAlmullaCode().equals(false)){
			RequestContext.getCurrentInstance().execute("recordExistInDB.show();");
			clearAllRule2();
			return;
		}else{
			duplicateCheckInDataTabelAlmullaCode();
		}
		}
		else
		{
			duplicateCheckInDataTabelAlmullaCode();
		}
		
	}
	
	
	public void duplicateCheckInDataTabelAlmullaCode(){if (additionalBankRuleDataList2.size() != 0) {
		i = 0;
		for (AdditionalBankRuleDataTable additionalBankRuleDT : additionalBankRuleDataList2) {
			if (additionalBankRuleDT.getAmiecCode().equalsIgnoreCase(getAmiecCode())) {
				i = 1;
				setAmiecCode(null);
				setAmiecDescription(null);
				RequestContext.getCurrentInstance().execute("amieExists.show();");
				clearAllRule2();
				return;
			} else {
				i = 0;
			}
		}
	} else {
		i = 0;
	}

	if (i == 0) {
		addAdditionalBankRule2DataTable();
	}
}

	// Add to Additional Bank Data table Rule 2
	public void addAdditionalBankRule2DataTable() {

		AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
		additionalBankRuleDataTable.setAdditionalBankRulaAlmullaCodePK(getAdditionalBankRulaAlmullaCodePK());
		additionalBankRuleDataTable.setAdditionalBankFieldId(getAdditionalBankFieldId());
		additionalBankRuleDataTable.setCountryId(getCountryId());
		additionalBankRuleDataTable.setCountryName(generalService.getCountryName(getCountryId()));
		additionalBankRuleDataTable.setFlexField(getFlexField());
		additionalBankRuleDataTable.setAmiecCode(getAmiecCode());
		additionalBankRuleDataTable.setAmiecDescription(getAmiecDescription());
		Boolean activecheck = false;
		if (getAdditionalBankRulaAlmullaCodePK() != null) {
			List<AdditionalBankRuleAmiec> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankAlmullaCodeRecordsForView(getCountryId(),getFlexField());
			if(additionalBankAlmullaCodeList.size()>0){
			for(AdditionalBankRuleAmiec additionalBank:additionalBankAlmullaCodeList){
				if(getCountryId() != null && getCountryId().intValue()== additionalBank.getCountryId().getCountryId().intValue() && getFlexField()!=null && getFlexField().equalsIgnoreCase(additionalBank.getFlexField()) && 
					 getAmiecCode()!=null &&	getAmiecCode().equalsIgnoreCase(additionalBank.getAmiecCode()) && getAmiecDescription()!=null && getAmiecDescription().equalsIgnoreCase(additionalBank.getAmiecDescription())){
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(getDynamicLabelForActivateDeactivateForAlmullaCode());
			additionalBankRuleDataTable.setIsActiveForAlmullaCode(getIsActiveForAlmullaCode());
			additionalBankRuleDataTable.setCreatedByForAlmullaCode(getCreatedByForAlmullaCode());
			additionalBankRuleDataTable.setCreatedDateForAlmullaCode(getCreatedDateForAlmullaCode());
			additionalBankRuleDataTable.setApproveByForAlmullaCode(getApproveByForAlmullaCode());
			additionalBankRuleDataTable.setApproveDateForAlmullaCode(getApproveDateForAlmullaCode());
			additionalBankRuleDataTable.setModifiedByForAlmullaCode(getModifiedByForAlmullaCode());
			additionalBankRuleDataTable.setModifiedDateForAlmullaCode(getModifiedDateForAlmullaCode());
			additionalBankRuleDataTable.setRemarksForAlmullaCode(getRemarksForAlmullaCode());
			activecheck=true;
				}else{
					//additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(null);
					additionalBankRuleDataTable.setIsActiveForAlmullaCode(Constants.U);
					additionalBankRuleDataTable.setCreatedByForAlmullaCode(getCreatedByForAlmullaCode());
					additionalBankRuleDataTable.setCreatedDateForAlmullaCode(getCreatedDateForAlmullaCode());
					additionalBankRuleDataTable.setApproveByForAlmullaCode(null);
					additionalBankRuleDataTable.setApproveDateForAlmullaCode(null);
					additionalBankRuleDataTable.setModifiedByForAlmullaCode(sessionStateManage.getUserName());
					additionalBankRuleDataTable.setModifiedDateForAlmullaCode(new Date());
					additionalBankRuleDataTable.setRemarksForAlmullaCode(null);
					additionalBankRuleDataTable.setEditClikedAlmullaCode(getEditClikedAlmullaCode());
				}
				
				if(activecheck.equals(true)){
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(getDynamicLabelForActivateDeactivateForAlmullaCode());
				}else{
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.PENDING_FOR_APPROVE);
				}
			}
			}
		
	
			
		} else {
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.REMOVE);
			additionalBankRuleDataTable.setIsActiveForAlmullaCode( Constants.U);
			additionalBankRuleDataTable.setCreatedByForAlmullaCode(sessionStateManage.getUserName());
			additionalBankRuleDataTable.setCreatedDateForAlmullaCode(new Date());
			additionalBankRuleDataTable.setEditClikedAlmullaCode(true);
		}

		additionalBankRuleDataList2.add(additionalBankRuleDataTable);
		if (getAdditionalBankRulaAlmullaCodePK() == null) {
			additionalBankRuleDataNewList2.add(additionalBankRuleDataTable);
		}
		setAdditionalBankRuleAmiecRendered(true);
		setSaveAdditionalBankRule2(true);
		clearAllRule2();
	}

	public void checkingDTBankRuleData() {
		setDisableEditBankRule(false);
		setDisableSubmitButtonForBankRule(false);
		setBooclearPanelForBankRule(false);
		List<AdditionalBankRuleAddData>  bankRuleList= additionalBankRuleMapService.duplicateCheckInDBBankRule(getCountryId(),getFlexField(),getBankId(),getAdditionalData(),getAdditionalDescription());
		if(bankRuleList.size()>0 && getEditClikedBankRule().equals(false)){
			RequestContext.getCurrentInstance().execute("recordExistInDB.show();");
			clearAllRule3();
			return;
		}else{
			duplicateCheckInDataTabelBankRule();
		}
	}

	
	public void duplicateCheckInDataTabelBankRule(){
		/*if (additionalBankRuleDataList3.size() != 0) {
		i = 0;
		for (AdditionalBankRuleDataTable additionalBankRuleDT : additionalBankRuleDataList3) {
			if (additionalBankRuleDT.getBankCode().equalsIgnoreCase(getAdditionalData())) {
				i = 1;
				setAdditionalData(null);
				setAdditionalDescription(null);
				RequestContext.getCurrentInstance().execute("bankruledataExists.show();");
				clearAllRule3();
				return;
			} else {
				i = 0;
			}
		}
	} else {
		i = 0;
	}

	if (i == 0) {*/
		addAdditionalBankRule3DataTable();
	/*}*/
}
	
	
	// Add to Additional Bank Data table Rule 3
	/*public void addAdditionalBankRule3DataTable() {

		AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();

		additionalBankRuleDataTable.setAdditionalBankRulePK(getAdditionalBankRulePK());
		additionalBankRuleDataTable.setAdditionalBankFieldIdForBankRule(getAdditionalBankFieldIdForBankRule());
		additionalBankRuleDataTable.setCountryId(getCountryId());
		additionalBankRuleDataTable.setCountryName(generalService.getCountryName(getCountryId()));
		additionalBankRuleDataTable.setBankId(getBankId());
		additionalBankRuleDataTable.setBankName(mapBankList.get(getBankId()));
		additionalBankRuleDataTable.setFlexField(getFlexField());
		additionalBankRuleDataTable.setBankCode(getAdditionalData());
		additionalBankRuleDataTable.setBankDescription(getAdditionalDescription());
		Boolean activecheck = false;

		if (getAdditionalBankRulePK() != null) {
			List<AdditionalBankRuleAddData> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankRuleRecordsForView(getCountryId(),getFlexField(),getBankId());
			if(additionalBankAlmullaCodeList.size()>0){
				for(AdditionalBankRuleAddData additionalBnak:additionalBankAlmullaCodeList){
					
			if(getCountryId() != null && getCountryId().intValue()== additionalBnak.getCountryId().getCountryId().intValue() && getFlexField()!=null && getFlexField().equalsIgnoreCase(additionalBnak.getFlexField())
				&& getBankId().intValue()==additionalBnak.getBankId().getBankId().intValue() && getAdditionalData()!=null && getAdditionalData().equalsIgnoreCase(additionalBnak.getAdditionalData())
						&& getAdditionalDescription()!=null && getAdditionalDescription().equalsIgnoreCase(additionalBnak.getAdditionalDescription())){
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(getDynamicLabelForActivateDeactivateForBankRule());
			additionalBankRuleDataTable.setIsActiveForBankRule(getIsActiveForBankRule());
			additionalBankRuleDataTable.setCreatedByForBankRule(getCreatedByForBankRule());
			additionalBankRuleDataTable.setCreatedDateForBankRule(getCreatedDateForBankRule());
			additionalBankRuleDataTable.setApproveByForBankRule(getApproveByForBankRule());
			additionalBankRuleDataTable.setApproveDateForBankRule(getApproveDateForBankRule());
			additionalBankRuleDataTable.setModifiedByForBankRule(getModifiedByForBankRule());
			additionalBankRuleDataTable.setModifiedDateForBankRule(getModifiedDateForBankRule());
			additionalBankRuleDataTable.setRemarksForBankRule(getRemarksForBankRule());
			additionalBankRuleDataTable.setEditClikedBankRule(getEditClikedBankRule());
			activecheck = true;;
			}else{
				//additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(null);
				additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
				additionalBankRuleDataTable.setCreatedByForBankRule(getCreatedByForBankRule());
				additionalBankRuleDataTable.setCreatedDateForBankRule(getCreatedDateForBankRule());
				additionalBankRuleDataTable.setApproveByForBankRule(null);
				additionalBankRuleDataTable.setApproveDateForBankRule(null);
				additionalBankRuleDataTable.setModifiedByForBankRule(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setModifiedDateForBankRule(new Date());
				additionalBankRuleDataTable.setRemarksForBankRule(null);
				//additionalBankRuleDataTable.setEditClikedBankRule(getEditClikedBankRule());
			}
			if(activecheck.equals(true)){
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(getDynamicLabelForActivateDeactivateForBankRule());
			}else{
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.PENDING_FOR_APPROVE);
			}
			}
			}
		} else {
			additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.REMOVE);
			additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
			additionalBankRuleDataTable.setCreatedByForBankRule(sessionStateManage.getUserName());
			additionalBankRuleDataTable.setCreatedDateForBankRule(new Date());
			additionalBankRuleDataTable.setEditClikedBankRule(true);
		}

		additionalBankRuleDataList3.add(additionalBankRuleDataTable);
		if (getAdditionalBankRulePK() == null) {
			additionalBankRuleDataNewList3.add(additionalBankRuleDataTable);
		}
		setAdditionalBankRuleAddDataRendered(true);
		setSaveAdditionalBankRule3(true);
		clearAllRule3();

	}*/
	
	// Edit record for Almulla Code
	public void editRecordForAlmullaCode(AdditionalBankRuleDataTable additionalBankRuleDataTableObj) {
		setDisableEditBankWithAlmullaCode(true);
		setDisableSubmitButtonForAlmullaCode(true);
		setAdditionalBankRuleId(additionalBankRuleDataTableObj.getAdditionalBankRuleId());
		setCountryId(additionalBankRuleDataTableObj.getCountryId());
		getFlexFieldList();
		setFlexField(additionalBankRuleDataTableObj.getFlexField());
		setAmiecCode(additionalBankRuleDataTableObj.getAmiecCode());
		setAmiecDescription(additionalBankRuleDataTableObj.getAmiecDescription());
		setAlmullaDescAlmulla(additionalBankRuleDataTableObj.getAmiecDescription());
		// for activate deactivate
		
		setBooclearfanel(true);
		setCreatedByForAlmullaCode(additionalBankRuleDataTableObj.getCreatedByForAlmullaCode());
		setCreatedDateForAlmullaCode(additionalBankRuleDataTableObj.getCreatedDateForAlmullaCode());
		setAdditionalBankRulaAlmullaCodePK(additionalBankRuleDataTableObj.getAdditionalBankRulaAlmullaCodePK());
		setAdditionalBankFieldId(additionalBankRuleDataTableObj.getAdditionalBankFieldId());
		setEditClikedAlmullaCode(true);
		setRemarksForAlmullaCode(additionalBankRuleDataTableObj.getRemarksForAlmullaCode());
		if (additionalBankRuleDataTableObj.getAdditionalBankRulaAlmullaCodePK() != null) {
			setModifiedByForAlmullaCode(additionalBankRuleDataTableObj.getModifiedBy());
			setModifiedDateForAlmullaCode(additionalBankRuleDataTableObj.getModifiedDate());
			setIsActiveForAlmullaCode(additionalBankRuleDataTableObj.getIsActiveForAlmullaCode());
			setApproveByForAlmullaCode(additionalBankRuleDataTableObj.getApproveByForAlmullaCode());
			setApproveDateForAlmullaCode(additionalBankRuleDataTableObj.getApproveDateForAlmullaCode());
			setDynamicLabelForActivateDeactivateForAlmullaCode(additionalBankRuleDataTableObj.getDynamicLabelForActivateDeactivateForAlmullaCode());


		} else {
			setIsActiveForAlmullaCode(Constants.U);
			setApproveByForAlmullaCode(null);
			setApproveDateForAlmullaCode(null);
			setDynamicLabelForActivateDeactivateForAlmullaCode(null);
		}

		// for activate deactivate
		additionalBankRuleDataList2.remove(additionalBankRuleDataTableObj);
		additionalBankRuleDataNewList2.remove(additionalBankRuleDataTableObj);
		
		setBooEditableRecord(true);
		
		if (additionalBankRuleDataList2.size() == 0) {
			setAdditionalBankRuleAmiecRendered(false);
			setSaveAdditionalBankRule2(false);
		}
	}

	// Remove for Almulla Code
	public void removeForAlmullaCode(AdditionalBankRuleDataTable addBankRule) {
		try{

		AdditionalBankRuleAmiec objAdditional = new AdditionalBankRuleAmiec();

		objAdditional.setAdditionalBankRuleDetailId(addBankRule.getAdditionalBankRulaAlmullaCodePK());

		if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.ACTIVATE)) {
			objAdditional.setIsActive(Constants.U);
		} else if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.DEACTIVATE)) {
			objAdditional.setIsActive(Constants.D);
			objAdditional.setRemarks(addBankRule.getRemarksForAlmullaCode());
		}

		objAdditional.setModifiedBy(addBankRule.getModifiedByForAlmullaCode());
		objAdditional.setModifiedDate(addBankRule.getModifiedDateForAlmullaCode());
		objAdditional.setCreatedBy(addBankRule.getCreatedByForAlmullaCode());
		objAdditional.setCreatedDate(addBankRule.getCreatedDateForAlmullaCode());
		objAdditional.setApprovedBy(addBankRule.getApproveByForAlmullaCode());
		objAdditional.setApprovedDate(addBankRule.getApproveDateForAlmullaCode());

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(addBankRule.getCountryId());
		objAdditional.setCountryId(countryMaster);

		AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();
		additionalBankRuleMap.setAdditionalBankRuleId(addBankRule.getAdditionalBankFieldId());
		objAdditional.setAdditionalBankFieldId(additionalBankRuleMap);

		objAdditional.setFlexField(addBankRule.getFlexField());
		objAdditional.setAmiecCode(addBankRule.getAmiecCode());
		objAdditional.setAmiecDescription(addBankRule.getAmiecDescription());

		additionalBankRuleAmiecService.save(objAdditional);
		}catch(NullPointerException ne){
			  log.info("Method Name::removeForAlmullaCode"+ne.getMessage());
			  setErrorMessage("Method Name::removeForAlmullaCode"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::removeForAlmullaCode "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Remove record for Almulla Code
	public void removeRecordForAlmullaCode(
			AdditionalBankRuleDataTable addBankRule) {
		try{

		if (addBankRule.getAdditionalBankRulaAlmullaCodePK() != null) {
			if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase("Approve")) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleWithAlMullaCodeApproval.xhtml");
			} else {
				removeForAlmullaCode(addBankRule);
			}
			additionalBankRuleDataList2.clear();
			getAdditionBankAlmullaCodeRecords();

		} else {
			
		}
		if (additionalBankRuleDataList2.size() == 0) {
			setAdditionalBankRuleAmiecRendered(false);
			setSaveAdditionalBankRule2(false);
		}
		}catch(NullPointerException ne){
			  log.info("Method Name::removeRecordForAlmullaCode"+ne.getMessage());
			  setErrorMessage("Method Name::removeRecordForAlmullaCode"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::removeRecordForAlmullaCode "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	// Edit Additional Bank Rule Map
	public void editRecordAdditionalBankRule(AdditionalBankRuleDataTable additionalBankRuleData) {
		setDisableEditBankRule(true);
		setDisableSubmitButtonForBankRule(true);
		setAdditionalBankRuleId(additionalBankRuleData.getAdditionalBankRuleId());
		setCountryId(additionalBankRuleData.getCountryId());
		setBankId(additionalBankRuleData.getBankId());
		setFlexField(additionalBankRuleData.getFlexField());
		setAdditionalData(additionalBankRuleData.getBankCode());
		setAdditionalDescription(additionalBankRuleData.getBankDescription());
		setAdditionalDescription(additionalBankRuleData.getBankDescription());

		setBooclearPanelForBankRule(true);
		setEditClikedBankRule(true);
		setCreatedByForBankRule(additionalBankRuleData.getCreatedByForBankRule());
		setCreatedDateForBankRule(additionalBankRuleData.getCreatedDateForBankRule());
		
		setAdditionalBankRulePK(additionalBankRuleData.getAdditionalBankRulePK());
		setAdditionalBankFieldIdForBankRule(additionalBankRuleData.getAdditionalBankFieldIdForBankRule());
		setRemarksForBankRule(additionalBankRuleData.getRemarksForBankRule());

		if (additionalBankRuleData.getAdditionalBankRulePK() != null) {
			setModifiedByForBankRule(sessionStateManage.getUserName());
			setModifiedDateForBankRule(new Date());
			setIsActiveForBankRule(Constants.U);
			setApproveByForBankRule(null);
			setApproveDateForBankRule(null);
			setDynamicLabelForActivateDeactivateForBankRule(null);
		} else {
			setModifiedBy(additionalBankRuleData.getModifiedByForBankRule());
			setModifiedDate(additionalBankRuleData.getModifiedDateForBankRule());
			setIsActiveForBankRule(additionalBankRuleData.getIsActiveForBankRule());
			setApproveByForBankRule(additionalBankRuleData.getApproveByForBankRule());
			setApproveDateForBankRule(additionalBankRuleData.getApproveDateForBankRule());
			setDynamicLabelForActivateDeactivateForBankRule(additionalBankRuleData.getDynamicLabelForActivateDeactivateForBankRule());
		}

		additionalBankRuleDataList3.remove(additionalBankRuleData);
		additionalBankRuleDataNewList3.remove(additionalBankRuleData);
		if (additionalBankRuleDataList3.size() == 0) {
			setSaveAdditionalBankRule3(false);
			setAdditionalBankRuleAddDataRendered(false);
		}
	}

	// Remove Additional Bank Rule Map
	public void removeRecordAddBankRule(AdditionalBankRuleDataTable addBankRuleDt)  {

		try{
		AdditionalBankRuleAddData objAdditional = new AdditionalBankRuleAddData();

		objAdditional.setAdditionalBankRuleDataId(addBankRuleDt.getAdditionalBankRulePK());

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(addBankRuleDt.getCountryId());
		objAdditional.setCountryId(countryMaster);

		if (addBankRuleDt.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.ACTIVATE)) {
			objAdditional.setIsActive(Constants.U);
		} else if (addBankRuleDt.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.DEACTIVATE)) {
			objAdditional.setIsActive(Constants.D);
			objAdditional.setRemarks(addBankRuleDt.getRemarksForBankRule());
		}
		objAdditional.setModifiedBy(addBankRuleDt.getModifiedByForBankRule());
		objAdditional.setModifiedDate(addBankRuleDt.getModifiedDateForBankRule());
		objAdditional.setCreatedBy(addBankRuleDt.getCreatedByForBankRule());
		objAdditional.setCreatedDate(addBankRuleDt.getCreatedDateForBankRule());
		objAdditional.setApprovedBy(addBankRuleDt.getApproveByForBankRule());
		objAdditional.setApprovedDate(addBankRuleDt.getApproveDateForBankRule());

		AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();
		additionalBankRuleMap.setAdditionalBankRuleId(addBankRuleDt.getAdditionalBankFieldIdForBankRule());
		objAdditional.setAdditionalBankFieldId(additionalBankRuleMap);

		objAdditional.setFlexField(addBankRuleDt.getFlexField());

		BankMaster bankMaster = new BankMaster();
		bankMaster.setBankId(addBankRuleDt.getBankId());
		objAdditional.setBankId(bankMaster);

		objAdditional.setAdditionalData(addBankRuleDt.getBankCode());

		objAdditional.setAdditionalDescription(addBankRuleDt.getBankDescription());

		additionalBankRuleAddService.save(objAdditional);
		}catch(NullPointerException ne){
			  log.info("Method Name::removeRecordAddBankRule"+ne.getMessage());
			  setErrorMessage("Method Name::removeRecordAddBankRule"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::removeRecordAddBankRule "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	// Save Additional Bank Rule Map
	public void saveAdditionalBankRule1(){
	
		count=0;
		if (additionalBankRuleDataList1.isEmpty()) {
			RequestContext.getCurrentInstance().execute("norecords.show();");
		} else {
			try{
			if(additionalBankRuleDataList1.size()>0){
				try{
			for (AdditionalBankRuleDataTable additionalBankRuleObj : additionalBankRuleDataList1) {
				
				if(additionalBankRuleObj.getEditClikedBankMapRule().equals(true)){
					count++;
				AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();

				additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleObj.getAdditionalBankRuleMapPk());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(additionalBankRuleObj.getCountryId());
				additionalBankRuleMap.setCountryId(countryMaster);

				additionalBankRuleMap.setFlexField(additionalBankRuleObj.getFlexField());
				additionalBankRuleMap.setFieldName(additionalBankRuleObj.getFieldName());
				additionalBankRuleMap.setOrderNo(additionalBankRuleObj.getOrderNo());

				additionalBankRuleMap.setCreatedBy(additionalBankRuleObj.getCreatedBy());
				additionalBankRuleMap.setCreatedDate(additionalBankRuleObj.getCreatedDate());
				additionalBankRuleMap.setApprovedBy(additionalBankRuleObj.getApproveBy());
				additionalBankRuleMap.setApprovedDate(additionalBankRuleObj.getApproveDate());
				additionalBankRuleMap.setModifiedDate(additionalBankRuleObj.getModifiedDate());
				additionalBankRuleMap.setModifiedBy(additionalBankRuleObj.getModifiedBy());
				additionalBankRuleMap.setIsActive(additionalBankRuleObj.getIsActive());

				additionalBankRuleMapService.save(additionalBankRuleMap);
				}
			}
				}catch(NullPointerException ne){
					  log.info("Method Name::saveAdditionalBankRule1"+ne.getMessage());
					  setErrorMessage("Method Name::saveAdditionalBankRule1"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					setErrorMessage(exception.getMessage());
					log.info("Method Name::saveAdditionalBankRule1 "+exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}
			setNoOfSaveCount(new BigDecimal(count));
			count=0;
			additionalBankRuleDataList1.clear();
			additionalBankRuleDataNewList1.clear();
			RequestContext.getCurrentInstance().execute("complete.show();");
		}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		clearAdditionalMap();
	}

	// Save Additional Bank Rule With Almulla Code
	public void saveAdditionalBankRule2() {
		if (additionalBankRuleDataList2.isEmpty()) {
			RequestContext.getCurrentInstance().execute("norecords.show();");
		} else {
			try{
			for (AdditionalBankRuleDataTable additionalBankRuleObj : additionalBankRuleDataList2) {

				if(additionalBankRuleObj.getEditClikedAlmullaCode().equals(true)){
				AdditionalBankRuleAmiec additionalBankRuleAmiec = new AdditionalBankRuleAmiec();

				additionalBankRuleAmiec.setAdditionalBankRuleDetailId(additionalBankRuleObj.getAdditionalBankRulaAlmullaCodePK());

				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(additionalBankRuleObj.getCountryId());
				additionalBankRuleAmiec.setCountryId(countryMaster);

				additionalBankRuleAmiec.setFlexField(additionalBankRuleObj.getFlexField());
				additionalBankRuleAmiec.setAmiecCode(additionalBankRuleObj.getAmiecCode());
				additionalBankRuleAmiec.setAmiecDescription(additionalBankRuleObj.getAmiecDescription());

				additionalBankRuleAmiec.setIsActive(additionalBankRuleObj.getIsActiveForAlmullaCode());
				additionalBankRuleAmiec.setModifiedDate(additionalBankRuleObj.getModifiedDateForAlmullaCode());
				additionalBankRuleAmiec.setModifiedBy(additionalBankRuleObj.getModifiedByForAlmullaCode());
				additionalBankRuleAmiec.setCreatedBy(additionalBankRuleObj.getCreatedByForAlmullaCode());
				additionalBankRuleAmiec.setCreatedDate(additionalBankRuleObj.getCreatedDateForAlmullaCode());
				additionalBankRuleAmiec.setApprovedBy(additionalBankRuleObj.getApproveByForAlmullaCode());
				additionalBankRuleAmiec.setApprovedDate(additionalBankRuleObj.getApproveDateForAlmullaCode());

				AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();
				//additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleAmiecService.getMasterPk(additionalBankRuleObj.getFlexField()));
				additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleAmiecService.getAdditionalBankRuleMapMasterPk(additionalBankRuleObj.getCountryId(),additionalBankRuleObj.getFlexField()));
				additionalBankRuleAmiec.setAdditionalBankFieldId(additionalBankRuleMap);

				additionalBankRuleAmiecService.save(additionalBankRuleAmiec);
				}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
			}catch(NullPointerException ne){
				  log.info("Method Name::saveAdditionalBankRule2"+ne.getMessage());
				  setErrorMessage("Method Name::saveAdditionalBankRule2"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				log.info("Method Name::saveAdditionalBankRule2 "+exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
	}
		finalClearForAMIEMap();
	}

	// Save Additional Bank Rule With Additional Data
	public void saveAdditionalBankRule3() {

		if (additionalBankRuleDataList3.isEmpty()) {
			RequestContext.getCurrentInstance().execute("norecords.show();");
		} else {
				try{
			for (AdditionalBankRuleDataTable additionalBankRuleObj : additionalBankRuleDataList3) {

				if(additionalBankRuleObj.getEditClikedBankRule().equals(true)){
				AdditionalBankRuleAddData additionalBankRuleAddlData = new AdditionalBankRuleAddData();
				additionalBankRuleAddlData.setAdditionalBankRuleDataId(additionalBankRuleObj.getAdditionalBankRulePK());
				CountryMaster countryMaster = new CountryMaster();
				countryMaster.setCountryId(additionalBankRuleObj.getCountryId());

				BankMaster bankMaster = new BankMaster();
				bankMaster.setBankId(additionalBankRuleObj.getBankId());

				additionalBankRuleAddlData.setCountryId(countryMaster);
				additionalBankRuleAddlData.setBankId(bankMaster);
				additionalBankRuleAddlData.setFlexField(additionalBankRuleObj.getFlexField());
				additionalBankRuleAddlData.setAdditionalData(additionalBankRuleObj.getBankCode());
				additionalBankRuleAddlData.setAdditionalDescription(additionalBankRuleObj.getBankDescription());

				additionalBankRuleAddlData.setApprovedBy(additionalBankRuleObj.getApproveByForBankRule());
				additionalBankRuleAddlData.setApprovedDate(additionalBankRuleObj.getApproveDateForBankRule());
				additionalBankRuleAddlData.setModifiedDate(additionalBankRuleObj.getModifiedDateForBankRule());
				additionalBankRuleAddlData.setModifiedBy(additionalBankRuleObj.getModifiedByForBankRule());
				additionalBankRuleAddlData.setCreatedBy(additionalBankRuleObj.getCreatedByForBankRule());
				additionalBankRuleAddlData.setCreatedDate(additionalBankRuleObj.getCreatedDateForBankRule());
				additionalBankRuleAddlData.setIsActive(additionalBankRuleObj.getIsActiveForBankRule());

				AdditionalBankRuleMap additionalBankRuleMap = new AdditionalBankRuleMap();
				//additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleAmiecService.getMasterPk(additionalBankRuleObj.getFlexField()));
				additionalBankRuleMap.setAdditionalBankRuleId(additionalBankRuleAmiecService.getAdditionalBankRuleMapMasterPk(additionalBankRuleObj.getCountryId(),additionalBankRuleObj.getFlexField()));
				additionalBankRuleAddlData.setAdditionalBankFieldId(additionalBankRuleMap);

				additionalBankRuleAddService.save(additionalBankRuleAddlData);
			}
			}
			RequestContext.getCurrentInstance().execute("complete.show();");
				}catch(NullPointerException ne){
					  log.info("Method Name::saveAdditionalBankRule3"+ne.getMessage());
					  setErrorMessage("Method Name::saveAdditionalBankRule3"); 
					    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
					    return; 
				} catch (Exception exception) {
					setErrorMessage(exception.getMessage());
					log.info("Method Name::saveAdditionalBankRule3 "+exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
				}

	//	addingLstPopUpDB();
		finalClearForBankRuleData();
		}
	}

	// Navigation for Additional Bank Rule
	public void additionalBankRulePageNavigation() {
		setNoOfSaveCount(null);
		setBooEditableRecord(null);
		setRenderCountryIdBankMapForApprove(false);
		setRenderCountryIdBankMap(true);
		setCountryNameForBankMap(null);
		setRenderFlexFieldBankMapForApprove(false);
		setRenderFlexFieldBankMap(true);
		setFlexFieldNameForBankMap(null);
		setFlexFieldForBankMap(null);
		setRenderFlexNameBankMapForApprove(false);
		setRenderFlexNameBankMap(true);
		setRenderOrderNoBankMap(true);
		setRenderOrderNoBankMapForApprove(false);
		setOrderNoForBankMap(null);
		setRenderApproveCancelButtonPanelForBankMap(false);
		setRenderAddButtonPanelForBankMap(true);
		setAddtionalBankRuleMapPk(null);
		setCreatedByForBankRuleMap(null);
		// rahamath Additional Bank Rule Map Approval code
		setEditClikedBankMapRule(false);
		ClearAllRule1();
		setSaveAdditionalBankRule1(false);
		setAdditionalBankRuleMapRendered(false);
		setBooRenderSaveExit(false);
		additionalBankRuleDataList1.clear();
		additionalBankRuleDataNewList1.clear();
		setDisableEditBankRuleMap(false);
		flexFiledViewList = additionalBankRuleAddService.getflexiFieldList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Navigation for Additional Bank Rule Additional data
	public void additionalBankRulePageNavigationThree() {
		setDisableEditBankRule(false);
		setRenderCountryIdForApprove(false);
		setRenderCountryId(true);
		setRenderFlexField(true);
		setRenderFlexFieldForApprove(false);
		setRenderBankId(true);
		setRenderBankIdForApprove(false);
		setRenderBankCode(true);
		setRenderBankCodeForApprove(false);
		setRenderBankDesc(true);
		setRenderBankDescForApprove(false);
		setRenderAddButtonPanel(true);
		setRenderApproveCancelButtonPanel(false);
		setCreatedByForApprove(null);
		setAddtionBankRulePK(null);
		setBooclearPanelForBankRule(false);
		finalClearForBankRuleData();
		setSaveAdditionalBankRule3(false);
		setAdditionalBankRuleAddDataRendered(false);
		additionalBankRuleDataList3.clear();
		additionalBankRuleDataNewList3.clear();
	
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "additionalbankruleadditionaldata.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	// Navigation for Additional Bank Rule AMIEC Mapping
	public void additionalBankRulePageNavigationTwo() {
		setDisableEditBankWithAlmullaCode(false);
		setRenderCountryIdAlmulla(true);
		setRenderCountryIdForApproveAlmulla(false);
		setCountryNameAlmulla(null);
		setRenderFlexFieldForApproveAlmulla(false);
		setRenderFlexFieldAlmulla(true);
		setFlexFieldNameAlmulla(null);
		setAlmullaCodeAlmulla(null);
		setRenderAlmullaCodeForApproveAlmulla(false);
		setRenderAlmullaCodeAlmulla(true);
		setAlmullaDescAlmulla(null);
		setRenderAlmullaDescAlmulla(true);
		setRenderAlmullaDescForApproveAlmulla(false);
		setCreatedByAlmulla(null);
		setAlmullaCodePK(null);
		setBooclearfanel(false);

		// for Button Panel off/On
		setRenderApproveCancelButtonPanel(false);
		setRenderAddButtonPanel(true);
		setRenderApproveCancelButtonPanelAlmulla(false);

		finalClearForAMIEMap();
		lstAllDBcountryFlex.clear();
		setSaveAdditionalBankRule2(false);
		setAdditionalBankRuleAmiecRendered(false);
		additionalBankRuleDataList2.clear();
		additionalBankRuleDataNewList2.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "additionalbankruleamiecmap.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Additional Bank Rule Map Enquiry Navigation
	public void additionalBankRuleMapEnquiryPageNavigation() {
		getAdditionBankRuleMapEnquiryRecords();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "AdditionalBankRuleMapEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionalBankRuleMapEnquiry.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Additional Bank Data Enquiry Navigation
	public void additionalBankDataEnquiryPageNavigation() {
		getAdditionBankRuleEnquiryRecords();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "AdditionalBankRuleaDataEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionalBankRuleaDataEnquiry.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// For AdditionalBankRuleAMIECMapEnquiry screen 2
	public void additionalBankALMCEnquiryPageNavigation() {
		getAdditionBankAlmullaCodeEnquiryRecords();

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "AdditionalBankRuleAMIECMapEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionalBankRuleAMIECMapEnquiry.xhtml");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// Click OK Navigation
	public void clickOnOKSave() {
		setNoOfSaveCount(null);
		setAdditionalBankRuleAddDataRendered(false);
		setAdditionalBankRuleMapRendered(false);
		additionalBankRuleDataList1.clear();
		additionalBankRuleDataNewList1.clear();
		setBooRenderSaveExit(false);
		setAdditionalBankRuleMapRendered(false);
		setSaveAdditionalBankRule1(false);
		setDisableEditBankRuleMap(false);
		setEditClikedBankMapRule(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Exit navigation
	public void clickOnOKSaveExit() {
		setAdditionalBankRuleAddDataRendered(false);
		setAdditionalBankRuleMapRendered(false);
		additionalBankRuleDataList1.clear();
		setAdditionalBankRuleMapRendered(false);
		setSaveAdditionalBankRule1(false);
		setBooRenderSaveExit(false);
		setDisableEditBankRuleMap(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click OK Navigation (AMIEC Map)
	public void clickOnOk() {
		setAdditionalBankRuleAmiecRendered(false);
		setAdditionalBankRuleAddDataRendered(false);
		additionalBankRuleDataList2.clear();
		setBooRenderSave(false);
		setAdditionalBankRuleAmiecRendered(false);
		setSaveAdditionalBankRule2(false);
	setDisableEditBankWithAlmullaCode(false);
	setEditClikedAlmullaCode(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Click OK Navigation (AMIEC Map)
	public void clickOnSave() {
		setAdditionalBankRuleAddDataRendered(false);
		setAdditionalBankRuleAmiecRendered(false);
		additionalBankRuleDataList2.clear();
		setBooRenderSave(false);
		setAdditionalBankRuleAmiecRendered(false);
		setSaveAdditionalBankRule2(false);
		setDisableEditBankWithAlmullaCode(false);
		setEditClikedAlmullaCode(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Clear Methods
	public void clearAllRule1() {

		//setCountryId(null);
		setFlexField(null);
		setdBflexField(null);
		setFieldName(null);
		setOrderNo(null);
		setAdditionabankrulemapPK(null);
		if (getLstFlexFiledDB().size() != 0) {
			lstFlexFiledDB.clear();
		}
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApproveDate(null);
		setEditClikedBankMapRule(false);

	}
	public void clearAdditionalMap(){
		  setCountryId(null);
		  setFlexField(null);
			setdBflexField(null);
			setFieldName(null);
			setOrderNo(null);
			setAdditionabankrulemapPK(null);
			if (getLstFlexFiledDB().size() != 0) {
				lstFlexFiledDB.clear();
			}
			setBooDBFlexField(false);
			setBooNewFlexField(true);
			setEditButton(false);
			setCreatedBy(null);
			setCreatedDate(null);
			setApproveBy(null);
			setApproveDate(null);
			setEditClikedBankMapRule(false);  
	}

	public void ClearAllRule1() {
		additionalBankRuleDataNewList1.clear();
		if (additionalBankRuleDataList1.size() != 0) {
			additionalBankRuleDataList1.clear();
		}
		if (getLstAllRecordsDB().size() != 0) {
			lstAllRecordsDB.clear();
		}
		setCountryId(null);
		setFlexField(null);
		setdBflexField(null);
		setFieldName(null);
		setOrderNo(null);
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setDisableSubmitButtonForBankMap(false);
		setDisableEditBankRuleMap(false);
		setDynamicLabelForActivateDeactivateForBankMap(null);
		setIsActive(null);
		setCreatedBy(null);
		setCreatedDate(null);
		setApproveBy(null);
		setApproveDate(null);
		setRemarksForBankMap(null);
		setAdditionabankrulemapPK(null);
		setEditClikedBankMapRule(false);
		setModifiedBy(null);
		setModifiedDate(null);
		
	}

	public void clearAllRule2() {

		setAmiecCode(null);
		setAmiecDescription(null);
		setAdditionalBankRulaAlmullaCodePK(null);
		setAdditionalBankFieldId(null);

		setAdditionalBankRuleId(null);
		//setCountryId(null);
		//setFlexField(null);
		setAmiecCode(null);
		setAmiecDescription(null);
		// for activate deactivate
		setDynamicLabelForActivateDeactivateForAlmullaCode(null);
		setIsActiveForAlmullaCode(null);
		setCreatedByForAlmullaCode(null);
		setCreatedDateForAlmullaCode(null);
		setApproveByForAlmullaCode(null);
		setApproveDateForAlmullaCode(null);
		setAdditionalBankRulaAlmullaCodePK(null);
		setAdditionalBankFieldId(null);
		setEditClikedAlmullaCode(false);
		setRemarksForAlmullaCode(null);
			setModifiedByForAlmullaCode(null);
			setModifiedDateForAlmullaCode(null);
		

	}

	// Final Clear for AMIEC Map
	public void finalClearForAMIEMap() {
		additionalBankRuleDataNewList2.clear();
		if (additionalBankRuleDataList2.size() != 0) {
			additionalBankRuleDataList2.clear();
		}
		if (getAdditionalBankRuleMaps().size() != 0) {
			additionalBankRuleMaps.clear();
		}
		setCountryId(null);
		setFlexField(null);
		setAmiecCode(null);
		setAmiecDescription(null);
	}

	public void clearAllRule3() {

		setAdditionalData(null);
		setAdditionalDescription(null);
		setAdditionalBankRulePK(null);
		setAdditionalBankFieldIdForBankRule(null);
		setDynamicLabelForActivateDeactivateForBankRule(null);
			setAdditionalBankRulePK(null);
			setCreatedByForBankRule(null);
			setCreatedDateForBankRule(null);
			setModifiedByForBankRule(null);
			setModifiedDateForBankRule(null);
			setApproveDateForBankRule(null);
			setApproveByForBankRule(null);
			setIsActiveForBankRule(null);
			setRemarksForBankRule(null);
			setAdditionalBankFieldIdForBankRule(null);
			setEditClikedBankRule(false);

	}

	public void finalClearForBankRuleData() {
		additionalBankRuleDataNewList3.clear();
		if (additionalBankRuleDataList3.size() != 0) {
			additionalBankRuleDataList3.clear();
		}
		if (getAdditionalBankRuleMaps().size() != 0) {
			additionalBankRuleMaps.clear();
		}
		setBankId(null);
		setCountryId(null);
		setFlexField(null);
		setAdditionalData(null);
		setAdditionalDescription(null);
	}

	public void exit() throws IOException {
		  additionalBankRuleDataList1.clear();
			additionalBankRuleDataNewList1.clear();
		if (sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void clickOk() {
		setAdditionalBankRuleAmiecRendered(false);
		setAdditionalBankRuleAddDataRendered(false);
		additionalBankRuleDataList3.clear();
		setBooRenderSaveOne(false);
		setAdditionalBankRuleAddDataRendered(false);
		setSaveAdditionalBankRule3(false);
		setDisableEditBankRule(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clickOnSaveOne() {
		setAdditionalBankRuleAmiecRendered(false);
		setAdditionalBankRuleAddDataRendered(false);
		setAdditionalBankRuleAddDataRendered(false);
		setSaveAdditionalBankRule3(false);
		additionalBankRuleDataList3.clear();
		setBooRenderSaveOne(false);
		setDisableEditBankRule(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Populate Flex Field
	public void popFlex() {
		setAmiecCode(null);
		setAmiecDescription(null);
		setFlexField(null);
		if (getFlexField() != null) {
			setDisableSubmitButtonForAlmullaCode(true);
		} else {
			setDisableSubmitButtonForAlmullaCode(false);
		}

		try{
		additionalBankRuleMaps = additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
		if (additionalBankRuleMaps.size() != 0) {
			setAdditionalBankRuleMaps(additionalBankRuleMaps);
			// viewRecordsFromDB();
		} else {
			setFlexField(null);
		
		}
		}catch(NullPointerException ne){
			  log.info("Method Name::popFlex"+ne.getMessage());
			  setErrorMessage("Method Name::popFlex"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::popFlex "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Populate Flex Country
	public void popCountryFlex() {
		try{
		additionalBankRuleMaps = additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
		if (additionalBankRuleMaps.size() != 0) {
			setAdditionalBankRuleMaps(additionalBankRuleMaps);
		} else {
			setBankId(null);
			setAdditionalData(null);
			setAdditionalDescription(null);
		
		}
		}catch(NullPointerException ne){
			  log.info("Method Name::popCountryFlex"+ne.getMessage());
			  setErrorMessage("Method Name::popCountryFlex"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::popCountryFlex "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Auto Complete
	public List<String> autoCompleteData(String query) {

		if (query.length() > 0) {
			return additionalBankRuleAmiecService.getAllComponent(query, getCountryId(), getFlexField());

		} else {
			return null;
		}
	}

	// Auto Complete Additional data
	public List<String> autoCompleteAdditionalData(String query) {

		if (query.length() > 0) {
			return additionalBankRuleAmiecService.getComponentadditionalData(query, getCountryId(), getFlexField(), getBankId());

		} else {
			return null;
		}
	}

	// Populate AMIEC Description
	public void populateAmiecDescription() {

		if (getCountryId() != null && getFlexField() != null && getAmiecCode() != null) {
			List<AdditionalBankRuleAmiec> additionalBankRuleAmiecList = new ArrayList<AdditionalBankRuleAmiec>();
           try{
			additionalBankRuleAmiecList = additionalBankRuleAmiecService .populateAmiecDescription(getCountryId(), getFlexField(), getAmiecCode());

			if (additionalBankRuleAmiecList.size() > 0) {
				setDisableEditBankWithAlmullaCode(true);
				setDisableSubmitButtonForAlmullaCode(true);
				setAdditionalBankRuleId(additionalBankRuleAmiecList.get(0).getAdditionalBankRuleDetailId());
				setCountryId(additionalBankRuleAmiecList.get(0).getCountryId().getCountryId());
				setFlexField(additionalBankRuleAmiecList.get(0).getFlexField());
				setAmiecCode(additionalBankRuleAmiecList.get(0).getAmiecCode());
				setAmiecDescription(additionalBankRuleAmiecList.get(0).getAmiecDescription());
				// for activate deactivate
				if(additionalBankRuleAmiecList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)){
					setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.DEACTIVATE);
				 }else if(additionalBankRuleAmiecList.get(0).getIsActive().equalsIgnoreCase( Constants.D)){
					 setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.ACTIVATE);
				 } else if(additionalBankRuleAmiecList.get(0).getIsActive().equalsIgnoreCase(Constants.U)&& additionalBankRuleAmiecList.get(0).getModifiedBy()==null && additionalBankRuleAmiecList.get(0).getModifiedDate()==null
							&& additionalBankRuleAmiecList.get(0).getApprovedBy()==null && additionalBankRuleAmiecList.get(0).getApprovedDate()==null 
							&& additionalBankRuleAmiecList.get(0).getRemarks()==null){
					 setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.DELETE);
					}else{
						setDynamicLabelForActivateDeactivateForAlmullaCode(null);
					}
				
				
				
				setIsActiveForAlmullaCode(additionalBankRuleAmiecList.get(0).getIsActive());
				setCreatedByForAlmullaCode(additionalBankRuleAmiecList.get(0).getCreatedBy());
				setCreatedDateForAlmullaCode(additionalBankRuleAmiecList.get(0).getCreatedDate());
				setApproveByForAlmullaCode(additionalBankRuleAmiecList.get(0).getApprovedBy());
				setApproveDateForAlmullaCode(additionalBankRuleAmiecList.get(0).getApprovedDate());
				setAdditionalBankRulaAlmullaCodePK(additionalBankRuleAmiecList.get(0).getAdditionalBankRuleDetailId());
				setAdditionalBankFieldId(additionalBankRuleAmiecList.get(0).getAdditionalBankFieldId().getAdditionalBankRuleId());
				setEditClikedAlmullaCode(true);
				setRemarksForAlmullaCode(additionalBankRuleAmiecList.get(0).getRemarks());
					setModifiedByForAlmullaCode(additionalBankRuleAmiecList.get(0).getModifiedBy());
					setModifiedDateForAlmullaCode(additionalBankRuleAmiecList.get(0).getModifiedDate());
				
			} else {
				setAmiecDescription(null);
			}
           }catch(NullPointerException ne){
 			  log.info("Method Name::populateAmiecDescription"+ne.getMessage());
 			  setErrorMessage("Method Name::populateAmiecDescription"); 
 			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
 			    return; 
 		} catch (Exception exception) {
 			setErrorMessage(exception.getMessage());
 			log.info("Method Name::populateAmiecDescription "+exception.getMessage());
 			RequestContext.getCurrentInstance().execute("exception.show();");
 			return;
 		}
		}
	}

	// Populate Additional Bank Rule Description
	public void populateAddBankRuleDesc() {
		
		if(getCountryId()!=null && getFlexField()!=null && getBankId()!=null && getAdditionalData()!=null){	
			List<AdditionalBankRuleAddData> additionalBankRuleList = new ArrayList<AdditionalBankRuleAddData>();
            try{
			additionalBankRuleList = additionalBankRuleAmiecService.populateBankRuleData(getCountryId(),getFlexField() ,getBankId() , getAdditionalData());

			if (additionalBankRuleList.size() > 0) {
				setDisableEditBankRule(true);
				setDisableSubmitButtonForBankRule(true);
				if (additionalBankRuleList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
					setDynamicLabelForActivateDeactivateForBankRule(Constants.DEACTIVATE);
				} else if (additionalBankRuleList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
					setDynamicLabelForActivateDeactivateForBankRule(Constants.ACTIVATE);
				}else if(additionalBankRuleList.get(0).getIsActive().equalsIgnoreCase(Constants.U)&& additionalBankRuleList.get(0).getModifiedBy()==null && additionalBankRuleList.get(0).getModifiedDate()==null
						&& additionalBankRuleList.get(0).getApprovedBy()==null && additionalBankRuleList.get(0).getApprovedDate()==null 
						&& additionalBankRuleList.get(0).getRemarks()==null){
					setDynamicLabelForActivateDeactivateForBankRule(Constants.DELETE);
				}else{
					setDynamicLabelForActivateDeactivateForBankRule(null);
				}
				setAdditionalBankRulePK(additionalBankRuleList.get(0).getAdditionalBankRuleDataId());
				setCountryId(additionalBankRuleList.get(0).getCountryId().getCountryId());
				setCountryName(generalService.getCountryName(additionalBankRuleList.get(0).getCountryId().getCountryId()));
				setFieldName(additionalBankRuleList.get(0).getAdditionalBankFieldId().getFieldName());
				setFlexField(additionalBankRuleList.get(0).getAdditionalBankFieldId().getFlexField());
				setBankId(additionalBankRuleList.get(0).getBankId().getBankId());
				setBankName(additionalBankRuleList.get(0).getBankId().getBankFullName());
				setAdditionalData(additionalBankRuleList.get(0).getAdditionalData());
				setAdditionalDescription(additionalBankRuleList.get(0).getAdditionalDescription());
				setCreatedByForBankRule(additionalBankRuleList.get(0).getCreatedBy());
				setCreatedDateForBankRule(additionalBankRuleList.get(0).getCreatedDate());
				setModifiedByForBankRule(additionalBankRuleList.get(0).getModifiedBy());
				setModifiedDateForBankRule(additionalBankRuleList.get(0).getModifiedDate());
				setApproveDateForBankRule(additionalBankRuleList.get(0).getApprovedDate());
				setApproveByForBankRule(additionalBankRuleList.get(0).getApprovedBy());
				setIsActiveForBankRule(additionalBankRuleList.get(0).getIsActive());
				if(additionalBankRuleList.get(0).getRemarks()!=null){
					setRemarksForBankRule(additionalBankRuleList.get(0).getRemarks());
				}else{
					setRemarksForBankRule(null);
				}
				setAdditionalBankFieldIdForBankRule(additionalBankRuleList.get(0).getAdditionalBankFieldId().getAdditionalBankRuleId());
				
			} else {
				setAdditionalDescription(null);
			}
            }catch(NullPointerException ne){
   			  log.info("Method Name::getFlexField"+ne.getMessage());
   			  setErrorMessage("Method Name::getFlexField"); 
   			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
   			    return; 
   		} catch (Exception exception) {
   			setErrorMessage(exception.getMessage());
   			log.info("Method Name::getFlexField "+exception.getMessage());
   			RequestContext.getCurrentInstance().execute("exception.show();");
   			return;
   		}
		}
		}
		

	// Update Amiec Navigation
	public void updateRecordAmiec() {

		if (getDupRecord().equals(Constants.YES)) {

			setAdditionalBankRuleAmiecRendered(true);
			setSaveAdditionalBankRule2(true);

		} else {
			setAdditionalBankRuleAmiecRendered(false);
			setSaveAdditionalBankRule2(false);
			clearAllRule2();
		}

		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Exit from AMIEC Mapping
	public void exitDialogAmiec() {
		setAdditionalBankRuleAmiecRendered(false);
		setSaveAdditionalBankRule2(false);
		clearAllRule2();
		setDisableEditBankWithAlmullaCode(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// Populate Addition Data Description
	public void populateAdditionalDescription() {
		setDupRecord(null);
		try{
		List<AdditionalBankRuleAddData> additionalBankRuleAddData = new ArrayList<AdditionalBankRuleAddData>();
		additionalBankRuleAddData = additionalBankRuleAmiecService.populateAdditionalDescription(getCountryId(), getBankId(),getFlexField(), getAdditionalData());
		if (additionalBankRuleAddData.size() > 0) {
			setAdditionalDescription(additionalBankRuleAddData.get(0).getAdditionalDescription());
			setDupRecord(Constants.YES);
			RequestContext.getCurrentInstance().execute("succ.show();");
			return;
		} else {
			setDupRecord(Constants.NO);
		}
		 }catch(NullPointerException ne){
  			  log.info("Method Name::populateAdditionalDescription"+ne.getMessage());
  			  setErrorMessage("Method Name::populateAdditionalDescription"); 
  			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
  			    return; 
  		} catch (Exception exception) {
  			setErrorMessage(exception.getMessage());
  			log.info("Method Name::populateAdditionalDescription "+exception.getMessage());
  			RequestContext.getCurrentInstance().execute("exception.show();");
  			return;
  		}
	}

	public void updateRecorDescription() {
		if (getDupRecord().equals(Constants.YES)) {
			setAdditionalBankRuleAddDataRendered(true);
			setSaveAdditionalBankRule3(false);
		} else {
			setAdditionalBankRuleAddDataRendered(false);
			setSaveAdditionalBankRule3(false);
			clearAllRule3();
		}
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void exitDialogDescription() {
		setDisableEditBankRule(false);
		setAdditionalBankRuleAmiecRendered(false);
		setSaveAdditionalBankRule3(false);
		clearAllRule3();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Fetch Flex field Data
	public void fetchData() {
        try{
		List<AdditionalBankRuleMap> lstflexfielddata = new ArrayList<AdditionalBankRuleMap>();
		lstflexfielddata = additionalBankRuleAmiecService.getLstofFlexFields(getCountryId());
		if (lstflexfielddata.size() > 1) {
			setdBflexField(null);
			setFlexField(null);
			setLstFlexFiledDB(lstflexfielddata);
			setEditButton(true);
			setBooDBFlexField(true);
			setBooNewFlexField(false);
		} else if (lstflexfielddata.size() == 1) {
			setLstFlexFiledDB(lstflexfielddata);
			setdBflexField(null);
			setFlexField(null);
			setEditButton(true);
			setBooDBFlexField(true);
			setBooNewFlexField(false);
		} else {
			setEditButton(false);
			setBooDBFlexField(false);
			setBooNewFlexField(true);
			setdBflexField(null);
			setFlexField(null);
			setFieldName(null);
			setOrderNo(null);
			if (getLstFlexFiledDB() != null) {
				lstFlexFiledDB.clear();
			}
		}
        }catch(NullPointerException ne){
			  log.info("Method Name::fetchData"+ne.getMessage());
			  setErrorMessage("Method Name::fetchData"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::fetchData "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void insertNewFlexField() {
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setEditButton(false);
		setdBflexField(null);
		setFlexField(null);
		setFieldName(null);
		setOrderNo(null);
	}

	public void fetchDataBaseRecords() {
		try{
		if (getdBflexField() != null) {
			setFlexField(getdBflexField());
		}
		if (getCountryId() != null && getFlexField() != null) {
			List<AdditionalBankRuleMap> lstallRecordsDB = new ArrayList<AdditionalBankRuleMap>();
			lstallRecordsDB = additionalBankRuleAmiecService.getAllRecordbyCountryFlex(getCountryId(), getFlexField());
			if (lstallRecordsDB.size() != 0) {
					for (AdditionalBankRuleMap additionalBankRuleMap : lstallRecordsDB) {
						// for disabling Submit button
						setDisableSubmitButtonForBankMap(true);
						setDisableEditBankRuleMap(true);
						// for activate deactivate
						if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)){
							setDynamicLabelForActivateDeactivateForBankMap(Constants.DEACTIVATE);
						 }else if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)){
							 setDynamicLabelForActivateDeactivateForBankMap(Constants.ACTIVATE);
						 }else if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U)&& additionalBankRuleMap.getModifiedBy()==null && additionalBankRuleMap.getModifiedDate()==null
									&& additionalBankRuleMap.getApprovedBy()==null && additionalBankRuleMap.getApprovedDate()==null 
									&& additionalBankRuleMap.getRemarks()==null){
							 setDynamicLabelForActivateDeactivateForBankMap(Constants.DELETE);
							}else{
								setDynamicLabelForActivateDeactivateForBankMap(null);
							}
						
						
						setIsActive(additionalBankRuleMap.getIsActive());
						setCreatedBy(additionalBankRuleMap.getCreatedBy());
						setCreatedDate(additionalBankRuleMap.getCreatedDate());
						setApproveBy(additionalBankRuleMap.getApprovedBy());
						setApproveDate(additionalBankRuleMap.getApprovedDate());
						setRemarksForBankMap(additionalBankRuleMap.getRemarks());
						setAdditionabankrulemapPK(additionalBankRuleMap.getAdditionalBankRuleId());
						setEditClikedBankMapRule(true);
						setModifiedBy(additionalBankRuleMap.getModifiedBy());
						setModifiedDate(additionalBankRuleMap.getModifiedDate());
						setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
						setFlexField(additionalBankRuleMap.getFlexField());
						setFieldName(additionalBankRuleMap.getFieldName());
						setOrderNo(additionalBankRuleMap.getOrderNo());
				}
			} else {
				setFieldName(null);
				setOrderNo(null);
			}
		}
		  }catch(NullPointerException ne){
			  log.info("Method Name::fetchDataBaseRecords"+ne.getMessage());
			  setErrorMessage("Method Name::fetchDataBaseRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::fetchDataBaseRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	// Remove records - Additional bank
	public void removeRecordForAddBankMap(AdditionalBankRuleDataTable addBankRule)  {
      try{
		AdditionalBankRuleMap objAdditional = new AdditionalBankRuleMap();
		objAdditional.setAdditionalBankRuleId(addBankRule.getAdditionalBankRuleMapPk());

		if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.ACTIVATE)) {
			objAdditional.setIsActive(Constants.U);
		} else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.DEACTIVATE)) {
			objAdditional.setIsActive(Constants.D);
			objAdditional.setRemarks(addBankRule.getRemarksForBankMap());
		}

		CountryMaster countryMaster = new CountryMaster();
		countryMaster.setCountryId(addBankRule.getCountryId());
		objAdditional.setCountryId(countryMaster);

		objAdditional.setFieldName(addBankRule.getFieldName());
		objAdditional.setFlexField(addBankRule.getFlexField());
		objAdditional.setOrderNo(addBankRule.getOrderNo());

		objAdditional.setModifiedBy(addBankRule.getModifiedBy());
		objAdditional.setModifiedDate(addBankRule.getModifiedDate());
		objAdditional.setCreatedBy(addBankRule.getCreatedBy());
		objAdditional.setCreatedDate(addBankRule.getCreatedDate());
		objAdditional.setApprovedBy(addBankRule.getApproveBy());
		objAdditional.setApprovedDate(addBankRule.getApproveDate());

		additionalBankRuleMapService.save(objAdditional);
      }catch(NullPointerException ne){
		  log.info("Method Name::removeRecordForAddBankMap"+ne.getMessage());
		  setErrorMessage("Method Name::removeRecordForAddBankMap"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	} catch (Exception exception) {
		setErrorMessage(exception.getMessage());
		log.info("Method Name::removeRecordForAddBankMap "+exception.getMessage());
		RequestContext.getCurrentInstance().execute("exception.show();");
		return;
	}
	}


	// Edit record
	public void editRecordDTBankMap(AdditionalBankRuleDataTable addBankRule) {
		// for disabling Submit button
		setDisableSubmitButtonForBankMap(true);
		setDisableEditBankRuleMap(true);
		// for activate deactivate
		
		setCreatedBy(addBankRule.getCreatedBy());
		setCreatedDate(addBankRule.getCreatedDate());
	
		setRemarksForBankMap(addBankRule.getRemarks());
		setAdditionabankrulemapPK(addBankRule.getAdditionalBankRuleMapPk());
		setEditClikedBankMapRule(true);
		if (addBankRule.getAdditionalBankRuleMapPk() != null) {
			setModifiedBy(sessionStateManage.getUserName());
			setModifiedDate(new Date());
			setIsActive(Constants.U);
			setApproveBy(null);
			setApproveDate(null);
			setDynamicLabelForActivateDeactivateForBankMap(null);
		} else {
			setModifiedBy(addBankRule.getModifiedBy());
			setModifiedDate(addBankRule.getModifiedDate());
			setIsActive(addBankRule.getIsActive());
			setApproveBy(addBankRule.getApproveBy());
			setApproveDate(addBankRule.getApproveDate());
			setDynamicLabelForActivateDeactivateForBankMap(addBankRule.getDynamicLabelForActivateDeactivateForBankMap());
		}
		// for activate deactivate

		setCountryId(addBankRule.getCountryId());
		setFlexField(addBankRule.getFlexField());
		setBooDBFlexField(false);
		setBooNewFlexField(true);
		setFieldName(addBankRule.getFieldName());
		setOrderNo(addBankRule.getOrderNo());
		additionalBankRuleDataList1.remove(addBankRule);
		additionalBankRuleDataNewList1.remove(addBankRule);
		if (additionalBankRuleDataList1.size() == 0) {
			setSaveAdditionalBankRule1(false);
			setAdditionalBankRuleMapRendered(false);
		}
	}

	// View Additional Bank Data
	public void viewRecordAddBankData() {
		try{
		if (getCountryId() != null && getFlexField() != null
				&& getBankId() != null) {
			setAdditionalData(null);
			setAdditionalDescription(null);
			List<AdditionalBankRuleAddData> lstAllDB = new ArrayList<AdditionalBankRuleAddData>();
			lstAllDB = additionalBankRuleAddService.getDBCountryFlexBank(
					getCountryId(), getFlexField(), getBankId());
			if (lstAllDB.size() != 0) {
				setLstAllDBcountryFlexBank(lstAllDB);
				
			} else {
				
			}
		}
		 }catch(NullPointerException ne){
			  log.info("Method Name::viewRecordAddBankData"+ne.getMessage());
			  setErrorMessage("Method Name::viewRecordAddBankData"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::viewRecordAddBankData "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void addingLstPopUpDB() {
		for (AdditionalBankRuleAddData additionalBankData : getLstAllDBcountryFlexBank()) {
			AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
			additionalBankRuleDataTable.setAdditionalBankRuleId(additionalBankData.getAdditionalBankRuleDataId());
			additionalBankRuleDataTable.setCountryId(additionalBankData.getCountryId().getCountryId());
			additionalBankRuleDataTable.setCountryName(generalService.getCountryName(new BigDecimal(sessionStateManage.isExists("languageId") ? sessionStateManage.getSessionValue("languageId"): "1"), additionalBankData.getCountryId().getCountryId()));
			additionalBankRuleDataTable.setFlexField(additionalBankData.getFlexField());
			additionalBankRuleDataTable.setBankId(additionalBankData.getBankId().getBankId());
			additionalBankRuleDataTable.setBankName(additionalBankData.getBankId().getBankFullName());
			additionalBankRuleDataTable.setBankCode(additionalBankData.getAdditionalData());
			additionalBankRuleDataTable.setBankDescription(additionalBankData.getAdditionalDescription());
			additionalBankRuleDataTable.setCreatedBy(additionalBankData.getCreatedBy());
			additionalBankRuleDataTable.setCreatedDate(additionalBankData.getCreatedDate());
			additionalBankRuleDataTable.setAdditionalBankRulePk(additionalBankData.getAdditionalBankFieldId().getAdditionalBankRuleId());
			additionalBankRuleDataList3.add(additionalBankRuleDataTable);
		}
	}

	public void clickOnOKGotoAddtionalBankRulePanel() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleApproval.xhtml");
	}

	public void approveRecords() {
		try{
		String approveMsg=additionalBankRuleAddService.approveRecord(getAddtionBankRulePK(),sessionStateManage.getUserName());
		if(approveMsg.equalsIgnoreCase("Sucess"))
		{
			RequestContext.getCurrentInstance().execute("approved.show();");
			return;
		}else
		{
			RequestContext.getCurrentInstance().execute("notApproved.show();");
			return;
		}
		
		 }catch(NullPointerException ne){
			  log.info("Method Name::approveRecords"+ne.getMessage());
			  setErrorMessage("Method Name::approveRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::approveRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	
	}

	public void cancelFromApproval() throws IOException {
		setRenderApproveCancelButtonPanel(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleApproval.xhtml");
	}

	public void clickOnOKGotoAlmullaCodePanel() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleWithAlMullaCodeApproval.xhtml");
	}

	public void approveRecordsAlmulla() {
			try{
				String approveMsg=additionalBankRuleAddService.approveRecordForAlmulla(getAlmullaCodePK(), sessionStateManage.getUserName());
				if(approveMsg.equalsIgnoreCase("Sucess"))
				{
					RequestContext.getCurrentInstance().execute("approved.show();");
					return;
				}else
				{
					RequestContext.getCurrentInstance().execute("notApproved.show();");
					return;
				}
				
			 }catch(NullPointerException ne){
				  log.info("Method Name::approveRecordsAlmulla"+ne.getMessage());
				  setErrorMessage("Method Name::approveRecordsAlmulla"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				log.info("Method Name::approveRecordsAlmulla "+exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
	}

	public void cancelFromApprovalAlmulla() throws IOException {
		setRenderApproveCancelButtonPanelAlmulla(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleWithAlMullaCodeApproval.xhtml");
	}

	public void clickOnOKGotoBankMapPanel() throws IOException {
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
	}

	public void approveRecordsBankMap() {
		
		try{
			String approveMsg=additionalBankRuleAddService.approveRecordForBankRuleMap( getAddtionalBankRuleMapPk(), sessionStateManage.getUserName());
			if(approveMsg.equalsIgnoreCase("Sucess"))
			{
				RequestContext.getCurrentInstance().execute("approved.show();");
				return;
			}else
			{
				RequestContext.getCurrentInstance().execute("notApproved.show();");
				return;
			}
			
		 }catch(NullPointerException ne){
			  log.info("Method Name::approveRecordsBankMap"+ne.getMessage());
			  setErrorMessage("Method Name::approveRecordsBankMap"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::approveRecordsBankMap "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void cancelFromApprovalBankMap() throws IOException {
		setRenderApproveCancelButtonPanelAlmulla(false);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
	}

	public void checkStatusTypeForBankMap(AdditionalBankRuleDataTable addBankRule) throws IOException {
		if(addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.REMOVE)){
			additionalBankRuleDataList1.remove(addBankRule);
			additionalBankRuleDataNewList1.remove(addBankRule);
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.DEACTIVATE)) {
			addBankRule.setRemarkCheckForBankMap(true);
			setActivateDate(addBankRule.getApproveDate());
			setActivateBy(addBankRule.getApproveBy());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.ACTIVATE)) {
			addBankRule.setActivateRecordCheck(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankMap().equalsIgnoreCase(Constants.DELETE)) {
				addBankRule.setPermanetDeleteCheckForBankMap(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
		}	
		if (additionalBankRuleDataList1.size() == 0) {
			setSaveAdditionalBankRule1(false);
			setAdditionalBankRuleMapRendered(false);
		}

	}
	public void confirmPermanentDeleteBankRuleMap(){
		if(additionalBankRuleDataList1.size()>0){
		for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList1){
			if(addBankRule.getPermanetDeleteCheckForBankMap()!=null){
			if(addBankRule.getPermanetDeleteCheckForBankMap().equals(true)){
				deleteRecordPermanentlyBankRuleMap(addBankRule);
				additionalBankRuleDataList1.remove(addBankRule);
			}
			}
	}
		}
	}
	
	public void deleteRecordPermanentlyBankRuleMap(AdditionalBankRuleDataTable addBankRule){
		additionalBankRuleMapService.deleteBankRuleMapRecord(addBankRule.getAdditionalBankRuleMapPk());
	}
	
	public void activateRecordBankRuleMap(){
		if(additionalBankRuleDataList1.size()>0){
		for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList1){
			if(addBankRule.getActivateRecordCheck()!=null){
			if(addBankRule.getActivateRecordCheck().equals(true)){
				confirmActivateBankRuleMap(addBankRule);
			}
			}
		}
		}
	}
	
	public void confirmActivateBankRuleMap(AdditionalBankRuleDataTable addBankRule){
		additionalBankRuleMapService.activateBankRuleMapRecord(addBankRule.getAdditionalBankRuleMapPk(), sessionStateManage.getUserName());
		getAdditionBankRuleMapRecords();
	}

	public void remarkSelectedRecordForBankMap() throws IOException {
		try{
		for (AdditionalBankRuleDataTable additionalBankMapDT : additionalBankRuleDataList1) {
			if (additionalBankMapDT.getRemarkCheckForBankMap() != null) {
				if (additionalBankMapDT.getRemarkCheckForBankMap().equals(true)) {
					if(getRemarksForBankMap()!=null){
					additionalBankMapDT.setRemarksForBankMap(getRemarksForBankMap());
					additionalBankRuleMapService.remarkBankRuleMapRecord(additionalBankMapDT.getAdditionalBankRuleMapPk(), getRemarksForBankMap(), sessionStateManage.getUserName());
					setRemarksForBankMap(null);
					setActivateDate(null);
					setActivateBy(null);
					getAdditionBankRuleMapRecords();
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
					} catch (Exception e) {
						e.printStackTrace();
					}
					}else{
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;
					}
					
				}
			}
		}
		 }catch(NullPointerException ne){
			  log.info("Method Name::remarkSelectedRecordForBankMap"+ne.getMessage());
			  setErrorMessage("Method Name::remarkSelectedRecordForBankMap"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::remarkSelectedRecordForBankMap "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		
	}

	public void hideSubmitButton() {
		if (getOrderNo() != null || getFieldName() != null) {
			setDisableSubmitButtonForBankMap(true);
		} else {
			setDisableSubmitButtonForBankMap(false);
		}
	}

	public void getAdditionBankRuleMapRecords() {
		clearAllRule1();
		setDisableSubmitButtonForBankMap(false);
		additionalBankRuleDataList1.clear();
		if(getCountryId() != null){
			try{
		List<AdditionalBankRuleMap> additionalBankRuleMapList = additionalBankRuleMapService.toFetchAdditionBankRuleMapRecordsForView(getCountryId());
		if (additionalBankRuleMapList != null && additionalBankRuleMapList.size() > 0) {
			for (AdditionalBankRuleMap additionalBankRuleMap : additionalBankRuleMapList) {
				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.DEACTIVATE);
				} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.ACTIVATE);
				}else if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U) && additionalBankRuleMap.getModifiedBy()==null && additionalBankRuleMap.getModifiedDate()==null
						&& additionalBankRuleMap.getApprovedBy()==null && additionalBankRuleMap.getApprovedDate()==null 
						&& additionalBankRuleMap.getRemarks()==null){
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(Constants.DELETE);
				}else {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankMap(null);
				}
				
				additionalBankRuleDataTable.setAdditionalBankRuleMapPk(additionalBankRuleMap.getAdditionalBankRuleId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getFlexField());
				additionalBankRuleDataTable.setOrderNo(additionalBankRuleMap.getOrderNo());
				additionalBankRuleDataTable.setCreatedBy(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDate(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setIsActive(additionalBankRuleMap.getIsActive());
				additionalBankRuleDataTable.setApproveBy(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setApproveDate(additionalBankRuleMap.getApprovedDate());
				additionalBankRuleDataTable.setModifiedBy(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDate(additionalBankRuleMap.getModifiedDate());
				additionalBankRuleDataTable.setRemarks(additionalBankRuleMap.getRemarks());
				additionalBankRuleDataList1.add(additionalBankRuleDataTable);

			}
			setAdditionalBankRuleMapRendered(true);
			setSaveAdditionalBankRule1(true);

		} else {
			  if(additionalBankRuleDataNewList1 != null && additionalBankRuleDataNewList1.size() ==0){
				    RequestContext.getCurrentInstance().execute("noDataFound.show();");
				    return;
			  }
		}
			 }catch(NullPointerException ne){
				  log.info("Method Name::getAdditionBankRuleMapRecords"+ne.getMessage());
				  setErrorMessage("Method Name::getAdditionBankRuleMapRecords"); 
				    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				    return; 
			} catch (Exception exception) {
				setErrorMessage(exception.getMessage());
				log.info("Method Name::getAdditionBankRuleMapRecords "+exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
		}else {
			  RequestContext.getCurrentInstance().execute("selectCountry.show();");  
			  return;
	  }
		if (additionalBankRuleDataNewList1.size() != 0) {
			additionalBankRuleDataList1.addAll(additionalBankRuleDataNewList1);
		}
	}

	public void checkStatusTypeForAlmullaCode(AdditionalBankRuleDataTable addBankRule) throws IOException {
	if(addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.REMOVE)){
		additionalBankRuleDataList2.remove(addBankRule);
		additionalBankRuleDataNewList2.remove(addBankRule);
	}else if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.DEACTIVATE)) {
		addBankRule.setRemarkCheckForAlmullaCode(true);
		setActivateDate(addBankRule.getApproveDateForAlmullaCode());
		setActivateBy(addBankRule.getApproveByForAlmullaCode());
		RequestContext.getCurrentInstance().execute("remarks.show();");
		return;
	}else if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.ACTIVATE)) {
		addBankRule.setActivateRecordCheckAlmullaCode(true);
		RequestContext.getCurrentInstance().execute("activateRecord.show();");
		return;
	}else if (addBankRule.getDynamicLabelForActivateDeactivateForAlmullaCode().equalsIgnoreCase(Constants.DELETE)) {
			addBankRule.setPermanetDeleteCheckForAlmullaCode(true);
			RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			return;
	}	
	if (additionalBankRuleDataList2.size() == 0) {
		setAdditionalBankRuleAmiecRendered(false);
		setSaveAdditionalBankRule2(false);
	}
}

	public void confirmPermanentDeleteAlmullaCode(){

		if(additionalBankRuleDataList2.size()>0){
		for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList2){
			if(addBankRule.getPermanetDeleteCheckForAlmullaCode()!=null){
			if(addBankRule.getPermanetDeleteCheckForAlmullaCode().equals(true)){
				deleteRecordPermanentlyAlmullaCode(addBankRule);
				additionalBankRuleDataList2.remove(addBankRule);
			}
			}
			}
		}
	}
	public void deleteRecordPermanentlyAlmullaCode(AdditionalBankRuleDataTable addBankRule){
		additionalBankRuleMapService.deleteBankRuleAlmullaCode(addBankRule.getAdditionalBankRulaAlmullaCodePK());
	}
	
public void activateRecordAlmullaCode(){

	if(additionalBankRuleDataList2.size()>0){
	for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList2){
		if(addBankRule.getActivateRecordCheckAlmullaCode()!=null){
		if(addBankRule.getActivateRecordCheckAlmullaCode().equals(true)){
			confirmActivateAlmullaCode(addBankRule);
		}
		}
	}
	}
}
public void confirmActivateAlmullaCode(AdditionalBankRuleDataTable addBankRule){
	additionalBankRuleMapService.activateBankRuleAlmullaCode(addBankRule.getAdditionalBankRulaAlmullaCodePK(), sessionStateManage.getUserName());
	getAdditionBankAlmullaCodeRecords();
}

	public void remarkSelectedRecordForAlmullaCode() {
		try{
		for (AdditionalBankRuleDataTable additionalBankMapDT : additionalBankRuleDataList2) {
			if (additionalBankMapDT.getRemarkCheckForAlmullaCode() != null) {
				if (additionalBankMapDT.getRemarkCheckForAlmullaCode().equals(true)) {
					if(getRemarksForAlmullaCode()!=null){
					additionalBankMapDT.setRemarksForAlmullaCode(getRemarksForAlmullaCode());
					additionalBankRuleMapService.remarkBankRuleAlmullaCode(additionalBankMapDT.getAdditionalBankRulaAlmullaCodePK(), getRemarksForAlmullaCode(), sessionStateManage.getUserName());
					setRemarksForAlmullaCode(null);
					setActivateDate(null);
					setActivateBy(null);
					getAdditionBankAlmullaCodeRecords();
			
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
					} catch (Exception e) {
						e.printStackTrace();
					}	
					}else{
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;
					}
				}
			}
		}
		
		 }catch(NullPointerException ne){
			  log.info("Method Name::remarkSelectedRecordForAlmullaCode"+ne.getMessage());
			  setErrorMessage("Method Name::remarkSelectedRecordForAlmullaCode"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::remarkSelectedRecordForAlmullaCode "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		
	}

	public void hideSubmitButtonForAlmullaCode() {
		setAmiecCode(null);
		setAmiecDescription(null);
		if (getAmiecDescription() != null) {
			setDisableSubmitButtonForAlmullaCode(true);
		} else {
			setDisableSubmitButtonForAlmullaCode(false);
		}
	}

	public void getAdditionBankAlmullaCodeRecords() {
		if(getCountryId()!=null&&getFlexField()!=null){
		clearAllRule2();
		setBooclearfanel(false);
		setDisableSubmitButtonForAlmullaCode(false);
		setBooEditableRecord(false);
		setDisableEditBankWithAlmullaCode(false);
		additionalBankRuleDataList2.clear();
		try{
		List<AdditionalBankRuleAmiec> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankAlmullaCodeRecordsForView(getCountryId(),getFlexField());
		if (additionalBankAlmullaCodeList.size() > 0) {
			for (AdditionalBankRuleAmiec additionalBankRuleMap : additionalBankAlmullaCodeList) {
				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				
				if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.DEACTIVATE);
				} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.ACTIVATE);
				} else if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U) && additionalBankRuleMap.getModifiedBy()==null && additionalBankRuleMap.getModifiedDate()==null
						&& additionalBankRuleMap.getApprovedBy()==null && additionalBankRuleMap.getApprovedDate()==null 
						&& additionalBankRuleMap.getRemarks()==null){
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.DELETE);
				}else {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForAlmullaCode(Constants.PENDING_FOR_APPROVE);
				}
				
				additionalBankRuleDataTable.setAdditionalBankRulaAlmullaCodePK(additionalBankRuleMap.getAdditionalBankRuleDetailId());
				additionalBankRuleDataTable.setAdditionalBankFieldId(additionalBankRuleMap.getAdditionalBankFieldId().getAdditionalBankRuleId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
				popFlex();
				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getAdditionalBankFieldId().getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getAdditionalBankFieldId().getFlexField());
				additionalBankRuleDataTable.setAmiecCode(additionalBankRuleMap.getAmiecCode());
				additionalBankRuleDataTable.setAmiecDescription(additionalBankRuleMap.getAmiecDescription());
				additionalBankRuleDataTable.setCreatedByForAlmullaCode(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDateForAlmullaCode(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setModifiedByForAlmullaCode(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDateForAlmullaCode(additionalBankRuleMap.getModifiedDate());
				additionalBankRuleDataTable.setApproveByForAlmullaCode(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setApproveDateForAlmullaCode(additionalBankRuleMap.getApprovedDate());
				additionalBankRuleDataTable.setRemarksForAlmullaCode(additionalBankRuleMap.getRemarks());
				additionalBankRuleDataTable.setIsActiveForAlmullaCode(additionalBankRuleMap.getIsActive());
				additionalBankRuleDataList2.add(additionalBankRuleDataTable);

			}
			setAdditionalBankRuleAmiecRendered(true);
			setSaveAdditionalBankRule2(true);

		} else {
			RequestContext.getCurrentInstance().execute("recordnot.show();");
			
		}
		 }catch(NullPointerException ne){
			  log.info("Method Name::getAdditionBankAlmullaCodeRecords"+ne.getMessage());
			  setErrorMessage("Method Name::getAdditionBankAlmullaCodeRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getAdditionBankAlmullaCodeRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		if (additionalBankRuleDataNewList2.size() != 0) {
			additionalBankRuleDataList2.addAll(additionalBankRuleDataNewList2);
		}
		
		}else{	RequestContext.getCurrentInstance().execute("pleaseselectcountry.show();");}
	}

	public void checkStatusTypeForBankRule(AdditionalBankRuleDataTable addBankRule) throws IOException {

		if(addBankRule.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.REMOVE)){
			additionalBankRuleDataList3.remove(addBankRule);
			additionalBankRuleDataNewList3.remove(addBankRule);
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.DEACTIVATE)) {
			addBankRule.setRemarkCheckForBankRule(true);
			setActivateDate(addBankRule.getApproveDateForBankRule());
			setActivateBy(addBankRule.getApproveByForBankRule());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.ACTIVATE)) {
			addBankRule.setActivateRecordCheckBankRule(true);
			RequestContext.getCurrentInstance().execute("activateRecord.show();");
			return;
		}else if (addBankRule.getDynamicLabelForActivateDeactivateForBankRule().equalsIgnoreCase(Constants.DELETE)) {
				addBankRule.setPermanetDeleteCheckForBankRule(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
		}	
		if (additionalBankRuleDataList3.size() == 0) {
			setSaveAdditionalBankRule3(false);
			setAdditionalBankRuleAddDataRendered(false);
		}
	}
public void activateRecordBankRule(){
	try{
	if(additionalBankRuleDataList3.size()>0){
	for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList3){
		if(addBankRule.getActivateRecordCheckBankRule()!=null){
		if(addBankRule.getActivateRecordCheckBankRule().equals(true)){
			confirmActivateBankRule(addBankRule);
		}
		}
	}
	}
	 }catch(NullPointerException ne){
		  log.info("Method Name::activateRecordBankRule"+ne.getMessage());
		  setErrorMessage("Method Name::activateRecordBankRule"); 
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
	} catch (Exception exception) {
		setErrorMessage(exception.getMessage());
		log.info("Method Name::activateRecordBankRule "+exception.getMessage());
		RequestContext.getCurrentInstance().execute("exception.show();");
		return;
	}
}

public void confirmActivateBankRule(AdditionalBankRuleDataTable addBankRule){
	additionalBankRuleMapService.activateBankRule(addBankRule.getAdditionalBankRulePK(), sessionStateManage.getUserName());
	getAdditionBankRuleRecords();
	
}
public void confirmPermanentDeleteBankRule(){
	if(additionalBankRuleDataList3.size()>0){
	for(AdditionalBankRuleDataTable addBankRule:additionalBankRuleDataList3){
		if(addBankRule.getPermanetDeleteCheckForBankRule()!=null){
		if(addBankRule.getPermanetDeleteCheckForBankRule().equals(true)){
			deleteRecordPermanentlyBankRule(addBankRule);
			additionalBankRuleDataList3.remove(addBankRule);
		}
		}
		}
	}
}

public void deleteRecordPermanentlyBankRule(AdditionalBankRuleDataTable addBankRule){
	additionalBankRuleMapService.deleteBankRule(addBankRule.getAdditionalBankRulePK());
}
	public void remarkSelectedRecordForBankRule(){
		try{
		for (AdditionalBankRuleDataTable additionalBankMapDT : additionalBankRuleDataList3) {
			if (additionalBankMapDT.getRemarkCheckForBankRule() != null) {
				if (additionalBankMapDT.getRemarkCheckForBankRule().equals(true)) {
					if(getRemarksForBankRule()!=null){
					additionalBankRuleMapService.remarkBankRule(additionalBankMapDT.getAdditionalBankRulePK(), getRemarksForBankRule(), sessionStateManage.getUserName());
					setRemarksForBankRule(null);
					setActivateDate(null);
					setActivateBy(null);
					getAdditionBankRuleRecords();
					try {
						FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
					} catch (IOException e) {
						e.printStackTrace();
					}
					}else{
						RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
						return;
					}
					
				}
			}
		}
		
		 }catch(NullPointerException ne){
			  log.info("Method Name::remarkSelectedRecordForBankRule"+ne.getMessage());
			  setErrorMessage("Method Name::remarkSelectedRecordForBankRule"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::remarkSelectedRecordForBankRule "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void hideSubmitButtonForBankRule() {
		if (getAmiecDescription() != null) {
			setDisableSubmitButtonForAlmullaCode(true);
		} else {
			setDisableSubmitButtonForAlmullaCode(false);
		}
	}

	public void getAdditionBankRuleRecords() {
		if(getCountryId()!=null&&getFlexField()!=null&&getBankId()!=null){
		additionalBankRuleDataList3.clear();
		clearAllRule3();
		setDisableEditBankRule(false);
		setDisableSubmitButtonForBankRule(false);
		setBooclearPanelForBankRule(false);
		try{
		List<AdditionalBankRuleAddData> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankRuleRecordsForView(getCountryId(),getFlexField(),getBankId());
		if (additionalBankAlmullaCodeList.size() > 0) {
			for (AdditionalBankRuleAddData additionalBankRuleMap : additionalBankAlmullaCodeList) {

				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();

				if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.DEACTIVATE);
				} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.ACTIVATE);
				}else if(additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.U)&& additionalBankRuleMap.getModifiedBy()==null && additionalBankRuleMap.getModifiedDate()==null
						&& additionalBankRuleMap.getApprovedBy()==null && additionalBankRuleMap.getApprovedDate()==null 
						&& additionalBankRuleMap.getRemarks()==null){
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.DELETE);
				}else {
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(null);
				}
				
				additionalBankRuleDataTable.setAdditionalBankRulePK(additionalBankRuleMap.getAdditionalBankRuleDataId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getAdditionalBankFieldId().getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getAdditionalBankFieldId().getFlexField());
				additionalBankRuleDataTable.setBankId(additionalBankRuleMap.getBankId().getBankId());
				additionalBankRuleDataTable.setBankName(additionalBankRuleMap.getBankId().getBankFullName());
				additionalBankRuleDataTable.setBankCode(additionalBankRuleMap.getAdditionalData());
				additionalBankRuleDataTable.setBankDescription(additionalBankRuleMap.getAdditionalDescription());
				additionalBankRuleDataTable.setCreatedByForBankRule(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDateForBankRule(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setModifiedByForBankRule(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDateForBankRule(additionalBankRuleMap.getModifiedDate());
				additionalBankRuleDataTable.setApproveDateForBankRule(additionalBankRuleMap.getApprovedDate());
				additionalBankRuleDataTable.setApproveByForBankRule(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setIsActiveForBankRule(additionalBankRuleMap.getIsActive());
				additionalBankRuleDataTable.setRemarksForBankRule(additionalBankRuleMap.getRemarks());
				additionalBankRuleDataTable.setAdditionalBankFieldIdForBankRule(additionalBankRuleMap.getAdditionalBankFieldId().getAdditionalBankRuleId());
				additionalBankRuleDataList3.add(additionalBankRuleDataTable);
			}

			setAdditionalBankRuleAddDataRendered(true);
			setSaveAdditionalBankRule3(true);

		} else {
			RequestContext.getCurrentInstance().execute("recordnotfounded.show();");
			return;
		}
		 }catch(NullPointerException ne){
			  log.info("Method Name::getAdditionBankRuleRecords"+ne.getMessage());
			  setErrorMessage("Method Name::getAdditionBankRuleRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getAdditionBankRuleRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		if (additionalBankRuleDataNewList3.size() != 0) {
			additionalBankRuleDataList3.addAll(additionalBankRuleDataNewList3);
		}
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleadditionaldata.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		}else{RequestContext.getCurrentInstance().execute("pleaseselect.show();");}
	}

	public void getAdditionBankRuleMapEnquiryRecords() {

		additionalBankRuleDataList1.clear();
		try{
		List<AdditionalBankRuleMap> additionalBankRuleMapList = additionalBankRuleMapService
				.getAdditionBankRuleMapRecordsForEnquiry();
		if (additionalBankRuleMapList.size() > 0) {
			for (AdditionalBankRuleMap additionalBankRuleMap : additionalBankRuleMapList) {

				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				additionalBankRuleDataTable.setAdditionalBankRuleMapPk(additionalBankRuleMap.getAdditionalBankRuleId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),additionalBankRuleMap.getCountryId().getCountryId()));

				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getFlexField());
				additionalBankRuleDataTable.setOrderNo(additionalBankRuleMap.getOrderNo());
				additionalBankRuleDataTable.setCreatedBy(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDate(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setIsActive(additionalBankRuleMap.getIsActive());
				additionalBankRuleDataTable.setApproveBy(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setApproveDate(additionalBankRuleMap.getApprovedDate());
				additionalBankRuleDataTable.setModifiedBy(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDate(additionalBankRuleMap.getModifiedDate());

				if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {

					setActiveStatusInWord("Approved");
					additionalBankRuleDataTable.setActiveStatusInWord("Approved");

				} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {

					additionalBankRuleDataTable.setActiveStatusInWord(Constants.DELETE);
					setActiveStatusInWord("Approved");

				} else {
					additionalBankRuleDataTable.setActiveStatusInWord("Un-Approved");
					setActiveStatusInWord("Un-Approved");
				}
				additionalBankRuleDataList1.add(additionalBankRuleDataTable);
			}
			setAdditionalBankRuleMapRendered(true);
			setSaveAdditionalBankRule1(true);

		} else {
			RequestContext.getCurrentInstance().execute("noDataFound.show();");
		}
		if (additionalBankRuleDataNewList1.size() != 0) {
			additionalBankRuleDataList1.addAll(additionalBankRuleDataNewList1);
		}
		
		}catch(NullPointerException ne){
			  log.info("Method Name::getAdditionBankRuleMapEnquiryRecords"+ne.getMessage());
			  setErrorMessage("Method Name::getAdditionBankRuleMapEnquiryRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getAdditionBankRuleMapEnquiryRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}

	public void getAdditionBankAlmullaCodeEnquiryRecords() {

		additionalBankRuleDataList2.clear();
       try{
		List<AdditionalBankRuleAmiec> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankAlmullaCodeRecordsForEnquiry();

		if (additionalBankAlmullaCodeList.size() > 0) {
			for (AdditionalBankRuleAmiec additionalBankRuleMap : additionalBankAlmullaCodeList) {

				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();
				additionalBankRuleDataTable.setAdditionalBankRulaAlmullaCodePK(additionalBankRuleMap.getAdditionalBankRuleDetailId());
				additionalBankRuleDataTable.setAdditionalBankFieldId(additionalBankRuleMap.getAdditionalBankFieldId().getAdditionalBankRuleId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				// additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),additionalBankRuleMap.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getAdditionalBankFieldId().getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getAdditionalBankFieldId().getFlexField());
				additionalBankRuleDataTable.setAmiecCode(additionalBankRuleMap.getAmiecCode());
				additionalBankRuleDataTable.setAmiecDescription(additionalBankRuleMap.getAmiecDescription());
				additionalBankRuleDataTable.setCreatedByForAlmullaCode(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDateForAlmullaCode(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setModifiedByForAlmullaCode(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDateForAlmullaCode(additionalBankRuleMap.getModifiedDate());
				additionalBankRuleDataTable.setApproveByForAlmullaCode(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setApproveDateForAlmullaCode(additionalBankRuleMap.getApprovedDate());

				if (additionalBankRuleMap.getIsActive() != null) {

					if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(
							 Constants.Yes)) {

						setActiveStatusInWord("Approved");
						additionalBankRuleDataTable.setActiveStatusInWord("Approved");

					} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {

						additionalBankRuleDataTable.setActiveStatusInWord(Constants.DELETE);
						setActiveStatusInWord("Approved");

					} else {
						additionalBankRuleDataTable.setActiveStatusInWord("Un-Approved");
						setActiveStatusInWord("Un-Approved");
					}
				}

				additionalBankRuleDataList2.add(additionalBankRuleDataTable);

			}
			setAdditionalBankRuleAmiecRendered(true);
			setSaveAdditionalBankRule2(true);

		} else {
			RequestContext.getCurrentInstance().execute("noDataFound.show();");
		}
		if (additionalBankRuleDataNewList2.size() != 0) {
			additionalBankRuleDataList2.addAll(additionalBankRuleDataNewList2);
		}
       }catch(NullPointerException ne){
			  log.info("Method Name::getAdditionBankAlmullaCodeEnquiryRecords"+ne.getMessage());
			  setErrorMessage("Method Name::getAdditionBankAlmullaCodeEnquiryRecords"); 
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
		} catch (Exception exception) {
			setErrorMessage(exception.getMessage());
			log.info("Method Name::getAdditionBankAlmullaCodeEnquiryRecords "+exception.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}

	public void getAdditionBankRuleEnquiryRecords() {

		additionalBankRuleDataList3.clear();
		List<AdditionalBankRuleAddData> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankRuleRecordsForEnquiry();
		if (additionalBankAlmullaCodeList.size() > 0) {
			for (AdditionalBankRuleAddData additionalBankRuleMap : additionalBankAlmullaCodeList) {

				AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();

				additionalBankRuleDataTable.setAdditionalBankRulePK(additionalBankRuleMap.getAdditionalBankRuleDataId());
				additionalBankRuleDataTable.setCountryId(additionalBankRuleMap.getCountryId().getCountryId());
				// additionalBankRuleDataTable.setCountryName(generalService.getCountryName(additionalBankRuleMap.getCountryId().getCountryId()));
				additionalBankRuleDataTable.setCountryName(generalService.getCountryName(sessionStateManage.getLanguageId(),additionalBankRuleMap.getCountryId().getCountryId()));

				additionalBankRuleDataTable.setFieldName(additionalBankRuleMap.getAdditionalBankFieldId().getFieldName());
				additionalBankRuleDataTable.setFlexField(additionalBankRuleMap.getAdditionalBankFieldId().getFlexField());
				additionalBankRuleDataTable.setBankId(additionalBankRuleMap.getBankId().getBankId());
				additionalBankRuleDataTable.setBankName(additionalBankRuleMap.getBankId().getBankFullName());
				additionalBankRuleDataTable.setBankCode(additionalBankRuleMap.getAdditionalData());
				additionalBankRuleDataTable.setBankDescription(additionalBankRuleMap.getAdditionalDescription());
				additionalBankRuleDataTable.setCreatedByForBankRule(additionalBankRuleMap.getCreatedBy());
				additionalBankRuleDataTable.setCreatedDateForBankRule(additionalBankRuleMap.getCreatedDate());
				additionalBankRuleDataTable.setModifiedByForBankRule(additionalBankRuleMap.getModifiedBy());
				additionalBankRuleDataTable.setModifiedDateForBankRule(additionalBankRuleMap.getModifiedDate());
				additionalBankRuleDataTable.setApproveDateForBankRule(additionalBankRuleMap.getApprovedDate());
				additionalBankRuleDataTable.setApproveByForBankRule(additionalBankRuleMap.getApprovedBy());
				additionalBankRuleDataTable.setIsActiveForBankRule(additionalBankRuleMap.getIsActive());
				additionalBankRuleDataTable.setAdditionalBankFieldIdForBankRule(additionalBankRuleMap.getAdditionalBankFieldId().getAdditionalBankRuleId());

				if (additionalBankRuleMap.getIsActive() != null) {

					if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.Yes)) {

						setActiveStatusInWord("Approved");
						additionalBankRuleDataTable.setActiveStatusInWord("Approved");

					} else if (additionalBankRuleMap.getIsActive().equalsIgnoreCase(Constants.D)) {

						additionalBankRuleDataTable.setActiveStatusInWord(Constants.DELETE);
						setActiveStatusInWord("Approved");

					} else {
						additionalBankRuleDataTable.setActiveStatusInWord("Un-Approved");
						setActiveStatusInWord("Un-Approved");
					}
				}

				additionalBankRuleDataList3.add(additionalBankRuleDataTable);
			}

			setAdditionalBankRuleAddDataRendered(true);
			setSaveAdditionalBankRule3(true);

		} else {
			RequestContext.getCurrentInstance().execute("noDataFound.show();");
		}
		if (additionalBankRuleDataNewList3.size() != 0) {
			additionalBankRuleDataList3.addAll(additionalBankRuleDataNewList3);
		}

	}

	// ***********************************************PROPERTIES
	// *****************************

	public void setLstBank(List<BankMaster> bankList) {
		this.bankList = bankList;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public void setAdditionalBankRuleId(BigDecimal additionalBankRuleId) {
		this.additionalBankRuleId = additionalBankRuleId;
	}

	public BigDecimal getAdditionalBankRuleId() {
		return additionalBankRuleId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFlexField() {
		return flexField;
	}

	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}

	public BigDecimal getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(BigDecimal orderNo) {
		this.orderNo = orderNo;
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

	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getAdditionalBankRuleDetailId() {
		return additionalBankRuleDetailId;
	}

	public void setAdditionalBankRuleDetailId(
			BigDecimal additionalBankRuleDetailId) {
		this.additionalBankRuleDetailId = additionalBankRuleDetailId;
	}

	public String getAmiecCode() {
		return amiecCode;
	}

	public void setAmiecCode(String amiecCode) {
		this.amiecCode = amiecCode;
	}

	public String getAmiecDescription() {
		return amiecDescription;
	}

	public void setAmiecDescription(String amiecDescription) {
		this.amiecDescription = amiecDescription;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getAdditionalData() {
		return additionalData;
	}

	public void setAdditionalData(String additionalData) {
		this.additionalData = additionalData;
	}

	public String getAdditionalDescription() {
		return additionalDescription;
	}

	public void setAdditionalDescription(String additionalDescription) {
		this.additionalDescription = additionalDescription;
	}

	public String getdBflexField() {
		return dBflexField;
	}

	public void setdBflexField(String dBflexField) {
		this.dBflexField = dBflexField;
	}

	public Boolean getBooDBFlexField() {
		return booDBFlexField;
	}

	public void setBooDBFlexField(Boolean booDBFlexField) {
		this.booDBFlexField = booDBFlexField;
	}

	public Boolean getBooNewFlexField() {
		return booNewFlexField;
	}

	public void setBooNewFlexField(Boolean booNewFlexField) {
		this.booNewFlexField = booNewFlexField;
	}

	public String getDupRecord() {
		return dupRecord;
	}

	public void setDupRecord(String dupRecord) {
		this.dupRecord = dupRecord;
	}

	public Boolean getEditButton() {
		return editButton;
	}

	public void setEditButton(Boolean editButton) {
		this.editButton = editButton;
	}

	public List<AdditionalBankRuleMap> getLstFlexFiledDB() {
		return lstFlexFiledDB;
	}

	public void setLstFlexFiledDB(List<AdditionalBankRuleMap> lstFlexFiledDB) {
		this.lstFlexFiledDB = lstFlexFiledDB;
	}

	public List<AdditionalBankRuleMap> getLstAllRecordsDB() {
		return lstAllRecordsDB;
	}

	public void setLstAllRecordsDB(List<AdditionalBankRuleMap> lstAllRecordsDB) {
		this.lstAllRecordsDB = lstAllRecordsDB;
	}

	public BigDecimal getAdditionabankrulemapPK() {
		return additionabankrulemapPK;
	}

	public void setAdditionabankrulemapPK(BigDecimal additionabankrulemapPK) {
		this.additionabankrulemapPK = additionabankrulemapPK;
	}

	public Boolean getDeleteIsActive() {
		return deleteIsActive;
	}

	public void setDeleteIsActive(Boolean deleteIsActive) {
		this.deleteIsActive = deleteIsActive;
	}

	public List<AdditionalBankRuleAmiec> getLstAllDBcountryFlex() {
		return lstAllDBcountryFlex;
	}

	public void setLstAllDBcountryFlex(
			List<AdditionalBankRuleAmiec> lstAllDBcountryFlex) {
		this.lstAllDBcountryFlex = lstAllDBcountryFlex;
	}

	public List<AdditionalBankRuleAddData> getLstAllDBcountryFlexBank() {
		return lstAllDBcountryFlexBank;
	}

	public void setLstAllDBcountryFlexBank(
			List<AdditionalBankRuleAddData> lstAllDBcountryFlexBank) {
		this.lstAllDBcountryFlexBank = lstAllDBcountryFlexBank;
	}

	public Boolean getRenderApproveCancelButtonPanelAlmulla() {
		return renderApproveCancelButtonPanelAlmulla;
	}

	public void setRenderApproveCancelButtonPanelAlmulla(
			Boolean renderApproveCancelButtonPanelAlmulla) {
		this.renderApproveCancelButtonPanelAlmulla = renderApproveCancelButtonPanelAlmulla;
	}

	public String getCreatedByAlmulla() {
		return createdByAlmulla;
	}

	public void setCreatedByAlmulla(String createdByAlmulla) {
		this.createdByAlmulla = createdByAlmulla;
	}

	public BigDecimal getAlmullaCodePK() {
		return almullaCodePK;
	}

	public void setAlmullaCodePK(BigDecimal almullaCodePK) {
		this.almullaCodePK = almullaCodePK;
	}

	public Boolean getRenderCountryIdAlmulla() {
		return renderCountryIdAlmulla;
	}

	public void setRenderCountryIdAlmulla(Boolean renderCountryIdAlmulla) {
		this.renderCountryIdAlmulla = renderCountryIdAlmulla;
	}

	public Boolean getRenderCountryIdForApproveAlmulla() {
		return renderCountryIdForApproveAlmulla;
	}

	public void setRenderCountryIdForApproveAlmulla(
			Boolean renderCountryIdForApproveAlmulla) {
		this.renderCountryIdForApproveAlmulla = renderCountryIdForApproveAlmulla;
	}

	public String getCountryNameAlmulla() {
		return countryNameAlmulla;
	}

	public void setCountryNameAlmulla(String countryNameAlmulla) {
		this.countryNameAlmulla = countryNameAlmulla;
	}

	public Boolean getRenderFlexFieldForApproveAlmulla() {
		return renderFlexFieldForApproveAlmulla;
	}

	public void setRenderFlexFieldForApproveAlmulla(
			Boolean renderFlexFieldForApproveAlmulla) {
		this.renderFlexFieldForApproveAlmulla = renderFlexFieldForApproveAlmulla;
	}

	public Boolean getRenderFlexFieldAlmulla() {
		return renderFlexFieldAlmulla;
	}

	public void setRenderFlexFieldAlmulla(Boolean renderFlexFieldAlmulla) {
		this.renderFlexFieldAlmulla = renderFlexFieldAlmulla;
	}

	public String getFlexFieldNameAlmulla() {
		return flexFieldNameAlmulla;
	}

	public void setFlexFieldNameAlmulla(String flexFieldNameAlmulla) {
		this.flexFieldNameAlmulla = flexFieldNameAlmulla;
	}

	public String getAlmullaCodeAlmulla() {
		return almullaCodeAlmulla;
	}

	public void setAlmullaCodeAlmulla(String almullaCodeAlmulla) {
		this.almullaCodeAlmulla = almullaCodeAlmulla;
	}

	public Boolean getRenderAlmullaCodeForApproveAlmulla() {
		return renderAlmullaCodeForApproveAlmulla;
	}

	public void setRenderAlmullaCodeForApproveAlmulla(
			Boolean renderAlmullaCodeForApproveAlmulla) {
		this.renderAlmullaCodeForApproveAlmulla = renderAlmullaCodeForApproveAlmulla;
	}

	public Boolean getRenderAlmullaCodeAlmulla() {
		return renderAlmullaCodeAlmulla;
	}

	public void setRenderAlmullaCodeAlmulla(Boolean renderAlmullaCodeAlmulla) {
		this.renderAlmullaCodeAlmulla = renderAlmullaCodeAlmulla;
	}

	public String getAlmullaDescAlmulla() {
		return almullaDescAlmulla;
	}

	public void setAlmullaDescAlmulla(String almullaDescAlmulla) {
		this.almullaDescAlmulla = almullaDescAlmulla;
	}

	public Boolean getRenderAlmullaDescAlmulla() {
		return renderAlmullaDescAlmulla;
	}

	public void setRenderAlmullaDescAlmulla(Boolean renderAlmullaDescAlmulla) {
		this.renderAlmullaDescAlmulla = renderAlmullaDescAlmulla;
	}

	public Boolean getRenderAlmullaDescForApproveAlmulla() {
		return renderAlmullaDescForApproveAlmulla;
	}

	public void setRenderAlmullaDescForApproveAlmulla(
			Boolean renderAlmullaDescForApproveAlmulla) {
		this.renderAlmullaDescForApproveAlmulla = renderAlmullaDescForApproveAlmulla;
	}

	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataList1() {
		return additionalBankRuleDataList1;
	}

	public void setAdditionalBankRuleDataList1(
			List<AdditionalBankRuleDataTable> additionalBankRuleDataList1) {
		this.additionalBankRuleDataList1 = additionalBankRuleDataList1;
	}

	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataList2() {
		return additionalBankRuleDataList2;
	}

	public void setAdditionalBankRuleDataList2(
			List<AdditionalBankRuleDataTable> additionalBankRuleDataList2) {
		this.additionalBankRuleDataList2 = additionalBankRuleDataList2;
	}

	public List<AdditionalBankRuleDataTable> getAdditionalBankRuleDataList3() {
		return additionalBankRuleDataList3;
	}

	public void setAdditionalBankRuleDataList3(
			List<AdditionalBankRuleDataTable> additionalBankRuleDataList3) {
		this.additionalBankRuleDataList3 = additionalBankRuleDataList3;
	}

	public boolean isAdditionalBankRuleMapRendered() {
		return additionalBankRuleMapRendered;
	}

	public void setAdditionalBankRuleMapRendered(
			boolean additionalBankRuleMapRendered) {
		this.additionalBankRuleMapRendered = additionalBankRuleMapRendered;
	}

	public boolean isAdditionalBankRuleAmiecRendered() {
		return additionalBankRuleAmiecRendered;
	}

	public void setAdditionalBankRuleAmiecRendered(
			boolean additionalBankRuleAmiecRendered) {
		this.additionalBankRuleAmiecRendered = additionalBankRuleAmiecRendered;
	}

	public boolean isAdditionalBankRuleAddDataRendered() {
		return additionalBankRuleAddDataRendered;
	}

	public void setAdditionalBankRuleAddDataRendered(
			boolean additionalBankRuleAddDataRendered) {
		this.additionalBankRuleAddDataRendered = additionalBankRuleAddDataRendered;
	}

	public boolean isSaveAdditionalBankRule1() {
		return saveAdditionalBankRule1;
	}

	public void setSaveAdditionalBankRule1(boolean saveAdditionalBankRule1) {
		this.saveAdditionalBankRule1 = saveAdditionalBankRule1;
	}

	public boolean isSaveAdditionalBankRule2() {
		return saveAdditionalBankRule2;
	}

	public void setSaveAdditionalBankRule2(boolean saveAdditionalBankRule2) {
		this.saveAdditionalBankRule2 = saveAdditionalBankRule2;
	}

	public boolean isSaveAdditionalBankRule3() {
		return saveAdditionalBankRule3;
	}

	public void setSaveAdditionalBankRule3(boolean saveAdditionalBankRule3) {
		this.saveAdditionalBankRule3 = saveAdditionalBankRule3;
	}

	public Boolean getBooRenderSaveOne() {
		return booRenderSaveOne;
	}

	public void setBooRenderSaveOne(Boolean booRenderSaveOne) {
		this.booRenderSaveOne = booRenderSaveOne;
	}

	public Boolean getBooRenderSaveExit() {
		return booRenderSaveExit;
	}

	public void setBooRenderSaveExit(Boolean booRenderSaveExit) {
		this.booRenderSaveExit = booRenderSaveExit;
	}

	public Boolean getBooRenderSave() {
		return booRenderSave;
	}

	public void setBooRenderSave(Boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}

	public List<AdditionalBankRuleMap> getAdditionalBankRuleMaps() {
		
		return additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
	}

	public void setAdditionalBankRuleMaps(
			List<AdditionalBankRuleMap> additionalBankRuleMaps) {
		this.additionalBankRuleMaps = additionalBankRuleMaps;
	}

	public List<AdditionalBankRuleMap> getAddingtoDataTable() {
		return addingtoDataTable;
	}

	public void setAddingtoDataTable(
			List<AdditionalBankRuleMap> addingtoDataTable) {
		this.addingtoDataTable = addingtoDataTable;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getFlexFieldName() {
		return flexFieldName;
	}

	public void setFlexFieldName(String flexFieldName) {
		this.flexFieldName = flexFieldName;
	}

	public String getCreatedByForApprove() {
		return createdByForApprove;
	}

	public void setCreatedByForApprove(String createdByForApprove) {
		this.createdByForApprove = createdByForApprove;
	}

	public BigDecimal getAddtionBankRulePK() {
		return addtionBankRulePK;
	}

	public void setAddtionBankRulePK(BigDecimal addtionBankRulePK) {
		this.addtionBankRulePK = addtionBankRulePK;
	}

	public Boolean getRenderAddButtonPanel() {
		return renderAddButtonPanel;
	}

	public void setRenderAddButtonPanel(Boolean renderAddButtonPanel) {
		this.renderAddButtonPanel = renderAddButtonPanel;
	}

	public Boolean getRenderApproveCancelButtonPanel() {
		return renderApproveCancelButtonPanel;
	}

	public void setRenderApproveCancelButtonPanel(
			Boolean renderApproveCancelButtonPanel) {
		this.renderApproveCancelButtonPanel = renderApproveCancelButtonPanel;
	}

	public Boolean getRenderCountryIdForApprove() {
		return renderCountryIdForApprove;
	}

	public void setRenderCountryIdForApprove(Boolean renderCountryIdForApprove) {
		this.renderCountryIdForApprove = renderCountryIdForApprove;
	}

	public Boolean getRenderCountryId() {
		return renderCountryId;
	}

	public void setRenderCountryId(Boolean renderCountryId) {
		this.renderCountryId = renderCountryId;
	}

	public Boolean getRenderFlexField() {
		return renderFlexField;
	}

	public void setRenderFlexField(Boolean renderFlexField) {
		this.renderFlexField = renderFlexField;
	}

	public Boolean getRenderFlexFieldForApprove() {
		return renderFlexFieldForApprove;
	}

	public void setRenderFlexFieldForApprove(Boolean renderFlexFieldForApprove) {
		this.renderFlexFieldForApprove = renderFlexFieldForApprove;
	}

	public Boolean getRenderBankId() {
		return renderBankId;
	}

	public void setRenderBankId(Boolean renderBankId) {
		this.renderBankId = renderBankId;
	}

	public Boolean getRenderBankIdForApprove() {
		return renderBankIdForApprove;
	}

	public void setRenderBankIdForApprove(Boolean renderBankIdForApprove) {
		this.renderBankIdForApprove = renderBankIdForApprove;
	}

	public Boolean getRenderBankCode() {
		return renderBankCode;
	}

	public void setRenderBankCode(Boolean renderBankCode) {
		this.renderBankCode = renderBankCode;
	}

	public Boolean getRenderBankCodeForApprove() {
		return renderBankCodeForApprove;
	}

	public void setRenderBankCodeForApprove(Boolean renderBankCodeForApprove) {
		this.renderBankCodeForApprove = renderBankCodeForApprove;
	}

	public Boolean getRenderBankDesc() {
		return renderBankDesc;
	}

	public void setRenderBankDesc(Boolean renderBankDesc) {
		this.renderBankDesc = renderBankDesc;
	}

	public Boolean getRenderBankDescForApprove() {
		return renderBankDescForApprove;
	}

	public void setRenderBankDescForApprove(Boolean renderBankDescForApprove) {
		this.renderBankDescForApprove = renderBankDescForApprove;
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

	public String getApproveBy() {
		return approveBy;
	}

	public void setApproveBy(String approveBy) {
		this.approveBy = approveBy;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public BigDecimal getAddtionalBankRuleMapPk() {
		return addtionalBankRuleMapPk;
	}

	public void setAddtionalBankRuleMapPk(BigDecimal addtionalBankRuleMapPk) {
		this.addtionalBankRuleMapPk = addtionalBankRuleMapPk;
	}

	public String getCreatedByForBankRuleMap() {
		return createdByForBankRuleMap;
	}

	public void setCreatedByForBankRuleMap(String createdByForBankRuleMap) {
		this.createdByForBankRuleMap = createdByForBankRuleMap;
	}

	public Boolean getRenderCountryIdBankMapForApprove() {
		return renderCountryIdBankMapForApprove;
	}

	public void setRenderCountryIdBankMapForApprove(
			Boolean renderCountryIdBankMapForApprove) {
		this.renderCountryIdBankMapForApprove = renderCountryIdBankMapForApprove;
	}

	public Boolean getRenderCountryIdBankMap() {
		return renderCountryIdBankMap;
	}

	public void setRenderCountryIdBankMap(Boolean renderCountryIdBankMap) {
		this.renderCountryIdBankMap = renderCountryIdBankMap;
	}

	public String getCountryNameForBankMap() {
		return countryNameForBankMap;
	}

	public void setCountryNameForBankMap(String countryNameForBankMap) {
		this.countryNameForBankMap = countryNameForBankMap;
	}

	public Boolean getRenderFlexFieldBankMapForApprove() {
		return renderFlexFieldBankMapForApprove;
	}

	public void setRenderFlexFieldBankMapForApprove(
			Boolean renderFlexFieldBankMapForApprove) {
		this.renderFlexFieldBankMapForApprove = renderFlexFieldBankMapForApprove;
	}

	public Boolean getRenderFlexFieldBankMap() {
		return renderFlexFieldBankMap;
	}

	public void setRenderFlexFieldBankMap(Boolean renderFlexFieldBankMap) {
		this.renderFlexFieldBankMap = renderFlexFieldBankMap;
	}

	public String getFlexFieldNameForBankMap() {
		return flexFieldNameForBankMap;
	}

	public void setFlexFieldNameForBankMap(String flexFieldNameForBankMap) {
		this.flexFieldNameForBankMap = flexFieldNameForBankMap;
	}

	public String getFlexFieldForBankMap() {
		return flexFieldForBankMap;
	}

	public void setFlexFieldForBankMap(String flexFieldForBankMap) {
		this.flexFieldForBankMap = flexFieldForBankMap;
	}

	public Boolean getRenderFlexNameBankMapForApprove() {
		return renderFlexNameBankMapForApprove;
	}

	public void setRenderFlexNameBankMapForApprove(
			Boolean renderFlexNameBankMapForApprove) {
		this.renderFlexNameBankMapForApprove = renderFlexNameBankMapForApprove;
	}

	public Boolean getRenderFlexNameBankMap() {
		return renderFlexNameBankMap;
	}

	public void setRenderFlexNameBankMap(Boolean renderFlexNameBankMap) {
		this.renderFlexNameBankMap = renderFlexNameBankMap;
	}

	public Boolean getRenderOrderNoBankMap() {
		return renderOrderNoBankMap;
	}

	public void setRenderOrderNoBankMap(Boolean renderOrderNoBankMap) {
		this.renderOrderNoBankMap = renderOrderNoBankMap;
	}

	public Boolean getRenderOrderNoBankMapForApprove() {
		return renderOrderNoBankMapForApprove;
	}

	public void setRenderOrderNoBankMapForApprove(
			Boolean renderOrderNoBankMapForApprove) {
		this.renderOrderNoBankMapForApprove = renderOrderNoBankMapForApprove;
	}

	public String getOrderNoForBankMap() {
		return orderNoForBankMap;
	}

	public void setOrderNoForBankMap(String orderNoForBankMap) {
		this.orderNoForBankMap = orderNoForBankMap;
	}

	public Boolean getRenderApproveCancelButtonPanelForBankMap() {
		return renderApproveCancelButtonPanelForBankMap;
	}

	public void setRenderApproveCancelButtonPanelForBankMap(
			Boolean renderApproveCancelButtonPanelForBankMap) {
		this.renderApproveCancelButtonPanelForBankMap = renderApproveCancelButtonPanelForBankMap;
	}

	public Boolean getRenderAddButtonPanelForBankMap() {
		return renderAddButtonPanelForBankMap;
	}

	public void setRenderAddButtonPanelForBankMap(
			Boolean renderAddButtonPanelForBankMap) {
		this.renderAddButtonPanelForBankMap = renderAddButtonPanelForBankMap;
	}

	

	public String getDynamicLabelForActivateDeactivateForBankMap() {
		return dynamicLabelForActivateDeactivateForBankMap;
	}

	public void setDynamicLabelForActivateDeactivateForBankMap(
			String dynamicLabelForActivateDeactivateForBankMap) {
		this.dynamicLabelForActivateDeactivateForBankMap = dynamicLabelForActivateDeactivateForBankMap;
	}


	public Boolean getDisableSubmitButtonForBankMap() {
		return disableSubmitButtonForBankMap;
	}

	public void setDisableSubmitButtonForBankMap(
			Boolean disableSubmitButtonForBankMap) {
		this.disableSubmitButtonForBankMap = disableSubmitButtonForBankMap;
	}

	public String getRemarksForBankMap() {
		return remarksForBankMap;
	}

	public void setRemarksForBankMap(String remarksForBankMap) {
		this.remarksForBankMap = remarksForBankMap;
	}

	public BigDecimal getAdditionalBankFieldId() {
		return additionalBankFieldId;
	}

	public void setAdditionalBankFieldId(BigDecimal additionalBankFieldId) {
		this.additionalBankFieldId = additionalBankFieldId;
	}

	public String getApproveByForAlmullaCode() {
		return approveByForAlmullaCode;
	}

	public void setApproveByForAlmullaCode(String approveByForAlmullaCode) {
		this.approveByForAlmullaCode = approveByForAlmullaCode;
	}

	public Date getApproveDateForAlmullaCode() {
		return approveDateForAlmullaCode;
	}

	public void setApproveDateForAlmullaCode(Date approveDateForAlmullaCode) {
		this.approveDateForAlmullaCode = approveDateForAlmullaCode;
	}

	public String getModifiedByForAlmullaCode() {
		return modifiedByForAlmullaCode;
	}

	public void setModifiedByForAlmullaCode(String modifiedByForAlmullaCode) {
		this.modifiedByForAlmullaCode = modifiedByForAlmullaCode;
	}

	public Date getModifiedDateForAlmullaCode() {
		return modifiedDateForAlmullaCode;
	}

	public void setModifiedDateForAlmullaCode(Date modifiedDateForAlmullaCode) {
		this.modifiedDateForAlmullaCode = modifiedDateForAlmullaCode;
	}

	public String getDynamicLabelForActivateDeactivateForAlmullaCode() {
		return dynamicLabelForActivateDeactivateForAlmullaCode;
	}

	public void setDynamicLabelForActivateDeactivateForAlmullaCode(
			String dynamicLabelForActivateDeactivateForAlmullaCode) {
		this.dynamicLabelForActivateDeactivateForAlmullaCode = dynamicLabelForActivateDeactivateForAlmullaCode;
	}


	public BigDecimal getAdditionalBankRulaAlmullaCodePK() {
		return additionalBankRulaAlmullaCodePK;
	}

	public void setAdditionalBankRulaAlmullaCodePK(
			BigDecimal additionalBankRulaAlmullaCodePK) {
		this.additionalBankRulaAlmullaCodePK = additionalBankRulaAlmullaCodePK;
	}

	public Boolean getDisableSubmitButtonForAlmullaCode() {
		return disableSubmitButtonForAlmullaCode;
	}

	public void setDisableSubmitButtonForAlmullaCode(
			Boolean disableSubmitButtonForAlmullaCode) {
		this.disableSubmitButtonForAlmullaCode = disableSubmitButtonForAlmullaCode;
	}

	public String getIsActiveForAlmullaCode() {
		return isActiveForAlmullaCode;
	}

	public void setIsActiveForAlmullaCode(String isActiveForAlmullaCode) {
		this.isActiveForAlmullaCode = isActiveForAlmullaCode;
	}

	public String getCreatedByForAlmullaCode() {
		return createdByForAlmullaCode;
	}

	public void setCreatedByForAlmullaCode(String createdByForAlmullaCode) {
		this.createdByForAlmullaCode = createdByForAlmullaCode;
	}

	public Date getCreatedDateForAlmullaCode() {
		return createdDateForAlmullaCode;
	}

	public void setCreatedDateForAlmullaCode(Date createdDateForAlmullaCode) {
		this.createdDateForAlmullaCode = createdDateForAlmullaCode;
	}

	public String getRemarksForAlmullaCode() {
		return remarksForAlmullaCode;
	}

	public void setRemarksForAlmullaCode(String remarksForAlmullaCode) {
		this.remarksForAlmullaCode = remarksForAlmullaCode;
	}

	public String getActiveStatusInWord() {
		return activeStatusInWord;
	}

	public void setActiveStatusInWord(String activeStatusInWord) {
		this.activeStatusInWord = activeStatusInWord;
	}

	public String getRemarksForBankRule() {
		return remarksForBankRule;
	}

	public void setRemarksForBankRule(String remarksForBankRule) {
		this.remarksForBankRule = remarksForBankRule;
	}

	public BigDecimal getAdditionalBankFieldIdForBankRule() {
		return additionalBankFieldIdForBankRule;
	}

	public void setAdditionalBankFieldIdForBankRule(
			BigDecimal additionalBankFieldIdForBankRule) {
		this.additionalBankFieldIdForBankRule = additionalBankFieldIdForBankRule;
	}

	public String getApproveByForBankRule() {
		return approveByForBankRule;
	}

	public void setApproveByForBankRule(String approveByForBankRule) {
		this.approveByForBankRule = approveByForBankRule;
	}

	public Date getApproveDateForBankRule() {
		return approveDateForBankRule;
	}

	public void setApproveDateForBankRule(Date approveDateForBankRule) {
		this.approveDateForBankRule = approveDateForBankRule;
	}

	public String getModifiedByForBankRule() {
		return modifiedByForBankRule;
	}

	public void setModifiedByForBankRule(String modifiedByForBankRule) {
		this.modifiedByForBankRule = modifiedByForBankRule;
	}

	public Date getModifiedDateForBankRule() {
		return modifiedDateForBankRule;
	}

	public void setModifiedDateForBankRule(Date modifiedDateForBankRule) {
		this.modifiedDateForBankRule = modifiedDateForBankRule;
	}

	public Boolean getDisableSubmitButtonForBankRule() {
		return disableSubmitButtonForBankRule;
	}

	public void setDisableSubmitButtonForBankRule(
			Boolean disableSubmitButtonForBankRule) {
		this.disableSubmitButtonForBankRule = disableSubmitButtonForBankRule;
	}

	public String getDynamicLabelForActivateDeactivateForBankRule() {
		return dynamicLabelForActivateDeactivateForBankRule;
	}

	public void setDynamicLabelForActivateDeactivateForBankRule(
			String dynamicLabelForActivateDeactivateForBankRule) {
		this.dynamicLabelForActivateDeactivateForBankRule = dynamicLabelForActivateDeactivateForBankRule;
	}


	public String getIsActiveForBankRule() {
		return isActiveForBankRule;
	}

	public void setIsActiveForBankRule(String isActiveForBankRule) {
		this.isActiveForBankRule = isActiveForBankRule;
	}


	public String getCreatedByForBankRule() {
		return createdByForBankRule;
	}

	public void setCreatedByForBankRule(String createdByForBankRule) {
		this.createdByForBankRule = createdByForBankRule;
	}

	public Date getCreatedDateForBankRule() {
		return createdDateForBankRule;
	}

	public void setCreatedDateForBankRule(Date createdDateForBankRule) {
		this.createdDateForBankRule = createdDateForBankRule;
	}

	public BigDecimal getAdditionalBankRulePK() {
		return additionalBankRulePK;
	}

	public void setAdditionalBankRulePK(BigDecimal additionalBankRulePK) {
		this.additionalBankRulePK = additionalBankRulePK;
	}

	public void clearRemarksBankMap(){
		setRemarksForBankMap(null);
		setRemarksForAlmullaCode(null);
		setRemarksForBankRule(null);
	}
	
	public void clearAllrecordsAlmullaCode(){
		  setCountryId(null);
		  setdBflexField(null);
		  setAmiecCode(null);
		  setAmiecDescription(null);
		  setAdditionalBankRulaAlmullaCodePK(null);
		  setCreatedByForAlmullaCode(null);
		  setCreatedDateForAlmullaCode(null);
		  setModifiedByForAlmullaCode(null);
		  setModifiedDateForAlmullaCode(null);
		  setApproveDateForAlmullaCode(null);
		  setApproveByForAlmullaCode(null);
		  setIsActive(null);
		  setDynamicLabelForActivateDeactivateForAlmullaCode(null);
		  additionalBankRuleDataList2.clear();
		  additionalBankRuleDataNewList2.clear();
		  setAdditionalBankRuleAmiecRendered(false);
		  setSaveAdditionalBankRule2(false);
	}
	
	  private Boolean booclearfanel = false;

	  public Boolean getBooclearfanel() {
		    return booclearfanel;
	  }

	  public void setBooclearfanel(Boolean booclearfanel) {
		    this.booclearfanel = booclearfanel;
	  }
	
	  public void clearAllAddtionalBankRule(){
		    setCountryId(null);
		    setdBflexField(null);
		    setBankId(null);
		    setAdditionalData(null);
		    setAdditionalDescription(null);
		    setAdditionalBankRulePK(null);
		    setIsActiveForBankRule(null);
		    setDynamicLabelForActivateDeactivateForBankRule(null);
		    setCreatedByForBankRule(null);
		    setCreatedDateForBankRule(null);
		    setModifiedByForBankRule(null);
		    setModifiedDateForBankRule(null);
		    setApproveByForBankRule(null);
		    setApproveDateForBankRule(null);
		    setAdditionalBankRuleAddDataRendered(false);
		    setSaveAdditionalBankRule3(false);
		    additionalBankRuleDataList3.clear();
		    additionalBankRuleDataNewList3.clear();
	  }
	
	  private Boolean booclearPanelForBankRule=false;

	  public Boolean getBooclearPanelForBankRule() {
	  	  return booclearPanelForBankRule;
	  }

	  public void setBooclearPanelForBankRule(Boolean booclearPanelForBankRule) {
	  	  this.booclearPanelForBankRule = booclearPanelForBankRule;
	  }
	  
	  
	  public void addAdditionalBankRule3DataTable() {

			AdditionalBankRuleDataTable additionalBankRuleDataTable = new AdditionalBankRuleDataTable();

			additionalBankRuleDataTable.setAdditionalBankRulePK(getAdditionalBankRulePK());
			additionalBankRuleDataTable.setAdditionalBankFieldIdForBankRule(getAdditionalBankFieldIdForBankRule());
			additionalBankRuleDataTable.setCountryId(getCountryId());
			additionalBankRuleDataTable.setCountryName(generalService.getCountryName(getCountryId()));
			additionalBankRuleDataTable.setBankId(getBankId());
			additionalBankRuleDataTable.setBankName(mapBankList.get(getBankId()));
			additionalBankRuleDataTable.setFlexField(getFlexField());
			additionalBankRuleDataTable.setBankCode(getAdditionalData());
			additionalBankRuleDataTable.setBankDescription(getAdditionalDescription());
			
			if (getAdditionalBankRulePK() != null)
			{
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.PENDING_FOR_APPROVE);
				additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
				additionalBankRuleDataTable.setCreatedByForBankRule(getCreatedByForBankRule());
				additionalBankRuleDataTable.setCreatedDateForBankRule(getCreatedDateForBankRule());
				//additionalBankRuleDataTable.setApproveByForBankRule(getApproveByForBankRule());
				//additionalBankRuleDataTable.setApproveDateForBankRule(getApproveDateForBankRule());
				additionalBankRuleDataTable.setModifiedByForBankRule(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setModifiedDateForBankRule(new Date());
				additionalBankRuleDataTable.setRemarksForBankRule(getRemarksForBankRule());
				additionalBankRuleDataTable.setEditClikedBankRule(getEditClikedBankRule());

			}else
			{
				boolean checkDuplicate=checkDuplicateRecord();
				if(checkDuplicate)
				{
					return;
				}

				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.REMOVE);
				additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
				additionalBankRuleDataTable.setCreatedByForBankRule(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setCreatedDateForBankRule(new Date());
				additionalBankRuleDataTable.setEditClikedBankRule(true);
			}
			
			additionalBankRuleDataList3.add(additionalBankRuleDataTable);
			if (getAdditionalBankRulePK() == null) {
				additionalBankRuleDataNewList3.add(additionalBankRuleDataTable);
			}
			setAdditionalBankRuleAddDataRendered(true);
			setSaveAdditionalBankRule3(true);
			clearAllRule3();
			/*
			Boolean activecheck = false;

			if (getAdditionalBankRulePK() != null) {
				List<AdditionalBankRuleAddData> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankRuleRecordsForView(getCountryId(),getFlexField(),getBankId());
				if(additionalBankAlmullaCodeList.size()>0){
					for(AdditionalBankRuleAddData additionalBnak:additionalBankAlmullaCodeList){
						
				if(getCountryId() != null && getCountryId().intValue()== additionalBnak.getCountryId().getCountryId().intValue() && getFlexField()!=null && getFlexField().equalsIgnoreCase(additionalBnak.getFlexField())
					&& getBankId().intValue()==additionalBnak.getBankId().getBankId().intValue() && getAdditionalData()!=null && getAdditionalData().equalsIgnoreCase(additionalBnak.getAdditionalData())
							&& getAdditionalDescription()!=null && getAdditionalDescription().equalsIgnoreCase(additionalBnak.getAdditionalDescription())){
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(getDynamicLabelForActivateDeactivateForBankRule());
				additionalBankRuleDataTable.setIsActiveForBankRule(getIsActiveForBankRule());
				additionalBankRuleDataTable.setCreatedByForBankRule(getCreatedByForBankRule());
				additionalBankRuleDataTable.setCreatedDateForBankRule(getCreatedDateForBankRule());
				additionalBankRuleDataTable.setApproveByForBankRule(getApproveByForBankRule());
				additionalBankRuleDataTable.setApproveDateForBankRule(getApproveDateForBankRule());
				additionalBankRuleDataTable.setModifiedByForBankRule(getModifiedByForBankRule());
				additionalBankRuleDataTable.setModifiedDateForBankRule(getModifiedDateForBankRule());
				additionalBankRuleDataTable.setRemarksForBankRule(getRemarksForBankRule());
				additionalBankRuleDataTable.setEditClikedBankRule(getEditClikedBankRule());
				activecheck = true;;
				}else{
					//additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(null);
					additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
					additionalBankRuleDataTable.setCreatedByForBankRule(getCreatedByForBankRule());
					additionalBankRuleDataTable.setCreatedDateForBankRule(getCreatedDateForBankRule());
					additionalBankRuleDataTable.setApproveByForBankRule(null);
					additionalBankRuleDataTable.setApproveDateForBankRule(null);
					additionalBankRuleDataTable.setModifiedByForBankRule(sessionStateManage.getUserName());
					additionalBankRuleDataTable.setModifiedDateForBankRule(new Date());
					additionalBankRuleDataTable.setRemarksForBankRule(null);
					//additionalBankRuleDataTable.setEditClikedBankRule(getEditClikedBankRule());
				}
				if(activecheck.equals(true)){
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(getDynamicLabelForActivateDeactivateForBankRule());
				}else{
					additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.PENDING_FOR_APPROVE);
				}
				}
				}
			} else {
				additionalBankRuleDataTable.setDynamicLabelForActivateDeactivateForBankRule(Constants.REMOVE);
				additionalBankRuleDataTable.setIsActiveForBankRule(Constants.U);
				additionalBankRuleDataTable.setCreatedByForBankRule(sessionStateManage.getUserName());
				additionalBankRuleDataTable.setCreatedDateForBankRule(new Date());
				additionalBankRuleDataTable.setEditClikedBankRule(true);
			}

			additionalBankRuleDataList3.add(additionalBankRuleDataTable);
			if (getAdditionalBankRulePK() == null) {
				additionalBankRuleDataNewList3.add(additionalBankRuleDataTable);
			}
			setAdditionalBankRuleAddDataRendered(true);
			setSaveAdditionalBankRule3(true);
			clearAllRule3();*/

		}
	
	  public boolean checkDuplicateRecord()
	  {
		  boolean checkDuplicate=false;
		  List<AdditionalBankRuleAddData> additionalBankAlmullaCodeList = additionalBankRuleMapService.getAdditionBankRuleRecordsForView(getCountryId(),getFlexField(),getBankId());
		  if(additionalBankAlmullaCodeList!=null && additionalBankAlmullaCodeList.size()>0){
			  for(AdditionalBankRuleAddData additionalBnak:additionalBankAlmullaCodeList){

				  if(getCountryId() != null && getCountryId().intValue()== additionalBnak.getCountryId().getCountryId().intValue() && getFlexField()!=null && getFlexField().equalsIgnoreCase(additionalBnak.getFlexField())
						  && getBankId().intValue()==additionalBnak.getBankId().getBankId().intValue() && getAdditionalData()!=null && getAdditionalData().equalsIgnoreCase(additionalBnak.getAdditionalData())
						  && getAdditionalDescription()!=null && getAdditionalDescription().equalsIgnoreCase(additionalBnak.getAdditionalDescription())){
					  checkDuplicate=true;
					  break;

				  }else
				  {
					  checkDuplicate=false;
				  }
			  }
		  }
		  if(!checkDuplicate)
		  {
			  List<AdditionalBankRuleDataTable> additionalBankRuleDataList = getAdditionalBankRuleDataList3();
			  if(additionalBankRuleDataList!=null && additionalBankRuleDataList.size()>0)
			  {
				  for(AdditionalBankRuleDataTable additionalBankRuleDataTable : additionalBankRuleDataList)
				  {
					  if(getCountryId() != null && getCountryId().intValue()== additionalBankRuleDataTable.getCountryId().intValue() && getFlexField()!=null && getFlexField().equalsIgnoreCase(additionalBankRuleDataTable.getFlexField())
							  && getBankId().intValue()==additionalBankRuleDataTable.getBankId().intValue() && getAdditionalData()!=null && getAdditionalData().equalsIgnoreCase(additionalBankRuleDataTable.getAdditionalData())
							  && getAdditionalDescription()!=null && getAdditionalDescription().equalsIgnoreCase(additionalBankRuleDataTable.getAdditionalDescription())){
						  checkDuplicate=true;
						  break;

					  }else
					  {
						  checkDuplicate=false;
					  }
				  }
			  }
		  }
		  return checkDuplicate;
	  }
}
