package com.amg.exchange.blacklist.bean;

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

import com.amg.exchange.blacklist.model.BlackListDetails;
import com.amg.exchange.blacklist.model.BlackListMaster;
import com.amg.exchange.blacklist.model.BlackListView;
import com.amg.exchange.blacklist.service.IBlackListService;
import com.amg.exchange.common.TokenGeneration;
import com.amg.exchange.common.bean.RuleEngine;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.complaint.bean.ComplaintAssignedBean;
import com.amg.exchange.registration.model.IdentityTypeMaster;
import com.amg.exchange.registration.service.IRemmiterOnlineRegService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("blackListMasterBean")
@Scope("session")
public class BlackListMasterBean<T> implements Serializable {
	  private static final Logger log = Logger.getLogger(BlackListMasterBean.class);
	  private static final long serialVersionUID = 1L;

	  private BigDecimal blackMasterPk;
	  private BigDecimal blackListDetailsPk;
	  private BigDecimal applicationCountryId;
	  private BigDecimal englishId;
	  private String englishName;
	  private String arabicName;
	  private String sequenceNumber;
	  private String englishAddress;
	  private String arabicAddress;
	  private Date dateOfBrith;
	  private String placeOfBrith;
	  private String cbkRefereceNumber;
	  private Date cbkDate;
	  private BigDecimal partyTypeId;
	  private String partyTypeName;
	  private BigDecimal nationalityId;
	  private String nationalityName;
	  private BigDecimal idType;
	  private String idName;
	  private String idNumber;
	  private Date minDate;
	  private BigDecimal tokenKey;
	  private Boolean booSelectOne = false;
	  private Boolean bootext = false;
	  // common variables
	  private String dynamicLabelForActivateDeactivate;
	  private String createdBy;
	  private Date createdDate;
	  private String modifiedBy;
	  private Date modifiedDate;
	  private String approvedBy;
	  private Date approvedDate;
	  private String remarks;
	  private String isActive;
	  // TokenGeneration
	  private TokenGeneration tokenGeneration = new TokenGeneration();
	  // boolean Variables
	  private Boolean booSaveOrExit = false;
	  private Boolean renderEditButton = false;
	  private Boolean booEditButton = false;
	  private Boolean booRenderDataTable = false;
	  private BlackMasterDataTable blackMasterObj = null;

	  private List<BlackMasterDataTable> blackMasterDtList = new CopyOnWriteArrayList<>();
	  private List<BlackMasterDataTable> blackMasterNewDtList = new CopyOnWriteArrayList<>();
	  private List<BlackListView> lstBlackListViews = new ArrayList<BlackListView>();
	  private List<BlackListView> lstViews = new ArrayList<BlackListView>();
	  private List<CountryMasterDesc> countryMasterDescList = new ArrayList<CountryMasterDesc>();
	  private List<IdentityTypeMaster> identityTypeMasterList = new ArrayList<IdentityTypeMaster>();
	  Map<BigDecimal, String> mapIdentityType = new HashMap<BigDecimal, String>();
	  Map<BigDecimal, String> mapNationalityType = new HashMap<BigDecimal, String>();
	  Map<BigDecimal, String> mapBlackListViewlst = new HashMap<BigDecimal, String>();
	  
	  private String errorMessage;
	  
	  @Autowired
	  IBlackListService blackListService;

	  @Autowired
	  IGeneralService<T> generalService;

	  @Autowired
	  IRemmiterOnlineRegService<T> remOnlineReg;

	  @Autowired
	  RuleEngine<T> ruleEngine;

	  SessionStateManage sessionStateManage = new SessionStateManage();

	  public BigDecimal getBlackMasterPk() {
		    return blackMasterPk;
	  }

	  public void setBlackMasterPk(BigDecimal blackMasterPk) {
		    this.blackMasterPk = blackMasterPk;
	  }

	  public BigDecimal getBlackListDetailsPk() {
		    return blackListDetailsPk;
	  }

	  public void setBlackListDetailsPk(BigDecimal blackListDetailsPk) {
		    this.blackListDetailsPk = blackListDetailsPk;
	  }

	  public BigDecimal getApplicationCountryId() {
		    return applicationCountryId;
	  }

	  public void setApplicationCountryId(BigDecimal applicationCountryId) {
		    this.applicationCountryId = applicationCountryId;
	  }

