/**
 * 
 */
package com.amg.exchange.remittance.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.registration.model.CountryBranch;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.stoppayment.model.RemittanceTransaction;
import com.amg.exchange.stoppayment.service.IStopPaymentCollectionService;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.LoginLogoutHistoryUtil;
import com.amg.exchange.util.SessionStateManage;

/**
 * @author Subramaniam
 * 
 */
@Component("highValueCustomerBean")
@Scope("session")
public class HighValueCustomerBean<T> implements Serializable {

	private static final Logger LOGGER = Logger.getLogger(HighValueCustomerBean.class);
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private BigDecimal countryBranch;
	private Boolean booHighValueCustomer = false;
	private Boolean booHighValueCustomerDataTable = false;

	private Boolean selectedrecord = false;
	
	SessionStateManage sessionStateManage = new SessionStateManage();
	
	@Autowired
	IStopPaymentCollectionService iStopPaymentCollectionService;
	
	List<CountryBranch> lstofCountry = new ArrayList<CountryBranch>();
	List<RemittanceTransaction> lstofCustomer = new ArrayList<RemittanceTransaction>();
	
	List<HighValueCustomerDataTable> lstofHighValueCustomer = new ArrayList<HighValueCustomerDataTable>();

	public HighValueCustomerBean() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getCountryBranch() {
		return countryBranch;
	}

	public void setCountryBranch(BigDecimal countryBranch) {
		this.countryBranch = countryBranch;
	}

	public Boolean getBooHighValueCustomer() {
		return booHighValueCustomer;
	}

	public void setBooHighValueCustomer(Boolean booHighValueCustomer) {
		this.booHighValueCustomer = booHighValueCustomer;
	}

	public Boolean getBooHighValueCustomerDataTable() {
		return booHighValueCustomerDataTable;
	}

	public void setBooHighValueCustomerDataTable(Boolean booHighValueCustomerDataTable) {
		this.booHighValueCustomerDataTable = booHighValueCustomerDataTable;
	}
	
	

	public IStopPaymentCollectionService getiStopPaymentCollectionService() {
		return iStopPaymentCollectionService;
	}

	public void setiStopPaymentCollectionService(IStopPaymentCollectionService iStopPaymentCollectionService) {
		this.iStopPaymentCollectionService = iStopPaymentCollectionService;
	}
	
	

	public List<CountryBranch> getLstofCountry() {
		  try{
		lstofCountry = getiStopPaymentCollectionService().getRemittanceTransaction(sessionStateManage.getCountryId());
		  }catch(Exception exception){
		    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		  }
		
			return lstofCountry;
	}

	public void setLstofCountry(List<CountryBranch> lstofCountry) {
		this.lstofCountry = lstofCountry;
	}
	
	

	public Boolean getSelectedrecord() {
		return selectedrecord;
	}

	public void setSelectedrecord(Boolean selectedrecord) {
		this.selectedrecord = selectedrecord;
	}

	public List<HighValueCustomerDataTable> getLstofHighValueCustomer() {
		return lstofHighValueCustomer;
	}

	public void setLstofHighValueCustomer(List<HighValueCustomerDataTable> lstofHighValueCustomer) {
		this.lstofHighValueCustomer = lstofHighValueCustomer;
	}

	public List<RemittanceTransaction> getLstofCustomer() {
		return lstofCustomer;
	}

	public void setLstofCustomer(List<RemittanceTransaction> lstofCustomer) {
		this.lstofCustomer = lstofCustomer;
	}
	@Autowired
	LoginLogoutHistoryUtil<T> loginLogoutHistoryUtil;
	public void pageNavigation() {

		try {
			loginLogoutHistoryUtil.saveLoginLogoutDetails(sessionStateManage.getCountryId(), sessionStateManage.getUserType(), sessionStateManage.getUserName(), "highvaluecustomerrelease.xhtml");
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecustomerrelease.xhtml");

			setBooHighValueCustomer(true);
			setBooHighValueCustomerDataTable(false);
			setCountryBranch(null);
			lstofHighValueCustomer.clear();

		} catch(Exception exception){
		    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;
		  }

	}

