package com.amg.exchange.loyalty.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
@Entity
@Table(name = "EX_LTY_SCHEDULER")
public class LoyaltyScheduler implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal loyaltySchedulerId;
	private String enableFlag;
	private String dayFlag;
	private String timeToStart;
	private String schedularFlag;
	private String createdBy;
	private  Date createdDate;
	private Date modifiedDate;
	private String modifiedBy;
	private String isActive;
	
	
	public LoyaltyScheduler() {
		super();
		 
	}
	public LoyaltyScheduler(BigDecimal loyaltySchedulerId, String enableFlag,
			String dayFlag, String timeToStart, String schedularFlag,
			String createdBy, Date createdDate, Date modifiedDate,
			String modifiedBy) {
		super();
		this.loyaltySchedulerId = loyaltySchedulerId;
		this.enableFlag = enableFlag;
		this.dayFlag = dayFlag;
		this.timeToStart = timeToStart;
		this.schedularFlag = schedularFlag;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.modifiedBy = modifiedBy;
	}
	
	@Id
	@GeneratedValue(generator = "ex_lty_scheduler_seq", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "ex_lty_scheduler_seq", sequenceName = "EX_LTY_SCHEDULER_SEQ", allocationSize = 1)
	@Column(name = "LTY_SCHEDULER_ID", unique = true, nullable = false, precision = 22, scale = 0)
	public BigDecimal getLoyaltySchedulerId() {
		return loyaltySchedulerId;
	}
	public void setLoyaltySchedulerId(BigDecimal loyaltySchedulerId) {
		this.loyaltySchedulerId = loyaltySchedulerId;
	}
	@Column(name="ENABLE_FLAG")
	public String getEnableFlag() {
		return enableFlag;
	}
	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	@Column(name="DAY_FLAG")
	public String getDayFlag() {
		return dayFlag;
	}
	public void setDayFlag(String dayFlag) {
		this.dayFlag = dayFlag;
	}
	@Column(name="TIME_TO_START")
	public String getTimeToStart() {
		return timeToStart;
	}
	public void setTimeToStart(String timeToStart) {
		this.timeToStart = timeToStart;
	}
	@Column(name="SCHEDULER_FLAG")
	public String getSchedularFlag() {
		return schedularFlag;
	}
	public void setSchedularFlag(String schedularFlag) {
		this.schedularFlag = schedularFlag;
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
	@Column(name="MODIFIED_DATE")
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	@Column(name="MODIFIED_BY")
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	@Column(name="ISACTIVE")
	public String getIsActive() {
		return isActive;
	}
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
	

}
