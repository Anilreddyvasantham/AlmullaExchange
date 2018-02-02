package com.amg.exchange.cashtransfer.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cashtransfer.model.CashHeader;
import com.amg.exchange.cashtransfer.service.ICashTransferLToLService;
import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("cashTransferApprove")
@Scope("session")
public class CashTransferApprove<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private String location;
	private String cashier;
	private String warningMessage;

	private SessionStateManage session=new SessionStateManage();
	private List<CashTransferApproveDataTable> cashTransferApproveList=new ArrayList<CashTransferApproveDataTable>();

	@Autowired
	ICashTransferLToLService cashTransferLToLService;

	public List<CashTransferApproveDataTable> getCashTransferApproveList() {
		return cashTransferApproveList;
	}
	public void setCashTransferApproveList(List<CashTransferApproveDataTable> cashTransferApproveList) {
		this.cashTransferApproveList = cashTransferApproveList;
	}

	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}

	public String getCashier() {
		return cashier;
	}
	public void setCashier(String cashier) {
		this.cashier = cashier;
	}
	
	public String getWarningMessage() {
		return warningMessage;
	}
	public void setWarningMessage(String warningMessage) {
		this.warningMessage = warningMessage;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToCashTransferApprovalPage(){
		cashTransferApproveList.clear();
		setCashier(session.getUserName());
		List<CountryBranch> branchList = cashTransferLToLService.getBranchName(new BigDecimal(session.getBranchId()));
		setLocation(branchList.get(0).getBranchName());
		getCashDataForApprove();
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "CashTransferApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../cashtransfer/CashTransferApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void getCashDataForApprove(){
		cashTransferApproveList.clear();
		List<CashHeader> cashHeaderList=cashTransferLToLService.getCashDataForApprove(session.getUserName(),new BigDecimal(session.getBranchId()));
		for(CashHeader cashHeader:cashHeaderList){
			CashTransferApproveDataTable cashTransferApproveDTobj=new CashTransferApproveDataTable();
			cashTransferApproveDTobj.setCashHeaderPk(cashHeader.getCashHeaderId());
			List<CountryBranch> branchList = cashTransferLToLService.getBranchName(cashHeader.getCountryBranchId().getCountryBranchId());
			cashTransferApproveDTobj.setFromLocation(branchList.get(0).getBranchName());
			cashTransferApproveDTobj.setFromLocationId(cashHeader.getCountryBranchId().getCountryBranchId());
			cashTransferApproveDTobj.setFromCashier(cashHeader.getFromCashier());
			cashTransferApproveDTobj.setTransferDocumentDate(cashHeader.getDocumentDate());
			cashTransferApproveDTobj.setTransferDocumentCode(cashHeader.getDocumentCode());
			cashTransferApproveDTobj.setTransferDocumentNo(cashHeader.getDocumentNo());
			cashTransferApproveDTobj.setTransferDocumentYear(cashHeader.getFinancialYear());
			cashTransferApproveDTobj.setCreatedUserName(cashHeader.getCreatedBy());
			cashTransferApproveList.add(cashTransferApproveDTobj);

		}

	}

	public void approveRecord(CashTransferApproveDataTable cashTransferApproveDTobj){

		try {
			if(!cashTransferApproveDTobj.getCreatedUserName().equalsIgnoreCase(session.getUserName())){
				
				// checking stock is available or not
				String errmsgValidate = cashTransferLToLService.procedurePValidateCashTransferStock(session.getCountryId(),session.getCompanyId(),cashTransferApproveDTobj.getTransferDocumentCode() ,cashTransferApproveDTobj.getTransferDocumentYear(),cashTransferApproveDTobj.getTransferDocumentNo());
				
				if(errmsgValidate != null){
					setWarningMessage("Exception : "+errmsgValidate);
					RequestContext.getCurrentInstance().execute("proceErr.show();");
				}else{
					cashTransferLToLService.approveRecord(cashTransferApproveDTobj.getCashHeaderPk(), session.getUserName());
					
					// call Procedure EX_P_POP_CASH_TRANSFER_APPROVE				
					String errmsg = cashTransferLToLService.procedurePopulateCashTransferApproval(session.getCountryId(),session.getCompanyId(),cashTransferApproveDTobj.getTransferDocumentCode() ,cashTransferApproveDTobj.getTransferDocumentYear(),cashTransferApproveDTobj.getTransferDocumentNo());
					
					if(errmsg != null){
						setWarningMessage("Exception : "+errmsg);
						RequestContext.getCurrentInstance().execute("proceErr.show();");
					}else{
						RequestContext.getCurrentInstance().execute("approve.show();");
					}
				}
				
			}else{
				RequestContext.getCurrentInstance().execute("notApproved.show();");
			}
		} catch (Exception e) {
			setWarningMessage("Exception : "+e.getMessage());
			RequestContext.getCurrentInstance().execute("proceErr.show();");
		}


	}

	public void clickOk(){
		getCashDataForApprove();
	}


}
