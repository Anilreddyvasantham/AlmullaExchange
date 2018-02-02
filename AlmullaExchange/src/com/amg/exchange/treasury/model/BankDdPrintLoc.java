package com.amg.exchange.treasury.model;

// default package
// Generated May 23, 2014 5:18:25  Created by Chennai ODC

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.CityMaster;
import com.amg.exchange.common.model.CompanyMaster;
import com.amg.exchange.common.model.CountryMaster;
import com.amg.exchange.common.model.DistrictMaster;
import com.amg.exchange.common.model.StateMaster;

/**
 * BankDdPrintLoc Created by Chennai ODC
 */
@Entity
@Table(name = "EX_BANK_DD_PRINT_LOC")
public class BankDdPrintLoc implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal ddPrintLocId;
	private BankBranch exBankBranch;
	private CompanyMaster fsCompanyMaster;
	private CountryMaster fsCountryMaster;	
	private DistrictMaster fsDistrictMaster;
	private StateMaster fsStateMaster;
	private CityMaster fsCityMaster;	
	private String ddAgent;
	private String ddPrintLocation;
	private String recordStatus;
	private Date createDate;
	private Date updateDate;
	private String creator;
	private String modifier;

	public BankDdPrintLoc() {
	}

	public BankDdPrintLoc(BigDecimal ddPrintLocId) {
		this.ddPrintLocId = ddPrintLocId;
	}

	public BankDdPrintLoc(BigDecimal ddPrintLocId,
			CompanyMaster fsCompanyMaster, CountryMaster fsCountryMaster,
			String ddAgent, String ddPrintLocation, String recordStatus,
			Date createDate, Date updateDate, String creator,
			String modifier,BankBranch exBankBranch,			
			 DistrictMaster fsDistrictMaster,
		     StateMaster fsStateMaster,
			 CityMaster fsCityMaster	
			
			) {
		this.ddPrintLocId = ddPrintLocId;
		this.fsCompanyMaster = fsCompanyMaster;
		this.fsCountryMaster = fsCountryMaster;
		this.ddAgent = ddAgent;
		this.ddPrintLocation = ddPrintLocation;
		this.recordStatus = recordStatus;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.creator = creator;
		this.modifier = modifier;
		this.fsDistrictMaster = fsDistrictMaster;
		this.fsStateMaster = fsStateMaster;
		this.fsCityMaster = fsCityMaster;
		this.exBankBranch=exBankBranch;
	}

/*	@Id
	@TableGenerator(name = "ddprintlocid", table = "ex_ddprintlocidpk", pkColumnName = "ddprintlocidkey", pkColumnValue = "ddprintlocidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "ddprintlocid")
	@Column(name = "DD_PRINT_LOC_ID", unique = true, nullable = false, precision = 22, scale = 0)*/
	
	//Added by kani begin
	@Id
	@GeneratedValue(generator="ex_bank_dd_print_loc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_bank_dd_print_loc_seq",sequenceName="EX_BANK_DD_PRINT_LOC_SEQ",allocationSize=1)
	@Column(name = "DD_PRINT_LOC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	//Added by kani end	
	public BigDecimal getDdPrintLocId() {
		return this.ddPrintLocId;
	}

	public void setDdPrintLocId(BigDecimal ddPrintLocId) {
		this.ddPrintLocId = ddPrintLocId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPANY_ID")
	public CompanyMaster getFsCompanyMaster() {
		return this.fsCompanyMaster;
	}

	public void setFsCompanyMaster(CompanyMaster fsCompanyMaster) {
		this.fsCompanyMaster = fsCompanyMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COUNTRY_ID")
	public CountryMaster getFsCountryMaster() {
		return this.fsCountryMaster;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DISTRICT_ID")
	public DistrictMaster getFsDistrictMaster() {
		return this.fsDistrictMaster;
	}

	public void setFsDistrictMaster(DistrictMaster fsDistrictMaster) {
		this.fsDistrictMaster = fsDistrictMaster;
	}

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	public StateMaster getFsStateMaster() {
		return this.fsStateMaster;
	}

	public void setFsStateMaster(StateMaster fsStateMaster) {
		this.fsStateMaster = fsStateMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	public CityMaster getFsCityMaster() {
		return this.fsCityMaster;
	}

	public void setFsCityMaster(CityMaster fsCityMaster) {
		this.fsCityMaster = fsCityMaster;
	}
	
	
	
	public void setFsCountryMaster(CountryMaster fsCountryMaster) {
		this.fsCountryMaster = fsCountryMaster;
	}

	@Column(name = "DD_AGENT", length = 30)
	public String getDdAgent() {
		return this.ddAgent;
	}

	public void setDdAgent(String ddAgent) {
		this.ddAgent = ddAgent;
	}

	@Column(name = "DD_PRINT_LOCATION", length = 40)
	public String getDdPrintLocation() {
		return this.ddPrintLocation;
	}

	public void setDdPrintLocation(String ddPrintLocation) {
		this.ddPrintLocation = ddPrintLocation;
	}

	@Column(name = "RECORD_STATUS", length = 1)
	public String getRecordStatus() {
		return this.recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	@Column(name = "CREATE_DATE")
	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "UPDATE_DATE")
	public Date getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "CREATOR", length = 15)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "MODIFIER", length = 15)
	public String getModifier() {
		return this.modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BANK_BRANCH_ID")
	public BankBranch getExBankBranch() {
		return this.exBankBranch;
	}

	public void setExBankBranch(BankBranch exBankBranch) {
		this.exBankBranch = exBankBranch;
	}

}