	  public BigDecimal getEnglishId() {
		    return englishId;
	  }

	  public void setEnglishId(BigDecimal englishId) {
		    this.englishId = englishId;
	  }

	  public String getEnglishName() {
		    return englishName;
	  }

	  public void setEnglishName(String englishName) {
		    this.englishName = englishName;
	  }

	  public String getArabicName() {
		    return arabicName;
	  }

	  public void setArabicName(String arabicName) {
		    this.arabicName = arabicName;
	  }

	  public String getSequenceNumber() {
		    return sequenceNumber;
	  }

	  public void setSequenceNumber(String sequenceNumber) {
		    this.sequenceNumber = sequenceNumber;
	  }

	  public String getEnglishAddress() {
		    return englishAddress;
	  }

	  public void setEnglishAddress(String englishAddress) {
		    this.englishAddress = englishAddress;
	  }

	  public String getArabicAddress() {
		    return arabicAddress;
	  }

	  public void setArabicAddress(String arabicAddress) {
		    this.arabicAddress = arabicAddress;
	  }

	  public Date getDateOfBrith() {
		    return dateOfBrith;
	  }

	  public void setDateOfBrith(Date dateOfBrith) {
		    this.dateOfBrith = dateOfBrith;
	  }

	  public String getPlaceOfBrith() {
		    return placeOfBrith;
	  }

	  public void setPlaceOfBrith(String placeOfBrith) {
		    this.placeOfBrith = placeOfBrith;
	  }

	  public String getCbkRefereceNumber() {
		    return cbkRefereceNumber;
	  }

	  public void setCbkRefereceNumber(String cbkRefereceNumber) {
		    this.cbkRefereceNumber = cbkRefereceNumber;
	  }

	  public Date getCbkDate() {
		    return cbkDate;
	  }

	  public void setCbkDate(Date cbkDate) {
		    this.cbkDate = cbkDate;
	  }

	  public BigDecimal getPartyTypeId() {
		    return partyTypeId;
	  }

	  public void setPartyTypeId(BigDecimal partyTypeId) {
		    this.partyTypeId = partyTypeId;
	  }

	  public String getPartyTypeName() {
		    return partyTypeName;
	  }

	  public void setPartyTypeName(String partyTypeName) {
		    this.partyTypeName = partyTypeName;
	  }

	  public BigDecimal getNationalityId() {
		    return nationalityId;
	  }

	  public void setNationalityId(BigDecimal nationalityId) {
		    this.nationalityId = nationalityId;
	  }

	  public String getNationalityName() {
		    return nationalityName;
	  }

	  public void setNationalityName(String nationalityName) {
		    this.nationalityName = nationalityName;
	  }

	  public BigDecimal getIdType() {
		    return idType;
	  }

	  public void setIdType(BigDecimal idType) {
		    this.idType = idType;
	  }

	  public String getIdName() {
		    return idName;
	  }

	  public void setIdName(String idName) {
		    this.idName = idName;
	  }

	  public String getIdNumber() {
		    return idNumber;
	  }

	  public void setIdNumber(String idNumber) {
		    this.idNumber = idNumber;
	  }

	  public String getDynamicLabelForActivateDeactivate() {
		    return dynamicLabelForActivateDeactivate;
	  }

	  public void setDynamicLabelForActivateDeactivate(String dynamicLabelForActivateDeactivate) {
		    this.dynamicLabelForActivateDeactivate = dynamicLabelForActivateDeactivate;
	  }

	  public Boolean getRenderEditButton() {
		    return renderEditButton;
	  }

	  public void setRenderEditButton(Boolean renderEditButton) {
		    this.renderEditButton = renderEditButton;
	  }

	  public Boolean getBooEditButton() {
		    return booEditButton;
	  }

	  public void setBooEditButton(Boolean booEditButton) {
		    this.booEditButton = booEditButton;
	  }

	  public Boolean getBooSaveOrExit() {
		    return booSaveOrExit;
	  }

	  public void setBooSaveOrExit(Boolean booSaveOrExit) {
		    this.booSaveOrExit = booSaveOrExit;
	  }

	  public List<BlackMasterDataTable> getBlackMasterDtList() {
		    return blackMasterDtList;
	  }

	  public void setBlackMasterDtList(List<BlackMasterDataTable> blackMasterDtList) {
		    this.blackMasterDtList = blackMasterDtList;
	  }