	public void getCustomerList() {
		  try{
		lstofHighValueCustomer.clear();
		setBooHighValueCustomer(true);
		setBooHighValueCustomerDataTable(true);

				
		lstofCustomer = getiStopPaymentCollectionService().getHighValueCusotmerList(getCountryBranch(),Constants.Yes);
		
		if(lstofCustomer !=null){
			
			for (RemittanceTransaction remittanceTransaction : lstofCustomer) {
				
				HighValueCustomerDataTable  HighValueCustomer = new HighValueCustomerDataTable();
				System.out.println("highValueCustomerDataTable.getRemittanceTransactionId() === > "+remittanceTransaction.getRemittanceTransactionId());
				HighValueCustomer.setRemittanceTransactionId(remittanceTransaction.getRemittanceTransactionId());
				HighValueCustomer.setCustomerRef(remittanceTransaction.getCustomerRef());
				HighValueCustomer.setDocumentFinanceYear(remittanceTransaction.getDocumentFinanceYear());
				HighValueCustomer.setDocumentNo(remittanceTransaction.getDocumentNo());
				HighValueCustomer.setApplicationFinanceYear(remittanceTransaction.getApplicationFinanceYear());
				HighValueCustomer.setCreatedBy(remittanceTransaction.getCreatedBy());
				HighValueCustomer.setCreatedDate(remittanceTransaction.getCreatedDate());
				HighValueCustomer.setDebitAccountNo(remittanceTransaction.getDebitAccountNo());
				HighValueCustomer.setLocalChargeAmount(remittanceTransaction.getLocalChargeAmount());
				HighValueCustomer.setLocalCommisionAmount(remittanceTransaction.getLocalCommisionAmount());
				
				HighValueCustomer.setLocalTranxAmount(remittanceTransaction.getLocalTranxAmount());
				HighValueCustomer.setLocalNetTranxAmount(remittanceTransaction.getLocalNetTranxAmount());
				
				lstofHighValueCustomer.add(HighValueCustomer);
				
				
			}

		}else{
			RequestContext.getCurrentInstance().execute("empty.show()");
			setBooHighValueCustomerDataTable(false);
			
		}
		
		  }catch(NullPointerException ne){
		    LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		    setErrorMessage("MethodName::getCustomerList");
		    RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		    return; 
		    }catch(Exception exception){
		    LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		    setErrorMessage(exception.getMessage()); 
		    RequestContext.getCurrentInstance().execute("error.show();");
		    return;       
		    }
	

	}
	List<HighValueCustomerDataTable> selectChecks  ;
	List<BigDecimal> lstSelected = new ArrayList<BigDecimal>();
	
	
	public List<HighValueCustomerDataTable> getSelectChecks() {
		return selectChecks;
	}

	public void setSelectChecks(List<HighValueCustomerDataTable> selectChecks) {
		this.selectChecks = selectChecks;
	}
	
	public void update(){
		try{
		System.out.println("selectChecks === > "+selectChecks);
		
		for(HighValueCustomerDataTable highValueCustomerDataTable:selectChecks){
			
			
			if(selectChecks !=null){
				lstSelected.add(highValueCustomerDataTable.getRemittanceTransactionId());
			}
			
		}
		
		if(lstSelected.size()>0){
			getiStopPaymentCollectionService().updateRemittanceTransaction(lstSelected,sessionStateManage.getUserName());
			
			RequestContext.getCurrentInstance().execute("complete.show()");
			
		}
		}catch(NullPointerException ne){
		  LOGGER.info("ne.getMessage():::::::::::::::::::::::::::::::"+ne.getMessage());
		  setErrorMessage("MethodName::saveDataTableRecods");
		  RequestContext.getCurrentInstance().execute("nullPointerId.show();");
		  return; 
		  }catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
		
		
	}
	
	

	public void exit(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecustomerrelease.xhtml");

			setBooHighValueCustomer(true);
			setBooHighValueCustomerDataTable(false);
			setCountryBranch(null);
			lstofHighValueCustomer.clear();

		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	
	public void clickOk(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecustomerrelease.xhtml");
			getCustomerList();
			setBooHighValueCustomer(true);
			setBooHighValueCustomerDataTable(false);
			setCountryBranch(null);
			
		} catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	public void successonOk(){
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/highvaluecustomerrelease.xhtml");
			getCustomerList();
			setBooHighValueCustomer(true);
			setBooHighValueCustomerDataTable(true);
			setCountryBranch(null);
			
		}catch(Exception exception){
		  LOGGER.info("exception.getMessage():::::::::::::::::::::::::::::::"+exception.getMessage());
		  setErrorMessage(exception.getMessage()); 
		  RequestContext.getCurrentInstance().execute("error.show();");
		  return;       
		  }
	}
	
	  private String errorMessage;

	  public String getErrorMessage() {
		    return errorMessage;
	  }

	  public void setErrorMessage(String errorMessage) {
		    this.errorMessage = errorMessage;
	  }
	
	
}
