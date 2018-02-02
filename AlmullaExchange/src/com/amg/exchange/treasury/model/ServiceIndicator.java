/*package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

*//**
 * @author Rahamathali Shaik 
 *//*    

@Entity
@Table(name = "EX_SERVICE_INDICATOR")
public class ServiceIndicator implements Serializable{

	private BigDecimal serviceIndicatorId;
	private String serviceShortDesc;
	private String serviceFullDesc;
	private List<ExchangeRate> exchangeRate=new ArrayList<ExchangeRate>();
	private List<PipsMaster> pipsMaster=new ArrayList<PipsMaster>();
	
	
	public ServiceIndicator() {
		super();
	}
	
	public ServiceIndicator(BigDecimal serviceIndicatorId) {
		super();
		this.serviceIndicatorId = serviceIndicatorId;
	}

	public ServiceIndicator(BigDecimal serviceIndicatorId,
			String serviceShortDesc, String serviceFullDesc,List<ExchangeRate> exchangeRate,List<PipsMaster> pipsMaster) {
		super();
		this.serviceIndicatorId = serviceIndicatorId;
		this.serviceShortDesc = serviceShortDesc;
		this.serviceFullDesc = serviceFullDesc;
		this.exchangeRate=exchangeRate;
		this.pipsMaster=pipsMaster;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_service_indicator_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_service_indicator_seq",sequenceName="EX_SERVICE_INDICATOR_SEQ",allocationSize=1)
	@Column(name ="SERVICE_INDICATOR_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getServiceIndicatorId() {
		return serviceIndicatorId;
	}
	public void setServiceIndicatorId(BigDecimal serviceIndicatorId) {
		this.serviceIndicatorId = serviceIndicatorId;
	}
	
	@Column(name="SERVICE_SHORT_DESC")
	public String getServiceShortDesc() {
		return serviceShortDesc;
	}
	public void setServiceShortDesc(String serviceShortDesc) {
		this.serviceShortDesc = serviceShortDesc;
	}
	
	@Column(name="SERVICE_FULL_DESC")
	public String getServiceFullDesc() {
		return serviceFullDesc;
	}
	public void setServiceFullDesc(String serviceFullDesc) {
		this.serviceFullDesc = serviceFullDesc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceIndicatorId")
	public List<ExchangeRate> getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(List<ExchangeRate> exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "serviceIndicator")
	public List<PipsMaster> getPipsMaster() {
		return pipsMaster;
	}

	public void setPipsMaster(List<PipsMaster> pipsMaster) {
		this.pipsMaster = pipsMaster;
	}
	
	
}
*/