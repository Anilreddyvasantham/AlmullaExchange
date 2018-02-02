package com.amg.exchange.remittance.model;

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
@Table(name="EX_TRANSFER_MODE")
public class TransferMode implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	
	private BigDecimal transferModeId;
	private String transferMode;
	private String transferModeDesc;
	private String createdBy;
	private Date createdDate;
	private String isactive;
	
	public TransferMode()
	{
		
	}
	public TransferMode(BigDecimal transferModeId, String transferMode,
			String transferModeDesc, String createdBy, Date createdDate,
			String isactive) {
		super();
		this.transferModeId = transferModeId;
		this.transferMode = transferMode;
		this.transferModeDesc = transferModeDesc;
		this.createdBy = createdBy;
		this.createdDate = createdDate;
		this.isactive = isactive;
	}

	@Id
	@GeneratedValue(generator="ex_transfer_mode_seq",strategy=GenerationType.SEQUENCE)
	@SequenceGenerator(name="ex_transfer_mode_seq",sequenceName="EX_TRANSFER_MODE_SEQ",allocationSize=1)
	@Column(name="TRANSFER_MODE_ID", unique=true, nullable=false, precision=22, scale=0)
	public BigDecimal getTransferModeId() {
		return transferModeId;
	}


	public void setTransferModeId(BigDecimal transferModeId) {
		this.transferModeId = transferModeId;
	}

	@Column(name="TRANSFER_MODE")
	public String getTransferMode() {
		return transferMode;
	}


	public void setTransferMode(String transferMode) {
		this.transferMode = transferMode;
	}

	@Column(name="TRANSFER_MODE_DESC")
	public String getTransferModeDesc() {
		return transferModeDesc;
	}


	public void setTransferModeDesc(String transferModeDesc) {
		this.transferModeDesc = transferModeDesc;
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

	@Column(name="ISACTIVE")
	public String getIsactive() {
		return isactive;
	}


	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}
	
	
	

}
