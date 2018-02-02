package com.amg.exchange.cbk.bean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cbk.service.CBKReportsService;
import com.amg.exchange.util.SessionStateManage;

@Component("generateCBKReportsBean")
@Scope("session")
public class GenerateCBKReportsBean {

	private String reportFreqSelected;

	private Date weeklyFromDt;
	private Date weeklyToDt;

	private Date monthlyFromDt;
	private Date monthlyToDt;
	private Date entMonDt;

	private Date quarterlyFromDt;
	private Date quarterlyToDt;

	private String errorMessage;
	private Date quarterlySelectedDate;

	private SessionStateManage session = new SessionStateManage();

	@Autowired
	CBKReportsService cBKReportsService;

	public void pageNavigation() {
		try {
			setReportFreqSelected(null);
			FacesContext.getCurrentInstance().getExternalContext().redirect("../cbk/generateCBKReports.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Date getDisableWeek() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, -6);
		return c.getTime();
	}
	
	public Date getDisableMonth() {
		Date MonthDate = new Date();
		Calendar monthCal = Calendar.getInstance();
		monthCal.setTime(MonthDate);
		monthCal.add(Calendar.MONTH, -1);
		return monthCal.getTime();
	}
	
	public Date getDisableQuarter() {
		Date MonthDate = new Date();
		Calendar monthCal = Calendar.getInstance();
		monthCal.setTime(MonthDate);
		monthCal.add(Calendar.MONTH, -2);
		return monthCal.getTime();
	}
	
	public void defaultValues(){
		if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("W")) {
			Date cDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(cDate);
			c.add(Calendar.DATE, -6);
			Date sixthDate = c.getTime();
			setWeeklyFromDt(sixthDate);
			
			Date toDate = sixthDate;
			Calendar toCal = Calendar.getInstance();
			toCal.setTime(toDate);
			toCal.add(Calendar.DATE, 6);
			setWeeklyToDt(toCal.getTime());
			
		} else if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("M")) {
			Date MonthDate = new Date();
			Calendar monthCal = Calendar.getInstance();
			monthCal.setTime(MonthDate);
			monthCal.add(Calendar.MONTH, -1);
			Date monthDt = monthCal.getTime();
			
			setMonthlyFromDt(monthDt);
			setMonthlyToDt(monthDt);
			setEntMonDt(monthDt);
			
		} else if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("Q")) {
			Date quaDate = new Date();
			Calendar quaCal = Calendar.getInstance();
			quaCal.setTime(quaDate);
			quaCal.add(Calendar.MONTH, -2);
			Date quaMonthDt = quaCal.getTime();
			setQuarterlyFromDt(quaMonthDt);
			setQuarterlySelectedDate(quaMonthDt);
			
			Date toDate = quaMonthDt;
			Calendar toCal = Calendar.getInstance();
			toCal.setTime(toDate);
			toCal.add(Calendar.MONTH, 2);
			setQuarterlyToDt(toCal.getTime());
		}
	}

	public void dateSelected() {

		if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("W")) {

			Date cDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(cDate);
			c.add(Calendar.DATE, -6);

			Date sixthDate = c.getTime();
			Date selectDate = getWeeklyFromDt();

			if (sixthDate.compareTo(selectDate) < 0) {
				RequestContext.getCurrentInstance().execute("weekDtCompare.show();");
				defaultValues();
			} else {
				Date toDate = getWeeklyFromDt();
				Calendar toCal = Calendar.getInstance();
				toCal.setTime(toDate);
				toCal.add(Calendar.DATE, 6);
				setWeeklyToDt(toCal.getTime());
			}

		} else if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("M")) {
			Date MonthDate = new Date();
			Calendar monthCal = Calendar.getInstance();
			monthCal.setTime(MonthDate);
			monthCal.add(Calendar.MONTH, -1);

			Date monthDt = monthCal.getTime();
			Date selMonthDate = getMonthlyFromDt();

			if (monthDt.compareTo(selMonthDate) < 0) {
				RequestContext.getCurrentInstance().execute("monthDtCompare.show();");
				defaultValues();
			} else {
				Date conMonthDate = getMonthlyFromDt();
				SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");  
				String strDate= formatter.format(conMonthDate);
				Date date1;
				try {
					date1 = formatter.parse(strDate);
					setMonthlyFromDt(date1);
					setMonthlyToDt(getMonthlyFromDt());
					setEntMonDt(getMonthlyFromDt());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}

		} else if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("Q")) {
			Date quaDate = new Date();
			Calendar quaCal = Calendar.getInstance();
			quaCal.setTime(quaDate);
			quaCal.add(Calendar.MONTH, -2);

			Date quaMonthDt = quaCal.getTime();
			Date selQuaDate = getQuarterlyFromDt();

			if (quaMonthDt.compareTo(selQuaDate) < 0) {
				RequestContext.getCurrentInstance().execute("quaDtCompare.show();");
				defaultValues();
			} else {
				Date toDate = getQuarterlyFromDt();
				Calendar toCal = Calendar.getInstance();
				toCal.setTime(toDate);
				toCal.add(Calendar.MONTH, 2);
				setQuarterlySelectedDate(getQuarterlyFromDt());
				setQuarterlyToDt(toCal.getTime());
			}
		}
	}

	public void exit() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dateEntered() {
		
		if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("M")) {
			Date val = new Date("01/01/1900");
			if(getEntMonDt() == null || val.compareTo(getEntMonDt()) > 0 ){
				RequestContext.getCurrentInstance().execute("validDate.show();");
				defaultValues();
			}else{
				setMonthlyFromDt(null);
				Date MonthDate = new Date();
				Calendar monthCal = Calendar.getInstance();
				monthCal.setTime(MonthDate);
				monthCal.add(Calendar.MONTH, -1);

				Date monthDt = monthCal.getTime();
				Date selMonthDate = getEntMonDt();

				if (monthDt.compareTo(selMonthDate) < 0) {
					RequestContext.getCurrentInstance().execute("monthDtCompare.show();");
					defaultValues();
				} else {
					Date conMonthDate = getEntMonDt();
					SimpleDateFormat formatter = new SimpleDateFormat("MM/yyyy");
					String strDate = formatter.format(conMonthDate);
					Date date1;
					try {
						date1 = formatter.parse(strDate);
						setMonthlyFromDt(date1);
						setMonthlyToDt(getMonthlyFromDt());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		} else if (reportFreqSelected != null && reportFreqSelected.equalsIgnoreCase("Q")) {
			Date val = new Date("01/01/1900");
			if(getQuarterlySelectedDate() == null || val.compareTo(getQuarterlySelectedDate()) > 0){
				RequestContext.getCurrentInstance().execute("validDate.show();");
				defaultValues();
			} else {
				setQuarterlyFromDt(null);
				Date quaDate = new Date();
				Calendar quaCal = Calendar.getInstance();
				quaCal.setTime(quaDate);
				quaCal.add(Calendar.MONTH, -2);

				Date quaMonthDt = quaCal.getTime();
				Date selQuaDate = getQuarterlySelectedDate();

				if (quaMonthDt.compareTo(selQuaDate) < 0) {
					RequestContext.getCurrentInstance().execute("quaDtCompare.show();");
					defaultValues();
				} else {
					Date toDate = getQuarterlySelectedDate();
					Calendar toCal = Calendar.getInstance();
					toCal.setTime(toDate);
					toCal.add(Calendar.MONTH, 2);
					setQuarterlyFromDt(getQuarterlySelectedDate());
					setQuarterlyToDt(toCal.getTime());
				}
			}			
		}
	}
	

	public void submitSelected() {

		try {

			Date weekFrom = new Date();
			Date weekTo = new Date();
			Date monthFrom = new Date();
			Date monthTo = new Date();
			Date quaFrom = new Date();
			Date quaTo = new Date();

			if (getReportFreqSelected() == null || getReportFreqSelected().equalsIgnoreCase("")) {
				RequestContext.getCurrentInstance().execute("reportFreq.show();");

			} else if (getReportFreqSelected() != null) {
				if (reportFreqSelected.equalsIgnoreCase("W")) {
					if (getWeeklyFromDt() == null) {
						RequestContext.getCurrentInstance().execute("weeklyFrom.show();");
					} else {
						cBKReportsService.getCBKReportProcedure(getReportFreqSelected(), getWeeklyFromDt(), getWeeklyToDt(), monthFrom, monthTo, quaFrom, quaTo, session.getUserName());
						RequestContext.getCurrentInstance().execute("informationSaved.show();");
					}

				} else if (reportFreqSelected.equalsIgnoreCase("M")) {
					if (getMonthlyFromDt() == null) {
						RequestContext.getCurrentInstance().execute("monthlyFrom.show();");
					} else {
						cBKReportsService.getCBKReportProcedure(getReportFreqSelected(), weekFrom, weekTo, getMonthlyFromDt(), getMonthlyToDt(), quaFrom, quaTo, session.getUserName());
						RequestContext.getCurrentInstance().execute("informationSaved.show();");
					}
				} else if (reportFreqSelected.equalsIgnoreCase("Q")) {
					if (getQuarterlyFromDt() == null) {
						RequestContext.getCurrentInstance().execute("quarterlyFrom.show();");
					} else {
						cBKReportsService.getCBKReportProcedure(getReportFreqSelected(), weekFrom, weekTo, monthFrom, monthTo, getQuarterlyFromDt(), getQuarterlyToDt(), session.getUserName());
						RequestContext.getCurrentInstance().execute("informationSaved.show();");
					}
				}

			}

		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
		}
	}

	// Getters and setters

	public String getReportFreqSelected() {
		return reportFreqSelected;
	}

	public void setReportFreqSelected(String reportFreqSelected) {
		this.reportFreqSelected = reportFreqSelected;
	}

	public Date getWeeklyFromDt() {
		return weeklyFromDt;
	}

	public void setWeeklyFromDt(Date weeklyFromDt) {
		this.weeklyFromDt = weeklyFromDt;
	}

	public Date getWeeklyToDt() {
		return weeklyToDt;
	}

	public void setWeeklyToDt(Date weeklyToDt) {
		this.weeklyToDt = weeklyToDt;
	}

	public Date getMonthlyFromDt() {
		return monthlyFromDt;
	}

	public void setMonthlyFromDt(Date monthlyFromDt) {
		this.monthlyFromDt = monthlyFromDt;
	}

	public Date getMonthlyToDt() {
		return monthlyToDt;
	}

	public void setMonthlyToDt(Date monthlyToDt) {
		this.monthlyToDt = monthlyToDt;
	}

	public Date getQuarterlyFromDt() {
		return quarterlyFromDt;
	}

	public void setQuarterlyFromDt(Date quarterlyFromDt) {
		this.quarterlyFromDt = quarterlyFromDt;
	}

	public Date getQuarterlyToDt() {
		return quarterlyToDt;
	}

	public void setQuarterlyToDt(Date quarterlyToDt) {
		this.quarterlyToDt = quarterlyToDt;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public CBKReportsService getcBKReportsService() {
		return cBKReportsService;
	}

	public void setcBKReportsService(CBKReportsService cBKReportsService) {
		this.cBKReportsService = cBKReportsService;
	}

	public Date getQuarterlySelectedDate() {
		return quarterlySelectedDate;
	}

	public void setQuarterlySelectedDate(Date quarterlySelectedDate) {
		this.quarterlySelectedDate = quarterlySelectedDate;
	}

	public Date getEntMonDt() {
		return entMonDt;
	}

	public void setEntMonDt(Date entMonDt) {
		this.entMonDt = entMonDt;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

}
