package com.amg.exchange.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name = "EX_RATE_ALERT_FREQUENCY")
public class RateAlertFrequency implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private BigDecimal onlineRateAlertId;
	private String frequencyDescription;
	
	
	public RateAlertFrequency() {
		
	}


	public RateAlertFrequency(BigDecimal onlineRateAlertId, String frequencyDescription) {
		this.onlineRateAlertId = onlineRateAlertId;
		this.frequencyDescription = frequencyDescription;
	}
	
	
	
	@Id
	@GeneratedValue(generator="ex_rate_alert_frequency_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_rate_alert_frequency_seq",sequenceName="EX_RATE_ALERT_FREQUENCY_SEQ",allocationSize=1)
	@Column(name = "RATE_ALERT_FREQUENCY_ID")
	public BigDecimal getOnlineRateAlertId() {
		return onlineRateAlertId;
	}
	public void setOnlineRateAlertId(BigDecimal onlineRateAlertId) {
		this.onlineRateAlertId = onlineRateAlertId;
	}
	
	@Column(name = "FREQUENCY_DESC")
	public String getFrequencyDescription() {
		return frequencyDescription;
	}
	public void setFrequencyDescription(String frequencyDescription) {
		this.frequencyDescription = frequencyDescription;
	}
	
	

}
