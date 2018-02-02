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


/**
 * Author Rahamathali Shaik
 */
@Entity
@Table(name="EX_REMITTANCE_MODE_DESC")
public class RemittanceModeDescription implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private  BigDecimal remittanceModeDescId;
	private RemittanceModeMaster remittanceModeMaster;
	private LanguageType languageType;
	private String remittanceDescription;
//	private String englishRemittanceDescription;
	
	
	
	public RemittanceModeDescription() {
	}

	public RemittanceModeDescription(BigDecimal remittanceModeDescId) {
		this.remittanceModeDescId = remittanceModeDescId;
	}

	public RemittanceModeDescription(BigDecimal remittanceModeDescId,
			RemittanceModeMaster remittanceModeMaster,
			LanguageType languageType,String remittanceDescription
			/*,String englishRemittanceDescription*/) {
		this.remittanceModeDescId = remittanceModeDescId;
		this.remittanceModeMaster = remittanceModeMaster;
		this.languageType = languageType;
		this.remittanceDescription = remittanceDescription;
	//	this.englishRemittanceDescription = englishRemittanceDescription;
	
	}

	@Id
	@GeneratedValue(generator="ex_remittance_mode_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_remittance_mode_desc_seq",sequenceName="EX_REMITTANCE_MODE_DESC_SEQ",allocationSize=1)
	@Column(name ="REMITTANCE_MODE_DESC_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRemittanceModeDescId() {
		return remittanceModeDescId;
	}

	public void setRemittanceModeDescId(BigDecimal remittanceModeDescId) {
		this.remittanceModeDescId = remittanceModeDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REMITTANCE_MODE_ID")
	public RemittanceModeMaster getRemittanceModeMaster() {
		return remittanceModeMaster;
	}

	public void setRemittanceModeMaster(RemittanceModeMaster remittanceModeMaster) {
		this.remittanceModeMaster = remittanceModeMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}

	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}

	@Column(name="REMITTANCE_DESCRIPTION")
	public String getRemittanceDescription() {
		return remittanceDescription;
	}

	public void setRemittanceDescription(String remittanceDescription) {
		this.remittanceDescription = remittanceDescription;
	}

	
}