	  public Boolean getBooRenderDataTable() {
		    return booRenderDataTable;
	  }

	  public void setBooRenderDataTable(Boolean booRenderDataTable) {
		    this.booRenderDataTable = booRenderDataTable;
	  }

	  public List<BlackMasterDataTable> getBlackMasterNewDtList() {
		    return blackMasterNewDtList;
	  }

	  public void setBlackMasterNewDtList(List<BlackMasterDataTable> blackMasterNewDtList) {
		    this.blackMasterNewDtList = blackMasterNewDtList;
	  }

	  public List<BlackListView> getLstBlackListViews() {
		    return lstBlackListViews;
	  }

	  public void setLstBlackListViews(List<BlackListView> lstBlackListViews) {
		    this.lstBlackListViews = lstBlackListViews;
	  }

	  public Date getMinDate() {
		    return new Date();
	  }

	  public void setMinDate(Date minDate) {
		    this.minDate = minDate;
	  }

	  public List<CountryMasterDesc> getCountryMasterDescList() {
		    return countryMasterDescList;
	  }

	  public void setCountryMasterDescList(List<CountryMasterDesc> countryMasterDescList) {
		    this.countryMasterDescList = countryMasterDescList;
	  }

	  public List<BlackListView> getLstViews() {
		    return lstViews;
	  }

	  public void setLstViews(List<BlackListView> lstViews) {
		    this.lstViews = lstViews;
	  }

	  public BigDecimal getTokenKey() {
		    return tokenKey;
	  }

	  public void setTokenKey(BigDecimal tokenKey) {
		    this.tokenKey = tokenKey;
	  }

	  public TokenGeneration getTokenGeneration() {
		    return tokenGeneration;
	  }

	  public void setTokenGeneration(TokenGeneration tokenGeneration) {
		    this.tokenGeneration = tokenGeneration;
	  }

	  public IRemmiterOnlineRegService<T> getRemOnlineReg() {
		    return remOnlineReg;
	  }

	  public void setRemOnlineReg(IRemmiterOnlineRegService<T> remOnlineReg) {
		    this.remOnlineReg = remOnlineReg;
	  }

	  public Boolean getBooSelectOne() {
		    return booSelectOne;
	  }

	  public void setBooSelectOne(Boolean booSelectOne) {
		    this.booSelectOne = booSelectOne;
	  }

	  public Boolean getBootext() {
		    return bootext;
	  }

	  public void setBootext(Boolean bootext) {
		    this.bootext = bootext;
	  }

	  public List<IdentityTypeMaster> getIdentityTypeMasterList() {
		    return identityTypeMasterList;
	  }

	  public void setIdentityTypeMasterList(List<IdentityTypeMaster> identityTypeMasterList) {
		    this.identityTypeMasterList = identityTypeMasterList;
	  }

	  public Map<BigDecimal, String> getMapIdentityType() {
		    return mapIdentityType;
	  }

	  public void setMapIdentityType(Map<BigDecimal, String> mapIdentityType) {
		    this.mapIdentityType = mapIdentityType;
	  }

	  public Map<BigDecimal, String> getMapNationalityType() {
		    return mapNationalityType;
	  }

	  public void setMapNationalityType(Map<BigDecimal, String> mapNationalityType) {
		    this.mapNationalityType = mapNationalityType;
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

	  public String getIsActive() {
		    return isActive;
	  }

	  public void setIsActive(String isActive) {
		    this.isActive = isActive;
	  }

	  public BlackMasterDataTable getBlackMasterObj() {
		    return blackMasterObj;
	  }

	  public void setBlackMasterObj(BlackMasterDataTable blackMasterObj) {
		    this.blackMasterObj = blackMasterObj;
	  }

	  public Map<BigDecimal, String> getMapBlackListViewlst() {
		    return mapBlackListViewlst;
	  }

	  public void setMapBlackListViewlst(Map<BigDecimal, String> mapBlackListViewlst) {
		    this.mapBlackListViewlst = mapBlackListViewlst;
	  }
	    

	  public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;

	// page navigation
	  public void blackMasterPageNavigation() {
		    blackMasterDtList.clear();
		    lstBlackListViews.clear();
		    lstViews.clear();
		    countryMasterDescList.clear();
		    identityTypeMasterList.clear();
		    setBooSaveOrExit(false);
		    setBooRenderDataTable(false);
		    setBooSelectOne(true);
		    setBootext(false);
		    clearAllFields();
		    // form loading to fetch all Desc
		    fetchDataFromView();
		    try {
		    	loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "BlackListMaster.xhtml");
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../blackList/BlackListMaster.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }

	  }

