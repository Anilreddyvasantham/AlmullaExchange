package com.amg.exchange.common.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.cancelreissue.model.ViewRemiitanceInfo;
import com.amg.exchange.common.model.HighValueAMLAuthViewFC;
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.common.service.IComponentTypeService;
import com.amg.exchange.enquiry.bean.RemittanceInquiryBean;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("highValueAmlAuthFCurrencyBean")
@Scope("session")
public class HighValueAmlAuthFCurrencyBean<T> {

	@Autowired
	IComponentTypeService<T> componentTypeService;
	@Autowired
	RemittanceInquiryBean remittanceInquiryBean;
	@Autowired
	IStopPaymentService<T> stopPaymentService;

	private String remarks;
	private String errorMessage;
	SessionStateManage session =new SessionStateManage();

	private List<HighValueAMLAuthViewFC>  highvalueAmlFcList ;
	private List<HighValueAMLAuthViewFC> highvalueAmlFcSelectedList;

	// clear
	public void clear(){
		setRemarks(null);
		setHighvalueAmlFcList(null);
		setErrorMessage(null);
		setHighvalueAmlFcSelectedList(null);
	}


	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;





	// Inquiry
	public void gotoCustonmerInquiry(HighValueAMLAuthViewFC customerData){

		try{
			ViewRemiitanceInfo viewRemittanceInfo=stopPaymentService.getRemittanceTrnxDetailsFromView(customerData.getDocumentNo(), customerData.getDocumentFinanceYear() , Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE, session.getCompanyId());
			if(viewRemittanceInfo!=null){
				remittanceInquiryBean.clearAll();
				remittanceInquiryBean.setRenderPanel(false);
				remittanceInquiryBean.setBackButtonRender(false);
				remittanceInquiryBean.setExitButtonRender(false);
				remittanceInquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
				remittanceInquiryBean.setRenderForHIGHVALUETrnx(false);
				remittanceInquiryBean.setRenderForHIGHVALUETrnxForFC(true);
				remittanceInquiryBean.setDocNumber(customerData.getDocumentNo());
				remittanceInquiryBean.setDocYear(customerData.getDocumentFinanceYear() );
				remittanceInquiryBean.fetchData();
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("../enquiry/remittanceinquiry.xhtml");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				RequestContext.getCurrentInstance().execute("noDatafound.show();");
				return;
			}
		}catch(Exception e){
			setErrorMessage(e.getMessage());
			RequestContext.getCurrentInstance().execute("showDailog.show();");
		}
	}


	public void updateSelectedRecords(){
		try{
			if(getRemarks()!=null && !getRemarks().isEmpty()){
				if(highvalueAmlFcSelectedList!=null && highvalueAmlFcSelectedList.size()>0){
					Boolean resultType = componentTypeService.saveHighValueAmlAuthForFC(highvalueAmlFcSelectedList, session.getUserName(), getRemarks());
					if(resultType){
						RequestContext.getCurrentInstance().execute("complete.show();");
					}
				}else{
					setErrorMessage("Please select atleast One Record");
					RequestContext.getCurrentInstance().execute("showDailog.show();");
				}
			} else{
				setErrorMessage("Remarks mandatory");
				RequestContext.getCurrentInstance().execute("showDailog.show();");
			}
		}catch(AMGException ex){
			setErrorMessage("Exception While Saving :" +ex.getMessage());
			RequestContext.getCurrentInstance().execute("showDailog.show();");
		}
	}

	public void gotoHighValueAmlAuthFCurrencyPage(){
		try {
			clear();
			highvalueAmlFcList = new ArrayList<HighValueAMLAuthViewFC>();
			highvalueAmlFcSelectedList = new ArrayList<HighValueAMLAuthViewFC>();
			List<HighValueAMLAuthViewFC> highvalueAmlTempList = componentTypeService.getHighValueAmlAuthRecordsForFC();
			if(highvalueAmlTempList!=null && highvalueAmlTempList.size()>0){
				setHighvalueAmlFcList(highvalueAmlTempList);
			} 
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "highValueAmlAuthFCurreny.xhtml");
			//return "/common/highValueAmlAuthFCurreny.xhtml?faces-redirect=true"; 
			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/highValueAmlAuthFCurreny.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// exit
	public void exit() throws IOException{
		if(session.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<HighValueAMLAuthViewFC> getHighvalueAmlFcList() {
		return highvalueAmlFcList;
	}

	public void setHighvalueAmlFcList(List<HighValueAMLAuthViewFC> highvalueAmlFcList) {
		this.highvalueAmlFcList = highvalueAmlFcList;
	}

	public List<HighValueAMLAuthViewFC> getHighvalueAmlFcSelectedList() {
		return highvalueAmlFcSelectedList;
	}

	public void setHighvalueAmlFcSelectedList(
			List<HighValueAMLAuthViewFC> highvalueAmlFcSelectedList) {
		this.highvalueAmlFcSelectedList = highvalueAmlFcSelectedList;
	}







}
