//package com.amg.exchange.treasury.bean;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//
//import javax.faces.context.FacesContext;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Scope;
//import org.springframework.stereotype.Component;
//
//import com.amg.exchange.common.service.IGeneralService;
//import com.amg.exchange.treasury.model.FundEstimation;
//import com.amg.exchange.treasury.model.FundEstimationDetails;
//import com.amg.exchange.treasury.service.IFundEstimationService;
//import com.amg.exchange.treasury.service.ITotalUsdRequirementService;
//import com.amg.exchange.treasury.viewModel.TotalUsdRequirementView;
//import com.amg.exchange.util.Constants;
//import com.amg.exchange.util.SessionStateManage;
//
//
//@Component("totalUsdRequirementDetails")
//@Scope("session")
//public class TotalUsdRequirementDetailsBean2<T> implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	
//	SessionStateManage sessionStateManage = new SessionStateManage();
//	
//	private double currentUsdBalance;
//	private double usdSalesProjection;
//	private double fctoUsdRequirment;
//	private double totalUsdRequirment;
//	private BigDecimal countryId;
//	private Boolean booCountry;
//	
//	private String projectionDate = null;
//
//	@Autowired
//	ITotalUsdRequirementService<T> totalUsdRequirementService;
//	
//	@Autowired
//	IFundEstimationService<T> fundEstimationService;
//	
//	@Autowired
//	IGeneralService<T> generalService;
//	
//	List<FundEstimationDetails> fundEstimationDetails=new ArrayList<FundEstimationDetails>();
//	List<FundEstimation> fundEstimationusd  =new ArrayList<FundEstimation>();
//	
//	List<TotalUsdRequirementDetails> totalUsdReqDetails=new ArrayList<TotalUsdRequirementDetails>();
//		
//	//Added for 
//	List<TotalUsdRequirementView> totalUsdRequirementDetailsAll=new ArrayList<TotalUsdRequirementView>();
//	
//	public List<TotalUsdRequirementDetails> getTotalUsdReqDetails() {
//		return totalUsdReqDetails;
//	}
//	public void setTotalUsdReqDetails(
//			List<TotalUsdRequirementDetails> totalUsdReqDetails) {
//		this.totalUsdReqDetails = totalUsdReqDetails;
//	}
//	
//	
//	
//	public List<FundEstimationDetails> getFundEstimationDetails() {
//		return fundEstimationDetails;
//	}
//	
//	
//	public void setFundEstimationDetails(
//			List<FundEstimationDetails> fundEstimationDetails) {
//		this.fundEstimationDetails = fundEstimationDetails;
//	}
//	public double getCurrentUsdBalance() {
//		return currentUsdBalance;
//	}
//	public void setCurrentUsdBalance(double currentUsdBalance) {
//		this.currentUsdBalance = currentUsdBalance;
//	}
//	public double getUsdSalesProjection() {
//		return usdSalesProjection;
//	}
//	public void setUsdSalesProjection(double usdSalesProjection) {
//		this.usdSalesProjection = usdSalesProjection;
//	}
//	public double getFctoUsdRequirment() {
//		return fctoUsdRequirment;
//	}
//	public void setFctoUsdRequirment(double fctoUsdRequirment) {
//		this.fctoUsdRequirment = fctoUsdRequirment;
//	}
//	public double getTotalUsdRequirment() {
//		return totalUsdRequirment;
//	}
//	public void setTotalUsdRequirment(double totalUsdRequirment) {
//		this.totalUsdRequirment = totalUsdRequirment;
//	}
//	public BigDecimal getCountryId() {
//		return countryId;
//	}
//	
//	public void setCountryId(BigDecimal countryId) {
//		this.countryId = countryId;
//	}
//	public Boolean getBooCountry() {
//		return booCountry;
//	}
//	public void setBooCountry(Boolean booCountry) {
//		this.booCountry = booCountry;
//	}
//	
//	public String getProjectionDate() {
//		
//		 String projectionDate = null;
//	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//	        Calendar calendar = Calendar.getInstance();
//	        Date date = calendar.getTime();
//	        projectionDate =sdf.format(date);
//	        calendar.setTime(new Date());
//
//		return projectionDate;
//	}
//	public void setProjectionDate(String projectionDate) {
//		this.projectionDate = projectionDate;
//	}
//	
//	public void fetchTotalUsd() {
//		
//	}
//	/**
//	 * For Exit Button
//	 * @throws IOException 
//	 */
//	public void exit() throws IOException {
//		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
//			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
//		}else{
//			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
//		}
//	}
//	
//	public void displayUsdData(BigDecimal countryId,String roleId,String projectionDate) throws ParseException{
//		
//		BigDecimal sumCurrentUsdBalance = BigDecimal.ZERO;
//		BigDecimal sumUsdSalesProjection = BigDecimal.ZERO;
//		BigDecimal sumFcTousdRequirement = BigDecimal.ZERO;
//		BigDecimal sumValueDatedTranx = BigDecimal.ZERO;
//		
//		fundEstimationDetails=totalUsdRequirementService.getSaleProjectionValues(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
//		fundEstimationusd = totalUsdRequirementService.getTotalUsd(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
//		System.out.println("**********inside displayUsdData  ***************");
//		if(sessionStateManage.getRoleId().equalsIgnoreCase("1")) {
//			
//			List<BigDecimal> countryData = new ArrayList<BigDecimal>(); 	
//			
//			for (FundEstimation fundEstimation2 : fundEstimationusd) {
//				if(countryData.contains(fundEstimation2.getApplicationCountryId()) {
//					TotalUsdRequirementDetails tUsdReq = new TotalUsdRequirementDetails();
//					for (TotalUsdRequirementDetails totalUsdRequirementDetails : totalUsdReqDetails) {
//						if(totalUsdRequirementDetails.getAppCountry().equalsIgnoreCase(fundEstimation2.getApplicationCountryId().toPlainString())){
//							for (FundEstimationDetails fundEstimation5 : fundEstimationDetails) {
//								sumFcTousdRequirement = sumFcTousdRequirement.add(fundEstimation5.getUsdValue());
//							}
//							tUsdReq.setCalFcTousdRequirement(sumFcTousdRequirement);
//							tUsdReq.setCalUsdSaleProjection(BigDecimal.ZERO);
//							setCountryId(fundEstimation2.getApplicationCountryId());
//							tUsdReq.setAppCountry(fundEstimationService.getCountryName(getCountryId()));
//							//fundEstimationusd = totalUsdRequirementService.getTotalUsd(fundEstimationDetails.get(0).getFundEstimtaionId().getApplicationCountryId().getCountryId(),sessionStateManage.getRoleId());
//							sumCurrentUsdBalance = sumCurrentUsdBalance.add(fundEstimation2.getUsdCurrentBalanace());
//							tUsdReq.setCalTotalUsdrequirement(sumCurrentUsdBalance.subtract(sumFcTousdRequirement)  );
//							tUsdReq.setUsdCurrentBalanace(sumCurrentUsdBalance);
//							totalUsdReqDetails.add(tUsdReq);
//						}
//					}	 
//				} else {
//					
//					countryData.add(fundEstimation2.getApplicationCountryId());
//					TotalUsdRequirementDetails tUsdReq = new TotalUsdRequirementDetails();
//					tUsdReq.setCalFcTousdRequirement(fundEstimationDetails.get(0).getUsdValue());
//					tUsdReq.setCalUsdSaleProjection(BigDecimal.ZERO);
//					setCountryId(fundEstimation2.getApplicationCountryId());
//					tUsdReq.setAppCountry(fundEstimationService.getCountryName(getCountryId()));
//					//fundEstimationusd = totalUsdRequirementService.getTotalUsd(fundEstimationDetails.get(0).getFundEstimtaionId().getExFundEstimationForApplicationCountry().getCountryId(),sessionStateManage.getRoleId());
//					//sumCurrentUsdBalance = sumCurrentUsdBalance+ fundusdsum.getUsdCurrentBalanace().doubleValue();
//					tUsdReq.setCalTotalUsdrequirement(fundEstimation2.getUsdCurrentBalanace().subtract(fundEstimationDetails.get(0).getUsdValue()));
//					tUsdReq.setUsdCurrentBalanace(fundEstimation2.getUsdCurrentBalanace());
//					totalUsdReqDetails.add(tUsdReq);
//				}
//			}
//		} else{
//			
//			TotalUsdRequirementDetails tUsdReq = new TotalUsdRequirementDetails();
//			
//			for (FundEstimationDetails fundEstimation1 : fundEstimationDetails) {
//				sumFcTousdRequirement = sumFcTousdRequirement.add(fundEstimation1.getUsdValue());
//			}
//			tUsdReq.setCalFcTousdRequirement(new BigDecimal(round(sumFcTousdRequirement.doubleValue(),3)));
//			tUsdReq.setCalUsdSaleProjection(BigDecimal.ZERO);
//			if(fundEstimationDetails.size()>0) {
//				setCountryId(fundEstimationDetails.get(0).getFundEstimtaionId().getExFundEstimationForApplicationCountry().getCountryId());
//				tUsdReq.setAppCountry(fundEstimationService.getCountryName(getCountryId()));
//				}
//			fundEstimationusd = totalUsdRequirementService.getTotalUsd(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
//			
//			for (FundEstimation fundusdsum : fundEstimationusd) {
//				sumCurrentUsdBalance = sumCurrentUsdBalance.add(fundusdsum.getUsdCurrentBalanace());
//				sumValueDatedTranx  = sumValueDatedTranx.add(fundusdsum.getuSdValueDatedTransaction());
//			}
//			BigDecimal total = sumFcTousdRequirement.add(sumValueDatedTranx);
//			
//			tUsdReq.setCalTotalUsdrequirement(new BigDecimal(round(total.subtract(sumCurrentUsdBalance).doubleValue(), 3)));
//			tUsdReq.setUsdCurrentBalanace(new BigDecimal((round(sumCurrentUsdBalance.doubleValue(),3))));
//			if(fundEstimationDetails.size()>0) {
//				totalUsdReqDetails.add(tUsdReq);
//			}
//		}
//	}
//	
//	
//	/*
//	 * This Method is added for Treasury – Sales Projections CR Point -9 begin
//	 */
//		
//	public void displayUsdDataChiefUser() throws ParseException{
//		
//
//		totalUsdRequirementDetailsAll=null;
//		fundEstimationusd=null;
//		totalUsdRequirementDetailsAll= totalUsdRequirementService.getSaleProjectionValuesChief();
//		TotalUsdRequirementDetails tUsdReq =new TotalUsdRequirementDetails();
//		BigDecimal countryId=new BigDecimal(0);
//		
//		if(totalUsdRequirementDetailsAll.size()>0){
//			for(int i=0;i<totalUsdRequirementDetailsAll.size();i++){
//				 tUsdReq =new TotalUsdRequirementDetails();
//				 countryId=totalUsdRequirementDetailsAll.get(i).getApplicationCountryId();
//				 BigDecimal kuwaitCountryId = generalService.getCountryIdBasedOnCountryAlpha2Code(Constants.KUWAIT_ALPHA_TWO_CODE);
//				 BigDecimal omanCountryId = generalService.getCountryIdBasedOnCountryAlpha2Code(Constants.OMAN_ALPHA_TWO_CODE);
//				 BigDecimal bahrainCountryId = generalService.getCountryIdBasedOnCountryAlpha2Code(Constants.BAHRAIN_ALPHA_TWO_CODE);
//				 if(kuwaitCountryId!=null || omanCountryId!=null || bahrainCountryId!=null ||countryId!=null){
//				 if(countryId.equals(kuwaitCountryId)){
//					 tUsdReq.setAppCountry("Kuwait");
//				 } if(countryId.equals(omanCountryId)){
//					 tUsdReq.setAppCountry("Oman");
//				 } if(countryId.equals(bahrainCountryId)){
//					 tUsdReq.setAppCountry("Bahrain");
//		     	 }				
//			}
//			
//			tUsdReq.setUsdCurrentBalanace(totalUsdRequirementDetailsAll.get(i).getCurrentBalance());
//			tUsdReq.setCalUsdSaleProjection(new BigDecimal( totalUsdRequirementDetailsAll.get(i).getSale_Projection()));  
//			tUsdReq.setCalFcTousdRequirement(new BigDecimal( totalUsdRequirementDetailsAll.get(i).getCurrencyRequirement()));
//			tUsdReq.setCalTotalUsdrequirement(new BigDecimal( totalUsdRequirementDetailsAll.get(i).getTotal()));
//			totalUsdReqDetails.add(tUsdReq);
//        	 /*
//			if(totalUsdRequirementDetailsAll.size()>0) {
//					totalUsdReqDetails.add(tUsdReq);
//					System.out.println("Size of totalUsdReqDetails************  "+totalUsdReqDetails.size());
//					}*/
//			
//				}
//			}
//		}
//
//			
//	/*
//	 * Above  mathod is added for Treasury – Sales Projections CR Point -9 End
//	 */
//	
//	
//	public String  callTotalUsdRequirement() throws IOException, ParseException{
//		totalUsdReqDetails.clear();
//		//displayUsdData(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),getProjectionDate());
//		displayUsdDataChiefUser();
//		return "totalUsdRequirement";
//	}
//	
//	public double round(double value, int places) {
//		if (places < 0)
//			throw new IllegalArgumentException();
//
//		long factor = (long) Math.pow(10, places);
//		value = value * factor;
//		long tmp = Math.round(value);
//		return (double) tmp / factor;
//	}
//}
