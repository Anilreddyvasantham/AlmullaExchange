package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.util.SessionStateManage;


/*
 * Author RAHAMATHALI SHAIK
 */
@Component("additionBankRuleMapApprovalBean")
@Scope("session")
public class AdditionBankRuleMapApprovalBean {
	
	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;
	
	@Autowired
	IGeneralService generalService;
	
	@Autowired
	AdditionalBankRuleBean additionalBankRuleBean;
	
	SessionStateManage session=new SessionStateManage();
List<AdditionBankRuleMapApprovalDataTable>	addtionBankRuleMapDataTableList=new ArrayList<AdditionBankRuleMapApprovalDataTable>();
	
public List<AdditionBankRuleMapApprovalDataTable> getAddtionBankRuleMapDataTableList() {
	  fetchDataForApproval();
	return addtionBankRuleMapDataTableList;
}

public void setAddtionBankRuleMapDataTableList(
		List<AdditionBankRuleMapApprovalDataTable> addtionBankRuleMapDataTableList) {
	this.addtionBankRuleMapDataTableList = addtionBankRuleMapDataTableList;
}



public void approveAdditionBankRuleMap(AdditionBankRuleMapApprovalDataTable bankRuleMapDataTable) throws IOException{
	if((bankRuleMapDataTable.getModifiedBy()==null ? bankRuleMapDataTable.getCreatedBy() : bankRuleMapDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
		RequestContext.getCurrentInstance().execute("notApproved.show();");
	}else{
	additionalBankRuleBean.setRenderCountryIdBankMapForApprove(true);
	additionalBankRuleBean.setRenderCountryIdBankMap(false);
	additionalBankRuleBean.setAdditionalBankRuleMapRendered(false);
	additionalBankRuleBean.setSaveAdditionalBankRule1(false);
	additionalBankRuleBean.setCountryNameForBankMap(bankRuleMapDataTable.getCountryName());
	additionalBankRuleBean.setRenderFlexFieldBankMapForApprove(true);
	additionalBankRuleBean.setRenderFlexFieldBankMap(false);
	additionalBankRuleBean.setFlexFieldNameForBankMap(bankRuleMapDataTable.getFieldName());
	additionalBankRuleBean.setFlexFieldForBankMap(bankRuleMapDataTable.getFlexField());
	additionalBankRuleBean.setRenderFlexNameBankMapForApprove(true);
	additionalBankRuleBean.setRenderFlexNameBankMap(false);
	additionalBankRuleBean.setRenderOrderNoBankMap(false);
	additionalBankRuleBean.setRenderOrderNoBankMapForApprove(true);
	additionalBankRuleBean.setOrderNoForBankMap(bankRuleMapDataTable.getOrderNo());
	additionalBankRuleBean.setRenderApproveCancelButtonPanelForBankMap(true);
	additionalBankRuleBean.setRenderAddButtonPanelForBankMap(false);
	additionalBankRuleBean.setAddtionalBankRuleMapPk(bankRuleMapDataTable.getAddtionBankRuleMapPK());
	additionalBankRuleBean.setCreatedByForBankRuleMap(bankRuleMapDataTable.getCreatedBy());
	FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankrulemap.xhtml");
	}
	}


public void fetchDataForApproval(){
	addtionBankRuleMapDataTableList.clear();
	List<AdditionalBankRuleMap> addtionaBankRuleMapList=additionalBankRuleAddService.getDataForApprovalForBankRuleMap();
	if(addtionaBankRuleMapList.size()>0){
	for(AdditionalBankRuleMap addtionBankMap:addtionaBankRuleMapList){
		AdditionBankRuleMapApprovalDataTable approvalDataTable=new AdditionBankRuleMapApprovalDataTable();
		approvalDataTable.setAddtionBankRuleMapPK(addtionBankMap.getAdditionalBankRuleId());
		approvalDataTable.setFlexField(addtionBankMap.getFlexField());
		approvalDataTable.setFieldName(addtionBankMap.getFieldName());
		approvalDataTable.setCountryId(addtionBankMap.getCountryId().getCountryId());
		approvalDataTable.setCountryName(generalService.getCountryName(session.getLanguageId() ,addtionBankMap.getCountryId().getCountryId()));
		approvalDataTable.setOrderNo(addtionBankMap.getOrderNo().toString());
		approvalDataTable.setCreatedBy(addtionBankMap.getCreatedBy());
		approvalDataTable.setModifiedBy(addtionBankMap.getModifiedBy());
		addtionBankRuleMapDataTableList.add(approvalDataTable);
	}
	}
	
}




public void navigateToAdditionalBankRuleMapApprovalPage() throws IOException{
	FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleMapApproval.xhtml");
}
}
