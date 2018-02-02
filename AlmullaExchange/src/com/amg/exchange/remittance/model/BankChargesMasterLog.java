package com.amg.exchange.remittance.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "MASLOG")
public class BankChargesMasterLog implements Serializable {
	
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tabName;	
	private String changeType ;	
	private String uniqIndex ;	
	private String uniqIndexVaue ;
	private String fileName ;	 
	private String oldInf;	 
	private String newInf;	
	private String oracleUser;
	private Date changeDate;
	
 	@Id
	@Column(name = "TABNAM")
	public String getTabName() {
		return tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
	@Column(name = "CHNGTYP")
	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	@Column(name = "UNIQINDX")
	public String getUniqIndex() {
		return uniqIndex;
	}

	public void setUniqIndex(String uniqIndex) {
		this.uniqIndex = uniqIndex;
	}
	@Column(name = "UNIQINDXVAL")
	public String getUniqIndexVaue() {
		return uniqIndexVaue;
	}

	public void setUniqIndexVaue(String uniqIndexVaue) {
		this.uniqIndexVaue = uniqIndexVaue;
	}
	@Column(name = "FILNAM")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Column(name = "OLDINF")
	public String getOldInf() {
		return oldInf;
	}

	public void setOldInf(String oldInf) {
		this.oldInf = oldInf;
	}
	@Column(name = "NEWINF")
	public String getNewInf() {
		return newInf;
	}

	public void setNewInf(String newInf) {
		this.newInf = newInf;
	}
	@Column(name = "ORAUSR")
	public String getOracleUser() {
		return oracleUser;
	}

	public void setOracleUser(String oracleUser) {
		this.oracleUser = oracleUser;
	}
@Column(name = "CHNGDAT")
	public Date getChangeDate() {
		return changeDate;
	}

	public void setChangeDate(Date changeDate) {
		this.changeDate = changeDate;
	}


	
	
}
