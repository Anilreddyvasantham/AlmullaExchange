package com.amg.exchange.treasury.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.treasury.model.CustomerSpecialDealRequest;
import com.amg.exchange.treasury.service.ISpecialCustomerDealRequestApprovService;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

@Component("specialCustomerDealRequestBeandata")
@Scope("session")
public class SpecialCustomerDealRequestApproveBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(SpecialCustomerDealRequestApproveBean.class);
	private SessionStateManage sessionStateManage = new SessionStateManage();
	
	private List<CustomerSpecialDealRequestBean> customerSpecialDealReq = new ArrayList<CustomerSpecialDealRequestBean>();
	private List<CustomerSpecialDealRequest> customerSpecialDealRequest = new ArrayList<CustomerSpecialDealRequest>();
	private List<CustomerSpecialDealRequest> customer = new ArrayList<CustomerSpecialDealRequest>();
	List<BigDecimal> lstApproved = new ArrayList<BigDecimal>();
	private Boolean aproveAll = false;
	private List<CustomerSpecialDealRequestBean> selectedCustomers;

	@Autowired
	ISpecialCustomerDealRequestApprovService<T> CustomerSpecialDealRequest;
	
	public ISpecialCustomerDealRequestApprovService<T> getCustomerSpecialDealRequest() {
		return CustomerSpecialDealRequest;
	}

	public void setCustomerSpecialDealRequest(
			ISpecialCustomerDealRequestApprovService<T> customerSpecialDealRequest) {
		CustomerSpecialDealRequest = customerSpecialDealRequest;
	}
	

	public List<CustomerSpecialDealRequest> getCustomer() {
		return customer;
	}

	public void setCustomer(List<CustomerSpecialDealRequest> customer) {
		this.customer = customer;
	}

	public Boolean getAproveAll() {
		return aproveAll;
	}
	
	

	public List<CustomerSpecialDealRequestBean> getSelectedCustomers() {
		
		return selectedCustomers;
	}

	public void setSelectedCustomers(
			List<CustomerSpecialDealRequestBean> selectedCustomers) {
		this.selectedCustomers = selectedCustomers;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public List<CustomerSpecialDealRequestBean> getCustomerSpecialDealReq() {
		  try{
			  loginLogoutHistoryUtil.saveLoginLogoutDetails(session.getCountryId(), session.getUserType(), session.getUserName(), "specialcustomerdealrequestapproval.xhtml");
		customerSpecialDealReq.clear();

		customerSpecialDealRequest = CustomerSpecialDealRequest.CustomerSpecialDealRequest(sessionStateManage.getCountryId());
		
		for (CustomerSpecialDealRequest da : customerSpecialDealRequest) {

			CustomerSpecialDealRequestBean cusSpl = new CustomerSpecialDealRequestBean();

			if (da.getApprovedBy() == null) {
				cusSpl.setPk(da.getCustomerSpecialDealReqId());
				if(da.getCustomerSpeacialDealReqCountryMaster().getCountryId()!=null){
				cusSpl.setCountryName(da.getCustomerSpeacialDealReqCountryMaster().getCountryId().toString());
				}
				cusSpl.setBankName(da.getCustomerSpeacialDealReqBankMaster().getBankFullName());
				cusSpl.setCurrencyName(da.getCustomerSpeacialDealReqCurrencyMaster().getCurrencyName());
				cusSpl.setFcAmount(da.getForeignCurrencyAmount());
				cusSpl.setRequestNo(da.getDocumentNumber());
				SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
				cusSpl.setValueDate(format.format(da.getValueDate()) );
 
			
				cusSpl.setCountryName(CustomerSpecialDealRequest
						.countryName(da
								.getCustomerSpeacialDealReqCountryMaster()
								.getCountryId(), session.getLanguageId()));

				customer = CustomerSpecialDealRequest.getCustName(da
						.getCustomerSpeacialDealReqCustomer());

				cusSpl.setCustName(customer.get(0)
						.getCustomerSpeacialDealReqCustomer().getFirstName());

				try {
					cusSpl.setProjectDate(new SimpleDateFormat("dd/MM/yyyy")
							.format(da.getProjectionDate()).toString());
					cusSpl.setRequestYear(new SimpleDateFormat("yyyy").format(
							da.getCreatedDate()).toString());
				} catch(Exception exception){
					    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
					    setErrorMessage(exception.getMessage()); 
					    }
				customerSpecialDealReq.add(cusSpl);
			}
		}
		  }catch(NullPointerException ne){
			    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("MethodName::saveDataTableRecods");
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    }
	  return customerSpecialDealReq;
	}

	public void setCustomerSpecialDealReq(
			List<CustomerSpecialDealRequestBean> customerSpecialDealReq) {
		this.customerSpecialDealReq = customerSpecialDealReq;
	}



	public void save(){
		  try{
		for (CustomerSpecialDealRequestBean element : selectedCustomers) {
			if (!(element.getRequestNo()==null)) {
				lstApproved.add(element.getPk());
				
			}
		}
		if (lstApproved.size() >= 1) {
			CustomerSpecialDealRequest.updateApprove(lstApproved, sessionStateManage.getUserName());
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("approved.show();");
			lstApproved.clear();
		} else {
			RequestContext context = RequestContext.getCurrentInstance();
			context.execute("notapproved.show();");
		}
		  }catch(NullPointerException ne){
			    log.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
			    setErrorMessage("MethodName::save");
			    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
			    return; 
			    }catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return;       
			    }
	}

	public void refresh() {
		customerSpecialDealReq.clear();
		try {

			FacesContext
					.getCurrentInstance()
					.getExternalContext()
					.redirect(
							"../treasury/specialcustomerdealrequestapproval.xhtml");

		} catch(Exception exception){
			    log.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
			    setErrorMessage(exception.getMessage()); 
			    RequestContext.getCurrentInstance().execute("error.show();");
			    return; 
		}
	}
	
	
	SessionStateManage session=new SessionStateManage();

	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
}
