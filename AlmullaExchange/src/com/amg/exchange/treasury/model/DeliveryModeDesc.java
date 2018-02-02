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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.amg.exchange.common.model.LanguageType;

@Entity
@Table(name="EX_DELIVERY_MODE_DESC")
public class DeliveryModeDesc implements Serializable {
	private static final long serialVersionUID = 2315791709068216697L;
	
	private BigDecimal deliveryModeDescId;
	private DeliveryMode deliveryMode;
	private LanguageType languageType;
	//private String localDeliveryName;
	private String englishDeliveryName;
	
	

	public DeliveryModeDesc(){
		
	}
	
	public DeliveryModeDesc(BigDecimal deliveryModeDescId,
			LanguageType languageType, String localDeliveryName,
			DeliveryMode deliveryMode,String englishDeliveryName) {
		super();
		this.deliveryModeDescId = deliveryModeDescId;
		this.languageType = languageType;
		//this.localDeliveryName = localDeliveryName;
		this.deliveryMode = deliveryMode;
		this.englishDeliveryName=englishDeliveryName;
	}
	
	@Id
	@GeneratedValue(generator="ex_delivery_mode_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_delivery_mode_desc_seq",sequenceName="EX_DELIVERY_MODE_DESC_SEQ",allocationSize=1)
	@Column(name ="DELIVERY_MODE_DESC_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getDeliveryModeDescId() {
		return deliveryModeDescId;
	}
	public void setDeliveryModeDescId(BigDecimal deliveryModeDescId) {
		this.deliveryModeDescId = deliveryModeDescId;
	}
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "DELIVERY_MODE_ID")
	public DeliveryMode getDeliveryMode() {
		return deliveryMode;
	}
	
	public void setDeliveryMode(DeliveryMode deliveryMode) {
		this.deliveryMode = deliveryMode;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public  LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	
	@Column(name="DELIVERY_DESCRIPTION")
	public String getEnglishDeliveryName() {
		return englishDeliveryName;
	}

	public void setEnglishDeliveryName(String englishDeliveryName) {
		this.englishDeliveryName = englishDeliveryName;
	}

	

}
