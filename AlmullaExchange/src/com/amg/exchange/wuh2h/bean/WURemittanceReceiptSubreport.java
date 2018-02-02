package com.amg.exchange.wuh2h.bean;

import java.util.List;

public class WURemittanceReceiptSubreport {

	private List<WURemittanceReportBean> remittanceApplList;
	
	private List<WURemittanceReportBean> fcsaleAppList;
	
	private List<WURemittanceReportBean> collectionAppList;
	
	private List<WURemittanceReportBean> cities;
	
	private String waterMarkLogoPath;
	private Boolean waterMarkCheck;
	
	public Boolean getWaterMarkCheck() {
		return waterMarkCheck;
	}

	public void setWaterMarkCheck(Boolean waterMarkCheck) {
		this.waterMarkCheck = waterMarkCheck;
	}

	public String getWaterMarkLogoPath() {
		return waterMarkLogoPath;
	}

	public void setWaterMarkLogoPath(String waterMarkLogoPath) {
		this.waterMarkLogoPath = waterMarkLogoPath;
	}
	
	
	public List<WURemittanceReportBean> getCities() {
		return cities;
	}

	public void setCities(List<WURemittanceReportBean> cities) {
		this.cities = cities;
	}



	private String subReport;
	
	private Boolean fcsaleApplicationCheck;
	private Boolean remittanceReceiptCheck;
	

	
	public Boolean getRemittanceReceiptCheck() {
		return remittanceReceiptCheck;
	}

	public void setRemittanceReceiptCheck(Boolean remittanceReceiptCheck) {
		this.remittanceReceiptCheck = remittanceReceiptCheck;
	}

	public Boolean getFcsaleApplicationCheck() {
		return fcsaleApplicationCheck;
	}

	public void setFcsaleApplicationCheck(Boolean fcsaleApplicationCheck) {
		this.fcsaleApplicationCheck = fcsaleApplicationCheck;
	}

	public String getSubReport() {
		return subReport;
	}

	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}

	public List<WURemittanceReportBean> getRemittanceApplList() {
		return remittanceApplList;
	}

	public List<WURemittanceReportBean> getFcsaleAppList() {
		return fcsaleAppList;
	}

	public void setRemittanceApplList(List<WURemittanceReportBean> remittanceApplList) {
		this.remittanceApplList = remittanceApplList;
	}

	public void setFcsaleAppList(List<WURemittanceReportBean> fcsaleAppList) {
		this.fcsaleAppList = fcsaleAppList;
	}



	public List<WURemittanceReportBean> getCollectionAppList() {
		return collectionAppList;
	}



	public void setCollectionAppList(List<WURemittanceReportBean> collectionAppList) {
		this.collectionAppList = collectionAppList;
	}







	
	
	
	
}
