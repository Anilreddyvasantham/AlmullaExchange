package com.amg.exchange.cbk.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "V_EX_UNMAPPED_GLS")
public class ViewExUnmappedGLS implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String aclsCode;
	private String aclsDesc;	
	
	
	public ViewExUnmappedGLS(){
		
	}	
	
	public ViewExUnmappedGLS(String aclsCode, String aclsDesc) {
		super();
		this.aclsCode = aclsCode;
		this.aclsDesc = aclsDesc;
	}
	
	
	
	//Getters and Setters.
	
	@Id
	@Column(name = "ACLSCOD")
	public String getAclsCode() {
		return aclsCode;
	}
	public void setAclsCode(String aclsCode) {
		this.aclsCode = aclsCode;
	}
	
	@Column(name = "ACLSDES")
	public String getAclsDesc() {
		return aclsDesc;
	}
	public void setAclsDesc(String aclsDesc) {
		this.aclsDesc = aclsDesc;
	}

}
