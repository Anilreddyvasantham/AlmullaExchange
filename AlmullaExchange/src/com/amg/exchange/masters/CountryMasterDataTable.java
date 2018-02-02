package com.amg.exchange.masters;

public class CountryMasterDataTable extends CountryMasterBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6837807970306822296L;
	private Boolean remarksCheck;
	private String duplicate;
	
	
	
	public String getDuplicate() {
		return duplicate;
	}

	public void setDuplicate(String duplicate) {
		this.duplicate = duplicate;
	}

	public Boolean getRemarksCheck() {
		return remarksCheck;
	}

	public void setRemarksCheck(Boolean remarksCheck) {
		this.remarksCheck = remarksCheck;
	}
}
