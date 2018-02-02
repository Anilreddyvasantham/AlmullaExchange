package com.amg.exchange.foreigncurrency.model;

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
@Table(name = "EX_SOURCE_OF_INCOME_DESC" )
public class SourceOfIncomeDescription implements Serializable {

	 	private static final long serialVersionUID = 1L;
 
	 	private BigDecimal sourceOfIncomeDescId;
	 	private String sourceOfIncomeFullDesc;
	 	private String sourceOfIncomeShortDesc;
	 	private LanguageType languageId;
	 	private  SourceOfIncome sourceOfIncomeId;
	 	
	 	
	 	public SourceOfIncomeDescription() {
			super();
			 
		}

		public SourceOfIncomeDescription(BigDecimal sourceOfIncomeDescId,
				String sourceOfIncomeFullDesc, String sourceOfIncomeShortDesc,
				LanguageType languageId, SourceOfIncome sourceOfIncomeId) {
			super();
			this.sourceOfIncomeDescId = sourceOfIncomeDescId;
			this.sourceOfIncomeFullDesc = sourceOfIncomeFullDesc;
			this.sourceOfIncomeShortDesc = sourceOfIncomeShortDesc;
			this.languageId = languageId;
			this.sourceOfIncomeId = sourceOfIncomeId;
		}
	 	
		@Id
		@GeneratedValue(generator="ex_source_of_income_desc_seq",strategy=GenerationType.SEQUENCE)
		@SequenceGenerator(name="ex_source_of_income_desc_seq",sequenceName="EX_SOURCE_OF_INCOME_DESC_SEQ",allocationSize=1)
		@Column(name = "SOURCE_OF_INCOME_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	 	public BigDecimal getSourceOfIncomeDescId() {
			return sourceOfIncomeDescId;
		}
		public void setSourceOfIncomeDescId(BigDecimal sourceOfIncomeDescId) {
			this.sourceOfIncomeDescId = sourceOfIncomeDescId;
		}
		@Column(name = "FULL_DESC")
		public String getSourceOfIncomeFullDesc() {
			return sourceOfIncomeFullDesc;
		}
		public void setSourceOfIncomeFullDesc(String sourceOfIncomeFullDesc) {
			this.sourceOfIncomeFullDesc = sourceOfIncomeFullDesc;
		}
		@Column(name = "SHORT_DESC")
		public String getSourceOfIncomeShortDesc() {
			return sourceOfIncomeShortDesc;
		}
		public void setSourceOfIncomeShortDesc(String sourceOfIncomeShortDesc) {
			this.sourceOfIncomeShortDesc = sourceOfIncomeShortDesc;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "LANGUAGE_ID")
		public LanguageType getLanguageId() {
			return languageId;
		}
		public void setLanguageId(LanguageType languageId) {
			this.languageId = languageId;
		}
		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name = "SOURCE_OF_INCOME_ID")
		public SourceOfIncome getSourceOfIncomeId() {
			return sourceOfIncomeId;
		}
		public void setSourceOfIncomeId(SourceOfIncome sourceOfIncomeId) {
			this.sourceOfIncomeId = sourceOfIncomeId;
		}
		

}
