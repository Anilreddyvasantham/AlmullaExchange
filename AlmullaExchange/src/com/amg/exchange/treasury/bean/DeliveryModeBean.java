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
import com.amg.exchange.treasury.model.DeliveryMode;
import com.amg.exchange.treasury.model.DeliveryModeDesc;
import com.amg.exchange.treasury.service.DeliveryModeService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("deliveryModeBean")
@Scope("session")
public class DeliveryModeBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DeliveryModeBean.class);
	private BigDecimal deliveryModeId;
	private String deliveryMode = null;
	private String englishDeliveryModeDesc = null;
	private String localDeliveryModeDesc = null;

	private BigDecimal DeliveryModeDescId;
	private String localDeliveryName = null;
	private LanguageType languageId;

	private String createdBy = null;
	private Date createdDate = null;
	private String modifiedBy = null;
	private Date modifiedDate = null;
	private String isActive = null;
	private Boolean booRenderDataTable = false;
	private Boolean booRenderSave = false;
	private Boolean booDeliveryModeCheck = false;
	private Boolean booDeliveryCheckForDataTable = false;
	private BigDecimal countNoOfSave;
	private BigDecimal engLanguageId;
	private BigDecimal araLanguageId;
	private int count = 0;
	private boolean booEditButton;
	private Boolean clearPanel;
	private DeliveryModeDatatable deliveryDTObj=null;
	public BigDecimal getDeliveryModeDescId() {
		return DeliveryModeDescId;
	}

	public void setDeliveryModeDescId(BigDecimal deliveryModeDescId) {
		DeliveryModeDescId = deliveryModeDescId;
	}

	public String getLocalDeliveryName() {
		return localDeliveryName;
	}

	public void setLocalDeliveryName(String localDeliveryName) {
		this.localDeliveryName = localDeliveryName;
	}

	public LanguageType getLanguageId() {
		return languageId;
	}

	public void setLanguageId(LanguageType languageId) {
		this.languageId = languageId;
	}

	public Boolean getBooDeliveryCheckForDataTable() {
		return booDeliveryCheckForDataTable;
	}

	
	public Boolean getClearPanel() {
		return clearPanel;
	}

	public void setClearPanel(Boolean clearPanel) {
		this.clearPanel = clearPanel;
	}

	public void setBooDeliveryCheckForDataTable(
			Boolean booDeliveryCheckForDataTable) {
		this.booDeliveryCheckForDataTable = booDeliveryCheckForDataTable;
	}

	
	public boolean isBooEditButton() {
		return booEditButton;
	}

	public void setBooEditButton(boolean booEditButton) {
		this.booEditButton = booEditButton;
	}


	public DeliveryModeDatatable getDeliveryDTObj() {
		return deliveryDTObj;
	}

	public void setDeliveryDTObj(DeliveryModeDatatable deliveryDTObj) {
		this.deliveryDTObj = deliveryDTObj;
	}


	private List<DeliveryModeDatatable> listData = new CopyOnWriteArrayList<DeliveryModeDatatable>();
	private List<DeliveryModeDatatable> newListData = new CopyOnWriteArrayList<DeliveryModeDatatable>();

	public List<DeliveryModeDatatable> getListData() {
		return listData;
	}

	public void setListData(List<DeliveryModeDatatable> listData) {
		this.listData = listData;
	}

	public Boolean getBooDeliveryModeCheck() {
		return booDeliveryModeCheck;
	}

	public void setBooDeliveryModeCheck(Boolean booDeliveryModeCheck) {
		this.booDeliveryModeCheck = booDeliveryModeCheck;
	}

	public Boolean getBooRenderDataTable() {
		return booRenderDataTable;
	}

	public void setBooRenderDataTable(Boolean booRenderDataTable) {
		this.booRenderDataTable = booRenderDataTable;
	}

	public Boolean getBooRenderSave() {
		return booRenderSave;
	}

	public void setBooRenderSave(Boolean booRenderSave) {
		this.booRenderSave = booRenderSave;
	}

	
	public BigDecimal getEngLanguageId() {
		return engLanguageId;
	}

	public void setEngLanguageId(BigDecimal engLanguageId) {
		this.engLanguageId = engLanguageId;
	}

	public BigDecimal getAraLanguageId() {
		return araLanguageId;
	}

	public void setAraLanguageId(BigDecimal araLanguageId) {
		this.araLanguageId = araLanguageId;
	}

	private SessionStateManage session = new SessionStateManage();

	@Autowired
	DeliveryModeService deliveryModeService;

	public BigDecimal getDeliveryModeId() {
		return deliveryModeId;
	}

	public void setDeliveryModeId(BigDecimal deliveryModeId) {
		this.deliveryModeId = deliveryModeId;
	}

	public String getDeliveryMode() {
		return deliveryMode;
	}

	public void setDeliveryMode(String deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	public String getEnglishDeliveryModeDesc() {
		return englishDeliveryModeDesc;
	}

	public void setEnglishDeliveryModeDesc(String englishDeliveryModeDesc) {
		this.englishDeliveryModeDesc = englishDeliveryModeDesc;
	}

	public String getLocalDeliveryModeDesc() {
		return localDeliveryModeDesc;
	}

	public void setLocalDeliveryModeDesc(String localDeliveryModeDesc) {
		this.localDeliveryModeDesc = localDeliveryModeDesc;
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

	public BigDecimal getCountNoOfSave() {
		return countNoOfSave;
	}

	public void setCountNoOfSave(BigDecimal countNoOfSave) {
		this.countNoOfSave = countNoOfSave;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	



	public void saveDataTableRecords() {
		if (listData.isEmpty()) {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("dataNotFund.show();");
		} else {

			try {
				for (DeliveryModeDatatable deliveryDTObj : listData) {
					  if(deliveryDTObj.getIfEditClicked().equals(true)){
					count = count + 1;
					DeliveryMode deliverymode = new DeliveryMode();
					deliverymode.setDeliveryMode(deliveryDTObj.getDeliveryMode());
					deliverymode.setDeliveryModeId(deliveryDTObj.getDeliveryPk());
					deliverymode.setCreatedBy(deliveryDTObj.getCreatedBy());
					deliverymode.setCreatedDate(deliveryDTObj.getCreateDate());
					deliverymode.setModifiedBy(deliveryDTObj.getModifiedBy());
					deliverymode.setModifiedDate(deliveryDTObj.getModifiedDate());
					deliverymode.setAppovedBy(deliveryDTObj.getApprovedBy());
					deliverymode.setApproveDate(deliveryDTObj.getApprovedDate());
					deliverymode.setIsActive(deliveryDTObj.getIsActive());
					deliverymode.setRemrks(deliveryDTObj.getRemarks());
					//deliveryModeService.save(deliverymode);

					DeliveryModeDesc desc = new DeliveryModeDesc();
					LanguageType languageType = new LanguageType();
					languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
					desc.setLanguageType(languageType);
					desc.setDeliveryModeDescId(deliveryDTObj.getDeliveryEnglishPk());
					desc.setEnglishDeliveryName(deliveryDTObj.getEnglishDeliveryModeDesc());
					desc.setDeliveryMode(deliverymode);
					//deliveryModeService.saveRecord(desc);

					DeliveryModeDesc desc2 = new DeliveryModeDesc();
					LanguageType languageType2 = new LanguageType();
					languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
					desc2.setLanguageType(languageType2);
					desc2.setDeliveryModeDescId(deliveryDTObj.getDeliveryLocalPk());
					desc2.setEnglishDeliveryName(deliveryDTObj.getLocalDeliveryModeDesc());
					desc2.setDeliveryMode(deliverymode);
					//deliveryModeService.saveRecord(desc2);
					deliveryModeService.saveDelivery(deliverymode,desc,desc2);
				}
				}
				setCountNoOfSave(new BigDecimal(count));
				count = 0;
				RequestContext.getCurrentInstance().execute("complete.show();");
				newListData.clear();
				
			} catch (Exception exception) {
				  setErrorMessage(exception.getMessage());
				log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
			}
			
		}
	}

	public void editRecord(DeliveryModeDatatable deliveryDTObj) {
		  try{
		setDeliveryDTObj(deliveryDTObj);
		setDeliveryMode(deliveryDTObj.getDeliveryMode());
		setDeliveryPk(deliveryDTObj.getDeliveryPk());
		setEnglishDeliveryModeDesc(deliveryDTObj.getEnglishDeliveryModeDesc());
		setLocalDeliveryModeDesc(deliveryDTObj.getLocalDeliveryModeDesc());
		setDeliveryEnglishPk(deliveryDTObj.getDeliveryEnglishPk());
		setDeliveryLocalPk(deliveryDTObj.getDeliveryLocalPk());
		setDynamicLabelForActivateDeactivate(deliveryDTObj
				.getDynamicLabelForActivateDeactivate());
		setRenderEditButton(deliveryDTObj.getRenderEditButton());
		setIsActive(deliveryDTObj.getIsActive());
		setDisableSubmitButton(true);
		setCreatedBy(deliveryDTObj.getCreatedBy());
		setCreatedDate(deliveryDTObj.getCreateDate());
		setModifiedBy(deliveryDTObj.getModifiedBy());
		setModifiedDate(deliveryDTObj.getModifiedDate());
		setApprovedBy(deliveryDTObj.getApprovedBy());
		setApprovedDate(deliveryDTObj.getApprovedDate());
		setRemarks(deliveryDTObj.getRemarks());
		setIfEditClicked(true);
		setBooEditButton(true);
		setClearPanel(true);
		listData.remove(deliveryDTObj);
		newListData.remove(deliveryDTObj);
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("edit Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;    
		  }
	}

	/*public void removeRecord(DeliveryModeDatatable deliveryDTObj) {
		if (deliveryDTObj.getDeliveryPk() == null) {
			listData.remove(deliveryDTObj);
			newListData.remove(deliveryDTObj);
			if (listData.size() <= 0) {
				setBooRenderSave(false);
				setBooRenderDataTable(false);
			}
		}
		else {
			delete(deliveryDTObj);
			listData.clear();
			viewDBData();
		}

	}*/

	/*public void delete(DeliveryModeDatatable deliveryDTObj) {
		listData.clear();
		try {
			DeliveryMode deliverymode = new DeliveryMode();
			deliverymode.setDeliveryMode(deliveryDTObj.getDeliveryMode());
			if (deliveryDTObj.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.ACTIVATE)) {
				deliverymode.setIsActive(Constants.U);
			} else if (deliveryDTObj.getDynamicLabelForActivateDeactivate()
					.equalsIgnoreCase(Constants.DEACTIVATE)) {
				deliverymode.setIsActive(Constants.D);
				deliverymode.setRemrks(deliveryDTObj.getRemarks());
			}
			if (deliveryDTObj.getDeliveryPk() != null) {
				deliverymode.setDeliveryModeId(deliveryDTObj.getDeliveryPk());
				deliverymode.setCreatedBy(deliveryDTObj.getCreatedBy());
				deliverymode.setCreatedDate(deliveryDTObj.getCreateDate());
				deliverymode.setModifiedBy(session.getUserName());
				deliverymode.setModifiedDate(new Date());
			}
			deliverymode.setCreatedBy(deliveryDTObj.getCreatedBy());
			deliverymode.setCreatedDate(deliveryDTObj.getCreateDate());
			deliverymode.setModifiedBy(session.getUserName());
			deliverymode.setModifiedDate(new Date());
			
			deliveryModeService.save(deliverymode);

			deliveryModeService.save(deliverymode);
			DeliveryModeDesc desc=new DeliveryModeDesc();
			LanguageType languageType = new LanguageType();
			languageType.setLanguageId(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID));
			desc.setLanguageType(languageType);
			desc.setDeliveryModeDescId(deliveryDTObj.getDeliveryEnglishPk());
			desc.setEnglishDeliveryName(deliveryDTObj.getEnglishDeliveryModeDesc());
			desc.setDeliveryMode(deliverymode);
			deliveryModeService.saveRecord(desc);

			DeliveryModeDesc desc2 = new DeliveryModeDesc();
			LanguageType languageType2 = new LanguageType();
			languageType2.setLanguageId(new BigDecimal(Constants.ARABIC_LANGUAGE_ID));
			desc2.setLanguageType(languageType2);
			desc2.setDeliveryModeDescId(deliveryDTObj.getDeliveryLocalPk());
			desc2.setEnglishDeliveryName(deliveryDTObj.getLocalDeliveryModeDesc());
			desc2.setDeliveryMode(deliverymode);
			deliveryModeService.saveRecord(desc2);
			
		} catch (Exception e) {

		}
	}*/

	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../registration/employeehome.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("exit  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	public void clearAllFields() {
		setDeliveryMode(null);
		setEnglishDeliveryModeDesc(null);
		setLocalDeliveryModeDesc(null);
		setDeliveryPk(null);
		setDeliveryEnglishPk(null);
		setDeliveryLocalPk(null);
		if (listData.size() > 0) {
			setDisableSubmitButton(false);
		}
	}

	public void clickOnOKSave() {
		listData.clear();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void deliveryCodeMasterPageNavigation() {
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
		setClearPanel(false);
		setBooEditButton(false);
		// Delivery Approval functionality
		setBooReadonly(false);
		setBoohideButton(true);
		setBoohideSecod(false);
		// End
		listData.clear();
		newListData.clear();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "DeliveryCodeMaster.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}

	}

	int i = 0;

	public void addRecordsToDataTable() {
		  try{
		setDisableSubmitButton(false);
		setBooEditButton(false);
		setClearPanel(false);
		DeliveryModeDatatable deliveryDt = new DeliveryModeDatatable();
		deliveryDt.setDeliveryMode(getDeliveryMode());
		deliveryDt.setDeliveryPk(getDeliveryPk());
		deliveryDt.setDeliveryEnglishPk(getDeliveryEnglishPk());
		deliveryDt.setDeliveryLocalPk(getDeliveryLocalPk());
		deliveryDt.setEnglishDeliveryModeDesc(getEnglishDeliveryModeDesc());
		deliveryDt.setLocalDeliveryModeDesc(getLocalDeliveryModeDesc());
		deliveryDt.setCreateDate(getCreatedDate());
		deliveryDt.setCreatedBy(getCreatedBy());
	/*	if(getDeliveryPk() != null ){
			if(newListData.size() == 0){
				if(listData.size() !=0){
					if(deliveryDTObj !=null){
						if(deliveryDt.getDeliveryMode().equals(deliveryDTObj.getDeliveryMode())&&deliveryDt.getEnglishDeliveryModeDesc().equals(deliveryDTObj.getEnglishDeliveryModeDesc())&&deliveryDt.getLocalDeliveryModeDesc().equals(deliveryDTObj.getLocalDeliveryModeDesc())){
							deliveryDt.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
							deliveryDt.setRenderEditButton(true);
							deliveryDt.setIsActive(getIsActive());
							deliveryDt.setModifiedBy(getModifiedBy());
							deliveryDt.setModifiedDate(getModifiedDate());
							deliveryDt.setApprovedBy(getApprovedBy());
							deliveryDt.setApprovedDate(getApprovedDate());
							deliveryDt.setRemarks(getRemarks());
							
						}else{
							deliveryDt.setDynamicLabelForActivateDeactivate("");
							deliveryDt.setRenderEditButton(true);
							deliveryDt.setIsActive(Constants.U);
							deliveryDt.setIfEditClicked(true);
							deliveryDt.setModifiedBy(session.getUserName());
							deliveryDt.setModifiedDate(new Date());
							deliveryDt.setApprovedBy(null);
							deliveryDt.setApprovedDate(null);
							deliveryDt.setRemarks(null);
						}
					}else{
						deliveryDt.setRenderEditButton(true);
						deliveryDt.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
						deliveryDt.setModifiedBy(getModifiedBy());
						deliveryDt.setModifiedDate(getModifiedDate());
						deliveryDt.setIsActive(getIsActive());
						deliveryDt.setApprovedBy(getApprovedBy());
						deliveryDt.setApprovedDate(getApprovedDate());
						deliveryDt.setRemarks(getRemarks());
					}
				}else{
					deliveryDt.setRenderEditButton(true);
					deliveryDt.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					deliveryDt.setModifiedBy(getModifiedBy());
					deliveryDt.setModifiedDate(getModifiedDate());
					deliveryDt.setIsActive(getIsActive());
					deliveryDt.setApprovedBy(getApprovedBy());
					deliveryDt.setApprovedDate(getApprovedDate());
					deliveryDt.setRemarks(getRemarks());
				}
			}
			
		}
		
		else {
			deliveryDt.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
			deliveryDt.setRenderEditButton(true);
			deliveryDt.setIsActive(Constants.U);
			deliveryDt.setIfEditClicked(true);
			deliveryDt.setCreateDate(new Date());
			deliveryDt.setCreatedBy(session.getUserName());
		}*/
		 if (getDeliveryPk() != null) {
			   if(deliveryDt.getDeliveryMode().equals(deliveryDTObj.getDeliveryMode())&&deliveryDt.getEnglishDeliveryModeDesc().equals(deliveryDTObj.getEnglishDeliveryModeDesc())&&deliveryDt.getLocalDeliveryModeDesc().equals(deliveryDTObj.getLocalDeliveryModeDesc())){
				     deliveryDt.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					deliveryDt.setRenderEditButton(true);
					deliveryDt.setIsActive(getIsActive());
					deliveryDt.setModifiedBy(getModifiedBy());
					deliveryDt.setModifiedDate(getModifiedDate());
					deliveryDt.setApprovedBy(getApprovedBy());
					deliveryDt.setApprovedDate(getApprovedDate());
					deliveryDt.setRemarks(getRemarks());
					
				}else{
					deliveryDt.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					deliveryDt.setRenderEditButton(true);
					deliveryDt.setIsActive(Constants.U);
					deliveryDt.setIfEditClicked(true);
					deliveryDt.setModifiedBy(session.getUserName());
					deliveryDt.setModifiedDate(new Date());
					deliveryDt.setApprovedBy(null);
					deliveryDt.setApprovedDate(null);
					deliveryDt.setRemarks(null);
				}    
			   }else{
				     deliveryDt.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
					deliveryDt.setRenderEditButton(true);
					deliveryDt.setIsActive(Constants.U);
					deliveryDt.setIfEditClicked(true);
					deliveryDt.setCreateDate(new Date());
					deliveryDt.setCreatedBy(session.getUserName());     
			   }
			listData.add(deliveryDt);
			if (getDeliveryPk() == null) {
					newListData.add(deliveryDt);
				}

				clearAllFields();
				setBooRenderDataTable(true);
				setBooRenderSave(true);
		
		
		
	/*System.out.println("$$$$$$$$$$$$$$$$$$$4" + getLocalDeliveryModeDesc());

		listData.add(deliveryDt);

		if (getDeliveryPk() == null) {
			newListData.add(deliveryDt);
		}

		clearAllFields();
		setBooRenderDataTable(true);
		setBooRenderSave(true);*/
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while adding data Table DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
		
	}

	private boolean setBooRenderDialogBox = false;

	public boolean isSetBooRenderDialogBox() {
		return setBooRenderDialogBox;
	}

	public void setSetBooRenderDialogBox(boolean setBooRenderDialogBox) {
		this.setBooRenderDialogBox = setBooRenderDialogBox;
	}

	public void checkDelivery() {
		/*popUp();
		List<DeliveryMode> matchRemittance = new ArrayList<DeliveryMode>();
		matchRemittance.addAll(deliveryModeService
				.getDeliveryMode(getDeliveryMode()));*/

	}

	// Page Navigation added on 23Jan @Koti
	public void deliveryModePageNavigation() {

		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSave(false);
	
		listData.clear();
		newListData.clear();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	/* added to populate the DB Values added @Koti 20/02/2015* */
	private BigDecimal deliveryPk;
	private BigDecimal deliveryEnglishPk;
	private BigDecimal deliveryLocalPk;

	List<DeliveryMode> deliveryModeList = new ArrayList<DeliveryMode>();
	List<DeliveryModeDesc> deliveryMDesc = new ArrayList<DeliveryModeDesc>();

	  public void popUp() {
		    try {
			     deliveryModeList = deliveryModeService.getDeliveryMode(getDeliveryMode());
				    if (deliveryModeList.size() > 0) {
					      clearAllFields();
					      RequestContext.getCurrentInstance().execute("datatable.show();");
					      return;
				    }
			    /*  setDeliveryDTObj(null);
			      setDeliveryPk(null);
			      setDeliveryEnglishPk(null);
			      setDeliveryLocalPk(null);
			      setEnglishDeliveryModeDesc(null);
			      setLocalDeliveryModeDesc(null);
			      deliveryModeList = deliveryModeService.getDeliveryMode(getDeliveryMode());
			      if (deliveryModeList.size() != 0) {
					if (deliveryModeList.get(0).getIsActive().equalsIgnoreCase(Constants.Yes)) {
						  setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
						  setRenderEditButton(true);
					} else if (deliveryModeList.get(0).getIsActive().equalsIgnoreCase(Constants.D)) {
						  setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
						  setRemarks(deliveryModeList.get(0).getRemrks());
						  setRenderEditButton(true);
					} else if (deliveryModeList.get(0).getIsActive().equalsIgnoreCase(Constants.U)) {
						  if (deliveryModeList.get(0).getModifiedBy() == null && deliveryModeList.get(0).getModifiedDate() == null) {
							    setDynamicLabelForActivateDeactivate(Constants.DELETE);
							    setRenderEditButton(true);
						  } else {
							    setDynamicLabelForActivateDeactivate("");
							    setRenderEditButton(true);
						  }
					}
					setDeliveryPk(deliveryModeList.get(0).getDeliveryModeId());
					setIsActive(deliveryModeList.get(0).getIsActive());
					setCreatedBy(deliveryModeList.get(0).getCreatedBy());
					setCreatedDate(deliveryModeList.get(0).getCreatedDate());
					setModifiedBy(deliveryModeList.get(0).getModifiedBy());
					setModifiedDate(deliveryModeList.get(0).getModifiedDate());
					setApprovedBy(deliveryModeList.get(0).getAppovedBy());
					setApprovedDate(deliveryModeList.get(0).getApproveDate());

					setRenderEditButton(true);

					deliveryMDesc = deliveryModeService.getDeliveryDescriptionList(deliveryModeList.get(0).getDeliveryModeId());
					for (DeliveryModeDesc desc : deliveryMDesc) {
						  if (desc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ENGLISH_LANGUAGE_ID))) {
							    setDeliveryEnglishPk(desc.getDeliveryModeDescId());
							    setEnglishDeliveryModeDesc(desc.getEnglishDeliveryName());
						  }
						  if (desc.getLanguageType().getLanguageId().equals(new BigDecimal(Constants.ARABIC_LANGUAGE_ID))) {
							    setDeliveryLocalPk(desc.getDeliveryModeDescId());
							    setLocalDeliveryModeDesc(desc.getEnglishDeliveryName());
						  }
					}
			      }*/

		    } catch (Exception exception) {
			      setErrorMessage(exception.getMessage());
			      log.info("popup()::::::::::::::::::::::::::::::::" + exception.getMessage());
			      RequestContext.getCurrentInstance().execute("exception.show();");
			      return;
		    }
	  }

	public BigDecimal getDeliveryPk() {
		return deliveryPk;
	}

	public BigDecimal getDeliveryEnglishPk() {
		return deliveryEnglishPk;
	}

	public BigDecimal getDeliveryLocalPk() {
		return deliveryLocalPk;
	}

	public void setDeliveryPk(BigDecimal deliveryPk) {
		this.deliveryPk = deliveryPk;
	}

	public void setDeliveryEnglishPk(BigDecimal deliveryEnglishPk) {
		this.deliveryEnglishPk = deliveryEnglishPk;
	}

	public void setDeliveryLocalPk(BigDecimal deliveryLocalPk) {
		this.deliveryLocalPk = deliveryLocalPk;
	}

	public List<String> autoComplete(String query)throws Exception {
		  List<String> list=null;
		  try{
		if (query.length() > 0) {
			  list=deliveryModeService.getAllData(query);

		} else {
			return list;
		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
			      log.info("popup()::::::::::::::::::::::::::::::::" + exception.getMessage());
		  }
		  return list;
	}

	/* added to populate the DB Values added @Koti 20/02/2015* */
	public void allDetailsToListElement() {

		if (listData.size() != 0) {
			for (DeliveryModeDatatable DeliveryDtlidt : listData) {
				if (DeliveryDtlidt.getDeliveryMode().equals(getDeliveryMode())) {
					setDeliveryMode(null);
					setEnglishDeliveryModeDesc(null);
					setLocalDeliveryModeDesc(null);
					setDisableSubmitButton(false);
					RequestContext.getCurrentInstance().execute("succ.show();");
					setDeliveryPk(null);
				}

			}
		}
		
		if (getDeliveryMode() != null) {
			addRecordsToDataTable();
		}

	}

	public void update() {
		if (getDeliveryMode() != "") {
			RequestContext.getCurrentInstance().execute("succ.show();");
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../treasury/DeliveryCodeMaster.xhtml");
			} catch (IOException exception) {
				  setErrorMessage(exception.getMessage());
					log.error("Error Occured while updating DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;
			}
		}
	}

	public void clear() {
		listData.clear();
		clearAllFields();
		setBooRenderDataTable(false);
		setBooRenderSave(false);

		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (IOException exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while clering DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	public void view() {
		listData.clear();
		List<Object> deliveryModeList = deliveryModeService
				.getDeliveryDescriptionList();

		for (int i = 0; i < deliveryModeList.size(); i++) {
			Object[] object = (Object[]) deliveryModeList.get(i);
			DeliveryModeDatatable deliveryDt = new DeliveryModeDatatable();

			deliveryDt.setDeliveryMode(String.valueOf(object[0]));

			deliveryDt.setEnglishDeliveryModeDesc(String.valueOf(object[1]));

			deliveryDt.setLocalDeliveryModeDesc(String.valueOf(object[2]));

			deliveryDt.setDeliveryCode(String.valueOf(object[3]));

			listData.add(deliveryDt);

			clearAllFields();
			setBooRenderDataTable(true);
			setBooRenderSave(true);
		}
	}

	public void viewDBData() {
		  try{
		listData.clear();
		List<DeliveryMode> deliveryModeList=deliveryModeService.getDelivertylist();
		if(deliveryModeList.size()>0){
		for (DeliveryMode deliveryMode : deliveryModeList) {
				DeliveryModeDatatable datatable=new DeliveryModeDatatable();
				datatable.setDeliveryMode(deliveryMode.getDeliveryMode());
				datatable.setDeliveryPk(deliveryMode.getDeliveryModeId());
				datatable.setCreatedBy(deliveryMode.getCreatedBy());
				datatable.setCreateDate(deliveryMode.getCreatedDate());
				datatable.setModifiedBy(deliveryMode.getModifiedBy());
				datatable.setModifiedDate(deliveryMode.getModifiedDate());
				datatable.setApprovedBy(deliveryMode.getAppovedBy());
				datatable.setApprovedDate(deliveryMode.getApproveDate());
				datatable.setIsActive(deliveryMode.getIsActive());
				datatable.setRemarks(deliveryMode.getRemrks());
				datatable.setRenderEditButton(true);
				datatable.setBooEditButton(false);
				setBooEditButton(false);
				setClearPanel(false);
				List<DeliveryModeDesc> deliveryModeDescList=deliveryModeService.getAllDescList(deliveryMode.getDeliveryModeId());
				if(deliveryModeDescList.size()>0 && deliveryModeDescList !=null){
				for (DeliveryModeDesc deliveryModeDesc : deliveryModeDescList) {
					if(deliveryModeDesc.getLanguageType().getLanguageId().intValue()==1){
						datatable.setDeliveryEnglishPk(deliveryModeDesc.getDeliveryModeDescId());
						datatable.setEnglishDeliveryModeDesc(deliveryModeDesc.getEnglishDeliveryName());
						datatable.setEngLanguageId(deliveryModeDesc.getLanguageType().getLanguageId());
					}
					if(deliveryModeDesc.getLanguageType().getLanguageId().intValue()==2){
						datatable.setDeliveryLocalPk(deliveryModeDesc.getDeliveryModeDescId());
						datatable.setLocalDeliveryModeDesc(deliveryModeDesc.getEnglishDeliveryName());
						datatable.setAraLanguageId(deliveryModeDesc.getLanguageType().getLanguageId());
					}
				}
				}else{
					continue;
				}
				if(deliveryMode.getIsActive().equalsIgnoreCase(Constants.Yes)){
					datatable.setDynamicLabelForActivateDeactivate(Constants.DEACTIVATE);
				}else if(deliveryMode.getIsActive().equalsIgnoreCase(Constants.D)){
					datatable.setDynamicLabelForActivateDeactivate(Constants.ACTIVATE);
				}else if(deliveryMode.getIsActive().equalsIgnoreCase(Constants.U)){
					if(deliveryMode.getModifiedBy() == null && deliveryMode.getModifiedDate() == null && deliveryMode.getAppovedBy() == null && deliveryMode.getApproveDate() == null && deliveryMode.getRemrks() == null){
					datatable.setDynamicLabelForActivateDeactivate(Constants.DELETE);
					}
					else{
						datatable.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);	
					}
				}
				listData.add(datatable);
				
			}
		listData.addAll(newListData);
		clearAllFields();
		setBooRenderDataTable(true);
		setBooRenderSave(true);
		try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (Exception e) {
			  setErrorMessage(e.getMessage());
			log.info("e.getMessage():::::::::::::"+e.getMessage());
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
		}else{
				RequestContext.getCurrentInstance().execute("norecord.show();");
				return;
			}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while View Db Data DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
			
		}
		
	

	/*public void remove(DeliveryModeDatatable deliveryDTObj) {
		try {
			DeliveryMode delivery = new DeliveryMode();
			delivery.setDeliveryModeId(deliveryDTObj.getDeliveryPk());

			delivery.setDeliveryMode(deliveryDTObj.getDeliveryCode());
			delivery.setCreatedBy(deliveryDTObj.getCreatedBy());
			delivery.setCreatedDate(deliveryDTObj.getCreateDate());
			delivery.setModifiedBy(session.getUserName());
			delivery.setModifiedDate(new Date());
			delivery.setDeliveryMode(deliveryDTObj.getDeliveryMode());
			deliveryModeService.save(delivery);

			DeliveryModeDesc deliveryServiceDesc1 = new DeliveryModeDesc();

			deliveryServiceDesc1.setDeliveryModeDescId(deliveryDTObj
					.getDeliveryEnglishPk());
			deliveryServiceDesc1.setEnglishDeliveryName(deliveryDTObj
					.getEnglishDeliveryModeDesc());

			DeliveryMode deliveryId1 = new DeliveryMode();

			deliveryId1.setDeliveryModeId(deliveryDTObj.getDeliveryPk());

			deliveryServiceDesc1.setDeliveryMode(deliveryId1);

			LanguageType langId = new LanguageType();

			langId.setLanguageId(new BigDecimal(1));

			deliveryServiceDesc1.setLanguageType(langId);

			deliveryModeService.saveRecord(deliveryServiceDesc1);

			DeliveryModeDesc deliveryServiceDesc2 = new DeliveryModeDesc();

			deliveryServiceDesc2.setDeliveryModeDescId(deliveryDTObj
					.getDeliveryLocalPk());

			deliveryServiceDesc2.setEnglishDeliveryName(deliveryDTObj
					.getLocalDeliveryModeDesc());
			DeliveryMode deliveryId2 = new DeliveryMode();
			deliveryId2.setDeliveryModeId(deliveryDTObj.getDeliveryPk());
			deliveryServiceDesc2.setDeliveryMode(deliveryId2);

			LanguageType langId2 = new LanguageType();
			langId2.setLanguageId(new BigDecimal(2));
			deliveryServiceDesc2.setLanguageType(langId2);

			deliveryModeService.saveRecord(deliveryServiceDesc2);

			listData.remove(deliveryDTObj);

		} catch (Exception e) {

		}

	}*/

	private List<DeliveryMode> listDelivery = new ArrayList<DeliveryMode>();

	public List<DeliveryMode> getListDelivery() {

		return listDelivery;
	}

	public void setListDelivery(List<DeliveryMode> listDelivery) {
		this.listDelivery = listDelivery;
	}

	public void navigation() {

		try {
			fetchData();
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "DeliveryCodeMasterAppoval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMasterAppoval.xhtml");

		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while navigation DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	public void navigationEdit(DeliveryModeDatatable datatable) {
	/*	if (datatable.getCreatedBy().equalsIgnoreCase(session.getUserName())) */
		  try{
		 if((datatable.getModifiedBy()==null?datatable.getCreatedBy():datatable.getModifiedBy()).equalsIgnoreCase(session.getUserName()))
		{
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {
			setBooRenderDataTable(false);
			setBooRenderSave(false);
			try {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../treasury/DeliveryCodeMaster.xhtml");

			} catch (Exception exception) {
				  setErrorMessage(exception.getMessage());
					log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
					RequestContext.getCurrentInstance().execute("exception.show();");
					return;

			}

			setBooReadonly(true);

			setBoohideButton(false);
			setBoohideSecod(true);
			setDeliveryMode(datatable.getDeliveryMode());
			
			setDeliveryPk(datatable.getDeliveryPk());

			setEnglishDeliveryModeDesc(deliveryModeService
					.getEngDelivery(datatable.getDeliveryPk()));

			setLocalDeliveryModeDesc(deliveryModeService
					.getArbDelivery(datatable.getDeliveryPk()));

			setCreatedBy(datatable.getCreatedBy());
			setCreatedDate(datatable.getCreateDate());
			setIsActive(datatable.getIsActive());
			setModifiedBy(datatable.getModifiedBy());
			setModifiedDate(datatable.getModifiedDate());

		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while navigation edit DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
	}

	public void cancel() {
		listDeli.clear();
		try {
			fetchData();
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMasterAppoval.xhtml");

		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while cancel DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		}
	}

	public void approv() {
		/* userName=deliveryModeService.getCreatedName(); */

		

			/*DeliveryMode deliverymode = new DeliveryMode();
			deliverymode.setDeliveryModeId(getDeliveryPk());
			deliverymode.setDeliveryMode(getDeliveryMode());
			deliverymode.setAppovedBy(session.getUserName());
			deliverymode.setApproveDate(new Date());
			deliverymode.setCreatedBy(getCreatedBy());
			deliverymode.setCreatedDate(getCreatedDate());
			deliverymode.setModifiedBy(getModifiedBy());
			deliverymode.setModifiedDate(getModifiedDate());
			deliverymode.setIsActive(Constants.Yes);
			deliveryModeService.save(deliverymode);*/
		  try{
			    String approveMsg=deliveryModeService.approveRecord(getDeliveryPk(),session.getUserName());
				if(approveMsg.equalsIgnoreCase("Success")){	
				RequestContext.getCurrentInstance().execute("approv.show();");
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
				}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
			    log.info("approve():::::::::::::::::::::::::::"+exception.getMessage());
			    RequestContext.getCurrentInstance().execute("exception.show();");
			    return;
		  }
	}

	private String createduserName;

	public String getCreateduserName() {
		return createduserName;
	}

	public void setCreateduserName(String createduserName) {
		this.createduserName = createduserName;
	}

	public List<DeliveryModeDatatable> getListDeli() {
		return listDeli;
	}

	public void fetchData() {
		  try{
		listData.clear();
		List<DeliveryMode> deliveryModelt=deliveryModeService.getDeliveryApprovel();
		if(deliveryModelt.size()>0){
			for (DeliveryMode deliveryMode : deliveryModelt) {
				try {
					DeliveryModeDatatable deliveryModeDatatable=new DeliveryModeDatatable();
					deliveryModeDatatable.setDeliveryMode(deliveryMode.getDeliveryMode());
					deliveryModeDatatable.setDeliveryPk(deliveryMode.getDeliveryModeId());
					deliveryModeDatatable.setCreateDate(deliveryMode.getCreatedDate());
					deliveryModeDatatable.setCreatedBy(deliveryMode.getCreatedBy());
					deliveryModeDatatable.setModifiedBy(deliveryMode.getModifiedBy());
					deliveryModeDatatable.setModifiedDate(deliveryMode.getModifiedDate());
					List<DeliveryModeDesc> deliveryModeLt=deliveryModeService.getAllDescList(deliveryMode.getDeliveryModeId());
					if(deliveryModeLt.size()>0 && deliveryModeLt !=null){
						for (DeliveryModeDesc deliveryModeDesc : deliveryModeLt) {
							if(deliveryModeDesc.getLanguageType().getLanguageId().intValue()==1){
								deliveryModeDatatable.setDeliveryEnglishPk(deliveryModeDesc.getDeliveryModeDescId());
								deliveryModeDatatable.setEnglishDeliveryModeDesc(deliveryModeDesc.getEnglishDeliveryName());
								deliveryModeDatatable.setEngLanguageId(deliveryModeDesc.getLanguageType().getLanguageId());
							}
							if(deliveryModeDesc.getLanguageType().getLanguageId().intValue()==2){
								deliveryModeDatatable.setDeliveryLocalPk(deliveryModeDesc.getDeliveryModeDescId());
								deliveryModeDatatable.setLocalDeliveryModeDesc(deliveryModeDesc.getEnglishDeliveryName());
								deliveryModeDatatable.setAraLanguageId(deliveryModeDesc.getLanguageType().getLanguageId());
							}
						}
						}else{
							continue;
						}
					listData.add(deliveryModeDatatable);
				}catch (Exception exception) {
					  setErrorMessage(exception.getMessage());
						log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
						RequestContext.getCurrentInstance().execute("exception.show();");
						return;
				}
			}
		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while fetchData DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
		
	}

	public void setListDeli(List<DeliveryModeDatatable> listDeli) {
		this.listDeli = listDeli;
	}

	private String checkData;
	private boolean boohideButton = true;
	private boolean boohideSecod = false;
	private boolean booReadonly = false;
	private List<DeliveryModeDatatable> listDeli = new ArrayList<DeliveryModeDatatable>();
	private String userName;

	

	public boolean isBooReadonly() {
		return booReadonly;
	}

	public void setBooReadonly(boolean booReadonly) {
		this.booReadonly = booReadonly;
	}

	public String getCheckData() {
		return checkData;
	}

	public void setCheckData(String checkData) {
		this.checkData = checkData;
	}

	public boolean isBoohideButton() {
		return boohideButton;
	}

	public void setBoohideButton(boolean boohideButton) {
		this.boohideButton = boohideButton;
	}

	public boolean isBoohideSecod() {
		return boohideSecod;
	}

	public void setBoohideSecod(boolean boohideSecod) {
		this.boohideSecod = boohideSecod;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	private String dynamicLabelForActivateDeactivate;

	public String getDynamicLabelForActivateDeactivate() {
		return dynamicLabelForActivateDeactivate;
	}

	public void setDynamicLabelForActivateDeactivate(
			String dynamicLabelForActivateDeactivate) {
		this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	}

	private Boolean renderEditButton = false;

	public Boolean getRenderEditButton() {
		return renderEditButton;
	}

	public void setRenderEditButton(Boolean renderEditButton) {
		this.renderEditButton = renderEditButton;
	}

	public String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void checkStatus(DeliveryModeDatatable datatable) {
		/*if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			datatable.setRemarksCheck(true);
			setIsActive(datatable.getIsActive());
			datatable.setRemarks(getRemarks());
			setApprovedBy(datatable.getApprovedBy());
			setApprovedDate(datatable.getApprovedDate());
			RequestContext.getCurrentInstance().execute("remarks.show();");
			return;

		}else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)){
			  datatable.setActivateRecordCheck(true);
			      RequestContext.getCurrentInstance().execute("activateRecord.show();");
			      return;
		}else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE)&&datatable.getModifiedBy()==null&&datatable.getModifiedDate()==null&&datatable.getApprovedBy()==null&&datatable.getApprovedDate()==null&&datatable.getRemarks()==null) {
				datatable.setPermanetDeleteCheck(true);
				RequestContext.getCurrentInstance().execute("permanentDelete.show();");
				return;
			}else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
				      RequestContext.getCurrentInstance().execute("pending.show();");
				      return;
			    }
		removeRecord(datatable);*/
		 try{
		  if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.PENDING_FOR_APPROVE)) {
			      RequestContext.getCurrentInstance().execute("pending.show();");
			      return;
		    } else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.REMOVE)) {
			      listData.remove(datatable);
			      newListData.remove(datatable);
		    } else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DEACTIVATE)) {
			      datatable.setRemarksCheck(true);
			      setApprovedBy(datatable.getApprovedBy());
			      setApprovedDate(datatable.getApprovedDate());
			      RequestContext.getCurrentInstance().execute("remarks.show();");
		    } else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.ACTIVATE)) {
			      datatable.setActivateRecordCheck(true);
			      setRemarks(datatable.getRemarks());
			      RequestContext.getCurrentInstance().execute("activateRecord.show();");
			      return;
		    } else if (datatable.getDynamicLabelForActivateDeactivate().equalsIgnoreCase(Constants.DELETE) && datatable.getModifiedBy() == null && datatable.getModifiedDate() == null && datatable.getApprovedBy() == null && datatable.getApprovedDate() == null
					&& datatable.getRemarks() == null) {
			      datatable.setPermanetDeleteCheck(true);
			      RequestContext.getCurrentInstance().execute("permanentDelete.show();");
			      return;
		    }
		    if (listData.size() == 0) {
			      setBooRenderDataTable(false);
			      setBooRenderSave(false);
			       setBoohideButton(true); 
			      setBoohideSecod(false);
			      setBooReadonly(false);
			       
		    }
		 }catch(Exception exception){
			   setErrorMessage(exception.getMessage());
				log.error("Error Occured while Check status DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		 }
	}
	
	  public void activateRecord() {
		    try{
		    if (listData.size() > 0) {
			      for (DeliveryModeDatatable deliModeBean : listData) {
					if (deliModeBean.getActivateRecordCheck() != null) {
						  if (deliModeBean.getActivateRecordCheck().equals(true)) {
							    activateRecordtoPending(deliModeBean);
						  }
					}
			      }
		    }
		    }catch(Exception exception){
			      setErrorMessage(exception.getMessage());
				log.error("Error Occured while activateRecord DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		    }
	  }
	  
	  public void activateRecordtoPending(DeliveryModeDatatable datatable)throws Exception{
		    deliveryModeService.activeRecordToPendingForApproval(datatable.getDeliveryPk(),session.getUserName());
		   viewDBData();
	  }

	public void remarkSelectedRecord() throws IOException {
		  try{
		if(getRemarks()!=null && !getRemarks().equals("")) {
		for (DeliveryModeDatatable datatable : listData) {
			if (datatable.getRemarksCheck() != null) {
				if (datatable.getRemarksCheck().equals(true)) {
					datatable.setRemarks(getRemarks());
					updateDelivaryMode(datatable);
					viewDBData();
					clearAllFields();
					setRemarks(null);
				}
			}
		}
		/*try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/DeliveryCodeMaster.xhtml");
		} catch (Exception e) {
			
		}*/
		}
		else
		{
			RequestContext.getCurrentInstance().execute("remarksEmpty.show();");
		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while remarkSelectedRecord DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
	}
	
	public void updateDelivaryMode(DeliveryModeDatatable dataTable){
		  deliveryModeService.upDateActiveRecordtoDeActive(dataTable.getDeliveryPk(), dataTable.getRemarks(), session.getUserName());
	}

	private String activateBy;
	private Date activateDate;

	public String getActivateBy() {
		return activateBy;
	}

	public void setActivateBy(String activateBy) {
		this.activateBy = activateBy;
	}

	public Date getActivateDate() {
		return activateDate;
	}

	public void setActivateDate(Date activateDate) {
		this.activateDate = activateDate;
	}

	private String approvedBy;
	private Date approvedDate;

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

	private Boolean disableSubmitButton;

	public Boolean getDisableSubmitButton() {
		return disableSubmitButton;
	}

	public void setDisableSubmitButton(Boolean disableSubmitButton) {
		this.disableSubmitButton = disableSubmitButton;
	}

	public void hideSubmitButton() {
		if (getEnglishDeliveryModeDesc() != null
				|| getLocalDeliveryModeDesc() != null) {
			setDisableSubmitButton(true);
		} else {
			setDisableSubmitButton(false);
		}
	}

	public void cancelFromActivation() {
		setRemarks(null);
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../treasury/DeliveryCodeMaster.xhtml");

		} catch (Exception exception) {
			  setErrorMessage(exception.getMessage());
				log.error("Error Occured while Saving DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;

		}
	}
	
	public void hardDelete(DeliveryModeDatatable Datatable){
		deliveryModeService.deleteRecordPermentily(Datatable.getDeliveryPk(),Datatable.getDeliveryEnglishPk(),Datatable.getDeliveryLocalPk());
	}
	
	public void confirmPermanentDeleteCancel(){
		  for (DeliveryModeDatatable deliveryModeDatatable : listData) {
			    if(deliveryModeDatatable.getActivateRecordCheck()!=null){
					if(deliveryModeDatatable.getPermanetDeleteCheck().equals(true)){
						  deliveryModeDatatable.setPermanetDeleteCheck(false);  
					}
			    }
		  }
	}
	public void confirmPermanentDelete(){
		  try{
		if(listData.size()>0){
			for (DeliveryModeDatatable deliveryModeDatatable : listData) {
				if(deliveryModeDatatable.getActivateRecordCheck()!=null){
					if(deliveryModeDatatable.getPermanetDeleteCheck().equals(true)){
						hardDelete(deliveryModeDatatable);
						listData.remove(deliveryModeDatatable);
					}
				}
			}
		}
		  }catch(Exception exception){
			    setErrorMessage(exception.getMessage());
				log.error("Error Occured while confirmPermanentDelete DeliveryMode and DeliveryModeDesc Records  :"+ exception.getMessage());
				RequestContext.getCurrentInstance().execute("exception.show();");
				return;
		  }
	}
	
	
	
	  private String errorMessage;
	  private Boolean ifEditClicked=false;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }

	  public Boolean getIfEditClicked() {
	  	  return ifEditClicked;
	  }

	  public void setIfEditClicked(Boolean ifEditClicked) {
	  	  this.ifEditClicked = ifEditClicked;
	  }
	  
	  
	
	
}
