package com.amg.exchange.remittance.bean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.common.model.CountryMasterDesc;
import com.amg.exchange.common.service.IGeneralService;
import com.amg.exchange.registration.model.Customer;
import com.amg.exchange.remittance.model.SwiftMaster;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;
import com.amg.exchange.util.SessionStateManage;

@Component("searchSwiftCodeBean")
@Scope("session")
public class SearchSwiftCodeBean<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SessionStateManage session=new SessionStateManage();
	
	private String errmsg;
	private List<CountryMasterDesc> lstCountry;
	private String swiftCode;
	private String swiftBankName;
	private Boolean enableDatatable;
	private List<SearchSwiftCodeDataTable> lstDataTableSwiftCode;
	private BigDecimal countryId;
	private String fromSwiftCode;
	
	
	
	
	
	public String getFromSwiftCode() {
		return fromSwiftCode;
	}


	public void setFromSwiftCode(String fromSwiftCode) {
		this.fromSwiftCode = fromSwiftCode;
	}


	public String getErrmsg() {
		return errmsg;
	}


	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}


	public List<CountryMasterDesc> getLstCountry() {
		return lstCountry;
	}


	public void setLstCountry(List<CountryMasterDesc> lstCountry) {
		this.lstCountry = lstCountry;
	}


	public String getSwiftCode() {
		return swiftCode;
	}


	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}


	public String getSwiftBankName() {
		return swiftBankName;
	}


	public void setSwiftBankName(String swiftBankName) {
		this.swiftBankName = swiftBankName;
	}





	public Boolean getEnableDatatable() {
		return enableDatatable;
	}


	public void setEnableDatatable(Boolean enableDatatable) {
		this.enableDatatable = enableDatatable;
	}


	public List<SearchSwiftCodeDataTable> getLstDataTableSwiftCode() {
		return lstDataTableSwiftCode;
	}


	public void setLstDataTableSwiftCode(
			List<SearchSwiftCodeDataTable> lstDataTableSwiftCode) {
		this.lstDataTableSwiftCode = lstDataTableSwiftCode;
	}





	public BigDecimal getCountryId() {
		return countryId;
	}


	public void setCountryId(BigDecimal countryId) {
		this.countryId = countryId;
	}





	@Autowired
	IGeneralService<T> generalService;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;
	
	@Autowired
	ApplicationContext appContext;
	
	public void pageNavigation(){

		try {		
			setEnableDatatable(false);
			setCountryId(null);
			setSwiftCode(null);
			setSwiftBankName(null);
			
			List<CountryMasterDesc>  lstCountryMasterDesc= generalService.getCountryList(session.getLanguageId());
			setLstCountry(lstCountryMasterDesc);
		
			
			FacesContext.getCurrentInstance().getExternalContext()
			.redirect("../remittance/SearchSwiftCode.xhtml");
			
			
			
		} catch (IOException e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");		
			return;
		}


	}
	public void searchSwiftCode()
	{
		
		setEnableDatatable(false);
		if((getSwiftCode()== null || getSwiftCode().equalsIgnoreCase("")) && (getSwiftBankName()== null || getSwiftBankName().equalsIgnoreCase("")) )
		{
			RequestContext.getCurrentInstance().execute("mandatoryCheck.show();");		
			return;
		}
			
			
		if(getSwiftCode()== null || getSwiftCode().equalsIgnoreCase(""))
		{
			if(getSwiftBankName()!=null && getSwiftBankName().length()<4)
			{
				RequestContext.getCurrentInstance().execute("minCharCheck.show();");		
				return;
			}
				
		}
		
		if(getSwiftBankName()== null || getSwiftBankName().equalsIgnoreCase(""))
		{
			if(getSwiftCode()!=null && getSwiftCode().length()<4)
			{
				RequestContext.getCurrentInstance().execute("minCharCheck.show();");		
				return;
			}
		}
		
		
		if((getSwiftCode()!=null && !getSwiftCode().equalsIgnoreCase("")) && getSwiftCode().length()<4)
		{
			RequestContext.getCurrentInstance().execute("minCharCheck.show();");		
			return;
		}
		if((getSwiftBankName()!=null && !getSwiftBankName().equalsIgnoreCase("")) && getSwiftBankName().length()<4)
		{
			RequestContext.getCurrentInstance().execute("minCharCheck.show();");		
			return;
		}
		

		if((getSwiftBankName()!= null && !getSwiftBankName().equalsIgnoreCase("")) && (getSwiftCode()!=null && !getSwiftCode().equalsIgnoreCase("")))
		{
			RequestContext.getCurrentInstance().execute("mandatoryBoth.show();");		
			return;
		}
		
		
		List<SwiftMaster> lstSwiftMaster= iPersonalRemittanceService.getSwiftCodeSearch(getCountryId(), getSwiftCode(), getSwiftBankName());
		
		if(lstSwiftMaster!=null)
		{
			List<SearchSwiftCodeDataTable> lstDataTableSwiftCode = new ArrayList<SearchSwiftCodeDataTable>();
			for(SwiftMaster swiftMaster: lstSwiftMaster)
			{
				SearchSwiftCodeDataTable searchSwiftCodeDataTable = new SearchSwiftCodeDataTable();
				searchSwiftCodeDataTable.setSwiftBankName(swiftMaster.getBankName());
				searchSwiftCodeDataTable.setSwiftCode(swiftMaster.getSwiftBIC());
				searchSwiftCodeDataTable.setSwiftBankId(swiftMaster.getSwiftId());
				searchSwiftCodeDataTable.setCountryCode(swiftMaster.getSwiftCountryCode());
				lstDataTableSwiftCode.add(searchSwiftCodeDataTable);
				
			}
			setEnableDatatable(true);
			setLstDataTableSwiftCode(lstDataTableSwiftCode);
		}
		
		
	}
	
	public void selectSwiftCode(SearchSwiftCodeDataTable searchSwiftCodeDataTable)
	{
		PersonalRemittanceBean personalRemittanceBean=	(PersonalRemittanceBean)appContext.getBean("personalRemittanceBean");
		
		if(getFromSwiftCode().equalsIgnoreCase("FromSwiftCode1"))
		{
			personalRemittanceBean.setBeneSwiftBank1(searchSwiftCodeDataTable.getSwiftCode());
			personalRemittanceBean.setSwiftId1(searchSwiftCodeDataTable.getSwiftBankId());
			personalRemittanceBean.setBeneSwiftBankAddr1(searchSwiftCodeDataTable.getSwiftBankName());
			
		}else if(getFromSwiftCode().equalsIgnoreCase("FromSwiftCode2"))
		{
			personalRemittanceBean.setBeneSwiftBank2(searchSwiftCodeDataTable.getSwiftCode());
			personalRemittanceBean.setSwiftId2(searchSwiftCodeDataTable.getSwiftBankId());
			personalRemittanceBean.setBeneSwiftBankAddr2(searchSwiftCodeDataTable.getSwiftBankName());
		}
		
		
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");		
			return;
		}
		
	}
	
	public void clearSearch()
	{
		setEnableDatatable(false);
		setCountryId(null);
		setSwiftCode(null);
		setSwiftBankName(null);
	}
	
	public void exit()
	{
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("../remittance/PersonalRemittance.xhtml");
		} catch (IOException e) {
			setErrmsg(e.getMessage());
			RequestContext.getCurrentInstance().execute("csp.show();");		
			return;
		}
	}
}
