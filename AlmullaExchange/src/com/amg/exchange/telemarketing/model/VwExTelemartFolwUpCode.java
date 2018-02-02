package com.amg.exchange.telemarketing.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_TM_FOLLOW_UP_CODE")
public class VwExTelemartFolwUpCode implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String srNo;
	private BigDecimal applicationCountryId;
	private String tmFollowUpCOde;
	private String tmFollowUpDescription;
	private String tmFollowUpShortDesc;
	
	
	public VwExTelemartFolwUpCode(){
		
	}	
	
	
	public VwExTelemartFolwUpCode(String srNo, BigDecimal applicationCountryId,
			String tmFollowUpCOde, String tmFollowUpDescription,
			String tmFollowUpShortDesc) {
		super();
		this.srNo = srNo;
		this.applicationCountryId = applicationCountryId;
		this.tmFollowUpCOde = tmFollowUpCOde;
		this.tmFollowUpDescription = tmFollowUpDescription;
		this.tmFollowUpShortDesc = tmFollowUpShortDesc;
	}
	
	
	//Getters and setters.
	
	@Id
	@Column(name = "SRNO")
	public String getSrNo() {
		return srNo;
	}
	public void setSrNo(String srNo) {
		this.srNo = srNo;
	}
	
	@Column(name = "APPLICATION_COUNTRY_ID")
	public BigDecimal getApplicationCountryId() {
		return applicationCountryId;
	}
	public void setApplicationCountryId(BigDecimal applicationCountryId) {
		this.applicationCountryId = applicationCountryId;
	}
	
	@Column(name = "TM_FOLLOWUP_CODE")
	public String getTmFollowUpCOde() {
		return tmFollowUpCOde;
	}
	public void setTmFollowUpCOde(String tmFollowUpCOde) {
		this.tmFollowUpCOde = tmFollowUpCOde;
	}
	
	@Column(name = "TM_FOLLOWUP_DESCRIPTION")
	public String getTmFollowUpDescription() {
		return tmFollowUpDescription;
	}
	public void setTmFollowUpDescription(String tmFollowUpDescription) {
		this.tmFollowUpDescription = tmFollowUpDescription;
	}
	
	@Column(name = "TM_FOLLOWUP_SH_DESCRIPTION")
	public String getTmFollowUpShortDesc() {
		return tmFollowUpShortDesc;
	}
	public void setTmFollowUpShortDesc(String tmFollowUpShortDesc) {
		this.tmFollowUpShortDesc = tmFollowUpShortDesc;
	}	

}
