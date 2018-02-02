package com.amg.exchange.common.bean;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.BankAccountType;
import com.amg.exchange.common.model.BankAccountTypeDesc;
import com.amg.exchange.common.service.IBankAccountTypeService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("bankAccountTypeApproveBean")
@Scope("session")
public class BankAccountTypeApprovalBean<T> {
	
	private static final Logger log= Logger.getLogger(BankAccountTypeApprovalBean.class);
	private SessionStateManage session=new SessionStateManage();
	private List<BankAccountTypeBeanDataTable> bankAccountTypeList=new CopyOnWriteArrayList<BankAccountTypeBeanDataTable>();
	
	@Autowired
	BankAccountTypeBean bankAccountTypeBean;
	@Autowired
	IBankAccountTypeService bankAccountTypeService;
	
	
	public List<BankAccountTypeBeanDataTable> getBankAccountTypeList() {
		return bankAccountTypeList;
	}
	public void setBankAccountTypeList(
			List<BankAccountTypeBeanDataTable> bankAccountTypeList) {
		this.bankAccountTypeList = bankAccountTypeList;
	}



	public void gotoBankAccountTypeScreen(BankAccountTypeBeanDataTable bankAccountTypeDTObj) throws IOException{
		
		if((bankAccountTypeDTObj.getModifiedBy()==null ? bankAccountTypeDTObj.getCreatedBy() : bankAccountTypeDTObj.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
				RequestContext.getCurrentInstance().execute("notApproved.show();");
		}else{
			
		bankAccountTypeBean.setAccountTypeCode(bankAccountTypeDTObj.getAccountTypeCode());
		bankAccountTypeBean.setAccountTypeDesc(bankAccountTypeDTObj.getAccountTypeDesc());
		bankAccountTypeBean.setAccountTypeDescLocal(bankAccountTypeDTObj.getAccountTypeDescLocal());
		bankAccountTypeBean.setAccountTypePk(bankAccountTypeDTObj.getAccountTypePk());
		
		bankAccountTypeBean.setRenderSaveButton(false);
		bankAccountTypeBean.setRenderDataTable(false);
		bankAccountTypeBean.setReadOnlyAccountTypeCode(true);
		bankAccountTypeBean.setReadOnlyAccountTypeDescLocal(true);
		bankAccountTypeBean.setReadOnlyAccountTypeDesc(true);
		bankAccountTypeBean.setRenderSavePanel(false);
		bankAccountTypeBean.setRenderUpdatePanel(true);
		FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttype.xhtml");
		}
		
	}
	
	public void getAllApprovalRecords(){
		bankAccountTypeList.clear();
		List<BankAccountType> bankAccountList=bankAccountTypeService.getAllRecordsForApproval();
		for(BankAccountType bankAccountType:bankAccountList){
			BankAccountTypeBeanDataTable bankAccountTypeDTObj=new BankAccountTypeBeanDataTable();
			bankAccountTypeDTObj.setAccountTypeCode(bankAccountType.getBankAccountTypeCode());
			bankAccountTypeDTObj.setAccountTypePk(bankAccountType.getBankAccountTypeId());
			bankAccountTypeDTObj.setCreatedBy(bankAccountType.getCreatedBy());
			bankAccountTypeDTObj.setModifiedBy(bankAccountType.getModifiedBy());
			
			List<BankAccountTypeDesc> bankAccountTypeDescList=bankAccountTypeService.getallRecordsRelatedBankAccountType(bankAccountType.getBankAccountTypeId());
			for(BankAccountTypeDesc bankAccountTypeDesc:bankAccountTypeDescList){
				if(bankAccountTypeDesc.getLanguageId().getLanguageId().intValue()==1){
					bankAccountTypeDTObj.setAccountTypeDesc(bankAccountTypeDesc.getBankAccountTypeDesc());
				}
				if(bankAccountTypeDesc.getLanguageId().getLanguageId().intValue()==2){
					bankAccountTypeDTObj.setAccountTypeDescLocal(bankAccountTypeDesc.getBankAccountTypeDesc());
				}
			}
			bankAccountTypeList.add(bankAccountTypeDTObj);
		}
		
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToBankAccountTypeApprovalPage() throws IOException{
		getAllApprovalRecords();
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "bankaccounttypeapproval.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../common/bankaccounttypeapproval.xhtml");
	}
}
