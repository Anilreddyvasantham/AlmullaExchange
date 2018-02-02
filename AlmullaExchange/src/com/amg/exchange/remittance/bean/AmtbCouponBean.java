package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;
import org.hibernate.mapping.Array;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.remittance.model.ViewAmtbCouponReport;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.GetRound;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("amtbCouponBean")
@Scope("session")
public class AmtbCouponBean<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	SessionStateManage sessionStateManage = new SessionStateManage();
	Logger log = Logger.getLogger(AmtbCouponBean.class);
	private SessionStateManage session = new SessionStateManage();
	
	
	private Date maxDate = new Date();
	private Date toDate;
	private Date fromDate = new Date();
	private boolean booRenderLocation = false;
	private String errorMessage;
	
	private List<CountryBranch> branchList;
	private List<ViewAmtbCouponReport> viewAmtbCouponReportList;
	
	private List<AmtbCouponDT>  amtbCouponDTBeanList;
	private JasperPrint jasperPrint;
	
	private BigDecimal branchId;
	
	
	private String location;
	private String userName;
	private String strfromDate;
	private String strToDate;
	
	
	
	// Autowired objects
		@Autowired
		IGeneralService<T> generalService;
		@Autowired
		LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
		@Autowired
		IPersonalRemittanceService iPersonalRemittanceService;
		
		
		public void navigateAmtbCouponReportPage(){
			populateBranch();
			setFromDate(new Date());
			setToDate(new Date());
			setBranchId(new BigDecimal(session.getBranchId()));
			// branch manager restricted
			String roleNameDesc=iPersonalRemittanceService.toFetchRoleName(new BigDecimal(session.getRoleId()));
			String roleName = roleNameDesc.trim();
			if (roleName.equalsIgnoreCase(Constants.USER_ROLE_BRANCH_MANAGER)) {
				setBooRenderLocation(true);
			} else {
				setBooRenderLocation(false);
			}
			
			try {
				loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "amtbReportEnquiry.xhtml");
				FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/amtbReportEnquiry.xhtml");
			}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
			}
		}

		
		public void populateBranch() {
			setBranchList(null);
			setBranchId(null);
			List<CountryBranch> listSearchBranch  = generalService.getBranchDetails(session.getCountryId());
			if(listSearchBranch.size()>0){
				setBranchList(listSearchBranch);
			}
		}
		

		
		public void exitFromReportScreen(){
			try {
				if (session.getRoleId().equalsIgnoreCase("1")) {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
				} else {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
				}
			}catch(Exception exception){
				log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
				setErrorMessage(exception.getMessage()); 
				RequestContext.getCurrentInstance().execute("error.show();");
				return;       
			}
		}
		
		public void searchAmtbCouponReport(){
			viewAmtbCouponReportList = new ArrayList<ViewAmtbCouponReport>();
			AmtbCouponDT amtbDt = new AmtbCouponDT();
			amtbDt.setCountryBranchId(new BigDecimal(session.getBranchId()));
			amtbDt.setFromDate(getFromDate());
			amtbDt.setToDate(getToDate());
			viewAmtbCouponReportList = generalService.getListFromAmtbCouponReport(amtbDt);
			
		}
		
		
		public void generateReport(){
			
			
			ServletOutputStream servletOutputStream=null;
			try {
				amtbCouponDTBeanList =new ArrayList<AmtbCouponDT>();
				fetchAmtbCouponReport();
				amtbReportInit();


				HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
				httpServletResponse.setHeader("Cache-Control","cache, must-revalidate");
				httpServletResponse.addHeader("Content-disposition","attachment; filename=AMX_AMTB_Coupon_Report.pdf");
				servletOutputStream=httpServletResponse.getOutputStream();  
				JasperExportManager.exportReportToPdfStream(jasperPrint,servletOutputStream);
				FacesContext.getCurrentInstance().responseComplete();

			} catch (Exception e) {
				
				if(e.getMessage()!=null){
					setErrorMessage(e.getMessage());	
				}else{
					setErrorMessage("Exception  :"+e);
				}
				RequestContext.getCurrentInstance().execute("error.show();");
			} 
			
		}
		
		
		public void amtbReportInit() throws JRException {

			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(amtbCouponDTBeanList);
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			//String reportPath = realPath +"\\reports\\design\\RemittanceReceiptNewReport.jasper"; //for Window
			String reportPath = realPath +"//reports//design//amx_amtb_coupon_report.jasper";
			//String reportPath = realPath +"//reports//design//amtb_amx_main_rpt.jasper";
			System.out.println(reportPath);		
			jasperPrint = JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);
		}
		
		public void fetchAmtbCouponReport(){
			try{
				
				int i=0;
			ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath = ctx.getRealPath("//");
			String subReportPath = realPath + Constants.SUB_REPORT_PATH;
			String waterMark = realPath+Constants.REPORT_WATERMARK_LOGO;

			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String currentDate = dateFormat.format(new Date());
			
			AmtbCouponDT amtbCouponDTReport = new AmtbCouponDT();
			amtbCouponDTReport.setLocation(sessionStateManage.getLocation());
			amtbCouponDTReport.setUserName(sessionStateManage.getUserName());
			amtbCouponDTReport.setStrfromDate(dateFormat.format(getFromDate()));
			amtbCouponDTReport.setStrToDate(dateFormat.format(getToDate()));
		//	amtbCouponDTReport.setSubReport(subReportPath);
			
			
			List<AmtbCouponDT> amtbCouponDTReportList= new ArrayList<AmtbCouponDT>();
			for(ViewAmtbCouponReport viewAmtbCouponReport :viewAmtbCouponReportList){
				 i++;
				 AmtbCouponDT amtbReportBean = new AmtbCouponDT();
				 amtbReportBean.setSrNo(i+"");
				 amtbReportBean.setCouponNoStr(viewAmtbCouponReport.getCouponNo().toString());
				 amtbReportBean.setCustomerName(viewAmtbCouponReport.getCustomerName());
				 amtbReportBean.setIdNo(viewAmtbCouponReport.getIdNo());
				 amtbReportBean.setRemitDocNo(viewAmtbCouponReport.getRemitDocNo());
				
				 amtbCouponDTReportList.add(amtbReportBean);
				
			}
			
			amtbCouponDTReport.setAmtbCouponDTList(amtbCouponDTReportList);
			
			
			//amtbCouponDTReport.setAmtbCouponDTList(amtbCouponDTReportList);
			amtbCouponDTBeanList.add(amtbCouponDTReport);
	
			
			}catch(Exception e){
				System.out.println("MEssage :"+e.getMessage());
			}
			
		}
		
		public void clear(){
			setToDate(new Date());
			setFromDate(new Date());
			setBranchId(null);
			viewAmtbCouponReportList.clear();
		}
		public Date getMaxDate() {
			return maxDate;
		}


		public void setMaxDate(Date maxDate) {
			this.maxDate = maxDate;
		}


		public Date getToDate() {
			return toDate;
		}


		public void setToDate(Date toDate) {
			this.toDate = toDate;
		}


		public Date getFromDate() {
			return fromDate;
		}


		public void setFromDate(Date fromDate) {
			this.fromDate = fromDate;
		}


		public boolean isBooRenderLocation() {
			return booRenderLocation;
		}


		public void setBooRenderLocation(boolean booRenderLocation) {
			this.booRenderLocation = booRenderLocation;
		}


		public List<CountryBranch> getBranchList() {
			return branchList;
		}


		public void setBranchList(List<CountryBranch> branchList) {
			this.branchList = branchList;
		}


		public BigDecimal getBranchId() {
			return branchId;
		}


		public void setBranchId(BigDecimal branchId) {
			this.branchId = branchId;
		}


		public IPersonalRemittanceService getiPersonalRemittanceService() {
			return iPersonalRemittanceService;
		}


		public void setiPersonalRemittanceService(IPersonalRemittanceService iPersonalRemittanceService) {
			this.iPersonalRemittanceService = iPersonalRemittanceService;
		}


		public String getErrorMessage() {
			return errorMessage;
		}


		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}


		public List<ViewAmtbCouponReport> getViewAmtbCouponReportList() {
			return viewAmtbCouponReportList;
		}


		public void setViewAmtbCouponReportList(List<ViewAmtbCouponReport> viewAmtbCouponReportList) {
			this.viewAmtbCouponReportList = viewAmtbCouponReportList;
		}


		public List<AmtbCouponDT> getAmtbCouponDTBeanList() {
			return amtbCouponDTBeanList;
		}


		public void setAmtbCouponDTBeanList(List<AmtbCouponDT> amtbCouponDTBeanList) {
			this.amtbCouponDTBeanList = amtbCouponDTBeanList;
		}


		public String getLocation() {
			return location;
		}


		public void setLocation(String location) {
			this.location = location;
		}


		public String getUserName() {
			return userName;
		}


		public void setUserName(String userName) {
			this.userName = userName;
		}


		public String getStrfromDate() {
			return strfromDate;
		}


		public void setStrfromDate(String strfromDate) {
			this.strfromDate = strfromDate;
		}


		public String getStrToDate() {
			return strToDate;
		}


		public void setStrToDate(String strToDate) {
			this.strToDate = strToDate;
		}

}
