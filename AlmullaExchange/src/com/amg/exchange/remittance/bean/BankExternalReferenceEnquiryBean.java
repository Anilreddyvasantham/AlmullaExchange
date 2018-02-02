package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.remittance.model.BankExternalReferenceDetail;
import com.amg.exchange.remittance.model.BankExternalReferenceHead;
import com.amg.exchange.treasury.model.BankApplicability;
import com.amg.exchange.treasury.model.BankIndicator;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;

@Component("bankExternalReferenceEnquiryBean")
@Scope("session")
public class BankExternalReferenceEnquiryBean extends BankExternalReferenceBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Boolean booEnableDatatable = false;
	private BigDecimal bank;
	private String errorMsg;
	private SessionStateManage session = new SessionStateManage();
	private List<BankExternalReferenceDataTable> viewList = new ArrayList<BankExternalReferenceDataTable>();
	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private Map<BigDecimal, String> bankCountryMap = new HashMap<BigDecimal, String>();
	private List<BankApplicability> bankList = new ArrayList<BankApplicability>();
	private Map<BigDecimal, String> bankMap = new HashMap<BigDecimal, String>();
	private Map<BigDecimal, String> bankCodeMap = new HashMap<BigDecimal, String>();
	private List<BankExternalReferenceDailogDataTable> popup = new ArrayList<BankExternalReferenceDailogDataTable>();

	public List<BankExternalReferenceDailogDataTable> getPopup() {
		return popup;
	}

	public void setPopup(List<BankExternalReferenceDailogDataTable> popup) {
		this.popup = popup;
	}

	public List<BankExternalReferenceDataTable> getViewList() {
		return viewList;
	}

	public void setViewList(List<BankExternalReferenceDataTable> viewList) {
		this.viewList = viewList;
	}

	public Boolean getBooEnableDatatable() {
		return booEnableDatatable;
	}

	public void setBooEnableDatatable(Boolean booEnableDatatable) {
		this.booEnableDatatable = booEnableDatatable;
	}

	public void clearEnquiryBean() {
		setBankCountryId(null);
		setBank(null);
		popup.clear();
		bankList.clear();
		setBooEnableDatatable(false);
	}

	public BigDecimal getBank() {
		return bank;
	}

	public void setBank(BigDecimal bank) {
		this.bank = bank;
	}

	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<CountryMasterDesc> getCountryList() {
		countryList = generalService.getCountryList(session.getLanguageId());
		for (CountryMasterDesc bankMaster : countryList) {
			bankCountryMap.put(bankMaster.getFsCountryMaster().getCountryId(), bankMaster.getCountryName());
		}
		return countryList;
	}

	public void getBankOnCountry() {
		try{
		BigDecimal pkCorresBankInd = null;
		BigDecimal pkServProBankInd = null;
		List<BankIndicator> corrBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_CORR_BANK);
		if (corrBankIndlist.size() != 0) {
			pkCorresBankInd = corrBankIndlist.get(0).getBankIndicatorId();
		}
		List<BankIndicator> serviceBankIndlist = bankIndicatorService.getRecordFromDB(Constants.BANK_INDICATOR_SERVICEPRO_BANK);
		if (serviceBankIndlist.size() != 0) {
			pkServProBankInd = serviceBankIndlist.get(0).getBankIndicatorId();
		}
		if (pkCorresBankInd != null && pkServProBankInd != null) {
			bankList = bankExternalReferenceservice.getBankListbyIndicators(getBankCountryId(), pkCorresBankInd, pkServProBankInd);
			if (null != bankList) {
				System.out.println(bankList.size());
			}
			for (BankApplicability bankMaster : bankList) {
				bankMap.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankFullName());
				bankCodeMap.put(bankMaster.getBankMaster().getBankId(), bankMaster.getBankMaster().getBankCode());
				System.out.println(bankMaster.getBankMaster().getBankId());
				System.out.println(bankMaster.getBankMaster().getBankCode());
				System.out.println(bankMaster.getBankMaster().getBankFullName());
			}
			setBankList(bankList);
		} else {
			System.out.println("List Not Excuted");
		}
		}catch(NullPointerException  e){ 
			 
				setErrorMsg("getBankOnCountry :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
	}

	public void clickOnExit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public void bankExternalReferenceEnquiryPageNavigation() {
		popup.clear();
		viewList.clear();
		setBankCountryId(null);
		setBank(null);
		setBooEnableDatatable(false);
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "BankexternalreferenceEnquiry.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/BankexternalreferenceEnquiry.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void viewAll() {
		try{
		System.out.println("Entering into viewAll method");
		boolean fetchAll = true;
		setBooEnableDatatable(true);
		viewList = new ArrayList<BankExternalReferenceDataTable>();
		System.out.println(getBankCountryId());
		System.out.println(getBank());
		List<BankExternalReferenceHead> hList = bankExternalReferenceservice.getAllRecordsfromHead(fetchAll, getBankCountryId(), getBank());
		System.out.println(hList.size());
		// List<BankExternalReferenceDetail> aList=
		// bankExternalReferenceservice.getAllRecords();
		for (BankExternalReferenceHead head : hList) {
			BankExternalReferenceDataTable dataTableRecord = new BankExternalReferenceDataTable();
			dataTableRecord.setBankExtRefSeqId(head.getBankExtRefSeqId());
			dataTableRecord.setBankId(head.getBank().getBankId());
			dataTableRecord.setBankCode(head.getBankCode());
			dataTableRecord.setBankName(generalService.getBankName(head.getBank().getBankId()));
			// BankExternalReferenceDetail detail =
			// bankExternalReferenceservice.findDetailById(head.getBankExtRefSeqId()).get(0);
			dataTableRecord.setBankExternalId(head.getBankExternalId());
			BigDecimal countryId = head.getBankCountry().getCountryId();
			dataTableRecord.setCountryId(countryId);
			dataTableRecord.setCountryName(generalService.getCountryName(new BigDecimal(1), countryId));
			dataTableRecord.setBeneficaryBankCode(head.getBeneficaryBankCode());
			dataTableRecord.setBeneficaryBankId(head.getBeneficaryBank().getBankId());
			dataTableRecord.setBeneficaryBankName(generalService.getBankName(head.getBeneficaryBank().getBankId()));
			dataTableRecord.setCreatedBy(head.getCreatedBy());
			dataTableRecord.setCreatedOn(head.getCreatedDate());
			dataTableRecord.setIsActive(setRecordStatus(head.getIsActive()));
			System.out.println(dataTableRecord);
			viewList.add(dataTableRecord);
		}
		}catch(NullPointerException  e){ 
				setErrorMsg("viewAll :");
				RequestContext.getCurrentInstance().execute("nullPoiterId.show();");
			}
		catch (Exception e) {
				setErrorMsg(e.getMessage());
				RequestContext.getCurrentInstance().execute("csp.show();");
			}
		System.out.println("Exit into viewAll method");
	}

	private String setRecordStatus(String status) {
		String strStatus = null;
		if (status.equalsIgnoreCase(Constants.U)) {
			strStatus = "Un_ Approve";
		}
		if (status.equalsIgnoreCase(Constants.D)) {
			strStatus = "Deleted";
		}
		if (status.equalsIgnoreCase(Constants.Yes)) {
			strStatus = "Activated";
		}
		return strStatus;
	}

	public void viewBranchData(BankExternalReferenceDataTable event) {
		try{
		popup.clear();
		popup = null;
		popup = new ArrayList<BankExternalReferenceDailogDataTable>();
		System.out.println(event.getBankExtRefDetailSeqId());
		System.out.println(event.getBankExtRefSeqId());
		if (event.getBankExtRefSeqId() != null) {
			List<BankExternalReferenceDetail> detailList = bankExternalReferenceservice.findDetailById(event.getBankExtRefSeqId());
			System.out.println(detailList.size());
			for (BankExternalReferenceDetail detail : detailList) {
				BankExternalReferenceDailogDataTable dialogTable = new BankExternalReferenceDailogDataTable();
				dialogTable.setBranchCode(detail.getBeneficaryBranch().getBranchCode());
				dialogTable.setBranchDescription(detail.getBeneficaryBranch().getBranchFullName());
				System.out.println(detail.getBeneficaryBranch().getBranchCode());
				System.out.println(detail.getBeneficaryBranch().getBranchFullName());
				System.out.println(detail.getBankBranchExternalId());
				dialogTable.setBranchExternalId(detail.getBankBranchExternalId());
				popup.add(dialogTable);
			}
		}
		if (!popup.isEmpty()) {
			RequestContext.getCurrentInstance().execute("datatabledetails.show();");
		} else {
			RequestContext.getCurrentInstance().execute("nobranchdetails.show();");
		}
		}catch(NullPointerException  e){ 
			setErrorMsg("viewBranchData :");
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