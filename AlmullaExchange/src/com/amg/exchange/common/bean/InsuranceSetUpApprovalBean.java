package com.amg.exchange.common.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.InsuranceMasterDescription;
import com.amg.exchange.common.service.IInsuranceSetUpService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("insuranceSetUpApprovalBean")
@Scope("session")
public class InsuranceSetUpApprovalBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(InsuranceSetUpApprovalBean.class);
	private BigDecimal fromAmount;
	private BigDecimal toAmount;
	private String insuranceMsgOne;
	private String insuranceMsgTwo;
	private String insuranceArabicMsgOne;
	private String insuranceArabicMsgTwo;
	private String effectiveFromDate;
	private String effectiveToDate;
	private BigDecimal insuranceDays;
	private BigDecimal loyaltyPoints;
	private BigDecimal insuranceAmount;
	private BigDecimal insuranceAdditionalAmount;
	private BigDecimal insuranceSetUpPk;
	private Date createdDate;
	private String createdBy;
	private String modifiedBy;
	private Date modifiedDate;
	SessionStateManage sessionStateManage = new SessionStateManage();
	@Autowired
	IInsuranceSetUpService insuranceSetupService;
	
	
	private String errorMessage;
	private Boolean booVisible;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public BigDecimal getFromAmount() {
		return fromAmount;
	}

	public void setFromAmount(BigDecimal fromAmount) {
		this.fromAmount = fromAmount;
	}

	public BigDecimal getToAmount() {
		return toAmount;
	}

	public void setToAmount(BigDecimal toAmount) {
		this.toAmount = toAmount;
	}

	public String getInsuranceMsgOne() {
		return insuranceMsgOne;
	}

	public void setInsuranceMsgOne(String insuranceMsgOne) {
		this.insuranceMsgOne = insuranceMsgOne;
	}

	public String getInsuranceMsgTwo() {
		return insuranceMsgTwo;
	}

	public void setInsuranceMsgTwo(String insuranceMsgTwo) {
		this.insuranceMsgTwo = insuranceMsgTwo;
	}

	public String getInsuranceArabicMsgOne() {
		return insuranceArabicMsgOne;
	}

	public void setInsuranceArabicMsgOne(String insuranceArabicMsgOne) {
		this.insuranceArabicMsgOne = insuranceArabicMsgOne;
	}

	public String getInsuranceArabicMsgTwo() {
		return insuranceArabicMsgTwo;
	}

	public void setInsuranceArabicMsgTwo(String insuranceArabicMsgTwo) {
		this.insuranceArabicMsgTwo = insuranceArabicMsgTwo;
	}

	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}

	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}

	public String getEffectiveToDate() {
		return effectiveToDate;
	}

	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}

	public BigDecimal getInsuranceDays() {
		return insuranceDays;
	}

	public void setInsuranceDays(BigDecimal insuranceDays) {
		this.insuranceDays = insuranceDays;
	}

	public BigDecimal getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(BigDecimal loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public BigDecimal getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(BigDecimal insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public BigDecimal getInsuranceAdditionalAmount() {
		return insuranceAdditionalAmount;
	}

	public void setInsuranceAdditionalAmount(BigDecimal insuranceAdditionalAmount) {
		this.insuranceAdditionalAmount = insuranceAdditionalAmount;
	}

	public BigDecimal getInsuranceSetUpPk() {
		return insuranceSetUpPk;
	}

	public void setInsuranceSetUpPk(BigDecimal insuranceSetUpPk) {
		this.insuranceSetUpPk = insuranceSetUpPk;
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

	List<InsuranceSetUpDataTableBean> approvalList = new ArrayList<InsuranceSetUpDataTableBean>();
	private List<InsuranceMasterDescription> appList = new ArrayList<InsuranceMasterDescription>();

	public List<InsuranceMasterDescription> getAppList() {
		return appList;
	}

	public void setAppList(List<InsuranceMasterDescription> appList) {
		this.appList = appList;
	}

	public List<InsuranceSetUpDataTableBean> getApprovalList() {
		return approvalList;
	}

	public void setApprovalList(List<InsuranceSetUpDataTableBean> approvalList) {
		this.approvalList = approvalList;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationtoApproval() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "medicalinsurancesetupapproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetupapproval.xhtml");
			approvalList.clear();
			approvalRecords();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void approvalRecords() {
		try {
			setBooVisible(false);
			approvalList.clear();
			appList = insuranceSetupService.getAllUnApprovedRecords();
			System.out.println("=================size=" + appList.size());
			if (appList.size() > 0) {
				for (InsuranceMasterDescription insuranceDescObj : appList) {
					InsuranceSetUpDataTableBean insuarnceDTObj = new InsuranceSetUpDataTableBean();
					// insuarnceDTObj.setEffectiveFromDate(insuranceDescObj.getInsuranceMasterId().getFromDate().toString()
					// );
					// insuarnceDTObj.setEffectiveToDate(
					// insuranceDescObj.getInsuranceMasterId().getToDate().toString());
					SimpleDateFormat fromdate = new SimpleDateFormat("dd/MM/yyyy");
					insuarnceDTObj.setEffectiveFromDate(fromdate.format(insuranceDescObj.getInsuranceMasterId().getFromDate()));
					SimpleDateFormat todate = new SimpleDateFormat("dd/MM/yyyy");
					insuarnceDTObj.setEffectiveToDate(todate.format(insuranceDescObj.getInsuranceMasterId().getToDate()));
					insuarnceDTObj.setFromAmount(insuranceDescObj.getInsuranceMasterId().getFromAmount());
					insuarnceDTObj.setToAmount(insuranceDescObj.getInsuranceMasterId().getToAmount());
					insuarnceDTObj.setLoyaltyPoints(insuranceDescObj.getInsuranceMasterId().getInsuranceLoyaltyPoints());
					insuarnceDTObj.setInsuranceDays(insuranceDescObj.getInsuranceMasterId().getInsuranceDays());
					insuarnceDTObj.setInsuranceAdditionalAmount(insuranceDescObj.getInsuranceMasterId().getInsuranceAdditionalAmount());
					insuarnceDTObj.setInsuranceAmount(insuranceDescObj.getInsuranceMasterId().getInsuranceAmount());
					insuarnceDTObj.setCreatedBy(insuranceDescObj.getInsuranceMasterId().getCreatedBy());
					insuarnceDTObj.setCreatedDate(insuranceDescObj.getInsuranceMasterId().getCreatedDate());
					insuarnceDTObj.setModifiedBy(insuranceDescObj.getInsuranceMasterId().getModifiedBy());
					insuarnceDTObj.setModifiedDate(insuranceDescObj.getInsuranceMasterId().getModifiedDate());
					insuarnceDTObj.setInsuranceSetUpPk(insuranceDescObj.getInsuranceMasterId().getInsuranceSetUpId());
					if (insuranceDescObj.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())) {
						insuarnceDTObj.setInsuranceMsgOne(insuranceDescObj.getInsurancePrimaryMessage());
						insuarnceDTObj.setInsuranceMsgTwo(insuranceDescObj.getInsuranceSecondaryMessage());
						insuarnceDTObj.setInsuranceMasterDescPk(insuranceDescObj.getInsuranceMasterDescId());
						if (insuarnceDTObj.getInsuranceMasterDescPk() != null && insuarnceDTObj.getInsuranceMasterDescArabicPk() != null) {
							approvalList.add(insuarnceDTObj);
						}
					}
					List<InsuranceMasterDescription> appList2 = insuranceSetupService.getAllUnApprovedRecords();
					for (InsuranceMasterDescription InsuranceDescObj2 : appList2) {
						if (insuarnceDTObj.getInsuranceSetUpPk().toString().equals(InsuranceDescObj2.getInsuranceMasterId().getInsuranceSetUpId().toString()) && InsuranceDescObj2.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())) {
							insuarnceDTObj.setInsuranceArabicMsgOne(InsuranceDescObj2.getInsurancePrimaryMessage());
							insuarnceDTObj.setInsuranceArabicMsgTwo(InsuranceDescObj2.getInsuranceSecondaryMessage());
							insuarnceDTObj.setInsuranceMasterDescArabicPk(InsuranceDescObj2.getInsuranceMasterDescId());
							if (insuarnceDTObj.getInsuranceMasterDescPk() != null && insuarnceDTObj.getInsuranceMasterDescArabicPk() != null) {
								approvalList.add(insuarnceDTObj);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void navigationEdit(InsuranceSetUpDataTableBean dataTable) {
		
		try {
			setBooVisible(false);
			if ((dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(sessionStateManage.getUserName())) {
				RequestContext.getCurrentInstance().execute("username.show();");
			} else {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetupfinalapproval.xhtml");
				} catch (IOException e) {
					e.printStackTrace();
				}
				setEffectiveFromDate(dataTable.getEffectiveFromDate());
				setEffectiveToDate(dataTable.getEffectiveToDate());
				setInsuranceSetUpPk(dataTable.getInsuranceSetUpPk());
				setFromAmount(dataTable.getFromAmount());
				setToAmount(dataTable.getToAmount());
				setLoyaltyPoints(dataTable.getLoyaltyPoints());
				setInsuranceDays(dataTable.getInsuranceDays());
				setInsuranceMsgOne(dataTable.getInsuranceMsgOne());
				setInsuranceMsgTwo(dataTable.getInsuranceMsgTwo());
				setInsuranceArabicMsgOne(dataTable.getInsuranceArabicMsgOne());
				setInsuranceArabicMsgTwo(dataTable.getInsuranceArabicMsgTwo());
				setInsuranceAdditionalAmount(dataTable.getInsuranceAdditionalAmount());
				setInsuranceAmount(dataTable.getInsuranceAmount());
				setToAmount(dataTable.getToAmount());
				setFromAmount(dataTable.getFromAmount());
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void approve() {
		try {
			setBooVisible(false);
			String approvalMsg = insuranceSetupService.approveRecordRecord(getInsuranceSetUpPk(), sessionStateManage.getUserName());
			if (approvalMsg.equalsIgnoreCase("Success")) {
				RequestContext.getCurrentInstance().execute("approve.show();");
			} else {
				RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
			}
		} catch (Exception e) {
			log.info("*****************************************************" + getErrorMessage());
			setBooVisible(true);
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage("Exception ocurred" + e);
			return;
		}
	}

	public void clickOKButton() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetupapproval.xhtml");
		} catch (Exception e) {
			log.info("This is Navigation problem in swiftPageApproval()");
		}
	}

	public void clickOnOk() {
		approvalList.clear();
		approvalRecords();
		log.info("Entered into clickOnOk() method ");
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetupapproval.xhtml");
		} catch (Exception e) {
			log.error("This is Navigation problem in clickOnOk()");
		}
	}

	public void cancel() {
		approvalList.clear();
		approvalRecords();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/medicalinsurancesetupapproval.xhtml");
		} catch (Exception e) {
			log.error("This is Navigation problem when click cancel()" + e);
		}
	}
}
