package com.amg.exchange.remittance.model;

/**
 * Author :MRU
 * Date   :14/12/2015
 * Purpose: Mapping with Agent Table
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;



@Entity
@Table(name="EX_ROUTING_AGENTS")
public class RoutingAgent {
	private BigDecimal routingAgentId;
	private BigDecimal routingHeaderId;
	private BigDecimal agentId;
	private BigDecimal agentBranchId;
	private String agentBranchApplicability;
	private String isActive;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private String approvedBy;
	private Date approvedDate;
	

	@Id
	@GeneratedValue(generator="EX_ROUTING_AGENTS_SEQ",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="EX_ROUTING_AGENTS_SEQ",sequenceName="EX_ROUTING_AGENTS_SEQ",allocationSize=1)
	@Column(name ="ROUTING_AGENTS_ID" , unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getRoutingAgentId() {
		return routingAgentId;
	}
	public void setRoutingAgentId(BigDecimal routingAgentId) {
		this.routingAgentId = routingAgentId;
	}
	@Column(name ="ROUTING_HEADER_ID")
	public BigDecimal getRoutingHeaderId() {
		return routingHeaderId;
	}
	public void setRoutingHeaderId(BigDecimal routingHeaderId) {
		this.routingHeaderId = routingHeaderId;
	}
	@Column(name ="AGENT_ID")
	public BigDecimal getAgentId() {
		return agentId;
	}
	public void setAgentId(BigDecimal agentId) {
		this.agentId = agentId;
	}
	@Column(name ="AGENT_BRANCH_ID")
	public BigDecimal getAgentBranchId() {
		return agentBranchId;
	}
	public void setAgentBranchId(BigDecimal agentBranchId) {
		this.agentBranchId = agentBranchId;
	}
	
	@Column(name="AGENT_BRANCH_APPLICABILITY")
	public String getAgentBranchApplicability() {
		return agentBranchApplicability;
	}

	public void setAgentBranchApplicability(String agentBranchApplicability) {
		this.agentBranchApplicability = agentBranchApplicability;
	}

	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	
	@Column(name="CREATED_BY")
	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	@Column(name="APPROVED_BY")
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	
	@Column(name="APPROVED_DATE")
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
}
