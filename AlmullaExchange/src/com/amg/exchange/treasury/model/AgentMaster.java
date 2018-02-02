package com.amg.exchange.treasury.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EX_AGENT_MASTER")
public class AgentMaster implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private BigDecimal agentId;
	private String agentName;
	private String agentDesc;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	
	public AgentMaster(){
		
	}
	
	
	
	/**
	 * @param agentId
	 * @param agentName
	 * @param agentDesc
	 * @param createdBy
	 * @param createdDate
	 * @param modifiedBy
	 * @param modifiedDate
	 */
	public AgentMaster(BigDecimal agentId, String agentName, String agentDesc,
			String createdBy, Date createdDate, String modifiedBy,
			Date modifiedDate) {
		
		this.agentId = agentId;
		this.agentName = agentName;
		this.agentDesc = agentDesc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedBy = modifiedBy;
		this.modifiedDate = modifiedDate;
	}


	@Id
	@Column(name ="AGENT_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getAgentId() {
		return agentId;
	}
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}
	@Column(name ="AGENT_NAME")
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	@Column(name ="AGENT_DESC")
	public String getAgentDesc() {
		return agentDesc;
	}
	public void setAgentDesc(String agentDesc) {
		this.agentDesc = agentDesc;
	}
	@Column(name = "CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	@Column(name = "CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}@Column(name = "MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name = "MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	
}
