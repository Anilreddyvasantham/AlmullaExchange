package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.Generated;

import com.amg.exchange.common.model.CountryMaster;

/**
 * @author Rahamathali Shaik 
 */    

@Entity
@Table(name = "EX_DATA")
public class Data implements Serializable {
	
	private BigDecimal pId;
	private CountryMaster fsApplicationCountryMaster;
	private String  headerData1;
	private String  headerData2;
	private String  headerData3;
	private String  headerData4;
	private String  headerData5;
	

	
	public Data(){
		
	}
	
	public Data(BigDecimal pId) {
		super();
		this.pId = pId;
	}

	public Data(BigDecimal pId,CountryMaster fsApplicationCountryMaster, String headerData1,
			String headerData2, String headerData3, String headerData4,
			String headerData5) {
		super();
		this.pId=pId;
		this.fsApplicationCountryMaster = fsApplicationCountryMaster;
		this.headerData1 = headerData1;
		this.headerData2 = headerData2;
		this.headerData3 = headerData3;
		this.headerData4 = headerData4;
		this.headerData5 = headerData5;
	}

	
	@Id
	@GeneratedValue
	@Column(name ="PID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getpId() {
		return pId;
	}

	public void setpId(BigDecimal pId) {
		this.pId = pId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APPLICATION_COUNTRY_ID")
	public CountryMaster getFsApplicationCountryMaster() {
		return fsApplicationCountryMaster;
	}

	public void setFsApplicationCountryMaster(
			CountryMaster fsApplicationCountryMaster) {
		this.fsApplicationCountryMaster = fsApplicationCountryMaster;
	}

	@Column(name="HEADER_DATA1")
	public String getHeaderData1() {
		return headerData1;
	}

	public void setHeaderData1(String headerData1) {
		this.headerData1 = headerData1;
	}

	@Column(name="HEADER_DATA2")
	public String getHeaderData2() {
		return headerData2;
	}

	public void setHeaderData2(String headerData2) {
		this.headerData2 = headerData2;
	}

	@Column(name="HEADER_DATA3")
	public String getHeaderData3() {
		return headerData3;
	}

	public void setHeaderData3(String headerData3) {
		this.headerData3 = headerData3;
	}

	@Column(name="HEADER_DATA4")
	public String getHeaderData4() {
		return headerData4;
	}

	public void setHeaderData4(String headerData4) {
		this.headerData4 = headerData4;
	}

	@Column(name="HEADER_DATA5")
	public String getHeaderData5() {
		return headerData5;
	}

	public void setHeaderData5(String headerData5) {
		this.headerData5 = headerData5;
	}
	
}
