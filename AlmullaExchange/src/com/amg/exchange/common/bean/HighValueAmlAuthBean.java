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
import com.amg.exchange.common.model.HighValueAMLAuthViewLocal;
import com.amg.exchange.common.service.IComponentTypeService;
import com.amg.exchange.enquiry.bean.RemittanceInquiryBean;
import com.amg.exchange.stoppayment.service.IStopPaymentService;
import com.amg.exchange.util.AMGException;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;


@Component("highValueAmlAuthBean")
@Scope("session")
public class HighValueAmlAuthBean<T> {

	private List<HighValueAMLAuthViewLocal>  highvalueAmlList ;
	private List<HighValueAMLAuthViewLocal> highvalueAmlSelectedList;
	private String remarks;
	private String errorMessage;
	SessionStateManage session =new SessionStateManage();

	@Autowired
	IComponentTypeService<T> componentTypeService;
	@Autowired
	RemittanceInquiryBean remittanceInquiryBean;
	@Autowired
	IStopPaymentService<T> stopPaymentService;

	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	
	public void gotoHighValueAmlAuthPage(){
		try {
			clear();
			highvalueAmlList = new ArrayList<HighValueAMLAuthViewLocal>();
			highvalueAmlSelectedList = new ArrayList<HighValueAMLAuthViewLocal>();
			List<HighValueAMLAuthViewLocal> highvalueAmlTempList = componentTypeService.getHighValueAmlAuthRecords();
			if(highvalueAmlTempList!=null && highvalueAmlTempList.size()>0){
				setHighvalueAmlList(highvalueAmlTempList);
			} 
			loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "highValuAmlAuth.xhtml");
			//return "/common/highValuAmlAuth.xhtml?faces-redirect=true"; 

			FacesContext.getCurrentInstance().getExternalContext().redirect("../common/highValuAmlAuth.xhtml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void updateSelectedRecords(){
		try{
			if(getRemarks()!=null && !getRemarks().isEmpty()){
				if(highvalueAmlSelectedList!=null && highvalueAmlSelectedList.size()>0){
					Boolean resultType = componentTypeService.saveHighValueAmlAuth(highvalueAmlSelectedList, session.getUserName(), getRemarks());
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

	// clear
	public void clear(){
		setRemarks(null);
		setHighvalueAmlList(null);
		setErrorMessage(null);
		setHighvalueAmlSelectedList(null);
	}

	// exit
	public void exit() throws IOException{
		if(session.getRoleId().equalsIgnoreCase("1")){
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		}else{
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}

	// Inquiry
	public void gotoCustonmerInquiry(HighValueAMLAuthViewLocal customerData){

		try{
			ViewRemiitanceInfo viewRemittanceInfo=stopPaymentService.getRemittanceTrnxDetailsFromView(customerData.getDocumentNo(), customerData.getDocumentFinanceYear() , Constants.DOCUMENT_CODE_FOR_CANCEL_REISSUE, session.getCompanyId());
			if(viewRemittanceInfo!=null){
				remittanceInquiryBean.clearAll();
				remittanceInquiryBean.setRenderPanel(false);
				remittanceInquiryBean.setBackButtonRender(false);
				remittanceInquiryBean.setExitButtonRender(false);
				remittanceInquiryBean.setRenderForRemittanceBranchWiseEnquiry(false);
				remittanceInquiryBean.setRenderForHIGHVALUETrnx(true);
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

	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public List<HighValueAMLAuthViewLocal> getHighvalueAmlList() {
		return highvalueAmlList;
	}

	public void setHighvalueAmlList(List<HighValueAMLAuthViewLocal> highvalueAmlList) {
		this.highvalueAmlList = highvalueAmlList;
	}

	public List<HighValueAMLAuthViewLocal> getHighvalueAmlSelectedList() {
		return highvalueAmlSelectedList;
	}

	public void setHighvalueAmlSelectedList(List<HighValueAMLAuthViewLocal> highvalueAmlSelectedList) {
		this.highvalueAmlSelectedList = highvalueAmlSelectedList;
	}


}
