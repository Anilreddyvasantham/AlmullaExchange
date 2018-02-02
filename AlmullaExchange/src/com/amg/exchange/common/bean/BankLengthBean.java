package com.amg.exchange.common.bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.service.IBankLengthService;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.treasury.model.BankAccountLength;
import com.amg.exchange.treasury.model.BankMaster;
import com.amg.exchange.util.Constants;
import com.amg.exchange.util.SessionStateManage;


@Component("bankLengthBean")
@Scope("session")
public class BankLengthBean {
	private BigDecimal bankId;
	private BigDecimal bankLengthId;
	private List<BankMaster> bankList=new ArrayList<BankMaster>();
	private SessionStateManage session=new SessionStateManage();
	
	
	@Autowired
	IGeneralService generalService;
	@Autowired
	IBankLengthService bankLengthService;
	
	
	
	public List<BankMaster> getBankList() {
		return generalService.getAllBankListFromBankMaster();
	}



	public void setBankList(List<BankMaster> bankList) {
		this.bankList = bankList;
	}



	public BigDecimal getBankId() {
		return bankId;
	}



	public void setBankId(BigDecimal bankId) {
		this.bankId = bankId;
	}




	public BigDecimal getBankLengthId() {
		return bankLengthId;
	}



	public void setBankLengthId(BigDecimal bankLengthId) {
		this.bankLengthId = bankLengthId;
	}



	public void clearAllFields(){
		setBankId(null);
		setBankLengthId(null);
	
	}
	public void saveBankLengthRecord(){
		
		List<BankAccountLength> lstBnkLngth=bankLengthService.findBankId(getBankId());
		
		BigDecimal accountLengthId=null;
		if(lstBnkLngth.size()!=0)
		{
			BankAccountLength bankLength=lstBnkLngth.get(0);
			accountLengthId=bankLength.getAccountLenId();
		}
		BankAccountLength bankLength=new BankAccountLength();
		
		System.out.println("BANK ID################"+getBankId());
		BankMaster bankMaster=new BankMaster();
		bankMaster.setBankId(getBankId());
		bankLength.setBankMaster(bankMaster);
		
		bankLength.setAccountLenId(accountLengthId);
		
		bankLength.setAcLength(getBankLengthId());
		bankLength.setRecordStatus(Constants.Yes);
		bankLength.setCreator(session.getUserName());
		bankLength.setCreateDate(new Date());
		bankLengthService.saveBankLengthRecord(bankLength);
		RequestContext.getCurrentInstance().execute("success.show();");
		clearAllFields();
	}
	
	
	
	public void bankLengthPageNavigation(){
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../fxdeal/BankLength.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exit() throws IOException {
		if (session.getRoleId().equalsIgnoreCase("1")) {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} else {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/branchhome.xhtml");
		}
	}
	public void clickOnOK(){
		clearAllFields();
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../registration/employeehome.xhtml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
