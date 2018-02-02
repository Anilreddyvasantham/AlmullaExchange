package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ITotalUsdRequirementService;
import com.amg.exchange.util.SessionStateManage;


@Component("totalUsdRequirementDetailsOld")
@Scope("session")
public class TotalUsdRequirementDetailsBean<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	private double currentUsdBalance;
	private double usdSalesProjection;
	private double fctoUsdRequirment;
	private double totalUsdRequirment;
	private BigDecimal countryId;
	private Boolean booCountry;
	
	private String projectionDate = null;

	@Autowired
	ITotalUsdRequirementService<T> totalUsdRequirementService;
	
	@Autowired
	IFundEstimationService<T> fundEstimationService;
	
	List<FundEstimationDetails> fundEstimationDetails=new ArrayList<FundEstimationDetails>();
	List<FundEstimation> fundEstimationusd  =new ArrayList<FundEstimation>();
	
	List<TotalUsdRequirementDetails> totalUsdReqDetails=new ArrayList<TotalUsdRequirementDetails>();
	
	
	
	
	public List<TotalUsdRequirementDetails> getTotalUsdReqDetails() {
		return totalUsdReqDetails;
	}
	public void setTotalUsdReqDetails(
			List<TotalUsdRequirementDetails> totalUsdReqDetails) {
		this.totalUsdReqDetails = totalUsdReqDetails;
	}
	public List<FundEstimationDetails> getFundEstimationDetails() {
		return fundEstimationDetails;
	}
	public void setFundEstimationDetails(
			List<FundEstimationDetails> fundEstimationDetails) {
		this.fundEstimationDetails = fundEstimationDetails;
	}
	public double getCurrentUsdBalance() {
		return currentUsdBalance;
	}
	public void setCurrentUsdBalance(double currentUsdBalance) {
		this.currentUsdBalance = currentUsdBalance;
	}
	public double getUsdSalesProjection() {
		return usdSalesProjection;
	}
	public void setUsdSalesProjection(double usdSalesProjection) {
		this.usdSalesProjection = usdSalesProjection;
	}
	public double getFctoUsdRequirment() {
		return fctoUsdRequirment;
	}
	public void setFctoUsdRequirment(double fctoUsdRequirment) {
		this.fctoUsdRequirment = fctoUsdRequirment;
	}
	public double getTotalUsdRequirment() {
		return totalUsdRequirment;
	}
	public void setTotalUsdRequirment(double totalUsdRequirment) {
		this.totalUsdRequirment = totalUsdRequirment;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}
	
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}
	public Boolean getBooCountry() {
		return booCountry;
	}
	public void setBooCountry(Boolean booCountry) {
		this.booCountry = booCountry;
	}
	
	public String getProjectionDate() {
		
		 String projectionDate = null;
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        Calendar calendar = Calendar.getInstance();
	        Date date = calendar.getTime();
	        projectionDate =sdf.format(date);
	        calendar.setTime(new Date());

		return projectionDate;
	}
	public void setProjectionDate(String projectionDate) {
		this.projectionDate = projectionDate;
	}
	
	public void fetchTotalUsd() {
		
	}
	/**
	 * For Exit Button
	 * @throws IOException 
	 */
	public void exit() throws IOException {
		
              if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
			
			
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
			
		}
	}
	

	
	public void displayUsdData(BigDecimal countryId,String roleId,String projectionDate) throws ParseException{

		try {
			BigDecimal sumCurrentUsdBalance = BigDecimal.ZERO;
			BigDecimal sumUsdSalesProjection = BigDecimal.ZERO;
			BigDecimal sumFcTousdRequirement = BigDecimal.ZERO;
			
			fundEstimationDetails=totalUsdRequirementService.getSaleProjectionValues(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
			fundEstimationusd = totalUsdRequirementService.getTotalUsd(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
			
			
			if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
				
				List<BigDecimal> countryData = new ArrayList<BigDecimal>(); 	
				for (FundEstimationDetails fundEstimation2 : fundEstimationDetails) { 
					//if(countryData.contains(fundEstimation2.getExFundEstimtaionDetailsForApplicationCountry().getCountryId())) {
					if(countryData.contains(fundEstimation2.getApplicationCountryId())) {	
					for (TotalUsdRequirementDetails totalUsdRequirementDetails : totalUsdReqDetails) {
							sumFcTousdRequirement = BigDecimal.ZERO;
							sumCurrentUsdBalance = BigDecimal.ZERO;
							if(totalUsdRequirementDetails.getAppCountry().equalsIgnoreCase(fundEstimation2.getApplicationCountryId().toPlainString())){
								for (FundEstimationDetails fundEstimation5 : fundEstimationDetails) {
									sumFcTousdRequirement = sumFcTousdRequirement.add(fundEstimation5.getUsdValue());
								}
								totalUsdRequirementDetails.setCalFcTousdRequirement(sumFcTousdRequirement);
								totalUsdRequirementDetails.setCalUsdSaleProjection(BigDecimal.ZERO);
								setCountryId(fundEstimation2.getApplicationCountryId());
								totalUsdRequirementDetails.setAppCountry(fundEstimationService.getCountryName(getCountryId()));
								totalUsdRequirementDetails.setAppCountry(getCountryId().toPlainString());
							
								for (FundEstimation fundusdsum : fundEstimationusd) {
									sumCurrentUsdBalance = sumCurrentUsdBalance.add(fundusdsum.getUsdCurrentBalanace());
								}
								
								
								totalUsdRequirementDetails.setCalTotalUsdrequirement(sumFcTousdRequirement.subtract(sumCurrentUsdBalance));
								totalUsdRequirementDetails.setUsdCurrentBalanace(sumCurrentUsdBalance);
								setBooCountry(true);
						}
					} 
					
				} else {
					countryData.add(fundEstimation2.getApplicationCountryId());
					
					TotalUsdRequirementDetails tUsdReq = new TotalUsdRequirementDetails();
					tUsdReq.setCalFcTousdRequirement(fundEstimationDetails.get(0).getUsdValue().subtract(fundEstimation2.getFundEstimtaionId().getUsdCurrentBalanace()));
					tUsdReq.setCalUsdSaleProjection(BigDecimal.ZERO);
					setCountryId(fundEstimation2.getApplicationCountryId());
					tUsdReq.setAppCountry(fundEstimationService.getCountryName(getCountryId()));
					tUsdReq.setAppCountry(getCountryId().toPlainString());
					tUsdReq.setUsdCurrentBalanace(fundEstimation2.getFundEstimtaionId().getUsdCurrentBalanace());
					totalUsdReqDetails.add(tUsdReq);
					setBooCountry(true);
				}
			}
				
				
				for (TotalUsdRequirementDetails element : totalUsdReqDetails) {
					System.out.println("USD :: "+element.getUsdCurrentBalanace());
				}
				
				
				
			}else{
				
				System.out.println("====Called Role 2====");
				
				TotalUsdRequirementDetails tUsdReq = new TotalUsdRequirementDetails();
				for (FundEstimationDetails fundEstimation1 : fundEstimationDetails) {
					sumFcTousdRequirement = sumFcTousdRequirement.add(fundEstimation1.getUsdValue());
				}
				tUsdReq.setCalFcTousdRequirement(sumFcTousdRequirement);
				tUsdReq.setCalUsdSaleProjection(BigDecimal.ZERO);
				
				fundEstimationusd = totalUsdRequirementService.getTotalUsd(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),new SimpleDateFormat("dd/MM/yy").parse(getProjectionDate()));
				for (FundEstimation fundusdsum : fundEstimationusd) {
					sumCurrentUsdBalance = sumCurrentUsdBalance.add(fundusdsum.getUsdCurrentBalanace());
				}
				tUsdReq.setCalTotalUsdrequirement(sumFcTousdRequirement.subtract(sumCurrentUsdBalance));
				tUsdReq.setUsdCurrentBalanace(sumCurrentUsdBalance);
				totalUsdReqDetails.add(tUsdReq);
				
				setBooCountry(false);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
	public String  callTotalUsdRequirement() throws IOException, ParseException{
		
		totalUsdReqDetails.clear();
		displayUsdData(sessionStateManage.getCountryId(),sessionStateManage.getRoleId(),getProjectionDate());
		
		
		return "totalUsdRequirement";
	}
	

}
