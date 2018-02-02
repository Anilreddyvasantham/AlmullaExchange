package com.amg.exchange.treasury.model;
// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

import java.io.Serializable;
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

/**
 * ExSpecialDealUtiliesed Created by Chennai ODC
 */
@Entity
@Table(name = "EX_SPECIAL_DEAL_UTILIESED")
public class SpecialDealUtiliesed implements Serializable {

	/**
	 * Generated Serialized UID
	 */
	private static final long serialVersionUID = -6905546134120784502L;
	private BigDecimal specialDealUtilId;
	private SpecialDeal exSpecialDeal;
	private BigDecimal amountUtilised;
	private Date utilisedDate;

	public SpecialDealUtiliesed() {
	}

	public SpecialDealUtiliesed(BigDecimal specialDealUtilId) {
		this.specialDealUtilId = specialDealUtilId;
	}

	public SpecialDealUtiliesed(BigDecimal specialDealUtilId,
			SpecialDeal exSpecialDeal, BigDecimal amountUtilised,
			Date utilisedDate) {
		this.specialDealUtilId = specialDealUtilId;
		this.exSpecialDeal = exSpecialDeal;
		this.amountUtilised = amountUtilised;
		this.utilisedDate = utilisedDate;
	}

	
	/*@Id
	@TableGenerator(name="specialdealutilid",table="ex_specialdealutilidpk",pkColumnName="specialdealutilidkey",pkColumnValue="specialdealutilidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="specialdealutilid")
	@Column(name = "SPECIAL_DEAL_UTIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	*/
	
	//Added by kani begin
	
	@Id
	@GeneratedValue(generator="ex_special_deal_utiliesed_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_special_deal_utiliesed_seq",sequenceName="EX_SPECIAL_DEAL_UTILIESED_SEQ",allocationSize=1)
	@Column(name = "SPECIAL_DEAL_UTIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	
	//Added by kani end
	
	public BigDecimal getSpecialDealUtilId() {
		return this.specialDealUtilId;
	}

	public void setSpecialDealUtilId(BigDecimal specialDealUtilId) {
		this.specialDealUtilId = specialDealUtilId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SPECIAL_DEAL_ID")
	public SpecialDeal getExSpecialDeal() {
		return this.exSpecialDeal;
	}

	public void setExSpecialDeal(SpecialDeal exSpecialDeal) {
		this.exSpecialDeal = exSpecialDeal;
	}

	@Column(name = "AMOUNT_UTILISED", precision = 22, scale = 3)
	public BigDecimal getAmountUtilised() {
		return this.amountUtilised;
	}

	public void setAmountUtilised(BigDecimal amountUtilised) {
		this.amountUtilised = amountUtilised;
	}

	@Column(name = "UTILISED_DATE")
	public Date getUtilisedDate() {
		return this.utilisedDate;
	}

	public void setUtilisedDate(Date utilisedDate) {
		this.utilisedDate = utilisedDate;
	}

}
