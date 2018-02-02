package com.amg.exchange.common.model;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/*******************************************************************************************************************

		 File		: ComponentTypeDetail.java
 
		 Project	: AlmullaExchangeService

		 Package	: com.amg.exchange.common.model
 
		 Created	:	
 						Date	: 08-Jul-2014 2:01:49 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 08-Jul-2014 2:01:49 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@SuppressWarnings("serial")
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_COMPONENT_TYPE_DETAIL")
public class ComponentTypeDetail implements java.io.Serializable {

	private BigDecimal componentTypeDetailId;
	private ComponentType componentType;
	private String ismultiple;
	private String visibility;
	private String minlength;
	private String maxlength;
	private String numeric;
	private String alphabet;
	private String special;
	private String mandatory;
	private String readonly;
	private String enable;
	private String defaultValue;

	public ComponentTypeDetail() {
	}

	public ComponentTypeDetail(BigDecimal componentTypeDetailId,
			ComponentType componentType, String ismultiple, String visibility,
			String minlength, String maxlength, String numeric,
			String alphabet, String special, String mandatory, String readonly,
			String enable, String defaultValue) {

		this.componentTypeDetailId = componentTypeDetailId;
		this.componentType = componentType;
		this.ismultiple = ismultiple;
		this.visibility = visibility;
		this.minlength = minlength;
		this.maxlength = maxlength;
		this.numeric = numeric;
		this.alphabet = alphabet;
		this.special = special;
		this.mandatory = mandatory;
		this.readonly = readonly;
		this.enable = enable;
		this.defaultValue = defaultValue;
	}

	@Id
	/*@TableGenerator(name = "componenttypedetailid", table = "fs_componenttypedetailidpk", pkColumnName = "componenttypedetailidkey", pkColumnValue = "componenttypedetailidvalue", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "componenttypedetailid")FS_COMPONENT_TYPE_DETAIL
	*/
	@GeneratedValue(generator="fs_component_type_detail_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_component_type_detail_seq" ,sequenceName="FS_COMPONENT_TYPE_DETAIL_SEQ",allocationSize=1)	
	@Column(name = "COMPONENT_TYPE_DETAIL_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getComponentTypeDetailId() {
		return componentTypeDetailId;
	}

	public void setComponentTypeDetailId(BigDecimal componentTypeDetailId) {
		this.componentTypeDetailId = componentTypeDetailId;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "COMPONENT_TYPE_ID")
	public ComponentType getComponentType() {
		return componentType;
	}

	public void setComponentType(ComponentType componentType) {
		this.componentType = componentType;
	}

	@Column(name = "ISMULTIPLE", length = 1)
	public String getIsmultiple() {
		return ismultiple;
	}

	public void setIsmultiple(String ismultiple) {
		this.ismultiple = ismultiple;
	}

	@Column(name = "VISIBILITY", length = 1)
	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	@Column(name = "MINLENGTH", length = 1)
	public String getMinlength() {
		return minlength;
	}

	public void setMinlength(String minlength) {
		this.minlength = minlength;
	}

	@Column(name = "MAXLENGTH", length = 1)
	public String getMaxlength() {
		return maxlength;
	}

	public void setMaxlength(String maxlength) {
		this.maxlength = maxlength;
	}

	@Column(name = "NUMERIC", length = 1)
	public String getNumeric() {
		return numeric;
	}

	public void setNumeric(String numeric) {
		this.numeric = numeric;
	}

	@Column(name = "ALPHABET", length = 1)
	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	@Column(name = "SPECIAL", length = 1)
	public String getSpecial() {
		return special;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	@Column(name = "MANDATORY", length = 1)
	public String getMandatory() {
		return mandatory;
	}

	public void setMandatory(String mandatory) {
		this.mandatory = mandatory;
	}

	@Column(name = "READONLY", length = 1)
	public String getReadonly() {
		return readonly;
	}

	public void setReadonly(String readonly) {
		this.readonly = readonly;
	}

	@Column(name = "ENABLE", length = 1)
	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	@Column(name = "DEFAULT_VALUE", length = 1)
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

}
