package com.amg.exchange.beneficiary.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.beneficiary.model.BankBranchView;
import com.amg.exchange.beneficiary.service.IBeneficaryCreation;
import com.amg.exchange.common.model.StateMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.bean.BankBranchDetails;
import com.amg.exchange.util.SessionStateManage;

@Component("searchBranch")
@Scope("session")
public class SearchBranchDeatilsBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SearchBranchDeatilsBean.class);
	private String searchBranchName;
	private String searchIFSC;
	private String searchSwift;
	private String searchState;
	private Boolean enableDatatable;
	private String errmsg;
	private String beneficiaryBankName;
	private String beneficiaryBankCountryName;

	@Autowired
	IBeneficaryCreation beneficaryCreation;
	@Autowired
	BeneficiaryCreationBean beneficiaryCreationBean;
	@Autowired
	IGeneralService<T> generalService;
	@Autowired
	ApplicationContext appContext;

	SessionStateManage sessionStateManage = new SessionStateManage();
	private List<BranchDataTable> lstDataTableBankbranch = new ArrayList<BranchDataTable>();
	private List<StateMasterDesc> lststate = new ArrayList<StateMasterDesc>();


	@SuppressWarnings("unchecked")
	public SearchBranchDeatilsBean(){
		setSearchState(null);
		//			this.setLststate(null);
		/*	HttpSession session = sessionStateManage.getSession();
			if(lststate!=null){
				lststate.clear();
			}
			List<StateMasterDesc> lststate= (List<StateMasterDesc>)session.getAttribute("stateList");*/

		//			this.setLststate(lststate);
	}

	public String getBeneficiaryBankName() {
		//setting bank name
		HttpSession session = sessionStateManage.getSession();
		String beneficiaryBankName = (String)session.getAttribute("BenificiaryBankName");
		return beneficiaryBankName;
	}

	public List<StateMasterDesc> getLststate() {
		HttpSession session = sessionStateManage.getSession();
		List<StateMasterDesc> lststate= (List<StateMasterDesc>)session.getAttribute("stateList");
		return lststate;
	}




	public String getBeneficiaryBankCountryName() {
		//setting bank name
		HttpSession session = sessionStateManage.getSession();
		String beneficiaryBankCountryName = (String)session.getAttribute("BenificiaryCountryName");
		return beneficiaryBankCountryName;
	}

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



	public String getErrmsg() {
		return errmsg;
	}



	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}

	public List<BranchDataTable> getLstDataTableBankbranch() {
		return lstDataTableBankbranch;
	}

	public void setLstDataTableBankbranch(
			List<BranchDataTable> lstDataTableBankbranch) {
		this.lstDataTableBankbranch = lstDataTableBankbranch;
	}

	public void searchBranch()
	{
		setErrmsg(null);
		try {
			HttpSession session = sessionStateManage.getSession();

			BigDecimal beniBankId=(BigDecimal)session.getAttribute("BenificiaryBankID");
			BigDecimal beneCountryId = (BigDecimal)session.getAttribute("BenificiaryCountryID");
			System.out.println("Bene BANK ID:"+beniBankId+"\t beneCountryId :"+beneCountryId);

			System.out.println("getSearchBranchName() :"+getSearchBranchName()+"\t getSearchIFSC() :"+getSearchIFSC()+"\t getSearchSwift() :"+getSearchSwift()+"\t getSearchState() :"+getSearchState());

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
				setSearchState(getSearchState().toString());
			}

			lstDataTableBankbranch.clear();
			System.out.println("Entering into searchBranch method");
			System.out.println(getSearchBranchName());
			System.out.println(getSearchIFSC());
			System.out.println(getSearchSwift());
			System.out.println(getSearchState());
			System.out.println("Exit into searchBranch method");

			List<BankBranchView> lstBankbranchView = beneficaryCreation.serachBranch(beniBankId, getSearchBranchName(), getSearchIFSC(), getSearchSwift(), getSearchState());
			
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
					lstDataTableBankbranch.add(datatable);
				}
			}else{
				setEnableDatatable(false);
				setErrmsg("No record found");
				RequestContext.getCurrentInstance().execute("csp.show();");
				return;
			}

		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("csp.show();");
			setErrmsg(e.toString());
			return;
		}
	}

	public void selectBranchfromViewWindowforEdit(BranchDataTable branchDataTable) {
		System.out.println("Entering into selectBranchfromViewWindow method");
		HttpSession session = sessionStateManage.getSession();
		Boolean editBaneficiary=(Boolean)session.getAttribute("EditBaneficiary");
		Boolean creationBaneficiary=(Boolean)session.getAttribute("CreationBaneficiary");
		Boolean editBeneAccount=(Boolean)session.getAttribute("editBeneAccount");
		Boolean editBankBranchMaster=(Boolean)session.getAttribute("EditBankBranchMaster");
		clearSearch();
		if(editBaneficiary!=null && editBaneficiary){		
			beneficiaryCreationBean.selectBranchfromViewWindowforEdit(branchDataTable);
			/*try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/firsttimebeneficaryedit.xhtml");
			} catch (Exception e) {
				session.removeAttribute("EditBaneficiary");
				session.removeAttribute("CreationBaneficiary");
				session.removeAttribute("editBeneAccount");
				session.removeAttribute("stateList");
				e.printStackTrace();
			}*/
		}else if(creationBaneficiary !=null && creationBaneficiary){
			beneficiaryCreationBean.selectBranchfromViewWindow(branchDataTable);
		}else if(editBeneAccount !=null && editBeneAccount){
			beneficiaryCreationBean.selectBranchfromViewWindowEdit(branchDataTable);
		}else if(editBankBranchMaster != null && editBankBranchMaster){
			BankBranchDetails<T> bankBranch = (BankBranchDetails<T>)appContext.getBean("bankBranchDetails");
			bankBranch.fetchBankBranchMaster(branchDataTable);
		}

		session.removeAttribute("EditBaneficiary");
		session.removeAttribute("CreationBaneficiary");
		session.removeAttribute("editBeneAccount");
		session.removeAttribute("stateList");
		session.removeAttribute("EditBankBranchMaster");

		System.out.println("Exit into selectBranchfromViewWindow method");
	}


	public void clearSearch()
	{
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

	public void exit(){

		HttpSession session = sessionStateManage.getSession();
		Boolean editBaneficiary=(Boolean)session.getAttribute("EditBaneficiary");
		Boolean creationBaneficiary=(Boolean)session.getAttribute("CreationBaneficiary");
		Boolean editBeneAccount=(Boolean)session.getAttribute("editBeneAccount");
		Boolean editBankBranchMaster=(Boolean)session.getAttribute("EditBankBranchMaster");

		clearSearch();
		if(editBaneficiary!=null && editBaneficiary){		
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/firsttimebeneficaryedit.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(creationBaneficiary !=null && creationBaneficiary){

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/beneficiarybankaccountdetails.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(editBeneAccount !=null && editBeneAccount){

			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../beneficary/editbeneficiarybankaccountdetails.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else if(editBankBranchMaster != null && editBankBranchMaster){
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankbranchdetails.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		session.removeAttribute("EditBaneficiary");
		session.removeAttribute("CreationBaneficiary");
		session.removeAttribute("editBeneAccount");
		session.removeAttribute("stateList");


	}



}

