package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.faces.context.FacesContext;


import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.remittance.model.AdditionalBankRuleAmiec;
import com.amg.exchange.remittance.model.AdditionalBankRuleMap;
import com.amg.exchange.remittance.service.IAdditionalBankRuleAddService;
import com.amg.exchange.remittance.service.IAdditionalBankRuleMapService;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


/*
 * Author RAHAMATHALI SHAIK
 */
@Component("additionBankRuleWithAlMullaCodeApprovalBean")
@Scope("session")
public class AdditionBankRuleWithAlMullaCodeApprovalBean<T> {

	@Autowired
	IGeneralService generalService;

	@Autowired
	IAdditionalBankRuleAddService additionalBankRuleAddService;

	@Autowired
	AdditionalBankRuleBean additionalBankRuleBean;
	
	@Autowired
	IAdditionalBankRuleMapService additionalBankRuleMapService;

	@Autowired
	IAmiecAndBankMappingService amiecAndBankMappingService;

	SessionStateManage session=new SessionStateManage();

	private List<AdditionBankRuleWithAlMullaCodeApprovalDataTable> addtionalBankAllmulladataTableList=new ArrayList<AdditionBankRuleWithAlMullaCodeApprovalDataTable>();


	public List<AdditionBankRuleWithAlMullaCodeApprovalDataTable> getAddtionalBankAllmulladataTableList() {
		return addtionalBankAllmulladataTableList;
	}

	public void setAddtionalBankAllmulladataTableList(
			List<AdditionBankRuleWithAlMullaCodeApprovalDataTable> addtionalBankAllmulladataTableList) {
		this.addtionalBankAllmulladataTableList = addtionalBankAllmulladataTableList;
	}

