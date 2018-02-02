package com.amg.exchange.remittance.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.amg.exchange.remittance.model.AllBeneficiaryView;
import com.amg.exchange.remittance.service.IPersonalRemittanceService;

/**
 * @author Rahamathali Shaik
 * 
 */
@Component("benefeciaryEnquiryBean")
@Scope("session")
public class BenefeciaryEnquiryBean {
 
	private BigDecimal beneficiaryCountryId;
	private BigDecimal customerNo;
	private List<PopulateData>  allBeneCountryList ;
	private BigDecimal customerRefno;
	private String idNumber;
	private String customerFullName;
	private String customerLocalFullName;
	private List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList;
	
	@Autowired
	IPersonalRemittanceService iPersonalRemittanceService;

	public BigDecimal getBeneficiaryCountryId() {
		return beneficiaryCountryId;
	}

	public void setBeneficiaryCountryId(BigDecimal beneficiaryCountryId) {
		this.beneficiaryCountryId = beneficiaryCountryId;
	}

	public List<PopulateData> getAllBeneCountryList() {
		return allBeneCountryList;
	}

	public void setAllBeneCountryList(List<PopulateData> allBeneCountryList) {
		this.allBeneCountryList = allBeneCountryList;
	}
	 
	
	public BigDecimal getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(BigDecimal customerNo) {
		this.customerNo = customerNo;
	}

	public List<PersonalRemmitanceBeneficaryDataTable> getCoustomerBeneficaryDTList() {
		return coustomerBeneficaryDTList;
	}

	public void setCoustomerBeneficaryDTList(
			List<PersonalRemmitanceBeneficaryDataTable> coustomerBeneficaryDTList) {
		this.coustomerBeneficaryDTList = coustomerBeneficaryDTList;
	}
	
	public BigDecimal getCustomerRefno() {
		return customerRefno;
	}

	public void setCustomerRefno(BigDecimal customerRefno) {
		this.customerRefno = customerRefno;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getCustomerFullName() {
		return customerFullName;
	}

	public void setCustomerFullName(String customerFullName) {
		this.customerFullName = customerFullName;
	}

	public String getCustomerLocalFullName() {
		return customerLocalFullName;
	}

	public void setCustomerLocalFullName(String customerLocalFullName) {
		this.customerLocalFullName = customerLocalFullName;
	}

	public void preLoadData(){
		List<PopulateData> countryList = iPersonalRemittanceService.getBeneficaryList(getCustomerNo());
		clear();
		if(countryList!=null && countryList.size()>0){
			setAllBeneCountryList(countryList);
		}
	}
	
	public void clear(){
		setCoustomerBeneficaryDTList(null);
		//setBeneficiaryCountryId(null);
	}
	
	public void populateBeneficiaryEnqData(){
		if(getBeneficiaryCountryId()!=null){
			List<AllBeneficiaryView> beneViewList = iPersonalRemittanceService.getAllBeneficiaryt(getCustomerNo(),getBeneficiaryCountryId());
			if(beneViewList!=null && beneViewList.size()>0){
				coustomerBeneficaryDTList = new ArrayList<PersonalRemmitanceBeneficaryDataTable>();
				for(AllBeneficiaryView beneView:beneViewList){
				PersonalRemmitanceBeneficaryDataTable personalRBDataTable = new PersonalRemmitanceBeneficaryDataTable();
				personalRBDataTable.setBenificaryName(beneView.getBeneName());
				personalRBDataTable.setRelationShipName(beneView.getRelationName());
				personalRBDataTable.setCountryName(beneView.getBeneBankCountryName()); 
				personalRBDataTable.setBankName(beneView.getBankName());
				personalRBDataTable.setServiceGroupName(beneView.getServerviceGroupCode());
				personalRBDataTable.setBankBranchName(beneView.getBankBranchName());
				personalRBDataTable.setCurrencyQuoteName(beneView.getCurrencyQuoteName());
				personalRBDataTable.setTransactinStatus(beneView.getIsActiveDesc());
				personalRBDataTable.setBankAccountNumber(beneView.getBankAccountNumber());
				personalRBDataTable.setRemarks(beneView.getRemarks());
				coustomerBeneficaryDTList.add(personalRBDataTable);
				}
				setCoustomerBeneficaryDTList(coustomerBeneficaryDTList);
			}
		}
	}
	
	
	public String backToPersonalRemittance(){  
		return "PersonalRemittance?faces-redirect=true"; 
	}
	
	
	
	
	
}
