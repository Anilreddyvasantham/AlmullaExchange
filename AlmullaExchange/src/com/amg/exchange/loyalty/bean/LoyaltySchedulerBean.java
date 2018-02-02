package com.amg.exchange.loyalty.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.loyalty.model.LoyaltyScheduler;
import com.amg.exchange.loyalty.service.ILoyaltySchedulerService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("loyaltySchedulerBean")
@Scope("session")
public class LoyaltySchedulerBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(LoyaltySchedulerBean.class);
	private SessionStateManage session = new SessionStateManage();
	private String dayFlag;
	private  Boolean schedularFlag;
	private BigDecimal schedulerPk;
	private String enableFlag;
	private Date timeToStart;
	private String errorMsg;

	@Autowired
	ILoyaltySchedulerService iLoyaltyService;

	public String getDayFlag() {
		return dayFlag;
	}

	public void setDayFlag(String dayFlag) {
		this.dayFlag = dayFlag;
	}

	public Boolean getSchedularFlag() {
		return schedularFlag;
	}

	public void setSchedularFlag(Boolean schedularFlag) {
		this.schedularFlag = schedularFlag;
	}

	public BigDecimal getSchedulerPk() {
		return schedulerPk;
	}

	public void setSchedulerPk(BigDecimal schedulerPk) {
		this.schedulerPk = schedulerPk;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}

	public Date getTimeToStart() {
		return timeToStart;
	}

	public void setTimeToStart(Date timeToStart) {
		this.timeToStart = timeToStart;
	}

	// when click Save button
	public void saveLoyaltyScheduler() throws DatatypeConfigurationException {
	try{
		if (getTimeToStart() != null) {
			List<LoyaltyScheduler> loyaltySchedulerList = iLoyaltyService
					.dulaicateCheck();
			LoyaltyScheduler loyaltyScheduler = new LoyaltyScheduler();
			if (loyaltySchedulerList.size() > 0) {
				loyaltyScheduler.setLoyaltySchedulerId(loyaltySchedulerList
						.get(0).getLoyaltySchedulerId());
				loyaltyScheduler.setModifiedBy(session.getUserName());
				loyaltyScheduler.setModifiedDate(new Date());
				loyaltyScheduler.setCreatedDate(loyaltySchedulerList.get(0)
						.getCreatedDate());
				loyaltyScheduler.setCreatedBy(loyaltySchedulerList.get(0)
						.getCreatedBy());

			} else {
				loyaltyScheduler.setCreatedDate(new Date());
				loyaltyScheduler.setCreatedBy(session.getUserName());

			}

			GregorianCalendar processtime = new GregorianCalendar();
			processtime.setTime(getTimeToStart());
			XMLGregorianCalendar xmlFin = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(processtime);

			String hours = xmlFin.getHour() + "";
			String minutes = xmlFin.getMinute() + "";
			String starttimeHours = "";
			String starttimeMinutes = "";

			if (hours.length() == 1) {
				starttimeHours = "0" + "" + hours;
			} else {
				starttimeHours = hours;
			}
			if (minutes.length() == 1) {
				starttimeMinutes = "0" + minutes;
			} else {
				starttimeMinutes = minutes;
			}
			if (starttimeHours.length() == 2 && starttimeHours.length() == 2) {
				loyaltyScheduler.setTimeToStart(starttimeHours + ":"
						+ starttimeMinutes);
			}
			//Boolean sflag = Boolean.valueOf(getSchedularFlag());
			if (getSchedularFlag()) {
				loyaltyScheduler.setSchedularFlag(Constants.Yes);
			} else {
				loyaltyScheduler.setSchedularFlag(Constants.No);
			}

			loyaltyScheduler.setEnableFlag(getEnableFlag());
			loyaltyScheduler.setDayFlag(getDayFlag());
			loyaltyScheduler.setIsActive(Constants.Yes);

			iLoyaltyService.saveOrUpdate(loyaltyScheduler);
			RequestContext.getCurrentInstance().execute("complete.show();");
			// clearAll();
		} else {
			RequestContext.getCurrentInstance().execute(
					"pleaseentertime.show();");
		}
	} catch(NullPointerException  e){ 
		 
			setErrorMsg("saveLoyaltyScheduler :");
			RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
		}
	catch (Exception e) {
			setErrorMsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");
		}

	}

	public void clearAll() {
		setDayFlag(null);
		setEnableFlag(null);
		setSchedularFlag(null);
		setTimeToStart(null);
		setSchedulerPk(null);

	}

	public void exitButton() {
		try {
			if (session.getRoleId().equalsIgnoreCase("1")) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/employeehome.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("../registration/branchhome.xhtml");
			}
		} catch (Exception e) {
			log.error("======Problem Ocuured in Exit Button=====");
		}

	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigationToLoyaltyScheduler() {
		setDayFlag(null);
		setTimeToStart(null);
		setSchedulerPk(null);
		setSchedularFlag(null);
try{
		List<LoyaltyScheduler> loyaltyServiceList = iLoyaltyService.dulaicateCheck();
		if (loyaltyServiceList.size() > 0) {

			setEnableFlag(loyaltyServiceList.get(0).getEnableFlag());
			if (loyaltyServiceList.get(0).getSchedularFlag()
					.equalsIgnoreCase(Constants.Yes)) {
				setSchedularFlag(true);
			} else {
				setSchedularFlag(false);
			}
			setDayFlag(loyaltyServiceList.get(0).getDayFlag());

			String processTime = loyaltyServiceList.get(0).getTimeToStart();
			DateFormat formatter = new SimpleDateFormat("HH:mm");

			Date fromDate;
			try {
				fromDate = (Date) formatter.parse(processTime);
				setTimeToStart(fromDate);
			} catch (ParseException e) {

				log.error("=====Problem Occured Conversion fro String to date=====");
			}

		}

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "loyaltyscheduler.xhtml");
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("../loyalty/loyaltyscheduler.xhtml");
		} catch (Exception e) {
			log.error("========Error in Page Navigation to  Loyalty Schedular=========");
		}
		
		
		
		
		
		
}catch(NullPointerException  e){ 
	 
		setErrorMsg("pageNavigationToLoyaltyScheduler :");
		RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
	}
catch (Exception e) {
		setErrorMsg(e.getMessage());
		RequestContext.getCurrentInstance().execute("csp.show();");
	}
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

}
