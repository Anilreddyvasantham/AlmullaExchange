package com.amg.exchange.treasury.viewModel;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_VIEW_CUST_SPL_DEAL")
public class SpecialCustomerValueDatedView {
	
	@Id
	@Column(name="PARAM_DETAILS_ID")
	private BigDecimal paramDetailsId;
	
	@Column(name="CUSTOMER_TYPE")
	private String customerType;
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="SHORT_DESC")
	private String shortDesc;
	
	@Column(name="VALID_DAYS")
	private BigDecimal validDays;

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getShortDesc() {
		return shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}

	public BigDecimal getValidDays() {
		return validDays;
	}

	public void setValidDays(BigDecimal validDays) {
		this.validDays = validDays;
	}
}
