package com.amg.exchange.masters;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.model.OwsSchedule;
import com.amg.exchange.registration.service.ICountryMasterservice;
import com.amg.exchange.registration.service.IOwService;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.treasury.service.IBankApplicabilityService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("owsscheduleBean")
@Scope("session")
public class OwsScheduleBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOGGER = Logger.getLogger(OwsScheduleBean.class);
	private BigDecimal owsScheduleId;
	private BigDecimal bankId;
	private BigDecimal bankcountryId;
	private BigDecimal applicationcountryId;
	private String liveIndicator;
	private String neft;
	private String rtgs;
	private String xml;
	private String owsFlag;
	private BigDecimal repeatInterval;
	private String errorMessage;
	private Date sneftStartTime;
	private Date sneftEndTime;
	private Date srtgsStartTime;
	private Date srtgsEndTime;
	private Date sweStartTime;
	private Date sweEndTime;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	private String isActive;
	private SessionStateManage sessionmanage = new SessionStateManage();
	private List<BankApplicability> bankList = new ArrayList<BankApplicability>();
	private List<BankApplicability> FbankList = new ArrayList<BankApplicability>();
	@Autowired
	IOwService iOwService;
	private boolean isPopulate;
	@Autowired
	IBankApplicabilityService<T> bankApplicabilityService;
	@Autowired
	ICountryMasterservice iCountryMasterservice;

	public boolean isPopulate() {
		return isPopulate;
	}

	public void setPopulate(boolean isPopulate) {
		this.isPopulate = isPopulate;
	}

	public BigDecimal getOwsScheduleId() {
		return owsScheduleId;
	}

	public void setOwsScheduleId(BigDecimal owsScheduleId) {
		this.owsScheduleId = owsScheduleId;
	}

	

	public BigDecimal getBankcountryId() {
		return bankcountryId;
	}

	public void setBankcountryId(BigDecimal bankcountryId) {
		this.bankcountryId = bankcountryId;
	}

	public BigDecimal getApplicationcountryId() {
		return applicationcountryId;
	}

	public void setApplicationcountryId(BigDecimal applicationcountryId) {
		this.applicationcountryId = applicationcountryId;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
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

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<BankApplicability> getFbankList() {
		return FbankList;
	}

	public Date getSneftStartTime() {
		return sneftStartTime;
	}

	public void setSneftStartTime(Date sneftStartTime) {
		this.sneftStartTime = sneftStartTime;
	}

	public Date getSneftEndTime() {
		return sneftEndTime;
	}

	public void setSneftEndTime(Date sneftEndTime) {
		this.sneftEndTime = sneftEndTime;
	}

	public Date getSrtgsStartTime() {
		return srtgsStartTime;
	}

	public void setSrtgsStartTime(Date srtgsStartTime) {
		this.srtgsStartTime = srtgsStartTime;
	}

	public Date getSrtgsEndTime() {
		return srtgsEndTime;
	}

	public void setSrtgsEndTime(Date srtgsEndTime) {
		this.srtgsEndTime = srtgsEndTime;
	}

	public Date getSweStartTime() {
		return sweStartTime;
	}

	public void setSweStartTime(Date sweStartTime) {
		this.sweStartTime = sweStartTime;
	}

	public Date getSweEndTime() {
		return sweEndTime;
	}

	public void setSweEndTime(Date sweEndTime) {
		this.sweEndTime = sweEndTime;
	}

	public void setFbankList(List<BankApplicability> fbankList) {
		FbankList = fbankList;
	}

	public BigDecimal getBankId() {
		return bankId;
	}

	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}

	public String getLiveIndicator() {
		return liveIndicator;
	}

	public void setLiveIndicator(String liveIndicator) {
		this.liveIndicator = liveIndicator;
	}

	public String getNeft() {
		return neft;
	}

	public void setNeft(String neft) {
		this.neft = neft;
	}

	public String getRtgs() {
		return rtgs;
	}

	public void setRtgs(String rtgs) {
		this.rtgs = rtgs;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public String getOwsFlag() {
		return owsFlag;
	}

	public void setOwsFlag(String owsFlag) {
		this.owsFlag = owsFlag;
	}

	public BigDecimal getRepeatInterval() {
		return repeatInterval;
	}

	public void setRepeatInterval(BigDecimal repeatInterval) {
		this.repeatInterval = repeatInterval;
	}

	public List<BankApplicability> getBankList() {
		return bankList;
	}

	public void setBankList(List<BankApplicability> bankList) {
		this.bankList = bankList;
	}

	private List<CountryMasterDesc> bankApplicabilityCountryList = new ArrayList<CountryMasterDesc>();
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {
		clearAll();
		if (sessionmanage.getUserName() == null) {
		}
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionmanage.getCountryId(), sessionmanage.getUserType(), sessionmanage.getUserName(), "owsschedule.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../customermaster/owsschedule.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("Exit  into pageNavigation");
	}

	public void clearAll() {
		setOwsScheduleId(null);
		setBankId(null);
		setBankcountryId(null);
		setLiveIndicator(null);
		setNeft(null);
		setRtgs(null);
		setXml(null);
		setOwsFlag(null);
		setRepeatInterval(null);
		setSneftStartTime(null);
		setSneftEndTime(null);
		setSrtgsStartTime(null);
		setSrtgsEndTime(null);
		setSweStartTime(null);
		setSweEndTime(null);
		setApplicationcountryId(null);
	}

	List<BigDecimal> appCountryList = new ArrayList<BigDecimal>();
	
	
	private Boolean booVisible;

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public List<CountryMasterDesc> getBankApplicabilityCountryList() {
		try {
			LOGGER.info(sessionmanage.getCountryId());
			appCountryList = bankApplicabilityService.getCorrespondingCountryList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_CORR_BANK);
			if (appCountryList != null) {
				for (int i = 0; i < appCountryList.size(); i++) {
					LOGGER.info(appCountryList.get(i));
				}
			}
			if (appCountryList != null) {
				bankApplicabilityCountryList = iCountryMasterservice.getbankApplicabilityCountryList(sessionmanage.getLanguageId(), appCountryList);
				if (bankApplicabilityCountryList != null) {
					for (CountryMasterDesc cm : bankApplicabilityCountryList) {
						LOGGER.info(cm.getCountryName());
					}
				}
			}
			return bankApplicabilityCountryList;
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return null;
		}
	}

	public void setBankApplicabilityCountryList(List<CountryMasterDesc> bankApplicabilityCountryList) {
		this.bankApplicabilityCountryList = bankApplicabilityCountryList;
	}
	
	public void clearExceptCountry()
	{
		setOwsScheduleId(null);
		setBankId(null);
		/*setBankcountryId(null);*/
		setLiveIndicator(null);
		setNeft(null);
		setRtgs(null);
		setXml(null);
		setOwsFlag(null);
		setRepeatInterval(null);
		setSneftStartTime(null);
		setSneftEndTime(null);
		setSrtgsStartTime(null);
		setSrtgsEndTime(null);
		setSweStartTime(null);
		setSweEndTime(null);
		/*setApplicationcountryId(null);*/
	}

	public void getBankBasedOnCountry() {
		try {
			clearExceptCountry();
			FbankList = bankApplicabilityService.getApplicabilityBankList(sessionmanage.getCountryId(), Constants.BANK_INDICATOR_CORR_BANK);
			LOGGER.info("*****************************");
			bankList.clear();
			for (BankApplicability bankApplicability : FbankList) {
				if (bankApplicability.getBankMaster().getFsCountryMaster().getCountryId().equals(getBankcountryId())) {
					bankList.add(bankApplicability);
				}
			}
			LOGGER.info("*****************************");
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return ;
		}
	}

	@SuppressWarnings("deprecation")
	public void save() {
		
		LOGGER.info("input  ----->" +toString());
		if (getSneftStartTime().compareTo(getSneftEndTime()) >= 0) {
			setErrorMessage("NEFT End Time Should not equal or less than NEFT Start Time");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
		if (getSrtgsStartTime().compareTo(getSrtgsEndTime()) >= 0) {
			setErrorMessage("RTGS End Time Should not equal or less than RTGS Start Time");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
		if (getSweStartTime().compareTo(getSweEndTime()) >= 0) {
			setErrorMessage("Weekend  End Time Should not equal or less than Weekend Start Time");
			RequestContext.getCurrentInstance().execute("csp.show();");
			return;
		}
		// validation over**********************************************//
		LOGGER.info("Country id " + getBankcountryId());
		LOGGER.info("Application Country id " + sessionmanage.getCountryId());
		LOGGER.info("Entering into save method");
		OwsSchedule saveOwsSchedule = new OwsSchedule();
		CountryMaster appC = new CountryMaster();
		appC.setCountryId(sessionmanage.getCountryId());
		saveOwsSchedule.setApplicationCountry(appC);
		CountryMaster bankC = new CountryMaster();
		bankC.setCountryId(getBankcountryId());
		saveOwsSchedule.setBankCountry(bankC);
		BankMaster bank = new BankMaster();
		bank.setBankId(getBankId());
		saveOwsSchedule.setBank(bank);
		saveOwsSchedule.setRepeatInterval(getRepeatInterval());
		saveOwsSchedule.setLiveIndicator(getLiveIndicator());
		saveOwsSchedule.setNeftIndicator(getNeft());
		saveOwsSchedule.setRtgsIndicator(getRtgs());
		saveOwsSchedule.setXmlCreation(getXml());
		saveOwsSchedule.setOwsReportFlag(getOwsFlag());
		LOGGER.info("neft start time hour" + getSneftStartTime().getHours());
		LOGGER.info("neft start time min" + getSneftStartTime().getMinutes());
		saveOwsSchedule.setNeftStartTime(dateToNmberConvert(getSneftStartTime()));
		LOGGER.info("neft end time hour" + getSneftEndTime().getHours());
		LOGGER.info("neft end time min" + getSneftEndTime().getMinutes());
		saveOwsSchedule.setNeftEndTime(dateToNmberConvert(getSneftEndTime()));
		LOGGER.info("rtgs start time hour" + getSrtgsStartTime().getHours());
		LOGGER.info("neft start time min" + getSrtgsStartTime().getMinutes());
		saveOwsSchedule.setRtgsStartTime(dateToNmberConvert(getSrtgsStartTime()));
		LOGGER.info("rtgs end time hour" + getSrtgsEndTime().getHours());
		LOGGER.info("rtgs end  time min" + getSrtgsEndTime().getMinutes());
		saveOwsSchedule.setRtgsEndTime(dateToNmberConvert(getSrtgsEndTime()));
		LOGGER.info("Weekend start time hour" + getSweStartTime().getHours());
		LOGGER.info("Weekend start time min" + getSweStartTime().getMinutes());
		saveOwsSchedule.setWeekendStartTime(dateToNmberConvert(getSweStartTime()));
		LOGGER.info("Weekend end time hour" + getSweEndTime().getHours());
		LOGGER.info("Weekend end time min" + getSweEndTime().getMinutes());
		saveOwsSchedule.setWeekendEndTime(dateToNmberConvert(getSweEndTime()));
		if (isPopulate()) {
			saveOwsSchedule.setCreatedBy(getCreatedBy());
			saveOwsSchedule.setCreatedDate(getCreatedDate());
			saveOwsSchedule.setModifiedBy(sessionmanage.getUserName());
			saveOwsSchedule.setModifiedDate(new Date());
		} else {
			saveOwsSchedule.setCreatedBy(sessionmanage.getUserName());
			saveOwsSchedule.setCreatedDate(new Date());
			saveOwsSchedule.setModifiedBy(null);
			saveOwsSchedule.setModifiedDate(null);
		}
		saveOwsSchedule.setApprovedBy(null);
		saveOwsSchedule.setApprovedDate(null);
		saveOwsSchedule.setIsActive(Constants.Yes);
		saveOwsSchedule.setOwsScheduleId(getOwsScheduleId());
		LOGGER.info("Exit into save method");
		try {
			iOwService.save(saveOwsSchedule);
			RequestContext.getCurrentInstance().execute("save.show();");
		} catch (Exception e) {
			setErrorMessage("Exception occured:" + e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}
	}

	@SuppressWarnings("deprecation")
	private BigDecimal dateToNmberConvert(Date sneftStartTime2) {
		LOGGER.info(sneftStartTime2.getHours());
		/* int hours = sneftStartTime2.getHours(); */
		String hoursString = new String("");
		String minsString = new String("");
		if (sneftStartTime2.getHours() <= 9) {
			hoursString = "0";
		}
		hoursString = hoursString + sneftStartTime2.getHours();
		if (sneftStartTime2.getMinutes() <= 9) {
			minsString = "0";
		}
		minsString = minsString + sneftStartTime2.getMinutes();
		LOGGER.info("hoursString " + hoursString);
		LOGGER.info("minsString " + minsString);
		String hm = hoursString + minsString;
		LOGGER.info("hours and mins " + hm);
		BigDecimal finalValue = new BigDecimal(hm);
		return finalValue;
	}

	public void neft() {
	}

	public void populateData() {
		try {
			LOGGER.info("Entering into populateData method");
			LOGGER.info(getBankcountryId());
			LOGGER.info(getBankId());
			LOGGER.info(sessionmanage.getCountryId());
			OwsSchedule dbOWSSchedule = iOwService.populateData(getBankcountryId(), getBankId(), sessionmanage.getCountryId());
			if (dbOWSSchedule != null) {
				setCreatedBy(dbOWSSchedule.getCreatedBy());
				setCreatedDate(dbOWSSchedule.getCreatedDate());
				setModifiedBy(sessionmanage.getUserName());
				setModifiedDate(new Date());
				setIsActive(dbOWSSchedule.getIsActive());
				setBankId(dbOWSSchedule.getBank().getBankId());
				setBankcountryId(dbOWSSchedule.getBankCountry().getCountryId());
				setApplicationcountryId(dbOWSSchedule.getApplicationCountry().getCountryId());
				setLiveIndicator(dbOWSSchedule.getLiveIndicator());
				setNeft(dbOWSSchedule.getNeftIndicator());
				setRtgs(dbOWSSchedule.getRtgsIndicator());
				setXml(dbOWSSchedule.getXmlCreation());
				setOwsFlag(dbOWSSchedule.getOwsReportFlag());
				setRepeatInterval(dbOWSSchedule.getRepeatInterval());
				BigDecimal neftStarttime = dbOWSSchedule.getNeftStartTime();
				LOGGER.info("neftStarttime" + neftStarttime);
				setSneftStartTime(numberToDate(dbOWSSchedule.getNeftStartTime()));
				setSneftEndTime(numberToDate(dbOWSSchedule.getNeftEndTime()));
				setSrtgsStartTime(numberToDate(dbOWSSchedule.getRtgsStartTime()));
				setSrtgsEndTime(numberToDate(dbOWSSchedule.getRtgsEndTime()));
				setSweStartTime(numberToDate(dbOWSSchedule.getWeekendStartTime()));
				setSweEndTime(numberToDate(dbOWSSchedule.getWeekendEndTime()));
				setOwsScheduleId(dbOWSSchedule.getOwsScheduleId());
				LOGGER.info(toString());
				setPopulate(true);
			} else {
				
				setOwsScheduleId(null);
				setLiveIndicator(null);
				setNeft(null);
				setRtgs(null);
				setXml(null);
				setOwsFlag(null);
				setRepeatInterval(null);
				setSneftStartTime(null);
				setSneftEndTime(null);
				setSrtgsStartTime(null);
				setSrtgsEndTime(null);
				setSweStartTime(null);
				setSweEndTime(null);
				/*setApplicationcountryId(null);*/
				
				
				LOGGER.info("No mathching record found");
			}
			LOGGER.info("Exit into populateData method");
		} catch (Exception e) {
			
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrorMessage("Exception ocurred" + e);
			return ;
		}
	}

	@SuppressWarnings("deprecation")
	private Date numberToDate(BigDecimal neftStarttime) {
		LOGGER.info(neftStarttime);
		Date returnDate = new Date();
		if (neftStarttime != null) {
			String inputString = neftStarttime.toString();
			if (inputString.length() == 1) {
				returnDate.setHours(0);
				returnDate.setMinutes(0);
			} else if (inputString.length() == 2) {
				returnDate.setHours(0);
				returnDate.setMinutes(Integer.parseInt(inputString));
			} else if (inputString.length() == 3) {
				LOGGER.info(inputString.substring(0, 1));
				LOGGER.info(inputString.substring(1, 3));
				returnDate.setHours(Integer.parseInt(inputString.substring(0, 1)));
				returnDate.setMinutes(Integer.parseInt(inputString.substring(1, 3)));
			} else if (inputString.length() == 4) {
				LOGGER.info(inputString.substring(0, 2));
				LOGGER.info(inputString.substring(2, 4));
				returnDate.setHours(Integer.parseInt(inputString.substring(0, 2)));
				returnDate.setMinutes(Integer.parseInt(inputString.substring(2, 4)));
			}
		}
		return returnDate;
	}

	public void clickOnExit() throws IOException {
		if (sessionmanage.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	
	@Override
	public String toString() {
		return "OwsScheduleBean [bankId=" + bankId + ", bankcountryId=" + bankcountryId + ", applicationcountryId=" + applicationcountryId + ", liveIndicator=" + liveIndicator + ", neft=" + neft + ", rtgs=" + rtgs + ", xml=" + xml + ", owsFlag=" + owsFlag + ", repeatInterval=" + repeatInterval
				+ ", errorMessage=" + errorMessage + ", sneftStartTime=" + sneftStartTime + ", sneftEndTime=" + sneftEndTime + ", srtgsStartTime=" + srtgsStartTime + ", srtgsEndTime=" + srtgsEndTime + ", sweStartTime=" + sweStartTime + ", sweEndTime=" + sweEndTime + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + ", modifiedBy=" + modifiedBy + ", modifiedDate=" + modifiedDate + ", approvedBy=" + approvedBy + ", approvedDate=" + approvedDate + ", isActive=" + isActive + "]";
	}
}
