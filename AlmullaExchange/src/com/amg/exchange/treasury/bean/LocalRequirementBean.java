package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.FundEstimation;
import com.amg.exchange.treasury.model.FundEstimationDetails;
import com.amg.exchange.treasury.service.IFundEstimationService;
import com.amg.exchange.treasury.service.ILocalRequirementService;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestService;
import com.amg.exchange.util.SessionStateManage;

@Component("localRequirementBean")
@Scope("session")
public class LocalRequirementBean<T> implements Serializable {

	private BigDecimal countryId = null;
	private BigDecimal currencyId = null;
	private BigDecimal balance = null;
	private BigDecimal totalSaleProjection = null;

	private SessionStateManage sessionStateManage = new SessionStateManage();

	@Autowired
	ILocalRequirementService<T> localRequirementService;
	@Autowired
	ISpecialCustomerDealRequestService<T> iSpecialCustomerDealRequestService;
	@Autowired
	IFundEstimationService<T> fundEstimationService;

	private List<FundEstimation> fundEstimationList2 = new ArrayList<FundEstimation>();
	private List<FundEstimation> fundEstimationList = new ArrayList<FundEstimation>();
	private List<LocalRequirementDataTable> localRequirementDTList=new ArrayList<LocalRequirementDataTable>();
	private List<LocalRequirementDataTable> loRequirementDT=new ArrayList<LocalRequirementDataTable>();

	public void setLocalRequirementDTList(
			List<LocalRequirementDataTable> localRequirementDTList) {
		this.localRequirementDTList = localRequirementDTList;
	}

	public List<FundEstimationDetails> getFundEstimationDetailsList() {
	
		return localRequirementService.getFundEstimationDetailsList(sessionStateManage.getCountryId());
	}

	public List<FundEstimation> getFundEstimationList() {
		return fundEstimationList;
	}

	public void setFundEstimationList(List<FundEstimation> fundEstimationList) {
		this.fundEstimationList = fundEstimationList;
	}
	public BigDecimal getCountryId() {
		return countryId;
	}

	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public BigDecimal getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(BigDecimal currencyId) {
		this.currencyId = currencyId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getTotalSaleProjection() {
		return totalSaleProjection;
	}

	public void setTotalSaleProjection(BigDecimal totalSaleProjection) {
		this.totalSaleProjection = totalSaleProjection;
	}
	/*public List<LocalRequirementDataTable> getLoRequirementDT() {
		return loRequirementDT;
	}

	public void setLoRequirementDT(List<LocalRequirementDataTable> loRequirementDT) {
		this.loRequirementDT = loRequirementDT;
	}

	public List<LocalRequirementDataTable> getLocalRequirementDTList() {
		localRequirementDTList.clear();
		int totalAmount=0;
		int totalProjectionAmount=0;
		String s1=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		List<BankCountryPopulationBean> bankCountryList= fundEstimationService.getBankContryFromView(sessionStateManage.getCountryId());
		for(BankCountryPopulationBean countryList:bankCountryList){
		
		fundEstimationList2=localRequirementService.getFundEstimationListBasedOnCurrency(countryList.getBankCountryId());
		if(fundEstimationList2.size()>0){
			LocalRequirementDataTable localRequirementDataTable=new LocalRequirementDataTable();
			localRequirementDataTable.setBankCountryId(fundEstimationList2.get(0).getCountryId());
			localRequirementDataTable.setBankCountry(iSpecialCustomerDealRequestService.getBankCountryNameForUpdate(fundEstimationList2.get(0).getExFundEstimationForBankCountry().getCountryId()));
			localRequirementDataTable.setBankCurrency(iSpecialCustomerDealRequestService.getCurrencyForUpdate(fundEstimationList2.get(0).getExCurrenyMaster().getCurrencyId()));
			totalProjectionAmount=0;
			for(FundEstimation fundEst:fundEstimationList2){
				if((fundEst.getExFundEstimationForBankCountry().getCountryId()).equals(countryList.getBankCountryId())){
				totalAmount=fundEst.getPreviousDaysCurrentBalance().intValue();
				}
				List<FundEstimationDetails> fundEstimation=localRequirementService.getFundEstimationDetailsList(fundEst.getFundEstimtaionId());
				for(FundEstimationDetails fundEstma:fundEstimation){
					String s2=new SimpleDateFormat("dd/MM/yyyy").format(fundEstma.getProjectionDate());
					if((fundEstma.getExFundEstimtaionDeatilsForBankCountry().getCountryId()).equals(countryList.getBankCountryId())){
						if(s1.equals(s2)){
						totalProjectionAmount=totalProjectionAmount+fundEstma.getSalesForeignCurrencyProjection().intValue();
						}
					}
				}
			}
			localRequirementDataTable.setLocalBalance(new BigDecimal(totalProjectionAmount));
			localRequirementDataTable.setTotalAmount(new BigDecimal(totalAmount));
			localRequirementDTList.add(localRequirementDataTable);
		}
	
		}
		return localRequirementDTList;
	}*/

	/*public void populateProjectionDetails(LocalRequirementDataTable LocalReq) {
		loRequirementDT.clear();
		String s1=new SimpleDateFormat("dd/MM/yyyy").format(new Date());
		
		List<FundEstimationDetails> fundEstimation = localRequirementService.getFundEstimationDetailsListBasedOnCountry(LocalReq.getBankCountryId());
		
			for(int i=0;i<fundEstimation.size();i++){
			
			LocalRequirementDataTable localDT=new LocalRequirementDataTable();
			FundEstimationDetails fundEsti=fundEstimation.get(i);
			String s3=new SimpleDateFormat("dd/MM/yyyy").format(fundEsti.getProjectionDate());
			if(s1.equals(s3)){
			localDT.setBankCountryId(fundEsti.getExFundEstimtaionDeatilsForBankCountry().getCountryId());
			localDT.setBankCountry(iSpecialCustomerDealRequestService.getBankCountryNameForUpdate(fundEsti.getExFundEstimtaionDeatilsForBankCountry().getCountryId()));
			localDT.setBankCurrency(iSpecialCustomerDealRequestService.getCurrencyForUpdate( fundEsti.getExCurrenyMaster().getCurrencyId()));
			//localDT.setBankName(iSpecialCustomerDealRequestService.getBankNameForUpdate(fundEsti.getExBankMaster().getBankId()));
			localDT.setBankName(iSpecialCustomerDealRequestService.getBankNameForUpdate(fundEsti.getBankId()));
			
			
			
			fundEstimationList=localRequirementService.getFundEstimationListBasedOnCurrency(LocalReq.getBankCountryId());
			for(FundEstimation fundEs:fundEstimationList){
			if(fundEs.getExFundEstimationForBankCountry().getCountryId().equals(LocalReq.getBankCountryId())){
				localDT.setLocalBalance(fundEs.getPreviousDaysCurrentBalance());
			}
			}
			
			localDT.setTotalAmount(fundEsti.getSalesForeignCurrencyProjection());
			localDT.setSaleProjection(fundEsti.getProjectionDate());
			loRequirementDT.add(localDT);
		}
	}
		setLoRequirementDT(loRequirementDT);
		RequestContext context = RequestContext.getCurrentInstance();
		context.execute("localReq.show();");
	}*/
	
	public void localRequirementPageNavigation(){
		//getLocalRequirementDTList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../search/LocalRequirement.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exit() throws IOException {
        if(sessionStateManage.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	

}
