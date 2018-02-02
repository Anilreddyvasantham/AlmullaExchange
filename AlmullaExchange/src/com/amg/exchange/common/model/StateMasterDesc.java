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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/*******************************************************************************************************************

		 File		: StateMasterDesc.java
 
		 Project	: AlmullaExchange

		 Package	: com.amg.exchange.model
 
		 Created	:	
 						Date	: 29-May-2014 5:18:06 pm
		 				By		: Justin Vincent
 						Revision:
 
 		 Last Change:
 						Date	: 29-May-2014 5:18:06 pm
 						By		: Justin Vincent
		 				Revision:

		 Description: TODO 

********************************************************************************************************************/
@Cacheable  
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "FS_STATE_MASTER_DESC" )
public class StateMasterDesc implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private BigDecimal stateDescId;
	private StateMaster fsStateMaster;
	private LanguageType fsLanguageType;
	private String stateName;

	public StateMasterDesc() {
	}

	public StateMasterDesc(BigDecimal stateDescId) {
		this.stateDescId = stateDescId;
	}

	public StateMasterDesc(BigDecimal stateDescId,
			StateMaster fsStateMaster, LanguageType fsLanguageType,
			String stateName) {
		this.stateDescId = stateDescId;
		this.fsStateMaster = fsStateMaster;
		this.fsLanguageType = fsLanguageType;
		this.stateName = stateName;
	}

/*	@Id
	@TableGenerator(name="statedescid",table="fs_statedescidpk",pkColumnName="statedescidkey",pkColumnValue="statedescidvalue",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE,generator="statedescid")*/
	
	@Id
	@GeneratedValue(generator="fs_state_master_desc_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="fs_state_master_desc_seq",sequenceName="FS_STATE_MASTER_DESC_SEQ",allocationSize=1)
	@Column(name = "STATE_DESC_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getStateDescId() {
		return this.stateDescId;
	}

	public void setStateDescId(BigDecimal stateDescId) {
		this.stateDescId = stateDescId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "STATE_ID")
	public StateMaster getFsStateMaster() {
		return this.fsStateMaster;
	}

	public void setFsStateMaster(StateMaster fsStateMaster) {
		this.fsStateMaster = fsStateMaster;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LANGUAGE_ID")
	public LanguageType getFsLanguageType() {
		return this.fsLanguageType;
	}

	public void setFsLanguageType(LanguageType fsLanguageType) {
		this.fsLanguageType = fsLanguageType;
	}

	@Column(name = "STATE_NAME", length = 45)
	public String getStateName() {
		return this.stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

}
