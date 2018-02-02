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
import com.amg.exchange.remittance.model.AmiecAndBankMapping;
import com.amg.exchange.remittance.service.IAmiecAndBankMappingService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/*
 * Author RAHAMATHALI SHAIK
 */
@SuppressWarnings("rawtypes")
@Component("amiecAndBankMappingApprovalBean")
@Scope("session")
public class AMIECAndBankMappingApprovalBean<T> {

	@Autowired
	IAmiecAndBankMappingService amiecAndBankMappingService;

	@Autowired
	IGeneralService generalService;

	@Autowired
	AdditionalBankMappingBean additionalBankMappingBean;

	private List<AMIECAndBankMappingApprovalDataTable> amiecBankdataTableList = new ArrayList<AMIECAndBankMappingApprovalDataTable>();

	SessionStateManage session = new SessionStateManage();

	public List<AMIECAndBankMappingApprovalDataTable> getAmiecBankdataTableList() {
		fetchDataForApproval();
		return amiecBankdataTableList;
	}

	public void setAmiecBankdataTableList(
			List<AMIECAndBankMappingApprovalDataTable> amiecBankdataTableList) {
		this.amiecBankdataTableList = amiecBankdataTableList;
	}

	public void fetchDataForApproval() {
		amiecBankdataTableList.clear();
		List<AmiecAndBankMapping> amiecAndBankMappingList = amiecAndBankMappingService.getDataForApproval();
		if (amiecAndBankMappingList.size() > 0) {
			for (AmiecAndBankMapping amiecAndBankMapping : amiecAndBankMappingList) {
				AMIECAndBankMappingApprovalDataTable approvalDataTable = new AMIECAndBankMappingApprovalDataTable();

				approvalDataTable.setAmiecAndBankMappingPK(amiecAndBankMapping.getAmiecAndBankMappingId());
				approvalDataTable.setFlexField(amiecAndBankMapping.getFlexField());
				approvalDataTable.setFieldName(amiecAndBankMappingService.getFlexFieldName(amiecAndBankMapping.getFlexField()));
				approvalDataTable.setCountryId(amiecAndBankMapping.getCountryId().getCountryId());
				approvalDataTable.setCountryName(generalService.getCountryName(session.getLanguageId(), amiecAndBankMapping.getCountryId().getCountryId()));
				approvalDataTable.setBankId(amiecAndBankMapping.getBankId().getBankId());
				approvalDataTable.setBankName(generalService.getBankName(amiecAndBankMapping.getBankId().getBankId()));
				approvalDataTable.setBankCode(amiecAndBankMapping.getBankCode());
				approvalDataTable.setAmiecCode(amiecAndBankMapping.getAmiecCode());
				approvalDataTable.setAmiecDescription(amiecAndBankMapping.getAmiecDescription());
				approvalDataTable.setBankDecription(amiecAndBankMapping.getBankDecription());
				approvalDataTable.setCreatedBy(amiecAndBankMapping.getCreatedBy());
				approvalDataTable.setModifiedBy(amiecAndBankMapping.getModifiedBy());
				amiecBankdataTableList.add(approvalDataTable);
			}
		}

	}

	public void approveAMIECBank(AMIECAndBankMappingApprovalDataTable approvalDataTable)
			throws IOException {
		// for populating all fileds in AMIEC AND BANK MAPPING
		if((approvalDataTable.getModifiedBy()==null ? approvalDataTable.getCreatedBy() : approvalDataTable.getModifiedBy()).equalsIgnoreCase(session.getUserName())){
			RequestContext.getCurrentInstance().execute("notApproved.show();");
		} else {
			additionalBankMappingBean.setCountryId(approvalDataTable.getCountryId());
			additionalBankMappingBean.setCountryName(approvalDataTable.getCountryName());
			additionalBankMappingBean.setBankId(approvalDataTable.getBankId());
			additionalBankMappingBean.setBankName(approvalDataTable.getBankName());
			additionalBankMappingBean.setFlexField(approvalDataTable.getFlexField());
			additionalBankMappingBean.setFieldName(approvalDataTable.getFieldName());
			additionalBankMappingBean.setAmiecCode(approvalDataTable.getAmiecCode());
			additionalBankMappingBean.setAmiecDescription(approvalDataTable.getAmiecDescription());
			additionalBankMappingBean.setBankCode(approvalDataTable.getBankCode());
			additionalBankMappingBean.setBankDescription(approvalDataTable.getBankDecription());

			// hide panel for approving
			additionalBankMappingBean.setRenderAddViewButtonPanel(false);
			additionalBankMappingBean.setRenderApproveCancelButtonPanel(true);
			additionalBankMappingBean.setDataTableRendered(false);
			additionalBankMappingBean.setSavePanelRender(false);
			additionalBankMappingBean.setAmiecBankMapPK(approvalDataTable.getAmiecAndBankMappingPK());
			additionalBankMappingBean.setCreatedByForApprove(approvalDataTable.getCreatedBy());
			additionalBankMappingBean.setRenderCountryId(false);
			additionalBankMappingBean.setRenderBankId(false);
			additionalBankMappingBean.setRenderFlexField(false);
			additionalBankMappingBean.setRenderAlmullaCode(false);
			additionalBankMappingBean.setRenderBankCode(false);
			additionalBankMappingBean.setRenderBankIdForApprove(true);
			additionalBankMappingBean.setRenderCountryIdForApprove(true);
			additionalBankMappingBean.setRenderFlexFieldForApprove(true);
			additionalBankMappingBean.setRenderAlmullaCodeForApprove(true);
			additionalBankMappingBean.setRenderBankCodeForApprove(true);

			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/amiecandbankmapping.xhtml");
		}
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void navigateToAMIECAndBankMappingApprovalPage() throws IOException {
		loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "AMIECAndBankMappingApproval.xhtml");
		FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/AMIECAndBankMappingApproval.xhtml");
	}

}
