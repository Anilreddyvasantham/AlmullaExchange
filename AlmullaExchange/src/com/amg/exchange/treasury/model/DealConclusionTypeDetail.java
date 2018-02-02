package com.amg.exchange.treasury.model;

// default package
// Generated Jul 10, 2014 5:34:26  Created by Chennai ODC

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
import javax.persistence.TableGenerator;

import com.amg.exchange.common.model.LanguageType;

/**
 * ExDealConclusionTypeDetail Created by Chennai ODC
 */
@Entity
@Table(name = "EX_DEAL_CONCLUSION_TYPE_DETAIL")
public class DealConclusionTypeDetail implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4028337886394155769L;
	private BigDecimal dealConclusionDetailId;
	private DealConclusionType exDealConclusionType;
	private LanguageType fsLanguageType;
	private String dealConclnTypeCode;
	private String dealConclnDesc;

	public DealConclusionTypeDetail() {
	}

	public DealConclusionTypeDetail(BigDecimal dealConclusionDetailId) {
		this.dealConclusionDetailId = dealConclusionDetailId;
	}

	public DealConclusionTypeDetail(BigDecimal dealConclusionDetailId,
			DealConclusionType exDealConclusionType,
			LanguageType fsLanguageType, String dealConclnTypeCode,
			String dealConclnDesc) {
		this.dealConclusionDetailId = dealConclusionDetailId;
		this.exDealConclusionType = exDealConclusionType;
		this.fsLanguageType = fsLanguageType;
		this.dealConclnTypeCode = dealConclnTypeCode;
		this.dealConclnDesc = dealConclnDesc;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_deal_conclu_type_det_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_deal_conclu_type_det_seq",sequenceName="EX_DEAL_CONCLU_TYPE_DET_SEQ",allocationSize=1)
	@Column(name = "DEAL_CONCLUSION_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getDealConclusionDetailId() {
		return this.dealConclusionDetailId;
	}

	public void setDealConclusionDetailId(BigDecimal dealConclusionDetailId) {
		this.dealConclusionDetailId = dealConclusionDetailId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEAL_CONCLUSION_TYPE_ID")
	public DealConclusionType getExDealConclusionType() {
		return this.exDealConclusionType;
	}

	public void setExDealConclusionType(DealConclusionType exDealConclusionType) {
		this.exDealConclusionType = exDealConclusionType;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "DEAL_CONCLN_TYPE_CODE", length = 50)
	public String getDealConclnTypeCode() {
		return this.dealConclnTypeCode;
	}

	public void setDealConclnTypeCode(String dealConclnTypeCode) {
		this.dealConclnTypeCode = dealConclnTypeCode;
	}

	@Column(name = "DEAL_CONCLN_DESC", length = 200)
	public String getDealConclnDesc() {
		return this.dealConclnDesc;
	}

	public void setDealConclnDesc(String dealConclnDesc) {
		this.dealConclnDesc = dealConclnDesc;
	}

}