    //	view records
	public void fetchDataForApproval(){
		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}
		if(addtionalBankAllmulladataTableList != null && !addtionalBankAllmulladataTableList.isEmpty()){
			addtionalBankAllmulladataTableList.clear();
		}
		setSelectAll(false);
		setRenderDataTableButtonsAlmulla(false);
		List<AdditionalBankRuleAmiec> amiecAndBankMappingList=additionalBankRuleAddService.getDataForApprovalForAmMulla(countryId, flexField);
		if(amiecAndBankMappingList.size()>0){
			setRenderDataTableButtonsAlmulla(true);
			for(AdditionalBankRuleAmiec amiecAndBankMapping:amiecAndBankMappingList){
				AdditionBankRuleWithAlMullaCodeApprovalDataTable approvalDataTable=new AdditionBankRuleWithAlMullaCodeApprovalDataTable();

				approvalDataTable.setAddtionAlmullaBankPK(amiecAndBankMapping.getAdditionalBankRuleDetailId());
				approvalDataTable.setFlexField(amiecAndBankMapping.getFlexField());
				approvalDataTable.setFieldName(amiecAndBankMappingService.getFlexFieldName(amiecAndBankMapping.getFlexField()));
				approvalDataTable.setCountryId(amiecAndBankMapping.getCountryId().getCountryId());
				approvalDataTable.setCountryName(generalService.getCountryName(session.getLanguageId() ,amiecAndBankMapping.getCountryId().getCountryId()));
				approvalDataTable.setAlmullaDescription(amiecAndBankMapping.getAmiecDescription());
				approvalDataTable.setAlmullaCode(amiecAndBankMapping.getAmiecCode());
				approvalDataTable.setCreatedBy(amiecAndBankMapping.getCreatedBy());
				approvalDataTable.setModifiedBy(amiecAndBankMapping.getModifiedBy());
				approvalDataTable.setIsCheck(false);
				approvalDataTable.setUserName(approvalDataTable.getModifiedBy()==null?approvalDataTable.getCreatedBy() : approvalDataTable.getModifiedBy());
				if(approvalDataTable.getModifiedBy()==null?approvalDataTable.getCreatedBy().equalsIgnoreCase(session.getUserName()) : approvalDataTable.getModifiedBy().equalsIgnoreCase(session.getUserName())){
					approvalDataTable.setDisableCheck(true);
				}else{
					approvalDataTable.setDisableCheck(false);
				}
				
				addtionalBankAllmulladataTableList.add(approvalDataTable);
			}
		}else{
			setRenderDataTableButtonsAlmulla(false);
			RequestContext.getCurrentInstance().execute("norecord.show();");
		}

	}


	public void approveAlmullaAddtionBank(AdditionBankRuleWithAlMullaCodeApprovalDataTable addBankAlmullaDataTable) throws IOException{
		if((addBankAlmullaDataTable.getModifiedBy()==null ? addBankAlmullaDataTable.getCreatedBy() : addBankAlmullaDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		}else{
			additionalBankRuleBean.setRenderCountryIdAlmulla(false);
			additionalBankRuleBean.setRenderCountryIdForApproveAlmulla(true);
			additionalBankRuleBean.setCountryNameAlmulla(addBankAlmullaDataTable.getCountryName());
			additionalBankRuleBean.setRenderFlexFieldForApproveAlmulla(true);
			additionalBankRuleBean.setRenderFlexFieldAlmulla(false);
			additionalBankRuleBean.setFlexFieldNameAlmulla(addBankAlmullaDataTable.getFieldName());
			additionalBankRuleBean.setAlmullaCodeAlmulla(addBankAlmullaDataTable.getAlmullaCode());
			additionalBankRuleBean.setRenderAlmullaCodeForApproveAlmulla(true);
			additionalBankRuleBean.setRenderAlmullaCodeAlmulla(false);
			additionalBankRuleBean.setAlmullaDescAlmulla(addBankAlmullaDataTable.getAlmullaDescription());
			additionalBankRuleBean.setRenderAlmullaDescAlmulla(false);
			additionalBankRuleBean.setRenderAlmullaDescForApproveAlmulla(true);
			additionalBankRuleBean.setCreatedByAlmulla(addBankAlmullaDataTable.getCreatedBy());
			additionalBankRuleBean.setAlmullaCodePK(addBankAlmullaDataTable.getAddtionAlmullaBankPK());

			//for Button Panel off/On 
			additionalBankRuleBean.setRenderApproveCancelButtonPanel(false);
			additionalBankRuleBean.setRenderAddButtonPanel(false);
			additionalBankRuleBean.setRenderApproveCancelButtonPanelAlmulla(true);
			additionalBankRuleBean.setAdditionalBankRuleAmiecRendered(false);
			additionalBankRuleBean.setSaveAdditionalBankRule2(false);


			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/additionalbankruleamiecmap.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToAdditionBankRuleWithAlmullaCodeApprovalPage() throws IOException{
		if(lstApproved != null && !lstApproved.isEmpty()){
			lstApproved.clear();
		}
		if(addtionalBankAllmulladataTableList != null && !addtionalBankAllmulladataTableList.isEmpty()){
			addtionalBankAllmulladataTableList.clear();
		}
		setCountryId(null);
		setFlexField(null);
		setSelectAll(false);
		if(additionalBankRuleMaps != null && !additionalBankRuleMaps.isEmpty()){
			additionalBankRuleMaps.clear();
		}
		setRenderDataTableButtonsAlmulla(false);
		getCountryNameList();
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "AdditionBankRuleWithAlMullaCodeApproval.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AdditionBankRuleWithAlMullaCodeApproval.xhtml");
	}

	// variables
	private BigDecimal countryId;
	private String flexField;
	private Boolean renderDataTableButtonsAlmulla = false;
	Boolean selectAll = false;

	private List<CountryMasterDesc> countryList = new ArrayList<CountryMasterDesc>();
	private List<AdditionalBankRuleMap> additionalBankRuleMaps = new ArrayList<AdditionalBankRuleMap>();
	private List<BigDecimal> lstApproved = new CopyOnWriteArrayList<BigDecimal>();
	
	
	public BigDecimal getCountryId() {
		return countryId;
	}
	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}

	public String getFlexField() {
		return flexField;
	}
	public void setFlexField(String flexField) {
		this.flexField = flexField;
	}
	
	public Boolean getRenderDataTableButtonsAlmulla() {
		return renderDataTableButtonsAlmulla;
	}
	public void setRenderDataTableButtonsAlmulla(Boolean renderDataTableButtonsAlmulla) {
		this.renderDataTableButtonsAlmulla = renderDataTableButtonsAlmulla;
	}

	public Boolean getSelectAll() {
		return selectAll;
	}
	public void setSelectAll(Boolean selectAll) {
		this.selectAll = selectAll;
	}

	public List<CountryMasterDesc> getCountryList() {
		return countryList;
	}
	public void setCountryList(List<CountryMasterDesc> countryList) {
		this.countryList = countryList;
	}

	public List<AdditionalBankRuleMap> getAdditionalBankRuleMaps() {
		return additionalBankRuleMaps;
	}
	public void setAdditionalBankRuleMaps(List<AdditionalBankRuleMap> additionalBankRuleMaps) {
		this.additionalBankRuleMaps = additionalBankRuleMaps;
	}

	public List<BigDecimal> getLstApproved() {
		return lstApproved;
	}
	public void setLstApproved(List<BigDecimal> lstApproved) {
		this.lstApproved = lstApproved;
	}

	// To get All Country List from CountryMasterDesc
	public void getCountryNameList() {
		if(countryList != null && !countryList.isEmpty()){
			countryList.clear();
		}
		List<CountryMasterDesc> countryListdet = generalService.getCountryList(session.getLanguageId());
		if(countryListdet.size()!=0){
			countryList.addAll(countryListdet);
		}
	}
	
	// Populate Flex_Field
	public void popFlex() {
		setFlexField(null);
		if(additionalBankRuleMaps != null && !additionalBankRuleMaps.isEmpty()){
			additionalBankRuleMaps.clear();
		}
		List<AdditionalBankRuleMap> additionalBankRule = additionalBankRuleMapService.getAdditionalFlexField(getCountryId());
		if (additionalBankRule.size() != 0) {
			additionalBankRuleMaps.addAll(additionalBankRule);
		} else {
			setFlexField(null);
		}
	}
	
	public void selecatAndDeselectAll(){
		if(selectAll){
			for(AdditionBankRuleWithAlMullaCodeApprovalDataTable serviceAppApproval : addtionalBankAllmulladataTableList){
				if(!serviceAppApproval.getDisableCheck()){
					serviceAppApproval.setIsCheck(true);
					lstApproved.add(serviceAppApproval.getAddtionAlmullaBankPK());
				}
			}
		}else{
			for(AdditionBankRuleWithAlMullaCodeApprovalDataTable serviceAppApproval : addtionalBankAllmulladataTableList){
				serviceAppApproval.setIsCheck(false);
			}
			if(lstApproved != null && !lstApproved.isEmpty()){
				lstApproved.clear();
			}
		}
	}
	
	public void addingServiceAppApprovalRecord(AdditionBankRuleWithAlMullaCodeApprovalDataTable additionBankRuleWithAlMullaCodeApprovalDataTable){

		if(additionBankRuleWithAlMullaCodeApprovalDataTable.getIsCheck()){
				lstApproved.add(additionBankRuleWithAlMullaCodeApprovalDataTable.getAddtionAlmullaBankPK());
		}else{
			for (BigDecimal selectedRec : lstApproved) {
				if(selectedRec.compareTo(additionBankRuleWithAlMullaCodeApprovalDataTable.getAddtionAlmullaBankPK())==0){
					lstApproved.remove(selectedRec);
				}
			}
		}
	}
	
	public void approvalAllRecords(){
		if(addtionalBankAllmulladataTableList.size() != 0){
			String list = "Fail";
			if(lstApproved.size() != 0){
				list=additionalBankRuleAddService.approveRecord(lstApproved,session.getUserName());
				if(list.equalsIgnoreCase("Success")){
					RequestContext.getCurrentInstance().execute("approvedsucc.show();");
					if(lstApproved != null && !lstApproved.isEmpty()){
						lstApproved.clear();
					}
					return;
				}else{
					RequestContext.getCurrentInstance().execute("alreadyapprov.show();");
					return;
				}
			}

		}
	}


}
