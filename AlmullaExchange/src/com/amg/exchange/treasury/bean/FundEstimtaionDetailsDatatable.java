package com.amg.exchange.treasury.bean;


public class FundEstimtaionDetailsDatatable {
	
	private int serinalNo;
	private String projectionDate;
	private String bankCountry;
	private String bankName;
	private String bankCode;
	private double balanceUsd;
	private double usdSalesProject;
	private double fcToUsdProjection;
	private double ikonRate;
	private double salesprojection;
	
	public FundEstimtaionDetailsDatatable(int serinalNo, String projectionDate,
			String bankCountry, String bankName, double balanceUsd,
			double usdSalesProject, double salesprojection,  double ikonRate, double fcToUsdProjection,String bankCode ) {
		this.serinalNo = serinalNo;
		this.projectionDate = projectionDate;
		this.bankCountry = bankCountry;
		this.bankName = bankName;
		this.balanceUsd = balanceUsd;
		this.usdSalesProject = usdSalesProject;
		this.fcToUsdProjection = fcToUsdProjection;
		this.ikonRate = ikonRate;
		this.salesprojection = salesprojection;
		this.bankCode =bankCode;
	}
	
	

	public String getBankCode() {
		return bankCode;
	}



	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}



	public int getSerinalNo() {
		return serinalNo;
	}
	public void setSerinalNo(int serinalNo) {
		this.serinalNo = serinalNo;
	}

	public String getProjectionDate() {
		return projectionDate;
	}
	public void setProjectionDate(String projectionDate) {
		this.projectionDate = projectionDate;
	}

	public String getBankCountry() {
		return bankCountry;
	}
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}

	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public double getBalanceUsd() {
		return balanceUsd;
	}
	public void setBalanceUsd(double balanceUsd) {
		this.balanceUsd = balanceUsd;
	}

	public double getUsdSalesProject() {
		return usdSalesProject;
	}
	public void setUsdSalesProject(double usdSalesProject) {
		this.usdSalesProject = usdSalesProject;
	}

	public double getFcToUsdProjection() {
		return fcToUsdProjection;
	}
	public void setFcToUsdProjection(double fcToUsdProjection) {
		this.fcToUsdProjection = fcToUsdProjection;
	}

	public double getIkonRate() {
		return ikonRate;
	}

	public void setIkonRate(double ikonRate) {
		this.ikonRate = ikonRate;
	}

	public double getSalesprojection() {
		return salesprojection;
	}

	public void setSalesprojection(double salesprojection) {
		this.salesprojection = salesprojection;
	}
	
}