	  public void fetchDataFromView() {
		  try{
		    // to fetch All English Names
		    mapBlackListViewlst.clear();
		    lstBlackListViews = blackListService.toFetchOnFormLoadingForAllViewData();
		    for (BlackListView blackListView : lstBlackListViews) {
			      mapBlackListViewlst.put(blackListView.getIdNo(), blackListView.getEnglishName());
		    }
		    // to fetch Nationality Names
		    mapNationalityType.clear();
		    countryMasterDescList = generalService.getCountryList(sessionStateManage.getLanguageId());
		    for (CountryMasterDesc countryMasterDesc : countryMasterDescList) {
			      mapNationalityType.put(countryMasterDesc.getFsCountryMaster().getCountryId(), countryMasterDesc.getNationality());
		    }
		    // to fetch Identity type
		    mapIdentityType.clear();
		    identityTypeMasterList = blackListService.toFetchIdentityTypeTypeBasedOnApplicatonCountryId(sessionStateManage.getCountryId());
		    for (IdentityTypeMaster identityTypeMaster : identityTypeMasterList) {
			      mapIdentityType.put(identityTypeMaster.getBusinessComponentId(), identityTypeMaster.getIdentityType());
		    }
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  // ajax Validation
	  public void selectAnyOneAddress() {
		    if (getEnglishId() == null || getArabicName() == null) {
			      RequestContext.getCurrentInstance().execute("selectAnyOne.show();");
			      return;
		    }
	  }

	  // auto generate Seq number
	  public void autoGenerateSequenceNumber() {
		  try{
		    if (getEnglishId() != null) {
			      lstViews = blackListService.toFetchSequenceNumberBasedOnEnglishName(getEnglishId());
			      setBooSelectOne(true);
			      setBootext(false);
		    }
		  } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  public void onSelectSeqNumberToPopulateDialog() {
		    if (new BigDecimal(getSequenceNumber()) != null) {
			      RequestContext.getCurrentInstance().execute("seqNumber.show();");
			      return;
		    }
	  }

	  // on dialog action for seq increment yes/no
	  // if click on Yes

	  public void autoIncrementOfSequenceNumber() {
		  try{
		    lstViews.clear();
		    setBooSelectOne(false);
		    setBootext(true);
		    setEnglishAddress(null);
		    setArabicAddress(null);
		    setDateOfBrith(null);
		    setPlaceOfBrith(null);
		    setPartyTypeId(null);
		    setPartyTypeName(null);
		    setNationalityId(null);
		    setNationalityName(null);
		    setIdType(null);
		    setIdName(null);
		    setIdNumber(null);
		    String strToken = null;
		    try {
			      strToken = getTokenGeneration().getRandomIdentifier(8);
		    } catch (Exception e) {
			      // log.info("Problem in Token Generation" + e);
		    }
		    boolean tokenConfirm = true;
		    while (tokenConfirm) {
			      try {
					if (getRemOnlineReg().CheckTokenAvailable(strToken).size() > 0) {
						  tokenConfirm = true;
						  strToken = getTokenGeneration().getRandomIdentifier(8);
					} else {
						  tokenConfirm = false;
					}
			      } catch (Exception e) {
					tokenConfirm = false;
			      }
		    }

		    setSequenceNumber(strToken);
		  } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  // if click on NO
	  public void noIncrementofSeq() {
		    setEnglishId(null);
		    setEnglishName(null);
		    setArabicName(null);
		    setSequenceNumber(null);
		    lstViews.clear();
		    setBooSelectOne(true);
		    setBootext(false);

	  }

	  // id Number Validation
	  public void toSelectBasedOnIdTypeValidation() {
		    mapIdentityType.clear();
		    try{
		    mapIdentityType = ruleEngine.getComponentData("Identity Type");
		    BigDecimal identityTpeIds = null;
		    for (Map.Entry<BigDecimal, String> entry : mapIdentityType.entrySet()) {
			      if (entry.getValue().equalsIgnoreCase(Constants.CIVILID)) {
					identityTpeIds = entry.getKey();

					break;
			      }
		    }
		    if (getIdType() != null && getIdType().intValue() == identityTpeIds.intValue()) {
			      try {
					String returnString = generalService.getCivilIdStatus(new BigDecimal(getIdNumber()));
					if (returnString.equalsIgnoreCase("N")) {
						  setIdNumber(null);
						  RequestContext.getCurrentInstance().execute("selectAnyOne.show();");
						  return;
					}
			      } catch (Exception e) {

			      }
		    }
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  // clear all Fields
	  public void clearAllFields() {
		    setBlackMasterPk(null);
		    setEnglishName(null);
		    setEnglishId(null);
		    setArabicName(null);
		    setSequenceNumber(null);
		    setEnglishAddress(null);
		    setArabicAddress(null);
		    setDateOfBrith(null);
		    setPlaceOfBrith(null);
		    setCbkDate(null);
		    setCbkRefereceNumber(null);
		    setPartyTypeId(null);
		    setPartyTypeName(null);
		    setNationalityId(null);
		    setNationalityName(null);
		    setIdType(null);
		    setIdName(null);
		    setIdNumber(null);
		    lstViews.clear();
	  }

	  public void duplicateCheckForDataTable() {
		  
		  try{
		    if (blackMasterDtList.size() > 0) {
			      if (getNationalityId() != null && getIdType() != null && getIdNumber() != null) {
					for (BlackMasterDataTable blackMasterDTOBJ : blackMasterDtList) {
						  if (blackMasterDTOBJ.getNationalityId().equals(getNationalityId()) && blackMasterDTOBJ.getIdType().equals(getIdType()) && blackMasterDTOBJ.getIdNumber().equals(getIdNumber())) {
							    clearAllFields();
							    RequestContext.getCurrentInstance().execute("datatable.show();");
							    return;
						  }
					}
			      }
		    }
		    addRecordsToDataTable();
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  public void addRecordsToDataTable() {
		    BlackMasterDataTable blackMasterDtObj = new BlackMasterDataTable();
		    blackMasterDtObj.setApplicationCountryId(sessionStateManage.getCountryId());
		    blackMasterDtObj.setEnglishId(getEnglishId());
		    blackMasterDtObj.setEnglishName(mapBlackListViewlst.get(getEnglishId()));
		    blackMasterDtObj.setArabicName(getArabicName());
		    blackMasterDtObj.setSequenceNumber(getSequenceNumber());
		    blackMasterDtObj.setEnglishAddress(getEnglishAddress());
		    blackMasterDtObj.setArabicAddress(getArabicAddress());
		    blackMasterDtObj.setDateOfBrith(getDateOfBrith());
		    blackMasterDtObj.setPlaceOfBrith(getPlaceOfBrith());
		    blackMasterDtObj.setCbkRefereceNumber(getCbkRefereceNumber());
		    blackMasterDtObj.setCbkDate(getCbkDate());
		    blackMasterDtObj.setRenderEditButton(true);
		    if (getPartyTypeId() != null) {
			      if (getPartyTypeId().intValue() == 1) {
					blackMasterDtObj.setPartyTypeName("INDIVIDUAL");
			      } else {
					blackMasterDtObj.setPartyTypeName("CORPORATE");
			      }
		    } else {
			      blackMasterDtObj.setPartyTypeName("");
		    }
		    blackMasterDtObj.setNationalityId(getNationalityId());
		    blackMasterDtObj.setNationalityName(mapNationalityType.get(getNationalityId()));
		    blackMasterDtObj.setIdType(getIdType());
		    blackMasterDtObj.setIdName(mapIdentityType.get(getIdType()));
		    blackMasterDtObj.setIdNumber(getIdNumber());
		    blackMasterDtObj.setCreatedBy(getCreatedBy());
		    blackMasterDtObj.setCreatedDate(getCreatedDate());
		    if (getBlackMasterPk() != null) {
			      blackMasterDtObj.setBlackMasterPk(blackListService.tofetchBlackListPkBasedOnEnglishName(getEnglishName()));
			      if (blackMasterDtObj.getEnglishName().equalsIgnoreCase(blackMasterObj.getEnglishName()) && blackMasterDtObj.getArabicName().equalsIgnoreCase(blackMasterObj.getArabicName()) && blackMasterDtObj.getSequenceNumber().equalsIgnoreCase(blackMasterObj.getSequenceNumber())
						  && blackMasterDtObj.getEnglishAddress().equalsIgnoreCase(blackMasterObj.getEnglishAddress()) && blackMasterDtObj.getArabicAddress().equalsIgnoreCase(blackMasterObj.getArabicAddress())
						  && blackMasterDtObj.getDateOfBrith().equals(blackMasterObj.getDateOfBrith()) && blackMasterDtObj.getPlaceOfBrith().equalsIgnoreCase(blackMasterObj.getPlaceOfBrith())
						  && blackMasterDtObj.getCbkRefereceNumber().equals(blackMasterObj.getCbkRefereceNumber()) && blackMasterDtObj.getCbkDate().equals(blackMasterObj.getCbkDate()) && blackMasterDtObj.getPartyTypeId().equals(blackMasterObj.getPartyTypeId())
						  && blackMasterDtObj.getNationalityId().equals(blackMasterObj.getNationalityId()) && blackMasterDtObj.getIdType().equals(blackMasterObj.getIdType()) && blackMasterDtObj.getIdNumber().equals(blackMasterObj.getIdNumber())) {
					blackMasterDtObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					blackMasterDtObj.setIsActive(getIsActive());
					blackMasterDtObj.setModifiedBy(getModifiedBy());
					blackMasterDtObj.setModifiedDate(getModifiedDate());
					blackMasterDtObj.setApprovedBy(getApprovedBy());
					blackMasterDtObj.setApprovedDate(getApprovedDate());
					blackMasterDtObj.setRemarks(getRemarks());
			      } else {
					blackMasterDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					blackMasterDtObj.setIsActive(Constants.U);
					blackMasterDtObj.setModifiedBy(sessionStateManage.getUserName());
					blackMasterDtObj.setModifiedDate(new Date());
					blackMasterDtObj.setApprovedBy(null);
					blackMasterDtObj.setApprovedDate(null);
					blackMasterDtObj.setRemarks(null);
			      }
		    } else {
			      blackMasterDtObj.setCreatedBy(sessionStateManage.getUserName());
			      blackMasterDtObj.setCreatedDate(new Date());
			      blackMasterDtObj.setIsActive(Constants.U);
			      blackMasterDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		    }
		    if (getBlackListDetailsPk() != null) {
			      if (blackMasterDtObj.getEnglishName().equalsIgnoreCase(blackMasterObj.getEnglishName()) && blackMasterDtObj.getArabicName().equalsIgnoreCase(blackMasterObj.getArabicName()) && blackMasterDtObj.getSequenceNumber().equalsIgnoreCase(blackMasterObj.getSequenceNumber())
						  && blackMasterDtObj.getEnglishAddress().equalsIgnoreCase(blackMasterObj.getEnglishAddress()) && blackMasterDtObj.getArabicAddress().equalsIgnoreCase(blackMasterObj.getArabicAddress())
						  && blackMasterDtObj.getDateOfBrith().equals(blackMasterObj.getDateOfBrith()) && blackMasterDtObj.getPlaceOfBrith().equalsIgnoreCase(blackMasterObj.getPlaceOfBrith())
						  && blackMasterDtObj.getCbkRefereceNumber().equals(blackMasterObj.getCbkRefereceNumber()) && blackMasterDtObj.getCbkDate().equals(blackMasterObj.getCbkDate()) && blackMasterDtObj.getPartyTypeId().equals(blackMasterObj.getPartyTypeId())
						  && blackMasterDtObj.getNationalityId().equals(blackMasterObj.getNationalityId()) && blackMasterDtObj.getIdType().equals(blackMasterObj.getIdType()) && blackMasterDtObj.getIdNumber().equals(blackMasterObj.getIdNumber())) {
					blackMasterDtObj.setDynamicLabelForActivateDeactivate(getDynamicLabelForActivateDeactivate());
					blackMasterDtObj.setIsActive(getIsActive());
					blackMasterDtObj.setModifiedBy(getModifiedBy());
					blackMasterDtObj.setModifiedDate(getModifiedDate());
					blackMasterDtObj.setApprovedBy(getApprovedBy());
					blackMasterDtObj.setApprovedDate(getApprovedDate());
					blackMasterDtObj.setRemarks(getRemarks());
			      } else {
					blackMasterDtObj.setDynamicLabelForActivateDeactivate(Constants.PENDING_FOR_APPROVE);
					blackMasterDtObj.setIsActive(Constants.U);
					blackMasterDtObj.setModifiedBy(sessionStateManage.getUserName());
					blackMasterDtObj.setModifiedDate(new Date());
					blackMasterDtObj.setApprovedBy(null);
					blackMasterDtObj.setApprovedDate(null);
					blackMasterDtObj.setRemarks(null);
			      }
		    } else {
			      blackMasterDtObj.setCreatedBy(sessionStateManage.getUserName());
			      blackMasterDtObj.setCreatedDate(new Date());
			      blackMasterDtObj.setIsActive(Constants.U);
			      blackMasterDtObj.setDynamicLabelForActivateDeactivate(Constants.REMOVE);
		    }

		    blackMasterDtList.add(blackMasterDtObj);
		    if (getBlackMasterPk() == null) {
			      blackMasterNewDtList.addAll(blackMasterDtList);
		    }
		    setBooRenderDataTable(true);
		    setBooSaveOrExit(true);
		    clearAllFields();
	  }

	  // edit button
	  public void edit(BlackMasterDataTable dataTable) {
		  
		  try{
		    setBlackMasterObj(dataTable);
		    setApplicationCountryId(dataTable.getApplicationCountryId());
		    setBlackMasterPk(dataTable.getBlackMasterPk());
		    setEnglishId(dataTable.getEnglishId());
		    setArabicName(dataTable.getArabicName());
		    setSequenceNumber(dataTable.getSequenceNumber());
		    setEnglishAddress(dataTable.getEnglishAddress());
		    setArabicAddress(dataTable.getArabicAddress());
		    setDateOfBrith(dataTable.getDateOfBrith());
		    setPlaceOfBrith(dataTable.getPlaceOfBrith());
		    setCbkRefereceNumber(dataTable.getCbkRefereceNumber());
		    setCbkDate(dataTable.getCbkDate());
		    if (dataTable.getPartyTypeName() != null) {
			      if (dataTable.getPartyTypeName().equalsIgnoreCase("INDIVIDUAL")) {
					setPartyTypeId(new BigDecimal(1));
			      } else {
					setPartyTypeId(new BigDecimal(2));
			      }
		    }
		    setNationalityId(dataTable.getNationalityId());
		    setIdType(dataTable.getIdType());
		    setIdNumber(dataTable.getIdNumber());
		    setCreatedBy(dataTable.getCreatedBy());
		    setCreatedDate(dataTable.getCreatedDate());
		    setModifiedBy(dataTable.getModifiedBy());
		    setModifiedDate(dataTable.getModifiedDate());
		    setApprovedBy(dataTable.getApprovedBy());
		    setApprovedDate(dataTable.getApprovedDate());
		    setIsActive(dataTable.getIsActive());
		    setDynamicLabelForActivateDeactivate(dataTable.getDynamicLabelForActivateDeactivate());
		    blackMasterDtList.remove(dataTable);
		    blackMasterNewDtList.remove(dataTable);
		    if (blackMasterDtList.size() == 0) {
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
		    } else {
			      setBooRenderDataTable(true);
			      setBooSaveOrExit(true);
		    }
		  }catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  // Save Function
	  public void saveBlackListMaster() {
		    try {
			      for (BlackMasterDataTable blackMasterDtObj : blackMasterDtList) {
					BlackListMaster blackListMaster = new BlackListMaster();
					blackListMaster.setBlackListMasterId(blackMasterDtObj.getBlackMasterPk());
					blackListMaster.setApplicationCountryId(blackMasterDtObj.getApplicationCountryId());
					blackListMaster.setFullName(blackMasterDtObj.getEnglishName());
					blackListMaster.setArabicFullName(blackMasterDtObj.getArabicName());
					blackListMaster.setSeqNo(blackMasterDtObj.getSequenceNumber());
					blackListMaster.setAddress(blackMasterDtObj.getEnglishAddress());
					blackListMaster.setArabicFullName(blackMasterDtObj.getArabicName());
					blackListMaster.setDateOfDate(blackMasterDtObj.getDateOfBrith());
					blackListMaster.setPob(blackMasterDtObj.getPlaceOfBrith());
					blackListMaster.setCbkRefferenceNo(blackMasterDtObj.getCbkRefereceNumber());
					blackListMaster.setCbkRefferenceDate(blackMasterDtObj.getCbkDate());
					if (blackMasterDtObj.getIdName().equalsIgnoreCase("INDIVIDUAL")) {
						  blackListMaster.setPartyType("1");
					} else {
						  blackListMaster.setPartyType("2");
					}
					blackListMaster.setCreatedBy(blackMasterDtObj.getCreatedBy());
					blackListMaster.setCreatedDate(blackMasterDtObj.getCreatedDate());
					blackListMaster.setModifiedBy(blackMasterDtObj.getModifiedBy());
					blackListMaster.setModifiedDate(blackMasterDtObj.getModifiedDate());
					blackListMaster.setApprovedBy(blackMasterDtObj.getApprovedBy());
					blackListMaster.setApprovedDate(blackMasterDtObj.getApprovedDate());
					blackListMaster.setIsactive(blackMasterDtObj.getIsActive());
					blackListMaster.setRemarks(blackMasterDtObj.getRemarks());
					blackListService.saveBlackListMaster(blackListMaster);

					BlackListDetails blackListDetails = new BlackListDetails();
					blackListDetails.setBlackListDetailId(blackMasterDtObj.getBlackListDetailsPk());
					//blackListDetails.setApplicatonCountryId(blackMasterDtObj.getApplicationCountryId());
					// black List Master Id
					BlackListMaster blackListMaster2 = new BlackListMaster();
					blackListMaster2.setBlackListMasterId(blackMasterDtObj.getBlackMasterPk());
					blackListDetails.setBlackListMasterId(blackListMaster2);
					blackListDetails.setFullName(blackMasterDtObj.getEnglishName());
					blackListDetails.setNationalityCode(blackMasterDtObj.getNationalityId().toPlainString());
					blackListDetails.setIdType(blackMasterDtObj.getIdType().toPlainString());
					blackListDetails.setIdNo(blackMasterDtObj.getIdNumber());
					//blackListDetails.setSeqNo(blackMasterDtObj.getSequenceNumber());
					blackListDetails.setCreatedBy(blackMasterDtObj.getCreatedBy());
					blackListDetails.setCreatedDate(blackMasterDtObj.getCreatedDate());
					blackListDetails.setModifiedBy(blackMasterDtObj.getModifiedBy());
					blackListDetails.setModifiedDate(blackMasterDtObj.getModifiedDate());
					blackListDetails.setApprovedBy(blackMasterDtObj.getApprovedBy());
					blackListDetails.setApprovedDate(blackMasterDtObj.getApprovedDate());
					blackListDetails.setRemarks(blackMasterDtObj.getRemarks());
					blackListDetails.setIsactive(blackMasterDtObj.getIsActive());
					blackListService.saveBlackListDetails(blackListDetails);
					//blackListService.saveBlackListDetailsAndBlackListMaster(blackListDetails,blackMasterDtObj.getBlackMasterPk());

			      }
			      blackMasterDtList.clear();
			      blackMasterNewDtList.clear();
			      setBooRenderDataTable(false);
			      setBooSaveOrExit(false);
			      clearAllFields();
			      RequestContext.getCurrentInstance().execute("complete.show();");
			      return;
		    } catch (NullPointerException ne) {
				// LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
				setErrorMessage("Method Name::save");
				RequestContext.getCurrentInstance().execute("nullPointerId.show();");
				return;
			} catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			  setErrorMessage(exception.getMessage()); 
			  RequestContext.getCurrentInstance().execute("error.show();");
			  return;       
			  }
	  }

	  public void clickOnOKSave() {
		    blackMasterDtList.clear();
		    blackMasterNewDtList.clear();
		    clearAllFields();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../blackList/BlackListMaster.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }
	  }
	  
	  public void blackListAndDetailsNotSaved(){
		    blackMasterDtList.clear();
		    blackMasterNewDtList.clear();
		    clearAllFields();
		    setBooRenderDataTable(false);
		    setBooSaveOrExit(false);
		    try {
			      FacesContext.getCurrentInstance().getExternalContext().redirect("../blackList/BlackListMaster.xhtml");
		    } catch (Exception e) {
			      e.printStackTrace();
		    }  
	  }
}
