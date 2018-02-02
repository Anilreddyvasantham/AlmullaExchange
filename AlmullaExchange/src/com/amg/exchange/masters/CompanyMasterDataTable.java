package com.amg.exchange.masters;

public class CompanyMasterDataTable extends CompanyMasterBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private Boolean remarksCheck;

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}

	@Override
	public String toString() {
		return "CompanyMasterDataTable [remarksCheck=" + remarksCheck + ", companyMaster=" + companyMaster + ", companyMasterDesc=" + companyMasterDesc + ", icompanyMasterservice=" + icompanyMasterservice + ", generalService=" + generalService + "]";
	}

}
