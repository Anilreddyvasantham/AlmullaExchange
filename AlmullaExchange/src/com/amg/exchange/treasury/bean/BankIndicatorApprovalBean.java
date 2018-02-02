package com.amg.exchange.treasury.bean;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.BankIndicatorDescription;
import com.amg.exchange.treasury.service.IBankIndicatorService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankIndicatorApprovalBean")
@Scope("session")
public class BankIndicatorApprovalBean<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	private SessionStateManage session = new SessionStateManage();
	private String bankIndicatorCode;
	private String bankIndicatorDescInEnglish;
	private String bankIndicatorDescInLocal;
	private String errorMessage;
	private Boolean booVisible;
	@Autowired
	IBankIndicatorService bankIndicatorService;
	@Autowired
	BankIndicatorBean bankIndicatorBean;
	// List<BankIndicator> bankIndunappList=new ArrayList<BankIndicator>();
	List<BankIndicatorDescription> bankIndunappList = new ArrayList<BankIndicatorDescription>();

	public List<BankIndicatorDescription> getBankIndunappList() {
		return bankIndunappList;
	}

	public void setBankIndunappList(List<BankIndicatorDescription> bankIndunappList) {
		this.bankIndunappList = bankIndunappList;
	}

	List<BankIndicatorDataTableBean> bankIndapprovalList = new ArrayList<BankIndicatorDataTableBean>();

	public Boolean getBooVisible() {
		return booVisible;
	}

	public void setBooVisible(Boolean booVisible) {
		this.booVisible = booVisible;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<BankIndicatorDataTableBean> getBankIndapprovalList() {
		return bankIndapprovalList;
	}

	public String getBankIndicatorCode() {
		return bankIndicatorCode;
	}

	public void setBankIndicatorCode(String bankIndicatorCode) {
		this.bankIndicatorCode = bankIndicatorCode;
	}

	public String getBankIndicatorDescInEnglish() {
		return bankIndicatorDescInEnglish;
	}

	public void setBankIndicatorDescInEnglish(String bankIndicatorDescInEnglish) {
		this.bankIndicatorDescInEnglish = bankIndicatorDescInEnglish;
	}

	public String getBankIndicatorDescInLocal() {
		return bankIndicatorDescInLocal;
	}

	public void setBankIndicatorDescInLocal(String bankIndicatorDescInLocal) {
		this.bankIndicatorDescInLocal = bankIndicatorDescInLocal;
	}

	public SessionStateManage getSession() {
		return session;
	}

	public void setSession(SessionStateManage session) {
		this.session = session;
	}

	public IBankIndicatorService getBankIndicatorService() {
		return bankIndicatorService;
	}

	public void setBankIndicatorService(IBankIndicatorService bankIndicatorService) {
		this.bankIndicatorService = bankIndicatorService;
	}

	public void approvalList() {
		try {
			bankIndunappList.clear();
			try {
				bankIndunappList = bankIndicatorService.getAllRecordsForApproval();
			} catch (Exception e) {
				RequestContext.getCurrentInstance().execute("error.show();");
				setErrorMessage(e.toString());
				setBooVisible(true);
				return;
			}
			if (bankIndunappList.size() > 0) {
				for (BankIndicatorDescription bankIndDescObj : bankIndunappList) {
					BankIndicatorDataTableBean bankIndData = new BankIndicatorDataTableBean();
					bankIndData.setBankIndicatorCode(bankIndDescObj.getBankIndicator().getBankIndicatorCode());
					bankIndData.setBankIndicatorId(bankIndDescObj.getBankIndicator().getBankIndicatorId());
					bankIndData.setCreatedBy(bankIndDescObj.getBankIndicator().getCreatedBy());
					bankIndData.setCreatedDate(bankIndDescObj.getBankIndicator().getCreatedDate());
					bankIndData.setModifiedBy(bankIndDescObj.getBankIndicator().getModifiedBy());
					bankIndData.setModifiedDate(bankIndDescObj.getBankIndicator().getModifiedDate());
					if (bankIndDescObj.getLanguageType().getLanguageId().toString().equals(Constants.ENGLISH_LANGUAGE_ID.toString())) {
						bankIndData.setBankIndicatorEnglishDecPk(bankIndDescObj.getBankIndicatorDescId());
						bankIndData.setBankIndicatorDescInEnglish(bankIndDescObj.getBankIndicatorDescription());
						if (bankIndData.getBankIndicatorEnglishDecPk() != null && bankIndData.getBankIndicatorLocalDecPk() != null) {
							bankIndapprovalList.add(bankIndData);
						}
					}
					// List<BankIndicatorDescription> list1 =
					// bankIndicatorService.getAllRecordsForApproval();
					for (BankIndicatorDescription bankInDescObj : bankIndunappList) {
						if (bankIndData.getBankIndicatorCode().toString().equals(bankInDescObj.getBankIndicator().getBankIndicatorCode()) && bankInDescObj.getLanguageType().getLanguageId().toString().equals(Constants.ARABIC_LANGUAGE_ID.toString())) {
							bankIndData.setBankIndicatorLocalDecPk(bankInDescObj.getBankIndicatorDescId());
							bankIndData.setBankIndicatorDescInLocal(bankInDescObj.getBankIndicatorDescription());
							if (bankIndData.getBankIndicatorEnglishDecPk() != null && bankIndData.getBankIndicatorLocalDecPk() != null) {
								bankIndapprovalList.add(bankIndData);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			RequestContext.getCurrentInstance().execute("error.show();");
			setErrorMessage(e.toString());
			setBooVisible(true);
			return;
		}
	}

	public void setBankIndapprovalList(List<BankIndicatorDataTableBean> bankIndapprovalList) {
		this.bankIndapprovalList = bankIndapprovalList;
	}

	public void navigationEdit(BankIndicatorDataTableBean dataTable) {
		bankIndapprovalList.clear();
		if ((dataTable.getModifiedBy() == null ? dataTable.getCreatedBy() : dataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())) {
			RequestContext.getCurrentInstance().execute("username.show();");
		} else {
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorMaster.xhtml");
			} catch (IOException e) {
				e.printStackTrace();
			}
			bankIndicatorBean.setBooRenderDatatable(false);
			bankIndicatorBean.setBooReadOnly(true);
			bankIndicatorBean.setBooRenderApprove(true);
			bankIndicatorBean.setBooRenderSave(false);
			bankIndicatorBean.setBooSubmit(false);
			bankIndicatorBean.setBankIndicatorMasterPk(dataTable.getBankIndicatorId());
			bankIndicatorBean.setBankIndicatorCode(dataTable.getBankIndicatorCode());
			bankIndicatorBean.setBankIndicatorDescInEnglish(dataTable.getBankIndicatorDescInEnglish());
			bankIndicatorBean.setBankIndicatorDescInLocal(dataTable.getBankIndicatorDescInLocal());
			bankIndicatorBean.setCreatedBy(dataTable.getCreatedBy());
			bankIndicatorBean.setCreatedDate(dataTable.getCreatedDate());
			bankIndicatorBean.setModifiedBy(dataTable.getModifiedBy());
			bankIndicatorBean.setModifiedDate(dataTable.getModifiedDate());
		}
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void bankIndicatorPageApproval() {
		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankIndicatorApproval.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorApproval.xhtml");
			bankIndapprovalList.clear();
			approvalList();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void cancel() {
		bankIndapprovalList.clear();
		approvalList();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../treasury/bankIndicatorApproval.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
