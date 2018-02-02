package com.amg.exchange.remittance.bean;

import java.util.List;

public class RemittanceDeclarationMainReportBean {
	
	
	
	private List<DeclerationReportBean> remittanceDeclaration2500List; 
	private List<DeclerationReportBean> remittanceDeclaration10000List ;
	private String subReport ;
	private Boolean declaration10000Check ;
	private Boolean declaration2500Check;
	private  String  waterMarkLogoPath;
	private Boolean waterMarkCheck ;
 
	private List<DeclerationReportBean> declarationList; 

	public List<DeclerationReportBean> getDeclarationList() {
		return declarationList;
	}
	public void setDeclarationList(List<DeclerationReportBean> declarationList) {
		this.declarationList = declarationList;
	}
	
	
	public List<DeclerationReportBean> getRemittanceDeclaration2500List() {
		return remittanceDeclaration2500List;
	}
	public void setRemittanceDeclaration2500List(
			List<DeclerationReportBean> remittanceDeclaration2500List) {
		this.remittanceDeclaration2500List = remittanceDeclaration2500List;
	}
	public List<DeclerationReportBean> getRemittanceDeclaration10000List() {
		return remittanceDeclaration10000List;
	}
	public void setRemittanceDeclaration10000List(
			List<DeclerationReportBean> remittanceDeclaration10000List) {
		this.remittanceDeclaration10000List = remittanceDeclaration10000List;
	}
	public String getSubReport() {
		return subReport;
	}
	public void setSubReport(String subReport) {
		this.subReport = subReport;
	}
	public Boolean getDeclaration10000Check() {
		return declaration10000Check;
	}
	public void setDeclaration10000Check(Boolean declaration10000Check) {
		this.declaration10000Check = declaration10000Check;
	}
	public Boolean getDeclaration2500Check() {
		return declaration2500Check;
	}
	public void setDeclaration2500Check(Boolean declaration2500Check) {
		this.declaration2500Check = declaration2500Check;
	}
	public String getWaterMarkLogoPath() {
		return waterMarkLogoPath;
	}
	public void setWaterMarkLogoPath(String waterMarkLogoPath) {
		this.waterMarkLogoPath = waterMarkLogoPath;
	}
	public Boolean getWaterMarkCheck() {
		return waterMarkCheck;
	}
	public void setWaterMarkCheck(Boolean waterMarkCheck) {
		this.waterMarkCheck = waterMarkCheck;
	}

}
