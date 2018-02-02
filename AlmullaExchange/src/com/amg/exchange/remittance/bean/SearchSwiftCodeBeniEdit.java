package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.bean.BeneficiaryCreationBean;
import com.amg.exchange.beneficiary.bean.BranchDataTable;
import com.amg.exchange.beneficiary.bean.SearchBranchDeatilsBean;
import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.util.SessionStateManage;
@Component("searchSwiftCodeBeniEdit")
@Scope("session")
public class SearchSwiftCodeBeniEdit<T> implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SearchBranchDeatilsBean.class);
	private String searchBranchName;
	private String searchIFSC;
	private String searchSwift;
	private String searchState;
	private Boolean enableDatatable=false;
	private String errmsg;
	private String beneficiaryBankName;
	private String beneficiaryBankCountryName;
	private BigDecimal beneCountryId;
	private BigDecimal beneBankId;

	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	BeneficiaryEditBean beneficiaryEditBean;
	@Autowired
	IGeneralService<T> generalService;

	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<BranchDataTable> lstDataTableBankbranch;
	private List<StateMasterDesc> lststate;
	public String getSearchBranchName() {
		return searchBranchName;
	}
	public void setSearchBranchName(String searchBranchName) {
		this.searchBranchName = searchBranchName;
	}
	public String getSearchIFSC() {
		return searchIFSC;
	}
	public void setSearchIFSC(String searchIFSC) {
		this.searchIFSC = searchIFSC;
	}
	public String getSearchSwift() {
		return searchSwift;
	}
	public void setSearchSwift(String searchSwift) {
		this.searchSwift = searchSwift;
	}
	public String getSearchState() {
		return searchState;
	}
	public void setSearchState(String searchState) {
		this.searchState = searchState;
	}
	public Boolean getEnableDatatable() {
		return enableDatatable;
	}
	public void setEnableDatatable(Boolean enableDatatable) {
		this.enableDatatable = enableDatatable;
	}
	public String getBeneficiaryBankName() {
		return beneficiaryBankName;
	}
	public void setBeneficiaryBankName(String beneficiaryBankName) {
		this.beneficiaryBankName = beneficiaryBankName;
	}
	public String getBeneficiaryBankCountryName() {
		return beneficiaryBankCountryName;
	}
	public void setBeneficiaryBankCountryName(String beneficiaryBankCountryName) {
		this.beneficiaryBankCountryName = beneficiaryBankCountryName;
	}
	public List<BranchDataTable> getLstDataTableBankbranch() {
		return lstDataTableBankbranch;
	}
	public void setLstDataTableBankbranch(
			List<BranchDataTable> lstDataTableBankbranch) {
		this.lstDataTableBankbranch = lstDataTableBankbranch;
	}
	public List<StateMasterDesc> getLststate() {
		return lststate;
	}
	public void setLststate(List<StateMasterDesc> lststate) {
		this.lststate = lststate;
	}
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public BigDecimal getBeneCountryId() {
		return beneCountryId;
	}
	public void setBeneCountryId(BigDecimal beneCountryId) {
		this.beneCountryId = beneCountryId;
	}
	
	public BigDecimal getBeneBankId() {
		return beneBankId;
	}
	public void setBeneBankId(BigDecimal beneBankId) {
		this.beneBankId = beneBankId;
	}
	public void pageNavigation() {
		try {
			setEnableDatatable(false);
			List<StateMasterDesc> lstDescs=generalService.getStateList(sessionStateManage.getLanguageId(), getBeneCountryId());
			if(lstDescs.size()>0){
				setLststate(lstDescs);
			}
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/branchSearchEditBene.xhtml");
		} catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}

	}
	//clear all fields
	public void clearSearch(){
		setSearchIFSC(null);
		setSearchSwift(null);
		setSearchState(null);
		setEnableDatatable(false);
		setSearchBranchName(null);
		lstDataTableBankbranch.clear();
		setEnableDatatable(false);
		if(lststate!=null){
			lststate.clear();
		}
	}
	//exit
	public void exit(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	//search button
	public void searchBranch(){
		setErrmsg(null);
		try {
			if( (getSearchBranchName() == null || getSearchBranchName().equals("")) 
					&& (getSearchIFSC() == null ||getSearchIFSC().equals("")) && (getSearchSwift() == null || getSearchSwift().equals("")) && (getSearchState()==null || getSearchState().equals("")))
			{
				setErrmsg("Please enter atleast any one of  branch Name , IFSC ,Swift ,State");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchBranchName()!=null && !getSearchBranchName().trim().equals("") && getSearchBranchName().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Branch Name");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchIFSC()!=null && !getSearchIFSC().trim().equals("") &&  getSearchIFSC().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of IFSC");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchSwift()!=null && !getSearchSwift().trim().equals("") &&  getSearchSwift().trim().length() <3)
			{
				setErrmsg("Please enter atleast 3 char of Swift");
				RequestContext.getCurrentInstance().execute("csp.show();");		
				return;
			}

			if(getSearchState()!=null && !getSearchState().trim().equals(""))
			{
				setSearchState(getSearchState());
			}
			setLstDataTableBankbranch(null);
			List<BankBranchView> lstBankbranchView =beneficaryCreation.serachBranch(getBeneBankId(), getSearchBranchName(), getSearchIFSC(), getSearchSwift(), getSearchState());
			List<BranchDataTable> branchDataTablesList=new ArrayList<BranchDataTable>();
			if(lstBankbranchView.size()>0){
				setEnableDatatable(true);
				BranchDataTable datatable = null;
				for (BankBranchView bankBranch : lstBankbranchView) {
					datatable = new BranchDataTable();
					if (bankBranch.getBranchFullName() != null) {
						datatable.setBankFullName(bankBranch.getBranchFullName());
					}
					if (bankBranch.getIfscCode() != null) {
						datatable.setBranchIFSC(bankBranch.getIfscCode());
					}
					if (bankBranch.getStateId() != null) {
						datatable.setStateName(bankBranch.getStateName());
						datatable.setStateId(bankBranch.getStateId());
					}
					if (bankBranch.getDistrictId() != null) {
						datatable.setDistrictName(bankBranch.getDistrictName());
						datatable.setDistictId(bankBranch.getDistrictId());
					}
					if (bankBranch.getCityId() != null) {
						datatable.setCityName(bankBranch.getCityName());
						datatable.setCityId(bankBranch.getCityId());
					}
					if (bankBranch.getSwift() != null) {
						datatable.setSwiftCode(bankBranch.getSwift());
					}
					if (bankBranch.getBankBranchId() != null) {
						datatable.setBankBranchId(bankBranch.getBankBranchId());
						datatable.setBankBranchCode(bankBranch.getBranchCode());
					}
					branchDataTablesList.add(datatable);
					setLstDataTableBankbranch(branchDataTablesList);
				}
			}else{
				setEnableDatatable(false);
				setErrmsg("No record found");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

		}catch (Exception exception) {
			log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			setErrmsg(exception.getMessage()); 
			RequestContext.getCurrentInstance().execute("exception.show();");
			return;
		}
	}
	
	public void selectBranchfromViewWindowforEdit(BranchDataTable branchDataTable){
		clearSearch();
		beneficiaryEditBean.selectBranchfromViewWindowforEdit(branchDataTable);
	}
}
