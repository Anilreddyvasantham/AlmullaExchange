package com.amg.exchange.remittance.model;

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
@Table(name="EX_RELATIONS_DESC")
public class RelationsDescription implements Serializable {

	private static final long serialVersionUID = 1L;
	private  BigDecimal relationsDescriptionId;
	private Relations relations;
	private LanguageType languageType;
	private String localRelationsDescription;
	
	
	public RelationsDescription() {
	}
	public RelationsDescription(BigDecimal relationsDescriptionId) {
		this.relationsDescriptionId = relationsDescriptionId;
	}
	public RelationsDescription(BigDecimal relationsDescriptionId,
			Relations relations, LanguageType languageType,
			String localRelationsDescription) {
		this.relationsDescriptionId = relationsDescriptionId;
		this.relations = relations;
		this.languageType = languageType;
		this.localRelationsDescription = localRelationsDescription;
	}
	
	
	@Id
	@GeneratedValue(generator="ex_relations_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_relations_desc_seq",sequenceName="EX_RELATIONS_DESC_SEQ",allocationSize=1)
	@Column(name ="RELEATIONS_DESC_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRelationsDescriptionId() {
		return relationsDescriptionId;
	}
	public void setRelationsDescriptionId(BigDecimal relationsDescriptionId) {
		this.relationsDescriptionId = relationsDescriptionId;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RELATIONS_ID")
	public Relations getRelations() {
		return relations;
	}
	public void setRelations(Relations relations) {
		this.relations = relations;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getLanguageType() {
		return languageType;
	}
	public void setLanguageType(LanguageType languageType) {
		this.languageType = languageType;
	}
	@Column(name="LOCAL_RELATIONS_DESC")
	public String getLocalRelationsDescription() {
		return localRelationsDescription;
	}
	public void setLocalRelationsDescription(String localRelationsDescription) {
		this.localRelationsDescription = localRelationsDescription;
	}
	
	
	
	
	
	
}
